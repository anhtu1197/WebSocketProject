<!DOCTYPE html>
<html>
<#include "../common/header.ftl">
<style type="text/css">
    .infinite-scroll-preloader {
        margin-top:-20px;
    }
</style>
<link rel="stylesheet" type="text/css" href="/susu/css/allchat.css"/>
<body>
<div class="page">
    <header class="bar bar-nav">
        <a class="button button-link button-nav pull-left" href="/susu/su/home" data-transition='slide-out'>
            <span class="icon icon-left"></span>
        </a>
        <h1 class="title">Phòng chat công cộng</h1>
    </header>
    <input hidden id="userName" value="${userName!''}">
    <div class="content infinite-scroll" style="height: 78%;">
        <div class="his" id="TTHistory"> </div>
        <div class="chat"></div>
    </div>
    <div style="position: absolute;bottom: 0px;width: 100%;">
        <div class="row">
            <div class="col-85">
                <input class='msg' style="height: 50px;width: 100%;" id="message" >
            </div>
            <div class="col-35" style="width: auto" ><a href="#" class="button button-big button-fill button-success" style="width: 100%"  onclick="sendd()">Gửi</a></div>
        </div>
        <div class="col-120" style="width: 100%"><input class="button button-big button-fill button-success" style="width: 100%" type="file" id="file" value="Hình ảnh"></div>
        <#--<img src="" height="200" alt="Image preview area..." title="preview-img">-->
    </div>
</div>
<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<script src='/susu/js/newChat.js'></script>
<#include "../common/floor.ftl">
<script>
    var msg = '';
    <#list userMsgList as userMsg>
    msg = msg +'<div class="speech-bubble-ds ">' +
            ' <p><strong>'+ '${userMsg.name!''}'+'</strong></p>' +
            '<p>'+'${userMsg.msg!''}' +'</p></div>' +
            '<div class="speech-bubble-ds-arrow"></div>'
            ;

    </#list>
    msg = msg+'<div class="continue msgCente">--- Toàn bộ lịch sử Chat ---</div>';
</script>
<div class = "continue"></div>
</body>
</html>