(function ($) {
    'use strict';

    var plaats = [
        'milReg.placeOfBirth',
        'milReg.placeOfLiving',
        'milReg.placeParents',
        'milReg.placeGuardian'
    ];

    var anaam = [
        'milReg.familyName',
        'milReg.familyNameFather',
        'milReg.familyNameMother',
        'milReg.familyNameGuardian'
    ];

    var vnaam = [
        'milReg.firstName',
        'milReg.firstNameFather',
        'milReg.firstNameMother',
        'milReg.firstNameGuardian'
    ];

    var beroep = [
        'milReg.profession',
        'milReg.professionFather',
        'milReg.professionMother',
        'milReg.professionGuardian'
    ];

    $(document).on('focus', '.form-elem', function onFocus(e) {
        var id = $(e.target).attr('id');
        var year = $('.year').getIntegerText();
        var type = $('.type').text();

        $('.info').hideNoEvent();

        if (plaats.indexOf(id) >= 0)
            $('.info-plaats').showNoEvent();
        if (anaam.indexOf(id) >= 0)
            $('.info-anaam').showNoEvent();
        if (vnaam.indexOf(id) >= 0)
            $('.info-vnaam').showNoEvent();
        if (beroep.indexOf(id) >= 0)
            $('.info-beroep').showNoEvent();

        if (id === 'mil.drawnNumber') {
            year = $('#mil\\.year').getIntegerValue();
            type = $('#mil\\.type').val();

            if (year >= 1913 && year <= 1922) {
                if (type === 'A')
                    $('.info-1912A-lotingsnummer').showNoEvent();
                if (type === 'L')
                    $('.info-1912L-lotingsnummer').showNoEvent();
            }
        }

        if (id === 'includesExemption') {
            if (year >= 1913 && year <= 1922) {
                if (type === 'A')
                    $('.info-1913A-exemption').showNoEvent();
            }
        }

        if (id === 'includesMedical') {
            if (year >= 1913 && year <= 1922) {
                if (type === 'A')
                    $('.info-1913A-medical').showNoEvent();
            }
        }

        if (id === 'includesDelayMilition') {
            if (year >= 1913 && year <= 1922) {
                if (type === 'A')
                    $('.info-1913A-delayMilition').showNoEvent();
            }
        }

        if (id === 'includesAppeal') {
            if (year >= 1913 && year <= 1922) {
                if (type === 'A')
                    $('.info-1913A-appeal').showNoEvent();
            }
        }
    });
})(jQuery);