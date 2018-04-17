import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
public class Song {
    private StringProperty fileName = new SimpleStringProperty(this, "fileName", "");
    private StringProperty title = new SimpleStringProperty(this, "title", "");
    private StringProperty artistName = new SimpleStringProperty(this, "artistName", "");
    private StringProperty albumName = new SimpleStringProperty(this, "albumName", "");
    // public Song(String file, String title, String artist, String album ) {
    //  	this.fileName = file;
    //     this.title = title;
    //     this.artistName = artist;
    //     this.albumName = album;
    // }
    public Song() {

    }
    public String getFileName() {
     	return this.fileName.get();
    }
    public void setFileName(String fileName) {
        this.fileName.set(fileName);
    }
    public void setTitle(String title) {
        this.title.set(title);
    }
    public String getTitle() {
        return this.title.get();
    }
    public void setArtistName(String artist) {
        this.artistName.set(artist);
    }
    public String getArtistName() {
        return this.artistName.get();
    }
    public void setAlbumName(String albumName) {
        this.albumName.set(albumName);
    }
    public String getAlbumName() {
        return this.albumName.getValueSafe();
    }
    // public String toString() {
    // 	return "FileName: " + getFileName() + ", Title: " + getTitle() + ", Artist: " + getArtistName() + "Album: " + getAlbumName();
    // }
}
