package com.example.securityhome;

public class Partial_Model {
    private int id;
    private String name;
    private String Notes;

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public Partial_Model(int id, String name, String notes) {
        this.id = id;
        this.name = name;
        this.Notes = notes;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

