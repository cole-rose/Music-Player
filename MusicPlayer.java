import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.control.TableView ;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.Group;
import java.net.URL;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableRow;
import java.util.List;
import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.beans.property.StringProperty;
import javafx.scene.control.cell.PropertyValueFactory;
public class MusicPlayer extends Application {
	private static final String MEDIA_URL =
 		"http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv";
 		final URL resource = getClass().getResource("BossaBossa.mp3");
 		private TableView table = new TableView();
	@Override 
	public void start(Stage primaryStage) {
		ObservableList<Song> songList = FXCollections.observableArrayList();
		songList.add(new Song("BossaBossa"));
		songList.add(new Song ("BitterSweet"));
		TableColumn<Song,String> songCol= new TableColumn<>("Song Name");
		//songCol.setCellValueFactory(songList);
		songCol.setCellValueFactory(new PropertyValueFactory<>("songName"));
		table.setItems(songList);
		table.getColumns().addAll(songCol);


		primaryStage.setTitle("Cole's Media Player");
        Group root = new Group();
        Scene scene = new Scene(root, 540, 210);
		Media media = new Media(resource.toString());
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		Button playButton = new Button();
		playButton.setText("Play");
		mediaPlayer.setAutoPlay(true);
		// create mediaView and add media player to the viewer
		MediaView mediaView = new MediaView(mediaPlayer);
		((Group) scene.getRoot()).getChildren().addAll(mediaView,table);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();

	}
}

