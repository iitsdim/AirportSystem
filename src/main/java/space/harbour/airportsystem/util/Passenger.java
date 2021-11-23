package space.harbour.airportsystem.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Passenger implements Comparable<Passenger> {
    LocalDate currentDay;
    String city;

    @Override
    public int compareTo(Passenger that) {
        return this.currentDay.compareTo(that.currentDay);
    }
}
