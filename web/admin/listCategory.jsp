<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" import="java.util.*" %>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>

<script>
	$(function () {
		$("#addForm").submit(function () {
		    //如果是输入值为空，则按照header.jsp，跳出第二个参数值+不能为空的提示。
			if(!checkEmpty("name","分类名称"))
			    return false;
			if(!checkEmpty("categoryPic","分类图片"))
			    return false;
			return true;
        })
    })
</script>

<title>分类管理</title>
<div class="workingArea">
	<h1 class="label label-info">分类管理</h1>
	<br>
	<br>

	<div class="listDateTableDiv">
		<table class="table table-striped table-bordered  table-hover table-condensed">
			<thead>
				<tr class="success">
					<th>ID</th>
					<th>图片</th>
					<th>分类</th>
					<th>属性管理</th>
					<th>产品管理</th>
					<th>编辑</th>
					<th>删除</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${thecs}" var="c">
				<tr>
					<td>${c.id}</td>
					<td><img height="40px" src="img/category/${c.id}.jpg"></td>
					<td>${c.name}</td>
					<%--属性管理--%>
					<td><a href="admin_property_list?cid=${c.id}"><span class="glyphicon glyphicon-th-list"></span></a></td>
					<%--产品管理--%>
					<td><a href="admin_product_list?cid=${c.id}"><span class="glyphicon glyphicon-shopping-cart"></span></a></td>
					<%--编辑分类--%>
					<td><a href="admin_category_edit?id=${c.id}"><span class="glyphicon glyphicon-edit"></span></a></td>
					<%--删除分类--%>
					<td><a deleteLink="true" href="admin_category_delete?id=${c.id}"><span class="glyphicon glyphicon-trash"></span></a></td>

				</tr>
				</c:forEach>
			</tbody>

		</table>
	</div>

	<div class="pageDiv">
		<%--现在先不写，分页栏目标签--%>
		<%@include file="../include/admin/adminPage.jsp" %>
	</div>

	<%--新增分类的对话框，含有输入新增分类名和上传图片
	说明：	1.form的action="admin_category_add"，会导致访问CategoryServlet的add()方法
			2.method="post" 用于保证中文的正确提交
			3. 必须有enctype="multipart/form-data"，这样才能上传文件
			4. accept="image/*" 这样把上传的文件类型限制在了图片 --%>
	<div class="panel panel-warning addDiv">
		<div class="panel-heading">新增分类</div>
		<div class="panel-body">
			<form method="post" id="addForm" action="admin_category_add" enctype="multipart/form-data">
				<table class="addTable">
					<tr>
						<td>分类名称</td>
						<td><input type="text" id="name" name="name" class="form-control"></td>
					</tr>
					<tr>
						<td>分类图片</td>
						<td>
							<input id="categoryPic" accept="image/*" type="file" name="filepath" />
						</td>
					</tr>
					<tr class="submitTR">
						<td colspan="2" align="center">
							<button type="submit" class="btn btn-success">提交</button>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>


</div>
<%@include file="../include/admin/adminFooter.jsp"%>
