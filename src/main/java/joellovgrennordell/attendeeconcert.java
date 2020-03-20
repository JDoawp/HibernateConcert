package joellovgrennordell;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "attendeeconcert")
public class attendeeconcert implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id

    @Column(name = "attendeeID")
    private int personID;

    @Column(name = "concertID")
    private int concertID;

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int attendeeID) {
        this.personID = attendeeID;
    }

    public int getConcertID() {
        return concertID;
    }

    public void setConcertID(int concertID) {
        this.concertID = concertID;
    }
}
