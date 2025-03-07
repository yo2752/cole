package trafico.modelo;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.rpc.ServiceException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCreditoFacturado;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import es.oegam.atem.wsclient.util.ATEMClient;
import escrituras.beans.ResultBean;
import hibernate.dao.trafico.TramiteTrafSolInfoDAO;
import hibernate.entities.trafico.TramiteTrafSolInfo;
import hibernate.entities.trafico.TramiteTrafico;
import oegam.constantes.ConstantesPQ;
import trafico.beans.RespuestaAtemBean;
import trafico.beans.daos.BeanPQCambiarEstadoTramite;
import trafico.utiles.constantes.Constantes;
import trafico.utiles.enumerados.ResultadoAvpo;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.DescontarCreditosExcepcion;

public class ModeloAtem{

	private static ILoggerOegam log = LoggerOegam.getLogger("ProcesoAtem");
	private ATEMClient atemClient;
	private TramiteTrafSolInfoDAO tramiteTrafSolInfoDAO;

	// Constantes
	private static final String ERROR_TAG = "error";
	private static final String VEHICULO_TAG = "vehiculo";
	private static final String REFERENCIA_TAG = "referencia";

	private static final String CODIGO_ERROR_TAG = "codigoError";
	private static final String MENSAJE_ERROR_TAG = "mensajeError";
	private static final String DESCRIPCION_ERROR_TAG = "descripcionError";

	private ModeloCreditosTrafico modeloCreditosTrafico;
	private ModeloTrafico modeloTrafico;

	private GestorDocumentos gestorDocumentos;

	public GestorDocumentos getGuardarDocumentos() {
		if (gestorDocumentos == null) {
			gestorDocumentos = ContextoSpring.getInstance().getBean(GestorDocumentos.class);
		}
		return gestorDocumentos;
	}

	public ModeloAtem() throws ServiceException, OegamExcepcion {
		super();
		atemClient = new ATEMClient();
	}

	/**
	 * Si todo va bien, devuelve resultbean error a false, y en mensaje, el XML con la referencia o el listado de vehículos.
	 * Si hay fallo, resultbean con error a true y en el mensaje la descripción del error.
	 * Si hay fallo en alguna referencia pero otras fueron bien. Error a true. Lista mensajes con el mensaje de error y el xml con las correctas.
	 * Y en mensaje se indicara "error parcial"
	 * 
	 * @param referencia
	 * @return
	 * @throws OegamExcepcion
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 */
	public RespuestaAtemBean generaPeticionReferencias(List<TramiteTrafSolInfo> listTramiteTrafSolInfo) throws OegamExcepcion, IOException, SAXException, ParserConfigurationException, TransformerException{
		// Se inicializa la respuesta
		RespuestaAtemBean respuestaAtemBean = new RespuestaAtemBean();

		// Ordenar el listado para poder relacionar las respuestas con las solicitudes
		ordenaLista(listTramiteTrafSolInfo);

		// Llamada al WS
		respuestaAtemBean.setRespuestaCompleta(atemClient.obtenerResultadoConsulta(listTramiteTrafSolInfo.get(0).getReferenciaAtem()));

		// Si la respuesta del WS ha sido que ha dado error.
		compruebaError(listTramiteTrafSolInfo, respuestaAtemBean);

		return respuestaAtemBean;
	}

	private String generaPeticionMatriculasMasivas(List<TramiteTrafSolInfo> listaTramiteTrafSolInfo){
		StringBuffer peticionXML = new StringBuffer();

		//Cabecera del objeto de la petición
		peticionXML.append("<![CDATA[<vehiculos>");

		//Se itera por cada objeto que exista en la lista y se añade como nodo
		for (TramiteTrafSolInfo tramiteTrafSolInfo: listaTramiteTrafSolInfo ){
			peticionXML.append("<vehiculo><matricula>");
			peticionXML.append(tramiteTrafSolInfo.getVehiculo().getMatricula());
			peticionXML.append("</matricula><tasa>");
			peticionXML.append(tramiteTrafSolInfo.getTasa().getCodigoTasa());
			peticionXML.append("</tasa></vehiculo>");
		}

		//Se añade final de la petición
		peticionXML.append("</vehiculos>]]>");

		return new String(peticionXML.toString().getBytes(Charset.forName(Claves.ENCODING_ISO88591)), Charset.forName(Claves.ENCODING_ISO88591));
	}

