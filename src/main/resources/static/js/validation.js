/**
 * Checks for elements with the data-byz attribute to see if the user should go to the bijzonderheden screen.
 * And disables both the byz button and the next button in case of errors.
 */
(function ($) {
    'use strict';

    $.setError = function (isError, name, errorMessage, elemParent) {
        if (name.indexOf('.') >= 0) {
            name = name.substring(0, name.indexOf('.'));
        }

        addError(isError, name, name + '-error', function (messages, message) {
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

    $.setErrorWithClass = function (isError, name, elem, errorMessage) {
        addError(isError, null, name + '-error');
        isError ? elem.text(errorMessage).show() : elem.hide();
    };

    $.fn.hasErrorWhen = function (condition, elemParent) {
        if (elemParent === undefined || elemParent === null) {
            elemParent = this.getParentOfFormElement();
        }

        // Determine if there is an error
        var error = (condition && this.is(':enabled') && this.is(':visible'));

        // Determine if the field is empty
        // In case of dates, the day, month and year have to be filled out completely
        var allFieldsEntered = true;
        var inputElems = elemParent.find(':input:visible');
        if (elemParent.find('.day, .month, .year').filter(':visible').length > 0) {
            inputElems = inputElems.filter('.day, .month, .year');
        }

        inputElems.each(function () {
            var val = $(this).val().trim();
            if ((val.length === 0) || (parseInt(val) === 0)) {
                allFieldsEntered = false;
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

    var getBtnNext = function () {
        var crudTableBtn = $('.crud-table-container:visible').find('.btn-save-new, .btn-save-update');
        if (crudTableBtn.length > 0) {
            return crudTableBtn;
        }

        var modalBtn = $('.modal.in').find('.btn-save-new, .btn-save-update');
        if (modalBtn.length > 0) {
            return modalBtn;
        }

        return $('.btn-next');
    };

    var getMessage = function (className) {
        return getMessages().find('.' + className);
    };

    var getMessages = function () {
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
    };

    var addError = function (isError, className, btnNextClassName, onError) {
        var showMsg = typeof className === 'string';
        var message = showMsg ? getMessage(className) : null;

        if (isError) {
            getBtnNext().addClass(btnNextClassName);
            if (showMsg) {
                message = onError(getMessages(), message);
                showMessage(message, true);
            }
        }
        else {
            getBtnNext().removeClass(btnNextClassName);
            if (showMsg) {
                hideMessage(message, true);
            }
        }
    };

    var showMessage = function (message, isErrorCheck) {
        if (!message.is(':visible')) {
            var hiddenByErrorCheck = message.data('error-check');
            if (isErrorCheck || (!hiddenByErrorCheck && !isErrorCheck)) {
                message.showNoEvent();
                getMessages().showNoEvent();
            }
        }
    };

    var hideMessage = function (message, isErrorCheck) {
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
    };

    var checkByzElement = function (elem) {
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
    };

    var checkByzElements = function (elements) {
        var byzOk = true;
        elements.each(function () {
            if (checkByzElement($(this)) === false) {
                byzOk = false;
            }
        });
        return byzOk;
    };

    var checkByz = function () {
        addError(!checkByzElements($.getDataElem('byz').filter(':visible')), false, 'byz-required');
    };

    var checkRequired = function (elem) {
        var elems;
        if (elem.is('.required')) {
            elems = elem;
        }
        else {
            elems = elem.find('.required');
        }

        elems.each(function () {
            var elem = $(this);
            elem.hasErrorWhen(
                (elem.val() === undefined) || (elem.val().trim() === '') ||
                (!elem.hasClass('zero-allowed') && (elem.getIntegerValue() === 0))
            );
        });

        $.triggerChangeOfState();
    };

    var checkErrorMessages = function () {
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
    };

    var checkNextByzButton = function () {
        var nextBtn = getBtnNext();
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
    };

    var init = function (forceRun) {
        if (forceRun || !$.isRunningInit()) {
            if ($.checkByz() && !$.isCorrection()) {
                checkByz();
            }

            checkErrorMessages();
            checkNextByzButton();
        }
    };

    $(document).on('blur', '.form-elem', function (e) {
        var elem = $(e.target);

        if (elem.hasClass('required')) {
            checkRequired(elem);
        }

        if (elem.closest('form').length > 0) {
            init();
        }
    }).on('change', '.required', function (e) {
        var self = $(e.target);
        var focus = $(':focus');

        if (!focus.is(self)) {
            checkRequired(self);
        }
    }).on('show', function (e) {
        checkRequired($(e.target));
    }).on('show hide', function (e) {
        if (!$.isRunningInit()) {
            // Prevent a endless loop: call init, show message, 'show' event called, back to call init ...
            var self = $(e.target);
            if (!self.hasClass('.messages') && (self.closest('.messages').length === 0)) {
                init();
            }
        }
    });

    $.triggerChangeOfState = function () {
        if (!$.isRunningInit()) {
            checkErrorMessages();
            checkNextByzButton();
        }
    };

    $.registerInit(function (elem) {
        checkRequired(elem);
        init(true);
    });
})(jQuery);
