(function ($) {
    var getVisibleCrudTableContainer = function () {
        return $('.crud-table-container:visible:first');
    };

    var getElems = function () {
        var parent = getVisibleCrudTableContainer();
        var onEdit = parent.find('.on-edit');
        return {
            parent: parent,
            table: parent.find('.crud-table'),
            onEdit: onEdit,
            onNoEdit: parent.find('.on-no-edit'),
            btnSaveNew: onEdit.find('.btn-save-new'),
            btnSaveUpdate: onEdit.find('.btn-save-update')
        };
    };

    var resetValues = function () {
        var elems = getElems();
        var inputElems = elems.onEdit.find('input').not('[type=button]');

        inputElems.filter('.integer-field').val(0);
        inputElems.not('.integer-field').val('');
        inputElems.filter('[data-selected]').attr('data-selected', 0);
        inputElems.blur();

        elems.onEdit.find('p.picklist-label').html('&nbsp;');
        elems.parent.find('.modalMessages').hide().empty();

        $(document).trigger('crud-table-reset', [elems]);
    };

    var getData = function (parentElem) {
        var data = {};
        parentElem.find(':input:visible, input[type=hidden]').not('[disabled],[type=button]').each(function () {
            var inputElem = $(this);
            data[inputElem.attr('name')] = inputElem.val();
        });
        return data;
    };

    var openOnEdit = function (self, isNew) {
        var elems = getElems();

        var row = self.closest('tr');
        row.addClass('rowToUpdate');

        isNew ? elems.btnSaveNew.show() : elems.btnSaveNew.hide();
        isNew ? elems.btnSaveUpdate.hide() : elems.btnSaveUpdate.show();

        elems.onNoEdit.hide();
        elems.onEdit.show();

        var valuesToSet = elems.onEdit.find(':input').not('[type=button]');
        valuesToSet.each(function () {
            var elem = $(this);

            var searchElem = row;
            var generalDataElem = elems.parent.find('.general-data');

            var searchValueElem = searchElem.find('.' + elem.attr('name'));
            var generalDataValueElem = generalDataElem.find('.' + elem.attr('name'));

            if (searchValueElem.length > 0 || generalDataValueElem.length > 0) {
                var value = (searchValueElem.length > 0) ? searchValueElem.text() : generalDataValueElem.text();
                elem.val(value);

                if (elem.is('[data-selected]')) {
                    elem.attr('data-selected', value);

                    var valueName = row.find('.' + elem.attr('name') + 'Name').text();
                    elem.nextAll('p.picklist-label').first().text(valueName);
                }
            }
        });

        elems.parent.find('.btn-update, .btn-delete').attr('disabled', 'disabled');
        elems.onEdit.find(':input').filter(':visible').not(':disabled').first().focus().keyup();

        if (isNew) {
            self.trigger('crud-table-new', [elems]);
        }
        else {
            self.trigger('crud-table-update', [elems]);
        }
    };

    var onCancel = function (self) {
        var elems = getElems();

        elems.onNoEdit.show();
        elems.onEdit.hide();

        elems.parent.find('.btn-update, .btn-delete').removeAttr('disabled');

        var row = elems.parent.find('tr.rowToUpdate');
        if (row.length > 0) {
            row.removeClass('rowToUpdate');
            row.find('.btn-update').first().focus();
        }
        else {
            elems.onNoEdit.find('.btn-new').first().focus();
        }

        resetValues();
        self.trigger('crud-table-cancel', [elems]);
    };

    var onDelete = function (self) {
        self.trigger('crud-table-delete', [getElems()]);
    };

    var onSave = function (self, isNew) {
        var elems = getElems();

        $.resetInvisibleFormElements();
        var data = getData(elems.onEdit);

        elems.onNoEdit.show();
        elems.onEdit.hide();
        resetValues();

        if (isNew) {
            self.trigger('crud-table-save-new', [elems, data]);
        }
        else {
            var row = elems.parent.find('tr.rowToUpdate');
            row.removeClass('rowToUpdate');

            self.trigger('crud-table-save-update', [elems, data]);
        }
    };

    var onAjaxSuccess = function (self, result) {
        var elems = getElems();
        var resultElem = $(result);
        var index = elems.table.find('.free tr').index(self.closest('tr'));

        elems.table.find('.ajax-updated').replaceWith(resultElem);
        $(document).trigger('ajax-update', [resultElem]);

        var row = elems.table.find('.free tr').eq(index);
        if (row.length > 0) {
            row.find('.btn-update:first').focus();
        }
        else {
            elems.parent.find('.btn-new:first').focus();
        }
    };

    $(document).keypress(function (e) {
        var char = String.fromCharCode(e.which);
        switch (char) {
            case 'b':
                var btnNew = getVisibleCrudTableContainer().find('.btn-new:visible:first');
                if (btnNew.length === 1) {
                    openOnEdit(btnNew, true);
                    e.preventDefault();
                }
                break;
            case 'c':
                var btnCor = getVisibleCrudTableContainer().find('tr.active .btn-update:enabled:first');
                if (btnCor.length === 1) {
                    openOnEdit(btnCor, false);
                    e.preventDefault();
                }
                break;
            case 'v':
                var btnDelete = getVisibleCrudTableContainer().find('tr.active .btn-delete:enabled:first');
                if (btnDelete.length === 1) {
                    onDelete(btnDelete);
                    e.preventDefault();
                }
                break;
        }
    });

    $(document).keydown(function (e) {
        switch (e.which) {
            case 27: // Esc
                var btnCancel = getVisibleCrudTableContainer().find('.btn-cancel:visible:first');
                if (btnCancel.length === 1) {
                    btnCancel.click();
                    e.preventDefault();
                }
                break;
        }
    });

    $(document).on('click', '.crud-table-container:visible:first .btn-new', function (e) {
        openOnEdit($(e.target), true);
    }).on('click', '.crud-table-container:visible:first .btn-update', function (e) {
        openOnEdit($(e.target), false);
    }).on('click', '.crud-table-container:visible:first .btn-cancel', function (e) {
        onCancel($(e.target));
    }).on('click', '.crud-table-container:visible:first .btn-delete', function (e) {
        onDelete($(e.target));
    }).on('click', '.crud-table-container:visible:first .btn-save-new', function (e) {
        onSave($(e.target), true);
    }).on('click', '.crud-table-container:visible:first .btn-save-update', function (e) {
        onSave($(e.target), false);
    }).on('crud-table-ajax-success', function (e, result) {
        onAjaxSuccess($(e.target), result);
    });

    $.registerInit(function (elem) {
        var tableScroll = function (e) {
            var self = $(e.target);
            var target = self.closest('.crud-table-container').find('.on-edit .free.scrollable');
            target.scrollTop(self.scrollTop());
            target.scrollLeft(self.scrollLeft());
        };

        var editScroll = function (e) {
            var self = $(e.target);
            var target = self.closest('.crud-table-container').find('.crud-table .free.scrollable');
            target.scrollTop(self.scrollTop());
            target.scrollLeft(self.scrollLeft());
        };

        elem.find('.free.scrollable').each(function () {
            var self = $(this);
            if (self.closest('.crud-table-container').length === 1) {
                if (self.closest('.crud-table').length === 1) {
                    self.scroll(tableScroll);
                }
                if (self.closest('.on-edit').length === 1) {
                    self.scroll(editScroll);
                }
            }
        });
    });
})(jQuery);