	private String generaPeticionBastidoresMasivos(List<TramiteTrafSolInfo> listaTramiteTrafSolInfo){

		//Cabera del objeto de la petición
		StringBuffer peticionXML = new StringBuffer("<![CDATA[<vehiculos>");

		//Se itera por cada objeto que exista en la lista y se añade como nodo
		for (TramiteTrafSolInfo tramiteTrafSolInfo: listaTramiteTrafSolInfo ){
			peticionXML.append("<vehiculo><bastidor>");
			peticionXML.append(tramiteTrafSolInfo.getVehiculo().getBastidor());
			peticionXML.append("</bastidor><tasa>");
			peticionXML.append(tramiteTrafSolInfo.getTasa().getCodigoTasa());
			peticionXML.append("</tasa></vehiculo>");
		}

		//Se añade final de la petición
		peticionXML.append("</vehiculos>]]>");

		return new String(peticionXML.toString().getBytes(Charset.forName(Claves.ENCODING_UTF8)), Charset.forName(Claves.ENCODING_UTF8));

	}

	/**
	 * Si todo va bien, devuelve resultbean error a false, y en mensaje, el XML con la referencia o el listado de vehículos.
	 * Si hay fallo, resultbean con error a true y en el mensaje la descripción del error.
	 * 
	 * @param sb
	 * @return
	 * @throws OegamExcepcion
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 */
	public RespuestaAtemBean invocaPeticionMatriculaMasiva(List<TramiteTrafSolInfo> listaTramiteTrafSolInfo) throws OegamExcepcion, IOException, SAXException, ParserConfigurationException, TransformerException{
		// Se construye el resultado
		RespuestaAtemBean respuestaAtemBean = new RespuestaAtemBean();

		// Ordenar el listado para poder relacionar las respuestas con las solicitudes
		ordenaLista(listaTramiteTrafSolInfo);

		// Generar xml de la petición
		String xml = generaPeticionMatriculasMasivas(listaTramiteTrafSolInfo);

		//Recoge la respuesta y llama al metodo que invoca al WS
		log.debug("Invocar al cliente para realizar la petición consultaMasiva con tipo matricula");
		respuestaAtemBean.setRespuestaCompleta(atemClient.consultaMasiva("Matricula", xml));

		// Si la respuesta del WS ha sido que ha dado error.
		compruebaError(listaTramiteTrafSolInfo, respuestaAtemBean);

		return respuestaAtemBean;
	}

	/**
	 * Si todo va bien, devuelve resultbean error a false, y en mensaje, el XML con la referencia o el listado de vehículos.
	 * Si hay fallo, resultbean con error a true y en el mensaje la descripción del error.
	 * 
	 * @param sb
	 * @return
	 * @throws OegamExcepcion
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 */
	public RespuestaAtemBean invocaPeticionBastidorMasivo(List<TramiteTrafSolInfo> listaTramiteTrafSolInfo) throws OegamExcepcion, IOException, SAXException, ParserConfigurationException, TransformerException{
		// Se construye el resultado
		RespuestaAtemBean respuestaAtemBean = new RespuestaAtemBean();

		// Ordenar el listado para poder relacionar las respuestas con las solicitudes 
		ordenaLista(listaTramiteTrafSolInfo);

		// Generar xml de la petición
		String xml = generaPeticionBastidoresMasivos(listaTramiteTrafSolInfo);

		//Recoge la respuesta y llama al metodo que invoca al WS
		log.debug("Invocar al cliente para realizar la petición consultaMasiva con tipo bastidor");
		respuestaAtemBean.setRespuestaCompleta(atemClient.consultaMasiva("Bastidor", xml));

		// Si la respuesta del WS ha sido que ha dado error.
		compruebaError(listaTramiteTrafSolInfo, respuestaAtemBean);

		return respuestaAtemBean;
	}

	/**
	 * Si todo va bien, devuelve resultbean error a false, y en mensaje, el XML a parsear.
	 * Si hay fallo, resultbean con error a true y en el mensaje la descripción del error.
	 * 
	 * @param tramiteTrafSolInfo
	 * @return
	 * @throws OegamExcepcion 
	 * @throws ParserConfigurationException 
	 * @throws SAXException 
	 * @throws IOException 
	 * @throws TransformerException 
	 */
	public RespuestaAtemBean invocaPeticionMatriculaUnitaria(TramiteTrafSolInfo tramiteTrafSolInfo) throws OegamExcepcion, IOException, SAXException, ParserConfigurationException, TransformerException {
		// Se construye el resultado
		RespuestaAtemBean respuestaAtemBean = new RespuestaAtemBean();

		// Llamada al WS
		log.debug("Invocar al cliente para realizar la petición consultaMatricula");
		respuestaAtemBean.setRespuestaCompleta(atemClient.consultaMatricula(tramiteTrafSolInfo.getTasa().getCodigoTasa(), tramiteTrafSolInfo.getVehiculo().getMatricula()));

		// Si la respuesta del WS ha sido que ha dado error.
		compruebaError(tramiteTrafSolInfo, respuestaAtemBean);

		return respuestaAtemBean;
	}

