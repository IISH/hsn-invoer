(function ($) {
    'use strict';

    var militions;

    function checkEnteredInput() {
        var message = $('#scansEnteredMessage');

        var year = $('#mil\\.year').val();
        var municipality = $('#mil\\.municipality').val();

        var militionsFound = [];
        $.each(militions, function (idx, milition) {
            if (year === milition.year || municipality === milition.municipality) {
                militionsFound.push(milition);
            }
        });

        if (militionsFound.length > 0) {
            var list = message.find('ul').html('');
            $.each(militionsFound, function (idx, milition) {
                list.append('<li>' + milition.municipality + ' / ' + milition.year + ' / ' + milition.type + '</li>');
            });
            message.show();
        }
        else {
            message.hide();
        }
    }

    function checkYearAndType() {
        var year = $('#mil\\.year').getIntegerValue();
        var type = $('#mil\\.type').val();

        $.setError(
            (type === 'A') && (year >= 1862) && (year <= 1912),
            'year-and-type',
            'Deze variant mag niet worden ingevoerd!'
        );

        if (((type === 'A') && (year < 1862 || year > 1912))) {
            $('#mil\\.seqRegister').getParentOfFormElement().show();
        }
        else {
            $('#mil\\.seqRegister').getParentOfFormElement().hide();
        }

        if ((type !== 'L' && type !== 'K' && type !== 'N')) {
            $('#mil\\.seqRefer').getParentOfFormElement().show();
        }
        else {
            $('#mil\\.seqRefer').getParentOfFormElement().hide();
        }

        if (!(type === 'I' && year >= 1815 && year <= 1861)) {
            $('#mil\\.drawnNumber').getParentOfFormElement().show();
        }
        else {
            $('#mil\\.drawnNumber').getParentOfFormElement().hide();
        }
    }

    $.initCheckDate('.checkYearMilitie', null, function dateCheckMilition(hsnDate) {
        var yearVal = hsnDate.year.getValue();
        return (yearVal === 0 || yearVal <= 1700 || yearVal >= 2025);
    });

    $(document).on('focus', '.form-elem', function onFocus(e) {
        var lotingsNr = $('#lotingsnummerMessage');
        var year = $('#mil\\.year').getIntegerValue();

        lotingsNr.hideNoEvent();
        if (year >= 1913 && year <= 1922) {
            lotingsNr.showNoEvent();
        }
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