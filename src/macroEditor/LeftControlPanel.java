package macroEditor;


import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import jacksonXML.MacroList;
import jacksonXML.MacrosStorage;
import listenersList.LeftControlPanelComboBoxListener;
import main.XmlClassObjectReader;

public class LeftControlPanel extends ControlPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MiddleControlPanel mcp;
	private boolean start;
	protected MacroList selectedMacroList;
	private ProgramWindow window;
	private JButton saveButton;
	public LeftControlPanel(Editor editor, int id,ProgramWindow window) {
		super(editor, id);
		this.window=window;
		
	}

	@Override
	public void setup() {
		this.saveButton=new JButton("save");
		start=false;
		new LeftControlPanelComboBoxListener(this.secondTextData,": ",this.editor,this);
		//here is the setup for the comboBox
	    if (this.updateBox()==true) {
	    	this.secondTextData.setEditable(true);
	    	this.start=false;
	    } else {
	    	this.start=true;
	    	this.upgrade.setIsBlocked(this.secondTextData,true);
	    }
	    this.saveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
	}
	@Override
	public void create() {
		if (this.start==true) {
			this.start=false;
			this.upgrade.setIsBlocked(this.secondTextData,false);
			this.secondTextData.setEditable(true);
		}
		MacroList macroList=new MacroList();
		macroList.name="";
		this.editor.runner.macrosStorage.macrosList.add(macroList);
		this.updateBox();

	}

	@Override
	public boolean updateBox() {
		return this.upgrade.updateComboBox(secondTextData,this.editor.runner.macrosStorage,null,false);
		
	}
	@Override
	public void delete() {
		if (this.editor.runner.macrosStorage.macrosList.size()==0) {
			
		} else {
			this.editor.runner.macrosStorage.macrosList.remove(this.secondTextData.getSelectedIndex());
		}
		if (this.editor.runner.macrosStorage.macrosList.size()==0) {
			this.start=true;
			this.upgrade.setIsBlocked(this.secondTextData,true);
		}
		this.updateBox();
	}
	@Override
	public void confirm() {
		if (this.secondTextData.getItemCount()>0) {
			if (this.firstTimeStarter==true) {
				mcp=(MiddleControlPanel) this.editor.getComponent(1);
			}
			super.confirm();
			this.setAll("blocking",true);
			mcp.setAll("blocking",false);	
			mcp.upgrade.setIsBlocked(mcp.secondTextData,true);

			String text=(String) this.secondTextData.getSelectedItem();
			JLabel label=(JLabel) mcp.firstTextData;
			if (text.split(": ").length>1) {
				label.setText(text.split(": ")[1]);
			} else {
				
			}
			mcp.secondTextData.setEditable(false);
			selectedMacroList=this.editor.runner.macrosStorage.macrosList.get(this.secondTextData.getSelectedIndex());
			mcp.macros=selectedMacroList.macros;
			mcp.updateBox();
		}
		
	}
	@Override
	public void goBack() {
		this.window.switchToComponent("menu");
	}
	@Override
	protected void endSetup(Container container) {
		container.setLayout(new GridLayout(1,5));
		container.add(this.saveButton);
	}
	private void save() {
		XmlClassObjectReader reader=new XmlClassObjectReader();
		MacrosStorage test=this.editor.runner.macrosStorage;
		reader.write(test,this.editor.runner.path);
	}


}
