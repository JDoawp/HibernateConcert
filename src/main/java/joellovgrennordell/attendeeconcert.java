package joellovgrennordell;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "attendeeconcert")
public class attendeeconcert implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id

    @Column(name = "personID")
    private int personID;

    @Column(name = "concertID")
    private int concertID;

    public attendeeconcert() {
    }

    public attendeeconcert(int personID, int concertID) {
        this.personID = personID;
        this.concertID = concertID;
    }

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

    @Override
    public String toString() {
        return "attendeeconcert{" +
                "personID=" + personID +
                ", concertID=" + concertID +
                '}';
    }
}
