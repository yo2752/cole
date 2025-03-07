package org.gestoresmadrid.oegam2.view.decorator;

import java.math.BigDecimal;
import java.util.Date;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.proceso.view.dto.ProcesoDto;

public class GestorProcesosDecorator extends TableDecorator {

	public String getEstado() {
		ProcesoDto fila = (ProcesoDto) getCurrentRowObject();
		if (fila.getEstado().compareTo(BigDecimal.ZERO) == 0) {
			return "<span style=\"background-color:red;color:white\">PARADO</span>";
		} else {
			return "<span size='5' style=\"background-color:green;color:white\" >ACTIVO</span>";
		}
	}

	public String getAccion() {
		ProcesoDto fila = (ProcesoDto) getCurrentRowObject();
		if ((ProcesosEnum.Excel_Bajas_Alcala.getValorEnum().equals(fila.getProceso())
				|| ProcesosEnum.Excel_Bajas_Alcorcon.getValorEnum().equals(fila.getProceso())
				|| ProcesosEnum.Excel_Bajas_Avila.getValorEnum().equals(fila.getProceso())
				|| ProcesosEnum.Excel_Bajas_Ciudad_Real.getValorEnum().equals(fila.getProceso())
				|| ProcesosEnum.Excel_Bajas_Cuenca.getValorEnum().equals(fila.getProceso())
				|| ProcesosEnum.Excel_Bajas_Guadalajara.getValorEnum().equals(fila.getProceso())
				|| ProcesosEnum.Excel_Bajas_Madrid.getValorEnum().equals(fila.getProceso())
				|| ProcesosEnum.Excel_Bajas_Segovia.getValorEnum().equals(fila.getProceso())
				|| ProcesosEnum.Excel_Duplicados_Alcala.getValorEnum().equals(fila.getProceso())
				|| ProcesosEnum.Excel_Duplicados_Alcorcon.getValorEnum().equals(fila.getProceso())
				|| ProcesosEnum.Excel_Duplicados_Avila.getValorEnum().equals(fila.getProceso())
				|| ProcesosEnum.Excel_Duplicados_Ciudad_Real.getValorEnum().equals(fila.getProceso())
				|| ProcesosEnum.Excel_Duplicados_Cuenca.getValorEnum().equals(fila.getProceso())
				|| ProcesosEnum.Excel_Duplicados_Guadalajara.getValorEnum().equals(fila.getProceso())
				|| ProcesosEnum.Excel_Duplicados_Madrid.getValorEnum().equals(fila.getProceso())
				|| ProcesosEnum.Excel_Duplicados_Segovia.getValorEnum().equals(fila.getProceso()))
				&& fila.getEstado().compareTo(BigDecimal.ZERO) == 0) {
			Boolean esMensajeHoras = Boolean.FALSE;
			if (new Date().getHours() > 9) {
				esMensajeHoras = Boolean.TRUE;
			}
			return "<input type=\"button\" class=\"botonpeque\" id=\"btnAccion" + fila.getOrden() + "\" value=\"Activar"
					+ "\" onclick=\"activarExcel(" + fila.getOrden() + "," + esMensajeHoras + ")\" />";
		} else if (fila.getEstado().compareTo(BigDecimal.ZERO) == 0) {
			return "<input type=\"button\" class=\"botonpeque\" id=\"btnAccion" + fila.getOrden() + "\" value=\"Activar"
					+ "\" onclick=\"activar(" + fila.getOrden() + ")\" />";
		} else {
			return "<input type=\"button\" class=\"botonpeque\" id=\"btnAccion" + fila.getOrden() + "\" value=\"Detener"
					+ "\" onclick=\"parar(" + fila.getOrden() + ")\" />";
		}
	}

}