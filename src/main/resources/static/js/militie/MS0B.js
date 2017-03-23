(function ($) {
    'use strict';

    var militions;

    var checkEnteredInput = function () {
        var message = $('#scansEnteredMessage');

        var year = $('#mil\\.year').val();
        var municipality = $('#mil\\.municipality').val();

        var militionsFound = [];
        $.each(militions, function (idx, milition) {
            if (year == milition.year || municipality == milition.municipality) {
                militionsFound.push(milition);
            }
        });

        if (militionsFound.length > 0) {
            var list = message.find('ul').html('');
            $.each(militionsFound, function (idx, milition) {
                list.append('<li>' + milition.municipality + ' / ' + milition.year + '</li>');
            });
            message.show();
        }
        else {
            message.hide();
        }
    };

    var checkYearAndType = function () {
        var year = $('#mil\\.year').getIntegerValue();
        var type = $('#mil\\.type').val();

        $.setError(
            (type === 'a') && (year >= 1862) && (year <= 1912),
            'year-and-type',
            'Deze variant mag niet worden ingevoerd!'
        );

        if (type === 'k') {
            $('#mil\\.invNumber').getParentOfFormElement().show();
        }
        else {
            $('#mil\\.invNumber').getParentOfFormElement().hide();
        }

        if ((type === 'a') && (year < 1862 || year > 1912)) {
            $('#mil\\.seqRegister').getParentOfFormElement().show();
        }
        else {
            $('#mil\\.seqRegister').getParentOfFormElement().hide();
        }

        if (type !== 'l' && type !== 'k' && type !== 'n') {
            $('#mil\\.seqRefer').getParentOfFormElement().show();
        }
        else {
            $('#mil\\.seqRefer').getParentOfFormElement().hide();
        }

        if (!(type === 'i' && year >= 1815 && year <= 1861)) {
            $('#mil\\.drawnNumber').getParentOfFormElement().show();
        }
        else {
            $('#mil\\.drawnNumber').getParentOfFormElement().hide();
        }
    };

    $.initCheckDate('.checkYearMilitie', null, function (hsnDate) {
        var yearVal = hsnDate.year.getValue();
        return (yearVal === 0 || yearVal <= 1700 || yearVal >= 2025);
    });

    $(document).ready(function () {
        if (!$.isCorrection()) {
            $.lockNavigation();
            $.getJSON('/ajax/lookup/m0/list', {idnr: $('.idnr').text()}, function (data) {
                $.unlockNavigation();
                militions = data;

                checkEnteredInput();
                $('#mil\\.year, #mil\\.municipality').blur(checkEnteredInput);
            });
        }

        checkYearAndType();
        $('#mil\\.year, #mil\\.type').blur(checkYearAndType);
    });
})(jQuery);