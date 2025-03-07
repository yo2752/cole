package org.gestoresmadrid.oegam2comun.creditos.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.general.model.dao.CreditoDao;
import org.gestoresmadrid.core.general.model.dao.CreditoProcedureDao;
import org.gestoresmadrid.core.general.model.dao.TipoCreditoDao;
import org.gestoresmadrid.core.general.model.dao.TipoCreditoTramiteDao;
import org.gestoresmadrid.core.general.model.procedure.bean.ValidacionCredito;
import org.gestoresmadrid.core.general.model.vo.CreditoPK;
import org.gestoresmadrid.core.general.model.vo.CreditoVO;
import org.gestoresmadrid.core.general.model.vo.TipoCreditoTramiteVO;
import org.gestoresmadrid.core.general.model.vo.TipoCreditoVO;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.historicocreditos.model.vo.CreditoFacturadoVO;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCredito;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCreditoFacturado;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioHistoricoCredito;
import org.gestoresmadrid.oegam2comun.creditos.view.dto.CreditoDto;
import org.gestoresmadrid.oegam2comun.impresion.view.dto.ImprimirTramiteTraficoDto;
import org.gestoresmadrid.oegam2comun.model.service.ServicioTipoTramite;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import general.utiles.Constantes;
import trafico.utiles.enumerados.TipoTransferencia;
import trafico.utiles.enumerados.TipoTransferenciaActual;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
@Transactional
public class ServicioCreditoImpl implements ServicioCredito {

	private static final long serialVersionUID = -2123859402048652121L;

	private static ILoggerOegam log = LoggerOegam.getLogger(ServicioCreditoImpl.class);

	private static String INCREMENTAL = "I";
	private static String DECREMENTAL = "D";

	@Autowired
	private ServicioCreditoFacturado servicioCreditoFacturado;

	@Autowired
	private CreditoDao creditoDao;

	@Autowired
	private ServicioHistoricoCredito servicioHistoricoCredito;

	@Autowired
	private ServicioTipoTramite servicioTipoTramite;

	@Autowired
	private TipoCreditoTramiteDao tipoCreditoTramiteDao;

	@Autowired
	private TipoCreditoDao tipoCreditoDao;

	@Autowired
	private CreditoProcedureDao validacionCreditoDao;

	@Autowired
	private Conversor conversor;

	@Autowired
	Utiles utiles;

	@Override
	public ResultBean validarCreditos(String tipoTramite, BigDecimal idContrato, BigDecimal cantidadCreditos) {
		ResultBean respuesta = new ResultBean();
		ValidacionCredito vTramite = validacionCreditoDao.validarCreditos(idContrato, tipoTramite, cantidadCreditos);
		if (vTramite != null && vTramite.getSqlerrm().equals("Correcto")) {
			respuesta.setError(false);
			respuesta.addAttachment(CREDITOS_DISPONIBLES, vTramite.getCreditosDisponibles());
			respuesta.addAttachment(CREDITOS_TOTALES, vTramite.getCreditos());
			respuesta.addAttachment(CREDITOS_BLOQUEADOS, vTramite.getCreditos().subtract(vTramite.getCreditosDisponibles()));
		} else {
			respuesta.setError(true);
			if (vTramite != null) {
				respuesta.addMensajeALista(vTramite.getSqlerrm());
			} else {
				respuesta.addMensajeALista("Error al validar");
			}
		}
		return respuesta;
	}

	@Override
	public ResultBean descontarCreditos(String tipoTramite, BigDecimal idContrato, BigDecimal cantidadCreditos, BigDecimal idUsuario, ConceptoCreditoFacturado concepto, String... tramites) {
		ResultBean result = new ResultBean();
		TipoCreditoTramiteVO tipoCreditoTramite = tipoCreditoTramiteDao.getTipoCreditoTramite(tipoTramite);
		if (tipoCreditoTramite != null && tipoCreditoTramite.getTipoCredito() != null && !"".equals(tipoCreditoTramite.getTipoCredito())) {
			result = cargarCreditos(tipoTramite, tipoCreditoTramite.getTipoCredito(), idContrato, cantidadCreditos, idUsuario, concepto, false, tramites);
		} else {
			result.setError(true);
			result.setMensaje("No existe tipo de crédito trámite para ese tipo de trámite");
		}
		return result;
	}
	
