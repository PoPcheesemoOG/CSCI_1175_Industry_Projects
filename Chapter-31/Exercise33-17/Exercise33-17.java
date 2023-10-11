/* Name: Paul Helske
 * Date: 10/10/23
 */

import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.geometry.*;
import javafx.stage.*;
import javafx.application.*;
import javafx.scene.*;

public class Exercise31_17 extends Application {
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

		tfInvestment.setPrefColumnCount(14);
		tfYears.setPrefColumnCount(14);
		tfInterest.setPrefColumnCount(14);
		tfFutureValue.setPrefColumnCount(14);
		
		tfInvestment.setAlignment(Pos.CENTER_RIGHT);
		tfYears.setAlignment(Pos.CENTER_RIGHT);
		tfInterest.setAlignment(Pos.CENTER_RIGHT);
		tfFutureValue.setAlignment(Pos.CENTER_RIGHT);
		
		Button btCalc = new Button("Calculate");
		btCalc.setAlignment(Pos.BOTTOM_RIGHT);
		
		GridPane gridPane = new GridPane();
		gridPane.addColumn(0, new Label("Invesment Amount: "), new Label("Number of Years: "), 
				new Label("Annual Interest Rate: "), new Label("Future Value: "));
		gridPane.addColumn(1, tfInvestment, tfYears, tfInterest, tfFutureValue, btCalc);
		
		VBox vBox = new VBox(10);
		vBox.getChildren().addAll(menuBar, gridPane);
		
		Scene scene = new Scene(vBox, 400, 300);
		primaryStage.setTitle("Investment Calculator"); // Set the window title 
		primaryStage.setScene(scene); // Place the scene in the window 
		primaryStage.show(); // Display the window

		//Handle menu actions
		scene.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				Calculate(investmentAmount, monthlyInterestRate, years);
			}
		});
		menuItemCalculate.setOnAction(e -> Calculate(investmentAmount, monthlyInterestRate, years));
		btCalc.setOnAction(e -> Calculate(investmentAmount, monthlyInterestRate, years));
		menuItemExit.setOnAction(e -> System.exit(0));
	}
	
	public static void main(String[] args) {
		launch (args);
	}	
	public void Calculate(double investmentAmount, double monthlyInterestRate, double years) {
		investmentAmount = Double.parseDouble(tfInvestment.getText());
		monthlyInterestRate = Double.parseDouble(tfInterest.getText()) / 1200;
		years = Double.parseDouble(tfYears.getText());

		futureValue = (investmentAmount * (Math.pow((1 + monthlyInterestRate), (years * 12))));
		new String();
		tfFutureValue.setText(String.format("$%.2f", futureValue));
		return;
	}
}
