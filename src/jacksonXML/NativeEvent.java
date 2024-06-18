package jacksonXML;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;


public class NativeEvent {
	@JacksonXmlProperty(isAttribute = true)
	public String type;
	@JacksonXmlProperty(isAttribute = true)
	public boolean isInverted;
	public Integer data;
}
