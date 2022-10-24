package uet.oop.bomberman;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ScreenController implements Initializable {
    private Stage stage;
    private ArrayList<File> songs;
    private File directory;
    private File[] files;
    private Media media;
    private static MediaPlayer mediaPlayer;
    private int songnumber;
    @FXML
    private Button button;
    private Scene scene;
    private Parent root;
    @FXML
    private Button logoutbutton;
    @FXML
    private Button startmusic;
    @FXML
    private AnchorPane scenePane;

    public void logout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("You're about to exit");
        alert.setContentText("Do you want to exit?");
        alert.getDialogPane().getStylesheets().add(this.getClass().getClassLoader().getResource("ui/alert.css").toExternalForm());
        alert.getDialogPane().getStyleClass().add("alert");
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("icon.png"));
        if (alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
        //stage.close();
    }

   public void playMusic() {
        if (startmusic.getText().equals("MUSIC : OFF")) {
            startmusic.setText("MUSIC : ON");
            mediaPlayer.play();
            return;
        }
        startmusic.setText("MUSIC : OFF");
        mediaPlayer.pause();
    }


    //   public void pausemusic() {
    //  mediaPlayer.pause();
    // }


    public void onClick(ActionEvent event) {
        try {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        stage.setScene(BombermanGame.Gamescene);
        BombermanGame.setSceneToShow(1);
        //stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button.setOnAction(this::onClick);
        ImageView background = new ImageView();
        // READ MEDIA FILE
        songs = new ArrayList<File>();
        directory = new File("res/Music");
        files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                songs.add(file);
            }
        }
        media = new Media(songs.get(songnumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    public static void pauseMusic() {
        mediaPlayer.pause();
    }
}
