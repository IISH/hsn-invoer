(function ($) {
    'use strict';

    function getVisibleCrudTableContainer() {
        return $('.crud-table-container').filter(':visible').first();
    }

    function getElems() {
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
    }

    function resetValues() {
        var elems = getElems();
        var inputElems = elems.onEdit.find('input').not('[type=button]');

        inputElems.filter('.integer-field').val(0);
        inputElems.not('.integer-field').val('');
        inputElems.filter('[data-selected]').attr('data-selected', 0);

        elems.onEdit.find('p.picklist-label').html('&nbsp;');
        elems.parent.find('.modalMessages').hide().empty();

        $(document).trigger('crud-table-reset', [elems]);
    }

    function getData(parentElem) {
        var data = {};
        parentElem.find(':input:visible, input[type=hidden]').not('[disabled],[type=button]').each(function () {
            var inputElem = $(this);
            data[inputElem.attr('name')] = inputElem.val();
        });
        return data;
    }

    function openOnEdit(self, isNew) {
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
        elems.onEdit.trigger('show');
        elems.onEdit.find('input').filter(':enabled:visible').first().change().focus();

        if (isNew) {
            self.trigger('crud-table-new', [elems]);
        }
        else {
            self.trigger('crud-table-update', [elems]);
        }
    }

    function onCancel(self) {
        var elems = getElems();
        var isContinued = elems.parent.hasClass('continued');

        elems.onNoEdit.show();
        elems.onEdit.hide();

        elems.parent.find('.btn-update, .btn-delete').removeAttr('disabled');

        var row = elems.parent.find('tr.rowToUpdate');
        if (row.length > 0) {
            row.removeClass('rowToUpdate');
            row.find('.btn-update:first').focus();
        }
        else if (isContinued) {
            elems.onNoEdit.find('.btn-new:first').getNextFormElement().focus();
        }
        else {
            elems.onNoEdit.find('.btn-new:first').focus();
        }

        resetValues();
        self.trigger('crud-table-cancel', [elems]);
    }

    function onDelete(self) {
        self.trigger('crud-table-delete', [getElems()]);
    }

    function onSave(self, isNew) {
        var elems = getElems();

        elems.onEdit.resetInvisibleFormElements();
        var data = getData(elems.onEdit);

        elems.onNoEdit.show();
        elems.onEdit.hide();
        resetValues();

        if (isNew) {
            self.trigger('crud-table-save-new', [elems, data, function () {
                if (elems.parent.hasClass('continued')) {
                    openOnEdit(elems.parent.find('.btn-new'), true);
                }
            }]);
        }
        else {
            self.trigger('crud-table-save-update', [elems, data]);
        }
    }

    function onAjaxSuccess(self, result, cb) {
        var elems = getElems();
        var resultElem = $(result);
        var index = elems.table.find('.free tr').index(elems.table.find('.free tr.rowToUpdate'));

        elems.table.find('.ajax-updated').replaceWith(resultElem);
        $(document).trigger('ajax-update', [resultElem]);

        if (!elems.onEdit.is(':visible')) {
            var row = elems.table.find('.free tr').eq(index);
            if (row.length > 0) {
                row.find('.btn-update:first').focus();
            }
            else {
                elems.parent.find('.btn-new:first').focus();
            }
        }

        if ($.isFunction(cb)) {
            cb();
        }
    }

    $(document).keypress(function (e) {
        var char = String.fromCharCode(e.which);
        switch (char) {
            case 'b':
                var btnNew = getVisibleCrudTableContainer().find('.btn-new').filter(':visible:enabled').first();
                if (btnNew.length === 1) {
                    openOnEdit(btnNew, true);
                    e.preventDefault();
                }
                break;
            case 'c':
                var btnCor = getVisibleCrudTableContainer().find('tr.active .btn-update').filter(':enabled').first();
                if (btnCor.length === 1) {
                    openOnEdit(btnCor, false);
                    e.preventDefault();
                }
                break;
            case 'v':
                var btnDelete = getVisibleCrudTableContainer().find('tr.active .btn-delete').filter(':enabled').first();
                if (btnDelete.length === 1) {
                    onDelete(btnDelete);
                    e.preventDefault();
                }
                break;
        }
    }).keydown(function (e) {
        switch (e.which) {
            case 27: // Esc
                var btnCancel = getVisibleCrudTableContainer().find('.btn-cancel').filter(':visible').first();
                if (btnCancel.length === 1) {
                    btnCancel.click();
                    e.preventDefault();
                }
                break;
        }
    }).on('click', 'button', function (e) {
        var elem = $(e.target);
        var container = elem.closest('.crud-table-container');
        if ((container.length > 0) && (container.is(':visible'))) {
            if (elem.hasClass('btn-new')) {
                openOnEdit(elem, true);
            }

            if (elem.hasClass('btn-update')) {
                openOnEdit(elem, false);
            }

            if (elem.hasClass('btn-cancel')) {
                onCancel(elem);
            }

            if (elem.hasClass('btn-delete')) {
                onDelete(elem);
            }

            if (elem.hasClass('btn-save-new')) {
                onSave(elem, true);
            }

            if (elem.hasClass('btn-save-update')) {
                onSave(elem, false);
            }
        }
    }).on('crud-table-ajax-success', function (e, result, cb) {
        onAjaxSuccess($(e.target), result, cb);
    }).ready(function () {
        var elem = getVisibleCrudTableContainer();
        if (elem.hasClass('continued')) {
            openOnEdit(elem.find('.btn-new'), true);
        }
    });

    $.registerInit(function (elem) {
        var syncScroll = function (self, target) {
            target.scrollTop(self.scrollTop());
            target.scrollLeft(self.scrollLeft());
        };

        elem.find('.free.scrollable').each(function () {
            var self = $(this);
            if (self.closest('.crud-table-container').length === 1) {
                if (self.closest('.crud-table').length === 1) {
                    self.scroll(function () {
                        syncScroll(self, self.closest('.crud-table-container').find('.on-edit .free.scrollable'));
                    });
                }
                var onEdit = self.closest('.on-edit');
                if (onEdit.length === 1) {
                    var other = self.closest('.crud-table-container').find('.crud-table .free.scrollable');
                    self.scroll(function () {
                        syncScroll(self, other);
                    });
                    onEdit.on('show', function () {
                        syncScroll(self, other);
                    });
                }
            }
        });
    });
})(jQuery);