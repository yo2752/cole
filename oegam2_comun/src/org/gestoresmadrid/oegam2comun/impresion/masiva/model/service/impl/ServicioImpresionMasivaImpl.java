package org.gestoresmadrid.oegam2comun.impresion.masiva.model.service.impl;

import java.util.Date;

import org.gestoresmadrid.core.impresion.masiva.model.dao.ImpresionMasivaDao;
import org.gestoresmadrid.core.impresion.masiva.model.vo.ImpresionMasivaVO;
import org.gestoresmadrid.core.model.enumerados.EstadoImpresion;
import org.gestoresmadrid.oegam2comun.impresion.masiva.model.service.ServicioImpresionMasiva;
import org.gestoresmadrid.oegam2comun.impresion.masiva.view.dto.ImpresionMasivaDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioImpresionMasivaImpl implements ServicioImpresionMasiva {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioImpresionMasivaImpl.class);

	@Autowired
	private ImpresionMasivaDao impresionMasivaDao;

	@Autowired
	private Conversor conversor;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	@Transactional
	public ImpresionMasivaDto getImpresionMasivaPorNombreFichero(String nombreFichero) {
		ImpresionMasivaVO impresionMasivaVO = impresionMasivaDao.getImpresionMasivaPorNombreFichero(nombreFichero);
		return conversor.transform(impresionMasivaVO, ImpresionMasivaDto.class);
	}

	@Override
	@Transactional
	public void eliminar(String nombreFichero) {
		ImpresionMasivaVO impresionMasivaVO = null;
		try {
			impresionMasivaVO = impresionMasivaDao.getImpresionMasivaPorNombreFichero(nombreFichero);
			if (impresionMasivaVO != null) {
				impresionMasivaDao.borrar(impresionMasivaVO);
			}
		} catch (Exception e) {
			log.error("Error al eliminar una impresión masiva", e);
		}
	}

	@Override
	@Transactional
	public void guardar(ImpresionMasivaDto impresionMasivaDto) {
		ImpresionMasivaVO impresionMasiva = conversor.transform(impresionMasivaDto, ImpresionMasivaVO.class);
		impresionMasiva.setEstadoImpresion(EstadoImpresion.SIN_DESCARGAR.getValorEnum());
		impresionMasivaDao.guardarOActualizar(impresionMasiva);
	}

	@Override
	@Transactional
	public void cambiarEstadoImpresion(ImpresionMasivaDto impresionMasivaDto) {
		ImpresionMasivaVO impresionMasiva = conversor.transform(impresionMasivaDto, ImpresionMasivaVO.class);
		impresionMasiva.setEstadoImpresion(EstadoImpresion.DESCARGADO.getValorEnum());
		impresionMasivaDao.actualizar(impresionMasiva);
	}

	@Override
	@Transactional
	public ResultBean guardarImpresionMasiva(String path, String nombreFichero, String fechaHora, String tipoImpreso, String numColegiado) {
		ResultBean result = null;
		ImpresionMasivaVO impresionMasivaVO = getImpresionMasivaVo(path, nombreFichero, fechaHora, tipoImpreso, numColegiado);
		impresionMasivaVO.setIdImpresionMasiva((Long)impresionMasivaDao.guardar(impresionMasivaVO));
		if(impresionMasivaVO.getIdImpresionMasiva() != null){
			result = new ResultBean(false,"Se ha guardado correctamente");
		}
		return result;
	}
	
	private ImpresionMasivaVO getImpresionMasivaVo(String path, String nombreFichero, String fechaHora, String tipoImpreso, String numColegiado) {
		ImpresionMasivaVO impresionMasivaVO = new ImpresionMasivaVO();
		impresionMasivaVO.setRutaFichero(path);
		impresionMasivaVO.setNombreFichero(nombreFichero);
		impresionMasivaVO.setTipoDocumento(tipoImpreso);
		impresionMasivaVO.setNumColegiado(numColegiado);
		impresionMasivaVO.setEstadoImpresion(EstadoImpresion.SIN_DESCARGAR.getValorEnum());
		
		Date fechaDate = utilesFecha.getFechaHoraDate(fechaHora);
		impresionMasivaVO.setFechaEnviadoProceso(fechaDate);
		impresionMasivaVO.setFechaAltaBBDD(utilesFecha.formatoFecha("dd/MM/yyyy",utilesFecha.getFechaHoy()));
		return impresionMasivaVO;
	}

	public ImpresionMasivaDao getImpresionMasivaDao() {
		return impresionMasivaDao;
	}

	public void setImpresionMasivaDao(ImpresionMasivaDao impresionMasivaDao) {
		this.impresionMasivaDao = impresionMasivaDao;
	}
}
