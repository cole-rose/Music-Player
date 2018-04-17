import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
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
import javafx.collections.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.beans.property.StringProperty;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.stream.Collectors;
import java.io.File;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import java.util.Set;
import java.util.HashSet;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
public class MusicPlayer extends Application {
	private static final String MEDIA_URL =
 		"http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv";
 		final URL resource = getClass().getResource("BossaBossa.mp3");
 		private TableView table = new TableView();	
	@Override 
	public void start(Stage primaryStage) throws IOException {
		File curDirectory = new File(".");
		List<String> fileNames = Files.walk(Paths.get(""))
			.map(b -> b.toString()).filter(a -> a.contains(".mp3"))
			.collect(Collectors.toList());
		List<URL> filePaths = fileNames.stream()
			.map(z -> getClass().getResource(z))
			.collect(Collectors.toList());
		List<Media> mediaObjects = filePaths.stream().map(q -> q.toString())
			.map(m -> new Media(m)).collect(Collectors.toList());
		ObservableList<Song> songList = FXCollections.observableArrayList();
		for(int i = 0; i < mediaObjects.size(); i++) {
			String fileName = fileNames.get(i);
			Media mediaObject = mediaObjects.get(i);
			// String songName = mediaObject.getMetadata().get("title").toString();
			// String artistName = mediaObject.getMetadata().get("artist").toString();
			// String albumName = mediaObject.getMetadata().get("album").toString();
		}
		Media media1 = mediaObjects.get(0); 
		for (Media med : mediaObjects) {
			Song s = new Song();
			med.getMetadata().addListener(new MapChangeListener<String,Object>() {
			public void onChanged(MapChangeListener.Change<? extends String,? extends Object> c) {
			String key = c.getKey();
			String value = c.getValueAdded().toString();
			if (key.equals("title")) {
				s.setTitle(value);
			} else if (key.equals("artist")) {
				s.setArtistName(value);
			} else if(key.equals("album")) {
				s.setAlbumName(value);
			}
			s.setTitle(value);
			s.setFileName(med.getSource());
			
				}

		}
		);
			ObservableMap<String,Object> meta = med.getMetadata();
			s.setTitle(meta.get("title").toString());
			songList.add(s);
			
			
	}
		//songList.add(new Song("BossaBossa"));
		//songList.add(new Song ("BitterSweet"));
		TableColumn<Song,String> fileCol = new TableColumn<>("File Name");
		fileCol.setCellValueFactory(new PropertyValueFactory<>("fileName"));
		TableColumn<Song,String> artistCol = new TableColumn<>("Artist");
		artistCol.setCellValueFactory(new PropertyValueFactory<>("artistName"));
		TableColumn<Song,String> songCol = new TableColumn<>("Title");
		songCol.setCellValueFactory(new PropertyValueFactory<>("title"));
		TableColumn<Song,String> albumCol = new TableColumn<>("Album");
		albumCol.setCellValueFactory(new PropertyValueFactory<>("albumName"));
		table.setItems(songList);
		table.getColumns().addAll(fileCol,artistCol,songCol,albumCol);


		primaryStage.setTitle("Cole's Media Player");
        Group root = new Group();
        //Scene scene = new Scene(root, 540, 210);
		Media media = new Media(resource.toString());
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		Button playButton = new Button();
		playButton.setText("Play");
		playButton.setOnAction(new EventHandler<ActionEvent>() {
    public void handle(ActionEvent e) {
        }
    });
		Button pauseButton = new Button("Pause");
		mediaPlayer.setAutoPlay(true);
		// create mediaView and add media player to the viewer
		MediaView mediaView = new MediaView(mediaPlayer);
		HBox hbox = new HBox();
		hbox.getChildren().addAll(playButton, pauseButton);
		VBox vbox = new VBox();
		vbox.getChildren().addAll(mediaView,table,hbox);
		//((Group) scene.getRoot()).getChildren().addAll(mediaView,table,vbox);
		Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();

	}
}
