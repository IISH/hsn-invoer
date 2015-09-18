(function ($) {
    'use strict';

    var checkNumberOfGetuigen = function () {
        var self = $('#huwknd\\.ngtg');
        var nrGetuigen = self.getIntegerValue();

        self.hasErrorWhen(isNaN(nrGetuigen) || ((nrGetuigen < 1 || nrGetuigen > 6) && (nrGetuigen !== -5)));
        $(document).trigger('changeOfState');
    };

    $(document).ready(function () {
        checkNumberOfGetuigen();
        $('#huwknd\\.ngtg').blur(checkNumberOfGetuigen);
    });
})(jQuery);