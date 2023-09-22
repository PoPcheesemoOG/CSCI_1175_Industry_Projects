import java.io.File;
import javafx.scene.image.Image;
import javafx.animation.PathTransition; 
import javafx.application.Application; 
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FlagRisingAnimation extends Application {
	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) {
		// Create a pane
		Pane pane = new Pane();

		// Add an image view and add it to pane
		//ImageView imageView = new ImageView("/Users/student/eclipse-workspace/f/src/image/us.gif");
		
		File file = new File("/Users/student/eclipse-workspace/f/src/image/us.gif");
		Image image = new Image(file.toURI().toString());
		ImageView imageView = new ImageView(image);
		
		pane.getChildren().add(imageView);

		// Create a path transition
		Thread thread = new Thread( (new Runnable () {
			@Override
			public void run() {
				try {
						PathTransition pt = new PathTransition(Duration.millis(10000),
							new Line(100, 200, 100, 0), imageView); pt.setCycleCount(5);
							pt.play(); // Start animation
				}
				catch (Exception ex) {
				}				
			}
		}));
		thread.start();

		// Create a scene and place it in the stage
		Scene scene = new Scene(pane, 250, 200); 
		primaryStage.setTitle("FlagRisingAnimation"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}