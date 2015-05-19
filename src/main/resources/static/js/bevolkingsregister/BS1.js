(function ($) {
    /* Utility methods */

    var isAllLines = function () {
        return ($('#registrationAllLines').length === 1);
    };

    var getActiveRow = function () {
        return $('#registrationAllLines').find('tr.active');
    };

    /* Overloading of methods */

    $.getCurPerson = function () {
        var person;
        if (isAllLines()) {
            person = getActiveRow().getIntegerDataValue('rp');
        }
        else {
            person = ('#curPerson').getIntegerValue();
        }

        if (!isNaN(person)) {
            return person;
        }
        return 0;
    };

    $.getCurPersonModal = function () {
        if (isAllLines()) {
            return getActiveRow().find('.personModal:first');
        }
        else {
            return $('.personModal:first');
        }
    };

    $.getCurPersonData = function () {
        // First check in the case of screen BS1 where all lines are entered at once
        var activeRow = getActiveRow();
        if (activeRow.length > 0) {
            return {
                firstName: activeRow.find('.firstName:first').val(),
                familyName: activeRow.find('.familyName:first').val(),
                dayOfBirth: activeRow.find('.geb-date-person .day:first').val(),
                monthOfBirth: activeRow.find('.geb-date-person .month:first').val(),
                yearOfBirth: activeRow.find('.geb-date-person .year:first').val(),
                placeOfBirth: activeRow.find('.placeOfBirth:first').val()
            }
        }

        // Otherwise this is the case where linea are added one by one
        return {
            firstName: $('#curB2\\.firstName').val(),
            familyName: $('#curB2\\.familyName').val(),
            dayOfBirth: $('#curB2\\.dayOfBirth').val(),
            monthOfBirth: $('#curB2\\.monthOfBirth').val(),
            yearOfBirth: $('#curB2\\.yearOfBirth').val(),
            placeOfBirth: $('#curB2\\.placeOfBirth').val()
        };
    };

    /* BS1 specific operations */

    // TODO: eDayInsValidate
    var registerDateOfRegistration = function (elem) {
        elem.find('.datum-inschrijving').each(function () {
            var self = $(this);
            var toggleField = self.getDataValueAsElem('show-when');

            var day = self.find('.day');
            var month = self.find('.month');
            var year = self.find('.year');

            var onToggle = function () {
                if (self.is(':hidden') && toggleField.val() === 'j') {
                    day.val('');
                    month.val('');
                    year.val('');
                }
                else if (self.is(':visible') && toggleField.val() !== 'j') {
                    day.val(-1);
                    month.val(-1);
                    year.val(-1);
                }
            };

            toggleField.keyup(onToggle);
            day.blur(function () {
                if ($(this).getIntegerValue() === 0) {
                    toggleField.val('n');
                    onToggle();
                }
            });

            var onBlur = function () {
                if (day.getIntegerValue() === -1 && month.getIntegerValue() === -1 && year.getIntegerValue() === -1) {
                    toggleField.val('n').focus();
                    onToggle();
                }
            };

            day.blur(onBlur);
            month.blur(onBlur);
            year.blur(onBlur);
        });
    };

    var registerRelatie = function (elem) {
        var elems = elem;
        if (!elem.is('tr') && !elem.hasClass('relatieElems')) {
            elems = elem.find('.relatieElems, #registrationAllLines tr');
        }

        elems.each(function () {
            var container = $(this);
            var relatie = container.find('.relatie');
            var parent = relatie.getParentOfFormElement();

            // Prevent multiple popovers on the relatie elem when either the 'F4' or the 'F9' popover was selected
            var f4, f9 = false;
            var focusPopover = false;

            var relatieRegel = container.find('.relatie-regel');
            var dynamicData2 = container.find('.dynamicData2');

            var ind = container.find('.ind').first();
            var sex = container.find('.sex').first();

            var relatieRegelPopup = parent.find('.relatieRegelPopup').first();
            var relatieDateHoofdPopup = parent.find('.relatieDateHoofdPopup').first();
            var relatieRegelInterprPopup = parent.find('.relatieRegelInterprPopup').first();

            // Init popover for relation element
            relatie.popover({
                content: relatieRegelPopup.html(),
                html: true,
                placement: 'bottom',
                trigger: 'manual'
            });

            // Synchronizes the relatie regel
            var setRelatieRegel = function (value) {
                if ((value !== undefined) && !isNaN(value)) {
                    if (value === 0) {
                        value = -1;
                    }

                    relatieRegel.val(value);
                    (value > 0) ? ind.show() : ind.hide();
                }
            };

            // TODO: eDayInsValidate
            // Encodes the datum expliciet hoofd and synchronizes the value
            var onDatumExplicietHoofd = function (dayVal, monthVal, yearVal) {
                var value = '';
                if (!isNaN(dayVal) && !isNaN(monthVal) && !isNaN(yearVal)) {
                    value = '###$' + ('0' + dayVal).slice(-2) + '/' + ('0' + monthVal).slice(-2) + '/' + yearVal;
                }
                dynamicData2.val(value);
            };

            // Hides the popover and performs the related checks and procedures
            var hidePopover = function (popover, checkError) {
                // Check for errors first, if necessary
                if (!checkError || (popover.find('.has-error').length === 0)) {
                    var focusOnRelatieElem = false; // In some cases, the focus has to go back to the relatie elem

                    // In case of an 'expliciet hoofd', we have to safely store and transform the date before closing
                    if (popover.find('.relatieDateHoofdPopupContent').length > 0) {
                        var day = parent.find('.relatieDateHoofd.day');
                        var month = parent.find('.relatieDateHoofd.month');
                        var year = parent.find('.relatieDateHoofd.year');

                        day.val(popover.find('.day').val());
                        month.val(popover.find('.month').val());
                        year.val(popover.find('.year').val());

                        onDatumExplicietHoofd(day.getIntegerValue(), month.getIntegerValue(), year.getIntegerValue());
                    }
                    // In case of an 'relatie regel' and 'kode' safely store the values
                    // and synchronize the 'regel' with the other 'regel' input elems
                    else if (popover.find('.relatieRegelInterprPopupContent').length > 0) {
                        setRelatieRegel(popover.find('.regel').getIntegerValue());
                        parent.find('.relatieRegelInterpr.kode').val(popover.find('.kode').val());
                    }
                    // Last case is the 'relatie regel' only case,
                    // so safely store and synchronize with the other 'regel' input elems
                    else {
                        f9 = false;
                        focusOnRelatieElem = true;
                        setRelatieRegel(popover.find('input').getIntegerValue());
                    }

                    focusPopover = false;
                    relatie.popover('hide');

                    if (focusOnRelatieElem) {
                        relatie.focus();
                    }
                }
            };

            // Determine what has to happen after a relation has been chosen
            var onRelationChosen = function (isInit) {
                // If the blur was caused by pressing 'F9' do nothing yet
                if (f4 || f9) {
                    return;
                }

                // Does the chosen relation require an 'expliciet hoofd' value?
                var person = container.find($.getDataElemSelector('person')).getIntegerDataValue('person');
                if (!isInit && (relatieDateHoofdPopup.length > 0) && (person > 1) && (relatie.getIntegerValue() === 1)) {
                    relatieDateHoofdPopup.find('.day').attr('value', parent.find('.relatieDateHoofd.day').val());
                    relatieDateHoofdPopup.find('.month').attr('value', parent.find('.relatieDateHoofd.month').val());
                    relatieDateHoofdPopup.find('.year').attr('value', parent.find('.relatieDateHoofd.year').val());

                    relatie.data('bs.popover').options.content = relatieDateHoofdPopup.html();
                    relatie.popover('show');
                }
                else {
                    var dateHoofd = container.find('.dateHoofd');
                    if ((person > 1) && (relatie.getIntegerValue() === 1)) {
                        dateHoofd.show();
                    }
                    else {
                        dateHoofd.hide();
                    }
                }

                // Does the chosen relation require an 'regel relatie' value?
                var shouldShow = [30, 40, 51, 52].indexOf(relatie.getIntegerValue()) >= 0;
                if (!isInit && (relatieRegelInterprPopup.length > 0) && shouldShow) {
                    relatieRegelInterprPopup.find('.regel').attr('value', parent.find('.relatieRegelInterpr.regel').val());
                    relatieRegelInterprPopup.find('.kode').attr('value', parent.find('.relatieRegelInterpr.kode').val());

                    relatie.data('bs.popover').options.content = relatieRegelInterprPopup.html();
                    relatie.popover('show');
                }
                else {
                    var regelInterpr = container.find('.regelInterpr');
                    if (shouldShow) {
                        regelInterpr.show();
                    }
                    else {
                        regelInterpr.hide();
                    }
                }

                // Now validate the relation
                var relVal = relatie.getIntegerValue();
                if (relatie.length > 0) {
                    if ((container.hasClass('only-head')) || (container.find('.only-head').length > 0)) {
                        $.setError(
                                (relVal != -3) && (relVal != 1),
                                'rel-only-head-' + relatie.attr('id'),
                            'Relatiecode moet 1 of -3 zijn'
                        );
                    }

                    /* TODO: var nrHeads = 0;
                     if (isAllLines()) {
                     $('.relatie:visible').each(function () {
                     if ($(this).getIntegerValue() == 1) {
                     nrHeads++;
                     }
                     });
                     }
                     else {
                     nrHeads = container.getIntegerDataValue('nr-heads');
                     }

                     $.setError(nrHeads > 2, 'nr-of-heads', 'Slechts een expliciet hoofd toegestaan');*/
                }
            };

            // If the user wants to close the popover, determine whether this is allowed
            // and what has to happen first by calling 'hidePopover'
            $(document).on('focus', 'input', function (e) {
                var popover = parent.find('.popover');
                if ((popover.length > 0) && $.contains(popover[0], e.target)) {
                    focusPopover = true;
                }
                else if (focusPopover) {
                    hidePopover(parent.find('.popover'), true);
                }
            });

            // Bind the 'F9' key to open the 'relatie regel' popover or to close the opened relatie popover
            parent.keydown(function (e) {
                if (e.which === 120) { // F9
                    var popover = parent.find('.popover');
                    if (popover.is(':visible')) {
                        hidePopover(popover, true);
                        relatie.focus();
                    }
                    else {
                        relatieRegelPopup.find(':input').attr('value', relatieRegel.val());
                        relatie.data('bs.popover').options.content = relatieRegelPopup.html();
                        relatie.popover('show');

                        f9 = true;
                        parent.find('.popover :input:first').focus();
                    }
                }

                if (e.which === 115) { // F4
                    f4 = true;
                }
            });

            // Synchronize the value with the other 'regel' input elems
            relatieRegel.blur(function () {
                setRelatieRegel($(this).getIntegerValue());
            });

            // Synchronize the value dynamicData2
            dynamicData2.blur(function () {
                dynamicData2.val($(this).val());
            });

            // Update the datum expliciet hoofd when changes are made
            container.on('blur', $.createDateSelector('.datum-expliciet-hoofd'), function (e) {
                var hsnDate = $(e.target).getParentOfFormElement().getHsnDate();
                onDatumExplicietHoofd(hsnDate.day.getValue(), hsnDate.month.getValue(), hsnDate.year.getValue());
            });

            // Validate the relation based on the given sex and the other way around
            container.on('blur', '.relatie, .sex', function () {
                var rel = relatie.getIntegerValue();
                var sexVal = sex.is(':input') ? sex.val() : sex.text();

                var error = false;
                var message = '';

                var female = [2, 4, 6, 9, 40, 52, 54, 55, 56, 82, 84, 86, 88];
                var male = [3, 5, 8, 30, 51, 53, 57, 58, 81, 83, 85, 87];

                if ((sexVal === 'm') && ((female.indexOf(rel) >= 0) || (rel > 19 && rel < 29) || (rel > 69 && rel < 80))) {
                    error = true;
                    message = 'Geslacht is mannelijk en relatiekode is vrouwelijk van aard!';
                }
                else if ((sexVal === 'v') && ((male.indexOf(rel) >= 0) || (rel > 9 && rel < 19) || (rel > 59 && rel < 70))) {
                    error = true;
                    message = 'Geslacht is vrouwelijk en relatiekode is mannelijk van aard!';
                }

                $.setError(error, 'relatie-geslacht-' + relatie.attr('id'), message);
            });

            // Always close the popover if the focus is back on the relation input box, even in case of errors
            parent.on('focus', '.relatie', function () {
                hidePopover(parent.find('.popover'), false);
            });

            // Determine what has to happen after a relation has been chosen
            parent.on('blur', '.relatie', function () {
                onRelationChosen(false);
            });

            // If the focus is back on the container, it must indicate that the person dynamic window (f4) is closed
            container.focus(function () {
                f4 = false;
            });

            onRelationChosen(true);
        });
    };

    var registerVolgendeInschrijvingPopup = function (elem) {
        elem.find('.geb-date-person').each(function () {
            var gebDatePerson = $(this);
            var volgendeInschrijving = gebDatePerson.find('.volgende-inschrijving').first();
            var popup = gebDatePerson.find('.volgendeInschrijvingOpPopup').first();

            var dayPerson = gebDatePerson.find('.day');
            var monthPerson = gebDatePerson.find('.month');
            var yearPerson = gebDatePerson.find('.year');

            yearPerson.popover({
                content: popup.html(),
                html: true,
                placement: 'bottom',
                trigger: 'manual'
            });

            gebDatePerson.on('blur', '.popover input', function (e) {
                if ($(e.target).val() === 'j') {
                    volgendeInschrijving.val(5); // Second OP
                }
                else {
                    volgendeInschrijving.val(2); // No OP
                }
                yearPerson.popover('hide');
            });

            var onBlur = function () {
                var dayPersonVal = dayPerson.getIntegerValue();
                var monthPersonVal = monthPerson.getIntegerValue();
                var yearPersonVal = yearPerson.getIntegerValue();

                if (!gebDatePerson.data('is-op') && gebDatePerson.is('[data-op-geb-day]')) {
                    var dayOpVal = parseInt(gebDatePerson.attr('data-op-geb-day'));
                    var monthOpVal = parseInt(gebDatePerson.attr('data-op-geb-month'));
                    var yearOpVal = parseInt(gebDatePerson.attr('data-op-geb-year'));

                    if (dayPersonVal === dayOpVal && monthPersonVal === monthOpVal && yearPersonVal === yearOpVal) {
                        yearPerson.popover('show');
                    }
                    else {
                        volgendeInschrijving.val(2); // No OP
                    }
                }
                else if (gebDatePerson.data('is-op')) {
                    $('[data-op-geb-day]').attr('data-op-geb-day', dayPersonVal);
                    $('[data-op-geb-month]').attr('data-op-geb-month', monthPersonVal);
                    $('[data-op-geb-year]').attr('data-op-geb-year', yearPersonVal);
                }
            };

            dayPerson.blur(onBlur);
            monthPerson.blur(onBlur);
            yearPerson.blur(onBlur);
        });
    };

    var registerBurgStandCheck = function (elem) {
        if ($('#currentPerson').data('is-burg-stand-rel-fix')) {
            elem.find(':input').not('.burg-stand-relatie, .btn-next, .modal button').attr('disabled', 'disabled');
        }

        elem.find('.burg-stand-relatie').each(function () {
            var self = $(this);

            var onBlur = function () {
                var relatie = self.getIntegerValue();
                var curPerson = $('#currentPerson');

                var numberOfLines = 0;
                if (isAllLines()) {
                    numberOfLines = $('#registrationAllLines').find('tr[data-rp]:last').getIntegerDataValue('rp');
                }
                else if (curPerson.data('is-burg-stand-rel-fix')) {
                    numberOfLines = curPerson.getIntegerDataValue('nr-persons');
                }

                $.setError(
                        !isNaN(numberOfLines) && (numberOfLines > 0) && (relatie > 0) && (relatie > numberOfLines),
                    'burg-stand',
                        'Een relatie met regelnummer ' + relatie + ' is onmogelijk.'
                );
                $(document).trigger('changeOfState');
            };

            onBlur();
            self.blur(onBlur);
        });
    };

    var setNationality = function (elem) {
        if (elem.val().trim().toUpperCase() === 'NL') {
            elem.val('Nederlandse');
        }
    };

    var checkLegalPlaceOfLivingInCodes = function (elem) {
        var value = elem.val().trim().toLowerCase();
        var validValues = ['w', 'v', 'n', 'vw', 'wv'];
        elem.hasErrorWhen(validValues.indexOf(value) < 0);
    };

    var updateNumberOfLines = function () {
        var table = $('#registrationAllLines');
        var fixedLeftTable = table.closest('.fixed-left-column').find('.fixed table');

        var currentNrOfLines = table.find('tr[data-rp]:last').getIntegerDataValue('rp');
        if (currentNrOfLines !== undefined && currentNrOfLines !== null) {
            var nrOfLines = parseInt(prompt('Regelnummers:', currentNrOfLines));
            if (isNaN(nrOfLines)) {
                return;
            }

            $.ajax({
                type: 'POST',
                dataType: 'text',
                data: {
                    _eventId: 'update-number-of-persons',
                    ajaxSource: true,
                    nrOfLines: nrOfLines
                },
                success: function (result) {
                    var resultElem = $(result);
                    if (nrOfLines > currentNrOfLines) {
                        for (var i = currentNrOfLines + 1; i <= nrOfLines; i++) {
                            var row = resultElem.find('tr[data-rp=' + i + ']');
                            table.find('tr:last').after(row);
                            fixedLeftTable.find('tr:last').after('<tr><td><span></span></td></tr>');
                            $(document).trigger('ajax-update', [row]);
                        }
                    }
                    else if (nrOfLines < currentNrOfLines) {
                        for (var i = currentNrOfLines; i > nrOfLines; i--) {
                            table.find('tr[data-rp=' + i + ']').remove();
                            fixedLeftTable.find('tr:last').remove();
                        }
                    }

                    var focusElem = $(':input:focus');
                    if (focusElem.length === 0) {
                        table.find('tr:last input:first').focus();
                    }
                },
                error: function () {
                    // TODO: Now it is an assumption that the RP would otherwise have been deleted
                    alert('U kunt de regel van de OP en voorgaande niet verwijderen.');
                }
            });
        }
    };

    var copyLine = function () {
        var person = $.getCurPerson();
        if (isNaN(person) || (person === 0)) {
            return;
        }

        var copyLine = parseInt(prompt('Kopieren gegevens uit regel:'));
        if (isNaN(copyLine)) {
            return;
        }
        if (copyLine === person) {
            alert('Hetzelfde regelnummer.');
            return;
        }
        if (!confirm('Bent u zeker dat u gegevens wilt kopieren?')) {
            return;
        }

        $.ajax({
            type: 'POST',
            dataType: 'text',
            data: {
                _eventId: 'copy-line',
                ajaxSource: true,
                person: person,
                copyLine: copyLine
            },
            success: function (result) {
                var target;
                var resultElem = $(result);

                var table = $('#registrationAllLines');
                if (table.length > 0) {
                    target = table.find('tr[data-rp=' + person + ']');
                    resultElem = resultElem.find('tr[data-rp=' + person + ']');
                }
                else {
                    target = $('#currentPerson');
                }

                var focusElemId = $(':focus').attr('id');
                target.replaceWith(resultElem);
                $(document).trigger('ajax-update', [resultElem]);
                $(document.getElementById(focusElemId)).focus();
            },
            error: function () {
                // TODO: Now it is an assumption that the line is not valid
                alert('Regel niet aanwezig.');
            }
        });
    };

    var copyFromPrevLine = function () {
        var row = getActiveRow();
        if (!$.isCorrection() && (row.data('copy-prev-person') !== 'copy-prev-person')) {
            var prevRow = row.prev();
            if (prevRow.is('tr')) {
                row.find('.has-inschrijving').val(prevRow.find('.has-inschrijving').val());
                row.find('.datum-inschrijving .day').val(prevRow.find('.datum-inschrijving .day').val());
                row.find('.datum-inschrijving .month').val(prevRow.find('.datum-inschrijving .month').val());
                row.find('.datum-inschrijving .year').val(prevRow.find('.datum-inschrijving .year').val());

                var familyName = row.find('.lastName');
                if (familyName.val().trim().length === 0) {
                    familyName.val(prevRow.find('.lastName').val());
                }

                var placeOfBirth = row.find('.placeOfBirth');
                if (placeOfBirth.val().trim().length === 0) {
                    placeOfBirth.val(prevRow.find('.placeOfBirth').val());
                }

                row.find('.nationality').val(prevRow.find('.nationality').val());
                row.find('.legalPlaceOfLiving').val(prevRow.find('.legalPlaceOfLiving').val());

                row.find('.kg').val(prevRow.find('.kg').val());

                row.find('.has-herkomst').val(prevRow.find('.has-herkomst').val());
                row.find('.herkomst-datum .day').val(prevRow.find('.herkomst-datum .day').val());
                row.find('.herkomst-datum .month').val(prevRow.find('.herkomst-datum .month').val());
                row.find('.herkomst-datum .year').val(prevRow.find('.herkomst-datum .year').val());
                row.find('.herkomst-plaats').val(prevRow.find('.herkomst-plaats').val());

                row.find('.has-vertrek').val(prevRow.find('.has-vertrek').val());
                row.find('.vertrek-datum .day').val(prevRow.find('.vertrek-datum .day').val());
                row.find('.vertrek-datum .month').val(prevRow.find('.vertrek-datum .month').val());
                row.find('.vertrek-datum .year').val(prevRow.find('.vertrek-datum .year').val());
                row.find('.vertrek-plaats').val(prevRow.find('.vertrek-plaats').val());

                row.data('copy-prev-person', 'copy-prev-person');
            }
        }
    };

    $(document).keydown(function (e) {
        switch (e.which) {
            case 114: // F3
                updateNumberOfLines();
                e.preventDefault();
                break;
            case 118: // F7
                copyLine();
                e.preventDefault();
                break;
        }
    }).on('focus', '#registrationAllLines input', function () {
        copyFromPrevLine();
    }).on('blur', '.nationality', function (e) {
        setNationality($(e.target));
    }).on('blur', '.legalPlaceOfLivingInCodes', function (e) {
        checkLegalPlaceOfLivingInCodes($(e.target));
    }).ready(function () {
        // Extend the width to create more space in case one enters all lines at once
        if (isAllLines()) {
            $('#main').addClass('extend-width');
        }

        // If the user has to fix the burg. stand relation, then immediately open the person dynamic modal
        if ($('#currentPerson').data('is-burg-stand-rel-fix')) {
            // Open the person dynamic modal by triggering a F4 event
            var e = $.Event('keydown');
            e.which = 115; // F4
            e.keyCode = 115; // F4

            $('.burg-stand-relatie:first').focus().trigger(e);
        }
    });

    $.registerInit(function (elem) {
        registerDateOfRegistration(elem);
        registerRelatie(elem);
        registerVolgendeInschrijvingPopup(elem);
        registerBurgStandCheck(elem);
    });
})(jQuery);