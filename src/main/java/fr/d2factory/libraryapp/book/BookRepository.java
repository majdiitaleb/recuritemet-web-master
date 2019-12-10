package fr.d2factory.libraryapp.book;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.d2factory.libraryapp.library.BookNotAvailableException;

/**
 * The book repository emulates a database via 2 HashMaps
 */
public class BookRepository {

	private Map<ISBN, Book> availableBooks = new HashMap<>();
	private Map<Book, LocalDate> borrowedBooks = new HashMap<>();

	public void addBooks(List<Book> books) {
		if (books.size() > 0) {

			for (Book book : books) {
				availableBooks.put(book.getIsbn(), book);
			}
		}
	}

	public Book findBook(long isbnCode) {
		if (isbnCode != 0) {
			ISBN key = new ISBN(isbnCode);
			if (availableBooks.containsKey(key)) {
				return availableBooks.get(key);
			} else {
				throw new BookNotAvailableException("The book with ISBN " + isbnCode + " is no longer available.");
			}
		}
		return null;
	}

	public void saveBookBorrow(Book book, LocalDate borrowedAt) {
		if (book != null) {
			if (findBook(book.getIsbn().getIsbnCode()) != null) {
				borrowedBooks.put(book, borrowedAt);
				availableBooks.remove(book.getIsbn());
			}
		}
	}

	public void removeBookBorrowed(Book book) {
		if (book != null) {
			borrowedBooks.remove(book);
			availableBooks.put(book.getIsbn(), book);
		}
	}

	public LocalDate findBorrowedBookDate(Book book) {
		if (book != null) {
			if (borrowedBooks.containsKey(book))
				return borrowedBooks.get(book);
			return null;
		}
		return null;
	}

	public void afficherBorrowed() {

		System.out.println("Borrowed Books : ");
		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		for (Map.Entry<Book, LocalDate> entry : borrowedBooks.entrySet()) {

			String dateFormatted = entry.getValue().format(formatters);
			System.out.println("Book Key = " + entry.getKey() + ", Date Value = " + dateFormatted);
		}
	}

	public void afficherAvailable() {
		System.out.println("Available Books : ");
		for (Map.Entry<ISBN, Book> entry : availableBooks.entrySet()) {

			System.out.println("ISBN Key = " + entry.getKey() + ", Book Value = " + entry.getValue());
		}
	}
}
