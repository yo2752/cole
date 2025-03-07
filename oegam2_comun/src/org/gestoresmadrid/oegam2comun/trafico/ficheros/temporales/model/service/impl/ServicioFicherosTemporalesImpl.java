package org.gestoresmadrid.oegam2comun.trafico.ficheros.temporales.model.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.ficheros.temporales.model.dao.FicherosTemporalesDao;
import org.gestoresmadrid.core.ficheros.temporales.model.vo.FicherosTemporalesVO;
import org.gestoresmadrid.core.model.enumerados.EstadoImpresion;
import org.gestoresmadrid.core.model.enumerados.SubTipoFicheros;
import org.gestoresmadrid.core.model.enumerados.TipoFicheros;
import org.gestoresmadrid.oegam2comun.ficheros.temporales.view.dto.FicherosTemporalesDto;
import org.gestoresmadrid.oegam2comun.ficheros.temporales.view.dto.ResultadoFicherosTemp;
import org.gestoresmadrid.oegam2comun.trafico.ficheros.temporales.model.service.ServicioFicherosTemporales;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.beans.ResultBean;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import utilidades.web.OegamExcepcion.EnumError;

@Service
public class ServicioFicherosTemporalesImpl implements ServicioFicherosTemporales{
	
	private static final ILoggerOegam LOG = LoggerOegam.getLogger(ServicioFicherosTemporalesImpl.class);
	
	@Autowired
	private FicherosTemporalesDao ficherosTemporalesDao; 
	
	@Autowired
	private GestorDocumentos gestorDocumentos;
	
	@Autowired
	private Conversor conversor;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	@Transactional
	public ResultBean altaFicheroTemporal(String nombreDocumento, String extension, String tipoDocumento, String subTipo, Fecha fecha, 
			String numColegiado, BigDecimal idUsuario, BigDecimal idContrato) throws OegamExcepcion {
		ResultBean resultado = null;
		try{
			FicherosTemporalesVO ficherosTemporalesVO = crearFicheroTemporalVO(nombreDocumento,extension,tipoDocumento,subTipo,fecha,numColegiado, idUsuario,idContrato,
					EstadoImpresion.SIN_DESCARGAR.getValorEnum());
			ficherosTemporalesVO.setIdFicheroTemporal((Long) ficherosTemporalesDao.guardar(ficherosTemporalesVO));
			resultado = new ResultBean(false);
			resultado.addAttachment("idFicheroTemporal", ficherosTemporalesVO.getIdFicheroTemporal());
		}catch(Exception e){
			Log.error("Ha sucedido un error a la hora de guardar el fichero temporal, error:", e);
			throw new OegamExcepcion(e,EnumError.error_00001);
		}
		return resultado;
	}

	private FicherosTemporalesVO crearFicheroTemporalVO(String nombreDocumento, String extension, String tipoDocumento, String subTipo,
			Fecha fecha, String numColegiado, BigDecimal idUsuario, BigDecimal idContrato, Integer estado) {
		FicherosTemporalesVO ficheroVO = new FicherosTemporalesVO();
		ficheroVO.setNombre(nombreDocumento);
		ficheroVO.setExtension(extension);
		ficheroVO.setTipoDocumento(tipoDocumento);
		ficheroVO.setSubTipoDocumento(subTipo);
		if(fecha != null){
			ficheroVO.setFecha(utilesFecha.convertirFechaEnDate(fecha));
		}
		ficheroVO.setNumColegiado(numColegiado);
		ficheroVO.setIdUsuario(idUsuario.longValue());
		ficheroVO.setIdContrato(idContrato.longValue());
		ficheroVO.setEstado(estado);
		return ficheroVO;
	}
	
