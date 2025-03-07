package org.gestoresmadrid.oegam2comun.conductor.model.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;

import org.gestoresmadrid.core.conductor.model.vo.ConductorVO;
import org.gestoresmadrid.oegam2comun.conductor.view.beans.ResultConsultaConductorBean;
import org.gestoresmadrid.oegam2comun.conductor.view.xml.DatosEspecificos;
import org.gestoresmadrid.oegam2comun.conductor.view.xml.DatosFirmados;
import org.gestoresmadrid.oegam2comun.conductor.view.xml.DatosGenericos;
import org.gestoresmadrid.oegam2comun.conductor.view.xml.Documentos;
import org.gestoresmadrid.oegam2comun.conductor.view.xml.InformacionAsunto;
import org.gestoresmadrid.oegam2comun.conductor.view.xml.InformacionDestino;
import org.gestoresmadrid.oegam2comun.conductor.view.xml.InformacionPersona;
import org.gestoresmadrid.oegam2comun.conductor.view.xml.Interesados;
import org.gestoresmadrid.oegam2comun.conductor.view.xml.ObjectFactory;
import org.gestoresmadrid.oegam2comun.conductor.view.xml.XmlConductorFactory;
import org.springframework.stereotype.Component;

import utilidades.web.OegamExcepcion;
import viafirma.utilidades.UtilesViafirma;

@Component
public class BuildConductorHabitual implements Serializable {

	private static final long serialVersionUID = 3817163425015151276L;
	private static final String CADENA_VACIA = "";

	public ResultConsultaConductorBean generarXml(ConductorVO conductorVO) {
		ResultConsultaConductorBean resultado = new ResultConsultaConductorBean(Boolean.FALSE);
		try {
			ObjectFactory objectFactory = new ObjectFactory();
			// Solicitud Registro Entrada
			org.gestoresmadrid.oegam2comun.conductor.view.xml.SolicitudRegistroEntrada solicitud = objectFactory
					.createSolicitudRegistroEntrada();
			// Datos Firmados
			DatosFirmados datosFirmados = objectFactory.createDatosFirmados();

			// Datos Genéricos
			DatosGenericos datosGenericos = objectFactory.createDatosGenericos();

			// Información Persona
			InformacionPersona informacionPersona = objectFactory.createInformacionPersona();
			informacionPersona.setNombre(conductorVO.getContrato().getColegio().getNombre());

			informacionPersona.setApellidos(CADENA_VACIA);

			JAXBElement<String> correoElectronico = null;
			try {
				if (!conductorVO.getPersona().getCorreoElectronico().isEmpty()) {
					correoElectronico = objectFactory
							.createInformacionPersonaCorreoElectronico(conductorVO.getPersona().getCorreoElectronico());
					informacionPersona.setCorreoElectronico(correoElectronico);
				}
			} catch (Exception e) {
			}

			InformacionPersona.DocumentoIdentificacion documentoIdentificacion = objectFactory
					.createInformacionPersonaDocumentoIdentificacion();
			documentoIdentificacion.setNumero(conductorVO.getContrato().getColegio().getCif());
			// OJO HAY QUE VER QUE SE METE EN ESTE PARÁMETRO
			JAXBElement<Integer> tipo = objectFactory.createInformacionPersonaDocumentoIdentificacionTipo(1);
			documentoIdentificacion.setTipo(tipo);
			informacionPersona.setDocumentoIdentificacion(documentoIdentificacion);
			datosGenericos.setRemitente(informacionPersona);
			InformacionAsunto informacionAsunto = objectFactory.createInformacionAsunto();
			informacionAsunto.setCodigo("SAA");
			informacionAsunto.setDescripcion("Solicitud de Anotacion de Conductor Habitual");
			datosGenericos.setAsunto(informacionAsunto);
			Interesados interesados = objectFactory.createInteresados();
			InformacionPersona interesadoPersona = objectFactory.createInformacionPersona();
			int coma = conductorVO.getContrato().getColegiado().getUsuario().getApellidosNombre().indexOf(",");
			String nombreColegiado = conductorVO.getContrato().getColegiado().getUsuario().getApellidosNombre()
					.substring(coma + 1,
							conductorVO.getContrato().getColegiado().getUsuario().getApellidosNombre().length());
			String apeColegiado = conductorVO.getContrato().getColegiado().getUsuario().getApellidosNombre()
					.substring(0, coma);
			interesadoPersona.setNombre(nombreColegiado);
			interesadoPersona.setApellidos(apeColegiado);

			JAXBElement<String> correoElectronicoInteresado = objectFactory.createInformacionPersonaCorreoElectronico(
					conductorVO.getContrato().getCorreoElectronico().toString());
			interesadoPersona.setCorreoElectronico(correoElectronicoInteresado);
			InformacionPersona.DocumentoIdentificacion docIdentInteresado = objectFactory
					.createInformacionPersonaDocumentoIdentificacion();
			docIdentInteresado.setNumero(conductorVO.getContrato().getColegiado().getUsuario().getNif());
			// OJO HAY QUE VER QUE SE METE EN ESTE PARÁMETRO
			JAXBElement<Integer> tipointeresado = objectFactory.createInformacionPersonaDocumentoIdentificacionTipo(1);
			docIdentInteresado.setTipo(tipointeresado);
			interesadoPersona.setDocumentoIdentificacion(docIdentInteresado);
			interesados.getInteresado().add(interesadoPersona);
			datosGenericos.setInteresados(interesados);
			InformacionDestino informacionDestino = objectFactory.createInformacionDestino();
			// OJO HAY QUE VER QUE SE METE EN ESTE PARÁMETRO
			informacionDestino.setCodigo("101001");
			// OJO HAY QUE VER QUE SE METE EN ESTE PARÁMETRO
			informacionDestino.setDescripcion("DGT - Vehiculos");
			datosGenericos.setDestino(informacionDestino);

			int longitud = conductorVO.getBastidor().length();

			// Datos Específicos
			DatosEspecificos datosEspecificos = objectFactory.createDatosEspecificos();
			datosEspecificos.setMatricula(conductorVO.getMatricula());
			datosEspecificos.setBastidor(conductorVO.getBastidor().substring(longitud - 6, longitud));
			// Documentos
			Documentos documentos = objectFactory.createDocumentos();
//			InformacionDocumento documento = objectFactory.createInformacionDocumento();

			// OJO HAY QUE VER QUE SE METE EN ESTE PARÁMETRO
//			documento.setCodigo("123");
//			documento.setHash("123456789012345678901234567890");
//			documento.setNombre("A+.B+");

			datosFirmados.setDatosGenericos(datosGenericos);
			datosFirmados.setDatosEspecificos(datosEspecificos);

			solicitud.setDatosFirmados(datosFirmados);
			resultado.setSolicitudRegistroEntrada(solicitud);
		} catch (Exception e) {
			resultado.setMensaje("Ha sucedido un error a la hora de rellenar el objeto de la solicitud de conductor.");
			resultado.setError(Boolean.TRUE);
		}
		return resultado;
	}

