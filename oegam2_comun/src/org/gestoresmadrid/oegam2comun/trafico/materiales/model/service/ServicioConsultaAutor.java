package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service;

import java.io.Serializable;

import org.gestoresmadrid.core.trafico.materiales.model.vo.AutorVO;

public interface ServicioConsultaAutor extends Serializable {

	AutorVO getAutorByPrimaryKey(Long autorId);
}
