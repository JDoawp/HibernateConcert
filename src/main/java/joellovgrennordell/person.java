package joellovgrennordell;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "person")
public class person implements Serializable{
    private static final long serialVersionUID = 1L;

    public person(int id, Date age, String name) {
        this.id = id;
        this.age = age;
        this.name = name;
    }

    public person(){

    }

    @Id
    @Column(name = "idperson", unique = true, nullable = false)
    private int id;

    @Column(name = "age", nullable = false)
    private Date age;

    @Column(name = "name", nullable = false)
    private String name;


    public int getId() {
        return id;
    }

    public Date getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "person{" +
                "id=" + id +
                ", age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
