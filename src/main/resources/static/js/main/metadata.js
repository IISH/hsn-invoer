(function ($) {
    var checkLength = function (elem) {
        elem.hasErrorWhen(elem.val().length !== 3);
        $(document).trigger('changeOfState');
    };

    $(document).ready(function () {
        $('#init, #ondrzk, #opdrnr').blur(function () {
            checkLength($(this));
        }).each(function () {
            checkLength($(this));
        });
    });
})(jQuery);