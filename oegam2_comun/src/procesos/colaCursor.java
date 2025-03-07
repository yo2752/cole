package procesos;

import java.math.BigDecimal;

/**
 * Método que será donde almacenaremos el cursor de la búsqueda de solicitudes erróneas
 * SELECT c.ID_ENVIO, c.PROCESO, c.COLA, c.FECHA_HORA, c.ESTADO, c.N_INTENTO, c.TIPO_TRAMITE, c.ID_TRAMITE, c.ID_USUARIO, c.XML_ENVIAR, c.NODO, c.RESPUESTA
      FROM COLA c
 * @author juan.gomez
 *
 */
public class colaCursor {
		
	private BigDecimal id_envio; 
	private String proceso; 
	private String cola; 
	private String fecha_hora; //date en base de datos
	private BigDecimal estado; 
	private BigDecimal n_Intento;
	private String tipo_tramite; 
	private BigDecimal id_tramite; 
	private BigDecimal id_usuario; 
	private String xml_Enviar;
	private String nodo;
	private String respuesta;
	private String matricula;
	private String bastidor;
	
	public colaCursor() {
		super();
		
	}


	public BigDecimal getId_envio() {
		return id_envio;
	}

	public void setId_envio(BigDecimal idEnvio) {
		id_envio = idEnvio;
	}

	public String getProceso() {
		return proceso;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
	}

	public String getCola() {
		return cola;
	}

	public void setCola(String cola) {
		this.cola = cola;
	}

	public String getFecha_hora() {
		return fecha_hora;
	}

	public void setFecha_hora(String fechaHora) {
		fecha_hora = fechaHora;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public BigDecimal getN_Intento() {
		return n_Intento;
	}

	public void setN_Intento(BigDecimal nIntento) {
		n_Intento = nIntento;
	}

	public String getTipo_tramite() {
		return tipo_tramite;
	}

	public void setTipo_tramite(String tipoTramite) {
		tipo_tramite = tipoTramite;
	}

	public BigDecimal getId_tramite() {
		return id_tramite;
	}

	public void setId_tramite(BigDecimal idTramite) {
		id_tramite = idTramite;
	}

	public BigDecimal getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(BigDecimal idUsuario) {
		id_usuario = idUsuario;
	}

	public String getXml_Enviar() {
		return xml_Enviar;
	}

	public void setXml_Enviar(String xmlEnviar) {
		xml_Enviar = xmlEnviar;
	}

	public String getNodo() {
		return nodo;
	}

	public void setNodo(String nodo) {
		this.nodo = nodo;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getBastidor() {
		return bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}
	
	
	
}
