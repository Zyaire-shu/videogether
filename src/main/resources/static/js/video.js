$(document).ready(function (){
    var httpUrl;
    $("#stream").click(function (){
        let seek = $("#seekin").val();
        let loc = $("title").text();
        var info = {
            "videoLocation" : loc,
            "startTime" : 0
        };
        if (seek != ""){
             info = {
                "videoLocation" : loc,
                "startTime" : seek
            };
        }
        console.log(info);
        $.ajax({
            type: 'POST',
            url: serverAddress+'/stream',
            contentType: 'application/json',
            data: JSON.stringify(info),
            dataType: 'json',
            success: function(data, status) {
                console.log(data);
                if(data.isSuccess){
                    httpUrl = data.videoUrl;
                    alert(data.sMessage);
                    //window.location.replace("http://127.0.0.1:8090/streamList");
                }else{
                    alert(data.sMessage);
                }
            },
            error: function() {

            }
        })
    })
    $("#play").click(function (){
        if (httpUrl==null){
            let loc = $("title").text();
           // if (seek == null){
                let info = {
                    "videoLocation" : loc,
                };

            $.ajax({
                type: 'POST',
                url: serverAddress+'/playStream',
                contentType: 'application/json',
                data: JSON.stringify(info),
                dataType: 'json',
                success: function(data, status) {
                    console.log(data);
                    if(data.isSuccess){
                        httpUrl = data.videoUrl;
                        playVideo(httpUrl);
                        //alert(data.sMessage);
                        //window.location.replace("http://127.0.0.1:8090/streamList");
                    }else{
                        alert("播放失败");
                    }
                },
                error: function() {

                }
            })
        }else {
            playVideo(httpUrl);
        }

    })

})
function playVideo(httpUrl){
    // if (flvjs.isSupported()) {
    //     var videoElement = document.getElementById('videoElement');
    //     var flvPlayer = flvjs.createPlayer({
    //         type: 'flv',//rtmp://192.168.1.254:1935/live/rkFSTArvBN
    //         url: httpUrl
    //     });
    //     flvPlayer.attachMediaElement(videoElement);
    //     flvPlayer.load();
    //     flvPlayer.play();
    // }
    $('#player').attr('video-url',httpUrl+".flv");
        // 地址栏
       // var value = document.getElementById('value');
        //播放事件 传入地址播放


}