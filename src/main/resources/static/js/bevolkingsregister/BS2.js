(function ($) {
    'use strict';

    /* Utility methods */

    var getActiveRow = function () {
        var readOnlyRow = $('#registrationAddresses').find('tr.active');
        if (readOnlyRow.length === 1) {
            return readOnlyRow;
        }
        return $('.on-edit .free tr:visible');
    };

    /* Overloading of methods */

    $.getCurPerson = function () {
        var person = 0;

        var personElem = getActiveRow().find('span.keyToRegistrationPersons, input[name=keyToRegistrationPersons]');
        if (personElem.length > 0) {
            if (personElem.is(':input')) {
                person = personElem.getIntegerValue();
            }
            else {
                person = personElem.getIntegerText();
            }
        }

        if (!isNaN(person)) {
            return person;
        }
        return 0;
    };

    $.getCurPersonModal = function () {
        return $('.personByzModals > [data-person=' + $.getCurPerson() + '] > .personModal:first');
    };

    $.getCurPersonData = function () {
        // Not necessary, data of persons cannot be changed in this screen
        return {};
    };

    /* BS2 specific operations */

    var determineSeqNr = function (blurPerson) {
        var row = getActiveRow();
        var personElem = row.find('.person');
        var seqNrElem = row.find('.seqNr');

        var person = personElem.getIntegerValue();
        var seqNr = seqNrElem.getIntegerValue();

        var nrOfPersons = $('#registrationAddresses').getIntegerDataValue('number-of-persons');
        if (isNaN(person) || (person < 0) || (person > nrOfPersons)) {
            person = 0;
            personElem.val(0);
        }

        var lastSeqNr = 0;
        $('#registrationAddressesTable').find('tbody tr').each(function () {
            var row = $(this);
            if (row.find('.person').getIntegerText() === person) {
                lastSeqNr = row.find('.seqNr').getIntegerText();
            }
        });

        var btnSaveUpdate = row.find('.btn-save-update');
        if (btnSaveUpdate.is(':visible')) {
            var orgPerson = btnSaveUpdate.getIntegerDataValue('person');
            var orgSeqNr = btnSaveUpdate.getIntegerDataValue('seqNr');
            if ((orgPerson !== person) && blurPerson) {
                seqNrElem.val(lastSeqNr + 1);
            }
            else if ((orgPerson === person) && (seqNr > orgSeqNr) && !blurPerson) {
                seqNrElem.val(orgSeqNr);
            }
            else if (seqNr > lastSeqNr) {
                seqNrElem.val(lastSeqNr + 1);
            }
        }
        else {
            if (blurPerson) {
                seqNrElem.val(lastSeqNr + 1);
            }
            else if (seqNr > lastSeqNr) {
                seqNrElem.val(lastSeqNr + 1);
            }
        }
    };

    var checkOrderOfDate = function () {
        var warningMsg = $('#dateOrderWarning');

        var row = getActiveRow();
        var person = row.find('.person').getIntegerValue();
        var seqNr = row.find('.seqNr').getIntegerValue();

        var hsnDate = row.find('.dateOrder').getHsnDate();
        if (hsnDate.day.getValue() > 0 && hsnDate.month.getValue() > 0 && hsnDate.year.getValue() > 0) {
            var lastHsnDate = null;
            $('#registrationAddressesTable').find('tbody tr').each(function () {
                var row = $(this);
                if ((row.find('.person').getIntegerText() === person) &&
                    (row.find('.seqNr').getIntegerText() < seqNr)) {
                    var foundHsnDate = row.find('.dateOrder').getHsnDate();
                    if (foundHsnDate.day.getValue() > 0 && foundHsnDate.month.getValue() > 0
                        && foundHsnDate.year.getValue() > 0) {
                        lastHsnDate = foundHsnDate;
                    }
                }
            });

            if (lastHsnDate !== null) {
                var date = new Date(hsnDate.year.getValue(), hsnDate.month.getValue() - 1, hsnDate.day.getValue());
                var lastDate = new Date(lastHsnDate.year.getValue(), lastHsnDate.month.getValue() - 1, lastHsnDate.day.getValue());

                if (date.getTime() < lastDate.getTime()) {
                    warningMsg.show();
                    return;
                }
            }
        }

        warningMsg.hide();
    };

    var copyFromLastLine = function () {
        var row = getActiveRow();
        var lastRow = $('#registrationAddresses').find('.free tbody tr:last-child');

        if (lastRow.length > 0) {
            var names = ['keyToRegistrationPersons', 'dayOfAddress', 'monthOfAddress', 'yearOfAddress', 'synchroneNumber'];
            $.each(names, function (i, name) {
                var value = lastRow.find('.' + name).text();
                row.find('[name=' + name + ']').val(value);
            });
            row.find('.adrestype').val('WK').attr('data-selected', 'WK');
        }

        determineSeqNr(true);
        $(document).trigger('changeOfState');
    };

    var onReset = function () {
        $('#dateOrderWarning').hide();
    };

    var onUpdate = function (self, elems) {
        var row = self.closest('tr');
        var person = row.find('.person').text();
        var seqNr = row.find('.seqNr').text();
        elems.btnSaveUpdate.data('seqNr', seqNr).data('person', person);
    };

    var onDelete = function (self) {
        var row = self.closest('tr');
        $.ajax({
            type: 'POST',
            dataType: 'text',
            data: {
                _eventId: 'registration-address-remove',
                ajaxSource: 'btn-delete',
                person: row.find('.keyToRegistrationPersons').text(),
                seqNr: row.find('.sequenceNumberToAddresses').text()
            },
            success: function (result) {
                self.trigger('crud-table-ajax-success', [result]);
            }
        });
    };

    var onSaveNew = function (self, data) {
        $.ajax({
            type: 'POST',
            dataType: 'text',
            data: $.extend({
                _eventId: 'registration-address-add',
                ajaxSource: 'btn-save-new',
                person: data.keyToRegistrationPersons,
                seqNr: data.sequenceNumberToAddresses
            }, data),
            success: function (result) {
                self.trigger('crud-table-ajax-success', [result]);
            }
        });
    };

    var onSaveUpdate = function (self, data) {
        $.ajax({
            type: 'POST',
            dataType: 'text',
            data: $.extend({
                _eventId: 'registration-address-update',
                ajaxSource: 'btn-save-update',
                person: self.data('person'),
                seqNr: self.data('seqNr')
            }, data),
            success: function (result) {
                self.trigger('crud-table-ajax-success', [result]);
            }
        });
    };

    /* Event registration */

    $(document).on('crud-table-reset', function (e) {
        onReset();
    }).on('crud-table-update', function (e, elems) {
        onUpdate($(e.target), elems);
    }).on('crud-table-delete', function (e, elems, data) {
        onDelete($(e.target), data);
    }).on('crud-table-save-new', function (e, elems, data) {
        onSaveNew($(e.target), data);
    }).on('crud-table-save-update', function (e, elems, data) {
        onSaveUpdate($(e.target), data);
    }).ready(function () {
        // Extend the width to create more space in case one enters all lines at once
        $('#main').addClass('extend-width');
    });

    $('input.person, input.seqNr').blur(function (e) {
        if ($(e.target).hasClass('person')) {
            determineSeqNr(true);
        }
        else {
            determineSeqNr(false);
        }
    });

    $('input[name=dayOfAddress], input[name=monthOfAddress], input[name=yearOfAddress]').blur(checkOrderOfDate);

    $('.on-edit :input').keydown(function (e) {
        if (e.which === 121) { // F10
            copyFromLastLine();
            e.preventDefault();
        }
    });
})(jQuery);