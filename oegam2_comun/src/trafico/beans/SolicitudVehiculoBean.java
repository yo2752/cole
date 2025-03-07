package trafico.beans;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.tasas.view.beans.Tasa;

import trafico.utiles.enumerados.ResultadoAvpo;
import trafico.utiles.enumerados.TipoTramiteTrafico;

public class SolicitudVehiculoBean implements Serializable {

	private static final long serialVersionUID = -5783647001001964203L;

	private BigDecimal numExpediente;
	private BigDecimal idContrato;
	private TipoTramiteTrafico tipoTramite;
	private VehiculoBean vehiculo;
	private Tasa tasa;
	private String resultado;
	private ResultadoAvpo estado;// 0 (NOK) y 1 (OK Enviado)
	private String referenciaAtem;
	private String motivoInteve;
	private String tipoInforme;

	public SolicitudVehiculoBean() {

	}

	public SolicitudVehiculoBean(boolean iniciar) {
		vehiculo = new VehiculoBean(true);
		tasa = new Tasa();
		tipoInforme = "0";
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public BigDecimal getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(BigDecimal idContrato) {
		this.idContrato = idContrato;
	}

	public TipoTramiteTrafico getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = TipoTramiteTrafico.convertir(tipoTramite);
	}

	public VehiculoBean getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(VehiculoBean vehiculo) {
		this.vehiculo = vehiculo;
	}

	public Tasa getTasa() {
		return tasa;
	}

	public void setTasa(Tasa tasa) {
		this.tasa = tasa;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public ResultadoAvpo getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = ResultadoAvpo.convertir(estado);
	}

	public String getReferenciaAtem() {
		return referenciaAtem;
	}

	public void setReferenciaAtem(String referenciaAtem) {
		this.referenciaAtem = referenciaAtem;
	}

	public String getMotivoInteve() {
		return motivoInteve;
	}

	public void setMotivoInteve(String motivoInteve) {
		this.motivoInteve = motivoInteve;
	}

	public void setTipoTramite(TipoTramiteTrafico tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public void setEstado(ResultadoAvpo estado) {
		this.estado = estado;
	}

	public String getTipoInforme() {
		return tipoInforme;
	}

	public void setTipoInforme(String tipoInforme) {
		this.tipoInforme = tipoInforme;
	}
}
