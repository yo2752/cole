package org.gestoresmadrid.oegamComun.credito.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.enumerados.ProcesosAmEnum;
import org.gestoresmadrid.core.general.model.dao.CreditoDao;
import org.gestoresmadrid.core.general.model.dao.HistoricoCreditoDao;
import org.gestoresmadrid.core.general.model.dao.TipoCreditoDao;
import org.gestoresmadrid.core.general.model.dao.TipoCreditoTramiteDao;
import org.gestoresmadrid.core.general.model.vo.CreditoPK;
import org.gestoresmadrid.core.general.model.vo.CreditoVO;
import org.gestoresmadrid.core.general.model.vo.HistoricoCreditoVO;
import org.gestoresmadrid.core.general.model.vo.TipoCreditoTramiteVO;
import org.gestoresmadrid.core.general.model.vo.TipoCreditoVO;
import org.gestoresmadrid.core.general.model.vo.TipoTramiteVO;
import org.gestoresmadrid.core.historicocreditos.model.dao.CreditoFacturadoDao;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.historicocreditos.model.vo.CreditoFacturadoTramiteVO;
import org.gestoresmadrid.core.historicocreditos.model.vo.CreditoFacturadoVO;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTransferencia;
import org.gestoresmadrid.oegamComun.cola.service.ServicioComunCola;
import org.gestoresmadrid.oegamComun.credito.service.ServicioComunCredito;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
@Transactional
public class ServicioComunCreditoImpl implements ServicioComunCredito {

	private static final long serialVersionUID = -3921693739194025788L;

	private static ILoggerOegam log = LoggerOegam.getLogger(ServicioComunCreditoImpl.class);

	@Autowired
	CreditoDao creditoDao;

	@Autowired
	TipoCreditoTramiteDao tipoCreditoTramiteDao;

	@Autowired
	CreditoFacturadoDao creditoFacturadoDao;

	@Autowired
	HistoricoCreditoDao historicoCreditoDao;

	@Autowired
	ServicioComunCola servicioComunCola;

	@Autowired
	TipoCreditoDao tipoCreditoDao;

	@Override
	@Transactional(readOnly = true)
	public String getTipoTramitePorTipoCredito(String tipoCredito) {
		try {
			TipoCreditoTramiteVO tipoCreditoTramiteVO = tipoCreditoTramiteDao.getTipoCreditoTramitePorTipoCredito(tipoCredito);
			if(tipoCreditoTramiteVO != null){
				return tipoCreditoTramiteVO.getTipoTramite();
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el tipo tramite del tipoCredito: " + tipoCredito + ", error: ",e);
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ResultadoBean creditosDisponiblesComprobandoPendientes(Long numCreditos, Long idContrato, String proceso, String tipoTramite) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			TipoCreditoTramiteVO tipoCreditoTramite = tipoCreditoTramiteDao.getTipoCreditoTramite(tipoTramite);
			if (tipoCreditoTramite != null) {
				CreditoVO credito = creditoDao.getCreditoConTipoCredito(tipoCreditoTramite.getTipoCredito(), idContrato);
				if (credito != null && credito.getTipoCreditoVO() != null && DECREMENTAL.equals(credito.getTipoCreditoVO().getIncreDecre())) {
					int numColasProceso = servicioComunCola.getNumColasProcesoPorContrato(proceso, idContrato);
					Long totalCreditosDesc = numCreditos;
					if (numColasProceso != 0) {
						totalCreditosDesc += numColasProceso;
					}
					if (credito.getCreditos().longValue() < totalCreditosDesc) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("No tiene creditos disponibles para realizar esta accion.");
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Este tipo credito no es decremental");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existe tipo de credito tramite para ese tipo de tramite");
			}
		} catch (Exception e) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora e verificar los creditos disponibles.");
			log.error("Ha sucedido un error a la hora e verificar los creditos disponibles para el contrato: " + idContrato + ", error: ", e);
		}
		return resultado;
	}

