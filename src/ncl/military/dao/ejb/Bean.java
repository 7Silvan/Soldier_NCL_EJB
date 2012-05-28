package ncl.military.dao.ejb;

import ncl.military.dao.exceptions.DAOInitException;
import ncl.military.dao.exceptions.DataAccessException;
import ncl.military.dao.tools.Alias;
import ncl.military.entity.*;
import org.apache.log4j.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bean {

    protected static final Logger log = Logger.getLogger("model");

    /**
     * Accessorie for performing query with parsing on callback
     */
    protected interface SetParser {
        Object parse(ResultSet raw, PreparedStatement prst, Connection conn) throws SQLException;
    }

    //private OracleDataSource dataSource;
    private DataSource dataSource;

    // SOLDIER ALIASES //
    private static final String SOLDIER_ID = "soldier_id";
    private static final String SOLDIER_NAME = "soldier_name";
    private static final String SOLDIER_RANK = "soldier_rank";
    private static final String SOLDIER_COMMANDER = "soldier_commander";
    private static final String SOLDIER_UNIT = /*"soldier_*/"unit_name"; // TODO careful
    private static final String SOLDIER_BIRTHDATE = "soldier_birthdate";
    private static final String SOLDIER_COMMANDER_NAME = "commander_name";
    // UNIT ALIASES //
    private static final String UNIT_ID = "unit_id";
    private static final String UNIT_NAME = "unit_name";
    private static final String UNIT_HEAD_NAME = "head_of_unit_name";//"unit_"; // TODO careful
    private static final String UNIT_HEAD_ID = "head_of_unit";//"unit_"; // TODO careful
    private static final String UNIT_LOCATION = "location_name";//"unit_"; //TODO careful
    // LOCATION ALIASES //
    private static final String LOCATION_ID = "location_id";
    private static final String LOCATION_NAME = "location_name";
    private static final String LOCATION_REGION = "location_region";
    private static final String LOCATION_CITY = "location_city";

    private static final Map<Alias, String> aliasMapping = new HashMap<Alias, String>() {{
        put(Alias.SOLDIER_ID, SOLDIER_ID);
        put(Alias.SOLDIER_NAME, SOLDIER_NAME);
        put(Alias.SOLDIER_RANK, SOLDIER_RANK);
        put(Alias.SOLDIER_COMMANDER, SOLDIER_COMMANDER);
        put(Alias.SOLDIER_UNIT, SOLDIER_UNIT);
        put(Alias.SOLDIER_BIRTHDATE, SOLDIER_BIRTHDATE);
        put(Alias.SOLDIER_COMMANDER_NAME, SOLDIER_COMMANDER_NAME);
        put(Alias.UNIT_ID, UNIT_ID);
        put(Alias.UNIT_NAME, UNIT_NAME);
        put(Alias.UNIT_HEAD_ID, UNIT_HEAD_ID);
        put(Alias.UNIT_HEAD_NAME, UNIT_HEAD_NAME);
        put(Alias.UNIT_LOCATION, UNIT_LOCATION);
        put(Alias.LOCATION_ID, LOCATION_ID);
        put(Alias.LOCATION_NAME, LOCATION_NAME);
        put(Alias.LOCATION_CITY, LOCATION_CITY);
        put(Alias.LOCATION_REGION, LOCATION_REGION);
    }};

    private String getColumnNameForAlias(Alias alias) {
        return aliasMapping.get(alias);
    }

    private static final String SQL_GET_SOLDIERS_PURE_4_INSERT =
            // values to take // soldier_id // soldier_name // soldier_rank // soldier_commander // unit_name // soldier_birthdate
            "select soldier_id as soldier_id, name as soldier_name, rank as soldier_rank, commander as soldier_commander, unit as unit_name, birthdate as soldier_birthdate from soldier ";

    private static final String SQL_GET_ALL_SOLDIERS_FULL_INFO =
            // values to take // soldier_id// soldier_name // soldier_rank // soldier_commander // unit_name // soldier_birthdate // location_name // commander_name
            "select soldier_id, soldier_name, soldier_rank, soldier_commander, unit_name, soldier_birthdate, location_name, commander.name as commander_name from (select soldier_id as soldier_id,soldier.name as soldier_name,soldier.rank as soldier_rank,soldier.commander as soldier_commander,unit.name as unit_name,soldier.birthdate as soldier_birthdate, location.name as location_name from unit join soldier on unit = unit_id join location on location.loc_id = unit.location) soldier left join (select soldier_id as id, name from soldier) commander on soldier.soldier_commander = commander.id ";

    private static final String SQL_GET_ALL_SOLDIERS =
            // values to take // soldier_id // soldier_name // soldier_rank // soldier_commander // unit_name // soldier_birthdate
            "select soldier_id as soldier_id,soldier.name as soldier_name,soldier.rank as soldier_rank,soldier.commander as soldier_commander,unit.name as unit_name,soldier.birthdate as soldier_birthdate from unit join soldier on unit = unit_id ";

    private static final String SQL_GET_SOLDIER_BY_ID_FOR_UPDATE =
            // values to take // soldier_id // soldier_name // soldier_rank // soldier_commander // unit_name // soldier_birthdate
            "select soldier_id as soldier_id,soldier.name as soldier_name,soldier.rank as soldier_rank,soldier.commander as soldier_commander,soldier.unit as unit_name,soldier.birthdate as soldier_birthdate from soldier " +
                    "where soldier_id = ? ";

    private static final String SQL_GET_SOLDIER_BY_ID =
            // values to take // soldier_id // soldier_name // soldier_rank // soldier_commander // unit_name // soldier_birthdate
            "select soldier_id as soldier_id,soldier.name as soldier_name,soldier.rank as soldier_rank,soldier.commander as soldier_commander,unit.name as unit_name,soldier.birthdate as soldier_birthdate from unit join soldier on unit = unit_id " +
                    "where soldier_id = ? ";

    private static final String SQL_GET_COMMANDER_OF_SOLDIER_BY_ID =
            "select \n" +
                    "    soldier_id as soldier_id,\n" +
                    "    soldier.name as soldier_name,\n" +
                    "    soldier.rank as soldier_rank,\n" +
                    "    soldier.commander as soldier_commander,\n" +
                    "    unit.name as unit_name,\n" +
                    "    soldier.birthdate as soldier_birthdate \n" +
                    "  from soldier join unit on unit.unit_id = soldier.unit\n" +
                    "  where soldier.soldier_id = (select commander from soldier where soldier_id = ?)";

    private static final String SQL_GET_TOP_OF_SOLDIERS =
// values to take // soldier_id // soldier_name // soldier_rank // soldier_commander // unit_name // soldier_birthdate
            "select soldier_id as soldier_id,soldier.name as soldier_name,soldier.rank as soldier_rank,soldier.commander as soldier_commander,unit.name as unit_name,soldier.birthdate as soldier_birthdate,location.name as location_name from unit join soldier on unit = unit_id join location on location.loc_id = unit.location " +
                    "where commander is null";

    private static final String SQL_GET_SUBS_OF_SOLDIER_BY_ID =
// values to take // soldier_id // soldier_name // soldier_rank // soldier_commander // unit_name // soldier_birthdate
            "select soldier_id as soldier_id,soldier.name as soldier_name,soldier.rank as soldier_rank,soldier.commander as soldier_commander,unit.name as unit_name,soldier.birthdate as soldier_birthdate from soldier left join unit on unit = unit_id " +
                    "start with commander = ? connect by prior soldier_id = commander and level = 1  order by 1 ";

    private static final String SQL_GET_SUBS_OF_PARENT_OF_SOLDIER_BY_ID =
            "select\n" +
                    "        soldier_id as soldier_id,\n" +
                    "        soldier.name as soldier_name,\n" +
                    "        soldier.rank as soldier_rank,\n" +
                    "        soldier.commander as soldier_commander,\n" +
                    "        unit.name as unit_name,\n" +
                    "        soldier.birthdate as soldier_birthdate\n" +
                    "  from soldier left join unit on unit = unit_id\n" +
                    "  where soldier.commander = (select commander from soldier where soldier_id = ? )\n" +
                    "  order by 1";

    private static final String SQL_GET_SOLDIERS_OF_UNIT =
// values to take // soldier_id // soldier_name // soldier_rank // soldier_commander // unit_name // soldier_birthdate
            "select soldier_id as soldier_id,soldier.name as soldier_name,soldier.rank as soldier_rank,soldier.commander as soldier_commander,unit.name as unit_name,soldier.birthdate as soldier_birthdate from soldier left join unit on unit.unit_id = soldier.unit " +
                    "where unit_id = ? ";

    private static final String SQL_GET_HIERARCHY_OF_SOLDIERS_BY_ID =
// values to take // soldier_id // soldier_name // soldier_rank // soldier_commander // unit_name // soldier_birthdate
            "select soldier_id as soldier_id,soldier.name as soldier_name,soldier.rank as soldier_rank,soldier.commander as soldier_commander,unit.name as unit_name,soldier.birthdate as soldier_birthdate from soldier left join unit on unit = unit_id " +
                    "start with soldier_id = ? " +
                    "connect by prior commander = soldier_id " +
                    "order by level desc ";

    private static final String SQL_GET_SOLDIERS_WITHOUT_SUBS_BY_ID =
            // values to take // soldier_id // soldier_name // soldier_rank // soldier_commander // unit_name // soldier_birthdate
            "select soldier.soldier_id as soldier_id, soldier.name as soldier_name,soldier.rank as soldier_rank,soldier.commander as soldier_commander,unit.name as unit_name,soldier.birthdate as soldier_birthdate from ( soldier left join\n" +
                    "(select soldier.soldier_id as soldier_id, soldier.name as soldier_name,soldier.rank as soldier_rank,soldier.commander as soldier_commander,unit.name as unit_name,soldier.birthdate as soldier_birthdate from soldier left join unit on unit = unit_id start with soldier_id = ? connect by prior soldier_id=commander order by level desc) hie_soldier on soldier.soldier_id = hie_soldier.soldier_id) left join unit on unit = unit_id\n" +
                    "where hie_soldier.soldier_id is null order by soldier_id";

    private static final String SQL_GET_ALL_UNITS =
            // values to take // unit_id // unit_name // head_of_unit // head_of_unit_name // location_name
            "select unit_id as unit_id,unit.name as unit_name, unit.headOfUnit as head_of_unit,soldier.name as head_of_unit_name, location.name as location_name from unit join location on unit.location = location.loc_id left join soldier on unit.headOfUnit = soldier.soldier_id";

    private static final String SQL_GET_UNIT_BY_ID =
            // values to take // unit_id // unit_name // head_of_unit // head_of_unit_name // location_name
            "select unit_id as unit_id,unit.name as unit_name,unit.headOfUnit as head_of_unit,soldier.name as head_of_unit_name,location.name as location_name from unit join location on unit.location = location.loc_id left join soldier on unit.headOfUnit = soldier.soldier_id where unit_id = ? ";

    private static final String SQL_GET_UNITS_OF_LOCATION =
            // values to take // unit_id // unit_name // head_of_unit // head_of_unit_name // location_name
            "select unit_id as unit_id,unit.name as unit_name,unit.headOfUnit as head_of_unit,soldier.name as head_of_unit_name,location.name as location_name from unit join location on unit.location = location.loc_id left join soldier on unit.headOfUnit = soldier.soldier_id where location.loc_id = ? ";

    private static final String SQL_GET_ALL_LOCATIONS =
            // values to take // location_id // location_name // location_region // location_city
            "select loc_id as location_id, name as location_name, region as location_region, city as location_city from location ";

    private static final String SQL_GET_LOCATION_BY_ID =
            // values to take // location_id // location_name // location_region // location_city
            "select loc_id as location_id, name as location_name, region as location_region, city as location_city from location where loc_id = ? ";

    private static final String SQL_GET_UNIT_BY_ID_FOR_UPDATE = // values to take // unit_id // unit_name // head_of_unit // location_name
            "select unit_id as unit_id, unit.name as unit_name, unit.headOfUnit as head_of_unit, unit.location as location_name from unit where unit_id = ? ";

    private static final String SQL_RELEASE_SOLDIER_FROM_UNIT_COMMANDING =
            "update unit set headOfUnit = null where unit.unit_id = ? ";

    public void init(Map<String, String> initParams) {
        try {
            log.debug("jndi from descriptors " + initParams.get("jndiPath"));
            dataSource = (DataSource) new InitialContext().lookup(initParams.get("jndiPath"));
        } catch (NamingException e) {
            log.error("Cannot find resources over jndi", e);
            throw new DAOInitException("Cannot find resources over jndi", e);
        }
    }

    /**
     * Main generalized tool for performing queries with callback
     *
     * @param query      to perform
     * @param parser     to parse
     * @param parameters to include into perform
     * @return Object parsed with given parser
     * @throws SQLException        if have some exceptions with query performing
     * @throws ncl.military.dao.exceptions.DataAccessException if have troubles with q.p. accessories (connection, preparedStatement, resultset)
     */
    private Object performQuery(String query, SetParser parser, String... parameters) throws SQLException, DataAccessException {
        Connection conn = null;
        PreparedStatement prst = null;
        ResultSet rs = null;
        SQLException exception = null;
        try {
            conn = dataSource.getConnection();
            prst = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            log.debug("Preparing statement with query : " + query);

            int currentParameter = 0;
            for (String parameter : parameters) {
                prst.setString(++currentParameter, parameter);
                log.debug("assigning parameter #" + currentParameter + " with value: " + parameter);
            }

            rs = prst.executeQuery();

            return parser.parse(rs, prst, conn);
        } catch (SQLException e) {
            log.error("Some SQL error occured.", e);
            exception = e;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    log.error("Result set closing error.", e);
                    if (exception == null) exception = e;
                } finally {
                    if (prst != null) {
                        try {
                            prst.close();
                        } catch (SQLException e) {
                            log.error("Prepared statement closing error", e);
                            if (exception == null) exception = e;
                        } finally {
                            if (conn != null) {
                                try {
                                    conn.close();
                                } catch (SQLException e) {
                                    log.error("Connection closing error", e);
                                    if (exception == null) exception = e;
                                }
                            } else
                                log.error("Connection was null.");
                        }
                    } else
                        log.error("PreparedStatement was null.");
                }
            } else
                log.error("ResultSet was null.");
            if (exception != null)
                throw new DataAccessException("Performing query/Closing connection resources errors.", exception); // this will not override exception from catch block
        }
        return parser;
    }

    /**
     * Level up generalization
     *
     * @param query to perform (which you are sure will select soldiers)
     * @return List of selected with given query soldiers
     * @throws DataAccessException if query performing or result parsing ended with error
     */
    private List<ncl.military.entity.Soldier> getSoldiersListCustomQuery(String query) throws DataAccessException {
        try {
            return (List<ncl.military.entity.Soldier>) performQuery(query, new SetParser() {
                public Object parse(ResultSet raw, PreparedStatement prst, Connection conn) throws SQLException {
                    List<ncl.military.entity.Soldier> soldiers = new ArrayList<ncl.military.entity.Soldier>();
                    while (raw.next()) {
                        ncl.military.entity.Soldier sd = new ncl.military.entity.Soldier(
                                raw.getString(SOLDIER_ID),
                                raw.getString(SOLDIER_NAME),
                                raw.getString(SOLDIER_RANK),
                                raw.getString(SOLDIER_UNIT),
                                raw.getString(SOLDIER_COMMANDER),
                                raw.getDate(SOLDIER_BIRTHDATE)
                        );
                        soldiers.add(sd);
                    }
                    return soldiers;
                }
            });
        } catch (SQLException e) {
            log.error("Parsing result set error.", e);
            throw new DataAccessException("Performing data operations failed.", e);
        }
    }
}
