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

        var viewElem = $('#view');
        if (viewElem.length > 0) {
            var hsnCanvas = new HsnCanvas('view', false);
            var image = sessionStorage.getItem('hsnScan');
            if (image !== null) {
                hsnCanvas.loadImage(image);
            }
        }

        // Keep the session alive, call keepalive every minute
        setInterval(function () {
            $.post('/keepalive');
        }, 60000);
    }).on('ajax-update', function (e, elem) {
        runInit(elem);
    });

    /**
     * Allow table with active row selector which is always visible, even on a scrollable table
     */

    var toggleActiveRow = function (elem) {
        var rows, focusIndicator;

        // Find out which row to make active, if there is one...
        var freePart = elem.closest('.free');
        var parent = freePart.closest('.fixed-left-column');
        if (parent.length === 1) {
            var row = elem.closest('tr');
            var index = row.closest('tbody').find('tr').index(row);
            var fixedRow = parent.find('.fixed tbody tr:nth-child(' + (index + 1) + ')');

            rows = row.add(fixedRow);
            rows.addClass('active');

            focusIndicator = fixedRow.find('span');
            focusIndicator.addClass('glyphicon glyphicon-triangle-right');
        }

        // Now de-activate all rows except the active ones
        var fixedLeftColumn = $('.fixed-left-column');
        fixedLeftColumn.find('tr.active').not(rows).removeClass('active');
        fixedLeftColumn.find('.fixed .glyphicon').not(focusIndicator).removeClass('glyphicon glyphicon-triangle-right');
    };

    $(document).on('focus', '.form-elem', function (e) {
        toggleActiveRow($(e.target));
    });

    /**
     * Modal and popover utility methods
     */

    $.fn.setPopover = function (content) {
        var firstClass = 'nav-trigger popover-left form-elem hidden tabindex';
        var lastClass = 'nav-trigger popover-right form-elem hidden tabindex';

        var firstTabIndex = content.find('.form-elem:first').getIntegerAttr('tabindex') - 1;
        var lastTabIndex = content.find('.form-elem:last').getIntegerAttr('tabindex') + 1;

        var popupContent = '<span class="' + firstClass + firstTabIndex + '" tabindex="' + firstTabIndex + '"></span>'
            + content.html()
            + '<span class="' + lastClass + lastTabIndex + '" tabindex="' + lastTabIndex + '"></span>';

        if (this.data('bs.popover') === undefined) {
            this.popover({
                content: popupContent,
                html: true,
                placement: 'bottom',
                trigger: 'manual',
                container: 'body'
            });
        }
        else {
            this.data('bs.popover').options.content = popupContent;
        }

        return this;
    };

    $.getOpenedModal = function () {
        return $('.modal.in').first();
    };

    $(document).on('shown.bs.modal', function (e) {
        if (e.namespace === 'bs.modal') {
            $(e.target)
                .data('focus-element-id', $(':focus').attr('id'))
                .find('.form-elem').filter(':enabled:visible').first().focus();
        }
    }).on('hidden.bs.modal', function (e) {
        if (e.namespace === 'bs.modal') {
            var focusElementId = $(e.target).data('focus-element-id');
            var element = $(document.getElementById(focusElementId));
            if (!element.is(':enabled:visible')) {
                element = element.getPrevFormElement();
            }
            element.focus();
        }
    }).on('show.bs.popover', function (e) {
        if (e.namespace === 'bs.popover') {
            var popoverBackdrop = $('body > .popover-backdrop.in');
            if (popoverBackdrop.length === 0) {
                popoverBackdrop = $('<div class="popover-backdrop in"></div>')
                    .hideNoEvent()
                    .appendTo($('body'));
            }
            popoverBackdrop.fadeIn();
        }
    }).on('shown.bs.popover', function (e) {
        if (e.namespace === 'bs.popover') {
            $('.popover.in').first().trigger('show')
                .find('input').filter(':enabled:visible').first().focus();
        }
    }).on('hide.bs.popover', function (e) {
        if (e.namespace === 'bs.popover') {
            $(this)
                .find('.popover-backdrop.in')
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
        this.find('.form-elem:input')
            .not(':visible')
            .not('[type=hidden]')
            .not('.noResetOnHidden')
            .valNoEvent('');
    };

    $.imageBlobToDataUrl = function (blob, callback) {
        var reader = new FileReader();
        reader.onload = function (evt) {
            callback(evt.target.result);
        };
        reader.readAsDataURL(blob);
    };

    /* TODO: Prevent using timeout in bevolkingsregister in Chrome */

    var useTimeout = true;

    $.noTimeoutWithNav = function () {
        useTimeout = false;
    };

    $.useTimeout = function () {
        return useTimeout;
    };

    var shouldCheckByz = true;

    $.dontCheckByz = function () {
        shouldCheckByz = false;
    };

    $.checkByz = function () {
        return shouldCheckByz;
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
        var isBackdrop = false;
        this.each(function () {
            var elem = $(this);
            if (elem.css('display') === 'none')
                isHidden = true;
            if (elem.hasClass('typeahead'))
                isTypeahead = true;
            if (elem[0].className.indexOf('backdrop') >= 0)
                isBackdrop = true;
        });

        var toReturn = onHide.apply(this, arguments);

        // Only trigger the event if at least one element was not yet hidden and is not typeahead or a backdrop
        if (!isHidden && !isTypeahead && !isBackdrop)
            this.trigger('hide');

        return toReturn;
    };

    $.fn.hideNoEvent = function () {
        return onHide.apply(this, arguments);
    };

    var onShow = $.fn.show;
    $.fn.show = function () {
        // Determine if the elements are all already shown
        var isHidden = false;
        var isTypeahead = false;
        var isBackdrop = false;
        this.each(function () {
            var elem = $(this);
            if (elem.css('display') === 'none')
                isHidden = true;
            if (elem.hasClass('typeahead'))
                isTypeahead = true;
            if (elem[0].className.indexOf('backdrop') >= 0)
                isBackdrop = true;
        });

        var toReturn = onShow.apply(this, arguments);

        // Only trigger the event if at least one element was not yet shown and is not typeahead or a backdrop
        if (isHidden && !isTypeahead && !isBackdrop)
            this.trigger('show');

        return toReturn;
    };

    $.fn.showNoEvent = function () {
        return onShow.apply(this, arguments);
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

    $.fn.valNoEvent = function () {
        return onVal.apply(this, arguments);
    };
})(jQuery);
