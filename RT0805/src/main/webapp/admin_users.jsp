<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Gestion des Utilisateurs" />
</jsp:include>

<c:if test="${user.type != '1'}">
    <c:redirect url="/index"/>
</c:if>

    <body>
        <div class="bloc-prim">
            <h2>Liste des utilisateurs</h2><br /> <br />
            <table class="table table-light">
                <thead class="dunoir">
                    <tr>
                        <td>Identifiant</td>
                        <td>Login</td>
                        <td>Type</td>
                        <td>Supprimer</td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="i" begin="0" end="${liste_users.length() -1}">
                        <tr>
                            <td id="identifiant">${liste_users.get(i).getString("id")}</td>
                            <td id="login">${liste_users.get(i).getString("login")}</td>
                            <td id="type">${liste_users.get(i).getString("type")}</td>
                            <td><button onclick="modifierInfos(this.parentNode.parentNode)" class="btn btn-danger">X</button></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div id="confirmation" class="alert" role="alert">
            </div>
        </div>
    </body>
<%@ include file="footer.jsp"%> 
