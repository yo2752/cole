package org.gestoresmadrid.oegam2comun.datosSensibles.model.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.handler.HandlerInfo;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.core.datosSensibles.model.dao.DatosSensiblesBastidorDao;
import org.gestoresmadrid.core.datosSensibles.model.dao.DatosSensiblesMatriculaDao;
import org.gestoresmadrid.core.datosSensibles.model.dao.DatosSensiblesNifDao;
import org.gestoresmadrid.core.datosSensibles.model.enumerados.ConstantesA9;
import org.gestoresmadrid.core.datosSensibles.model.vo.DatosSensiblesBastidorPK;
import org.gestoresmadrid.core.datosSensibles.model.vo.DatosSensiblesBastidorVO;
import org.gestoresmadrid.core.datosSensibles.model.vo.DatosSensiblesMatriculaPK;
import org.gestoresmadrid.core.datosSensibles.model.vo.DatosSensiblesMatriculaVO;
import org.gestoresmadrid.core.datosSensibles.model.vo.DatosSensiblesNifPK;
import org.gestoresmadrid.core.datosSensibles.model.vo.DatosSensiblesNifVO;
import org.gestoresmadrid.core.evolucionDatosSensibles.model.dao.EvolucionDatosSensiblesBastidorDao;
import org.gestoresmadrid.core.evolucionDatosSensibles.model.dao.EvolucionDatosSensiblesMatriculaDao;
import org.gestoresmadrid.core.evolucionDatosSensibles.model.dao.EvolucionDatosSensiblesNifDao;
import org.gestoresmadrid.core.evolucionDatosSensibles.model.vo.EvolucionDatosSensiblesBastidorVO;
import org.gestoresmadrid.core.evolucionDatosSensibles.model.vo.EvolucionDatosSensiblesMatriculaVO;
import org.gestoresmadrid.core.evolucionDatosSensibles.model.vo.EvolucionDatosSensiblesNifVO;
import org.gestoresmadrid.core.general.model.dao.UsuarioDao;
import org.gestoresmadrid.core.general.model.vo.GrupoVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.model.enumerados.EstadoBastidor;
import org.gestoresmadrid.core.model.enumerados.TipoBastidor;
import org.gestoresmadrid.core.model.enumerados.TipoBastidorSantander;
import org.gestoresmadrid.core.model.enumerados.TipoFicheroImportacionBastidores;
import org.gestoresmadrid.core.model.enumerados.TipoImportacionBastidores;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.pagination.model.component.impl.PaginatedListImpl;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoDao;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.datos.sensibles.ws.SoapDatosSensiblesWSHandler;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.datosSensibles.model.service.ServicioDatosSensibles;
import org.gestoresmadrid.oegam2comun.datosSensibles.views.beans.BastidorErroneoBean;
import org.gestoresmadrid.oegam2comun.datosSensibles.views.beans.DatosSensiblesAgrupados;
import org.gestoresmadrid.oegam2comun.datosSensibles.views.beans.DatosSensiblesBean;
import org.gestoresmadrid.oegam2comun.datosSensibles.views.beans.EvolucionDatosSensiblesBean;
import org.gestoresmadrid.oegam2comun.datosSensibles.views.beans.ResultadoAltaBastidorBean;
import org.gestoresmadrid.oegam2comun.datosSensibles.views.beans.ResultadoImportacionSantanderBean;
import org.gestoresmadrid.oegam2comun.datosSensibles.views.dto.DatosSensiblesBastidorDto;
import org.gestoresmadrid.oegam2comun.datosSensibles.views.dto.ImportarBastidorDto;
import org.gestoresmadrid.oegam2comun.datosSensibles.views.dto.RespuestaDatosSensibles;
import org.gestoresmadrid.oegam2comun.datosSensibles.views.dto.ResultadoDatosSensibles;
import org.gestoresmadrid.oegam2comun.grupo.model.service.ServicioGrupo;
import org.gestoresmadrid.oegam2comun.santanderWS.views.beans.ResultadoBastidor;
import org.gestoresmadrid.oegam2comun.santanderWS.views.enums.ResultadoSantanderWSEnum;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.utiles.UtilidadesNIFValidator;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.HibernateException;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.gaa9.bsn.BSNServiceLocator;
import com.gaa9.bsn.BSNWS;
import com.gaa9.bsn.BSNWSBindingStub;
import com.gaa9.bsn.BsnRequest;
import com.gaa9.bsn.BsnResponse;

import colas.constantes.ConstantesProcesos;
import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.beans.ResultBean;
import jxl.CellView;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import trafico.datosSensibles.utiles.UtilesVistaDatosSensibles;
import trafico.datosSensibles.utiles.enumerados.TiempoBajaDatosSensibles;
import trafico.datosSensibles.utiles.enumerados.TiposDatosSensibles;
import trafico.utiles.constantes.ConstantesMensajes;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.web.OegamExcepcion;
import utilidades.web.OegamExcepcion.EnumError;

@Service
public class ServicioDatosSensiblesImpl implements ServicioDatosSensibles {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioDatosSensiblesImpl.class);

	@Autowired
	private DatosSensiblesBastidorDao datosSensiblesBastidorDao;

	@Autowired
	private DatosSensiblesMatriculaDao datosSensiblesMatriculaDao;

	@Autowired
	private DatosSensiblesNifDao datosSensiblesNifDao;

	@Autowired
	private EvolucionDatosSensiblesBastidorDao evolucionDatosSensiblesBastidorDao;

	@Autowired
	private EvolucionDatosSensiblesMatriculaDao evoluciondatosSensiblesMatriculaDao;

	@Autowired
	private EvolucionDatosSensiblesNifDao evoluciondatosSensiblesNifDao;

	@Autowired
	private TramiteTraficoDao tramiteTraficoDao;

	@Autowired
	private UsuarioDao usuarioDao;

	@Autowired
	private ServicioCola servicioCola;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private ServicioGrupo servicioGrupo;

	@Autowired
	private Conversor conversor;

	@Autowired
	private ServicioCorreo servicioCorreo;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilidadesNIFValidator utilidadesNIFValidator;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	private static final String NOMBRE_HOST_SOLICITUD = "nombreHostSolicitud";

	private static final String enviarCorreo = "correo.bastidores.enviarCorreo";
	private static final String asuntoCorreo_Alta = "asunto.correo.datosSensibles";
	private static final String asuntoCorreo_Baja = "asunto.correo.datosSensiblesBaja";
	private static final String asuntoCorreo_Error_Fichero = "asunto.correo.datosSensibles.errorFichero";
	private static final String asuntoCorreoBastidores = "asunto.correo.bastidores";
	private static final String asuntoProcesadoCorreoSantanderBastidores = "asunto.procesado.correo.santander";
	private static final String asuntoProcesadoCorreoSantanderNif = "asunto.procesado.correo.santanderNif";
	private static final String asuntoProcesadoCorreoSantanderMatricula = "asunto.procesado.correo.santanderMatricula";
	private static final String destinatarioCorreoBastidores = "destinatario.correo.bastidores";
	private static final String destinatarioCorreoBastidoresOculto = "destinatario.correo.bastidores.oculto";
	private static final String destinatarioCorreoBastidoresConCopia = "datos.sensibles.mail.cc";
	private static final String destinatarioSantanderOperDatoSensibleFR = "destinatario.correo.sensibles.fr";
	private static final String dirOcultasOperDatoSensible = "datos.sensibles.mail.bcc";
	private static final String dirOcultasOperDatoSensibleVN = "datos.sensibles.mail.bvncc";
	private static final String dirOcultasOperDatoSensibleVO = "datos.sensibles.mail.bvocc";
	private static final String dirOcultasOperDatoSensibleDM = "datos.sensibles.mail.bdmcc";
	private static final String dirOcultasOperDatoSensibleFI = "datos.sensibles.mail.bficc";
	private static final String dirOcultasOperDatoSensibleLE = "datos.sensibles.mail.blecc";
	private static final String dirOcultasOperDatoSensibleRE = "datos.sensibles.mail.brecc";
	private static final String dirOcultasOperDatoSensibleFR = "datos.sensibles.mail.bfrcc";

	private static final String activadoEnvioCorreoBastidorVO = "envio.correo.notificacion.bastidorvo";
	private static final String activadoEnvioCorreoBastidorDM = "envio.correo.notificacion.bastidordm";
	private static final String activadoEnvioCorreoBastidorFI = "envio.correo.notificacion.bastidorfi";
	private static final String activadoEnvioCorreoBastidorLE = "envio.correo.notificacion.bastidorle";
	private static final String activadoEnvioCorreoBastidorRE = "envio.correo.notificacion.bastidorre";
	private static final String activadoEnvioCorreoBastidorFR = "envio.correo.notificacion.bastidorfr";
	private static final String subjectOperDatoSensible = "subject.datos.sensibles";
	private static final String subjectOperDatoSensibleFR = "subject.datos.sensiblesFR";

