<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "https://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
        <title>Portail du tracking sportif</title>
    </head>
        <body>
            Merci de vous authentifier :
            <form name="f" method="GET" action="auth">
                <input type="text" name="login"/>
                <input type="text" name="mdp"/>
                <input type="submit"/>
            </form>
        </body>
</html>