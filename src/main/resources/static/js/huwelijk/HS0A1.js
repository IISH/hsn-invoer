(function ($) {
    'use strict';

    function checkNumberOfGetuigen() {
        var self = $('#huwknd\\.ngtg');
        var nrGetuigen = self.getIntegerValue();

        self.hasErrorWhen(isNaN(nrGetuigen) || ((nrGetuigen < 1 || nrGetuigen > 6) && (nrGetuigen !== -5)));
        $.triggerChangeOfState();
    }

    $(document).ready(function () {
        checkNumberOfGetuigen();
        $('#huwknd\\.ngtg').blur(checkNumberOfGetuigen);
    });
})(jQuery);