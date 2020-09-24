package ru.job4j.xmlandannotations.annotations;

import java.util.Collection;

public interface CRUDE {
    Car createCar(Car car);
    Driver createDriver(Driver driver);
    Engine createEngine(Engine engine);
    void updateCar(Car car);
    void updateDriver(Driver driver);
    void updateEngine(Engine engine);
    void deleteCar(Integer id);
    void deleteDriver(Integer id);
    void deleteEngine(Integer id);
    Car findCarById(Integer id);
    Driver findDriverById(Integer id);
    Engine findEngineById(Integer id);
    Collection<Car> findAllCars();
    Collection<Driver> findAllDrivers();
    Collection<Engine> findAllEngine();
}
