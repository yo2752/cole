package org.gestoresmadrid.oegam2comun.consultaDev.model.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.consultaDev.model.dao.SuscripcionDevDao;
import org.gestoresmadrid.core.consultaDev.model.enumerados.EstadoCif;
import org.gestoresmadrid.core.consultaDev.model.vo.SuscripcionDevVO;
import org.gestoresmadrid.oegam2comun.consultaDev.model.service.ServicioSuscripcionDev;
import org.gestoresmadrid.oegam2comun.consultaDev.model.service.ServicioWebServiceConsultaDev;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.dgt.www.nostra.esquemas.consultaDEV.datos_especificos.Suscripcion;
import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioSuscripcionDevImpl implements ServicioSuscripcionDev{

	private static final long serialVersionUID = -7190877339943931952L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioSuscripcionDevImpl.class);
	
	@Autowired
	Conversor conversor;
	
	@Autowired
	SuscripcionDevDao suscripcionDevDao;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Override
	@Transactional
	public ResultBean guardarSuscripcionesWSConsultaDev(Suscripcion[] suscripciones, Long idConsultaDev) {
		ResultBean resultado = new ResultBean();
		BigDecimal estadoSuscripcion = null;
		try {
			if(suscripciones != null){
				for(Suscripcion suscripcion : suscripciones){
					SuscripcionDevVO suscripcionDevVO = conversor.transform(suscripcion, SuscripcionDevVO.class);
					suscripcionDevVO.setIdConsultaDev(idConsultaDev);
					suscripcionDevVO.setCodEstado(suscripcion.getCodEstado().getValue());
					suscripcionDevVO.setFechaSuscripcion(utilesFecha.formatoFecha("yyyy-MM-dd HH:mm:ss", suscripcion.getFechaSuscripcion().replace("T", " ")));
					suscripcionDevDao.guardar(suscripcionDevVO);
					estadoSuscripcion = EstadoCif.convertirEstadoSuscripcionToEstadoCif(suscripcionDevVO.getCodEstado());
				}
				resultado.addAttachment(ServicioWebServiceConsultaDev.estadoSuscripcion, estadoSuscripcion);
			}else{
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos de las suscripciones para poder actualizar la consulta dev.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar las suscripciones de la consulta dev, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar las suscripciones de la consulta dev.");
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly=true)
	public ResultBean getSuscripcionesConsultaDev(Long idConsultaDev) {
		List<SuscripcionDevVO> resultado2 = null;
		ResultBean resultado = new ResultBean();

		try {
			if(idConsultaDev != null){
				
				resultado2 =  getSuscripcionDevVO(idConsultaDev,true);
				//SuscripcionDevVO suscripcionDev = getSuscripcionDevVO(idConsultaDev,true);
				if(resultado2 != null){
					resultado.addAttachment("listaSuscripcion",resultado2 );
				}else{
					resultado.setError(true);
					resultado.addMensajeALista("No existen datos para esa consulta Dev.");
				}
			}else{
				resultado.setError(true);
				resultado.addMensajeALista("Debe de indicar la consulta dev que desea obtener sus datos.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la consulta dev con id: " + idConsultaDev + " , error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de obtener la consulta dev con id: " + idConsultaDev);
		}
		return resultado;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<SuscripcionDevVO> getSuscripcionDevVO(Long idConsultaDev, Boolean completo) {
		try {
			if(idConsultaDev != null){
				return suscripcionDevDao.getTodasSuscripcionesDevPorIdConsultaDev(idConsultaDev);
			}else{
				log.error("No se puede realizar una consulta a la BBDD con el id a null para las suscripciones dev.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la consulta dev con id: " + idConsultaDev + " , error: ",e);
		}
		return null;
	}
	
}
