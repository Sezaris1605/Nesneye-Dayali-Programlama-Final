package dao;

import entity.Student;
import org.hibernate.*;
import util.HibernateUtil;
import java.util.List;

public class StudentDao {
 public void save(Student s){Session ss=HibernateUtil.getSessionFactory().openSession();Transaction t=ss.beginTransaction();ss.save(s);t.commit();ss.close();}
 public Student getById(int id){Session ss=HibernateUtil.getSessionFactory().openSession();Student s=ss.get(Student.class,id);ss.close();return s;}
 public List<Student> getAll(){Session ss=HibernateUtil.getSessionFactory().openSession();List<Student> l=ss.createQuery("from Student",Student.class).list();ss.close();return l;}
}