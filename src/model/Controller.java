package model;


import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * <b>Name: </b> Controller
 * <b>Description: </b> This class is responsible for the backend of the application 
 * responsible of provide solutions to the following functional requirements:
 * 
 * @author Sebastian Garcia Acosta. 
 */

public class Controller {
    private ArrayList<Event> events;
    private ArrayList<Auditorium> auditoriums;
    public static final String  SIA = "Schedule is available"; 

    public Controller(){
        this.events = new ArrayList<Event>();
        this.auditoriums = new ArrayList<Auditorium>();
        this.auditoriums.add(new Auditorium("VARELA", "no sé"));
        this.auditoriums.add(new Auditorium("MANUELITA", "no sé"));
        this.auditoriums.add(new Auditorium("ARGOS", "no sé"));
        this.auditoriums.add(new Auditorium("SIDOC", "no sé"));
        this.auditoriums.add(new Auditorium("BANCO DE OCCIDENTE", "no sé"));
        this.auditoriums.add(new Auditorium("ERNESTO DELIMA", "no sé"));
        this.auditoriums.add(new Auditorium("TEATRINO", "no sé"));
        this.auditoriums.add(new Auditorium("TARIMA BIENESTAR", "no sé"));
    }


    /**
     * Searches an auditorium by its mame.
     * @param name String, name of the auditorium.
     * @return index of auditorium searched if found, else return -1.
     */
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

    /**
     * Answers if an auditorium exists.
     * @param name String, the name of the auditorium.
     * @return true if found; otherwise, false.
     */
    public boolean auditoriumExists(String name){
        return (searchAuditoriumByName(name) != -1); 
    }

    /**
     * Searches an event by its mame.
     * @param name String, name of the event.
     * @return the index of the event if found, else return -1.
     */
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

    /**
     * <b>pre: </b> event specified by name is already registered
     * @param name, name of the event searched.
     * @return event found. 
    */
    public Event getEventByName(String name){
        return (events.get(searchEventByName(name)));
    }

    /**
     * <b>pre: </b> auditorium specified by name is already registered
     * @param name, name of the auditorium searched.
     * @return Auditorium found. 
     */
    public Auditorium getAuditoriumByName(String name){
        return auditoriums.get(searchAuditoriumByName(name));
    }

    public boolean eventExists(String name){
        return (searchEventByName(name) != -1); 
    }


    /**
     * Answers if the specified schedule is available
     * <b>pre: </b> auditorium specified by name is already registered
     * <b>pre: </b> event specified by name is NOT  already registered
     * @param audit_name String, the name of the auditorium to be consulted
     * @param starting_date LocalDateTime starting date of event to be potentially registered
     * @param duration_hours int, number of hours of duration of event to be potentially registered
     * @param num_assistants int, number of assistants of event to be potentially registered
     * @return "Schedule is available" if fields meet the conditions to be added; otherwise, returns a message explainig why fields are not valid.
     */
    public String isScheduleAvailable(String audit_name, LocalDateTime starting_date,int duration_hours, int num_assistants){
        String log = "";
        LocalDateTime end_date = starting_date.plusHours(duration_hours);
        int audi_idx = searchAuditoriumByName(audit_name);
        boolean event_coincide_with_another = false;
        boolean date_is_valid = true;
        boolean audit_has_enough_capacity = ( getAuditoriumByName(audit_name).getCapacity() >= num_assistants);
        log += (audit_has_enough_capacity) ? "" : audit_name + " has not enough capacity to host " + num_assistants + " people.";

        if(auditoriums.get(audi_idx).getEvents().size() > 0) {
            for(Event event : auditoriums.get(audi_idx).getEvents()){
                if(event.getStartDate().isEqual(starting_date) || (starting_date.isBefore(event.getEndDate()) && end_date.isAfter(event.getStartDate() ) ) ) {
                    log +=  audit_name + " is already in use at that time by " + event.getName() + ", which begins at " + event.getStartDate().toString() + " and ends at " + event.getEndDate().toString();
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
        if( starting_date.getHour() < 7 || end_date.getHour() > 20){
            log += "Events must begin after 6:59 and end before 20:00";
            date_is_valid = false;
        } 
        if(date_is_valid && !event_coincide_with_another && audit_has_enough_capacity)
            log = SIA;

        return log;
    }

    public String registerAuditorium(String name, String location){
        auditoriums.add(new Auditorium(name, location));
        return ("*" + name + " added successfully").toUpperCase();
    }

    /**
     * Creates an event and associate it with the specified auditorium
     * <b>pre: </b> auditorium specified by name is already registered
     * <b>pre: </b> all fields of event are valid according to the following criteria:
     * <ol>
     * <li>1.	The number of assistants has to be hosted perfectly by the auditorium selected.</li>
     * <li>2.	Event’s name cannot match with an existing one.</li>
     * <li>3.	Event’s date cannot coincide with another’s one.</li>
     * <li>4.	Event’s date cannot be before the current date.</li>
     * <li>5.	Event’s duration hours cannot exceed 12 and cannot be less than 2.</li>
     * <li>6.	An event cannot take place before 7:00 nor after 20:00 </li>
     * </ol>
     * @return String containing the message with information about the result of operation. </li>
     */
    public String registerEvent(String event_name, String audit_name, LocalDateTime starting_date, int duration_hours, String teacher_in_charge,
    String faculty_in_charge, int num_assistants){
        Event new_event = new Event(event_name, starting_date, duration_hours, teacher_in_charge, faculty_in_charge, num_assistants); 
        events.add(new_event);
        auditoriums.get(searchAuditoriumByName(audit_name)).assignEvent(new_event);
        return ("*" + event_name + " added succesfully *").toUpperCase();
    }

    /**
     * Cancels an event 
     * <b>pre: </b> event's name corresponds to an event already registrated. 
     * @param name String, name of the event to be canceled
     * @return String, the answer about what happened in the process.
     */
    public String cancelEvent(String name){
        if(eventExists(name)){
            events.remove(searchEventByName(name));
            return "Event cencelled";
        }
        else {
            return "Event doesn't been registered";
        }
    }

    /**
     * Generates report about the events registered that will occur in 5 days.
     * @return String, message containing the name and date of all the events within the next 5 days. 
     */
    public String getEventsInNext5Days(){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime in_5_days = now.plusDays(5);
        ArrayList<Event> next_events = new ArrayList<Event>();

        String list = "";
        for( Event e : events)
            if( (e.getStartDate().isAfter(now) || e.getStartDate().isEqual(now)) && e.getStartDate().isBefore(in_5_days))
                next_events.add(e);
        
        for(Event e : next_events)
            list += e.toString() + "\n";
        
        return list;
    }

    /**
     * Get events currently registered.
     * @return arraylist of events registered currently.
     */
    public ArrayList<Event> getEvents() {
        return events;
    }

    /**
     * <b>post: </b> updates ArrayList of events 
     * @param events ArrayList<Event> new events to be updated. 
     */
    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    /**
     * Get auditoriums currently registered.
     * @return arraylist of auditoriums registered currently.
     */
    public ArrayList<Auditorium> getAuditoriums() {
        return auditoriums;
    }

     /**
     * <b>post: </b> updates ArrayList of auditoriums 
     * @param events ArrayList<Auditorium> new auditoriums to be updated. 
     */
    public void setAuditoriums(ArrayList<Auditorium> auditoriums) {
        this.auditoriums = auditoriums;
    }
}