	@Override
	public ResultBean descontarCreditosAM(String tipoTramite, BigDecimal idContrato, BigDecimal cantidadCreditos, BigDecimal idUsuario, ConceptoCreditoFacturado concepto, String... tramites) {
		ResultBean result = new ResultBean();
		TipoCreditoTramiteVO tipoCreditoTramite = tipoCreditoTramiteDao.getTipoCreditoTramite(tipoTramite);
		if (tipoCreditoTramite != null && tipoCreditoTramite.getTipoCredito() != null && !"".equals(tipoCreditoTramite.getTipoCredito())) {
			result = cargarCreditosAM(tipoTramite, tipoCreditoTramite.getTipoCredito(), idContrato, cantidadCreditos, idUsuario, concepto, tramites);
		} else {
			result.setError(true);
			result.setMensaje("No existe tipo de crédito trámite para ese tipo de trámite");
		}
		return result;
	}

	@Override
	public ResultBean devolverCreditos(String tipoTramite, BigDecimal idContrato, int cantidadCreditos, BigDecimal idUsuario, ConceptoCreditoFacturado concepto, String... tramites) {
		ResultBean result = new ResultBean();
		TipoCreditoTramiteVO tipoCreditoTramite = tipoCreditoTramiteDao.getTipoCreditoTramite(tipoTramite);
		if (tipoCreditoTramite != null && tipoCreditoTramite.getTipoCredito() != null && !"".equals(tipoCreditoTramite.getTipoCredito())) {
			result = cargarCreditos(tipoTramite, tipoCreditoTramite.getTipoCredito(), idContrato, new BigDecimal(-cantidadCreditos), idUsuario, concepto, false, tramites);
		} else {
			result.setError(true);
			result.addMensajeALista("No existe tipo de crédito trámite para ese tipo de trámite");
		}

		return result;
	}

	@Override
	public ResultBean cargoCreditos(String tipoCredito, BigDecimal idContrato, int cantidadCreditos, BigDecimal idUsuario) {
		return cargarCreditos(null, tipoCredito, idContrato, new BigDecimal(-cantidadCreditos), idUsuario, null, true);
	}

