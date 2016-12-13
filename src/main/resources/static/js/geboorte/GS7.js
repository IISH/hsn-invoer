(function ($) {
    'use strict';

    $.initCheckDate(
        '.checkDate',
        function (hsnDate, elem) {
            var parent = elem.getParentOfFormElement();
            if (!parent.hasClass('kantmeldingDateCheck')) {
                $.prepareDate(hsnDate, elem);
            }
        },
        function (hsnDate, elem) {
            var error = false;
            var parent = elem.getParentOfFormElement();

            var dayVal = hsnDate.day.getValue();
            var monthVal = hsnDate.month.getValue();
            var yearVal = hsnDate.year.getValue();

            if (!parent.hasClass('kantmeldingDateCheck')) {
                error = $.checkHsnDate(hsnDate, elem);
            }

            if (dayVal >= 32 || monthVal >= 13) {
                error = true;
            }
            if (dayVal === 0 || monthVal === 0) {
                error = true;
            }
            if (yearVal <= 1810) {
                if (!parent.hasClass('checkDate7a') || !(yearVal === -1 || yearVal === -2 || yearVal === -3)) {
                    error = true;
                }
            }

            return error;
        }
    );

    var checkNoDate = function (elem) {
        var hsnDate = elem.getHsnDate();

        var parent = elem.closest('.form-group');
        var prevFormGroup = parent.prev('.form-group');
        if (prevFormGroup.length === 0) {
            prevFormGroup = parent.parent().prev('.form-group');
        }
        var control = prevFormGroup.find(':input');

        var onBlur = function () {
            var dayVal = hsnDate.day.getValue();
            var monthVal = hsnDate.month.getValue();
            var yearVal = hsnDate.year.getValue();

            if (dayVal === -1 && monthVal === -1 && yearVal === -1) {
                control.val('n');
            }
        };

        hsnDate.day.elem.blur(onBlur);
        hsnDate.month.elem.blur(onBlur);
        hsnDate.year.elem.blur(onBlur);

        onBlur();
    };

    $(document).ready(function () {
        $('.checkDate').each(function () {
            checkNoDate($(this));
        });
    });
})(jQuery);