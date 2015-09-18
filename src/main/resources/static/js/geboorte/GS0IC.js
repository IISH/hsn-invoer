(function ($) {
    'use strict';

    $.initCheckDate(
        '.checkDateTime',
        function (hsnDate) {
            $.each([hsnDate.day, hsnDate.month], function (i, elem) {
                if (elem.isInput && (elem.getValue() === 0) && !elem.isEmptyVal()) {
                    elem.elem.val(-1);
                }
            });
        },
        function (hsnDate) {
            var error = false;

            var dayVal = hsnDate.day.getValue();
            var monthVal = hsnDate.month.getValue();
            var yearVal = hsnDate.year.getValue();
            var hourVal = hsnDate.hour.getValue();

            if (hourVal < -3 || hourVal > 23) {
                error = true;
            }
            if (monthVal === 0 || monthVal < -3 || monthVal > 12) {
                error = true;
            }
            if (dayVal === 0 || dayVal < -3 || dayVal > 31) {
                error = true;
            }

            if ((monthVal === 4 || monthVal === 6 || monthVal === 9 || monthVal === 11) && (dayVal === 31)) {
                error = true;
            }
            if (monthVal === 2 && dayVal === 29) {
                error = true;
            }
            if (monthVal === 2 && monthVal === 28 && (yearVal % 4 > 0)) {
                error = true;
            }

            return error;
        }
    );
})(jQuery);