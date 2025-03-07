package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;

public interface ServicioDetalleDocPrmDstvFichaDgt extends Serializable{

	ResultadoPermisoDistintivoItvBean getTramitesDocPrmDstvFicha(String docId, int page, String resultadosPorPagina);

}
