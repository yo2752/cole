package org.gestoresmadrid.oegamImportacion.trafico.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.ivtmMatriculacion.model.vo.IvtmMatriculacionVO;
import org.gestoresmadrid.oegamConversiones.jaxb.matw.DatosIvtm;

public interface ServicioIvtmMatriculacionImportacion extends Serializable {

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

	String guardarIvtm(IvtmMatriculacionVO ivtmMatriculacionVO);

	DatosIvtm obtenerDatosParaImportacion(BigDecimal numExpediente, boolean tienePermisoIVTM);

}
