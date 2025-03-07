package org.gestoresmadrid.oegam2comun.trafico.tramitar.build;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.gestoresmadrid.core.direccion.model.vo.TipoViaVO;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteMatriculacion;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.oegam2comun.colegio.model.service.ServicioColegio;
import org.gestoresmadrid.oegam2comun.conversion.Conversiones;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioTipoVia;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioJefaturaTrafico;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafMatrDto;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegamComun.colegio.view.dto.ColegioDto;
import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.JefaturaTraficoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import colas.constantes.ConstantesProcesos;
import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import general.utiles.BasicText;
import general.utiles.UtilesCadenaCaracteres;
import trafico.beans.jaxb.matriculacion.TipoSINO;
import trafico.beans.jaxb.matw.TipoInspeccion;
import trafico.beans.jaxb.matw.dgt.tipos.TipoCambioDomicilio;
import trafico.beans.jaxb.matw.dgt.tipos.TipoDatoAsuntoMATG;
import trafico.beans.jaxb.matw.dgt.tipos.TipoDatoColegio;
import trafico.beans.jaxb.matw.dgt.tipos.TipoDatoColegio.DocumentoIdentificacion;
import trafico.beans.jaxb.matw.dgt.tipos.TipoDatoConductor;
import trafico.beans.jaxb.matw.dgt.tipos.TipoDatoDestino;
import trafico.beans.jaxb.matw.dgt.tipos.TipoDatoDireccion;
import trafico.beans.jaxb.matw.dgt.tipos.TipoDatoGestoria;
import trafico.beans.jaxb.matw.dgt.tipos.TipoDatoNombre;
import trafico.beans.jaxb.matw.dgt.tipos.TipoDatoTitular;
import trafico.beans.jaxb.matw.dgt.tipos.TipoDocumentoIdentificacion;
import trafico.beans.jaxb.matw.dgt.tipos.TipoSN;
import trafico.beans.jaxb.matw.dgt.tipos.complejos.DatosEspecificos;
import trafico.beans.jaxb.matw.dgt.tipos.complejos.DatosEspecificos.Arrendatario;
import trafico.beans.jaxb.matw.dgt.tipos.complejos.DatosEspecificos.ConductorHabitual;
import trafico.beans.jaxb.matw.dgt.tipos.complejos.DatosEspecificos.DatosTecnicos;
import trafico.beans.jaxb.matw.dgt.tipos.complejos.DatosEspecificos.DatosTecnicos.Caracteristicas;
import trafico.beans.jaxb.matw.dgt.tipos.complejos.DatosEspecificos.DatosVehiculo;
import trafico.beans.jaxb.matw.dgt.tipos.complejos.DatosEspecificos.Impuestos;
import trafico.beans.jaxb.matw.dgt.tipos.complejos.DatosEspecificos.Titular;
import trafico.beans.jaxb.matw.dgt.tipos.complejos.DatosEspecificos.Tutor;
import trafico.beans.jaxb.matw.dgt.tipos.complejos.DatosGenericos;
import trafico.beans.jaxb.matw.dgt.tipos.complejos.DatosGenericos.Interesados;
import trafico.beans.jaxb.matw.dgt.tipos.complejos.Documentos;
import trafico.beans.jaxb.matw.dgt.tipos.complejos.ObjectFactory;
import trafico.beans.jaxb.matw.dgt.tipos.complejos.SolicitudRegistroEntrada;
import trafico.beans.jaxb.matw.dgt.tipos.complejos.TipoVehiculoDGT;
import trafico.beans.jaxb.pruebaCertificado.DatosFirmados;
import trafico.beans.jaxb.pruebaCertificado.SolicitudPruebaCertificado;
import trafico.utiles.UtilesConversiones;
import trafico.utiles.XMLMatwFactory;
import trafico.utiles.enumerados.TipoTutela;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import viafirma.utilidades.UtilesViafirma;

@Component
public class BuildMatriculacion implements Serializable {

	private static final long serialVersionUID = 2902218536085049120L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(BuildMatriculacion.class);

	private static final String VERSION_REGISTRO_ENTRADA = "3.0";

	private static final String VERSION = "1.1";

	private static final String ORGANISMO = "DGT";

	private static final String PAIS = "ESP";

	private static final String CODIGO_ASUNTO = "MATW";
	private static final String DESCRIPCION_ASUNTO = "Solicitud de Matriculación Online para Gestores con Datos Técnicos";
	private static final String CODIGO_DESTINO = "101001";
	private static final String DESCRIPCION_DESTINO = "DGT - Vehículos";

	private static final String _0 = "0";
	private static int _18 = 18;
	private static int _45 = 45;

	private static final String SIN_CODIG = "SINCODIG";

	private static final String ERROR = "ERROR";
	private static final String ERROR_AL_OBTENER_BYTES_UTF_8 = "Error al obtener bytes UTF-8";
	private static final String ERROR_AL_FIRMAR_EL_COLEGIO = "Error al firmar el Colegio";
	private static final String HA_OCURRIDO_UN_ERROR_RECUPERANDO_EL_XML_FIRMADO_A_TRAVES_DEL_ID_DE_FIRMA = "Ha ocurrido un error recuperando el xml firmado a traves del id de firma";
	private static final String HA_OCURRIDO_UN_ERROR_COMPROBANDO_LA_CADUCIDAD_DEL_CERTIFICADO = "Ha ocurrido un error comprobando la caducidad del certificado";

	private static final String XML_VALIDO = "CORRECTO";

	@Autowired
	ServicioPersona servicioPersona;

	@Autowired
	ServicioColegio servicioColegio;

	@Autowired
	ServicioJefaturaTrafico servicioJefaturaTrafico;

	@Autowired
	ServicioTipoVia servicioTipoVia;

	@Autowired
	ServicioVehiculo servicioVehiculo;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	Conversiones conversiones;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilesConversiones utilesConversiones;

