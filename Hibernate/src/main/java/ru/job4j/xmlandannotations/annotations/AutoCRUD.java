package ru.job4j.xmlandannotations.annotations;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;

public class AutoCRUD implements CRUDE {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();

    private SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    @Override
    public Car createCar(Car car) {
        this.transaction(session -> session.save(car), sf);
        return car;
    }

    @Override
    public Driver createDriver(Driver driver) {
        this.transaction(session -> session.save(driver), sf);
        return driver;
    }

    @Override
    public Engine createEngine(Engine engine) {
        this.transaction(session -> session.save(engine), sf);
        return engine;
    }

    @Override
    public void updateCar(Car car) {
        this.transactionVoid(session -> session.update(car), sf);
    }

    @Override
    public void updateDriver(Driver driver) {
        this.transactionVoid(session -> session.update(driver), sf);
    }

    @Override
    public void updateEngine(Engine engine) {
        this.transactionVoid(session -> session.update(engine), sf);
    }

    @Override
    public void deleteCar(Integer id) {
        Car car = new Car();
        car.setId(id);
        this.transactionVoid(session -> session.delete(car), sf);
    }

    @Override
    public void deleteDriver(Integer id) {
        Driver driver = new Driver();
        driver.setId(id);
        this.transactionVoid(session -> session.delete(driver), sf);
    }

    @Override
    public void deleteEngine(Integer id) {
        Engine engine = new Engine();
        engine.setId(id);
        this.transactionVoid(session -> session.delete(engine), sf);
    }

    @Override
    public Car findCarById(Integer id) {
        return this.transaction(session -> session.get(Car.class, id), sf);
    }

    @Override
    public Driver findDriverById(Integer id) {
        return this.transaction(session -> session.get(Driver.class, id), sf);
    }

    @Override
    public Engine findEngineById(Integer id) {
        return this.transaction(session -> session.get(Engine.class, id), sf);
    }

    @Override
    public Collection<Car> findAllCars() {
        return this.transaction(session -> session.createQuery("from ru.job4j.xmlandannotations"
                + ".annotations.Car").list(), sf);
    }

    @Override
    public Collection<Driver> findAllDrivers() {
        return this.transaction(session -> session.createQuery("from ru.job4j.xmlandannotations"
                + ".annotations.Driver").list(), sf);
    }

    @Override
    public Collection<Engine> findAllEngine() {
        return this.transaction(session -> session.createQuery("from ru.job4j.xmlandannotations"
                + ".annotations.Engine").list(), sf);
    }

    private <T> T transaction(final Function<Session, T> command, SessionFactory sf) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T result = command.apply(session);
            tx.commit();
            return result;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    private void transactionVoid(final Consumer<Session> command, SessionFactory sf) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            command.accept(session);
            tx.commit();
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

}
