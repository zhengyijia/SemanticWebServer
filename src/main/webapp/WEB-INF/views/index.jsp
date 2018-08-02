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
    <title>查询</title>
    <script src="${ctx}/static/js/jquery-3.3.1.js"></script>
    <script src="${ctx}/static/js/jquery.cookie.js"></script>
</head>
<style>
    #sparql_text {
        width: 100%;
        height: 200px;
        text-align: left;
        border: 1px solid #000;
    }
    #sparql_button {
        margin-top: 5px;
    }
    #result_box {
        width: 100%;
        border: 1px solid #000;
        padding: 5px;
        margin-top: 5px;
    }
    #query_result {
        margin-top: 5px;
        width: 100%;
    }
    th, td {
        border: 1px solid #000;
    }
</style>
<body>
<p>PREFIX rdf:&lt;http://www.w3.org/1999/02/22-rdf-syntax-ns#&gt;
    <br>PREFIX owl:&lt;http://www.w3.org/2002/07/owl#&gt;
    <br>PREFIX xml:&lt;http://www.w3.org/XML/1998/namespace&gt;
    <br>PREFIX xsd:&lt;http://www.w3.org/2001/XMLSchema#&gt;
    <br>PREFIX rdfs:&lt;http://www.w3.org/2000/01/rdf-schema#&gt;
    <br>PREFIX ONTO:&lt;http://10.2.1.169:9999/semanticweb/ontologies/&gt;</p>
<textarea id="sparql_text"></textarea>
<input type="button"  id="sparql_button"  value="查询">
<div id="result_box">
    <span style="display: block">SPARQL results:</span>
    <table id="query_result">

    </table>
</div>
</body>
<script>
    "use strict";

    $(function () {
        // 设置默认SPARQL
        $("#sparql_text").val(
            "select * \n" +
            "where { \n" +
            "    ?s rdf:type ONTO:Person. \n" +
            "}LIMIT 10"
        );

        // 从cookie获取数据
        var token = $.cookie('token');
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

            },
            error : function() {
                window.location.href="${ctx}/login";
            }
        })
    });

    $("#sparql_button").click(function () {
        var sparql = $("#sparql_text").val();
        $("#query_result").empty();
        var row = $("<tr/>");
        row.append($("<th style='color: #666; text-align: left'>[Executing querying...]</th>"));
        $("#query_result").append(row);

        $.ajax({
            url : "${ctx}/api/query/query_sparql",
            data : {
                "sparql" : sparql
            },
            dataType : "json",
            type : "GET",
            success : function(result) {
                $("#query_result").empty();

                if (result.results.bindings == false) {
                    var row = $("<tr/>");
                    row.append($("<th style='color: #000; text-align: left'>[no results]</th>"));
                    $("#query_result").append(row);
                    return;
                }

                var headers = result.head.vars;
                var row = $("<tr/>");
                for (var i in headers) {
                    row.append($("<th>" + headers[i] + "</th>"));
                }
                $("#query_result").append(row);

                var bindings = result.results.bindings;
                for (var i in bindings) {
                    var row = $("<tr/>");

                    for (var j in headers) {
                        var name = headers[j];
                        var node = bindings[i][name];
                        if (null == node) {
                            row.append($("<td/>"));
                        } else if (node.type == "blank") {
                            row.append($("<td>_:" + node.value + "</td>"));
                        } else if (node.type == "uri") {
                            row.append($("<td><a href='" + node.value + "'>" + node.value + "</a></td>"));
                        } else if (node.type == "literal") {
                            row.append($("<td>\"" + node.value + "\"@" + node.lang + "</td>"));
                        } else if (node.type == "typed-literal") {
                            row.append($("<td>\"" + node.value + "\"^^<<a href='" + node.datatype + "'>" + node.datatype + "</a>></td>"));
                        }
                    }

                    $("#query_result").append(row);
                }
            },
            error : function() {
                $("#query_result").empty();

                var row = $("<tr/>");
                row.append($("<th style='color: #ff0000; text-align: left'>[查询失败]</th>"));
                $("#query_result").append(row);
            }
        });
    })
</script>
</html>
