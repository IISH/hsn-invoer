/**
 * Combines various data attributes to be used on elements.
 * Deals with actions to be performed when certain values are entered in input fields.
 */
(function ($) {
    var regexOnlyNumbers = /^[0-9/-]+$/;

    /**
     * Submit the form when the user pressed a (valid) key.
     */
    var onSubmitOnKeyup = function (elem) {
        if (elem.val().trim().length > 0) {
            elem.closest('form').submit();
        }
    };

    /**
     * Only allow numbers to be entered in a field.
     */
    var onIntegerField = function (elem, e) {
        // If there is already a 'valid chars' handler, don't handle the new event
        if (!elem.is('[data-valid-chars]') && (e.charCode !== 0)) {
            var char = String.fromCharCode(e.which);
            return regexOnlyNumbers.test(char);
        }
    };

    /**
     * Only allow a certain set of characters to be entered in a field.
     * Some examples:
     * 1)   data-valid-chars="j;n"
     *      Only the characters 'j' and 'n' can be entered.
     * 2)   data-valid-chars="1;2;3;4;5;6;7;8;9"
     *      Only the numbers '1' until '9' can be entered.
     */
    var onValidChars = function (elem, e) {
        if (e.charCode !== 0) {
            var validChars = elem.getMultipleDataValues('valid-chars');
            var char = String.fromCharCode(e.which);
            return (validChars.indexOf(char) >= 0);
        }
    };

    /**
     * Only allow a value of a field within a certain range, otherwise clear the field.
     * Some examples:
     * 1)   data-min-value="100" data-max-value="200"
     *      Only entered numbers that fall within the range of (including) 100 and 200 can be entered.
     * 2)   data-min-value="150"
     *      Only if 150 or larger was entered, the value is accepted.
     */
    var onMinValue = function (elem) {
        var minValue = elem.getIntegerDataValue('min-value');
        var number = elem.getIntegerValue();

        if ((number !== -1) && (isNaN(number) || (number < minValue))) {
            elem.val('');
        }
        else {
            elem.val(number);
        }
    };

    var onMaxValue = function (elem) {
        var maxValue = elem.getIntegerDataValue('max-value');
        var number = elem.getIntegerValue();

        if ((number !== -1) && (isNaN(number) || (number > maxValue))) {
            elem.val('');
        }
        else {
            elem.val(number);
        }
    };

    /**
     * Replace certain values of a field with another value.
     * Some examples:
     * 1)   data-replace="1:2;3:4"
     *      Replace the value '1' with the value '2' and replace '3' with the value '4'.
     * 2)   data-replace=":-1"
     *      Replace an empty value with the value '-1'.
     */
    var onReplace = function (elem) {
        var replace = elem.getMultipleDataValues('replace');
        for (var i = 0; i < replace.length; i++) {
            var delimiterPos = replace[i].indexOf(':');
            var left = replace[i].substring(0, delimiterPos);
            var right = replace[i].substring(delimiterPos + 1, replace[i].length);

            if (elem.val() == left) {
                elem.val(right);
            }
        }
    };

    /**
     * Replace certain values of a field with another value in another field.
     * Some examples:
     * 1)   data-replace-in-field="#a" data-replace="1:2;3:4"
     *      If the value is '1', then set the value '2' in element with id 'a'.
     *      If the value is '3', then set the value '4' in element with id 'a'.
     * 2)   data-replace-in-field="#b" data-replace=":-1"
     *      If there is an empty value, then set the value '-1' in element with id 'b'.
     */
    var onReplaceInField = function (elem) {
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
    };

    /**
     * Set a trigger on a field only to be shown when a certain condition has been fulfilled.
     * Some examples:
     * 1)   data-show-when="#a" data-has-values-in="1;2;3"
     *      Only show this field when an element with id 'a' has the values 1, 2 or 3.
     *
     * 2)   data-show-when="#b" data-has-values-not-in="4,5,6"
     *      Only show this field when an element with id 'b' does NOT have the values 4, 5 or 6.
     */
    var prepareShowWhen = function (elem) {
        var showWhen = elem.getDataValueAsElem('show-when');

        hasValues(
            elem, showWhen,
            function () {
                if (showWhen.is(':visible')) {
                    elem.show();
                }
                else {
                    elem.hide();
                }
            },
            function () {
                elem.hide();
            }
        );

        hasNoErrors(
            elem, showWhen,
            function () {
                elem.show();
            },
            function () {
                elem.hide();
            }
        );
    };

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
    var prepareSetValueWhen = function (elem) {
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
    };

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

            enableCase: function () {
                this.isActive = true;
                if (this.valueElement.is('input')) {
                    this.valueElementUpdate();
                    if (this.mainElement !== this.valueElement) {
                        this.valueElement.on('keyup', this.valueElementUpdate);
                    }
                }
                else {
                    this.mainElement
                        .val(this.value)
                        .trigger('change');
                }
            },

            disableCase: function () {
                this.isActive = false;
                this.valueElement.off('keyup', this.valueElementUpdate);
            }
        };

        // This function is used in an event, where 'this' won't refer to this object anymore.
        // Therefor refer to this object using the variable 'conditionalCase'.
        conditionalCase.valueElementUpdate = function () {
            if (conditionalCase.isActive) {
                conditionalCase.mainElement
                    .val(conditionalCase.valueElement.val())
                    .trigger('change');
            }
        };

        return conditionalCase;
    }

    var hasValues = function (elem, target, onTrue, onFalse) {
        var onValuesIn = elem.getDataValue('has-values-in');
        onValuesIn = (onValuesIn !== undefined) ? onValuesIn.toString().split(';') : onValuesIn;
        var onValuesNotIn = elem.getDataValue('has-values-not-in');
        onValuesNotIn = (onValuesNotIn !== undefined) ? onValuesNotIn.toString().split(';') : onValuesNotIn;

        var valuesAreDefined = function (onValues) {
            return ((onValues !== undefined) && (onValues.length > 0));
        };
        var checkConditions = function (onValues, isNegate) {
            if (onValues.indexOf(target.val()) >= 0) {
                isNegate ? onFalse() : onTrue();
            }
            else {
                isNegate ? onTrue() : onFalse();
            }
        };
        var onEvent = function () {
            if (valuesAreDefined(onValuesIn)) {
                checkConditions(onValuesIn, false);
            }
            else if (valuesAreDefined(onValuesNotIn)) {
                checkConditions(onValuesNotIn, true);
            }
        };

        onEvent();
        $(document).on('keyup', onEvent);
    };

    var hasNoErrors = function (elem, target, onTrue, onFalse) {
        if (elem.data('has-no-errors') !== undefined) {
            var onEvent = function () {
                if (target.hasClass('has-an-error')) {
                    onFalse();
                }
                else {
                    onTrue();
                }
            };

            onEvent();
            $(document).blur(onEvent);
        }
    };

    $(document).on('keyup', '.submit-on-keyup', function (e) {
        return onSubmitOnKeyup($(e.target));
    });

    $(document).on('keypress', '.integer-field', function (e) {
        return onIntegerField($(e.target), e);
    });

    $(document).on('keypress', $.getDataElemSelector('valid-chars'), function (e) {
        return onValidChars($(e.target), e);
    });

    $(document).on('blur', $.getDataElemSelector('min-value'), function (e) {
        onMinValue($(e.target));
    });

    $(document).on('blur', $.getDataElemSelector('max-value'), function (e) {
        onMaxValue($(e.target));
    });

    $(document).on('blur', $.getDataElemSelector('replace'), function (e) {
        onReplace($(e.target));
    });

    $(document).on('blur', $.getDataElemSelector('replace-in-field'), function (e) {
        onReplaceInField($(e.target));
    });

    $.registerInit(function (elem) {
        elem.find($.getDataElemSelector('min-value')).each(function () {
            onMinValue($(this));
        });

        elem.find($.getDataElemSelector('max-value')).each(function () {
            onMaxValue($(this));
        });

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
    });
})(jQuery);