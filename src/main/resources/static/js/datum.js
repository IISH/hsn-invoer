/**
 * Specifies various common code to validate dates and times.
 */
(function ($) {
    'use strict';

    $.createDateSelector = function (parentSelector) {
        return parentSelector + ' .dateInput';
    };

    $.checkHsnDate = function (hsnDate) {
        var error = true;

        var dayVal = hsnDate.day.getValue();
        var monthVal = hsnDate.month.getValue();
        var yearVal = hsnDate.year.getValue();

        if (dayVal !== 0 && dayVal > -4 && dayVal < 32) {
            if (monthVal !== 0 && monthVal > -4 && monthVal < 13) {
                if (yearVal !== 0 && (yearVal < 2025 && yearVal > 1700) || yearVal === -1 || yearVal === -2 || yearVal === -3) {
                    error = false;

                    if ((monthVal === 4 || monthVal === 6 || monthVal === 9 || monthVal === 11) && (dayVal === 31)) {
                        error = true;
                    }
                    if (monthVal === 2 && dayVal > 29) {
                        error = true;
                    }
                    if (monthVal === 2 && dayVal > 28 && (yearVal % 4 > 0)) {
                        error = true;
                    }
                }
            }
        }

        return error;
    };

    $.prepareDate = function (hsnDate) {
        if ($.getCurNavigation().isNext) {
            var day = hsnDate.day;
            var dayIsZero = (day.isInput && (day.getValue() === 0));

            var toUpdate = $();
            $.each(hsnDate, function (name, elem) {
                if (dayIsZero && elem.isInput) {
                    toUpdate = toUpdate.add(elem.elem);
                }
            });
            toUpdate.val(-1);

            if (dayIsZero) {
                var parent = day.elem.getParentOfFormElement();
                var lastElement = parent.find(':input:enabled:visible:last');
                lastElement.autoNextFocus(true);
            }
        }
    };

    $.fn.getHsnDate = function () {
        function PartOfDate(elem) {
            return {
                elem: elem,
                isInput: elem.is('input'),
                getValue: function () {
                    var val = this.isInput ? this.elem.getIntegerValue() : this.elem.getIntegerText();
                    return isNaN(val) ? 0 : val;
                },
                isEmptyVal: function () {
                    var val = this.isInput ? this.elem.getIntegerValue() : this.elem.getIntegerText();
                    return isNaN(val);
                }
            };
        }

        var dateInputs = this.find('.dateInput');
        return {
            day: new PartOfDate(dateInputs.filter('.day')),
            month: new PartOfDate(dateInputs.filter('.month')),
            year: new PartOfDate(dateInputs.filter('.year')),
            hour: new PartOfDate(dateInputs.filter('.hour')),
            minute: new PartOfDate(dateInputs.filter('.minute'))
        };
    };

    $.initCheckDate = function (selector, prepare, dateCheck) {
        var doCheckDate = function (elem, prepare, dateCheck, isInit, parent) {
            parent = (parent) ? parent : elem.closest(selector);

            var hsnDate = parent.getHsnDate();
            if (!isInit && $.isFunction(prepare)) {
                prepare(hsnDate, elem);
            }

            var error = dateCheck(hsnDate, elem);
            elem.hasErrorWhen(error, parent);
        };

        $(document).ready(function () {
            $(selector).each(function () {
                var parent = $(this);
                var elem = parent.find('.dateInput').first();
                doCheckDate(elem, prepare, dateCheck, true, parent);
            });
            $.triggerChangeOfState();
        }).on('blur', $.createDateSelector(selector), function (e) {
            if (!$.isRunningInit()) {
                doCheckDate($(e.target), prepare, dateCheck, false);
                $.triggerChangeOfState();
            }
        }).on('show', function (e) {
            if (!$.isRunningInit()) {
                var elem = $(e.target);
                var elements = [];
                if (elem.is(selector)) {
                    elements = elem;
                }
                else {
                    elements = elem.find(selector);
                }

                if (elements.length > 0) {
                    elements.each(function () {
                        var elem = $(this).find('.day, .month, .year, .minute, .hour').first();
                        doCheckDate(elem, prepare, dateCheck, true);
                    });
                    $.triggerChangeOfState();
                }
            }
        });
    };

    $.initCheckDate('.checkDateDefault', $.prepareDate, $.checkHsnDate);
})(jQuery);