package model;

import java.util.ArrayList;
import java.util.Date;


public class Event {
    private String name;
    private Date date;
    private int start_hour;
    private int end_hour;
    private String teacher_in_charge;
    private String faculty_in_charge;
    private ArrayList<Auditorium> auditoriums;

    public Event(String name, Date date, int start_hour, int end_hour, String teacher_in_charge,
            String faculty_in_charge) {
        this.name = name;
        this.date = date;
        this.start_hour = start_hour;
        this.end_hour = end_hour;
        this.teacher_in_charge = teacher_in_charge;
        this.faculty_in_charge = faculty_in_charge;
        this.auditoriums = new ArrayList<Auditorium>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getStartHour() {
        return start_hour;
    }

    public void setStartHour(int StartHour) {
        this.start_hour = StartHour;
    }

    public int getEndHour() {
        return end_hour;
    }

    public void setEndHour(int EndHour) {
        this.end_hour = EndHour;
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
}