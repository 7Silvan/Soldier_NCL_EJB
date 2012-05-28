package ncl.military.controller.handle.executors;

import ncl.military.controller.handle.HandlerFactory;
import ncl.military.dao.DAO;
import ncl.military.dao.tools.EntityValue;
import ncl.military.entity.Location;
import ncl.military.entity.Soldier;
import ncl.military.entity.Unit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gural
 * @version 1.0
 *          Date: 26.04.12
 *          Time: 16:40
 */
public class UnitChanger extends Executor {

    public UnitChanger(DAO dao) {
        super(dao);
    }

    public Map<String, Object> execute(Map<String, Object> params) {
        Map<String, Object> result = new HashMap<String, Object>();
        String action = (String) params.get("action");
        String unitIdMatch = (String) params.get("unitIdMatch");
        List<Location> locationList = getDao().getAllLocations();
        List<Soldier> soldierList = getDao().getSoldiersOfUnit(unitIdMatch);

        result.put("listOfSoldiers", soldierList);
        result.put("listOfLocations", locationList);
        result.put("unitIdMatch", unitIdMatch);

        if (HandlerFactory.getContext().getInitParameter("action_EDIT_UNIT").equals(action) && unitIdMatch != null) {
            List<EntityValue> values = getValuesOfUnit(params);

            if (values.size() != 0)
                result.put("success", getDao().setUnitAttributes(unitIdMatch, values));
        } else {

        }

        Unit unit = getDao().getUnitById(unitIdMatch);
        result.put("queriedUnit", unit);
        return result;
    }
}