	/**
	 * Este método se usa para hacer la llamada al servicio web de Atem desde un trámite Anuntis
	 * Si todo va bien, devuelve resultbean error a false, y en mensaje, el XML a parsear.
	 * Si hay fallo, resultbean con error a true y en el mensaje la descripción del error.
	 * 
	 * @param tramiteTrafSolInfo
	 * @return
	 * @throws OegamExcepcion
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 */
	public RespuestaAtemBean invocaPeticionMatriculaUnitariaAnuntis(String matricula, String codigoTasa) throws OegamExcepcion, IOException, SAXException, ParserConfigurationException, TransformerException {
		// Se construye el resultado
		RespuestaAtemBean respuestaAtemBean = new RespuestaAtemBean();

		// Llamada al WS
		log.debug("Invocar al cliente para realizar la petición consultaMatricula");
		respuestaAtemBean.setRespuestaCompleta(atemClient.consultaMatricula(codigoTasa, matricula));

		// Si la respuesta del WS ha sido que ha dado error.
		compruebaErrorAnuntis(respuestaAtemBean);

		return respuestaAtemBean;
	}

	/**
	 * Comprueba la respuesta del servicio Atem cuando el trámite es de Anuntis
	 * 
	 * @param respuesta
	 * @return
	 * @throws OegamExcepcion
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 */
	private RespuestaAtemBean compruebaErrorAnuntis(RespuestaAtemBean respuesta) throws OegamExcepcion, IOException, SAXException, ParserConfigurationException, TransformerException {
		// Se inicializa la respuesta

		if (respuesta.getRespuestaCompleta() == null || respuesta.getRespuestaCompleta().isEmpty()) {
			throw new OegamExcepcion("Respuesta obtenida vacía");
		} else {
			// Obeteremos el documento del XML
			Document doc = null;
			try {
				doc = getDocument(respuesta.getRespuestaCompleta().getBytes());
			} catch (ParserConfigurationException e) {
				log.error("Error parseando el XML", e);
				throw e;
			} catch (SAXException e) {
				log.error("Error parseando el XML", e);
				throw e;
			} catch (IOException e) {
				log.error("Error parseando el XML", e);
				throw e;
			}
			// Se normaliza para obtener mayor velocidad de procesado
			doc.getDocumentElement().normalize();

			NodeList errores = doc.getElementsByTagName(ERROR_TAG);
			NodeList vehiculos = doc.getElementsByTagName(VEHICULO_TAG);

			// Comprobar si existen errores devueltos en la respuesta
			if (errores != null && errores.getLength() > 0) {
				ResultBean resultUnitario = new ResultBean();
				try {
					Element eElement = (Element) errores.item(0);
					resultUnitario.setMensaje(new StringBuffer(
												eElement.getElementsByTagName(
														CODIGO_ERROR_TAG).item(0)
														.getTextContent())
												.append(" - ")
												.append(eElement
														.getElementsByTagName(
																MENSAJE_ERROR_TAG)
														.item(0).getTextContent())
												.toString());

				} catch (Exception e) {
					log.error("Error recuperando mensaje", e);
					resultUnitario.setMensaje(e.getMessage());
				}
				resultUnitario.setError(true);
				respuesta.setError(true);
				respuesta.setResultadosUnitarios(new HashMap<Long, ResultBean>());
				respuesta.getResultadosUnitarios().put(new Long(1), resultUnitario);

			}else if (vehiculos != null && vehiculos.getLength() > 0) {
				// No se informa de error
				respuesta.setError(false);
				// Obtener el listado de vehículos
				try {
					ResultBean resultUnitario = new ResultBean();
					resultUnitario.setError(false);

					TransformerFactory transFactory = TransformerFactory.newInstance();
					Transformer transformer = transFactory.newTransformer();

					StringWriter writer = new StringWriter();
					transformer.transform(new DOMSource(vehiculos.item(0)), new StreamResult(writer));
					resultUnitario.setMensaje(writer.toString());

					respuesta.setError(false);
					respuesta.setResultadosUnitarios(new HashMap<Long, ResultBean>());
					respuesta.getResultadosUnitarios().put(new Long(1), resultUnitario);
				}catch (TransformerConfigurationException e){
					log.error("No se pueden recuperar el listado de vehiculos", e);
				}
			}
			respuesta.setReferencia(false);
		}
		return respuesta;
	}

