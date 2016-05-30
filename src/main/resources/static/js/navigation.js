/**
 * Allows navigation throughout the form in question by using either:
 * - Tab or arrow down or enter to navigate forwards.
 * - Shift tab or arrow up to navigate backwards.
 *
 * In addition, it also sets the focus on the fist element of the first found form on the page.
 */
(function ($) {
    'use strict';

    var curNav = {
        isNext: false,
        isPrev: false,
        isUp: false,
        isDown: false,
        isLeft: false,
        isRight: false
    };

    $.getCurNavigation = function () {
        return curNav;
    };

    var lockNavigation = false;
    
    $.lockNavigation = function () {
        lockNavigation = true; 
    };

    $.unlockNavigation = function () {
        lockNavigation = false;
    };

    $.duringNavigation = function (e, onDefaultNavigation, onTableNavigation) {
        var self = $(e.target);

        // Disable the enter key, unless the focused element is a button
        if ((e.which === 13) && !self.is('button')) {
            if (typeof e.preventDefault === 'function') {
                e.preventDefault();
            }
            return;
        }

        // Also disable navigation in case the ALT key was pressed
        if (e.altKey) {
            return;
        }

        // Navigation is different inside a table: use arrow keys to navigate through the cells
        if ((onTableNavigation !== undefined) && (self.closest('table').length > 0)) {
            var isUp = (e.which === 38); // Arrow up
            var isDown = (e.which === 40); // Arrow down
            var isLeft = ((e.which === 9) && (e.shiftKey)); // Tab
            var isRight = ((e.which === 9) && (!e.shiftKey)); // Shift + Tab

            if (isUp || isDown || isLeft || isRight) {
                curNav.isUp = isUp;
                curNav.isDown = isDown;
                curNav.isLeft = isLeft;
                curNav.isRight = isRight;
                curNav.isPrev = isLeft;
                curNav.isNext = isRight;

                onTableNavigation(self, isUp, isDown, isLeft, isRight);
            }
        }
        else if (onDefaultNavigation !== undefined) {
            var isNext = ((e.which === 9) && (!e.shiftKey)); // Tab
            var isPrev = ((e.which === 9) && (e.shiftKey)); // Shift + Tab

            if (isNext || isPrev) {
                curNav.isPrev = isPrev;
                curNav.isNext = isNext;

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

            var afterBlur = function () {
                // If the blur caused a focus on a new element, then ignore default navigation
                if ($(':focus').filter(':input').not(self).length > 0) {
                    return;
                }
                
                // If navigation is locked, then return as well
                if (lockNavigation) {
                    return;
                }

                var focusElem = determineFocusElem();
                if (focusElem.hasClass('nav-trigger')) {
                    var popover = self.closest('.popover');
                    if (popover.length === 0) {
                        $('.popover.in').first().find('input').filter(':enabled:visible').first().focus();
                        return;
                    }
                    else {
                        focusElem.trigger('nav-trigger', [self, popover]);
                    }
                }
                else {
                    focusElem.focus();
                    focusElem.setCaret(0); // Also make sure the caret is set to the start of the input field
                }

                // Reset current navigation
                $.each(curNav, function (k, v) {
                    curNav[k] = false;
                });
            };

            // setTimeout can also be used to make IE wait until the blur event has completed
            if ($.useTimeout()) {
                setTimeout(afterBlur, 0);
            }
            else {
                afterBlur();
            }
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
                    var focusElem = null;
                    var table = self.closest('table');

                    if (isUp || isDown) {
                        var column = self.closest('td');
                        var row = column.closest('tr');

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
                                    .find('.form-elem').filter(':enabled:visible').first();
                                if (input.length === 1) {
                                    return input;
                                }
                                columnIndex--;
                            }
                        }
                        else {
                            if (prev) {
                                focusElem = table.find('.form-elem:first').getPrevFormElement(self);
                            }
                            else {
                                focusElem = table.find('.form-elem:last').getNextFormElement(self);
                            }
                        }
                    }
                    else if (isLeft) {
                        focusElem = self.getPrevFormElement();
                    }
                    else {
                        focusElem = self.getNextFormElement();
                    }

                    if (focusElem.closest('table').length === 0) {
                        table.closest('.scrollable').scrollLeft(0);
                    }

                    return focusElem;
                });
            }
        );
    };

    var autoFocusNext = false;

    var shouldAutoNextFocusElem = function (e) {
        if (e.charCode !== 0) {
            var self = $(e.target);
            var maxLength = self.getIntegerAttr('maxlength');
            autoFocusNext = (!self.getParentOfFormElement().hasClass('noAutoNext') && !isNaN(maxLength) && (self.getCaret() >= (maxLength - 1)));
        }
    };

    var autoNextFocusElem = function (e) {
        if (autoFocusNext) {
            autoFocusNext = false;
            $(e.target).autoNextFocus(true);
        }
    };

    var initTabIndexes = function () {
        var i = 0;
        $('input,button,textarea,.nav-trigger').each(function () {
            var elem = $(this);
            var curTabIndex = elem.getIntegerAttr('tabindex');
            elem
                .removeClass('tabindex' + curTabIndex)
                .attr('tabindex', i)
                .addClass('form-elem tabindex' + i);
            i++;
        });
    };

    var getNewFormElement = function (elem, originalSrc, isPrev) {
        var order = (isPrev) ? -1 : 1;

        var newElement = null;
        var curElement = elem;

        var popover = $('.popover.in').first();
        var modal = $('.modal.in').first();
        var form = $('form:visible').first();

        var parent = null;
        if (popover.length > 0) {
            parent = popover;
        }
        else if (modal.length > 0) {
            parent = modal;
        }
        else {
            parent = form;
        }

        var allFormElems = parent.find('.form-elem');
        var nextTabIndex = elem.getIntegerAttr('tabindex') + order;

        while (newElement === null) {
            curElement = allFormElems.filter('.tabindex' + nextTabIndex);
            if (curElement.length > 0) {
                if (curElement.is(elem)) {
                    newElement = originalSrc;
                }
                else if ($.isFormElement(curElement) || curElement.hasClass('nav-trigger')) {
                    newElement = curElement;
                }
                else {
                    nextTabIndex = nextTabIndex + order;
                }
            }
            else {
                var firstOrLast = (isPrev) ? ':last' : ':first';
                nextTabIndex = parent.find('.form-elem' + firstOrLast).getIntegerAttr('tabindex');
            }
        }

        return newElement;
    };

    $.fn.getPrevFormElement = function (originalSrc) {
        originalSrc = (originalSrc === undefined) ? this : originalSrc;
        return getNewFormElement(this, originalSrc, true);
    };

    $.fn.getNextFormElement = function (originalSrc) {
        originalSrc = (originalSrc === undefined) ? this : originalSrc;
        return getNewFormElement(this, originalSrc, false);
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

        initTabIndexes();
    });

    $('form:not(.noResetOnSubmit)').submit(function () {
        $(document).resetInvisibleFormElements();
    });

    $(document).ready(function () {
        var focusElem = $('.focusOnReady:first');
        if (focusElem.length > 0) {
            focusElem.focus();
        }
        else {
            $('.form-elem:enabled:visible:first').focus();
        }
    });
})(jQuery);
