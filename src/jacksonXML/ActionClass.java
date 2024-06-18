package jacksonXML;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ActionClass {
	@JacksonXmlProperty(isAttribute = true)
	public String category;
	@JacksonXmlProperty(isAttribute = true)
	public String type;
	public int data;
	
}
