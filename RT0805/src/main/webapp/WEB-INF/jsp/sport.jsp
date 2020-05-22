<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Liste des activites" />
</jsp:include>

<c:if test="${empty auth}">
    <c:redirect url="/index"/>
</c:if>
<body>
    <div class="bloc-prim">
        <h1>Toutes vos activités</h1>
        <c:if test="${liste_activite.length() >= 1}">
            <ul class="list-group">
                <li class="list-group-item active">Cliquez sur une activité</li>
                <c:forEach var="i" begin="0" end="${liste_activite.length() -1}">
                    <li class="list-group-item"><a href="/visu/sports/${liste_activite.get(i).getInt('Activite')}"> ${liste_activite.get(i).getString("Sport")} - ${liste_activite.get(i).getString("Date")} - ${liste_activite.get(i).getString("Heure")}</a></li>
                </c:forEach>
            </ul>
            <hr />
            <div class="accordion">
                <div class="card">
                    <div class="card-header" id="headingOne">
                      <h2 class="mb-0">
                        <button class="btn btn-link" type="button" data-toggle="collapse" data-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                          Statitstiques
                        </button>
                      </h2>
                    </div>

                    <div id="collapseOne" class="collapse show" aria-labelledby="headingOne" data-parent="#accordionExample">
                        <div class="card-body">
                            Nombre d'activité : ${stats.getInt("Nb_activite")} <br>
                            Distance parcourue totale : ${stats.get("Distance_totale")} km<br>
                            <a href="/visu/sports/${stats.getInt('Activite_plus_distance')}">Activité ayant le plus de distance</a>                        </div>
                      </div>
                    </div>
            </div>
        </c:if>
        <c:if test="${liste_activite.length() == 0}">
                <h2>Vous n'avez pas encore d'activité, téléchargez l'application afin de devenir actif !</h2>
        </c:if>
    </div>
</body>
<%@ include file="footer.jsp"%> 