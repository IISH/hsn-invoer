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
        var dayIsZero = ((day.getValue() === 0) && !day.isEmptyVal());
        $.each(hsnDate, function (name, elem) {
            if (day.isInput && dayIsZero && elem.isInput) {
                elem.elem.val(-1);
            }
        });
    };

    $.fn.getHsnDate = function () {
        function PartOfDate(elem) {
            var obj = {
                elem: elem,
                isInput: elem.is(':input'),
                getValue: function () {
                    var val = this.isInput ? this.elem.getIntegerValue() : this.elem.getIntegerText();
                    return isNaN(val) ? 0 : val;
                }
            };

            /*if (obj.isInput && isNaN(obj.getValue())) {
             obj.elem.val(0);
             }*/
            return obj;
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

            // Generate an random id as an identifier for error messages
            var id = parent.data('random-id');
            if (id === undefined || id === null) {
                id = Math.floor(Math.random() * 999999).toString();
                parent.data('random-id', id);
            }

            var message = 'De datum ' + hsnDate.day.getValue() + '-' +
                hsnDate.month.getValue() + '-' + hsnDate.year.getValue() + ' ';
            if ((hsnDate.hour.elem.length > 0) && (hsnDate.minute.elem.length > 0)) {
                message += 'en/of de tijd ' + hsnDate.hour.getValue() + ':' + hsnDate.minute.getValue() + ' ';
            }
            else if ((hsnDate.hour.elem.length > 0) && (hsnDate.minute.elem.length === 0)) {
                message += 'en/of het uur ' + hsnDate.hour.getValue() + ' ';
            }
            message += 'is niet geldig.';

            elem.hasErrorWhen(dateCheck(hsnDate, elem), parent, id, message);
            $(document).trigger('changeOfState');
        };

        $(document).ready(function () {
            $(selector).each(function () {
                var elem = $(this).find('.day, .month, .year, .minute, .hour').first();
                doCheckDate(elem, prepare, dateCheck, false);
            });
        });

        $(document).on('blur', $.createDateSelector(selector), function (e) {
            doCheckDate($(e.target), prepare, dateCheck, true);
        });
    };

    $.initCheckDate('.checkDateDefault', $.prepareDate, $.checkHsnDate);
})(jQuery);