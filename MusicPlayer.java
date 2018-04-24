import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.util.List;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.stream.Collectors;
import java.io.File;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
/**
 * Music Player class to make a music player
 * @author crose37
 * @version  4.20
 */
public class MusicPlayer extends Application {
    private TableView table = new TableView();
    /**
     * class to represent songs
     */
    public class Song {
        private Media media;
        private StringProperty fileName = new SimpleStringProperty(
                this, "fileName", "");
        private StringProperty title = new SimpleStringProperty(
            this, "title", "");
        private StringProperty artistName = new SimpleStringProperty(
                this, "artistName", "");
        private StringProperty albumName = new SimpleStringProperty(
                this, "albumName", "");
        /**
         * setter method for media obj
         * @param media media obj
         */
        public void setMedia(Media media) {
            this.media = media;
        }
        /**
         * getter method for media obj
         * @return media object
         */
        public Media getMedia() {
            return this.media;
        }
        /**
         * getter method for filename
         * @return name of file
         */
        public String getFileName() {
            return this.fileName.get();
        }
        /**
         * setter for filename without path
         * @param fileName full filename path
         */
        public void setFileName(String fileName) {
            this.fileName.set(fileName.substring(fileName.lastIndexOf('/')
                + 1).replace("%20", " "));
        }
        /**
         * setter for title of song
         * @param title title of song
         */
        public void setTitle(String title) {
            this.title.set(title);
        }
        /**
         * getter method for obj title
         * @return song title
         */
        public String getTitle() {
            return this.title.getValueSafe();
        }
        /**
         * setter for artist's name
         * @param artist artist
         */
        public void setArtistName(String artist) {
            this.artistName.set(artist);
        }
        /**
         * getter method for artists name
         * @return arist's name
         */
        public String getArtistName() {
            return this.artistName.getValueSafe();
        }
        /**
         * setter method for album name
         * @param albumName album name
         */
        public void setAlbumName(String albumName) {
            this.albumName.set(albumName);
        }
        /**
         * getter method for album name
         * @return album's name
         */
        public String getAlbumName() {
            return this.albumName.getValueSafe();
        }
    }
    /**
     * start method for music player
     * @param  primaryStage the stage of the player
     * @throws IOException  exception
     */
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
        for (Media med : mediaObjects) {
            Song t = new Song();
            t.setMedia(med);
            songList.add(t);
        }
        int i = 0;
        for (Song s : songList) {
            Song n = songList.get(i++);
            n.getMedia().getMetadata().addListener(
                new MapChangeListener<String, Object>() {
                    public void onChanged(
                            MapChangeListener.
                                Change<? extends String, ? extends Object> c) {
                        String key = c.getKey();
                        String value = c.getValueAdded().toString();
                        if (key.equals("title")) {
                            n.setTitle(value);
                        } else if (key.equals("artist")) {
                            n.setArtistName(value);
                        } else if (key.equals("album")) {
                            n.setAlbumName(value);
                        }
                        n.setFileName(n.getMedia().getSource());
                        table.refresh();
                    } });
        }
        int w = 300;
        TableColumn<Song, String> fileCol = new TableColumn<>("File Name");
        fileCol.setMinWidth(w);
        fileCol.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        TableColumn<Song, String> attributes = new TableColumn<>("Attributes");
        attributes.setMinWidth(500);
        TableColumn<Song, String> artistCol = new TableColumn<>("Artist");
        artistCol.setMinWidth(w);
        artistCol.setCellValueFactory(new PropertyValueFactory<>("artistName"));
        TableColumn<Song, String> songCol = new TableColumn<>("Title");
        songCol.setMinWidth(w);
        songCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<Song, String> albumCol = new TableColumn<>("Album");
        albumCol.setCellValueFactory(new PropertyValueFactory<>("albumName"));
        albumCol.setMinWidth(w);
        table.setItems(songList);
        attributes.getColumns().addAll(artistCol, songCol, albumCol);
        table.getColumns().addAll(fileCol, attributes);
        primaryStage.setTitle("Cole's Music Player");
        MediaPlayer mediaPlayer;
        Button playButton = new Button("Play");
        Button pauseButton = new Button("Pause");
        playButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                //table has get selection model and
                // then you can call getselected item
                //should return what song is being
                // selected and from that song you can get the media
                //media player equals new MediaPlayer(selectedmediafromsong)
                //look at mediaplayer class in java api. .play
                MediaPlayer mediaPlayer = new MediaPlayer(
                    ((Song) table.getSelectionModel().
                    getSelectedItems().get(0)).getMedia());
                mediaPlayer.play();
                playButton.setDisable(true);
                pauseButton.setDisable(false);
                pauseButton.setOnAction(action -> {
                        mediaPlayer.pause();
                        pauseButton.setDisable(true);
                        playButton.setDisable(false);
                    });
            }
        });
        HBox hbox = new HBox();
        hbox.getChildren().addAll(playButton, pauseButton);
        VBox vbox = new VBox();
        vbox.getChildren().addAll(table, hbox);
        table.refresh();
        Scene scene = new Scene(vbox, 1200, 400);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }
}