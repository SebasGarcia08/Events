package model;

public class Chair {

    public static String D = "DEFICIENT";
    public static String O = "OPTIMUM";
    private String state;

    // Constructor
    public Chair(String state) {
        this.state = state;
    }

    /**
     * Get the state of this chair
     * @return String, state of this chair DEFICIENT or OPTIMUM
     */
    public String getState() {
        return state;
    }
    
    /**
     * Updates the state of this chair
     * @param String, new state of this chair DEFICIENT or OPTIMUM
     */
    public void setState(String state) {
        this.state = state;
    }
}