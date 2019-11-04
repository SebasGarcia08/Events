package model;

public class Chair {
//     + D : String = "DEFICIENT"
// + O: String = "OPTIMUM"
// - state: String

    public static String D = "DEFICIENT";
    public static String O = "OPTIMUM";
    private String state;
    public static int numberOfObjs = 0;

    public Chair(String state) {
        this.state = state;
        ++numberOfObjs;
    }

    public static int getNumberOfObjs() {
        return numberOfObjs;
    }

    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
}