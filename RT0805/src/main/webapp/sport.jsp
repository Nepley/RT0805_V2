<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Liste des sports" />
</jsp:include>

<c:if test="${empty auth}">
    <c:redirect url="/index"/>
</c:if>

Bonjour ${user.login}

<body>
    <div class="bloc-prim">
        <h1>Toutes vos activités</h1><br/><br/><hr/>
        <c:if test="${liste_activite.length() > 1}">
        <ul class="list-group">
            <li class="list-group-item active">Cliquez sur une activité</li>
            <c:forEach var="i" begin="0" end="${liste_activite.length() -1}">
                <li class="list-group-item"><a href="/visu/sports/${liste_activite.get(i).getInt('Activite')}"> ${liste_activite.get(i).getString("Sport")} - ${liste_activite.get(i).getString("Date")} - ${liste_activite.get(i).getString("Heure")}</a></li>
            </c:forEach>
        </ul>
        <div>
            Stats : <br>
            Nombre d'activité : ${stats.getInt("Nb_activite")}
            <br>
            Distance parcourue totale : ${stats.getDouble("Distance_totale")}
            <br>
            <a href="/visu/sports/${stats.getInt('Activite_plus_distance')}">Activité ayant le plus de distance</a>
        </div>

        </c:if>
        <c:if test="${liste_activite.length() == 0}">
                <h2>Vous n'avez pas encore d'activité, téléchargez l'application afin de devenir actif !</h2>
        </c:if>
    </div>
</body>
<%@ include file="footer.jsp"%> 