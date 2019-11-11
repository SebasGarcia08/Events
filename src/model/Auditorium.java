package model;

import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.Collections;
import java.util.Arrays;

// - name: String
// - location: double[2]
// - chairs: Chair[][]
// - events: ArrayList<Event>

public class Auditorium {
    private String name;
    private double[] location;
    private Chair[][] chairs;
    private ArrayList<Event> events;

    public Auditorium(String name, double[] location) {
        this.name = name;
        this.location = location;
        this.events = new ArrayList<Event>();
    }

    /**
     * <b>Description:</b> Creates the Chairs objects that fills the Chair
     * matrix.<br>
     * <b>pre: </b> every position in array should correspond to a letter in
     * Alphabet.<br>
     * <b>post: </b> fills the chairs matrix corresponding to the numbers in every
     * position of the array passed as parameter. <br>
     * The larger number in array will be taken as the number of columns of the
     * matrix to be created.<br>
     * Likewise, the length of array will be the number of rows of the matrix to be
     * created.
     * 
     * @param chairs_per_row integer array, every position corresponds to a letter
     *                       in the alphabet (that character will be the id of every
     *                       row) and the number of that position corresponds to the
     *                       number of chairs in that row. E.g. if chairs_per_row[0]
     *                       = 10, then the row A will contain 10 rows.<br>
     */
    public void createChairs(int[] chairs_per_row) {
        int number_of_columns = chairs_per_row[0];
        int number_of_rows = chairs_per_row.length;

        // Finding the maximum value in chairs_per_row array.
        for (int number : chairs_per_row)
            if (number > number_of_columns)
                number_of_columns = number;

        // Initialising the chairs matrix.
        Chair[][] chairs = new Chair[number_of_rows][number_of_columns];

        // Fill chairs matrix in specified order and centered.
        for (int r = 0; r < number_of_rows; r++) {
            int edge_indentation = (number_of_columns == chairs_per_row[r]) ? 0 : number_of_columns / chairs_per_row[r];
            for (int c = edge_indentation; c < chairs_per_row[r] + edge_indentation; c++) {
                chairs[r][c] = new Chair(Chair.O);
            }
        }
        setChairs(chairs);
    }

    public String showChairs() {
        String image = "AUDITORY:\n";
        for (int r = 0; r < chairs.length; r++) {
            image += (char) (r + 65) + " |";
            for (int c = 0; c < chairs[r].length; c++) {
                if (chairs[r][c] == null)
                    image += " - ";
                else if (chairs[r][c].getState() == Chair.O)
                    image += " A ";
                else
                    image += " X ";
                if (c == (chairs[r].length - 1))
                    image += "|\n";
            }
        }
        image += "Number of chairs: " + Chair.getNumberOfObjs() + "\nPerfectage of deficient chairs: "
                + calculatePercentageOfDeficient();
        return image;
    }

    public double calculatePercentageOfDeficient() {
        int number_of_optimum_chairs = 0;
        for (Chair[] chair_row : chairs)
            for (Chair chair : chair_row)
                number_of_optimum_chairs += (chair != null && chair.getState() == Chair.O) ? 1 : 0;
        int number_of_deficient_chairs = Chair.getNumberOfObjs() - number_of_optimum_chairs;
        return (100 * number_of_deficient_chairs) / Chair.getNumberOfObjs();
    }

    public void reportDeficientChair(String id) {
        int row = (int) (id.charAt(0)) - 65;
        int column = Integer.valueOf(id.substring(1)) - 1;
        chairs[row][column].setState(Chair.D);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double[] getLocation() {
        return location;
    }

    public void setLocation(double[] location) {
        this.location = location;
    }

    public Chair[][] getChairs() {
        return chairs;
    }

    public void setChairs(Chair[][] chairs) {
        this.chairs = chairs;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return "Auditorium [events=" + events + ", location=" + Arrays.toString(location) + ", name=" + name + "]";
    }
    
}