package trafico.modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang.StringUtils;
import org.displaytag.properties.SortOrderEnum;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.enumerados.ProcesosAmEnum;
import org.gestoresmadrid.core.enumerados.ProcesosEnum;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.modelos.model.vo.FundamentoExencion620VO;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.core.trafico.model.vo.JefaturaTraficoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafTranVO;
import org.gestoresmadrid.core.vehiculo.model.vo.MarcaCamVO;
import org.gestoresmadrid.oegam2comun.accionTramite.model.service.ServicioAccionTramite;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.model.service.ServicioOficinaLiquidadora620;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.FicheroInfo;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioFundamentoExencion620;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioJefaturaTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoTransmision;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoCtitBean;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.DatosCTITDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafTranDto;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioDirectivaCee;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioMarcaCam;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioModeloCam;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.ModeloCamDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.viafirma.cliente.exception.InternalException;
import org.xml.sax.SAXParseException;

import com.thoughtworks.xstream.XStream;

import colas.constantes.ConstantesProcesos;
import colas.modelo.ModeloSolicitud;
import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ColegioBean;
import escrituras.beans.Direccion;
import escrituras.beans.Persona;
import escrituras.beans.ResultBean;
import escrituras.modelo.ModeloColegiado;
import escrituras.modelo.ModeloContratoNuevo;
import escrituras.modelo.ModeloPersona;
import general.beans.RespuestaGenerica;
import general.modelo.ModeloBasePQ;
import oegam.constantes.ConstantesFirma;
import oegam.constantes.ConstantesPQ;
import oegam.constantes.ValoresSchemas;
import procesos.daos.BeanPQCrearSolicitud;
import trafico.beans.DatosCTITBean;
import trafico.beans.DescripcionesDireccionBean;
import trafico.beans.DescripcionesTraficoBean;
import trafico.beans.IntervinienteTrafico;
import trafico.beans.TramiteTraficoMatriculacionBean;
import trafico.beans.TramiteTraficoTransmisionBean;
import trafico.beans.VehiculoBean;
import trafico.beans.VehiculoTramiteTraficoBean;
import trafico.beans.daos.BeanPQCambiarEstadoTramite;
import trafico.beans.daos.BeanPQDatosCTIT;
import trafico.beans.daos.BeanPQDatosColegio;
import trafico.beans.daos.BeanPQDetalleTransmision;
import trafico.beans.daos.BeanPQDireccionViaEquivalente;
import trafico.beans.daos.BeanPQGeneral;
import trafico.beans.daos.BeanPQTramiteTraficoGuardarInterviniente;
import trafico.beans.daos.BeanPQTramiteTraficoGuardarTransmision;
import trafico.beans.daos.BeanPQTramiteTransmision;
import trafico.beans.daos.BeanPQVehiculosGuardar;
import trafico.beans.daos.pq_tramite_trafico.BeanPQCAMBIAR_ESTADO_S_V;
import trafico.beans.jaxb.transmision.TipoMotor;
import trafico.beans.schemas.generated.checkctit.DatosFirmados.ListaAdquirientes;
import trafico.beans.schemas.generated.checkctit.DatosFirmados.ListaTransmitentes;
import trafico.beans.schemas.generated.checkctit.SolicitudTramite;
import trafico.beans.schemas.generated.checkctit.TipoServicioDelVehiculo;
import trafico.beans.schemas.generated.checkctit.TipoTramite;
import trafico.beans.schemas.generated.transTelematica.Asunto;
import trafico.beans.schemas.generated.transTelematica.DatosAdquirente;
import trafico.beans.schemas.generated.transTelematica.DatosEspecificos;
import trafico.beans.schemas.generated.transTelematica.DatosEspecificos.AcreditacionActividad;
import trafico.beans.schemas.generated.transTelematica.DatosEspecificos.AcreditacionDerecho;
import trafico.beans.schemas.generated.transTelematica.DatosEspecificos.AcreditacionDerecho.FallecimientoDonacion;
import trafico.beans.schemas.generated.transTelematica.DatosEspecificos.AcreditacionDerecho.MotivoTransmision;
import trafico.beans.schemas.generated.transTelematica.DatosEspecificos.AcreditacionFiscal;
import trafico.beans.schemas.generated.transTelematica.DatosEspecificos.AcreditacionFiscal.IEDMT;
import trafico.beans.schemas.generated.transTelematica.DatosEspecificos.AcreditacionFiscal.ISD;
import trafico.beans.schemas.generated.transTelematica.DatosEspecificos.AcreditacionFiscal.ITP;
import trafico.beans.schemas.generated.transTelematica.DatosEspecificos.AcreditacionFiscal.IVTM;
import trafico.beans.schemas.generated.transTelematica.DatosEspecificos.Arrendatario;
import trafico.beans.schemas.generated.transTelematica.DatosEspecificos.ConductorHabitual;
import trafico.beans.schemas.generated.transTelematica.DatosEspecificos.CotitularesTransmitentes;
import trafico.beans.schemas.generated.transTelematica.DatosEspecificos.DatosAutonomo;
import trafico.beans.schemas.generated.transTelematica.DatosEspecificos.DatosColegio;
import trafico.beans.schemas.generated.transTelematica.DatosEspecificos.DatosExpediente;
import trafico.beans.schemas.generated.transTelematica.DatosEspecificos.DatosGestor;
import trafico.beans.schemas.generated.transTelematica.DatosEspecificos.DatosGestoria;
import trafico.beans.schemas.generated.transTelematica.DatosEspecificos.DatosTutela;
import trafico.beans.schemas.generated.transTelematica.DatosEspecificos.DatosTutela.TutelaAdquirente;
import trafico.beans.schemas.generated.transTelematica.DatosEspecificos.DatosTutela.TutelaTransmiente;
import trafico.beans.schemas.generated.transTelematica.DatosEspecificos.DatosVehiculo;
import trafico.beans.schemas.generated.transTelematica.DatosEspecificos.DatosVehiculo.DatosITV;
import trafico.beans.schemas.generated.transTelematica.DatosEspecificos.DatosVehiculo.DatosMatriculacion;
import trafico.beans.schemas.generated.transTelematica.DatosEspecificos.DatosVehiculo.DatosServicio;
import trafico.beans.schemas.generated.transTelematica.DatosEspecificos.Poseedor;
import trafico.beans.schemas.generated.transTelematica.DatosEspecificos.Tasas;
import trafico.beans.schemas.generated.transTelematica.DatosFirmados;
import trafico.beans.schemas.generated.transTelematica.DatosGenericos;
import trafico.beans.schemas.generated.transTelematica.DatosGenericos.Interesados;
import trafico.beans.schemas.generated.transTelematica.DatosInteresado;
import trafico.beans.schemas.generated.transTelematica.DatosKilometraje;
import trafico.beans.schemas.generated.transTelematica.DatosPersonales;
import trafico.beans.schemas.generated.transTelematica.DatosPersonales.DatosFiliacion;
import trafico.beans.schemas.generated.transTelematica.DatosPersonales.DatosFiliacion.PersonaFisica;
import trafico.beans.schemas.generated.transTelematica.DatosPersonales.DatosFiliacion.PersonaJuridica;
import trafico.beans.schemas.generated.transTelematica.DatosPersonalesTransmitente;
import trafico.beans.schemas.generated.transTelematica.DatosRemitente;
import trafico.beans.schemas.generated.transTelematica.DatosRepresentante;
import trafico.beans.schemas.generated.transTelematica.DatosTransmitente;
import trafico.beans.schemas.generated.transTelematica.DatosTransmitenteConAutonomo;
import trafico.beans.schemas.generated.transTelematica.Destino;
import trafico.beans.schemas.generated.transTelematica.Domicilio;
import trafico.beans.schemas.generated.transTelematica.SolicitudRegistroEntrada;
import trafico.beans.schemas.generated.transTelematica.TipoCEM;
import trafico.beans.schemas.generated.transTelematica.TipoConsentimiento;
import trafico.beans.schemas.generated.transTelematica.TipoOrganismo;
import trafico.beans.schemas.generated.transTelematica.TipoResultadoITV;
import trafico.beans.schemas.generated.transTelematica.TipoSI;
import trafico.beans.schemas.generated.transTelematica.TipoSINO;
import trafico.beans.schemas.generated.transTelematica.TipoTextoLegal;
import trafico.beans.transmision.jaxb.FundamentoType;
import trafico.beans.transmision.jaxb.IntervinienteA2Type;
import trafico.beans.transmision.jaxb.OficinaLiquidadoraType;
import trafico.beans.transmision.jaxb.ProvinciaDescType;
import trafico.beans.transmision.jaxb.ProvinciaType;
import trafico.beans.transmision.jaxb.ReduccionType;
import trafico.beans.transmision.jaxb.Remesa;
import trafico.beans.transmision.jaxb.Remesa.Declaracion;
import trafico.beans.transmision.jaxb.Remesa.Declaracion.Identificacion;
import trafico.beans.transmision.jaxb.Remesa.Declaracion.Liquidacion;
import trafico.beans.transmision.jaxb.Remesa.Declaracion.ListaIntervinientes;
import trafico.beans.transmision.jaxb.Remesa.Declaracion.Vehiculos;
import trafico.beans.transmision.jaxb.SiglasType;
import trafico.beans.transmision.jaxb.TipoIntervinienteType;
import trafico.beans.transmision.jaxb.TipoType;
import trafico.beans.utiles.IntervinienteTraficoBeanPQConversion;
import trafico.beans.utiles.TransmisionTramiteTraficoBeanPQConversion;
import trafico.beans.utiles.VehiculoBeanPQConversion;
import trafico.utiles.UtilesConversiones;
import trafico.utiles.UtilesConversionesTrafico;
import trafico.utiles.UtilesWSCTIT;
import trafico.utiles.XMLTransmisionNoTelematicaFactory;
import trafico.utiles.XmlCheckCTITFactory;
import trafico.utiles.XmlTransTelematicaFactory;
import trafico.utiles.constantes.ConstantesTrafico;
import trafico.utiles.enumerados.AcreditacionTrafico;
import trafico.utiles.enumerados.ConceptoTutela;
import trafico.utiles.enumerados.MapeoTipoTransferencia;
import trafico.utiles.enumerados.ModoAdjudicacion;
import trafico.utiles.enumerados.Paso;
import trafico.utiles.enumerados.PorcentajeReduccionAnual;
import trafico.utiles.enumerados.TipoAccion;
import trafico.utiles.enumerados.TipoCreacion;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import trafico.utiles.enumerados.TipoTransferencia;
import trafico.utiles.enumerados.TipoVehiculoCam;
import utilidades.constantes.ValoresCatalog;
import utilidades.estructuras.Fecha;
import utilidades.estructuras.ListaRegistros;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.CrearSolicitudExcepcion;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.AccionException;
import viafirma.utilidades.UtilesViafirma;

public class ModeloTransmision extends ModeloBasePQ{
	private static final String ERROR = "ERROR";
	private static final String ID_FIRMA = "idFirma=";
	private static final String TRAFICO_BEANS_TRANSMISION_JAXB = "trafico.beans.transmision.jaxb";
	private static final String EL_TIPO_DE_TRANSFERENCIA_NO_ES_CORRECTO = "El tipo de transferencia no es correcto";
	private static final String ERROR_AL_FIRMAR_CON_EL_COLEGIO = "Error al firmar con el colegio";
	private static final String GUARDAR_INTERVINIENTE = "GUARDAR_INTERVINIENTE";
	private static final String ANADIDA_FIRMA_DEL_COLEGIO_AL_REGISTRO_DE_ENTRADA = "Añadida firma del colegio al registro de entrada: ";
	private static final String FIRMAR_COLEGIO = "firmarColegio";
	private static final String XML_FIRMADO_EN_SERVIDOR_POR_EL_COLEGIO = "XML firmado en servidor por el colegio: ";
	private static final String EL_NOMBRE_DE_VIA_DEL_PRESENTADOR_ES_OBLIGATORIA = "El nombre de vía del presentador es obligatoria";
	private static final String EL_NOMBRE_DE_VIA_DEL_PRESENTADOR_NO_PUEDE_TENER_MAS_DE_50_CARACTERES = "El nombre de vía del presentador no puede tener más de 50 caracteres";
	private static final String EL_NOMBRE_DE_VIA_DEL_PRESENTADOR_ES_OBLIGATORIO = "El nombre de vía del presentador es obligatorio";
	private static final String EL_TIPO_DE_VIA_DEL_PRESENTADOR_ES_OBLIGATORIO = "El tipo de vía del presentador es obligatorio";
	private static final String EL_MUNICIPIO_DEL_PRESENTADOR_ES_OBLIGATORIO = "El municipio del presentador es obligatorio";
	private static final String LA_PROVINCIA_DEL_PRESENTADOR_ES_OBLIGATORIA = "La provincia del presentador es obligatoria";
	private static final String LA_RAZON_SOCIAL_DEL_PRESENTADOR_NO_PUEDE_TENER_MAS_DE_75_CARACTERES = "La razón social del presentador tomado del contrato del colegiado no puede tener más de 75 caracteres";
	private static final String EL_PRIMER_APELLIDO_DEL_PRESENTADOR_NO_PUEDE_TENER_MAS_DE_25_CARACTERES = "El primer apellido del presentador tomado del contrato del colegiado no puede tener más de 25 caracteres";
	private static final String EL_NOMBRE_DEL_PRESENTADOR_NO_PUEDE_TENER_MAS_DE_25_CARACTERES = "El nombre del presentador tomado del contrato del colegiado no puede tener más de 25 caracteres";
	private static final String EL_NOMBRE_DEL_PRESENTADOR_ES_OBLIGATORIO = "El nombre del presentador es obligatorio";
	private static final String EL_DNI_DEL_PRESENTADOR_DEBE_SER_VALIDO = "El DNI del presentador debe ser válido";
	private static final String EL_NIF_DEL_PRESENTADOR_ES_OBLIGATORIO = "El NIF del presentador es obligatorio";
	private static final String LA_DIRECCION_DEL_TRANSMITENTE_ES_OBLIGATORIA = "La dirección del transmitente es obligatoria";
	private static final String LA_PUERTA_DE_LA_DIRECCION_DEL_TRANSMITENTE_NO_PUEDE_TENER_MAS_DE_2_CARACTERES = "La puerta de la dirección del transmitente no puede tener más de 2 caracteres";
	private static final String EL_PISO_DE_LA_DIRECCION_DEL_TRANSMITENTE_NO_PUEDE_TENER_MAS_DE_3_CARACTERES = "El piso de la dirección del transmitente no puede tener más de 3 caracteres";
	private static final String LA_ESCALERA_DE_LA_DIRECCION_DEL_TRANSMITENTE_NO_PUEDE_TENER_MAS_DE_2_CARACTERES = "La escalera de la dirección del transmitente no puede tener más de 2 caracteres";
	private static final String EL_PORTAL_DE_LA_DIRECCION_DEL_TRANSMITENTE_NO_PUEDE_TENER_MAS_DE_1_CARACTER = "El portal de la dirección del transmitente no puede tener más de 1 caracter";
	private static final String EL_CODIGO_POSTAL_DEL_TRANSMITENTE_ES_OBLIGATORIO = "El código postal del transmitente es obligatorio";
	private static final String EL_NUMERO_DE_VIA_DEL_TRANSMITENTE_ES_OBLIGATORIO = "El número de vía del transmitente es obligatorio";
	private static final String NOMBRE_DE_VIA_DEL_TRANSMITENTE_NO_PUEDE_TENER_MAS_DE_50_CARACTERES = "El nombre de vía del transmitente no puede tener más de 50 caracteres";
	private static final String NOMBRE_DE_VIA_DEL_TRANSMITENTE_OBLIGATORIO = "El nombre de vía del transmitente es obligatorio";
	private static final String EL_TIPO_DE_VIA_DEL_TRANSMITENTE_ES_OBLIGATORIO = "El tipo de vía del transmitente es obligatorio";
	private static final String EL_MUNICIPIO_DEL_TRANSMITENTE_ES_OBLIGATORIO = "El municipio del transmitente es obligatorio";
	private static final String LA_PROVINCIA_DEL_TRANSMITENTE_ES_OBLIGATORIA = "La provincia del transmitente es obligatoria";
	private static final String LA_RAZON_SOCIAL_DEL_TRANSMITENTE_NO_PUEDE_TENER_MAS_DE_75_CARACTERES = "La razón social del transmitente no puede tener más de 75 caracteres";
	private static final String EL_PRIMER_APELLIDO_DEL_TRANSMITENTE_NO_PUEDE_TENER_MAS_DE_25_CARACTERES = "El primer apellido del transmitente no puede tener más de 25 caracteres";
	private static final String EL_NOMBRE_DEL_TRANSMITENTE_NO_PUEDE_TENER_MAS_DE_25_CARACTERES = "El nombre del transmitente no puede tener más de 25 caracteres";
	private static final String EL_NOMBRE_DEL_TRANSMITENTE_ES_OBLIGATORIO = "El nombre del transmitente es obligatorio";
	private static final String EL_PRIMER_APELLIDO_O_RAZON_SOCIAL_DEL_TRANSMITENTE_ES_OBLIGATORIO = "El primer apellido o razón social del transmitente es obligatorio";
	private static final String EL_DNI_DEL_TRANSMITENTE_DEBE_SER_VALIDO = "El DNI del transmitente debe ser válido";
	private static final String EL_NIF_DEL_TRANSMITENTE_ES_OBLIGATORIO = "El NIF del transmitente es obligatorio";
	private static final String LA_DIRECCION_DEL_ADQUIRIENTE_ES_OBLIGATORIA = "Faltan datos obligatorios de la dirección del adquiriente ó poseedor.";
	private static final String LA_PUERTA_DE_LA_DIRECCION_DEL_ADQUIRIENTE_NO_PUEDE_TENER_MAS_DE_3_CARACTERES = "La puerta de la dirección del adquiriente ó poseedor no puede tener más de 3 caracteres";
	private static final String EL_PISO_DE_LA_DIRECCION_DEL_ADQUIRIENTE_NO_PUEDE_TENER_MAS_DE_3_CARACTERES = "El piso de la dirección del adquiriente ó poseedor no puede tener más de 3 caracteres";
	private static final String LA_ESCALERA_DE_LA_DIRECCION_DEL_ADQUIRIENTE_NO_PUEDE_TENER_MAS_DE_3_CARACTERES = "La escalera de la dirección del adquiriente ó poseedor no puede tener más de 3 caracteres";
	private static final String EL_PORTAL_DE_LA_DIRECCION_DEL_ADQUIRIENTE_NO_PUEDE_TENER_MAS_DE_1_CARACTER = "El portal de la dirección del adquiriente ó poseedor no puede tener más de 1 caracter";
	private static final String EL_CODIGO_POSTAL_DEL_ADQUIRIENTE_ES_OBLIGATORIO = "El código postal del adquiriente ó poseedor es obligatorio";
	private static final String EL_NUMERO_DE_VIA_DEL_ADQUIRIENTE_ES_OBLIGATORIO = "El número de vía del adquiriente ó poseedor es obligatorio";
	private static final String EL_NOMBRE_DE_VIA_DEL_ADQUIRIENTE_NO_PUEDE_TENER_MAS_DE_50_CARACTERES = "El nombre de vía del adquiriente ó poseedor no puede tener más de 50 caracteres";
	private static final String EL_NOMBRE_DE_VIA_DEL_ADQUIRIENTE_ES_OBLIGATORIO = "El nombre de vía del adquiriente ó poseedor es obligatorio";
	private static final String EL_TIPO_DE_VIA_DEL_ADQUIRIENTE_ES_OBLIGATORIO = "El tipo de vía del adquiriente ó poseedor es obligatorio";
	private static final String EL_MUNICIPIO_DEL_ADQUIRIENTE_ES_OBLIGATORIO = "El municipio del adquiriente ó poseedor es obligatorio";
	private static final String LA_PROVINCIA_DEL_ADQUIRIENTE_ES_OBLIGATORIA = "La provincia del adquiriente ó poseedor es obligatoria";
	private static final String LA_PROVINCIA_DEL_VEHICULO_ES_OBLIGATORIA = "La provincia del vehículo es obligatoria";
	private static final String LA_DIRECCION_DEL_VEHICULO_ES_OBLIGATORIA = "La dirección del vehículo es obligatoria.";
	private static final String LA_PUERTA_DE_LA_DIRECCION_DEL_VEHICULO_NO_PUEDE_TENER_MAS_DE_2_CARACTERES = "La puerta de la dirección del vehículo no puede tener más de 2 caracteres";
	private static final String EL_PISO_DE_LA_DIRECCION_DEL_VEHICULO_NO_PUEDE_TENER_MAS_DE_3_CARACTERES = "El piso de la dirección del vehículo no puede tener más de 3 caracteres";
	private static final String LA_ESCALERA_DE_LA_DIRECCION_DEL_VEHICULO_NO_PUEDE_TENER_MAS_DE_2_CARACTERES = "La escalera de la dirección del vehículo no puede tener más de 2 caracteres";
	private static final String EL_PORTAL_DE_LA_DIRECCION_DEL_VEHICULO_NO_PUEDE_TENER_MAS_DE_1_CARACTER = "El portal de la dirección del vehículo no puede tener más de 1 caracter";
	private static final String EL_CODIGO_POSTAL_DEL_VEHICULO_ES_OBLIGATORIO = "El código postal del vehículo es obligatorio";
	private static final String EL_NUMERO_DE_VIA_DEL_VEHICULO_ES_OBLIGATORIO = "El número de vía del vehículo es obligatorio";
	private static final String EL_NOMBRE_DE_VIA_DEL_VEHICULO_NO_PUEDE_TENER_MAS_DE_50_CARACTERES = "El nombre de vía del vehículo no puede tener más de 50 caracteres";
	private static final String EL_NOMBRE_DE_VIA_DEL_VEHICULO_ES_OBLIGATORIO = "El nombre de vía del vehículo es obligatorio";
	private static final String EL_TIPO_DE_VIA_DEL_VEHICULO_ES_OBLIGATORIO = "El tipo de vía del vehículo es obligatorio";
	private static final String EL_MUNICIPIO_DEL_VEHICULO_ES_OBLIGATORIO = "El municipio del vehículo es obligatorio";
	private static final String LA_RAZON_SOCIAL_DEL_ADQUIRIENTE_NO_PUEDE_TENER_MAS_DE_75_CARACTERES = "La razón social del adquiriente ó poseedor no puede tener más de 75 caracteres";
	private static final String EL_PRIMER_APELLIDO_DEL_ADQUIRIENTE_NO_PUEDE_TENER_MAS_DE_25_CARACTERES = "El primer apellido del adquiriente ó poseedor no puede tener más de 25 caracteres";
	private static final String EL_NOMBRE_DEL_ADQUIRIENTE_NO_PUEDE_TENER_MAS_DE_25_CARACTERES = "El nombre del adquiriente ó poseedor no puede tener más de 25 caracteres";
	private static final String EL_NOMBRE_DEL_ADQUIRIENTE_ES_OBLIGATORIO = "El nombre del adquiriente ó poseedor es obligatorio";
	private static final String EL_PRIMER_APELLIDO_O_RAZON_SOCIAL_DEL_ADQUIRIENTE_ES_OBLIGATORIO = "El primer apellido o razón social del adquiriente ó poseedor es obligatorio";
	private static final String EL_DNI_DEL_ADQUIRIENTE_DEBE_SER_VALIDO = "El DNI del adquiriente ó poseedor debe ser válido";
	public static final String CIF = "CIF";
	public static final String DNI = "DNI";
	private static final String EL_DNI_DEL_ADQUIRIENTE_ES_OBLIGATORIO = "El DNI del adquiriente es obligatorio";
	private static final String FALTAN_DATOS_OBLIGATORIOS_POR_RELLENAR = "Faltan datos obligatorios por rellenar";
	private static final String EL_NUMERO_DE_VIA_DEL_PRESENTADOR_ES_OBLIGATORIO = "El número de vía del presentador es obligatorio";
	private static final String EL_CODIGO_POSTAL_DEBE_TENER_5_CARACTERES = "El código postal debe tener 5 caracteres";
	private static final String EL_CODIGO_POSTAL_VEHICULO_DEBE_TENER_5_CARACTERES = "El código postal del vehículo debe tener 5 caracteres";
	private static final String EL_CODIGO_POSTAL_ADQUIRIENTE_DEBE_TENER_5_CARACTERES = "El código postal del adquiriente debe tener 5 caracteres";
	private static final String EL_CODIGO_POSTAL_TRANSMITENTE_DEBE_TENER_5_CARACTERES = "El código postal del transmitente debe tener 5 caracteres";
	private static final String EL_CODIGO_POSTAL_PRESENTADOR_DEBE_TENER_5_CARACTERES = "El código postal del presentador debe tener 5 caracteres";
	private static final String EL_CODIGO_POSTAL_DEL_PRESENTADOR_ES_OBLIGATORIO = "El código postal del presentador es obligatorio";
	private static final String EL_TELEFONO_DEL_PRESENTADOR_ES_OBLIGATORIO = "El teléfono del presentador es obligatorio";
	private static final String EL_TELEFONO_DEL_PRESENTADOR_DEBE_TENER_9_DIGITOS = "El teléfono del presentador debe tener 9 dígitos";
	private static final String EL_PORTAL_DE_LA_DIRECCION_DEL_PRESENTADOR_NO_PUEDE_TENER_MAS_DE_1_CARACTER = "El portal de la dirección del presentador no puede tener más de 1 caracter";
	private static final String LA_ESCALERA_DE_LA_DIRECCION_DEL_PRESENTADOR_NO_PUEDE_TENER_MAS_DE_2_CARACTERES = "La escalera de la dirección del presentador no puede tener más de 2 caracteres";
	private static final String EL_PISO_DE_LA_DIRECCION_DEL_PRESENTADOR_NO_PUEDE_TENER_MAS_DE_2_CARACTERES = "El piso de la dirección del presentador no puede tener más de 2 caracteres";
	private static final String LA_PUERTA_DE_LA_DIRECCION_DEL_PRESENTADOR_NO_PUEDE_TENER_MAS_DE_2_CARACTERES = "La puerta de la dirección del presentador no puede tener más de 2 caracteres";
	private static final String LA_DIRECCION_DEL_PRESENTADOR_ES_OBLIGATORIA = "La dirección del presentador es obligatoria";
	private static final String LA_OFICINA_LIQUIDADORA_ES_OBLIGATORIA = "La Oficina liquidadora es obligatoria";
	private static final String LA_FECHA_DE_DEVENGO_ES_OBLIGATORIA = "La fecha de Devengo es obligatoria";
	private static final String LA_MATRICULA_DEL_VEHICULO_ES_OBLIGATORIA = "La matrícula del vehículo es obligatoria";
	private static final String LA_FECHA_DE_PRIMERA_MATRICULACION_ES_OBLIGATORIA = "La fecha de primera matriculación es obligatoria";
	private static final String LA_MATRICULA_DEL_VEHICULO_NO_PUEDE_TENER_MAS_DE_15_CARACTERES = "La matrícula del vehículo no puede tener más de 15 caracteres";
	private static final String EL_BASTIDOR_DEL_VEHICULO_NO_PUEDE_TENER_MAS_DE_15_CARACTERES = "El bastidor del vehículo no puede tener más de 15 caracteres";
	private static final String EL_TIPO_DE_VEHICULO_EN_EL_MODELO_620_ES_OBLIGATORIO = "El tipo de vehículo en el modelo 620 es obligatorio";
	private static final String EL_VALOR_DECLARADO_ES_OBLIGATORIO = "El valor declarado es obligatorio";
	private static final String LA_MARCA_COMUNIDAD_AUTONOMA_DE_MADRID_DEL_VEHICULO_ES_OBLIGATORIA = "La marca (Comunidad Autónoma de Madrid) del vehículo es obligatoria";
	private static final String EL_MODELO_COMUNIDAD_AUTONOMA_DE_MADRID_DEL_VEHICULO_ES_OBLIGATORIO = "El modelo (Comunidad Autónoma de Madrid) del vehículo es obligatorio";
	private static final String EL_MODELO_COMUNIDAD_AUTONOMA_DE_MADRID_DEL_VEHICULO_NO_PUEDE_TENER_MAS_DE_150_CARACTERES = "El modelo (Comunidad Autónoma de Madrid) del vehículo no puede tener más de 150 caracteres";
	private static final String LA_MARCA_COMUNIDAD_AUTONOMA_DE_MADRID_DEL_VEHICULO_NO_PUEDE_TENER_MAS_DE_30_CARACTERES = "La marca (Comunidad Autónoma de Madrid) del vehículo no puede tener más de 30 caracteres";
	private static final String NO_SE_HA_PODIDO_RECUPERAR_EL_NOMBRE_DE_LA_MARCA_Y_EL_MODELO_CAM = "No se ha podido recuperar el nombre de la marca y el modelo CAM";
	private static final String EL_PORCENTAJE_DE_REDUCCION_ANUAL_ES_OBLIGATORIO = "El porcentaje de Reducción Anual es obligatorio";
	private static final String LA_FECHA_DE_DEVENGO_NO_PUEDE_SER_POSTERIOR_A_LA_ACTUAL = "La fecha de Devengo no puede ser posterior a la actual";
	private static final String EL_ANIO_DE_LA_FECHA_DE_DEVENGO_SOLO_PUEDE_SER_EL_ANIO_EN_CURSO_O_EL_ANTERIOR = "El año de la fecha de devengo solo puede ser el año en curso o el anterior";
	private static final String LA_FECHA_DE_LA_PRIMERA_MATRICULACION_NO_PUEDE_SER_POSTERIOR_A_LA_ACTUAL = "La fecha de la primera matriculación no puede ser posterior a la actual";
	private static final String ERROR_AL_OBTENER_LA_FECHA_DE_PRIMERA_MATRICULACION = "Error al obtener la fecha de primera matriculación";
	private static final String LA_FECHA_DE_LA_PRIMERA_MATRICULACION_NO_PUEDE_SER_POSTERIOR_A_LA_FECHA_DE_DEVENGO = "La fecha de la primera matriculación no puede ser posterior a la fecha de devengo";
	private static final String TIPO_MOTOR_OBLIGATORIO = "Debe indicar un Tipo de motor";
	private static final String LA_POTENCIA_FISCAL_ES_OBLIGATORIA = "La potencia fiscal es obligatoria";
	private static final String LA_CILINDRADA_ES_OBLIGATORIA = "La cilindrada es obligatoria";
	private static final String LA_POTENCIA_FISCAL_NO_CONCUERDA_CON_LOS_DATOS_DE_LA_CAM = "La potencia fiscal no concuerda con los datos de la CAM";
	private static final String LA_POTENCIA_FISCAL_NO_TIENE_EL_FORMATO_CORRECTO = "La potencia fiscal no tiene el formato correcto";
	private static final String LA_CILINDRADA_NO_CONCUERDA_CON_LOS_DATOS_DE_LA_CAM = "La cilindrada no concuerda con los datos de la CAM";
	private static final String LA_CILINDRADA_NO_TIENE_EL_FORMATO_CORRECTO = "La cilindrada no tiene el formato correcto";
	private static final String EL_VALOR_DECLARADO_NO_CONCUERDA_CON_LOS_DATOS_DE_LA_CAM = "El valor declarado no concuerda con los datos de la CAM";
	private static final String EL_VALOR_DECLARADO_NO_TIENE_EL_FORMATO_CORRECTO = "El valor declarado no tiene el formato correcto";
	private static final String ERROR_AL_OBTENER_LA_FECHA_DE_DEVENGO_Y_LA_FECHA_DE_PRIMERA_MATRICULACION = "Error al obtener la fecha de Devengo y la fecha de primera matriculación";
	private static final String EL_PORCENTAJE_DE_REDUCCION_NO_CONCUERDA_CON_LOS_DATOS_DE_LA_CAM = "El porcentaje de reducción no concuerda con los datos de la CAM";
	private static final String ERROR_AL_OBTENER_LA_FECHA_DE_DEVENGO = "Error al obtener la fecha de Devengo";
	private static final String EL_PORCENTAJE_DE_ADQUISICION_ES_OBLIGATORIO = "El porcentaje de adquisición es obligatorio";
	private static final String LA_BASE_IMPONIBLE_ES_OBLIGATORIA = "La base imponible es obligatoria";
	private static final String EL_FUNDAMENTO_DE_EXENCION_ES_OBLIGATORIO_SI_ES_EXENTO = "El fundamento de exención es obligatorio si es exento";
	private static final String EL_FUNDAMENTO_DE_EXENCION_NO_PUEDE_TENER_MAS_DE_100_CARACTERES = "El fundamento de exención no puede tener más de 100 caracteres.";
	private static final String EL_FUNDAMENTO_DE_NO_SUJECCION_ES_OBLIGATORIO_SI_ES_NO_SUJETO = "El fundamento de no sujección es obligatorio si es no sujeto";
	private static final String EL_FUNDAMENTO_DE_NO_SUJECCION_NO_PUEDE_TENER_MAS_DE_100_CARACTERES = "El fundamento de no sujección no puede tener más de 100 caracteres.";
	private static final String SI_NO_MARCA_EXENTO_O_NO_SUJETO_DEBE_INTRODUCIR_LA_CUOTA_TRIBUTARIA_Y_EL_TIPO_DE_GRAVAMEN = "Si no marca exento o no sujeto debe introducir la cuota tributaria y el tipo de gravamen";
	private static final String _4 = "4";
	private static final String _4_0 = "4.0";
	private static final String EL_TIPO_DE_GRAVAMEN_SOLO_PUEDE_TENER_EL_VALOR_4_0 = "El tipo de gravamen solo puede tener el valor 4.0";
	private static final String LA_VALIDACION_FUE_CORRECTA_PERO_HA_HABIDO_UN_ERROR_AL_CAMBIAR_EL_ESTADO_DEL_TRAMITE = "La validación fue correcta pero ha habido un error al cambiar el estado del trámite";
	private static final String XML_TRANSMISION = "xmlTransmision";
	private static final String ERROR_AL_GUARDAR_EL_XML_DE_AUDITORIA_DE_TRANSMISION = "Error al guardar el XML de auditoría de transmisión";
	private static final String ERROR_AL_OBTENER_BYTES_UTF_8 = "Error al obtener bytes UTF-8";
	private static final String _1_6 = "1.6";
	private static final String _00001 = "00001";
	private static final String _1_4 = "1.4";
	private static final String _00 = "00";
	private static final String _620A4 = "620A4";
	private static final String S = "S";
	private static final String NO_SE_HA_PODIDO_RECUPERAR_INFORMACION_DE_LA_PROVINCIA_MUNICIPIO_Y_TIPO_DE_VIA_DEL_ADQUIRIENTE = "No se ha podido recuperar información de la provincia, municipio y tipo de vía del adquiriente/poseedor: ";
	private static final String NO_SE_HA_PODIDO_RECUPERAR_INFORMACION_DE_LA_PROVINCIA_MUNICIPIO_Y_TIPO_DE_VIA_DEL_PRESENTADOR = "No se ha podido recuperar información de la provincia, municipio y tipo de vía del presentador: ";
	private static final String CAM = "CAM";
	private static final String N = "N";
	private static final String NO_SE_HA_PODIDO_RECUPERAR_NI_LA_FECHA_DE_DEVENGO_NI_LA_DE_PRIMERA_MATRICULACION = "No se ha podido recuperar ni la fecha de devengo ni la de primera matriculación";
	private static final String A = "A";
	private static final String B = "B";
	private static final String C = "C";
	private static final String NO_SE_HA_PODIDO_RECUPERAR_LA_FECHA_DE_DEVENGO = "No se ha podido recuperar la fecha de devengo";
	private static final String HA_OCURRIDO_UN_ERROR_AL_PARSEAR_EL_XML = "Ha ocurrido un error al parsear el XML";
	private static final String SOLICITUD_DE_CAMBIO_DE_TITULARIDAD_DE_VEHICULOS_ONLINE_PARA_GESTORES = "Solicitud de Cambio de Titularidad de Vehículos Online para Gestores";
	private static final String CADENA_VACIA = "";
	private static final String SN = "sn";
	private static final String SN_620 = "S/N";
	private static final String _00000000 = "00000000";
	private static final String _1_0 = "1.0";
	private static final String FIRMA_COLEGIADO = "firmaColegiado";
	private static final String SOLICITUD_TRAMITE = "solicitud_tramite";
	private static final String ERROR_AL_FIRMAR_CON_EL_COLEGIADO = "Error al firmar con el colegiado";
	private static final String SOLICITUD_REGISTRO_ENTRADA = "solicitud_registro_entrada";
	private static final String FICHERO = "fichero";
	private static final String ERROR_AL_PARSEAR_LA_FECHA_DE_NACIMIENTO_DEL_CONDUCTOR_HABITUAL = "Error al parsear la fecha de nacimiento del conductor habitual.";
	private static final String ERROR_AL_PARSEAR_LA_FECHA_DE_NACIMIENTO_DEL_ARRENDATARIO = "Error al parsear la fecha de nacimiento del arrendatario.";
	private static final String ERROR_AL_PARSEAR_LA_FECHA_DE_NACIMIENTO_DEL_POSEEDOR = "Error al parsear la fecha de nacimiento del poseedor.";
	private static final String ESP = "ESP";
	private static final String ERROR_AL_PARSEAR_LA_FECHA_DE_NACIMIENTO_DEL_ADQUIRIENTE = "Error al parsear la fecha de nacimiento del adquiriente.";
	private static final String ERROR_AL_PARSEAR_LA_FECHA_DE_NACIMIENTO_DEL_SEGUNDO_COTITULAR_TRANSMITENTE = "Error al parsear la fecha de nacimiento del segundo cotitular transmitente.";
	private static final String ERROR_AL_PARSEAR_LA_FECHA_DE_NACIMIENTO_DEL_PRIMER_COTITULAR_TRANSMITENTE = "Error al parsear la fecha de nacimiento del primer cotitular transmitente.";
	private static final String ERROR_AL_PARSEAR_LA_FECHA_DE_NACIMIENTO_DEL_TRANSMITENTE = "Error al parsear la fecha de nacimiento del transmitente.";
	private static final String ERROR_AL_PARSEAR_LA_FECHA_DE_CADUCIDAD_DE_LA_ITV_DEL_VEHICULO = "Error al parsear la fecha de caducidad de la ITV del vehículo";
	private static final String ERROR_AL_PARSEAR_LA_FECHA_DE_INSPECCION_DEL_VEHICULO = "Error al parsear la fecha de inspección del vehículo";
	private static final String ERROR_AL_PARSEAR_LA_FECHA_DE_MATRICULACION_DEL_VEHICULO = "Error al parsear la fecha de matriculación del vehículo";
	private static final String _0 = "0";
	private static final String TRUE = "true";
	private static final String FALSE = "false";
	private static final String SI = "SI";
	private static final String NO = "NO";
	private static final String NO_SE_HA_PODIDO_RECUPERAR_EL_NOMBRE_DEL_FUNDAMENTO_DE_EXENCION = "No se ha podido recuperar el nombre del fundamento de exención";
	private static final String NO_SE_HA_PODIDO_RECUPERAR_EL_NOMBRE_DEL_FUNDAMENTO_DE_NO_SUJECCION = "No se ha podido recuperar el nombre del fundamento de no sujección";
	private static final String _101001 = "101001";
	private static final String _E00130201 = "E00130201";
	private static final String DGT_VEHICULOS = "DGT - Vehículos";
	private static final String CTVG = "CTVG";
	private static final String CTIGE = "CTIGE";
	private static final String NOTGE = "NOTGE";
	private static final String ENTGE = "ENTGE";
	private static final String SOLICITUD_CREADA_CORRECTAMENTE = "Solicitud creada correctamente";
	private static final String ERROR_AL_CREAR_LA_SOLICITUD = "Error al crear la solicitud";
	private static final String EL_VEHICULO_O_TITULAR_ADQUIRIENTE_DEBEN_TENER_UNA_DIRECCION_ASOCIADA = "El vehículo o titular (adquiriente), deben tener una dirección asociada";
	private static final String ADQUIRIENTE = "ADQUIRIENTE";
	private static final String REPRESENTANTE_ADQUIRIENTE = "REPRESENTANTE ADQUIRIENTE";
	private static final String CONDUCTOR_HABITUAL = "CONDUCTOR HABITUAL";
	private static final String REPRESENTANTE_TRANSMITENTE = "REPRESENTANTE TRANSMITENTE";
	private static final String PRIMER_COTITULAR = "PRIMER COTITULAR";
	private static final String SEGUNDO_COTITULAR = "SEGUNDO COTITULAR";
	private static final String REPRESENTANTE_PRIMER_COTITULAR_TRANSMITENTE = "REPRESENTANTE PRIMER COTITULAR TRANSMITENTE";
	private static final String REPRESENTANTE_SEGUNDO_COTITULAR_TRANSMITENTE = "REPRESENTANTE SEGUNDO COTITULAR TRANSMITENTE";
	private static final String COMPRAVENTA = "POSEEDOR";
	private static final String TRANSMITENTE = "TRANSMITENTE";
	private static final String REPRESENTANTE_COMPRAVENTA = "REPRESENTANTE POSEEDOR";
	private static final String TITULAR = "TITULAR";
	private static final String REPRESENTANTE_TITULAR = "REPRESENTANTE_TITULAR";
	private static final String ARRENDATARIO = "ARRENDATARIO";
	private static final String VALIDAR_ANAGRAMA_ANTES_VALIDAR_TRAMITE = "validar.anagrama.aeat";
	private static final String OPCION_SI_VALIDAR_ANAGRAMA_ANTES_VALIDAR_TRAMITE = "SI";
	private static final String POSEEDOR = "POSEEDOR";
	private static final String REPRESENTANTE_POSEEDOR = "REPRESENTANTE DEL POSEEDOR";
	/* INICIO MANTIS 0012707 */
	private static final String ERROR_AL_CREAR_LA_ACCION_PARA_GENERAR_EL_XML_620 = "Ha ocurrido un error en la generación del XML 620";
	/* FIN MANTIS 0012707 */
	//Inicio Mantis 12982: David Sierra
	private static final String ERROR_FORMATO_FECHA_PRIMERA_MATRICULACION = "La fecha de primera matriculación no tiene un formato válido";
	// Fin Mantis 12982

	// INICIO Mantis 0024132
	private static final String CHECK_IMPRESION_PERMISO = "SIImpresionPermiso";
	private static final String RESPUESTA_CHECK_IMPRESION_PERMISO = "Ha marcado la casilla Impresión Permiso";
	private static final String RESPUESTA_DESCHECKED_IMPRESION_PERMISO = "Ha desmarcado la casilla Impresión Permiso confirmando la lectura de la advertencia";
	private static final String DESCHECKED_IMPRESION_PERMISO = "NOImpresionPermiso";
	// FIN Mantis 0024132

	private static final String OPCION_SI_VALIDAR_ANAGRAMA_SI_AEAT_CAIDA_SELENIUM = "SI";
	private static final String VALIDAR_ANAGRAMA_SI_AEAT_CAIDA_SELENIUM = "validar.anagrama.aeat.selenium";

	private static final String PROPERTIE_CORREO_ASUNTO = "transmision.620.correo.asunto";
	private static final String PROPERTIE_CORREO_PARA = "transmision.620.correo.para";
	private static final String PROPERTIE_CORREO_COPIA = "transmision.620.correo.copia";

	private static final String FORMATO_FECHA = "yyyyMMdd";
	private static final String FORMATO_FECHA_2 = "dd/MM/yyyy";
	private static final String ERRORES = "errores";

	private static final String TRANSMISION_620_CILINDRADA_0_50 = "transmision.620.cilindrada.0-50";

	private static final String P_DESCRIPCION = "P_DESCRIPCION";

	private static ILoggerOegam log = LoggerOegam.getLogger(ModeloTransmision.class);

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private ServicioDirectivaCee servicioDirectivaCee;

	@Autowired
	private ServicioTramiteTraficoTransmision servicioTramiteTraficoTransmision;

	@Autowired
	private ServicioAccionTramite servicioAccionTramite;

	@Autowired
	private ServicioCorreo servicioCorreo;

	@Autowired
	private ServicioMarcaCam servicioMarcaCam;

	@Autowired
	private ServicioModeloCam servicioModeloCam;

	@Autowired
	private ServicioContrato servicioContrato;

	@Autowired
	ServicioJefaturaTrafico servicioJefaturaTrafico;

	@Autowired
	ServicioOficinaLiquidadora620 servicioOficinaLiquidadora620;

	@Autowired
	ServicioFundamentoExencion620 servicioFundamentoExencion620;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	VehiculoBeanPQConversion vehiculoBeanPQConversion;

	@Autowired
	TransmisionTramiteTraficoBeanPQConversion transmisionTramiteTraficoBeanPQConversion;

	@Autowired
	IntervinienteTraficoBeanPQConversion intervinienteTraficoBeanPQConversion;

	@Autowired
	ModeloContratoNuevo modeloContratoNuevo;

	@Autowired
	ValoresSchemas valoresSchemas;

	@Autowired
	Utiles utiles;

	@Autowired
	ServicioCola servicioCola;

	@Autowired
	private UtilesConversiones utilesConversiones;

	@Autowired
	private UtilesColegiado utilesColegiado;

	@Autowired
	ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	ServicioVehiculo servicioVehiculo;

	private ModeloInterviniente modeloInterviniente;
	private ModeloTrafico modeloTrafico;
	private ModeloSolicitud modeloSolicitud;
	private ModeloVehiculo modeloVehiculo;
	private ModeloAcciones modeloAcciones;
	private ModeloCreditosTrafico modeloCreditosTrafico;
	private ModeloDGTWS modeloDGTWS;

	public ModeloTransmision(){
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	/**
	 * @author antonio.miguez
	 * @param BeanPQTramiteTransmision, TramiteTraficoTransmisionBean
	 * @param TramiteTraficoTransmisionBean beanPantallaTransmision
	 * @return HashMap<String,Object>
	 * @throws OegamExcepcion 
	 */
	/*
	 * Método para guardar un alta de trámite de transmisión en la BD.
	 */
	public Map<String,Object> guardarAltaTramiteTransmision(BeanPQTramiteTransmision beanTransmision, TramiteTraficoTransmisionBean beanPantallaTransmision) {
		log.info("guardarAltaTramiteTransmision");
		HashMap<String,Object> resultadoAlta = new HashMap<>();
		ResultBean resultado = new ResultBean();

		BigDecimal idVehiculo = null;
		BigDecimal numExpediente = null;

		//Respuestas de cada una de las llamadas
		RespuestaGenerica resultadoVehiculo = null;
		RespuestaGenerica resultadoTramiteTraficoGuardarTransmision = null;
		RespuestaGenerica resultadoTramiteTraficoGuardarIntervinienteAdquiriente = null;
		RespuestaGenerica resultadoTramiteTraficoGuardarIntervinienteConductorHabitual = null;
		RespuestaGenerica resultadoTramiteTraficoGuardarIntervinienteRepresentanteAdquiriente = null;
		RespuestaGenerica resultadoTramiteTraficoGuardarIntervinienteTransmitente = null;
		RespuestaGenerica resultadoTramiteTraficoGuardarIntervinienteRepresentanteTransmitente = null;
		RespuestaGenerica resultadoTramiteTraficoGuardarIntervinientePrimerCotitularTransmitente = null;
		RespuestaGenerica resultadoTramiteTraficoGuardarIntervinienteRepresentantePrimerCotitularTransmitente = null;
		RespuestaGenerica resultadoTramiteTraficoGuardarIntervinienteSegundoCotitularTransmitente = null;
		RespuestaGenerica resultadoTramiteTraficoGuardarIntervinienteRepresentanteSegundoCotitularTransmitente = null;
		RespuestaGenerica resultadoTramiteTraficoGuardarIntervinientePresentador = null;
		RespuestaGenerica resultadoTramiteTraficoGuardarIntervinientePoseedor = null;
		RespuestaGenerica resultadoTramiteTraficoGuardarIntervinienteRepresentantePoseedor = null;
		RespuestaGenerica resultadoTramiteTraficoGuardarIntervinienteArrendatario = null;
		RespuestaGenerica resultadoTramiteTraficoGuardarIntervinienteRepresentanteArrendatario = null;

		// DRC@03-10-2012 Incidencia: 1801
		//RespuestaGenerica resultadoTramiteTraficoGuardarIntervinienteCompraventa = null;
		//RespuestaGenerica resultadoTramiteTraficoGuardarIntervinienteRepresentanteCompraventa = null;

		//Beans de pantalla
		VehiculoBean vehiculoPantalla = null;
		TramiteTraficoTransmisionBean transmisionPantalla = null;
		IntervinienteTrafico adquirientePantalla = null;
		IntervinienteTrafico conductorHabitualPantalla = null;
		IntervinienteTrafico representanteAdquirientePantalla = null;
		IntervinienteTrafico transmitentePantalla = null;
		IntervinienteTrafico representanteTransmitentePantalla = null;
		IntervinienteTrafico primerCotitularTransmitentePantalla = null;
		IntervinienteTrafico representantePrimerCotitularTransmitentePantalla = null;
		IntervinienteTrafico segundoCotitularTransmitentePantalla = null;
		IntervinienteTrafico representanteSegundoCotitularTransmitentePantalla = null;
		IntervinienteTrafico presentadorPantalla = null;
		IntervinienteTrafico poseedorPantalla = null;
		IntervinienteTrafico representantePoseedorPantalla = null;
		IntervinienteTrafico arrendatarioPantalla = null;
		IntervinienteTrafico representanteArrendatarioPantalla = null;
//		IntervinienteTrafico compraventaPantalla = null;
//		IntervinienteTrafico representanteCompraVentaPantalla = null;
		String fechaCaducidadITV = null;

		BigDecimal pCodeVehiculo = null;
		String sqlErrmVehiculo = null;
		String pInformacionVehiculo = null;
		BeanPQTramiteTraficoGuardarTransmision beanPQTramiteTraficoGuardarTransmision = beanTransmision.getBeanGuardarTransmision();
		BeanPQVehiculosGuardar beanPQVehiculosGuardar = beanTransmision.getBeanGuardarVehiculo();
		//Control del tipo de trámite.
		beanPQVehiculosGuardar.setP_TIPO_TRAMITE(TipoTramiteTrafico.Transmision.getValorEnum());
		//DRC@15-02-2013 Incidencia Mantis: 2708
		beanPQTramiteTraficoGuardarTransmision.setP_ID_TIPO_CREACION(beanTransmision.getBeanGuardarTransmision().getP_ID_TIPO_CREACION());

		// Para guardar un vehículo es necesario que informe la matrícula. El bastidor ya no es un campo único
		beanPQVehiculosGuardar.setP_DIFERENCIA_ANYO(beanPQVehiculosGuardar.getP_DIFERENCIA_ANYO());
		if((beanPQVehiculosGuardar.getP_MATRICULA() == null || beanPQVehiculosGuardar.getP_MATRICULA().isEmpty())){
			resultado.setMensaje("Los datos del vehículo no se guardarán si no tiene matrícula");
			vehiculoPantalla = beanPantallaTransmision.getTramiteTraficoBean().getVehiculo();
		} else {
			if (beanPQTramiteTraficoGuardarTransmision.getP_FECHA_DEVENGO_ITP() != null) {
				Timestamp fecha = beanPQTramiteTraficoGuardarTransmision.getP_FECHA_DEVENGO_ITP();
				SimpleDateFormat formateadorFecha = new SimpleDateFormat("yyyy");
				String anioFormateado = formateadorFecha.format((java.util.Date)fecha);
				Calendar cal = new GregorianCalendar();
				cal.set(Calendar.YEAR, Integer.parseInt(anioFormateado));
				cal.set(Calendar.MONTH, 0);
				cal.set(Calendar.DATE, 1);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);

				beanPQVehiculosGuardar.setP_FECDESDE(new Timestamp(cal.getTimeInMillis()));
			}

			// INCIDENCIA 3038
			beanPQTramiteTraficoGuardarTransmision.setP_CONSENTIMIENTO_CAMBIO(TRUE.equals(beanPantallaTransmision.getConsentimientoCambio()) ? SI : NO);
			// Si el consentimiento está marcado

			// Ejecutamos guardar vehículo
			resultadoVehiculo = ejecutarProc(beanPQVehiculosGuardar,valoresSchemas.getSchema(),ValoresCatalog.PQ_VEHICULOS,"GUARDAR",BeanPQGeneral.class);

			// Recuperamos información de respuesta
			pCodeVehiculo = (BigDecimal)resultadoVehiculo.getParametro(ConstantesPQ.P_CODE);
			sqlErrmVehiculo = (String)resultadoVehiculo.getParametro(ConstantesPQ.P_SQLERRM);
			pInformacionVehiculo = (String)resultadoVehiculo.getParametro(ConstantesPQ.P_INFORMACION);

			// Con el vehículo guardado, guardamos el trámite
			if (ConstantesPQ.pCodeOk.equals(pCodeVehiculo)) {
				// Convertimos a bean de pantalla
				vehiculoPantalla = vehiculoBeanPQConversion.convertirPQToBean(resultadoVehiculo);
				vehiculoPantalla.setHomologacionBean(servicioDirectivaCee.getHomologacionBean((String)resultadoVehiculo.getParametro("P_ID_DIRECTIVA_CEE")));

				// Obtenemos el P_ID_VEHICULO
				idVehiculo = (BigDecimal)resultadoVehiculo.getParametro("P_ID_VEHICULO");
				// Lo seteamos
				beanPQTramiteTraficoGuardarTransmision.setP_ID_VEHICULO(idVehiculo);

				// DRC@06-02-2013 Incidencia Mantis: 3019
				fechaCaducidadITV = vehiculoPantalla.getCheckFechaCaducidadITV();

				// Si viene p_informacion hay que mostrarlo
				if (pInformacionVehiculo !=null && !CADENA_VACIA.equals(pInformacionVehiculo)) {
					resultado.setMensaje(pInformacionVehiculo);
				}
			} else {
				log.error("El vehículo no se ha guardado. Error: "+sqlErrmVehiculo);
				resultado.setMensaje("Error al guardar el vehículo: "+sqlErrmVehiculo);
				if (pInformacionVehiculo != null && !CADENA_VACIA.equals(pInformacionVehiculo)) {
					resultado.setMensaje("Vehículo: "+pInformacionVehiculo);
				}
				vehiculoPantalla = beanPantallaTransmision.getTramiteTraficoBean().getVehiculo();
			}
		}

		// INCIDENCIA 3038
		beanPQTramiteTraficoGuardarTransmision.setP_CONSENTIMIENTO_CAMBIO(TRUE.equals(beanPantallaTransmision.getConsentimientoCambio()) ? SI : NO);

		//DRC@15-02-2013 Incidencia Mantis: 2708
		beanPQTramiteTraficoGuardarTransmision.setP_ID_TIPO_CREACION(new BigDecimal(TipoCreacion.OEGAM.getValorEnum()));

		//JRG: Incidencia 0003728: Mejora para indicar cuando es una transferencia simultánea
		beanPQTramiteTraficoGuardarTransmision.setP_SIMULTANEA(beanPantallaTransmision.getSimultanea()!=null ? beanPantallaTransmision.getSimultanea() : new BigDecimal(-1));
		/*Se guardan en el BeanPQTramiteTraficoGuardar los nuevos campos que se van a almacenar en la base de datos */
		if (beanPantallaTransmision.getFechaFactura() != null && !beanPantallaTransmision.getFechaFactura().isfechaNula()) {
			try {
				beanPQTramiteTraficoGuardarTransmision.setP_FECHA_FACTURA(beanPantallaTransmision.getFechaFactura().getTimestamp());
			} catch (ParseException e) {
				resultado.setMensaje("La fecha que introdujo es inválida");
			}
		}

		if (beanPantallaTransmision.getFechaContrato()!=null && !beanPantallaTransmision.getFechaContrato().isfechaNula()) {
			try {
				beanPQTramiteTraficoGuardarTransmision.setP_FECHA_CONTRATO(beanPantallaTransmision.getFechaContrato().getTimestamp());
			} catch (ParseException e) {
				resultado.setMensaje("La fecha que introdujo es inválida");
			}
		}
		/*Campos guardados en el bean*/
		//Ejecutamos el procedimiento de guardar el trámite

		resultadoTramiteTraficoGuardarTransmision = ejecutarProc(beanPQTramiteTraficoGuardarTransmision,
				valoresSchemas.getSchema(), ValoresCatalog.PQ_TRAMITE_TRAFICO, "GUARDAR_TRANSMISION",
				BeanPQGeneral.class);
		// Mantis 25415: Aquí en vez de meter el dato por PQ vamos a mantener lo que ya hay en PQ
		// y después actualizamos el registro tirando de Hibernate
		// Además, cogemos la respuesta genérica y le añadimos el valor del valor real para que se muestre en pantalla
		HashMap<String,Object> auxiliar = resultadoTramiteTraficoGuardarTransmision.getMapaParametros();
		BigDecimal valorReal = beanPQTramiteTraficoGuardarTransmision.getP_VALOR_REAL();
		auxiliar.put("P_VALOR_REAL", valorReal);
		//DVV
		BigDecimal esSiniestro = beanPQTramiteTraficoGuardarTransmision.getP_ES_SINIESTRO();
		auxiliar.put("P_ES_SINIESTRO", esSiniestro);

//		BigDecimal tieneCargaFinanciera = beanPQTramiteTraficoGuardarTransmision.getP_TIENE_CARGA_FINANCIERA();
//		auxiliar.put("P_TIENE_CARGA_FINANCIERA", tieneCargaFinanciera);

//		BigDecimal cambioSocietario = beanPQTramiteTraficoGuardarTransmision.getP_CAMBIO_SOCIETARIO();
//		auxiliar.put("P_CAMBIO_SOCIETARIO", cambioSocietario);

//		BigDecimal modificacionNoConstante = beanPQTramiteTraficoGuardarTransmision.getP_MODIFICACION_NO_CONSTANTE();
//		auxiliar.put("P_MODIFICACION_NO_CONSTANTE", modificacionNoConstante);
		String acreditaPago = beanPQTramiteTraficoGuardarTransmision.getP_ACREDITACION_PAGO();
		auxiliar.put("P_ACREDITACION_PAGO", acreditaPago);
		/*TramiteTrafTranDto tramitDto = servicioTramiteTraficoTransmision.actualizarAcreditacionPago(beanPantallaTransmision.getTramiteTraficoBean().getNumExpediente());
		if(tramitDto != null && tramitDto.getAcreditacionPago() != null) {
			auxiliar.put("P_ACREDITACION_PAGO", tramitDto.getAcreditacionPago());
		}*/
	

		numExpediente = (BigDecimal)resultadoTramiteTraficoGuardarTransmision.getParametro("P_NUM_EXPEDIENTE");

		resultadoTramiteTraficoGuardarTransmision.setMapaParametros(auxiliar);
		String vReal = null;
		if (valorReal != null && !CADENA_VACIA.equals(valorReal.toString())) {
			vReal = valorReal.toString();
		}
		if (vReal != null && !vReal.equals(CADENA_VACIA) && numExpediente != null) {
			String[] cadena = vReal.split("\\.");
			if (cadena.length == 2 && (cadena[0].length()> 6 || cadena[1].length() > 2) ||
					cadena.length == 1 && cadena[0].length() > 6 ||
					cadena.length == 1 && vReal.startsWith(".") && cadena[0].length() > 2 ||
					vReal.charAt(0) == '-'){
				resultado.setMensaje("El dato del valor real no se ha guardado: no puede introducir"
						+ " valores con más de 6 enteros o 2 decimales.");
			} else {
				servicioTramiteTraficoTransmision.actualizarValorRealTransmision(numExpediente, valorReal);
			}
		}

		//DVV
		if (numExpediente != null) {
			if (esSiniestro != null) {
				servicioVehiculo.actualizarEsSiniestro(numExpediente, esSiniestro);
			}

//			if (tieneCargaFinanciera != null) {
//				servicioVehiculo.actualizarTieneCargaFinanciera(numExpediente, tieneCargaFinanciera);
//			}

//			if (cambioSocietario != null) {
//				servicioTramiteTraficoTransmision.actualizarCambioSocietario(numExpediente, cambioSocietario);
//			}

//			if (modificacionNoConstante != null) {
//				servicioTramiteTraficoTransmision.actualizarModificacionNoConstante(numExpediente, modificacionNoConstante);
//			}

		}

		// Recuperamos información de respuesta
		BigDecimal pCodeTramite = (BigDecimal)resultadoTramiteTraficoGuardarTransmision.getParametro(ConstantesPQ.P_CODE);
		String sqlErrmTramite = (String)resultadoTramiteTraficoGuardarTransmision.getParametro(ConstantesPQ.P_SQLERRM);
		String pInformacionTramite = (String)resultadoTramiteTraficoGuardarTransmision.getParametro(ConstantesPQ.P_INFORMACION);

		// Si el trámite se ha guardado bien, continuamos guardando los intervinientes
		boolean historicoCheckImpresionPermiso = Boolean.parseBoolean(beanPantallaTransmision.getHistoricoCheckImpresionPermiso());
		boolean impresionPermiso = Boolean.parseBoolean(beanPantallaTransmision.getImpresionPermiso());
		if (ConstantesPQ.pCodeOk.equals(pCodeTramite)) {

			// INICIO Mantis 0024132
			if (historicoCheckImpresionPermiso && !impresionPermiso){
				servicioAccionTramite.crearCerrarAccionTramite(utilesColegiado.getIdUsuarioSessionBigDecimal(), numExpediente, DESCHECKED_IMPRESION_PERMISO, RESPUESTA_DESCHECKED_IMPRESION_PERMISO);
			} else if (!historicoCheckImpresionPermiso && impresionPermiso){
				log.info("Cambio de valor de check Impresión permiso circulación en expediente: "+ numExpediente);
				servicioAccionTramite.crearCerrarAccionTramite(utilesColegiado.getIdUsuarioSessionBigDecimal(), numExpediente, CHECK_IMPRESION_PERMISO, RESPUESTA_CHECK_IMPRESION_PERMISO);
			}
			// FIN Mantis 0024132

			// Si viene p_informacion hay que mostrarlo
			if (pInformacionTramite != null && !CADENA_VACIA.equals(pInformacionTramite)) {
				resultado.setMensaje(pInformacionTramite);
			}

			// Convertimos a bean de pantalla
			transmisionPantalla = transmisionTramiteTraficoBeanPQConversion.convertirPQToBean(resultadoTramiteTraficoGuardarTransmision);
			// Los checks de aceptación de responsabilidad y vehículo agrícola habrá que setearlos después para que no se pierdan
			String consentimiento = transmisionPantalla.getTramiteTraficoBean().getVehiculo().getConsentimiento();
			// 3038

			Boolean vehiculoAgricola = transmisionPantalla.getTramiteTraficoBean().getVehiculo().getVehiculoAgricola();

			transmisionPantalla.getTramiteTraficoBean().setVehiculo(vehiculoPantalla);
			transmisionPantalla.getTramiteTraficoBean().getVehiculo().setConsentimiento(consentimiento);
			// 3038
			transmisionPantalla.getTramiteTraficoBean().getVehiculo().setVehiculoAgricola(vehiculoAgricola);
			transmisionPantalla.getTramiteTraficoBean().getVehiculo().setCheckFechaCaducidadITV(fechaCaducidadITV);

			// Incidencia 0002003
			// Se va a guardar los datos de la relación vehiculo_tramite_trafico si se guardó el vehículo y el trámite
			if (ConstantesPQ.pCodeOk.equals(pCodeVehiculo) && beanPantallaTransmision.getTramiteTraficoBean().getVehiculo().getVehiculoTramiteTraficoBean().getKilometros() !=null) {
				VehiculoTramiteTraficoBean vehiculoTramiteTraficoBean = beanPantallaTransmision.getTramiteTraficoBean().getVehiculo().getVehiculoTramiteTraficoBean();
				vehiculoTramiteTraficoBean.setIdVehiculo(idVehiculo);
				vehiculoTramiteTraficoBean.setNumExpediente(numExpediente);
				vehiculoTramiteTraficoBean.setNumColegiado(utilesColegiado.getNumColegiadoSession());
				ModeloVehiculoTramiteTrafico.guardarVehiculoTramiteTrafico(vehiculoTramiteTraficoBean);
				transmisionPantalla.getTramiteTraficoBean().getVehiculo().getVehiculoTramiteTraficoBean().setKilometros(beanPantallaTransmision.getTramiteTraficoBean().getVehiculo().getVehiculoTramiteTraficoBean().getKilometros());
			}

			//------------------------ADQUIRIENTE-------------------------------
			BeanPQTramiteTraficoGuardarInterviniente beanPQTramiteTraficoGuardarAdquiriente = beanTransmision.getBeanGuardarAdquiriente();
			beanPQTramiteTraficoGuardarAdquiriente.setP_NUM_EXPEDIENTE(numExpediente);

			if((beanPQTramiteTraficoGuardarAdquiriente.getP_NIF() != null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarAdquiriente.getP_NIF())) ||
					(beanPQTramiteTraficoGuardarAdquiriente.getP_TIPO_INTERVINIENTE() != null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarAdquiriente.getP_TIPO_INTERVINIENTE()))){
				//Ejecutamos el procedimiento de guardar el adquiriente
				resultadoTramiteTraficoGuardarIntervinienteAdquiriente = ejecutarProc(beanPQTramiteTraficoGuardarAdquiriente,valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,GUARDAR_INTERVINIENTE,BeanPQGeneral.class);

				//Recuperamos información de respuesta
				BigDecimal pCodeAdquiriente = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteAdquiriente.getParametro(ConstantesPQ.P_CODE);
				String pSqlErrmAdquiriente = (String)resultadoTramiteTraficoGuardarIntervinienteAdquiriente.getParametro(ConstantesPQ.P_SQLERRM);
				String pInformacionAdquiriente = (String)resultadoTramiteTraficoGuardarIntervinienteAdquiriente.getParametro(ConstantesPQ.P_INFORMACION);

				if (ConstantesPQ.pCodeOkAlt.equals(pCodeAdquiriente)) {
					if ((beanPQTramiteTraficoGuardarAdquiriente.getP_APELLIDO1_RAZON_SOCIAL()!=null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarAdquiriente.getP_APELLIDO1_RAZON_SOCIAL()))
							|| (beanPQTramiteTraficoGuardarAdquiriente.getP_NOMBRE()!=null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarAdquiriente.getP_NOMBRE()))) {
						resultado.setMensaje("El adquiriente no se ha guardado: Faltan datos obligatorios");
					}
					adquirientePantalla = beanPantallaTransmision.getAdquirienteBean();
				} else if (!ConstantesPQ.pCodeOk.equals(pCodeAdquiriente)){
					log.error("El adquiriente no se ha guardado. ", numExpediente.toString());
					resultado.setMensaje("Error al guardar el adquiriente: "+pSqlErrmAdquiriente);
					if(pInformacionAdquiriente!=null && !CADENA_VACIA.equals(pInformacionAdquiriente)){
						resultado.setMensaje(pInformacionAdquiriente);
					}
					adquirientePantalla = beanPantallaTransmision.getAdquirienteBean();
				} else {
					//Si viene p_informacion hay que mostrarlo
					if (pInformacionAdquiriente != null && !CADENA_VACIA.equals(pInformacionAdquiriente)){
						resultado.setMensaje("Adquiriente: "+pInformacionAdquiriente);
					}
					//Convertimos a bean de pantalla
					adquirientePantalla = resultadoTramiteTraficoGuardarIntervinienteAdquiriente!=null?intervinienteTraficoBeanPQConversion.convertirPQToBean(resultadoTramiteTraficoGuardarIntervinienteAdquiriente):null;
					//ROCÍO: Puede cambiar el estado del trámite al guardar un interviniente
					transmisionPantalla.getTramiteTraficoBean().setEstado(adquirientePantalla.getEstadoTramite()!= null ?adquirientePantalla.getEstadoTramite().getValorEnum():null);
				}
			}

			//Seteamos
			transmisionPantalla.setAdquirienteBean(adquirientePantalla);

			//------------------------CONDUCTOR HABITUAL-------------------------------
			BeanPQTramiteTraficoGuardarInterviniente beanPQTramiteTraficoGuardarConductorHabitual = beanTransmision.getBeanGuardarConductorHabitual();
			beanPQTramiteTraficoGuardarConductorHabitual.setP_NUM_EXPEDIENTE(numExpediente);

			if ((beanPQTramiteTraficoGuardarConductorHabitual.getP_NIF()!=null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarConductorHabitual.getP_NIF())) ||
					(beanPQTramiteTraficoGuardarConductorHabitual.getP_TIPO_INTERVINIENTE()!=null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarConductorHabitual.getP_TIPO_INTERVINIENTE()))){
				// Ejecutamos el procedimiento de guardar el conductor habitual
				resultadoTramiteTraficoGuardarIntervinienteConductorHabitual = ejecutarProc(beanPQTramiteTraficoGuardarConductorHabitual, valoresSchemas.getSchema(), ValoresCatalog.PQ_TRAMITE_TRAFICO,GUARDAR_INTERVINIENTE, BeanPQGeneral.class);

				// Recuperamos información de respuesta
				BigDecimal pCodeConductorHabitual = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteConductorHabitual.getParametro(ConstantesPQ.P_CODE);
				String pSqlErrmConductorHabitual = (String)resultadoTramiteTraficoGuardarIntervinienteConductorHabitual.getParametro(ConstantesPQ.P_SQLERRM);
				String pInformacionConductorHabitual = (String)resultadoTramiteTraficoGuardarIntervinienteConductorHabitual.getParametro(ConstantesPQ.P_INFORMACION);

				if (ConstantesPQ.pCodeOkAlt.equals(pCodeConductorHabitual)){
					if ((beanPQTramiteTraficoGuardarConductorHabitual.getP_APELLIDO1_RAZON_SOCIAL()!=null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarConductorHabitual.getP_APELLIDO1_RAZON_SOCIAL())) ||
							(beanPQTramiteTraficoGuardarConductorHabitual.getP_NOMBRE()!=null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarConductorHabitual.getP_NOMBRE()))){
						resultado.setMensaje("El conductor habitual no se ha guardado: Faltan datos obligatorios");
					}
					conductorHabitualPantalla = beanPantallaTransmision.getConductorHabitualBean();
				} else if (!ConstantesPQ.pCodeOk.equals(pCodeConductorHabitual)){
					log.error(" El conductor habitual no se ha guardado. ", numExpediente.toString());
					resultado.setMensaje("Error al guardar el conductor habitual: "+pSqlErrmConductorHabitual);
					if (pInformacionConductorHabitual!=null && !CADENA_VACIA.equals(pInformacionConductorHabitual)) {
						resultado.setMensaje(pInformacionConductorHabitual);
					}
					conductorHabitualPantalla = beanPantallaTransmision.getConductorHabitualBean();
				} else {
					// Si viene p_informacion hay que mostrarlo
					if (pInformacionConductorHabitual != null && !CADENA_VACIA.equals(pInformacionConductorHabitual)) {
						resultado.setMensaje("Conductor Habitual: "+pInformacionConductorHabitual);
					}
					// Convertimos a bean de pantalla
					conductorHabitualPantalla = resultadoTramiteTraficoGuardarIntervinienteConductorHabitual!=null ? intervinienteTraficoBeanPQConversion.convertirPQToBean(resultadoTramiteTraficoGuardarIntervinienteConductorHabitual) : null;
					//ROCÍO: Puede cambiar el estado del trámite al guardar un interviniente
					transmisionPantalla.getTramiteTraficoBean().setEstado(conductorHabitualPantalla.getEstadoTramite()!= null ? conductorHabitualPantalla.getEstadoTramite().getValorEnum() : null);
				}
			}

			//Seteamos
			transmisionPantalla.setConductorHabitualBean(conductorHabitualPantalla);

			//-------------------------REPRESENTANTE DEL ADQUIRIENTE-------------------------
			BeanPQTramiteTraficoGuardarInterviniente beanPQTramiteTraficoGuardarRepresentanteAdquiriente = beanTransmision.getBeanGuardarRepresentanteAdquiriente();
			beanPQTramiteTraficoGuardarRepresentanteAdquiriente.setP_NUM_EXPEDIENTE(numExpediente);

			if((beanPQTramiteTraficoGuardarRepresentanteAdquiriente.getP_NIF()!=null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarRepresentanteAdquiriente.getP_NIF())) ||
					(beanPQTramiteTraficoGuardarRepresentanteAdquiriente.getP_TIPO_INTERVINIENTE()!=null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarRepresentanteAdquiriente.getP_TIPO_INTERVINIENTE()))){
				// Ejecutamos el procedimiento de guardar el representante del adquiriente
				resultadoTramiteTraficoGuardarIntervinienteRepresentanteAdquiriente = ejecutarProc(beanPQTramiteTraficoGuardarRepresentanteAdquiriente, valoresSchemas.getSchema(), ValoresCatalog.PQ_TRAMITE_TRAFICO,GUARDAR_INTERVINIENTE, BeanPQGeneral.class);

				// Recuperamos información de respuesta
				BigDecimal pCodeRepresentanteAdquiriente = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteRepresentanteAdquiriente.getParametro(ConstantesPQ.P_CODE);
				String pSqlErrmRepresentanteAdquiriente = (String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteAdquiriente.getParametro(ConstantesPQ.P_SQLERRM);
				String pInformacionRepresentanteAdquiriente = (String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteAdquiriente.getParametro(ConstantesPQ.P_INFORMACION);
				if (ConstantesPQ.pCodeOkAlt.equals(pCodeRepresentanteAdquiriente)){
					if((beanPQTramiteTraficoGuardarRepresentanteAdquiriente.getP_APELLIDO1_RAZON_SOCIAL()!=null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarRepresentanteAdquiriente.getP_APELLIDO1_RAZON_SOCIAL())) ||
							(beanPQTramiteTraficoGuardarRepresentanteAdquiriente.getP_NOMBRE()!=null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarRepresentanteAdquiriente.getP_NOMBRE()))){
						resultado.setMensaje("El representante del adquiriente no se ha guardado: Faltan datos obligatorios");
					}
					representanteAdquirientePantalla = beanPantallaTransmision.getRepresentanteAdquirienteBean();
				} else if (!ConstantesPQ.pCodeOk.equals(pCodeRepresentanteAdquiriente)){
					log.error(" El representante del adquiriente no se ha guardado. ", numExpediente.toString());
					resultado.setMensaje("Error al guardar el representante del adquiriente: "+pSqlErrmRepresentanteAdquiriente);
					if (pInformacionRepresentanteAdquiriente!=null && !CADENA_VACIA.equals(pInformacionRepresentanteAdquiriente)) {
						resultado.setMensaje(pInformacionRepresentanteAdquiriente);
					}
					representanteAdquirientePantalla = beanPantallaTransmision.getRepresentanteAdquirienteBean();
				} else {
					//Si viene p_informacion hay que mostrarlo
					if (pInformacionRepresentanteAdquiriente!=null && !CADENA_VACIA.equals(pInformacionRepresentanteAdquiriente)) {
						resultado.setMensaje("Repr. Adquiriente: "+pInformacionRepresentanteAdquiriente);
					}
					//Convertimos a bean de pantalla
					representanteAdquirientePantalla = resultadoTramiteTraficoGuardarIntervinienteRepresentanteAdquiriente!=null ? intervinienteTraficoBeanPQConversion.convertirPQToBean(resultadoTramiteTraficoGuardarIntervinienteRepresentanteAdquiriente):null;
					//ROCIO: Puede cambiar el estado del trámite al guardar un interviniente
					transmisionPantalla.getTramiteTraficoBean().setEstado(representanteAdquirientePantalla.getEstadoTramite()!= null ?representanteAdquirientePantalla.getEstadoTramite().getValorEnum():null);
				}
			}

			//Seteamos
			transmisionPantalla.setRepresentanteAdquirienteBean(representanteAdquirientePantalla);

			//------------------------TRANSMITENTE-------------------------------
			BeanPQTramiteTraficoGuardarInterviniente beanPQTramiteTraficoGuardarTransmitente = beanTransmision.getBeanGuardarTransmitente();
			beanPQTramiteTraficoGuardarTransmitente.setP_NUM_EXPEDIENTE(numExpediente);

			// Mantis 16626. David Sierra: Mensaje de error si no se rellena el campo NIF/CIF
			if ((beanPQTramiteTraficoGuardarTransmitente.getP_NIF()==null || beanPQTramiteTraficoGuardarTransmitente.getP_NIF().isEmpty())) {
				resultado.setMensaje("El Transmitente no se ha guardado: falta rellenar el campo obligatorio NIF/CIF");
			}

			if ((beanPQTramiteTraficoGuardarTransmitente.getP_NIF()!=null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarTransmitente.getP_NIF())) ||
					(beanPQTramiteTraficoGuardarTransmitente.getP_TIPO_INTERVINIENTE()!=null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarTransmitente.getP_TIPO_INTERVINIENTE()))){
				//Ejecutamos el procedimiento de guardar el transmitente
				resultadoTramiteTraficoGuardarIntervinienteTransmitente = ejecutarProc(beanPQTramiteTraficoGuardarTransmitente,valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,GUARDAR_INTERVINIENTE,BeanPQGeneral.class);

				//Recuperamos información de respuesta
				BigDecimal pCodeTransmitente = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteTransmitente.getParametro(ConstantesPQ.P_CODE);
				String pSqlErrmTransmitente = (String)resultadoTramiteTraficoGuardarIntervinienteTransmitente.getParametro(ConstantesPQ.P_SQLERRM);
				String pInformacionTransmitente = (String)resultadoTramiteTraficoGuardarIntervinienteTransmitente.getParametro(ConstantesPQ.P_INFORMACION);
				if (ConstantesPQ.pCodeOkAlt.equals(pCodeTransmitente)){
					if ((beanPQTramiteTraficoGuardarTransmitente.getP_APELLIDO1_RAZON_SOCIAL()!=null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarTransmitente.getP_APELLIDO1_RAZON_SOCIAL())) ||
							(beanPQTramiteTraficoGuardarTransmitente.getP_NOMBRE()!=null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarTransmitente.getP_NOMBRE()))){
						resultado.setMensaje("El transmitente no se ha guardado: Faltan datos obligatorios");
					}
					transmitentePantalla = beanPantallaTransmision.getTransmitenteBean();
				} else if (!ConstantesPQ.pCodeOk.equals(pCodeTransmitente)){
					log.error(" El transmitente no se ha guardado. ", numExpediente.toString());
					resultado.setMensaje("Error al guardar el transmitente: "+pSqlErrmTransmitente);
					if (pInformacionTransmitente != null && !CADENA_VACIA.equals(pInformacionTransmitente)){
						resultado.setMensaje(pInformacionTransmitente);
					}
					transmitentePantalla = beanPantallaTransmision.getTransmitenteBean();
				} else {
					//Si viene p_informacion hay que mostrarlo
					if(pInformacionTransmitente!=null && !CADENA_VACIA.equals(pInformacionTransmitente)){
						resultado.setMensaje("Transmitente: "+pInformacionTransmitente);
					}
					//Convertimos a bean de pantalla
					transmitentePantalla = resultadoTramiteTraficoGuardarIntervinienteTransmitente != null ? intervinienteTraficoBeanPQConversion.convertirPQToBean(resultadoTramiteTraficoGuardarIntervinienteTransmitente) : null;
					//ROCIO: Puede cambiar el estado del trámite al guardar un interviniente
					transmisionPantalla.getTramiteTraficoBean().setEstado(transmitentePantalla.getEstadoTramite() != null ? transmitentePantalla.getEstadoTramite().getValorEnum() : null);
				}
			}

			//Seteamos
			transmisionPantalla.setTransmitenteBean(transmitentePantalla);

			//-------------------------REPRESENTANTE DEL TRANSMITENTE-------------------------
			BeanPQTramiteTraficoGuardarInterviniente beanPQTramiteTraficoGuardarRepresentanteTransmitente = beanTransmision.getBeanGuardarRepresentanteTransmitente();
			beanPQTramiteTraficoGuardarRepresentanteTransmitente.setP_NUM_EXPEDIENTE(numExpediente);

			if ((beanPQTramiteTraficoGuardarRepresentanteTransmitente.getP_NIF() != null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarRepresentanteTransmitente.getP_NIF())) ||
					(beanPQTramiteTraficoGuardarRepresentanteTransmitente.getP_TIPO_INTERVINIENTE()!=null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarRepresentanteTransmitente.getP_TIPO_INTERVINIENTE()))){
				//Ejecutamos el procedimiento de guardar el representante del transmitente
				resultadoTramiteTraficoGuardarIntervinienteRepresentanteTransmitente = ejecutarProc(beanPQTramiteTraficoGuardarRepresentanteTransmitente,valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,GUARDAR_INTERVINIENTE,BeanPQGeneral.class);

				//Recuperamos información de respuesta
				BigDecimal pCodeRepresentanteTransmitente = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteRepresentanteTransmitente.getParametro(ConstantesPQ.P_CODE);
				String pSqlErrmRepresentanteTransmitente = (String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteTransmitente.getParametro(ConstantesPQ.P_SQLERRM);
				String pInformacionRepresentanteTransmitente = (String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteTransmitente.getParametro(ConstantesPQ.P_INFORMACION);

				if (ConstantesPQ.pCodeOkAlt.equals(pCodeRepresentanteTransmitente)){
					if ((beanPQTramiteTraficoGuardarRepresentanteTransmitente.getP_APELLIDO1_RAZON_SOCIAL() != null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarRepresentanteTransmitente.getP_APELLIDO1_RAZON_SOCIAL())) ||
							(beanPQTramiteTraficoGuardarRepresentanteTransmitente.getP_NOMBRE() != null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarRepresentanteTransmitente.getP_NOMBRE()))){
						resultado.setMensaje("El representante del transmitente no se ha guardado: Faltan datos obligatorios");
					}
					representanteTransmitentePantalla = beanPantallaTransmision.getRepresentanteTransmitenteBean();
				} else if (!ConstantesPQ.pCodeOk.equals(pCodeRepresentanteTransmitente)){
					log.error("El representante del transmitente no se ha guardado. ", numExpediente.toString());
					resultado.setMensaje("Error al guardar el representante del transmitente: "+pSqlErrmRepresentanteTransmitente);
					if (pInformacionRepresentanteTransmitente != null && !CADENA_VACIA.equals(pInformacionRepresentanteTransmitente)){
						resultado.setMensaje(pInformacionRepresentanteTransmitente);
					}
					representanteTransmitentePantalla = beanPantallaTransmision.getRepresentanteTransmitenteBean();
				} else {
					//Si viene p_informacion hay que mostrarlo
					if (pInformacionRepresentanteTransmitente != null && !CADENA_VACIA.equals(pInformacionRepresentanteTransmitente)){
						resultado.setMensaje("Repr. Transmitente: "+pInformacionRepresentanteTransmitente);
					}
					//Convertimos a bean de pantalla
					representanteTransmitentePantalla = resultadoTramiteTraficoGuardarIntervinienteRepresentanteTransmitente != null ? intervinienteTraficoBeanPQConversion.convertirPQToBean(resultadoTramiteTraficoGuardarIntervinienteRepresentanteTransmitente) : null;
					//ROCIO: Puede cambiar el estado del trámite al guardar un interviniente
					transmisionPantalla.getTramiteTraficoBean().setEstado(representanteTransmitentePantalla.getEstadoTramite() != null ? representanteTransmitentePantalla.getEstadoTramite().getValorEnum() : null);
				}
			}

			//Seteamos
			transmisionPantalla.setRepresentanteTransmitenteBean(representanteTransmitentePantalla);

			//------------------------PRIMER COTITULAR TRANSMITENTE-------------------------------
			BeanPQTramiteTraficoGuardarInterviniente beanPQTramiteTraficoGuardarPrimerCotitularTransmitente = beanTransmision.getBeanGuardarPrimerCotitularTransmitente();
			beanPQTramiteTraficoGuardarPrimerCotitularTransmitente.setP_NUM_EXPEDIENTE(numExpediente);

			if((beanPQTramiteTraficoGuardarPrimerCotitularTransmitente.getP_NIF()!=null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarPrimerCotitularTransmitente.getP_NIF())) ||
					(beanPQTramiteTraficoGuardarPrimerCotitularTransmitente.getP_TIPO_INTERVINIENTE()!=null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarPrimerCotitularTransmitente.getP_TIPO_INTERVINIENTE()))){
				//Ejecutamos el procedimiento de guardar el transmitente
				resultadoTramiteTraficoGuardarIntervinientePrimerCotitularTransmitente = ejecutarProc(beanPQTramiteTraficoGuardarPrimerCotitularTransmitente,valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,GUARDAR_INTERVINIENTE,BeanPQGeneral.class);

				//Recuperamos información de respuesta
				BigDecimal pCodePrimerCotitularTransmitente = (BigDecimal)resultadoTramiteTraficoGuardarIntervinientePrimerCotitularTransmitente.getParametro(ConstantesPQ.P_CODE);
				String pSqlErrmPrimerCotitularTransmitente = (String)resultadoTramiteTraficoGuardarIntervinientePrimerCotitularTransmitente.getParametro(ConstantesPQ.P_SQLERRM);
				String pInformacionPrimerCotitularTransmitente = (String)resultadoTramiteTraficoGuardarIntervinientePrimerCotitularTransmitente.getParametro(ConstantesPQ.P_INFORMACION);
				if (ConstantesPQ.pCodeOkAlt.equals(pCodePrimerCotitularTransmitente)){
					if((beanPQTramiteTraficoGuardarPrimerCotitularTransmitente.getP_APELLIDO1_RAZON_SOCIAL()!=null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarPrimerCotitularTransmitente.getP_APELLIDO1_RAZON_SOCIAL())) ||
							(beanPQTramiteTraficoGuardarPrimerCotitularTransmitente.getP_NOMBRE()!=null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarPrimerCotitularTransmitente.getP_NOMBRE()))){
						resultado.setMensaje("El primer cotitular transmitente no se ha guardado: Faltan datos obligatorios");
					}
					primerCotitularTransmitentePantalla = beanPantallaTransmision.getPrimerCotitularTransmitenteBean();
				} else if (!ConstantesPQ.pCodeOk.equals(pCodePrimerCotitularTransmitente)){
					log.error(" El primer cotitular transmitente no se ha guardado. ", numExpediente.toString());
					resultado.setMensaje("Error al guardar el primer cotitular transmitente: "+pSqlErrmPrimerCotitularTransmitente);
					if(pInformacionPrimerCotitularTransmitente!=null && !CADENA_VACIA.equals(pInformacionPrimerCotitularTransmitente)){
						resultado.setMensaje(pInformacionPrimerCotitularTransmitente);
					}
					primerCotitularTransmitentePantalla = beanPantallaTransmision.getPrimerCotitularTransmitenteBean();
				} else {
					//Si viene p_informacion hay que mostrarlo
					if (pInformacionPrimerCotitularTransmitente!=null && !CADENA_VACIA.equals(pInformacionPrimerCotitularTransmitente)) {
						resultado.setMensaje("Primer Cotitular Transmitente: "+pInformacionPrimerCotitularTransmitente);
					}
					//Convertimos a bean de pantalla
					primerCotitularTransmitentePantalla = resultadoTramiteTraficoGuardarIntervinientePrimerCotitularTransmitente!=null?intervinienteTraficoBeanPQConversion.convertirPQToBean(resultadoTramiteTraficoGuardarIntervinientePrimerCotitularTransmitente):null;
					//ROCÍO: Puede cambiar el estado del trámite al guardar un interviniente
					transmisionPantalla.getTramiteTraficoBean().setEstado(primerCotitularTransmitentePantalla.getEstadoTramite()!= null ?primerCotitularTransmitentePantalla.getEstadoTramite().getValorEnum():null);
				}
			}

			//Seteamos
			transmisionPantalla.setPrimerCotitularTransmitenteBean(primerCotitularTransmitentePantalla);

			//-------------------------REPRESENTANTE DEL PRIMER COTITULAR TRANSMITENTE-------------------------
			BeanPQTramiteTraficoGuardarInterviniente beanPQTramiteTraficoGuardarRepresentantePrimerCotitularTransmitente = beanTransmision.getBeanGuardarRepresentantePrimerCotitularTransmitente();
			beanPQTramiteTraficoGuardarRepresentantePrimerCotitularTransmitente.setP_NUM_EXPEDIENTE(numExpediente);

			if (beanPQTramiteTraficoGuardarRepresentantePrimerCotitularTransmitente.getP_NIF()!=null && !beanPQTramiteTraficoGuardarRepresentantePrimerCotitularTransmitente.getP_NIF().equals(CADENA_VACIA)){
				// Ejecutamos el procedimiento de guardar el representante del transmitente
				resultadoTramiteTraficoGuardarIntervinienteRepresentantePrimerCotitularTransmitente = ejecutarProc(beanPQTramiteTraficoGuardarRepresentantePrimerCotitularTransmitente,valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,GUARDAR_INTERVINIENTE,BeanPQGeneral.class);

				// Recuperamos información de respuesta
				BigDecimal pCodeRepresentantePrimerCotitularTransmitente = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteRepresentantePrimerCotitularTransmitente.getParametro(ConstantesPQ.P_CODE);
				String pSqlErrmRepresentantePrimerCotitularTransmitente = (String)resultadoTramiteTraficoGuardarIntervinienteRepresentantePrimerCotitularTransmitente.getParametro(ConstantesPQ.P_SQLERRM);
				String pInformacionRepresentantePrimerCotitularTransmitente = (String)resultadoTramiteTraficoGuardarIntervinienteRepresentantePrimerCotitularTransmitente.getParametro(ConstantesPQ.P_INFORMACION);

				if (!ConstantesPQ.pCodeOk.equals(pCodeRepresentantePrimerCotitularTransmitente)){
					log.debug("El representante del primer cotitular transmitente no se ha guardado.");
					resultado.setMensaje("Error al guardar el representante del primer cotitular transmitente: "+pSqlErrmRepresentantePrimerCotitularTransmitente);
					if(pInformacionRepresentantePrimerCotitularTransmitente!=null && !pInformacionRepresentantePrimerCotitularTransmitente.equals(CADENA_VACIA)){
						resultado.setMensaje(pInformacionRepresentantePrimerCotitularTransmitente);
					}
				} else { // Convertimos a bean de pantalla
					representantePrimerCotitularTransmitentePantalla = resultadoTramiteTraficoGuardarIntervinienteRepresentantePrimerCotitularTransmitente!=null ? intervinienteTraficoBeanPQConversion.convertirPQToBean(resultadoTramiteTraficoGuardarIntervinienteRepresentantePrimerCotitularTransmitente) : null;
					transmisionPantalla.setRepresentantePrimerCotitularTransmitenteBean(representantePrimerCotitularTransmitentePantalla);
				}
			}

			//------------------------SEGUNDO COTITULAR TRANSMITENTE-------------------------------
			BeanPQTramiteTraficoGuardarInterviniente beanPQTramiteTraficoGuardarSegundoCotitularTransmitente = beanTransmision.getBeanGuardarSegundoCotitularTransmitente();
			beanPQTramiteTraficoGuardarSegundoCotitularTransmitente.setP_NUM_EXPEDIENTE(numExpediente);
			beanPQTramiteTraficoGuardarSegundoCotitularTransmitente.setP_NUM_INTERVINIENTE(new BigDecimal(2));
			if((beanPQTramiteTraficoGuardarSegundoCotitularTransmitente.getP_NIF()!=null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarSegundoCotitularTransmitente.getP_NIF())) ||
					(beanPQTramiteTraficoGuardarSegundoCotitularTransmitente.getP_TIPO_INTERVINIENTE()!=null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarSegundoCotitularTransmitente.getP_TIPO_INTERVINIENTE()))){
				//Ejecutamos el procedimiento de guardar el transmitente
				resultadoTramiteTraficoGuardarIntervinienteSegundoCotitularTransmitente = ejecutarProc(beanPQTramiteTraficoGuardarSegundoCotitularTransmitente,valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,GUARDAR_INTERVINIENTE,BeanPQGeneral.class);

				//Recuperamos información de respuesta
				BigDecimal pCodeSegundoCotitularTransmitente = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteSegundoCotitularTransmitente.getParametro(ConstantesPQ.P_CODE);
				String pSqlErrmSegundoCotitularTransmitente = (String)resultadoTramiteTraficoGuardarIntervinienteSegundoCotitularTransmitente.getParametro(ConstantesPQ.P_SQLERRM);
				String pInformacionSegundoCotitularTransmitente = (String)resultadoTramiteTraficoGuardarIntervinienteSegundoCotitularTransmitente.getParametro(ConstantesPQ.P_INFORMACION);

				if (ConstantesPQ.pCodeOkAlt.equals(pCodeSegundoCotitularTransmitente)) {
					if ((beanPQTramiteTraficoGuardarSegundoCotitularTransmitente.getP_APELLIDO1_RAZON_SOCIAL()!=null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarSegundoCotitularTransmitente.getP_APELLIDO1_RAZON_SOCIAL())) ||
							(beanPQTramiteTraficoGuardarSegundoCotitularTransmitente.getP_NOMBRE()!=null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarSegundoCotitularTransmitente.getP_NOMBRE()))) {
						resultado.setMensaje("El segundo cotitular transmitente no se ha guardado: Faltan datos obligatorios");
					}
					segundoCotitularTransmitentePantalla = beanPantallaTransmision.getSegundoCotitularTransmitenteBean();
				} else if (!ConstantesPQ.pCodeOk.equals(pCodeSegundoCotitularTransmitente)){
					log.error("El segundo cotitular transmitente no se ha guardado. ", numExpediente.toString());
					resultado.setMensaje("Error al guardar el segundo cotitular transmitente: "+pSqlErrmSegundoCotitularTransmitente);
					if(pInformacionSegundoCotitularTransmitente!=null && !CADENA_VACIA.equals(pInformacionSegundoCotitularTransmitente)){
						resultado.setMensaje(pInformacionSegundoCotitularTransmitente);
					}
					segundoCotitularTransmitentePantalla = beanPantallaTransmision.getSegundoCotitularTransmitenteBean();
				} else {
					//Si viene p_informacion hay que mostrarlo
					if(pInformacionSegundoCotitularTransmitente!=null && !CADENA_VACIA.equals(pInformacionSegundoCotitularTransmitente)){
						resultado.setMensaje("Segundo Cotitular Transmitente: "+pInformacionSegundoCotitularTransmitente);
					}
					//Convertimos a bean de pantalla
					segundoCotitularTransmitentePantalla = resultadoTramiteTraficoGuardarIntervinienteSegundoCotitularTransmitente!=null?intervinienteTraficoBeanPQConversion.convertirPQToBean(resultadoTramiteTraficoGuardarIntervinienteSegundoCotitularTransmitente):null;
					//ROCIO: Puede cambiar el estado del trámite al guardar un interviniente
					transmisionPantalla.getTramiteTraficoBean().setEstado(segundoCotitularTransmitentePantalla.getEstadoTramite()!= null ?segundoCotitularTransmitentePantalla.getEstadoTramite().getValorEnum():null);
				}
			}

			//Seteamos
			transmisionPantalla.setSegundoCotitularTransmitenteBean(segundoCotitularTransmitentePantalla);

			//-------------------------REPRESENTANTE DEL SEGUNDO COTITULAR TRANSMITENTE-------------------------
			BeanPQTramiteTraficoGuardarInterviniente beanPQTramiteTraficoGuardarRepresentanteSegundoCotitularTransmitente = beanTransmision.getBeanGuardarRepresentanteSegundoCotitularTransmitente();
			beanPQTramiteTraficoGuardarRepresentanteSegundoCotitularTransmitente.setP_NUM_EXPEDIENTE(numExpediente);

			if(beanPQTramiteTraficoGuardarRepresentanteSegundoCotitularTransmitente.getP_NIF()!=null && !beanPQTramiteTraficoGuardarRepresentanteSegundoCotitularTransmitente.getP_NIF().equals(CADENA_VACIA)){
				//Ejecutamos el procedimiento de guardar el representante del transmitente
				resultadoTramiteTraficoGuardarIntervinienteRepresentanteSegundoCotitularTransmitente = ejecutarProc(beanPQTramiteTraficoGuardarRepresentanteSegundoCotitularTransmitente,valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,GUARDAR_INTERVINIENTE,BeanPQGeneral.class);

				//Recuperamos información de respuesta
				BigDecimal pCodeRepresentanteSegundoCotitularTransmitente = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteRepresentanteSegundoCotitularTransmitente.getParametro(ConstantesPQ.P_CODE);
				String pSqlErrmRepresentanteSegundoCotitularTransmitente = (String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteSegundoCotitularTransmitente.getParametro(ConstantesPQ.P_SQLERRM);
				String pInformacionRepresentanteSegundoCotitularTransmitente = (String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteSegundoCotitularTransmitente.getParametro(ConstantesPQ.P_INFORMACION);

				if (!ConstantesPQ.pCodeOk.equals(pCodeRepresentanteSegundoCotitularTransmitente)){
					log.debug("El representante del segundo cotitular transmitente no se ha guardado.");
					resultado.setMensaje("Error al guardar el representante del segundo cotitular transmitente: "+pSqlErrmRepresentanteSegundoCotitularTransmitente);
					if(pInformacionRepresentanteSegundoCotitularTransmitente!=null && !pInformacionRepresentanteSegundoCotitularTransmitente.equals(CADENA_VACIA)){
						resultado.setMensaje(pInformacionRepresentanteSegundoCotitularTransmitente);
					}
				} else { //Convertimos a bean de pantalla
					representanteSegundoCotitularTransmitentePantalla = resultadoTramiteTraficoGuardarIntervinienteRepresentanteSegundoCotitularTransmitente!=null?intervinienteTraficoBeanPQConversion.convertirPQToBean(resultadoTramiteTraficoGuardarIntervinienteRepresentanteSegundoCotitularTransmitente):null;
					transmisionPantalla.setRepresentanteSegundoCotitularTransmitenteBean(representanteSegundoCotitularTransmitentePantalla);
				}
			}

			//------------------------PRESENTADOR-------------------------------
			BeanPQTramiteTraficoGuardarInterviniente beanPQTramiteTraficoGuardarPresentador = beanTransmision.getBeanGuardarPresentador();
			beanPQTramiteTraficoGuardarPresentador.setP_NUM_EXPEDIENTE(numExpediente);

			if ((beanPQTramiteTraficoGuardarPresentador.getP_NIF() != null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarPresentador.getP_NIF())) ||
					(beanPQTramiteTraficoGuardarPresentador.getP_TIPO_INTERVINIENTE()!=null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarPresentador.getP_TIPO_INTERVINIENTE()))){
				//Ejecutamos el procedimiento de guardar el presentador
				resultadoTramiteTraficoGuardarIntervinientePresentador = ejecutarProc(beanPQTramiteTraficoGuardarPresentador, valoresSchemas.getSchema(), ValoresCatalog.PQ_TRAMITE_TRAFICO,GUARDAR_INTERVINIENTE, BeanPQGeneral.class);

				//Recuperamos información de respuesta
				BigDecimal pCodePresentador = (BigDecimal)resultadoTramiteTraficoGuardarIntervinientePresentador.getParametro(ConstantesPQ.P_CODE);
				String pSqlErrmPresentador = (String)resultadoTramiteTraficoGuardarIntervinientePresentador.getParametro(ConstantesPQ.P_SQLERRM);
				String pInformacionPresentador = (String)resultadoTramiteTraficoGuardarIntervinientePresentador.getParametro(ConstantesPQ.P_INFORMACION);

				if (ConstantesPQ.pCodeOkAlt.equals(pCodePresentador)) {
					if ((beanPQTramiteTraficoGuardarPresentador.getP_APELLIDO1_RAZON_SOCIAL()!=null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarPresentador.getP_APELLIDO1_RAZON_SOCIAL())) ||
								(beanPQTramiteTraficoGuardarPresentador.getP_NOMBRE()!=null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarPresentador.getP_NOMBRE()))){
						resultado.setMensaje("El presentador no se ha guardado: Faltan datos obligatorios");
					}
					presentadorPantalla = beanPantallaTransmision.getPresentadorBean();
				} else if (!ConstantesPQ.pCodeOk.equals(pCodePresentador)){
					log.error("El presentador no se ha guardado. ", numExpediente.toString());
					resultado.setMensaje("Error al guardar el presentador: "+pSqlErrmPresentador);
					if(pInformacionPresentador!=null && !CADENA_VACIA.equals(pInformacionPresentador)){
						resultado.setMensaje(pInformacionPresentador);
					}
					presentadorPantalla = beanPantallaTransmision.getPresentadorBean();
				} else {
					//Si viene p_informacion hay que mostrarlo
					if(pInformacionPresentador!=null && !CADENA_VACIA.equals(pInformacionPresentador)){
						resultado.setMensaje("Presentador: "+pInformacionPresentador);
					}
					//Convertimos a bean de pantalla
					presentadorPantalla = resultadoTramiteTraficoGuardarIntervinientePresentador!=null ? intervinienteTraficoBeanPQConversion.convertirPQToBean(resultadoTramiteTraficoGuardarIntervinientePresentador):null;
					//ROCIO: Puede cambiar el estado del trámite al guardar un interviniente
					transmisionPantalla.getTramiteTraficoBean().setEstado(presentadorPantalla.getEstadoTramite()!= null ?presentadorPantalla.getEstadoTramite().getValorEnum():null);
				}
			}
			//Seteamos
			transmisionPantalla.setPresentadorBean(presentadorPantalla);

			//------------------------POSEEDOR-------------------------------
			BeanPQTramiteTraficoGuardarInterviniente beanPQTramiteTraficoGuardarPoseedor = beanTransmision.getBeanGuardarPoseedor();
			beanPQTramiteTraficoGuardarPoseedor.setP_NUM_EXPEDIENTE(numExpediente);

			if ((beanPQTramiteTraficoGuardarPoseedor.getP_NIF()!=null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarPoseedor.getP_NIF())) ||
					(beanPQTramiteTraficoGuardarPoseedor.getP_TIPO_INTERVINIENTE()!=null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarPoseedor.getP_TIPO_INTERVINIENTE()))){
				//Ejecutamos el procedimiento de guardar el poseedor
				resultadoTramiteTraficoGuardarIntervinientePoseedor = ejecutarProc(beanPQTramiteTraficoGuardarPoseedor, valoresSchemas.getSchema(), ValoresCatalog.PQ_TRAMITE_TRAFICO,GUARDAR_INTERVINIENTE, BeanPQGeneral.class);

				//Recuperamos información de respuesta
				BigDecimal pCodePoseedor = (BigDecimal)resultadoTramiteTraficoGuardarIntervinientePoseedor.getParametro(ConstantesPQ.P_CODE);
				String pSqlErrmPoseedor = (String)resultadoTramiteTraficoGuardarIntervinientePoseedor.getParametro(ConstantesPQ.P_SQLERRM);
				String pInformacionPoseedor = (String)resultadoTramiteTraficoGuardarIntervinientePoseedor.getParametro(ConstantesPQ.P_INFORMACION);

				if (pInformacionPoseedor != null)
					pInformacionPoseedor.replace("Número,", CADENA_VACIA);

				if (ConstantesPQ.pCodeOkAlt.equals(pCodePoseedor)) {
					if ((beanPQTramiteTraficoGuardarPoseedor.getP_APELLIDO1_RAZON_SOCIAL()!=null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarPoseedor.getP_APELLIDO1_RAZON_SOCIAL())) ||
								(beanPQTramiteTraficoGuardarPoseedor.getP_NOMBRE()!=null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarPoseedor.getP_NOMBRE()))){
						resultado.setMensaje("El poseedor no se ha guardado: Faltan datos obligatorios");
					}
					poseedorPantalla = beanPantallaTransmision.getPoseedorBean();
				} else if (!ConstantesPQ.pCodeOk.equals(pCodePoseedor)){
					log.error("El poseedor no se ha guardado. ", numExpediente.toString());
					resultado.setMensaje("Error al guardar el poseedor: "+pSqlErrmPoseedor);
					if(pInformacionPoseedor!=null && !CADENA_VACIA.equals(pInformacionPoseedor)){
						resultado.setMensaje(pInformacionPoseedor);
					}
					poseedorPantalla = beanPantallaTransmision.getPoseedorBean();
				} else {
					//Si viene p_informacion hay que mostrarlo
					if (pInformacionPoseedor!=null && !CADENA_VACIA.equals(pInformacionPoseedor)) {
						resultado.setMensaje("Poseedor: "+pInformacionPoseedor);
					}
					//Convertimos a bean de pantalla
					poseedorPantalla = resultadoTramiteTraficoGuardarIntervinientePoseedor!=null ? intervinienteTraficoBeanPQConversion.convertirPQToBean(resultadoTramiteTraficoGuardarIntervinientePoseedor) : null;
					//ROCIO: Puede cambiar el estado del trámite al guardar un interviniente
					transmisionPantalla.getTramiteTraficoBean().setEstado(poseedorPantalla.getEstadoTramite()!= null ? poseedorPantalla.getEstadoTramite().getValorEnum() : null);
				}
			}

			//Seteamos
			transmisionPantalla.setPoseedorBean(poseedorPantalla);

			//-------------------------REPRESENTANTE DEL POSEEDOR-------------------------
			BeanPQTramiteTraficoGuardarInterviniente beanPQTramiteTraficoGuardarRepresentantePoseedor = beanTransmision.getBeanGuardarRepresentantePoseedor();
			beanPQTramiteTraficoGuardarRepresentantePoseedor.setP_NUM_EXPEDIENTE(numExpediente);

			if((beanPQTramiteTraficoGuardarRepresentantePoseedor.getP_NIF()!=null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarRepresentantePoseedor.getP_NIF())) ||
					(beanPQTramiteTraficoGuardarRepresentantePoseedor.getP_TIPO_INTERVINIENTE()!=null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarRepresentantePoseedor.getP_TIPO_INTERVINIENTE()))){
				//Ejecutamos el procedimiento de guardar el representante del Poseedor
				resultadoTramiteTraficoGuardarIntervinienteRepresentantePoseedor = ejecutarProc(beanPQTramiteTraficoGuardarRepresentantePoseedor, valoresSchemas.getSchema(), ValoresCatalog.PQ_TRAMITE_TRAFICO,GUARDAR_INTERVINIENTE, BeanPQGeneral.class);

				//Recuperamos información de respuesta
				BigDecimal pCodeRepresentantePoseedor = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteRepresentantePoseedor.getParametro(ConstantesPQ.P_CODE);
				String pSqlErrmRepresentantePoseedor = (String)resultadoTramiteTraficoGuardarIntervinienteRepresentantePoseedor.getParametro(ConstantesPQ.P_SQLERRM);
				String pInformacionRepresentantePoseedor = (String)resultadoTramiteTraficoGuardarIntervinienteRepresentantePoseedor.getParametro(ConstantesPQ.P_INFORMACION);

				if (ConstantesPQ.pCodeOkAlt.equals(pCodeRepresentantePoseedor)){
					if ((beanPQTramiteTraficoGuardarRepresentantePoseedor.getP_APELLIDO1_RAZON_SOCIAL() != null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarRepresentantePoseedor.getP_APELLIDO1_RAZON_SOCIAL())) ||
								(beanPQTramiteTraficoGuardarRepresentantePoseedor.getP_NOMBRE()!=null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarRepresentantePoseedor.getP_NOMBRE()))){
						resultado.setMensaje("El representante del poseedor no se ha guardado: Faltan datos obligatorios");
					}
					representantePoseedorPantalla = beanPantallaTransmision.getRepresentantePoseedorBean();
				} else if (!ConstantesPQ.pCodeOk.equals(pCodeRepresentantePoseedor)) {
					log.error(" El representante del poseedor no se ha guardado. ", numExpediente.toString());
					resultado.setMensaje("Error al guardar el representante del poseedor: "+pSqlErrmRepresentantePoseedor);
					if (pInformacionRepresentantePoseedor != null && !CADENA_VACIA.equals(pInformacionRepresentantePoseedor)) {
						resultado.setMensaje(pInformacionRepresentantePoseedor);
					}
					representantePoseedorPantalla = beanPantallaTransmision.getRepresentantePoseedorBean();
				} else {
					//Si viene p_informacion hay que mostrarlo
					if (pInformacionRepresentantePoseedor!=null && !CADENA_VACIA.equals(pInformacionRepresentantePoseedor)) {
						resultado.setMensaje("Repr. Poseedor: "+pInformacionRepresentantePoseedor);
					}
					//Convertimos a bean de pantalla
					representantePoseedorPantalla = resultadoTramiteTraficoGuardarIntervinienteRepresentantePoseedor!=null?intervinienteTraficoBeanPQConversion.convertirPQToBean(resultadoTramiteTraficoGuardarIntervinienteRepresentantePoseedor):null;
					//ROCIO: Puede cambiar el estado del trámite al guardar un interviniente
					transmisionPantalla.getTramiteTraficoBean().setEstado(representantePoseedorPantalla.getEstadoTramite()!= null ? representantePoseedorPantalla.getEstadoTramite().getValorEnum() : null);
				}
			}

			//Seteamos
			transmisionPantalla.setRepresentantePoseedorBean(representantePoseedorPantalla);

			//------------------------ARRENDATARIO-------------------------------
			BeanPQTramiteTraficoGuardarInterviniente beanPQTramiteTraficoGuardarArrendatario = beanTransmision.getBeanGuardarArrendatario();
			beanPQTramiteTraficoGuardarArrendatario.setP_NUM_EXPEDIENTE(numExpediente);

			if ((beanPQTramiteTraficoGuardarArrendatario.getP_NIF() != null
					&& !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarArrendatario.getP_NIF()))
					|| (beanPQTramiteTraficoGuardarArrendatario.getP_TIPO_INTERVINIENTE()!=null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarArrendatario.getP_TIPO_INTERVINIENTE()))) {
				// Ejecutamos el procedimiento de guardar el arrendatario
				resultadoTramiteTraficoGuardarIntervinienteArrendatario = ejecutarProc(beanPQTramiteTraficoGuardarArrendatario, valoresSchemas.getSchema(), ValoresCatalog.PQ_TRAMITE_TRAFICO,GUARDAR_INTERVINIENTE, BeanPQGeneral.class);

				// Recuperamos información de respuesta
				BigDecimal pCodeArrendatario = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteArrendatario.getParametro(ConstantesPQ.P_CODE);
				String pSqlErrmArrendatario = (String)resultadoTramiteTraficoGuardarIntervinienteArrendatario.getParametro(ConstantesPQ.P_SQLERRM);
				String pInformacionArrendatario = (String)resultadoTramiteTraficoGuardarIntervinienteArrendatario.getParametro(ConstantesPQ.P_INFORMACION);

				if (ConstantesPQ.pCodeOkAlt.equals(pCodeArrendatario)) {
					if ((beanPQTramiteTraficoGuardarArrendatario.getP_APELLIDO1_RAZON_SOCIAL() != null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarArrendatario.getP_APELLIDO1_RAZON_SOCIAL())) ||
							(beanPQTramiteTraficoGuardarArrendatario.getP_NOMBRE() != null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarArrendatario.getP_NOMBRE()))) {
						resultado.setMensaje("El arrendatario no se ha guardado: Faltan datos obligatorios");
					}
					arrendatarioPantalla = beanPantallaTransmision.getArrendatarioBean();
				} else if (!ConstantesPQ.pCodeOk.equals(pCodeArrendatario)){
					log.error(" El arrendatario no se ha guardado. ", numExpediente.toString());
					resultado.setMensaje("Error al guardar el arrendatario: "+pSqlErrmArrendatario);
					if(pInformacionArrendatario != null && !CADENA_VACIA.equals(pInformacionArrendatario)){
						resultado.setMensaje(pInformacionArrendatario);
					}
					arrendatarioPantalla = beanPantallaTransmision.getArrendatarioBean();
				} else {
					// Si viene p_informacion hay que mostrarlo
					if (pInformacionArrendatario!=null && !CADENA_VACIA.equals(pInformacionArrendatario)) {
						resultado.setMensaje("Arrendatario: "+pInformacionArrendatario);
					}
					// Convertimos a bean de pantalla
					arrendatarioPantalla = resultadoTramiteTraficoGuardarIntervinienteArrendatario != null ? intervinienteTraficoBeanPQConversion.convertirPQToBean(resultadoTramiteTraficoGuardarIntervinienteArrendatario) : null;
					//ROCIO: Puede cambiar el estado del trámite al guardar un interviniente
					transmisionPantalla.getTramiteTraficoBean().setEstado(arrendatarioPantalla.getEstadoTramite() != null ? arrendatarioPantalla.getEstadoTramite().getValorEnum() : null);
				}
			}

			//Seteamos
			transmisionPantalla.setArrendatarioBean(arrendatarioPantalla);

			//-------------------------REPRESENTANTE DEL ARRENDATARIO-------------------------
			BeanPQTramiteTraficoGuardarInterviniente beanPQTramiteTraficoGuardarRepresentanteArrendatario = beanTransmision.getBeanGuardarRepresentanteArrendatario();
			beanPQTramiteTraficoGuardarRepresentanteArrendatario.setP_NUM_EXPEDIENTE(numExpediente);

			if ((beanPQTramiteTraficoGuardarRepresentanteArrendatario.getP_NIF() != null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarRepresentanteArrendatario.getP_NIF())) ||
					(beanPQTramiteTraficoGuardarRepresentanteArrendatario.getP_TIPO_INTERVINIENTE() != null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarRepresentanteArrendatario.getP_TIPO_INTERVINIENTE()))){
				//Ejecutamos el procedimiento de guardar el representante del Arrendatario
				resultadoTramiteTraficoGuardarIntervinienteRepresentanteArrendatario = ejecutarProc(beanPQTramiteTraficoGuardarRepresentanteArrendatario,valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,GUARDAR_INTERVINIENTE,BeanPQGeneral.class);

				//Recuperamos información de respuesta
				BigDecimal pCodeRepresentanteArrendatario = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteRepresentanteArrendatario.getParametro(ConstantesPQ.P_CODE);
				String pSqlErrmRepresentanteArrendatario = (String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteArrendatario.getParametro(ConstantesPQ.P_SQLERRM);
				String pInformacionRepresentanteArrendatario = (String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteArrendatario.getParametro(ConstantesPQ.P_INFORMACION);

				if (ConstantesPQ.pCodeOkAlt.equals(pCodeRepresentanteArrendatario)) {
					if ((beanPQTramiteTraficoGuardarRepresentanteArrendatario.getP_APELLIDO1_RAZON_SOCIAL() != null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarRepresentanteArrendatario.getP_APELLIDO1_RAZON_SOCIAL())) ||
							(beanPQTramiteTraficoGuardarRepresentanteArrendatario.getP_NOMBRE()!=null && !CADENA_VACIA.equals(beanPQTramiteTraficoGuardarRepresentanteArrendatario.getP_NOMBRE()))){
						resultado.setMensaje("El representante del arrendatario no se ha guardado: Faltan datos obligatorios");
					}
					representanteArrendatarioPantalla = beanPantallaTransmision.getRepresentanteArrendatarioBean();
				} else if (!ConstantesPQ.pCodeOk.equals(pCodeRepresentanteArrendatario)){
					log.error(" El representante del arrendatario no se ha guardado. ", numExpediente.toString());
					resultado.setMensaje("Error al guardar el representante del arrendatario: "+pSqlErrmRepresentanteArrendatario);
					if(pInformacionRepresentanteArrendatario != null && !CADENA_VACIA.equals(pInformacionRepresentanteArrendatario)){
						resultado.setMensaje(pInformacionRepresentanteArrendatario);
					}
					representanteArrendatarioPantalla = beanPantallaTransmision.getRepresentanteArrendatarioBean();
				} else {
					//Si viene p_informacion hay que mostrarlo
					if(pInformacionRepresentanteArrendatario!=null && !CADENA_VACIA.equals(pInformacionRepresentanteArrendatario)){
						resultado.setMensaje("Repr. Arrendatario: "+pInformacionRepresentanteArrendatario);
					}
					// Convertimos a bean de pantalla
					representanteArrendatarioPantalla = resultadoTramiteTraficoGuardarIntervinienteRepresentanteArrendatario != null ? intervinienteTraficoBeanPQConversion.convertirPQToBean(resultadoTramiteTraficoGuardarIntervinienteRepresentanteArrendatario):null;
					// ROCÍO: Puede cambiar el estado del trámite al guardar un interviniente
					transmisionPantalla.getTramiteTraficoBean().setEstado(representanteArrendatarioPantalla.getEstadoTramite() != null ? representanteArrendatarioPantalla.getEstadoTramite().getValorEnum() : null);
				}
			}

			//Seteamos
			transmisionPantalla.setRepresentanteArrendatarioBean(representanteArrendatarioPantalla);
			// Se ingresan datos del 620
			TramiteTrafTranVO tramiteTrafTranVO = servicioTramiteTrafico.getTramiteTransmision(transmisionPantalla.getTramiteTraficoBean().getNumExpediente(), true);
			tramiteTrafTranVO.setSubasta(beanPQTramiteTraficoGuardarTransmision.getP_SUBASTA() != null ? beanPQTramiteTraficoGuardarTransmision.getP_SUBASTA() : null);
			tramiteTrafTranVO.setTipoMotor(beanPQTramiteTraficoGuardarTransmision.getP_TIPO_MOTOR() != null ? beanPQTramiteTraficoGuardarTransmision.getP_TIPO_MOTOR() : null);
			tramiteTrafTranVO.setProcedencia(beanPQTramiteTraficoGuardarTransmision.getP_PROCEDENCIA_620() != null ? beanPQTramiteTraficoGuardarTransmision.getP_PROCEDENCIA_620() : null);
			tramiteTrafTranVO.setTipoReduccion(beanPQTramiteTraficoGuardarTransmision.getP_TIPO_REDUCCION() != null ? beanPQTramiteTraficoGuardarTransmision.getP_TIPO_REDUCCION() : null);
			tramiteTrafTranVO.setReduccionPorcentaje(beanPQTramiteTraficoGuardarTransmision.getP_REDUCCION_PORCENTAJE() != null ? beanPQTramiteTraficoGuardarTransmision.getP_REDUCCION_PORCENTAJE() : null);
			tramiteTrafTranVO.setReduccionImporte((tramiteTrafTranVO.getTipoReduccion() == null || tramiteTrafTranVO.getTipoReduccion().equals(CADENA_VACIA)) ? BigDecimal.valueOf(0.00) : tramiteTrafTranVO.getReduccionPorcentaje().multiply(tramiteTrafTranVO.getValorDeclarado()).divide(new BigDecimal(100)));

			try {
				servicioTramiteTraficoTransmision.guardarOActualizar(tramiteTrafTranVO);
			} catch (OegamExcepcion e) {
				log.error("Error al guardar trámite. ", e);
			}

			transmisionPantalla.setProcedencia(tramiteTrafTranVO.getProcedencia() != null ? tramiteTrafTranVO.getProcedencia() : null);
			transmisionPantalla.setSubasta(tramiteTrafTranVO.getSubasta() != null && tramiteTrafTranVO.getSubasta().equalsIgnoreCase(S) ? TRUE: FALSE);
			transmisionPantalla.setCheck620ValorManual(tramiteTrafTranVO.getCheckValorDeclarado() == null || tramiteTrafTranVO.getCheckValorDeclarado().equalsIgnoreCase(FALSE) ? FALSE: TRUE);
			transmisionPantalla.getTipoMotor().setIdTipoMotor(tramiteTrafTranVO.getTipoMotor() != null ? tramiteTrafTranVO.getTipoMotor() : null);

			transmisionPantalla.getReduccionCodigo().setTipoReduccion(tramiteTrafTranVO.getTipoReduccion() != null ? tramiteTrafTranVO.getTipoReduccion() : null);
			transmisionPantalla.getReduccionCodigo().setPorcentajeReduccion(tramiteTrafTranVO.getReduccionPorcentaje() != null ? tramiteTrafTranVO.getReduccionPorcentaje() : null);
			transmisionPantalla.setReduccionImporte(tramiteTrafTranVO.getReduccionImporte() != null ? tramiteTrafTranVO.getReduccionImporte() : null);

			// DVV
			if (transmisionPantalla.getTramiteTraficoBean() != null && transmisionPantalla.getTramiteTraficoBean().getVehiculo() != null
					&& tramiteTrafTranVO != null && tramiteTrafTranVO.getVehiculo() != null) {
				transmisionPantalla.getTramiteTraficoBean().getVehiculo().setEsSiniestro(tramiteTrafTranVO.getVehiculo().getEsSiniestro());
//				transmisionPantalla.getTramiteTraficoBean().getVehiculo().setTieneCargaFinanciera(tramiteTrafTranVO.getVehiculo().getTieneCargaFinanciera());
			}

//			transmisionPantalla.setCambioSocietario(tramiteTrafTranVO.getCambioSocietario() != null && tramiteTrafTranVO.getCambioSocietario());
//			transmisionPantalla.setModificacionNoConstante(tramiteTrafTranVO.getModificacionNoConstante() != null && tramiteTrafTranVO.getModificacionNoConstante());
//			if (tramiteTrafTranVO.getAcreditacionPago() != null) {
//				transmisionPantalla.setAcreditacionPago(Boolean.TRUE.equals(tramiteTrafTranVO.getAcreditacionPago()) ? "1" : "0");
//			}
		} else {
			//ERROR trámite, no se guardan los intervinientes
			resultado.setError(true);
			resultado.setMensaje("Error al guardar el trámite: "+sqlErrmTramite);
			if (pInformacionTramite != null && !CADENA_VACIA.equals(pInformacionTramite)) {
				resultado.setMensaje(pInformacionTramite);
			}
			resultado.setMensaje("No se han guardado los datos de los intervinientes.");
			transmisionPantalla = beanPantallaTransmision;
		}

		//JRG: Arreglo temporal para que cuando recupere el código de provincia si es de un dígito le meta un 0 delante.
		//La solución correcta sería cambiar el tipo de dato de vehiculo.provincia_primera_matricula a String (ahora es BigDecimal).
		int provinciaPrimeraMatricula = Integer.parseInt(transmisionPantalla.getTramiteTraficoBean().getVehiculo().getProvinciaPrimeraMatricula() != null ?
					transmisionPantalla.getTramiteTraficoBean().getVehiculo().getProvinciaPrimeraMatricula().getIdProvincia():"-1");
		//Si viene -1 entonces no debe concatenar el 0.
		if (provinciaPrimeraMatricula < 10 && provinciaPrimeraMatricula > 0){
			transmisionPantalla.getTramiteTraficoBean().getVehiculo().getProvinciaPrimeraMatricula().setIdProvincia("0" +
					transmisionPantalla.getTramiteTraficoBean().getVehiculo().getProvinciaPrimeraMatricula().getIdProvincia());
		}
		transmisionPantalla.setHistoricoCheckImpresionPermiso(transmisionPantalla.getImpresionPermiso() != null ? transmisionPantalla.getImpresionPermiso() : null);
		historicoCheckImpresionPermiso = impresionPermiso;
		resultadoAlta.put(ConstantesPQ.RESULTBEAN, resultado);
		resultadoAlta.put(ConstantesPQ.BEANPANTALLA, transmisionPantalla);
		return resultadoAlta;
	}

	@Override
	public ListaRegistros listarTabla(Integer pagina,
			Integer numeroElementosPagina, SortOrderEnum orden,
			String columnaOrden) {
		return null;
	}

	/**
	 * @author antonio.miguez
	 * @param BigDecimal
	 * @return HashMap<String,Object>
	 */
	/*
	 * Método para obtener el detalle de un trámite de transmisión de la BD.
	 */
	public Map<String, Object> obtenerDetalle(BigDecimal numExpediente) {
		HashMap<String, Object> resultadoMetodo = new HashMap<>();
		BeanPQDetalleTransmision beanConsultaTransmision = new BeanPQDetalleTransmision();
		ResultBean resultado = new ResultBean();
		Boolean error = false;
		String mensaje = CADENA_VACIA;

		beanConsultaTransmision.setP_NUM_EXPEDIENTE(numExpediente);

		//Ejecución genérica del acceso a la base de datos
		RespuestaGenerica resultadoBD = ejecutarProc(beanConsultaTransmision, valoresSchemas.getSchema(), ValoresCatalog.PQ_TRAMITE_TRAFICO, "DETALLE_TRANSMISION", BeanPQGeneral.class);

		//servicioTramiteTraficoTransmision.actualizarValorRealTransmision(beanPQTramiteTraficoGuardarTransmision.getP_NUM_EXPEDIENTE(), beanPQTramiteTraficoGuardarTransmision.getP_VALOR_REAL());

		//Mantis 25415: Recuperamos el valor real de la transmisión
		HashMap<String,Object> auxiliar = resultadoBD.getMapaParametros();

		TramiteTrafTranDto tramiteTrafDto = servicioTramiteTraficoTransmision.getTramiteTransmision(numExpediente, true);
		auxiliar.put("P_VALOR_REAL", tramiteTrafDto.getValorReal());
		//Cambio Modelo 620
		auxiliar.put("P_TIPO_MOTOR", tramiteTrafDto.getTipoMotor() != null ? tramiteTrafDto.getTipoMotor().getTipoMotor() : null);
		auxiliar.put("P_SUBASTA", tramiteTrafDto.getSubasta());
		auxiliar.put("P_PRESCRITA", tramiteTrafDto.getPrescrita());
		auxiliar.put("P_TIPO_REDUCCION", tramiteTrafDto.getTipoReduccion() != null ? tramiteTrafDto.getTipoReduccion().getTipoReduccion() : null);
		auxiliar.put("P_REDUCCION_PORCENTAJE", tramiteTrafDto.getTipoReduccion() != null ? tramiteTrafDto.getTipoReduccion().getPorcentaje() : BigDecimal.valueOf(0.00));
		auxiliar.put("P_REDUCCION_IMPORTE", tramiteTrafDto.getReduccionImporte());
		auxiliar.put("P_TRANSMITENTE", tramiteTrafDto.getIndTransmitente());
		auxiliar.put("P_PROCEDENCIA_620", tramiteTrafDto.getProcedencia());

		//DVV
//		auxiliar.put("P_CAMBIO_SOCIETARIO", tramiteTrafDto.getCambioSocietario());
//		auxiliar.put("P_MODIFICACION_NO_CONSTANTE", tramiteTrafDto.getModificacionNoConstante());
		auxiliar.put("P_ES_SINIESTRO", tramiteTrafDto.getVehiculoDto() != null && tramiteTrafDto.getVehiculoDto().getEsSiniestro() != null ? tramiteTrafDto.getVehiculoDto().getEsSiniestro() : false);
//		auxiliar.put("P_TIENE_CARGA_FINANCIERA", tramiteTrafDto.getVehiculoDto() != null && tramiteTrafDto.getVehiculoDto().getTieneCargaFinanciera() != null ? tramiteTrafDto.getVehiculoDto().getTieneCargaFinanciera() : false);
		auxiliar.put("P_ACREDITACION_PAGO", tramiteTrafDto.getAcreditacionPago());

		resultadoBD.setMapaParametros(auxiliar);

		// Recuperamos información de respuesta
		BigDecimal pCodeTramite = (BigDecimal)resultadoBD.getParametro(ConstantesPQ.P_CODE);
		log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoBD.getParametro(ConstantesPQ.P_SQLERRM));
		log.debug(ConstantesPQ.LOG_P_CUENTA + resultadoBD.getParametro("CUENTA"));

		// Se controla el error y se actualiza para su devolución
		if (!ConstantesPQ.pCodeOk.equals(pCodeTramite)){
			error = true;
			mensaje += (" - Se ha producido un error al obtener el trámite " + numExpediente + ": " +
					(String)resultadoBD.getParametro(ConstantesPQ.P_SQLERRM) + ". ");
		}
		//Convertimos y rellenamos los datos complejos del bean
		TramiteTraficoTransmisionBean beanDetalleTransmision = transmisionTramiteTraficoBeanPQConversion.convertirPQToBean(resultadoBD);

		String consentimiento = beanDetalleTransmision.getTramiteTraficoBean().getVehiculo().getConsentimiento();
		//3038

		ArrayList<FicheroInfo> ficherosSubidos = servicioTramiteTraficoTransmision.recuperarDocumentos(numExpediente);
		if (ficherosSubidos != null && !ficherosSubidos.isEmpty()) {
			beanDetalleTransmision.setFicherosSubidos(ficherosSubidos);
		}
		
		
		//Vehículo
		VehiculoBean vehiculo = getModeloVehiculo().cargaVehiculoPorId(beanDetalleTransmision.getTramiteTraficoBean().getVehiculo().getIdVehiculo());

		String consentimientoCambioLocalizacion = beanDetalleTransmision.getConsentimientoCambio();

		//Boolean vehiculoAgricola = beanDetalleTransmision.getTramiteTraficoBean().getVehiculo().getVehiculoAgricola();
		if (!"TRUE".equals(consentimientoCambioLocalizacion)) {
			// Le asignamos la del adquiriente
			beanDetalleTransmision.getTramiteTraficoBean().getVehiculo().setDireccionBean(beanDetalleTransmision.getAdquirienteBean().getPersona().getDireccion());
		}

		String fechaCaducidadITV = vehiculo.getCheckFechaCaducidadITV();
		beanDetalleTransmision.getTramiteTraficoBean().setVehiculo(vehiculo);
		beanDetalleTransmision.getTramiteTraficoBean().getVehiculo().setConsentimiento(consentimiento);
		//3038
		//beanDetalleTransmision.getTramiteTraficoBean().getVehiculo().setConsentimientoCambioLocalizacion(consentimientoCambioLocalizacion);

		// Se consideraba el check de vehículo agrícola dentro del trámite, no de los datos del vehículo.
		//beanDetalleTransmision.getTramiteTraficoBean().getVehiculo().setVehiculoAgricola(vehiculoAgricola);

		beanDetalleTransmision.getTramiteTraficoBean().getVehiculo().setCheckFechaCaducidadITV(fechaCaducidadITV);

		//DVV - Se establecen valores de BBDD para mostrarlos en pantalla
		if (beanDetalleTransmision.getTramiteTraficoBean() != null && beanDetalleTransmision.getTramiteTraficoBean().getVehiculo() != null) {
			beanDetalleTransmision.getTramiteTraficoBean().getVehiculo().setEsSiniestro(tramiteTrafDto.getVehiculoDto() != null && tramiteTrafDto.getVehiculoDto().getEsSiniestro() != null ? tramiteTrafDto.getVehiculoDto().getEsSiniestro() : false);
//			beanDetalleTransmision.getTramiteTraficoBean().getVehiculo().setTieneCargaFinanciera(tramiteTrafDto.getVehiculoDto() != null && tramiteTrafDto.getVehiculoDto().getTieneCargaFinanciera() != null ? tramiteTrafDto.getVehiculoDto().getTieneCargaFinanciera() : false);
		}

		//Cargar la relación entre el vehículo y el trámite tráfico
		VehiculoTramiteTraficoBean vehiculoTramiteTraficoBean = vehiculo.getVehiculoTramiteTraficoBean();
		vehiculoTramiteTraficoBean.setIdVehiculo(beanDetalleTransmision.getTramiteTraficoBean().getVehiculo().getIdVehiculo());
		vehiculoTramiteTraficoBean.setNumColegiado(beanDetalleTransmision.getTramiteTraficoBean().getNumColegiado());
		vehiculoTramiteTraficoBean.setNumExpediente(numExpediente);
		vehiculoTramiteTraficoBean = ModeloVehiculoTramiteTrafico.detalleVehiculoTramiteTrafico(vehiculoTramiteTraficoBean);
		if (vehiculoTramiteTraficoBean != null) {
			vehiculo.setVehiculoTramiteTraficoBean(vehiculoTramiteTraficoBean);
		}

		//Intervinientes
		List<IntervinienteTrafico> listaIntervinienteTrafico = getModeloInterviniente().obtenerDetalleIntervinientes(numExpediente,
				beanDetalleTransmision.getTramiteTraficoBean().getNumColegiado(), beanDetalleTransmision.getTramiteTraficoBean().getIdContrato());

		for (IntervinienteTrafico intervinienteTrafico : listaIntervinienteTrafico) {
			// Conductor Habitual
			if (intervinienteTrafico.getTipoInterviniente().getValorEnum().equals(TipoInterviniente.ConductorHabitual.getValorEnum())) {
				beanDetalleTransmision.setConductorHabitualBean(intervinienteTrafico);
			} // Adquiriente
			else if(intervinienteTrafico.getTipoInterviniente().getValorEnum().equals(TipoInterviniente.Adquiriente.getValorEnum())){
				beanDetalleTransmision.setAdquirienteBean(intervinienteTrafico);
				beanDetalleTransmision.getAdquirienteBean().getPersona().setFechaNacimientoBean(utilesFecha.getFechaFracionada(beanDetalleTransmision.getAdquirienteBean().getPersona().getFechaNacimiento()));
			} // Representante Adquiriente
			else if(intervinienteTrafico.getTipoInterviniente().getValorEnum().equals(TipoInterviniente.RepresentanteAdquiriente.getValorEnum())){
				beanDetalleTransmision.setRepresentanteAdquirienteBean(intervinienteTrafico);
			} // Transmitente
			else if(intervinienteTrafico.getTipoInterviniente().getValorEnum().equals(TipoInterviniente.TransmitenteTrafico.getValorEnum())){
				beanDetalleTransmision.setTransmitenteBean(intervinienteTrafico);
				beanDetalleTransmision.getTransmitenteBean().getPersona().setFechaNacimientoBean(utilesFecha.getFechaFracionada(beanDetalleTransmision.getTransmitenteBean().getPersona().getFechaNacimiento()));
			} // Representante Transmitente
			else if(intervinienteTrafico.getTipoInterviniente().getValorEnum().equals(TipoInterviniente.RepresentanteTransmitente.getValorEnum())){
				beanDetalleTransmision.setRepresentanteTransmitenteBean(intervinienteTrafico);
			} // Cotitular Transmitente (Primero y Segundo)
			else if (intervinienteTrafico.getTipoInterviniente().getValorEnum().equals(TipoInterviniente.CotitularTransmision.getValorEnum())) {
				if ("1".equals(String.valueOf(intervinienteTrafico.getNumInterviniente()))) {
					beanDetalleTransmision.setPrimerCotitularTransmitenteBean(intervinienteTrafico);
				} else if ("2".equals(String.valueOf(intervinienteTrafico.getNumInterviniente()))) {
					beanDetalleTransmision.setSegundoCotitularTransmitenteBean(intervinienteTrafico);
				}
			} // Presentador
			else if(intervinienteTrafico.getTipoInterviniente().getValorEnum().equals(TipoInterviniente.PresentadorTrafico.getValorEnum())){
				beanDetalleTransmision.setPresentadorBean(intervinienteTrafico);
				beanDetalleTransmision.getPresentadorBean().getPersona().setFechaNacimientoBean(utilesFecha.getFechaFracionada(beanDetalleTransmision.getPresentadorBean().getPersona().getFechaNacimiento()));
			} // Representante Primer Cotitular Transmitente
			else if(intervinienteTrafico.getTipoInterviniente().getValorEnum().equals(TipoInterviniente.RepresentantePrimerCotitularTransmision.getValorEnum())){
				beanDetalleTransmision.setRepresentantePrimerCotitularTransmitenteBean(intervinienteTrafico);
			} // Presentador
			else if(intervinienteTrafico.getTipoInterviniente().getValorEnum().equals(TipoInterviniente.RepresentanteSegundoCotitularTransmision.getValorEnum())){
				beanDetalleTransmision.setRepresentanteSegundoCotitularTransmitenteBean(intervinienteTrafico);
			} // Arrendatario
			else if(intervinienteTrafico.getTipoInterviniente().getValorEnum().equals(TipoInterviniente.Arrendatario.getValorEnum())){
				beanDetalleTransmision.setArrendatarioBean(intervinienteTrafico);
				beanDetalleTransmision.getArrendatarioBean().getPersona().setFechaNacimientoBean(utilesFecha.getFechaFracionada(beanDetalleTransmision.getArrendatarioBean().getPersona().getFechaNacimiento()));
			} // Representante Arrendatario
			else if(intervinienteTrafico.getTipoInterviniente().getValorEnum().equals(TipoInterviniente.RepresentanteArrendatario.getValorEnum())){
				beanDetalleTransmision.setRepresentanteArrendatarioBean(intervinienteTrafico);
			} //CompraVenta
			else if(intervinienteTrafico.getTipoInterviniente().getValorEnum().equals(TipoInterviniente.Compraventa.getValorEnum())){
				beanDetalleTransmision.setPoseedorBean(intervinienteTrafico);
				beanDetalleTransmision.getPoseedorBean().getPersona().setFechaNacimientoBean(utilesFecha.getFechaFracionada(beanDetalleTransmision.getPoseedorBean().getPersona().getFechaNacimiento()));
			} // Representate CompraVenta
			else if(intervinienteTrafico.getTipoInterviniente().getValorEnum().equals(TipoInterviniente.RepresentanteCompraventa.getValorEnum())){
				beanDetalleTransmision.setRepresentantePoseedorBean(intervinienteTrafico);
			}
		}

		resultado.setError(error);
		resultado.setMensaje(mensaje);
		beanDetalleTransmision.setHistoricoCheckImpresionPermiso(beanDetalleTransmision.getImpresionPermiso());
		resultadoMetodo.put(ConstantesPQ.RESULTBEAN, resultado);
		resultadoMetodo.put(ConstantesPQ.BEANPANTALLA, beanDetalleTransmision);
		return resultadoMetodo;
	}

	/**
	 * @author antonio.miguez
	 * @param BigDecimal
	 * @return HashMap<String,Object>
	 */
	/*
	 * Método para obtener el detalle de un trámite de transmisión de la BD, con descripciones incluídas.
	 */
	public Map<String, Object> obtenerDetalleConDescripciones(BigDecimal numEXPEDIENTE) {
		DescripcionesDireccionBean descripcionesDireccionBean = new DescripcionesDireccionBean();
		DescripcionesTraficoBean descripcionesTraficoBean = new DescripcionesTraficoBean();
		Boolean error = false;
		String mensaje = CADENA_VACIA;

		Map<String, Object> resultadoMetodo = obtenerDetalle(numEXPEDIENTE);

		ResultBean resultado = (ResultBean)resultadoMetodo.get(ConstantesPQ.RESULTBEAN);
		TramiteTraficoTransmisionBean beanDetalleTransmision = (TramiteTraficoTransmisionBean)resultadoMetodo.get(ConstantesPQ.BEANPANTALLA);
		if(resultado.getError()){
			return resultadoMetodo;
		}

		//Obtenemos las descripciones
		//El adquiriente
		if (beanDetalleTransmision.getAdquirienteBean() != null
				&& beanDetalleTransmision.getAdquirienteBean().getPersona() != null
				&& beanDetalleTransmision.getAdquirienteBean().getPersona().getDireccion() != null
				&& beanDetalleTransmision.getAdquirienteBean().getPersona().getDireccion().getIdDireccion() != null) {
			resultadoMetodo = getModeloTrafico().obtenerDescripcionesPorIdDireccion(new BigDecimal(beanDetalleTransmision.getAdquirienteBean().getPersona().getDireccion().getIdDireccion()));
			resultado = (ResultBean)resultadoMetodo.get(ConstantesPQ.RESULTBEAN);
			if (resultado.getError()) {
				log.error("Error al obtener las descripciones de la dirección del adquiriente");
			} else {
				descripcionesDireccionBean = (DescripcionesDireccionBean)resultadoMetodo.get(ConstantesPQ.BEANPANTALLA);
				beanDetalleTransmision.getAdquirienteBean().getPersona().getDireccion().getMunicipio().setNombre(descripcionesDireccionBean.getMuniNombre());
				beanDetalleTransmision.getAdquirienteBean().getPersona().getDireccion().getMunicipio().getProvincia().setNombre(descripcionesDireccionBean.getProvNombre());
				beanDetalleTransmision.getAdquirienteBean().getPersona().getDireccion().getTipoVia().setNombre(descripcionesDireccionBean.getTipViaNombre());
			}
		}

		//El conductor habitual
		if (beanDetalleTransmision.getConductorHabitualBean()!=null &&
				beanDetalleTransmision.getConductorHabitualBean().getPersona()!=null &&
				beanDetalleTransmision.getConductorHabitualBean().getPersona().getDireccion()!=null &&
				beanDetalleTransmision.getConductorHabitualBean().getPersona().getDireccion().getIdDireccion()!=null){
			resultadoMetodo = getModeloTrafico().obtenerDescripcionesPorIdDireccion(new BigDecimal(beanDetalleTransmision.getConductorHabitualBean().getPersona().getDireccion().getIdDireccion()));
			resultado = (ResultBean)resultadoMetodo.get(ConstantesPQ.RESULTBEAN);
			if (resultado.getError()) {
				log.error("Error al obtener las descripciones de la dirección del conductor habitual");
			} else {
				descripcionesDireccionBean = (DescripcionesDireccionBean)resultadoMetodo.get(ConstantesPQ.BEANPANTALLA);
				beanDetalleTransmision.getConductorHabitualBean().getPersona().getDireccion().getMunicipio().setNombre(descripcionesDireccionBean.getMuniNombre());
				beanDetalleTransmision.getConductorHabitualBean().getPersona().getDireccion().getMunicipio().getProvincia().setNombre(descripcionesDireccionBean.getProvNombre());
				beanDetalleTransmision.getConductorHabitualBean().getPersona().getDireccion().getTipoVia().setNombre(descripcionesDireccionBean.getTipViaNombre());
			}
		}

		//El representante del adquiriente
		if(beanDetalleTransmision.getRepresentanteAdquirienteBean()!=null &&
				beanDetalleTransmision.getRepresentanteAdquirienteBean().getPersona()!=null &&
				beanDetalleTransmision.getRepresentanteAdquirienteBean().getPersona().getDireccion()!=null &&
				beanDetalleTransmision.getRepresentanteAdquirienteBean().getPersona().getDireccion().getIdDireccion()!=null){
			resultadoMetodo = getModeloTrafico().obtenerDescripcionesPorIdDireccion(new BigDecimal(beanDetalleTransmision.getRepresentanteAdquirienteBean().getPersona().getDireccion().getIdDireccion()));
			resultado = (ResultBean)resultadoMetodo.get(ConstantesPQ.RESULTBEAN);
			if(resultado.getError()){
				log.error("Error al obtener las descripciones de la dirección del representante del adquiriente");
			} else {
				descripcionesDireccionBean = (DescripcionesDireccionBean)resultadoMetodo.get(ConstantesPQ.BEANPANTALLA);
				beanDetalleTransmision.getRepresentanteAdquirienteBean().getPersona().getDireccion().getMunicipio().setNombre(descripcionesDireccionBean.getMuniNombre());
				beanDetalleTransmision.getRepresentanteAdquirienteBean().getPersona().getDireccion().getMunicipio().getProvincia().setNombre(descripcionesDireccionBean.getProvNombre());
				beanDetalleTransmision.getRepresentanteAdquirienteBean().getPersona().getDireccion().getTipoVia().setNombre(descripcionesDireccionBean.getTipViaNombre());
			}
		}

		//El transmitente
		if (beanDetalleTransmision.getTransmitenteBean() != null
				&& beanDetalleTransmision.getTransmitenteBean().getPersona() != null
				&& beanDetalleTransmision.getTransmitenteBean().getPersona().getDireccion() != null
				&& beanDetalleTransmision.getTransmitenteBean().getPersona().getDireccion().getIdDireccion() != null) {
			resultadoMetodo = getModeloTrafico().obtenerDescripcionesPorIdDireccion(new BigDecimal(beanDetalleTransmision.getTransmitenteBean().getPersona().getDireccion().getIdDireccion()));
			resultado = (ResultBean)resultadoMetodo.get(ConstantesPQ.RESULTBEAN);
			if (resultado.getError()) {
				log.error("Error al obtener las descripciones de la dirección del transmitente");
			} else {
				descripcionesDireccionBean = (DescripcionesDireccionBean)resultadoMetodo.get(ConstantesPQ.BEANPANTALLA);
				beanDetalleTransmision.getTransmitenteBean().getPersona().getDireccion().getMunicipio().setNombre(descripcionesDireccionBean.getMuniNombre());
				beanDetalleTransmision.getTransmitenteBean().getPersona().getDireccion().getMunicipio().getProvincia().setNombre(descripcionesDireccionBean.getProvNombre());
				beanDetalleTransmision.getTransmitenteBean().getPersona().getDireccion().getTipoVia().setNombre(descripcionesDireccionBean.getTipViaNombre());
			}
		}

		//El representante del transmitente
		if(beanDetalleTransmision.getRepresentanteTransmitenteBean()!=null &&
				beanDetalleTransmision.getRepresentanteTransmitenteBean().getPersona()!=null &&
				beanDetalleTransmision.getRepresentanteTransmitenteBean().getPersona().getDireccion()!=null &&
				beanDetalleTransmision.getRepresentanteTransmitenteBean().getPersona().getDireccion().getIdDireccion()!=null){
			resultadoMetodo = getModeloTrafico().obtenerDescripcionesPorIdDireccion(new BigDecimal(beanDetalleTransmision.getRepresentanteTransmitenteBean().getPersona().getDireccion().getIdDireccion()));
			resultado = (ResultBean)resultadoMetodo.get(ConstantesPQ.RESULTBEAN);
			if(resultado.getError()){
				log.error("Error al obtener las descripciones de la dirección del representante del transmitente");
			} else {
				descripcionesDireccionBean = (DescripcionesDireccionBean)resultadoMetodo.get(ConstantesPQ.BEANPANTALLA);
				beanDetalleTransmision.getRepresentanteTransmitenteBean().getPersona().getDireccion().getMunicipio().setNombre(descripcionesDireccionBean.getMuniNombre());
				beanDetalleTransmision.getRepresentanteTransmitenteBean().getPersona().getDireccion().getMunicipio().getProvincia().setNombre(descripcionesDireccionBean.getProvNombre());
				beanDetalleTransmision.getRepresentanteTransmitenteBean().getPersona().getDireccion().getTipoVia().setNombre(descripcionesDireccionBean.getTipViaNombre());
			}
		}

		//El primer cotitular transmitente
		if(beanDetalleTransmision.getPrimerCotitularTransmitenteBean()!=null &&
				beanDetalleTransmision.getPrimerCotitularTransmitenteBean().getPersona()!=null &&
				beanDetalleTransmision.getPrimerCotitularTransmitenteBean().getPersona().getDireccion()!=null &&
				beanDetalleTransmision.getPrimerCotitularTransmitenteBean().getPersona().getDireccion().getIdDireccion()!=null){
			resultadoMetodo = getModeloTrafico().obtenerDescripcionesPorIdDireccion(new BigDecimal(beanDetalleTransmision.getPrimerCotitularTransmitenteBean().getPersona().getDireccion().getIdDireccion()));
			resultado = (ResultBean)resultadoMetodo.get(ConstantesPQ.RESULTBEAN);
			if(resultado.getError()){
				log.error("Error al obtener las descripciones de la dirección del primer cotitular transmitente");
			} else {
				descripcionesDireccionBean = (DescripcionesDireccionBean)resultadoMetodo.get(ConstantesPQ.BEANPANTALLA);
				beanDetalleTransmision.getPrimerCotitularTransmitenteBean().getPersona().getDireccion().getMunicipio().setNombre(descripcionesDireccionBean.getMuniNombre());
				beanDetalleTransmision.getPrimerCotitularTransmitenteBean().getPersona().getDireccion().getMunicipio().getProvincia().setNombre(descripcionesDireccionBean.getProvNombre());
				beanDetalleTransmision.getPrimerCotitularTransmitenteBean().getPersona().getDireccion().getTipoVia().setNombre(descripcionesDireccionBean.getTipViaNombre());
			}
		}

		//El segundo cotitular transmitente
		if (beanDetalleTransmision.getSegundoCotitularTransmitenteBean() != null
				&& beanDetalleTransmision.getSegundoCotitularTransmitenteBean().getPersona() != null
				&& beanDetalleTransmision.getSegundoCotitularTransmitenteBean().getPersona().getDireccion() != null
				&& beanDetalleTransmision.getSegundoCotitularTransmitenteBean().getPersona().getDireccion().getIdDireccion() != null) {
			resultadoMetodo = getModeloTrafico().obtenerDescripcionesPorIdDireccion(new BigDecimal(beanDetalleTransmision.getSegundoCotitularTransmitenteBean().getPersona().getDireccion().getIdDireccion()));
			resultado = (ResultBean)resultadoMetodo.get(ConstantesPQ.RESULTBEAN);
			if (resultado.getError()) {
				log.error("Error al obtener las descripciones de la dirección del segundo cotitular transmitente");
			} else {
				descripcionesDireccionBean = (DescripcionesDireccionBean)resultadoMetodo.get(ConstantesPQ.BEANPANTALLA);
				beanDetalleTransmision.getSegundoCotitularTransmitenteBean().getPersona().getDireccion().getMunicipio().setNombre(descripcionesDireccionBean.getMuniNombre());
				beanDetalleTransmision.getSegundoCotitularTransmitenteBean().getPersona().getDireccion().getMunicipio().getProvincia().setNombre(descripcionesDireccionBean.getProvNombre());
				beanDetalleTransmision.getSegundoCotitularTransmitenteBean().getPersona().getDireccion().getTipoVia().setNombre(descripcionesDireccionBean.getTipViaNombre());
			}
		}

		//El presentador
		if (beanDetalleTransmision.getPresentadorBean() != null
				&& beanDetalleTransmision.getPresentadorBean().getPersona() != null
				&& beanDetalleTransmision.getPresentadorBean().getPersona().getDireccion() != null
				&& beanDetalleTransmision.getPresentadorBean().getPersona().getDireccion().getIdDireccion() != null) {
			resultadoMetodo = getModeloTrafico().obtenerDescripcionesPorIdDireccion(new BigDecimal(beanDetalleTransmision.getPresentadorBean().getPersona().getDireccion().getIdDireccion()));
			resultado = (ResultBean)resultadoMetodo.get(ConstantesPQ.RESULTBEAN);
			if (resultado.getError()) {
				log.error("Error al obtener las descripciones de la dirección del presentador");
			} else {
				descripcionesDireccionBean = (DescripcionesDireccionBean)resultadoMetodo.get(ConstantesPQ.BEANPANTALLA);
				beanDetalleTransmision.getPresentadorBean().getPersona().getDireccion().getMunicipio().setNombre(descripcionesDireccionBean.getMuniNombre());
				beanDetalleTransmision.getPresentadorBean().getPersona().getDireccion().getMunicipio().getProvincia().setNombre(descripcionesDireccionBean.getProvNombre());
				beanDetalleTransmision.getPresentadorBean().getPersona().getDireccion().getTipoVia().setNombre(descripcionesDireccionBean.getTipViaNombre());
			}
		}

		//El arrendatario
		if (beanDetalleTransmision.getArrendatarioBean() != null
				&& beanDetalleTransmision.getArrendatarioBean().getPersona() != null
				&& beanDetalleTransmision.getArrendatarioBean().getPersona().getDireccion() != null
				&& beanDetalleTransmision.getArrendatarioBean().getPersona().getDireccion().getIdDireccion() != null) {
			resultadoMetodo = getModeloTrafico().obtenerDescripcionesPorIdDireccion(new BigDecimal(beanDetalleTransmision.getArrendatarioBean().getPersona().getDireccion().getIdDireccion()));
			resultado = (ResultBean)resultadoMetodo.get(ConstantesPQ.RESULTBEAN);
			if(resultado.getError()){
				log.error("Error al obtener las descripciones de la dirección del arrendatario");
			} else {
				descripcionesDireccionBean = (DescripcionesDireccionBean)resultadoMetodo.get(ConstantesPQ.BEANPANTALLA);
				beanDetalleTransmision.getArrendatarioBean().getPersona().getDireccion().getMunicipio().setNombre(descripcionesDireccionBean.getMuniNombre());
				beanDetalleTransmision.getArrendatarioBean().getPersona().getDireccion().getMunicipio().getProvincia().setNombre(descripcionesDireccionBean.getProvNombre());
				beanDetalleTransmision.getArrendatarioBean().getPersona().getDireccion().getTipoVia().setNombre(descripcionesDireccionBean.getTipViaNombre());
			}
		}

		//El representante del arrendatario
		if (beanDetalleTransmision.getRepresentanteArrendatarioBean() != null
				&& beanDetalleTransmision.getRepresentanteArrendatarioBean().getPersona() != null
				&& beanDetalleTransmision.getRepresentanteArrendatarioBean().getPersona().getDireccion() != null
				&& beanDetalleTransmision.getRepresentanteArrendatarioBean().getPersona().getDireccion().getIdDireccion() != null) {
			resultadoMetodo = getModeloTrafico().obtenerDescripcionesPorIdDireccion(new BigDecimal(beanDetalleTransmision.getRepresentanteArrendatarioBean().getPersona().getDireccion().getIdDireccion()));
			resultado = (ResultBean)resultadoMetodo.get(ConstantesPQ.RESULTBEAN);
			if(resultado.getError()){
				log.error("Error al obtener las descripciones de la dirección del representante del arrendatario");
			} else {
				descripcionesDireccionBean = (DescripcionesDireccionBean)resultadoMetodo.get(ConstantesPQ.BEANPANTALLA);
				beanDetalleTransmision.getRepresentanteArrendatarioBean().getPersona().getDireccion().getMunicipio().setNombre(descripcionesDireccionBean.getMuniNombre());
				beanDetalleTransmision.getRepresentanteArrendatarioBean().getPersona().getDireccion().getMunicipio().getProvincia().setNombre(descripcionesDireccionBean.getProvNombre());
				beanDetalleTransmision.getRepresentanteArrendatarioBean().getPersona().getDireccion().getTipoVia().setNombre(descripcionesDireccionBean.getTipViaNombre());
			}
		}

		//El poseedor
		if (beanDetalleTransmision.getPoseedorBean() != null
				&& beanDetalleTransmision.getPoseedorBean().getPersona() != null
				&& beanDetalleTransmision.getPoseedorBean().getPersona().getDireccion() != null
				&& beanDetalleTransmision.getPoseedorBean().getPersona().getDireccion().getIdDireccion() != null) {
			resultadoMetodo = getModeloTrafico().obtenerDescripcionesPorIdDireccion(new BigDecimal(beanDetalleTransmision.getPoseedorBean().getPersona().getDireccion().getIdDireccion()));
			resultado = (ResultBean)resultadoMetodo.get(ConstantesPQ.RESULTBEAN);
			if(resultado.getError()){
				log.error("Error al obtener las descripciones de la dirección del poseedor");
			} else {
				descripcionesDireccionBean = (DescripcionesDireccionBean)resultadoMetodo.get(ConstantesPQ.BEANPANTALLA);
				beanDetalleTransmision.getPoseedorBean().getPersona().getDireccion().getMunicipio().setNombre(descripcionesDireccionBean.getMuniNombre());
				beanDetalleTransmision.getPoseedorBean().getPersona().getDireccion().getMunicipio().getProvincia().setNombre(descripcionesDireccionBean.getProvNombre());
				beanDetalleTransmision.getPoseedorBean().getPersona().getDireccion().getTipoVia().setNombre(descripcionesDireccionBean.getTipViaNombre());
			}
		}

		//El representante del poseedor
		if (beanDetalleTransmision.getRepresentantePoseedorBean() != null
				&& beanDetalleTransmision.getRepresentantePoseedorBean().getPersona() != null
				&& beanDetalleTransmision.getRepresentantePoseedorBean().getPersona().getDireccion() != null
				&& beanDetalleTransmision.getRepresentantePoseedorBean().getPersona().getDireccion().getIdDireccion() != null) {
			resultadoMetodo = getModeloTrafico().obtenerDescripcionesPorIdDireccion(new BigDecimal(beanDetalleTransmision.getRepresentantePoseedorBean().getPersona().getDireccion().getIdDireccion()));
			resultado = (ResultBean)resultadoMetodo.get(ConstantesPQ.RESULTBEAN);
			if (resultado.getError()) {
				log.error("Error al obtener las descripciones de la dirección del representante del poseedor");
			} else {
				descripcionesDireccionBean = (DescripcionesDireccionBean)resultadoMetodo.get(ConstantesPQ.BEANPANTALLA);
				beanDetalleTransmision.getRepresentantePoseedorBean().getPersona().getDireccion().getMunicipio().setNombre(descripcionesDireccionBean.getMuniNombre());
				beanDetalleTransmision.getRepresentantePoseedorBean().getPersona().getDireccion().getMunicipio().getProvincia().setNombre(descripcionesDireccionBean.getProvNombre());
				beanDetalleTransmision.getRepresentantePoseedorBean().getPersona().getDireccion().getTipoVia().setNombre(descripcionesDireccionBean.getTipViaNombre());
			}
		}

		//El vehículo
		if (beanDetalleTransmision.getTramiteTraficoBean() != null
				&& beanDetalleTransmision.getTramiteTraficoBean().getVehiculo()!=null
				&& beanDetalleTransmision.getTramiteTraficoBean().getVehiculo().getDireccionBean() != null
				&& beanDetalleTransmision.getTramiteTraficoBean().getVehiculo().getDireccionBean().getIdDireccion() != null
				&& (TRUE.equals(beanDetalleTransmision.getConsentimientoCambio()))) {
			// Se obtiene la dirección del adquiriente o la del vehículo dependiendo si se ha marcado el check
			// de consentimiento
			resultadoMetodo = getModeloTrafico().obtenerDescripcionesPorIdDireccion(new BigDecimal(
					beanDetalleTransmision.getTramiteTraficoBean().getVehiculo().getDireccionBean().getIdDireccion()));

			resultado = (ResultBean)resultadoMetodo.get(ConstantesPQ.RESULTBEAN);
			if(resultado.getError()){
				log.error("Error al obtener las descripciones de la dirección del vehículo");
			} else {
				descripcionesDireccionBean = (DescripcionesDireccionBean)resultadoMetodo.get(ConstantesPQ.BEANPANTALLA);
				beanDetalleTransmision.getTramiteTraficoBean().getVehiculo().getDireccionBean().getMunicipio().setNombre(descripcionesDireccionBean.getMuniNombre());
				beanDetalleTransmision.getTramiteTraficoBean().getVehiculo().getDireccionBean().getMunicipio().getProvincia().setNombre(descripcionesDireccionBean.getProvNombre());
				beanDetalleTransmision.getTramiteTraficoBean().getVehiculo().getDireccionBean().getTipoVia().setNombre(descripcionesDireccionBean.getTipViaNombre());
			}
		}

		//Obtenemos los datos propios del trámite
		resultadoMetodo = getModeloTrafico().obtenerDescripcionesPorNumExpediente(numEXPEDIENTE);
		resultado = (ResultBean)resultadoMetodo.get(ConstantesPQ.RESULTBEAN);
		if (resultado.getError()) {
			log.error("Error al obtener las descripciones de la dirección del adquiriente");
		} else {
			descripcionesTraficoBean = (DescripcionesTraficoBean)resultadoMetodo.get(ConstantesPQ.BEANPANTALLA);

			if (beanDetalleTransmision.getTramiteTraficoBean() != null) {
				//Gestoría CIF y RAZÓN SOCIAL
				beanDetalleTransmision.getTramiteTraficoBean().setCif(descripcionesTraficoBean.getCif());
				beanDetalleTransmision.getTramiteTraficoBean().setRazonSocial(descripcionesTraficoBean.getRazonSocial());

				beanDetalleTransmision.getTramiteTraficoBean().getTasa().setTipoTasa(descripcionesTraficoBean.getTipoTasa());
				//Jefatura
				if(null != beanDetalleTransmision.getTramiteTraficoBean().getJefaturaTrafico()){
					beanDetalleTransmision.getTramiteTraficoBean().getJefaturaTrafico()
						.setDescripcion(descripcionesTraficoBean.getDescripcionJefaturaSucursal());
					beanDetalleTransmision.getTramiteTraficoBean().getJefaturaTrafico().getProvincia().
						setNombre(descripcionesTraficoBean.getDescripcionJefaturaProvincial());
				}
				beanDetalleTransmision.getTramiteTraficoBean().getJefaturaTrafico().setDescripcion(descripcionesTraficoBean.getDescripcionJefaturaSucursal());
				beanDetalleTransmision.getTramiteTraficoBean().getJefaturaTrafico().setJefatura(descripcionesTraficoBean.getJefaturaProvincia());
				beanDetalleTransmision.getTramiteTraficoBean().getVehiculo().getMarcaBean().setMarca(descripcionesTraficoBean.getMarca());
				beanDetalleTransmision.getTramiteTraficoBean().getVehiculo().getMarcaBean().setMarcaSinEditar(descripcionesTraficoBean.getMarcaSinEditar());
				beanDetalleTransmision.getTramiteTraficoBean().getVehiculo().getTipoVehiculoBean().setDescripcion(descripcionesTraficoBean.getDescripcionTipoVehiculo());
				beanDetalleTransmision.getTramiteTraficoBean().getVehiculo().getServicioTraficoBean().setDescripcion(descripcionesTraficoBean.getDescripcionServicio());
				beanDetalleTransmision.getTramiteTraficoBean().getVehiculo().getCriterioConstruccionBean().setDescripcion(descripcionesTraficoBean.getDescripcionCriterioConstruccion());
				beanDetalleTransmision.getTramiteTraficoBean().getVehiculo().getCriterioUtilizacionBean().setDescripcion(descripcionesTraficoBean.getDescripcionCriterioUtilizacion());
				beanDetalleTransmision.getTramiteTraficoBean().getVehiculo().getHomologacionBean().setDescripcion(descripcionesTraficoBean.getDescripcionDirectivaCEE());
				beanDetalleTransmision.getTramiteTraficoBean().getVehiculo().getMotivoItv().setDescripcion(descripcionesTraficoBean.getDescripcionMotivoITV());
				beanDetalleTransmision.getTramiteTraficoBean().getVehiculo().setEstacionItv(descripcionesTraficoBean.getEstacionITV());
				//Se agrega el tipoTramite para validaciones futuras
				beanDetalleTransmision.getTramiteTraficoBean().setTipoTramite(TipoTramiteTrafico.TransmisionElectronica.getValorEnum());
			}
		}

		resultado.setError(error);
		resultado.setMensaje(mensaje);

		resultadoMetodo.put(ConstantesPQ.RESULTBEAN, resultado);
		resultadoMetodo.put(ConstantesPQ.BEANPANTALLA, beanDetalleTransmision);

		return resultadoMetodo;
	}

	/**
	 * @author antonio.miguez
	 * @param TramiteTraficoTransmisionBean
	 * @return List<String>
	 */
	/*
	 * Método para validar los datos necesarios para generar el XML para G@ta
	 */
	public List<String> validarCamposXML(TramiteTraficoTransmisionBean tramite){
		//Se validan los campos
		List<String> listaMensajes = new ArrayList<>();
		Date fechaActual = new Date();
		IntervinienteTrafico adquirienteBean;

		boolean tieneValorDeclarado = true;
		Direccion direccion = null;

		String tipoPersonaAdquiriente = CADENA_VACIA;

		if (null == tramite.getTipoTransferencia() ||
				!tramite.getTipoTransferencia().getValorEnum().equals(TipoTransferencia.tipo5.getValorEnum())) {//Antes era tipo 3
			adquirienteBean = tramite.getAdquirienteBean();
		} else {
			// Se consideran los datos del poseedor como adquiriente en una Entrega Compraventa
			adquirienteBean = tramite.getPoseedorBean();
		}

		IntervinienteTrafico transmitenteBean = tramite.getTransmitenteBean();
		//Obtenemos el presentador de la base de datos, para que la validación se corresponda a la generación del XML
		ResultBean resultadoDatosGestoria = modeloContratoNuevo.obtenerDatosGestoria(tramite.getTramiteTraficoBean().getIdContrato());

		IntervinienteTrafico presentadorBean = new IntervinienteTrafico(true);

		if (!resultadoDatosGestoria.getError()) {
			presentadorBean = (IntervinienteTrafico) resultadoDatosGestoria.getAttachment(ConstantesPQ.BEANPANTALLA);
		} else {
			presentadorBean = tramite.getPresentadorBean();
		}

		VehiculoBean vehiculo = tramite.getTramiteTraficoBean().getVehiculo();

		if(adquirienteBean==null || transmitenteBean==null || presentadorBean==null || vehiculo==null){
			listaMensajes.add(FALTAN_DATOS_OBLIGATORIOS_POR_RELLENAR);
			return listaMensajes;
		}

		// Datos del Adquiriente o Poseedor dependiente del tipo de transferencia.
		// Si transfencia es 5 (Entrega Compraventa) --> Valida Datos del Poseedor.
		// Resto de transferencias --> Valida Datos del Adquiriente.

		if(null==tramite.getTipoTransferencia() ||
				!tramite.getTipoTransferencia().getValorEnum().equals(TipoTransferencia.tipo5.getValorEnum())){
			String dniAdquiriente = adquirienteBean.getPersona().getNif();

			if(dniAdquiriente==null || CADENA_VACIA.equals(dniAdquiriente)){
				listaMensajes.add(EL_DNI_DEL_ADQUIRIENTE_ES_OBLIGATORIO);
			} else if(validarDNI(dniAdquiriente) || validarCIF(dniAdquiriente)){
				tipoPersonaAdquiriente = (validarCIF(dniAdquiriente)) ? CIF : DNI;
			} else{
				listaMensajes.add(EL_DNI_DEL_ADQUIRIENTE_DEBE_SER_VALIDO);
			}

			if(adquirienteBean.getPersona().getApellido1RazonSocial()==null || CADENA_VACIA.equals(adquirienteBean.getPersona().getApellido1RazonSocial())){
				listaMensajes.add(EL_PRIMER_APELLIDO_O_RAZON_SOCIAL_DEL_ADQUIRIENTE_ES_OBLIGATORIO);
			}
			if(tipoPersonaAdquiriente.equals(DNI)){
				if(adquirienteBean.getPersona().getNombre()==null || CADENA_VACIA.equals(adquirienteBean.getPersona().getNombre())){
					listaMensajes.add(EL_NOMBRE_DEL_ADQUIRIENTE_ES_OBLIGATORIO);
				} else if(adquirienteBean.getPersona().getNombre().length() > 25){
					listaMensajes.add(EL_NOMBRE_DEL_ADQUIRIENTE_NO_PUEDE_TENER_MAS_DE_25_CARACTERES);
				} else if(adquirienteBean.getPersona().getApellido1RazonSocial().length() > 25){
					listaMensajes.add(EL_PRIMER_APELLIDO_DEL_ADQUIRIENTE_NO_PUEDE_TENER_MAS_DE_25_CARACTERES);
				}
			} else if(adquirienteBean.getPersona().getApellido1RazonSocial()!=null && adquirienteBean.getPersona().getApellido1RazonSocial().length() > 75){
				listaMensajes.add(LA_RAZON_SOCIAL_DEL_ADQUIRIENTE_NO_PUEDE_TENER_MAS_DE_75_CARACTERES);
			}
		}

		if (TRUE.equals(tramite.getConsentimientoCambio())) {
			if (vehiculo.getDireccionBean()!=null && vehiculo.getDireccionBean().getMunicipio()!=null && vehiculo.getDireccionBean().getMunicipio().getProvincia() != null) {
				direccion = vehiculo.getDireccionBean();
				if (StringUtils.isBlank(direccion.getMunicipio().getProvincia().getIdProvincia()) ||
						"-1".equals(direccion.getMunicipio().getProvincia().getIdProvincia())) {
					listaMensajes.add(LA_PROVINCIA_DEL_VEHICULO_ES_OBLIGATORIA);
				}

				if (CADENA_VACIA.equals(direccion.getMunicipio().getIdMunicipio())
						|| "-1".equals(direccion.getMunicipio().getIdMunicipio())) {
					listaMensajes.add(EL_MUNICIPIO_DEL_VEHICULO_ES_OBLIGATORIO);
				}

				if(CADENA_VACIA.equals(direccion.getTipoVia().getIdTipoVia()) ||
						"-1".equals(direccion.getTipoVia().getIdTipoVia())){
					listaMensajes.add(EL_TIPO_DE_VIA_DEL_VEHICULO_ES_OBLIGATORIO);
				}

				if (direccion.getNombreVia() != null) {
					if(CADENA_VACIA.equals(direccion.getNombreVia())){
						listaMensajes.add(EL_NOMBRE_DE_VIA_DEL_VEHICULO_ES_OBLIGATORIO);
					} else if (direccion.getNombreVia().length() > 50) {
						listaMensajes.add(EL_NOMBRE_DE_VIA_DEL_VEHICULO_NO_PUEDE_TENER_MAS_DE_50_CARACTERES);
					}
				} else {
					listaMensajes.add(EL_NOMBRE_DE_VIA_DEL_VEHICULO_ES_OBLIGATORIO);
				}

				if(CADENA_VACIA.equals(direccion.getNumero())){
					listaMensajes.add(EL_NUMERO_DE_VIA_DEL_VEHICULO_ES_OBLIGATORIO);
				}

				if (direccion.getCodPostal() == null || CADENA_VACIA.equals(direccion.getCodPostal())) {
					listaMensajes.add(EL_CODIGO_POSTAL_DEL_VEHICULO_ES_OBLIGATORIO);
				} else if (direccion.getCodPostal().length() < 5) {
					listaMensajes.add(EL_CODIGO_POSTAL_VEHICULO_DEBE_TENER_5_CARACTERES);
				}

				if (direccion.getPortal() != null && !CADENA_VACIA.equals(direccion.getPortal())
						&& direccion.getPortal().length() > 1) {
					listaMensajes.add(EL_PORTAL_DE_LA_DIRECCION_DEL_VEHICULO_NO_PUEDE_TENER_MAS_DE_1_CARACTER);
				}

				if (direccion.getEscalera() != null && !CADENA_VACIA.equals(direccion.getEscalera())
						&& direccion.getEscalera().length() > 2) {
					listaMensajes.add(LA_ESCALERA_DE_LA_DIRECCION_DEL_VEHICULO_NO_PUEDE_TENER_MAS_DE_2_CARACTERES);
				}

				if (direccion.getPlanta() != null && !CADENA_VACIA.equals(direccion.getPlanta())
						&& direccion.getPlanta().length() > 3) {
					listaMensajes.add(EL_PISO_DE_LA_DIRECCION_DEL_VEHICULO_NO_PUEDE_TENER_MAS_DE_3_CARACTERES);
				}

				if (direccion.getPuerta() != null && !CADENA_VACIA.equals(direccion.getPuerta())
						&& direccion.getPuerta().length() > 2) {
					listaMensajes.add(LA_PUERTA_DE_LA_DIRECCION_DEL_VEHICULO_NO_PUEDE_TENER_MAS_DE_2_CARACTERES);
				}
			} else {
				listaMensajes.add(LA_DIRECCION_DEL_VEHICULO_ES_OBLIGATORIA);
			}
		} else if(adquirienteBean.getPersona().getDireccion()!=null && adquirienteBean.getPersona().getDireccion().getMunicipio()!=null && adquirienteBean.getPersona().getDireccion().getMunicipio().getProvincia() != null && adquirienteBean.getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia()!=null
					&& adquirienteBean.getPersona().getDireccion().getMunicipio().getIdMunicipio()!=null && adquirienteBean.getPersona().getDireccion().getNombreVia()!=null){

			direccion = adquirienteBean.getPersona().getDireccion();

			if (CADENA_VACIA.equals(direccion.getMunicipio().getProvincia().getIdProvincia())
					|| "-1".equals(direccion.getMunicipio().getProvincia().getIdProvincia())){
				listaMensajes.add(LA_PROVINCIA_DEL_ADQUIRIENTE_ES_OBLIGATORIA);
			}

			if(CADENA_VACIA.equals(direccion.getMunicipio().getIdMunicipio()) ||
					"-1".equals(direccion.getMunicipio().getIdMunicipio())){
				listaMensajes.add(EL_MUNICIPIO_DEL_ADQUIRIENTE_ES_OBLIGATORIO);
			}

			if(CADENA_VACIA.equals(direccion.getTipoVia().getIdTipoVia()) ||
					"-1".equals(direccion.getTipoVia().getIdTipoVia())){
				listaMensajes.add(EL_TIPO_DE_VIA_DEL_ADQUIRIENTE_ES_OBLIGATORIO);
			}

			if(CADENA_VACIA.equals(direccion.getNombreVia())){
				listaMensajes.add(EL_NOMBRE_DE_VIA_DEL_ADQUIRIENTE_ES_OBLIGATORIO);
			} else if(direccion.getNombreVia().length() > 50){
				listaMensajes.add(EL_NOMBRE_DE_VIA_DEL_ADQUIRIENTE_NO_PUEDE_TENER_MAS_DE_50_CARACTERES);
			}

			if(CADENA_VACIA.equals(direccion.getNumero())){
				listaMensajes.add(EL_NUMERO_DE_VIA_DEL_ADQUIRIENTE_ES_OBLIGATORIO);
			}

			if(CADENA_VACIA.equals(direccion.getCodPostal())){
				listaMensajes.add(EL_CODIGO_POSTAL_DEL_ADQUIRIENTE_ES_OBLIGATORIO);
			} else if (direccion.getCodPostal().length() < 5) {
				listaMensajes.add(EL_CODIGO_POSTAL_ADQUIRIENTE_DEBE_TENER_5_CARACTERES);
			}

			if (direccion.getPortal() != null && !CADENA_VACIA.equals(direccion.getPortal())
					&& direccion.getPortal().length() > 1) {
				listaMensajes.add(EL_PORTAL_DE_LA_DIRECCION_DEL_ADQUIRIENTE_NO_PUEDE_TENER_MAS_DE_1_CARACTER);
			}

			if (direccion.getEscalera() != null && !CADENA_VACIA.equals(direccion.getEscalera())
					&& direccion.getEscalera().length() > 3) {
				listaMensajes.add(LA_ESCALERA_DE_LA_DIRECCION_DEL_ADQUIRIENTE_NO_PUEDE_TENER_MAS_DE_3_CARACTERES);
			}

			if (direccion.getPlanta() != null && !CADENA_VACIA.equals(direccion.getPlanta())
					&& direccion.getPlanta().length() > 3) {
				listaMensajes.add(EL_PISO_DE_LA_DIRECCION_DEL_ADQUIRIENTE_NO_PUEDE_TENER_MAS_DE_3_CARACTERES);
			}

			if (direccion.getPuerta() != null && !CADENA_VACIA.equals(direccion.getPuerta())
					&& direccion.getPuerta().length() > 3) {
				listaMensajes.add(LA_PUERTA_DE_LA_DIRECCION_DEL_ADQUIRIENTE_NO_PUEDE_TENER_MAS_DE_3_CARACTERES);
			}
		} else {
			listaMensajes.add(LA_DIRECCION_DEL_ADQUIRIENTE_ES_OBLIGATORIA);
		}

		String tipoPersonaTransmitente = CADENA_VACIA;
		String dniTransmitente = transmitenteBean.getPersona().getNif();

		if(dniTransmitente==null || CADENA_VACIA.equals(dniTransmitente)){
			listaMensajes.add(EL_NIF_DEL_TRANSMITENTE_ES_OBLIGATORIO);
		}else if(validarDNI(dniTransmitente) || validarCIF(dniTransmitente)){
			tipoPersonaTransmitente = validarCIF(dniTransmitente) ? CIF : DNI;
		}else{
			listaMensajes.add(EL_DNI_DEL_TRANSMITENTE_DEBE_SER_VALIDO);
		}

		if (transmitenteBean.getPersona().getApellido1RazonSocial() == null
				|| CADENA_VACIA.equals(transmitenteBean.getPersona().getApellido1RazonSocial())){
			listaMensajes.add(EL_PRIMER_APELLIDO_O_RAZON_SOCIAL_DEL_TRANSMITENTE_ES_OBLIGATORIO);
		}

		if (tipoPersonaTransmitente.equals(DNI)) {
			String nombreTransmitente = transmitenteBean.getPersona().getNombre();
			if(nombreTransmitente==null || CADENA_VACIA.equals(nombreTransmitente)){
				listaMensajes.add(EL_NOMBRE_DEL_TRANSMITENTE_ES_OBLIGATORIO);
			} else if (nombreTransmitente.length() > 25) {
				listaMensajes.add(EL_NOMBRE_DEL_TRANSMITENTE_NO_PUEDE_TENER_MAS_DE_25_CARACTERES);
			} else if (transmitenteBean.getPersona().getApellido1RazonSocial().length() > 25) {
				listaMensajes.add(EL_PRIMER_APELLIDO_DEL_TRANSMITENTE_NO_PUEDE_TENER_MAS_DE_25_CARACTERES);
			}
		} else if (transmitenteBean.getPersona().getApellido1RazonSocial() != null && transmitenteBean.getPersona().getApellido1RazonSocial().length() > 75){
			listaMensajes.add(LA_RAZON_SOCIAL_DEL_TRANSMITENTE_NO_PUEDE_TENER_MAS_DE_75_CARACTERES);
		}

		if (transmitenteBean.getPersona().getDireccion() != null && transmitenteBean.getPersona().getDireccion().getMunicipio()!=null
				&& transmitenteBean.getPersona().getDireccion().getMunicipio().getProvincia() != null
				&& transmitenteBean.getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia()!=null){
			direccion = transmitenteBean.getPersona().getDireccion();
			if (CADENA_VACIA.equals(direccion.getMunicipio().getProvincia().getIdProvincia()) ||
					"-1".equals(direccion.getMunicipio().getProvincia().getIdProvincia())){
				listaMensajes.add(LA_PROVINCIA_DEL_TRANSMITENTE_ES_OBLIGATORIA);
			}

			if (CADENA_VACIA.equals(direccion.getMunicipio().getIdMunicipio())
					|| "-1".equals(direccion.getMunicipio().getIdMunicipio())) {
				listaMensajes.add(EL_MUNICIPIO_DEL_TRANSMITENTE_ES_OBLIGATORIO);
			}

			if (CADENA_VACIA.equals(direccion.getTipoVia().getIdTipoVia()) ||
					"-1".equals(direccion.getTipoVia().getIdTipoVia())){
				listaMensajes.add(EL_TIPO_DE_VIA_DEL_TRANSMITENTE_ES_OBLIGATORIO);
			}

			if (CADENA_VACIA.equals(direccion.getNombreVia())) {
				listaMensajes.add(NOMBRE_DE_VIA_DEL_TRANSMITENTE_OBLIGATORIO);
			} else if (direccion.getNombreVia().length() > 50) {
				listaMensajes.add(NOMBRE_DE_VIA_DEL_TRANSMITENTE_NO_PUEDE_TENER_MAS_DE_50_CARACTERES);
			}

			if (direccion.getNumero() != null && CADENA_VACIA.equals(direccion.getNumero())){
				listaMensajes.add(EL_NUMERO_DE_VIA_DEL_TRANSMITENTE_ES_OBLIGATORIO);
			}

			if (direccion.getCodPostal() == null || CADENA_VACIA.equals(direccion.getCodPostal())) {
				listaMensajes.add(EL_CODIGO_POSTAL_DEL_TRANSMITENTE_ES_OBLIGATORIO);
			} else if (direccion.getCodPostal().length() < 5) {
				listaMensajes.add(EL_CODIGO_POSTAL_TRANSMITENTE_DEBE_TENER_5_CARACTERES);
			}

			if (direccion.getPortal() != null && !CADENA_VACIA.equals(direccion.getPortal())
					&& direccion.getPortal().length() > 1) {
				listaMensajes.add(EL_PORTAL_DE_LA_DIRECCION_DEL_TRANSMITENTE_NO_PUEDE_TENER_MAS_DE_1_CARACTER);
			}

			if (direccion.getEscalera() != null && !CADENA_VACIA.equals(direccion.getEscalera())
					&& direccion.getEscalera().length() > 2) {
				listaMensajes.add(LA_ESCALERA_DE_LA_DIRECCION_DEL_TRANSMITENTE_NO_PUEDE_TENER_MAS_DE_2_CARACTERES);
			}

			if (direccion.getPlanta() != null && !CADENA_VACIA.equals(direccion.getPlanta())
					&& direccion.getPlanta().length() > 3) {
				listaMensajes.add(EL_PISO_DE_LA_DIRECCION_DEL_TRANSMITENTE_NO_PUEDE_TENER_MAS_DE_3_CARACTERES);
			}

			if (direccion.getPuerta() != null && !CADENA_VACIA.equals(direccion.getPuerta())
					&& direccion.getPuerta().length() > 2) {
				listaMensajes.add(LA_PUERTA_DE_LA_DIRECCION_DEL_TRANSMITENTE_NO_PUEDE_TENER_MAS_DE_2_CARACTERES);
			}
		} else {
			listaMensajes.add(LA_DIRECCION_DEL_TRANSMITENTE_ES_OBLIGATORIA);
		}

		String tipoPersonaPresentador = CADENA_VACIA;
		String dniPresentador = presentadorBean.getPersona().getNif();

		if (dniPresentador==null || CADENA_VACIA.equals(dniPresentador)) {
			listaMensajes.add(EL_NIF_DEL_PRESENTADOR_ES_OBLIGATORIO);
		} else if (validarDNI(dniPresentador) || validarCIF(dniPresentador)) {
			tipoPersonaPresentador = validarCIF(dniPresentador) ? CIF : DNI;
		} else {
			listaMensajes.add(EL_DNI_DEL_PRESENTADOR_DEBE_SER_VALIDO);
		}

		if(presentadorBean.getPersona().getApellido1RazonSocial() == null ||
				CADENA_VACIA.equals(presentadorBean.getPersona().getApellido1RazonSocial())){
			listaMensajes.add(EL_PRIMER_APELLIDO_O_RAZON_SOCIAL_DEL_TRANSMITENTE_ES_OBLIGATORIO);
		}

		if (tipoPersonaPresentador.equals(DNI)) {
			if (presentadorBean.getPersona().getNombre() == null ||
					CADENA_VACIA.equals(presentadorBean.getPersona().getNombre())) {
				listaMensajes.add(EL_NOMBRE_DEL_PRESENTADOR_ES_OBLIGATORIO);
			} else if (presentadorBean.getPersona().getNombre().length() > 25) {
				listaMensajes.add(EL_NOMBRE_DEL_PRESENTADOR_NO_PUEDE_TENER_MAS_DE_25_CARACTERES);
			} else if (presentadorBean.getPersona().getApellido1RazonSocial().length() > 25) {
				listaMensajes.add(EL_PRIMER_APELLIDO_DEL_PRESENTADOR_NO_PUEDE_TENER_MAS_DE_25_CARACTERES);
			}
		} else if (presentadorBean.getPersona().getApellido1RazonSocial()!=null && presentadorBean.getPersona().getApellido1RazonSocial().length() > 75) {
			listaMensajes.add(LA_RAZON_SOCIAL_DEL_PRESENTADOR_NO_PUEDE_TENER_MAS_DE_75_CARACTERES);
		}

		if (presentadorBean.getPersona().getDireccion() != null && presentadorBean.getPersona().getDireccion().getMunicipio() != null
				&& presentadorBean.getPersona().getDireccion().getMunicipio().getProvincia() != null) {
			direccion = presentadorBean.getPersona().getDireccion();
			try {
				if(CADENA_VACIA.equals(direccion.getMunicipio().getProvincia().getIdProvincia()) ||
						"-1".equals(direccion.getMunicipio().getProvincia().getIdProvincia())){
					listaMensajes.add(LA_PROVINCIA_DEL_PRESENTADOR_ES_OBLIGATORIA);
				}
			} catch (Exception e) {
				listaMensajes.add(LA_PROVINCIA_DEL_PRESENTADOR_ES_OBLIGATORIA);
			}

			try {
				if(CADENA_VACIA.equals(direccion.getMunicipio().getIdMunicipio()) ||
						"-1".equals(direccion.getMunicipio().getIdMunicipio())){
					listaMensajes.add(EL_MUNICIPIO_DEL_PRESENTADOR_ES_OBLIGATORIO);
				}
			}catch (Exception e){
				listaMensajes.add(EL_MUNICIPIO_DEL_PRESENTADOR_ES_OBLIGATORIO);
			}

			try {
				if(CADENA_VACIA.equals(direccion.getTipoVia().getIdTipoVia()) ||
						"-1".equals(direccion.getTipoVia().getIdTipoVia())){
					listaMensajes.add(EL_TIPO_DE_VIA_DEL_PRESENTADOR_ES_OBLIGATORIO);
				}
			}catch (Exception e){
				listaMensajes.add(EL_TIPO_DE_VIA_DEL_PRESENTADOR_ES_OBLIGATORIO);
			}

			try {
				if(CADENA_VACIA.equals(direccion.getNombreVia())){
					listaMensajes.add(EL_NOMBRE_DE_VIA_DEL_PRESENTADOR_ES_OBLIGATORIO);
				} else if(direccion.getNombreVia().length()>50){
					listaMensajes.add(EL_NOMBRE_DE_VIA_DEL_PRESENTADOR_NO_PUEDE_TENER_MAS_DE_50_CARACTERES);
				}
			}catch (Exception e){
				listaMensajes.add(EL_NOMBRE_DE_VIA_DEL_PRESENTADOR_ES_OBLIGATORIA);
			}

			try {
				if(CADENA_VACIA.equals(direccion.getNumero())){
					listaMensajes.add(EL_NUMERO_DE_VIA_DEL_PRESENTADOR_ES_OBLIGATORIO);
				}
			}catch (Exception e){
				listaMensajes.add(EL_NUMERO_DE_VIA_DEL_PRESENTADOR_ES_OBLIGATORIO);
			}

			try{
				if(CADENA_VACIA.equals(direccion.getCodPostal())){
					listaMensajes.add(EL_CODIGO_POSTAL_DEL_PRESENTADOR_ES_OBLIGATORIO);
				} else if (direccion.getCodPostal().length() < 5) {
					listaMensajes.add(EL_CODIGO_POSTAL_PRESENTADOR_DEBE_TENER_5_CARACTERES);
				}
			}catch (Exception e){
				listaMensajes.add(EL_CODIGO_POSTAL_DEL_PRESENTADOR_ES_OBLIGATORIO);
			}

			try {
				if(presentadorBean.getPersona().getTelefonos()==null || CADENA_VACIA.equals(presentadorBean.getPersona().getTelefonos())){
					listaMensajes.add(EL_TELEFONO_DEL_PRESENTADOR_ES_OBLIGATORIO);
				}
			}catch (Exception e){
				listaMensajes.add(EL_TELEFONO_DEL_PRESENTADOR_ES_OBLIGATORIO);
			}

			try {
				if (presentadorBean.getPersona().getTelefonos().length() != 9) {
					listaMensajes.add(EL_TELEFONO_DEL_PRESENTADOR_DEBE_TENER_9_DIGITOS);
				}
			} catch (Exception e) {
				listaMensajes.add(EL_TELEFONO_DEL_PRESENTADOR_DEBE_TENER_9_DIGITOS);
			}

			if (direccion.getPortal()!=null && !CADENA_VACIA.equals(direccion.getPortal())
					&& direccion.getPortal().length() > 1){
				listaMensajes.add(EL_PORTAL_DE_LA_DIRECCION_DEL_PRESENTADOR_NO_PUEDE_TENER_MAS_DE_1_CARACTER);
			}

			if(direccion.getEscalera()!=null && !CADENA_VACIA.equals(direccion.getEscalera())
					&& direccion.getEscalera().length() > 2){
				listaMensajes.add(LA_ESCALERA_DE_LA_DIRECCION_DEL_PRESENTADOR_NO_PUEDE_TENER_MAS_DE_2_CARACTERES);
			}

			if(direccion.getPlanta()!=null && !CADENA_VACIA.equals(direccion.getPlanta())
					&& direccion.getPlanta().length() > 3){
				listaMensajes.add(EL_PISO_DE_LA_DIRECCION_DEL_PRESENTADOR_NO_PUEDE_TENER_MAS_DE_2_CARACTERES);
			}

			if(direccion.getPuerta()!=null && !CADENA_VACIA.equals(direccion.getPuerta())
					&& direccion.getPuerta().length() > 2){
				listaMensajes.add(LA_PUERTA_DE_LA_DIRECCION_DEL_PRESENTADOR_NO_PUEDE_TENER_MAS_DE_2_CARACTERES);
			}
		} else {
			listaMensajes.add(LA_DIRECCION_DEL_PRESENTADOR_ES_OBLIGATORIA);
		}

		/*
		 * VEHICULO
		 */

		if (tramite.getOficinaLiquidadora620() != null
				&& (tramite.getOficinaLiquidadora620().getOficinaLiquidadora()==null ||
					CADENA_VACIA.equals(tramite.getOficinaLiquidadora620().getOficinaLiquidadora()) ||
					"-1".equals(tramite.getOficinaLiquidadora620().getOficinaLiquidadora()))){
			listaMensajes.add(LA_OFICINA_LIQUIDADORA_ES_OBLIGATORIA);
		}

		boolean fechaDevengo = false;

		if (tramite.getFechaDevengoItp620()==null ||
				tramite.getFechaDevengoItp620().getDia()==null ||
				tramite.getFechaDevengoItp620().getMes()==null ||
				tramite.getFechaDevengoItp620().getAnio()==null ||
				CADENA_VACIA.equals(tramite.getFechaDevengoItp620().getDia()) ||
				CADENA_VACIA.equals(tramite.getFechaDevengoItp620().getMes()) ||
				CADENA_VACIA.equals(tramite.getFechaDevengoItp620().getAnio())){
			listaMensajes.add(LA_FECHA_DE_DEVENGO_ES_OBLIGATORIA);
		}else{
			fechaDevengo = true;
		}
		boolean fechaPrimMatriculacion = true;
		if (vehiculo.getFechaPrimMatri()==null ||
				vehiculo.getFechaPrimMatri().getDia()==null ||
				vehiculo.getFechaPrimMatri().getMes()==null ||
				vehiculo.getFechaPrimMatri().getAnio()==null ||
				CADENA_VACIA.equals(vehiculo.getFechaPrimMatri().getDia()) ||
				CADENA_VACIA.equals(vehiculo.getFechaPrimMatri().getMes()) ||
				CADENA_VACIA.equals(vehiculo.getFechaPrimMatri().getAnio())){
			fechaPrimMatriculacion = false;
			listaMensajes.add(LA_FECHA_DE_PRIMERA_MATRICULACION_ES_OBLIGATORIA);
		}

		if (vehiculo.getMatricula()==null || CADENA_VACIA.equals(vehiculo.getMatricula())){
			listaMensajes.add(LA_MATRICULA_DEL_VEHICULO_ES_OBLIGATORIA);
		} else if(vehiculo.getMatricula().length() > 15){
			listaMensajes.add(LA_MATRICULA_DEL_VEHICULO_NO_PUEDE_TENER_MAS_DE_15_CARACTERES);
		}

		if (vehiculo.getBastidor()!=null && !CADENA_VACIA.equals(vehiculo.getBastidor()) && vehiculo.getBastidor().length() > 21) {
			listaMensajes.add(EL_BASTIDOR_DEL_VEHICULO_NO_PUEDE_TENER_MAS_DE_15_CARACTERES);
		}

		if (vehiculo.getTipVehi() == null){
			listaMensajes.add(EL_TIPO_DE_VEHICULO_EN_EL_MODELO_620_ES_OBLIGATORIO);
		}

		if (tramite.getTipoMotor() == null || tramite.getTipoMotor().getIdTipoMotor() == null || tramite.getTipoMotor().getIdTipoMotor().equals(CADENA_VACIA)) {
			listaMensajes.add(TIPO_MOTOR_OBLIGATORIO);
		}

		if (tramite.getValorDeclarado620() == null) {
			listaMensajes.add(EL_VALOR_DECLARADO_ES_OBLIGATORIO);
			tieneValorDeclarado = false;
		}

		if (vehiculo.getCdMarca() == null || CADENA_VACIA.equals(vehiculo.getCdMarca())) {
			listaMensajes.add(LA_MARCA_COMUNIDAD_AUTONOMA_DE_MADRID_DEL_VEHICULO_ES_OBLIGATORIA);
		}

		if (vehiculo.getCdModVeh() == null || CADENA_VACIA.equals(vehiculo.getCdModVeh())) {
			listaMensajes.add(EL_MODELO_COMUNIDAD_AUTONOMA_DE_MADRID_DEL_VEHICULO_ES_OBLIGATORIO);
		}

		if (vehiculo.getCdMarca()!=null && vehiculo.getCdModVeh()!=null && vehiculo.getTipVehi()!=null
				&& vehiculo.getTipVehi().getValorEnum()!=null && tramite.getFechaDevengoItp620()!=null) {
			try {
				// Si es un Turismo (Vehículo) o Todoterreno
				if (A.equals(vehiculo.getTipVehi().getValorEnum()) || B.equals(vehiculo.getTipVehi().getValorEnum())) {
					String cdMarca = vehiculo.getCdMarca();
					String cdModVeh = vehiculo.getCdModVeh();
					String tipVehi = vehiculo.getTipVehi().getValorEnum();
					String fecDesde = new SimpleDateFormat(FORMATO_FECHA).format(tramite.getFechaDevengoItp620().getFecha()).substring(0, 4) + "0101";

					List<MarcaCamVO> marcasCamVO = servicioMarcaCam.listaMarcaCam(tipVehi, cdMarca, fecDesde);

					if (null == marcasCamVO || marcasCamVO.isEmpty() || StringUtils.isBlank(marcasCamVO.get(0).getDsMarca())) {
						listaMensajes.add(NO_SE_HA_PODIDO_RECUPERAR_EL_NOMBRE_DE_LA_MARCA_Y_EL_MODELO_CAM);
					} else if (marcasCamVO.get(0).getDsMarca().length() > 30) {
						listaMensajes.add(LA_MARCA_COMUNIDAD_AUTONOMA_DE_MADRID_DEL_VEHICULO_NO_PUEDE_TENER_MAS_DE_30_CARACTERES);
					}

					List<ModeloCamDto> modelosCamDto = servicioModeloCam.listaModeloCam(tipVehi, fecDesde, cdMarca, cdModVeh);
					if (null == modelosCamDto || modelosCamDto.isEmpty() || StringUtils.isBlank(modelosCamDto.get(0).getDsModVeh())) {
						listaMensajes.add(NO_SE_HA_PODIDO_RECUPERAR_EL_NOMBRE_DE_LA_MARCA_Y_EL_MODELO_CAM);
					} else if (null != modelosCamDto && !modelosCamDto.isEmpty()
							&& (modelosCamDto.get(0)).getDsModVeh().length() > 150) {
						listaMensajes.add(EL_MODELO_COMUNIDAD_AUTONOMA_DE_MADRID_DEL_VEHICULO_NO_PUEDE_TENER_MAS_DE_150_CARACTERES);
					}
				} else { //En otro caso, la descripción es lo que tenemos guardado
					if(vehiculo.getCdMarca().length()>30)
						listaMensajes.add(LA_MARCA_COMUNIDAD_AUTONOMA_DE_MADRID_DEL_VEHICULO_NO_PUEDE_TENER_MAS_DE_30_CARACTERES);
					if(vehiculo.getCdModVeh().length()>150)
						listaMensajes.add(EL_MODELO_COMUNIDAD_AUTONOMA_DE_MADRID_DEL_VEHICULO_NO_PUEDE_TENER_MAS_DE_150_CARACTERES);
				}
			} catch(ParseException | NullPointerException e){
				listaMensajes.add(NO_SE_HA_PODIDO_RECUPERAR_LA_FECHA_DE_DEVENGO);
			}
		}

		boolean porcentajeReduccion = true;
		if(tramite.getPorcentajeReduccionAnual620()==null){
			listaMensajes.add(EL_PORCENTAJE_DE_REDUCCION_ANUAL_ES_OBLIGATORIO);
			porcentajeReduccion = false;
		}

		Date fechaDevengoDate = null;
		Date fecha1MatriculacionDate = null;

		if(fechaDevengo){
			try{
				fechaDevengoDate = (Date)tramite.getFechaDevengoItp620().getTimestamp();
				if (fechaDevengoDate.after(fechaActual)){
					listaMensajes.add(LA_FECHA_DE_DEVENGO_NO_PUEDE_SER_POSTERIOR_A_LA_ACTUAL);
					Date now = new Date();
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(now);
					calendar.set(Calendar.DAY_OF_MONTH, 31);
					calendar.set(Calendar.MONTH, Calendar.DECEMBER);
					int anioActual = calendar.get(Calendar.YEAR);
					int anioAnterior = anioActual-1;

					SimpleDateFormat formateadorFecha = new SimpleDateFormat("yyyy");
					String anioString = formateadorFecha.format(fechaDevengoDate);
					int anio = Integer.parseInt(anioString);

					if (anio!=anioAnterior && anio!=anioActual) {
						listaMensajes.add(EL_ANIO_DE_LA_FECHA_DE_DEVENGO_SOLO_PUEDE_SER_EL_ANIO_EN_CURSO_O_EL_ANTERIOR);
					}
				}
			}catch(ParseException e){
				listaMensajes.add(ERROR_AL_OBTENER_LA_FECHA_DE_DEVENGO);
			}
		}
		// Mantis 12982 David Sierra: se controla cuando el formato de la fecha de primera matriculación no es correcto
		if (fechaPrimMatriculacion) {
			try{
				fecha1MatriculacionDate = (Date)vehiculo.getFechaPrimMatri().getTimestamp();
				if(fecha1MatriculacionDate.after(fechaActual)){
					listaMensajes.add(LA_FECHA_DE_LA_PRIMERA_MATRICULACION_NO_PUEDE_SER_POSTERIOR_A_LA_ACTUAL);
				}
			}catch(ParseException e){
				listaMensajes.add(ERROR_FORMATO_FECHA_PRIMERA_MATRICULACION);
			}
			// Fin mantis 12982
		}
		try{
			if(fechaDevengo && fechaPrimMatriculacion && fecha1MatriculacionDate.after(fechaDevengoDate)){
				listaMensajes.add(LA_FECHA_DE_LA_PRIMERA_MATRICULACION_NO_PUEDE_SER_POSTERIOR_A_LA_FECHA_DE_DEVENGO);
			}
		}catch(NullPointerException e){
			listaMensajes.add(ERROR_AL_OBTENER_LA_FECHA_DE_PRIMERA_MATRICULACION);
		}

		//Llamamos a BD para obtener los datos del vehículo de la CAM y validar
		//Como ya tenemos implementada la llamada en DaoAjax, lo usamos

		// Mantis 22852. Se ha añadido isfechaNula(), ya que generaba un nullpointer
		if(vehiculo.getTipVehi()!=null && !tramite.getFechaDevengoItp620().isfechaNula()){
			try{
				String fecDesde = new SimpleDateFormat(FORMATO_FECHA).format(tramite.getFechaDevengoItp620().getFecha()).substring(0, 4) + "0101";

				List<ModeloCamDto> modelosCamDto = servicioModeloCam.listaModeloCam(vehiculo.getTipVehi().getValorEnum(), fecDesde, vehiculo.getCdMarca(), vehiculo.getCdModVeh());
				boolean error = false;
				if(modelosCamDto != null) {
					for(ModeloCamDto modelo : modelosCamDto){
						actualizarValoresVehiculoCam(modelo);
						if (tramite.getTipoMotor() != null && tramite.getTipoMotor().getIdTipoMotor() != null && tramite.getTipoMotor().getIdTipoMotor().equalsIgnoreCase(TipoMotor.Elc.value())) {
							actualizarValoresVehiculoBBDD(vehiculo);
						}
						if(modelo.getTipVehi().equals(TipoVehiculoCam.VEHICULO.getValorEnum()) ||
								modelo.getTipVehi().equals(TipoVehiculoCam.TODOTERRENO.getValorEnum())){
							boolean tienePotencia = true;
							boolean tieneCilindrada = true;

							if(vehiculo.getPotenciaFiscal()==null){
								listaMensajes.add(LA_POTENCIA_FISCAL_ES_OBLIGATORIA);
								tienePotencia = false;
								error = true;
							}

							if(vehiculo.getCilindrada()==null || CADENA_VACIA.equals(vehiculo.getCilindrada())){
								listaMensajes.add(LA_CILINDRADA_ES_OBLIGATORIA);
								tieneCilindrada = false;
								error = true;
							}

							if(tienePotencia){
								try {
									BigDecimal potencia = modelo.getvPotFisc();
									if(!potencia.equals(vehiculo.getPotenciaFiscal())){
										listaMensajes.add(LA_POTENCIA_FISCAL_NO_CONCUERDA_CON_LOS_DATOS_DE_LA_CAM);
										error = true;
									}
								} catch (NumberFormatException ex){
									listaMensajes.add(LA_POTENCIA_FISCAL_NO_TIENE_EL_FORMATO_CORRECTO);
									error = true;
								}
							}

							if(tieneCilindrada){
								try {
									BigDecimal cilindrada = modelo.getvCilindr();
									if(!cilindrada.equals(new BigDecimal(vehiculo.getCilindrada()))){
										listaMensajes.add(LA_CILINDRADA_NO_CONCUERDA_CON_LOS_DATOS_DE_LA_CAM);
										error = true;
									}
								} catch (NumberFormatException ex){
									listaMensajes.add(LA_CILINDRADA_NO_TIENE_EL_FORMATO_CORRECTO);
									error = true;
								}
							}

							if (FALSE.equals(tramite.getCheck620ValorManual()) && tieneValorDeclarado) {
								try {
									BigDecimal valorDeclarado = modelo.getvPreVehi();
									if(!valorDeclarado.equals(tramite.getValorDeclarado620())){
										listaMensajes.add(EL_VALOR_DECLARADO_NO_CONCUERDA_CON_LOS_DATOS_DE_LA_CAM);
										error = true;
									}
								} catch (NumberFormatException ex){
									listaMensajes.add(EL_VALOR_DECLARADO_NO_TIENE_EL_FORMATO_CORRECTO);
									error = true;
								}
							}

							// Si es necesario habrá que validar el porcentaje de reducción
							if (fechaPrimMatriculacion && porcentajeReduccion) {
								Date fechaMat = null;
								Date fechaDev = null;
								//Obtener el porcentaje de la Base imponible
								try {
									fechaMat = (Date)vehiculo.getFechaPrimMatri().getTimestamp();
									fechaDev = (Date)tramite.getFechaDevengoItp620().getTimestamp();
								} catch (ParseException e) {
									listaMensajes.add(ERROR_AL_OBTENER_LA_FECHA_DE_DEVENGO_Y_LA_FECHA_DE_PRIMERA_MATRICULACION);
									error = true;
								}
								// Mantis 12982. David Sierra: Se evita un error cuando la fecha de primera matriculación es superior al día actual
								if(!fecha1MatriculacionDate.after(fechaActual)){
									long diferencia = fechaDev.getTime() - fechaMat.getTime();
									int aniosDiff =(int)Math.floor((diferencia/(1000*60*60*24*365.2425)));
									if(aniosDiff > 12)
										aniosDiff = 12;

									String porcentajeEnTabla = PorcentajeReduccionAnual.convertirTexto(aniosDiff);
									BigDecimal porcentaje = tramite.getPorcentajeReduccionAnual620();
									if(porcentaje.intValue() != Integer.parseInt(porcentajeEnTabla)){
										listaMensajes.add(EL_PORCENTAJE_DE_REDUCCION_NO_CONCUERDA_CON_LOS_DATOS_DE_LA_CAM);
										error = true;
									}
								} // Fin Mantis 12982
							}
						}
						if (error)
							break;
					}
				}
			}catch(ParseException e){
				listaMensajes.add(ERROR_AL_OBTENER_LA_FECHA_DE_DEVENGO);
			}catch(NullPointerException e){
				log.error("Error al validar el 620", e);
				listaMensajes.add(e.getMessage());
			}
		}

		/*
		 * LIQUIDACIÓN
		 */
		if(tramite.getPorcentajeAdquisicion620()==null){
			listaMensajes.add(EL_PORCENTAJE_DE_ADQUISICION_ES_OBLIGATORIO);
		}

		if(tramite.getBaseImponible620()==null){
			listaMensajes.add(LA_BASE_IMPONIBLE_ES_OBLIGATORIA);
		}

		boolean estaExento = false;
		String exento = tramite.getExento620();
		if(TRUE.equalsIgnoreCase(exento)){
			estaExento = true;
			if(tramite.getFundamentoExencion620()==null || CADENA_VACIA.equals(tramite.getFundamentoExencion620()) || "-1".equals(tramite.getFundamentoExencion620())){
				listaMensajes.add(EL_FUNDAMENTO_DE_EXENCION_ES_OBLIGATORIO_SI_ES_EXENTO);
			} else if(tramite.getFundamentoExencion620().length() > 100){
				listaMensajes.add(EL_FUNDAMENTO_DE_EXENCION_NO_PUEDE_TENER_MAS_DE_100_CARACTERES);
			} else {
				FundamentoExencion620VO fundamentoExencion = servicioFundamentoExencion620.getlistaFundamentoExencion620(tramite.getFundamentoExencion620());
				if (fundamentoExencion == null || StringUtils.isBlank(fundamentoExencion.getDescripcion())) {
					listaMensajes.add(NO_SE_HA_PODIDO_RECUPERAR_EL_NOMBRE_DEL_FUNDAMENTO_DE_EXENCION);
				} else if (fundamentoExencion.getDescripcion().length() > 100) {
					listaMensajes.add(EL_FUNDAMENTO_DE_EXENCION_NO_PUEDE_TENER_MAS_DE_100_CARACTERES);
				}
			}
		}

		boolean estaNoSujeto = false;
		String noSujeto = tramite.getNoSujeto620();
		if(TRUE.equalsIgnoreCase(noSujeto)){
			estaNoSujeto = true;
			if(tramite.getFundamentoNoSujeccion620()==null || CADENA_VACIA.equals(tramite.getFundamentoNoSujeccion620()) || "-1".equals(tramite.getFundamentoNoSujeccion620())){
				listaMensajes.add(EL_FUNDAMENTO_DE_NO_SUJECCION_ES_OBLIGATORIO_SI_ES_NO_SUJETO);
			} else if(tramite.getFundamentoNoSujeccion620().length()>100){
				listaMensajes.add(EL_FUNDAMENTO_DE_NO_SUJECCION_NO_PUEDE_TENER_MAS_DE_100_CARACTERES);
			}
		}

		if (!estaExento && !estaNoSujeto) {
			if (tramite.getCuotaTributaria620() == null ||tramite.getTipoGravamen620() == null){
				listaMensajes.add(SI_NO_MARCA_EXENTO_O_NO_SUJETO_DEBE_INTRODUCIR_LA_CUOTA_TRIBUTARIA_Y_EL_TIPO_DE_GRAVAMEN);
			}

			if(!new BigDecimal(_4_0).equals(tramite.getTipoGravamen620()) && !new BigDecimal(_4).equals(tramite.getTipoGravamen620())){
				listaMensajes.add(EL_TIPO_DE_GRAVAMEN_SOLO_PUEDE_TENER_EL_VALOR_4_0);
			}
		}

		if (listaMensajes.isEmpty()) {
			BeanPQTramiteTraficoGuardarTransmision beanParam = transmisionTramiteTraficoBeanPQConversion.convertirTramiteTransmisionToPQ(tramite).getBeanGuardarTransmision();
			// Llamamos al procedimiento validar620 para cambiar el estado del modelo 620
			RespuestaGenerica resp = ejecutarProc(beanParam,valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,"VALIDAR_620",BeanPQGeneral.class);

			// Recuperamos información de respuesta
			BigDecimal pCode = (BigDecimal)resp.getParametro(ConstantesPQ.P_CODE);

			// Si el pcode no es 0 es que ha habido un error. Lo indicamos.
			if (!ConstantesPQ.pCodeOk.equals(pCode)) {
				listaMensajes.add(LA_VALIDACION_FUE_CORRECTA_PERO_HA_HABIDO_UN_ERROR_AL_CAMBIAR_EL_ESTADO_DEL_TRAMITE);
			} else {
				tramite.setEstado620(((BigDecimal)resp.getParametro("P_ESTADO_620")).toString());
			}
		}

		return listaMensajes;
	}

	/**
	 * Setea a 0 los valores de la potencia fiscal, cilindrada y número de
	 * cilindros si estos vienen nulos.
	 * @author david.amaya
	 * @param modeloCamDto objeto de tipo ModeloCamDto
	 */
	private void actualizarValoresVehiculoCam(ModeloCamDto modeloCamDto) {
		if (modeloCamDto.getvPotFisc() == null) {
			modeloCamDto.setvPotFisc(new BigDecimal(0));
		}
		if (modeloCamDto.getvCilindr() == null) {
			modeloCamDto.setvCilindr(new BigDecimal(0));
		}
		if (modeloCamDto.getNumCilin() == null) {
			modeloCamDto.setNumCilin(new BigDecimal(0));
		}
	}

	/**
	 * Setea a 0 los valores de la potencia fiscal, cilindrada y número de
	 * cilindros si estos vienen nulos.
	 * @author david.amaya
	 * @param vehiculo objeto de tipo VehiculoBean
	 */
	private void actualizarValoresVehiculoBBDD(VehiculoBean vehiculo) {
		if (vehiculo.getPotenciaFiscal() == null) {
			vehiculo.setPotenciaFiscal(new BigDecimal(0));
		}
		if (vehiculo.getCilindrada() == null) {
			vehiculo.setCilindrada("0");
		}
		if (vehiculo.getNumCilindros() == null) {
			vehiculo.setNumCilindros(new BigDecimal(0));
		}
	}

	/**
	 * @author antonio.miguez
	 * @param List<TramiteTraficoTransmisionBean>, String
	 * @return Map<String,Object>
	 * @throws Throwable 
	 */
	/*
	 * Método para generar el XML para G@ta
	 */
	public Map<String,Object> generarXMLJaxb(List<TramiteTraficoTransmisionBean> tramites, String idSession) throws Throwable {
		Map<String, Object> resultado = new HashMap<>();
		String ruta = new UtilesWSCTIT().getRutaXML620(); // Si no existe la carpeta, la creamos

		// Si no existe la carpeta, la creamos
		File carpeta = new File(ruta.substring(0, ruta.length()-1));
		if (!carpeta.exists())
			carpeta.mkdirs();

		// Creamos el fichero
		File fichero = new File(ruta);
		// A partir del objeto File creamos el fichero físicamente
		try{
			fichero.createNewFile();
			fichero.setReadable(true);
			fichero.setWritable(true);
		} catch(IOException e){
			log.error(ERROR_AL_GUARDAR_EL_XML_DE_AUDITORIA_DE_TRANSMISION);
		}

		List<String> errores = new ArrayList<>();

		//Creamos cada uno de los nodos del XML
		//Comenzamos por el Object Factory
		trafico.beans.transmision.jaxb.ObjectFactory objFactory = new trafico.beans.transmision.jaxb.ObjectFactory();

		//Nodo principal: Remesa
		Remesa remesa = objFactory.createRemesa();
		//Atributos simples
		remesa.setCodigo(_00001); // Generado por G@ta
		remesa.setFecEmision(new SimpleDateFormat(FORMATO_FECHA_2).format(new Date()));
		remesa.setTotalDecl(new BigInteger(CADENA_VACIA+tramites.size())); // Núm. declaraciones
		remesa.setVersioncodigo(_1_6); // Valor fijo
		remesa.setVersionXsd(CADENA_VACIA); // Sin informar

		for(TramiteTraficoTransmisionBean tramite:tramites){
			/* INICIO MANTIS 0012707: Capturamos el error por si falla la creación de la acción */
			try {
				//Iniciamos la acción para la generación del XML 620
				getModeloAcciones().crearAccion(utilesColegiado.getIdUsuarioSessionBigDecimal(),
											tramite.getTramiteTraficoBean().getNumExpediente(),
											TipoAccion.GeneracionXML620.getValorEnum());
			} catch (AccionException e) {
				errores.add(ERROR_AL_CREAR_LA_ACCION_PARA_GENERAR_EL_XML_620);
				// Si ha habido un error, no podemos seguir con este trámite
				continue;
			}
			/* FIN MANTIS 0012707 */

			//MANTIS 28738: Mandar correo cuando alguien modifica manualmente el valor declarado en un modelo 620
			try{
				TramiteTrafTranDto tramiteTraf = servicioTramiteTraficoTransmision.getTramiteTransmision(tramite.getTramiteTraficoBean().getNumExpediente(), true);
				tramiteTraf.setFechaGeneracion620(new Date());
				servicioTramiteTraficoTransmision.actualizarTramiteTransmision(tramiteTraf);
			}catch(Exception e){
				log.error("No se ha podido actualizar la fecha de generación del 620", e);
			}

			String enviarCorreo620 = gestorPropiedades.valorPropertie("envio.correo.transmision.620");
			if (enviarCorreo620 != null && SI.equals(enviarCorreo620)
				&& TRUE.equals(tramite.getCheck620ValorManual()) && FALSE.equals(tramite.getExento620())
					&& calcularDatos(tramite)) {
				String texto = montarTexto(tramite);
				servicioCorreo.enviarCorreo(texto, null, null, gestorPropiedades.valorPropertie(PROPERTIE_CORREO_ASUNTO), gestorPropiedades.valorPropertie(PROPERTIE_CORREO_PARA),
						gestorPropiedades.valorPropertie(PROPERTIE_CORREO_COPIA), null, null, null);
			}

			//Creamos la declaración...
			Declaracion declaracion = objFactory.createRemesaDeclaracion();
			//El código será el núm. expediente menos los 4 primeros dígitos, que corresponden al núm. colegiado
			int longitudExpediente = tramite.getTramiteTraficoBean().getNumExpediente().toString().length();
			if (longitudExpediente == 15) {
				declaracion.setCodigo(_00+tramite.getTramiteTraficoBean().getNumExpediente().toString().substring(4));
			} else if (longitudExpediente == 14) {
				declaracion.setCodigo(_00+tramite.getTramiteTraficoBean().getNumExpediente().toString().substring(3));
			}
			declaracion.setModelo(_620A4);
			declaracion.setVersionXsdModelo(_1_4);

			//Rellenamos los objetos de la declaración
			//Primero la lista de intervinientes...
			ListaIntervinientes listaIntervinientes = objFactory.createRemesaDeclaracionListaIntervinientes();

			//El ADQUIRIENTE...
			IntervinienteA2Type adquiriente = objFactory.createIntervinienteA2Type(); // El del XML
			IntervinienteTrafico adquirienteBean = tramite.getAdquirienteBean(); // El del Bean
			//Seteamos
			adquiriente.setTipo(TipoIntervinienteType.SUJETO_PASIVO);
			adquiriente.setNif(adquirienteBean.getPersona().getNif());
			if(validarCIF(adquirienteBean.getPersona().getNif())){
				adquiriente.setNombre(CADENA_VACIA);
				adquiriente.setApellido1(CADENA_VACIA);
				adquiriente.setApellido2(CADENA_VACIA);
				adquiriente.setNombreRazonsocial(adquirienteBean.getPersona().getApellido1RazonSocial());
			} else {
				adquiriente.setNombre(adquirienteBean.getPersona().getNombre());
				adquiriente.setApellido1(adquirienteBean.getPersona().getApellido1RazonSocial());
				adquiriente.setApellido2(null != adquirienteBean.getPersona().getApellido2() || CADENA_VACIA.equals(adquirienteBean.getPersona().getApellido2())
						? adquirienteBean.getPersona().getApellido2() : CADENA_VACIA);
				adquiriente.setNombreRazonsocial(CADENA_VACIA);
			}

			Direccion dir = null;
			RespuestaGenerica respuesta = null;
			if(tramite.getDireccionDistintaAdquiriente620()!=null && tramite.getDireccionDistintaAdquiriente620().equals(TRUE)){
				dir = tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean();
				//Llamamos a PQ_DIRECCION.VIA_EQUIVALENTE, que nos devolverá la provincia, el municipio y
				//el tipo de vía de la CAM equivalente
				BeanPQDireccionViaEquivalente beanPQDireccionViaEquivalente = new BeanPQDireccionViaEquivalente();
				beanPQDireccionViaEquivalente.setP_ORGANISMO(CAM);
				beanPQDireccionViaEquivalente.setP_ID_MUNICIPIO(dir.getMunicipio().getIdMunicipio());
				beanPQDireccionViaEquivalente.setP_ID_PROVINCIA(dir.getMunicipio().getProvincia().getIdProvincia());
				beanPQDireccionViaEquivalente.setP_ID_TIPO_VIA(dir.getTipoVia().getIdTipoVia());
				respuesta = ejecutarProc(beanPQDireccionViaEquivalente,valoresSchemas.getSchema(),ValoresCatalog.PQ_DIRECCION, "VIA_EQUIVALENTE", BeanPQGeneral.class);
				BigDecimal pCode = (BigDecimal)respuesta.getParametro(ConstantesPQ.P_CODE);
				String sqlErrm = (String)respuesta.getParametro(ConstantesPQ.P_SQLERRM);
				if (!ConstantesPQ.pCodeOk.equals(pCode)) {
					errores.add(NO_SE_HA_PODIDO_RECUPERAR_INFORMACION_DE_LA_PROVINCIA_MUNICIPIO_Y_TIPO_DE_VIA_DEL_ADQUIRIENTE+sqlErrm);
				}
				adquiriente.setProvincia(ProvinciaType.fromValue(dir.getMunicipio().getProvincia().getIdProvincia()));
				//31-01-2023. DVV. Se añade toString().
				adquiriente.setProvinciaDesc(respuesta.getParametro("P_PROVINCIA") != null ?
						  ProvinciaDescType.fromValue(((String)respuesta.getParametro("P_PROVINCIA")).trim()).toString()
						: ProvinciaDescType.fromValue("DESCONOCIDA").toString());
				adquiriente.setMunicipio(respuesta.getParametro("P_ID_MUNICIPIO_CAM") != null ? ((String)respuesta.getParametro("P_ID_MUNICIPIO_CAM")).trim():CADENA_VACIA);
				adquiriente.setMunicipioDesc(respuesta.getParametro("P_NOMBRE")!=null ? ((String)respuesta.getParametro("P_NOMBRE")).trim():CADENA_VACIA);
				adquiriente.setSiglas(SiglasType.fromValue(respuesta.getParametro("P_VIA_EQUI")!=null ? ((String)respuesta.getParametro("P_VIA_EQUI")).trim():CADENA_VACIA));
			} else {
				dir = adquirienteBean.getPersona().getDireccion();
				//Llamamos a PQ_DIRECCION.VIA_EQUIVALENTE, que nos devolverá la provincia, el municipio y
				//el tipo de vía de la CAM equivalente
				BeanPQDireccionViaEquivalente beanPQDireccionViaEquivalente = new BeanPQDireccionViaEquivalente();
				beanPQDireccionViaEquivalente.setP_ORGANISMO(CAM);
				beanPQDireccionViaEquivalente.setP_ID_MUNICIPIO(dir.getMunicipio().getIdMunicipio());
				beanPQDireccionViaEquivalente.setP_ID_PROVINCIA(dir.getMunicipio().getProvincia().getIdProvincia());
				beanPQDireccionViaEquivalente.setP_ID_TIPO_VIA(dir.getTipoVia().getIdTipoVia());
				respuesta = ejecutarProc(beanPQDireccionViaEquivalente,valoresSchemas.getSchema(),ValoresCatalog.PQ_DIRECCION,"VIA_EQUIVALENTE",BeanPQGeneral.class);
				BigDecimal pCode = (BigDecimal)respuesta.getParametro(ConstantesPQ.P_CODE);
				String sqlErrm = (String)respuesta.getParametro(ConstantesPQ.P_SQLERRM);
				if(!ConstantesPQ.pCodeOk.equals(pCode)){
					errores.add(NO_SE_HA_PODIDO_RECUPERAR_INFORMACION_DE_LA_PROVINCIA_MUNICIPIO_Y_TIPO_DE_VIA_DEL_ADQUIRIENTE+sqlErrm);
				}
				adquiriente.setProvincia(ProvinciaType.fromValue(dir.getMunicipio().getProvincia().getIdProvincia()));
				//31-01-2023. DVV. Se añade toString().
				adquiriente.setProvinciaDesc(ProvinciaDescType.fromValue(((String)respuesta.getParametro("P_PROVINCIA")).trim()).toString());
				adquiriente.setMunicipio(((String)respuesta.getParametro("P_ID_MUNICIPIO_CAM")).trim());
				adquiriente.setMunicipioDesc(((String)respuesta.getParametro("P_NOMBRE")).trim());
				adquiriente.setSiglas(SiglasType.fromValue(((String)respuesta.getParametro("P_VIA_EQUI")).trim()));
			}
			adquiriente.setSiglasDesc(respuesta.getParametro(P_DESCRIPCION)!=null?((String)respuesta.getParametro(P_DESCRIPCION)).trim():CADENA_VACIA);
			adquiriente.setNombreVia(utilesConversiones.ajustarCamposIne(dir.getNombreVia()));
			adquiriente.setNumero(null!=dir.getNumero() && !CADENA_VACIA.equals(dir.getNumero()) && !SN.equals(dir.getNumero()) ? dir.getNumero() : SN_620);
			adquiriente.setEscalera(null!=dir.getEscalera() ? dir.getEscalera() : CADENA_VACIA);
			adquiriente.setPiso(null!=dir.getPlanta() ? dir.getPlanta() : CADENA_VACIA);
			adquiriente.setPuerta(null!=dir.getPuerta() ? dir.getPuerta() : CADENA_VACIA);
			adquiriente.setCp(dir.getCodPostal());
			adquiriente.setTelf(null!=adquirienteBean.getPersona().getTelefonos()?adquirienteBean.getPersona().getTelefonos():CADENA_VACIA);

			//El PRESENTADOR...
			//Cambios en el presentador por petición de colegiados de Toledo.
			//Ahora se deben pasar los datos de la gestoría en vez de los datos de los gestores.
			IntervinienteA2Type presentador = objFactory.createIntervinienteA2Type(); // El del XML
			ResultBean resultadoDatosGestoria = modeloContratoNuevo.obtenerDatosGestoria(tramite.getTramiteTraficoBean().getIdContrato());

			IntervinienteTrafico presentadorBean = new IntervinienteTrafico(true);

			if (!resultadoDatosGestoria.getError()) {
				presentadorBean = (IntervinienteTrafico) resultadoDatosGestoria.getAttachment(ConstantesPQ.BEANPANTALLA);
			} else{
				presentadorBean = tramite.getPresentadorBean();
				errores.add(resultadoDatosGestoria.getMensaje());
			}

			// Seteamos
			presentador.setTipo(TipoIntervinienteType.PRESENTADOR);
			presentador.setNif(presentadorBean.getPersona().getNif());
			if (validarCIF(presentadorBean.getPersona().getNif())) {
				presentador.setNombre(CADENA_VACIA);
				presentador.setApellido1(CADENA_VACIA);
				presentador.setApellido2(CADENA_VACIA);
				presentador.setNombreRazonsocial(presentadorBean.getPersona().getApellido1RazonSocial());
			} else {
				presentador.setNombre(presentadorBean.getPersona().getNombre());
				presentador.setApellido1(presentadorBean.getPersona().getApellido1RazonSocial());
				presentador.setApellido2(null != presentadorBean.getPersona().getApellido2()
						|| CADENA_VACIA.equals(presentadorBean.getPersona().getApellido2())
								? presentadorBean.getPersona().getApellido2()
								: CADENA_VACIA);
				presentador.setNombreRazonsocial(CADENA_VACIA);
			}

			dir = presentadorBean.getPersona().getDireccion();
			// Llamamos a PQ_DIRECCION.VIA_EQUIVALENTE, que nos devolverá la provincia, el municipio y
			//el tipo de vía de la CAM equivalente
			BeanPQDireccionViaEquivalente beanPQDireccionViaEquivalente = new BeanPQDireccionViaEquivalente();
			beanPQDireccionViaEquivalente.setP_ORGANISMO(CAM);
			beanPQDireccionViaEquivalente.setP_ID_MUNICIPIO(dir.getMunicipio().getIdMunicipio());
			beanPQDireccionViaEquivalente.setP_ID_PROVINCIA(dir.getMunicipio().getProvincia().getIdProvincia());
			beanPQDireccionViaEquivalente.setP_ID_TIPO_VIA(dir.getTipoVia().getIdTipoVia());
			respuesta = ejecutarProc(beanPQDireccionViaEquivalente,valoresSchemas.getSchema(),ValoresCatalog.PQ_DIRECCION, "VIA_EQUIVALENTE", BeanPQGeneral.class);
			BigDecimal pCode = (BigDecimal)respuesta.getParametro(ConstantesPQ.P_CODE);
			String sqlErrm = (String)respuesta.getParametro(ConstantesPQ.P_SQLERRM);
			if(!ConstantesPQ.pCodeOk.equals(pCode)){
				errores.add(NO_SE_HA_PODIDO_RECUPERAR_INFORMACION_DE_LA_PROVINCIA_MUNICIPIO_Y_TIPO_DE_VIA_DEL_PRESENTADOR+sqlErrm);
			}
			presentador.setProvincia(ProvinciaType.fromValue(dir.getMunicipio().getProvincia().getIdProvincia()));
			// 31-01-2023. DVV. Se añade toString() para tratar campo como String.
			presentador.setProvinciaDesc(ProvinciaDescType.fromValue(((String)respuesta.getParametro("P_PROVINCIA")).trim()).toString());
			presentador.setMunicipio(((String)respuesta.getParametro("P_ID_MUNICIPIO_CAM")).trim());
			presentador.setMunicipioDesc(((String)respuesta.getParametro("P_NOMBRE")).trim());
			presentador.setSiglas(SiglasType.fromValue(((String)respuesta.getParametro("P_VIA_EQUI")).trim()));
			presentador.setSiglasDesc(respuesta.getParametro(P_DESCRIPCION)!=null?((String)respuesta.getParametro(P_DESCRIPCION)).trim():CADENA_VACIA);
			presentador.setNombreVia(utilesConversiones.ajustarCamposIne(dir.getNombreVia()));
			presentador.setNumero(null!=dir.getNumero() && !CADENA_VACIA.equals(dir.getNumero()) && !SN.equals(dir.getNumero()) ? dir.getNumero() : SN_620);
			presentador.setEscalera(null!=dir.getEscalera()?dir.getEscalera():CADENA_VACIA);
			presentador.setPiso(null!=dir.getPlanta()?dir.getPlanta():CADENA_VACIA);
			presentador.setPuerta(null!=dir.getPuerta()?dir.getPuerta():CADENA_VACIA);
			presentador.setCp(dir.getCodPostal());
			presentador.setTelf(null!=presentadorBean.getPersona().getTelefonos() ? presentadorBean.getPersona().getTelefonos() : CADENA_VACIA);

			//El TRANSMITENTE...
			IntervinienteA2Type transmitente = objFactory.createIntervinienteA2Type(); // El del XML
			IntervinienteTrafico transmitenteBean = tramite.getTransmitenteBean(); // El del Bean
			//Seteamos
			transmitente.setTipo(TipoIntervinienteType.TRANSMITENTE);
			transmitente.setNif(transmitenteBean.getPersona().getNif());
			if (validarCIF(transmitenteBean.getPersona().getNif())) {
				transmitente.setNombre(CADENA_VACIA);
				transmitente.setApellido1(CADENA_VACIA);
				transmitente.setApellido2(CADENA_VACIA);
				transmitente.setNombreRazonsocial(transmitenteBean.getPersona().getApellido1RazonSocial());
			} else {
				transmitente.setNombre(transmitenteBean.getPersona().getNombre());
				transmitente.setApellido1(transmitenteBean.getPersona().getApellido1RazonSocial());
				transmitente.setApellido2(null!=transmitenteBean.getPersona().getApellido2() || CADENA_VACIA.equals(transmitenteBean.getPersona().getApellido2()) ? transmitenteBean.getPersona().getApellido2() : CADENA_VACIA);
				transmitente.setNombreRazonsocial(CADENA_VACIA);
			}

			dir = transmitenteBean.getPersona().getDireccion();
			//Llamamos a PQ_DIRECCION.VIA_EQUIVALENTE, que nos devolverá la provincia, el municipio y
			//el tipo de vía de la CAM equivalente
			beanPQDireccionViaEquivalente = new BeanPQDireccionViaEquivalente();
			beanPQDireccionViaEquivalente.setP_ORGANISMO(CAM);
			beanPQDireccionViaEquivalente.setP_ID_MUNICIPIO(dir.getMunicipio().getIdMunicipio());
			beanPQDireccionViaEquivalente.setP_ID_PROVINCIA(dir.getMunicipio().getProvincia().getIdProvincia());
			beanPQDireccionViaEquivalente.setP_ID_TIPO_VIA(dir.getTipoVia().getIdTipoVia());
			respuesta = ejecutarProc(beanPQDireccionViaEquivalente,valoresSchemas.getSchema(),ValoresCatalog.PQ_DIRECCION, "VIA_EQUIVALENTE", BeanPQGeneral.class);
			pCode = (BigDecimal)respuesta.getParametro(ConstantesPQ.P_CODE);
			sqlErrm = (String)respuesta.getParametro(ConstantesPQ.P_SQLERRM);
			if (!ConstantesPQ.pCodeOk.equals(pCode)) {
				errores.add(NO_SE_HA_PODIDO_RECUPERAR_INFORMACION_DE_LA_PROVINCIA_MUNICIPIO_Y_TIPO_DE_VIA_DEL_ADQUIRIENTE+sqlErrm);
			}
			transmitente.setProvincia(ProvinciaType.fromValue(dir.getMunicipio().getProvincia().getIdProvincia()));
			//31-01-2023. DVV. Se añade toString().
			transmitente.setProvinciaDesc(ProvinciaDescType.fromValue(((String)respuesta.getParametro("P_PROVINCIA")).trim()).toString());
			transmitente.setMunicipio(((String)respuesta.getParametro("P_ID_MUNICIPIO_CAM")).trim());
			transmitente.setMunicipioDesc(((String)respuesta.getParametro("P_NOMBRE")).trim());
			transmitente.setSiglas(SiglasType.fromValue(((String)respuesta.getParametro("P_VIA_EQUI")).trim()));
			transmitente.setSiglasDesc(respuesta.getParametro(P_DESCRIPCION) != null ? ((String)respuesta.getParametro(P_DESCRIPCION)).trim() : CADENA_VACIA);
			transmitente.setNombreVia(utilesConversiones.ajustarCamposIne(dir.getNombreVia()));

			transmitente.setNumero(null!=dir.getNumero() && !CADENA_VACIA.equals(dir.getNumero()) && !SN.equals(dir.getNumero()) ? dir.getNumero() : SN_620);
			transmitente.setEscalera(null!=dir.getEscalera() ? dir.getEscalera() : CADENA_VACIA);
			transmitente.setPiso(null!=dir.getPlanta() ? dir.getPlanta() : CADENA_VACIA);
			transmitente.setPuerta(null!=dir.getPuerta() ? dir.getPuerta() : CADENA_VACIA);
			transmitente.setCp(dir.getCodPostal());
			transmitente.setTelf(null!=transmitenteBean.getPersona().getTelefonos() ? transmitenteBean.getPersona().getTelefonos() : CADENA_VACIA);

			// Metemos cada interviniente en la lista de intervinientes
			listaIntervinientes.getInterviniente().add(adquiriente);
			listaIntervinientes.getInterviniente().add(presentador);
			listaIntervinientes.getInterviniente().add(transmitente);

			// Metemos la lista de intervinientes en la declaración
			declaracion.setListaIntervinientes(listaIntervinientes);

			// Segundo, la identificación...
			Identificacion identificacion = objFactory.createRemesaDeclaracionIdentificacion();
			identificacion.setOficinaLiquidadora(OficinaLiquidadoraType.fromValue("000")); // 07/06/2023. Se establece este valor a petición expresa de D. G. Tributos
			identificacion.setOficinaLiquidadoraDesc("MADRID CAPITAL (D.G. TRIBUTOS)"); // 07/06/2023. Se establece este valor a petición expresa de D. G. Tributos

			identificacion.setIndTransmitente(!transmitente.getNif().isEmpty() ? S : N);

			try {
				identificacion.setFechaDevengo(new SimpleDateFormat(FORMATO_FECHA_2).format(tramite.getFechaDevengoItp620().getTimestamp()));
				identificacion.setFechaMatricula(new SimpleDateFormat(FORMATO_FECHA_2).format(tramite.getTramiteTraficoBean().getVehiculo().getFechaPrimMatri().getTimestamp()));
			} catch(ParseException e) {
				errores.add(NO_SE_HA_PODIDO_RECUPERAR_NI_LA_FECHA_DE_DEVENGO_NI_LA_DE_PRIMERA_MATRICULACION);
			}

			identificacion.setNumMatricula(tramite.getTramiteTraficoBean().getVehiculo().getMatricula() != null ? tramite.getTramiteTraficoBean().getVehiculo().getMatricula() : CADENA_VACIA);
			identificacion.setTipo(TipoType.fromValue(tramite.getTramiteTraficoBean().getVehiculo().getTipVehi().getValorEnum()));
			identificacion.setTipoDesc(tramite.getTramiteTraficoBean().getVehiculo().getTipVehi().getNombreEnum());

			// Metemos la identificación en la declaración
			declaracion.setIdentificacion(identificacion);

			// Tercero, el vehículo
 			Vehiculos vehiculos = objFactory.createRemesaDeclaracionVehiculos();
			VehiculoBean vehiculoBean = tramite.getTramiteTraficoBean().getVehiculo();
			String tipoVehiculo = vehiculoBean.getTipVehi().getValorEnum();
			// Traemos de BD la descripción de marca y modelo
			// Si el vehículo es un Turismo o Todoterreno, tenemos sus id y hay que recuperar la descripción
			if (A.equals(tipoVehiculo) || B.equals(tipoVehiculo)) {
				String cdMarca = vehiculoBean.getCdMarca();
				String cdModVeh = vehiculoBean.getCdModVeh();
				String fecDesde = new SimpleDateFormat(FORMATO_FECHA).format(tramite.getFechaDevengoItp620().getFecha()).substring(0, 4) + "0101";

				List<MarcaCamVO> marcasCamVO = servicioMarcaCam.listaMarcaCam(tipoVehiculo, cdMarca, fecDesde);
				vehiculos.setMarca(vehiculoBean.getCdMarca());
				vehiculos.setMarcaDesc((marcasCamVO.get(0)).getDsMarca());

				List<ModeloCamDto> modelosCamDto = servicioModeloCam.listaModeloCam(tipoVehiculo, fecDesde, cdMarca,
						cdModVeh);
				vehiculos.setModelo(vehiculoBean.getCdModVeh());
				vehiculos.setModeloDesc((modelosCamDto.get(0)).getDsModVeh());
			} else if (C.equals(tipoVehiculo)) { // Si el vehículo es una moto no seteamos la Marca ni el Modelo, solo sus descripciones
				vehiculos.setMarca(CADENA_VACIA);
				vehiculos.setMarcaDesc(vehiculoBean.getCdMarca());
				vehiculos.setModelo(CADENA_VACIA);
				vehiculos.setModeloDesc(vehiculoBean.getCdModVeh());
			} else { // Si el vehículo es de otro tipo, tenemos directamente su descripción
				vehiculos.setMarca(vehiculoBean.getCdMarca());
				vehiculos.setMarcaDesc(vehiculoBean.getCdMarca());
				vehiculos.setModelo(vehiculoBean.getCdModVeh());
				vehiculos.setModeloDesc(vehiculoBean.getCdModVeh());
			}

			if (A.equals(tipoVehiculo) || B.equals(tipoVehiculo)) {
				vehiculos.setPotencia((null != vehiculoBean.getPotenciaFiscal()) ? formatoDecimalGata(vehiculoBean.getPotenciaFiscal()) : CADENA_VACIA);
			} else {
				vehiculos.setPotencia(CADENA_VACIA);
			}

			// Cambios Modelo 620
			if (tramite.getProcedencia() == null)
				tramite.setProcedencia(CADENA_VACIA);
			vehiculos.setProcedenciaDesc(tramite.getProcedencia());
			vehiculos.setSubasta(TRUE.equalsIgnoreCase(tramite.getSubasta()) ? S : N);

			// Se eliminan campos IndGasolina e IndDiesel. Ya no se realiza esa lógica, y en su lugar se añade tipoMotor.
			if (tramite.getTipoMotor() == null || tramite.getTipoMotor().getIdTipoMotor() == null) {
				vehiculos.setTipoMotor(CADENA_VACIA);
			} else {
				vehiculos.setTipoMotor(tramite.getTipoMotor().getIdTipoMotor() != null ? tramite.getTipoMotor().getIdTipoMotor() : CADENA_VACIA);
			}

			vehiculos.setCilindrada((null!=vehiculoBean.getCilindrada() && !vehiculoBean.getCilindrada().equalsIgnoreCase(CADENA_VACIA)) ? formatoDecimalGata(vehiculoBean.getCilindrada()) : null);
			vehiculos.setBastidor(null!=vehiculoBean.getBastidor() ? vehiculoBean.getBastidor() : CADENA_VACIA);

			// Metemos el vehículo en la declaración
			declaracion.setVehiculos(vehiculos);

			// Cuarto, la liquidación
			Liquidacion liquidacion = objFactory.createRemesaDeclaracionLiquidacion();
			liquidacion.setExento(TRUE.equals(tramite.getExento620())?S:N);
			liquidacion.setNoSujeto(TRUE.equals(tramite.getNoSujeto620())?S:N);
			if (TRUE.equals(tramite.getExento620()) || TRUE.equals(tramite.getNoSujeto620())) {
				if (TRUE.equals(tramite.getExento620())) {
					liquidacion.setFundamento(FundamentoType.fromValue(tramite.getFundamentoExencion620()));
					// Traer de BD la descripción del fundamento de exención
					FundamentoExencion620VO fundamentoExencion = servicioFundamentoExencion620.getlistaFundamentoExencion620(tramite.getFundamentoExencion620());
					if (fundamentoExencion == null || StringUtils.isBlank(fundamentoExencion.getDescripcion())) {
						errores.add(NO_SE_HA_PODIDO_RECUPERAR_EL_NOMBRE_DEL_FUNDAMENTO_DE_EXENCION);
					} else {
						liquidacion.setFundamentoDesc(fundamentoExencion.getDescripcion());
					}
				} else {
					liquidacion.setFundamento(FundamentoType.fromValue(tramite.getFundamentoNoSujeccion620()));
					// Traer de BD la descripción del fundamento de no sujección
					FundamentoExencion620VO fundamentoExencion = servicioFundamentoExencion620.getlistaFundamentoNoSujeto620(tramite.getFundamentoNoSujeccion620());
					if (fundamentoExencion == null || StringUtils.isBlank(fundamentoExencion.getDescripcion())) {
						errores.add(NO_SE_HA_PODIDO_RECUPERAR_EL_NOMBRE_DEL_FUNDAMENTO_DE_NO_SUJECCION);
					} else {
						liquidacion.setFundamentoDesc(fundamentoExencion.getDescripcion());
					}
				}
			} else {
				liquidacion.setFundamento(FundamentoType.fromValue(CADENA_VACIA));
				liquidacion.setFundamentoDesc(CADENA_VACIA);
			}

			liquidacion.setPorcentaje(null!=tramite.getPorcentajeAdquisicion620() ? formatoDecimalGata(tramite.getPorcentajeAdquisicion620().toString()) : CADENA_VACIA);
			
			//si no hay reduccion base y compraventa es lo mismo 

			// Cambio Modelo 620
			Calendar fechaInicioEstadoAlerta = new GregorianCalendar();
			Calendar fechaFinEstadoAlerta = new GregorianCalendar();
			Calendar fechaDevengo = new GregorianCalendar();
			Calendar fechaDevengoPrescripcion = new GregorianCalendar();
			Calendar fechaActual = Calendar.getInstance();

			fechaInicioEstadoAlerta.set(2020, 2, 13, 0, 0, 0);
			fechaFinEstadoAlerta.set(2020, 5, 30, 0, 0, 0);
			fechaDevengo.set(Integer.parseInt(tramite.getFechaDevengoItp620().getAnio()), Integer.parseInt(tramite.getFechaDevengoItp620().getMes())-1, Integer.parseInt(tramite.getFechaDevengoItp620().getDia()),0,0,0);
			fechaDevengoPrescripcion.set(Integer.parseInt(tramite.getFechaDevengoItp620().getAnio()), Integer.parseInt(tramite.getFechaDevengoItp620().getMes())-1, Integer.parseInt(tramite.getFechaDevengoItp620().getDia()),0,0,0);
			fechaDevengoPrescripcion.add(Calendar.YEAR, 4);
			fechaDevengoPrescripcion.add(Calendar.DATE, 30);

			if(fechaDevengo.before(fechaInicioEstadoAlerta) && (fechaDevengoPrescripcion.after(fechaInicioEstadoAlerta) && fechaDevengoPrescripcion.before(fechaFinEstadoAlerta)) ||
				(fechaDevengo.after(fechaInicioEstadoAlerta) && fechaDevengo.before(fechaFinEstadoAlerta)) && fechaDevengoPrescripcion.after(fechaFinEstadoAlerta) ||
				fechaDevengoPrescripcion.getTime().toString().equals(fechaInicioEstadoAlerta.getTime().toString()) ||
				fechaDevengoPrescripcion.getTime().toString().equals(fechaFinEstadoAlerta.getTime().toString()) ||
				fechaDevengo.getTime().toString().equals(fechaInicioEstadoAlerta.getTime().toString()) ||
				fechaDevengo.getTime().toString().equals(fechaFinEstadoAlerta.getTime().toString()) ||
				(fechaDevengo.before(fechaInicioEstadoAlerta) && fechaDevengoPrescripcion.after(fechaFinEstadoAlerta))){

				fechaDevengoPrescripcion.add(Calendar.DATE, 109);
			}
			liquidacion.setPrescrita(fechaDevengoPrescripcion.before(fechaActual) ? S : N);
			if (tramite.getReduccionCodigo() != null && tramite.getReduccionCodigo().getTipoReduccion() != null) {
				liquidacion.setReduccionCodigo(ReduccionType.fromValue(tramite.getReduccionCodigo().getTipoReduccion()));
				
				liquidacion.setPrecioCompraventa(null!=tramite.getValorReal() && tramite.getValorReal().compareTo(tramite.getBaseImponible620()) == 1 && tramite.getValorReal().compareTo(BigDecimal.ZERO) != 0 ? formatoDecimalGata(tramite.getValorReal()) : (tramite.getValorDeclarado620() != null ? formatoDecimalGata(tramite.getValorDeclarado620().multiply(tramite.getPorcentajeReduccionAnual620().divide(new BigDecimal(100)))) : CADENA_VACIA));
				liquidacion.setBase(null!=tramite.getValorReal() &&  tramite.getValorReal().compareTo(tramite.getBaseImponible620()) == 1 ? formatoDecimalGata(tramite.getValorReal()) : (null != tramite.getBaseImponible620() ? formatoDecimalGata(tramite.getBaseImponible620()) : CADENA_VACIA));
				
			} else
				liquidacion.setReduccionCodigo(ReduccionType.fromValue(CADENA_VACIA));

			if (null!=tramite.getReduccionCodigo() && tramite.getReduccionCodigo().getPorcentajeReduccion() != null && !tramite.getReduccionCodigo().getPorcentajeReduccion().equals(new BigDecimal("0.00")))
				liquidacion.setReduccionPorcentaje(formatoDecimalGata(tramite.getReduccionCodigo().getPorcentajeReduccion()));
			else
				liquidacion.setReduccionPorcentaje(CADENA_VACIA);

			if (null != tramite.getReduccionImporte() && !tramite.getReduccionImporte().equals(new BigDecimal("0.00"))) {
				liquidacion.setReduccionImporte(formatoDecimalGata(tramite.getReduccionImporte()));
				
				liquidacion.setPrecioCompraventa((null != tramite.getValorReal() && tramite.getValorReal().compareTo(tramite.getBaseImponible620().add(tramite.getReduccionImporte())) >= 0 && tramite.getValorReal().compareTo(BigDecimal.ZERO) != 0) ? formatoDecimalGata(tramite.getValorReal()) : (tramite.getValorDeclarado620() != null ? formatoDecimalGata(tramite.getValorDeclarado620().multiply(tramite.getPorcentajeReduccionAnual620().divide(new BigDecimal(100)))) : CADENA_VACIA));
				liquidacion.setBase((null != tramite.getValorReal() && tramite.getValorReal().compareTo(tramite.getBaseImponible620().add(tramite.getReduccionImporte())) >= 0 && tramite.getValorReal().compareTo(BigDecimal.ZERO) != 0) ? formatoDecimalGata(tramite.getValorReal().subtract(tramite.getReduccionImporte())) : (null != tramite.getBaseImponible620() ? formatoDecimalGata(tramite.getBaseImponible620()) : CADENA_VACIA));
			}else {
				liquidacion.setReduccionImporte(CADENA_VACIA);
			
				String precioSinReduccion = (null != tramite.getValorReal() && tramite.getValorReal().compareTo(tramite.getBaseImponible620()) >= 0 && tramite.getValorReal().compareTo(BigDecimal.ZERO) != 0) ? formatoDecimalGata(tramite.getValorReal()) : (null != tramite.getBaseImponible620() ? formatoDecimalGata(tramite.getBaseImponible620()) : CADENA_VACIA);
				liquidacion.setPrecioCompraventa(precioSinReduccion);
				liquidacion.setBase(precioSinReduccion);
			}

			if(TRUE.equals(tramite.getExento620()) || TRUE.equals(tramite.getNoSujeto620())){
				liquidacion.setTipoGravamen(CADENA_VACIA);
				liquidacion.setCuota(CADENA_VACIA);
			} else {
				liquidacion.setTipoGravamen(null!=tramite.getTipoGravamen620() ? formatoDecimalGata(tramite.getTipoGravamen620().toString()) : CADENA_VACIA);
				
				
				
				BigDecimal valorTrasReduccionAnual = tramite.getValorDeclarado620().multiply(tramite.getPorcentajeReduccionAnual620()).divide(new BigDecimal(100));
				BigDecimal valorRealDeLaCompra = tramite.getValorReal();
				
				
				if (valorTrasReduccionAnual.compareTo(valorRealDeLaCompra) >= 0) {
					BigDecimal cuotaSinReduccion = valorTrasReduccionAnual.multiply(new BigDecimal(liquidacion.getTipoGravamen().replace(",", ".")).divide(new BigDecimal(100)));
					if (liquidacion.getReduccionCodigo() != null  && !liquidacion.getReduccionCodigo().equals(ReduccionType.VALUE_9)) {
						BigDecimal cuotaConReduccion = convertStringToBigDecimal(liquidacion.getBase()).multiply(new BigDecimal(liquidacion.getTipoGravamen().replace(",", ".")).divide(new  BigDecimal(100)));
						liquidacion.setCuota(formatoDecimalGata(cuotaConReduccion.toString()));
					} else {
						liquidacion.setCuota(formatoDecimalGata(cuotaSinReduccion.toString()));
					}
				} else {
					BigDecimal cuotaSinReduccion = valorRealDeLaCompra.multiply(new BigDecimal(liquidacion.getTipoGravamen().replace(",", ".")).divide(new BigDecimal(100)));
					if (liquidacion.getReduccionCodigo() != null && !liquidacion.getReduccionCodigo().equals(ReduccionType.VALUE_9)) {
						BigDecimal cuotaConReduccion = convertStringToBigDecimal(liquidacion.getBase()).multiply(new BigDecimal(liquidacion.getTipoGravamen().replace(",", ".")).divide(new  BigDecimal(100)));
						liquidacion.setCuota(formatoDecimalGata(cuotaConReduccion.toString()));
					} else {
						liquidacion.setCuota(formatoDecimalGata(cuotaSinReduccion.toString()));
					}
				}
				//liquidacion.setCuota(null!=tramite.getCuotaTributaria620() ? formatoDecimalGata(tramite.getCuotaTributaria620().toString()) : CADENA_VACIA);
			}

			//si el valor real de compra venta es menor o 0 se inserta el valor de base imponible sin calcular el tipo de reduccion	
			
			
			//Metemos la liquidación en la declaración
			declaracion.setLiquidacion(liquidacion);

			//Por último, asignamos la declaración a la remesa
			remesa.getDeclaracion().add(declaracion);

			if(errores.isEmpty()){
				getModeloAcciones().cerrarAccion(TipoAccion.GeneracionXML620.getValorEnum(),
						utilesColegiado.getIdUsuarioSessionBigDecimal(),
											tramite.getTramiteTraficoBean().getNumExpediente(),
											"Generado sin errores");
			} else {
				getModeloAcciones().cerrarAccion(TipoAccion.GeneracionXML620.getValorEnum(),
						utilesColegiado.getIdUsuarioSessionBigDecimal(),
						tramite.getTramiteTraficoBean().getNumExpediente(),
						"Generado con errores");
			}
		}//FIN FOR

		/* INICIO MANTIS 0012707: No generamos el XML si no hay declaraciones en la remesa */
		if (!remesa.getDeclaracion().isEmpty()) {
			FicheroBean ficheroBean = null;
			//Creamos el XML
			try {
				ficheroBean = new FicheroBean();
				ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
				ficheroBean.setNombreDocumento(XML_TRANSMISION+idSession);
				ficheroBean.setSobreescribir(false);
				ficheroBean.setFecha(utilesFecha.getFechaActual());
				ficheroBean.setTipoDocumento(ConstantesGestorFicheros.EXPORTAR);
				ficheroBean.setSubTipo(ConstantesGestorFicheros.CTIT);

				gestorDocumentos.crearFicheroXml(ficheroBean, TRAFICO_BEANS_TRANSMISION_JAXB, remesa, null, null);
			} catch (OegamExcepcion e) {
				errores.add(HA_OCURRIDO_UN_ERROR_AL_PARSEAR_EL_XML);
				log.error(e);
			}
			resultado.put(FICHERO, ficheroBean.getFichero().getAbsolutePath());
		}
		/* FIN MANTIS 0012707 */

		if(!errores.isEmpty())
			resultado.put(ERRORES, errores);

		return resultado;
	}

	private static BigDecimal convertStringToBigDecimal(String numberString) {
		// Eliminar los puntos de los miles y reemplazar la coma por un punto decimal
		String cleanedString = numberString.replaceAll("\\.", "").replace(",", ".");

		// Convertir la cadena limpiada a BigDecimal
		return new BigDecimal(cleanedString);
	}
	/**
	 * @author antonio.miguez
	 * @param TramiteTraficoTransmisionBean
	 * @return Map<String,Object>
	 * @throws Throwable 
	 */
	/*
	 * Método para generar el XML para la llamada al CheckCTIT
	 */
	public Map<String,Object> generarXMLCheckCTIT(TramiteTraficoTransmisionBean tramite,String numColegiado) throws Throwable {
		Map<String, Object> resultado = new HashMap<>();

		List<String> errores = new ArrayList<>();

		//Creamos cada uno de los nodos del XML
		//Comenzamos por el Object Factory
		trafico.beans.schemas.generated.checkctit.ObjectFactory objFactory = new trafico.beans.schemas.generated.checkctit.ObjectFactory();

		//NODO PRINCIPAL: SolicitudTramite
		SolicitudTramite solTramite = objFactory.createSolicitudTramite();

		//NODO SECUNDARIO: DatosFirmados
		trafico.beans.schemas.generated.checkctit.DatosFirmados datosFirmados = objFactory.createDatosFirmados();
		ModeloColegiado modeloColegiado = null;

		modeloColegiado = new ModeloColegiado();

		// Evitamos el acceso a sesión
		Persona presentador = modeloColegiado.obtenerColegiadoCompleto(tramite.getTramiteTraficoBean().getIdContrato());

		datosFirmados.setNIFGestor(presentador.getNif());
		datosFirmados.setMatricula(tramite.getTramiteTraficoBean().getVehiculo().getMatricula());

		//Lista transmitentes
		ListaTransmitentes listaTransmitentes = objFactory.createDatosFirmadosListaTransmitentes();
		//Transmitente
		listaTransmitentes.getDOI().add(tramite.getTransmitenteBean().getPersona().getNif());
		//Cotitulares transmitentes
		if (tramite.getPrimerCotitularTransmitenteBean()!=null && tramite.getPrimerCotitularTransmitenteBean().getPersona() != null
				&& tramite.getPrimerCotitularTransmitenteBean().getPersona().getNif()!=null && !CADENA_VACIA.equals(tramite.getPrimerCotitularTransmitenteBean().getPersona().getNif())){
			listaTransmitentes.getDOI().add(tramite.getPrimerCotitularTransmitenteBean().getPersona().getNif());
		}
		if (tramite.getSegundoCotitularTransmitenteBean()!=null && tramite.getSegundoCotitularTransmitenteBean().getPersona() != null
				&& tramite.getSegundoCotitularTransmitenteBean().getPersona().getNif()!=null && !CADENA_VACIA.equals(tramite.getSegundoCotitularTransmitenteBean().getPersona().getNif())){
			listaTransmitentes.getDOI().add(tramite.getSegundoCotitularTransmitenteBean().getPersona().getNif());
		}
		//Seteamos
		datosFirmados.setListaTransmitentes(listaTransmitentes);

		//Lista adquirientes
		ListaAdquirientes listaAdquirientes = objFactory.createDatosFirmadosListaAdquirientes();
		//Adquiriente

		// Si se trata de una entrega a compraventa, el adquiriente será el poseedor.
		if (!tramite.getTipoTransferencia().getValorEnum().equals(TipoTransferencia.tipo5.getValorEnum())) { // Antes era tipo 3
			listaAdquirientes.getDOI().add(tramite.getAdquirienteBean().getPersona().getNif());
		} else {
			listaAdquirientes.getDOI().add(tramite.getPoseedorBean().getPersona().getNif());
		}

		//Seteamos
		datosFirmados.setListaAdquirientes(listaAdquirientes);

		if(null != tramite.getTipoTransferencia()){
			//Antes era tipo1
			if(tramite.getTipoTransferencia().getValorEnum().equals(TipoTransferencia.tipo1.getValorEnum()) ||
					tramite.getTipoTransferencia().getValorEnum().equals(TipoTransferencia.tipo2.getValorEnum())
					|| tramite.getTipoTransferencia().getValorEnum().equals(TipoTransferencia.tipo3.getValorEnum())) {
				datosFirmados.setTipoTramite(TipoTramite.CTI);
			} else if(tramite.getTipoTransferencia().getValorEnum().equals(TipoTransferencia.tipo4.getValorEnum())) { //Antes era tipo2
				datosFirmados.setTipoTramite(TipoTramite.NOT);
			} else if(tramite.getTipoTransferencia().getValorEnum().equals(TipoTransferencia.tipo5.getValorEnum())) { // Antes era tipo3
				datosFirmados.setTipoTramite(TipoTramite.ENT);
			} else {
				errores.add(EL_TIPO_DE_TRANSFERENCIA_NO_ES_CORRECTO);
			}
		}else{
			errores.add(EL_TIPO_DE_TRANSFERENCIA_NO_ES_CORRECTO);
		}

		if (tramite.getTramiteTraficoBean().getVehiculo().getServicioTraficoBean() != null
				&& tramite.getTramiteTraficoBean().getVehiculo().getServicioTraficoBean().getIdServicio() != null){
			datosFirmados.setServicioDelVehiculo(TipoServicioDelVehiculo.fromValue(tramite.getTramiteTraficoBean().getVehiculo().getServicioTraficoBean().getIdServicio()));
		}
		datosFirmados.setServicioDelVehiculo(null);
		// QUITAMOS SERVICIO A MANO
		datosFirmados.setTextoLegal(CADENA_VACIA);

		//Seteamos los DATOS FIRMADOS
		solTramite.setDatosFirmados(datosFirmados);

		//Seteamos la FIRMA
		solTramite.setFirmaGestor(new byte[0]);

		//Seteamos la VERSIÓN
		solTramite.setVersion(_1_0);

		ResultBean resultadoFirmasBean = new ResultBean();

		try{
			resultadoFirmasBean = anhadirFirmasCheckCTITHSM(solTramite, numColegiado);
		} catch (Exception e) {
			resultadoFirmasBean.setError(true);
			log.error(e);
		}

		//Se crea un fichero xml de envío

		/**Cambio Gestor de Documentos**/

		if (!resultadoFirmasBean.getError()) {
			BigDecimal numExpediente = tramite.getTramiteTraficoBean().getNumExpediente();
			XmlCheckCTITFactory xmlFactory = new XmlCheckCTITFactory();
			String xmlFirmado = xmlFactory.toXML(resultadoFirmasBean.getAttachment(SOLICITUD_TRAMITE));
			FicheroBean ficheroBean = new FicheroBean();
			ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
			ficheroBean.setTipoDocumento(ConstantesGestorFicheros.CHECKCTIT);
			ficheroBean.setSubTipo(ConstantesGestorFicheros.CTITENVIO);
			ficheroBean.setNombreDocumento(ConstantesGestorFicheros.NOMBRE_CHECKCTIT + String.valueOf(numExpediente));
			ficheroBean.setFecha(Utilidades.transformExpedienteFecha(numExpediente));
			ficheroBean.setSobreescribir(true);

			gestorDocumentos.crearFicheroXml(ficheroBean, XmlCheckCTITFactory.NAME_CONTEXT, resultadoFirmasBean.getAttachment(SOLICITUD_TRAMITE), xmlFirmado, null);

			String nombreFich = ficheroBean.getFichero().getPath();

			log.info("incluimos " + nombreFich + " al hashmap de resultado de generar xml para el tramite: " + tramite.getTramiteTraficoBean().getNumExpediente());
			resultado.put(FICHERO, nombreFich);
			resultado.put(SOLICITUD_TRAMITE, resultadoFirmasBean.getAttachment(SOLICITUD_TRAMITE));
		}
		if (!errores.isEmpty() || resultadoFirmasBean.getError()) {
			errores.add(resultadoFirmasBean.getMensaje());
			resultado.put(ERRORES, errores);
		}
		return resultado;
	}

	public Map<String,Object> generarXMLTransmisionNoTelematica(TramiteTraficoTransmisionBean tramite){
		Map<String,Object> resultado = new HashMap<>();
		ResultBean resultBeanExportar = new ResultBean();
		XStream xstream = new XStream();
		String xml = xstream.toXML(tramite);

		byte[] xmlByte;
		try {
			xmlByte = firmarXml(xml, tramite.getTramiteTraficoBean().getNumColegiado());
			FicheroBean ficheroBean = new FicheroBean();
			ficheroBean.setFicheroByte(xmlByte);
			ficheroBean.setTipoDocumento(ConstantesGestorFicheros.NO_TELEMATICO);
			ficheroBean.setSubTipo(ConstantesGestorFicheros.CTIT);
			ficheroBean.setNombreDocumento(tramite.getTramiteTraficoBean().getNumExpediente().toString());
			ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
			ficheroBean.setFecha(Utilidades.transformExpedienteFecha(tramite.getTramiteTraficoBean().getNumExpediente()));
			ficheroBean.setSobreescribir(true);
			gestorDocumentos.guardarFichero(ficheroBean);
		}catch (OegamExcepcion e) {
			log.error("Error al guardar el fichero de custodia para una Transmision",e, tramite.getTramiteTraficoBean().getNumExpediente().toString());
			resultBeanExportar.setMensaje("Error al guardar el fichero de custodia para una Transmisión");
			resultBeanExportar.setError(true);
		} catch (InternalException e) {
			log.error("Error al guardar el fichero de custodia para una baja.",e, tramite.getTramiteTraficoBean().getNumExpediente().toString());
			resultBeanExportar.setMensaje("Error al guardar el fichero de custodia para una Transmisión");
			resultBeanExportar.setError(true);
		}
		return resultado;
	}

	private byte[] firmarXml(String xml, String numColegiado) throws InternalException {
		byte[] Afirmar = new String (xml.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8).getBytes(StandardCharsets.UTF_8);

		String aliasColegiado= utilesColegiado.getAlias(numColegiado);
		UtilesViafirma utilesViafirma = new UtilesViafirma();
		return utilesViafirma.firmarXMLTrafico(Afirmar, aliasColegiado);
	}

	/**
	 * @author antonio.miguez
	 * @param TramiteTraficoTransmisionBean, HttpServletRequest, HttpServletResponse
	 * @return Map<String,Object>
	 * @throws Throwable 
	 */
	/*
	 * Método para generar el XML para la llamada al FullCTIT, TradeCTIT o NotificationCTIT
	 */
	public Map<String,Object> generarXMLTransmisionTelematica(TramiteTraficoTransmisionBean tramite, Paso paso, String numColegiado) throws Throwable {
		Map<String, Object> resultado = new HashMap<>();

		List<String> errores = new ArrayList<>();
		Direccion dirVehiculo;

		SimpleDateFormat formatoFecha = new SimpleDateFormat(FORMATO_FECHA);

		//Creamos cada uno de los nodos del XML
		//Comenzamos por el Object Factory
		trafico.beans.schemas.generated.transTelematica.ObjectFactory objFactory = new trafico.beans.schemas.generated.transTelematica.ObjectFactory();

		//NODO PRINCIPAL: Solicitud entrada
		SolicitudRegistroEntrada solRegEntrada = objFactory.createSolicitudRegistroEntrada();

		//NODO SECUNDARIO: Datos firmados
		DatosFirmados datosFirmados = objFactory.createDatosFirmados();

		//NODO TERCIARIO: Datos genéricos
		DatosGenericos datosGenericos = objFactory.createDatosGenericos();

		//Organismo
		datosGenericos.setOrganismo(TipoOrganismo.DGT);

		//Datos remitente
		DatosRemitente datosRemitente = objFactory.createDatosRemitente();

		BeanPQDatosColegio beanPQDatosColegio = new BeanPQDatosColegio();
		beanPQDatosColegio.setP_COLEGIO(utilesColegiado.getColegioDelContrato());
		ColegioBean colegioBean= (ColegioBean) getModeloTrafico().obtenerDatosColegio(beanPQDatosColegio).get(ConstantesPQ.BEANPANTALLA);

		datosRemitente.setNombre(colegioBean.getNombre());
		datosRemitente.setApellidos(CADENA_VACIA);
		trafico.beans.schemas.generated.transTelematica.DatosRemitente.DocumentoIdentificacion docRemitente = objFactory.createDatosRemitenteDocumentoIdentificacion();
		docRemitente.setNumero(colegioBean.getCif());
		datosRemitente.setDocumentoIdentificacion(docRemitente);
		datosRemitente.setCorreoElectronico(colegioBean.getCorreoElectronico());

		//Seteamos
		datosGenericos.setRemitente(datosRemitente);

		//Interesados
		Interesados interesados = objFactory.createDatosGenericosInteresados();
		ModeloColegiado modeloColegiado = null;
		modeloColegiado = new ModeloColegiado();

		Persona presentador = modeloColegiado.obtenerColegiadoCompleto(tramite.getTramiteTraficoBean().getIdContrato() != null
				? tramite.getTramiteTraficoBean().getIdContrato()
						: utilesColegiado.getIdContratoSessionBigDecimal());
		//Datos interesado
		DatosInteresado datosInteresado = objFactory.createDatosInteresado();
		datosInteresado.setNombre(presentador.getNombre());
		datosInteresado.setApellidos(presentador.getApellido1RazonSocial()+" "+presentador.getApellido2());
		trafico.beans.schemas.generated.transTelematica.DatosInteresado.DocumentoIdentificacion docInteresado = objFactory.createDatosInteresadoDocumentoIdentificacion();
		docInteresado.setNumero(presentador.getNif());
		datosInteresado.setDocumentoIdentificacion(docInteresado);
		datosInteresado.setCorreoElectronico(presentador.getCorreoElectronico());

		//Seteamos el interesado
		interesados.setInteresado(datosInteresado);

		//Seteamos
		datosGenericos.setInteresados(interesados);

		//Destino
		Destino destino = objFactory.createDestino();
		destino.setDescripcion(DGT_VEHICULOS);

		//Asunto
		Asunto asunto = objFactory.createAsunto();

		String nuevasEspecificaciones = gestorPropiedades.valorPropertie("nuevas.espcificaciones.ctit.5");
		if (StringUtils.isNotBlank(nuevasEspecificaciones) && SI.equals(nuevasEspecificaciones)) {
			if (tramite.getTipoTransferencia() != null && TipoTransferencia.tipo4.getValorEnum().equals(tramite.getTipoTransferencia().getValorEnum())) {
				asunto.setCodigo(NOTGE);
			} else if (tramite.getTipoTransferencia() != null && TipoTransferencia.tipo5.getValorEnum().equals(tramite.getTipoTransferencia().getValorEnum())) {
				asunto.setCodigo(ENTGE);
			} else {
				asunto.setCodigo(CTIGE);
			}
			destino.setCodigo(_E00130201);
		} else {
			asunto.setCodigo(CTVG);
			destino.setCodigo(_101001);
		}

		asunto.setDescripcion(SOLICITUD_DE_CAMBIO_DE_TITULARIDAD_DE_VEHICULOS_ONLINE_PARA_GESTORES);
		//Seteamos
		datosGenericos.setAsunto(asunto);

		//Seteamos
		datosGenericos.setDestino(destino);

		//Seteamos los DATOS GENÉRICOS
		datosFirmados.setDatosGenericos(datosGenericos);

		//NODO TERCIARIO: DATOS ESPECÍFICOS
		DatosEspecificos datosEspecificos = objFactory.createDatosEspecificos();

		//Datos Colegio
		DatosColegio datosColegio = objFactory.createDatosEspecificosDatosColegio();
		datosColegio.setId(colegioBean.getColegio());
		//Seteamos
		datosEspecificos.setDatosColegio(datosColegio);

		//Datos Gestoria
		DatosGestoria datosGestoria = objFactory.createDatosEspecificosDatosGestoria();
		datosGestoria.setId(tramite.getTramiteTraficoBean().getNumColegiado());

		//Seteamos
		datosEspecificos.setDatosGestoria(datosGestoria);

		//Datos Gestor
		DatosGestor datosGestor = objFactory.createDatosEspecificosDatosGestor();
		datosGestor.setFiliacion(presentador.getTipoPersona().getValorEnum());
		datosGestor.setDOI(presentador.getNif());

		//Seteamos
		datosEspecificos.setDatosGestor(datosGestor);

		// Nº Expediente Gestor
		datosEspecificos.setNumeroExpedienteGestor(tramite.getTramiteTraficoBean().getNumExpediente().toString());

		// Datos Expediente
		DatosExpediente datosExpediente = objFactory.createDatosEspecificosDatosExpediente();

		// Cambiado por Mantis 1551
		// DRC@01-10-2012 Incidencia: 2493
		if (tramite != null && tramite.getTipoTransferencia() != null && tramite.getTipoTransferencia().getValorEnum() != null) 
			datosExpediente.setTipoTramite(MapeoTipoTransferencia.valorEnumTipoTransferencia(tramite.getTipoTransferencia().getValorEnum()));
		else
			datosExpediente.setTipoTramite(MapeoTipoTransferencia.valorEnumTipoTransferencia(CADENA_VACIA));

		if (tramite != null && tramite.getModoAdjudicacion() != null)
			datosExpediente.setMotivoTransmision(tramite.getModoAdjudicacion().getValorEnum());
		else
			datosExpediente.setMotivoTransmision(CADENA_VACIA);

		datosExpediente.setFechaTramite(formatoFecha.format(new Date()));

		JefaturaTraficoVO jefaturaTrafico = servicioJefaturaTrafico.getJefaturaTrafico(tramite.getTramiteTraficoBean().getJefaturaTrafico().getJefaturaProvincial());

		datosExpediente.setJefatura(jefaturaTrafico.getJefatura());
		String sucursal = jefaturaTrafico.getSucursal();
		datosExpediente.setSucursal(sucursal != null ? sucursal : CADENA_VACIA);

		if (tramite.getTipoTransferencia() != null && TipoTransferencia.tipo5.getValorEnum().equals(tramite.getTipoTransferencia().getValorEnum())) {
			datosExpediente.setImpresionPermisoCirculacion(TipoSINO.NO);
		} else {
			datosExpediente.setImpresionPermisoCirculacion(TRUE.equals(tramite.getImpresionPermiso()) ? TipoSINO.SI : TipoSINO.NO);
		}

		// Seteamos
		datosEspecificos.setDatosExpediente(datosExpediente);

		// Tasas
		Tasas tasas = objFactory.createDatosEspecificosTasas();

		if (tramite.getTramiteTraficoBean().getTasa() != null &&
				tramite.getTramiteTraficoBean().getTasa().getCodigoTasa() != null) {
			String tasa = utiles.rellenarCeros(tramite.getTramiteTraficoBean().getTasa().getCodigoTasa(), 12);
			tasas.getTasaTramiteOrTasaInforme().add(objFactory.createDatosEspecificosTasasTasaTramite(tasa));
		}

		if (tramite.getCodigoTasaInforme() != null && !tramite.getCodigoTasaInforme().isEmpty()
				 && !"-1".equals(tramite.getCodigoTasaInforme())) {
			String tasa = utiles.rellenarCeros(tramite.getCodigoTasaInforme(), 12);
			tasas.getTasaTramiteOrTasaInforme().add(objFactory.createDatosEspecificosTasasTasaInforme(tasa));
		}

		if (tramite.getCodigoTasaCambioServicio() != null && !"-1".equals(tramite.getCodigoTasaCambioServicio())) {
			String tasa = utiles.rellenarCeros(tramite.getCodigoTasaCambioServicio(), 12);
			tasas.setTasaCambioServicio(tasa);
		}

		//Seteamos
		datosEspecificos.setTasas(tasas);

		// Datos Vehículo
		// Obtenemos el bean vehículo
		VehiculoBean vehiculo = tramite.getTramiteTraficoBean().getVehiculo();
		DatosVehiculo datosVehiculo = objFactory.createDatosEspecificosDatosVehiculo();

		// Datos Matriculación
		DatosMatriculacion datosMatriculacion = objFactory.createDatosEspecificosDatosVehiculoDatosMatriculacion();
		datosMatriculacion.setMatricula(vehiculo.getMatricula());
		try{
			// DRC@01-10-2012 Incidencia: 2493
			if (vehiculo.getFechaMatriculacion() != null && vehiculo.getFechaMatriculacion().getTimestamp() != null)
				datosMatriculacion.setFechaMatriculacion(formatoFecha.format(vehiculo.getFechaMatriculacion().getTimestamp()));
			else
				datosMatriculacion.setFechaMatriculacion(CADENA_VACIA);
		}catch(ParseException e){
			errores.add(ERROR_AL_PARSEAR_LA_FECHA_DE_MATRICULACION_DEL_VEHICULO);
		}

		// Seteamos los datos de matriculación
		datosVehiculo.setDatosMatriculacion(datosMatriculacion);

		// Datos Servicio
		DatosServicio datosServicio = objFactory.createDatosEspecificosDatosVehiculoDatosServicio();
		datosServicio.setCambioServicio(TRUE.equals(tramite.getCambioServicio())?TipoSINO.SI:TipoSINO.NO);
		if(TRUE.equals(tramite.getCambioServicio()) && !CADENA_VACIA.equals(vehiculo.getServicioTraficoBean().getIdServicio()) && !"-1".equals(vehiculo.getServicioTraficoBean().getIdServicio())){
			datosServicio.setServicio(vehiculo.getServicioTraficoBean().getIdServicio());
		}

		// Seteamos los datos de servicio
		datosVehiculo.setDatosServicio(datosServicio);
		datosVehiculo.setRenting(TRUE.equals(tramite.getTramiteTraficoBean().getRenting()) ? TipoSINO.SI : TipoSINO.NO);
		datosVehiculo.setTipoVehiculo(vehiculo.getTipoVehiculoBean() != null ? vehiculo.getTipoVehiculoBean().getTipoVehiculo() : CADENA_VACIA);

		//Datos ITV
		if (!TRUE.equals(tramite.getTramiteTraficoBean().getVehiculo().getConsentimiento())) {
			DatosITV datosITV = objFactory.createDatosEspecificosDatosVehiculoDatosITV();
			datosITV.setResultadoITV(TipoResultadoITV.F);
			try {
				// DRC@01-10-2012 Incidencia: 2493
				if (vehiculo.getFechaInspeccion() != null && vehiculo.getFechaInspeccion().getTimestamp() != null)
					datosITV.setFechaRealizacion(formatoFecha.format(vehiculo.getFechaInspeccion().getTimestamp()));
				else
					datosITV.setFechaRealizacion(CADENA_VACIA);
			} catch (ParseException e) {
				errores.add(ERROR_AL_PARSEAR_LA_FECHA_DE_INSPECCION_DEL_VEHICULO);
			}
			try {
				// DRC@01-10-2012 Incidencia: 2493
				if (vehiculo.getFechaItv() != null && vehiculo.getFechaItv().getTimestamp() != null)
					datosITV.setFechaCaducidad(formatoFecha.format(vehiculo.getFechaItv().getTimestamp()));
				else
					datosITV.setFechaCaducidad(CADENA_VACIA);
			} catch (ParseException e) {
				errores.add(ERROR_AL_PARSEAR_LA_FECHA_DE_CADUCIDAD_DE_LA_ITV_DEL_VEHICULO);
			}

			String estacionITV = vehiculo.getEstacionItv();

			if (estacionITV != null && !CADENA_VACIA.equals(estacionITV)) {
				String estacion = estacionITV.substring(estacionITV.length()-2, estacionITV.length());
				String provEstacion = estacionITV.substring(0, estacionITV.length()-2);
				datosITV.setEstacion(_0+estacion);
				datosITV.setProvincia(provEstacion);
			}
			datosITV.setMotivo(vehiculo.getMotivoItv() != null && vehiculo.getMotivoItv().getIdMotivo() != null ? vehiculo.getMotivoItv().getIdMotivo() : CADENA_VACIA);
			//Seteamos los datos de ITV
			datosVehiculo.setDatosITV(datosITV);
			datosVehiculo.setDatosITV(null);
		}

		if (tramite.getTramiteTraficoBean().getVehiculo() != null && tramite.getTramiteTraficoBean().getVehiculo().getVehiculoTramiteTraficoBean() != null && tramite.getTramiteTraficoBean().getVehiculo().getVehiculoTramiteTraficoBean().getKilometros() != null) {
			DatosKilometraje datosKilometraje = objFactory.createDatosKilometraje();
			datosKilometraje.setKilometraje(tramite.getTramiteTraficoBean().getVehiculo().getVehiculoTramiteTraficoBean().getKilometros().toString());
			if (tramite.getTramiteTraficoBean().getVehiculo().getFechaLecturaKm() != null) {
				datosKilometraje.setFechaLectura(formatoFecha.format(tramite.getTramiteTraficoBean().getVehiculo().getFechaLecturaKm().getTimestamp()));
			}
			datosKilometraje.setDOIResponsable(tramite.getTramiteTraficoBean().getVehiculo().getDoiResponsableKm());
			datosVehiculo.setDatosKilometraje(datosKilometraje);
		}

		//Domicilio del vehículo
		// incidencia 3038: Localización del vehículo - Transmisión CTIT y BAJAS (BLOQUEAR) atc
		// Si está chekeado la aceptación del cambio de localización se incluye la dirección en caso de que exista

		if(null==tramite.getTipoTransferencia() ||
				!tramite.getTipoTransferencia().getValorEnum().equals(TipoTransferencia.tipo5.getValorEnum()) ){ // Antes era tipo 3
			dirVehiculo = tramite.getAdquirienteBean().getPersona().getDireccion();
		} else {
			dirVehiculo = tramite.getPoseedorBean().getPersona().getDireccion();
		}

		// La dirección del vehículo ya ha sido validada y no hay que comprobar si está completa.
		if(TRUE.equals(tramite.getConsentimientoCambio())){
			dirVehiculo = vehiculo.getDireccionBean();
		}

//		JRG. 18-02-2013 En la última actualización del WS de fullCtit se pide que en caso de que no haya via se inserte "SN".
//		Antes se comprobaba si tenía número de vía. Con eso se daba por hecho que se había rellenado la dirección. Ahora va a comprobar el nombre de vía
		if (dirVehiculo != null && dirVehiculo.getNombreVia() != null && !CADENA_VACIA.equals(dirVehiculo.getNombreVia())) {
			Domicilio domicilioVehiculo = objFactory.createDomicilio();
			domicilioVehiculo.setMunicipio(dirVehiculo.getMunicipio().getProvincia().getIdProvincia()+dirVehiculo.getMunicipio().getIdMunicipio());
			domicilioVehiculo.setLocalidad(dirVehiculo.getPueblo()!=null?dirVehiculo.getPueblo():CADENA_VACIA);
			domicilioVehiculo.setProvincia(dirVehiculo.getMunicipio().getProvincia().getIdProvincia());
			domicilioVehiculo.setCodigoPostal(dirVehiculo.getCodPostal());
			domicilioVehiculo.setTipoVia(dirVehiculo.getTipoVia().getIdTipoVia());
			domicilioVehiculo.setNombreVia(utilesConversiones.ajustarCamposIne(dirVehiculo.getNombreVia()));

			// JRG. 18-02-2013 En la última actualización del WS de fullCtit se pide que en caso de que no haya número de vía se inserte "SN".
			if (!CADENA_VACIA.equals(dirVehiculo.getNombreVia())){
				// Si se ha metido dirección tiene que tener número de vía o SN.
				if (null!=dirVehiculo.getNumero() && !dirVehiculo.getNumero().equals(CADENA_VACIA)) {
					domicilioVehiculo.setNumero(dirVehiculo.getNumero());
				} else {
					domicilioVehiculo.setNumero(SN);}
			} else {
				//Si no tiene dirección le mete cadena vacía.
				domicilioVehiculo.setNumero(CADENA_VACIA);
			}

			domicilioVehiculo.setKilometro(dirVehiculo.getPuntoKilometrico()!=null?dirVehiculo.getPuntoKilometrico():CADENA_VACIA);
			domicilioVehiculo.setHectometro(dirVehiculo.getHm()!=null?dirVehiculo.getHm():CADENA_VACIA);
			domicilioVehiculo.setBloque(dirVehiculo.getBloque()!=null?dirVehiculo.getBloque():CADENA_VACIA);
			domicilioVehiculo.setPortal(dirVehiculo.getPortal()!=null && dirVehiculo.getPortal().length() <= 2 ?dirVehiculo.getPortal():CADENA_VACIA);
			domicilioVehiculo.setEscalera(dirVehiculo.getEscalera()!=null?dirVehiculo.getEscalera():CADENA_VACIA);
			domicilioVehiculo.setPlanta(dirVehiculo.getPlanta()!=null?dirVehiculo.getPlanta():CADENA_VACIA);
			domicilioVehiculo.setPuerta(dirVehiculo.getPuerta()!=null?dirVehiculo.getPuerta():CADENA_VACIA);
			domicilioVehiculo.setPais(ESP);
			//Seteamos el domicilio del vehículo
			datosVehiculo.setDomicilio(domicilioVehiculo);

			//Seteamos
			datosEspecificos.setDatosVehiculo(datosVehiculo);
			// Fin 3038 
		}

		//Datos Tutela
		if((tramite.getRepresentanteAdquirienteBean()!=null && tramite.getRepresentanteAdquirienteBean().getPersona()!=null
				&& null!=tramite.getRepresentanteAdquirienteBean().getPersona().getNif() && !CADENA_VACIA.equals(tramite.getRepresentanteAdquirienteBean().getPersona().getNif())
				&& null!=tramite.getRepresentanteAdquirienteBean().getIdMotivoTutela()) ||
				(tramite.getRepresentanteTransmitenteBean()!=null && tramite.getRepresentanteTransmitenteBean().getPersona()!=null
				&& tramite.getRepresentanteTransmitenteBean().getPersona().getNif()!=null && !CADENA_VACIA.equals(tramite.getRepresentanteTransmitenteBean().getPersona().getNif())
				&& null!=tramite.getRepresentanteTransmitenteBean().getIdMotivoTutela())){

			DatosTutela datosTutela = objFactory.createDatosEspecificosDatosTutela();
			//Tutela Transmitente
			if(tramite.getRepresentanteTransmitenteBean()!=null
					&& tramite.getRepresentanteTransmitenteBean().getPersona()!=null
					&& tramite.getRepresentanteTransmitenteBean().getPersona().getNif()!=null
					&& !CADENA_VACIA.equals(tramite.getRepresentanteTransmitenteBean().getPersona().getNif())
					&& null!=tramite.getRepresentanteTransmitenteBean().getIdMotivoTutela()) {
				TutelaTransmiente tutelaTransmitente = objFactory.createDatosEspecificosDatosTutelaTutelaTransmiente();
				tutelaTransmitente.setDOITutor(tramite.getRepresentanteTransmitenteBean().getPersona().getNif());
				//Seteamos la tutela del transmitente
				datosTutela.setTutelaTransmiente(tutelaTransmitente);
			}
			//Tutela Adquiriente
			if(tramite.getRepresentanteAdquirienteBean()!=null
					&& tramite.getRepresentanteAdquirienteBean().getPersona()!=null
					&& tramite.getRepresentanteAdquirienteBean().getPersona().getNif() !=null
					&& !CADENA_VACIA.equals(tramite.getRepresentanteAdquirienteBean().getPersona().getNif())
					&& null!=tramite.getRepresentanteAdquirienteBean().getIdMotivoTutela()
					&& !tramite.getTipoTransferencia().equals(TipoTransferencia.tipo5)){//TIPO TRANSFERENCIA TRADE NO LLEVA ADQUIRIENTE NI SU REPRESENTANTE. (antes tipo3)
				TutelaAdquirente tutelaAdquiriente = objFactory.createDatosEspecificosDatosTutelaTutelaAdquirente();
				tutelaAdquiriente.setTipoTutela(null!=tramite.getRepresentanteAdquirienteBean().getIdMotivoTutela() ? tramite.getRepresentanteAdquirienteBean().getIdMotivoTutela().getValorEnum():CADENA_VACIA);
				tutelaAdquiriente.setDOITutor(tramite.getRepresentanteAdquirienteBean().getPersona().getNif());
				//Seteamos la tutela del adquiriente
				datosTutela.setTutelaAdquirente(tutelaAdquiriente);
			}
			//Seteamos
			datosEspecificos.setDatosTutela(datosTutela);
		}

		//Titular Transmitente
		DatosTransmitente datosTransmitente = objFactory.createDatosTransmitente();
		DatosTransmitenteConAutonomo datosTransmitenteConAutonomo = objFactory.createDatosTransmitenteConAutonomo();
		Persona transmitente = tramite.getTransmitenteBean().getPersona();

		//Datos personales
		DatosPersonalesTransmitente datosPersonalesTransmitente = objFactory.createDatosPersonalesTransmitente();

		//Datos Filiación
		DatosPersonalesTransmitente.DatosFiliacion datosFiliacionTransmitente = objFactory.createDatosPersonalesTransmitenteDatosFiliacion();
		if(TipoPersona.Fisica.getValorEnum().equals(transmitente.getTipoPersona().getValorEnum())){
			//Persona Física
			DatosPersonalesTransmitente.DatosFiliacion.PersonaFisica personaFisica = objFactory.createDatosPersonalesTransmitenteDatosFiliacionPersonaFisica();
			personaFisica.setNombre(transmitente.getNombre());
			personaFisica.setPrimerApellido(transmitente.getApellido1RazonSocial());
			personaFisica.setSegundoApellido(!CADENA_VACIA.equals(transmitente.getApellido2()) ? transmitente.getApellido2() : CADENA_VACIA);
			try{
				// DRC@01-10-2012 Incidencia: 2493
				if (transmitente.getFechaNacimientoBean()!=null && transmitente.getFechaNacimientoBean().getTimestamp() != null)
					personaFisica.setFechaNacimiento(formatoFecha.format(transmitente.getFechaNacimientoBean().getTimestamp()));
				else
					personaFisica.setFechaNacimiento(null);
			}catch(ParseException e){
				errores.add(ERROR_AL_PARSEAR_LA_FECHA_DE_NACIMIENTO_DEL_TRANSMITENTE);
			}
			//Seteamos la persona física
			datosFiliacionTransmitente.setPersonaFisica(personaFisica);
		} else {
			//Persona Jurídica
			DatosPersonalesTransmitente.DatosFiliacion.PersonaJuridica personaJuridica = objFactory.createDatosPersonalesTransmitenteDatosFiliacionPersonaJuridica();
			personaJuridica.setRazonSocial(transmitente.getApellido1RazonSocial());
			//Seteamos la persona jurídica
			datosFiliacionTransmitente.setPersonaJuridica(personaJuridica);
		}
		//Seteamos la persona física o jurídica
		datosPersonalesTransmitente.setDatosFiliacion(datosFiliacionTransmitente);
		datosPersonalesTransmitente.setSexo("H".equals(transmitente.getSexo())?"M":transmitente.getSexo());
		datosPersonalesTransmitente.setDOI(transmitente.getNif());
		//Seteamos los datos del transmitente
		datosTransmitente.setDatosTransmitente(datosPersonalesTransmitente);
		datosTransmitenteConAutonomo.setDatosTransmitente(datosPersonalesTransmitente);
		if(tramite != null && tramite.getRepresentanteTransmitenteBean() != null
				&& tramite.getRepresentanteTransmitenteBean().getPersona() != null
				&& tramite.getRepresentanteTransmitenteBean().getPersona().getNif() != null
				&& !CADENA_VACIA.equals(tramite.getRepresentanteTransmitenteBean().getPersona().getNif())
			//Datos representante
				&& tramite.getRepresentanteTransmitenteBean().getConceptoRepre()!= null
				&& !tramite.getRepresentanteTransmitenteBean().getConceptoRepre().equals(ConceptoTutela.Tutela)) { // Si es tutela no es representante.
			DatosRepresentante datosRepresentanteTransmitente = objFactory.createDatosRepresentante();
			datosRepresentanteTransmitente.setDOI(tramite.getRepresentanteTransmitenteBean().getPersona().getNif());

			// Seteamos los datos del representante del transmitente
			datosTransmitente.setDatosRepresentante(datosRepresentanteTransmitente);
			datosTransmitenteConAutonomo.setDatosRepresentante(datosRepresentanteTransmitente);
		}

		//Inserta valor para el campo Autonomo_Transmitente SI o NO
		if (tramite.getTransmitenteBean()!=null && TRUE.equalsIgnoreCase(tramite.getTransmitenteBean().getAutonomo())){
			datosTransmitenteConAutonomo.setAutonomoTransmitente(TipoSINO.SI);
		}

		//Seteamos
		datosEspecificos.setTitularTransmitente(datosTransmitenteConAutonomo);

		//Cotitulares Transmitentes
		if((tramite.getPrimerCotitularTransmitenteBean()!=null && tramite.getPrimerCotitularTransmitenteBean().getPersona()!=null
				&& tramite.getPrimerCotitularTransmitenteBean().getPersona().getNif()!=null
				&& !CADENA_VACIA.equals(tramite.getPrimerCotitularTransmitenteBean().getPersona().getNif())) ||
				(tramite.getSegundoCotitularTransmitenteBean()!=null && tramite.getSegundoCotitularTransmitenteBean().getPersona()!=null &&
						tramite.getSegundoCotitularTransmitenteBean().getPersona().getNif()!=null && !CADENA_VACIA.equals(tramite.getSegundoCotitularTransmitenteBean().getPersona().getNif()))){
			CotitularesTransmitentes cotitularesTransmitentes = objFactory.createDatosEspecificosCotitularesTransmitentes();
			//Si hay primer cotitular...
			if(tramite.getPrimerCotitularTransmitenteBean()!=null && tramite.getPrimerCotitularTransmitenteBean().getPersona()!=null &&
					tramite.getPrimerCotitularTransmitenteBean().getPersona().getNif()!=null && !CADENA_VACIA.equals(tramite.getPrimerCotitularTransmitenteBean().getPersona().getNif())){
				DatosTransmitente datosPrimerCotitularTransmitente = objFactory.createDatosTransmitente();
				Persona primerCotitular = tramite.getPrimerCotitularTransmitenteBean().getPersona();
				// Datos representante:
				if(tramite.getRepresentantePrimerCotitularTransmitenteBean() != null
						&& tramite.getRepresentantePrimerCotitularTransmitenteBean().getPersona() != null
						&& tramite.getRepresentantePrimerCotitularTransmitenteBean().getPersona().getNif() != null
						&& !tramite.getRepresentantePrimerCotitularTransmitenteBean().getPersona().getNif().equals(CADENA_VACIA)){
					DatosRepresentante datosRepresentante = objFactory.createDatosRepresentante();
					datosRepresentante.setDOI(tramite.getRepresentantePrimerCotitularTransmitenteBean().getPersona().getNif());
					datosPrimerCotitularTransmitente.setDatosRepresentante(datosRepresentante);
				}
				//Datos personales
				DatosPersonalesTransmitente datosPersonalesPrimerCotitularTransmitente = objFactory.createDatosPersonalesTransmitente();
				//Datos Filiacion
				DatosPersonalesTransmitente.DatosFiliacion datosFiliacion = objFactory.createDatosPersonalesTransmitenteDatosFiliacion();
				if(TipoPersona.Fisica.getValorEnum().equals(primerCotitular.getTipoPersona().getValorEnum())){
					//Persona Fisica
					DatosPersonalesTransmitente.DatosFiliacion.PersonaFisica personaFisica = objFactory.createDatosPersonalesTransmitenteDatosFiliacionPersonaFisica();
					personaFisica.setNombre(primerCotitular.getNombre());
					personaFisica.setPrimerApellido(primerCotitular.getApellido1RazonSocial());
					personaFisica.setSegundoApellido(!CADENA_VACIA.equals(primerCotitular.getApellido2())?primerCotitular.getApellido2():CADENA_VACIA);
					try {
						// DRC@01-10-2012 Incidencia: 2493
						if (primerCotitular.getFechaNacimientoBean() != null && primerCotitular.getFechaNacimientoBean().getTimestamp() != null)
							personaFisica.setFechaNacimiento(formatoFecha.format(primerCotitular.getFechaNacimientoBean().getTimestamp()));
						else
							personaFisica.setFechaNacimiento(CADENA_VACIA);
					} catch(ParseException e) {
						errores.add(ERROR_AL_PARSEAR_LA_FECHA_DE_NACIMIENTO_DEL_PRIMER_COTITULAR_TRANSMITENTE);
					}
					//Seteamos la persona física
					datosFiliacion.setPersonaFisica(personaFisica);
				} else {
					//Persona Jurídica
					DatosPersonalesTransmitente.DatosFiliacion.PersonaJuridica personaJuridica = objFactory.createDatosPersonalesTransmitenteDatosFiliacionPersonaJuridica();
					personaJuridica.setRazonSocial(primerCotitular.getApellido1RazonSocial());
					//Seteamos la persona jurídica
					datosFiliacion.setPersonaJuridica(personaJuridica);
				}
				//Seteamos la persona física o jurídica
				datosPersonalesPrimerCotitularTransmitente.setDatosFiliacion(datosFiliacion);
				datosPersonalesPrimerCotitularTransmitente.setSexo(primerCotitular.getSexo().equals("H")?"M":primerCotitular.getSexo());
				datosPersonalesPrimerCotitularTransmitente.setDOI(primerCotitular.getNif());
				//Seteamos los datos del transmitente
				datosPrimerCotitularTransmitente.setDatosTransmitente(datosPersonalesPrimerCotitularTransmitente);
				//Seteamos el primer cotitular transmitente
				cotitularesTransmitentes.getTransmitente().add(datosPrimerCotitularTransmitente);
			}

			if(tramite.getSegundoCotitularTransmitenteBean()!=null && tramite.getSegundoCotitularTransmitenteBean().getPersona()!=null
					&& tramite.getSegundoCotitularTransmitenteBean().getPersona().getNif()!=null
					&& !CADENA_VACIA.equals(tramite.getSegundoCotitularTransmitenteBean().getPersona().getNif())){
				DatosTransmitente datosSegundoCotitularTransmitente = objFactory.createDatosTransmitente();
				Persona segundoCotitular = tramite.getSegundoCotitularTransmitenteBean().getPersona();
				// Datos representante:
				if(tramite.getRepresentanteSegundoCotitularTransmitenteBean() != null && tramite.getRepresentanteSegundoCotitularTransmitenteBean().getPersona() != null
						&& tramite.getRepresentanteSegundoCotitularTransmitenteBean().getPersona().getNif() != null
						&& !tramite.getRepresentanteSegundoCotitularTransmitenteBean().getPersona().getNif().equals(CADENA_VACIA)){
					DatosRepresentante datosRepresentante = objFactory.createDatosRepresentante();
					datosRepresentante.setDOI(tramite.getRepresentanteSegundoCotitularTransmitenteBean().getPersona().getNif());
					datosSegundoCotitularTransmitente.setDatosRepresentante(datosRepresentante);
				}
				//Datos personales
				DatosPersonalesTransmitente datosPersonalesSegundoCotitularTransmitente = objFactory.createDatosPersonalesTransmitente();
				//Datos Filiación
				DatosPersonalesTransmitente.DatosFiliacion datosFiliacion = objFactory.createDatosPersonalesTransmitenteDatosFiliacion();
				if (TipoPersona.Fisica.getValorEnum().equals(segundoCotitular.getTipoPersona().getValorEnum())) {
					//Persona Física
					DatosPersonalesTransmitente.DatosFiliacion.PersonaFisica personaFisica = objFactory.createDatosPersonalesTransmitenteDatosFiliacionPersonaFisica();
					personaFisica.setNombre(segundoCotitular.getNombre());
					personaFisica.setPrimerApellido(segundoCotitular.getApellido1RazonSocial());
					personaFisica.setSegundoApellido(!CADENA_VACIA.equals(segundoCotitular.getApellido2())?segundoCotitular.getApellido2():CADENA_VACIA);
					try {
						// DRC@01-10-2012 Incidencia: 2493
						if (segundoCotitular != null && segundoCotitular.getFechaNacimientoBean() != null)
							personaFisica.setFechaNacimiento(formatoFecha.format(segundoCotitular.getFechaNacimientoBean().getTimestamp()));
						else
							personaFisica.setFechaNacimiento(CADENA_VACIA);
					} catch(ParseException e) {
						errores.add(ERROR_AL_PARSEAR_LA_FECHA_DE_NACIMIENTO_DEL_SEGUNDO_COTITULAR_TRANSMITENTE);
					}
					//Seteamos la persona física
					datosFiliacion.setPersonaFisica(personaFisica);
				} else {
					//Persona Jurídica
					DatosPersonalesTransmitente.DatosFiliacion.PersonaJuridica personaJuridica = objFactory.createDatosPersonalesTransmitenteDatosFiliacionPersonaJuridica();
					personaJuridica.setRazonSocial(segundoCotitular.getApellido1RazonSocial());
					//Seteamos la persona jurídica
					datosFiliacion.setPersonaJuridica(personaJuridica);
				}
				//Seteamos la persona física o jurídica
				datosPersonalesSegundoCotitularTransmitente.setDatosFiliacion(datosFiliacion);
				datosPersonalesSegundoCotitularTransmitente.setSexo(segundoCotitular.getSexo().equals("H")?"M":segundoCotitular.getSexo());
				datosPersonalesSegundoCotitularTransmitente.setDOI(segundoCotitular.getNif());
				//Seteamos los datos del transmitente
				datosSegundoCotitularTransmitente.setDatosTransmitente(datosPersonalesSegundoCotitularTransmitente);
				//Seteamos el segundo cotitular transmitente
				cotitularesTransmitentes.getTransmitente().add(datosSegundoCotitularTransmitente);
			}

			//Seteamos
			datosEspecificos.setCotitularesTransmitentes(cotitularesTransmitentes);
		}

		String codigoIAE = null;

		//Adquiriente
		if (tramite.getAdquirienteBean() != null && tramite.getAdquirienteBean().getPersona() != null
				&& tramite.getAdquirienteBean().getPersona().getNif()!=null && !CADENA_VACIA.equals(tramite.getAdquirienteBean().getPersona().getNif())
				&& !tramite.getTipoTransferencia().equals(TipoTransferencia.tipo5)) { //TIPO TRANSFERENCIA TRADE NO LLEVA ADQUIRIENTE (Antes tipo3)
			DatosAdquirente datosAdquiriente = objFactory.createDatosAdquirente();
			Persona adquiriente = tramite.getAdquirienteBean().getPersona();
			//Datos personales
			DatosPersonales datosPersonalesAdquiriente = objFactory.createDatosPersonales();
			//Datos Filiación
			DatosFiliacion datosFiliacionAdquiriente = objFactory.createDatosPersonalesDatosFiliacion();
			if(TipoPersona.Fisica.getValorEnum().equals(adquiriente.getTipoPersona().getValorEnum())){
				//Persona Fisica
				PersonaFisica personaFisica = objFactory.createDatosPersonalesDatosFiliacionPersonaFisica();
				personaFisica.setNombre(adquiriente.getNombre());
				personaFisica.setPrimerApellido(adquiriente.getApellido1RazonSocial());
				personaFisica.setSegundoApellido(!CADENA_VACIA.equals(adquiriente.getApellido2())?adquiriente.getApellido2():CADENA_VACIA);
				try{
					// DRC@01-10-2012 Incidencia: 2493
					if (adquiriente.getFechaNacimientoBean() != null && adquiriente.getFechaNacimientoBean().getTimestamp() != null)
						personaFisica.setFechaNacimiento(formatoFecha.format(adquiriente.getFechaNacimientoBean().getTimestamp()));
					else
						personaFisica.setFechaNacimiento(CADENA_VACIA);
				}catch(ParseException e){
					errores.add(ERROR_AL_PARSEAR_LA_FECHA_DE_NACIMIENTO_DEL_ADQUIRIENTE);
				}
				//Seteamos la persona física
				datosFiliacionAdquiriente.setPersonaFisica(personaFisica);
			} else {
				//Persona Jurídica
				PersonaJuridica personaJuridica = objFactory.createDatosPersonalesDatosFiliacionPersonaJuridica();
				personaJuridica.setRazonSocial(adquiriente.getApellido1RazonSocial());
				//Seteamos la persona jurídica
				datosFiliacionAdquiriente.setPersonaJuridica(personaJuridica);
			}
			//Seteamos la persona física o jurídica
			datosPersonalesAdquiriente.setDatosFiliacion(datosFiliacionAdquiriente);
			datosPersonalesAdquiriente.setSexo(adquiriente.getSexo().equals("H") ? "M" : adquiriente.getSexo());
			datosPersonalesAdquiriente.setDOI(adquiriente.getNif());
			//Seteamos los datos del Adquiriente
			datosAdquiriente.setDatosAdquirente(datosPersonalesAdquiriente);
			if(tramite.getRepresentanteAdquirienteBean() != null && tramite.getRepresentanteAdquirienteBean().getPersona() != null
					&& tramite.getRepresentanteAdquirienteBean().getPersona().getNif() != null
					&& !CADENA_VACIA.equals(tramite.getRepresentanteAdquirienteBean().getPersona().getNif())){
				//Datos representante
				DatosRepresentante datosRepresentanteAdquiriente = objFactory.createDatosRepresentante();
				datosRepresentanteAdquiriente.setDOI(tramite.getRepresentanteAdquirienteBean().getPersona().getNif());
				//Seteamos los datos del representante del Adquiriente
				datosAdquiriente.setDatosRepresentante(datosRepresentanteAdquiriente);
			}
			datosAdquiriente.setActualizacionDomicilio(TRUE.equals(tramite.getAdquirienteBean().getCambioDomicilio())?TipoSINO.SI:TipoSINO.NO);
			//Domicilio Adquiriente
			Direccion dirAdquiriente = adquiriente.getDireccion();
			Domicilio domicilioAdquiriente = objFactory.createDomicilio();

			String idProvinciaAdquiriente = dirAdquiriente.getMunicipio().getProvincia().getIdProvincia()!=null &&
				!"-1".equals(dirAdquiriente.getMunicipio().getProvincia().getIdProvincia())?dirAdquiriente.getMunicipio().getProvincia().getIdProvincia():CADENA_VACIA;
			String idMunicipioAdquiriente = dirAdquiriente.getMunicipio().getIdMunicipio()!=null &&
				!"-1".equals(dirAdquiriente.getMunicipio().getIdMunicipio())?dirAdquiriente.getMunicipio().getIdMunicipio():CADENA_VACIA;
			String idPuebloAdquiriente = dirAdquiriente.getPueblo()!=null &&
				!"-1".equals(dirAdquiriente.getPueblo())?dirAdquiriente.getPueblo():CADENA_VACIA;
				String idTipoViaAdquiriente = dirAdquiriente.getTipoVia().getIdTipoVia()!=null &&
				!"-1".equals(dirAdquiriente.getTipoVia().getIdTipoVia())?dirAdquiriente.getTipoVia().getIdTipoVia():CADENA_VACIA;
			String idNombreViaAdquiriente = dirAdquiriente.getNombreVia()!=null &&
				!"-1".equals(dirAdquiriente.getNombreVia())?dirAdquiriente.getNombreVia():CADENA_VACIA;

			domicilioAdquiriente.setMunicipio(idProvinciaAdquiriente+idMunicipioAdquiriente);
			domicilioAdquiriente.setLocalidad(idPuebloAdquiriente);
			domicilioAdquiriente.setProvincia(idProvinciaAdquiriente);
			domicilioAdquiriente.setCodigoPostal(null!=dirAdquiriente.getCodPostal()?dirAdquiriente.getCodPostal():CADENA_VACIA);
			domicilioAdquiriente.setTipoVia(idTipoViaAdquiriente);
			domicilioAdquiriente.setNombreVia(utilesConversiones.ajustarCamposIne(idNombreViaAdquiriente));

			//JRG. 18-02-2013 En la última actualización del WS de fullCtit se pide que en caso de que no haya de vía se inserte "SN".
			if (!CADENA_VACIA.equals(idNombreViaAdquiriente)){
				//Si se ha metido dirección tiene que tener número de vía o SN.
				if (null!=dirAdquiriente.getNumero() && !dirAdquiriente.getNumero().equals(CADENA_VACIA)){
					domicilioAdquiriente.setNumero(dirAdquiriente.getNumero());}
				else{
					domicilioAdquiriente.setNumero(SN);}
			}else{
				//Si no tiene dirección le mete cadena vacía.
				domicilioAdquiriente.setNumero(CADENA_VACIA);
			}

			domicilioAdquiriente.setNumero(null!=dirAdquiriente.getNumero()&& !dirAdquiriente.getNumero().equals(CADENA_VACIA)?dirAdquiriente.getNumero():SN);
			domicilioAdquiriente.setKilometro(dirAdquiriente.getPuntoKilometrico()!=null?dirAdquiriente.getPuntoKilometrico():CADENA_VACIA);
			domicilioAdquiriente.setHectometro(dirAdquiriente.getHm()!=null?dirAdquiriente.getHm():CADENA_VACIA);
			domicilioAdquiriente.setBloque(dirAdquiriente.getBloque()!=null?dirAdquiriente.getBloque():CADENA_VACIA);
			domicilioAdquiriente.setPortal(dirAdquiriente.getPortal()!=null && dirAdquiriente.getPortal().length() <= 2 ?dirAdquiriente.getPortal():CADENA_VACIA);
			domicilioAdquiriente.setEscalera(dirAdquiriente.getEscalera()!=null?dirAdquiriente.getEscalera():CADENA_VACIA);
			domicilioAdquiriente.setPlanta(dirAdquiriente.getPlanta()!=null?dirAdquiriente.getPlanta():CADENA_VACIA);
			domicilioAdquiriente.setPuerta(dirAdquiriente.getPuerta()!=null?dirAdquiriente.getPuerta():CADENA_VACIA);
			domicilioAdquiriente.setPais(ESP);
			//Seteamos el domicilio del adquiriente
			datosAdquiriente.setDomicilio(domicilioAdquiriente);
			//Seteamos
			datosEspecificos.setTitularAdquirente(datosAdquiriente);
		}// Fin de tipo transferencia no trade.
		//TODO PROBAR LAS OTRAS TIPOS DE TRANSFERENCIA.

		//Datos autónomo
		DatosAutonomo datosAutonomoAdquiriente = objFactory.createDatosEspecificosDatosAutonomo();
		datosAutonomoAdquiriente.setAutonomo(null!=tramite.getAdquirienteBean() &&
				null!=tramite.getAdquirienteBean().getAutonomo() &&
				TRUE.equals(tramite.getAdquirienteBean().getAutonomo()) ? TipoSINO.SI : TipoSINO.NO);

		if( null!=tramite.getAdquirienteBean() &&
				null!=tramite.getAdquirienteBean().getCodigoIAE() &&
				!"-1".equals(tramite.getAdquirienteBean().getCodigoIAE())){
			codigoIAE = tramite.getAdquirienteBean().getCodigoIAE();
		}
		datosAutonomoAdquiriente.setCodigoIAE(codigoIAE);
		//Seteamos
		datosEspecificos.setDatosAutonomo(datosAutonomoAdquiriente);

		//Poseedor
		if(tramite.getPoseedorBean()!=null && tramite.getPoseedorBean().getPersona()!=null &&
				tramite.getPoseedorBean().getPersona().getNif()!=null && !CADENA_VACIA.equals(tramite.getPoseedorBean().getPersona().getNif())){
			Poseedor poseedor = objFactory.createDatosEspecificosPoseedor();
			Persona poseedorPersona = tramite.getPoseedorBean().getPersona();
			//Datos personales
			DatosPersonales datosPersonalesPoseedor = objFactory.createDatosPersonales();
			//Datos Filiación
			DatosFiliacion datosFiliacionPoseedor = objFactory.createDatosPersonalesDatosFiliacion();
			if(TipoPersona.Fisica.getValorEnum().equals(poseedorPersona.getTipoPersona().getValorEnum())){
				//Persona Física
				PersonaFisica personaFisica = objFactory.createDatosPersonalesDatosFiliacionPersonaFisica();
				personaFisica.setNombre(poseedorPersona.getNombre());
				personaFisica.setPrimerApellido(poseedorPersona.getApellido1RazonSocial());
				personaFisica.setSegundoApellido(!CADENA_VACIA.equals(poseedorPersona.getApellido2())?poseedorPersona.getApellido2():CADENA_VACIA);
				try {
					// DRC@01-10-2012 Incidencia: 2493
					if (poseedorPersona.getFechaNacimientoBean() != null && poseedorPersona.getFechaNacimientoBean().getTimestamp() != null)
						personaFisica.setFechaNacimiento(formatoFecha.format(poseedorPersona.getFechaNacimientoBean().getTimestamp()));
					else
						personaFisica.setFechaNacimiento(CADENA_VACIA);
				} catch(ParseException e) {
					errores.add(ERROR_AL_PARSEAR_LA_FECHA_DE_NACIMIENTO_DEL_POSEEDOR);
				}
				//Seteamos la persona física
				datosFiliacionPoseedor.setPersonaFisica(personaFisica);
			} else {
				//Persona Jurídica
				PersonaJuridica personaJuridica = objFactory.createDatosPersonalesDatosFiliacionPersonaJuridica();
				personaJuridica.setRazonSocial(poseedorPersona.getApellido1RazonSocial());
				//Seteamos la persona jurídica
				datosFiliacionPoseedor.setPersonaJuridica(personaJuridica);
			}
			//Seteamos la persona física o jurídica
			datosPersonalesPoseedor.setDatosFiliacion(datosFiliacionPoseedor);
			datosPersonalesPoseedor.setDOI(poseedorPersona.getNif());
			datosPersonalesPoseedor.setSexo(poseedorPersona.getSexo().equals("H") ? "M" : poseedorPersona.getSexo());
			//Seteamos los datos del Poseedor
			poseedor.setDatosPoseedor(datosPersonalesPoseedor);

			codigoIAE = null;
			if (!"-1".equals(tramite.getPoseedorBean().getCodigoIAE())
					&& tramite.getPoseedorBean().getCodigoIAE() != null) {
				codigoIAE = tramite.getPoseedorBean().getCodigoIAE();
			}
			poseedor.setCodigoIAE(null!=codigoIAE ? codigoIAE : CADENA_VACIA);

			if(tramite.getRepresentantePoseedorBean()!=null && tramite.getRepresentantePoseedorBean().getPersona()!=null &&
					tramite.getRepresentantePoseedorBean().getPersona().getNif()!=null && !CADENA_VACIA.equals(tramite.getRepresentantePoseedorBean().getPersona().getNif())){
				//Datos representante
				DatosRepresentante datosRepresentantePoseedor = objFactory.createDatosRepresentante();
				datosRepresentantePoseedor.setDOI(tramite.getRepresentantePoseedorBean().getPersona().getNif());
				//Seteamos los datos del representante del Poseedor
				poseedor.setDatosRepresentante(datosRepresentantePoseedor);
			}
			poseedor.setActualizacionDomicilio(TRUE.equals(tramite.getAdquirienteBean().getCambioDomicilio())?TipoSINO.SI:TipoSINO.NO);
			//Domicilio Poseedor
			Domicilio domicilioPoseedor = objFactory.createDomicilio();
			Direccion dirPoseedor = poseedorPersona.getDireccion();

			String idProvinciaPoseedor = dirPoseedor.getMunicipio().getProvincia().getIdProvincia()!=null &&
				!"-1".equals(dirPoseedor.getMunicipio().getProvincia().getIdProvincia())?dirPoseedor.getMunicipio().getProvincia().getIdProvincia():CADENA_VACIA;
			String idMunicipioPoseedor = dirPoseedor.getMunicipio().getIdMunicipio()!=null &&
				!"-1".equals(dirPoseedor.getMunicipio().getIdMunicipio())?dirPoseedor.getMunicipio().getIdMunicipio():CADENA_VACIA;
			String idPuebloPoseedor = dirPoseedor.getPueblo()!=null &&
				!"-1".equals(dirPoseedor.getPueblo())?dirPoseedor.getPueblo():CADENA_VACIA;
			String idTipoViaPoseedor = dirPoseedor.getTipoVia().getIdTipoVia()!=null &&
				!"-1".equals(dirPoseedor.getTipoVia().getIdTipoVia())?dirPoseedor.getTipoVia().getIdTipoVia():CADENA_VACIA;
			String idNombreViaPoseedor = dirPoseedor.getNombreVia()!=null &&
				!"-1".equals(dirPoseedor.getNombreVia())?dirPoseedor.getNombreVia():CADENA_VACIA;

			domicilioPoseedor.setMunicipio(idProvinciaPoseedor+idMunicipioPoseedor);
			domicilioPoseedor.setLocalidad(idPuebloPoseedor);
			domicilioPoseedor.setProvincia(idProvinciaPoseedor);
			domicilioPoseedor.setCodigoPostal(null!=dirPoseedor.getCodPostal()?dirPoseedor.getCodPostal():CADENA_VACIA);
			domicilioPoseedor.setTipoVia(idTipoViaPoseedor);
			domicilioPoseedor.setNombreVia(utilesConversiones.ajustarCamposIne(idNombreViaPoseedor));
			//JRG. 18-02-2013 En la ultima actualización del WS de fullCtit se pide que en caso de que no haya de vía se inserte "SN".
			if (!CADENA_VACIA.equals(idNombreViaPoseedor)){
				//Si se ha metido dirección tiene que tener número de vía o SN.
				if (null!=dirPoseedor.getNumero() && !dirPoseedor.getNumero().equals(CADENA_VACIA)){
					domicilioPoseedor.setNumero(dirPoseedor.getNumero());}
				else{
					domicilioPoseedor.setNumero(SN);}
			}else{
				domicilioPoseedor.setNumero(CADENA_VACIA);
			}
			domicilioPoseedor.setKilometro(dirPoseedor.getPuntoKilometrico()!=null?dirPoseedor.getPuntoKilometrico():CADENA_VACIA);
			domicilioPoseedor.setHectometro(dirPoseedor.getHm()!=null?dirPoseedor.getHm():CADENA_VACIA);
			domicilioPoseedor.setBloque(dirPoseedor.getBloque()!=null?dirPoseedor.getBloque():CADENA_VACIA);
			domicilioPoseedor.setPortal(dirPoseedor.getPortal()!=null && dirPoseedor.getPortal().length() <= 2 ?dirPoseedor.getPortal():CADENA_VACIA);
			domicilioPoseedor.setEscalera(dirPoseedor.getEscalera()!=null?dirPoseedor.getEscalera():CADENA_VACIA);
			domicilioPoseedor.setPlanta(dirPoseedor.getPlanta()!=null?dirPoseedor.getPlanta():CADENA_VACIA);
			domicilioPoseedor.setPuerta(dirPoseedor.getPuerta()!=null?dirPoseedor.getPuerta():CADENA_VACIA);
			domicilioPoseedor.setPais(ESP);
			//Seteamos el domicilio del Poseedor
			poseedor.setDomicilio(domicilioPoseedor);
			//Seteamos
			datosEspecificos.setPoseedor(poseedor);
		}

		//Arrendatario
		if (TRUE.equals(tramite.getTramiteTraficoBean().getRenting()) && tramite.getArrendatarioBean()!=null && tramite.getArrendatarioBean().getPersona() != null
				&& tramite.getArrendatarioBean().getPersona().getNif()!=null && !CADENA_VACIA.equals(tramite.getArrendatarioBean().getPersona().getNif())){
			Arrendatario arrendatario = objFactory.createDatosEspecificosArrendatario();
			Persona arrendatarioPersona = tramite.getArrendatarioBean().getPersona();
			//Datos personales
			DatosPersonales datosPersonalesArrendatario = objFactory.createDatosPersonales();
			//Datos Filiación
			DatosFiliacion datosFiliacionArrendatario = objFactory.createDatosPersonalesDatosFiliacion();
			if (TipoPersona.Fisica.getValorEnum().equals(tramite.getArrendatarioBean().getPersona().getTipoPersona().getValorEnum())){
				//Persona Física
				PersonaFisica personaFisica = objFactory.createDatosPersonalesDatosFiliacionPersonaFisica();
				personaFisica.setNombre(arrendatarioPersona.getNombre());
				personaFisica.setPrimerApellido(arrendatarioPersona.getApellido1RazonSocial());
				personaFisica.setSegundoApellido(!CADENA_VACIA.equals(arrendatarioPersona.getApellido2())?arrendatarioPersona.getApellido2():CADENA_VACIA);
				try {
					// DRC@01-10-2012 Incidencia: 2493
					if (arrendatarioPersona.getFechaNacimientoBean() != null)
						personaFisica.setFechaNacimiento(formatoFecha.format(arrendatarioPersona.getFechaNacimientoBean().getTimestamp()));
					else
						personaFisica.setFechaNacimiento(CADENA_VACIA);
				} catch(ParseException e) {
					errores.add(ERROR_AL_PARSEAR_LA_FECHA_DE_NACIMIENTO_DEL_ARRENDATARIO);
				}
				//Seteamos la persona física
				datosFiliacionArrendatario.setPersonaFisica(personaFisica);
			} else {
				//Persona Jurídica
				PersonaJuridica personaJuridica = objFactory.createDatosPersonalesDatosFiliacionPersonaJuridica();
				personaJuridica.setRazonSocial(arrendatarioPersona.getApellido1RazonSocial());
				//Seteamos la persona jurídica
				datosFiliacionArrendatario.setPersonaJuridica(personaJuridica);
			}
			// Seteamos la persona física o jurídica
			datosPersonalesArrendatario.setDatosFiliacion(datosFiliacionArrendatario);
			datosPersonalesArrendatario.setSexo(arrendatarioPersona.getSexo().equals("H") ? "M" : arrendatarioPersona.getSexo());
			datosPersonalesArrendatario.setDOI(arrendatarioPersona.getNif());
			//Seteamos los datos del Arrendatario
			arrendatario.setDatosArrendatario(datosPersonalesArrendatario);
			//Domicilio Arrendatario
			Domicilio domicilioArrendatario = objFactory.createDomicilio();
			Direccion dirArrendatario = arrendatarioPersona.getDireccion();

			String idProvinciaArrendatario = dirArrendatario.getMunicipio().getProvincia().getIdProvincia() != null
					&& !"-1".equals(dirArrendatario.getMunicipio().getProvincia().getIdProvincia()) ? dirArrendatario.getMunicipio().getProvincia().getIdProvincia() : CADENA_VACIA;
			String idMunicipioArrendatario = dirArrendatario.getMunicipio().getIdMunicipio() != null
					&& !"-1".equals(dirArrendatario.getMunicipio().getIdMunicipio())?dirArrendatario.getMunicipio().getIdMunicipio():CADENA_VACIA;
			String idPuebloArrendatario = dirArrendatario.getPueblo() != null
					&& !"-1".equals(dirArrendatario.getPueblo())?dirArrendatario.getPueblo():CADENA_VACIA;
			String idTipoViaArrendatario = dirArrendatario.getTipoVia().getIdTipoVia()!=null
					&& !"-1".equals(dirArrendatario.getTipoVia().getIdTipoVia())?dirArrendatario.getTipoVia().getIdTipoVia():CADENA_VACIA;
			String idNombreViaArrendatario = dirArrendatario.getNombreVia() != null
					&& !"-1".equals(dirArrendatario.getNombreVia())?dirArrendatario.getNombreVia():CADENA_VACIA;

			domicilioArrendatario.setMunicipio(idProvinciaArrendatario+idMunicipioArrendatario);
			domicilioArrendatario.setLocalidad(idPuebloArrendatario);
			domicilioArrendatario.setProvincia(idProvinciaArrendatario);
			domicilioArrendatario.setCodigoPostal(null!=dirArrendatario.getCodPostal()?dirArrendatario.getCodPostal():CADENA_VACIA);
			domicilioArrendatario.setTipoVia(idTipoViaArrendatario);
			domicilioArrendatario.setNombreVia(utilesConversiones.ajustarCamposIne(idNombreViaArrendatario));

			// JRG. 18-02-2013 En la ultima actualización del WS de fullCtit se pide que en caso de que no haya número de via se inserte "SN".
			if (!CADENA_VACIA.equals(idNombreViaArrendatario)) {
				// Si se ha metido dirección tiene que tener número de vía o SN.
				if (null != dirArrendatario.getNumero() && !dirArrendatario.getNumero().equals(CADENA_VACIA)) {
					domicilioArrendatario.setNumero(dirArrendatario.getNumero());
				} else {
					domicilioArrendatario.setNumero(SN);
				}
			} else {
				// Si no tiene dirección le mete cadena vacía.
				domicilioArrendatario.setNumero(CADENA_VACIA);
			}

			domicilioArrendatario.setKilometro(dirArrendatario.getPuntoKilometrico()!=null?dirArrendatario.getPuntoKilometrico():CADENA_VACIA);
			domicilioArrendatario.setHectometro(dirArrendatario.getHm()!=null?dirArrendatario.getHm():CADENA_VACIA);
			domicilioArrendatario.setBloque(dirArrendatario.getBloque()!=null?dirArrendatario.getBloque():CADENA_VACIA);
			domicilioArrendatario.setPortal(dirArrendatario.getPortal()!=null && dirArrendatario.getPortal().length() <= 2 ? dirArrendatario.getPortal() : CADENA_VACIA);
			domicilioArrendatario.setEscalera(dirArrendatario.getEscalera()!=null ? dirArrendatario.getEscalera() : CADENA_VACIA);
			domicilioArrendatario.setPlanta(dirArrendatario.getPlanta()!=null ? dirArrendatario.getPlanta() : CADENA_VACIA);
			domicilioArrendatario.setPuerta(dirArrendatario.getPuerta()!=null ? dirArrendatario.getPuerta() : CADENA_VACIA);
			domicilioArrendatario.setPais(ESP);
			//Seteamos el domicilio del Arrendatario
			arrendatario.setDomicilio(domicilioArrendatario);
			//Seteamos
			datosEspecificos.setArrendatario(arrendatario);
		}

		//Conductor Habitual
		if (tramite.getConductorHabitualBean() !=null && tramite.getConductorHabitualBean().getPersona() != null
				&& tramite.getConductorHabitualBean().getPersona().getNif() != null && !CADENA_VACIA.equals(tramite.getConductorHabitualBean().getPersona().getNif())) {
			ConductorHabitual conductorHabitual = objFactory.createDatosEspecificosConductorHabitual();
			conductorHabitual.setDOI(tramite.getConductorHabitualBean().getPersona().getNif());
			try {
				conductorHabitual.setFechaNacimiento(tramite.getConductorHabitualBean().getPersona().getFechaNacimientoBean()!=null
						&& !CADENA_VACIA.equals(tramite.getConductorHabitualBean().getPersona().getFechaNacimientoBean().getAnio())
							? formatoFecha.format(tramite.getConductorHabitualBean().getPersona().getFechaNacimientoBean().getTimestamp())
									: CADENA_VACIA);
			} catch(ParseException e) {
				errores.add(ERROR_AL_PARSEAR_LA_FECHA_DE_NACIMIENTO_DEL_CONDUCTOR_HABITUAL);
			}
			//Seteamos
			datosEspecificos.setConductorHabitual(conductorHabitual);
		}

		//Acreditación Derecho
		AcreditacionDerecho acreditacionDerecho = objFactory.createDatosEspecificosAcreditacionDerecho();
		acreditacionDerecho.setSolicitud(TipoSI.SI);

		//En el primer paso de los envíos al Full en caso de tramitable con incidencia, Antes tipo1
		// DRC@01-10-2012 Incidencia: 2493
		if (tramite != null && tramite.getTipoTransferencia() != null
				&& (tramite.getTipoTransferencia().equals(TipoTransferencia.tipo1) ||
				tramite.getTipoTransferencia().equals(TipoTransferencia.tipo2) ||
				tramite.getTipoTransferencia().equals(TipoTransferencia.tipo3)) &&
				paso.equals(Paso.UnoFullIncidencias)) {
			acreditacionDerecho.setConsentimiento(TipoConsentimiento.NO);
		} else if (tramite.getCodigoTasaInforme() != null && !"-1".equals(tramite.getCodigoTasaInforme())) {
			acreditacionDerecho.setConsentimiento(TRUE.equals(tramite.getConsentimiento())?TipoConsentimiento.SI:TipoConsentimiento.N_A);
		} else {
			acreditacionDerecho.setConsentimiento(TipoConsentimiento.N_A);
		}

		//Motivo Transmisión
		MotivoTransmision motivoTransmision = objFactory.createDatosEspecificosAcreditacionDerechoMotivoTransmision();
		if (tramite.getFactura() != null && !CADENA_VACIA.equals(tramite.getFactura())) {
			motivoTransmision.setFactura(tramite.getFactura());
		} else {
			motivoTransmision.setContratoCompraventa(TRUE.equals(tramite.getContratoFactura()) ? TipoSINO.SI : TipoSINO.NO);
		}
		//Seteamos el motivo de transmisión
		acreditacionDerecho.setMotivoTransmision(motivoTransmision);

		//Fallecimiento / Donación
		// DRC@01-10-2012 Incidencia: 2493
		if (tramite.getModoAdjudicacion() != null
				&& ModoAdjudicacion.tipo3.getValorEnum().equals(tramite.getModoAdjudicacion().getValorEnum())) {
			FallecimientoDonacion fallecimientoDonacion = objFactory.createDatosEspecificosAcreditacionDerechoFallecimientoDonacion();

			if (tramite.getAcreditaHerenciaDonacion() != null) {
				if (tramite.getAcreditaHerenciaDonacion().getValorEnum().equals(AcreditacionTrafico.Herencia.getValorEnum())) {
					fallecimientoDonacion.setAcreditacionHerencia(TipoSINO.SI);
				}

				if (tramite.getAcreditaHerenciaDonacion().getValorEnum().equals(AcreditacionTrafico.Posesion.getValorEnum())
						|| tramite.getAcreditaHerenciaDonacion().getValorEnum().equals(AcreditacionTrafico.Donacion.getValorEnum())) {
					fallecimientoDonacion.setAcreditacionPosesion(TipoSINO.SI);
				}
			}

			// Seteamos acreditación herencia / donación
			acreditacionDerecho.setFallecimientoDonacion(fallecimientoDonacion);
		}
		// Seteamos
		datosEspecificos.setAcreditacionDerecho(acreditacionDerecho);

		// Acreditación Fiscal
		AcreditacionFiscal acreditacionFiscal = objFactory.createDatosEspecificosAcreditacionFiscal();
		String provinciaImpuestos = CADENA_VACIA;
		// DRC@01-10-2012 Incindecia: 2493
		if (tramite != null
				&& tramite.getProvinciaCet() != null
				&& tramite.getProvinciaCet().getIdProvincia() != null
				&& !tramite.getProvinciaCet().getIdProvincia().isEmpty()
				&& !"-1".equalsIgnoreCase(tramite.getProvinciaCet().getIdProvincia())){
			provinciaImpuestos = tramite.getProvinciaCet().getIdProvincia();
		} else if (tramite != null
				&& tramite.getProvinciaCem() != null
				&& tramite.getProvinciaCem().getIdProvincia() != null
				&& !tramite.getProvinciaCem().getIdProvincia().isEmpty()
				&& !"-1".equalsIgnoreCase(tramite.getProvinciaCem().getIdProvincia())){
			provinciaImpuestos = tramite.getProvinciaCem().getIdProvincia();
		} else if (tramite != null
				&& tramite.getAdquirienteBean() != null
				&& tramite.getAdquirienteBean().getPersona() != null
				&& tramite.getAdquirienteBean().getPersona().getDireccion() != null
				&& tramite.getAdquirienteBean().getPersona().getDireccion().getMunicipio() != null
				&& tramite.getAdquirienteBean().getPersona().getDireccion().getMunicipio().getProvincia() != null
				&& tramite.getAdquirienteBean().getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia() != null
				&& !tramite.getAdquirienteBean().getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia().equalsIgnoreCase(CADENA_VACIA)
				&& !"-1".equals(tramite.getAdquirienteBean().getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia())) {
			provinciaImpuestos = tramite.getAdquirienteBean().getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia();
		} else if (tramite != null
				&& tramite.getPoseedorBean() != null
				&& tramite.getPoseedorBean().getPersona() != null
				&& tramite.getPoseedorBean().getPersona().getDireccion() != null
				&& tramite.getPoseedorBean().getPersona().getDireccion().getMunicipio() != null
				&& tramite.getPoseedorBean().getPersona().getDireccion().getMunicipio().getProvincia() != null
				&& tramite.getPoseedorBean().getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia() != null
				&& !tramite.getPoseedorBean().getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia().equalsIgnoreCase(CADENA_VACIA)
				&& !tramite.getPoseedorBean().getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia().equalsIgnoreCase("-1")) {
			provinciaImpuestos = tramite.getPoseedorBean().getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia();
		} else if (tramite != null
				&& tramite.getTramiteTraficoBean() != null
				&& tramite.getTramiteTraficoBean().getVehiculo() != null
				&& tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean() != null
				&& tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getMunicipio() != null
				&& tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getMunicipio().getProvincia() != null
				&& tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getMunicipio().getProvincia().getIdProvincia() != null
				&& !"-1".equals(tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getMunicipio().getProvincia().getIdProvincia())) {
			provinciaImpuestos = tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getMunicipio().getProvincia().getIdProvincia();
		}
		/* Mantis 0003789: Tipo transferencia : ENTREGA COMPRAVENTA
		 * Gilberto Pedroso*/
		else if (!tramite.getTipoTransferencia().getValorEnum().equals("5")) {
			log.error(EL_VEHICULO_O_TITULAR_ADQUIRIENTE_DEBEN_TENER_UNA_DIRECCION_ASOCIADA);
			errores.add(EL_VEHICULO_O_TITULAR_ADQUIRIENTE_DEBEN_TENER_UNA_DIRECCION_ASOCIADA);
		}
		// ITP
		/*
		 * Mantis 0002977: Notifificaciones - NOTIFICATION CTIT -
		 * Mantis 0003789: Tipo transferencia : ENTREGA COMPRAVENTA
		 * Gilberto Pedroso*/
	String provinciaImpuestosCet = "00";
	String codigoElectronico = null != tramite.getCetItp() && !CADENA_VACIA.equals(tramite.getCetItp()) ? tramite.getCetItp() : _00000000;
	if (!codigoElectronico.equals(_00000000)) {
		provinciaImpuestosCet = provinciaImpuestos;
	}
	if (null != tramite.getTipoTransferencia()) {
		if (!tramite.getTipoTransferencia().getValorEnum().equals("4") && !tramite.getTipoTransferencia().getValorEnum().equals("5")) {
			if (tramite.getModoAdjudicacion() != null && !tramite.getModoAdjudicacion().getValorEnum().equals(ModoAdjudicacion.tipo3.getValorEnum())
					&& (tramite.getFactura()==null || CADENA_VACIA.equals(tramite.getFactura()))){
					ITP itp = objFactory.createDatosEspecificosAcreditacionFiscalITP();
					//Acreditación Pago
					if (!TRUE.equals(tramite.getExentoItp()) && !TRUE.equals(tramite.getNoSujetoItp())) {
						trafico.beans.schemas.generated.transTelematica.DatosEspecificos.AcreditacionFiscal.ITP.AcreditacionPago acreditacionPagoITP = objFactory.createDatosEspecificosAcreditacionFiscalITPAcreditacionPago();
						TipoCEM cetItp = objFactory.createTipoCEM();

						cetItp.setCodigoElectronico(codigoElectronico);

						cetItp.setCodigoProvincia(provinciaImpuestosCet);
						acreditacionPagoITP.setCET(cetItp);
						acreditacionPagoITP.setModelo(!"-1".equals(tramite.getModeloItp()) ? tramite.getModeloItp() : CADENA_VACIA);
						//Seteamos la acreditación de pago
						itp.setAcreditacionPago(acreditacionPagoITP);
					}
					if (TRUE.equals(tramite.getExentoItp())) {
						//Acreditación de exención
						trafico.beans.schemas.generated.transTelematica.DatosEspecificos.AcreditacionFiscal.ITP.AcreditacionExencion acreditacionExencion = objFactory.createDatosEspecificosAcreditacionFiscalITPAcreditacionExencion();
						acreditacionExencion.setModelo(!"-1".equals(tramite.getModeloItp())?tramite.getModeloItp():null);
						acreditacionExencion.setExencion(TipoSI.SI);
						TipoCEM cetItp = objFactory.createTipoCEM();

						cetItp.setCodigoElectronico(codigoElectronico);

						cetItp.setCodigoProvincia(provinciaImpuestosCet);
						acreditacionExencion.setCET(cetItp);
						//Seteamos la acreditación de exención
						itp.setAcreditacionExencion(acreditacionExencion);
					} else if (TRUE.equals(tramite.getNoSujetoItp())) {
						//Acreditación de no sujección
						trafico.beans.schemas.generated.transTelematica.DatosEspecificos.AcreditacionFiscal.ITP.AcreditacionNoSujecion acreditacionNoSujecion = objFactory.createDatosEspecificosAcreditacionFiscalITPAcreditacionNoSujecion();
						acreditacionNoSujecion.setModelo(!"-1".equals(tramite.getModeloItp())?tramite.getModeloItp():CADENA_VACIA);
						acreditacionNoSujecion.setNoSujecion(TipoSI.SI);
						TipoCEM cetItp = objFactory.createTipoCEM();

						cetItp.setCodigoElectronico(codigoElectronico);

						cetItp.setCodigoProvincia(provinciaImpuestosCet);
						acreditacionNoSujecion.setCET(cetItp);
						//Seteamos la acreditación de no sujección
						itp.setAcreditacionNoSujecion(acreditacionNoSujecion);
					}
					//Seteamos el ITP
					acreditacionFiscal.setITP(itp);
				}
			} else if (null!=tramite.getCetItp() && !CADENA_VACIA.equals(tramite.getCetItp())
						&& !tramite.getModoAdjudicacion().getValorEnum().equals(ModoAdjudicacion.tipo3.getValorEnum())
						&& (tramite.getFactura()==null || CADENA_VACIA.equals(tramite.getFactura()))) {
				ITP itp = objFactory.createDatosEspecificosAcreditacionFiscalITP();
				//Acreditación Pago
				if (!TRUE.equals(tramite.getExentoItp()) && !TRUE.equals(tramite.getNoSujetoItp())) {
					trafico.beans.schemas.generated.transTelematica.DatosEspecificos.AcreditacionFiscal.ITP.AcreditacionPago acreditacionPagoITP = objFactory.createDatosEspecificosAcreditacionFiscalITPAcreditacionPago();
					TipoCEM cetItp = objFactory.createTipoCEM();

					cetItp.setCodigoElectronico(tramite.getCetItp());

					cetItp.setCodigoProvincia(provinciaImpuestosCet);
					acreditacionPagoITP.setCET(cetItp);
					acreditacionPagoITP.setModelo(!"-1".equals(tramite.getModeloItp())?tramite.getModeloItp():CADENA_VACIA);
					//Seteamos la acreditación de pago
					itp.setAcreditacionPago(acreditacionPagoITP);
				}
				if (TRUE.equals(tramite.getExentoItp())) {
					//Acreditación de exención
					trafico.beans.schemas.generated.transTelematica.DatosEspecificos.AcreditacionFiscal.ITP.AcreditacionExencion acreditacionExencion = objFactory.createDatosEspecificosAcreditacionFiscalITPAcreditacionExencion();
					acreditacionExencion.setModelo(!"-1".equals(tramite.getModeloItp())?tramite.getModeloItp():null);
					acreditacionExencion.setExencion(TipoSI.SI);
					TipoCEM cetItp = objFactory.createTipoCEM();

					cetItp.setCodigoElectronico(tramite.getCetItp());

					cetItp.setCodigoProvincia(provinciaImpuestosCet);
					acreditacionExencion.setCET(cetItp);
					//Seteamos la acreditación de exención
					itp.setAcreditacionExencion(acreditacionExencion);
				} else if(TRUE.equals(tramite.getNoSujetoItp())) {
					//Acreditación de no sujección
					trafico.beans.schemas.generated.transTelematica.DatosEspecificos.AcreditacionFiscal.ITP.AcreditacionNoSujecion acreditacionNoSujecion = objFactory.createDatosEspecificosAcreditacionFiscalITPAcreditacionNoSujecion();
					acreditacionNoSujecion.setModelo(!"-1".equals(tramite.getModeloItp())?tramite.getModeloItp():CADENA_VACIA);
					acreditacionNoSujecion.setNoSujecion(TipoSI.SI);
					TipoCEM cetItp = objFactory.createTipoCEM();

					cetItp.setCodigoElectronico(tramite.getCetItp());

					cetItp.setCodigoProvincia(provinciaImpuestosCet);
					acreditacionNoSujecion.setCET(cetItp);
					//Seteamos la acreditación de no sujección
					itp.setAcreditacionNoSujecion(acreditacionNoSujecion);
				}
				//Seteamos el ITP
				acreditacionFiscal.setITP(itp);
			}
		}
		//ISD
		if (tramite.getModoAdjudicacion() != null && tramite.getModoAdjudicacion().getValorEnum().equals(ModoAdjudicacion.tipo3.getValorEnum())
				&& (tramite.getFactura()==null || CADENA_VACIA.equals(tramite.getFactura()))) {
			ISD isd = null;
			//Acreditación Pago
			/*if(!TRUE.equals(tramite.getExentoIsd()) && !TRUE.equals(tramite.getNoSujetoIsd())){
				trafico.beans.schemas.generated.transTelematica.DatosEspecificos.AcreditacionFiscal.ISD.AcreditacionPago acreditacionPagoISD = objFactory.createDatosEspecificosAcreditacionFiscalISDAcreditacionPago();
				acreditacionPagoISD.setModelo(tramite.getModeloIsd() != null && !"-1".equals(tramite.getModeloIsd())?tramite.getModeloIsd():CADENA_VACIA);
				acreditacionPagoISD.setNumeroAutoliquidacion(tramite.getNumAutoIsd() != null ? tramite.getNumAutoIsd() : CADENA_VACIA);
				//Seteamos la acreditación de pago
				isd.setAcreditacionPago(acreditacionPagoISD);
			}*/
			if (TRUE.equals(tramite.getExentoIsd())) {
				isd = objFactory.createDatosEspecificosAcreditacionFiscalISD();
				//Acreditación de exención
				trafico.beans.schemas.generated.transTelematica.DatosEspecificos.AcreditacionFiscal.ISD.AcreditacionExencion acreditacionExencion = objFactory.createDatosEspecificosAcreditacionFiscalISDAcreditacionExencion();
				acreditacionExencion.setModelo(tramite.getModeloIsd() != null && !"-1".equals(tramite.getModeloIsd())?tramite.getModeloIsd():CADENA_VACIA);
				acreditacionExencion.setExencion(TipoSI.SI);
				//Seteamos la acreditación de exención
				isd.setAcreditacionExencion(acreditacionExencion);
			} else if (TRUE.equals(tramite.getNoSujetoIsd())) {
				isd = objFactory.createDatosEspecificosAcreditacionFiscalISD();
				// Acreditación de no sujección
				trafico.beans.schemas.generated.transTelematica.DatosEspecificos.AcreditacionFiscal.ISD.AcreditacionNoSujecion acreditacionNoSujecion = objFactory.createDatosEspecificosAcreditacionFiscalISDAcreditacionNoSujecion();
				acreditacionNoSujecion.setModelo(tramite.getModeloIsd() != null && !"-1".equals(tramite.getModeloIsd())?tramite.getModeloIsd():CADENA_VACIA);
				acreditacionNoSujecion.setNoSujecion(TipoSI.SI);
				// Seteamos la acreditación de no sujección
				isd.setAcreditacionNoSujecion(acreditacionNoSujecion);
			}
			//Seteamos el ISD
			if (isd != null) {
				acreditacionFiscal.setISD(isd);
			}
		}
		String provinciaImpuestosCem = "00";
		String codigoElectronicoMat = null!=tramite.getTramiteTraficoBean().getCemIedtm() && !CADENA_VACIA.equals(tramite.getTramiteTraficoBean().getCemIedtm())?tramite.getTramiteTraficoBean().getCemIedtm():_00000000;
		if(!codigoElectronicoMat.equals(_00000000)){
			provinciaImpuestosCem = provinciaImpuestos;
		}
		//IEDTM
		IEDMT iedmt = objFactory.createDatosEspecificosAcreditacionFiscalIEDMT();
		//Acreditación Pago
		if(!TRUE.equals(tramite.getTramiteTraficoBean().getExentoIedtm()) && !TRUE.equals(tramite.getTramiteTraficoBean().getNoSujetoIedtm())){
			trafico.beans.schemas.generated.transTelematica.DatosEspecificos.AcreditacionFiscal.IEDMT.AcreditacionPago acreditacionPagoIEDMT = objFactory.createDatosEspecificosAcreditacionFiscalIEDMTAcreditacionPago();
			acreditacionPagoIEDMT.setModelo(null!=tramite.getTramiteTraficoBean().getModeloIedtm() && !"-1".equals(tramite.getTramiteTraficoBean().getModeloIedtm())?tramite.getTramiteTraficoBean().getModeloIedtm():"576");
			TipoCEM cem = objFactory.createTipoCEM();
			cem.setCodigoElectronico(codigoElectronicoMat);
			// DRC@24-01-2013 Incidencia Mantia: 3437
			if (tramite != null && tramite.getProvinciaCem() != null && tramite.getProvinciaCem().getIdProvincia() != null
					&& !tramite.getProvinciaCem().getIdProvincia().equalsIgnoreCase("-1")){
				cem.setCodigoProvincia(tramite.getProvinciaCem().getIdProvincia());
			} else {
				cem.setCodigoProvincia(provinciaImpuestosCem);
			}
			acreditacionPagoIEDMT.setCEM(cem);
			//Seteamos la acreditación de pago
			iedmt.setAcreditacionPago(acreditacionPagoIEDMT);
		}
		if(TRUE.equals(tramite.getTramiteTraficoBean().getExentoIedtm())){
			//Acreditación de exención
			trafico.beans.schemas.generated.transTelematica.DatosEspecificos.AcreditacionFiscal.IEDMT.AcreditacionExencion acreditacionExencion = objFactory.createDatosEspecificosAcreditacionFiscalIEDMTAcreditacionExencion();
			acreditacionExencion.setModelo(!"-1".equals(tramite.getTramiteTraficoBean().getModeloIedtm()) ? tramite.getTramiteTraficoBean().getModeloIedtm() : "576");
			acreditacionExencion.setExencion(TipoSI.SI);
			TipoCEM cem = objFactory.createTipoCEM();
			cem.setCodigoElectronico(codigoElectronicoMat);

			// DRC@24-01-2013 Incidencia Mantia: 3437
			if (tramite != null && tramite.getProvinciaCem() != null && tramite.getProvinciaCem().getIdProvincia() != null)
				cem.setCodigoProvincia(tramite.getProvinciaCem().getIdProvincia());
			else
				cem.setCodigoProvincia(provinciaImpuestosCem);
			acreditacionExencion.setCEM(cem);
			//Seteamos la acreditación de exención
			iedmt.setAcreditacionExencion(acreditacionExencion);
		} else if(TRUE.equals(tramite.getTramiteTraficoBean().getNoSujetoIedtm())){
			//Acreditación de no sujección
			trafico.beans.schemas.generated.transTelematica.DatosEspecificos.AcreditacionFiscal.IEDMT.AcreditacionNoSujecion acreditacionNoSujecion = objFactory.createDatosEspecificosAcreditacionFiscalIEDMTAcreditacionNoSujecion();
			acreditacionNoSujecion.setModelo(!"-1".equals(tramite.getTramiteTraficoBean().getModeloIedtm()) ? tramite.getTramiteTraficoBean().getModeloIedtm() : "576");
			acreditacionNoSujecion.setNoSujecion(TipoSI.SI);
			TipoCEM cem = objFactory.createTipoCEM();
			cem.setCodigoElectronico(codigoElectronicoMat);

			// DRC@24-01-2013 Incidencia Mantia: 3437
			if (tramite != null && tramite.getProvinciaCem() != null && tramite.getProvinciaCem().getIdProvincia() != null)
				cem.setCodigoProvincia(tramite.getProvinciaCem().getIdProvincia());
			else
				cem.setCodigoProvincia(provinciaImpuestosCem);
			acreditacionNoSujecion.setCEM(cem);
			//Seteamos la acreditación de no sujección
			iedmt.setAcreditacionNoSujecion(acreditacionNoSujecion);

			if(tramite.getTramiteTraficoBean().getCemIedtm() == _00000000){
				tramite.getProvinciaCem().setIdProvincia("00");
			}
		}

		//Seteamos el IEDMT
		acreditacionFiscal.setIEDMT(iedmt);

		//IVTM
		IVTM ivtm = objFactory.createDatosEspecificosAcreditacionFiscalIVTM();
		ivtm.setAltaIVTM(TRUE.equals(tramite.getAltaIvtm()) ? TipoSINO.SI : TipoSINO.NO);

		//DVV: Acreditación de pago IVTM
		

		//Seteamos el IVTM
		acreditacionFiscal.setIVTM(ivtm);

		//Seteamos
		datosEspecificos.setAcreditacionFiscal(acreditacionFiscal);

		//Acreditación Actividad
		AcreditacionActividad acreditacionActividad = objFactory.createDatosEspecificosAcreditacionActividad();
		acreditacionActividad.setVehiculosAgricolas(Boolean.TRUE.equals(tramite.getTramiteTraficoBean().getVehiculo().getVehiculoAgricola())?TipoSINO.SI:TipoSINO.NO);

		//Seteamos
		datosEspecificos.setAcreditacionActividad(acreditacionActividad);

		//Texto Legal
		datosEspecificos.setTextoLegal(TipoTextoLegal.ESTE_COLEGIO_DE_GESTORES_ADMINISTRATIVOS_HA_VERIFICADO_QUE_LA_SOLICITUD_DE_CAMBIO_DE_TITULARIDAD_PRESENTADA_CUMPLE_TODAS_LAS_OBLIGACIONES_ESTABLECIDAS_EN_EL_REGLAMENTO_GENERAL_DE_VEHÍCULOS_Y_RESTO_DE_NORMATIVA_APLICABLE_ASÍ_COMO_LAS_DERIVADAS_DE_LA_APLICACIÓN_DE_LAS_INSTRUCCIONES_DE_LA_DIRECCIÓN_GENERAL_DE_TRÁFICO_VIGENTES_EN_EL_MOMENTO_DE_LA_SOLICITUD_Y_DEL_PROTOCOLO_DE_CAMBIO_DE_TITULARIDAD_FIRMADO_ENTRE_EL_CONSEJO_GENERAL_DE_COLEGIOS_DE_GESTORES_ADMINISTRATIVOS_Y_LA_DIRECCIÓN_GENERAL_DE_TRÁFICO);

		//Firma Gestor
		datosEspecificos.setFirmaGestor(new byte[0]);

		//Seteamos los DATOS ESPECIFICOS
		datosFirmados.setDatosEspecificos(datosEspecificos);

		if(null!=tramite.getFactura() && !CADENA_VACIA.equals(tramite.getFactura())){
			acreditacionFiscal.setIVA(TipoSI.SI);
			if (datosFirmados.getDatosEspecificos().getAcreditacionFiscal()!=null
					&&datosFirmados.getDatosEspecificos().getAcreditacionFiscal().getITP()!=null
						&&datosFirmados.getDatosEspecificos().getAcreditacionFiscal().getITP().getAcreditacionPago()!=null) {
				datosFirmados.getDatosEspecificos().getAcreditacionFiscal().getITP().getAcreditacionPago().setCET(null);
			}
		}

		//Seteamos los DATOS FIRMADOS y los DATOS ESPECIFICOS
		solRegEntrada.setDatosFirmados(datosFirmados);

		//Seteamos la FIRMA
		solRegEntrada.setFirma(new byte[0]);

		//Seteamos la VERSION
		solRegEntrada.setVersion(_1_0);

		ResultBean resultadoFirmasBean = new ResultBean();

		try {
			resultadoFirmasBean = anhadirFirmasTransTelematicaHSM(solRegEntrada,numColegiado);
		} catch (Exception e) {
			resultadoFirmasBean.setError(true);
			log.error(e);
		}

		resultado.put(SOLICITUD_REGISTRO_ENTRADA, resultadoFirmasBean.getAttachment(SOLICITUD_REGISTRO_ENTRADA));

		String xmlFirmado = null;
		try {
			XmlTransTelematicaFactory xmlFactory = new XmlTransTelematicaFactory();
			xmlFirmado = xmlFactory.toXML(resultadoFirmasBean.getAttachment(SOLICITUD_REGISTRO_ENTRADA));
		} catch (JAXBException e) {
			log.error("Error generando XML firmado", e, tramite.getTramiteTraficoBean().getNumExpediente().toString());
		}

		BigDecimal numExpediente = tramite.getTramiteTraficoBean().getNumExpediente();
		FicheroBean ficheroBean = new FicheroBean();
		ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
		ficheroBean.setTipoDocumento(ConstantesGestorFicheros.CTIT);
		ficheroBean.setSubTipo(ConstantesGestorFicheros.CTITENVIO);
		ficheroBean.setNombreDocumento(ConstantesGestorFicheros.NOMBRE_CTIT + String.valueOf(numExpediente));
		ficheroBean.setFecha(Utilidades.transformExpedienteFecha(numExpediente));
		ficheroBean.setSobreescribir(true);

		gestorDocumentos.crearFicheroXml(ficheroBean, XmlTransTelematicaFactory.NAME_CONTEXT, resultadoFirmasBean.getAttachment(SOLICITUD_REGISTRO_ENTRADA), xmlFirmado, null);

		resultado.put(FICHERO, ficheroBean.getFichero().getPath());
		return resultado;
	}

	public ResultBean anhadirFirmasTransNoTelematicaHSM(trafico.beans.jaxb.transNoTelematica.FORMATOGA solicitudRegistroEntrada,String numColegiado) throws Exception {
		ResultBean resultFirmasBean=new ResultBean();
		ResultBean resultBeanFirmaColegiado = realizarFirmaDatosFirmadosNoTele(solicitudRegistroEntrada,false,numColegiado);
		if (!resultBeanFirmaColegiado.getError()) {
			resultBeanFirmaColegiado.addAttachment(SOLICITUD_REGISTRO_ENTRADA, solicitudRegistroEntrada);
			resultBeanFirmaColegiado.addAttachment(FIRMA_COLEGIADO, resultBeanFirmaColegiado.getMensaje());
			ResultBean resultBeanSolicitudConfirmaColegiado = anhadirFirmaColegiadoGA(resultBeanFirmaColegiado);
			if(!resultBeanSolicitudConfirmaColegiado.getError()){
				/**
				 * Firma del XML por el Colegio
				 */
				ResultBean resultBeanConFirmas = firmarColegioGA(
						(trafico.beans.jaxb.transNoTelematica.FORMATOGA)resultBeanSolicitudConfirmaColegiado.getAttachment(SOLICITUD_REGISTRO_ENTRADA), numColegiado);

				if(!resultBeanConFirmas.getError()){
					resultFirmasBean.addAttachment(SOLICITUD_REGISTRO_ENTRADA, (trafico.beans.jaxb.transNoTelematica.FORMATOGA)resultBeanConFirmas.getAttachment(SOLICITUD_REGISTRO_ENTRADA));
				} else{
					resultFirmasBean.setError(true);
					resultFirmasBean.setMensaje(ERROR_AL_FIRMAR_CON_EL_COLEGIO);
				}
			} else{
				resultFirmasBean.setError(true);
				resultFirmasBean.setMensaje(ERROR_AL_FIRMAR_CON_EL_COLEGIADO);
			}
		} else {
			resultFirmasBean.setError(true);
			resultFirmasBean.setMensaje(ERROR_AL_FIRMAR_CON_EL_COLEGIADO);
		}
		return resultFirmasBean;
	}

	/**
	 * @author antonio.miguez
	 * @param SolicitudRegistroEntrada
	 * @throws Exception
	 * @return ResultBean
	 */
	/*
	 * Método para añadir las firmas de colegio y colegiado al XML para la llamada a CTIT
	 */
	public ResultBean anhadirFirmasTransTelematicaHSM(SolicitudRegistroEntrada solicitudRegistroEntrada,String numColegiado) throws Exception {
		ResultBean resultFirmasBean = new ResultBean();

		/**
		 * Firma del XML por el Colegiado
		 */
		ResultBean resultBeanFirmaColegiado = realizarFirmaDatosFirmados(solicitudRegistroEntrada, false, numColegiado);
		if (!resultBeanFirmaColegiado.getError()) {
			resultBeanFirmaColegiado.addAttachment(SOLICITUD_REGISTRO_ENTRADA, solicitudRegistroEntrada);
			resultBeanFirmaColegiado.addAttachment(FIRMA_COLEGIADO, resultBeanFirmaColegiado.getMensaje());
			ResultBean resultBeanSolicitudConfirmaColegiado = anhadirFirmaColegiado(resultBeanFirmaColegiado);
			if(!resultBeanSolicitudConfirmaColegiado.getError()){
				/**
				 * Firma del XML por el Colegio
				 */
				ResultBean resultBeanConFirmas = firmarColegio(
						(SolicitudRegistroEntrada)resultBeanSolicitudConfirmaColegiado.getAttachment(SOLICITUD_REGISTRO_ENTRADA), numColegiado);

				if(!resultBeanConFirmas.getError()){
					resultFirmasBean.addAttachment(SOLICITUD_REGISTRO_ENTRADA, (SolicitudRegistroEntrada)resultBeanConFirmas.getAttachment(SOLICITUD_REGISTRO_ENTRADA));
				} else{
					resultFirmasBean.setError(true);
					resultFirmasBean.setMensaje(ERROR_AL_FIRMAR_CON_EL_COLEGIO);
				}
			} else{
				resultFirmasBean.setError(true);
				resultFirmasBean.setMensaje(ERROR_AL_FIRMAR_CON_EL_COLEGIADO);
			}
		} else {
			resultFirmasBean.setError(true);
			resultFirmasBean.setMensaje(ERROR_AL_FIRMAR_CON_EL_COLEGIADO);
		}
		return resultFirmasBean;
	}

	/**
	 * @author antonio.miguez
	 * @param SolicitudTramite, HttpServletRequest, HttpServletResponse
	 * @throws Exception
	 * @return ResultBean
	 */
	/*
	 * Método para añadir las firmas de colegio y colegiado al XML para la llamada a CTIT
	 */
	private ResultBean anhadirFirmasCheckCTITHSM(SolicitudTramite solicitudTramite,String numColegiado) throws Exception {
		ResultBean resultFirmasBean=new ResultBean();

		/**
		 *	Firma del XML por el Colegiado
		 */
		ResultBean resultBeanFirmaColegiado = realizarFirmaDatosFirmadosCheckCTIT(solicitudTramite,numColegiado);
		if (!resultBeanFirmaColegiado.getError()) {
			resultBeanFirmaColegiado.addAttachment(SOLICITUD_TRAMITE, solicitudTramite);
			resultBeanFirmaColegiado.addAttachment(FIRMA_COLEGIADO, resultBeanFirmaColegiado.getMensaje());
			ResultBean resultBeanSolicitudConfirmaColegiado = anhadirFirmaColegiadoCheckCTIT(resultBeanFirmaColegiado);
			if(!resultBeanSolicitudConfirmaColegiado.getError()){
				resultFirmasBean.addAttachment(SOLICITUD_TRAMITE, (SolicitudTramite)resultBeanSolicitudConfirmaColegiado.getAttachment(SOLICITUD_TRAMITE));
			} else{
				resultFirmasBean.setError(true);
				resultFirmasBean.setMensaje(ERROR_AL_FIRMAR_CON_EL_COLEGIADO);
			}
		} else {
			resultFirmasBean.setError(true);
			resultFirmasBean.setMensaje(ERROR_AL_FIRMAR_CON_EL_COLEGIADO);
		}

		return resultFirmasBean;
	}

	private ResultBean realizarFirmaDatosFirmadosNoTele(trafico.beans.jaxb.transNoTelematica.FORMATOGA solicitudRegistroEntrada, boolean colegio, String numColegiado){
		String xml = new XMLTransmisionNoTelematicaFactory().toXML(solicitudRegistroEntrada);
		// Quitamos los namespaces porque da error
		xml = xml.replaceAll("<ns2:", "<");
		xml = xml.replaceAll("</ns2:", "</");
		//xml = xml.replaceAll("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" standalone=\"yes\"?>", CADENA_VACIA);

		// Obtenemos los datos que realmente se tienen que firmar
		int inicio = xml.indexOf("<FORMATO_GA")+76;
		int fin = xml.indexOf("</FORMATO_GA>");
		String datosFirmados = xml.substring(inicio, fin);
		log.info("TRANSMISION_NO_TELEMATICA DATOS FIRMADOS "+datosFirmados);
		log.info("Datos a firmar:" + datosFirmados);

		// Se codifican los datos a firmar en base 64
		String encodedAFirmar = CADENA_VACIA;

		try{
			encodedAFirmar = utiles.doBase64Encode(datosFirmados.getBytes(StandardCharsets.UTF_8));
			encodedAFirmar = encodedAFirmar.replaceAll("\n", CADENA_VACIA);
			log.info("LOG_CTIT ENCODED A FIRMAR: "+encodedAFirmar);
		} catch(Exception e) {	
			log.error("Error al codificar en base 64 el UTF-8");
		}

		// Se construye el XML que contiene los datos a firmar
		String xmlAFirmar = "<AFIRMA><CONTENT Id=\"D0\">" + encodedAFirmar + "</CONTENT></AFIRMA>";
		log.info("XML a firmar:" + xmlAFirmar);

		return firmar(xmlAFirmar,colegio,numColegiado);
	}

	/**
	 * @author antonio.miguez
	 * @param SolicitudRegistroEntrada, boolean, HttpServletRequest, HttpServletResponse
	 * @return ResultBean
	 */
	/*
	 * Método para generar las firmas de colegio y colegiado
	 */
	private ResultBean realizarFirmaDatosFirmados(SolicitudRegistroEntrada solicitudRegistroEntrada, boolean colegio, String numColegiado){
		XmlTransTelematicaFactory xmlFullCTITFactory = new XmlTransTelematicaFactory();
		String xml = xmlFullCTITFactory.toXML(solicitudRegistroEntrada);
		//Quitamos los namespaces porque da error
		xml = xml.replaceAll("<ns2:", "<");
		xml = xml.replaceAll("</ns2:", "</");

		//Obtenemos los datos que realmente se tienen que firmar
		int inicio = xml.indexOf("<Datos_Firmados>") + 16;
		int fin = xml.indexOf("</Datos_Firmados>");
		String datosFirmados = xml.substring(inicio, fin);
		log.info("LOG_CTIT DATOS FIRMADOS "+datosFirmados);
		log.info("Datos a firmar:" + datosFirmados);

		//Se codifican los datos a firmar en base 64
		String encodedAFirmar = CADENA_VACIA;

		try{
			encodedAFirmar = utiles.doBase64Encode(datosFirmados.getBytes(StandardCharsets.UTF_8));
			encodedAFirmar = encodedAFirmar.replaceAll("\n", CADENA_VACIA);
			log.info("LOG_CTIT ENCODED A FIRMAR: "+encodedAFirmar);
		} catch(Exception e) {	
			log.error("Error al codificar en base 64 el UTF-8");
		}

		//Se construye el XML que contiene los datos a firmar
		String xmlAFirmar = "<AFIRMA><CONTENT Id=\"D0\">" + encodedAFirmar + "</CONTENT></AFIRMA>";
		log.info("XML a firmar:" + xmlAFirmar);

		return firmar(xmlAFirmar, colegio, numColegiado);
	}

	/**
	 * @author antonio.miguez
	 * @param SolicitudTramite, boolean, HttpServletRequest, HttpServletResponse
	 * @return ResultBean
	 */
	/*
	 * Método para generar las firmas de colegio y colegiado
	 */
	private ResultBean realizarFirmaDatosFirmadosCheckCTIT(SolicitudTramite solicitudTramite,String numColegiado){
		XmlCheckCTITFactory xmlCheckCTITFactory = new XmlCheckCTITFactory();
		String xml = xmlCheckCTITFactory.toXML(solicitudTramite);
		//Quitamos los namespaces porque da error
		xml = xml.replace("<ns2:", "<");
		xml = xml.replaceAll("</ns2:", "</");

		//Obtenemos los datos que realmente se tienen que firmar
		int inicio = xml.indexOf("<Datos_Firmados>") + 16;
		int fin = xml.indexOf("</Datos_Firmados>");
		String datosFirmados = xml.substring(inicio, fin);
		log.info("LOG_CTIT DATOS FIRMADOS "+datosFirmados);
		log.info("Datos a firmar:" + datosFirmados);

		//Se codifican los datos a firmar en base 64
		String encodedAFirmar = CADENA_VACIA;

		try{
			encodedAFirmar = utiles.doBase64Encode(datosFirmados.getBytes(StandardCharsets.UTF_8));
			encodedAFirmar = encodedAFirmar.replaceAll("\n", CADENA_VACIA);
			log.info("LOG_CTIT ENCODED A FIRMAR: "+encodedAFirmar);
		} catch(Exception e) {	
			log.error("Error al codificar en base 64 el UTF-8");
		}

		//Se construye el XML que contiene los datos a firmar
		String xmlAFirmar = "<AFIRMA><CONTENT Id=\"D0\">" + encodedAFirmar + "</CONTENT></AFIRMA>";
		log.info("XML a firmar:" + xmlAFirmar);

		return firmarCheckCTIT(xmlAFirmar,numColegiado);
	}

	/**
	 * @author antonio.miguez
	 * @param SolicitudRegistroEntrada, boolean, HttpServletRequest, HttpServletResponse
	 * @return ResultBean
	 */
	/*
	 * Método para firmar un XML por el colegio o el colegiado
	 */
	private ResultBean firmar(String cadenaXML,boolean colegio, String numColegiado){
		UtilesViafirma utilesViafirma=new UtilesViafirma();
		ResultBean resultBean = new ResultBean();
		// Antes de guardar el trámite se ha de firmar en servidor para que se guarde el
		// identificador de la firma en parametrosBusqueda como idFirmaDoc

		// Se firma en servidor para obtener un identificador que permita la recuperación del documento original
		// para cuando lo firmen los certificantes:
		String idFirma = CADENA_VACIA;
		try {
			if (colegio) {
				idFirma = utilesViafirma.firmarMensajeTransmisionServidor(cadenaXML, gestorPropiedades.valorPropertie("trafico.claves.colegio.alias"));
			} else {
				idFirma = utilesViafirma.firmarMensajeTransmisionServidor(cadenaXML, utilesColegiado.getAlias(numColegiado));
			}

			resultBean.setError(false);
			//resultBean.setMensaje(idFirma);
			log.info(ID_FIRMA + idFirma);
		} catch (Exception e) {
			resultBean.setMensaje("Ha ocurrido un error durante el proceso de firma en servidor del trámite");
			resultBean.setError(true);
			log.error(e);
			return resultBean;
		}

		if(idFirma.equals(ERROR)) {
			// Ha fallado el proceso de firma en servidor
			resultBean.setMensaje("Ha ocurrido un error durante el proceso de firma en servidor del trámite");
			resultBean.setError(true);
			return resultBean;
		} else {// Firma correctamente
			try {
				if (!CADENA_VACIA.equals(idFirma)) {
					String xmlFirmado = utilesViafirma.getDocumentoFirmado(idFirma);
					log.info("xml custodiado recuperado a traves del idFirma=" +idFirma);
					log.debug("xml firmado : " + xmlFirmado);
					resultBean.addAttachment("idFirma", idFirma);
					resultBean.setMensaje(xmlFirmado);
					resultBean.setError(false);
				} else {
					resultBean.setError(false); // Firmó el colegiado, por lo tanto aquí no tenemos idFirma.
				}
			/*}catch (OegamExcepcion e) {
				log.info("Ha ocurrido un error recuperando el xml firmado a traves del id de firma");
				resultBean.setMensaje("Ha ocurrido un error recuperando el xml firmado a traves del id de firma");
				resultBean.setError(true);
				log.error(e);
				*/
			} catch (Exception e) {
				log.error(e);
			}
		}

		return resultBean;
	}

	/**
	 * @author antonio.miguez
	 * @param String, boolean, HttpServletRequest, HttpServletResponse
	 * @return ResultBean
	 */
	/*
	 * Método para firmar un xml por el colegio o el colegiado
	 */
	private ResultBean firmarCheckCTIT(String cadenaXML,String numColegiado){
		UtilesViafirma utilesViafirma=new UtilesViafirma();
		ResultBean resultBean = new ResultBean();
		// Antes de guardar el trámite se ha de firmar en servidor para que se guarde el
		// identificador de la firma en parametrosBusqueda como idFirmaDoc

		// Se firma en servidor para obtener un identificador que permita la recuperación del documento original
		// para cuando lo firmen los certificantes:
		String idFirma = CADENA_VACIA;
		try {
			idFirma = utilesViafirma.firmarMensajeTransmisionServidor(cadenaXML, utilesColegiado.getAlias(numColegiado));
			resultBean.setError(false);
			resultBean.setMensaje(idFirma);
			log.info(ID_FIRMA + idFirma);
		} catch (Exception e) {
			resultBean.setMensaje("Ha ocurrido un error durante el proceso de firma en servidor del trámite");
			resultBean.setError(true);
			log.error(e);
			return resultBean;
		}

		if(idFirma.equals(ERROR)) {
			// Ha fallado el proceso de firma en servidor
			resultBean.setMensaje("Ha ocurrido un error durante el proceso de firma en servidor del trámite");
			resultBean.setError(true);
			return resultBean;
		} else {// Firma correctamente
			try {
				if (!CADENA_VACIA.equals(idFirma)) {
					String xmlFirmado = utilesViafirma.getDocumentoFirmado(idFirma);
					log.info("xml custodiado recuperado a traves del idFirma=" +idFirma);
					log.debug("xml firmado : " + xmlFirmado);
					resultBean.addAttachment("idFirma", idFirma);
					resultBean.setMensaje(new String(xmlFirmado));
					resultBean.setError(false);
				} else {
					resultBean.setError(false); // Firmó el colegiado, por lo tanto aquí no tenemos idFirma.
				}
			} catch (Exception e) {
				log.error(e);
			}
		}
		return resultBean;
	}

	/**
	 * @author antonio.miguez
	 * @param ResultBean
	 * @return ResultBean
	 */
	/*
	 * Método para añadir la firma del colegiado a una SolicitudRegistroEntrada
	 */
	public ResultBean anhadirFirmaColegiado(ResultBean resultBeanFirmaColegiado){
		String xmlFirmadoColegiado = (String) resultBeanFirmaColegiado.getAttachment(FIRMA_COLEGIADO);
		SolicitudRegistroEntrada solicitudRegistroEntrada = (SolicitudRegistroEntrada) resultBeanFirmaColegiado.getAttachment(SOLICITUD_REGISTRO_ENTRADA);
		log.info("XML procedente de viafirma firmado por el Colegiado:" + xmlFirmadoColegiado);
		ResultBean resultBeanConFirmaColegiado = new ResultBean();

		/**
		 * Se añade la firma del gestor al Registro de Entrada
		 */
		try{
			solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setFirmaGestor(xmlFirmadoColegiado.getBytes(StandardCharsets.UTF_8));
			resultBeanConFirmaColegiado.addAttachment(SOLICITUD_REGISTRO_ENTRADA, solicitudRegistroEntrada);
			log.info("añadida firma del gestor al registro de entrada:" + xmlFirmadoColegiado);
		} catch(Exception e){
			log.error(ERROR_AL_OBTENER_BYTES_UTF_8);
		}
		return resultBeanConFirmaColegiado;
	}

	public ResultBean anhadirFirmaColegiadoGA(ResultBean resultBeanFirmaColegiado){
		String xmlFirmadoColegiado = (String) resultBeanFirmaColegiado.getAttachment(FIRMA_COLEGIADO);
		trafico.beans.jaxb.transNoTelematica.FORMATOGA solicitudRegistroEntrada = (trafico.beans.jaxb.transNoTelematica.FORMATOGA) resultBeanFirmaColegiado.getAttachment(SOLICITUD_REGISTRO_ENTRADA);
		log.info("XML procedente de viafirma firmado por el Colegiado:" + xmlFirmadoColegiado);
		ResultBean resultBeanConFirmaColegiado = new ResultBean();

		/**
		 * Se añade la firma del gestor al Registro de Entrada
		 */
		try{
			solicitudRegistroEntrada.getFIRMA().setFIRMAGESTOR(xmlFirmadoColegiado.getBytes(StandardCharsets.UTF_8));
			resultBeanConFirmaColegiado.addAttachment(SOLICITUD_REGISTRO_ENTRADA, solicitudRegistroEntrada);
			log.info("añadida firma del gestor al registro de entrada:" + xmlFirmadoColegiado);
		} catch(Exception e){
			log.error(ERROR_AL_OBTENER_BYTES_UTF_8);
		}
		return resultBeanConFirmaColegiado;
	}

	/**
	 * @author antonio.miguez
	 * @param ResultBean
	 * @return ResultBean
	 */
	/*
	 * Método para añadir la firma del colegiado a una SolicitudTramite
	 */
	public ResultBean anhadirFirmaColegiadoCheckCTIT(ResultBean resultBeanFirmaColegiado){
		String xmlFirmadoColegiado = (String) resultBeanFirmaColegiado.getAttachment(FIRMA_COLEGIADO);
		SolicitudTramite solicitudTramite = (SolicitudTramite) resultBeanFirmaColegiado.getAttachment(SOLICITUD_TRAMITE);
		log.info("XML procedente de viafirma firmado por el Colegiado:" + xmlFirmadoColegiado);
		ResultBean resultBeanConFirmaColegiado = new ResultBean();

		/**
		 * Se añade la firma del gestor al Registro de Entrada
		 */
		try{
			solicitudTramite.setFirmaGestor(xmlFirmadoColegiado.getBytes(StandardCharsets.UTF_8));
			resultBeanConFirmaColegiado.addAttachment(SOLICITUD_TRAMITE, solicitudTramite);
			resultBeanConFirmaColegiado.setMensaje(xmlFirmadoColegiado);
			log.info("añadida firma del gestor al registro de entrada:" + xmlFirmadoColegiado);
		} catch(Exception e){
			log.error(ERROR_AL_OBTENER_BYTES_UTF_8);
		}

		return resultBeanConFirmaColegiado;
	}

	/**
	 * @author antonio.miguez
	 * @param SolicitudRegistroEntrada, HttpServletRequest, HttpServletResponse
	 * @return ResultBean
	 */
	/*
	 * Método para firmar por el colegio una SolicitudRegistroEntrada
	 */
	public ResultBean firmarColegio(SolicitudRegistroEntrada solicitudRegistroEntrada,String numColegiado){
		/**
		 * Firma del XML por el Colegio
		 */
		log.info(FIRMAR_COLEGIO);
		ResultBean resultBeanFirmaColegio = realizarFirmaDatosFirmados(solicitudRegistroEntrada, true, numColegiado);
		String xmlFirmadoColegio = resultBeanFirmaColegio.getMensaje();
		log.debug(XML_FIRMADO_EN_SERVIDOR_POR_EL_COLEGIO);
		log.debug(xmlFirmadoColegio);

		if(xmlFirmadoColegio!=null){
			/**
			 * Se añade la firma del colegio al Registro de Entrada
			 */
			try {
				solicitudRegistroEntrada.setFirma(xmlFirmadoColegio.getBytes(StandardCharsets.UTF_8));
				log.info(ANADIDA_FIRMA_DEL_COLEGIO_AL_REGISTRO_DE_ENTRADA + xmlFirmadoColegio.getBytes(StandardCharsets.UTF_8));
				resultBeanFirmaColegio.addAttachment(SOLICITUD_REGISTRO_ENTRADA, solicitudRegistroEntrada);
			} catch(Exception e) {
				log.error(ERROR_AL_OBTENER_BYTES_UTF_8);
			}
		} else {
			resultBeanFirmaColegio.setError(true);
			resultBeanFirmaColegio.setMensaje("Error al firmar el colegio");
		}
		return resultBeanFirmaColegio;
	}

	public ResultBean firmarColegioGA(trafico.beans.jaxb.transNoTelematica.FORMATOGA solicitudRegistroEntrada, String numColegiado) {
		/**
		 * Firma del XML por el Colegio
		 */
		log.info(FIRMAR_COLEGIO);
		ResultBean resultBeanFirmaColegio = realizarFirmaDatosFirmadosNoTele(solicitudRegistroEntrada, true, numColegiado);
		String xmlFirmadoColegio = resultBeanFirmaColegio.getMensaje();
		log.debug(XML_FIRMADO_EN_SERVIDOR_POR_EL_COLEGIO);
		log.debug(xmlFirmadoColegio);

		if(xmlFirmadoColegio!=null){
			/**
			 * Se añade la firma del colegio al Registro de Entrada
			 */
			try {
				solicitudRegistroEntrada.getFIRMA().setFIRMACOLEGIO(xmlFirmadoColegio.getBytes(StandardCharsets.UTF_8));
				log.info(ANADIDA_FIRMA_DEL_COLEGIO_AL_REGISTRO_DE_ENTRADA + xmlFirmadoColegio.getBytes(StandardCharsets.UTF_8));
				resultBeanFirmaColegio.addAttachment(SOLICITUD_REGISTRO_ENTRADA, solicitudRegistroEntrada);
			} catch(Exception e) {
				log.error(ERROR_AL_OBTENER_BYTES_UTF_8);
			}
		} else {
			resultBeanFirmaColegio.setError(true);
			resultBeanFirmaColegio.setMensaje("Error al firmar el colegio");
		}
		return resultBeanFirmaColegio;
	}

	public boolean comprobarDireccionCorrecta(DireccionVO direccion) {
		if (utiles.convertirCombo(direccion.getIdMunicipio()) == null
				|| utiles.convertirCombo(direccion.getIdProvincia()) == null
				|| utiles.convertirCombo(direccion.getIdTipoVia()) == null
				|| utiles.convertirNulltoString(direccion.getNombreVia()) == null
				|| utiles.convertirNulltoString(direccion.getNumero()) == null) {
			return false;
		}
		return true;
	}

	/**
	 * @author antonio.miguez
	 * @param TramiteTraficoTransmisionBean
	 * @return ResultBean
	 */
	public ResultBean validar(TramiteTraficoTransmisionBean tramiteTraficoTransmisionBean){
		ResultBean resultBean = new ResultBean();
		resultBean.setError(false);
		List<String> errores = new ArrayList<>();
		BigDecimal numExpediente = BigDecimal.ZERO;

		// Inicio de validación por territorialidad
		if (!validarTerritorialidad(tramiteTraficoTransmisionBean)) {
			String mensajeTerritorialidad = gestorPropiedades.valorPropertie("territorialidad.ctit.exclusiones.mensaje");
			resultBean.setError(true);
			resultBean.setMensaje(mensajeTerritorialidad);
			return resultBean;
		}
		if (tramiteTraficoTransmisionBean.getTramiteTraficoBean().getVehiculo() != null
				&& tramiteTraficoTransmisionBean.getTramiteTraficoBean().getVehiculo().getVehiculoTramiteTraficoBean() != null
				&& tramiteTraficoTransmisionBean.getTramiteTraficoBean().getVehiculo().getVehiculoTramiteTraficoBean().getKilometros() != null
				&& (tramiteTraficoTransmisionBean.getTramiteTraficoBean().getVehiculo().getFechaLecturaKm() == null
					|| StringUtils.isBlank(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getVehiculo().getDoiResponsableKm()))) {
			resultBean.setError(true);
			resultBean.addMensajeALista("Si rellena el kilometraje del vehículo, debe rellenar la fecha de lectura y el NIF del responsable de la lectura");
			return resultBean;
		}

		// Mantis 19172 y 21598. David Sierra: Validar fecha caducidad NIFs y fecha nacimiento intervinientes
		try {
			ResultBean resultValCadNif = comprobarCaducidadNifs(tramiteTraficoTransmisionBean, true);
			if(resultValCadNif.getError()){
				return resultValCadNif;
			}else if(!resultValCadNif.getError() && resultValCadNif.getListaMensajes() != null && !resultValCadNif.getListaMensajes().isEmpty()){
				resultBean.setListaMensajes(resultValCadNif.getListaMensajes());
			}
			String mensajeValidezFechas = comprobarFormatoFechas(tramiteTraficoTransmisionBean);
			if(!mensajeValidezFechas.isEmpty()) {
				resultBean.setError(true);
				resultBean.setMensaje(mensajeValidezFechas);
				return resultBean;
			}
		} catch (ParseException e) {
			log.error("Al comprobar la fecha de caducidad de un NIF se ha producido el siguiente error: " + e.getMessage());
		}

		if(tramiteTraficoTransmisionBean.getTramiteTraficoBean() != null
			&& tramiteTraficoTransmisionBean.getTramiteTraficoBean().getTipoTramite() != null
			&& TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getTipoTramite().getValorEnum())){
			ResultBean resultadoValidaFecha = validarFechaFacturacionContrato(tramiteTraficoTransmisionBean);
			if(resultadoValidaFecha.getListaMensajes() != null && !resultadoValidaFecha.getListaMensajes().isEmpty()){
				for (String mensaje : resultadoValidaFecha.getListaMensajes()) {
					resultBean.setMensaje(mensaje);
					resultBean.setError(true);
				}
				return resultBean;
			}
		}

		// Validar dirección del vehículo si se ha marcado el check de localización del vehículo.
		if (TRUE.equals(tramiteTraficoTransmisionBean.getConsentimientoCambio())) {
			List<String> resultValidaDirVehiculo = validarDireccionVehiculo(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getVehiculo());
			if (!resultValidaDirVehiculo.isEmpty()) {
				resultBean.setError(true);
				resultBean.addMensajeALista("LOCALIZACION DEL VEHICULO: ");
				resultBean.addListaMensajes(resultValidaDirVehiculo);
				return resultBean;
			}
		}

		String validarAnag = gestorPropiedades.valorPropertie(VALIDAR_ANAGRAMA_ANTES_VALIDAR_TRAMITE);
		if (OPCION_SI_VALIDAR_ANAGRAMA_ANTES_VALIDAR_TRAMITE.equalsIgnoreCase(validarAnag) && !EstadoTramiteTrafico.Iniciado.equals(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getEstado())) {
			boolean noHayAdquiriente = false;
			boolean esHerenciaoSubasta = false;
			ResultBean result = null;

			// Verificamos si el trámite es de Herencia o de Adjudicación Judicial o Subasta
			// Esta parte se valida en ModeloTrafico.validaTramite() pero la necesitamos para saber si validamos el anagrama o no
			// Del Adquiriente y del Transmitente
			if(ModoAdjudicacion.tipo0 != null && ModoAdjudicacion.tipo0.equals(tramiteTraficoTransmisionBean.getModoAdjudicacion())){
				resultBean.setError(true);
				resultBean.setMensaje("El modo de adjudicación es obligatorio.");
				return resultBean;
			} else if (ModoAdjudicacion.tipo2.equals(tramiteTraficoTransmisionBean.getModoAdjudicacion())
					|| (ModoAdjudicacion.tipo3.equals(tramiteTraficoTransmisionBean.getModoAdjudicacion()) 
							&& AcreditacionTrafico.Herencia.equals(tramiteTraficoTransmisionBean.getAcreditaHerenciaDonacion()))) {
					esHerenciaoSubasta = true;
				}

			// Verifica el tipo de transferencia de la transmisión (si es tipo 5 'entrega compraventa' no hay adquiriente:
			// Antes tipo3
			if (TipoTransferencia.tipo5.equals(tramiteTraficoTransmisionBean.getTipoTransferencia())) {
				noHayAdquiriente = true;
			} else {
				if (!esHerenciaoSubasta) {
					if (OPCION_SI_VALIDAR_ANAGRAMA_SI_AEAT_CAIDA_SELENIUM.equalsIgnoreCase(gestorPropiedades.valorPropertie(VALIDAR_ANAGRAMA_SI_AEAT_CAIDA_SELENIUM))){
						result = getModeloTrafico().validarAnagramaSelenium(tramiteTraficoTransmisionBean.getAdquirienteBean().getPersona().getNif(),
								tramiteTraficoTransmisionBean.getAdquirienteBean().getPersona().getApellido1RazonSocial(), tramiteTraficoTransmisionBean.getAdquirienteBean().getPersona().getAnagrama());
					}else{
						result = getModeloTrafico().validarAnagrama(tramiteTraficoTransmisionBean.getAdquirienteBean().getPersona().getNif(),
								tramiteTraficoTransmisionBean.getAdquirienteBean().getPersona().getApellido1RazonSocial(), tramiteTraficoTransmisionBean.getAdquirienteBean().getPersona().getAnagrama());
					}
				} else {
					result = new ResultBean();
					result.setError(false);
				}
			}

			if (noHayAdquiriente || !result.getError() || TipoPersona.Juridica.equals(tramiteTraficoTransmisionBean.getAdquirienteBean().getPersona().getTipoPersona())) {
				if (!esHerenciaoSubasta) {
					if (OPCION_SI_VALIDAR_ANAGRAMA_SI_AEAT_CAIDA_SELENIUM.equalsIgnoreCase(gestorPropiedades.valorPropertie(VALIDAR_ANAGRAMA_SI_AEAT_CAIDA_SELENIUM))){
						result = getModeloTrafico().validarAnagramaSelenium(tramiteTraficoTransmisionBean.getTransmitenteBean().getPersona().getNif(),
								tramiteTraficoTransmisionBean.getTransmitenteBean().getPersona().getApellido1RazonSocial(), tramiteTraficoTransmisionBean.getTransmitenteBean().getPersona().getAnagrama());
					}else{
						result = getModeloTrafico().validarAnagrama(tramiteTraficoTransmisionBean.getTransmitenteBean().getPersona().getNif(),
								tramiteTraficoTransmisionBean.getTransmitenteBean().getPersona().getApellido1RazonSocial(), tramiteTraficoTransmisionBean.getTransmitenteBean().getPersona().getAnagrama());
					}
				} else {
					result.setError(false);
				}

				if (!result.getError() || TipoPersona.Juridica.equals(tramiteTraficoTransmisionBean.getTransmitenteBean().getPersona().getTipoPersona())) {
					if (OPCION_SI_VALIDAR_ANAGRAMA_SI_AEAT_CAIDA_SELENIUM.equalsIgnoreCase(gestorPropiedades.valorPropertie(VALIDAR_ANAGRAMA_SI_AEAT_CAIDA_SELENIUM))){
						result = getModeloTrafico().validarAnagramaSelenium(tramiteTraficoTransmisionBean.getPresentadorBean().getPersona().getNif(),
								tramiteTraficoTransmisionBean.getPresentadorBean().getPersona().getApellido1RazonSocial(), tramiteTraficoTransmisionBean.getPresentadorBean().getPersona().getAnagrama());
					}else{
						result = getModeloTrafico().validarAnagrama(tramiteTraficoTransmisionBean.getPresentadorBean().getPersona().getNif(),
						tramiteTraficoTransmisionBean.getPresentadorBean().getPersona().getApellido1RazonSocial(), tramiteTraficoTransmisionBean.getPresentadorBean().getPersona().getAnagrama());
					}

					if (!result.getError() || TipoPersona.Juridica.equals(tramiteTraficoTransmisionBean.getPresentadorBean().getPersona().getTipoPersona())) {
						// Puede que no haya poseedor (compraventa), así que debemos comprobarlo antes de validar su anagrama
						if (tramiteTraficoTransmisionBean.getPoseedorBean() != null && tramiteTraficoTransmisionBean.getPoseedorBean().getPersona() != null
								&& tramiteTraficoTransmisionBean.getPoseedorBean().getPersona().getNif() != null && !tramiteTraficoTransmisionBean.getPoseedorBean().getPersona().getNif().isEmpty()
								&& tramiteTraficoTransmisionBean.getPoseedorBean().getPersona().getApellido1RazonSocial() != null
								&& !tramiteTraficoTransmisionBean.getPoseedorBean().getPersona().getApellido1RazonSocial().isEmpty()
								&& tramiteTraficoTransmisionBean.getPoseedorBean().getPersona().getAnagrama() != null
								&& !tramiteTraficoTransmisionBean.getPoseedorBean().getPersona().getAnagrama().isEmpty()) {
							if (OPCION_SI_VALIDAR_ANAGRAMA_SI_AEAT_CAIDA_SELENIUM.equalsIgnoreCase(gestorPropiedades.valorPropertie(VALIDAR_ANAGRAMA_SI_AEAT_CAIDA_SELENIUM))){
								result = getModeloTrafico().validarAnagramaSelenium(tramiteTraficoTransmisionBean.getPoseedorBean().getPersona().getNif(),
										tramiteTraficoTransmisionBean.getPoseedorBean().getPersona().getApellido1RazonSocial(), tramiteTraficoTransmisionBean.getPoseedorBean().getPersona().getAnagrama());
							}else{
								result = getModeloTrafico().validarAnagrama(tramiteTraficoTransmisionBean.getPoseedorBean().getPersona().getNif(),
								tramiteTraficoTransmisionBean.getPoseedorBean().getPersona().getApellido1RazonSocial(), tramiteTraficoTransmisionBean.getPoseedorBean().getPersona().getAnagrama());
							}

							if (result.getError() && !TipoPersona.Juridica.equals(tramiteTraficoTransmisionBean.getPoseedorBean().getPersona().getTipoPersona())) {
								// Fallo poseedor
								resultBean.setError(true);
								resultBean.setMensaje("Ha fallado la validación del anagrama del poseedor contra la AEAT: " + result.getMensaje());
								return resultBean;
							}
						}
					} else { // Fallo presentador
						resultBean.setError(true);
						resultBean.setMensaje("Ha fallado la validación del anagrama del presentador contra la AEAT: " + result.getMensaje());
						return resultBean;
					}
				} else { // Fallo transmitente
					resultBean.setError(true);
					resultBean.setMensaje("Ha fallado la validación del anagrama del transmitente contra la AEAT: " + result.getMensaje());
					return resultBean;
				}
			} else { // Fallo adquiriente
				resultBean.setError(true);
				resultBean.setMensaje("Ha fallado la validación del anagrama del adquiriente contra la AEAT: " + result.getMensaje());
				return resultBean;
			}
			ResultBean resultValidar = new ResultBean(Boolean.FALSE);
			resultValidar = getModeloTrafico().validaTramite(tramiteTraficoTransmisionBean.getTramiteTraficoBean());
			errores = resultValidar.getListaMensajes();
		} else {
			ResultBean resultValidar = new ResultBean(Boolean.FALSE);
			resultValidar = getModeloTrafico().validaTramite(tramiteTraficoTransmisionBean.getTramiteTraficoBean());
			errores = resultValidar.getListaMensajes();

			//Validamos los anagramas de los intervinientes contra la AEAT si el resultado de la validación
			//del trámite es Validado Telemáticamente.
			if(EstadoTramiteTrafico.Validado_Telematicamente.equals(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getEstado())
					&& !resultValidar.getError()){
				boolean noHayAdquiriente = false;
				ResultBean result = new ResultBean(Boolean.FALSE);

				if (tramiteTraficoTransmisionBean != null
						&& tramiteTraficoTransmisionBean.getTramiteTraficoBean() != null
						&& tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente() != null) {
					numExpediente = tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente();
				}

				// Verifica el tipo de transferencia de la transmisión (si es tipo 3 'entrega compraventa' no hay adquiriente:
				// Antes tipo3
				if (TipoTransferencia.tipo5.equals(tramiteTraficoTransmisionBean.getTipoTransferencia())){
					noHayAdquiriente = true;
				} else {
					if (OPCION_SI_VALIDAR_ANAGRAMA_SI_AEAT_CAIDA_SELENIUM.equalsIgnoreCase(gestorPropiedades.valorPropertie(VALIDAR_ANAGRAMA_SI_AEAT_CAIDA_SELENIUM))){
						result = getModeloTrafico().validarAnagramaSelenium(tramiteTraficoTransmisionBean.getAdquirienteBean().getPersona().getNif(),
								tramiteTraficoTransmisionBean.getAdquirienteBean().getPersona().getApellido1RazonSocial(), 
								tramiteTraficoTransmisionBean.getAdquirienteBean().getPersona().getAnagrama());
					}
				}

				if (noHayAdquiriente || !result.getError() || TipoPersona.Juridica.equals(tramiteTraficoTransmisionBean.getAdquirienteBean().getPersona().getTipoPersona())) {
					if (OPCION_SI_VALIDAR_ANAGRAMA_SI_AEAT_CAIDA_SELENIUM.equalsIgnoreCase(gestorPropiedades.valorPropertie(VALIDAR_ANAGRAMA_SI_AEAT_CAIDA_SELENIUM))){
						result = getModeloTrafico().validarAnagramaSelenium(tramiteTraficoTransmisionBean.getTransmitenteBean().getPersona().getNif(), 
								tramiteTraficoTransmisionBean.getTransmitenteBean().getPersona().getApellido1RazonSocial(),
								tramiteTraficoTransmisionBean.getTransmitenteBean().getPersona().getAnagrama());
					}

					if (!result.getError() || TipoPersona.Juridica.equals(tramiteTraficoTransmisionBean.getTransmitenteBean().getPersona().getTipoPersona())){
						if (OPCION_SI_VALIDAR_ANAGRAMA_SI_AEAT_CAIDA_SELENIUM.equalsIgnoreCase(gestorPropiedades.valorPropertie(VALIDAR_ANAGRAMA_SI_AEAT_CAIDA_SELENIUM))){
							result = getModeloTrafico().validarAnagramaSelenium(tramiteTraficoTransmisionBean.getPresentadorBean().getPersona().getNif(), 
									tramiteTraficoTransmisionBean.getPresentadorBean().getPersona().getApellido1RazonSocial(),
									tramiteTraficoTransmisionBean.getPresentadorBean().getPersona().getAnagrama());
						}

						if (!result.getError() || TipoPersona.Juridica.equals(tramiteTraficoTransmisionBean.getPresentadorBean().getPersona().getTipoPersona())) {
							// Puede que no haya poseedor, así que debemos comprobarlo antes de validar su anagrama
							if (tramiteTraficoTransmisionBean.getPoseedorBean() != null && tramiteTraficoTransmisionBean.getPoseedorBean().getPersona() != null
									&& tramiteTraficoTransmisionBean.getPoseedorBean().getPersona().getNif() != null
									&& !CADENA_VACIA.equals(tramiteTraficoTransmisionBean.getPoseedorBean().getPersona().getNif())
									&& tramiteTraficoTransmisionBean.getPoseedorBean().getPersona().getApellido1RazonSocial() != null
									&& !CADENA_VACIA.equals(tramiteTraficoTransmisionBean.getPoseedorBean().getPersona().getApellido1RazonSocial())
									&& tramiteTraficoTransmisionBean.getPoseedorBean().getPersona().getAnagrama() != null
									&& !CADENA_VACIA.equals(tramiteTraficoTransmisionBean.getPoseedorBean().getPersona().getAnagrama())) {

								if (OPCION_SI_VALIDAR_ANAGRAMA_SI_AEAT_CAIDA_SELENIUM.equalsIgnoreCase(gestorPropiedades.valorPropertie(VALIDAR_ANAGRAMA_SI_AEAT_CAIDA_SELENIUM))){
									result = getModeloTrafico().validarAnagramaSelenium(tramiteTraficoTransmisionBean.getPoseedorBean().getPersona().getNif(),
											tramiteTraficoTransmisionBean.getPoseedorBean().getPersona().getApellido1RazonSocial(),
											tramiteTraficoTransmisionBean.getPoseedorBean().getPersona().getAnagrama());
								}

								// DRC@14-05-2013 Incidencia Mantis: 4468
								if (result.getError() && tramiteTraficoTransmisionBean.getAdquirienteBean() != null && tramiteTraficoTransmisionBean.getAdquirienteBean().getPersona() != null
										&& tramiteTraficoTransmisionBean.getAdquirienteBean().getPersona().getTipoPersona() != null
										&& !TipoPersona.Juridica.equals(tramiteTraficoTransmisionBean.getAdquirienteBean().getPersona().getTipoPersona())) {
									// Fallo poseedor
									errores.add("Ha fallado la validación del anagrama del poseedor contra la AEAT: " + result.getMensaje());
									log.error(numExpediente + " Ha fallado la validación del anagrama del poseedor contra la AEAT. - " + result.getMensaje());
									BeanPQCambiarEstadoTramite beanPQCambiarEstadoTramite = new BeanPQCambiarEstadoTramite();
									beanPQCambiarEstadoTramite.setP_NUM_EXPEDIENTE(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente());
									beanPQCambiarEstadoTramite.setP_ESTADO(new BigDecimal(EstadoTramiteTrafico.Validado_PDF.getValorEnum()));
									beanPQCambiarEstadoTramite.setP_ID_USUARIO(utilesColegiado.getIdUsuarioSessionBigDecimal());
									resultBean = (ResultBean) getModeloTrafico().cambiarEstadoTramite(beanPQCambiarEstadoTramite).get(ConstantesPQ.RESULTBEAN);

									if (!resultBean.getError()) {
										tramiteTraficoTransmisionBean.getTramiteTraficoBean().setEstado(EstadoTramiteTrafico.Validado_PDF.getValorEnum());
									} else {
										errores.add("Ha fallado el cambio de estado del trámite a Validado PDF: " + resultBean.getMensaje());
										log.error(numExpediente + " Ha fallado el cambio de estado del trámite a Validado PDF. - " + resultBean.getMensaje());
									}
								}
							}
						} else {
							//Fallo presentador
							errores.add("Ha fallado la validación del anagrama del presentador contra la AEAT: "+result.getMensaje());
							log.error(numExpediente + " Ha fallado la validación del anagrama del presentador contra la AEAT. - " + result.getMensaje());
							BeanPQCambiarEstadoTramite beanPQCambiarEstadoTramite = new BeanPQCambiarEstadoTramite();
							beanPQCambiarEstadoTramite.setP_NUM_EXPEDIENTE(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente());
							beanPQCambiarEstadoTramite.setP_ESTADO(new BigDecimal(EstadoTramiteTrafico.Validado_PDF.getValorEnum()));
							beanPQCambiarEstadoTramite.setP_ID_USUARIO(utilesColegiado.getIdUsuarioSessionBigDecimal());
							resultBean = (ResultBean) getModeloTrafico().cambiarEstadoTramite(beanPQCambiarEstadoTramite).get(ConstantesPQ.RESULTBEAN);

							if (!resultBean.getError()){
								tramiteTraficoTransmisionBean.getTramiteTraficoBean().setEstado(EstadoTramiteTrafico.Validado_PDF.getValorEnum());
							} else {
								errores.add("Ha fallado el cambio de estado del trámite a Validado PDF: "+resultBean.getMensaje());
								log.error(numExpediente + " Ha fallado el cambio de estado del trámite a Validado PDF. - " + resultBean.getMensaje());
							}
						}

					} else {
						//Fallo transmitente
						errores.add("Ha fallado la validación del anagrama del transmitente contra la AEAT: "+result.getMensaje());
						log.error(numExpediente + " Ha fallado la validación del anagrama del transmitente contra la AEAT. - " + result.getMensaje());
						BeanPQCambiarEstadoTramite beanPQCambiarEstadoTramite = new BeanPQCambiarEstadoTramite();
						beanPQCambiarEstadoTramite.setP_NUM_EXPEDIENTE(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente());
						beanPQCambiarEstadoTramite.setP_ESTADO(new BigDecimal(EstadoTramiteTrafico.Validado_PDF.getValorEnum()));
						beanPQCambiarEstadoTramite.setP_ID_USUARIO(utilesColegiado.getIdUsuarioSessionBigDecimal());
						resultBean = (ResultBean) getModeloTrafico().cambiarEstadoTramite(beanPQCambiarEstadoTramite).get(ConstantesPQ.RESULTBEAN);

						if (!resultBean.getError()){
							tramiteTraficoTransmisionBean.getTramiteTraficoBean().setEstado(EstadoTramiteTrafico.Validado_PDF.getValorEnum());
						} else {
							errores.add("Ha fallado el cambio de estado del trámite a Validado PDF: "+resultBean.getMensaje());
							log.error(numExpediente + " Ha fallado el cambio de estado del trámite a Validado PDF. - " + resultBean.getMensaje());
						}
					}

				} else {
					//Fallo adquiriente
					errores.add("Ha fallado la validación del anagrama del adquiriente contra la AEAT: "+result.getMensaje());
					log.error(numExpediente + " Ha fallado la validación del anagrama del adquiriente contra la AEAT. - " + result.getMensaje());
					BeanPQCambiarEstadoTramite beanPQCambiarEstadoTramite = new BeanPQCambiarEstadoTramite();
					beanPQCambiarEstadoTramite.setP_NUM_EXPEDIENTE(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente());
					beanPQCambiarEstadoTramite.setP_ESTADO(new BigDecimal(EstadoTramiteTrafico.Validado_PDF.getValorEnum()));
					beanPQCambiarEstadoTramite.setP_ID_USUARIO(utilesColegiado.getIdUsuarioSessionBigDecimal());
					resultBean = (ResultBean) getModeloTrafico().cambiarEstadoTramite(beanPQCambiarEstadoTramite).get(ConstantesPQ.RESULTBEAN);

					if (!resultBean.getError()){
						tramiteTraficoTransmisionBean.getTramiteTraficoBean().setEstado(EstadoTramiteTrafico.Validado_PDF.getValorEnum());
					} else {
						errores.add("Ha fallado el cambio de estado del trámite a Validado PDF: "+resultBean.getMensaje());
						log.error(numExpediente + " Ha fallado el cambio de estado del trámite a Validado PDF. - " + resultBean.getMensaje());
					}
				}
			}
		}
		resultBean.setListaMensajes(errores);
		if (errores != null && !errores.isEmpty()) {
			if (errores.size() == 1) {
				if ("VALIDADO TELEMATICAMENTE".equals(errores.get(0))) {
					tramiteTraficoTransmisionBean.setResCheckCtit("TELEMATICO");
					resultBean.setMensaje(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getEstado().getValorEnum());
					resultBean.getListaMensajes().remove(resultBean.getListaMensajes().size() - 1);
					resultBean.setError(false);
					errores.clear();
					// 15-10-2012. Ricardo Rodriguez. Incidencia : 0002589
				} else if (errores.get(0).contains(ConstantesTrafico.AVISO_INE_DIRECCION)) {
					tramiteTraficoTransmisionBean.setResCheckCtit("TELEMATICO");
					resultBean.setError(false);
					resultBean.setMensaje(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getEstado().getValorEnum());
					resultBean.getListaMensajes().remove(resultBean.getListaMensajes().size() - 1);
					// FIN. 15-10-2012. Ricardo Rodriguez. Incidencia : 0002589
				} else {
					resultBean.setError(true);
					resultBean.setMensaje(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getEstado().getValorEnum());
					resultBean.getListaMensajes().remove(resultBean.getListaMensajes().size() - 1);
				}
				// 15-10-2012. Ricardo Rodriguez. Incidencia : 0002589
			} else if (soloErroresIne(errores)) {
				tramiteTraficoTransmisionBean.setResCheckCtit("TELEMATICO");
				resultBean.setError(false);
				resultBean.setMensaje(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getEstado().getValorEnum());
				resultBean.getListaMensajes().remove(resultBean.getListaMensajes().size() - 1);
				// FIN. 15-10-2012. Ricardo Rodriguez. Incidencia : 0002589
			} else {
				resultBean.setError(true);
				resultBean.setMensaje(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getEstado().getValorEnum());
				resultBean.getListaMensajes().remove(resultBean.getListaMensajes().size() - 1);
			}
		}else{
			resultBean.setMensaje(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getEstado().getValorEnum());
			resultBean.getListaMensajes().remove(resultBean.getListaMensajes().size() - 1);
		}
		return resultBean;
	}

	/**
	 * Valida si la dirección del vehículo está completa
	 * @param tramiteTraficoTransmisionBean
	 * @return
	 */
	private List<String> validarDireccionVehiculo(VehiculoBean vehiculo) {
		List<String> listaMensajes = new ArrayList<>();

		if (vehiculo.getDireccionBean() != null && vehiculo.getDireccionBean().getMunicipio() != null && vehiculo.getDireccionBean().getMunicipio().getIdMunicipio() != null
				&& vehiculo.getDireccionBean().getMunicipio().getProvincia() != null && vehiculo.getDireccionBean().getMunicipio().getProvincia().getIdProvincia() != null) {

			Direccion direccionVehiculo	= vehiculo.getDireccionBean();
			String idProvinciaVehiculo	= direccionVehiculo.getMunicipio().getProvincia().getIdProvincia();
			String idMunicipioVehiculo	= direccionVehiculo.getMunicipio().getIdMunicipio();
			String idTipoViaVehiculo	= direccionVehiculo.getTipoVia().getIdTipoVia();

			if (idProvinciaVehiculo.isEmpty() || idProvinciaVehiculo.equals("-1")) {
				listaMensajes.add(LA_PROVINCIA_DEL_VEHICULO_ES_OBLIGATORIA);
			}

			if (idMunicipioVehiculo.isEmpty() || idMunicipioVehiculo.equals("-1")) {
				listaMensajes.add(EL_MUNICIPIO_DEL_VEHICULO_ES_OBLIGATORIO);
			}

			if (idTipoViaVehiculo.isEmpty() || idTipoViaVehiculo.equals("-1")) {
				listaMensajes.add(EL_TIPO_DE_VIA_DEL_VEHICULO_ES_OBLIGATORIO);
			}

			if (direccionVehiculo.getNombreVia().isEmpty()) {
				listaMensajes.add(EL_NOMBRE_DE_VIA_DEL_VEHICULO_ES_OBLIGATORIO);
			} else if (direccionVehiculo.getNombreVia().length() > 50) {
				listaMensajes.add(EL_NOMBRE_DE_VIA_DEL_VEHICULO_NO_PUEDE_TENER_MAS_DE_50_CARACTERES);
			}

			if (direccionVehiculo.getNumero().isEmpty()) {
				listaMensajes.add(EL_NUMERO_DE_VIA_DEL_VEHICULO_ES_OBLIGATORIO);
			}

			if (direccionVehiculo.getCodPostal() == null || direccionVehiculo.getCodPostal().isEmpty()) {
				listaMensajes.add(EL_CODIGO_POSTAL_DEL_VEHICULO_ES_OBLIGATORIO);
			} else if (direccionVehiculo.getCodPostal().length() < 5) {
				listaMensajes.add(EL_CODIGO_POSTAL_DEBE_TENER_5_CARACTERES);
			}

			if (direccionVehiculo.getPortal() != null && direccionVehiculo.getPortal().length() > 1) {
				listaMensajes.add(EL_PORTAL_DE_LA_DIRECCION_DEL_VEHICULO_NO_PUEDE_TENER_MAS_DE_1_CARACTER);
			}

			if (direccionVehiculo.getEscalera() != null && direccionVehiculo.getEscalera().length() > 2) {
				listaMensajes.add(LA_ESCALERA_DE_LA_DIRECCION_DEL_VEHICULO_NO_PUEDE_TENER_MAS_DE_2_CARACTERES);
			}

			if (direccionVehiculo.getPlanta() != null && direccionVehiculo.getPlanta().length() > 3) {
				listaMensajes.add(EL_PISO_DE_LA_DIRECCION_DEL_VEHICULO_NO_PUEDE_TENER_MAS_DE_3_CARACTERES);
			}

			if (direccionVehiculo.getPuerta() != null && direccionVehiculo.getPuerta().length() > 2) {
				listaMensajes.add(LA_PUERTA_DE_LA_DIRECCION_DEL_VEHICULO_NO_PUEDE_TENER_MAS_DE_2_CARACTERES);
			}

		} else {
			listaMensajes.add(LA_DIRECCION_DEL_VEHICULO_ES_OBLIGATORIA);
		}

		return listaMensajes;
	}

	/**
	 * Valida si los intervinientes y el vehículo son válidos por la territorialidad de su dirección
	 * @param tramiteTraficoTransmisionBean
	 * @return
	 */
	private boolean validarTerritorialidad(TramiteTraficoTransmisionBean tramiteTraficoTransmisionBean) {
		boolean valido = true;

		try {
			Persona adquiriente = null;
			Persona transmitente = null;
			Persona compraventa = null;
			VehiculoBean vehiculo = null;
			String provinciaAdquiriente = null;
			String provinciaTransmitente = null;
			String provinciaVehiculo = null;

			String[] arrayExcluidas = gestorPropiedades.valorPropertie("territorialidad.ctit.exclusiones.provincias").split(",");
			if (tramiteTraficoTransmisionBean != null) {
				adquiriente = tramiteTraficoTransmisionBean.getAdquirienteBean() != null ? tramiteTraficoTransmisionBean.getAdquirienteBean().getPersona() : null;
				transmitente = tramiteTraficoTransmisionBean.getTransmitenteBean() != null ? tramiteTraficoTransmisionBean.getTransmitenteBean().getPersona() : null;
				compraventa = tramiteTraficoTransmisionBean.getPoseedorBean() != null ? tramiteTraficoTransmisionBean.getPoseedorBean().getPersona() : null;
				vehiculo = tramiteTraficoTransmisionBean.getTramiteTraficoBean() != null ? tramiteTraficoTransmisionBean.getTramiteTraficoBean().getVehiculo() : null;
			}

			// Comparar tipo de transferencia para tomar datos del adquiriente o Compraventa
			if (!TipoTransferencia.tipo5.equals(tramiteTraficoTransmisionBean.getTipoTransferencia()) && adquiriente != null) {
				provinciaAdquiriente = ModeloPersona.dameProvinciaPersona(adquiriente.getDireccion());
			} else if (compraventa != null) {
				// Se considera el compraventa para ENTREGA COMPRAVENTA
				provinciaAdquiriente = ModeloPersona.dameProvinciaPersona(compraventa.getDireccion());
			}

			if (transmitente != null) {
				provinciaTransmitente = ModeloPersona.dameProvinciaPersona(transmitente.getDireccion());
			}

			if (vehiculo != null) {
				provinciaVehiculo = ModeloPersona.dameProvinciaPersona(vehiculo.getDireccionBean());
				if (provinciaVehiculo == null) {
					provinciaVehiculo = provinciaTransmitente;
				}
			}

			boolean adquirienteValido = true;
			boolean transmitenteValido = true;
			boolean vehiculoValido = true;

			for (String provinciaExcluida : arrayExcluidas) {
				if (provinciaExcluida.equals(provinciaAdquiriente)) {
					adquirienteValido = false;
				}
				if (provinciaExcluida.equals(provinciaTransmitente)) {
					transmitenteValido = false;
				}
				// Si no está marcado consentimiento cambio, no se tiene en cuenta la provincia del vehículo
				// (se pone vehiculoValido false, para que la condición "!adquirienteValido && !transmitenteValido && !vehiculoValido"
				// solo tenga en cuenta las direcciones de adquiriente y transmitente)
				if (FALSE.equals(tramiteTraficoTransmisionBean.getConsentimientoCambio()) || provinciaExcluida.equals(provinciaVehiculo)) {
					vehiculoValido = false;
				}
			}

			if (!adquirienteValido && !transmitenteValido && !vehiculoValido) {
				valido = false;
			}

		} catch (Throwable e) {
			valido = true;
			String numExpediente = tramiteTraficoTransmisionBean != null && tramiteTraficoTransmisionBean.getTransmitenteBean() != null
					&& tramiteTraficoTransmisionBean.getTransmitenteBean().getNumExpediente() != null ? tramiteTraficoTransmisionBean.getTransmitenteBean().getNumExpediente().toString() : CADENA_VACIA;
			log.error("Error general validando la territorialidad del trámite " + numExpediente + ". Se da como válida para permitir la ejecución normal de la validación.");
		}
		return valido;
	}

	/**
	 * Recorre la lista de errores verificando si todos son validaciones ine de dirección o no
	 * @param errores
	 * @return true si todo son errores de validación INE, false si no.
	 */
	private boolean soloErroresIne(List<String> errores){
		if(errores == null || errores.isEmpty()){
			return false;
		}else{
			int contador = 0;
			int erroresIne = 0;
			for (String error : errores) {
				contador += 1;
				if (error.contains(ConstantesTrafico.AVISO_INE_DIRECCION)) {
					erroresIne += 1;
				}
			}
			return (contador == erroresIne);
		}
	}

	/**
	 * Añade una fila en la tabla de colas para su envío a DGT
	 * @param nombreXml
	 * @return
	 */
	public String crearSolicitudTransmisionElectronicaCheckCtit(String nombreXml, String nombreProceso,TramiteTraficoTransmisionBean tramiteTraficoTransmisionBean){
		String resultado = CADENA_VACIA;
		BeanPQCrearSolicitud beanPQCrearSolicitud = new BeanPQCrearSolicitud();
		beanPQCrearSolicitud.setP_ID_TRAMITE(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente());
		beanPQCrearSolicitud.setP_ID_USUARIO(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getIdUsuario());
		beanPQCrearSolicitud.setP_TIPO_TRAMITE(TipoTramiteTrafico.TransmisionElectronica.getValorEnum());
		beanPQCrearSolicitud.setP_XML_ENVIAR(nombreXml);
		beanPQCrearSolicitud.setP_PROCESO(nombreProceso);

		HashMap<String,Object> resultadoSolicitud = getModeloSolicitud().crearSolicitud(beanPQCrearSolicitud);

		ResultBean resultBean = (ResultBean) resultadoSolicitud.get(ConstantesPQ.RESULTBEAN);

		if (!resultBean.getError()) {
			resultado = SOLICITUD_CREADA_CORRECTAMENTE;
		} else {
			resultado = ERROR_AL_CREAR_LA_SOLICITUD;
//			for(String mensaje:resultBean.getListaMensajes()){
//				addActionError(mensaje);
//			}
		}
		return resultado;
	}

	public ResultBean cambiaEstadoPendienteDgt(String nombreProceso,TramiteTraficoTransmisionBean tramiteTraficoTransmisionBean) {
		BeanPQCambiarEstadoTramite beanPQCambiarEstadoTramite = new BeanPQCambiarEstadoTramite();
		if (nombreProceso.equals(ConstantesProcesos.PROCESO_CHECKCTIT)) {
			beanPQCambiarEstadoTramite.setP_ESTADO(new BigDecimal(EstadoTramiteTrafico.Pendiente_Check_Ctit.getValorEnum()));
		} else {
			beanPQCambiarEstadoTramite.setP_ESTADO(new BigDecimal(EstadoTramiteTrafico.Pendiente_Envio.getValorEnum()));
		}
		beanPQCambiarEstadoTramite.setP_NUM_EXPEDIENTE(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente());

		HashMap<String,Object> resultadoCambiarEstado = getModeloTrafico().cambiarEstadoTramite(beanPQCambiarEstadoTramite);
		return (ResultBean)resultadoCambiarEstado.get(ConstantesPQ.RESULTBEAN);
	}

	/**
	 * 
	 * @param xmlEnviar
	 * @param tipoConsentimiento
	 * @return el registro entrada con los datos necesarios cambiados para el segundo envío de la doble tramitación.
	 * @throws MalformedURLException
	 * @throws JAXBException
	 * @throws OegamExcepcion
	 */
	public SolicitudRegistroEntrada rehacerSolicitudRegEntradaFullSegundoEnvio(String xmlEnviar, TipoConsentimiento tipoConsentimiento)
			throws MalformedURLException, JAXBException, OegamExcepcion {
		SolicitudRegistroEntrada solicitudRegistroEntrada = new XmlTransTelematicaFactory().getSolicitudRegistroEntradaPorNombreFichero(xmlEnviar);
		solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().getTasas().getTasaTramiteOrTasaInforme().add(null);
		solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().getAcreditacionDerecho().setConsentimiento(
				TRUE.equals(tipoConsentimiento) ? TipoConsentimiento.SI : TipoConsentimiento.N_A);
		solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setFirmaGestor(new byte[0]); // Borramos la firma
		solicitudRegistroEntrada.setFirma(new byte[0]);

		return solicitudRegistroEntrada;
	}

	public void crearSolicitudEnColaWsInfoCheckCtit(BigDecimal numExpediente,BigDecimal usuario) throws CrearSolicitudExcepcion {
		getModeloSolicitud().crearSolicitud(
				numExpediente, usuario,TipoTramiteTrafico.TransmisionElectronica, ConstantesProcesos.PROCESO_INFO_WS,ConstantesProcesos.PROCESO_CHECKCTIT);
	}

	/**
	 * 
	 * @param tramiteTraficoTransmisionBean
	 * @param idUsuario
	 * @return
	 */
	public ResultBean validarTramiteTransmisionEnServidor(TramiteTraficoTransmisionBean tramiteTraficoTransmisionBean, BigDecimal idUsuario){
		ResultBean resultValidarTramiteTransmisionEnServidor = new ResultBean();

		ResultBean resultBeanValidar = validar(tramiteTraficoTransmisionBean);
		if (resultBeanValidar.getError()) {
			resultValidarTramiteTransmisionEnServidor.setError(resultBeanValidar.getError());

			for(String mensaje:resultBeanValidar.getListaMensajes()){
				resultValidarTramiteTransmisionEnServidor.setMensaje(mensaje);
			}
			return resultValidarTramiteTransmisionEnServidor;
		}

		try {
			crearSolicitudEnColaWsInfoCheckCtit(
					tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente(),idUsuario);
			resultValidarTramiteTransmisionEnServidor.setMensaje(SOLICITUD_CREADA_CORRECTAMENTE);
			cambiaEstadoPendienteDgt(ConstantesProcesos.PROCESO_CHECKCTIT, tramiteTraficoTransmisionBean);
		} catch (CrearSolicitudExcepcion e) {
			resultValidarTramiteTransmisionEnServidor.setError(true);
			resultValidarTramiteTransmisionEnServidor.setMensaje(e.getMensajeError1());
		}

		return resultValidarTramiteTransmisionEnServidor;
	}

	/**
	 * @author antonio.miguez
	 * @param String
	 * @return String
	 */
	/*
	 * Método para formatear un número en el formato decimal de G@ta
	 */
	public String formatoDecimalGata(String cadena){
		if (_0.equals(cadena)) return "0,00";
		DecimalFormat formateador = new DecimalFormat("###############.00");
		String resultado = formateador.format(Float.parseFloat(cadena));
		resultado = resultado.replace(".", ",");
		if (Float.parseFloat(cadena)>999.99) {
			int tamCadSinDecimales = resultado.length()-3;
			if (tamCadSinDecimales > 6) { // Necesitaremos poner dos puntos, separadores de miles
				resultado = resultado.substring(0,resultado.length()-9)+"."+resultado.substring(resultado.length()-9,resultado.length()-6)+"."+resultado.substring(resultado.length()-6);
			} else { // Solo un punto, separador de miles
				resultado = resultado.substring(0,resultado.length()-6)+"."+resultado.substring(resultado.length()-6);
			}
		}
		return resultado;
	}

	/**
	 * @author antonio.miguez
	 * @param BigDecimal
	 * @return String
	 */
	/*
	 * Método para formatear un número en el formato decimal de G@ta
	 */
	public String formatoDecimalGata(BigDecimal decimal){
		DecimalFormat formateador = new DecimalFormat("###############.00");
		formateador.setMinimumIntegerDigits(1);
		String cadena = decimal.toString();
		String resultado = formateador.format(Float.parseFloat(cadena));
		resultado = resultado.replace(".", ",");
		if(Float.parseFloat(cadena)>999.99){
			int tamCadSinDecimales = resultado.length()-3;
			if(tamCadSinDecimales>6){ // Necesitaremos poner dos puntos, separadores de miles
				resultado = resultado.substring(0,resultado.length()-9)+"."+resultado.substring(resultado.length()-9,resultado.length()-6)+"."+resultado.substring(resultado.length()-6);
			} else { // Solo un punto, separador de miles
				resultado = resultado.substring(0,resultado.length()-6)+"."+resultado.substring(resultado.length()-6);
			}
		}
		return resultado;
	}

	/**
	 * @author antonio.miguez
	 * @param String
	 * @return boolean
	 */
	/*
	 * Método para validar el formato de un DNI
	 */
	public boolean validarDNI(String nif){
		//si es NIE, eliminar la x,y,z inicial para tratarlo como NIF
		String comienzoNIE = CADENA_VACIA;
		if (nif.toUpperCase().startsWith("X") || nif.toUpperCase().startsWith("Y") || nif.toUpperCase().startsWith("Z")) {
			comienzoNIE = nif.substring(0,1);
			nif = nif.substring(1);
		}
		Pattern nifPattern =
		Pattern.compile("(\\d{1,8})([TRWAGMYFPDXBNJZSQVHLCKEtrwagmyfpdxbnjzsqvhlcke])");
		Matcher m = nifPattern.matcher(nif);
		if(m.matches()){
			String letra = m.group(2);
			//Extraer letra del NIF
			String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
			int dni = Integer.parseInt(m.group(1));
			if(comienzoNIE.equals("Y")){
				dni = dni + 10000000;
			}
			if(comienzoNIE.equals("Z")){
				dni = dni + 20000000;
			}

			dni = dni % 23;
			String reference = letras.substring(dni,dni+1);

			return reference.equalsIgnoreCase(letra);
		} else
			return false;
	}

	/**
	 * @author antonio.miguez
	 * @param String
	 * @return boolean
	 */
	/*
	 * Método para validar el formato de un CIF
	 */
	public boolean validarCIF(String valor){
		String cif = valor.toUpperCase();
		String dc = "JABCDEFGHI";

		// Inicio - 0006978: VALIDAR 620 - Entidad religiosa - 2564
		String letras = "ABCDEFGHJNPQRSUVW"; // Según documento AEAT (ORDEN EHA/451/2008)
		// Fin - 0006978: VALIDAR 620 - Entidad religiosa - 2564

		boolean resu = false;
		int[] v1 = { 0, 2, 4, 6, 8, 1, 3, 5, 7, 9 };
		int temp = 0;
		int i = 0;
		String digitos = cif.substring(1, cif.length() - 1);

		if (cif.length() != 9) {
			return false;
		}
		try {
			Long.parseLong(digitos);
		} catch (NumberFormatException e) {
			return false;
		}
		if (!letras.contains(cif.substring(0, 1))) {
			return false;
		}

		for (i = 2; i <= 6; i += 2) {
			temp = temp + v1[Integer.parseInt(cif.substring(i - 1, i))];
			temp = temp + Integer.parseInt(cif.substring(i, i + 1));
		}

		temp = temp + v1[Integer.parseInt(cif.substring(7, 8))];
		temp = (10 - (temp % 10));

		if (temp == 10) {
			if (cif.charAt(cif.length() - 1) == '0' || cif.charAt(cif.length() - 1) == 'J') {
				resu = true;
			}
		} else {
			if (Character.isDigit(cif.charAt(cif.length() - 1))
					&& Integer.parseInt(cif.charAt(cif.length() - 1) + CADENA_VACIA) == temp) {
				resu = true;
			}
			if (Character.isLetter(cif.charAt(cif.length() - 1)) && cif.charAt(cif.length() - 1) == dc.charAt(temp)) {
				resu = true;
			}
		}
		if (!resu) {
			return false;
		}
		return resu;
	}

	// Comprobamos si el vehículo tiene más de 10 años.
	public boolean vehiculoMayorDe10Anios(VehiculoBean vehiculo) {
		try{
			if (vehiculo.getFechaPrimMatri().getAnio()==null || vehiculo.getFechaPrimMatri().getMes()==null || vehiculo.getFechaPrimMatri().getDia()==null){
				vehiculo.setFechaPrimMatri(vehiculo.getFechaMatriculacion());
			}

			boolean esMayor = false;

			Calendar fechaVehiculo = new GregorianCalendar((new Integer (vehiculo.getFechaPrimMatri().getAnio())+10),
															(new Integer (vehiculo.getFechaPrimMatri().getMes())-1),
															(new Integer (vehiculo.getFechaPrimMatri().getDia())));
			Calendar fechaHoy = new GregorianCalendar();
			if (fechaVehiculo.after(fechaHoy)){
				esMayor = false;
			}else{
				esMayor = true;
			}
			return esMayor;
		} catch (Exception e){
			return false;
		}
	}

	/*
	 * Método desarrollado para Yerbabuena para comprobar la caducidad de los NIF en las transmisiones.
	 */
	public ResultBean validarCaducidadNIF(TramiteTraficoTransmisionBean tramiteTraficoTransmisionBean) throws ParseException{
		List<String> resultadoValidacion = new ArrayList<>();
		ResultBean resultBean = new ResultBean();
		//Excepciones de la validación de la caducidad del NIF
		//Obtener TipoTransferencia para ver los casos exentos de validación
		if(tramiteTraficoTransmisionBean != null && tramiteTraficoTransmisionBean.getTipoTransferencia() != null){
			int tipoTransferencia = Integer.parseInt(tramiteTraficoTransmisionBean.getTipoTransferencia().getValorEnum());

			switch(tipoTransferencia){
				case 1: //Cambio de titularidad completo
					if (tramiteTraficoTransmisionBean.getModoAdjudicacion()!=null && tramiteTraficoTransmisionBean.getAcreditaHerenciaDonacion() != null){
						if (ModoAdjudicacion.tipo3.toString().equals(tramiteTraficoTransmisionBean.getModoAdjudicacion().getValorEnum())
								&& AcreditacionTrafico.Herencia.equals(tramiteTraficoTransmisionBean.getAcreditaHerenciaDonacion())){
							// Cumple la norma y es el caso 1 en el que está exento el transmitente
							resultadoValidacion = validaNIFSinVendedor(tramiteTraficoTransmisionBean);
						}else{
							// No es un modo de adjudicación por fallecimiento y no es un caso excepcional.
							resultadoValidacion = validaNIFSinExcepcion(tramiteTraficoTransmisionBean);
						}
					}else{
						resultadoValidacion = validaNIFSinExcepcion(tramiteTraficoTransmisionBean);
					}
					break;
				case 2: // Finalización tras una notificación
				case 3: // Interviene Compra-Venta
				// Excepción temporal. Cuando indiquen que no es necesario eliminar el case 5.
				case 5: // Entrega Compra-venta
					resultadoValidacion = validaNIFSinVendedor(tramiteTraficoTransmisionBean);
					break;
				case 4: // Notificación cambio de titularidad
					resultadoValidacion = validaNIFSinComprador(tramiteTraficoTransmisionBean);
					break;
				default: // Para el resto de casos no hay excepción.
					resultadoValidacion = validaNIFSinExcepcion(tramiteTraficoTransmisionBean);
			}
		}else{
			resultBean.setError(true);
			resultBean.setMensaje("Existen errores en el trámite y no se puede validar la caducidad del NIF.");
			return resultBean;
		}

		if (resultadoValidacion.isEmpty()){
			resultBean.setError(false);
		} else{
			boolean firstTime = true;
			StringBuffer sb = new StringBuffer("Los siguientes NIF están caducados: ");
			for (String error:resultadoValidacion){
				if (firstTime){
					sb.append(error);
					firstTime = false;
				}else{
					sb.append(", ").append(error);
				}
			}
			resultBean.setError(false);
			resultBean.setMensaje(sb.toString());
		}
		return resultBean;
	}

	public List<String> validaNIFSinVendedor(TramiteTraficoTransmisionBean tramiteTraficoTransmisionBean) throws ParseException{
		//Obtengo los NIF de los intervinientes que hay que comprobar su fecha de caducidad.
		//No hay que comprobar el NIF del transmitente.
		List<String> validacionNIFs = new ArrayList<>();
		Fecha fechaComparar = new Fecha();

		if (tramiteTraficoTransmisionBean.getFechaContrato() != null && !tramiteTraficoTransmisionBean.getFechaContrato().isfechaNula()){
			fechaComparar = tramiteTraficoTransmisionBean.getFechaContrato();
		} else if (tramiteTraficoTransmisionBean.getFechaFactura() != null && !tramiteTraficoTransmisionBean.getFechaFactura().isfechaNula()){
			fechaComparar = tramiteTraficoTransmisionBean.getFechaFactura();
		}

		if (validaDocumentacionIntervinienteCaducada(tramiteTraficoTransmisionBean.getAdquirienteBean(),fechaComparar)){
			validacionNIFs.add(ADQUIRIENTE);
		}

		if (validaDocumentacionIntervinienteCaducada(tramiteTraficoTransmisionBean.getRepresentanteAdquirienteBean(),fechaComparar)){
			validacionNIFs.add(REPRESENTANTE_ADQUIRIENTE);
		}

		if (validaDocumentacionIntervinienteCaducada(tramiteTraficoTransmisionBean.getConductorHabitualBean(),fechaComparar)){
			validacionNIFs.add(CONDUCTOR_HABITUAL);
		}

		if (validaDocumentacionIntervinienteCaducada(tramiteTraficoTransmisionBean.getPrimerCotitularTransmitenteBean(),fechaComparar)){
			validacionNIFs.add(PRIMER_COTITULAR);
		}

		if (validaDocumentacionIntervinienteCaducada(tramiteTraficoTransmisionBean.getSegundoCotitularTransmitenteBean(),fechaComparar)){
			validacionNIFs.add(SEGUNDO_COTITULAR);
		}

		if (validaDocumentacionIntervinienteCaducada(tramiteTraficoTransmisionBean.getRepresentantePrimerCotitularTransmitenteBean(),fechaComparar)){
			validacionNIFs.add(REPRESENTANTE_PRIMER_COTITULAR_TRANSMITENTE);
		}

		if (validaDocumentacionIntervinienteCaducada(tramiteTraficoTransmisionBean.getRepresentanteSegundoCotitularTransmitenteBean(),fechaComparar)){
			validacionNIFs.add(REPRESENTANTE_SEGUNDO_COTITULAR_TRANSMITENTE);
		}

		if (validaDocumentacionIntervinienteCaducada(tramiteTraficoTransmisionBean.getPoseedorBean(),fechaComparar)){
			validacionNIFs.add(COMPRAVENTA);
		}

		if (validaDocumentacionIntervinienteCaducada(tramiteTraficoTransmisionBean.getRepresentantePoseedorBean(),fechaComparar)){
			validacionNIFs.add(REPRESENTANTE_COMPRAVENTA);
		}

		return validacionNIFs;
	}

	/**
	 * Método desarrollado para Yerbabuena para comprobar la caducidad de los NIF en las matriculaciones.
	 */
	public ResultBean validarCaducidadNIF(TramiteTraficoMatriculacionBean tramiteTraficoMatriculacionBean) throws ParseException{
		List<String> resultadoValidacion = new ArrayList<>();
		ResultBean resultBean = new ResultBean();
		// Obtener TipoTransferencia para ver los casos exentos de validación

		resultadoValidacion = validaNIFSinExcepcion(tramiteTraficoMatriculacionBean);

		if (resultadoValidacion.isEmpty()){
			resultBean.setError(false);}
		else{
			boolean firstTime = true;
			StringBuffer sb = new StringBuffer("Los siguientes NIF están caducados: ");
			for (String error:resultadoValidacion){
				if (firstTime){
					sb.append(error);
					firstTime = false;
				}else{
					sb.append(", ").append(error);
				}
			}
			resultBean.setError(true);
			resultBean.setMensaje(sb.toString());
		}
		return resultBean;
	}

	/**
	 * Validación de NIF
	 * */
	public List<String> validaNIFSinExcepcion(TramiteTraficoMatriculacionBean tramiteTraficoMatriculacionBean) throws ParseException{
		List<String> validacionNIFs = new ArrayList<>();
		Fecha hoy = new Fecha(new SimpleDateFormat(FORMATO_FECHA_2).format(new Date()));
		//Obtengo los NIF de los intervinientes que hay que comprobar su fecha de caducidad.

		if (validaDocumentacionIntervinienteCaducada(tramiteTraficoMatriculacionBean.getTitularBean(), hoy)){
			validacionNIFs.add(TITULAR);
		}

		if (validaDocumentacionIntervinienteCaducada(tramiteTraficoMatriculacionBean.getRepresentanteTitularBean(), hoy)){
			validacionNIFs.add(REPRESENTANTE_TITULAR);
		}

		if (validaDocumentacionIntervinienteCaducada(tramiteTraficoMatriculacionBean.getConductorHabitualBean(), hoy)){
			validacionNIFs.add(CONDUCTOR_HABITUAL);
		}

		if (validaDocumentacionIntervinienteCaducada(tramiteTraficoMatriculacionBean.getArrendatarioBean(), hoy)){
			validacionNIFs.add(ARRENDATARIO);
		}

		return validacionNIFs;
	}

	public List<String> validaNIFSinExcepcion(TramiteTraficoTransmisionBean tramiteTraficoTransmisionBean) throws ParseException{
		//Obtengo los NIF de los intervinientes que hay que comprobar su fecha de caducidad.
		List<String> validacionNIFs = new ArrayList<>();
		Fecha fechaComparar = new Fecha();

		if(tramiteTraficoTransmisionBean.getFechaContrato()!=null && !tramiteTraficoTransmisionBean.getFechaContrato().isfechaNula()){
			fechaComparar = tramiteTraficoTransmisionBean.getFechaContrato();
		} else if(tramiteTraficoTransmisionBean.getFechaFactura()!=null && !tramiteTraficoTransmisionBean.getFechaFactura().isfechaNula()){
			fechaComparar = tramiteTraficoTransmisionBean.getFechaFactura();
		}

		if (validaDocumentacionIntervinienteCaducada(tramiteTraficoTransmisionBean.getAdquirienteBean(),fechaComparar)){
			validacionNIFs.add(ADQUIRIENTE);
		}

		validacionNIFs.addAll(validaNIFSinComprador(tramiteTraficoTransmisionBean));
		return validacionNIFs;
	}

	public List<String> validaNIFSinComprador(TramiteTraficoTransmisionBean tramiteTraficoTransmisionBean) throws ParseException{
		//Obtengo los NIF de los intervinientes que hay que comprobar su fecha de caducidad.
		//Exento de validar el comprador
		List<String> validacionNIFs = new ArrayList<>();
		Fecha fechaComparar = new Fecha();

		if(tramiteTraficoTransmisionBean.getFechaContrato()!=null && !tramiteTraficoTransmisionBean.getFechaContrato().isfechaNula()){
			fechaComparar = tramiteTraficoTransmisionBean.getFechaContrato();
		} else if(tramiteTraficoTransmisionBean.getFechaFactura()!=null && !tramiteTraficoTransmisionBean.getFechaFactura().isfechaNula()){
			fechaComparar = tramiteTraficoTransmisionBean.getFechaFactura();
		}

		if (validaDocumentacionIntervinienteCaducada(tramiteTraficoTransmisionBean.getRepresentanteAdquirienteBean(),fechaComparar)){
			validacionNIFs.add(REPRESENTANTE_ADQUIRIENTE);
		}

		if (validaDocumentacionIntervinienteCaducada(tramiteTraficoTransmisionBean.getTransmitenteBean(),fechaComparar)){
			validacionNIFs.add(TRANSMITENTE);
		}

		if (validaDocumentacionIntervinienteCaducada(tramiteTraficoTransmisionBean.getConductorHabitualBean(),fechaComparar)){
			validacionNIFs.add(CONDUCTOR_HABITUAL);
		}

		if (validaDocumentacionIntervinienteCaducada(tramiteTraficoTransmisionBean.getRepresentanteTransmitenteBean(),fechaComparar)){
			validacionNIFs.add(REPRESENTANTE_TRANSMITENTE);
		}

		if (validaDocumentacionIntervinienteCaducada(tramiteTraficoTransmisionBean.getPrimerCotitularTransmitenteBean(),fechaComparar)){
			validacionNIFs.add(PRIMER_COTITULAR);
		}

		if (validaDocumentacionIntervinienteCaducada(tramiteTraficoTransmisionBean.getSegundoCotitularTransmitenteBean(),fechaComparar)){
			validacionNIFs.add(SEGUNDO_COTITULAR);
		}

		if (validaDocumentacionIntervinienteCaducada(tramiteTraficoTransmisionBean.getRepresentantePrimerCotitularTransmitenteBean(),fechaComparar)){
			validacionNIFs.add(REPRESENTANTE_PRIMER_COTITULAR_TRANSMITENTE);
		}

		if (validaDocumentacionIntervinienteCaducada(tramiteTraficoTransmisionBean.getRepresentanteSegundoCotitularTransmitenteBean(),fechaComparar)){
			validacionNIFs.add(REPRESENTANTE_SEGUNDO_COTITULAR_TRANSMITENTE);
		}

		if (validaDocumentacionIntervinienteCaducada(tramiteTraficoTransmisionBean.getPoseedorBean(),fechaComparar)){
			validacionNIFs.add(COMPRAVENTA);
		}

		if (validaDocumentacionIntervinienteCaducada(tramiteTraficoTransmisionBean.getRepresentantePoseedorBean(),fechaComparar)){
			validacionNIFs.add(REPRESENTANTE_COMPRAVENTA);
		}

		return validacionNIFs;
	}
	// Validación fecha caducidad NIFs
	public ResultBean comprobarCaducidadNifs(TramiteTraficoTransmisionBean tramiteTraficoTransmisionBean, boolean validacion) throws ParseException {
		ResultBean resultado = new ResultBean(false);
		Fecha fechaActual = utilesFecha.getFechaActual();
		java.util.Date fechaAdvertencia = utilesFecha.getDate((utilesFecha.sumaDias(fechaActual, "-30")));
		List<TipoInterviniente> listaIntervinientes = new ArrayList<>();
		listaIntervinientes.add(TipoInterviniente.Adquiriente);
		listaIntervinientes.add(TipoInterviniente.RepresentanteAdquiriente);
		listaIntervinientes.add(TipoInterviniente.ConductorHabitual);
		listaIntervinientes.add(TipoInterviniente.Transmitente);
		listaIntervinientes.add(TipoInterviniente.RepresentanteTransmitente);
		listaIntervinientes.add(TipoInterviniente.CotitularTransmision);
		listaIntervinientes.add(TipoInterviniente.Compraventa);
		listaIntervinientes.add(TipoInterviniente.RepresentanteCompraventa);
		listaIntervinientes.add(TipoInterviniente.Arrendatario);
		listaIntervinientes.add(TipoInterviniente.RepresentanteArrendatario);
		Persona persona = null;
		for (TipoInterviniente interviniente : listaIntervinientes) {
			switch (interviniente) {
				case Adquiriente:
					if (tramiteTraficoTransmisionBean != null && tramiteTraficoTransmisionBean.getAdquirienteBean() != null
							&& tramiteTraficoTransmisionBean.getAdquirienteBean().getPersona() != null) {
						persona = tramiteTraficoTransmisionBean.getAdquirienteBean().getPersona();
					}
					break;
				case RepresentanteAdquiriente:
					if (tramiteTraficoTransmisionBean != null && tramiteTraficoTransmisionBean.getRepresentanteAdquirienteBean() != null
							&& tramiteTraficoTransmisionBean.getRepresentanteAdquirienteBean().getPersona() != null) {
						persona = tramiteTraficoTransmisionBean.getRepresentanteAdquirienteBean().getPersona();
					}
					break;
				case ConductorHabitual:
					if (tramiteTraficoTransmisionBean != null && tramiteTraficoTransmisionBean.getConductorHabitualBean() != null
							&& tramiteTraficoTransmisionBean.getConductorHabitualBean().getPersona() != null) {
						persona = tramiteTraficoTransmisionBean.getConductorHabitualBean().getPersona();
					}
					break;
				case Transmitente:
					if (tramiteTraficoTransmisionBean != null && tramiteTraficoTransmisionBean.getTransmitenteBean() != null
							&& tramiteTraficoTransmisionBean.getTransmitenteBean().getPersona() != null) {
						persona = tramiteTraficoTransmisionBean.getTransmitenteBean().getPersona();
					}
					break;
				case RepresentanteTransmitente:
					if (tramiteTraficoTransmisionBean != null && tramiteTraficoTransmisionBean.getRepresentanteTransmitenteBean() != null
							&& tramiteTraficoTransmisionBean.getRepresentanteTransmitenteBean().getPersona() != null) {
						persona = tramiteTraficoTransmisionBean.getRepresentanteTransmitenteBean().getPersona();
					}
					break;
				case CotitularTransmision:
					if (tramiteTraficoTransmisionBean != null && tramiteTraficoTransmisionBean.getPrimerCotitularTransmitenteBean() != null
							&& tramiteTraficoTransmisionBean.getPrimerCotitularTransmitenteBean().getPersona() != null) {
						persona = tramiteTraficoTransmisionBean.getPrimerCotitularTransmitenteBean().getPersona();
					}
					break;
				case Compraventa:
					if (tramiteTraficoTransmisionBean != null && tramiteTraficoTransmisionBean.getPoseedorBean() != null
							&& tramiteTraficoTransmisionBean.getPoseedorBean().getPersona() != null) {
						persona = tramiteTraficoTransmisionBean.getPoseedorBean().getPersona();
					}
					break;
				case RepresentanteCompraventa:
					if (tramiteTraficoTransmisionBean != null && tramiteTraficoTransmisionBean.getRepresentantePoseedorBean() != null
							&& tramiteTraficoTransmisionBean.getRepresentantePoseedorBean().getPersona() != null) {
						persona = tramiteTraficoTransmisionBean.getRepresentantePoseedorBean().getPersona();
					}
					break;
				case Arrendatario:
					if (tramiteTraficoTransmisionBean != null && tramiteTraficoTransmisionBean.getArrendatarioBean() != null
							&& tramiteTraficoTransmisionBean.getArrendatarioBean().getPersona() != null) {
						persona = tramiteTraficoTransmisionBean.getArrendatarioBean().getPersona();
					}
					break;
				case RepresentanteArrendatario:
					if (tramiteTraficoTransmisionBean != null && tramiteTraficoTransmisionBean.getRepresentanteArrendatarioBean() != null
							&& tramiteTraficoTransmisionBean.getRepresentanteArrendatarioBean().getPersona() != null) {
						persona = tramiteTraficoTransmisionBean.getRepresentanteArrendatarioBean().getPersona();
					}
					break;
				default:
					persona = null;
					break;
			}
			ResultBean resultValNif = validarCaducidadNif(persona,tramiteTraficoTransmisionBean,
					validacion, interviniente,fechaActual,fechaAdvertencia);
			if (!resultado.getError() && resultValNif.getError()) {
				resultado.setError(true);
			}
			if (resultValNif.getListaMensajes() != null && !resultValNif.getListaMensajes().isEmpty()) {
				for (String mensaje : resultValNif.getListaMensajes()) {
					resultado.addMensajeALista(mensaje);
				}
			}
		}
		return resultado;
	}

	public ResultBean validarCaducidadNif(Persona persona, TramiteTraficoTransmisionBean tramiteTraficoTransmisionBean, boolean validacion, TipoInterviniente interviniente, Fecha fechaActual, Date fechaAdvertencia){
		ResultBean resultado = new ResultBean(false);
		boolean sinFechaFactura = false;
		boolean sinFechaContrato = false;
		try{
			if (persona != null && persona.getFechaCaducidadNif() != null && persona.getFechaCaducidadNif().getDate() != null) {
				fechaAdvertencia = utilesFecha.getDate(utilesFecha.sumaDias(persona.getFechaCaducidadNif(), "-30"));
				// Si la fecha de caducidad es después de la fecha actual, pero anterior a la fecha de advertencia (está en el rango), mensaje de próximo a caducar
				if (!validacion && persona.getFechaCaducidadNif().getDate().after(fechaActual.getDate()) && persona.getFechaCaducidadNif().getDate().before(fechaAdvertencia)) {
					resultado.addMensajeALista("NIF del " + interviniente.getNombreEnum() + " próximo a caducar");
					if (persona.getCodigoMandato() != null) {
						resultado.addMensajeALista(" Recuerde que esta persona tiene asociado el código de mandato: " + persona.getCodigoMandato());
					}
				} else if (persona.getFechaCaducidadNif().getDate().before(fechaActual.getDate())) {
					// Mantis 19850. David Sierra: Fecha de Factura prevalece sobre fecha de caducidad de los NIFs
					if(tramiteTraficoTransmisionBean.getFechaFactura() != null && !tramiteTraficoTransmisionBean.getFechaFactura().isfechaNula()){
						if (persona.getFechaCaducidadNif().getDate().getTime()<(tramiteTraficoTransmisionBean.getFechaFactura().getDate().getTime())){
							if(!ModoAdjudicacion.tipo3.getValorEnum().equals(tramiteTraficoTransmisionBean.getModoAdjudicacion().getValorEnum())){
								resultado.addMensajeALista("NIF del " + interviniente.getNombreEnum() + " caducado");
								resultado.setError(true);
							}else{
								resultado.setError(false);
							}
						}
					} else {
						sinFechaFactura = true;
					}
					if (tramiteTraficoTransmisionBean.getFechaContrato() != null && !tramiteTraficoTransmisionBean.getFechaContrato().isfechaNula()){
						if (persona.getFechaCaducidadNif().getDate().getTime()<(tramiteTraficoTransmisionBean.getFechaContrato().getDate().getTime())){
							if(!ModoAdjudicacion.tipo3.getValorEnum().equals(tramiteTraficoTransmisionBean.getModoAdjudicacion().getValorEnum())){
								resultado.addMensajeALista("NIF del " + interviniente.getNombreEnum() + " caducado");
								resultado.setError(true);
							}else{
								resultado.setError(false);
							}
						}
					}else{
						sinFechaContrato = true;
					}
					if (sinFechaFactura && sinFechaContrato){
						if(!ModoAdjudicacion.tipo3.getValorEnum().equals(tramiteTraficoTransmisionBean.getModoAdjudicacion().getValorEnum())){
							resultado.addMensajeALista("NIF del " + interviniente.getNombreEnum() + " caducado");
							resultado.setError(true);
						}else{
							resultado.setError(false);
						}
					}
				}
				if (resultado.getListaMensajes() != null && !resultado.getListaMensajes().isEmpty()) {
					// Comprobamos que tenga documento alternativo, para el caso en que el DNI esté caducado no falle
					if (persona.isOtroDocumentoIdentidad()) {
						resultado.setError(persona.getFechaCaducidadAlternativo() == null || persona.getFechaCaducidadAlternativo().isfechaNula());
					} else {
						resultado.setError(true);
					}
				}
			}
		} catch (ParseException e) {
			log.error("Ha sucedido un error a la hora de validar la caducidad del NIF, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de validar la caducidad del NIF del " + interviniente.getNombreEnum());
		}
		return resultado;
	}

	// Mantis 19172 y 21598. David Sierra: Validar fecha nacimiento de los intervinientes
	public String comprobarFormatoFechas(TramiteTraficoTransmisionBean tramiteTraficoTransmisionBean) throws ParseException {
		String resultado = CADENA_VACIA;
		String resultadoValidacionFecha;
		List<TipoInterviniente> listaIntervinientes = new ArrayList<>();
		listaIntervinientes.add(TipoInterviniente.Adquiriente);
		listaIntervinientes.add(TipoInterviniente.ConductorHabitual);
		listaIntervinientes.add(TipoInterviniente.Transmitente);
		listaIntervinientes.add(TipoInterviniente.CotitularTransmision);
		listaIntervinientes.add(TipoInterviniente.Compraventa);
		listaIntervinientes.add(TipoInterviniente.Arrendatario);
		Persona persona = null;
		for (TipoInterviniente interviniente : listaIntervinientes) {
			switch (interviniente) {
				case Adquiriente:
					if (tramiteTraficoTransmisionBean != null && tramiteTraficoTransmisionBean.getAdquirienteBean() != null && tramiteTraficoTransmisionBean.getAdquirienteBean().getPersona() != null) {
						persona = tramiteTraficoTransmisionBean.getAdquirienteBean().getPersona();
					}
					break;
				case ConductorHabitual:
					if (tramiteTraficoTransmisionBean != null && tramiteTraficoTransmisionBean.getConductorHabitualBean() != null
							&& tramiteTraficoTransmisionBean.getConductorHabitualBean().getPersona() != null) {
						persona = tramiteTraficoTransmisionBean.getConductorHabitualBean().getPersona();
					}
					break;
				case Transmitente:
					if (tramiteTraficoTransmisionBean != null && tramiteTraficoTransmisionBean.getTransmitenteBean() != null
							&& tramiteTraficoTransmisionBean.getTransmitenteBean().getPersona() != null) {
						persona = tramiteTraficoTransmisionBean.getTransmitenteBean().getPersona();
					}
					break;
				case CotitularTransmision:
					if (tramiteTraficoTransmisionBean != null && tramiteTraficoTransmisionBean.getPrimerCotitularTransmitenteBean() != null
							&& tramiteTraficoTransmisionBean.getPrimerCotitularTransmitenteBean().getPersona() != null) {
						persona = tramiteTraficoTransmisionBean.getPrimerCotitularTransmitenteBean().getPersona();
					}
					break;
				case Compraventa:
					if (tramiteTraficoTransmisionBean != null && tramiteTraficoTransmisionBean.getPoseedorBean() != null && tramiteTraficoTransmisionBean.getPoseedorBean().getPersona() != null) {
						persona = tramiteTraficoTransmisionBean.getPoseedorBean().getPersona();
					}
					break;
				case Arrendatario:
					if (tramiteTraficoTransmisionBean != null && tramiteTraficoTransmisionBean.getArrendatarioBean() != null
							&& tramiteTraficoTransmisionBean.getArrendatarioBean().getPersona() != null) {
						persona = tramiteTraficoTransmisionBean.getArrendatarioBean().getPersona();
					}
					break;
				default:
					persona = null;
					break;
			}
			resultadoValidacionFecha = CADENA_VACIA;
			if (persona != null && persona.getFechaNacimientoBean() != null && persona.getFechaNacimientoBean().getDia() != null
					&& persona.getFechaNacimientoBean().getMes() != null && persona.getFechaNacimientoBean().getAnio() != null) {
				String dia = persona.getFechaNacimientoBean().getDia();
				String mes = persona.getFechaNacimientoBean().getMes();
				String anio = persona.getFechaNacimientoBean().getAnio();
				int diaNum = 0;
				int mesNum = 0;
				if (!dia.isEmpty()) {
					diaNum = Integer.parseInt(dia);
					if (diaNum < 0 || diaNum > 31) {
						resultadoValidacionFecha = "El formato de la fecha de nacimiento del " + interviniente.getNombreEnum()
						+ " no es válida. El día debe estar comprendido entre el 1 y el 31";
					}
				}
				if (!mes.isEmpty()) {
					mesNum = Integer.parseInt(mes);
					if (mesNum < 1 || mesNum > 12) {
						resultadoValidacionFecha = "El formato de la fecha de nacimiento del " + interviniente.getNombreEnum()
						+ " no es válida. El mes debe estar comprendido entre el 1 y el 12";
					}
				}
				if(!anio.isEmpty() && anio.length() != 4) {
					resultadoValidacionFecha = "El formato de la fecha de nacimiento del " + interviniente.getNombreEnum()
					+ " no es válida. El año debe tener 4 dígitos.";
				}
			}
			if (resultadoValidacionFecha != null && !resultadoValidacionFecha.equals(CADENA_VACIA)) {
				resultado += "\n" + resultadoValidacionFecha;
			}

		}
		return resultado;
	}

	/**
	 * Validación de documentación para Intervinientes
	 * */
	public boolean validaDocumentacionIntervinienteCaducada(IntervinienteTrafico interviniente, Fecha fechaContratoFactura) throws ParseException{
		if (interviniente != null && interviniente.getPersona() != null && interviniente.getPersona().getIndefinido() != null
				&& !interviniente.getPersona().getIndefinido().equals(true)) {
			return validaDocumentacionPersonaCaducada(interviniente.getPersona(), fechaContratoFactura);
		} else {
			return false;
		}
	}

	/**
	 * Validación de documentación alternativa para Persona
	 * */
	public boolean validaDocumentacionPersonaCaducada(Persona persona, Fecha fechaContratoFactura) throws ParseException{
		if (persona == null){
			return false;
		}
		if (persona.getTipoDocumentoAlternativo()!=null && persona.getFechaCaducidadAlternativo() != null) {
			return validaCaducidadNIF(persona.getFechaCaducidadAlternativo(), fechaContratoFactura);
		} else if (persona.getFechaCaducidadNif() != null ) {
			return validaCaducidadNIF(persona.getFechaCaducidadNif(), fechaContratoFactura);
		}
		return false;
	}

	/**
	 * Validación de la caducidad del NIF con respecto a la fecha de contrato/factura
	 * */
	public boolean validaCaducidadNIF(Fecha fechaCaducidadNIF, Fecha fechaContratoFactura) throws ParseException {
		if (fechaCaducidadNIF == null || fechaCaducidadNIF.isfechaNula() || fechaContratoFactura == null || fechaContratoFactura.isfechaNula()) {
			return false;
		}
		Calendar calendar = new GregorianCalendar();
		Date fechaComparar = fechaContratoFactura.getDate();
		calendar.setTime(fechaComparar);

		return ((fechaComparar.compareTo(fechaCaducidadNIF.getFecha())) > 0); // NIF caducado
	}

	public ResultBean validarFechaFacturacionContrato(TramiteTraficoTransmisionBean tramite){
		ResultBean resultado = new ResultBean();
		ArrayList<String> errores = new ArrayList<>();
		resultado.setError(Boolean.FALSE);
		/*Validación de cumplimentación de fechas*/
		/*No se encuentra ninguna de las fechas, cuando debe de estar una de las dos cumplimentada*/
		if (tramite.getTipoTransferencia()!=null && !TipoTransferencia.tipo4.equals(tramite.getTipoTransferencia()) && !TipoTransferencia.tipo5.equals(tramite.getTipoTransferencia()) && (!ModoAdjudicacion.tipo3.getValorEnum().equals(tramite.getModoAdjudicacion().getValorEnum()))) {
			if (tramite.getFechaContrato()==null && tramite.getFechaFactura() == null) {
				errores.add("Falta fecha de factura o contrato");
			}
			/*Las dos fechas han sido cumplimentadas, caso que no es posible*/
			if (tramite.getFechaContrato()!=null && tramite.getFechaFactura() != null) {
				errores.add("No se pueden presentar las fechas de factura y contrato a la vez");
				errores.add("Solamente es válida una de las dos en función del documento aportado");
			}
		}
		resultado.addListaMensajes(errores);
		return resultado;
	}

	public List<String> compruebafechaValidezNifSiRellena(TramiteTraficoTransmisionBean tramite){
		List<String> adquirientesFallidos = new ArrayList<>();

		boolean excepcionTransmitente = false;
		boolean excepcionAdquiriente = false;

		int tipoTransferencia = 5; // Se fuerza el error.
		if (tramite.getTipoTransferencia() != null) {
			tipoTransferencia = Integer.parseInt(tramite.getTipoTransferencia().getValorEnum());
		}

		switch (tipoTransferencia){
			case 1:
				//Cambio de titularidad completo
				if (tramite.getModoAdjudicacion()!=null && tramite.getModoAdjudicacion().getValorEnum().equals(ModoAdjudicacion.tipo3.toString())
						&& tramite.getAcreditaHerenciaDonacion()!=null && tramite.getAcreditaHerenciaDonacion().equals(AcreditacionTrafico.Herencia)){
					// Marco como excepción para el transmitente de cambio de la titularidad, si es fallecimiento
					excepcionTransmitente = true;
				}
				break;
			case 2: // Finalización tras una notificación
			case 3: // Interviene Compra-Venta
			// Excepción temporal. Cuando indiquen que no es necesario eliminar el case 5.
			case 5: // Entrega compra-venta
				excepcionTransmitente = true;
				break;
			case 4: // Notificación cambio de titularidad
				excepcionAdquiriente = true;
				break;
		}

		if (tramite != null) {
			if (tramite.getAdquirienteBean()!=null && !excepcionAdquiriente){
				if (!compruebaInterviniente(tramite.getAdquirienteBean())){
					adquirientesFallidos.add(TipoInterviniente.Adquiriente.getNombreEnum());
				}
				if (tramite.getRepresentanteAdquirienteBean() != null
						&& !compruebaInterviniente(tramite.getRepresentanteAdquirienteBean())) {
					adquirientesFallidos.add(TipoInterviniente.RepresentanteAdquiriente.getNombreEnum());
				}
			}

			if (tramite.getConductorHabitualBean() != null
					&& !compruebaInterviniente(tramite.getConductorHabitualBean())) {
				adquirientesFallidos.add(TipoInterviniente.ConductorHabitual.getNombreEnum());
			}

			if (tramite.getTransmitenteBean()!=null && !excepcionTransmitente){
				if (!compruebaInterviniente(tramite.getTransmitenteBean())){
					adquirientesFallidos.add(TipoInterviniente.Transmitente.getNombreEnum());
				}

				if (tramite.getRepresentanteTransmitenteBean() != null
						&& !compruebaInterviniente(tramite.getRepresentanteTransmitenteBean())) {
					adquirientesFallidos.add(TipoInterviniente.RepresentanteTransmitente.getNombreEnum());
				}
			}

			if (tramite.getPrimerCotitularTransmitenteBean() != null
					&& !compruebaInterviniente(tramite.getPrimerCotitularTransmitenteBean())) {
				adquirientesFallidos.add(PRIMER_COTITULAR);
			}

			if (tramite.getSegundoCotitularTransmitenteBean() != null
					&& !compruebaInterviniente(tramite.getSegundoCotitularTransmitenteBean())) {
				adquirientesFallidos.add(SEGUNDO_COTITULAR);
			}

			if (tramite.getPoseedorBean()!=null){
				if (!compruebaInterviniente(tramite.getPoseedorBean())){
					adquirientesFallidos.add(POSEEDOR);
				}

				if (tramite.getRepresentantePoseedorBean() != null
						&& !compruebaInterviniente(tramite.getRepresentantePoseedorBean())) {
					adquirientesFallidos.add(REPRESENTANTE_POSEEDOR);
				}
			}

			if (tramite.getArrendatarioBean()!=null){
				if (!compruebaInterviniente(tramite.getArrendatarioBean())){
					adquirientesFallidos.add(TipoInterviniente.Arrendatario.getNombreEnum());
				}

				if (tramite.getRepresentanteArrendatarioBean() != null
						&& !compruebaInterviniente(tramite.getRepresentanteArrendatarioBean())) {
					adquirientesFallidos.add(TipoInterviniente.RepresentanteArrendatario.getNombreEnum());
				}
			}
		}
		return adquirientesFallidos;
	}

	public boolean compruebaInterviniente(trafico.beans.IntervinienteTrafico interviniente){
		if (interviniente.getPersona()!=null && interviniente.getPersona().getNif()!=null && interviniente.getPersona().getTipoPersona()!=null
				&& interviniente.getPersona().getTipoPersona().equals(TipoPersona.Fisica) && interviniente.getPersona().getIndefinido()!=null
				&& !interviniente.getPersona().getIndefinido().equals(true)){
			if (interviniente.getPersona().getTipoDocumentoAlternativo()==null){
				if (interviniente.getPersona().getFechaCaducidadNif()==null){
					return false;
				}
			}else if (interviniente.getPersona().getFechaCaducidadAlternativo()==null){
				return false;
			}
		}
		return true;
	}

	/**
	 * Comprueba un trámite que le pasemos
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ResultBean validarTramiteTransmisionMetodo(TramiteTraficoTransmisionBean tramite) throws Throwable{

		ResultBean resultadoComprobar = new ResultBean();
		String nombreFicheroComprobacion = CADENA_VACIA;

		log.info("Transmision Tramite Trafico: inicio--validarTramiteTransmisionMetodo:");

		ResultBean resultadoMetodo = validar(tramite);
		if(!resultadoMetodo.getError()){
			//Se han validado los anagramas y el trámite
			resultadoComprobar.setError(false);
			for(String mensaje:resultadoMetodo.getListaMensajes()){
				resultadoComprobar.setMensaje(mensaje.replace("--", " -"));
			}

			//Se genera el XML para el CheckCTIT si el estado es iniciado...
			if(tramite.getTramiteTraficoBean().getEstado().getValorEnum().equals(EstadoTramiteTrafico.Iniciado.getValorEnum())){
				String tipoFirma = gestorPropiedades.valorPropertie(ConstantesFirma.TIPO_FIRMA_TRANSMISION);
				Map<String,Object> respuestaGenerarXMLJaxb = generarXMLCheckCTIT(tramite,tramite.getTramiteTraficoBean().getNumColegiado());
				if(respuestaGenerarXMLJaxb.get(ERRORES) != null){ //Si ha habido errores, los mostramos...
					List<String> errores = (List<String>) respuestaGenerarXMLJaxb.get(ERRORES);
					resultadoComprobar.setError(true);
					for(String error:errores){
						resultadoComprobar.setMensaje(error.replace("--", " -"));
					}
				} else if(tipoFirma.equals(ConstantesFirma.TIPO_FIRMA_HSM)){ //Si todo ha ido bien y la firma es con HSM...
					SolicitudTramite solicitudTramite = (SolicitudTramite)respuestaGenerarXMLJaxb.get(SOLICITUD_TRAMITE);

					nombreFicheroComprobacion = (String)respuestaGenerarXMLJaxb.get(FICHERO);
					XmlCheckCTITFactory xmlCheckCTITFactory = new XmlCheckCTITFactory();
					try {
						Marshaller marshaller = xmlCheckCTITFactory.getContext().createMarshaller();
						marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
						marshaller.marshal(solicitudTramite, new FileOutputStream(nombreFicheroComprobacion));
					} catch (JAXBException e) {
						resultadoComprobar.setError(true);
						resultadoComprobar.setMensaje(HA_OCURRIDO_UN_ERROR_AL_PARSEAR_EL_XML);
					} catch (FileNotFoundException e){
						resultadoComprobar.setError(true);
						resultadoComprobar.setMensaje("No se ha encontrado el fichero XML");
					}

					//Lo validamos contra el XSD
					File fichero = new File(nombreFicheroComprobacion);
					// Ricardo Rodriguez. Incidencia 3149. 20/12/2012
					try{
						xmlCheckCTITFactory.validarXMLCheckCTIT(fichero);
					}catch(OegamExcepcion ex){
						resultadoComprobar.setError(true);
						resultadoComprobar.setMensaje(ex.getMensajeError1());
						return resultadoComprobar;
					}
					// XmlCheckCTITFactory.validarXMLCheckCTIT(fichero);
					// Fin. Ricardo Rodriguez. Incidencia 3149. 20/12/2012

					//Se añade la solicitud a la cola
					return crearSolicitudMetodo(tramite, fichero.getName(), ConstantesProcesos.PROCESO_CHECKCTIT, resultadoComprobar);
				}
			}
		}else {
			resultadoComprobar.setError(resultadoMetodo.getError());
			resultadoComprobar.setMensaje("Ha habido un problema al validar el trámite");
			for(String mensaje:resultadoMetodo.getListaMensajes()){
				resultadoComprobar.setMensaje(mensaje);
			}
		}

		return resultadoComprobar;
	}

	/**
	 * Añade una fila en la tabla de colas para su envío a DGT
	 * @param nombreXml
	 * @return
	 */
	public ResultBean crearSolicitudMetodo(TramiteTraficoTransmisionBean tramite, String nombreXml,	String nombreProceso, ResultBean resultadoComprobar){
		ResultBean resultadoMetodo = new ResultBean();
		resultadoMetodo.setError(false);
		BeanPQCrearSolicitud beanPQCrearSolicitud = new BeanPQCrearSolicitud();
		beanPQCrearSolicitud.setP_ID_TRAMITE(tramite.getTramiteTraficoBean().getNumExpediente());
		beanPQCrearSolicitud.setP_ID_USUARIO(utilesColegiado.getIdUsuarioSessionBigDecimal());
		beanPQCrearSolicitud.setP_TIPO_TRAMITE(TipoTramiteTrafico.TransmisionElectronica.getValorEnum());
		beanPQCrearSolicitud.setP_XML_ENVIAR(nombreXml);
		beanPQCrearSolicitud.setP_PROCESO(nombreProceso);

		// DRC@12-03-2013 Incidencia Mantis: 3853
		//ResultBean resultBeanCambiarEstado = cambiaEstadoPendienteDgt(nombreProceso, tramite);

		BeanPQCambiarEstadoTramite beanPQCambiarEstadoTramite = new BeanPQCambiarEstadoTramite();
		if(nombreProceso.equals(ConstantesProcesos.PROCESO_CHECKCTIT)){
			beanPQCambiarEstadoTramite.setP_ESTADO(new BigDecimal(EstadoTramiteTrafico.Pendiente_Check_Ctit.getValorEnum()));
		} else {
			beanPQCambiarEstadoTramite.setP_ESTADO(new BigDecimal(EstadoTramiteTrafico.Pendiente_Envio.getValorEnum()));
		}
		beanPQCambiarEstadoTramite.setP_NUM_EXPEDIENTE(tramite.getTramiteTraficoBean().getNumExpediente());
		HashMap<String,Object> resultadoCambiarEstado = getModeloTrafico().cambiarEstadoTramite(beanPQCambiarEstadoTramite);
		ResultBean resultBeanCambiarEstado = (ResultBean) resultadoCambiarEstado.get(ConstantesPQ.RESULTBEAN);

		if (resultBeanCambiarEstado.getError()) {
			resultadoMetodo.setError(true);
			resultadoMetodo.setMensaje("Error al cambiar el estado del trámite");
			resultadoMetodo.addListaMensajes(resultBeanCambiarEstado.getListaMensajes());
		} else {
			HashMap<String,Object> resultadoSolicitud = getModeloSolicitud().crearSolicitud(beanPQCrearSolicitud);
			ResultBean resultBean = (ResultBean) resultadoSolicitud.get(ConstantesPQ.RESULTBEAN);
			if (!resultBean.getError()) {
				resultadoMetodo.setMensaje(SOLICITUD_CREADA_CORRECTAMENTE);
			} else {
				try {
					BeanPQCAMBIAR_ESTADO_S_V beanCambEstado = UtilesConversionesTrafico.convertirTramiteTraficoABeanPQCAMB_ESTADO_S_V(tramite.getTramiteTraficoBean());
					beanCambEstado.setP_ESTADO(new BigDecimal(tramite.getTramiteTraficoBean().getEstado().getValorEnum()));
					beanCambEstado.execute();
					if (!beanCambEstado.getP_CODE().toString().equals("0")) {
						log.error("Se ha producido un error al actualizar al estado anterior el trámite." + beanCambEstado.getP_SQLERRM());
					}
				} catch (Exception e) {
					log.error("Se ha producido un error al actualizar al estado anterior el trámite, ya que no se ha podido crear la solicitud. Error: " + e);
				}

				resultadoMetodo.setError(true);
				resultadoMetodo.setMensaje(ERROR_AL_CREAR_LA_SOLICITUD);
				//resultadoMetodo.addListaMensajes(resultBean.getListaMensajes());
			}
		}

		// 15-10-2012. Ricardo Rodriguez. Incidencia : 0002589
		if(resultadoComprobar != null && resultadoComprobar.getListaMensajes() != null && !resultadoComprobar.getListaMensajes().isEmpty()){
			for(String mensajeComprobar : resultadoComprobar.getListaMensajes()){
				resultadoMetodo.getListaMensajes().add(mensajeComprobar);
			}
		}
		// FIN 15-10-2012. Ricardo Rodríguez. Incidencia : 0002589
		return resultadoMetodo;
	}

	/**
	 * Método que se va a utilizar para la tramitación telemática en bloque.
	 * @param idContrato
	 * @param idUsuario
	 * @return
	 * @throws Throwable
	 */
	@SuppressWarnings({ "unchecked" })
	public ResultBean transmisionTelematicaMetodo(TramiteTraficoTransmisionBean tramite, BigDecimal idUsuario, BigDecimal idContrato) throws Throwable{
		ResultBean resultadoTransmision = new ResultBean();

		//String nombreFicheroTransTelematica = CADENA_VACIA;
		log.info("Transmision Tramite Trafico: inicio--transmisionTelematica:");

		// Vemos qué tipo de transferencia es (Notificación o Trade = Créditos de Baja) para saber qué tipo de Créditos contar
		String tipoCredito;
		if(TipoTransferencia.tipo5.getValorEnum().equals(tramite.getTipoTransferencia().getValorEnum())
				|| TipoTransferencia.tipo4.getValorEnum().equals(tramite.getTipoTransferencia().getValorEnum()))
			tipoCredito = TipoTramiteTrafico.Baja.getValorEnum();
		else
			tipoCredito = TipoTramiteTrafico.TransmisionElectronica.getValorEnum();

		// Validamos que tenga créditos suficientes
		String gestionarConAM = gestorPropiedades.valorPropertie("gestionar.conAm");
		if ("2631".equals(tramite.getTramiteTraficoBean().getNumColegiado()) && SI.equals(gestionarConAM)) {
			tramite.getTramiteTraficoBean().setIdUsuario(idUsuario);
			resultadoTransmision = servicioTramiteTraficoTransmision.cambiarEstadoTramite(tramite);
			if (!resultadoTransmision.getError()) {
				String proceso = null;
				if (TipoTransferencia.tipo1.getValorEnum().equals(tramite.getTipoTransferencia().toString()) || TipoTransferencia.tipo2.getValorEnum().equals(tramite.getTipoTransferencia().toString())
					|| TipoTransferencia.tipo3.getValorEnum().equals(tramite.getTipoTransferencia().toString())) {
					proceso = ProcesosAmEnum.FULLCTIT.getValorEnum();
				} else if(TipoTransferencia.tipo4.getValorEnum().equals(tramite.getTipoTransferencia().toString())) {
					proceso = ProcesosAmEnum.NOTIFICATIONCTIT.getValorEnum();
				} else if(TipoTransferencia.tipo5.getValorEnum().equals(tramite.getTipoTransferencia().toString())) {
					proceso = ProcesosAmEnum.TRADECTIT.getValorEnum();
				}
				ResultBean resulCola = servicioCola.crearSolicitud(proceso, null,gestorPropiedades.valorPropertie("nombreHostSolicitud") ,TipoTramiteTrafico.TransmisionElectronica.getValorEnum(),
						tramite.getTramiteTraficoBean().getNumExpediente().toString(),tramite.getTramiteTraficoBean().getIdUsuario(), null, tramite.getTramiteTraficoBean().getIdContrato());
				if (resulCola.getError()) {
					resultadoTransmision.setError(Boolean.TRUE);
					resultadoTransmision.setMensaje("Ha sucedido un error a la hora de encolar la transmisión para su finalización.");
				}
			}else {
				resultadoTransmision.setError(Boolean.TRUE);
				resultadoTransmision.setMensaje("Ha ocurrido un error a la hora de cambiar el estado.");
			}
		} else {
			Map<String, Object> resultadoMetodo = getModeloCreditosTrafico().validarCreditosPorNumColegiado(
					idContrato.toString(), new BigDecimal(1), tipoCredito);
			ResultBean resultBean = (ResultBean) resultadoMetodo.get(ConstantesPQ.RESULTBEAN);
			if (resultBean.getError()) {
				resultadoTransmision.setError(true);
				resultadoTransmision.setMensaje("Créditos insuficientes para realizar la operación");
				return resultadoTransmision;
			}

		//	String tipoFirma = gestorPropiedades.valorPropertie(ConstantesFirma.TIPO_FIRMA_TRANSMISION);

			String deshabilitarPQ = gestorPropiedades.valorPropertie("deshabilitar.pq.dgtws");

			String nPasos = CADENA_VACIA;
			if (deshabilitarPQ != null && SI.equals(deshabilitarPQ)) {
				DatosCTITDto datosCTITDto = servicioTramiteTraficoTransmision.datosCTIT(tramite.getTramiteTraficoBean().getNumExpediente());
				nPasos = datosCTITDto.getPasos();
			} else {
				DatosCTITBean datosCTITBean = getDatosCTIT(tramite);
				nPasos = datosCTITBean.getPasos();
			}

			if (SI.equalsIgnoreCase(gestorPropiedades.valorPropertie("nuevas.url.sega.checkCtit"))) {
				ResultadoCtitBean resultado = servicioTramiteTraficoTransmision.transmisionTelematica(tramite, nPasos, idUsuario, idContrato);
				if (resultado.getError()) {
					resultadoTransmision.setError(Boolean.TRUE);
					resultadoTransmision.setListaMensajes(resultado.getListaMensajes());
				} else {
					resultadoTransmision.setError(Boolean.FALSE);
					resultadoTransmision.setMensaje(SOLICITUD_CREADA_CORRECTAMENTE);
				}
			} else {
				// Generamos el XML
				/*Map<String,Object> respuestaGenerarXMLJaxb = null;
				// Si el número de pasos es 1, el trámite antes de ser VALIDADO TELEMÁTICAMENTE, era TRAMITABLE CON INCIDENCIAS
				if (nPasos != null && nPasos.equals(NPasos.Dos.getValorEnum())) {
					respuestaGenerarXMLJaxb = generarXMLTransmisionTelematica(tramite, Paso.UnoFullIncidencias,tramite.getTramiteTraficoBean().getNumColegiado());
				}
				// Si el número de pasos es 1, el trámite antes de ser VALIDADO TELEMÁTICAMENTE, era TRAMITABLE
				if (nPasos.equals(NPasos.Uno.getValorEnum()) || nPasos == null) {
					respuestaGenerarXMLJaxb = generarXMLTransmisionTelematica(tramite,Paso.FullTramitable,tramite.getTramiteTraficoBean().getNumColegiado());
				}

				if (respuestaGenerarXMLJaxb.get(ERRORES) != null) { // Si ha habido errores, los mostramos...
					List<String> errores = (List<String>) respuestaGenerarXMLJaxb.get(ERRORES);
					resultadoTransmision.addListaMensajes(errores);
					resultadoTransmision.setError(true);
					return resultadoTransmision;
		//		} else if(tipoFirma.equals(ConstantesFirma.TIPO_FIRMA_CERTIFICADO)){ // Si todo ha ido bien y la firma es con el applet...
		//			fileNameTransTelematica = (String)respuestaGenerarXMLJaxb.get("fichero");
		//			request.getSession().setAttribute("fileNameTransTelematica", fileNameTransTelematica);
		//			request.getSession().setAttribute("numExpediente", tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente());
		//			//Redirigimos a una página vacía puesto que se ha invocado al applet de firma
		//			return "applet";
				} else if (tipoFirma.equals(ConstantesFirma.TIPO_FIRMA_HSM)) { // Si todo ha ido bien y la firma es con HSM...
					nombreFicheroTransTelematica = (String)respuestaGenerarXMLJaxb.get(FICHERO);
					SolicitudRegistroEntrada solicitudRegistroEntrada = (SolicitudRegistroEntrada)respuestaGenerarXMLJaxb.get(SOLICITUD_REGISTRO_ENTRADA);
					Marshaller marshaller;
					XmlTransTelematicaFactory xmlTransTelematicaFactory = new XmlTransTelematicaFactory();
					try {
						marshaller = xmlTransTelematicaFactory.getContext().createMarshaller();
						marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
						marshaller.marshal(solicitudRegistroEntrada, new FileOutputStream(nombreFicheroTransTelematica));
					} catch (JAXBException e) {
						resultadoTransmision.setMensaje(HA_OCURRIDO_UN_ERROR_AL_PARSEAR_EL_XML);
					} catch (FileNotFoundException e) {
						resultadoTransmision.setMensaje("No se ha encontrado el fichero XML");
					}
					// Lo validamos contra el XSD
					File fichero = new File(nombreFicheroTransTelematica);
					String resultadoValidar = xmlTransTelematicaFactory.validarXMLTransTelematica(fichero);
					if (!ConstantesTrafico.XML_VALIDO.equals(resultadoValidar)) {
						resultadoTransmision.setError(true);
						resultadoTransmision.setMensaje("Ha ocurrido un error al validar el XML generado para realizar la transferencia telemática contra el XSD: " + resultadoValidar);
						log.error("ERROR AL VALIDAR EL XML: " + fichero + " -- " + resultadoValidar);
						return resultadoTransmision;
					}
*/
					// Se añade la solicitud a la cola de forma nueva
				resultadoTransmision = tramitar(tramite,idUsuario,idContrato);

				// Se añade la solicitud a la cola
				
					/*if (tramite.getTipoTransferencia().getValorEnum().equals(TipoTransferencia.tipo1.getValorEnum()) || // Antes tipo1
							tramite.getTipoTransferencia().getValorEnum().equals(TipoTransferencia.tipo2.getValorEnum())||
							tramite.getTipoTransferencia().getValorEnum().equals(TipoTransferencia.tipo3.getValorEnum())){
						resultadoTransmision = crearSolicitudMetodo(tramite, null, ConstantesProcesos.PROCESO_FULLCTIT,null);
					} else if(tramite.getTipoTransferencia().getValorEnum().equals(TipoTransferencia.tipo4.getValorEnum())){//Antes tipo2
						resultadoTransmision = crearSolicitudMetodo(tramite, null, ConstantesProcesos.PROCESO_NOTIFICATIONCTIT,null);
					} else if(tramite.getTipoTransferencia().getValorEnum().equals(TipoTransferencia.tipo5.getValorEnum())){//Antes tipo3
						resultadoTransmision = crearSolicitudMetodo(tramite, null, ConstantesProcesos.PROCESO_TRADECTIT,null);
					}*/
				}
			}
		return resultadoTransmision;
	}

	private ResultBean tramitar(TramiteTraficoTransmisionBean tramite, BigDecimal idUsuario, BigDecimal idContrato) throws OegamExcepcion {
		ResultBean resultado = new ResultBean();
		try {
			tramite.getTramiteTraficoBean().setIdUsuario(idUsuario);
			resultado = servicioTramiteTraficoTransmision.cambiarEstadoTramite(tramite);
			if (!resultado.getError()) {
				String proceso = null;
				if (TipoTransferencia.tipo1.getValorEnum().equals(tramite.getTipoTransferencia().toString()) || TipoTransferencia.tipo2.getValorEnum().equals(tramite.getTipoTransferencia().toString())
					|| TipoTransferencia.tipo3.getValorEnum().equals(tramite.getTipoTransferencia().toString())) {
					proceso = ProcesosEnum.FULLCTIT.getNombreEnum();
				} else if(TipoTransferencia.tipo4.getValorEnum().equals(tramite.getTipoTransferencia().toString())) {
					proceso = ProcesosEnum.NOTIFICATIONCTIT.getNombreEnum();
				} else if(TipoTransferencia.tipo5.getValorEnum().equals(tramite.getTipoTransferencia().toString())) {
					proceso = ProcesosEnum.TRADECTIT.getNombreEnum();
				}
				ResultBean resulCola = servicioCola.crearSolicitud(proceso, null,gestorPropiedades.valorPropertie("nombreHostSolicitud") ,TipoTramiteTrafico.TransmisionElectronica.getValorEnum(),
						tramite.getTramiteTraficoBean().getNumExpediente().toString(),tramite.getTramiteTraficoBean().getIdUsuario(), null, tramite.getTramiteTraficoBean().getIdContrato());
				if (resulCola.getError()) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Ha sucedido un error a la hora de encolar la transmisión para su finalización.");
				}else {
					log.info("Se agregó correctamente en la cola el trámite de TRANSMISION:" + tramite.getTramiteTraficoBean().getNumExpediente());
					resultado.setMensaje("Solicitud creada correctamente");
				}
			}else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Ha ocurrido un error a la hora de cambiar el estado.");
			}
		} catch (Exception e) {
			resultado.setMensaje("Ha ocurrido un error durante el proceso de firma en servidor del trámite");
			resultado.setError(Boolean.TRUE);
			log.error(e);
		}
		return resultado;
	}

	public DatosCTITBean getDatosCTIT(TramiteTraficoTransmisionBean tramiteTraficoTransmisionBean) {
		BeanPQDatosCTIT beanPQDatosCTIT = new BeanPQDatosCTIT();
		beanPQDatosCTIT.setP_NUM_EXPEDIENTE(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente());
		HashMap<String, Object> respuestaCTIT = getModeloDGTWS().datosCTIT(beanPQDatosCTIT);
		return (DatosCTITBean) respuestaCTIT.get(ConstantesPQ.BEANPANTALLA);
	}

	/**
	 * Muestra de forma legible por el usuario el mensaje de parseSAXParseException
	 * Formatea el error: cvc-maxLength-valid y el cvc-pattern-valid 
	 */
	public String parseSAXParseException(SAXParseException spe) {
		if (spe == null) {
			return null;
		} else {
			if (spe.getMessage() != null && !spe.getMessage().isEmpty()) {
				String ERROR_LENGTH = "^(cvc-maxLength-valid: Value ')(.+)(' with length = ')(.+)(' is not facet-valid with respect to maxLength ')(.+)(' for type ')(.+)";
				Matcher matcher = Pattern.compile(ERROR_LENGTH).matcher(spe.getMessage());
				if (matcher.find()) {
					return new StringBuffer("La lóngitud de \"")
							.append(matcher.group(2))
							.append("\" excede el máximo permitido (")
							.append(matcher.group(6)).append(")").toString();
				// Mantis 11823. David Sierra: Agregado para mostrar un mensaje de error indicando que valor no coincide con
				// el patrón esperado y que este error se muestre de forma legible al usuario
				} else {
					String ERROR_PATTERN = "^(cvc-pattern-valid: Value ')(.+)(' is not facet-valid with respect to pattern ')(.+)(' for type ')(.+)";
					matcher = Pattern.compile(ERROR_PATTERN).matcher(spe.getMessage());
					if (matcher.find()) {
						return new StringBuffer("El valor  \"")
								.append(matcher.group(2))
								.append("\" no coincide con el formato esperado por el validador.").toString();
					}
				} // Fin Mantis
			}
			return spe.getMessage();
		}
	}

	private String montarTexto(TramiteTraficoTransmisionBean tramite){
		StringBuilder salida = new StringBuilder();
		try{
			List<ModeloCamDto> marcas = servicioModeloCam.listaModeloCam(tramite.getTramiteTraficoBean().getVehiculo().getTipVehi().getValorEnum(), tramite.getFechaDevengoItp620().getAnio() + "0101", tramite.getTramiteTraficoBean().getVehiculo().getCdMarca(), tramite.getTramiteTraficoBean().getVehiculo().getCdModVeh());
			ContratoDto contrato = servicioContrato.getContratoDto(tramite.getTramiteTraficoBean().getIdContrato());

			salida.append("Se ha modificado manualmente el valor declarado con los siguientes datos: <br>");
			salida.append("MATRICULA: " + tramite.getTramiteTraficoBean().getVehiculo().getMatricula() +"<br>");
			if (marcas != null
					&& (!"C".equals(tramite.getTramiteTraficoBean().getVehiculo().getTipVehi().getValorEnum()) ||
						!"D".equals(tramite.getTramiteTraficoBean().getVehiculo().getTipVehi().getValorEnum()) ||
						!"E".equals(tramite.getTramiteTraficoBean().getVehiculo().getTipVehi().getValorEnum()) ||
						!"F".equals(tramite.getTramiteTraficoBean().getVehiculo().getTipVehi().getValorEnum()))) {
				salida.append("MARCA: "+ marcas.get(0).getCdMarca()+"<br>");
				salida.append("MODELO: " + marcas.get(0).getDsModVeh()+"<br>");
			}else{
				salida.append("MARCA: "+ tramite.getTramiteTraficoBean().getVehiculo().getCdMarca()+"<br>");
				salida.append("MODELO: " + tramite.getTramiteTraficoBean().getVehiculo().getCdModVeh()+"<br>");
			}

			salida.append("NUMERO EXPEDIENTE: " + tramite.getTramiteTraficoBean().getNumExpediente().toString()+"<br>");
			salida.append("CONTRATO: " + contrato.getColegiadoDto().getNumColegiado()+ " - " +contrato.getVia());
			salida.append("<br><br>");
			salida.append("INTRODUCIDO POR PLATAFORMA: <br><br>" );
			salida.append("VALOR DECLARADO: " + tramite.getValorDeclarado620().toString() +"<br>");
			salida.append("BASE IMPONIBLE: " + tramite.getBaseImponible620().toString()+"<br>");
			salida.append("CUOTA: " + tramite.getCuotaTributaria620().toString()+"<br><br>");

			salida.append("VALORES CALCULADOS POR LA PLATAFORMA: <br><br>");

			if(marcas!=null &&
					(!"C".equals(tramite.getTramiteTraficoBean().getVehiculo().getTipVehi().getValorEnum()) ||
						!"D".equals(tramite.getTramiteTraficoBean().getVehiculo().getTipVehi().getValorEnum()))) {
				salida.append("VALOR DECLARADO: " + marcas.get(0).getvPreVehi() + "<br>");
				BigDecimal base = marcas.get(0).getvPreVehi().multiply(tramite.getPorcentajeReduccionAnual620()).divide(new BigDecimal("100"));
				salida.append("BASE IMPONIBLE: " + base + "<br>");
				salida.append("CUOTA: " + base.multiply(tramite.getTipoGravamen620()).divide(new BigDecimal("100")) + "<br>");
			}else{
				String valorString = tramite.getTramiteTraficoBean().getVehiculo().getCilindrada();
				BigDecimal valor = new BigDecimal(valorString);
				if(valorString!=null && !CADENA_VACIA.equals(valorString)){
					if(valor.intValue() >=0 && valor.intValue() <= 50){
						valorString = gestorPropiedades.valorPropertie(TRANSMISION_620_CILINDRADA_0_50);
					}else if(valor.intValue() > 50 && valor.intValue()<=75){
						valorString = gestorPropiedades.valorPropertie("transmision.620.cilindrada.50-75");
					}else if(valor.intValue() > 75 && valor.intValue()<=125){
						valorString = gestorPropiedades.valorPropertie("transmision.620.cilindrada.75-125");
					}else if(valor.intValue() > 125 && valor.intValue()<=150){
						valorString = gestorPropiedades.valorPropertie("transmision.620.cilindrada.125-150");
					}else if(valor.intValue() > 150 && valor.intValue()<=200){
						valorString = gestorPropiedades.valorPropertie("transmision.620.cilindrada.150-200");
					}else if(valor.intValue() > 200 && valor.intValue()<=250){
						valorString = gestorPropiedades.valorPropertie("transmision.620.cilindrada.200-250");
					}else if(valor.intValue() >250 && valor.intValue()<=350){
						valorString = gestorPropiedades.valorPropertie("transmision.620.cilindrada.250-350");
					}else if(valor.intValue() >350 && valor.intValue()<=450){
						valorString = gestorPropiedades.valorPropertie("transmision.620.cilindrada.350-450");
					}else if(valor.intValue() >450 && valor.intValue()<=550){
						valorString = gestorPropiedades.valorPropertie("transmision.620.cilindrada.450-550");
					}else if(valor.intValue() >550 && valor.intValue()<=750){
						valorString = gestorPropiedades.valorPropertie("transmision.620.cilindrada.550-750");
					}else if(valor.intValue() >750 && valor.intValue()<=1000){
						valorString = gestorPropiedades.valorPropertie("transmision.620.cilindrada.750-1000");
					}else if(valor.intValue() >1000 && valor.intValue()<=1200){
						valorString = gestorPropiedades.valorPropertie("transmision.620.cilindrada.1000-1200");
					}else if(valor.intValue() >1200){
						valorString = gestorPropiedades.valorPropertie("transmision.620.cilindrada.Desde-1200");
					}

					salida.append("VALOR DECLARADO: " + valorString+"<br>");
					BigDecimal baseImp = new BigDecimal(valorString);
					baseImp.multiply(tramite.getPorcentajeReduccionAnual620()).divide(new BigDecimal("100"));
					salida.append("BASE IMPONIBLE: " + baseImp +"<br>");
					salida.append("CUOTA: " + baseImp.multiply(tramite.getTipoGravamen620()).divide(new BigDecimal("100"))+"<br>");
				}

			}
		}catch(Exception e){
			salida.append("No se han podido calcular todos los valores <br>");
		}

		return salida.toString();
	}

	private boolean calcularDatos(TramiteTraficoTransmisionBean tramite){
		Boolean salida = false;
		BigDecimal valorDeclarado = tramite.getValorDeclarado620();

		if(!"C".equals(tramite.getTramiteTraficoBean().getVehiculo().getTipVehi().getValorEnum())
				&& !"D".equals(tramite.getTramiteTraficoBean().getVehiculo().getTipVehi().getValorEnum())) {
			//VEHÍCULO NORMAL, SACAMOS EL VALOR DE TABLAS Y COMPARAMOS CON EL DECLARADO
			List<ModeloCamDto> marcas = servicioModeloCam.listaModeloCam(tramite.getTramiteTraficoBean().getVehiculo().getTipVehi().getValorEnum(), tramite.getFechaDevengoItp620().getAnio() + "0101", tramite.getTramiteTraficoBean().getVehiculo().getCdMarca(), tramite.getTramiteTraficoBean().getVehiculo().getCdModVeh());
			if (marcas != null && marcas.get(0).getvPreVehi().compareTo(valorDeclarado) != 0) {
				salida = true;
			}
		}else{
			//MOTOCICLETA, CALCULAMOS EN FUNCIÓN DE LA CILINDRADA Y COMPARAMOS CON EL DECLARADO
			String calculado = calcularValorDeclaradoMotocicletas(tramite.getTramiteTraficoBean().getVehiculo().getCilindrada());
			if(valorDeclarado.compareTo(new BigDecimal(calculado))!=0){
				salida = true;
			}
		}
		return salida;
	}

	private String calcularValorDeclaradoMotocicletas(String cilindrada){
		String salida = null;
		if(cilindrada!=null && !CADENA_VACIA.equals(cilindrada)){
			BigDecimal valor = new BigDecimal(cilindrada);
			if(valor.intValue() >=0 && valor.intValue() <= 50){
				salida = gestorPropiedades.valorPropertie(TRANSMISION_620_CILINDRADA_0_50);
			}else if(valor.intValue() >50 && valor.intValue()<=75){
				salida = gestorPropiedades.valorPropertie("transmision.620.cilindrada.50-75");
			}else if(valor.intValue() >75 && valor.intValue()<=125){
				salida = gestorPropiedades.valorPropertie("transmision.620.cilindrada.75-125");
			}else if(valor.intValue() >125 && valor.intValue()<=150){
				salida = gestorPropiedades.valorPropertie("transmision.620.cilindrada.125-150");
			}else if(valor.intValue() >150 && valor.intValue()<=200){
				salida = gestorPropiedades.valorPropertie("transmision.620.cilindrada.150-200");
			}else if(valor.intValue() >200 && valor.intValue()<=250){
				salida = gestorPropiedades.valorPropertie("transmision.620.cilindrada.200-250");
			}else if(valor.intValue() >250 && valor.intValue()<=350){
				salida = gestorPropiedades.valorPropertie("transmision.620.cilindrada.250-350");
			}else if(valor.intValue() >350 && valor.intValue()<=450){
				salida = gestorPropiedades.valorPropertie("transmision.620.cilindrada.350-450");
			}else if(valor.intValue() >450 && valor.intValue()<=550){
				salida = gestorPropiedades.valorPropertie("transmision.620.cilindrada.450-550");
			}else if(valor.intValue() >550 && valor.intValue()<=750){
				salida = gestorPropiedades.valorPropertie("transmision.620.cilindrada.550-750");
			}else if(valor.intValue() >750 && valor.intValue()<=1000){
				salida = gestorPropiedades.valorPropertie("transmision.620.cilindrada.750-1000");
			}else if(valor.intValue() >1000 && valor.intValue()<=1200){
				salida = gestorPropiedades.valorPropertie("transmision.620.cilindrada.1000-1200");
			}else if(valor.intValue() >1200 ){
				salida = gestorPropiedades.valorPropertie("transmision.620.cilindrada.Desde-1200");
			}
		}
		return salida;
	}

	// TODO: Hacer función para comprobación de Potencia Fiscal
//	private void checkPotencia() {
//	
//	}

	/* *********************************************************************** */
	/* MODELOS *************************************************************** */
	/* *********************************************************************** */
	// FIXME Si se implementa inyección por Spring quitar los ifs de los getters
	/* *********************************************************************** */

	public ModeloInterviniente getModeloInterviniente() {
		if (modeloInterviniente == null) {
			modeloInterviniente = new ModeloInterviniente();
		}
		return modeloInterviniente;
	}

	public void setModeloInterviniente(ModeloInterviniente modeloInterviniente) {
		this.modeloInterviniente = modeloInterviniente;
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

	public ModeloSolicitud getModeloSolicitud() {
		if (modeloSolicitud == null) {
			modeloSolicitud = new ModeloSolicitud();
		}
		return modeloSolicitud;
	}

	public void setModeloSolicitud(ModeloSolicitud modeloSolicitud) {
		this.modeloSolicitud = modeloSolicitud;
	}

	public ModeloVehiculo getModeloVehiculo() {
		if (modeloVehiculo == null) {
			modeloVehiculo = new ModeloVehiculo();
		}
		return modeloVehiculo;
	}

	public void setModeloVehiculo(ModeloVehiculo modeloVehiculo) {
		this.modeloVehiculo = modeloVehiculo;
	}

	public ModeloAcciones getModeloAcciones() {
		if (modeloAcciones == null) {
			modeloAcciones = new ModeloAcciones();
		}
		return modeloAcciones;
	}

	public void setModeloAcciones(ModeloAcciones modeloAcciones) {
		this.modeloAcciones = modeloAcciones;
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

	public ModeloDGTWS getModeloDGTWS() {
		if (modeloDGTWS == null) {
			modeloDGTWS = new ModeloDGTWS();
		}
		return modeloDGTWS;
	}

	public void setModeloDGTWS(ModeloDGTWS modeloDGTWS) {
		this.modeloDGTWS = modeloDGTWS;
	}

}