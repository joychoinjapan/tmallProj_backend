<%--
  Created by IntelliJ IDEA.
  User: baobaochu
  Date: 2017/7/2
  Time: 11:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminPage.jsp"%>

<script>
    $(function(){
        $(".addFormSingle").submit(function(){
            if(checkEmpty("filepathSingle","图片文件")){
                //对于某些浏览器，比如火狐，提交过一次文件，再刷新之后，会记录上一次提交的文件路径。 这句话的用处是，刷新之后，保持当前文件选择input是空。
                $("#filepathSingle").value("");
                return true;
            }
        });
        $(".addFormDetail").submit(function () {
            if(checkEmpty("filepathDetail","图片文件"))
                return true;
            return false;
        })
    })
</script>

<title>产品图片管理</title>
<div class="workingArea">
    <ol class="breadcrumb">
        <li><a href="admin_category_list></a></li>
        <li><a href="admin_product_list?cid=${p.category.id}>${p.category.name}</a></li>
        <li class="active">${p.name}</li>
        <li class="active">产品图片管理</li>
    </ol>

    <table class="addPictureTable" align="center">
        <tr>
            <td class="addPictureTableTD">
                <div>
                    <div class="panel panel-warning addPictureDiv">
                        <div class="panel-heading">新增产品<b class="text-primary">单个</b>图片</div>
                        <div class="panel-body">
                            <form method="post" class="addFormSingle" action="admin_productImage_add" enctype="multipart/form-data">
                                <table class="addTable">
                                    <tr>
                                        <td>
                                            请选择本地图片 尺寸400x400为佳
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <input id="filepathSingle" type="file" name="filepath" />
                                        </td>
                                    </tr>
                                    <tr class="submitTR">
                                        <td align="center">
                                            <input type="hidden" name="type" value="type_detail"/>
                                            <input type="hidden" name="pid" value="${p.id}"/>
                                            <button type="submit" class="btn btn-success">提交</button>
                                        </td>
                                    </tr>
                                </table>
                            </form>
                        </div>
                    </div>
                </div>
                <table class="table table-striped table table-hover table-condensed">
                    <thread>
                        <tr class="success">
                            <th>ID</th>
                            <th>产品单个图片缩略图</th>
                            <th>删除</th>
                        </tr>
                    </thread>
                    <tbody>
                    <c:forEach items="${pisSingle}" var="pi">
                        <tr>
                            <td>${pi.id}</td>
                            <td><a title="点击查看原图" href="img/productSingle/${pi.id}.jpg"><img height="50px" src="img/productSingle/${pi.id}.jpg"></a></td>
                            <td><a deleteLink="true" href="admin_productImage_delete?id=${pi.id}"><span class="glyphicon glyphicon-trash"></span></a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

            </td>
            <td class="addPictureTableTD">
                <div>
                    <div class="panel panel-warning addPictureDiv">
                        <div class="panel-heading">新增产品<b class="text-primary">详情</b>图片</div>
                        <div class="panel-body">
                            <form method="post" class="addFormDetail" action="admin_productImage_add">
                                <table class="addTable">
                                    <tr>
                                        <td>请选本地图片宽度790为佳</td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <input id="filepathDetail" type="file" name="filepath"/>
                                        </td>
                                    </tr>
                                    <tr class="submitTR">
                                        <td align="center">
                                            <input type="hidden" name="type" value="type_detail" ／>
                                            <input type="hidden" name="pid" value="${p.id}">
                                            <button type="submit" class="btn btn-success">提交</button>
                                        </td>
                                    </tr>
                                </table>
                            </form>
                        </div>

                    </div>
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>产品详情图片缩略图</th>
                                <th>删除</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${pisDetail}" var="pi">
                             <tr>
                                 <td>${pi.id}</td>
                                 <td><a title="点击查看原图" href="img/productDetail/${pi.id}.jpg"><img height="50px" src="img/productDetail/${pi.id}.jpg"></a></td>
                                 <td><a deleteLink="true" href="admin_productImage_delete?id=${pi.id}"><span class="glyphicon glyphicon-trash"></span></a></td>
                             </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

            </td>
        </tr>
    </table>

</div>