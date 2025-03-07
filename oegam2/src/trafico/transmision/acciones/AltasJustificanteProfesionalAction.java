package trafico.transmision.acciones;

import java.math.BigDecimal;
import java.util.Map;

import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados.EstadoJustificante;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.Tasa;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioJustificanteProfesional;
import org.gestoresmadrid.oegamBajas.service.ServicioTramiteBaja;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXParseException;

import escrituras.beans.Persona;
import escrituras.beans.ResultBean;
import escrituras.modelo.ModeloColegiado;
import escrituras.modelo.ModeloPersona;
import general.acciones.ActionBase;
import oegam.constantes.ConstantesPQ;
import trafico.beans.IntervinienteTrafico;
import trafico.beans.TramiteTraficoBean;
import trafico.beans.TramiteTraficoTransmisionBean;
import trafico.beans.schemas.generated.transTelematica.TipoSINO;
import trafico.beans.utiles.DuplicadoTramiteTraficoBeanPQConversion;
import trafico.beans.utiles.TransmisionTramiteTraficoBeanPQConversion;
import trafico.modelo.ModeloDuplicado;
import trafico.modelo.ModeloJustificanteProfesional;
import trafico.modelo.ModeloTransmision;
import trafico.utiles.constantes.Constantes;
import trafico.utiles.constantes.ConstantesTrafico;
import trafico.utiles.enumerados.DocumentosJustificante;
import trafico.utiles.enumerados.MotivoJustificante;
import trafico.utiles.enumerados.TipoCreacion;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import trafico.utiles.enumerados.TipoTramiteTraficoJustificante;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.ValidacionJustificantePorFechaExcepcion;
import utilidades.web.excepciones.ValidacionJustificanteRepetidoExcepcion;
import viafirma.utilidades.UtilesViafirma;

public class AltasJustificanteProfesionalAction extends ActionBase {

	private static final String ALTAS_TRAMITE_JUSTIFICANTE = "altasTramiteJustificante";

	private static final long serialVersionUID = 1L;

	// Log para errores
	private static final ILoggerOegam log = LoggerOegam.getLogger(AltasJustificanteProfesionalAction.class);

	private MotivoJustificante motivoJustificante;

	private DocumentosJustificante documentosJustificante;

	private TramiteTraficoBean tramiteTraficoBean;

	private IntervinienteTrafico titular;

	private String mensajeErrorFormulario;

	private String tipoIntervinienteBuscar;

	private TipoTramiteTraficoJustificante tipoTramiteJustificante;

	private String tipoTasa;

	private Integer diasValidez;

	private boolean checkIdFuerzasArmadas;

	private ModeloDuplicado modeloDuplicado;

	private ModeloJustificanteProfesional modeloJustificanteProfesional;

	private ModeloTransmision modeloTransmision;

	private UtilesViafirma utilesViafirma;

	@Autowired
	private ServicioJustificanteProfesional servicioJustificanteProfesional;

	@Autowired
	DuplicadoTramiteTraficoBeanPQConversion duplicadoTramiteTraficoBeanPQConversion;

	@Autowired
	TransmisionTramiteTraficoBeanPQConversion transmisionTramiteTraficoBeanPQConversion;

	@Autowired
	ServicioTramiteBaja servicioTramiteBaja;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	private UtilesColegiado utilesColegiado;

	// MÉTODOS

	public String inicio() throws Throwable {
		tramiteTraficoBean = new TramiteTraficoBean(true);
		tramiteTraficoBean.getJefaturaTrafico().setJefaturaProvincial(utilesColegiado.getIdJefaturaSesion());
		limpiarCamposSession();
		return generarJustificante(true);
	}

	public String generarJustificante() {
		try {
			return generarJustificante(false);
		} catch (SAXParseException spe) {
			addActionError(getModeloJustificanteProfesional().parseSAXParseException(spe));
			log.error("Error al generar el justificante", spe);
		} catch (Throwable t) {
			addActionError(t.toString());
			log.error(t.getMessage(), t);
		}
		return ALTAS_TRAMITE_JUSTIFICANTE;
	}