	public ResultConsultaConductorBean validarXml(
			org.gestoresmadrid.oegam2comun.conductor.view.xml.SolicitudRegistroEntrada solicitudRegistroEntrada) {
		ResultConsultaConductorBean resultado = new ResultConsultaConductorBean(Boolean.FALSE);
		try {
			XmlConductorFactory xmlFactory = new XmlConductorFactory();
			Marshaller marshaller = (Marshaller) xmlFactory.getContext().createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(solicitudRegistroEntrada, new FileOutputStream("prueba.xml"));
			File fichero = new File("prueba.xml");
			try {
				xmlFactory.validarXMLConductor(fichero);
			} catch (OegamExcepcion e) {
				if (e.getMensajeError1().contains("{")) {
					resultado.setMensaje("Error en la validacion del expediente, faltan los siguientes campos: "
							+ e.getMensajeError1().substring(e.getMensajeError1().indexOf("{") + 1,
									e.getMensajeError1().indexOf("}")));
				} else {
					resultado.setMensaje("Error en la validacion del expediente: " + e.getMensajeError1());
				}
				resultado.setError(Boolean.TRUE);
			}
			try {
				fichero.delete();
			} catch (Exception ee) {
			}
		} catch (Exception e) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de validar el xml contra el xsd de validación");
		}
		return resultado;
	}

	public ResultConsultaConductorBean firmarXml(
			org.gestoresmadrid.oegam2comun.conductor.view.xml.SolicitudRegistroEntrada solicitudRegistroEntrada,
			String alias) {
		ResultConsultaConductorBean resultado = new ResultConsultaConductorBean(Boolean.FALSE);
		try {
			UtilesViafirma utilesViafirma = new UtilesViafirma();

			String xml = null;

			try {
				StringWriter writer = new StringWriter();
				Marshaller m = JAXBContext.newInstance("org.gestoresmadrid.oegam2comun.conductor.view.xml")
						.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
				m.marshal(solicitudRegistroEntrada, writer);
				writer.flush();

				xml = writer.toString();
			} catch (Exception e) {
				e.getMessage();
			}

			String valCertificado = utilesViafirma.firmarPruebaCertificadoCaducidad(xml, alias);
			if (valCertificado == null || valCertificado.isEmpty()) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("HA_OCURRIDO_UN_ERROR_COMPROBANDO_LA_CADUCIDAD_DEL_CERTIFICADO");
			} else {
				byte[] bXmlFirmado = utilesViafirma.firmarXmlPorAlias(xml.getBytes(StandardCharsets.UTF_8), alias);
				if (bXmlFirmado != null) {
					resultado.setXml(bXmlFirmado);
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Ha sucedido un error a la hora de firmar la solicitud.");
				}
			}
		} catch (Exception e) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de firmar la solicitud.");
		}
		return resultado;
	}

}