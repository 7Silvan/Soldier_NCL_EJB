package ncl.military.dao.ejb;

import ncl.military.dao.tools.EntityValue;

import javax.ejb.*;
import java.util.List;


public class LocationBean implements EntityBean {
    public LocationBean() {
    }

    public void setEntityContext(EntityContext entityContext) throws EJBException {
    }

    public void unsetEntityContext() throws EJBException {
    }

    public void ejbRemove() throws RemoveException, EJBException {
    }

    public void ejbActivate() throws EJBException {
    }

    public void ejbPassivate() throws EJBException {
    }

    public void ejbLoad() throws EJBException {
    }

    public void ejbStore() throws EJBException {
    }

    public String ejbFindByPrimaryKey(String key) throws FinderException {
        return null;
    }

    public String ejbFindAll() throws FinderException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getId() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getRegion() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getCity() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void ejbHomeEditLocation(String key, List<EntityValue> values) throws FinderException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


}
