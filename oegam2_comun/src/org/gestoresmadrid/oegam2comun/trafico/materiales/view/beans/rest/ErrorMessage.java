package org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest;

import java.lang.reflect.InvocationTargetException;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.beanutils.BeanUtils;

public class ErrorMessage {
	@XmlElement(name = "name")	      private String  name;
	@XmlElement(name = "message")     private String  message;
	@XmlElement(name = "code")	      private int     code;
	@XmlElement(name = "status")      private Integer status;
	@XmlElement(name = "type")        private String  type;
	@XmlElement(name = "previous")    private Object  previous;
	@XmlElement(name = "file")        private String  file;
	@XmlElement(name = "line")        private Integer line;
	@XmlElement(name = "stack-trace") private Object  stackTrace;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public Integer getLine() {
		return line;
	}
	public void setLine(Integer line) {
		this.line = line;
	}
	
	public Object getStackTrace() {
		return stackTrace;
	}
	public void setStackTrace(Object stackTrace) {
		this.stackTrace = stackTrace;
	}
	public Object getPrevious() {
		return previous;
	}
	public void setPrevious(Object previous) {
		this.previous = previous;
	}
	public ErrorMessage(AppException ex){
		try {
			BeanUtils.copyProperties(this, ex);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ErrorMessage() {}
	
}
