package org.gestoresmadrid.oegamImportacion.schemas.utiles;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.direccion.model.vo.DgtMunicipioVO;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoPK;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.ivtmMatriculacion.model.enumerados.EstadoIVTM;
import org.gestoresmadrid.core.ivtmMatriculacion.model.vo.IvtmMatriculacionVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteMatriculacion;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoVehiculoEnum;
import org.gestoresmadrid.core.personas.model.enumerados.SexoPersona;
import org.gestoresmadrid.core.personas.model.enumerados.SubtipoPersona;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.core.personas.model.vo.PersonaPK;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;
import org.gestoresmadrid.core.trafico.eeff.model.vo.LiberacionEEFFVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.core.vehiculo.model.enumerados.ConceptoTutela;
import org.gestoresmadrid.core.vehiculo.model.enumerados.PaisFabricacion;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegamConversiones.conversion.ParametrosConversiones;
import org.gestoresmadrid.oegamConversiones.jaxb.matw.FORMATOGA;
import org.gestoresmadrid.oegamConversiones.jaxb.matw.FORMATOGA.MATRICULACION;
import org.gestoresmadrid.oegamConversiones.jaxb.matw.FORMATOGA.MATRICULACION.DATOSARRENDATARIO;
import org.gestoresmadrid.oegamConversiones.jaxb.matw.FORMATOGA.MATRICULACION.DATOSCONDUCTORHABITUAL;
import org.gestoresmadrid.oegamConversiones.jaxb.matw.FORMATOGA.MATRICULACION.DATOSIMPORTADOR;
import org.gestoresmadrid.oegamConversiones.jaxb.matw.FORMATOGA.MATRICULACION.DATOSIMPUESTOS;
import org.gestoresmadrid.oegamConversiones.jaxb.matw.FORMATOGA.MATRICULACION.DATOSREPRESENTANTEARRENDATARIO;
import org.gestoresmadrid.oegamConversiones.jaxb.matw.FORMATOGA.MATRICULACION.DATOSREPRESENTANTETITULAR;
import org.gestoresmadrid.oegamConversiones.jaxb.matw.FORMATOGA.MATRICULACION.DATOSTITULAR;
import org.gestoresmadrid.oegamConversiones.jaxb.matw.FORMATOGA.MATRICULACION.DATOSVEHICULO;
import org.gestoresmadrid.oegamConversiones.jaxb.matw.TipoSN;
import org.gestoresmadrid.oegamConversiones.schemas.utiles.XMLMatwFactory;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoImportacionBean;
import org.gestoresmadrid.oegamImportacion.constantes.Constantes;
import org.gestoresmadrid.oegamImportacion.contrato.service.ServicioContratoImportacion;
import org.gestoresmadrid.oegamImportacion.direccion.service.ServicioDireccionImportacion;
import org.gestoresmadrid.oegamImportacion.persona.service.ServicioPersonaImportacion;
import org.gestoresmadrid.oegamImportacion.trafico.service.ServicioIvtmMatriculacionImportacion;
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
public class ConversionFormatoImpGAMatw implements Serializable {

	private static final long serialVersionUID = -7536703725731019882L;

	private static ILoggerOegam log = LoggerOegam.getLogger(ConversionFormatoImpGAMatw.class);

	private static final String FORMATO_FECHA = "dd/MM/yyyy";

	@Autowired
	ServicioContratoImportacion servicioContrato;

	@Autowired
	ParametrosConversiones conversiones;

	@Autowired
	ServicioDireccionImportacion servicioDireccion;

	@Autowired
	ServicioVehiculoImportacion servicioVehiculo;

	@Autowired
	ServicioPersonaImportacion servicioPersona;

	@Autowired
	ServicioIvtmMatriculacionImportacion servicioIvtmMatriculacion;

	@Autowired
	ServicioMarcaDgtImportacion servicioMarcaDgt;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	XMLMatwFactory xMLMatwFactory;

	@Autowired
	UtilidadesConversionesImportacion utilidadesConversiones;

	@Autowired
	UtilidadesNIFValidatorImportacion utilidadesNIFValidator;

	@Autowired
	UtilidadesAnagramaImportacion utilidadesAnagrama;

	@Autowired
	UtilesFecha utilesFecha;

