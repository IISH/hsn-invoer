(function ($) {
    'use strict';

    function openModal(elem) {
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
                $('#personsModal').modal({keyboard: false, backdrop: 'static'});
            }
        });
    }

    function forIdnr() {
        var modal = $.getOpenedModal();

        var idnr = parseInt(modal.find('input').val());
        if (isNaN(idnr)) {
            modal.modal('hide');
            return;
        }

        modal.modal('hide');
        location.search = "?idnr=" + idnr;
    }

    function forIdnrOpen() {
        var modal = $('#idnr');
        modal.find('input').val('0');
        modal.modal({keyboard: false, backdrop: 'static'});
    }

    $(document).on('click', '.btn-persons', function (e) {
        openModal($(e.target).closest('tr'));
    });

    $(document).keydown(function (e) {
        var modal = $.getOpenedModal();
        var isModalVisible = (modal.length === 1);
        var isIdnrModal = (isModalVisible && (modal.is('#idnr')));
        var isPersonsModal = (isModalVisible && (modal.is('#personsModal')));

        if (e.shiftKey) {
            switch (e.which) {
                case 115: // F4
                    if (isIdnrModal) {
                        forIdnr();
                        e.preventDefault();
                    }
                    else if (!isModalVisible) {
                        forIdnrOpen();
                        e.preventDefault();
                    }
                    break;
                case 114: // F3
                    if (isPersonsModal) {
                        modal.modal('hide');
                        e.preventDefault();
                    }
                    else if (!isModalVisible) {
                        openModal($(':focus').closest('tr'));
                        e.preventDefault();
                    }
                    break;
            }
        }
        else if ((e.which === 27) && isModalVisible) { // Esc
            modal.modal('hide');
            e.preventDefault();
        }
    });
})(jQuery);