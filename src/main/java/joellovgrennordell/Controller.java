package joellovgrennordell;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

public class Controller {
    //Concert tab
    public ListView<String> lvConcerts;
    public TextField txtArtist;
    public DatePicker dateDate;
    public ListView<String> lvSelectedPeople;
    public Button btnAddConcert;

    //People tab
    public ListView<String> lvPeople;
    public TextField txtName;
    public DatePicker dateAge;
    public ListView<String> lvSelectedConcerts;
    public Button btnAddPerson;
    public ObservableList<String> peopleList = FXCollections.observableArrayList();
    public ObservableList<String> concertList = FXCollections.observableArrayList();

    public void initialize(){
        lvSelectedConcerts.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lvSelectedPeople.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        peopleList.add("Ben Dover");
        peopleList.add("Jhon Doe");
        peopleList.add("Mike Hunt");
        lvSelectedPeople.setItems(peopleList);

    }
}
