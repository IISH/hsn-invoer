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

        $('.info').hideNoEvent();

        if (plaats.indexOf(id) >= 0) {
            $('.info-plaats').showNoEvent();
        }

        if (anaam.indexOf(id) >= 0) {
            $('.info-anaam').showNoEvent();
        }

        if (vnaam.indexOf(id) >= 0) {
            $('.info-vnaam').showNoEvent();
        }

        if (beroep.indexOf(id) >= 0) {
            $('.info-beroep').showNoEvent();
        }
    });
})(jQuery);