/**
 * Add utility methods to the jQuery object.
 */
(function ($) {
    /**
     * Initialization:
     * Runs registered initialization functions on every page load and every AJAX update.
     * Basically, every time the DOM is changed.
     */

    var initFunctions = [];

    $.registerInit = function (func) {
        initFunctions.push(func);
    };

    var runInit = function (elem) {
        for (var i = 0; i < initFunctions.length; i++) {
            var initFunction = initFunctions[i];
            initFunction(elem);
        }
    };

    $(document).ready(function () {
        runInit($(document));
    });

    $(document).on('ajax-update', function (e, elem) {
        runInit(elem);
    });

    /**
     * Allow table with active row selector which is always visible, even on a scrollable table
     */

    var toggleActiveRow = function (setActive, e) {
        var row = $(e.target).closest('tr');
        var index = row.closest('tbody').find('tr').index(row);

        var parent = row.closest('.fixed-left-column');
        var fixedRow = parent.find('.fixed tbody tr:nth-child(' + (index + 1) + ')');

        var rows = row.add(fixedRow);
        setActive ? rows.addClass('active') : rows.removeClass('active');

        var classNames = 'glyphicon glyphicon-triangle-right';
        var focusIndicator = fixedRow.find('span');
        setActive ? focusIndicator.addClass(classNames) : focusIndicator.removeClass(classNames);
    };

    var selector = '.fixed-left-column .free tbody input, .fixed-left-column .free tbody button';
    $(document).on('focus', selector, function (e) {
        toggleActiveRow(true, e);
    });
    $(document).on('blur', selector, function (e) {
        toggleActiveRow(false, e);
    });

    /**
     * Modal utility methods
     */

    $.getOpenedModal = function () {
        return $('.modal:visible:first');
    };

    $(document).on('shown.bs.modal', function (e) {
        $(e.target)
            .data('focus-element-id', $(':focus').attr('id'))
            .find(':input:first').focus();
    });

    $(document).on('hidden.bs.modal', function (e) {
        var focusElementId = $(e.target).data('focus-element-id');
        $(document.getElementById(focusElementId)).focus();
    });

    /* Without selector */

    $.getAllFormElements = function (elem) {
        if (elem !== undefined) {
            return elem.closest('form, .modal').find('input,button,textarea');
        }
        return $('form:first').find('input,button,textarea');
    };

    $.getFormElements = function (elem) {
        return $.getAllFormElements(elem).filter(':visible');
    };

    $.getFormElementsEnabled = function (elem) {
        return $.getFormElements(elem).filter(':enabled');
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

    $.resetInvisibleFormElements = function () {
        var notVisibleInputElements = $.getAllFormElements()
            .filter(':input')
            .not(':visible')
            .not('[type=hidden]');

        notVisibleInputElements.filter('.integer-field').val(0);
        notVisibleInputElements.not('.integer-field').val('');
    };

    /* With selector */

    $.fn.getPrevFormElement = function () {
        var formElements = $.getFormElementsEnabled(this);

        var curIndex = formElements.index(this);
        var newIndex = (curIndex === 0) ? formElements.length - 1 : curIndex - 1;

        return formElements.eq(newIndex);
    };

    $.fn.getNextFormElement = function () {
        var formElements = $.getFormElementsEnabled(this);

        var curIndex = formElements.index(this);
        var newIndex = (curIndex === (formElements.length - 1)) ? 0 : curIndex + 1;

        return formElements.eq(newIndex);
    };

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
})(jQuery);
