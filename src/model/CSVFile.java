package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class CSVFile {
    private final ArrayList<String[]> Rs = new ArrayList<String[]>();
    private String[] OneRow;
    private int noOfColumns = 0;
    private int remaining;
    private int length;
    
    public ArrayList<String[]> ReadCSVfile(File DataFile) {
        try {
            BufferedReader brd = new BufferedReader(new FileReader(DataFile));
            ArrayList<String[]> temp_Rs = new ArrayList<String[]>();
            String[] tempOneRow_01;
                
            int tempLength;
            while (brd.ready()) {
                String st = brd.readLine();
                
                tempOneRow_01 = st.replace("\"", "").split(",");
                
                tempLength = tempOneRow_01.length;
                if(tempLength<=0) {
                	continue;
                }
                else {
                    if(tempLength>noOfColumns) {
                    	noOfColumns = tempLength;
                    }
                    else {
                    	// nothing to do
                    }
                }
                temp_Rs.add(tempOneRow_01);
            }
            brd.close();
            
            for(String [] tempOneRow_02: temp_Rs) {
                OneRow = new String[noOfColumns];
                int tempIndex = 0;
                for(String itr: tempOneRow_02) {
                	OneRow[tempIndex++] = itr;
                }
                length = OneRow.length;
                remaining = noOfColumns-length;
                if(remaining>0) {
                	for(int i=1;i<remaining;i++) {
                		System.out.println(i+length);
                		OneRow[i+length] = new String();
                	}
                }
                Rs.add(OneRow);
            }
            System.out.println(noOfColumns);
        }
        catch (Exception e) {
            String errmsg = e.getMessage();
            System.out.println("File not found:" + errmsg);
            JOptionPane.showMessageDialog(null, "File structure is not matched", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return Rs;
    }
    
    public void setNoOfColumns(int columns) {
    	noOfColumns = columns;
    }
    
    public int getNoOfColumns() {
    	return this.noOfColumns;
    }
}