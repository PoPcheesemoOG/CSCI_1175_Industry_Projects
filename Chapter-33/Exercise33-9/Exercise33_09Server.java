import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.net.*;
import java.io.*;
import java.util.*;

public class Exercise33_09Server extends Application {
	private TextArea taServer = new TextArea();
	private TextArea taClient = new TextArea();

	DataInputStream fromClient = null;
	DataOutputStream toClient = null;

	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) {
		taServer.setWrapText(true);
		taClient.setWrapText(true);
		//taClient.setDisable(true);

		BorderPane pane1 = new BorderPane();
		pane1.setTop(new Label("History"));
		pane1.setCenter(new ScrollPane(taServer));
		BorderPane pane2 = new BorderPane();
		pane2.setTop(new Label("New Message"));
		pane2.setCenter(new ScrollPane(taClient));

		VBox vBox = new VBox(5);
		vBox.getChildren().addAll(pane1, pane2);

		// Create a scene and place it in the stage
		Scene scene = new Scene(vBox, 400, 350);
		primaryStage.setTitle("Exercise31_09Server"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage

		// To complete later


		taClient.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				try {
					taServer.appendText("Server: " + taClient.getText().trim() + '\n');
					toClient.writeUTF(taClient.getText().trim());
					taClient.clear();
					toClient.flush();

				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});
		new Thread (() -> {
			try {
				ServerSocket serverSocket = new ServerSocket(1338);
				Socket socket = serverSocket.accept();
				fromClient = new DataInputStream(socket.getInputStream());
				toClient = new DataOutputStream(socket.getOutputStream());
				taServer.appendText("Server started at " + new Date() + '\n');
				taServer.appendText("Starting thread for Client.\n");
				InetAddress inetAddress = socket.getInetAddress();
				taServer.appendText("Client's host name is " + inetAddress.getHostName() + '\n');

				while (true) {
					String message = fromClient.readUTF();
					
					Platform.runLater( () -> {
						taServer.appendText("Client: " + message + '\n');
					});
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}).start();
	}

	public static void main(String[] args) {
		launch(args);
	}
}