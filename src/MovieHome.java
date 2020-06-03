import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.CSVFile;
import model.CSVTableModel;
import model.FileTypeFilter;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.LineBorder;

import au.com.bytecode.opencsv.CSVWriter;

import java.awt.Color;

public class MovieHome extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final int SAVE = 0;
	private static final int SAVE_AS = 1;
	
	private String title = "Movie Manager";
	
	// Children of JFrame(MovieHome)
	private JPanel contentPane;
	
	// Children of contentPane
	private JPanel topPanel;
	private JScrollPane tablePane;
	
	// Children of topPanel
	private JPanel labelPanel;
	private JPanel buttonPanel_01;
	private JPanel buttonPanel_02;
	private JPanel buttonPanel_03;
	
	// Children of labelPanel
	private JLabel label_01;
	private JLabel label_02;

	// Children of buttonPanel_01
	private JTextField searchBox;
	private JButton searchBtn;
	private JButton saveBtn;
	private JButton saveAsBtn;
	private JButton editBtn;
	private JButton addNewBtn;
	private JButton addNewColumnBtn;
	private JButton openBtn;
	private JButton createBtn;
	
	// Children of tablePane
	private JTable table;
	
	// CSV File chooser
	private JFileChooser fileChooser;
	private File DataFile;
	
	// Data Model
	private CSVTableModel NewModel;
	private int noOfColumns = 0;

	/**
	 * Create the frame.
	 */
	public MovieHome() {
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 650);
		Image icon = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/MovieManager.png"));  
		setIconImage(icon);
		
		contentPane = new JPanel();
		setContentPane(contentPane);
		// Add a border to the JPanel
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		// Align child items along with the y-axis
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		
		topPanel = new JPanel();
		// Aligns child items in a row from the center
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.PAGE_AXIS));
		topPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		topPanel.setPreferredSize(new Dimension(800,200));
		contentPane.add(topPanel);
		
		
		labelPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.PAGE_AXIS));
		labelPanel.setPreferredSize(new Dimension(800,50));
		topPanel.add(labelPanel);
		
		label_01 = new JLabel();
		label_01.setText("*To edit a movie name please select a cell and click edit");
		labelPanel.add(label_01);
		
		label_02 = new JLabel();
		label_02.setText("*For search missing DVD/CD search '*'");
		labelPanel.add(label_02);
		
		buttonPanel_01 = new JPanel();
		buttonPanel_01.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		buttonPanel_01.setPreferredSize(new Dimension(800,50));		
		topPanel.add(buttonPanel_01);
		
		createBtn = new JButton();
		createBtn.setText("Create");
		createBtn.addActionListener(new ActionListener(){
			@Override
		    public void actionPerformed(ActionEvent e) {
		        System.out.println("Create button was clicked");
				searchBox.setEnabled(false);
				searchBtn.setEnabled(false);
				addNewColumnBtn.setEnabled(false);
				addNewBtn.setEnabled(false);
				editBtn.setEnabled(false);
		        createCSVFile();
		    }
		});
				
		openBtn = new JButton();
		openBtn.setText("Open");
		openBtn.addActionListener(new ActionListener(){
			@Override
		    public void actionPerformed(ActionEvent e) {
		        System.out.println("Open button was clicked");
				searchBox.setEnabled(false);
				searchBtn.setEnabled(false);
				addNewColumnBtn.setEnabled(false);
				addNewBtn.setEnabled(false);
				editBtn.setEnabled(false);
		        openFileChooser();
		    }
		});
		
		saveBtn = new JButton();
		saveBtn.setText("Save");
		saveBtn.addActionListener(new ActionListener(){
			@Override
		    public void actionPerformed(ActionEvent e) {
		        System.out.println("Save Button was clicked");
		        writeToCSV(NewModel, SAVE);
		    }
		});
		
		saveAsBtn = new JButton();
		saveAsBtn.setText("Save As");
		saveAsBtn.addActionListener(new ActionListener(){
			@Override
		    public void actionPerformed(ActionEvent e) {
		        System.out.println("SaveAs Button was clicked");
		        writeToCSV(NewModel, SAVE_AS);
		    }
		});
		
		buttonPanel_01.add(createBtn);
		buttonPanel_01.add(openBtn);
		buttonPanel_01.add(saveBtn);
		buttonPanel_01.add(saveAsBtn);
		
		buttonPanel_02 = new JPanel();
		buttonPanel_02.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		buttonPanel_02.setPreferredSize(new Dimension(800,50));		
		topPanel.add(buttonPanel_02);
		
		addNewBtn = new JButton();
		addNewBtn.setText("Add New Entry");
		addNewBtn.addActionListener(new ActionListener(){
			@Override
		    public void actionPerformed(ActionEvent e) {
		        System.out.println("Add new button was clicked");
		        try {
			        int no_OfColumns = Integer.parseInt(JOptionPane.showInputDialog(null, "No of movies in the DVD(MAX:4, MIN:1)"));
			        if(no_OfColumns>noOfColumns-1) {
			        	no_OfColumns = noOfColumns-1;
			        }
			        else {
			        	// nothing to do
			        }
			        System.out.println(noOfColumns);
			        String[] tmpInputArray = new String[noOfColumns];
			        tmpInputArray[0] = "0";
			        String tmpInput = null;
			        for(int i=1;i<noOfColumns;i++) {
			        	tmpInput = null;
			        	if(i<=no_OfColumns) {
			        		tmpInput = JOptionPane.showInputDialog(null, "Enter movie name:");
			        		tmpInput = replaceReservedChars(tmpInput);
			        	}
			        	else {
			        		// nothing to do
			        	}
			        	tmpInputArray[i] = tmpInput;
			        }
			        NewModel.addNewRow(tmpInputArray);
			        table.repaint();
		        }
		        catch(Exception ex) {
		        	ex.printStackTrace();
		        }
		    }
		});
		
		editBtn = new JButton();
		editBtn.setText("Edit");
		editBtn.addActionListener(new ActionListener(){
			@Override
		    public void actionPerformed(ActionEvent e) {
		        int rowNo = table.getSelectedRow();
		        int columnNo = table.getSelectedColumn();
		        if(columnNo==0) {
		        	JOptionPane.showMessageDialog(null, "Index column can not be edited", "Error", JOptionPane.ERROR_MESSAGE);
					return;
		        }
				if(columnNo<0 || rowNo<0) {
					JOptionPane.showMessageDialog(null, "Please select the cell you want to edit");
					return;
				}
				else {
			        String editedMovieName = JOptionPane.showInputDialog("Edit Movie Name: ");
			        System.out.println("Updated movie name: " + editedMovieName);
			        try{
			        	if(editedMovieName != null) {
			        		editedMovieName = replaceReservedChars(editedMovieName);
				        	NewModel.setvalue(editedMovieName, rowNo, columnNo);
				        	table.repaint();
			        	}
			        	else {
			        		// nothing to do
			        	}
			        }
			        catch(Exception ex) {
			        	ex.printStackTrace();
			        }
				}
		    }
		});
		
		addNewColumnBtn = new JButton();
		addNewColumnBtn.setText("Add New Column");
		addNewColumnBtn.addActionListener(new ActionListener(){
			@Override
		    public void actionPerformed(ActionEvent e) {
		        System.out.println("Add new column button was clicked");
		        if(JOptionPane.showConfirmDialog(null,"Do you realyy want to add a new column")==0) {
			        addNewColumnToCSV();
		        }
		        else {
		        	// nothing to do
		        }
		    }
		});

		addNewColumnBtn.setEnabled(false);
		addNewBtn.setEnabled(false);
		editBtn.setEnabled(false);
		buttonPanel_02.add(addNewBtn);
		buttonPanel_02.add(editBtn);
		buttonPanel_02.add(addNewColumnBtn);
		
		buttonPanel_03 = new JPanel();
		buttonPanel_03.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		buttonPanel_03.setPreferredSize(new Dimension(800,50));		
		topPanel.add(buttonPanel_03);
		
		ActionListener searchAction = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				searchMovies(searchBox.getText().toLowerCase());
			}
		};
		
		searchBox = new JTextField(30);
		searchBox.addActionListener(searchAction);
		
		searchBtn = new JButton();
		searchBtn.setText("Search");
		searchBtn.addActionListener(searchAction);

		searchBtn.setEnabled(false);
		searchBox.setEnabled(false);
		buttonPanel_03.add(searchBox);
		buttonPanel_03.add(searchBtn);
		
		table = new JTable(new CSVTableModel(noOfColumns));
		table.setPreferredScrollableViewportSize(new Dimension(700, 70));
        table.setFillsViewportHeight(true);
        tablePane = new JScrollPane(table);
        tablePane.setPreferredSize(new Dimension(800,500));
        contentPane.add(tablePane);
        
	}
	
	private String replaceReservedChars(String text) {
		text = text.replace(",", "");
		text = text.replace("*", "");
		return text;
	}
	
	private void createCSVFile() {
		try {
			noOfColumns = Integer.parseInt(JOptionPane.showInputDialog("Enter maximum allowed movies in an entry:"))+1;
			File file = openSaveFileChooser(null);
			if(file!=null) {
				String filename = file.getName();
				if(filename.contains(".")) {
					if (!filename.endsWith(".csv")) {
						JOptionPane.showMessageDialog(null, "Invalid file type", "Error", JOptionPane.ERROR_MESSAGE);
					}
					else {
						//  nothing to do
					}
				}
				else {
					file = new File(file.toString() + ".csv");
				}		        
				FileWriter outputfile = new FileWriter(file); 
		        CSVWriter writer = new CSVWriter(outputfile);
				writer.close();
				DataFile = file;	
				this.setTitle(DataFile.getPath());
				setFirstReadFromCSV(noOfColumns);			
			}
			else {
				JOptionPane.showMessageDialog(null, "Select a destination and give a name", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void openFileChooser() {
		fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileTypeFilter(".csv", "Comma Seperated Value file"));
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			DataFile = fileChooser.getSelectedFile();
			this.setTitle(DataFile.getPath());
			readFromCSV();
		}
		else {
			// nothing to do
		}
	}
	
	private File openSaveFileChooser(File prevFile) {
		File res = null;
		fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileTypeFilter(".csv", "Comma Seperated Value file"));
        fileChooser.setCurrentDirectory(prevFile);
		int result = fileChooser.showSaveDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			res = fileChooser.getSelectedFile();
		}
		else {
			// nothing to do
		}
		return res;
	}
	
	private void addNewColumnToCSV() {
		noOfColumns++;
		CSVFile Rd = new CSVFile();
		Rd.setNoOfColumns(noOfColumns);
        ArrayList<String[]> Rs2 = Rd.ReadCSVfile(DataFile);
        NewModel = new CSVTableModel(noOfColumns);
        table.setModel(NewModel);
        NewModel.AddCSVData(Rs2);
        searchBox.setEnabled(true);
        searchBtn.setEnabled(true);
        addNewColumnBtn.setEnabled(true);
		addNewBtn.setEnabled(true);
		editBtn.setEnabled(true);
	}
	
	private void setFirstReadFromCSV(int columns) {
		CSVFile Rd = new CSVFile();
        ArrayList<String[]> Rs2 = Rd.ReadCSVfile(DataFile);
        NewModel = new CSVTableModel(columns);
        table.setModel(NewModel);
        NewModel.AddCSVData(Rs2);
        searchBox.setEnabled(true);
        searchBtn.setEnabled(true);
        addNewColumnBtn.setEnabled(true);
		addNewBtn.setEnabled(true);
		editBtn.setEnabled(true);
	}
	
	private void readFromCSV() {
		CSVFile Rd = new CSVFile();
        ArrayList<String[]> Rs2 = Rd.ReadCSVfile(DataFile);
        noOfColumns = Rd.getNoOfColumns();
        NewModel = new CSVTableModel(noOfColumns);
        table.setModel(NewModel);
        NewModel.AddCSVData(Rs2);
        searchBox.setEnabled(true);
        searchBtn.setEnabled(true);
        addNewColumnBtn.setEnabled(true);
		addNewBtn.setEnabled(true);
		editBtn.setEnabled(true);
	}
	
	private void writeToCSV(CSVTableModel csvModel, int type) {
		try {
			File file = null;
			if(type == SAVE) {
				file = DataFile;
			}
			else {
				file = openSaveFileChooser(DataFile);
			}
			if(file!=null) {
				String filename = file.getName();
				if(filename.contains(".")) {
					if (!filename.endsWith(".csv")) {
						JOptionPane.showMessageDialog(null, "Invalid file type", "Error", JOptionPane.ERROR_MESSAGE);
					}
					else {
						//  nothing to do
					}
				}
				else {
					file = new File(file.toString() + ".csv");
				}		        
				FileWriter outputfile = new FileWriter(file); 
		        CSVWriter writer = new CSVWriter(outputfile); 

				int rows = csvModel.getRowCount();
		        
		        for(int i=0; i<rows; i++) {
		        	writer.writeNext(csvModel.getRow(i));
		        }
				writer.close();
				DataFile = file;	
				this.setTitle(DataFile.getPath());
				readFromCSV();			
			}
			else {
				JOptionPane.showMessageDialog(null, "Select a destination and give a name", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void searchMovies(String search) {
		ArrayList<String[]> tempData = NewModel.getData();
		ArrayList<String[]> resultData  = new ArrayList<String[]>();
		for(String [] row: tempData) {
			for(String column: row) {
				if(column == null) {
					continue;
				}				
				if(column.toLowerCase().contains(search)) {
					System.out.println(column);
					if(!resultData.contains(row)) {
						resultData.add(row);
					}
				}
			}
		}

		JFrame newJFrame = new JFrame();
		newJFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		newJFrame.setTitle("Search Results");
		newJFrame.setBounds(100, 100, 800, 650);
		
		JPanel new_contentPane = new JPanel();
		newJFrame.setContentPane(new_contentPane);
		new_contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));		
		new_contentPane.setLayout(new BoxLayout(new_contentPane, BoxLayout.PAGE_AXIS));
				
		JTable new_table = new JTable(new CSVTableModel(noOfColumns));
		new_table.setPreferredScrollableViewportSize(new Dimension(700, 70));
		new_table.setFillsViewportHeight(true);
        JScrollPane new_tablePane = new JScrollPane(new_table);
        new_tablePane.setPreferredSize(new Dimension(800,500));
        new_contentPane.add(new_tablePane);
        
        CSVTableModel new_NewModel = new CSVTableModel(noOfColumns);
        new_table.setModel(new_NewModel);
        new_NewModel.AddCSVData(resultData);
		newJFrame.setVisible(true);
	}

}