	private ResultBean cargarCreditos(String tipoTramite, String tipoCredito, BigDecimal idContrato, BigDecimal cantidadCreditos, BigDecimal idUsuario, ConceptoCreditoFacturado concepto,
			boolean cargarCAU, String... tramites) {
		ResultBean result = new ResultBean();
		boolean historicos = false;

		TipoCreditoVO tipoCreditoVO = tipoCreditoDao.getTipoCredito(tipoCredito);
		if (tipoCreditoVO != null && tipoCreditoVO.getTipoCredito() != null && !tipoCreditoVO.getTipoCredito().isEmpty()) {
			CreditoVO credito = creditoDao.getCredito(tipoCreditoVO.getTipoCredito(), idContrato.longValue());
			if("CT11".equalsIgnoreCase(tipoCreditoVO.getTipoCredito())){
				if(credito == null) {
					credito = new CreditoVO();
					CreditoPK pk = new CreditoPK();
					pk.setIdContrato(idContrato.longValue());
					pk.setTipoCredito(tipoCredito);
					credito.setId(pk);
					credito.setCreditos(BigDecimal.ZERO);
					creditoDao.guardarOActualizar(credito);
					result.setError(true);
					result.addMensajeALista("Debe adquirir creditos para poder imprimir el listado de Canjes.");
				}else {
					if(DECREMENTAL.equals(tipoCreditoVO.getIncreDecre()) && cantidadCreditos.intValue() <= credito.getCreditos().intValue()) {
						int creditosResta = credito.getCreditos().intValue() - cantidadCreditos.intValue();
						credito.setCreditos(new BigDecimal(creditosResta));
						creditoDao.actualizar(credito);
						servicioCreditoFacturado.anotarGasto(utiles.convertirBigDecimalAInteger(cantidadCreditos), ConceptoCreditoFacturado.TCAN, idContrato.longValue(), TipoTramiteTrafico.IMPRESION_CANJES.getValorEnum(), tramites);
						historicos = true;
					}else {
						result.setError(true);
						result.addMensajeALista("No tiene créditos suficientes.");
					}
				}
			}else {
				if (credito != null) {
					if (INCREMENTAL.equals(tipoCreditoVO.getIncreDecre())) {
						int creditosSuma = credito.getCreditos().intValue() + cantidadCreditos.intValue();
						credito.setCreditos(new BigDecimal(creditosSuma));
					} else if (DECREMENTAL.equals(tipoCreditoVO.getIncreDecre())) {
						int creditosResta = credito.getCreditos().intValue() - cantidadCreditos.intValue();
						credito.setCreditos(new BigDecimal(creditosResta));
					}
					creditoDao.actualizar(credito);
					historicos = true;
					if (historicos) {
						try {
							if (tramites != null) {
								servicioCreditoFacturado.anotarGasto(utiles.convertirBigDecimalAInteger(cantidadCreditos), concepto, idContrato.longValue(), tipoTramite, tramites);
							}
						} catch (Exception e) {
							log.error("Se ha producido un error al guardar en créditos facturados", e);
						}
						try {
							BigDecimal creditosRestados = null;
							BigDecimal creditosAniadidos = null;
							if (!cargarCAU) {
								if (INCREMENTAL.equals(tipoCreditoVO.getIncreDecre())) {
									creditosAniadidos = cantidadCreditos;
								} else if (DECREMENTAL.equals(tipoCreditoVO.getIncreDecre())) {
									creditosRestados = cantidadCreditos;
								}
							} else{
								if (cantidadCreditos.intValue() < 0) {
									creditosAniadidos = new BigDecimal(-cantidadCreditos.intValue());
								} else {
									creditosRestados = cantidadCreditos;
								}
							}
							servicioHistoricoCredito.anotarGasto(tipoCreditoVO.getTipoCredito(), idContrato.longValue(), idUsuario, creditosAniadidos, creditosRestados);
						} catch (Exception e) {
							log.error("Se ha producido un error al guardar historico de creditos", e);
						}
					}
				} else {
					if (INCREMENTAL.equals(tipoCreditoVO.getIncreDecre()) || (DECREMENTAL.equals(tipoCreditoVO.getIncreDecre()) && cantidadCreditos.intValue() < 0)) {
						credito = new CreditoVO();
						CreditoPK pk = new CreditoPK();
						if (INCREMENTAL.equals(tipoCreditoVO.getIncreDecre())) {
							credito.setCreditos(cantidadCreditos);
						} else {
							credito.setCreditos(new BigDecimal(-cantidadCreditos.intValue()));
						}
						pk.setIdContrato(idContrato.longValue());
						pk.setTipoCredito(tipoCreditoVO.getTipoCredito());
						credito.setId(pk);
						creditoDao.guardar(credito);
						historicos = true;
					} else {
						result.setError(true);
						result.addMensajeALista("No tiene créditos suficientes.");
					}
				}
			}
			if (historicos) {
				try {
					if (tramites != null) {
						servicioCreditoFacturado.anotarGasto(utiles.convertirBigDecimalAInteger(cantidadCreditos), concepto, idContrato.longValue(), tipoTramite, tramites);
					}
				} catch (Exception e) {
					log.error("Se ha producido un error al guardar en créditos facturados", e);
				}
				try {
					BigDecimal creditosRestados = null;
					BigDecimal creditosAniadidos = null;
					if (!cargarCAU) {
						if (INCREMENTAL.equals(tipoCreditoVO.getIncreDecre())) {
							creditosAniadidos = cantidadCreditos;
						} else if (DECREMENTAL.equals(tipoCreditoVO.getIncreDecre())) {
							creditosRestados = cantidadCreditos;
						}
					} else{
						if (cantidadCreditos.intValue() < 0) {
							creditosAniadidos = new BigDecimal(-cantidadCreditos.intValue());
						} else {
							creditosRestados = cantidadCreditos;
						}
					}
					servicioHistoricoCredito.anotarGasto(tipoCreditoVO.getTipoCredito(), idContrato.longValue(), idUsuario, creditosAniadidos, creditosRestados);
				} catch (Exception e) {
					log.error("Se ha producido un error al guardar historico de creditos", e);
				}
			}
			
		}
		return result;
	}
	
