package org.hdanyel.Beans;

public class Utilisateur 
{
    private String login;
    private String id;
    private String type;

    public Utilisateur()
    {
        this.login = "temp";
        this.id = "0";
        this.type = "0";
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
    
    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
}