package hibernate.entities.trafico;
//TODO MPC. Cambio IVTM. Clase añadida.
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The persistent class for the TRAMITE_TRAFICO e IVTM_MATRICULACION database table.
 * 
 */
@Entity
@Table(name = "IVTM_MATRICULACION")
@PrimaryKeyJoinColumn(name = "NUM_EXPEDIENTE")
public class TramiteTraficoIVTM extends TramiteTraficoAbstract implements Serializable {
	private static final long serialVersionUID = 1L;
	@Transient
	public static final String TIPO_TRAMITE = "T1";

	@Column(name = "ESTADO_IVTM")
	private String estadoIvtm;

	@Column(name = "ID_PETICION")
	private String idPeticion;

	@Column(name = "NRC")
	private String nrc;

	public String getEstadoIvtm() {
		return estadoIvtm;
	}

	public void setEstadoIvtm(String estadoIvtm) {
		this.estadoIvtm = estadoIvtm;
	}

	public String getIdPeticion() {
		return idPeticion;
	}

	public void setIdPeticion(String idPeticion) {
		this.idPeticion = idPeticion;
	}

	public String getNrc() {
		return nrc;
	}

	public void setNrc(String nrc) {
		this.nrc = nrc;
	}

}