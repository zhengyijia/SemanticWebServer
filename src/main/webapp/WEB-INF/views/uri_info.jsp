<%--
  Created by IntelliJ IDEA.
  User: zheng
  Date: 2018/7/12
  Time: 19:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" %>
<%@ include file="./include/taglibs.jsp"%>
<html>
<head>
    <title></title>
    <script src="${ctx}/static/js/jquery-3.3.1.js"></script>
    <script src="${ctx}/static/js/jquery.cookie.js"></script>
</head>
<body>
<div id="page_title"></div>
<div id="line">======================================================================</div>
<div id="info"></div>
</body>
<script>
    "use strict";
    var uri = window.location.href;
    var token;

    $(function () {
        // 从cookie获取数据
        token = $.cookie('token');
        if (undefined == token || null == token) {
            window.location.href="${ctx}/login";
        }

        $.ajax({
            url : "${ctx}/api/auth/verify",
            headers: {
                "Authorization": token
            },
            type : "GET",
            success : function(result) {
                getDetailInfo();
            },
            error : function() {
                window.location.href="${ctx}/login";
            }
        })
    });

    function getDetailInfo() {
        $.ajax({
            url: "${ctx}/api/query/query_uri_label",
            headers: {
                "Authorization": token
            },
            data: {
                "uri" : uri
            },
            dataType: "json",
            type: "GET",
            success: function (result) {
                showLabel(result);
            },
            error: function () {
                alert("404");
            }
        });

        $.ajax({
            url : "${ctx}/api/query/query_uri",
            headers: {
                "Authorization": token
            },
            data : {
                "uri" : uri
            },
            dataType : "json",
            type : "GET",
            success : function(result) {
                showInfo(result);
            },
            error : function() {
                alert("404");
            }
        });
    }

    function showLabel(result) {
        if (result.results.bindings == false) {
            $(document).attr('title', uri);
            $('#page_title').text(uri);
        } else {
            $(document).attr('title', result.results.bindings[0].o.value);
            $('#page_title').text(result.results.bindings[0].o.value);
        }
    }

    function showInfo(result) {

        var info = $('#info');

        for(var i in result.results.bindings) {
            var binding = result.results.bindings[i];

            info.html(
                info.html()
                + binding.p_label.value
                + "："
                + genNodeHtml(binding.o, binding.o_label)
                + "<br/>"
            );
        }
    }

    function genNodeHtml(node, node_label) {
        if (node.type === "uri") {
            return '<a href="' + node.value + '">' + node_label.value + '</a>';
        } else {
            return node.value;
        }
    }
</script>
</html>
