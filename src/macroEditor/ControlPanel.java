package macroEditor;


import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import listenersList.MouseWheelCountdownListener;


public class ControlPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Editor editor;
	@SuppressWarnings("unused")
	private int id;
	protected ComponentUpgrade[] upgradeList;
	//------------------------------------------------------
	private JLabel firstText;
	protected Component firstTextData;
	private JLabel secondText;
	public JComboBox<String> secondTextData;

	protected String[][] startText=new String[3][];
	protected String[] panelText;

	protected boolean firstTimeStarter=true;

	//----------------------------------------------------------------
	private int lastComponentPosition=15;

	protected JButton BackButton;
	protected JButton OkayButton;
	protected JButton CreateButton;
	protected JButton DeleteButton;
	
	protected ComponentUpgrade upgrade;

	public ControlPanel(Editor editor,int id) {
		// TODO Auto-generated constructor stub
		upgrade=new ComponentUpgrade();
		BackButton=new JButton("Back");
		OkayButton=new JButton("Okay");
		CreateButton=new JButton("Create");
		DeleteButton=new JButton("Delete");
		BackButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				goBack();

			}
		});
		OkayButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				confirm();
			}
		});
		CreateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				create();
			}
		});
		DeleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				delete();
			}
		});
		startText[0]=new String[2];
		startText[1]=new String[2];
		startText[2]=new String[6];
		startText[0][0]="Mousewheelcooldown in milliseconds:";
		startText[0][1]="Select your macrolist that you wanna edit!";
		startText[1][0]="Name of the selected macrolist:";
		startText[1][1]="Select your Macro,that you wanna edit!";
		startText[2][0]="Name of selected Macro:";
		startText[2][1]="What do you wanna edit?";
		startText[2][2]="Select your specific keys, that you wanna edit";
		startText[2][3]="What type should it be";
		startText[2][4]="key:";
		startText[2][5]="isInverted:";
		panelText=startText[id];
		this.firstText=new JLabel();
		this.editor=editor;
		this.id=id;
		this.setLayout(new GridLayout(15,1));
		upgradeList=new ComponentUpgrade[15];
		for (int i=0;i<15;i++) {
			upgradeList[i]=new ComponentUpgrade();
		}
		this.firstText=new JLabel(panelText[0]);
		this.firstText.setName("null");
		upgradeList[0].addAutomaticResize(this.firstText);
		this.add(this.firstText);
		if(id==0) {
			this.firstTextData=new JTextField(""+editor.runner.macrosStorage.mouseCooldown);
			JTextField mouseCooldownField=(JTextField) this.firstTextData;
			mouseCooldownField.addKeyListener(new MouseWheelCountdownListener(mouseCooldownField,this.editor.runner));
			upgradeList[1].addAcceptOnlyIntegers(this.firstTextData);

		} else {
			this.firstTextData=new JLabel();
			JLabel label=(JLabel) this.firstTextData;
			label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

		}
		upgradeList[1].addAutomaticResize(this.firstTextData);
		this.upgrade.placeInTheCenter(this.firstTextData,this);
		this.secondText=new JLabel(panelText[1]);
		this.secondText.setName("null");
		upgradeList[2].addAutomaticResize(this.secondText);
		this.add(this.secondText);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		this.secondTextData=new JComboBox<String>();
		this.add(this.secondTextData);
		this.upgradeList[3].addAutomaticResize(this.secondTextData);
		this.setup();

		for (int i=this.getComponentCount();i<(lastComponentPosition-1);i++) {
			this.add(new JLabel());
		}
		Container container=new Container();
		container.setLayout(new GridLayout(1,4));
		container.add(BackButton);
		container.add(DeleteButton);
		if (id!=2) {
			container.add(OkayButton);
			container.add(CreateButton);
		}
		this.add(container);
		this.upgradeList[7].addAutomaticResize(BackButton);
		this.upgradeList[8].addAutomaticResize(OkayButton);
		this.upgradeList[9].addAutomaticResize(CreateButton);
		this.upgradeList[10].addAutomaticResize(DeleteButton);
		this.endSetup(container);
	}
	public void setup() {

	}
	
	public void create() {

	}
	public void delete() {

	}

	public void goBack() {

	}
	public void confirm() {
		this.firstTimeStarter=false;
	}
	protected void endSetup(Container container) {
		
	}
	//--------------------------------------------------------------------
	public void setAll(String type,boolean value) {
		for (Component oc:this.getComponents()) {
			if (oc.getClass()!=Container.class) {
				if (oc.getName()!="null") {
					if (type.equals("blocking") ) {
						this.upgrade.setIsBlocked(oc,value);
					}
					if (type.equals("texts")) {

						this.upgrade.resetText(oc);
					}

				}
			} else {
				if (oc.getClass()==Container.class) {
					Container container=(Container) oc;
					for (Component componentObject:container.getComponents()) {
						if (componentObject.getName()!="null") {
							if (type.equals("blocking") ) {
								this.upgrade.setIsBlocked(oc,value);
							}
							if (type.equals("texts")) {

								this.upgrade.resetText(oc);
							}
						}
					}
				}
			}
		}
	}
	public boolean updateBox() {
		return false;

	}
}
