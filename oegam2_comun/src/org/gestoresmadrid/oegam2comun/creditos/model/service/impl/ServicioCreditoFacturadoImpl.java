package org.gestoresmadrid.oegam2comun.creditos.model.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.general.model.vo.TipoTramiteVO;
import org.gestoresmadrid.core.historicocreditos.model.dao.CreditoFacturadoDao;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.historicocreditos.model.vo.CreditoFacturadoTramiteVO;
import org.gestoresmadrid.core.historicocreditos.model.vo.CreditoFacturadoVO;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.trafico.solicitudesplacas.model.vo.SolicitudPlacaVO;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCreditoFacturado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
@Transactional
public class ServicioCreditoFacturadoImpl implements ServicioCreditoFacturado {

	private static final long serialVersionUID = -6508460558085159875L;

	private static ILoggerOegam log = LoggerOegam.getLogger(ServicioCreditoFacturadoImpl.class);

	@Autowired
	private CreditoFacturadoDao creditoFacturadoDao;

	@Override
	public boolean anotarGasto(Integer numCreditos, ConceptoCreditoFacturado concepto, Long idContrato, String tipoTramite, String... tramites) {
		boolean result = false;
		String tipoCredito = getTipoCredito(tipoTramite);
		// Comprueba que todos los campos vengan correctamente informados
		if (numCreditos != null && tipoCredito != null && !tipoCredito.isEmpty() && tipoTramite != null && !tipoTramite.isEmpty() && idContrato != null && concepto != null) {
			CreditoFacturadoVO creditoFacturadoVO = new CreditoFacturadoVO();
			creditoFacturadoVO.setFecha(new Date());
			creditoFacturadoVO.setNumeroCreditos(new BigDecimal(numCreditos));
			creditoFacturadoVO.setTipoTramite(new TipoTramiteVO());
			creditoFacturadoVO.getTipoTramite().setTipoTramite(tipoTramite);
			creditoFacturadoVO.setConcepto(concepto);
			creditoFacturadoVO.setContrato(new ContratoVO());
			creditoFacturadoVO.getContrato().setIdContrato(idContrato);

			// Asociacion de tramites al gasto
			if(tramites!=null)
			{
			for (String tramite : tramites) {
				CreditoFacturadoTramiteVO creditoFacturadoTramite = new CreditoFacturadoTramiteVO();
				creditoFacturadoTramite.setIdTramite(tramite);
				creditoFacturadoVO.addCreditoFacturadoTramite(creditoFacturadoTramite);
			 }
			}
			Long id = null;
			try {
				id = (Long) creditoFacturadoDao.guardar(creditoFacturadoVO);
			} catch (Exception e) {
				log.error("Error guardando el registro de creditos facturados", e);
			}

			// Comprueba el identificador devuelto para comprobar que se ha guardado correctamente
			if (id != null && id > 0) {
				result = true;
			}
		}
		return result;
	}

	@Override
	public List<CreditoFacturadoVO> movimientoCreditosTramite(String idTramite) {
		CreditoFacturadoTramiteVO creditoFacturadoTramiteVO = new CreditoFacturadoTramiteVO();
		creditoFacturadoTramiteVO.setIdTramite(idTramite);

		return creditoFacturadoDao.buscarCreditoFacturadoTramite(creditoFacturadoTramiteVO);
	}

	
	@Override
	public void eliminarGasto(SolicitudPlacaVO solicitud) {
		CreditoFacturadoTramiteVO creditoFacturadoTramiteVO = new CreditoFacturadoTramiteVO();
		creditoFacturadoTramiteVO.setIdTramite(solicitud.getIdSolicitud().toString());

		List<CreditoFacturadoVO> listaCreditoFacturadoVO = creditoFacturadoDao.buscarCreditoFacturadoTramite(creditoFacturadoTramiteVO);
		for (CreditoFacturadoVO creditoFacturadoVO : listaCreditoFacturadoVO) {
			creditoFacturadoDao.borrar(creditoFacturadoVO);
		}
	}

	private String getTipoCredito(String tipoTramite) {
		return creditoFacturadoDao.getTipoCredito(tipoTramite);
	}

	@Override
	public Long guardar(CreditoFacturadoVO creditoFacturadoVO) {
		return (Long) creditoFacturadoDao.guardar(creditoFacturadoVO);
	}

	@Override
	public CreditoFacturadoVO actualizar(CreditoFacturadoVO creditoFacturadoVO) {
		return creditoFacturadoDao.actualizar(creditoFacturadoVO);
	}

	@Override
	public boolean borrar(CreditoFacturadoVO creditoFacturadoVO) {
		return creditoFacturadoDao.borrar(creditoFacturadoVO);
	}

	@Override
	public CreditoFacturadoVO getCreditoFacturadoVO(Long id, String... initialized) {
		return creditoFacturadoDao.getCreditoFacturadoVO(id, initialized);
	}

	@Override
	public List<CreditoFacturadoVO> buscar(Object filter) {
		return creditoFacturadoDao.buscar((CreditoFacturadoVO) filter);
	}

	@Override
	public ConceptoCreditoFacturado getConceptoCreditoImpresion(TipoTramiteTrafico tipoTramite) {
		if (tipoTramite == TipoTramiteTrafico.Transmision || tipoTramite == TipoTramiteTrafico.TransmisionElectronica) {
			return ConceptoCreditoFacturado.ITT;
		} else if (tipoTramite == TipoTramiteTrafico.Matriculacion) {
			return ConceptoCreditoFacturado.ITM;
		} else if (tipoTramite == TipoTramiteTrafico.Baja) {
			return ConceptoCreditoFacturado.ITB;
		}
		return null;
	}


	public CreditoFacturadoDao getCreditoFacturadoDao() {
		return creditoFacturadoDao;
	}

	public void setCreditoFacturadoDao(CreditoFacturadoDao creditoFacturadoDao) {
		this.creditoFacturadoDao = creditoFacturadoDao;
	}

}
