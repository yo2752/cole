package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.docPermDistItv.model.vo.DocPermDistItvVO;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.GestionPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;

public interface ServicioGestionDocPrmDstvFichas extends Serializable{

	ResultadoPermisoDistintivoItvBean imprimirDoc(String docsId, String jefaturaImpr, BigDecimal idUsuario, String ipConexion);

	ResultadoPermisoDistintivoItvBean descargarDoc(String sDocIds);

	void borrarFichero(File fichero);

	Boolean esDocPermisos(String codSeleccionados);

	ResultadoPermisoDistintivoItvBean reactivarDoc(String codSeleccionados, BigDecimal idUsuario);

	List<GestionPermisoDistintivoItvBean> convertirListaEnBeanPantalla(List<DocPermDistItvVO> lista);

	ResultadoPermisoDistintivoItvBean cambiarEstado(String codSeleccionados, BigDecimal estadoNuevo, BigDecimal idUsuario);

	ResultadoPermisoDistintivoItvBean impresionAutoImpr(String codSeleccionados, String jefaturaImpr,BigDecimal idUsuario);

	ResultadoPermisoDistintivoItvBean reactivarDocJefatura(String codSeleccionados, String jefaturaImpr, BigDecimal idUsuario);

	ResultadoPermisoDistintivoItvBean anular(String codSeleccionados, BigDecimal idUsuario, String ipConexion);

	ResultadoPermisoDistintivoItvBean reiniciarDoc(String codSeleccionados, BigDecimal idUsuario);

}
