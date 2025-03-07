package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.Serializable;
import java.util.Date;

import utilidades.estructuras.Fecha;

public class NotarioRegistroDto implements Serializable{

		private static final long serialVersionUID = 916772501876347958L;

		private long codigo;
		private String nombre;
		private String apellido1;
		private String apellido2;
		private String nif;
		private Fecha fecOtorgamientoNotario;
		private Date fecOtorgamiento;
		private String numProtocolo;
		private String tipoPersona;
		private String codProvincia;
		private String codMunicipio;
		private String plazaNotario;

		
		public long getCodigo() {
			return codigo;
		}
		public void setCodigo(long codigoNotario) {
			this.codigo = codigoNotario;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public String getApellido1() {
			return apellido1;
		}
		public void setApellido1(String apellido1) {
			this.apellido1 = apellido1;
		}
		public String getApellido2() {
			return apellido2;
		}
		public void setApellido2(String apellido2) {
			this.apellido2 = apellido2;
		}
		public String getNif() {
			return nif;
		}
		public void setNif(String nif) {
			this.nif = nif;
		}
		public Date getFecOtorgamiento() {
			return fecOtorgamiento;
		}
		public void setFecOtorgamiento(Date fecOtorgamiento) {
			this.fecOtorgamiento = fecOtorgamiento;
		}
		public String getNumProtocolo() {
			return numProtocolo;
		}
		public void setNumProtocolo(String numProtocolo) {
			this.numProtocolo = numProtocolo;
		}
		public String getTipoPersona() {
			return tipoPersona;
		}
		public void setTipoPersona(String tipoPersona) {
			this.tipoPersona = tipoPersona;
		}
		public String getCodProvincia() {
			return codProvincia;
		}
		public void setCodProvincia(String codProvincia) {
			this.codProvincia = codProvincia;
		}
		public String getCodMunicipio() {
			return codMunicipio;
		}
		public void setCodMunicipio(String codMunicipio) {
			this.codMunicipio = codMunicipio;
		}
		public Fecha getFecOtorgamientoNotario() {
			return fecOtorgamientoNotario;
		}
		public void setFecOtorgamientoNotario(Fecha fecOtorgamientoNotario) {
			this.fecOtorgamientoNotario = fecOtorgamientoNotario;
		}
		public String getPlazaNotario() {
			return plazaNotario;
		}
		public void setPlazaNotario(String plazaNotario) {
			this.plazaNotario = plazaNotario;
		}
}
