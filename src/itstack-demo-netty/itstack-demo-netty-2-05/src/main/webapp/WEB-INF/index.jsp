<%--
  虫洞栈：https://bugstack.cn
  公众号：bugstack虫洞栈  ｛获取学习源码｝
  Create by fuzhengwei on 2019
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<title>关注公众号bugstack虫洞栈，获取学习源码。bugstack.cn 付政委</title>

<script type="text/javascript" src="res/js/jquery.min.js"></script>
<script type="text/javascript" src="res/js/jquery.serialize-object.min.js"></script>
<script type="text/javascript" src="res/js/index.js"></script>

<style>
body{
	background-image:url(res/img/bg.jpg);
	background-repeat:no-repeat;
	background-size:cover;
	font-family:”微软雅黑”;
}


#chatDiv{
	position: relative;
	margin:0 auto;
	margin-top:150px;
	width:839px;
	height:667px;
	background-color:#CCCCCC;
	border-radius:3px;-moz-border-radius:3px;
}

</style>

</head>

<body>

<div id="chatDiv">
	<!-- left -->
	<div style="width:60px; height:667px; background-color:#2D2B2A; float:left;">
		<!-- 头像 -->
		<div style="width:35px; height:35px; margin:0 auto; margin-top:19px; margin-left:12px; float:left; border:1px solid #666666;border-radius:3px;-moz-border-radius:3px;">
			<img src="res/img/bugstack.png" width="35px" height="35px"/>
		</div>

		<!-- 聊天 -->
		<div style="width:28px; height:28px; margin:0 auto; margin-top:25px; margin-left:16px; float:left;">
			<img src="res/img/chat.png" width="28px" height="28px"/>
		</div>

		<!-- 好友 -->
		<div style="width:28px; height:28px; margin:0 auto; margin-top:20px; margin-left:16px; float:left;">
			<img src="res/img/friend.png" width="28px" height="28px"/>
		</div>

		<!-- 收藏 -->
		<div style="width:28px; height:28px; margin:0 auto; margin-top:20px; margin-left:16px; float:left;">
			<img src="res/img/collection.png" width="28px" height="28px"/>
		</div>

		<!-- 设置 -->
		<div style="width:20px; height:20px; margin:0 auto; margin-left:20px; float:left; position:absolute;bottom:0; margin-bottom:12px;">
			<img src="res/img/set.png" width="20px" height="20px"/>
		</div>

	</div>

	<!-- center -->
	<div style="width:250px; height:667px; background-color:#EBE9E8; float:left;">
		<div style=" background-image:url(res/img/search.png); background-repeat:no-repeat;margin:0 auto; margin-top:25px; padding-top:5px; padding-bottom:5px; width:210px; background-color:#DBD9D8;border-radius:3px;-moz-border-radius:3px; float:left; margin-left:20px; font-size:12px; color:#333333;text-indent:27px;">
			https://bugstack.cn
		</div>

		<!-- friendList -->
		<div id="friendList" style="float:left; margin-top:5px;">
			<div style="width:250px; height:65px;">
				<table style="width:240px; height:60px; margin:0 auto; margin-top:2px; background-color:#E5E5E5;">
					<tr>
						<td rowspan="2" width="50"><img src="res/img/head1.jpg" width="50px" height="50px" style="border-radius:3px;-moz-border-radius:3px;"/></td>
						<td style="color:#333333; text-indent:5px; font-size:12px; vertical-align:bottom; font-weight:bolder;">哪咤宝</td>
					</tr>
					<tr>
						<td style="color:#999999; text-indent:5px; font-size:10px;">付政委：[图片]</td>
					</tr>
				</table>

				<table style="width:240px; height:60px; margin:0 auto; margin-top:2px; background-color:#C9C7C6;">
					<tr>
						<td rowspan="2" width="50"><img src="res/img/head2.jpg" width="50px" height="50px" style="border-radius:3px;-moz-border-radius:3px;"/></td>
						<td style="color:#333333; text-indent:5px; font-size:12px; vertical-align:bottom; font-weight:bolder;">bugstack虫洞栈</td>
					</tr>
					<tr>
						<td style="color:#999999; text-indent:5px; font-size:10px;">北京程序猿-小白：netty开发..</td>
					</tr>
				</table>

				<table style="width:240px; height:60px; margin:0 auto; margin-top:2px; background-color:#E5E5E5;">
					<tr>
						<td rowspan="2" width="50"><img src="res/img/head3.jpg" width="50px" height="50px" style="border-radius:3px;-moz-border-radius:3px;"/></td>
						<td style="color:#333333; text-indent:5px; font-size:12px; vertical-align:bottom; font-weight:bolder;">咸鱼江湖</td>
					</tr>
					<tr>
						<td style="color:#999999; text-indent:5px; font-size:10px;">背包冲：情人节没礼物，不存..</td>
					</tr>
				</table>

				<table style="width:240px; height:60px; margin:0 auto; margin-top:2px; background-color:#E5E5E5;">
					<tr>
						<td rowspan="2" width="50"><img src="res/img/head4.jpg" width="50px" height="50px" style="border-radius:3px;-moz-border-radius:3px;"/></td>
						<td style="color:#333333; text-indent:5px; font-size:12px; vertical-align:bottom; font-weight:bolder;">整条街最靓</td>
					</tr>
					<tr>
						<td style="color:#999999; text-indent:5px; font-size:10px;">公司-老板：[文件]下个Q的KPI</td>
					</tr>
				</table>

				<table style="width:240px; height:60px; margin:0 auto; margin-top:2px; background-color:#E5E5E5;">
					<tr>
						<td rowspan="2" width="50"><img src="res/img/head5.jpg" width="50px" height="50px" style="border-radius:3px;-moz-border-radius:3px;"/></td>
						<td style="color:#333333; text-indent:5px; font-size:12px; vertical-align:bottom; font-weight:bolder;">Sniper</td>
					</tr>
					<tr>
						<td style="color:#999999; text-indent:5px; font-size:10px;">Sniper：雨后天晴写下，年华..</td>
					</tr>
				</table>

				<table style="width:240px; height:60px; margin:0 auto; margin-top:2px; background-color:#E5E5E5;">
					<tr>
						<td rowspan="2" width="50"><img src="res/img/head7.jpg" width="50px" height="50px" style="border-radius:3px;-moz-border-radius:3px;"/></td>
						<td style="color:#333333; text-indent:5px; font-size:12px; vertical-align:bottom; font-weight:bolder;">星星点灯照亮我的家门</td>
					</tr>
					<tr>
						<td style="color:#999999; text-indent:5px; font-size:10px;">王老板：不吹牛的说我家77套..</td>
					</tr>
				</table>

				<table style="width:240px; height:60px; margin:0 auto; margin-top:2px; background-color:#E5E5E5;">
					<tr>
						<td rowspan="2" width="50"><img src="res/img/head8.jpg" width="50px" height="50px" style="border-radius:3px;-moz-border-radius:3px;"/></td>
						<td style="color:#333333; text-indent:5px; font-size:12px; vertical-align:bottom; font-weight:bolder;">詹姆斯·高斯林</td>
					</tr>
					<tr>
						<td style="color:#999999; text-indent:5px; font-size:10px;">詹姆斯·高斯林：我所说的都关..</td>
					</tr>
				</table>

				<table style="width:240px; height:60px; margin:0 auto; margin-top:2px; background-color:#E5E5E5;">
					<tr>
						<td rowspan="2" width="50"><img src="res/img/head9.jpg" width="50px" height="50px" style="border-radius:3px;-moz-border-radius:3px;"/></td>
						<td style="color:#333333; text-indent:5px; font-size:12px; vertical-align:bottom; font-weight:bolder;">叮裤猫</td>
					</tr>
					<tr>
						<td style="color:#999999; text-indent:5px; font-size:10px;">叮裤猫：那还第一次见</td>
					</tr>
				</table>

				<table style="width:240px; height:60px; margin:0 auto; margin-top:2px; background-color:#E5E5E5;">
					<tr>
						<td rowspan="2" width="50"><img src="res/img/head10.jpg" width="50px" height="50px" style="border-radius:3px;-moz-border-radius:3px;"/></td>
						<td style="color:#333333; text-indent:5px; font-size:12px; vertical-align:bottom; font-weight:bolder;">背锅冲</td>
					</tr>
					<tr>
						<td style="color:#999999; text-indent:5px; font-size:10px;">背锅冲：大树说的，不让去。</td>
					</tr>
				</table>
			</div>
		</div>

	</div>


	<!-- chat -->
	<div id="chat" style="width:529px; height:667px; background-color:#F5F5F5; float:right;">
		<div style="width:16px; height:16px; background-image:url(res/img/exit.png); background-repeat:no-repeat; float:right; margin-top:10px; margin-right:30px;"></div>
		<div style="width:16px; height:16px; background-image:url(res/img/min.png); background-repeat:no-repeat; float:right; margin-top:10px; margin-right:12px;"></div>
		<div style="border-bottom:1px #E7E7E7 solid;width:509px; padding-top:0px; padding-left:20px; padding-bottom:10px; font-size:18px; font-weight:bolder;float:left;">
			bugstack虫洞栈（1024）
		</div>

		<!-- 会话区域 begin -->
		<div id="show" style="width:529px; height:450px; float:left;overflow-y:scroll;">

			<!-- 消息块；系统 -->
			<div class="msgBlockSystem" style="margin-left:30px; margin-top:15px; width:340px; height:auto; margin-bottom:15px; float:left;">
				<div class="msgBlock_userHeadImg" style="float:left; width:35px; height:35px;border-radius:3px;-moz-border-radius:3px; background-color:#FFFFFF;">
					<img class="point" src="res/img/head0.jpg" width="35px" height="35px" style="border-radius:3px;-moz-border-radius:3px;"/>
				</div>

				<div class="msgBlock_channelId" style="float:left; width:100px; margin-top:-5px; margin-left:10px; padding-bottom:2px; font-size:10px;">
					bugstack虫洞栈
				</div>

				<div class="msgBlock_msgInfo" style="height:auto;width:280px;float:left;margin-left:12px; margin-top:4px;border-radius:3px;-moz-border-radius:3px; ">
					<div style="width:4px; height:20px; background-color:#CC0000; float:left;border-radius:3px;-moz-border-radius:3px;"></div>
					<div class="point" style="float:left;width:260px; padding:7px; background-color:#FFFFFF; border-radius:3px;-moz-border-radius:3px; height:auto; font-size:12px;display:block;word-break: break-all;word-wrap: break-word;">
						微信公众号：bugstack虫洞栈，欢迎您的关注&获取源码！https://bugstack.cn
