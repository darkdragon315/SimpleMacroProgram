package main;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import macroEditor.ProgramWindow;
import sequence.HotKeyRunner;

public class Main extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public static void main(String[] args) {
		File file = null;
		boolean value=false;
		JFileChooser selector = new JFileChooser();
		selector.setDialogTitle("Select your xml file!");
		selector.setCurrentDirectory(new java.io.File("."));
		selector.setAcceptAllFileFilterUsed(false);
		selector.addChoosableFileFilter(new FileNameExtensionFilter("Select your xml file","xml"));
		if (selector.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			file=selector.getSelectedFile();
			value=true;
		} else {
			System.out.println("No Selection ");

		}


		if (value==true) {
			new ProgramWindow(new HotKeyRunner(file),"menu",false,null);
		}





	}


}
