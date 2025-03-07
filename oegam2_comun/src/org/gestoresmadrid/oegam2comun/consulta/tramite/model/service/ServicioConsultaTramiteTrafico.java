package org.gestoresmadrid.oegam2comun.consulta.tramite.model.service;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.oegam2comun.ficheroSolicitud05.beans.ResultadoFicheroSolicitud05Bean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.ResultadoCertificadoTasasBean;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.beans.ResultadoConsultaEEFF;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.beans.ResultadoConsultaJustProfBean;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultCambioEstadoBean;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoMatriculacionBean;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDto;
import org.gestoresmadrid.oegamComun.solicitudNRE06.beans.ResultadoSolicitudNRE06Bean;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.oegamDocBase.view.bean.ResultadoDocBaseBean;

import escrituras.beans.ResultBean;
import trafico.beans.ResumenErroresFicheroMOVE;
import utilidades.web.OegamExcepcion;

public interface ServicioConsultaTramiteTrafico extends Serializable {

	public final String NOMBRE_HOST = "nombreHostProceso";
	public final String NOMBRE_HOST_SOLICITUD_NODO_2 = "nombreHostSolicitudProcesos2";
	public final String NOMBRE_ZIP = "JUSTIFICANTES_PETICION";
	public final String RUTA_FICH_TEMP = "RUTA_ARCHIVOS_TEMP";

	ResultBean consultaEitv(String[] numsExpedientes, BigDecimal idUsuario, BigDecimal idContrato);

	ResultadoCertificadoTasasBean generarCertificadoDesdeConsultaTramite(String[] numsExpedientes, BigDecimal idContrato);

	File getFicheroCertificadosTasa(String ficheroDescarga);

	ResultadoConsultaJustProfBean generarJustificanteProfDesdeConsultaTramite(String[] numsExpedientes, BigDecimal idUsuario, String motivoJustificantes, String documentoJustificantes,
			String diasValidezJustificantes);

	ResultadoConsultaJustProfBean forzarJustificantesProf(String[] numsExpedientes, BigDecimal idUsuario);

	ResultadoConsultaJustProfBean imprimirJustificantesProf(String[] numsExpedientes);

	ResultBean cambiarEstadoFinalizadoErrorBloque(String[] numsExpedientes, BigDecimal idUsuario);

	ResultBean liberarDocBaseNive(String[] numsExpedientes, BigDecimal idUsuarioDeSesion);

	ResultadoConsultaEEFF consultaEEFFBloque(String[] numsExpedientes, BigDecimal idUsuario);

	ResultadoConsultaEEFF liberacionEEFFBloque(String[] numsExpedientes, BigDecimal idUsuario);

	ResultadoFicheroSolicitud05Bean generarFicheroSolicitud05DesdeConsultaTramite(String[] numExpedientes, BigDecimal idUsuarioDeSesion);

	ResultadoFicheroSolicitud05Bean descargarFichero05(String nombreFichero);

	TramiteTrafDto getTramiteDto(BigDecimal bigDecimal, boolean b);

	ResultBean comprobarPrevioAClonarTramites(String[] codSeleccionados);

	ResultBean duplicarTramite(BigDecimal[] codSeleccionados, BigDecimal idUsuario, String desasignarTasaAlDuplicar);

	ResultadoMatriculacionBean descargarFicheros576(String[] sNumsExpedientes);

	void borrarFichero(File fichero);

	ResultadoPermisoDistintivoItvBean generarDistintivos(String codSeleccionados, BigDecimal idUsuario, Boolean tienePermisoImpresionDstvB, Boolean tienePermisoImpresionDstvC,
			Boolean tienePermisoAdmin);

	ResultBean validarMismoTipoTramiteGenJustificante(String listaExpedientes);

	ResultBean autorizarImpresionBTV(String[] numsExpedientes, Boolean tienePermisoAdmin);

	ResultadoDocBaseBean generarDocBase(String numsExpedientes, BigDecimal idUsuario, Boolean tienePermisoAdmin);

	ResultCambioEstadoBean cambiarEstadoBloque(String codSeleccionados, String estadoNuevo, BigDecimal idUsuario);

	ResultCambioEstadoBean cambiarEstadoBloque(String[] codSeleccionados, String valorEstadoCambio, BigDecimal idUsuarioDeSesion);

	ResultCambioEstadoBean eliminarTramites(String codSeleccionados, BigDecimal idUsuario);

	ResultCambioEstadoBean eliminar(BigDecimal numExpediente, BigDecimal idUsuario);

	ResultadoPermisoDistintivoItvBean generarDistintivos(String[] codSeleccionados, BigDecimal idUsuario,
			Boolean tienePermisoImpresionDstvB, Boolean tienePermisoImpresionDstvC, Boolean tienePermisoAdmin);

	ResultBean cambiarEstadoFinalizadoErrorBloque(String codSeleccionados, BigDecimal idUsuario);

	ResultBean liberarDocBaseNive(String codSeleccionados, BigDecimal idUsuario);

	ResultadoFicheroSolicitud05Bean generarFicheroSolicitud05DesdeConsultaTramite(String codSeleccionados, BigDecimal idContrato);

	ResultadoConsultaEEFF consultaEEFFBloque(String codSeleccionados, BigDecimal idUsuario);

	ResultadoSolicitudNRE06Bean tramitarNRE06(String[] codSeleccionados, BigDecimal idUsuario) throws OegamExcepcion;

	ResultadoBean cambiarFechaPresentacion(String[] numExpedientes, BigDecimal idUsuario, Long idContrato);

	ResultadoFicheroSolicitud05Bean generarFicheroMOVE(String codSeleccionados, List<ResumenErroresFicheroMOVE> listaResumenErroresFicheroMOVE);

	ResultBean modificarReferenciaPropia(String[] numsExpedientes, BigDecimal idUsuario, String nuevaReferenciaPropia);

	ResultadoBean desasignarTasaCau(String[] numsExpedientes, BigDecimal idUsuario);

	ResultBean asignarTasaXml(String[] codSeleccionados, BigDecimal bigDecimal, Long idContratoSession) throws OegamExcepcion;
	
}
