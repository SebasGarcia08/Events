package model;

public class Chair {

    public static final String D = "DEFICIENT";
    public static final String O = "OPTIMUM";
    public static final String A = "AVAILABLE";
    public static final String B = "OCCUPIED";
    private String state;
    private String defectiveDescription = null;

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

    /**
     * Updates the description of the defect of this chair if any.
     * @param defectiveDescription
     */
    public void setDefectiveDescription(String defectiveDescription) {
        this.defectiveDescription = defectiveDescription;
    }

}