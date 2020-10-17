import java.io.*;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.function.*;
import java.util.*;
import java.util.stream.Collectors;

import telran.persons.dto.CompanySalary;
import telran.persons.dto.Employee;
import telran.persons.dto.Person;

public class PersonRestoreAppl {
	private static Person personsList[];
	private static final String FILE_NAME = "./persons.data";
	private static HashMap<Integer, Person> persons = new HashMap<>();
	private static HashMap<Integer, Employee> employees = new HashMap<>();
	private static HashMap<String, List<Person>> personsCities = new HashMap<>();
	private static TreeMap<Integer, List<Employee>> employeeSalary = new TreeMap<>();
	private static HashMap<String, List<Employee>> employeeCompany = new HashMap<>();

	public static void main(String[] args) throws Exception {
		if (!new File(FILE_NAME).exists()) {
			throw new FileNotFoundException("Nothing to restore");
		}
		
		readDataFile();

		Arrays.stream(personsList).forEach(PersonRestoreAppl::addPerson);

		getAllStatistic();
		//mostPopulatedCities();

	}

	private static void readDataFile() throws Exception {
		try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
			personsList = (Person[]) stream.readObject();
		}
	}

	private static void addPerson(Person person) {
		persons.put(person.getId(), person);

		addPersonCity(person);

		if (person instanceof Employee) {
			Employee employee = (Employee) person;
			addEmployee(employee);
			addEmployeeCompany(employee);
			addEmployeeSalary(employee);
		}
	}

	private static void addEmployee(Employee employee) {
		employees.put(employee.getId(), employee);

	}

	private static void addEmployeeSalary(Employee employee) {
		int salary = employee.getSalary();
		employeeSalary.computeIfAbsent(salary, n -> new ArrayList<>()).add(employee);

	}

	private static void addEmployeeCompany(Employee employee) {
		String company = employee.getCompany();
		employeeCompany.computeIfAbsent(company, n -> new ArrayList<>()).add(employee);

	}

	private static void addPersonCity(Person person) {
		String city = person.getAddress().getCity();
		personsCities.computeIfAbsent(city, n -> new ArrayList<>()).add(person);
	}

	// ============ Get statistic section ============//

	public static Map<String, Integer> mostPopulatedCities() {
		// FIXME
		Map<String, Integer> statistic = new HashMap<>();
		int size= 0;
		
		for(Map.Entry m : personsCities.entrySet()) {
			size = ((List<Person>) m.getValue()).size();
			
			statistic.put((String) m.getKey(), size);
		}
		
		
		statistic.entrySet()
				.stream()
				.sorted(Map.Entry.<String, Integer>comparingByValue())
				.collect(Collectors.toMap(e -> e.getKey(),e -> e.getValue()));



		return statistic;
	}

	public static CompanySalary[] getCompanyAvgSalaryDistribution() {
		return employees.values().stream()
				.collect(Collectors.groupingBy(Employee::getCompany, Collectors.averagingInt(Employee::getSalary)))
				.entrySet().stream().sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
				.map(e -> new CompanySalary(e.getKey(), e.getValue())).toArray(CompanySalary[]::new);
	}

	public static List<Employee> getEmployeesWithSalaryGraterAvgSalary() {
		CompanySalary[] companySalaryArray = getCompanyAvgSalaryDistribution();
		int overallAvgSalary = 0;
		int maxSalary = employeeSalary.lastKey();

		for (CompanySalary elem : companySalaryArray) {
			overallAvgSalary += elem.getAvgSalary();
		}

		overallAvgSalary /= companySalaryArray.length;

		NavigableMap<Integer, List<Employee>> employeesSubMap = employeeSalary.subMap(overallAvgSalary, true, maxSalary,
				true);

		return toCollectionEmployees(employeesSubMap.values());

	}

	private static List<Employee> toCollectionEmployees(Collection<List<Employee>> collection) {
		List<Employee> result = new ArrayList<>();
		collection.forEach(result::addAll);
		return result;
	}

	public static void getAllStatistic() {
		System.out.println("\tStatistic:\n");
		System.out.println("Company with avg salary : \n");
		Arrays.stream(getCompanyAvgSalaryDistribution()).forEach(System.out::println);

		System.out.println("\n\nEmployees with salary grater than ovaral avg salary : \n");
		getEmployeesWithSalaryGraterAvgSalary().forEach(System.out::println);
	}

}
