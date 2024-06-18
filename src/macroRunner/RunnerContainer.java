package macroRunner;

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
import sequence.HotKeyRunner;

public class RunnerContainer extends Container {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HotKeyRunner runner;
	private int lastComponentPosition=15;
	private JLabel label;
	private JComboBox<String> comboBox;

	private JButton backButton;
	private JButton runButton;
	private JButton stopButton;
	private ComponentUpgrade upgrade;
	private ProgramWindow programWindow;
	
	private ArrayList<ComponentUpgrade> upgradeList;
	public RunnerContainer(HotKeyRunner runner,ProgramWindow programWindow) {
		this.upgradeList=new ArrayList<ComponentUpgrade>();
		this.upgradeList.add(new ComponentUpgrade());
		this.upgradeList.add(new ComponentUpgrade());
		this.upgradeList.add(new ComponentUpgrade());
		this.programWindow=programWindow;
		JPanel program=new JPanel();
		program.setLayout(new GridLayout(lastComponentPosition,1));
		this.setLayout(new GridLayout(1,3));
		this.comboBox=new JComboBox<String>();
		this.upgrade=new ComponentUpgrade();
		this.label=new JLabel("Select your macrolist that you wanna run!");
		this.backButton=new JButton("go back");
		this.runButton=new JButton("run");
		this.stopButton=new JButton("stop");
		this.runner=runner;
		this.stopButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				stop();
			}
		});
		this.runButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				run();
			}
		});
		this.backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				back();
			}
		});
		this.runner=runner;
		Container containerlast=new Container();
		containerlast.setLayout(new GridLayout(1,3));
		containerlast.add(backButton);
		containerlast.add(runButton);
		containerlast.add(stopButton);
		program.add(this.label);
		program.add(this.comboBox);
		this.add(program);
		for (int i=program.getComponentCount();i<(lastComponentPosition-1);i++) {
			program.add(new JLabel());
		}
		program.add(containerlast);
		this.upgradeList.get(0).addAutomaticResize(this.label);
		this.upgradeList.get(1).addAutomaticResize(this.comboBox);

	}
	public void updateThis() {
		this.upgrade.updateComboBox(this.comboBox,this.runner.macrosStorage,null,false);
	}
	private void run() {
		if (this.comboBox.getItemCount()>0) {
			this.runner.update(this.comboBox.getSelectedIndex());
		}
	}
	private void stop() {
		this.runner.removeListeners();
	}
	private void back() {
		this.stop();
		this.programWindow.switchToComponent("menu");
	}

}
