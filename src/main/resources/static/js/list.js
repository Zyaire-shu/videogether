$(document).ready(function () {

})
function play(url){
   // var location = $(play).prev().text();
    console.log("url");
    console.log(url);
    window.location.replace(serverAddress+"/video?location=" + encodeURIComponent(url));
}
function kill(url){

    var info = {
        "location":url
    }
    $.ajax({
        type: 'POST',
        url: serverAddress+'/kill',
        contentType: 'application/json',
        data: JSON.stringify(info),
        dataType: 'json',
        success: function(data, status) {
            console.log(data);
            if(data.isKill){
                alert("删除成功");
            }else{
                alert("删除失败");
            }
            window.location.reload();
        },
        error: function() {

        }
    })
}