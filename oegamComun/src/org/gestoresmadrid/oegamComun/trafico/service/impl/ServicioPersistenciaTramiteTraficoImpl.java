package org.gestoresmadrid.oegamComun.trafico.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.OperacionPrmDstvFicha;
import org.gestoresmadrid.core.enumerados.ProcesosAmEnum;
import org.gestoresmadrid.core.enumerados.ProcesosEnum;
import org.gestoresmadrid.core.enumerados.TipoImpr;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.impr.model.dao.EvoPrmDstvFichaDao;
import org.gestoresmadrid.core.impr.model.vo.EvoPrmDstvFichaVO;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoBajaDao;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoDao;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoDuplicadosDao;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoMatrDao;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoSolInfoDao;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoTransDao;
import org.gestoresmadrid.core.trafico.model.vo.EvolucionTramiteTraficoPK;
import org.gestoresmadrid.core.trafico.model.vo.EvolucionTramiteTraficoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafBajaVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafDuplicadoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafSolInfoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafTranVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegamComun.cola.service.ServicioComunCola;
import org.gestoresmadrid.oegamComun.trafico.service.ServicioPersistenciaEvoTramiteTrafico;
import org.gestoresmadrid.oegamComun.trafico.service.ServicioPersistenciaTramiteTrafico;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
public class ServicioPersistenciaTramiteTraficoImpl implements ServicioPersistenciaTramiteTrafico {

	private static final long serialVersionUID = 8281349658548011455L;
	private static final Logger log = LoggerFactory.getLogger(ServicioPersistenciaTramiteTraficoImpl.class);

	@Autowired
	TramiteTraficoDao tramiteTraficoDao;

	@Autowired
	TramiteTraficoBajaDao tramiteTraficoBajaDao;

	@Autowired
	TramiteTraficoTransDao tramiteTraficoTransDao;

	@Autowired
	TramiteTraficoMatrDao tramiteTraficoMatrDao;

	@Autowired
	TramiteTraficoDuplicadosDao tramiteTraficoDuplicadosDao;

	@Autowired
	TramiteTraficoSolInfoDao tramiteTraficoSolInfoDao;

	@Autowired
	ServicioPersistenciaEvoTramiteTrafico servicioPersistenciaEvoTramiteTrafico;
	
	@Autowired
	ServicioComunCola servicioCola;
	
	@Autowired
	EvoPrmDstvFichaDao evoPrmDstvFichaDao;

	@Override
	@Transactional(readOnly = true)
	public TramiteTraficoVO getTramite(BigDecimal numExpediente, Boolean tramiteCompleto) {
		return tramiteTraficoDao.getTramite(numExpediente, tramiteCompleto);
	}

	@Override
	@Transactional(readOnly = true)
	public TramiteTrafTranVO getTramiteTransmision(BigDecimal numExpediente, Boolean tramiteCompleto) {
		return tramiteTraficoTransDao.getTramiteTransmision(numExpediente, tramiteCompleto);
	}

	@Override
	@Transactional(readOnly = true)
	public TramiteTrafBajaVO getTramiteBaja(BigDecimal numExpediente, Boolean tramiteCompleto) {
		return tramiteTraficoBajaDao.getTramiteBaja(numExpediente, tramiteCompleto);
	}

	@Override
	@Transactional(readOnly = true)
	public TramiteTrafDuplicadoVO getTramiteDuplicado(BigDecimal numExpediente, Boolean tramiteCompleto) {
		return tramiteTraficoDuplicadosDao.getTramiteDuplicado(numExpediente, tramiteCompleto);
	}

	@Override
	@Transactional(readOnly = true)
	public TramiteTrafMatrVO getTramiteMatriculacion(BigDecimal numExpediente, Boolean tramiteCompleto) {
		return tramiteTraficoMatrDao.getTramiteTrafMatr(numExpediente, tramiteCompleto, false);
	}

	@Override
	@Transactional(readOnly = true)
	public TramiteTrafSolInfoVO getTramiteSolictud(BigDecimal numExpediente, Boolean tramiteCompleto) {
		return tramiteTraficoSolInfoDao.getTramiteTrafSolInfo(numExpediente, tramiteCompleto);
	}

	@Override
	@Transactional
	public BigDecimal crearTramite(TramiteTraficoVO tramiteTrafico) throws Exception {
		tramiteTrafico.setNumExpediente(tramiteTraficoDao.generarNumExpediente(tramiteTrafico.getNumColegiado()));
		tramiteTraficoDao.guardarOActualizar(tramiteTrafico);
		return tramiteTrafico.getNumExpediente();
	}

