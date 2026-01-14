package entity;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="loans")
public class Loan {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne @JoinColumn(name = "student_id")
    private Student student;

    private Date borrowDate; // java.util.Date kullanarak hatayı sildik
    private Date returnDate;

    public Loan() {}
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public Date getBorrowDate() { return borrowDate; }
    public void setBorrowDate(Date borrowDate) { this.borrowDate = borrowDate; }
    public Date getReturnDate() { return returnDate; }
    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }
}