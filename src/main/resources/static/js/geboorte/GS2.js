(function ($) {
    'use strict';

    $.initCheckDate(
        '.checkDateAndTime',
        function (hsnDate, elem) {
            $.prepareDate(hsnDate);

            var yearVal = hsnDate.year.getValue();
            if (yearVal === -1) {
                hsnDate.year.elem.val(elem.getIntegerDataValue('prev-year-val'));
            }
            else {
                elem.data('prev-year-val', yearVal);
            }

            if (hsnDate.hour.getValue() === -1) {
                hsnDate.minute.elem.val(-1);
            }
        },
        function (hsnDate) {
            var error = $.checkHsnDate(hsnDate);

            var yearVal = hsnDate.year.getValue();
            var hourVal = hsnDate.hour.getValue();
            var minuteVal = hsnDate.minute.getValue();

            if (!error) {
                error = true;

                if (hourVal < 25 && minuteVal < 60) {
                    if (yearVal > 1810 && yearVal < 1923) {
                        if (hourVal > -4 && minuteVal > -4) {
                            error = false;
                        }
                    }
                }
            }

            return error;
        }
    );

    var checkBirthBeforeAkteDate = function () {
        var aktedag = $.getDataElem('aktedag').getIntegerDataValue('aktedag');
        var aktemnd = $.getDataElem('aktemnd').getIntegerDataValue('aktemnd');

        var gebdag = $('#gebknd\\.gebdag').getIntegerValue();
        var gebmnd = $('#gebknd\\.gebmnd').getIntegerValue();

        $.setError(
            (gebdag > aktedag) && (gebmnd === aktemnd) && (aktedag > 0),
            'birth-before-akte',
            'Verkeerd dagnummer geboorte!! Later dan dagnummer akte!!'
        );
    };

    var checkAge = function () {
        var self = $('#gebknd\\.lftmr');
        var age = self.getIntegerValue();

        if (age === 0) {
            age = -1;
            self.val(age);
        }

        self.hasErrorWhen(!isNaN(age) && (age < 12 || age > 55) && (age != -1));
        $.triggerChangeOfState();
    };

    $(document).ready(function () {
        $('.checkDateAndTime').each(function () {
            var self = $(this);
            self.data('prev-year-val', self.find('.year').getIntegerValue());
        });

        checkBirthBeforeAkteDate();
        $('#gebknd\\.gebdag, #gebknd\\.gebmnd').blur(checkBirthBeforeAkteDate);

        checkAge();
        $('#gebknd\\.lftmr').blur(checkAge);
    });
})(jQuery);