	private ResultBean cargarCreditosAM(String tipoTramite, String tipoCredito, BigDecimal idContrato, BigDecimal cantidadCreditos, BigDecimal idUsuario, 
			ConceptoCreditoFacturado concepto, String... tramites)  {
		ResultBean result = new ResultBean();
		boolean historicos = false;

		TipoCreditoVO tipoCreditoVO = tipoCreditoDao.getTipoCredito(tipoCredito);
		if (tipoCreditoVO != null && StringUtils.isNotBlank(tipoCreditoVO.getTipoCredito())) {
			CreditoVO credito = creditoDao.getCredito(tipoCreditoVO.getTipoCredito(), idContrato.longValue());

			if (credito != null) {
				if (INCREMENTAL.equals(tipoCreditoVO.getIncreDecre())) {
					//int creditosSuma = credito.getCreditos().intValue() + cantidadCreditos.intValue();
					credito.setCreditos(credito.getCreditos().add(cantidadCreditos));
				} else if (DECREMENTAL.equals(tipoCreditoVO.getIncreDecre())) {
					//int creditosResta = credito.getCreditos().intValue() - cantidadCreditos.intValue();
					credito.setCreditos(credito.getCreditos().subtract(cantidadCreditos));
				}
				creditoDao.actualizar(credito);
				historicos = true;
			} else {
				if (INCREMENTAL.equals(tipoCreditoVO.getIncreDecre()) || (DECREMENTAL.equals(tipoCreditoVO.getIncreDecre()) && cantidadCreditos.intValue() < 0)) {
					credito = new CreditoVO();
					CreditoPK pk = new CreditoPK();
					if (INCREMENTAL.equals(tipoCreditoVO.getIncreDecre())) {
						credito.setCreditos(cantidadCreditos);
					} else {
						credito.setCreditos(new BigDecimal(-cantidadCreditos.intValue()));
					}
					pk.setIdContrato(idContrato.longValue());
					pk.setTipoCredito(tipoCreditoVO.getTipoCredito());
					credito.setId(pk);
					creditoDao.guardar(credito);
					historicos = true;
				} else {
					result.setError(true);
					result.addMensajeALista("No tiene créditos suficientes.");
				}
			}
			if (historicos) {
				try {
					if (tramites != null) {
						servicioCreditoFacturado.anotarGasto(utiles.convertirBigDecimalAInteger(cantidadCreditos), concepto, idContrato.longValue(), tipoTramite, tramites);
					}
				} catch (Exception ex) {
					log.error("Se ha producido un error al anotar gasto: ", ex);
					result.setError(true);
					result.addMensajeALista("Se ha producido un error al anotar gasto.");
				}
				
				try {
					BigDecimal creditosRestados = null;
					BigDecimal creditosAniadidos = null;
					/*if (INCREMENTAL.equals(tipoCreditoVO.getIncreDecre())) {
						creditosAniadidos = new BigDecimal(-cantidadCreditos.intValue());
					} else if (DECREMENTAL.equals(tipoCreditoVO.getIncreDecre())) {
						creditosRestados = cantidadCreditos;
					}*/
					if (cantidadCreditos.intValue() < 0) {
						creditosAniadidos = new BigDecimal(-cantidadCreditos.intValue());
					} else {
						creditosRestados = cantidadCreditos;
					}
					servicioHistoricoCredito.anotarGasto(tipoCreditoVO.getTipoCredito(), idContrato.longValue(),idUsuario, creditosAniadidos, creditosRestados);
				} catch (Exception ex) {
					log.error("Se ha producido un error al guardar historico de creditos: ", ex);
					result.setError(true);
					result.addMensajeALista("Se ha producido un error al guardar historico de creditos.");
				}
			}
		}
		return result;
	}
	

