package fr.d2factory.libraryapp.member;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.library.Library;

/**
 * A member is a person who can borrow and return books to a {@link Library}
 * A member can be either a student or a resident
 */
public abstract class Member {
    /**
     * An initial sum of money the member has
     */
    private float wallet;
    private boolean isTooLate=false;

    private List<Book> borrowedBooks;
    /**
     * The member should pay their books when they are returned to the library
     *
     * @param numberOfDays the number of days they kept the book
     */
    public abstract void payBook(long numberOfDays);

    public abstract int limitBorrowBook();
    public Member() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Member(float wallet) {
		super();
		this.wallet = wallet;
	}

	public float getWallet() {
        return wallet;
    }

    public void setWallet(float wallet) {
        this.wallet = wallet;
    }

	public List<Book> getBorrowedBooks() {
		return borrowedBooks;
	}

	public void setBorrowedBooks(List<Book> borrowedBooks) {
		this.borrowedBooks = borrowedBooks;
	}

	
	public boolean isTooLate() {
		return isTooLate;
	}

	public void setTooLate(boolean isTooLate) {
		this.isTooLate = isTooLate;
	}

	public void addBorrowed(Book book) {
		if (book != null) {
			if (borrowedBooks == null) {
				borrowedBooks = new ArrayList<Book>();
			}
			borrowedBooks.add(book);
		}
	}
	

	
	public void removeBook(Book book) {
		
		List<Book> borrowedBooksTmp = new ArrayList<Book>();
		for (Iterator<Book> iterator = borrowedBooks.iterator(); iterator.hasNext();) {
			Book bookac = (Book) iterator.next();
			if(!book.equals(bookac)) {
				borrowedBooksTmp.add(bookac);
			}
		}
		borrowedBooks = borrowedBooksTmp;
		
	}
	public boolean hasEnoughMoney(float cost) {
		return wallet >= cost;
	}
	
	public void pay(float cost) {
		if (cost > 0 && hasEnoughMoney(cost)) {
			wallet -= cost;
			//borrowedBooks.remove(book)
		}
	}
	
	public void addToWallet(float amount) {
		wallet += amount;
	}

	@Override
	public String toString() {
		return "Member [wallet=" + wallet + ", borrowedBooks=" + borrowedBooks + "]";
	}
    
}
