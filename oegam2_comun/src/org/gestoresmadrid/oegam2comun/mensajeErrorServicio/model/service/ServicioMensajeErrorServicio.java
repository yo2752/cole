package org.gestoresmadrid.oegam2comun.mensajeErrorServicio.model.service;

import java.util.Date;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.gestoresmadrid.oegam2comun.mensajeErrorServicio.view.bean.ResultadoMensajeErrorServicio;
import org.gestoresmadrid.oegam2comun.mensajeErrorServicio.views.dto.MensajeErrorServicioDto;

import utilidades.estructuras.Fecha;
import utilidades.estructuras.FechaFraccionada;

/**
 * @author ext_fjcl
 */
public interface ServicioMensajeErrorServicio {

	List<MensajeErrorServicioDto> getListaMensajeErrorServicio(Date fecha);

	ResultadoMensajeErrorServicio obtenerExcel(Fecha fecha);

	ResultadoMensajeErrorServicio generarExcel(List<MensajeErrorServicioDto> lista);

	ResultadoMensajeErrorServicio guardar(MensajeErrorServicioDto mensaje);

	ResultadoMensajeErrorServicio borrar(List<MensajeErrorServicioDto> lista);

	ResultadoMensajeErrorServicio guardarFichero(XSSFWorkbook workbook);

	ResultadoMensajeErrorServicio generarExcelListadoMensajesErrorServicio(FechaFraccionada fecha);

}
