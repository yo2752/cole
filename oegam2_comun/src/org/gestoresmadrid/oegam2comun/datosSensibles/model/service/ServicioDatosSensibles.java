package org.gestoresmadrid.oegam2comun.datosSensibles.model.service;

import java.io.File;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.core.datosSensibles.model.vo.DatosSensiblesBastidorVO;
import org.gestoresmadrid.core.datosSensibles.model.vo.DatosSensiblesMatriculaVO;
import org.gestoresmadrid.core.datosSensibles.model.vo.DatosSensiblesNifVO;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.model.enumerados.TipoImportacionBastidores;
import org.gestoresmadrid.oegam2comun.datosSensibles.views.beans.BastidorErroneoBean;
import org.gestoresmadrid.oegam2comun.datosSensibles.views.beans.DatosSensiblesAgrupados;
import org.gestoresmadrid.oegam2comun.datosSensibles.views.beans.DatosSensiblesBean;
import org.gestoresmadrid.oegam2comun.datosSensibles.views.beans.EvolucionDatosSensiblesBean;
import org.gestoresmadrid.oegam2comun.datosSensibles.views.beans.ResultadoAltaBastidorBean;
import org.gestoresmadrid.oegam2comun.datosSensibles.views.beans.ResultadoImportacionSantanderBean;
import org.gestoresmadrid.oegam2comun.datosSensibles.views.dto.DatosSensiblesBastidorDto;
import org.gestoresmadrid.oegam2comun.datosSensibles.views.dto.RespuestaDatosSensibles;
import org.gestoresmadrid.oegam2comun.datosSensibles.views.dto.ResultadoDatosSensibles;
import org.gestoresmadrid.oegam2comun.santanderWS.views.beans.ResultadoBastidor;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import escrituras.beans.ResultBean;
import utilidades.web.OegamExcepcion;

public interface ServicioDatosSensibles {

	public static final String PROPERTY_KEY_DATOSSENSIBLES_GRUPO = "importacion.datossensibles.usuario.grupo";
	public static final String PROPERTY_KEY_DATOSSENSIBLES_ID_CONTRATO = "importacion.datossensibles.usuario.idcontrato";
	public static final String PROPERTY_KEY_DATOSSENSIBLES_NUM_COLEGIADO = "importacion.datossensibles.usuario.numcolegiado";
	public static final String PROPERTY_KEY_DATOSSENSIBLES_ID_USUARIO = "importacion.datossensibles.usuario.idusuario";
	public static final String PROPERTY_KEY_DATOSSENSIBLES_APELLIDOS_NOMBRE = "importacion.datossensibles.usuario.nombre";
	public static final String PORPERTY_GRUPO_VOVN = "importacion.datossensibles.usuario.grupo";

	DatosSensiblesBastidorVO getDatosSensibles(String xmlEnviar);

	RespuestaDatosSensibles getllamadaWSDatosSensibles(DatosSensiblesBastidorVO datosSensiblesBastidorVO, ColaBean solicitud) throws RemoteException, MalformedURLException, ServiceException,
			OegamExcepcion;

	String obtenerGrupo(BigDecimal idUsuario) throws OegamExcepcion;

	List<DatosSensiblesAgrupados> getListaDatosSensibles(DatosSensiblesBean datosSensiblesBean) throws OegamExcepcion;

	List<String> existenDatosSensiblesPorExpedientes(BigDecimal[] numerosExpedientes, BigDecimal idUsuario) throws OegamExcepcion;

	List<DatosSensiblesAgrupados> getListaBastidores(DatosSensiblesBean datosSensiblesBean) throws OegamExcepcion;

	DatosSensiblesBean getDatosSensiblesEntidadesFinancieras(String tipoAgrupacion);

	DatosSensiblesMatriculaVO getdatosSensiblesMatricula(String xmlEnviar);

	ResultBean comprobarDatosImportacion(File fichero, DatosSensiblesBean datosSensiblesBean);

	PaginatedList crearListaPaginated(List<DatosSensiblesAgrupados> list, String resultadosPorPagina, int page, String dir, String columdefectImportados);

	Boolean getFlagDisabled(DatosSensiblesBean datosSensiblesBean);

	ResultBean comprobarDatosSensibles(DatosSensiblesBean datosSensiblesBean);

	ResultadoDatosSensibles guardarDatosSensibles(DatosSensiblesBean datosSensiblesBean, BigDecimal idContrato, String numColegiado, BigDecimal idUsuario, String apellidosNombre)
			throws OegamExcepcion;

