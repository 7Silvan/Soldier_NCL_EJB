package ncl.military.controller.handle.executors;

import ncl.military.controller.handle.HandlerFactory;
import ncl.military.dao.DAO;
import ncl.military.entity.Soldier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gural
 * @version 1.0
 *          Date: 03.05.12
 *          Time: 14:42
 */
public class SoldierDeletter extends Executor {

    public SoldierDeletter(DAO dao) {
        super(dao);
    }

    public Map<String, Object> execute(Map<String, Object> params) {
        Map<String, Object> result = new HashMap<String, Object>();

        String soldierIdMatch = (String) params.get("soldierIdMatch");
        // first getting commander
        Soldier commander = getDao().getCommanderOfSoldierById(soldierIdMatch);
        // then deletting the target
        result.put("success", getDao().deleteSoldierById(soldierIdMatch));

        if (commander == null) {
            result.put("listOfSoldiers", getDao().getTopOfSoldiers());
            result.put("action", HandlerFactory.getContext().getInitParameter("action_GET_TOP"));
        } else {
            result.put("listOfSoldiers", getDao().getSubSoldiersOfByID(commander.getId()));
            result.put("action", HandlerFactory.getContext().getInitParameter("action_GET_SUBS_OF_SOLDIER"));

            List<Soldier> hierarchyList = getDao().getHierarchy(commander.getId());
            result.put("hierarchyList", hierarchyList);

            Soldier currentSoldier = getDao().getSoldierById(commander.getId());
            result.put("queriedSoldier", currentSoldier);
        }

        return result;
    }
}
