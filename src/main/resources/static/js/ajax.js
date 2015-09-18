/**
 * The element with attribute 'ajax-update' will trigger an AJAX request when the focus is lost from the element.
 * The specified selector will be used to update the element with the resulting response from the AJAX request.
 */
(function ($) {
    'use strict';

    $(document).on('blur', $.getDataElemSelector('ajax-update'), function (e) {
        $.resetInvisibleFormElements();

        var elem = $(e.target);

        var form = elem.parents('form:first');
        var target = elem.getDataValueAsElem('ajax-update');
        var data = form.serialize() + '&_eventId=ajax&ajaxSource=' + elem.attr('id');

        $.ajax({
            type: 'POST',
            dataType: 'text',
            data: data,
            success: function (result) {
                var resultElem = $(result);
                target.replaceWith(resultElem);
                $(document).trigger('ajax-update', [resultElem]);
                $(document).trigger('show');
                elem.getNextFormElement().focus();
            }
        });
    });
})(jQuery);
