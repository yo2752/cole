package org.gestoresmadrid.oegamImportacion.trafico.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;

public interface ServicioTramiteTraficoMatriculacionImportacion extends Serializable {

	public static String NUMEXPEDIENTE = "NUMEXPEDIENTE";
	public static String ESTADO_VALIDAR = "estadoValidar";
	public static String TITULAR_LIBERACION = "titularLiberacionDto";
	public static String NOMBRE_HOST_PROCESOS_3 = "nombreHostSolicitudProcesos3";
	public static final String antiguoDatosSensibles = "datosSensibles.antiguo";
	public static final String NOMBRE_HOST_SOLICITUD = "nombreHostSolicitud";
	public static final String NOMBRE_HOST_SOLICITUD2 = "nombreHostSolicitudProcesos2";
	public static final String DIRECCION_MAIL_GENERAR_PERMISO = "solicitud.generar.permiso.mail.bcc";
	public static final String SUBJECT_PERMISO = "subject.generar.permiso";
	public static final String SUBJECT_DISTINTIVO = "subject.generar.distintivo";
	public static final String SUBJECT_EITV = "subject.generar.eitv";

	public static final String CONTRATO_COLEGIO = "contrato.colegio";
	public static final String USUARIO_ADMIN = "usuario.admin";

	public static final String CODIGO_FUNCION_FICHA_TECNICA = "OT13M";
	public static final String CODIGO_APLICACION_TRAFICO = "OEGAM_TRAF";

	TramiteTrafMatrVO getTramiteMatriculacionVO(BigDecimal numExpediente, boolean permisoLiberacion, boolean tramiteCompleto);

	List<TramiteTrafMatrVO> getListaTramitesMatriculacion(BigDecimal[] listaNumsExpedientes, Boolean tramiteCompleto);

	//ContratoDto getContratoTramite(String numExpediente);

	List<TramiteTrafMatrVO> getTramitePorMatriculaContrato(String matricula, Long idContrato);

	List<TramiteTrafMatrVO> getListaTramitesPorMatricula(String matricula);

}
