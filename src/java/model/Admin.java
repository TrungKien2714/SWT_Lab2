/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor // ==> tat ca thuoc tinh deu duoc set gia tri
@NoArgsConstructor // ==> thuoc tinh rong
@Getter
@Setter
@ToString
@Builder
public class Admin {

    private int id;
    private int user_id;
    private String phoneNumber;
    private String fullname;

}

