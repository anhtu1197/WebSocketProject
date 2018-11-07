<!DOCTYPE html>
<html>
<#include "../common/header.ftl">
<body>
<div class="page">
    <header class="bar bar-nav">
        <h1 class="title">Ứng dụng chat</h1>
    </header>
    <nav class="bar bar-tab">
        <a class="tab-item" href="/susu/su/home">
            <span class="icon icon-message"></span>
            <span class="tab-label">Thông tin cá nhân</span>
        </a>
        <a class="tab-item" href="/susu/su/chat">
            <span class="icon icon-friends"></span>
            <span class="tab-label">Danh bạ</span>
        </a>
        <a class="tab-item active" href="#">
            <span class="icon icon-browser"></span>
            <span class="tab-label">Tìm kiếm</span>
        </a>
        <a class="tab-item" href="/susu/su/me">
            <span class="icon icon-me"></span>
            <span class="tab-label">Trang cá nhân</span>
        </a>
    </nav>
    <div class="content">
        <div class="list-block">
            <ul class="list-container">
                <li class="item-content">
                    <div class="item-inner">
                        <span class="icon icon-app"></span><div class="item-title">Danh sách bạn bè</div>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</div>
<#include "../common/floor.ftl">
</body>
</html>