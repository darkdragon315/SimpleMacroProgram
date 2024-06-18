package macroEditor;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseWheelEvent;

import jacksonXML.ActionClass;
import jacksonXML.NativeEvent;
import listenersList.DelayListener;
import listenersList.KeyTrackingInput;
import listenersList.KeyTrackingOutput;
import sequence.Macro;


public class RightControlPanel extends ControlPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MiddleControlPanel mcp;
	private JLabel text1;
	private JLabel text2;
	private JLabel text3;
	private JLabel text4;
	public Macro selectedMacro;
	protected JComboBox<String> keyList;
	protected JComboBox<String> selector2;

	private KeyTrackingInput inputListener;
	private KeyTrackingOutput outputListener;
	public JButton buttonKeySelector;
	private int kind;
	private boolean setupValue;

	private ComboBoxListener blocker;
	
	
	private JButton createUp;
	private JButton createDown;
	
	private JTextField delayField;
	private ScheduledExecutorService service;
	public RightControlPanel(Editor editor, int id,MiddleControlPanel mcp) {
		super(editor, id);
		this.mcp=mcp;
	}

	@Override
	public void setup() {
		this.createUp=new JButton("Create up");
		this.createDown=new JButton("Create down");
		this.service=Executors.newSingleThreadScheduledExecutor();
		this.text4=new JLabel();
		this.delayField=new JTextField();
		this.blocker=new ComboBoxListener(new JComboBox<String>(),":", editor);
		inputListener=new KeyTrackingInput(this,Executors.newSingleThreadScheduledExecutor());
		outputListener=new KeyTrackingOutput(this,Executors.newSingleThreadScheduledExecutor());
		this.setupValue=true;
		this.buttonKeySelector=new JButton();
		this.keyList=new JComboBox<String>();
		this.selector2=new JComboBox<String>();
		text2=new JLabel();
		this.OkayButton.setText("Save");
		text1=new JLabel();
		text3=new JLabel();
		Container container2=new Container();
		container2.setLayout(new GridLayout(3,1));
		this.upgradeList[5].addAutomaticResize(this.text1);
		this.upgradeList[8].addAcceptOnlyIntegers(this.delayField);
		this.upgradeList[8].addAutomaticResize(this.text4);
		this.secondTextData.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					Object item = event.getItem();
					String itemText=(String) item;
					if (itemText.equals("Input")) {
						kind=0;
						setupValue=false;
						updateSelector2();
						selector2.setSelectedIndex(0);

						updateKeyList(0);
						text1.setText("Select the specific input key from the list!");
						text2.setText("Where is the input coming from?");
						text3.setText("isInverted:");
						text4.setText("");
						buttonKeySelector.setText("Input:");
						setupValue=true;
						delayField.setEnabled(false);
						
					} else {
						if (itemText.equals("Output")) {
							delayField.setEnabled(false);
							kind=1;
							setupValue=false;
							updateSelector2();
							selector2.setSelectedIndex(0);
							updateKeyList(1);
							text1.setText("Select the specific action from the list!");
							text2.setText("Where should the output go?");
							text3.setText("What should happen?");
							buttonKeySelector.setText("Output:");
							setupValue=true;
					
						}
					}
					updateButtons();
				}
			}
		});
		this.add(text1);
		this.add(this.keyList);
		this.add(text3);
		//category selector
		this.add(selector2);
		this.add(new JLabel());
		this.add(buttonKeySelector);

	    

		this.selector2.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					if (setupValue==true) {
						int b=keyList.getSelectedIndex();
						//input
						if (kind==0) {
							switch ((String)selector2.getSelectedItem()) {
							case "true":
								selectedMacro.eventlist.get(b).isInverted=true;
								break;
							case "false":
								selectedMacro.eventlist.get(b).isInverted=false;
								break;
							}
							selector2.setSelectedIndex(0);
							updateKeyList(0);
							
						} else {
							//output
							if (kind==1) {
								if (selector2.getSelectedIndex()>0) {
									selectedMacro.actions.get(b).category=(String) selector2.getSelectedItem();
									selector2.setSelectedIndex(0);
									updateKeyList(1);
									ActionClass action=selectedMacro.actions.get(keyList.getSelectedIndex());
									if (action.category!=null) {
										if (action.category.equals("wait")==false) {
											service.schedule(new Runnable() {
												
												@Override
												public void run() {
													delayField.setEnabled(false);
												}
											},20L,TimeUnit.MILLISECONDS);
										}
									}
								}
							}
						}
						
					}

				}

			}});
		this.keyList.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					if (setupValue==true) {
						
					}
					if (kind==1) {

						ActionClass action=selectedMacro.actions.get(keyList.getSelectedIndex());
						if (action.category!=null) {
							if (action.category.equals("wait")) {
								if (action.equals(null)) {
									delayField.setText("");
								} else {
									delayField.setText(""+action.data);
								}
								service.schedule(new Runnable() {
									
									@Override
									public void run() {
										delayField.setEnabled(true);
									}
								},10L,TimeUnit.MILLISECONDS);
								text4.setText("Delay in milliseconds:");
								buttonKeySelector.setEnabled(false);
								action.type=null;
								
								
							} else {
								text4.setText("");
								delayField.setText("");
								delayField.setEnabled(false);
								buttonKeySelector.setEnabled(true);
							}
						} else {
							text4.setText("");
							delayField.setEnabled(false);
							delayField.setText("");
							buttonKeySelector.setEnabled(true);
						}
						
					} else {
						text4.setText("");
						delayField.setEnabled(false);
						delayField.setText("");
						buttonKeySelector.setEnabled(true);
					}
				}
			}
		});


		this.buttonKeySelector.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				if (kind==0) {
					inputListener.start();
				}else {
					outputListener.start();
				}
			} 
		} );
		this.add(text4);
		this.add(delayField);
		this.text4.setEnabled(true);
		this.delayField.addKeyListener(new DelayListener(this.delayField,this.keyList,this));
	}
	public void updateButtons() {
		if (kind==1) {
			if (selectedMacro.actions==null) {
				selectedMacro.actions=new ArrayList<ActionClass>();
			}
			if (selectedMacro.actions.size()==0) {
				blocker.setSwitchButtonIsEnabled(keyList,false);
				keyList.setEnabled(false);
				selector2.setEnabled(false);
				buttonKeySelector.setEnabled(false);
			} else {
				blocker.setSwitchButtonIsEnabled(keyList,true);
				keyList.setEnabled(true);
				selector2.setEnabled(true);
				buttonKeySelector.setEnabled(true);
			}
		} else {
			if (selectedMacro.eventlist==null) {
				selectedMacro.eventlist=new ArrayList<NativeEvent>();
			}
			if (selectedMacro.eventlist.size()==0) {
				blocker.setSwitchButtonIsEnabled(keyList,false);
				keyList.setEnabled(false);
				selector2.setEnabled(false);
				buttonKeySelector.setEnabled(false);
			} else {
				blocker.setSwitchButtonIsEnabled(keyList,true);
				keyList.setEnabled(true);
				selector2.setEnabled(true);
				buttonKeySelector.setEnabled(true);
			}
		}
	}
	@Override
	public void goBack() {
		this.setAll("blocking",true);
		mcp.setAll("blocking",false);
		this.setAll("texts",false);
		selectedMacro=null;
	}
	private void updateKeyList(int kind) {
		if (kind==0) {
			this.upgrade.updateComboBox(this.keyList,selectedMacro.eventlist,NativeEvent.class,false);
		} else {
			this.upgrade.updateComboBox(this.keyList,selectedMacro.actions,ActionClass.class,false);
		}
	}
	@Override
	public void delete() {
		int position=keyList.getSelectedIndex();
		if (kind==0) {
			if (position!=-1) {
				
				ArrayList<NativeEvent> eventList=selectedMacro.eventlist;
				eventList.remove(position);
			}
			this.updateKeyList(0);
		} else {
			if (position!=-1) {
				ArrayList<ActionClass> actionList=selectedMacro.actions;
				actionList.remove(position);
			}
			this.updateKeyList(1);
		}
		this.updateButtons();
		if (kind==1) {
			if (selectedMacro.actions.get(keyList.getSelectedIndex()).category.equals("wait")) {
				buttonKeySelector.setEnabled(false);
			}
		}

	}
	//delete this is not needed
	@SuppressWarnings("unchecked")
	public void createWithShift(int shift) {
		if (kind==0) {
			if (selectedMacro.eventlist==null) {
				selectedMacro.eventlist=new ArrayList<NativeEvent>();
			}
			//selectedMacro.eventlist.add(new NativeEvent());
			selectedMacro.eventlist=(ArrayList<NativeEvent>) this.createWithPosition(selectedMacro.eventlist,keyList.getSelectedIndex(),new NativeEvent(), shift);
			this.updateKeyList(0);
		} else {
			if (selectedMacro.actions==null) {
				selectedMacro.actions=new ArrayList<ActionClass>();
			}
			//selectedMacro.actions.add(new ActionClass());
			selectedMacro.actions=(ArrayList<ActionClass>) this.createWithPosition(selectedMacro.actions,keyList.getSelectedIndex(),new ActionClass(), shift);
			this.updateKeyList(1);
		}
		
		this.updateButtons();
		
		//-------------------------------------------------------------------------------
		if (kind==1) {
			ActionClass action=selectedMacro.actions.get(keyList.getSelectedIndex());
			if (action.category!=null) {
				if (action.category.equals("wait")) {
					buttonKeySelector.setEnabled(false);
				}
			}
			
		}
	}
	public void updateSelector2() {
		if (kind==0) {
			this.upgrade.updateComboBox(this.selector2,"isInverted",null,true);
		} else {
			this.upgrade.updateComboBox(this.selector2,"",ActionClass.class,true);
		}

	}
	public void runWithInput(Object object) {
		if (kind==0) {
			NativeEvent nativeEventClass=this.selectedMacro.eventlist.get(this.keyList.getSelectedIndex());
			if (object.getClass()==NativeMouseEvent.class) {
				NativeMouseEvent e=(NativeMouseEvent) object;
				nativeEventClass.data=e.getButton();
				nativeEventClass.type="mouse button";
				this.updateKeyList(0);
			}
			if (object.getClass()==NativeMouseWheelEvent.class) {
				NativeMouseWheelEvent e=(NativeMouseWheelEvent) object;
				int keyCode=e.getWheelRotation();
				nativeEventClass.data=keyCode;
				nativeEventClass.type="mouse wheel";
				this.updateKeyList(0);
			}
			if (object.getClass()==NativeKeyEvent.class) {
				NativeKeyEvent e=(NativeKeyEvent) object;
				nativeEventClass.data=e.getKeyCode();
				nativeEventClass.type="keyboard";
				this.updateKeyList(0);

			}
		} else {
			ActionClass action=this.selectedMacro.actions.get(this.keyList.getSelectedIndex());
			if (object.getClass()==MouseEvent.class) {
				MouseEvent e=(MouseEvent) object;
				action.data=e.getButton();
				action.type="mouse button";
				this.updateKeyList(1);
			}
			if (object.getClass()==MouseWheelEvent.class) {
				MouseWheelEvent e=(MouseWheelEvent) object;
				int keyCode=e.getWheelRotation();
				action.data=keyCode;
				action.type="mouse wheel";
				this.updateKeyList(1);
			}
			if (object.getClass()==KeyEvent.class) {
				KeyEvent e=(KeyEvent) object;
				action.data=e.getKeyCode();
				action.type="keyboard";
				this.updateKeyList(1);

			}

		}
	}
	@Override
	protected void endSetup(Container container) {
		container.add(createUp);
		container.add(createDown);
		createUp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				createWithShift(1);
			}
		});
		createDown.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				createWithShift(-1);
			}
		});
	}
	private ArrayList<?> createWithPosition(ArrayList<?> listStart,int keyPosition,Object newObject,int shift) {
		ArrayList<Object> newList=new ArrayList<Object>();
		for (int i=0;i<listStart.size();i++) {
			if (i!=keyPosition) {
				newList.add(listStart.get(i));
			} else {
				if (shift==1) {
					newList.add(newObject);
					newList.add(listStart.get(i));
				} else if (shift==-1) {
					newList.add(listStart.get(i));
					newList.add(newObject);
				}
			}
		}
		if (listStart.size()==0) {
			newList.add(newObject);
		}
		return newList;
		
	}
}

