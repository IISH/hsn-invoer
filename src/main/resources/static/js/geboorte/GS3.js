(function ($) {
    'use strict';

    var updateAnaamKind = function () {
        var anaamKind = $('#gebknd\\.anmgeb');
        if ((anaamKind.val() === undefined) || (anaamKind.val() === '')) {
            var brgstmr = $('#gebknd\\.brgstmr').getIntegerValue();
            if ([5, 7, 8].indexOf(brgstmr) >= 0) {
                anaamKind.val($.getDataElem('anaam-ag').data('anaam-ag'));
            }
            if ([1, 6].indexOf(brgstmr) >= 0) {
                anaamKind.val($.getDataElem('anaam-mr').data('anaam-mr'));
            }
        }
    };

    $(document).ready(function () {
        if (!$.isCorrection()) {
            updateAnaamKind();
            $('#gebknd\\.brgstmr').change(updateAnaamKind);
        }
    });
})(jQuery);