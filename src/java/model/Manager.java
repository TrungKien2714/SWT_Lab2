package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author ASUS
 */
@AllArgsConstructor // ==> tat ca thuoc tinh deu duoc set gia tri
@NoArgsConstructor // ==> thuoc tinh rong
@Getter
@Setter
@ToString
@Builder
public class Manager {

    private int id;
    private int user_id;
    private String address;
    private String phoneNumber;
    private String fullName;  
    private int status;
}

