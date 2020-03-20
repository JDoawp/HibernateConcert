package joellovgrennordell;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "concert")
public class concert implements Serializable{
    private static final long serialVersionUID = 1L;

    public concert(int id, Date date, String artist) {
        this.id = id;
        this.date = date;
        this.artist = artist;
    }

    public concert() {
    }

    @Id
    @Column(name = "idconcert", unique = true, nullable = false)
    private int id;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "artist", nullable = false)
    private String artist;


    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getArtist() {
        return artist;
    }

    @Override
    public String toString() {
        return "concert{" +
                "id=" + id +
                ", date=" + date +
                ", artist='" + artist + '\'' +
                '}';
    }
}
