package Dispatcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.Date;

import Interface.MyInterface;

public class Dispatcher {

	public static void main(String[] args) throws IOException {
		System.out.println("Dispatcher has started!"); 
		ServerSocket ss = new ServerSocket(8888); // using port 8888 set up a ServerSocket object
		Socket s = null;
		while ((s = ss.accept()) != null) { // listen to the request, start a thread for a client after accepting
			new ListenerThread(s).start();
		}
		ss.close();
	}
}

class ListenerThread extends Thread {
	private Socket socket;

	public ListenerThread(Socket socket) {
		super();
		this.socket = socket;
	}



	public void run() {
		try {
			InputStream in = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(isr);

			String inputStr = br.readLine();

				// define operation "R"(Read) or "W"(Write)	
				inputStr = inputStr.trim();
				String[] inputSplit = inputStr.split(" ");
				String operation = inputSplit[0];
				int dataID = 0;
				if (inputSplit.length>1) {
					dataID = Integer.parseInt(inputSplit[1]);;
				}
				int dataValue = 0;
				// if change value is not null
				if (operation.equals("W") && inputSplit.length>2) {
					dataValue = Integer.parseInt(inputSplit[2]);
				}

				OutputStream os = socket.getOutputStream();
				PrintWriter pw = new PrintWriter(os);
				MyInterface RMIobject = null;
				int RMIdata = 0;
				// decide which server the message should be sent to
				if (dataID >= 1 && dataID < 10) {
					RMIobject = (MyInterface) Naming
							.lookup("rmi://localhost:8889/Server1");
				} else{// if dataID if not between 1 and 9, just handle to Server2(10-19 or other)
					RMIobject = (MyInterface) Naming
							.lookup("rmi://localhost:8886/Server2");
				}
				if (operation.equals("R")) {
					RMIdata = RMIobject.dataRead(dataID);
					if(RMIdata==-1){
						System.out.println("have no record of "+dataID);
						pw.write( "have no record of"+dataID+" (read operation)");
					}else{
						System.out.println("read operation,dataID:"+dataID+" value:"+RMIdata);
						pw.write(RMIdata + " (read operation)");	
					}
				} else if (operation.equals("W")) {
					boolean b = RMIobject.dataWrite(dataID, dataValue);
					if(b){
					System.out.println("write operation success,DataID:"+dataID+" value is "+dataValue);
					pw.write(b + " (wirte operation)");
					}else{
						System.out.println("write operation fail,dataID:"+dataID);
						pw.write("write operation fail");
					}
					
				} else if (operation.equals("E")) {
					pw.write( "disconnected");

				}

				pw.flush();
				pw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (socket != null)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
