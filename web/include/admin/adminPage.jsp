<%--
  Created by IntelliJ IDEA.
  User: baobaochu
  Date: 2017/6/26
  Time: 06:06
  To change this template use File | Settings | File Templates.

--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
pageEncoding="UTF-8" isELIgnored="false" %>
<script>
    $(function(){
        $("ul.pagination li.disabled a").click(function () {
            return false;
        })
    })
</script>

<nav>
    <ul class="pagination">

        <%--首页超链--%>
        <li <c:if test="${!page.hasPrevious}">class="disabled"</c:if>>
            <a href="?page.start=0${page.param}" aria-label="Previous">
                <span aria-hidden="true">«</span>
            </a>
        </li>

        <%--上一页超链--%>
        <li <c:if test="${!page.hasPrevious}">class="disabled"</c:if>>
            <a href="?page.start=${page.start-page.count}" aria-label="Previous">
                <span aria-hidden="true">‹</span>
            </a>
        </li>
        <%--中间页超链--%>
        <c:forEach begin="0" end="${page.totalPage-1}" varStatus="status">

            <c:if test="${status.count*page.count-page.start<=20&&status.count-page.start>=-10}">
                <li <c:if test="${status.index*page.count==page.start}">class="disabled"</c:if>>
                <a href="?page.start=${status.index*page.count}${page.param}"
                   <c:if test="${status.index*page.count==page.start}">class="current"</c:if>>
                   ${status.count}
                </a>
                </li>
            </c:if>
        </c:forEach>
        <%--下一页超链--%>
            <li <c:if test="${!page.hasNext}">class="disabled" </c:if>>
            <a href="?page.start=${page.start+page.count}${page.param}" aria-label="Next">
            <span aria-hidden="true">›</span>
            </a>
        </li>

        <%--最后一页--%>
            <li <c:if test="${!page.hasNext}">class="disabled" </c:if>
            <a href="?page.start=${page.last}${page.param}" aria-label="Next">
                <span aria-hidden="true">»</span>
            </a>
        </li>
    </ul>
</nav>