package org.gestoresmadrid.oegam2comun.arrendatarios.model.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;

import org.gestoresmadrid.core.arrendatarios.model.vo.ArrendatarioVO;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.beans.ResultConsultaArrendatarioBean;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.xml.DatosEspecificos;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.xml.DatosFirmados;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.xml.DatosGenericos;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.xml.InformacionAsunto;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.xml.InformacionDestino;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.xml.InformacionPersona;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.xml.InformacionPersona.DocumentoIdentificacion;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.xml.Interesados;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.xml.ObjectFactory;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.xml.SolicitudRegistroEntrada;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.xml.XmlArrendatarioFactory;
import org.springframework.stereotype.Component;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import viafirma.utilidades.UtilesViafirma;

@Component
public class BuildArrendatario implements Serializable{

	private static final long serialVersionUID = 5151353261648613125L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(BuildArrendatario.class);
	private static final String CADENA_VACIA = "";
	
	public static final String HA_OCURRIDO_UN_ERROR_COMPROBANDO_LA_CADUCIDAD_DEL_CERTIFICADO = "Ha ocurrido un error comprobando la caducidad del certificado";

	public ResultConsultaArrendatarioBean generarXml(ArrendatarioVO arrendatarioVO) {
    ResultConsultaArrendatarioBean resultado = new ResultConsultaArrendatarioBean(Boolean.FALSE);
    try {
      ObjectFactory objectFactory = new ObjectFactory();
      // Solicitud Registro Entrada
      SolicitudRegistroEntrada solicitud = objectFactory.createSolicitudRegistroEntrada();
      // Datos Firmados
      DatosFirmados datosFirmados = objectFactory.createDatosFirmados();
      DatosEspecificos datosEspecificos = rellenarDatosEspecificos(arrendatarioVO, objectFactory);
      DatosGenericos datosGenericos = rellenarDatosGenericos(arrendatarioVO, objectFactory);
      
      

      
      InformacionAsunto informacionAsunto = objectFactory.createInformacionAsunto();
      informacionAsunto.setCodigo("SAA");
      informacionAsunto.setDescripcion("Solicitud de Anotacion de Arrendatario");
      datosGenericos.setAsunto(informacionAsunto);
      
     /* interesadoPersona.setDocumentoIdentificacion(docIdentInteresado);
      interesados.getInteresado().add(interesadoPersona);*/
    //  datosGenericos.setInteresados(interesados);
      InformacionDestino informacionDestino = objectFactory.createInformacionDestino();
      // OJO HAY QUE VER QUE SE METE EN ESTE PARAMETRO
      informacionDestino.setCodigo("101001");
// OJO HAY QUE VER QUE SE METE EN ESTE PARAMETRO
      informacionDestino.setDescripcion("DGT - Vehiculos");
      datosGenericos.setDestino(informacionDestino);
  //    datosGenericos.setNumeroExpediente(arrendatarioVO.getNumExpediente().toString());
      
  //    datosGenericos.setMatricula(arrendatarioVO.getMatricula());
  //    datosGenericos.setBastidor(arrendatarioVO.getBastidor().substring(longitud - 6, longitud));
      // Datos Especificos
      
      // Documentos
//      Documentos documentos = objectFactory.createDocumentos();
//      InformacionDocumento documento = objectFactory.createInformacionDocumento();
// OJO HAY QUE VER QUE SE METE EN ESTE PARAMETRO      
//      documento.setCodigo("123");
//      documento.setHash("123456789012345678901234567890");
//      documento.setNombre("A+.B+");
      
      
  //    documentos.getDocumento().add(documento);
      datosFirmados.setDatosGenericos(datosGenericos);
      datosFirmados.setDatosEspecificos(datosEspecificos);
     // datosFirmados.setDocumentos(documentos);
      solicitud.setDatosFirmados(datosFirmados);
      resultado.setSolicitudRegistroEntrada(solicitud);
    } catch (Exception e) {
      log.error("Ha sucedido un error a la hora de rellenar el objeto de la solicitud de arrendatario, error: ", e);
      resultado.setMensaje("Ha sucedido un error a la hora de rellenar el objeto de la solicitud de arrendatario.");
      resultado.setError(Boolean.TRUE);
    }
    return resultado;
  }

