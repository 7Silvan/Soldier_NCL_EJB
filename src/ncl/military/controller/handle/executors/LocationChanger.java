package ncl.military.controller.handle.executors;

import ncl.military.controller.handle.HandlerFactory;
import ncl.military.dao.DAO;
import ncl.military.dao.tools.EntityValue;
import ncl.military.entity.Location;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gural
 * @version 1.0
 *          Date: 26.04.12
 *          Time: 16:39
 */
public class LocationChanger extends Executor {

    public static final Logger log = Logger.getLogger("controller");

    public LocationChanger(DAO dao) {
        super(dao);
    }

    public Map<String, Object> execute(Map<String, Object> params) {
        Map<String, Object> result = new HashMap<String, Object>();
        String action = (String) params.get("action");
        String locationIdMatch = (String) params.get("locationIdMatch");
        List<Location> locationList = getDao().getAllLocations();
        result.put("listOfLocations", locationList);
        result.put("locationIdMatch", locationIdMatch);

        if (HandlerFactory.getContext().getInitParameter("action_EDIT_LOCATION").equals(action) && locationIdMatch != null) {
            List<EntityValue> values = getValuesOfLocation(params);

            if (values.size() != 0) {
                result.put("success", getDao().setLocationAttributes(locationIdMatch, values));
            }
        }
        Location location = getDao().getLocationById(locationIdMatch);
        result.put("queriedLocation", location);
        return result;
    }
}