	public String generarJustificante(Boolean formularioInicial) throws Throwable {
		if (!utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MANTENIMIENTO_JUSTIFICANTES_PROFESIONALES)) {
			addActionError("No tiene permiso para realizar un justificante.");
			return ALTAS_TRAMITE_JUSTIFICANTE;
		}
		if (!formularioInicial) {
			// Lógica para crear un trámite acorde del tipo, meter en BD y llamar al WS
			try {
				ResultBean resultValidaciones = validacionesPrevias();
				if (resultValidaciones.getError()) {
					for (String mensaje : resultValidaciones.getListaMensajes()) {
						addActionError(mensaje);
					}
					return ALTAS_TRAMITE_JUSTIFICANTE;
				}
				rellenarDiasValidez();

				String pruebaCaduCert = getUtilesViafirma().recuperaCertificadoColegiado(utilesColegiado.getAlias());

				if (pruebaCaduCert != null) {
//					if (TipoTramiteTraficoJustificante.TransmisionElectronica.getValorEnum().equals(tipoTramiteJustificante.getValorEnum()) || TipoTramiteTraficoJustificante.CAMBIO_TITULAR
//							.getValorEnum().equals(tipoTramiteJustificante.getValorEnum()) || TipoTramiteTraficoJustificante.NOTIFICACION_VENTA.getValorEnum().equals(tipoTramiteJustificante
//									.getValorEnum())) {
						TramiteTraficoTransmisionBean tramite = new TramiteTraficoTransmisionBean(true);
						tramite.setElectronica("S");
						tramite.getTramiteTraficoBean().setTipoTramite(TipoTramiteTrafico.TransmisionElectronica.getValorEnum());
						tramite.setTramiteTraficoBean(tramiteTraficoBean);
						tramite.setAdquirienteBean(titular);
						tramite.setImpresionPermiso(TipoSINO.SI.value());

						ModeloColegiado modeloColegiado = new ModeloColegiado();
						tramite.getPresentadorBean().setPersona(modeloColegiado.obtenerColegiadoCompleto(tramite.getTramiteTraficoBean().getIdContrato() != null ? tramite.getTramiteTraficoBean()
								.getIdContrato() : utilesColegiado.getIdContratoSessionBigDecimal()));
						tramite.getPresentadorBean().setTipoInterviniente(TipoInterviniente.PresentadorTrafico.getValorEnum());

						tramite = guardarTramiteTransmision(tramite);
						setTramiteTraficoBean(tramite.getTramiteTraficoBean());
						setTitular(tramite.getAdquirienteBean());

						// 0004359: consulta justificantes profesionales. Si el nº de días de validez no se indica o es > 60 entonces diasValidez=30 días.
						getModeloJustificanteProfesional().validarYGenerarJustificanteTransmisionElectronica(tramite, utilesColegiado.getAlias(), utilesColegiado.getIdUsuarioSessionBigDecimal(),
								utilesColegiado.getIdContratoSessionBigDecimal(), diasValidez, utilesColegiado.getColegioDelContrato(), utilesColegiado.getNumColegiadoSession(), motivoJustificante, documentosJustificante,
								checkIdFuerzasArmadas, false);
//					} else if (TipoTramiteTraficoJustificante.Duplicado.getValorEnum().equals(tipoTramiteJustificante.getValorEnum()) || TipoTramiteTraficoJustificante.DUPLICADO.getValorEnum().equals(tipoTramiteJustificante.getValorEnum())) {
//						TramiteTraficoDuplicadoBean tramite = new TramiteTraficoDuplicadoBean(true);
//						tramite.setTramiteTrafico(tramiteTraficoBean);
//						tramite.setTitular(titular);
//						tramite = guardarTramiteDuplicado(tramite);
//						setTramiteTraficoBean(tramite.getTramiteTrafico());
//						setTitular(tramite.getTitular());
//						getModeloJustificanteProfesional().validarYGenerarJustificanteTramiteDuplicados(tramite, utilesColegiado.getAlias(), utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado
//								.getIdContratoSessionBigDecimal(), diasValidez, utilesColegiado.getColegioDelContrato(), utilesColegiado.getNumColegiadoSession(), motivoJustificante, documentosJustificante,
//								checkIdFuerzasArmadas, false);
//					} else if (TipoTramiteTraficoJustificante.Baja.getValorEnum().equals(tipoTramiteJustificante.getValorEnum()) || TipoTramiteTraficoJustificante.BAJA_TEMPORAL.getValorEnum().equals(tipoTramiteJustificante.getValorEnum())) {
//						IntervinienteTraficoDto interviniente = convertirInterviniente(titular, utilesColegiado.getNumColegiadoSession());
//						VehiculoDto vehiculo = convertirVehiculo(tramiteTraficoBean.getVehiculo(), utilesColegiado.getNumColegiadoSession());
//						ResultadoBean resultado = servicioTramiteBaja.crearTramiteBajaJustificante(utilesColegiado.getIdContratoSession(), utilesColegiado.getIdUsuarioSession(),
//								utilesColegiado.getNumColegiadoSession(), interviniente, vehiculo);
//
//						TramiteTraficoDuplicadoBean tramite = new TramiteTraficoDuplicadoBean(true);
//						tramite.setTramiteTrafico(tramiteTraficoBean);
//						tramite.setTitular(titular);
//						setTramiteTraficoBean(tramite.getTramiteTrafico());
//						setTitular(tramite.getTitular());
//						tramite.getTramiteTrafico().setEstado(EstadoTramiteTrafico.Iniciado.getValorEnum());
//						tramite.getTramiteTrafico().setNumColegiado(utilesColegiado.getNumColegiadoSession());
//						tramite.getTramiteTrafico().setNumExpediente(resultado.getNumExpediente());
//
//						getModeloJustificanteProfesional().validarYGenerarJustificanteTramiteBajas(tramite, utilesColegiado.getAlias(), utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado
//								.getIdContratoSessionBigDecimal(), diasValidez, utilesColegiado.getColegioDelContrato(), utilesColegiado.getNumColegiadoSession(), motivoJustificante, documentosJustificante,
//								checkIdFuerzasArmadas, false);
//					}
					addActionMessage("Solicitud de justificante firmada y enviada");
				} else {
					log.error("Error con la firma del justificante");
					addActionError("Error al guardar el justificante, no se ha podido realizar la firma del justificante ");
				}
			} catch (ValidacionJustificanteRepetidoExcepcion e) {
				log.info("Traza de prueba cuando un justificante está repetido. Expediente: " + tramiteTraficoBean.getNumExpediente());
				/*
				 * Si no se permite crear un justificante (ni siquiera forzándolo) para un adquiriente y matrícula que previamente ya se había generado
				 */
				if ("SI".equals(Constantes.PERMITE_SER_FORZADO)) {
					log.info("En el true de permite ser forzado.");
					servicioJustificanteProfesional.cambiarEstadoConEvolucionSega(tramiteTraficoBean.getNumExpediente(), EstadoJustificante.Iniciado, EstadoJustificante.Pendiente_autorizacion_colegio, utilesColegiado.getIdUsuarioSessionBigDecimal());
					addActionError("Tramite " + tramiteTraficoBean.getNumExpediente() + "; " + Constantes.JUSTIFICANTE_REPETIDO_EXCEPTION);
				} else {
					log.info("En el false de permite ser forzado.");
					addActionError("Tramite " + tramiteTraficoBean.getNumExpediente() + "; " + Constantes.JUSTIFICANTE_REPETIDO_EXCEPTION);
				}
			} catch (ValidacionJustificantePorFechaExcepcion e) {
				log.error("Entra en la exception de validacion por fecha.");
				servicioJustificanteProfesional.cambiarEstadoConEvolucionSega(tramiteTraficoBean.getNumExpediente(), EstadoJustificante.Iniciado, EstadoJustificante.Pendiente_autorizacion_colegio, utilesColegiado.getIdUsuarioSessionBigDecimal());
				addActionError("Tramite " + getTramiteTraficoBean().getNumExpediente() + "; El Justificante Profesional fue emitido hace menos de 30 días para este vehículo. "
						+ "Si quiere volver a emitir un Justificante Profesional póngase en contacto con el Colegio de Gestores Administrativos de Madrid.");
			} catch (OegamExcepcion e) {
				addActionError(e.getMessage());
			}
		}
		return ALTAS_TRAMITE_JUSTIFICANTE;
	}

