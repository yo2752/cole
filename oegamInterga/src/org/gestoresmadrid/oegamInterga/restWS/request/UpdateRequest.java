package org.gestoresmadrid.oegamInterga.restWS.request;

import java.io.Serializable;

public class UpdateRequest implements Serializable {

	private static final long serialVersionUID = -1314833211678382722L;

	private String platformCif;

	private String societyCif;

	private String fileType;

	private String fileDate;

	private String fileDoi;

	private String filePlate;

	private String reference;

	private String base64;

	public String getPlatformCif() {
		return platformCif;
	}

	public void setPlatformCif(String platformCif) {
		this.platformCif = platformCif;
	}

	public String getSocietyCif() {
		return societyCif;
	}

	public void setSocietyCif(String societyCif) {
		this.societyCif = societyCif;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileDoi() {
		return fileDoi;
	}

	public void setFileDoi(String fileDoi) {
		this.fileDoi = fileDoi;
	}

	public String getFilePlate() {
		return filePlate;
	}

	public void setFilePlate(String filePlate) {
		this.filePlate = filePlate;
	}

	public String getFileDate() {
		return fileDate;
	}

	public void setFileDate(String fileDate) {
		this.fileDate = fileDate;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}
}
