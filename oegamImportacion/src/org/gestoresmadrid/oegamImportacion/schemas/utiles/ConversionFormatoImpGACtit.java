package org.gestoresmadrid.oegamImportacion.schemas.utiles;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.direccion.model.vo.MunicipioVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoPK;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.Estado620;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTransferencia;
import org.gestoresmadrid.core.personas.model.enumerados.SexoPersona;
import org.gestoresmadrid.core.personas.model.enumerados.SubtipoPersona;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.core.personas.model.vo.PersonaPK;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafTranVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegamConversiones.conversion.ParametrosConversiones;
import org.gestoresmadrid.oegamConversiones.jaxb.ctit.DATOS620;
import org.gestoresmadrid.oegamConversiones.jaxb.ctit.DATOSADQUIRIENTE;
import org.gestoresmadrid.oegamConversiones.jaxb.ctit.DATOSARRENDADOR;
import org.gestoresmadrid.oegamConversiones.jaxb.ctit.DATOSCOMPRAVENTA;
import org.gestoresmadrid.oegamConversiones.jaxb.ctit.DATOSTRANSMITENTE;
import org.gestoresmadrid.oegamConversiones.jaxb.ctit.DATOSVEHICULO;
import org.gestoresmadrid.oegamConversiones.jaxb.ctit.FORMATOGA;
import org.gestoresmadrid.oegamConversiones.jaxb.ctit.TRANSMISION;
import org.gestoresmadrid.oegamConversiones.schemas.utiles.XMLCtitFactory;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoImportacionBean;
import org.gestoresmadrid.oegamImportacion.constantes.Constantes;
import org.gestoresmadrid.oegamImportacion.contrato.service.ServicioContratoImportacion;
import org.gestoresmadrid.oegamImportacion.direccion.service.ServicioDireccionImportacion;
import org.gestoresmadrid.oegamImportacion.persona.service.ServicioPersonaImportacion;
import org.gestoresmadrid.oegamImportacion.utiles.UtilidadesAnagramaImportacion;
import org.gestoresmadrid.oegamImportacion.utiles.UtilidadesConversionesImportacion;
import org.gestoresmadrid.oegamImportacion.utiles.UtilidadesNIFValidatorImportacion;
import org.gestoresmadrid.oegamImportacion.vehiculo.service.ServicioMarcaDgtImportacion;
import org.gestoresmadrid.oegamImportacion.vehiculo.service.ServicioVehiculoImportacion;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.excepciones.XmlNoValidoExcepcion;

@Component
public class ConversionFormatoImpGACtit implements Serializable {