	public SolicitudRegistroEntrada obtenerSolicitudRegistroEntrada(TramiteTrafMatrDto tramiteDto, String colegio) {
		SolicitudRegistroEntrada solicitudRegistroEntrada = null;

		ObjectFactory objFactory = new ObjectFactory();

		PersonaDto colegiadoCompleto = servicioPersona.obtenerColegiadoCompleto(tramiteDto.getNumColegiado(), tramiteDto.getIdContrato());

		ColegioDto colegioDto = servicioColegio.getColegioDto(colegio);

		// -----------------ELEMENTO RAIZ----------------------
		solicitudRegistroEntrada = (SolicitudRegistroEntrada) objFactory.createSolicitudRegistroEntrada();

		solicitudRegistroEntrada.setDatosFirmados(objFactory.createDatosFirmados());
		solicitudRegistroEntrada.setVersion(VERSION_REGISTRO_ENTRADA);

		// --------------------------------------------------DATOS FIRMADOS----------------------------------------------------------------------------
		DatosEspecificos datosEspecificos = objFactory.createDatosEspecificos();
		Documentos documentos = objFactory.createDocumentos();
		DatosGenericos datosGenericos = objFactory.createDatosGenericos();

		solicitudRegistroEntrada.getDatosFirmados().setDocumentos(documentos);
		solicitudRegistroEntrada.getDatosFirmados().setDatosGenericos(datosGenericos);
		solicitudRegistroEntrada.getDatosFirmados().setDatosEspecificos(datosEspecificos);

		// ---------------------------------------------------DOCUMENTOS------------------------------------------
		// --------------------------------------------------DATOS GENERICOS---------------------------------------------------------------------------
		// TIPO DATO ASUNTO:
		TipoDatoAsuntoMATG tipoDatoAsunto = new TipoDatoAsuntoMATG();
		tipoDatoAsunto.setCodigo(CODIGO_ASUNTO);
		tipoDatoAsunto.setDescripcion(DESCRIPCION_ASUNTO);
		// TIPO DATO DESTINO
		TipoDatoDestino tipoDatoDestino = new TipoDatoDestino();
		tipoDatoDestino.setCodigo(CODIGO_DESTINO);
		tipoDatoDestino.setDescripcion(DESCRIPCION_DESTINO);
		// TIPO DATO COLEGIO
		TipoDatoColegio tipoDatoColegioRemitente = new TipoDatoColegio();
		tipoDatoColegioRemitente.setApellidos("");
		String correoElectronico = (colegioDto.getCorreoElectronico() != null) ? colegioDto.getCorreoElectronico() : "";
		tipoDatoColegioRemitente.setCorreoElectronico(correoElectronico);
		tipoDatoColegioRemitente.setNombre(colegioDto.getNombre());
		DocumentoIdentificacion documentoIdentificacion = new DocumentoIdentificacion();
		documentoIdentificacion.setNumero(colegioDto.getCif());
		tipoDatoColegioRemitente.setDocumentoIdentificacion(documentoIdentificacion);

		// datos genericos
		datosGenericos.setAsunto(tipoDatoAsunto);
		datosGenericos.setDestino(tipoDatoDestino);

		// INTERESADOS
		Interesados interesados = objFactory.createDatosGenericosInteresados();
		datosGenericos.setInteresados(interesados);
		TipoDocumentoIdentificacion tipoDocumentoIdentificacion = new TipoDocumentoIdentificacion();
		tipoDocumentoIdentificacion.setNumero(colegiadoCompleto.getNif());

		// TIPODATOGESTORIA -> para interesados
		TipoDatoGestoria tipoDatoGestoria = new TipoDatoGestoria();
		tipoDatoGestoria.setCorreoElectronico(colegiadoCompleto.getCorreoElectronico());
		tipoDatoGestoria.setDocumentoIdentificacion(tipoDocumentoIdentificacion);

		String nombreUsuario = colegiadoCompleto.getNombre();

		if (nombreUsuario.length() > _18) {
			nombreUsuario = nombreUsuario.substring(0, _18);
		}
		tipoDatoGestoria.setNombre(nombreUsuario);

		String apellidosUsuario = colegiadoCompleto.getApellido1RazonSocial();
		if (apellidosUsuario != null && !apellidosUsuario.isEmpty()) {
			apellidosUsuario += " " + colegiadoCompleto.getApellido2();
			if (nombreUsuario.length() > _45) {
				apellidosUsuario = apellidosUsuario.substring(0, _45);
			}
			tipoDatoGestoria.setApellidos(apellidosUsuario);
		}

		interesados.setInteresado(tipoDatoGestoria);

		// COMPLETAMOS DATOS GENERICOS
		datosGenericos.setOrganismo(ORGANISMO);
		datosGenericos.setInteresados(interesados);
		datosGenericos.setNumeroExpediente(tramiteDto.getNumExpediente().toString());
		datosGenericos.setRemitente(tipoDatoColegioRemitente);

		// --------------------------------------------------DATOS ESPECIFICOS--------------------------------------------------------------------------

		Titular titular = objFactory.createDatosEspecificosTitular();
		Arrendatario arrendatario = objFactory.createDatosEspecificosArrendatario();
		ConductorHabitual conductorHabitual = objFactory.createDatosEspecificosConductorHabitual();
		DatosVehiculo datosVehiculo = objFactory.createDatosEspecificosDatosVehiculo();
		Tutor tutor = objFactory.createDatosEspecificosTutor();

		TipoDatoDireccion tipoDatoDireccionVehiculo = new TipoDatoDireccion();
		if (tramiteDto.getVehiculoDto() != null && tramiteDto.getVehiculoDto().getDireccion() != null && tramiteDto.getVehiculoDto().getDireccion().getIdDireccion() != null) {
			tipoDatoDireccionVehiculo = beanToXmldireccion(tramiteDto.getVehiculoDto().getDireccion());
			datosEspecificos.setDomicilioVehiculo(tipoDatoDireccionVehiculo);
		} else if (tramiteDto.getTitular() != null && tramiteDto.getTitular().getDireccion() != null && tramiteDto.getTitular().getDireccion().getIdDireccion() != null) {
			// Rellena la dirección del vehículo con la dirección del titular si no hay datos específicos de dirección del vehículo:
			tipoDatoDireccionVehiculo = beanToXmldireccion(tramiteDto.getTitular().getDireccion());
			datosEspecificos.setDomicilioVehiculo(tipoDatoDireccionVehiculo);
		}

		// datosEspecificos.setDatosTecnicos(completaDatosTecnicos(traficoTramiteMatriculacionBean));

		// ----------------NUM EXPEDIENTE COLEGIO
		datosEspecificos.setNumeroExpedienteColegio(tramiteDto.getNumExpediente().toString());

		// ----- IMPUESTOS

		// IMPUESTOS
		Impuestos impuestos = objFactory.createDatosEspecificosImpuestos();
		if (tramiteDto.getCem() != null && !tramiteDto.getCem().isEmpty()) {
			impuestos.setCEM(tramiteDto.getCem());
		}
		impuestos.setCEMA(tramiteDto.getCema());

		if (tramiteDto.getRegIedtm() != null && !tramiteDto.getRegIedtm().isEmpty() && !tramiteDto.getRegIedtm().equalsIgnoreCase("-1")) {
			impuestos.setExencionCEM(tramiteDto.getRegIedtm());
		} else {
			impuestos.setExencionCEM(null);
		}

		if (tramiteDto.getJustificadoIvtm() != null && tramiteDto.getJustificadoIvtm()) {
			impuestos.setJustificadoIVTM(TipoSN.S);
		} else if (tramiteDto.getJustificadoIvtm() != null && !tramiteDto.getJustificadoIvtm()) {
			impuestos.setJustificadoIVTM(TipoSN.N);
		}

		datosEspecificos.setImpuestos(impuestos);

		// --------------VERSION-------------------
		datosEspecificos.setVersion(VERSION);

		// -----------------------------------------

		if (tramiteDto.getTasa() != null) {
			datosEspecificos.setTasa(tramiteDto.getTasa().getCodigoTasa());
		}

		JefaturaTraficoDto jefatura = servicioJefaturaTrafico.getJefatura(tramiteDto.getJefaturaTraficoDto().getJefaturaProvincial());

		String sucursal = jefatura.getSucursal();
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

		datosEspecificos.setJefatura(jefatura.getJefatura());

		datosEspecificos.setTipoTramite(tramiteDto.getTipoTramiteMatr());

		// ---------------TITULAR---------------------
		// DOMICILIO -> tipoCambioDomicilio
		TipoCambioDomicilio tipoCambioDomicilioTitular = new TipoCambioDomicilio();
		if (tramiteDto.getTitular() != null && tramiteDto.getTitular().getCambioDomicilio() != null) {
			if (tramiteDto.getTitular().getCambioDomicilio()) {
				tipoCambioDomicilioTitular.setCambioDomicilio(TipoSN.S.value());
			} else {
				tipoCambioDomicilioTitular.setCambioDomicilio(TipoSN.N.value());
			}
			tipoCambioDomicilioTitular.setDatosDomicilio(beanToXmldireccion(tramiteDto.getTitular().getDireccion()));
		} else {
			tipoCambioDomicilioTitular.setCambioDomicilio(TipoSN.N.value());
		}

		titular.setDomicilioTitular(tipoCambioDomicilioTitular);

		// TIPO DATO TITULAR -> Identificacion
		TipoDatoTitular tipoDatoTitular = beanToXmlTipoDatoTitular(objFactory, tramiteDto.getTitular());
		if (tramiteDto.getRepresentanteTitular() != null) {
			if (tramiteDto.getRepresentanteTitular().getPersona().getNif() != null && !"".equals(tramiteDto.getRepresentanteTitular().getPersona().getNif())) {
				if (tramiteDto.getRepresentanteTitular().getIdMotivoTutela() == null) {
					TipoDocumentoIdentificacion tipoDocumentoIdentificacionRepresentante = new TipoDocumentoIdentificacion();
					tipoDocumentoIdentificacionRepresentante.setNumero(tramiteDto.getRepresentanteTitular().getPersona().getNif());
					tipoDatoTitular.setDocumentoIdentidadRepresentante(tipoDocumentoIdentificacionRepresentante);
				}
			}
		}
		titular.setIdentificacion(tipoDatoTitular);
		// SERVICIO AUTONOMO
		DatosEspecificos.Titular.ServicioAutonomo servicioAutonomo = objFactory.createDatosEspecificosTitularServicioAutonomo();

		if (tramiteDto.getTitular() != null && tramiteDto.getTitular().getCodigoIAE() != null && !tramiteDto.getTitular().getCodigoIAE().isEmpty()) {
			servicioAutonomo.setAutonomo(TipoSN.S.value());
			servicioAutonomo.setCodigoIAE(new BigInteger(tramiteDto.getTitular().getCodigoIAE()));
		} else {
			servicioAutonomo.setAutonomo(TipoSN.N.value());
			servicioAutonomo.setCodigoIAE(null);
		}
		titular.setServicioAutonomo(servicioAutonomo);

		// rellenamos titular
		solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setTitular(titular);

		// ------------------ARRENDATARIO-----------------

		if (tramiteDto.getArrendatario() != null)
			if (tramiteDto.getArrendatario().getPersona().getNif() != null && !tramiteDto.getArrendatario().getPersona().getNif().isEmpty()) {
				// DOMICILIO -> tipoCambioDomicilio
				TipoCambioDomicilio tipoCambioDomicilioArrendatario = new TipoCambioDomicilio();

				if (tramiteDto.getArrendatario().getCambioDomicilio() != null) {
					if (tramiteDto.getArrendatario().getCambioDomicilio()) {
						tipoCambioDomicilioArrendatario.setCambioDomicilio(TipoSN.S.value());
					} else {
						tipoCambioDomicilioArrendatario.setCambioDomicilio(TipoSN.N.value());
					}
					tipoCambioDomicilioArrendatario.setDatosDomicilio(beanToXmldireccion(tramiteDto.getArrendatario().getDireccion()));
				} else
					tipoCambioDomicilioArrendatario.setCambioDomicilio(TipoSN.N.value());

				arrendatario.setDomicilioArrendatario(tipoCambioDomicilioArrendatario);

				Fecha fechaInicioArrendatario = tramiteDto.getArrendatario().getFechaInicio();
				if (fechaInicioArrendatario != null && !fechaInicioArrendatario.isfechaNula()) {
					arrendatario.setFechaInicio(utilesFecha.invertirFecha(tramiteDto.getArrendatario().getFechaInicio()));
				}

				Fecha fechaFinArrendatario = tramiteDto.getArrendatario().getFechaFin();
				if (fechaFinArrendatario != null && !fechaFinArrendatario.isfechaNula()) {
					arrendatario.setFechaFin(utilesFecha.invertirFecha(tramiteDto.getArrendatario().getFechaFin()));
				}

				if (tramiteDto.getArrendatario().getHoraInicio() != null) {
					if (!tramiteDto.getArrendatario().getHoraInicio().isEmpty()) {
						String hora = (tramiteDto.getArrendatario().getHoraInicio());
						hora = hora.replace(":", "");
						arrendatario.setHoraInicio(new BigInteger(hora));
					}
				} else {
					arrendatario.setHoraInicio(null);
				}

				if (tramiteDto.getArrendatario().getHoraFin() != null) {
					if (!tramiteDto.getArrendatario().getHoraFin().isEmpty()) {
						String hora = tramiteDto.getArrendatario().getHoraFin();
						hora = hora.replace(":", "");
						arrendatario.setHoraFin(new BigInteger(hora));
					}
				} else {
					arrendatario.setHoraFin(null);
				}

				// TIPO DATO ARRENDATARIO -> Identificacion
				TipoDatoTitular tipoDatoArrendatario = beanToXmlTipoDatoTitular(objFactory, tramiteDto.getArrendatario());
				if (tramiteDto.getRepresentanteArrendatario() != null && tramiteDto.getRepresentanteArrendatario().getPersona().getNif() != null
						&& !tramiteDto.getRepresentanteArrendatario().getPersona().getNif().isEmpty()) {
					TipoDocumentoIdentificacion tipoDocumentoIdentificacionRepresentante = new TipoDocumentoIdentificacion();
					tipoDocumentoIdentificacionRepresentante.setNumero(tramiteDto.getRepresentanteArrendatario().getPersona().getNif());
					tipoDatoArrendatario.setDocumentoIdentidadRepresentante(tipoDocumentoIdentificacionRepresentante);
				}
				arrendatario.setIdentificacion(tipoDatoArrendatario);

				// SERVICIO AUTONOMO
				if (tramiteDto.getArrendatario().getPersona().getNif() == null || !tramiteDto.getArrendatario().getPersona().getNif().isEmpty())
					solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setArrendatario(arrendatario);

			} else {
				solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setArrendatario(null);
			}

		// --------------------REPRESENTANTE-TUTOR----------------------

		if (tramiteDto.getRepresentanteTitular() != null && tramiteDto.getRepresentanteTitular().getIdMotivoTutela() != null)
			tutor = completaDatosTutor(tutor, tramiteDto);
		else
			tutor = null;

		solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setTutor(tutor);

		// ------------------CONDUCTOR HABITUAL----------

		if (tramiteDto.getConductorHabitual() != null) {
			if (tramiteDto.getConductorHabitual().getPersona().getNif() != null && !tramiteDto.getConductorHabitual().getPersona().getNif().isEmpty()) {
				TipoDatoConductor tipoDatoConductor = new TipoDatoConductor();
				TipoDocumentoIdentificacion tipoDocumentoIdentificacionCH = new TipoDocumentoIdentificacion();
				tipoDocumentoIdentificacionCH.setNumero(tramiteDto.getConductorHabitual().getPersona().getNif());
				tipoDatoConductor.setDocumentoIdentidad(tipoDocumentoIdentificacionCH);
				conductorHabitual.setIdentificacion(tipoDatoConductor);

				// DOMICILIO -> tipoCambioDomicilio
				TipoCambioDomicilio tipoCambioDomicilioCH = new TipoCambioDomicilio();

				if (tramiteDto.getConductorHabitual().getCambioDomicilio() != null) {
					if (tramiteDto.getConductorHabitual().getCambioDomicilio()) {
						tipoCambioDomicilioCH.setCambioDomicilio(TipoSN.S.value());
					} else {
						tipoCambioDomicilioCH.setCambioDomicilio(TipoSN.N.value());
					}
					tipoCambioDomicilioCH.setDatosDomicilio(beanToXmldireccion(tramiteDto.getConductorHabitual().getDireccion()));
				} else
					tipoCambioDomicilioCH.setCambioDomicilio(TipoSN.N.value());

				conductorHabitual.setDomicilioConductor(tipoCambioDomicilioCH);

				if (tramiteDto.getConductorHabitual().getHoraFin() != null) {
					if (!tramiteDto.getConductorHabitual().getHoraFin().isEmpty()) {
						String hora = tramiteDto.getConductorHabitual().getHoraFin();
						hora = hora.replace(":", "");
						conductorHabitual.setHoraFin(new BigInteger(hora));
					}
				} else {
					conductorHabitual.setHoraFin(null);
				}

				if (tramiteDto.getConductorHabitual().getFechaFin() != null && !tramiteDto.getConductorHabitual().getFechaFin().isfechaNula()) {
					conductorHabitual.setFechaFin(utilesFecha.invertirFecha(tramiteDto.getConductorHabitual().getFechaFin()));
				}
			} else {
				conductorHabitual = null;
			}
		} else {
			conductorHabitual = null;
		}

		// rellenamos conductor habitual
		solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setConductorHabitual(conductorHabitual);

		// ----------------DATOS VEHICULO---------------

		if (tramiteDto.getVehiculoDto() != null) {
			datosVehiculo.setBastidor(tramiteDto.getVehiculoDto().getBastidor());
			if (tramiteDto.getVehiculoDto().getSubastado() != null && tramiteDto.getVehiculoDto().getSubastado()) {
				datosVehiculo.setSubasta(TipoSN.S.value());
			} else {
				datosVehiculo.setSubasta(TipoSN.N.value());
			}

			if (tramiteDto.getVehiculoDto().getMotivoItv() != null) {

				if (tramiteDto.getVehiculoDto().getMotivoItv().equalsIgnoreCase("M")) {
					datosVehiculo.setTipoInspeccionItv(TipoInspeccion.M.value());
				}
				// Mantis 15845. David getVehiculoDto: Se ha agregado a tipoInspeccionItv E: Exento ITV.
				else if (tramiteDto.getVehiculoDto().getMotivoItv().equalsIgnoreCase("E")) {
					datosVehiculo.setTipoInspeccionItv(TipoInspeccion.E.value());
				}
			}

			Fecha fechaValidezItv = tramiteDto.getVehiculoDto().getFechaItv();
			if (fechaValidezItv != null && !fechaValidezItv.isfechaNula()) {
				datosVehiculo.setFechaValidezITV(utilesFecha.invertirFecha(tramiteDto.getVehiculoDto().getFechaItv()));
			}

			if (fechaValidezItv != null && !fechaValidezItv.isfechaNula() && "E".equals(datosVehiculo.getTipoInspeccionItv())) {
				datosVehiculo.setFechaValidezITV(null);
			}

			if (tramiteDto.getVehiculoDto().getVehiUsado() != null && tramiteDto.getVehiculoDto().getVehiUsado()) {
				datosVehiculo.setUsado(TipoSN.S.value());
			} else {
				datosVehiculo.setUsado(TipoSN.N.value());
			}
			if (datosVehiculo.getUsado() != null && datosVehiculo.getUsado().equals(TipoSN.S.value())) {
				// FECHA DE LA PRIMERA MATRICULACIÓN
				Fecha fechaPrimeraMatriculacion = tramiteDto.getVehiculoDto().getFechaPrimMatri();
				if (fechaPrimeraMatriculacion != null && !fechaPrimeraMatriculacion.isfechaNula()) {
					datosVehiculo.setFechaPrimeraMatriculacion(utilesFecha.invertirFecha(tramiteDto.getVehiculoDto().getFechaPrimMatri()));
				}
				// MATRÍCULA ORIGEN
				if (tramiteDto.getVehiculoDto().getMatriculaOrigen() != null && !tramiteDto.getVehiculoDto().getMatriculaOrigen().isEmpty() && TipoTramiteMatriculacion.REMATRICULAR.equals(tramiteDto
						.getTipoTramiteMatr())) {
					datosVehiculo.setMatriculaOrigen(tramiteDto.getVehiculoDto().getMatriculaOrigen());
				}
				// KILÓMETROS DE USO
				if (tramiteDto.getVehiculoDto().getKmUso() != null && tramiteDto.getVehiculoDto().getKmUso().toString().length() <= 6) {
					datosVehiculo.setKilometraje(tramiteDto.getVehiculoDto().getKmUso().toBigInteger());
				} else if (tramiteDto.getVehiculoDto().getKmUso() != null && tramiteDto.getVehiculoDto().getKmUso().toString().length() > 6) {
					datosVehiculo.setKilometraje(new BigDecimal(999999).toBigInteger());
				}
				// HORAS DE USO
				if (tramiteDto.getVehiculoDto().getHorasUso() != null && tramiteDto.getVehiculoDto().getHorasUso().toString().length() <= 6) {
					datosVehiculo.setCuentaHoras(tramiteDto.getVehiculoDto().getHorasUso().toBigInteger());
				} else if (tramiteDto.getVehiculoDto().getHorasUso() != null && tramiteDto.getVehiculoDto().getHorasUso().toString().length() > 6) {
					datosVehiculo.setCuentaHoras(new BigDecimal(999999).toBigInteger());
				}
				// IMPORTADO
				if (tramiteDto.getVehiculoDto().getImportado() != null && tramiteDto.getVehiculoDto().getImportado()) {
					datosVehiculo.setImportado(TipoSN.S.value());
				} else {
					datosVehiculo.setImportado(TipoSN.N.value());
				}
			}
			// FIN DE DATOS DE VEHÍCULO USADO

			if (tramiteDto.getVehiculoDto().getServicioTrafico() != null && tramiteDto.getVehiculoDto().getServicioTrafico().getIdServicio() != null && !tramiteDto.getVehiculoDto()
					.getServicioTrafico().getIdServicio().isEmpty() && !tramiteDto.getVehiculoDto().getServicioTrafico().getIdServicio().equals("-1")) {
				datosVehiculo.setServicio(tramiteDto.getVehiculoDto().getServicioTrafico().getIdServicio());
			}

			// rellenamos vehiculo
			solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setDatosVehiculo(datosVehiculo);
			// Nuevos Matw NIVE jbc
			if (tramiteDto.getVehiculoDto().getNive() != null && !tramiteDto.getVehiculoDto().getNive().isEmpty()) {
				datosVehiculo.setNIVE(tramiteDto.getVehiculoDto().getNive());
				solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setDatosTecnicos(new DatosTecnicos());
				// Tipo tarjeta itv
				if (tramiteDto.getVehiculoDto().getTipoTarjetaITV() != null && !tramiteDto.getVehiculoDto().getTipoTarjetaITV().isEmpty()) {
					solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().getDatosTecnicos().setTipoTarjetaItv(tramiteDto.getVehiculoDto().getTipoTarjetaITV());
				}
				// Tipo vehiculo
				if (tramiteDto.getVehiculoDto().getTipoVehiculo() != null && !tramiteDto.getVehiculoDto().getTipoVehiculo().isEmpty()) {
					solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().getDatosTecnicos().setTipoVehiculoDGT(tramiteDto.getVehiculoDto().getTipoVehiculo());
				}
			} else {
				solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setDatosTecnicos(completaDatosTecnicos(objFactory, tramiteDto));
			}
		}
		return solicitudRegistroEntrada;
	}

