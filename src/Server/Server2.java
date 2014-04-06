package Server;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import Interface.MyInterface;

public class Server2 { 
    public static void main(String args[]) { 

        try { 
        	//create a new remote object  
        	 MyInterface  S2 = new Server2Implement(); 
        	//Registry,indicate the port is 8886
            LocateRegistry.createRegistry(8886); 
           
          //register the object to the server, naming Server2 
            Naming.bind("rmi://localhost:8886/Server2",S2); 
            System.out.println("Server2 has started!"); 
            
        } catch (RemoteException e) { 
            System.out.println("creating remote object occur exception"); 
            e.printStackTrace(); 
        } catch (AlreadyBoundException e) { 
            System.out.println("duplicati blinding!"); 
            e.printStackTrace(); 
        } catch (MalformedURLException e) { 
            System.out.println("URL is not correct"); 
            e.printStackTrace(); 
        } 
    } 
}