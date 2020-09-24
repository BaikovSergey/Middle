package ru.job4j.xmlandannotations.annotations;

import javax.persistence.*;

@Entity
@Table(name = "engine")
public class Engine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public Engine() {

    }

    public Engine(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
