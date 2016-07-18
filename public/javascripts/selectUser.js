let $menu = $('#someMenu');
$menu.on('show.bs.dropdown', function () {
    $.post({
        url: '/echo/json/',
        data: {
            json: JSON.stringify({
                list: [{id: 1, name: 'user1'}, {id: 2, name: 'user2'}]
            }),
            delay: 1
        },
        success: function(response) {
            drawMenu($menu, response);
        }
    });
})

drawMenu = function(menu, items) {
    let menuList = menu.find('ul');
    let menuListItems = items.list;
    for (let i in menuListItems) {
        let item = menuListItems[i];
        menuList
            .append('<li><a href="#">Load menu item "'+
                item.name+'" with id #'+item.id+'</a></li>');
    }
};