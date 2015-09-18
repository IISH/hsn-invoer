(function ($) {
    'use strict';

    var originalIdnrCheck = function () {
        var elem = $('#pkknd\\.idnrp');
        var value = elem.getIntegerValue();

        elem.hasErrorWhen(isNaN(value) || value <= 0 || value >= 500000);
        $(document).trigger('changeOfState');
    };

    var typePkCheck = function () {
        var validOptions = [1, 2, 3, 4, 5, 6, 7, 8, 10];
        var elem = $('#pkknd\\.pktype');
        var value = elem.getIntegerValue();

        elem.hasErrorWhen(validOptions.indexOf(value) < 0);
        $(document).trigger('changeOfState');
    };

    $.initCheckDate(
        '.checkDatePersBewijs', $.prepareDate, function (hsnDate) {
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