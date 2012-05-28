<%--
  User: Silvan
  Date: 11.05.12
  Time: 5:41
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div style="margin-top:15px; ">
    <h3> View of Locations</h3>
    <table id="resultTable" class="condensed-table bordered-table zebra-striped">
        <thead>
        <tr>
            <th>NAME</th>
            <th>REGION</th>
            <th>CITY</th>
            <th>ACTION</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="location" items="${requestScope.listOfLocations}">
            <tr>
                <td>
                    <c:url var="url" value="/viewUnits">
                        <c:param name="action" value="getUnitsOfLocations"/>
                        <c:param name="locationIdMatch" value="${location.id}"/>
                    </c:url>
                    <a href="${url}">${location.name}</a>
                </td>
                <td>${location.region}</td>
                <td>${location.city}</td>
                <td>
                    <c:url var="editUrl" value="/viewLocations">
                        <c:param name="locationIdMatch" value="${location.id}"/>
                        <c:param name="action" value="${initParam.action_EDIT_LOCATION}"/>
                    </c:url>
                    <a class="btn primary" href="${editUrl}">Edit</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <small>* To sort values in the table press the headers.</small>
</div>