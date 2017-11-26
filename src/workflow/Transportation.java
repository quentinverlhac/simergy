package workflow;

import core.EmergencyDepartment;
import core.Event;
import processing.EndService;
import processing.StartService;
import processing.Task;
import resources.BloodTestRoom;
import resources.MRIRoom;
import resources.Nurse;
import resources.Patient;
import resources.Room;
import resources.ShockRoom;
import resources.Transporter;
import resources.XRayRoom;
import utils.ProbabilityDistribution;

/**
 * This is a class extending WorkflowElement. It represents the transportation to test step.
 * The patients are sent to the test rooms corresponding to their test needs.
 * The step requires a transporter.
 * @author Quentin
 *
 */

public class Transportation extends WorkflowElement {
	protected final Double transportTime = 5.0;
	
	public Transportation(String name, ProbabilityDistribution durationProbability, EmergencyDepartment emergencyDepartment, Double cost) {
		super(name, durationProbability, cost, emergencyDepartment);
	}
	
	public Transportation(ProbabilityDistribution durationProbability, EmergencyDepartment emergencyDepartment, Double cost) {
		super("Transportation", durationProbability, cost, emergencyDepartment);
	}

	public void transportPatient(Transporter transporter, Patient patient) {
		// TODO Auto-generated method stub
		Double transportBeginningTime = emergencyDepartment.getTime();
		Event transportBeginning = new Event("Transport beginning", transportBeginningTime);
		Event transportEnding = new Event("Transport ending", transportBeginningTime + this.transportTime);
		patient.addEvent(transportBeginning);
		patient.addEvent(transportEnding);
		transporter.addEvent(transportBeginning);
		transporter.addEvent(transportEnding);
		patient.addCharges(cost);
	}
	
	/**
	 * This method overrides canTreatPatient of WorkflowElement.
	 * It checks if there is an idle transporter and an available room for examining the patient.
	 * @param patient A Patient instance
	 * @return boolean: true if the patient can be treated by the service, false otherwise
	 * @see WorkflowElement#canTreatPatient
	 */
	@Override
	public boolean canTreatPatient(Patient patient) {
		String healthService = patient.getHistory().get(patient.getHistory().size()).getName().split(" ")[0];
		String roomType = healthService + "Room";
		return (patient != null & this.emergencyDepartment.getIdleTransporter() != null & emergencyDepartment.getAvailableRoom(roomType) != null);
	}
	
	@Override
	public void startServiceOnPatient(Patient patient) {
		// TODO Auto-generated method stub
		this.waitingQueue.remove(patient);
		Transporter transporter = emergencyDepartment.getIdleTransporter();
		transporter.setState("occupied");
		String healthService = patient.getHistory().get(patient.getHistory().size()).getName().split(" ")[0];
		String roomType = healthService + "Room";
		Room room = emergencyDepartment.getAvailableRoom(roomType);
		room.addPatient(patient);
		Event beginTransportation = new Event("Transportation beginning", emergencyDepartment.getTime());
		patient.addEvent(beginTransportation);
		patient.setState("being-transported");
		this.generateEndTask(this, patient, transporter, room);
	}

	@Override
	public void endServiceOnPatient(Patient patient) {
		// TODO Auto-generated method stub
		Event endTransportation = new Event("Transportation ending", emergencyDepartment.getTime());
		patient.addEvent(endTransportation);
		patient.addCharges(cost);
		patient.setState("waiting");
		String healthService;
		if (patient.getLocation() instanceof BloodTestRoom) {
			healthService = "BloodTest";
		}
		else if (patient.getLocation() instanceof XRayRoom) {
			healthService = "XRay";
		}
		else {
			healthService = "MRI";
		}
		emergencyDepartment.getService(healthService).addPatientToWaitingList(patient);
	}
}
