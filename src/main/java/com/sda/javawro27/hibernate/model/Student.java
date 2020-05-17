package com.sda.javawro27.hibernate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity // jest to klasa bazodanowa (UWAGA! NIE ZAPOMNIJ O PLIKU CFG.XML)
@Table()
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // identity - identyfikator pochodzi z bazy danych
    // sequence - licznik identyfikatorów pochodzi z hibernate, wszystkie klasy posiadają wspólny licznik
    // table - licznik identyfikatorów pochodzi z hibernate i posiada oddzielny dla każdej tabeli
    private Long id;

//    @Column(name = "first_name")
    private String firstName;
    private String lastName;

    private double height;
    private int age;

    private boolean alive;

    @Enumerated(value = EnumType.STRING)
    private Behaviour behaviour;

}

