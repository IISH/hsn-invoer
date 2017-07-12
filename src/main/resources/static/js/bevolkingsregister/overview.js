(function ($) {
    'use strict';

    function remember(elem) {
        $.ajax({
            url: '/bevolkingsregister/remember',
            type: 'POST',
            data: {
                keyToSourceRegister: elem.find('.keyToSourceRegister').text(),
                dayEntryHead: elem.find('.dayEntryHead').text(),
                monthEntryHead: elem.find('.monthEntryHead').text(),
                yearEntryHead: elem.find('.yearEntryHead').text(),
                keyToRP: elem.find('.keyToRP').text()
            },
            success: function () {
                $(document).trigger('bevolkingsregister-remember');
            }
        });
    }

    $(document).on('click', '.btn-remember', function (e) {
        remember($(e.target).closest('tr'));
    });
})(jQuery);