$(function (){
   ajaxCall();
});

var ajaxCall = function () {
    var ajaxcallBack = {
        success:onSuccess,
        error:onError
    };
    jsRoutes.controllers.Applictation.ajaxCall().ajax(ajaxcallBack)

};

var onSuccess = function (data) {
    alert(data)
}


var onError = function (error) {
    alert(error)
}