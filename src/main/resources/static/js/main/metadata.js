(function ($) {
    'use strict';

    function checkLength(elem) {
        elem.hasErrorWhen(elem.val().length !== 3);
        $.triggerChangeOfState();
    }

    $(document).ready(function () {
        $('#init, #ondrzk, #opdrnr').blur(function () {
            checkLength($(this));
        }).each(function () {
            checkLength($(this));
        });

        if (window.location.search === '?exit=true') {
            $('.btn-logout').addClass('focusOnReady');
        }
    });
})(jQuery);