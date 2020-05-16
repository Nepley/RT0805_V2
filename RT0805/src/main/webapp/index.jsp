<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Accueil" />
</jsp:include>

    <body>

        <div class="bloc-prim">
            
            <h1>Bienvenue sur le portail du tracking sportif</h1>
            <c:if test="${empty user.id}">
                Veuillez choisir une méthode pour vous authentifier :
            </c:if>
            <c:if test="${not empty user.id}">
                Bonjour ${user.login}
            </c:if>

            <div class="row">
                <div id="connexion" class="card form-group formulaires">
                    <h5 class="card-title">Vous avez déjà un compte ? Connectez vous</h5>
                    <form name="f" method="POST" action="visu/auth">
                        <label for="login">Votre login :</label>
                        <input type="hidden" name="purpose" value="login"/>
                        <input class="form-control" id="login" type="text" name="login"/>
                        <label for="mdp">Votre mot de passe :</label>
                        <input class="form-control" id="mdp" type="password" name="mdp"/>
                        <input type="submit"/>
                    </form>
                </div>
                <div id="inscription" class="card form-group formulaires">
                    <h5 class="card-title">Pas de compte ? Inscrivez vous :</h5>
                    <form name="f" method="POST" action="visu/auth">
                        <label for="login">Votre login :</label>
                        <input type="hidden" name="purpose" value="inscription"/>
                        <input class="form-control" id="login" type="text" name="login"/>
                        <label for="mdp">Votre mot de passe :</label>
                        <input class="form-control" id="mdp" type="password" name="mdp"/>
                        <label for="mdp2">Veuillez confirmer votre mot de passe :</label>
                        <input class="form-control" id="mdp2" type="password" name="mdp2"/>
                        <input type="submit"/>
                    </form>
                </div>
            </div>
        </div>
    </body>
<%@ include file="footer.jsp"%>