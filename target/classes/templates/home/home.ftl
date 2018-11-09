<!DOCTYPE html>
<html>
<#include "../common/header.ftl">
<style type="text/css">
    .infinite-scroll-preloader {
        margin-top:-20px;
    }
</style>

<body>
<div class="page">
    <header class="bar bar-nav">
        <h1 class="title">Ứng dụng chat</h1>
    </header>
    <nav class="bar bar-tab">
        <a class="tab-item active" href="#">
            <span class="icon icon-message"></span>
            <span class="tab-label">Chatting</span>
        </a>
        <a class="tab-item" href="/susu/">
            <span class="icon icon-friends"></span>
            <span class="tab-label">Online</span>
        </a>
        <a class="tab-item" href="/susu/su/find">
            <span class="icon icon-browser"></span>
            <span class="tab-label">Tìm kiếm</span>
        </a>
        <a class="tab-item" href="/susu/su/me">
            <span class="icon icon-me"></span>
            <span class="tab-label">Trang cá nhân</span>
        </a>
    </nav>
    <div class="content">
        <form role="form" id="rec" hidden>
        </form>
        <#--<div class="content infinite-scroll infinite-scroll-bottom" data-distance="100">-->
            <div class="list-block">
                <ul class="list-container">
                    <li class="item-content" onclick="allchat()">
                        <div class="item-inner">
                            <img style="height: 70px;width: 70px;" src="/susu/image/logoSmall.png"><div class="item-title">Chat với tất cả người dùng</div>
                        </div>
                    </li>
                </ul>
            </div>
            <#--<div class="infinite-scroll-preloader">-->
                <#--<div class="preloader"></div>-->
            <#--</div>-->
        <#--</div>-->
    </div>
</div>
<#include "../common/floor.ftl">
<script type="text/javascript">
    function allchat() {
        document.getElementById('rec').action = "http://localhost:8080/susu/chat/netty";
        document.getElementById('rec').method = "GET";
        document.getElementById('rec').submit();
    }
</script>
</body>
</html>