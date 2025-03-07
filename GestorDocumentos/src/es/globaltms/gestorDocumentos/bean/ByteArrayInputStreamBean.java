package es.globaltms.gestorDocumentos.bean;

import java.io.InputStream;

import es.globaltms.gestorDocumentos.enums.FileResultStatus;

public class ByteArrayInputStreamBean {

	private FileResultStatus status;
	private String message;
	private InputStream byteArrayInputStream;
	private String fileName;

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

	public InputStream getByteArrayInputStream() {
		return byteArrayInputStream;
	}

	public void setByteArrayInputStream(InputStream byteArrayInputStream) {
		this.byteArrayInputStream = byteArrayInputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
