<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Liste des sports" />
</jsp:include>
<body>
    <div class="bloc-prim">
        <h1>Toutes vos activités</h1><br/><br/><hr/>
        <ul class="list-group">
            <li class="list-group-item active">Cliquez sur une activité</li>
            <c:forEach var="i" begin="0" end="${liste_activite.length() -1}">
                <li class="list-group-item"><a href="/visu/sports/${liste_activite.get(i).getInt('Activite')}"> ${liste_activite.get(i).getString("Sport")} - ${liste_activite.get(i).getString("Date")} - ${liste_activite.get(i).getString("Heure")}</a></li>
            </c:forEach>
        </ul>
    </div>
</body>
<%@ include file="footer.jsp"%> 