	/**
	 * Si todo va bien, devuelve resultbean error a false, y en mensaje, el XML a parsear.
	 * Si hay fallo, resultbean con error a true y en el mensaje la descripción del error.
	 * 
	 * @param tramiteTrafSolInfo
	 * @return
	 * @throws OegamExcepcion 
	 * @throws ParserConfigurationException 
	 * @throws SAXException 
	 * @throws IOException 
	 * @throws TransformerException 
	 */
	public RespuestaAtemBean invocaPeticionBastidorUnitario(TramiteTrafSolInfo tramiteTrafSolInfo) throws OegamExcepcion, IOException, SAXException, ParserConfigurationException, TransformerException {
		// Se construye el resultado
		RespuestaAtemBean respuestaAtemBean = new RespuestaAtemBean();

		// Llamada al WS
		log.debug("Invocar al cliente para realizar la petición consultaBastidor");
		respuestaAtemBean.setRespuestaCompleta(atemClient.consultaBastidor(tramiteTrafSolInfo.getTasa().getCodigoTasa(), tramiteTrafSolInfo.getVehiculo().getBastidor()));

		// Si la respuesta del WS ha sido que ha dado error.
		compruebaError(tramiteTrafSolInfo, respuestaAtemBean);

		return respuestaAtemBean;
	}

	public String cambiarEstadoTramite(BigDecimal idUsuario, TramiteTrafico tramiteTrafico) throws Exception{
		try{
			/*Despúes de procesar las solicitudes hay que comprobar como se encuentran estas para cambiar el estado del trámite
			 * 
			 * RECIBIDO	REFERENCIA	ERROR	ESTADO TRAMITE
					1		0		0			13
					1		1		0			22
					1		0		1			11
					0		1		1			25
					0		1		0			25
					0		0		1			11
					1		1		1			22
			 * 
			 * */

			int recibidos = 0;
			int referencia = 0;
			int error = 0;

			for (TramiteTrafSolInfo solicitud : tramiteTrafico.getTramiteTrafSolInfo()) {
				if (new BigDecimal(ResultadoAvpo.Recibido.getValorEnum()).compareTo(solicitud.getEstado()) == 0) {
					recibidos++;
				}
				if (new BigDecimal(ResultadoAvpo.Recibido_error.getValorEnum()).compareTo(solicitud.getEstado()) == 0) {
					error++;
				}
				if (new BigDecimal(ResultadoAvpo.Referencia.getValorEnum()).compareTo(solicitud.getEstado()) == 0) {
					referencia++;
				}
			}

			if (recibidos > 0 && referencia == 0 && error == 0){

				ResultBean resultBean = cambiarEstadoTramite(idUsuario, new BigDecimal(tramiteTrafico.getNumExpediente()), EstadoTramiteTrafico.Finalizado_PDF);
				if (resultBean.getError()==true){
					log.error("Ha ocurrido un error cambiando el estado a 'FinalizadoPDF': " + resultBean.getMensaje());
				} else {
					return EstadoTramiteTrafico.Finalizado_PDF.getValorEnum();
				}
			}

			if ((recibidos > 0 && referencia > 0 && error == 0) || (recibidos > 0 && referencia > 0 && error > 0)){
				ResultBean resultBean = cambiarEstadoTramite(idUsuario, new BigDecimal(tramiteTrafico.getNumExpediente()), EstadoTramiteTrafico.Finalizado_Parcialmente);
				if(resultBean.getError()==true){
					log.error("Ha ocurrido un error cambiando el estado a 'Finalizado parcialmente': " + resultBean.getMensaje());
				} else {
					return EstadoTramiteTrafico.Finalizado_Parcialmente.getValorEnum();
				}
			}

			if ((recibidos > 0 && referencia == 0 && error > 0) || (recibidos == 0 && referencia == 0 && error > 0)){
				ResultBean resultBean = cambiarEstadoTramite(idUsuario, new BigDecimal(tramiteTrafico.getNumExpediente()), EstadoTramiteTrafico.Finalizado_Con_Error);
				if(resultBean.getError()==true){
					log.error("Ha ocurrido un error cambiando el estado a 'Finalizado con error': " + resultBean.getMensaje());
				} else {
					return EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum();
				}
			}

			if ((recibidos == 0 && referencia > 0 && error > 0) || (recibidos == 0 && referencia > 0 && error == 0)){
				ResultBean resultBean = cambiarEstadoTramite(idUsuario, new BigDecimal(tramiteTrafico.getNumExpediente()), EstadoTramiteTrafico.Recibida_Referencia_Atem);
				if(resultBean.getError()==true){
					log.error("Ha ocurrido un error cambiando el estado a 'Recibida_referencia_ATEM': " + resultBean.getMensaje());
				} else {
					return EstadoTramiteTrafico.Recibida_Referencia_Atem.getValorEnum();
				}
			}

			throw new Exception("Proceso Atem. Cambio de estado del trámite. Ha habido un error. ");

		}catch(Exception e){
			throw new Exception("Proceso Atem. Cambio de estado del trámite. Ha habido un error. ", e);
		}
	}