	private static final long serialVersionUID = -3590307258757267082L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConversionFormatoImpGACtit.class);

	@Autowired
	XMLCtitFactory xMLCtitFactory;

	@Autowired
	UtilidadesAnagramaImportacion utilidadesAnagrama;

	@Autowired
	UtilidadesConversionesImportacion utilidadesConversiones;

	@Autowired
	UtilidadesNIFValidatorImportacion utilidadesNIFValidator;

	@Autowired
	ServicioMarcaDgtImportacion servicioMarcaDgt;

	@Autowired
	ServicioContratoImportacion servicioContrato;

	@Autowired
	ServicioDireccionImportacion servicioDireccion;

	@Autowired
	ServicioPersonaImportacion servicioPersona;

	@Autowired
	ServicioVehiculoImportacion servicioVehiculo;

	@Autowired
	ParametrosConversiones conversiones;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	public ResultadoImportacionBean convertirFicheroFormatoGA(File fichero, ContratoVO contrato, Boolean tienePermisoAdmin) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			FORMATOGA ficheroCtit = xMLCtitFactory.getFormatoCtit(fichero);
			xMLCtitFactory.validarXMLFORMATOGACtit(fichero, gestorPropiedades.valorPropertie(Constantes.PROPERTIES_RUTA_ESQUEMA_TRANSMISION));
			if (ficheroCtit.getCABECERA() == null || ficheroCtit.getCABECERA().getDATOSGESTORIA() == null || ficheroCtit.getCABECERA().getDATOSGESTORIA().getPROFESIONAL() == null || "".equals(
					ficheroCtit.getCABECERA().getDATOSGESTORIA().getPROFESIONAL())) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de indicar los datos de la cabecera para poder importar el fichero.");
			} else if (!contrato.getColegiado().getNumColegiado().equals(ficheroCtit.getCABECERA().getDATOSGESTORIA().getPROFESIONAL()) && !tienePermisoAdmin) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Solo se pueden importar tramites del mismo gestor.");
			} else {
				resultado.setFormatoOegam2Ctit(ficheroCtit);
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

	public ResultadoImportacionBean convertirFormatoGAToVO(FORMATOGA formatoOegam2Ctit, ContratoVO contrato, Boolean tienePermisoAdmin, String tipoCreacion, Long idUsuario) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		int posicion = 0;
		int cont = 1;
		for (TRANSMISION ctitImportar : formatoOegam2Ctit.getTRANSMISION()) {
			log.error("INICIO CONVERTIR VO CTIT tramite " + cont + " del colegiado " + contrato.getColegiado().getNumColegiado());
			try {
				TramiteTrafTranVO tramiteCtit = convertirTramiteFormatoOegam2CtitToVO(formatoOegam2Ctit.getCABECERA().getDATOSGESTORIA().getPROFESIONAL(), ctitImportar, contrato, tienePermisoAdmin,
						tipoCreacion, idUsuario);
				List<IntervinienteTraficoVO> listaIntervinientes = new ArrayList<IntervinienteTraficoVO>();
				if (ctitImportar.getDATOSVEHICULO() != null) {
					tramiteCtit.setVehiculo(convertirVehiculoGAtoVO(ctitImportar.getDATOSVEHICULO(), ctitImportar.getMATRICULA(), ctitImportar.getDATOS620(), contrato));
				}
				if (ctitImportar.getDATOSADQUIRIENTE() != null) {
					IntervinienteTraficoVO interviniente = convertirAdquirienteGAtoVO(ctitImportar.getDATOSADQUIRIENTE(), contrato);
					if (interviniente != null) {
						listaIntervinientes.add(interviniente);
					}
					interviniente = convertirRepreAdquirienteGAtoVO(ctitImportar.getDATOSADQUIRIENTE(), contrato);
					if (interviniente != null) {
						listaIntervinientes.add(interviniente);
					}
					interviniente = convertirConductorHabitualGAtoVO(ctitImportar.getDATOSADQUIRIENTE(), contrato);
					if (interviniente != null) {
						listaIntervinientes.add(interviniente);
					}
				}
				if (ctitImportar.getDATOSCOMPRAVENTA() != null) {
					IntervinienteTraficoVO interviniente = convertirCompraventaGAtoVO(ctitImportar.getDATOSCOMPRAVENTA(), contrato);
					if (interviniente != null) {
						listaIntervinientes.add(interviniente);
					}
					interviniente = convertirRepreCompraventaGAtoVO(ctitImportar.getDATOSCOMPRAVENTA(), contrato);
					if (interviniente != null) {
						listaIntervinientes.add(interviniente);
					}
				}
				if (ctitImportar.getDATOSTRANSMITENTE() != null) {
					IntervinienteTraficoVO interviniente = convertirTransmitenteGAtoVO(ctitImportar.getDATOSTRANSMITENTE(), tramiteCtit, contrato);
					if (interviniente != null) {
						listaIntervinientes.add(interviniente);
					}
					interviniente = convertirRepreTransmitenteGAtoVO(ctitImportar.getDATOSTRANSMITENTE(), contrato);
					if (interviniente != null) {
						listaIntervinientes.add(interviniente);
					}
					interviniente = convertirPrimerCotitularGAtoVO(ctitImportar.getDATOSTRANSMITENTE(), contrato);
					if (interviniente != null) {
						listaIntervinientes.add(interviniente);
					}
					interviniente = convertirSegundoCotitularGAtoVO(ctitImportar.getDATOSTRANSMITENTE(), contrato);
					if (interviniente != null) {
						listaIntervinientes.add(interviniente);
					}
				}
				if (ctitImportar.getDATOSARRENDADOR() != null) {
					IntervinienteTraficoVO interviniente = convertirArrendadorGAtoVO(ctitImportar.getDATOSARRENDADOR(), contrato);
					if (interviniente != null) {
						listaIntervinientes.add(interviniente);
					}
					interviniente = convertirRepreArrendadorGAtoVO(ctitImportar.getDATOSARRENDADOR(), contrato);
					if (interviniente != null) {
						listaIntervinientes.add(interviniente);
					}
				}
				IntervinienteTraficoVO interviniente = convertirPresentadorGAtoVO(contrato);
				if (interviniente != null) {
					listaIntervinientes.add(interviniente);
				}

				tramiteCtit.setIntervinienteTraficos(listaIntervinientes);

				resultado.addListaTramiteCtit(tramiteCtit, posicion);
				log.error("FiN CONVERTIR VO CTIT tramite " + cont + " del colegiado " + contrato.getColegiado().getNumColegiado());
			} catch (Throwable th) {
				log.error("Error al convertir CTIT tramite " + cont + " colegiado " + contrato.getColegiado().getNumColegiado(), th);
				resultado.addConversionesError(gestionarMensajeErrorConversion(ctitImportar));
			}
			cont++;
			posicion++;
		}
		return resultado;
	}

	private String gestionarMensajeErrorConversion(TRANSMISION ctitImportar) {
		String mensaje = "";
		if (ctitImportar != null) {
			if (ctitImportar.getDATOSVEHICULO() != null && StringUtils.isNotBlank(ctitImportar.getDATOSVEHICULO().getNUMEROBASTIDOR())) {
				mensaje = "Error el convertir el trámite con bastidor: " + ctitImportar.getDATOSVEHICULO().getNUMEROBASTIDOR();
			} else if (StringUtils.isNotBlank(ctitImportar.getMATRICULA())) {
				mensaje = "Error el convertir el trámite con matrícula: " + ctitImportar.getMATRICULA();
			}
		}
		return mensaje;
	}

	private IntervinienteTraficoVO convertirConductorHabitualGAtoVO(DATOSADQUIRIENTE conductor, ContratoVO contrato) {
		IntervinienteTraficoVO interviniente = null;
		if (conductor != null && StringUtils.isNotBlank(conductor.getDNICONDUCTORHABITUAL())) {
			interviniente = new IntervinienteTraficoVO();
			IntervinienteTraficoPK idInterviniente = new IntervinienteTraficoPK();
			idInterviniente.setNumColegiado(contrato.getColegiado().getNumColegiado());
			idInterviniente.setNif(conductor.getDNICONDUCTORHABITUAL().toUpperCase());
			idInterviniente.setTipoInterviniente(TipoInterviniente.ConductorHabitual.getValorEnum());
			interviniente.setId(idInterviniente);

			if (conductor.getFECHAINICIOCONDUCTORHABITUAL() != null && !conductor.getFECHAINICIOCONDUCTORHABITUAL().isEmpty()) {
				interviniente.setFechaInicio(utilesFecha.formatoFecha("dd/MM/yyyy", conductor.getFECHAINICIOCONDUCTORHABITUAL()));
			}

			if (conductor.getFECHAFINCONDUCTORHABITUAL() != null && !conductor.getFECHAFINCONDUCTORHABITUAL().isEmpty()) {
				interviniente.setFechaFin(utilesFecha.formatoFecha("dd/MM/yyyy", conductor.getFECHAFINCONDUCTORHABITUAL()));
			}

			PersonaVO persona = new PersonaVO();
			PersonaPK idPersona = new PersonaPK();
			idPersona.setNif(conductor.getDNICONDUCTORHABITUAL().toUpperCase());
			idPersona.setNumColegiado(contrato.getColegiado().getNumColegiado());
			persona.setId(idPersona);

			if (StringUtils.isNotBlank(conductor.getAPELLIDO1RAZONSOCIALCONDUCTORHABITUAL())) {
				persona.setApellido1RazonSocial(conductor.getAPELLIDO1RAZONSOCIALCONDUCTORHABITUAL().toUpperCase());
			} else {
				PersonaVO personaBBDD = servicioPersona.getPersonaVO(persona.getId().getNif(), persona.getId().getNumColegiado());
				if (personaBBDD != null) {
					persona.setApellido1RazonSocial(personaBBDD.getApellido1RazonSocial());
				}
			}

			persona.setApellido2(conductor.getAPELLIDO2CONDUCTORHABITUAL());
			persona.setNombre(conductor.getNOMBRECONDUCTORHABITUAL());
			persona.setTipoDocumentoAlternativo(conductor.getTIPODOCUMENTOSUSTITUTIVOCONDUCTORHABITUAL());

			if (conductor.getFECHACADUCIDADNIFCONDUCTORHABITUAL() != null && !conductor.getFECHACADUCIDADNIFCONDUCTORHABITUAL().isEmpty()) {
				persona.setFechaCaducidadNIF(utilesFecha.formatoFecha("dd/MM/yyyy", conductor.getFECHACADUCIDADNIFCONDUCTORHABITUAL()));
			}

			if (conductor.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOCONDUCTORHABITUAL() != null && !conductor.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOCONDUCTORHABITUAL().isEmpty()) {
				persona.setFechaCaducidadAlternativo(utilesFecha.formatoFecha("dd/MM/yyyy", conductor.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOCONDUCTORHABITUAL()));
			}

			if (StringUtils.isNotBlank(conductor.getINDEFINIDOCONDUCTORHABITUAL()) && ("SI".equals(conductor.getINDEFINIDOCONDUCTORHABITUAL().toUpperCase()) || "S".equals(conductor
					.getINDEFINIDOCONDUCTORHABITUAL().toUpperCase()))) {
				persona.setIndefinido("S");
			} else {
				persona.setIndefinido("N");
			}

			setDatosPersona(persona);
			persona.setEstado(new Long(1));
			interviniente.setPersona(persona);
		}
		return interviniente;
	}

	private IntervinienteTraficoVO convertirSegundoCotitularGAtoVO(DATOSTRANSMITENTE segundoCotitular, ContratoVO contrato) {
		IntervinienteTraficoVO interviniente = null;
		if (segundoCotitular != null && StringUtils.isNotBlank(segundoCotitular.getDNISEGUNDOCOTITULARTRANSMITENTE())) {
			interviniente = new IntervinienteTraficoVO();
			interviniente.setNumInterviniente(2);
			IntervinienteTraficoPK idInterviniente = new IntervinienteTraficoPK();
			idInterviniente.setNumColegiado(contrato.getColegiado().getNumColegiado());

			idInterviniente.setNif(segundoCotitular.getDNISEGUNDOCOTITULARTRANSMITENTE().toUpperCase());
			idInterviniente.setTipoInterviniente(TipoInterviniente.CotitularTransmision.getValorEnum());
			interviniente.setId(idInterviniente);

			PersonaVO persona = new PersonaVO();
			PersonaPK idPersona = new PersonaPK();
			idPersona.setNif(segundoCotitular.getDNISEGUNDOCOTITULARTRANSMITENTE().toUpperCase());
			idPersona.setNumColegiado(contrato.getColegiado().getNumColegiado());
			persona.setId(idPersona);

			if (StringUtils.isNotBlank(segundoCotitular.getAPELLIDO1RAZONSOCIALSEGUNDOCOTITULARTRANSMITENTE())) {
				persona.setApellido1RazonSocial(segundoCotitular.getAPELLIDO1RAZONSOCIALSEGUNDOCOTITULARTRANSMITENTE().toUpperCase());
			} else {
				PersonaVO personaBBDD = servicioPersona.getPersonaVO(persona.getId().getNif(), persona.getId().getNumColegiado());
				if (personaBBDD != null) {
					persona.setApellido1RazonSocial(personaBBDD.getApellido1RazonSocial());
				}
			}

			persona.setApellido2(segundoCotitular.getAPELLIDO2SEGUNDOCOTITULARTRANSMITENTE());
			persona.setNombre(segundoCotitular.getNOMBRESEGUNDOCOTITULARTRANSMITENTE());
			persona.setSexo(segundoCotitular.getSEXOSEGUNDOCOTITULARTRANSMITENTE());
			persona.setTipoDocumentoAlternativo(segundoCotitular.getTIPODOCUMENTOSUSTITUTIVOSEGUNDOCOTITULARTRANSMITENTE());
			persona.setTelefonos(segundoCotitular.getTELEFONOSEGUNDOCOTITULARTRANSMITENTE());

			String anagrama = utilidadesAnagrama.obtenerAnagramaFiscal(persona.getApellido1RazonSocial(), persona.getId().getNif());
			if (anagrama != null && !anagrama.isEmpty()) {
				persona.setAnagrama(anagrama);
			}

			if (segundoCotitular.getFECHANACIMIENTOSEGUNDOCOTITULARTRANSMITENTE() != null && !segundoCotitular.getFECHANACIMIENTOSEGUNDOCOTITULARTRANSMITENTE().isEmpty()) {
				persona.setFechaNacimiento(utilesFecha.formatoFecha("dd/MM/yyyy", segundoCotitular.getFECHANACIMIENTOSEGUNDOCOTITULARTRANSMITENTE()));
			}

			if (segundoCotitular.getFECHACADUCIDADNIFSEGUNDOCOTITULARTRANSMITENTE() != null && !segundoCotitular.getFECHACADUCIDADNIFSEGUNDOCOTITULARTRANSMITENTE().isEmpty()) {
				persona.setFechaCaducidadNIF(utilesFecha.formatoFecha("dd/MM/yyyy", segundoCotitular.getFECHACADUCIDADNIFSEGUNDOCOTITULARTRANSMITENTE()));
			}

			if (segundoCotitular.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOSEGUNDOCOTITULARTRANSMITENTE() != null && !segundoCotitular.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOSEGUNDOCOTITULARTRANSMITENTE()
					.isEmpty()) {
				persona.setFechaCaducidadAlternativo(utilesFecha.formatoFecha("dd/MM/yyyy", segundoCotitular.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOSEGUNDOCOTITULARTRANSMITENTE()));
			}

			if (StringUtils.isNotBlank(segundoCotitular.getINDEFINIDOSEGUNDOCOTITULARTRANSMITENTE()) && ("SI".equals(segundoCotitular.getINDEFINIDOSEGUNDOCOTITULARTRANSMITENTE().toUpperCase()) || "S"
					.equals(segundoCotitular.getINDEFINIDOSEGUNDOCOTITULARTRANSMITENTE().toUpperCase()))) {
				persona.setIndefinido("S");
			} else {
				persona.setIndefinido("N");
			}

			setDatosPersona(persona);
			persona.setEstado(new Long(1));
			interviniente.setPersona(persona);

			if (StringUtils.isNotBlank(segundoCotitular.getPROVINCIASEGUNDOCOTITULARTRANSMITENTE())) {
				DireccionVO direccion = new DireccionVO();
				direccion.setIdProvincia(utilidadesConversiones.getIdProvinciaFromSiglas(segundoCotitular.getPROVINCIASEGUNDOCOTITULARTRANSMITENTE()));

				MunicipioVO municipio = servicioDireccion.getMunicipioPorNombre(segundoCotitular.getMUNICIPIOSEGUNDOCOTITULARTRANSMITENTE(), direccion.getIdProvincia());
				if (municipio != null && municipio.getId() != null) {
					direccion.setIdMunicipio(municipio.getId().getIdMunicipio());
				}

				direccion.setNombreVia(segundoCotitular.getNOMBREVIADIRECCIONSEGUNDOCOTITULARTRANSMITENTE());
				direccion.setViaSineditar(utilidadesConversiones.getViaSinEditar(direccion.getNombreVia()));
				direccion.setIdTipoVia(utilidadesConversiones.tipoVia(segundoCotitular.getSIGLASDIRECCIONSEGUNDOCOTITULARTRANSMITENTE()));
				direccion.setNumero(segundoCotitular.getNUMERODIRECCIONSEGUNDOCOTITULARTRANSMITENTE());
				direccion.setCodPostal(segundoCotitular.getCPSEGUNDOCOTITULARTRANSMITENTE());
				if (segundoCotitular.getPUEBLOSEGUNDOCOTITULARTRANSMITENTE() != null) {
					direccion.setPueblo(segundoCotitular.getPUEBLOSEGUNDOCOTITULARTRANSMITENTE().toUpperCase());
				}
				direccion.setLetra(segundoCotitular.getLETRADIRECCIONSEGUNDOCOTITULARTRANSMITENTE());
				direccion.setEscalera(segundoCotitular.getESCALERADIRECCIONSEGUNDOCOTITULARTRANSMITENTE());
				direccion.setBloque(segundoCotitular.getBLOQUEDIRECCIONSEGUNDOCOTITULARTRANSMITENTE());
				direccion.setPlanta(segundoCotitular.getPISODIRECCIONSEGUNDOCOTITULARTRANSMITENTE());
				direccion.setPuerta(segundoCotitular.getPUERTADIRECCIONSEGUNDOCOTITULARTRANSMITENTE());

				if (StringUtils.isNotBlank(segundoCotitular.getKMDIRECCIONSEGUNDOCOTITULARTRANSMITENTE())) {
					try {
						direccion.setKm(new BigDecimal(segundoCotitular.getKMDIRECCIONSEGUNDOCOTITULARTRANSMITENTE()));
					} catch (Exception e) {
						direccion.setKm(null);
					}
				}

				if (StringUtils.isNotBlank(segundoCotitular.getHMDIRECCIONSEGUNDOCOTITULARTRANSMITENTE())) {
					try {
						direccion.setHm(new BigDecimal(segundoCotitular.getHMDIRECCIONSEGUNDOCOTITULARTRANSMITENTE()));
					} catch (Exception e) {
						direccion.setHm(null);
					}
				}

				if (StringUtils.isNotBlank(segundoCotitular.getDIRECCIONACTIVASEGUNDOCOTITULARTRANSMITENTE()) && ("SI".equals(segundoCotitular.getDIRECCIONACTIVASEGUNDOCOTITULARTRANSMITENTE()
						.toUpperCase()) || "S".equals(segundoCotitular.getDIRECCIONACTIVASEGUNDOCOTITULARTRANSMITENTE().toUpperCase()))) {
					direccion.setAsignarPrincipal("S");
				} else {
					direccion.setAsignarPrincipal("N");
				}

				interviniente.setDireccion(direccion);
			}
		}
		return interviniente;
	}

	private IntervinienteTraficoVO convertirPrimerCotitularGAtoVO(DATOSTRANSMITENTE primerCotitular, ContratoVO contrato) {
		IntervinienteTraficoVO interviniente = null;
		if (primerCotitular != null && StringUtils.isNotBlank(primerCotitular.getDNIPRIMERCOTITULARTRANSMITENTE())) {
			interviniente = new IntervinienteTraficoVO();
			IntervinienteTraficoPK idInterviniente = new IntervinienteTraficoPK();
			interviniente.setNumInterviniente(1);
			idInterviniente.setNumColegiado(contrato.getColegiado().getNumColegiado());
			idInterviniente.setNif(primerCotitular.getDNIPRIMERCOTITULARTRANSMITENTE().toUpperCase());
			idInterviniente.setTipoInterviniente(TipoInterviniente.CotitularTransmision.getValorEnum());
			interviniente.setId(idInterviniente);

			PersonaVO persona = new PersonaVO();
			PersonaPK idPersona = new PersonaPK();
			idPersona.setNif(primerCotitular.getDNIPRIMERCOTITULARTRANSMITENTE().toUpperCase());
			idPersona.setNumColegiado(contrato.getColegiado().getNumColegiado());
			persona.setId(idPersona);

			if (StringUtils.isNotBlank(primerCotitular.getAPELLIDO1RAZONSOCIALPRIMERCOTITULARTRANSMITENTE())) {
				persona.setApellido1RazonSocial(primerCotitular.getAPELLIDO1RAZONSOCIALPRIMERCOTITULARTRANSMITENTE().toUpperCase());
			} else {
				PersonaVO personaBBDD = servicioPersona.getPersonaVO(persona.getId().getNif(), persona.getId().getNumColegiado());
				if (personaBBDD != null) {
					persona.setApellido1RazonSocial(personaBBDD.getApellido1RazonSocial());
				}
			}

			persona.setApellido2(primerCotitular.getAPELLIDO2PRIMERCOTITULARTRANSMITENTE());
			persona.setNombre(primerCotitular.getNOMBREPRIMERCOTITULARTRANSMITENTE());
			persona.setSexo(primerCotitular.getSEXOPRIMERCOTITULARTRANSMITENTE());
			persona.setTipoDocumentoAlternativo(primerCotitular.getTIPODOCUMENTOSUSTITUTIVOPRIMERCOTITULARTRANSMITENTE());
			persona.setTelefonos(primerCotitular.getTELEFONOPRIMERCOTITULARTRANSMITENTE());

			String anagrama = utilidadesAnagrama.obtenerAnagramaFiscal(persona.getApellido1RazonSocial(), persona.getId().getNif());
			if (anagrama != null && !anagrama.isEmpty()) {
				persona.setAnagrama(anagrama);
			}

			if (primerCotitular.getFECHANACIMIENTOPRIMERCOTITULARTRANSMITENTE() != null && !primerCotitular.getFECHANACIMIENTOPRIMERCOTITULARTRANSMITENTE().isEmpty()) {
				persona.setFechaNacimiento(utilesFecha.formatoFecha("dd/MM/yyyy", primerCotitular.getFECHANACIMIENTOPRIMERCOTITULARTRANSMITENTE()));
			}

			if (primerCotitular.getFECHACADUCIDADNIFPRIMERCOTITULARTRANSMITENTE() != null && !primerCotitular.getFECHACADUCIDADNIFPRIMERCOTITULARTRANSMITENTE().isEmpty()) {
				persona.setFechaCaducidadNIF(utilesFecha.formatoFecha("dd/MM/yyyy", primerCotitular.getFECHACADUCIDADNIFPRIMERCOTITULARTRANSMITENTE()));
			}

			if (primerCotitular.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOPRIMERCOTITULARTRANSMITENTE() != null && !primerCotitular.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOPRIMERCOTITULARTRANSMITENTE()
					.isEmpty()) {
				persona.setFechaCaducidadAlternativo(utilesFecha.formatoFecha("dd/MM/yyyy", primerCotitular.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOPRIMERCOTITULARTRANSMITENTE()));
			}

			if (StringUtils.isNotBlank(primerCotitular.getINDEFINIDOPRIMERCOTITULARTRANSMITENTE()) && ("SI".equals(primerCotitular.getINDEFINIDOPRIMERCOTITULARTRANSMITENTE().toUpperCase()) || "S"
					.equals(primerCotitular.getINDEFINIDOPRIMERCOTITULARTRANSMITENTE().toUpperCase()))) {
				persona.setIndefinido("S");
			} else {
				persona.setIndefinido("N");
			}

			persona.setEstado(new Long(1));
			setDatosPersona(persona);
			interviniente.setPersona(persona);

			if (StringUtils.isNotBlank(primerCotitular.getPROVINCIAPRIMERCOTITULARTRANSMITENTE())) {
				DireccionVO direccion = new DireccionVO();
				direccion.setIdProvincia(utilidadesConversiones.getIdProvinciaFromSiglas(primerCotitular.getPROVINCIAPRIMERCOTITULARTRANSMITENTE()));

				MunicipioVO municipio = servicioDireccion.getMunicipioPorNombre(primerCotitular.getMUNICIPIOPRIMERCOTITULARTRANSMITENTE(), direccion.getIdProvincia());
				if (municipio != null && municipio.getId() != null) {
					direccion.setIdMunicipio(municipio.getId().getIdMunicipio());
				}

				direccion.setNombreVia(primerCotitular.getNOMBREVIADIRECCIONPRIMERCOTITULARTRANSMITENTE());
				direccion.setViaSineditar(utilidadesConversiones.getViaSinEditar(direccion.getNombreVia()));
				direccion.setIdTipoVia(utilidadesConversiones.tipoVia(primerCotitular.getSIGLASDIRECCIONPRIMERCOTITULARTRANSMITENTE()));
				direccion.setNumero(primerCotitular.getNUMERODIRECCIONPRIMERCOTITULARTRANSMITENTE());
				direccion.setCodPostal(primerCotitular.getCPPRIMERCOTITULARTRANSMITENTE());
				if (primerCotitular.getPUEBLOPRIMERCOTITULARTRANSMITENTE() != null) {
					direccion.setPueblo(primerCotitular.getPUEBLOPRIMERCOTITULARTRANSMITENTE().toUpperCase());
				}
				direccion.setLetra(primerCotitular.getLETRADIRECCIONPRIMERCOTITULARTRANSMITENTE());
				direccion.setEscalera(primerCotitular.getESCALERADIRECCIONPRIMERCOTITULARTRANSMITENTE());
				direccion.setBloque(primerCotitular.getBLOQUEDIRECCIONPRIMERCOTITULARTRANSMITENTE());
				direccion.setPlanta(primerCotitular.getPISODIRECCIONPRIMERCOTITULARTRANSMITENTE());
				direccion.setPuerta(primerCotitular.getPUERTADIRECCIONPRIMERCOTITULARTRANSMITENTE());

				if (StringUtils.isNotBlank(primerCotitular.getKMDIRECCIONPRIMERCOTITULARTRANSMITENTE())) {
					try {
						direccion.setKm(new BigDecimal(primerCotitular.getKMDIRECCIONPRIMERCOTITULARTRANSMITENTE()));
					} catch (Exception e) {
						direccion.setKm(null);
					}
				}
				if (StringUtils.isNotBlank(primerCotitular.getHMDIRECCIONPRIMERCOTITULARTRANSMITENTE())) {
					try {
						direccion.setHm(new BigDecimal(primerCotitular.getHMDIRECCIONPRIMERCOTITULARTRANSMITENTE()));
					} catch (Exception e) {
						direccion.setHm(null);
					}
				}

				if (StringUtils.isNotBlank(primerCotitular.getDIRECCIONACTIVAPRIMERCOTITULARTRANSMITENTE()) && ("SI".equals(primerCotitular.getDIRECCIONACTIVAPRIMERCOTITULARTRANSMITENTE()
						.toUpperCase()) || "S".equals(primerCotitular.getDIRECCIONACTIVAPRIMERCOTITULARTRANSMITENTE().toUpperCase()))) {
					direccion.setAsignarPrincipal("S");
				} else {
					direccion.setAsignarPrincipal("N");
				}

				interviniente.setDireccion(direccion);
			}
		}
		return interviniente;
	}

	private IntervinienteTraficoVO convertirRepreArrendadorGAtoVO(DATOSARRENDADOR repreArrendador, ContratoVO contrato) {
		IntervinienteTraficoVO interviniente = null;
		if (repreArrendador != null && StringUtils.isNotBlank(repreArrendador.getDNIREPRESENTANTEARRENDADOR())) {
			interviniente = new IntervinienteTraficoVO();
			IntervinienteTraficoPK idInterviniente = new IntervinienteTraficoPK();
			idInterviniente.setNumColegiado(contrato.getColegiado().getNumColegiado());
			idInterviniente.setNif(repreArrendador.getDNIREPRESENTANTEARRENDADOR().toUpperCase());
			idInterviniente.setTipoInterviniente(TipoInterviniente.RepresentanteArrendatario.getValorEnum());
			interviniente.setId(idInterviniente);

			if (repreArrendador.getFECHAINICIOTUTELAARRENDADOR() != null && !repreArrendador.getFECHAINICIOTUTELAARRENDADOR().isEmpty()) {
				interviniente.setFechaInicio(utilesFecha.formatoFecha("dd/MM/yyyy", repreArrendador.getFECHAINICIOTUTELAARRENDADOR()));
			}

			if (repreArrendador.getFECHAFINTUTELAARRENDADOR() != null && !repreArrendador.getFECHAFINTUTELAARRENDADOR().isEmpty()) {
				interviniente.setFechaFin(utilesFecha.formatoFecha("dd/MM/yyyy", repreArrendador.getFECHAFINTUTELAARRENDADOR()));
			}

			interviniente.setConceptoRepre(repreArrendador.getCONCEPTOREPRESENTANTEARRENDADOR());
			interviniente.setIdMotivoTutela(repreArrendador.getMOTIVOREPRESENTANTEARRENDADOR());
			interviniente.setDatosDocumento(repreArrendador.getDOCUMENTOSREPRESENTANTEARRENDADOR());

			PersonaVO persona = new PersonaVO();
			PersonaPK idPersona = new PersonaPK();
			idPersona.setNif(repreArrendador.getDNIREPRESENTANTEARRENDADOR().toUpperCase());
			idPersona.setNumColegiado(contrato.getColegiado().getNumColegiado());
			persona.setId(idPersona);

			if (StringUtils.isNotBlank(repreArrendador.getNOMBREAPELLIDOSREPRESENTANTEARRENDADOR()) && StringUtils.isBlank(repreArrendador.getNOMBREREPRESENTANTEARRENDADOR()) && StringUtils.isBlank(
					repreArrendador.getAPELLIDO1RAZONSOCIALREPRESENTANTEARRENDADOR())) {
				utilidadesConversiones.separarNombreConComas(persona, repreArrendador.getNOMBREAPELLIDOSREPRESENTANTEARRENDADOR().replace(" ", ","));
			} else {
				if (StringUtils.isNotBlank(repreArrendador.getAPELLIDO1RAZONSOCIALREPRESENTANTEARRENDADOR())) {
					persona.setApellido1RazonSocial(repreArrendador.getAPELLIDO1RAZONSOCIALREPRESENTANTEARRENDADOR().toUpperCase());
				} else {
					PersonaVO personaBBDD = servicioPersona.getPersonaVO(persona.getId().getNif(), persona.getId().getNumColegiado());
					if (personaBBDD != null) {
						persona.setApellido1RazonSocial(personaBBDD.getApellido1RazonSocial());
					}
				}
				persona.setApellido2(repreArrendador.getAPELLIDO2REPRESENTANTEARRENDADOR());
				persona.setNombre(repreArrendador.getNOMBREREPRESENTANTEARRENDADOR());
			}

			if (StringUtils.isNotBlank(repreArrendador.getINDEFINIDOREPRESENTANTEARRENDADOR()) && ("SI".equals(repreArrendador.getINDEFINIDOREPRESENTANTEARRENDADOR().toUpperCase()) || "S".equals(
					repreArrendador.getINDEFINIDOREPRESENTANTEARRENDADOR().toUpperCase()))) {
				persona.setIndefinido("S");
			} else {
				persona.setIndefinido("N");
			}

			setDatosPersona(persona);
			persona.setEstado(new Long(1));
			interviniente.setPersona(persona);
		}
		return interviniente;
	}

	private IntervinienteTraficoVO convertirRepreTransmitenteGAtoVO(DATOSTRANSMITENTE repreTransmitente, ContratoVO contrato) {
		IntervinienteTraficoVO interviniente = null;
		if (repreTransmitente != null && StringUtils.isNotBlank(repreTransmitente.getDNIREPRESENTANTETRANSMITENTE())) {
			interviniente = new IntervinienteTraficoVO();
			IntervinienteTraficoPK idInterviniente = new IntervinienteTraficoPK();
			idInterviniente.setNumColegiado(contrato.getColegiado().getNumColegiado());
			idInterviniente.setNif(repreTransmitente.getDNIREPRESENTANTETRANSMITENTE().toUpperCase());
			idInterviniente.setTipoInterviniente(TipoInterviniente.RepresentanteTransmitente.getValorEnum());
			interviniente.setId(idInterviniente);

			if (repreTransmitente.getFECHAINICIOTUTELATRANSMITENTE() != null && !repreTransmitente.getFECHAINICIOTUTELATRANSMITENTE().isEmpty()) {
				interviniente.setFechaInicio(utilesFecha.formatoFecha("dd/MM/yyyy", repreTransmitente.getFECHAINICIOTUTELATRANSMITENTE()));
			}

			if (repreTransmitente.getFECHAFINTUTELATRANSMITENTE() != null && !repreTransmitente.getFECHAFINTUTELATRANSMITENTE().isEmpty()) {
				interviniente.setFechaFin(utilesFecha.formatoFecha("dd/MM/yyyy", repreTransmitente.getFECHAFINTUTELATRANSMITENTE()));
			}

			interviniente.setConceptoRepre(repreTransmitente.getCONCEPTOREPRESENTANTETRANSMITENTE());
			interviniente.setIdMotivoTutela(repreTransmitente.getMOTIVOREPRESENTANTETRANSMITENTE());
			interviniente.setDatosDocumento(repreTransmitente.getDOCUMENTOSREPRESENTANTETRANSMITENTE());

			PersonaVO persona = new PersonaVO();
			PersonaPK idPersona = new PersonaPK();
			idPersona.setNif(repreTransmitente.getDNIREPRESENTANTETRANSMITENTE().toUpperCase());
			idPersona.setNumColegiado(contrato.getColegiado().getNumColegiado());
			persona.setId(idPersona);

			if (StringUtils.isNotBlank(repreTransmitente.getNOMBREAPELLIDOSREPRESENTANTETRANSMITENTE()) && StringUtils.isBlank(repreTransmitente.getAPELLIDO2REPRESENTANTETRANSMITENTE()) && StringUtils
					.isBlank(repreTransmitente.getAPELLIDO1RAZONSOCIALREPRESENTANTETRANSMITENTE())) {
				utilidadesConversiones.separarNombreConComas(persona, repreTransmitente.getNOMBREAPELLIDOSREPRESENTANTETRANSMITENTE().replace(" ", ","));
			} else {
				if (StringUtils.isNotBlank(repreTransmitente.getAPELLIDO1RAZONSOCIALREPRESENTANTETRANSMITENTE())) {
					persona.setApellido1RazonSocial(repreTransmitente.getAPELLIDO1RAZONSOCIALREPRESENTANTETRANSMITENTE().toUpperCase());
				} else {
					PersonaVO personaBBDD = servicioPersona.getPersonaVO(persona.getId().getNif(), persona.getId().getNumColegiado());
					if (personaBBDD != null) {
						persona.setApellido1RazonSocial(personaBBDD.getApellido1RazonSocial());
					}
				}
				persona.setApellido2(repreTransmitente.getAPELLIDO2REPRESENTANTETRANSMITENTE());
				persona.setNombre(repreTransmitente.getNOMBREREPRESENTANTETRANSMITENTE());
			}

			if (StringUtils.isNotBlank(repreTransmitente.getINDEFINIDOREPRESENTANTETRANSMITENTE()) && ("SI".equals(repreTransmitente.getINDEFINIDOREPRESENTANTETRANSMITENTE().toUpperCase()) || "S"
					.equals(repreTransmitente.getINDEFINIDOREPRESENTANTETRANSMITENTE().toUpperCase()))) {
				persona.setIndefinido("S");
			} else {
				persona.setIndefinido("N");
			}

			setDatosPersona(persona);
			persona.setEstado(new Long(1));
			interviniente.setPersona(persona);
		}
		return interviniente;
	}

	private IntervinienteTraficoVO convertirRepreCompraventaGAtoVO(DATOSCOMPRAVENTA repreCompraventa, ContratoVO contrato) {
		IntervinienteTraficoVO interviniente = null;
		if (repreCompraventa != null && StringUtils.isNotBlank(repreCompraventa.getDNIREPRESENTANTECOMPRAVENTA())) {
			interviniente = new IntervinienteTraficoVO();
			IntervinienteTraficoPK idInterviniente = new IntervinienteTraficoPK();
			idInterviniente.setNumColegiado(contrato.getColegiado().getNumColegiado());
			idInterviniente.setNif(repreCompraventa.getDNIREPRESENTANTECOMPRAVENTA().toUpperCase());
			idInterviniente.setTipoInterviniente(TipoInterviniente.RepresentanteCompraventa.getValorEnum());
			interviniente.setId(idInterviniente);

			if (repreCompraventa.getFECHAINICIOTUTELACOMPRAVENTA() != null && !repreCompraventa.getFECHAINICIOTUTELACOMPRAVENTA().isEmpty()) {
				interviniente.setFechaInicio(utilesFecha.formatoFecha("dd/MM/yyyy", repreCompraventa.getFECHAINICIOTUTELACOMPRAVENTA()));
			}

			if (repreCompraventa.getFECHAFINTUTELACOMPRAVENTA() != null && !repreCompraventa.getFECHAFINTUTELACOMPRAVENTA().isEmpty()) {
				interviniente.setFechaFin(utilesFecha.formatoFecha("dd/MM/yyyy", repreCompraventa.getFECHAFINTUTELACOMPRAVENTA()));
			}

			interviniente.setConceptoRepre(repreCompraventa.getCONCEPTOREPRESENTANTECOMPRAVENTA());
			interviniente.setIdMotivoTutela(repreCompraventa.getMOTIVOREPRESENTANTECOMPRAVENTA());
			interviniente.setDatosDocumento(repreCompraventa.getDOCUMENTOSREPRESENTANTECOMPRAVENTA());

			PersonaVO persona = new PersonaVO();
			PersonaPK idPersona = new PersonaPK();
			idPersona.setNif(repreCompraventa.getDNIREPRESENTANTECOMPRAVENTA().toUpperCase());
			idPersona.setNumColegiado(contrato.getColegiado().getNumColegiado());
			persona.setId(idPersona);

			if (StringUtils.isNotBlank(repreCompraventa.getNOMBREAPELLIDOSREPRESENTANTECOMPRAVENTA()) && StringUtils.isBlank(repreCompraventa.getNOMBREREPRESENTANTECOMPRAVENTA()) && StringUtils
					.isBlank(repreCompraventa.getAPELLIDO1RAZONSOCIALREPRESENTANTECOMPRAVENTA())) {
				utilidadesConversiones.separarNombreConComas(persona, repreCompraventa.getNOMBREAPELLIDOSREPRESENTANTECOMPRAVENTA().replace(" ", ","));
			} else {
				if (StringUtils.isNotBlank(repreCompraventa.getAPELLIDO1RAZONSOCIALREPRESENTANTECOMPRAVENTA())) {
					persona.setApellido1RazonSocial(repreCompraventa.getAPELLIDO1RAZONSOCIALREPRESENTANTECOMPRAVENTA().toUpperCase());
				} else {
					PersonaVO personaBBDD = servicioPersona.getPersonaVO(persona.getId().getNif(), persona.getId().getNumColegiado());
					if (personaBBDD != null) {
						persona.setApellido1RazonSocial(personaBBDD.getApellido1RazonSocial());
					}
				}
				persona.setApellido2(repreCompraventa.getAPELLIDO2REPRESENTANTECOMPRAVENTA());
				persona.setNombre(repreCompraventa.getNOMBREREPRESENTANTECOMPRAVENTA());
			}

			if (StringUtils.isNotBlank(repreCompraventa.getINDEFINIDOREPRESENTANTECOMPRAVENTA()) && ("SI".equals(repreCompraventa.getINDEFINIDOREPRESENTANTECOMPRAVENTA().toUpperCase()) || "S".equals(
					repreCompraventa.getINDEFINIDOREPRESENTANTECOMPRAVENTA().toUpperCase()))) {
				persona.setIndefinido("S");
			} else {
				persona.setIndefinido("N");
			}

			setDatosPersona(persona);
			persona.setEstado(new Long(1));
			interviniente.setPersona(persona);
		}
		return interviniente;
	}

	private IntervinienteTraficoVO convertirRepreAdquirienteGAtoVO(DATOSADQUIRIENTE repreAdquirente, ContratoVO contrato) {
		IntervinienteTraficoVO interviniente = null;
		if (repreAdquirente != null && StringUtils.isNotBlank(repreAdquirente.getDNIREPRESENTANTEADQUIRIENTE())) {
			interviniente = new IntervinienteTraficoVO();
			IntervinienteTraficoPK idInterviniente = new IntervinienteTraficoPK();
			idInterviniente.setNumColegiado(contrato.getColegiado().getNumColegiado());
			idInterviniente.setNif(repreAdquirente.getDNIREPRESENTANTEADQUIRIENTE().toUpperCase());
			idInterviniente.setTipoInterviniente(TipoInterviniente.RepresentanteAdquiriente.getValorEnum());
			interviniente.setId(idInterviniente);

			if (repreAdquirente.getFECHAINICIOTUTELAADQUIERIENTE() != null && !repreAdquirente.getFECHAINICIOTUTELAADQUIERIENTE().isEmpty()) {
				interviniente.setFechaInicio(utilesFecha.formatoFecha("dd/MM/yyyy", repreAdquirente.getFECHAINICIOTUTELAADQUIERIENTE()));
			}

			if (repreAdquirente.getFECHAFINTUTELAADQUIERIENTE() != null && !repreAdquirente.getFECHAFINTUTELAADQUIERIENTE().isEmpty()) {
				interviniente.setFechaFin(utilesFecha.formatoFecha("dd/MM/yyyy", repreAdquirente.getFECHAFINTUTELAADQUIERIENTE()));
			}

			interviniente.setConceptoRepre(repreAdquirente.getCONCEPTOREPRESENTANTEADQUIRIENTE());
			interviniente.setIdMotivoTutela(repreAdquirente.getMOTIVOREPRESENTANTEADQUIRIENTE());
			interviniente.setDatosDocumento(repreAdquirente.getDOCUMENTOSREPRESENTANTEADQUIRIENTE());

			PersonaVO persona = new PersonaVO();
			PersonaPK idPersona = new PersonaPK();
			idPersona.setNif(repreAdquirente.getDNIREPRESENTANTEADQUIRIENTE().toUpperCase());
			idPersona.setNumColegiado(contrato.getColegiado().getNumColegiado());
			persona.setId(idPersona);

			if (StringUtils.isNotBlank(repreAdquirente.getNOMBREAPELLIDOSREPRESENTANTEADQUIRIENTE()) && StringUtils.isBlank(repreAdquirente.getNOMBREREPRESENTANTEADQUIRIENTE()) && StringUtils.isBlank(
					repreAdquirente.getAPELLIDO1RAZONSOCIALREPRESENTANTEADQUIRIENTE())) {
				utilidadesConversiones.separarNombreConComas(persona, repreAdquirente.getNOMBREAPELLIDOSREPRESENTANTEADQUIRIENTE().replace(" ", ","));
			} else {
				if (StringUtils.isNotBlank(repreAdquirente.getAPELLIDO1RAZONSOCIALREPRESENTANTEADQUIRIENTE())) {
					persona.setApellido1RazonSocial(repreAdquirente.getAPELLIDO1RAZONSOCIALREPRESENTANTEADQUIRIENTE().toUpperCase());
				} else {
					PersonaVO personaBBDD = servicioPersona.getPersonaVO(persona.getId().getNif(), persona.getId().getNumColegiado());
					if (personaBBDD != null) {
						persona.setApellido1RazonSocial(personaBBDD.getApellido1RazonSocial());
					}
				}
				persona.setApellido2(repreAdquirente.getAPELLIDO2REPRESENTANTEADQUIRIENTE());
				persona.setNombre(repreAdquirente.getNOMBREREPRESENTANTEADQUIRIENTE());
			}

			persona.setTipoDocumentoAlternativo(repreAdquirente.getTIPODOCUMENTOSUSTITUTIVOREPRESENTANTEADQUIRIENTE());

			if (StringUtils.isNotBlank(repreAdquirente.getINDEFINIDOREPRESENTANTEADQUIRIENTE()) && ("SI".equals(repreAdquirente.getINDEFINIDOREPRESENTANTEADQUIRIENTE().toUpperCase()) || "S".equals(
					repreAdquirente.getINDEFINIDOADQUIRIENTE().toUpperCase()))) {
				persona.setIndefinido("S");
			} else {
				persona.setIndefinido("N");
			}

			if (repreAdquirente.getFECHACADUCIDADNIFREPRESENTANTEADQUIRIENTE() != null && !repreAdquirente.getFECHACADUCIDADNIFREPRESENTANTEADQUIRIENTE().isEmpty()) {
				persona.setFechaCaducidadNIF(utilesFecha.formatoFecha("dd/MM/yyyy", repreAdquirente.getFECHACADUCIDADNIFREPRESENTANTEADQUIRIENTE()));
			}

			if (repreAdquirente.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOREPRESENTANTEADQUIRIENTE() != null && !repreAdquirente.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOREPRESENTANTEADQUIRIENTE().isEmpty()) {
				persona.setFechaCaducidadAlternativo(utilesFecha.formatoFecha("dd/MM/yyyy", repreAdquirente.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOREPRESENTANTEADQUIRIENTE()));
			}

			setDatosPersona(persona);
			persona.setEstado(new Long(1));
			interviniente.setPersona(persona);
		}
		return interviniente;
	}

	private IntervinienteTraficoVO convertirCompraventaGAtoVO(DATOSCOMPRAVENTA compraventa, ContratoVO contrato) {
		IntervinienteTraficoVO interviniente = null;
		if (compraventa != null && StringUtils.isNotBlank(compraventa.getDNICOMPRAVENTA())) {
			interviniente = new IntervinienteTraficoVO();
			IntervinienteTraficoPK idInterviniente = new IntervinienteTraficoPK();
			idInterviniente.setNumColegiado(contrato.getColegiado().getNumColegiado());
			idInterviniente.setNif(compraventa.getDNICOMPRAVENTA().toUpperCase());
			idInterviniente.setTipoInterviniente(TipoInterviniente.Compraventa.getValorEnum());
			interviniente.setId(idInterviniente);

			if (StringUtils.isNotBlank(compraventa.getACTUALIZACIONDOMICILIOCOMPRAVENTA())) {
				interviniente.setCambioDomicilio(compraventa.getACTUALIZACIONDOMICILIOCOMPRAVENTA().toUpperCase());
			}

			if (StringUtils.isNotBlank(compraventa.getAUTONOMOCOMPRAVENTA())) {
				interviniente.setAutonomo(compraventa.getAUTONOMOCOMPRAVENTA().toUpperCase());
			}

			interviniente.setCodigoIae(compraventa.getCODIGOIAECOMPRAVENTA());

			PersonaVO persona = new PersonaVO();
			PersonaPK idPersona = new PersonaPK();
			idPersona.setNif(compraventa.getDNICOMPRAVENTA().toUpperCase());
			idPersona.setNumColegiado(contrato.getColegiado().getNumColegiado());
			persona.setId(idPersona);
			persona.setSexo(compraventa.getSEXOCOMPRAVENTA());
			persona.setTipoDocumentoAlternativo(compraventa.getTIPODOCUMENTOSUSTITUTIVOCOMPRAVENTA());
			persona.setTelefonos(compraventa.getTELEFONOCOMPRAVENTA());

			persona.setApellido1RazonSocial(compraventa.getNOMBREAPELLIDOSCOMPRAVENTA());
			if (StringUtils.isNotBlank(compraventa.getNOMBREAPELLIDOSCOMPRAVENTA()) && StringUtils.isBlank(compraventa.getNOMBRECOMPRAVENTA()) && StringUtils.isBlank(compraventa
					.getAPELLIDO1RAZONSOCIALCOMPRAVENTA())) {
				utilidadesConversiones.separarNombreConComas(persona, compraventa.getNOMBREAPELLIDOSREPRESENTANTECOMPRAVENTA().replace(" ", ","));
			} else {
				if (StringUtils.isNotBlank(compraventa.getAPELLIDO1RAZONSOCIALCOMPRAVENTA())) {
					persona.setApellido1RazonSocial(compraventa.getAPELLIDO1RAZONSOCIALCOMPRAVENTA().toUpperCase());
				} else {
					PersonaVO personaBBDD = servicioPersona.getPersonaVO(persona.getId().getNif(), persona.getId().getNumColegiado());
					if (personaBBDD != null) {
						persona.setApellido1RazonSocial(personaBBDD.getApellido1RazonSocial());
					}
				}
			}

			persona.setApellido2(compraventa.getAPELLIDO2COMPRAVENTA());
			persona.setNombre(compraventa.getNOMBRECOMPRAVENTA());

			if (StringUtils.isNotBlank(compraventa.getINDEFINIDOCOMPRAVENTA()) && ("SI".equals(compraventa.getINDEFINIDOCOMPRAVENTA()) || "S".equals(compraventa.getINDEFINIDOCOMPRAVENTA()
					.toUpperCase()))) {
				persona.setIndefinido("S");
			} else {
				persona.setIndefinido("N");
			}

			if (compraventa.getFECHANACIMIENTOCOMPRAVENTA() != null && !compraventa.getFECHANACIMIENTOCOMPRAVENTA().isEmpty()) {
				persona.setFechaNacimiento(utilesFecha.formatoFecha("dd/MM/yyyy", compraventa.getFECHANACIMIENTOCOMPRAVENTA()));
			}

			if (compraventa.getFECHACADUCIDADNIFCOMPRAVENTA() != null && !compraventa.getFECHACADUCIDADNIFCOMPRAVENTA().isEmpty()) {
				persona.setFechaCaducidadNIF(utilesFecha.formatoFecha("dd/MM/yyyy", compraventa.getFECHACADUCIDADNIFCOMPRAVENTA()));
			}

			if (compraventa.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOCOMPRAVENTA() != null && !compraventa.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOCOMPRAVENTA().isEmpty()) {
				persona.setFechaCaducidadAlternativo(utilesFecha.formatoFecha("dd/MM/yyyy", compraventa.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOCOMPRAVENTA()));
			}

			setDatosPersona(persona);
			persona.setEstado(new Long(1));
			interviniente.setPersona(persona);

			if (StringUtils.isNotBlank(compraventa.getPROVINCIACOMPRAVENTA())) {
				DireccionVO direccion = new DireccionVO();
				direccion.setIdProvincia(utilidadesConversiones.getIdProvinciaFromSiglas(compraventa.getPROVINCIACOMPRAVENTA()));

				MunicipioVO municipio = servicioDireccion.getMunicipioPorNombre(compraventa.getMUNICIPIOCOMPRAVENTA(), direccion.getIdProvincia());
				if (municipio != null && municipio.getId() != null) {
					direccion.setIdMunicipio(municipio.getId().getIdMunicipio());
				}

				direccion.setNombreVia(compraventa.getNOMBREVIADIRECCIONCOMPRAVENTA());
				direccion.setViaSineditar(utilidadesConversiones.getViaSinEditar(direccion.getNombreVia()));
				direccion.setIdTipoVia(utilidadesConversiones.tipoVia(compraventa.getSIGLASDIRECCIONCOMPRAVENTA()));
				direccion.setNumero(compraventa.getNUMERODIRECCIONCOMPRAVENTA());
				direccion.setCodPostal(compraventa.getCPCOMPRAVENTA());
				if (compraventa.getPUEBLODIRECCIONCOMPRAVENTA() != null) {
					direccion.setPueblo(compraventa.getPUEBLODIRECCIONCOMPRAVENTA().toUpperCase());
				}
				direccion.setLetra(compraventa.getLETRADIRECCIONCOMPRAVENTA());
				direccion.setEscalera(compraventa.getESCALERADIRECCIONCOMPRAVENTA());
				direccion.setBloque(compraventa.getBLOQUEDIRECCIONCOMPRAVENTA());
				direccion.setPlanta(compraventa.getPISODIRECCIONCOMPRAVENTA());
				direccion.setPuerta(compraventa.getPUERTADIRECCIONCOMPRAVENTA());

				if (StringUtils.isNotBlank(compraventa.getKMDIRECCIONCOMPRAVENTA())) {
					try {
						direccion.setKm(new BigDecimal(compraventa.getKMDIRECCIONCOMPRAVENTA()));
					} catch (Exception e) {
						direccion.setKm(null);
					}
				}
				if (StringUtils.isNotBlank(compraventa.getHMDIRECCIONCOMPRAVENTA())) {
					try {
						direccion.setHm(new BigDecimal(compraventa.getHMDIRECCIONCOMPRAVENTA()));
					} catch (Exception e) {
						direccion.setHm(null);
					}
				}

				if (StringUtils.isNotBlank(compraventa.getDIRECCIONACTIVACOMPRAVENTA()) && ("SI".equals(compraventa.getDIRECCIONACTIVACOMPRAVENTA().toUpperCase()) || "S".equals(compraventa
						.getDIRECCIONACTIVACOMPRAVENTA().toUpperCase()))) {
					direccion.setAsignarPrincipal("S");
				} else {
					direccion.setAsignarPrincipal("N");
				}

				interviniente.setDireccion(direccion);
			}
		}
		return interviniente;

	}

	private IntervinienteTraficoVO convertirTransmitenteGAtoVO(DATOSTRANSMITENTE transmitente, TramiteTrafTranVO tramiteCtit, ContratoVO contrato) {
		IntervinienteTraficoVO interviniente = null;
		if (transmitente != null && StringUtils.isNotBlank(transmitente.getDNITRANSMITENTE())) {
			interviniente = new IntervinienteTraficoVO();
			IntervinienteTraficoPK idInterviniente = new IntervinienteTraficoPK();
			idInterviniente.setNumColegiado(contrato.getColegiado().getNumColegiado());
			idInterviniente.setNif(transmitente.getDNITRANSMITENTE().toUpperCase());
			idInterviniente.setTipoInterviniente(TipoInterviniente.TransmitenteTrafico.getValorEnum());
			interviniente.setId(idInterviniente);

			if (StringUtils.isNotBlank(transmitente.getAUTONOMOTRANSMITENTE())) {
				interviniente.setAutonomo(transmitente.getAUTONOMOTRANSMITENTE().toUpperCase());
			}

			interviniente.setCodigoIae(transmitente.getCODIGOIAETRANSMITENTE());

			PersonaVO persona = new PersonaVO();
			PersonaPK idPersona = new PersonaPK();
			idPersona.setNif(transmitente.getDNITRANSMITENTE().toUpperCase());
			idPersona.setNumColegiado(contrato.getColegiado().getNumColegiado());
			persona.setId(idPersona);
			persona.setSexo(transmitente.getSEXOTRANSMITENTE());
			persona.setTipoDocumentoAlternativo(transmitente.getTIPODOCUMENTOSUSTITUTIVOTRANSMITENTE());
			persona.setTelefonos(transmitente.getTELEFONOTRANSMITENTE());

			if (StringUtils.isNotBlank(transmitente.getAPELLIDO1RAZONSOCIALTRANSMITENTE())) {
				persona.setApellido1RazonSocial(transmitente.getAPELLIDO1RAZONSOCIALTRANSMITENTE().toUpperCase());
			} else {
				PersonaVO personaBBDD = servicioPersona.getPersonaVO(persona.getId().getNif(), persona.getId().getNumColegiado());
				if (personaBBDD != null) {
					persona.setApellido1RazonSocial(personaBBDD.getApellido1RazonSocial());
				}
			}

			persona.setApellido2(transmitente.getAPELLIDO2TRANSMITENTE());
			persona.setNombre(transmitente.getNOMBRETRANSMITENTE());

			if (StringUtils.isNotBlank(transmitente.getINDEFINIDOTRANSMITENTE()) && ("SI".equals(transmitente.getINDEFINIDOTRANSMITENTE()) || "S".equals(transmitente.getINDEFINIDOTRANSMITENTE()
					.toUpperCase()))) {
				persona.setIndefinido("S");
			} else {
				persona.setIndefinido("N");
			}

			if (transmitente.getFECHANACIMIENTOTRANSMITENTE() != null && !transmitente.getFECHANACIMIENTOTRANSMITENTE().isEmpty()) {
				persona.setFechaNacimiento(utilesFecha.formatoFecha("dd/MM/yyyy", transmitente.getFECHANACIMIENTOTRANSMITENTE()));
			}

			if (transmitente.getFECHACADUCIDADNIFTRANSMITENTE() != null && !transmitente.getFECHACADUCIDADNIFTRANSMITENTE().isEmpty()) {
				persona.setFechaCaducidadNIF(utilesFecha.formatoFecha("dd/MM/yyyy", transmitente.getFECHACADUCIDADNIFTRANSMITENTE()));
			}

			if (transmitente.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOTRANSMITENTE() != null && !transmitente.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOTRANSMITENTE().isEmpty()) {
				persona.setFechaCaducidadAlternativo(utilesFecha.formatoFecha("dd/MM/yyyy", transmitente.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOTRANSMITENTE()));
			}

			setDatosPersona(persona);
			persona.setEstado(new Long(1));
			interviniente.setPersona(persona);

			if (StringUtils.isNotBlank(transmitente.getPROVINCIATRANSMITENTE())) {
				DireccionVO direccion = new DireccionVO();
				direccion.setIdProvincia(utilidadesConversiones.getIdProvinciaFromSiglas(transmitente.getPROVINCIATRANSMITENTE()));

				MunicipioVO municipio = servicioDireccion.getMunicipioPorNombre(transmitente.getMUNICIPIOTRANSMITENTE(), direccion.getIdProvincia());
				if (municipio != null && municipio.getId() != null) {
					direccion.setIdMunicipio(municipio.getId().getIdMunicipio());
				}

				direccion.setNombreVia(transmitente.getNOMBREVIADIRECCIONTRANSMITENTE());
				direccion.setViaSineditar(utilidadesConversiones.getViaSinEditar(direccion.getNombreVia()));
				direccion.setIdTipoVia(utilidadesConversiones.tipoVia(transmitente.getSIGLASDIRECCIONTRANSMITENTE()));
				direccion.setNumero(transmitente.getNUMERODIRECCIONTRANSMITENTE());
				direccion.setCodPostal(transmitente.getCPTRANSMITENTE());
				if (transmitente.getPUEBLOTRANSMITENTE() != null) {
					direccion.setPueblo(transmitente.getPUEBLOTRANSMITENTE().toUpperCase());
				}
				direccion.setLetra(transmitente.getLETRADIRECCIONTRANSMITENTE());
				direccion.setEscalera(transmitente.getESCALERADIRECCIONTRANSMITENTE());
				direccion.setBloque(transmitente.getBLOQUEDIRECCIONTRANSMITENTE());
				direccion.setPlanta(transmitente.getPISODIRECCIONTRANSMITENTE());
				direccion.setPuerta(transmitente.getPUERTADIRECCIONTRANSMITENTE());

				if (StringUtils.isNotBlank(transmitente.getKMDIRECCIONTRANSMITENTE())) {
					try {
						direccion.setKm(new BigDecimal(transmitente.getKMDIRECCIONTRANSMITENTE()));
					} catch (Exception e) {
						direccion.setKm(null);
					}
				}
				if (StringUtils.isNotBlank(transmitente.getHMDIRECCIONTRANSMITENTE())) {
					try {
						direccion.setHm(new BigDecimal(transmitente.getHMDIRECCIONTRANSMITENTE()));
					} catch (Exception e) {
						direccion.setHm(null);
					}
				}

				if (StringUtils.isNotBlank(transmitente.getDIRECCIONACTIVATRANSMITENTE()) && ("SI".equals(transmitente.getDIRECCIONACTIVATRANSMITENTE().toUpperCase()) || "S".equals(transmitente
						.getDIRECCIONACTIVATRANSMITENTE().toUpperCase()))) {
					direccion.setAsignarPrincipal("S");
				} else {
					direccion.setAsignarPrincipal("N");
				}

				interviniente.setDireccion(direccion);
			}

			if (StringUtils.isNotBlank(transmitente.getNUMEROTITULARES())) {
				tramiteCtit.setNumtitulares(new BigDecimal(transmitente.getNUMEROTITULARES()));
			}
		}
		return interviniente;
	}

	private IntervinienteTraficoVO convertirPresentadorGAtoVO(ContratoVO contrato) {
		IntervinienteTraficoVO interviniente = new IntervinienteTraficoVO();

		IntervinienteTraficoPK idInterviniente = new IntervinienteTraficoPK();
		idInterviniente.setNumColegiado(contrato.getColegiado().getNumColegiado());
		idInterviniente.setNif(contrato.getColegiado().getUsuario().getNif().toUpperCase());
		idInterviniente.setTipoInterviniente(TipoInterviniente.PresentadorTrafico.getValorEnum());
		interviniente.setId(idInterviniente);

		interviniente = servicioPersona.obtenerColegiadoCompleto(interviniente, contrato);
		return interviniente;
	}

	private IntervinienteTraficoVO convertirArrendadorGAtoVO(DATOSARRENDADOR arrendador, ContratoVO contrato) {
		IntervinienteTraficoVO interviniente = null;
		if (arrendador != null && StringUtils.isNotBlank(arrendador.getDNIARRENDADOR())) {
			interviniente = new IntervinienteTraficoVO();
			IntervinienteTraficoPK idInterviniente = new IntervinienteTraficoPK();
			idInterviniente.setNumColegiado(contrato.getColegiado().getNumColegiado());
			idInterviniente.setNif(arrendador.getDNIARRENDADOR().toUpperCase());
			idInterviniente.setTipoInterviniente(TipoInterviniente.Arrendatario.getValorEnum());
			interviniente.setId(idInterviniente);

			if (StringUtils.isNotBlank(arrendador.getACTUALIZACIONDOMICILIOARRENDADOR())) {
				interviniente.setCambioDomicilio(arrendador.getACTUALIZACIONDOMICILIOARRENDADOR().toUpperCase());
			}

			if (StringUtils.isNotBlank(arrendador.getAUTONOMOARRENDADOR())) {
				interviniente.setAutonomo(arrendador.getAUTONOMOARRENDADOR().toUpperCase());
			}

			interviniente.setCodigoIae(arrendador.getCODIGOIAEARRENDADOR());

			PersonaVO persona = new PersonaVO();
			PersonaPK idPersona = new PersonaPK();
			idPersona.setNif(arrendador.getDNIARRENDADOR().toUpperCase());
			idPersona.setNumColegiado(contrato.getColegiado().getNumColegiado());
			persona.setId(idPersona);

			if (StringUtils.isNotBlank(arrendador.getAPELLIDO1RAZONSOCIALARRENDADOR())) {
				persona.setApellido1RazonSocial(arrendador.getAPELLIDO1RAZONSOCIALARRENDADOR().toUpperCase());
			} else {
				PersonaVO personaBBDD = servicioPersona.getPersonaVO(persona.getId().getNif(), persona.getId().getNumColegiado());
				if (personaBBDD != null) {
					persona.setApellido1RazonSocial(personaBBDD.getApellido1RazonSocial());
				}
			}

			persona.setApellido2(arrendador.getAPELLIDO2ARRENDADOR());
			persona.setNombre(arrendador.getNOMBREARRENDADOR());
			persona.setSexo(arrendador.getSEXOARRENDADOR());
			persona.setTipoDocumentoAlternativo(arrendador.getTIPODOCUMENTOSUSTITUTIVOARRENDADOR());
			persona.setTelefonos(arrendador.getTELEFONOARRENDADOR());

			if (StringUtils.isNotBlank(arrendador.getINDEFINIDOARRENDADOR()) && ("SI".equals(arrendador.getINDEFINIDOARRENDADOR().toUpperCase()) || "S".equals(arrendador.getINDEFINIDOARRENDADOR()
					.toUpperCase()))) {
				persona.setIndefinido("S");
			} else {
				persona.setIndefinido("N");
			}

			if (arrendador.getFECHANACIMIENTOARRENDADOR() != null && !arrendador.getFECHANACIMIENTOARRENDADOR().isEmpty()) {
				persona.setFechaNacimiento(utilesFecha.formatoFecha("dd/MM/yyyy", arrendador.getFECHANACIMIENTOARRENDADOR()));
			}

			if (arrendador.getFECHACADUCIDADNIFARRENDADOR() != null && !arrendador.getFECHACADUCIDADNIFARRENDADOR().isEmpty()) {
				persona.setFechaCaducidadNIF(utilesFecha.formatoFecha("dd/MM/yyyy", arrendador.getFECHACADUCIDADNIFARRENDADOR()));
			}

			if (arrendador.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOARRENDADOR() != null && !arrendador.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOARRENDADOR().isEmpty()) {
				persona.setFechaCaducidadAlternativo(utilesFecha.formatoFecha("dd/MM/YYYY", arrendador.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOARRENDADOR()));
			}

			persona.setEstado(new Long(1));
			setDatosPersona(persona);
			interviniente.setPersona(persona);

			if (StringUtils.isNotBlank(arrendador.getPROVINCIAARRENDADOR())) {
				DireccionVO direccion = new DireccionVO();
				direccion.setIdProvincia(utilidadesConversiones.getIdProvinciaFromSiglas(arrendador.getPROVINCIAARRENDADOR()));

				MunicipioVO municipio = servicioDireccion.getMunicipioPorNombre(arrendador.getMUNICIPIOARRENDADOR(), direccion.getIdProvincia());
				if (municipio != null && municipio.getId() != null) {
					direccion.setIdMunicipio(municipio.getId().getIdMunicipio());
				}

				direccion.setNombreVia(arrendador.getNOMBREVIADIRECCIONARRENDADOR());
				direccion.setViaSineditar(utilidadesConversiones.getViaSinEditar(direccion.getNombreVia()));
				direccion.setIdTipoVia(utilidadesConversiones.tipoVia(arrendador.getSIGLASDIRECCIONARRENDADOR()));
				direccion.setNumero(arrendador.getNUMERODIRECCIONARRENDADOR());
				direccion.setCodPostal(arrendador.getCPARRENDADOR());
				if (arrendador.getPUEBLODIRECCIONARRENDADOR() != null) {
					direccion.setPueblo(arrendador.getPUEBLODIRECCIONARRENDADOR().toUpperCase());
				}
				direccion.setLetra(arrendador.getLETRADIRECCIONARRENDADOR());
				direccion.setEscalera(arrendador.getESCALERADIRECCIONARRENDADOR());
				direccion.setBloque(arrendador.getBLOQUEDIRECCIONARRENDADOR());
				direccion.setPlanta(arrendador.getPISODIRECCIONARRENDADOR());
				direccion.setPuerta(arrendador.getPUERTADIRECCIONARRENDADOR());

				if (StringUtils.isNotBlank(arrendador.getKMDIRECCIONARRENDADOR())) {
					try {
						direccion.setKm(new BigDecimal(arrendador.getKMDIRECCIONARRENDADOR()));
					} catch (Exception e) {
						direccion.setKm(null);
					}
				}

				if (StringUtils.isNotBlank(arrendador.getHMDIRECCIONARRENDADOR())) {
					try {
						direccion.setHm(new BigDecimal(arrendador.getHMDIRECCIONARRENDADOR()));
					} catch (Exception e) {
						direccion.setHm(null);
					}
				}

				if (StringUtils.isNotBlank(arrendador.getDIRECCIONACTIVAARRENDADOR()) && ("SI".equals(arrendador.getDIRECCIONACTIVAARRENDADOR().toUpperCase()) || "S".equals(arrendador
						.getDIRECCIONACTIVAARRENDADOR().toUpperCase()))) {
					direccion.setAsignarPrincipal("S");
				} else {
					direccion.setAsignarPrincipal("N");
				}

				interviniente.setDireccion(direccion);
			}
		}
		return interviniente;
	}

	private IntervinienteTraficoVO convertirAdquirienteGAtoVO(DATOSADQUIRIENTE adquirente, ContratoVO contrato) {
		IntervinienteTraficoVO interviniente = null;
		if (adquirente != null && StringUtils.isNotBlank(adquirente.getDNIADQUIRIENTE())) {
			interviniente = new IntervinienteTraficoVO();
			IntervinienteTraficoPK idInterviniente = new IntervinienteTraficoPK();
			idInterviniente.setNumColegiado(contrato.getColegiado().getNumColegiado());
			idInterviniente.setNif(adquirente.getDNIADQUIRIENTE().toUpperCase());
			idInterviniente.setTipoInterviniente(TipoInterviniente.Adquiriente.getValorEnum());
			interviniente.setId(idInterviniente);

			if (StringUtils.isNotBlank(adquirente.getACTUALIZACIONDOMICILIOADQUIRIENTE())) {
				interviniente.setCambioDomicilio(adquirente.getACTUALIZACIONDOMICILIOADQUIRIENTE().toUpperCase());
			}

			if (StringUtils.isNotBlank(adquirente.getAUTONOMOADQUIRIENTE())) {
				interviniente.setAutonomo(adquirente.getAUTONOMOADQUIRIENTE().toUpperCase());
			}

			interviniente.setCodigoIae(adquirente.getCODIGOIAEADQUIRIENTE());

			PersonaVO persona = new PersonaVO();
			PersonaPK idPersona = new PersonaPK();
			idPersona.setNif(adquirente.getDNIADQUIRIENTE().toUpperCase());
			idPersona.setNumColegiado(contrato.getColegiado().getNumColegiado());
			persona.setId(idPersona);

			if (StringUtils.isNotBlank(adquirente.getAPELLIDO1RAZONSOCIALADQUIRIENTE())) {
				persona.setApellido1RazonSocial(adquirente.getAPELLIDO1RAZONSOCIALADQUIRIENTE().toUpperCase());
			} else {
				PersonaVO personaBBDD = servicioPersona.getPersonaVO(persona.getId().getNif(), persona.getId().getNumColegiado());
				if (personaBBDD != null) {
					persona.setApellido1RazonSocial(personaBBDD.getApellido1RazonSocial());
				}
			}

			persona.setApellido2(adquirente.getAPELLIDO2ADQUIRIENTE());
			persona.setNombre(adquirente.getNOMBREADQUIRIENTE());
			persona.setSexo(adquirente.getSEXOADQUIRIENTE());
			persona.setTipoDocumentoAlternativo(adquirente.getTIPODOCUMENTOSUSTITUTIVOADQUIRIENTE());
			persona.setTelefonos(adquirente.getTELEFONOADQUIRIENTE());

			if (StringUtils.isNotBlank(adquirente.getINDEFINIDOADQUIRIENTE()) && ("SI".equals(adquirente.getINDEFINIDOADQUIRIENTE().toUpperCase()) || "S".equals(adquirente.getINDEFINIDOADQUIRIENTE()
					.toUpperCase()))) {
				persona.setIndefinido("S");
			} else {
				persona.setIndefinido("N");
			}

			if (adquirente.getFECHANACIMIENTOADQUIRIENTE() != null && !adquirente.getFECHANACIMIENTOADQUIRIENTE().isEmpty()) {
				persona.setFechaNacimiento(utilesFecha.formatoFecha("dd/MM/yyyy", adquirente.getFECHANACIMIENTOADQUIRIENTE()));
			}

			if (adquirente.getFECHACADUCIDADNIFADQUIRIENTE() != null && !adquirente.getFECHACADUCIDADNIFADQUIRIENTE().isEmpty()) {
				persona.setFechaCaducidadNIF(utilesFecha.formatoFecha("dd/MM/yyyy", adquirente.getFECHACADUCIDADNIFADQUIRIENTE()));
			}

			if (adquirente.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOADQUIRIENTE() != null && !adquirente.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOADQUIRIENTE().isEmpty()) {
				persona.setFechaCaducidadAlternativo(utilesFecha.formatoFecha("dd/MM/yyyy", adquirente.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOADQUIRIENTE()));
			}

			setDatosPersona(persona);
			persona.setEstado(new Long(1));
			interviniente.setPersona(persona);

			if (StringUtils.isNotBlank(adquirente.getPROVINCIAADQUIRIENTE())) {
				DireccionVO direccion = new DireccionVO();
				direccion.setIdProvincia(utilidadesConversiones.getIdProvinciaFromSiglas(adquirente.getPROVINCIAADQUIRIENTE()));

				MunicipioVO municipio = servicioDireccion.getMunicipioPorNombre(adquirente.getMUNICIPIOADQUIRIENTE(), direccion.getIdProvincia());
				if (municipio != null && municipio.getId() != null) {
					direccion.setIdMunicipio(municipio.getId().getIdMunicipio());
				}

				direccion.setNombreVia(adquirente.getNOMBREVIADIRECCIONADQUIRIENTE());
				direccion.setViaSineditar(utilidadesConversiones.getViaSinEditar(direccion.getNombreVia()));
				direccion.setIdTipoVia(utilidadesConversiones.tipoVia(adquirente.getSIGLASDIRECCIONADQUIRIENTE()));
				direccion.setNumero(adquirente.getNUMERODIRECCIONADQUIRIENTE());
				direccion.setCodPostal(adquirente.getCPADQUIRIENTE());
				if (adquirente.getPUEBLOADQUIRIENTE() != null) {
					direccion.setPueblo(adquirente.getPUEBLOADQUIRIENTE().toUpperCase());
				}
				direccion.setLetra(adquirente.getLETRADIRECCIONADQUIRIENTE());
				direccion.setEscalera(adquirente.getESCALERADIRECCIONADQUIRIENTE());
				direccion.setBloque(adquirente.getBLOQUEDIRECCIONADQUIRIENTE());
				direccion.setPlanta(adquirente.getPISODIRECCIONADQUIRIENTE());
				direccion.setPuerta(adquirente.getPUERTADIRECCIONADQUIRIENTE());

				if (StringUtils.isNotBlank(adquirente.getKMDIRECCIONADQUIRIENTE())) {
					try {
						direccion.setKm(new BigDecimal(adquirente.getKMDIRECCIONADQUIRIENTE()));
					} catch (Exception e) {
						direccion.setKm(null);
					}
				}

				if (StringUtils.isNotBlank(adquirente.getHMDIRECCIONADQUIRIENTE())) {
					try {
						direccion.setHm(new BigDecimal(adquirente.getHMDIRECCIONADQUIRIENTE()));
					} catch (Exception e) {
						direccion.setHm(null);
					}
				}

				if (StringUtils.isNotBlank(adquirente.getDIRECCIONACTIVAADQUIRIENTE()) && ("SI".equals(adquirente.getDIRECCIONACTIVAADQUIRIENTE().toUpperCase()) || "S".equals(adquirente
						.getDIRECCIONACTIVAADQUIRIENTE().toUpperCase()))) {
					direccion.setAsignarPrincipal("S");
				} else {
					direccion.setAsignarPrincipal("N");
				}

				interviniente.setDireccion(direccion);
			}
		}
		return interviniente;
	}

	private VehiculoVO convertirVehiculoGAtoVO(DATOSVEHICULO vehiculoGa, String matricula, DATOS620 datos620, ContratoVO contrato) {
		VehiculoVO vehiculo = new VehiculoVO();
		vehiculo.setNumColegiado(contrato.getColegiado().getNumColegiado());

		if (matricula != null && !matricula.isEmpty()) {
			vehiculo.setMatricula(matricula.replace(" ", "").replace("-", "").replace("_", ""));
		}

		if (StringUtils.isNotBlank(vehiculoGa.getNUMEROBASTIDOR())) {
			vehiculo.setBastidor(vehiculoGa.getNUMEROBASTIDOR().toUpperCase());
		}

		if (vehiculoGa.getFECHAMATRICULACION() != null && !vehiculoGa.getFECHAMATRICULACION().isEmpty()) {
			vehiculo.setFechaMatriculacion(utilesFecha.formatoFecha("dd/MM/yyyy", vehiculoGa.getFECHAMATRICULACION()));
		}

		if (StringUtils.isNotBlank(vehiculoGa.getSERVICIO())) {
			vehiculo.setIdServicio(vehiculoGa.getSERVICIO());
		}

		if (StringUtils.isNotBlank(vehiculoGa.getSERVICIOANTERIOR())) {
			vehiculo.setIdServicioAnterior(vehiculoGa.getSERVICIOANTERIOR());
		}

		if (vehiculoGa.getFECHACADUCIDADITV() != null && !vehiculoGa.getFECHACADUCIDADITV().isEmpty()) {
			vehiculo.setFechaItv(utilesFecha.formatoFecha("dd/MM/yyyy", vehiculoGa.getFECHACADUCIDADITV()));
		}

		if (vehiculoGa.getFECHAITV() != null && !vehiculoGa.getFECHAITV().isEmpty()) {
			vehiculo.setFechaInspeccion(utilesFecha.formatoFecha("dd/MM/yyyy", vehiculoGa.getFECHAITV()));
		}

		if (StringUtils.isNotBlank(vehiculoGa.getCHECKCADUCIDADITV())) {
			vehiculo.setCheckFechaCaducidadItv(vehiculoGa.getCHECKCADUCIDADITV());
		}

		if (StringUtils.isNotBlank(vehiculoGa.getPLAZAS())) {
			vehiculo.setPlazas(new BigDecimal(vehiculoGa.getPLAZAS()));
		}

		if (StringUtils.isNotBlank(vehiculoGa.getKILOMETRAJE())) {
			try {
				vehiculo.setKmUso(new BigDecimal(vehiculoGa.getKILOMETRAJE()));
			} catch (Exception e) {
				vehiculo.setKmUso(null);
			}
		}

		vehiculo.setModelo(vehiculoGa.getMODELO());
		vehiculo.setTipoVehiculo(vehiculoGa.getTIPOIDVEHICULO());
		vehiculo.setIdMotivoItv(vehiculoGa.getMOTIVOITV());
		vehiculo.setConceptoItv(vehiculoGa.getCONCEPTOITV());
		vehiculo.setEstacionItv(vehiculoGa.getESTACIONITV());
		vehiculo.setTara(vehiculoGa.getTARA());
		vehiculo.setPesoMma(vehiculoGa.getPESOMMA());

		if (StringUtils.isNotBlank(vehiculoGa.getMARCA())) {
			String marca = utilidadesConversiones.getMarcaSinEditar(vehiculoGa.getMARCA());
			vehiculo.setCodigoMarca(servicioMarcaDgt.getCodMarcaPorMarcaSinEditar(marca, Boolean.FALSE));
		}

		if (vehiculoGa.getPROVINCIAPRIMERAMATRICULA() != null && !vehiculoGa.getPROVINCIAPRIMERAMATRICULA().equals("") && !vehiculoGa.getPROVINCIAPRIMERAMATRICULA().equals("-1")) {
			BigDecimal codigoProvincia = new BigDecimal(utilidadesConversiones.getIdProvinciaFromSiglas(vehiculoGa.getPROVINCIAPRIMERAMATRICULA()));
			vehiculo.setProvinciaPrimeraMatricula(codigoProvincia);
		} else {
			vehiculo.setProvinciaPrimeraMatricula(new BigDecimal(-1));
		}

		if (datos620 != null) {

			if (datos620.getFECHADEVENGO() != null && !datos620.getFECHADEVENGO().isEmpty()) {
				vehiculo.setFecdesde(utilesFecha.formatoFecha("dd/MM/yyyy", datos620.getFECHADEVENGO()));
			}

			if (datos620.getFECHAPRIMERAMATRICULACION() != null && !datos620.getFECHAPRIMERAMATRICULACION().isEmpty()) {
				vehiculo.setFechaPrimMatri(utilesFecha.formatoFecha("dd/MM/yyyy", datos620.getFECHAPRIMERAMATRICULACION()));
			}

			if (datos620.getTIPOVEHICULO() != null && !datos620.getTIPOVEHICULO().isEmpty() && ("A".equals(datos620.getTIPOVEHICULO()) || "B".equals(datos620.getTIPOVEHICULO()) || "T".equals(datos620
					.getTIPOVEHICULO()) || "X".equals(datos620.getTIPOVEHICULO()))) {
				vehiculo.setCdmarca(datos620.getMARCA620());
				vehiculo.setCdmodveh(datos620.getMODELO620());
			} else {
				vehiculo.setCdmarca(datos620.getMARCA620DESC());
				vehiculo.setCdmodveh(datos620.getMODELO620DESC());
			}

			if (StringUtils.isNotBlank(datos620.getNUMCILINDROS())) {
				vehiculo.setnCilindros(new BigDecimal(datos620.getNUMCILINDROS()));
			}

			vehiculo.setIdCarburante(datos620.getCARBURANTE());
			vehiculo.setCaracteristicas(datos620.getCARACTERISTICAS());
			//vehiculo.setAnioFabrica(datos620.getANIOFABRICACION());

			vehiculo.setTipvehi(utilidadesConversiones.tipVehiNuevo(datos620.getTIPOVEHICULO()));
			vehiculo.setPotenciaFiscal(utilidadesConversiones.stringToBigDecimalDosDecimales(datos620.getPOTENCIAFISCAL()));

			try {
				vehiculo.setCilindrada(utilidadesConversiones.quitarDecimales(datos620.getCILINDRADA()).toString());
			} catch (Exception e) {
				vehiculo.setCilindrada("");
			}
		}

		return vehiculo;
	}

	private TramiteTrafTranVO convertirTramiteFormatoOegam2CtitToVO(String numColegiadoImportacion, TRANSMISION ctit, ContratoVO contrato, Boolean tienePermisoAdmin, String tipoCreacion,
			Long idUsuario) {
		TramiteTrafTranVO tramite = new TramiteTrafTranVO();
		tramite.setFechaAlta(utilesFecha.getFechaActualDesfaseBBDD());
		tramite.setFechaUltModif(tramite.getFechaAlta());
		tramite.setNumExpediente(null);
		tramite.setContrato(contrato);
		tramite.setYbestado(BigDecimal.ZERO);
		tramite.setNumColegiado(contrato.getColegiado().getNumColegiado());
		tramite.setRefPropia(ctit.getREFERENCIAPROPIA());
		tramite.setJefaturaTrafico(contrato.getJefaturaTrafico());
		tramite.setTipoTramite(TipoTramiteTrafico.TransmisionElectronica.getValorEnum());
		tramite.setIdTipoCreacion(new BigDecimal(tipoCreacion));
		tramite.setEstado(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));
		UsuarioVO usuario = new UsuarioVO();
		usuario.setIdUsuario(idUsuario);
		tramite.setUsuario(usuario);

		if (ctit.getDATOSPRESENTACION() != null) {
			if (ctit.getDATOSPRESENTACION().getFECHAPRESENTACION() != null && !ctit.getDATOSPRESENTACION().getFECHAPRESENTACION().isEmpty()) {
				tramite.setFechaPresentacion(utilesFecha.formatoFecha("dd/MM/yyyy", ctit.getDATOSPRESENTACION().getFECHAPRESENTACION()));
			} else {
				tramite.setFechaPresentacion(new Date());
			}

			if (ctit.getDATOSPRESENTACION().getCODIGOTASA() != null && !ctit.getDATOSPRESENTACION().getCODIGOTASA().isEmpty()) {
				if (tienePermisoAdmin) {
					if (contrato.getColegiado().getNumColegiado().equals(numColegiadoImportacion)) {
						TasaVO tasa = new TasaVO();
						tasa.setCodigoTasa(ctit.getDATOSPRESENTACION().getCODIGOTASA());
						tasa.setTipoTasa(ctit.getDATOSPRESENTACION().getTIPOTASA());
						tramite.setTasa(tasa);
					}
				} else {
					TasaVO tasa = new TasaVO();
					tasa.setCodigoTasa(ctit.getDATOSPRESENTACION().getCODIGOTASA());
					tasa.setTipoTasa(ctit.getDATOSPRESENTACION().getTIPOTASA());
					tramite.setTasa(tasa);
				}
			}

			if (ctit.getDATOSPRESENTACION().getCODIGOTASAINFORME() != null && !ctit.getDATOSPRESENTACION().getCODIGOTASAINFORME().isEmpty()) {
				if (tienePermisoAdmin) {
					if (contrato.getColegiado().getNumColegiado().equals(numColegiadoImportacion)) {
						TasaVO tasa = new TasaVO();
						tasa.setCodigoTasa(ctit.getDATOSPRESENTACION().getCODIGOTASAINFORME());
						tasa.setTipoTasa(ctit.getDATOSPRESENTACION().getTIPOTASAINFORME());
						tramite.setTasa1(tasa);
					}
				} else {
					TasaVO tasa = new TasaVO();
					tasa.setCodigoTasa(ctit.getDATOSPRESENTACION().getCODIGOTASAINFORME());
					tasa.setTipoTasa(ctit.getDATOSPRESENTACION().getTIPOTASAINFORME());
					tramite.setTasa1(tasa);
				}
			}

			if (ctit.getDATOSPRESENTACION().getCODIGOTASACAMBIO() != null && !ctit.getDATOSPRESENTACION().getCODIGOTASACAMBIO().isEmpty()) {
				if (tienePermisoAdmin) {
					if (contrato.getColegiado().getNumColegiado().equals(numColegiadoImportacion)) {
						TasaVO tasa = new TasaVO();
						tasa.setCodigoTasa(ctit.getDATOSPRESENTACION().getCODIGOTASACAMBIO());
						tasa.setTipoTasa(ctit.getDATOSPRESENTACION().getTIPOTASACAMBIO());
						tramite.setTasa2(tasa);
					}
				} else {
					TasaVO tasa = new TasaVO();
					tasa.setCodigoTasa(ctit.getDATOSPRESENTACION().getCODIGOTASACAMBIO());
					tasa.setTipoTasa(ctit.getDATOSPRESENTACION().getTIPOTASACAMBIO());
					tramite.setTasa2(tasa);
				}
			}

			if (StringUtils.isNotBlank(ctit.getDATOSPRESENTACION().getEXENTOITP())) {
				tramite.setExentoItp(ctit.getDATOSPRESENTACION().getEXENTOITP().toUpperCase());
			}

			if (StringUtils.isNotBlank(ctit.getDATOSPRESENTACION().getNOSUJETOITP())) {
				tramite.setNoSujetoItp(ctit.getDATOSPRESENTACION().getNOSUJETOITP().toUpperCase());
			}

			if (StringUtils.isNotBlank(ctit.getDATOSPRESENTACION().getEXENTOISD())) {
				tramite.setNoSujetoIsd(ctit.getDATOSPRESENTACION().getEXENTOISD().toUpperCase());
			}

			if (StringUtils.isNotBlank(ctit.getDATOSPRESENTACION().getNOSUJETOISD())) {
				tramite.setNoSujetoIsd(ctit.getDATOSPRESENTACION().getNOSUJETOISD().toUpperCase());
			}

			if (StringUtils.isNotBlank(ctit.getDATOSPRESENTACION().getEXENTOCEM())) {
				tramite.setExentoCem(ctit.getDATOSPRESENTACION().getEXENTOCEM().toUpperCase());
			}

			if (StringUtils.isNotBlank(ctit.getDATOSPRESENTACION().getEXENTOIEDMT())) {
				tramite.setExentoIedtm(ctit.getDATOSPRESENTACION().getEXENTOIEDMT().toUpperCase());
			}

			if (StringUtils.isNotBlank(ctit.getDATOSPRESENTACION().getNOSUJETOIEDMT())) {
				tramite.setNoSujecionIedtm(ctit.getDATOSPRESENTACION().getNOSUJETOIEDMT().toUpperCase());
			}

			if (StringUtils.isNotBlank(ctit.getDATOSPRESENTACION().getPROVINCIACET())) {
				tramite.setIdProvinciaCet(utilidadesConversiones.getIdProvinciaFromSiglas(ctit.getDATOSPRESENTACION().getPROVINCIACET()));
			} else {
				tramite.setIdProvinciaCet("");
			}

			if (StringUtils.isNotBlank(ctit.getDATOSPRESENTACION().getPROVINCIACEM())) {
				tramite.setIdProvinciaCem(utilidadesConversiones.getIdProvinciaFromSiglas(ctit.getDATOSPRESENTACION().getPROVINCIACEM()));
			} else {
				tramite.setIdProvinciaCem("");
			}

			if (ctit.getDATOSPRESENTACION().getFECHAFACTURA() != null && !ctit.getDATOSPRESENTACION().getFECHAFACTURA().isEmpty()) {
				tramite.setFechaFactura(utilesFecha.formatoFecha("dd/MM/yyyy", ctit.getDATOSPRESENTACION().getFECHAFACTURA()));
			}

			if (ctit.getDATOSPRESENTACION().getFECHACONTRATO() != null && !ctit.getDATOSPRESENTACION().getFECHACONTRATO().isEmpty()) {
				tramite.setFechaContrato(utilesFecha.formatoFecha("dd/MM/yyyy", ctit.getDATOSPRESENTACION().getFECHACONTRATO()));
			}

			tramite.setModeloItp(ctit.getDATOSPRESENTACION().getMODELOITP());
			tramite.setCetItp(ctit.getDATOSPRESENTACION().getCODIGOELECTRONICOTRANSFERENCIA());
			tramite.setNumAutoItp(ctit.getDATOSPRESENTACION().getNUMAUTOITP());
			tramite.setNumAutoIsd(ctit.getDATOSPRESENTACION().getNUMADJUDICACIONISD());
			tramite.setModeloIsd(ctit.getDATOSPRESENTACION().getMODELOISD());
			tramite.setCem(ctit.getDATOSPRESENTACION().getCODIGOELECTRONICOMATRICULACION());
			tramite.setModeloIedtm(ctit.getDATOSPRESENTACION().getMODELOIEDMT());
			tramite.setCema(ctit.getDATOSPRESENTACION().getCEMA());
			tramite.setIdReduccion05(ctit.getDATOSPRESENTACION().getIDREDUCCION05());
			tramite.setIdNoSujeccion06(ctit.getDATOSPRESENTACION().getIDNOSUJECCION06());
			tramite.setDua(ctit.getDATOSPRESENTACION().getDUA());
			tramite.setAltaIvtm(ctit.getDATOSPRESENTACION().getALTAIVTM());
			tramite.setFactura(ctit.getDATOSPRESENTACION().getNUMEROFACTURA());
			tramite.setValorReal(ctit.getDATOSPRESENTACION().getVALORREAL());
		}

		// Datos limitacion
		if (ctit.getDATOSLIMITACION() != null) {
			if (ctit.getDATOSLIMITACION().getFECHALIMITACION() != null && !ctit.getDATOSLIMITACION().getFECHALIMITACION().isEmpty()) {
				tramite.setFechaPresentacion(utilesFecha.formatoFecha("dd/MM/yyyy", ctit.getDATOSLIMITACION().getFECHALIMITACION()));
			}

			tramite.setnRegIedtm(ctit.getDATOSLIMITACION().getNUMEROREGISTROLIMITACION());
			tramite.setFinancieraIedtm(ctit.getDATOSLIMITACION().getFINANCIERALIMITACION());

			if (StringUtils.isNotBlank(ctit.getDATOSLIMITACION().getTIPOLIMITACION())) {
				tramite.setIedtm("E".equals(ctit.getDATOSLIMITACION().getTIPOLIMITACION().toUpperCase()) ? "E" : "");
			} else {
				tramite.setIedtm("");
			}
		}

		if (ctit.getDATOSVEHICULO() != null) {
			if (StringUtils.isNotBlank(ctit.getDATOSVEHICULO().getDECLARACIONRESPONSABILIDAD())) {
				tramite.setAceptacionResponsItv("SI".equals(ctit.getDATOSVEHICULO().getDECLARACIONRESPONSABILIDAD().toUpperCase()) ? "SI" : "NO");
			} else {
				tramite.setAceptacionResponsItv("");
			}
		}

		if (ctit.getDATOS620() != null) {
			if (ctit.getDATOS620().getFECHADEVENGO() != null && !ctit.getDATOS620().getFECHADEVENGO().isEmpty()) {
				tramite.setFechaDevengoItp(utilesFecha.formatoFecha("dd/MM/yyyy", ctit.getDATOS620().getFECHADEVENGO()));
			}

			if (StringUtils.isNotBlank(ctit.getDATOS620().getESTADO620())) {
				tramite.setEstado620(new BigDecimal(ctit.getDATOS620().getESTADO620()));
			} else {
				tramite.setEstado620(new BigDecimal(Estado620.Iniciado.getValorEnum()));
			}

			if (StringUtils.isNotBlank(ctit.getDATOS620().getPORCENTAJEREDUCCIONANUAL())) {
				tramite.setpReduccionAnual(utilidadesConversiones.stringDecimalToBigDecimal(ctit.getDATOS620().getPORCENTAJEREDUCCIONANUAL()));
			}

			if (StringUtils.isNotBlank(ctit.getDATOS620().getVALORDECLARADO())) {
				tramite.setValorDeclarado(utilidadesConversiones.stringDecimalToBigDecimal(ctit.getDATOS620().getVALORDECLARADO()));
			}

			if (StringUtils.isNotBlank(ctit.getDATOS620().getPORCENTAJEADQUISICION())) {
				tramite.setpAdquisicion(utilidadesConversiones.stringDecimalToBigDecimal(ctit.getDATOS620().getPORCENTAJEADQUISICION()));
			}

			if (StringUtils.isNotBlank(ctit.getDATOS620().getBASEIMPONIBLE())) {
				tramite.setBaseImponible(utilidadesConversiones.stringDecimalToBigDecimal(ctit.getDATOS620().getBASEIMPONIBLE()));
			}

			if (StringUtils.isNotBlank(ctit.getDATOS620().getTIPOGRAVAMEN())) {
				tramite.setTipoGravamen(utilidadesConversiones.stringDecimalToBigDecimal(ctit.getDATOS620().getTIPOGRAVAMEN()));
			}

			if (StringUtils.isNotBlank(ctit.getDATOS620().getCUOTATRIBUTARIA())) {
				tramite.setCuotaTributaria(utilidadesConversiones.stringDecimalToBigDecimal(ctit.getDATOS620().getCUOTATRIBUTARIA()));
			}

			tramite.setFundamentoExencion(ctit.getDATOS620().getFUNDAMENTOEXENCION());
			tramite.setExentoItp(ctit.getDATOS620().getEXENTO());
			tramite.setNoSujetoItp(ctit.getDATOS620().getNOSUJETO());
			tramite.setFundamentoNoSujetoItp(ctit.getDATOS620().getFUNDAMENTONOSUJETO());
		} else {
			tramite.setEstado620(new BigDecimal(Estado620.Iniciado.getValorEnum()));
		}

		tramite.setModoAdjudicacion(ctit.getDATOSVEHICULO().getMODOADJUDICACION());
		tramite.setTipoTransferencia(ctit.getDATOSVEHICULO().getTIPOTRANSFERENCIA());

		if (TipoTransferencia.tipo5.getValorEnum().equals(tramite.getTipoTransferencia()) || TipoTransferencia.tipo4.getValorEnum().equals(tramite.getTipoTransferencia())) {
			tramite.setImprPermisoCircu("NO");
		} else {
			tramite.setImprPermisoCircu("SI");
		}

		if (StringUtils.isNotBlank(ctit.getRENTING())) {
			tramite.setRenting("SI".equals(ctit.getRENTING().toUpperCase()) ? "SI" : "NO");
		} else {
			tramite.setRenting("NO");
		}

		if (StringUtils.isNotBlank(ctit.getRENTING())) {
			tramite.setSimultanea(ctit.getSIMULTANEA() != null ? new BigDecimal(ctit.getSIMULTANEA()) : new BigDecimal(-1));
		}

		if (StringUtils.isNotBlank(ctit.getCAMBIOSERVICIO())) {
			tramite.setCambioServicio("SI".equals(ctit.getCAMBIOSERVICIO().toUpperCase()) ? "SI" : "NO");
		} else {
			tramite.setCambioServicio("");
		}

		if (TipoTransferencia.tipo4.getValorEnum().equals(tramite.getTipoTransferencia()) || TipoTransferencia.tipo5.getValorEnum().equals(tramite.getTipoTransferencia())) {
			tramite.setImprPermisoCircu("NO");
		} else {
			if (StringUtils.isNotBlank(ctit.getIMPRESOPERMISO())) {
				tramite.setImprPermisoCircu(ctit.getIMPRESOPERMISO().toUpperCase());
			} else {
				tramite.setImprPermisoCircu("SI");
			}
		}

		if (StringUtils.isNotBlank(ctit.getCONSENTIMIENTO())) {
			tramite.setConsentimiento(ctit.getCONSENTIMIENTO().toUpperCase());
		}

		if (StringUtils.isNotBlank(ctit.getCONTRATOCOMPRAVENTA())) {
			tramite.setContratoFactura(ctit.getCONTRATOCOMPRAVENTA().toUpperCase());
		}

		if (StringUtils.isNotBlank(ctit.getACTAADJUDICACION())) {
			tramite.setActaSubasta(ctit.getACTAADJUDICACION().toUpperCase());
		}

		if (StringUtils.isNotBlank(ctit.getSENTENCIAADJUDICACION())) {
			tramite.setSentenciaJudicial(ctit.getSENTENCIAADJUDICACION().toUpperCase());
		}

		if (StringUtils.isNotBlank(ctit.getACREDITACION())) {
			tramite.setAcreditaHerenciaDonacion(ctit.getACREDITACION().toUpperCase());
		}

		if (StringUtils.isNotBlank(ctit.getACREDITACION())) {
			tramite.setAcreditaHerenciaDonacion(ctit.getACREDITACION().toUpperCase());
		}

		return tramite;
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