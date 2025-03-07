package org.gestoresmadrid.oegamBajas.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.enumerados.ProcesosEnum;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.MotivoBaja;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTasa;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoBajaDao;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoDao;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoProcedureDao;
import org.gestoresmadrid.core.trafico.model.procedure.bean.ValidacionTramite;
import org.gestoresmadrid.core.trafico.model.vo.EvolucionTramiteTraficoPK;
import org.gestoresmadrid.core.trafico.model.vo.EvolucionTramiteTraficoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafBajaVO;
import org.gestoresmadrid.oegam2comun.presentacion.jpt.enumerados.JefaturasJPTEnum;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioTasa;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoTransmision;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafBajaDto;
import org.gestoresmadrid.oegamBajas.service.ServicioPersistenciaBaja;
import org.gestoresmadrid.oegamBajas.service.ServicioValidacionBajas;
import org.gestoresmadrid.oegamBajas.view.bean.ResultadoBajasBean;
import org.gestoresmadrid.oegamComun.credito.service.ServicioComunCredito;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioValidacionBajasImpl implements ServicioValidacionBajas{

	private static final long serialVersionUID = 2530845513081467667L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioValidacionBajasImpl.class);

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	ServicioTramiteTraficoTransmision servicioTramiteTraficoTransmision;

	@Autowired
	ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	ServicioPersistenciaBaja servicioPersistenciaBaja;

	@Autowired
	ServicioTasa servicioTasa;

	@Autowired
	TramiteTraficoProcedureDao tramiteTraficoProcedureDao;

	@Autowired
	TramiteTraficoDao tramiteDao;

	@Autowired
	TramiteTraficoBajaDao traficoBajaDao;

	@Autowired
	ServicioComunCredito servicioComunCredito;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Override
	public ResultadoBajasBean validarPendienteEnvioExcel(TramiteTrafBajaVO tramiteTrafBajaVO, BigDecimal numExpediente) {
		ResultadoBajasBean resultado = new ResultadoBajasBean(Boolean.FALSE);
		try {
			if (tramiteTrafBajaVO != null) {
				validarDatosEnvioExcel(tramiteTrafBajaVO, resultado);
				if (!resultado.getError()) {
					ResultadoBean resultCredito = servicioComunCredito.creditosDisponiblesComprobandoPendientes(new Long(1),
							tramiteTrafBajaVO.getContrato().getIdContrato(), ProcesosEnum.BTV.getNombreEnum(), TipoTramiteTrafico.Baja.getValorEnum());
					if (resultCredito.getError()) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("No tiene creditos suficientes para poder realizar el envio a excel.");
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos para el expediente: " + numExpediente);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el tramite de baja: " + numExpediente + " para enviarlo a excel, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de validar el tramite de baja: " + numExpediente + " para enviarlo a excel.");
		}
		return resultado;
	}

	private void validarDatosEnvioExcel(TramiteTrafBajaVO tramiteTrafBajaVO, ResultadoBajasBean resultado) {
		if(!EstadoTramiteTrafico.Validado_PDF.getValorEnum().equals(tramiteTrafBajaVO.getEstado().toString())){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El tramite: " + tramiteTrafBajaVO.getNumExpediente() +" debe de estar en estado Validado PDF para poder enviarlo a excel.");
		} else if(MotivoBaja.TempS.getValorEnum().equals(tramiteTrafBajaVO.getMotivoBaja()) 
				|| MotivoBaja.TempFCA.getValorEnum().equals(tramiteTrafBajaVO.getMotivoBaja()) 
				|| MotivoBaja.TempT.getValorEnum().equals(tramiteTrafBajaVO.getMotivoBaja()) 
				|| MotivoBaja.DefRP.getValorEnum().equals(tramiteTrafBajaVO.getMotivoBaja()) 
				|| MotivoBaja.DefTC.getValorEnum().equals(tramiteTrafBajaVO.getMotivoBaja()) 
				|| MotivoBaja.DefE.getValorEnum().equals(tramiteTrafBajaVO.getMotivoBaja())){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El tramite: " + tramiteTrafBajaVO.getNumExpediente() +" no tiene un motivo valido para enviarlo a excel.");
		} else if (!JefaturasJPTEnum.MADRID.getJefatura().equals(tramiteTrafBajaVO.getJefaturaTrafico().getJefaturaProvincial())
				&& !JefaturasJPTEnum.ALCORCON.getJefatura().equals(tramiteTrafBajaVO.getJefaturaTrafico().getJefaturaProvincial())
				&& !JefaturasJPTEnum.ALCALADEHENARES.getJefatura().equals(tramiteTrafBajaVO.getJefaturaTrafico().getJefaturaProvincial())) {
			if (JefaturasJPTEnum.AVILA.getJefatura().equals(tramiteTrafBajaVO.getJefaturaTrafico().getJefaturaProvincial())) {
				if (!"SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaAvila"))) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("El proceso de envio de Bajas por excel no esta habilitado en este momento para la jefatura del expediente: " + tramiteTrafBajaVO.getNumExpediente());
				}
			} else if (JefaturasJPTEnum.CIUDADREAL.getJefatura().equals(tramiteTrafBajaVO.getJefaturaTrafico().getJefaturaProvincial())) {
				if (!"SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaCiudadReal"))) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("El proceso de envio de Bajas por excel no esta habilitado en este momento para la jefatura del expediente: " + tramiteTrafBajaVO.getNumExpediente());
				}
			} else if (JefaturasJPTEnum.CUENCA.getJefatura().equals(tramiteTrafBajaVO.getJefaturaTrafico().getJefaturaProvincial())) {
				if (!"SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaCuenca"))) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("El proceso de envio de Bajas por excel no esta habilitado en este momento para la jefatura del expediente: " + tramiteTrafBajaVO.getNumExpediente());
				}
			} else if (JefaturasJPTEnum.GUADALAJARA.getJefatura().equals(tramiteTrafBajaVO.getJefaturaTrafico().getJefaturaProvincial())) {
				if (!"SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaGuadalajara"))) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("El proceso de envio de Bajas por excel no esta habilitado en este momento para la jefatura del expediente: " + tramiteTrafBajaVO.getNumExpediente());
				}
			} else if (JefaturasJPTEnum.SEGOVIA.getJefatura().equals(tramiteTrafBajaVO.getJefaturaTrafico().getJefaturaProvincial())) {
				if (!"SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaSegovia"))) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("El proceso de envio de Bajas por excel no esta habilitado en este momento para la jefatura del expediente: " + tramiteTrafBajaVO.getNumExpediente());
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("El proceso de envio de Bajas por excel no esta habilitado en este momento para la jefatura del expediente: " + tramiteTrafBajaVO.getNumExpediente());
			}
		}
	}

	@Override
	public ResultadoBajasBean validarTramitabilidadTramite(TramiteTrafBajaVO tramiteTrafBajaVO, BigDecimal numExpediente) {
		ResultadoBajasBean resultado = new ResultadoBajasBean(Boolean.FALSE);
		try {
			if(tramiteTrafBajaVO != null){
				validarDatosTramitabilidad(tramiteTrafBajaVO, resultado);
				if (!resultado.getError()) {
					ResultadoBean resultCredito = servicioComunCredito.creditosDisponiblesComprobandoPendientes(1L,
							tramiteTrafBajaVO.getContrato().getIdContrato(), ProcesosEnum.BTV.getNombreEnum(), TipoTramiteTrafico.Baja.getValorEnum());
					if (resultCredito.getError()) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("No tiene creditos suficientes para poder realizar la tramitación telematica.");
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos para el expediente: " + numExpediente);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar la tramitabilidad del tramite de baja: " + numExpediente + ", error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de comprobar la tramitabilidad del tramite de baja: " + numExpediente);
		}
		return resultado;
	}

	private void validarDatosTramitabilidad(TramiteTrafBajaVO tramiteTrafBajaVO, ResultadoBajasBean resultado) {
		if(!EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum().equals(tramiteTrafBajaVO.getEstado().toString())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El tramite: " + tramiteTrafBajaVO.getNumExpediente() +" debe de estar en estado Validado Telematicamente para poder tramitarlo telematicamente.");
		} else if (!MotivoBaja.DefE.getValorEnum().equals(tramiteTrafBajaVO.getMotivoBaja())
				&& !MotivoBaja.DefTC.getValorEnum().equals(tramiteTrafBajaVO.getMotivoBaja())
				&& !MotivoBaja.TempV.getValorEnum().equals(tramiteTrafBajaVO.getMotivoBaja())
				&& !MotivoBaja.TempR.getValorEnum().equals(tramiteTrafBajaVO.getMotivoBaja())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Solo se pueden tramitar telemáticamente las bajas definitivas de exportación,transito comunitario,temporal voluntaria y renovación baja temporal.");
		}
	}

	@Override
	public ResultadoBajasBean comprobarLocalmenteTramitabilidad(TramiteTrafBajaVO tramiteTrafBajaVO, BigDecimal numExpediente) {
		ResultadoBajasBean resultado = new ResultadoBajasBean(Boolean.FALSE);
		try {
			comprobarDatosMinimosCheckBtv(tramiteTrafBajaVO, numExpediente, resultado);
			if (!resultado.getError()) {
				comprobarCheckFichaPermiso(tramiteTrafBajaVO, resultado);
				if (!resultado.getError()) {
					comprobarLocalmente(tramiteTrafBajaVO, resultado);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar localmente la tramitabilidad del tramite de baja: " + numExpediente + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de comprobar localmente la tramitabilidad del tramite de baja.: " + numExpediente);
		}
		return resultado;
	}

	private void comprobarLocalmente(TramiteTrafBajaVO tramiteTrafBajaVO, ResultadoBajasBean resultado) {
		if (tramiteTrafBajaVO.getTasa() != null
				&& StringUtils.isNotBlank(tramiteTrafBajaVO.getTasa().getCodigoTasa())) {
			if (!vehiculoMayorDe15Anios(utilesFecha.getFechaConDate(tramiteTrafBajaVO.getVehiculo().getFechaMatriculacion()))) {
				if (!TipoTasa.CuatroUno.getValorEnum().equals(tramiteTrafBajaVO.getTasa().getTipoTasa())) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("La tasa con la que se intenta tramitar la baja no es valida para la tramitación telemática, por favor seleccione la 4.1.");
				} else if(vehiculoMayorDe15Anios(utilesFecha.getFechaConDate(tramiteTrafBajaVO.getVehiculo().getFechaMatriculacion()))){
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Bajas por " + MotivoBaja.convertirTexto(tramiteTrafBajaVO.getMotivoBaja()) + " y vehiculo >=15 años no es necesaria tasa .");
				}
			}
		}
		if (!resultado.getError()) {
			if (tramiteTrafBajaVO.getVehiculo().getDireccion() != null
					&& ("51".equals(tramiteTrafBajaVO.getVehiculo().getDireccion().getIdProvincia())
							|| "52".equals(tramiteTrafBajaVO.getVehiculo().getDireccion().getIdProvincia()))) {
				String prov = "";
				if ("51".equals(tramiteTrafBajaVO.getVehiculo().getDireccion().getIdProvincia())) {
					prov = "Ceuta";
				} else if ("52".equals(tramiteTrafBajaVO.getVehiculo().getDireccion().getIdProvincia())) {
					prov = "Melilla";
				}
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("El vehículo con domicilio en la provincia de " + prov
						+ " no se puede tramitar telemáticamente.");
			}
		}
		if (!resultado.getError()) {
			for (IntervinienteTraficoVO intervinienteTraficoVO : tramiteTrafBajaVO.getIntervinienteTraficosAsList()) {
				if (TipoInterviniente.Titular.getValorEnum()
						.equals(intervinienteTraficoVO.getId().getTipoInterviniente())) {
					if (intervinienteTraficoVO.getDireccion() != null
							&& ("51".equals(intervinienteTraficoVO.getDireccion().getIdProvincia())
									|| "52".equals(intervinienteTraficoVO.getDireccion().getIdProvincia()))) {
						String prov = "";
						if ("51".equals(intervinienteTraficoVO.getDireccion().getIdProvincia())) {
							prov = "Ceuta";
						} else if ("52".equals(intervinienteTraficoVO.getDireccion().getIdProvincia())) {
							prov = "Melilla";
						}
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("El titular con domicilio en la provincia de " + prov
								+ " no se puede tramitar telemáticamente.");
					}
					break;
				}
			}
		}
	}

	private void comprobarCheckFichaPermiso(TramiteTrafBajaVO tramiteTrafBajaVO, ResultadoBajasBean resultado) {
		if ((Boolean.FALSE.equals(tramiteTrafBajaVO.getTarjetaInspeccion()) 
				|| Boolean.FALSE.equals(tramiteTrafBajaVO.getPermisoCircu())) 
				&& Boolean.FALSE.equals(tramiteTrafBajaVO.getDeclaracionJuradaExtravio())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Para este motivo de baja debe ir seleccionado los checks de permiso de circulación y tarjeta de inspección técnica, o en su defecto la declaración jurada");
		}
	}

	private void comprobarDatosMinimosCheckBtv(TramiteTrafBajaVO tramiteTrafBajaVO, BigDecimal numExpediente, ResultadoBajasBean resultado) {
		if (tramiteTrafBajaVO == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se han encontrado datos para el expediente: " + numExpediente);
		} else if (!EstadoTramiteTrafico.Iniciado.getValorEnum().equals(tramiteTrafBajaVO.getEstado().toString())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El tramite tiene que estar en estado Iniciado para poder realizar el CheckBTV.");
		} else if (StringUtils.isBlank(tramiteTrafBajaVO.getMotivoBaja())
				|| (!MotivoBaja.DefE.getValorEnum().equals(tramiteTrafBajaVO.getMotivoBaja())
						&& !MotivoBaja.DefTC.getValorEnum().equals(tramiteTrafBajaVO.getMotivoBaja()))) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("La baja no se puede comprobar porque no es del motivo adecuado, debe validarla directamente.");
		} else if(tramiteTrafBajaVO.getVehiculo() == null){
			resultado.setError(true);
			resultado.setMensaje("Debe rellenar los datos del vehiculo.");
		} else if (tramiteTrafBajaVO.getVehiculo().getFechaMatriculacion() == null) {
			resultado.setError(true);
			resultado.setMensaje("Debe rellenar la fecha de matriculación del vehiculo para poder realizar el checkBTV.");
			resultado.setMensaje("Debe rellenar la fecha de matriculación del vehiculo para poder realizar el checkBTV.");
		} else if (MotivoBaja.DefE.getValorEnum().equals(tramiteTrafBajaVO.getMotivoBaja())) {
			if (tramiteTrafBajaVO.getVehiculo().getFechaMatriculacion() == null) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Si es baja exportación es obligatorio informar la fecha primera matriculación.");
			}else {
				Date fechaMas4 = utilesFecha.sumaRestaAnios(tramiteTrafBajaVO.getVehiculo().getFechaMatriculacion(), 4);
				Date fechaItv = tramiteTrafBajaVO.getVehiculo().getFechaItv();
				Date fechaActual = new Date();
				if(fechaActual.after(fechaMas4) || tramiteTrafBajaVO.getVehiculo().getEsSiniestro().booleanValue()) {
					if(null == fechaItv || fechaItv.before(fechaActual)) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("Los vehículos de más de cuatro años de antiguedad desde la fecha de matriculación o siniestro total, deben tener la ITV en vigor.");
					}
				}
			}
		} else if (tramiteTrafBajaVO.getTasa() == null || StringUtils.isBlank(tramiteTrafBajaVO.getTasa().getCodigoTasa())) {
			if (!vehiculoMayorDe15Anios(utilesFecha.getFechaConDate(tramiteTrafBajaVO.getVehiculo().getFechaMatriculacion()))) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe seleccionar una Tasa para poder realizar el checkBTV.");
			}
		} else if (StringUtils.isBlank(tramiteTrafBajaVO.getVehiculo().getMatricula())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe rellenar la matricula del vehiculo para poder realizar el checkBTV.");
		} else if (StringUtils.isBlank(tramiteTrafBajaVO.getVehiculo().getBastidor())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe rellenar el bastidor del vehiculo para poder realizar el checkBTV.");
		} else if (tramiteTrafBajaVO.getPais() == null || tramiteTrafBajaVO.getPais().getIdPais() == null) {
			resultado.setError(true);
			resultado.setMensaje("Debe rellenar el pais de la baja para poder realizar el checkBTV.");
		} else if(tramiteTrafBajaVO.getIntervinienteTraficosAsList() == null || tramiteTrafBajaVO.getIntervinienteTraficosAsList().isEmpty()){
			resultado.setError(true);
			resultado.setMensaje("Debe rellenar el titular de la baja para poder realizar el checkBTV.");
		} else {
			Boolean existeTitular = Boolean.FALSE;
			for (IntervinienteTraficoVO intervinienteTraficoVO : tramiteTrafBajaVO.getIntervinienteTraficosAsList()) {
				if (TipoInterviniente.Titular.getValorEnum()
						.equals(intervinienteTraficoVO.getId().getTipoInterviniente())) {
					existeTitular = Boolean.TRUE;
					break;
				}
			}
			if (!existeTitular) {
				resultado.setError(true);
				resultado.setMensaje("Debe rellenar el titular de la baja para poder realizar el checkBTV.");
			}
		}
	}

	@Override
	public ResultadoBajasBean validacionesBloqueantesGuardado(TramiteTrafBajaDto tramiteTraficoBaja) {
		ResultadoBajasBean resultado = new ResultadoBajasBean(Boolean.FALSE);
		try {
			log.info("Inicio validacion bloqueante guardado tramite bajas.");
			if(tramiteTraficoBaja != null){
				resultado = validarFechasBaja(tramiteTraficoBaja);
				if(!resultado.getError()){
					resultado = procesarMotivoBajas(tramiteTraficoBaja);
					if(!resultado.getError()) {
						resultado = validarJefatura(tramiteTraficoBaja);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.addListaMensajeError("Debe de rellenar algún dato para poder dar de alta un tramite de baja.");
			}
			log.info("Fin validacion bloqueante guardado  tramite bajas.");
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar los datos bloqueantes antes de guardar el tramite de baja, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de validar los datos bloqueantes antes de guardar el tramite de baja.");
		}
		return resultado;
	}

	private ResultadoBajasBean validarJefatura(TramiteTrafBajaDto tramiteTraficoBaja) {
		ResultadoBajasBean resultado = new ResultadoBajasBean(Boolean.FALSE);
		if (tramiteTraficoBaja.getJefaturaTraficoDto().getJefaturaProvincial() != null
				&& "-1".equals(tramiteTraficoBaja.getJefaturaTraficoDto().getJefaturaProvincial())) {
			resultado.setError(Boolean.TRUE);
			resultado.addListaMensajeError("Debe indicar una Jefatura Provincial para guardar el trámite");
		}
		return resultado;
	}

	private ResultadoBajasBean procesarMotivoBajas(TramiteTrafBajaDto tramiteTraficoBaja) {
		ResultadoBajasBean resultado = new ResultadoBajasBean(Boolean.FALSE);
		Boolean hayTasa = Boolean.FALSE;
		if (tramiteTraficoBaja.getTasa() != null && StringUtils.isNotBlank(tramiteTraficoBaja.getTasa().getCodigoTasa()) 
				&& !"-1".equals(tramiteTraficoBaja.getTasa().getCodigoTasa())) {
			hayTasa = Boolean.TRUE;
		}
		if (MotivoBaja.DefE.getValorEnum().equals(tramiteTraficoBaja.getMotivoBaja())) {
			if (tramiteTraficoBaja.getDeclaracionResiduo() != null && !tramiteTraficoBaja.getDeclaracionResiduo()) {
				resultado.setError(Boolean.TRUE);
				resultado.addListaMensajeError("Bajas por exportación es necesario declaración de NO es residuo sólido");
			} else if (vehiculoMayorDe15Anios(tramiteTraficoBaja.getVehiculoDto().getFechaMatriculacion()) && hayTasa) {
				resultado.addListaMensajeAvisos("Bajas por " + MotivoBaja.convertirTexto(tramiteTraficoBaja.getMotivoBaja()) + 
						" y vehiculo >=15 años no es necesaria tasa .");
				tramiteTraficoBaja.setTasa(null);
			} else if (tramiteTraficoBaja.getPais() == null || tramiteTraficoBaja.getPais().getIdPais() == null || tramiteTraficoBaja.getPais().getIdPais() == -1) {
				resultado.setError(Boolean.TRUE);
				resultado.addListaMensajeError("Bajas por " + MotivoBaja.convertirTexto(tramiteTraficoBaja.getMotivoBaja()) + 
							" es necesario seleccionar pais de la baja");
			}
		} else if (MotivoBaja.DefCT.getValorEnum().equals(tramiteTraficoBaja.getMotivoBaja())) {
			resultado.setError(Boolean.TRUE);
			resultado.addListaMensajeError("El procedimiento de Bajas definitivas por carta de la DGT ya no está operativo. Ese tipo de bajas habrá que solicitarlas como BAJAS DEFINITIVAS VOLUNTARIAS");
		} else if(MotivoBaja.DefV.getValorEnum().equals(tramiteTraficoBaja.getMotivoBaja())){
			if (tramiteTraficoBaja.getCertificadoMedioAmbiental() != null && tramiteTraficoBaja.getCertificadoMedioAmbiental() && hayTasa) {
				resultado.addListaMensajeAvisos("Bajas por " + MotivoBaja.convertirTexto(tramiteTraficoBaja.getMotivoBaja()) + 
						" no es necesaria tasa si aporta Certificado Medioambiental.");
				tramiteTraficoBaja.setTasa(null);
			} else if (vehiculoMayorDe15Anios(tramiteTraficoBaja.getVehiculoDto().getFechaMatriculacion()) && hayTasa) {
				resultado.addListaMensajeAvisos("Bajas por " + MotivoBaja.convertirTexto(tramiteTraficoBaja.getMotivoBaja()) + " no es necesaria tasa si el vehículo tiene más de 15 años.");
				tramiteTraficoBaja.setTasa(null);
			}
		} else if(MotivoBaja.DefTC.getValorEnum().equals(tramiteTraficoBaja.getMotivoBaja())){
			if (tramiteTraficoBaja.getPais() == null || tramiteTraficoBaja.getPais().getIdPais() == null || tramiteTraficoBaja.getPais().getIdPais() == -1) {
				resultado.setError(Boolean.TRUE);
				resultado.addListaMensajeError("Bajas por " + MotivoBaja.convertirTexto(tramiteTraficoBaja.getMotivoBaja()) + 
						" es necesario seleccionar pais de la baja");
			} else if (vehiculoMayorDe15Anios(tramiteTraficoBaja.getVehiculoDto().getFechaMatriculacion()) && hayTasa) {
				resultado.addListaMensajeAvisos("Bajas por " + MotivoBaja.convertirTexto(tramiteTraficoBaja.getMotivoBaja()) + 
						" y vehiculo >=15 años no es necesaria tasa .");
				tramiteTraficoBaja.setTasa(null);
			}
		} else if (MotivoBaja.TranCTIT.getValorEnum().equals(tramiteTraficoBaja.getMotivoBaja()) 
				|| MotivoBaja.ExpCTIT.getValorEnum().equals(tramiteTraficoBaja.getMotivoBaja())) {
			Boolean resultadoComprobar = servicioTramiteTraficoTransmision.sitexComprobarCTITPrevio(tramiteTraficoBaja.getVehiculoDto().getMatricula());
			if (!resultadoComprobar) {
				resultado.setError(Boolean.TRUE);
				resultado.addListaMensajeError("Bajas de exportación y tránsito posteriores a CTIT, requieren de un trámite de transmisión electrónica previo.");
			} else if (tramiteTraficoBaja.getTasaDuplicado() != null && !"".equals(tramiteTraficoBaja.getTasaDuplicado())) {
				tramiteTraficoBaja.setTasaDuplicado(null);
				resultado.addListaMensajeAvisos("Procede  de un cambio de titularidad telemático y por ello no requiere de una tasa de duplicado para expedir el permiso de circulación");
			}
		} else if (MotivoBaja.TempT.getValorEnum().equals(tramiteTraficoBaja.getMotivoBaja())) {
			resultado.setError(Boolean.TRUE);
			resultado.addListaMensajeError("Este tipo de trámite se debe realizar a través del servicio CTIT - Entrega CompraVenta");
		} else if ("-1".equals(tramiteTraficoBaja.getMotivoBaja())) {
			tramiteTraficoBaja.setMotivoBaja(null);
		}
		return resultado;
	}
	
	@Override
	public Boolean vehiculoMayorDe15Anios(Fecha fechaMatriculacion) {
		try {
			Boolean esMayor = Boolean.FALSE;
			Calendar fechaVehiculo = new GregorianCalendar((new Integer(fechaMatriculacion.getAnio()) + 15),
					(new Integer(fechaMatriculacion.getMes()) - 1), (new Integer(fechaMatriculacion.getDia())));
			Calendar fechaHoy = new GregorianCalendar();
			esMayor = fechaVehiculo.after(fechaHoy) ? Boolean.FALSE : Boolean.TRUE;
			return esMayor;
		} catch (Exception e) {
			return false;
		}
	}

	private ResultadoBajasBean validarFechasBaja(TramiteTrafBajaDto tramiteTraficoBaja) {
		ResultadoBajasBean resultado = new ResultadoBajasBean(Boolean.FALSE);
		if (tramiteTraficoBaja.getVehiculoDto() != null && tramiteTraficoBaja.getVehiculoDto().getFechaMatriculacion() != null
				&& !tramiteTraficoBaja.getVehiculoDto().getFechaMatriculacion().isfechaNula()) {
			if (!utilesFecha.validarFormatoFecha(tramiteTraficoBaja.getVehiculoDto().getFechaMatriculacion())) {
				resultado.setError(Boolean.TRUE);
				resultado.addListaMensajeError("La fecha de matriculación del vehículo que ha introducido no es valida");
			}
		}
		if (tramiteTraficoBaja.getTitular() != null && tramiteTraficoBaja.getTitular().getPersona() != null
				&& tramiteTraficoBaja.getTitular().getPersona().getFechaNacimiento() != null
				&& !tramiteTraficoBaja.getTitular().getPersona().getFechaNacimiento().isfechaNula()) {
			if (!utilesFecha.validarFormatoFecha(tramiteTraficoBaja.getTitular().getPersona().getFechaNacimiento())) {
				resultado.setError(Boolean.TRUE);
				resultado
						.addListaMensajeError("La fecha de nacimiento que ha introducido para el titular no es valida");
			}
		}
		if (tramiteTraficoBaja.getRepresentanteTitular() != null
				&& tramiteTraficoBaja.getRepresentanteTitular().getPersona() != null) {
			if (tramiteTraficoBaja.getRepresentanteTitular().getFechaInicio() != null
					&& !tramiteTraficoBaja.getRepresentanteTitular().getFechaInicio().isfechaNula()) {
				if (!utilesFecha.validarFormatoFecha(tramiteTraficoBaja.getRepresentanteTitular().getFechaInicio())) {
					resultado.setError(Boolean.TRUE);
					resultado.addListaMensajeError("La fecha de inicio de tutela que ha introducido para el representante del titular no es valida");
				}
			}
			if (tramiteTraficoBaja.getRepresentanteTitular().getFechaFin() != null
					&& !tramiteTraficoBaja.getRepresentanteTitular().getFechaFin().isfechaNula()) {
				if (!utilesFecha.validarFormatoFecha(tramiteTraficoBaja.getRepresentanteTitular().getFechaFin())) {
					resultado.setError(Boolean.TRUE);
					resultado.addListaMensajeError("La fecha de fin de tutela que ha introducido para el representante del titular no es valida");
				}
			}
		}
		if (tramiteTraficoBaja.getAdquiriente() != null && tramiteTraficoBaja.getAdquiriente().getPersona() != null
			&& tramiteTraficoBaja.getAdquiriente().getPersona().getFechaNacimiento() != null
				&& !tramiteTraficoBaja.getAdquiriente().getPersona().getFechaNacimiento().isfechaNula()) {
			if (!utilesFecha
					.validarFormatoFecha(tramiteTraficoBaja.getAdquiriente().getPersona().getFechaNacimiento())) {
				resultado.setError(Boolean.TRUE);
				resultado.addListaMensajeError("La fecha de nacimiento que ha introducido para el adquiriente no es valida");
			}
		}
		if (tramiteTraficoBaja.getRepresentanteAdquiriente() != null
				&& tramiteTraficoBaja.getRepresentanteAdquiriente().getPersona() != null) {
			if (tramiteTraficoBaja.getRepresentanteAdquiriente().getFechaInicio() != null
					&& !tramiteTraficoBaja.getRepresentanteAdquiriente().getFechaInicio().isfechaNula()) {
				if (!utilesFecha.validarFormatoFecha(tramiteTraficoBaja.getRepresentanteAdquiriente().getFechaInicio())) {
					resultado.setError(Boolean.TRUE);
					resultado.addListaMensajeError("La fecha de inicio de tutela que ha introducido para el representante del adquiriente no es valida");
				}
			}
			if (tramiteTraficoBaja.getRepresentanteAdquiriente().getFechaFin() != null
					&& !tramiteTraficoBaja.getRepresentanteAdquiriente().getFechaFin().isfechaNula() && !utilesFecha
							.validarFormatoFecha(tramiteTraficoBaja.getRepresentanteAdquiriente().getFechaFin())) {
				resultado.setError(Boolean.TRUE);
				resultado.addListaMensajeError(
						"La fecha de fin de tutela que ha introducido para el representante del adquiriente no es valida");
			}
		}
		return resultado;
	}

	@Override
	public ResultadoBajasBean validarFechaMatriculacion(TramiteTrafBajaVO tramiteTrafBajaVO) {
		ResultadoBajasBean resultado = new ResultadoBajasBean(Boolean.FALSE);
		try {
			if (tramiteTrafBajaVO.getVehiculo() != null && tramiteTrafBajaVO.getVehiculo().getFechaMatriculacion() != null) {
				int resCom;
				resCom = utilesFecha.compararFechaMayor(utilesFecha.getFechaConDate(tramiteTrafBajaVO.getVehiculo().getFechaMatriculacion()), utilesFecha.getFechaActual());
				if (resCom == 1) {
					// Si la fecha de matriculación es superior a la decha de presentación mostramos el aviso
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("La fecha de matriculación del vehículo no puede ser posterior a la fecha del día de hoy.");
				} else if (resCom == -1 && tramiteTrafBajaVO.getFechaPresentacion() != null) {
					resCom = utilesFecha.compararFechaMayor(utilesFecha.getFechaConDate(tramiteTrafBajaVO.getVehiculo().getFechaPrimMatri()), utilesFecha.getFechaConDate(tramiteTrafBajaVO
							.getFechaPresentacion()));
					if (resCom == 1) {
						// Si la fecha de matriculación es superior a la decha de presentación mostramos el aviso
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("La fecha de matriculación del vehículo no puede ser posterior a la fecha de presentación del tramite.");
					}
				}
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de validar la fecha de matriculacion del vehiculo del tramite de baja: " + tramiteTrafBajaVO.getNumExpediente() + ", error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de validar la fecha de matriculacion del vehiculo.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoBajasBean validarTramiteBtvPQ(TramiteTrafBajaVO tramiteTrafBajaVO,BigDecimal idUsuario) {
		ResultadoBajasBean resultado = new ResultadoBajasBean(Boolean.FALSE);
		try {
			ValidacionTramite validado = tramiteTraficoProcedureDao.validarTramiteTelematicamenteBaja(tramiteTrafBajaVO);
			if (validado == null || !"Correcto".equals(validado.getSqlerrm())) {
				resultado.setError(Boolean.TRUE);
				if(StringUtils.isNotBlank(validado.getSqlerrm())){
					resultado.setMensaje(validado.getSqlerrm());
				} else {
					resultado.setMensaje("Ha sucedido un error a la hora de validar el tramite de baja.");
				}
			} else {
				resultado.setEstadoNuevo(validado.getEstado());
				try {
					String estadoAntiguo = tramiteTrafBajaVO.getEstado().toString();
					tramiteTrafBajaVO.setEstado(new BigDecimal(resultado.getEstadoNuevo()));
					EvolucionTramiteTraficoVO evolucionTramiteTraficoVO = rellenarEvolucionTramite(tramiteTrafBajaVO, estadoAntiguo, idUsuario);
					servicioPersistenciaBaja.guardarActualizarTramiteConEvo(tramiteTrafBajaVO, evolucionTramiteTraficoVO);
				} catch (Exception e) {
					log.error("Error al actualizar el estado del trámite de baja: " + tramiteTrafBajaVO.getNumExpediente(), e, tramiteTrafBajaVO.getNumExpediente().toString());
					resultado.setMensaje("Error al actualizar el estado del trámite de baja: " + tramiteTrafBajaVO.getNumExpediente());
					resultado.setError(Boolean.TRUE);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el tramite de baja BTV, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de validar el tramite de baja.");
		}
		return resultado;
	}

	private EvolucionTramiteTraficoVO rellenarEvolucionTramite(TramiteTrafBajaVO tramiteTrafBajaVO, String estadoAntiguo, BigDecimal idUsuario) {
		EvolucionTramiteTraficoVO evolucionTramiteTraficoVO = new EvolucionTramiteTraficoVO();
		EvolucionTramiteTraficoPK id = new EvolucionTramiteTraficoPK();

		id.setEstadoAnterior(estadoAntiguo == null ? BigDecimal.ZERO : new BigDecimal(estadoAntiguo));

		id.setEstadoNuevo(tramiteTrafBajaVO.getEstado());
		id.setNumExpediente(tramiteTrafBajaVO.getNumExpediente());
		evolucionTramiteTraficoVO.setId(id);
		UsuarioVO usuarioVO = new UsuarioVO();
		usuarioVO.setIdUsuario(idUsuario.longValue());
		evolucionTramiteTraficoVO.setUsuario(usuarioVO);
		return evolucionTramiteTraficoVO;
	}

	@Override
	@Transactional
	public ResultadoBajasBean validarTramitePQ(TramiteTrafBajaVO tramiteTrafBajaVO) {
		ResultadoBajasBean resultado = new ResultadoBajasBean(Boolean.FALSE);
		try {
			ValidacionTramite validado = tramiteTraficoProcedureDao.validarTramiteBaja(tramiteTrafBajaVO);
			if (validado == null || !"Correcto".equals(validado.getSqlerrm())) {
				resultado.setError(Boolean.TRUE);
				if(StringUtils.isNotBlank(validado.getSqlerrm())){
					resultado.setMensaje(validado.getSqlerrm());
				} else {
					resultado.setMensaje("Ha sucedido un error a la hora de validar el tramite de baja.");
				}
			} else {
				resultado.setEstadoNuevo(validado.getEstado());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el tramite de baja, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de validar el tramite de baja.");
		}
		return resultado;
	}

}