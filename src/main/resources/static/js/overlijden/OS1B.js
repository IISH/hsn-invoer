(function ($) {
    'use strict';

    $.initCheckDate(
        '.checkDateTimeOvl',
        function (hsnDate) {
            if (hsnDate.hour.getValue() === -1) {
                hsnDate.minute.elem.val(-1);
            }
        },
        function (hsnDate) {
            var error = true;

            var dayVal = hsnDate.day.getValue();
            var monthVal = hsnDate.month.getValue();
            var yearVal = hsnDate.year.getValue();
            var hourVal = hsnDate.hour.getValue();
            var minuteVal = hsnDate.minute.getValue();

            if (dayVal > -4 && dayVal < 32 && monthVal > -4 && monthVal < 13) {
                if (dayVal !== 0 && monthVal !== 0) {
                    if (hourVal > -4 && hourVal < 24) {
                        if (minuteVal > -4 && minuteVal < 60) {
                            if (yearVal > 1810) {
                                error = false;
                            }
                        }
                    }
                }
            }

            return error;
        }
    );
})(jQuery);