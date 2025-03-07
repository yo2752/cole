package org.gestoresmadrid.oegam2comun.facturacionDistintivo.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.docPermDistItv.model.vo.DocPermDistItvVO;
import org.gestoresmadrid.core.facturacionDistintivo.model.vo.FacturacionDistintivoVO;
import org.gestoresmadrid.oegam2comun.facturacionDistintivo.model.view.bean.FacturacionDstvBean;
import org.gestoresmadrid.oegam2comun.facturacionDistintivo.model.view.bean.FacturacionDstvFilterBean;
import org.gestoresmadrid.oegam2comun.facturacionDistintivo.model.view.bean.ResultadoFactDstvBean;
import org.gestoresmadrid.oegam2comun.facturacionDistintivo.model.view.dto.FacturacionDstvIncDto;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;

public interface ServicioFacturacionDistintivo extends Serializable{

	ResultadoPermisoDistintivoItvBean guardarFacturacionDocumento(DocPermDistItvVO docPermDistItvVO);

	ResultadoPermisoDistintivoItvBean borrarFacturacion(Long idDoc);

	List<FacturacionDstvBean> convertirListaEnBeanPantallaConsulta(List<FacturacionDistintivoVO> list);

	ResultadoFactDstvBean guardarIncidenciaDstv(String codSeleccionados,FacturacionDstvIncDto facturacionDstvInc, BigDecimal idUsuario);

	ResultadoFactDstvBean generarExcel(FacturacionDstvFilterBean facturacionDstv);
	
	ResultadoFactDstvBean generarExcelDetallado(FacturacionDstvFilterBean facturacionDstv);

	ResultadoFactDstvBean descargarExcel(String nombreFichero, Date fechaGen);
}
