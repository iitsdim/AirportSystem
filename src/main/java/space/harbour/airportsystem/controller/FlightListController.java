package space.harbour.airportsystem.controller;

import javafx.collections.FXCollections;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import lombok.SneakyThrows;
import space.harbour.airportsystem.util.Flight;
import space.harbour.airportsystem.util.FlightManager;

import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class FlightListController implements Initializable {
    public TextField searchTextField;
    public TableView<Flight> dataTable;
    private FlightManager flightManager = FlightSystemController.flightManager;
    private List<Flight> flightList;

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prepareTableView();
    }

    private void prepareTableView() throws SQLException {

        TableColumn<Flight, String> departCity = new TableColumn<>("Departure City");
        departCity.setCellValueFactory(new PropertyValueFactory<>("departureCity"));
        TableColumn<Flight, String> departDate = new TableColumn<>("Departure Date");
        departDate.setCellValueFactory(new PropertyValueFactory<>("departureDate"));

        TableColumn<Flight, String> arrivalCity = new TableColumn<>("Arrival City");
        arrivalCity.setCellValueFactory(new PropertyValueFactory<>("arrivalCity"));
        TableColumn<Flight, String> arrivalDate = new TableColumn<>("Arrival Date");
        arrivalDate.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));

        dataTable.getColumns().add(departCity);
        dataTable.getColumns().add(departDate);
        dataTable.getColumns().add(arrivalCity);
        dataTable.getColumns().add(arrivalDate);

        flightList = flightManager.allFlights();
        dataTable.setItems(FXCollections.observableArrayList(flightList));
    }

    @SneakyThrows
    @FXML
    public void searchCityFrom(ActionEvent event) {
        String cityName = searchTextField.getText();
        flightList = flightManager.allFlights();
        if (cityName.equals("")) {
            dataTable.setItems(FXCollections.observableArrayList(flightList));
            return;
        }
        List<Flight> resultList = flightList.stream()
                .filter(order -> order.getDepartureCity().equals(cityName))
                .collect(Collectors.toList());
        dataTable.setItems(FXCollections.observableArrayList(resultList));
    }
}
