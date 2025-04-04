import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class CanvasManager {
    private Canvas canvas;
    private GraphicsContext gc;
    private double prevX, prevY;
    private Color brushColor = Color.BLACK;
    private double brushWidth = 2.0;
    private List<Object> sessionData = new ArrayList<>();

    public CanvasManager(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(brushWidth);
        gc.setStroke(brushColor);
        setupCanvasEvents();
    }

    private void setupCanvasEvents() {
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            prevX = event.getX();
            prevY = event.getY();
        });

        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            double x = event.getX();
            double y = event.getY();
            gc.strokeLine(prevX, prevY, x, y);
            sessionData.add(new double[]{prevX, prevY, x, y});
            prevX = x;
            prevY = y;
        });
    }

    public void setBrushColor(Color color) {
        this.brushColor = color;
        gc.setStroke(color);
    }

    public void setBrushWidth(double width) {
        this.brushWidth = width;
        gc.setLineWidth(width);
    }

    public void clearCanvas() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        sessionData.clear();
    }

    public void addText(String text, double x, double y) {
        gc.setFont(new Font(20));
        gc.fillText(text, x, y);
        sessionData.add("TEXT:" + text + "," + x + "," + y);
    }

    public void addImage(File file, double x, double y) {
        try {
            Image image = new Image(new FileInputStream(file));
            gc.drawImage(image, x, y);
            sessionData.add("IMAGE:" + file.getAbsolutePath() + "," + x + "," + y);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addAudio(File file) {
        Media media = new Media(file.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        sessionData.add("AUDIO:" + file.getAbsolutePath());
    }

    public void addVideo(File file) {
        Media media = new Media(file.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        sessionData.add("VIDEO:" + file.getAbsolutePath());
    }

    public void saveSession(File file) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(sessionData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
