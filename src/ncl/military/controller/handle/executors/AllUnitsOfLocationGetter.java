package ncl.military.controller.handle.executors;

import ncl.military.controller.handle.HandlerFactory;
import ncl.military.dao.DAO;
import ncl.military.entity.Unit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Silvan
 * Date: 25.04.12
 * Time: 5:39
 */
public class AllUnitsOfLocationGetter extends Executor {

    public AllUnitsOfLocationGetter(DAO dao) {
        super(dao);
    }

    public Map<String, Object> execute(Map<String, Object> params) {
        Map<String, Object> result = new HashMap<String, Object>();

        String locationIdMatch = (String) params.get("locationIdMatch");
        List<Unit> unitList = getDao().getUnitsOfLocation(locationIdMatch);
        result.put("listOfUnits", unitList);

        result.put("action", HandlerFactory.getContext().getInitParameter("action_GET_ALL"));

        return result;
    }
}
