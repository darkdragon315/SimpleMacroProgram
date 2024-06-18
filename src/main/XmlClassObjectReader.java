package main;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XmlClassObjectReader{
	private XmlMapper  mapper=new XmlMapper();
	public XmlClassObjectReader() {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
	}
	public Object read(Class<?> clasz,File file) {
		try {
			Object object;
			object=mapper.readValue(file,clasz);
			return object;
		} catch (StreamReadException e) {
			e.printStackTrace();
		} catch (DatabindException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	public void write(Object object,File file) {
		try {
			mapper.writeValue(file,object);
		} catch (StreamWriteException e) {
			e.printStackTrace();
		} catch (DatabindException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
