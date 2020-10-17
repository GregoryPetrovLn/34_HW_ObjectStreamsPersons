import static telran.util.RandomData.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

import telran.persons.dto.*;

public class RandomPersonCreationAppl {
	private static final long N_PESONS = 100;
	private static final int CHILD_PROB = 30;
	private static final int MIN_ID = 1;
	private static final int MAX_ID = Integer.MAX_VALUE;
	private static final int MIN_AGE_EMPLOYEE = 18;
	private static final int MAX_AGE_EMPLOYEE = 70;
	private static final int MIN_AGE_CHILD = 2;
	private static final int MAX_AGE_CHILD = 6;
	private static final int MIN_SALARY = 15_000;
	private static final int MAX_SALARY = 40_000;
	private static final int MIN_BUILDING_NUMBER = 1;
	private static final int MAX_BUILDING_NUMBER = 100;
	private static final int MIN_APARTMENT_NUMBER = 1;
	private static final int MAX_APARTMENT_NUMBER = 50;

	public static final String FILE_NAME = "persons.data";

	private static final String CITIES[] = { "Tel-Aviv", "Ashdod", "Jerusalem", "Hifa", "Ber-Sheva", "Holon",
			"Ashkelon", "Herzlia", "Bat-Yam" };
	private static final String STREETS[] = { "Rabin", "Ben-Gurion", "Rothschild", "Herzl", "Dizengoff", "King George",
			"Bograshov", "Bialik" };
	private static final String NAMES[] = { "Zuriel", "Abraham", "Sarah", "Itshak", "Rivka", "Asaf", "Yakov",
			"Benyamin", "Yosef", "Olya", "Moshe", "Sergey", "David", "Evgeniy", "Tanya", "Vladimir", "Gregory", "Daria",
			"Lena", "Andrey", "Alexander", "Raisa" };
	private static final String KINDERGARTENS_NAMES[] = { "Sun", "Star", "Flower", "Sea", "Sky", "Sunrise", "Grass",
			"Lake", "Watterfall" };
	private static final String TITLES[] = { "Fullstack Developer", "Architector", "Front-End Developer",
			"Cuber security", "Back-End Developer", "Designer", "Promoter" };
	private static final String COMPANIES[] = { "Google", "Yandex", "Joom", "Microsoft", "Apple", "Tesla", "Sony",
			"Rock Star Games", "Ubisoft", "Blizzard" };

	private static Person persons[];
	private static HashSet<Integer> idOfPersons = new HashSet<>();// only for method getRandomId, to check if there is
																	// already such an ID

	/**
	 * 
	 * @param args
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws Exception {
		persons = getRandomPersons();
		writeDataFile();


	}

	/**
	 * 
	 * @throws Exception
	 */
	private static void writeDataFile() throws Exception {
		try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
			stream.writeObject(persons);
		}
	}

	/**
	 * 
	 * @return
	 */
	private static Person[] getRandomPersons() {
		return Stream.generate(() -> {
			return chance() <= CHILD_PROB ? getChild() : getEmployee();
		}).limit(N_PESONS).toArray(Person[]::new);
	}

	private static Object getEmployee() {
		return new Employee(getRandomId(MIN_ID, MAX_ID), getRandomAddress(), getRandomName(),
				getRandomBirthDate(MIN_AGE_EMPLOYEE, MAX_AGE_EMPLOYEE), getRandomCompany(), getRandomTitle(),
				getRandomSalary(MIN_SALARY, MAX_SALARY));
	}

	private static Object getChild() {
		return new Child(getRandomId(MIN_ID, MAX_ID), getRandomAddress(), getRandomName(),
				getRandomBirthDate(MIN_AGE_CHILD, MAX_AGE_CHILD), getRandomGarten());
	}

	// =============== Section of getting random elements ===============//

	private static int getRandomId(int min, int max) {
		int id = getRandomInt(min, max);
		if (idOfPersons.contains(id)) {
			getRandomId(min, max);
		}
		return id;
	}

	private static int getRandomSalary(int minSalary, int maxSalary) {
		return getRandomInt(minSalary, maxSalary);
	}

	private static String getRandomTitle() {
		return getRandomElement(TITLES);
	}

	private static String getRandomCompany() {
		return getRandomElement(COMPANIES);
	}

	public static String getRandomCity() {
		return getRandomElement(CITIES);
	}

	public static String getRandomStreet() {
		return getRandomElement(STREETS);
	}

	public static String getRandomName() {
		return getRandomElement(NAMES);
	}

	private static LocalDate getRandomBirthDate(int minAgeEmployee, int maxAgeEmployee) {
		return getRandomDate(minAgeEmployee, maxAgeEmployee);
	}

	private static Address getRandomAddress() {
		String city = getRandomCity();
		String street = getRandomStreet();
		int building = getRandomInt(MIN_BUILDING_NUMBER, MAX_BUILDING_NUMBER);
		int apartment = getRandomInt(MIN_APARTMENT_NUMBER, MAX_APARTMENT_NUMBER);
		return new Address(city, street, building, apartment);
	}

	private static String getRandomGarten() {
		return getRandomElement(KINDERGARTENS_NAMES);
	}

}
