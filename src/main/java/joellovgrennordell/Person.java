package joellovgrennordell;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Person {
    private int id;
    private Date age;
    private String name;
    private List<Integer> concertIDs = new ArrayList<>();

    public Person(int id, Date age, String name) {
        this.id = id;
        this.age = age;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public Date getAge() {
        return age;
    }

    public String getName() {
        return name;
    }
}
