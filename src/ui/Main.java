
package ui;

import model.*;
import java.util.*;
import static java.lang.System.out;
import java.lang.System;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Main {
    private static Controller controller;
    private static Scanner num_sc;
    private static Scanner str_sc;
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); 

    public Main(){
        controller = new Controller();
        this.num_sc = new Scanner(System.in);
        this.str_sc = new Scanner(System.in);
    }

    /**
     * Main method.
     * @param args String array.
     */
    public static void main(String[] args){
       Main app = new Main();
        int election = 0;
        out.println("Welcome to this application for managing events in ICESI University. Choose:");
        // Principal menu
        while(election != 6){
            out.print("MENU    \n[1]. Register event "+
                              "\n[2]. Register new auditorium"+
                              "\n[3]. Report defective chair" +
                              "\n[4]. Cancell event"+ 
                              "\n[5]. Show events in the next 5 days"+
                              "\n[6]. Exit"+
                              "\nElection [1/2/3/4]: ");
            election = num_sc.nextInt();
            switch (election) {
                case 1:
                    app.registerEvent(); break;
                case 2:
                    app.registerAuditorium(); break;
                case 3:
                    app.reportDefectiveChair(); break;
                case 4:
                    app.cancelEvent(); break;
                case 5:
                    out.println(app.controller.getEventsInNext5Days()); break;
                case 6:
                    break;
                default:
                    out.println("Invalid choice"); break;
            }
        }

       
    }
     // VALIDTORS   

     /**
      * This method is enchraged of requesting a valid event name according to the situation. 
      * @param exclusive boolean, if true the event's name return will not exists; otherwise, event's name returned exists.  
      * @return a valid name typed by user for adding or consulting an event.
      */
     public String requestEventName(boolean exclusive){
        String event_name = "";
        boolean name_is_valid = false;
        while(!name_is_valid){
            out.print("Type the name of the event: ");
            event_name = str_sc.nextLine();
            boolean event_exists = controller.eventExists(event_name);
            if( (exclusive) ?  event_exists : !event_exists)
                    out.println("ERROR:" +  ((exclusive) ? "Event already registered. Try again.": "Event is not registered. Try again."));
            else
                    name_is_valid = true; 
        }
        return event_name;
    }

    /**
     * This method is enchraged of requesting a valid auditorium name according to the situation.
     * @param exclusive boolean, if true the auditorium's name return will not exists; otherwise, auditorium's name returned exists.
     * @return a valid name typed by user for adding or consulting an auditorium.
     */
    public String requestAuditName(boolean exclusive){
        String event_name = "";
        boolean name_is_valid = false;
        while(!name_is_valid){
            out.print("Type the name of the auditorium: ");
            event_name = str_sc.nextLine();
            boolean event_exists = controller.auditoriumExists(event_name);
            if( (exclusive) ?  event_exists : !event_exists)
                    out.println("ERROR:" +  ((exclusive) ? "Auditorium already registered. Try again.": "Auditorium is not registered. Try again."));
            else
                    name_is_valid = true; 
        }
        return event_name;
    }
    

    // Register event
    /**
     * Registers an event with the certainty of having valid fields
     * <b>pre: </b> Controller class must have  events and auditoriums arraylists already initialized.
     * <b>post: </b> All fields of new event will be valid.
     */
    public void registerEvent(){
        String event_name = requestEventName(true);
    //     String name, LocalDateTime start_date, int duration_hours, String teacher_in_charge,
    // String faculty_in_charge
        out.print("Type the starting date with the next format (yyyy:MM:dd HH:mm) Example: (2019-12-07 07:00): ");
        LocalDateTime starting_date = LocalDateTime.parse(str_sc.nextLine(), format);
        out.print("Type the number of hours of duration: ");
        int duration_hours = num_sc.nextInt();
        String audit_name = requestAuditName(false);
        out.print("Type the number of assistants: ");
        int num_assistants = num_sc.nextInt();
        String res = controller.isScheduleAvailable(audit_name, starting_date, duration_hours, num_assistants); 
        if( res.equals(controller.SIA)){
            out.print("Type the name of the teacher in charge: ");
             String teacher_in_charge = str_sc.nextLine();
            out.print("Type the name of the faculty in charge: ");
            String faculty_in_charge = str_sc.nextLine();
            out.println(controller.registerEvent(event_name, audit_name,starting_date, duration_hours, teacher_in_charge, faculty_in_charge));
        } else {
            out.println(res);
            return;
        }
    }

   /**
     * Registers an auditorium with the certainty of having valid fields
     * <b>pre: </b> Controller class must have events and auditoriums arraylists already initialized.
     * <b>post: </b> All fields of new auditorium will be valid.
     */
    public void registerAuditorium(){
        String audit_name = requestAuditName(true);
        out.print("Enter the location of the auditorium: ");
        String location = str_sc.nextLine();
        out.println(controller.registerAuditorium(audit_name, location));
    }

// Report chair
    /**
     * <b>pre: </b> user will type a String where the first character is a letter that is cotained in auditorium's row letters. 
     * And the rest of characters are integers greater than 1 and less than the number of columns of auditorium's chairs.
     * <b>post: </b> state of the chair indicated will be seted to deficient.
     */
    public void reportDefectiveChair(){
        String audit_name = requestAuditName(false), chair_loc;
        Auditorium audit = controller.getAuditoriumByName(audit_name); 
        out.println(audit.showChairs());
        while(true){
            out.print("Insert the defective chair location: ");
            chair_loc = str_sc.nextLine().toUpperCase();
            if(chair_loc.length() > 3)
                out.print("ERROR: type a valid location for chair");
            else if(!audit.chairExists(chair_loc)){
                out.println("ERROR: chair location not found.");
            } else{
                break;
            }
        }
        audit.reportDeficientChair(chair_loc);
    }

    /**
     * Canceles an event.
     * <b>post: </b> cancells event indicated with that name;
     */
    public void cancelEvent(){
        String event_name = requestEventName(false);
        out.println(controller.cancelEvent(event_name));
    }
}