package org.gestoresmadrid.oegamImportacion.schemas.utiles;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoPK;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.personas.model.enumerados.SexoPersona;
import org.gestoresmadrid.core.personas.model.enumerados.SubtipoPersona;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.core.personas.model.vo.PersonaPK;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.core.trafico.inteve.model.vo.TramiteTraficoInteveVO;
import org.gestoresmadrid.core.trafico.inteve.model.vo.TramiteTraficoSolInteveVO;
import org.gestoresmadrid.oegamConversiones.conversion.ParametrosConversiones;
import org.gestoresmadrid.oegamConversiones.jaxb.solicitud.DATOSSOLICITANTE;
import org.gestoresmadrid.oegamConversiones.jaxb.solicitud.DATOSSOLICITUD;
import org.gestoresmadrid.oegamConversiones.jaxb.solicitud.FORMATOOEGAM2SOLICITUD;
import org.gestoresmadrid.oegamConversiones.jaxb.solicitud.TRAMITEINTEVE;
import org.gestoresmadrid.oegamConversiones.schemas.utiles.XMLSolicitudFactory;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoImportacionBean;
import org.gestoresmadrid.oegamImportacion.constantes.Constantes;
import org.gestoresmadrid.oegamImportacion.contrato.service.ServicioContratoImportacion;
import org.gestoresmadrid.oegamImportacion.persona.service.ServicioPersonaImportacion;
import org.gestoresmadrid.oegamImportacion.tasa.service.ServicioTasaImportacion;
import org.gestoresmadrid.oegamImportacion.utiles.UtilidadesAnagramaImportacion;
import org.gestoresmadrid.oegamImportacion.utiles.UtilidadesConversionesImportacion;
import org.gestoresmadrid.oegamImportacion.utiles.UtilidadesNIFValidatorImportacion;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.excepciones.XmlNoValidoExcepcion;

@Component
public class ConversionFormatoImpGAInteve implements Serializable {