	public SolicitudPruebaCertificado obtenerSolicitudPruebaCertificado(String alias) {
		SolicitudPruebaCertificado solicitudPruebaCertificado = null;
		trafico.beans.jaxb.pruebaCertificado.ObjectFactory objectFactory = new trafico.beans.jaxb.pruebaCertificado.ObjectFactory();
		solicitudPruebaCertificado = objectFactory.createSolicitudPruebaCertificado();
		DatosFirmados datosFirmados = objectFactory.createDatosFirmados();
		datosFirmados.setAlias(alias);
		solicitudPruebaCertificado.setDatosFirmados(datosFirmados);
		return solicitudPruebaCertificado;
	}

	private TipoDatoDireccion beanToXmldireccion(DireccionDto direccion) {
		TipoDatoDireccion tipoDatoDireccion = new TipoDatoDireccion();

		if (direccion != null) {
			if (direccion.getBloque() != null && !direccion.getBloque().isEmpty()) {
				tipoDatoDireccion.setBloque(direccion.getBloque());
			} else {
				tipoDatoDireccion.setBloque("");
			}

			if (direccion.getPuerta() != null && !direccion.getPuerta().isEmpty()) {
				tipoDatoDireccion.setPortal(direccion.getPuerta());
			} else {
				tipoDatoDireccion.setPortal("");
			}

			if (direccion.getCodPostalCorreos() != null && !direccion.getCodPostalCorreos().isEmpty()) {
				tipoDatoDireccion.setCodigoPostal(direccion.getCodPostalCorreos());
			} else {
				tipoDatoDireccion.setCodigoPostal("");
			}

			if (direccion.getEscalera() != null && !direccion.getEscalera().isEmpty()) {
				tipoDatoDireccion.setEscalera(direccion.getEscalera());
			} else {
				tipoDatoDireccion.setEscalera("");
			}

			if (direccion.getHm() != null) {
				tipoDatoDireccion.setHectometro(new BigInteger(direccion.getHm().toString()));
			}

			if (direccion.getKm() != null) {
				tipoDatoDireccion.setKilometro(new BigInteger(direccion.getKm().toString()));
			}

			if (direccion.getPuebloCorreos() != null && !direccion.getPuebloCorreos().isEmpty()) {
				tipoDatoDireccion.setLocalidad(direccion.getPuebloCorreos());
			} else {
				tipoDatoDireccion.setLocalidad("");
			}

			if (!direccion.getIdProvincia().isEmpty() && direccion.getIdMunicipio() != null && !direccion.getIdMunicipio().isEmpty()) {
				tipoDatoDireccion.setMunicipio(direccion.getIdProvincia() + direccion.getIdMunicipio());
			} else {
				tipoDatoDireccion.setMunicipio("");
			}

			if (direccion.getNombreVia() != null && !direccion.getNombreVia().isEmpty()) {
				String nombreVia = utilesConversiones.ajustarCamposIne(direccion.getNombreVia());
				tipoDatoDireccion.setNombreVia(UtilesCadenaCaracteres.quitarCaracteresExtranios(nombreVia));
			} else {
				tipoDatoDireccion.setNombreVia("");
			}

			if (direccion.getNumero() != null && !direccion.getNumero().isEmpty()) {
				tipoDatoDireccion.setNumero(direccion.getNumero());
			} else {
				tipoDatoDireccion.setNumero("");
			}

			if (direccion.getPlanta() != null && !direccion.getPlanta().isEmpty()) {
				tipoDatoDireccion.setPlanta(direccion.getPlanta());
			} else {
				tipoDatoDireccion.setPlanta("");
			}

			if (direccion.getLetra() != null) {
				tipoDatoDireccion.setPuerta(direccion.getLetra());
			} else {
				tipoDatoDireccion.setPuerta("");
			}

			tipoDatoDireccion.setPais(PAIS);

			if (direccion != null && direccion.getIdProvincia() != null) {
				if ("-1".equals(direccion.getIdProvincia())) {
					tipoDatoDireccion.setProvincia("");
				} else {
					tipoDatoDireccion.setProvincia(conversiones.getSiglasFromIdProvincia(direccion.getIdProvincia()));
				}
			} else
				tipoDatoDireccion.setProvincia("");

			if (direccion != null && direccion.getIdTipoVia() != null) {
				if ("-1".equals(direccion.getIdTipoVia())) {
					tipoDatoDireccion.setTipoVia("");
				} else {
					// Comprobamos si el tipo de vía tiene una traducción entre las aceptadas por DGT (que para MATW sólo los tiene en castellano)
					TipoViaVO tipoVia = servicioTipoVia.getTipoVia(direccion.getIdTipoVia());
					if (tipoVia != null) {
						tipoDatoDireccion.setTipoVia(tipoVia.getIdTipoViaDgt());
					} else {
						tipoDatoDireccion.setTipoVia(direccion.getIdTipoVia());
					}
				}
			} else {
				tipoDatoDireccion.setTipoVia("");
			}

		}

		return tipoDatoDireccion;
	}

