package org.gestoresmadrid.oegam2comun.personas.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.oegam2comun.personas.view.dto.EvolucionPersonaDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.TramiteRegistroDto;
import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;

import escrituras.beans.ResultBean;

public interface ServicioPersona extends Serializable {

	public static String PERSONA = "PERSONA";

	public static String TIPO_TRAMITE_MANTENIMIENTO = "MTO";
	public static String TIPO_TRAMITE_CONTRATO = "CTR";

	public static String CONVERSION_PERSONA_BAJA = "personaBaja";
	public static String CONVERSION_PERSONA_DUPLICADO = "personaDuplicado";
	public static String CONVERSION_PERSONA_TITULAR = "personaTitular";
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

	PersonaDto getPersona(String nif, String numColegiado);

	PersonaVO getPersonaVO(String nif, String numColegiado);

	PersonaDto getPersonaConDireccion(String nif, String numColegiado);

	ResultBean guardarActualizar(PersonaDto persona);

	ResultBean guardarMantenimientoPersona(PersonaDto persona, BigDecimal numExpediente, String tipoTramite, BigDecimal idUsuario, String conversion);

	ResultBean guardarActualizar(PersonaVO personaPantalla, BigDecimal numExpediente, String tipoTramite, UsuarioDto usuario, String conversion);

	ResultBean buscarPersona(String nif, String numColegiado);

	boolean esModificada(PersonaVO personaVO, PersonaVO personaBBDD, EvolucionPersonaDto evolucionPersonaDto);

	PersonaDto obtenerColegiadoCompleto(String numColegiado, BigDecimal idContrato);

	DireccionDto getDireccionActiva(String nif, String numColegiado);

	ResultBean eliminarFusionarDireccion(String numColegiado, String nif, Long idDireccionPrincipal, Long idDireccionBorrar, Short estadoPersonaDireccion);

	ResultBean asignarPrincipal(String numColegiado, String nif, Long idDireccionPrincipal);

	List<PersonaDto> getListaNuevasPersonasPorFecha(Date fechaUltimoEnvioData);

	ResultBean guardarCargo(PersonaDto cargo, BigDecimal numExpediente, String tipoTramite, BigDecimal idUsuario);

	ResultBean guardarSociedad(TramiteRegistroDto tramiteRegistroDto, BigDecimal idUsuario);

	String obtenerCodigoMandato(String numColegiado);

}
