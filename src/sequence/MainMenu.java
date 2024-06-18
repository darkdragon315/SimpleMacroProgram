package sequence;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import macroEditor.ComponentUpgrade;
import macroEditor.ProgramWindow;

public class MainMenu extends Container {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int lastComponentPosition=15;
	private JLabel label;
	private JComboBox<String> comboBox;

	private JButton runButton;
	private ArrayList<ComponentUpgrade> upgradeList;
	private ProgramWindow window;
	public MainMenu(ProgramWindow window) {
		this.window=window;
		JPanel program=new JPanel();
		program.setLayout(new GridLayout(lastComponentPosition,1));
		this.setLayout(new GridLayout(1,3));
		this.comboBox=new JComboBox<String>();
		this.upgradeList=new ArrayList<ComponentUpgrade>();
		this.upgradeList.add(new ComponentUpgrade());
		this.upgradeList.add(new ComponentUpgrade());
		this.upgradeList.add(new ComponentUpgrade());
		this.label=new JLabel("Do you wanna run the editor or execute your macros?");
		this.runButton=new JButton("run");
		comboBox.addItem("Editor");
		comboBox.addItem("Executor");
		this.runButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				run();
			}
		});
		program.add(this.label);
		program.add(this.comboBox);
		this.add(program);
		for (int i=program.getComponentCount();i<(lastComponentPosition-1);i++) {
			program.add(new JLabel());
		}
		program.add(runButton);
		this.upgradeList.get(0).addAutomaticResize(this.label);
		this.upgradeList.get(1).addAutomaticResize(this.comboBox);

	}
	private void run() {
		switch (this.comboBox.getSelectedIndex()) {
		case 0:
			this.window.switchToComponent("editor");
			break;
		case 1:
			this.window.switchToComponent("runner");
			break;


		}
	}
}
