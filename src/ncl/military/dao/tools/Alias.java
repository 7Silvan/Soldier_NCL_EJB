package ncl.military.dao.tools;

/**
 * @author gural
 * @version 1.0
 *          Date: 10.05.12
 *          Time: 16:27
 */
public enum Alias {
    LOCATION_ID("location_id"),
    LOCATION_NAME("location_name"),
    LOCATION_REGION("location_region"),
    LOCATION_CITY("location_city"),
    UNIT_ID("unit_id"),
    UNIT_NAME("unit_name"),
    UNIT_HEAD_NAME("head_of_unit_name"),
    UNIT_HEAD_ID("head_of_unit"),
    UNIT_LOCATION("location_name"),
    SOLDIER_ID("soldier_id"),
    SOLDIER_NAME("soldier_name"),
    SOLDIER_RANK("soldier_rank"),
    SOLDIER_COMMANDER("soldier_commander"),
    SOLDIER_UNIT("unit_name"),
    SOLDIER_BIRTHDATE("soldier_birthdate"),
    SOLDIER_COMMANDER_NAME("commander_name");

    private String label;

    Alias(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public String getLabelAsQueried() {
        return "queried_" + this.label;
    }

    public static Alias getAlias(String label) {
        if (label != null) {
            for (Alias a : Alias.values()) {
                if (label.equals(a.label))
                    return a;
            }
            throw new IllegalArgumentException("Given label of " + label + " was not found.");
        } else
            throw new IllegalArgumentException("Given label must not be null.");
    }
}
