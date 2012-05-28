package ncl.military.controller.handle.executors;

import ncl.military.controller.handle.HandlerFactory;
import ncl.military.dao.DAO;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Silvan
 * Date: 07.05.12
 * Time: 8:56
 */
public class SoldierMover extends Executor {
    public SoldierMover(DAO dao) {
        super(dao);
    }

    public Map<String, Object> execute(Map<String, Object> params) {
        Map<String, Object> result = new HashMap<String, Object>();

        result.put("subAction", HandlerFactory.getContext().getInitParameter("action_MOVE_SOLDIER"));

        String soldierIdMatch = (String) params.get("soldierIdMatch");

        result.put("soldierIdMatch", soldierIdMatch);
        result.put("listOfSoldiers", getDao().getNotSubsSoldiers(soldierIdMatch));

        return result;
    }
}
