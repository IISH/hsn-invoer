(function ($) {
    var registerType;
    var allRegistrationsOfOp = [];

    var getIdnr = function () {
        var idnr = $('#b4\\.registrationId\\.keyToRP');
        if (idnr.is(':input')) {
            return idnr.getIntegerValue();
        }
        return idnr.getIntegerText();
    };

    var checkIdnr = function () {
        var idnr = $(this).getIntegerValue();
        if (!isNaN(idnr)) {
            $.getJSON('/ajax/lookup/gbh', {idnr: idnr}, function (gbh) {
                $.each(gbh, function (key, value) {
                    $('.data-' + key).setValue(value);
                });

                $('.fail.op').hide();
                $('.btn-next').removeClass('op-error');
                $(document).trigger('changeOfState');

                checkHoofdDatum();
                checkOtherRegistrations();
            }).fail(function () {
                $('.fail.op').text('De onderzoekspersoon met deze identificatie is niet aanwezig!').show();
                $('.btn-next').addClass('op-error');
                $(document).trigger('changeOfState');
            });
        }
        else {
            $('.btn-next').addClass('op-error');
            $(document).trigger('changeOfState');
        }
    };

    var checkBron = function () {
        var keyToSourceRegister = $(this).getIntegerValue();
        if (!isNaN(keyToSourceRegister)) {
            $.getJSON('/ajax/lookup/ainb', {keyToSourceRegister: keyToSourceRegister}, function (ainb) {
                $.each(ainb, function (key, value) {
                    $('.data-' + key).setValue(value);
                });

                registerType = ainb.typeRegister.trim().toUpperCase();

                if (registerType === 'I' || registerType === 'G') {
                    $('.typeIG').show();
                    $('.typeNotIG').hide();
                }
                else {
                    $('.typeIG').hide();
                    $('.typeNotIG').show();
                }

                $('.fail.bron').hide();
                $('.btn-next').removeClass('bron-error');
                $(document).trigger('changeOfState');

                checkHoofdDatum();
            }).fail(function () {
                $('.typeIG').hide();
                $('.typeNotIG').hide();

                $('.fail.bron').text('De bron met deze identificatie is niet aanwezig!').show();
                $('.btn-next').addClass('bron-error');
                $(document).trigger('changeOfState');
            });
        }
        else {
            $('.btn-next').addClass('bron-error');
            $(document).trigger('changeOfState');
        }
    };

    var checkHoofdDatum = function () {
        var idnr = getIdnr();
        var keyToSourceRegister = $('#b4\\.registrationId\\.keyToSourceRegister').getIntegerValue();
        var day = $('#b4\\.registrationId\\.dayEntryHead').getIntegerValue();
        var month = $('#b4\\.registrationId\\.monthEntryHead').getIntegerValue();
        var year = $('#b4\\.registrationId\\.yearEntryHead').getIntegerValue();

        var orgKeyToSourceRegister = $.getDataElem('keytosourceregister').getIntegerDataValue('keytosourceregister');
        var orgDay = $.getDataElem('dayentryhead').getIntegerDataValue('dayentryhead');
        var orgMonth = $.getDataElem('monthentryhead').getIntegerDataValue('monthentryhead');
        var orgYear = $.getDataElem('yearentryhead').getIntegerDataValue('yearentryhead');

        var isCorrectionOfIdentification = ($.isCorrection() && !isNaN(orgKeyToSourceRegister) &&
            !isNaN(orgDay) && !isNaN(orgMonth) && !isNaN(orgYear));

        var onError = function (message) {
            $('.fail.registration').text(message).show();
            $('.btn-next').addClass('registration-error');
            $(document).trigger('changeOfState');
        };

        var onSuccess = function () {
            $('.fail.registration').hide();
            $('.btn-next').removeClass('registration-error');
            $(document).trigger('changeOfState');
        };

        if (isCorrectionOfIdentification && (keyToSourceRegister === orgKeyToSourceRegister) &&
            (day === orgDay) && (month === orgMonth) && (year === orgYear)) {
            onSuccess();
            return;
        }

        if (!isNaN(idnr) && !isNaN(keyToSourceRegister) && !isNaN(day) && !isNaN(month) && !isNaN(year)) {
            $.getJSON('/ajax/lookup/b4', {
                keyToSourceRegister: keyToSourceRegister,
                dayEntryHead: day,
                monthEntryHead: month,
                yearEntryHead: year,
                keyToRP: idnr
            }, function () {
                if ($.isCorrection() && !isCorrectionOfIdentification) {
                    onSuccess();
                }
                else {
                    onError('De combinatie van bronnummer en hoofddatum is reeds ingevoerd!');
                }
            }).fail(function () {
                if ($.isCorrection() && !isCorrectionOfIdentification) {
                    onError('Record niet aanwezig!');
                }
                else {
                    if (!isCorrectionOfIdentification) {
                        checkOtherRegistrations();
                    }
                    onSuccess();
                }
            });
        }
        else {
            $('.btn-next').addClass('registration-error');
            $(document).trigger('changeOfState');
        }
    };

    var checkOpDatum = function () {
        var idnr = getIdnr();
        var day = $('#b4\\.dayEntryRP').getIntegerValue();
        var month = $('#b4\\.monthEntryRP').getIntegerValue();
        var year = $('#b4\\.yearEntryRP').getIntegerValue();

        var orgDay = $.getDataElem('dayentryrp').getIntegerDataValue('dayentryrp');
        var orgMonth = $.getDataElem('monthentryrp').getIntegerDataValue('monthentryrp');
        var orgYear = $.getDataElem('yearentryrp').getIntegerDataValue('yearentryrp');

        var onError = function () {
            $('.fail.op-registration').text('Datum OP bestaat reeds!').show();
            $('.btn-next').addClass('op-registration-error');
            $(document).trigger('changeOfState');
        };

        var onSuccess = function () {
            $('.fail.op-registration').hide();
            $('.btn-next').removeClass('op-registration-error');
            $(document).trigger('changeOfState');
        };

        if (!isNaN(orgDay) && !isNaN(orgMonth) && !isNaN(orgYear) &&
            (day === orgDay) && (month === orgMonth) && (year === orgYear)) {
            onSuccess();
            return;
        }

        if (!isNaN(idnr) && !isNaN(day) && !isNaN(month) && !isNaN(year)) {
            $.getJSON('/ajax/lookup/b4/op', {
                idnr: idnr,
                day: day,
                month: month,
                year: year
            }, function () {
                onError();
            }).fail(function () {
                if (!$.isCorrection()) {
                    checkOtherRegistrations();
                }
                onSuccess();
            });
        }
        else {
            $('.btn-next').addClass('op-registration-error');
            $(document).trigger('changeOfState');
        }
    };

    var checkOtherRegistrations = function () {
        var prevRegistration = null;
        var nextRegistration = null;

        var idnr = $('#b4\\.registrationId\\.keyToRP').getIntegerValue();
        var keyToSourceRegister = $('#b4\\.registrationId\\.keyToSourceRegister').getIntegerValue();

        var dayEntryHead = $('#b4\\.registrationId\\.dayEntryHead').getIntegerValue();
        var monthEntryHead = $('#b4\\.registrationId\\.monthEntryHead').getIntegerValue();
        var yearEntryHead = $('#b4\\.registrationId\\.yearEntryHead').getIntegerValue();

        var dayEntryRP = $('#b4\\.dayEntryRP').getIntegerValue();
        var monthEntryRP = $('#b4\\.monthEntryRP').getIntegerValue();
        var yearEntryRP = $('#b4\\.yearEntryRP').getIntegerValue();

        var findRegistration = function (earlier, registrations) {
            var foundReg = null;
            var curRegDate = new Date(yearEntryRP, monthEntryRP - 1, dayEntryRP);
            $.each(registrations, function (i, reg) {
                if (!(  keyToSourceRegister === reg.registrationId.keyToSourceRegister &&
                    dayEntryHead === reg.registrationId.dayEntryHead &&
                    monthEntryHead === reg.registrationId.monthEntryHead &&
                    yearEntryHead === reg.registrationId.yearEntryHead
                    )) {
                    var thisRegDate = new Date(reg.yearEntryRP, reg.monthEntryRP - 1, reg.dayEntryRP);
                    if ((earlier && ((curRegDate.getTime() - thisRegDate.getTime()) > 0)) ||
                        (!earlier && ((curRegDate.getTime() - thisRegDate.getTime()) < 0))) {
                        foundReg = reg;
                        return false;
                    }
                }
            });
            return foundReg;
        };

        if (!isNaN(idnr) && (dayEntryRP > 0 && monthEntryRP > 0 && yearEntryRP > 0)) {
            $.getJSON('/ajax/lookup/b4/op/list', {idnr: idnr}, function (registrations) {
                allRegistrationsOfOp = registrations;

                nextRegistration = findRegistration(false, registrations);
                prevRegistration = findRegistration(true, registrations.reverse());

                if ((prevRegistration !== null) || (nextRegistration !== null)) {
                    var reg = prevRegistration;
                    if (prevRegistration !== null) {
                        $('.vorige-inschrijving-label').text('Met vorige inschrijving?');
                        $('#vorige-inschrijving').val('j').trigger('keyup');
                    }
                    else {
                        $('.vorige-inschrijving-label').text('Met latere inschrijving?');
                        $('#vorige-inschrijving').val('n').trigger('keyup');
                        reg = nextRegistration;
                    }

                    $('#prevRegistration\\.keyToSourceRegister').val(reg.registrationId.keyToSourceRegister);
                    $('#prevRegistration\\.dayEntryHead').val(reg.registrationId.dayEntryHead);
                    $('#prevRegistration\\.monthEntryHead').val(reg.registrationId.monthEntryHead);
                    $('#prevRegistration\\.yearEntryHead').val(reg.registrationId.yearEntryHead);

                    $('.group-vorige-inschrijving').show();
                }
                else {
                    $('.group-vorige-inschrijving').hide();
                }

                checkVorigeInsschrijving();
            });
        }
        else {
            allRegistrationsOfOp = [];
            $('.group-vorige-inschrijving').hide();
            checkVorigeInsschrijving();
        }
    };

    var checkDateHfd = function (hsnDate, elem) {
        var error = $.checkDateBevReg(hsnDate, elem);

        var opHsnDate = $('.checkDateOP').getHsnDate();

        var dayVal = hsnDate.day.getValue();
        var monthVal = hsnDate.month.getValue();
        var yearVal = hsnDate.year.getValue();

        var opDate = new Date(opHsnDate.year.getValue(), opHsnDate.month.getValue() - 1, opHsnDate.day.getValue());
        var hfdDate = new Date(yearVal, monthVal - 1, dayVal);

        if (!error && opHsnDate.year.getValue() > 0 && opHsnDate.month.getValue() > 0 && opHsnDate.day.getValue() > 0) {
            $.setError(
                    opDate.getTime() < hfdDate.getTime(),
                'op-hfd-datum',
                'OPdatum eerder dan Hoofddatum'
            );
        }

        return error;
    };

    var checkDateOP = function (hsnDate, elem) {
        var error = $.checkDateBevReg(hsnDate, elem);

        var hfdHsnDate = $('.checkDateHfd').getHsnDate();

        var dayVal = hsnDate.day.getValue();
        var monthVal = hsnDate.month.getValue();
        var yearVal = hsnDate.year.getValue();

        var opDate = new Date(yearVal, monthVal - 1, dayVal);
        var hfdDate = new Date(hfdHsnDate.year.getValue(), hfdHsnDate.month.getValue() - 1, hfdHsnDate.day.getValue());

        if (!error) {
            $.setError(
                    opDate.getTime() < hfdDate.getTime(),
                'op-hfd-datum',
                'OPdatum eerder dan Hoofddatum'
            );
        }

        return error;
    };

    var checkVolgnummer = function (self) {
        var volgnummer = self.getIntegerValue();
        if (!isNaN(volgnummer)) {
            $.setError(
                    (registerType === 'A' || registerType === 'I') && (volgnummer > 1),
                'volgnummer-op',
                'Slechts een persoon per familie eenheid toegestaan!'
            );

            if (self.attr('id') === 'volgnrOP') {
                $('#noRegels').val(volgnummer);
            }
        }
    };

    var checkVorigeInsschrijving = function () {
        var error = false;
        var vorigeInschrijving = $('#vorige-inschrijving');

        if (vorigeInschrijving.val() === 'j') {
            var bron = $('#prevRegistration\\.keyToSourceRegister').getIntegerValue();
            var day = $('#prevRegistration\\.dayEntryHead').getIntegerValue();
            var month = $('#prevRegistration\\.monthEntryHead').getIntegerValue();
            var year = $('#prevRegistration\\.yearEntryHead').getIntegerValue();

            error = true;
            $.each(allRegistrationsOfOp, function (i, reg) {
                if (reg.registrationId.keyToSourceRegister === bron &&
                    reg.registrationId.dayEntryHead === day &&
                    reg.registrationId.monthEntryHead === month &&
                    reg.registrationId.yearEntryHead === year) {
                    error = false;
                }
            });

            $.setError(
                error,
                'vorige-inschrijving',
                'Deze combinatie van bronnummer en hoofddatum is nog niet ingevoerd!'
            );
        }
    };

    $.initCheckDate('.checkDateHfd', $.prepareDate, checkDateHfd);
    $.initCheckDate('.checkDateOP', $.prepareDate, checkDateOP);

    $(document).ready(function () {
        $('#b4\\.registrationId\\.keyToRP').filter(':input').blur(checkIdnr);
        $('#b4\\.registrationId\\.keyToSourceRegister').blur(checkBron);
        $('#b4\\.registrationId\\.dayEntryHead, #b4\\.registrationId\\.monthEntryHead, #b4\\.registrationId\\.yearEntryHead').blur(checkHoofdDatum);
        $('#b4\\..dayEntryRP, #b4\\.monthEntryRP, #b4\\.yearEntryRP').blur(checkOpDatum);

        if (!$.isCorrection()) {
            var volgNrOp = $('#volgnrOP');
            checkVolgnummer(volgNrOp);
            volgNrOp.blur(function () {
                checkVolgnummer(volgNrOp);
            });

            var aantalRegels = $('#noRegels');
            checkVolgnummer(aantalRegels);
            aantalRegels.blur(function () {
                checkVolgnummer(aantalRegels);
            });
        }

        $('.group-vorige-inschrijving input').blur(function () {
            checkVorigeInsschrijving();
        });
    });
})(jQuery);