	private TipoDatoTitular beanToXmlTipoDatoTitular(ObjectFactory objectFactory, IntervinienteTraficoDto interviniente) {
		TipoDatoTitular tipoDatoTitular = new TipoDatoTitular();

		if (interviniente != null) {
			// DOCUMENTO DE IDENTIDAD
			TipoDocumentoIdentificacion tipoDocumentoIdentificacion = beanToXmlTipoDocumentoIdentificacion(objectFactory, interviniente);
			tipoDatoTitular.setDocumentoIdentidad(tipoDocumentoIdentificacion);

			// TIPODATONOMBRE
			TipoDatoNombre tipoDatoNombre = beanToXmlTipoDatoNombre(objectFactory, interviniente, tipoDatoTitular);
			tipoDatoTitular.setDatosNombre(tipoDatoNombre);

			// FECHA NACIMIENTO
			if (!interviniente.getPersona().getTipoPersona().equals(TipoPersona.Juridica)) {
				if (interviniente.getPersona().getFechaNacimiento() != null && !interviniente.getPersona().getFechaNacimiento().getAnio().isEmpty() && !interviniente.getPersona().getFechaNacimiento()
						.getMes().isEmpty() && !interviniente.getPersona().getFechaNacimiento().getDia().isEmpty())
					tipoDatoTitular.setFechaNacimiento(utilesFecha.invertirCadenaFechaSinSeparador(interviniente.getPersona().getFechaNacimiento().toString()));
			} else {
				tipoDatoTitular.setFechaNacimiento(null);
			}
			// SEXO
			tipoDatoTitular.setSexo(interviniente.getPersona().getSexo());
		}

		return tipoDatoTitular;
	}

