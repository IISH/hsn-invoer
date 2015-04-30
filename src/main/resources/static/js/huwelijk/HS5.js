(function ($) {
    var checkAge = function () {
        var self = $('#curHuwgtg\\.lftjgt');
        var age = self.getIntegerValue();

        self.hasErrorWhen(age < -3 || age > 110 || (age > -1 && age < 15 && age !== 0));
        $(document).trigger('changeOfState');
    };

    $(document).ready(function () {
        checkAge();
        $('#curHuwgtg\\.lftjgt').blur(checkAge);
    });
})(jQuery);