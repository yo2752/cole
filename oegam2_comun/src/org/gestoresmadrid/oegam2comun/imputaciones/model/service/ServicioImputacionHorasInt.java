package org.gestoresmadrid.oegam2comun.imputaciones.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.imputaciones.model.vo.TipoImputacionVO;
import org.gestoresmadrid.oegam2comun.imputaciones.views.bean.ImputacionHorasBean;

import escrituras.beans.ResultBean;

public interface ServicioImputacionHorasInt extends Serializable {

	public List<TipoImputacionVO> getListaTipoImputacion();

	public UsuarioVO getUsuarioAcceso();

	public String getDescripcionTipoImputacion(Long idTipo);

	public ResultBean guardarImputacion(ImputacionHorasBean imputacionHorasBean);

	public ResultBean borrarImputaciones(List<String> listaPorImputacion);

}