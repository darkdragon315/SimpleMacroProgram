package macroEditor;

import java.awt.Container;
import java.awt.GridLayout;

import sequence.HotKeyRunner;

public class Editor extends Container {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GridLayout mainWidthLayout;
	public HotKeyRunner runner;
	public Editor(HotKeyRunner runner,ProgramWindow window) {
		// TODO Auto-generated constructor stub
		this.runner=runner;
		mainWidthLayout=new GridLayout(1,3);
		this.setLayout(mainWidthLayout);
		this.add(new LeftControlPanel(this,0,window));
		this.add(new MiddleControlPanel(this,1));
		this.add(new RightControlPanel(this,2,(MiddleControlPanel) this.getComponent(1)));
		for (int i=0;i<3;i++) {
			ControlPanel panel = null;
			if (i==0) {
				LeftControlPanel p=(LeftControlPanel) this.getComponent(i);
				panel=p;
			}
			if (i==1) {
				MiddleControlPanel p=(MiddleControlPanel) this.getComponent(i);
				panel=p;
			}
			if (i==2) {
				RightControlPanel p=(RightControlPanel) this.getComponent(i);
				panel=p;
			}
			if (i!=0) {
				panel.setAll("blocking",true);
				panel.setAll("texts",true);
			}
		}
	}
}
