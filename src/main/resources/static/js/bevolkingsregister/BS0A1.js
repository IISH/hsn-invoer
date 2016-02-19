(function ($) {
    'use strict';

    $(document).on('blur', '.new-key', function (e) {
        var noLines = $.getDataElem('no-lines').getIntegerDataValue('no-lines');
        var newKeys = $('.new-key');
        var keys = [];
        var doubleKeyError, maxLinesError = false;
        newKeys.each(function (i, newKey) {
            var newKeyVal = $(newKey).getIntegerValue();
            if (newKeyVal !== 0) {
                if (keys.indexOf(newKeyVal) >= 0) {
                    doubleKeyError = true;
                }
                else {
                    keys.push(newKeyVal);
                }

                if ((noLines > 0) && (newKeyVal > noLines)) {
                    maxLinesError = true;
                }
            }
        });
        $.setError(doubleKeyError, 'double-keys', 'Regel bestaat reeds!');
        $.setError(maxLinesError, 'max-lines', 'U heeft opgegeven maximaal ' + noLines + ' regels in te voeren!');
    });
})(jQuery);