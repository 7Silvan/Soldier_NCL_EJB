package ncl.military.controller.handle.executors;

import ncl.military.dao.DAO;
import ncl.military.entity.Soldier;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Silvan
 * Date: 06.05.12
 * Time: 17:39
 */
public class SoldierGetter extends Executor {

    public SoldierGetter(DAO dao) {
        super(dao);
    }

    public Map<String, Object> execute(Map<String, Object> params) {
        Map<String, Object> result = new HashMap<String, Object>();

        String soldierId = (String) params.get("queriedSoldierId");
        Soldier soldier = getDao().getSoldierById(soldierId);
        result.put("queriedSoldier", soldier);

        return result;
    }
}
