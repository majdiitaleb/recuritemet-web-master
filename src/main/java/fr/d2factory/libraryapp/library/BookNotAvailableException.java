package fr.d2factory.libraryapp.library;

/**
 * This exception is thrown when a member who owns late books tries to borrow anavalable book
 */
public class BookNotAvailableException extends RuntimeException {

	public BookNotAvailableException(String arg0) {
		super(arg0);
	}
	
	
}
