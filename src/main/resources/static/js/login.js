$(document).ready(function(){
    $("#login").click(function(){
        var name = $("#name").val();
        var pass = $("#pass").val();
        var info = {
            "userName" : name,
            "passWord" : pass
        }
        console.log(info);
        $.ajax({
            type: 'POST',
            url: serverAddress+'/user/login',
            contentType: 'application/json',
            data: JSON.stringify(info),
            dataType: 'json',
            success: function(data, status) {
                console.log(data);
                if(data.isSuccess){
                    alert(data.sMessage);
                    window.location.replace(serverAddress);
                }else{
                    alert(data.sMessage);
                }
            },
            error: function() {
                alert("出错了");
            }
        })
    })
})