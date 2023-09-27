

import java.io.*;
import java.net.*;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;


public class MultiThreadServer extends Application {

	private TextArea ta = new TextArea();					// Text area for displaying contents
	private int clientNo = 0;								// Number for a client

	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Scene(new ScrollPane(ta), 450, 200);
		primaryStage.setTitle("MultiThreadServer");			// Set stage title
		primaryStage.setScene(scene);						// Place the scene in the stage
		primaryStage.show();								// Display the stage

		new Thread( () -> {
			try {
				ServerSocket serverSocket = new ServerSocket(1338);
				ta.appendText("MultiThreadServer started at " + new Date() + '\n');
				while (true) {
					Socket socket = serverSocket.accept();	// Listen for a new connection request
					clientNo++;								// Increment client number

					Platform.runLater( () -> {
						// Display client number
						ta.appendText("Starting thread for client " + clientNo + " at " + new Date() + '\n');
						// Find the client's host name, and IP address
						InetAddress inetAddress = socket.getInetAddress();
						ta.appendText("Client " + clientNo + "'s host name is " + inetAddress.getHostName() + '\n');
						ta.appendText("Client " + clientNo + "'s IP Address is " + inetAddress.getHostAddress() + "\n");						
					});
					// Create and start a new thread for the connection
					new Thread(new HandleAClient(socket)).start();
				}
			}
			catch (IOException e) {
				System.err.println(e);
			}
		}).start();
	}
	
	class HandleAClient implements Runnable {				// Define the thread class for handling a new connection
		private Socket socket; 								// A connected socket

		public HandleAClient(Socket socket) {				// Construct a thread
			this.socket = socket;
		}
		@Override
		public void run() {									// Run a thread
			try {
				// Create data input and output streams
				DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
				DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());
				
				// Continuously serve the client 
				while (true) {
					// Receive radius from the client
					double radius = inputFromClient.readDouble();
					// Compute area
					double area = radius * radius * Math.PI;
					// Send area back to the client
					outputToClient.writeUTF("" + area);
			//		outputToClient.writeDouble(area);
					outputToClient.flush();
					Platform.runLater(() -> {
						ta.appendText("radius received from client: " +  radius + '\n');
						ta.appendText("Area found: " + area + '\n');
					});
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		// new MultiThreadServer();
		launch (args);
	}
}