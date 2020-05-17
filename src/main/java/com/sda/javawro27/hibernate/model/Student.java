package com.sda.javawro27.hibernate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@Entity // jest to klasa bazodanowa (UWAGA! NIE ZAPOMNIJ O PLIKU CFG.XML)
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Student {
    private Long id;

    private String firstName;
    private String lastName;

    private double height;
    private int age;

    private boolean alive;

}
