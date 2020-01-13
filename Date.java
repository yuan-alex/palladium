public class Date {
	// standard format is month/day/year
	int month;
	int day;
	int year;

	public Date(int month, int day, int year) {
		this.month = month;
		this.day = day;
		this.year = year;
	}

	public Date(String date_string) {
		String[] split_date = date_string.split("/");
		this.month = Integer.parseInt(split_date[0]);
		this.day = Integer.parseInt(split_date[1]);
		this.year = Integer.parseInt(split_date[2]);
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	public int getYear() {
		return year;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String toString() {
		return this.month + "/" + this.day + "/" + this.year;
	}

}