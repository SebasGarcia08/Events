package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

import java.time.temporal.ChronoUnit;

public class Auditorium {
    public static final String O = "OCCUPIED";
    public static final String A = "AVAILABLE";

    private String name;
    private String location;
    private Chair[][] chairs;
    private ArrayList<Event> events;
    private String state;

    // Constructor
    public Auditorium(String name, String location) {
        this.name = name;
        this.location = location;
        this.events = new ArrayList<Event>();
        createChairs(createRandomChairs());
        this.state = A;   
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
    private void createChairs(int[] chairs_per_row) {
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
        this.chairs = chairs;
    }

    /**
     * Creates a random array of numbers multiple of 4.
     * @return int[] array of integers with random length.
     */
    private int[] createRandomChairs(){
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
        getCurrentState();
        String image = "\n/*===================== " +name + " AUDITORIUM =====================*/\n".toUpperCase();
        image += "AUDITORIUM IS CURRENTLY [" + ((state.equals(A)) ? A : O) + "]\n";
        image += "Chair's state:\nO: Optimum\nA: Available\nC: Occupied\nX: Deficient\n";
        for (int r = 0; r < chairs.length; r++) {
            image += (char) (r + 65) + " |";
            for (int c = 0; c < chairs[r].length; c++) {
                if (chairs[r][c] == null)
                    image += "  ";
                else if (chairs[r][c].getState().equals(Chair.O))
                    image += "O ";
                else if (chairs[r][c].getState().equals(Chair.A))
                    image += "A ";
                else if (chairs[r][c].getState().equals(Chair.B))
                    image += "C ";
                else
                    image += "X ";
                if (c == (chairs[r].length - 1))
                    image += "|\n";
            }
        }
        image +=   "   " + " - - - - - - - - - - - \n" ;
        image +=   "   " + "|     S C R E E N     |\n" ;
        image +=   "   " + " - - - - - - - - - - -\n" ;

        image += "Number of chairs: " + getNumChairs() + "\nPercentage of deficient chairs: %"
                + calculatePercentageOfDeficient() + "\n" + getCurrentState()+"\n";
        return image;
    }

    /**
     * Evaluates if the auditoriums is currently available or occupied by an event. 
     * @return String, message informing if is available; otherwise answers in how many hours will be occupied.
     */
    public String getCurrentState(){
        if(events.size() > 0){
            LocalDateTime now = LocalDateTime.now();
            String res = "Auditorium is currently available, but will be occupied in " + now.until(getNextEvent().getStartDate(), ChronoUnit.HOURS) + " hours by " + getNextEvent().getName();
            for(Event e : events)   
                if(now.isBefore(e.getEndDate()) && now.isAfter(e.getStartDate())){
                    fillChairs(e);
                    this.state = O;
                    res = "Auditorium is currently occupied by event " + e.getName();
                }
            return res;
        } else {
            this.state = A;
            return "Auditorium has not events assigned yet.";
        }
    }

    /**
     * Updates the state of the chairs according to the number od assistants event specified.
     * <b>pre: </b> event0s assistants are equal or less than the capicity of this auditorium.
     * @param e event which assistants will occupy this auditorium's chairs.
     */
    private void fillChairs(Event e){
        int num_filled = 0;
        Chair[][] old_chairs = this.chairs;
        Chair[][] new_chairs = old_chairs;
        for(Chair[] chair_row : new_chairs){
            for(Chair chair : chair_row){
                if(num_filled == e.getNumAssistants()){
                    return;
                }
                else{
                    if(chair != null){
                        chair.setState(Chair.B);
                        ++num_filled;    
                    }
                }
            }
        }
        this.chairs = new_chairs;
    }

    /**
     * Gets the next event taking as reference the current date time.
     * @return the sooner event registered in this auditorium; if not registered, returns null.
     */
    public Event getNextEvent(){
        if(events.size() > 0){
            Event next = events.get(events.size()-1);
            LocalDateTime now = LocalDateTime.now(); 
            LocalDateTime max = LocalDateTime.MAX;
            for(Event e : events){
                if(now.isBefore(e.getStartDate()) && e.getStartDate().isBefore(max)){
                    max = e.getStartDate();
                    next = e;
                }
            }
            return next;
        } else {
            return null;
        }
    }

    /**
     * Calculates the percentage of deficient chairs. 
     * @return double, percentage of deficient chairs.
     */
    private double calculatePercentageOfDeficient() {
        int number_of_optimum_chairs = 0;
        for (Chair[] chair_row : chairs)
            for (Chair chair : chair_row)
                number_of_optimum_chairs += (chair != null && !chair.getState().equals(Chair.D)) ? 1 : 0;
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
        int num = 0;
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
    public void reportDeficientChair(String id, String desc) {
        int row = (int) (id.toUpperCase().charAt(0)) - 65;
        int column = Integer.valueOf(id.substring(1)) - 1;
        chairs[row][column].setState(Chair.D);
        chairs[row][column].setDefectiveDescription(desc);
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