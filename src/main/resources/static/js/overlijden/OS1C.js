(function ($) {
    $.initCheckDate(
        '.checkDateTimeOvl',
        function (hsnDate) {
            $.prepareDate(hsnDate);

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

            if (dayVal < 32) {
                if (monthVal < 13) {
                    if ((yearVal < 2025 && yearVal > 1700) || yearVal === -1 || yearVal === -2 || yearVal === -3) {
                        error = false;
                        if ((monthVal === 4 || monthVal === 6 || monthVal === 9 || monthVal === 11)
                            && (dayVal === 31)) {
                            error = true;
                        }
                        if (monthVal === 2 && dayVal > 28 && yearVal % 4 > 0) {
                            error = true;
                        }
                    }
                }
            }

            if (dayVal === 0 || monthVal === 0) {
                error = true;
            }
            if (hourVal > 23 || minuteVal > 59 || hourVal < -3 || minuteVal < -3) {
                error = true;
            }

            return error;
        }
    );

    var checkDeathAfterAkteDate = function () {
        var aktedag = $.getDataElem('aktedag').getIntegerDataValue('aktedag');
        var aktemnd = $.getDataElem('aktemnd').getIntegerDataValue('aktemnd');
        var aktejr = $.getDataElem('aktejr').getIntegerDataValue('aktejr');

        var ovldag = $('#ovlknd\\.ovldag').getIntegerValue();
        var ovlmnd = $('#ovlknd\\.ovlmnd').getIntegerValue();
        var ovljr = $('#ovlknd\\.ovljr').getIntegerValue();

        var error = false;
        if (!isNaN(ovldag) && (ovldag !== 0) && !isNaN(ovlmnd) && (ovlmnd !== 0) && !isNaN(ovljr) && (ovljr !== 0)) {
            error = ((aktejr < ovljr) ||
                ((aktemnd < ovlmnd) && (aktejr === ovljr)) ||
                ((aktejr === ovljr) && (aktemnd === ovlmnd) && (aktedag < ovldag)))
        }

        $.setError(
            error,
            'dagtekening',
            'Dagtekening AKTE is eerder in de tijd dan dagtekening OVERLIJDEN! ' +
            'Corrigeer of stop met deze akte!'
        );
    };

    var calculateAge = function () {
        var ovlDay = $('#ovlknd\\.ovldag').getIntegerValue();
        var ovlMonth = $('#ovlknd\\.ovlmnd').getIntegerValue();
        var ovlYear = $('#ovlknd\\.ovljr').getIntegerValue();

        if ((ovlDay !== -1) && (ovlMonth !== -1) && (ovlYear !== -1)) {
            var gebDay = $.getDataElem('geb-day').getIntegerDataValue('geb-day');
            var gebMonth = $.getDataElem('geb-month').getIntegerDataValue('geb-month');
            var gebYear = $.getDataElem('geb-year').getIntegerDataValue('geb-year');

            var gebDays = ((gebYear - 1800) * 366) + (gebMonth * 30) + gebDay;
            var ovlDays = ((ovlYear - 1800) * 366) + (ovlMonth * 30) + ovlDay;

            var ageDays = ovlDays - gebDays;

            var ageYears = Math.floor(ageDays / 366);
            ageDays = ageDays - (ageYears * 366);

            var ageMonths = Math.floor(ageDays / 30);
            ageDays = ageDays - (ageMonths * 30);

            var ageWeeks = Math.floor(ageDays / 7);
            ageDays = ageDays - (ageWeeks * 7);

            $('#ovlknd\\.lftdovl').val(ageDays);
            $('#ovlknd\\.lftwovl').val(ageWeeks);
            $('#ovlknd\\.lftmovl').val(ageMonths);
            $('#ovlknd\\.lftjovl').val(ageYears);
        }
    };

    var showAge = function () {
        if (($('#ovlknd\\.lftdovl').getIntegerValue() === 0) &&
            ($('#ovlknd\\.lftwovl').getIntegerValue() === 0) &&
            ($('#ovlknd\\.lftmovl').getIntegerValue() === 0) &&
            ($('#ovlknd\\.lftjovl').getIntegerValue() === 0)) {

            $('.age-manual').show();
        }
        else {
            $('.age-manual').hide();
        }
    };

    $(document).ready(function () {
        checkDeathAfterAkteDate();
        $('#ovlknd\\.ovldag, #ovlknd\\.ovlmnd, #ovlknd\\.ovljr, #ovlknd\\.ovluur, #ovlknd\\.ovlmin')
            .blur(checkDeathAfterAkteDate);

        $('#ovlknd\\.ovlmin').blur(function () {
            if (!$(this).closest('.form-group').hasClass('has-an-error')) {
                $('.ageContainer').show();
            }
        });

        var ageInput = $('.age input');
        if (!$.isCorrection()) {
            var calculatedAge = false;
            ageInput.focus(function () {
                if (!calculatedAge) {
                    calculateAge();
                    showAge();
                    calculatedAge = true;
                }
            });
        }

        showAge();
        ageInput.blur(showAge);
    });
})(jQuery);