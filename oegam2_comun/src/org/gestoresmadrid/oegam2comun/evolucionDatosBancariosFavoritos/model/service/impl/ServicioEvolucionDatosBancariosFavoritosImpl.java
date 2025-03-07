package org.gestoresmadrid.oegam2comun.evolucionDatosBancariosFavoritos.model.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.datosBancariosFavoritos.model.enumerados.EstadoDatosBancarios;
import org.gestoresmadrid.core.evolucionDatosBancariosFavoritos.modelo.dao.EvolucionDatosBancariosFavoritosDao;
import org.gestoresmadrid.core.evolucionDatosBancariosFavoritos.modelo.vo.EvolucionDatosBancariosFavoritosVO;
import org.gestoresmadrid.core.model.enumerados.TipoActualizacion;
import org.gestoresmadrid.core.tasas.model.enumeration.FormaPago;
import org.gestoresmadrid.oegam2comun.evolucionDatosBancariosFavoritos.model.service.ServicioEvolucionDatosBancariosFavoritos;
import org.gestoresmadrid.oegam2comun.evolucionDatosBancariosFavoritos.view.beans.EvolucionDatosBancariosFavoritosBean;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioEvolucionDatosBancariosFavoritosImpl implements ServicioEvolucionDatosBancariosFavoritos{

	private static final long serialVersionUID = -4504770903634311331L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioEvolucionDatosBancariosFavoritosImpl.class);

	@Autowired
	private Conversor conversor;
	
	@Autowired
	private EvolucionDatosBancariosFavoritosDao evolucionDatosBancariosFavoritosDao;
	
	@Override
	public List<EvolucionDatosBancariosFavoritosBean> convertirListaEnBeanPantalla(List<EvolucionDatosBancariosFavoritosVO> list) {
		try {
			if(list != null && !list.isEmpty()){
				List<EvolucionDatosBancariosFavoritosBean> listaEvolucion = new ArrayList<EvolucionDatosBancariosFavoritosBean>();
				for(EvolucionDatosBancariosFavoritosVO evolucionDatosBancariosFavoritosVO : list){
					EvolucionDatosBancariosFavoritosBean evolucionDatosBancariosFavoritosBean = conversor.transform(evolucionDatosBancariosFavoritosVO, EvolucionDatosBancariosFavoritosBean.class);
					evolucionDatosBancariosFavoritosBean.setTipoActuacion(TipoActualizacion.getNombrePorValor(evolucionDatosBancariosFavoritosBean.getTipoActuacion()));
					if(evolucionDatosBancariosFavoritosVO.getEstadoAnt() != null){
						evolucionDatosBancariosFavoritosBean.setEstadoAnt(EstadoDatosBancarios.getNombrePorValor(evolucionDatosBancariosFavoritosVO.getEstadoAnt().toString()));
					}
					if(evolucionDatosBancariosFavoritosVO.getEstadoNuevo() != null){
						evolucionDatosBancariosFavoritosBean.setEstadoNuevo(EstadoDatosBancarios.getNombrePorValor(evolucionDatosBancariosFavoritosVO.getEstadoNuevo().toString()));
					}
					if(evolucionDatosBancariosFavoritosVO.getFormaPagoAnt() != null){
						evolucionDatosBancariosFavoritosBean.setFormaPagoAnt(FormaPago.convertirTexto(evolucionDatosBancariosFavoritosVO.getFormaPagoAnt().intValue()));
					}
					if(evolucionDatosBancariosFavoritosVO.getFormaPagoNueva() != null){
						evolucionDatosBancariosFavoritosBean.setFormaPagoNueva(FormaPago.convertirTexto(evolucionDatosBancariosFavoritosVO.getFormaPagoNueva().intValue()));
					}
					listaEvolucion.add(evolucionDatosBancariosFavoritosBean);
				}
				return listaEvolucion;
			}
		}catch(Exception e){
			log.error("Ha sucedido un error a la hora de convertir los datos de la evolucion de datos bancarios para mostrarlos por pantalla, error: ",e);
		}
		return Collections.emptyList();
	}
	
	@Override
	@Transactional
	public ResultBean guardarEvolucion(EvolucionDatosBancariosFavoritosVO evolucionDatosBancariosFavoritosVO) {
		ResultBean resultado = new ResultBean(false);
		try {
			if(evolucionDatosBancariosFavoritosVO != null){
				evolucionDatosBancariosFavoritosDao.guardar(evolucionDatosBancariosFavoritosVO);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar la evolucion, error: ", e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de guardar la evolucion.");
		}
		return resultado;
	}
}
