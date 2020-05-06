<jsp:include page="header.jsp">
    <jsp:param name="title" value="Accueil" />
</jsp:include>
    <body>
        <div class="bg-image"></div>
        <div class="bloc-prim">
            <div id="connexion" class="form-group formulaires">
                Merci de vous authentifier :
                <form name="f" method="GET" action="auth">
                    <input class="form-control" type="text" name="login"/>
                    <input class="form-control" type="password" name="mdp"/>
                    <input type="submit"/>
                </form>
            </div>
            <div id="inscription" class="form-group formulaires">
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