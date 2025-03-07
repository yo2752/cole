package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service;

import java.io.Serializable;

import org.gestoresmadrid.core.trafico.materiales.model.vo.AutorVO;
import org.gestoresmadrid.core.trafico.materiales.model.vo.UsuarioColegioVO;
import org.gestoresmadrid.core.trafico.model.vo.JefaturaTraficoVO;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest.UsuarioInfoRest;

public interface ServicioConsultaUsuarioConsejo extends Serializable {
	JefaturaTraficoVO obtenerJefaturaProvincial(String jefatura); 
	UsuarioColegioVO  getDtoFromInfoRest(UsuarioInfoRest usuarioInfoRest);
	UsuarioColegioVO  obtenerUsuarioColegio();
	AutorVO           obtenerAutorFromUsuarioColegio();
}
