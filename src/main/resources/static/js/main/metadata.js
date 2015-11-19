(function ($) {
    'use strict';

    var checkLength = function (elem) {
        elem.hasErrorWhen(elem.val().length !== 3);
        $.triggerChangeOfState();
    };

    $(document).ready(function () {
        $('#init, #ondrzk, #opdrnr').blur(function () {
            checkLength($(this));
        }).each(function () {
            checkLength($(this));
        });
    });
})(jQuery);