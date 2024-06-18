package listenersList;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;

import macroEditor.RightControlPanel;


public class KeyTrackingInput implements NativeKeyListener, NativeMouseWheelListener,NativeMouseInputListener {
	private RightControlPanel panel;
	private ScheduledExecutorService service;
	public KeyTrackingInput(RightControlPanel panel,ScheduledExecutorService service) {
		this.panel=panel;
		this.service=service;
	}
	@Override
	public void nativeMousePressed(NativeMouseEvent e) {
		this.removeListeners();
		this.close();
		panel.runWithInput(e);
	}

	@Override
	public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {
		this.removeListeners();
		this.close();
		panel.runWithInput(e);
	}
	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		this.removeListeners();
		this.close();
		panel.runWithInput(e);
	}
	
	public void start() {
		panel.buttonKeySelector.setEnabled(false);
		service.schedule(new Runnable() {
			
			@Override
			public void run() {
				addListeners();
				JOptionPane.showMessageDialog(null,"Waiting for input...","test2",JOptionPane.CLOSED_OPTION);
			}
		},250L,TimeUnit.MILLISECONDS);
	}
	public void close() {
		JOptionPane.getRootFrame().dispose();
		panel.buttonKeySelector.setEnabled(true);
	}
	public void addListeners() {
		try {
			GlobalScreen.registerNativeHook();
		}
		catch(NativeHookException e) {
			e.printStackTrace();
		}
		GlobalScreen.addNativeKeyListener(this);
		GlobalScreen.addNativeMouseWheelListener(this);
		GlobalScreen.addNativeMouseListener(this);
		LogManager.getLogManager().reset();
		Logger.getLogger(GlobalScreen.class.getPackage().getName()).setLevel(Level.OFF);
	}
	public void removeListeners() {
		GlobalScreen.removeNativeKeyListener(this);
		GlobalScreen.removeNativeMouseListener(this);
		GlobalScreen.removeNativeMouseWheelListener(this);
		try {
			GlobalScreen.unregisterNativeHook();
		} catch (NativeHookException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void nativeMouseClicked(NativeMouseEvent e) {
	}
	@Override
	public void nativeMouseMoved(NativeMouseEvent e) {
	}
	@Override
	public void nativeMouseDragged(NativeMouseEvent e) {
	}
	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
	}
	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
	}
	@Override
	public void nativeMouseReleased(NativeMouseEvent e) {
	}
	
	
}
