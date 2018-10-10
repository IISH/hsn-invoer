/**
 * Combines various data attributes to be used on elements.
 * Deals with actions to be performed when certain values are entered in input fields.
 */
(function ($) {
    'use strict';

    var regexOnlyNumbers = /^[0-9]+$/;

    /**
     * Replaces the first character with an uppercase character.
     */
    function onUppercase(elem) {
        // If there is already a 'integer-field' or 'data-valid-chars' handler, don't handle the new event
        var caret = elem.getCaret();
        if (!elem.hasClass('integer-field') &&
            !elem.hasClass('is-id') &&
            !elem.hasClass('no-auto-uppercase') &&
            (!elem.is('[data-valid-chars]') || (elem.is('[data-valid-chars]') && elem.hasClass('auto-uppercase')))) {

            var val = elem.val();
            var firstChar = val.substr(0, 1);
            var firstCharUpper = firstChar.toUpperCase();
            if (firstChar !== firstCharUpper) {
                elem.val(firstCharUpper + val.substr(1));
                elem.setCaret(caret);
            }
        }
    }

    /**
     * Submit the form when the user pressed a (valid) key.
     */
    function onSubmitOnKeyup(elem) {
        if (elem.val().trim().length > 0) {
            elem.closest('form').submit();
        }
    }

    /**
     * Only allow numbers to be entered in a field.
     */
    function onIntegerField(elem, e) {
        // If there is already a 'valid chars' handler, don't handle the new event
        if (!elem.is('[data-valid-chars]') && (e.charCode !== 0)) {
            var char = String.fromCharCode(e.which);
            var allow = regexOnlyNumbers.test(char);

            // If the character is not allowed, but we are about to replace the first character,
            // then check if the first character starts with a dash to indicate a negative number
            if (!allow && (elem.getCaret() === 0)) {
                allow = (char === '-') && (!elem.attr('maxlength') || elem.attr('maxlength') > 1);
            }

            if (!allow) e.stopImmediatePropagation();
            return allow;
        }
        return true;
    }

    /**
     * Only allow a certain set of characters to be entered in a field.
     * Some examples:
     * 1)   data-valid-chars="j;n"
     *      Only the characters 'j' and 'n' can be entered.
     * 2)   data-valid-chars="1;2;3;4;5;6;7;8;9"
     *      Only the numbers '1' until '9' can be entered.
     */
    function onValidChars(elem, e) {
        if (e.charCode !== 0) {
            var validChars = elem.getMultipleDataValues('valid-chars');
            var char = String.fromCharCode(e.which);
            if (elem.hasClass('auto-uppercase'))
                char = char.toUpperCase();
            var allow = (validChars.indexOf(char) >= 0);
            if (!allow) e.stopImmediatePropagation();
            return allow;
        }
        return true;
    }

    /**
     * Replace certain values of a field with another value.
     * Some examples:
     * 1)   data-replace="1:2;3:4"
     *      Replace the value '1' with the value '2' and replace '3' with the value '4'.
     * 2)   data-replace=":-1"
     *      Replace an empty value with the value '-1'.
     */
    function onReplace(elem) {
        var replace = elem.getMultipleDataValues('replace');
        for (var i = 0; i < replace.length; i++) {
            var delimiterPos = replace[i].indexOf(':');
            var left = replace[i].substring(0, delimiterPos);
            var right = replace[i].substring(delimiterPos + 1, replace[i].length);

            if (elem.val() == left) {
                elem.val(right);
            }
        }
    }

    /**
     * Replace certain values of a field with another value in another field.
     * Some examples:
     * 1)   data-replace-in-field="#a" data-replace="1:2;3:4"
     *      If the value is '1', then set the value '2' in element with id 'a'.
     *      If the value is '3', then set the value '4' in element with id 'a'.
     * 2)   data-replace-in-field="#b" data-replace=":-1"
     *      If there is an empty value, then set the value '-1' in element with id 'b'.
     */
    function onReplaceInField(elem) {
        var replaceInField = elem.getDataValueAsElem('replace-in-field');
        var forValues = elem.getMultipleDataValues('for-values');

        for (var i = 0; i < forValues.length; i++) {
            var delimiterPos = forValues[i].indexOf(':');
            var left = forValues[i].substring(0, delimiterPos);
            var right = forValues[i].substring(delimiterPos + 1, forValues[i].length);

            if (elem.val() == left) {
                replaceInField.val(right);
            }
        }
    }

    /**
     * Set a trigger on a field only to be shown when a certain condition has been fulfilled.
     * Some examples:
     * 1)   data-show-when="#a" data-has-values-in="1;2;3"
     *      Only show this field when an element with id 'a' has the values 1, 2 or 3.
     *
     * 2)   data-show-when="#b" data-has-values-not-in="4,5,6"
     *      Only show this field when an element with id 'b' does NOT have the values 4, 5 or 6.
     */
    function prepareShowWhen(elem) {
        var showWhen = elem.getDataValueAsElem('show-when');

        hasValues(
            elem, showWhen,
            function () {
                var visible = showWhen.is(':visible');
                var hidden = showWhen.is('input[type=hidden]');
                if (visible || hidden) {
                    elem.show();
                }
                else {
                    elem.hide();
                }
                return visible || hidden;
            },
            function () {
                elem.hide();
                return false;
            }
        );
    }

    /**
     * Set the value of a field based on a certain condition.
     * Some examples:
     * 1)   data-set-value-when="#a" data-has-values-in="1;2;3" data-then-value-of="#b" data-else-value="4"
     *      When an element with id 'a' has the values 1, 2 or 3, then the value of this element equals the value
     *      of the element with id 'b', else the value of this element equals 4.
     *
     * 2)   data-set-value-when="#b" data-has-values-in="4;5;6" data-then-value="7" data-else-value="#a"
     *      When an element with id 'b' does NOT have the values 4, 5 or 6, then the value of this element equals 7,
     *      else the value of this element equals the value of the element with id 'a'.
     */
    function prepareSetValueWhen(elem) {
        var setValueWhen = elem.getDataValueAsElem('set-value-when');

        var thenCase = new ConditionalCase(elem, elem.getDataValue('then-value'), elem.getDataValueAsElem('then-value-of'));
        var elseCase = new ConditionalCase(elem, elem.getDataValue('else-value'), elem.getDataValueAsElem('else-value-of'));

        hasValues(
            elem, setValueWhen,
            function () {
                elseCase.disableCase();
                thenCase.enableCase();
            },
            function () {
                thenCase.disableCase();
                elseCase.enableCase();
            }
        );
    }

    function ConditionalCase(mainElement, value, valueElement) {
        if ((value === undefined) && (valueElement.length === 0)) {
            valueElement = mainElement;
        }

        var conditionalCase = {
            isActive: false,
            mainElement: mainElement,

            value: value,
            valueElement: valueElement,

            valueElementUpdate: undefined,

            enableCase: function enableCase() {
                this.isActive = true;
                if (this.valueElement.is('input')) {
                    this.valueElementUpdate();
                    if (this.mainElement !== this.valueElement) {
                        this.valueElement.on('keyup', this.valueElementUpdate);
                    }
                }
                else {
                    this.mainElement.val(this.value);
                }
            },

            disableCase: function disableCase() {
                this.isActive = false;
                this.valueElement.off('keyup', this.valueElementUpdate);
            }
        };

        // This function is used in an event, where 'this' won't refer to this object anymore.
        // Therefor refer to this object using the variable 'conditionalCase'.
        conditionalCase.valueElementUpdate = function () {
            if (conditionalCase.isActive) {
                conditionalCase.mainElement.val(conditionalCase.valueElement.val());
            }
        };

        return conditionalCase;
    }

    function hasValues(elem, target, onTrue, onFalse) {
        function valuesAreDefined(onValues) {
            return ((onValues !== undefined) && (onValues.length > 0));
        }

        function checkConditions(onValues, isNegate) {
            var newResult = null;
            if (onValues.indexOf(target.val()) >= 0) {
                newResult = isNegate ? onFalse() : onTrue();
            }
            else {
                newResult = isNegate ? onTrue() : onFalse();
            }

            if (newResult !== previousResult) {
                $.triggerChangeOfState();
            }
            previousResult = newResult;
        }

        function onEvent() {
            if (valuesAreDefined(onValuesIn)) {
                checkConditions(onValuesIn, false);
            }
            else if (valuesAreDefined(onValuesNotIn)) {
                checkConditions(onValuesNotIn, true);
            }
        }

        var onValuesIn = elem.getDataValue('has-values-in');
        onValuesIn = (onValuesIn !== undefined) ? onValuesIn.toString().split(';') : onValuesIn;
        var onValuesNotIn = elem.getDataValue('has-values-not-in');
        onValuesNotIn = (onValuesNotIn !== undefined) ? onValuesNotIn.toString().split(';') : onValuesNotIn;

        var previousResult = null;

        onEvent();
        $(target).change(onEvent);
    }

    function prepareByz(elem) {
        var allByzElem = elem.find('textarea');
        allByzElem[0].setSelectionRange(allByzElem.val().length * 2, allByzElem.val().length * 2);
    }

    function setOverwrite(elem, e) {
        if ((e.charCode !== 0) && !elem.prop('readonly')) {
            var text = elem.valNoEvent();
            var caret = elem.getCaret();

            // First remove the character that will be replaced
            var output = text.substring(0, caret);
            elem.valNoEvent(output + text.substring(caret + 1));

            // Then reinitialize the caret position
            elem.setCaret(caret);
        }
        return true;
    }

    // IE9 does not support 'maxlength' on textarea
    function setMaxLength(elem) {
        var length = elem.val().length;
        var maxlength = elem.attr('maxlength');
        if (maxlength && (length > maxlength)) {
            elem.val(elem.val().slice(0, maxlength));
        }
    }

    function updateScrollPosition(elem) {
        var parent = elem.closest('.complete-focus');
        if (parent.length > 0) {
            parent.get(0).scrollIntoView({block: 'end', behavior: 'smooth'});
        }
    }

    function initZero(elem) {
        if (elem.val().length === 0) {
            elem.val('0');
        }
    }

    $(document).on('keyup', '.form-elem', function (e) {
        var elem = $(e.target);

        onUppercase(elem);

        if (elem.hasClass('submit-on-keyup')) {
            onSubmitOnKeyup(elem);
        }
    }).on('keypress', '.form-elem', function (e) {
        var elem = $(e.target);
        var allow = true;

        if (elem.hasClass('integer-field')) {
            allow = allow && onIntegerField(elem, e);
        }

        if (elem.is($.getDataElemSelector('valid-chars'))) {
            allow = allow && onValidChars(elem, e);
        }

        if (!elem.is('textarea')) {
            allow = allow && setOverwrite(elem, e);
        }

        return allow;
    }).on('blur', 'input', function (e) {
        var elem = $(e.target);

        if (elem.is($.getDataElemSelector('replace'))) {
            onReplace(elem);
        }

        if (elem.is($.getDataElemSelector('replace-in-field'))) {
            onReplaceInField(elem);
        }

        updateScrollPosition(elem);
    });

    // IE9 does not support 'maxlength' on textarea
    if ((window.navigator.userAgent.indexOf('MSIE ') > 0) || !!navigator.userAgent.match(/Trident.*rv\:11\./)) {
        $(document).on('keypress blur', 'textarea', function (e) {
            return setMaxLength($(e.target));
        });
    }

    $.registerInit(function (elem) {
        elem.find($.getDataElemSelector('replace')).each(function () {
            onReplace($(this));
        });

        elem.find($.getDataElemSelector('replace-in-field')).each(function () {
            onReplaceInField($(this));
        });

        // We have to place individual events on elements based on data attribute values.
        // So we cannot register global events on the document level.

        elem.find($.getDataElemSelector('show-when')).each(function () {
            prepareShowWhen($(this));
        });

        elem.find($.getDataElemSelector('set-value-when')).each(function () {
            prepareSetValueWhen($(this));
        });

        var byz = elem.find('.bijzonderheden');
        if (byz.length > 0) {
            prepareByz(byz);
        }

        elem.find('.init-zero').each(function () {
            initZero($(this));
        });
    });
})(jQuery);