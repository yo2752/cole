package org.gestoresmadrid.oegam2comun.ivtmMatriculacion.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.ivtmMatriculacion.model.vo.IvtmMatriculacionVO;
import org.gestoresmadrid.oegam2comun.ivtmMatriculacion.model.view.dto.IvtmMatriculacionDto;
import org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmConsultaMatriculacionDto;
import org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmDatosSalidaMatriculasWS;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafMatrDto;

import trafico.beans.jaxb.matriculacion.DatosIvtm;
import trafico.beans.jaxb.matw.FORMATOGA;
import trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION;
import utilidades.web.OegamExcepcion;
import escrituras.beans.ResultBean;

public interface ServicioIvtmMatriculacion extends Serializable {

	public static final String URL_APLICACION = "<urlAplicacion>";
	public static final String ID_EMISOR = "<idEmisor>";
	public static final String IDENTIFICADOR = "<identificador>";
	public static final String GESTOR = "<gestor>";
	public static final String DC = "<dc>";
	public static final String FECHA_CONTROL = "<fechaControl>";
	public static final String IMPORTE = "<importe>";
	public static final String DISTRITO_MUNICIPAL = "<distritoMunicipal>";
	public static final String NIF = "<nif>";
	public static final String ENTORNO = "<entorno>";
	public static final String TIPO_SERVICIO = "<tipoServicio>";
	public static final String APLICACION = "<aplicacion>";
	public final String NOMBRE_HOST_SOLICITUD = "nombreHostSolicitud";

	ResultBean guardarIvtm(IvtmMatriculacionDto ivtmMatriculacionDto);

	ResultBean puedeGenerarAutoliquidacion(BigDecimal numExpediente);

	List<String> validarMatriculacion(TramiteTrafMatrDto tramiteDto);

	IvtmMatriculacionVO getIvtmPorExpediente(BigDecimal numExpediente);

	IvtmMatriculacionDto getIvtmPorExpedienteDto(BigDecimal numExpediente);

	ResultBean validarTramite(BigDecimal numExpediente) throws OegamExcepcion;

	ResultBean validarTramitePago(BigDecimal numExpediente) throws OegamExcepcion;

	BigDecimal generarIdPeticion(String numColegiado);

	String crearUrl(boolean desdeConsulta, IvtmMatriculacionDto ivtmMatriculacionDto, TramiteTrafMatrDto tramiteTrafMatrDto);

	// Importación
	ResultBean guardarDatosImportados(MATRICULACION matriculacionGA, BigDecimal numExp);

	ResultBean validarFORMATOIVTMGA(FORMATOGA ga, boolean tienePermisoIVTM);

	DatosIvtm obtenerDatosParaImportacion(BigDecimal numExpediente, boolean tienePermisoIVTM);

	IvtmDatosSalidaMatriculasWS recuperarMatriculasWS(
			org.gestoresmadrid.core.ivtmMatriculacion.model.beans.IvtmDatosEntradaMatriculasWS datosEntrada);

	ResultBean encolarConsultar(IvtmConsultaMatriculacionDto ivtmConsultaMatriculacionDto, BigDecimal idUsuario);

	BigDecimal guardarDatosConsulta(IvtmConsultaMatriculacionDto ivtmConsultaMatriculacionDto);
}