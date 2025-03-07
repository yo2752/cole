package trafico.utiles.enumerados;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class CertificadoCsv {

	private String doiPlataforma;
	private String doiColegio;
	private String idExpediente;
	private String idVersionExp;
	private String hash;

	public CertificadoCsv(String doiPlataforma, String doiColegio, String idExpediente, String idVersionExp)
			throws NoSuchAlgorithmException {
		this.doiPlataforma = doiPlataforma;
		this.doiColegio = doiColegio;
		this.idExpediente = idExpediente;
		this.idVersionExp = idVersionExp;
		this.hash = calculateHash();
	}

	private String calculateHash() throws NoSuchAlgorithmException {
		String dataToHash = this.doiPlataforma + this.doiColegio + this.idExpediente + this.idVersionExp;
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(dataToHash.getBytes(StandardCharsets.UTF_8));
		return DatatypeConverter.printHexBinary(hash);
	}

	public String getDoiPlataforma() {
		return doiPlataforma;
	}

	public void setDoiPlataforma(String doiPlataforma) {
		this.doiPlataforma = doiPlataforma;
	}

	public String getDoiColegio() {
		return doiColegio;
	}

	public void setDoiColegio(String doiColegio) {
		this.doiColegio = doiColegio;
	}

	public String getIdExpediente() {
		return idExpediente;
	}

	public void setIdExpediente(String idExpediente) {
		this.idExpediente = idExpediente;
	}

	public String getIdVersionExp() {
		return idVersionExp;
	}

	public void setIdVersionExp(String idVersionExp) {
		this.idVersionExp = idVersionExp;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

}