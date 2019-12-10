package fr.d2factory.libraryapp.library.impl;

import java.time.LocalDate;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.BookRepository;
import fr.d2factory.libraryapp.library.HasLateBooksException;
import fr.d2factory.libraryapp.library.Library;
import fr.d2factory.libraryapp.member.Member;
import fr.d2factory.libraryapp.util.Utils;

public class LibraryImpl implements Library {

	@Override
	public Book borrowBook(BookRepository bookRepository, long isbnCode, Member member, LocalDate borrowedAt)
			throws HasLateBooksException {

		Book book = bookRepository.findBook(isbnCode);
		if (book != null) {
			bookRepository.saveBookBorrow(book, borrowedAt);
			member.addBorrowed(book);
			return book;
		}
		return null;
	}

	@Override
	public void returnBook(BookRepository bookRepository, Book book, Member member) {
		if (member != null) {

			LocalDate borrowDate = bookRepository.findBorrowedBookDate(book);
			long numberOfDays = Utils.differenceBetweenDatesInDays(borrowDate, LocalDate.now());
			member.payBook(numberOfDays);
			member.removeBook(book);

			bookRepository.removeBookBorrowed(book);
		}
	}

}
