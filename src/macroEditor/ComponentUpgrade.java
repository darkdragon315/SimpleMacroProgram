package macroEditor;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jnativehook.keyboard.NativeKeyEvent;

import jacksonXML.ActionClass;
import jacksonXML.MacroList;
import jacksonXML.MacrosStorage;
import jacksonXML.NativeEvent;
import sequence.Macro;

public class ComponentUpgrade {
	private Dimension dimensionBoxPast;
	private String inputtext = null;
	//bad code be like
	public void addAutomaticResize(Component input) {
		input.addComponentListener(new ComponentAdapter(){
			
			public void componentResized(ComponentEvent e){
				if (input.getClass()==JLabel.class) {
					JLabel label=(JLabel)input;
					inputtext=label.getText();
				}
				if (input.getClass()==JTextField.class) {
					JTextField label=(JTextField)input;
					inputtext=label.getText();
				}
				if (input.getClass()==JComboBox.class) {
					JComboBox<?> label=(JComboBox<?>)input;
					inputtext=(String)label.getSelectedItem();
					
				}
				if (input.getClass()==JButton.class) {
					JButton button=(JButton) input;
					inputtext=button.getText();
				}
				if (inputtext!=null) {
					String[] text=html2text(inputtext).split("<br/>");
					Dimension dimensionBox=e.getComponent().getSize();
					FontRenderContext context = new FontRenderContext(new AffineTransform(),true,true);
					Font font = e.getComponent().getFont();
					int[] textwidth = new int[text.length];
					int[] textheight = new int[text.length];
					int totalHeight=0;
					for (int i=0;i<text.length;i++) {
						textheight[i]=(int)(font.getStringBounds(text[i], context).getHeight());
						if (input.getClass()==JComboBox.class) {
							textwidth[i]=(int)((float)(font.getStringBounds(text[i], context).getWidth())*1.2f);
						} else {
							textwidth[i]=(int)(font.getStringBounds(text[i], context).getWidth());
						}
					}
					for (int i=0;i<text.length;i++) {
						totalHeight+=textheight[i];
					}
					Dimension dimensionText=new Dimension(Arrays.stream(textwidth).max().getAsInt(),totalHeight);
					Dimension deltaDifference=new Dimension();
					if (dimensionBoxPast!=null) {
						deltaDifference.width=dimensionBox.width-dimensionBoxPast.width;
						deltaDifference.height=dimensionBox.height-dimensionBoxPast.height;
						int minDifference=Math.min(deltaDifference.width, deltaDifference.height);
						if (minDifference<0) {
							if (dimensionBox.height>=dimensionText.height&&dimensionBox.width>=dimensionText.width) {


							} else {
								int deltaHeight=dimensionText.height-dimensionBox.height;
								int deltaWidth=dimensionText.width-dimensionBox.width;
								int deltaMax=Math.max(deltaHeight, deltaWidth);
								Font thisFont=e.getComponent().getFont().deriveFont(e.getComponent().getFont().getSize()*(1f-((float)deltaMax/7f)));
								e.getComponent().setFont(thisFont);
							}
						} else {
							if (dimensionBox.height<=dimensionText.height&&dimensionBox.width<=dimensionText.width) {

							} else {
								int deltaHeight=dimensionBox.height-dimensionText.height;
								int deltaWidth=dimensionBox.width-dimensionText.width;
								int deltaMax=Math.min(deltaHeight, deltaWidth);
								float test=(1f+((float)deltaMax/1200f));
								Font thisFont=e.getComponent().getFont().deriveFont(e.getComponent().getFont().getSize()*test);
								e.getComponent().setFont(thisFont);
							}
						}



					}

					dimensionBoxPast=dimensionBox;
				}
			}
		});
	}
	public void addAcceptOnlyIntegers(Component input) {
		JTextField component=(JTextField) input;
		component.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {

				if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9'||ke.getKeyCode()==KeyEvent.VK_BACK_SPACE) {
					component.setEditable(true);
				} else {
					component.setEditable(false);
				}
			}
		});
	}
	private static String html2text(String html) {

		return html.replace("<html>","").replace("</html>","");
	}
	@SuppressWarnings({ "static-access", "unchecked" })
	public boolean updateComboBox(JComboBox<String> box,Object object,Class<?> clasz,boolean stopIndex) {
		int index = 0;
		if (box.getItemCount()>0) {
			index=box.getSelectedIndex();
			box.removeAllItems();


		}
		if (object!=null) {
			if (object.getClass()==MacrosStorage.class) {
				MacrosStorage macrosStorage=(MacrosStorage) object;
				if (macrosStorage.macrosList!=null) {
					for (int i=0;i<macrosStorage.macrosList.size();i++) {
						String name=macrosStorage.macrosList.get(i).name;
						box.addItem(i+": "+name);

					}
				} else {
					return false;
				}


			} else {
				if (object.getClass()==MacroList.class) {
					MacroList macroList=(MacroList) object;
					if (macroList.macros!=null) {
						for (int i=0;i<macroList.macros.size();i++) {
							String name=macroList.macros.get(i).tag;
							box.addItem(i+": "+name);
						}
					} else {
						return false;
					}

				}
				if (object.getClass()==Macro.class) {

					box.addItem("Input");
					box.addItem("Output");
				}
				if (object.getClass()==ArrayList.class) {
					if (clasz==ActionClass.class) {
						ArrayList<ActionClass> actions=(ArrayList<ActionClass>) object;
						for (int i=0;i<actions.size();i++) {
							ActionClass ac=actions.get(i);
							String category=ac.category;
							String type=ac.type;
							if (category!=null) {
								if (category.equals("wait")) {
									box.addItem(i+": "+category);
								} else {
									//here mouse,keyboard,mouse wheel
									if (type!=null) {
										switch (type) {
										case "keyboard":
											KeyEvent e = null;
											box.addItem(i+": "+type+" {category: "+ac.category+"}"+" {"+e.getKeyText(ac.data).toLowerCase()+"}");
											break;
										case "mouse button":
											box.addItem(i+": "+type+" {category: "+ac.category+"}"+" {"+"id: "+ac.data+"}");
											break;
										case "mouse wheel":
											String text=null;
											if (ac.data==-1) {
												text="up";
											} else {
												text="down";
											}
											box.addItem(i+": "+type+" {category: "+ac.category+"}"+" {"+text+"}");
											break;

										}
									} else {
										box.addItem(i+": "+null+" {category: "+ac.category+"}"+" {"+"id: "+null+"}");
									}
								}
							} else {
								box.addItem(i+": "+null+" {category: "+null+"}"+" {"+ac.data+"}");
							}
							
						}
					} else {
						if (clasz==NativeEvent.class) {
							ArrayList<NativeEvent> nativeEvents=(ArrayList<NativeEvent>) object;
							for (int i3=0;i3<nativeEvents.size();i3++) {
								NativeEvent nE=nativeEvents.get(i3);
								String name=nE.type;
								if (name!=null) {
									switch (name) {
									case "keyboard":
										NativeKeyEvent e = null;
										box.addItem(i3+": "+name+" {isInverted: "+nE.isInverted+"}"+" {"+e.getKeyText(nE.data).toLowerCase()+"}");
										break;
									case "mouse button":
										box.addItem(i3+": "+name+" {isInverted: "+nE.isInverted+"}"+" {"+"id: "+nE.data+"}");
										break;
									case "mouse wheel":
										String text=null;
										if (nE.data==-1) {
											text="up";
										} else {
											text="down";
										}
										box.addItem(i3+": "+name+" {isInverted: "+nE.isInverted+"}"+" {"+text+"}");
										break;

									}
								} else {
									box.addItem(i3+": "+name+" {isInverted: "+nE.isInverted+"}"+" {"+null+"}");
								}
								
							}
						}
					}

				} else {
					//object needs to be a String
					if (object.getClass()==String.class) {
						if (clasz==ActionClass.class) {
							box.addItem("");
							box.addItem("wait");
							box.addItem("press");
							box.addItem("release");

						} else {
							if (clasz==NativeEvent.class) {
								box.addItem("");
								box.addItem("keyboard");
								box.addItem("mouse button");
								box.addItem("mouse wheel");
							} else {
								if (object.equals("isInverted")) {
									box.addItem("");
									box.addItem("true");
									box.addItem("false");
								}
							}
						}
					}
				}


			}
		} else {
			return false;
		}

		if (box.getItemCount()>0) {
			if (stopIndex==true) {

			} else {
				if (box.getItemCount()>=index+1) {
					box.setSelectedIndex(index);
				} else {
					if (box.getItemCount()>0) {
						box.setSelectedIndex(box.getItemCount()-1);
					}
				}
			}
			return true;
		} else {
			return false;
		}
	}
	protected void placeInTheCenter(Component component,JPanel panel) {
		Container container2=new Container();
		container2.setLayout(new GridLayout(1,3));
		container2.add(new JLabel());
		container2.add(component);
		container2.add(new JLabel());
		panel.add(container2);
	}
	public void resetText(Component component) {
		if (component.getClass()==JTextField.class) {
			JTextField field=(JTextField) component;
			field.setText("");
		} else {
			if (component.getClass()==JComboBox.class) {
				JComboBox<?> comboBox=(JComboBox<?>) component;
				comboBox.removeAllItems();
			} else {
				if (component.getClass()==JLabel.class) {
					JLabel label=(JLabel) component;
					label.setText("");

				} else {
					if (component.getClass()==Container.class) {
						Container container=(Container) component;
						for (Component componentObject:container.getComponents()) {

							this.resetText(componentObject);
						}
					}
				}
			}
		}
	}
	public void setIsBlocked(Component component,boolean value) {
		if (component.getClass()==JTextField.class) {
			JTextField field=(JTextField) component;
			if (value==true) {
				field.setEditable(false);

			} else {
				field.setEditable(true);
			}
		} else {
			if (component.getClass()==JComboBox.class) {
				JComboBox<?> comboBox=(JComboBox<?>) component;
				if (value==true) {
					comboBox.setEditable(false);
					comboBox.setEnabled(false);
				} else {
					comboBox.setEditable(true);
					comboBox.setEnabled(true);
				}
			} else {
				if (component.getClass()==JButton.class) {
					JButton button=(JButton) component;
					if (value==true) {
						button.setEnabled(false);
					} else {
						button.setEnabled(true);

					}
				} else {
					if (component.getClass()==Container.class) {
						Container container=(Container) component;
						for (Component componentObject:container.getComponents()) {

							if (value==true) {
								this.setIsBlocked(componentObject, true);
							} else {
								this.setIsBlocked(componentObject, false);
							}
						}
					}
				}
			}
		}
	}
	
}
