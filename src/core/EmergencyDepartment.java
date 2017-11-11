package core;

import java.util.ArrayList;

import resource.Human;
import resource.Patient;
import resource.Physician;
import workflow.WorkflowElement;

public class EmergencyDepartment {
	protected ArrayList<Patient> patients;
	protected ArrayList<Entity> resources;
	protected ArrayList<Human> staff;
	protected ArrayList<Event> history;
	protected ArrayList<WorkflowElement> services;
	
	EmergencyDepartment(ProbabilityDistribution L1Distribution, ProbabilityDistribution L2Distribution,
			ProbabilityDistribution L3Distribution, ProbabilityDistribution L4Distribution,
			ProbabilityDistribution L5Distribution) {
		patients = new ArrayList<Patient>();
		resources = new ArrayList<Entity>();
		staff = new ArrayList<Human>();
		history = new ArrayList<Event>();
		services = new ArrayList<WorkflowElement>();
	}

	public ArrayList<Patient> getPatients() {
		return patients;
	}

	public void setPatients(ArrayList<Patient> patients) {
		this.patients = patients;
	}

	public void addPatient(Patient patient) {
		this.patients.add(patient);
	}
	
	public void removePatient(Patient patient) {
		this.patients.remove(patient);
	}
	
	public ArrayList<Entity> getResources() {
		return resources;
	}

	public void setResources(ArrayList<Entity> resources) {
		this.resources = resources;
	}

	public void addResource(Entity resource) {
		this.resources.add(resource);
	}
	
	public void removeResource(Entity resource) {
		this.resources.remove(resource);
	}
	
	public ArrayList<Human> getStaff() {
		return staff;
	}

	public void setStaff(ArrayList<Human> staff) {
		this.staff = staff;
	}
	
	public void addEmployee(Human employee) {
		this.staff.add(employee);
	}
	
	public void removeEmployee(Human employee) {
		this.staff.remove(employee);
	}

	public ArrayList<Event> getHistory() {
		return history;
	}

	public void setHistory(ArrayList<Event> history) {
		this.history = history;
	}

	public void addEvent(Event event) {
		this.history.add(event);
	}
	
	public void removeEvent(Event event) {
		this.history.remove(event);
	}
	
	public ArrayList<WorkflowElement> getServices() {
		return services;
	}

	public void setServices(ArrayList<WorkflowElement> services) {
		this.services = services;
	}
	
	public void addService(WorkflowElement service) {
		this.services.add(service);
	}
	
	public void removeService(WorkflowElement service) {
		this.services.remove(service);
	}
}
