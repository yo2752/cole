package escrituras.beans.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * Bean que almacena datos de la tabla PERSONA
 *
 */
/*select rowid_notificacion, ID_NOTIFICACION, TIPO_TRAMITE, descripcion, ID_TRAMITE, 
                FECHA_HORA, ESTADO_ANT, ESTADO_NUE*/

public class NotificacionDao {

	private BigDecimal id_Notificacion;
	private String codigo_aplicacion ; 
	private String tipo_Tramite;
	private String descripcion;
	private BigDecimal id_Tramite;
	private Timestamp fecha_Hora;
	private Integer estado_Ant;
	private Integer estado_Nue;
	public BigDecimal getId_Notificacion() {
		return id_Notificacion;
	}
	public void setId_Notificacion(BigDecimal idNotificacion) {
		id_Notificacion = idNotificacion;
	}
	public String getTipo_Tramite() {
		return tipo_Tramite;
	}
	public void setTipo_Tramite(String tipoTramite) {
		tipo_Tramite = tipoTramite;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public BigDecimal getId_Tramite() {
		return id_Tramite;
	}
	public void setId_Tramite(BigDecimal idTramite) {
		id_Tramite = idTramite;
	}
	public Timestamp getFecha_Hora() {
		return fecha_Hora;
	}
	public void setFecha_Hora(Timestamp fechaHora) {
		fecha_Hora = fechaHora;
	}
	public Integer getEstado_Ant() {
		return estado_Ant;
	}
	public void setEstado_Ant(Integer estadoAnt) {
		estado_Ant = estadoAnt;
	}
	public Integer getEstado_Nue() {
		return estado_Nue;
	}
	public void setEstado_Nue(Integer estadoNue) {
		estado_Nue = estadoNue;
	}
	public String getCodigo_aplicacion() {
		return codigo_aplicacion;
	}
	public void setCodigo_aplicacion(String codigoAplicacion) {
		codigo_aplicacion = codigoAplicacion;
	}
	

	
}