(function ($) {
    'use strict';

    function initializePlaatsPicklists(elem) {
        initializeAjaxPickLists(elem.find('.plaats'), 'gemnr', 'gemnaam',
            '/ajax/lookup/plaatsen', '/ajax/picklist/set/plaats', false);
    }

    function initializeBeroepPicklists(elem) {
        initializeAjaxPickLists(elem.find('.beroep'), null, 'berpnaam',
            '/ajax/lookup/beroepen', '/ajax/picklist/set/beroep', false);
    }

    function initializeRelatiePicklists(elem) {
        initializeAjaxPickLists(elem.find('.relatie'), 'relkode', 'relatie',
            '/ajax/lookup/relaties', '/ajax/picklist/set/relatie', false);
    }

    function initializeKindRelatiePicklists(elem) {
        initializeAjaxPickLists(elem.find('.kindrelatie'), null, 'relatie',
            '/ajax/lookup/kindrelaties', '/ajax/picklist/set/kindrelatie', false);
    }

    function initializeKerkGenootschapPicklists(elem) {
        initializeAjaxPickLists(elem.find('.kg'), null, 'kerkgeno',
            '/ajax/lookup/kerkgenootschappen', '/ajax/picklist/set/kerkgenootschap', false);
    }

    function initializeBurgStandPicklists(elem) {
        initializeSimplePickLists(elem.find('.burgstand'), [
            {id: 1, value: 'Ongehuwd'},
            {id: 2, value: 'Verweduwd'},
            {id: 3, value: 'Gescheiden'},
            {id: 5, value: 'Gehuwd'},
            {id: 6, value: 'Onbekend'},
            {id: 9, value: 'Onb -> huwelijk'}
        ]);
    }

    function initializeAdresTypePicklists(elem) {
        initializeAjaxPickLists(elem.find('.adrestype'), 'code', 'type', '/ajax/lookup/adrestypes', null, true);
    }

    function initializeAjaxPickLists(fields, idField, recordField, lookupURL, saveURL, showAllOptions) {
        initializePickLists(fields, idField, recordField, saveURL, showAllOptions, function (isId, query, withRecords) {
            var params = {keyword: query};
            if (isId) {
                var id = parseInt(query);
                if (!isNaN(id)) {
                    params.id = id;
                }
            }

            return $.getJSON(lookupURL, params, function (records) {
                return withRecords(records);
            });
        });
    }

    function initializeSimplePickLists(fields, allValues) {
        initializePickLists(fields, 'id', 'value', null, true, function (isId, query, withRecords) {
            var records = [];
            var id = parseInt(query);
            var substrRegex = new RegExp('^' + query, 'i');

            $.each(allValues, function (i, record) {
                if ((isId && !isNaN(id) && (record.id === id)) || substrRegex.test(record.value)) {
                    records.push(record);
                }
            });

            return withRecords(records);
        });
    }

    function initializePickLists(fields, idField, recordField, saveURL, showAllOptions, getRecords) {
        fields.typeahead('destroy');

        fields.each(function initializePickListForField(i, field) {
            field = $(field);

            var values = [];
            var recordsByLabel = {};
            var isId = field.hasClass('is-id');

            field.typeahead({
                minLength: showAllOptions ? 0 : 1,
                appendTo: $('body'),
                source: function pickListSource(query, process) {
                    return getRecords(isId, query, function (records) {
                        values = [];
                        recordsByLabel = {};

                        $.each(records, function (i, record) {
                            if (record !== null) {
                                var value = record[recordField];
                                if (isId) {
                                    value = record[idField] + ' ' + value;
                                }

                                if (!isId || (record[idField] !== null)) {
                                    values.push(value);
                                    recordsByLabel[value] = record;
                                }
                            }
                        });

                        return process(values);
                    });
                },
                updater: function pickListUpdater(item) {
                    // Should also trigger one of the key events
                    $(document).trigger('keydown').trigger('keypress').trigger('keyup');

                    var id = 0;
                    var recordOfItem = recordsByLabel[item];
                    if ((idField !== null) && (recordOfItem !== null)) {
                        id = recordOfItem[idField];
                    }

                    if (isId) {
                        field.attr('data-selected', id);

                        var label = (id !== 0) ? recordOfItem[recordField] : '';
                        field.nextAll('p.picklist-label').first().text(label);

                        return id;
                    }
                    else {
                        field.prev().filter('input[type=hidden]').val(id);
                        if (recordOfItem !== null) {
                            return recordOfItem[recordField];
                        }
                        return item;
                    }
                }
            });

            field.on('typeahead-change', function () {
                field.autoNextFocus(true);
            });

            if (isId) {
                field.keydown(function (e, data) {
                    var curValueId = field.val().trim();
                    var curSelectedId = field.attr('data-selected').trim();
                    if (curValueId !== curSelectedId) {
                        field.attr('data-selected', 0);
                    }
                });

                field.blur(function () {
                    if (field.attr('data-selected').trim() === '0') {
                        field.val('');
                        field.nextAll('p.picklist-label').first().html('&nbsp;');
                    }
                });
            }
            else if (saveURL !== null) {
                field.blur(function () {
                    var value = field.val().trim();
                    if (value.length > 0) {
                        $.ajax({
                            url: saveURL,
                            type: 'POST',
                            dataType: 'text',
                            data: {value: value}
                        });
                    }
                });
            }
        });
    }

    $.registerInit(function (elem) {
        initializePlaatsPicklists(elem);
        initializeBeroepPicklists(elem);
        initializeRelatiePicklists(elem);
        initializeKindRelatiePicklists(elem);
        initializeBurgStandPicklists(elem);
        initializeKerkGenootschapPicklists(elem);
        initializeAdresTypePicklists(elem);
    });
})(jQuery);