	@Override
	public TipoTramiteTrafico getTipoCreditosACobrar(TipoTramiteTrafico tipoTramite, TipoTransferenciaActual tipoTransferenciaActual, TipoTransferencia tipoTransferencia) {
		TipoTramiteTrafico tipoTramiteCreditos = tipoTramite;
		// Si es una transmisión, comprobamos si cobra créditos de transmisión o de baja.
		if (tipoTramiteCreditos == TipoTramiteTrafico.Transmision && tipoTransferenciaActual == TipoTransferenciaActual.tipo2) {
			tipoTramiteCreditos = TipoTramiteTrafico.Baja;
		} else if (tipoTramiteCreditos == TipoTramiteTrafico.TransmisionElectronica && (tipoTransferencia == TipoTransferencia.tipo4 || tipoTransferencia == TipoTransferencia.tipo5)) {
			tipoTramiteCreditos = TipoTramiteTrafico.Baja;
		}
		return tipoTramiteCreditos;
	}

	@Override
	public ResultBean descontarCreditosPorExpedientes(String[] numsExpedientes, ImprimirTramiteTraficoDto imprimirTramiteTrafico, BigDecimal idContrato, BigDecimal idUsuario) {
		TipoTramiteTrafico tipoTramiteCreditos = getTipoCreditosACobrar(imprimirTramiteTrafico.getResultBeanImprimir().getTipoTramite(), imprimirTramiteTrafico.getResultBeanImprimir()
				.getTipoTransmisionActual(), imprimirTramiteTrafico.getResultBeanImprimir().getTipoTransmision());

		ConceptoCreditoFacturado conceptoCreditoFacturado = servicioCreditoFacturado.getConceptoCreditoImpresion(tipoTramiteCreditos);

		return descontarCreditos(tipoTramiteCreditos.toString(), idContrato, new BigDecimal(numsExpedientes.length), idUsuario, conceptoCreditoFacturado, numsExpedientes);
	}

	@Override
	public int cantidadCreditosAnotados(String idTramite, ConceptoCreditoFacturado conceptoGasto, ConceptoCreditoFacturado conceptoDevolucion) {
		int totalCreditos = 0;
		List<CreditoFacturadoVO> creditos = servicioCreditoFacturado.movimientoCreditosTramite(idTramite);
		for (CreditoFacturadoVO creditoFacturado : creditos) {
			if (conceptoGasto == null || conceptoGasto.equals(creditoFacturado.getConcepto())) {
				totalCreditos++;
			} else if (conceptoDevolucion != null && conceptoDevolucion.equals(creditoFacturado.getConcepto())) {
				totalCreditos--;
			}
		}
		return totalCreditos;
	}

	@Override
	public String obtenerDescripcionTipoCredito(String tipoCredito) {
		TipoCreditoVO tipoCreditoVO = tipoCreditoDao.getTipoCredito(tipoCredito);
		if (tipoCreditoVO != null) {
			return tipoCreditoVO.getDescripcion();
		}
		return null;
	}

	@Override
	public List<CreditoDto> busquedaCreditosPorContrato(Long idContrato) {
		List<CreditoDto> creditos = new ArrayList<CreditoDto>();
		List<String> listaTipos = servicioTipoTramite.busquedaTiposCreditosPorContrato(idContrato);
		if (listaTipos != null && !listaTipos.isEmpty()) {
			listaTipos = eliminarDuplicados(listaTipos);
			for (String tipo : listaTipos) {
				CreditoVO creditoVO = creditoDao.getCredito(tipo, idContrato);
				CreditoDto creditoDto = new CreditoDto();
				if (creditoVO == null) {
					creditoDto.setCreditos(BigDecimal.ZERO);
					creditoDto.setIdContrato(idContrato);
					creditoDto.setTipoCredito(tipo);
				} else {
					creditoDto = conversor.transform(creditoVO, CreditoDto.class);
				}
				String descripcion = obtenerDescripcionTipoCredito(creditoDto.getTipoCredito());
				creditoDto.setDescripcionTipoCredito(descripcion);
				creditos.add(creditoDto);
			}
		}
		return creditos;
	}

	private List<String> eliminarDuplicados(List<String> listaDuplicados) {
		List<String> listaNoDuplicados = new ArrayList<String>();
		for (String tipo : listaDuplicados) {
			if (!listaNoDuplicados.contains(tipo)) {
				listaNoDuplicados.add(tipo);
			}
		}
		return listaNoDuplicados;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<TipoCreditoTramiteVO> getListaTiposCreditosTramite(String tipoCredito) {
		return tipoCreditoTramiteDao.getListaTiposCreditosTramite(tipoCredito);
	}
}