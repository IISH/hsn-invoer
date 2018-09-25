(function ($) {
    'use strict';

    var brpAjax = false;

    function updateBrpFields(elem, isNext, isPrev) {
        if (!brpAjax) {
            brpAjax = true;
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
                    $('#brp').replaceWith(resultElem);
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

                    brpAjax = false;
                }
            });
        }
    }

    $(document).on('blur', '.beroep', function (e) {
        var self = $(e.target);
        var curNav = $.getCurNavigation();
        updateBrpFields(self, curNav.isNext, curNav.isPrev);
    });

    $(document).on('typeahead-change', '.beroep', function (e) {
        var self = $(e.target);
        updateBrpFields(self, true, false);
    });
})(jQuery);