package org.gestoresmadrid.oegamLegalizaciones.service;

import java.io.File;
import java.io.Serializable;
import java.text.ParseException;
import java.util.List;

import org.gestoresmadrid.core.legalizacion.model.vo.LegalizacionCitaVO;
import org.gestoresmadrid.oegamLegalizaciones.view.beans.ResultadoLegalizacionesBean;
import org.gestoresmadrid.oegamLegalizaciones.view.dto.LegalizacionCitaDto;

import utilidades.estructuras.Fecha;
import utilidades.web.OegamExcepcion;

public interface ServicioLegalizacionesCita extends Serializable {

	public LegalizacionCitaDto getPeticion(LegalizacionCitaDto legalizacion) throws ParseException;

	public List<File> getDocumentacion(LegalizacionCitaDto legalizacionCitaDto) throws OegamExcepcion, ParseException;

	public List<LegalizacionCitaDto> getListadoDiario(String numColegiado, Fecha fechaListado) throws Throwable;

	public String transformToXML(List<LegalizacionCitaDto> listaLeg);

	public ResultadoLegalizacionesBean borrarPeticiones(String idPeticion, boolean esAdmin) throws Exception;

	public ResultadoLegalizacionesBean solDocumentaciones(List<LegalizacionCitaVO> listaSeleccionados) throws Exception;

	public ResultadoLegalizacionesBean solLegalizaciones(List<LegalizacionCitaVO> listaSeleccionados, Fecha fechaLegalizacion, boolean esAdmin) throws Exception;

	public ResultadoLegalizacionesBean cambiarEstados(List<LegalizacionCitaVO> listaSeleccionados, String cambioEstado) throws Exception;

	public List<LegalizacionCitaVO> obtenerSeleccionados(String[] codSeleccionados) throws Exception;

	public ResultadoLegalizacionesBean guardarPeticion(LegalizacionCitaDto legDto, File fileUpload, String fileUploadFileName, boolean esAdmin);

}
