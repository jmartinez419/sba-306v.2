package sba.sms.services;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import sba.sms.dao.CourseI;
import sba.sms.models.Course;
import sba.sms.utils.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * CourseService is a concrete class. This class implements the
 * CourseI interface, overrides all abstract service methods and
 * provides implementation for each method.
 */
public class CourseService implements CourseI {

    @Override
    public void createCourse(Course course) {
        Session sess = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try { tx = sess.beginTransaction();
            sess.persist(course);
            tx.commit();
        } catch (HibernateException exception) {
            if (tx != null) tx.rollback();
            exception.printStackTrace();
        } finally {
            sess.close();
        }
    }

    @Override
    public Course getCourseById(int courseId) {
        Session sess = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        Course course = new Course();

        try { tx = sess.beginTransaction();

            Query<Course> Query = sess.createQuery("FROM Course where id =:id", Course.class);
            Query.setParameter("id", courseId);

            course = Query.getSingleResult();

            tx.commit();

        } catch (HibernateException exception) {
            if (tx != null) tx.rollback();
            exception.printStackTrace();

        } finally {
            sess.close();
        } return course;

    }

    @Override
    public List<Course> getAllCourses() {

        Session sess = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        List<Course> courseList = new ArrayList<>();

        try { tx = sess.beginTransaction();

            Query<Course> Query = sess.createQuery("FROM Course ", Course.class);
            courseList = Query.getResultList();

            tx.commit();
        } catch (HibernateException exception) {

            if (tx != null) tx.rollback();
            exception.printStackTrace();
        } finally {
            sess.close();
        }
        return courseList;
    }
}