	private TipoDocumentoIdentificacion beanToXmlTipoDocumentoIdentificacion(ObjectFactory objectFactory, IntervinienteTraficoDto interviniente) {
		TipoDocumentoIdentificacion tipoDocumentoIdentificacion = new TipoDocumentoIdentificacion();

		if (interviniente.getPersona().getNif() != null && !interviniente.getPersona().getNif().isEmpty()) {
			tipoDocumentoIdentificacion.setNumero(interviniente.getPersona().getNif());
		} else {
			tipoDocumentoIdentificacion.setNumero(null);
		}
		return tipoDocumentoIdentificacion;
	}

	private TipoDatoNombre beanToXmlTipoDatoNombre(ObjectFactory objectFactory, IntervinienteTraficoDto titular, TipoDatoTitular tipoDatoTitular) {
		TipoDatoNombre tipoDatoNombre = new TipoDatoNombre();
		if (titular.getPersona().getNombre() == null || titular.getPersona().getNombre().isEmpty()) {
			if (titular.getPersona().getApellido1RazonSocial().length() >= 70) {
				tipoDatoNombre.setRazonSocial(titular.getPersona().getApellido1RazonSocial().substring(0, 70));
			} else {
				tipoDatoNombre.setRazonSocial(titular.getPersona().getApellido1RazonSocial());
			}
			tipoDatoNombre.setNombre("");
			tipoDatoNombre.setPrimerApellido("");
			tipoDatoNombre.setSegundoApellido("");
		} else {
			tipoDatoNombre.setRazonSocial("");
			tipoDatoNombre.setNombre(titular.getPersona().getNombre());
			if (titular.getPersona().getApellido1RazonSocial().length() >= 26) {
				tipoDatoNombre.setPrimerApellido(titular.getPersona().getApellido1RazonSocial().substring(0, 26));
			} else {
				tipoDatoNombre.setPrimerApellido(titular.getPersona().getApellido1RazonSocial());
			}
			tipoDatoNombre.setSegundoApellido(titular.getPersona().getApellido2());
		}

		tipoDatoTitular.setDatosNombre(tipoDatoNombre);
		return tipoDatoNombre;
	}

	private Tutor completaDatosTutor(Tutor tutor, TramiteTrafMatrDto tramiteDto) {
		TipoDocumentoIdentificacion tipoDocumentoIdentificacionTutor = new TipoDocumentoIdentificacion();
		if (tramiteDto.getRepresentanteTitular().getPersona().getNif() != null && !tramiteDto.getRepresentanteTitular().getPersona().getNif().isEmpty()) {
			tipoDocumentoIdentificacionTutor.setNumero(tramiteDto.getRepresentanteTitular().getPersona().getNif());
			tutor.setDocumentoIdentidad(tipoDocumentoIdentificacionTutor);

			String tipoTutela = null;

			if (tramiteDto.getRepresentanteTitular().getIdMotivoTutela() != null) {
				tipoTutela = tramiteDto.getRepresentanteTitular().getIdMotivoTutela();
				if (tipoTutela.equals(TipoTutela.MinoriaEdad.getValorEnum())) {
					tutor.setTipoTutela(TipoTutela.MinoriaEdad.toString());
				} else if (tipoTutela.equals(TipoTutela.MenorEmancipado.getValorEnum())) {
					tutor.setTipoTutela(TipoTutela.MenorEmancipado.toString());
				} else if (tipoTutela.equals(TipoTutela.Otros.getValorEnum())) {
					tutor.setTipoTutela(TipoTutela.Otros.toString());
				}
			}
		}
		return tutor;
	}

