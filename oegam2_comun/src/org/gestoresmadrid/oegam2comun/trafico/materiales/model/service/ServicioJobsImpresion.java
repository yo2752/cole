package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.docPermDistItv.model.vo.DocPermDistItvVO;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;

public interface ServicioJobsImpresion extends Serializable{

	ResultadoPermisoDistintivoItvBean imprimirDistintivos(DocPermDistItvVO docPermDistItvVO, BigDecimal idUsuario, Boolean esColegio);

	ResultadoPermisoDistintivoItvBean imprimirPermisos(DocPermDistItvVO docPermDistItvVO, BigDecimal idUsuario, Boolean esColegio);

	ResultadoPermisoDistintivoItvBean imprimirFichas(DocPermDistItvVO docPermDistItvVO, BigDecimal idUsuario, Boolean esColegio);

	ResultadoPermisoDistintivoItvBean imprimirPermisosFichas(DocPermDistItvVO docPermDistItvVO, BigDecimal idUsuario, Boolean esColegio);

	ResultadoPermisoDistintivoItvBean imprimirDistintivosJefatura(DocPermDistItvVO docPermDistItvVO, BigDecimal idUsuario, String jefaturaImpr);

	ResultadoPermisoDistintivoItvBean imprimirPermisosJefatura(DocPermDistItvVO docPermDistItvVO, BigDecimal idUsuario, String jefaturaImpr);

	ResultadoPermisoDistintivoItvBean imprimirFichasJefatura(DocPermDistItvVO docPermDistItvVO, BigDecimal idUsuario, String jefaturaImpr);

}
