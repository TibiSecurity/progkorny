package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;

import model.Client;
import model.Car;

public class TablePanel extends JPanel {
	
	private JTable clientTable;
	private ClientTableModel tableModel;
	private JPopupMenu popup;
	private ClientTableListener clientTableListener;
	
	public TablePanel(){
		tableModel = new ClientTableModel();
		clientTable = new JTable(tableModel);
		popup = new JPopupMenu();
		
		Border innerBorder = BorderFactory.createTitledBorder("Ügyféllista");
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
		
		clientTable.setRowHeight(20);
		
		JMenuItem removeItem = new JMenuItem("Törlés");
		popup.add(removeItem);
		
		clientTable.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				int row = clientTable.rowAtPoint(e.getPoint());
				clientTable.getSelectionModel().setSelectionInterval(row, row);
				
				if(e.getButton() == MouseEvent.BUTTON3){
					popup.show(clientTable, e.getX(), e.getY());
				}
			}
		});
		
		removeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = clientTable.getSelectedRow();
				
				if(clientTableListener != null){
					clientTableListener.rowDeleted(row);
					tableModel.fireTableRowsDeleted(row, row);
				}
			}
		});
		
		setLayout(new BorderLayout());
		
		add(new JScrollPane(clientTable), BorderLayout.CENTER);
	}
	
	public void setData(List<Client> db){
		tableModel.setData(db);
	}
	
	public void setCarData(List<Car> dbC){
		tableModel.setCarData(dbC);
	}
	
	public void refresh(){
		tableModel.fireTableDataChanged();
	}
	
	public void setClientTableListener(ClientTableListener listener){
		this.clientTableListener = listener;
	}
}
