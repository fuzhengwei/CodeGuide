<%--
  虫洞栈：https://bugstack.cn
  公众号：bugstack虫洞栈  ｛获取学习源码｝
  Create by fuzhengwei on 2019
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<title>关注公众号：bugstack虫洞栈 | 专题案例开发，关注取源码 | bugstack.cn 付政委</title>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="res/js/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="res/js/themes/icon.css">
<script type="text/javascript" src="res/js/jquery.min.js"></script>
<script type="text/javascript" src="res/js/jquery.easyui.min.js"></script>

<style>

</style>

<script>
util = {
  formatDate: function (value, row, index) {
      if (null == value) return "";
      var date = new Date();
      date.setTime(value);
      return date.format('yyyy-MM-dd HH:mm:ss');
  }
};
</script>

</head>

<body>
	<div style="margin:20px 0;"></div>
	<table class="easyui-datagrid" title="localhost:${serverPort} | Netty服务端" style="width:700px;height:250px"
			data-options="rownumbers:true,singleSelect:true,url:'/queryNettyServerList',method:'get',toolbar:toolbar">
		<thead>
			<tr>
				<th data-options="field:'ip'">IP</th>
				<th data-options="field:'port'">端口</th>
				<th data-options="field:'openDate'">启动时间</th>
			</tr>
		</thead>
	</table>
	<script type="text/javascript">
		var toolbar = [{
			text:'启动',
			iconCls:'icon-open',
			handler:function(){
			    $.post('/openNettyServer',{}, function (res) {
                               if (res.success) {
                                  $.messager.show({
                                        title: '消息提示',
                                        msg: '启动成功，请稍后刷新页面！'
                                  });
                                   $('#easyui-datagrid').datagrid('reload');
                               } else {
                                   $.messager.show({
                                       title: 'Error',
                                       msg: res.msg
                                   });
                               }
                            }, 'json');
			}
		},'-',{
			text:'关闭',
			iconCls:'icon-close',
			handler:function(){
			     //可以自己添加实现
			}
		}];
	</script>
    <hr/>
	<!-- server-user -->
    <table class="easyui-datagrid" title="localhost:${serverPort} | 用户链接信息" style="width:700px;height:250px"
    			data-options="rownumbers:true,singleSelect:true,url:'/queryUserChannelInfoList',method:'get'">
    		<thead>
    			<tr>
    				<th data-options="field:'ip'">IP</th>
    				<th data-options="field:'port'">端口</th>
    				<th data-options="field:'channelId'">用户ID</th>
    				<th data-options="field:'linkDate'">链接时间</th>
    			</tr>
    		</thead>
    </table>
</body>
</html>