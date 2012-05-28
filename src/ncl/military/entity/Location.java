package ncl.military.entity;

/**
 * @author gural
 * @version 1.0
 *          Date: 13.04.12
 *          Time: 13:48
 */
public class Location {

    private String id;

    private String name;
    private String region;
    private String city;

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Location(String id, String name, String region, String city) {
        this.id = id;
        this.name = name;
        this.region = region;
        this.city = city;
    }
}
