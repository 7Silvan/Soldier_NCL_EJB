package ncl.military.dao.tools;

/**
 * @author gural
 * @version 1.0
 *          Date: 26.04.12
 *          Time: 16:28
 */
public class EntityValue {
    private Alias key;
    private String value;

    public EntityValue(Alias key, String value) {
        this.key = key;
        this.value = value;
    }

    public Alias getKey() {
        return key;
    }

    public void setKey(Alias key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
