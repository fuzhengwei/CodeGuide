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
       data-options="rownumbers:true,singleSelect:true,url:'/queryNettyServerList',method:'get'">
    <thead>
    <tr>
        <th data-options="field:'ip'">IP</th>
        <th data-options="field:'port'">端口</th>
        <th data-options="field:'openDate'">启动时间</th>
    </tr>
    </thead>
</table>
<script type="text/javascript">
    var serverToolbar = [{
        text: '启动',
        iconCls: 'icon-open',
        handler: function () {
            $.post('/openNettyServer', {}, function (res) {
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
    }];
</script>
<hr/>
<!-- device-list -->
<table id="deviceDg" class="easyui-datagrid" title="localhost:${serverPort} | 用户链接信息" style="width:700px;height:250px"
       data-options="rownumbers:true,singleSelect:true,url:'/queryDeviceList',method:'get',toolbar:deviceToolbar">
    <thead>
    <tr>
        <th data-options="field:'channelId'">channelId</th>
        <th data-options="field:'number'">设备编号</th>
        <th data-options="field:'ip'">设备IP</th>
        <th data-options="field:'port'">设备端口</th>
        <th data-options="field:'connectTime'">连接时间</th>
    </tr>
    </thead>
</table>

<!-- 发送消息 -->
<div id="dg_send" class="easyui-dialog" style="width: 370px; height: 220px; padding: 10px 20px" closed="true"
     buttons="#dlg-send-buttons">
    <div class="ftitle">
        发送消息
    </div>
    <div class="fitem">
        <label style="text-align:right;width: 90px;">
            channelId：
        </label>
        <span id="p_channelId"></span>
    </div>
    <div class="fitem">
        <div style="width: 90px;float:left;text-align: left;">
            发送内容：
        </div>
        <input id="p_content" class="easyui-textbox"
               data-options="multiline:true,missingMessage:'该输入项为必输项'"
               style="width:300px;height:50px" required="true" max="20" value="测试信息，公众号：bugstack虫洞栈 | 欢迎关注！">
    </div>
</div>
<div id="dlg-send-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6"
       iconCls="icon-ok" onclick="util.doSendMsg();" style="width: 90px">发送</a>
    <a href="javascript:void(0)" class="easyui-linkbutton"
       iconCls="icon-cancel" onclick="javascript: $('#dg_send').dialog('close');" style="width: 90px">关闭</a>
</div>

<script type="text/javascript">
    var deviceToolbar = [{
        text: '发送消息',
        iconCls: 'icon-large-smartart',
        handler: function () {
            var row = $('#deviceDg').datagrid('getChecked');
            if (row.length != 1) {
                $.messager.alert('警告', '请选中一条数据进行操作！');
                return
            }
            $('#p_channelId').text(row[0].channelId);
            $('#dg_send').dialog('open').dialog('setTitle', '发送消息');
        }
    }];

    var util = {
        doSendMsg: function () {
            var row = $('#deviceDg').datagrid('getChecked');
            if (row.length != 1) {
                $.messager.alert('警告', '请选中一条数据进行操作！');
                return
            }
            var infoProtocolReq = {};
            infoProtocolReq.channelId = row[0].channelId;
            infoProtocolReq.msg = $('#p_content').textbox("getValue");

            $.post('/doSendMsg', {
                reqStr: JSON.stringify(infoProtocolReq)
            }, function (res) {
                if (res.success) {
                    $.messager.show({
                        title: '消息提示',
                        msg: '消息发送Success！'
                    });
                } else {
                    $.messager.show({
                        title: 'Error',
                        msg: res.msg
                    });
                }
            }, 'json');
        }
    };
</script>
</body>
</html>