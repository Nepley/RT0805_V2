package org.hdanyel.beans;

import org.json.JSONArray;

public class Activite 
{
    private String id_activite;
    private String id_u;
    private int id_s;
    private String date_debut;
    private String date_fin;
    JSONArray pts;

    public Activite()
    {
        this.id_u = "temp";
        this.id_s = 0;
        this.date_debut = "0:0";
        this.date_fin = "0:0";
    }

    public String getId_activite()
    {
        return this.id_activite;
    }

    public void setId_activite(String id)
    {
        this.id_activite = id;
    }

    public String getDate_debut() 
    {
        return this.date_debut;
    }

    public String getDate_fin() 
    {
        return this.date_fin;
    }

    public void setDate_debut(String date_debut) 
    {
        this.date_debut = date_debut;
    }

    public void setDate_fin(String date_fin) 
    {
        this.date_fin = date_fin;
    }

    public String getId_u() 
    {
        return this.id_u;
    }

    public void setId_u(String id) 
    {
        this.id_u = id;
    }

    public int getId_s() 
    {
        return this.id_s;
    }

    public void setId_s(int id) 
    {
        this.id_s = id;
    }

    public JSONArray getPts()
    {
        return this.pts;
    }

    public void AddPts(double x, double y, String date)
    {
        JSONArray new_pt = new JSONArray();
        new_pt.put(x);
        new_pt.put(y);
        new_pt.put(date);
        this.pts.put(new_pt);
    }

    public void SetPts(JSONArray pts)
    {
        this.pts = pts;
    }
}