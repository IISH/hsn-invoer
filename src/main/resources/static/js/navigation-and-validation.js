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
    $.setError = function (isError, name, errorMessage) {
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
            }

            message.html(errorMessage);
            return message;
        });
    };

    $.fn.hasErrorWhen = function (condition, elemParent) {
        if (elemParent === undefined) {
            elemParent = this.getParentOfFormElement();
        }

        if (condition && this.is(':enabled')) {
            elemParent.addClass('has-an-error');
        }
        else {
            elemParent.removeClass('has-an-error');
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
        var messages = getMessages();
        var message = getMessage(className);

        if (isError) {
            getBtnNext().addClass(btnNextClassName);

            message = onError(messages, message);

            message.show();
            messages.show();
        }
        else {
            getBtnNext().removeClass(btnNextClassName);

            message.hide();

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
        var byzOnValues = elem.getMultipleDataValues('byz');
        return (byzOnValues.indexOf(elem.val()) < 0);
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

    var checkRequired = function () {
        $('.required:visible').each(function () {
            var elem = $(this);
            elem.hasErrorWhen(
                (elem.val() === undefined) || (elem.val().trim() === '') ||
                (!elem.hasClass('zero-allowed') && (elem.getIntegerValue() === 0))
            );
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

    $(document).keydown(function (e) {
        var self = $(e.target);
        var formElements = $.getFormElementsEnabled();

        // Disable the enter key, unless the focused element is a button
        if ((e.which === 13) && ($.inArray(self[0], formElements.filter('input')) > -1)) {
            e.preventDefault();
            return;
        }

        // Navigation is different inside a table: use arrow keys to navigate through the cells
        if (self.closest('table').length > 0) {
            var isUp = (e.which === 38); // Arrow up
            var isDown = (e.which === 40); // Arrow down
            var isLeft = ((e.which === 9) && (e.shiftKey)); // Tab
            var isRight = ((e.which === 9) && (!e.shiftKey)); // Shift + Tab

            if ((isUp || isDown || isLeft || isRight) && ($.inArray(self[0], formElements) > -1)) {
                e.preventDefault();
                self.blur(); // Now perform the blur and all event handlers attached to this event

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
                                input.focus();
                                focusElemFound = true;
                            }
                            columnIndex--;
                        }
                    }
                    else {
                        var table = self.closest('table');
                        if (prev) {
                            table.find(':input:first').getPrevFormElement().focus();
                        }
                        else {
                            table.find(':input:last').getNextFormElement().focus();
                        }
                    }
                }
                else if (isLeft) {
                    self.getPrevFormElement().focus();
                }
                else {
                    self.getNextFormElement().focus();
                }
            }
        }
        // Otherwise we only know about up/down (previous/next input element)
        else {
            var isNext = (((e.which === 9) && (!e.shiftKey)) || (e.which === 40)); // Tab or arrow down
            var isPrev = (((e.which === 9) && (e.shiftKey)) || (e.which === 38)); // Shift + Tab or arrow up

            if ((isNext || isPrev) && ($.inArray(self[0], formElements) > -1)) {
                e.preventDefault();
                self.blur(); // Now perform the blur and all event handlers attached to this event

                if (isPrev) {
                    self.getPrevFormElement().focus();
                }
                else {
                    self.getNextFormElement().focus();
                }
            }
        }
    });

    var init = function () {
        if (!$.isCorrection()) {
            checkByz();
        }
        checkRequired();
        checkNextByzButton();
    };

    $.registerInit(init);
    $(document).on('blur', 'form input, form button', init);
    $(document).on('changeOfState', checkNextByzButton);

    $('form:first').submit(function () {
        $.resetInvisibleFormElements();
    });

    $(document).ready(function () {
        $.getFormElements().first().focus();
    });
})(jQuery);
