package space.harbour.airportsystem.util;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Flight {
    private String departureCity;
    private LocalDate departureDate;
    private String arrivalCity;
    private LocalDate arrivalDate;
}
