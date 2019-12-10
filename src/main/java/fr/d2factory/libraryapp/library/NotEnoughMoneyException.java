package fr.d2factory.libraryapp.library;

/**
 * This exception is thrown when a member who owns late books tries to borrow anavalable book
 */
public class NotEnoughMoneyException extends RuntimeException {

	public NotEnoughMoneyException(String arg0) {
		super(arg0);
	}
	
	
}
