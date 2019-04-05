import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class MultithreadedEchoServerTCP {
	private static final int PORT = 1235;
	public static ConcurrentHashMap<String,ArrayList<String>> TableOf = new ConcurrentHashMap();
	
	public static void main(String args[]) throws IOException {

		ServerSocket connectionSocket = connectionSocket = new ServerSocket(PORT);
		ArrayList<String> temp = new ArrayList<String>();
		temp.add("ANAXWRISI");
		temp.add("12:30");
		
		TableOf.put("XY1234", temp );

		
		

		while (true) {	

			System.out.println("Server is listening to port: " + PORT);
			Socket dataSocket = connectionSocket.accept();
			System.out.println("Received request from " + dataSocket.getInetAddress());

			ServerThread sthread = new ServerThread(dataSocket, TableOf);
			sthread.start();
		}
	}
}