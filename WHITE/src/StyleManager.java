import javafx.scene.Scene;

public class StyleManager {
    public static void applyInternalCSS(Scene scene) {
        scene.getRoot().setStyle(
                "-fx-background-color: #f0f0f0;" +
                "-fx-font-family: 'cascadia code', algerian;"
        );
    }
}
