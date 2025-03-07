package org.gestoresmadrid.oegam2comun.imputaciones.model.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.general.model.dao.UsuarioDao;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.imputaciones.model.dao.ImputacionHorasDao;
import org.gestoresmadrid.core.imputaciones.model.dao.TipoImputacionHorasDao;
import org.gestoresmadrid.core.imputaciones.model.vo.ImputacionHorasVO;
import org.gestoresmadrid.core.imputaciones.model.vo.TipoImputacionVO;
import org.gestoresmadrid.oegam2comun.imputaciones.model.service.ServicioImputacionHorasInt;
import org.gestoresmadrid.oegam2comun.imputaciones.views.bean.ImputacionHorasBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;

@Service(value="servicioImputacionHoras")
@Transactional
public class ServicioImputacionHorasImpl implements ServicioImputacionHorasInt, Serializable{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private TipoImputacionHorasDao tipoImputacionHorasDaoImpl;
	
	@Autowired 
	private ImputacionHorasDao imputacionHorasDaoImpl;
	
	@Autowired
	private UsuarioDao usuarioDAOImpl;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	@Override
	public List<TipoImputacionVO> getListaTipoImputacion() {
		TipoImputacionVO tipoImputacionVO = new TipoImputacionVO();
		return tipoImputacionHorasDaoImpl.buscarTipoImputaciones(tipoImputacionVO);
	}

	@Override
	public UsuarioVO getUsuarioAcceso() {
		return usuarioDAOImpl.getUsuario(utilesColegiado.getIdUsuarioSession());
	}

	@Override
	public String getDescripcionTipoImputacion(Long idTipo) {
		List<TipoImputacionVO> listaTipoImputacionVO = getListaTipoImputacion();
		for(TipoImputacionVO tipoImputacionVO : listaTipoImputacionVO){
			if(tipoImputacionVO.getIdTipoImputacion().equals(idTipo)){
				return tipoImputacionVO.getDescripcion();
			}
		}
		return null;
	}

	@Override
	public ResultBean guardarImputacion(ImputacionHorasBean imputacionHorasBean) {
		if(imputacionHorasBean.getDescImputacion() == null || imputacionHorasBean.getDescImputacion().equals(""))
			imputacionHorasBean.setDescImputacion(getDescripcionTipoImputacion(imputacionHorasBean.getIdTipoImputacion()));
			
		ImputacionHorasVO imputacionHorasVO = convertirBeanToEntity(imputacionHorasBean);
		ResultBean resultBean = new ResultBean();
		
		List<ImputacionHorasVO> listaImputacionHorasVO = imputacionHorasDaoImpl.getImputacionesPorFechaUsuario(imputacionHorasVO);
		
		BigDecimal totalHoras = imputacionHorasVO.getHoras();
		
		for(ImputacionHorasVO impuHorasVO : listaImputacionHorasVO){
			totalHoras = totalHoras.add(impuHorasVO.getHoras());
		}
		
		if(totalHoras.compareTo(new BigDecimal(8)) <= 0){
			imputacionHorasVO = imputacionHorasDaoImpl.guardarImputacion(imputacionHorasVO);
			resultBean.setMensaje("Imputacion registrada correctamente.");
			return resultBean;
		}else{
			resultBean.setError(true);
			resultBean.setMensaje("NO puede imputar mas de 8 horas para la fecha seleccionada.");
			return resultBean;
		}	
			
		
	}

	private ImputacionHorasVO convertirBeanToEntity(ImputacionHorasBean imputacionHorasBean) {
		ImputacionHorasVO imputacionHorasVO = new ImputacionHorasVO();
		
		Date fecha = imputacionHorasBean.getFecha().getFechaInicio();
		
		imputacionHorasVO.setFechaImputacion(fecha);		
		imputacionHorasVO.setHoras(imputacionHorasBean.getHoras());
		imputacionHorasVO.setDescImputacion(imputacionHorasBean.getDescImputacion());
		
		TipoImputacionVO tipoImputacionVO = new TipoImputacionVO();
		tipoImputacionVO.setIdTipoImputacion(imputacionHorasBean.getIdTipoImputacion());
		tipoImputacionVO.setDescripcion(imputacionHorasBean.getDescImputacion());
		
		imputacionHorasVO.setTipoImputacionVO(tipoImputacionVO);
		
		UsuarioVO usuarioVO = new UsuarioVO();
		usuarioVO.setIdUsuario(utilesColegiado.getIdUsuarioSession());
		imputacionHorasVO.setUsuario(usuarioVO);
		
		return imputacionHorasVO;
	}

	public ResultBean borrarImputaciones(List<String> listaPorImputacion) {
		ResultBean resultBean = new ResultBean();
		
		for(String aux:listaPorImputacion){
			ImputacionHorasVO voAux = new ImputacionHorasVO();
			voAux.setIdImputacionHoras(new Long(aux));
			imputacionHorasDaoImpl.borrar(voAux);
		}
		
		resultBean.setError(false);
		
		return resultBean;
	}



}
