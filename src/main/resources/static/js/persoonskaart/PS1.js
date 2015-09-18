(function ($) {
    'use strict';

    var showDeathCause = function () {
        var toShow = $('.checkYear');
        var year = $('#pkknd\\.ojrperp').getIntegerValue();

        if (year < 1957) {
            toShow.show();
        }
        else {
            toShow.hide();
        }
    };

    $(document).ready(function () {
        showDeathCause();
        $('#pkknd\\.ojrperp').blur(showDeathCause);
    });
})(jQuery);