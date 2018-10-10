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

        if (type !== 'L' && type !== 'K' && type !== 'N' && type !== 'F'
            && type !== 'M' && type !== 'D' && type !== 'B') {
            $('#mil\\.seqRefer').getParentOfFormElement().show();
        }
        else {
            $('#mil\\.seqRefer').getParentOfFormElement().hide();
        }

        if (!(type === 'I' && year >= 1815 && year <= 1861) &&
            !((type === 'F') || (type === 'M') || (type === 'D') || (type === 'B'))) {
            $('#mil\\.drawnNumber').getParentOfFormElement().show();
        }
        else {
            $('#mil\\.drawnNumber').getParentOfFormElement().hide();
        }

        if ((type === 'F') || (type === 'M') || (type === 'D') || (type === 'B')) {
            $('.onlyYear').hide().find('.year').attr('name', '');
            $('.fullDate').show().find('.year').attr('name', 'mil.year');
            $('#mil\\.yearChoice').getParentOfFormElement().hide();
            $('#mil\\.invNumber').getParentOfFormElement().find('label').text('Administratief nummer:');
        }
        else {
            $('.fullDate').hide().find('.year').attr('name', '');
            $('.onlyYear').show().find('.year').attr('name', 'mil.year');
            $('#mil\\.yearChoice').getParentOfFormElement().show();
            $('#mil\\.invNumber').getParentOfFormElement().find('label').text('Inventarisnummer:');
        }
    }

    $.initCheckDate('.checkYearMilitie', null, function dateCheckMilition(hsnDate) {
        var yearVal = hsnDate.year.getValue();
        return (yearVal === 0 || yearVal < 1800 || yearVal > 2000);
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