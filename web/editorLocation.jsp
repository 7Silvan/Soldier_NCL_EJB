<%--
  User: Silvan
  Date: 11.05.12
  Time: 12:18
--%>
<c:if test="${fn:contains(requestScope.success, 'true')}">
    <c:url var="redirectUrl" value="/viewLocations"/>
    <script type="text/javascript">
        setTimeout('location.replace("${redirectUrl}")', 1000);
    </script>
    <h1>Saved!</h1>
</c:if>
<c:if test="${fn:contains(requestScope.userPath, '/viewLocations') and fn:contains(requestScope.action, initParam.action_EDIT_LOCATION)}">
    <%--<jsp:useBean id="queriedLocation" scope="request" class="ncl.military.entity.Location"/>--%>
    <c:url var="postUrl" value="/viewLocations"/>
    <form action="${postUrl}" method="post">

    <input name="locationIdMatch" value="${requestScope.locationIdMatch}" type="hidden"/>

    <input name="action" value="${initParam.action_EDIT_LOCATION}" type="hidden"/>
    <table>
        <tr>
            <td name="queried_location_name">Location</td>
            <td><label>
                <input name="queried_location_name" value="${requestScope.queriedLocation.name}" type="text"/>
            </label></td>
        </tr>
        <tr>
            <td name="queried_location_region">Region</td>
            <td><label>
                <input name="queried_location_region" value="${requestScope.queriedLocation.region}" type="text"/>
            </label></td>
        </tr>
        <tr>
            <td>City</td>
            <td><label>
                <input name="queried_location_city" value="${requestScope.queriedLocation.city}" type="text"/>
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