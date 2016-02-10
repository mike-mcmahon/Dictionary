/*
 * Course: CCPS 305 Algorithms and Data Structures
 * Term: Fall 2015
 * Student Name: Mike McMahon, A.Sc.T.
 * Student ID: 500605231
 * Date: November 15, 2015
 * 
 * Assignment #2 - Write an algorithm that reads a text file and compiles a sorted list of
 * unique words.  The algorithm also includes a count of occurrences for each word.
 * 
 * => class DictionaryClient is used to created a SWT GUI for selecting the text file to read.  This class passes
 * the new file to the Dictionary object that then creates and displays the dictionary on the system console. <=
 * 
 * class methods:
 * 		-public static void main(String[] args)
 * 	
 * 		-public static void contents(Shell shell)
 * 	 
 */

import java.io.*;
import java.util.Map;
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class DictionaryClient 
{
	//data fields used by this class
	private static File selectedFile;
	private static File saveFile;
	private static Dictionary dictionary;
	private static PrintWriter out;
	private static boolean fileIsSelected;
	private static boolean fileIsProcessed;

	/*
	 * Main entry point for the application.
	 * Contains the boilerplate for creating the GUI and calling the method
	 * to populate the GUI with objects.
	 */
	public static void main(String[] args)
	{
		//SWT GUI Boilerplate
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Assignment 2 - Dictionary");
		
		//populate the GUI with meaningful objects
		contents(shell);
		
		shell.pack();
		shell.open();
		while(!shell.isDisposed())
		{
			if(!display.readAndDispatch())
			{
				display.sleep();
			}
		}
		display.dispose();
	}

	
	/*
	 * Method for opening, processing, and saving a file.
	 * When a text file is selected, the application parses the input
	 * and creates a dictionary of unique words with their occurrence counts.
	 */
	public static void contents(Shell shell)
	{
		//set layout to 3 columns width
		shell.setLayout(new GridLayout(3, true));
		
		//create a label and text display
		new Label(shell, SWT.NONE).setText("File Name:");
		Text fileName = new Text(shell, SWT.BORDER);
	    GridData data = new GridData(GridData.FILL_HORIZONTAL);
	    data.horizontalSpan = 3;
	    fileName.setLayoutData(data);
	    
	    //create Dictionary object and initialize boolean flags
	    dictionary = new Dictionary();
	    fileIsSelected = false;
	    fileIsProcessed = false;
	    
	    //create file open button and action listener
	    Button open = new Button(shell, SWT.PUSH);
	    open.setText("Select File...");
	    open.addSelectionListener(new SelectionAdapter() 
	    {
	    	public void widgetSelected(SelectionEvent event) 
	    	{
	    		// open a single file
	    		FileDialog dialog = new FileDialog(shell, SWT.OPEN);
	    	
	    		String file = dialog.open();
	    		if (file != null) 
	    		{
	    			fileName.setText("Opening File at: " + file);
	    			selectedFile = new File(file);
	    			fileIsSelected = true;
	    		}
	    	}
	    });
	    
	    //create process file button and action listener
	    Button process = new Button(shell, SWT.PUSH);
	    process.setText("Process File");
	    process.addSelectionListener(new SelectionAdapter()
	    {
	    	public void widgetSelected(SelectionEvent event)
	    	{
	    		//process a text file only if one is selected.
	    		//otherwise prompt user to select a file first.
	    		if(fileIsSelected)
	    		{
	    			dictionary.makeDictionary(selectedFile);
	    			fileName.setText("File is now processed, ready to save.");
	    			fileIsProcessed = true;
	    		}
	    		else
	    		{
	    			fileName.setText("Please select a file to process.");
	    		}
	    	}
		});
	    
	    //create save file button and action listener
	    Button save = new Button(shell, SWT.PUSH);
	    save.setText("Save File...");
	    save.addSelectionListener(new SelectionAdapter() 
	    {
	    	public void widgetSelected(SelectionEvent event) 
	    	{	
	    		//if a file is selected and processed, save the file
	    		if(fileIsSelected && fileIsProcessed)
	    		{
	    			//save a file 
	    			FileDialog dialog = new FileDialog(shell, SWT.SAVE);
	    			String file = dialog.open();
	    			if (file != null) 
	    			{
	    				fileName.setText("Saving file at: " + file);
	    				saveFile = new File(file);
	    				try
	    				{
	    					out = new PrintWriter(saveFile);
	    					
	    					//print each dictionary term to a text file.
	    					for(Map.Entry<String, Integer> entry : dictionary.getTree().entrySet())
	    		        	{
	    					out.printf("%-25s %s %d times\n", entry.getKey(), "occurs", entry.getValue());
	    		        	}
	    				}
	    				catch (IOException e)
	    				{
	    				System.out.println("Invalid Filename");
	    				}
	    				finally
	    				{
	    				out.close();
	    				}
	    				
	    				//reset fileIsSelected and fileIsProcessed so another file can be processed
	    				fileIsSelected = false;
	    				fileIsProcessed = false;
	    			}
	    		}
	    		else if(fileIsSelected && !fileIsProcessed)
	    		{
	    			fileName.setText("Please process the file.");
	    		}
	    		else
	    		{
	    			fileName.setText("Please select a file to process.");
	    		}
	    	}
	    });
	  }
}
