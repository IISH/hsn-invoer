/**
 * Add utility methods to the jQuery object.
 */
(function ($) {
    'use strict';

    /**
     * Initialization:
     * Runs registered initialization functions on every page load and every AJAX update.
     * Basically, every time the DOM is changed.
     */

    var runningInit = true;
    var initFunctions = [];

    $.registerInit = function (func) {
        initFunctions.push(func);
    };

    $.isRunningInit = function () {
        return runningInit;
    };

    var runInit = function (elem) {
        runningInit = true;
        for (var i = 0; i < initFunctions.length; i++) {
            var initFunction = initFunctions[i];
            initFunction(elem);
        }
        runningInit = false;
    };

    $(document).ready(function () {
        runInit($(document));

        // Keep the session alive, call keepalive every minute
        setInterval(function () {
            $.post('/keepalive');
        }, 60000);
    });

    $(document).on('ajax-update', function (e, elem) {
        runInit(elem);
    });

    /**
     * Allow table with active row selector which is always visible, even on a scrollable table
     */

    var toggleActiveRow = function (elem) {
        var rows, focusIndicator;

        // Find out which row to make active, if there is one...
        if (elem.is('.fixed-left-column .free tbody input, .fixed-left-column .free tbody button')) {
            var row = elem.closest('tr');
            var index = row.closest('tbody').find('tr').index(row);

            var parent = row.closest('.fixed-left-column');
            var fixedRow = parent.find('.fixed tbody tr:nth-child(' + (index + 1) + ')');

            rows = row.add(fixedRow);
            rows.addClass('active');

            focusIndicator = fixedRow.find('span');
            focusIndicator.addClass('glyphicon glyphicon-triangle-right');
        }

        // Now de-activate all rows except the active ones
        $('.fixed-left-column tr.active').not(rows).removeClass('active');
        $('.fixed-left-column .fixed .glyphicon').not(focusIndicator).removeClass('glyphicon glyphicon-triangle-right');
    };

    $(document).on('focus', ':input', function (e) {
        toggleActiveRow($(e.target));
    });

    /**
     * Modal and popover utility methods
     */

    $.getOpenedModal = function () {
        return $('.modal:visible:first');
    };

    $.fn.initPopoverContent = function (content) {
        var firstTabIndex = content.find('.form-elem:first').getIntegerAttr('tabindex') - 1;
        var lastTabIndex = content.find('.form-elem:last').getIntegerAttr('tabindex') + 1;
        this.data('bs.popover').options.content =
            '<span class="nav-trigger popover-left form-elem hidden tabindex' + firstTabIndex + '" tabindex="' + firstTabIndex + '"></span>' +
            content.html() +
            '<span class="nav-trigger popover-right form-elem hidden tabindex' + lastTabIndex + '" tabindex="' + lastTabIndex + '"></span>';
    };

    $(document).on('shown.bs.modal', function (e) {
        if (e.namespace === 'bs.modal') {
            $(e.target)
                .data('focus-element-id', $(':focus').attr('id'))
                .find(':input:enabled:visible:first')
                .focus();
        }
    });

    $(document).on('shown.bs.popover', function (e) {
        if (e.namespace === 'bs.popover') {
            $('.popover:visible:first')
                .trigger('show')
                .find(':input:enabled:visible:first')
                .focus();
        }
    });

    $(document).on('hidden.bs.modal', function (e) {
        if (e.namespace === 'bs.modal') {
            var focusElementId = $(e.target).data('focus-element-id');
            var element = $(document.getElementById(focusElementId));
            if (!element.is(':enabled:visible')) {
                element = element.getPrevFormElement();
            }
            element.focus();
        }
    });

    $(document).on('show.bs.popover', function (e) {
        if (e.namespace === 'bs.popover') {
            var modalBackdrop = $('body > .modal-backdrop.in');
            if (modalBackdrop.length === 0) {
                modalBackdrop = $('<div class="modal-backdrop in"></div>')
                    .hide()
                    .prependTo($(this).find('body'));
            }
            modalBackdrop.fadeIn();
        }
    });

    $(document).on('hide.bs.popover', function (e) {
        if (e.namespace === 'bs.popover') {
            $(this)
                .find('.modal-backdrop.in')
                .fadeOut(400, function () {
                    $(this).remove();
                });
        }
    });

    /* Without selector */

    $.isFormElement = function (elem) {
        return ((elem !== undefined) && elem.hasClass('form-elem') && elem.is(':enabled:visible'));
    };

    $.getDataElemSelector = function (dataName) {
        return '[data-' + dataName + ']';
    };

    $.getDataElem = function (dataName) {
        return $($.getDataElemSelector(dataName));
    };

    $.isCorrection = function () {
        return $.getDataElem('is-correction').data('is-correction');
    };

    $.fn.resetInvisibleFormElements = function () {
        var notVisibleInputElements = this
            .find('.form-elem:input')
            .not(':visible')
            .not('[type=hidden]');
        notVisibleInputElements.filter('.integer-field').val(0);
        notVisibleInputElements.not('.integer-field').val('');
    };

    /* TODO: Prevent using timeout in bevolkingsregister in Chrome */

    var useTimeout = true;

    $.noTimeoutWithNav = function () {
        useTimeout = false;
    };

    $.useTimeout = function () {
        return useTimeout;
    };

    /* With selector */

    $.fn.getParentOfFormElement = function () {
        return this.closest('td, .form-group, .sub-form-group');
    };

    $.fn.getDataValue = function (dataName) {
        var data = this.data(dataName);
        if (data !== undefined) {
            return data.toString();
        }
        return undefined;
    };

    $.fn.getIntegerDataValue = function (dataName) {
        var data = this.getDataValue(dataName);
        if (data !== undefined) {
            return parseInt(data);
        }
        return undefined;
    };

    $.fn.getMultipleDataValues = function (dataName) {
        var data = this.getDataValue(dataName);
        if (data !== undefined) {
            return data.split(';');
        }
        return undefined;
    };

    $.fn.getDataValueAsElem = function (dataName) {
        return $(this.getDataValue(dataName));
    };

    $.fn.getIntegerValue = function () {
        return parseInt(this.val());
    };

    $.fn.getIntegerText = function () {
        return parseInt(this.text());
    };

    $.fn.getIntegerAttr = function (attr) {
        return parseInt(this.attr(attr));
    };

    $.fn.setValue = function (val) {
        if (this.is(':input')) {
            this.val(val);
        }
        else {
            this.text(val);
        }
    };

    $.fn.getCaret = function () {
        if ((this.length > 0) && (typeof this[0].selectionStart !== 'undefined')) {
            return this[0].selectionStart;
        }
        return 0;
    };

    $.fn.setCaret = function (caretPos) {
        if ((this.length > 0) && (typeof this[0].setSelectionRange !== 'undefined')) {
            this[0].focus();
            this[0].setSelectionRange(caretPos, caretPos);
        }
    };

    /* Overwrite 'show' / 'hide' jQuery functions to send event */

    var onHide = $.fn.hide;
    $.fn.hide = function () {
        // Determine if the elements are all already hidden
        var isHidden = true;
        var isTypeahead = false;
        this.each(function () {
            if ($(this).css('display') !== 'none')
                isHidden = false;
            if ($(this).hasClass('typeahead'))
                isTypeahead = true;
        });

        var toReturn = onHide.apply(this, arguments);

        // Only trigger the event if at least one element was not yet hidden and is not typeahead
        if (!isHidden && !isTypeahead)
            this.trigger('hide');

        return toReturn;
    };

    var onShow = $.fn.show;
    $.fn.show = function () {
        // Determine if the elements are all already shown
        var isHidden = false;
        var isTypeahead = false;
        this.each(function () {
            if ($(this).css('display') === 'none')
                isHidden = true;
            if ($(this).hasClass('typeahead'))
                isTypeahead = true;
        });

        var toReturn = onShow.apply(this, arguments);

        // Only trigger the event if at least one element was not yet shown and is not typeahead
        if (isHidden && !isTypeahead)
            this.trigger('show');

        return toReturn;
    };

    /* Overwrite 'val' jQuery function to send 'change' event */
    var onVal = $.fn.val;
    $.fn.val = function () {
        var changeValue = (arguments.length > 0);
        if (changeValue) {
            var valueBefore = onVal.apply(this);
        }
        var result = onVal.apply(this, arguments);
        if (changeValue) {
            var valueAfter = onVal.apply(this);
            if (valueBefore !== valueAfter) this.change();
        }
        return result;
    };
})(jQuery);
