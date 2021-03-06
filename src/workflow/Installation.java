package workflow;

import core.EmergencyDepartment;
import core.Event;
import resources.Nurse;
import resources.Patient;
import resources.Room;
import utils.ProbabilityDistribution;

/**
 * This is a class extending WorkflowElement. It represents the installation in the consultation room.
 * The patients are sent to the room the triage assigned them.
 * The step requires a nurse.
 * @author Quentin
 *
 */

public class Installation extends WorkflowElement {

	public Installation(String name, ProbabilityDistribution durationProbability, Double cost, EmergencyDepartment emergencyDepartment) {
		super(name, durationProbability, cost, emergencyDepartment);
	}
	
	public Installation(ProbabilityDistribution durationProbability, Double cost, EmergencyDepartment emergencyDepartment) {
		super("Installation", durationProbability, cost, emergencyDepartment);
	}
	
	/**
	 * This method overrides canTreatPatient of WorkflowElement.
	 * It checks if there is an available nurse and an available room for patient's installation.
	 * @param patient A Patient instance
	 * @return boolean: true if the patient can be treated by the service, false otherwise
	 * @see WorkflowElement#canTreatPatient
	 */
	@Override
	public boolean canTreatPatient(Patient patient) {
		Nurse nurse = emergencyDepartment.getIdleNurse();
		String roomType;
		if (patient != null && patient.getSeverityLevel().getLevel() < 3) {
			roomType = "ShockRoom";
		}
		else {
			roomType = "BoxRoom";
		}
		return (patient != null && nurse != null && emergencyDepartment.getAvailableRoom(roomType) != null);
	}
	

	/**
	 * This method overrides startServiceOnPatient of WorkflowElement.
	 * It finds a nurse and a room for the patient installation.
	 * It updates the patient, the nurse, and the room information.
	 * Then it adds the end of the task to the service task queue.
	 * @param patient A Patient instance
	 */
	@Override
	public void startServiceOnPatient(Patient patient) {
		this.waitingQueue.remove(patient);
		Nurse nurse = emergencyDepartment.getIdleNurse();
		nurse.setState("occupied");
		Room room;
		if(patient.getSeverityLevel().getLevel() <= 2) {
			room = emergencyDepartment.getAvailableRoom("ShockRoom");
			room.addPatient(patient);
		}
		else {
			room = emergencyDepartment.getAvailableRoom("BoxRoom");
			room.addPatient(patient);
		}
		patient.getLocation().removePatient(patient);
		patient.setLocation(room);
		Event beginTransportation = new Event("Installation beginning", emergencyDepartment.getTime());
		patient.addEvent(beginTransportation);
		patient.setState("being-installed");
		this.generateEndTask(this, patient, nurse);
	}
	
	/**
	 * This method overrides endServiceOnPatient of WorkflowElement.
	 * It ends the installation of a patient before sending him to the waiting queue of consultation.
	 * First it updates the patient, the nurse, and the room information.
	 * Then it adds the patient to the consultation waiting queue.
	 * @param patient A Patient instance
	 */
	@Override
	public void endServiceOnPatient(Patient patient) {
		Event endTransportation = new Event("Installation ending", emergencyDepartment.getTime());
		patient.addEvent(endTransportation);
		patient.addCharges(cost);
		patient.setState("waiting");
		emergencyDepartment.getService("Consultation").addPatientToWaitingList(patient);
	}

}
