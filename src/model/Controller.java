package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class Controller {
    private ArrayList<Event> events;
    private ArrayList<Auditorium> auditoriums;

    public Controller(){
        this.events = new ArrayList<Event>();
        this.auditoriums = new ArrayList<Auditorium>();
    }

    public int searchAuditoriumByName(String name){
        int idx = -1;
        boolean found = false;
        for(int i = 0; i < auditoriums.size() && !found; i++){
            if(auditoriums.get(i).getName().equalsIgnoreCase(name)){
                idx = i; 
                found = true;
            }
        }
        return idx;
    }

    public boolean auditoriumExists(String name){
        return (searchAuditoriumByName(name) != -1); 
    }

    public int searchEventByName(String name){
        int idx = -1;
        boolean found = false;
        for(int i = 0; i < events.size() && !found; i++){
            if(events.get(i).getName().equalsIgnoreCase(name)){
                idx = i; 
                found = true;
            }
        }
        return idx;
    }

    public boolean eventExists(String name){
        return (searchEventByName(name) != -1); 
    }

    public String isScheduleAvailable(String name, int year, int month, int day, int hour, int minute, int duration_hours){
        String log = "";
        LocalDateTime starting_date = LocalDateTime.of(year, month, day, hour, minute);
        LocalDateTime end_date = starting_date.plusHours(duration_hours);
        int audi_idx = searchAuditoriumByName(name);
        boolean event_coincide_with_another = false;
        boolean date_is_valid = true;

        if(auditoriums.get(audi_idx).getEvents().size() > 0) {
            for(Event event : auditoriums.get(audi_idx).getEvents()){
                if(event.getStartDate().isEqual(starting_date) || (starting_date.isBefore(event.getEndDate()) && end_date.isAfter(event.getStartDate() ) ) ) {
                    log += "Event is overlapping with another event called " + event.getName() + ", which begins at " + event.getStartDate().toString() + " and ends at " + event.getEndDate().toString();
                    event_coincide_with_another = true;
                }
            }
        }

        if(starting_date.isBefore(LocalDateTime.now())){
            log += "Event's date cannot be before the current date.";
            date_is_valid = false;
        }

        if( duration_hours > 12 || duration_hours < 2){
            log += "Events must last at least 2 hours and not more than 12 hours.";
            date_is_valid = false;
        }
        if( starting_date.getHour() < 7 || starting_date.getHour() > 20){
            log += "Events must begin after 6:59 and before 20:00";
            date_is_valid = false;
        }

        if(date_is_valid && !event_coincide_with_another)
            log = "Schedule is available";

        return log;
    }

    public String registerAuditorium(String name, double[] location){
        String res = "Added successfully";
        if(auditoriums.size() > 8)
            res = "The university only have 8 auditoriums, you cannot register more than those.";
        else
            auditoriums.add(new Auditorium(name, location));
        return res;
    }

    public String registerEvent(String name, int year, int month, int day, int hour, int minute, int duration_hours, String teacher_in_charge,
    String faculty_in_charge ){
        events.add(new Event(name, LocalDateTime.of(year, month, day, hour, minute), duration_hours, teacher_in_charge, faculty_in_charge));
        return "Added succesfully";
    }

    public String cancelEvent(String name){
        if(eventExists(name)){
            events.remove(searchEventByName(name));
            return "Event cencelled";
        }
        else {
            return "Event doesn't been registered";
        }
    }

    public String getEventsInNext5Days(){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime in_5_days = now.plusDays(5);
        ArrayList<Event> next_events = new ArrayList<Event>();

        String list = "";
        for( Event e : events)
            if( (e.getStartDate().isAfter(now) || e.getStartDate().isEqual(now)) && e.getStartDate().isBefore(in_5_days))
                next_events.add(e);
        
        for(Event e : next_events)
            list += e.getName() + ", " + e.getStartDate() + "\n";
        
        return list;
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