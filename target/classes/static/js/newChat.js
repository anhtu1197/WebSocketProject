var socket;
if(!window.WebSocket) {
	window.WebSocket = window.MozWebSocket;
}

var chars = ['0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];

function generateMixed(n) {
    var res = "";
    for(var i = 0; i < n ; i ++) {
        var id = Math.ceil(Math.random()*35);
        res += chars[id];
    }
    return res;
}
//gửi ảnh
if(window.WebSocket) {
	socket = new WebSocket("ws://localhost:8090/ws");
	socket.onmessage = function(event) {
        var msg = event.data;
        console.log(msg);
        if(msg instanceof Blob){
            console.log("blobs");
            var idran = generateMixed(3);
            // ta = "<div class=\"speech-bubble-ds \">" +
            // "<img id='\"+idran+\"' src='' class='chatimg'>"
            // + "</div>";
            var ta = "<div class='msgCente speech-bubble-ds'><img id='"+idran+"' src='' class='chatimg'></div>";
            $('.his').append(ta);
            //var previewImg = document.querySelector('img');
            var previewImg = document.getElementById(idran);

            var reader = new FileReader();
            reader.addEventListener("load", function () {
                previewImg.src = reader.result;
            }, false);
            reader.readAsDataURL(msg);
        }
        var data = event.data;
        data  = data.split("-")
        var username = data[1];
        var msg = data[0];
        var showingMess = "<div class=\"speech-bubble-ds \" >" +
                                "<p><strong>" +username + "</strong></p>" +
                                "<p>" + msg+ "</p>"
                                + "<div class=\"speech-bubble-ds-arrow\"></div>";
        $('.his').append(showingMess);


        // if(msg.substring(0, 1) == '[') {
        //      msg = event.data;
        //     var ta = "<div class='msgLeft'><span>"+event.data+"</span></div>";
        //    // $('.chat').append(ta);
        //     //alert('Data' + msg);
        // } else {
        //     username = event.data;
        //     alert('Username =' + username);
        //     var ta = "<div class='msgLeft'><span>"+event.data+"</span></div>";
        //    // $('.chat').append(ta);
        //     //(ta);
        // }
	};
	socket.onopen = function(event) {
        $('.tips').html('Kết nối đã được mở！');
        $('.tips').css('color', 'green');

		var his = $('#TTHistory');

		console.log(msg);
		his.html(msg);
	};
	socket.onclose = function(event) {
        $('.tips').html('Kết nối đóng');
        $('.tips').css('color', 'red');
	};
} else {
	var ta = "<div>Browser của bạn không hỗ trợ WebSocket！</div>";
		$('.content_bodyer').append(ta);
}

function send(message) {
	if(!window.WebSocket) {
		return;
	}
	if(socket.readyState == WebSocket.OPEN) {
		socket.send(message);
	} else {
		alert("Kết nối không được mở.");
	}
}

window.onbeforeunload = function(event) {
	event.returnValue = "Refresh";
}

;
// document.onkeydown = function(e) {
//
// 	var userName = $('#userName');
// 	if(e.keyCode == 13) {
//         $('.chat').css('bottom', 0);
// 		var message = userName.val() + '-' + $('.msg').val().trim();
// 		send(message);
//         $('.msg').val('');
// 	}
// }

function sendd() {
    var userName = $('#userName');
    var message = userName.val() + '-' + $('.msg').val().trim();
    send(message);
    sendFile();
    $('.msg').val('');
}

$('.openHistory').click(function() {
    $('.history').fadeToggle();
});

$('.content_bodyer').mouseenter(function() {
    $('.scrollbar').fadeIn();
})

$('.content_bodyer').mouseleave(function() {
    $('.scrollbar').fadeOut();
})

function sendFile(){
    var thum = $('#file')[0].files[0];
    if(!thum) return;
    console.log(thum);
    var reader = new FileReader();
    reader.readAsArrayBuffer(thum);
    reader.onload = function loaded(evt) {
        console.log(evt);
        var blob = evt.target.result;
        socket.send(blob);
        console.log(blob);

        console.log("finnish");
    }
}

var thumb = $('.thumb');
var scrollBar = $('.scrollbar');
var chat = $('.chat');

thumb.on('mousedown', function(e) {
    chat.css('bottom', 'initial');
    thumb.isMouseDown = true;
    return false;
});

thumb.on('selectstart', function() {
    return false;
});

$(window).on('mouseup', function(e) {
    thumb.isMouseDown = false;
});

$(window).on('mousemove', function(e) {
    if (thumb.isMouseDown){
        var pos = e.clientY - scrollBar.parent().offset().top - thumb.height() / 2;
        if (pos < 0 ){
            pos = 0;
        } else if(pos > scrollBar.height() - thumb.height()){
            pos = scrollBar.height() - thumb.height();
        }
        thumb.css("top", pos + "px");

        var percentage = thumb.offset().top / (scrollBar.height() - thumb.height());
        var top = (chat.height() - chat.parent().height()) * percentage;
        chat.css("top", -top + "px");
    }
})


