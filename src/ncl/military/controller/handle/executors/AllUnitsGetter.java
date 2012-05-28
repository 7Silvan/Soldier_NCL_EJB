package ncl.military.controller.handle.executors;

import ncl.military.dao.DAO;
import ncl.military.entity.Unit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gural
 * @version 1.0
 *          Date: 24.04.12
 *          Time: 13:10
 */
public class AllUnitsGetter extends Executor {

    public AllUnitsGetter(DAO dao) {
        super(dao);
    }

    public Map<String, Object> execute(Map<String, Object> params) {
        Map<String, Object> result = new HashMap<String, Object>();

        List<Unit> unitList = getDao().getAllUnits();
        result.put("listOfUnits", unitList);

        return result;
    }
}
