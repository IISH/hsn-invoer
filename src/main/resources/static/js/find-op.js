(function ($) {
    var self;

    function FindOp(elem) {
        // Hold a reference to 'this' in cases where 'this' won't refer to this object anymore
        self = this;

        self.idnrElem = elem;
        self.failElem = $('.fail');
        self.withOpElems = $('.with-op');
        self.withoutOpElems = $('.without-op');
        self.withOpStateElems = $('.with-op-state');
        self.huwCheckElem = $('.huw-check');
        self.nextBtnElem = $('.btn-next');
        self.lookup = elem.getDataValue('lookup');

        self.idnrElem.focus(self.onTyping);

        if (self.idnrElem.hasClass('stpb-idnr')) {
            self.idnrElem.blur(function () {
                self.blur = $(this);
                self.stpbLookup();
            });
        }
        else if (self.idnrElem.hasClass('gbh-idnr') && self.idnrElem.hasClass('no-gbh-lookup')) {
            self.idnrElem.blur(function () {
                self.blur = $(this);
                self.noRefGBHLookup();
            });
        }
        else if (self.idnrElem.hasClass('gbh-idnr')) {
            self.idnrElem.blur(function () {
                self.blur = $(this);
                self.refGBHLookup();
            });
        }

        if (!self.huwCheckElem.is(':input')) {
            self.huwCheckElem = self.huwCheckElem.find(':input:last');
        }

        self.huwCheckElem.blur(function () {
            self.blur = $(this);
            self.marriageLookup();
        });
    }

    FindOp.prototype.withIdnr = function (handler) {
        var idnr = self.idnrElem.getIntegerValue();
        if (!isNaN(idnr)) {
            handler(idnr);
        }
    };

    FindOp.prototype.stpbLookup = function () {
        self.withIdnr(function (idnr) {
            $.getJSON('/ajax/lookup/stpb', {idnr: idnr}, function (stpb) {
                $('.gemeente').text(stpb.gemeente);
                $('.jaar').text(stpb.jaar);
                $('.aktenr').text(stpb.aktenr);
                $('.idnr').text(stpb.idnr);

                if (stpb.gemeente.trim().length === 0) {
                    $.getJSON('/ajax/lookup/plaats', {gemnr: stpb.gemnr}, function (plaats) {
                        $('.gemeente').text(plaats.gemnaam);
                    });
                }

                $.getJSON('/ajax/lookup/gebakte', {idnr: idnr}, function (gebakte) {
                    self.onFailure('In het bestand opgenomen kode: ' + gebakte.gebkode, true, true, false);
                }).fail(function () {
                    self.gebkndLookup();
                });
            }).fail(function () {
                self.onFailure('Nummer niet gevonden!', false, false, true);
            });
        });
    };

    FindOp.prototype.gebkndLookup = function () {
        self.withIdnr(function (idnr) {
            $.getJSON('/ajax/lookup/gebknd', {idnr: idnr}, function (gebknd) {
                if ($.isCorrection()) {
                    $('.day').val(gebknd.aktedag);
                    $('.month').val(gebknd.aktemnd);
                    $('.hour').val(gebknd.akteuur);

                    self.onSuccess();
                }
                else {
                    self.onFailure('U moet eerst de opgenomen geboorteakte (kode = 1) vernietigen, ' +
                    'alvorens u de kode kunt veranderen!', true, false, true);
                }
            }).fail(function () {
                if ($.isCorrection()) {
                    self.onFailure('Gegevens met deze identificatie zijn nog niet ingevoerd!', false, false, true);
                }
                else {
                    self.onSuccess();
                }
            });
        });
    };

    FindOp.prototype.refGBHLookup = function () {
        self.withIdnr(function (idnr) {
            if (idnr < 500000) {
                $.getJSON('/ajax/lookup/gbh', {idnr: idnr}, function () {
                    if (self.idnrElem.hasClass('only-gbh-lookup')) {
                        self.onSuccess();
                    }
                    else {
                        self.noRefGBHLookup();
                    }
                }).fail(function () {
                    self.onFailure('De onderzoekspersoon met deze identificatie is niet aanwezig!', false, false, true);
                });
            }
            else {
                self.noRefGBHLookup();
            }
        });
    };

    FindOp.prototype.noRefGBHLookup = function () {
        self.withIdnr(function (idnr) {
            $.getJSON('/ajax/lookup/' + self.lookup, {idnr: idnr}, function () {
                if ($.isCorrection()) {
                    self.onSuccess();
                }
                else {
                    self.onFailure('De akte of kaart met deze identificatie is reeds ingevoerd!', true, false, true);
                }
            }).fail(function () {
                if ($.isCorrection()) {
                    self.onFailure('Gegevens met deze identificatie zijn nog niet ingevoerd!', false, false, true);
                }
                else {
                    self.onSuccess();
                }
            });
        });
    };

    FindOp.prototype.marriageLookup = function () {
        self.withIdnr(function (idnr) {
            var day = $('.day').val();
            var month = $('.month').val();
            var year = $('.year').val();

            $.getJSON('/ajax/lookup/huwttl', {idnr: idnr, hdag: day, hmaand: month, hjaar: year}, function (huwttl) {
                if ($.isCorrection()) {
                    self.onSuccess();
                }
                else {
                    self.onFailure('Deze akte van deze persoon is reeds ingevoerd, ' +
                    'het gaat hier om bovenstaand identificatienummer en de volgende akte: <br/>' +
                    '<div class="text-left center-block" style="width:50%;">' +
                    'gemeentenaam: ' + huwttl.hplts + '<br/>' +
                    'huwelijkdsdatum: ' + huwttl.hdag + '-' + huwttl.hmaand + '-' + huwttl.hjaar + '<br/>' +
                    'aktenummer: ' + huwttl.haktenr + '</div>', true, true, true);
                }
            }).fail(function () {
                if ($.isCorrection()) {
                    self.onFailure('Gegevens met deze identificatie zijn nog niet ingevoerd!', false, true, true);
                }
                else {
                    self.onSuccess();
                }
            });
        });
    };

    FindOp.prototype.onSuccess = function () {
        self.failElem.hide();

        self.withOpElems.show();
        self.withoutOpElems.hide();

        self.withOpStateElems.show();

        self.nextBtnElem.removeClass('op-error');
        $(document).trigger('changeOfState');

        self.blur.getNextFormElement().focus();
    };

    FindOp.prototype.onTyping = function () {
        self.failElem.hide();

        self.withOpElems.hide();
        self.withoutOpElems.show();

        self.withOpStateElems.hide();

        self.nextBtnElem.addClass('op-error');
        $(document).trigger('changeOfState');
    };

    FindOp.prototype.onFailure = function (message, foundOp, editOp, error) {
        self.failElem.html(message).show();

        self.withOpElems.hide();
        self.withoutOpElems.hide();

        foundOp ? self.withOpElems.show() : self.withoutOpElems.show();
        editOp ? self.withOpStateElems.show() : self.withOpStateElems.hide();
        error ? self.nextBtnElem.addClass('op-error') : self.nextBtnElem.removeClass('op-error');

        self.blur.getNextFormElement().focus();
        $(document).trigger('changeOfState');
    };

    $(document).ready(function () {
        new FindOp($('.stpb-idnr, .gbh-idnr'));
    });
})(jQuery);