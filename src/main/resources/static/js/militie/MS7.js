(function ($) {
    'use strict';

    $(document).ready(function () {
        if (!$.isCorrection()) {
           $('.warning-byz').show();
        }
    });
})(jQuery);