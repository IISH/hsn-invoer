(function ($) {
    'use strict';

    function originalIdnrCheck() {
        var elem = $('#pkknd\\.idnrp');
        var value = elem.getIntegerValue();

        elem.hasErrorWhen(isNaN(value) || value <= 0 || value >= 500000);
        $.triggerChangeOfState();
    }

    function typePkCheck() {
        var validOptions = [1, 2, 3, 4, 5, 6, 7, 8, 10];
        var elem = $('#pkknd\\.pktype');
        var value = elem.getIntegerValue();

        elem.hasErrorWhen(validOptions.indexOf(value) < 0);
        $.triggerChangeOfState();
    }

    $.initCheckDate(
        '.checkDatePersBewijs', $.prepareDate, function checkDate(hsnDate) {
            var error = $.checkHsnDate(hsnDate);
            if (error) {
                return true;
            }

            var yearVal = hsnDate.year.getValue();
            return !((yearVal > 1939 && yearVal < 1946) || (yearVal === -1) || (yearVal === -2) || (yearVal === -3));
        }
    );

    $(document).ready(function () {
        originalIdnrCheck();
        $('#pkknd\\.idnrp').blur(originalIdnrCheck);

        typePkCheck();
        $('#pkknd\\.pktype').blur(typePkCheck);
    });
})(jQuery);