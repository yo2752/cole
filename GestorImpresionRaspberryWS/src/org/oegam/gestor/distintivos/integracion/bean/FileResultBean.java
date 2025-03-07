package org.oegam.gestor.distintivos.integracion.bean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.oegam.gestor.distintivos.enums.FileResultStatus;

public class FileResultBean {

	private FileResultStatus status;
	private String message;
	private List<String> listaMensajes;
	private File file;
	private List<File> files;

	public FileResultStatus getStatus() {
		return status;
	}

	public void setStatus(FileResultStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getListaMensajes() {
		return this.listaMensajes;
	}

	public void setListaMensajes(List<String> listaMensajes) {
		this.listaMensajes = listaMensajes;
	}

	public void addMensajeALista(String mensaje) {
		if (listaMensajes == null) {
			listaMensajes = new ArrayList<>();
		}
		listaMensajes.add(mensaje);
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

}