jQuery(document).ready(function(){
    jQuery('th.clickable').click( function ( ) {
        var table = jQuery(this).parents('table').eq(0)
        var rows = table.find ( 'tr:gt(0)' ).toArray ( ).sort(comparer(jQuery(this).index()))
        this.asc = ! this.asc
        if ( ! this.asc ) { rows = rows.reverse ( ) }
        for ( var i = 0 ; i < rows.length ; i ++ ) { table.append ( rows[ i ] ) }
    })
});

function comparer(index){
    return function ( a, b ) {
        var valA = getCellValue ( a, index ), valB = getCellValue ( b, index )
        return isNaN ( valA ) || isNaN ( valB ) ? valA.localeCompare ( valB ) : valA - valB
    }
}

function getCellValue(row, index){return jQuery(row).children ('td').eq ( index ).text ( )}