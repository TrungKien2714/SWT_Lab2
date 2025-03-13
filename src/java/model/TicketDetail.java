/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ASUS
 */
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author ASUS
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class TicketDetail {

    private int id;
    private int status;
    private int luggageType;
    private int routeId;
    private int seatId;
    private int staffId;
    private int transactionId;
    private BigDecimal amountPaid;
    private String phoneNumber;
    private String fullName;
    private String address;
    private String departureStation;
    private String arrivalStation;
    private Timestamp departureTime;
    private Timestamp arrivalTime;
}
