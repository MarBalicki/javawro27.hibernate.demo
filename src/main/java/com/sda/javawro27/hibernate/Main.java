package com.sda.javawro27.hibernate;

import com.sda.javawro27.hibernate.model.Behaviour;
import com.sda.javawro27.hibernate.model.Student;
import com.sda.javawro27.hibernate.model.StudentDao;

import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // trzeba mieć stworzoną bazę i tabelę
        StudentDao dao = new StudentDao();
        Scanner scanner = new Scanner(System.in);
        String komenda;
        do {
            // https://pl.spoj.com/
            System.out.println("Podaj komendę [add/list/delete/update/quit]");
            komenda = scanner.nextLine();
            if (komenda.equalsIgnoreCase("add")) {
                addStudents(dao, scanner);
            } else if (komenda.equalsIgnoreCase("list")) {
                listStudents(dao);
            } else if (komenda.equalsIgnoreCase("delete")) {
                deleteStudent(dao, scanner);
            } else if (komenda.equalsIgnoreCase("update")) {
                updateStudent(dao, scanner);
            } else if (komenda.equalsIgnoreCase("byAge")) {
                findByAge(dao, scanner);
            }else if (komenda.equalsIgnoreCase("byBhv")) {
                findByBehaviourAndAlive(dao, scanner);
            }
        } while (!komenda.equalsIgnoreCase("quit"));
    }

    private static void deleteStudent(StudentDao dao, Scanner scanner) {
        // nie da się usunąć rekordu po id (bezpośrednio z sesji)
        System.out.println("Podaj parametry: Identyfikator");
        Long id = Long.valueOf(scanner.nextLine());
        Optional<Student> studentOptional = dao.findById(id);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            dao.delete(student);
        }
    }

    private static void listStudents(StudentDao dao) {
        System.out.println("Lista studentów:");
        dao.getAll().stream().forEach(System.out::println);
    }

    private static void addStudents(StudentDao dao, Scanner scanner) {
        System.out.println("Podaj parametry: IMIE NAZWISKO WZROST WIEK ŻYWY ZACHOWANIE");
        String linia = scanner.nextLine();
        String[] slowa = linia.split(" ");
        Student student = Student.builder()
                .firstName(slowa[0])
                .lastName(slowa[1])
                .height(Double.parseDouble(slowa[2]))
                .age(Integer.parseInt(slowa[3]))
                .alive(Boolean.parseBoolean(slowa[4]))
                .behaviour(Behaviour.valueOf(slowa[5].toUpperCase()))
                .build();
        dao.saveOrUpdate(student);
    }

    private static void updateStudent(StudentDao dao, Scanner scanner) {
        System.out.println("Podaj parametry: Identyfikator");
        Long id = Long.valueOf(scanner.nextLine());
        Optional<Student> studentOptional = dao.findById(id);   // szukamy studenta
        if (studentOptional.isPresent()) {                       // jeśli uda się go odnaleźć
            Student student = studentOptional.get();            // wyciągamy studenta z Optional (Box, opakowanie)
            System.out.println("Próbujesz edytować rekord: " + student);
            System.out.println("Podaj parametry: IMIE NAZWISKO WZROST WIEK ŻYWY ZACHOWANIE");
            String linia = scanner.nextLine();
            String[] slowa = linia.split(" ");

            student = Student.builder()
                    .firstName(slowa[0])
                    .lastName(slowa[1])
                    .height(Double.parseDouble(slowa[2]))
                    .age(Integer.parseInt(slowa[3]))
                    .alive(Boolean.parseBoolean(slowa[4]))
                    .behaviour(Behaviour.valueOf(slowa[5].toUpperCase()))
                    .id(id)
                    .build();
            dao.saveOrUpdate(student);
        } else {
            System.err.println("Error, student z takim id nie istnieje.");
        }
    }

    private static void findByAge(StudentDao dao, Scanner scanner) {
        System.out.println("Podaj parametry: AgeFrom AgeTo");
        String linie = scanner.nextLine();
        int ageFrom = Integer.parseInt(linie.split(" ")[0]);
        int ageTo = Integer.parseInt(linie.split(" ")[1]);

        System.out.println("Znalezione rekordy: ");
        dao.findByAgeBetween(ageFrom, ageTo);
    }

    private static void findByBehaviourAndAlive(StudentDao dao, Scanner scanner) {
        System.out.println("Podaj parametry: Behaviour Alive");
        String linie = scanner.nextLine();
        Behaviour behaviour = Behaviour.valueOf(linie.split(" ")[0]);
        boolean alive = Boolean.parseBoolean((linie.split(" ")[1]));

        System.out.println("Znalezione rekordy: ");
        dao.findByAgeBehaviourAndAlive(behaviour, alive);
    }


}

