package joellovgrennordell;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Concert {
    private int id;
    private Date date;
    private String artist;
    private List<Integer> attendeIDs = new ArrayList<>();

    public Concert(int id, Date date, String artist, List<Integer> attendeIDs) {
        this.id = id;
        this.date = date;
        this.artist = artist;
        this.attendeIDs = attendeIDs;
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

    public List<Integer> getAttendeIDs(){
        return attendeIDs;
    }
}
