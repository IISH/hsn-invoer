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
    });
})(jQuery);