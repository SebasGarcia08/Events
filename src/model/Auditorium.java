package model;

import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.Collections;
import java.util.Arrays;
import java.util.Random;

// - name: String
// - location: double[2]
// - chairs: Chair[][]
// - events: ArrayList<Event>

public class Auditorium {
    private String name;
    private String location;
    private Chair[][] chairs;
    private ArrayList<Event> events;

    // Constructor
    public Auditorium(String name, String location) {
        this.name = name;
        this.location = location;
        this.events = new ArrayList<Event>();
        createChairs(createRandomChairs());   
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
        // Arrays.sort(chairs_per_row);
        int number_of_columns = chairs_per_row[0];
        int number_of_rows = chairs_per_row.length;

        // Finding the maximum value in chairs_per_row array.
        for (int number : chairs_per_row)
            if (number > number_of_columns)
                number_of_columns = number;

        // Initialising the chairs matrix.
        Chair[][] chairs = new Chair[number_of_rows][number_of_columns];

        // // Fill first row
        // for(int j = 0; j < number_of_columns; j++)
        //     chairs[0][j] = new Chair(Chair.O);

        // Fill chairs matrix in specified order and centered.
        for (int r = 0; r < number_of_rows; r++) {
            // int edge =  Math.abs(chairs_per_row[r-1] - chairs_per_row[r]);  

            // int edge_indentation = (number_of_columns == chairs_per_row[r]) ? 0 : edge/2;
            int edge_indentation = (number_of_columns == chairs_per_row[r]) ? 0 : number_of_columns / chairs_per_row[r];
            for (int c = edge_indentation; c < chairs_per_row[r] + edge_indentation; c++) {
                chairs[r][c] = new Chair(Chair.O);
            }
        }
        this.chairs = chairs;
    }

    /**
     * Creates a random array of numbers multiple of 4.
     * @return int[] array of integers with random length.
     */
    public int[] createRandomChairs(){
        Random rand = new Random();
        int num_cols = rand.nextInt((10 - 1) + 5) + 5;
        int[] res = new int[num_cols];

        for(int i = res.length-1; i >= 0; i--)
            res[i] = 4 * (i+1);

        return res;
    }

    /**
     * Sends visualization of the chairs of this auditorium.
     * @return String, image of the state of the chairs of this auditorium 
     */
    public String showChairs() {
        String image = "AUDITORY:\n";
        for (int r = 0; r < chairs.length; r++) {
            image += (char) (r + 65) + " |";
            for (int c = 0; c < chairs[r].length; c++) {
                if (chairs[r][c] == null)
                    image += "- ";
                else if (chairs[r][c].getState() == Chair.O)
                    image += "A ";
                else
                    image += "X ";
                if (c == (chairs[r].length - 1))
                    image += "|\n";
            }
        }
        image +=   "   " + " - - - - - - - - - \n" ;
        image +=   "   " + "| P A N T A L L A |\n" ;
        image +=   "   " + " - - - - - - - - -\n" ;

        image += "Number of chairs: " + getNumChairs() + "\nPercentage of deficient chairs: "
                + calculatePercentageOfDeficient();
        return image;
    }

    /**
     * Calculates the percentage of deficient chairs. 
     * @return double, percentage of deficient chairs.
     */
    public double calculatePercentageOfDeficient() {
        int number_of_optimum_chairs = 0;
        for (Chair[] chair_row : chairs)
            for (Chair chair : chair_row)
                number_of_optimum_chairs += (chair != null && chair.getState() == Chair.O) ? 1 : 0;
        int number_of_deficient_chairs = getNumChairs() - number_of_optimum_chairs;
        return (100 * number_of_deficient_chairs) / (double) getNumChairs();
    }

    /**
     * Calculates how many persons can be hosted in the chairs of this auditorium.
     * @return int, number of persons that can be hosted on the number of chairs available. 
     */
    public int getCapacity(){
        int num =0;
        for (Chair[] chair_row : chairs)
            for (Chair chair : chair_row)
                num += (chair == null || chair.getState().equals(Chair.D)) ? 0 : 1;
        return num;
    }

    /**
     * Calculates the number of chairs, regardless of being reported as deficient or not.
     * @return int, number of chairs.
     */
    public int getNumChairs(){
        int num =0;
        for (Chair[] chair_row : chairs)
            for (Chair chair : chair_row)
                num += (chair == null) ? 0 : 1;
        return num;
    }

    /**
     * Changes the state of chair specified
     * @param id String where the first character is a letter that is cotained in auditorium's row letters. 
     * And the rest of characters are integers greater than 1 and less than the number of columns of auditorium's chairs.   
     */
    public void reportDeficientChair(String id) {
        int row = (int) (id.toUpperCase().charAt(0)) - 65;
        int column = Integer.valueOf(id.substring(1)) - 1;
        chairs[row][column].setState(Chair.D);
    }

    /**
     * Anwers if chair specified exists
     * @param id String where the first character is a letter that is cotained in auditorium's row letters. 
     * And the rest of characters are integers greater than 1 and less than the number of columns of auditorium's chairs.
     * @return true if exists; othwerwise, returns false.
     */
    public boolean chairExists(String id){
        int row = (int) (id.charAt(0)) - 65;
        int column = Integer.valueOf(id.substring(1)) - 1;
        return (chairs[row][column] != null);
    }

    /**
     * Get name of auditorium
     * @return name of auditorium
     */
    public String getName() {
        return name;
    }

    /**
     * Get events of this auditoriums
     * @return events of this auditoriums.
     */
    public ArrayList<Event> getEvents() {
        return events;
    }

    /**
     * Updates the events of this auditorium
     * @param events Event, new events.
     */
    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    /**
     * Adds a new event to the events ArrayList of this auditorium
     * @param event Event, new event to be added.
     */
    public void assignEvent(Event event){
        this.events.add(event);
    }

    /**
     * @return String summurizing the attributes of this auditorium.
     */
    @Override
    public String toString() {
        return "Auditorium [events=" + events + ", location=" + location + ", name=" + name + "]";
    }
    
}