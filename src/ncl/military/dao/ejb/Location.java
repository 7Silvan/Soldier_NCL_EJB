package ncl.military.dao.ejb;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

public interface Location extends EJBObject {
    public String getId() throws RemoteException;
    public String getName() throws RemoteException;
    public String getRegion() throws RemoteException;
    public String getCity() throws RemoteException;
}
