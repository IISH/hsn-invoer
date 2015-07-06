(function ($) {
    var brpAjax = false;

    var setPositie = function (elem) {
        var value = elem.val().trim();
        if ((value === 'N') || (value === 'Z')) {
            elem.closest('.form-group').find('.positie').val('n');
        }
    };

    var updateBrpFields = function (elem, isNext, isPrev) {
        if (!brpAjax) {
            brpAjax = true;
            $.resetInvisibleFormElements();

            var id = elem.attr('id');
            var form = elem.parents('form:first');
            var data = form.serialize() + '&_eventId=ajax&ajaxSource=' + id;

            $.ajax({
                type:     'POST',
                dataType: 'text',
                data:     data,
                success:  function (result) {
                    var resultElem = $(result);
                    $('#brp').replaceWith(resultElem);
                    $(document).trigger('ajax-update', [resultElem]);

                    if (isPrev) {
                        $(document.getElementById(id)).autoPrevFocus(false);
                    }
                    else {
                        $(document.getElementById(id)).autoNextFocus(false);
                    }

                    brpAjax = false;
                }
            });
        }
    };

    $(document).on('keydown', '.beroep', function (e) {
        $.ifDefaultNavigation(e, function (self, isNext, isPrev) {
            if (isPrev) {
                setPositie(self);
                updateBrpFields(self, isNext, isPrev);
            }
        });
    });

    $(document).on('change', '.beroep', function (e) {
        var self = $(e.target);
        setPositie(self);
        updateBrpFields(self, true, false);
    });
})(jQuery);