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
    private int num_assistants;

    // Constructor
    public Event(String name, LocalDateTime start_date, int duration_hours, String teacher_in_charge,
    String faculty_in_charge, int num_assistants) {
        this.name = name;
        this.start_date = start_date;
        this.duration_hours = duration_hours;
        this.teacher_in_charge = teacher_in_charge;
        this.faculty_in_charge = faculty_in_charge;
        this.num_assistants = num_assistants;
        this.end_date = start_date.plusHours(duration_hours);
    }

    /**
     * Get number of assistants of event.
     * @return number of assistants.
     */
    public int getNumAssistants(){
        return this.num_assistants;
    }

    /**
     * Get the ending date of event
     * @return LocalDateTime, the ending date of the event
     */
    public LocalDateTime getEndDate(){
        return this.end_date;
    }

    /**
     * Get the start date of event
     * @return LocalDateTime, the start date of the event
     */    
    public LocalDateTime getStartDate() {
        return start_date;
    }

    /**
     * GEt duration hours of the event
     * @return int, the number of hours of duration of the event
     */
    public int getDurationHours() {
        return duration_hours;
    }

    /**
     * Get the name of the event
     * @return String, the name of the event
     */
    public String getName() {
        return name;
    }

    /**
     * Get the auditoriums of the event
     * @return ArrayList<Auditorium>, the auditoriumsused by this event.
     */
    public ArrayList<Auditorium> getAuditoriums() {
        return auditoriums;
    }

    /**
     * Uodates the list of auditoriums used by this event
     * @param auditoriums ArrayList of new auditoriums, cannot be null.
     */
    public void setAuditoriums(ArrayList<Auditorium> auditoriums) {
        this.auditoriums = auditoriums;
    }

    /**
     * @return String summurizing the attributes of this event.
     */
    @Override
    public String toString() {
        return "Event [auditoriums=" + auditoriums + ", duration_hours=" + duration_hours + ", end_date=" + end_date
                + ", faculty_in_charge=" + faculty_in_charge + ", name=" + name + ", start_date=" + start_date
                + ", teacher_in_charge=" + teacher_in_charge + "]";
    }

    
}