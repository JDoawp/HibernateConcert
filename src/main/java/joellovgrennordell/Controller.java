package joellovgrennordell;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

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
    private List<concert> concertList = em.createQuery("SELECT c FROM concert AS c").getResultList();
    private ObservableList<String> concertObsList = FXCollections.observableArrayList();

    //People tab
    public TextField txtName;
    public DatePicker dateAge;
    public ListView<String> lvPeople;
    public ListView<String> lvAtConcerts;
    private List<person> personList = em.createQuery("SELECT a FROM person AS a").getResultList();
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



        updateListings();
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
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        try{
            et = em.getTransaction();
            et.begin();
            Date date = Date.valueOf(dateDate.getValue());
            concertList.add(new concert(concertList.size()+1, date, txtArtist.getText()));
            System.out.println("New Concert added: " +concertList.get(concertList.size()-1).toString());
            em.persist(concertList.get(concertList.size()-1));
            et.commit();
        }catch (Exception ex){
            if(et != null){
                et.rollback();
            }

            ex.printStackTrace();
        }finally {
            em.close();
            updateListings();
        }
    }

    public void btnAddPerson(){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        try{
            et = em.getTransaction();
            et.begin();
            Date age = Date.valueOf(dateAge.getValue());
            personList.add(new person(personList.size()+1, age, txtName.getText()));
            System.out.println("New person added: " +personList.get(personList.size()-1).toString());
            em.persist(personList.get(personList.size()-1));
            et.commit();
        }catch (Exception ex){
            if(et != null){
                et.rollback();
            }

            ex.printStackTrace();
        }finally {
            em.close();
            updateListings();
        }
    }

    private void updateListings(){
        concertObsList.clear();
        for (joellovgrennordell.concert concert : concertList) {
            concertObsList.add(concert.getArtist());
            System.out.println("Listing updated: " +concert.toString());
        }

        peopleObsList.clear();
        for (joellovgrennordell.person person : personList) {
            peopleObsList.add(person.getName());
            System.out.println("Listing updated: " +person.toString());
        }


        lvAtConcerts.setItems(concertObsList);
        lvConcerts.setItems(concertObsList);
        lvAttendees.setItems(peopleObsList);
        lvPeople.setItems(peopleObsList);
    }
}