	private DatosEspecificos rellenarDatosEspecificos(ArrendatarioVO arrendatarioVO, ObjectFactory objectFactory) {
		int longitud = arrendatarioVO.getBastidor().length();
		DatosEspecificos datosEspecificos = objectFactory.createDatosEspecificos();
	    datosEspecificos.setMatricula(arrendatarioVO.getMatricula());
	    datosEspecificos.setBastidor(arrendatarioVO.getBastidor().substring(longitud - 6, longitud));
		return datosEspecificos;
	}

	private DatosGenericos rellenarDatosGenericos(ArrendatarioVO arrendatarioVO, ObjectFactory objectFactory) {
		DatosGenericos datosGenericos = objectFactory.createDatosGenericos();
	    datosGenericos.setRemitente(rellenarRemitente(arrendatarioVO,objectFactory));
	    datosGenericos.setInteresados(rellenarInteresados(arrendatarioVO,objectFactory));
		return datosGenericos;
	}

	private Interesados rellenarInteresados(ArrendatarioVO arrendatarioVO, ObjectFactory objectFactory) {
		Interesados interesados = objectFactory.createInteresados();
	    InformacionPersona interesadoPersona = objectFactory.createInformacionPersona();
	    int coma = arrendatarioVO.getContrato().getColegiado().getUsuario().getApellidosNombre().indexOf(",");
	    String nombreColegiado = arrendatarioVO.getContrato().getColegiado().getUsuario().getApellidosNombre()
	          .substring(coma + 1, arrendatarioVO.getContrato().getColegiado().getUsuario().getApellidosNombre().length());
	    String apeColegiado = arrendatarioVO.getContrato().getColegiado().getUsuario().getApellidosNombre().substring(0,
	          coma);
	    interesadoPersona.setNombre(nombreColegiado);
	    interesadoPersona.setApellidos(apeColegiado);
	    interesadoPersona.setCorreoElectronico(objectFactory.createInformacionPersonaCorreoElectronico(arrendatarioVO.getContrato().getCodPostal()));
	    
	    
	    DocumentoIdentificacion docIdentInteresado = objectFactory.createInformacionPersonaDocumentoIdentificacion();
	      docIdentInteresado.setNumero(arrendatarioVO.getContrato().getColegiado().getUsuario().getNif());
	// OJO HAY QUE VER QUE SE METE EN ESTE PARAMETRO
	      JAXBElement<Integer> tipointeresado = objectFactory.createInformacionPersonaDocumentoIdentificacionTipo(1);
	      docIdentInteresado.setTipo(tipointeresado);
	      
	     interesadoPersona.setDocumentoIdentificacion(docIdentInteresado);
	     interesados.getInteresado().add(interesadoPersona);
		return interesados;
	}

	private InformacionPersona rellenarRemitente(ArrendatarioVO arrendatarioVO, ObjectFactory objectFactory) {
        InformacionPersona informacionPersona = objectFactory.createInformacionPersona();
        informacionPersona.setNombre(arrendatarioVO.getContrato().getColegio().getNombre());
        informacionPersona.setApellidos(CADENA_VACIA);
        informacionPersona.setCorreoElectronico(objectFactory.createInformacionPersonaCorreoElectronico(arrendatarioVO.getContrato().getColegio().getCorreoElectronico()));
        DocumentoIdentificacion documentoIdentificacion = objectFactory.createInformacionPersonaDocumentoIdentificacion();
        documentoIdentificacion.setNumero(arrendatarioVO.getContrato().getColegio().getCif());
        informacionPersona.setDocumentoIdentificacion(documentoIdentificacion);
		return informacionPersona;
	}

