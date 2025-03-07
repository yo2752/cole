package org.gestoresmadrid.oegam2comun.remesa.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.intervinienteModelos.model.vo.IntervinienteModelosVO;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.modelo600_601.model.vo.Modelo600_601VO;
import org.gestoresmadrid.core.modelo600_601.model.vo.ResultadoModelo600601VO;
import org.gestoresmadrid.core.modelos.model.enumerados.ErroresWSModelo600601;
import org.gestoresmadrid.core.modelos.model.enumerados.EstadoModelos;
import org.gestoresmadrid.core.modelos.model.enumerados.EstadoRemesas;
import org.gestoresmadrid.core.modelos.model.enumerados.GrupoConcepto;
import org.gestoresmadrid.core.modelos.model.enumerados.Modelo;
import org.gestoresmadrid.core.modelos.model.enumerados.TipoBien;
import org.gestoresmadrid.core.modelos.model.enumerados.TipoTramiteModelos;
import org.gestoresmadrid.core.modelos.model.vo.TipoImpuestoVO;
import org.gestoresmadrid.core.remesa.model.dao.RemesaDao;
import org.gestoresmadrid.core.remesa.model.vo.RemesaVO;
import org.gestoresmadrid.oegam2comun.bien.model.service.ServicioBien;
import org.gestoresmadrid.oegam2comun.bien.view.dto.BienRemesaDto;
import org.gestoresmadrid.oegam2comun.bienRustico.model.service.ServicioBienRustico;
import org.gestoresmadrid.oegam2comun.bienUrbano.model.service.ServicioBienUrbano;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.datosBancariosFavoritos.view.dto.DatosBancariosFavoritosDto;
import org.gestoresmadrid.oegam2comun.evolucionRemesa.model.service.ServicioEvolucionRemesas;
import org.gestoresmadrid.oegam2comun.intervinienteModelos.model.service.ServicioIntervinienteModelos;
import org.gestoresmadrid.oegam2comun.intervinienteModelos.view.dto.IntervinienteModelosDto;
import org.gestoresmadrid.oegam2comun.model.service.ServicioNotificacion;
import org.gestoresmadrid.oegam2comun.model.service.ServicioUsuario;
import org.gestoresmadrid.oegam2comun.modelo600_601.model.service.ServicioModelo600_601;
import org.gestoresmadrid.oegam2comun.modelo600_601.view.dto.Modelo600_601Dto;
import org.gestoresmadrid.oegam2comun.modelos.model.service.ServicioBonificacion;
import org.gestoresmadrid.oegam2comun.modelos.model.service.ServicioConcepto;
import org.gestoresmadrid.oegam2comun.modelos.model.service.ServicioModelos;
import org.gestoresmadrid.oegam2comun.modelos.model.service.ServicioTipoImpuesto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.BonificacionDto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.ConceptoDto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.ModeloDto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.TipoImpuestoDto;
import org.gestoresmadrid.oegam2comun.participacion.model.service.ServicioParticipacion;
import org.gestoresmadrid.oegam2comun.participacion.view.dto.ParticipacionDto;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersonaDireccion;
import org.gestoresmadrid.oegam2comun.remesa.model.service.ServicioRemesas;
import org.gestoresmadrid.oegam2comun.remesa.view.bean.RemesaBean;
import org.gestoresmadrid.oegam2comun.remesa.view.dto.RemesaDto;
import org.gestoresmadrid.oegam2comun.tipoInmueble.view.dto.TipoInmuebleDto;
import org.gestoresmadrid.oegam2comun.view.dto.NotificacionDto;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDireccionDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service(value = "servicioRemesas")
public class ServicioRemesasImpl implements ServicioRemesas {

