package joellovgrennordell;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Concert {
    private int id;
    private Date date;
    private String artist;

    public Concert(int id, Date date, String artist) {
        this.id = id;
        this.date = date;
        this.artist = artist;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getArtist() {
        return artist;
    }
}
