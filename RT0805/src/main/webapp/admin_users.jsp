<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Bienvenue sur la map" />
</jsp:include>


    <body>
        <div class="bloc-prim">
            <ul class="list-group">
                <li class="list-group-item active">Liste des utilisateurs</li>
                <c:forEach var="i" begin="0" end="${liste_users.length() -1}">
                    <li class="list-group-item"> ${liste_activite.get(i).getString("login")}</li>
                </c:forEach>
            </ul>
        </div>
    </body>
<%@ include file="footer.jsp"%> 
