package org.gestoresmadrid.oegam2comun.distintivo.model.service;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.docPermDistItv.model.vo.DocPermDistItvVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ConsultaDistintivoDgtBean;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.GestionDistintivoDgtBean;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ResultadoDistintivoDgtBean;

public interface ServicioGestionDistintivoDgt extends Serializable {

	public static final String RUTA_FICH_TEMP = "RUTA_ARCHIVOS_TEMP";
	public final String NOMBRE_HOST_SOLICITUD_NODO_2 = "nombreHostSolicitudProcesos2";

	List<GestionDistintivoDgtBean> convertirListaEnBeanPantalla(List<DocPermDistItvVO> listaBBDD);

	void borrarFichero(File fichero);

	ResultadoDistintivoDgtBean solicitarDstvBloque(String codSeleccionados, BigDecimal idUsuario);

	ResultadoDistintivoDgtBean cambiarEstadoDstvBloque(String codSeleccionados, BigDecimal estadoNuevo, BigDecimal idUsuario);

	List<ConsultaDistintivoDgtBean> convertirListaEnBeanPantallaConsulta(List<TramiteTrafMatrVO> listaBBDD);

	ResultadoDistintivoDgtBean generarDemandaDistintivos(String[] codSeleccionados, BigDecimal idUsuario,Boolean tienePermisosAdmin);

	ResultadoDistintivoDgtBean generarDistintivos(String[] numExpedientes, BigDecimal idUsuario,Boolean tienePermisosAdmin);

	ResultadoDistintivoDgtBean revertir(String[] numExpedientes, BigDecimal idUsuarioDeSesion, Boolean tienePermisoAdmin);

	ResultadoDistintivoDgtBean desasignar(String codSeleccionados, BigDecimal idUsuario);

}