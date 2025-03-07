package org.gestoresmadrid.oegamComun.trafico.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTasa;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTransferencia;
import org.gestoresmadrid.core.model.enumerados.TipoVehiculoEnum;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafBajaVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafDuplicadoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafSolInfoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafTranVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.solicitudNRE06.beans.ResultadoSolicitudNRE06Bean;
import org.gestoresmadrid.oegamComun.tasa.service.ServicioComunTasa;
import org.gestoresmadrid.oegamComun.trafico.service.ServicioComunTramiteTrafico;
import org.gestoresmadrid.oegamComun.trafico.service.ServicioPersistenciaTramiteTrafico;
import org.gestoresmadrid.oegamComun.trafico.view.dto.TramiteTraficoDto;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thoughtworks.xstream.XStream;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import viafirma.utilidades.UtilesViafirma;

@Service
public class ServicioComunTramiteTraficoImpl implements ServicioComunTramiteTrafico {

	private static final long serialVersionUID = 1245998649148828958L;

	private static ILoggerOegam log = LoggerOegam.getLogger(ServicioComunTramiteTraficoImpl.class);

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	ServicioPersistenciaTramiteTrafico servicioPersistenciaTramiteTrafico;

	@Autowired
	ServicioComunTasa servicioComunTasa;

	@Autowired
	Conversor conversor;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Override
	public TramiteTraficoVO getTramite(BigDecimal numExpediente, Boolean tramiteCompleto) {
		TramiteTraficoVO tramite = null;
		try {
			if (numExpediente != null) {
				tramite = servicioPersistenciaTramiteTrafico.getTramite(numExpediente, tramiteCompleto);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el tramite: " + numExpediente, e, numExpediente.toString());
		}
		return tramite;
	}

	@Override
	public TramiteTrafBajaVO getTramiteBajaVO(BigDecimal numExpediente, Boolean tramiteCompleto) {
		try {
			return servicioPersistenciaTramiteTrafico.getTramiteBaja(numExpediente, tramiteCompleto);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el trámite de Baja ,error: ", e, numExpediente.toString());
		}
		return null;
	}

	@Override
	public TramiteTrafTranVO getTramiteTransmisionVO(BigDecimal numExpediente, Boolean tramiteCompleto) {
		try {
			return servicioPersistenciaTramiteTrafico.getTramiteTransmision(numExpediente, tramiteCompleto);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el trámite de transmision ,error: ", e, numExpediente.toString());
		}
		return null;
	}

	@Override
	public TramiteTrafDuplicadoVO getTramiteDuplicadoVO(BigDecimal numExpediente, Boolean tramiteCompleto) {
		try {
			return servicioPersistenciaTramiteTrafico.getTramiteDuplicado(numExpediente, tramiteCompleto);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el trámite de Duplicado ,error: ", e, numExpediente.toString());
		}
		return null;
	}

	@Override
	public TramiteTrafMatrVO getTramiteMatriculacionVO(BigDecimal numExpediente, Boolean tramiteCompleto) {
		try {
			return servicioPersistenciaTramiteTrafico.getTramiteMatriculacion(numExpediente, tramiteCompleto);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el trámite de Matriculación ,error: ", e, numExpediente.toString());
		}
		return null;
	}

	@Override
	public TramiteTrafSolInfoVO getTramiteSolictudVO(BigDecimal numExpediente, Boolean tramiteCompleto) {
		try {
			return servicioPersistenciaTramiteTrafico.getTramiteSolictud(numExpediente, tramiteCompleto);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el trámite de Solicitud ,error: ", e, numExpediente.toString());
		}
		return null;
	}

	@Override
	public void cambiarEstado(BigDecimal numExpediente, BigDecimal estadoNuevo, Long idUsuario) {
		try {
			servicioPersistenciaTramiteTrafico.cambiarEstado(numExpediente, estadoNuevo, idUsuario);
		} catch (Exception e) {
			log.error("Error cambiar el estado para la impresion del expediente: " + numExpediente, e, numExpediente.toString());
		}
	}

	@Override
	public void cambiarEstadoBajaDuplRelacionMatriculas(BigDecimal numExpediente, Long idUsuario) {
		try {
			TramiteTraficoVO tramite = getTramite(numExpediente, Boolean.FALSE);
			if (tramite != null && (EstadoTramiteTrafico.Finalizado_Excel.getValorEnum().equals(tramite.getEstado().toString()) || EstadoTramiteTrafico.Validado_PDF.getValorEnum().equals(tramite
					.getEstado().toString()))) {
				BigDecimal estadoAntiguo = tramite.getEstado();
				BigDecimal estadoNuevo = null;

				if (new BigDecimal(EstadoTramiteTrafico.Finalizado_Excel.getValorEnum()).equals(estadoAntiguo)) {
					estadoNuevo = new BigDecimal(EstadoTramiteTrafico.Finalizado_Excel_Impreso.getValorEnum());
				} else if (new BigDecimal(EstadoTramiteTrafico.Validado_PDF.getValorEnum()).equals(estadoAntiguo)) {
					estadoNuevo = new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum());
				}

				servicioPersistenciaTramiteTrafico.cambiarEstado(numExpediente, estadoNuevo, idUsuario);
			}
		} catch (Exception e) {
			log.error("Error cambiar el estado para la impresion del expediente: " + numExpediente, e, numExpediente.toString());
		}
	}

