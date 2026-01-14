package dao;
import entity.Loan;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;
import java.util.List;

public class LoanDao {
    public void save(Loan loan) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(loan);
        tx.commit();
        session.close();
    }
    public List<Loan> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Loan> loans = session.createQuery("from Loan", Loan.class).list();
        session.close();
        return loans;
    }
}