package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.docPermDistItv.model.vo.DocPermDistItvVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;

import utilidades.web.OegamExcepcion;

public interface ServicioImpresionPermisosFichasDgt extends Serializable {

	public static final String NOMBRE_FICHERO_IMPRESION_MASIVA = "ImpresionMasivaDistintivos_";
	public static final String NOMBRE_FICHERO_IMPRESION = "ImpresionDistintivos_";
	public static final String NOMBRE_PERM_DSTV_EITV_IMPRESION = "DocumentosImpresos_";

	ResultadoPermisoDistintivoItvBean impresionPermisoFichas(ColaDto solicitud);

	void actualizarEstado(BigDecimal idDoc, EstadoPermisoDistintivoItv estado, String resultadoEjecucion, BigDecimal idUsuario);

	ResultadoPermisoDistintivoItvBean impresionPermisoFichasErroneos(List<byte[]> listaPdf, List<TramiteTraficoVO> listaTramites, DocPermDistItvVO docPermDistItvNuevo, String tipoTramite,
			ContratoVO contrato, DocPermDistItvVO docPermDistItvAnterior, String esConNive) throws OegamExcepcion;

}
