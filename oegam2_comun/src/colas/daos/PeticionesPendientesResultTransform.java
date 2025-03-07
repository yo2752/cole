package colas.daos;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;

import colas.constantes.ConstantesProcesos;
import colas.utiles.EstadoPeticiones;
import hibernate.entities.trafico.Vehiculo;
import trafico.beans.ConsultaTramiteTraficoFilaTablaResultadoBean;
import trafico.utiles.enumerados.TipoTramiteTrafico;


public class PeticionesPendientesResultTransform extends ConsultaTramiteTraficoFilaTablaResultadoBean {

	public PeticionesPendientesResultTransform(){
		super();
	}

	private PeticionesPendientesResultTransform resultadoTransformado;
	private static final long serialVersionUID = 1L;
	private static SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss");

	private Long idEnvio;
	private String proceso;
	private BigDecimal envios;
	private String hilo;
	private String respuesta;
	private String estadoTramite;
	private String codEstado;
	// Mantis 13880. David Sierra: Se agrega a la consulta
	private String xmlEnviar;
	private Date fechaHora;

	@Override
	public Object transformTuple(Object[] valoresRecuperadosDeBd, String[] arg1) {

		resultadoTransformado = new PeticionesPendientesResultTransform();
		establecerValoresQueNoRequierenTransformacion(valoresRecuperadosDeBd);

		if (valoresRecuperadosDeBd[2] != null){
			// Los valores recuperados de la bd corresponden a un trámite de tráfico
			transformacionValoresDeTramitesTrafico(valoresRecuperadosDeBd);
		} 
		else if(valoresRecuperadosDeBd[3] != null) {
			// Los valores recuperados de la bd corresponden a un trámite de registro
			transformacionValoresDeTramitesRegistro(valoresRecuperadosDeBd);
		}
		else{
			// No se trata de un trámite de tráfico ni de registro
			transformacionValoresDeOtroTipoDeTramites(valoresRecuperadosDeBd);
		}

		return resultadoTransformado;
		
	}

	@Override
	public Vector<String> crearProyecciones() {
		
		Vector<String> proyecciones = new Vector<String>();
		proyecciones.add("idEnvio");
		proyecciones.add("proceso.id.proceso");
		proyecciones.add("tramiteTrafico.numExpediente");
		proyecciones.add("tramiteRegistro.idTramiteRegistro");
		proyecciones.add("vehiculo.bastidor");
		proyecciones.add("vehiculo.matricula");
		proyecciones.add("tramiteTrafico.tipoTramite");
		proyecciones.add("tramiteRegistro.tipoTramite");
		proyecciones.add("nIntento");
		proyecciones.add("cola");
		proyecciones.add("estado");
		proyecciones.add("respuesta");
		proyecciones.add("tramiteTrafico.estado"); // Agregado estadoTramite
		proyecciones.add("xmlEnviar"); // Mantis 13880
		proyecciones.add("fechaHora");// Mantis 22874
		return proyecciones;
		
	}

	// MÉTODOS PRIVADOS

	private void establecerValoresQueNoRequierenTransformacion(Object[] valoresRecuperadosDeBd){
		
		resultadoTransformado.setIdEnvio((Long)valoresRecuperadosDeBd[0]);
		resultadoTransformado.setProceso((String)valoresRecuperadosDeBd[1]);
		resultadoTransformado.setEnvios((BigDecimal)valoresRecuperadosDeBd[8]);
		resultadoTransformado.setHilo((String)valoresRecuperadosDeBd[9]);
		resultadoTransformado.setEstado(EstadoPeticiones.convertirTexto(((BigDecimal)valoresRecuperadosDeBd[10]).toString()));
		resultadoTransformado.setRespuesta((String)valoresRecuperadosDeBd[11]);
		resultadoTransformado.setXmlEnviar((String)valoresRecuperadosDeBd[13]); // Mantis 13880
		resultadoTransformado.setFechaHora((Date)valoresRecuperadosDeBd[14]);// Mantis 22874
		
	}

	private void transformacionValoresDeTramitesTrafico(Object[] valoresRecuperadosDeBd){
		
		resultadoTransformado.setNumExpediente((Long)valoresRecuperadosDeBd[2]);
		Vector<Vehiculo> vectorVehiculo= new Vector<Vehiculo>();
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setBastidor((String)valoresRecuperadosDeBd[4]);
		vehiculo.setMatricula((String)valoresRecuperadosDeBd[5]);
		if (vehiculo.getBastidor() != null || vehiculo.getMatricula() != null){
			vectorVehiculo.add(vehiculo);
		}
		resultadoTransformado.setVehiculo(vectorVehiculo);
		resultadoTransformado.setTipoTramite(TipoTramiteTrafico.convertirTexto((String)valoresRecuperadosDeBd[6]));
		resultadoTransformado.setCodEstado((String)valoresRecuperadosDeBd[12].toString());
		resultadoTransformado.setEstadoTramite(EstadoTramiteTrafico.convertirTexto(((BigDecimal)valoresRecuperadosDeBd[12]).toString())); // Agregado estadoTramite
		
	}

