/**
 * Checks for elements with the data-byz attribute to see if the user should go to the bijzonderheden screen.
 * And disables both the byz button and the next button in case of errors.
 */
(function ($) {
    'use strict';

    $.setError = function setError(isError, name, errorMessage, elemParent, allNextBtns) {
        if (name.indexOf('.') >= 0) {
            name = name.substring(0, name.indexOf('.'));
        }

        addError(isError, name, name + '-error', allNextBtns, function (messages, message) {
            var alertBox = messages;
            if (!messages.hasClass('alert')) {
                alertBox = messages.find('.alert');
            }

            if (message.length === 0) {
                message = $('<div class="' + name + '"></div>');
                alertBox.append(message);

                if (elemParent !== undefined) {
                    message.data('error-elem', elemParent);
                }
            }

            message.html(errorMessage);
            return message;
        });
    };

    $.setErrorWithClass = function setErrorWithClass(isError, name, elem, errorMessage, allNextBtns) {
        addError(isError, null, name + '-error', allNextBtns);
        isError ? elem.text(errorMessage).show() : elem.hide();
    };

    $.fn.hasErrorWhen = function hasErrorWhen(condition, elemParent) {
        if (elemParent === undefined || elemParent === null) {
            elemParent = this.getParentOfFormElement();
        }

        // Determine if there is an error
        var error = (condition && this.is(':enabled') && this.is(':visible'));

        // Determine if the field is empty
        // In case of dates, the day, month and year have to be filled out completely
        var allFieldsEntered = false, allFieldsDates = false;
        var inputElems = elemParent.find(':input:visible');
        if (elemParent.find('.day, .month, .year').filter(':visible').length > 0) {
            inputElems = inputElems.filter('.day, .month, .year');
            allFieldsDates = true;
            allFieldsEntered = true;
        }

        inputElems.each(function () {
            var val = $(this).val().trim();
            if (allFieldsDates && ((val.length === 0) || (parseInt(val) === 0))) {
                allFieldsEntered = false;
            }
            else if (!allFieldsDates && ((val.length > 0) || (parseInt(val) > 0))) {
                allFieldsEntered = true;
            }
        });

        if (error && !allFieldsEntered) {
            elemParent.addClass('has-an-error has-error-empty');
            elemParent.removeClass('has-error-incorrect');
        }
        else if (error) {
            elemParent.addClass('has-an-error has-error-incorrect');
            elemParent.removeClass('has-error-empty');
        }
        else {
            elemParent.removeClass('has-an-error has-error-empty has-error-incorrect');
        }
    };

    function getBtnNext(all) {
        if (all) {
            var crudTableBtn = $('.crud-table-container:visible').find('.btn-save-new:visible, .btn-save-update:visible');
            if (crudTableBtn.length > 0) {
                return crudTableBtn;
            }

            var modalBtn = $('.modal.in').find('.btn-save-new:visible, .btn-save-update:visible');
            if (modalBtn.length > 0) {
                return modalBtn;
            }
        }
        return $('.btn-next:visible');
    }

    function getMessage(className) {
        return getMessages().find('.' + className);
    }

    function getMessages() {
        var modal = $('.modal.in');
        if (modal.length > 0) {
            // If the modal is a crud table container, then only show messages when the user is editing
            if (!modal.is('.crud-table-container') || (modal.find('.on-edit').is(':visible'))) {
                return modal.find('.modalMessages');
            }
        }
        else {
            return $('.messages');
        }

        return $();
    }

    function addError(isError, className, btnNextClassName, allBtnNext, onError) {
        var showMsg = typeof className === 'string';
        var message = showMsg ? getMessage(className) : null;
        allBtnNext = (allBtnNext === undefined) ? true : allBtnNext;

        if (isError) {
            getBtnNext(allBtnNext).addClass(btnNextClassName);
            if (showMsg) {
                message = onError(getMessages(), message);
                showMessage(message, true);
            }
        }
        else {
            getBtnNext(allBtnNext).removeClass(btnNextClassName);
            if (showMsg) {
                hideMessage(message, true);
            }
        }
    }

    function showMessage(message, isErrorCheck) {
        if (!message.is(':visible')) {
            var hiddenByErrorCheck = message.data('error-check');
            if (isErrorCheck || (!hiddenByErrorCheck && !isErrorCheck)) {
                message.showNoEvent();
                getMessages().showNoEvent();
            }
        }
    }

    function hideMessage(message, isErrorCheck) {
        if (message.is(':visible')) {
            message.data('error-check', isErrorCheck);
            message.hideNoEvent();

            var messages = getMessages();
            var alertBox = messages;
            if (!messages.hasClass('alert')) {
                alertBox = messages.find('.alert');
            }
            if (alertBox.children().filter(':visible').length === 0) {
                messages.hideNoEvent();
            }
        }
    }

    function checkByzElement(elem) {
        var value = elem.val();
        var parent = elem.getParentOfFormElement();
        var byzOnValues = elem.getMultipleDataValues('byz');
        var isOk = (byzOnValues.indexOf(value) < 0);

        if (!isOk && (value.trim().length === 0 || parseInt(value) === 0)) {
            parent.addClass('has-byz-empty');
        }
        else {
            parent.removeClass('has-byz-empty');
        }

        return isOk;
    }

    function checkByzElements(elements) {
        var byzOk = true;
        elements.each(function () {
            if (checkByzElement($(this)) === false) {
                byzOk = false;
            }
        });
        return byzOk;
    }

    function checkByz() {
        addError(!checkByzElements($.getDataElem('byz').filter(':visible')), false, 'byz-required');
    }

    function checkErrors(elem) {
        var elems = [];
        if (elem.is('.required') ||
            elem.is($.getDataElemSelector('min-value')) || elem.is($.getDataElemSelector('max-value'))) {
            elems = elem;
        }
        else {
            elems = elem.find('.required,' + $.getDataElemSelector('min-value') + ',' + $.getDataElemSelector('max-value'));
        }

        if (elems.length > 0) {
            elems.each(function () {
                var elem = $(this);
                var number = elem.getIntegerValue();
                var minValue = elem.getIntegerDataValue('min-value');
                var maxValue = elem.getIntegerDataValue('max-value');

                var errorRequired = elem.is('.required') &&
                    ((elem.val() === undefined) || (elem.val().trim() === '')
                        || (!elem.hasClass('zero-allowed') && (number === 0)));

                var errorMinValue = elem.is($.getDataElemSelector('min-value')) &&
                    (elem.hasClass('strict-min-check') || (number !== -1)) && (isNaN(number) || (number < minValue));

                var errorMaxValue = elem.is($.getDataElemSelector('max-value')) &&
                    (number !== -1) && (isNaN(number) || (number > maxValue));

                elem.hasErrorWhen(errorRequired || errorMinValue || errorMaxValue);
            });

            $.triggerChangeOfState();
        }
    }

    function checkErrorMessages() {
        getMessages().children().each(function () {
            var message = $(this);
            var errorElem = message.data('error-elem');
            if ((errorElem !== null) && (errorElem !== undefined)) {
                if (errorElem.filter(':visible').length === 0) {
                    hideMessage(message, false);
                }
                else {
                    showMessage(message, false);
                }
            }
        });
    }

    function checkNextByzButton() {
        var nextBtn = getBtnNext(true);
        var byzBtn = $('.btn-byz');

        var hasAnError = [];
        var modal = $('.modal.in');
        if (modal.length === 0) {
            hasAnError = $('.has-an-error:visible');
        }
        else {
            hasAnError = modal.find('.has-an-error:visible');
        }

        if (nextBtn.is('[class$=error]') || (hasAnError.length > 0)) {
            nextBtn.attr('disabled', 'disabled');
            byzBtn.attr('disabled', 'disabled');
        }
        else if (nextBtn.hasClass('byz-required') && byzBtn.hasClass('no-byz')) {
            nextBtn.attr('disabled', 'disabled');
            byzBtn.attr('disabled', 'disabled');
        }
        else if (nextBtn.hasClass('byz-required')) {
            nextBtn.attr('disabled', 'disabled');
            byzBtn.removeAttr('disabled');
        }
        else if (byzBtn.hasClass('no-byz')) {
            nextBtn.removeAttr('disabled');
            byzBtn.attr('disabled', 'disabled');
        }
        else {
            nextBtn.removeAttr('disabled');
            byzBtn.removeAttr('disabled');
        }

        if (nextBtn.is(':focus:disabled')) {
            nextBtn.getNextFormElement().focus();
        }
        if (byzBtn.is(':focus:disabled')) {
            byzBtn.getNextFormElement().focus();
        }
    }

    function init(forceRun) {
        if (forceRun || !$.isRunningInit()) {
            if ($.checkByz() && !$.isCorrection()) {
                checkByz();
            }

            checkErrorMessages();
            checkNextByzButton();
        }
    }

    $(document).on('blur', '.form-elem', function (e) {
        var elem = $(e.target);

        checkErrors(elem);
        if (elem.closest('form').length > 0) {
            init();
        }
    }).on('change', '.form-elem', function (e) {
        var self = $(e.target);
        var focus = $(':focus');

        if (!focus.is(self)) {
            checkErrors(self);
        }
    }).on('changeCheckErrors', function (e) {
        checkErrors($(e.target));
    }).on('show', function (e) {
        checkErrors($(e.target));
    }).on('show hide', function (e) {
        if (!$.isRunningInit()) {
            // Prevent a endless loop: call init, show message, 'show' event called, back to call init ...
            var self = $(e.target);
            if (!self.hasClass('.messages') && (self.closest('.messages').length === 0)) {
                init();
            }
        }
    });

    $.triggerChangeOfState = function triggerChangeOfState() {
        if (!$.isRunningInit()) {
            checkErrorMessages();
            checkNextByzButton();
        }
    };

    $.registerInit(function registerInit(elem) {
        checkErrors(elem);
        init(true);
    });
})(jQuery);
