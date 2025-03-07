package org.gestoresmadrid.oegamImportacion.trafico.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.trafico.inteve.model.dao.TramiteTraficoInteveDao;
import org.gestoresmadrid.core.trafico.inteve.model.dao.TramiteTraficoSolInteveDao;
import org.gestoresmadrid.core.trafico.inteve.model.vo.TramiteTraficoInteveVO;
import org.gestoresmadrid.core.trafico.inteve.model.vo.TramiteTraficoSolInteveVO;
import org.gestoresmadrid.core.trafico.model.dao.EvolucionTramiteTraficoDao;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoBajaDao;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoDao;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoDuplicadosDao;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoMatrDao;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoTransDao;
import org.gestoresmadrid.core.trafico.model.vo.EvolucionTramiteTraficoVO;
import org.gestoresmadrid.core.trafico.model.vo.JefaturaTraficoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafBajaVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafDuplicadoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafTranVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoBean;
import org.gestoresmadrid.oegamImportacion.trafico.service.ServicioEvolucionTramiteImportacion;
import org.gestoresmadrid.oegamImportacion.trafico.service.ServicioTramiteTraficoImportacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioTramiteTraficoImportacionImpl implements ServicioTramiteTraficoImportacion {

	private static final long serialVersionUID = -8377100775566838703L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioTramiteTraficoImportacionImpl.class);

	@Autowired
	TramiteTraficoDao tramiteTraficoDao;

	@Autowired
	TramiteTraficoBajaDao tramiteTraficoBajaDao;

	@Autowired
	TramiteTraficoTransDao tramiteTraficoTransDao;

	@Autowired
	TramiteTraficoMatrDao tramiteTraficoMatrDao;

	@Autowired
	TramiteTraficoDuplicadosDao tramiteTraficoDuplicadoDao;

	@Autowired
	TramiteTraficoInteveDao tramiteTraficoIntDao;
	
	@Autowired
	TramiteTraficoSolInteveDao tramiteTraficoSolInteveDao;

	@Autowired
	ServicioEvolucionTramiteImportacion servicioEvolucionTramite;
	
	@Autowired
	EvolucionTramiteTraficoDao evolucionTramiteTraficoDao;

	@Override
	@Transactional
	public void guardarTramiteTraficoConEvolucion(TramiteTraficoVO tramiteTraficoVO, Long idUsuario) {
		tramiteTraficoDao.guardarOActualizar(tramiteTraficoVO);
		servicioEvolucionTramite.guardarEvolucion(tramiteTraficoVO.getNumExpediente(), tramiteTraficoVO.getEstado(), idUsuario, tramiteTraficoVO.getFechaAlta());
	}
	
	@Override
	@Transactional
	public ResultadoBean guardarTramiteBaja(TramiteTrafBajaVO tramiteBajaVO, Long idUsuario) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			tramiteTraficoBajaDao.guardarOActualizar(tramiteBajaVO);
			servicioEvolucionTramite.guardarEvolucion(tramiteBajaVO.getNumExpediente(), tramiteBajaVO.getEstado(), idUsuario, tramiteBajaVO.getFechaAlta());
		} catch (Exception e) {
			log.error("El tramite: " + tramiteBajaVO.getNumExpediente() + " no se ha guardado correctamente todos sus datos, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El tramite: " + tramiteBajaVO.getNumExpediente() + " no se ha guardado correctamente todos sus datos.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoBean guardarTramiteCtit(TramiteTrafTranVO tramiteTranVO, Long idUsuario) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			tramiteTraficoTransDao.guardarOActualizar(tramiteTranVO);
			servicioEvolucionTramite.guardarEvolucion(tramiteTranVO.getNumExpediente(), tramiteTranVO.getEstado(), idUsuario, tramiteTranVO.getFechaAlta());
		} catch (Exception e) {
			log.error("El tramite: " + tramiteTranVO.getNumExpediente() + " no se ha guardado correctamente todos sus datos, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El tramite: " + tramiteTranVO.getNumExpediente() + " no se ha guardado correctamente todos sus datos.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoBean guardarTramiteMatw(TramiteTrafMatrVO tramiteMatrVO, Long idUsuario) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			tramiteTraficoMatrDao.guardarOActualizar(tramiteMatrVO);
			servicioEvolucionTramite.guardarEvolucion(tramiteMatrVO.getNumExpediente(), tramiteMatrVO.getEstado(), idUsuario, tramiteMatrVO.getFechaAlta());
		} catch (Exception e) {
			log.error("El tramite: " + tramiteMatrVO.getNumExpediente() + " no se ha guardado correctamente todos sus datos, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El tramite: " + tramiteMatrVO.getNumExpediente() + " no se ha guardado correctamente todos sus datos.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoBean guardarTramiteDuplicado(TramiteTrafDuplicadoVO tramiteDuplicadoVO, Long idUsuario) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			tramiteTraficoDuplicadoDao.guardarOActualizar(tramiteDuplicadoVO);
			servicioEvolucionTramite.guardarEvolucion(tramiteDuplicadoVO.getNumExpediente(), tramiteDuplicadoVO.getEstado(), idUsuario, tramiteDuplicadoVO.getFechaAlta());
		} catch (Exception e) {
			log.error("El tramite: " + tramiteDuplicadoVO.getNumExpediente() + " no se ha guardado correctamente todos sus datos, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El tramite: " + tramiteDuplicadoVO.getNumExpediente() + " no se ha guardado correctamente todos sus datos.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoBean guardarTramiteInteve(TramiteTraficoInteveVO tramiteSolicitudVO, Long idUsuario) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			tramiteTraficoIntDao.guardarOActualizar(tramiteSolicitudVO);
		} catch (Exception e) {
			log.error("El tramite: " + tramiteSolicitudVO.getNumExpediente() + " no se ha guardado correctamente todos sus datos, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El tramite: " + tramiteSolicitudVO.getNumExpediente() + " no se ha guardado correctamente todos sus datos.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public Boolean comprobarTasaAsignada(String codigoTasa) {
		try {
			TramiteTraficoVO tramite = tramiteTraficoDao.getTramitePorTasa(codigoTasa);
			if (tramite == null) {
				return Boolean.FALSE;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error al comprobar si existe tasa en el tramite, error: ", e);
		}
		return Boolean.TRUE;
	}

	@Override
	@Transactional
	public ResultadoBean crearTramite(String numColegiado, String tipoTramite, Long idContrato, Long idUsuario, Date fechaAlta, JefaturaTraficoVO jefatura, BigDecimal tipoCreacion) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			TramiteTraficoVO tramiteTrafico = new TramiteTraficoVO();
			tramiteTrafico.setFechaAlta(fechaAlta);
			tramiteTrafico.setFechaUltModif(fechaAlta);
			tramiteTrafico.setTipoTramite(tipoTramite);
			ContratoVO contrato = new ContratoVO();
			contrato.setIdContrato(idContrato.longValue());
			tramiteTrafico.setContrato(contrato);
			UsuarioVO usuario = new UsuarioVO();
			usuario.setIdUsuario(idUsuario.longValue());
			tramiteTrafico.setUsuario(usuario);
			tramiteTrafico.setNumColegiado(numColegiado);
			tramiteTrafico.setEstado(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));
			resultado.setNumExpediente(tramiteTraficoDao.generarNumExpediente(numColegiado));
			tramiteTrafico.setNumExpediente(resultado.getNumExpediente());
			tramiteTraficoDao.guardar(tramiteTrafico);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de crear el tramite del tipo: " + tipoTramite + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de crear el tramite.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public int getNumTramitePorVehiculo(BigDecimal numExpediente, Long idVehiculo) {
		return tramiteTraficoDao.getNumTramitePorVehiculo(numExpediente, idVehiculo);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean validarPosibleDuplicado(String bastidor, String nif, String tipoTramite, String numColegiado) {
		Integer numPosiblesDuplicados = tramiteTraficoDao.getPosibleDuplicado(bastidor, nif, tipoTramite, numColegiado);
		if (numPosiblesDuplicados != null && numPosiblesDuplicados.intValue() > 0) {
			return true;
		}
		return false;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean validarPosibleDuplicadoCtit(String bastidor, String matricula, String nif, String tipoTramite, String numColegiado, String tipoTransfenrencia) {
		Integer numPosiblesDuplicados = tramiteTraficoTransDao.getPosibleDuplicado(bastidor, matricula, nif, tipoTramite, numColegiado, tipoTransfenrencia);
		if (numPosiblesDuplicados != null && numPosiblesDuplicados.intValue() > 0) {
			return true;
		}
		return false;
	}
	@Override
	@Transactional
	public void guardarOactualizarTramiteSolInteve(TramiteTraficoSolInteveVO solInteveVo) {
		tramiteTraficoSolInteveDao.guardarOActualizar(solInteveVo);
	}

	@Override
	@Transactional
	public void guardarEvolucionTramite(EvolucionTramiteTraficoVO evolucion) {
		evolucionTramiteTraficoDao.guardar(evolucion);
	}

	@Override
	@Transactional(readOnly = true)
	public TramiteTraficoVO getTramite(BigDecimal numExpediente, boolean tramiteCompleto) {
		TramiteTraficoVO tramite = null;
		try {
			if (numExpediente != null) {
				tramite = tramiteTraficoDao.getTramite(numExpediente, tramiteCompleto);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el trámite: " + numExpediente, e, numExpediente.toString());
		}
		return tramite;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean validarPosibleDuplicadoExcel(String matricula, String nif, String tipoTramite, String numColegiado) {
		Integer numPosiblesDuplicados = tramiteTraficoDao.getPosibleDuplicado(matricula, nif, tipoTramite, numColegiado);
		if (numPosiblesDuplicados != null && numPosiblesDuplicados.intValue() > 0) {
			return true;
		}
		return false;
	}
}
