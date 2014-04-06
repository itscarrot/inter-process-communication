package Server;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import Interface.MyInterface;

public class Server1 { 
    public static void main(String args[]) { 

        try { 
            //create a new remote object 
        	 MyInterface  S1 = new ServerImplement(); 
            //Registry,indicate the port is 8889
            LocateRegistry.createRegistry(8889); 
            //register the object to the server, naming Server1 
            Naming.bind("rmi://localhost:8889/Server1",S1); 
            System.out.println("Server1 has started!"); 
            
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