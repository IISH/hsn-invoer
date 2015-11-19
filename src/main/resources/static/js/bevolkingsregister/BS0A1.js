(function ($) {
    'use strict';

    $(document).on('blur', '.new-key', function (e) {
        var newKeys = $('.new-key');
        var keys = [];
        var doubleKeyError = false;
        newKeys.each(function (i, newKey) {
            var newKeyVal = $(newKey).getIntegerValue();
            if (newKeyVal !== 0) {
                if (keys.indexOf(newKeyVal) >= 0) {
                    doubleKeyError = true;
                }
                else {
                    keys.push(newKeyVal);
                }
            }
        });
        $.setError(doubleKeyError, 'double-keys', 'Regel bestaat reeds!');

        var self = $(e.target);
        if (self.hasClass('is-rp')) {
            var value = self.getIntegerValue();
            $.setError((isNaN(value) || (value === 0)), 'rp-delete', 'OP-regel kan niet verwijderd worden.');
        }
    });
})(jQuery);