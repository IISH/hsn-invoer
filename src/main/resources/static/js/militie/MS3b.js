(function ($) {
    'use strict';

    var onderwijsAjax = false;

    function updateOnderwijsFields(elem, isNext, isPrev) {
        if (!onderwijsAjax) {
            onderwijsAjax = true;
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
                    $('#onderwijs').replaceWith(resultElem);
                    $(document).trigger('ajax-update', [resultElem]);

                    var elem = $(document.getElementById(id));
                    if (isPrev) {
                        elem.autoPrevFocus(false);
                    }
                    else if (elem.val().trim().length === 0) {
                        $('.btn-next').focus();
                    }
                    else {
                        elem.autoNextFocus(false);
                    }

                    onderwijsAjax = false;
                }
            });
        }
    }

    $(document).on('blur', '.onderwijs', function (e) {
        var self = $(e.target);
        var curNav = $.getCurNavigation();
        updateOnderwijsFields(self, curNav.isNext, curNav.isPrev);
    });
})(jQuery);