//	private IntervinienteTraficoDto convertirInterviniente(IntervinienteTrafico bean, String numColegiado) {
//		IntervinienteTraficoDto dto = new IntervinienteTraficoDto();
//		dto.setNifInterviniente(bean.getPersona().getNif());
//		dto.setTipoInterviniente(TipoInterviniente.Titular.getValorEnum());
//		dto.setNumColegiado(numColegiado);
//
//		PersonaDto persona = new PersonaDto();
//		persona.setNif(bean.getPersona().getNif());
//		persona.setNumColegiado(numColegiado);
//		if (bean.getPersona().getTipoPersona() != null) {
//			persona.setTipoPersona(bean.getPersona().getTipoPersona().getValorEnum());
//		}
//		persona.setNombre(bean.getPersona().getNombre());
//		persona.setApellido1RazonSocial(bean.getPersona().getApellido1RazonSocial());
//		persona.setApellido2(bean.getPersona().getApellido2());
//		persona.setFechaNacimiento(bean.getPersona().getFechaNacimientoBean());
//		persona.setTelefonos(bean.getPersona().getTelefonos());
//
//		if (bean.getPersona().getDireccion() != null && bean.getPersona().getDireccion().getMunicipio() != null && bean.getPersona().getDireccion().getMunicipio().getProvincia() != null) {
//			DireccionDto direccion = new DireccionDto();
//			direccion.setIdProvincia(bean.getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia());
//			direccion.setIdMunicipio(bean.getPersona().getDireccion().getMunicipio().getIdMunicipio());
//			direccion.setPueblo(bean.getPersona().getDireccion().getPueblo());
//			direccion.setCodPostal(bean.getPersona().getDireccion().getCodPostal());
//			if (bean.getPersona().getDireccion().getTipoVia() != null) {
//				direccion.setIdTipoVia(bean.getPersona().getDireccion().getTipoVia().getIdTipoVia());
//			}
//			direccion.setNombreVia(bean.getPersona().getDireccion().getNombreVia());
//			direccion.setNumero(bean.getPersona().getDireccion().getNumero());
//			direccion.setLetra(bean.getPersona().getDireccion().getLetra());
//			direccion.setEscalera(bean.getPersona().getDireccion().getEscalera());
//			direccion.setPlanta(bean.getPersona().getDireccion().getPlanta());
//			direccion.setPuerta(bean.getPersona().getDireccion().getPuerta());
//			direccion.setBloque(bean.getPersona().getDireccion().getBloque());
//
//			if (bean.getPersona().getDireccion().getHm() != null && !bean.getPersona().getDireccion().getHm().isEmpty()) {
//				direccion.setHm(new BigDecimal(bean.getPersona().getDireccion().getHm()));
//			}
//
//			if (bean.getPersona().getDireccion().getPuntoKilometrico() != null && !bean.getPersona().getDireccion().getPuntoKilometrico().isEmpty()) {
//				direccion.setKm(new BigDecimal(bean.getPersona().getDireccion().getPuntoKilometrico()));
//			}
//
//			persona.setDireccionDto(direccion);
//			dto.setDireccion(direccion);
//		}
//
//		dto.setPersona(persona);
//		return dto;
//	}

