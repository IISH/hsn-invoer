/**
 * Allows navigation throughout the form in question by using either:
 * - Tab or arrow down or enter to navigate forwards.
 * - Shift tab or arrow up to navigate backwards.
 *
 * In addition, it also sets the focus on the fist element of the first found form on the page.
 */
(function ($) {
    $.duringNavigation = function (e, onDefaultNavigation, onTableNavigation) {
        var self = $(e.target);
        var formElements = $.getAllFormElements();

        // Disable the enter key, unless the focused element is a button
        if ((e.which === 13) && ($.inArray(self[0], formElements.filter('input')) > -1)) {
            if (typeof e.preventDefault === 'function') {
                e.preventDefault();
            }
            return;
        }

        // Navigation is different inside a table: use arrow keys to navigate through the cells
        if ((onTableNavigation !== undefined) && (self.closest('table').length > 0)) {
            var isUp = (e.which === 38); // Arrow up
            var isDown = (e.which === 40); // Arrow down
            var isLeft = ((e.which === 9) && (e.shiftKey)); // Tab
            var isRight = ((e.which === 9) && (!e.shiftKey)); // Shift + Tab

            if ((isUp || isDown || isLeft || isRight) && ($.inArray(self[0], formElements) > -1)) {
                onTableNavigation(self, isUp, isDown, isLeft, isRight);
            }
        }
        else if (onDefaultNavigation !== undefined) {
            var isNext = (((e.which === 9) && (!e.shiftKey)) || (e.which === 40)); // Tab or arrow down
            var isPrev = (((e.which === 9) && (e.shiftKey)) || (e.which === 38)); // Shift + Tab or arrow up

            if ((isNext || isPrev) && ($.inArray(self[0], formElements) > -1)) {
                onDefaultNavigation(self, isNext, isPrev);
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
        var onNavigation = function (self, determineFocusElem) {
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

                var focusElem = determineFocusElem();
                focusElem.focus();
                focusElem.setCaret(0); // Also make sure the caret is set to the start of the input field
            }, 0);
        };

        $.duringNavigation(e,
            function (self, isNext, isPrev) {
                onNavigation(self, function () {
                    if (isPrev) {
                        return self.getPrevFormElement();
                    }
                    return self.getNextFormElement();
                });
            },
            function (self, isUp, isDown, isLeft, isRight) {
                onNavigation(self, function () {
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
                            while (columnIndex >= 0) {
                                var input = rows
                                    .eq(nextRowIndex)
                                    .find('td')
                                    .eq(columnIndex)
                                    .find(':input:visible:enabled:first');
                                if (input.length === 1) {
                                    return input;
                                }
                                columnIndex--;
                            }
                        }
                        else {
                            var table = self.closest('table');
                            if (prev) {
                                return table.find(':input:first').getPrevFormElement();
                            }
                            return table.find(':input:last').getNextFormElement();
                        }
                    }
                    else if (isLeft) {
                        return self.getPrevFormElement();
                    }
                    return self.getNextFormElement();
                });
            }
        );
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
        $.getAllFormElements().each(function () {
            var self = $(this);
            if (self.is(':enabled:visible')) {
                self.focus();
                return false;
            }
        });
    });
})(jQuery);
