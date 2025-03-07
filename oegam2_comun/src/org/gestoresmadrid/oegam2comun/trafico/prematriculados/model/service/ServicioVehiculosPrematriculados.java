package org.gestoresmadrid.oegam2comun.trafico.prematriculados.model.service;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.trafico.prematriculados.model.vo.VehiculoPrematriculadoVO;
import org.gestoresmadrid.oegam2comun.trafico.prematriculados.view.dto.VehiculoPrematriculadoDto;

import escrituras.beans.ResultBean;

public interface ServicioVehiculosPrematriculados extends Serializable {

	ResultBean validarConsulta(VehiculoPrematriculadoDto vehiculo);
	
	ResultBean consultarEitv(VehiculoPrematriculadoDto vehiculo);
	
	File buscarCaracteristicasDocumento(Long id, Date fecha);
	
	ResultBean guardarXmlRespuestaDatosTecnicos(String data, Long id, Date fecha);
	
	File buscarFichaTecnicaDocumento(Long id, Date fecha);

	List<VehiculoPrematriculadoDto> buscar(Long[] ids, String numColegiado);
	
	String recogerXmlEitv(Long id);

	void generarSolicitudXml(Long longValue);
	
	boolean guardarRespuestaDatosTecnicos(boolean correcta, String respuesta, Long id);
	
	boolean guardarRespuestaFichaTecnica(boolean correcta, String respuesta, Long id);

	ResultBean consultaFichaTecnica(Long id);

	ResultBean guardarSolicitudes(List<VehiculoPrematriculadoDto> listaVehiculos);

	VehiculoPrematriculadoVO buscarVehiculoPrematriculado(Long idVehiculo);

	ResultBean guardarPdfRespuestaDatosTecnicos(String ficha, VehiculoPrematriculadoVO vehiculoPreVO);
}
