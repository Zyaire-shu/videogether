$(document).ready(function(){
    $("#username").keyup(function() {
        var t = $("#username").val();
        if(t==""){
            $("#cc").css("color", "#999").text("用户名");
            return;
        }
        console.log(t);
        var info = {
            "userName": t,
        };
        console.log(info);
        $.ajax({
            type: 'POST',
            url: serverAddress+'/user/check',
            contentType: 'application/json',
            data: JSON.stringify(info),
            dataType: 'json',
            success: function(data, status) {
                console.log(data.userCount);
                var a = data.userCount;
                if (a == 1) {
                    $("#cc").css("color", "red").text("用户已存在");
                    $("#registerBtn").prop('disabled', true);
                } else {
                    $("#cc").css("color", "green").text("用户不存在");
                    $("#registerBtn").prop('disabled', false);
                }

            },
            error: function() {

            }
        })
    });
    $(".passin").keyup(function(){
        var enoughRegex = new RegExp("(?=.{6,}).*", "g");
        var pass = $("#password");
        var passRepeat = $("#password-repeat");
        if(pass.val()=="" && passRepeat.val()==""){
            $("#result").css("color","#999").text("重复密码");
            return;
        }
        if(pass.val()==passRepeat.val()){
            $("#result").css("color","green").text("密码一致");
            if (false === enoughRegex.test($(this).val())) {
                $("#registerBtn").prop('disabled', true);
                //密码小于六位的时候，密码强度图片都为灰色
            }else {
                $("#registerBtn").prop('disabled', false);
            }
        }else{
            $("#result").css("color","red").text("密码不一致");
            $("#registerBtn").prop('disabled', true);
        }
    });
    $("#password").keyup(function (){
        var strongRegex = new RegExp("^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$", "g");
        var mediumRegex = new RegExp("^(?=.{7,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$", "g");
        var enoughRegex = new RegExp("(?=.{6,}).*", "g");

        if (false === enoughRegex.test($(this).val())) {
            $("#passwordInit").text("密码太短");
            $("#registerBtn").prop('disabled', true);
            //密码小于六位的时候，密码强度图片都为灰色
        }
        else if (strongRegex.test($(this).val())) {
            $("#passwordInit").text("强度最强");
            //密码为八位及以上并且字母数字特殊字符三项都包括,强度最强
        }
        else if (mediumRegex.test($(this).val())) {
            $("#passwordInit").text("强度中等");
            //密码为七位及以上并且字母、数字、特殊字符三项中有两项，强度是中等
        }
        else {
            $("#passwordInit").text("强度太弱");
            //如果密码为6为及以下，就算字母、数字、特殊字符三项都包括，强度也是弱的
        }
    });
    $("#registerBtn").click(function(){
        let name = $("#username").val();
        let pass = $("#password-repeat").val();
        if (name.replace(" ","")==="" || pass.replace(" ","")===""){
            alert("输入不能为空");
            return;
        }
        var info = {
            "userName" : name,
            "passWord" : pass
        };

        console.log(info);
        $.ajax({
            type: 'POST',
            url: serverAddress+'/user/register',
            contentType: 'application/json',
            data: JSON.stringify(info),
            dataType: 'json',
            success: function(data, status) {
                console.log(data);
                if(data.isSuccess){
                    alert("注册成功");
                    window.location.replace(serverAddress);
                }else{
                    alert("注册失败");
                }
            },
            error: function() {

            }
        })
    })
})