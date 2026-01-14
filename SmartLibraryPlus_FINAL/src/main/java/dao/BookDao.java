package dao;

import entity.Book;
import org.hibernate.*;
import util.HibernateUtil;
import java.util.List;

public class BookDao {
 public void save(Book b){Session s=HibernateUtil.getSessionFactory().openSession();Transaction t=s.beginTransaction();s.save(b);t.commit();s.close();}
 public void update(Book b){Session s=HibernateUtil.getSessionFactory().openSession();Transaction t=s.beginTransaction();s.update(b);t.commit();s.close();}
 public Book getById(int id){Session s=HibernateUtil.getSessionFactory().openSession();Book b=s.get(Book.class,id);s.close();return b;}
 public List<Book> getAll(){Session s=HibernateUtil.getSessionFactory().openSession();List<Book> l=s.createQuery("from Book",Book.class).list();s.close();return l;}
 public void delete(Book b){Session s=HibernateUtil.getSessionFactory().openSession();Transaction t=s.beginTransaction();s.delete(b);t.commit();s.close();}
}