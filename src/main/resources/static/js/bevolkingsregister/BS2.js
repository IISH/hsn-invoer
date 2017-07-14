(function ($) {
    'use strict';

    /* Utility methods */

    function getActiveRow() {
        var readOnlyRow = $('#registrationAddresses').find('tr.active');
        if (readOnlyRow.length === 1) {
            return readOnlyRow;
        }
        return $('.on-edit .free tr:visible');
    }

    /* Overloading of methods */

    $.getCurPerson = function getCurPerson() {
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

    $.getCurPersonModal = function getCurPersonModal() {
        return $('.personByzModals > [data-person=' + $.getCurPerson() + '] > .personModal:first');
    };

    $.getCurPersonData = function getCurPersonData() {
        // Not necessary, data of persons cannot be changed in this screen
        return {};
    };

    /* BS2 specific operations */

    function determineSeqNr(blurPerson) {
        var row = getActiveRow();
        var personElem = row.find('.person');
        var seqNrElem = row.find('.seqNr');

        var person = personElem.getIntegerValue();
        var seqNr = seqNrElem.getIntegerValue();

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
            else if ((orgPerson === person) && (seqNr > orgSeqNr) && !blurPerson && ((seqNr === 0) || isNaN(seqNr))) {
                seqNrElem.val(orgSeqNr);
            }
            else if (seqNr > lastSeqNr) {
                seqNrElem.val(lastSeqNr);
            }
        }
        else if (blurPerson || (seqNr > lastSeqNr)) {
            seqNrElem.val(lastSeqNr + 1);
        }

        if (blurPerson && btnSaveUpdate.is(':hidden')) {
            seqNrElem.autoNextFocus(false);
        }
    }

    function checkOrderOfDate() {
        var error = false;
        var lastHsnDate = null, lastPerson = null;

        $('#registrationAddressesTable').find('tbody tr').each(function () {
            if (error) return;

            var row = $(this);
            var foundHsnDate = row.find('.dateOrder').getHsnDate();
            var foundPerson = row.find('.person').getIntegerText();

            if (foundPerson !== lastPerson) {
                lastHsnDate = null;
                lastPerson = foundPerson;
            }

            if (foundHsnDate.day.getValue() > 0 && foundHsnDate.month.getValue() > 0
                && foundHsnDate.year.getValue() > 0) {
                if (lastHsnDate !== null) {
                    var date = new Date(foundHsnDate.year.getValue(), foundHsnDate.month.getValue() - 1, foundHsnDate.day.getValue());
                    var lastDate = new Date(lastHsnDate.year.getValue(), lastHsnDate.month.getValue() - 1, lastHsnDate.day.getValue());
                    error = date.getTime() < lastDate.getTime();
                }

                lastHsnDate = foundHsnDate;
            }
        });

        $.setError(error, 'date-order', 'Datum is niet op chronologische volgorde.', undefined, false);
        $.triggerChangeOfState();
    }

    function copyFromLastLine() {
        var row = getActiveRow();
        var lastRow = $('#registrationAddresses').find('.free tbody tr:last-child');

        if (lastRow.length > 0) {
            var names = ['keyToRegistrationPersons', 'dayOfAddress', 'monthOfAddress', 'yearOfAddress',
                'synchroneNumber', 'addressType', 'nameOfStreet', 'number', 'additionToNumber'];
            $.each(names, function (i, name) {
                var value = lastRow.find('.' + name).text();

                if (name === 'addressType') {
                    row.find('.adrestype').val(value).attr('data-selected', value);
                }
                else {
                    row.find('[name=' + name + ']').val(value);
                }
            });
            row.trigger('show');
        }

        determineSeqNr(true);
        $.triggerChangeOfState();
    }

    function onReset() {
        $('#dateOrderWarning').hide();
    }

    function onNew(self, elems) {
        var person = elems.onEdit.find('.person');
        var seqNr = elems.onEdit.find('.seqNr');

        person.val(0).blur();
        seqNr.val('');
        person.focus().setCaret(0);
    }

    function onUpdate(self, elems) {
        var row = self.closest('tr');
        var person = row.find('.person').text();
        var seqNr = row.find('.seqNr').text();
        elems.btnSaveUpdate.data('seqNr', seqNr).data('person', person);
    }

    function onDelete(self) {
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
    }

    function onSaveNew(self, data, cb) {
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
                self.trigger('crud-table-ajax-success', [result, cb]);
                checkOrderOfDate();
            }
        });
    }

    function onSaveUpdate(self, data) {
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
                checkOrderOfDate();
            }
        });
    }

    /* Event registration */

    $(document).on('crud-table-reset', function (e) {
        onReset();
    }).on('crud-table-new', function (e, elems) {
        onNew($(e.target), elems);
    }).on('crud-table-update', function (e, elems) {
        onUpdate($(e.target), elems);
    }).on('crud-table-delete', function (e, elems, data) {
        onDelete($(e.target), data);
    }).on('crud-table-save-new', function (e, elems, data, cb) {
        onSaveNew($(e.target), data, cb);
    }).on('crud-table-save-update', function (e, elems, data) {
        onSaveUpdate($(e.target), data);
    }).on('person-byz-save', function (e, person) {
        var serialized = $(e.target).find('textarea').serialize();
        var data = serialized + '&_eventId=register-person&ajaxSource=true&person=' + person;
        $.ajax({type: 'POST', data: data});
    }).on('click', '.btn-stop', function (e) {
        if (!confirm('U wilt stoppen, dat betekent dat alle ingevoerde gegevens zullen verdwijnen!')) {
            e.preventDefault();
            e.stopImmediatePropagation();
        }
    }).ready(function () {
        // Extend the width to create more space in case one enters all lines at once
        $('#main').addClass('extend-width');
    });

    $('input.person, input.seqNr').blur(function (e) {
        if ($(e.target).hasClass('person')) {
            var person = $(e.target).getIntegerValue();
            var nrOfPersons = $('#registrationAddresses').getIntegerDataValue('number-of-persons');
            $.setError(
                (isNaN(person) || (person < 0) || (person > nrOfPersons) || (person === 1 && nrOfPersons === 1)),
                'max-persons',
                'Er zijn ' + nrOfPersons + ' personen ingevoerd, dus persoon ' + person + ' is niet toegestaan!'
            );

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

    $('.btn-next').click(function (e) {
        if ($('table span.seqNr').length === 0) {
            if (!confirm('Er zijn geen addressen opgeslagen, weet u zeker dat dit correct is? ' +
                    'Zo ja, dan wordt er automatisch een leeg adres toegevoegd!')) {
                e.preventDefault();
            }
        }
    });
})(jQuery);