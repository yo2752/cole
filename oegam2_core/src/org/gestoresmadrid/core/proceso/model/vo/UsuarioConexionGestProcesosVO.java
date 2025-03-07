package org.gestoresmadrid.core.proceso.model.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the USUARIO_CONEXION_GEST_PROCESOS database table.
 */
@Entity
@Table(name = "USUARIO_CONEXION_GEST_PROCESOS")
public class UsuarioConexionGestProcesosVO implements Serializable {

	private static final long serialVersionUID = 6733562362313550434L;

	@Id
	@Column(name = "ID_CONEXION_GEST_PROCESOS")
	@SequenceGenerator(name = "usuario_conexion_gest_procesos_secuencia", sequenceName = "USUARIO_CON_GEST_PROCESOS_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "usuario_conexion_gest_procesos_secuencia")
	private Long idConexionGestProcesos;

	@Column(name = "FECHA_CONEXION")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaConexion;

	@Column(name = "MAQUINA_PROCESO")
	private String maquinaProceso;

	@Column(name = "IP_ACCESO")
	private String ipAcceso;

	@Column(name = "ID_GEST_PROCESOS")
	private Long idGestProcesos;

	@Column(name = "TIPO_OPERACION")
	private String tipoOperacion;

	@Column(name = "PROCESO")
	private String proceso;

	public Long getIdConexionGestProcesos() {
		return idConexionGestProcesos;
	}

	public void setIdConexionGestProcesos(Long idConexionGestProcesos) {
		this.idConexionGestProcesos = idConexionGestProcesos;
	}

	public Date getFechaConexion() {
		return fechaConexion;
	}

	public void setFechaConexion(Date fechaConexion) {
		this.fechaConexion = fechaConexion;
	}

	public String getMaquinaProceso() {
		return maquinaProceso;
	}

	public void setMaquinaProceso(String maquinaProceso) {
		this.maquinaProceso = maquinaProceso;
	}

	public String getIpAcceso() {
		return ipAcceso;
	}

	public void setIpAcceso(String ipAcceso) {
		this.ipAcceso = ipAcceso;
	}

	public Long getIdGestProcesos() {
		return idGestProcesos;
	}

	public void setIdGestProcesos(Long idGestProcesos) {
		this.idGestProcesos = idGestProcesos;
	}

	public String getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public String getProceso() {
		return proceso;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
	}
}