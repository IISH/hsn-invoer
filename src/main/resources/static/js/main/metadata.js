(function ($) {
    'use strict';

    function checkLength(elem) {
        elem.hasErrorWhen(elem.val().length !== 3);
    };

    var checkAccess = function () {
        var errorMessage = 'Helaas mag u voor deze werk order niet invoeren of correcties uitvoeren!';

        if ($('.has-an-error:visible').length === 0) {
            var ondrzk = $('#ondrzk').val();
            var opdrnr = $('#opdrnr').val();

            $.ajax('/check', {
                type: 'GET',
                data: {ondrzk: ondrzk, opdrnr: opdrnr},
                success: function () {
                    $.setError(false, 'metadata', errorMessage);
                    $.triggerChangeOfState();
                    $('.btn-next').focus();
                },
                error: function () {
                    $.setError(true, 'metadata', errorMessage);
                    $.triggerChangeOfState();
                }
            });
        }
        else {
            $.setError(false, 'metadata', errorMessage);
        }
    }

    $(document).ready(function () {
        $('#ondrzk, #opdrnr').blur(function () {
            checkLength($(this));
            checkAccess();
            $.triggerChangeOfState();
        }).each(function () {
            checkLength($(this));
            $.triggerChangeOfState();
        });

        if (window.location.search === '?exit=true') {
            $('.btn-logout').addClass('focusOnReady');
        }
    });
})(jQuery);