	@Override
	@Transactional
	public FicherosTemporalesDto getFicheroTemporalDto(Long idFichero) {
		FicherosTemporalesVO ficherosTemporalesVO = new FicherosTemporalesVO();
		ficherosTemporalesVO.setIdFicheroTemporal(idFichero);
		List<FicherosTemporalesVO> listaFichero = ficherosTemporalesDao.getFicheroPorId(ficherosTemporalesVO);
		if(listaFichero != null && !listaFichero.isEmpty()){
			return conversor.transform(listaFichero.get(0), FicherosTemporalesDto.class);
		}
		return null;
	}
	
	@Override
	@Transactional
	public ResultadoFicherosTemp getFicheroImprimir(FicherosTemporalesDto ficheroTemporalDto) {
		ResultadoFicherosTemp resultado = null;
		FileResultBean file = null;
		FicheroBean ficheroBean = null;
		try {
			file = gestorDocumentos.buscarFicheroPorNombreTipo(TipoFicheros.convertirTexto(ficheroTemporalDto.getTipoDocumento()), SubTipoFicheros.convertirTexto(ficheroTemporalDto.getTipoDocumento()),
						utilesFecha.getFechaConDate(ficheroTemporalDto.getFecha()), ficheroTemporalDto.getNombre(), ficheroTemporalDto.getExtension());
		} catch (OegamExcepcion e) {
			LOG.error(e);
		}
		if(file != null && file.getFile() != null){
			ficheroBean = new FicheroBean();
			ficheroBean.setFichero(file.getFile());
			ficheroBean.setNombreDocumento(ficheroTemporalDto.getNombre());
			ficheroBean.setExtension(ficheroTemporalDto.getExtension());
			resultado = new ResultadoFicherosTemp(false, "El fichero se ha descargado correctamente", ficheroBean);
		}else{
			resultado = new ResultadoFicherosTemp(true, "El fichero que intenta descargar, no se encuentra disponible para su descarga.");
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public ResultadoFicherosTemp borrarFicheroTemporal(FicherosTemporalesDto ficheroTemporalDto) {
		ResultadoFicherosTemp resultado = null;
		try{
			FicherosTemporalesVO ficherosTemporalesVO = conversor.transform(ficheroTemporalDto, FicherosTemporalesVO.class);
			if(ficherosTemporalesVO != null){
				ficherosTemporalesDao.borrar(ficherosTemporalesVO);
			}else{
				resultado = new ResultadoFicherosTemp(true, "No se encuentran datos del fichero para borrar.");
			}
		}catch(Exception e){
			LOG.error(e);
			resultado = new ResultadoFicherosTemp(true, "Ha surgido un error a la hora de borrar el Fichero.");
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public ResultadoFicherosTemp actualizarEstadoFichero(FicherosTemporalesDto ficheroTemporalDto, Integer nuevoEstado) {
		ResultadoFicherosTemp resultado = null;
		try{
			FicherosTemporalesVO ficherosTemporalesVO = conversor.transform(ficheroTemporalDto, FicherosTemporalesVO.class);
			if(ficherosTemporalesVO != null){
				ficherosTemporalesVO.setEstado(nuevoEstado);
				ficherosTemporalesDao.actualizar(ficherosTemporalesVO);
			}else{
				resultado = new ResultadoFicherosTemp(true, "Ha surgido un error a la hora de actualizar el Fichero Temporal, no existen datos pra actualizar.");
			}
		}catch(Exception e){
			LOG.error(e);
			resultado = new ResultadoFicherosTemp(true, "Ha surgido un error a la hora de actualizar el Fichero Temporal."); 
		}
		return resultado;
	}

	public FicherosTemporalesDao getFicherosTemporalesDao() {
		return ficherosTemporalesDao;
	}

	public void setFicherosTemporalesDao(FicherosTemporalesDao ficherosTemporalesDao) {
		this.ficherosTemporalesDao = ficherosTemporalesDao;
	}

	public Conversor getConversor() {
		return conversor;
	}

	public void setConversor(Conversor conversor) {
		this.conversor = conversor;
	}
	
}
