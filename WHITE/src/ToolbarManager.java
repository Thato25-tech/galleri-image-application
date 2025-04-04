import java.io.File;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.geometry.Insets;
import javafx.scene.Node;

public class ToolbarManager {
    private CanvasManager canvasManager;

    public ToolbarManager(CanvasManager canvasManager) {
        this.canvasManager = canvasManager;
    }

    public VBox createToolbar() {
        VBox toolbar = new VBox(10);
        toolbar.setPadding(new Insets(10));
        toolbar.setStyle("-fx-background-color: black;");

        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(Color.BLACK);
        colorPicker.setOnAction(event -> canvasManager.setBrushColor(colorPicker.getValue()));

        Slider brushWidthSlider = new Slider(1, 10, 2);
        brushWidthSlider.setBlockIncrement(1);
        brushWidthSlider.valueProperty().addListener((obs, oldValue, newValue) -> 
            canvasManager.setBrushWidth(newValue.doubleValue()));

        Button clearButton = createStyledButton("Clear Canvas");
        clearButton.setOnAction(event -> canvasManager.clearCanvas());

        Button addTextButton = createStyledButton("Add Text");
        addTextButton.setOnAction(event -> {
            TextInputDialog dialog = new TextInputDialog("Enter text");
            dialog.setHeaderText(null);
            dialog.setContentText("Text:");
            dialog.showAndWait().ifPresent(text -> canvasManager.addText(text, 100, 100));
        });

        Button addImageButton = createStyledButton("Add Image");
        addImageButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
            File file = fileChooser.showOpenDialog(null);
            if (file != null) canvasManager.addImage(file, 100, 100);
        });

        Button addAudioButton = createStyledButton("Add Audio");
        addAudioButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Audio Files", "*.mp3", "*.wav"));
            File file = fileChooser.showOpenDialog(null);
            if (file != null) canvasManager.addAudio(file);
        });

        Button addVideoButton = createStyledButton("Add Video");
        addVideoButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.avi"));
            File file = fileChooser.showOpenDialog(null);
            if (file != null) canvasManager.addVideo(file);
        });

        Button saveSessionButton = createStyledButton("Save Session");
        saveSessionButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Files", "*.png", "*.jpeg"));
            File file = fileChooser.showSaveDialog(null);
            if (file != null) canvasManager.saveSession(file);
        });

        toolbar.getChildren().addAll(
                new Label("Brush Color:"), colorPicker,
                new Label("Brush Width:"), brushWidthSlider,
                clearButton, addTextButton, addImageButton, addAudioButton, addVideoButton, saveSessionButton
        );

        return toolbar;
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px;");
        return button;
    }
}
