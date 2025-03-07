package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.impl;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.gestoresmadrid.core.trafico.materiales.model.dao.DelegacionDao;
import org.gestoresmadrid.core.trafico.materiales.model.dao.IncidenciaDao;
import org.gestoresmadrid.core.trafico.materiales.model.dao.IncidenciaSerialDao;
import org.gestoresmadrid.core.trafico.materiales.model.dao.MaterialDao;
import org.gestoresmadrid.core.trafico.materiales.model.enumerados.EstadoPedido;
import org.gestoresmadrid.core.trafico.materiales.model.enumerados.MaterialesConstants;
import org.gestoresmadrid.core.trafico.materiales.model.vo.DelegacionVO;
import org.gestoresmadrid.core.trafico.materiales.model.vo.IncidenciaIntervaloVO;
import org.gestoresmadrid.core.trafico.materiales.model.vo.IncidenciaSerialVO;
import org.gestoresmadrid.core.trafico.materiales.model.vo.IncidenciaSerialVOId;
import org.gestoresmadrid.core.trafico.materiales.model.vo.IncidenciaVO;
import org.gestoresmadrid.core.trafico.materiales.model.vo.MaterialStockVO;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioCallToRestfullMateriales;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest.AppException;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest.ErrorMessage;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest.IncidenciasInfoRest;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest.MaterialesInfoRest;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest.PedidoInfoRest;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest.PedidosInfoRest;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest.RequestIncidencia;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest.Response;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest.StockInfoRest;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest.StocksInfoRest;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest.UsuarioInfoRest;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.IncidenciaDto;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.PedidoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import trafico.utiles.UtilesWSMatw;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioCallToRestfullMaterialesImpl implements ServicioCallToRestfullMateriales {

	/**
	 * 
	 */
	private static final long serialVersionUID = 973149945380113097L;
	
	@Resource DelegacionDao       delegacionDao;
	@Resource MaterialDao         materialDao;
	@Resource IncidenciaDao       incidenciaDao;
	@Resource IncidenciaSerialDao incidenciaSerialDao;
	
	private ILoggerOegam log = LoggerOegam.getLogger(ServicioCallToRestfullMaterialesImpl.class);
	
	private static final String SERVER_DEV = "stockdes.consejogestores.org";
	private static final String SERVER_PRO = "stock.consejogestores.org";

	private static final Long PEDIDOS_POR_PAGINA = 5L;
	private static final Long STOCKS_POR_PAGINA  = 50L;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Override
	public UsuarioInfoRest llamadaWSInfoUsuario() throws OegamExcepcion, AppException {
		
		log.info("Entra en la llamadaWS de Informacion de Usuario");

		UsuarioInfoRest usuario = null;

		try {
			
			String urlBasic = gestorPropiedades.valorPropertie(MaterialesConstants.URL_STOCK_BASICA);
			String operacion = MaterialesConstants.OPERACIONES_SERVICIOS_REST.OPERACION_INFO;
			String accessToken = gestorPropiedades.valorPropertie(MaterialesConstants.ACCESS_TOKEN_SERVICIOS_REST.ACCESS_TOKEN);
			
			URIBuilder url = new URIBuilder(urlBasic + operacion);
			url.addParameter("access_token", accessToken);

			if (SERVER_DEV.equals(url.getHost())) {
				fixUntrustCertificate();
			} else if (SERVER_PRO.equals(url.getHost())) {
				new UtilesWSMatw().cargarAlmacenesTrafico();
			} else {
				fixUntrustCertificate();
			}
			
			ClientConfig config = new DefaultClientConfig();
			config.getClasses().add(JacksonJsonProvider.class);
	
			Client client = Client.create(config);
			
			WebResource webResourceGet  = client.resource(url.toString());
			log.info("URL webResourceGet => " + webResourceGet.getURI().toASCIIString());
			
			webResourceGet.type(MediaType.APPLICATION_JSON_TYPE);
			webResourceGet.accept(MediaType.APPLICATION_JSON_TYPE);
		
			usuario = webResourceGet.get(new GenericType<UsuarioInfoRest>() {});
			
		} catch (UniformInterfaceException ex) {
			log.error("ha ocurrido un error " + ex.getMessage() );
			ErrorMessage errorM = ex.getResponse().getEntity(ErrorMessage.class);
			throw new AppException(errorM.getName(), errorM.getMessage(), errorM.getCode(), errorM.getStatus(), errorM.getType());
		} catch (URISyntaxException e) {
			log.error("ha ocurrido un error al construir la url " + e.getMessage() );
		} catch (KeyManagementException e) {
			log.error("ha ocurrido un error al validar host " + e.getMessage() );
		} catch (NoSuchAlgorithmException e) {
			log.error("ha ocurrido un error al validar Host " + e.getMessage() );
		}
		
		return usuario;
	}

	@Override
	public MaterialesInfoRest llamadaWSInfoMaterial() throws OegamExcepcion, AppException {

		log.info("Entra en la llamadaWS de Informacion de Material");

		MaterialesInfoRest materiales = null;
		
		try {
			
			String urlBasic = gestorPropiedades.valorPropertie(MaterialesConstants.URL_STOCK_BASICA);
			String operacion = MaterialesConstants.OPERACIONES_SERVICIOS_REST.OPERACION_MATERIALES;
			String accessToken = gestorPropiedades.valorPropertie(MaterialesConstants.ACCESS_TOKEN_SERVICIOS_REST.ACCESS_TOKEN);
			
			URIBuilder url = new URIBuilder(urlBasic + operacion);
			url.addParameter("access_token", accessToken);
			
			if (SERVER_DEV.equals(url.getHost())) {
				fixUntrustCertificate();
			} else if (SERVER_PRO.equals(url.getHost())) {
				new UtilesWSMatw().cargarAlmacenesTrafico();
			} else {
				fixUntrustCertificate();
			}
			
			ClientConfig config = new DefaultClientConfig();
			config.getClasses().add(JacksonJsonProvider.class);
	
			Client client = Client.create(config);
			
			WebResource webResourceGet  = client.resource(url.toString());
			log.info("URL webResourceGet => " + webResourceGet.getURI().toASCIIString());
			
			webResourceGet.type(MediaType.APPLICATION_JSON_TYPE);
			webResourceGet.accept(MediaType.APPLICATION_JSON_TYPE);
	
			materiales = webResourceGet.get(new GenericType<MaterialesInfoRest>() {});
			
		} catch (UniformInterfaceException ex) {
			log.error("ha ocurrido un error " + ex.getMessage() );
			ErrorMessage errorM = ex.getResponse().getEntity(ErrorMessage.class);
			throw new AppException(errorM.getName(), errorM.getMessage(), errorM.getCode(), errorM.getStatus(), errorM.getType());
		} catch (URISyntaxException e) {
			log.error("ha ocurrido un error al construir la url " + e.getMessage());
		} catch (KeyManagementException e) {
			log.error("ha ocurrido un error al validar host " + e.getMessage() );
		} catch (NoSuchAlgorithmException e) {
			log.error("ha ocurrido un error al validar Host " + e.getMessage() );
		}
		
		return materiales;

	}

	@Override
	public StocksInfoRest llamadaWSInfoStocks() throws OegamExcepcion, AppException {
		
		log.info("Entra en la llamadaWS de Informacion de Stock");
		StocksInfoRest       stock = null;
		List<StockInfoRest> stocksObtenidos = null;

		
		try {
			String urlBasic = gestorPropiedades.valorPropertie(MaterialesConstants.URL_STOCK_BASICA);
			String operacion = MaterialesConstants.OPERACIONES_SERVICIOS_REST.OPERACION_STOCKS;
			String accessToken = gestorPropiedades.valorPropertie(MaterialesConstants.ACCESS_TOKEN_SERVICIOS_REST.ACCESS_TOKEN);
			
			URIBuilder url = new URIBuilder(urlBasic + operacion);
			url.addParameter("access_token", accessToken);
			Long regPorPagina = STOCKS_POR_PAGINA;
			url.addParameter("per-page", regPorPagina.toString());

			if (SERVER_DEV.equals(url.getHost())) {
				fixUntrustCertificate();
			} else if (SERVER_PRO.equals(url.getHost())) {
				new UtilesWSMatw().cargarAlmacenesTrafico();
			} else {
				fixUntrustCertificate();
			}

			ClientConfig config = new DefaultClientConfig();
			config.getClasses().add(JacksonJsonProvider.class);
	
			Client client = Client.create(config);
	

			stocksObtenidos = new ArrayList<StockInfoRest>();
			Long numRecords = 0L;
			Long page = 1L;
			Long obtenidos = 0L;
			do {
				url.addParameter("page", page.toString());

				WebResource webResourceGet  = client.resource(url.toString());
				log.info("URL webResourceGet => " + webResourceGet.getURI().toASCIIString());
				
				webResourceGet.type(MediaType.APPLICATION_JSON_TYPE);
				webResourceGet.accept(MediaType.APPLICATION_JSON_TYPE);

				stock = webResourceGet.get(new GenericType<StocksInfoRest>() {});
				if (stock != null){
					numRecords = stock.getTotal();
					obtenidos +=  regPorPagina;
					stocksObtenidos.addAll(stock.getRecords());
					page++; 
				}
				
			} while (numRecords > obtenidos);

			
		} catch (UniformInterfaceException ex) {
			log.error("ha ocurrido un error " + ex.getMessage() );
			ErrorMessage errorM = ex.getResponse().getEntity(ErrorMessage.class);
			throw new AppException(errorM.getName(), errorM.getMessage(), errorM.getCode(), errorM.getStatus(), errorM.getType());
		} catch (URISyntaxException e) {
			log.error("ha ocurrido un error al construir la url " + e.getMessage() );
		} catch (KeyManagementException e) {
			log.error("ha ocurrido un error al validar host " + e.getMessage()  );
		} catch (NoSuchAlgorithmException e) {
			log.error("ha ocurrido un error al validar Host " + e.getMessage() );
		}
		
		return stock;
		
	}

	@Override
	public PedidosInfoRest llamadaWSInfoPedidos() throws OegamExcepcion, AppException {
		
		log.info("Entra en la llamadaWS de Informacion de Pedidos");
		PedidosInfoRest pedidos = null;
		List<PedidoInfoRest> pedidosObtenidos = null;
		
		try {
			String urlBasic = gestorPropiedades.valorPropertie(MaterialesConstants.URL_STOCK_BASICA);
			String operacion = MaterialesConstants.OPERACIONES_SERVICIOS_REST.OPERACION_PEDIDOS;
			String accessToken = gestorPropiedades.valorPropertie(MaterialesConstants.ACCESS_TOKEN_SERVICIOS_REST.ACCESS_TOKEN);
			
			URIBuilder url = new URIBuilder(urlBasic + operacion);
			url.addParameter("access_token", accessToken);
			Long regPorPagina = PEDIDOS_POR_PAGINA;
			url.addParameter("per-page", regPorPagina.toString());

			if (SERVER_DEV.equals(url.getHost())) {
				fixUntrustCertificate();
			} else if (SERVER_PRO.equals(url.getHost())) {
				new UtilesWSMatw().cargarAlmacenesTrafico();
			} else {
				fixUntrustCertificate();
			}

			ClientConfig config = new DefaultClientConfig();
			config.getClasses().add(JacksonJsonProvider.class);
	
			Client client = Client.create(config);

			pedidosObtenidos = new ArrayList<PedidoInfoRest>();
			Long numRecords = 0L;
			Long page = 1L;
			Long obtenidos = 0L;
			do {
				log.info("Procesando Informacion de Pedidos pagina ==> " + page);
				url.addParameter("page", page.toString());

				WebResource webResourceGet  = client.resource(url.toString());
				log.info("URL webResourceGet => " + webResourceGet.getURI().toASCIIString());
				
				webResourceGet.type(MediaType.APPLICATION_JSON_TYPE);
				webResourceGet.accept(MediaType.APPLICATION_JSON_TYPE);

				pedidos = webResourceGet.get(new GenericType<PedidosInfoRest>() {});
				if (pedidos != null){
					numRecords = pedidos.getTotal();
					obtenidos +=  regPorPagina;
					pedidosObtenidos.addAll(pedidos.getRecords());
					log.info("Fin Procesando pagina ==> " + page);
					page++; 
				}
				
			} while (numRecords > obtenidos);
				
			
		} catch (UniformInterfaceException ex) {
			log.error("ha ocurrido un error " + ex.getMessage() );
			ErrorMessage errorM = ex.getResponse().getEntity(ErrorMessage.class);
			throw new AppException(errorM.getName(), errorM.getMessage(), errorM.getCode(), errorM.getStatus(), errorM.getType());
		} catch (URISyntaxException e) {
			log.error("ha ocurrido un error al construir la url " + e.getMessage());
		} catch (KeyManagementException e) {
			log.error("ha ocurrido un error al validar host " + e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			log.error("ha ocurrido un error al validar Host " + e.getMessage());
		}
		
		PedidosInfoRest pedidosRecuperados = new PedidosInfoRest();
		pedidosRecuperados.setTotal(new Long(pedidosObtenidos.size()));
		pedidosRecuperados.setRecords(pedidosObtenidos);
		
		return pedidosRecuperados;
	}

	@Override
	public StockInfoRest llamadaWSInfoStock(Long stockId) throws OegamExcepcion, AppException {
		log.info("Entra en la llamadaWS de Informacion de un stock de un material");
		StockInfoRest stockObtenido = null;

		try {

			String urlBasic = gestorPropiedades.valorPropertie(MaterialesConstants.URL_STOCK_BASICA);
			String operacion = MaterialesConstants.OPERACIONES_SERVICIOS_REST.OPERACION_STOCK;
			String accessToken = gestorPropiedades.valorPropertie(MaterialesConstants.ACCESS_TOKEN_SERVICIOS_REST.ACCESS_TOKEN);

			URIBuilder url = new URIBuilder(urlBasic + operacion);
			url.addParameter("access_token", accessToken);
			url.addParameter("id", stockId.toString());
			
			if (SERVER_DEV.equals(url.getHost())) {
				fixUntrustCertificate();
			} else if (SERVER_PRO.equals(url.getHost())) {
				new UtilesWSMatw().cargarAlmacenesTrafico();
			} else {
				fixUntrustCertificate();
			}

			ClientConfig config = new DefaultClientConfig();
			config.getClasses().add(JacksonJsonProvider.class);
	
			Client client = Client.create(config);

			WebResource webResourceGet  = client.resource(url.toString());
			log.info("URL webResourceGet => " + webResourceGet.getURI().toASCIIString());
			
			webResourceGet.type(MediaType.APPLICATION_JSON_TYPE);
			webResourceGet.accept(MediaType.APPLICATION_JSON_TYPE);

			stockObtenido = webResourceGet.get(new GenericType<StockInfoRest>() {});
			
		} catch (UniformInterfaceException ex) {
			log.error("ha ocurrido un error " + ex.getMessage() );
			ErrorMessage errorM = ex.getResponse().getEntity(ErrorMessage.class);
			throw new AppException(errorM.getName(), errorM.getMessage(), errorM.getCode(), errorM.getStatus(), errorM.getType());
		} catch (URISyntaxException e) {
			log.error("ha ocurrido un error al construir la url " + e.getMessage());
		} catch (KeyManagementException e) {
			log.error("ha ocurrido un error al validar host " + e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			log.error("ha ocurrido un error al validar Host " + e.getMessage());
		}

		return stockObtenido;
	}

	@Override
	public PedidoInfoRest llamadaWSInfoPedido(Long pedidoId) throws OegamExcepcion, AppException {
		log.info("Entra en la llamadaWS de Informacion de un Pedido");
		PedidoInfoRest pedidoObtenido = null;

		try {

			String urlBasic = gestorPropiedades.valorPropertie(MaterialesConstants.URL_STOCK_BASICA);
			String operacion = MaterialesConstants.OPERACIONES_SERVICIOS_REST.OPERACION_PEDIDO;
			String accessToken = gestorPropiedades.valorPropertie(MaterialesConstants.ACCESS_TOKEN_SERVICIOS_REST.ACCESS_TOKEN);
			
			URIBuilder url = new URIBuilder(urlBasic + operacion);
			url.addParameter("access_token", accessToken);
			url.addParameter("id", pedidoId.toString());
			
			if (SERVER_DEV.equals(url.getHost())) {
				fixUntrustCertificate();
			} else if (SERVER_PRO.equals(url.getHost())) {
				new UtilesWSMatw().cargarAlmacenesTrafico();
			} else {
				fixUntrustCertificate();
			}

			ClientConfig config = new DefaultClientConfig();
			config.getClasses().add(JacksonJsonProvider.class);
	
			Client client = Client.create(config);

			WebResource webResourceGet  = client.resource(url.toString());
			log.info("URL webResourceGet => " + webResourceGet.getURI().toASCIIString());
			
			webResourceGet.type(MediaType.APPLICATION_JSON_TYPE);
			webResourceGet.accept(MediaType.APPLICATION_JSON_TYPE);

			pedidoObtenido = webResourceGet.get(new GenericType<PedidoInfoRest>() {});
			
		} catch (UniformInterfaceException ex) {
			log.error("ha ocurrido un error " + ex.getMessage() );
			ErrorMessage errorM = ex.getResponse().getEntity(ErrorMessage.class);
			throw new AppException(errorM.getName(), errorM.getMessage(), errorM.getCode(), errorM.getStatus(), errorM.getType());
		} catch (URISyntaxException e) {
			log.error("ha ocurrido un error al construir la url " + e.getMessage());
		} catch (KeyManagementException e) {
			log.error("ha ocurrido un error al validar host " + e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			log.error("ha ocurrido un error al validar Host " + e.getMessage());
		}

		return pedidoObtenido;
	}


	@Override
	public IncidenciasInfoRest llamadaWSInfoIncidencias() throws OegamExcepcion, AppException {
		log.info("Entra en la llamadaWS de Informacion de incidencias");
		IncidenciasInfoRest listIncidencias = null;
		
		try {
			String urlBasic = gestorPropiedades.valorPropertie(MaterialesConstants.URL_STOCK_BASICA);
			String operacion = MaterialesConstants.OPERACIONES_SERVICIOS_REST.OPERACION_INCIDENCIAS;
			String accessToken = gestorPropiedades.valorPropertie(MaterialesConstants.ACCESS_TOKEN_SERVICIOS_REST.ACCESS_TOKEN);
			
			URIBuilder url = new URIBuilder(urlBasic + operacion);
			url.addParameter("access_token", accessToken);

			if (SERVER_DEV.equals(url.getHost())) {
				fixUntrustCertificate();
			} else if (SERVER_PRO.equals(url.getHost())) {
				new UtilesWSMatw().cargarAlmacenesTrafico();
			} else {
				fixUntrustCertificate();
			}

			ClientConfig config = new DefaultClientConfig();
			config.getClasses().add(JacksonJsonProvider.class);
	
			Client client = Client.create(config);
	
			WebResource webResourceGet  = client.resource(url.toString());
			log.info("URL webResourceGet => " + webResourceGet.getURI().toASCIIString());
			
			webResourceGet.type(MediaType.APPLICATION_JSON_TYPE);
			webResourceGet.accept(MediaType.APPLICATION_JSON_TYPE);
		
			listIncidencias = webResourceGet.get(new GenericType<IncidenciasInfoRest>() {});
			
		} catch (UniformInterfaceException ex) {
			log.error("ha ocurrido un error " + ex.getMessage());
			ErrorMessage errorM = ex.getResponse().getEntity(ErrorMessage.class);
			throw new AppException(errorM.getName(), errorM.getMessage(), errorM.getCode(), errorM.getStatus(), errorM.getType());
		} catch (URISyntaxException e) {
			log.error("ha ocurrido un error al construir la url " + e.getMessage());
		} catch (KeyManagementException e) {
			log.error("ha ocurrido un error al validar host " + e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			log.error("ha ocurrido un error al validar Host " + e.getMessage());
		}
		
		return listIncidencias;
	}

	@Override
	@Transactional(readOnly=true)
	public Response llamadaWSCrearPedido(PedidoDto pedidoDto) throws OegamExcepcion, AppException {
		
		log.info("Entra en la llamadaWS de crear pedido");
		Response respuesta = null;
		
		try {
			String urlBasic = gestorPropiedades.valorPropertie(MaterialesConstants.URL_STOCK_BASICA);
			String operacion = MaterialesConstants.OPERACIONES_SERVICIOS_REST.OPERACION_PEDIDO_CREAR;
			String accessToken = gestorPropiedades.valorPropertie(MaterialesConstants.ACCESS_TOKEN_SERVICIOS_REST.ACCESS_TOKEN);
			
			URIBuilder url = new URIBuilder(urlBasic + operacion);
			url.addParameter("access_token", accessToken);

			if (SERVER_DEV.equals(url.getHost())) {
				fixUntrustCertificate();
			} else if (SERVER_PRO.equals(url.getHost())) {
				new UtilesWSMatw().cargarAlmacenesTrafico();
			} else {
				fixUntrustCertificate();
			}

			ClientConfig config = new DefaultClientConfig();
			config.getClasses().add(JacksonJsonProvider.class);
	
			Client client = Client.create(config);
	
			WebResource webResourcePost  = client.resource(url.toString());
			log.info("URL webResourcePost => " + webResourcePost.getURI().toASCIIString());
			
			MultivaluedMap<String, String> formData = crearPedidoToSend(pedidoDto);		
			
			respuesta = webResourcePost
			           .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
			           .accept(MediaType.APPLICATION_JSON_TYPE)
			           .post(Response.class, formData);

		} catch (UniformInterfaceException ex) {
			log.error("ha ocurrido un error " + respuesta);
			ErrorMessage errorM = ex.getResponse().getEntity(ErrorMessage.class);
			throw new AppException(errorM.getName(), errorM.getMessage(), errorM.getCode(), errorM.getStatus(), errorM.getType());
		} catch (URISyntaxException e) {
			log.error("ha ocurrido un error al construir la url " + e.getMessage());
		} catch (KeyManagementException e) {
			log.error("ha ocurrido un error al validar host " + e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			log.error("ha ocurrido un error al validar Host " + e.getMessage());
		} catch (ClientHandlerException e) {
			log.error("ha ocurrido un error al validar Host " + e.getMessage());
		} catch (NullPointerException e) {
			log.error("no ha podido cumplimentar todos los datos requeridos " + e.getMessage());
		}
		
		return respuesta;
	}

	@Override
	@Transactional(readOnly=true)
	public Response llamadaWSModificarPedido(PedidoDto pedidoDto)  throws OegamExcepcion, AppException {
		log.info("Entra en la llamadaWS de modificar pedido");
		Response respuesta = null;
		
		try {
			String urlBasic = gestorPropiedades.valorPropertie(MaterialesConstants.URL_STOCK_BASICA);
			String operacion = MaterialesConstants.OPERACIONES_SERVICIOS_REST.OPERACION_MODIFICAR_PEDIDO;
			String accessToken = gestorPropiedades.valorPropertie(MaterialesConstants.ACCESS_TOKEN_SERVICIOS_REST.ACCESS_TOKEN);
			
			URIBuilder url = new URIBuilder(urlBasic + operacion);
			url.addParameter("access_token", accessToken);
			url.addParameter("id", pedidoDto.getPedidoInvId().toString());

			if (SERVER_DEV.equals(url.getHost())) {
				fixUntrustCertificate();
			} else if (SERVER_PRO.equals(url.getHost())) {
				new UtilesWSMatw().cargarAlmacenesTrafico();
			} else {
				fixUntrustCertificate();
			}

			ClientConfig config = new DefaultClientConfig();
			config.getClasses().add(JacksonJsonProvider.class);
	
			Client client = Client.create(config);

			WebResource webResourcePut  = client.resource(url.toString());
			
			MultivaluedMap<String, String> formData = modifyPedidoToSend(pedidoDto);				
			
			respuesta = webResourcePut
			           .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
			           .put(Response.class, formData);
			
		} catch (UniformInterfaceException ex) {
			log.error("ha ocurrido un error " + respuesta);
			ErrorMessage errorM = ex.getResponse().getEntity(ErrorMessage.class);
			throw new AppException(errorM.getName(), errorM.getMessage(), errorM.getCode(), errorM.getStatus(), errorM.getType());
		} catch (URISyntaxException e) {
			log.error("ha ocurrido un error al construir la url " + e.getMessage());
		} catch (KeyManagementException e) {
			log.error("ha ocurrido un error al validar host " + e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			log.error("ha ocurrido un error al validar Host " + e.getMessage());
		} catch (ClientHandlerException e) {
			log.error("ha ocurrido un error al validar Host " + e.getMessage());
		} catch (NullPointerException e) {
			log.error("no ha podido cumplimentar todos los datos requeridos " + e.getMessage());
		}
		
		return respuesta;
	}

	@Override
	@Transactional
	public Response llamadaWSCrearIncidencia(IncidenciaDto incidenciaDto) throws OegamExcepcion ,AppException {
		Response respuesta = null;
		
		try {

			String urlBasic = gestorPropiedades.valorPropertie(MaterialesConstants.URL_STOCK_BASICA);
			String operacion = MaterialesConstants.OPERACIONES_SERVICIOS_REST.OPERACION_INCIDENCIA_CREAR;
			String accessToken = gestorPropiedades.valorPropertie(MaterialesConstants.ACCESS_TOKEN_SERVICIOS_REST.ACCESS_TOKEN);
			
			URIBuilder url = new URIBuilder(urlBasic + operacion);
			url.addParameter("access_token", accessToken);
			
			if (SERVER_DEV.equals(url.getHost())) {
				fixUntrustCertificate();
			} else if (SERVER_PRO.equals(url.getHost())) {
				new UtilesWSMatw().cargarAlmacenesTrafico();
			} else {
				fixUntrustCertificate();
			}
			
			ClientConfig config = new DefaultClientConfig();
			config.getClasses().add(JacksonJsonProvider.class);
	
			Client client = Client.create(config);
	
			WebResource webResourcePost  = client.resource(url.toString());
			log.info("URL webResourcePost => " + webResourcePost.getURI().toASCIIString());
			
			IncidenciaVO incidenciaVO = incidenciaDao.findIncidenciaByPk(incidenciaDto.getIncidenciaId());
			
			RequestIncidencia requestInci = new RequestIncidencia();
			requestInci.setMaterial(incidenciaVO.getMaterialVO().getMaterialId().toString());
			requestInci.setObservaciones(incidenciaVO.getObservaciones());
			DelegacionVO delegacionVO = delegacionDao.findByJefaturaProvincial(incidenciaVO.getJefaturaProvincial().getJefaturaProvincial());
			requestInci.setDelegacion_id(delegacionVO.getDelegacionId().toString());
			requestInci.setTipo(incidenciaVO.getTipo().toString());
			
			for(IncidenciaSerialVO itemSerial: incidenciaVO.getListaSeriales()) {
                requestInci.setNumSerie(itemSerial.getPk().getNumSerie());
				MultivaluedMap<String, String> formData = crearIncidenciaToSend(requestInci);				
					
				respuesta = webResourcePost
					           .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
					           .post(Response.class, formData);
				if ("ok".equals(respuesta.getMessage())) {
					IncidenciaSerialVO incidenciaSerialVO = incidenciaSerialDao.findIncidenciaSerialBySerial(incidenciaVO.getIncidenciaId(), itemSerial.getPk().getNumSerie());
					incidenciaSerialVO.setIncidenciaInvId(respuesta.getId());
					incidenciaSerialVO = incidenciaSerialDao.guardarOActualizar(incidenciaSerialVO);
					
					itemSerial.setIncidenciaInvId(respuesta.getId());
				} else {
					return respuesta;
				}
				
				incidenciaVO = incidenciaDao.guardarOActualizar(incidenciaVO);
			}
			
			for( IncidenciaIntervaloVO itemInt: incidenciaVO.getListaIntervalos() ) {
				
				String prefijo = obtenerPrefijo( itemInt.getPk().getNumSerieIni() );
				int longitudSerial = obtenerLongSerial(itemInt.getPk().getNumSerieIni());
				Long serialIni = obtenerSerial( itemInt.getPk().getNumSerieIni() );
				Long serialFin = obtenerSerial( itemInt.getNumSerieFin());
				
				for(Long ind = serialIni; ind <= serialFin; ind++) {
	                requestInci.setNumSerie(obtenerNumSerie(prefijo, ind, longitudSerial));
					MultivaluedMap<String, String> formData = crearIncidenciaToSend(requestInci);				
						
					respuesta = webResourcePost
						           .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
						           .post(Response.class, formData);
					if ("ok".equals(respuesta.getMessage())) {
						IncidenciaSerialVO serialVO = new IncidenciaSerialVO();
						IncidenciaSerialVOId pk = new IncidenciaSerialVOId();
						pk.setIncidenciaId(incidenciaVO.getIncidenciaId());
						pk.setNumSerie(obtenerNumSerie(prefijo, ind, longitudSerial));
						serialVO.setPk(pk);
						serialVO.setIncidenciaInvId(respuesta.getId());
						
						serialVO = incidenciaSerialDao.guardarOActualizar(serialVO);
						
						incidenciaVO.getListaSeriales().add(serialVO);
					} else {
						return respuesta;
					}
					
				}
				
				incidenciaVO = incidenciaDao.guardarOActualizar(incidenciaVO);
			}
			
			
	
		} catch (UniformInterfaceException ex) {
			log.error("ha ocurrido un error " + ex.getMessage());
			ErrorMessage errorM = ex.getResponse().getEntity(ErrorMessage.class);
			throw new AppException(errorM.getName(), errorM.getMessage(), errorM.getCode(), errorM.getStatus(), errorM.getType());
		} catch (URISyntaxException e) {
			log.error("ha ocurrido un error al construir la url " + e.getMessage() );
		} catch (KeyManagementException e) {
			log.error("ha ocurrido un error al validar host " + e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			log.error("ha ocurrido un error al validar Host " + e.getMessage() );
		} catch (NullPointerException e) {
			log.error("no ha podido cumplimentar todos los datos requeridos "  + e.getMessage() );
		}
		
		return respuesta;
	}	

	
	private MultivaluedMap<String, String> crearIncidenciaToSend(RequestIncidencia requestIncidencia) throws NullPointerException {
		MultivaluedMap<String, String> postBody = new MultivaluedMapImpl();
		
		try {
			postBody.add("material",      requestIncidencia.getMaterial());
			postBody.add("numserie",      requestIncidencia.getNumSerie());
			postBody.add("observaciones", requestIncidencia.getObservaciones());
			postBody.add("tipo",          requestIncidencia.getTipo());
			postBody.add("delegacion_id", requestIncidencia.getDelegacion_id());
			
		} catch (NullPointerException e) {
			throw e;
		}

		return postBody;
	}


	Long obtenerSerial(String numSerial) {
		String[] parts = numSerial.split("-");
		String serial = parts[1]; 
		return Long.valueOf(serial);
	}

	int obtenerLongSerial(String numSerial) {
		String[] parts = numSerial.split("-");
		String serial = parts[1]; 
		return serial.length();
	}

	String obtenerPrefijo(String numSerial) {
		String[] parts = numSerial.split("-");
		return parts[0]; 
	}
	
	String obtenerNumSerie( String prefijo, Long serial, int longitud) {
		StringBuffer str = new StringBuffer(prefijo + '-');
		String strSerial = StringUtils.leftPad(serial.toString(), longitud, '0');
		str.append(strSerial);
		return str.toString();
	}
			
	@Override
	@Transactional(readOnly=true)
	public Response llamadaWSUpdateIncidencia(IncidenciaDto incidenciaDto) throws OegamExcepcion, AppException {
		Response respuesta = null;
		
//		try {
//			String urlBasic = gestorPropiedades.valorPropertie(MaterialesConstants.URL_STOCK_BASICA);
//			String operacion = MaterialesConstants.OPERACIONES_SERVICIOS_REST.OPERACION_INCIDENCIA_UPDATE;
//			String accessToken = gestorPropiedades.valorPropertie(MaterialesConstants.ACCESS_TOKEN_SERVICIOS_REST.ACCESS_TOKEN);
//			
//			URIBuilder url = new URIBuilder(urlBasic + operacion);
//			url.addParameter("access_token", accessToken);
//			url.addParameter("id", incidenciaDto.getIncidenciaInve().toString());
//			
//			if (SERVER_DEV.equals(url.getHost())) {
//				fixUntrustCertificate();
//			} else if (SERVER_PRO.equals(url.getHost())) {
//				new UtilesWSMatw().cargarAlmacenesTrafico();
//			} else {
//				fixUntrustCertificate();
//			}
//
//			ClientConfig config = new DefaultClientConfig();
//			config.getClasses().add(JacksonJsonProvider.class);
//	
//			Client client = Client.create(config);
//	
//			WebResource webResourcePut  = client.resource(url.toString());
//			
//			MultivaluedMap<String, String> formData = modifyIncidenciaToSend(incidenciaDto);				
//			
//			respuesta = webResourcePut
//			           .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
//			           .put(Response.class, formData);
//
//			
//		} catch (UniformInterfaceException ex) {
//			log.error("ha ocurrido un error " );
//			ErrorMessage errorM = ex.getResponse().getEntity(ErrorMessage.class);
//			throw new AppException(errorM.getName(), errorM.getMessage(), errorM.getCode(), errorM.getStatus(), errorM.getType());
//		} catch (URISyntaxException e) {
//			log.error("ha ocurrido un error al construir la url" );
//			e.printStackTrace();
//		} catch (KeyManagementException e) {
//			log.error("ha ocurrido un error al validar host" );
//			e.printStackTrace();
//		} catch (NoSuchAlgorithmException e) {
//			log.error("ha ocurrido un error al validar Host" );
//			e.printStackTrace();
//		}
		
		return respuesta;
	}


	@Override
	public Response llamadaWSActualizarStock(MaterialStockVO materialStockVO) throws OegamExcepcion, AppException {
		Response respuesta = null;
		
		try {
			String urlBasic = gestorPropiedades.valorPropertie(MaterialesConstants.URL_STOCK_BASICA);
			String operacion = MaterialesConstants.OPERACIONES_SERVICIOS_REST.OPERACION_UPDATE_STOCK;
			String accessToken = gestorPropiedades.valorPropertie(MaterialesConstants.ACCESS_TOKEN_SERVICIOS_REST.ACCESS_TOKEN);
			String observaciones = "Actualizacion día " + materialStockVO.getFecUltRecarga().toString();
			
			URIBuilder url = new URIBuilder(urlBasic + operacion);
			url.addParameter("access_token", accessToken);
			url.addParameter("id", materialStockVO.getStockInvId().toString());

			if (SERVER_DEV.equals(url.getHost())) {
				fixUntrustCertificate();
				log.info("Ejecuto fixUntrustCertificate");
			} else if (SERVER_PRO.equals(url.getHost())) {
				new UtilesWSMatw().cargarAlmacenesTrafico();
				log.info("Ejecuto cargarAlmacenesTrafico");
			} else {
				fixUntrustCertificate();
				log.info("Ejecuto fixUntrustCertificate");
			}

			ClientConfig config = new DefaultClientConfig();
			config.getClasses().add(JacksonJsonProvider.class);
	
			Client client = Client.create(config);
	
			WebResource webResourcePut  = client.resource(url.toString());
			log.info("URL webResourcePut => " + webResourcePut.getURI().toASCIIString());
			
			MultivaluedMap<String, String> formData = modifyStockToSend(materialStockVO.getUnidades(), observaciones);				

			respuesta = webResourcePut
			           .type(MediaType. APPLICATION_FORM_URLENCODED_TYPE)
			           .put(Response.class, formData);
			
		} catch (UniformInterfaceException ex) {
			log.error("ha ocurrido un error " );
			ErrorMessage errorM = ex.getResponse().getEntity(ErrorMessage.class);
			throw new AppException(errorM.getName(), errorM.getMessage(), errorM.getCode(), errorM.getStatus(), errorM.getType());
		} catch (URISyntaxException e) {
			log.error("ha ocurrido un error al construir la url " + e.getMessage());
		} catch (KeyManagementException e) {
			log.error("ha ocurrido un error al validar host " + e.getMessage() );
		} catch (NoSuchAlgorithmException e) {
			log.error("ha ocurrido un error al validar Host " + e.getMessage() );
		} catch (NullPointerException e) {
			log.error("no ha podido cumplimentar todos los datos requeridos " + e.getMessage() );
		}
		
		return respuesta;
	}

	private MultivaluedMap<String, String> modifyStockToSend(Long unidades, String observaciones) throws NullPointerException {
		MultivaluedMap<String, String> postBody = new MultivaluedMapImpl();	
		
		try {
			postBody.add("unidades",      unidades.toString());
			postBody.add("observaciones", observaciones);
			
		} catch (NullPointerException e) {
			throw e;
		}
		
		return postBody;
	}

	private MultivaluedMap<String, String> crearPedidoToSend(PedidoDto pedidoDto) throws NullPointerException {
		MultivaluedMap<String,String> postBody = new MultivaluedMapImpl();
		
		try {
			postBody.add("pedidoDgt",     pedidoDto.getPedidoDgt());
			postBody.add("observaciones", pedidoDto.getObservaciones());
			postBody.add("material_id",   pedidoDto.getMaterialId().toString());
			postBody.add("unidades",      pedidoDto.getUnidades().toString());
			
			DelegacionVO delegacionVO = delegacionDao.findByJefaturaProvincial(pedidoDto.getJefaturaProvincial());
			postBody.add("delegacion_id", delegacionVO.getDelegacionId().toString());
			
		} catch (NullPointerException e) {
			throw e;
		}
		
		return postBody;
	}

	private MultivaluedMap<String, String> modifyPedidoToSend(PedidoDto pedidoDto) {
		MultivaluedMap<String,String> postBody = new MultivaluedMapImpl();
		
		try {
			EstadoPedido estado = EstadoPedido.convertir(pedidoDto.getEstado());
			postBody.add("estado",        estado.getNombreEnum());
			postBody.add("observaciones", pedidoDto.getObservaciones());
			postBody.add("material_id",   pedidoDto.getMaterialId().toString());
			postBody.add("unidades",      pedidoDto.getUnidades().toString());
			
		} catch (NullPointerException e) {
			throw e;
		}
		
		return postBody;
	}

	public void fixUntrustCertificate() throws KeyManagementException, NoSuchAlgorithmException {
		log.info("Paso por fixUntrustCertificate");
        TrustManager[] trustAllCerts = new TrustManager[] {
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }

            }
        };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        // set the  allTrusting verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	}

}
