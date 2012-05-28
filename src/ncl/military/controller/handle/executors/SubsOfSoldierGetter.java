package ncl.military.controller.handle.executors;

import ncl.military.dao.DAO;
import ncl.military.entity.Soldier;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gural
 * @version 1.0
 *          Date: 19.04.12
 *          Time: 14:44
 */

public class SubsOfSoldierGetter extends Executor {

    public SubsOfSoldierGetter(DAO dao) {
        super(dao);
    }

    public Map<String, Object> execute(Map<String, Object> params) {
        Map<String, Object> result = new HashMap<String, Object>();

        if (params.get("subAction") != null) {
            result.put("subAction", params.get("subAction"));
            result.put("soldierIdMatch", params.get("soldierIdMatch"));
        }
        String idMatch = (String) params.get("queriedSoldierId");
        Logger.getLogger("controller").debug("queriedSoldierId for getting soldier got : " + idMatch);

        List<Soldier> soldierList = getDao().getSubSoldiersOfByID(idMatch);
        result.put("listOfSoldiers", soldierList);

        List<Soldier> hierarchyList = getDao().getHierarchy(idMatch);
        result.put("hierarchyList", hierarchyList);

        Soldier currentSoldier = getDao().getSoldierById(idMatch);
        result.put("queriedSoldier", currentSoldier);

        return result;
    }
}