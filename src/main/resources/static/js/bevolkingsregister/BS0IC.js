(function ($) {
    'use strict';

    var registerType;
    var allRegistrationsOfOp = [];

    function getIdnr() {
        var idnr = $('#b4\\.registrationId\\.keyToRP');
        if (idnr.is(':input')) {
            return idnr.getIntegerValue();
        }
        return idnr.getIntegerText();
    }

    function checkIdnr() {
        var idnr = $(this).getIntegerValue();
        if (!isNaN(idnr)) {
            $.getJSON('/ajax/lookup/rp', {idnr: idnr}, function (rp) {
                $.each(rp, function (key, value) {
                    $('.data-' + key).setValue(value);
                });

                $('.fail.op').hide();
                $('.btn-next').removeClass('op-error');
                $.triggerChangeOfState();

                checkHoofdDatum();
                checkOtherRegistrations();
            }).fail(function () {
                $('.fail.op').text('De onderzoekspersoon met deze identificatie is niet aanwezig!').show();
                $('.btn-next').addClass('op-error');
                $.triggerChangeOfState();
                $('.person span').text('');

                checkHoofdDatum();
                checkOtherRegistrations();
            });
        }
        else {
            $('.btn-next').addClass('op-error');
            $.triggerChangeOfState();
            $('.person span').text('');
        }
    }

    function checkBron() {
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
                $.triggerChangeOfState();

                checkHoofdDatum();
            }).fail(function () {
                $('.typeIG').hide();
                $('.typeNotIG').hide();

                $('.fail.bron').text('De bron met dit nummer is niet aanwezig!').show();
                $('.btn-next').addClass('bron-error');
                $.triggerChangeOfState();
                $('.register span').text('');

                checkHoofdDatum();
            });
        }
        else {
            $('.btn-next').addClass('bron-error');
            $.triggerChangeOfState();
            $('.register span').text('');
        }
    }

    function checkHoofdDatum(e) {
        function onError(message) {
            $('.fail.registration').text(message).show();
            $('.btn-next').addClass('registration-error');
            $.triggerChangeOfState();
        }

        function onSuccess(elem, isNext) {
            $('.fail.registration').hide();
            $('.btn-next').removeClass('registration-error');
            $.triggerChangeOfState();

            if ($.isCorrection() && elem.hasClass('year') && isNext) {
                elem.getNextFormElement().focus();
            }
        }

        var idnr = getIdnr();
        var keyToSourceRegister = $('#b4\\.registrationId\\.keyToSourceRegister').getIntegerValue();
        var day = $('#b4\\.registrationId\\.dayEntryHead').getIntegerValue();
        var month = $('#b4\\.registrationId\\.monthEntryHead').getIntegerValue();
        var year = $('#b4\\.registrationId\\.yearEntryHead').getIntegerValue();

        var orgKeyToSourceRegister = $.getDataElem('keytosourceregister').getIntegerDataValue('keytosourceregister');
        var orgDay = $.getDataElem('dayentryhead').getIntegerDataValue('dayentryhead');
        var orgMonth = $.getDataElem('monthentryhead').getIntegerDataValue('monthentryhead');
        var orgYear = $.getDataElem('yearentryhead').getIntegerDataValue('yearentryhead');

        var isNext = $.getCurNavigation().isNext;
        var isCorrectionOfIdentification = ($.isCorrection() && !isNaN(orgKeyToSourceRegister) &&
            !isNaN(orgDay) && !isNaN(orgMonth) && !isNaN(orgYear));

        if (isCorrectionOfIdentification && (keyToSourceRegister === orgKeyToSourceRegister) &&
            (day === orgDay) && (month === orgMonth) && (year === orgYear)) {
            onSuccess($(e.target), isNext);
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
                    onSuccess($(e.target), isNext);
                }
                else {
                    onError('De combinatie van bronnummer en hoofddatum is reeds ingevoerd!');
                }
            }).fail(function () {
                if ($.isCorrection() && !isCorrectionOfIdentification) {
                    onError('Deze door uw opgegeven combinatie van bronnummer en hoofddatum is nog niet ingevoerd!');
                }
                else {
                    if (!isCorrectionOfIdentification) {
                        checkOtherRegistrations();
                    }
                    onSuccess($(e.target), isNext);
                }
            });
        }
        else {
            $('.btn-next').addClass('registration-error');
            $.triggerChangeOfState();
        }
    }

    function checkOpDatum() {
        function onError() {
            $('.fail.op-registration').text('Datum OP bestaat reeds!').show();
            $('.btn-next').addClass('op-registration-error');
            $.triggerChangeOfState();
        }

        function onSuccess() {
            $('.fail.op-registration').hide();
            $('.btn-next').removeClass('op-registration-error');
            $.triggerChangeOfState();
        }

        var idnr = getIdnr();
        var day = $('#b4\\.dayEntryRP').getIntegerValue();
        var month = $('#b4\\.monthEntryRP').getIntegerValue();
        var year = $('#b4\\.yearEntryRP').getIntegerValue();

        var orgDay = $.getDataElem('dayentryrp').getIntegerDataValue('dayentryrp');
        var orgMonth = $.getDataElem('monthentryrp').getIntegerDataValue('monthentryrp');
        var orgYear = $.getDataElem('yearentryrp').getIntegerDataValue('yearentryrp');

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
            $.triggerChangeOfState();
        }
    }

    function checkOtherRegistrations() {
        function findRegistration(earlier, registrations) {
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
        }

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

        if (!isNaN(idnr) && (dayEntryRP > 0 && monthEntryRP > 0 && yearEntryRP > 0)) {
            $.getJSON('/ajax/lookup/b4/op/list', {idnr: idnr}, function (registrations) {
                allRegistrationsOfOp = registrations;

                nextRegistration = findRegistration(false, registrations);
                prevRegistration = findRegistration(true, registrations.reverse());

                var vorigeInschrijving = $('#vorige-inschrijving');
                if ((prevRegistration !== null) || (nextRegistration !== null)) {
                    var reg = prevRegistration;
                    if (prevRegistration !== null) {
                        $('.vorige-inschrijving-label').text('Met vorige inschrijving?');
                        vorigeInschrijving.val('j');
                    }
                    else {
                        $('.vorige-inschrijving-label').text('Met latere inschrijving?');
                        vorigeInschrijving.val('n');
                        reg = nextRegistration;
                    }

                    $('#prevRegistration\\.keyToSourceRegister').val(reg.registrationId.keyToSourceRegister);
                    $('#prevRegistration\\.dayEntryHead').val(reg.registrationId.dayEntryHead);
                    $('#prevRegistration\\.monthEntryHead').val(reg.registrationId.monthEntryHead);
                    $('#prevRegistration\\.yearEntryHead').val(reg.registrationId.yearEntryHead);

                    $('.group-vorige-inschrijving').show();
                    vorigeInschrijving.change();
                }
                else {
                    $('.group-vorige-inschrijving').hide();
                }

                checkVorigeInschrijving();
            });
        }
        else {
            allRegistrationsOfOp = [];
            $('.group-vorige-inschrijving').hide();
            checkVorigeInschrijving();
        }
    }

    function checkDateHfd(hsnDate, elem) {
        var error = $.checkDateBevReg(hsnDate, elem);

        var dateOp = $('.checkDateOP');
        if (dateOp.length > 0) {
            var opHsnDate = dateOp.getHsnDate();

            var dayVal = hsnDate.day.getValue();
            var monthVal = hsnDate.month.getValue();
            var yearVal = hsnDate.year.getValue();

            var opDate = new Date(opHsnDate.year.getValue(), opHsnDate.month.getValue() - 1, opHsnDate.day.getValue());
            var hfdDate = new Date(yearVal, monthVal - 1, dayVal);

            if (!error) {
                $.setError(
                    opDate.getTime() < hfdDate.getTime(),
                    'op-hfd-datum',
                    'OPdatum eerder dan Hoofddatum'
                );
            }
        }

        return error;
    }

    function checkDateOP(hsnDate, elem) {
        var error = $.checkDateBevReg(hsnDate, elem);

        var dateHfd = $('.checkDateHfd');
        if (dateHfd.length > 0) {
            var hfdHsnDate = dateHfd.getHsnDate();

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
        }

        return error;
    }

    function checkVolgnummer(self) {
        var volgnummer = self.getIntegerValue();
        if (!isNaN(volgnummer)) {
            var volgNrOpElem = $('#volgnrOP');
            var aantalRegelsElem = $('#noRegels');

            if ((self.attr('id') === 'volgnrOP') && isNaN(aantalRegelsElem.getIntegerValue())) {
                aantalRegelsElem.val(volgnummer);
            }

            var onePersonWarning = $('#onePersonWarning');
            if ((registerType === 'A' || registerType === 'I') && (volgnummer > 1)) {
                onePersonWarning.show();
            }
            else {
                onePersonWarning.hide();
            }

            var volgNrOp = volgNrOpElem.getIntegerValue();
            var aantalRegels = aantalRegelsElem.getIntegerValue();
            $.setErrorWithClass(
                !isNaN(volgNrOp) && !isNaN(aantalRegels) && (volgNrOp > aantalRegels),
                'volgnummer-op-too-big',
                $('.fail.volgnr-op'),
                'Volgnummer OP is hoger dan het aantal regels!'
            );
        }
    }

    function checkVorigeInschrijving() {
        var error = false;
        var vorigeInschrijving = $('#vorige-inschrijving');

        if ((vorigeInschrijving.val() === 'j') && vorigeInschrijving.is(':visible')) {
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
                'Deze door uw opgegeven combinatie van bronnummer en hoofddatum is nog niet ingevoerd!'
            );
        }
    }

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
            checkVorigeInschrijving();
        });
    });
})(jQuery);