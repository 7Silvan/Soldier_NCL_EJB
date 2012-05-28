<%--
  User: Silvan
  Date: 11.05.12
  Time: 5:41
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div style="margin-top:15px; ">
    <h3> View of Units</h3>
    <table id="resultTable" class="condensed-table bordered-table zebra-striped">
        <thead>
        <tr>
            <th>NAME</th>
            <th>HEAD</th>
            <th>LOCATION</th>
            <th>ACTION</th>
        </tr>
        </thead>
        <tbody>

        <c:forEach var="unit" items="${requestScope.listOfUnits}">
            <tr>
                <td>
                    <c:url var="url" value="/viewSoldiers">
                        <c:param name="action" value="getSoldiersOfUnit"/>
                        <c:param name="unitIdMatch" value="${unit.id}"/>
                    </c:url>
                    <a href="${url}">${unit.name}</a>
                </td>

                <td>
                    <c:url var="url" value="/viewSoldiers">
                        <c:param name="action" value="getSubsOfSoldier"/>
                        <c:param name="queriedSoldierId" value="${unit.headId}"/>
                    </c:url>
                    <a href="${url}">${unit.head}</a>
                </td>
                <td>
                    <c:url var="url" value="/viewSoldiers">
                        <c:param name="action" value="getSearchResults"/>
                        <c:param name="queried_location_name" value="${unit.location}"/>
                    </c:url>
                    <a href="${url}">${unit.location}</a>
                </td>
                <td>
                    <c:url var="editUrl" value="/viewUnits">
                        <c:param name="unitIdMatch" value="${unit.id}"/>
                        <c:param name="action" value="${initParam.action_EDIT_UNIT}"/>
                    </c:url>
                    <a class="btn primary" href="${editUrl}">Edit</a>
                </td>
            </tr>
        </c:forEach>

        </tbody>
    </table>
    <small>* To sort values in the table press the headers.</small>
</div>