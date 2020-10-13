package ru.job4j.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

public class HbnQuery {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    public void select() {
        try (Session session = sf.openSession()) {
            Query query = session.createQuery("from Candidate ");
            for (Object st : query.list()) {
                System.out.println(st);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public void selectById(int id) {
        try (Session session = sf.openSession()) {
            Query query = session.createQuery("from Candidate c where c.id = : fid");
            query.setParameter("fid", id);
            System.out.println(query.uniqueResult());
        } catch (Exception e) {
        e.printStackTrace();
        } finally {
        StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public void update(int id, String name, int experience, int salary) {
        try (Session session = sf.openSession()) {
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery(
                    "update Candidate c set c.name = :newName,"
                            + "c.experience = :newExperience, c.salary =:newSalary "
                            + "where c.id = :fid");
            query.setParameter("fid", id);
            query.setParameter("newName", name);
            query.setParameter("newExperience", experience);
            query.setParameter("newSalary", salary);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public void delete(int id) {
        try (Session session = sf.openSession()) {
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("delete from Candidate where id = : fid");
            query.setParameter("fid", id);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public void insert(int id) {
        try (Session session = sf.openSession()) {
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("insert into Candidate (name, experience, salary)"
                    + "select concat(name, 'NEW'), experience + 5, salary - 10000 from "
                    + "Candidate where id = : " + "fid");
            query.setParameter("fid", id);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static void main(String[] args) {
        HbnQuery test = new HbnQuery();
        test.insert(2);
    }
}
