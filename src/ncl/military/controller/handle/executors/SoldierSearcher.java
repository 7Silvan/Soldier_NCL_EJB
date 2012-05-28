package ncl.military.controller.handle.executors;

import ncl.military.dao.DAO;
import ncl.military.dao.tools.Alias;
import ncl.military.dao.tools.Filter;
import ncl.military.entity.Soldier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gural
 * @version 1.0
 *          Date: 24.04.12
 *          Time: 13:02
 */
public class SoldierSearcher extends Executor {

    public SoldierSearcher(DAO dao) {
        super(dao);
    }

    public Map<String, Object> execute(Map<String, Object> params) {
        Map<String, Object> result = new HashMap<String, Object>();

        List<Soldier> soldierList = null;

        List<Alias> aliases = new ArrayList<Alias>() {{
            add(Alias.SOLDIER_NAME);
            add(Alias.UNIT_NAME);
            add(Alias.LOCATION_NAME);
            add(Alias.SOLDIER_COMMANDER_NAME);
            add(Alias.SOLDIER_RANK);
        }};

        // putting search-params into output map (result) to view it in search form
        for (Alias alias : aliases) {
            result.put(alias.getLabelAsQueried(), (String) params.get(alias.getLabelAsQueried()));
        }

        List<Filter> filters = getFiltersOfSearch(aliases, params);

        if (filters.size() != 0)
            soldierList = getDao().searchForSoldiers(filters);
        else
            soldierList = getDao().getAllSoldiers();

        result.put("listOfSoldiers", soldierList);

        return result;
    }
}
