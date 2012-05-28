package ncl.military.controller.handle.executors;

import ncl.military.dao.DAO;
import ncl.military.entity.Soldier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gural
 * @version 1.0
 *          Date: 24.04.12
 *          Time: 13:13
 */
public class AllSoldiersOfUnitGetter extends Executor {

    public AllSoldiersOfUnitGetter(DAO dao) {
        super(dao);
    }

    public Map<String, Object> execute(Map<String, Object> params) {
        Map<String, Object> result = new HashMap<String, Object>();

        String unitIdMatch = (String) params.get("unitIdMatch");
        List<Soldier> soldierList = getDao().getSoldiersOfUnit(unitIdMatch);
        result.put("listOfSoldiers", soldierList);

        return result;
    }
}