	@Override
	public String getTipoCreditosACobrar(String tipoTramite, String tipoTransferencia) {
		if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tipoTramite) && (TipoTransferencia.tipo4.getValorEnum().equals(tipoTransferencia) || TipoTransferencia.tipo5.getValorEnum()
				.equals(tipoTransferencia))) {
			tipoTramite = TipoTramiteTrafico.Baja.getValorEnum();
		}
		return tipoTramite;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultadoBean creditosDisponibles(Long numCreditos, Long idContrato, String tipoTramite) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			TipoCreditoTramiteVO tipoCreditoTramite = tipoCreditoTramiteDao.getTipoCreditoTramite(tipoTramite);
			if (tipoCreditoTramite != null) {
				CreditoVO credito = creditoDao.getCreditoConTipoCredito(tipoCreditoTramite.getTipoCredito(), idContrato);
				if (credito != null && credito.getTipoCreditoVO() != null && DECREMENTAL.equals(credito.getTipoCreditoVO().getIncreDecre())) {
					if (credito.getCreditos().longValue() < numCreditos) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("No tiene creditos disponibles para realizar esta accion el contrato: " + idContrato);
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No existe creditos para ese tipo de tramite");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existe tipo de credito tramite para ese tipo de tramite");
			}
		} catch (Exception e) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora e verificar los creditos disponibles.");
			log.error("Ha sucedido un error a la hora e verificar los creditos disponibles para el contrato: " + idContrato + ", error: ", e);
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoBean devolverCredito(String tipoTramite, Long idContrato, Long idUsuario, List<BigDecimal> numsExpediente) {
		ResultadoBean result = new ResultadoBean(Boolean.FALSE);
		try {
			ConceptoCreditoFacturado concepto = getConceptoCreditoImpresion(tipoTramite);
			TipoCreditoTramiteVO tipoCreditoTramite = tipoCreditoTramiteDao.getTipoCreditoTramite(tipoTramite);
			if (tipoCreditoTramite != null && StringUtils.isNotBlank(tipoCreditoTramite.getTipoCredito())) {
				result = devolverCreditos(tipoTramite, tipoCreditoTramite.getTipoCredito(), idContrato, idUsuario, concepto, numsExpediente);
			} else {
				result.setError(true);
				result.setMensaje("No existe tipo de credito tramite para ese tipo de tramite");
			}
		} catch (Exception e) {
			result.setError(true);
			result.setMensaje("Error al devolver los creditos");
			log.error("Error al devolver los creditos, error: ", e);
		}
		return result;
	}

	private ResultadoBean devolverCreditos(String tipoTramite, String tipoCredito, Long idContrato, Long idUsuario, ConceptoCreditoFacturado concepto, List<BigDecimal> numsExpediente) {
		ResultadoBean result = new ResultadoBean(Boolean.FALSE);

		TipoCreditoVO tipoCreditoVO = tipoCreditoDao.getTipoCredito(tipoCredito);
		if (tipoCreditoVO != null) {
			CreditoVO credito = creditoDao.getCredito(tipoCredito, idContrato.longValue());

			if (credito != null) {
				Long creditosTotal = null;
				if (INCREMENTAL.equals(tipoCreditoVO.getIncreDecre())) {
					creditosTotal = credito.getCreditos().longValue() - numsExpediente.size();
				} else if (DECREMENTAL.equals(tipoCreditoVO.getIncreDecre())) {
					creditosTotal = credito.getCreditos().longValue() + numsExpediente.size();
				}
				credito.setCreditos(new BigDecimal(creditosTotal));
				creditoDao.actualizar(credito);
			} else {
				if (INCREMENTAL.equals(tipoCreditoVO.getIncreDecre())) {
					credito = new CreditoVO();
					CreditoPK pk = new CreditoPK();
					credito.setCreditos(new BigDecimal(-numsExpediente.size()));
					pk.setIdContrato(idContrato.longValue());
					pk.setTipoCredito(tipoCredito);
					credito.setId(pk);
					creditoDao.guardar(credito);
				} else {
					result.setError(Boolean.TRUE);
					result.setMensaje("No existe creditos para este contrato");
				}
			}
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("No existe el tipo credito");
		}
		if (!result.getError()) {
			anotarGastosCredito(numsExpediente, concepto, idContrato, tipoTramite, tipoCredito, idUsuario, tipoCreditoVO.getIncreDecre());
		}
		return result;
	}

	@Override
	@Transactional
	public ResultadoBean descontarCreditosInteve(String tipoTramite, Long idContrato, Long idUsuario, BigDecimal numExpediente, Long numCreditos) {
		ResultadoBean result = new ResultadoBean(Boolean.FALSE);
		try {
			ConceptoCreditoFacturado concepto = getConceptoCreditoImpresion(tipoTramite);
			TipoCreditoTramiteVO tipoCreditoTramite = tipoCreditoTramiteDao.getTipoCreditoTramite(tipoTramite);
			if (tipoCreditoTramite != null && tipoCreditoTramite.getTipoCredito() != null && !"".equals(tipoCreditoTramite.getTipoCredito())) {
				result = cargarCreditosInteve(tipoTramite, tipoCreditoTramite.getTipoCredito(), idContrato, idUsuario, concepto, numCreditos, numExpediente);
			} else {
				result.setError(true);
				result.setMensaje("No existe tipo de credito tramite para ese tipo de tramite");
			}
		} catch (Exception e) {
			result.setError(true);
			result.setMensaje("Error al descontar creditos");
			log.error("Error al descontar creditos", e);
		}
		return result;
	}

	@Override
	@Transactional
	public ResultadoBean descontarCreditos(String tipoTramite, Long idContrato, Long idUsuario, List<BigDecimal> numsExpediente) {
		ResultadoBean result = new ResultadoBean(Boolean.FALSE);
		try {
			ConceptoCreditoFacturado concepto = getConceptoCreditoImpresion(tipoTramite);
			TipoCreditoTramiteVO tipoCreditoTramite = tipoCreditoTramiteDao.getTipoCreditoTramite(tipoTramite);
			if (tipoCreditoTramite != null && tipoCreditoTramite.getTipoCredito() != null && !"".equals(tipoCreditoTramite.getTipoCredito())) {
				result = cargarCreditos(tipoTramite, tipoCreditoTramite.getTipoCredito(), idContrato, idUsuario, concepto, numsExpediente);
			} else {
				result.setError(true);
				result.setMensaje("No existe tipo de credito tramite para ese tipo de tramite");
			}
		} catch (Exception e) {
			result.setError(true);
			result.setMensaje("Error al descontar creditos");
			log.error("Error al descontar creditos", e);
		}
		return result;
	}

	private ResultadoBean cargarCreditos(String tipoTramite, String tipoCredito, Long idContrato, Long idUsuario, ConceptoCreditoFacturado concepto, List<BigDecimal> numsExpediente) {
		ResultadoBean result = new ResultadoBean(Boolean.FALSE);
		TipoCreditoVO tipoCreditoVO = tipoCreditoDao.getTipoCredito(tipoCredito);
		if (tipoCreditoVO != null) {
			CreditoVO credito = creditoDao.getCredito(tipoCredito, idContrato.longValue());
			if (credito != null) {
				Long creditosTotal = null;
				if (INCREMENTAL.equals(tipoCreditoVO.getIncreDecre())) {
					creditosTotal = credito.getCreditos().longValue() + numsExpediente.size();
				} else if (DECREMENTAL.equals(tipoCreditoVO.getIncreDecre())) {
					creditosTotal = credito.getCreditos().longValue() - numsExpediente.size();
				}
				credito.setCreditos(new BigDecimal(creditosTotal));
				creditoDao.actualizar(credito);
			} else {
				if (INCREMENTAL.equals(tipoCreditoVO.getIncreDecre())) {
					credito = new CreditoVO();
					CreditoPK pk = new CreditoPK();
					credito.setCreditos(new BigDecimal(numsExpediente.size()));
					pk.setIdContrato(idContrato.longValue());
					pk.setTipoCredito(tipoCredito);
					credito.setId(pk);
					creditoDao.guardar(credito);
				} else {
					result.setError(Boolean.TRUE);
					result.setMensaje("No existe creditos para este contrato");
				}
			}
			if (!result.getError()) {
				anotarGastosCredito(numsExpediente, concepto, idContrato, tipoTramite, tipoCredito, idUsuario, tipoCreditoVO.getIncreDecre());
			}
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("No existe el tipo credito");
		}
		return result;
	}

	private ResultadoBean cargarCreditosInteve(String tipoTramite, String tipoCredito, Long idContrato, Long idUsuario, ConceptoCreditoFacturado concepto, Long numCreditos, BigDecimal numExpediente) {
		ResultadoBean result = new ResultadoBean(Boolean.FALSE);
		TipoCreditoVO tipoCreditoVO = tipoCreditoDao.getTipoCredito(tipoCredito);
		if (tipoCreditoVO != null) {
			CreditoVO credito = creditoDao.getCredito(tipoCredito, idContrato.longValue());
			if (credito != null) {
				Long creditosTotal = null;
				if (INCREMENTAL.equals(tipoCreditoVO.getIncreDecre())) {
					creditosTotal = credito.getCreditos().longValue() + numCreditos;
				} else if (DECREMENTAL.equals(tipoCreditoVO.getIncreDecre())) {
					creditosTotal = credito.getCreditos().longValue() - numCreditos;
				}
				credito.setCreditos(new BigDecimal(creditosTotal));
				creditoDao.actualizar(credito);
			} else {
				if (INCREMENTAL.equals(tipoCreditoVO.getIncreDecre())) {
					credito = new CreditoVO();
					CreditoPK pk = new CreditoPK();
					credito.setCreditos(new BigDecimal(numCreditos));
					pk.setIdContrato(idContrato.longValue());
					pk.setTipoCredito(tipoCredito);
					credito.setId(pk);
					creditoDao.guardar(credito);
				} else {
					result.setError(Boolean.TRUE);
					result.setMensaje("No existe creditos para este contrato");
				}
			}
			if (!result.getError()) {
				try {
					anotarGastorFacturadoInteve(numCreditos, concepto, idContrato, tipoTramite, tipoCredito, numExpediente);
				} catch (Exception e) {
					log.error("Se ha producido un error al guardar historico de creditos, error: ", e);
				}
				try {
					anotarGastoHistorico(tipoCredito, idContrato, idUsuario, numCreditos, tipoCreditoVO.getIncreDecre());
				} catch (Exception e) {
					log.error("Se ha producido un error al guardar historico de creditos", e);
				}
			}
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("No existe el tipo credito");
		}
		return result;
	}

	private void anotarGastosCredito(List<BigDecimal> numsExpediente, ConceptoCreditoFacturado concepto, Long idContrato, String tipoTramite, String tipoCredito, Long idUsuario, String increDecre) {
		try {
			anotarGastoCreditoFacturado(numsExpediente.size(), concepto, idContrato, tipoTramite, tipoCredito, numsExpediente);
		} catch (Exception e) {
			log.error("Se ha producido un error al guardar en creditos facturados", e);
		}
		try {
			anotarGastoHistorico(tipoCredito, idContrato, idUsuario, new Long(numsExpediente.size()), increDecre);
		} catch (Exception e) {
			log.error("Se ha producido un error al guardar historico de creditos", e);
		}
	}

	private ConceptoCreditoFacturado getConceptoCreditoImpresion(String tipoTramite) {
		if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tipoTramite)) {
			return ConceptoCreditoFacturado.ITT;
		} else if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tipoTramite)) {
			return ConceptoCreditoFacturado.ITM;
		} else if (TipoTramiteTrafico.Baja.getValorEnum().equals(tipoTramite)) {
			return ConceptoCreditoFacturado.ITB;
		} else if (TipoTramiteTrafico.PermisonInternacional.getValorEnum().equals(tipoTramite)) {
			return ConceptoCreditoFacturado.PRI;
		}
		return null;
	}

	private void anotarGastoCreditoFacturado(int numCreditos, ConceptoCreditoFacturado concepto, Long idContrato, String tipoTramite, String tipoCredito, List<BigDecimal> numsExpediente) {
		CreditoFacturadoVO creditoFacturadoVO = new CreditoFacturadoVO();
		creditoFacturadoVO.setFecha(new Date());
		creditoFacturadoVO.setNumeroCreditos(new BigDecimal(numCreditos));
		creditoFacturadoVO.setTipoTramite(new TipoTramiteVO());
		creditoFacturadoVO.getTipoTramite().setTipoTramite(tipoTramite);
		creditoFacturadoVO.setConcepto(concepto);
		creditoFacturadoVO.setContrato(new ContratoVO());
		creditoFacturadoVO.getContrato().setIdContrato(idContrato);

		if (numsExpediente != null && !numsExpediente.isEmpty()) {
			for (BigDecimal numExpediente : numsExpediente) {
				CreditoFacturadoTramiteVO creditoFacturadoTramite = new CreditoFacturadoTramiteVO();
				creditoFacturadoTramite.setIdTramite(numExpediente.toString());
				creditoFacturadoVO.addCreditoFacturadoTramite(creditoFacturadoTramite);
			}
		}
		try {
			creditoFacturadoDao.guardar(creditoFacturadoVO);
		} catch (Exception e) {
			log.error("Error guardando el registro de creditos facturados", e);
		}
	}

	private void anotarGastorFacturadoInteve(Long numCreditos, ConceptoCreditoFacturado concepto, Long idContrato, String tipoTramite, String tipoCredito, BigDecimal numExpediente) {
		CreditoFacturadoVO creditoFacturadoVO = new CreditoFacturadoVO();
		creditoFacturadoVO.setFecha(new Date());
		creditoFacturadoVO.setNumeroCreditos(new BigDecimal(numCreditos));
		creditoFacturadoVO.setTipoTramite(new TipoTramiteVO());
		creditoFacturadoVO.getTipoTramite().setTipoTramite(tipoTramite);
		creditoFacturadoVO.setConcepto(concepto);
		creditoFacturadoVO.setContrato(new ContratoVO());
		creditoFacturadoVO.getContrato().setIdContrato(idContrato);

		CreditoFacturadoTramiteVO creditoFacturadoTramite = new CreditoFacturadoTramiteVO();
		creditoFacturadoTramite.setIdTramite(numExpediente.toString());
		creditoFacturadoVO.addCreditoFacturadoTramite(creditoFacturadoTramite);
		creditoFacturadoDao.guardar(creditoFacturadoVO);
	}

	private void anotarGastoHistorico(String tipoCredito, Long idContrato, Long idUsuario, Long cantidad, String increDecre) {
		HistoricoCreditoVO historicoCreditoVO = new HistoricoCreditoVO();
		if (INCREMENTAL.equals(increDecre)) {
			historicoCreditoVO.setCantidadSumada(new BigDecimal(cantidad));
		} else if (DECREMENTAL.equals(increDecre)) {
			historicoCreditoVO.setCantidadRestada(new BigDecimal(cantidad));
		}
		historicoCreditoVO.setFecha(new Date());
		historicoCreditoVO.setIdContrato(idContrato);
		historicoCreditoVO.setTipoCredito(tipoCredito);
		historicoCreditoVO.setIdUsuario(idUsuario);
		historicoCreditoDao.guardar(historicoCreditoVO);
	}

	@Override
	@Transactional(readOnly = true)
	public ResultadoBean validarTotalCreditos(Long idContrato, String tipoTramiteCredito) {
		ResultadoBean result = new ResultadoBean(Boolean.FALSE);
		TipoCreditoTramiteVO tipoCreditoTramite = tipoCreditoTramiteDao.getTipoCreditoTramite(tipoTramiteCredito);
		if (tipoCreditoTramite != null) {
			result.setTipoCredito(tipoCreditoTramite.getTipoCredito());
			TipoCreditoVO tipoCreditoVO = tipoCreditoDao.getTipoCredito(tipoCreditoTramite.getTipoCredito());
			if (tipoCreditoVO != null) {
				if (DECREMENTAL.equals(tipoCreditoVO.getIncreDecre())) {
					Long numCreditos = new Long(1);
					CreditoVO creditoContrato = creditoDao.getCredito(tipoCreditoTramite.getTipoCredito(), idContrato);
					if (creditoContrato != null) {
						int numColasProceso = servicioComunCola.getNumColasProcesoPorContrato(ProcesosAmEnum.GESTION_CREDITOS.getValorEnum(), idContrato);
						if (numColasProceso != 0) {
							numCreditos = numCreditos + numColasProceso;
						}
						if (creditoContrato.getCreditos().longValue() >= numCreditos) {
							result.setError(Boolean.TRUE);
							result.setMensaje("No dispones de créditos suficientes.");
						}
					} else {
						result.setError(Boolean.TRUE);
						result.setMensaje("No existe crédito para ese contrato.");
					}
				}
			}
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("No existe tipo de credito para ese tipo trámite crédito.");
			log.error("No existe tipo de credito para el tipo tramite credito: " + tipoTramiteCredito);
		}
		return result;
	}
}