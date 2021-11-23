package space.harbour.airportsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import space.harbour.airportsystem.controller.FlightSystemController;
import space.harbour.airportsystem.util.FlightManager;
import space.harbour.airportsystem.util.Resources;

import java.sql.SQLException;
import java.time.LocalDate;

/* AirPort System
 ** @author Dinmukhamed Tursynbay
 ** @version November 2021
 */

public class FlightSystemApp extends Application {
    static public void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Resources.getUI("FindPathPane.fxml"));
        loader.setController(new FlightSystemController());

        BorderPane pane = loader.load();

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setTitle("Airline System");
        stage.show();
    }

    static LocalDate QueryFastestPath(LocalDate startDate, String startCity, String finishCity) throws SQLException {
        FlightManager flightManager = new FlightManager();
        // returns the date of the fastest flight from one place to another
        // if there is no path returns null
        return flightManager.findPath(startDate, startCity, finishCity);
    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
}
