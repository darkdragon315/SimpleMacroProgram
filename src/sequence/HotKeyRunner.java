package sequence;

import com.sun.jna.Library;
import com.sun.jna.Native;

import jacksonXML.MacroList;
import jacksonXML.MacrosStorage;
import main.XmlClassObjectReader;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;




@SuppressWarnings("deprecation")
public class HotKeyRunner implements NativeKeyListener, NativeMouseWheelListener,NativeMouseInputListener {
	private final static User32 USER_32;
	private XmlClassObjectReader reader;
	private MacroList programList;
	public HashMap<Integer,CountdownTimer> timerList=new HashMap<Integer, CountdownTimer>();

	private Macro macropast;
	public MacrosStorage macrosStorage;
	public File path;
	public int 	macroID;
	public HotKeyRunner(File path) {
		this.path=path;
		reader=new XmlClassObjectReader();
		if (this.setup()==true) {
			timerList.put(-1,new CountdownTimer(this));
			timerList.put(1,new CountdownTimer(this));
			//this update & addListeners
		}
		this.setup();
	}
	static {

		//ROBOT = local;
		USER_32 = (User32) Native.loadLibrary("user32", User32.class);
		unblock();
	}


	public void update(int macroID) {
		this.removeListeners();
		if (macrosStorage!=null) {
			for (MacroList macroList:macrosStorage.macrosList) {
				for (Macro macro:macroList.macros) {
					macro.updateKeyArray();
				}
			}
		}
		programList=macrosStorage.macrosList.get(macroID);
		this.macroID=macroID;
		this.addListeners();


	}
	public boolean setup() {
		try {
			macrosStorage=(MacrosStorage) reader.read(MacrosStorage.class,this.path);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (macrosStorage!=null) {
			if (macrosStorage.macrosList==null) {
				macrosStorage.macrosList=new ArrayList<MacroList>();
			}
			for (MacroList macroList:macrosStorage.macrosList) {
				if (macroList.macros!=null) {
					for (Macro macro:macroList.macros) {
						macro.setup(this);
					}
				} else {
					macroList.macros=new ArrayList<Macro>();
					for (Macro macro:macroList.macros) {
						macro.setup(this);
					}
				}
			}
			return true;
		} else {
			macrosStorage=new MacrosStorage();
			macrosStorage.mouseCooldown=200;
			macrosStorage.macrosList=new ArrayList<MacroList>();
			return true;
		}

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

	private static interface User32 extends Library {
		boolean BlockInput(boolean block);
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		startofEvent(e.getKeyCode(),0);

	}

	// tpye is the position in the array that starts at 0
	public void startofEvent(int keyCode,int type) {
		for (Macro macro:programList.macros) {
			macro.keysPressed.get(type).put(keyCode,1);
			if (macro.getStart()) {
				boolean value=testkeyPressed(macro);

				if (value) {

					//---------------------------------------------------
					if (this.macropast!=null) {
						this.macropast.setIsRunning(false);
						this.macropast.isRunning=false;
					}
					macro.run();
					this.macropast=macro;
				}
			}

		}
	}
	private boolean testkeyPressed(Macro macro) {
		boolean value=true;
		for (int i=0;i<macro.keysList.size();i++) {
			for (int key:macro.keysList.get(i).keySet()) {
				if (macro.keysList.get(i).get(key)==macro.keysPressed.get(i).get(key)) {

				} else {
					value=false;

				}
			}
			for (int i3=0;i3<macro.keysNotPressed.size();i3++) {
				for (int key:macro.keysNotPressed.get(i).keySet()) {
					if (macro.keysNotPressed.get(i).get(key)!=macro.keysPressed.get(i).get(key)) {
					} else {

						value=false;

					}
				}
			}
		}
		return value;
	}

	// tpye is the position in the array that starts at 0
	public void endofEvent(int keyCode,int type) {
		for (Macro macro:programList.macros) {
			boolean value1=testkeyPressed(macro);
			macro.keysPressed.get(type).put(keyCode,0);
			boolean value2=testkeyPressed(macro);
			if (value1==true&&value2==false) {
				macro.setStart(true);
			}

		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		endofEvent(e.getKeyCode(),0);
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {

	}

	public static void block() {
		USER_32.BlockInput(true);
	}
	public static void unblock() {
		USER_32.BlockInput(false);
	}


	// why
	@Override
	public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {
		CountdownTimer timer=timerList.get(e.getWheelRotation());
		timer.use(e.getWheelRotation(),2);
	}

	@Override
	public void nativeMouseClicked(NativeMouseEvent e) {

	}

	@Override
	public void nativeMousePressed(NativeMouseEvent e) {
		startofEvent(e.getButton(),1);
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent e) {
		endofEvent(e.getButton(),1);
	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent e) {

	}

	@Override
	public void nativeMouseDragged(NativeMouseEvent e) {

	}
	public MacrosStorage getMacrosStorage() {
		return macrosStorage;
	}
}
