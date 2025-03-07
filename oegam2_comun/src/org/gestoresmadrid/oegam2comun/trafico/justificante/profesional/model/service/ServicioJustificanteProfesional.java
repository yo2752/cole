package org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados.EstadoJustificante;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.vo.JustificanteProfVO;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.beans.ResultadoConsultaJustProfBean;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.dto.JustificanteProfDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDuplicadoDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafTranDto;
import org.gestoresmadrid.oegamComun.colegio.view.dto.ColegioDto;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.JefaturaTraficoDto;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;
import org.hibernate.criterion.ProjectionList;

import es.globaltms.gestorDocumentos.bean.ByteArrayInputStreamBean;
import escrituras.beans.ResultBean;
import utilidades.web.OegamExcepcion;

public interface ServicioJustificanteProfesional extends Serializable {

	public static String NUM_PETICIONES = "NUMPETICIONES";
	public static String NUM_EXPEDIENTE = "NUM_EXPEDIENTE";
	public static String JUSTIFICANTE = "JUSTIFICANTE";
	public static String ID_JUSTIFICANTE_INTERNO = "IDJUSTIFICANTEINTERNO";
	public static String DTO_JUSTIFICANTE = "justificanteDto";
	public static String TRAMITE_TRAF_JUST_PROF = "tramiteTrafJustProf";
	public static String TITULAR_JUST_PROF = "titularJustProf";
	public static String VEHICULO_JUST_PROF = "vehiculoJustProf";
	public static String PRESENTADOR_JUST_PROF = "titularJustProf";
	public static String TIPO_TRAMITE_JUSTIFICANTE = "tipoTramiteJustfProf";
	public static String FICHERO_JP = "ficheroJP";
	public static String NOMBRE_FICHERO_JP = "nombreFicheroJP";
	public static String NOMBRE_ZIP = "Justificantes_Profesionales";
	public static String ES_JP_OK = "esOk";
	public static String ES_JP_FORZAR = "esForzar";
	public static String ES_JP_ERROR_FECHA = "esErrorFechas";
	public static String NOMBRE_XML = "nombreXml";
	public static String JUSTIFICANTE_REPETIDO = "Por la instrucción 14/V - 107, una vez ya emitido un justificante provisional, no se podrá emitir uno nuevo con idénticos adquiriente y matrícula.";
	public static String JUSTIFICANTE_POR_FECHA = "Por la instrucción 14/V - 107, una vez ya emitido un justificante provisional, solo se podrá emitir uno nuevo si se tiene el permiso de circulación a nombre titular del primer justificante. En dicho caso contacte con el Colegio.";
	public static String JUSTIFICANTE_REPETIDO_MATRICULA = "Por la instrucción 14/V - 107, una vez ya emitido un justificante provisional, no se podrá emitir uno nuevo para la misma matrícula.";

	public static final String JUSTIF_NO_FINALIZADOS = " JustificantesNoFinalizados ";
	public static final Integer COL_DESCRIPCION = 7;
	public static final String NEXPEDIENTE = " Nº Expediente ";
	public static final Integer COL_NEXPEDIENTE = 0;
	public static final String NIDENJUSTIFICANTE = " Identificador Justificante ";
	public static final Integer COL_NIDENJUSTIFICANTE = 1;
	public static final String NNUMJUSTIFICANTES = " Nº de justificantes ";
	public static final Integer COL_NNUMJUSTIFICANTES = 2;
	public static final String NMATRICULA = " Matricula ";
	public static final Integer COL_NMATRICULA = 3;
	public static final String NGESTOR = " Gestor ";
	public static final Integer COL_NGESTOR = 4;
	public static final String NDNITITULAR = " DNI Titular ";
	public static final Integer COL_NDNITITULAR = 5;
	public static final String NFECHACREACION = " Fecha Creación Justificante ";
	public static final Integer COL_NFECHACREACION = 6;

	ResultBean generarJustificanteTransmision(TramiteTrafTranDto tramiteTransmision, JustificanteProfDto justificanteProfDto, BigDecimal idUsuario, boolean admin) throws OegamExcepcion;

	ResultBean generarJustificanteDuplicado(TramiteTrafDuplicadoDto tramiteDuplicado, JustificanteProfDto justificanteProfDto, BigDecimal idUsuario, boolean admin) throws OegamExcepcion;

	ResultBean crearSolicitud(String nombreProceso, Long idJustificanteInterno, BigDecimal idUsuario, BigDecimal idContrato, String nombreXml) throws OegamExcepcion;

	ResultBean verificar(JustificanteProfDto justificanteProfDto, BigDecimal idUsuario, BigDecimal idContrato) throws OegamExcepcion;

	boolean hayJustificante(Long idJustificanteInterno, BigDecimal numExpediente, EstadoJustificante estadoJustificante);

	ResultBean guardarJustificanteProfesional(JustificanteProfDto justificanteProfDto, BigDecimal numExpediente, BigDecimal idUsuario, BigDecimal estadoAnterior, BigDecimal estado,
			String comentariosEvolucion);

	ResultBean guardarJustificanteProfesional(JustificanteProfVO justificanteProfVO, BigDecimal numExpediente, BigDecimal idUsuario, BigDecimal estadoAnterior, BigDecimal estado,
			String comentariosEvolucion);

	List<JustificanteProfVO> getJustificantes(String numColegiado, String matricula, String nif);

	ResultBean validarDatosObligatorios(VehiculoDto vehiculo, IntervinienteTraficoDto titular, JefaturaTraficoDto jefatura);