	private RespuestaAtemBean compruebaError(List<TramiteTrafSolInfo> listaTramiteTrafSolInfo, RespuestaAtemBean respuesta) throws OegamExcepcion, IOException, SAXException, ParserConfigurationException, TransformerException {
		// Se inicializa la respuesta

		if (respuesta.getRespuestaCompleta() == null || respuesta.getRespuestaCompleta().isEmpty()) {
			throw new OegamExcepcion("Respuesta obtenida vacía");
		} else {
			// Obeteremos el documento del XML
			Document doc = null;
			try {
				doc = getDocument(respuesta.getRespuestaCompleta().getBytes());
			} catch (ParserConfigurationException e) {
				log.error("Error parseando el XML", e);
				throw e;
			} catch (SAXException e) {
				log.error("Error parseando el XML", e);
				throw e;
			} catch (IOException e) {
				log.error("Error parseando el XML", e);
				throw e;
			}

			// Se normaliza para obtener mayor velocidad de procesado
			doc.getDocumentElement().normalize();

			// Buscar referencia para consultarlo en diferido
			NodeList referencias = doc.getElementsByTagName(REFERENCIA_TAG);
			if (referencias != null && referencias.getLength() == 1) {
				respuesta.setReferencia(true);
				respuesta.setReferenciaAtem(referencias.item(0).getTextContent());
			} else {
				respuesta.setReferencia(false);
				respuesta.setResultadosUnitarios(new HashMap<Long, ResultBean>());
				// Si encontramos algún error, lo pondremos a true
				respuesta.setError(false);

				// Obtener el listado de vehículos. Buscar los nodos vehículo
				NodeList vehiculos = doc.getElementsByTagName(VEHICULO_TAG);
				if (vehiculos != null && vehiculos.getLength() > 0) {

					TransformerFactory transFactory = TransformerFactory.newInstance();
					Transformer transformer = transFactory.newTransformer();

					for (int i = 0; i < vehiculos.getLength(); i++) {
						//Se trata la siguiente línea <vehiculo>...</vehiculo>
						Element eElement = (Element) vehiculos.item(i);

						// Se obtiene la referencia del vehículo a la que debería hacer referencia esta línea
						Long idVehiculo = listaTramiteTrafSolInfo.get(i).getVehiculo().getId().getIdVehiculo();
	
						// Inicializamos el resultado de esta línea
						ResultBean resultadoUnitario = new ResultBean();

						// Comprobar si se dió un error en esta línea
						NodeList codErrores = eElement.getElementsByTagName(CODIGO_ERROR_TAG);
						if (codErrores != null && codErrores.getLength()>0) {
							// Este vehículo tiene error
							resultadoUnitario.setError(true);
							resultadoUnitario.setMensaje(new StringBuffer(
									codErrores.item(0).getTextContent())
									.append(" - ")
									.append(eElement
											.getElementsByTagName(
													DESCRIPCION_ERROR_TAG)
											.item(0).getTextContent())
									.toString());

							// Este tramite ya tiene alguna solicitud con error
							respuesta.setError(true);
							respuesta.getResultadosUnitarios().put(idVehiculo, resultadoUnitario);

						} else {
							// No hay error en esta línea, es el XML correspondiente a un vehículo
							StringWriter writer = new StringWriter();
							transformer.transform(new DOMSource(vehiculos.item(i)), new StreamResult(writer));
							resultadoUnitario.setMensaje(writer.toString());
							respuesta.getResultadosUnitarios().put(idVehiculo, resultadoUnitario);
						}
					}
				} else {
					// Ha debido ocurrir algún error

					// Obtener el listado de vehículos. Buscar los nodos vehículo
					NodeList errores = doc.getElementsByTagName(ERROR_TAG);
					if (errores != null && errores.getLength()>0) {
						ResultBean resultUnitario = new ResultBean();
						Element eElement = (Element) errores.item(0);
						resultUnitario.setMensaje(new StringBuffer(
													eElement.getElementsByTagName(
															CODIGO_ERROR_TAG).item(0)
															.getTextContent())
													.append(" - ")
													.append(eElement
															.getElementsByTagName(
																	MENSAJE_ERROR_TAG)
															.item(0).getTextContent())
													.toString());

						resultUnitario.setError(true);
						respuesta.setError(true);
						respuesta.setResultadosUnitarios(new HashMap<Long, ResultBean>());
						respuesta.getResultadosUnitarios().put(0L, resultUnitario);
					}
				}
			}
		}
		return respuesta;
	}

