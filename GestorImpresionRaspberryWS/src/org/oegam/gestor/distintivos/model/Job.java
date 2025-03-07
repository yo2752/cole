package org.oegam.gestor.distintivos.model;

import java.io.Serializable;

public class Job implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8973164902029299887L;
	
	private Long id;
	private Long printer;
	private Long queue;
	private Long status;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPrinter() {
		return printer;
	}
	public void setPrinter(Long printer) {
		this.printer = printer;
	}
	public Long getQueue() {
		return queue;
	}
	public void setQueue(Long queue) {
		this.queue = queue;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	
	
}
