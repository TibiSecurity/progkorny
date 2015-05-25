import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;
import static org.hamcrest.CoreMatchers.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import model.Car;
import model.CarDatabase;
import model.Client;
import model.ClientDatabase;
import model.Manufacturer;
import model.Model;

import org.junit.Test;

import view.FormEvent;
import controller.Controller;

public class TestController {
	@Test
	public void testConnectionValidCredentials() {
		Controller controller = new Controller();
		try {
			controller.configure("H_B2WKL0", "kassai");
		} catch (Exception e) {
			fail("Database Connection could not be established");
		}
		assertNotNull(controller.getConnection());
	}
	
	@Test
	public void testGetClients(){
		Controller controller = new Controller();
		try {
			controller.configure("H_B2WKL0", "kassai");
		} catch (Exception e) {
			fail("Database Connection could not be established");
		}
		controller.addClient(new FormEvent(this, "TesztUser", "TestPhoneNum", new Timestamp(Calendar.getInstance().getTimeInMillis()), "Audi", "R8", "AAA-000", "1999", "fekete", 13345));
		
		List<Client> clientList = new ArrayList<Client>();
		clientList = controller.getClients();
		assertEquals(clientList.size(), 1);
	}
	
	@Test
	public void testGetCars(){
		Controller controller = new Controller();
		try {
			controller.configure("H_B2WKL0", "kassai");
		} catch (Exception e) {
			fail("Database Connection could not be established");
		}
		controller.addClient(new FormEvent(this, "TesztUser", "TestPhoneNum", new Timestamp(Calendar.getInstance().getTimeInMillis()), "Audi", "R8", "AAA-000", "1999", "fekete", 13345));
		controller.addCar(new FormEvent(this, "TesztUser", "TestPhoneNum", new Timestamp(Calendar.getInstance().getTimeInMillis()), "Audi", "R8", "AAA-000", "1999", "fekete", 13345));
		
		List<Car> carList = new ArrayList<Car>();
		carList = controller.getCars();
		assertEquals(carList.size(), 1);
	}
	
	@Test
	public void testClientAndCarRemoval(){
		Controller controller = new Controller();
		try {
			controller.configure("H_B2WKL0", "kassai");
		} catch (Exception e) {
			fail("Database Connection could not be established");
		}
		
		controller.addClient(new FormEvent(this, "TesztUser", "TestPhoneNum", new Timestamp(Calendar.getInstance().getTimeInMillis()), "Audi", "R8", "AAA-000", "1999", "fekete", 13345));
		controller.addCar(new FormEvent(this, "TesztUser", "TestPhoneNum", new Timestamp(Calendar.getInstance().getTimeInMillis()), "Audi", "R8", "AAA-000", "1999", "fekete", 13345));
		
		controller.removeClient(0);
		List<Client> clientList = new ArrayList<Client>();
		clientList = controller.getClients();
		assertEquals(clientList.get(0).isMarkedForDeletion(), true);
		List<Car> carList = new ArrayList<Car>();
		carList = controller.getCars();
		assertEquals(carList.get(0).isMarkedForDeletion(), true);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testDatabaseRelatedMethods() throws SQLException{
		Controller controller = new Controller();
		try {
			controller.configure("H_B2WKL0", "kassai");
		} catch (Exception e) {
			fail("Database Connection could not be established");
		}
		
		Connection connection = controller.getConnection();
		connection.setAutoCommit(false);

		try{
			controller.addClient(new FormEvent(this, "TesztUser", "TestPhoneNum", new Timestamp(Calendar.getInstance().getTimeInMillis()), "Audi", "R8", "AAA-000", "1999", "fekete", 13345));
			controller.addCar(new FormEvent(this, "TesztUser", "TestPhoneNum", new Timestamp(Calendar.getInstance().getTimeInMillis()), "Audi", "R8", "AAA-000", "1999", "fekete", 13345));
			
			controller.save();
			controller.load();
			
			List<Client> clientList = new ArrayList<Client>();
			clientList = controller.getClients();
			assertEquals(clientList.get(0).getName(), "TesztUser");
			
			List<Car> carList = new ArrayList<Car>();
			carList = controller.getCars();
			assertEquals(carList.get(0).getClientName(), "TesztUser");
			
			controller.removeClient(0);
			controller.remove();
			
			controller.load();
			clientList = new ArrayList<Client>();
			clientList = controller.getClients();
			assertNull(clientList.get(0).getName());
		  } finally {
		    connection.rollback();
		    connection.close();
		  } 
	}
	
	@Test
	public void testListManufacturers() throws SQLException{
		Controller controller = new Controller();
		try {
			controller.configure("H_B2WKL0", "kassai");
		} catch (Exception e) {
			fail("Database Connection could not be established");
		}
		
		List<Manufacturer> manList = new ArrayList<Manufacturer>();
		manList = controller.loadManufacturers();
		assertNotNull(manList.size());
	}
	
	@Test
	public void testListModels() throws SQLException{
		Controller controller = new Controller();
		try {
			controller.configure("H_B2WKL0", "kassai");
		} catch (Exception e) {
			fail("Database Connection could not be established");
		}
		
		List<Model> modList = new ArrayList<Model>();
		modList = controller.loadModels("Audi");
		assertNotNull(modList.size());
	}
	
	@Test
	public void testConnectAndDisconnect() throws SQLException{
		Controller controller = new Controller();
		try {
		controller.configure("H_B2WKL0", "kassai");
		} catch (Exception e) {
			fail("Database Connection could not be established");
		}
		controller.disconnect();
		assertEquals(controller.getConnection().isClosed(), true);
		
		Controller controller2 = new Controller();
		try {
		controller2.configure("H_B2WKL0", "kassai");
		} catch (Exception e) {
			fail("Database Connection could not be established");
		}
		try {
			controller2.connect();
		} catch (Exception e) {
			fail("Database Connection could not be established");
		}
		assertTrue(controller2.getConnection().isValid(10));
	}
	
	@Test
	public void testAddClient(){
		Controller controller = new Controller();
		
		controller.addClient(new FormEvent(this, "TesztUser", "TestPhoneNum", new Timestamp(Calendar.getInstance().getTimeInMillis()), "Audi", "R8", "AAA-000", "1999", "fekete", 13345));
		
		List<Client> clientList = new ArrayList<Client>();
		clientList = controller.getClients();
		assertEquals(clientList.get(0).getName(), "TesztUser");
	}
	
	@Test
	public void testAddCar(){
		Controller controller = new Controller();
		
		controller.addCar(new FormEvent(this, "TesztUser", "TestPhoneNum", new Timestamp(Calendar.getInstance().getTimeInMillis()), "Audi", "R8", "AAA-000", "1999", "fekete", 13345));
		
		List<Car> carList = new ArrayList<Car>();
		carList = controller.getCars();
		assertEquals(carList.get(0).getColor(), "fekete");
	}
}
