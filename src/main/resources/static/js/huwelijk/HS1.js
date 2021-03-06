(function ($) {
    'use strict';

    function checkAge() {
        var self = $('#huwknd\\.lftjhm, #huwknd\\.lftjhv');
        var age = self.getIntegerValue();

        self.hasErrorWhen(age < -3 || age > 110 || (age > -1 && age < 15 && age !== 0));
        $.triggerChangeOfState();
    }

    $(document).ready(function () {
        checkAge();
        $('#huwknd\\.lftjhm, #huwknd\\.lftjhv').blur(checkAge);
    });
})(jQuery);