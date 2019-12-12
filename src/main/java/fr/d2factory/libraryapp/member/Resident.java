package fr.d2factory.libraryapp.member;

public class Resident extends Member {

	public Resident() {
		super();
	}

	public Resident(float wallet) {
		super(wallet);
	}

	@Override
	public void payBook(long numberOfDays) {

		float cost = (float) 0.0;

		if (numberOfDays <= 60) {
			cost = (float) (numberOfDays * 0.10);

		} else if (numberOfDays > 60) {
			cost = (float) (60 * 0.10 + (numberOfDays - 60) * 0.20);
		} else {
			cost = 0;
		}

		if (hasEnoughMoney(cost)) {
			this.pay(cost);
		} else {
			
		}
	}

	@Override
	public String toString() {
		return "Resident [getWallet()=" + getWallet() + ", getBorrowedBooks()=" + getBorrowedBooks() + "]";
	}

	@Override
	public int limitBorrowBook() {
		// TODO Auto-generated method stub
		return 60;
	}

	
}
