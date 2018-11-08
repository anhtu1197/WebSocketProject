var socket;
if(!window.WebSocket) {
	window.WebSocket = window.MozWebSocket;
}

if(window.WebSocket) {
	socket = new WebSocket("ws://localhost:8090/ws");
	socket.onmessage = function(event) {
		var ta = document.getElementById('responseText');
		ta.value = ta.value + '\n' + event.data
	};
	socket.onopen = function(event) {
		var ta = document.getElementById('responseText');
        if(msg.length > 0){
            ta.value = "--- Mở bộ sưu tập! ---"+'\n'+msg;
        }else{
            ta.value = "---  Mở bộ sưu tập! ---"
        }
	};
	socket.onclose = function(event) {
		var ta = document.getElementById('responseText');
		ta.value = ta.value + "Kết nối đã bị đóng";
	};
} else {
	alert("Browser của bạn không hỗ trợ WebSocket！");
}

function send(message) {
	if(!window.WebSocket) {
		return;
	}
	if(socket.readyState == WebSocket.OPEN) {
		socket.send(message);
	} else {
		alert("Không thể mở kết nối.");
	}
}

window.onbeforeunload = function(event) {
	event.returnValue = "Refresh";
}

;
document.onkeydown = function(e) {
    var userName = document.getElementById('userName');
	if(e.keyCode == 13) {
		var message = userName.value + '-' +document.getElementsByClassName('msg')[0].value;
		send(message);
        document.getElementsByClassName('msg')[0].value = '';
	}
}