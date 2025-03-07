package org.gestoresmadrid.oegam2comun.trafico.tramitar.build;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;

import javax.xml.bind.Marshaller;

import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ResultadoDistintivoDgtBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.utiles.XmlPermisoDistintivoItvFactory;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.xml.CategoriaElectrica;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.xml.Jefatura;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.xml.ObjectFactory;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.xml.SolicitudImpresionDocumentos;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.xml.SolicitudImpresionDocumentos.DatosFirmados;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.xml.SolicitudImpresionDocumentos.DatosFirmados.DatosEspecificos;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.xml.SolicitudImpresionDocumentos.DatosFirmados.DatosEspecificos.DatosDistintivo;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.xml.SolicitudImpresionDocumentos.DatosFirmados.DatosEspecificos.DatosPermiso;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.xml.SolicitudImpresionDocumentos.DatosFirmados.DatosGenericos;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.xml.SolicitudImpresionDocumentos.DatosFirmados.DatosGenericos.Interesado;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.xml.SolicitudImpresionDocumentos.DatosFirmados.DatosGenericos.Remitente;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.xml.TipoDatoTitular;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.xml.VehiclePurpose;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegamComun.colegio.view.dto.ColegioDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.validaciones.NIFValidator;
import utilidades.web.OegamExcepcion;
import viafirma.utilidades.UtilesViafirma;

@Component
public class BuildXmlMatwDistintivo implements Serializable{

	private static final long serialVersionUID = -902923795498138368L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(BuildXmlMatwDistintivo.class);

	public static final String TEXTO_LEGAL = "Este Colegio de Gestores Administrativos ha verificado que la solicitud presentada cumple todas las obligaciones establecidas en el Reglamento General de Vehículos y resto de normativa aplicable, así como las derivadas de la aplicación de las Instrucciones de la Dirección General de Tráfico vigentes en el momento de la solicitud.";
	public static final String HA_OCURRIDO_UN_ERROR_COMPROBANDO_LA_CADUCIDAD_DEL_CERTIFICADO = "Ha ocurrido un error comprobando la caducidad del certificado";

	@Autowired
	ServicioVehiculo servicioVehiculo;

	@Autowired
	ServicioPersona servicioPersona;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	public SolicitudImpresionDocumentos generarSolicitudImpresion(TramiteTrafMatrVO tramiteTrafMatr, ColegioDto colegio) throws ParseException {
		ObjectFactory objectFactory = new ObjectFactory();
		PersonaDto colegiadoCompleto = servicioPersona.obtenerColegiadoCompleto(tramiteTrafMatr.getNumColegiado(), new BigDecimal(tramiteTrafMatr.getContrato().getIdContrato()));
		SolicitudImpresionDocumentos solicitudImpresionDocumentos = objectFactory.createSolicitudImpresionDocumentos();
		DatosFirmados datosFirmados = objectFactory.createSolicitudImpresionDocumentosDatosFirmados();
		datosFirmados.setDatosEspecificos(createDatosEspecificos(tramiteTrafMatr, objectFactory));
		datosFirmados.setDatosGenericos(createDatosGenericos(colegio, colegiadoCompleto,objectFactory));
		solicitudImpresionDocumentos.setDatosFirmados(datosFirmados);
		return solicitudImpresionDocumentos;
	}

