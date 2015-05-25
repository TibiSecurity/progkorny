package view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.Car;
import model.Client;
import controller.Controller;

public class ClientTableModel extends AbstractTableModel {
	private List<Client> db;
	private List<Car> dbC;
	
	private String[] colNames = { "Név", "Telefonszám", "Autó márka", "Autó típus", "Rendszám", "Évjárat", "Hozzáadás dátuma", "Módosítás dátuma" };
	
	public void ClientTableModel(){
		
	}
	
	public String getColumnName(int column) {
		return colNames[column];
	}
	
	@Override
	public void setValueAt(Object value, int row, int col) {
		if( db == null )
			return;
		
		Client client = db.get(row);
		
		switch(col){
			case 0:
				client.setName((String)value);
				break;
			case 1:
				client.setPhoneNum((String)value);
				break;
			default:
				return;
		}
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		switch(col){
			case 0:
				return true;
			case 1:
				return true;
			default:
				return false;
		}
	}

	public void setData(List<Client> db){
		this.db = db;
	}
	
	public void setCarData(List<Car> dbC){
		this.dbC = dbC;
	}
	
	public int getColumnCount() {
		return 8;
	}

	public int getRowCount() {
		return db.size();
	}

	public Object getValueAt(int row, int col) {
		Client cl = db.get(row);
		Client client = new Client(0, "", "", null, null);
		if( !cl.isMarkedForDeletion() )
			client = cl;
			
		Car car = new Car("", "", "", client.getName(), "", "", 0);
		
		for( Car carToCheck : dbC )
			if( carToCheck.getClientName().equals(client.getName()) )
				car = carToCheck;
		
		switch( col ){
			case 0: return client.getName();
			case 1: return client.getPhoneNum();
			case 2: return car.getManufacturerName();
			case 3: return car.getModelName();
			case 4: return car.getLicensePlateNo();
			case 5: return car.getYear();
			case 6:	return client.getCreationDate();
			case 7:	return client.getModificationDate();
		}
		
		return null;
	}

}
