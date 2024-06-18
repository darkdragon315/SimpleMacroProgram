package macroEditor;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import macroRunner.RunnerContainer;
import sequence.HotKeyRunner;
import sequence.MainMenu;

public class ProgramWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HotKeyRunner runner;
	private Editor editor;
	private RunnerContainer runnerWindow;
	private MainMenu menu;
	@SuppressWarnings("rawtypes")
	private ArrayList<Class> classList;
	private ProgramWindow frameNow;
	private int counter;
	@SuppressWarnings("rawtypes")
	public ProgramWindow(HotKeyRunner runner,String input,boolean value,ProgramWindow windowPast) {
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setTitle("SimpleMacroProgram by DarkDragon315");
		this.counter=0;
		this.classList=new ArrayList<Class>();
		this.classList.add(MainMenu.class);
		this.classList.add(RunnerContainer.class);
		this.classList.add(Editor.class);
		this.runner=runner;
		this.editor=new Editor(runner,this);
		this.runnerWindow=new RunnerContainer(runner,this);
		this.menu=new MainMenu(this);
		this.switchToComponent(input);
		this.frameNow=this;
		if (value==false) {
			this.setBiggestSize(this.frameNow);
		} else {
			this.setSize(windowPast.getSize());
			this.setLocation(windowPast.getLocation());
		}
		this.frameNow.setVisible(true);
	}
	private void updateFrame(int id,String input) {
		switch (id) {
		case 0:
			if (input.equals("add")) {
				this.add(editor);
			} else {
				if (input.equals("delete")) {
					this.resetWindow();
				}
			}
			break;
		case 1:
			if (input.equals("add")) {
				this.add(runnerWindow);
			} else {
				if (input.equals("delete")) {
					this.resetWindow();
				}
			}
			break;
		case 2:
			if (input.equals("add")) {
				this.add(menu);
			} else {
				if (input.equals("delete")) {
					this.resetWindow();
				}
			}
			break;



		}
	}
	@SuppressWarnings("rawtypes")
	private void resetWindow() {
		for (Class clasz:classList) {
			for (int i=0;i<this.getComponentCount();i++) {
				if (this.getComponent(i).getClass()==clasz) {
					this.remove(i);
				}
			}
		}
	}
	public void switchToComponent(String input) {
		this.setVisible(false);
		switch (input) {
		case "editor":
			this.updateFrame(0,"delete");
			this.updateFrame(0,"add");
			break;
		case "runner":
			this.updateFrame(1,"delete");
			this.updateFrame(1,"add");
			this.runnerWindow.updateThis();
			break;
		case "menu":
			this.updateFrame(2,"delete");
			this.updateFrame(2,"add");
			break;
		}
		if (counter>0) {
			this.frameNow=new ProgramWindow(runner,input,true,this);
			this.frameNow.setVisible(true);
		}
		this.counter+=1;

	}
	private void setBiggestSize(JFrame frame) {
		GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		Rectangle border = graphicsDevice.getDefaultConfiguration().getBounds();
		Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(graphicsDevice.getDefaultConfiguration());

		Rectangle border2 = new Rectangle(border);
		border2.x += insets.left;
		border2.y += insets.top;
		border2.width -= (insets.left + insets.right);
		border2.height -= (insets.top + insets.bottom);
		frame.setSize(border2.width,border2.height);
	}

}