//	private VehiculoDto convertirVehiculo(VehiculoBean bean, String numColegiado) {
//		VehiculoDto dto = new VehiculoDto();
//		dto.setNumColegiado(numColegiado);
//		if (bean.getTipoVehiculoBean() != null) {
//			dto.setTipoVehiculo(bean.getTipoVehiculoBean().getTipoVehiculo());
//		}
//
//		if (bean.getMarcaBean() != null && bean.getMarcaBean().getCodigoMarca() != null) {
//			dto.setCodigoMarca(bean.getMarcaBean().getCodigoMarca().toString());
//		}
//
//		dto.setModelo(bean.getModelo());
//		dto.setBastidor(bean.getBastidor());
//		dto.setMatricula(bean.getMatricula());
//		return dto;
//	}

	private void rellenarDiasValidez() {
		if (diasValidez == null || diasValidez > 30) {
			diasValidez = ConstantesTrafico.DIAS_VALIDEZ_POR_DEFECTO;
		}
	}

//	private TramiteTraficoDuplicadoBean guardarTramiteDuplicado(TramiteTraficoDuplicadoBean tramite) {
//		tramite.setIdTipoCreacion(new BigDecimal(TipoCreacion.OEGAM.getValorEnum()));
//		HashMap<String, Object> resultado = getModeloDuplicado().guardarAltaTramiteDuplicado(duplicadoTramiteTraficoBeanPQConversion.convertirTramiteDuplicadoToPQ(tramite));
//		ResultBean resultBean = (ResultBean) (resultado.get(ConstantesPQ.RESULTBEAN));
//		if (resultBean.getError() == false) {
//			addActionMessage("Justificante guardado");
//			if (resultBean.getMensaje() != null && !"".equals(resultBean.getMensaje()))
//				addActionMessage(resultBean.getMensaje());
//			return (TramiteTraficoDuplicadoBean) resultado.get(ConstantesPQ.BEANPANTALLA);
//		} else if (resultBean.getMensaje().length() > 0) {
//			addActionError("Detalle del tramite:" + resultBean.getMensaje());
//		} else {
//			log.error(" Error al guardar: ");
//			addActionError("Detalle del tramite:" + resultBean.getMensaje());
//			return null;
//		}
//		return null;
//	}

	private TramiteTraficoTransmisionBean guardarTramiteTransmision(TramiteTraficoTransmisionBean tramiteTraficoTransmisionBean) {
		tramiteTraficoTransmisionBean.setIdTipoCreacion(new BigDecimal(TipoCreacion.OEGAM.getValorEnum()));

		Map<String, Object> resultadoModelo = getModeloTransmision().guardarAltaTramiteTransmision(transmisionTramiteTraficoBeanPQConversion.convertirTramiteTransmisionToPQ(
				tramiteTraficoTransmisionBean), tramiteTraficoTransmisionBean);
		ResultBean resultBean = (ResultBean) resultadoModelo.get(ConstantesPQ.RESULTBEAN);

		log.debug("resultado = " + resultBean.getMensaje());

		if (!resultBean.getError()) {
			addActionMessage("Justificante guardado");
			return (TramiteTraficoTransmisionBean) resultadoModelo.get(ConstantesPQ.BEANPANTALLA);
		} else {
			log.error(" Error al guardar: ");
			for (String mensaje : resultBean.getListaMensajes()) {
				addActionError(mensaje);
			}
			return null;
		}
	}

	private ResultBean validacionesPrevias() throws OegamExcepcion {
		ResultBean resultado = new ResultBean(false);

		if (tipoTramiteJustificante == null) {
			resultado.setError(true);
			resultado.addMensajeALista("Debe seleccionar un tipo de Trámite.");
		}
		if (tramiteTraficoBean.getJefaturaTrafico() == null
				|| tramiteTraficoBean.getJefaturaTrafico().getJefaturaProvincial() == null
				|| tramiteTraficoBean.getJefaturaTrafico().getJefaturaProvincial().isEmpty()) {
			resultado.setError(true);
			resultado.addMensajeALista("Debe seleccionar la Jefatura Provincial.");
		}
		if (motivoJustificante == null) {
			resultado.setError(true);
			resultado.addMensajeALista("Debe seleccionar el motivo del Justificante.");
		}
		if (titular == null || titular.getPersona() == null) {
			resultado.setError(true);
			resultado.addMensajeALista("Debe rellenar los datos obligatorios del titular.");
		} else {
			if (titular.getPersona().getNif() == null || titular.getPersona().getNif().isEmpty()) {

				resultado.setError(true);
				resultado.addMensajeALista("Debe rellenar el NIF del titular.");
			}
			if (titular.getPersona().getApellido1RazonSocial() == null || titular.getPersona().getApellido1RazonSocial().isEmpty()) {
				resultado.setError(true);
				resultado.addMensajeALista("Debe rellenar el apellido o razón social del titular.");
			}
			if (titular.getPersona().getDireccion() == null) {
				resultado.setError(true);
				resultado.addMensajeALista("Debe rellenar los datos obligatorios de la dirección del titular.");
			} else {
				if (titular.getPersona().getDireccion().getMunicipio() == null) {
					resultado.setError(true);
					resultado.addMensajeALista("Debe rellenar los datos obligatorios del municipio del titular.");
				} else {
					if (titular.getPersona().getDireccion().getMunicipio().getProvincia() == null
							|| titular.getPersona().getDireccion().getMunicipio().getProvincia()
									.getIdProvincia() == null
							|| titular.getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia()
									.isEmpty()) {
						resultado.setError(true);
						resultado.addMensajeALista("Debe rellenar la provincia del titular.");
					}
					if (titular.getPersona().getDireccion().getMunicipio().getIdMunicipio() == null
							|| titular.getPersona().getDireccion().getMunicipio().getIdMunicipio().isEmpty()) {
						resultado.setError(true);
						resultado.addMensajeALista("Debe rellenar el municipio del titular.");
					}
				}
				if (titular.getPersona().getDireccion().getCodPostal() == null
						|| titular.getPersona().getDireccion().getCodPostal().isEmpty()) {
					resultado.setError(true);
					resultado.addMensajeALista("Debe rellenar el código postal de la dirección del titular.");
				}
				if (titular.getPersona().getDireccion().getTipoVia() == null) {
					resultado.setError(true);
					resultado.addMensajeALista("Debe rellenar el tipo de vía de la dirección del titular.");
				}
				if (titular.getPersona().getDireccion().getNombreVia() == null
						|| titular.getPersona().getDireccion().getNombreVia().isEmpty()) {
					resultado.setError(true);
					resultado.addMensajeALista("Debe rellenar el nombre de la vía de la dirección del titular.");
				}
				if (titular.getPersona().getDireccion().getNumero() == null
						|| titular.getPersona().getDireccion().getNumero().isEmpty()) {
					resultado.setError(true);
					resultado.addMensajeALista("Debe rellenar el número de la vía de la dirección del titular.");
				}
			}

			if (tramiteTraficoBean == null || tramiteTraficoBean.getVehiculo() == null) {
				resultado.setError(true);
				resultado.addMensajeALista("Debe rellenar los datos obligatorios del vehículo.");
			} else {
				if (tramiteTraficoBean.getVehiculo().getTipoVehiculoBean() == null
						|| tramiteTraficoBean.getVehiculo().getTipoVehiculoBean().getTipoVehiculo() == null
						|| tramiteTraficoBean.getVehiculo().getTipoVehiculoBean().getTipoVehiculo().isEmpty()) {
					resultado.setError(true);
					resultado.addMensajeALista("Debe rellenar el tipo de vehículo.");
				}
				if (tramiteTraficoBean.getVehiculo().getMarcaBean() == null
						|| tramiteTraficoBean.getVehiculo().getMarcaBean().getCodigoMarca() == null) {
					resultado.setError(true);
					resultado.addMensajeALista("Debe rellenar la marca del vehículo.");
				}
				if (tramiteTraficoBean.getVehiculo().getModelo() == null
						|| tramiteTraficoBean.getVehiculo().getModelo().isEmpty()) {
					resultado.setError(true);
					resultado.addMensajeALista("Debe rellenar el modelo del vehículo.");
				}
				if (tramiteTraficoBean.getVehiculo().getMatricula() == null
						|| tramiteTraficoBean.getVehiculo().getMatricula().isEmpty()) {
					resultado.setError(true);
					resultado.addMensajeALista("Debe rellenar la matrícula del vehículo.");
				}
				if (tramiteTraficoBean.getVehiculo().getBastidor() == null
						|| tramiteTraficoBean.getVehiculo().getBastidor().isEmpty()) {
					resultado.setError(true);
					resultado.addMensajeALista("Debe rellenar el bastidor del vehículo.");
				} else if (tramiteTraficoBean.getVehiculo().getBastidor().length() < 4) {
					resultado.setError(true);
					resultado.addMensajeALista("El Bastidor debe contener como mínimo las 4 últimás cifras.");
				}
			}
		}

//		if ("SI".equals(gestorPropiedades.valorPropertie("habilitar.justificantes.temporal"))) {
//			if (MotivoJustificante.TransferenciaCambioTitularidad.equals(motivoJustificante) || MotivoJustificante.TransferenciaUltimacion.equals(motivoJustificante)
//					|| MotivoJustificante.ReformaCambiosServicioEIncidencias.equals(motivoJustificante) || MotivoJustificante.ReformaCambiosServicioEIncidencias.equals(motivoJustificante)) {
//				if (!TipoTramiteTraficoJustificante.CAMBIO_TITULAR.equals(tipoTramiteJustificante)) {
//					resultado.setError(true);
//					resultado.addMensajeALista("Para este motivo de justificante el tipo de trámite debe ser 'CAMBIO TITULAR'.");
//				}
//			} else if (MotivoJustificante.TransferenciaNotificacionVenta.equals(motivoJustificante)) {
//				if (!TipoTramiteTraficoJustificante.NOTIFICACION_VENTA.equals(tipoTramiteJustificante)) {
//					resultado.setError(true);
//					resultado.addMensajeALista("Para este motivo de justificante el tipo de trámite debe ser 'NOTIFICACIÓN VENTA'.");
//				}
//			} else if (MotivoJustificante.DuplicadoCambioDatos.equals(motivoJustificante) || MotivoJustificante.DuplicadoDeterioro.equals(motivoJustificante)
//					|| MotivoJustificante.DuplicadoCambioDomicilio.equals(motivoJustificante)) {
//				if (!TipoTramiteTraficoJustificante.DUPLICADO.equals(tipoTramiteJustificante)) {
//					resultado.setError(true);
//					resultado.addMensajeALista("Para este motivo de justificante el tipo de trámite debe ser 'DUPLICADO'.");
//				}
//			} else if (MotivoJustificante.BajaTemporal.equals(motivoJustificante)) {
//				if (!TipoTramiteTraficoJustificante.BAJA_TEMPORAL.equals(tipoTramiteJustificante)) {
//					resultado.setError(true);
//					resultado.addMensajeALista("Para este motivo de justificante el tipo de trámite debe ser 'BAJA TEMPORAL'.");
//				}
//			}
//		} else {
		if (documentosJustificante == null) {
			resultado.setError(true);
			resultado.addMensajeALista("Debe seleccionar el tipo de documento.");
		}
//		}

		if (!resultado.getError() && tramiteTraficoBean.getTasa() == null) { // ES NECESARIO PORQUE EN ESTA PANTALLA DE ALTAS NO VA LA TASA.
			tramiteTraficoBean.setTasa(new Tasa(true));
		}
		return resultado;
	}

	/**
	 * Método que busca un interviniente en BBDD por el DNI y devuelve todos los datos del interviniente.
	 * @return
	 * @throws Throwable
	 */
	public String buscarInterviniente() throws Throwable {
		String numColegiado = (null != tramiteTraficoBean.getNumColegiado() && !"".equals(tramiteTraficoBean.getNumColegiado())) ? tramiteTraficoBean.getNumColegiado() : utilesColegiado
				.getNumColegiadoSession();
		Persona persona = ModeloPersona.obtenerDetallePersonaCompleto(getTitular().getPersona().getNif(), numColegiado);
		IntervinienteTrafico interviniente = new IntervinienteTrafico(true);
		interviniente.setPersona(persona);
		interviniente.setIdContrato(tramiteTraficoBean.getIdContrato());
		interviniente.setNumColegiado(tramiteTraficoBean.getNumColegiado());
		setTitular(interviniente);
		return ALTAS_TRAMITE_JUSTIFICANTE;
	}

	// GETTERS & SETTERS

	public String getMensajeErrorFormulario() {
		return mensajeErrorFormulario;
	}

	public void setMensajeErrorFormulario(String mensajeErrorFormulario) {
		this.mensajeErrorFormulario = mensajeErrorFormulario;
	}

	public String getTipoIntervinienteBuscar() {
		return tipoIntervinienteBuscar;
	}

	public void setTipoIntervinienteBuscar(String tipoIntervinienteBuscar) {
		this.tipoIntervinienteBuscar = tipoIntervinienteBuscar;
	}

	public TramiteTraficoBean getTramiteTraficoBean() {
		return tramiteTraficoBean;
	}

	public void setTramiteTraficoBean(TramiteTraficoBean tramiteTraficoBean) {
		this.tramiteTraficoBean = tramiteTraficoBean;
	}

	public IntervinienteTrafico getTitular() {
		return titular;
	}

	public void setTitular(IntervinienteTrafico titular) {
		this.titular = titular;
	}

	public String getTipoTasa() {
		return tipoTasa;
	}

	public void setTipoTasa(String tipoTasa) {
		this.tipoTasa = tipoTasa;
	}

	public void setTipoTramiteJustificante(String tipoTramite) {
		this.tipoTramiteJustificante = TipoTramiteTraficoJustificante.convertir(tipoTramite);
	}

	public MotivoJustificante getMotivoJustificante() {
		return motivoJustificante;
	}

	public void setMotivoJustificante(String motivoJustificante) {
		this.motivoJustificante = MotivoJustificante.convertir(motivoJustificante);
	}

	public DocumentosJustificante getDocumentosJustificante() {
		return documentosJustificante;
	}

	public void setDocumentosJustificante(String documentosJustificante) {
		this.documentosJustificante = DocumentosJustificante.convertir(documentosJustificante);
	}

	public TipoTramiteTraficoJustificante getTipoTramiteJustificante() {
		return tipoTramiteJustificante;
	}

	public Integer getDiasValidez() {
		return diasValidez;
	}

	public void setDiasValidez(Integer diasValidez) {
		this.diasValidez = diasValidez;
	}

	public boolean isCheckIdFuerzasArmadas() {
		return checkIdFuerzasArmadas;
	}

	public void setCheckIdFuerzasArmadas(boolean checkIdFuerzasArmadas) {
		this.checkIdFuerzasArmadas = checkIdFuerzasArmadas;
	}

	/* ************************************************ */
	/* MODELOS **************************************** */
	/* ************************************************ */

	public ModeloDuplicado getModeloDuplicado() {
		if (modeloDuplicado == null) {
			modeloDuplicado = new ModeloDuplicado();
		}
		return modeloDuplicado;
	}

	public void setModeloDuplicado(ModeloDuplicado modeloDuplicado) {
		this.modeloDuplicado = modeloDuplicado;
	}

	public ModeloJustificanteProfesional getModeloJustificanteProfesional() {
		if (modeloJustificanteProfesional == null) {
			modeloJustificanteProfesional = new ModeloJustificanteProfesional();
		}
		return modeloJustificanteProfesional;
	}

	public void setModeloJustificanteProfesional(ModeloJustificanteProfesional modeloJustificanteProfesional) {
		this.modeloJustificanteProfesional = modeloJustificanteProfesional;
	}

	public ModeloTransmision getModeloTransmision() {
		if (modeloTransmision == null) {
			modeloTransmision = new ModeloTransmision();
		}
		return modeloTransmision;
	}

	public void setModeloTransmision(ModeloTransmision modeloTransmision) {
		this.modeloTransmision = modeloTransmision;
	}

	public UtilesViafirma getUtilesViafirma() {
		if (utilesViafirma == null) {
			utilesViafirma = new UtilesViafirma();
		}
		return utilesViafirma;
	}
}