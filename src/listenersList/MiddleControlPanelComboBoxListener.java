package listenersList;

import javax.swing.JComboBox;

import macroEditor.ComboBoxListener;
import macroEditor.Editor;
import macroEditor.LeftControlPanel;
import macroEditor.MiddleControlPanel;
import sequence.Macro;

public class MiddleControlPanelComboBoxListener extends ComboBoxListener {
	private MiddleControlPanel mcp;
	private LeftControlPanel lcp;
	public MiddleControlPanelComboBoxListener(JComboBox<?> comboBox, String corner, Editor editor,MiddleControlPanel mcp,LeftControlPanel lcp) {
		super(comboBox, corner, editor);
		// TODO Auto-generated constructor stub
		this.mcp=mcp;
		this.lcp=lcp;
	}
	@Override
	public void update(String newText) {
		// TODO Auto-generated method stub
		super.update(newText);
		Macro macro=this.editor.runner.macrosStorage.macrosList.get(lcp.secondTextData.getSelectedIndex()).macros.get(mcp.secondTextData.getSelectedIndex());
		if (newText!=null) {
			macro.tag=newText;
		} else {
			macro.tag="";
		}
		this.mcp.updateBox();
	}

}
