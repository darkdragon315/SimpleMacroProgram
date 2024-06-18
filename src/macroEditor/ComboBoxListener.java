package macroEditor;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.AbstractButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

public class ComboBoxListener extends KeyAdapter {
	protected JComboBox<?> comboBox;
	private String corner;
	protected Editor editor;
	public ComboBoxListener(JComboBox<?> comboBox,String corner,Editor editor) {
		// TODO Auto-generated constructor stub
		this.comboBox=comboBox;
		this.corner=corner;
		this.comboBox.getEditor().getEditorComponent().addKeyListener(this);
		this.editor=editor;
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		this.setSwitchButtonIsEnabled(comboBox, false);
		int mousePosition=( (JTextField)comboBox.getEditor().getEditorComponent() ).getCaretPosition();
		String wholeText=((JTextComponent)this.comboBox.getEditor().getEditorComponent()).getText();
		if (mousePosition==0) {
			( (JTextField)comboBox.getEditor().getEditorComponent() ).setCaretPosition(wholeText.length());
		}
		mousePosition=( (JTextField)comboBox.getEditor().getEditorComponent() ).getCaretPosition();
		int newLength;
		int startTextLength=wholeText.split(corner)[0].length()+2;
		if (e.getKeyCode()==KeyEvent.VK_BACK_SPACE) {
			newLength=wholeText.length()-1;
		} else {
			newLength=wholeText.length();
		}
		if (mousePosition>startTextLength&&newLength>=startTextLength) {
			this.setJComboBoxToDefault(comboBox);
		} else {
			if (e.getKeyCode()!=KeyEvent.VK_BACK_SPACE&&mousePosition>startTextLength-1) {
				this.setJComboBoxToDefault(comboBox);
			} else {
				this.setJComboBoxReadOnly(comboBox);
			}
		}
		if (e.getKeyCode()==46) {
			this.setJComboBoxReadOnly(comboBox);
		}
		


	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		this.setSwitchButtonIsEnabled(comboBox,true);
		super.keyReleased(e);
		String wholeText=((JTextComponent)this.comboBox.getEditor().getEditorComponent()).getText();
		String[] newTextArray=wholeText.split(corner);
		String newText = null;
		if (newTextArray.length>1) {
			newText=newTextArray[1];
		}
		this.update(newText);
	}
	public void update(String newText) {
		
	}
	public void setJComboBoxReadOnly(JComboBox<?> jcb)
	{
		JTextField jtf = (JTextField)jcb.getEditor().getEditorComponent();
		jtf.setEditable(false);
		this.setSwitchButtonIsEnabled(jcb, true);

	}
	public void setJComboBoxToDefault(JComboBox<?> jcb) {
		JTextField jtf = (JTextField)jcb.getEditor().getEditorComponent();
		jtf.setEditable(true);
	}
	public void setSwitchButtonIsEnabled(JComboBox<?> jcb,boolean value) {
		Component[] componentList = jcb.getComponents();
		for (Component component : componentList)
		{
			if (component instanceof AbstractButton)
			{
				AbstractButton ab = (AbstractButton)component;
				ab.setEnabled(value);


			}
		}
	}


}
