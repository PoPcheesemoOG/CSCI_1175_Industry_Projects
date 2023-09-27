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
		
		double clientInputDouble;
		double Area;
//		int Area1;
		String Area1;
		DataOutputStream outputToServer = new DataOutputStream(socket.getOutputStream());
		DataInputStream inputFromServer = new DataInputStream(socket.getInputStream());

		String str = "";

		while (clientInput != "STOP") {
			System.out.println("Please give the radius of a circle");
			clientInputDouble = in.nextDouble();

			outputToServer.writeDouble(clientInputDouble);
			outputToServer.flush();
			
//			Area = inputFromServer.readDouble();

//			Area1 = input.read();
			Area1 = inputFromServer.readUTF();
			
//			System.out.println("Area of the circle is: " + Area);
			System.out.println("Area of the circle is: " + Area1);
				
		}
	}
}
