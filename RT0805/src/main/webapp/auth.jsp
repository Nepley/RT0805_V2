<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
        <title>Authentification</title>
    </head>
    <body>
        <jsp:useBean id="user" scope="page" class="org.hdanyel.Beans.Utilisateur">
            <jsp:setProperty name="user" property="*" />
        </jsp:useBean>
        
        <p>nom : <jsp:getProperty name="user" property="login"/></p>
        <p>prénom : <jsp:getProperty name="user" property="id"/></p>
    </body>
</html>