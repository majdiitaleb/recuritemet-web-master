package fr.d2factory.libraryapp.member;

public class Student extends Member {

	private int level;

	@Override
	public void payBook(long numberOfDays) {
		float cost = (float) 0.0;
		if (this.level == 1) {
			if (numberOfDays > 15 && numberOfDays <= 30) {
				cost = (float) ((numberOfDays - 15) * 0.10);

			} else if (numberOfDays > 30) {
				cost = (float) ((15 * 0.10) + ((numberOfDays - 30) * 0.15));
			} else {
				cost = 0;
			}
		} else {
			cost = (float) (numberOfDays > 30 ? 30 * 0.10 + (numberOfDays - 30) * 0.15 : numberOfDays * 0.10);
		}
		if (hasEnoughMoney(cost)) {
			this.pay(cost);
		}

	}

	public Student() {
		super();
	}

	public Student(int level, float wallet) {
		super(wallet);
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public String toString() {
		
		return super.toString().concat(" : ").concat("Student [level=" + level + "]");
	}

}
