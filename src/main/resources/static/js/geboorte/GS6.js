(function ($) {
    'use strict';

    var mayAddByz = function () {
        var byzBtn = $('.btn-byz');

        byzBtn.addClass('no-byz');
        if (($('#gebknd\\.kant').val() === 'j') && ($('#gebkant\\.kanttype').getIntegerValue() === 5)) {
            byzBtn.removeClass('no-byz');
        }

        $.triggerChangeOfState();
    };

    $(document).ready(function () {
        mayAddByz();
        $('#gebknd\\.kant, #gebkant\\.kanttype').change(mayAddByz);
    });
})(jQuery);