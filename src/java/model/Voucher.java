/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.text.SimpleDateFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;




@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Voucher {
    private String codeName;
    private String code;
    private BigDecimal discountAmount;
    private Timestamp validFrom;
    private Timestamp validTo;
    private int quantity;
    private int status;
    private int createdBy;

}
