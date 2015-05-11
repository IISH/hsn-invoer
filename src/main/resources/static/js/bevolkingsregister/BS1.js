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

    // TODO: eDayInsValidate
    var onDatumExplicietHoofd = function (date, dayVal, monthVal, yearVal) {
        if (!isNaN(dayVal) && !isNaN(monthVal) && !isNaN(yearVal)) {
            date.val('###$' + ('0' + dayVal).slice(-2) + '/' + ('0' + monthVal).slice(-2) + '/' + yearVal);
        }
        else {
            date.val('');
        }
    };

    var registerRelatieRegelPopup = function (elem) {
        var elems = elem;
        if (!elem.is('tr')) {
            elems = elem.find('.relatieElems, #registrationAllLines tr');
        }

        elems.each(function () {
            var container = $(this);
            var relatie = container.find('.relatie');
            var parent = relatie.getParentOfFormElement();

            var f9 = false;
            var focusPopover = false;
            var rp = parent.getIntegerDataValue('rp');

            var ind = container.find('.ind').first();
            var relatieRegel = container.find('.relatie-regel').first();
            var relatieRegel2 = container.find('.relatie-regel-2').first();

            var relatieRegelPopup = parent.find('.relatieRegelPopup').first();
            var relatieDateHoofdPopup = parent.find('.relatieDateHoofdPopup').first();
            var relatieRegelInterprPopup = parent.find('.relatieRegelInterprPopup').first();

            relatie.popover({
                content: relatieRegelPopup.html(),
                html: true,
                placement: 'bottom',
                trigger: 'manual'
            });

            var setRelatieRegel = function (value) {
                if (value !== undefined) {
                    relatieRegel.val(value);
                    relatieRegel2.val(value);
                    (relatieRegel.getIntegerValue() > 0) ? ind.show() : ind.hide();
                }
            };

            var hidePopover = function (popover, checkError) {
                if (!checkError || (popover.find('.has-error').length === 0)) {
                    if (popover.find('.relatieDateHoofdPopupContent').length > 0) {
                        var day = parent.find('.relatieDateHoofd.day');
                        var month = parent.find('.relatieDateHoofd.month');
                        var year = parent.find('.relatieDateHoofd.year');

                        day.val(popover.find('.day').val());
                        month.val(popover.find('.month').val());
                        year.val(popover.find('.year').val());

                        onDatumExplicietHoofd(
                            parent.find('.relatieDateHoofd.code'),
                            day.getIntegerValue(), month.getIntegerValue(), year.getIntegerValue()
                        );
                    }
                    else if (popover.find('.relatieRegelInterprPopupContent').length > 0) {
                        setRelatieRegel(popover.find('.regel').val());
                        parent.find('.relatieRegelInterpr.kode').val(popover.find('.kode').val());
                    }
                    else {
                        f9 = false;
                        setRelatieRegel(popover.find('input').val());
                    }

                    focusPopover = false;
                    relatie.popover('hide');
                }
            };

            $(document).on('focus', 'input', function (e) {
                var popover = parent.find('.popover');
                if ((popover.length > 0) && $.contains(popover[0], e.target)) {
                    focusPopover = true;
                }
                else if (focusPopover) {
                    hidePopover(parent.find('.popover'), true);
                }
            });

            parent.keydown(function (e) {
                if (e.which === 120) { // F9
                    var popover = parent.find('.popover');
                    if (popover.is(':visible')) {
                        hidePopover(parent.find('.popover'), true);
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
            });

            relatieRegel2.blur(function () {
                setRelatieRegel(relatieRegel2.val());
            });

            parent.on('blur', '.relatie', function () {
                var self = $(this);
                var rel = self.getIntegerValue();
                var sex = container.find('.sex').val();

                var error = false;
                var message = '';

                var female = [2, 4, 6, 9, 40, 52, 54, 55, 56, 82, 84, 86, 88];
                var male = [3, 5, 8, 30, 51, 53, 57, 58, 81, 83, 85, 87];

                if ((sex === 'm') && ((female.indexOf(rel) >= 0) || (rel > 19 && rel < 29) || (rel > 69 && rel < 80))) {
                    error = true;
                    message = 'Geslacht is mannelijk en relatiekode is vrouwelijk van aard!';
                }
                else if ((sex === 'v') && ((male.indexOf(rel) >= 0) || (rel > 9 && rel < 19) || (rel > 59 && rel < 70))) {
                    error = true;
                    message = 'Geslacht is vrouwelijk en relatiekode is mannelijk van aard!';
                }

                $.setError(error, 'relatie-geslacht-' + self.attr('id'), message);
            });

            parent.on('focus', '.relatie', function () {
                hidePopover(parent.find('.popover'), false);
            });

            parent.on('blur', '.relatie', function (e) {
                if (f9) {
                    return;
                }

                if ((relatieDateHoofdPopup.length > 0) && (rp > 1) && ($(e.target).getIntegerValue() === 1)) {
                    relatieDateHoofdPopup.find('.day').attr('value', parent.find('.relatieDateHoofd.day').val());
                    relatieDateHoofdPopup.find('.month').attr('value', parent.find('.relatieDateHoofd.month').val());
                    relatieDateHoofdPopup.find('.year').attr('value', parent.find('.relatieDateHoofd.year').val());

                    relatie.data('bs.popover').options.content = relatieDateHoofdPopup.html();
                    relatie.popover('show');
                }
                else {
                    var dateHoofd = container.find('.dateHoofd');
                    if ((rp > 1) && ($(e.target).getIntegerValue() === 1)) {
                        dateHoofd.show();
                    }
                    else {
                        dateHoofd.hide();
                    }
                }
            });

            parent.on('blur', '.relatie', function (e) {
                if (f9) {
                    return;
                }

                var shouldShow = [30, 40, 51, 52].indexOf($(e.target).getIntegerValue()) >= 0;
                if ((relatieRegelInterprPopup.length > 0) && shouldShow) {
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
            });
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
                if (gebDatePerson.data('is-op') && gebDatePerson.is('[data-op-geb-day]')) {
                    var dayPersonVal = dayPerson.getIntegerValue();
                    var monthPersonVal = monthPerson.getIntegerValue();
                    var yearPersonVal = yearPerson.getIntegerValue();

                    var dayOpVal = gebDatePerson.getIntegerDataValue('op-geb-day');
                    var monthOpVal = gebDatePerson.getIntegerDataValue('op-geb-month');
                    var yearOpVal = gebDatePerson.getIntegerDataValue('op-geb-year');

                    if (dayPersonVal === dayOpVal && monthPersonVal === monthOpVal && yearPersonVal === yearOpVal) {
                        yearPerson.popover('show');
                    }
                    else {
                        volgendeInschrijving.val(2); // No OP
                    }
                }
            };

            dayPerson.blur(onBlur);
            monthPerson.blur(onBlur);
            yearPerson.blur(onBlur);
        });
    };

    var registerDatumExplicietHoofd = function (elem) {
        elem.find('.datum-expliciet-hoofd').each(function () {
            var self = $(this);
            var day = self.find('.day');
            var month = self.find('.month');
            var year = self.find('.year');
            var date = self.prev();

            var onBlur = function () {
                var dayVal = day.getIntegerValue();
                var monthVal = month.getIntegerValue();
                var yearVal = year.getIntegerValue();
                onDatumExplicietHoofd(date, dayVal, monthVal, yearVal);
            };

            day.blur(onBlur);
            month.blur(onBlur);
            year.blur(onBlur);
        });
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
    });

    $.registerInit(function (elem) {
        registerDateOfRegistration(elem);
        registerRelatieRegelPopup(elem);
        registerVolgendeInschrijvingPopup(elem);
        registerDatumExplicietHoofd(elem);
    });
})(jQuery);