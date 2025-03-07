package org.gestoresmadrid.oegamComun.persona.service.impl;

import java.io.Serializable;

import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.oegamComun.persona.view.bean.ResultadoValPersonaBean;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Component
public class ServicioValidacionPersona implements Serializable{

	private static final long serialVersionUID = 3668288742423225070L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioValidacionPersona.class);

	@Autowired
	Utiles utiles;
	
	@Autowired
	UtilesFecha utilesFecha;
	
	public ResultadoValPersonaBean esModificadaPersona(PersonaVO personaVO, PersonaVO personaBBDD) {
		ResultadoValPersonaBean resultado = new ResultadoValPersonaBean(Boolean.FALSE);
		try {
			if (!utiles.sonIgualesString(personaVO.getApellido1RazonSocial() != null ? personaVO.getApellido1RazonSocial().toUpperCase() : null, personaBBDD.getApellido1RazonSocial() != null
					? personaBBDD.getApellido1RazonSocial().toUpperCase() : null)) {
				resultado.setApellidoAnt(personaBBDD.getApellido1RazonSocial() != null ? personaBBDD.getApellido1RazonSocial().toUpperCase() : null);
				resultado.setEsModificada(Boolean.TRUE);
				resultado.setApellidoNue(personaVO.getApellido1RazonSocial() != null ? personaVO.getApellido1RazonSocial().toUpperCase() : null);
			}
			if (!utiles.sonIgualesString(personaVO.getApellido2() != null ? personaVO.getApellido2().toUpperCase() : null, personaBBDD.getApellido2() != null ? personaBBDD.getApellido2().toUpperCase()
					: null)) {
				resultado.setApellido2Ant(personaBBDD.getApellido2() != null ? personaBBDD.getApellido2().toUpperCase() : null);
				resultado.setEsModificada(Boolean.TRUE);
				resultado.setApellido2Nue(personaVO.getApellido2().toUpperCase());
			}
			if (!utiles.sonIgualesString(personaVO.getNombre() != null ? personaVO.getNombre().toUpperCase() : null, personaBBDD.getNombre() != null ? personaBBDD.getNombre().toUpperCase() : null)) {
				resultado.setNombreAnt(personaBBDD.getNombre() != null ? personaBBDD.getNombre().toUpperCase() : null);
				resultado.setEsModificada(Boolean.TRUE);
				resultado.setNombreNue(personaVO.getNombre() != null ? personaVO.getNombre().toUpperCase() : null);
			}
			if (utilesFecha.compararFechaDate(personaVO.getFechaNacimiento(), personaBBDD.getFechaNacimiento()) != 0) {
				resultado.setFechaNacimientoAnt(personaBBDD.getFechaNacimiento());
				resultado.setEsModificada(Boolean.TRUE);
				resultado.setFechaNacimientoNue(personaVO.getFechaNacimiento());
			}
			if (!utiles.sonIgualesString(personaVO.getAnagrama() != null ? personaVO.getAnagrama().toUpperCase() : null, personaBBDD.getAnagrama() != null ? personaBBDD.getAnagrama().toUpperCase()
					: null)) {
				resultado.setEsModificada(Boolean.TRUE);
				resultado.addTextoModificado("Anagrama,");
			}
			if (!utiles.sonIgualesObjetos(personaVO.getEstado(), personaBBDD.getEstado())) {
				resultado.setEsModificada(Boolean.TRUE);
				resultado.addTextoModificado("Estado,");
			}
			if (!utiles.sonIgualesCombo(personaVO.getTipoPersona(), personaBBDD.getTipoPersona())) {
				resultado.setEsModificada(Boolean.TRUE);
				resultado.addTextoModificado("Tipo Persona,");
			}
			if (!utiles.sonIgualesString(personaVO.getTelefonos(), personaBBDD.getTelefonos())) {
				resultado.setEsModificada(Boolean.TRUE);
				resultado.addTextoModificado("Telefonos,");
			}
			if (!utiles.sonIgualesCombo(personaVO.getEstadoCivil(), personaBBDD.getEstadoCivil())) {
				resultado.setEsModificada(Boolean.TRUE);
				resultado.addTextoModificado("EstadoCivil,");
			}
			if (!utiles.sonIgualesCombo(personaVO.getSexo(), personaBBDD.getSexo())) {
				resultado.setEsModificada(Boolean.TRUE);
				resultado.addTextoModificado("Sexo,");
			}
			if (!utiles.sonIgualesObjetos(personaVO.getSeccion(), personaBBDD.getSeccion())) {
				resultado.setEsModificada(Boolean.TRUE);
				resultado.addTextoModificado("Seccion,");
			}
			if (!utiles.sonIgualesObjetos(personaVO.getHoja(), personaBBDD.getHoja())) {
				resultado.setEsModificada(Boolean.TRUE);
				resultado.addTextoModificado("Hoja,");
			}
			if (!utiles.sonIgualesString(personaVO.getHojaBis(), personaBBDD.getHojaBis())) {
				resultado.setEsModificada(Boolean.TRUE);
				resultado.addTextoModificado("HojaBis,");
			}
			if (!utiles.sonIgualesObjetos(personaVO.getIus(), personaBBDD.getIus())) {
				resultado.setEsModificada(Boolean.TRUE);
				resultado.addTextoModificado("Ius,");
			}
			if (!utiles.sonIgualesString(personaVO.getCorreoElectronico() != null ? personaVO.getCorreoElectronico().toUpperCase() : null, personaBBDD.getCorreoElectronico() != null ? personaBBDD
					.getCorreoElectronico().toUpperCase() : null)) {
				resultado.setEsModificada(Boolean.TRUE);
				resultado.addTextoModificado("Correo Electronico,");
			}
			if (!utiles.sonIgualesString(personaVO.getIban(), personaBBDD.getIban())) {
				resultado.setEsModificada(Boolean.TRUE);
				resultado.addTextoModificado("Iban,");
			}
			if (!utiles.sonIgualesString(personaVO.getNcorpme(), personaBBDD.getNcorpme())) {
				resultado.setEsModificada(Boolean.TRUE);
				resultado.addTextoModificado("Ncorpme,");
			}
			if (!utiles.sonIgualesObjetos(personaVO.getPirpf(), personaBBDD.getPirpf())) {
				resultado.setEsModificada(Boolean.TRUE);
				resultado.addTextoModificado("Pirpf,");
			}
			if (!utiles.sonIgualesString(personaVO.getFa(), personaBBDD.getFa())) {
				resultado.setEsModificada(Boolean.TRUE);
				resultado.addTextoModificado("Fa,");
			}
			if (utilesFecha.compararFechaDate(personaVO.getFechaCaducidadNIF(), personaBBDD.getFechaCaducidadNIF()) != 0) {
				resultado.setEsModificada(Boolean.TRUE);
				resultado.addTextoModificado("Fecha Caducidad Nif,");
			}
			if (utilesFecha.compararFechaDate(personaVO.getFechaCaducidadAlternativo(), personaBBDD.getFechaCaducidadAlternativo()) != 0) {
				resultado.setEsModificada(Boolean.TRUE);
				resultado.addTextoModificado("Fecha Caducidad Alternativo,");
			}
			if (!utiles.sonIgualesCombo(personaVO.getTipoDocumentoAlternativo(), personaBBDD.getTipoDocumentoAlternativo())) {
				resultado.setEsModificada(Boolean.TRUE);
				resultado.addTextoModificado("Tipo Documento Alternativo,");
			}
			if (!utiles.sonIgualesCheckBox(personaVO.getIndefinido(), personaBBDD.getIndefinido())) {
				resultado.setEsModificada(Boolean.TRUE);
				resultado.addTextoModificado("Indefinido,");
			}
			if (!utiles.sonIgualesString(personaVO.getCodigoMandato(), personaBBDD.getCodigoMandato())) {
				resultado.setEsModificada(Boolean.TRUE);
				resultado.addTextoModificado("Código Mandato,");
			}
			if (!utiles.sonIgualesString(personaVO.getSubtipo(), personaBBDD.getSubtipo())) {
				resultado.setEsModificada(Boolean.TRUE);
				resultado.addTextoModificado("SubTipo,");
			}
			if (!utiles.sonIgualesString(personaVO.getUsuarioCorpme(), personaBBDD.getUsuarioCorpme())) {
				resultado.setEsModificada(Boolean.TRUE);
				resultado.addTextoModificado("Usuario Corpme,");
			}
			if (!utiles.sonIgualesString(personaVO.getPasswordCorpme(), personaBBDD.getPasswordCorpme())) {
				resultado.setEsModificada(Boolean.TRUE);
				resultado.addTextoModificado("Password Corpme,");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar si la persona habia sido modificada, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de comprobar si la persona habia sido modificada.");
		}
		return resultado;
	}

}