	private DatosTecnicos completaDatosTecnicos(ObjectFactory objectFactory, TramiteTrafMatrDto tramiteDto) {
		DatosTecnicos datosTecnicos = objectFactory.createDatosEspecificosDatosTecnicos();

		// prueba blanco a numero homologacion si valen null:
		if (tramiteDto.getVehiculoDto().getNumHomologacion() == null) {
			datosTecnicos.setNumeroHomologacion("");
		} else {
			datosTecnicos.setNumeroHomologacion(tramiteDto.getVehiculoDto().getNumHomologacion());
		}

		String codITV = tramiteDto.getVehiculoDto().getCodItv();

		if (!SIN_CODIG.equals(codITV)) {
			datosTecnicos.setCodigoITV(codITV);
		}
		String valor = tramiteDto.getVehiculoDto().getCriterioConstruccion() + tramiteDto.getVehiculoDto().getCriterioUtilizacion();
		datosTecnicos.setTipoVehiculoIndustria(valor);

		if (tramiteDto.getVehiculoDto().getTipoVehiculo() != null) {
			valor = tramiteDto.getVehiculoDto().getTipoVehiculo();
		} else {
			valor = null;
		}
		if (valor != null && !valor.trim().isEmpty() && !"-1".equals(valor.trim()) && TipoVehiculoDGT.contains(valor)) {
			datosTecnicos.setTipoVehiculoDGT(valor);
		}

		String nombreMarca = servicioVehiculo.obtenerNombreMarca(tramiteDto.getVehiculoDto().getCodigoMarca(), true);
		if (nombreMarca != null && !nombreMarca.isEmpty()) {
			datosTecnicos.setMarca(nombreMarca);
		}

		String modelo = tramiteDto.getVehiculoDto().getModelo();
		if (modelo != null && !modelo.isEmpty() && modelo.length() > 22) {
			modelo = tramiteDto.getVehiculoDto().getModelo().substring(0, 21);
		}
		datosTecnicos.setModelo(modelo);

		valor = (tramiteDto.getVehiculoDto().getColor() != null ? tramiteDto.getVehiculoDto().getColor() : "");

		if (valor != null && !valor.trim().isEmpty()) {
			if ("-".equals(valor))
				datosTecnicos.setColor("");
			else {
				datosTecnicos.setColor(valor);
			}
		}
		if (tramiteDto.getVehiculoDto().getTipoItv() != null && !tramiteDto.getVehiculoDto().getTipoItv().isEmpty()) {
			datosTecnicos.setTipo(tramiteDto.getVehiculoDto().getTipoItv());
		}

		// Variante:
		if (tramiteDto.getVehiculoDto().getVariante() != null && !tramiteDto.getVehiculoDto().getVariante().isEmpty()) {
			datosTecnicos.setVariante(tramiteDto.getVehiculoDto().getVariante());
		}

		// Version:
		if (tramiteDto.getVehiculoDto().getVersion() != null && !tramiteDto.getVehiculoDto().getVersion().isEmpty()) {
			datosTecnicos.setVersion(tramiteDto.getVehiculoDto().getVersion());
		}

		// MATE 2.5

		// Procedencia si se trata de vehículo usado e importado.
		if (tramiteDto.getVehiculoDto().getProcedencia() != null && tramiteDto.getVehiculoDto().getProcedencia() != null && !tramiteDto.getVehiculoDto().getProcedencia().equals("") && !tramiteDto
				.getVehiculoDto().getProcedencia().equals("-1") && tramiteDto.getVehiculoDto().getVehiUsado() != null && tramiteDto.getVehiculoDto().getVehiUsado().equals("true") && tramiteDto
						.getVehiculoDto().getImportado() != null && tramiteDto.getVehiculoDto().getImportado()) {
			datosTecnicos.setProcedencia(new BigInteger(tramiteDto.getVehiculoDto().getProcedencia()));
		}

		// Fabricante:
		if (tramiteDto.getVehiculoDto().getFabricante() != null && !tramiteDto.getVehiculoDto().getFabricante().isEmpty()) {
			datosTecnicos.setFabricante(tramiteDto.getVehiculoDto().getFabricante());
		}

		// Tipo tarjeta itv
		if (tramiteDto.getVehiculoDto().getTipoTarjetaITV() != null && !tramiteDto.getVehiculoDto().getTipoTarjetaITV().isEmpty()) {
			datosTecnicos.setTipoTarjetaItv(tramiteDto.getVehiculoDto().getTipoTarjetaITV());
		}

		// FIN MATE 2.5

		datosTecnicos.setCaracteristicas(completaCaracteristicasVehiculo(objectFactory, tramiteDto));

		// MATW. País de fabricación
		if (tramiteDto.getVehiculoDto().getPaisFabricacion() != null) {
			datosTecnicos.setPaisFabricacion(tramiteDto.getVehiculoDto().getPaisFabricacion());
		}

		// VEHICULOS MULTIFASICOS
		if (tramiteDto != null && tramiteDto.getVehiculoDto() != null) {

			// Marca base
			if (tramiteDto.getVehiculoDto().getCodigoMarcaBase() != null) {
				nombreMarca = servicioVehiculo.obtenerNombreMarca(tramiteDto.getVehiculoDto().getCodigoMarcaBase(), true);
				if (nombreMarca != null && !nombreMarca.isEmpty()) {
					datosTecnicos.setMarcaBase(nombreMarca);
				}
			}

			// Fabricante base
			if (tramiteDto.getVehiculoDto().getFabricanteBase() != null && !tramiteDto.getVehiculoDto().getFabricanteBase().isEmpty()) {
				datosTecnicos.setFabricanteBase(tramiteDto.getVehiculoDto().getFabricanteBase());
			}

			// Tipo base
			if (tramiteDto.getVehiculoDto().getTipoBase() != null && !tramiteDto.getVehiculoDto().getTipoBase().isEmpty()) {
				datosTecnicos.setTipoBase(tramiteDto.getVehiculoDto().getTipoBase());
			}

			// Variante base
			if (tramiteDto.getVehiculoDto().getVarianteBase() != null && !tramiteDto.getVehiculoDto().getVarianteBase().isEmpty()) {
				datosTecnicos.setVarianteBase(tramiteDto.getVehiculoDto().getVarianteBase());
			}

			// Version base
			if (tramiteDto.getVehiculoDto().getVersionBase() != null && !tramiteDto.getVehiculoDto().getVersionBase().isEmpty()) {
				datosTecnicos.setVersionBase(tramiteDto.getVehiculoDto().getVersionBase());
			}

			// Homologacion base; prueba blanco a numero homologacion si valen null:
			if (tramiteDto.getVehiculoDto().getNumHomologacionBase() != null && !tramiteDto.getVehiculoDto().getNumHomologacionBase().isEmpty()) {
				datosTecnicos.setNumeroHomologacionBase(tramiteDto.getVehiculoDto().getNumHomologacionBase());
			}
		}
		return datosTecnicos;
	}

