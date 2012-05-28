package ncl.military.dao.ejb;

import javax.ejb.EJBHome;
import javax.ejb.FinderException;
import java.rmi.RemoteException;

public interface SoldierHome extends EJBHome {
    ncl.military.dao.ejb.Soldier findByPrimaryKey(String key) throws RemoteException, FinderException;
}
