(function ($) {
    'use strict';

    /* Utility methods */

    function isAllLines() {
        return ($('#registrationAllLines').length === 1);
    }

    function getActiveRow() {
        return $('#registrationAllLines').find('tr.active');
    }

    $.fn.getPersonContainer = function getPersonContainer(includePopover) {
        var selector = '#currentPerson,tr';
        if (includePopover === true) {
            selector += ',.popover';
        }
        return this.closest(selector);
    };

    /* Overloading of methods */

    $.getCurPerson = function getCurPerson() {
        var person;
        if (isAllLines()) {
            var modal = $('.modal.in');
            if (modal.length > 0) {
                person = modal.getIntegerDataValue('rp');
            }
            else {
                person = getActiveRow().getIntegerDataValue('rp');
            }
        }
        else {
            person = $('#curPerson').getIntegerValue();
        }

        if (!isNaN(person)) {
            return person;
        }
        return 0;
    };

    $.getCurPersonModal = function getCurPersonModal() {
        if (isAllLines()) {
            return getActiveRow().find('.personModal:first');
        }
        else {
            return $('.personModal:first');
        }
    };

    $.getCurPersonData = function getCurPersonData() {
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

        // Otherwise this is the case where lines are added one by one
        return {
            firstName: $('#curB2\\.firstName').val(),
            familyName: $('#curB2\\.familyName').val(),
            dayOfBirth: $('#curB2\\.dayOfBirth').val(),
            monthOfBirth: $('#curB2\\.monthOfBirth').val(),
            yearOfBirth: $('#curB2\\.yearOfBirth').val(),
            placeOfBirth: $('#curB2\\.placeOfBirth').val()
        };
    };

    /* BS1 relation specific operations */

    var determinePrevNext, f9 = false;

    // Synchronizes the relatie regel
    function setRelatieRegel(container, value) {
        if ((value !== undefined) && !isNaN(value)) {
            if (value === 0) {
                value = -1;
            }

            container.find('.relatieRelatedHidden,.regel').val(value);
            (value > 0) ? container.find('.ind').show() : container.find('.ind').hide();

            onRelatieRegel(container, value);
        }
    }

    // Synchronizes the relatie kode
    function setRelatieKode(container, value) {
        container.find('.relatieDynamicHidden,.kode').val(value);
    }

    // Encodes the datum expliciet hoofd and synchronizes the value
    function onDatumExplicietHoofd(container, hsnDate) {
        var value = '';
        if (!hsnDate.day.isEmptyVal() && !hsnDate.month.isEmptyVal() && !hsnDate.year.isEmptyVal()) {
            value = '###$' + ('0' + hsnDate.day.getValue()).slice(-2) + '/'
                + ('0' + hsnDate.month.getValue()).slice(-2) + '/' + hsnDate.year.getValue();
        }
        container.find('.relatieDynamicHidden').val(value);
    }

    // Also set the relatie kode if the relatie regel is undetermined
    function onRelatieRegel(container, value) {
        if (value === -3) {
            setRelatieKode(container, 9);
        }
    }

    // Hides the popover and performs the related checks and procedures
    function hidePopover(container, popover, checkError) {
        determinePrevNext = false;

        // Check for errors first, if necessary
        if (!checkError || (popover.find('.has-an-error').length === 0)) {
            var focusOnRelatieElem = false; // In some cases, the focus has to go back to the relatie elem

            // In case of an 'expliciet hoofd', we have to safely store and transform the date before closing
            if (popover.find('.relatieDateHoofdPopupContent').length > 0) {
                var hsnDate = popover.getHsnDate();

                var day = container.find('.relatieDateHoofd.day');
                var month = container.find('.relatieDateHoofd.month');
                var year = container.find('.relatieDateHoofd.year');

                day.val(hsnDate.day.getValue());
                month.val(hsnDate.month.getValue());
                year.val(hsnDate.year.getValue());

                onDatumExplicietHoofd(container, hsnDate);
            }
            // In case of an 'relatie regel' and 'kode' safely store the values
            // and synchronize the 'regel' with the other 'regel' input elems
            else if (popover.find('.relatieRegelInterprPopupContent').length > 0) {
                setRelatieRegel(container, popover.find('.regel').getIntegerValue());
                setRelatieKode(container, popover.find('.kode').val());
            }
            // Last case is the 'relatie regel' only case,
            // so safely store and synchronize with the other 'regel' input elems
            else if (f9) {
                f9 = false;
                focusOnRelatieElem = true;
                setRelatieRegel(container, popover.find('input').getIntegerValue());
            }

            var relatie = container.find('.relatie');
            (focusOnRelatieElem) ? relatie.focus() : (determinePrevNext = true);

            if (popover.length > 0) {
                relatie.popover('hide');
            }

            return true;
        }

        return false;
    }

    // Determine what has to happen after a relation has been chosen
    function onRelationChosen(container, force) {
        // If the blur was caused by pressing 'F9' or when the container is not within a modal
        // and a modal is visible, don't show the popover
        if (force !== true) {
            if (f9 || (container.closest('.modal').length === 0) && ($('.modal.in').length > 0)) {
                return;
            }
        }

        var relatie = container.find('.relatie:first');
        var relatieRegelPopup = container.find('.relatieRegelPopup:first');
        var relatieDateHoofdPopup = container.find('.relatieDateHoofdPopup:first');
        var relatieRegelInterprPopup = container.find('.relatieRegelInterprPopup:first');

        // Does the chosen relation require an 'expliciet hoofd' value?
        var person = container.find($.getDataElemSelector('person')).getIntegerDataValue('person');
        if ((relatieDateHoofdPopup.length > 0) && (person > 1) && (relatie.getIntegerValue() === 1)) {
            relatieDateHoofdPopup.find('.day').attr('value', container.find('.relatieDateHoofd.day').val());
            relatieDateHoofdPopup.find('.month').attr('value', container.find('.relatieDateHoofd.month').val());
            relatieDateHoofdPopup.find('.year').attr('value', container.find('.relatieDateHoofd.year').val());

            relatie.setPopover(relatieDateHoofdPopup).popover('show');
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
        var kodeElem = container.find('.relatieDynamicHidden');
        var shouldShow = [30, 40, 51, 52].indexOf(relatie.getIntegerValue()) >= 0;
        if ((relatieRegelInterprPopup.length > 0) && shouldShow) {
            if (kodeElem.val().length > 1) {
                kodeElem.val('');
            }

            relatieRegelInterprPopup.find('.regel').attr('value', container.find('.relatieRelatedHidden').val());
            relatieRegelInterprPopup.find('.kode').attr('value', kodeElem.val());

            relatie.setPopover(relatieRegelInterprPopup).popover('show');
        }
        else {
            var regelInterpr = container.find('.regelInterpr');
            if (shouldShow) {
                if (kodeElem.val().length > 1) {
                    kodeElem.val('');
                }
                regelInterpr.find('.kode').attr('value', kodeElem.val());
                regelInterpr.show();
            }
            else {
                regelInterpr.hide();
            }
        }

        // Now validate the relation
        if ((relatie.length > 0) && (container.find('.only-head').length > 0)) {
            var message = 'Relatiecode moet 1 of -3 zijn';
            if (isAllLines()) {
                message = 'Persoon ' + $.getCurPerson() + ': ' + message;
            }

            var relVal = relatie.getIntegerValue();
            $.setError(
                (relVal !== -3) && (relVal !== 1),
                'rel-only-head-' + relatie.attr('id'),
                message
            );
        }
    }

    // Bind the 'F9' key to open the 'relatie regel' popover or to close the opened relatie popover
    function relatieOnF9(target, e) {
        if (e.which === 120) { // F9
            var popover = $('.popover');

            if (f9 && popover.hasClass('in')) {
                hidePopover(popover.data('bs.popover').$element.getPersonContainer(), popover, true);
            }
            else {
                var relatie = target;
                var container = relatie.getPersonContainer();

                var relatieRegelPopup = container.find('.relatieRegelPopup').first();
                relatieRegelPopup.find('input').attr('value', container.find('.relatieRelatedHidden').val());

                f9 = true;
                relatie.setPopover(relatieRegelPopup).popover('show');
                $('.popover input').first().focus();
            }
        }
    }

    // Validate the relation based on the given sex and the other way around
    function validateRelationSex(elem) {
        var container = elem.getPersonContainer();
        var relatie = container.find('.relatie');
        var sex = container.find('.sex');

        var rel = relatie.getIntegerValue();
        var sexVal = sex.is('input') ? sex.val() : sex.text();

        var error = false;
        var message = '';

        var female = [2, 4, 6, 9, 40, 52, 54, 55, 56, 82, 84, 86, 88];
        var male = [3, 5, 8, 30, 51, 53, 57, 58, 81, 83, 85, 87];

        if ((sexVal === 'm') && ((female.indexOf(rel) >= 0) || (rel > 19 && rel < 29) || (rel > 69 && rel < 80))) {
            error = true;
            message = 'Geslacht is mannelijk en relatiecode is vrouwelijk van aard!';
        }
        else if ((sexVal === 'v') && ((male.indexOf(rel) >= 0) || (rel > 9 && rel < 19) || (rel > 59 && rel < 70))) {
            error = true;
            message = 'Geslacht is vrouwelijk en relatiecode is mannelijk van aard!';
        }

        if (isAllLines()) {
            message = 'Persoon ' + $.getCurPerson() + ': ' + message;
        }

        $.setError(error, 'relatie-geslacht-' + relatie.attr('id'), message);
    }

    function onRelatiePopoverNavTrigger(relatie, isLeft, prevField, popover) {
        (isLeft) ? relatie.data('nav', 'left') : relatie.data('nav', 'right');

        if (!hidePopover(relatie.getPersonContainer(), popover, true)) {
            (isLeft)
                ? popover.find('input').filter(':enabled:visible').last().focus()
                : popover.find('input').filter(':enabled:visible').first().focus();
        }
    }

    function onRelatiePopoverHidden(relatie) {
        if (determinePrevNext) {
            (relatie.data('nav') === 'left') ? relatie.autoPrevFocus(false) : relatie.autoNextFocus(false);
        }
    }

    /* BS1 next registration specific operations */

    function openNextRpPopover(elem) {
        var gebDatePerson = elem.closest('.geb-date-person');
        var gebDate = gebDatePerson.getHsnDate();

        var dayPersonVal = gebDate.day.getValue();
        var monthPersonVal = gebDate.month.getValue();
        var yearPersonVal = gebDate.year.getValue();

        if (!gebDatePerson.data('is-op') && gebDatePerson.is('[data-op-geb-day]')) {
            var dayOpVal = gebDatePerson.getIntegerAttr('data-op-geb-day');
            var monthOpVal = gebDatePerson.getIntegerAttr('data-op-geb-month');
            var yearOpVal = gebDatePerson.getIntegerAttr('data-op-geb-year');

            if (dayPersonVal === dayOpVal && monthPersonVal === monthOpVal && yearPersonVal === yearOpVal) {
                if (!gebDate.year.elem.data('popover-closed')) {
                    gebDate.year.elem.setPopover(gebDatePerson.find('.volgendeInschrijvingOpPopup')).popover('show');
                }
            }
            else {
                gebDatePerson.find('.volgende-inschrijving').val(2); // No OP
                gebDate.year.elem.removeData('popover-closed');
            }
        }
        else if (gebDatePerson.data('is-op')) {
            $($.getDataElemSelector('op-geb-day')).attr('data-op-geb-day', dayPersonVal);
            $($.getDataElemSelector('op-geb-month')).attr('data-op-geb-month', monthPersonVal);
            $($.getDataElemSelector('op-geb-year')).attr('data-op-geb-year', yearPersonVal);
        }
    }

    function isRpCheck(target) {
        var popover = target.closest('.popover');
        var gebDatePerson = popover.data('bs.popover').$element.closest('.geb-date-person');
        if (gebDatePerson.length > 0) {
            var value = target.val().trim();
            if (value.length > 0) {
                var volgendeInschrijving = gebDatePerson.find('.volgende-inschrijving');
                if (value === 'j') {
                    volgendeInschrijving.val(5); // Second OP
                }
                else {
                    volgendeInschrijving.val(2); // No OP
                }
                gebDatePerson.find('.year').data('popover-closed', true);
            }
        }
    }

    function onNextRpPopoverNavTrigger(yearPerson, isLeft, prevField, popover) {
        isLeft ? yearPerson.data('nav', 'left') : yearPerson.data('nav', 'right');
        yearPerson.popover('hide');
    }

    function onNextRpPopoverHidden(yearPerson) {
        (yearPerson.data('nav') === 'left') ? yearPerson.autoPrevFocus(false) : yearPerson.autoNextFocus(false);
    }

    /* Various other BS1 specific operations */

    function showDatumInschrijving(elem) {
        var datumInschrijving = elem.getPersonContainer().find('.datum-inschrijving');
        onDatumInschrijvingToggle(elem, datumInschrijving);

        if (elem.val() === 'j') {
            datumInschrijving.show();
        }
        else {
            datumInschrijving.hide();
        }
    }

    function onDatumInschrijving(elem) {
        var person = elem.getPersonContainer();
        var hasInschrijving = person.find('.has-inschrijving');
        var datumInschrijving = person.find('.datum-inschrijving');
        var hsnDate = datumInschrijving.getHsnDate();

        if (elem.hasClass('day') && (elem.getIntegerValue() === 0)) {
            hasInschrijving.val('n');
            onDatumInschrijvingToggle(hasInschrijving, datumInschrijving);
        }
        else if (hsnDate.day.getValue() === -1 && hsnDate.month.getValue() === -1 && hsnDate.year.getValue() === -1) {
            hasInschrijving.val('n');
            onDatumInschrijvingToggle(hasInschrijving, datumInschrijving);
            hsnDate.year.elem.autoNextFocus(false);
        }
    }

    function onDatumInschrijvingToggle(hasInschrijvingElem, datumInschrijvingElem) {
        var hsnDate = datumInschrijvingElem.getHsnDate();
        if (datumInschrijvingElem.is(':hidden') && hasInschrijvingElem.val() === 'j') {
            hsnDate.day.elem.val('');
            hsnDate.month.elem.val('');
            hsnDate.year.elem.val('');

            var prevRow = getActiveRow().prev();
            if (prevRow.is('tr') && (prevRow.find('.has-inschrijving').val() === 'j')) {
                hsnDate.day.elem.val(prevRow.find('.datum-inschrijving .day').val());
                hsnDate.month.elem.val(prevRow.find('.datum-inschrijving .month').val());
                hsnDate.year.elem
                    .val(prevRow.find('.datum-inschrijving .year').val())
                    .autoNextFocus(true);
            }
        }
        else if (datumInschrijvingElem.is(':visible') && hasInschrijvingElem.val() !== 'j') {
            hsnDate.day.elem.val(-1);
            hsnDate.month.elem.val(-1);
            hsnDate.year.elem.val(-1);
        }
    }

    function setPositie(elem) {
        var value = elem.val().trim();
        if ((value === 'N') || (value === 'Z')) {
            elem
                .getPersonContainer()
                .find('.positie')
                .val('n')
                .autoNextFocus(true);
        }
    }

    function updatePositie(elem) {
        var replaceVal = 0;
        switch (elem.val()) {
            case 'h':
                replaceVal = 1;
                break;
            case 'o':
                replaceVal = 2;
                break;
            case 'n':
                replaceVal = 3;
                break;
        }
        elem.prev('input[type=hidden]').val(replaceVal);
    }

    function showBurgStandAdditional(elem) {
        var burgStandToggle = elem.getPersonContainer().find('.burgStandToggle');
        var value = elem.getIntegerValue();
        if ([2, 3, 5, 9].indexOf(value) > -1) {
            if (isAllLines() && burgStandToggle.is(':hidden')) {
                var data = location.search.substring(1) + '&person=' + $.getCurPerson() + '&type=BURGELIJKE_STAND';
                $.get('/bevolkingsregister/related-person-dynamics', data, function (personDynamics) {
                    var found = false;
                    $.each(personDynamics, function (i, personDynamic) {
                        if (!found && (personDynamic.contentOfDynamicData === value)) {
                            burgStandToggle.find('.burg-stand-relatie').val(personDynamic.keyToRegistrationPersons);
                            burgStandToggle.find('.day').val(personDynamic.dayOfMutation);
                            burgStandToggle.find('.month').val(personDynamic.monthOfMutation);
                            burgStandToggle.find('.year').val(personDynamic.yearOfMutation);
                            burgStandToggle.find('.plaats').val(personDynamic.dynamicData2);
                            found = true;
                        }
                    });
                    burgStandToggle.trigger('show');
                });
            }
            burgStandToggle.show();
        }
        else {
            burgStandToggle.hide();
        }
    }

    function checkBurgStand(elem) {
        var relatie = elem.getIntegerValue();
        var curPerson = $('#currentPerson');

        var numberOfLines = 0;
        if (isAllLines()) {
            numberOfLines = $('#registrationAllLines').find('tr[data-rp]:last').getIntegerDataValue('rp');
        }
        else if (curPerson.data('is-burg-stand-rel-fix') ||
            ($.isCorrection() && (curPerson.getIntegerDataValue('correction-code') === 6))) {
            numberOfLines = curPerson.getIntegerDataValue('nr-persons');
        }

        var message = 'Een relatie met regelnummer ' + relatie + ' is onmogelijk.';
        if (isAllLines()) {
            message = 'Persoon ' + $.getCurPerson() + ': ' + message;
        }

        $.setError(
            !isNaN(numberOfLines) && (numberOfLines > 0) && (relatie > 0) && (relatie > numberOfLines),
            'burg-stand-' + elem.attr('id'),
            message
        );
        $.triggerChangeOfState();
    }

    function showDatumPlaats(elem) {
        var field = elem.attr('class').match(/has-([a-z]*)/)[1];
        var toggleFields = elem.getPersonContainer()
            .find('.' + field + '-datum, .' + field + '-plaats, .' + field + '-container');

        if (elem.val() === 'j') {
            toggleFields.show();
        }
        else {
            toggleFields.hide();
        }
    }

    function setNationality(elem) {
        if (elem.val().trim().toUpperCase() === 'NL') {
            elem.val('Nederlandse');
        }
    }

    function checkLegalPlaceOfLivingInCodes(elem) {
        var value = elem.val().trim().toLowerCase();
        var validValues = ['w', 'v', 'n', 'vw', 'wv'];
        elem.hasErrorWhen(validValues.indexOf(value) < 0);
        $.triggerChangeOfState();
    }

    function updateBurgRelation(self, elems) {
        var relation = elems.parent.find('.valueOfRelatedPerson:last').getIntegerText();
        relation = isNaN(relation) ? '' : relation;
        elems.onEdit.find('input[name=valueOfRelatedPerson]').val(relation);
    }

    function updateNumberOfLines(update) {
        var modal = $.getOpenedModal();

        var nrOfLines = parseInt(modal.find('input').val());
        if (!update || isNaN(nrOfLines)) {
            modal.modal('hide');
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
                var table = $('#registrationAllLines');
                var fixedLeftTable = table.closest('.fixed-left-column').find('.fixed table');
                var currentNrOfLines = table.find('tr[data-rp]:last').getIntegerDataValue('rp');

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
                    for (var j = currentNrOfLines; j > nrOfLines; j--) {
                        table.find('tr[data-rp=' + j + ']').remove();
                        fixedLeftTable.find('tr:last').remove();
                    }
                }

                var focusElem = $(':input:focus');
                if (focusElem.length === 0) {
                    table.find('tr:last input:first').focus();
                }

                modal.modal('hide');
            },
            error: function () {
                // TODO: Now it is an assumption that the RP would otherwise have been deleted
                alert('U kunt de regel van de OP en voorgaande niet verwijderen.');
                modal.modal('hide');
            }
        });
    }

    function updateNumberOfLinesOpen() {
        var modal = $('.nrOfLinesModal:first');
        modal.find('input').val('');
        modal.modal({keyboard: false, backdrop: 'static'});
    }

    function copyLine(update) {
        var modal = $.getOpenedModal();

        var person = modal.data('person');
        if (isNaN(person) || (person === 0)) {
            modal.modal('hide');
            return;
        }

        var copyLine = parseInt(modal.find('input').val());
        if (!update || isNaN(copyLine)) {
            modal.modal('hide');
            return;
        }
        if (copyLine === person) {
            alert('Hetzelfde regelnummer.');
            modal.modal('hide');
            return;
        }
        if (!confirm('Bent u zeker dat u gegevens wilt kopieren?')) {
            modal.modal('hide');
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
                    resultElem.data('copy-prev-person', 'copy-prev-person');
                }
                else {
                    target = $('#currentPerson');
                }

                var focusElemId = $(':focus').attr('id');
                target.replaceWith(resultElem);
                $(document).trigger('ajax-update', [resultElem]);
                $(document.getElementById(focusElemId)).focus();

                modal.modal('hide');
            },
            error: function () {
                // TODO: Now it is an assumption that the line is not valid
                alert('Regel niet aanwezig.');
                modal.modal('hide');
            }
        });
    }

    function copyLineOpen() {
        var person = $.getCurPerson();
        if (isNaN(person) || (person === 0)) {
            return;
        }

        var modal = $('.copyLineModal:first');
        modal.find('input').val('');
        modal.data('person', person);
        modal.modal({keyboard: false, backdrop: 'static'});
    }

    function nextLine(update) {
        var modal = $.getOpenedModal();

        var person = modal.data('person');
        if (isNaN(person) || (person === 0)) {
            modal.modal('hide');
            return;
        }

        var nextLine = parseInt(modal.find('input').val());
        if (!update || isNaN(nextLine)) {
            modal.modal('hide');
            return;
        }
        if (nextLine === person) {
            alert('Hetzelfde regelnummer.');
            modal.modal('hide');
            return;
        }
        if (nextLine > person) {
            alert('Het nummer moet kleiner zijn dan ' + person);
            modal.modal('hide');
            return;
        }

        $('#nextPersonKey').val(nextLine);
        modal.modal('hide');
    }

    function nextLineOpen() {
        var person = $.getCurPerson();
        if (isNaN(person) || (person === 0)) {
            return;
        }

        var modal = $('.nextLineModal:first');
        var nextPersonKeyElem = $('#nextPersonKey');
        modal.find('input').val(nextPersonKeyElem.val());
        modal.data('person', person);
        modal.modal({keyboard: false, backdrop: 'static'});
    }

    function copyFromPrevLine(elem) {
        var row = getActiveRow();
        if (!$.isCorrection() && (row.data('copy-prev-person') !== 'copy-prev-person')) {
            var prevRow = row.prev();
            if (prevRow.is('tr')) {
                var hasInscrhijving = prevRow.find('.has-inschrijving').val();
                var datumInschrijving = row.find('.datum-inschrijving');
                if (hasInscrhijving === 'j') {
                    datumInschrijving.show();
                }
                datumInschrijving.find('.day').val(prevRow.find('.datum-inschrijving .day').val());
                datumInschrijving.find('.month').val(prevRow.find('.datum-inschrijving .month').val());
                datumInschrijving.find('.year').val(prevRow.find('.datum-inschrijving .year').val());
                row.find('.has-inschrijving').val(hasInscrhijving);

                var familyName = row.find('.lastName');
                if (familyName.val().trim().length === 0) {
                    if ($.getCurPerson() === 3) {
                        familyName.val($('#registrationAllLines').find('tr[data-rp=1] .lastName').val());
                    }
                    else {
                        familyName.val(prevRow.find('.lastName').val());
                    }
                }

                var placeOfBirth = row.find('.placeOfBirth');
                if (placeOfBirth.val().trim().length === 0) {
                    placeOfBirth.val(prevRow.find('.placeOfBirth').val());
                }

                row.find('.nationality').val(prevRow.find('.nationality').val());
                row.find('.legalPlaceOfLiving').val(prevRow.find('.legalPlaceOfLiving').val());

                row.find('.kg').val(prevRow.find('.kg').val());

                row.find('.herkomst-datum .day').val(prevRow.find('.herkomst-datum .day').val());
                row.find('.herkomst-datum .month').val(prevRow.find('.herkomst-datum .month').val());
                row.find('.herkomst-datum .year').val(prevRow.find('.herkomst-datum .year').val());
                row.find('.herkomst-plaats').val(prevRow.find('.herkomst-plaats').val());
                row.find('.has-herkomst').val(prevRow.find('.has-herkomst').val());

                row.find('.vertrek-datum .day').val(prevRow.find('.vertrek-datum .day').val());
                row.find('.vertrek-datum .month').val(prevRow.find('.vertrek-datum .month').val());
                row.find('.vertrek-datum .year').val(prevRow.find('.vertrek-datum .year').val());
                row.find('.vertrek-plaats').val(prevRow.find('.vertrek-plaats').val());
                row.find('.has-vertrek').val(prevRow.find('.has-vertrek').val());

                // Make sure the focused element is also checked for errors
                elem.trigger('changeCheckErrors');

                row.data('copy-prev-person', 'copy-prev-person');
            }
        }
    }

    function registerPersons() {
        $('#registrationAllLines').find('tr.register-person').each(function () {
            var row = $(this);
            row.resetInvisibleFormElements();
            row.removeClass('register-person');
            var data = row.find('.form-elem').serialize()
                + '&b4.remarks=' + $('[name=b4\\.remarks]').val()
                + '&_eventId=register-person&ajaxSource=true&person=' + row.data('rp');
            $.ajax({type: 'POST', data: data});
        });
    }

    function registerPerson() {
        var row = getActiveRow();
        if (!row.hasClass('register-person')) {
            registerPersons();
            row.addClass('register-person');
        }
    }

    function updateNavCurPerson(e) {
        var person = parseInt($(e.target).closest('tr').getIntegerDataValue('rp'));
        if (!isNaN(person)) {
            $('.navCurPerson').text(person);
            $('.navCurPersonContainer').showNoEvent();
        }
        else {
            $('.navCurPersonContainer').hideNoEvent();
        }
    }

    function updateScrollPosition(elem) {
        var parent = elem.getParentOfFormElement();
        var scrollable = parent.closest('.scrollable');

        if ((parent.length > 0) && (scrollable.length > 0)) {
            var minLeft = parseInt(scrollable.width() / 2);
            var position = parent.offset().left;

            if (position > minLeft) {
                var firstColumn, lastGroup;
                var column = (parent.is('td')) ? parent : parent.closest('td');
                var lastColumn = column;

                while (!firstColumn) {
                    var curGroup = column.data('group');
                    if (!curGroup || (lastGroup && (lastGroup !== curGroup))) {
                        firstColumn = lastColumn;
                    }

                    var nextColumn = column.prev('td');
                    if (!nextColumn) {
                        firstColumn = column;
                    }
                    else {
                        lastGroup = curGroup;
                        lastColumn = column;
                        column = nextColumn;
                    }
                }

                scrollable.scrollLeft(scrollable.scrollLeft() + firstColumn.offset().left - 50);
            }
        }
    }

    $(document).keydown(function (e) {
        var modal = $.getOpenedModal();
        var isModalVisible = (modal.length === 1);
        var isNrOfLinesModal = (isModalVisible && (modal.hasClass('nrOfLinesModal')));
        var isCopyLineModal = (isModalVisible && (modal.hasClass('copyLineModal')));
        var isNextLineModal = (isModalVisible && (modal.hasClass('nextLineModal')));

        var target = $(e.target);
        if (target.hasClass('relatie') || target.hasClass('f9-input')) {
            relatieOnF9(target, e);
        }

        switch (e.which) {
            case 27: // Esc
                if (isNrOfLinesModal) {
                    updateNumberOfLines(false);
                    e.preventDefault();
                }
                if (isCopyLineModal) {
                    copyLine(false);
                    e.preventDefault();
                }
                if (isNextLineModal) {
                    nextLine(false);
                    e.preventDefault();
                }
                break;
            case 114: // F3
                if (isAllLines()) {
                    if (isNrOfLinesModal) {
                        updateNumberOfLines(true);
                        e.preventDefault();
                    }
                    else if (!isModalVisible) {
                        updateNumberOfLinesOpen();
                        e.preventDefault();
                    }
                }
                break;
            case 118: // F7
                if (isCopyLineModal) {
                    copyLine(true);
                    e.preventDefault();
                }
                else if (!isModalVisible) {
                    copyLineOpen();
                    e.preventDefault();
                }
                break;
            case 119: // F8
                if (!isAllLines()) {
                    if (isNextLineModal) {
                        nextLine(true);
                        e.preventDefault();
                    }
                    else if (!isModalVisible) {
                        nextLineOpen();
                        e.preventDefault();
                    }
                }
                break;
        }
    }).on('focus', '.form-elem', function (e) {
        var elem = $(e.target);

        if (isAllLines()) {
            updateNavCurPerson(e);
        }

        if (elem.hasClass('btn-next')) {
            registerPersons();
        }
        else if (elem.closest('#registrationAllLines').length > 0) {
            registerPerson();
            copyFromPrevLine(elem);
            updateScrollPosition(elem);
        }
    }).on('change', 'input', function (e) {
        var elem = $(e.target);

        if (elem.hasClass('has-inschrijving')) {
            showDatumInschrijving(elem);
        }
        else if (elem.hasClass('burgstand')) {
            showBurgStandAdditional(elem);
        }
        else if (elem.hasClass('has-hvo')) {
            showDatumPlaats(elem);
        }
        else if (elem.hasClass('legalPlaceOfLivingInCodes')) {
            checkLegalPlaceOfLivingInCodes(elem);
        }
    }).on('blur', 'input', function (e) {
        var elem = $(e.target);

        if (elem.hasClass('regel')) {
            onRelatieRegel(elem.getPersonContainer(true), elem.getIntegerValue());
        }
        else if (elem.hasClass('relatie') || elem.hasClass('sex')) {
            validateRelationSex(elem);

            // Determine what has to happen after a relation has been chosen
            if (elem.hasClass('relatie')) {
                onRelationChosen(elem.getPersonContainer());
            }
        }
        else if (elem.hasClass('positie')) {
            updatePositie(elem);
        }
        else if (elem.hasClass('burg-stand-relatie')) {
            checkBurgStand(elem);
        }
        else if (elem.hasClass('nationality')) {
            setNationality(elem);
        }
        else if (elem.hasClass('is-next-rp')) {
            isRpCheck(elem);
        }
        else if (elem.hasClass('dateInput')) {
            var parent = elem.getParentOfFormElement();

            // Update the datum expliciet hoofd when changes are made
            if (parent.hasClass('dateHoofd')) {
                onDatumExplicietHoofd(parent.getPersonContainer(), parent.getHsnDate());
            }
            else if (parent.hasClass('datum-inschrijving')) {
                onDatumInschrijving(elem);
            }
            else if (parent.hasClass('geb-date-person-parent')) {
                openNextRpPopover(elem);
            }
        }
    }).on('typeahead-change', '.beroep', function (e) {
        setPositie($(e.target));
    }).on('crud-table-new', '[data-type=BURGELIJKE_STAND]', function (e, elems) {
        updateBurgRelation($(e.target), elems);
    }).on('nav-trigger', function (e, prevField, popover) {
        var target = $(e.target);

        if (target.hasClass('popover-left') || target.hasClass('popover-right')) {
            var isLeft = target.hasClass('popover-left');
            var elem = popover.data('bs.popover').$element;

            if (elem.hasClass('relatie')) {
                onRelatiePopoverNavTrigger(elem, isLeft, prevField, popover);
            }

            if (elem.hasClass('year')) {
                onNextRpPopoverNavTrigger(elem, isLeft, prevField, popover);
            }
        }
    }).on('hidden.bs.popover', function (e) {
        var elem = $(e.target);

        if (elem.hasClass('relatie')) {
            onRelatiePopoverHidden(elem);
        }

        if (elem.hasClass('year')) {
            onNextRpPopoverHidden(elem);
        }
    }).on('click', '.btn-stop', function (e) {
        var message = 'U wilt stoppen, dat betekent dat alle ingevoerde gegevens ' +
            'van de laatste persoon zullen verdwijnen. Tenzij u nog geen OP heeft ingevoerd, dan verdwijnt alles!';
        if (isAllLines()) {
            message = 'U wilt stoppen, dat betekent dat alle ingevoerde gegevens zullen verdwijnen!';
        }

        if (!confirm(message)) {
            e.preventDefault();
            e.stopImmediatePropagation();
        }
    }).ready(function () {
        if (isAllLines()) {
            // Extend the width to create more space in case one enters all lines at once
            $('#main').addClass('extend-width');

            // Also person registration is done via AJAX, so don't submit data on next/cancel
            // This is done by binding the submit buttons to another empty form
            $('form button[type=submit]').attr('form', 'no-form');
        }
        else {
            $(document).on('blur', 'input', function (e) {
                var elem = $(e.target);

                if (elem.hasClass('regel')) {
                    // Synchronize the relatie value with the other 'regel' input elems
                    setRelatieRegel($('#currentPerson'), elem.getIntegerValue());
                }
                else if (elem.hasClass('kode')) {
                    // Synchronize the relatie value with the other 'kode' input elems
                    setRelatieKode($('#currentPerson'), elem.val());
                }
            });
        }

        // If the user has to fix the burg. stand relation, then disable everything else
        if ($('#currentPerson').data('is-burg-stand-rel-fix')) {
            $(':input').not('.burg-stand-relatie, .btn-next, .modal button').attr('disabled', 'disabled');

            // Also immediately open the person dynamic modal, by triggering a F4 event
            var e = $.Event('keydown');
            e.which = 115; // F4
            e.keyCode = 115; // F4

            $('.burg-stand-relatie:first').addClass('no-auto-new').focus().trigger(e);
        }
    });

    $.registerInit(function () {
        if (!isAllLines()) {
            onRelationChosen($('#currentPerson'), true);
        }
    });
})(jQuery);