// Exercise31_01Server.java: The server can communicate with
// multiple clients concurrently using the multiple threads
import java.util.*;
import java.net.*;
import java.io.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Exercise33_01Server extends Application {
	// Text area for displaying contents
	private TextArea ta = new TextArea();
	private int clientNo = 0;

	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) {
		ta.setWrapText(true);

		// Create a scene and place it in the stage
		Scene scene = new Scene(new ScrollPane(ta), 400, 200);
		primaryStage.setTitle("Exercise31_01Server"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
	
		new Thread( () -> {
			try {
				ServerSocket serverSocket = new ServerSocket(1337);
				ta.appendText("Starting multi thread server at: " + new Date() + '\n');
				while (true) {
					Socket socket = serverSocket.accept();
					clientNo++;
					
					Platform.runLater( () -> {
						ta.appendText("Starting thread for client: " + clientNo + '\n');
						InetAddress inetAddress = socket.getInetAddress();
						ta.appendText("Client " + clientNo + "'s host name is " + inetAddress.getHostName() + '\n');
						ta.appendText("Client " + clientNo + "'s host address is " + inetAddress.getHostAddress() + '\n');
					
					});	
					new Thread(new HandleAClient(socket)).start();
				}
			}
			catch (IOException e) {
				System.err.println(e);
			}
		}).start();	
	}

	/**
	 * The main method is only needed for the IDE with limited
	 * JavaFX support. Not needed for running from the command line.
	 */
	public static void main(String[] args) {
		launch(args);
	}
	class HandleAClient implements Runnable {
		private Socket socket;
		public HandleAClient(Socket socket) {
			this.socket = socket;
		}
		@Override
		public void run() {
			try {
				DataInputStream fromClient = new DataInputStream(socket.getInputStream());
				ObjectOutputStream toClient = new ObjectOutputStream(socket.getOutputStream());
				
				while (true) {
					
					double annualInterestRate = fromClient.readDouble();
					ta.appendText("Annual Interest Rate: " + annualInterestRate + '\n');
					int numberOfYears = fromClient.readInt();
					ta.appendText("Number of Years: " + numberOfYears + '\n');
					double loanAmount = fromClient.readDouble();
					ta.appendText("Loan Amount: " + loanAmount + '\n');
					
					Loan loan = new Loan(annualInterestRate, numberOfYears, loanAmount);
					
					toClient.writeObject(loan);
					
				}			
			}
			catch (Exception ex) {
				System.err.println(ex);
			}
		}
	}
}