	public ResultadoDistintivoDgtBean validarXml(SolicitudImpresionDocumentos solicitudImpresionDocumentos) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			XmlPermisoDistintivoItvFactory xmlFactory = new XmlPermisoDistintivoItvFactory();
			Marshaller marshaller = (Marshaller) xmlFactory.getContext().createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(solicitudImpresionDocumentos, new FileOutputStream("prueba.xml"));
			// Lo validamos contra el XSD
			File fichero = new File("prueba.xml");
			try {
				xmlFactory.validarXML(fichero);
			} catch (OegamExcepcion e) {
				if( e.getMensajeError1().contains("{")){
					resultado.setMensaje("Error en la validacion del expediente, faltan los siguientes campos: " +  e.getMensajeError1().substring( e.getMensajeError1().indexOf("{") + 1, e.getMensajeError1().indexOf("}")));
				} else {
					resultado.setMensaje("Error en la validacion del expediente: " +  e.getMensajeError1());
				}
				resultado.setError(true);
			}
			try{
				fichero.delete();
			}catch(Exception ee){
				log.error("Ha sucedido un error a la hora de eliminar el fichero temporal de la validacion contra el xsd de Matw_Impresion_Permiso_Distintivo, error: ", ee);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el xml con el xsd de validación, error: ", e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de validar el xml con el xsd de validación");
		}

		return resultado;
	}

	public ResultadoDistintivoDgtBean firmarXml(SolicitudImpresionDocumentos solicitudImpresionDocumentos) throws UnsupportedEncodingException {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		XmlPermisoDistintivoItvFactory xmlFactory = new XmlPermisoDistintivoItvFactory();
		String xml = xmlFactory.toXML(solicitudImpresionDocumentos);
		UtilesViafirma utilesViafirma = new UtilesViafirma();
		String aliasColegio = gestorPropiedades.valorPropertie("trafico.claves.colegio.alias");
		String valCertificado = utilesViafirma.firmarPruebaCertificadoCaducidad(xml, aliasColegio);
		if(valCertificado == null || valCertificado.isEmpty()){
			log.error(HA_OCURRIDO_UN_ERROR_COMPROBANDO_LA_CADUCIDAD_DEL_CERTIFICADO);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(HA_OCURRIDO_UN_ERROR_COMPROBANDO_LA_CADUCIDAD_DEL_CERTIFICADO);
		}else{
			byte[] bXmlFirmado = utilesViafirma.firmarXmlPorAlias(xml.getBytes("UTF-8"), aliasColegio);
			if(bXmlFirmado != null){
				resultado.setXml(bXmlFirmado);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Ha sucedido un error a la hora de firmar la solicitud.");
			}
		}
		return resultado;
	}

	private DatosGenericos createDatosGenericos(ColegioDto colegioDto, PersonaDto colegiado, ObjectFactory objectFactory) {
		DatosGenericos datosGenericos = objectFactory.createSolicitudImpresionDocumentosDatosFirmadosDatosGenericos();
		datosGenericos.setInteresado(createDatosInteresado(colegiado, objectFactory));
		datosGenericos.setRemitente(createDatosRemitente(colegioDto, objectFactory));
		return datosGenericos;
	}

	private Remitente createDatosRemitente(ColegioDto colegioDto, ObjectFactory objectFactory) {
		Remitente remitente = objectFactory.createSolicitudImpresionDocumentosDatosFirmadosDatosGenericosRemitente();
		remitente.setNumeroDocumentoIdentidad(colegioDto.getCif());
		return remitente;
	}

	private Interesado createDatosInteresado(PersonaDto colegiado, ObjectFactory objectFactory) {
		Interesado interesado = objectFactory.createSolicitudImpresionDocumentosDatosFirmadosDatosGenericosInteresado();
		interesado.setNumeroDocumentoIdentidad(colegiado.getNif());
		return interesado;
	}

	private DatosEspecificos createDatosEspecificos(TramiteTrafMatrVO tramiteTrafMatr, ObjectFactory objectFactory) throws ParseException {
		DatosEspecificos datosEspecificos = objectFactory.createSolicitudImpresionDocumentosDatosFirmadosDatosEspecificos();
		if(tramiteTrafMatr.getVehiculo().getAutonomiaElectrica() != null){
			datosEspecificos.setAutonomiaElectrica(tramiteTrafMatr.getVehiculo().getAutonomiaElectrica().intValue());
		}
		if(tramiteTrafMatr.getVehiculo().getCategoriaElectrica() != null && !tramiteTrafMatr.getVehiculo().getCategoriaElectrica().isEmpty()){
			datosEspecificos.setCategoriaElectrica(CategoriaElectrica.fromValue(tramiteTrafMatr.getVehiculo().getCategoriaElectrica()));
		}
		DatosDistintivo datosDistintivo = objectFactory.createSolicitudImpresionDocumentosDatosFirmadosDatosEspecificosDatosDistintivo();
		datosDistintivo.setCategoriaEu(tramiteTrafMatr.getVehiculo().getIdDirectivaCee());
		datosDistintivo.setNivelEmisiones(tramiteTrafMatr.getVehiculo().getNivelEmisiones());
		datosDistintivo.setPotenciaFiscal(tramiteTrafMatr.getVehiculo().getPotenciaFiscal());
		datosEspecificos.setDatosDistintivo(datosDistintivo);

		IntervinienteTraficoVO titular = null;
		if(tramiteTrafMatr.getIntervinienteTraficos() != null && !tramiteTrafMatr.getIntervinienteTraficos().isEmpty()){
			for(IntervinienteTraficoVO intervinienteTraf : tramiteTrafMatr.getIntervinienteTraficos()){
				if(TipoInterviniente.Titular.getValorEnum().equals(intervinienteTraf.getTipoIntervinienteVO().getTipoInterviniente())){
					titular = intervinienteTraf;
					break;
				}
			}
		}

		datosEspecificos.setDatosPermiso(createDatosPermiso(tramiteTrafMatr.getVehiculo(),titular, objectFactory));
		if(tramiteTrafMatr.getVehiculo().getFechaMatriculacion() != null && tramiteTrafMatr.getVehiculo().getFechaMatriculacion() != null){
			datosEspecificos.setFechaMatriculacion(utilesFecha.formatoFecha("yyyyMMdd",tramiteTrafMatr.getVehiculo().getFechaMatriculacion()));
		}
		if(tramiteTrafMatr.getDistintivo() != null && tramiteTrafMatr.getDistintivo().isEmpty()){
			datosEspecificos.setImprimirDistintivo("N");
		} else {
			datosEspecificos.setImprimirDistintivo("S");
		}
		datosEspecificos.setImprimirPermiso("N");

		if(tramiteTrafMatr.getJefaturaTrafico() != null && tramiteTrafMatr.getJefaturaTrafico().getJefatura() != null 
				&& !tramiteTrafMatr.getJefaturaTrafico().getJefatura().isEmpty()){
			datosEspecificos.setJefatura(Jefatura.fromValue(tramiteTrafMatr.getJefaturaTrafico().getJefatura()));
		}
		String nombreMarca = servicioVehiculo.obtenerNombreMarca(tramiteTrafMatr.getVehiculo().getCodigoMarca(), true);
		if(nombreMarca != null && !nombreMarca.isEmpty()){
			datosEspecificos.setMarca(nombreMarca);
		}
		datosEspecificos.setMatricula(tramiteTrafMatr.getVehiculo().getMatricula());
		datosEspecificos.setModelo(tramiteTrafMatr.getVehiculo().getModelo());

		String sucursal = tramiteTrafMatr.getJefaturaTrafico().getSucursal();
		if (!"1".equals(sucursal)) {
			String activarSucursal = gestorPropiedades.valorPropertie("activar.sucursal.alcala.matw.xml");
			if ("2".equals(sucursal) && "SI".equals(activarSucursal)) {
				datosEspecificos.setSucursal(sucursal);
			} else {
				datosEspecificos.setSucursal("");
			}
		} else {
			datosEspecificos.setSucursal(sucursal);
		}

		datosEspecificos.setTextoLegal(TEXTO_LEGAL);
		datosEspecificos.setTipoCarburante(tramiteTrafMatr.getVehiculo().getIdCarburante());

		return datosEspecificos;
	}

	private DatosPermiso createDatosPermiso(VehiculoVO vehiculo, IntervinienteTraficoVO titular, ObjectFactory objectFactory) throws ParseException {
		DatosPermiso datosPermiso = objectFactory.createSolicitudImpresionDocumentosDatosFirmadosDatosEspecificosDatosPermiso();
		datosPermiso.setBastidor(vehiculo.getBastidor());
		if(vehiculo.getCilindrada() != null && !vehiculo.getCilindrada().isEmpty()){
			datosPermiso.setCilindrada(new BigDecimal(vehiculo.getCilindrada()));
		}
		if(vehiculo.getConsumo() != null){
			datosPermiso.setConsumoElectrico(vehiculo.getConsumo().intValue());
		}
		//datosPermiso.setDiasValidez(new Integer(15));
		//datosPermiso.setFechaLecturaKilometraje();
		if(vehiculo.getFechaItv() != null){
			datosPermiso.setFechaValidezITV(utilesFecha.formatoFecha("yyyyMMdd",vehiculo.getFechaItv()));
		}
		if(vehiculo.getKmUso() != null){
			datosPermiso.setKilometraje(vehiculo.getKmUso().intValue());
		}
		if(vehiculo.getMom() != null){
			datosPermiso.setMasaEnServicio(vehiculo.getMom().intValue());
		}
		if(vehiculo.getPesoMma() != null && !vehiculo.getPesoMma().isEmpty()){
			datosPermiso.setMasaMaximaAutorizada(new Integer(vehiculo.getPesoMma()));
		}
		if(vehiculo.getMtmaItv() != null && !vehiculo.getMtmaItv().isEmpty()){
			datosPermiso.setMasaTecnicaAdmisible(new Integer(vehiculo.getMtmaItv()));
		}
		datosPermiso.setNive(vehiculo.getNive());
		datosPermiso.setNumeroHomologacion(vehiculo.getnHomologacion());
		if(vehiculo.getPlazas() != null){
		datosPermiso.setNumeroMaximoPlazas(vehiculo.getPlazas().intValue());
		}
		if(vehiculo.getnPlazasPie() != null){
			datosPermiso.setNumeroPlazasDePie(vehiculo.getnPlazasPie().intValue());
		}
		datosPermiso.setPotenciaNetaMaxima(vehiculo.getPotenciaNeta());
		datosPermiso.setRelacionPotenciaPeso(vehiculo.getPotenciaPeso());
		if(vehiculo.getIdServicio() != null && !vehiculo.getIdServicio().isEmpty()){
			datosPermiso.setServicio(VehiclePurpose.fromValue(vehiculo.getIdServicio()));
		}
		datosPermiso.setTipo(vehiculo.getTipoBase());
		TipoDatoTitular tipoTitular = objectFactory.createTipoDatoTitular();
		int tipo = NIFValidator.isValidDniNieCif(titular.getPersona().getId().getNif().toUpperCase());
		int tamTotal = 0;
		if(NIFValidator.JURIDICA_PRIVADA == tipo || NIFValidator.JURIDICA_PUBLICA == tipo){
			tamTotal = titular.getPersona().getApellido1RazonSocial().length();
			tipoTitular.setRazonSocial(titular.getPersona().getApellido1RazonSocial().substring(0, tamTotal > 70 ? 70 : tamTotal));
			tipoTitular.setSegundoApellido("");
		} else {
			tamTotal = titular.getPersona().getNombre().length();
			tipoTitular.setNombre(titular.getPersona().getNombre().substring(0, tamTotal > 20 ? 20 : tamTotal));
			tamTotal = titular.getPersona().getApellido1RazonSocial().length();
			tipoTitular.setPrimerApellido(titular.getPersona().getApellido1RazonSocial().substring(0, tamTotal > 20 ? 20 : tamTotal));
			if( titular.getPersona().getApellido2() != null && ! titular.getPersona().getApellido2().isEmpty()){
				tamTotal = titular.getPersona().getApellido2().length();
				tipoTitular.setSegundoApellido(titular.getPersona().getApellido2().substring(0, tamTotal > 20 ? 20 : tamTotal));
			}
		}
		datosPermiso.setTitular(tipoTitular);
		datosPermiso.setVariante(vehiculo.getVariante());
		datosPermiso.setVersion(vehiculo.getVersion());
		return datosPermiso;
	}

}