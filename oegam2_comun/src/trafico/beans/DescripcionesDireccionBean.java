package trafico.beans;

import java.math.BigDecimal;

public class DescripcionesDireccionBean {
	private BigDecimal idDireccion;
	private String idProvincia;
	private String provNombre;
	private String idMunicipio;
	private String muniNombre;
	private String idTipoVia;
	private String tipViaNombre;

	public DescripcionesDireccionBean() {
		super();
	}

	public BigDecimal getIdDireccion() {
		return idDireccion;
	}
	public void setIdDireccion(BigDecimal idDireccion) {
		this.idDireccion = idDireccion;
	}
	public String getIdProvincia() {
		return idProvincia;
	}
	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}
	public String getProvNombre() {
		return provNombre;
	}
	public void setProvNombre(String provNombre) {
		this.provNombre = provNombre;
	}
	public String getIdMunicipio() {
		return idMunicipio;
	}
	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
	}
	public String getMuniNombre() {
		return muniNombre;
	}
	public void setMuniNombre(String muniNombre) {
		this.muniNombre = muniNombre;
	}
	public String getIdTipoVia() {
		return idTipoVia;
	}
	public void setIdTipoVia(String idTipoVia) {
		this.idTipoVia = idTipoVia;
	}
	public String getTipViaNombre() {
		return tipViaNombre;
	}
	public void setTipViaNombre(String tipViaNombre) {
		this.tipViaNombre = tipViaNombre;
	}
}