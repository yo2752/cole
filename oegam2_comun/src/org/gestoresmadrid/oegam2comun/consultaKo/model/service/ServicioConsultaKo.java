package org.gestoresmadrid.oegam2comun.consultaKo.model.service;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.consultaKo.model.vo.ConsultaKoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegam2comun.consultaKo.view.bean.ConsultaKoBean;
import org.gestoresmadrid.oegam2comun.consultaKo.view.bean.ResultadoConsultaKoBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;

public interface ServicioConsultaKo extends Serializable {

	public final String RUTA_FICH_TEMP = "RUTA_ARCHIVOS_TEMP";

	public final String NOMBRE_ZIP = "CONSULTAS_KO";

	List<ConsultaKoBean> convertirListaEnBeanPantallaConsulta(List<ConsultaKoVO> lista);

	void borrarZipKO(File fichero);

	ResultadoConsultaKoBean validar(String codSeleccionados, BigDecimal idUsuario, String idJefatura);

	ResultadoPermisoDistintivoItvBean crearConsultaKoTramite(TramiteTraficoVO tramiteTraficoVO, String tipo, Date fecha);

	ResultadoConsultaKoBean generarExcelDemanda(String codSeleccionados, BigDecimal idUsuarioDeSesion);

	ResultadoConsultaKoBean procesarPeticionGenExcelKoDemanda(Long idUsuario);

	ResultadoConsultaKoBean generarExcelImprNocturno();

	void actualizarEstadoErrorConsultaKoProceso(List<Long> listaIdsConsultaKoError, Long idUsuario);

	void finalizarConErrorDemanda(Long idUsuario);

	ResultadoConsultaKoBean descargarExcelKO(String codSeleccionados);

	ResultadoConsultaKoBean cambiarEstado(String codSeleccionados, String estadoNuevo, Long idUsuario);

}