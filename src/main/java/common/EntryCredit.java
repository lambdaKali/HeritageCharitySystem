/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package common;

import java.io.Serializable;
import java.time.LocalDate;
// EntryCredit class in common package shared by saxson and celtic
public class EntryCredit implements Serializable{
    private String siteObtained;
    private LocalDate expirationDate;
    private Integer memberId;

    public EntryCredit(Integer memberId, LocalDate expirationDate, String siteObtained) {
        this.siteObtained = siteObtained;
        this.expirationDate = expirationDate;
        this.memberId = memberId;
    }

    public String getSiteObtained() {
        return siteObtained;
    }

    public void setSiteObtained(String siteObtained) {
        this.siteObtained = siteObtained;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }
}

