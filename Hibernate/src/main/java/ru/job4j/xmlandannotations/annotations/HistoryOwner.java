package ru.job4j.xmlandannotations.annotations;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "history_owner")
public class HistoryOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private Set<Driver> drivers;

    private Car car;

    public HistoryOwner() {

    }

    public HistoryOwner(int id, Set<Driver> driver, Car car) {
        this.id = id;
        this.drivers = driver;
        this.car = car;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "history_owner", joinColumns = {
            @JoinColumn(name = "driver_id", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "car_id",
                    nullable = false, updatable = false) })
    public Set<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(Set<Driver> driver) {
        this.drivers = driver;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

}
