// Exercise31_01Client.java: The client sends the input to the server and receives
// result back from the server
import javafx.application.Application;
import java.io.*;
import java.net.*;
import java.util.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Exercise33_01Client extends Application {
	// Text field for receiving data
	private TextField tfAnnualInterestRate = new TextField();
	private TextField tfNumOfYears = new TextField();
	private TextField tfLoanAmount = new TextField();
	private Button btSubmit= new Button("Submit");

	// Text area to display contents
	private TextArea ta = new TextArea();

	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) throws IOException {
		ta.setWrapText(true);

		GridPane gridPane = new GridPane();
		gridPane.add(new Label("Annual Interest Rate"), 0, 0);
		gridPane.add(new Label("Number Of Years"), 0, 1);
		gridPane.add(new Label("Loan Amount"), 0, 2);
		gridPane.add(tfAnnualInterestRate, 1, 0);
		gridPane.add(tfNumOfYears, 1, 1);
		gridPane.add(tfLoanAmount, 1, 2);
		gridPane.add(btSubmit, 2, 1);

		tfAnnualInterestRate.setAlignment(Pos.BASELINE_RIGHT);
		tfNumOfYears.setAlignment(Pos.BASELINE_RIGHT);
		tfLoanAmount.setAlignment(Pos.BASELINE_RIGHT);

		tfAnnualInterestRate.setPrefColumnCount(5);
		tfNumOfYears.setPrefColumnCount(5);
		tfLoanAmount.setPrefColumnCount(5);

		BorderPane pane = new BorderPane();
		pane.setCenter(new ScrollPane(ta));
		pane.setTop(gridPane);

		// Create a scene and place it in the stage
		Scene scene = new Scene(pane, 400, 250);
		primaryStage.setTitle("Exercise31_01Client"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage

		Socket socket = new Socket("localhost", 1337);
		//		Scanner in = new Scanner(System.in);
		//		String clientInput = "";

		DataOutputStream toServer = new DataOutputStream(socket.getOutputStream());
		ObjectInputStream fromServer = new ObjectInputStream(socket.getInputStream());

		btSubmit.setOnAction(e -> {
			try {
				double annualInterestRate = Double.parseDouble(tfAnnualInterestRate.getText());			
				int numberOfYears = Integer.parseInt(tfNumOfYears.getText());			
				double loanAmount = Double.parseDouble(tfLoanAmount.getText());
				toServer.writeDouble(annualInterestRate);
				toServer.writeInt(numberOfYears);
				toServer.writeDouble(loanAmount);
				Loan loan = (Loan) fromServer.readObject();		
				ta.appendText(loan.toString());
			}
			catch (Exception ex){
				System.err.println(ex);
			}
		});
	}
	/**
	 * The main method is only needed for the IDE with limited
	 * JavaFX support. Not needed for running from the command line.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