	private void transformacionValoresDeTramitesRegistro(Object[] valoresRecuperadosDeBd){
		BigDecimal idTramiteRegistro = (BigDecimal)valoresRecuperadosDeBd[3];
		resultadoTransformado.setNumExpediente(idTramiteRegistro.longValue());
		Vector<Vehiculo> vectorVehiculo= new Vector<Vehiculo>();
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setBastidor("n.a.");
		vehiculo.setMatricula("n.a.");
		if (vehiculo.getBastidor() != null || vehiculo.getMatricula() != null){
			vectorVehiculo.add(vehiculo);
		}
		resultadoTransformado.setVehiculo(vectorVehiculo);
		resultadoTransformado.setTipoTramite("REGISTRO");
		
	}

	private void transformacionValoresDeOtroTipoDeTramites(Object[] valoresRecuperadosDeBd){
		
		if(valoresRecuperadosDeBd[2] != null && ((String)valoresRecuperadosDeBd[2]).equals(ConstantesProcesos.PROCESO_INFO_WS)){
			Vector<Vehiculo> vectorVehiculo = new Vector<Vehiculo>();
			Vehiculo vehiculo = new Vehiculo();
			vehiculo.setBastidor("");
			vehiculo.setMatricula("");
			if (vehiculo.getBastidor() != null || vehiculo.getMatricula() != null){
				vectorVehiculo.add(vehiculo);
			}
			resultadoTransformado.setVehiculo(vectorVehiculo);
			resultadoTransformado.setTipoTramite("");
		}
		// Mantis 13880. David Sierra: En el caso de que el proceso sea ENTIDADESFINANCIERAS (ProcesoDatosSensiblesEntidadesFinancieras)
		// el valor del campo xmlEnviar se agregara al valor del campo bastidor, ya que para este proceso el valor de xmlEnviar
		// es un bastidor sensible.
		if(valoresRecuperadosDeBd[1] != null && ((String)valoresRecuperadosDeBd[1]).equals(ConstantesProcesos.PROCESO_ENTIDADES_FINANCIERAS)){
			Vector<Vehiculo> vectorVehiculo = new Vector<Vehiculo>();
			Vehiculo vehiculo = new Vehiculo();
			vehiculo.setBastidor((String)valoresRecuperadosDeBd[13]);
			if (vehiculo.getBastidor() != null){
				vectorVehiculo.add(vehiculo);
			}
			resultadoTransformado.setVehiculo(vectorVehiculo);
			resultadoTransformado.setTipoTramite("");
		}
		
	}

	// SETTER Y GETTER

	public Long getIdEnvio() {
		return idEnvio;
	}
	public void setIdEnvio(Long idEnvio) {
		this.idEnvio = idEnvio;
	}
	public String getProceso() {
		return proceso;
	}
	public void setProceso(String proceso) {
		this.proceso = proceso;
	}
	public BigDecimal getEnvios() {
		return envios;
	}
	public void setEnvios(BigDecimal envios) {
		this.envios = envios;
	}
	public String getHilo() {
		return hilo;
	}
	public void setHilo(String hilo) {
		this.hilo = hilo;
	}
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	public PeticionesPendientesResultTransform getResultadoTransformado() {
		return resultadoTransformado;
	}
	public void setResultadoTransformado(
			PeticionesPendientesResultTransform resultadoTransformado) {
		this.resultadoTransformado = resultadoTransformado;
	}

	public String getEstadoTramite() {
		return estadoTramite;
	}

	public void setEstadoTramite(String estadoTramite) {
		this.estadoTramite = estadoTramite;
	}

	public String getXmlEnviar() {
		return xmlEnviar;
	}

	public void setXmlEnviar(String xmlEnviar) {
		this.xmlEnviar = xmlEnviar;
	}
	
	public Date getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}
	
	public String getfechaHora() {
		return formatoFecha.format(fechaHora);
	}

	public void setfechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	public String getCodEstado() {
		return codEstado;
	}

	public void setCodEstado(String codEstado) {
		this.codEstado = codEstado;
	}
	
}