	ResultadoDatosSensibles guardarDatoSensibleBastidor(DatosSensiblesBean datosSensiblesBean, BigDecimal idContrato, String numColegiado, BigDecimal idUsuario, String apellidosNombre,
			String origenCambio) throws OegamExcepcion;

	ResultadoDatosSensibles importarBastidor(DatosSensiblesBean datosSensiblesBean, FicheroBean fichero, BigDecimal idContrato, String numColegiado, BigDecimal idUsuario, String apellidosNombre,
			String origenCambio) throws OegamExcepcion;

	ResultBean eliminarNifes(String indices, DatosSensiblesBean datosSensiblesBean, BigDecimal idContrato, BigDecimal idUsuario) throws OegamExcepcion;

	ResultBean eliminarBastidores(String indices, DatosSensiblesBean datosSensiblesBean, BigDecimal idContrato, BigDecimal idUsuario, String origenCambio) throws OegamExcepcion;

	ResultBean eliminarDatosSensible(String indices, DatosSensiblesBean datosSensiblesBean, BigDecimal idContrato, BigDecimal idUsuario) throws OegamExcepcion;

	ResultBean generarYEnviarExcel(Map<String, List<ResultadoAltaBastidorBean>> resultadoEjecucionComleta, String proceso, List<FicheroBean> ficherosImportados, String recipent,
			String direccionesOcultas, String subject) throws OegamExcepcion;

	ResultadoDatosSensibles importarBastidor(DatosSensiblesBean datosSensiblesBean, FicheroBean fichero, BigDecimal idContrato, String numColegiado, BigDecimal idUsuario, String apellidosNombre,
			List<ResultadoAltaBastidorBean> listaResultadosBastidor, String origenCambio) throws OegamExcepcion;

	void enviarCorreoNoSeEncontraronFicherosFTP(String procesos, String tipo, String recipent, String direccionesOcultas);

	void enviarCorreoError(String error, String proceso, String recipent, String direccionesOcultas);

	DatosSensiblesBastidorVO getBastidorVOPorBastidor(String bastidor);

	boolean comprobarBastidorCache(String bastidor);

	String[] getDatosUsuarios(String grupo, BigDecimal idContrato, String num_colegiado, BigDecimal id_usuario, String apellidos_nombre, Boolean esAdmin);

	ResultadoDatosSensibles actualizarBastidorExistente(DatosSensiblesBean datosSensiblesBean, BigDecimal idUsuario, String origenCambio) throws OegamExcepcion;

	PaginatedList crearListaResultadosImportarErroneosPaginated(List<BastidorErroneoBean> listaBastidoresErroneos, String resultadosPorPaginaErroneos, int pageError, String dirError,
			String columdefectImportados);

	ResultBean getListaBastidoresErroneos(String bastidoresErroneos, String grupoBastidores);

	ResultadoDatosSensibles enviarPeticion(List<DatosSensiblesBastidorDto> listaBastidoresDto, BigDecimal idContrato, BigDecimal idUsuario);

	void enviarCorreoAvisoLiberacionBastidores(String proceso, String bastidor, String accion);

	void enviarCorreoAvisoLiberacionNif(String proceso, String bastidor, String accion);

	void enviarCorreoAvisoLiberacionMatricula(String proceso, String bastidor, String accion);

	List<DatosSensiblesBastidorVO> buscarPorBastidor(String bastidor);

	List<DatosSensiblesNifVO> buscarPorNif(String objetoAdesbloquear);

	ResultadoBastidor operacionesBastidorWS(DatosSensiblesBastidorVO datosSensiblesBastidorVO, String idContrato, String idUsuario, TipoImportacionBastidores tipoOperacio);

	DatosSensiblesBastidorVO getBastidorPorId(String numeroBastidor, String idGrupo);

	ResultBean activarDatosSensible(String indices, DatosSensiblesBean datosSensiblesBean, BigDecimal idContrato, BigDecimal idUsuario) throws OegamExcepcion;

	List<DatosSensiblesMatriculaVO> buscarPorMatricula(String matricula);

	ResultBean eliminarMatriculas(String indices, DatosSensiblesBean datosSensiblesBean, BigDecimal idContrato, BigDecimal idUsuario, String origenCambio) throws OegamExcepcion;

	List<EvolucionDatosSensiblesBean> getEvolucionDatosSensibles(String tipo, String numero, String grupo);

	void envioMail(String texto, String asunto, String tipo) throws OegamExcepcion;

	void importarBastidoresFTPSantander(FicheroBean fichero, ResultadoImportacionSantanderBean resultado, Boolean esRetail);

	ResultBean altaBastidoresWS(String[] bastidores, String tipoOperacion, String tipoFinanciacion, String tipoAlerta);
}
