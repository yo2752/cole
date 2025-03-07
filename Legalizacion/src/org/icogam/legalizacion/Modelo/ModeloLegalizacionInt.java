package org.icogam.legalizacion.Modelo;

import java.io.File;
import java.text.ParseException;
import java.util.List;

import org.icogam.legalizacion.beans.LegalizacionCita;
import org.icogam.legalizacion.dto.LegalizacionCitaDto;

import escrituras.beans.ResultBean;
import utilidades.estructuras.Fecha;
import utilidades.web.OegamExcepcion;

public interface ModeloLegalizacionInt{

	/**
	 * 
	 * @param legalizacion
	 * @return
	 */
	public List<LegalizacionCita> listarPeticiones(LegalizacionCitaDto legalizacion) throws ParseException;

	public LegalizacionCitaDto getPeticion(LegalizacionCitaDto legalizacion) throws ParseException;

	public LegalizacionCitaDto actualizar(LegalizacionCitaDto legDto) throws ParseException;

	public LegalizacionCitaDto guardar(LegalizacionCitaDto legDto) throws ParseException, OegamExcepcion;

	public boolean guardarDocumentacion(LegalizacionCitaDto legDto, File fileUpload, String extension) throws OegamExcepcion, ParseException;

	public List<File> getDocumentacion(LegalizacionCitaDto legDto) throws OegamExcepcion, ParseException;

	public ResultBean borrarPeticion (int idPeticion) throws Exception;

	public ResultBean solicitarDocumentacion(int idPeticion) throws OegamExcepcion;

	public void actualizarSolicitado(int idPeticion);

	public void envioMailInformandoMAECDocumentacionSubida(LegalizacionCitaDto legDto) throws OegamExcepcion;

	public List<LegalizacionCitaDto> getListadoDiario(String numColegiado, Fecha fechaListado) throws Throwable;

	public String transformToXML(List<LegalizacionCitaDto> listaLeg);

	public void cambiarEstado(String[] idsPeticion, String cambioEstado);

	public ResultBean permiteModificarPeticion(int idPeticion, Fecha fechaLegalizacion)throws Exception;

	public ResultBean permiteGuardarPeticion(LegalizacionCitaDto legalizacionCitaDto) throws ParseException, Throwable;

	public ResultBean confirmarFechaLegalizacion(String idPeticion, Fecha fechaLegalizacion) throws Exception;
}