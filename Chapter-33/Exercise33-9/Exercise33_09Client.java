import javafx.application.Application;
import javafx.application.Platform;

import static javafx.application.Application.launch;
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

public class Exercise33_09Client extends Application {
	private TextArea taServer = new TextArea();
	private TextArea taClient = new TextArea();

	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) throws IOException {
		taServer.setWrapText(true);
		taClient.setWrapText(true);
		//taServer.setDisable(true);

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
		primaryStage.setTitle("Exercise31_09Client"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage

		// To complete later
		Socket socket = new Socket("localhost", 1338);

		DataOutputStream toServer = new DataOutputStream(socket.getOutputStream());
		DataInputStream fromServer = new DataInputStream(socket.getInputStream());

		taClient.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				try {
					taServer.appendText("Client: " + taClient.getText().trim() + '\n');
					toServer.writeUTF(taClient.getText().trim());
					taClient.clear();
					toServer.flush();

				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});
		new Thread(() -> {
			try {
				while (true) {
					String message = fromServer.readUTF();

					Platform.runLater( () -> {
						taServer.appendText("Server: " + message + '\n');
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
