package telran.util;

import java.time.LocalDate;

public class RandomData {
	

	public static int getRandomInt(int min, int max) {
		return (int) (min + Math.random() * (max - min + 1));
	}

	public static <T> T getRandomElement(T[] array) {
		int index = getRandomInt(0, array.length - 1);
		return array[index];
	}

	public static int chance() {
		return getRandomInt(1, 100);
	}

	public static LocalDate getRandomDate(int minAge, int maxAge) {
		int year = getYear(minAge, maxAge);
		int month = getRandomInt(1, 12);
		int day = getRandomInt(1, 28);
		return LocalDate.of(year, month, day);
	}
	
	private static int getYear(int minAge, int maxAge) {
		int minYear = LocalDate.now().getYear() - minAge;
		int maxYear = LocalDate.now().getYear() - maxAge;
		return getRandomInt(minYear, maxYear);
	}
	
	

}
