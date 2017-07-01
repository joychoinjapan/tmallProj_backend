<%--
  Created by IntelliJ IDEA.
  User: baobaochu
  Date: 2017/6/26
  Time: 06:06
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix='fmt' %>
<html>
<head>
    <script src="js/jquery-3.2.1.min.js"></script>
    <link href="bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <link href="css/back/style.css" rel="stylesheet">
    <script>
        //预先定义判断输入框的函数方便后面使用

        //验证是否为空
        function checkEmpty(id,name) {
            var value=$('#'+id).val();
            if(value.length==0){
                alert(name+"不能为空");
                $("#"+id)[0].focus();
                return false;
            }
            return true

        }

        //验证是否为数字
        function checkNumber(id,name) {
            var value=$('#'+id).val();
            if(value.length==0){
                alert(name+"不能为空");
                $("#"+id)[0].focus();
                return false;
            }
            if(isNaN(value)){
                alert(name+"必须是数字");
                $("#"+id)[0].focus();
                return false;
            }
            return true;

        }

        //验证是否为整数
        function checkInt(id,name) {
            var value=$("#"+id).val();
            if(value.length==0){
                alert(name+"不能为空");
                $("#"+id)[0].focus();
                return false;
            }
            if(paseInt(value)!=value){
                alert(name+"必须是整数");
                $("#"+id)[0].focus();
                return false;
            }
            return true;
        }

        //关于删除超链，都需要进行确认操作
        $(function () {
            $("a").click(function () {
                var deleteLink=$(this).attr("deleteLink");
                console.log(deleteLink);
                if("true"==deleteLink){
                    var confirmDelete=confirm("确认要删除");
                    if(confirmDelete)
                        return true;
                    return false;
                }
            })
        })
    </script>
</head>
<body>

