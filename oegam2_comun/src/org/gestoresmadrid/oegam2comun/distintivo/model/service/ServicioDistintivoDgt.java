package org.gestoresmadrid.oegam2comun.distintivo.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ResultadoDistintivoDgtBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;

public interface ServicioDistintivoDgt extends Serializable {

	public final String RUTA_FICH_TEMP = "RUTA_ARCHIVOS_TEMP";
	public final String NOMBRE_ZIP = "CONSULTAS_PERMISO_DSTV_ITV";
	public static final String SOLICITUD_POR_PROCESO = "SOL_PROCESO_MATW";
	public static final String SOLICITUD_DSTV = "DSTV";
	public static final String SOLICITUD_DUPLICADO_DSTV = "DUPLICADO";
	public static final String IMPRESION_PROCESO_DSTV = "IMPRESION_PROCESO";
	public static final String NOMBRE_FICH_IMPRESION = "DocumentosImpresos";
	public static final String SUBJECT_PERMISO_IMPRESO = "subject.generar.permiso.impreso";
	public static final String SUBJECT_DISTINTIVO_IMPRESO = "subject.generar.distintivo.impreso";
	public static final String SUBJECT_EITV_IMPRESO = "subject.generar.eitv.impreso";
	public final String NOMBRE_HOST_SOLICITUD_2 = "nombreHostSolicitudProcesos2";
	public static final int longitudSecuencialDocPermDstvEitv = 9;
	public static final String NOMBRE_HOST = "nombreHostProceso";

	ResultadoDistintivoDgtBean solicitarDistintivo(BigDecimal numExpediente, BigDecimal idUsuario);

	ResultadoDistintivoDgtBean cambiarEstado(BigDecimal numExpediente, BigDecimal estadoNuevo, Boolean accionesAsociadas,Boolean esDstv, Date fechaSol, BigDecimal idUsuario);

	ResultadoPermisoDistintivoItvBean solicitarImpresionDistintivo(BigDecimal numExpediente, Boolean tienePermisosAdmin, BigDecimal idUsuario);

	ResultadoDistintivoDgtBean generarDemandaDistintivos(String[] numExpedientes, BigDecimal idUsuario, Boolean tienePermisoAdmin);

	List<TramiteTrafMatrVO> getListaTramitesPorDocId(Long idDocPermDistItv);

	ResultadoDistintivoDgtBean revertirDistintivo(BigDecimal numExpediente, BigDecimal idUsuario, Date fecha);

	ResultadoDistintivoDgtBean guardarPdfDstv(byte[] distintivoPdf, BigDecimal numExpediente);

	ResultadoDistintivoDgtBean generarDistintivoNoche(ContratoDto contrato);

	ResultadoDistintivoDgtBean solicitarDistintivoAntiguo(TramiteTrafMatrVO tramiteTrafMatrVO);

	ResultadoDistintivoDgtBean solicitarDistintivoMasivos(String matricula, Long idContrato, BigDecimal idUsuario);

	ResultadoDistintivoDgtBean solicitarImpresionDstvMasivo(String matricula, Long idContrato, BigDecimal idUsuario);

	ResultadoDistintivoDgtBean solicitarImpresionDemandaMasivo(List<String> matricula, String nifTitular, Long idContrato, BigDecimal idUsuario);

	ResultadoDistintivoDgtBean desasignar(BigDecimal numExpediente, BigDecimal idUsuario);

}