	@Override
	public List<Long> getListaIdsContratosFinalizadosTelematicamenteDocBase(Date fechaPresentacion) {
		try {
			String[] tiposTramites = null;
			if ("SI".equals(gestorPropiedades.valorPropertie("generar.docBase.btv"))) {
				tiposTramites = new String[] { TipoTramiteTrafico.Matriculacion.getValorEnum(), TipoTramiteTrafico.TransmisionElectronica.getValorEnum(), TipoTramiteTrafico.Baja.getValorEnum() };
			} else {
				tiposTramites = new String[] { TipoTramiteTrafico.Matriculacion.getValorEnum(), TipoTramiteTrafico.TransmisionElectronica.getValorEnum() };
			}
			List<Long> listaIdContratos = servicioPersistenciaTramiteTrafico.getListaIdsContratosFinalizadosTelematicamentePorFecha(fechaPresentacion, tiposTramites);
			if (listaIdContratos != null && !listaIdContratos.isEmpty()) {
				return listaIdContratos;
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de obtener la lista de contratos con tramites telematcios para generar su documento base, error: ", e);
		}
		return Collections.emptyList();
	}

	@Override
	public List<TramiteTraficoVO> getListaTramitesDocBaseNocturno(Long idContrato, Date fechaPresentacion, String tipoTramite) {
		try {
			return servicioPersistenciaTramiteTrafico.getListaTramitesDocBaseNocturno(idContrato, fechaPresentacion, tipoTramite);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el listado de tramites para generar el docBaseNocturno para el contrato: " + idContrato + " y el tipo tramite: " + tipoTramite
					+ ", error, e", e);
		}
		return Collections.emptyList();
	}

	@Override
	public void actualizarTramite(TramiteTraficoVO tramiteVO) {
		servicioPersistenciaTramiteTrafico.actualizarTramite(tramiteVO);
	}

	@Override
	public List<TramiteTraficoVO> getListaTramitesTraficoVO(BigDecimal[] numExpedientes, Boolean tramiteCompleto) {
		try {
			if (numExpedientes != null) {
				List<TramiteTraficoVO> listaTramitesBBDD = servicioPersistenciaTramiteTrafico.getListaTramiteTraficoVO(numExpedientes, tramiteCompleto);
				if (listaTramitesBBDD != null && !listaTramitesBBDD.isEmpty()) {
					return listaTramitesBBDD;
				}
			}
		} catch (Exception e) {
			log.error("Error al recuperar el listado de tramistes, error:  ", e);
		}
		return Collections.emptyList();
	}

	@Override
	public BigDecimal generarNumExpediente(String numColegiado) throws Exception {
		return servicioPersistenciaTramiteTrafico.generarNumExpediente(numColegiado);
	}

