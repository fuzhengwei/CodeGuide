// JavaScript Document
var socket;
$(function(){
	

	if(!window.WebSocket){
		window.WebSocket = window.MozWebSocket;
	}
	
	if(!window.WebSocket){
		alert("您的浏览器不支持WebSocket协议！推荐使用谷歌浏览器进行测试。");
		return;
	}

	socket = new WebSocket("ws://localhost:7398/websocket");

	socket.onmessage = function(event){

		var msg = JSON.parse(event.data);
		console.info(msg);
		
		$("#msgBox").val($("#msgBox").val() + event.data + "\r\n");

	};

	socket.onopen = function(event){
		console.info("打开WebSoket 服务正常，浏览器支持WebSoket!");		
 	};

	socket.onclose = function(event){
		console.info("WebSocket 关闭");
	};

	document.onkeydown = function(e) {
		//console.log(e.ctrlKey);
		if (13 == e.keyCode && e.ctrlKey)
		{
			//console.log(c1);
			util.send();
		}
	}

});

util = {
	send: function(){
		if(!window.WebSocket){return;}
		if(socket.readyState == WebSocket.OPEN){
			var infoProtocol = {};
			infoProtocol.channelId = $("#wsChannelId").val();
			infoProtocol.msgType = 2;
			var queryInfoReq = {};
			queryInfoReq.stateType = $("#wsMsg").val();
			infoProtocol.msgObj = queryInfoReq;
			socket.send(JSON.stringify(infoProtocol));
		}else{
			alert("WebSocket 连接没有建立成功！");
		}
	},
	divScroll: function(){
		var div = document.getElementById('show'); 
		div.scrollTop = div.scrollHeight; 
	}	
	
};