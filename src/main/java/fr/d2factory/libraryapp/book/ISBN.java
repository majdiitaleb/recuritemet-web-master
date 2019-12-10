package fr.d2factory.libraryapp.book;

public class ISBN {

	private long isbnCode;

	public ISBN(long isbnCode) {
		this.isbnCode = isbnCode;
	}

	public long getIsbnCode() {
		return isbnCode;
	}

	public void setIsbnCode(long isbnCode) {
		this.isbnCode = isbnCode;
	}

	@Override
	public int hashCode() {
		
		Long l= this.getIsbnCode();
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
		ISBN isbn = (ISBN) obj;
		if (this.getIsbnCode() != isbn.getIsbnCode())
			return false;
		return true;
	}
}
