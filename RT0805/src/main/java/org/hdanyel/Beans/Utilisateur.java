package org.hdanyel.Beans;

public class Utilisateur 
{
    private String login;
    private String id;

    public Utilisateur()
    {
        this.login = "temp";
        this.id = "0";
    }

    public String getLogin() 
    {
        return login;
    }

    public void setLogin(String login) 
    {
        this.login = login;
    }

    public String getId() 
    {
        return id;
    }

    public void setId(String id) 
    {
        this.id = id;
    }
}