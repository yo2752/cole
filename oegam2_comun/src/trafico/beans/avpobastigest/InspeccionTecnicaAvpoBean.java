package trafico.beans.avpobastigest;


/**
 * Contiene la información de la respuesta del BSTI para obtener la matrícula.
 *
 */
public class InspeccionTecnicaAvpoBean {

	//Campos a rellenar en la presentación
	private String concepto;
	private String fecha;
	private String fechaCad;
	private String estacionProv;
	private String motivo;
	private String defectosCalif;
	
	public InspeccionTecnicaAvpoBean() {
		
	}
	
	public void init(){
		//
	}
	
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public String getDefectosCalif() {
		return defectosCalif;
	}
	public void setDefectosCalif(String defectosCalif) {
		this.defectosCalif = defectosCalif;
	}
	public String getEstacionProv() {
		return estacionProv;
	}
	public void setEstacionProv(String estacionProv) {
		this.estacionProv = estacionProv;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getFechaCad() {
		return fechaCad;
	}
	public void setFechaCad(String fechaCad) {
		this.fechaCad = fechaCad;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	
}
