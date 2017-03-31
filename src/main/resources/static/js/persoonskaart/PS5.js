(function ($) {
    'use strict';

    var adrAjax = false;

    function initAddressRenumbering(elem) {
        if (!$.isCorrection()) {
            var regex = /pkadres([0-9]).vernum/;
            elem.find('.adresRenumbering').blur(function (e) {
                var target = $(e.target);
                var code = target.val().trim();

                if ((code === 'v') || (code === 'n')) {
                    var index = parseInt(target.attr('id').match(regex)[1]);
                    var prevIndex = index - 1;

                    var curPlaats = $('#pkadres' + index + '\\.pladrp');
                    if (curPlaats.val().trim().length === 0) {
                        curPlaats.val($('#pkadres' + prevIndex + '\\.pladrp').val());
                    }

                    if (code === 'v') {
                        var curAdres = $('#pkadres' + index + '\\.stradrp');
                        if (curAdres.val().trim().length === 0) {
                            curAdres.val($('#pkadres' + prevIndex + '\\.stradrp').val());
                        }

                        var curLand = $('#pkadres' + index + '\\.lndadrp');
                        if (curLand.val().trim().length === 0) {
                            curLand.val($('#pkadres' + prevIndex + '\\.lndadrp').val());
                        }
                    }
                }
            });
        }
    }

    function initCheckAddressOrder(elem) {
        elem.find('.day, .month, .year').blur(function () {
            var index = 0;
            var prevOrder = null;

            var isOrderOk = true;
            var shouldContinue = true;

            while (shouldContinue && isOrderOk) {
                var yearVal = $('#pkadres' + index + '\\.jradrp').getIntegerValue();
                var monthVal = $('#pkadres' + index + '\\.mdadrp').getIntegerValue();
                var dayVal = $('#pkadres' + index + '\\.dgadrp').getIntegerValue();

                if (yearVal && monthVal && dayVal) {
                    var order = (monthVal - 1) * 30 + dayVal + (yearVal - 1900) * 365;
                    if ((order > 0) && (prevOrder !== null) && (order < prevOrder)) {
                        isOrderOk = false;
                    }
                    prevOrder = order;
                }
                else {
                    shouldContinue = false;
                }

                index++;
            }

            $.setError(!isOrderOk, 'address-order', 'De adressen staan niet op chronologische volgorde, verbeter nu!');
        });
    }

    function updateCountry(elem) {
        var parentElem = elem.closest('.pk-adres');

        var plaats = parentElem.find('.plaats');
        var street = parentElem.find('.street');
        var country = parentElem.find('.country');

        if ((country.val().trim().length === 0) &&
            ((plaats.val().trim().length > 0) || (street.val().trim().length > 0))) {
            country.val('Nl');
        }
    }

    function updateAdrFields(elem, isNext, isPrev) {
        if (!adrAjax) {
            adrAjax = true;
            $(document).resetInvisibleFormElements();

            var id = elem.attr('id');
            var form = elem.parents('form:first');
            var data = form.serialize() + '&_eventId=ajax&ajaxSource=' + id;

            $.ajax({
                type: 'POST',
                dataType: 'text',
                data: data,
                success: function (result) {
                    var resultElem = $(result);
                    $('#addresses').replaceWith(resultElem);
                    $(document).trigger('ajax-update', [resultElem]);
                    $(document).trigger('show');

                    var elem = $(document.getElementById(id));
                    var btnNext = $('.btn-next');

                    var date = elem.getParentOfFormElement().getHsnDate();
                    var dateIsEmpty = ((date.day.getValue() === 0) && (date.month.getValue() === 0) 
                        && (date.year.getValue() === 0));

                    if (isPrev) {
                        elem.autoPrevFocus(false);
                    }
                    else if (dateIsEmpty && btnNext.is(':enabled')) {
                        btnNext.focus();
                    }
                    else {
                        elem.autoNextFocus(false);
                    }

                    adrAjax = false;
                }
            });
        }
    }

    function updateDateFieldsIfEmpty(hsnDate) {
        var day = hsnDate.day;
        var dayIsZero = (day.isInput && (day.getValue() === 0));

        if (dayIsZero) {
            $.each(hsnDate, function (name, elem) {
                if (dayIsZero && elem.isInput) {
                    elem.elem.val('');
                }
            });
        }

        return dayIsZero;
    }

    $(document).on('blur', '.day, .year', function (e) {
        var self = $(e.target);
        var hsnDate = self.getParentOfFormElement().getHsnDate();
        var curNav = $.getCurNavigation();
        if (self.hasClass('year') || (self.hasClass('day') && curNav.isNext && updateDateFieldsIfEmpty(hsnDate))) {
            updateAdrFields(self, curNav.isNext, curNav.isPrev);
        }
    });

    $(document).on('blur', '.plaats, .street', function (e) {
        updateCountry($(e.target));
    });

    $.registerInit(function (elem) {
        initAddressRenumbering(elem);
        initCheckAddressOrder(elem);
    });

    $.initCheckDate('.checkAddressDate', updateDateFieldsIfEmpty, function (hsnDate) {
        if (hsnDate.day.getValue() === 0 && hsnDate.month.getValue() === 0 && hsnDate.year.getValue() === 0)
            return false;
        return $.checkHsnDate(hsnDate);
    });
})
(jQuery);