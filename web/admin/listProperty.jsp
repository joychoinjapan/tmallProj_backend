<%--
  Created by IntelliJ IDEA.
  User: baobaochu
  Date: 2017/6/29
  Time: 15:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="utf-8" import="java.util.*" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>
<script>
    $(function () {
        $("#addForm").submit(function () {
            if(checkEmpty("name","属性名称"))
                return true;
            return false;
        })
    })
</script>
<title>属性管理</title>
<div class="workingArea">
    <ol class="breadcrumb">
        <li><a href="admin_category_list">所有分类</a></li>
        <li><a href="admin_property_list?cid=${c.id}">${c.name}</a></li>
        <li class="active">属性管理</li>
    </ol>

    <div class="listDateTableDiv">
        <table class="table table-striped table-bordered  table-hover table-condensed">
            <thread>
                <tr class="success">
                    <th>ID</th>
                    <th>属性名称</th>
                    <th>编辑</th>
                    <th>删除</th>
                </tr>
            </thread>
            <tbody>
            <%--获取某一个分类下的属性集合--%>
            <c:forEach items="${ps}" var="p">
                <tr>
                    <%--给每一行设置属性id，属性名称，编辑和删除的链接--%>
                    <td>${p.id}</td>
                    <td>${p.name}</td>
                    <td><a href="admin_property_edit?id=${p.id}"><span class="glyphicon glyphicon-edit"></span></a></td>
                    <td><a deleteLink="true" href="admin_property_delete?id=${p.id}"><span class="glyphicon glyphicon-trash"></span></a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="pageDiv">
        <%@include file="../include/admin/adminPage.jsp"%>
    </div>
    <div class="panel panel-warning addDiv">
        <div class="panel-heading">新增属性</div>
        <div class="panel-body">
            <form action="admin_property_add" id="addForm" method="post">
                <table class="addTable">
                    <tr>
                        <td>属性名称</td>
                        <td><input type="text" id="name" name="name" class="form-control"></td>
                    </tr>
                    <tr class="submitTR">
                        <td colspan="2" align="center">
                            <input type="hidden" name="cid" value="${c.id}">
                            <button type="submit" class="btn btn-success">提交</button>

                        </td>
                    </tr>

                </table>

            </form>
        </div>

    </div>


</div>