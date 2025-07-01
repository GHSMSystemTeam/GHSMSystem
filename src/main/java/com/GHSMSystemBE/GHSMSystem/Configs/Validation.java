package com.GHSMSystemBE.GHSMSystem.Configs;

import java.time.LocalDate;
import java.time.LocalTime;

public class Validation {
    public boolean validationSlot(LocalDate bookDate , LocalTime bookTime, int slot, int sizeOfBooking){
        LocalDate thisDate = LocalDate.now();
        LocalDate tomorrow = thisDate.plusDays(1);

        LocalTime startSlot1 = LocalTime.of(7,0,0);
        LocalTime endSlot1 = LocalTime.of(9,0,0);
        LocalTime endSlot2 = LocalTime.of(11,0,0);
        LocalTime endSlot3 = LocalTime.of(13,0,0);
        LocalTime endSlot4 = LocalTime.of(15,0,0);

        // when that slot have 10 booked
        if(sizeOfBooking >= 10){
            return false;
        }

        // when booking for the following days
        if(bookDate.isEqual(tomorrow)){
            return true;
        }

        // check date
        if(bookDate.isEqual(thisDate)){
            if(bookTime.isAfter(startSlot1) && bookTime.isBefore(endSlot1) && slot==1){ // time between 7h - 9h
                return false;
            }else if(bookTime.isAfter(endSlot1) && bookTime.isBefore(endSlot2) && slot <= 2){ // time between 9h - 11h
                return false;
            }else if(bookTime.isAfter(endSlot2) && bookTime.isBefore(endSlot3) && slot <= 3){ // time between 11h - 13h
                return false;
            }else if(bookTime.isAfter(endSlot3) && bookTime.isBefore(endSlot4) && slot <= 4){ // time between 13h - 15h
                return false;
            }else return !bookTime.isAfter(endSlot4); // time from 15h to rest of day
        }
        return false;
    }
}
