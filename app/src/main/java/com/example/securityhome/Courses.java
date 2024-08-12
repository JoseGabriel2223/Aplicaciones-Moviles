package com.example.securityhome;

public class Courses {
    private String nombre;
    private String Id_course;
    private String progress;
    private String cours_name;
    private String teacherName;
    private String subject_id;

    private String id_group;

    public String getId_group() {
        return id_group;
    }

    public void setId_group(String id_group) {
        this.id_group = id_group;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    private String notes;

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getCours_name() {
        return cours_name;
    }

    public void setCours_name(String cours_name) {
        this.cours_name = cours_name;
    }

    public String getId_course() {
        return Id_course;
    }

    public void setId_course(String id_course) {
        Id_course = id_course;
    }

    public Courses(String nombre, String idcourse, String coursename, String progress, String teacher_name, String subjectid, String notes){
        this.nombre= nombre;
        this.Id_course = idcourse;
        this.cours_name = coursename;
        this.progress = progress;
        this.teacherName = teacher_name;
        this.id_group = subjectid;
        this.notes = notes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
