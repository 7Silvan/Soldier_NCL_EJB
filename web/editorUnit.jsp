<%--
  User: Silvan
  Date: 11.05.12
  Time: 12:18
--%>
<c:if test="${fn:contains(requestScope.success, 'true')}">
    <c:url var="redirectUrl" value="/viewUnits"/>
    <script type="text/javascript">
        setTimeout('location.replace("${redirectUrl}")', 1000);
    </script>
    <h1>Saved!</h1>
</c:if>
<c:if test="${fn:contains(requestScope.userPath, '/viewUnits') and fn:contains(requestScope.action, initParam.action_EDIT_UNIT)}">
    <%--<jsp:useBean id="queriedUnit" scope="request" class="ncl.military.entity.Unit"/>--%>
    <c:url var="postUrl" value="/viewUnits"/>
    <form action="${postUrl}" method="post">

    <input name="unitIdMatch" value="${requestScope.unitIdMatch}" type="hidden"/>

    <input name="action" value="${initParam.action_EDIT_UNIT}" type="hidden"/>
    <table>
        <tr>
            <td><%--@declare id="queried_unit_name"--%><label for="queried_unit_name">Unit: </label></td>
            <td><label>
                <input name="queried_unit_name" type="text" value="${requestScope.queriedUnit.name}"/>
            </label></td>
        </tr>
        <tr>
            <td><label>Location:</label></td>
            <td><label>
                <select name="queried_location_name">
                    <c:forEach var="location" items="${requestScope.listOfLocations}">
                        <c:choose>
                            <c:when test="${requestScope.queriedUnit.location eq location.name}">
                                <option value="${location.id}" selected="">${location.name}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${location.id}">${location.name}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </label></td>
        </tr>


        <tr>
            <td><label for="queried_head_of_unit">Head name: </label></td>
            <td><label>
                <select id="queried_head_of_unit" name="queried_head_of_unit" class="required">
                    <c:forEach var="soldier" items="${requestScope.listOfSoldiers}">
                        <c:choose>
                            <c:when test="${requestScope.queriedUnit.headId eq soldier.id}">
                                <option value="${soldier.id}" selected="">${soldier.name}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${soldier.id}">${soldier.name}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
                <!--<input id="queried_commander_name" name="queried_commander_name" type="text"/>-->
            </label></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" class="btn primary pull-right" value="Edit"/>
            </td>
        </tr>
    </table>
</c:if>
</form>