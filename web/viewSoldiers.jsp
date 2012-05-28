<%--
  User: Silvan
  Date: 11.05.12
  Time: 5:34
--%>
<%--this is soldier's infoblock with url to it's commander page, some other blocke relates on this--%>
<c:if test="${fn:contains(requestScope.action,'getSubsOfSoldier')}">
    <div class="span-one-third">
        <h3>Current Soldier</h3>
        <jsp:useBean id="queriedSoldier" scope="request" class="ncl.military.entity.Soldier"/>
        <table class="condensed-table borderless">
            <tr>
                <th>Name</th>
                <td>${queriedSoldier.name}</td>
            </tr>
            <tr>
                <th>Rank</th>
                <td>${queriedSoldier.rank}</td>
            </tr>
            <tr>
                <th>Commander</th>
                <c:choose>
                    <c:when test="${requestScope.hierarchyList ne null && fn:length(requestScope.hierarchyList) > 1}">
                        <c:set var="commanderId"
                               value="${requestScope.hierarchyList[fn:length(requestScope.hierarchyList)-1].id}"/>
                        <c:url var="commanderUrl" value="/viewSoldiers">
                            <c:param name="subAction" value="${requestScope.subAction}"/>
                            <c:param name="soldierIdMatch" value="${requestScope.soldierIdMatch}"/>
                            <c:param name="action" value="getSubsOfSoldier"/>
                            <c:param name="queriedSoldierId"
                                     value="${requestScope.hierarchyList[fn:length(requestScope.hierarchyList)-2].id}"/>
                        </c:url>
                        <td>
                            <a href="${commanderUrl}">${requestScope.hierarchyList[fn:length(requestScope.hierarchyList)-2].name}</a>
                        </td>
                    </c:when>
                    <c:otherwise>
                        <c:set var="commanderId"
                               value="${requestScope.hierarchyList[fn:length(requestScope.hierarchyList)-1].id}"/>
                        <c:url var="commanderUrl" value="/viewSoldiers">
                            <c:param name="subAction" value="${requestScope.subAction}"/>
                            <c:param name="soldierIdMatch" value="${requestScope.soldierIdMatch}"/>
                            <c:param name="action" value="getTop"/>
                        </c:url>
                        <td>${queriedSoldier.commander}</td>
                    </c:otherwise>
                </c:choose>
            </tr>
            <tr>
                <th>Unit</th>
                <td>${queriedSoldier.unit}</td>
            </tr>
            <tr>
                <th>Birthday</th>
                <td>${queriedSoldier.birthDate}</td>
            </tr>
        </table>
    </div>
</c:if>
<%--this is input form for search of soldiers on filters--%>
<c:if test="${fn:contains(requestScope.action,'getSearchResults')}">
    <h2>Search Tool</h2>

    <h6>* rule of serching "begins with"</h6>

    <div class="span-one-third">
        <c:url var="url" value="/viewSoldiers">
            <c:param name="action" value="getSearchResults"/>
        </c:url>
        <div class="span8">
            <form action="${url}" method="post">
                <table>
                    <tr>
                        <td><label for="queried_soldier_name">Soldier name: </label></td>
                        <td><label>
                            <input id="queried_soldier_name" name="queried_soldier_name" type="text"
                                   value="${requestScope.queried_soldier_name}"/>
                        </label></td>
                    </tr>
                    <tr>
                        <td><label for="queried_unit_name">Unit: </label></td>
                        <td><label>
                            <input id="queried_unit_name" name="queried_unit_name" type="text"
                                   value="${requestScope.queried_unit_name}"/>
                        </label></td>
                    </tr>
                    <tr>
                        <td><label for="queried_location_name">Location: </label></td>
                        <td><label>
                            <input id="queried_location_name" name="queried_location_name" type="text"
                                   value="${requestScope.queried_location_name}"/>
                        </label></td>
                    </tr>
                    <tr>
                        <td><label for="queried_commander_name">Commander name: </label></td>
                        <td><label>
                            <input id="queried_commander_name" name="queried_commander_name" type="text"
                                   value="${requestScope.queried_commander_name}"/>
                        </label></td>
                    </tr>
                    <tr>
                        <td><label for="queried_soldier_rank">Rank: </label></td>
                        <td><label>
                            <input id="queried_soldier_rank" name="queried_soldier_rank" type="text"
                                   value="${requestScope.queried_soldier_rank}"/>
                        </label></td>
                    </tr>
                    <tr>
                        <td colspan="2"><input type="submit" class="btn primary pull-right" value="Find"/></td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</c:if>
