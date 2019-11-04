
package ui;

import model.*;
import java.util.ArrayList;
import static java.lang.System.out;

public class Main {
    private static Controller controller;

    public Main(){
        controller = new Controller();
    }

    public static void main(String[] args){
        Main program = new Main();
        double[] chairs = {1, 2};
        int[] num_chairs = {10,8,6,4,2};
        controller.registerAuditorium("name", chairs);
        controller.getAuditoriums().get(0).createChairs(num_chairs);
        out.println(controller.getAuditoriums().get(0).showChairs());
        controller.getAuditoriums().get(0).reportDeficientChair("A1");
        out.println(controller.getAuditoriums().get(0).showChairs());
    }
}