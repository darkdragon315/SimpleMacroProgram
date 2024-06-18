package jacksonXML;

import java.util.ArrayList;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import sequence.Macro;

public class MacroList {
	@JacksonXmlElementWrapper(localName = "macroList")
	@JacksonXmlProperty(localName = "macro")
	public ArrayList<Macro> macros;
	@JacksonXmlProperty(isAttribute = true)
	public String name;
}
