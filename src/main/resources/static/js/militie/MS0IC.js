(function ($) {
    'use strict';

    $.initCheckDate('.checkYearMilitie', null, function (hsnDate) {
        var yearVal = hsnDate.year.getValue();
        return (yearVal === 0 || yearVal <= 1700 || yearVal >= 2025);
    });
})(jQuery);