	@Override
	public ResultadoBean crearTramite(Long idContrato, Date fechaAlta, String tipoTramite, Long idUsuario, String numColegiado, BigDecimal tipoCreacion) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		BigDecimal numExpediente = null;
		TramiteTraficoVO tramiteTrafico = new TramiteTraficoVO();
		tramiteTrafico.setFechaAlta(fechaAlta);
		tramiteTrafico.setFechaUltModif(fechaAlta);
		tramiteTrafico.setTipoTramite(tipoTramite);
		ContratoVO contratoVO = new ContratoVO();
		contratoVO.setIdContrato(idContrato);
		tramiteTrafico.setContrato(contratoVO);
		UsuarioVO usuario = new UsuarioVO();
		usuario.setIdUsuario(idUsuario);
		tramiteTrafico.setUsuario(usuario);
		tramiteTrafico.setNumColegiado(numColegiado);
		tramiteTrafico.setEstado(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));
		tramiteTrafico.setIdTipoCreacion(tipoCreacion);
		for (int i = 1; i <= 10; i++) {
			try {
				numExpediente = servicioPersistenciaTramiteTrafico.crearTramite(tramiteTrafico);
				if (numExpediente != null) {
					resultado.setError(Boolean.FALSE);
					resultado.setNumExpediente(numExpediente);
					break;
				}
			} catch (Exception e) {
				resultado.setError(Boolean.TRUE);
				if (i == 10) {
					log.error("Ha sucedido un error a la hora de generar el numero de expediente del tramite, error: ", e);
					resultado.setMensaje("Ha sucedido un error a la hora de generar el numero de expediente.");
				}
			}
		}
		return resultado;
	}

	@Override
	public int getNumTramitePorVehiculo(BigDecimal numExpediente, Long idVehiculo) {
		return servicioPersistenciaTramiteTrafico.getNumTramitePorVehiculo(numExpediente, idVehiculo);
	}
	
	@Override
	public Integer getTotalPorColegiado(String numColegiado) {
		return servicioPersistenciaTramiteTrafico.getNumTramitePorColegiado(numColegiado);
	}

	@Override
	public ResultadoBean custodiar(TramiteTraficoVO tramiteTrafico, String alias) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			TramiteTraficoDto tramiteTraficoDto = conversor.transform(tramiteTrafico, TramiteTraficoDto.class);
			if (tramiteTraficoDto != null) {
				XStream xstream = new XStream();
				xstream.processAnnotations(TramiteTraficoDto.class);
				String xml = xstream.toXML(tramiteTraficoDto);
				byte[] Afirmar = new String(xml.getBytes("UTF-8"), "UTF-8").getBytes("UTF-8");
				FicheroBean ficheroBean = new FicheroBean();
				UtilesViafirma utilesViafirma = new UtilesViafirma();
				ficheroBean.setFicheroByte(utilesViafirma.firmarXMLTrafico(Afirmar, alias));
				ficheroBean.setTipoDocumento(ConstantesGestorFicheros.NO_TELEMATICO);
				ficheroBean.setSubTipo(TipoTramiteTrafico.convertirTexto(tramiteTrafico.getTipoTramite()));
				ficheroBean.setNombreDocumento(tramiteTrafico.getNumExpediente().toString());
				ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
				ficheroBean.setFecha(Utilidades.transformExpedienteFecha(tramiteTrafico.getNumExpediente()));
				ficheroBean.setSobreescribir(true);
				gestorDocumentos.guardarFichero(ficheroBean);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se han encontrado datos para poder generar el XML de presentacion no telematica.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de custodiar el XML_NO_TELEMATICO del expediente: " + tramiteTrafico.getNumExpediente() + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de custodiar el XML_NO_TELEMATICO del expediente: " + tramiteTrafico.getNumExpediente());
		}
		return resultado;
	}

	@Override
	public ResultadoBean cambiarEstadoRevisado(BigDecimal numExpediente, Long idUsuario) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			TramiteTraficoVO tramite = getTramite(numExpediente, Boolean.FALSE);
			if (tramite != null) {
				resultado = validarCambioEstadoRevisado(tramite);
				if (resultado != null && !resultado.getError()) {
					BigDecimal estadoNuevo = new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum());
					if (new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum()).equals(tramite.getEstado())) {
						estadoNuevo = new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum());
					}
					servicioPersistenciaTramiteTrafico.cambiarEstado(numExpediente, estadoNuevo, idUsuario);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Trámite no encontrato.");
			}
		} catch (Exception e) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error a la hora de cambiar el estado del trámite en el método revisdado.");
			log.error("Error a la hora de cambiar el estado del trámite en el método revisdado", e);
		}

		return resultado;
	}

	@Override
	public void actualizarTasa(BigDecimal numExpediente, String codigoTasa) {
		TramiteTraficoVO tramite = getTramite(numExpediente, Boolean.FALSE);
		if (tramite != null) {
			TasaVO tasa = new TasaVO();
			tasa.setCodigoTasa(codigoTasa);
			tramite.setTasa(tasa);
			actualizarTramite(tramite);
		}
	}

	private ResultadoBean validarCambioEstadoRevisado(TramiteTraficoVO tramite) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		if (!TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tramite.getTipoTramite()) && !TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tramite.getTipoTramite())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Sólo puede realizar el cambio para 'Matriculaciones' y 'Transmisiones'.");
		} else if (!new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum()).equals(tramite.getEstado()) && !new BigDecimal(
				EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum()).equals(tramite.getEstado())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Sólo puede realizar el cambio en trámites con estado 'Finalizados Telemáticamente' y 'Finalizados Telemáticamente Revisado'.");
		}
		return resultado;
	}

	@Override
	public ResultadoBean asignarTasaLibre(BigDecimal numExpediente, Long idUsuario) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			TramiteTraficoVO tramite = getTramite(numExpediente, Boolean.TRUE);
			if (tramite != null) {
				resultado = validarTasaLibre(tramite);
				if (resultado != null && !resultado.getError()) {
					String tipoTasa = obtenerTipoTasa(tramite.getTipoTramite(), tramite.getVehiculo(), tramite.getNumExpediente());
					if (StringUtils.isNotBlank(tipoTasa)) {
						resultado = servicioComunTasa.asignarTasaLibre(numExpediente, tramite.getTipoTramite(), tipoTasa, tramite.getContrato().getIdContrato(), idUsuario);
						if (resultado != null && !resultado.getError()) {
							try {
								actualizarTasa(numExpediente, resultado.getCodigoTasa());
							} catch (Exception e) {
								log.error("Error a la hora de guardar la tasa en el expediente, se procede a desasignar", e);
								resultado.setError(Boolean.TRUE);
								resultado.setMensaje("Error a la hora de asignar la tasa libre al expediente.");
								servicioComunTasa.desasignarTasaExpediente(resultado.getCodigoTasa(), numExpediente, tramite.getContrato().getIdContrato(), tipoTasa, idUsuario);
							}
						}
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("Ya tiene asociada una tasa.");
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Trámite no encontrato.");
			}
		} catch (Exception e) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error a la hora de asignar la tasa libre al expediente.");
			log.error("Error a la hora de asignar la tasa libre al expediente", e);
		}

		return resultado;
	}

	private ResultadoBean validarTasaLibre(TramiteTraficoVO tramite) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		if (!TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tramite.getTipoTramite()) && !TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tramite.getTipoTramite())
				&& !TipoTramiteTrafico.Baja.getValorEnum().equals(tramite.getTipoTramite()) && !TipoTramiteTrafico.Duplicado.getValorEnum().equals(tramite.getTipoTramite())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se pueden asignar tasas masivas a este tipo de trámites.");
		} else if (new BigDecimal(EstadoTramiteTrafico.Anulado.getValorEnum()).equals(tramite.getEstado())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede asignar una tasa a un trámite en estado 'Anulado'.");
		} else if (tramite.getTasa() != null && tramite.getTasa().getCodigoTasa() != null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ya tiene asociada una tasa.");
		} else if (tramite.getVehiculo() == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Sin vehículo.");
		} else if (StringUtils.isBlank(tramite.getVehiculo().getTipoVehiculo())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Se necesita rellenar el tipo de vehículo.");
		}
		return resultado;
	}

	private String obtenerTipoTasa(String tipoTramite, VehiculoVO vehiculo, BigDecimal numExpediente) {
		String tipoTasa = null;
		if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tipoTramite)) {
			if (!TipoVehiculoEnum.CICLOMOTOR_DE_2_RUEDAS.getValorEnum().equals(vehiculo.getTipoVehiculo()) && !TipoVehiculoEnum.CICLOMOTOR_DE_3_RUEDAS.getValorEnum().equals(vehiculo.getTipoVehiculo())
					&& !TipoVehiculoEnum.CUADRICICLO_LIGERO.getValorEnum().equals(vehiculo.getTipoVehiculo())) {
				tipoTasa = TipoTasa.UnoUno.getValorEnum();
			} else {
				tipoTasa = TipoTasa.UnoDos.getValorEnum();
			}
		} else if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tipoTramite)) {
			TramiteTrafTranVO tramiteTran = getTramiteTransmisionVO(numExpediente, Boolean.TRUE);
			if (TipoTransferencia.tipo1.getValorEnum().equals(tramiteTran.getTipoTransferencia()) || TipoTransferencia.tipo2.getValorEnum().equals(tramiteTran.getTipoTransferencia())
					|| TipoTransferencia.tipo3.getValorEnum().equals(tramiteTran.getTipoTransferencia())) {
				if (!TipoVehiculoEnum.CICLOMOTOR_DE_2_RUEDAS.getValorEnum().equals(vehiculo.getTipoVehiculo()) && !TipoVehiculoEnum.CICLOMOTOR_DE_3_RUEDAS.getValorEnum().equals(vehiculo
						.getTipoVehiculo()) && !TipoVehiculoEnum.CUADRICICLO_LIGERO.getValorEnum().equals(vehiculo.getTipoVehiculo())) {
					tipoTasa = TipoTasa.UnoCinco.getValorEnum();
				} else {
					tipoTasa = TipoTasa.UnoDos.getValorEnum();
				}
			} else if (TipoTransferencia.tipo4.getValorEnum().equals(tramiteTran.getTipoTransferencia()) || TipoTransferencia.tipo5.getValorEnum().equals(tramiteTran.getTipoTransferencia())) {
				tipoTasa = TipoTasa.CuatroUno.getValorEnum();
			}
		} else if (TipoTramiteTrafico.Baja.getValorEnum().equals(tipoTramite)) {
			tipoTasa = TipoTasa.CuatroUno.getValorEnum();
		} else if (TipoTramiteTrafico.Duplicado.getValorEnum().equals(tipoTramite)) {
			tipoTasa = TipoTasa.CuatroCuatro.getValorEnum();
		}
		return tipoTasa;
	}

	@Override
	public ResultadoSolicitudNRE06Bean guardarNRE(TramiteTraficoVO tramiteTraficoBBDD,String nre,Date fechaRegistro) {
		ResultadoSolicitudNRE06Bean resultGuardar = new ResultadoSolicitudNRE06Bean(Boolean.FALSE);
		try {
			servicioPersistenciaTramiteTrafico.guardarNRE(tramiteTraficoBBDD.getNumExpediente(),nre,fechaRegistro);
		} catch (Exception e) {
			resultGuardar.setError(Boolean.TRUE);
			resultGuardar.setMensaje("Error a la hora de guardar el NRE.");
			log.error("Error a la hora de guardar el NRE", e);
		}
		return resultGuardar;
		
	}

	@Override
	public List<Object[]> getTramiteNRE() {
		List<Object[]> tramite = null;
		try {
			tramite = servicioPersistenciaTramiteTrafico.getTramitePorNRE();
		} catch (Exception e) {
			log.error("Error al recuperar el listado de tramites");
		}
		return tramite;
	}

	@Override
	public ResultadoBean generarDocImprNocturno(List<BigDecimal> listaExpedientesImpr, Long docId, String sDocImpr,
			Long idUsuario, String tipoImpr, Boolean esEntornoAm, Long idContrato) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			resultado = servicioPersistenciaTramiteTrafico.generarSolDocImprNocturno(listaExpedientesImpr, docId, sDocImpr, idUsuario, tipoImpr, idContrato, esEntornoAm);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar tratar los IMPR para su solicitud, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar tratar los IMPR para su solicitud.");
		}
		return resultado;
	}

	@Override
	public void actualizarFechaDiaHoy(BigDecimal numExpediente, Long idUsuario) {
		 servicioPersistenciaTramiteTrafico.actualizarFechaDiaHoy(numExpediente,idUsuario);
	}

	@Override
	public ResultadoBean desasignarTasaTramiteTrafico(BigDecimal numExpediente, Long idUsuario) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		resultado =  servicioPersistenciaTramiteTrafico.desasignarTasaTramiteTrafico(numExpediente,idUsuario);
		if(resultado.getError()) {
			resultado.setMensaje(resultado.getMensaje());
		}
		 return resultado;
		
	}
	
}
