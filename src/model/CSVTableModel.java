package model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class CSVTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private final String[] columnNames;
    private ArrayList<String[]> Data = new ArrayList<String[]>();
    
    public CSVTableModel(int noOfColumns) {
    	super();
    	columnNames = new String[noOfColumns];
    	for(int i=0;i<noOfColumns;i++) {
    		if(i==0) {
    			columnNames[i]="Index";
    		}
    		else {
    			columnNames[i]="Movie " + i;
    		}
    	}
    }

    public void AddCSVData(ArrayList<String[]> DataIn) {
        this.Data = DataIn;
        this.fireTableDataChanged();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return Data.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        return Data.get(row)[col];
    }
    
    public void setvalue(String value, int row, int column) {
    	String[] tmpArray = Data.get(row);
    	tmpArray[column] = value;
    	Data.set(row, tmpArray);
        this.fireTableDataChanged();
    }
    
    public String[] getColumnNames() {
    	return columnNames;
    }
    
    public void addNewRow(String [] row) {
    	int index = getRowCount()+1;
    	row[0] = Integer.toString(index);
    	Data.add(row);
        this.fireTableDataChanged();
    }
    
    public String[] getRow(int index) {
    	if(index<Data.size()) {
    		return Data.get(index);
    	}
    	else {
    		return null;
    	}
    }
    
    public ArrayList<String[]> getData(){
    	return Data;
    }
}
