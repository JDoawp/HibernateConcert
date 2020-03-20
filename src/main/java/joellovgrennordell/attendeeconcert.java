package joellovgrennordell;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "attendeeconcert")
public class attendeeconcert implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id

    @Column(name = "attendeeID")
    private int attendeeID;

    @Column(name = "concertID")
    private int concertID;

    public int getAttendeeID() {
        return attendeeID;
    }

    public void setAttendeeID(int attendeeID) {
        this.attendeeID = attendeeID;
    }

    public int getConcertID() {
        return concertID;
    }

    public void setConcertID(int concertID) {
        this.concertID = concertID;
    }
}
