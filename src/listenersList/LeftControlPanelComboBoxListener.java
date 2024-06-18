package listenersList;

import javax.swing.JComboBox;

import macroEditor.ComboBoxListener;
import macroEditor.Editor;
import macroEditor.LeftControlPanel;

public class LeftControlPanelComboBoxListener extends ComboBoxListener {
	private LeftControlPanel lcp;
	public LeftControlPanelComboBoxListener(JComboBox<?> comboBox, String corner, Editor editor,LeftControlPanel lcp) {
		super(comboBox, corner, editor);
		// TODO Auto-generated constructor stub
		this.lcp=lcp;
	}
	@Override
	public void update(String newText) {
		// TODO Auto-generated method stub
		if (newText!=null) {
			this.editor.runner.macrosStorage.macrosList.get(this.comboBox.getSelectedIndex()).name=newText;
		} else {
			this.editor.runner.macrosStorage.macrosList.get(this.comboBox.getSelectedIndex()).name="";
		}
		
		this.lcp.updateBox();
		
	}

}
