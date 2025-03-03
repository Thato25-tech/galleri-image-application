/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package internetgallery;

import java.awt.Color;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.animation.FadeTransition;
import java.io.File;
import java.net.URL;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javax.swing.UIManager;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.transform.Rotate;

/**
 *
 * @author SIMON
 */
public class InternetGallery extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        // Create the gallery view
        GridPane gridPane = new GridPane();
        gridPane.setId("internetGallery");
        gridPane.getStyleClass().add("gridPane");
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(30);
        gridPane.setVgap(30);
        
         //setting background color for the gallery
        gridPane.setStyle("-fx-background-color:lightpink");
               
        // Wrap the GridPane in a ScrollPane
         
        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setContent(gridPane);
        scrollPane.getStyleClass().add("scrollPane");
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        StakePane root=new StakePane();
        UIManager.put("ScrollPane.background", Color.LIGHT_GRAY);
        
        // Inline style for ScrollPane
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        
        // Load images from "src/images"
        File folder = new File("src/image");
        File[] files = folder.listFiles();
        List<ImageView> thumbnails = new ArrayList<>();

        if (files != null) {
            int col = 0, row = 0;
            for (File file : files) {
                if (file.isFile()) {
                    ImageView thumbnail = createThumbnail(file.toURI().toString());
                    thumbnail.setOnMouseClicked(e -> showFullImage(file.toURI().toString()));

                    gridPane.add(thumbnail, col, row);
                    col++;
                    if (col == 4) { col = 0; row++; }
                }
            }
        }
        
       // Set up the main scene
       // BorderPane root = new BorderPane();
       // root.setCenter(scrollPane);
        Scene scene = new Scene(scrollPane, 800, 600);
        
        scene.getStylesheets().add(getClass().getResource("MyCss.css").toExternalForm());
        URL cssURL=getClass().getResource("MyCss.css");
        System.out.println("CSS File URL:" + cssURL);
        scene.getStylesheets().add(cssURL.toExternalForm());
        
        //Set up the root layout
        //BorderPane root = new BorderPane();
       //root.setCenter(scrollPane);

        // Inline style for the root BorderPane

        primaryStage.setTitle("Image Gallery");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Create a thumbnail with fixed size
    private ImageView createThumbnail(String imagePath) {
        ImageView imageView = new ImageView(new Image(imagePath));
        imageView.setFitWidth(150);
        imageView.setFitHeight(100);
        return imageView;
           
    }

    
    // Show full-size image in a new window
    private void showFullImage(String imagePath) {
        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("borderPane");
        ImageView imageView = new ImageView(new Image(imagePath));
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(500);
        imageView.setFitWidth(300);
        
        Button rotateButton=new Button("rotate image");
        Rotate rotate=new Rotate();
        imageView.getTransforms().add(rotate);
        
        //creating buttons
        Button backButton = new Button("back"); 
        backButton.setId("backButton");
        backButton.getStyleClass().add("backButton");
        
        FadeTransition fadeTransition= new FadeTransition(Duration.seconds(2), backButton);
        fadeTransition.setFromValue(2);
        fadeTransition.setToValue(4);
        fadeTransition.setCycleCount(2);
        fadeTransition.setAutoReverse(true);
        
        backButton.setOnAction(event->fadeTransition.play());
        
        
        
        StackPane root=new StackPane();
        root.getChildren().add(backButton);
       
        VBox vBox= new VBox(backButton);
        vBox.setAlignment(Pos.CENTER);
        
        backButton.setOnAction(e -> ((Stage) backButton.getScene().getWindow()).close());
        
    
        HBox controls = new HBox(10, backButton);
        controls.setPadding(new Insets(10));

        borderPane.setCenter(imageView);
        borderPane.setBottom(controls);

        Stage fullImageStage = new Stage();
        fullImageStage.setScene(new Scene(borderPane, 800, 600));
        fullImageStage.show();
    }
        
        private void animateImageTranslation(double TranslateTransition, Node imageView){
            TranslateTransition translateTransition= new TranslateTransition(Duration.seconds(3), imageView);
            translateTransition.setOnFinished(event-> {
            });
            
        
        
    }

    public static void main(String[] args) {
        launch();
    }
}
