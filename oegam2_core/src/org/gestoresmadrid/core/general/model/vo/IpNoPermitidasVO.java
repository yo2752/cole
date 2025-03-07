package org.gestoresmadrid.core.general.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author ext_amt
 *
 */

@Entity
@Table(name="IP_NO_PERMITIDAS")
public class IpNoPermitidasVO implements Serializable{

		//default serial version id, required for serializable classes.
		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy=GenerationType.AUTO)
		@Column(name="ID_IP_NO_PERMITIDAS")
		private long idIP;
		
		@Column(name="IP")
		private String ip;

		@Column(name="PERMITIR_ACCESO")
		private int permitirAcceso;
		
		@Column(name="ENVIAR_CORREO")
		private int enviarCorreo;

		@Column(name="CORREO_ELECTRONICO")
		private String mail;

		@ManyToOne(fetch=FetchType.LAZY)
		@JoinColumn(name="NUM_COLEGIADO")
		private ColegiadoVO colegiado;
		
		@ManyToOne(fetch=FetchType.LAZY)
		@JoinColumn(name="ID_USUARIO")
		private UsuarioVO usuario;
		
		public IpNoPermitidasVO() {
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

		public String getIp() {
			return ip;
		}

		public void setIp(String ip) {
			this.ip = ip;
		}

		public String getMail() {
			return mail;
		}

		public void setMail(String mail) {
			this.mail = mail;
		}

		public long getIdIP() {
			return idIP;
		}

		public void setIdIP(long idIP) {
			this.idIP = idIP;
		}
		
		public int getPermitirAcceso() {
			return permitirAcceso;
		}

		public void setPermitirAcceso(int permitirAcceso) {
			this.permitirAcceso = permitirAcceso;
		}

		public int getEnviarCorreo() {
			return enviarCorreo;
		}

		public void setEnviarCorreo(int enviarCorreo) {
			this.enviarCorreo = enviarCorreo;
		}

		public ColegiadoVO getColegiado() {
			return colegiado;
		}

		public void setColegiado(ColegiadoVO colegiado) {
			this.colegiado = colegiado;
		}

		public UsuarioVO getUsuario() {
			return usuario;
		}

		public void setUsuario(UsuarioVO usuario) {
			this.usuario = usuario;
		}
}