	private Caracteristicas completaCaracteristicasVehiculo(ObjectFactory objFactory, TramiteTrafMatrDto tramiteDto) {
		String valor = null;
		BigDecimal valorBigDec = new BigDecimal(0);

		Caracteristicas vehiculo = objFactory.createDatosEspecificosDatosTecnicosCaracteristicas();

		if (tramiteDto.getVehiculoDto().getPotenciaFiscal() != null && !tramiteDto.getVehiculoDto().getPotenciaFiscal().toString().trim().isEmpty()) {
			valor = tramiteDto.getVehiculoDto().getPotenciaFiscal().toString();
			vehiculo.setPotenciaFiscal(new BigDecimal(valor).setScale(2, BigDecimal.ROUND_DOWN));
		} else {
			vehiculo.setPotenciaFiscal(null);
		}

		if (tramiteDto.getVehiculoDto().getCilindrada() != null && !tramiteDto.getVehiculoDto().getCilindrada().trim().isEmpty()) {
			valor = tramiteDto.getVehiculoDto().getCilindrada();
			vehiculo.setCilindrada(new BigDecimal(valor).setScale(2, BigDecimal.ROUND_DOWN));
		} else if (tramiteDto.getVehiculoDto().getCilindrada() == null || tramiteDto.getVehiculoDto().getCilindrada().trim().isEmpty()) {
			vehiculo.setCilindrada(null);
		} else {
			vehiculo.setCilindrada(new BigDecimal(0));
		}

		if (tramiteDto.getVehiculoDto().getTara() != null && !tramiteDto.getVehiculoDto().getTara().isEmpty() && Integer.parseInt(tramiteDto.getVehiculoDto().getTara()) > 0) {
			vehiculo.setTara(new BigInteger(tramiteDto.getVehiculoDto().getTara()));
		}

		if (tramiteDto.getVehiculoDto().getPesoMma() != null && !tramiteDto.getVehiculoDto().getPesoMma().trim().isEmpty()) {
			valor = tramiteDto.getVehiculoDto().getPesoMma();
			vehiculo.setMasaMaximaAutorizada(new BigInteger(valor));
		} else {
			vehiculo.setMasaMaximaAutorizada(new BigInteger(_0));
		}

		if (tramiteDto.getVehiculoDto().getPlazas() != null && !tramiteDto.getVehiculoDto().getPlazas().toString().trim().isEmpty()) {
			valor = tramiteDto.getVehiculoDto().getPlazas().toString();
			vehiculo.setNumeroMaximoPlazas(new BigInteger(valor));
		} else {
			vehiculo.setNumeroMaximoPlazas(new BigInteger(_0));
		}

		if (tramiteDto.getVehiculoDto().getCarburante() != null) {
			vehiculo.setTipoCarburante(tramiteDto.getVehiculoDto().getCarburante());
		}

		if (tramiteDto.getVehiculoDto().getPotenciaPeso() != null) {
			valorBigDec = tramiteDto.getVehiculoDto().getPotenciaPeso();
			vehiculo.setRelacionPotenciaPeso(valorBigDec.setScale(4, BigDecimal.ROUND_DOWN));
		} else {
			vehiculo.setRelacionPotenciaPeso(new BigDecimal(_0).setScale(4, BigDecimal.ROUND_DOWN));
		}

		if (tramiteDto.getVehiculoDto().getNumPlazasPie() != null) {
			valorBigDec = tramiteDto.getVehiculoDto().getNumPlazasPie();
			vehiculo.setNumeroPlazasDePie(valorBigDec.toBigInteger());
		} else {
			vehiculo.setNumeroPlazasDePie(new BigInteger(_0));
		}

		if (tramiteDto.getVehiculoDto().getCo2() != null && !tramiteDto.getVehiculoDto().getCo2().isEmpty() && tramiteDto.getVehiculoDto().getCarburante() != null && !"E".equals(tramiteDto
				.getVehiculoDto().getCarburante())) {

			valor = tramiteDto.getVehiculoDto().getCo2();
			// MATW. Por si se recuperan valores anteriores a matw con más de tres decimales, setea el co2 construyendo
			// el big decimal indicando como 3 el máximo número de decimales. No redondea.
			vehiculo.setCo2(new BigDecimal(valor).setScale(3, BigDecimal.ROUND_DOWN));
		} else if (tramiteDto.getVehiculoDto().getCarburante() != null && "E".equals(tramiteDto.getVehiculoDto().getCarburante())) {
			// No se establece el parametro ya que no debe de ir en el XML
			valor = tramiteDto.getVehiculoDto().getCo2();
			if (valor != null && !valor.isEmpty()) {
				vehiculo.setCo2(new BigDecimal(valor).setScale(3, BigDecimal.ROUND_DOWN));
			}
		} else {
			vehiculo.setCo2(null);
		}

		// Masa en servicio (mom):
		if (tramiteDto.getVehiculoDto().getMom() != null) {
			BigInteger intMasaServicio = new BigInteger(tramiteDto.getVehiculoDto().getMom().toString());
			vehiculo.setMasaEnServicio(intMasaServicio);
		}

		// MASA TÉCNICA ADMISIBLE
		if (tramiteDto.getVehiculoDto().getMtmaItv() != null && !tramiteDto.getVehiculoDto().getMtmaItv().isEmpty()) {
			BigInteger intMasaTecnicaAdmisible = new BigInteger(tramiteDto.getVehiculoDto().getMtmaItv());
			vehiculo.setMasaTecnicaAdmisible(intMasaTecnicaAdmisible);
		}

		if (tramiteDto.getVehiculoDto().getPotenciaNeta() != null && !tramiteDto.getVehiculoDto().getPotenciaNeta().toString().trim().isEmpty()) {
			valor = tramiteDto.getVehiculoDto().getPotenciaNeta().toString();
			vehiculo.setPotenciaNetaMaxima(new BigDecimal(valor).setScale(3, BigDecimal.ROUND_DOWN));
		} else {
			vehiculo.setPotenciaNetaMaxima(new BigDecimal(0));
		}

		// MATE 2.5

		// Código eco:
		if (tramiteDto.getVehiculoDto().getCodigoEco() != null && !tramiteDto.getVehiculoDto().getCodigoEco().isEmpty()) {
			vehiculo.setCodigoECO(tramiteDto.getVehiculoDto().getCodigoEco());
		}

		// Distancia entre ejes:
		if (tramiteDto.getVehiculoDto().getDistanciaEjes() != null) {
			BigInteger intDistEjes = new BigInteger(tramiteDto.getVehiculoDto().getDistanciaEjes().toString());
			vehiculo.setDistanciaEjes(intDistEjes);
		}

		// Eco innovación:
		if (tramiteDto.getVehiculoDto().getEcoInnovacion() != null && !tramiteDto.getVehiculoDto().getEcoInnovacion().isEmpty()) {
			if (tramiteDto.getVehiculoDto().getEcoInnovacion().equals(TipoSINO.SI)) {
				vehiculo.setEcoInnovacion(TipoSN.S.value());
			} else {
				vehiculo.setEcoInnovacion(TipoSN.N.value());
			}
		}

		if (tramiteDto.getVehiculoDto().getNivelEmisiones() != null && !tramiteDto.getVehiculoDto().getNivelEmisiones().equals("") && !"1".equals(tramiteDto.getVehiculoDto().getNivelEmisiones())) {
			valor = tramiteDto.getVehiculoDto().getNivelEmisiones().toUpperCase();
			vehiculo.setNivelEmisiones(valor);
		} else if (tramiteDto.getVehiculoDto().getNivelEmisiones() != null && tramiteDto.getVehiculoDto().getNivelEmisiones().equals("1") && tramiteDto.getVehiculoDto().getTipoTarjetaITV() != null
				&& tramiteDto.getVehiculoDto().getTipoTarjetaITV().equals("D")) {
			valor = BasicText.changeSize("1", 6, '0', true);
			vehiculo.setNivelEmisiones(valor);
		} else {
			vehiculo.setNivelEmisiones("0");
		}

		// Reduccion eco:
		if (tramiteDto.getVehiculoDto().getReduccionEco() != null) {
			vehiculo.setReduccionEco(new BigInteger(tramiteDto.getVehiculoDto().getReduccionEco().toString()));
		}

		// Via anterior:
		if (tramiteDto.getVehiculoDto().getViaAnterior() != null) {
			BigInteger intViaAnt = new BigInteger(tramiteDto.getVehiculoDto().getViaAnterior().toString());
			vehiculo.setViaAnterior(intViaAnt);
		}

		// Via posterior:
		if (tramiteDto.getVehiculoDto().getViaPosterior() != null) {
			BigInteger intViaPos = new BigInteger(tramiteDto.getVehiculoDto().getViaPosterior().toString());
			vehiculo.setViaPosterior(intViaPos);
		}

		// Consumo:
		if (tramiteDto.getVehiculoDto().getConsumo() != null) {
			vehiculo.setConsumo(new BigInteger(tramiteDto.getVehiculoDto().getConsumo().toString()));
		}

		// Carrocería:
		if (tramiteDto.getVehiculoDto().getCarroceria() != null && !tramiteDto.getVehiculoDto().getCarroceria().isEmpty() && !tramiteDto.getVehiculoDto().getCarroceria().equals("-1")) {
			vehiculo.setCarroceria(tramiteDto.getVehiculoDto().getCarroceria());
		}

		// Categoría de homologación:
		if (tramiteDto.getVehiculoDto().getIdDirectivaCee() != null && !tramiteDto.getVehiculoDto().getIdDirectivaCee().isEmpty() && !tramiteDto.getVehiculoDto().getIdDirectivaCee().equals("-1")) {
			vehiculo.setCategoriaEu(tramiteDto.getVehiculoDto().getIdDirectivaCee());
		}

		// Tipo de alimentación
		if (tramiteDto.getVehiculoDto().getTipoAlimentacion() != null) {
			vehiculo.setTipoAlimentacion(tramiteDto.getVehiculoDto().getTipoAlimentacion());
		}

		// VEHICULOS MULTIFASICOS
		// Masa en servicio base (mom base):
		if (tramiteDto.getVehiculoDto().getMomBase() != null) {
			vehiculo.setMOMBase(tramiteDto.getVehiculoDto().getMomBase().toBigInteger());
		}

		// Categoría eléctrica
		if (tramiteDto.getVehiculoDto().getCategoriaElectrica() != null && !tramiteDto.getVehiculoDto().getCategoriaElectrica().isEmpty()) {
			vehiculo.setCategoriaElectrica(tramiteDto.getVehiculoDto().getCategoriaElectrica());
		}

		// Autonomía eléctrica
		if (tramiteDto.getVehiculoDto().getAutonomiaElectrica() != null) {
			vehiculo.setAutonomiaElectrica(tramiteDto.getVehiculoDto().getAutonomiaElectrica().toBigInteger());
		}
		return vehiculo;
	}

