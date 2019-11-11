
package ui;

import model.*;
import java.util.*;
import static java.lang.System.out;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    private static Controller controller;

    public Main(){
        controller = new Controller();
    }

    public static void main(String[] args){
        Main program = new Main();
        double[] location = {1, 2};
        int[] num_chairs = {10,8,6,4,2};

        // Test of auditorium creation
        controller.registerAuditorium("Varela", location);
        
        // Test of creating the charis
        controller.getAuditoriums().get(0).createChairs(num_chairs);
    
        // Test of showing chairs for auditorium created 
        out.println(controller.getAuditoriums().get(0).showChairs());
        
        // Testing the reporting of deficient chairs
        controller.getAuditoriums().get(0).reportDeficientChair("A1");
        
        // Viewing the changes
        out.println(controller.getAuditoriums().get(0).showChairs());

        // SEGUIMIENTO
        out.println("TEST VALIDATION WITH INVALID FIELDS..");
        String is_schedule_available = controller.isScheduleAvailable("Varela", 2019, 11, 8, 21, 0, 2000); // This event won't be added, since the fields are invalid
        out.println(is_schedule_available);
        
        if(is_schedule_available.equals("Schedule is available")){
            out.println(controller.registerEvent("Evento1", 2019, 11, 30, 21, 0, 2000, "teacher_in_charge", "faculty_in_charge"));
            // This piece of code won't be executed, since the above condition doesn't apply
        }
        
        out.println("ADDING EVENTS (WITH VALID FIELDS) WITHIN THE NEXT 5 DAYS...");
        out.println(controller.registerEvent("Evento1", 2019, 11, 16, 7, 0, 2, "Liliana", "Ing. Sistemas"));
        out.println(controller.registerEvent("Evento2", 2019, 11, 17, 7, 0, 5, "Julian", "Ing. Sistemas"));
        out.println(controller.registerEvent("Evento3", 2019, 11, 18, 7, 0, 3, "Profe1", "Ing. Sistemas"));
        out.println(controller.registerEvent("Evento4", 2019, 11, 19, 7, 0, 6, "Profe2", "Ing. Sistemas"));
        out.println(controller.registerEvent("Evento5", 2019, 11, 20, 7, 0, 7, "Profe3", "Ing. Sistemas"));
        out.println(controller.registerEvent("Evento6", 2019, 11, 21, 7, 0, 7, "Profe3", "Ing. Sistemas"));
        out.println(controller.registerEvent("Evento7", 2019, 11, 22, 7, 0, 7, "Profe3", "Ing. Sistemas"));

        out.println("Testing the cancellation of event...");
        out.println(controller.cancelEvent("Evento5"));

        out.println("Testing the listing of the events in the next 5 days:");
        out.println(controller.getEventsInNext5Days());

    }
}