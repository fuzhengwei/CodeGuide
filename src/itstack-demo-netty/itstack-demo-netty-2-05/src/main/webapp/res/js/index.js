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

	socket = new WebSocket("ws://localhost:7397/websocket");

	socket.onmessage = function(event){

		var msg = JSON.parse(event.data);

		//链接信息;1自发信息、2群发消息
		if(1 == msg.type){
			jQuery.data(document.body, 'channelId', msg.channelId);
			return;
		}

		//链接信息;1自发信息、2群发消息
		if(2 == msg.type){

			var channelId =	msg.channelId;
			//自己
			if(channelId == jQuery.data(document.body, 'channelId')){
				var module = $(".msgBlockOwnerClone").clone();
				module.removeClass("msgBlockOwnerClone").addClass("msgBlockOwner").css({display: "block"});
				module.find(".headPoint").attr("src", "res/img/"+msg.userHeadImg);
				module.find(".msgBlock_msgInfo .msgPoint").text(msg.msgInfo);

				$("#msgPoint").before(module);

				util.divScroll();
			}
			//好友
			else{
				var module = $(".msgBlockFriendClone").clone();
				module.removeClass("msgBlockFriendClone").addClass("msgBlockFriend").css({display: "block"});
				module.find(".headPoint").attr("src", "res/img/"+msg.userHeadImg);
				module.find(".msgBlock_channelId").text("ID："+msg.channelId);
				module.find(".msgBlock_msgInfo .msgPoint").text(msg.msgInfo);
				$("#msgPoint").before(module);
				util.divScroll();
			}

		}


	};

	socket.onopen = function(event){
		console.info("打开WebSoket 服务正常，浏览器支持WebSoket!");
		var clientMsgProtocol = {};
		clientMsgProtocol.type = 1;
		clientMsgProtocol.msgInfo = "请求个人信息";
		socket.send(JSON.stringify(clientMsgProtocol));
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
			var clientMsgProtocol = {};
			clientMsgProtocol.type = 2;
			clientMsgProtocol.msgInfo = $("#sendBox").val();
			socket.send(JSON.stringify(clientMsgProtocol));
			$("#sendBox").val("");
		}else{
			alert("WebSocket 连接没有建立成功！");
		}
	},
	divScroll: function(){
		 var div = document.getElementById('show'); 
		div.scrollTop = div.scrollHeight; 
	}	
	
};