package listenersList;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import macroEditor.RightControlPanel;
import sequence.Macro;

public class DelayListener extends KeyAdapter {
	private JTextField field;
	private JComboBox<String> keyList;
	private RightControlPanel rcp;
	public DelayListener(JTextField field, JComboBox<String> keyList,RightControlPanel rcp) {
		this.field=field;
		this.field.addKeyListener(this);
		this.keyList=keyList;
		this.rcp=rcp;
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if (field.isEnabled()==true) {
			Macro macro=rcp.selectedMacro;
			Integer delay=Integer.parseInt(this.field.getText());
			macro.actions.get(keyList.getSelectedIndex()).data=delay;
		}
		
	}
}
