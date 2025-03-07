package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service;

import java.io.Serializable;

import org.gestoresmadrid.core.trafico.materiales.model.vo.UsuarioColegioVO;

import escrituras.beans.ResultBean;

public interface ServicioGuardarUsuarioConsejo extends Serializable {
	ResultBean guardarUsuarioConsejo(UsuarioColegioVO usuarioConsejoVO);
}
