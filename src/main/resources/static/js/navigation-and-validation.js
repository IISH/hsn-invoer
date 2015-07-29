/**
 * Allows navigation throughout the form in question by using either:
 * - Tab or arrow down or enter to navigate forwards.
 * - Shift tab or arrow up to navigate backwards.
 *
 * Also checks for elements with the data-byz attribute to see if the user should go to the bijzonderheden screen.
 * And disables both the byz button and the next button in case of errors.
 *
 * In addition, it also sets the focus on the fist element of the first found form on the page.
 */
(function ($) {
    /* Validation */

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
        if (elemParent.find('.day, .month, .year').length > 0) {
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
        var crudTableBtn = $('.crud-table-container:visible .btn-save-new, .crud-table-container:visible .btn-save-update');
        if (crudTableBtn.length > 0) {
            return crudTableBtn;
        }

        var modalBtn = $('.modal:visible .btn-save-new, .modal:visible .btn-save-update');
        if (modalBtn.length > 0) {
            return modalBtn;
        }

        return $('.btn-next');
    };

    var getMessage = function (className) {
        return getMessages().find('.' + className);
    };

    var getMessages = function () {
        var modal = $('.modal:visible');
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
        var message = getMessage(className);

        if (isError) {
            getBtnNext().addClass(btnNextClassName);
            message = onError(getMessages(), message);
            showMessage(message, true);
        }
        else {
            getBtnNext().removeClass(btnNextClassName);
            hideMessage(message, true);
        }
    };

    var showMessage = function (message, isErrorCheck) {
        if (!message.is(':visible')) {
            var hiddenByErrorCheck = message.data('error-check');
            if (isErrorCheck || (!hiddenByErrorCheck && !isErrorCheck)) {
                message.show();
                getMessages().show();
            }
        }
    };

    var hideMessage = function (message, isErrorCheck) {
        if (message.is(':visible')) {
            message.data('error-check', isErrorCheck);
            message.hide();

            var messages = getMessages();
            var alertBox = messages;
            if (!messages.hasClass('alert')) {
                alertBox = messages.find('.alert');
            }
            if (alertBox.children().filter(':visible').length === 0) {
                messages.hide();
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
        addError(
            !checkByzElements($.getDataElem('byz').filter(':visible')),
            'byz', 'byz-required',
            function (messages, message) {
                var byzTextElement = $('.byz-text');
                if (byzTextElement.length > 0) {
                    message.text(byzTextElement.text());
                }
                return message;
            }
        );
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

        $(document).trigger('changeOfState');
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
        if ($('.modal:visible').length === 0) {
            hasAnError = $('.has-an-error:visible');
        }
        else {
            hasAnError = $('.modal:visible .has-an-error:visible');
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
    };

    var init = function (forceRun) {
        if (forceRun || !$.isRunningInit()) {
            if (!$.isCorrection()) {
                checkByz();
            }

            checkErrorMessages();
            checkNextByzButton();
        }
    };

    $(document)
        .on('blur', '.required', function (e) {
            checkRequired($(e.target));
        })
        .on('show', function (e) {
            checkRequired($(e.target));
        })
        .on('blur', 'form input, form button', init)
        .on('show hide', function (e) {
            if (!$.isRunningInit()) {
                // Prevent a endless loop: call init, show message, 'show' event called, back to call init ...
                var self = $(e.target);
                if (!self.hasClass('.messages') && (self.closest('.messages').length === 0)) {
                    init();
                }
            }
        })
        .on('changeOfState', function () {
            if (!$.isRunningInit()) {
                checkErrorMessages();
                checkNextByzButton();
            }
        });

    $.registerInit(function (elem) {
        checkRequired(elem);
        init(true);
    });

    /* Navigation */

    $.ifTableNavigation = function (e, body) {
        var self = $(e.target);
        var formElements = $.getFormElementsEnabled();

        // Disable the enter key, unless the focused element is a button
        if ((e.which === 13) && ($.inArray(self[0], formElements.filter('input')) > -1)) {
            if (typeof e.preventDefault === 'function') {
                e.preventDefault();
            }
            return;
        }

        // Navigation is different inside a table: use arrow keys to navigate through the cells
        if (self.closest('table').length > 0) {
            var isUp = (e.which === 38); // Arrow up
            var isDown = (e.which === 40); // Arrow down
            var isLeft = ((e.which === 9) && (e.shiftKey)); // Tab
            var isRight = ((e.which === 9) && (!e.shiftKey)); // Shift + Tab

            if ((isUp || isDown || isLeft || isRight) && ($.inArray(self[0], formElements) > -1)) {
                body(self, isUp, isDown, isLeft, isRight);
            }
        }
    };

    $.ifDefaultNavigation = function (e, body) {
        var self = $(e.target);
        var formElements = $.getFormElementsEnabled();

        // Disable the enter key, unless the focused element is a button
        if ((e.which === 13) && ($.inArray(self[0], formElements.filter('input')) > -1)) {
            if (typeof e.preventDefault === 'function') {
                e.preventDefault();
            }
            return;
        }

        // Navigation is different inside a table
        if (self.closest('table').length === 0) {
            var isNext = (((e.which === 9) && (!e.shiftKey)) || (e.which === 40)); // Tab or arrow down
            var isPrev = (((e.which === 9) && (e.shiftKey)) || (e.which === 38)); // Shift + Tab or arrow up

            if ((isNext || isPrev) && ($.inArray(self[0], formElements) > -1)) {
                body(self, isNext, isPrev);
            }
        }
    };

    $.fn.autoNextFocus = function (performBlur) {
        if (this.length > 0) {
            // Simulate a TAB
            $.determineNextFocusElem({
                target: this[0],
                which: 9,
                shiftKey: false
            }, performBlur);
        }
    };

    $.fn.autoPrevFocus = function (performBlur) {
        if (this.length > 0) {
            // Simulate a Shift + TAB
            $.determineNextFocusElem({
                target: this[0],
                which: 9,
                shiftKey: true
            }, performBlur);
        }
    };

    $.determineNextFocusElem = function (e, performBlur) {
        $.ifTableNavigation(e, function (self, isUp, isDown, isLeft, isRight) {
            if (typeof e.preventDefault === 'function') {
                e.preventDefault();
            }
            if (performBlur || (performBlur === undefined)) {
                self.blur(); // Now perform the blur and all event handlers attached to this event
            }

            // setTimeout can also be used to make IE wait until the blur event has completed
            setTimeout(function () {
                // If the blur caused a focus on a new element, then ignore default navigation
                if ($(':focus').filter(':input').length > 0) {
                    return;
                }

                var focusElem;
                if (isUp || isDown) {
                    var column = self.closest('td');
                    var row = self.closest('tr');

                    var columns = row.find('td');
                    var rows = row.closest('tbody').find('tr');

                    var columnIndex = columns.index(column);
                    var rowIndex = rows.index(row);

                    // The booleans prev and next indicate whether we should leave the table
                    // and thus focus a previous or next element outside the table
                    var prev, next = false;
                    var nextRowIndex = 0;
                    if (isUp) {
                        nextRowIndex = rowIndex - 1;
                        if (nextRowIndex < 0) {
                            prev = true;
                        }
                    }
                    else {
                        nextRowIndex = rowIndex + 1;
                        if (nextRowIndex >= rows.length) {
                            next = true;
                        }
                    }

                    if (!prev && !next) {
                        // Sometimes there is no input element in the new column
                        // Then keep moving to the left till a column with an input element to focus is found
                        var focusElemFound = false;
                        while (!focusElemFound) {
                            var input = rows.eq(nextRowIndex).find('td').eq(columnIndex).find(':input:visible:enabled:first');
                            if (input.length === 1) {
                                focusElem = input;
                                focusElemFound = true;
                            }
                            columnIndex--;
                        }
                    }
                    else {
                        var table = self.closest('table');
                        if (prev) {
                            focusElem = table.find(':input:first').getPrevFormElement();
                        }
                        else {
                            focusElem = table.find(':input:last').getNextFormElement();
                        }
                    }
                }
                else if (isLeft) {
                    focusElem = self.getPrevFormElement();
                }
                else {
                    focusElem = self.getNextFormElement();
                }

                focusElem.focus();
                focusElem.setCaret(0); // Also make sure the caret is set to the start of the input field
            }, 0);
        });

        $.ifDefaultNavigation(e, function (self, isNext, isPrev) {
            if (typeof e.preventDefault === 'function') {
                e.preventDefault();
            }
            if (performBlur || (performBlur === undefined)) {
                self.blur(); // Now perform the blur and all event handlers attached to this event
            }

            // setTimeout can also be used to make IE wait until the blur event has completed
            setTimeout(function () {
                // If the blur caused a focus on a new element, then ignore default navigation
                if ($(':focus').filter(':input').length > 0) {
                    return;
                }

                var focusElem;
                if (isPrev) {
                    focusElem = self.getPrevFormElement();
                }
                else {
                    focusElem = self.getNextFormElement();
                }

                focusElem.focus();
                focusElem.setCaret(0); // Also make sure the caret is set to the start of the input field
            }, 0);
        });
    };

    var autoFocusNext = false;

    var shouldAutoNextFocusElem = function (e) {
        if (e.charCode !== 0) {
            var self = $(e.target);
            var maxLength = parseInt(self.attr('maxlength'));
            autoFocusNext = (!self.getParentOfFormElement().hasClass('noAutoNext') && !isNaN(maxLength) && (self.getCaret() >= (maxLength - 1)));
        }
    };

    var autoNextFocusElem = function (e) {
        if (autoFocusNext) {
            autoFocusNext = false;
            $(e.target).autoNextFocus(true);
        }
    };

    $.registerInit(function () {
        // We want to ensure these are always performed last, so unbind and bind them again
        $(document)
            .off('keydown', $.determineNextFocusElem)
            .on('keydown', $.determineNextFocusElem)
            .off('keyup', autoNextFocusElem)
            .on('keyup', autoNextFocusElem)
            .off('keypress', shouldAutoNextFocusElem)
            .on('keypress', shouldAutoNextFocusElem);
    });

    $('form:first').submit(function () {
        $.resetInvisibleFormElements();
    });

    $(document).ready(function () {
        $.getFormElements().first().focus();
    });
})(jQuery);
