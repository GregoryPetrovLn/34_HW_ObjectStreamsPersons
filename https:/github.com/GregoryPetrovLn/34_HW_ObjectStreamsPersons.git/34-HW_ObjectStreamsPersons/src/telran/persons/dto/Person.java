package telran.persons.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class Person implements Serializable {
	private static final long serialVersionUID = 1L;
	int id;
	Address address;
	String name;
	LocalDate birthDate;

	public Person() {
		//Default
	}

	public Person(int id, Address address, String name, LocalDate birthDate) {
		super();
		this.id = id;
		this.address = address;
		this.name = name;
		this.birthDate = birthDate;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

}
