package org.gestoresmadrid.oegam2comun.impresion.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.docPermDistItv.model.enumerados.TipoDistintivo;
import org.gestoresmadrid.core.tipoPermDistItv.model.enumerado.TipoDocumentoImprimirEnum;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;

import escrituras.beans.ResultBean;
import trafico.servicio.implementacion.CriteriosImprimirTramiteTraficoBean;
import trafico.utiles.enumerados.TipoImpreso;

public interface ServicioImpresionMatriculacion extends Serializable {

	ResultBean imprimirEnProcesoListaBastidores(String[] numsExpedientes);

	ResultBean imprimirMatriculacionPorExpedientes(String[] numsExpedientes, CriteriosImprimirTramiteTraficoBean criteriosImprimir, BigDecimal idUsuario, Boolean tienePermisoImpresionPdf417);

	ResultBean imprimirListadoBastidores(String[] numsExpedientes);

	ResultBean imprimirMatw(String[] numsExpedientes, BigDecimal idUsuario, TipoImpreso tipoImpreso);

	ResultBean imprimirMatwProceso(String[] numExpedientes, BigDecimal idUsuario, TipoImpreso tipoImp);

	ResultadoPermisoDistintivoItvBean generarInformeDocImpresoGestoria(List<TramiteTrafMatrVO> listaTramitesMatrBBDD, TipoDocumentoImprimirEnum tipoDocumento, TipoDistintivo tipoDistintivo, String docId, Boolean esMoto);

	ResultadoPermisoDistintivoItvBean generarInformeDocImpresoPermEitvGestoria(List<TramiteTrafMatrVO> listaTramitesMatrBBDD,TipoDocumentoImprimirEnum tipoDocumento, TipoDistintivo tipoDistintivo, String docId, Boolean esMoto);

	ResultadoPermisoDistintivoItvBean generarInformeDocImpresoPermEitvColegio(List<TramiteTrafMatrVO> listaTramitesMatrBBDD,TipoDocumentoImprimirEnum tipoDocumento, TipoDistintivo tipoDistintivo, String docId, Boolean esMoto);

}
