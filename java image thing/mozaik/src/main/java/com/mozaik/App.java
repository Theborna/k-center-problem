package com.mozaik;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.opencsv.CSVReader;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
        String name = "asuka2";
        Image image = new Image(App.class.getResource("image/" + name + ".jpg").toExternalForm());
        ImageParser imageParser = new ImageParser(image);
        imageParser.writeToCSV(new File(name + ".csv"));
        JSONParser parser = new JSONParser();
        JSONArray colors = null;
        try {
            colors = (JSONArray) parser
                    .parse(new FileReader(new File("centers_" + name + ".json")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ImageMaker maker = new ImageMaker(image, colors);
        maker.createImage(name);
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}