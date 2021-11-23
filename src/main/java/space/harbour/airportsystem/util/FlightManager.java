package space.harbour.airportsystem.util;

import org.postgresql.util.PSQLException;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class FlightManager {
    private Connection connSQL;

    public LocalDate findPath(LocalDate startDate, String startCity, String finishCity) throws SQLException {
        HashMap<String, LocalDate> distance = new HashMap<>();
        PriorityQueue<Passenger> pq = new PriorityQueue<>();
        pq.add(new Passenger(startDate, startCity));
        distance.put(startCity, startDate);

        while (pq.size() > 0) {
            Passenger currentCase = pq.poll();
            String fromCity = currentCase.getCity();
            LocalDate currentDate = currentCase.getCurrentDay();
            for (Flight flight : getFlights(fromCity)) {
                if (flight.getDepartureDate().compareTo(currentDate) >= 0) {
                    String toCity = flight.getArrivalCity();
                    LocalDate arrivalDay = flight.getArrivalDate();
                    if (!distance.containsKey(toCity) || arrivalDay.compareTo(distance.get(toCity)) < 0) {
                        distance.put(toCity, arrivalDay);
                        pq.add(new Passenger(arrivalDay, toCity));
                    }
                }
            }
        }

        return distance.get(finishCity);
    }

    public void addFlight(String departCity, LocalDate departDate, String arrivalCity, LocalDate arrivalDate) throws SQLException{
        connSQL = Constants.connect();
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();
        for (String curVal : Constants.COLUMN_HEADERS) {
            if (columns.length() > 0) {
                columns.append(", ");
            }
            columns.append(curVal);
        }
        for (String curVal : new String[]{departCity,
                departDate.toString(), arrivalCity, arrivalDate.toString()}) {
            if (values.length() > 0) {
                values.append(", ");
            }
            values.append("'" + curVal + "'");
        }

        String query = "insert into flights (" + columns + ") values (" + values + ")";

        if(values.length() == 0) {
            System.out.println("Wrong values!");
            return;
        }
        try (PreparedStatement stmt = connSQL.prepareStatement(query)) {
            System.out.println(stmt);
            stmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeFlight(String departCity, LocalDate departDate, String arrivalCity, LocalDate arrivalDate) throws SQLException{
        connSQL = Constants.connect();

        String query = "delete from flights where departurecity=? AND departuredate=? AND arrivalcity=? AND arrivaldate=?";

        try (PreparedStatement stmt = connSQL.prepareStatement(query)) {

            stmt.setString(1, departCity);
            stmt.setString(3, arrivalCity);
            stmt.setDate(2, Date.valueOf(departDate));
            stmt.setDate(4, Date.valueOf(arrivalDate));

            System.out.println(stmt);
            stmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private List<Flight> getFlights(String currentCity) throws SQLException {
        String query = "select * from flights where departurecity = ? order by arrivaldate";

        connSQL = Constants.connect();
        List<Flight> neededFlights;
        ResultSet flightSet;
        neededFlights = new Vector<>() {
        };
        try (PreparedStatement stmt = connSQL.prepareStatement(query)) {
            stmt.setString(1, currentCity);
            flightSet = stmt.executeQuery();
            while (flightSet.next()) {
                neededFlights.add(
                        new Flight(flightSet.getString("departurecity"),
                                flightSet.getDate("departuredate").toLocalDate(),
                                flightSet.getString("arrivalcity"),
                                flightSet.getDate("arrivaldate").toLocalDate())
                );
            }
        } catch (NullPointerException | PSQLException e) {
            System.out.println(e.getMessage());
        }
        return neededFlights;
    }

    public List<Flight> allFlights() throws SQLException {
        String query = "select * from flights";

        connSQL = Constants.connect();
        List<Flight> allFlights;
        ResultSet flightSet;
        allFlights = new Vector<>() {
        };
        try (PreparedStatement stmt = connSQL.prepareStatement(query)) {
            flightSet = stmt.executeQuery();
            while (flightSet.next()) {
                allFlights.add(
                        new Flight(flightSet.getString("departurecity"),
                                flightSet.getDate("departuredate").toLocalDate(),
                                flightSet.getString("arrivalcity"),
                                flightSet.getDate("arrivaldate").toLocalDate())
                );
            }
        } catch (NullPointerException | PSQLException e) {
            System.out.println(e.getMessage());
        }
        return allFlights;
    }
}