	public ResultBean anhadirFirmasHsm(SolicitudRegistroEntrada solicitudRegistroEntrada) {
		ResultBean resultFirmasBean = new ResultBean();

		String aliasColegio = gestorPropiedades.valorPropertie("trafico.claves.colegiado.aliasHsm");

		ResultBean resultFirmaColegioHsm = realizarFirmaDatosFirmadosHsm(solicitudRegistroEntrada, aliasColegio);
		String xmlFirmadoColegio = resultFirmaColegioHsm.getMensaje();

		log.debug("XML firmado en servidor:" + xmlFirmadoColegio);

		if (!resultFirmaColegioHsm.getError()) {
			try {
				resultFirmasBean.setError(false);
				resultFirmasBean.setMensaje(xmlFirmadoColegio);
			} catch (Exception e) {
				log.error(ERROR_AL_OBTENER_BYTES_UTF_8);
			}
		} else {
			resultFirmasBean.setError(true);
			resultFirmasBean.setMensaje(ERROR_AL_FIRMAR_EL_COLEGIO);
		}
		return resultFirmasBean;
	}

	private ResultBean realizarFirmaDatosFirmadosHsm(SolicitudRegistroEntrada solicitudRegistroEntrada, String alias) {
		XMLMatwFactory xmlMatwFactory = new XMLMatwFactory();
		String xml = xmlMatwFactory.toXML(solicitudRegistroEntrada);
		return firmarHsm(xml, alias);
	}

	private ResultBean firmarHsm(String cadenaXML, String alias) {
		UtilesViafirma utilesViafirma = new UtilesViafirma();
		ResultBean resultBean = new ResultBean();

		if (utilesViafirma.firmarPruebaCertificadoCaducidad(cadenaXML, alias) == null) {
			log.error(HA_OCURRIDO_UN_ERROR_COMPROBANDO_LA_CADUCIDAD_DEL_CERTIFICADO);
			resultBean.setMensaje(HA_OCURRIDO_UN_ERROR_COMPROBANDO_LA_CADUCIDAD_DEL_CERTIFICADO);
			resultBean.setError(true);
		} else {
			String xmlFirmado = utilesViafirma.firmarMensajeMatriculacionServidor_MATW(cadenaXML, alias);

			if (xmlFirmado.equals(ERROR)) {
				// Ha fallado el proceso de firma en servidor
				log.error(HA_OCURRIDO_UN_ERROR_RECUPERANDO_EL_XML_FIRMADO_A_TRAVES_DEL_ID_DE_FIRMA);
				resultBean.setMensaje(HA_OCURRIDO_UN_ERROR_RECUPERANDO_EL_XML_FIRMADO_A_TRAVES_DEL_ID_DE_FIRMA);
				resultBean.setError(true);
			} else {// firma correctamente
				resultBean.setError(false);
				resultBean.setMensaje(xmlFirmado);
			}
		}
		return resultBean;
	}

	public ResultBean completarSolicitud(SolicitudRegistroEntrada solicitudRegistroEntrada, String firmado) throws Throwable {
		ResultBean resultSolicitudCompleta = new ResultBean();
		FicheroBean ficheroBean = new FicheroBean();
		try {
			String nombre = ConstantesProcesos.PROCESO_MATW + solicitudRegistroEntrada.getDatosFirmados().getDatosGenericos().getNumeroExpediente();
			ficheroBean.setTipoDocumento(ConstantesGestorFicheros.MATE);
			ficheroBean.setSubTipo(ConstantesGestorFicheros.ENVIO);
			ficheroBean.setSobreescribir(true);
			ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
			ficheroBean.setNombreDocumento(nombre);
			ficheroBean.setFecha(Utilidades.transformExpedienteFecha(solicitudRegistroEntrada.getDatosFirmados().getDatosGenericos().getNumeroExpediente()));

			gestorDocumentos.crearFicheroXml(ficheroBean, XMLMatwFactory.NAME_CONTEXT, solicitudRegistroEntrada, firmado, null);
		} catch (OegamExcepcion oe) {
			resultSolicitudCompleta.setError(true);
			resultSolicitudCompleta.setMensaje(oe.getMessage());
			return resultSolicitudCompleta;
		}

		String xmlResultadoValidar = new XMLMatwFactory().validarXMLMATW(ficheroBean.getFichero());

		if (xmlResultadoValidar.equals(XML_VALIDO)) {
			resultSolicitudCompleta.setError(false);
			resultSolicitudCompleta.setMensaje(solicitudRegistroEntrada.getDatosFirmados().getDatosGenericos().getNumeroExpediente());
		} else {
			resultSolicitudCompleta.setError(true);
			resultSolicitudCompleta.setMensaje(" La petición no es válida debido al siguiente error: " + xmlResultadoValidar);
			try {
				log.error("Error validación XML:" + xmlResultadoValidar + " del expediente: " + solicitudRegistroEntrada.getDatosFirmados().getDatosGenericos().getNumeroExpediente());
			} catch (Exception e) {
				log.error("Excepcion al sacar el numExpediente del xml por error de validación. Detalle: " + e.getMessage());
				log.error("Error validación XML:" + xmlResultadoValidar);
			}
		}

		return resultSolicitudCompleta;
	}

}