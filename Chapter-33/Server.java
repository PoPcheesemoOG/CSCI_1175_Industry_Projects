import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Server {
	public static void main(String[] args) throws IOException {
		ServerSocket serversocket = new ServerSocket(1338);
		Socket socket1 = serversocket.accept();

		System.out.println("Client Connected");

		InputStreamReader input = new InputStreamReader(socket1.getInputStream());
		BufferedReader br = new BufferedReader(input);

		String str = br.readLine();

		Scanner in = new Scanner(System.in);
		String serverInput = "";
		PrintWriter pw = new PrintWriter(socket1.getOutputStream());

		while (str != "STOP") {

			System.out.println("Client: " + str);

			serverInput = in.nextLine();

			pw.println(serverInput);
			pw.flush();
			
			System.out.println("Server: " + serverInput);
			
			str = br.readLine();
		}



//		while (serverInput != "STOP") {
//			System.out.println("Please write message and hit enter to send, "
//					+ "type in STOP to stop.");
//			serverInput = in.nextLine();
//
//			pw.println(serverInput);
//			pw.flush();
//
//		}
	}
}
