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

public class Controller {
    //General stuff
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

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

        });


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

    private void showAttendees(){

    }


    public void btnAddConcert() {
        int id = concertList.size();
        Date date = Date.valueOf(dateDate.getValue());
        List<Integer> selectedWhatever = new ArrayList<>(lvAttendees.getSelectionModel().getSelectedIndices());

        concertList.add(new Concert(id, date, txtArtist.getText(), selectedWhatever));

        concertObsList.add(concertList.get(id).getArtist());
        lvAtConcerts.setItems(concertObsList);
        lvConcerts.setItems(concertObsList);
    }

    public void btnAddPerson(){
        int id = personList.size();
        Date age = Date.valueOf(dateAge.getValue());

        personList.add(new Person(id, age, txtName.getText()));

        peopleObsList.add(personList.get(id).getName());
        lvAttendees.setItems(peopleObsList);
        lvPeople.setItems(peopleObsList);
    }
}
