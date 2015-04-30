(function ($) {
    var initAddressRenumbering = function (elem) {
        if (!$.isCorrection()) {
            var regex = /pkadres([0-9]).vernum/;
            elem.find('.adresRenumbering').blur(function (e) {
                var target = $(e.target);
                var code = target.val().trim();

                if ((code === 'v') || (code === 'n')) {
                    var index = parseInt(target.attr('id').match(regex)[1]);
                    var prevIndex = index - 1;

                    $('#pkadres' + index + '\\.pladrp').val($('#pkadres' + prevIndex + '\\.pladrp').val());
                    if (code === 'v') {
                        $('#pkadres' + index + '\\.stradrp').val($('#pkadres' + prevIndex + '\\.stradrp').val());
                    }
                }
            });
        }
    };

    var initCheckAddressOrder = function (elem) {
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
    };

    $.registerInit(function (elem) {
        initAddressRenumbering(elem);
        initCheckAddressOrder(elem);
    });
})
(jQuery);