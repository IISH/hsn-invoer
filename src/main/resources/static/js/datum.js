/**
 * Specifies various common code to validate dates and times.
 */
(function ($) {
    $.createDateSelector = function (parentSelector) {
        var selectors = [];
        $.each({
            day: '.day',
            month: '.month',
            year: '.year',
            hour: '.hour',
            minute: '.minute'
        }, function (name, selector) {
            selectors.push(parentSelector + ' ' + selector);
        });
        return selectors.join(',');
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
        var day = hsnDate.day;
        var dayIsZero = (day.isInput && (day.getValue() === 0));

        $.each(hsnDate, function (name, elem) {
            if (dayIsZero && elem.isInput) {
                elem.elem.val(-1);
            }
        });

        if (dayIsZero) {
            var parent = day.elem.getParentOfFormElement();
            var lastElement = parent.find(':input:enabled:visible:last');
            lastElement.autoNextFocus(false);
        }
    };

    $.fn.getHsnDate = function () {
        function PartOfDate(elem) {
            return {
                elem: elem,
                isInput: elem.is(':input'),
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

        return {
            day: new PartOfDate(this.find('.day')),
            month: new PartOfDate(this.find('.month')),
            year: new PartOfDate(this.find('.year')),
            hour: new PartOfDate(this.find('.hour')),
            minute: new PartOfDate(this.find('.minute'))
        };
    };

    $.initCheckDate = function (selector, prepare, dateCheck) {
        var doCheckDate = function (elem, prepare, dateCheck, runPrepare) {
            var parent = elem.closest(selector);
            var hsnDate = parent.getHsnDate();
            if (runPrepare && $.isFunction(prepare)) {
                prepare(hsnDate, elem);
            }

            var error = dateCheck(hsnDate, elem);
            elem.hasErrorWhen(error, parent);
            $(document).trigger('changeOfState');
        };

        $(document).ready(function () {
            $(selector).each(function () {
                var elem = $(this).find('.day, .month, .year, .minute, .hour').first();
                doCheckDate(elem, prepare, dateCheck, false);
            });
        });

        $(document).on('blur', $.createDateSelector(selector), function (e) {
            if (!$.isRunningInit()) {
                doCheckDate($(e.target), prepare, dateCheck, true);
            }
        });

        $(document).on('show', function (e) {
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
                        doCheckDate(elem, prepare, dateCheck, false);
                    });
                }
            }
        });
    };

    $.initCheckDate('.checkDateDefault', $.prepareDate, $.checkHsnDate);
})(jQuery);