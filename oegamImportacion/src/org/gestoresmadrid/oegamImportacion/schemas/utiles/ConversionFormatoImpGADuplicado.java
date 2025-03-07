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
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.personas.model.enumerados.SexoPersona;
import org.gestoresmadrid.core.personas.model.enumerados.SubtipoPersona;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.core.personas.model.vo.PersonaPK;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafDuplicadoVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegamConversiones.conversion.ParametrosConversiones;
import org.gestoresmadrid.oegamConversiones.jaxb.duplicado.DATOSCOTITULAR;
import org.gestoresmadrid.oegamConversiones.jaxb.duplicado.DATOSREPRESENTANTETITULAR;
import org.gestoresmadrid.oegamConversiones.jaxb.duplicado.DATOSTITULAR;
import org.gestoresmadrid.oegamConversiones.jaxb.duplicado.DATOSVEHICULO;
import org.gestoresmadrid.oegamConversiones.jaxb.duplicado.DUPLICADO;
import org.gestoresmadrid.oegamConversiones.jaxb.duplicado.FORMATOOEGAM2DUPLICADO;
import org.gestoresmadrid.oegamConversiones.schemas.utiles.XMLDuplicadoFactory;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoImportacionBean;
import org.gestoresmadrid.oegamImportacion.constantes.Constantes;
import org.gestoresmadrid.oegamImportacion.contrato.service.ServicioContratoImportacion;
import org.gestoresmadrid.oegamImportacion.direccion.service.ServicioDireccionImportacion;
import org.gestoresmadrid.oegamImportacion.persona.service.ServicioPersonaImportacion;
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
public class ConversionFormatoImpGADuplicado implements Serializable {

	private static final long serialVersionUID = -6411289853872765344L;

	private static ILoggerOegam log = LoggerOegam.getLogger(ConversionFormatoImpGADuplicado.class);

	@Autowired
	ServicioContratoImportacion servicioContrato;

	@Autowired
	ServicioDireccionImportacion servicioDireccion;

	@Autowired
	ServicioPersonaImportacion servicioPersona;

	@Autowired
	ParametrosConversiones conversiones;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	XMLDuplicadoFactory xMLDuplicadoFactory;

	@Autowired
	UtilidadesAnagramaImportacion utilidadesAnagrama;

	@Autowired
	UtilidadesNIFValidatorImportacion utilidadesNIFValidator;

	@Autowired
	UtilidadesConversionesImportacion utilidadesConversiones;

	@Autowired
	UtilesFecha utilesFecha;

