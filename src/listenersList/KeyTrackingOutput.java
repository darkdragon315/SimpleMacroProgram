package listenersList;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import macroEditor.RightControlPanel;


public class KeyTrackingOutput implements KeyListener, MouseWheelListener, MouseListener{
	private JFrame frame;
	private RightControlPanel panel;
	private ScheduledExecutorService service;
	public KeyTrackingOutput(RightControlPanel panel,ScheduledExecutorService service) {
		this.frame=new JFrame();
		this.frame.setSize(new Dimension(200,200));
		this.panel=panel;
		this.service=service;
		this.frame.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent e) {
		        stop();
		    }
		});
	}
	@Override
	public void mousePressed(MouseEvent e) {
		this.stop();
		panel.runWithInput(e);
	}
	@Override
	public void keyPressed(KeyEvent e) {
		this.stop();
		panel.runWithInput(e);
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		this.stop();
		panel.runWithInput(e);
	}
	public void start() {
		this.frame.addKeyListener(this);
		this.frame.addMouseListener(this);
		this.frame.addMouseWheelListener(this);
		this.service.schedule(new Runnable() {
			
			@Override
			public void run() {
				panel.buttonKeySelector.setEnabled(false);
			    frame.setVisible(true);
			}
		},750,TimeUnit.MILLISECONDS);
	}
	public void stop() {
		this.frame.removeKeyListener(this);
		this.frame.removeMouseWheelListener(this);
		this.frame.removeMouseListener(this);
		panel.buttonKeySelector.setEnabled(true);
		this.frame.setVisible(false);
	}
	
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}
	@Override
	public void keyReleased(KeyEvent e) {
	}

}
