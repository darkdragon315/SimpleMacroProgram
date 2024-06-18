package sequence;

import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import io.quarkus.runtime.annotations.IgnoreProperty;
import jacksonXML.ActionClass;
import jacksonXML.NativeEvent;






@JacksonXmlRootElement(localName = "macro")
public class Macro {
	@JacksonXmlElementWrapper(localName = "actionList")
	public ArrayList<ActionClass> actions=new ArrayList<ActionClass>();;
	@JacksonXmlElementWrapper(localName = "eventList")
	@JacksonXmlProperty(localName = "nativeEvent")
	public ArrayList<NativeEvent> eventlist;
	@JacksonXmlProperty(isAttribute = true)
	public String tag;
	@JsonIgnore
	@IgnoreProperty
	public ArrayList<HashMap<Integer,Integer>> keysList=new ArrayList<HashMap<Integer,Integer>>(3);
	@JsonIgnore
	@IgnoreProperty
	public ArrayList<HashMap<Integer,Integer>> keysPressed=new ArrayList<HashMap<Integer,Integer>>(3);
	@JsonIgnore
	@IgnoreProperty
	public ArrayList<HashMap<Integer,Integer>> keysNotPressed=new ArrayList<HashMap<Integer,Integer>>(3);

	private MacroRunnable executor;

	@JsonIgnore
	@IgnoreProperty
	private boolean start;
	@JsonIgnore
	@IgnoreProperty
	public HotKeyRunner manager;
	@JsonIgnore
	@IgnoreProperty
	public int counter;
	@JsonIgnore
	@IgnoreProperty
	public volatile boolean isRunning;
	public Macro() {
		// TODO Auto-generated constructor stub
		this.executor=new MacroRunnable(this);
	}


	public void setIsRunning(boolean value) {
		this.isRunning=value;
	}

	public void setup(HotKeyRunner manager) {
		this.executor=new MacroRunnable(this);
		this.manager=manager;
		start=true;

	}


	public void updateKeyArray() {
		keysPressed=new ArrayList<HashMap<Integer,Integer>>();
		keysList=new ArrayList<HashMap<Integer,Integer>>();
		keysNotPressed=new ArrayList<HashMap<Integer,Integer>>();
		for (int i=0;i<3;i++) {
			keysPressed.add(new HashMap<Integer, Integer>());
			keysNotPressed.add(new HashMap<Integer, Integer>());
			keysList.add(new HashMap<Integer, Integer>());
		}
		for (NativeEvent nativevent:eventlist) {
			if (nativevent.isInverted==false) {
				switch (nativevent.type) {
				case "keyboard":
					int i=nativevent.data;
					keysList.get(0).put(i,1);
					keysPressed.get(0).put(i,0);

					break;
				case "mouse button":
					int i2=nativevent.data;
					keysList.get(1).put(i2,1);
					keysPressed.get(1).put(i2,0);

					break;
				case "mouse wheel":
					int i3=nativevent.data;

					keysList.get(2).put(i3,1);
					keysPressed.get(2).put(i3,0);

					break;
				}
			} else {
				switch (nativevent.type) {
				case "keyboard":
					int i4=nativevent.data;
					keysNotPressed.get(0).put(i4,1);
					keysPressed.get(0).put(i4,0);

					break;
				case "mouse button":
					int i5=nativevent.data;
					keysNotPressed.get(1).put(i5,1);
					keysPressed.get(1).put(i5,0);

					break;
				case "mouse wheel":
					int i6=nativevent.data;
					keysNotPressed.get(2).put(i6,1);
					keysPressed.get(2).put(i6,0);
				}
				break;
			}
		}
	}







	public void run() {
		// TODO Auto-generated method stub
		this.isRunning=true;
		this.start=false;
		counter=0;
		this.executor.run();
	}

	@JsonIgnore
	@IgnoreProperty
	public boolean getStart() {
		return start;
	}
	public void setStart(boolean value) {
		this.start=value;
	}


}