package telran.persons.dto;

public class CompanySalary {
	String company;
	Double avgSalary;
	
	
	public CompanySalary(String company, Double avgSalary) {
		super();
		this.company = company;
		this.avgSalary = avgSalary;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((avgSalary == null) ? 0 : avgSalary.hashCode());
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompanySalary other = (CompanySalary) obj;
		if (avgSalary == null) {
			if (other.avgSalary != null)
				return false;
		} else if (!avgSalary.equals(other.avgSalary))
			return false;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		return true;
	}



	public String getCompany() {
		return company;
	}



	public Double getAvgSalary() {
		return avgSalary;
	}



	@Override
	public String toString() {
		return "CompanySalary [company=" + company + ", avgSalary=" + avgSalary + "]";
	}
	
	
	

}