	ResultBean cambiarEstadoAnularJustificante(JustificanteProfDto justificanteProfDto, BigDecimal numExpediente);

	/**
	 * Recupera el dto de justificante profesional por su ID
	 * @param idJustificanteInterno
	 * @return
	 */
	JustificanteProfDto getJustificanteProfesional(Long idJustificanteInterno);

	JustificanteProfDto getJustificanteProfesionalPorNumExpediente(BigDecimal numExpediente, BigDecimal estadoJustificante);

	JustificanteProfVO getJustificanteProfesionalPorCodigoVerificacion(String codigoVerificacion);

	List<String> obtenerExpedientesSinJustificantesEnEstado(Long[] idsJustificantesInternos, EstadoJustificante estadoJustificantes);

	List<String> obtenerExpedientesSinJustificantesEnEstadoPorNumExpediente(String[] numExpedientes, EstadoJustificante estadoJustificantes);

	List<JustificanteProfVO> obtenerJustificantesNoFinalizados(Date fechaHoy, Date fechaMenosDiezDias);

	ResultBean executeEmailJustificantesNoFinalizados();

	/**
	 * Recupera el interviniente titular si es duplicado, o adquiriente si es transmision
	 * @param tramiteTrafico
	 * @return
	 */
	IntervinienteTraficoDto getTitularAdquiriente(TramiteTrafDto tramiteTrafico);

	ByteArrayInputStreamBean imprimirJustificantes(Long[] listaIdJustificantesInternos);

	ByteArrayInputStreamBean imprimirJustificantesPorNumExpediente(String[] listaNumExpediente);

	/****** nuevos action y pantallas justificantes *****/

	ResultBean getJustificanteProfesionalPantalla(Long idJustificanteInterno);

	ResultBean pendienteAutorizacionColegioJP(Long idJustificanteInterno, BigDecimal idUsuario, Boolean esPteConsulta);

	JustificanteProfVO getJustificanteProfesionalPorIDInternoVO(Long idJustificanteInterno);

	ResultBean forzarJP(Long idJustificanteInterno, BigDecimal idUsuario, Boolean esForzadoConsulta);

	ResultBean imprimirJP(Long idJustificanteInterno);

	ResultBean anularJP(Long idJustificanteInterno, BigDecimal idUsuario, Boolean esAnularConsulta);

	ResultBean generarJP(JustificanteProfDto justificanteProfDto, IntervinienteTraficoDto titular, boolean checkIdFuerzasArmadas, BigDecimal idUsuarioDeSesion, Boolean tienePermisoAdmin);

	ResultBean getTitularNif(String nif, String numColegiado);

	ResultBean getVehiculoColegiado(String matricula, String numColegiado);

	ColegioDto getColegioContrato(Long idContrato);

	ResultBean guardarJustifProfWS(Long idJustificanteInterno, BigDecimal numExpediente, BigDecimal idJustificante, byte[] justificantePdf);

	ResultBean crearJustificanteProfDtoToConsultaTramite(String numExp, String motivo, String documento, String diasValidez);

	ResultBean guardarJustificanteConsultaTramite(JustificanteProfDto justificanteProfDto, BigDecimal idUsuario);

	ResultBean getJustificanteProfPorNumExpediente(BigDecimal numExpediente, Boolean justificanteCompleto, BigDecimal estadoJustificante);

	void cambiarEstadoConEvolucion(Long idJustificanteInterno, EstadoJustificante estadoNuevo, BigDecimal idUsuario);

	void devolverCreditos(Long idJustificanteInterno, BigDecimal idUsuario);

	ResultBean validarGenerarJP(JustificanteProfDto justificanteProfDto, IntervinienteTraficoDto titular, Date fechaInicio);

	ResultadoConsultaJustProfBean descargaJP(String nombreJustificantes);

	ResultBean imprimirConsultaJP(Long idJustificanteInterno);

	JustificanteProfVO getJustificanteProfesionalPorIdJustificante(BigDecimal idJustificante);

	JustificanteProfVO getJustificanteProfesionalPorNumExpediente(BigDecimal idTramite, Boolean justificanteCompleto, BigDecimal estadoJustificante);

	void cambiarEstadoConEvolucionSega(BigDecimal numExpediente, EstadoJustificante estadoNuevo, BigDecimal idUsuario);

	void cambiarEstadoConEvolucionSega2(BigDecimal numExpediente, EstadoJustificante estadoNuevo, BigDecimal idUsuario);

	void devolverCreditosSega(BigDecimal numExpediente, BigDecimal idUsuario, BigDecimal estadoJustificante);

	void cambiarEstadoConEvolucionSega(BigDecimal numExpediente, EstadoJustificante estadoAnterior, EstadoJustificante estadoJustificanteNuevo, BigDecimal idUsuario);

	ResultBean validarGenracionJPDesdeTransmisionODuplicado(JustificanteProfDto justificanteProf, IntervinienteTraficoDto titular, Boolean checkFuerzasArmadas);

	ResultBean crearJustificanteDesdeTransmisionODuplicado(JustificanteProfDto justificanteProfDto, IntervinienteTraficoDto titular, Boolean esForzar, BigDecimal idUsuario);

	boolean cambiarEstadoConEvolucionPorNumExpediente(BigDecimal numExpediente, EstadoJustificante estadoNuevo, BigDecimal idUsuario);

	int numeroElementosListadoJustificantesNoUltimados(Object filter, String[] fetchModeJoinList, List<AliasQueryBean> entitiesJoin, ProjectionList listaProyecciones);

	public ResultBean generarExcelListadoJustificantesNoUltimados(Object filter);

}