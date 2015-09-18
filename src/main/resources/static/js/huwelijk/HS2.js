(function ($) {
    'use strict';

    var checkNotAlive = function () {
        var isAlive = $(forAllFour('#huwknd\\.lev'));

        var names = [];
        names.push(forAllFour('#huwknd\\.anm'));
        names.push(forAllFour('#huwknd\\.vrn1'));
        names.push(forAllFour('#huwknd\\.vrn2'));
        names.push(forAllFour('#huwknd\\.vrn3'));

        if (!$.isCorrection() && (isAlive.val() === 'o')) {
            $(names.join(',')).val('');
        }
    };

    var checkAge = function () {
        var ageElem = $(forAllFour('#huwknd\\.lftj'));
        var age = ageElem.getIntegerValue();

        ageElem.hasErrorWhen(age < -3 || age > 110 || (age > -1 && age < 15 && age !== 0));
        $(document).trigger('changeOfState');
    };

    var forAllFour = function (name) {
        var names = [];
        names.push(name + 'vrhm');
        names.push(name + 'mrhm');
        names.push(name + 'vrhv');
        names.push(name + 'mrhv');
        return names.join(',');
    };

    $(document).ready(function () {
        $(forAllFour('#huwknd\\.lev')).one('blur', checkNotAlive);

        checkAge();
        $(forAllFour('#huwknd\\.lftj')).blur(checkAge);
    });
})(jQuery);