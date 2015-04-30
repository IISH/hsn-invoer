(function ($) {
    $.initCheckDate(
        '.checkDateTimeHuw', null, function (hsnDate) {
            var error = true;

            var dayVal = hsnDate.day.getValue();
            var monthVal = hsnDate.month.getValue();
            var yearVal = hsnDate.year.getValue();
            var hourVal = hsnDate.hour.getValue();

            if (dayVal < 32 && monthVal < 13 && hourVal < 24) {
                if (dayVal > -4 && monthVal > -4 && hourVal > -4) {
                    if (dayVal !== 0 && monthVal !== 0) {
                        if (yearVal > 1810) {
                            error = false;
                        }
                    }
                }
            }

            return error;
        }
    );
})(jQuery);