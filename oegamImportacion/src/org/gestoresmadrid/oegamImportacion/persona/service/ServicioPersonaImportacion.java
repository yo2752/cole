package org.gestoresmadrid.oegamImportacion.persona.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoBean;

public interface ServicioPersonaImportacion extends Serializable {

	public static String PERSONA = "PERSONA";

	public static String TIPO_TRAMITE_MANTENIMIENTO = "MTO";
	public static String TIPO_TRAMITE_CONTRATO = "CTR";

	public static String CONVERSION_PERSONA_BAJA = "personaBaja";
	public static String CONVERSION_PERSONA_REPRESENTANTE_BAJA = "personaRepresentanteBaja";

	public static String CONVERSION_PERSONA_CTIT = "personaCtit";
	public static String CONVERSION_PERSONA_REPRESENTANTE_CTIT = "personaRepresentanteCtit";
	public static String CONVERSION_PERSONA_CONDUCTOR_CTIT = "personaConductorCtit";

	public static String CONVERSION_PERSONA_MATW = "personaMatw";
	public static String CONVERSION_PERSONA_REPRE_ARRENDATARIO_MATW = "personaRepreArrendatarioMatw";
	public static String CONVERSION_PERSONA_REPRE_TITULAR_MATW = "personaRepreTitularMatw";
	public static String CONVERSION_PERSONA_TITULAR_MATW = "personaTitularMatw";
	public static String CONVERSION_PERSONA_INTEGRADOR = "personaIntegrador";

	public static String CONVERSION_PERSONA_DUPLICADO = "personaDuplicado";
	public static String CONVERSION_PERSONA_REPRE_DUPLICADO = "personaRepreDuplicado";
	
	public static String CONVERSION_PERSONA_SOLICITUD = "personaSolicitud";

	ResultadoBean guardarActualizarImportacion(PersonaVO persona2, BigDecimal numExpediente, String tipoTramite, Long idUsuario, String conversion);

	PersonaVO getPersonaVO(String nif, String numColegiado);

	IntervinienteTraficoVO obtenerColegiadoCompleto(IntervinienteTraficoVO presentador, ContratoVO contrato);
}
