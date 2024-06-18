package sequence;

import java.awt.Robot;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import jacksonXML.ActionClass;

public class MacroRunnable implements Runnable {
	private Macro macro;
	private static ScheduledExecutorService executorService=Executors.newSingleThreadScheduledExecutor();
	private static Robot ROBOT;
	private boolean running;
	public MacroRunnable(Macro macro) {
		this.macro=macro;
	}
	
	static {
		Robot local = null;
		try {
			local = new Robot();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		ROBOT = local;

	}
	@Override
	public void run() {
		ActionClass action=macro.actions.get(macro.counter);
		if (macro.isRunning==false) {
			
		} else {
			switch (action.category) {
			case "wait":
				this.scheduleThis(action, 1);
				
				break;
			case "press":
				switch (action.type) {
				case "keyboard":
					ROBOT.keyPress(action.data);
					break;
				case "mouse button":
					ROBOT.mousePress(action.data);
					break;
				case "mouse wheel":
					ROBOT.mouseWheel(action.data);
					break;

				}
				this.running=true;
				this.scheduleThis(action, 0);
				break;
			

			}
			
		}
		if (this.running==true) {
			switch (action.category) {
			
			case "release":
				switch (action.type) {
				case "keyboard":
					ROBOT.keyRelease(action.data);
					break;
				case "mouse button":
					ROBOT.mouseRelease(action.data);
					break;
				case "mouse wheel":
					
					break;

				}
				this.running=false;
				this.scheduleThis(action, 0);
				

			}
		}
		
		
	}
	private void scheduleThis(ActionClass action,int type) {
		macro.counter+=1;
		if (macro.counter<macro.actions.size()) {
			
		} else {
			macro.isRunning=false;
		}
		if (type==1) {
			executorService.schedule(this,(int)action.data,TimeUnit.MILLISECONDS);
		} else {
			executorService.execute(this);
		}
	}

}
