package listenersList;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

import sequence.HotKeyRunner;

public class MouseWheelCountdownListener extends KeyAdapter {
	private JTextField field;
	private HotKeyRunner runner;
	public MouseWheelCountdownListener(JTextField field,HotKeyRunner runner) {
		this.runner=runner;
		this.field=field;
		this.field.addKeyListener(this);
	}
	@Override
	public void keyReleased(KeyEvent e) {
		Integer cooldown=Integer.parseInt(this.field.getText());
		runner.macrosStorage.mouseCooldown=cooldown;
	}

}
