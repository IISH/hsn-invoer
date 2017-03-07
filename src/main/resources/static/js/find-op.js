(function ($) {
    'use strict';

    var self;

    function FindOp(elem) {
        // Hold a reference to 'this' in cases where 'this' won't refer to this object anymore
        self = this;

        self.isTyping = false;
        self.idnrElem = elem;
        self.failElem = $('.fail');
        self.withOpElems = $('.with-op');
        self.withoutOpElems = $('.without-op');
        self.withOpStateElems = $('.with-op-state');
        self.huwCheckElem = $('.huw-check');
        self.militieCheckElem = $('.militie-check');
        self.nextBtnElem = $('.btn-next');
        self.lookup = elem.getDataValue('lookup');

        self.idnrElem.keypress(function (e) {
            if ((e.charCode !== 0) && !self.isTyping) {
                self.isTyping = true;
                self.onTyping();
            }
        });

        if (self.idnrElem.hasClass('stpb-idnr')) {
            self.idnrElem.blur(function () {
                if ($.getCurNavigation().isNext) {
                    self.isTyping = false;
                    self.blur = $(this);
                    self.stpbLookup();
                }
            });
        }
        else if (self.idnrElem.hasClass('rp-idnr') && self.idnrElem.hasClass('no-rp-lookup')) {
            self.idnrElem.blur(function () {
                if ($.getCurNavigation().isNext) {
                    self.blur = $(this);
                    self.noRefRPLookup();
                }
            });
        }
        else if (self.idnrElem.hasClass('rp-idnr')) {
            self.idnrElem.blur(function () {
                if ($.getCurNavigation().isNext) {
                    self.blur = $(this);
                    self.refRPLookup();
                }
            });
        }

        if (!self.huwCheckElem.is(':input')) {
            self.huwCheckElem = self.huwCheckElem.find(':input:last');
        }

        self.huwCheckElem.blur(function () {
            if ($.getCurNavigation().isNext) {
                self.blur = $(this);
                self.marriageLookup();
            }
        });

        if (!self.militieCheckElem.is(':input')) {
            self.militieCheckElem = self.militieCheckElem.find(':input:last');
        }

        self.militieCheckElem.blur(function () {
            if ($.getCurNavigation().isNext) {
                self.blur = $(this);
                self.militionSequenceLookup();
            }
        });
    }

    FindOp.prototype.withIdnr = function withIdnr(handler) {
        var idnr = self.idnrElem.getIntegerValue();
        if (!isNaN(idnr)) {
            handler(idnr);
        }
    };

    FindOp.prototype.stpbLookup = function stpbLookup() {
        self.withIdnr(function (idnr) {
            self.serverCall('/ajax/lookup/stpb', {idnr: idnr}, function (stpb) {
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
                    if ($.isCorrection()) {
                        self.onFailure('De akte is al ingevoerd onder code ' + gebakte.gebkode, false, false, true);
                    }
                    else {
                        self.onFailure('De akte is al ingevoerd onder code ' + gebakte.gebkode, true, true, false);
                    }
                }).fail(function () {
                    self.gebkndLookup();
                });
            }, function () {
                self.onFailure('Nummer niet gevonden!', false, false, true);
            });
        });
    };

    FindOp.prototype.gebkndLookup = function gebkndLookup() {
        self.withIdnr(function (idnr) {
            self.serverCall('/ajax/lookup/gebknd', {idnr: idnr}, function (gebknd) {
                if ($.isCorrection()) {
                    $('.day').val(gebknd.aktedag);
                    $('.month').val(gebknd.aktemnd);
                    $('.hour').val(gebknd.akteuur);

                    self.onSuccess();
                }
                else {
                    self.onFailure('De akte met dit identificatienummer is al ingevoerd!', true, false, true);
                }
            }, function () {
                if ($.isCorrection()) {
                    self.onFailure('Gegevens met deze identificatie zijn nog niet ingevoerd!', false, false, true);
                }
                else {
                    self.onSuccess();
                }
            });
        });
    };

    FindOp.prototype.refRPLookup = function refRPLookup() {
        self.withIdnr(function (idnr) {
            if ((idnr >= 500000) && self.idnrElem.hasClass('allow-large-idnrs')) {
                self.noRefRPLookup();
            }
            else {
                self.serverCall('/ajax/lookup/rp', {idnr: idnr}, function () {
                    if (self.idnrElem.hasClass('only-rp-lookup')) {
                        self.onSuccess();
                    }
                    else if (self.idnrElem.hasClass('m0-lookup')) {
                        if (!$.isCorrection()) {
                            self.militionLookup();
                        }
                        else {
                            self.militionSeq();
                        }
                    }
                    else {
                        self.noRefRPLookup();
                    }
                }, function () {
                    self.onFailure('De onderzoekspersoon met deze identificatie is niet aanwezig!', false, false, true);
                });
            }
        });
    };

    FindOp.prototype.militionLookup = function () {
        self.withIdnr(function (idnr) {
            self.serverCall('/ajax/lookup/m0/list', {idnr: idnr} , function (enteredScans) {
                self.serverCall('/ajax/lookup/m0/scans', {idnr: idnr} , function (availableScans) {
                    if (enteredScans.length < availableScans.length) {
                        self.onSuccess();
                    }
                    else if (availableScans.length === 0) {
                        self.onFailure('Er zijn geen scans voor de OP met deze identificatie gevonden ' +
                            'en/of de naamgeving van de scan is niet correct!', true, false, true);
                    }
                    else {
                        self.onFailure('Alle militieregisters met deze identificatie zijn reeds ingevoerd!', true, false, true);
                    }
                });
            });
        });
    };

    FindOp.prototype.noRefRPLookup = function noRefRPLookup() {
        self.withIdnr(function (idnr) {
            self.serverCall('/ajax/lookup/' + self.lookup, {idnr: idnr}, function () {
                if ($.isCorrection()) {
                    self.onSuccess();
                }
                else {
                    self.onFailure('De akte of kaart met deze identificatie is reeds ingevoerd!', true, false, true);
                }
            }, function () {
                if ($.isCorrection()) {
                    self.onFailure('Gegevens met deze identificatie zijn nog niet ingevoerd!', false, false, true);
                }
                else {
                    self.onSuccess();
                }
            });
        });
    };

    FindOp.prototype.marriageLookup = function marriageLookup() {
        self.withIdnr(function (idnr) {
            var day = $('.day').val();
            var month = $('.month').val();
            var year = $('.year').val();

            self.serverCall('/ajax/lookup/huwttl', {idnr: idnr, hdag: day, hmaand: month, hjaar: year}, function (huwttl) {
                if ($.isCorrection()) {
                    self.onSuccess();
                }
                else {
                    self.onFailure('Deze akte van deze persoon is reeds ingevoerd, ' +
                        'het gaat hier om bovenstaand identificatienummer en de volgende akte: <br/>' +
                        '<div class="text-left center-block" style="width:50%;">' +
                        'gemeentenaam: ' + huwttl.hplts + '<br/>' +
                        'huwelijkdsdatum: ' + huwttl.huw.hdag + '-' + huwttl.huw.hmaand + '-' + huwttl.huw.hjaar + '<br/>' +
                        'aktenummer: ' + huwttl.haktenr + '</div>', true, true, true);
                }
            }, function () {
                if ($.isCorrection()) {
                    self.onFailure('Gegevens met deze identificatie zijn nog niet ingevoerd!', false, true, true);
                }
                else {
                    self.onSuccess();
                }
            });
        });
    };

    FindOp.prototype.militionSequenceLookup = function () {
        self.withIdnr(function (idnr) {
            var seq = parseInt($('.seq').val());

            self.serverCall('/ajax/lookup/m0/list', {idnr: idnr} , function (enteredScans) {
                if ((seq > 0) && (enteredScans.length >= seq)) {
                    self.onSuccess();
                }
                else {
                    self.onFailure('Er is/zijn maximaal ' + enteredScans.length + ' register ingevoerd voor deze OP!', false, true, true);
                }
            }, function () {
                self.onFailure('Gegevens met deze identificatie zijn nog niet ingevoerd!', false, true, true);
            });
        });
    };

    FindOp.prototype.militionSeq = function () {
        self.withIdnr(function (idnr) {
            self.serverCall('/ajax/lookup/m0/list', {idnr: idnr} , function (enteredScans) {
                if (enteredScans.length > 1) {
                    enteredScans.sort(function (a, b) { return a.seq > b.seq });

                    var list = $('<ol>');
                    $.each(enteredScans, function (i, enteredScan) {
                        var typeRegister = 'Niet bekend';
                        if (enteredScan.type === 'A')
                            typeRegister = 'Alfabetische naamlijst';
                        if (enteredScan.type === 'I')
                            typeRegister = 'Inschrijvingsregister';
                        if (enteredScan.type === 'K')
                            typeRegister = 'Keuringsregister';
                        if (enteredScan.type === 'L')
                            typeRegister = 'Lotingsregister';

                        list.append(
                            $('<li class="spacing">')
                                .append($('<div>').text(
                                    enteredScan.familyName + ', ' + enteredScan.firstName
                                ))
                                .append($('<div>').text(
                                    typeRegister + ': ' + enteredScan.municipality + ', ' + enteredScan.year
                                ))
                        );
                    });
                    $('#volgnummers').find('ol').replaceWith(list);

                    self.onSuccess();
                }
                else if (enteredScans.length === 1) {
                    self.militieCheckElem.val(1);
                    self.onSuccess(true, false);
                }
                else {
                    self.onFailure('Gegevens met deze identificatie zijn nog niet ingevoerd!', false, false, true);
                }
            });
        });
    };

    FindOp.prototype.onSuccess = function onSuccess(autoNextElement, showOp) {
        autoNextElement = (autoNextElement || (autoNextElement === undefined));
        showOp = (showOp || (showOp === undefined));
        self.failElem.hide();

        if (showOp) self.withOpElems.show();
        self.withoutOpElems.hide();

        if (showOp) self.withOpStateElems.show();

        self.nextBtnElem.removeClass('op-error');
        $.triggerChangeOfState();

        if (autoNextElement) self.blur.getNextFormElement().focus();
        $.triggerChangeOfState();
    };

    FindOp.prototype.onTyping = function onTyping() {
        self.failElem.hide();

        self.withOpElems.hide();
        self.withoutOpElems.show();

        self.withOpStateElems.hide();

        self.nextBtnElem.addClass('op-error');
        $.triggerChangeOfState();
    };

    FindOp.prototype.onFailure = function onFailure(message, foundOp, editOp, error) {
        self.failElem.html(message).show();

        self.withOpElems.hide();
        self.withoutOpElems.hide();

        foundOp ? self.withOpElems.show() : self.withoutOpElems.show();
        editOp ? self.withOpStateElems.show() : self.withOpStateElems.hide();
        error ? self.nextBtnElem.addClass('op-error') : self.nextBtnElem.removeClass('op-error');

        $.triggerChangeOfState();
        self.blur.getNextFormElement().focus();
    };

    FindOp.prototype.serverCall = function serverCall(url, params, onSuccess, onFailure) {
        $.lockNavigation();

        /* TODO: In case a spinner is required
        var formGroup = self.blur.closest('.form-group').addClass('has-feedback');
        var icon = $('<span class="glyphicon glyphicon-refresh glyphicon-refresh-animate form-control-feedback" ' +
            'aria-hidden="true"></span>').appendTo(self.blur.parent());

        var removeIcon = function () {
            icon.remove();
            formGroup.removeClass('formGroup');
        };*/

        $.getJSON(url, params, function () {
            $.unlockNavigation();
            //removeIcon();

            onSuccess.apply(this, arguments);
        }).fail(function () {
            $.unlockNavigation();
            //removeIcon();
            
            onFailure.apply(this, arguments);
        });
    };

    $(document).ready(function () {
        new FindOp($('.stpb-idnr, .rp-idnr'));
    });
})(jQuery);