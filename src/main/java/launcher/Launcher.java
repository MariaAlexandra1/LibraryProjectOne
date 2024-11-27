package launcher;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Launcher extends Application {
    private Stage window;
    private Scene sceneOne, sceneTwo;

    public static void main(String[] args){
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        EmployeeComponentFactory.getInstance(false, primaryStage);
    }
}
