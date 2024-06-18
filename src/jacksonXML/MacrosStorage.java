package jacksonXML;

import java.util.ArrayList;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "program")
public class MacrosStorage {
	@JacksonXmlProperty(localName = "H")
	@JacksonXmlElementWrapper(useWrapping = false)
	public ArrayList<MacroList> macrosList;
	public int mouseCooldown;
}
