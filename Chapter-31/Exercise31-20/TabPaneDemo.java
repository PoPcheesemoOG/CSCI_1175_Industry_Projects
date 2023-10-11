/* Name: Paul Helske
 * Date: 10/10/23
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class TabPaneDemo extends Application {   
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {   
    TabPane tabPane = new TabPane();
    Tab tab1 = new Tab("Line");
    StackPane pane1 = new StackPane();
    pane1.getChildren().add(new Line(10, 10, 80, 80));
    tab1.setContent(pane1);
    Tab tab2 = new Tab("Rectangle");
    tab2.setContent(new Rectangle(10, 10, 200, 200));
    Tab tab3 = new Tab("Circle");
    tab3.setContent(new Circle(50, 50, 20));    
    Tab tab4 = new Tab("Ellipse");
    tab4.setContent(new Ellipse(10, 10, 100, 80));
    tabPane.getTabs().addAll(tab1, tab2, tab3, tab4);
    
    VBox vbox = new VBox();
    HBox hbox = new HBox();
    
    RadioButton rbTop = new RadioButton("Top");
    RadioButton rbBottom = new RadioButton("Bottom");
    RadioButton rbLeft = new RadioButton("Left");
    RadioButton rbRight = new RadioButton("Right");
    
    ToggleGroup tGroup = new ToggleGroup();
    rbTop.setToggleGroup(tGroup);
    rbBottom.setToggleGroup(tGroup);
    rbLeft.setToggleGroup(tGroup);
    rbRight.setToggleGroup(tGroup);
    
    rbTop.setPrefWidth(150);
    rbBottom.setPrefWidth(150);
    rbLeft.setPrefWidth(150);
    rbRight.setPrefWidth(150);
    
    rbTop.setOnAction(e -> {
    	tabPane.setRotate(0);
    });
    rbBottom.setOnAction(e -> {
    	tabPane.setRotate(180);
    });
    rbLeft.setOnAction(e -> {
    	tabPane.setRotate(270);
    });
    rbRight.setOnAction(e -> {
    	tabPane.setRotate(90);
    });
    
    hbox.getChildren().addAll(rbTop, rbBottom, rbLeft, rbRight);  
    vbox.getChildren().addAll(tabPane, hbox);
    
    Scene scene = new Scene(vbox, 300, 250);  
    primaryStage.setTitle("DisplayFigure"); // Set the window title
    primaryStage.setScene(scene); // Place the scene in the window
    primaryStage.show(); // Display the window
  }

  public static void main(String[] args) {
    launch(args);
  }
}

