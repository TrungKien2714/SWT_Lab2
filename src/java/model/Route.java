package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Route {
    private int id; 
    private int trainId;
    private String routeCode; 
    private String description; 
    private String departureStation; 
    private String arrivalStation; 
    private Timestamp departureTime; 
    private Timestamp arrivalTime; 
    private int status; 

    public String getDepartureDate() {
        return (departureTime != null) ? new SimpleDateFormat("MM/dd/yyyy").format(departureTime) : "N/A";
    }

    public String getDepartureHour() {
        return (departureTime != null) ? new SimpleDateFormat("hh:mm a").format(departureTime) : "N/A";
    }

    public String getArrivalHour() {
        return (arrivalTime != null) ? new SimpleDateFormat("hh:mm a").format(arrivalTime) : "N/A";
    }
}
