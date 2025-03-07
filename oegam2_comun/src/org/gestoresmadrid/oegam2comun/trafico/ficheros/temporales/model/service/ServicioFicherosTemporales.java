package org.gestoresmadrid.oegam2comun.trafico.ficheros.temporales.model.service;

import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.ficheros.temporales.view.dto.FicherosTemporalesDto;
import org.gestoresmadrid.oegam2comun.ficheros.temporales.view.dto.ResultadoFicherosTemp;

import utilidades.estructuras.Fecha;
import utilidades.web.OegamExcepcion;
import escrituras.beans.ResultBean;

public interface ServicioFicherosTemporales {

	ResultBean altaFicheroTemporal(String nombreDocumento,String extension, String tipoDocumento, String subTipo,Fecha fecha, String numColegiado, 
			BigDecimal idUsuario, BigDecimal idContrato) throws OegamExcepcion;

	ResultadoFicherosTemp getFicheroImprimir(FicherosTemporalesDto ficheroTemporalDto);

	FicherosTemporalesDto getFicheroTemporalDto(Long idFichero);

	ResultadoFicherosTemp borrarFicheroTemporal(FicherosTemporalesDto ficheroTemporalDto);

	ResultadoFicherosTemp actualizarEstadoFichero(FicherosTemporalesDto ficheroTemporalDto, Integer nuevoEstado);

}
