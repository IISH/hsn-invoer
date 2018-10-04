(function ($) {
    'use strict';

    function validateHierarchyAndNoChildren() {
        var numberOfChildrenElem = $('#mil\\.numberOfChildren');
        var hierarchyElem = $('#mil\\.hierarchy');

        var numberOfChildren = numberOfChildrenElem.getIntegerValue();
        var hierarchy = hierarchyElem.getIntegerValue();

        var error = (!isNaN(numberOfChildren) && !isNaN(hierarchy)
            && (numberOfChildren > 0) && (hierarchy > 0) && (hierarchy > numberOfChildren));

        $.setError(error, 'hierarchy-number-children', 'De rangorde mag nooit hoger zijn dan het aantal kinderen!');
        $.triggerChangeOfState();
    }

    $(document).ready(function () {
        validateHierarchyAndNoChildren();
        $('#mil\\.numberOfChildren').blur(validateHierarchyAndNoChildren);
        $('#mil\\.hierarchy').blur(validateHierarchyAndNoChildren);
    });
})(jQuery);