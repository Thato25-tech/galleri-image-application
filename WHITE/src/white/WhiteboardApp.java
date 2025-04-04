import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class WhiteboardApp extends Application {
    private CanvasManager canvasManager;
    private ToolbarManager toolbarManager;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        Canvas canvas = new Canvas(800, 600);
        canvasManager = new CanvasManager(canvas);
        toolbarManager = new ToolbarManager(canvasManager);
        
        root.setCenter(canvas);
        root.setLeft(toolbarManager.createToolbar());
        
        Scene scene = new Scene(root, 1000, 700);
        StyleManager.applyInternalCSS(scene);
        
        primaryStage.setTitle("Interactive Digital Whiteboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
