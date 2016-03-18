(function ($) {
    'use strict';

    var openModal = function (modal, elem) {
        $.ajax({
            url: '/bevolkingsregister/overzicht/persons',
            type: 'GET',
            dataType: 'text',
            data: {
                keyToSourceRegister: elem.find('.keyToSourceRegister').text(),
                dayEntryHead: elem.find('.dayEntryHead').text(),
                monthEntryHead: elem.find('.monthEntryHead').text(),
                yearEntryHead: elem.find('.yearEntryHead').text(),
                keyToRP: elem.find('.keyToRP').text()
            },
            success: function (result) {
                var resultElem = $(result);
                $('#personsBody').replaceWith(resultElem);
                $(document).trigger('ajax-update', [resultElem]);

                modal.modal({keyboard: false, backdrop: 'static'});
            }
        });
    };

    var closeModal = function (modal) {
        modal.modal('hide');
    };

    var forIdnr = function () {
        var idnr = parseInt(prompt('Identificatienummer Onderzoekspersoon:', '0'));
        if (isNaN(idnr)) {
            return;
        }
        location.search = "?idnr=" + idnr;
    };

    $(document).on('click', '.btn-persons', function (e) {
        openModal($('#personsModal'), $(e.target).closest('tr'));
    });

    $(document).keydown(function (e) {
        var modal = $('#personsModal');

        if (e.shiftKey) {
            switch (e.which) {
                case 115: // F4
                    forIdnr();
                    e.preventDefault();
                    break;
                case 114: // F3
                    if (!modal.hasClass('in')) {
                        openModal(modal, $(':focus').closest('tr'));
                        e.preventDefault();
                    }
                    break;
            }
        }
        else if ((e.which == 27) && modal.hasClass('in')) { // Esc
            closeModal(modal);
            e.preventDefault();
        }
    });
})(jQuery);