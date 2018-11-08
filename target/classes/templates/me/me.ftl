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
            <span class="tab-label">Thông tin</span>
        </a>
        <a class="tab-item" href="/susu/su/chat">
            <span class="icon icon-friends"></span>
            <span class="tab-label">Danh sách bạn bè</span>
        </a>
        <a class="tab-item" href="/susu/su/find">
            <span class="icon icon-browser"></span>
            <span class="tab-label">Tìm kiếm</span>
        </a>
        <a class="tab-item active" href="#">
            <span class="icon icon-me"></span>
            <span class="tab-label">Cá nhân</span>
        </a>
    </nav>
    <div class="content">
        <div class="list-block">
            <ul class="list-container">
                <li class="item-content">
                    <div class="item-inner">
                        <img style="height: 70px;width: 70px;" src="/susu/image/logoSmall.png"><div class="item-title">${userName!''}</div>
                    </div>
                </li>
            </ul>
        </div>
        <div style="height: 10px;"></div>
        <div class="list-block">
            <ul class="list-container">
                <li class="item-content">
                    <div class="item-inner">
                        <span class="icon icon-gift"></span><div class="item-title">Tập hợp</div>
                    </div>
                </li>
            </ul>
        </div>
        <div class="list-block">
            <ul class="list-container">
                <li class="item-content">
                    <div class="item-inner">
                        <span class="icon icon-picture"></span><div class="item-title">Album</div>
                    </div>
                </li>
            </ul>
        </div>
        <div class="list-block">
            <ul class="list-container">
                <li class="item-content">
                    <div class="item-inner">
                        <span class="icon icon-card"></span><div class="item-title">Card Package</div>
                    </div>
                </li>
            </ul>
        </div>
        <div class="list-block">
            <ul class="list-container">
                <li class="item-content">
                    <div class="item-inner">
                        <span class="icon icon-emoji"></span><div class="item-title">Expression</div>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</div>
<#include "../common/floor.ftl">
</body>
</html>