	private static final long serialVersionUID = -5306915677645033775L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConversionFormatoImpGAInteve.class);

	@Autowired
	XMLSolicitudFactory xmlSolicitudFactory;

	@Autowired
	UtilidadesAnagramaImportacion utilidadesAnagrama;

	@Autowired
	UtilidadesNIFValidatorImportacion utilidadesNIFValidator;

	@Autowired
	ParametrosConversiones conversiones;

	@Autowired
	ServicioContratoImportacion servicioContrato;

	@Autowired
	ServicioPersonaImportacion servicioPersona;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;
	
	@Autowired
	ServicioTasaImportacion servicioTasa;

	@Autowired
	UtilidadesConversionesImportacion utilidadesConversiones;

	public ResultadoImportacionBean convertirFicheroFormatoGA(File fichero, ContratoVO contrato, Boolean tienePermisoAdmin) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			xmlSolicitudFactory.validarXMLFORMATOOEGAM2INTEVE(fichero, gestorPropiedades.valorPropertie(Constantes.PROPERTIES_RUTA_ESQUEMA_SOLICITUD));
			FORMATOOEGAM2SOLICITUD ficheroInteve = xmlSolicitudFactory.getFormatoSolicitud(fichero);
			if (ficheroInteve.getCABECERA() == null || ficheroInteve.getCABECERA().getDATOSGESTORIA() == null || ficheroInteve.getCABECERA().getDATOSGESTORIA().getPROFESIONAL() == null || "".equals(
					ficheroInteve.getCABECERA().getDATOSGESTORIA().getPROFESIONAL())) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de indicar los datos de la cabecera para poder importar el fichero.");
			} else if (!contrato.getColegiado().getNumColegiado().equals(ficheroInteve.getCABECERA().getDATOSGESTORIA().getPROFESIONAL()) && !tienePermisoAdmin) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Solo se pueden importar tramites del mismo gestor.");
			} else {
				resultado.setFormatoOegam2Solicitud(ficheroInteve);
			}
		} catch (XmlNoValidoExcepcion e) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede importar el fichero por los siguientes errores: " + e.getMensajeError1());
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora convertir el fichero, error", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora convertir el fichero.");
		}
		return resultado;
	}

	public ResultadoImportacionBean convertirFormatoGAToVO(FORMATOOEGAM2SOLICITUD formatoOegam2Solicitud, ContratoVO contrato, Boolean tienePermisoAdmin, String tipoCreacion, Long idUsuario) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		int posicion = 0;
		int cont = 1;
		for (TRAMITEINTEVE solicitudImportar : formatoOegam2Solicitud.getTRAMITEINTEVE()) {
			log.error("INICIO CONVERTIR VO Solicitud tramite " + cont + " del colegiado " + contrato.getColegiado().getNumColegiado());
			TramiteTraficoInteveVO tramiteInteve = convertirTramiteFormatoOegam2InteveToVO(formatoOegam2Solicitud.getCABECERA().getDATOSGESTORIA().getPROFESIONAL()
					,solicitudImportar,contrato,tienePermisoAdmin,tipoCreacion,idUsuario);
			List<IntervinienteTraficoVO> listaIntervinientes = new ArrayList<IntervinienteTraficoVO>();
			if (solicitudImportar.getDATOSSOLICITANTE() != null) {
				IntervinienteTraficoVO interviniente = convertirSolicitanteGAtoVO(solicitudImportar.getDATOSSOLICITANTE(), contrato);
				if (interviniente != null) {
					listaIntervinientes.add(interviniente);
				}
			}
			tramiteInteve.setIntervinienteTraficos(listaIntervinientes);
			List<TramiteTraficoSolInteveVO> listaSolicitudes = new ArrayList<TramiteTraficoSolInteveVO>();
			
			if(solicitudImportar.getDATOSSOLICITUD() != null && !solicitudImportar.getDATOSSOLICITUD().isEmpty()) {
				for(DATOSSOLICITUD datosSolicitud : solicitudImportar.getDATOSSOLICITUD()) {
					TramiteTraficoSolInteveVO tramiteSolInteve = new TramiteTraficoSolInteveVO();
					if(StringUtils.isNotBlank(datosSolicitud.getMATRICULA())) {
						tramiteSolInteve.setMatricula(datosSolicitud.getMATRICULA().replace(" ", "").replace("-", "").replace("_", ""));
					} 
					if(StringUtils.isNotBlank(datosSolicitud.getBASTIDOR())) {
						tramiteSolInteve.setBastidor(datosSolicitud.getBASTIDOR().toUpperCase());
					}
					if(StringUtils.isNotBlank(datosSolicitud.getNIVE())) {
						tramiteSolInteve.setNive(datosSolicitud.getNIVE().toUpperCase());
					}
					if(StringUtils.isNotBlank(datosSolicitud.getCODIGOTASA())) {
						tramiteSolInteve.setCodigoTasa(datosSolicitud.getCODIGOTASA());
					}
					if(StringUtils.isNotBlank(datosSolicitud.getTIPOINFORME())) {
						tramiteSolInteve.setTipoInforme(datosSolicitud.getTIPOINFORME());
					}
					tramiteSolInteve.setEstado(EstadoTramiteTrafico.Iniciado.getValorEnum());
					listaSolicitudes.add(tramiteSolInteve);
				}
			}
			tramiteInteve.setTramitesInteves(listaSolicitudes);
			resultado.addListaTramiteInteve(tramiteInteve, posicion);
			log.error("FIN CONVERTIR VO Solicitud tramite " + cont + " del colegiado " + contrato.getColegiado().getNumColegiado());
			cont++;
			posicion++;
		}
		return resultado;
	}


	private TramiteTraficoInteveVO convertirTramiteFormatoOegam2InteveToVO(String profesional,
			TRAMITEINTEVE solicitudImportar, ContratoVO contrato, Boolean tienePermisoAdmin, String tipoCreacion,
			Long idUsuario) {
		TramiteTraficoInteveVO tramiteInteve  = new TramiteTraficoInteveVO();
		if(solicitudImportar.getREFERENCIAPROPIA() != null) {
			tramiteInteve.setRefPropia(solicitudImportar.getREFERENCIAPROPIA());
		}
		tramiteInteve.setNumColegiado(contrato.getColegiado().getNumColegiado());
		tramiteInteve.setContrato(contrato);
		tramiteInteve.setTipoTramite(TipoTramiteTrafico.Inteve.getValorEnum());
		UsuarioVO usuario = new UsuarioVO();
		usuario.setIdUsuario(idUsuario.longValue());
		tramiteInteve.setUsuario(usuario);
		tramiteInteve.setFechaAlta(utilesFecha.getFechaActualDesfaseBBDD());
		tramiteInteve.setFechaUltModif(tramiteInteve.getFechaAlta());
		tramiteInteve.setJefaturaTrafico(contrato.getJefaturaTrafico());
		tramiteInteve.setIdTipoCreacion(new BigDecimal(tipoCreacion));
		tramiteInteve.setEstado(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));
		
		return tramiteInteve;
	}

	private IntervinienteTraficoVO convertirSolicitanteGAtoVO(DATOSSOLICITANTE solicitante, ContratoVO contrato) {
		IntervinienteTraficoVO interviniente = null;
		if (solicitante != null && StringUtils.isNotBlank(solicitante.getNIFCIF())) {
			interviniente = new IntervinienteTraficoVO();
			IntervinienteTraficoPK idInterviniente = new IntervinienteTraficoPK();
			idInterviniente.setNumColegiado(contrato.getColegiado().getNumColegiado());
			idInterviniente.setNif(solicitante.getNIFCIF().toUpperCase());
			idInterviniente.setTipoInterviniente(TipoInterviniente.Solicitante.getValorEnum());
			interviniente.setId(idInterviniente);

			PersonaVO persona = new PersonaVO();
			PersonaPK idPersona = new PersonaPK();
			if (StringUtils.isNotBlank(solicitante.getNIFCIF())) {
				idPersona.setNif(solicitante.getNIFCIF().toUpperCase());
			}
			idPersona.setNumColegiado(contrato.getColegiado().getNumColegiado());
			persona.setId(idPersona);

			if (StringUtils.isNotBlank(solicitante.getAPELLIDO1RAZONSOCIAL())) {
				persona.setApellido1RazonSocial(solicitante.getAPELLIDO1RAZONSOCIAL().toUpperCase());
			} else {
				PersonaVO personaBBDD = servicioPersona.getPersonaVO(persona.getId().getNif(), persona.getId().getNumColegiado());
				if (personaBBDD != null) {
					persona.setApellido1RazonSocial(personaBBDD.getApellido1RazonSocial());
				}
			}

			if (StringUtils.isNotBlank(solicitante.getAPELLIDO2())) {
				persona.setApellido2(solicitante.getAPELLIDO2().toUpperCase());
			}
			if (StringUtils.isNotBlank(solicitante.getNOMBRE())) {
				persona.setNombre(solicitante.getNOMBRE().toUpperCase());
			}

			setDatosPersona(persona);

			persona.setEstado(new Long(1));
			interviniente.setPersona(persona);

		}
		return interviniente;
	}

	private PersonaVO setDatosPersona(PersonaVO persona) {
		int tipo = utilidadesNIFValidator.isValidDniNieCif(persona.getId().getNif().toUpperCase());
		if (tipo >= 0) {
			if (tipo == utilidadesNIFValidator.FISICA) {
				String anagrama = utilidadesAnagrama.obtenerAnagramaFiscal(persona.getApellido1RazonSocial(), persona.getId().getNif());
				if (anagrama != null && !anagrama.isEmpty()) {
					persona.setAnagrama(anagrama);
				}
				persona.setTipoPersona(TipoPersona.Fisica.getValorEnum());
				persona.setSubtipo(null);
			} else {
				persona.setSexo(SexoPersona.Juridica.getNombreEnum());
				persona.setApellido2(null);
				persona.setAnagrama(null);
				persona.setFechaNacimiento(null);
				persona.setTipoPersona(TipoPersona.Juridica.getValorEnum());
				if ((persona.getSubtipo() == null || persona.getSubtipo().isEmpty()) && tipo == utilidadesNIFValidator.JURIDICA_PUBLICA) {
					persona.setSubtipo(SubtipoPersona.Publica.getNombreEnum());
				} else if ((persona.getSubtipo() == null || persona.getSubtipo().isEmpty()) && tipo == utilidadesNIFValidator.JURIDICA_PRIVADA) {
					persona.setSubtipo(SubtipoPersona.Privada.getNombreEnum());
				}
			}
		}
		return persona;
	}

}
