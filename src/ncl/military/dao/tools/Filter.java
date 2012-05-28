package ncl.military.dao.tools;

/**
 * @author gural
 * @version 1.0
 *          Date: 13.04.12
 *          Time: 16:39
 */
public class Filter {
    private Alias attribute;
    private String valueToCompare;
    private FilterType typeOfComparison;

    public Filter(Alias attribute, FilterType type, String value) {
        this.attribute = attribute;
        this.valueToCompare = value;
        this.typeOfComparison = type;
    }

    public Alias getAttribute() {
        return attribute;
    }

    public String getValue() {
        return valueToCompare;
    }

    public FilterType getTypeOfComparison() {
        return typeOfComparison;
    }
}
