package estadisticas.beans.consulta;

import java.math.BigDecimal;
import java.util.Collection;

import escrituras.beans.Provincia;
import trafico.beans.JefaturaTrafico;
import trafico.beans.MarcaBean;
import trafico.beans.TipoVehiculoBean;
import utilidades.estructuras.FechaFraccionada;

public class ConsultaEstadisticasBean {

	private FechaFraccionada fechaMatriculacion;
	private FechaFraccionada fechaPrimeraMatriculacion;
	private FechaFraccionada fechaPresentacion;
	
	private String estado;
	private String tipoTramite; 
	private String numColegiado; 
	private String numExpediente;
	private String codigoTasa;
	private Provincia provincia; 
	private JefaturaTrafico jefatura; 
	private TipoVehiculoBean tipoVehiculoBean; 
	private MarcaBean marcaBean;
	private int frontal;	
	
	private BigDecimal idTipoCreacion;
	private BigDecimal agrupacion;
	private BigDecimal agrupacionVehiculos;
	private String textoAgrupacion;
	private String password;
	private String numMatricula;
	private String letraMatricula;
	
	private String[] estadoMultiple; 
	
	// Atributos usuados para criterios
	private Collection<Object> listIdUsuarios;
	private String ordenBusqueda;

	public ConsultaEstadisticasBean() {	}	
	
	public Provincia getProvincia() {
		return provincia;
	}
	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}
	public JefaturaTrafico getJefatura() {
		return jefatura;
	}
	public void setJefatura(JefaturaTrafico jefatura) {
		this.jefatura = jefatura;
	}
	public TipoVehiculoBean getTipoVehiculoBean() {
		return tipoVehiculoBean;
	}
	public void setTipoVehiculoBean(TipoVehiculoBean tipoVehiculoBean) {
		this.tipoVehiculoBean = tipoVehiculoBean;
	}
	public MarcaBean getMarcaBean() {
		return marcaBean;
	}
	public void setMarcaBean(MarcaBean marcaBean) {
		this.marcaBean = marcaBean;
	}	
	public ConsultaEstadisticasBean(boolean inicio) {
		fechaMatriculacion = new FechaFraccionada();
	}	
	public String getTipoTramite() {
		return tipoTramite;
	}
	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}
	public String getNumColegiado() {
		return numColegiado;
	}
	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	public BigDecimal getAgrupacion() {
		return agrupacion;
	}
	public void setAgrupacion(BigDecimal agrupacion) {
		this.agrupacion = agrupacion;
	}
	public FechaFraccionada getFechaMatriculacion() {
		return fechaMatriculacion;
	}
	public void setFechaMatriculacion(FechaFraccionada fechaMatriculacion) {
		this.fechaMatriculacion = fechaMatriculacion;
	}	
	public String getTextoAgrupacion() {
		return textoAgrupacion;
	}
	public void setTextoAgrupacion(String textoAgrupacion) {
		this.textoAgrupacion = textoAgrupacion;
	}
	public void setFrontal(int frontal) {
		this.frontal = frontal;
	}
	public int getFrontal() {
		return frontal;
	}	
	public BigDecimal getIdTipoCreacion() {
		return idTipoCreacion;
	}
	public void setIdTipoCreacion(BigDecimal idTipoCreacion) {
		this.idTipoCreacion = idTipoCreacion;
	}
	public FechaFraccionada getFechaPrimeraMatriculacion() {
		return fechaPrimeraMatriculacion;
	}
	public void setFechaPrimeraMatriculacion(
			FechaFraccionada fechaPrimeraMatriculacion) {
		this.fechaPrimeraMatriculacion = fechaPrimeraMatriculacion;
	}
	public BigDecimal getAgrupacionVehiculos() {
		return agrupacionVehiculos;
	}
	public void setAgrupacionVehiculos(BigDecimal agrupacionVehiculos) {
		this.agrupacionVehiculos = agrupacionVehiculos;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNumMatricula() {
		return numMatricula;
	}
	public void setNumMatricula(String numMatricula) {
		this.numMatricula = numMatricula;
	}
	public String getLetraMatricula() {
		return letraMatricula.toUpperCase();
	}
	public void setLetraMatricula(String letraMatricula) {
		this.letraMatricula = letraMatricula;
	}
	public FechaFraccionada getFechaPresentacion() {
		return fechaPresentacion;
	}
	public void setFechaPresentacion(FechaFraccionada fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}
	public String getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getCodigoTasa() {
		return codigoTasa;
	}

	public void setCodigoTasa(String codigoTasa) {
		this.codigoTasa = codigoTasa;
	}

	public Collection<Object> getListIdUsuarios() {
		return listIdUsuarios;
	}

	public void setListIdUsuarios(Collection<Object> listIdUsuarios) {
		this.listIdUsuarios = listIdUsuarios;
	}

	public String getOrdenBusqueda() {
		return ordenBusqueda;
	}

	public void setOrdenBusqueda(String ordenBusqueda) {
		this.ordenBusqueda = ordenBusqueda;
	}

	public String[] getEstadoMultiple() {
		return estadoMultiple;
	}

	public void setEstadoMultiple(String[] estadoMultiple) {
		this.estadoMultiple = estadoMultiple;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}