package Interface;

import java.rmi.Remote;
import java.rmi.RemoteException;
 
public interface MyInterface extends Remote { 
  
    public int dataRead(int dataID) throws RemoteException;
    public boolean dataWrite(int dataID, int value) throws RemoteException;
}