<%--this is for viewing items--%>
<div class="item_list">
    <c:if test="${not empty commanderUrl}">

        <div style="margin: 4px 0 8px;" class="span-one-third">
            <a class="btn primary" href="${commanderUrl}">Level up!</a>
            <c:url var="editUrl" value="/viewSoldiers">
                <c:param name="soldierIdMatch" value="${commanderId}"/>
                <c:param name="action" value="${initParam.action_EDIT_SOLDIER}"/>
            </c:url>
            <a class="btn primary" href="${editUrl}">Edit</a>
            <c:url var="addUrl" value="/viewSoldiers">
                <c:param name="queriedCommanderId" value="${commanderId}"/>
                <c:param name="action" value="${initParam.action_ADD_SOLDIER}"/>
            </c:url>
            <a class="btn primary" href="${addUrl}">Add New Soldier</a>
        </div>
    </c:if>
    <div style="margin-top:15px; ">
        <h3> View
            <c:if test="${fn:contains(requestScope.action, 'getTop')}"> Top </c:if>
            of Soldiers
            <c:if test="${fn:contains(requestScope.subAction, initParam.action_MOVE_SOLDIER)}"> (Select soldier to command the selected one) </c:if>
        </h3>
        <table id="resultTable" class="condensed-table bordered-table zebra-striped">
            <thead>
            <tr>
                <th>NAME</th>
                <th>RANK</th>
                <th>UNIT</th>
                <th>BIRTHDATE</th>
                <th>ACTIONS</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="soldier" items="${requestScope.listOfSoldiers}">
                <tr>
                    <td>
                        <c:choose>
                            <c:when test="${fn:contains(requestScope.subAction, 'moveSoldier') and requestScope.soldierIdMatch eq soldier.id}">
                                ${soldier.name}
                            </c:when>
                            <c:otherwise>
                                <c:url var="url" value="/viewSoldiers">
                                    <c:param name="action" value="getSubsOfSoldier"/>
                                    <c:param name="subAction" value="${requestScope.subAction}"/>
                                    <c:param name="soldierIdMatch" value="${requestScope.soldierIdMatch}"/>
                                    <c:param name="queriedSoldierId" value="${soldier.id}"/>
                                </c:url>
                                <a href="${url}">${soldier.name}</a>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:url var="url" value="/viewSoldiers">
                            <c:param name="action" value="getSearchResults"/>
                            <c:param name="queried_soldier_rank" value="${soldier.rank}"/>
                        </c:url>
                        <a href="${url}">${soldier.rank}</a>
                    </td>
                    <td>
                        <c:url var="url" value="/viewSoldiers">
                            <c:param name="action" value="getSearchResults"/>
                            <c:param name="queried_unit_name" value="${soldier.unit}"/>
                        </c:url>
                        <a href="${url}">${soldier.unit}</a>
                    </td>
                    <td>
                            ${soldier.birthDate}
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${fn:contains(requestScope.subAction, 'moveSoldier' )}">
                                <c:if test="${requestScope.soldierIdMatch ne soldier.id}">
                                    <c:url var="moveUnderThisUrl" value="/viewSoldiers">
                                        <c:param name="soldierIdMatch" value="${requestScope.soldierIdMatch}"/>
                                        <c:param name="queried_soldier_commander" value="${soldier.id}"/>
                                        <c:param name="action" value="${initParam.action_MOVE_UNDER_THIS_SOLDIER}"/>
                                    </c:url>
                                    <a class="btn primary" href="${moveUnderThisUrl}">Select</a>
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <c:url var="editUrl" value="/viewSoldiers">
                                    <c:param name="soldierIdMatch" value="${soldier.id}"/>
                                    <c:param name="action" value="${initParam.action_EDIT_SOLDIER}"/>
                                </c:url>
                                <a class="btn primary" href="${editUrl}">Edit</a>
                                <c:url var="deleteUrl" value="/viewSoldiers">
                                    <c:param name="soldierIdMatch" value="${soldier.id}"/>
                                    <c:param name="action" value="${initParam.action_DELETE_SOLDIER}"/>
                                </c:url>
                                <a class="btn primary" href="${deleteUrl}">Delete</a>
                                <c:url var="moveUrl" value="/viewSoldiers">
                                    <c:param name="soldierIdMatch" value="${soldier.id}"/>
                                    <c:param name="action" value="${initParam.action_GET_TOP}"/>
                                    <c:param name="subAction" value="${initParam.action_MOVE_SOLDIER}"/>
                                </c:url>
                                <a class="btn primary" href="${moveUrl}">Move</a>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <h6>Press rank or unit to search for soldier's with same attribute.</h6>
        <small>* To sort values in the table press the headers.</small>
    </div>
</div>