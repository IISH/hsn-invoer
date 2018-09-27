(function ($) {
    'use strict';

    $(document).on('keyup', '.lengths input', function validateLengths(e) {
        var elem = $(e.target);
        var val = elem.val();
        if ((e.which !== 9) && (e.which !== 16) && (val.indexOf('-') !== 0) && (val.length === 1)) {
            elem.autoNextFocus(true);
        }
        return true;
    });

    function checkLength(elem) {
        var val = elem.getIntegerValue();
        elem.hasErrorWhen(isNaN(val) || (val < -3) || (val > 9) || (elem.hasClass('meter') && (val > 2)));
    }

    function checkScore(elem) {
        var val = elem.getIntegerValue();
        elem.hasErrorWhen(isNaN(val) || (val < -2) || (val > 5));
    }

    function setEmpty(elem) {
        if ($.isCorrection() && isNaN(elem.getIntegerValue())) {
            $(elem).val(0);
        }
    }

    $(document).ready(function () {
        var lengths = $('.lengths input');
        lengths.each(function () {
            setEmpty($(this));
            checkLength($(this));
        });
        lengths.blur(function (e) {
            checkLength($(e.target));
        });

        var weights = $('.weights input');
        weights.each(function () {
            setEmpty($(this));
        });

        var scores = $('.score');
        scores.blur(function (e) {
            checkScore($(e.target));
        });
    });
})(jQuery);