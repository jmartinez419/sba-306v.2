package sba.sms.services;
import lombok.*;
import lombok.extern.java.Log;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import sba.sms.dao.StudentI;
import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.utils.HibernateUtil;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
/**
 * StudentService is a concrete class. This class implements the
 * StudentI interface, overrides all abstract service methods and
 * provides implementation for each method. Lombok @Log used to
 * generate a logger file.
 */

public class StudentService  implements StudentI  {

    @Override
    public void createStudent(Student student) {

        Session  sess = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try{
            tx = sess.beginTransaction();
            sess.persist(student);
            tx.commit();
        }
        catch (Exception e){
            if (tx != null) tx.rollback();
            throw e;
        }
        finally {
            sess.close();
        }
    }


    @Override
    public List<Student> getAllStudents()   {

        Session  sess = HibernateUtil.getSessionFactory().openSession();

        Transaction tx = null;

         List<Student> StudentList = new ArrayList<>();

        try{tx = sess.beginTransaction();
            
            Query<Student> Query = sess.createQuery("from student",  Student.class);
            StudentList = Query.getResultList();
            tx.commit();
        }
        catch (Exception e){
            if (tx != null) tx.rollback();
            throw e;
        }
        finally {
            sess.close();
        }
        return StudentList;
    }

    @Override
    public Student getStudentByEmail(String email) {
        Session  sess = HibernateUtil.getSessionFactory().openSession();

        Transaction tx = null;
        Student student;
        try{tx = sess.beginTransaction();

            Query<Student> Query = sess.createQuery("from Student where email =:email ",  Student.class);
            Query.setParameter("email", email);
            student = Query.getSingleResult();

            tx.commit();
        }
        catch (Exception e){
            if (tx != null) tx.rollback();
            throw e;
        }
        finally {
            sess.close();
        }
        return student;
    }

    @Override
    public boolean validateStudent(String email, String password) {
        Session sess = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Student student = sess.get(Student.class, email);
        try {
            tx = sess.beginTransaction();

            if (student == null) throw new HibernateException("Student not found");
            else if (student.getPassword().equals(password)) {
                return true;
            }
            {
                tx.commit();
            }

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            sess.close();
        }
        return false;
    }

    @Override
    public void registerStudentToCourse(String email, int courseId) {
        Session  sess = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        CourseService getcourseId = new CourseService();
        Student student = getStudentByEmail(email);

        try{tx = sess.beginTransaction();

            student.registerCourseHelper(getcourseId.getCourseById(courseId));
            sess.merge(student);

            tx.commit();
        }
        catch (Exception e){
            if (tx != null) tx.rollback();
            throw e;
        }
        finally {
            sess.close();
        }

    }
    @Override
    public List<Course> getStudentCourses(String email) {
        Session sess = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        List<Course> courseList = new ArrayList<>();

        try {tx = sess.beginTransaction();

            Query<Course> Query = sess.createNativeQuery("select Course.CourseId, Instructor, Course.courseName from Course inner join student_courses on Course.CourseId = student_courses.courses_id where student_email=:email ",Course.class);
            Query.setParameter("email", email);

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
