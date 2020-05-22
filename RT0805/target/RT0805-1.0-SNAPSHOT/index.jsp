<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<jsp:include page="WEB-INF/jsp/header.jsp">
    <jsp:param name="title" value="Accueil" />
</jsp:include>

<c:if test="${empty servlet}">
    <c:redirect url="/index"/>
</c:if>

    <body>
        <div class="bloc-prim">
            <h1>Bienvenue sur le portail du tracking sportif</h1>
            <c:if test="${empty user.id}">
                Veuillez choisir une méthode pour vous authentifier :
            </c:if>
            <c:if test="${not empty user.id}">
                Bonjour ${user.login} <br/>
                Choisissez ce qui vous intéresse :
            </c:if>

            <c:if test="${empty user.id}">
                <div class="row">
                    <div id="connexion" class="card form-group blocs">
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
                    <div id="inscription" class="card form-group blocs">
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
            </c:if>
            <c:if test="${not empty user.id}">
                <div class="row">
                    <div class="card blocs" style="width: 18rem;">
                        <img src="/res/img/activites.jpg" class="card-img-top">
                        <div class="card-body">
                          <h5 class="card-title">Voir mes Activités</h5>
                          <a href="visu/sports" class="btn btn-primary">➔</a>
                        </div>
                    </div>
                    <c:if test="${user.type == '1'}">
                        <div class="card blocs" style="width: 18rem;">
                            <img src="/res/img/profil.jpg" class="card-img-top">
                            <div class="card-body">
                            <h5 class="card-title">Gérer les utilisateurs</h5>
                            <a href="/visu/admin/users" class="btn btn-primary">➔</a>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${maxid != 0}">
                        <div class="card blocs" style="width: 18rem;">
                            <img src="/res/img/activites.jpg" class="card-img-top">
                            <div class="card-body">
                            <h5 class="card-title">Voir ma dernière course</h5>
                            <a href="/visu/sports/${maxid}" class="btn btn-primary">➔</a>
                            </div>
                        </div>
                    </c:if>
                </div>
            </c:if>

            <c:if test="${message.getString('mess').equals('error')}">
                <div id="confirmation" class="alert alert-danger" role="alert">
                    ${message.getString("text")}
                </div>
            </c:if>
                
            <c:if test="${message.getString('mess').equals('success')}">
                <div id="confirmation" class="alert alert-success" role="alert">
                    ${message.getString("text")}
                </div>
            </c:if>
        </div>
    </body>
<%@ include file="WEB-INF/jsp/footer.jsp"%>

