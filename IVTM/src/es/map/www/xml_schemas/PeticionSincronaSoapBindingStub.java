/**
 * PeticionSincronaSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.map.www.xml_schemas;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.rmi.RemoteException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.apache.axis.Message;
import org.apache.axis.message.SOAPEnvelope;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;

import com.ivtm.constantes.ConstantesIVTMWS;

import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import es.map.www.scsp.esquemas.excepcionmap.ExcepcionMap;
import utilidades.web.OegamExcepcion;

public class PeticionSincronaSoapBindingStub extends
		org.apache.axis.client.Stub implements
		es.map.www.xml_schemas.PeticionPortType {
	private java.util.Vector cachedSerClasses = new java.util.Vector();
	private java.util.Vector cachedSerQNames = new java.util.Vector();
	private java.util.Vector cachedSerFactories = new java.util.Vector();
	private java.util.Vector cachedDeserFactories = new java.util.Vector();

	@Autowired
	protected GestorDocumentos servicioGuardarDocumento;

	public GestorDocumentos getServicioGuardarDocumento() {
		if (servicioGuardarDocumento==null){
			servicioGuardarDocumento = ContextoSpring.getInstance().getBean(GestorDocumentos.class);
		}
		return servicioGuardarDocumento;
	}

	public void setServicioGuardarDocumento(
			GestorDocumentos servicioGuardarDocumento) {
		this.servicioGuardarDocumento = servicioGuardarDocumento;
	}


	static org.apache.axis.description.OperationDesc[] _operations;

	static {
		_operations = new org.apache.axis.description.OperationDesc[1];
		_initOperationDesc1();
	}

	private static void _initOperationDesc1() {
		org.apache.axis.description.OperationDesc oper;
		org.apache.axis.description.ParameterDesc param;
		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("PeticionSincrona");
		param = new org.apache.axis.description.ParameterDesc(
				new javax.xml.namespace.QName(
						"http://www.map.es/scsp/esquemas/V2/peticion",
						"Peticion"),
				org.apache.axis.description.ParameterDesc.IN,
				new javax.xml.namespace.QName(
						"http://www.map.es/scsp/esquemas/V2/peticion",
						"Peticion"),
						es.map.scsp.esquemas.v2.peticion.altaivtm.Peticion.class,
				false, false);
		oper.addParameter(param);
		oper.setReturnType(new javax.xml.namespace.QName(
				"http://www.map.es/scsp/esquemas/V2/respuesta", "Respuesta"));
		oper
				.setReturnClass(es.map.scsp.esquemas.v2.respuesta.altaivtm.Respuesta.class);
		oper.setReturnQName(new javax.xml.namespace.QName(
				"http://www.map.es/scsp/esquemas/V2/respuesta", "Respuesta"));
		oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		oper.addFault(new org.apache.axis.description.FaultDesc(
				new javax.xml.namespace.QName("http://www.map.es/xml-schemas",
						"fault"),
				"es.map.www.scsp.esquemas.excepcionmap.ExcepcionMap",
				new javax.xml.namespace.QName(
						"http://www.map.es/scsp/esquemas/excepcionmap",
						"ExcepcionMap"), true));
		_operations[0] = oper;

	}

	public PeticionSincronaSoapBindingStub() throws org.apache.axis.AxisFault {
		this(null);
	}

	public PeticionSincronaSoapBindingStub(java.net.URL endpointURL,
			javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
		this(service);
		super.cachedEndpoint = endpointURL;
	}

	public PeticionSincronaSoapBindingStub(javax.xml.rpc.Service service)
			throws org.apache.axis.AxisFault {
		if (service == null) {
			super.service = new org.apache.axis.client.Service();
		} else {
			super.service = service;
		}
		((org.apache.axis.client.Service) super.service)
				.setTypeMappingVersion("1.2");
//		java.lang.Class cls;
//		javax.xml.namespace.QName qName;
//		javax.xml.namespace.QName qName2;
//		java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
//		java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
//		java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
//		java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
//		java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
//		java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
//		java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
//		java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
//		java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
//		java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
//		qName = new javax.xml.namespace.QName(
//				"http://peticion.V2.comun.map.es", "Atributos");
//		cachedSerQNames.add(qName);
//		cls = es.map.scsp.esquemas.v2.peticion.altaivtm.Atributos.class;
//		cachedSerClasses.add(cls);
//		cachedSerFactories.add(beansf);
//		cachedDeserFactories.add(beandf);
//
//		// qName = new
//		// javax.xml.namespace.QName("http://peticion.V2.comun.map.es",
//		// "Consentimiento");
//		// cachedSerQNames.add(qName);
//		// cls = es.map.scsp.esquemas.v2.peticion.Consentimiento.class;
//		// cachedSerClasses.add(cls);
//		// cachedSerFactories.add(enumsf);
//		// cachedDeserFactories.add(enumdf);
//
//		qName = new javax.xml.namespace.QName(
//				"http://peticion.V2.comun.map.es", "DatosGenericos");
//		cachedSerQNames.add(qName);
//		cls = es.map.scsp.esquemas.v2.peticion.altaivtm.DatosGenericos.class;
//		cachedSerClasses.add(cls);
//		cachedSerFactories.add(beansf);
//		cachedDeserFactories.add(beandf);
//
//		qName = new javax.xml.namespace.QName(
//				"http://peticion.V2.comun.map.es", "Emisor");
//		cachedSerQNames.add(qName);
//		cls = es.map.scsp.esquemas.v2.peticion.altaivtm.Emisor.class;
//		cachedSerClasses.add(cls);
//		cachedSerFactories.add(beansf);
//		cachedDeserFactories.add(beandf);
//
//		qName = new javax.xml.namespace.QName(
//				"http://peticion.V2.comun.map.es", "Estado");
//		cachedSerQNames.add(qName);
//		cls = es.map.scsp.esquemas.v2.peticion.altaivtm.Estado.class;
//		cachedSerClasses.add(cls);
//		cachedSerFactories.add(beansf);
//		cachedDeserFactories.add(beandf);
//
//		qName = new javax.xml.namespace.QName(
//				"http://peticion.V2.comun.map.es", "Funcionario");
//		cachedSerQNames.add(qName);
//		cls = es.map.scsp.esquemas.v2.peticion.altaivtm.Funcionario.class;
//		cachedSerClasses.add(cls);
//		cachedSerFactories.add(beansf);
//		cachedDeserFactories.add(beandf);
//
//		qName = new javax.xml.namespace.QName(
//				"http://peticion.V2.comun.map.es", "Solicitante");
//		cachedSerQNames.add(qName);
//		cls = es.map.scsp.esquemas.v2.peticion.altaivtm.Solicitante.class;
//		cachedSerClasses.add(cls);
//		cachedSerFactories.add(beansf);
//		cachedDeserFactories.add(beandf);
//
//		qName = new javax.xml.namespace.QName(
//				"http://peticion.V2.comun.map.es", "Solicitudes");
//		cachedSerQNames.add(qName);
//		cls = es.map.scsp.esquemas.v2.peticion.altaivtm.Solicitudes.class;
//		cachedSerClasses.add(cls);
//		cachedSerFactories.add(beansf);
//		cachedDeserFactories.add(beandf);
//
//		qName = new javax.xml.namespace.QName(
//				"http://www.map.es/scsp/esquemas/V2/peticion",
//				"SolicitudTransmision");
//		cls = es.map.scsp.esquemas.v2.peticion.altaivtm.SolicitudTransmision.class;
//		cachedSerClasses.add(cls);
//		cachedSerQNames.add(qName);
//		cachedSerFactories.add(beansf);
//		cachedDeserFactories.add(beandf);
//
//		qName = new javax.xml.namespace.QName(
//				"http://peticion.V2.comun.map.es", "Titular");
//		cachedSerQNames.add(qName);
//		cls = es.map.scsp.esquemas.v2.peticion.altaivtm.Titular.class;
//		cachedSerClasses.add(cls);
//		cachedSerFactories.add(beansf);
//		cachedDeserFactories.add(beandf);
//
//		qName = new javax.xml.namespace.QName(
//				"http://peticion.V2.comun.map.es", "Transmision");
//		cachedSerQNames.add(qName);
//		cls = es.map.scsp.esquemas.v2.peticion.altaivtm.Transmision.class;
//		cachedSerClasses.add(cls);
//		cachedSerFactories.add(beansf);
//		cachedDeserFactories.add(beandf);
//
//		qName = new javax.xml.namespace.QName(
//				"http://respuesta.V2.comun.map.es", "Atributos");
//		cachedSerQNames.add(qName);
//		cls = es.map.scsp.esquemas.v2.respuesta.altaivtm.Atributos.class;
//		cachedSerClasses.add(cls);
//		cachedSerFactories.add(beansf);
//		cachedDeserFactories.add(beandf);
//
//		qName = new javax.xml.namespace.QName(
//				"http://respuesta.V2.comun.map.es", "DatosGenericos");
//		cachedSerQNames.add(qName);
//		cls = es.map.scsp.esquemas.v2.respuesta.altaivtm.DatosGenericos.class;
//		cachedSerClasses.add(cls);
//		cachedSerFactories.add(beansf);
//		cachedDeserFactories.add(beandf);
//
//		qName = new javax.xml.namespace.QName(
//				"http://respuesta.V2.comun.map.es", "Emisor");
//		cachedSerQNames.add(qName);
//		cls = es.map.scsp.esquemas.v2.respuesta.altaivtm.Emisor.class;
//		cachedSerClasses.add(cls);
//		cachedSerFactories.add(beansf);
//		cachedDeserFactories.add(beandf);
//
//		qName = new javax.xml.namespace.QName(
//				"http://respuesta.V2.comun.map.es", "Estado");
//		cachedSerQNames.add(qName);
//		cls = es.map.scsp.esquemas.v2.respuesta.altaivtm.Estado.class;
//		cachedSerClasses.add(cls);
//		cachedSerFactories.add(beansf);
//		cachedDeserFactories.add(beandf);
//
//		qName = new javax.xml.namespace.QName(
//				"http://respuesta.V2.comun.map.es", "Funcionario");
//		cachedSerQNames.add(qName);
//		cls = es.map.scsp.esquemas.v2.respuesta.altaivtm.Funcionario.class;
//		cachedSerClasses.add(cls);
//		cachedSerFactories.add(beansf);
//		cachedDeserFactories.add(beandf);
//
//		qName = new javax.xml.namespace.QName(
//				"http://respuesta.V2.comun.map.es", "Solicitante");
//		cachedSerQNames.add(qName);
//		cls = es.map.scsp.esquemas.v2.respuesta.altaivtm.Solicitante.class;
//		cachedSerClasses.add(cls);
//		cachedSerFactories.add(beansf);
//		cachedDeserFactories.add(beandf);
//
//		qName = new javax.xml.namespace.QName(
//				"http://respuesta.V2.comun.map.es", "Titular");
//		cachedSerQNames.add(qName);
//		cls = es.map.scsp.esquemas.v2.respuesta.altaivtm.Titular.class;
//		cachedSerClasses.add(cls);
//		cachedSerFactories.add(beansf);
//		cachedDeserFactories.add(beandf);
//
//		qName = new javax.xml.namespace.QName(
//				"http://respuesta.V2.comun.map.es", "Transmision");
//		cachedSerQNames.add(qName);
//		cls = es.map.scsp.esquemas.v2.respuesta.altaivtm.Transmision.class;
//		cachedSerClasses.add(cls);
//		cachedSerFactories.add(beansf);
//		cachedDeserFactories.add(beandf);
//
//		qName = new javax.xml.namespace.QName(
//				"http://respuesta.V2.comun.map.es", "Transmisiones");
//		cachedSerQNames.add(qName);
//		cls = es.map.scsp.esquemas.v2.respuesta.altaivtm.TransmisionDatos.class;
//		cachedSerClasses.add(cls);
//		qName = new javax.xml.namespace.QName(
//				"http://www.map.es/scsp/esquemas/V2/respuesta",
//				"TransmisionDatos");
//		qName2 = new javax.xml.namespace.QName(
//				"http://respuesta.V2.comun.map.es", "TransmisionDatos");
//		cachedSerFactories
//				.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(
//						qName, qName2));
//		cachedDeserFactories
//				.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());
//
//		qName = new javax.xml.namespace.QName(
//				"http://www.map.es/scsp/esquemas/datosespecificos",
//				"DatosEspecificos");
//		cachedSerQNames.add(qName);
//		cls = es.map.scsp.esquemas.datosespecificos.DatosEspecificosAltaProyectoIVTMEntrada.class;
//		cachedSerClasses.add(cls);
//		cachedSerFactories.add(beansf);
//		cachedDeserFactories.add(beandf);
//
//		qName = new javax.xml.namespace.QName(
//				"http://www.map.es/scsp/esquemas/datosespecificos",
//				"DatosEspecificos");
//		cachedSerQNames.add(qName);
//		cls = es.map.scsp.esquemas.datosespecificos.DatosEspecificosAltaProyectoIVTMRespuesta.class;
//		cachedSerClasses.add(cls);
//		cachedSerFactories.add(beansf);
//		cachedDeserFactories.add(beandf);
//
//		qName = new javax.xml.namespace.QName(
//				"http://www.map.es/scsp/esquemas/excepcionmap", "ExcepcionMap");
//		cachedSerQNames.add(qName);
//		cls = es.map.www.scsp.esquemas.excepcionmap.ExcepcionMap.class;
//		cachedSerClasses.add(cls);
//		cachedSerFactories.add(beansf);
//		cachedDeserFactories.add(beandf);
//
//		qName = new javax.xml.namespace.QName(
//				"http://www.map.es/scsp/esquemas/V2/peticion", "Peticion");
//		cachedSerQNames.add(qName);
//		cls = es.map.scsp.esquemas.v2.peticion.altaivtm.Peticion.class;
//		cachedSerClasses.add(cls);
//		cachedSerFactories.add(beansf);
//		cachedDeserFactories.add(beandf);
//
//		qName = new javax.xml.namespace.QName(
//				"http://www.map.es/scsp/esquemas/V2/peticion",
//				"SolicitudTransmision");
//		cachedSerQNames.add(qName);
//		cls = es.map.scsp.esquemas.v2.peticion.altaivtm.SolicitudTransmision.class;
//		cachedSerClasses.add(cls);
//		cachedSerFactories.add(beansf);
//		cachedDeserFactories.add(beandf);
//
//		qName = new javax.xml.namespace.QName(
//				"http://www.map.es/scsp/esquemas/V2/respuesta", "Respuesta");
//		cachedSerQNames.add(qName);
//		cls = es.map.scsp.esquemas.v2.respuesta.altaivtm.Respuesta.class;
//		cachedSerClasses.add(cls);
//		cachedSerFactories.add(beansf);
//		cachedDeserFactories.add(beandf);
//
//		qName = new javax.xml.namespace.QName(
//				"http://www.map.es/scsp/esquemas/V2/respuesta",
//				"TransmisionDatos");
//		cachedSerQNames.add(qName);
//		cls = es.map.scsp.esquemas.v2.respuesta.altaivtm.TransmisionDatos.class;
//		cachedSerClasses.add(cls);
//		cachedSerFactories.add(beansf);
//		cachedDeserFactories.add(beandf);
//
//		qName = new javax.xml.namespace.QName(
//				"http://www.map.es/scsp/esquemas/datosespecificos", "Vehiculo");
//		cachedSerQNames.add(qName);
//		cls = es.map.scsp.esquemas.datosespecificos.Vehiculo.class;
//		cachedSerClasses.add(cls);
//		cachedSerFactories.add(beansf);
//		cachedDeserFactories.add(beandf);
//
//		qName = new javax.xml.namespace.QName(
//				"http://www.map.es/scsp/esquemas/datosespecificos", "Sujeto");
//		cachedSerQNames.add(qName);
//		cls = es.map.scsp.esquemas.datosespecificos.Sujeto.class;
//		cachedSerClasses.add(cls);
//		cachedSerFactories.add(beansf);
//		cachedDeserFactories.add(beandf);
//
//		qName = new javax.xml.namespace.QName(
//				"http://www.map.es/scsp/esquemas/datosespecificos", "Osujeto");
//		cachedSerQNames.add(qName);
//		cls = es.map.scsp.esquemas.datosespecificos.Osujeto.class;
//		cachedSerClasses.add(cls);
//		cachedSerFactories.add(beansf);
//		cachedDeserFactories.add(beandf);
//
//		qName = new javax.xml.namespace.QName(
//				"http://www.map.es/scsp/esquemas/datosespecificos", "Domicilio");
//		cachedSerQNames.add(qName);
//		cls = es.map.scsp.esquemas.datosespecificos.Domicilio.class;
//		cachedSerClasses.add(cls);
//		cachedSerFactories.add(beansf);
//		cachedDeserFactories.add(beandf);
	}

	protected org.apache.axis.client.Call createCall()
			throws java.rmi.RemoteException {
		try {
			org.apache.axis.client.Call _call = super._createCall();
			if (super.maintainSessionSet) {
				_call.setMaintainSession(super.maintainSession);
			}
			if (super.cachedUsername != null) {
				_call.setUsername(super.cachedUsername);
			}
			if (super.cachedPassword != null) {
				_call.setPassword(super.cachedPassword);
			}
			if (super.cachedEndpoint != null) {
				_call.setTargetEndpointAddress(super.cachedEndpoint);
			}
			if (super.cachedTimeout != null) {
				_call.setTimeout(super.cachedTimeout);
			}
			if (super.cachedPortName != null) {
				_call.setPortName(super.cachedPortName);
			}
			java.util.Enumeration keys = super.cachedProperties.keys();
			while (keys.hasMoreElements()) {
				java.lang.String key = (java.lang.String) keys.nextElement();
				_call.setProperty(key, super.cachedProperties.get(key));
			}
			// All the type mapping information is registered
			// when the first call is made.
			// The type mapping information is actually registered in
			// the TypeMappingRegistry of the service, which
			// is the reason why registration is only needed for the first call.
			synchronized (this) {
				if (firstCall()) {
					// must set encoding style before registering serializers
					_call.setEncodingStyle(null);
					for (int i = 0; i < cachedSerFactories.size(); ++i) {
						java.lang.Class cls = (java.lang.Class) cachedSerClasses
								.get(i);
						javax.xml.namespace.QName qName = (javax.xml.namespace.QName) cachedSerQNames
								.get(i);
						java.lang.Object x = cachedSerFactories.get(i);
						if (x instanceof Class) {
							java.lang.Class sf = (java.lang.Class) cachedSerFactories
									.get(i);
							java.lang.Class df = (java.lang.Class) cachedDeserFactories
									.get(i);
							_call
									.registerTypeMapping(cls, qName, sf, df,
											false);
						} else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
							org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory) cachedSerFactories
									.get(i);
							org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory) cachedDeserFactories
									.get(i);
							_call
									.registerTypeMapping(cls, qName, sf, df,
											false);
						}
					}
				}
			}
			return _call;
		} catch (java.lang.Throwable _t) {
			throw new org.apache.axis.AxisFault(
					"Failure trying to get the Call object", _t);
		}
	}

	public es.map.scsp.esquemas.v2.respuesta.altaivtm.Respuesta peticionSincronaAltaIVTM(
			es.map.scsp.esquemas.v2.peticion.altaivtm.Peticion peticion,
			java.lang.String firma) throws java.rmi.RemoteException,
			es.map.www.scsp.esquemas.excepcionmap.ExcepcionMap {
		return (es.map.scsp.esquemas.v2.respuesta.altaivtm.Respuesta) peticionSincronaIVTM(es.map.scsp.esquemas.v2.respuesta.altaivtm.Respuesta.class, new BigDecimal(peticion.getAtributos().getIdPeticion()), ConstantesIVTMWS.IVTMMATRICULACIONRESPUESTA_GESTOR_FICHEROS, firma);
	}

	@Override
	public es.map.scsp.esquemas.v2.respuesta.consultaivtm.Respuesta peticionSincronaConsultaIVTM(
			es.map.scsp.esquemas.v2.peticion.consultaivtm.Peticion peticion,
			String firma) throws RemoteException, ExcepcionMap {
		return (es.map.scsp.esquemas.v2.respuesta.consultaivtm.Respuesta) peticionSincronaIVTM(es.map.scsp.esquemas.v2.respuesta.consultaivtm.Respuesta.class, new BigDecimal(peticion.getAtributos().getIdPeticion()),ConstantesIVTMWS.IVTMCONSULTARESPUESTA_GESTOR_FICHEROS, firma);
	}

	@Override
	public es.map.scsp.esquemas.v2.respuesta.modificacionivtm.Respuesta peticionSincronaModificacionIVTM(es.map.scsp.esquemas.v2.peticion.modificacionivtm.Peticion peticion,
			String firma) throws RemoteException, ExcepcionMap {
		return (es.map.scsp.esquemas.v2.respuesta.modificacionivtm.Respuesta) peticionSincronaIVTM(es.map.scsp.esquemas.v2.respuesta.modificacionivtm.Respuesta.class, new BigDecimal(peticion.getAtributos().getIdPeticion()),ConstantesIVTMWS.IVTMMATRICULACIONRESPUESTA_GESTOR_FICHEROS, firma);
	}
	
	public Object peticionSincronaIVTM(Class claseRespuesta, BigDecimal idPeticion, String subtipo, String firma) throws java.rmi.RemoteException, ExcepcionMap {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[0]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("PeticionSincrona");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR,
				Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS,
				Boolean.FALSE);
		_call
				.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName("",
				"PeticionSincrona"));

		try {
			// Creación del manejador que securizará la petición
			ClientHandler sender = new ClientHandler();
			_call.setClientHandlers(sender, null);
		} catch (Exception e) {
			Log.error(ConstantesIVTMWS.TEXTO_LOG_PROCESO+" Se ha generado una excepción usando el ClientHandler "+e.getMessage());
		}
		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new Message(firma));
			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					Object res = null;
					if (_resp instanceof SOAPEnvelope) {
						// Recuperamos la respuesta SOAP como un String
						String response = ((SOAPEnvelope) _resp).getAsString();
						res = guardarRespuesta(claseRespuesta, response, idPeticion, subtipo);
						return res;
					}
				} catch (java.lang.Exception _exception) {
					return org.apache.axis.utils.JavaUtils
							.convert(
									_resp,
									claseRespuesta);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			if (axisFaultException.detail != null) {
				if (axisFaultException.detail instanceof java.rmi.RemoteException) {
					throw (java.rmi.RemoteException) axisFaultException.detail;
				}
				if (axisFaultException.detail instanceof es.map.www.scsp.esquemas.excepcionmap.ExcepcionMap) {
					throw (es.map.www.scsp.esquemas.excepcionmap.ExcepcionMap) axisFaultException.detail;
				}
			}
			throw axisFaultException;
		}
		return null;
	}

	private Object guardarRespuesta(Class claseRespuesta, String response, BigDecimal idPeticion, String subtipo) throws ParserConfigurationException, SAXException, IOException, TransformerFactoryConfigurationError, TransformerException, JAXBException{
		Object res = null;
		try {
			response = response.substring(response.indexOf("<Respuesta "), response.indexOf("</Respuesta>")	+ new String("</Respuesta>").length());
			response = response.replaceAll("<Respuesta xmlns=\"http://www.map.es/scsp/esquemas/V2/respuesta\">","<Respuesta xmlns=\"http://www.map.es/scsp/esquemas/V2/respuesta\" xmlns:xsd=\"http://www.w3.org./2001/XMLSchema-instance\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");
			getServicioGuardarDocumento().guardarFichero(ConstantesIVTMWS.TIPO_IVTM_GESTOR_FICHEROS, subtipo, Utilidades.transformExpedienteFecha(idPeticion), ConstantesIVTMWS.IVTM_INICIO_NOMBRE_FICHERO_GUARDAR_RESPUESTA + idPeticion.toString(), ".xml", (ConstantesIVTMWS.CABECERA_XML + response).getBytes());
			// Unmarshall de la respuesta
			JAXBContext context = JAXBContext.newInstance(claseRespuesta);
			Unmarshaller um = context.createUnmarshaller();

			res = um.unmarshal(new StringReader(response));

		} catch (OegamExcepcion e) {
			Log.error(ConstantesIVTMWS.TEXTO_LOG_PROCESO+" Error guardando la respuesta "+e.getMessage());
		}
		return res;
	}

}