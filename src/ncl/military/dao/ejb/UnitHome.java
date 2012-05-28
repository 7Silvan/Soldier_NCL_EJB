package ncl.military.dao.ejb;

import javax.ejb.EJBHome;
import javax.ejb.FinderException;
import java.rmi.RemoteException;

public interface UnitHome extends EJBHome {
    ncl.military.dao.ejb.Unit findByPrimaryKey(String key) throws RemoteException, FinderException;
}
