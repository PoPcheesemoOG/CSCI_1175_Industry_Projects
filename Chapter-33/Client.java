import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Client {

	public static void main(String[] args) throws IOException {

		Socket socket = new Socket("localhost", 1338);
		Scanner in = new Scanner(System.in);
		String clientInput = "";
		PrintWriter pw = new PrintWriter(socket.getOutputStream());
		InputStreamReader input = new InputStreamReader(socket.getInputStream());
		BufferedReader br = new BufferedReader(input);

		String str = "";

		while (clientInput != "STOP") {
			System.out.println("Please write message and hit enter to send, "
					+ "type in stop to stop.");
			
			if (clientInput != null) {
				clientInput = in.nextLine();
				pw.println(clientInput);
				pw.flush();
				clientInput = null;
			}

			
			str = br.readLine();

			if(str != null) {	
				System.out.println("Server: " + str);
				str = null;
			}
		}


		while (br.readLine() != "STOP") {

			System.out.println("Server: " + str);
			str = br.readLine();
		}
	}
}