	private RespuestaAtemBean compruebaError(TramiteTrafSolInfo tramiteTrafSolInfo, RespuestaAtemBean respuesta) throws OegamExcepcion, IOException, SAXException, ParserConfigurationException, TransformerException {
		// Se inicializa la respuesta

		if (respuesta.getRespuestaCompleta() == null || respuesta.getRespuestaCompleta().isEmpty()) {
			throw new OegamExcepcion("Respuesta obtenida vacía");
		} else {
			// Obeteremos el documento del XML
			Document doc = null;
			try {
				doc = getDocument(respuesta.getRespuestaCompleta().getBytes());
			} catch (ParserConfigurationException e) {
				log.error("Error parseando el XML", e);
				throw e;
			} catch (SAXException e) {
				log.error("Error parseando el XML", e);
				throw e;
			} catch (IOException e) {
				log.error("Error parseando el XML", e);
				throw e;
			}

			// Se normaliza para obtener mayor velocidad de procesado
			doc.getDocumentElement().normalize();

			NodeList errores = doc.getElementsByTagName(ERROR_TAG);
			NodeList vehiculos = doc.getElementsByTagName(VEHICULO_TAG);

			// Comprobar si existen errores devueltos en la respuesta
			if (errores != null && errores.getLength() > 0) {
				ResultBean resultUnitario = new ResultBean();
				try {
					Element eElement = (Element) errores.item(0);
					resultUnitario.setMensaje(new StringBuffer(
												eElement.getElementsByTagName(
														CODIGO_ERROR_TAG).item(0)
														.getTextContent())
												.append(" - ")
												.append(eElement
														.getElementsByTagName(
																MENSAJE_ERROR_TAG)
														.item(0).getTextContent())
												.toString());

				} catch (Exception e) {
					log.error("Error recuperando mensaje", e);
					resultUnitario.setMensaje(e.getMessage());
				}
				resultUnitario.setError(true);
				respuesta.setError(true);
				respuesta.setResultadosUnitarios(new HashMap<Long, ResultBean>());
				respuesta.getResultadosUnitarios().put(tramiteTrafSolInfo.getVehiculo().getId().getIdVehiculo(), resultUnitario);

			} else if (vehiculos != null && vehiculos.getLength() > 0) {
				// No se informa de error
				respuesta.setError(false);
				// Obtener el listado de vehículos
				try {
					ResultBean resultUnitario = new ResultBean();
					resultUnitario.setError(false);

					TransformerFactory transFactory = TransformerFactory.newInstance();
					Transformer transformer = transFactory.newTransformer();

					StringWriter writer = new StringWriter();
					transformer.transform(new DOMSource(vehiculos.item(0)), new StreamResult(writer));
					resultUnitario.setMensaje(writer.toString());

					respuesta.setError(false);
					respuesta.setResultadosUnitarios(new HashMap<Long, ResultBean>());
					respuesta.getResultadosUnitarios().put(tramiteTrafSolInfo.getVehiculo().getId().getIdVehiculo(), resultUnitario);
				}catch (TransformerConfigurationException e){
					log.error("No se pueden recuperar el listado de vehiculos", e);
				}
			}
			respuesta.setReferencia(false);
		}
		return respuesta;
	}

