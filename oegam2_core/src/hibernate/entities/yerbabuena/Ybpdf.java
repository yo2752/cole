package hibernate.entities.yerbabuena;

import hibernate.entities.trafico.TramiteTrafico;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the YBPDF database table.
 * 
 */
@Entity
public class Ybpdf implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_YBPDF")
	private String idYbpdf;
	
	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_ENVIO")
	private Date fecha;

	@Column(name="NUM_COLEGIADO", updatable=false)
	private String numColegiado;

	@Column(name="CARPETA", updatable=false)
	private String carpeta;
	
	@Column(name="ENVIARAYB")
	private int enviarayb;
	
	//bi-directional many-to-one association to TramiteTrafico
	@OneToMany(mappedBy="ybpdf", fetch=FetchType.LAZY)
	private List<TramiteTrafico> tramiteTraficos;

    public Ybpdf() {
    }

	public String getIdYbpdf() {
		return this.idYbpdf;
	}

	public void setIdYbpdf(String idYbpdf) {
		this.idYbpdf = idYbpdf;
	}
	
	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public List<TramiteTrafico> getTramiteTraficos() {
		return this.tramiteTraficos;
	}

	public void setTramiteTraficos(List<TramiteTrafico> tramiteTraficos) {
		this.tramiteTraficos = tramiteTraficos;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getCarpeta() {
		return carpeta;
	}

	public void setCarpeta(String carpeta) {
		this.carpeta = carpeta;
	}

	public int getEnviarayb() {
		return enviarayb;
	}

	public void setEnviarayb(int enviarayb) {
		this.enviarayb = enviarayb;
	}
	
}