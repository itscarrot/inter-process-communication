package Server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Interface.MyInterface;

public class ServerImplement extends UnicastRemoteObject implements MyInterface {

	// List <dataRec>data = new ArrayList<dataRec>();
	Map<Integer, dataRec> data = new HashMap<Integer, dataRec>();

	public ServerImplement() throws RemoteException {
		// initialize the data, put them in hashmap,dataID from 1 to 9
		for (int i = 1; i < 10; i++) {
			data.put(i, new dataRec(i, 0));
		}
	}

	public int dataRead(int dataID) throws RemoteException {
		// TODO Auto-generated method stub
		// get the object from the hashmap according the dataID
		dataRec readData = data.get(dataID);
		int value = -1;
		if (dataID >= 1 && dataID < 10)
			value = readData.op_read();
		return value;
	}

	public boolean dataWrite(int dataID, int value) throws RemoteException {
		// TODO Auto-generated method stub
		dataRec writeData = data.get(dataID);
		boolean b = false;
		if (dataID >= 1 && dataID < 10)
			b = writeData.op_write(value);
		return b;
	}
}
