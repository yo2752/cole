package org.gestoresmadrid.oegamInterga.restWS.request;

import java.io.Serializable;
import java.util.List;

public class SendFilesRequest implements Serializable {

	private static final long serialVersionUID = -1648863584728543961L;

	private String platformCif;

	private String societyCif;

	private List<Files> files;

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

	public List<Files> getFiles() {
		return files;
	}

	public void setFiles(List<Files> files) {
		this.files = files;
	}
}
