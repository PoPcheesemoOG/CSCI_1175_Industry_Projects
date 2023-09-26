

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

	private TextArea ta = new TextArea();			// Text area for displaying contents
	private int clientNo = 0;						// Number for a client

	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Scene(new ScrollPane(ta), 450, 200);
		primaryStage.setTitle("MultiThreadServer");	// Set stage title
		primaryStage.setScene(scene);				// Place the scene in the stage
		primaryStage.show();						// Display the stage

		new Thread( () -> {
			try {
				ServerSocket serverSocket = new ServerSocket(1337);
				ta.appendText("MultiThreadServer started at " + new Date() + '\n');
				while (true) {
					Socket socket = serverSocket.accept();
					clientNo++;

					Platform.runLater( () -> {
						ta.appendText("Starting thread for client " + clientNo + " at " + new Date() + '\n');
						InetAddress inetAddress = socket.getInetAddress();
						ta.appendText("Client " + clientNo + "'s host name is " + inetAddress.getHostName() + '\n');
						ta.appendText("Client " + clientNo + "'s IP Address is " + inetAddress.getHostAddress() + "\n");						
					});
					new Thread(new HandleAClient(socket)).start();
				}
			}
			catch (IOException e) {
				System.err.println(e);
			}
		}).start();

	}

	class HandleAClient implements Runnable {
		private Socket socket; // A connected socket

		public HandleAClient(Socket socket) {
			this.socket = socket;
		}
		@Override
		public void run() {
			try {
				DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
				DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());
				while (true) {
					double radius = inputFromClient.readDouble();
					double area = radius * radius * Math.PI;
					outputToClient.writeDouble(area);
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
}