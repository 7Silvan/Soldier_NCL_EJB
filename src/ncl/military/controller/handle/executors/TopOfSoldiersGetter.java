package ncl.military.controller.handle.executors;

import ncl.military.dao.DAO;
import ncl.military.entity.Soldier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gural
 * @version 1.0
 *          Date: 20.04.12
 *          Time: 11:33
 */
public class TopOfSoldiersGetter extends Executor {

    public TopOfSoldiersGetter(DAO dao) {
        super(dao);
    }

    public Map<String, Object> execute(Map<String, Object> params) {
        Map<String, Object> result = new HashMap<String, Object>();

        if (params.get("subAction") != null) {
            result.put("subAction", params.get("subAction"));
            result.put("soldierIdMatch", params.get("soldierIdMatch"));
        }

        List<Soldier> soldierList = getDao().getTopOfSoldiers();
        result.put("listOfSoldiers", soldierList);

        return result;
    }
}
