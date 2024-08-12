package com.example.securityhome;

public class Casas {

    private String nomCasa;
    private String Description;
    private int Id;
    private int rol_id;
    private int user_id;

    public int getRol_id() {
        return rol_id;
    }

    public void setRol_id(int rol_id) {
        this.rol_id = rol_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }


    public Casas(String nomCasa, String description, int id, int rolId, int userId){
        this.nomCasa=nomCasa;
        this.Description = description;
        this.Id = id;
        this.rol_id = rolId;
        this.user_id = userId;
    }

    public String getNomCasa() {
        return nomCasa;
    }

    public void setNomCasa(String nomCasa) {
        this.nomCasa = nomCasa;
    }
}
