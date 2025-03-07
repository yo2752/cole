package org.gestoresmadrid.core.trafico.model.vo;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.SecondaryTable;

import org.hibernate.annotations.FetchMode;

@Entity
@SecondaryTable(name = "TRAMITE_TRAF_DUPLICADO")
@org.hibernate.annotations.Table(appliesTo = "TRAMITE_TRAF_DUPLICADO", fetch = FetchMode.SELECT)
@DiscriminatorValue("T8")
public class TramiteTrafDuplicadoVO extends TramiteTraficoVO {

	private static final long serialVersionUID = -8606330068836067885L;

	@Column(table = "TRAMITE_TRAF_DUPLICADO", name = "MOTIVO_DUPLICADO")
	private String motivoDuplicado;

	@Column(table = "TRAMITE_TRAF_DUPLICADO", name = "IMPR_PERMISO_CIRCU")
	private String imprPermisoCirculacion;

	@Column(table = "TRAMITE_TRAF_DUPLICADO", name = "IMPORTACION")
	private String importacion;

	@Column(table = "TRAMITE_TRAF_DUPLICADO", name = "TASA_IMPORTACION")
	private String tasaImportacion;
	
	@Column(table = "TRAMITE_TRAF_DUPLICADO", name = "TASA_FICHA_TECNICA")
	private String tasaFichaTecnica;
	
	@Column(table = "TRAMITE_TRAF_DUPLICADO", name = "TASA_PERMISO")
	private String tasaPermiso;

	@Column(table = "TRAMITE_TRAF_DUPLICADO", name = "TIPO_DOCUMENTO")
	private String tipoDocumento;

	@Column(table = "TRAMITE_TRAF_DUPLICADO", name = "RES_CHECK_DUPL")
	private String resCheckDupl;
	
	public String getMotivoDuplicado() {
		return motivoDuplicado;
	}

	public void setMotivoDuplicado(String motivoDuplicado) {
		this.motivoDuplicado = motivoDuplicado;
	}

	public String getImprPermisoCirculacion() {
		return imprPermisoCirculacion;
	}

	public void setImprPermisoCirculacion(String imprPermisoCirculacion) {
		this.imprPermisoCirculacion = imprPermisoCirculacion;
	}

	public String getImportacion() {
		return importacion;
	}

	public void setImportacion(String importacion) {
		this.importacion = importacion;
	}

	public String getTasaImportacion() {
		return tasaImportacion;
	}

	public void setTasaImportacion(String tasaImportacion) {
		this.tasaImportacion = tasaImportacion;
	}
	
	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getTasaFichaTecnica() {
		return tasaFichaTecnica;
	}

	public void setTasaFichaTecnica(String tasaFichaTecnica) {
		this.tasaFichaTecnica = tasaFichaTecnica;
	}

	public String getTasaPermiso() {
		return tasaPermiso;
	}

	public void setTasaPermiso(String tasaPermiso) {
		this.tasaPermiso = tasaPermiso;
	}

	public String getResCheckDupl() {
		return resCheckDupl;
	}

	public void setResCheckDupl(String resCheckDupl) {
		this.resCheckDupl = resCheckDupl;
	}
}