	public ResultConsultaArrendatarioBean validarXml(SolicitudRegistroEntrada solicitudRegistroEntrada) {
		ResultConsultaArrendatarioBean resultado = new ResultConsultaArrendatarioBean(Boolean.FALSE);
		try {
			XmlArrendatarioFactory xmlFactory = new XmlArrendatarioFactory();
			Marshaller marshaller = (Marshaller) xmlFactory.getContext().createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(solicitudRegistroEntrada, new FileOutputStream("prueba.xml"));
			File fichero = new File("prueba.xml");
			try {
				xmlFactory.validarXMLArrendatario(fichero);
			} catch (OegamExcepcion e) {
				if( e.getMensajeError1().contains("{")){
					resultado.setMensaje("Error en la validacion del expediente, faltan los siguientes campos: " +  e.getMensajeError1().substring( e.getMensajeError1().indexOf("{") + 1, e.getMensajeError1().indexOf("}")));
				} else {
					resultado.setMensaje("Error en la validacion del expediente: " +  e.getMensajeError1());
				}
				resultado.setError(Boolean.TRUE);
			}
			try{
				fichero.delete();
			}catch(Exception ee){
				log.error("Ha sucedido un error a la hora de eliminar el fichero temporal de la validacion contra el xsd de Interfaz_Arrendatario, error: ", ee);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el xml contra el xsd de validación, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de validar el xml contra el xsd de validación");
		}
		return resultado;
	}

	public ResultConsultaArrendatarioBean validarXml(String xml) {
		
		
		ResultConsultaArrendatarioBean resultado = new ResultConsultaArrendatarioBean(Boolean.FALSE);
		try {
			XmlArrendatarioFactory xmlFactory = new XmlArrendatarioFactory();
			
			try {
				xmlFactory.validarXMLArrendatario(xml);
			} catch (OegamExcepcion e) {
				if( e.getMensajeError1().contains("{")){
					resultado.setMensaje("Error en la validacion del expediente, faltan los siguientes campos: " +  e.getMensajeError1().substring( e.getMensajeError1().indexOf("{") + 1, e.getMensajeError1().indexOf("}")));
				} else {
					resultado.setMensaje("Error en la validacion del expediente: " +  e.getMensajeError1());
				}
				resultado.setError(Boolean.TRUE);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el xml contra el xsd de validación, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de validar el xml contra el xsd de validación");
		}
		return resultado;
	}

	public ResultConsultaArrendatarioBean firmarXml(SolicitudRegistroEntrada solicitudRegistroEntrada, String alias) {
		ResultConsultaArrendatarioBean resultado = new ResultConsultaArrendatarioBean(Boolean.FALSE);
		try {
			UtilesViafirma utilesViafirma = new UtilesViafirma();
			//String xml = xmlFactory.toXML(solicitudRegistroEntrada);
			
			
			String xml = null;
			
			try {
				StringWriter writer = new StringWriter();			
				Marshaller m =JAXBContext.newInstance("org.gestoresmadrid.oegam2comun.arrendatarios.view.xml").createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
				m.marshal(solicitudRegistroEntrada, writer);
				writer.flush();		
				
				xml = writer.toString();
			} catch(Exception e) {
				log.error("Error al generar XML del objeto SolicitudRegistroEntrada");
				log.error(e);
			}	
			
			
			
			String valCertificado = utilesViafirma.firmarPruebaCertificadoCaducidad(xml, alias);
			if(valCertificado == null || valCertificado.isEmpty()){
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje(HA_OCURRIDO_UN_ERROR_COMPROBANDO_LA_CADUCIDAD_DEL_CERTIFICADO);
			} else {
				byte[] bXmlFirmado = utilesViafirma.firmarXmlPorAlias(xml.getBytes("UTF-8"), alias);
				if(bXmlFirmado != null){
					resultado.setXml(bXmlFirmado);
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Ha sucedido un error a la hora de firmar la solicitud.");
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de firmar la solicitud, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de firmar la solicitud.");
		}
		return resultado;
	}


}
