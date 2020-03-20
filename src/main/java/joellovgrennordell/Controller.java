package joellovgrennordell;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import javax.persistence.*;

public class Controller {
    //TODO
    // On Attendee/AtConcert list change send equivalent update to Hibernate

    //General stuff
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("HibernateConcert");
    EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
    List<attendeeconcert> attendeeconcertList = em.createQuery("SELECT a FROM attendeeconcert AS a").getResultList();

    //Concert tab
    public TextField txtArtist;
    public DatePicker dateDate;
    public ListView<String> lvConcerts;
    public ListView<String> lvAttendees;
    private List<Concert> concertList = new ArrayList<>();
    private ObservableList<String> concertObsList = FXCollections.observableArrayList();

    //People tab
    public TextField txtName;
    public DatePicker dateAge;
    public ListView<String> lvPeople;
    public ListView<String> lvAtConcerts;
    private List<Person> personList = new ArrayList<>();
    private ObservableList<String> peopleObsList = FXCollections.observableArrayList();

    public void initialize(){
        lvAtConcerts.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lvAttendees.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listViewHandler(lvAtConcerts);
        listViewHandler(lvAttendees);


        lvPeople.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            //highlightConcerts(lvPeople.getSelectionModel().getSelectedIndex());
        });
        lvConcerts.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            //highlightAttendees(lvConcerts.getSelectionModel().getSelectedIndex());
        });

        for (joellovgrennordell.attendeeconcert attendeeconcert : attendeeconcertList) {
            System.out.println(attendeeconcert.getAttendeeID()  +" " + attendeeconcert.getConcertID());
        }

        lvAtConcerts.setItems(concertObsList);
        lvConcerts.setItems(concertObsList);
        lvAttendees.setItems(peopleObsList);
        lvPeople.setItems(peopleObsList);
    }

    //Adds an eventHandler to a given listview so each item will act more like a checkbox / toggle button
    //Graciously provided by fabian from stackoverflow @ https://stackoverflow.com/a/40906993
    private void listViewHandler(ListView<String> listview) {
        listview.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            Node node = event.getPickResult().getIntersectedNode();

            //Go up from the target node until a list cell is found or it's clear it was not a cell that was clicked
            while(node != null && node != lvAttendees && !(node instanceof ListCell)){
                node = node.getParent();
            }

            //If node is part of a cell or is the cell handle the event instead of using standard handling
            if(node instanceof ListCell){
                event.consume();

                ListCell<String> cell = (ListCell) node;
                ListView<String> lv = cell.getListView();

                //Focus the listview
                lv.requestFocus();

                if(!cell.isEmpty()){
                    //Handle selection of non-empty cells
                    int index = cell.getIndex();
                    if(cell.isSelected()){
                        lv.getSelectionModel().clearSelection(index);
                    }else{
                        lv.getSelectionModel().select(index);
                    }
                }
            }
        });
    }

    /*
    private void highlightAttendees(int index){
        for(int i = 0; i < concertList.get(index).getAttendeIDs().size(); i++){
            lvAttendees.getSelectionModel().select(concertList.get(index).getAttendeIDs().get(i));
        }
    }

    private void highlightConcerts(int index){
        for(int i = 0; i < personList.get(index).getConcertIDs().size(); i++){
            lvPeople.getSelectionModel().select(personList.get(index).getConcertIDs().get(i));
        }
    }
    */


    public void btnAddConcert() {
        int id = concertList.size();
        Date date = Date.valueOf(dateDate.getValue());

        concertList.add(new Concert(id, date, txtArtist.getText()));

        concertObsList.add(concertList.get(id).getArtist());
        lvAtConcerts.setItems(concertObsList);
        lvConcerts.setItems(concertObsList);
    }

    public void btnAddPerson(){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        try{
            et = em.getTransaction();
            et.begin();
            Date age = Date.valueOf(dateAge.getValue());
            Person person = new Person(personList.size(), age, txtName.getText());
            em.persist(person);
            et.commit();
        }catch (Exception ex){
            if(et != null){
                et.rollback();
            }

            ex.printStackTrace();
        }finally {
            em.close();
        }



        int id = personList.size();
        Date age = Date.valueOf(dateAge.getValue());

        personList.add(new Person(id, age, txtName.getText()));

        peopleObsList.add(personList.get(id).getName());
        lvAttendees.setItems(peopleObsList);
        lvPeople.setItems(peopleObsList);
    }
}