	public ResultadoImportacionBean convertirFicheroFormatoGA(File fichero, ContratoVO contrato, Boolean tienePermisoAdmin) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			xMLMatwFactory.validarXMLFORMATOGAMatw(fichero, gestorPropiedades.valorPropertie(Constantes.PROPERTIES_RUTA_ESQUEMA_MATRICULACION_MATW));
			FORMATOGA ficheroMatw = xMLMatwFactory.getFormatoMatw(fichero);
			if (ficheroMatw.getCABECERA() == null || ficheroMatw.getCABECERA().getDATOSGESTORIA() == null || ficheroMatw.getCABECERA().getDATOSGESTORIA().getPROFESIONAL() == null || "".equals(
					ficheroMatw.getCABECERA().getDATOSGESTORIA().getPROFESIONAL())) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de indicar los datos de la cabecera para poder importar el fichero.");
			} else if (!contrato.getColegiado().getNumColegiado().equals(ficheroMatw.getCABECERA().getDATOSGESTORIA().getPROFESIONAL().toString()) && !tienePermisoAdmin) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Solo se pueden importar tramites del mismo gestor.");
			} else {
				resultado.setFormatoOegam2Matw(ficheroMatw);
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

	public ResultadoImportacionBean convertirFormatoGAToVO(FORMATOGA formatoOegam2Matw, ContratoVO contrato, Boolean tienePermisoAdmin, String tipoCreacion, Boolean tienePermisoLiberarEEFF,
			Long idUsuario) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		int posicion = 0;
		int cont = 1;
		for (MATRICULACION matwImportar : formatoOegam2Matw.getMATRICULACION()) {
			log.error("INICIO CONVERTIR VO MATW tramite " + cont + " del colegiado " + contrato.getColegiado().getNumColegiado());
			try {
				TramiteTrafMatrVO tramiteMatw = convertirTramiteFormatoOegam2MatwToVO(formatoOegam2Matw.getCABECERA().getDATOSGESTORIA().getPROFESIONAL().toString(), matwImportar, contrato,
						tienePermisoAdmin, tipoCreacion, idUsuario);
				List<IntervinienteTraficoVO> listaIntervinientes = new ArrayList<>();
				if (matwImportar.getDATOSVEHICULO() != null) {
					tramiteMatw.setVehiculo(convertirVehiculoGAtoVO(matwImportar.getDATOSVEHICULO(), matwImportar.getDATOSIMPUESTOS(), matwImportar.getDATOSIMPORTADOR(), contrato));
				}
				if (matwImportar.getDATOSTITULAR() != null) {
					IntervinienteTraficoVO interviniente = convertirTitularGAtoVO(matwImportar.getDATOSTITULAR(), contrato);
					if (interviniente != null) {
						listaIntervinientes.add(interviniente);
					}
				}
				if (matwImportar.getDATOSREPRESENTANTETITULAR() != null) {
					IntervinienteTraficoVO interviniente = convertirRepreTitularGAtoVO(matwImportar.getDATOSREPRESENTANTETITULAR(), contrato);
					if (interviniente != null) {
						listaIntervinientes.add(interviniente);
					}
				}
				if (matwImportar.getDATOSCONDUCTORHABITUAL() != null) {
					IntervinienteTraficoVO interviniente = convertirConductorHabitualGAtoVO(matwImportar.getDATOSCONDUCTORHABITUAL(), contrato);
					if (interviniente != null) {
						listaIntervinientes.add(interviniente);
					}
				}
				if (matwImportar.getDATOSARRENDATARIO() != null) {
					IntervinienteTraficoVO interviniente = convertirArrendatarioGAtoVO(matwImportar.getDATOSARRENDATARIO(), contrato);
					if (interviniente != null) {
						listaIntervinientes.add(interviniente);
					}
				}
				if (matwImportar.getDATOSREPRESENTANTEARRENDATARIO() != null) {
					IntervinienteTraficoVO interviniente = convertirRepreArrendatarioGAtoVO(matwImportar.getDATOSREPRESENTANTEARRENDATARIO(), contrato);
					if (interviniente != null) {
						listaIntervinientes.add(interviniente);
					}
				}

				tramiteMatw.setIntervinienteTraficos(listaIntervinientes);

				if (tienePermisoLiberarEEFF) {
					tramiteMatw.setLiberacionEEFF(convertirLiberacionEEFFGAtoDto(matwImportar));
				}

				resultado.addListaTramiteMatw(tramiteMatw, posicion);
				log.error("FIN CONVERTIR VO MATW tramite " + cont + " del colegiado " + contrato.getColegiado().getNumColegiado());
			} catch (Throwable th) {
				log.error("Error al convertir MATW tramite " + cont + " colegiado " + contrato.getColegiado().getNumColegiado(), th);
				resultado.addConversionesError(gestionarMensajeErrorConversion(matwImportar));
			}
			posicion++;
			cont++;
		}
		return resultado;
	}

	private String gestionarMensajeErrorConversion(MATRICULACION matwImportar) {
		String mensaje = "";
		if (matwImportar != null) {
			if (matwImportar.getDATOSVEHICULO() != null && StringUtils.isNotBlank(matwImportar.getDATOSVEHICULO().getNUMEROBASTIDOR())) {
				mensaje = "Error el convertir el trámite con bastidor: " + matwImportar.getDATOSVEHICULO().getNUMEROBASTIDOR();
			} else if (matwImportar.getDATOSVEHICULO() != null && StringUtils.isNotBlank(matwImportar.getDATOSVEHICULO().getNIVE())) {
				mensaje = "Error el convertir el trámite con nive: " + matwImportar.getDATOSVEHICULO().getNIVE();
			}
		}
		return mensaje;
	}

	private LiberacionEEFFVO convertirLiberacionEEFFGAtoDto(MATRICULACION matriculacion) {
		LiberacionEEFFVO liberacionEEFFVO = new LiberacionEEFFVO();

		if (StringUtils.isNotBlank(matriculacion.getEXENTOLIBERAR()) && ("SI".equalsIgnoreCase(matriculacion.getEXENTOLIBERAR()) || "S".equalsIgnoreCase(matriculacion.getEXENTOLIBERAR()))) {
			liberacionEEFFVO.setExento(Boolean.TRUE);
		} else {
			if (matriculacion.getDATOSLIBERACION() != null) {
				liberacionEEFFVO.setFirCif(matriculacion.getDATOSLIBERACION().getCIFFIR());
				if (matriculacion.getDATOSLIBERACION().getFECHAFACTURA() != null && !matriculacion.getDATOSLIBERACION().getFECHAFACTURA().isEmpty()) {
					liberacionEEFFVO.setFechaFactura(utilesFecha.formatoFecha(FORMATO_FECHA, matriculacion.getDATOSLIBERACION().getFECHAFACTURA()));
				}
				if (matriculacion.getDATOSLIBERACION().getFECHAREALIZACION() != null && !matriculacion.getDATOSLIBERACION().getFECHAREALIZACION().isEmpty()) {
					liberacionEEFFVO.setFechaRealizacion(utilesFecha.formatoFecha(FORMATO_FECHA, matriculacion.getDATOSLIBERACION().getFECHAREALIZACION()));
				}
				if (matriculacion.getDATOSLIBERACION().getIMPORTE() != null && !matriculacion.getDATOSLIBERACION().getIMPORTE().isEmpty()) {
					liberacionEEFFVO.setImporte(new BigDecimal(matriculacion.getDATOSLIBERACION().getIMPORTE()));
				}
				liberacionEEFFVO.setFirMarca(matriculacion.getDATOSLIBERACION().getMARCAFIR());
				liberacionEEFFVO.setNifRepresentado(matriculacion.getDATOSLIBERACION().getNIFREPRESENTADO());
				liberacionEEFFVO.setNumFactura(matriculacion.getDATOSLIBERACION().getNUMFACTURA());
			} else {
				liberacionEEFFVO.setExento(Boolean.TRUE);
			}
		}
		return liberacionEEFFVO;
	}

	private IntervinienteTraficoVO convertirRepreArrendatarioGAtoVO(DATOSREPRESENTANTEARRENDATARIO repreArrendatario, ContratoVO contrato) {
		IntervinienteTraficoVO interviniente = null;
		if (repreArrendatario != null && StringUtils.isNotBlank(repreArrendatario.getDNIREPRESARR())) {
			interviniente = new IntervinienteTraficoVO();
			IntervinienteTraficoPK idInterviniente = new IntervinienteTraficoPK();
			idInterviniente.setNumColegiado(contrato.getColegiado().getNumColegiado());
			idInterviniente.setNif(repreArrendatario.getDNIREPRESARR().toUpperCase());
			idInterviniente.setTipoInterviniente(TipoInterviniente.RepresentanteArrendatario.getValorEnum());
			interviniente.setId(idInterviniente);

			PersonaVO persona = new PersonaVO();
			PersonaPK idPersona = new PersonaPK();
			idPersona.setNif(repreArrendatario.getDNIREPRESARR().toUpperCase());
			idPersona.setNumColegiado(contrato.getColegiado().getNumColegiado());
			persona.setId(idPersona);

			if (StringUtils.isNotBlank(repreArrendatario.getAPELLIDO1REPRESARR())) {
				persona.setApellido1RazonSocial(repreArrendatario.getAPELLIDO1REPRESARR().toUpperCase());
			} else {
				PersonaVO personaBBDD = servicioPersona.getPersonaVO(persona.getId().getNif(), persona.getId().getNumColegiado());
				if (personaBBDD != null) {
					persona.setApellido1RazonSocial(personaBBDD.getApellido1RazonSocial());
				}
			}

			persona.setApellido2(repreArrendatario.getAPELLIDO2REPRESARR());
			persona.setNombre(repreArrendatario.getNOMBREREPRESARR());

			setDatosPersona(persona);
			persona.setEstado(new Long(1));
			interviniente.setPersona(persona);
		}
		return interviniente;
	}

	private IntervinienteTraficoVO convertirArrendatarioGAtoVO(DATOSARRENDATARIO arrendatario, ContratoVO contrato) {
		IntervinienteTraficoVO interviniente = null;
		if (arrendatario != null && StringUtils.isNotBlank(arrendatario.getDNIARR())) {
			interviniente = new IntervinienteTraficoVO();
			IntervinienteTraficoPK idInterviniente = new IntervinienteTraficoPK();
			idInterviniente.setNumColegiado(contrato.getColegiado().getNumColegiado());
			idInterviniente.setNif(arrendatario.getDNIARR().toUpperCase());
			idInterviniente.setTipoInterviniente(TipoInterviniente.Arrendatario.getValorEnum());
			interviniente.setId(idInterviniente);

			if (StringUtils.isNotBlank(arrendatario.getCAMBIODOMICILIOARR())) {
				interviniente.setCambioDomicilio(arrendatario.getCAMBIODOMICILIOARR().toUpperCase());
			}

			if (arrendatario.getFECHAINICIORENTING() != null && !arrendatario.getFECHAINICIORENTING().isEmpty()) {
				interviniente.setFechaInicio(utilesFecha.formatoFecha(FORMATO_FECHA, arrendatario.getFECHAINICIORENTING()));
			}

			if (arrendatario.getFECHAFINRENTING() != null && !arrendatario.getFECHAFINRENTING().isEmpty()) {
				interviniente.setFechaFin(utilesFecha.formatoFecha(FORMATO_FECHA, arrendatario.getFECHAFINRENTING()));
			}

			interviniente.setHoraInicio(arrendatario.getHORAINICIORENTING());
			interviniente.setHoraFin(arrendatario.getHORAFINRENTING());

			PersonaVO persona = new PersonaVO();
			PersonaPK idPersona = new PersonaPK();
			idPersona.setNif(arrendatario.getDNIARR().toUpperCase());
			idPersona.setNumColegiado(contrato.getColegiado().getNumColegiado());
			persona.setId(idPersona);

			if (StringUtils.isNotBlank(arrendatario.getAPELLIDO1ARR())) {
				persona.setApellido1RazonSocial(arrendatario.getAPELLIDO1ARR().toUpperCase());
			} else if (StringUtils.isNotBlank(arrendatario.getRAZONSOCIALARR())) {
				persona.setApellido1RazonSocial(arrendatario.getRAZONSOCIALARR().toUpperCase());
			} else {
				PersonaVO personaBBDD = servicioPersona.getPersonaVO(persona.getId().getNif(), persona.getId().getNumColegiado());
				if (personaBBDD != null) {
					persona.setApellido1RazonSocial(personaBBDD.getApellido1RazonSocial());
				}
			}

			persona.setApellido2(arrendatario.getAPELLIDO2ARR());
			persona.setNombre(arrendatario.getNOMBREARR());
			persona.setSexo(arrendatario.getSEXOARR());
			persona.setTipoDocumentoAlternativo(arrendatario.getTIPODOCUMENTOSUSTITUTIVOARR());

			if (StringUtils.isNotBlank(arrendatario.getINDEFINIDOARR()) && ("SI".equalsIgnoreCase(arrendatario.getINDEFINIDOARR()) || "S".equalsIgnoreCase(arrendatario.getINDEFINIDOARR()))) {
				persona.setIndefinido("S");
			} else {
				persona.setIndefinido("N");
			}

			if (arrendatario.getFECHANACIMIENTOARR() != null && !arrendatario.getFECHANACIMIENTOARR().isEmpty()) {
				persona.setFechaNacimiento(utilesFecha.formatoFecha(FORMATO_FECHA, arrendatario.getFECHANACIMIENTOARR()));
			}

			if (arrendatario.getFECHACADUCIDADNIFARR() != null && !arrendatario.getFECHACADUCIDADNIFARR().isEmpty()) {
				persona.setFechaCaducidadNIF(utilesFecha.formatoFecha(FORMATO_FECHA, arrendatario.getFECHACADUCIDADNIFARR()));
			}

			if (arrendatario.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOARR() != null && !arrendatario.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOARR().isEmpty()) {
				persona.setFechaCaducidadAlternativo(utilesFecha.formatoFecha(FORMATO_FECHA, arrendatario.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOARR()));
			}

			setDatosPersona(persona);
			persona.setEstado(new Long(1));
			interviniente.setPersona(persona);

			if (StringUtils.isNotBlank(arrendatario.getPROVINCIAARR())) {
				DireccionVO direccion = new DireccionVO();
				direccion.setIdProvincia(utilidadesConversiones.getIdProvinciaFromSiglas(arrendatario.getPROVINCIAARR()));

				DgtMunicipioVO dgtMunicipio = servicioDireccion.getMunicipioDGTPorNombre(arrendatario.getMUNICIPIOARR(), direccion.getIdProvincia());
				if (dgtMunicipio != null) {
					direccion.setIdMunicipio(dgtMunicipio.getCodigoIne());
				}

				direccion.setNombreVia(arrendatario.getNOMBREVIADIRECCIONARR());
				direccion.setViaSineditar(utilidadesConversiones.getViaSinEditar(direccion.getNombreVia()));
				direccion.setIdTipoVia(utilidadesConversiones.tipoVia(arrendatario.getSIGLASDIRECCIONARR()));
				direccion.setNumero(arrendatario.getNUMERODIRECCIONARR());
				direccion.setCodPostalCorreos(arrendatario.getCPARR());
				if (arrendatario.getPUEBLOARR() != null) {
					direccion.setPuebloCorreos(arrendatario.getPUEBLOARR().toUpperCase());
				}
				direccion.setLetra(arrendatario.getLETRADIRECCIONARR());
				direccion.setEscalera(arrendatario.getESCALERADIRECCIONARR());
				direccion.setBloque(arrendatario.getBLOQUEDIRECCIONARR());
				direccion.setPlanta(arrendatario.getPISODIRECCIONARR());
				direccion.setPuerta(arrendatario.getPUERTADIRECCIONARR());

				if (StringUtils.isNotBlank(arrendatario.getKMDIRECCIONARR())) {
					try {
						direccion.setKm(new BigDecimal(arrendatario.getKMDIRECCIONARR()));
					} catch (Exception e) {
						direccion.setKm(null);
					}
				}

				if (StringUtils.isNotBlank(arrendatario.getHECTOMETRODIRECCIONARR())) {
					try {
						direccion.setHm(new BigDecimal(arrendatario.getHECTOMETRODIRECCIONARR()));
					} catch (Exception e) {
						direccion.setHm(null);
					}
				}

				if (StringUtils.isNotBlank(arrendatario.getDIRECCIONACTIVAARR()) && ("SI".equalsIgnoreCase(arrendatario.getDIRECCIONACTIVAARR()) || "S".equalsIgnoreCase(arrendatario.getDIRECCIONACTIVAARR()))) {
					direccion.setAsignarPrincipal("S");
				} else {
					direccion.setAsignarPrincipal("N");
				}

				interviniente.setDireccion(direccion);
			}
		}
		return interviniente;
	}

	private IntervinienteTraficoVO convertirConductorHabitualGAtoVO(DATOSCONDUCTORHABITUAL conductorHabitual, ContratoVO contrato) {
		IntervinienteTraficoVO interviniente = null;
		if (conductorHabitual != null && StringUtils.isNotBlank(conductorHabitual.getDNICONDHABITUAL())) {
			interviniente = new IntervinienteTraficoVO();
			IntervinienteTraficoPK idInterviniente = new IntervinienteTraficoPK();
			idInterviniente.setNumColegiado(contrato.getColegiado().getNumColegiado());
			idInterviniente.setNif(conductorHabitual.getDNICONDHABITUAL().toUpperCase());
			idInterviniente.setTipoInterviniente(TipoInterviniente.ConductorHabitual.getValorEnum());
			interviniente.setId(idInterviniente);

			if (conductorHabitual.getFECHAFINCONDHABITUAL() != null && !conductorHabitual.getFECHAFINCONDHABITUAL().isEmpty()) {
				interviniente.setFechaFin(utilesFecha.formatoFecha(FORMATO_FECHA, conductorHabitual.getFECHAFINCONDHABITUAL()));
			}
			interviniente.setHoraFin(conductorHabitual.getHORAFINCONDHABITUAL());

			if (StringUtils.isNotBlank(conductorHabitual.getCAMBIODOMICILIOCONDHABITUAL())) {
				interviniente.setCambioDomicilio(conductorHabitual.getCAMBIODOMICILIOCONDHABITUAL().toUpperCase());
			}

			PersonaVO persona = new PersonaVO();
			PersonaPK idPersona = new PersonaPK();
			idPersona.setNif(conductorHabitual.getDNICONDHABITUAL().toUpperCase());
			idPersona.setNumColegiado(contrato.getColegiado().getNumColegiado());
			persona.setId(idPersona);

			if (StringUtils.isNotBlank(conductorHabitual.getAPELLIDO1CONDHABITUAL())) {
				persona.setApellido1RazonSocial(conductorHabitual.getAPELLIDO1CONDHABITUAL().toUpperCase());
			} else if (StringUtils.isNotBlank(conductorHabitual.getRAZONSOCIALCONDHABITUAL())) {
				persona.setApellido1RazonSocial(conductorHabitual.getRAZONSOCIALCONDHABITUAL().toUpperCase());
			} else {
				PersonaVO personaBBDD = servicioPersona.getPersonaVO(persona.getId().getNif(), persona.getId().getNumColegiado());
				if (personaBBDD != null) {
					persona.setApellido1RazonSocial(personaBBDD.getApellido1RazonSocial());
				}
			}

			persona.setApellido2(conductorHabitual.getAPELLIDO2CONDHABITUAL());
			persona.setNombre(conductorHabitual.getNOMBRECONDHABITUAL());
			persona.setSexo(conductorHabitual.getSEXOCONDHABITUAL());
			persona.setTipoDocumentoAlternativo(conductorHabitual.getTIPODOCUMENTOSUSTITUTIVOCONDHABITUAL());

			if (StringUtils.isNotBlank(conductorHabitual.getINDEFINIDOCONDHABITUAL()) && ("SI".equalsIgnoreCase(conductorHabitual.getINDEFINIDOCONDHABITUAL()) || "S".equalsIgnoreCase(conductorHabitual
					.getINDEFINIDOCONDHABITUAL()))) {
				persona.setIndefinido("S");
			} else {
				persona.setIndefinido("N");
			}

			if (conductorHabitual.getFECHANACIMIENTOCONDHABITUAL() != null && !conductorHabitual.getFECHANACIMIENTOCONDHABITUAL().isEmpty()) {
				persona.setFechaNacimiento(utilesFecha.formatoFecha(FORMATO_FECHA, conductorHabitual.getFECHANACIMIENTOCONDHABITUAL()));
			}

			if (conductorHabitual.getFECHACADUCIDADNIFCONDHABITUAL() != null && !conductorHabitual.getFECHACADUCIDADNIFCONDHABITUAL().isEmpty()) {
				persona.setFechaCaducidadNIF(utilesFecha.formatoFecha(FORMATO_FECHA, conductorHabitual.getFECHACADUCIDADNIFCONDHABITUAL()));
			}

			if (conductorHabitual.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOCONDHABITUAL() != null && !conductorHabitual.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOCONDHABITUAL().isEmpty()) {
				persona.setFechaCaducidadAlternativo(utilesFecha.formatoFecha(FORMATO_FECHA, conductorHabitual.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOCONDHABITUAL()));
			}

			setDatosPersona(persona);
			persona.setEstado(new Long(1));
			interviniente.setPersona(persona);

			if (StringUtils.isNotBlank(conductorHabitual.getPROVINCIACONDHABITUAL())) {
				DireccionVO direccion = new DireccionVO();
				direccion.setIdProvincia(utilidadesConversiones.getIdProvinciaFromSiglas(conductorHabitual.getPROVINCIACONDHABITUAL()));

				DgtMunicipioVO dgtMunicipio = servicioDireccion.getMunicipioDGTPorNombre(conductorHabitual.getMUNICIPIOCONDHABITUAL(), direccion.getIdProvincia());
				if (dgtMunicipio != null) {
					direccion.setIdMunicipio(dgtMunicipio.getCodigoIne());
				}

				direccion.setNombreVia(conductorHabitual.getNOMBREVIADIRECCIONCONDHABITUAL());
				direccion.setViaSineditar(utilidadesConversiones.getViaSinEditar(direccion.getNombreVia()));
				direccion.setIdTipoVia(utilidadesConversiones.tipoVia(conductorHabitual.getSIGLASDIRECCIONCONDHABITUAL()));
				direccion.setNumero(conductorHabitual.getNUMERODIRECCIONCONDHABITUAL());
				direccion.setCodPostalCorreos(conductorHabitual.getCPCONDHABITUAL());
				if (conductorHabitual.getPUEBLOCONDHABITUAL() != null) {
					direccion.setPuebloCorreos(conductorHabitual.getPUEBLOCONDHABITUAL().toUpperCase());
				}
				direccion.setLetra(conductorHabitual.getLETRADIRECCIONCONDHABITUAL());
				direccion.setEscalera(conductorHabitual.getESCALERADIRECCIONCONDHABITUAL());
				direccion.setBloque(conductorHabitual.getBLOQUEDIRECCIONCONDHABITUAL());
				direccion.setPlanta(conductorHabitual.getPISODIRECCIONCONDHABITUAL());
				direccion.setPuerta(conductorHabitual.getPUERTADIRECCIONCONDHABITUAL());

				if (StringUtils.isNotBlank(conductorHabitual.getKMDIRECCIONCONDHABITUAL())) {
					try {
						direccion.setKm(new BigDecimal(conductorHabitual.getKMDIRECCIONCONDHABITUAL()));
					} catch (Exception e) {
						direccion.setKm(null);
					}
				}

				if (StringUtils.isNotBlank(conductorHabitual.getHECTOMETRODIRECCIONCONDHABITUAL())) {
					try {
						direccion.setHm(new BigDecimal(conductorHabitual.getHECTOMETRODIRECCIONCONDHABITUAL()));
					} catch (Exception e) {
						direccion.setHm(null);
					}
				}

				if (StringUtils.isNotBlank(conductorHabitual.getDIRECCIONACTIVACONDHABITUAL()) && ("SI".equalsIgnoreCase(conductorHabitual.getDIRECCIONACTIVACONDHABITUAL()) || "S".equalsIgnoreCase(
						conductorHabitual.getDIRECCIONACTIVACONDHABITUAL()))) {
					direccion.setAsignarPrincipal("S");
				} else {
					direccion.setAsignarPrincipal("N");
				}

				interviniente.setDireccion(direccion);
			}

		}
		return interviniente;
	}

	private IntervinienteTraficoVO convertirRepreTitularGAtoVO(DATOSREPRESENTANTETITULAR repreTitular, ContratoVO contrato) {
		IntervinienteTraficoVO interviniente = null;
		if (repreTitular != null && StringUtils.isNotBlank(repreTitular.getDNIREP())) {
			interviniente = new IntervinienteTraficoVO();
			IntervinienteTraficoPK idInterviniente = new IntervinienteTraficoPK();
			idInterviniente.setNumColegiado(contrato.getColegiado().getNumColegiado());
			idInterviniente.setNif(repreTitular.getDNIREP().toUpperCase());
			idInterviniente.setTipoInterviniente(TipoInterviniente.RepresentanteTitular.getValorEnum());
			interviniente.setId(idInterviniente);

			if (repreTitular.getFECHAFINTUTELA() != null && !repreTitular.getFECHAFINTUTELA().isEmpty()) {
				interviniente.setFechaFin(utilesFecha.formatoFecha(FORMATO_FECHA, repreTitular.getFECHAFINTUTELA()));
			}

			if (StringUtils.isNotBlank(repreTitular.getCONCEPTOREPTITULAR())) {
				interviniente.setConceptoRepre(repreTitular.getCONCEPTOREPTITULAR());
				if (ConceptoTutela.convertir(repreTitular.getCONCEPTOREPTITULAR()) == ConceptoTutela.Tutela) {
					if (repreTitular.getMOTIVOTUTELA() != null) {
						interviniente.setIdMotivoTutela(repreTitular.getMOTIVOTUTELA().value());
					}
				}
			}

			interviniente.setDatosDocumento(repreTitular.getACREDITACIONREPTITULAR());

			PersonaVO persona = new PersonaVO();
			PersonaPK idPersona = new PersonaPK();
			idPersona.setNif(repreTitular.getDNIREP().toUpperCase());
			idPersona.setNumColegiado(contrato.getColegiado().getNumColegiado());
			persona.setId(idPersona);

			if (StringUtils.isNotBlank(repreTitular.getAPELLIDO1REP())) {
				persona.setApellido1RazonSocial(repreTitular.getAPELLIDO1REP().toUpperCase());
			} else {
				PersonaVO personaBBDD = servicioPersona.getPersonaVO(persona.getId().getNif(), persona.getId().getNumColegiado());
				if (personaBBDD != null) {
					persona.setApellido1RazonSocial(personaBBDD.getApellido1RazonSocial());
				}
			}

			persona.setApellido2(repreTitular.getAPELLIDO2REP());
			persona.setNombre(repreTitular.getNOMBREREP());
			persona.setTipoDocumentoAlternativo(repreTitular.getTIPODOCUMENTOSUSTITUTIVOREPRESENTANTETITULAR());

			if (StringUtils.isNotBlank(repreTitular.getINDEFINIDOREPRESENTANTETITULAR()) && ("SI".equalsIgnoreCase(repreTitular.getINDEFINIDOREPRESENTANTETITULAR()) || "S".equalsIgnoreCase(repreTitular
					.getINDEFINIDOREPRESENTANTETITULAR()))) {
				persona.setIndefinido("S");
			} else {
				persona.setIndefinido("N");
			}

			if (repreTitular.getFECHANACIMIENTOREP() != null && !repreTitular.getFECHANACIMIENTOREP().isEmpty()) {
				persona.setFechaNacimiento(utilesFecha.formatoFecha(FORMATO_FECHA, repreTitular.getFECHANACIMIENTOREP()));
			}

			if (repreTitular.getFECHACADUCIDADNIFREPRESENTANTETITULAR() != null && !repreTitular.getFECHACADUCIDADNIFREPRESENTANTETITULAR().isEmpty()) {
				persona.setFechaCaducidadNIF(utilesFecha.formatoFecha(FORMATO_FECHA, repreTitular.getFECHACADUCIDADNIFREPRESENTANTETITULAR()));
			}

			if (repreTitular.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOREPRESENTANTETITULAR() != null && !repreTitular.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOREPRESENTANTETITULAR().isEmpty()) {
				persona.setFechaCaducidadAlternativo(utilesFecha.formatoFecha(FORMATO_FECHA, repreTitular.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOREPRESENTANTETITULAR()));
			}

			setDatosPersona(persona);
			persona.setEstado(new Long(1));
			interviniente.setPersona(persona);

			if (StringUtils.isNotBlank(repreTitular.getPROVINCIAREP())) {
				DireccionVO direccion = new DireccionVO();
				direccion.setIdProvincia(utilidadesConversiones.getIdProvinciaFromSiglas(repreTitular.getPROVINCIAREP()));

				DgtMunicipioVO dgtMunicipio = servicioDireccion.getMunicipioDGTPorNombre(repreTitular.getMUNICIPIOREP(), direccion.getIdProvincia());
				if (dgtMunicipio != null) {
					direccion.setIdMunicipio(dgtMunicipio.getCodigoIne());
				}

				direccion.setNombreVia(repreTitular.getNOMBREVIADIRECCIONREP());
				direccion.setViaSineditar(utilidadesConversiones.getViaSinEditar(direccion.getNombreVia()));
				direccion.setIdTipoVia(utilidadesConversiones.tipoVia(repreTitular.getSIGLASDIRECCIONREP()));
				direccion.setNumero(repreTitular.getNUMERODIRECCIONREP());
				direccion.setCodPostalCorreos(repreTitular.getCPREP());
				if (repreTitular.getPUEBLOREP() != null) {
					direccion.setPuebloCorreos(repreTitular.getPUEBLOREP().toUpperCase());
				}
				direccion.setLetra(repreTitular.getLETRADIRECCIONREP());
				direccion.setEscalera(repreTitular.getESCALERADIRECCIONREP());
				direccion.setBloque(repreTitular.getBLOQUEDIRECCIONREP());
				direccion.setPlanta(repreTitular.getPISODIRECCIONREP());
				direccion.setPuerta(repreTitular.getPUERTADIRECCIONREP());

				if (StringUtils.isNotBlank(repreTitular.getKMDIRECCIONREP())) {
					try {
						direccion.setKm(new BigDecimal(repreTitular.getKMDIRECCIONREP()));
					} catch (Exception e) {
						direccion.setKm(null);
					}
				}

				if (StringUtils.isNotBlank(repreTitular.getHECTOMETRODIRECCIONREP())) {
					try {
						direccion.setHm(new BigDecimal(repreTitular.getHECTOMETRODIRECCIONREP()));
					} catch (Exception e) {
						direccion.setHm(null);
					}
				}

				if (StringUtils.isNotBlank(repreTitular.getDIRECCIONACTIVAREP()) && ("SI".equalsIgnoreCase(repreTitular.getDIRECCIONACTIVAREP()) || "S".equalsIgnoreCase(repreTitular.getDIRECCIONACTIVAREP()))) {
					direccion.setAsignarPrincipal("S");
				} else {
					direccion.setAsignarPrincipal("N");
				}

				interviniente.setDireccion(direccion);
			}

		}
		return interviniente;
	}

	private IntervinienteTraficoVO convertirTitularGAtoVO(DATOSTITULAR titular, ContratoVO contrato) {
		IntervinienteTraficoVO interviniente = null;
		if (titular != null && StringUtils.isNotBlank(titular.getDNITITULAR())) {
			interviniente = new IntervinienteTraficoVO();
			IntervinienteTraficoPK idInterviniente = new IntervinienteTraficoPK();
			idInterviniente.setNumColegiado(contrato.getColegiado().getNumColegiado());
			idInterviniente.setNif(titular.getDNITITULAR().toUpperCase());
			idInterviniente.setTipoInterviniente(TipoInterviniente.Titular.getValorEnum());
			interviniente.setId(idInterviniente);

			if (StringUtils.isNotBlank(titular.getCAMBIODOMICILIOTITULAR())) {
				interviniente.setCambioDomicilio(titular.getCAMBIODOMICILIOTITULAR().toUpperCase());
			}

			if (StringUtils.isNotBlank(titular.getAUTONOMO())) {
				interviniente.setAutonomo(titular.getAUTONOMO().toUpperCase());
			}

			interviniente.setCodigoIae(titular.getCODIGOIAE());

			PersonaVO persona = new PersonaVO();
			PersonaPK idPersona = new PersonaPK();
			idPersona.setNif(titular.getDNITITULAR().toUpperCase());
			idPersona.setNumColegiado(contrato.getColegiado().getNumColegiado());
			persona.setId(idPersona);

			if (StringUtils.isNotBlank(titular.getAPELLIDO1TITULAR())) {
				persona.setApellido1RazonSocial(titular.getAPELLIDO1TITULAR().toUpperCase());
			} else if (StringUtils.isNotBlank(titular.getRAZONSOCIALTITULAR())) {
				persona.setApellido1RazonSocial(titular.getRAZONSOCIALTITULAR().toUpperCase());
			} else {
				PersonaVO personaBBDD = servicioPersona.getPersonaVO(persona.getId().getNif(), persona.getId().getNumColegiado());
				if (personaBBDD != null) {
					persona.setApellido1RazonSocial(personaBBDD.getApellido1RazonSocial());
				}
			}

			persona.setApellido2(titular.getAPELLIDO2TITULAR());
			persona.setNombre(titular.getNOMBRETITULAR());
			persona.setSexo(titular.getSEXOTITULAR());
			persona.setTipoDocumentoAlternativo(titular.getTIPODOCUMENTOSUSTITUTIVOTITULAR());
			persona.setTelefonos(titular.getTELEFONOTITULAR());
			persona.setCorreoElectronico(titular.getCORREOELECTRONICOTITULAR());

			if (StringUtils.isNotBlank(titular.getINDEFINIDOTITULAR()) && ("SI".equalsIgnoreCase(titular.getINDEFINIDOTITULAR()) || "S".equalsIgnoreCase(titular.getINDEFINIDOTITULAR()))) {
				persona.setIndefinido("S");
			} else {
				persona.setIndefinido("N");
			}

			if (titular.getFECHANACIMIENTOTITULAR() != null && !titular.getFECHANACIMIENTOTITULAR().isEmpty()) {
				persona.setFechaNacimiento(utilesFecha.formatoFecha(FORMATO_FECHA, titular.getFECHANACIMIENTOTITULAR()));
			}

			if (titular.getFECHACADUCIDADNIFTITULAR() != null && !titular.getFECHACADUCIDADNIFTITULAR().isEmpty()) {
				persona.setFechaCaducidadNIF(utilesFecha.formatoFecha(FORMATO_FECHA, titular.getFECHACADUCIDADNIFTITULAR()));
			}

			if (titular.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOTITULAR() != null && !titular.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOTITULAR().isEmpty()) {
				persona.setFechaCaducidadAlternativo(utilesFecha.formatoFecha(FORMATO_FECHA, titular.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOTITULAR()));
			}

			setDatosPersona(persona);
			persona.setEstado(new Long(1));
			interviniente.setPersona(persona);

			if (StringUtils.isNotBlank(titular.getPROVINCIATITULAR())) {
				DireccionVO direccion = new DireccionVO();
				direccion.setIdProvincia(utilidadesConversiones.getIdProvinciaFromSiglas(titular.getPROVINCIATITULAR()));

				DgtMunicipioVO dgtMunicipio = servicioDireccion.getMunicipioDGTPorNombre(titular.getMUNICIPIOTITULAR(), direccion.getIdProvincia());
				if (dgtMunicipio != null) {
					direccion.setIdMunicipio(dgtMunicipio.getCodigoIne());
				}

				direccion.setNombreVia(titular.getNOMBREVIADIRECCIONTITULAR());
				direccion.setViaSineditar(utilidadesConversiones.getViaSinEditar(direccion.getNombreVia()));
				direccion.setIdTipoVia(utilidadesConversiones.tipoVia(titular.getSIGLASDIRECCIONTITULAR()));
				direccion.setNumero(titular.getNUMERODIRECCIONTITULAR());
				direccion.setCodPostalCorreos(titular.getCPTITULAR());
				if (titular.getPUEBLOTITULAR() != null) {
					direccion.setPuebloCorreos(titular.getPUEBLOTITULAR().toUpperCase());
				}
				direccion.setLetra(titular.getLETRADIRECCIONTITULAR());
				direccion.setEscalera(titular.getESCALERADIRECCIONTITULAR());
				direccion.setBloque(titular.getBLOQUEDIRECCIONTITULAR());
				direccion.setPlanta(titular.getPISODIRECCIONTITULAR());
				direccion.setPuerta(titular.getPUERTADIRECCIONTITULAR());

				if (StringUtils.isNotBlank(titular.getKMDIRECCIONTITULAR())) {
					try {
						direccion.setKm(new BigDecimal(titular.getKMDIRECCIONTITULAR()));
					} catch (Exception e) {
						direccion.setKm(null);
					}
				}

				if (StringUtils.isNotBlank(titular.getHECTOMETRODIRECCIONTITULAR())) {
					try {
						direccion.setHm(new BigDecimal(titular.getHECTOMETRODIRECCIONTITULAR()));
					} catch (Exception e) {
						direccion.setHm(null);
					}
				}

				if (StringUtils.isNotBlank(titular.getDIRECCIONACTIVATITULAR()) && ("SI".equalsIgnoreCase(titular.getDIRECCIONACTIVATITULAR()) || "S".equalsIgnoreCase(titular.getDIRECCIONACTIVATITULAR()))) {
					direccion.setAsignarPrincipal("S");
				} else {
					direccion.setAsignarPrincipal("N");
				}

				interviniente.setDireccion(direccion);
			}
		}
		return interviniente;
	}

	private VehiculoVO convertirVehiculoGAtoVO(DATOSVEHICULO vehiculoGa, DATOSIMPUESTOS datosImpuestos, DATOSIMPORTADOR integrador, ContratoVO contrato) {
		VehiculoVO vehiculo = new VehiculoVO();
		vehiculo.setNumColegiado(contrato.getColegiado().getNumColegiado());

		vehiculo.setMatricula(null);
		vehiculo.setBastidor(vehiculoGa.getNUMEROBASTIDOR().toUpperCase());

		if (StringUtils.isNotBlank(vehiculoGa.getNIVE())) {
			vehiculo.setNive(vehiculoGa.getNIVE().toUpperCase());
		}

		vehiculo.setModelo(vehiculoGa.getMODELO());
		vehiculo.setTipoVehiculo(vehiculoGa.getTIPOVEHICULO());
		vehiculo.setTipoItv(vehiculoGa.getTIPOITV());
		vehiculo.setProcedencia(vehiculoGa.getPROCEDENCIA());
		vehiculo.setCoditv(vehiculoGa.getCODIGOITV());
		vehiculo.setPotenciaFiscal(vehiculoGa.getPOTENCIAFISCAL());
		vehiculo.setPotenciaNeta(vehiculoGa.getPOTENCIANETA());
		vehiculo.setPotenciaPeso(vehiculoGa.getRELACIONPOTENCIAPESO());
		vehiculo.setVersion(vehiculoGa.getVERSIONITV());
		vehiculo.setVariante(vehiculoGa.getVARIANTEITV());
		vehiculo.setnHomologacion(vehiculoGa.getNUMEROHOMOLOGACIONITV());
		vehiculo.setnSerie(vehiculoGa.getNUMEROSERIEITV());
		vehiculo.setIdEpigrafe(utilidadesConversiones.convertirEpigrafe(vehiculoGa.getEPIGRAFE()));
		vehiculo.setTipoIndustria(vehiculoGa.getTIPOINDUSTRIA());
		vehiculo.setCodigoEco(vehiculoGa.getCODIGOECO());
		vehiculo.setEcoInnovacion(vehiculoGa.getECOINNOVACION());
		vehiculo.setNivelEmisiones(vehiculoGa.getNIVELEMISIONES());
		vehiculo.setFabricante(vehiculoGa.getFABRICANTE());
		vehiculo.setMatriculaOrigen(vehiculoGa.getMATRICULAORIGEN());
		vehiculo.setMatriculaOrigExtr(vehiculoGa.getMATRICULAORIGENEXTRANJERO());
		vehiculo.setFabricanteBase(vehiculoGa.getFABRICANTEBASE());
		vehiculo.setTipoBase(vehiculoGa.getTIPOBASE());
		vehiculo.setVarianteBase(vehiculoGa.getVARIANTEBASE());
		vehiculo.setVersionBase(vehiculoGa.getVERSIONBASE());
		vehiculo.setnHomologacionBase(vehiculoGa.getNUMEROHOMOLOGACIONBASE());

		String marcaSinEditar = utilidadesConversiones.getMarcaSinEditar(vehiculoGa.getMARCA());
		vehiculo.setCodigoMarca(servicioMarcaDgt.getCodMarcaPorMarcaSinEditar(marcaSinEditar, Boolean.FALSE));

		if (vehiculoGa.getSERVICIODESTINO() != null) {
			vehiculo.setIdServicio(vehiculoGa.getSERVICIODESTINO().value());
		}

		if (vehiculoGa.getCOLOR() != null) {
			vehiculo.setIdColor(vehiculoGa.getCOLOR().name());
		} else {
			vehiculo.setIdColor("-");
		}

		if (vehiculoGa.getCLASIFICACIONITV() != null) {
			String clasificacion = String.valueOf(vehiculoGa.getCLASIFICACIONITV());
			// Puede que el primer número sea un 0, por lo tanto al ser BigInteger no vendrá el 0
			if (clasificacion.length() == 3) {
				vehiculo.setClasificacionItv("0" + clasificacion);
			} else {
				vehiculo.setClasificacionItv(clasificacion);
			}
		}

		if (vehiculoGa.getCLASIFICACIONVEHICULO() != null && vehiculoGa.getCLASIFICACIONVEHICULO().length() >= 4) {
			vehiculo.setIdCriterioConstruccion(vehiculoGa.getCLASIFICACIONVEHICULO().substring(0, 2));
			vehiculo.setIdCriterioUtilizacion(vehiculoGa.getCLASIFICACIONVEHICULO().substring(2, 4));
		}

		if (vehiculoGa.getPLAZAS() != null) {
			try {
				vehiculo.setPlazas(new BigDecimal(vehiculoGa.getPLAZAS()));
			} catch (Exception e) {
				vehiculo.setPlazas(null);
			}
		}

		if (vehiculoGa.getCILINDRADA() != null) {
			if (TipoVehiculoEnum.CICLOMOTOR_DE_2_RUEDAS.getValorEnum().equals(vehiculo.getTipoVehiculo()) || TipoVehiculoEnum.CICLOMOTOR_DE_3_RUEDAS.getValorEnum().equals(vehiculo
					.getTipoVehiculo())) {
				BigDecimal valor = new BigDecimal(vehiculoGa.getCILINDRADA()).setScale(2, BigDecimal.ROUND_DOWN);
				if (valor != null) {
					vehiculo.setCilindrada(valor.toString());
				}
			} else {
				String valor = vehiculoGa.getCILINDRADA();
				StringUtils.stripStart(valor, "0");
				vehiculo.setCilindrada(valor);
			}
		}

		if (vehiculoGa.getEMISIONCO2() != null) {
			vehiculo.setCo2(vehiculoGa.getEMISIONCO2().toString());
		}

		if (vehiculoGa.getTARA() != null) {
			vehiculo.setTara(vehiculoGa.getTARA().toString());
		}

		if (vehiculoGa.getMASA() != null) {
			vehiculo.setPesoMma(vehiculoGa.getMASA().toString());
		}

		if (vehiculoGa.getMASAMAXIMAADMISIBLE() != null) {
			vehiculo.setMtmaItv(vehiculoGa.getMASAMAXIMAADMISIBLE().toString());
		}

		if (vehiculoGa.getFECHAITV() != null && !vehiculoGa.getFECHAITV().isEmpty()) {
			vehiculo.setFechaItv(utilesFecha.formatoFecha(FORMATO_FECHA, vehiculoGa.getFECHAITV()));
		}

		if (vehiculoGa.getTIPOTARJETAITV() != null) {
			vehiculo.setIdTipoTarjetaItv(utilidadesConversiones.tipoTarjetaItv(vehiculoGa.getTIPOTARJETAITV()));
		}

		if (vehiculoGa.getPLAZASPIE() != null) {
			try {
				BigDecimal plazasPie = new BigDecimal(vehiculoGa.getPLAZASPIE());
				if (!plazasPie.equals(BigDecimal.ZERO)) {
					vehiculo.setnPlazasPie(new BigDecimal(vehiculoGa.getPLAZASPIE()));
				} else {
					vehiculo.setnPlazasPie(null);
				}
			} catch (NumberFormatException ex) {
				vehiculo.setnPlazasPie(null);
			}
		}

		if (StringUtils.isNotBlank(vehiculoGa.getUSADO()) && (("SI".equalsIgnoreCase(vehiculoGa.getUSADO().trim())) || (TipoSN.S.value().equals(vehiculoGa.getUSADO().trim().toUpperCase())))) {
			vehiculo.setVehiUsado("SI");
			if (vehiculoGa.getFECHAPRIMERAMATRICULACION() != null && !vehiculoGa.getFECHAPRIMERAMATRICULACION().isEmpty()) {
				vehiculo.setFechaPrimMatri(utilesFecha.formatoFecha(FORMATO_FECHA, vehiculoGa.getFECHAPRIMERAMATRICULACION()));
			}
		} else {
			vehiculo.setVehiUsado("NO");
		}

		if (StringUtils.isNotBlank(vehiculoGa.getLUGARMATRICULACION())) {
			vehiculo.setIdLugarMatriculacion(vehiculoGa.getLUGARMATRICULACION());
		} else {
			vehiculo.setIdLugarMatriculacion("-1");
		}

		if (vehiculoGa.getKM() != null) {
			try {
				vehiculo.setKmUso(new BigDecimal(vehiculoGa.getKM()));
			} catch (Exception e) {
				vehiculo.setKmUso(null);
			}
		}

		if (vehiculoGa.getCUENTAHORAS() != null) {
			try {
				vehiculo.setHorasUso(new BigDecimal(vehiculoGa.getCUENTAHORAS()));
			} catch (Exception e) {
				vehiculo.setHorasUso(null);
			}
		}

		if (vehiculoGa.getIDALIMENTACION() != null) {
			vehiculo.setTipoAlimentacion(vehiculoGa.getIDALIMENTACION().value());
		}

		if (vehiculoGa.getIDCARROCERIA() != null) {
			vehiculo.setCarroceria(vehiculoGa.getIDCARROCERIA().value());
		}

		if (vehiculoGa.getCONSUMO() != null) {
			try {
				vehiculo.setConsumo(new BigDecimal(vehiculoGa.getCONSUMO()));
			} catch (NumberFormatException e) {
				vehiculo.setConsumo(null);
			}
		}

		if (vehiculoGa.getDISTANCIAENTREEJES() != null) {
			try {
				vehiculo.setDistanciaEjes(new BigDecimal(vehiculoGa.getDISTANCIAENTREEJES()));
			} catch (NumberFormatException e) {
				vehiculo.setDistanciaEjes(null);
			}
		}

		if (vehiculoGa.getIDCARBURANTE() != null) {
			vehiculo.setIdCarburante(vehiculoGa.getIDCARBURANTE().value());
		}
		if (vehiculoGa.getIDHOMOLOGACION() != null) {
			vehiculo.setIdDirectivaCee(vehiculoGa.getIDHOMOLOGACION().value());
		}

		if (StringUtils.isNotBlank(vehiculoGa.getIMPORTADO()) && (vehiculoGa.getIMPORTADO().trim().toUpperCase().equals(TipoSN.S.value()) || vehiculoGa.getIMPORTADO().trim().equalsIgnoreCase(
				"SI"))) {
			vehiculo.setImportado("SI");
		} else {
			vehiculo.setImportado("NO");
		}

		if (vehiculoGa.getMOM() != null) {
			try {
				vehiculo.setMom(new BigDecimal(vehiculoGa.getMOM()));
			} catch (NumberFormatException e) {
				vehiculo.setMom(null);
			}
		}

		if (vehiculoGa.getREDUCCIONECO() != null) {
			vehiculo.setReduccionEco(new BigDecimal(vehiculoGa.getREDUCCIONECO()));
		}

		if (StringUtils.isNotBlank(vehiculoGa.getSUBASTA()) && (("SI".equalsIgnoreCase(vehiculoGa.getSUBASTA().trim())) || (TipoSN.S.value().equalsIgnoreCase(vehiculoGa.getSUBASTA().trim())))) {
			vehiculo.setSubastado("SI");
		} else {
			vehiculo.setSubastado("NO");
		}

		if (vehiculoGa.getVIAANTERIOR() != null) {
			try {
				vehiculo.setViaAnterior(new BigDecimal(vehiculoGa.getVIAANTERIOR()));
			} catch (NumberFormatException e) {
				log.error("No se ha podido parsear la VIA ANTERIOR a BigDecimal para el paquete: " + e);
				vehiculo.setViaAnterior(null);
			}
		}

		if (vehiculoGa.getVIAPOSTERIOR() != null) {
			try {
				vehiculo.setViaPosterior(new BigDecimal(vehiculoGa.getVIAPOSTERIOR()));
			} catch (NumberFormatException e) {
				log.error("No se ha podido parsear la VIA POSTERIOR a BigDecimal para el paquete: " + e);
				vehiculo.setViaPosterior(null);
			}
		}

		if (StringUtils.isNotBlank(vehiculoGa.getCHECKCADUCIDADITV()) && (("SI".equalsIgnoreCase(vehiculoGa.getCHECKCADUCIDADITV().trim())) || (TipoSN.S.value().equals(vehiculoGa
				.getCHECKCADUCIDADITV().trim().toUpperCase())))) {
			vehiculo.setCheckFechaCaducidadItv("SI");
		} else {
			vehiculo.setCheckFechaCaducidadItv("NO");
		}

		if (vehiculoGa.getTIPOINSPECCIONITV() != null) {
			vehiculo.setIdMotivoItv(vehiculoGa.getTIPOINSPECCIONITV().value());
		}

		if (vehiculoGa.getPAISFABRICACION() != null) {
			try {
				vehiculo.setPaisFabricacion(PaisFabricacion.getPaisFabricacion(vehiculoGa.getPAISFABRICACION()).getValorEnum());
			} catch (Exception e) {
				vehiculo.setPaisFabricacion(null);
			}
		}

		if (StringUtils.isNotBlank(vehiculoGa.getFICHATECNICARD750()) && "SI".equalsIgnoreCase(vehiculoGa.getFICHATECNICARD750())) {
			vehiculo.setFichaTecnicaRd750(Boolean.TRUE);
		} else {
			vehiculo.setFichaTecnicaRd750(Boolean.FALSE);
		}

		if (StringUtils.isNotBlank(vehiculoGa.getMARCABASE())) {
			String marcaSinEditarBase = utilidadesConversiones.getMarcaSinEditar(vehiculoGa.getMARCABASE());
			vehiculo.setCodigoMarcaBase(servicioMarcaDgt.getCodMarcaPorMarcaSinEditar(marcaSinEditarBase, Boolean.TRUE));
		}

		if (vehiculoGa.getMOMBASE() != null) {
			try {
				vehiculo.setMomBase(new BigDecimal(vehiculoGa.getMOMBASE()));
			} catch (Exception e) {
				vehiculo.setMomBase(null);
			}
		}

		if (vehiculoGa.getCATEGORIAELECTRICA() != null) {
			vehiculo.setCategoriaElectrica(vehiculoGa.getCATEGORIAELECTRICA().value());
		}

		if (vehiculoGa.getAUTONOMIAELECTRICA() != null) {
			try {
				vehiculo.setAutonomiaElectrica(new BigDecimal(vehiculoGa.getAUTONOMIAELECTRICA()));
			} catch (Exception e) {
				vehiculo.setMomBase(null);
			}
		}

		if (datosImpuestos.getDATOS576() != null) {
			VehiculoVO vehiculoPrever = new VehiculoVO();
			vehiculoPrever.setBastidor(datosImpuestos.getDATOS576().getNUMEROBASTIDORPREVER576());
			vehiculoPrever.setMatricula(datosImpuestos.getDATOS576().getMATRICULAPREVER576());
			vehiculoPrever.setModelo(datosImpuestos.getDATOS576().getMODELOPREVER576());

			if (StringUtils.isNotBlank(datosImpuestos.getDATOS576().getMARCAPREVER576())) {
				vehiculoPrever.setCodigoMarca(datosImpuestos.getDATOS576().getMARCAPREVER576());
			}

			if (StringUtils.isNotBlank(datosImpuestos.getDATOS576().getCLASIFICACIONPREVER576())) {
				vehiculoPrever.setClasificacionItv(datosImpuestos.getDATOS576().getCLASIFICACIONPREVER576());
				if (datosImpuestos.getDATOS576().getCLASIFICACIONPREVER576().length() == 4) {
					vehiculoPrever.setIdCriterioConstruccion(datosImpuestos.getDATOS576().getCLASIFICACIONPREVER576().substring(0, 2));
					vehiculoPrever.setIdCriterioUtilizacion(datosImpuestos.getDATOS576().getCLASIFICACIONPREVER576().substring(2, 4));
				}
			}
			vehiculoPrever.setTipoItv(datosImpuestos.getDATOS576().getTIPOITVPREVER576());

			vehiculo.setVehiculoPrever(vehiculoPrever);
		}

		if (integrador != null) {
			PersonaVO personaIntegrador = new PersonaVO();
			if (StringUtils.isNotBlank(integrador.getRAZONSOCIALIMPORTADOR())) {
				personaIntegrador.setApellido1RazonSocial(integrador.getRAZONSOCIALIMPORTADOR());
			} else if (StringUtils.isNotBlank(integrador.getAPELLIDO1IMPORTADOR())) {
				personaIntegrador.setApellido1RazonSocial(integrador.getAPELLIDO1IMPORTADOR());
			} else {
				personaIntegrador.setApellido1RazonSocial("");
			}

			personaIntegrador.setApellido2(integrador.getAPELLIDO2IMPORTADOR());
			personaIntegrador.setNombre(integrador.getNOMBREIMPORTADOR());
			PersonaPK id = new PersonaPK();
			id.setNif(integrador.getDNIIMPORTADOR());
			id.setNumColegiado(contrato.getColegiado().getNumColegiado());
			personaIntegrador.setId(id);
			personaIntegrador.setEstado(new Long(1));

			vehiculo.setPersona(personaIntegrador);
		}

		if (StringUtils.isNotBlank(vehiculoGa.getPROVINCIAVEHICULO())) {
			DireccionVO direccion = new DireccionVO();
			direccion.setIdProvincia(utilidadesConversiones.getIdProvinciaFromSiglas(vehiculoGa.getPROVINCIAVEHICULO()));

			DgtMunicipioVO dgtMunicipio = servicioDireccion.getMunicipioDGTPorNombre(vehiculoGa.getMUNICIPIOVEHICULO(), direccion.getIdProvincia());
			if (dgtMunicipio != null) {
				direccion.setIdMunicipio(dgtMunicipio.getCodigoIne());
			}

			direccion.setNombreVia(vehiculoGa.getDOMICILIOVEHICULO());
			direccion.setNumero(vehiculoGa.getNUMERODIRECCIONVEHICULO());
			direccion.setCodPostalCorreos(vehiculoGa.getCPVEHICULO());
			direccion.setLetra(vehiculoGa.getLETRADIRECCIONVEHICULO());
			direccion.setEscalera(vehiculoGa.getESCALERADIRECCIONVEHICULO());
			direccion.setBloque(vehiculoGa.getBLOQUEDIRECCIONVEHICULO());
			direccion.setPlanta(vehiculoGa.getPISODIRECCIONVEHICULO());
			direccion.setPuerta(vehiculoGa.getPUERTADIRECCIONVEHICULO());
			direccion.setIdTipoVia(utilidadesConversiones.tipoVia(vehiculoGa.getSIGLASDIRECCIONVEHICULO()));
			if (vehiculoGa.getPUEBLOVEHICULO() != null) {
				direccion.setPuebloCorreos(vehiculoGa.getPUEBLOVEHICULO().toUpperCase());
			}

			if (StringUtils.isNotBlank(vehiculoGa.getKMDIRECCIONVEHICULO())) {
				try {
					direccion.setKm(new BigDecimal(vehiculoGa.getKMDIRECCIONVEHICULO()));
				} catch (Exception e) {
					direccion.setKm(null);
				}
			}

			if (StringUtils.isNotBlank(vehiculoGa.getHECTOMETRODIRECCIONVEHICULO())) {
				try {
					direccion.setHm(new BigDecimal(vehiculoGa.getHECTOMETRODIRECCIONVEHICULO()));
				} catch (Exception e) {
					direccion.setHm(null);
				}
			}

			vehiculo.setDireccion(direccion);
		}
		return vehiculo;
	}

	private TramiteTrafMatrVO convertirTramiteFormatoOegam2MatwToVO(String numColegiadoImportacion, MATRICULACION matriculacion, ContratoVO contrato, Boolean tienePermisoAdmin, String tipoCreacion,
			Long idUsuario) {
		TramiteTrafMatrVO tramite = new TramiteTrafMatrVO();
		tramite.setFechaAlta(utilesFecha.getFechaActualDesfaseBBDD());
		tramite.setFechaUltModif(tramite.getFechaAlta());
		tramite.setNumExpediente(null);
		tramite.setContrato(contrato);
		tramite.setYbestado(BigDecimal.ZERO);
		tramite.setNumColegiado(contrato.getColegiado().getNumColegiado());
		tramite.setRefPropia(matriculacion.getREFERENCIAPROPIA());
		tramite.setJefaturaTrafico(contrato.getJefaturaTrafico());
		tramite.setTipoTramite(TipoTramiteTrafico.Matriculacion.getValorEnum());
		tramite.setIdTipoCreacion(new BigDecimal(tipoCreacion));
		tramite.setEstado(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));
		UsuarioVO usuario = new UsuarioVO();
		usuario.setIdUsuario(idUsuario.longValue());
		tramite.setUsuario(usuario);

		tramite.setAnotaciones(matriculacion.getOBSERVACIONES());

		if (matriculacion.getFECHAPRESENTACION() != null && !matriculacion.getFECHAPRESENTACION().isEmpty()) {
			tramite.setFechaPresentacion(utilesFecha.formatoFecha(FORMATO_FECHA, matriculacion.getFECHAPRESENTACION()));
		} else {
			tramite.setFechaPresentacion(new Date());
		}

		if (matriculacion.getDATOSARRENDATARIO() != null) {
			if (StringUtils.isNotBlank(matriculacion.getDATOSARRENDATARIO().getDNIARR())) {
				tramite.setRenting("SI");
			} else {
				tramite.setRenting("NO");
			}
		}

		if (matriculacion.getDATOSVEHICULO() != null) {
			if (matriculacion.getDATOSARRENDATARIO() != null) {
				if (StringUtils.isNotBlank(matriculacion.getDATOSARRENDATARIO().getDNIARR()) && StringUtils.isNotBlank(matriculacion.getDATOSVEHICULO().getRENTING())) {
					tramite.setRenting(matriculacion.getDATOSVEHICULO().getRENTING().equalsIgnoreCase("SI") ? "SI" : "NO");
				} else {
					tramite.setRenting("NO");
				}
			}

			if (StringUtils.isNotBlank(matriculacion.getDATOSVEHICULO().getCARSHARING())) {
				tramite.setCarsharing(matriculacion.getDATOSVEHICULO().getCARSHARING().equalsIgnoreCase("SI") ? "SI" : "NO");
			} else {
				tramite.setCarsharing("NO");
			}
		}

		if (matriculacion.getDATOSLIMITACION() != null) {
			tramite.setIedtm("E".equals(matriculacion.getDATOSLIMITACION().getTIPOLIMITACION()) ? "E" : "");
			tramite.setFinancieraIedtm(matriculacion.getDATOSLIMITACION().getFINANCIERALIMITACION());

			if (StringUtils.isNotBlank(matriculacion.getDATOSLIMITACION().getNUMEROREGISTROLIMITACION())) {
				tramite.setnRegIedtm(matriculacion.getDATOSLIMITACION().getNUMEROREGISTROLIMITACION());
			}

			if (matriculacion.getDATOSLIMITACION().getFECHALIMITACION() != null && !matriculacion.getDATOSLIMITACION().getFECHALIMITACION().isEmpty()) {
				tramite.setFechaIedtm(utilesFecha.formatoFecha(FORMATO_FECHA, matriculacion.getDATOSLIMITACION().getFECHALIMITACION()));
			}
		}

		if (matriculacion.getDATOSIMPUESTOS() != null) {
			tramite.setCem(matriculacion.getDATOSIMPUESTOS().getCODIGOELECTRONICOAEAT());
			tramite.setCema(matriculacion.getDATOSIMPUESTOS().getCEMA());

			if (StringUtils.isNotBlank(matriculacion.getDATOSIMPUESTOS().getEXENTOCEM())) {
				tramite.setExentoCem(matriculacion.getDATOSIMPUESTOS().getEXENTOCEM().equalsIgnoreCase("SI") ? "SI" : "NO");
			} else {
				tramite.setExentoCem("NO");
			}

			if (StringUtils.isNotBlank(matriculacion.getDATOSIMPUESTOS().getJUSTIFICADOIVTM())) {
				tramite.setJustificadoIvtm(matriculacion.getDATOSIMPUESTOS().getJUSTIFICADOIVTM().equalsIgnoreCase("SI") ? "SI" : "NO");
			} else {
				tramite.setJustificadoIvtm("NO");
			}

			if (matriculacion.getDATOSIMPUESTOS().getDATOSIMVTM() != null) {
				IvtmMatriculacionVO ivtmMatriculacionVO = new IvtmMatriculacionVO();

				if (matriculacion.getDATOSIMPUESTOS().getDATOSIMVTM().getFECHAALTAAUTOLIQUIDACIONIMVTM() != null && !matriculacion.getDATOSIMPUESTOS().getDATOSIMVTM()
						.getFECHAALTAAUTOLIQUIDACIONIMVTM().isEmpty()) {
					ivtmMatriculacionVO.setFechaPago(utilesFecha.formatoFecha(FORMATO_FECHA, matriculacion.getDATOSIMPUESTOS().getDATOSIMVTM().getFECHAALTAAUTOLIQUIDACIONIMVTM()));
				}

				ivtmMatriculacionVO.setImporte(matriculacion.getDATOSIMPUESTOS().getDATOSIMVTM().getCUOTAAPAGARIMVTM());
				ivtmMatriculacionVO.setNrc(matriculacion.getDATOSIMPUESTOS().getDATOSIMVTM().getNRC());
				ivtmMatriculacionVO.setEstadoIvtm(new BigDecimal(EstadoIVTM.Ivtm_Importado.getValorEnum()));
				tramite.setIvtmMatriculacionVO(ivtmMatriculacionVO);
			}

			if (matriculacion.getDATOSIMPUESTOS().getDATOS576() != null) {
				tramite.setBaseImponible576(matriculacion.getDATOSIMPUESTOS().getDATOS576().getBASEIMPONIBLE576());
				tramite.setBaseImpoReducida576(matriculacion.getDATOSIMPUESTOS().getDATOS576().getBASEIMPONIBLEREDUCIDA576());
				tramite.setTipoGravamen576(matriculacion.getDATOSIMPUESTOS().getDATOS576().getTIPOGRAVAMEN576());
				tramite.setCuota576(matriculacion.getDATOSIMPUESTOS().getDATOS576().getCUOTA576());
				tramite.setDeduccionLineal576(matriculacion.getDATOSIMPUESTOS().getDATOS576().getDEDUCCIONLINEAL576());
				tramite.setCuotaIngresar576(matriculacion.getDATOSIMPUESTOS().getDATOS576().getCUOTAINGRESAR576());
				tramite.setaDeducir576(matriculacion.getDATOSIMPUESTOS().getDATOS576().getADEDUCIR576());
				tramite.setImporte576(matriculacion.getDATOSIMPUESTOS().getDATOS576().getIMPORTETOTAL576());
				tramite.setLiquidacion576(matriculacion.getDATOSIMPUESTOS().getDATOS576().getRESULTADOLIQUIDACION576());
				tramite.setnDeclaracionComp576(matriculacion.getDATOSIMPUESTOS().getDATOS576().getDECLARACIONCOMPLEMENTARIA576());
				tramite.setCausaHechoImpon576(matriculacion.getDATOSIMPUESTOS().getDATOS576().getCAUSAHECHOIMPONIBLE());
				tramite.setObservaciones576(matriculacion.getDATOSIMPUESTOS().getDATOS576().getOBSERVACIONES576());

				if (StringUtils.isNotBlank(matriculacion.getDATOSIMPUESTOS().getDATOS576().getEXENTO576())) {
					tramite.setExento576(matriculacion.getDATOSIMPUESTOS().getDATOS576().getEXENTO576().equalsIgnoreCase("SI") ? "SI" : "NO");
				}

				if (matriculacion.getDATOSIMPUESTOS().getDATOS576().getEJERCICIODEVENGO576() != null) {
					tramite.setEjercicio576(new BigDecimal(matriculacion.getDATOSIMPUESTOS().getDATOS576().getEJERCICIODEVENGO576()));
				}
			}

			if (matriculacion.getDATOSIMPUESTOS().getDATOSNRC() != null) {
				tramite.setNrc576(matriculacion.getDATOSIMPUESTOS().getDATOSNRC().getCODIGONRC());

				if (matriculacion.getDATOSIMPUESTOS().getDATOSNRC().getFECHAOPERACIONNRC() != null && !matriculacion.getDATOSIMPUESTOS().getDATOSNRC().getFECHAOPERACIONNRC().isEmpty()) {
					tramite.setFechaPago576(utilesFecha.formatoFecha(FORMATO_FECHA, matriculacion.getDATOSIMPUESTOS().getDATOSNRC().getFECHAOPERACIONNRC()));
				}
			}

			if (matriculacion.getDATOSIMPUESTOS().getDATOS0506() != null) {
				if (matriculacion.getDATOSIMPUESTOS().getDATOS0506().getMOTIVOEXENCION05() != null) {
					tramite.setIdReduccion05(matriculacion.getDATOSIMPUESTOS().getDATOS0506().getMOTIVOEXENCION05().value());
				}

				if ((tramite.getIdReduccion05() == null || tramite.getIdReduccion05().isEmpty()) && tramite.getnRegIedtm() != null && !tramite.getnRegIedtm().isEmpty()) {
					tramite.setIdReduccion05(utilidadesConversiones.numRegistroLimitacionToMotivo(tramite.getnRegIedtm(), "05"));
				}

				if (matriculacion.getDATOSIMPUESTOS().getDATOS0506().getMOTIVOEXENCION06() != null) {
					tramite.setIdNoSujeccion06(matriculacion.getDATOSIMPUESTOS().getDATOS0506().getMOTIVOEXENCION06().value());
				}

				if ((tramite.getIdNoSujeccion06() == null || tramite.getIdNoSujeccion06().isEmpty()) && tramite.getnRegIedtm() != null && !tramite.getnRegIedtm().isEmpty()) {
					tramite.setIdNoSujeccion06(utilidadesConversiones.numRegistroLimitacionToMotivo(tramite.getnRegIedtm(), "06"));
				}
			}
		}

		tramite.setTipoTramiteMatr(TipoTramiteMatriculacion.MATRICULAR_TIPO_DEFINITIVA.getValorEnum());

		if (matriculacion.getTASA() != null && !matriculacion.getTASA().isEmpty()) {
			if (tienePermisoAdmin) {
				if (contrato.getColegiado().getNumColegiado().equals(numColegiadoImportacion)) {
					TasaVO tasa = new TasaVO();
					tasa.setCodigoTasa(matriculacion.getTASA());
					tasa.setTipoTasa(matriculacion.getTIPOTASA());
					tramite.setTasa(tasa);
				}
			} else {
				TasaVO tasa = new TasaVO();
				tasa.setCodigoTasa(matriculacion.getTASA());
				tasa.setTipoTasa(matriculacion.getTIPOTASA());
				tramite.setTasa(tasa);
			}
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