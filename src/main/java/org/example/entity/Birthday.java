package org.example.entity;

import java.time.LocalDate;
import java.time.Period;

public record Birthday(LocalDate birthday) {
    public long getAge(){
        return Period.between(birthday, LocalDate.now()).getYears();
    }
}
