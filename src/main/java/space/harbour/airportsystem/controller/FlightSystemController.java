package space.harbour.airportsystem.controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import space.harbour.airportsystem.util.FlightManager;
import space.harbour.airportsystem.util.Resources;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;



public class FlightSystemController implements Initializable {
    public TextField resultPath;
    public TextField startCity;
    public TextField finishCity;
    public TextField startDate;

    public TextField departureDate;
    public TextField departureCity;
    public TextField arrivalCity;
    public TextField arrivalDate;

    static FlightManager flightManager;

    public FlightSystemController() {

    }

    @FXML
    public void searchFlightPressed(ActionEvent event) throws SQLException {
        String cityA = startCity.getText().toLowerCase(Locale.ROOT);
        String cityB = finishCity.getText().toLowerCase(Locale.ROOT);
        LocalDate beginDate = LocalDate.parse(startDate.getText());

        // returns the date of the fastest flight from one place to another
        // if there is no path returns null
        LocalDate result = flightManager.findPath(beginDate, cityA, cityB);
        if (result == null) {
            resultPath.setText("No way to " + cityB);
        } else {
            resultPath.setText("Earliest Date is " + result);
        }
    }

    @FXML
    public void addFlightPressed(ActionEvent event) throws SQLException {
        String cityA = departureCity.getText().toLowerCase();
        String cityB = arrivalCity.getText().toLowerCase();
        LocalDate dateA = LocalDate.parse(departureDate.getText());
        LocalDate dateB = LocalDate.parse(arrivalDate.getText());
        flightManager.addFlight(cityA, dateA, cityB, dateB);
    }

    @FXML
    public void removeFlightPressed(ActionEvent event) throws SQLException {
        String cityA = departureCity.getText().toLowerCase();
        String cityB = arrivalCity.getText().toLowerCase();
        LocalDate dateA = LocalDate.parse(departureDate.getText());
        LocalDate dateB = LocalDate.parse(arrivalDate.getText());
        flightManager.removeFlight(cityA, dateA, cityB, dateB);
    }

    @SneakyThrows
    @FXML
    public void showFlightsPressed(ActionEvent event) throws SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Resources.getUI("AllFlightsPane.fxml"));
        loader.setController(new FlightListController());

        BorderPane pane = loader.load();

        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Airline System");
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        flightManager = new FlightManager();
    }

}
