package ncl.military.dao.ejb;

import javax.ejb.EJBHome;
import javax.ejb.FinderException;
import java.rmi.RemoteException;
import java.util.List;

import ncl.military.dao.ejb.Location;
import ncl.military.dao.tools.EntityValue;

public interface LocationHome extends EJBHome {
    public List<Location> findAll() throws RemoteException, FinderException;
    public Location findByPrimaryKey(String key) throws RemoteException, FinderException;
    public void editLocation(String key, List<EntityValue> values) throws RemoteException, FinderException;
}
