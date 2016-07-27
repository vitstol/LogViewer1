jQuery(function(){
    function sumTotal (selector) {
        jQuery(selector).each(function () {
            var total = 0,
                column = jQuery(this).siblings(selector).andSelf().index(this);
            jQuery(this).parents().prevUntil(':has(' + selector + ')').each(function () {
                total += parseFloat(jQuery('td.sum:eq(' + column + ')', this).html()) || 0;
            });
            jQuery(this).html(total);
        });
    }
    sumTotal('td.total');
});
