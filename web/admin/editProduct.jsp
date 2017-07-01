<%--
  Created by IntelliJ IDEA.
  User: baobaochu
  Date: 2017/7/1
  Time: 16:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>

<title>编辑产品</title>
<script>
    $(function () {
        $("editForm").submit(function () {
            if(!checkEmpty("name","产品名称"))
                return false;
            if(!checkEmpty("name","产品小标题"))
                return false;
            if(!checkNumber("name","原价格"))
                return false;
            if(!checkNumber("name","优惠价格"))
                return false;
            if(!checkNumber("name","库存"))
                return false;
            return true;

        })
    })
</script>
<div class="workingArea">
    <ol class="breadcrumb">
        <li><a href="admin_category_list">所有分类</a></li>
        <li><a href="admin_product_list?cid=${p.category.id}">${p.category.name}</a></li>
        <li class="active">${p.name}</li>
        <li class="active">编辑产品</li>
    </ol>
    <div class="panel panel-warning editDiv">
        <div class="panel-heading">编辑产品</div>
        <div class="panel-body">
            <form method="post" action="admin_product_update" id="editForm">
                <table class="editTable">
                    <tr>
                        <td>产品名称</td>
                        <td><input id="name" name="name" value="${p.name}" type="text" class="form-controlm"></td>
                    </tr>
                    <tr>
                        <td>产品小标题</td>
                        <td><input id="subTitle" name="subTitle" value="${p.subTitle}" type="text" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>原价格</td>
                        <td><input id="originalPrice" value="99.98" value="${p.originalPrice}" name="originalPrice" type="text" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>优惠价格</td>
                        <td><input id="promotePrice" value="19.98" value="${promotePrice}" name="originalPrice" type="text" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>库存</td>
                        <td><input id="stock" value="99" name="stock" value="${p.stock}" type="text" class="form-control"></td>
                    </tr>
                    <tr class="submitTR">
                        <td colspan="2" align="center">
                            <input type="hidden" name="id" value="${p.id}">
                            <input type="hidden" name="cid" value="${p.category.id}">
                            <button type="submit" class="btn btn-success">提交</button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>

    </div>

</div>