	private static final long serialVersionUID = 3970138255225930737L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioRemesasImpl.class);

	@Autowired
	private ServicioIntervinienteModelos servicioIntervinienteModelos;

	@Autowired
	private ServicioPersonaDireccion servicioPersonaDireccion;

	@Autowired
	private ServicioPersona servicioPersona;

	@Autowired
	private ServicioContrato servicioContrato;

	@Autowired
	private ServicioBien servicioBien;

	@Autowired
	private ServicioParticipacion servicioParticipacion;

	@Autowired
	private ServicioModelos servicioModelos;

	@Autowired
	private ServicioBonificacion servicioBonificacion;

	@Autowired
	private ServicioConcepto servicioConcepto;

	@Autowired
	private ServicioUsuario servicioUsuario;

	@Autowired
	private ServicioBienRustico servicioBienRustico;

	@Autowired
	private ServicioBienUrbano servicioBienUrbano;

	@Autowired
	private Conversor conversor;

	@Autowired
	private ServicioTipoImpuesto servicioTipoImpuesto;

	@Autowired
	private ServicioModelo600_601 servicioModelo600_601;

	@Autowired
	private ServicioNotificacion servicioNotificacion;

	@Autowired
	private RemesaDao remesaDao;

	@Autowired
	private ServicioEvolucionRemesas servicioEvolucionRemesas;

	@Autowired
	Utiles utiles;

	@Override
	@Transactional
	public ResultBean guardarRemesa(RemesaDto remesa, String codigosPorct, String porctj, String numColegiado, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(false);
		String tipoTramiteModelo = null;
		BigDecimal estadoAnt = null;
		try {
			resultado = validarDatosMinimosRemesa(remesa);
			if (resultado == null) {
				resultado = new ResultBean(false);
				convertirCombos(remesa);
				RemesaVO remesaVO = conversor.transform(remesa, RemesaVO.class);
				if (remesaVO.getNumExpediente() == null) {
					remesaVO.setFechaAlta(new Date());
					remesaVO.setNumExpediente(generarNumExpediente(numColegiado));
				}
				if (remesaVO.getEstado() != null) {
					estadoAnt = remesaVO.getEstado();
				}
				remesaVO.setEstado(new BigDecimal(EstadoRemesas.Inicial.getValorEnum()));
				ResultBean resultTpI = servicioTipoImpuesto.buscarTipoImpuestoPorConceptoYModelo(remesa.getConcepto(), remesa.getModelo());
				if (!resultTpI.getError()) {
					remesaVO.setTipoImpuesto(conversor.transform(((TipoImpuestoDto) resultTpI.getAttachment("tipoImpuesto")), TipoImpuestoVO.class));
				}
				remesaDao.guardarOActualizar(remesaVO);
				servicioEvolucionRemesas.guardarEvolucionRemesa(remesaVO.getNumExpediente(), estadoAnt, new BigDecimal(EstadoRemesas.Inicial.getValorEnum()), idUsuario);
				if (Modelo.Modelo600.getValorEnum().equals(remesaVO.getModelo().getId().getModelo())) {
					tipoTramiteModelo = TipoTramiteModelos.Remesa600.getValorEnum();
				} else if (Modelo.Modelo601.getValorEnum().equals(remesaVO.getModelo().getId().getModelo())) {
					tipoTramiteModelo = TipoTramiteModelos.Remesa601.getValorEnum();
				}
				UsuarioDto usuario = servicioUsuario.getUsuarioDto(idUsuario);
				boolean esConceptoBienes = servicioConcepto.esConceptoBienes(remesa.getConcepto());
				if (esConceptoBienes) {

					resultado = guardadoConBienes(remesa, remesaVO, usuario, tipoTramiteModelo, codigosPorct, porctj, esConceptoBienes);
				} else {
					resultado = guardadoSinBienes(remesa, remesaVO, usuario, tipoTramiteModelo, esConceptoBienes);
				}
				if (resultado == null || !resultado.getError()) {
					if (resultado == null) {
						resultado = new ResultBean(false);
					}
					resultado.addAttachment("numExpediente", remesaVO.getNumExpediente());
					resultado.addAttachment("idRemesa", remesaVO.getIdRemesa());
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar la remesa, error: ", e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de guardar la remesa.");
		}
		if (resultado != null && resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultBean guardadoSinBienes(RemesaDto remesa, RemesaVO remesaVO, UsuarioDto usuario, String tipoTramiteModelo, boolean esConceptoBienes) {
		ResultBean resultado = new ResultBean(false);
		if (resultado == null || !resultado.getError()) {
			ResultBean resultInterv = guardarIntervinientes(remesa, remesaVO, usuario, tipoTramiteModelo, esConceptoBienes);
			if (!resultInterv.getError()) {
				if (resultInterv.getListaMensajes() != null && !resultInterv.getListaMensajes().isEmpty()) {
					for (String error : resultInterv.getListaMensajes()) {
						resultado.addMensajeALista(error);
					}
				}
				if (!resultado.getError()) {
					ResultBean resultPart = servicioParticipacion.comprobarParticipacionesExistentes(remesaVO.getIdRemesa(), esConceptoBienes);
					if (resultPart.getError()) {
						return resultPart;
					}
				}
			} else {
				resultado = resultInterv;
			}
		}
		return resultado;
	}

	private ResultBean guardadoConBienes(RemesaDto remesa, RemesaVO remesaVO, UsuarioDto usuario, String tipoTramiteModelo, String codigosPorct, String porctj, boolean esConceptoBienes) {
		ResultBean resultado = new ResultBean(false);
		ResultBean resultInterv = guardarIntervinientes(remesa, remesaVO, usuario, tipoTramiteModelo, esConceptoBienes);
		if (!resultInterv.getError()) {
			if (resultInterv.getListaMensajes() != null && !resultInterv.getListaMensajes().isEmpty()) {
				for (String error : resultInterv.getListaMensajes()) {
					resultado.getListaMensajes().add(error);
				}
			}

			ResultBean resultBien = guardarBienes(remesa, remesaVO, usuario, tipoTramiteModelo);
			if (!resultBien.getError()) {
				if (resultBien.getListaMensajes() != null && !resultBien.getListaMensajes().isEmpty()) {
					for (String error : resultBien.getListaMensajes()) {
						resultado.getListaMensajes().add(error);
					}
				}
				ResultBean resultCoefPart = guardarCoefParticipacion(remesa, remesaVO, codigosPorct, porctj);
				if (!resultCoefPart.getError()) {
					if (resultCoefPart.getListaMensajes() != null && !resultCoefPart.getListaMensajes().isEmpty()) {
						for (String error : resultCoefPart.getListaMensajes()) {
							resultado.getListaMensajes().add(error);
						}
					}
				} else {
					resultado = resultCoefPart;
				}
			} else {
				resultado = resultBien;
			}
		} else {
			resultado = resultInterv;
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean gestionarConceptoRemesa(Long idRemesa) {
		ResultBean resultado = new ResultBean(false);
		try {
			RemesaVO remesaBBDD = remesaDao.getRemesaPorID(idRemesa, false);
			resultado = servicioConcepto.gestionarRemesaPorConcepto(remesaBBDD);
			if (resultado != null && resultado.getError()) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de gestionar los datos de la remesa por el concepto, error: ", e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de gestionar los datos de la remesa por el concepto");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean actualizarRemesa(RemesaVO remesaVO) {
		ResultBean resultado = new ResultBean(false);
		try {
			if (remesaVO != null) {
				remesaDao.actualizar(remesaVO);
			} else {
				resultado.setError(true);
				resultado.addMensajeALista("No existena datos de la remesa para actualizar.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar la remesa, error: ", e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de actualizar la remesa.");
		}
		return resultado;
	}

	private void convertirCombos(RemesaDto remesa) {
		if (remesa.getBonificacion() != null && remesa.getBonificacion().getBonificacion().equals("-1")) {
			remesa.setBonificacion(null);
		}
		if (remesa.getFundamentoExencion() != null && remesa.getFundamentoExencion().getFundamentoExcencion().equals("-1")) {
			remesa.setFundamentoExencion(null);
		}
	}

	private ResultBean guardarCoefParticipacion(RemesaDto remesa, RemesaVO remesaVO, String codigoPorct, String porcentajes) {
		ResultBean resultBean = new ResultBean(false);
		resultBean = servicioParticipacion.guardarCoefParticipacion(codigoPorct, porcentajes, remesaVO.getIdRemesa());
		return resultBean;
	}

	private ResultBean guardarBienes(RemesaDto remesa, RemesaVO remesaVO, UsuarioDto usuario, String tipoTramiteModelo) {
		ResultBean resultado = new ResultBean(false);
		if (remesa.getTipoBien() != null && !remesa.getTipoBien().isEmpty()) {
			if (servicioBienRustico.esBienRustico(remesa.getBienRusticoDto())) {
				remesa.getBienRusticoDto().getTipoInmueble().setIdTipoBien(TipoBien.Rustico.getValorEnum());
				resultado = servicioBien.guardarBienRemesaPantalla(remesa.getBienRusticoDto(), remesaVO.getIdRemesa(), remesaVO.getNumExpediente(), usuario, tipoTramiteModelo);
			} else if (servicioBienUrbano.esBienUrbano(remesa.getBienUrbanoDto())) {
				remesa.getBienUrbanoDto().getTipoInmueble().setIdTipoBien(TipoBien.Urbano.getValorEnum());
				resultado = servicioBien.guardarBienRemesaPantalla(remesa.getBienUrbanoDto(), remesaVO.getIdRemesa(), remesaVO.getNumExpediente(), usuario, tipoTramiteModelo);
			} else if (servicioBien.esOtroBien(remesa.getOtroBienDto()) || TipoBien.Otro.getValorEnum().equals(remesa.getTipoBien())) {
				TipoInmuebleDto tipoInmueble = new TipoInmuebleDto();
				tipoInmueble.setIdTipoBien(TipoBien.Otro.getValorEnum());
				tipoInmueble.setIdTipoInmueble("OT");
				remesa.getOtroBienDto().setTipoInmueble(tipoInmueble);
				if (StringUtils.isNotBlank(remesa.getOtroBienDto().getRefCatrastal()) || remesa.getOtroBienDto().getValorDeclarado() != null || remesa.getOtroBienDto().getTransmision() != null
						|| remesa.getOtroBienDto().getValorTasacion() != null) {
					resultado = servicioBien.guardarBienRemesaPantalla(remesa.getOtroBienDto(), remesaVO.getIdRemesa(), remesaVO.getNumExpediente(), usuario, tipoTramiteModelo);
				}

			}
		}
		return resultado;
	}

	private ResultBean guardarIntervinientes(RemesaDto remesa, RemesaVO remesaVO, UsuarioDto usuario, String tipoTramiteModelo, boolean esConceptoBienes) {
		ResultBean resultado = new ResultBean(false);
		List<String> listaMensajes = new ArrayList<String>();
		if (remesa.getSujetoPasivo() != null && remesa.getSujetoPasivo().getPersona() != null && remesa.getSujetoPasivo().getPersona().getNif() != null && !remesa.getSujetoPasivo().getPersona()
				.getNif().isEmpty()) {
			remesa.getSujetoPasivo().setTipoInterviniente(TipoInterviniente.SujetoPasivo.getValorEnum());
			ResultBean resultSjP = servicioIntervinienteModelos.guardarIntervinientesRemesa(conversor.transform(remesa.getSujetoPasivo(), IntervinienteModelosVO.class), remesaVO, tipoTramiteModelo,
					usuario, esConceptoBienes, remesa.getSujetoPasivo().getPorcentaje());
			if (resultSjP.getError()) {
				return resultSjP;
			} else {
				if (resultSjP.getListaMensajes() != null && !resultSjP.getListaMensajes().isEmpty()) {
					for (String mensaje : resultSjP.getListaMensajes()) {
						listaMensajes.add(mensaje);
					}
				}
			}
		}
		if (remesa.getTransmitente() != null && remesa.getTransmitente().getPersona() != null && remesa.getTransmitente().getPersona().getNif() != null && !remesa.getTransmitente().getPersona()
				.getNif().isEmpty()) {
			remesa.getTransmitente().setTipoInterviniente(TipoInterviniente.Transmitente.getValorEnum());
			ResultBean resultTrans = servicioIntervinienteModelos.guardarIntervinientesRemesa(conversor.transform(remesa.getTransmitente(), IntervinienteModelosVO.class), remesaVO, tipoTramiteModelo,
					usuario, esConceptoBienes, remesa.getTransmitente().getPorcentaje());
			if (resultTrans.getError()) {
				return resultTrans;
			} else {
				if (resultTrans.getListaMensajes() != null && !resultTrans.getListaMensajes().isEmpty()) {
					for (String mensaje : resultTrans.getListaMensajes()) {
						listaMensajes.add(mensaje);
					}
				}
			}
		}
		if (remesa.getPresentador() != null && remesa.getPresentador().getPersona() != null && remesa.getPresentador().getPersona().getNif() != null && !remesa.getPresentador().getPersona().getNif()
				.isEmpty() && remesa.getPresentador().getIdInterviniente() == null) {
			remesa.getPresentador().setTipoInterviniente(TipoInterviniente.Presentador.getValorEnum());
			ResultBean resultP = servicioIntervinienteModelos.guardarIntervinientesRemesa(conversor.transform(remesa.getPresentador(), IntervinienteModelosVO.class), remesaVO, tipoTramiteModelo,
					usuario, true, null);
			if (resultP.getError()) {
				return resultP;
			} else {
				if (resultP.getListaMensajes() != null && !resultP.getListaMensajes().isEmpty()) {
					for (String mensaje : resultP.getListaMensajes()) {
						listaMensajes.add(mensaje);
					}
				}
			}
		}
		if (listaMensajes != null && !listaMensajes.isEmpty()) {
			resultado.setListaMensajes(listaMensajes);
		}
		return resultado;
	}

	private ResultBean convertirVoToDto(RemesaVO remesaVO) {
		ResultBean resultBean = null;
		RemesaDto remesaDto = conversor.transform(remesaVO, RemesaDto.class);
		resultBean = servicioBien.convertirListaBienVoToDto(remesaDto, remesaVO);
		if (resultBean == null) {
			resultBean = servicioIntervinienteModelos.convertirIntervinientesVoToDto(remesaDto, remesaVO);
			if (resultBean == null) {
				Boolean esConceptoBien = servicioConcepto.esConceptoBienes(remesaDto.getConcepto());
				if (esConceptoBien) {
					resultBean = servicioParticipacion.convertirListaCoefParticipacionVoToDto(remesaDto, remesaVO);
				} else {
					resultBean = servicioParticipacion.convertirListaCoefPartIntervVOToDto(remesaDto, remesaVO);
				}
			}
			if (resultBean == null || !resultBean.getError()) {
				if (remesaVO.getListaBienes() != null && !remesaVO.getListaBienes().isEmpty()) {
					rellenarNombreBienes(remesaVO, remesaDto);
					remesaDto.setNumBienes(new BigDecimal(remesaVO.getListaBienes().size()));
				}
				resultBean = new ResultBean(false);
				resultBean.addAttachment("remesaDto", remesaDto);
				Boolean existePresentable = false;
				if (remesaDto.getListaModelos() != null && !remesaDto.getListaModelos().isEmpty()) {
					for (Modelo600_601Dto modeloDto : remesaDto.getListaModelos()) {
						if (EstadoModelos.Autoliquidable.getValorEnum().equals(modeloDto.getEstado())) {
							existePresentable = true;
							break;
						}
					}
				}
				resultBean.addAttachment("existePresentable", existePresentable);
			}
		}
		return resultBean;
	}

	private void rellenarNombreBienes(RemesaVO remesaVO, RemesaDto remesaDto) {
		int io = 0;
		int jo = 0;
		List<ParticipacionDto> listaAuxSujPas = new ArrayList<ParticipacionDto>();
		List<ParticipacionDto> listaAuxTr = new ArrayList<ParticipacionDto>();
		if (remesaDto.getListaBienesRusticos() != null && !remesaDto.getListaBienesRusticos().isEmpty()) {
			ordenarBienes(remesaDto.getListaBienesRusticos());
			for (int i = 0; i < remesaDto.getListaBienesRusticos().size(); i++) {
				remesaDto.getListaBienesRusticos().get(i).getBien().setNombreBien(ServicioBien.NOMBRE_BIEN_RUSTICO + i);
				if (remesaDto.getListaPartSujPasivo() != null && !remesaDto.getListaPartSujPasivo().isEmpty()) {
					for (ParticipacionDto partSujPasivoDto : remesaDto.getListaPartSujPasivo()) {
						if (partSujPasivoDto.getBien().getIdBien().equals(remesaDto.getListaBienesRusticos().get(i).getBien().getIdBien())) {
							partSujPasivoDto.getBien().setNombreBien(ServicioBien.NOMBRE_BIEN_RUSTICO + i);
							listaAuxSujPas.add(io++, partSujPasivoDto);
						}
					}
				}
				if (remesaDto.getListaPartTransmitente() != null && !remesaDto.getListaPartTransmitente().isEmpty()) {
					for (ParticipacionDto partTransDto : remesaDto.getListaPartTransmitente()) {
						if (partTransDto.getBien().getIdBien().equals(remesaDto.getListaBienesRusticos().get(i).getBien().getIdBien())) {
							partTransDto.getBien().setNombreBien(ServicioBien.NOMBRE_BIEN_RUSTICO + i);
							listaAuxTr.add(jo++, partTransDto);
						}
					}
				}
			}
		}
		if (remesaDto.getListaBienesUrbanos() != null && !remesaDto.getListaBienesUrbanos().isEmpty()) {
			ordenarBienes(remesaDto.getListaBienesUrbanos());
			for (int j = 0; j < remesaDto.getListaBienesUrbanos().size(); j++) {
				remesaDto.getListaBienesUrbanos().get(j).getBien().setNombreBien(ServicioBien.NOMBRE_BIEN_URBANO + j);
				if (remesaDto.getListaPartSujPasivo() != null && !remesaDto.getListaPartSujPasivo().isEmpty()) {
					for (ParticipacionDto partSujPasivoDto : remesaDto.getListaPartSujPasivo()) {
						if (partSujPasivoDto.getBien().getIdBien().equals(remesaDto.getListaBienesUrbanos().get(j).getBien().getIdBien())) {
							partSujPasivoDto.getBien().setNombreBien(ServicioBien.NOMBRE_BIEN_URBANO + j);
							listaAuxSujPas.add(io++, partSujPasivoDto);
						}
					}
				}
				if (remesaDto.getListaPartTransmitente() != null && !remesaDto.getListaPartTransmitente().isEmpty()) {
					for (ParticipacionDto partTransDto : remesaDto.getListaPartTransmitente()) {
						if (partTransDto.getBien().getIdBien().equals(remesaDto.getListaBienesUrbanos().get(j).getBien().getIdBien())) {
							partTransDto.getBien().setNombreBien(ServicioBien.NOMBRE_BIEN_URBANO + j);
							listaAuxTr.add(jo++, partTransDto);
						}
					}
				}
			}
		}
		if (listaAuxSujPas != null && !listaAuxSujPas.isEmpty()) {
			remesaDto.setListaPartSujPasivo(listaAuxSujPas);
		}
		if (listaAuxTr != null && !listaAuxTr.isEmpty()) {
			remesaDto.setListaPartTransmitente(listaAuxTr);
		}

	}

	private void ordenarBienes(List<BienRemesaDto> lista) {
		Collections.sort(lista, new Comparator<BienRemesaDto>() {
			@Override
			public int compare(BienRemesaDto br1, BienRemesaDto br2) {
				return br1.getBien().getIdBien().compareTo(br2.getBien().getIdBien());
			}
		});
	}

	private ResultBean validarDatosMinimosRemesa(RemesaDto remesa) {
		if (remesa == null) {
			return new ResultBean(true, "Debe rellenar datos de la remesa para poder guardarla.");
		}
		if (remesa.getOficinaLiquidadora() == null || remesa.getOficinaLiquidadora().getId() == null || remesa.getOficinaLiquidadora().getId().isEmpty()) {
			return new ResultBean(true, "Debe seleccionar una oficina liquidadora.");
		}
		if (remesa.getOficinaLiquidadora() == null || remesa.getOficinaLiquidadora().getIdProvincia() == null || remesa.getOficinaLiquidadora().getIdProvincia().isEmpty()) {
			return new ResultBean(true, "Debe seleccionar una provincia para la oficina liquidadora.");
		}
		if (remesa.getTipoDocumento() == null || remesa.getTipoDocumento().isEmpty()) {
			return new ResultBean(true, "Debe seleccionar un tipo de documento.");
		}
		if (remesa.getConcepto() == null || remesa.getConcepto().getConcepto() == null || remesa.getConcepto().getConcepto().isEmpty()) {
			return new ResultBean(true, "Debe seleccionar un concepto.");
		}
		if (remesa.getContrato() == null || remesa.getContrato().getIdContrato() == null) {
			return new ResultBean(true, "No existen datos del contrato, por lo que no se podra guardar la remesa.");
		}
		if (remesa.getListaBienesRusticos() != null && !remesa.getListaBienesRusticos().isEmpty()) {
			ResultBean resultBean = validarBienTransValorDeclarado(remesa.getListaBienesRusticos());
			if (resultBean != null) {
				return resultBean;
			}
		}
		if (remesa.getListaBienesUrbanos() != null && !remesa.getListaBienesUrbanos().isEmpty()) {
			ResultBean resultBean = validarBienTransValorDeclarado(remesa.getListaBienesUrbanos());
			if (resultBean != null) {
				return resultBean;
			}
		}
		if (remesa.getListaOtrosBienes() != null && !remesa.getListaOtrosBienes().isEmpty()) {
			ResultBean resultBean = validarBienTransValorDeclarado(remesa.getListaOtrosBienes());
			if (resultBean != null) {
				return resultBean;
			}
		}
		if (!remesa.getLiquidacionComplementaria()) {
			remesa.setNumJustiPrimeraAutoliq(null);
			remesa.setFechaPrimLiquidacion(null);
			remesa.setNumPrimPresentacion(null);
		}
		return null;
	}

	private ResultBean validarBienTransValorDeclarado(List<BienRemesaDto> listaBienes) {
		for (BienRemesaDto bienRemesaDto : listaBienes) {
			if (bienRemesaDto.getValorDeclarado() == null) {
				return new ResultBean(true, "Si ha seleccionado algún bien, debe rellenar el valor declarado.");
			} else if (bienRemesaDto.getTransmision() == null) {
				return new ResultBean(true, "Si ha seleccionado algún bien, debe rellenar el valor de la transmisión.");
			}
		}
		return null;
	}

	@Override
	@Transactional
	public ResultBean buscarPersona(String nif, BigDecimal idContrato, Long idRemesa, String tipoInterviniente) {
		ResultBean resultado = null;
		try {
			// buscamos si existe el interviniete en la remesa
			if (idRemesa != null) {
				resultado = servicioIntervinienteModelos.getIntervinientePorNifYIdRemesaYTipoInterviniente(nif, idRemesa, tipoInterviniente);
			}
			if (resultado == null) {
				ContratoDto contratoDto = servicioContrato.getContratoDto(idContrato);
				if (contratoDto != null && contratoDto.getColegiadoDto() != null && contratoDto.getColegiadoDto().getNumColegiado() != null && nif != null && !nif.isEmpty()) {
					ResultBean resultBean = servicioPersona.buscarPersona(nif, contratoDto.getColegiadoDto().getNumColegiado());
					if (!resultBean.getError()) {
						PersonaDto personaDto = (PersonaDto) resultBean.getAttachment(ServicioPersona.PERSONA);
						IntervinienteModelosDto intervinienteModelosDto = new IntervinienteModelosDto();
						if (personaDto != null) {
							resultBean = servicioPersonaDireccion.buscarPersonaDireccionDto(contratoDto.getColegiadoDto().getNumColegiado(), nif);
							if (!resultBean.getError()) {
								PersonaDireccionDto personaDireccionDto = (PersonaDireccionDto) resultBean.getAttachment(ServicioPersonaDireccion.PERSONADIRECCION);
								if (personaDireccionDto != null) {
									intervinienteModelosDto.setDireccion(personaDireccionDto.getDireccion());
								}
							}
						} else {
							personaDto = new PersonaDto();
							personaDto.setNif(nif);
							personaDto.setNumColegiado(contratoDto.getColegiadoDto().getNumColegiado());
						}
						intervinienteModelosDto.setPersona(personaDto);
						resultado = new ResultBean(false);
						resultado.addAttachment("intervinienteDto", intervinienteModelosDto);
					} else {
						resultado = new ResultBean(true, "Ha sucedido un error a la hora de buscar la persona");
					}
				} else {
					resultado = new ResultBean(true, "No existen datos suficientes para poder realizar la busqueda de los intervinientes.");
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la persona por el nif: " + nif + ", y para el contrato: " + idContrato + ",error: ", e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de obtener la persona");
		}
		return resultado;
	}

	@Override
	@Transactional
	public IntervinienteModelosDto getPresentador(BigDecimal idContrato) {
		ResultBean resultado = null;
		IntervinienteModelosDto intervinienteModelosDto = null;
		try {
			ContratoDto contratoDto = servicioContrato.getContratoDto(idContrato);
			if (contratoDto != null && contratoDto.getColegiadoDto() != null) {
				resultado = servicioPersona.buscarPersona(contratoDto.getColegiadoDto().getUsuario().getNif(), contratoDto.getColegiadoDto().getNumColegiado());
				if (!resultado.getError()) {
					PersonaDto personaDto = (PersonaDto) resultado.getAttachment(ServicioPersona.PERSONA);
					intervinienteModelosDto = new IntervinienteModelosDto();
					if (personaDto != null) {
						intervinienteModelosDto.setPersona(personaDto);
						intervinienteModelosDto.setTipoInterviniente(TipoInterviniente.Presentador.getValorEnum());
						resultado = servicioPersonaDireccion.buscarPersonaDireccionDto(contratoDto.getColegiadoDto().getNumColegiado(), contratoDto.getColegiadoDto().getUsuario().getNif());
						if (!resultado.getError()) {
							PersonaDireccionDto personaDireccionDto = (PersonaDireccionDto) resultado.getAttachment(ServicioPersonaDireccion.PERSONADIRECCION);
							intervinienteModelosDto.setDireccion(personaDireccionDto.getDireccion());
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el presentador como el colegiado, error: ", e);
		}
		return intervinienteModelosDto;
	}

	@Override
	@Transactional
	public ResultBean eliminarIntervinientesRemesa(Long idRemesa, String codigos) {
		ResultBean resultado = null;
		try {
			if (codigos != null && !codigos.isEmpty()) {
				String idIntervientes[] = codigos.split("-");
				for (String idInterviniente : idIntervientes) {
					ResultBean resultCoefPart = servicioParticipacion.eliminarCoefPartRemesaInterviniente(idRemesa, Long.parseLong(idInterviniente));
					if (resultCoefPart == null || !resultCoefPart.getError()) {
						ResultBean resultInterviniente = servicioIntervinienteModelos.eliminarInterviniente(Long.parseLong(idInterviniente));
						if (resultInterviniente != null && resultInterviniente.getError()) {
							resultado = resultInterviniente;
							break;
						}
					} else {
						resultado = resultCoefPart;
						break;
					}
				}
			} else {
				resultado = new ResultBean(true, "Debe seleccionar algún interviniente para eliminar.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar los intervinientes, error: ", e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de eliminar los intervinientes");
		}
		if (resultado != null && resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal generarNumExpediente(String numColegiado) throws Exception {
		return remesaDao.generarNumExpediente(numColegiado);
	}

	@Override
	public ModeloDto getModelo(Modelo modelo) {
		return servicioModelos.getModeloDtoPorModelo(modelo);
	}

	@Override
	public String getMontoBonificacion(String codigo) {
		if (codigo != null && !codigo.isEmpty()) {
			BonificacionDto bonificacionDto = servicioBonificacion.getBonificacionPorId(codigo);
			if (bonificacionDto != null && bonificacionDto.getMonto() != null) {
				return bonificacionDto.getMonto().toString();
			}
		}
		return null;
	}

	@Override
	public String getGrupoValidacion(String codigo) {
		String grupo = "";
		if (codigo != null && !codigo.isEmpty()) {
			ConceptoDto conceptoDto = servicioConcepto.getConceptoPorAbreviatura(codigo);
			if (conceptoDto != null && conceptoDto.getGrupoValidacion() != null && !conceptoDto.getGrupoValidacion().isEmpty()) {
				return GrupoConcepto.convertirNombre(conceptoDto.getGrupoValidacion());
			}
		}
		return grupo;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultBean getRemesaPorNumExpediente(BigDecimal numExpediente, boolean remesaCompleta) {
		ResultBean resultado = null;
		try {
			if (numExpediente != null) {
				RemesaVO remesaVO = remesaDao.getRemesaPorExpediente(numExpediente, remesaCompleta);
				if (remesaVO != null) {
					resultado = convertirVoToDto(remesaVO);
				} else {
					resultado = new ResultBean(true, "No existe ninguna remesa con ese numero de expediente.");
				}
			} else {
				resultado = new ResultBean(true, "Debe seleccionar un numero de expediente para obtener la remesa.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la remesa por el numero de expediente, error: ", e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de obtener la remesa por el numero de expediente.");
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultBean esEstadoAnulado(BigDecimal numExpediente) {
		ResultBean resultado = new ResultBean(false);
		try {
			if (numExpediente != null) {
				RemesaVO remesaVO = remesaDao.getRemesaPorExpediente(numExpediente, false);
				if (remesaVO != null) {
					Boolean esEstadoAnulado = false;
					if (EstadoRemesas.Anulada.getValorEnum().equals(remesaVO.getEstado().toString())) {
						esEstadoAnulado = true;
					}
					resultado.addAttachment("esAnulado", esEstadoAnulado);
				} else {
					resultado.setError(true);
					resultado.addMensajeALista("No existe ninguna remesa con ese numero de expediente.");
				}
			} else {
				resultado.setError(true);
				resultado.addMensajeALista("Debe seleccionar un numero de expediente para obtener la remesa.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la remesa por el numero de expediente, error: ", e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de obtener la remesa por el numero de expediente.");
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public RemesaVO getRemesaVoPorExpediente(BigDecimal numExpediente, boolean completa) {
		try {
			if (numExpediente != null) {
				RemesaVO remesaVO = remesaDao.getRemesaPorExpediente(numExpediente, completa);
				if (remesaVO != null) {
					return remesaVO;
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la remesaVO por el numero de expediente, error: ", e);
		}
		return null;
	}

	@Override
	public List<RemesaBean> convertirListaEnBeanPantalla(List<RemesaVO> lista) {
		try {
			if (lista != null && !lista.isEmpty()) {
				List<RemesaBean> listaBeanPantalla = new ArrayList<RemesaBean>();
				for (RemesaVO remesaVo : lista) {
					RemesaBean remesaPantBean = conversor.transform(remesaVo, RemesaBean.class, "remesaBeanPantalla");
					remesaPantBean.setDescEstado(EstadoRemesas.convertirTexto(remesaPantBean.getEstado()));
					listaBeanPantalla.add(remesaPantBean);
				}
				return listaBeanPantalla;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de convertir el objeto RemesaVO to RemesaBean, error: ", e);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional
	public ResultBean guardarBienesPantalla(RemesaDto remesa, String codigos, BigDecimal idUsuario, String numColegiado) {
		ResultBean resultado = new ResultBean(false);
		Boolean conErrores = false;
		try {
			ResultBean resultRemesa = guardarRemesa(remesa, null, null, numColegiado, idUsuario);
			if (!resultRemesa.getError()) {
				Long idRemesa = (Long) resultRemesa.getAttachment("idRemesa");
				BigDecimal numExpediente = (BigDecimal) resultRemesa.getAttachment("numExpediente");
				String idBienes[] = codigos.split("-");
				for (String idBien : idBienes) {
					ResultBean resultBien = servicioBien.guardarBienRemesa(Long.parseLong(idBien), idRemesa);
					if (resultBien != null && !resultBien.getError()) {
						conErrores = true;
					} else if (resultBien != null && resultBien.getError()) {
						resultado = resultBien;
						break;
					}
				}
				if (conErrores) {
					resultado.getListaMensajes().add("Algunos bienes seleccionados no se han guardado correctamente.");
				}
				if (!resultado.getError()) {
					ResultBean resultCoefPart = servicioParticipacion.guardarCoefParticipacion(null, null, idRemesa);
					if (!resultCoefPart.getError()) {
						if (resultCoefPart.getListaMensajes() != null && !resultCoefPart.getListaMensajes().isEmpty()) {
							for (String error : resultCoefPart.getListaMensajes()) {
								resultado.getListaMensajes().add(error);
							}
						}
					} else {
						resultado = resultCoefPart;
					}
				}
				if (!resultado.getError()) {
					resultado.addAttachment("idRemesa", idRemesa);
					resultado.addAttachment("numExpediente", numExpediente);
				}
			} else {
				resultado = resultRemesa;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar los bienes, error: ", e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de guardar los bienes");
		}
		if (resultado != null && resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean eliminarBien(Long idRemesa, String codigo) {
		ResultBean resultado = null;
		try {
			if (codigo != null && !codigo.isEmpty()) {
				String idBienes[] = codigo.split("-");
				for (String idBien : idBienes) {
					ResultBean resultCoefPart = servicioParticipacion.eliminarCoefPartRemesaBien(idRemesa, Long.parseLong(idBien));
					if (resultCoefPart == null || resultCoefPart.getError()) {
						ResultBean resultBien = servicioBien.eliminarBienRemesa(Long.parseLong(idBien), idRemesa);
						if (resultBien != null && resultBien.getError()) {
							resultado = resultBien;
							break;
						}
					} else {
						resultado = resultCoefPart;
						break;
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar los bienes, error: ", e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de eliminar los bienes");
		}
		if (resultado != null && resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean generarModelo(BigDecimal numExpediente, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(false);
		try {
			RemesaVO remesaBBDD = remesaDao.getRemesaPorExpediente(numExpediente, true);
			resultado = validarRemesa(remesaBBDD, true);
			if (!resultado.getError()) {
				ResultBean resultModelo = servicioModelo600_601.generarModeloRemesa(remesaBBDD, idUsuario);
				if (resultModelo != null && resultModelo.getError()) {
					resultado = resultModelo;
				}
				remesaBBDD.setEstado(new BigDecimal(EstadoRemesas.Generada.getValorEnum()));
				remesaDao.actualizar(remesaBBDD);
				servicioEvolucionRemesas.guardarEvolucionRemesa(remesaBBDD.getNumExpediente(), new BigDecimal(EstadoRemesas.Validada.getValorEnum()), new BigDecimal(EstadoRemesas.Generada
						.getValorEnum()), idUsuario);
				resultado.addAttachment("tipoRemesa", remesaBBDD.getModelo().getId().getModelo());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar la remesa, error: ", e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de generar la remesa");
		}
		if (resultado != null && resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean validarRemesa(BigDecimal numExpediente, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(false);
		try {
			RemesaVO remesaVO = remesaDao.getRemesaPorExpediente(numExpediente, true);
			resultado = validarRemesa(remesaVO, false);
			if (!resultado.getError()) {
				remesaVO.setEstado(new BigDecimal(EstadoRemesas.Validada.getValorEnum()));
				remesaDao.actualizar(remesaVO);
				servicioEvolucionRemesas.guardarEvolucionRemesa(remesaVO.getNumExpediente(), new BigDecimal(EstadoRemesas.Inicial.getValorEnum()), new BigDecimal(EstadoRemesas.Validada
						.getValorEnum()), idUsuario);
			}
			resultado.addAttachment("tipoRemesa", remesaVO.getModelo().getId().getModelo());
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar la remesa, error : ", e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de validar la remesa.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultBean validarRemesa(RemesaVO remesaVO, Boolean esGenerarModelos) {
		ResultBean resultado = new ResultBean(false);
		if (remesaVO == null) {
			resultado.setError(true);
			resultado.addMensajeALista("No se encuentran datos de la remesa para poder generarla.");
		}
		if (esGenerarModelos) {
			if (remesaVO.getEstado() == null || !EstadoRemesas.Validada.getValorEnum().equals(remesaVO.getEstado().toString())) {
				resultado.setError(true);
				resultado.addMensajeALista("La remesa se debe de encontrar en estado Validada para poder generarla.");
			}
		} else {
			if (remesaVO.getEstado() == null || !EstadoRemesas.Inicial.getValorEnum().equals(remesaVO.getEstado().toString())) {
				resultado.setError(true);
				resultado.addMensajeALista("La remesa se debe de encontrar en estado Inicial para poder validarla.");
			}
		}
		if (remesaVO.getConcepto() == null || remesaVO.getConcepto().getId().getConcepto() == null || remesaVO.getConcepto().getNinter() == null) {
			resultado.setError(true);
			resultado.addMensajeALista("Debe seleccionar un concepto para poder generar la remesa.");
		}

		ResultBean resultValCoefPart = servicioParticipacion.validarGenerarModelosCoefParticipacion(remesaVO);
		if (resultValCoefPart != null && resultValCoefPart.getError()) {
			resultado.setError(true);
			for (String mensaje : resultValCoefPart.getListaMensajes()) {
				resultado.addMensajeALista(mensaje);
			}
		}

		ResultBean resultValBien = servicioBien.validarGenerarModelosBien(remesaVO);
		if (resultValBien != null && resultValBien.getError()) {
			resultado.setError(true);
			for (String mensaje : resultValBien.getListaMensajes()) {
				resultado.addMensajeALista(mensaje);
			}
		}

		if (Modelo.Modelo600.getValorEnum().equals(remesaVO.getModelo().getId().getModelo())) {
			// validar por concepto
			ResultBean resulValRemesaConcept = servicioConcepto.validarRemesa600PorConcepto(remesaVO);
			if (resulValRemesaConcept != null && resulValRemesaConcept.getError()) {
				resultado.setError(true);
				for (String mensaje : resulValRemesaConcept.getListaMensajes()) {
					resultado.addMensajeALista(mensaje);
				}
			}
		}

		if (remesaVO.getTipoDocumento() == null || remesaVO.getTipoDocumento().isEmpty()) {
			resultado.setError(true);
			resultado.addMensajeALista("Debe seleccionar un tipo de documento para poder generar la remesa.");
		} else {
			if (ServicioRemesas.TIPO_DOC_PUBLICO.equals(remesaVO.getTipoDocumento())) {
				if (remesaVO.getNotario() == null || remesaVO.getNotario().getCodigoNotario() == null || remesaVO.getNotario().getCodigoNotario().isEmpty()) {
					resultado.setError(true);
					resultado.addMensajeALista("Para un documento público debe seleccionar un notario para poder generar la remesa.");
				} else if (remesaVO.getNumProtocolo() == null) {
					resultado.setError(true);
					resultado.addMensajeALista("Para un documento público debe rellenar el numero de protocolo para poder generar la remesa.");
				}
			} else {
				if (remesaVO.getNumProtocolo() == null) {
					resultado.setError(true);
					resultado.addMensajeALista("Para un documento no público debe rellenar el numero de protocolo para poder generar la remesa.");
				}
			}
		}

		if (BigDecimal.ONE.equals(remesaVO.getExento()) && (remesaVO.getFundamentoExencion() == null || remesaVO.getFundamentoExencion().getFundamentoExcencion() == null || remesaVO
				.getFundamentoExencion().getFundamentoExcencion().isEmpty())) {
			resultado.setError(true);
			resultado.addMensajeALista("Debe seleccionar un fundamento de exencion si marca la casilla de exento para poder generar la remesa.");
		} else if (BigDecimal.ONE.equals(remesaVO.getNoSujeto()) && (remesaVO.getFundamentoNoSujeccion() == null || remesaVO.getFundamentoNoSujeccion().isEmpty())) {
			resultado.setError(true);
			resultado.addMensajeALista("Debe seleccionar un fundamento de no sujeccion si marca la casilla de no sujeto para poder generar la remesa.");
		}

		if (BigDecimal.ONE.equals(remesaVO.getLiquidacionComplementaria())) {
			if (remesaVO.getNumJustiPrimeraAutoliq() == null || remesaVO.getNumJustiPrimeraAutoliq().isEmpty()) {
				resultado.setError(true);
				resultado.addMensajeALista("Debe de rellenar el numero de justificante de la primera autoliquidación para poder generar la remesa.");
			} else if (remesaVO.getFechaPrimLiquidacion() == null) {
				resultado.setError(true);
				resultado.addMensajeALista("Debe de rellenar la fecha de la primera liquidación para poder generar la remesa.");
			} else if (remesaVO.getNumPrimPresentacion() == null) {
				resultado.setError(true);
				resultado.addMensajeALista("Debe de rellenar el numero de la primera presentación para poder generar la remesa.");
			}
		}

		if (remesaVO.getFechaDevengo() == null) {
			resultado.setError(true);
			resultado.addMensajeALista("Debe de rellenar la fecha devengo para poder generar la remesa.");
		}
		if (remesaVO.getImporteTotal() == null) {
			resultado.setError(true);
			resultado.addMensajeALista("Debe de rellenar el importe total para poder generar la remesa.");
		}
		if (remesaVO.getOficinaLiquidadora() == null || remesaVO.getOficinaLiquidadora().getId().getIdProvincia() == null || remesaVO.getOficinaLiquidadora().getId().getIdProvincia().isEmpty()
				|| remesaVO.getOficinaLiquidadora().getId().getOficinaLiquidadora() == null || remesaVO.getOficinaLiquidadora().getId().getOficinaLiquidadora().isEmpty()) {
			resultado.setError(true);
			resultado.addMensajeALista("Debe de seleccionar la oficina liquidadora para poder generar la remesa.");
		}

		if (remesaVO.getTipoImpuesto() == null || remesaVO.getTipoImpuesto().getMonto() == null) {
			resultado.setError(true);
			resultado.addMensajeALista("No existe el tipo de impuesto poder generar la remesa.");
		}

		ResultBean resultValInterv = servicioIntervinienteModelos.validarGenerarModelosIntervinientes(remesaVO);
		if (resultValInterv != null && resultValInterv.getError()) {
			resultado.setError(true);
			for (String mensaje : resultValInterv.getListaMensajes()) {
				resultado.addMensajeALista(mensaje);
			}
		} else {
			if (!resultado.getError()) {
				if (resultValInterv.getListaMensajes() != null && !resultValInterv.getListaMensajes().isEmpty()) {
					for (String mensaje : resultValInterv.getListaMensajes()) {
						resultado.addMensajeALista(mensaje);
					}
				}
			}
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean cambiarEstado(BigDecimal numExpediente, BigDecimal estadoNuevo, BigDecimal idUsuario, Boolean acciones) {
		ResultBean resultado = new ResultBean(false);
		try {
			resultado = cambiarEstadoConPosibleEvolucion(true, numExpediente, estadoNuevo, idUsuario, false, null, acciones);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado de la remesa, error: ", e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de cambiar el estado de la remesa.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultBean cambiarEstadoConPosibleEvolucion(boolean evolucion, BigDecimal numExpediente, BigDecimal estadoNuevo, BigDecimal idUsuario, boolean notificar, String textoNotificacion,
			Boolean acciones) {
		ResultBean resultado = new ResultBean(false);
		if (estadoNuevo == null) {
			resultado.setError(true);
			resultado.addMensajeALista("No se ha indicado el estado para el cambio de estado");
		} else {
			if (numExpediente != null) {
				RemesaVO remesaBBDD = remesaDao.getRemesaPorExpediente(numExpediente, false);
				BigDecimal estadoAnt = remesaBBDD.getEstado();
				remesaBBDD.setEstado(estadoNuevo);
				remesaDao.actualizar(remesaBBDD);
				if (evolucion) {
					servicioEvolucionRemesas.guardarEvolucionRemesa(remesaBBDD.getNumExpediente(), estadoAnt, estadoNuevo, idUsuario);
				}
				if (notificar) {
					NotificacionDto dto = new NotificacionDto();
					dto.setDescripcion(textoNotificacion);
					if (estadoAnt != null) {
						dto.setEstadoAnt(estadoAnt);
					}
					if (estadoNuevo != null) {
						dto.setEstadoNue(estadoNuevo);
					}
					dto.setIdTramite(remesaBBDD.getNumExpediente());
					if (idUsuario != null) {
						dto.setIdUsuario(idUsuario.longValue());
					}
					if (Modelo.Modelo600.getValorEnum().equals(remesaBBDD.getModelo().getId().getModelo())) {
						dto.setTipoTramite(TipoTramiteModelos.Remesa600.getValorEnum());
					} else if (Modelo.Modelo601.getValorEnum().equals(remesaBBDD.getModelo().getId().getModelo())) {
						dto.setTipoTramite(TipoTramiteModelos.Remesa601.getValorEnum());
					}
					servicioNotificacion.crearNotificacion(dto);
				}
				if (acciones) {
					resultado = accionesCambiarEstadoSinValidacion(remesaBBDD, estadoAnt, estadoNuevo, idUsuario, remesaBBDD.getContrato().getIdContrato());
				}
				if (!resultado.getError()) {
					resultado.addAttachment("tipoRemesa", remesaBBDD.getModelo().getId().getModelo());
				}
			} else {
				resultado.setError(true);
				resultado.addMensajeALista("No Existen datos de la remesa para poder cambiar el estado");
			}
		}
		return resultado;
	}

	private ResultBean accionesCambiarEstadoSinValidacion(RemesaVO remesaBBDD, BigDecimal estadoAnt, BigDecimal estadoNuevo, BigDecimal idUsuario, Long idContrato) {
		if (EstadoRemesas.Generada.getValorEnum().equals(estadoAnt.toString())) {
			if (EstadoRemesas.Anulada.getValorEnum().equals(estadoNuevo.toString())) {
				return servicioModelo600_601.cambiarEstadoModelosRemesa(remesaBBDD.getIdRemesa(), new BigDecimal(EstadoModelos.Anulado.getValorEnum()), idUsuario);
			} else {
				return servicioModelo600_601.eliminarModelosRemesa(remesaBBDD.getIdRemesa());
			}
		} else if (EstadoRemesas.Anulada.getValorEnum().equals(estadoAnt.toString())) {
			if (EstadoRemesas.Generada.getValorEnum().equals(estadoNuevo.toString())) {
				return servicioModelo600_601.cambiarEstadoModelosRemesa(remesaBBDD.getIdRemesa(), new BigDecimal(EstadoModelos.Autoliquidable.getValorEnum()), idUsuario);
			} else {
				return servicioModelo600_601.eliminarModelosRemesa(remesaBBDD.getIdRemesa());
			}
		} else {
			if (EstadoRemesas.Generada.getValorEnum().equals(estadoNuevo.toString())) {
				return servicioModelo600_601.generarModeloRemesa(remesaBBDD, idUsuario);
			}
		}
		return new ResultBean(false);
	}

	@Override
	public ResultBean comprobarEstadosModelosGenerarPorBloque(String codigos) {
		ResultBean resultado = new ResultBean(false);
		try {
			if (codigos != null && !codigos.isEmpty()) {
				String[] numExpedientes = codigos.split("-");
				for (String numExp : numExpedientes) {
					BigDecimal estadoModelo = servicioModelo600_601.getEstadoModelo(new BigDecimal(numExp));
					if (estadoModelo != null) {
						if (!EstadoModelos.Autoliquidable.getValorEnum().equals(estadoModelo.toString())) {
							resultado.setError(true);
							break;
						}
					} else {
						resultado.setError(true);
						break;
					}
				}
			} else {
				resultado.setError(true);
				resultado.addMensajeALista("Debe seleccionar algún modelo para comprobar su estado.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar los estado de los modelos, error: ", e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de comprobar los estado de los modelos.");
		}
		return resultado;
	}

	@Override
	public ResultBean presentarModelos(String codigos, DatosBancariosFavoritosDto datosBancariosDto, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(false);
		try {
			if (codigos != null && !codigos.isEmpty()) {
				if (datosBancariosDto != null) {
					String[] numExpedientes = codigos.split("-");
					List<String> listaErrores = new ArrayList<String>();
					List<String> listaOk = new ArrayList<String>();
					int numPresOk = 0;
					int numPresError = 0;
					for (String numExp : numExpedientes) {
						ResultBean resultPresentar = servicioModelo600_601.presentar(new BigDecimal(numExp), datosBancariosDto, idUsuario, null, null);
						if (resultPresentar.getError()) {
							numPresError++;
							listaErrores.add(resultPresentar.getListaMensajes().get(0));
						} else {
							numPresOk++;
							listaOk.add("Modelo " + numExp + " presentado correctamente.");
						}
					}
					resultado.addAttachment("numOk", numPresOk);
					resultado.addAttachment("numErrores", numPresError);
					resultado.addAttachment("listaErrores", listaErrores);
					resultado.addAttachment("listaOk", listaOk);
				} else {
					resultado.setError(true);
					resultado.addMensajeALista("Debe rellenar los datos bancarios para poder presentar los modelos.");
				}
			} else {
				resultado.setError(true);
				resultado.addMensajeALista("Debe seleccionar algún modelo para presentar.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de presentar los modelos seleccionados de la remesa, error: ", e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de presentar los modelos seleccionados de la remesa");
		}
		return resultado;
	}

	@Override
	public ResultBean presentarRemesas(RemesaVO remesaBBDD, DatosBancariosFavoritosDto datosBancarios, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(false);
		try {
			if (datosBancarios != null) {
				List<String> listaErrores = new ArrayList<String>();
				List<String> listaOk = new ArrayList<String>();
				int numOkMD = 0;
				int numErrorMD = 0;
				ResultBean resultValidarRm = validarRemesaPresentacion(remesaBBDD);
				if (!resultValidarRm.getError()) {
					for (Modelo600_601VO modelo600_601vo : remesaBBDD.getListaModelos()) {
						ResultBean resultPresentarMd = servicioModelo600_601.presentarModeloRemesasBloque(modelo600_601vo, datosBancarios, idUsuario);
						if (resultPresentarMd.getError()) {
							numErrorMD++;
							listaErrores.add("Para la remesa: " + remesaBBDD.getNumExpediente() + " ha sucedido el siguiente fallo: " + resultPresentarMd.getListaMensajes().get(0));
						} else {
							numOkMD++;
							listaOk.add("Remesa " + remesaBBDD.getNumExpediente() + ": " + resultPresentarMd.getListaMensajes().get(0));
						}
					}
					resultado.addAttachment("numOkMD", numOkMD);
					resultado.addAttachment("numErrorMD", numErrorMD);
					resultado.addAttachment("listaOk", listaOk);
					resultado.addAttachment("listaErrores", listaErrores);
				} else {
					resultado.setError(true);
					resultado.addMensajeALista(resultValidarRm.getListaMensajes().get(0));
				}
				resultado.addAttachment("tipoRemesa", remesaBBDD.getModelo().getId().getModelo());
			} else {
				resultado.setError(true);
				resultado.addMensajeALista("Debe rellenar los datos bancarios para poder presentar una remesa.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de presentar la remesa, error: ", e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de presentar la remesa.");
		}
		return resultado;
	}

	private ResultBean validarRemesaPresentacion(RemesaVO remesaVO) {
		ResultBean resultado = new ResultBean(false);
		if (remesaVO == null) {
			resultado.setError(true);
			resultado.addMensajeALista("No existen datos de la remesa para presentar.");
			return resultado;
		}
		if (!EstadoRemesas.Generada.getValorEnum().equals(remesaVO.getEstado().toString())) {
			resultado.setError(true);
			resultado.addMensajeALista("La remesa: " + remesaVO.getNumExpediente() + ", no se encuentra en estado 'Generada', por lo que no se puede presentar.");
			return resultado;
		}
		if (remesaVO.getListaModelos() == null || remesaVO.getListaModelos().isEmpty()) {
			resultado.setError(true);
			resultado.addMensajeALista("La remesa: " + remesaVO.getNumExpediente() + ", no tiene modelos generados para poder presentarlos.");
			return resultado;
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultBean validarContratosPresentacionEnBloque(String codSeleccionados, BigDecimal idContrato) {
		ResultBean resultado = new ResultBean(false);
		try {
			if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
				String[] numExpedientes = codSeleccionados.split("-");
				for (String numExp : numExpedientes) {
					RemesaVO remesaBBDD = remesaDao.getRemesaPorExpediente(new BigDecimal(numExp), false);
					if (remesaBBDD != null) {
						if (remesaBBDD.getContrato() != null && remesaBBDD.getContrato().getIdContrato() != null) {
							if (idContrato.longValue() != remesaBBDD.getContrato().getIdContrato()) {
								resultado.setError(true);
								resultado.addMensajeALista("Para poder presentar en bloque todos las remesas deben de ser del mismo contrato.");
								break;
							}
						} else {
							resultado.setError(true);
							resultado.addMensajeALista("Para la remesa: " + numExp + " no existen datos de su contrato.");
							break;
						}
					} else {
						resultado.setError(true);
						resultado.addMensajeALista("Para la remesa: " + numExp + " no existen datos.");
						break;
					}
				}

			} else {
				resultado.setError(true);
				resultado.addMensajeALista("Debe de seleccionar alguna remesa para presentar.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar las remesas en bloque para presentarlas, error: ", e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de validar las remesas en bloque para presentarlas.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public List<RemesaDto> getListaRemesasPorExpedientesYContrato(String codSeleccionados, Long idContrato) {
		try {
			if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
				List<RemesaVO> lista = remesaDao.getListaRemesasPorExpedientesYContrato(utiles.convertirStringArrayToBigDecimal(codSeleccionados.split("-")), idContrato);
				if (lista != null && !lista.isEmpty()) {
					return conversor.transform(lista, RemesaDto.class);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la lista de remesas 600/601 por sus expedientes, error: ", e);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional(readOnly = true)
	public ResultBean validarRemesaImpresion(BigDecimal numExpediente) {
		ResultBean resultado = new ResultBean(false);
		try {
			if (numExpediente != null) {
				RemesaVO remesaBBDD = remesaDao.getRemesaPorExpediente(numExpediente, true);
				if (remesaBBDD != null) {
					if (!EstadoRemesas.Generada.getValorEnum().equals(remesaBBDD.getEstado().toString())) {
						resultado.setError(true);
						resultado.addMensajeALista("La remesa: " + numExpediente + " se debe de encontrar en estado '" + EstadoRemesas.Generada.getNombreEnum()
								+ "' para poder realizar su impresión.");
						return resultado;
					}
					if (remesaBBDD.getListaModelos() != null && !remesaBBDD.getListaModelos().isEmpty()) {
						Boolean existeModOk = false;
						for (Modelo600_601VO modelo600_601vo : remesaBBDD.getListaModelos()) {
							if (modelo600_601vo.getListaResultadoModelo() != null && !modelo600_601vo.getListaResultadoModelo().isEmpty()) {
								for (ResultadoModelo600601VO resultadoModelo600601VO : modelo600_601vo.getListaResultadoModelo()) {
									if (ErroresWSModelo600601.Error000.getValorEnum().equals(resultadoModelo600601VO.getCodigoResultado())) {
										existeModOk = true;
										break;
									}
								}
							}
						}
						if (!existeModOk) {
							resultado.setError(true);
							resultado.addMensajeALista("La remesa: " + numExpediente + " no tiene modelos con resultados correctos para poder realizar la impresión de sus documentos.");
						}
					} else {
						resultado.setError(true);
						resultado.addMensajeALista("La remesa: " + numExpediente + " no tiene modelos generados para su impresión.");
					}
				} else {
					resultado.setError(true);
					resultado.addMensajeALista("No existen datos para la remesa: " + numExpediente + ".");
				}
			} else {
				resultado.setError(true);
				resultado.addMensajeALista("Debe indicar un numero de expediente valido para comprobar.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar la remesa 600/601 para su impresión, error: ", e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de validar la remesa 600/601 para su impresión.");
		}
		return resultado;
	}

	@Override
	public IntervinienteModelosDto getPresentadorContrato(ContratoDto contrato) {
		IntervinienteModelosDto intervinienteModelosDto = null;
		try {
			intervinienteModelosDto = getPresentador(contrato.getIdContrato());
			DireccionDto direccion = new DireccionDto();
			direccion.setCodPostal(contrato.getCodPostal());
			direccion.setEscalera(contrato.getEscalera());
			direccion.setIdMunicipio(contrato.getIdMunicipio());
			direccion.setIdProvincia(contrato.getIdProvincia());
			direccion.setIdTipoVia(contrato.getIdTipoVia());
			// direccion.setNombreMunicipio(contrato.getMunicipioDesc());
			direccion.setNombreProvincia(contrato.getContratoProvincia());
			direccion.setNombreVia(contrato.getVia());
			direccion.setNumero(contrato.getNumero());
			direccion.setPuerta(contrato.getPuerta());
			direccion.setPlanta(contrato.getPiso());
			direccion.setTipoViaDescripcion(contrato.getVia());
			intervinienteModelosDto.setDireccion(direccion);
			// PersonaDto persona = new PersonaDto();
			// persona.setApellido1RazonSocial(contrato.getColegiadoDto().getUsuario().getApellido1());
			//// if(contrato.getRazonSocial().trim().isEmpty()){
			//// persona.setApellido1RazonSocial(contrato.getApellido1());
			//// }
			// persona.setApellido2(contrato.getColegiadoDto().getUsuario().getApellido2());
			// persona.setNif(contrato.getColegiadoDto().getUsuario().getNif());
			// persona.setNombre(contrato.getColegiadoDto().getUsuario().getNombre());
			// persona.setTelefonos(String.valueOf(contrato.getTelefono()));
			// //persona.setFechaNacimiento(contrato.getColegiadoDto().getUsuario().getf);
			// intervinienteModelosDto.setPersona(persona);
			intervinienteModelosDto.setTipoInterviniente("Presentador");
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el presentador como el colegiado, error: ", e);
		}
		return intervinienteModelosDto;
	}

}
