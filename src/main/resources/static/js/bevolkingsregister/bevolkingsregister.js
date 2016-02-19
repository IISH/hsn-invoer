(function ($) {
    'use strict';

    // TODO: Prevent unwanted focus by disabling timeout in Chrome for bevolkingsregister
    $.noTimeoutWithNav();
    $.dontCheckByz();

    /* Bevolkingsregister methods to be overloaded for specific cases */

    /**
     *  The default getCurPerson method with the default value of no current person.
     *  To be overloaded for specific cases on different pages in the bevolkingsregister flow.
     */
    $.getCurPerson = function () {
        return 0;
    };

    /**
     *  The default getCurPersonModal method with an empty jQuery object, as in: no modal found.
     *  To be overloaded for specific cases on different pages in the bevolkingsregister flow.
     */
    $.getCurPersonModal = function () {
        return $();
    };

    /**
     *  The default getCurPersonData method with an empty object, as in: no data found.
     *  To be overloaded for specific cases on different pages in the bevolkingsregister flow.
     */
    $.getCurPersonData = function () {
        return {};
    };

    /* Date checks */

    $.checkDateBevReg = function (hsnDate, elem) {
        var error = false;
        var parent = elem.getParentOfFormElement();

        var dayVal = hsnDate.day.getValue();
        var monthVal = hsnDate.month.getValue();
        var yearVal = hsnDate.year.getValue();

        var date = new Date(yearVal, monthVal - 1, dayVal);

        if (dayVal === 0 || dayVal < -3 || dayVal > 31) {
            error = true;
        }
        if (monthVal === 0 || monthVal < -3 || monthVal > 12) {
            error = true;
        }
        if (yearVal === 0 || yearVal < -3 || yearVal > 1960 || (yearVal > 0 && yearVal < 1750)) {
            error = true;
        }
        if (parent.hasClass('noNeg') && (dayVal <= 0 || monthVal <= 0 || yearVal <= 0)) { // basically 'required'
            error = true;
        }
        if (parent.hasClass('fullCheck') && (dayVal > 0 && monthVal > 0 && yearVal > 0)) {
            if (date.getDate() !== dayVal || date.getMonth() !== (monthVal - 1) || date.getFullYear() !== yearVal) {
                error = true;
            }
        }
        if (parent.hasClass('afterHfdDatum')) {
            var hfdDate = new Date(
                $('.year-hfd-datum:first').getIntegerText(),
                $('.month-hfd-datum:first').getIntegerText() - 1,
                $('.day-hfd-datum:first').getIntegerText()
            );
            if (hfdDate >= date) {
                error = true;
            }
        }

        return error;
    };

    var checkInterprDateBevReg = function (hsnDate) {
        var error = false;

        var dayVal = hsnDate.day.getValue();
        var monthVal = hsnDate.month.getValue();
        var yearVal = hsnDate.year.getValue();

        if (dayVal < -3 || dayVal > 31) {
            error = true;
        }
        if (monthVal < -3 || monthVal > 12) {
            error = true;
        }
        if (yearVal < -3 || yearVal > 1960 || (yearVal > 0 && yearVal < 1750)) {
            error = true;
        }
        if ((dayVal < 0 || monthVal < 0 || yearVal < 0) && !(dayVal === -3 && monthVal === -3 && yearVal === -3)) {
            error = true;
        }
        if (dayVal > 0 && monthVal > 0 && yearVal > 0) {
            var date = new Date(yearVal, monthVal - 1, dayVal);
            if (date.getDate() !== dayVal || date.getMonth() !== (monthVal - 1) || date.getFullYear() !== yearVal) {
                error = true;
            }
        }

        return error;
    };

    $.initCheckDate('.checkDateBevReg', $.prepareDate, $.checkDateBevReg);
    $.initCheckDate('.checkInterprDateBevReg', null, checkInterprDateBevReg);

    /* Bijzonderheden and overview modals */

    var onOverviewOpen = function () {
        var modal = $('.overviewModal:first');
        if (modal.length === 1) {
            $.get('/bevolkingsregister/overzicht/modal', {}, function (overviewTable) {
                modal.find('#overviewTable').replaceWith(overviewTable);
                modal.modal({keyboard: false, backdrop: 'static'});
            });
        }
    };

    var onOverviewClose = function () {
        $.getOpenedModal().modal('hide');
    };

    var onPersonByzOpen = function () {
        var modal = $.getCurPersonModal();
        if (modal.length === 1) {
            $.each($.getCurPersonData(), function (key, value) {
                modal.find('span.data-' + key).text(value);
            });
            modal.data('content', modal.find('textarea').val());
            modal.modal({keyboard: false, backdrop: 'static'});
        }
    };

    var onPersonByzClose = function (save) {
        var modal = $.getOpenedModal();
        if (save) {
            modal.trigger('person-byz-save', [$.getCurPerson()]);
        }
        else {
            modal.find('textarea').val(modal.data('content'));
        }
        modal.modal('hide');
    };

    var onRegistrationByzOpen = function () {
        var modal = $('.registrationModal');
        if (modal.length === 1) {
            modal.data('content', modal.find('textarea').val());
            modal.modal({keyboard: false, backdrop: 'static'});
        }
    };

    var onRegistrationByzClose = function (save) {
        var modal = $.getOpenedModal();
        if (!save) {
            modal.find('textarea').val(modal.data('content'));
        }
        modal.modal('hide');
    };

    $(document).on('click', '.btn-delete', function (e) {
        if (!confirm('Wilt u deze inschrijving verwijderen?')) {
            e.preventDefault();
            e.stopImmediatePropagation();
        }
    }).keydown(function (e) {
        var modal = $.getOpenedModal();
        var isModalVisible = (modal.length === 1);
        var isOverviewModal = (isModalVisible && (modal.hasClass('overviewModal')));
        var isPersonByzModal = (isModalVisible && (modal.hasClass('personModal')));
        var isRegistrationByzModal = (isModalVisible && (modal.hasClass('registrationModal')));

        switch (e.which) {
            case 27: // Esc
                if (isOverviewModal) {
                    onOverviewClose();
                    e.preventDefault();
                }
                else if (isRegistrationByzModal) {
                    onRegistrationByzClose(false);
                    e.preventDefault();
                }
                else if (isPersonByzModal) {
                    onPersonByzClose(false);
                    e.preventDefault();
                }
                break;
            case 113: // F2
                if (e.shiftKey) {
                    if (isOverviewModal) {
                        onOverviewClose();
                        e.preventDefault();
                    }
                    else if (!isModalVisible) {
                        onOverviewOpen();
                        e.preventDefault();
                    }
                }
                break;
            case 116: // F5
                if (isPersonByzModal) {
                    onPersonByzClose(true);
                    e.preventDefault();
                }
                else if (!isModalVisible) {
                    onPersonByzOpen();
                    e.preventDefault();
                }
                break;
            case 117: // F6
                if (isRegistrationByzModal) {
                    onRegistrationByzClose(true);
                    e.preventDefault();
                }
                else if (!isModalVisible) {
                    onRegistrationByzOpen();
                    e.preventDefault();
                }
                break;
        }
    });
})(jQuery);