package model;

import java.time.LocalDateTime;
import java.util.ArrayList;


public class Event {
    private String name;
    private LocalDateTime start_date;
    private int duration_hours;
    private String teacher_in_charge;
    private String faculty_in_charge;
    private ArrayList<Auditorium> auditoriums;
    private LocalDateTime end_date;

    public Event(String name, LocalDateTime start_date, int duration_hours, String teacher_in_charge,
    String faculty_in_charge) {
        this.name = name;
        this.start_date = start_date;
        this.duration_hours = duration_hours;
        this.teacher_in_charge = teacher_in_charge;
        this.faculty_in_charge = faculty_in_charge;
        this.end_date = start_date.plusHours(duration_hours);
    }

    public LocalDateTime getEndDate(){
        return this.end_date;
    }

    public LocalDateTime getStartDate() {
        return start_date;
    }

    public void setStartDate(LocalDateTime start_date) {
        this.start_date = start_date;
    }

    public int getDurationHours() {
        return duration_hours;
    }

    public void setDurationHours(int duration_hours) {
        this.duration_hours = duration_hours;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacherInCharge() {
        return teacher_in_charge;
    }

    public void setTeacherInCharge(String TeacherInCharge) {
        this.teacher_in_charge = TeacherInCharge;
    }

    public String getFacultyInCharge() {
        return faculty_in_charge;
    }

    public void setFacultyInCharge(String FacultyInCharge) {
        this.faculty_in_charge = FacultyInCharge;
    }

    public ArrayList<Auditorium> getAuditoriums() {
        return auditoriums;
    }

    public void setAuditoriums(ArrayList<Auditorium> auditoriums) {
        this.auditoriums = auditoriums;
    }

    @Override
    public String toString() {
        return "Event [auditoriums=" + auditoriums + ", duration_hours=" + duration_hours + ", end_date=" + end_date
                + ", faculty_in_charge=" + faculty_in_charge + ", name=" + name + ", start_date=" + start_date
                + ", teacher_in_charge=" + teacher_in_charge + "]";
    }

    
}