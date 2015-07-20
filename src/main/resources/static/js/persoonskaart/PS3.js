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
    };

    $(document).on('keydown', '.beroep', function (e) {
        $.ifDefaultNavigation(e, function (self, isNext, isPrev) {
            setPositie(self);
            updateBrpFields(self, isNext, isPrev);
        });
    });

    $(document).on('typeahead-change', '.beroep', function (e) {
        var self = $(e.target);
        setPositie(self);
        updateBrpFields(self, true, false);
    });
})(jQuery);