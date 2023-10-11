/* Name: Paul Helske
 * Date: 10/10/23
 */

import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.stage.*;
import javafx.application.*;
import javafx.scene.*;

public class f extends Application {
	private TextField tfInvestment = new TextField();
	private TextField tfYears = new TextField();
	private TextField tfInterest = new TextField();
	private TextField tfFutureValue = new TextField();
	double futureValue, investmentAmount, monthlyInterestRate, years;


	@Override
	public void start(Stage primaryStage) throws Exception {
		MenuBar menuBar = new MenuBar();		
		Menu menuOperation = new Menu("Operation");		
		menuBar.getMenus().add(menuOperation);

		MenuItem menuItemCalculate = new MenuItem("Calculate");
		MenuItem menuItemExit = new MenuItem("Exit");

		menuOperation.getItems().addAll(menuItemCalculate, menuItemExit);

		VBox vbox1 = new VBox(2);
		HBox hbox1 = new HBox(2);
		tfInvestment.setPrefColumnCount(2);
		tfYears.setPrefColumnCount(2);
		tfInterest.setPrefColumnCount(2);
		tfFutureValue.setPrefColumnCount(2);
		
		
		vbox1.getChildren().addAll(new Label("Invesment Amount: "), tfInvestment, new Label("Number of Years: "), 
				tfYears, new Label("Annual Interest Rate: "), tfInterest, 
				new Label("Future Value"), tfFutureValue);
		vbox1.setAlignment(Pos.CENTER);
		
		
		hbox1.getChildren().addAll(new Label("Invesment Amount: "), tfInvestment, new Label("Number of Years: "), 
				tfYears, new Label("Annual Interest Rate: "), tfInterest, 
				new Label("Future Value"), tfFutureValue);
		hbox1.setAlignment(Pos.CENTER);

		VBox vBox = new VBox(8);
		vBox.getChildren().addAll(menuBar, hbox1);
		Scene scene = new Scene(vBox, 400, 300);
		primaryStage.setTitle("MenuDemo"); // Set the window title 
		primaryStage.setScene(scene); // Place the scene in the window 
		primaryStage.show(); // Display the window

		//Handle menu actions
		menuItemCalculate.setOnAction(e -> Calculate(investmentAmount, monthlyInterestRate, years));

	}
	
	public static void main(String[] args) {
		launch (args);
	}
	
	public void Calculate(double investmentAmount, double monthlyInterestRate, double years) {
		investmentAmount = Double.parseDouble(tfInvestment.getText());
		monthlyInterestRate = Double.parseDouble(tfInterest.getText());
		years = Double.parseDouble(tfYears.getText());
		futureValue = (investmentAmount * Math.pow((1 + monthlyInterestRate), (years * 12)));
		tfFutureValue.setText("" + futureValue);
		return;
	}
}
