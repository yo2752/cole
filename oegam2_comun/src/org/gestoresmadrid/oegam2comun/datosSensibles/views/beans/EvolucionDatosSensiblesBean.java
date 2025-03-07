package org.gestoresmadrid.oegam2comun.datosSensibles.views.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class EvolucionDatosSensiblesBean implements Serializable{

		private static final long serialVersionUID = 1486638927526736789L;
		
		private BigDecimal id;
		
		private BigDecimal idUsuario;

		private String tipoCambio;

		private String estadoAnterior;

		private String estadoNuevo;

		private Date fechaCambio;

		/**
		 * Guarda el numero de nif, bastidor o matricula
		 */
		private String num;
		
		private String idGrupo;
		
		private String apellidosNombre;
		
		private String origen;

		/**
		 * @return the id
		 */
		public BigDecimal getId() {
			return id;
		}

		/**
		 * @param id the id to set
		 */
		public void setId(BigDecimal id) {
			this.id = id;
		}

		/**
		 * @return the idUsuario
		 */
		public BigDecimal getIdUsuario() {
			return idUsuario;
		}

		/**
		 * @param idUsuario the idUsuario to set
		 */
		public void setIdUsuario(BigDecimal idUsuario) {
			this.idUsuario = idUsuario;
		}

		/**
		 * @return the tipoCambio
		 */
		public String getTipoCambio() {
			return tipoCambio;
		}

		/**
		 * @param tipoCambio the tipoCambio to set
		 */
		public void setTipoCambio(String tipoCambio) {
			this.tipoCambio = tipoCambio;
		}

		/**
		 * @return the estadoAnterior
		 */
		public String getEstadoAnterior() {
			return estadoAnterior;
		}

		/**
		 * @param estadoAnterior the estadoAnterior to set
		 */
		public void setEstadoAnterior(String estadoAnterior) {
			this.estadoAnterior = estadoAnterior;
		}

		/**
		 * @return the estadoNuevo
		 */
		public String getEstadoNuevo() {
			return estadoNuevo;
		}

		/**
		 * @param estadoNuevo the estadoNuevo to set
		 */
		public void setEstadoNuevo(String estadoNuevo) {
			this.estadoNuevo = estadoNuevo;
		}

		/**
		 * @return the fechaCambio
		 */
		public Date getFechaCambio() {
			return fechaCambio;
		}

		/**
		 * @param fechaCambio the fechaCambio to set
		 */
		public void setFechaCambio(Date fechaCambio) {
			this.fechaCambio = fechaCambio;
		}

		/**
		 * @return the num
		 */
		public String getNum() {
			return num;
		}

		/**
		 * @param num the num to set
		 */
		public void setNum(String num) {
			this.num = num;
		}

		/**
		 * @return the idGrupo
		 */
		public String getIdGrupo() {
			return idGrupo;
		}

		/**
		 * @param idGrupo the idGrupo to set
		 */
		public void setIdGrupo(String idGrupo) {
			this.idGrupo = idGrupo;
		}

		/**
		 * @return the apellidosNombre
		 */
		public String getApellidosNombre() {
			return apellidosNombre;
		}

		/**
		 * @param apellidosNombre the apellidosNombre to set
		 */
		public void setApellidosNombre(String apellidosNombre) {
			this.apellidosNombre = apellidosNombre;
		}

		/**
		 * @return the origen
		 */
		public String getOrigen() {
			return origen;
		}

		/**
		 * @param origen the origen to set
		 */
		public void setOrigen(String origen) {
			this.origen = origen;
		}
		

	}

