<jsp:include page="header.jsp">
    <jsp:param name="title" value="Accueil" />
</jsp:include>
    <body>
        <div class="bloc-prim row">
            <h1>Bienvenue sur le portail du tracking sportif</h1>
            Veuillez choisir une méthode pour vous authentifier :
            <div id="connexion" class="card form-group formulaires">
                <h5 class="card-title">Vous avez déjà un compte ? Connectez vous</h5>
                <form name="f" method="GET" action="auth">
                    <input class="form-control" type="text" name="login"/>
                    <input class="form-control" type="password" name="mdp"/>
                    <input type="submit"/>
                </form>
            </div>
            <div id="inscription" class="card form-group formulaires">
                Pas de compte ? Inscrivez vous :
                <form name="f" method="GET" action="auth">
                    <input class="form-control" type="text" name="login"/>
                    <input class="form-control" type="password" name="mdp"/>
                    <input class="form-control" type="password" name="mdp2"/>
                    <input type="submit"/>
                </form>
            </div>
        </div>
    </body>
<%@ include file="footer.jsp"%>