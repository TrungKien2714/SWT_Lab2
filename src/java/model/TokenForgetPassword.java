/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;

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
/**
 *
 * @author ASUS
 */
public class TokenForgetPassword {
    private int id, user_id;
    private boolean isUsed;
    private String token;
    private LocalDateTime expiryTime;

    public TokenForgetPassword(int user_id, boolean isUsed, String token, LocalDateTime expiryTime) {
        this.user_id = user_id;
        this.isUsed = isUsed;
        this.token = token;
        this.expiryTime = expiryTime;
    }
    
}
