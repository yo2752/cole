package org.gestoresmadrid.oegamImportacion.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.direccion.model.vo.TipoViaVO;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.MotivoBaja;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTasa;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTransferencia;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;
import org.gestoresmadrid.core.trafico.inteve.model.vo.TramiteTraficoInteveVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafBajaVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafDuplicadoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafTranVO;
import org.gestoresmadrid.core.trafico.solInfo.model.enumerados.TipoInforme;
import org.gestoresmadrid.core.vehiculo.model.vo.MarcaDgtVO;
import org.gestoresmadrid.core.vehiculo.model.vo.MarcaFabricanteVO;
import org.gestoresmadrid.core.vehiculo.model.vo.TipoVehiculoVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoValidacionImporBean;
import org.gestoresmadrid.oegamImportacion.cet.bean.AutoliquidacionBean;
import org.gestoresmadrid.oegamImportacion.direccion.service.ServicioDireccionImportacion;
import org.gestoresmadrid.oegamImportacion.direccion.service.ServicioPaisImportacion;
import org.gestoresmadrid.oegamImportacion.service.ServicioValidacionesImportacion;
import org.gestoresmadrid.oegamImportacion.tasa.service.ServicioTasaImportacion;
import org.gestoresmadrid.oegamImportacion.trafico.service.ServicioTramiteTraficoImportacion;
import org.gestoresmadrid.oegamImportacion.utiles.UtilidadesNIFValidatorImportacion;
import org.gestoresmadrid.oegamImportacion.vehiculo.service.ServicioCaracteristicasElectricasImportacion;
import org.gestoresmadrid.oegamImportacion.vehiculo.service.ServicioMarcaDgtImportacion;
import org.gestoresmadrid.oegamImportacion.vehiculo.service.ServicioVehiculoImportacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioValidacionesImportacionImpl implements ServicioValidacionesImportacion {

	private static final long serialVersionUID = 4553212100462581384L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioValidacionesImportacionImpl.class);

	@Autowired
	ServicioDireccionImportacion servicioDireccion;

	@Autowired
	ServicioVehiculoImportacion servicioVehiculo;

	@Autowired
	ServicioTasaImportacion servicioTasa;

	@Autowired
	ServicioPaisImportacion servicioPais;

	@Autowired
	ServicioMarcaDgtImportacion servicioMarcaDgt;

	@Autowired
	ServicioCaracteristicasElectricasImportacion servicioCaracteristicasElectricas;

	@Autowired
	ServicioTramiteTraficoImportacion servicioTramiteTraficoImportacion;

	@Autowired
	UtilidadesNIFValidatorImportacion utilidadesNIFValidator;

	@Override
	public ResultadoValidacionImporBean validarInterviniente(IntervinienteTraficoVO intervinienteTraficoVO, String tipoTramite) {
		ResultadoValidacionImporBean resultado = new ResultadoValidacionImporBean(Boolean.FALSE);
		try {
			int tipo = utilidadesNIFValidator.isValidDniNieCif(intervinienteTraficoVO.getId().getNif().toUpperCase());
			if (tipo < 0) {
				resultado.addListaMensajeError("El DNI / NIE / NIF del " + TipoInterviniente.convertirTexto(intervinienteTraficoVO.getId().getTipoInterviniente()) + " no es válido.");
				resultado.setErrorInterviniente(Boolean.TRUE);
				return resultado;
			}
			if (intervinienteTraficoVO.getDireccion() != null) {
				ResultadoValidacionImporBean resultValDir = procesarDireccion(intervinienteTraficoVO.getDireccion(), tipoTramite);
				if (resultValDir.getError()) {
					ResultadoValidacionImporBean resultObtener = servicioDireccion.buscarPersonaDireccion(intervinienteTraficoVO.getId().getNumColegiado(), intervinienteTraficoVO.getId().getNif());
					if (resultObtener != null && resultObtener.getDireccion() != null) {
						intervinienteTraficoVO.setDireccion(resultObtener.getDireccion());
						resultado.addListaMensajeAvisos("- No se ha guardado la dirección del " + TipoInterviniente.convertirTexto(intervinienteTraficoVO.getId().getTipoInterviniente())
								+ " se asocia la dirección principal.");
					} else {
						resultado.addListaMensajeAvisos("- La dirección del " + TipoInterviniente.convertirTexto(intervinienteTraficoVO.getId().getTipoInterviniente()) + ": " + resultValDir
								.getMensaje());
						intervinienteTraficoVO.setDireccion(null);
						resultado.setErrorDireccion(Boolean.TRUE);
						intervinienteTraficoVO.setDireccion(null);
					}
				} else if (resultValDir.getListaMensajeError() != null && !resultValDir.getListaMensajeError().isEmpty()) {
					ResultadoValidacionImporBean resultObtener = servicioDireccion.buscarPersonaDireccion(intervinienteTraficoVO.getId().getNumColegiado(), intervinienteTraficoVO.getId().getNif());
					if (resultObtener != null && resultObtener.getDireccion() != null) {
						intervinienteTraficoVO.setDireccion(resultObtener.getDireccion());
						resultado.addListaMensajeAvisos("- Dirección inválida del " + TipoInterviniente.convertirTexto(intervinienteTraficoVO.getId().getTipoInterviniente())
								+ " se asocia la dirección principal por los siguientes motivos: " + getMensaje(resultValDir.getListaMensajeError()));
					} else {
						resultado.addListaMensajeAvisos("- La dirección del " + TipoInterviniente.convertirTexto(intervinienteTraficoVO.getId().getTipoInterviniente())
								+ " no se guardará por los siguientes motivos: " + getMensaje(resultValDir.getListaMensajeError()));
						resultado.setErrorDireccion(Boolean.TRUE);
						intervinienteTraficoVO.setDireccion(null);
					}
				} else if (resultValDir.getListaMensajesAvisos() != null && !resultValDir.getListaMensajesAvisos().isEmpty()) {
					resultado.addListaMensajeAvisos("- La dirección del " + TipoInterviniente.convertirTexto(intervinienteTraficoVO.getId().getTipoInterviniente())
							+ " se guardara sin los siguientes campos: " + getMensaje(resultValDir.getListaMensajesAvisos()));
				}
			} else {
				ResultadoValidacionImporBean resultDirActiva = servicioDireccion.buscarPersonaDireccion(intervinienteTraficoVO.getId().getNumColegiado(), intervinienteTraficoVO.getId().getNif());
				if (!resultDirActiva.getError() && resultDirActiva.getDireccion() != null && !TipoInterviniente.RepresentanteArrendatario.getValorEnum().equals(intervinienteTraficoVO.getId().getTipoInterviniente())) {
					intervinienteTraficoVO.setDireccion(resultDirActiva.getDireccion());
					resultado.addListaMensajeAvisos("- " + TipoInterviniente.convertirTexto(intervinienteTraficoVO.getId().getTipoInterviniente())
							+ ": sin dirección, se asocia la dirección principal.");
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el " + TipoInterviniente.convertirTexto(intervinienteTraficoVO.getTipoIntervinienteVO().getTipoInterviniente()) + ", error: ", e);
			resultado.addListaMensajeError("Ha sucedido un error a la hora de validar el " + TipoInterviniente.convertirTexto(intervinienteTraficoVO.getId().getTipoInterviniente()));
			resultado.setErrorInterviniente(Boolean.TRUE);
		}
		return resultado;
	}

	@Override
	public ResultadoValidacionImporBean validarTramiteBaja(TramiteTrafBajaVO tramiteBajaVO) {
		ResultadoValidacionImporBean resultado = new ResultadoValidacionImporBean(Boolean.FALSE);
		try {
			if (tramiteBajaVO.getMotivoBaja() != null && !tramiteBajaVO.getMotivoBaja().isEmpty()
					&& !MotivoBaja.esMotivoBajaCorrecto(tramiteBajaVO.getMotivoBaja())) {
				resultado.addListaMensajeError("- El motivo de la baja no se guardara al no ser un motivo válido.");
				resultado.setError(Boolean.TRUE);
			}
			if (tramiteBajaVO.getTasa() != null && tramiteBajaVO.getTasa().getCodigoTasa() != null && !tramiteBajaVO.getTasa().getCodigoTasa().isEmpty()) {
				TasaVO tasaCorrecta = servicioTasa.esTasaValida(tramiteBajaVO.getTasa().getCodigoTasa(), TipoTasa.CuatroUno.getValorEnum());
				if (tasaCorrecta == null) {
					resultado.addListaMensajeAvisos("- La tasa no se guardará al estar aplicada o no existir.");
					tramiteBajaVO.setTasa(null);
				} else {
					tramiteBajaVO.setTasa(tasaCorrecta);
				}
			}
			if (tramiteBajaVO.getTasaDuplicado() != null && !tramiteBajaVO.getTasaDuplicado().isEmpty()) {
				TasaVO tasaCorrecta = servicioTasa.esTasaValida(tramiteBajaVO.getTasa().getCodigoTasa(), TipoTasa.CuatroCuatro.getValorEnum());
				if (tasaCorrecta == null) {
					resultado.addListaMensajeAvisos("- La tasa de duplicado no se guardará al estar aplicada o no existir.");
					tramiteBajaVO.setTasaDuplicado(null);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el trámite de baja, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de validar el trámite de baja.");
		}
		return resultado;
	}

	@Override
	public ResultadoValidacionImporBean validarTramiteCtit(TramiteTrafTranVO tramiteCtitVO) {
		ResultadoValidacionImporBean resultado = new ResultadoValidacionImporBean(Boolean.FALSE);
		try {
			if (tramiteCtitVO.getTasa() != null && tramiteCtitVO.getTasa().getCodigoTasa() != null && !tramiteCtitVO.getTasa().getCodigoTasa().isEmpty()) {
				TasaVO tasaCorrecta = servicioTasa.esTasaValida(tramiteCtitVO.getTasa().getCodigoTasa(), tramiteCtitVO.getTasa().getTipoTasa());
				if (tasaCorrecta == null) {
					resultado.addListaMensajeAvisos("- La tasa no se guardará al estar aplicada o no existir.");
					tramiteCtitVO.setTasa(null);
				} else {
					tramiteCtitVO.setTasa(tasaCorrecta);
				}
			}

			if (tramiteCtitVO.getTasa1() != null && tramiteCtitVO.getTasa1().getCodigoTasa() != null && !tramiteCtitVO.getTasa1().getCodigoTasa().isEmpty()) {
				TasaVO tasaCorrecta = servicioTasa.esTasaValida(tramiteCtitVO.getTasa1().getCodigoTasa(), TipoTasa.CuatroUno.getValorEnum());
				if (tasaCorrecta == null) {
					resultado.addListaMensajeAvisos("- La tasa de informe no se guardará al estar aplicada o no existir.");
					tramiteCtitVO.setTasa1(null);
				} else {
					tramiteCtitVO.setTasa1(tasaCorrecta);
				}
			}

			if (tramiteCtitVO.getTasa2() != null && tramiteCtitVO.getTasa2().getCodigoTasa() != null && !tramiteCtitVO.getTasa2().getCodigoTasa().isEmpty()) {
				TasaVO tasaCorrecta = servicioTasa.esTasaValida(tramiteCtitVO.getTasa2().getCodigoTasa(), TipoTasa.CuatroUno.getValorEnum());
				if (tasaCorrecta == null) {
					resultado.addListaMensajeAvisos("- La tasa de cambio no se guardará al estar aplicada o no existir.");
					tramiteCtitVO.setTasa2(null);
				} else {
					tramiteCtitVO.setTasa2(tasaCorrecta);
				}
			}

			if ((tramiteCtitVO.getCetItp() != null && !tramiteCtitVO.getCetItp().isEmpty()) && (tramiteCtitVO.getFactura() != null && !tramiteCtitVO.getFactura().isEmpty())) {
				resultado.addListaMensajeAvisos("- No se ha importado el CET porque ya se ha introducido el número de factura.");
				tramiteCtitVO.setCetItp("");
			}

			if (tramiteCtitVO.getValorReal() != null) {
				int longitudCorte = 0;
				BigDecimal valor = tramiteCtitVO.getValorReal();
				if (valor.toString().indexOf(".") != -1) {
					longitudCorte = valor.toString().indexOf(".");
				} else if (valor.toString().indexOf(",") != -1) {
					longitudCorte = valor.toString().indexOf(",");
				}
				if (longitudCorte != 0 && valor.toString().substring(longitudCorte).length() > 3) {
					resultado.addListaMensajeAvisos("- No se puede introducir m\u00E1s de dos decimales en el valor real, se ha añadido s\u00F3lo con dos decimales.");
					valor = new BigDecimal(valor.toString().substring(0, longitudCorte + 3));
				}
				tramiteCtitVO.setValorReal(valor);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el trámite de ctit, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de validar el trámite de ctit.");
		}
		return resultado;
	}

	@Override
	public ResultadoValidacionImporBean validarTramiteMatw(TramiteTrafMatrVO tramiteMatwVO) {
		ResultadoValidacionImporBean resultado = new ResultadoValidacionImporBean(Boolean.FALSE);
		try {
			if (tramiteMatwVO.getTasa() != null && tramiteMatwVO.getTasa().getCodigoTasa() != null && !tramiteMatwVO.getTasa().getCodigoTasa().isEmpty()) {
				TasaVO tasaCorrecta = servicioTasa.esTasaValida(tramiteMatwVO.getTasa().getCodigoTasa(), tramiteMatwVO.getTasa().getTipoTasa());
				if (tasaCorrecta == null) {
					resultado.addListaMensajeAvisos("- La tasa no se guardará al estar aplicada o no existir.");
					tramiteMatwVO.setTasa(null);
				} else {
					tramiteMatwVO.setTasa(tasaCorrecta);
				}
			}

			if (StringUtils.isNotBlank(tramiteMatwVO.getCem()) && "SI".equals(tramiteMatwVO.getExentoCem())) {
				resultado.setMensaje("El trámite no se ha guardado. No se puede indicar CEM y Exento_CEM a la vez. Por favor modifique uno.");
				resultado.setError(Boolean.TRUE);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el trámite de ctit, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de validar el trámite de ctit.");
		}
		return resultado;
	}

	@Override
	public ResultadoValidacionImporBean validarTramiteDuplicado(TramiteTrafDuplicadoVO tramiteDuplicadoVO) {
		ResultadoValidacionImporBean resultado = new ResultadoValidacionImporBean(Boolean.FALSE);
		try {
			if (tramiteDuplicadoVO.getTasa() != null && tramiteDuplicadoVO.getTasa().getCodigoTasa() != null && !tramiteDuplicadoVO.getTasa().getCodigoTasa().isEmpty()) {
				TasaVO tasaCorrecta = servicioTasa.esTasaValida(tramiteDuplicadoVO.getTasa().getCodigoTasa(), tramiteDuplicadoVO.getTasa().getTipoTasa());
				if (tasaCorrecta == null) {
					resultado.addListaMensajeAvisos("- La tasa no se guardará al estar aplicada o no existir.");
					tramiteDuplicadoVO.setTasa(null);
				} else {
					tramiteDuplicadoVO.setTasa(tasaCorrecta);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el trámite de duplicado, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de validar el trámite de duplicado.");
		}
		return resultado;
	}

	@Override
	public ResultadoValidacionImporBean validarAutoliquidacion(AutoliquidacionBean autoliquidacion) {
		ResultadoValidacionImporBean resultado = new ResultadoValidacionImporBean(Boolean.FALSE);
		try {
			if (StringUtils.isBlank(autoliquidacion.getCet())) {
				resultado.setMensaje("Informe el CET");
				resultado.setError(Boolean.TRUE);
			} else if (StringUtils.isBlank(autoliquidacion.getMatricula())) {
				resultado.setMensaje("Informe la matrícula");
				resultado.setError(Boolean.TRUE);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar cet, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de validar cet.");
		}
		return resultado;
	}

	@Override
	public ResultadoValidacionImporBean validarTramiteSolicitud(TramiteTraficoInteveVO tramiteSolicitudVO) {
		ResultadoValidacionImporBean resultado = new ResultadoValidacionImporBean(Boolean.FALSE);
		try {
			if (tramiteSolicitudVO.getTasa() != null && tramiteSolicitudVO.getTasa().getCodigoTasa() != null && !tramiteSolicitudVO.getTasa().getCodigoTasa().isEmpty()) {
				TasaVO tasaCorrecta = servicioTasa.esTasaValida(tramiteSolicitudVO.getTasa().getCodigoTasa(), TipoTasa.CuatroUno.getValorEnum());
				if (tasaCorrecta == null) {
					String identificador = "";
					if (tramiteSolicitudVO.getTramitesInteveAsList().get(0).getMatricula() != null) {
						identificador = tramiteSolicitudVO.getTramitesInteveAsList().get(0).getMatricula();
					} else if (tramiteSolicitudVO.getTramitesInteveAsList().get(0).getBastidor() != null){
						identificador = tramiteSolicitudVO.getTramitesInteveAsList().get(0).getBastidor();
					} else if (tramiteSolicitudVO.getTramitesInteveAsList().get(0).getNive() != null){
						identificador = tramiteSolicitudVO.getTramitesInteveAsList().get(0).getNive();
					}
					resultado.addListaMensajeAvisos("- La tasa de la solicitud " + identificador + " no se guardará al estar aplicada o no existir.");
					tramiteSolicitudVO.setTasa(null);
				} else {
					tramiteSolicitudVO.setTasa(tasaCorrecta);
				}
			}
			if (tramiteSolicitudVO.getTramitesInteveAsList().get(0).getTipoInforme() != null && !tramiteSolicitudVO.getTramitesInteveAsList().get(0).getTipoInforme().isEmpty()) {
				if (!TipoInforme.validarTipoInforme(tramiteSolicitudVO.getTramitesInteveAsList().get(0).getTipoInforme())) {
					resultado.addListaMensajeError("No contiene un tipo de informe válido para poder realizar la solicitud.");
					resultado.setError(Boolean.TRUE);
				}
			} else {
				resultado.addListaMensajeError("No contiene un tipo de informe válido de la solicitud.");
				resultado.setError(Boolean.TRUE);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el trámite de solicitud, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de validar el trámite de solicitud.");
		}
		return resultado;
	}

	@Override
	public ResultadoValidacionImporBean validarVehiculo(VehiculoVO vehiculo, String tipoTramite) {
		ResultadoValidacionImporBean resultado = new ResultadoValidacionImporBean(Boolean.FALSE);
		try {
			if (StringUtils.isNotBlank(vehiculo.getMatricula()) || (StringUtils.isNotBlank(vehiculo.getBastidor()) && vehiculo.getBastidor().length() == 17) || (StringUtils.isNotBlank(vehiculo
					.getBastidor()) && vehiculo.getBastidor().length() >= 3 && vehiculo.getFichaTecnicaRd750())) {
				if (vehiculo.getDireccion() != null) {
					ResultadoValidacionImporBean resultValDir = procesarDireccion(vehiculo.getDireccion(), tipoTramite);
					if (resultValDir.getError()) {
						resultado.addListaMensajeError("La dirección del vehículo: " + resultValDir.getMensaje() + ". ");
						resultado.setError(Boolean.TRUE);
						resultado.setErrorDireccion(Boolean.TRUE);
					} else if (resultValDir.getListaMensajeError() != null && !resultValDir.getListaMensajeError().isEmpty()) {
						resultado.addListaMensajeError("La dirección del vehículo no se guardará por los siguientes motivos: " + getMensaje(resultValDir.getListaMensajeError())+ ". ");
						resultado.setError(Boolean.TRUE);
						resultado.setErrorDireccion(Boolean.TRUE);
					} else if (resultValDir.getListaMensajesAvisos() != null && !resultValDir.getListaMensajesAvisos().isEmpty()) {
						resultado.addListaMensajeAvisos("La dirección del vehículo se guardará sin los siguientes campos: " + getMensaje(resultValDir.getListaMensajesAvisos()) + ". ");
					}
				}

				if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tipoTramite)) {
					if (StringUtils.isNotBlank(vehiculo.getBastidor()) && StringUtils.isNotBlank(vehiculo.getNumColegiado()) &&
						servicioVehiculo.isBastidorDuplicado(vehiculo.getBastidor(), vehiculo.getNumColegiado())) {
							resultado.addListaMensajeAvisos("El bastidor " + vehiculo.getBastidor() + " está duplicado para su número de colegiado. ");
					}

					if (StringUtils.isNotBlank(vehiculo.getIdMotivoItv()) && vehiculo.getFechaItv() != null
							&& "E".equals(vehiculo.getIdMotivoItv())) {
						vehiculo.setFechaItv(null);
						resultado.addListaMensajeAvisos("Para los vehículos que están exentos de realizar la Inspección ITV no se debe rellenar la Fecha caducidad ITV. ");
					}

					if (vehiculo.getModelo() != null && !vehiculo.getModelo().isEmpty() && vehiculo.getModelo().length() > 80) {
						vehiculo.setModelo(vehiculo.getModelo().substring(0, 79));
						resultado.addListaMensajeAvisos("El modelo del vehículo se ha acortado a 80 caracteres, si desea modificarlo puede hacerlo desde el propio trámite. ");
					}

					resultado = validarMultifasicos(vehiculo, resultado);
					if (resultado != null && !resultado.getError()) {
						resultado = validarElectricos(vehiculo, resultado);
					}
				}

				validarTipoVehiculo (vehiculo, resultado);

			} else {
				resultado.setError(Boolean.TRUE);
				resultado.addListaMensajeError("- El vehículo no tiene matrícula ni número de bastidor válido, no se puede guardar. ");
				resultado.setErrorVehiculo(Boolean.TRUE);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el vehículo, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.addListaMensajeError("Ha sucedido un error a la hora de validar el vehículo.");
			resultado.setErrorVehiculo(Boolean.TRUE);
		}
		return resultado;
	}

	@Override
	public ResultadoValidacionImporBean validarPosibleDuplicado(String bastidor, String nif, String tipoTramite, String numColegiado) {
		ResultadoValidacionImporBean resultado = new ResultadoValidacionImporBean(Boolean.FALSE);
		try {
			boolean posiblesDuplicados = servicioTramiteTraficoImportacion.validarPosibleDuplicado(bastidor, nif, tipoTramite, numColegiado);
			if (posiblesDuplicados) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Existe un trámite con el mismo NIF y el mismo bastidor en un estado 'No Finalizado'.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar posible duplicado, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de validar posible duplicado.");
		}
		return resultado;
	}

	@Override
	public ResultadoValidacionImporBean validarPosibleDuplicadoExcel(String matricula, String nif, String tipoTramite, String numColegiado) {
		ResultadoValidacionImporBean resultado = new ResultadoValidacionImporBean(Boolean.FALSE);
		try {
			boolean posiblesDuplicados = servicioTramiteTraficoImportacion.validarPosibleDuplicadoExcel(matricula, nif, tipoTramite, numColegiado);
			if (posiblesDuplicados) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Existe un trámite con el mismo NIF y el mismo bastidor en un estado 'No Finalizado'.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar posible duplicado, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de validar posible duplicado.");
		}
		return resultado;
	}

	@Override
	public ResultadoValidacionImporBean validarPosibleDuplicadoCtit(String bastidor, String matricula, String nif, String tipoTramite, String numColegiado, String tipoTransferencia) {
		ResultadoValidacionImporBean resultado = new ResultadoValidacionImporBean(Boolean.FALSE);
		try {
			boolean posiblesDuplicados = servicioTramiteTraficoImportacion.validarPosibleDuplicadoCtit(bastidor, matricula, nif, tipoTramite, numColegiado, tipoTransferencia);
			if (posiblesDuplicados) {
				resultado.setError(Boolean.TRUE);
				if (TipoTransferencia.tipo5.getValorEnum().equals(tipoTransferencia)) {
					resultado.setMensaje("Existe un trámite con el mismo transmitente, el mismo bastidor y el mismo tipo de transferencia en un estado 'No Finalizado'.");
				} else {
					resultado.setMensaje("Existe un trámite con el mismo adquiriente, el mismo bastidor y el mismo tipo de transferencia en un estado 'No Finalizado'.");
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar posible duplicado, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de validar posible duplicado.");
		}
		return resultado;
	}

	private String getMensaje(List<String> listaMensaje) {
		String mensaje = "";
		if (listaMensaje != null && !listaMensaje.isEmpty()) {
			for (String mensajeLista : listaMensaje) {
				mensaje += mensajeLista;
			}
		}
		return mensaje;
	}

	private ResultadoValidacionImporBean procesarDireccion(DireccionVO direccion, String tipoTramite) {
		ResultadoValidacionImporBean resultadoVal = new ResultadoValidacionImporBean(Boolean.FALSE);
		try {
			if (direccion.getIdProvincia() != null && !direccion.getIdProvincia().isEmpty()) {
				Boolean provinciaOK = servicioDireccion.codigoProvinciaCorrecta(direccion.getIdProvincia());
				if (!provinciaOK) {
					resultadoVal.addListaMensajeError("La direccion no se importara porque la provincia no es correcta. ");
					resultadoVal.setError(Boolean.TRUE);
				}
			} else {
				resultadoVal.addListaMensajeError("La direccion no se importa porque la provincia es obligatoria. ");
				resultadoVal.setError(Boolean.TRUE);
			}
			if (resultadoVal.getError()) {
				return resultadoVal;
			}
			if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tipoTramite)) {
				if (direccion.getIdTipoVia() != null && !direccion.getIdTipoVia().isEmpty()) {
					TipoViaVO tipoViaVO = servicioDireccion.getTipoViaPorIdDgt(direccion.getIdTipoVia());
					if (tipoViaVO == null) {
						resultadoVal.addListaMensajeError("- No existe el tipo de vía. ");
					} else {
						direccion.setIdTipoVia(tipoViaVO.getIdTipoVia());
					}
				} else {
					resultadoVal.addListaMensajeError("- El tipo de via es obligatorio. ");
				}

				if (direccion.getIdMunicipio() == null || direccion.getIdMunicipio().isEmpty()) {
					resultadoVal.addListaMensajeError("- El municipio es obligatorio o no es válido para la DGT. ");
				}

				String codigoPostal = "";
				if (direccion.getIdMunicipio() != null && !direccion.getIdMunicipio().isEmpty()) {
					codigoPostal = servicioDireccion.obtenerCodigoPostalDGT(new BigDecimal(direccion.getIdMunicipio()), direccion.getIdProvincia());
				}
				if (direccion.getCodPostalCorreos() == null || direccion.getCodPostalCorreos().isEmpty()) {
					if (codigoPostal == null || codigoPostal.isEmpty()) {
						resultadoVal.addListaMensajeError("- El código postal es obligatorio. ");
					} else {
						direccion.setCodPostalCorreos(codigoPostal);
					}
				} else {
					if (!direccion.getCodPostalCorreos().contains(codigoPostal)) {
						resultadoVal.addListaMensajeError("- El código postal no es correcto por lo que no se guardara la dirección. ");
					}
				}

				if (direccion.getPuebloCorreos() != null && !direccion.getPuebloCorreos().isEmpty()) {
					Boolean esPuebloOK = servicioDireccion.esPuebloCorrecto(direccion.getIdProvincia(), direccion.getIdMunicipio(), direccion.getPuebloCorreos());
					if (!esPuebloOK) {
						resultadoVal.addListaMensajeAvisos("- El pueblo no es del callejero INE. ");
					}
				}
			} else {
				if (direccion.getIdMunicipio() != null && !direccion.getIdMunicipio().isEmpty()) {
					Boolean municipioOK = servicioDireccion.codigoMunicipioCorrecto(direccion.getIdProvincia(), direccion.getIdMunicipio());
					if (!municipioOK) {
						resultadoVal.addListaMensajeError("- El municipio es obligatorio o no es válido para la DGT. ");
					}
				} else {
					resultadoVal.addListaMensajeError("- El municipio es obligatorio. ");
				}

				if (direccion.getIdTipoVia() != null && !direccion.getIdTipoVia().isEmpty()) {
					Boolean esTipoViaOK = servicioDireccion.esTipoViaCorrecta(direccion.getIdTipoVia());
					if (!esTipoViaOK) {
						TipoViaVO tipoViaVO = servicioDireccion.getTipoViaPorNombre(direccion.getIdTipoVia());
						if (tipoViaVO == null) {
							tipoViaVO = servicioDireccion.getTipoViaPorIdDgt(direccion.getIdTipoVia());
							if (tipoViaVO == null) {
								resultadoVal.addListaMensajeError("- No existe el tipo de vía. ");
							} else {
								direccion.setIdTipoVia(tipoViaVO.getIdTipoVia());
							}
						} else {
							direccion.setIdTipoVia(tipoViaVO.getIdTipoVia());
						}
					}
				} else {
					resultadoVal.addListaMensajeError("- El tipo de via es obligatorio. ");
				}
				String codigoPostal = null;
				if (direccion.getIdMunicipio() != null && !direccion.getIdMunicipio().isEmpty()) {
					codigoPostal = servicioDireccion.obtenerCodigoPostal(direccion.getIdMunicipio(), direccion.getIdProvincia());
				}
				if (direccion.getCodPostal() == null || direccion.getCodPostal().isEmpty()) {
					if (codigoPostal == null || codigoPostal.isEmpty()) {
						resultadoVal.addListaMensajeError("- El código postal es obligatorio. ");
					} else {
						direccion.setCodPostal(codigoPostal);
					}
				} else {
					if (codigoPostal != null && !direccion.getCodPostal().contains(codigoPostal)) {
						resultadoVal.addListaMensajeError("- El código postal no es correcto por lo que no se guardara la dirección. ");
					}
				}

				if (direccion.getPueblo() != null && !direccion.getPueblo().isEmpty()) {
					Boolean esPuebloOK = servicioDireccion.esPuebloCorrecto(direccion.getIdProvincia(), direccion.getIdMunicipio(), direccion.getPueblo());
					if (!esPuebloOK) {
						resultadoVal.addListaMensajeAvisos("- El pueblo no es del callejero INE. ");
					}
				}
			}
			if (direccion.getIdTipoVia() != null && !direccion.getIdTipoVia().isEmpty()) {
				if (direccion.getNombreVia() == null || direccion.getNombreVia().isEmpty()) {
					resultadoVal.addListaMensajeError("- El nombre de la via debe de ir relleno.");
				}
			}
			if (direccion.getNumero() == null || direccion.getNumero().isEmpty()) {
				resultadoVal.addListaMensajeError("- El número de la vía debe de ir relleno.");
			}
		} catch (Exception e) {
			log.error("No se ha podido guardar la dirección porque ha sucedido un error a la hora de validar sus datos, error: ", e);
			resultadoVal.setError(Boolean.TRUE);
			resultadoVal.setMensaje("No se ha podido guardar la dirección porque ha sucedido un error a la hora de validar sus datos.");
		}
		return resultadoVal;
	}

	private ResultadoValidacionImporBean validarMultifasicos(VehiculoVO vehiculo, ResultadoValidacionImporBean resultado) {
		if (StringUtils.isNotBlank(vehiculo.getCodigoMarcaBase())) {
			MarcaDgtVO marca = servicioMarcaDgt.getMarcaDgt(vehiculo.getCodigoMarcaBase(), null, true);
			if (marca == null) {
				resultado.setError(true);
				resultado.addListaMensajeError("La Marca Base no existe");
			}
		}

		if (StringUtils.isBlank(vehiculo.getCodigoMarcaBase()) && StringUtils.isNotBlank(vehiculo.getFabricanteBase())) {
			resultado.setError(true);
			resultado.addListaMensajeError("Debe informar la Marca Base si el Fabricante Base está relleno");
		} else if (StringUtils.isNotBlank(vehiculo.getCodigoMarcaBase()) && StringUtils.isBlank(vehiculo.getFabricanteBase())) {
			resultado.setError(true);
			resultado.addListaMensajeError("Debe informar el Fabricante Base si la Marca Base está rellena");
		}

		if (StringUtils.isNotBlank(vehiculo.getFabricanteBase()) && !"ND".equals(vehiculo.getFabricanteBase()) && StringUtils.isNotBlank(vehiculo.getCodigoMarcaBase())) {
			MarcaFabricanteVO marcaFabricante = servicioVehiculo.obtenerMarcaFabricante(vehiculo.getFabricanteBase(), vehiculo.getCodigoMarcaBase());
			if (marcaFabricante == null) {
				resultado.setError(true);
				resultado.addListaMensajeError("El fabricante base no corresponde con la marca base. Si desconoce el fabricante ponga ND");
			}
		}
		if (StringUtils.isNotBlank(vehiculo.getIdTipoTarjetaItv()) && !"A".equals(vehiculo.getIdTipoTarjetaItv().substring(0, 1)) && !"D".equals(vehiculo.getIdTipoTarjetaItv().substring(0, 1))) {
			if (StringUtils.isNotBlank(vehiculo.getCodigoMarcaBase())) {
				resultado.setError(true);
				resultado.addListaMensajeError("La Marca Base no permitida para este tipo de tarjeta");
			}
			if (StringUtils.isNotBlank(vehiculo.getFabricanteBase())) {
				resultado.setError(true);
				resultado.addListaMensajeError("El Fabricante Base no permitido para este tipo de tarjeta");
			}
			if (StringUtils.isNotBlank(vehiculo.getTipoBase())) {
				resultado.setError(true);
				resultado.addListaMensajeError("El Tipo Base no permitido para este tipo de tarjeta");
			}
			if (StringUtils.isNotBlank(vehiculo.getVersionBase())) {
				resultado.setError(true);
				resultado.addListaMensajeError("La Version Base no permitida para este tipo de tarjeta");
			}
			if (StringUtils.isNotBlank(vehiculo.getnHomologacionBase())) {
				resultado.setError(true);
				resultado.addListaMensajeError("El Numero de Homologacion Base no permitida para este tipo de tarjeta");
			}
			if (vehiculo.getMomBase() != null) {
				resultado.setError(true);
				resultado.addListaMensajeError("El MOM Base no permitido para este tipo de tarjeta");
			}
		}
		return resultado;
	}

	private ResultadoValidacionImporBean validarElectricos(VehiculoVO vehiculo, ResultadoValidacionImporBean resultado) {
		if ("E".equals(vehiculo.getIdCarburante())) {
			if ((vehiculo.getConsumo() == null || vehiculo.getConsumo().intValue() <= 0) &&
				(!"BEV".equals(vehiculo.getCategoriaElectrica()) && !"PHEV".equals(vehiculo.getCategoriaElectrica()) && !"REEV".equals(vehiculo.getCategoriaElectrica()) &&
					!"HEV".equals(vehiculo.getCategoriaElectrica()) || !"FCEV".equals(vehiculo.getCategoriaElectrica()))) {
				resultado.setError(true);
				resultado.addListaMensajeError("Informe Consumo KW/H para vehículos eléctricos o híbridos");
			}
			if (vehiculo.getCategoriaElectrica() == null && ("M".equals(vehiculo.getIdDirectivaCee().substring(0, 1)) || "N".equals(vehiculo.getIdDirectivaCee().substring(0, 1)))) {
				resultado.setError(true);
				resultado.addListaMensajeError("Categoría eléctrica obligatoria con combustible eléctrico");
			}
		} else if (vehiculo.getCategoriaElectrica() != null) {
			if (!"BEV".equals(vehiculo.getCategoriaElectrica()) && !"PHEV".equals(vehiculo.getCategoriaElectrica()) && !"REEV".equals(vehiculo.getCategoriaElectrica()) &&
					!"HEV".equals(vehiculo.getCategoriaElectrica()) && !"FCEV".equals(vehiculo.getCategoriaElectrica())) {
				resultado.setError(true);
				resultado.addListaMensajeError("Categoría eléctrica no corresponde con ninguna existente");
			}
		} else {
			if (vehiculo.getConsumo() != null && vehiculo.getConsumo().intValue() > 0) {
				resultado.setError(true);
				resultado.addListaMensajeError("Sólo informe Consumo KW/H para vehículos eléctricos o híbridos");
			}
			if (vehiculo.getAutonomiaElectrica() != null) {
				resultado.setError(true);
				resultado.addListaMensajeError("Sólo informe autonomía eléctrica para vehículos eléctricos o híbridos");
			}
		}

		/*
		 * if (vehiculo.getIdCarburante() != null && "E".equals(vehiculo.getIdCarburante())) { int numCoincidentes = servicioCaracteristicasElectricas.numeroCoincidentes(vehiculo.getCodigoMarca(), vehiculo.getModelo(), vehiculo.getTipoItv(), vehiculo.getVersion(), vehiculo .getVariante(),
		 * vehiculo.getBastidor(), vehiculo.getCategoriaElectrica(), vehiculo.getConsumo(), vehiculo.getAutonomiaElectrica()); if (numCoincidentes == 0) { resultado.addListaMensajeAvisos("- Las características del vehículo eléctrico no coinciden con ningún vehículo según circular del 04/07/2014.");
		 * } }
		 */
		return resultado;
	}

	private ResultadoValidacionImporBean validarTipoVehiculo(VehiculoVO vehiculo, ResultadoValidacionImporBean resultado) {
		if (StringUtils.isNotBlank(vehiculo.getTipoVehiculo())) {
			TipoVehiculoVO tipoVehiculoVO = servicioVehiculo.obtenerTipoVehiculo(vehiculo.getTipoVehiculo());
			if (tipoVehiculoVO == null) {
				resultado.setError(Boolean.TRUE);
				resultado.addListaMensajeError("el Tipo Vehículo es incorrecto.");
			}
		}
		return resultado;
	}

}