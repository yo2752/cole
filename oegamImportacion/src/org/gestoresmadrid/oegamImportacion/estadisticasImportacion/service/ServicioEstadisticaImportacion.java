package org.gestoresmadrid.oegamImportacion.estadisticasImportacion.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.importacionFichero.model.vo.EstadisticaImportacionFicherosVO;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoBean;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoImportacionBean;
import org.gestoresmadrid.oegamImportacion.estadisticasImportacion.view.bean.ConsultaEstadisticasImportBean;
import org.gestoresmadrid.oegamImportacion.estadisticasImportacion.view.bean.EstadisticaImportacionBean;
import org.gestoresmadrid.oegamImportacion.estadisticasImportacion.view.bean.EstadisticaImportacionFilterBean;

public interface ServicioEstadisticaImportacion extends Serializable {

	List<EstadisticaImportacionBean> convertirListaEnBeanPantallaConsulta(List<EstadisticaImportacionFicherosVO> list);

	ResultadoImportacionBean guardarEstadistica(EstadisticaImportacionFicherosVO estadisticaImporFich);

	ResultadoImportacionBean actualizarEstadistica(EstadisticaImportacionFicherosVO estadisticaImporFich);

	ResultadoImportacionBean generarExcel(EstadisticaImportacionFilterBean estadisticaImportacionFilterBean);

	ResultadoImportacionBean descargarExcel(String nombreFichero, Date fechaGener);

	ResultadoImportacionBean generarEstadisticaDinamica(EstadisticaImportacionFilterBean estadisticaImportacionFilterBean);

	List<EstadisticaImportacionFicherosVO> listaImportacionesEjecutandose(Long idContrato, String tipo);

	boolean hayImportacionesEjecutandose(Long idContrato, String tipo);

	int numeroPeticionesEjecutandosePorTipo(String tipo);

	boolean superaLimiteImportaciones(String tipo);

	boolean contratoConPermiso(Long idContrato, String tipo);

	List<ConsultaEstadisticasImportBean> convertirListaEnBeanPantallaConsultaEst(List<EstadisticaImportacionFicherosVO> listaBBDD);

	EstadisticaImportacionFicherosVO getEstadisticaImportacion(Long idImportacionFich);

	ResultadoBean terminarEjecucion(String idSeleccioneados);
}