	@Override
	@Transactional(readOnly = true)
	public int getNumTramitePorVehiculo(BigDecimal numExpediente, Long idVehiculo) {
		return tramiteTraficoDao.getNumTramitePorVehiculo(numExpediente, idVehiculo);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Integer getNumTramitePorColegiado(String numColegiado) {
		return tramiteTraficoDao.getNumTramitePorColegiado(numColegiado);
	}

	@Override
	@Transactional
	public void guardarOActualizar(TramiteTraficoVO tramite) {
		tramiteTraficoDao.guardarOActualizar(tramite);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Long> getListaIdsContratosFinalizadosTelematicamentePorFecha(Date fechaPresentacion, String[] tiposTramites) {
		return tramiteTraficoDao.getListaIdsContratosFinalizadosTelematicamentePorFecha(fechaPresentacion, tiposTramites, Boolean.TRUE);
	}

	@Override
	@Transactional(readOnly = true)
	public List<TramiteTraficoVO> getListaTramitesDocBaseNocturno(Long idContrato, Date fechaPresentacion, String tipoTramite) {
		return tramiteTraficoDao.getListaTramitesDocBaseNocturno(idContrato, fechaPresentacion, tipoTramite);
	}

	@Override
	@Transactional
	public void actualizarTramite(TramiteTraficoVO tramiteVO) {
		tramiteTraficoDao.actualizar(tramiteVO);
	}

	@Override
	@Transactional(readOnly = true)
	public List<TramiteTraficoVO> getListaTramiteTraficoVO(BigDecimal[] numExpedientes, Boolean tramiteCompleto) {
		return tramiteTraficoDao.getListaTramiteTraficoVO(numExpedientes, tramiteCompleto);
	}
	
	@Override
	@Transactional(readOnly = true)
	public BigDecimal generarNumExpediente(String numColegiado) throws Exception {
		return tramiteTraficoDao.generarNumExpediente(numColegiado);
	}

	@Override
	@Transactional
	public void cambiarEstado(BigDecimal numExpediente, BigDecimal estadoNuevo, Long idUsuario) {
		TramiteTraficoVO tramite = getTramite(numExpediente, Boolean.FALSE);
		BigDecimal estadoAntiguo = tramite.getEstado();
		tramite.setEstado(estadoNuevo);
		tramite.setFechaUltModif(new Date());
		tramiteTraficoDao.guardarOActualizar(tramite);
		guardarEvolucion(numExpediente, estadoNuevo, estadoAntiguo, idUsuario);
	}

	private void guardarEvolucion(BigDecimal numExpediente, BigDecimal estadoNuevo, BigDecimal estadoAntiguo, Long idUsuario) {
		EvolucionTramiteTraficoVO evTramiteTraficoVO = new EvolucionTramiteTraficoVO();
		EvolucionTramiteTraficoPK id = new EvolucionTramiteTraficoPK();
		id.setEstadoAnterior(estadoAntiguo);
		id.setEstadoNuevo(estadoNuevo);
		id.setFechaCambio(new Date());
		id.setNumExpediente(numExpediente);
		evTramiteTraficoVO.setId(id);
		UsuarioVO usuario = new UsuarioVO();
		usuario.setIdUsuario(idUsuario);
		evTramiteTraficoVO.setUsuario(usuario);

		servicioPersistenciaEvoTramiteTrafico.guardar(evTramiteTraficoVO);
	}

	@Override
	@Transactional
	public void guardarNRE(BigDecimal numExpediente, String nre, Date fechaRegistro) {
		TramiteTraficoVO tramiteTraficoBBDD = getTramite(numExpediente, Boolean.FALSE);
		tramiteTraficoBBDD.setNre(nre);
		tramiteTraficoBBDD.setFechaRegistroNRE(fechaRegistro);
		tramiteTraficoDao.guardar(tramiteTraficoBBDD);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> getTramitePorNRE() throws Exception {
		return tramiteTraficoDao.getTramitePorNRE();
	}

	@Override
	@Transactional
	public ResultadoBean generarSolDocImprNocturno(List<BigDecimal> listaExpedientesImpr, Long docId, String sDocImpr,
			Long idUsuario, String tipoImpr, Long idContrato, Boolean esEntornoAm) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			for (BigDecimal numExpediente : listaExpedientesImpr) {
				actualizarTramiteSolImpr(numExpediente, docId, sDocImpr, tipoImpr, idUsuario, OperacionPrmDstvFicha.CREACION.getValorEnum(), EstadoPermisoDistintivoItv.SOLICITANDO_IMPR
						.getValorEnum());
			}
			String tipoSolicitud = null;
			if (TipoImpr.Permiso_Circulacion.getValorEnum().equals(tipoImpr)) {
				tipoSolicitud = TipoTramiteTrafico.Solicitud_Permiso.getValorEnum();
			} else {
				tipoSolicitud = TipoTramiteTrafico.Solicitud_Ficha_Tecnica.getValorEnum();
			}
			ResultadoBean resultBean = servicioCola.crearSolicitud(esEntornoAm ? ProcesosAmEnum.IMPR_NOCTURNO.getValorEnum() : ProcesosEnum.IMPR_NOCTURNO.getNombreEnum(), null, tipoSolicitud,
					new BigDecimal(docId), idUsuario, new BigDecimal(idContrato));
			if (resultBean.getError()) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje(resultBean.getMensaje());

			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar tratar los IMPR para su solicitud, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar tratar los IMPR para su solicitud.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public void actualizarTramiteSolImpr(BigDecimal numExpediente, Long idDocImpr, String docId, String tipoImpr, Long idUsuario, String operacion, String estadoNuevo) {
		TramiteTraficoVO tramiteTrafico = getTramite(numExpediente, Boolean.FALSE);
		String estadoAnt = null;
		if (TipoImpr.Permiso_Circulacion.getValorEnum().equals(tipoImpr)) {
			estadoAnt = tramiteTrafico.getEstadoSolPerm();
			tramiteTrafico.setEstadoSolPerm(estadoNuevo);
			tramiteTrafico.setEstadoImpPerm(EstadoPermisoDistintivoItv.Iniciado.getValorEnum());
			tramiteTrafico.setDocPermiso(idDocImpr);
		} else {
			estadoAnt = tramiteTrafico.getEstadoSolFicha();
			tramiteTrafico.setEstadoSolFicha(estadoNuevo);
			tramiteTrafico.setEstadoImpFicha(EstadoPermisoDistintivoItv.Iniciado.getValorEnum());
			tramiteTrafico.setDocFichaTecnica(idDocImpr);
		}
		tramiteTraficoDao.actualizar(tramiteTrafico);
		evoPrmDstvFichaDao.guardar(rellenarEvoPrmDstvFicha(numExpediente, idUsuario, tipoImpr, operacion, new Date(), estadoAnt, estadoNuevo, docId));
		
	}
	
	private EvoPrmDstvFichaVO rellenarEvoPrmDstvFicha(BigDecimal numExpediente, Long idUsuario, String tipoImpr, String operacion, Date fecha, String estadoAnt, String estadoNuevo, String docId) {
		EvoPrmDstvFichaVO evolucionPrmDstvFichaVO = new EvoPrmDstvFichaVO();
		if (estadoAnt != null && !estadoAnt.isEmpty()) {
			evolucionPrmDstvFichaVO.setEstadoAnterior(new BigDecimal(estadoAnt));
		}
		if (estadoNuevo != null && !estadoNuevo.isEmpty()) {
			evolucionPrmDstvFichaVO.setEstadoNuevo(new BigDecimal(estadoNuevo));
		}
		evolucionPrmDstvFichaVO.setFechaCambio(fecha);
		evolucionPrmDstvFichaVO.setIdUsuario(idUsuario);
		evolucionPrmDstvFichaVO.setNumExpediente(numExpediente);
		evolucionPrmDstvFichaVO.setOperacion(operacion);
		evolucionPrmDstvFichaVO.setTipoDocumento(tipoImpr);
		evolucionPrmDstvFichaVO.setDocID(docId);
		return evolucionPrmDstvFichaVO;
	}

	@Override
	@Transactional
	public void actualizarFechaDiaHoy(BigDecimal numExpediente, Long idUsuario) {
			TramiteTraficoVO tramite = getTramite(numExpediente, Boolean.FALSE);
			tramite.setFechaPresentacion(new Date());
			tramiteTraficoDao.guardarOActualizar(tramite);
			guardarEvolucion(numExpediente, tramite.getEstado(), tramite.getEstado(), idUsuario);
		}

	@Override
	@Transactional
	public ResultadoBean desasignarTasaTramiteTrafico(BigDecimal numExpediente, Long idUsuario) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			TramiteTrafTranVO tramite = getTramiteTransmision(numExpediente, Boolean.FALSE);
			if(tramite.getTasa() != null) {
				tramite.setTasa(null);
			}
			if(tramite.getTasa1() != null) {
				tramite.setTasa1(null);
			}
			if(tramite.getTasa2() != null) {
				tramite.setTasa2(null);
			}
			tramiteTraficoDao.guardarOActualizar(tramite);
			guardarEvolucion(numExpediente, tramite.getEstado(), tramite.getEstado(), idUsuario);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de desasignar tasa del trámite, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de desasignar tasa del trámite.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		}
		return resultado;
	}
	
}
