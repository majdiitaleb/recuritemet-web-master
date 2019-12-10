package fr.d2factory.libraryapp.book;

/**
 * A simple representation of a book
 */
public class Book {

	private String title;
	private String author;
	private ISBN isbn;

	public Book(String title, String author, ISBN isbn) {
		this.title = title;
		this.author = author;
		this.isbn = isbn;
	}

	public Book(ISBN isbn) {
		super();
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public ISBN getIsbn() {
		return isbn;
	}

	public void setIsbn(ISBN isbn) {
		this.isbn = isbn;
	}

	@Override
	public int hashCode() {
		final int isbnValue = 41;
		Long l= this.isbn.getIsbnCode();
		int hash=l.hashCode();
		//result = isbnValue * result + Math.toIntExact(this.isbn.getIsbnCode());
		return (int)((l >> 32) ^ l);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book book = (Book) obj;
		if (this.isbn.getIsbnCode() != book.isbn.getIsbnCode())
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Book [title=" + title + ", author=" + author + ", isbn=" + isbn + "]";
	}

}
