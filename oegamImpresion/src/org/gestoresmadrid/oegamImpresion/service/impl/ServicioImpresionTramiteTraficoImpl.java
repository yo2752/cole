package org.gestoresmadrid.oegamImpresion.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.impresion.model.dao.ImpresionTramiteTraficoDao;
import org.gestoresmadrid.core.impresion.model.vo.ImpresionTramiteTraficoVO;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;
import org.gestoresmadrid.oegamImpresion.service.ServicioImpresionTramiteTrafico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioImpresionTramiteTraficoImpl implements ServicioImpresionTramiteTrafico {

	private static final long serialVersionUID = 4765159623450302355L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioImpresionTramiteTraficoImpl.class);

	@Autowired
	ImpresionTramiteTraficoDao impresionTramiteTraficoDao;

	@Override
	@Transactional
	public ResultadoImpresionBean crearImpresionTramiteTrafico(String[] numExpedientes, Long idImpresion) {
		ResultadoImpresionBean resultado = new ResultadoImpresionBean(Boolean.FALSE);
		try {
			if (numExpedientes != null) {
				ImpresionTramiteTraficoVO impresionTramiteTraficoVO = null;
				for (String numExpediente : numExpedientes) {
					impresionTramiteTraficoVO = new ImpresionTramiteTraficoVO();
					impresionTramiteTraficoVO.setIdImpresion(idImpresion);
					impresionTramiteTraficoVO.setNumExpediente(new BigDecimal(numExpediente));
					impresionTramiteTraficoDao.guardar(impresionTramiteTraficoVO);
				}
			}
		} catch (Exception e) {
			log.error("Error al guardar los datos de la impresion tramite trafico", e);
			resultado.setError(Boolean.TRUE);
		}
		return resultado;
	}

	@Override
	@Transactional
	public List<BigDecimal> obtenerNumExpedientes(Long idImpresion) {
		try {
			return impresionTramiteTraficoDao.obtenerNumExpedientes(idImpresion);
		} catch (Exception e) {
			log.error("Error al obtener los datos de la impresion con id:" + idImpresion, e);
		}
		return null;
	}

	@Override
	@Transactional
	public List<ImpresionTramiteTraficoVO> obtenerImpresionTramiteTrafico(Long idImpresion) {
		try {
			return impresionTramiteTraficoDao.getImpresionesTramiteTrafPorIdImpresion(idImpresion);
		} catch (Exception e) {
			log.error("Error al obtener los datos de la tabla impresion tramite trafico con id:" + idImpresion, e);
		}
		return null;
	}

	@Override
	@Transactional
	public void eliminarImpresionTramiteTrafPorIdImpresion(Long idImpresion) {
		try {
			List<ImpresionTramiteTraficoVO> lista = obtenerImpresionTramiteTrafico(idImpresion);
			if (lista != null && !lista.isEmpty()) {
				for (ImpresionTramiteTraficoVO impresionVO : lista) {
					impresionTramiteTraficoDao.borrar(impresionVO);
				}
			}
		} catch (Exception e) {
			log.error("Error al eliminar de la tabla de impresion tramite trafico con id:" + idImpresion, e);
		}
	}
}