<br/><br/>
本公众号会每天定时推送科技资料；专题、源码、书籍、视频、咨询、面试、环境等方面内容。尤其在技术专题方面会提供更多的原创内容，让更多的程序员可以从最基础开始了解到技术全貌，目前已经对外提供的有；《手写RPC框架》、《用Java实现JVM》、《基于JavaAgent的全链路监控》、《Netty案例》等专题。
<br/><br/>
不平凡的岁月终究来自你每日不停歇的刻苦拼搏，犹如；承遇朝霞，年少正恰，整装戎马，刻印风华。 <hr/><img width="260" height="260" src="res/img/gzh.jpg" />
					</div>
				</div>
			</div>

			<!-- 消息块；好友 -->
			<div class="msgBlockFriendClone" style=" display:none; margin-left:30px; margin-top:15px; width:340px; height:auto; margin-bottom:15px; float:left;">
				<div class="msgBlock_userHeadImg" style="float:left; width:35px; height:35px;border-radius:3px;-moz-border-radius:3px; background-color:#FFFFFF;">
					<img class="headPoint" src="res/img/head5.jpg" width="35px" height="35px" style="border-radius:3px;-moz-border-radius:3px;"/>
				</div>

				<div class="msgBlock_channelId" style="float:left; width:100px; margin-top:-5px; margin-left:10px; padding-bottom:2px; font-size:10px;">
					<!-- 名称 -->
				</div>

				<div class="msgBlock_msgInfo" style="height:auto;width:280px;float:left;margin-left:12px; margin-top:4px;border-radius:3px;-moz-border-radius:3px; ">
					<div style="width:4px; height:20px; background-color:#CC0000; float:left;border-radius:3px;-moz-border-radius:3px;"></div>
					<div class="msgPoint" style="float:left;width:260px; padding:7px; background-color:#FFFFFF; border-radius:3px;-moz-border-radius:3px; height:auto; font-size:12px;display:block;word-break: break-all;word-wrap: break-word;">
						<!-- 信息 -->
					</div>
				</div>
			</div>

			<!-- 消息块；自己 -->
			<div class="msgBlockOwnerClone" style=" display:none; margin-right:30px; margin-top:15px; width:340px; height:auto; margin-bottom:15px; float:right;">

				<div style="float:right; width:35px; height:35px;border-radius:3px;-moz-border-radius:3px; background-color:#FFFFFF;">
					<img class="headPoint" src="res/img/head3.jpg" width="35px" height="35px" style="border-radius:3px;-moz-border-radius:3px;"/>
				</div>

				<div class="msgBlock_msgInfo" style="height:auto;width:280px;float:left;margin-left:12px; margin-top:4px;border-radius:3px;-moz-border-radius:3px; ">
					<div class="msgPoint" style="float:left;width:260px; padding:7px; background-color:#FFFFFF; border-radius:3px;-moz-border-radius:3px; height:auto; font-size:12px;display:block;word-break: break-all;word-wrap: break-word;">
						<!-- 信息 -->
					</div>
					<div style="width:4px; height:20px; background-color:#CC0000; float:right;border-radius:3px;-moz-border-radius:3px;"></div>
				</div>

			</div>

			<span id="msgPoint"></span>

		</div>
		<!-- 会话区域 end -->
		<div style="width:100%; height:2px; float:left; background-color:#CCCCCC;"></div>
		<div style="margin:0 auto; width:100%; height:149px; margin-top:5px;  background-color:#FFFFFF; float:left;">
			<textarea id="sendBox" style="font-size:14px; border:0; width:499px; height:80px; outline:none; padding:15px;font-family:”微软雅黑”;resize: none;"></textarea>

			<div style="margin-top:20px; float:right; margin-right:35px; padding:5px; padding-left:15px; padding-right:15px; font-size:12px; background-color:#F5F5F5;border-radius:3px;-moz-border-radius:3px; cursor:pointer;" onclick="javascript:util.send();">发送(S)</div>
		</div>
	</div>

</div>


</body>
</html>