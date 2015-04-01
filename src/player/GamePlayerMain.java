package player;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GamePlayerMain extends Application {

    @Override
    public void start (Stage stage) throws Exception {
    	GamePlayer gp = new GamePlayer(stage);
        stage.show();
    }

    public static void main (String[] args) {
        launch(args);
    }
}