//	private static final String destinatarioCorreoCarterizado = "nuevos.formatos.ficheros.ftp.destinatarioCart";
//	private static final String destinatarioCopiaCorreoCarterizado = "nuevos.formatos.ficheros.ftp.copiaDestinatarioCart";
//	private static final String destinatarioCorreoRetail = "nuevos.formatos.ficheros.ftp.destinatarioRetail";

	// Mantis 19728
	private static final String emailCorreoBastidoresDestinatario = "proceso.correo.liberacion.bastidores.destinatario";

	private static final String encolarTipoBastidor = "datosSensibles.encolar.Bastidor";
	private static final String encolarTipoNif = "datosSensibles.encolar.Nif";
	private static final String encolarTipoMatricula = "datosSensibles.encolar.Matricula";

	private static final int MAX_LINEAS_FICHERO_IMPORTAR = 5000;

	private static final String COMPROBAR_DATOS_SENSIBLES = "comprobar.datos.sensibles.";
	private static final String COMPROBAR_DATOS_SENSIBLES_PATRON = "comprobar.datos.sensibles.patron.";
	private static final String ASUNTO_LIBERACION_DATO_SENSIBLE = "asunto.liberacion.dato.sensible";
	private static final String EMAIL_DESTINATARIO_LIBERACION_DATO_SENSIBLE = "correo.liberacion.dato.sensible.destinatario";

	@Override
	@Transactional
	public DatosSensiblesBastidorVO getDatosSensibles(String xmlEnviar) {
		String[] xml = xmlEnviar.split("_");
		DatosSensiblesBastidorVO bastidorVO = new DatosSensiblesBastidorVO();
		DatosSensiblesBastidorPK bastidorPK = new DatosSensiblesBastidorPK();
		bastidorPK.setBastidor(xml[0]);
		bastidorVO.setId(bastidorPK);
		GrupoVO grupoVO = new GrupoVO();
		grupoVO.setIdGrupo(xml[1]);
		bastidorVO.setGrupo(grupoVO);

		List<DatosSensiblesBastidorVO> lDatosSensiblesBastidor = datosSensiblesBastidorDao.buscarPorBastidorYGrupo(bastidorVO);
		if (lDatosSensiblesBastidor == null || lDatosSensiblesBastidor.isEmpty()) {
			return null;
		}
		return lDatosSensiblesBastidor.get(0);
	}

	@Override
	public DatosSensiblesMatriculaVO getdatosSensiblesMatricula(String xmlEnviar) {
		DatosSensiblesMatriculaVO matriculaVO = new DatosSensiblesMatriculaVO();
		DatosSensiblesMatriculaPK matriculaPK = new DatosSensiblesMatriculaPK();
		int ini = xmlEnviar.indexOf("_");
		matriculaPK.setMatricula(xmlEnviar.substring(ini + 1, ini + 8));
		matriculaVO.setId(matriculaPK);

		List<DatosSensiblesMatriculaVO> lDatosSensiblesMatricula = datosSensiblesMatriculaDao.buscarPorMatricula(matriculaVO);
		if (lDatosSensiblesMatricula == null || lDatosSensiblesMatricula.isEmpty()) {
			return null;
		}
		return lDatosSensiblesMatricula.get(0);
	}

	@Override
	public RespuestaDatosSensibles getllamadaWSDatosSensibles(DatosSensiblesBastidorVO datosSensiblesBastidorVO, ColaBean solicitud) throws RemoteException, MalformedURLException, ServiceException,
			OegamExcepcion {
		RespuestaDatosSensibles resultado = null;
		try {
			ResultBean resultBean = getWSDatosSensibles(datosSensiblesBastidorVO, solicitud);

			String mandaCorreo = gestorPropiedades.valorPropertie(enviarCorreo);
			if (mandaCorreo == null) {
				resultado = new RespuestaDatosSensibles();
				resultado.setError(true);
				resultado.setMensajeError("No se puede comprobar si debe de mandar el correo de monitorización.");
			} else {
				if (mandaCorreo.equals("true")) {
					envioMailInformandoResultadoBastidor(resultBean.getListaMensajes().get(0) + "<br>", solicitud.getProceso());
				}
			}
			if (resultBean != null && resultBean.getError()) {
				resultado = new RespuestaDatosSensibles();
				resultado.setError(true);
				resultado.setMensajeError(resultBean.getListaMensajes().get(0));
			}
		} catch (OegamExcepcion eg) {
			resultado = new RespuestaDatosSensibles();
			resultado.setException(new Exception(eg));
		} catch (Exception e) {
			resultado = new RespuestaDatosSensibles();
			resultado.setException(e);
		}
		return resultado;
	}

	private ResultBean getWSDatosSensibles(DatosSensiblesBastidorVO bastidorVO, ColaBean solicitud) throws MalformedURLException, ServiceException, RemoteException {
		ResultBean resultBean = null;
		log.error("Entra en el metodo para enviar el bastidor " + bastidorVO.getId().getBastidor() + " por el web service de SEA");
		String textoMensaje = "";

		log.error("Creación del stub de SEA");
		BSNWSBindingStub stub = getStubDatosSensibles();
		BsnRequest datosSensiblesRequest = new BsnRequest();

		if (new BigDecimal(TipoBastidor.VN.getValorEnum()).equals(bastidorVO.getTipoBastidor())) {
			// VN Bloquea
			if (BigDecimal.ONE.equals(bastidorVO.getEstado())) {
				datosSensiblesRequest.setBloqueado(true);
			} else {
				datosSensiblesRequest.setBloqueado(false);
			}
		} else if (Boolean.TRUE.toString().equalsIgnoreCase(gestorPropiedades.valorPropertie(encolarTipoBastidor))) {
			// VO y DEMO avisa
			if (BigDecimal.ONE.equals(bastidorVO.getEstado())) {
				datosSensiblesRequest.setAviso(true);
			} else {
				datosSensiblesRequest.setAviso(false);
			}
		}
		datosSensiblesRequest.setIdentificador(bastidorVO.getId().getBastidor());

		log.error("Antes de invocar el web service de datos sensibles para el bastidor: " + bastidorVO.getId().getBastidor());
		BsnResponse datosSensiblesResponse = stub.gestionarBastidores(datosSensiblesRequest);
		log.error("Recibida respuesta del web de datos sensibles");

		if (datosSensiblesResponse.getResultado()) {
			solicitud.setRespuesta(ConstantesA9.RESULTADO_OK);
			log.error("Respuesta de web service de SEA es OK para el bastidor: " + bastidorVO.getId().getBastidor());
			if (datosSensiblesResponse.getDescripcion().equals(ConstantesA9.EXITO_INSERTAR)) {
				textoMensaje = ConstantesMensajes.MENSAJE_EXISTO_INSERTAR_BASTIDOR_A9;
			} else if (datosSensiblesResponse.getDescripcion().equals(ConstantesA9.EXISTO_DESBLOQUEAR)) {
				textoMensaje = ConstantesMensajes.MENSAJE_EXISTO_ELIMINAR_BASTIDOR_A9;
			} else if (datosSensiblesResponse.getDescripcion().equals(ConstantesA9.EXITO_ACTUALIZAR)) {
				textoMensaje = ConstantesMensajes.MENSAJE_EXISTO_ACTUALIZAR_BASTIDOR_A9;
			}
			textoMensaje = textoMensaje + "BASTIDOR: " + bastidorVO.getId().getBastidor();
			resultBean = new ResultBean(false, textoMensaje);
		} else {
			if (datosSensiblesResponse.getDescripcion() != null && !datosSensiblesResponse.getDescripcion().isEmpty()) {
				textoMensaje = datosSensiblesResponse.getDescripcion();
			}
			textoMensaje += "BASTIDOR: " + bastidorVO.getId().getBastidor();
			resultBean = new ResultBean(true, textoMensaje + "BASTIDOR: " + bastidorVO.getId().getBastidor());
		}
		return resultBean;
	}

	protected BSNWSBindingStub getStubDatosSensibles() throws MalformedURLException, ServiceException {
		URL miURL = new URL(gestorPropiedades.valorPropertie(ConstantesA9.WEBSERVICE_TRANSMISION));
		String timeout = gestorPropiedades.valorPropertie(ConstantesA9.TIMEOUT);
		BSNWS datosSensiblesService = null;
		BSNWSBindingStub stub = null;

		if (miURL != null) {
			log.info(ConstantesA9.TEXTO_ENTIDADESFINANCIERAS_LOG + " miURL: " + miURL.toString());

			BSNServiceLocator datosSensiblesServiceLocator = new BSNServiceLocator();
			javax.xml.namespace.QName portName = new javax.xml.namespace.QName(miURL.toString(), datosSensiblesServiceLocator.getBSNServiceWSDDServiceName());

			@SuppressWarnings("unchecked")
			List<HandlerInfo> list = datosSensiblesServiceLocator.getHandlerRegistry().getHandlerChain(portName);
			HandlerInfo logHandlerInfo = new HandlerInfo();
			logHandlerInfo.setHandlerClass(SoapDatosSensiblesWSHandler.class);
			list.add(logHandlerInfo);

			datosSensiblesService = datosSensiblesServiceLocator.getBSNService(miURL);
			log.info(ConstantesA9.TEXTO_ENTIDADESFINANCIERAS_LOG + " generado servicio.");
		}

		stub = (BSNWSBindingStub) datosSensiblesService;
		log.info(ConstantesA9.TEXTO_ENTIDADESFINANCIERAS_LOG + " generado stub.");

		stub.setTimeout(timeout != null && !timeout.equals("") ? Integer.parseInt(timeout) : ConstantesA9.TIMEOUT_TRANSMISION);
		log.info(ConstantesA9.TEXTO_ENTIDADESFINANCIERAS_LOG + " timeout: " + timeout + ", si es nulo igual a " + ConstantesA9.TIMEOUT_TRANSMISION);

		return stub;
	}

	protected void envioMailInformandoResultadoBastidor(String textoAniadir, String proceso) throws OegamExcepcion {
		try {
			String subject = null;
			String destinatario = null;
			String destinatarioOculto = null;

			subject = gestorPropiedades.valorPropertie(asuntoCorreoBastidores) + " " + proceso;
			destinatario = gestorPropiedades.valorPropertie(destinatarioCorreoBastidores);
			destinatarioOculto = gestorPropiedades.valorPropertie(destinatarioCorreoBastidoresOculto);

			ResultBean resultEnvio;

			resultEnvio = servicioCorreo.enviarCorreo(textoAniadir, null, null, subject, destinatario, null, destinatarioOculto, null);
			if (resultEnvio == null || resultEnvio.getError())
				throw new OegamExcepcion("Se ha producido un error al enviar el mail informando el resultado de bastidores en el proceso " + proceso + ".", EnumError.error_00002);

		} catch (OegamExcepcion | IOException e) {
			throw new OegamExcepcion("Se ha producido un error al enviar el mail informando el resultado de bastidores en el proceso " + proceso + ".", EnumError.error_00002);
		}

	}

	public void envioMail(String texto, String asunto, String tipo) throws OegamExcepcion {
		String subject = gestorPropiedades.valorPropertie(asunto);
		if (tipo != null) {
			subject += " del Tipo " + tipo;
		}
		String destinatario = gestorPropiedades.valorPropertie(destinatarioCorreoBastidores);
		String destinatarioOculto = gestorPropiedades.valorPropertie(destinatarioCorreoBastidoresOculto);
		ResultBean resultEnvio;
		try {
			resultEnvio = servicioCorreo.enviarCorreo(texto, null, null, subject, destinatario, null, destinatarioOculto, null);
			if (resultEnvio == null || resultEnvio.getError())
				throw new OegamExcepcion("Se ha producido un error al enviar el mail informando el resultado de bastidores en el proceso .", EnumError.error_00002);
		} catch (IOException e) {
			throw new OegamExcepcion("Se ha producido un error al enviar el mail informando el resultado de bastidores en el proceso .", EnumError.error_00002);
		}

	}

	@Override
	@Transactional(rollbackFor = OegamExcepcion.class)
	public ResultadoDatosSensibles guardarDatosSensibles(DatosSensiblesBean datosSensiblesBean, BigDecimal idContrato, String numColegiado, BigDecimal idUsuario, String apellidosNombre)
			throws OegamExcepcion {
		ResultadoDatosSensibles resultado = null;
		try {
			if (datosSensiblesBean == null) {
				return resultado = new ResultadoDatosSensibles(false, true, "Se ha producido un error con los datos de pantalla introducidos, vuelva a intentarlo de nuevo.");
			}
			if (datosSensiblesBean.getGrupo() == null || datosSensiblesBean.getGrupo().equals("") || datosSensiblesBean.getGrupo().equals("-1")) {
				return resultado = new ResultadoDatosSensibles(false, true, "El Usuario debe de estar asociado a un grupo para poder dar de alta un Datos Sensible.");
			}
			if (TiposDatosSensibles.Bastidor.toString().equals(datosSensiblesBean.getTipoAgrupacion())) {
				resultado = guardarBastidor(datosSensiblesBean, idContrato, numColegiado, idUsuario, apellidosNombre, "APLICACION");
				if (resultado == null) {
					resultado = new ResultadoDatosSensibles(false, false, "El bastidor " + datosSensiblesBean.getTextoAgrupacion() + " se ha guardado correctamente para su grupo de usuarios");
					// envioMail("Se ha dado de alta el siguiente Bastidor como dato sensible en la aplicación: " + datosSensiblesBean.getTextoAgrupacion() + "<br>", asuntoCorreo_Alta, TipoBastidor.convertirTexto(datosSensiblesBean.getTipoControl()));
				}
			} else if (TiposDatosSensibles.Matricula.toString().equals(datosSensiblesBean.getTipoAgrupacion())) {
				resultado = guardarMatricula(datosSensiblesBean, idContrato, idUsuario);
				if (resultado == null) {
					resultado = new ResultadoDatosSensibles(false, false, "La matricula " + datosSensiblesBean.getTextoAgrupacion() + " se ha guardado correctamente para su grupo de usuarios");
					// envioMail("Se ha dado de alta la siguiente Matricula como dato sensible en la aplicación: " + datosSensiblesBean.getTextoAgrupacion() + "<br>", asuntoCorreo_Alta, null);
				}
			} else if (TiposDatosSensibles.Nif.toString().equals(datosSensiblesBean.getTipoAgrupacion())) {
				resultado = guardarNif(datosSensiblesBean, idUsuario);
				if (resultado == null) {
					resultado = new ResultadoDatosSensibles(false, false, "El Nif " + datosSensiblesBean.getTextoAgrupacion() + " se ha guardado correctamente para su grupo de usuarios");
					// envioMail("Se ha dado de alta el siguiente NIF como dato sensible en la aplicación: " + datosSensiblesBean.getTextoAgrupacion() + "<br>", asuntoCorreo_Alta, null);
				}
			}
		} catch (Exception e) {
			throw new OegamExcepcion(e.getMessage());
		}
		return resultado;
	}

	protected ResultadoDatosSensibles guardarNif(DatosSensiblesBean datosSensiblesBean, BigDecimal idUsuario) {
		log.info("ServicioDatosSensibles guardarNif guardar Inicio");
		ResultadoDatosSensibles resultado = null;
		DatosSensiblesNifVO datosSensiblesNifVO = convertirDatosSensiblesBeanToDatosSensiblesNifVOAlta(datosSensiblesBean);
		if (datosSensiblesNifVO != null) {
			List<DatosSensiblesNifVO> listaNif = datosSensiblesNifDao.existeNif(datosSensiblesNifVO.getId().getNif(), datosSensiblesNifVO.getId().getIdGrupo());

			if (listaNif == null || listaNif.isEmpty()) {
				datosSensiblesNifDao.guardar(datosSensiblesNifVO);
				evoluciondatosSensiblesNifDao.guardar(dameEvolucionDatosSensiblesNifVO(datosSensiblesNifVO, "ALTA", idUsuario, "APLICACION"));
			} else {
				resultado = new ResultadoDatosSensibles(false, true, "Se ha producido un error al guardar. El NIF " + datosSensiblesNifVO.getId().getNif()
						+ " ya existia en el sistema para su grupo de usuarios");
			}
		} else {
			resultado = new ResultadoDatosSensibles(false, true, "No existen datos para el nif introducida.");
		}
		return resultado;
	}

	protected ResultadoDatosSensibles guardarMatricula(DatosSensiblesBean datosSensiblesBean, BigDecimal idContrato, BigDecimal idUsuario) throws OegamExcepcion {
		log.info("ServicioDatosSensibles guardarMatricula guardar Inicio");
		ResultadoDatosSensibles resultado = null;
		DatosSensiblesMatriculaVO datosSensiblesMatriculaVO = convertirDatosSensiblesBeanToDatosSensiblesMatriculaVOAlta(datosSensiblesBean);
		if (datosSensiblesMatriculaVO != null) {
			List<DatosSensiblesMatriculaVO> listMatriculas = datosSensiblesMatriculaDao.existeMatricula(datosSensiblesMatriculaVO.getId().getMatricula(), datosSensiblesMatriculaVO.getId()
					.getIdGrupo());
			if (listMatriculas == null || listMatriculas.isEmpty()) {
				datosSensiblesMatriculaDao.guardar(datosSensiblesMatriculaVO);
				evoluciondatosSensiblesMatriculaDao.guardar(dameEvolucionDatosSensiblesMatriculaVO(datosSensiblesMatriculaVO, "ALTA", idUsuario, "APLICACION"));
			} else {
				resultado = new ResultadoDatosSensibles(false, true, "Se ha producido un error al guardar. La matricula " + datosSensiblesMatriculaVO.getId().getMatricula()
						+ " ya existia en el sistema para su grupo de usuarios");
			}
		} else {
			resultado = new ResultadoDatosSensibles(false, true, "No existen datos para la matricula introducida.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoBastidor operacionesBastidorWS(DatosSensiblesBastidorVO datosSensiblesBastidorVO, String idContrato, String idUsuario, TipoImportacionBastidores tipoOperacion) {
		ResultadoBastidor resultado = new ResultadoBastidor();
		try {
			String xmlEnviar = datosSensiblesBastidorVO.getId().getBastidor() + "_" + datosSensiblesBastidorVO.getId().getIdGrupo();
			if (TipoImportacionBastidores.Alta.equals(tipoOperacion)) {
				datosSensiblesBastidorDao.guardarOActualizar(datosSensiblesBastidorVO);
			} else if (TipoImportacionBastidores.Baja.equals(tipoOperacion)) {
				datosSensiblesBastidorDao.borradoLogico(datosSensiblesBastidorVO);
			}
			ResultBean resultBean = crearSolicitudesProcesos(xmlEnviar, new BigDecimal(idContrato), new BigDecimal(idUsuario));
			if (resultBean != null && resultBean.getError()) {
				for (String mensaje : resultBean.getListaMensajes()) {
					log.error("Ha sucedido un error a la hora de encolar el bastidor, error: " + mensaje);
				}
				resultado.setError(true);
				resultado.setResultadoError(ResultadoSantanderWSEnum.STDWS013);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de realizar las operaciones necesarias para el bastidor : " + datosSensiblesBastidorVO.getId().getBastidor() + ", error: ", e);
			resultado.setError(true);
			resultado.setResultadoError(ResultadoSantanderWSEnum.STDWS012);
		} catch (OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de encolar la solicitud para el bastidor : " + datosSensiblesBastidorVO.getId().getBastidor() + ", error: ", e);
			resultado.setError(true);
			resultado.setResultadoError(ResultadoSantanderWSEnum.STDWS013);
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	protected ResultadoDatosSensibles guardarBastidor(DatosSensiblesBean datosSensiblesBean, BigDecimal idContrato, String numColegiado, BigDecimal idUsuario, String apellidosNombre,
			String origenCambio) throws OegamExcepcion {
		ResultadoDatosSensibles result = null;
		log.info("ServicioDatosSensibles guardarBastidor Inicio");
		String sPropertieEncolar = gestorPropiedades.valorPropertie(encolarTipoBastidor);
		boolean esPosibleEncolar = false;
		if (sPropertieEncolar != null && sPropertieEncolar.equals("true")) {
			esPosibleEncolar = true;
		} else {
			if (TipoBastidor.VN.getValorEnum().equals(datosSensiblesBean.getTipoControl())) {
				esPosibleEncolar = true;
			}
		}
		DatosSensiblesBastidorVO datosSensiblesBastidorVO = convertirDatosSensiblesBeanToDatosSensiblesBastidorVOAlta(datosSensiblesBean, numColegiado, idUsuario, apellidosNombre);
		if (datosSensiblesBastidorVO != null) {
			List<DatosSensiblesBastidorVO> listaBastidores = datosSensiblesBastidorDao.existeBastidor(datosSensiblesBastidorVO.getId().getBastidor(), datosSensiblesBastidorVO.getId().getIdGrupo());
			if (listaBastidores == null || listaBastidores.isEmpty()) {
				String xmlEnviar = datosSensiblesBastidorVO.getId().getBastidor() + "_" + datosSensiblesBastidorVO.getId().getIdGrupo();
				try {
					datosSensiblesBastidorDao.guardar(datosSensiblesBastidorVO);
					evolucionDatosSensiblesBastidorDao.guardar(dameEvolucionDatosSensiblesBastidorVO(datosSensiblesBastidorVO, "ALTA", idUsuario, origenCambio));
				} catch (Exception e) {
					log.error(e);
					throw new OegamExcepcion(EnumError.error_00001);
				}
				if (esPosibleEncolar) {
					ResultBean resultBean = crearSolicitudesProcesos(xmlEnviar, idContrato, idUsuario);
					if (resultBean != null) {
						result = new ResultadoDatosSensibles(true, resultBean.getError());
						result.setListaMensajes(resultBean.getListaMensajes());
					}
				}
			} else {
				DatosSensiblesBastidorVO auxBastidorVO = listaBastidores.get(0);
				if (auxBastidorVO.getEstado().equals(BigDecimal.ZERO)) {
					if (auxBastidorVO.getEstado().equals(new BigDecimal(datosSensiblesBean.getTipoControl()))) {
						result = new ResultadoDatosSensibles(true, false, true);
					} else {
						result = new ResultadoDatosSensibles(true, true, false, "Se ha producido un error al guardar. El bastidor " + datosSensiblesBastidorVO.getId().getBastidor()
								+ " ya existia en el sistema para su grupo de usuarios y con un tipo de control distinto al que desea introducir.");
					}
				} else if (auxBastidorVO.getEstado().equals(BigDecimal.ONE)) {
					result = new ResultadoDatosSensibles(true, true, false, "Se ha producido un error al guardar. El bastidor " + datosSensiblesBastidorVO.getId().getBastidor()
							+ " ya existia en el sistema para su grupo de usuarios");
				}
			}
		} else {
			result = new ResultadoDatosSensibles(true, true, false, "No existen datos para el bastidor.");
		}
		return result;
	}

	protected ResultBean guardarBastidorImportado(DatosSensiblesBean datosSensiblesBean, BigDecimal idContrato, String numColegiado, BigDecimal idUsuario, String apellidosNombre, String origenCambio)
			throws OegamExcepcion {
		ResultBean resultBean = null;
		log.info("ServicioDatosSensibles guardarBastidor Inicio");
		String sPropertieEncolar = gestorPropiedades.valorPropertie(encolarTipoBastidor);
		boolean esPosibleEncolar = false;
		if (sPropertieEncolar != null && sPropertieEncolar.equals("true")) {
			esPosibleEncolar = true;
		} else {
			if (TipoBastidor.VN.getValorEnum().equals(datosSensiblesBean.getTipoControl())) {
				esPosibleEncolar = true;
			}
		}
		DatosSensiblesBastidorVO datosSensiblesBastidorVO = convertirDatosSensiblesBeanToDatosSensiblesBastidorVOAlta(datosSensiblesBean, numColegiado, idUsuario, apellidosNombre);
		if (datosSensiblesBastidorVO != null) {
			String xmlEnviar = datosSensiblesBastidorVO.getId().getBastidor() + "_" + datosSensiblesBastidorVO.getId().getIdGrupo();
			List<DatosSensiblesBastidorVO> listaBastidores = datosSensiblesBastidorDao.existeBastidor(datosSensiblesBastidorVO.getId().getBastidor(), datosSensiblesBastidorVO.getId().getIdGrupo());
			if (listaBastidores == null || listaBastidores.isEmpty()) {
				try {
					datosSensiblesBastidorDao.guardar(datosSensiblesBastidorVO);
					evolucionDatosSensiblesBastidorDao.guardar(dameEvolucionDatosSensiblesBastidorVO(datosSensiblesBastidorVO, "ALTA", idUsuario, origenCambio));
				} catch (Exception e) {
					throw new OegamExcepcion(EnumError.error_00001);
				}
				if (esPosibleEncolar) {
					resultBean = crearSolicitudesProcesos(xmlEnviar, idContrato, idUsuario);
				}
			} else {
				DatosSensiblesBastidorVO auxBastidorVO = listaBastidores.get(0);
				if (auxBastidorVO.getEstado().equals(BigDecimal.ZERO) && auxBastidorVO.getFechaAlta() != null) {
					if (auxBastidorVO.getTipoBastidor().equals(datosSensiblesBastidorVO.getTipoBastidor())) {
						auxBastidorVO.setEstado(BigDecimal.ONE);
						auxBastidorVO.setFechaBaja(null);
						auxBastidorVO.setFechaAlta(Calendar.getInstance().getTime());
						try {
							datosSensiblesBastidorDao.actualizar(auxBastidorVO);
							evolucionDatosSensiblesBastidorDao.guardar(dameEvolucionDatosSensiblesBastidorVO(datosSensiblesBastidorVO, "MODIFICACION", idUsuario, origenCambio));
						} catch (Exception e) {
							throw new OegamExcepcion(EnumError.error_00001);
						}
						if (esPosibleEncolar) {
							resultBean = crearSolicitudesProcesos(xmlEnviar, idContrato, idUsuario);
						}
						if (resultBean == null) {
							resultBean = new ResultBean(false, "El Bastidor " + datosSensiblesBastidorVO.getId().getBastidor() + " ya se encontraba dado de baja para el grupo " + auxBastidorVO
									.getGrupo().getDescGrupo() + " con fecha de alta del " + utilesFecha.formatoFecha("dd/MM/yyyy", auxBastidorVO.getFechaAlta())
									+ " por lo que se ha actualizado su estado y su fecha de alta.");
						}
					} else {
						String mensaje = "El Bastidor " + datosSensiblesBastidorVO.getId().getBastidor() + " ya se encuentra dado de alta y activo para el grupo " + auxBastidorVO.getGrupo()
								.getDescGrupo() + " con fecha de alta del " + utilesFecha.formatoFecha("dd/MM/yyyy", auxBastidorVO.getFechaAlta()) + " como " + TipoBastidor.convertirTexto(
										auxBastidorVO.getTipoBastidor().toString()) + ", por lo que no se puede actualizar como " + TipoBastidor.convertirTexto(datosSensiblesBastidorVO
												.getTipoBastidor().toString());
						resultBean = new ResultBean(true, mensaje);
					}
				} else if (auxBastidorVO.getEstado().equals(BigDecimal.ONE)) {
					String mensaje = "El Bastidor " + datosSensiblesBastidorVO.getId().getBastidor() + " ya se encuentra dado de alta y activo para el grupo " + auxBastidorVO.getGrupo().getDescGrupo()
							+ " con fecha de alta del " + utilesFecha.formatoFecha("dd/MM/yyyy", auxBastidorVO.getFechaAlta());
					resultBean = new ResultBean(true, mensaje);
				}
			}
		} else {
			resultBean = new ResultBean(true, "No existen datos para el bastidor " + datosSensiblesBean.getTextoAgrupacion().toUpperCase());
		}
		return resultBean;
	}

	@Override
	public ResultBean comprobarDatosImportacion(File fichero, DatosSensiblesBean datosSensiblesBean) {
		ResultBean result = null;
		if (fichero == null) {
			result = new ResultBean(true, "Debe seleccionar un fichero para poder importar los datos sensibles.");
			return result;
		}

		if (datosSensiblesBean == null) {
			result = new ResultBean(true, "Ha surgido un error a la hora de importar los Datos sensibles");
			log.error("El bean de los datos sensibles esta vacio");
			return result;
		}

		if (datosSensiblesBean.getTiempoDatosSensibles() == null) {
			result = new ResultBean(true, "Debe seleccionar un el tiempo de caducidad para los datos sensibles.");
			return result;
		}

		if (datosSensiblesBean.getTipoFichero() == null) {
			result = new ResultBean(true, "Debe seleccionar un tipo de fichero a Importar.");
			return result;
		}
		if (datosSensiblesBean.getGrupo() == null || datosSensiblesBean.getGrupo().equals("") || datosSensiblesBean.getGrupo().equals("-1")) {
			result = new ResultBean(true, "Debe seleccionar un Grupo al que importar los datos sensibles.");
			return result;
		}
		return result;
	}

	public String getNodoSolicitud() {
		return gestorPropiedades.valorPropertie(NOMBRE_HOST_SOLICITUD);
	}

	protected DatosSensiblesBastidorVO convertirDatosSensiblesBeanToDatosSensiblesBastidorVOAlta(DatosSensiblesBean datosSensiblesBean, String numColegiado, BigDecimal idUsuario,
			String apellidosNombre) {
		DatosSensiblesBastidorVO datosSensiblesBastidorVO = new DatosSensiblesBastidorVO();

		if (datosSensiblesBean == null) {
			return null;
		}

		DatosSensiblesBastidorPK id = new DatosSensiblesBastidorPK();
		id.setBastidor(datosSensiblesBean.getTextoAgrupacion().toUpperCase());
		id.setIdGrupo(datosSensiblesBean.getGrupo());
		datosSensiblesBastidorVO.setId(id);

		datosSensiblesBastidorVO.setNumColegiado(numColegiado);
		datosSensiblesBastidorVO.setIdUsuario(idUsuario);
		datosSensiblesBastidorVO.setApellidosNombre(apellidosNombre);

		datosSensiblesBastidorVO.setFechaAlta(Calendar.getInstance().getTime());
		datosSensiblesBastidorVO.setEstado(new BigDecimal(1));
		if (datosSensiblesBean.getTiempoDatosSensibles() == null) {
			datosSensiblesBastidorVO.setTiempoRestauracion(Long.parseLong(TiempoBajaDatosSensibles.No_caduca.toString()));
		} else {
			datosSensiblesBastidorVO.setTiempoRestauracion(datosSensiblesBean.getTiempoDatosSensibles().longValue());
		}

		if (datosSensiblesBean.getTipoControl() != null) {
			datosSensiblesBastidorVO.setTipoBastidor(new BigDecimal(datosSensiblesBean.getTipoControl()));
		}

		return datosSensiblesBastidorVO;
	}

	protected DatosSensiblesMatriculaVO convertirDatosSensiblesBeanToDatosSensiblesMatriculaVOAlta(DatosSensiblesBean datosSensiblesBean) {
		DatosSensiblesMatriculaVO datosSensiblesMatriculaVO = new DatosSensiblesMatriculaVO();

		if (datosSensiblesBean == null) {
			return null;
		}

		DatosSensiblesMatriculaPK id = new DatosSensiblesMatriculaPK();
		id.setIdGrupo(datosSensiblesBean.getGrupo());
		id.setMatricula(datosSensiblesBean.getTextoAgrupacion().toUpperCase());
		datosSensiblesMatriculaVO.setId(id);

		datosSensiblesMatriculaVO.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		datosSensiblesMatriculaVO.setIdUsuario(utilesColegiado.getIdUsuarioSessionBigDecimal());
		datosSensiblesMatriculaVO.setApellidosNombre(utilesColegiado.getApellidosNombreUsuario());

		datosSensiblesMatriculaVO.setFechaAlta(Calendar.getInstance().getTime());
		datosSensiblesMatriculaVO.setEstado(new BigDecimal(1));

		if (datosSensiblesBean.getTiempoDatosSensibles() == null) {
			datosSensiblesMatriculaVO.setTiempoRestauracion(new BigDecimal(TiempoBajaDatosSensibles.No_caduca.toString()));
		} else {
			datosSensiblesMatriculaVO.setTiempoRestauracion(datosSensiblesBean.getTiempoDatosSensibles());
		}

		return datosSensiblesMatriculaVO;
	}

	protected DatosSensiblesNifVO convertirDatosSensiblesBeanToDatosSensiblesNifVOAlta(DatosSensiblesBean datosSensiblesBean) {
		DatosSensiblesNifVO datosSensiblesNifVO = new DatosSensiblesNifVO();

		if (datosSensiblesBean == null) {
			return null;
		}

		DatosSensiblesNifPK id = new DatosSensiblesNifPK();
		id.setIdGrupo(datosSensiblesBean.getGrupo());
		id.setNif(datosSensiblesBean.getTextoAgrupacion().toUpperCase());
		datosSensiblesNifVO.setId(id);

		datosSensiblesNifVO.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		datosSensiblesNifVO.setIdUsuario(utilesColegiado.getIdUsuarioSessionBigDecimal());
		datosSensiblesNifVO.setApellidosNombre(utilesColegiado.getApellidosNombreUsuario());

		datosSensiblesNifVO.setFechaAlta(Calendar.getInstance().getTime());
		datosSensiblesNifVO.setEstado(new BigDecimal(1));

		if (datosSensiblesBean.getTiempoDatosSensibles() == null) {
			datosSensiblesNifVO.setTiempoRestauracion(new BigDecimal(TiempoBajaDatosSensibles.No_caduca.toString()));
		} else {
			datosSensiblesNifVO.setTiempoRestauracion(datosSensiblesBean.getTiempoDatosSensibles());
		}

		return datosSensiblesNifVO;
	}

	@Override
	@Transactional(rollbackFor = OegamExcepcion.class)
	public ResultBean eliminarDatosSensible(String indices, DatosSensiblesBean datosSensiblesBean, BigDecimal idContrato, BigDecimal idUsuario) throws OegamExcepcion {
		ResultBean resultBean = null;
		try {
			if (TiposDatosSensibles.Bastidor.toString().equals(datosSensiblesBean.getTipoAgrupacion())) {
				resultBean = eliminarBastidor(indices, datosSensiblesBean, idContrato, idUsuario, "APLICACION");
				if (resultBean == null) {
					indices = indices.replace("-", ", ");
					envioMail("Se ha dado de baja el siguiente bastidor: " + datosSensiblesBean.getTextoAgrupacion() + "<br>", asuntoCorreo_Baja, TipoBastidor.convertirTexto(datosSensiblesBean
							.getTipoControl()));
				}
			} else if (TiposDatosSensibles.Matricula.toString().equals(datosSensiblesBean.getTipoAgrupacion())) {
				resultBean = eliminarMatricula(indices, datosSensiblesBean, idUsuario);
				if (resultBean == null) {
					indices = indices.replace("-", ", ");
					envioMail("Se ha dado de baja la siguiente Matricula: " + datosSensiblesBean.getTextoAgrupacion() + "<br>", asuntoCorreo_Baja, null);
				}
			} else if (TiposDatosSensibles.Nif.toString().equals(datosSensiblesBean.getTipoAgrupacion())) {
				resultBean = eliminarNif(indices, datosSensiblesBean, idUsuario, "APLICACION");
				if (resultBean == null) {
					indices = indices.replace("-", ", ");
					envioMail("Se ha dado de baja el siguiente NIF: " + indices + "<br>", asuntoCorreo_Baja, null);
				}
			}
			if (resultBean != null && resultBean.getError()) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
		} catch (Exception e) {
			throw new OegamExcepcion(e.getMessage());
		}
		return resultBean;
	}

	@Override
	@Transactional
	public ResultBean eliminarBastidores(String indices, DatosSensiblesBean datosSensiblesBean, BigDecimal idContrato, BigDecimal idUsuario, String origenCambio) throws OegamExcepcion {
		ResultBean result = null;
		try {
			log.info("Eliminar Bastidores");
			result = eliminarBastidor(indices, datosSensiblesBean, idContrato, idUsuario, origenCambio);
			if (result == null) {
				log.info("Enviar 1º correo de eliminar Bastidores");
				envioMail("Se ha dado de baja el siguiente bastidor: " + indices.substring(0, 17) + "<br>", asuntoCorreo_Baja, TipoBastidor.convertirTexto(datosSensiblesBean.getTipoControl()));
			}
		} catch (Exception e) {
			throw new OegamExcepcion(e.getMessage());
		}
		return result;
	}

	private ResultBean eliminarNif(String indices, DatosSensiblesBean datosSensiblesBean, BigDecimal idUsuario, String origenCambio) {
		log.info("ServicioDatosSensiblesImpl eliminarNif Inicio");
		ResultBean result = null;
		String[] codSeleccionados = indices.split("-");
		try {
			for (int i = 0; i < codSeleccionados.length; i++) {
				String[] valores = codSeleccionados[i].split(",");

				DatosSensiblesNifVO datosSensiblesNifVO = setDatosSensiblesNifVo(valores[0], valores[1]);

				List<DatosSensiblesNifVO> lista = datosSensiblesNifDao.getNifPorId(datosSensiblesNifVO);

				if (lista != null && !lista.isEmpty()) {
					datosSensiblesNifVO = lista.get(0);
					datosSensiblesNifVO.setEstado(new BigDecimal(0));
					datosSensiblesNifVO.setFechaBaja(utilesFecha.formatoFecha("dd/MM/yyyy", utilesFecha.getFechaHoy()));

					datosSensiblesNifDao.borradoLogico(datosSensiblesNifVO);
					evoluciondatosSensiblesNifDao.guardar(dameEvolucionDatosSensiblesNifVO(datosSensiblesNifVO, "BORRADO LOGICO", idUsuario, origenCambio));
				} else {
					result = new ResultBean(true, "No existen datos para borra del siguiente Nif: " + datosSensiblesNifVO.getId().getNif());
					break;
				}
			}
		} catch (Exception e) {
			log.error("Ha surgido un error a la hora de dar de baja los nif, error: ", e);
			result = new ResultBean(true, "Ha surgido un error a la hora de dar de baja los nif");
		}
		return result;
	}

	// Mantis 25269 Eduardo Puerro Cepa
	protected ResultBean eliminarNif(String indices, DatosSensiblesBean datosSensiblesBean, BigDecimal idContrato, BigDecimal idUsuario, String origenCambio) throws OegamExcepcion {
		log.info("ServicioDatosSensiblesImpl eliminarNif Inicio");
		ResultBean resultBean = null;
		String[] codSeleccionados = indices.split("-");
		String sPropertieEncolar = gestorPropiedades.valorPropertie(encolarTipoNif);
		boolean esPosibleEncolar = false;
		if (sPropertieEncolar != null && sPropertieEncolar.equals("true")) {
			esPosibleEncolar = true;
		}
		try {
			for (int i = 0; i < codSeleccionados.length; i++) {
				String[] valores = codSeleccionados[i].split(",");

				DatosSensiblesNifVO datosSensiblesNifVO = setDatosSensiblesNifVo(valores[0], valores[1]);

				List<DatosSensiblesNifVO> lista = datosSensiblesNifDao.getNifPorId(datosSensiblesNifVO);

				if (lista != null && !lista.isEmpty()) {
					datosSensiblesNifVO = lista.get(0);
					datosSensiblesNifVO.setEstado(new BigDecimal(0));
					datosSensiblesNifVO.setFechaBaja(Calendar.getInstance().getTime());
					String xmlEnviar = valores[0] + "_" + datosSensiblesNifVO.getGrupo().getIdGrupo();
					log.info("Dentro de eliminar Nif");
					datosSensiblesNifDao.borradoLogico(datosSensiblesNifVO);
					evoluciondatosSensiblesNifDao.guardar(dameEvolucionDatosSensiblesNifVO(datosSensiblesNifVO, "BORRADO LOGICO", idUsuario, origenCambio));

					if (esPosibleEncolar) {
						resultBean = crearSolicitudesProcesos(xmlEnviar, idContrato, idUsuario);
					}
				} else {
					resultBean = new ResultBean(true, "No existen datos para borra del siguiente Nif: " + datosSensiblesNifVO.getId().getNif());
					break;
				}
			}
		} catch (Exception e) {
			log.error("Ha surgido un error a la hora de dar de baja los nif, error: ", e);
			resultBean = new ResultBean(true, "Ha surgido un error a la hora de dar de baja los nif");
		}
		return resultBean;
	}

	private DatosSensiblesNifVO setDatosSensiblesNifVo(String nif, String grupo) {
		DatosSensiblesNifVO datosSensiblesNifVO = new DatosSensiblesNifVO();
		DatosSensiblesNifPK id = new DatosSensiblesNifPK();
		id.setNif(nif);
		id.setIdGrupo(grupo);
		datosSensiblesNifVO.setId(id);
		return datosSensiblesNifVO;
	}

	protected ResultBean eliminarMatricula(String indices, DatosSensiblesBean datosSensiblesBean, BigDecimal idUsuario) {
		log.info("ServicioDatosSensiblesImpl eliminarMatriculas Inicio");
		String[] codSeleccionados = indices.split("-");
		ResultBean resultBean = null;
		for (int i = 0; i < codSeleccionados.length; i++) {
			String[] valores = codSeleccionados[i].split(",");

			DatosSensiblesMatriculaVO datosSensiblesMatriculaVO = setDatosSensiblesMatriculaVo(valores[0], valores[1]);

			List<DatosSensiblesMatriculaVO> lista = datosSensiblesMatriculaDao.getMatriculaPorId(datosSensiblesMatriculaVO);

			if (lista != null && !lista.isEmpty()) {
				datosSensiblesMatriculaVO = lista.get(0);
				datosSensiblesMatriculaVO.setEstado(new BigDecimal(0));
				datosSensiblesMatriculaVO.setFechaBaja(utilesFecha.formatoFecha("dd/MM/yyyy", utilesFecha.getFechaHoy()));

				datosSensiblesMatriculaDao.borradoLogico(datosSensiblesMatriculaVO);
				evoluciondatosSensiblesMatriculaDao.guardar(dameEvolucionDatosSensiblesMatriculaVO(datosSensiblesMatriculaVO, "BORRADO LOGICO", idUsuario, "APLICACION"));
			} else {
				resultBean = new ResultBean(true, "No existen datos para borra de la siguiente Matricula: " + datosSensiblesMatriculaVO.getId().getMatricula());
				break;
			}
		}
		return resultBean;
	}

	private DatosSensiblesMatriculaVO setDatosSensiblesMatriculaVo(String matricula, String grupo) {
		DatosSensiblesMatriculaVO datosSensiblesMatriculaVO = new DatosSensiblesMatriculaVO();
		DatosSensiblesMatriculaPK id = new DatosSensiblesMatriculaPK();
		id.setMatricula(matricula);
		id.setIdGrupo(grupo);
		datosSensiblesMatriculaVO.setId(id);
		return datosSensiblesMatriculaVO;
	}

	protected ResultBean eliminarBastidor(String indices, DatosSensiblesBean datosSensiblesBean, BigDecimal idContrato, BigDecimal idUsuario, String origenCambio) throws OegamExcepcion {
		log.info("ServicioDatosSensiblesImpl eliminarBastidores Inicio");
		ResultBean resultBean = null;
		String sPropertieEncolar = gestorPropiedades.valorPropertie(encolarTipoBastidor);
		boolean esPosibleEncolar = false;
		if (sPropertieEncolar != null && sPropertieEncolar.equals("true")) {
			esPosibleEncolar = true;
		} else {
			if (TipoBastidor.VN.getValorEnum().equals(datosSensiblesBean.getTipoControl())) {
				esPosibleEncolar = true;
			}
		}
		String[] codSeleccionados = indices.split("-");
		for (int i = 0; i < codSeleccionados.length; i++) {
			String[] valores = codSeleccionados[i].split(",");
			DatosSensiblesBastidorVO datosSensiblesBastidorVO = setDatosSensiblesBastidorVO(valores[0], valores[1]);
			List<DatosSensiblesBastidorVO> lista = datosSensiblesBastidorDao.getBastidorPorId(datosSensiblesBastidorVO);
			if (lista != null && !lista.isEmpty()) {
				datosSensiblesBastidorVO = lista.get(0);
				datosSensiblesBastidorVO.setEstado(new BigDecimal(0));
				datosSensiblesBastidorVO.setFechaBaja(Calendar.getInstance().getTime());
				String xmlEnviar = valores[0] + "_" + datosSensiblesBastidorVO.getGrupo().getIdGrupo();
				log.info("Dentro de eliminar Bastidores");
				datosSensiblesBastidorDao.borradoLogico(datosSensiblesBastidorVO);
				evolucionDatosSensiblesBastidorDao.guardar(dameEvolucionDatosSensiblesBastidorVO(datosSensiblesBastidorVO, "BORRADO LOGICO", idUsuario, origenCambio));
				if (esPosibleEncolar) {
					resultBean = crearSolicitudesProcesos(xmlEnviar, idContrato, idUsuario);
				}
			} else {
				resultBean = new ResultBean(true, "No existen datos para borra del siguiente Bastidor: " + datosSensiblesBastidorVO.getId().getBastidor());
				break;
			}
		}
		return resultBean;
	}

	@Override
	@Transactional(rollbackFor = OegamExcepcion.class)
	public ResultadoDatosSensibles guardarDatoSensibleBastidor(DatosSensiblesBean datosSensiblesBean, BigDecimal idContrato, String numColegiado, BigDecimal idUsuario, String apellidosNombre,
			String origenCambio) throws OegamExcepcion {
		ResultadoDatosSensibles resultado = null;
		try {
			if (datosSensiblesBean != null) {
				resultado = guardarBastidor(datosSensiblesBean, idContrato, numColegiado, idUsuario, apellidosNombre, origenCambio);
				if (resultado == null) {
					resultado = new ResultadoDatosSensibles(true, false, false, "El bastidor " + datosSensiblesBean.getTextoAgrupacion() + " se ha guardado correctamente para su grupo de usuarios");
					envioMail("Se ha dado de alta el siguiente Bastidor como dato sensible en la aplicación: " + datosSensiblesBean.getTextoAgrupacion() + "<br>", asuntoCorreo_Alta, TipoBastidor
							.convertirTexto(datosSensiblesBean.getTipoControl()));
				}
			} else {
				resultado = new ResultadoDatosSensibles(true, true, "No existen datos para ese Bastidor");
			}
		} catch (Exception e) {
			throw new OegamExcepcion(e.getMessage());
		}
		return resultado;
	}

	@Override
	@Transactional(rollbackFor = OegamExcepcion.class)
	public String obtenerGrupo(BigDecimal idUsuario) throws OegamExcepcion {
		UsuarioVO usuarioVO = null;
		try {
			usuarioVO = usuarioDao.getUsuario(idUsuario.longValue());
			if (usuarioVO == null) {
				return null;
			}
		} catch (HibernateException e) {
			throw new OegamExcepcion(e.getMessage());
		}
		return usuarioVO.getIdGrupo();
	}

	@Override
	@Transactional(rollbackFor = OegamExcepcion.class)
	public List<DatosSensiblesAgrupados> getListaDatosSensibles(DatosSensiblesBean datosSensiblesBean) throws OegamExcepcion {
		log.info("ServicioDatosSensiblesImpl getListaDatosSensibles");
		List<DatosSensiblesAgrupados> listaDatosSensibles = null;
		try {
			if (datosSensiblesBean != null) {
				if (TiposDatosSensibles.Bastidor.toString().equals(datosSensiblesBean.getTipoAgrupacion())) {
					log.info("ServicioDatosSensiblesImpl getListaDatosSensibles bastidores");
					List<DatosSensiblesBastidorVO> listaBastidores = getListaBastidoresVO(datosSensiblesBean);
					if (listaBastidores != null) {
						log.info("ServicioDatosSensiblesImpl getListaDatosSensibles existen bastidores");
						listaDatosSensibles = new ArrayList<DatosSensiblesAgrupados>();
						for (DatosSensiblesBastidorVO datBastidorVO : listaBastidores) {
							listaDatosSensibles.add(convertirDatosSensiblesBastidoresVOToDatosSensiblesAgrupados(datBastidorVO));
						}
					}
				} else if (TiposDatosSensibles.Matricula.toString().equals(datosSensiblesBean.getTipoAgrupacion())) {
					log.info("ServicioDatosSensiblesImpl getListaDatosSensibles matricula");
					List<DatosSensiblesMatriculaVO> listaMatriculas = getListaMatriculasVO(datosSensiblesBean);
					if (listaMatriculas != null) {
						log.info("ServicioDatosSensiblesImpl getListaDatosSensibles existen matriculas");
						listaDatosSensibles = new ArrayList<DatosSensiblesAgrupados>();
						for (DatosSensiblesMatriculaVO datMatriculaVO : listaMatriculas) {
							listaDatosSensibles.add(convertirDatosSensiblesMatriculaVOToDatosSensiblesAgrupados(datMatriculaVO));
						}
					}
				} else if (TiposDatosSensibles.Nif.toString().equals(datosSensiblesBean.getTipoAgrupacion())) {
					log.info("ServicioDatosSensiblesImpl getListaDatosSensibles nif");
					List<DatosSensiblesNifVO> listaNif = getListaNifVO(datosSensiblesBean);
					if (listaNif != null) {
						log.info("ServicioDatosSensiblesImpl getListaDatosSensibles existen nif");
						listaDatosSensibles = new ArrayList<DatosSensiblesAgrupados>();
						for (DatosSensiblesNifVO datNifVO : listaNif) {
							listaDatosSensibles.add(convertirDatosSensiblesNifVOToDatosSensiblesAgrupados(datNifVO));
						}
					}
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new OegamExcepcion(e.getMessage());
		}
		return listaDatosSensibles;
	}

	protected DatosSensiblesAgrupados convertirDatosSensiblesNifVOToDatosSensiblesAgrupados(DatosSensiblesNifVO datNifVO) {
		DatosSensiblesAgrupados datosSensiblesAgrupados = new DatosSensiblesAgrupados();

		datosSensiblesAgrupados.setApellidosNombre(datNifVO.getApellidosNombre());
		datosSensiblesAgrupados.setCampo(datNifVO.getId().getNif());
		datosSensiblesAgrupados.setFecha(utilesFecha.formatoFecha(datNifVO.getFechaAlta()));
		datosSensiblesAgrupados.setIdGrupo(datNifVO.getGrupo().getIdGrupo());
		datosSensiblesAgrupados.setDescGrupo(datNifVO.getGrupo().getDescGrupo());
		String nombreEnum = null;
		if (datNifVO.getTiempoRestauracion() != null) {
			nombreEnum = TiempoBajaDatosSensibles.convertirTexto(datNifVO.getTiempoRestauracion().intValue());
		} else {
			nombreEnum = TiempoBajaDatosSensibles.No_caduca.getNombreEnum();
		}
		datosSensiblesAgrupados.setTiempoRestauracion(nombreEnum);
		datosSensiblesAgrupados.setTipo("Nif");

		if (datNifVO.getEstado() != null) {
			datosSensiblesAgrupados.setEstado(EstadoBastidor.convertir(datNifVO.getEstado()).getNombreEnum());
		}

		return datosSensiblesAgrupados;
	}

	protected List<DatosSensiblesNifVO> getListaNifVO(DatosSensiblesBean datosSensiblesBean) {
		String idGrupo = null;
		String nif = null;
		BigDecimal estado = null;
		Date fechaDesde = null;
		Date fechaHasta = null;

		// Si es el usuario Administrador podra ver todos los datos de todos los grupos
		if (datosSensiblesBean.getGrupo() != null && !datosSensiblesBean.getGrupo().equals("-1")) {
			idGrupo = datosSensiblesBean.getGrupo();
		}

		if (datosSensiblesBean.getTextoAgrupacion() != null && !datosSensiblesBean.getTextoAgrupacion().isEmpty()) {
			nif = datosSensiblesBean.getTextoAgrupacion();
		}

		if (datosSensiblesBean.getFecha() != null) {
			if (datosSensiblesBean.getFecha().getFechaInicio() != null) {
				fechaDesde = datosSensiblesBean.getFecha().getFechaInicio();
			}
			if (datosSensiblesBean.getFecha().getFechaFin() != null) {
				Fecha fecha = new Fecha();
				fecha.setDia(datosSensiblesBean.getFecha().getDiaFin());
				fecha.setMes(datosSensiblesBean.getFecha().getMesFin());
				fecha.setAnio(datosSensiblesBean.getFecha().getAnioFin());

				fechaHasta = utilesFecha.sumaDiasFecha(fecha, "1");
			}
		}

		if (datosSensiblesBean.getEstado() != null && datosSensiblesBean.getEstado() > -1L) {
			estado = new BigDecimal(datosSensiblesBean.getEstado());
		}

		return datosSensiblesNifDao.getListaNif(nif, idGrupo, estado, fechaDesde, fechaHasta);
	}

	protected DatosSensiblesAgrupados convertirDatosSensiblesMatriculaVOToDatosSensiblesAgrupados(DatosSensiblesMatriculaVO datMatriculaVO) {
		DatosSensiblesAgrupados datosSensiblesAgrupados = new DatosSensiblesAgrupados();

		datosSensiblesAgrupados.setApellidosNombre(datMatriculaVO.getApellidosNombre());
		datosSensiblesAgrupados.setCampo(datMatriculaVO.getId().getMatricula());
		datosSensiblesAgrupados.setFecha(utilesFecha.formatoFecha(datMatriculaVO.getFechaAlta()));
		datosSensiblesAgrupados.setIdGrupo(datMatriculaVO.getGrupo().getIdGrupo());
		datosSensiblesAgrupados.setDescGrupo(datMatriculaVO.getGrupo().getDescGrupo());
		if (datMatriculaVO.getTiempoRestauracion() != null) {
			String nombreEnum = TiempoBajaDatosSensibles.convertirTexto(datMatriculaVO.getTiempoRestauracion().intValue());
			datosSensiblesAgrupados.setTiempoRestauracion(nombreEnum);
		} else {
			datosSensiblesAgrupados.setTiempoRestauracion(TiempoBajaDatosSensibles.No_caduca.getNombreEnum());
		}
		datosSensiblesAgrupados.setTipo("Matricula");

		if (datMatriculaVO.getEstado() != null) {
			datosSensiblesAgrupados.setEstado(EstadoBastidor.convertir(datMatriculaVO.getEstado()).getNombreEnum());
		}

		return datosSensiblesAgrupados;
	}

	protected List<DatosSensiblesMatriculaVO> getListaMatriculasVO(DatosSensiblesBean datosSensiblesBean) {
		String idGrupo = null;
		String matricula = null;
		BigDecimal estado = null;
		Date fechaDesde = null;
		Date fechaHasta = null;

		// Si es el usuario Administrador podra ver todos los datos de todos los grupos
		if (datosSensiblesBean.getGrupo() != null && !datosSensiblesBean.getGrupo().equals("-1")) {
			idGrupo = datosSensiblesBean.getGrupo();
		}

		if (datosSensiblesBean.getTextoAgrupacion() != null && !datosSensiblesBean.getTextoAgrupacion().isEmpty()) {
			matricula = datosSensiblesBean.getTextoAgrupacion();
		}

		if (datosSensiblesBean.getFecha() != null) {
			if (datosSensiblesBean.getFecha().getFechaInicio() != null) {
				fechaDesde = datosSensiblesBean.getFecha().getFechaInicio();
			}
			if (datosSensiblesBean.getFecha().getFechaFin() != null) {
				Fecha fecha = new Fecha();
				fecha.setDia(datosSensiblesBean.getFecha().getDiaFin());
				fecha.setMes(datosSensiblesBean.getFecha().getMesFin());
				fecha.setAnio(datosSensiblesBean.getFecha().getAnioFin());

				fechaHasta = utilesFecha.sumaDiasFecha(fecha, "1");
			}
		}

		if (datosSensiblesBean.getEstado() != null && datosSensiblesBean.getEstado() > -1L) {
			estado = new BigDecimal(datosSensiblesBean.getEstado());
		}

		return datosSensiblesMatriculaDao.getListaMatriculas(matricula, idGrupo, estado, fechaDesde, fechaHasta);
	}

	protected DatosSensiblesAgrupados convertirDatosSensiblesBastidoresVOToDatosSensiblesAgrupados(DatosSensiblesBastidorVO datBastidorVO) {
		DatosSensiblesAgrupados datosSensiblesAgrupados = new DatosSensiblesAgrupados();

		datosSensiblesAgrupados.setApellidosNombre(datBastidorVO.getApellidosNombre());
		datosSensiblesAgrupados.setCampo(datBastidorVO.getId().getBastidor());
		datosSensiblesAgrupados.setFecha(utilesFecha.formatoFecha(datBastidorVO.getFechaAlta()));
		datosSensiblesAgrupados.setIdGrupo(datBastidorVO.getGrupo().getIdGrupo());
		datosSensiblesAgrupados.setDescGrupo(datBastidorVO.getGrupo().getDescGrupo());
		if (datBastidorVO.getTiempoRestauracion() != null) {
			String nombreEnum = TiempoBajaDatosSensibles.convertirTexto(datBastidorVO.getTiempoRestauracion().intValue());
			datosSensiblesAgrupados.setTiempoRestauracion(nombreEnum);
		} else {
			datosSensiblesAgrupados.setTiempoRestauracion(TiempoBajaDatosSensibles.No_caduca.getNombreEnum());
		}
		datosSensiblesAgrupados.setTipo("Bastidor");
		if (datBastidorVO.getTipoBastidor() != null) {
			datosSensiblesAgrupados.setTipoControl(TipoBastidor.convertirTexto(datBastidorVO.getTipoBastidor().toString()));
		}

		if (datBastidorVO.getEstado() != null) {
			datosSensiblesAgrupados.setEstado(EstadoBastidor.convertir(datBastidorVO.getEstado()).getNombreEnum());
		}

		return datosSensiblesAgrupados;
	}

	protected List<DatosSensiblesBastidorVO> getListaBastidoresVO(DatosSensiblesBean datosSensiblesBean) {
		String idGrupo = null;
		String bastidor = null;
		BigDecimal estado = null;
		Date fechaDesde = null;
		Date fechaHasta = null;
		BigDecimal tipoBastidor = null;

		if (datosSensiblesBean.getGrupo() != null && !datosSensiblesBean.getGrupo().equals("-1")) {
			idGrupo = datosSensiblesBean.getGrupo();
		}

		if (datosSensiblesBean.getTextoAgrupacion() != null && !datosSensiblesBean.getTextoAgrupacion().isEmpty()) {
			bastidor = datosSensiblesBean.getTextoAgrupacion();
		}

		if (datosSensiblesBean.getFecha() != null) {
			if (datosSensiblesBean.getFecha().getFechaInicio() != null) {
				fechaDesde = datosSensiblesBean.getFecha().getFechaInicio();
			}
			if (datosSensiblesBean.getFecha().getFechaFin() != null) {
				Calendar c = Calendar.getInstance();
				c.setTime(datosSensiblesBean.getFecha().getFechaFin());
				c.add(Calendar.DAY_OF_MONTH, 1);
				fechaHasta = c.getTime();
			}
		}

		if (datosSensiblesBean.getTipoControl() != null && !datosSensiblesBean.getTipoControl().equals("-1")) {
			tipoBastidor = new BigDecimal(datosSensiblesBean.getTipoControl());
		}

		if (datosSensiblesBean.getEstado() != null && datosSensiblesBean.getEstado() > -1L) {
			estado = new BigDecimal(datosSensiblesBean.getEstado());
		}

		return datosSensiblesBastidorDao.getBastidores(bastidor, idGrupo, estado, fechaDesde, fechaHasta, tipoBastidor);
	}

	@Override
	@Transactional(rollbackFor = OegamExcepcion.class)
	public List<DatosSensiblesAgrupados> getListaBastidores(DatosSensiblesBean datosSensiblesBean) throws OegamExcepcion {
		List<DatosSensiblesAgrupados> listaDatosSensibles = null;
		try {
			if (datosSensiblesBean != null) {
				List<DatosSensiblesBastidorVO> listaBastidores = getListaBastidoresVO(datosSensiblesBean);
				if (listaBastidores != null) {
					listaDatosSensibles = new ArrayList<DatosSensiblesAgrupados>();
					for (DatosSensiblesBastidorVO datBastidorVO : listaBastidores) {
						listaDatosSensibles.add(convertirDatosSensiblesBastidoresVOToDatosSensiblesAgrupados(datBastidorVO));
					}
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new OegamExcepcion(e.getMessage());
		}
		return listaDatosSensibles;
	}

	@Override
	public DatosSensiblesBean getDatosSensiblesEntidadesFinancieras(String tipoAgrupacion) {
		DatosSensiblesBean datosSensiblesBean = new DatosSensiblesBean();

		datosSensiblesBean.setFecha(utilesFecha.getFechaFracionadaActual());
		datosSensiblesBean.setTextoAgrupacion("");
		if (tipoAgrupacion != null) {
			datosSensiblesBean.setTipoAgrupacion(tipoAgrupacion);
		}

		return datosSensiblesBean;
	}

	@Override
	@Transactional
	public List<String> existenDatosSensiblesPorExpedientes(BigDecimal[] numExpedientes, BigDecimal idUsuario) throws OegamExcepcion {
		List<String> listaExpedientesConDtSensibles = new ArrayList<String>();
		List<TramiteTraficoVO> listaTramitesAComprobar = tramiteTraficoDao.getListaTramitesAComprobarDatosSensibles(numExpedientes);
		if (listaTramitesAComprobar != null) {
			for (TramiteTraficoVO tramiteTraficoVO : listaTramitesAComprobar) {
				if (existeDatosSensible(tramiteTraficoVO, idUsuario)) {
					listaExpedientesConDtSensibles.add(tramiteTraficoVO.getNumExpediente().toString());
				}
			}

		}
		return listaExpedientesConDtSensibles;
	}

	private String extraerTipoPersonaTramite(TramiteTraficoVO tramiteTrafico) {
		String tipoPersona = "";
		if (tramiteTrafico.getIntervinienteTraficos() != null && !tramiteTrafico.getIntervinienteTraficos().isEmpty()) {
			if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tramiteTrafico.getTipoTramite()) || TipoTramiteTrafico.Duplicado.getValorEnum().equals(tramiteTrafico.getTipoTramite())
					|| TipoTramiteTrafico.Baja.getValorEnum().equals(tramiteTrafico.getTipoTramite())) {
				IntervinienteTraficoVO intervinienteTraficoVO = buscarInterviniente(tramiteTrafico, TipoInterviniente.Titular.getValorEnum());
				if (intervinienteTraficoVO != null) {
					int tipo = utilidadesNIFValidator.isValidDniNieCif(intervinienteTraficoVO.getId().getNif().toUpperCase());
					if (TipoPersona.Fisica.getValorPersona() == tipo) {
						tipoPersona = TipoPersona.Fisica.getNombreEnum();
					} else {
						tipoPersona = TipoPersona.Juridica.getNombreEnum();
					}
				}
			} else if (TipoTramiteTrafico.Transmision.getValorEnum().equals(tramiteTrafico.getTipoTramite()) || TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tramiteTrafico
					.getTipoTramite())) {
				IntervinienteTraficoVO intervinienteTraficoVO = buscarInterviniente(tramiteTrafico, TipoInterviniente.Adquiriente.getValorEnum());
				if (intervinienteTraficoVO != null) {
					int tipo = utilidadesNIFValidator.isValidDniNieCif(intervinienteTraficoVO.getId().getNif().toUpperCase());
					if (TipoPersona.Fisica.getValorPersona() == tipo) {
						tipoPersona = TipoPersona.Fisica.getNombreEnum();
					} else {
						tipoPersona = TipoPersona.Juridica.getNombreEnum();
					}
				}
			}
		}
		return tipoPersona;
	}

	private IntervinienteTraficoVO buscarInterviniente(TramiteTraficoVO tramite, String tipoInterviniente) {
		IntervinienteTraficoVO interviniente = null;
		if (tramite.getIntervinienteTraficosAsList() != null && !tramite.getIntervinienteTraficosAsList().isEmpty()) {
			for (IntervinienteTraficoVO inter : tramite.getIntervinienteTraficosAsList()) {
				if (tipoInterviniente.equals(inter.getId().getTipoInterviniente())) {
					interviniente = inter;
					break;
				}
			}
		}
		return interviniente;
	}

	private boolean existeDatosSensible(TramiteTraficoVO tramiteTraficoVO, BigDecimal idUsuario) throws OegamExcepcion {
		boolean resultado = false;
		log.info("inicio comprobacion existeDatosSensible");
		// Obtenemos las direcciones de los grupos que haya en BBDD
		Map<String, String> dirGrupos = UtilesVistaDatosSensibles.getInstance().direccionesGrupos();
		String tipoPersonaDT = extraerTipoPersonaTramite(tramiteTraficoVO);
		if (tramiteTraficoVO.getVehiculo() != null && tramiteTraficoVO.getVehiculo().getMatricula() != null) {
			if (contained(UtilesVistaDatosSensibles.getInstance().getMatriculasSensibles(), tramiteTraficoVO.getVehiculo().getMatricula())) {
				if (continuaMatriculaBloqueada(tramiteTraficoVO.getVehiculo().getMatricula(), idUsuario)) {
					log.info("Se ha detectado la siguiente matricula sensible: " + tramiteTraficoVO.getVehiculo().getMatricula());
					List<DatosSensiblesMatriculaVO> listaGruposSiguiendoMatricula = datosSensiblesMatriculaDao.getListaGruposPorMatricula(tramiteTraficoVO.getVehiculo().getMatricula(), new BigDecimal(
							1));
					resultado = true;
					if (listaGruposSiguiendoMatricula != null && !listaGruposSiguiendoMatricula.isEmpty()) {
						for (DatosSensiblesMatriculaVO datosSMatriculaVO : listaGruposSiguiendoMatricula) {
							if(datosSMatriculaVO.getGrupo().getCorreoElectronico() != null && !datosSMatriculaVO.getGrupo().getCorreoElectronico().isEmpty()) {
								if (!isFranjaHorariaAutoliberacionActiva(datosSMatriculaVO.getGrupo().getIdGrupo(), datosSMatriculaVO, null, null, idUsuario)) {
									StringBuffer sb = new StringBuffer("Se intentó realizar un trámite sobre un vehículo con una Matricula a seguir: <br>");
									sb.append("  Matrícula ").append(tramiteTraficoVO.getVehiculo().getMatricula()).append(" <br>");
									if (dirGrupos.containsKey(datosSMatriculaVO.getGrupo().getIdGrupo())) {
										envioCorreoDatosSensibles(tramiteTraficoVO.getTipoTramite(), tramiteTraficoVO.getNumExpediente(), datosSMatriculaVO.getGrupo().getCorreoElectronico(), sb,
												tramiteTraficoVO.getVehiculo().getMatricula(), tramiteTraficoVO.getNumColegiado(), tipoPersonaDT);
									}
								}
							}else {
								log.error("No hay un correo electrónico asociado a ese grupo: " + datosSMatriculaVO.getGrupo().getDescGrupo() + ". Existen problemas con la trámitación. Contacte urgentemente con el Colegio.");
								
							}
				
						}
					}

				}
			}
		}
		if (tramiteTraficoVO.getVehiculo() != null && tramiteTraficoVO.getVehiculo().getMatriculaOrigen() != null) {
			if (contained(UtilesVistaDatosSensibles.getInstance().getMatriculasSensibles(), tramiteTraficoVO.getVehiculo().getMatriculaOrigen())) {
				if (continuaMatriculaBloqueada(tramiteTraficoVO.getVehiculo().getMatriculaOrigen(), idUsuario)) {
					log.info("Se ha detectado la siguiente matricula origen sensible: " + tramiteTraficoVO.getVehiculo().getMatriculaOrigen());
					List<DatosSensiblesMatriculaVO> listaGruposSiguiendoMatricula = datosSensiblesMatriculaDao.getListaGruposPorMatricula(tramiteTraficoVO.getVehiculo().getMatriculaOrigen(),
							new BigDecimal(1));
					resultado = true;
					if (listaGruposSiguiendoMatricula != null && !listaGruposSiguiendoMatricula.isEmpty()) {
						for (DatosSensiblesMatriculaVO datosSMatriculaVO : listaGruposSiguiendoMatricula) {
							if(datosSMatriculaVO.getGrupo().getCorreoElectronico() != null && !datosSMatriculaVO.getGrupo().getCorreoElectronico().isEmpty()) {
								if (!isFranjaHorariaAutoliberacionActiva(datosSMatriculaVO.getGrupo().getIdGrupo(), datosSMatriculaVO, null, null, idUsuario)) {
									StringBuffer sb = new StringBuffer("Se intentó realizar un trámite sobre un vehículo con una Matricula Origen a seguir: <br>");
									sb.append("  Matrícula Origen ").append(tramiteTraficoVO.getVehiculo().getMatriculaOrigen()).append(" <br>");
									sb.append("  Matrícula ").append(tramiteTraficoVO.getVehiculo().getMatricula()).append(" <br>");
									if (dirGrupos.containsKey(datosSMatriculaVO.getGrupo().getIdGrupo())) {
										envioCorreoDatosSensibles(tramiteTraficoVO.getTipoTramite(), tramiteTraficoVO.getNumExpediente(), datosSMatriculaVO.getGrupo().getCorreoElectronico(), sb,
												tramiteTraficoVO.getVehiculo().getMatricula(), tramiteTraficoVO.getNumColegiado(), tipoPersonaDT);
									}
								}
							}else {
								log.error("No hay un correo electrónico asociado a ese grupo: " + datosSMatriculaVO.getGrupo().getDescGrupo() + ". Existen problemas con la trámitación. Contacte urgentemente con el Colegio.");
								
							}
						}
					}
				}
			}
		}
		// Si es bastidor
		if (tramiteTraficoVO.getVehiculo() != null && tramiteTraficoVO.getVehiculo().getBastidor() != null) {
			if (contained(UtilesVistaDatosSensibles.getInstance().getBastidoresSensibles(), tramiteTraficoVO.getVehiculo().getBastidor())) {
				if (continuaBastidorBloqueado(tramiteTraficoVO.getVehiculo().getBastidor(), idUsuario)) {
					log.info("Se ha detectado el siguiente bastidor sensible: " + tramiteTraficoVO.getVehiculo().getBastidor());
					List<DatosSensiblesBastidorVO> listaGruposSiguendoBastidor = datosSensiblesBastidorDao.getlistaGruposPorBastidor(tramiteTraficoVO.getVehiculo().getBastidor(), new BigDecimal(1));
					if (listaGruposSiguendoBastidor != null && !listaGruposSiguendoBastidor.isEmpty()) {
						for (DatosSensiblesBastidorVO bastidorVO : listaGruposSiguendoBastidor) {
							if (bastidorVO.getTipoBastidor() != null) {
								if (!isFranjaHorariaAutoliberacionActiva(bastidorVO.getGrupo().getIdGrupo(), null, bastidorVO, null, idUsuario)) {
									StringBuffer sb = null;
									String nuevoFormatosFicheros = gestorPropiedades.valorPropertie("nuevos.formatos.ficheros.ftp");
									if ("SI".equalsIgnoreCase(nuevoFormatosFicheros) && (bastidorVO.getTipoBastidorSantander() != null && TipoBastidorSantander.validarTipoBastidorPorValor(bastidorVO
											.getTipoBastidorSantander().toString()))) {
										sb = new StringBuffer("Aviso, se va a realizar una operaci&oacute;n sobre el Bastidor: " + bastidorVO.getId().getBastidor()
												+ " correspondiente a un veh&iacute;culo de Financiaci&oacute;n, Leasing o Renting. <br>");
										envioCorreoDatosSensiblesCrtRt(tramiteTraficoVO, bastidorVO, sb, tipoPersonaDT);
									} else {
										if (TipoBastidor.VN.getValorEnum().equals(bastidorVO.getTipoBastidor().toString())) {
											if (!TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tramiteTraficoVO.getTipoTramite())) {
												resultado = true;
												sb = new StringBuffer("Se intent&oacute; realizar un tr&aacute;mite sobre un veh&iacute;culo con un Bastidor a seguir: <br>");
												sb.append("  Bastidor ").append(bastidorVO.getId().getBastidor()).append(" <br>");
											} else {
												sb = new StringBuffer("Aviso, se va a realizar una operaci&oacute;n sobre el Bastidor: " + bastidorVO.getId().getBastidor()
														+ " correspondiente a un veh&iacute;culo nuevo. <br>");
											}
										} else if (TipoBastidor.DM.getValorEnum().equals(bastidorVO.getTipoBastidor().toString()) || TipoBastidor.VO.getValorEnum().equals(bastidorVO.getTipoBastidor()
												.toString()) || TipoBastidor.FI.getValorEnum().equals(bastidorVO.getTipoBastidor().toString()) || TipoBastidor.LE.getValorEnum().equals(bastidorVO
														.getTipoBastidor().toString()) || TipoBastidor.RE.getValorEnum().equals(bastidorVO.getTipoBastidor().toString())) {
											sb = new StringBuffer("Aviso, se va a realizar una operaci&oacute;n sobre el Bastidor: " + bastidorVO.getId().getBastidor()
													+ " correspondiente a un veh&iacute;culo de Ocasi&oacute;n,Demo,Financiaci&oacute;n,Leasing o Renting. <br>");
										} else if (TipoBastidor.FR.getValorEnum().equals(bastidorVO.getTipoBastidor().toString())) {
											sb = new StringBuffer("Aviso, se va a realizar una operaci&oacute;n sobre el Bastidor: " + bastidorVO.getId().getBastidor()
													+ " correspondiente a un veh&iacute;culo de Financiaci&oacute;n Retail. <br>");
										}
										if (dirGrupos.containsKey(bastidorVO.getGrupo().getIdGrupo())) {
											if (TipoBastidor.VN.getValorEnum().equals(bastidorVO.getTipoBastidor().toString())) {
												envioCorreoDatosSensiblesBastidorVN(tramiteTraficoVO.getTipoTramite(), tramiteTraficoVO.getNumExpediente(), bastidorVO.getGrupo()
														.getCorreoElectronico(), bastidorVO.getTipoBastidor(), sb, bastidorVO.getId().getBastidor(), tramiteTraficoVO.getNumColegiado(), tipoPersonaDT);
											} else if (TipoBastidor.VO.getValorEnum().equals(bastidorVO.getTipoBastidor().toString()) && ("si".equalsIgnoreCase(gestorPropiedades.valorPropertie(
													activadoEnvioCorreoBastidorVO)))) {
												envioCorreoDatosSensiblesBastidorVO(tramiteTraficoVO.getTipoTramite(), tramiteTraficoVO.getNumExpediente(), bastidorVO.getGrupo()
														.getCorreoElectronico(), bastidorVO.getTipoBastidor(), sb, bastidorVO.getId().getBastidor(), tramiteTraficoVO.getNumColegiado(), tipoPersonaDT);
											} else if (TipoBastidor.DM.getValorEnum().equals(bastidorVO.getTipoBastidor().toString()) && ("si".equalsIgnoreCase(gestorPropiedades.valorPropertie(
													activadoEnvioCorreoBastidorDM)))) {
												envioCorreoDatosSensiblesBastidorDM(tramiteTraficoVO.getTipoTramite(), tramiteTraficoVO.getNumExpediente(), bastidorVO.getGrupo()
														.getCorreoElectronico(), bastidorVO.getTipoBastidor(), sb, bastidorVO.getId().getBastidor(), tramiteTraficoVO.getNumColegiado(), tipoPersonaDT);
											} else if (TipoBastidor.FI.getValorEnum().equals(bastidorVO.getTipoBastidor().toString()) && ("si".equalsIgnoreCase(gestorPropiedades.valorPropertie(
													activadoEnvioCorreoBastidorFI)))) {
												envioCorreoDatosSensiblesBastidorFI(tramiteTraficoVO.getTipoTramite(), tramiteTraficoVO.getNumExpediente(), bastidorVO.getGrupo()
														.getCorreoElectronico(), bastidorVO.getTipoBastidor(), sb, bastidorVO.getId().getBastidor(), tramiteTraficoVO.getNumColegiado(), tipoPersonaDT);
											} else if (TipoBastidor.LE.getValorEnum().equals(bastidorVO.getTipoBastidor().toString()) && ("si".equalsIgnoreCase(gestorPropiedades.valorPropertie(
													activadoEnvioCorreoBastidorLE)))) {
												envioCorreoDatosSensiblesBastidorLE(tramiteTraficoVO.getTipoTramite(), tramiteTraficoVO.getNumExpediente(), bastidorVO.getGrupo()
														.getCorreoElectronico(), bastidorVO.getTipoBastidor(), sb, bastidorVO.getId().getBastidor(), tramiteTraficoVO.getNumColegiado(), tipoPersonaDT);
											} else if (TipoBastidor.RE.getValorEnum().equals(bastidorVO.getTipoBastidor().toString()) && ("si".equalsIgnoreCase(gestorPropiedades.valorPropertie(
													activadoEnvioCorreoBastidorRE)))) {
												envioCorreoDatosSensiblesBastidorRE(tramiteTraficoVO.getTipoTramite(), tramiteTraficoVO.getNumExpediente(), bastidorVO.getGrupo()
														.getCorreoElectronico(), bastidorVO.getTipoBastidor(), sb, bastidorVO.getId().getBastidor(), tramiteTraficoVO.getNumColegiado(), tipoPersonaDT);
											} else if (TipoBastidor.FR.getValorEnum().equals(bastidorVO.getTipoBastidor().toString()) && ("si".equalsIgnoreCase(gestorPropiedades.valorPropertie(
													activadoEnvioCorreoBastidorFR)))) {
												envioCorreoDatosSensiblesBastidorFR(tramiteTraficoVO.getTipoTramite(), tramiteTraficoVO.getNumExpediente(), bastidorVO.getTipoBastidor(), sb, bastidorVO.getId().getBastidor(), tramiteTraficoVO.getNumColegiado(), tipoPersonaDT);
											}
										}
									}
								}
							} else {
								log.error("El tipo bastidor correspondiente al bastidor: " + bastidorVO.getId().getBastidor() + " no puede ser nulo.");
								throw new OegamExcepcion(EnumError.error_00002, "Existen problemas con la impresión. Contacte urgentemente con el Colegio.");
							}
						}
					}
				}
			}
		}

		if (tramiteTraficoVO.getIntervinienteTraficos() != null && !tramiteTraficoVO.getIntervinienteTraficos().isEmpty()) {
			for (IntervinienteTraficoVO intervinienteTraficoVO : tramiteTraficoVO.getIntervinienteTraficos()) {
				if (intervinienteTraficoVO.getId().getNif() != null) {
					if (contained(UtilesVistaDatosSensibles.getInstance().getNifsSensibles(), intervinienteTraficoVO.getId().getNif())) {
						if (continuaNifBloqueado(intervinienteTraficoVO.getId().getNif(), idUsuario)) {
							log.info("Se ha detectado el siguiente NIF sensible: " + intervinienteTraficoVO.getId().getNif());
							List<DatosSensiblesNifVO> listaGruposSiguiendoNif = datosSensiblesNifDao.getListaGruposPorNif(intervinienteTraficoVO.getId().getNif(), new BigDecimal(1));
							resultado = true;
							if (listaGruposSiguiendoNif != null && !listaGruposSiguiendoNif.isEmpty()) {
								for (DatosSensiblesNifVO nifVO : listaGruposSiguiendoNif) {
									if(nifVO.getGrupo().getCorreoElectronico() != null && !nifVO.getGrupo().getCorreoElectronico().isEmpty()) {
										if (!isFranjaHorariaAutoliberacionActiva(nifVO.getId().getNif(), null, null, nifVO, idUsuario)) {
											StringBuffer sb = new StringBuffer("Se intentó realizar un trámite con un Nif a seguir: <br>");
											sb.append("  Nif ").append(nifVO.getId().getNif()).append(" <br>");
											if (dirGrupos.containsKey(nifVO.getGrupo().getIdGrupo())) {
												envioCorreoDatosSensibles(tramiteTraficoVO.getTipoTramite(), tramiteTraficoVO.getNumExpediente(), nifVO.getGrupo().getCorreoElectronico(), sb, nifVO.getId()
														.getNif(), tramiteTraficoVO.getNumColegiado(), tipoPersonaDT);
											}
										}	
									}else {
										log.error("No hay un correo electrónico asociado a ese grupo: " + nifVO.getGrupo().getDescGrupo() + ". Existen problemas con la trámitación. Contacte urgentemente con el Colegio.");
										
									}
								}
							}
						}
					}
				}
			}

		}
		log.info("fin existeDatosSensible");
		return resultado;
	}

	private void envioCorreoDatosSensiblesCrtRt(TramiteTraficoVO tramiteTraficoVO, DatosSensiblesBastidorVO bastidorVO, StringBuffer sb, String tipoPersona) throws OegamExcepcion {
		log.info("inicio envioCorreoDatosSensiblesBastidorVN");

		// Mantis 11888. David Sierra: Para que se envie en el correo el tipo de tramite con el nombreEnum y no con el valorEnum
		String tipoTramite = TipoTramiteTrafico.convertirTexto(tramiteTraficoVO.getTipoTramite());
		tipoTramite = tipoTramite != null ? tipoTramite : "";

		sb.append("<br>Tipo de tr&aacute;mite: ").append(tipoTramite).append("; N&uacute;mero de expediente: ").append(tramiteTraficoVO.getNumExpediente()).append(" <br>");
		sb.append("En caso de duda, contacte con el colegio. <br>");

		String destinatario = null;
		String destinatarioOculto = gestorPropiedades.valorPropertie("destinatario.correo.bastidores.oculto");
		// String copia = gestorPropiedades.valorPropertie("nuevos.formatos.ficheros.ftp.copiaDestinatarioCart");

		if (TipoBastidorSantander.RT.getValorEnum().equals(bastidorVO.getTipoBastidorSantander().toString())) {
			destinatario = gestorPropiedades.valorPropertie("nuevos.formatos.ficheros.ftp.destinatarioRetailAlerta");
		} else {
			destinatario = gestorPropiedades.valorPropertie("nuevos.formatos.ficheros.ftp.destinatarioCartAlerta");
			// copia = gestorPropiedades.valorPropertie("nuevos.formatos.ficheros.ftp.copiaDestinatarioCart");
		}

		String subject = gestorPropiedades.valorPropertie(subjectOperDatoSensible);

		subject += " - " + TipoBastidor.convertirTexto(bastidorVO.getTipoBastidor().toString());
		subject += " (" + bastidorVO.getId().getBastidor() + ") - Colegiado: " + tramiteTraficoVO.getNumColegiado() + " - Tipo Tramite: " + tipoTramite + "- Tipo Persona: " + tipoPersona;

		ResultBean resultEnvio;

		try {
			resultEnvio = servicioCorreo.enviarNotificacion(new String(sb.toString().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8), null, null, subject, destinatario, null, destinatarioOculto, null);
			if (resultEnvio == null || resultEnvio.getError())
				throw new OegamExcepcion("Se ha producido un error al enviar el mail");
		} catch (IOException e) {
			throw new OegamExcepcion("Se ha producido un error al enviar el mail");
		}

		log.error("Se va a enviar un correo electronico informando de una operacion con datos sensibles a los siguientes" + " destinatarios: " + destinatario + " y con copia oculta a: "
				+ destinatarioOculto);

	}

	private void envioCorreoDatosSensiblesBastidorVN(String tipoTramite, BigDecimal numExpediente, String direccionGrupo, BigDecimal tipoBastidor, StringBuffer sb, String datoSensible,
			String numColegiado, String tipoPersona) throws OegamExcepcion {
		log.info("inicio envioCorreoDatosSensiblesBastidorVN");

		// Mantis 11888. David Sierra: Para que se envie en el correo el tipo de tramite con el nombreEnum y no con el valorEnum
		tipoTramite = TipoTramiteTrafico.convertirTexto(tipoTramite);
		tipoTramite = tipoTramite != null ? tipoTramite : "";

		sb.append("<br>Tipo de tr&aacute;mite: ").append(tipoTramite).append("; N&uacute;mero de expediente: ").append(numExpediente).append(" <br>");
		sb.append("En caso de duda, contacte con el colegio. <br>");

		// Se envía el correo
		String dirOcultas = gestorPropiedades.valorPropertie(dirOcultasOperDatoSensibleVN);

		String subject = gestorPropiedades.valorPropertie(subjectOperDatoSensible);

		subject += " - " + TipoBastidor.convertirTexto(tipoBastidor.toString());
		subject += " (" + datoSensible + ") - Colegiado: " + numColegiado + " - Tipo Tramite: " + tipoTramite + "- Tipo Persona: " + tipoPersona;

		ResultBean resultEnvio;

		try {
			resultEnvio = servicioCorreo.enviarNotificacion(new String(sb.toString().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8), null, null, subject, direccionGrupo, null, dirOcultas, null);
			if (resultEnvio == null || resultEnvio.getError())
				throw new OegamExcepcion("Se ha producido un error al enviar el mail");
		} catch (IOException e) {
			throw new OegamExcepcion("Se ha producido un error al enviar el mail");
		}

		log.error("Se va a enviar un correo electronico informando de una operacion con datos sensibles a los siguientes" + " destinatarios: " + direccionGrupo + ";; y con copia oculta a: "
				+ dirOcultas);
	}

	private void envioCorreoDatosSensiblesBastidorVO(String tipoTramite, BigDecimal numExpediente, String direccionGrupo, BigDecimal tipoBastidor, StringBuffer sb, String datoSensible,
			String numColegiado, String tipoPersona) throws OegamExcepcion {
		log.info("inicio envioCorreoDatosSensiblesBastidorVO");

		// Mantis 11888. David Sierra: Para que se envie en el correo el tipo de tramite con el nombreEnum y no con el valorEnum
		tipoTramite = TipoTramiteTrafico.convertirTexto(tipoTramite);
		tipoTramite = tipoTramite != null ? tipoTramite : "";

		sb.append("<br>Tipo de tr&aacute;mite: ").append(tipoTramite).append("; N&uacute;mero de expediente: ").append(numExpediente).append(" <br>");
		sb.append("En caso de duda, contacte con el colegio. <br>");

		// Se envía el correo
		String dirOcultas = gestorPropiedades.valorPropertie(dirOcultasOperDatoSensibleVO);

		String subject = gestorPropiedades.valorPropertie(subjectOperDatoSensible);

		subject += " - " + TipoBastidor.convertirTexto(tipoBastidor.toString());
		subject += " (" + datoSensible + ") - Colegiado: " + numColegiado + " - Tipo Tramite: " + tipoTramite + "- Tipo Persona: " + tipoPersona;

		ResultBean resultEnvio;

		try {
			resultEnvio = servicioCorreo.enviarNotificacion(new String(sb.toString().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8), null, null, subject, direccionGrupo, null, dirOcultas, null);
			if (resultEnvio == null || resultEnvio.getError())
				throw new OegamExcepcion("Se ha producido un error al enviar el mail");
		} catch (IOException e) {
			throw new OegamExcepcion("Se ha producido un error al enviar el mail");
		}

		log.error("Se va a enviar un correo electronico informando de una operacion con datos sensibles a los siguientes" + " destinatarios: " + direccionGrupo + ";; y con copia oculta a: "
				+ dirOcultas);
	}

	private void envioCorreoDatosSensiblesBastidorDM(String tipoTramite, BigDecimal numExpediente, String direccionGrupo, BigDecimal tipoBastidor, StringBuffer sb, String datoSensible,
			String numColegiado, String tipoPersona) throws OegamExcepcion {
		log.info("inicio envioCorreoDatosSensiblesBastidorDM");

		// Mantis 11888. David Sierra: Para que se envie en el correo el tipo de tramite con el nombreEnum y no con el valorEnum
		tipoTramite = TipoTramiteTrafico.convertirTexto(tipoTramite);
		tipoTramite = tipoTramite != null ? tipoTramite : "";

		sb.append("<br>Tipo de tr&aacute;mite: ").append(tipoTramite).append("; N&uacute;mero de expediente: ").append(numExpediente).append(" <br>");
		sb.append("En caso de duda, contacte con el colegio. <br>");

		// Se envía el correo
		String dirOcultas = gestorPropiedades.valorPropertie(dirOcultasOperDatoSensibleDM);

		String subject = gestorPropiedades.valorPropertie(subjectOperDatoSensible);

		subject += " - " + TipoBastidor.convertirTexto(tipoBastidor.toString());
		subject += " (" + datoSensible + ") - Colegiado: " + numColegiado + " - Tipo Tramite: " + tipoTramite + "- Tipo Persona: " + tipoPersona;

		ResultBean resultEnvio;

		try {
			resultEnvio = servicioCorreo.enviarNotificacion(new String(sb.toString().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8), null, null, subject, direccionGrupo, null, dirOcultas, null);
			if (resultEnvio == null || resultEnvio.getError())
				throw new OegamExcepcion("Se ha producido un error al enviar el mail");
		} catch (IOException e) {
			throw new OegamExcepcion("Se ha producido un error al enviar el mail");
		}

		log.error("Se va a enviar un correo electronico informando de una operacion con datos sensibles a los siguientes" + " destinatarios: " + direccionGrupo + ";; y con copia oculta a: "
				+ dirOcultas);
	}

	private void envioCorreoDatosSensiblesBastidorFI(String tipoTramite, BigDecimal numExpediente, String direccionGrupo, BigDecimal tipoBastidor, StringBuffer sb, String datoSensible,
			String numColegiado, String tipoPersona) throws OegamExcepcion {
		log.info("inicio envioCorreoDatosSensiblesBastidorFI");

		// Mantis 11888. David Sierra: Para que se envie en el correo el tipo de tramite con el nombreEnum y no con el valorEnum
		tipoTramite = TipoTramiteTrafico.convertirTexto(tipoTramite);
		tipoTramite = tipoTramite != null ? tipoTramite : "";

		sb.append("<br>Tipo de tr&aacute;mite: ").append(tipoTramite).append("; N&uacute;mero de expediente: ").append(numExpediente).append(" <br>");
		sb.append("En caso de duda, contacte con el colegio. <br>");

		// Se envía el correo
		String dirOcultas = gestorPropiedades.valorPropertie(dirOcultasOperDatoSensibleFI);

		String subject = gestorPropiedades.valorPropertie(subjectOperDatoSensible);

		subject += " - " + TipoBastidor.convertirTexto(tipoBastidor.toString());
		subject += " (" + datoSensible + ") - Colegiado: " + numColegiado + " - Tipo Tramite: " + tipoTramite + "- Tipo Persona: " + tipoPersona;

		ResultBean resultEnvio;

		try {
			resultEnvio = servicioCorreo.enviarNotificacion(new String(sb.toString().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8), null, null, subject, direccionGrupo, null, dirOcultas, null);
			if (resultEnvio == null || resultEnvio.getError())
				throw new OegamExcepcion("Se ha producido un error al enviar el mail");
		} catch (IOException e) {
			throw new OegamExcepcion("Se ha producido un error al enviar el mail");
		}

		log.error("Se va a enviar un correo electronico informando de una operacion con datos sensibles a los siguientes" + " destinatarios: " + direccionGrupo + ";; y con copia oculta a: "
				+ dirOcultas);
	}

	private void envioCorreoDatosSensiblesBastidorLE(String tipoTramite, BigDecimal numExpediente, String direccionGrupo, BigDecimal tipoBastidor, StringBuffer sb, String datoSensible,
			String numColegiado, String tipoPersona) throws OegamExcepcion {
		log.info("inicio envioCorreoDatosSensiblesBastidorLE");

		// Mantis 11888. David Sierra: Para que se envie en el correo el tipo de tramite con el nombreEnum y no con el valorEnum
		tipoTramite = TipoTramiteTrafico.convertirTexto(tipoTramite);
		tipoTramite = tipoTramite != null ? tipoTramite : "";

		sb.append("<br>Tipo de tr&aacute;mite: ").append(tipoTramite).append("; N&uacute;mero de expediente: ").append(numExpediente).append(" <br>");
		sb.append("En caso de duda, contacte con el colegio. <br>");

		// Se envía el correo
		String dirOcultas = gestorPropiedades.valorPropertie(dirOcultasOperDatoSensibleLE);

		String subject = gestorPropiedades.valorPropertie(subjectOperDatoSensible);

		subject += " - " + TipoBastidor.convertirTexto(tipoBastidor.toString());
		subject += " (" + datoSensible + ") - Colegiado: " + numColegiado + " - Tipo Tramite: " + tipoTramite + "- Tipo Persona: " + tipoPersona;

		ResultBean resultEnvio;

		try {
			resultEnvio = servicioCorreo.enviarNotificacion(new String(sb.toString().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8), null, null, subject, direccionGrupo, null, dirOcultas, null);
			if (resultEnvio == null || resultEnvio.getError())
				throw new OegamExcepcion("Se ha producido un error al enviar el mail");
		} catch (IOException e) {
			throw new OegamExcepcion("Se ha producido un error al enviar el mail");
		}

		log.error("Se va a enviar un correo electronico informando de una operacion con datos sensibles a los siguientes" + " destinatarios: " + direccionGrupo + ";; y con copia oculta a: "
				+ dirOcultas);
	}

	private void envioCorreoDatosSensiblesBastidorRE(String tipoTramite, BigDecimal numExpediente, String direccionGrupo, BigDecimal tipoBastidor, StringBuffer sb, String datoSensible,
			String numColegiado, String tipoPersona) throws OegamExcepcion {
		log.info("inicio envioCorreoDatosSensiblesBastidorRE");

		// Mantis 11888. David Sierra: Para que se envie en el correo el tipo de tramite con el nombreEnum y no con el valorEnum
		tipoTramite = TipoTramiteTrafico.convertirTexto(tipoTramite);
		tipoTramite = tipoTramite != null ? tipoTramite : "";

		sb.append("<br>Tipo de tr&aacute;mite: ").append(tipoTramite).append("; N&uacute;mero de expediente: ").append(numExpediente).append(" <br>");
		sb.append("En caso de duda, contacte con el colegio. <br>");

		// Se envía el correo
		String dirOcultas = gestorPropiedades.valorPropertie(dirOcultasOperDatoSensibleRE);

		String subject = gestorPropiedades.valorPropertie(subjectOperDatoSensible);

		subject += " - " + TipoBastidor.convertirTexto(tipoBastidor.toString());
		subject += " (" + datoSensible + ") - Colegiado: " + numColegiado + " - Tipo Tramite: " + tipoTramite + "- Tipo Persona: " + tipoPersona;

		ResultBean resultEnvio;

		try {
			resultEnvio = servicioCorreo.enviarNotificacion(new String(sb.toString().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8), null, null, subject, direccionGrupo, null, dirOcultas, null);
			if (resultEnvio == null || resultEnvio.getError())
				throw new OegamExcepcion("Se ha producido un error al enviar el mail");
		} catch (IOException e) {
			throw new OegamExcepcion("Se ha producido un error al enviar el mail");
		}

		log.error("Se va a enviar un correo electronico informando de una operacion con datos sensibles a los siguientes" + " destinatarios: " + direccionGrupo + ";; y con copia oculta a: "
				+ dirOcultas);
	}

	private void envioCorreoDatosSensiblesBastidorFR(String tipoTramite, BigDecimal numExpediente, BigDecimal tipoBastidor, StringBuffer sb, String datoSensible,
			String numColegiado, String tipoPersona) throws OegamExcepcion {
		log.info("inicio envioCorreoDatosSensiblesBastidorFR");

		// Mantis 11888. David Sierra: Para que se envie en el correo el tipo de tramite con el nombreEnum y no con el valorEnum
		tipoTramite = TipoTramiteTrafico.convertirTexto(tipoTramite);
		tipoTramite = tipoTramite != null ? tipoTramite : "";

		sb.append("<br>Tipo de tr&aacute;mite: ").append(tipoTramite).append("; N&uacute;mero de expediente: ").append(numExpediente).append(" <br>");
		sb.append("En caso de duda, contacte con el colegio. <br>");

		// Se envía el correo
		String dirOcultas = gestorPropiedades.valorPropertie(dirOcultasOperDatoSensibleFR);
		String dirSantander = gestorPropiedades.valorPropertie(destinatarioSantanderOperDatoSensibleFR);
		String subject = gestorPropiedades.valorPropertie(subjectOperDatoSensibleFR);

		subject += " - " + TipoBastidor.convertirTexto(tipoBastidor.toString());
		subject += " (" + datoSensible + ") - Colegiado: " + numColegiado + " - Tipo Tramite: " + tipoTramite + "- Tipo Persona: " + tipoPersona;

		ResultBean resultEnvio;

		try {
			resultEnvio = servicioCorreo.enviarNotificacion(new String(sb.toString().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8), null, null, subject, dirSantander, null, dirOcultas, null);
			if (resultEnvio == null || resultEnvio.getError())
				throw new OegamExcepcion("Se ha producido un error al enviar el mail");
		} catch (IOException e) {
			throw new OegamExcepcion("Se ha producido un error al enviar el mail");
		}

		log.error("Se va a enviar un correo electronico informando de una operacion con datos sensibles a los siguientes" + " destinatarios: " + dirSantander + ";; y con copia oculta a: "
				+ dirOcultas);
	}

	private boolean continuaNifBloqueado(String nif, BigDecimal idUsuario) throws OegamExcepcion {
		boolean resultado = false;
		DatosSensiblesNifVO datosSensiblesNifVO = getDatosSensiblesNifPorNif(nif);

		if (datosSensiblesNifVO != null) {
			if (datosSensiblesNifVO.getTiempoRestauracion() != null && datosSensiblesNifVO.getTiempoRestauracion().compareTo(new BigDecimal(0)) != 0) {
				if (datosSensiblesNifVO.getFechaOperacion() == null) {
					datosSensiblesNifVO.setUsuarioOperacion(idUsuario);
					datosSensiblesNifVO.setFechaOperacion(utilesFecha.formatoFecha("dd/MM/yyyy", utilesFecha.getFechaHoy()));
					datosSensiblesNifDao.actualizar(datosSensiblesNifVO);
					resultado = true;
				} else {
					if (fechaOperacionSuperada(datosSensiblesNifVO.getTiempoRestauracion(), datosSensiblesNifVO.getFechaOperacion())) {
						datosSensiblesNifVO.setEstado(new BigDecimal(0));
						datosSensiblesNifVO.setFechaBaja(utilesFecha.formatoFecha("dd/MM/yyyy", utilesFecha.getFechaHoy()));
						datosSensiblesNifDao.actualizar(datosSensiblesNifVO);
						resultado = true;
					}
				}
			} else {
				resultado = true;
			}
		}
		return resultado;
	}

	private DatosSensiblesNifVO getDatosSensiblesNifPorNif(String nif) {
		DatosSensiblesNifVO datosSensiblesNifVO = new DatosSensiblesNifVO();
		DatosSensiblesNifPK id = new DatosSensiblesNifPK();
		id.setNif(nif);
		datosSensiblesNifVO.setId(id);

		List<DatosSensiblesNifVO> listaDatosSensiblesNif = datosSensiblesNifDao.getNif(datosSensiblesNifVO);
		if (listaDatosSensiblesNif != null && !listaDatosSensiblesNif.isEmpty()) {
			return listaDatosSensiblesNif.get(0);
		}
		return null;
	}

	private boolean continuaBastidorBloqueado(String bastidor, BigDecimal idUsuario) throws OegamExcepcion {
		boolean resultado = false;
		DatosSensiblesBastidorVO datosSensiblesBastidorVO = getDatosSensiblesBastidorVOPorBastidor(bastidor);
		if (datosSensiblesBastidorVO != null) {
			if (datosSensiblesBastidorVO.getTiempoRestauracion() != null && datosSensiblesBastidorVO.getTiempoRestauracion().compareTo(new Long(0)) != 0) {
				if (datosSensiblesBastidorVO.getFechaOperacion() == null) {
					datosSensiblesBastidorVO.setUsuarioOperacion(idUsuario.longValue());
					datosSensiblesBastidorVO.setFechaOperacion(utilesFecha.formatoFecha("dd/MM/yyyy", utilesFecha.getFechaHoy()));
					datosSensiblesBastidorDao.actualizar(datosSensiblesBastidorVO);
					resultado = true;
				} else {
					if (fechaOperacionSuperada(new BigDecimal(datosSensiblesBastidorVO.getTiempoRestauracion()), datosSensiblesBastidorVO.getFechaOperacion())) {
						datosSensiblesBastidorVO.setEstado(new BigDecimal(0));
						datosSensiblesBastidorVO.setFechaBaja(utilesFecha.formatoFecha("dd/MM/yyyy", utilesFecha.getFechaHoy()));
						datosSensiblesBastidorDao.actualizar(datosSensiblesBastidorVO);
						UtilesVistaDatosSensibles.getInstance().recargarDatosSensibles();
					} else
						resultado = true;
				}
			} else {
				resultado = true;
			}
		}
		return resultado;
	}

	private DatosSensiblesBastidorVO getDatosSensiblesBastidorVOPorBastidor(String bastidor) {
		DatosSensiblesBastidorVO datosSensiblesBastidorVO = new DatosSensiblesBastidorVO();
		DatosSensiblesBastidorPK id = new DatosSensiblesBastidorPK();
		id.setBastidor(bastidor);
		datosSensiblesBastidorVO.setId(id);

		List<DatosSensiblesBastidorVO> listaDatosSensiblesBastidorVO = datosSensiblesBastidorDao.getBastidorPorId(datosSensiblesBastidorVO);

		if (listaDatosSensiblesBastidorVO != null && !listaDatosSensiblesBastidorVO.isEmpty()) {
			return listaDatosSensiblesBastidorVO.get(0);
		}
		return null;
	}

	@Transactional
	public DatosSensiblesBastidorVO getBastidorVOPorBastidor(String bastidor) {
		DatosSensiblesBastidorVO datosSensiblesBastidorVO = new DatosSensiblesBastidorVO();
		DatosSensiblesBastidorPK id = new DatosSensiblesBastidorPK();
		id.setBastidor(bastidor);
		datosSensiblesBastidorVO.setId(id);

		List<DatosSensiblesBastidorVO> listaDatosSensiblesBastidorVO = datosSensiblesBastidorDao.getBastidorPorId(datosSensiblesBastidorVO);

		if (listaDatosSensiblesBastidorVO != null && !listaDatosSensiblesBastidorVO.isEmpty()) {
			return listaDatosSensiblesBastidorVO.get(0);
		}
		return null;
	}

	public boolean comprobarBastidorCache(String bastidor) {
		return contained(UtilesVistaDatosSensibles.getInstance().getBastidoresSensibles(), bastidor);
	}

	private void envioCorreoDatosSensibles(String tipoTramite, BigDecimal numExpediente, String direccionGrupo, StringBuffer sb, String datoSensible, String numColegiado, String tipoPersona)
			throws OegamExcepcion {
		log.info("inicio envioCorreoDatosSensibles");

		// Mantis 11888. David Sierra: Para que se envie en el correo el tipo de tramite con el nombreEnum y no con el valorEnum
		tipoTramite = TipoTramiteTrafico.convertirTexto(tipoTramite);
		tipoTramite = tipoTramite != null ? tipoTramite : "";

		sb.append("<br>Tipo de tr&aacute;mite: ").append(tipoTramite).append("; N&uacute;mero de expediente: ").append(numExpediente).append(" <br>");
		sb.append("En caso de duda, contacte con el colegio. <br>");

		// Se envía el correo
		String dirOcultas = gestorPropiedades.valorPropertie(dirOcultasOperDatoSensible);
		String copia = gestorPropiedades.valorPropertie(destinatarioCorreoBastidoresConCopia);

		String subject = gestorPropiedades.valorPropertie(subjectOperDatoSensible) + " (" + datoSensible + ") - Colegiado: " + numColegiado + " - Tipo Tramite: " + tipoTramite + "- Tipo Persona: "
				+ tipoPersona;
		ResultBean resultEnvio;
		if (datoSensible != null && !datoSensible.isEmpty() && datoSensible.length() < 17) {

			try {
				resultEnvio = servicioCorreo.enviarNotificacion(sb.toString(), null, null, subject, direccionGrupo, null, dirOcultas, null);
				if (resultEnvio == null || resultEnvio.getError())
					throw new OegamExcepcion("Se ha producido un error al enviar el mail");
			} catch (IOException e) {
				throw new OegamExcepcion("Se ha producido un error al enviar el mail");
			}
		} else {
			try {
				resultEnvio = servicioCorreo.enviarNotificacion(sb.toString(), null, null, subject, direccionGrupo, copia, dirOcultas, null);
				if (resultEnvio == null || resultEnvio.getError())
					throw new OegamExcepcion("Se ha producido un error al enviar el mail");
			} catch (IOException e) {
				throw new OegamExcepcion("Se ha producido un error al enviar el mail");
			}
		}

		log.error("Se va a enviar un correo electronico informando de una operacion con datos sensibles a los siguientes" + " destinatarios: " + direccionGrupo + ";; y con copia oculta a: "
				+ dirOcultas);
	}

	/*
	 * Entra aqui porque coincide la matricula con el dato sensible Comprueba si ese dato sensible debería ser dado de baja según la fecha_operacion y el tiempo_restauracion y si tiene que ser tomado en cuenta para realiar flujo normal de un dato sensible.
	 */
	private Boolean continuaMatriculaBloqueada(String matricula, BigDecimal idUsuario) throws OegamExcepcion {
		boolean matriculaBlock = false;
		DatosSensiblesMatriculaVO datosSensiblesMatriculaVO = getDatosSensiblesMatriculaVOPorMatricula(matricula);
		if (datosSensiblesMatriculaVO != null) {
			if (datosSensiblesMatriculaVO.getTiempoRestauracion() != null && datosSensiblesMatriculaVO.getTiempoRestauracion().compareTo(new BigDecimal(0)) != 0) {
				if (datosSensiblesMatriculaVO.getFechaOperacion() == null) {
					datosSensiblesMatriculaVO.setFechaOperacion(utilesFecha.formatoFecha("dd/MM/yyyy", utilesFecha.getFechaHoy()));
					datosSensiblesMatriculaVO.setUsuarioOperacion(idUsuario);
					datosSensiblesMatriculaDao.actualizar(datosSensiblesMatriculaVO);
					matriculaBlock = true;
				} else {
					if (fechaOperacionSuperada(datosSensiblesMatriculaVO.getTiempoRestauracion(), datosSensiblesMatriculaVO.getFechaOperacion())) {
						datosSensiblesMatriculaVO.setEstado(new BigDecimal(0));
						datosSensiblesMatriculaVO.setFechaBaja(utilesFecha.formatoFecha("dd/MM/yyyy", utilesFecha.getFechaHoy()));
						datosSensiblesMatriculaDao.actualizar(datosSensiblesMatriculaVO);
						UtilesVistaDatosSensibles.getInstance().recargarDatosSensibles();
						matriculaBlock = true;
					}
				}
			} else {
				matriculaBlock = true;
			}
		}
		return matriculaBlock;
	}

	public boolean fechaOperacionSuperada(BigDecimal tiempoRestauracion, Date fechaOperacion) {
		long diferenciaDias = 0;
		boolean resultado = false;

		try {
			diferenciaDias = utilesFecha.diferenciaFechaEnDias(fechaOperacion, new Date());
		} catch (Exception e) {
			log.error("Se ha producido un error en datos sensibles al comparar la fecha de operacion con la actual", e);
		}

		// 24 horas
		if (tiempoRestauracion.compareTo(new BigDecimal(1)) == 0) {
			if (diferenciaDias > 0) {
				resultado = true;
			}
		}

		// 48 horas
		if (tiempoRestauracion.compareTo(new BigDecimal(2)) == 0
			&& diferenciaDias > 1) {
			resultado = true;
		}

		return resultado;
	}

	private DatosSensiblesMatriculaVO getDatosSensiblesMatriculaVOPorMatricula(String matricula) {
		DatosSensiblesMatriculaVO datosSensiblesMatriculaVO = new DatosSensiblesMatriculaVO();
		DatosSensiblesMatriculaPK id = new DatosSensiblesMatriculaPK();
		id.setMatricula(matricula);
		datosSensiblesMatriculaVO.setId(id);

		List<DatosSensiblesMatriculaVO> listaDatosSensiblesMatriculaVO = datosSensiblesMatriculaDao.buscarPorMatricula(datosSensiblesMatriculaVO);

		if (listaDatosSensiblesMatriculaVO != null && !listaDatosSensiblesMatriculaVO.isEmpty()) {
			datosSensiblesMatriculaVO = listaDatosSensiblesMatriculaVO.get(0);
		} else {
			return null;
		}
		return datosSensiblesMatriculaVO;
	}

	private boolean contained(List<String> list, String s) {
		if (list != null && s != null) {
			for (String sl : list) {
				if (s.equalsIgnoreCase(sl)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	@Transactional
	public ResultBean getListaBastidoresErroneos(String bastidoresErroneos, String grupoBastidores) {
		ResultBean result = null;
		String[] bastidores = bastidoresErroneos.split("-");
		String[] grupos = grupoBastidores.split("-");
		List<DatosSensiblesBastidorVO> listaDatosSensiblesBastidorVOs = new ArrayList<>();
		List<String> listaMensajes = new ArrayList<>();
		for (int i = 0; i < bastidores.length; i++) {
			DatosSensiblesBastidorVO datosSensiblesBastidorVO = getBastidorVOPorBastidorYGrupo(bastidores[i], grupos[i]);
			if (datosSensiblesBastidorVO != null) {
				listaDatosSensiblesBastidorVOs.add(datosSensiblesBastidorVO);
			} else {
				String mensaje = "El Bastidor " + bastidores[i] + " no se encuentra dado de alta en la aplicación para el " + grupos[i];
				listaMensajes.add(mensaje);
			}
		}
		result = new ResultBean(false);
		result.setListaMensajes(listaMensajes);
		result.addAttachment("listaBastidoresDto", conversor.transform(listaDatosSensiblesBastidorVOs, DatosSensiblesBastidorDto.class));
		return result;
	}

	private DatosSensiblesBastidorVO getBastidorVOPorBastidorYGrupo(String bastidor, String grupo) {
		DatosSensiblesBastidorVO datosSensiblesBastidorVO = new DatosSensiblesBastidorVO();
		DatosSensiblesBastidorPK id = new DatosSensiblesBastidorPK();
		id.setBastidor(bastidor);
		id.setIdGrupo(grupo);
		datosSensiblesBastidorVO.setId(id);
		List<DatosSensiblesBastidorVO> listaDatosSensiblesBastidorVO = datosSensiblesBastidorDao.getBastidorPorId(datosSensiblesBastidorVO);
		if (listaDatosSensiblesBastidorVO != null && !listaDatosSensiblesBastidorVO.isEmpty()) {
			return listaDatosSensiblesBastidorVO.get(0);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public DatosSensiblesBastidorVO getBastidorPorId(String numeroBastidor, String idGrupo) {
		DatosSensiblesBastidorVO bastidorVO = null;
		try {
			bastidorVO = getBastidorVOPorBastidorYGrupo(numeroBastidor, idGrupo);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener los datos del bastidor por su id, error: ", e);
		}
		return bastidorVO;
	}

	@Override
	public PaginatedList crearListaResultadosImportarErroneosPaginated(List<BastidorErroneoBean> listaBastidoresErroneos, String resultadosPorPaginaErroneos, int pageError, String dirError,
			String columdefectImportados) {
		if (listaBastidoresErroneos != null) {
			int resultPorPagina = 5;
			if (resultadosPorPaginaErroneos != null) {
				resultPorPagina = resultadosPorPaginaErroneos.equals("0") ? 5 : Integer.parseInt(resultadosPorPaginaErroneos);
			}
			int e = 0;
			if (pageError != 0) {
				e = pageError - 1;
			} else {
				pageError = 1;
			}
			String sDirError = "asc";
			if (dirError != null) {
				sDirError = dirError;
			}

			List<BastidorErroneoBean> listAuxBastidorErroneo = new ArrayList<>();

			int eInicio = e * resultPorPagina;
			int eFinal = pageError * resultPorPagina;

			if (eFinal > listaBastidoresErroneos.size()) {
				eFinal = listaBastidoresErroneos.size();
			}

			if (listaBastidoresErroneos.size() > resultPorPagina) {

				for (e = eInicio; e < eFinal; e++) {
					listAuxBastidorErroneo.add(listaBastidoresErroneos.get(e));
				}
			} else {
				listAuxBastidorErroneo = listaBastidoresErroneos;
			}
			return new PaginatedListImpl(resultPorPagina, pageError, sDirError, columdefectImportados, listaBastidoresErroneos.size(), listAuxBastidorErroneo);
		}
		return null;
	}

	@Override
	public PaginatedList crearListaPaginated(List<DatosSensiblesAgrupados> list, String resultadosPorPagina, int page, String dir, String columdefectImportados) {
		int resultPorPagina = resultadosPorPagina.equals("0") ? 5 : Integer.parseInt(resultadosPorPagina);
		List<DatosSensiblesAgrupados> listAux = new ArrayList<>();
		int e = page - 1;
		int eInicio = e * resultPorPagina;
		int eFinal = page * resultPorPagina;

		if (eFinal > list.size()) {
			eFinal = list.size();
		}

		if (list.size() > resultPorPagina) {

			for (e = eInicio; e < eFinal; e++) {
				listAux.add(list.get(e));
			}
		} else {
			listAux = list;
		}
		return new PaginatedListImpl(resultPorPagina, page, dir, columdefectImportados, list.size(), listAux);
	}

	@Override
	@Transactional
	public void importarBastidoresFTPSantander(FicheroBean fichero, ResultadoImportacionSantanderBean resultado, Boolean esRetail) {
		try {
			Boolean esAlta = Boolean.FALSE;
			List<ImportarBastidorDto> listaImportadosOK = new ArrayList<>();
			List<ImportarBastidorDto> listaImportadosError = new ArrayList<>();
			List<ImportarBastidorDto> listaBastidoreExistentes = new ArrayList<>();
			List<String> listaMensajesError = new ArrayList<String>();
			List<String> listaMensajesOK = new ArrayList<String>();
			BigDecimal idContrato = new BigDecimal(gestorPropiedades.valorPropertie(ServicioDatosSensibles.PROPERTY_KEY_DATOSSENSIBLES_ID_CONTRATO));
			String numColegiado = gestorPropiedades.valorPropertie(ServicioDatosSensibles.PROPERTY_KEY_DATOSSENSIBLES_NUM_COLEGIADO);
			BigDecimal idUsuario = new BigDecimal(gestorPropiedades.valorPropertie(ServicioDatosSensibles.PROPERTY_KEY_DATOSSENSIBLES_ID_USUARIO));
			String apellidosNombre = gestorPropiedades.valorPropertie(ServicioDatosSensibles.PROPERTY_KEY_DATOSSENSIBLES_APELLIDOS_NOMBRE);
			String sPropertieEncolar = gestorPropiedades.valorPropertie(encolarTipoBastidor);
			Boolean esPosibleEncolar = Boolean.FALSE;
			if (sPropertieEncolar != null && sPropertieEncolar.equals("true")) {
				esPosibleEncolar = Boolean.TRUE;
			}
			for (ImportarBastidorDto importarBastidorDto : resultado.getListaBastidoresImportar()) {
				ResultadoAltaBastidorBean resultadoOperacion = new ResultadoAltaBastidorBean();
				if (importarBastidorDto.getBastidor() != null && !importarBastidorDto.getBastidor().equals("")) {
					if (TipoImportacionBastidores.Alta.getValorEnum().equals(importarBastidorDto.getTipoRegistro())) {
						esAlta = Boolean.TRUE;
						resultadoOperacion = altaBastidorImportadoFTP(importarBastidorDto, idContrato, numColegiado, idUsuario, apellidosNombre, esRetail, esPosibleEncolar);
						if (!esRetail) {
							resultado.addResultadoAltaBastCart(resultadoOperacion);
							resultado.addNumBastidoresCart();
						} else {
							resultado.addResultadoAltaBastRetail(resultadoOperacion);
							resultado.addNumBastidoresRetail();
						}
					} else if (TipoImportacionBastidores.Baja.getValorEnum().equals(importarBastidorDto.getTipoRegistro())) {
						esAlta = Boolean.FALSE;
						resultadoOperacion = bajaBastidorImportado(importarBastidorDto, idContrato, numColegiado, idUsuario, apellidosNombre, esRetail, esPosibleEncolar);
						if (!esRetail) {
							resultado.addResultadoBajaBastCart(resultadoOperacion);
							resultado.addNumBastidoresCart();
						} else {
							resultado.addResultadoBajaBastRetail(resultadoOperacion);
							resultado.addNumBastidoresRetail();
						}
					} else {
						resultadoOperacion.setResultadoConError(true);
						resultadoOperacion.setComentario("El tipo de operación no es aceptada por la plataforma.");
					}
				} else {
					resultadoOperacion.setResultadoConError(true);
					resultadoOperacion.setComentario("El bastidor esta vacio o es nulo.");
				}

				if (resultadoOperacion.getBastidorExistente()) {
					listaBastidoreExistentes.add(importarBastidorDto);
				} else if (resultadoOperacion.isResultadoConError()) {
					if (esRetail) {
						if (esAlta) {
							resultado.addNumAltaBastRetailError();
						} else {
							resultado.addNumBajaBastRetailError();
						}
					} else {
						if (esAlta) {
							resultado.addNumAltaBastCartError();
						} else {
							resultado.addNumBajaBastCartError();
						}
					}
					listaMensajesError.add(resultadoOperacion.getComentario());
					listaImportadosError.add(importarBastidorDto);
				} else {
					listaMensajesOK.add(resultadoOperacion.getComentario());
					listaImportadosOK.add(importarBastidorDto);
					if (esRetail) {
						if (esAlta) {
							resultado.addNumAltaBastRetailOK();
						} else {
							resultado.addNumBajaBastRetailOK();
						}
					} else {
						if (esAlta) {
							resultado.addNumAltaBastCartOK();
						} else {
							resultado.addNumBajaBastCartOK();
						}
					}
				}
			}
			enviarCorreosImportacionFTP(listaImportadosOK, listaImportadosError, resultado.getListaLineasErroneas(), listaBastidoreExistentes, listaMensajesError, listaMensajesOK, esAlta, esRetail);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de importar el fichero de bastidores, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setExcepcion(e);
		} catch (OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de importar el fichero de bastidores, error: ", e.getMensajeError1());
			resultado.setError(Boolean.TRUE);
			resultado.setExcepcion(new Exception(e.getMensajeError1()));
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
	}

	private void enviarCorreosImportacionFTP(List<ImportarBastidorDto> listaImportadosOK, List<ImportarBastidorDto> listaImportadosError, List<String> listaLineasErroneas,
			List<ImportarBastidorDto> listaBastidoreExistentes, List<String> listaMensajesError, List<String> listaMensajesOK, Boolean esAlta, Boolean esRetail) throws OegamExcepcion {
		String mensajeEmail = "";
		String asunto = "";
		if (esAlta) {
			asunto = asuntoCorreo_Alta;
		} else {
			asunto = asuntoCorreo_Baja;
		}

		if (listaImportadosOK != null && !listaImportadosOK.isEmpty()) {
			if (esAlta) {
				mensajeEmail = "Se han dado de alta los siguientes Bastidores " + (esRetail ? TipoBastidorSantander.RT.getNombreEnum() : TipoBastidorSantander.CT.getNombreEnum()) + ":<br> ";
			} else {
				mensajeEmail = "Se han dado de baja los siguientes Bastidores " + (esRetail ? TipoBastidorSantander.RT.getNombreEnum() : TipoBastidorSantander.CT.getNombreEnum()) + ":<br> ";
			}
			for (ImportarBastidorDto bastidorImportado : listaImportadosOK) {
				mensajeEmail += bastidorImportado.getBastidor() + "<br>";
			}
		}

		if (listaImportadosError != null && !listaImportadosError.isEmpty()) {
			if (esAlta) {
				mensajeEmail += "<br> Los siguientes bastidores han concluido con error porque ya existian y no han podido darse de alta " + (esRetail ? TipoBastidorSantander.RT.getNombreEnum()
						: TipoBastidorSantander.CT.getNombreEnum()) + ": <br>";
			} else {
				mensajeEmail += "<br> Los siguientes bastidores han concluido con error porque no existian y no se han podido dar de baja " + (esRetail ? TipoBastidorSantander.RT.getNombreEnum()
						: TipoBastidorSantander.CT.getNombreEnum()) + ": <br>";
			}
			for (ImportarBastidorDto bastidorImportado : listaImportadosError) {
				mensajeEmail += bastidorImportado.getBastidor() + "<br>";
			}
		}

		if (listaLineasErroneas != null && !listaLineasErroneas.isEmpty()) {
			mensajeEmail += "<br> El fichero contiene las siguientes lineas con errores de formato: <br>";
			for (String lineaErronea : listaLineasErroneas) {
				mensajeEmail += "  - " + lineaErronea + " <br>";
			}
		}

		if (listaBastidoreExistentes != null && !listaBastidoreExistentes.isEmpty()) {
			mensajeEmail += "<br> El fichero contiene los siguientes bastidores que ya existian en la aplicación y estan dados de baja, por lo que se ha procedido a su actualización automática "
					+ (esRetail ? TipoBastidorSantander.RT.getNombreEnum() : TipoBastidorSantander.CT.getNombreEnum()) + ": <br>";
			for (ImportarBastidorDto bastidor : listaBastidoreExistentes) {
				mensajeEmail += "  - " + bastidor.getBastidor() + " <br>";
			}
		}

		String subject = gestorPropiedades.valorPropertie(asunto);
		String destinatario = gestorPropiedades.valorPropertie(destinatarioCorreoBastidores);
		String destinatarioOculto = gestorPropiedades.valorPropertie(destinatarioCorreoBastidoresOculto);
		ResultBean resultEnvio;
		try {
			resultEnvio = servicioCorreo.enviarCorreo(mensajeEmail, null, null, subject, destinatario, null, destinatarioOculto, null);
			if (resultEnvio == null || resultEnvio.getError())
				throw new OegamExcepcion("Se ha producido un error al enviar el mail informando el resultado de bastidores en el proceso .", EnumError.error_00002);
		} catch (IOException e) {
			throw new OegamExcepcion("Se ha producido un error al enviar el mail informando el resultado de bastidores en el proceso .", EnumError.error_00002);
		}

	}

	private ResultadoAltaBastidorBean bajaBastidorImportado(ImportarBastidorDto importarBastidorDto, BigDecimal idContrato, String numColegiado, BigDecimal idUsuario, String apellidosNombre,
			Boolean esRetail, Boolean esPosibleEncolar) throws OegamExcepcion {
		ResultadoAltaBastidorBean resultadoOperacion = new ResultadoAltaBastidorBean();
		DatosSensiblesBastidorVO bastidorVO = new DatosSensiblesBastidorVO();
		DatosSensiblesBastidorPK id = new DatosSensiblesBastidorPK();
		id.setBastidor(importarBastidorDto.getBastidor());
		id.setIdGrupo(gestorPropiedades.valorPropertie("importacion.datossensibles.usuario.grupo"));
		bastidorVO.setId(id);
		List<DatosSensiblesBastidorVO> lista = datosSensiblesBastidorDao.getBastidorPorId(bastidorVO);
		if (lista != null && !lista.isEmpty()) {
			String xmlEnviar = bastidorVO.getId().getBastidor() + "_" + bastidorVO.getId().getIdGrupo();
			bastidorVO = lista.get(0);
			if (bastidorVO.getEstado().equals(BigDecimal.ONE)) {
				bastidorVO.setEstado(new BigDecimal(0));
				bastidorVO.setFechaBaja(Calendar.getInstance().getTime());
				datosSensiblesBastidorDao.borradoLogico(bastidorVO);
				evolucionDatosSensiblesBastidorDao.guardar(dameEvolucionDatosSensiblesBastidorVO(bastidorVO, "BORRADO LOGICO", idUsuario, "SANTANDER"));
				if (esPosibleEncolar) {
					ResultBean resultadoProcesoA9 = servicioCola.crearSolicitud(ConstantesProcesos.PROCESO_SEA_ENVIO_DS, xmlEnviar, getNodoSolicitud(), "N/A", null, idUsuario, null, idContrato);
					if (resultadoProcesoA9.getError()) {
						resultadoOperacion.setResultadoConError(Boolean.TRUE);
						String mensaje = "";
						for (String mensajeError : resultadoProcesoA9.getListaMensajes()) {
							mensaje += mensajeError + ".";
						}
						resultadoOperacion.setComentario(mensaje);
					}
				}
			} else {
				resultadoOperacion.setComentario("El Bastidor " + bastidorVO.getId().getBastidor() + " del tipo " + TipoBastidor.convertirTexto(bastidorVO.getTipoBastidor().toString())
						+ " ya se encuentra dado de baja para el grupo " + bastidorVO.getGrupo().getDescGrupo() + " con fecha de baja del " + utilesFecha.formatoFecha("dd/MM/yyyy", bastidorVO
								.getFechaBaja()));
				resultadoOperacion.setResultadoConError(Boolean.TRUE);
			}
		} else {
			resultadoOperacion.setComentario("El Bastidor " + bastidorVO.getId().getBastidor() + " del tipo " + TipoBastidor.convertirTexto(bastidorVO.getTipoBastidor().toString())
					+ " no se encuentra dado de alta para el grupo " + servicioGrupo.getDescripcionGrupo(bastidorVO.getId().getIdGrupo()));
			resultadoOperacion.setResultadoConError(Boolean.TRUE);
		}
		return resultadoOperacion;
	}

	private ResultadoAltaBastidorBean altaBastidorImportadoFTP(ImportarBastidorDto importarBastidorDto, BigDecimal idContrato, String numColegiado, BigDecimal idUsuario, String apellidosNombre,
			Boolean esRetail, Boolean esPosibleEncolar) throws OegamExcepcion {
		ResultadoAltaBastidorBean resultadoOperacion = new ResultadoAltaBastidorBean();
		DatosSensiblesBastidorVO bastidorVO = crearNuevoBastidor(importarBastidorDto, idContrato, numColegiado, idUsuario, apellidosNombre, esRetail);
		String xmlEnviar = bastidorVO.getId().getBastidor() + "_" + bastidorVO.getId().getIdGrupo();
		List<DatosSensiblesBastidorVO> listaBastidores = datosSensiblesBastidorDao.existeBastidor(bastidorVO.getId().getBastidor(), bastidorVO.getId().getIdGrupo());
		if (listaBastidores == null || listaBastidores.isEmpty()) {
			datosSensiblesBastidorDao.guardar(bastidorVO);
			evolucionDatosSensiblesBastidorDao.guardar(dameEvolucionDatosSensiblesBastidorVO(bastidorVO, "ALTA", idUsuario, "SANTANDER"));
			if (esPosibleEncolar) {
				ResultBean resultadoProcesoA9 = servicioCola.crearSolicitud(ConstantesProcesos.PROCESO_SEA_ENVIO_DS, xmlEnviar, getNodoSolicitud(), "N/A", null, idUsuario, null, idContrato);
				if (resultadoProcesoA9.getError()) {
					resultadoOperacion.setResultadoConError(Boolean.TRUE);
					String mensaje = "";
					for (String mensajeError : resultadoProcesoA9.getListaMensajes()) {
						mensaje += mensajeError + ".";
					}
					resultadoOperacion.setComentario(mensaje);
				}
			}
		} else {
			DatosSensiblesBastidorVO auxBastidorVO = listaBastidores.get(0);
			if (auxBastidorVO.getEstado().equals(BigDecimal.ZERO) && auxBastidorVO.getFechaAlta() != null) {
				if (auxBastidorVO.getTipoBastidor().equals(bastidorVO.getTipoBastidor())) {
					auxBastidorVO.setEstado(BigDecimal.ONE);
					auxBastidorVO.setFechaBaja(null);
					auxBastidorVO.setFechaAlta(Calendar.getInstance().getTime());
					datosSensiblesBastidorDao.actualizar(auxBastidorVO);
					evolucionDatosSensiblesBastidorDao.guardar(dameEvolucionDatosSensiblesBastidorVO(bastidorVO, "MODIFICACION", idUsuario, "SANTANDER"));
					if (esPosibleEncolar) {
						ResultBean resultadoProcesoA9 = servicioCola.crearSolicitud(ConstantesProcesos.PROCESO_SEA_ENVIO_DS, xmlEnviar, getNodoSolicitud(), "N/A", null, idUsuario, null, idContrato);
						if (resultadoProcesoA9.getError()) {
							resultadoOperacion.setResultadoConError(Boolean.TRUE);
							String mensaje = "";
							for (String mensajeError : resultadoProcesoA9.getListaMensajes()) {
								mensaje += mensajeError + ".";
							}
							resultadoOperacion.setComentario(mensaje);
						}
					}
					if (!resultadoOperacion.isResultadoConError()) {
						resultadoOperacion.setComentario("El Bastidor " + bastidorVO.getId().getBastidor() + " ya se encontraba dado de baja para el grupo " + auxBastidorVO.getGrupo().getDescGrupo()
								+ " con fecha de alta del " + utilesFecha.formatoFecha("dd/MM/yyyy", auxBastidorVO.getFechaAlta()) + " por lo que se ha actualizado su estado y su fecha de alta.");
						resultadoOperacion.setBastidorExistente(Boolean.TRUE);
					}
				} else {
					resultadoOperacion.setComentario("El Bastidor " + bastidorVO.getId().getBastidor() + " ya se encuentra dado de alta y activo para el grupo " + auxBastidorVO.getGrupo()
							.getDescGrupo() + " con fecha de alta del " + utilesFecha.formatoFecha("dd/MM/yyyy", auxBastidorVO.getFechaAlta()) + " como " + TipoBastidor.convertirTexto(auxBastidorVO
									.getTipoBastidor().toString()) + ", por lo que no se puede actualizar como " + TipoBastidor.convertirTexto(bastidorVO.getTipoBastidor().toString()));
					resultadoOperacion.setResultadoConError(Boolean.TRUE);
				}
			} else if (auxBastidorVO.getEstado().equals(BigDecimal.ONE)) {
				resultadoOperacion.setComentario("El Bastidor " + bastidorVO.getId().getBastidor() + " ya se encuentra dado de alta y activo para el grupo " + auxBastidorVO.getGrupo().getDescGrupo()
						+ " con fecha de alta del " + utilesFecha.formatoFecha("dd/MM/yyyy", auxBastidorVO.getFechaAlta()));
				resultadoOperacion.setResultadoConError(Boolean.TRUE);
			}
		}
		return resultadoOperacion;
	}

	private DatosSensiblesBastidorVO crearNuevoBastidor(ImportarBastidorDto importarBastidorDto, BigDecimal idContrato, String numColegiado, BigDecimal idUsuario, String apellidosNombre,
			Boolean esRetail) {
		DatosSensiblesBastidorVO datosSensiblesBastidorVO = new DatosSensiblesBastidorVO();
		DatosSensiblesBastidorPK id = new DatosSensiblesBastidorPK();
		id.setBastidor(importarBastidorDto.getBastidor().toUpperCase());
		id.setIdGrupo(gestorPropiedades.valorPropertie("importacion.datossensibles.usuario.grupo"));
		datosSensiblesBastidorVO.setId(id);

		datosSensiblesBastidorVO.setNumColegiado(numColegiado);
		datosSensiblesBastidorVO.setIdUsuario(idUsuario);
		datosSensiblesBastidorVO.setApellidosNombre(apellidosNombre);

		datosSensiblesBastidorVO.setFechaAlta(Calendar.getInstance().getTime());
		datosSensiblesBastidorVO.setEstado(new BigDecimal(1));
		datosSensiblesBastidorVO.setTiempoRestauracion(Long.parseLong(TiempoBajaDatosSensibles.No_caduca.toString()));

		if (importarBastidorDto.getTipoImportado() != null) {
			datosSensiblesBastidorVO.setTipoBastidor(new BigDecimal(importarBastidorDto.getTipoImportado()));
		}
		if (esRetail) {
			datosSensiblesBastidorVO.setTipoBastidorSantander(new BigDecimal(TipoBastidorSantander.RT.getValorEnum()));
		} else {
			datosSensiblesBastidorVO.setTipoBastidorSantander(new BigDecimal(TipoBastidorSantander.CT.getValorEnum()));
		}
		return datosSensiblesBastidorVO;
	}

	@Override
	@Transactional(rollbackFor = OegamExcepcion.class)
	@SuppressWarnings("unchecked")
	public ResultadoDatosSensibles importarBastidor(DatosSensiblesBean datosSensiblesBean, FicheroBean fichero, BigDecimal idContrato, String numColegiado, BigDecimal idUsuario,
			String apellidosNombre, List<ResultadoAltaBastidorBean> listaResultadosBastidor, String origenCambio) throws OegamExcepcion {
		ArrayList<String> listaMensajes = new ArrayList<String>();
		ArrayList<BastidorErroneoBean> listaBastidoresErroneos = new ArrayList<BastidorErroneoBean>();
		ArrayList<String> listaBastidoresExistentes = new ArrayList<String>();
		ArrayList<String> listaMensajesAdvertencias = new ArrayList<String>();
		List<ImportarBastidorDto> listaBastidoresOk = new ArrayList<ImportarBastidorDto>();
		ResultadoDatosSensibles resultadoDatosSensibles = null;
		BastidorErroneoBean bastidorErroneoBean = null;
		String mensaje = null;
		boolean esAlta = false;
		String sPropertieEncolar = gestorPropiedades.valorPropertie(encolarTipoBastidor);
		try {
			List<ImportarBastidorDto> listaBastidoresImportar = null;
			ResultBean result = getListaBastidoresImportar(fichero, datosSensiblesBean.getTipoFichero());
			boolean existenDatosError = false;
			String tipoBastidor = null;
			if (result == null || !result.getError()) {
				listaBastidoresImportar = (List<ImportarBastidorDto>) result.getAttachment("listaBastidoresImportar");
				List<String> listaLineasErroneas = (List<String>) result.getAttachment("listaLineasErroneas");
				ResultBean resultado = null;
				tipoBastidor = (String) result.getAttachment("tipoBastidor");
				for (ImportarBastidorDto importarBastidorDto : listaBastidoresImportar) {
					ResultadoAltaBastidorBean resultadoOperacion = new ResultadoAltaBastidorBean();
					if (importarBastidorDto.getBastidor() != null && !importarBastidorDto.getBastidor().equals("")) {
						datosSensiblesBean.setTipoControl(importarBastidorDto.getTipoImportado());
						if (importarBastidorDto.getTipoImportado() != null && !importarBastidorDto.getTipoImportado().equals("")) {
							datosSensiblesBean.setTextoAgrupacion(importarBastidorDto.getBastidor());
							resultadoOperacion.setOperacion(TipoImportacionBastidores.convertir(importarBastidorDto.getTipoRegistro()).toString());
							resultadoOperacion.setBastidor(importarBastidorDto.getBastidor());
							boolean esPosibleEncolar = "true".equalsIgnoreCase(sPropertieEncolar) || TipoBastidor.VN.getValorEnum().equals(importarBastidorDto.getTipoImportado());
							if (TipoImportacionBastidores.Alta.getValorEnum().equals(importarBastidorDto.getTipoRegistro())) {
								esAlta = true;
								resultado = guardarBastidorImportado(datosSensiblesBean, idContrato, numColegiado, idUsuario, apellidosNombre, origenCambio);
								if (resultado != null && resultado.getError()) {
									resultadoOperacion.setResultadoConError(true);
									if (!resultado.getListaMensajes().isEmpty()) {
										mensaje = resultado.getListaMensajes().get(0);
									}
									listaMensajes.add(mensaje);
									resultadoOperacion.setComentario(mensaje);
									bastidorErroneoBean = new BastidorErroneoBean(datosSensiblesBean.getTextoAgrupacion(), datosSensiblesBean.getGrupo(), mensaje, esPosibleEncolar);
									listaBastidoresErroneos.add(bastidorErroneoBean);
								} else if (resultado != null && !resultado.getError()) {
									listaMensajesAdvertencias.add(resultado.getListaMensajes().get(0));
									listaBastidoresExistentes.add(importarBastidorDto.getBastidor());
									resultadoOperacion.setResultadoConError(false);
									resultadoOperacion.setComentario(resultado.getListaMensajes().get(0));
								} else {
									listaBastidoresOk.add(importarBastidorDto);
								}
							} else if (TipoImportacionBastidores.Baja.getValorEnum().equals(importarBastidorDto.getTipoRegistro())) {
								esAlta = false;
								resultado = eliminarBastidorImportado(datosSensiblesBean, idContrato, idUsuario, origenCambio);
								if (resultado != null && !resultado.getError()) {
									if (!resultado.getListaMensajes().isEmpty()) {
										mensaje = resultado.getListaMensajes().get(0);
									}
									listaMensajesAdvertencias.add(mensaje);
									listaBastidoresExistentes.add(importarBastidorDto.getBastidor());
									resultadoOperacion.setResultadoConError(false);
									resultadoOperacion.setComentario(mensaje);
								} else if (resultado != null && resultado.getError()) {
									resultadoOperacion.setResultadoConError(true);
									if (!resultado.getListaMensajes().isEmpty()) {
										mensaje = resultado.getListaMensajes().get(0);
									}
									listaMensajes.add(mensaje);
									resultadoOperacion.setComentario(mensaje);
									bastidorErroneoBean = new BastidorErroneoBean(datosSensiblesBean.getTextoAgrupacion(), datosSensiblesBean.getGrupo(), mensaje, false);
									listaBastidoresErroneos.add(bastidorErroneoBean);
								} else {
									listaBastidoresOk.add(importarBastidorDto);
								}
							} else {
								mensaje = "El bastidor " + importarBastidorDto.getBastidor() + " tiene un tipo importacion no admitida.";
								listaMensajes.add(mensaje);
								bastidorErroneoBean = new BastidorErroneoBean(importarBastidorDto.getBastidor(), datosSensiblesBean.getGrupo(), mensaje, false);
								listaBastidoresErroneos.add(bastidorErroneoBean);
								resultadoOperacion.setResultadoConError(true);
								resultadoOperacion.setComentario(mensaje);
							}
						} else {
							mensaje = "El bastidor " + importarBastidorDto.getBastidor() + " no tiene tipo importacion, por lo que no se importara en la aplicación.";
							listaMensajes.add(mensaje);
							bastidorErroneoBean = new BastidorErroneoBean(importarBastidorDto.getBastidor(), datosSensiblesBean.getGrupo(), mensaje, false);
							listaBastidoresErroneos.add(bastidorErroneoBean);
							resultadoOperacion.setResultadoConError(true);
							resultadoOperacion.setComentario(mensaje);
						}
					} else {
						existenDatosError = true;
						resultadoOperacion.setResultadoConError(true);
					}
					listaResultadosBastidor.add(resultadoOperacion);
				}

				if (listaLineasErroneas != null && !listaLineasErroneas.isEmpty()) {
					for (String lineaError : listaLineasErroneas) {
						ResultadoAltaBastidorBean resultadoAltaBastidor = new ResultadoAltaBastidorBean();
						resultadoAltaBastidor.setResultadoConError(true);
						resultadoAltaBastidor.setComentario(lineaError);
						listaResultadosBastidor.add(resultadoAltaBastidor);
					}
				}
				crearListasResultadosYEnviarCorreos(listaMensajes, listaBastidoresErroneos, listaBastidoresOk, listaLineasErroneas, listaBastidoresExistentes, existenDatosError, esAlta, TipoBastidor
						.convertirTexto(tipoBastidor));
				resultadoDatosSensibles = new ResultadoDatosSensibles(true, false);
				resultadoDatosSensibles.setListaBastidoresErroneos(listaBastidoresErroneos);
				// resultadoDatosSensibles.setListaBastidoresImportados(listaBastidoresOk);
				resultadoDatosSensibles.setListaMensajes(listaMensajes);
				resultadoDatosSensibles.setListaMensajesAdvertencias(listaMensajesAdvertencias);
			} else {
				// Mantis #18011. No quieren que se envie un correo cuando el archivo esta vacío
				if (!result.getListaMensajes().get(0).contains("vacio")) {
					enviarCorreoImportacionError(result.getListaMensajes().get(0), fichero.getNombreDocumento(), fichero.getExtension());
				}
				ResultadoAltaBastidorBean resultadoOperacion = new ResultadoAltaBastidorBean();
				resultadoOperacion.setResultadoConError(true);
				resultadoOperacion.setComentario(result.getListaMensajes().get(0));
				listaResultadosBastidor.add(resultadoOperacion);
				resultadoDatosSensibles = new ResultadoDatosSensibles(true, true, result.getListaMensajes().get(0));
			}
		} catch (Exception e) {
			throw new OegamExcepcion(EnumError.error_00001, e.getMessage());
		}

		return resultadoDatosSensibles;
	}

	private ResultBean getListaBastidoresImportar(FicheroBean fichero, String tipoFichero) {
		ResultBean result = null;
		if (TipoFicheroImportacionBastidores.EXCEL.getCodigo() == Long.parseLong(tipoFichero)) {
			result = getListaBastidoresImportarExcel(fichero);
		} else if (TipoFicheroImportacionBastidores.TXT.getCodigo() == Long.parseLong(tipoFichero) || TipoFicheroImportacionBastidores.DAT.getCodigo() == Long.parseLong(tipoFichero)) {
			result = getListaBastidoresImportarTxt(fichero);
		}
		return result;
	}

	private ResultBean getListaBastidoresImportarTxt(FicheroBean fichero) {
		List<ImportarBastidorDto> listaBastidoresImportar = null;
		List<String> listaLineasErroneas = new ArrayList<String>();
		String mensajeError = null;
		ImportarBastidorDto importarBastidorDto = null;
		BufferedReader inputAnterior = null;
		ResultBean resultado = null;
		String tipoBastidor = null;
		try {
			InputStream fin = fichero.getInputStreamFromBytesOrFile();
			if (fin != null) {
				inputAnterior = new BufferedReader(new InputStreamReader(fin, Claves.ENCODING_ISO88591));
				String ln = null;
				if (inputAnterior != null) {
					listaBastidoresImportar = new ArrayList<ImportarBastidorDto>();
					while ((ln = inputAnterior.readLine()) != null) {
						int inicio = 0;
						int iniFin = 1;
						if (ln.length() <= 28 && ln.length() >= 11) {
							importarBastidorDto = new ImportarBastidorDto();
							String tipoRegistro = ln.substring(inicio, iniFin).toUpperCase();
							if ("A".equals(tipoRegistro) || "B".equals(tipoRegistro)) {
								importarBastidorDto.setTipoRegistro(tipoRegistro);
								inicio = iniFin;
								iniFin = 3;
								String tipoImport = ln.substring(inicio, iniFin).toUpperCase();
								importarBastidorDto.setTipoImportado(TipoBastidor.getValorEnumPorPorNombreEnum(tipoImport));
								tipoBastidor = importarBastidorDto.getTipoImportado();
								if (tipoBastidor != null) {
									inicio = iniFin;
									iniFin = 8 + iniFin;
									String fechaEnv = ln.substring(inicio, iniFin);
									if (utilesFecha.comprobarFecha(fechaEnv)) {
										importarBastidorDto.setFechaEnvio(fechaEnv);
										String bast = ln.substring(iniFin).toUpperCase();
										if (!bast.contains(" ") && bast.length() <= 21) {
											importarBastidorDto.setBastidor(bast);
											listaBastidoresImportar.add(importarBastidorDto);
										} else {
											mensajeError = "La Linea: " + ln + " contiene un bastidor con un formato incorrecto.";
											listaLineasErroneas.add(mensajeError);
										}
									} else {
										mensajeError = "La Linea: " + ln + " no contiene una fecha correcta.";
										listaLineasErroneas.add(mensajeError);
									}
								} else {
									mensajeError = "La Linea: " + ln + " no contiene un tipo de importación permitida.";
									listaLineasErroneas.add(mensajeError);
								}
							} else {
								mensajeError = "La Linea: " + ln + " no contiene un tipo de registro permitido.";
								listaLineasErroneas.add(mensajeError);
							}
						} else {
							mensajeError = "La Linea: " + ln + " tiene mas caracteres de los permitidos por linea.";
							listaLineasErroneas.add(mensajeError);
						}
					}
					if (listaBastidoresImportar.size() <= MAX_LINEAS_FICHERO_IMPORTAR) {
						resultado = new ResultBean();
						resultado.setError(false);
						resultado.addAttachment("listaBastidoresImportar", listaBastidoresImportar);
						resultado.addAttachment("tipoBastidor", tipoBastidor);
						resultado.addAttachment("listaLineasErroneas", listaLineasErroneas);
					} else {
						resultado = new ResultBean(true, "No se puede importar un fichero con un tamaño superior 5000 lineas.");
						resultado.addAttachment("listaBastidoresImportar", null);
					}
				}
			} else {
				resultado = new ResultBean(true, "El fichero que desea importar se encuentra vacio.");
				resultado.addAttachment("listaBastidoresImportar", null);
			}
		} catch (FileNotFoundException e) {
			log.error("Error a la hora de recuperar el fichero, error: ", e);
			resultado = new ResultBean(true, "El fichero que desea importar esta vacio o tiene un formato erroneo.");
			resultado.addAttachment("listaBastidoresImportar", listaBastidoresImportar);
		} catch (IOException e) {
			log.error("Error a la hora de recuperar el fichero, error: ", e);
			resultado = new ResultBean(true, "El fichero que desea importar esta vacio o tiene un formato erroneo.");
			resultado.addAttachment("listaBastidoresImportar", listaBastidoresImportar);
		} finally {
			try {
				if (inputAnterior != null) {
					inputAnterior.close();
				}
			} catch (IOException e) {
				log.error("Error a la hora de recuperar el fichero, error: ", e);
				resultado = new ResultBean(true, "El fichero que desea importar esta vacio o tiene un formato erroneo.");
				resultado.addAttachment("listaBastidoresImportar", listaBastidoresImportar);
			}
		}
		return resultado;
	}

	private void crearListasResultadosYEnviarCorreos(List<String> listaMensajes, ArrayList<BastidorErroneoBean> listaBastidoresErroneos, List<ImportarBastidorDto> listaBastidoresImportar,
			List<String> listaLineasErroneas, ArrayList<String> listaBastidoresExistentes, boolean existenDatosError, boolean esAlta, String tipoBastidor) throws OegamExcepcion {
		if (!listaBastidoresImportar.isEmpty() || (listaBastidoresErroneos != null && !listaBastidoresErroneos.isEmpty()) || (listaLineasErroneas != null && !listaLineasErroneas.isEmpty())
				|| (listaBastidoresExistentes != null && !listaBastidoresExistentes.isEmpty())) {
			enviarCorreoImportacion(listaBastidoresImportar, esAlta, listaBastidoresErroneos, listaLineasErroneas, listaBastidoresExistentes, tipoBastidor);
		}
	}

	// TODO ASG
	private void enviarCorreoImportacion(List<ImportarBastidorDto> listaBastidoresImportar, boolean esAlta, ArrayList<BastidorErroneoBean> listaBastidoresErroneos, List<String> listaLineasErroneas,
			ArrayList<String> listaBastidoresExistentes, String tipoBastidor) throws OegamExcepcion {
		String mensajeEmail = "";
		String asunto = "";
		if (esAlta) {
			asunto = asuntoCorreo_Alta;
		} else {
			asunto = asuntoCorreo_Baja;
		}

		if (listaBastidoresImportar != null && !listaBastidoresImportar.isEmpty()) {
			if (esAlta) {
				mensajeEmail = "Se han dado de alta los siguientes Bastidores:<br> ";
			} else {
				mensajeEmail = "Se han dado de baja los siguientes Bastidores:<br> ";
			}
			for (int e = 0; e < listaBastidoresImportar.size(); e++) {
				ImportarBastidorDto dto = listaBastidoresImportar.get(e);
				mensajeEmail += dto.getBastidor() + "<br>";
			}
		}

		if (listaBastidoresErroneos != null && !listaBastidoresErroneos.isEmpty()) {
			if (esAlta) {
				mensajeEmail += "<br> Los siguientes bastidores han concluido con error porque ya existian y no han podido darse de alta: <br>";
			} else {
				mensajeEmail += "<br> Los siguientes bastidores han concluido con error porque no existian y no se han podido dar de baja: <br>";
			}
			for (BastidorErroneoBean bastidorErroneo : listaBastidoresErroneos) {
				mensajeEmail += bastidorErroneo.getBastidor() + "<br> ";
			}
		}

		if (listaLineasErroneas != null && !listaLineasErroneas.isEmpty()) {
			mensajeEmail += "<br> El fichero contiene las siguientes lineas con errores de formato: <br>";
			for (String lineaErronea : listaLineasErroneas) {
				mensajeEmail += "  - " + lineaErronea + " <br>";
			}
		}

		if (listaBastidoresExistentes != null && !listaBastidoresExistentes.isEmpty()) {
			mensajeEmail += "<br> El fichero contiene los siguientes bastidores que ya existian en la aplicación y estan dados de baja, por lo que se ha procedido a su actualización automática: <br>";
			for (String bastidorExistente : listaBastidoresExistentes) {
				mensajeEmail += "  - " + bastidorExistente + " <br>";
			}
		}

		envioMail(mensajeEmail, asunto, tipoBastidor);
	}

	private void enviarCorreoImportacionError(String mensaje, String nombreFichero, String extension) throws OegamExcepcion {
		String asunto = asuntoCorreo_Error_Fichero;
		String mensajeEmail = "<br> El fichero " + nombreFichero + extension + " no se puede importar en la aplicación por el siguiente motivo: <br>  - " + mensaje + "<br>";

		envioMail(mensajeEmail, asunto, null);
	}

	private ResultBean eliminarBastidorImportado(DatosSensiblesBean datosSensiblesBean, BigDecimal idContrato, BigDecimal idUsuario, String origenCambio) throws OegamExcepcion {
		log.info("ServicioDatosSensiblesImpl eliminarBastidoresImportados Inicio");
		ResultBean resultBean = null;
		String sPropertieEncolar = gestorPropiedades.valorPropertie(encolarTipoBastidor);
		boolean esPosibleEncolar = false;
		if (sPropertieEncolar != null && sPropertieEncolar.equals("true")) {
			esPosibleEncolar = true;
		} else {
			if (TipoBastidor.VN.getValorEnum().equals(datosSensiblesBean.getTipoControl())) {
				esPosibleEncolar = true;
			}
		}
		DatosSensiblesBastidorVO datosSensiblesBastidorVO = setDatosSensiblesBastidorVO(datosSensiblesBean.getTextoAgrupacion(), datosSensiblesBean.getGrupo());
		List<DatosSensiblesBastidorVO> lista = datosSensiblesBastidorDao.getBastidorPorId(datosSensiblesBastidorVO);
		if (lista != null && !lista.isEmpty()) {
			String xmlEnviar = datosSensiblesBastidorVO.getId().getBastidor() + "_" + datosSensiblesBastidorVO.getId().getIdGrupo();
			datosSensiblesBastidorVO = lista.get(0);
			if (datosSensiblesBastidorVO.getEstado().equals(BigDecimal.ONE)) {
				datosSensiblesBastidorVO.setEstado(new BigDecimal(0));
				datosSensiblesBastidorVO.setFechaBaja(Calendar.getInstance().getTime());
				datosSensiblesBastidorDao.borradoLogico(datosSensiblesBastidorVO);
				evolucionDatosSensiblesBastidorDao.guardar(dameEvolucionDatosSensiblesBastidorVO(datosSensiblesBastidorVO, "BORRADO LOGICO", idUsuario, origenCambio));
				if (esPosibleEncolar) {
					resultBean = crearSolicitudesProcesos(xmlEnviar, idContrato, idUsuario);
				}
			} else {
				String mensaje = "El Bastidor " + datosSensiblesBastidorVO.getId().getBastidor() + " del tipo " + TipoBastidor.convertirTexto(datosSensiblesBastidorVO.getTipoBastidor().toString())
						+ " ya se encuentra dado de baja para el grupo " + datosSensiblesBastidorVO.getGrupo().getDescGrupo() + " con fecha de baja del " + utilesFecha.formatoFecha("dd/MM/yyyy",
								datosSensiblesBastidorVO.getFechaBaja());
				resultBean = new ResultBean(false, mensaje);
			}
		} else {
			String mensaje = "El Bastidor " + datosSensiblesBastidorVO.getId().getBastidor() + " del tipo " + TipoBastidor.convertirTexto(datosSensiblesBean.getTipoControl())
					+ " no se encuentra dado de alta para el grupo " + servicioGrupo.getDescripcionGrupo(datosSensiblesBean.getGrupo());
			resultBean = new ResultBean(true, mensaje);
		}
		return resultBean;

	}

	private ResultBean crearSolicitudesProcesos(String xmlEnviar, BigDecimal idContrato, BigDecimal idUsuario) throws OegamExcepcion {
		ResultBean resultBean = null;
		ArrayList<String> listaErrorres = new ArrayList<String>();

		Log.info("crearSolicitudesProcesos" + " idContrato " + idContrato + " idUsuario " + idUsuario);

		ResultBean resultadoProcesoA9 = servicioCola.crearSolicitud(ConstantesProcesos.PROCESO_SEA_ENVIO_DS, xmlEnviar, getNodoSolicitud(), "N/A", null, idUsuario, null, idContrato);

		if (resultadoProcesoA9 != null && resultadoProcesoA9.getError() && resultadoProcesoA9.getListaMensajes() != null && !resultadoProcesoA9.getListaMensajes().isEmpty()) {
			listaErrorres.add(resultadoProcesoA9.getListaMensajes().get(0));
		}

		if (!listaErrorres.isEmpty()) {
			resultBean = new ResultBean();
			resultBean.setError(true);
			resultBean.setListaMensajes(listaErrorres);
		}

		return resultBean;
	}

	private DatosSensiblesBastidorVO setDatosSensiblesBastidorVO(String bastidor, String grupo) {
		DatosSensiblesBastidorVO datosSensiblesBastidorVO = new DatosSensiblesBastidorVO();
		DatosSensiblesBastidorPK id = new DatosSensiblesBastidorPK();
		id.setBastidor(bastidor);
		id.setIdGrupo(grupo);
		datosSensiblesBastidorVO.setId(id);
		return datosSensiblesBastidorVO;
	}

	@SuppressWarnings({ "deprecation", "unused" })
	private ResultBean getListaBastidoresImportarExcel(FicheroBean fichero) {
		List<ImportarBastidorDto> listaBastidoresImportar = null;
		ResultBean resultado = null;
		String tipoBastidor = null;
		List<String> listaLineasErroneas = new ArrayList<String>();
		String mensajeError = null;
		try {
			@SuppressWarnings("resource")
			HSSFWorkbook workbook = new HSSFWorkbook(fichero.getInputStreamFromBytesOrFile());
			if (workbook != null) {
				int numHojas = workbook.getNumberOfSheets();
				HSSFSheet sheet = null;
				boolean errorHojasVacias = true;
				listaBastidoresImportar = new ArrayList<ImportarBastidorDto>();
				for (int i = 0; i < numHojas; i++) {
					sheet = workbook.getSheetAt(i);
					Iterator<Row> rows = sheet.rowIterator();
					int numTotalRow = sheet.getLastRowNum();
					if (numTotalRow < MAX_LINEAS_FICHERO_IMPORTAR) {
						int iFila = 0;
						ImportarBastidorDto importarBastidorDto = null;
						boolean error = false;
						while (rows.hasNext()) {
							HSSFRow row = (HSSFRow) rows.next();
							error = false;
							if (iFila != 0) {
								errorHojasVacias = false;
								importarBastidorDto = new ImportarBastidorDto();
								for (short e = row.getFirstCellNum(); e < row.getLastCellNum(); e++) {
									HSSFCell hssfCell = row.getCell(e);
									if (hssfCell != null) {
										switch ((int) e) {
											case 0:
												String tipoRegistro = hssfCell.getStringCellValue().toUpperCase();
												if (tipoRegistro != null && TipoImportacionBastidores.Alta.getValorEnum().equals(tipoRegistro) || TipoImportacionBastidores.Baja.getValorEnum().equals(
														tipoRegistro)) {
													importarBastidorDto.setTipoRegistro(tipoRegistro);
												} else {
													mensajeError = "La fila: " + (iFila + 1) + " del fichero excel no contiene un tipo de registro permitido.";
													listaLineasErroneas.add(mensajeError);
													error = true;
												}
												break;
											case 1:
												String tipoImportado = hssfCell.getStringCellValue().toUpperCase();
												if (tipoImportado != null && TipoBastidor.VN.getNombreEnum().equals(tipoImportado) || TipoBastidor.VO.getNombreEnum().equals(tipoImportado)
														|| TipoBastidor.DM.getNombreEnum().equals(tipoImportado)) {
													if (tipoImportado != null && !tipoImportado.isEmpty()) {
														importarBastidorDto.setTipoImportado(TipoBastidor.getValorEnumPorPorNombreEnum(tipoImportado));
														if (tipoBastidor == null) {
															tipoBastidor = importarBastidorDto.getTipoImportado();
														}
													}
												} else {
													mensajeError = "La fila: " + (iFila + 1) + " del fichero excel no contiene un tipo de importación permitida.";
													listaLineasErroneas.add(mensajeError);
													error = true;
												}
												break;
											case 2:
												hssfCell.setCellType(Cell.CELL_TYPE_STRING);
												String fechaEnv = hssfCell.getStringCellValue();
												if (fechaEnv != null && utilesFecha.comprobarFecha(fechaEnv)) {
													importarBastidorDto.setFechaEnvio(fechaEnv);
												} else {
													mensajeError = "La fila: " + (iFila + 1) + " del fichero excel no contiene una fecha correcta.";
													listaLineasErroneas.add(mensajeError);
													error = true;
												}
												break;
											case 3:
												String bast = hssfCell.getStringCellValue().toUpperCase();
												if (bast != null && !bast.contains(" ") && bast.length() <= 21) {
													importarBastidorDto.setBastidor(bast);
												} else {
													mensajeError = "La fila: " + (iFila + 1) + " del fichero excel contiene un bastidor con un formato incorrecto.";
													listaLineasErroneas.add(mensajeError);
													error = true;
												}
												break;
											default:
												break;
										}
										if (error) {
											break;
										}
									}
								}
								if (!error) {
									listaBastidoresImportar.add(importarBastidorDto);
								}
							}
							iFila++;
						}
					} else {
						resultado = new ResultBean(true, "No se puede importar un fichero con un tamaño superior 5000 lineas.");
						resultado.addAttachment("listaBastidoresImportar", null);
					}
				}
				if (errorHojasVacias) {
					resultado = new ResultBean(true, "El fichero que desea importar se encuentra vacio.");
					resultado.addAttachment("listaBastidoresImportar", null);
				} else {
					resultado = new ResultBean();
					resultado.setError(false);
					resultado.addAttachment("listaBastidoresImportar", listaBastidoresImportar);
					resultado.addAttachment("tipoBastidor", tipoBastidor);
					resultado.addAttachment("listaLineasErroneas", listaLineasErroneas);
				}
			} else {
				resultado = new ResultBean(true, "El fichero que desea importar se encuentra vacio.");
				resultado.addAttachment("listaBastidoresImportar", null);
			}
		} catch (FileNotFoundException e) {
			log.error("Error a la hora de recuperar el fichero, error: ", e);
			resultado = new ResultBean(true, "El fichero que desea importar esta vacio o tiene un formato erroneo.");
			resultado.addAttachment("listaBastidoresImportar", null);
		} catch (IOException e) {
			log.error("Error a la hora de recuperar el fichero, error: ", e);
			resultado = new ResultBean(true, "El fichero que desea importar esta vacio o tiene un formato erroneo.");
			resultado.addAttachment("listaBastidoresImportar", null);
		} catch (Exception e) {
			log.error("Error a la hora de recuperar el fichero, error: ", e);
			resultado = new ResultBean(true, "El fichero que desea importar esta vacio o tiene un formato erroneo.");
			resultado.addAttachment("listaBastidoresImportar", null);
		}
		return resultado;
	}

	@Override
	public Boolean getFlagDisabled(DatosSensiblesBean datosSensiblesBean) {
		if (datosSensiblesBean.getTipoAgrupacion() != null && TiposDatosSensibles.Bastidor.getNombreEnum().equals(datosSensiblesBean.getTipoAgrupacion())) {
			return false;
		}
		return true;
	}

	@Override
	public ResultBean comprobarDatosSensibles(DatosSensiblesBean datosSensiblesBean) {
		ResultBean result = null;
		if (datosSensiblesBean == null) {
			result = new ResultBean(true, "Debe rellenar datos para poder realizar el alta.");
			return result;
		}
		if (datosSensiblesBean.getTipoAgrupacion() == null || datosSensiblesBean.getTipoAgrupacion().equals("-1")) {
			result = new ResultBean(true, "Debe seleccionar un tipo de dato para poder realizar el alta.");
			return result;
		} else {
			if (TiposDatosSensibles.Bastidor.getNombreEnum().equals(datosSensiblesBean.getTipoAgrupacion())) {
				if (datosSensiblesBean.getTipoControl() == null || datosSensiblesBean.getTipoControl().equals("-1")) {
					result = new ResultBean(true, "El tipo de Control es obligatorio para realizar el alta de bastidores.");
					return result;
				}
			}
		}

		if (datosSensiblesBean.getGrupo() == null || datosSensiblesBean.getGrupo().equals("")) {
			result = new ResultBean(true, "Debe rellenar el grupo del datos sensibles para poder realizar el alta.");
			return result;
		}
		if (datosSensiblesBean.getTextoAgrupacion() == null || datosSensiblesBean.getTextoAgrupacion().isEmpty()) {
			result = new ResultBean(true, "Debe rellenar el valor del dato para poder realizar el alta.");
			return result;
		}
		return result;
	}

	@Override
	public ResultBean generarYEnviarExcel(Map<String, List<ResultadoAltaBastidorBean>> resultadoEjecucionComleta, String proceso, List<FicheroBean> ficherosImportados, String recipent,
			String direccionesOcultas, String subject) throws OegamExcepcion {

		try {

			StringBuffer texto = new StringBuffer("<span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\">");
			texto.append("El " + proceso + " ha finalizado. " + "Estado del FTP Correcto.<br>" + "Se han encontrado los siguientes archivos:<br><ul>");

			for (String fileAux : resultadoEjecucionComleta.keySet()) {
				texto.append("<li>").append(fileAux);
				List<ResultadoAltaBastidorBean> mensajes = resultadoEjecucionComleta.get(fileAux);
				// Mantis 21577. David Sierra: Procesado FTP datos sensibles
				// Se modifica el texto que se envia con el correo en caso de error en la importacion
				if (mensajes.get(0).getResultadoConError() && mensajes.get(0).getComentario() != null) {
					texto.append(" no ha sido procesado porque se ha producido el siguiente error en la importación: " + mensajes.get(0).getComentario());
				} else if (mensajes.get(0).getResultadoConError() && mensajes.get(0).getComentario() == null) {
					texto.append(" no ha sido procesado porque se ha producido un error en la importación.");
				} else if (mensajes != null && !mensajes.isEmpty() && mensajes.get(0).getComentario() != null && mensajes.get(0).getComentario().contains("vacio")) {
					texto.append(" no contiene bastidores");
				} else {
					texto.append(" contiene ").append(resultadoEjecucionComleta.get(fileAux).size() + " bastidores");
				}

				texto.append("</li>");
			}

			texto.append("</ul><br><br>Informe con resultados adjunto.<br><br></span>");

			/* Generamos el excel */
			FicheroBean ficheroAdjunto = generarExcel(resultadoEjecucionComleta);
			ficherosImportados.add(ficheroAdjunto);

			FicheroBean[] ficherosArray = ficherosImportados.toArray(new FicheroBean[ficherosImportados.size()]);
			ResultBean resultado;

			resultado = servicioCorreo.enviarCorreo(texto.toString(), null, null, subject, recipent, null, direccionesOcultas, null, ficherosArray);

			// Elimino el excel de la lista que ya se custodia en su metodo
			ficherosImportados.remove(ficherosImportados.size() - 1);

			if (resultado.getError()) {
				log.error("No se ha enviado el correo con el informe de la importacion de datos sensibles: " + resultado.getMensaje());
			}
		} catch (OegamExcepcion | IOException e) {
			log.error("No se ha enviado el correo con el informe de la importacion de datos sensibles", e);
		}

		return null;
	}

	@Override
	public void enviarCorreoNoSeEncontraronFicherosFTP(String proceso, String tipo, String recipent, String direccionesOcultas) {
		String subject = null;

		try {
			subject = "Estado FTP Importacion Datos Sensibles";

			String entorno = gestorPropiedades.valorPropertie("Entorno");
			if (!"PRODUCCION".equals(entorno)) {
				subject = entorno + ": " + subject;
			}

			StringBuffer texto = new StringBuffer("<span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\">");
			texto.append("Estado del FTP Correcto.<br>" + proceso + ", ha finalizado <strong>pero no se han encontrado archivos para importar de tipo " + tipo + ".</strong><br>");

			ResultBean resultado;
			resultado = servicioCorreo.enviarCorreo(texto.toString(), null, null, subject, recipent, null, direccionesOcultas, null);

			if (resultado.getError()) {
				log.error("No se ha enviado el correo con el estado del FTP de datos sensibles " + resultado.getMensaje());
			}
		} catch (OegamExcepcion | IOException e) {
			log.error("No se ha enviado el correo con el estado del FTP de datos sensibles ", e);
		}

	}

	@Override
	public void enviarCorreoError(String error, String proceso, String recipent, String direccionesOcultas) {
		String subject = null;

		try {
			subject = "Error FTP Importacion Datos Sensibles " + "- " + proceso;

			String entorno = gestorPropiedades.valorPropertie("Entorno");
			if (!"PRODUCCION".equals(entorno)) {
				subject = entorno + ": " + subject;
			}

			StringBuffer texto = new StringBuffer("<span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\">");
			texto.append("Estado del FTP Erroneo.<br> El " + proceso + " no ha podido conectar al FTP de datos sensibles: <br>");

			texto.append("<strong>" + error + "</strong><br><br>");

			ResultBean resultado;
			resultado = servicioCorreo.enviarCorreo(texto.toString(), null, null, subject, recipent, null, direccionesOcultas, null);

			if (resultado.getError()) {
				log.error("No se ha enviado el correo con el estado del FTP de datos sensibles " + resultado.getMensaje());
			}
		} catch (OegamExcepcion | IOException e) {
			log.error("No se ha enviado el correo con el estado del FTP de datos sensibles ", e);
		}

	}

	@Override
	public void enviarCorreoAvisoLiberacionBastidores(String proceso, String bastidor, String accion) {
		String subject = null;
		String recipent = null;
		// String direccionesOcultas = null;

		try {
			subject = gestorPropiedades.valorPropertie(asuntoProcesadoCorreoSantanderBastidores);

			StringBuffer texto = new StringBuffer("<span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\">");
			texto.append("El proceso " + proceso + " ha " + accion + " el siguiente bastidor: " + bastidor + "<br>");

			recipent = gestorPropiedades.valorPropertie(emailCorreoBastidoresDestinatario);
			// direccionesOcultas = gestorPropiedades.valorPropertie(emailInformeCCO);

			ResultBean resultado;
			resultado = servicioCorreo.enviarCorreo(texto.toString(), null, null, subject, recipent, null, null, null);

			if (resultado.getError()) {
				log.error("No se ha enviado el correo de aviso de liberacion de bastidores " + resultado.getMensaje());
			}
		} catch (OegamExcepcion | IOException e) {
			log.error("No se ha enviado el correo de aviso de liberacion de bastidores ", e);
		}

	}

	@Override
	public void enviarCorreoAvisoLiberacionNif(String proceso, String nif, String accion) {
		String subject = null;
		String recipent = null;
		// String direccionesOcultas = null;

		try {
			subject = gestorPropiedades.valorPropertie(asuntoProcesadoCorreoSantanderNif);

			StringBuffer texto = new StringBuffer("<span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\">");
			texto.append("El proceso " + proceso + " ha " + accion + " el siguiente nif: " + nif + "<br>");

			recipent = gestorPropiedades.valorPropertie(emailCorreoBastidoresDestinatario);
			// direccionesOcultas = gestorPropiedades.valorPropertie(emailInformeCCO);

			ResultBean resultado;
			resultado = servicioCorreo.enviarCorreo(texto.toString(), null, null, subject, recipent, null, null, null);

			if (resultado.getError()) {
				log.error("No se ha enviado el correo de aviso de liberacion de nif " + resultado.getMensaje());
			}
		} catch (OegamExcepcion | IOException e) {
			log.error("No se ha enviado el correo de aviso de liberacion de nif ", e);
		}

	}

	@Override
	public void enviarCorreoAvisoLiberacionMatricula(String proceso, String bastidor, String accion) {
		String subject = null;
		String recipent = null;
		// String direccionesOcultas = null;

		try {
			subject = gestorPropiedades.valorPropertie(asuntoProcesadoCorreoSantanderMatricula);

			StringBuffer texto = new StringBuffer("<span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\">");
			texto.append("El proceso " + proceso + " ha " + accion + " la siguiente matrícula: " + bastidor + "<br>");

			recipent = gestorPropiedades.valorPropertie(emailCorreoBastidoresDestinatario);
			// direccionesOcultas = gestorPropiedades.valorPropertie(emailInformeCCO);

			ResultBean resultado;
			resultado = servicioCorreo.enviarCorreo(texto.toString(), null, null, subject, recipent, null, null, null);

			if (resultado.getError()) {
				log.error("No se ha enviado el correo de aviso de liberacion de matrícula " + resultado.getMensaje());
			}
		} catch (OegamExcepcion | IOException e) {
			log.error("No se ha enviado el correo de aviso de liberacion de matrícula ", e);
		}

	}

	private FicheroBean generarExcel(Map<String, List<ResultadoAltaBastidorBean>> resultadoEjecucionComleta) throws OegamExcepcion {
		String nombreFichero = "";
		File archivo = null;
		FicheroBean fichero = new FicheroBean();

		Fecha hora = utilesFecha.getFechaHoraActual();
		nombreFichero = "Informe " + hora.getAnio() + "_" + hora.getMes() + "_" + hora.getDia() + "_" + hora.getHora() + "_" + hora.getMinutos() + "_" + hora.getSegundos();

		// Inicializamos el ficheroBean
		fichero.setExtension(ConstantesGestorFicheros.EXTENSION_XLS);
		fichero.setNombreDocumento(nombreFichero);
		fichero.setTipoDocumento(ConstantesGestorFicheros.TIPO_DOC_IMPORTACION_DATOS_SENSIBLES);
		fichero.setSubTipo("INFORMES");
		fichero.setFecha(utilesFecha.getFechaActual());
		fichero.setSobreescribir(true);
		fichero.setFichero(new File(nombreFichero));

		archivo = gestorDocumentos.guardarFichero(fichero);

		WritableWorkbook copyWorkbook = null;
		WritableSheet sheet;

		try {
			CellView vistaCeldas = new CellView();
			vistaCeldas.setAutosize(true);

			copyWorkbook = Workbook.createWorkbook(archivo);
			sheet = copyWorkbook.createSheet("Hoja1", 0);

			for (int i = 0; i < 7; i++) {
				sheet.setColumnView(i, vistaCeldas);
			}

			WritableFont fuente = new WritableFont(WritableFont.createFont("MS Sans Serif"), 10, WritableFont.BOLD, false);
			fuente.setColour(Colour.BLACK);
			WritableCellFormat formato = new WritableCellFormat(fuente);

			vistaCeldas.setFormat(formato);
			for (int i = 0; i < 4; i++) {
				vistaCeldas.setAutosize(true);
				sheet.setColumnView(i, vistaCeldas);
			}

			// Generamos las cabeceras de la hoja Excel con el formato indicado

			// Formato de las celdas de cabecera
			WritableFont fuenteCabecera = new WritableFont(WritableFont.createFont("MS Sans Serif"), 10, WritableFont.BOLD, false);
			fuenteCabecera.setColour(Colour.BLACK);
			WritableCellFormat formatoCabecera = new WritableCellFormat(fuenteCabecera);

			formatoCabecera.setBackground(Colour.PALE_BLUE);
			formatoCabecera.setAlignment(Alignment.CENTRE);
			formatoCabecera.setBorder(Border.ALL, BorderLineStyle.THIN);

			// Formato de las celdas de datos
			WritableFont fuenteDatos = new WritableFont(WritableFont.createFont("MS Sans Serif"), 10, WritableFont.NO_BOLD, false);
			fuenteDatos.setColour(Colour.BLACK);
			WritableCellFormat formatoDatos = new WritableCellFormat(fuenteDatos);

			formatoDatos.setAlignment(Alignment.CENTRE);

			// Formato de las celdas de datos
			WritableCellFormat formatoDatosIzquierda = new WritableCellFormat(fuenteDatos);

			formatoDatosIzquierda.setAlignment(Alignment.LEFT);

			Label label = null;

			try {

				label = new Label(0, 0, "Fichero", formatoCabecera);
				sheet.addCell(label);
				label = new Label(1, 0, "Bastidor", formatoCabecera);
				sheet.addCell(label);
				label = new Label(2, 0, "Operación", formatoCabecera);
				sheet.addCell(label);
				label = new Label(3, 0, "Resultado", formatoCabecera);
				sheet.addCell(label);
				label = new Label(4, 0, "Comentario", formatoCabecera);
				sheet.addCell(label);

				int i = 1;
				for (Map.Entry<String, List<ResultadoAltaBastidorBean>> entryAux : resultadoEjecucionComleta.entrySet()) {
					for (ResultadoAltaBastidorBean aux : entryAux.getValue()) {

						label = new Label(0, i, entryAux.getKey(), formatoDatosIzquierda);
						sheet.addCell(label);

						label = new Label(1, i, aux.getBastidor(), formatoDatos);
						sheet.addCell(label);

						label = new Label(2, i, aux.getOperacion(), formatoDatos);
						sheet.addCell(label);

						label = new Label(3, i, aux.getErrorFormateado(), formatoDatos);
						sheet.addCell(label);

						label = new Label(4, i, aux.getComentario(), formatoDatosIzquierda);
						sheet.addCell(label);
						i++;
					}
					i++; // Dejo una fila en blanco para separar
				}

			} catch (RowsExceededException e) {
				log.error("Error al obtener las estadisticas agrupadas para excel.", e);
			} catch (WriteException e) {
				log.error("Error al obtener las estadisticas agrupadas para excel.", e);
			}

			copyWorkbook.write();

		} catch (IOException e) {
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		}

		try {
			copyWorkbook.close();
		} catch (WriteException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		}

		FicheroBean ficheroAdjunto = new FicheroBean();
		ficheroAdjunto.setFichero(archivo);
		ficheroAdjunto.setNombreDocumento(nombreFichero + ".xls");

		return ficheroAdjunto;
	}

	@Override
	@Transactional
	public ResultadoDatosSensibles importarBastidor(DatosSensiblesBean datosSensiblesBean, FicheroBean fichero, BigDecimal idContrato, String numColegiado, BigDecimal idUsuario,
			String apellidosNombre, String origenCambio) throws OegamExcepcion {
		return importarBastidor(datosSensiblesBean, fichero, idContrato, numColegiado, idUsuario, apellidosNombre, new ArrayList<ResultadoAltaBastidorBean>(), origenCambio);
	}

	@Override
	public String[] getDatosUsuarios(String grupo, BigDecimal idContrato, String num_colegiado, BigDecimal id_usuario, String apellidos_nombre, Boolean esAdmin) {
		String[] datosUsuario = new String[4];
		if (esAdmin && "SAN".equals(grupo)) {
			datosUsuario[0] = gestorPropiedades.valorPropertie(PROPERTY_KEY_DATOSSENSIBLES_ID_CONTRATO);
			datosUsuario[1] = gestorPropiedades.valorPropertie(PROPERTY_KEY_DATOSSENSIBLES_NUM_COLEGIADO);
			datosUsuario[2] = gestorPropiedades.valorPropertie(PROPERTY_KEY_DATOSSENSIBLES_ID_USUARIO);
			datosUsuario[3] = gestorPropiedades.valorPropertie(PROPERTY_KEY_DATOSSENSIBLES_APELLIDOS_NOMBRE);
		} else {
			datosUsuario[0] = idContrato.toString();
			datosUsuario[1] = num_colegiado;
			datosUsuario[2] = id_usuario.toString();
			datosUsuario[3] = apellidos_nombre;
		}
		return datosUsuario;
	}

	@Override
	@Transactional
	public ResultadoDatosSensibles actualizarBastidorExistente(DatosSensiblesBean datosSensiblesBean, BigDecimal idUsuario, String origenCambio) throws OegamExcepcion {
		ResultadoDatosSensibles resultado = null;
		List<DatosSensiblesBastidorVO> listaBastidores = datosSensiblesBastidorDao.existeBastidor(datosSensiblesBean.getTextoAgrupacion(), datosSensiblesBean.getGrupo());
		if (listaBastidores != null && !listaBastidores.isEmpty()) {
			DatosSensiblesBastidorVO bastidorVO = listaBastidores.get(0);
			if (bastidorVO.getEstado().equals(BigDecimal.ZERO)) {
				bastidorVO.setEstado(BigDecimal.ONE);
				bastidorVO.setFechaBaja(null);
				bastidorVO.setFechaAlta(Calendar.getInstance().getTime());
				try {
					datosSensiblesBastidorDao.actualizar(bastidorVO);
					evolucionDatosSensiblesBastidorDao.guardar(dameEvolucionDatosSensiblesBastidorVO(bastidorVO, "ACTUALIZAR", idUsuario, origenCambio));
					resultado = new ResultadoDatosSensibles(true, false, false, "El bastidor " + bastidorVO.getId().getBastidor() + " se ha actualizado correctamente.");
				} catch (Exception e) {
					log.error(e);
					throw new OegamExcepcion(EnumError.error_00001);
				}
			} else {
				resultado = new ResultadoDatosSensibles(true, true, false, "El bastidor que desea actualizar no se encuentra en un estado valido para actualizar.");
			}
		} else {
			resultado = new ResultadoDatosSensibles(true, true, false, "Ha sucedido un error a la hora de actualizar el bastidor.");
			log.error("Error a la hora de actualizar un bastidor existente, no existen datos de ese bastidor para ctualizar.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoDatosSensibles enviarPeticion(List<DatosSensiblesBastidorDto> listaBastidoresDto, BigDecimal idContrato, BigDecimal idUsuario) {
		ResultadoDatosSensibles resultado = null;
		List<String> listaMensajes = new ArrayList<>();
		for (DatosSensiblesBastidorDto bastidorDto : listaBastidoresDto) {
			String xmlEnviar = bastidorDto.getBastidor() + "_" + bastidorDto.getIdGrupo();
			try {
				ResultBean resultBean = crearSolicitudesProcesos(xmlEnviar, idContrato, idUsuario);
				if (resultBean != null && resultBean.getError()) {
					for (String mensaje : resultBean.getListaMensajes()) {
						listaMensajes.add(mensaje);
					}
				}
			} catch (OegamExcepcion e) {
				log.error(e);
				listaMensajes.add("Ha surgido un error a la hora de encolar la solicitud al proceso para el bastidor: " + bastidorDto.getBastidor());
			}
		}
		if (listaMensajes != null && !listaMensajes.isEmpty()) {
			resultado = new ResultadoDatosSensibles(true, true);
			resultado.setListaMensajes(listaMensajes);
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public List<DatosSensiblesBastidorVO> buscarPorBastidor(String bastidor) {
		return datosSensiblesBastidorDao.buscarPorBastidor(bastidor);
	}

	@Override
	@Transactional(readOnly = true)
	public List<DatosSensiblesMatriculaVO> buscarPorMatricula(String matricula) {
		return datosSensiblesMatriculaDao.buscarPorMatricula(matricula);
	}

	@Override
	@Transactional(readOnly = true)
	public List<DatosSensiblesNifVO> buscarPorNif(String dni) {
		return datosSensiblesNifDao.buscarporNif(dni);
	}

	// Mantis 20495. David Sierra: Comprobacion datos sensibles del grupo Santander con control de horas
	private boolean isFranjaHorariaAutoliberacionActiva(String idGrupo, DatosSensiblesMatriculaVO datosMatriculaVO, DatosSensiblesBastidorVO datosBastidorVO, DatosSensiblesNifVO datosNifVO,
			BigDecimal idUsuario) throws OegamExcepcion {
		boolean activa = false;
		String grupo = idGrupo;
		if ("SI".equals(gestorPropiedades.valorPropertie(COMPROBAR_DATOS_SENSIBLES + grupo.toLowerCase()))) {
			String patron = gestorPropiedades.valorPropertie(COMPROBAR_DATOS_SENSIBLES_PATRON + grupo.toLowerCase());
			if (patron != null) {
				Fecha fechaActual = utilesFecha.getFechaHoraActualLEG();
				Calendar calendario = Calendar.getInstance();
				int diaSemanaNum;
				String diaSemana = null;
				try {
					calendario.setTime(fechaActual.getDate());
					diaSemanaNum = calendario.get(Calendar.DAY_OF_WEEK);
					String[] dias = { "D", "L", "M", "X", "J", "V", "S" };
					diaSemana = dias[diaSemanaNum - 1];
				} catch (ParseException e) {
					log.error(e);
				}
				for (String franja : patron.split(",")) {
					if (franja != null && diaSemana != null && franja.startsWith(diaSemana)) {
						int hora = Integer.parseInt(fechaActual.getHora());
						int horaini = Integer.parseInt(franja.substring(1, 3));
						int horafin = Integer.parseInt(franja.substring(4, 6));
						if (horaini <= hora && horafin >= hora) {
							activa = true;
							if (datosMatriculaVO != null) {
								String indices = datosMatriculaVO.getId().getMatricula();
								indices = indices.concat(",");
								indices = indices.concat(grupo);
								DatosSensiblesBean datosSensiblesBean = new DatosSensiblesBean();
								ResultBean resultado = eliminarMatricula(indices, datosSensiblesBean, idUsuario);
								if (resultado == null) {
									enviarCorreoAvisoLiberacionSantander(datosMatriculaVO.getId().getMatricula(), "matricula");
								}
							} else if (datosBastidorVO != null) {
								String indices = datosBastidorVO.getId().getBastidor();
								indices = indices.concat(",");
								indices = indices.concat(grupo);
								DatosSensiblesBean datosSensiblesBean = new DatosSensiblesBean();
								ResultBean resultado = eliminarBastidores(indices, datosSensiblesBean, new BigDecimal(gestorPropiedades.valorPropertie(
										ServicioDatosSensibles.PROPERTY_KEY_DATOSSENSIBLES_ID_CONTRATO)), new BigDecimal(gestorPropiedades.valorPropertie(
												ServicioDatosSensibles.PROPERTY_KEY_DATOSSENSIBLES_ID_USUARIO)), "APLICACION");
								if (resultado == null) {
									enviarCorreoAvisoLiberacionSantander(datosBastidorVO.getId().getBastidor(), "bastidor");
								}
							} else if (datosNifVO != null) {
								String indices = datosNifVO.getId().getNif();
								indices = indices.concat(",");
								indices = indices.concat(grupo);
								DatosSensiblesBean datosSensiblesBean = new DatosSensiblesBean();
								ResultBean resultado = eliminarNif(indices, datosSensiblesBean, idUsuario, "APLICACION");
								if (resultado == null) {
									enviarCorreoAvisoLiberacionSantander(datosNifVO.getId().getNif(), "nif");
								}
							}
						}
					}

				}
			}
		}
		return activa;
	}

	private void enviarCorreoAvisoLiberacionSantander(String datoSensible, String elemento) {
		String subject = null;
		String recipent = null;

		try {
			subject = gestorPropiedades.valorPropertie(ASUNTO_LIBERACION_DATO_SENSIBLE);
			String articulo = "";
			if ("matricula".equals(elemento)) {
				articulo = "La";
			} else {
				articulo = "El";
			}
			StringBuffer texto = new StringBuffer("<span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\">");
			texto.append(articulo + " " + elemento + " " + datoSensible + " ha sido liberado fuera del horario previsto" + "<br>");

			recipent = gestorPropiedades.valorPropertie(EMAIL_DESTINATARIO_LIBERACION_DATO_SENSIBLE);

			ResultBean resultado;
			resultado = servicioCorreo.enviarCorreo(texto.toString(), null, null, subject, recipent, null, null, null);

			if (resultado.getError()) {
				log.error("No se ha enviado el correo de aviso de liberacion de bastidores " + resultado.getMensaje());
			}
		} catch (OegamExcepcion | IOException e) {
			log.error("No se ha enviado el correo de aviso de liberacion de bastidores ", e);
		}

	}

	@Override
	@Transactional
	public ResultBean activarDatosSensible(String indices, DatosSensiblesBean datosSensiblesBean, BigDecimal idContrato, BigDecimal idUsuario) throws OegamExcepcion {
		ResultBean resultBean = null;
		try {
			if (TiposDatosSensibles.Bastidor.toString().equals(datosSensiblesBean.getTipoAgrupacion())) {
				resultBean = activarBastidor(indices, datosSensiblesBean, idContrato, idUsuario);
				if (!resultBean.getError()) {
					indices = indices.replace("-", ", ");
					envioMail("Se ha activado el siguiente bastidor: " + (String) resultBean.getAttachment("bastidoresAct") + "<br>", asuntoCorreo_Alta, TipoBastidor.convertirTexto(datosSensiblesBean
							.getTipoControl()));
				}
			} else if (TiposDatosSensibles.Matricula.toString().equals(datosSensiblesBean.getTipoAgrupacion())) {
				resultBean = activarMatricula(indices, datosSensiblesBean, idUsuario);
				if (resultBean == null) {
					indices = indices.replace("-", ", ");
					envioMail("Se ha activado la siguiente Matricula: " + datosSensiblesBean.getTextoAgrupacion() + "<br>", asuntoCorreo_Alta, null);
				}
			} else if (TiposDatosSensibles.Nif.toString().equals(datosSensiblesBean.getTipoAgrupacion())) {
				resultBean = activarNif(indices, datosSensiblesBean, idUsuario);
				if (resultBean == null) {
					indices = indices.replace("-", ", ");
					envioMail("Se ha activado el siguiente NIF: " + indices + "<br>", asuntoCorreo_Alta, null);
				}
			}
			if (resultBean != null && resultBean.getError()) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new OegamExcepcion(e.getMessage());
		}
		return resultBean;
	}

	protected ResultBean activarBastidor(String indices, DatosSensiblesBean datosSensiblesBean, BigDecimal idContrato, BigDecimal idUsuario) throws OegamExcepcion {
		log.info("ServicioDatosSensiblesImpl activarBastidor Inicio");
		ResultBean resultBean = new ResultBean(Boolean.FALSE);
		String bastidoresActivados = "";
		String sPropertieEncolar = gestorPropiedades.valorPropertie(encolarTipoBastidor);
		boolean esPosibleEncolar = false;
		if (sPropertieEncolar != null && sPropertieEncolar.equals("true")) {
			esPosibleEncolar = true;
		} else {
			if (TipoBastidor.VN.getValorEnum().equals(datosSensiblesBean.getTipoControl())) {
				esPosibleEncolar = true;
			}
		}
		String[] codSeleccionados = indices.split("-");
		for (int i = 0; i < codSeleccionados.length; i++) {
			String[] valores = codSeleccionados[i].split(",");
			DatosSensiblesBastidorVO datosSensiblesBastidorVO = setDatosSensiblesBastidorVO(valores[0], valores[1]);
			List<DatosSensiblesBastidorVO> lista = datosSensiblesBastidorDao.getBastidorPorId(datosSensiblesBastidorVO);
			if (lista != null && !lista.isEmpty()) {
				datosSensiblesBastidorVO = lista.get(0);
				datosSensiblesBastidorVO.setEstado(new BigDecimal(1));
				datosSensiblesBastidorVO.setFechaBaja(null);
				String xmlEnviar = valores[0] + "_" + datosSensiblesBastidorVO.getGrupo().getIdGrupo();
				datosSensiblesBastidorDao.actualizar(datosSensiblesBastidorVO);
				evolucionDatosSensiblesBastidorDao.guardar(dameEvolucionDatosSensiblesBastidorVO(datosSensiblesBastidorVO, "ACTIVAR", idUsuario, "APLICACION"));
				if (esPosibleEncolar) {
					resultBean = crearSolicitudesProcesos(xmlEnviar, idContrato, idUsuario);
					if (resultBean != null && resultBean.getError()) {
						break;
					}
				}
				if (!bastidoresActivados.isEmpty()) {
					bastidoresActivados += ", ";
				}
				bastidoresActivados += datosSensiblesBastidorVO.getId().getBastidor();
			} else {
				resultBean.setError(Boolean.TRUE);
				resultBean.setMensaje("No existen datos para activar el siguiente Bastidor: " + datosSensiblesBastidorVO.getId().getBastidor());
				break;
			}
		}
		if (resultBean == null) {
			resultBean = new ResultBean(Boolean.FALSE);
			resultBean.addAttachment("bastidoresAct", bastidoresActivados);
		} else if (!resultBean.getError()) {
			resultBean.addAttachment("bastidoresAct", bastidoresActivados);
		}
		return resultBean;
	}

	private ResultBean activarNif(String indices, DatosSensiblesBean datosSensiblesBean, BigDecimal idUsuario) {
		log.info("ServicioDatosSensiblesImpl activarNif Inicio");
		ResultBean result = null;
		String[] codSeleccionados = indices.split("-");
		try {
			for (int i = 0; i < codSeleccionados.length; i++) {
				String[] valores = codSeleccionados[i].split(",");

				DatosSensiblesNifVO datosSensiblesNifVO = setDatosSensiblesNifVo(valores[0], valores[1]);

				List<DatosSensiblesNifVO> lista = datosSensiblesNifDao.getNifPorId(datosSensiblesNifVO);

				if (lista != null && !lista.isEmpty()) {
					datosSensiblesNifVO = lista.get(0);
					datosSensiblesNifVO.setEstado(new BigDecimal(1));
					datosSensiblesNifVO.setFechaBaja(null);

					datosSensiblesNifDao.actualizar(datosSensiblesNifVO);
					evoluciondatosSensiblesNifDao.guardar(dameEvolucionDatosSensiblesNifVO(datosSensiblesNifVO, "ACTIVAR", idUsuario, "APLICACION"));
				} else {
					result = new ResultBean(true, "No existen datos para activar el siguiente Nif: " + datosSensiblesNifVO.getId().getNif());
					break;
				}
			}
		} catch (Exception e) {
			log.error("Ha surgido un error a la hora de activar los nif, error: ", e);
			result = new ResultBean(true, "Ha surgido un error a la hora de activars los nif");
		}
		return result;
	}

	protected ResultBean activarMatricula(String indices, DatosSensiblesBean datosSensiblesBean, BigDecimal idUsuario) {
		log.info("ServicioDatosSensiblesImpl activarMatriculas Inicio");
		String[] codSeleccionados = indices.split("-");
		ResultBean resultBean = null;
		for (int i = 0; i < codSeleccionados.length; i++) {
			String[] valores = codSeleccionados[i].split(",");

			DatosSensiblesMatriculaVO datosSensiblesMatriculaVO = setDatosSensiblesMatriculaVo(valores[0], valores[1]);

			List<DatosSensiblesMatriculaVO> lista = datosSensiblesMatriculaDao.getMatriculaPorId(datosSensiblesMatriculaVO);

			if (lista != null && !lista.isEmpty()) {
				datosSensiblesMatriculaVO = lista.get(0);
				datosSensiblesMatriculaVO.setEstado(new BigDecimal(1));
				datosSensiblesMatriculaVO.setFechaBaja(null);

				datosSensiblesMatriculaDao.actualizar(datosSensiblesMatriculaVO);
				evoluciondatosSensiblesMatriculaDao.guardar(dameEvolucionDatosSensiblesMatriculaVO(datosSensiblesMatriculaVO, "ACTIVAR", idUsuario, "APLICACION"));
			} else {
				resultBean = new ResultBean(true, "No existen datos para activar la siguiente Matricula: " + datosSensiblesMatriculaVO.getId().getMatricula());
				break;
			}
		}
		return resultBean;
	}

	@Override
	@Transactional
	public ResultBean eliminarNifes(String indices, DatosSensiblesBean datosSensiblesBean, BigDecimal idContrato, BigDecimal idUsuario) throws OegamExcepcion {
		log.info("Eliminar Nifes");
		ResultBean result = null;
		try {

			result = eliminarNif(indices, datosSensiblesBean, idContrato, idUsuario, "SANTANDER");
			if (result == null) {
				log.info("Enviar 1º correo de eliminar Nifes");
				envioMail("Se ha dado de baja el siguiente nif: " + indices.substring(0, indices.indexOf(",")) + "<br>", asuntoCorreo_Baja, null);
			}
		} catch (Exception e) {
			throw new OegamExcepcion(e.getMessage());
		}
		return result;
	}

	@Override
	@Transactional
	public ResultBean eliminarMatriculas(String indices, DatosSensiblesBean datosSensiblesBean, BigDecimal idContrato, BigDecimal idUsuario, String origenCambio) throws OegamExcepcion {
		ResultBean result = null;
		log.info("Eliminar Matricula");
		try {
			result = eliminarMatricula(indices, datosSensiblesBean, idContrato, idUsuario, origenCambio);
			if (result == null) {
				log.info("Enviar 1º correo de eliminar Matriculas");
				envioMail("Se ha dado de baja la siguiente matrícula: " + indices.substring(0, indices.indexOf(",")) + "<br>", asuntoCorreo_Baja, null);
			}
		} catch (Exception e) {
			throw new OegamExcepcion(e.getMessage());
		}
		return result;
	}

	protected ResultBean eliminarMatricula(String indices, DatosSensiblesBean datosSensiblesBean, BigDecimal idContrato, BigDecimal idUsuario, String origenCambio) throws OegamExcepcion {
		log.info("ServicioDatosSensiblesImpl eliminarMatricula Inicio");
		ResultBean resultBean = null;
		String sPropertieEncolar = gestorPropiedades.valorPropertie(encolarTipoMatricula);
		boolean esPosibleEncolar = false;
		if (sPropertieEncolar != null && sPropertieEncolar.equals("true")) {
			esPosibleEncolar = true;
		}

		String[] codSeleccionados = indices.split("-");
		for (int i = 0; i < codSeleccionados.length; i++) {
			String[] valores = codSeleccionados[i].split(",");
			DatosSensiblesMatriculaVO datosSensiblesMatriculaVO = setDatosSensiblesMatriculaVo(valores[0], valores[1]);
			List<DatosSensiblesMatriculaVO> lista = datosSensiblesMatriculaDao.getMatriculaPorId(datosSensiblesMatriculaVO);
			if (lista != null && !lista.isEmpty()) {
				datosSensiblesMatriculaVO = lista.get(0);
				datosSensiblesMatriculaVO.setEstado(new BigDecimal(0));
				datosSensiblesMatriculaVO.setFechaBaja(Calendar.getInstance().getTime());
				String xmlEnviar = valores[0] + "_" + datosSensiblesMatriculaVO.getGrupo().getIdGrupo();
				log.info("Dentro de eliminar Matricula");
				datosSensiblesMatriculaDao.borradoLogico(datosSensiblesMatriculaVO);
				evoluciondatosSensiblesMatriculaDao.guardar(dameEvolucionDatosSensiblesMatriculaVO(datosSensiblesMatriculaVO, "BORRADO LOGICO", idUsuario, origenCambio));
				if (esPosibleEncolar) {
					resultBean = crearSolicitudesProcesos(xmlEnviar, idContrato, idUsuario);
				}
			} else {
				resultBean = new ResultBean(true, "No existen datos para borrar de la siguiente Matricula: " + datosSensiblesMatriculaVO.getId().getMatricula());
				break;
			}
		}
		return resultBean;
	}

	@Transactional
	public List<EvolucionDatosSensiblesBean> getEvolucionDatosSensibles(String tipo, String numero, String grupo) {
		log.info("ServicioDatosSensiblesImpl getEvolucionDatosSensibles");
		List<EvolucionDatosSensiblesBean> listaEvolucionDatosSensibles = new ArrayList<>();
		;
		if (tipo != null) {
			listaEvolucionDatosSensibles = new ArrayList<>();
			if (TiposDatosSensibles.Bastidor.toString().equals(tipo)) {
				log.info("ServicioDatosSensiblesImpl getEvolucionDatosSensibles bastidores");
				List<EvolucionDatosSensiblesBastidorVO> listaEvolucionBastidores = evolucionDatosSensiblesBastidorDao.getEvolucion(numero, grupo);
				if (listaEvolucionBastidores != null) {
					log.info("ServicioDatosSensiblesImpl getEvolucionDatosSensibles getEvolucion bastidores");
					for (EvolucionDatosSensiblesBastidorVO elemento : listaEvolucionBastidores) {
						listaEvolucionDatosSensibles.add(convertirEvolucionDatosSensiblesVOToEvolucionDatosSensiblesBeans(tipo, elemento));
					}
				}
			} else if (TiposDatosSensibles.Matricula.toString().equals(tipo)) {
				log.info("ServicioDatosSensiblesImpl getEvolucionDatosSensibles matricula");
				List<EvolucionDatosSensiblesMatriculaVO> listaMatriculas = evoluciondatosSensiblesMatriculaDao.getEvolucion(numero, grupo);
				if (listaMatriculas != null) {
					log.info("ServicioDatosSensiblesImpl getListaDatosSensibles existen matriculas");
					for (EvolucionDatosSensiblesMatriculaVO elemento : listaMatriculas) {
						listaEvolucionDatosSensibles.add(convertirEvolucionDatosSensiblesVOToEvolucionDatosSensiblesBeans(tipo, elemento));
					}
				}
			} else if (TiposDatosSensibles.Nif.toString().equals(tipo)) {
				log.info("ServicioDatosSensiblesImpl getEvolucionDatosSensibles nif");
				List<EvolucionDatosSensiblesNifVO> listaNif = evoluciondatosSensiblesNifDao.getEvolucion(numero, grupo);
				if (listaNif != null) {
					log.info("ServicioDatosSensiblesImpl getEvolucionDatosSensibles existen nif");
					for (EvolucionDatosSensiblesNifVO elemento : listaNif) {
						listaEvolucionDatosSensibles.add(convertirEvolucionDatosSensiblesVOToEvolucionDatosSensiblesBeans(tipo, elemento));
					}
				}
			}
		}
		return listaEvolucionDatosSensibles;
	}

	private EvolucionDatosSensiblesBean convertirEvolucionDatosSensiblesVOToEvolucionDatosSensiblesBeans(String tipo, Object objeto) {
		EvolucionDatosSensiblesBean resultado = new EvolucionDatosSensiblesBean();
		if (TiposDatosSensibles.Bastidor.toString().equals(tipo)) {
			resultado = conversor.transform((EvolucionDatosSensiblesBastidorVO) objeto, EvolucionDatosSensiblesBean.class);
		} else if (TiposDatosSensibles.Matricula.toString().equals(tipo)) {
			resultado = conversor.transform((EvolucionDatosSensiblesMatriculaVO) objeto, EvolucionDatosSensiblesBean.class);
		} else if (TiposDatosSensibles.Nif.toString().equals(tipo)) {
			resultado = conversor.transform((EvolucionDatosSensiblesNifVO) objeto, EvolucionDatosSensiblesBean.class);
		}
		String estadoAnterior = EstadoBastidor.convertir(resultado.getEstadoAnterior()) == null ? "" : EstadoBastidor.convertir(resultado.getEstadoAnterior()).getNombreEnum();
		resultado.setEstadoAnterior(estadoAnterior);
		resultado.setEstadoNuevo(EstadoBastidor.convertir(resultado.getEstadoNuevo()).getNombreEnum());
		return resultado;
	}

	private EvolucionDatosSensiblesBastidorVO dameEvolucionDatosSensiblesBastidorVO(DatosSensiblesBastidorVO origen, String tipoCambio, BigDecimal idUsuario, String origenCambio) {
		EvolucionDatosSensiblesBastidorVO resultado = new EvolucionDatosSensiblesBastidorVO();
		resultado.setBastidor(origen.getId().getBastidor());
		resultado.setEstadoAnterior(dameEstadoAnterior(origen.getId().getBastidor(), origen.getId().getIdGrupo(), TiposDatosSensibles.Bastidor));
		resultado.setEstadoNuevo(origen.getEstado());
		resultado.setFechaCambio(new Date());
		resultado.setIdGrupo(origen.getId().getIdGrupo());
		resultado.setIdUsuario(idUsuario);
		resultado.setTipoCambio(tipoCambio);
		resultado.setOrigen(origenCambio);
		return resultado;
	}

	private EvolucionDatosSensiblesMatriculaVO dameEvolucionDatosSensiblesMatriculaVO(DatosSensiblesMatriculaVO origen, String tipoCambio, BigDecimal idUsuario, String origenCambio) {
		EvolucionDatosSensiblesMatriculaVO resultado = new EvolucionDatosSensiblesMatriculaVO();
		resultado.setMatricula(origen.getId().getMatricula());
		resultado.setEstadoAnterior(dameEstadoAnterior(origen.getId().getMatricula(), origen.getId().getIdGrupo(), TiposDatosSensibles.Matricula));
		resultado.setEstadoNuevo(origen.getEstado());
		resultado.setFechaCambio(new Date());
		resultado.setIdGrupo(origen.getId().getIdGrupo());
		resultado.setIdUsuario(idUsuario);
		resultado.setTipoCambio(tipoCambio);
		resultado.setOrigen(origenCambio);
		return resultado;
	}

	private EvolucionDatosSensiblesNifVO dameEvolucionDatosSensiblesNifVO(DatosSensiblesNifVO origen, String tipoCambio, BigDecimal idUsuario, String origenCambio) {
		EvolucionDatosSensiblesNifVO resultado = new EvolucionDatosSensiblesNifVO();
		resultado.setNif(origen.getId().getNif());
		resultado.setEstadoAnterior(dameEstadoAnterior(origen.getId().getNif(), origen.getId().getIdGrupo(), TiposDatosSensibles.Nif));
		resultado.setEstadoNuevo(origen.getEstado());
		resultado.setFechaCambio(new Date());
		resultado.setIdGrupo(origen.getId().getIdGrupo());
		resultado.setIdUsuario(idUsuario);
		resultado.setTipoCambio(tipoCambio);
		resultado.setOrigen(origenCambio);
		return resultado;
	}

	private BigDecimal dameEstadoAnterior(String numero, String idGrupo, TiposDatosSensibles tipo) {
		if (TiposDatosSensibles.Bastidor.equals(tipo)) {
			List<EvolucionDatosSensiblesBastidorVO> lista = evolucionDatosSensiblesBastidorDao.getEvolucion(numero, idGrupo);
			if (lista != null && !lista.isEmpty()) {
				return lista.get(0).getEstadoNuevo();
			}
		} else if (TiposDatosSensibles.Matricula.equals(tipo)) {
			List<EvolucionDatosSensiblesMatriculaVO> lista = evoluciondatosSensiblesMatriculaDao.getEvolucion(numero, idGrupo);
			if (lista != null && !lista.isEmpty()) {
				return lista.get(0).getEstadoNuevo();
			}
		} else if (TiposDatosSensibles.Nif.equals(tipo)) {
			List<EvolucionDatosSensiblesNifVO> lista = evoluciondatosSensiblesNifDao.getEvolucion(numero, idGrupo);
			if (lista != null && !lista.isEmpty()) {
				return lista.get(0).getEstadoNuevo();
			}
		}
		return null;
	}

	@Override
	@Transactional
	public ResultBean altaBastidoresWS(String[] bastidores, String tipoOperacion, String tipoFinanciacion, String tipoAlerta) {
		ResultBean result = new ResultBean(Boolean.FALSE);
		List<String> listadoBastidoresErroneos = new ArrayList<>();
		try {
			DatosSensiblesBastidorVO bastidorVO = crearNuevoBastidor(tipoFinanciacion, tipoAlerta);
			for (String bastidor : bastidores) {
				DatosSensiblesBastidorPK id = bastidorVO.getId();
				id.setBastidor(bastidor);
				bastidorVO.setId(id);
				if ("B".equals(tipoOperacion)) {
					result = bajaWS(bastidor, bastidorVO);
					if (result != null && result.getError()) {
						listadoBastidoresErroneos.add(bastidor);
					}
				} else {
					result = altaWS(bastidor, bastidorVO);
				}
			}
		} catch (Exception e) {
			log.error("Error a la hora de dar de altas bastidores, error: ", e);
			result.setError(Boolean.TRUE);
			result.setMensaje("Error a la hora de dar de altas bastidores");
		}
		result.addAttachment("listadoBastidoresErroneos", listadoBastidoresErroneos);
		return result;
	}

	private ResultBean bajaWS(String bastidor, DatosSensiblesBastidorVO bastidorVO) {
		ResultBean result = new ResultBean(Boolean.FALSE);
		List<DatosSensiblesBastidorVO> listaBastidores = datosSensiblesBastidorDao.existeBastidor(bastidor, bastidorVO.getId().getIdGrupo());
		if (listaBastidores == null || listaBastidores.isEmpty()) {
			result.setError(Boolean.TRUE);
			result.setMensaje("Algunos bastidores no se encuentran en el sistema");
		} else {
			if (bastidorVO.getEstado().equals(BigDecimal.ONE)) {
				DatosSensiblesBastidorVO aux = listaBastidores.get(0);
				aux.setEstado(BigDecimal.ZERO);
				aux.setFechaBaja(Calendar.getInstance().getTime());
				datosSensiblesBastidorDao.borradoLogico(bastidorVO);
				evolucionDatosSensiblesBastidorDao.guardar(dameEvolucionDatosSensiblesBastidorVO(aux, "BORRADO LOGICO", bastidorVO.getIdUsuario(), "SANTANDER"));
			} else {
				result.setError(Boolean.TRUE);
				result.setMensaje("Algunos bastidores ya están dados de baja");
			}
		}
		return result;
	}

	private ResultBean altaWS(String bastidor, DatosSensiblesBastidorVO bastidorVO) {
		ResultBean result = new ResultBean(Boolean.FALSE);
		bastidorVO.setEstado(BigDecimal.ONE);
		bastidorVO.setFechaAlta(Calendar.getInstance().getTime());
		List<DatosSensiblesBastidorVO> listaBastidores = datosSensiblesBastidorDao.existeBastidor(bastidor, bastidorVO.getId().getIdGrupo());
		if (listaBastidores == null || listaBastidores.isEmpty()) {
			datosSensiblesBastidorDao.guardar(bastidorVO);
			evolucionDatosSensiblesBastidorDao.guardar(dameEvolucionDatosSensiblesBastidorVO(bastidorVO, "ALTA", bastidorVO.getIdUsuario(), "SANTANDER"));
		} else {
			DatosSensiblesBastidorVO aux = listaBastidores.get(0);
			aux.setEstado(BigDecimal.ONE);
			aux.setFechaBaja(null);
			aux.setFechaAlta(Calendar.getInstance().getTime());
			aux.setTipoAlerta(bastidorVO.getTipoAlerta());
			aux.setTipoBastidor(bastidorVO.getTipoBastidor());
			aux.setTipoBastidorSantander(bastidorVO.getTipoBastidorSantander());
			datosSensiblesBastidorDao.actualizar(aux);
			evolucionDatosSensiblesBastidorDao.guardar(dameEvolucionDatosSensiblesBastidorVO(aux, "MODIFICACION", bastidorVO.getIdUsuario(), "SANTANDER"));
		}
		return result;
	}

	private DatosSensiblesBastidorVO crearNuevoBastidor(String tipoFinanciacion, String tipoAlerta) {
		DatosSensiblesBastidorVO datosSensiblesBastidorVO = new DatosSensiblesBastidorVO();

		// BBDD tabla properties
		String numColegiado = gestorPropiedades.valorPropertie(ServicioDatosSensibles.PROPERTY_KEY_DATOSSENSIBLES_NUM_COLEGIADO);
		BigDecimal idUsuario = new BigDecimal(gestorPropiedades.valorPropertie(ServicioDatosSensibles.PROPERTY_KEY_DATOSSENSIBLES_ID_USUARIO));
		String apellidosNombre = gestorPropiedades.valorPropertie(ServicioDatosSensibles.PROPERTY_KEY_DATOSSENSIBLES_APELLIDOS_NOMBRE);
		String grupo = gestorPropiedades.valorPropertie("importacion.datossensibles.usuario.grupo");

		DatosSensiblesBastidorPK id = new DatosSensiblesBastidorPK();
		id.setIdGrupo(grupo);

		datosSensiblesBastidorVO.setNumColegiado(numColegiado);
		datosSensiblesBastidorVO.setIdUsuario(idUsuario);
		datosSensiblesBastidorVO.setApellidosNombre(apellidosNombre);

		datosSensiblesBastidorVO.setFechaAlta(Calendar.getInstance().getTime());
		datosSensiblesBastidorVO.setEstado(new BigDecimal(1));
		datosSensiblesBastidorVO.setTiempoRestauracion(Long.parseLong(TiempoBajaDatosSensibles.No_caduca.toString()));

		datosSensiblesBastidorVO.setTipoBastidor(new BigDecimal(TipoBastidor.getValorEnumPorPorNombreEnum(tipoFinanciacion)));

		datosSensiblesBastidorVO.setTipoAlerta(new BigDecimal(tipoAlerta));

		return datosSensiblesBastidorVO;
	}
}