package macroEditor;

import java.util.ArrayList;

import javax.swing.JLabel;

import listenersList.MiddleControlPanelComboBoxListener;
import sequence.Macro;

@SuppressWarnings("serial")
public class MiddleControlPanel extends ControlPanel {
	LeftControlPanel anotherPanel;
	RightControlPanel rcp;
	private boolean start;
	private Macro macro;
	protected ArrayList<Macro> macros;
	public MiddleControlPanel(Editor editor, int id) {
		super(editor, id);
	}
	@Override
	public void setup() {
		macro=new Macro();
		anotherPanel=(LeftControlPanel) this.editor.getComponent(0);
		new MiddleControlPanelComboBoxListener(this.secondTextData,": ",this.editor,this,anotherPanel);
		this.start=false;
	}
	@Override
	public void goBack() {
		this.setAll("blocking",true);
		anotherPanel.setAll("blocking",false);
		this.setAll("texts",false);
	}
	@Override
	public void create() {
		Macro macro=new Macro();
		macro.tag="";
		if (anotherPanel.selectedMacroList.macros==null) {
			anotherPanel.selectedMacroList.macros=new ArrayList<Macro>();

		}
		if (this.start==true) {
			this.start=false;
			this.upgrade.setIsBlocked(this.secondTextData,false);
		}
		anotherPanel.selectedMacroList.macros.add(macro);
		macros=anotherPanel.selectedMacroList.macros;
		this.updateBox();
	}
	@Override
	public boolean updateBox() {
		boolean value=this.upgrade.updateComboBox(this.secondTextData,anotherPanel.selectedMacroList,null,false);
		if (value==true) {
			this.secondTextData.setEditable(true);
			this.upgrade.setIsBlocked(this.secondTextData,false);
		}
		return value;

	}
	@Override
	public void delete() {
		if (macros.size()==0) {
			
		} else {
			macros.remove(this.secondTextData.getSelectedIndex());
		}
		if (macros.size()==0) {
			this.start=true;
			this.upgrade.setIsBlocked(this.secondTextData,true);
		}
		
		this.updateBox();
	}
	@Override
	public void confirm() {
		super.confirm();
		if (this.secondTextData.getItemCount()>0) {
			rcp=(RightControlPanel) this.editor.getComponent(2);
			this.setAll("blocking",true);
			rcp.setAll("blocking",false);
			rcp.secondTextData.setEditable(false);
			rcp.keyList.setEditable(false);
			rcp.selector2.setEditable(false);

			String text=(String) this.secondTextData.getSelectedItem();
			JLabel label=(JLabel) rcp.firstTextData;

			if (text.split(": ").length>1) {
				label.setText(text.split(": ")[1]);
			} else {
			}
			this.rcp.selectedMacro=macros.get(this.secondTextData.getSelectedIndex());
			this.upgrade.updateComboBox(rcp.secondTextData,macro,null,false);
		}
		
	}

}
