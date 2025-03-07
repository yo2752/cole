package es.globaltms.gestorDocumentos.bean;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import utilidades.estructuras.Fecha;

public class FicheroBean {

	private String tipoDocumento;
	private Fecha fecha;
	private String subTipo;
	private String nombreDocumento;
	private String extension;
	private File fichero;
	private byte[] ficheroByte;
	private boolean sobreescribir;
	private String nombreZip;
	private Boolean subCarpetaDia;
	
	public Fecha getFecha() {
		return fecha;
	}
	public void setFecha(Fecha fecha) {
		this.fecha = fecha;
	}
	private List<File> listaFicheros;
	private Map<String, byte[]> listaByte;
	
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getSubTipo() {
		return subTipo;
	}
	public void setSubTipo(String subTipo) {
		this.subTipo = subTipo;
	}
	public String getNombreDocumento() {
		return nombreDocumento;
	}
	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public File getFichero() {
		return fichero;
	}
	public void setFichero(File fichero) {
		this.fichero = fichero;
	}
	public byte[] getFicheroByte() {
		return ficheroByte;
	}
	public void setFicheroByte(byte[] ficheroByte) {
		this.ficheroByte = ficheroByte;
	}
	public boolean isSobreescribir() {
		return sobreescribir;
	}
	public void setSobreescribir(boolean sobreescribir) {
		this.sobreescribir = sobreescribir;
	}
	
	public String getNombreZip() {
		return nombreZip;
	}
	public void setNombreZip(String nombreZip) {
		this.nombreZip = nombreZip;
	}
	public List<File> getListaFicheros() {
		return listaFicheros;
	}
	public void setListaFicheros(List<File> listaFicheros) {
		this.listaFicheros = listaFicheros;
	}
	public Map<String, byte[]> getListaByte() {
		return listaByte;
	}
	public void setListaByte(Map<String, byte[]> listaByte) {
		this.listaByte = listaByte;
	}
	public FicheroBean(){
		sobreescribir = true;
	}
	public Boolean getSubCarpetaDia() {
		return subCarpetaDia;
	}
	public void setSubCarpetaDia(Boolean subCarpetaDia) {
		this.subCarpetaDia = subCarpetaDia;
	}
	/**
	 * No hay que rellenar todo, lo que no se necesite mandar como null.
	 * @param tipoDocumento
	 * @param numExpediente
	 * @param subTipo
	 * @param nombreDocumento
	 * @param extension
	 * @param fichero
	 * @param ficheroByte
	 * @param sobreescribir
	 * @param nombreZip
	 * @param listaFicheros
	 */
	public FicheroBean(String tipoDocumento, Fecha fecha,
			String subTipo, String nombreDocumento, String extension,
			File fichero, byte[] ficheroByte, Boolean sobreescribir,
			String nombreZip, List<File> listaFicheros, Boolean subCarpetaDia) {
		super();
		if(tipoDocumento!=null)
			this.tipoDocumento = tipoDocumento;
		if(fecha!=null)
			this.fecha = fecha;
		if(subTipo!=null)
			this.subTipo = subTipo;
		if(nombreDocumento!=null)
			this.nombreDocumento = nombreDocumento;
		if(extension!=null)
			this.extension = extension;
		if(fichero!=null)
			this.fichero = fichero;
		if(ficheroByte!=null)
			this.ficheroByte = ficheroByte;
		if(sobreescribir!=null){
			this.sobreescribir = sobreescribir;
		}
		else{
			this.sobreescribir=true;
		}
		if(nombreZip!=null)
			this.nombreZip = nombreZip;
		if(listaFicheros!=null)
			this.listaFicheros = listaFicheros;
		
		if(subCarpetaDia!=null)
			this.subCarpetaDia = subCarpetaDia;
	}
	
	public InputStream getInputStreamFromBytesOrFile(){
		if (getFicheroByte() != null && getFicheroByte().length > 0){
			return new ByteArrayInputStream(getFicheroByte());
		}else if (getFichero() != null){
			try {
				return new FileInputStream(getFichero());
			} catch (FileNotFoundException e) {
				return null;
			}
		}else{
			return null;
		}
		
	}
	
	public void setNombreYExtension(String nombreCompleto){
		if(nombreCompleto.contains(".")){
			int punto = nombreCompleto.indexOf('.');
			this.setNombreDocumento(nombreCompleto.substring(0,punto));
			this.setExtension(nombreCompleto.substring(punto,nombreCompleto.length()));
		}else{
			this.setNombreDocumento(nombreCompleto);
			this.setExtension("");
		}
	}
	
	public String getNombreYExtension(){
		String nombre = this.getNombreDocumento();
		String extension = this.getExtension() == null ? "" : this.getExtension();
		return nombre+extension;
	}

}
