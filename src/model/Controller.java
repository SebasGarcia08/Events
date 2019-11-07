package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Controller {
    private ArrayList<Event> events;
    private ArrayList<Auditorium> auditoriums;

    public Controller(){
        this.events = new ArrayList<Event>();
        this.auditoriums = new ArrayList<Auditorium>();
    }

    public String registerEvent(String name, LocalDate date, int start_hour, int end_hour, String teacher_in_charge,
    String faculty_in_charge){
        String res = "Added successfully";
            events.add(new Event(name, date, start_hour, end_hour, teacher_in_charge, faculty_in_charge));
        return res;
    }

    public String registerAuditorium(String name, double[] location){
        String res = "Added successfully";
            auditoriums.add(new Auditorium(name, location));
        return res;
    }

    public boolean isScheduleAvailable(int start_hour, int end_hour){
        boolean is_available = false;

        return is_available;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public ArrayList<Auditorium> getAuditoriums() {
        return auditoriums;
    }

    public void setAuditoriums(ArrayList<Auditorium> auditoriums) {
        this.auditoriums = auditoriums;
    }
}