	public ResultadoImportacionBean convertirFicheroFormatoGA(File fichero, ContratoVO contrato, Boolean tienePermisoAdmin) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			xMLDuplicadoFactory.validarXMLFORMATOGADuplicado(fichero, gestorPropiedades.valorPropertie(Constantes.PROPERTIES_RUTA_ESQUEMA_DUPLICADO));
			FORMATOOEGAM2DUPLICADO ficheroDuplicado = xMLDuplicadoFactory.getFormatoDuplicado(fichero);
			if (ficheroDuplicado.getCABECERA() == null || ficheroDuplicado.getCABECERA().getDATOSGESTORIA() == null || ficheroDuplicado.getCABECERA().getDATOSGESTORIA().getPROFESIONAL() == null || ""
					.equals(ficheroDuplicado.getCABECERA().getDATOSGESTORIA().getPROFESIONAL())) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de indicar los datos de la cabecera para poder importar el fichero.");
			} else if (!contrato.getColegiado().getNumColegiado().equals(ficheroDuplicado.getCABECERA().getDATOSGESTORIA().getPROFESIONAL()) && !tienePermisoAdmin) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Solo se pueden importar tramites del mismo gestor.");
			} else {
				resultado.setFormatoOegam2Duplicado(ficheroDuplicado);
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

	public ResultadoImportacionBean convertirFormatoGAToVO(FORMATOOEGAM2DUPLICADO formatoOegam2Duplicado, ContratoVO contrato, Boolean tienePermisoAdmin, String tipoCreacion, Long idUsuario) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		int posicion = 0;
		int cont = 1;
		for (DUPLICADO duplicadoImportar : formatoOegam2Duplicado.getDUPLICADO()) {
			log.error("INICIO CONVERTIR VO Duplicado tramite " + cont + " del colegiado " + contrato.getColegiado().getNumColegiado());
			try {
				TramiteTrafDuplicadoVO tramiteDuplicado = convertirTramiteFormatoOegam2DuplicadoToVO(formatoOegam2Duplicado.getCABECERA().getDATOSGESTORIA().getPROFESIONAL().toString(),
						duplicadoImportar, contrato, tienePermisoAdmin, tipoCreacion, idUsuario);
				List<IntervinienteTraficoVO> listaIntervinientes = new ArrayList<IntervinienteTraficoVO>();
				if (duplicadoImportar.getDATOSVEHICULO() != null) {
					tramiteDuplicado.setVehiculo(convertirVehiculoGAtoVO(duplicadoImportar.getDATOSVEHICULO(), contrato));
				}
				if (duplicadoImportar.getDATOSTITULAR() != null) {
					IntervinienteTraficoVO interviniente = convertirTitularGAtoVO(duplicadoImportar.getDATOSTITULAR(), contrato);
					if (interviniente != null) {
						listaIntervinientes.add(interviniente);
					}
				}
				if (duplicadoImportar.getDATOSREPRESENTANTETITULAR() != null) {
					IntervinienteTraficoVO interviniente = convertirRepreTitularGAtoVO(duplicadoImportar.getDATOSREPRESENTANTETITULAR(), contrato);
					if (interviniente != null) {
						listaIntervinientes.add(interviniente);
					}
				}
				if (duplicadoImportar.getDATOSCOTITULAR() != null) {
					IntervinienteTraficoVO interviniente = convertirCotitularGAtoVO(duplicadoImportar.getDATOSCOTITULAR(), contrato);
					if (interviniente != null) {
						listaIntervinientes.add(interviniente);
					}
				}

				tramiteDuplicado.setIntervinienteTraficos(listaIntervinientes);

				resultado.addListaTramiteDuplicado(tramiteDuplicado, posicion);
				log.error("FIN CONVERTIR VO Duplicado tramite " + cont + " del colegiado " + contrato.getColegiado().getNumColegiado());
			} catch (Throwable th) {
				log.error("Error al convertir Duplicado tramite " + cont + " colegiado " + contrato.getColegiado().getNumColegiado(), th);
				resultado.addConversionesError(gestionarMensajeErrorConversion(duplicadoImportar));
			}
			cont++;
			posicion++;
		}
		return resultado;
	}

	private String gestionarMensajeErrorConversion(DUPLICADO duplicadoImportar) {
		String mensaje = "";
		if (duplicadoImportar != null) {
			if (duplicadoImportar.getDATOSVEHICULO() != null && StringUtils.isNotBlank(duplicadoImportar.getDATOSVEHICULO().getMATRICULA())) {
				mensaje = "Error el convertir el trámite con matrícula: " + duplicadoImportar.getDATOSVEHICULO().getMATRICULA();
			}
		}
		return mensaje;
	}

	private IntervinienteTraficoVO convertirCotitularGAtoVO(DATOSCOTITULAR cotitular, ContratoVO contrato) {
		IntervinienteTraficoVO interviniente = null;
		if (cotitular != null && StringUtils.isNotBlank(cotitular.getNIFCIF())) {
			interviniente = new IntervinienteTraficoVO();
			IntervinienteTraficoPK idInterviniente = new IntervinienteTraficoPK();
			idInterviniente.setNumColegiado(contrato.getColegiado().getNumColegiado());
			idInterviniente.setNif(cotitular.getNIFCIF().toUpperCase());
			idInterviniente.setTipoInterviniente(TipoInterviniente.CotitularTransmision.getValorEnum());
			interviniente.setId(idInterviniente);

			PersonaVO persona = new PersonaVO();
			PersonaPK idPersona = new PersonaPK();
			idPersona.setNif(cotitular.getNIFCIF().toUpperCase());
			idPersona.setNumColegiado(contrato.getColegiado().getNumColegiado());
			persona.setId(idPersona);

			if (StringUtils.isNotBlank(cotitular.getAPELLIDO1RAZONSOCIAL())) {
				persona.setApellido1RazonSocial(cotitular.getAPELLIDO1RAZONSOCIAL().toUpperCase());
			} else {
				PersonaVO personaBBDD = servicioPersona.getPersonaVO(persona.getId().getNif(), persona.getId().getNumColegiado());
				if (personaBBDD != null) {
					persona.setApellido1RazonSocial(personaBBDD.getApellido1RazonSocial());
				}
			}

			persona.setApellido2(cotitular.getAPELLIDO2());
			persona.setNombre(cotitular.getNOMBRE());
			if (cotitular.getSEXO() != null) {
				persona.setSexo(cotitular.getSEXO().name());
			}

			setDatosPersona(persona);
			persona.setEstado(new Long(1));
			interviniente.setPersona(persona);

			if (cotitular.getDIRECCION() != null) {
				if (StringUtils.isNotBlank(cotitular.getDIRECCION().getPROVINCIA())) {
					DireccionVO direccion = new DireccionVO();

					String idProvincia = utilidadesConversiones.getIdProvinciaFromSiglas(cotitular.getDIRECCION().getPROVINCIA());
					if (idProvincia != null && !idProvincia.isEmpty()) {
						direccion.setIdProvincia(idProvincia);
					} else {
						direccion.setIdProvincia(cotitular.getDIRECCION().getPROVINCIA());
					}

					MunicipioVO municipio = servicioDireccion.getMunicipioPorNombre(cotitular.getDIRECCION().getMUNICIPIO(), direccion.getIdProvincia());
					if (municipio != null) {
						direccion.setIdMunicipio(municipio.getId().getIdMunicipio());
					} else {
						direccion.setIdMunicipio(cotitular.getDIRECCION().getMUNICIPIO());
					}

					direccion.setNombreVia(cotitular.getDIRECCION().getNOMBREVIA());
					direccion.setViaSineditar(utilidadesConversiones.getViaSinEditar(direccion.getNombreVia()));
					direccion.setIdTipoVia(cotitular.getDIRECCION().getTIPOVIA());
					direccion.setNumero(cotitular.getDIRECCION().getNUMERO());
					direccion.setCodPostal(cotitular.getDIRECCION().getCODIGOPOSTAL());
					if (cotitular.getDIRECCION().getPUEBLO() != null) {
						direccion.setPueblo(cotitular.getDIRECCION().getPUEBLO().toUpperCase());
					}
					direccion.setLetra(cotitular.getDIRECCION().getLETRA());
					direccion.setEscalera(cotitular.getDIRECCION().getESCALERA());
					direccion.setBloque(cotitular.getDIRECCION().getBLOQUE());
					direccion.setPlanta(cotitular.getDIRECCION().getPISO());
					direccion.setPuerta(cotitular.getDIRECCION().getPUERTA());

					if (StringUtils.isNotBlank(cotitular.getDIRECCION().getKM())) {
						try {
							direccion.setKm(new BigDecimal(cotitular.getDIRECCION().getKM()));
						} catch (Exception e) {
							direccion.setKm(null);
						}
					}

					if (StringUtils.isNotBlank(cotitular.getDIRECCION().getHM())) {
						try {
							direccion.setHm(new BigDecimal(cotitular.getDIRECCION().getHM()));
						} catch (Exception e) {
							direccion.setHm(null);
						}
					}

					interviniente.setDireccion(direccion);
				}

			}
		}
		return interviniente;
	}

	private IntervinienteTraficoVO convertirRepreTitularGAtoVO(DATOSREPRESENTANTETITULAR repreTitular, ContratoVO contrato) {
		IntervinienteTraficoVO interviniente = null;
		if (repreTitular != null && StringUtils.isNotBlank(repreTitular.getNIFCIF())) {
			interviniente = new IntervinienteTraficoVO();
			IntervinienteTraficoPK idInterviniente = new IntervinienteTraficoPK();
			idInterviniente.setNumColegiado(contrato.getColegiado().getNumColegiado());
			idInterviniente.setNif(repreTitular.getNIFCIF().toUpperCase());
			idInterviniente.setTipoInterviniente(TipoInterviniente.RepresentanteTitular.getValorEnum());
			interviniente.setId(idInterviniente);

			if (repreTitular.getFECHAFINTUTELA() != null && !repreTitular.getFECHAFINTUTELA().isEmpty()) {
				interviniente.setFechaFin(utilesFecha.formatoFecha("dd/MM/yyyy", repreTitular.getFECHAFINTUTELA()));
			}

			if (repreTitular.getCONCEPTOTUTELA() != null) {
				interviniente.setConceptoRepre(repreTitular.getCONCEPTOTUTELA().name());
			}

			if (repreTitular.getMOTIVOTUTELA() != null) {
				interviniente.setIdMotivoTutela(repreTitular.getMOTIVOTUTELA().name());
			}

			if (repreTitular.getFECHAINICIOTUTELA() != null && !repreTitular.getFECHAINICIOTUTELA().isEmpty()) {
				interviniente.setFechaInicio(utilesFecha.formatoFecha("dd/MM/yyyy", repreTitular.getFECHAINICIOTUTELA()));
			}

			if (repreTitular.getFECHAFINTUTELA() != null && !repreTitular.getFECHAFINTUTELA().isEmpty()) {
				interviniente.setFechaFin(utilesFecha.formatoFecha("dd/MM/yyyy", repreTitular.getFECHAFINTUTELA()));
			}

			interviniente.setDatosDocumento(repreTitular.getDATOSDOCUMENTO());

			PersonaVO persona = new PersonaVO();
			PersonaPK idPersona = new PersonaPK();
			idPersona.setNif(repreTitular.getNIFCIF().toUpperCase());
			idPersona.setNumColegiado(contrato.getColegiado().getNumColegiado());
			persona.setId(idPersona);

			if (StringUtils.isNotBlank(repreTitular.getAPELLIDO1RAZONSOCIAL())) {
				persona.setApellido1RazonSocial(repreTitular.getAPELLIDO1RAZONSOCIAL().toUpperCase());
			} else {
				PersonaVO personaBBDD = servicioPersona.getPersonaVO(persona.getId().getNif(), persona.getId().getNumColegiado());
				if (personaBBDD != null) {
					persona.setApellido1RazonSocial(personaBBDD.getApellido1RazonSocial());
				}
			}

			persona.setApellido2(repreTitular.getAPELLIDO2());
			persona.setNombre(repreTitular.getNOMBRE());

			setDatosPersona(persona);
			persona.setEstado(new Long(1));
			interviniente.setPersona(persona);
		}
		return interviniente;
	}

	private IntervinienteTraficoVO convertirTitularGAtoVO(DATOSTITULAR titular, ContratoVO contrato) {
		IntervinienteTraficoVO interviniente = null;
		if (titular != null && StringUtils.isNotBlank(titular.getNIFCIF())) {
			interviniente = new IntervinienteTraficoVO();
			IntervinienteTraficoPK idInterviniente = new IntervinienteTraficoPK();
			idInterviniente.setNumColegiado(contrato.getColegiado().getNumColegiado());
			idInterviniente.setNif(titular.getNIFCIF().toUpperCase());
			idInterviniente.setTipoInterviniente(TipoInterviniente.Titular.getValorEnum());
			interviniente.setId(idInterviniente);

			PersonaVO persona = new PersonaVO();
			PersonaPK idPersona = new PersonaPK();
			idPersona.setNif(titular.getNIFCIF().toUpperCase());
			idPersona.setNumColegiado(contrato.getColegiado().getNumColegiado());
			persona.setId(idPersona);

			if (StringUtils.isNotBlank(titular.getAPELLIDO1RAZONSOCIAL())) {
				persona.setApellido1RazonSocial(titular.getAPELLIDO1RAZONSOCIAL().toUpperCase());
			} else {
				PersonaVO personaBBDD = servicioPersona.getPersonaVO(persona.getId().getNif(), persona.getId().getNumColegiado());
				if (personaBBDD != null) {
					persona.setApellido1RazonSocial(personaBBDD.getApellido1RazonSocial());
				}
			}

			persona.setApellido2(titular.getAPELLIDO2());
			persona.setNombre(titular.getNOMBRE());
			if (titular.getSEXO() != null) {
				persona.setSexo(titular.getSEXO().name());
			}

			setDatosPersona(persona);
			persona.setEstado(new Long(1));
			interviniente.setPersona(persona);

			if (titular.getDIRECCION() != null) {
				if (StringUtils.isNotBlank(titular.getDIRECCION().getPROVINCIA())) {
					DireccionVO direccion = new DireccionVO();

					String idProvincia = utilidadesConversiones.getIdProvinciaFromSiglas(titular.getDIRECCION().getPROVINCIA());
					if (idProvincia != null && !idProvincia.isEmpty()) {
						direccion.setIdProvincia(idProvincia);
					} else {
						direccion.setIdProvincia(titular.getDIRECCION().getPROVINCIA());
					}

					MunicipioVO municipio = servicioDireccion.getMunicipioPorNombre(titular.getDIRECCION().getMUNICIPIO(), direccion.getIdProvincia());
					if (municipio != null) {
						direccion.setIdMunicipio(municipio.getId().getIdMunicipio());
					} else {
						direccion.setIdMunicipio(titular.getDIRECCION().getMUNICIPIO());
					}

					direccion.setNombreVia(titular.getDIRECCION().getNOMBREVIA());
					direccion.setViaSineditar(utilidadesConversiones.getViaSinEditar(direccion.getNombreVia()));
					direccion.setIdTipoVia(titular.getDIRECCION().getTIPOVIA());
					direccion.setNumero(titular.getDIRECCION().getNUMERO());
					direccion.setCodPostal(titular.getDIRECCION().getCODIGOPOSTAL());
					if (titular.getDIRECCION().getPUEBLO() != null) {
						direccion.setPueblo(titular.getDIRECCION().getPUEBLO().toUpperCase());
					}
					direccion.setLetra(titular.getDIRECCION().getLETRA());
					direccion.setEscalera(titular.getDIRECCION().getESCALERA());
					direccion.setBloque(titular.getDIRECCION().getBLOQUE());
					direccion.setPlanta(titular.getDIRECCION().getPISO());
					direccion.setPuerta(titular.getDIRECCION().getPUERTA());

					if (StringUtils.isNotBlank(titular.getDIRECCION().getKM())) {
						try {
							direccion.setKm(new BigDecimal(titular.getDIRECCION().getKM()));
						} catch (Exception e) {
							direccion.setKm(null);
						}
					}

					if (StringUtils.isNotBlank(titular.getDIRECCION().getHM())) {
						try {
							direccion.setHm(new BigDecimal(titular.getDIRECCION().getHM()));
						} catch (Exception e) {
							direccion.setHm(null);
						}
					}
					interviniente.setDireccion(direccion);
				}
			}
		}
		return interviniente;
	}

	private VehiculoVO convertirVehiculoGAtoVO(DATOSVEHICULO vehiculoGa, ContratoVO contrato) {
		VehiculoVO vehiculo = new VehiculoVO();

		vehiculo.setNumColegiado(contrato.getColegiado().getNumColegiado());
		vehiculo.setMatricula(vehiculoGa.getMATRICULA());

		if (vehiculoGa.getFECHAITV() != null && !vehiculoGa.getFECHAITV().isEmpty()) {
			vehiculo.setFechaItv(utilesFecha.formatoFecha("dd/MM/yyyy", vehiculoGa.getFECHAITV()));
		}

		if (vehiculoGa.getFECHAMATRICULACION() != null && !vehiculoGa.getFECHAMATRICULACION().isEmpty()) {
			vehiculo.setFechaMatriculacion(utilesFecha.formatoFecha("dd/MM/yyyy", vehiculoGa.getFECHAMATRICULACION()));
		}

		return vehiculo;
	}

	private TramiteTrafDuplicadoVO convertirTramiteFormatoOegam2DuplicadoToVO(String numColegiadoImportacion, DUPLICADO duplicado, ContratoVO contrato, Boolean tienePermisoAdmin, String tipoCreacion,
			Long idUsuario) {
		TramiteTrafDuplicadoVO tramite = new TramiteTrafDuplicadoVO();
		tramite.setFechaAlta(utilesFecha.getFechaActualDesfaseBBDD());
		tramite.setFechaUltModif(tramite.getFechaAlta());
		tramite.setNumExpediente(null);
		tramite.setContrato(contrato);
		tramite.setNumColegiado(contrato.getColegiado().getNumColegiado());
		tramite.setJefaturaTrafico(contrato.getJefaturaTrafico());
		tramite.setTipoTramite(TipoTramiteTrafico.Duplicado.getValorEnum());
		tramite.setIdTipoCreacion(new BigDecimal(tipoCreacion));
		tramite.setEstado(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));
		UsuarioVO usuario = new UsuarioVO();
		usuario.setIdUsuario(idUsuario.longValue());
		tramite.setUsuario(usuario);

		if (duplicado.getDATOSDUPLICADO() != null) {
			tramite.setMotivoDuplicado(duplicado.getDATOSDUPLICADO().getMOTIVODUPLICADO());
			tramite.setRefPropia(duplicado.getDATOSDUPLICADO().getREFERENCIAPROPIA());

			if (duplicado.getDATOSDUPLICADO().isINDIMPRESIONPERMISOCIRCULACION() != null && duplicado.getDATOSDUPLICADO().isINDIMPRESIONPERMISOCIRCULACION()) {
				tramite.setImprPermisoCirculacion("SI");
			} else {
				tramite.setImprPermisoCirculacion("NO");
			}

			if (duplicado.getDATOSDUPLICADO().isINDVEHICULOIMPORTACION() != null && duplicado.getDATOSDUPLICADO().isINDVEHICULOIMPORTACION()) {
				tramite.setImportacion("SI");
			} else {
				tramite.setImportacion("NO");
			}

			tramite.setTasaImportacion(duplicado.getDATOSDUPLICADO().getCODIGOTASAVEHICULOIMPORTACION());
			tramite.setAnotaciones(duplicado.getDATOSDUPLICADO().getOBSERVACIONESDGT());
		}

		if (duplicado.getDATOSPAGOPRESENTACION() != null) {
			if (duplicado.getDATOSPAGOPRESENTACION().getFECHAPRESENTACION() != null && !duplicado.getDATOSPAGOPRESENTACION().getFECHAPRESENTACION().isEmpty()) {
				tramite.setFechaPresentacion(utilesFecha.formatoFecha("dd/MM/yyyy", duplicado.getDATOSPAGOPRESENTACION().getFECHAPRESENTACION()));
			} else {
				tramite.setFechaPresentacion(new Date());
			}

			if (duplicado.getDATOSPAGOPRESENTACION().getCODIGOTASA() != null && !duplicado.getDATOSPAGOPRESENTACION().getCODIGOTASA().isEmpty()) {
				if (tienePermisoAdmin) {
					if (contrato.getColegiado().getNumColegiado().equals(numColegiadoImportacion)) {
						TasaVO tasa = new TasaVO();
						tasa.setCodigoTasa(duplicado.getDATOSPAGOPRESENTACION().getCODIGOTASA());
						tramite.setTasa(tasa);
					}
				} else {
					TasaVO tasa = new TasaVO();
					tasa.setCodigoTasa(duplicado.getDATOSPAGOPRESENTACION().getCODIGOTASA());
					tramite.setTasa(tasa);
				}
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
