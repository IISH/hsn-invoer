(function ($) {
    'use strict';

    var onNew = function (self, elems) {
        var seqNr = elems.parent.find('.seqNr:last').getIntegerText() + 1;
        seqNr = isNaN(seqNr) ? 1 : seqNr;
        elems.onEdit.find('input[name=dynamicDataSequenceNumber]').val(seqNr);
    };

    var onUpdate = function (self, elems) {
        var seqNr = self.closest('tr').find('.seqNr').text();
        elems.btnSaveUpdate.data('seqnr', seqNr);
    };

    var onDelete = function (self) {
        $.ajax({
            type: 'POST',
            dataType: 'text',
            data: {
                _eventId: 'person-dynamic-remove',
                ajaxSource: 'btn-delete',
                person: self.data('person'),
                type: self.data('type'),
                seqNr: self.closest('tr').find('.seqNr').text()
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
                _eventId: 'person-dynamic-add',
                ajaxSource: 'btn-save-new',
                person: self.data('person'),
                type: self.data('type')
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
                _eventId: 'person-dynamic-update',
                ajaxSource: 'btn-save-update',
                person: self.data('person'),
                type: self.data('type'),
                seqNr: self.getIntegerDataValue('seqnr')
            }, data),
            success: function (result) {
                self.trigger('crud-table-ajax-success', [result]);
            }
        });
    };

    var onModalOpen = function () {
        var focusElem = $(':focus');
        var personDynamic = focusElem.closest('.personDynamic');
        if (personDynamic.length > 0) {
            var person = personDynamic.closest('tr');
            if (person.length === 0) {
                person = $('form:first');
            }

            person.resetInvisibleFormElements();
            var type = personDynamic.attr('data-type');
            var data = person.find('.form-elem').serialize() + '&_eventId=refresh-person-dynamics&ajaxSource=true&type=' + type;


            var rp = null;
            if (personDynamic.is('td')) {
                rp = person.data('rp');
                data += '&person=' + rp;
            }

            $.ajax({
                type: 'POST',
                dataType: 'text',
                data: data,
                success: function (result) {
                    var resultElem = $(result);
                    $('#b3Content' + type).replaceWith(resultElem);
                    $(document).trigger('ajax-update', [resultElem]);

                    var modal = $('.modal[data-type=' + type + ']');
                    modal.data('rp', rp);

                    if (rp !== null) {
                        modal.find($.getDataElemSelector('person')).data('person', rp);
                    }
                    modal.modal({keyboard: false, backdrop: 'static'});

                    if (!focusElem.hasClass('no-auto-new')) {
                        modal.find('.btn-new:first').click();
                    }
                }
            });
        }
    };

    var onModalClose = function () {
        var modal = $('.modal.in').first();

        var minRecords = modal.getIntegerDataValue('min-records');
        var maxRecords = modal.getIntegerDataValue('max-records');

        var numberOfRecords = modal.find('table span.seqNr').length;
        if (numberOfRecords < minRecords) {
            alert('Minimaal ' + minRecords + ' regels met dynamische gegevens is verplicht.');
        }
        else if ((maxRecords > 0) && (numberOfRecords > maxRecords)) {
            alert('Maximaal ' + maxRecords + ' regels met dynamische gegevens zijn toegestaan.');
        }
        else {
            modal.find('.btn-cancel').click();

            var rp = modal.data('rp');
            var data = {ajaxSource: 'ajax'};
            var target = $('#registrationAllLines').find('tr[data-rp=' + rp + ']');

            if (target.length === 1) {
                data._eventId = 'refresh-person';
                data.person = rp;
            }
            else {
                data._eventId = 'refresh-person-dynamic';
                data.type = modal.data('type');
                target = $('.personDynamic[data-type=' + data.type + ']');
            }

            $.ajax({
                type: 'POST',
                dataType: 'text',
                data: data,
                success: function (result) {
                    var resultElem = $(result);
                    if (rp) {
                        resultElem = resultElem.find('tr[data-rp=' + rp + ']');
                    }

                    target.replaceWith(resultElem);
                    $(document).trigger('ajax-update', [resultElem]);
                    modal.modal('hide');
                }
            });
        }
    };

    $(document).keydown(function (e) {
        var modal = $.getOpenedModal();
        var isModalVisible = (modal.length === 1);
        var isPopupVisible = ($('.popover.in').first().length === 1);
        var isPersonDynamicModal = (isModalVisible && (modal.hasClass('personDynamicModal')));

        switch (e.which) {
            case 115: // F4
                if (isPersonDynamicModal) {
                    onModalClose();
                    e.preventDefault();
                }
                else if (!isModalVisible && !isPopupVisible) {
                    onModalOpen();
                    e.preventDefault();
                }
                break;
        }
    });

    $(document).on('crud-table-new', function (e, elems) {
        onNew($(e.target), elems);
    }).on('crud-table-update', function (e, elems) {
        onUpdate($(e.target), elems);
    }).on('crud-table-delete', function (e, elems, data) {
        onDelete($(e.target), data);
    }).on('crud-table-save-new', function (e, elems, data) {
        onSaveNew($(e.target), data);
    }).on('crud-table-save-update', function (e, elems, data) {
        onSaveUpdate($(e.target), data);
    });
})(jQuery);