package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public class TCPClinet {

	public static void main(String[] args) throws UnknownHostException,
			IOException {
		System.out.println("format:operation dataID (value,if write operation)");
		System.out.println("example: read operation:R 1 | write operation:W 1 20");
		while (true) {
			Socket s = new Socket("localhost", 8888);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			String inputStr = "";
			inputStr = in.readLine();


			OutputStream socketOut = s.getOutputStream();
			inputStr = inputStr + "\r\n";
			socketOut.write(inputStr.getBytes());

			InputStream is = s.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String msg = "";
			while ((msg = br.readLine()) != null)
				System.out.println(msg);
			if(inputStr.equals("E"))break;
		}
	}

}
