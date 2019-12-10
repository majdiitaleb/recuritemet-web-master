package fr.d2factory.libraryapp.library;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.BookRepository;
import fr.d2factory.libraryapp.book.ISBN;
import fr.d2factory.libraryapp.library.impl.LibraryImpl;
import fr.d2factory.libraryapp.member.Resident;
import fr.d2factory.libraryapp.member.Student;
import fr.d2factory.libraryapp.util.Utils;
import junit.framework.Assert;

public class LibraryTest {

	private static Library library;
	private static BookRepository bookRepository;
	private static Student student, student2, student3;
	private static Resident resident, resident2, resident3;

	@BeforeClass
	public static void setup() {

		System.out.println("Start Setup");
		// TODO instantiate the library and the repository
		bookRepository = new BookRepository();

		// TODO add some test books (use BookRepository#addBooks)
		List<Book> books = new Utils().getBooks();
		System.out.println(books.size());
		if (books != null) {
			bookRepository.addBooks(books);
		}

		student = new Student(1, 25);
		resident = new Resident(20);
		student2 = new Student(1, 25);
		resident2 = new Resident(20);
		student3 = new Student(1, 25);
		resident3 = new Resident(20);

		library = new LibraryImpl();

		bookRepository.afficherAvailable();
		bookRepository.afficherBorrowed();

		System.out.println("End Setup");
	}

	@Test
	public void t_1_member_can_borrow_a_book_if_book_is_available() {

		Book book = new Book(new ISBN(Long.valueOf("46578964513")));
		Book availableBook = bookRepository.findBook(book.getIsbn().getIsbnCode());
		assertNotNull(availableBook);

		library.borrowBook(bookRepository, book.getIsbn().getIsbnCode(), student, LocalDate.now());

		bookRepository.afficherAvailable();
		bookRepository.afficherBorrowed();

		System.out.println("End Setup");
	}

	@Test(expected = BookNotAvailableException.class)
	public void t_2_borrowed_book_is_no_longer_available() {

		Book book = new Book(new ISBN(Long.valueOf("46578964513")));
		Book availableBook = bookRepository.findBook(book.getIsbn().getIsbnCode());
		assertNull(availableBook);

	}
//
	@Test
	public void t_3_residents_are_taxed_10cents_for_each_day_they_keep_a_book() {
		System.out.println("START t_3_residents_are_taxed_10cents_for_each_day_they_keep_a_book");
		Book book = new Book(new ISBN(Long.valueOf("3326456467846")));
		Book availableBook = bookRepository.findBook(book.getIsbn().getIsbnCode());
		assertNotNull(availableBook);

		library.borrowBook(bookRepository, book.getIsbn().getIsbnCode(), resident, LocalDate.now().minusDays(25));

		library.returnBook(bookRepository, availableBook, resident);
		Assert.assertEquals((float) 17.5, resident.getWallet());
		bookRepository.afficherAvailable();
		bookRepository.afficherBorrowed();
		System.out.println("END t_3_residents_are_taxed_10cents_for_each_day_they_keep_a_book");
	}

	@Test
	public void t4_students_pay_10_cents_the_first_30days() {
		System.out.println("START t4_students_pay_10_cents_the_first_30days");
		Book book = new Book(new ISBN(Long.valueOf("968787565445")));
		Book availableBook = bookRepository.findBook(book.getIsbn().getIsbnCode());
		assertNotNull(availableBook);

		library.borrowBook(bookRepository, book.getIsbn().getIsbnCode(), student, LocalDate.now().minusDays(25));

		library.returnBook(bookRepository, availableBook, student);
		Assert.assertEquals((float) 24, student.getWallet());
		bookRepository.afficherAvailable();
		bookRepository.afficherBorrowed();
		System.out.println("END t4_students_pay_10_cents_the_first_30days");
	}

	@Test
	public void t5_students_in_1st_year_are_not_taxed_for_the_first_15days() {
		System.out.println("t5_students_in_1st_year_are_not_taxed_for_the_first_15days");
		Book book = new Book(new ISBN(Long.valueOf("465789453149")));
		Book availableBook = bookRepository.findBook(book.getIsbn().getIsbnCode());
		assertNotNull(availableBook);

		library.borrowBook(bookRepository, book.getIsbn().getIsbnCode(), student2, LocalDate.now().minusDays(15));

		library.returnBook(bookRepository, availableBook, student2);
		Assert.assertEquals((float) 25, student2.getWallet());
		bookRepository.afficherAvailable();
		bookRepository.afficherBorrowed();
		System.out.println("END t5_students_in_1st_year_are_not_taxed_for_the_first_15days");
	}
//
	@Test
	public void t6_students_pay_15cents_for_each_day_they_keep_a_book_after_the_initial_30days() {
		System.out.println("START t6_students_pay_15cents_for_each_day_they_keep_a_book_after_the_initial_30day");
		Book book = new Book(new ISBN(Long.valueOf("465789453149")));
		Book availableBook = bookRepository.findBook(book.getIsbn().getIsbnCode());
		assertNotNull(availableBook);

		LocalDate borrowDate = LocalDate.now().minusDays(32);
		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String dateFormatted = borrowDate.format(formatters);
		System.out.println(dateFormatted);
		library.borrowBook(bookRepository, book.getIsbn().getIsbnCode(), student3, borrowDate);

		library.returnBook(bookRepository, availableBook, student3);
		Assert.assertEquals((float) 23.2, student3.getWallet());
		bookRepository.afficherAvailable();
		bookRepository.afficherBorrowed();
		System.out.println("END t6_students_pay_15cents_for_each_day_they_keep_a_book_after_the_initial_30days");
	}
//
	@Test
	public void t7_residents_pay_20cents_for_each_day_they_keep_a_book_after_the_initial_60days() {
		System.out.println("START t7_residents_pay_20cents_for_each_day_they_keep_a_book_after_the_initial_60days");
		Book book = new Book(new ISBN(Long.valueOf("465789453149")));
		Book availableBook = bookRepository.findBook(book.getIsbn().getIsbnCode());
		assertNotNull(availableBook);

		LocalDate borrowDate = LocalDate.now().minusDays(65);
		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String dateFormatted = borrowDate.format(formatters);
		System.out.println(dateFormatted);
		library.borrowBook(bookRepository, book.getIsbn().getIsbnCode(), resident2, borrowDate);

		library.returnBook(bookRepository, availableBook, resident2);
		Assert.assertEquals((float) 13, resident2.getWallet());
		bookRepository.afficherAvailable();
		bookRepository.afficherBorrowed();
		System.out.println("END t6_students_pay_15cents_for_each_day_they_keep_a_book_after_the_initial_30days");
	}
//
//	@Test public void members_cannot_borrow_book_if_they_have_late_books() {
//	 
//		}
	 
}