	public Document getDocument(byte[] bytes) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	/*	dbFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
		dbFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");*/
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		return dBuilder.parse(new ByteArrayInputStream(bytes));
	}

	public void ordenaLista(List<TramiteTrafSolInfo> list){
		Collections.sort(list, new Comparator<TramiteTrafSolInfo>() {
			@Override
			public int compare(TramiteTrafSolInfo o1, TramiteTrafSolInfo o2) {
				return new Long(o1.getVehiculo().getId().getIdVehiculo()).compareTo( new Long(o2.getVehiculo().getId().getIdVehiculo()));
			}
		});
	}

	/** 
	 * Método que guarda el fichero por medio del gestorDocumentos con tipo ANUNTIS y subtipo ATEM
	 * @param numExpediente
	 * @param mapaBytesApdf
	 * @throws IOException
	 * @throws OegamExcepcion
	 */
	public void guardarInformesAnuntis(BigDecimal numExpediente, Map<String, byte[]> mapaBytesApdf) throws IOException, OegamExcepcion{
		guardarInformes(ConstantesGestorFicheros.ANUNTIS, numExpediente, mapaBytesApdf);
	}

	/**
	 * Método que guarda el fichero por medio del gestorDocumentos con tipo SOLINFO y subtipo ATEM
	 * @param numExpediente
	 * @param mapaBytesApdf
	 * @throws IOException
	 * @throws OegamExcepcion
	 */
	public void guardarInformesSolInfo(BigDecimal numExpediente, Map<String, byte[]> mapaBytesApdf) throws IOException, OegamExcepcion{
		guardarInformes(ConstantesGestorFicheros.SOLICITUD_INFORMACION, numExpediente, mapaBytesApdf);
	}

	/**
	 * Método que guarda el fichero por medio del gestorDocumentos con subtipo ATEM
	 * @param tipoDocumento
	 * @param numExpediente
	 * @param mapaBytesApdf
	 * @throws IOException
	 * @throws OegamExcepcion
	 */
	private void guardarInformes(String tipoDocumento, BigDecimal numExpediente, Map<String, byte[]> mapaBytesApdf) throws IOException, OegamExcepcion{
		FicheroBean fichero = new FicheroBean();
		fichero.setSobreescribir(false);
		fichero.setTipoDocumento(tipoDocumento);
		fichero.setFecha(Utilidades.transformExpedienteFecha(numExpediente));
		fichero.setNombreZip("Consultas_"+numExpediente);
		fichero.setListaByte(mapaBytesApdf);

		getGuardarDocumentos().empaquetarEnZipByte(fichero);
	}

	/** 
	 * Guarda el fichero XML recibido en el webservice de ATEM en la ruta de Anuntis
	 * 
	 * @param numExpediente
	 * @param ficheroByte
	 */
	public void guardarXmlAtemAnuntis(BigDecimal numExpediente, byte[] ficheroByte) {
		guardarXmlAtem(ConstantesGestorFicheros.ANUNTIS, numExpediente, ficheroByte);
	}

	/**
	 * Guarda el fichero XML recibido en el webservice de ATEM en la ruta de SolInfo
	 * 
	 * @param numExpediente
	 * @param respuestaAtemBean
	 */
	public void guardarXmlAtemSolInfo(BigDecimal numExpediente, RespuestaAtemBean respuestaAtemBean) {
		if (respuestaAtemBean != null && respuestaAtemBean.getRespuestaCompleta() != null) {
			guardarXmlAtem(ConstantesGestorFicheros.SOLICITUD_INFORMACION, numExpediente, respuestaAtemBean.getRespuestaCompleta().getBytes());
		}
	}

	/**
	 * Guarda el fichero XML recibido en el webservice de ATEM
	 * 
	 * @param tipoDocumento
	 * @param numExpediente
	 * @param ficheroByte
	 */
	private void guardarXmlAtem(String tipoDocumento, BigDecimal numExpediente, byte[] ficheroByte) {
		try {
			FicheroBean fichero = new FicheroBean();
			fichero.setSobreescribir(false);
			fichero.setTipoDocumento(tipoDocumento);
			fichero.setSubTipo(ConstantesGestorFicheros.ATEM_XML);
			fichero.setFecha(Utilidades.transformExpedienteFecha(numExpediente));
			fichero.setNombreDocumento(ConstantesGestorFicheros.NOMBRE_CONSULTA+numExpediente.toString());
			fichero.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
			fichero.setFicheroByte(ficheroByte);

			getGuardarDocumentos().guardarByte(fichero);

		} catch (OegamExcepcion t){
			log.error("Error guardando copia del fichero XML recibido", t);
		}
	}

	public void descontarCreditos(String contrato, BigDecimal idUsuario) throws DescontarCreditosExcepcion{
		HashMap<String, Object> resultado = getModeloCreditosTrafico().descontarCreditos(
				contrato,
				ContextoSpring.getInstance().getBean(Utiles.class).convertirIntegerABigDecimal(1),
				"T4",idUsuario);

		ResultBean resultadoProcedimiento =(ResultBean) resultado.get(ConstantesPQ.RESULTBEAN);

		if(resultadoProcedimiento.getError()){
			String mensajeError = "Error al descontar créditos de la operación";
			resultadoProcedimiento.setError(true);
			log.error(mensajeError);
			throw new DescontarCreditosExcepcion(resultadoProcedimiento.getMensaje());
		}
	}

	public void anotarGasto(String contrato, BigDecimal idUsuario, TramiteTrafico tramiteTrafico){
		ServicioCreditoFacturado servicioCreditoFacturado = (ServicioCreditoFacturado) ContextoSpring.getInstance().getBean(Constantes.SERVICIO_HISTORICO_CREDITO);
		if (servicioCreditoFacturado != null ){
			servicioCreditoFacturado.anotarGasto(new Integer(1), ConceptoCreditoFacturado.ATE, new Long(contrato), TipoTramiteTrafico.Solicitud.getValorEnum(), tramiteTrafico.getNumExpediente().toString());
		}
	}

	public void descontarCreditosMasivo(String contrato, BigDecimal idUsuario, List<TramiteTrafSolInfo> listaParaMasivo) throws DescontarCreditosExcepcion{
		// Por cada elemento de la lista de matricula o bastidor masivo se debe llamar al metodo para descontar creditos. 
		for (TramiteTrafSolInfo tramiteTrafSolInfo: listaParaMasivo){
			if (log.isDebugEnabled()) {
				log.debug("PROCESO ATEM - Descontar crédito por expediente " + tramiteTrafSolInfo.getId().getNumExpediente() + " y vehiculo con ID " + tramiteTrafSolInfo.getId().getIdVehiculo());
			}
			descontarCreditos(contrato, idUsuario);
		}
	}

	/**
	 * Cambia el estado del tramite, Devuelve ResultBean.
	 * @param tramiteTrafico
	 * @param estado
	 * @return
	 * @throws Exception
	 */
	public ResultBean cambiarEstadoTramite(BigDecimal idUsuario, BigDecimal numExpediente, EstadoTramiteTrafico estado) {
		// Cambia el estado del trámite a pendiente de respuesta:
		BeanPQCambiarEstadoTramite beanPQ = new BeanPQCambiarEstadoTramite();
		beanPQ.setP_ESTADO(new BigDecimal(estado.getValorEnum()));
		beanPQ.setP_NUM_EXPEDIENTE(numExpediente);
		beanPQ.setP_ID_USUARIO(idUsuario);

		HashMap<String, Object> resultado = getModeloTrafico().cambiarEstadoTramite(beanPQ);
		return (ResultBean) resultado.get(ConstantesPQ.RESULTBEAN);
	}

	public void estableceSolicitudConError(TramiteTrafSolInfo tramiteTrafSolInfo){
		tramiteTrafSolInfo.setEstado(new BigDecimal(ResultadoAvpo.Recibido_error.getValorEnum()));
		getTramiteTrafSolInfoDAO().actualiza(tramiteTrafSolInfo);
	}

	public void estableceSolicitudRecibido(TramiteTrafSolInfo tramiteTrafSolInfo){
		tramiteTrafSolInfo.setEstado(new BigDecimal(ResultadoAvpo.Recibido.getValorEnum()));
		tramiteTrafSolInfo.setReferenciaAtem(null);
		getTramiteTrafSolInfoDAO().actualiza(tramiteTrafSolInfo);
	}

	public void estableceSolicitudesDeReferenciaConError(List<TramiteTrafSolInfo> listaTramitesSolInfo){
		for (TramiteTrafSolInfo tramiteTrafSolInfo :listaTramitesSolInfo){
			tramiteTrafSolInfo.setEstado(new BigDecimal(ResultadoAvpo.Recibido_error.getValorEnum()));
		}
		getTramiteTrafSolInfoDAO().actualizaMasivo(listaTramitesSolInfo);
	}

	public void estableceSolicitudesConError(List<TramiteTrafSolInfo> listaTramiteTrafSolInfo, String error){
		for(TramiteTrafSolInfo tramiteTrafSolInfo: listaTramiteTrafSolInfo){
			tramiteTrafSolInfo.setResultado(error);
			tramiteTrafSolInfo.setEstado(new BigDecimal(ResultadoAvpo.Recibido_error.getValorEnum()));
		}
		getTramiteTrafSolInfoDAO().actualizaMasivo(listaTramiteTrafSolInfo);
	}

	public void guardaReferencia(List<TramiteTrafSolInfo> listaTramiteTrafSolInfo, String referencia){

		for(TramiteTrafSolInfo tramiteTrafSolInfo: listaTramiteTrafSolInfo){
			tramiteTrafSolInfo.setReferenciaAtem(referencia);
			tramiteTrafSolInfo.setEstado(new BigDecimal(ResultadoAvpo.Referencia.getValorEnum()));
			tramiteTrafSolInfo.setResultado(referencia);
		}
		getTramiteTrafSolInfoDAO().actualizaMasivo(listaTramiteTrafSolInfo);
	}

	/* *********************************************************************** */
	/* MODELOS *************************************************************** */
	/* *********************************************************************** */
	// FIXME Si se implementa inyeccion por Spring quitar los ifs de los getters
	/* *********************************************************************** */

	public TramiteTrafSolInfoDAO getTramiteTrafSolInfoDAO() {
		if (tramiteTrafSolInfoDAO == null) {
			tramiteTrafSolInfoDAO = new TramiteTrafSolInfoDAO();
		}
		return tramiteTrafSolInfoDAO;
	}

	public void setTramiteTrafSolInfoDAO(TramiteTrafSolInfoDAO tramiteTrafSolInfoDAO) {
		this.tramiteTrafSolInfoDAO = tramiteTrafSolInfoDAO;
	}

	public ModeloCreditosTrafico getModeloCreditosTrafico() {
		if (modeloCreditosTrafico == null) {
			modeloCreditosTrafico = new ModeloCreditosTrafico();
		}
		return modeloCreditosTrafico;
	}

	public void setModeloCreditosTrafico(ModeloCreditosTrafico modeloCreditosTrafico) {
		this.modeloCreditosTrafico = modeloCreditosTrafico;
	}

	public ModeloTrafico getModeloTrafico() {
		if (modeloTrafico == null) {
			modeloTrafico = new ModeloTrafico();
		}
		return modeloTrafico;
	}

	public void setModeloTrafico(ModeloTrafico modeloTrafico) {
		this.modeloTrafico = modeloTrafico;
	}

}