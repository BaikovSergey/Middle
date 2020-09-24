package ru.job4j.xmlandannotations.annotations;

import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class AutoCRUDTest {

    final AutoCRUD autoCRUD = new AutoCRUD();

    @Test
    public void createCar() {
        Engine engine = new Engine();
        Car car = new Car(engine);
        int engineId = autoCRUD.createEngine(engine).getId();
        int carId = autoCRUD.createCar(car).getId();
        Car result = autoCRUD.findCarById(carId);
        autoCRUD.deleteCar(carId);
        autoCRUD.deleteEngine(engineId);
        assertThat(result.getId(), is(car.getId()));
    }

    @Test
    public void createDriver() {
        Driver driver = new Driver();
        int driverId = autoCRUD.createDriver(driver).getId();
        Driver result = autoCRUD.findDriverById(driverId);
        autoCRUD.deleteDriver(driverId);
        assertThat(result.getId(), is(driver.getId()));
    }

    @Test
    public void createEngine() {
        Engine engine = new Engine();
        int engineId = autoCRUD.createEngine(engine).getId();
        Engine result = autoCRUD.findEngineById(engineId);
        autoCRUD.deleteEngine(engineId);
        assertThat(result.getId(), is(engine.getId()));
    }

    @Test
    public void deleteCar() {
        Engine engine = new Engine();
        Car car = new Car(engine);
        int engineId = autoCRUD.createEngine(engine).getId();
        int carId = autoCRUD.createCar(car).getId();
        autoCRUD.deleteCar(carId);
        autoCRUD.deleteEngine(engineId);
        Collection<Car> list = autoCRUD.findAllCars();
        assertThat(list.size(), is(0));
    }

    @Test
    public void deleteDriver() {
        Driver driver = new Driver();
        int driverId = autoCRUD.createDriver(driver).getId();
        autoCRUD.deleteDriver(driverId);
        Collection<Driver> list = autoCRUD.findAllDrivers();
        assertThat(list.size(), is(0));
    }

    @Test
    public void deleteEngine() {
        Engine engine = new Engine();
        int engineId = autoCRUD.createEngine(engine).getId();
        autoCRUD.deleteEngine(engineId);
        Collection<Engine> list = autoCRUD.findAllEngine();
        assertThat(list.size(), is(0));
    }
}