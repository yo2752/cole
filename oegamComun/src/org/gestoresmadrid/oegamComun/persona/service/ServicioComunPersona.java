package org.gestoresmadrid.oegamComun.persona.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.personas.model.vo.EvolucionPersonaVO;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.oegamComun.persona.view.bean.ResultadoPersonaBean;

public interface ServicioComunPersona extends Serializable {

	public static String CONVERSION_PERSONA_BAJA = "personaBaja";
	public static String CONVERSION_PERSONA_DUPLICADO = "personaDuplicado";
	public static String CONVERSION_PERSONA_TITULAR = "personaTitular";
	public static String CONVERSION_PERSONA_INTERGA = "personaInterga";
	public static String CONVERSION_PERSONA_REPRESENTANTE = "personaRepresentante";
	public static String CONVERSION_PERSONA_MATRICULACION = "personaMatriculacion";
	public static String CONVERSION_PERSONA_ARRENDATARIO = "personaArrendatario";
	public static String CONVERSION_PERSONA_REPRESENTANTE_ARRENDATARIO = "personaRepresentanteArrendatario";
	public static String CONVERSION_PERSONA_CONTRATO = "personaRepresentanteArrendatario";
	public static String CONVERSION_PERSONA_INTEGRADOR = "personaIntegrador";
	public static String CONVERSION_PERSONA_MANTENIMIENTO = "personaMantenimiento";
	public static String CONVERSION_PERSONA_JUSTIFICANTE = "personaJustificante";
	public static String CONVERSION_PERSONA_SOCIEDAD = "personaSociedad";
	public static String CONVERSION_PERSONA_CARGO = "personaCargo";
	public static String CONVERSION_PERSONA_CORPME = "personaCorpme";
	public static String CONVERSION_PERSONA_REGISTRO = "personaRegistro";
	public static String CONVERSION_PERSONA_SOLICITANTE = "personaSolicitante";
	
	public static String TIPO_TRAMITE_MANTENIMIENTO = "MTO";

	PersonaVO obtenerColegiadoCompleto(String numColegiado, String nif);

	PersonaVO obtenerColegiadoCompletoConDireccion(String numColegiado, String nif, Long idContrato);

	PersonaVO getPersona(String nif, String numColegiado);

	ResultadoPersonaBean guardarActualizar(PersonaVO personaPantalla, BigDecimal numExpediente, String tipoTramite, Long idUsuario, String conversion);

	ResultadoPersonaBean tratarGuardadoPersona(PersonaVO persona, BigDecimal numExpediente, String tipoTramite, Long idUsuario, String conversion);

	void guardarPersonaConEvolucion(PersonaVO persona, EvolucionPersonaVO evolucion);

	void guardarPersonaSinEvo(PersonaVO persona);
}
