package app;

import dao.*;
import entity.*;
import java.util.Scanner;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BookDao bookDao = new BookDao();
        StudentDao studentDao = new StudentDao();
        LoanDao loanDao = new LoanDao();

        System.out.println("==========================================");
        System.out.println("     AKILLI KÜTÜPHANE SİSTEMİ - v1.0.0");
        System.out.println("==========================================");
        System.out.println("   Sistem başarıyla başlatıldı...");

        while (true) {
            System.out.println("\n>>>> ANA MENÜ <<<<");
            System.out.println("------------------------------------------");
            System.out.printf("| %-18s | %-18s |\n", "1 - Kitap Ekle", "2 - Kitap Listele");
            System.out.printf("| %-18s | %-18s |\n", "3 - Öğrenci Ekle", "4 - Öğrenci Listele");
            System.out.printf("| %-18s | %-18s |\n", "5 - Ödünç Ver", "6 - Ödünç Listesi");
            System.out.printf("| %-18s | %-18s |\n", "7 - Geri Al", "8 - İstatistikler");
            System.out.printf("| %-18s | %-18s |\n", "0 - Çıkış", "");
            System.out.println("------------------------------------------");
            System.out.print("Seçiminiz: ");

            int secim = -1;
            try {
                secim = scanner.nextInt();
                scanner.nextLine(); 
            } catch (Exception e) {
                System.out.println("(!) Hata: Lütfen geçerli bir rakam giriniz.");
                scanner.nextLine();
                continue;
            }

            switch (secim) {
                case 1:
                    System.out.println("\n[ KİTAP EKLEME ]");
                    System.out.print("> Kitap Adı: "); String t = scanner.nextLine();
                    System.out.print("> Yazar: "); String a = scanner.nextLine();
                    System.out.print("> Basım Yılı: "); int y = scanner.nextInt();
                    Book b = new Book();
                    b.setTitle(t); b.setAuthor(a); b.setYear(y);
                    b.setStatus("MUSAIT"); // İngilizce terim Türkçe yapıldı
                    bookDao.save(b);
                    System.out.println("✓ Başarılı: Kitap sisteme kaydedildi.");
                    break;

                case 2:
                    System.out.println("\n[ KİTAP LİSTESİ ]");
                    System.out.println("ID | Başlık | Yazar | Durum");
                    System.out.println("------------------------------------------");
                    for (Book bk : bookDao.getAll()) {
                        String durum = bk.getStatus().equals("MUSAIT") ? "Mevcut" : "Ödünçte";
                        System.out.printf("%d | %s | %s | [%s]\n", bk.getId(), bk.getTitle(), bk.getAuthor(), durum);
                    }
                    break;

                case 3:
                    System.out.println("\n[ ÖĞRENCİ KAYIT ]");
                    System.out.print("> Ad Soyad: "); String sn = scanner.nextLine();
                    System.out.print("> Bölüm: "); String sd = scanner.nextLine();
                    Student st = new Student();
                    st.setName(sn); st.setDepartment(sd);
                    studentDao.save(st);
                    System.out.println("✓ Başarılı: Öğrenci kaydı oluşturuldu.");
                    break;

                case 4:
                    System.out.println("\n[ ÖĞRENCİ LİSTESİ ]");
                    System.out.println("ID | Ad Soyad | Bölüm");
                    System.out.println("------------------------------------------");
                    for (Student s : studentDao.getAll()) {
                        System.out.printf("%d | %s | %s\n", s.getId(), s.getName(), s.getDepartment());
                    }
                    break;

                case 5:
                    System.out.println("\n[ ÖDÜNÇ VERME ]");
                    System.out.print("> Öğrenci ID: "); int sid = scanner.nextInt();
                    System.out.print("> Kitap ID: "); int bid = scanner.nextInt();
                    Book target = bookDao.getById(bid);
                    if (target != null && "MUSAIT".equals(target.getStatus())) {
                        target.setStatus("ODUNCTE");
                        bookDao.update(target);
                        Loan ln = new Loan();
                        ln.setBook(target);
                        ln.setStudent(studentDao.getById(sid));
                        ln.setBorrowDate(new Date());
                        loanDao.save(ln);
                        System.out.println("✓ Başarılı: Kitap öğrenciye verildi.");
                    } else {
                        System.out.println("X Hata: Kitap kütüphanede değil veya ID yanlış!");
                    }
                    break;

                case 6:
                    System.out.println("\n[ AKTİF ÖDÜNÇ LİSTESİ ]");
                    System.out.println("Öğrenci | Kitap | Tarih");
                    System.out.println("------------------------------------------");
                    for (Loan l : loanDao.getAll()) {
                        System.out.printf("%s -> %s (%s)\n", l.getStudent().getName(), l.getBook().getTitle(), l.getBorrowDate());
                    }
                    break;

                case 7:
                    System.out.println("\n[ İADE ALMA ]");
                    System.out.print("> Kitap ID: "); int iid = scanner.nextInt();
                    Book iadeB = bookDao.getById(iid);
                    if (iadeB != null && "ODUNCTE".equals(iadeB.getStatus())) {
                        iadeB.setStatus("MUSAIT");
                        bookDao.update(iadeB);
                        System.out.println("✓ Başarılı: Kitap geri alındı.");
                    } else {
                        System.out.println("X Hata: Kitap zaten kütüphanede veya bulunamadı.");
                    }
                    break;

                case 8:
                    System.out.println("\n[ İSTATİSTİKLER ]");
                    List<Book> allBooks = bookDao.getAll();
                    long oduncteOlan = allBooks.stream().filter(bk -> "ODUNCTE".equals(bk.getStatus())).count();
                    System.out.println("------------------------------------------");
                    System.out.println("Toplam Kitap Sayısı : " + allBooks.size());
                    System.out.println("Ödünç Verilenler    : " + oduncteOlan);
                    System.out.println("Kütüphanedekiler    : " + (allBooks.size() - oduncteOlan));
                    System.out.println("Kayıtlı Öğrenci     : " + studentDao.getAll().size());
                    System.out.println("------------------------------------------");
                    break;

                case 0:
                    System.out.println("Kapatılıyor... İyi günler!");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("(!) Geçersiz seçim.");
            }
        }
    }
}