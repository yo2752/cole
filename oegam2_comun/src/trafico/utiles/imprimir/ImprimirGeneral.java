package trafico.utiles.imprimir;

import static trafico.utiles.ConstantesPDF.PERSONA_JURIDICA;
import static trafico.utiles.ConstantesPDF.PROGRAMA_BAJA;
import static trafico.utiles.ConstantesPDF.PROGRAMA_MATRICULACION;
import static trafico.utiles.ConstantesPDF.SEXO_HEMBRA;
import static trafico.utiles.ConstantesPDF.SEXO_MUJER;
import static trafico.utiles.ConstantesPDF.TAM_NUM_REG;
import static trafico.utiles.ConstantesPDF.TAM_PRIMER_APELLIDO;
import static trafico.utiles.ConstantesPDF.TAM_PRIMER_APELLIDO_RAZON_SOCIAL;
import static trafico.utiles.ConstantesPDF.VALOR_N;
import static trafico.utiles.ConstantesPDF.VALOR_NO;
import static trafico.utiles.ConstantesPDF.VALOR_NUEVO;
import static trafico.utiles.ConstantesPDF.VALOR_S;
import static trafico.utiles.ConstantesPDF.VALOR_SI;
import static trafico.utiles.ConstantesPDF.VALOR_USADO;
import static trafico.utiles.ConstantesPDF.VERSION_BAJA;
import static trafico.utiles.constantes.ConstantesTrafico.*;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.MotivoBaja;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.core.vehiculo.model.enumerados.Alimentacion;
import org.gestoresmadrid.core.vehiculo.model.enumerados.Combustible;
import org.gestoresmadrid.core.vehiculo.model.enumerados.ConceptoTutela;
import org.gestoresmadrid.core.vehiculo.model.enumerados.Fabricacion;
import org.gestoresmadrid.core.vehiculo.model.enumerados.Procedencia;
import org.gestoresmadrid.core.vehiculo.model.enumerados.TiposInspeccionItv;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.model.service.ServicioNotificacion;
import org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmMatriculacionDto;
import org.gestoresmadrid.oegam2comun.view.dto.NotificacionDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.AcroFields.FieldPosition;

import escrituras.beans.ColegioBean;
import escrituras.beans.Direccion;
import escrituras.beans.Persona;
import escrituras.beans.ResultBean;
import escrituras.modelo.ModeloColegiado;
import general.utiles.BasicText;
import general.utiles.UtilesCadenaCaracteres;
import general.utiles.UtilesTrafico;
import hibernate.entities.general.ReconocimientoMedico;
import oegam.constantes.ConstantesPQ;
import trafico.beans.CarburanteBean;
import trafico.beans.IntervinienteTrafico;
import trafico.beans.TramiteTraficoBajaBean;
import trafico.beans.TramiteTraficoBean;
import trafico.beans.TramiteTraficoMatriculacionBean;
import trafico.beans.TramiteTraficoTransmisionBean;
import trafico.beans.VehiculoBean;
import trafico.beans.daos.BeanPQDatosColegio;
import trafico.beans.jaxb.matriculacion.TipoCombustible;
import trafico.beans.jaxb.matw.dgt.tipos.complejos.TipoVehiculoDGT;
import trafico.beans.utiles.CampoPdfBean;
import trafico.beans.utiles.ParametrosPegatinaMatriculacion;
import trafico.modelo.ModeloTrafico;
import trafico.modelo.ivtm.IVTMModeloMatriculacionInterface;
import trafico.modelo.ivtm.impl.IVTMModeloMatriculacionImpl;
import trafico.utiles.ConstantesPDF;
import trafico.utiles.ConversorServicio;
import trafico.utiles.PdfMaker;
import trafico.utiles.UtilResources;
import trafico.utiles.UtilesConversiones;
import trafico.utiles.UtilesConversionesMatw;
import trafico.utiles.UtilesMATW;
import trafico.utiles.UtilesVistaTrafico;
import trafico.utiles.constantes.ConstantesTrafico;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ImprimirGeneral {

	private static final String SIN_CEM = "00000000";
	private InputStream inputStream; // Flujo de bytes del fichero a exportar
	// (download)
	private String fileName; // Nombre del fichero a imprimir
	private String fileSize; // Tamaño del fichero a imprimir
	private ModeloTrafico modeloTrafico;

	private static ILoggerOegam log = LoggerOegam.getLogger(ImprimirGeneral.class);
	// Métodos auxiliares

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	Utiles utiles;

	@Autowired
	UtilesConversiones utilesConversiones;

	@Autowired
	private UtilesColegiado utilesColegiado;

	public ImprimirGeneral() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	/**
	 * Método que va a introducir los campos comunes de los trámites.
	 * 
	 * @param camposPlantilla
	 * @param tramiteTraficoBean
	 * @return
	 */
	protected ArrayList<CampoPdfBean> obtenerValoresCampos(
			Integer tamCampos, Set<String> camposPlantilla,
			TramiteTraficoBean tramiteTraficoBean) throws OegamExcepcion {

		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();
		CampoPdfBean campoAux = null;

		// DATOS GENERALES DE TRÁMITES

		// Insertamos la fecha actual:
		campoAux = new CampoPdfBean(ConstantesPDF.ID_FECHA_ACTUAL, utilesFecha
				.getFechaActual().toString(), false, false, tamCampos);
		camposFormateados.add(campoAux);

		if (camposPlantilla.contains(ConstantesTrafico.ID_TASA)) {
			if (tramiteTraficoBean.getTasa().getCodigoTasa() != null) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_TASA,
						tramiteTraficoBean.getTasa().getCodigoTasa(), true,
						false, tamCampos);
				camposFormateados.add(campoAux);
			}
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_TIPO_TASA)) {
			if (tramiteTraficoBean.getTasa().getTipoTasa() != null) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_TIPO_TASA,
						tramiteTraficoBean.getTasa().getTipoTasa(), true,
						false, tamCampos);
				camposFormateados.add(campoAux);
			}
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_NUM_EXPEDIENTE)) {
			String numero = (null != tramiteTraficoBean.getNumExpediente()) ? tramiteTraficoBean
					.getNumExpediente().toString()
					: "";
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_NUM_EXPEDIENTE,
					numero, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_MATRI)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_MATRI,
					tramiteTraficoBean.getVehiculo().getMatricula(), true,
					false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_FECHA_MATRI)) {
			if ((null != tramiteTraficoBean.getVehiculo()
					&& null != tramiteTraficoBean.getVehiculo()
							.getFechaMatriculacion()
					&& tramiteTraficoBean.getVehiculo().getFechaMatriculacion()
							.getAnio() != null
					&& tramiteTraficoBean.getVehiculo().getFechaMatriculacion()
							.getMes() != null && tramiteTraficoBean
					.getVehiculo().getFechaMatriculacion().getDia() != null)) {
				Fecha fecha = tramiteTraficoBean.getVehiculo()
						.getFechaMatriculacion();
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_FECHA_MATRI,
						fecha.toString(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
		}

		String cem = "";
		// String tipoMatr = UtilesVistaTrafico
		// .queTipoMatricula(null!=tramiteTraficoBean.get ()
		// .getTipoVehiculoBean().getTipoVehiculo()?tramiteTraficoBean.getVehiculo()
		// .getTipoVehiculoBean().getTipoVehiculo():"__");
		// if (ConstantesPDF.TIPO_MATRICULA_REMOLQUE.equals(tipoMatr)) {
		// cem = "00000000";
		// } else {
		cem = null != tramiteTraficoBean.getCemIedtm() ? tramiteTraficoBean
				.getCemIedtm() : "";
		// }
		if (camposPlantilla.contains(ConstantesTrafico.ID_CEM)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_CEM, cem, true,
					false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_CEMA)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_CEMA,
					tramiteTraficoBean.getCema(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_FECHA_PRESENTACION)) {
			if (null != tramiteTraficoBean.getFechaPresentacion()
					&& (tramiteTraficoBean.getFechaPresentacion().getAnio() != null
							|| tramiteTraficoBean.getFechaPresentacion()
									.getMes() != null || tramiteTraficoBean
							.getFechaPresentacion().getDia() != null)) {
				Fecha fecha = tramiteTraficoBean.getFechaPresentacion();
				campoAux = new CampoPdfBean(
						ConstantesTrafico.ID_FECHA_PRESENTACION, fecha
								.toString(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_DIA_PRESENTACION)) {
			if (null != tramiteTraficoBean.getFechaPresentacion()
					&& tramiteTraficoBean.getFechaPresentacion().getDia() != null) {
				campoAux = new CampoPdfBean(
						ConstantesTrafico.ID_DIA_PRESENTACION,
						tramiteTraficoBean.getFechaPresentacion().getDia(),
						false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_MES_PRESENTACION)) {
			if (null != tramiteTraficoBean.getFechaPresentacion()
					&& tramiteTraficoBean.getFechaPresentacion().getMes() != null) {
				campoAux = new CampoPdfBean(
						ConstantesTrafico.ID_MES_PRESENTACION,
						tramiteTraficoBean.getFechaPresentacion().getMes(),
						false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_ANIO_PRESENTACION)) {
			if (null != tramiteTraficoBean.getFechaPresentacion()
					&& tramiteTraficoBean.getFechaPresentacion().getAnio() != null) {
				campoAux = new CampoPdfBean(
						ConstantesTrafico.ID_ANIO_PRESENTACION,
						tramiteTraficoBean.getFechaPresentacion().getAnio(),
						false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_FECHA_CONTRATO430)) {
			if (null != tramiteTraficoBean.getFechaContrato()
					&& (tramiteTraficoBean.getFechaContrato().getAnio() != null
							|| tramiteTraficoBean.getFechaContrato()
									.getMes() != null || tramiteTraficoBean
							.getFechaContrato().getDia() != null)) {
				Fecha fecha = tramiteTraficoBean.getFechaContrato();
				campoAux = new CampoPdfBean(
						ConstantesTrafico.ID_FECHA_CONTRATO430, fecha
								.toString(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_DIA_CONTRATO430)) {
			if (null != tramiteTraficoBean.getFechaContrato()
					&& tramiteTraficoBean.getFechaContrato().getDia() != null) {
				campoAux = new CampoPdfBean(
						ConstantesTrafico.ID_DIA_CONTRATO430,
						tramiteTraficoBean.getFechaContrato().getDia(),
						false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_MES_CONTRATO430)) {
			if (null != tramiteTraficoBean.getFechaContrato()
					&& tramiteTraficoBean.getFechaContrato().getMes() != null) {
				campoAux = new CampoPdfBean(
						ConstantesTrafico.ID_MES_CONTRATO430,
						tramiteTraficoBean.getFechaContrato().getMes(),
						false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_ANIO_CONTRATO430)) {
			if (null != tramiteTraficoBean.getFechaContrato()
					&& tramiteTraficoBean.getFechaContrato().getAnio() != null) {
				campoAux = new CampoPdfBean(
						ConstantesTrafico.ID_ANIO_CONTRATO430,
						tramiteTraficoBean.getFechaContrato().getAnio(),
						false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
		}

		// Jefatura de tráfico
		if (tramiteTraficoBean.getJefaturaTrafico() != null) {
			if (camposPlantilla.contains(ConstantesTrafico.ID_JEFATURA_TRAFICO)) {
				campoAux = new CampoPdfBean(
						ConstantesTrafico.ID_JEFATURA_TRAFICO,
						tramiteTraficoBean.getJefaturaTrafico().getProvincia()
								.getNombre(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla
					.contains(ConstantesTrafico.ID_SUCURSAL_JEFATURA)) {
				campoAux = new CampoPdfBean(
						ConstantesTrafico.ID_SUCURSAL_JEFATURA,
						tramiteTraficoBean.getJefaturaTrafico()
								.getDescripcion(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
		}

		// Datos IEDTM
		if (camposPlantilla.contains(ConstantesTrafico.ID_TIPO_LIMITACION)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_TIPO_LIMITACION,
					"E".equals(tramiteTraficoBean.getIedtm()) ? "SI" : "NO",
					false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_FECHA_LIMITACION)) {
			if (tramiteTraficoBean.getFechaIedtm() != null
					&& tramiteTraficoBean.getFechaIedtm().getAnio() != null
					&& tramiteTraficoBean.getFechaIedtm().getMes() != null
					&& tramiteTraficoBean.getFechaIedtm().getDia() != null) {
				campoAux = new CampoPdfBean(
						ConstantesTrafico.ID_FECHA_LIMITACION,
						tramiteTraficoBean.getFechaIedtm().toString(), false,
						false, tamCampos);
				camposFormateados.add(campoAux);
			}
		}

		if (camposPlantilla
				.contains(ConstantesTrafico.ID_FINANCIERA_LIMITACION)) {
			campoAux = new CampoPdfBean(
					ConstantesTrafico.ID_FINANCIERA_LIMITACION,
					tramiteTraficoBean.getFinancieraIedtm(), false, false,
					tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_NUM_REG_LIMITACION)) {
			campoAux = new CampoPdfBean(
					ConstantesTrafico.ID_NUM_REG_LIMITACION, tramiteTraficoBean
							.getNumRegIedtm() != null ? tramiteTraficoBean
							.getNumRegIedtm().getValorEnum() : "", false,
					false, tamCampos);
			camposFormateados.add(campoAux);
		}

		// Datos gestor
			ModeloColegiado modeloColegiado = new ModeloColegiado();

			Persona colegiado = modeloColegiado
					.obtenerColegiadoCompleto(tramiteTraficoBean
							.getIdContrato() != null ? tramiteTraficoBean
							.getIdContrato() : utilesColegiado.getIdContratoSessionBigDecimal());
			if (camposPlantilla.contains(ConstantesTrafico.ID_NUM_PROFESIONAL)) {
				campoAux = new CampoPdfBean(
						ConstantesTrafico.ID_NUM_PROFESIONAL,
						tramiteTraficoBean.getNumColegiado(), false, false,
						ConstantesPDF._9);
				camposFormateados.add(campoAux);
			}
			if (camposPlantilla.contains(ConstantesPDF.ID_GESTOR)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_GESTOR,
						tramiteTraficoBean.getNumColegiado() + " - "
								+ colegiado.getNombre() + " "
								+ colegiado.getApellido1RazonSocial() + " "
								+ colegiado.getApellido2(), false, false,
						ConstantesPDF._9);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla
					.contains(ConstantesTrafico.ID_NOMBRE_PROFESIONAL)) {
				campoAux = new CampoPdfBean(
						ConstantesTrafico.ID_NOMBRE_PROFESIONAL, colegiado
								.getNombre()
								+ " "
								+ colegiado.getApellido1RazonSocial()
								+ " " + colegiado.getApellido2(), true, false,
								ConstantesPDF._9);
				camposFormateados.add(campoAux);
			}

			//Gilberto Pedroso - Mantis 2785 - Reducimos el tamaño del Campo Gestoria(ID_GESTORIA) Antes _9 ahora _8.
			if (camposPlantilla.contains(ConstantesPDF.ID_GESTORIA)) {
				String rSocial = tramiteTraficoBean.getRazonSocial();
				campoAux = new CampoPdfBean(ConstantesPDF.ID_GESTORIA, rSocial
						+ " (" + colegiado.getNif() + ")", false, false,
						ConstantesPDF._8);
				camposFormateados.add(campoAux);
			}

		if (camposPlantilla.contains(ConstantesTrafico.ID_CAMBIO_DOMICILIO)) {
			campoAux = new CampoPdfBean(
					ConstantesTrafico.ID_CAMBIO_DOMICILIO,
					"true".equals(tramiteTraficoBean.getCambioDomicilio()) ? "SI"
							: "NO", false, false, ConstantesPDF._6);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_INFORMACION)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_INFORMACION,
					tramiteTraficoBean.getAnotaciones(), false, false,
					tamCampos);
			camposFormateados.add(campoAux);
		}

		// DATOS DEL VEHICULO
		tamCampos -= ConstantesPDF._2;
		if (null != tramiteTraficoBean.getVehiculo()) {
			VehiculoBean vehiculo = tramiteTraficoBean.getVehiculo();
			if (camposPlantilla.contains(ConstantesTrafico.ID_BASTIDOR)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_BASTIDOR,
						vehiculo.getBastidor(), false, false, tamCampos + 3);
				camposFormateados.add(campoAux);
			}

			/* MATE 2.5 */
			// Subasta
			if (camposPlantilla.contains(ConstantesTrafico.ID_SUBASTA)) {
				String subasta = VALOR_N;
				if(vehiculo.getSubasta()!=null){
					if(vehiculo.getSubasta().booleanValue() == true){
						subasta = VALOR_S;
					}
				}
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_SUBASTA,
						subasta, true, false, tamCampos);
				camposFormateados.add(campoAux);
			}
			// Importacion
			if (camposPlantilla.contains(ConstantesTrafico.ID_IMPORTADO)) {
				String importado = null;
				if(vehiculo.getImportado() != null && vehiculo.getImportado() == true){
					importado = VALOR_S;
				}else{
					importado = VALOR_N;
				}
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_IMPORTADO,
						importado, true, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// País de Importación (Según ticket EVG-947-4934 de SEA, se pone el nombre del pais completo, no el identificador)
			if (camposPlantilla.contains(ConstantesTrafico.ID_PAIS_IMPORT)) {
				String pais_imp = ""; /*!= vehiculo.getPaisImportacionBean()
						.getDescripcion() ? vehiculo.getPaisImportacionBean()
								.getDescripcion():"";*/
				if(vehiculo.getPaisImportacionBean()!=null){
					if(vehiculo.getPaisImportacionBean().getDescripcion()!=null)
						pais_imp = vehiculo.getPaisImportacionBean().getDescripcion();
				}
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_PAIS_IMPORT,
						pais_imp, true, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_CATEGORIA_EU)) {
				/* Categoría de homologación de la UE */
				String homologacionUE = "";
				if(vehiculo.getHomologacionBean()!=null){
					homologacionUE = vehiculo.getHomologacionBean().getIdHomologacion();
				}
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_CATEGORIA_EU,
						homologacionUE, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
			if (camposPlantilla.contains(ConstantesTrafico.ID_DIST_EJES)) {
				/* Distancia entre ejes */
				String distEjes = "";
				if(vehiculo.getDistanciaEntreEjes()!=null){
					distEjes = vehiculo.getDistanciaEntreEjes().toString();
				}
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_DIST_EJES,
						distEjes, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
			if (camposPlantilla.contains(ConstantesTrafico.ID_VIA_ANT)) {
				/* Via anterior, que se recoge del bean */
				String viaAnt = "";
				if(vehiculo.getViaAnterior()!=null){
					viaAnt = vehiculo.getViaAnterior().toString();
				}
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_VIA_ANT,
						viaAnt, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
			if (camposPlantilla.contains(ConstantesTrafico.ID_VIA_POST)) {
				/* Via posterior, que se recoge del bean */
				String viaPost = "";
				if(vehiculo.getViaPosterior()!=null){
					viaPost = vehiculo.getViaPosterior().toString();
				}
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_VIA_POST,
						viaPost, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
			if (camposPlantilla.contains(ConstantesTrafico.ID_CONSUMO)) {
				/* Se recoge del bean de vehiculo */
				/* Hacer conversiones decimales */
				String campoConsumo = "";
				BigDecimal consumo = vehiculo.getConsumo();
				if (consumo != null){
					String property = gestorPropiedades.valorPropertie(ConstantesPDF.PROPERTY_COMPLETA_CEROS_ELECTRICOS);
					if (property != null && property.equals(ConstantesPDF.VALOR_CIERTO_PROPERTY_COMPLETA_CEROS_ELECTRICOS)){
						DecimalFormat formato4 = new DecimalFormat("#0000");
						campoConsumo = formato4.format(consumo);  
					} else {
						campoConsumo = consumo.toString();
					}
				}
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_CONSUMO,
						campoConsumo, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_FABRICACION)) {
				/* Fabricación, que es lo mismo que procedencia */
				String fabricacion = vehiculo.getFabricacionBean()!=null?vehiculo.getFabricacionBean().getIdFabricacion():"";
				
				if (fabricacion!=null && Fabricacion.NACIONAL.getValorEnum().equals(fabricacion))
					fabricacion = Fabricacion.NACIONAL.toString();
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_FABRICACION,
						fabricacion, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_FABRICANTE)) {
				/* Fabricante que se recoge del bean */
				String fabricante = vehiculo.getFabricante();
				if (fabricante == null || fabricante.equalsIgnoreCase(""))
					fabricante = "ND";
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_FABRICANTE,
						fabricante, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
			if (camposPlantilla
					.contains(ConstantesTrafico.ID_TIPO_ALIMENTACION)) {
				/* Cuando esté el bean de tipo de Alimentacion se consulta */
				String tipoAlim = vehiculo.getAlimentacionBean()!=null?vehiculo.getAlimentacionBean().getIdAlimentacion():"";
				campoAux = new CampoPdfBean(
						ConstantesTrafico.ID_TIPO_ALIMENTACION, tipoAlim, true,
						false, tamCampos);
				camposFormateados.add(campoAux);
			}
			if (camposPlantilla.contains(ConstantesTrafico.ID_NIVEL_EMISIONES)) {
				/* Cuando esté el campo de Nivel de emisiones se consulta */
				String nivelEmisiones = vehiculo.getNivelEmisiones()!=null?vehiculo.getNivelEmisiones().toString():"";
				campoAux = new CampoPdfBean(
						ConstantesTrafico.ID_NIVEL_EMISIONES, nivelEmisiones,
						false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
			if (camposPlantilla.contains(ConstantesTrafico.ID_CARROCERIA)) {
				/*
				 * Cuando esté el bean de carrocería declarado en vehículo, se
				 * consulta
				 */
				String carroceria = vehiculo.getCarroceriaBean()!=null?vehiculo.getCarroceriaBean().getIdCarroceria():"";
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_CARROCERIA,
						carroceria, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
			/* FIN MATE 2.5 */

			if (camposPlantilla.contains(ConstantesTrafico.ID_MATRICULA)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_MATRICULA,
						vehiculo.getMatricula(), true, false, tamCampos + 1);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_CODIGO_ITV)) {
				String codItvAux = vehiculo.getCodItv();
				if (codItvAux == null || codItvAux.length() < CODIGO_ITV) {
					codItvAux = SIN_CODIG;
				}
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_CODIGO_ITV,
						codItvAux, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_TIPO_VEHICULO)) {
				if (vehiculo.getTipoVehiculoBean() != null) {
					campoAux = new CampoPdfBean(ConstantesTrafico.ID_TIPO_VEHICULO,
							vehiculo.getTipoVehiculoBean().getDescripcion(), false,
							false, tamCampos - 2);
					camposFormateados.add(campoAux);
				}
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_TIPO_MATRICULA)) {
				String tipoMatricula = obtenerCodigoMatriculacion(vehiculo
						.getCriterioConstruccionBean()
						.getIdCriterioConstruccion());
				campoAux = new CampoPdfBean(
						ConstantesTrafico.ID_TIPO_MATRICULA, tipoMatricula,
						false, false, ConstantesPDF._9);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_TIPO_INDUSTRIA)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_TIPO_INDUSTRIA,
						vehiculo.getTipoIndustria(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_MARCA)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_MARCA,
						vehiculo.getMarcaBean().getMarca(), false, false,
						tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_MODELO)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_MODELO,
						vehiculo.getModelo(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla
					.contains(ConstantesTrafico.ID_FECHA_MATRICULACION)) {

				if (null != vehiculo.getFechaMatriculacion()
						&& vehiculo.getFechaMatriculacion().getAnio() != null
						&& vehiculo.getFechaMatriculacion().getMes() != null
						&& vehiculo.getFechaMatriculacion().getDia() != null) {
					Fecha fecha = vehiculo.getFechaMatriculacion();
					campoAux = new CampoPdfBean(
							ConstantesTrafico.ID_FECHA_MATRICULACION, fecha
									.toString(), false, false, tamCampos);
					camposFormateados.add(campoAux);
				}
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_FECHA_ITV)) {
				if (null != vehiculo.getFechaItv()
						&& (vehiculo.getFechaItv().getAnio() != null
								|| vehiculo.getFechaItv().getMes() != null || vehiculo
								.getFechaItv().getDia() != null)) {
					Fecha fecha = vehiculo.getFechaItv();
					campoAux = new CampoPdfBean(ConstantesTrafico.ID_FECHA_ITV,
							fecha.toString(), false, false, tamCampos);
					camposFormateados.add(campoAux);
				}
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_CONCEPTO_ITV)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_CONCEPTO_ITV,
						vehiculo.getConceptoItv(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_ESTACION_ITV)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_ESTACION_ITV,
						vehiculo.getEstacionItv(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla
					.contains(ConstantesTrafico.ID_FECHA_INSPECCION_ITV)) {
				if (null != vehiculo.getFechaInspeccion()
						&& (vehiculo.getFechaInspeccion().getAnio() != null
								|| vehiculo.getFechaInspeccion().getMes() != null || vehiculo
								.getFechaInspeccion().getDia() != null)) {
					Fecha fecha = vehiculo.getFechaInspeccion();
					campoAux = new CampoPdfBean(
							ConstantesTrafico.ID_FECHA_INSPECCION_ITV, fecha
									.toString(), false, false, tamCampos);
					camposFormateados.add(campoAux);
				}

			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_MOTIVO_ITV)) {
				if (null != vehiculo.getMotivoBean()) {
					campoAux = new CampoPdfBean(
							ConstantesTrafico.ID_MOTIVO_ITV, vehiculo
									.getMotivoBean().getDescripcion(), false,
							false, tamCampos);
					camposFormateados.add(campoAux);
				}
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_RESULTADO_ITV)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_RESULTADO_ITV,
						vehiculo.getConceptoItv(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
			if (camposPlantilla.contains(ConstantesTrafico.ID_TIPO_ITV)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_TIPO_ITV,
						vehiculo.getTipoItv(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_VARIANTE)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_VARIANTE,
						vehiculo.getVariante(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_VERSION)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_VERSION,
						vehiculo.getVersion(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_TARA)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_TARA, vehiculo
						.getTara(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_MASA)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_MASA, vehiculo
						.getPesoMma(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			String mtma = vehiculo.getMtmaItv();

			if (null == mtma || "".equals(mtma)) {
				mtma = "0";
			}
			if (camposPlantilla.contains(ConstantesTrafico.ID_MASA_MAX)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_MASA_MAX,
						mtma, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_NUMERO_RUEDAS)) {
				String numRuedas = (null != vehiculo.getNumRuedas()) ? vehiculo
						.getNumRuedas().toString() : "";
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_NUMERO_RUEDAS,
						numRuedas, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_PLAZAS)) {
				String numPlazas = (null != vehiculo.getPlazas()) ? vehiculo
						.getPlazas().toString() : "";
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_PLAZAS,
						numPlazas, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_PLAZAS_DE_PIE)) {
				String numPlazas = (null != vehiculo.getNumPlazasPie()) ? vehiculo
						.getNumPlazasPie().toString()
						: "";
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_PLAZAS_DE_PIE,
						numPlazas, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_CARBURANTE)) {
				if (null != vehiculo.getCarburanteBean()) {
					campoAux = new CampoPdfBean(
							ConstantesTrafico.ID_CARBURANTE, vehiculo
									.getCarburanteBean().getDescripcion(),
							false, false, tamCampos);
					camposFormateados.add(campoAux);
				}
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_CILINDRADA)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_CILINDRADA,
						vehiculo.getCilindrada(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// Introduce la potencia Fiscal
			if (camposPlantilla.contains(ConstantesTrafico.ID_POTENCIA)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_POTENCIA,
						null != vehiculo.getPotenciaFiscal() ? utiles
								.stringToBigDecimalDosDecimales(
										utiles.BigDecimalToStringDosDecimales(
												vehiculo.getPotenciaFiscal(),
												ConstantesPDF._5)).toString()
								: "", false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// Introduce la potencia Neta
			if (camposPlantilla.contains(ConstantesTrafico.ID_POTENCIA_MAX)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_POTENCIA_MAX,
						null != vehiculo.getPotenciaNeta() ? utiles
								.stringToBigDecimalDosDecimales(
										utiles.BigDecimalToStringDosDecimales(
												vehiculo.getPotenciaNeta(),
												ConstantesPDF._5)).toString()
								: "", false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_RELACION_POTENCIA_PESO)) {
				// Sólo es obligatorio para las motocicletas
				// BigDecimal potencia = null != vehiculo.getPotenciaNeta() ?
				// vehiculo
				// .getPotenciaNeta() : null;
				// BigDecimal peso = null != vehiculo.getPesoMma() ? new
				// BigDecimal(
				// vehiculo.getPesoMma()) : null;
				// BigDecimal relacion = null != peso && null != potencia ?
				// utiles
				// .divisionBigDecimals(potencia, peso) : null;

				BigDecimal relacion = vehiculo.getPotenciaPeso();
				BigDecimal relacionString = utiles.stringToBigDecimalDosDecimales(utiles.BigDecimalToStringDosDecimales(relacion, ConstantesPDF._7));

				if (vehiculo.getTipoVehiculoBean() != null) {
					String tipoVehiculo = vehiculo.getTipoVehiculoBean().getTipoVehiculo();

					if (tipoVehiculo != null && (tipoVehiculo.equals("50") || tipoVehiculo.equals("51") || tipoVehiculo.equals("52") || tipoVehiculo.equals("53") || tipoVehiculo.equals("54"))) {

						campoAux = new CampoPdfBean(ConstantesTrafico.ID_RELACION_POTENCIA_PESO, null != relacionString ? relacionString.toString() : "", false, false, tamCampos);
						camposFormateados.add(campoAux);
					}
				}
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_POTENCIA_MAX)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_POTENCIA_MAX,
						null != vehiculo.getPotenciaNeta() ? utiles
								.stringToBigDecimalDosDecimales(
										utiles.BigDecimalToStringDosDecimales(
												vehiculo.getPotenciaNeta(),
												ConstantesPDF._5)).toString()
								: "", false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
			

			if (camposPlantilla.contains(ConstantesTrafico.ID_SERVICIO)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_SERVICIO,
						null != vehiculo.getServicioTraficoBean()
								.getIdServicio() ? vehiculo
								.getServicioTraficoBean().getIdServicio()
								: "" + " - " + null != vehiculo
										.getServicioTraficoBean()
										.getDescripcion() ? vehiculo
										.getServicioTraficoBean()
										.getDescripcion() : "", false, false,
						ConstantesPDF._8);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_CAMBIO_SERVICIO)) {
				campoAux = new CampoPdfBean(
						ConstantesTrafico.ID_CAMBIO_SERVICIO,
						null != vehiculo.getServicioTraficoBean()
								.getIdServicio() ? vehiculo
								.getServicioTraficoAnteriorBean()
								.getIdServicio()
								: "" + " - " + null != vehiculo
										.getServicioTraficoBean()
										.getDescripcion() ? vehiculo
										.getServicioTraficoAnteriorBean()
										.getDescripcion() : "", false, false,
						ConstantesPDF._8);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_NUM_HOMOLOGACION)) {
				campoAux = new CampoPdfBean(
						ConstantesTrafico.ID_NUM_HOMOLOGACION, vehiculo
								.getNumHomologacion(), false, false, tamCampos
								- ConstantesPDF._2);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_MASA_SERVICIO)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_MASA_SERVICIO,
						vehiculo.getMom() !=null? vehiculo.getMom().toString():"",
						false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_CO2)) {
				String aux = utiles.cambiarFormatoCO2(vehiculo.getCo2());
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_CO2, aux, 
						false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_COLOR)) {
				if (null != vehiculo.getColorBean()) {
					campoAux = new CampoPdfBean(ConstantesTrafico.ID_COLOR,
							vehiculo.getColorBean().getNombreEnum(), false,
							false, tamCampos - 1);
					camposFormateados.add(campoAux);
				}
			}

			if (camposPlantilla
					.contains(ConstantesTrafico.ID_FECHA_PRIMERA_MATR)) {
				if (null != vehiculo.getFechaPrimMatri()
						&& (vehiculo.getFechaPrimMatri().getAnio() != null
								|| vehiculo.getFechaPrimMatri().getMes() != null || vehiculo
								.getFechaPrimMatri().getDia() != null)) {
					Fecha fecha = vehiculo.getFechaPrimMatri();
					campoAux = new CampoPdfBean(
							ConstantesTrafico.ID_FECHA_PRIMERA_MATR, fecha
									.toString(), false, false, tamCampos);
					camposFormateados.add(campoAux);
				}
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_NUM_KM_USO)) {
				String kmUso = (null != vehiculo.getKmUso()) ? vehiculo
						.getKmUso().toString() : "";
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_NUM_KM_USO,
						kmUso, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

		} 

		return camposFormateados;
	}

	protected ArrayList<CampoPdfBean> obtenerValoresDireccionVehiculo(
			Integer tamCampos, Set<String> camposPlantilla,
			TramiteTraficoTransmisionBean tramiteTraficoTransmisionBean) throws OegamExcepcion {

		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();
		VehiculoBean vehiculo =tramiteTraficoTransmisionBean.getTramiteTraficoBean().getVehiculo();
		Direccion direccion;

		// Dirección del vehículo
		if (null != vehiculo.getDireccionBean() &&
					"TRUE".equals(tramiteTraficoTransmisionBean.getConsentimientoCambio().toUpperCase())) {
				direccion = vehiculo.getDireccionBean();
		} else {
			direccion=tramiteTraficoTransmisionBean.getAdquirienteBean().getPersona().getDireccion();
		}

		camposFormateados.addAll(obtenerValoresDireccion(tamCampos - 3,
				direccion, ConstantesPDF._VEHICULO, camposPlantilla));

		return camposFormateados;
	}

	protected ArrayList<CampoPdfBean> obtenerValoresDireccionVehiculoBaja(Integer tamCampos, Set<String> camposPlantilla, TramiteTraficoBajaBean tramiteTraficoBajaBean) throws OegamExcepcion {
		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();
		VehiculoBean vehiculo = tramiteTraficoBajaBean.getTramiteTrafico().getVehiculo();
		Direccion direccion;
		// Dirección del vehículo
		if (null != vehiculo.getDireccionBean() && "TRUE".equals(tramiteTraficoBajaBean.getConsentimientoCambio().toUpperCase())) {
			direccion = vehiculo.getDireccionBean();
			camposFormateados.addAll(obtenerValoresDireccion(tamCampos - 3, direccion, ConstantesPDF._VEHICULO, camposPlantilla));

		}
		return camposFormateados;
	}

	protected ArrayList<CampoPdfBean> obtenerValoresCamposMATW(
			Integer tamCampos, Set<String> camposPlantilla,
			TramiteTraficoBean tramiteTraficoBean) {

		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();
		CampoPdfBean campoAux = null;
		VehiculoBean vehiculo =  tramiteTraficoBean.getVehiculo();

		if (camposPlantilla.contains(ConstantesPDF.ID_NUME_EXPEDIENTE)) {
			String numero = (null != tramiteTraficoBean.getNumExpediente()) ? tramiteTraficoBean
					.getNumExpediente().toString()
					: "";
			campoAux = new CampoPdfBean(ConstantesPDF.ID_NUME_EXPEDIENTE,
					numero, false, false, tamCampos + 2);
			camposFormateados.add(campoAux);
		}

		// Bastidor /VIN
		if (null != tramiteTraficoBean.getVehiculo()) {
			if (camposPlantilla.contains(ConstantesPDF.ID_BASTIDOR_VIN)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_BASTIDOR_VIN,
						vehiculo.getBastidor(), false, false, tamCampos + 2);
				camposFormateados.add(campoAux);
			}
		}
		// NIVE
		if (camposPlantilla.contains(ConstantesPDF.ID_NIVE)) {
			String numero = (null != tramiteTraficoBean.getVehiculo().getNive()) ? tramiteTraficoBean
					.getVehiculo().getNive()
					: "";
			campoAux = new CampoPdfBean(ConstantesPDF.ID_NIVE,
					numero, false, false, tamCampos + 2);
			camposFormateados.add(campoAux);
		}

	// CEM

		if (camposPlantilla.contains(ConstantesPDF.ID_CEM_MATEW)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_CEM_MATEW, null != tramiteTraficoBean.getCemIedtm() ? tramiteTraficoBean
					.getCemIedtm() : "", true,
					false, tamCampos + 2);
			camposFormateados.add(campoAux);
		}

		// CEMA 

		if (camposPlantilla.contains(ConstantesPDF.ID_CEMA_MTW)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_CEMA_MTW,
					null!=tramiteTraficoBean.getCema()?tramiteTraficoBean.getCema():"", false, false, tamCampos + 2);
			camposFormateados.add(campoAux);
		}

	// MOTIVO DE EXENCION (NumRegIetm)
		if (camposPlantilla.contains(ConstantesPDF.ID_MOTIVO_EXENCION)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_MOTIVO_EXENCION,
					tramiteTraficoBean.getNumRegIedtm()!=null?tramiteTraficoBean.getNumRegIedtm().getNombreEnum()
							.substring(5,tramiteTraficoBean.getNumRegIedtm().getNombreEnum().length()):"", false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

	// TASA

		if (camposPlantilla.contains(ConstantesPDF.ID_TASA_MATW)) {
			if (tramiteTraficoBean.getTasa().getCodigoTasa() != null) {
				campoAux = new  CampoPdfBean(ConstantesPDF.ID_TASA_MATW,
						tramiteTraficoBean.getTasa().getCodigoTasa(), true,
						false, tamCampos + 1);
				camposFormateados.add(campoAux);
			}
		}

	// SATISFECHO_CET 
	// CAMPO ELIMINADO EN LA VERSION 2 DE MATW
			/*	if (camposPlantilla.contains(ConstantesTrafico.ID_SATISFECHO_CET)) {
					if(tramiteTraficoBean.getSatisfechoCet()!=null){
						if(tramiteTraficoBean.getSatisfechoCet()=="true"){
							
							campoAux = new CampoPdfBean(ConstantesTrafico.ID_SATISFECHO_CET,
									"SI", true, false, tamCampos);
						} else {
					    campoAux = new CampoPdfBean(ConstantesTrafico.ID_SATISFECHO_CET,
							"NO", true, false, tamCampos);
						}

						camposFormateados.add(campoAux);
					}
				}
		*/

	// FECHA DE PRESENTACION
		if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_PRESENTACION_MTW)) {
			if (null != tramiteTraficoBean.getFechaPresentacion()
					&& (tramiteTraficoBean.getFechaPresentacion().getAnio() != null
							|| tramiteTraficoBean.getFechaPresentacion()
									.getMes() != null || tramiteTraficoBean
							.getFechaPresentacion().getDia() != null)) {
				Fecha fecha = tramiteTraficoBean.getFechaPresentacion();
				campoAux = new CampoPdfBean(
						ConstantesPDF.ID_FECHA_PRESENTACION_MTW, fecha
								.toString(), false, false, tamCampos + 2);
				camposFormateados.add(campoAux);
			}
		}

	// JEFATURA DE TRAFICO
		if (tramiteTraficoBean.getJefaturaTrafico() != null) {

			if (camposPlantilla.contains(ConstantesPDF.ID_JEFATURA_TRAFICO_MTW)) {
				campoAux = new CampoPdfBean(
						ConstantesPDF.ID_JEFATURA_TRAFICO_MTW,
						tramiteTraficoBean.getJefaturaTrafico().getJefaturaProvincial()
								, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla
					.contains(ConstantesPDF.ID_SUCURSAL_JEFATURA_MTW)) {
				campoAux = new CampoPdfBean(
						ConstantesPDF.ID_SUCURSAL_JEFATURA_MTW,
						tramiteTraficoBean.getJefaturaTrafico()
								.getDescripcion(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

		}

		// Datos IEDTM
		if (camposPlantilla.contains(ConstantesPDF.ID_TIPO_LIMITACION)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_TIPO_LIMITACION,
					"E".equals(tramiteTraficoBean.getIedtm()) ? "SI" : "NO",
					false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		// DATOS REPRESENTANTE PROFESIONAL

		// GESTOR
			ModeloColegiado modeloColegiado = new ModeloColegiado();
				Persona colegiado = modeloColegiado
					.obtenerColegiadoCompleto(tramiteTraficoBean
							.getIdContrato() != null ? tramiteTraficoBean
							.getIdContrato() : utilesColegiado.getIdContratoSessionBigDecimal());

			if (camposPlantilla.contains(ConstantesPDF.ID_GESTOR_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_GESTOR_MTW,
						tramiteTraficoBean.getNumColegiado() + " - "
								+ colegiado.getNombre() + " "
								+ colegiado.getApellido1RazonSocial() + " "
								+ colegiado.getApellido2(), false, false,
						ConstantesPDF._9);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla
					.contains(ConstantesPDF.ID_NOMBRE_PROFESIONAL_MTW)) {
				campoAux = new CampoPdfBean(
						ConstantesPDF.ID_NOMBRE_PROFESIONAL_MTW, colegiado
								.getNombre()
								+ " "
								+ colegiado.getApellido1RazonSocial()
								+ " " + colegiado.getApellido2(), true, false,
								ConstantesPDF._9);
				camposFormateados.add(campoAux);
			}

			// NOMBRE DEL GESTOR
			if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_GESTOR_MATW)){

				campoAux = new CampoPdfBean(
						ConstantesPDF.ID_NOMBRE_GESTOR_MATW, colegiado
								.getNombre()
								+ " "
								+ colegiado.getApellido1RazonSocial()
								+ " " + colegiado.getApellido2(), true, false,
								ConstantesPDF._9);
				camposFormateados.add(campoAux);
			}

		// GESTORIA	
			//Gilberto Pedroso - Mantis 2785 - Reducimos el tamaño del Campo Gestoria(ID_GESTORIA) Antes _9 ahora _8.
			if (camposPlantilla.contains(ConstantesPDF.ID_GESTORIA_MTW)) {
				String rSocial = tramiteTraficoBean.getRazonSocial();
				campoAux = new CampoPdfBean(ConstantesPDF.ID_GESTORIA_MTW, rSocial
						+ " (" + colegiado.getNif() + ")", false, false,
						ConstantesPDF._8);
				camposFormateados.add(campoAux);
			}

		if (camposPlantilla.contains(ConstantesPDF.ID_INFORMACION_MTW)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_INFORMACION_MTW,
					tramiteTraficoBean.getAnotaciones(), false, false,
					tamCampos);
			camposFormateados.add(campoAux);
		}

	// DATOS DEL VEHICULO

	//	tamCampos -= ConstantesPDF._2;

		// Subasta
			if (camposPlantilla.contains(ConstantesPDF.ID_SUBASTA_MTW)) {
				String subasta = ConstantesPDF.VALOR_NO;

				/*if(vehiculo.getSubasta()!=null){
					if(vehiculo.getSubasta().booleanValue() == true){
						subasta = VALOR_SI;
					}
				}*/

				subasta = vehiculo.getSubasta()!=null?vehiculo.getSubasta()==true?VALOR_S:VALOR_N:"";
				campoAux = new CampoPdfBean(ConstantesPDF.ID_SUBASTA_MTW,
						subasta, true, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// Usado
			if (camposPlantilla.contains(ConstantesPDF.ID_USADO_MTW)) {
				String usado = "true".equals(vehiculo.getVehiUsado()) ? VALOR_S : VALOR_N;
				campoAux = new CampoPdfBean(ConstantesPDF.ID_USADO_MTW, usado, true, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// Importacion
			if (camposPlantilla.contains(ConstantesPDF.ID_IMPORTADO_MTW)) {
				String importado = null;
				importado = null!= vehiculo.getImportado() ? vehiculo.getImportado()==true? VALOR_S:VALOR_N:"";

				/*if(vehiculo.getImportado() != null && vehiculo.getImportado() == true){
					importado = VALOR_SI;
				}else{
					importado = VALOR_NO;
				}*/

				campoAux = new CampoPdfBean(ConstantesPDF.ID_IMPORTADO_MTW,
						importado, true, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// PAIS DE IMPORTACION -- 

			if (camposPlantilla.contains(ConstantesPDF.ID_PAIS_IMPORT_MTW)) {
				
					campoAux = new CampoPdfBean(ConstantesPDF.ID_PAIS_IMPORT_MTW,null!= vehiculo.getProcedencia()
							&& vehiculo.getImportado()!= null && vehiculo.getImportado()!=false?
						 Procedencia.getProcedencia(vehiculo.getProcedencia()).getNombreEnum():"", true, false, tamCampos);
					camposFormateados.add(campoAux);
			}

			// TIPO TARJETA ITV 
			if (camposPlantilla.contains(ConstantesPDF.ID_TIPO_TARJETA_ITV_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_TIPO_TARJETA_ITV_MTW, null!= vehiculo.getTipoTarjetaItvBean()?vehiculo.getTipoTarjetaItvBean().getIdTipoTarjetaItv():"", false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// C0DIGO ITV 

			if (camposPlantilla.contains(ConstantesPDF.ID_CODIGO_ITV_MTW)) {
				String codItvAux = vehiculo.getCodItv();
				if (codItvAux == null || codItvAux.length() < CODIGO_ITV || codItvAux.equals(ConstantesTrafico.SIN_CODIG) || codItvAux.equals(ConstantesTrafico.SIN_CODIGO)) {
					codItvAux = SIN_CODIGO_MATW;
				}
				campoAux = new CampoPdfBean(ConstantesPDF.ID_CODIGO_ITV_MTW,
						codItvAux, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// TIPO VEHICULO DGT

			if (camposPlantilla.contains(ConstantesPDF.ID_TIPO_VEHICULO_MTW)) {
				if (vehiculo.getTipoVehiculoBean() != null) {
					campoAux = new CampoPdfBean(ConstantesPDF.ID_TIPO_VEHICULO_MTW,null!= vehiculo.getTipoVehiculoBean()?
							vehiculo.getTipoVehiculoBean().getDescripcion():"", false,
							false, tamCampos - 2);
					camposFormateados.add(campoAux);
				} 
			}

			// TIPO VEHICULO INDUSTRIA

			if (camposPlantilla.contains(ConstantesPDF.ID_TIPO_VEHICULO_IND_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_TIPO_VEHICULO_IND_MTW,null!=vehiculo.getClasificacionItv()?
						vehiculo.getClasificacionItv():"", false,
						false, tamCampos - 2);
				camposFormateados.add(campoAux);
			}

			// CATEGORIA EU

			if (camposPlantilla.contains(ConstantesPDF.ID_CATEGORIA_EU_MTW)) {
				/* Categoría de homologación de la UE */
				String homologacionUE = "";
				if(vehiculo.getHomologacionBean()!=null){
					homologacionUE = vehiculo.getHomologacionBean().getIdHomologacion();
				}
				campoAux = new CampoPdfBean(ConstantesPDF.ID_CATEGORIA_EU_MTW,
						homologacionUE, false, false, tamCampos + 2);
				camposFormateados.add(campoAux);
			}

			// CARROCERIA

			if (camposPlantilla.contains(ConstantesPDF.ID_CARROCERIA_MTW)) {
				/*
				 * Cuando esté el bean de carrocería declarado en vehículo, se
				 * consulta
				 */
				String carroceria = vehiculo.getCarroceriaBean()!=null?vehiculo.getCarroceriaBean().getIdCarroceria():"";
				campoAux = new CampoPdfBean(ConstantesPDF.ID_CARROCERIA_MTW,
						carroceria, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// FECHA CADUCIDAD ITV
			if (camposPlantilla.contains(ConstantesPDF.FECHA_CADUCIDAD_ITV)) {
					campoAux = new CampoPdfBean(ConstantesPDF.FECHA_CADUCIDAD_ITV, vehiculo
								.getFechaItv().toString(), false, false, tamCampos);
						camposFormateados.add(campoAux);
			}

			// MARCA
			if (camposPlantilla.contains(ConstantesPDF.ID_MARCA_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_MARCA_MTW,
						vehiculo.getMarcaBean().getMarca(), false, false,
						tamCampos);
				camposFormateados.add(campoAux);
			}

			// MARCA BASE
			if (camposPlantilla.contains(ConstantesPDF.ID_MARCA_BASE_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_MARCA_BASE_MTW, vehiculo.getMarcaBaseBean().getMarca(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// MODELO 

			if (camposPlantilla.contains(ConstantesPDF.ID_MODELO_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_MODELO_MTW,
						vehiculo.getModelo(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// FABRICANTE
			if (camposPlantilla.contains(ConstantesPDF.ID_FABRICANTE_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_FABRICANTE_MTW, vehiculo.getFabricante(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// FABRICANTE BASE
			if (camposPlantilla.contains(ConstantesPDF.ID_FABRICANTE_BASE_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_FABRICANTE_BASE_MTW, vehiculo.getFabricanteBase(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// TIPO
			String tipoVarianteVersion = "";
			if (camposPlantilla.contains(ConstantesPDF.ID_TIPO_ITV_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_TIPO_ITV_MTW,vehiculo.getTipoItv(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
			tipoVarianteVersion += vehiculo.getTipoItv() != null ? vehiculo.getTipoItv() + " / ": "" ;

			// VARIANTE
			if (camposPlantilla.contains(ConstantesPDF.ID_VARIANTE_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_VARIANTE_MTW,vehiculo.getVariante(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
			tipoVarianteVersion += vehiculo.getVariante() !=null ? vehiculo.getVariante() + " / " :"";

			// VERSION
			if (camposPlantilla.contains(ConstantesPDF.ID_VERSION_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_VERSION_MTW,vehiculo.getVersion(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
			tipoVarianteVersion += vehiculo.getVersion() != null ? vehiculo.getVersion():"";
			
			//TIPO, VARIANTE y VERSION
			if (camposPlantilla.contains(ConstantesPDF.ID_TIPO_VARIANTE_VERSION_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_TIPO_VARIANTE_VERSION_MTW, tipoVarianteVersion, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// TIPO BASE
			String tipoVarianteVersionBase = "";
			if (camposPlantilla.contains(ConstantesPDF.ID_TIPO_ITV_BASE_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_TIPO_ITV_BASE_MTW,vehiculo.getTipoItvBase(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
			tipoVarianteVersionBase += vehiculo.getTipoItvBase() != null ? vehiculo.getTipoItvBase() + " / ":"";
						
			// VARIANTE BASE
			if (camposPlantilla.contains(ConstantesPDF.ID_VARIANTE_BASE_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_VARIANTE_BASE_MTW,vehiculo.getVarianteBase(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
			tipoVarianteVersionBase += vehiculo.getVarianteBase() != null ? vehiculo.getVarianteBase() + " / ":"";

			// VERSION 	BASE
			if (camposPlantilla.contains(ConstantesPDF.ID_VERSION_BASE_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_VERSION_BASE_MTW,vehiculo.getVersionBase(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
			tipoVarianteVersionBase += vehiculo.getVersionBase() != null ? vehiculo.getVersionBase():"";

			//TIPO, VARIANTE y VERSION BASES
			if (camposPlantilla.contains(ConstantesPDF.ID_TIPO_VARIANTE_VERSION_BASE_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_TIPO_VARIANTE_VERSION_BASE_MTW, tipoVarianteVersionBase, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// TARA

			if (camposPlantilla.contains(ConstantesPDF.ID_TARA_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_TARA_MTW, vehiculo
						.getTara(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// MOM

			if (camposPlantilla.contains(ConstantesPDF.ID_MASA_SERVICIO_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_MASA_SERVICIO_MTW,
						vehiculo.getMom() !=null? vehiculo.getMom().toString():"",
						false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// MOM BASE
				if (camposPlantilla.contains(ConstantesPDF.ID_MASA_SERVICIO_BASE_MTW)) {
					campoAux = new CampoPdfBean(ConstantesPDF.ID_MASA_SERVICIO_BASE_MTW, vehiculo.getMomBase() !=null? vehiculo.getMomBase().toString():"",	false, false, tamCampos);
					camposFormateados.add(campoAux);
				}
			
			
			// MASA MAXIMO AUTO.
				
				if (camposPlantilla.contains(ConstantesPDF.ID_MASA_MTW)) {
					campoAux = new CampoPdfBean(ConstantesPDF.ID_MASA_MTW, vehiculo
							.getPesoMma(), false, false, tamCampos);
					camposFormateados.add(campoAux);
				}
	
				
		// MTMA 		
				String mtma = vehiculo.getMtmaItv();

				if (null == mtma || "".equals(mtma)) {
					mtma = "0";
				}
				if (camposPlantilla.contains(ConstantesPDF.ID_MASA_MAX_MTW)) {
					campoAux = new CampoPdfBean(ConstantesPDF.ID_MASA_MAX_MTW,
							mtma, false, false, tamCampos);
					camposFormateados.add(campoAux);
				}
		// Nº PLAZAS
				if (camposPlantilla.contains(ConstantesPDF.ID_NUMERO_PLAZAS_MTW)) {
					String numPlazas = (null != vehiculo.getPlazas()) ? vehiculo
							.getPlazas().toString() : "";
					campoAux = new CampoPdfBean(ConstantesPDF.ID_NUMERO_PLAZAS_MTW,
							numPlazas, false, false, tamCampos);
					camposFormateados.add(campoAux);
				}
		 // Nº PLAZAS DE PIE 

				if (camposPlantilla.contains(ConstantesPDF.ID_PLAZAS_PIE_MTW)) {
					String numPlazas = (null != vehiculo.getNumPlazasPie()) ? vehiculo
							.getNumPlazasPie().toString()
							: "0";
					campoAux = new CampoPdfBean(ConstantesPDF.ID_PLAZAS_PIE_MTW,
							numPlazas, false, false, tamCampos);
					camposFormateados.add(campoAux);
				}
				
				
				
			// DISTANCIA ENTRE EJES	
					
			if (camposPlantilla.contains(ConstantesPDF.ID_DIST_EJES_MTW)) {
				/* Distancia entre ejes */
				String distEjes = "";
				if(vehiculo.getDistanciaEntreEjes()!=null){
					distEjes = vehiculo.getDistanciaEntreEjes().toString();
				}
				campoAux = new CampoPdfBean(ConstantesPDF.ID_DIST_EJES_MTW,
						distEjes, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
		
			// VIA ANTERIOR 
			if (camposPlantilla.contains(ConstantesPDF.ID_VIA_ANT_MTW)) {
				/* Via anterior, que se recoge del bean */
				String viaAnt = "";
				if(vehiculo.getViaAnterior()!=null){
					viaAnt = vehiculo.getViaAnterior().toString();
				}
				campoAux = new CampoPdfBean(ConstantesPDF.ID_VIA_ANT_MTW,
						viaAnt, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
			
			// VIA POSTERIOR 
			
			if (camposPlantilla.contains(ConstantesPDF.ID_VIA_POS_MTW)) {
				/* Via posterior, que se recoge del bean */
				String viaPost = "";
				if(vehiculo.getViaPosterior()!=null){
					viaPost = vehiculo.getViaPosterior().toString();
				}
				campoAux = new CampoPdfBean(ConstantesPDF.ID_VIA_POS_MTW,
						viaPost, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
			
			// NEUMATICOS Nº RUEDAS
			

			if (camposPlantilla.contains(ConstantesPDF.ID_NUMERO_RUEDAS_MTW)) {
				String numRuedas = (null != vehiculo.getNumRuedas()) ? vehiculo
						.getNumRuedas().toString() : "";
				campoAux = new CampoPdfBean(ConstantesPDF.ID_NUMERO_RUEDAS_MTW,
						numRuedas, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			
			// Nº HOMOLOGACION
			
			if (camposPlantilla.contains(ConstantesPDF.ID_NUM_HOMOLOGACION_MTW)) {
				campoAux = new CampoPdfBean(
						ConstantesPDF.ID_NUM_HOMOLOGACION_MTW, vehiculo
								.getNumHomologacion(), false, false, tamCampos + 1
								- ConstantesPDF._2);
				camposFormateados.add(campoAux);
			}
			
			// Nº HOMOLOGACION BASE
			if (camposPlantilla.contains(ConstantesPDF.ID_NUM_HOMOLOGACION_BASE_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_NUM_HOMOLOGACION_BASE_MTW, vehiculo.getNumHomologacionBase(), false, false, tamCampos + 1 - ConstantesPDF._2);
				camposFormateados.add(campoAux);
			}
			
			// SERVICIO DESTINO 
			
			if (camposPlantilla.contains(ConstantesPDF.ID_SERVICIO_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_SERVICIO_MTW,
						null != vehiculo.getServicioTraficoBean()
								.getIdServicio() ? vehiculo
								.getServicioTraficoBean().getDescripcion()
								:"", false, false,
						ConstantesPDF._8);
				camposFormateados.add(campoAux);
			}
			
			
			// CILINDRADA 
			
			if (camposPlantilla.contains(ConstantesPDF.ID_CILINDRADA_MTW)) {
				
				campoAux = new CampoPdfBean(ConstantesPDF.ID_CILINDRADA_MTW,
						vehiculo.getCilindrada()!=null?vehiculo.getCilindrada():"", false, false, tamCampos);
		
			
			//	campoAux = new CampoPdfBean(ConstantesTrafico.ID_CILINDRADA_MTW,
			//			null != vehiculo.getCilindrada() ? utiles.stringToBigDecimalDosDecimales(vehiculo.getCilindrada()).toString(): "", false, false, tamCampos);
				
			
				
				
				camposFormateados.add(campoAux);
			}

			// POTENCIA FISCAL
			if (camposPlantilla.contains(ConstantesPDF.ID_POTENCIA_MTW)) {
					
				campoAux = new CampoPdfBean(ConstantesPDF.ID_POTENCIA_MTW,
						null != vehiculo.getPotenciaFiscal() ? utiles.stringToBigDecimalDosDecimales(utiles.BigDecimalToStringDosDecimales(vehiculo.getPotenciaFiscal(),
												ConstantesPDF._13)).toString(): "", false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// POTENCIA REAL
			if (camposPlantilla.contains(ConstantesPDF.ID_POTENCIA_MAX_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_POTENCIA_MAX_MTW,
						null != vehiculo.getPotenciaNeta() ? utiles
								.stringToBigDecimalTresDecimales(
										utiles.BigDecimalToStringTresDecimales(
												vehiculo.getPotenciaNeta(),
												ConstantesPDF._13)).toString()
								: "", false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// RELACION POTENCIA/PESO
			if (camposPlantilla.contains(ConstantesPDF.ID_RELACION_POTENCIA_PESO_MTW)) {
				BigDecimal relacionString = vehiculo.getPotenciaPeso();
				// BigDecimal relacionString = utiles
				// .stringToBigDecimalDosDecimales(utiles
				// .BigDecimalToStringDosDecimales(relacion,
				// ConstantesPDF._7));

				String parteEntera = "";
				String parteDecimal = "";
				String tcamp = "";

				if (relacionString != null) {
					// Redondear a 4 decimales
					relacionString = relacionString.setScale(4, BigDecimal.ROUND_DOWN);

					// Se completan 0 por la izquierda hasta 2 digitos.
					if (relacionString.toString().indexOf('.') != -1) {
						parteEntera = relacionString.toString().substring(0, relacionString.toString().indexOf('.'));
					} else {
						parteEntera = relacionString.toString();
					}

					// Rellena con 0 por delante la parte entera, hasta un total de
					// 2 digitos
					parteEntera = utiles.rellenarCeros(parteEntera, 2);
					parteDecimal = relacionString.toString().substring(relacionString.toString().indexOf('.') + 1);
					tcamp = parteEntera + '.' + parteDecimal;

					if (vehiculo.getTipoVehiculoBean() != null) {
						String tipoVehiculo = vehiculo.getTipoVehiculoBean().getTipoVehiculo();

						if (tipoVehiculo != null && (tipoVehiculo.equals("50") || tipoVehiculo.equals("51") || tipoVehiculo.equals("52") || tipoVehiculo.equals("53") || tipoVehiculo.equals("54")
								|| tipoVehiculo.equals("90") || tipoVehiculo.equals("91") || tipoVehiculo.equals("92"))) {

							campoAux = new CampoPdfBean(ConstantesPDF.ID_RELACION_POTENCIA_PESO_MTW, null != tcamp ? tcamp : "", false, false, tamCampos);
							camposFormateados.add(campoAux);
						}
					}
				}
			}
			
			// CONSUMO Wh/Km
			
			if (camposPlantilla.contains(ConstantesPDF.ID_CONSUMO_MTW)) {
				/* Se recoge del bean de vehiculo */
				/* Hacer conversiones decimales */
				String campoConsumo = "";
				BigDecimal consumo = vehiculo.getConsumo();
				if (consumo != null){
					String property = gestorPropiedades.valorPropertie(ConstantesPDF.PROPERTY_COMPLETA_CEROS_ELECTRICOS);
					if (property != null && property.equals(ConstantesPDF.VALOR_CIERTO_PROPERTY_COMPLETA_CEROS_ELECTRICOS)){
						DecimalFormat formato4 = new DecimalFormat("#0000");
						campoConsumo = formato4.format(consumo);  
					} else {
						campoConsumo = consumo.toString();
					}
				}
				campoAux = new CampoPdfBean(ConstantesPDF.ID_CONSUMO_MTW,
						campoConsumo, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
			
			// FABRICACION
						
			if (camposPlantilla.contains(ConstantesPDF.ID_FABRICACION_MTW)) {
				//String proc = PaisFabricacion.convertirPaisFabricacionBeanEnumerado(null!=vehiculo
				//		.getProcedenciaBean().getIdProcedencia()?vehiculo
			//					.getProcedenciaBean().getIdProcedencia():"");
			
				String fabr = null!= vehiculo.getPaisFabricacionBean().getIdPaisFabricacion()?
						trafico.utiles.enumerados.PaisFabricacion.convertirPaisFabricacionBeanEnumerado(vehiculo.getPaisFabricacionBean().getIdPaisFabricacion()):"";
					
				campoAux = new CampoPdfBean(ConstantesPDF.ID_FABRICACION_MTW,
						fabr, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
			
			// CARBURANTE
			if (camposPlantilla.contains(ConstantesPDF.ID_CARBURANTE_MTW)) {
				if (null != vehiculo.getCarburanteBean()) {
					campoAux = new CampoPdfBean(
							ConstantesPDF.ID_CARBURANTE_MTW, vehiculo
									.getCarburanteBean().getDescripcion(),
							false, false, tamCampos);
					camposFormateados.add(campoAux);
				}
			}

		    // TIPO ALIMENTACION
		
			if (camposPlantilla
					.contains(ConstantesPDF.ID_TIPO_ALIMENTACION_MTW)) {
				/* Cuando esté el bean de tipo de Alimentacion se consulta */
				String tipoAlim = vehiculo.getAlimentacionBean()!=null?vehiculo.getAlimentacionBean().getDescripcion():"";
				campoAux = new CampoPdfBean(
						ConstantesPDF.ID_TIPO_ALIMENTACION_MTW, tipoAlim, true,
						false, tamCampos);
				camposFormateados.add(campoAux);
			}
			
			// CO2  ********************   OJ0!!! COMPROBAR VALORES CORRECTOS NUEVA VERSION MATW VERSION 2
			if (camposPlantilla.contains(ConstantesPDF.ID_CO2_MTW)) {
				//String aux = utiles.cambiarFormatoCO2(vehiculo.getCo2());
			
			
				campoAux = new CampoPdfBean(ConstantesPDF.ID_CO2_MTW, vehiculo.getCo2(), 
						false, false, tamCampos);
			
				
				camposFormateados.add(campoAux);
			}
			
			// CATEGORIA ELECTRICA
			if (camposPlantilla.contains(ConstantesPDF.ID_CATEGORIA_ELECTRICA_MTW)) {
				String categoriaElectrica = vehiculo.getCategoriaElectricaBean()!=null?vehiculo.getCategoriaElectricaBean().getIdCategoriaElectrica():"";
				campoAux = new CampoPdfBean(ConstantesPDF.ID_CATEGORIA_ELECTRICA_MTW, categoriaElectrica, true,false, tamCampos);
				camposFormateados.add(campoAux);
			}
			
			// AUTONOMIA ELECTRICA
			if (camposPlantilla.contains(ConstantesPDF.ID_AUTONOMIA_ELECTRICA_MTW)) {
				String autonomiaElectrica = vehiculo.getAutonomiaElectrica()!=null?vehiculo.getAutonomiaElectrica().toString():"";
				String property = gestorPropiedades.valorPropertie(ConstantesPDF.PROPERTY_COMPLETA_CEROS_ELECTRICOS);
				if (property != null && property.equals(ConstantesPDF.VALOR_CIERTO_PROPERTY_COMPLETA_CEROS_ELECTRICOS)){
					DecimalFormat formato4 = new DecimalFormat("#0000");
					if (autonomiaElectrica!=null && !autonomiaElectrica.isEmpty()) {
						autonomiaElectrica = formato4.format(new BigDecimal(autonomiaElectrica));
					}
				}
				campoAux = new CampoPdfBean(ConstantesPDF.ID_AUTONOMIA_ELECTRICA_MTW, autonomiaElectrica, true,false, tamCampos);
				camposFormateados.add(campoAux);
			}
			
			// CODIGO ECO
			if (camposPlantilla.contains(ConstantesPDF.ID_CODIGO_ECO_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_CODIGO_ECO_MTW, vehiculo.getCodigoEco()!=null?vehiculo.getCodigoEco():"", true,false, tamCampos);
				camposFormateados.add(campoAux);
			}
			
			// REDUCCION ECO
			if (camposPlantilla.contains(ConstantesPDF.ID_REDUCCION_ECO_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_REDUCCION_ECO_MTW, vehiculo.getReduccionEco()!=null?vehiculo.getCodigoEco():"", true,false, tamCampos);
				camposFormateados.add(campoAux);
			}
						
			// ECO INNOVACION
			if (camposPlantilla.contains(ConstantesPDF.ID_ECO_INNOVACION_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_ECO_INNOVACION_MTW, vehiculo.getEcoInnovacion()!=null?vehiculo.getCodigoEco():"", true,false, tamCampos);
				camposFormateados.add(campoAux);
			}
			
			// NIVEL DE EMISIONES
			
			if (camposPlantilla.contains(ConstantesPDF.ID_NIVEL_EMISIONES_MTW)) {
				/* Cuando esté el campo de Nivel de emisiones se consulta */
				String nivelEmisiones = vehiculo.getNivelEmisiones()!=null?vehiculo.getNivelEmisiones().toString():"";
				campoAux = new CampoPdfBean(
						ConstantesPDF.ID_NIVEL_EMISIONES_MTW, nivelEmisiones,
						false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
			
			// COLOR 
			// *****************  PENDIENTE DE CAMBIAR POR  RICARDO  ND
			if (camposPlantilla.contains(ConstantesPDF.ID_COLOR_MTW)) {
				if (null != vehiculo.getColorBean()) {
					
				
					campoAux = new CampoPdfBean(ConstantesPDF.ID_COLOR_MTW,
							vehiculo.getColorBean().getValorEnum(), false,
							false, tamCampos - 1);
					camposFormateados.add(campoAux);
				}
			}
			
			// FECHA DE PRIMERA MATRICULACION
			
			if (camposPlantilla
					.contains(ConstantesPDF.ID_FECHA_PRIMERA_MATR_MTW)) {
				if (null != vehiculo.getFechaPrimMatri()
						&& (vehiculo.getFechaPrimMatri().getAnio() != null
								|| vehiculo.getFechaPrimMatri().getMes() != null || vehiculo
								.getFechaPrimMatri().getDia() != null)) {
					Fecha fecha = vehiculo.getFechaPrimMatri();
					campoAux = new CampoPdfBean(
							ConstantesPDF.ID_FECHA_PRIMERA_MATR_MTW, fecha
									.toString(), false, false, tamCampos);
					camposFormateados.add(campoAux);
				}
			}
			
			// DIRECCION VEHICULO 
			
			 if (null != vehiculo.getDireccionBean()) {
					
				 Direccion direccion = vehiculo.getDireccionBean();
				 camposFormateados.addAll(obtenerValoresDireccionMATEW( ConstantesPDF._8,direccion,4,camposPlantilla));
			
			 
			 }
			
			
			
			// DATOS ITV
			
			// MATRICULA ORIGEN   -- Se ha comentado a partir de 20/01/14 según indicaciones de Eduardo , no se puede 
			 // mostrar la matricula origen , solo se mostrar matricula origen extrajero si el propertie esta habilitado
						
			if (camposPlantilla.contains(ConstantesPDF.MATRICULA_ORIGEN_MTW)) {
				
				if (null != vehiculo.getMatriculaOrigen() ) {
				// La matricula origen extranjero si que se mostrará			
					campoAux = new CampoPdfBean(
						ConstantesPDF.MATRICULA_ORIGEN_MTW,"", false,false, tamCampos);
					camposFormateados.add(campoAux);
					
				}
				else
				{
					campoAux = new CampoPdfBean(
					ConstantesPDF.MATRICULA_ORIGEN_MTW,null!=vehiculo.getMatriculaOrigExtr()?vehiculo.getMatriculaOrigExtr():"", false,false, tamCampos);
					camposFormateados.add(campoAux);
					
				}
				
			}
			
			// TIPO INSPECCION
			if (camposPlantilla.contains(ConstantesPDF.TIPO_INSPECCION_ITV)) {
				campoAux = new CampoPdfBean(ConstantesPDF.TIPO_INSPECCION_ITV,null!=vehiculo.getMotivoBean()?
						TiposInspeccionItv.getDescripcion(vehiculo.getMotivoBean().getIdMotivo()):"", false, false, tamCampos);
				camposFormateados.add(campoAux);
			}			
						
////		   // FECHA INICIAL ITV 			--Eliminado en la version 2 de MATW 	
//////						
//////						if (camposPlantilla.contains(ConstantesTrafico.ID_FECHA_ITV_MTW)) {
//////					if (null != vehiculo.getFechaInicialItv()
//////							&& (vehiculo.getFechaInicialItv().getAnio() != null
//////									|| vehiculo.getFechaInicialItv().getMes() != null || vehiculo
//////									.getFechaInicialItv().getDia() != null)) {
//////						Fecha fecha = vehiculo.getFechaInicialItv();
//////						campoAux = new CampoPdfBean(ConstantesTrafico.ID_FECHA_ITV_MTW,
//////								fecha.toString(), false, false, tamCampos);
//////						camposFormateados.add(campoAux);
//////				}
////
////			//}
//
////						

////			
//			// ESTACION ITV -- ELMININADO EN LA VERSION 2 DE MATW
//			
////			if (camposPlantilla.contains(ConstantesTrafico.ID_ESTACION_ITV_MTW)) {
////				campoAux = new CampoPdfBean(ConstantesTrafico.ID_ESTACION_ITV_MTW,
////						vehiculo.getEstacionItv(), false, false, tamCampos);
////				camposFormateados.add(campoAux);
////			}
//
//			// PROVINCIA ITV  ELIMINADO EN VERSION 2 DE MATW
//			
//		/*	if (camposPlantilla.contains(ConstantesTrafico.PROVINCIA_INSPECCION_ITV)) {
//				campoAux = new CampoPdfBean(ConstantesTrafico.PROVINCIA_INSPECCION_ITV,
//						vehiculo.getIdProvinciaItv(), false, false, tamCampos);
//				camposFormateados.add(campoAux);
//			} */
//			
//			
			// KM ITV 
			if (camposPlantilla.contains(ConstantesPDF.KM_ITV_MTW)) {
				if (null != vehiculo.getKmUso()) {
					campoAux = new CampoPdfBean(
							ConstantesPDF.KM_ITV_MTW, vehiculo
									.getKmUso().toString(), false,false, tamCampos);
					camposFormateados.add(campoAux);
				}
			}

			// HORAS ITV 
			if (camposPlantilla.contains(ConstantesPDF.HORAS_ITV_MTW)) {
				if (null != vehiculo.getHorasUso()) {
					campoAux = new CampoPdfBean(
							ConstantesPDF.HORAS_ITV_MTW, vehiculo
									.getHorasUso().toString(), false,false, tamCampos);
					camposFormateados.add(campoAux);
				}
			}
			
		
		

		return camposFormateados;
	}

	
	/**
	 * Método que pasándole los siguientes parámetros nos devolverá los campos
	 * formateados correspondientes al interviniente que le pasemos.
	 * 
	 * @param interviniente
	 * @param recurso
	 * @param camposPlantilla
	 * @return
	 */
	protected Collection<? extends CampoPdfBean> obtenerValoresInterviniente(
			Integer tamCampos, IntervinienteTrafico interviniente,
			String recurso,Set<String> camposPlantilla) {

		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();
		CampoPdfBean campoAux = null;

		// Si existe en la plantilla el campo MATEW , se esta refiriendo a la nueva plantilla de MATEW
	
		// Etiquetas
		if (null != interviniente.getPersona()
				&& null != interviniente.getPersona().getSexo()
				&& interviniente.getPersona().getSexo()
						.equals(PERSONA_JURIDICA)) {
			if (camposPlantilla.contains(ConstantesPDF.TIT_NOMBRE + recurso)) {
				campoAux = new CampoPdfBean(ConstantesPDF.TIT_NOMBRE + recurso,
						ConstantesPDF.DENOMINACION, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
		} else if (null != interviniente.getPersona()
				&& null != interviniente.getPersona().getSexo()
				&& !interviniente.getPersona().getSexo().equals(
						PERSONA_JURIDICA)) {
			if (camposPlantilla.contains(ConstantesPDF.TIT_NOMBRE + recurso)) {
				campoAux = new CampoPdfBean(ConstantesPDF.TIT_NOMBRE + recurso,
						ConstantesPDF.NOMBRE, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesPDF.TIT_PRIMER_APELLIDO
					+ recurso)) {
				campoAux = new CampoPdfBean(ConstantesPDF.TIT_PRIMER_APELLIDO
						+ recurso, ConstantesPDF.APELLIDO1, false, false,
						tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesPDF.TIT_SEGUNDO_APELLIDO
					+ recurso)) {
				campoAux = new CampoPdfBean(ConstantesPDF.TIT_SEGUNDO_APELLIDO
						+ recurso, ConstantesPDF.APELLIDO2, false, false,
						tamCampos);
				camposFormateados.add(campoAux);
			}
		}

		// Datos
		if (camposPlantilla.contains(ConstantesTrafico.ID_CAMBIO_DOMICILIO
				+ recurso)) {
			campoAux = new CampoPdfBean(
					ConstantesTrafico.ID_CAMBIO_DOMICILIO + recurso,
					(null != interviniente.getCambioDomicilio() ? (interviniente
							.getCambioDomicilio().equals("true") ? VALOR_SI
							: VALOR_NO) 
							: VALOR_NO), true, false, ConstantesPDF._6);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_FLAG_AUTONOMO
				+ recurso)) {
			campoAux = new CampoPdfBean(
					ConstantesTrafico.ID_FLAG_AUTONOMO + recurso,
					(null != interviniente.getNifInterviniente() || null!=interviniente.getAutonomo()) ? (interviniente.getAutonomo().equals("true") ? VALOR_SI : VALOR_NO)
							: "", true, false, ConstantesPDF._6);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_CODIGO_IAE + recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_CODIGO_IAE
					+ recurso,
					(null != interviniente.getCodigoIAE() ? interviniente
							.getCodigoIAE() : ""), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_TUTELA + recurso)) {
			if (null != interviniente.getConceptoRepre()) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_TUTELA
						+ recurso,
						(ConceptoTutela.Tutela.getValorEnum() == interviniente
								.getConceptoRepre().getValorEnum() ? "SI"
								: "NO"), false, false, ConstantesPDF._8);
				camposFormateados.add(campoAux);
			}
		}

		if (camposPlantilla
				.contains(ConstantesTrafico.ID_TIPO_TUTELA + recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_TIPO_TUTELA
					+ recurso,
					(null != interviniente.getIdMotivoTutela() ? interviniente
							.getIdMotivoTutela().getNombreEnum() : ""), false,
					false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_TRAMITE_TUTELA
				+ recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_TRAMITE_TUTELA
					+ recurso,
					(null != interviniente.getDatosDocumento() ? interviniente
							.getDatosDocumento() : ""), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		
		if (camposPlantilla.contains(ConstantesTrafico.ID_CONCEPTO_TUTELA
				+ recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_CONCEPTO_TUTELA
					+ recurso,
					(null != interviniente.getConceptoRepre() ? interviniente
							.getConceptoRepre().getNombreEnum() : ""), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		// Para fechas
		if (camposPlantilla.contains(ConstantesTrafico.ID_FECHA_INICIO
				+ recurso)) {
			if (null != interviniente.getFechaInicio()
					&& (interviniente.getFechaInicio().getAnio() != null
							|| interviniente.getFechaInicio().getMes() != null || interviniente
							.getFechaInicio().getDia() != null)) {
				Fecha fecha = interviniente.getFechaInicio();
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_FECHA_INICIO
						+ recurso, fecha.toString(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
		}
		if (camposPlantilla.contains(ConstantesTrafico.ID_FECHA_FIN + recurso)) {
			if (null != interviniente.getFechaFin()
					&& (interviniente.getFechaFin().getAnio() != null
							|| interviniente.getFechaFin().getMes() != null || interviniente
							.getFechaFin().getDia() != null)) {
				Fecha fecha = interviniente.getFechaFin();
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_FECHA_FIN
						+ recurso, fecha.toString(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
		}
		if (camposPlantilla
				.contains(ConstantesTrafico.ID_HORA_INICIO + recurso)) {
			if (null != interviniente.getHoraInicio()) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_HORA_INICIO
						+ recurso, interviniente.getHoraInicio(), false, false,
						tamCampos);
				camposFormateados.add(campoAux);
			}
		}
		if (camposPlantilla.contains(ConstantesTrafico.ID_HORA_FIN + recurso)) {
			if (null != interviniente.getHoraFin()) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_HORA_FIN
						+ recurso, interviniente.getHoraFin(), false, false,
						tamCampos);
				camposFormateados.add(campoAux);
			}
		}

		Persona persona = interviniente.getPersona();

		camposFormateados.addAll(obtenerValoresPersona(tamCampos, persona,
				recurso, camposPlantilla,interviniente));
		
		return camposFormateados;
	}

	/**
	 * Método que pasándole los siguientes parámetros nos devolverá los campos
	 * formateados correspondientes a la persona que le pasemos.
	 * 
	 * @param persona
	 * @param recurso
	 * @param camposPlantilla
	 * @param interviniente 
	 * @return
	 */
	private Collection<? extends CampoPdfBean> obtenerValoresPersona(
			Integer tamCampos, Persona persona, String recurso,
			Set<String> camposPlantilla, IntervinienteTrafico interviniente) {

		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();
		CampoPdfBean campoAux = null;
/*		if (camposPlantilla.contains(ConstantesTrafico.ID_NOMBRE_APELLIDOS + recurso)) {
			String nombreApellidos = (null != persona.getNombre() ? persona
					.getNombre() : "")
					+ " "
					+ (null != persona.getApellido1RazonSocial() ? persona
							.getApellido1RazonSocial() : "")
					+ " "
					+ (null != persona.getApellido2() ? persona.getApellido2()
							: "");
			
			if("JURIDICA".equals(persona.getTipoPersona().getValorEnum()) && "005".equals(interviniente.getTipoInterviniente().getValorEnum())) {
				if(recurso.equals("_ADQUIRIENTE")) {
					campoAux = new CampoPdfBean(ConstantesTrafico.ID_NOMBRE_APELLIDOS_REPR_ADQUIRIENTE, nombreApellidos, false, false, tamCampos);
					camposFormateados.add(campoAux);
				}
				if(recurso.equals("_REPR_ADQUIRIENTE")) {
					campoAux = new CampoPdfBean(ConstantesTrafico.ID_NOMBRE_APELLIDOS_ADQUIRIENTE, nombreApellidos, false, false, tamCampos);
					camposFormateados.add(campoAux);
				}
			}
			if("FISICA".equals(persona.getTipoPersona().getValorEnum()) && "005".equals(interviniente.getTipoInterviniente().getValorEnum())){
				if(recurso.equals("_ADQUIRIENTE")) {
					campoAux = new CampoPdfBean(ConstantesTrafico.ID_NOMBRE_APELLIDOS_REPR_ADQUIRIENTE, nombreApellidos, false, false, tamCampos);
					camposFormateados.add(campoAux);
				}else if(recurso.equals("_REPR_ADQUIRIENTE")) {
					campoAux = new CampoPdfBean(ConstantesTrafico.ID_NOMBRE_APELLIDOS_ADQUIRIENTE, nombreApellidos, false, false, tamCampos);
					camposFormateados.add(campoAux);
				}
			}
			if("FISICA".equals(persona.getTipoPersona().getValorEnum()) && "008".equals(interviniente.getTipoInterviniente().getValorEnum())){
				if(recurso.equals("_ADQUIRIENTE")) {
					campoAux = new CampoPdfBean(ConstantesTrafico.ID_NOMBRE_APELLIDOS_REPR_ADQUIRIENTE, nombreApellidos, false, false, tamCampos);
					camposFormateados.add(campoAux);
				}else if(recurso.equals("_REPR_ADQUIRIENTE")) {
					campoAux = new CampoPdfBean(ConstantesTrafico.ID_NOMBRE_APELLIDOS_ADQUIRIENTE, nombreApellidos, false, false, tamCampos);
					camposFormateados.add(campoAux);
				}
			}
			if("FISICA".equals(persona.getTipoPersona().getValorEnum())){
				if(recurso.equals("_TRANSMITENTE")) {
					campoAux = new CampoPdfBean(ConstantesTrafico.ID_NOMBRE_APELLIDOS_TRANSMITENTE, nombreApellidos, false, false, tamCampos);
					camposFormateados.add(campoAux);
				}
			}
			
			
	}
	if (camposPlantilla.contains(ConstantesTrafico.ID_DNI + recurso)) {
		if("JURIDICA".equals(persona.getTipoPersona().getValorEnum()) && "005".equals(interviniente.getTipoInterviniente().getValorEnum())) {
			if(recurso.equals("_ADQUIRIENTE")) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_DNI_REPR_ADQUIRIENTE,
						persona.getNif(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
			if(recurso.equals("_REPR_ADQUIRIENTE")) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_DNI_ADQUIRIENTE ,
						persona.getNif(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
		
		}
		if("FISICA".equals(persona.getTipoPersona().getValorEnum()) && "005".equals(interviniente.getTipoInterviniente().getValorEnum())){
			if(recurso.equals("_ADQUIRIENTE")) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_DNI_REPR_ADQUIRIENTE,
						persona.getNif(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
			if(recurso.equals("_REPR_ADQUIRIENTE")) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_DNI_ADQUIRIENTE,
						persona.getNif(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
		}
		if("FISICA".equals(persona.getTipoPersona().getValorEnum()) && "008".equals(interviniente.getTipoInterviniente().getValorEnum())){
			if(recurso.equals("_ADQUIRIENTE")) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_DNI_REPR_ADQUIRIENTE,
						persona.getNif(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
			if(recurso.equals("_REPR_ADQUIRIENTE")) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_DNI_ADQUIRIENTE,
						persona.getNif(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
		}
		if("FISICA".equals(persona.getTipoPersona().getValorEnum())){
			if(recurso.equals("_TRANSMITENTE")) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_DNI_TRANSMITENTE,
						persona.getNif(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
		}
		
	}*/
		if (camposPlantilla.contains(ConstantesTrafico.ID_NOMBRE_APELLIDOS + recurso)){
			String nombreApellidos = (null != persona.getNombre() ? persona
					.getNombre() : "")
					+ " "
					+ (null != persona.getApellido1RazonSocial() ? persona
							.getApellido1RazonSocial() : "")
					+ " "
					+ (null != persona.getApellido2() ? persona.getApellido2()
							: "");
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_NOMBRE_APELLIDOS
					+ recurso, nombreApellidos, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_FECHA_NACIMIENTO
				+ recurso)) {
			if (null != persona.getFechaNacimiento()
					&& (persona.getFechaNacimientoBean().getAnio() != null
							|| persona.getFechaNacimientoBean().getMes() != null || persona
							.getFechaNacimientoBean().getDia() != null)) {
				Fecha fecha = persona.getFechaNacimientoBean();
				campoAux = new CampoPdfBean(
						ConstantesTrafico.ID_FECHA_NACIMIENTO + recurso, fecha
								.toString(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_NOMBRE + recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_NOMBRE + recurso,
					persona.getNombre(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_PRIMER_APELLIDO
				+ recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_PRIMER_APELLIDO
					+ recurso, persona.getApellido1RazonSocial(), false, false,
					tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_SEGUNDO_APELLIDO
				+ recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_SEGUNDO_APELLIDO
					+ recurso, persona.getApellido2(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_SEXO + recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_SEXO + recurso,
					persona.getSexo(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		
		if (camposPlantilla.contains(ConstantesTrafico.ID_DNI + recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_DNI + recurso,
					persona.getNif(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		// Dirección del vehículo
		if (null != persona.getDireccion()) {
			Direccion direccion = persona.getDireccion();

			camposFormateados.addAll(obtenerValoresDireccion(tamCampos,
					direccion, recurso, camposPlantilla));
		}
		
		// VALORES PROPIOS DE LA NUEVA MATRICULACION DE MATEW
		
		return camposFormateados;
	}

	
	
	
	
	
	
	protected Collection<? extends CampoPdfBean> obtenerValoresIntervinienteMTW(
			Integer tamCampos, IntervinienteTrafico interviniente
			,int recurso,Set<String> camposPlantilla) {

		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();
		CampoPdfBean campoAux = null;
		String  cambio_dom = null;
		String nombre_apellidos=null;
		String fecha_nacimiento = null;
		String sexo_persona = null;
		String dni_persona = null;

		// Si existe en la plantilla el campo MATEW , se esta refiriendo a la nueva plantilla de MATEW
		// Etiquetas
		// TITULAR
		switch (recurso){
		case 1:{  cambio_dom= ConstantesPDF.ID_CAMBIO_DOMICILIO_MTW;
		          nombre_apellidos = ConstantesTrafico.ID_NOMBRE_APELLIDOS_TITULAR_MTW;
		          fecha_nacimiento = ConstantesPDF.ID_FECHA_NACIMIENTO_TITULAR_MTW;
		          sexo_persona = ConstantesPDF.ID_SEXO_TITULAR_MTW;
		          dni_persona = ConstantesPDF.ID_DNI_TITULAR_MTW;
		         
		
		
		};break;
		// ARRENDATARIO
		case 2:{
			cambio_dom = ConstantesPDF.ID_CAMBIO_DOMICILIO_ARRENDATARIO_MTW;
			nombre_apellidos = ConstantesPDF.ID_NOMBRE_APELLIDOS_ARRENDATARIO_MTW;
			fecha_nacimiento = ConstantesPDF.ID_FECHA_NACIMIENTO_ARRENDATARIO_MTW;
			sexo_persona = ConstantesPDF.ID_SEXO_ARRENDATARIO_MTW;
	        dni_persona = ConstantesPDF.ID_DNI_ARRENDATARIO_MTW;
	       
			
		};break;
		// CONDUCTOR HABITUAL
		case 3:{
			cambio_dom = ConstantesPDF.ID_CAMBIO_DOMICILIO_CONDUCTOR_HABITUAL_MTW;
			nombre_apellidos = ConstantesPDF.ID_NOMBRE_APELLIDOS_CONDUCTOR_HABITUAL_MTW;
			fecha_nacimiento = ConstantesPDF.ID_FECHA_NACIMIENTO_CONDUCTOR_HABITUAL_MTW;
			sexo_persona = ConstantesPDF.ID_SEXO_CONDUCTOR_HABITUAL_MTW;
	        dni_persona = ConstantesPDF.ID_DNI_CONDUCTOR_HABITUAL_MTW;
			
		};break;
		default:{
			nombre_apellidos = "";
			fecha_nacimiento = "";
			sexo_persona = "";
	        dni_persona = "";
			
		};break;
		}
		//
		if (camposPlantilla.contains(cambio_dom
				)) {
			campoAux = new CampoPdfBean(cambio_dom,
					(null != interviniente.getNifInterviniente() || null!= interviniente.getCambioDomicilio()? (interviniente.getCambioDomicilio().equals("true") ? VALOR_S
							: VALOR_N) 
							: ""), true, false, ConstantesPDF._6);
			camposFormateados.add(campoAux);
		}
		
		// SOLO PARA EL TITULAR (FLAG_AUTONOMO Y CODIGO_IAE)
		if (recurso==1){
		

		if (camposPlantilla.contains(ConstantesPDF.ID_FLAG_AUTONOMO_TITULAR_MTW
				)) {
			campoAux = new CampoPdfBean(
					ConstantesPDF.ID_FLAG_AUTONOMO_TITULAR_MTW,
					(null != interviniente.getNifInterviniente() || null!= interviniente.getAutonomo() ) ? (interviniente.getAutonomo().equals("true") ? VALOR_S : VALOR_N)
							: "", true, false, ConstantesPDF._6);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_CODIGO_IAE_TITULAR_MTW )) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_CODIGO_IAE_TITULAR_MTW 
					,
					(null != interviniente.getCodigoIAE() ? interviniente
							.getCodigoIAE() : ""), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		
		

		

		if (camposPlantilla.contains(ConstantesPDF.ID_TRAMITE_TUTELA
				+ recurso)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_TRAMITE_TUTELA
					+ recurso,
					(null != interviniente.getDatosDocumento() ? interviniente
							.getDatosDocumento() : ""), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		
		
		
		// Para fechas
		if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_INICIO_TUTELA_TITULAR_MTW
				)) {
			if (null != interviniente.getFechaInicio()
					&& (interviniente.getFechaInicio().getAnio() != null
							|| interviniente.getFechaInicio().getMes() != null || interviniente
							.getFechaInicio().getDia() != null)) {
				Fecha fecha = interviniente.getFechaInicio();
				campoAux = new CampoPdfBean(ConstantesPDF.ID_FECHA_INICIO_TUTELA_TITULAR_MTW,
						fecha.toString(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
		}
		}
		// FIN DATOS PROPIOS DEL TITULAR
		
		// DATOS PROPIOS DEL ARRENDATARIO 
		if (recurso==2)
		{
			String fechaHoraInicio = "";
			String fechaHoraFin = "";
			// FECHA INICIO RENTING 
			if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_INICIO_ARRENDATARIO_MTW)) {
				if (null != interviniente.getFechaInicio()) {
					String Fech_inicio = interviniente.getFechaInicio().toString();
					campoAux = new CampoPdfBean(ConstantesPDF.ID_FECHA_INICIO_ARRENDATARIO_MTW, Fech_inicio, false, false, ConstantesPDF._8);
					camposFormateados.add(campoAux);
				}
			}	
			fechaHoraInicio += interviniente.getFechaInicio() !=null ? interviniente.getFechaInicio().toString() + "  ":"";
						
			// FECHA FIN RENTING
			if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_FIN_ARRENDATARIO_MTW)) {
				if (null != interviniente.getFechaFin()) {
					String Fech_fin = interviniente.getFechaFin().toString();
					campoAux = new CampoPdfBean(ConstantesPDF.ID_FECHA_FIN_ARRENDATARIO_MTW, Fech_fin, false, false, ConstantesPDF._8);
					camposFormateados.add(campoAux);
				}
			}	
			fechaHoraFin += interviniente.getFechaFin() !=null ? interviniente.getFechaFin().toString() + "  ":"";
						
			// HORA INICIO RENTING
			if (camposPlantilla.contains(ConstantesPDF.ID_HORA_INICIO_ARRENDATARIO_MTW)) {
				if (null != interviniente.getHoraInicio()) {
					String Hor_inicio = interviniente.getHoraInicio().toString();
					campoAux = new CampoPdfBean(ConstantesPDF.ID_HORA_INICIO_ARRENDATARIO_MTW, Hor_inicio, false, false, ConstantesPDF._8);
					camposFormateados.add(campoAux);
				}
			}
			fechaHoraInicio += interviniente.getHoraInicio() !=null ? interviniente.getHoraInicio().toString():"";
			
			// FECHA/HORA RENTING INICIO
			if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_HORA_INICIO_ARRENDATARIO_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_FECHA_HORA_INICIO_ARRENDATARIO_MTW, fechaHoraInicio, false, false, ConstantesPDF._8);
				camposFormateados.add(campoAux);
			}
						
		   // HORA FIN RENTING
			if (camposPlantilla.contains(ConstantesPDF.ID_HORA_FIN_ARRENDATARIO_MTW)) {
				if (null != interviniente.getHoraFin()) {
					String Hor_Fin = interviniente.getHoraFin().toString();
					campoAux = new CampoPdfBean(ConstantesPDF.ID_HORA_FIN_ARRENDATARIO_MTW, Hor_Fin, false, false, ConstantesPDF._8);
					camposFormateados.add(campoAux);
				}
			}
			fechaHoraFin += interviniente.getHoraFin() !=null ? interviniente.getHoraFin().toString():"";
			
			// FECHA/HORA RENTING INICIO
			if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_HORA_FIN_ARRENDATARIO_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_FECHA_HORA_FIN_ARRENDATARIO_MTW, fechaHoraFin, false, false, ConstantesPDF._8);
				camposFormateados.add(campoAux);
			}
		}
		
		// campos propios del conductor
		if (recurso==3)
		{
			// FECHA INICIO
			if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_INICIO_CONDUCTOR_HABITUAL_MTW)) {
				if (null != interviniente.getFechaInicio()) {
					String Fech_inicio = interviniente.getFechaInicio().toString();
					campoAux = new CampoPdfBean(ConstantesPDF.ID_FECHA_INICIO_CONDUCTOR_HABITUAL_MTW, Fech_inicio, false, false, ConstantesPDF._8);
					camposFormateados.add(campoAux);
				}
			}	
			
			// FECHA FIN 
			String fechaHoraFin = "";
			if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_FIN_CONDUCTOR_HABITUAL_MTW)) {
				if (null != interviniente.getFechaFin()) {
					String Fech_fin = interviniente.getFechaFin().toString();
					campoAux = new CampoPdfBean(ConstantesPDF.ID_FECHA_FIN_CONDUCTOR_HABITUAL_MTW, Fech_fin, false, false, ConstantesPDF._8);
					camposFormateados.add(campoAux);
				}
			}	
			fechaHoraFin += interviniente.getFechaFin() !=null ? interviniente.getFechaFin().toString() + "  " :"";
			
			// HORA FIN
			if (camposPlantilla.contains(ConstantesPDF.ID_HORA_FIN_ARRENDATARIO_MTW)) {
				if (null != interviniente.getHoraFin()) {
					String Hor_Fin = interviniente.getHoraFin().toString();
					campoAux = new CampoPdfBean(ConstantesPDF.ID_HORA_FIN_ARRENDATARIO_MTW, Hor_Fin, false, false, ConstantesPDF._8);
					camposFormateados.add(campoAux);
				}
			}
			fechaHoraFin += interviniente.getHoraFin() !=null ? interviniente.getHoraFin().toString():"";
			
			// FECHA/HORA FIN
			if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_HORA_FIN_CONDUCTOR_HABITUAL_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_FECHA_HORA_FIN_CONDUCTOR_HABITUAL_MTW, fechaHoraFin, false, false, ConstantesPDF._8);
				camposFormateados.add(campoAux);
			}
		}
		
		// FIN DE DATOS PROPIOS DEL ARRENDATARIO
		
		// OBTENEMOS VALORES DE LA PERSONA PARA TODOS LOS TIPOS DE INTERVNIENTES (TITULAR , ARRENDATARIO, CONDUCTOR_HABITUAL
		
		Persona persona = interviniente.getPersona();

		if (camposPlantilla.contains(nombre_apellidos))  
		{
			String nombreApellidos = (null != persona.getNombre() ? persona
					.getNombre() : "")
					+ " "
					+ (null != persona.getApellido1RazonSocial() ? persona
							.getApellido1RazonSocial() : "")
					+ " "
					+ (null != persona.getApellido2() ? persona.getApellido2()
							: "");
			campoAux = new CampoPdfBean(nombre_apellidos
					 , nombreApellidos, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		
		if (camposPlantilla.contains(fecha_nacimiento
				)) {
			if (null != persona.getFechaNacimiento()
					&& (persona.getFechaNacimientoBean().getAnio() != null
							|| persona.getFechaNacimientoBean().getMes() != null || persona
							.getFechaNacimientoBean().getDia() != null)) {
				Fecha fecha = persona.getFechaNacimientoBean();
				campoAux = new CampoPdfBean(
						fecha_nacimiento, fecha
								.toString(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
		}
		
		
		if (camposPlantilla.contains(sexo_persona)) {
			campoAux = new CampoPdfBean(sexo_persona,
					persona.getSexo(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		
		if (camposPlantilla.contains(dni_persona)) {
			campoAux = new CampoPdfBean(dni_persona ,
					persona.getNif(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		
		 // Dirección DE LA PERSONA
		 if (null != persona.getDireccion()) {
			Direccion direccion = persona.getDireccion();
			camposFormateados.addAll(obtenerValoresDireccionMATEW( ConstantesPDF._8,direccion,recurso,camposPlantilla));
			
		 }	
		return camposFormateados;
	}
	
	
	
	
	
	/**
	 * Método que pasandole estos parámetros devolverá los campos que
	 * correspondan para el PDF correspondientes a una dirección.
	 * 
	 * @param direccion
	 * @param recurso
	 * @param camposPlantilla
	 * @return
	 */
	public Collection<? extends CampoPdfBean> obtenerValoresDireccion(
			Integer tamCampos, Direccion direccion, String recurso,
			Set<String> camposPlantilla) {

		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();
		CampoPdfBean campoAux = null;

		if (camposPlantilla.contains(ConstantesTrafico.ID_TIPO_VIA + recurso)) {
			campoAux = new CampoPdfBean(
					ConstantesTrafico.ID_TIPO_VIA + recurso, direccion
							.getTipoVia().getNombre(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesTrafico.ID_CALLE + recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_CALLE + recurso,
					utilesConversiones.ajustarCamposIne(direccion
							.getNombreVia()), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesTrafico.ID_NUM_DOMICILIO
				+ recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_NUM_DOMICILIO
					+ recurso, direccion.getNumero(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesTrafico.ID_KM_DOMICILIO
				+ recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_KM_DOMICILIO
					+ recurso, direccion.getPuntoKilometrico(), false, false,
					tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesTrafico.ID_HM_DOMICILIO
				+ recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_HM_DOMICILIO
					+ recurso, direccion.getHm(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesTrafico.ID_BLOQUE_DOMICILIO
				+ recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_BLOQUE_DOMICILIO
					+ recurso, direccion.getBloque(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesTrafico.ID_LETRA_DOMICILIO
				+ recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_LETRA_DOMICILIO
					+ recurso, direccion.getLetra(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesTrafico.ID_ESCALERA_DOMICILIO
				+ recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_ESCALERA_DOMICILIO
					+ recurso, direccion.getEscalera(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesTrafico.ID_PISO_DOMICILIO
				+ recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_PISO_DOMICILIO
					+ recurso, direccion.getPlanta(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesTrafico.ID_PUERTA_DOMICILIO
				+ recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_PUERTA_DOMICILIO
					+ recurso, direccion.getPuerta(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesTrafico.ID_MUNICIPIO + recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_MUNICIPIO
					+ recurso, utilesConversiones.ajustarCamposIne(direccion
					.getMunicipio().getNombre()), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesTrafico.ID_PUEBLO + recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_PUEBLO + recurso,
					utilesConversiones.ajustarCamposIne(direccion.getPueblo()),
					false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesTrafico.ID_PROVINCIA + recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_PROVINCIA
					+ recurso, direccion.getMunicipio().getProvincia()
					.getNombre(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesTrafico.ID_CP + recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_CP + recurso,
					direccion.getCodPostal(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesTrafico.ID_DOMICILIO_COMPLETO
				+ recurso)) {
			String domicilioCompleto = null;
			if (direccion.getTipoVia().getNombre() != null
					&& !direccion.getTipoVia().getNombre().equals("")) {
				domicilioCompleto = direccion.getTipoVia().getNombre();
			}
			if (domicilioCompleto != null && direccion.getNombreVia() != null
					&& !direccion.getNombreVia().equals("")) {
				domicilioCompleto += " "
						+ utilesConversiones.ajustarCamposIne(direccion
								.getNombreVia());
			}
			if (domicilioCompleto != null && direccion.getNumero() != null
					&& !direccion.getNumero().equals("")) {
				domicilioCompleto += ", " + direccion.getNumero();
			}
			if (domicilioCompleto != null && direccion.getLetra() != null
					&& !direccion.getLetra().equals("")) {
				domicilioCompleto += ", puerta " + direccion.getLetra();
			}
			if (domicilioCompleto != null && direccion.getEscalera() != null
					&& !direccion.getEscalera().equals("")) {
				domicilioCompleto += ", esc. " + direccion.getEscalera();
			}
			if (domicilioCompleto != null && direccion.getPlanta() != null
					&& !direccion.getPlanta().equals("")) {
				domicilioCompleto += ", planta " + direccion.getPlanta();
			}
			if (domicilioCompleto != null && direccion.getPuerta() != null
					&& !direccion.getPuerta().equals("")) {
				domicilioCompleto += ", portal " + direccion.getPuerta();
			}
			if (domicilioCompleto != null && direccion.getBloque() != null
					&& !direccion.getBloque().equals("")) {
				domicilioCompleto += ", blq. " + direccion.getBloque();
			}
			if (domicilioCompleto != null
					&& direccion.getPuntoKilometrico() != null
					&& !direccion.getPuntoKilometrico().equals("")) {
				domicilioCompleto += ", km. " + direccion.getPuntoKilometrico();
			}
			if (domicilioCompleto != null && direccion.getHm() != null
					&& !direccion.getHm().equals("")) {
				domicilioCompleto += ", hm. " + direccion.getHm();
			}
			if (domicilioCompleto != null && direccion.getCodPostal() != null
					&& !direccion.getCodPostal().equals("")) {
				domicilioCompleto += ", CP. " + direccion.getCodPostal();
			}
			if (domicilioCompleto != null && direccion.getPueblo() != null
					&& !direccion.getPueblo().equals("")) {
				domicilioCompleto += ", "
						+ utilesConversiones.ajustarCamposIne(direccion
								.getPueblo());
			}
			if (domicilioCompleto != null && direccion.getMunicipio() != null
					&& direccion.getMunicipio().getNombre() != null) {
				domicilioCompleto += ", "
						+ utilesConversiones.ajustarCamposIne(direccion
								.getMunicipio().getNombre());
			}
			if (domicilioCompleto != null
					&& direccion.getMunicipio() != null
					&& direccion.getMunicipio().getProvincia() != null
					&& direccion.getMunicipio().getProvincia().getNombre() != null) {
				domicilioCompleto += ", "
						+ direccion.getMunicipio().getProvincia().getNombre();
			}
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_DOMICILIO_COMPLETO
					+ recurso, domicilioCompleto != null ? domicilioCompleto
					: "", false, false,
					recurso.equals("_VEHICULO") ? tamCampos + 3 : tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_DOMICILIO_DIRECCION
				+ recurso)) {
			String domicilioDireccion = null;
			if (direccion.getTipoVia().getNombre() != null
					&& !direccion.getTipoVia().getNombre().equals("")) {
				domicilioDireccion = direccion.getTipoVia().getNombre();
			}
			if (domicilioDireccion != null && direccion.getNombreVia() != null
					&& !direccion.getNombreVia().equals("")) {
				domicilioDireccion += " "
						+ utilesConversiones.ajustarCamposIne(direccion
								.getNombreVia());
			}
			if (domicilioDireccion != null && direccion.getNumero() != null
					&& !direccion.getNumero().equals("")) {
				domicilioDireccion += ", " + direccion.getNumero();
			}
			if (domicilioDireccion != null && direccion.getLetra() != null
					&& !direccion.getLetra().equals("")) {
				domicilioDireccion += ", puerta " + direccion.getLetra();
			}
			if (domicilioDireccion != null && direccion.getEscalera() != null
					&& !direccion.getEscalera().equals("")) {
				domicilioDireccion += ", esc. " + direccion.getEscalera();
			}
			if (domicilioDireccion != null && direccion.getPlanta() != null
					&& !direccion.getPlanta().equals("")) {
				domicilioDireccion += ", planta " + direccion.getPlanta();
			}
			if (domicilioDireccion != null && direccion.getPuerta() != null
					&& !direccion.getPuerta().equals("")) {
				domicilioDireccion += ", portal " + direccion.getPuerta();
			}
			if (domicilioDireccion != null && direccion.getBloque() != null
					&& !direccion.getBloque().equals("")) {
				domicilioDireccion += ", blq. " + direccion.getBloque();
			}
			if (domicilioDireccion != null
					&& direccion.getPuntoKilometrico() != null
					&& !direccion.getPuntoKilometrico().equals("")) {
				domicilioDireccion += ", km. "
						+ direccion.getPuntoKilometrico();
			}
			if (domicilioDireccion != null && direccion.getHm() != null
					&& !direccion.getHm().equals("")) {
				domicilioDireccion += ", hm. " + direccion.getHm();
			}

			campoAux = new CampoPdfBean(
					ConstantesTrafico.ID_DOMICILIO_DIRECCION + recurso,
					domicilioDireccion != null ? domicilioDireccion : "",
					false, false, recurso.equals("_VEHICULO") ? tamCampos + 3
							: tamCampos);
			camposFormateados.add(campoAux);
		}

		return camposFormateados;

	}
	
	/**
	 * Método que pasandole estos parámetros devolverá los campos que
	 * correspondan para el PDF correspondientes a una dirección.
	 * 
	 * @param direccion
	 * @param recurso
	 * @param camposPlantilla
	 * @return
	 */
	public Collection<? extends CampoPdfBean> obtenerValoresDireccionMATEW(
			Integer tamCampos, Direccion direccion, int recurso,
			Set<String> camposPlantilla) {

		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();
		CampoPdfBean campoAux = null;
		String domicilio = null;

		switch (recurso){
		case 1:{ 
			domicilio = ConstantesPDF.ID_DOMICILIO_COMPLETO_TITULAR_MTW;
		
		};break;
		// ARRENDATARIO
		case 2:{
			
			domicilio = ConstantesPDF.ID_DOMICILIO_COMPLETO_ARRENDATARIO_MTW;
			
		};break;
		// CONDUCTOR HABITUAL
		case 3:{
				domicilio = ConstantesPDF.ID_DOMICILIO_COMPLETO_CONDUCTOR_HABITUAL_MTW;
			};break;
		
		// VEHICULO
		case 4: {
			domicilio = ConstantesPDF.ID_DOMICILIO_COMPLETO_MTW_VEHICULO;
		};break;
		
		default:{
			
			domicilio = "";
	};break;
		}
		
		
		if (camposPlantilla.contains(domicilio
				)) {
			String domicilioCompleto = null;
			if (direccion.getTipoVia().getNombre() != null
					&& !direccion.getTipoVia().getNombre().equals("")) {
				domicilioCompleto = direccion.getTipoVia().getNombre();
			}
			if (domicilioCompleto != null && direccion.getNombreVia() != null
					&& !direccion.getNombreVia().equals("")) {
				domicilioCompleto += " "
						+ utilesConversiones.ajustarCamposIne(direccion
								.getNombreVia());
			}
			if (domicilioCompleto != null && direccion.getNumero() != null
					&& !direccion.getNumero().equals("")) {
				domicilioCompleto += ", " + direccion.getNumero();
			}
			if (domicilioCompleto != null && direccion.getLetra() != null
					&& !direccion.getLetra().equals("")) {
				domicilioCompleto += ", puerta " + direccion.getLetra();
			}
			if (domicilioCompleto != null && direccion.getEscalera() != null
					&& !direccion.getEscalera().equals("")) {
				domicilioCompleto += ", esc. " + direccion.getEscalera();
			}
			if (domicilioCompleto != null && direccion.getPlanta() != null
					&& !direccion.getPlanta().equals("")) {
				domicilioCompleto += ", planta " + direccion.getPlanta();
			}
			if (domicilioCompleto != null && direccion.getPuerta() != null
					&& !direccion.getPuerta().equals("")) {
				domicilioCompleto += ", portal " + direccion.getPuerta();
			}
			if (domicilioCompleto != null && direccion.getBloque() != null
					&& !direccion.getBloque().equals("")) {
				domicilioCompleto += ", blq. " + direccion.getBloque();
			}
			if (domicilioCompleto != null
					&& direccion.getPuntoKilometrico() != null
					&& !direccion.getPuntoKilometrico().equals("")) {
				domicilioCompleto += ", km. " + direccion.getPuntoKilometrico();
			}
			if (domicilioCompleto != null && direccion.getHm() != null
					&& !direccion.getHm().equals("")) {
				domicilioCompleto += ", hm. " + direccion.getHm();
			}
			if (domicilioCompleto != null && direccion.getCodPostalCorreos() != null
					&& !direccion.getCodPostalCorreos().equals("")) {
				domicilioCompleto += ", CP. " + direccion.getCodPostalCorreos();
			}
			if (domicilioCompleto != null && direccion.getPuebloCorreos() != null
					&& !direccion.getPuebloCorreos().equals("")) {
				domicilioCompleto += ", "
						+ direccion.getPuebloCorreos();
			}
			if (domicilioCompleto != null && direccion.getMunicipio() != null
					&& direccion.getMunicipio().getNombre() != null) {
				domicilioCompleto += ", "
						+ utilesConversiones.ajustarCamposIne(direccion
								.getMunicipio().getNombre());
			}
			if (domicilioCompleto != null
					&& direccion.getMunicipio() != null
					&& direccion.getMunicipio().getProvincia() != null
					&& direccion.getMunicipio().getProvincia().getNombre() != null) {
				domicilioCompleto += ", "
						+ direccion.getMunicipio().getProvincia().getNombre();
			}
			campoAux = new CampoPdfBean(domicilio
					, domicilioCompleto != null ? domicilioCompleto
					: "", false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		
		return camposFormateados;
	
		}
	
	

	/**
	 * Método que pasándole los siguientes parámetros nos devolverá los campos
	 * formateados correspondientes al Mandato.
	 * @param idContrato 
	 * 
	 * @param interviniente
	 * @param recurso
	 * @param camposPlantilla
	 * @return
	 * @throws OegamExcepcion 
	 */
	public Collection<? extends CampoPdfBean> obtenerValoresMandato(
			Integer tamCampos,
			IntervinienteTrafico titular,IntervinienteTrafico representante,
			BigDecimal idContrato, Set<String> camposPlantilla) throws OegamExcepcion {

		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();
		CampoPdfBean campoAux = null;

		Persona personaTitular = titular.getPersona();
 
		
		// Comprobamos si es una persona jurídica para la primera parte del
		// documento
		if (null != personaTitular && null != personaTitular.getSexo()
				&& personaTitular.getSexo().equals(PERSONA_JURIDICA)) {

			Persona personaRepresentante = representante.getPersona();

			// Datos de persona física
			if (camposPlantilla
					.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR)) {
				String nombreApellidos = (null != personaRepresentante
						.getNombre() ? personaRepresentante.getNombre() : "")
						+ " "
						+ (null != personaRepresentante
								.getApellido1RazonSocial() ? personaRepresentante
								.getApellido1RazonSocial()
								: "")
						+ " "
						+ (null != personaRepresentante.getApellido2() ? personaRepresentante
								.getApellido2()
								: "");
				campoAux = new CampoPdfBean(
						ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR,
						nombreApellidos, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_DNI_TITULAR)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_DNI_TITULAR,
						personaRepresentante.getNif(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// Datos de persona jurídica
			if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_ENTIDAD)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_NOMBRE_ENTIDAD,
						personaTitular.getApellido1RazonSocial(), false, false,
						tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesPDF.CIF_ENTIDAD)) {
				campoAux = new CampoPdfBean(ConstantesPDF.CIF_ENTIDAD,
						personaTitular.getNif(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_MUNICIPIO_ENTIDAD)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_MUNICIPIO_ENTIDAD,
						personaTitular.getDireccion().getMunicipio()
								.getNombre(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_DOMICILIO_ENTIDAD)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_DOMICILIO_ENTIDAD,
						utilesConversiones.ajustarCamposIne(personaTitular
								.getDireccion().getNombreVia()), false, false,
						tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla
					.contains(ConstantesPDF.ID_NUM_DOMICILIO_ENTIDAD)) {
				campoAux = new CampoPdfBean(
						ConstantesPDF.ID_NUM_DOMICILIO_ENTIDAD, personaTitular
								.getDireccion().getNumero(), false, false,
						tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_CP_ENTIDAD)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_CP_ENTIDAD,
						personaTitular.getDireccion().getCodPostal(), false,
						false, tamCampos);
				camposFormateados.add(campoAux);
			}

		} else if (null != personaTitular && null != personaTitular.getSexo()
				&& !personaTitular.getSexo().equals(PERSONA_JURIDICA)) {
			
			if(representante != null && representante.getPersona() != null && representante.getPersona().getNif() != null){
			Persona personaRepresentante = representante.getPersona();
			
			// Datos de persona física
				if (camposPlantilla
						.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR)) {
					String nombreApellidos = (null != personaRepresentante.getNombre() ? personaRepresentante
							.getNombre()
							: "")
							+ " "
							+ (null != personaRepresentante.getApellido1RazonSocial() ? personaRepresentante
									.getApellido1RazonSocial()
									: "")
							+ " "
							+ (null != personaRepresentante.getApellido2() ? personaRepresentante
									.getApellido2()
									: "");
					campoAux = new CampoPdfBean(
							ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR,
							nombreApellidos, false, false, tamCampos);
					camposFormateados.add(campoAux);
				}
	
				if (camposPlantilla.contains(ConstantesPDF.ID_DNI_TITULAR)) {
					campoAux = new CampoPdfBean(ConstantesPDF.ID_DNI_TITULAR,
							personaRepresentante.getNif(), false, false, tamCampos);
					camposFormateados.add(campoAux);
				}
				if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_ENTIDAD)) {
					String nombreApellidos = (null != personaTitular.getNombre() ? personaTitular.getNombre() : "") + " " + (null != personaTitular.getApellido1RazonSocial() ? personaTitular
							.getApellido1RazonSocial() : "") + " " + (null != personaTitular.getApellido2() ? personaTitular.getApellido2() : "");
					campoAux = new CampoPdfBean(ConstantesPDF.ID_NOMBRE_ENTIDAD, nombreApellidos, false, false, tamCampos);
					camposFormateados.add(campoAux);
				}

				if (camposPlantilla.contains(ConstantesPDF.CIF_ENTIDAD)) {
					campoAux = new CampoPdfBean(ConstantesPDF.CIF_ENTIDAD, personaTitular.getNif(), false, false, tamCampos);
					camposFormateados.add(campoAux);
				}
			}else{
				if (camposPlantilla
						.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR)) {
					String nombreApellidos = (null != personaTitular.getNombre() ? personaTitular
							.getNombre()
							: "")
							+ " "
							+ (null != personaTitular.getApellido1RazonSocial() ? personaTitular
									.getApellido1RazonSocial()
									: "")
							+ " "
							+ (null != personaTitular.getApellido2() ? personaTitular
									.getApellido2()
									: "");
					campoAux = new CampoPdfBean(
							ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR,
							nombreApellidos, false, false, tamCampos);
					camposFormateados.add(campoAux);
				}
	
				if (camposPlantilla.contains(ConstantesPDF.ID_DNI_TITULAR)) {
					campoAux = new CampoPdfBean(ConstantesPDF.ID_DNI_TITULAR,
							personaTitular.getNif(), false, false, tamCampos);
					camposFormateados.add(campoAux);
				}
			}
		}

		// SE COLOCA LA DIRECCIÓN EN TODOS LOS CASOS

		String nombreVia = utilesConversiones.ajustarCamposIne(personaTitular
				.getDireccion().getNombreVia());
		String nombreViaYMunicipio = "";

		// Picar la dirección si tiene más de 13 caractéres
		if (nombreVia != null && !nombreVia.isEmpty()) {
			nombreViaYMunicipio = nombreVia + (null !=personaTitular.getDireccion().getMunicipio().getNombre() ? ", " + personaTitular.getDireccion().getMunicipio().getNombre() : "");
		} else {
			/*nombreViaYMunicipio = ", "
					+ personaTitular.getDireccion().getMunicipio().getNombre();*/
			nombreViaYMunicipio = (null !=personaTitular.getDireccion().getMunicipio().getNombre()
			? personaTitular.getDireccion().getMunicipio().getNombre() : "");
			
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_PROVINCIA_ENTIDAD)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_PROVINCIA_ENTIDAD,
					personaTitular.getDireccion().getMunicipio().getProvincia()
							.getNombre(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}


		if (camposPlantilla.contains(ConstantesPDF.ID_NUM_DOMICILIO_ENTIDAD)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_NUM_DOMICILIO_ENTIDAD,
					personaTitular.getDireccion().getNumero(), false, false,
					tamCampos);
			camposFormateados.add(campoAux);
		}
		
		if (camposPlantilla
				.contains(ConstantesPDF.ID_DOMICILIO_ENTIDAD)) {
			campoAux = new CampoPdfBean(
					ConstantesPDF.ID_DOMICILIO_ENTIDAD,
					nombreVia, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_CP_ENTIDAD)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_CP_ENTIDAD,
					personaTitular.getDireccion().getCodPostal(), false, false,
					tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.ID_MUNICIPIO_ENTIDAD)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_MUNICIPIO_ENTIDAD,
					personaTitular.getDireccion().getMunicipio()
							.getNombre(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		// Mantis 23994: se generan los mandatos con los datos del usuario en sesion, en lugar del propio tramite.
		ServicioContrato servicioContrato =  ContextoSpring.getInstance().getBean(ServicioContrato.class);
		ContratoDto contratoDto = servicioContrato.getContratoDto(idContrato);
		if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_GESTOR)) {
			int inicioNombre = contratoDto.getColegiadoDto().getUsuario().getApellidosNombre().indexOf(", ");
			String nombre = contratoDto.getColegiadoDto().getUsuario().getApellidosNombre().substring(inicioNombre + 2,  contratoDto.getColegiadoDto().getUsuario().getApellidosNombre().length()); 
			String apellidos =  contratoDto.getColegiadoDto().getUsuario().getApellidosNombre().substring(0,inicioNombre);
			campoAux = new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_GESTOR,nombre + " " + apellidos , false, false, tamCampos);
			camposFormateados.add(campoAux);
		}


		if (camposPlantilla.contains(ConstantesPDF.ID_NUM_COLEGIADO_GESTOR)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_NUM_COLEGIADO_GESTOR, contratoDto.getColegiadoDto().getNumColegiado(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_COLEGIO_GESTOR)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_COLEGIO_GESTOR,
					contratoDto.getColegioDto().getColegio(), false, false,
					tamCampos);
			camposFormateados.add(campoAux);
		}

		
		if (camposPlantilla.contains(ConstantesPDF.ID_MUNICIPIO_GESTOR)) {
			ServicioDireccion servicioDireccion = ContextoSpring.getInstance().getBean(ServicioDireccion.class);
			String nombreMunicipioGestor = servicioDireccion.obtenerNombreMunicipio(contratoDto.getIdMunicipio(), contratoDto.getIdProvincia());
			campoAux = new CampoPdfBean(ConstantesPDF.ID_MUNICIPIO_GESTOR,
					nombreMunicipioGestor, false, false,
					tamCampos);
			
			
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_COLEGIO)) {
			
			campoAux = new CampoPdfBean(ConstantesPDF.ID_COLEGIO,
					contratoDto.getColegioDto().getColegio(), false, false,
					tamCampos);
			
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_DOMICILIO_GESTOR)) {
		
			campoAux = new CampoPdfBean(ConstantesPDF.ID_DOMICILIO_GESTOR,
			utilesConversiones.ajustarCamposIne(contratoDto.getVia()), false, false,
					tamCampos);
			
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.ID_MUNICIPIO_MANDANTE)) {
			
			ServicioDireccion servicioDireccion = ContextoSpring.getInstance().getBean(ServicioDireccion.class);
			String nombreMunicipio = servicioDireccion.obtenerNombreMunicipio(contratoDto.getIdMunicipio(), contratoDto.getIdProvincia());
			campoAux = new CampoPdfBean(ConstantesPDF.ID_MUNICIPIO_MANDANTE, nombreMunicipio, false, false, tamCampos);
			camposFormateados.add(campoAux);

		}
		
		//Mantis 25624 NUEVO MANDATO
		
		
		
		//Mantis
		//Mantis 25624 NUEVO MANDATO
		//RELLENAMOS LOS DATOS DE LA GESTORIA
		
		
		//provincia
		if (camposPlantilla.contains(ConstantesPDF.ID_DOMICILIO_GA)) {
			


			ServicioDireccion servicioDireccion = ContextoSpring.getInstance().getBean(ServicioDireccion.class);
			String nombreMunicipio = servicioDireccion.obtenerNombreMunicipio(contratoDto.getIdMunicipio(), contratoDto.getIdProvincia());
			campoAux = new CampoPdfBean(ConstantesPDF.ID_DOMICILIO_GA, nombreMunicipio, false, false, tamCampos);
			camposFormateados.add(campoAux);

		}
		
		
		//calle
		if (camposPlantilla.contains(ConstantesPDF.ID_VIA_MANDATARIO)) {
			
			campoAux = new CampoPdfBean(ConstantesPDF.ID_VIA_MANDATARIO,
					contratoDto.getVia(), false, false, tamCampos);
			
			camposFormateados.add(campoAux);
		}
		
		
		//Numero
		if (camposPlantilla.contains(ConstantesPDF.ID_VIA_GA)) {
			
			campoAux = new CampoPdfBean(ConstantesPDF.ID_VIA_GA,
					contratoDto.getNumero(), false, false, tamCampos);
			
			camposFormateados.add(campoAux);
		}
		
		if (camposPlantilla.contains(ConstantesPDF.ID_CP_GA)) {
			
			campoAux = new CampoPdfBean(ConstantesPDF.ID_CP_GA,
					contratoDto.getCodPostal(), false, false, tamCampos);
			
			camposFormateados.add(campoAux);
		}
		
		if (camposPlantilla.contains(ConstantesPDF.ID_NIF_GA)) {
			
			campoAux = new CampoPdfBean(ConstantesPDF.ID_NIF_GA,
					contratoDto.getCif(), false, false, tamCampos);
			
			camposFormateados.add(campoAux);
		}
		/* Solo para los genericos*/
		if (camposPlantilla.contains(ConstantesPDF.NIF_GA)) {
			
			campoAux = new CampoPdfBean(ConstantesPDF.NIF_GA,
					contratoDto.getCif(), false, false, tamCampos);
			
			camposFormateados.add(campoAux);
		}

//		
//		if (camposPlantilla.contains(ConstantesPDF.ID_DOMICILIO_GA)) {
//			
//			campoAux = new CampoPdfBean(ConstantesPDF.ID_DOMICILIO_GA,
//					ConstantesPDF.VALOR_PROVINCIA_MADRID, false, false, tamCampos);
//			
//			camposFormateados.add(campoAux);
//		}
//		
//		if (camposPlantilla.contains(ConstantesPDF.ID_VIA_MANDATARIO)) {
//			
//			campoAux = new CampoPdfBean(ConstantesPDF.ID_VIA_MANDATARIO,
//					ConstantesPDF.VALOR_DIRECCION_COLEGIO, false, false, tamCampos);
//			
//			camposFormateados.add(campoAux);
//		}
//		
//		if (camposPlantilla.contains(ConstantesPDF.ID_VIA_GA)) {
//			
//			campoAux = new CampoPdfBean(ConstantesPDF.ID_VIA_GA,
//					ConstantesPDF.VALOR_NUMERO_DIRECCION_COLEGIO, false, false, tamCampos);
//			
//			camposFormateados.add(campoAux);
//		}
//		
//		if (camposPlantilla.contains(ConstantesPDF.ID_CP_GA)) {
//			
//			campoAux = new CampoPdfBean(ConstantesPDF.ID_CP_GA,
//					ConstantesPDF.VALOR_CODIGO_POSTAL_COLEGIO, false, false, tamCampos);
//			
//			camposFormateados.add(campoAux);
//		}
		
		
		//FIN Mantis 25624 NUEVO MANDATO
		

		if (camposPlantilla.contains(ConstantesPDF.ID_NUM_DOMICILIO_GESTOR)) {
		
			campoAux = new CampoPdfBean(ConstantesPDF.ID_NUM_DOMICILIO_GESTOR,
					contratoDto.getNumero(), false, false, tamCampos);
			
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_CP_GESTOR)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_CP_GESTOR, contratoDto.getCodPostal(), false, false, tamCampos);
			
			camposFormateados.add(campoAux);
		}
     // FIN modificacion por incidencia 1708
		return camposFormateados;
	}
	
	/**
	 * Método para obtener el String para la nube de matriculación MATW - VERSION 2 SEPTIEMBRE 2013. UTILIZA CLASE UtilesMATW
	 * 
	 * @param detalleMatriculacion
	 * @return
	 */
	
	protected String obtenerNubeMatriculacionMATW(
			TramiteTraficoMatriculacionBean detalleMatriculacion,int mancha_ini,int mancha_fin){
		
		
		StringBuffer line = new StringBuffer();
		
		
		// Inicializamos utilesConversiones para la provincia del titular , vehiculo, arrendatario y conductor habitual.
				
		// Inicializamos ARRAY CON LOS CAMPOS DE LA NUBE
		UtilesMATW ArrayNubeMATW = new UtilesMATW();
		 // ArrayNubeMATW
		UtilesConversionesMatw utilConv = new UtilesConversionesMatw();
		String ncampo="";
		String campoNube="";
		
		
		   for (int i=mancha_ini; i< mancha_fin; i++){
		   
			 ncampo = ArrayNubeMATW.getCampoValorArray(i);
			 campoNube = ObtenerValorCampoNube(ncampo, detalleMatriculacion,utilConv);
		     if(campoNube!= null){
		    	 line.append(BasicText.changeSize(campoNube,ArrayNubeMATW.getLongitudCampoNube(ncampo)));
		     }
		   }

			if(log.isDebugEnabled()) {
				log.debug("Nube de puntos: "+line.toString());
			}
            return line.toString();
		
	}
	
	
	
	/**
	 * Método para obtener el String para la nube de matriculación 1.
	 * 
	 * @param detalleMatriculacion
	 * @return
	 */
	protected String obtenerNubeMatriculacion(
			TramiteTraficoMatriculacionBean detalleMatriculacion) {
		SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
		StringBuffer line = new StringBuffer();
		String campo = "";
		String tipoVehiculo = null;
		
		if (detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getTipoVehiculoBean() != null) {
			tipoVehiculo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getTipoVehiculoBean().getTipoVehiculo() != null ? detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
					.getTipoVehiculoBean().getTipoVehiculo() : null;
		}

		boolean ciclomotor = null != tipoVehiculo && UtilesVistaTrafico.getInstance().queTipoVehiculo(tipoVehiculo).equals(ConstantesPDF.TIPO_MATRICULA_CICLOMOTOR);

		boolean remolque = null != tipoVehiculo && ConstantesPDF.TIPO_MATRICULA_REMOLQUE.equals(UtilesVistaTrafico.getInstance().queTipoVehiculo(tipoVehiculo));

		// Variable que si vale true evitará el Co2 en la nube y en el pdf, puesto
		// que el combustible seleccionado no emite dióxido de carbono.
		// boolean combustibleSinCo2 = false;
		CarburanteBean carburanteBean = detalleMatriculacion.getTramiteTraficoBean().
			getVehiculo().getCarburanteBean();
		if(remolque){
			carburanteBean = null;
		}
		/*if(carburanteBean != null && 
				(carburanteBean.getIdCarburante().equals(Combustible.Electrico.getValorEnum()) ||
					carburanteBean.getIdCarburante().equals(Combustible.Hidrogeno.getValorEnum()) ||
					carburanteBean.getIdCarburante().equals(Combustible.BioMetano.getValorEnum()))){
			combustibleSinCo2 = true;
		}*/
		
		boolean electrico = false;
		if(carburanteBean != null &&  carburanteBean.getIdCarburante() != null && 
				carburanteBean.getIdCarburante().equals(Combustible.Electrico.getValorEnum())){
			electrico = true;
		}
		
		// Comenzamos a meter los campos de las etiquetas en orden para generar
		// la mancha
		campo = ConstantesPDF.VERSION_MATRICULACION;
		line.append(campo);
		campo = PROGRAMA_MATRICULACION;
		line.append(campo);

		campo = detalleMatriculacion.getTramiteTraficoBean().getTasa()
				.getCodigoTasa();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_TASA));
		} else if (campo.equals("EXENTO")) {
			line.append(BasicText.changeSize(campo, TAM_TASA));
		} else {
			line.append(BasicText.changeSize(campo, TAM_TASA, '0', false));
		}

		// En la definición la indica como fecha de Matriculación, pero a los
		// efectos tiene que ser la misma.
		try {
			if (detalleMatriculacion.getTramiteTraficoBean()
					.getFechaPresentacion() != null
					&& !detalleMatriculacion.getTramiteTraficoBean()
							.getFechaPresentacion().isfechaNula()) {
				campo = formato.format(detalleMatriculacion
						.getTramiteTraficoBean().getFechaPresentacion()
						.getTimestamp());
			} else {
				campo = null;
			}
		} catch (ParseException e) {
			campo = null;
		}
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_FECHA_MATRICULACION));
		} else {
			line.append(BasicText.changeSize(campo, TAM_FECHA_MATRICULACION,
					'0', false));
		}

		if( detalleMatriculacion.getTramiteTraficoBean().getVehiculo() != null 
				&&  detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getCodItv() != null){
			campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
					.getCodItv();
		}else{
				campo = null;
		}
		if (campo == null) {
			line.append(BasicText.changeSize("", CODIGO_ITV));
		} else {
			if (campo.length() < CODIGO_ITV) {
				campo = ConstantesTrafico.SIN_CODIG;
			}
			line.append(BasicText.changeSize(campo, CODIGO_ITV));
		}

		if( detalleMatriculacion.getTramiteTraficoBean().getVehiculo() != null 
				&&  detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getMarcaBean() != null
				&&  detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getMarcaBean().getMarca() != null){
			campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
					.getMarcaBean().getMarca();
		}else{
			campo = null;
		}
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_MARCA));
		} else {
			line.append(BasicText.changeSize(campo, TAM_MARCA));
		}

		if( detalleMatriculacion.getTramiteTraficoBean().getVehiculo() != null 
				&&  detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getModelo() != null){
			campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
					.getModelo();
		}else{
			campo = null;
		}
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_MODELO));
		} else {
			line.append(BasicText.changeSize(campo, TAM_MODELO));
		}

		if( detalleMatriculacion.getTramiteTraficoBean().getVehiculo() != null 
			&&  detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getColorBean() != null){
			campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
					.getColorBean().getValorEnum();
		}else{
			campo = null;
		}
		if (campo == null || (campo != null && campo.equals("-"))) {
			line.append(BasicText.changeSize("", TAM_COLOR));
		} else {
			line.append(BasicText.changeSize(campo, TAM_COLOR));
		}
		// Cambiar por fabricación
		if( detalleMatriculacion.getTramiteTraficoBean().getVehiculo() != null 
				&&  detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getFabricacionBean() != null
				&&  detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getFabricacionBean().getIdFabricacion() != null){
			campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
						.getFabricacionBean().getIdFabricacion();
		}else{
			campo = null;
		}
		if(campo!=null && campo.equalsIgnoreCase("NAC")){
			campo="";
		}
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_PROCEDENCIA));// Fabricacion
			// Mate 2.5
		} else {
			line.append(BasicText.changeSize(campo, TAM_PROCEDENCIA));
		}

		campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
				.getBastidor();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_BASTIDOR_MATE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_BASTIDOR_MATE));
		}

		campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
				.getServicioTraficoBean().getIdServicio();
		// Por si es un trámite antiguo y no tiene el valor nuevo
		if (campo == null) {
			campo = null!= detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
					.getServicioTraficoBean().getIdServicio()? ConversorServicio.darServicioDGTdeServicioOEGAM(
					detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
							.getServicioTraficoBean().getIdServicio()).value():null;
		}
		
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_SERVICIO_MATE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_SERVICIO_MATE));
		}

		// Sólo será necesario pasarlo para: ciclomotores, motocicletas,
		// remolques y semirremolques
		if (detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getTipoVehiculoBean() != null) {
			campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getTipoVehiculoBean().getTipoVehiculo();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_TIPO_VEHICULO));
			} else {
				if ((campo.compareTo("90") == 0) || (campo.compareTo("91") == 0) || (campo.compareTo("92") == 0)) {
					ciclomotor = true;
				}
				line.append(BasicText.changeSize(campo, TAM_TIPO_VEHICULO));
			}
		} else {
			line.append(BasicText.changeSize("", TAM_TIPO_VEHICULO));
		}
		

		// Contemplar el tema de los decimales, las tres primeras posiciones
		// enteros, dos últimas decimales
		// Es opcional para los tipos 53 y 54.

		// campo = utiles.cambiarDecimalFormatoMancha(null !=
		// detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getPotenciaFiscal()?
		// detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getPotenciaFiscal():"");

		campo = null != detalleMatriculacion.getTramiteTraficoBean()
				.getVehiculo().getPotenciaFiscal() ? utiles
				.BigDecimalToStringDosDecimales(detalleMatriculacion
						.getTramiteTraficoBean().getVehiculo()
						.getPotenciaFiscal(), ConstantesPDF._5) : null;
		if ((campo == null) || (remolque)) {
			line.append(BasicText.changeSize("", TAM_POTENCIA));
		} else {
			if (campo.trim().equals("0")) {
				line.append(BasicText.changeSize("", TAM_POTENCIA));
			} else {
				line.append(BasicText.changeSize(campo.replaceAll(",", ""),
						TAM_POTENCIA, '0', false));
			}
		}

		// Contemplar los decimales, las tres primeras posiciones enteros, dos
		// últimas decimales
		campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
				.getCilindrada();
		if ((campo == null) || (remolque) || (electrico)) {
			line.append(BasicText.changeSize("", TAM_CILINDRADA));
		} else {
			if (ciclomotor) {
				campo = utiles.BigDecimalToStringDosDecimales(new BigDecimal(
						campo), ConstantesPDF._5);
			}
			line
					.append(BasicText.changeSize(campo, TAM_CILINDRADA, '0',
							false));
		}

		campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
				.getTara();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_TARA));
		} else {
			line.append(BasicText.changeSize(campo, TAM_TARA, '0', false));
		}

		// Sólo para tipos de vehículos destinados al transporte de mercancías.
		campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
				.getPesoMma();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_MASA));
		} else {
			line.append(BasicText.changeSize(campo, TAM_MASA, '0', false));
		}

		campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
				.getPlazas() != null ? detalleMatriculacion
				.getTramiteTraficoBean().getVehiculo().getPlazas().toString()
				: "";
		if (remolque) {
			line.append(BasicText.changeSize("", TAM_PLAZAS));
		} else if (campo == null) {
			line.append(BasicText.changeSize("", TAM_PLAZAS));
		} else {
			line.append(BasicText.changeSize(campo, TAM_PLAZAS, '0', false));
		}

		if (null != detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getCarburanteBean()) {
			if (detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getCarburanteBean().equals(Combustible.Otros)) {
				if (null != detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getTipoVehiculoBean()
						&& null != detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getTipoVehiculoBean().getTipoVehiculo()
						&& ("R".equals(detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getTipoVehiculoBean().getTipoVehiculo().substring(0, 1)) || "S".equals(detalleMatriculacion
								.getTramiteTraficoBean().getVehiculo().getTipoVehiculoBean().getTipoVehiculo().substring(0, 1)))) {
					campo = null;
				} else
					campo = TipoCombustible.O.toString();
			} else {
				campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getCarburanteBean().getIdCarburante();
			}
		} else {
			campo = null;
		}
		
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_CARBURANTE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_CARBURANTE));
		}

		// Domicilio del vehiculo

		// Si el vehículo tiene dirección propia se la ponemos, si no le ponemos
		// la del titular
		if ((null != detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
				.getDireccionBean())
				&& (null != detalleMatriculacion.getTramiteTraficoBean()
						.getVehiculo().getDireccionBean().getIdDireccion())) {
			campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
					.getDireccionBean().getTipoVia().getIdTipoVia();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_TIPO_VIA_MATE));
			} else {
				line.append(BasicText.changeSize(campo, TAM_TIPO_VIA_MATE));
			}

			// Quiza comprobar Validación MATEGE
			campo = utilesConversiones.ajustarCamposIne(detalleMatriculacion
					.getTramiteTraficoBean().getVehiculo().getDireccionBean()
					.getNombreVia());
			if (campo == null) {
				line.append(BasicText
						.changeSize("", TAM_DOMICILIO_TITULAR_MATE));
			} else {
				line.append(BasicText.changeSize(UtilesCadenaCaracteres
						.quitarCaracteresExtranios(campo),
						TAM_DOMICILIO_TITULAR_MATE));
			}

			campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
					.getDireccionBean().getNumero();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_NUM_DOMICILIO_MATE));
			} else {
				line
						.append(BasicText.changeSize(campo,
								TAM_NUM_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
					.getDireccionBean().getPuntoKilometrico();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_KM_DOMICILIO_MATE));
			} else {
				line.append(BasicText.changeSize(campo, TAM_KM_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
					.getDireccionBean().getHm();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_HEC_DOMICILIO_MATE));
			} else {
				line
						.append(BasicText.changeSize(campo,
								TAM_HEC_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
					.getDireccionBean().getBloque();
			if (campo == null) {
				line
						.append(BasicText.changeSize("",
								TAM_BLOQUE_DOMICILIO_MATE));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_BLOQUE_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
					.getDireccionBean().getPortal();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_LETRA_DOMICILIO_MATE));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_LETRA_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
					.getDireccionBean().getEscalera();
			if (campo == null) {
				line.append(BasicText.changeSize("",
						TAM_ESCALERA_DOMICILIO_MATE));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_ESCALERA_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
					.getDireccionBean().getPlanta();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_PISO_DOMICILIO_MATE));
			} else {
				line.append(BasicText
						.changeSize(campo, TAM_PISO_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
					.getDireccionBean().getPuerta();
			if (campo == null) {
				line
						.append(BasicText.changeSize("",
								TAM_PUERTA_DOMICILIO_MATE));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_PUERTA_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
					.getDireccionBean().getMunicipio().getProvincia()
					.getIdProvincia();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_PROVINCIA_VEHICULO));
			} else {
				line
						.append(BasicText.changeSize(campo,
								TAM_PROVINCIA_VEHICULO));
			}

			campo += detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
					.getDireccionBean().getMunicipio().getIdMunicipio();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_MUNICIPIO_VEHICULO));
			} else {
				line
						.append(BasicText.changeSize(campo,
								TAM_MUNICIPIO_VEHICULO));
			}

			campo = utilesConversiones.ajustarCamposIne(detalleMatriculacion
					.getTramiteTraficoBean().getVehiculo().getDireccionBean()
					.getPueblo());
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_PUEBLO_VEHICULO));
			} else {
				line.append(BasicText.changeSize(campo, TAM_PUEBLO_VEHICULO));
			}

			campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
					.getDireccionBean().getCodPostal();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_CP_VEHICULO));
			} else {
				line.append(BasicText.changeSize(campo, TAM_CP_VEHICULO, '0',
						BasicText.DETRAS));
			}
		} else {
			campo = detalleMatriculacion.getTitularBean().getPersona()
					.getDireccion().getTipoVia().getIdTipoVia();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_TIPO_VIA_MATE));
			} else {
				line.append(BasicText.changeSize(campo, TAM_TIPO_VIA_MATE));
			}

			campo = utilesConversiones.ajustarCamposIne(detalleMatriculacion
					.getTitularBean().getPersona().getDireccion()
					.getNombreVia());
			if (campo == null) {
				line.append(BasicText
						.changeSize("", TAM_DOMICILIO_TITULAR_MATE));
			} else {
				line.append(BasicText.changeSize(UtilesCadenaCaracteres
						.quitarCaracteresExtranios(campo),
						TAM_DOMICILIO_TITULAR_MATE));
			}

			campo = detalleMatriculacion.getTitularBean().getPersona()
					.getDireccion().getNumero();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_NUM_DOMICILIO_MATE));
			} else {
				line
						.append(BasicText.changeSize(campo,
								TAM_NUM_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getTitularBean().getPersona()
					.getDireccion().getPuntoKilometrico();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_KM_DOMICILIO_MATE));
			} else {
				line.append(BasicText.changeSize(campo, TAM_KM_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getTitularBean().getPersona()
					.getDireccion().getHm();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_HEC_DOMICILIO_MATE));
			} else {
				line
						.append(BasicText.changeSize(campo,
								TAM_HEC_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getTitularBean().getPersona()
					.getDireccion().getBloque();
			if (campo == null) {
				line
						.append(BasicText.changeSize("",
								TAM_BLOQUE_DOMICILIO_MATE));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_BLOQUE_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getTitularBean().getPersona()
					.getDireccion().getPortal();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_LETRA_DOMICILIO_MATE));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_LETRA_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getTitularBean().getPersona()
					.getDireccion().getEscalera();
			if (campo == null) {
				line.append(BasicText.changeSize("",
						TAM_ESCALERA_DOMICILIO_MATE));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_ESCALERA_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getTitularBean().getPersona()
					.getDireccion().getPlanta();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_PISO_DOMICILIO_MATE));
			} else {
				line.append(BasicText
						.changeSize(campo, TAM_PISO_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getTitularBean().getPersona()
					.getDireccion().getPuerta();
			if (campo == null) {
				line
						.append(BasicText.changeSize("",
								TAM_PUERTA_DOMICILIO_MATE));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_PUERTA_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getTitularBean().getPersona()
					.getDireccion().getMunicipio().getProvincia()
					.getIdProvincia();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_PROVINCIA_TITULAR));
			} else {
				line.append(BasicText.changeSize(campo, TAM_PROVINCIA_TITULAR));
			}

			campo += detalleMatriculacion.getTitularBean().getPersona()
					.getDireccion().getMunicipio().getIdMunicipio();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_MUNICIPIO_TITULAR));
			} else {
				line.append(BasicText.changeSize(campo, TAM_MUNICIPIO_TITULAR));
			}

			campo = utilesConversiones.ajustarCamposIne(detalleMatriculacion
					.getTitularBean().getPersona().getDireccion().getPueblo());
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_PUEBLO_TITULAR));
			} else {
				line.append(BasicText.changeSize(campo, TAM_PUEBLO_TITULAR));
			}

			campo = detalleMatriculacion.getTitularBean().getPersona()
					.getDireccion().getCodPostal();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_CP_TITULAR));
			} else {
				line.append(BasicText.changeSize(campo, TAM_CP_TITULAR, '0',
						BasicText.DETRAS));
			}
		}

		// Datos del titular
		campo = detalleMatriculacion.getTitularBean().getPersona().getNif();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_DNI_TITULAR));
		} else {
			line.append(BasicText.changeSize(campo, TAM_DNI_TITULAR));
		}

		try {
			if (detalleMatriculacion.getTitularBean().getPersona()
					.getFechaNacimientoBean() != null
					&& !detalleMatriculacion.getTitularBean().getPersona()
							.getFechaNacimientoBean().isfechaNula()) {
				campo = formato.format(detalleMatriculacion.getTitularBean()
						.getPersona().getFechaNacimientoBean().getTimestamp());
			} else {
				campo = null;
			}
		} catch (ParseException e) {
			campo = null;
		}
		if (campo == null) {
			line.append(BasicText.changeSize("",
					TAM_FECHA_NACIMIENTO_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo,
					TAM_FECHA_NACIMIENTO_ADQUIRENTE, '0', false));
		}

		campo = detalleMatriculacion.getTitularBean().getPersona().getSexo();
		if (campo == null || campo.compareTo(PERSONA_JURIDICA) == 0) {
			campo = detalleMatriculacion.getTitularBean().getPersona()
					.getApellido1RazonSocial();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_PRIMER_APELLIDO));
			} else {
				line.append(BasicText.changeSize(campo, TAM_PRIMER_APELLIDO));
			}
			line.append(BasicText.changeSize("", TAM_SEGUNDO_APELLIDO));
			line.append(BasicText.changeSize("", TAM_NOMBRE_TITULAR));
		} else {
			campo = detalleMatriculacion.getTitularBean().getPersona()
					.getApellido1RazonSocial();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_PRIMER_APELLIDO));
			} else {
				line.append(BasicText.changeSize(campo, TAM_PRIMER_APELLIDO));
			}

			campo = detalleMatriculacion.getTitularBean().getPersona()
					.getApellido2();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_SEGUNDO_APELLIDO));
			} else {
				line.append(BasicText.changeSize(campo, TAM_SEGUNDO_APELLIDO));
			}

			campo = detalleMatriculacion.getTitularBean().getPersona()
					.getNombre();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_NOMBRE_TITULAR));
			} else {
				line.append(BasicText.changeSize(campo, TAM_NOMBRE_TITULAR));
			}
		}
		campo = detalleMatriculacion.getTitularBean().getPersona().getSexo();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_SEXO_TITULAR));
		} else {
			line.append(BasicText.changeSize(campo, TAM_SEXO_TITULAR));
		}

		campo = null != detalleMatriculacion.getTitularBean()
				.getCambioDomicilio()
				&& detalleMatriculacion.getTitularBean().getCambioDomicilio()
						.equals("true") ? VALOR_S : null;
		if (campo == null) {
			line.append(BasicText.changeSize("N", TAM_CAMBIO_DOMICILIO));
		} else {
			line.append(BasicText.changeSize(campo, TAM_CAMBIO_DOMICILIO));
		}

		campo = detalleMatriculacion.getTitularBean().getPersona()
				.getDireccion().getTipoVia().getIdTipoVia();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_TIPO_VIA_MATE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_TIPO_VIA_MATE));
		}

		campo = utilesConversiones.ajustarCamposIne(detalleMatriculacion
				.getTitularBean().getPersona().getDireccion().getNombreVia());
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_DOMICILIO_TITULAR_MATE));
		} else {
			line.append(BasicText.changeSize(UtilesCadenaCaracteres
					.quitarCaracteresExtranios(campo),
					TAM_DOMICILIO_TITULAR_MATE));
		}

		campo = detalleMatriculacion.getTitularBean().getPersona()
				.getDireccion().getNumero();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_NUM_DOMICILIO_MATE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_NUM_DOMICILIO_MATE));
		}

		campo = detalleMatriculacion.getTitularBean().getPersona()
				.getDireccion().getPuntoKilometrico();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_KM_DOMICILIO_MATE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_KM_DOMICILIO_MATE));
		}

		campo = detalleMatriculacion.getTitularBean().getPersona()
				.getDireccion().getHm();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_HEC_DOMICILIO_MATE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_HEC_DOMICILIO_MATE));
		}

		campo = detalleMatriculacion.getTitularBean().getPersona()
				.getDireccion().getBloque();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_BLOQUE_DOMICILIO_MATE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_BLOQUE_DOMICILIO_MATE));
		}

		campo = detalleMatriculacion.getTitularBean().getPersona()
				.getDireccion().getPortal();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_LETRA_DOMICILIO_MATE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_LETRA_DOMICILIO_MATE));
		}

		campo = detalleMatriculacion.getTitularBean().getPersona()
				.getDireccion().getEscalera();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_ESCALERA_DOMICILIO_MATE));
		} else {
			line.append(BasicText
					.changeSize(campo, TAM_ESCALERA_DOMICILIO_MATE));
		}

		campo = detalleMatriculacion.getTitularBean().getPersona()
				.getDireccion().getPlanta();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_PISO_DOMICILIO_MATE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_PISO_DOMICILIO_MATE));
		}

		campo = detalleMatriculacion.getTitularBean().getPersona()
				.getDireccion().getPuerta();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_PUERTA_DOMICILIO_MATE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_PUERTA_DOMICILIO_MATE));
		}

		campo = detalleMatriculacion.getTitularBean().getPersona()
				.getDireccion().getMunicipio().getProvincia().getIdProvincia();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_PROVINCIA_TITULAR));
		} else {
			line.append(BasicText.changeSize(campo, TAM_PROVINCIA_TITULAR));
		}

		campo += detalleMatriculacion.getTitularBean().getPersona()
				.getDireccion().getMunicipio().getIdMunicipio();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_MUNICIPIO_TITULAR));
		} else {
			line.append(BasicText.changeSize(campo, TAM_MUNICIPIO_TITULAR));
		}

		campo = utilesConversiones.ajustarCamposIne(detalleMatriculacion
				.getTitularBean().getPersona().getDireccion().getPueblo());
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_PUEBLO_TITULAR));
		} else {
			line.append(BasicText.changeSize(campo, TAM_PUEBLO_TITULAR));
		}

		campo = detalleMatriculacion.getTitularBean().getPersona()
				.getDireccion().getCodPostal();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_CP_TITULAR));
		} else {
			line.append(BasicText.changeSize(campo, TAM_CP_TITULAR, '0',
					BasicText.DETRAS));
		}

		// Datos de vehículo
		try {
			if (detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
					.getFechaItv() != null
					&& !detalleMatriculacion.getTramiteTraficoBean()
							.getVehiculo().getFechaItv().isfechaNula()) {
				
				//Requisito Mate 2.5: la fecha de la ITV verificar si fue generada o si fue modificada
				UtilesTrafico utilesTrafico = new UtilesTrafico();
				int iguales = utilesFecha.compararFechas(detalleMatriculacion.getTramiteTraficoBean()
														.getVehiculo().getFechaItv(), utilesTrafico.calcularFechaITV(detalleMatriculacion.getTramiteTraficoBean().getFechaPresentacion(), 
																detalleMatriculacion.getTramiteTraficoBean().getVehiculo()));
				if(iguales == 1){
					campo = null;
				} else{
					campo = formato.format(detalleMatriculacion
							.getTramiteTraficoBean().getVehiculo().getFechaItv()
							.getTimestamp());
				}
			} else {
				campo = null;
			}
		} catch (ParseException e) {
			campo = null;
		}

		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_FECHA_ITV));
		} else {
			line.append(BasicText.changeSize(campo, TAM_FECHA_ITV, '0', false));
		}

		// Sólo para ciclomotores que tuvieran matrícula en un ayuntamiento,
		// sino USADO
		if (ciclomotor) {
			campo = null != detalleMatriculacion.getTramiteTraficoBean()
					.getVehiculo().getMatriAyuntamiento()
					&& detalleMatriculacion.getTramiteTraficoBean()
							.getVehiculo().getMatriAyuntamiento()
							.equals("true") ? VALOR_S : VALOR_USADO;
		} else {
			campo = null;
		}
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_MATRICULA_AYTO));
		} else {
			line.append(BasicText.changeSize(campo, TAM_MATRICULA_AYTO));
		}

		campo = null != detalleMatriculacion.getTramiteTraficoBean()
				.getVehiculo().getExcesoPeso()
				&& detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
						.getExcesoPeso().equals("true") ? VALOR_S : null;
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_EXCESO_PESO));
		} else {
			line.append(BasicText.changeSize(campo, TAM_EXCESO_PESO));
		}

		// Inicialmente la fecha de ultima transferencia la ponemos a blanco
		line.append(BasicText.changeSize("", TAM_FECHA_PRIMERA_MATR));

		try {
			if (detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
					.getFechaPrimMatri() != null
					&& !detalleMatriculacion.getTramiteTraficoBean()
							.getVehiculo().getFechaPrimMatri().isfechaNula()) {
				campo = formato.format(detalleMatriculacion
						.getTramiteTraficoBean().getVehiculo()
						.getFechaPrimMatri().getTimestamp());
			} else {
				campo = null;
			}
		} catch (ParseException e) {
			campo = null;
		}
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_FECHA_PRIMERA_MATR));
		} else {
			line.append(BasicText.changeSize(campo, TAM_FECHA_PRIMERA_MATR,
					'0', false));
		}

		campo = null != detalleMatriculacion.getRepresentanteTitularBean()
				.getIdMotivoTutela() ? VALOR_S : null;
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_TUTELA_MATRICULACION));
		} else {
			line.append(BasicText.changeSize(campo, TAM_TUTELA_MATRICULACION));
		}

		campo = null != detalleMatriculacion.getTramiteTraficoBean()
				.getVehiculo().getDiplomatico()
				&& detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
						.getDiplomatico().equals("true") ? VALOR_S : null;

		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_MATRICULA_DIPL));
		} else {
			line.append(BasicText.changeSize(campo, TAM_MATRICULA_DIPL));
		}

		// Este apartado siempre irá a "E"
		campo = null != detalleMatriculacion.getTramiteTraficoBean().getIedtm()
				&& detalleMatriculacion.getTramiteTraficoBean().getIedtm()
 						.equals("E") ? "E" : null;
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_TIPO_LIMITACION));
		} else {
			line.append(BasicText.changeSize(campo, TAM_TIPO_LIMITACION));
		}

		try {
			if (detalleMatriculacion.getTramiteTraficoBean().getFechaIedtm() != null
					&& !detalleMatriculacion.getTramiteTraficoBean()
							.getFechaIedtm().isfechaNula()) {
				campo = formato
						.format(detalleMatriculacion.getTramiteTraficoBean()
								.getFechaIedtm().getTimestamp());
			} else {
				campo = null;
			}
		} catch (ParseException e) {
			campo = null;
		}
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_FECHA_LIMITACION));
		} else {
			line.append(BasicText.changeSize(campo, TAM_FECHA_LIMITACION, '0',
					false));
		}

		campo = null != detalleMatriculacion.getTramiteTraficoBean()
				.getNumRegIedtm()
				&& !"-1".equals(detalleMatriculacion.getTramiteTraficoBean()
						.getNumRegIedtm().getValorEnum()) ? detalleMatriculacion
				.getTramiteTraficoBean().getNumRegIedtm().getValorEnum()
				: null;
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_NUM_REG));
		} else {
			line.append(BasicText.changeSize(campo, TAM_NUM_REG));
		}

		// Se cubrirá con el literal "EXENTO PR IEDMT"
		// campo =
		// detalleMatriculacion.getTramiteTraficoBean().getFinancieraIedtm();
		campo = null != detalleMatriculacion.getTramiteTraficoBean().getIedtm()
				&& detalleMatriculacion.getTramiteTraficoBean().getIedtm()
						.equals("E") ? "EXENTO PR IEDMT" : "";
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_FINANCIERA_LIMITACION));
		} else {
			line.append(BasicText.changeSize(campo, TAM_FINANCIERA_LIMITACION));
		}

		// //Datos de carga para MATE4.3
		// campo = null;
		// if (campo==null){
		// line.append(BasicText.changeSize("", TAM_TIPO_CARGA));
		// } else {
		// line.append(BasicText.changeSize(campo, TAM_TIPO_CARGA));
		// }
		//
		// campo = null;
		// if (campo==null){
		// line.append(BasicText.changeSize("", TAM_FECHA_CARGA));
		// } else {
		// line.append(BasicText.changeSize(campo, TAM_FECHA_CARGA, '0',
		// false));
		// }
		//		
		// campo = null;
		// if (campo==null){
		// line.append(BasicText.changeSize("", TAM_REGISTRO_CARGA));
		// } else {
		// line.append(BasicText.changeSize(campo, TAM_REGISTRO_CARGA));
		// }
		//
		// campo = null;
		// if (campo==null){
		// line.append(BasicText.changeSize("", TAM_FINANCIERA_CARGA));
		// } else {
		// line.append(BasicText.changeSize(campo, TAM_FINANCIERA_CARGA));
		// }

		// Datos Técnicos //Aquí va el dato nuevo
		campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
				.getTipoItv();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_TIPO_ITV));
		} else {
			line.append(BasicText.changeSize(campo, TAM_TIPO_ITV));
		}

		campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
				.getVariante();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_VARIANTE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_VARIANTE));
		}

		campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
				.getVersion();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_VERSION));
		} else {
			line.append(BasicText.changeSize(campo, TAM_VERSION));
		}

		campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
				.getNumHomologacion();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_NUM_HOMOLOGACION));
		} else {
			line.append(BasicText.changeSize(campo, TAM_NUM_HOMOLOGACION));
		}
		// Corresponde con el campo "MTMA" de "MTMA/MMA"
		campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
				.getMtmaItv();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_MASA_MAX, '0'));
		} else {
			line.append(BasicText.changeSize(campo, TAM_MASA_MAX, '0', false));
		}

		//String taraString = detalleMatriculacion.getTramiteTraficoBean()
				//.getVehiculo().getTara();
		if(detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getMom() != null){
			campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo().
				getMom().toString();
		}
		//CalculaMMAServicio.calcular(taraString, tipoVehiculo);
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_MASA_SERVICIO, '0'));
		} else {
			line.append(BasicText.changeSize(campo, TAM_MASA_SERVICIO, '0',
					false));
		}

		campo = utiles.BigDecimalToStringDosDecimales(detalleMatriculacion
				.getTramiteTraficoBean().getVehiculo().getPotenciaNeta(),
				ConstantesPDF._5);
		if (remolque) {
			line.append(BasicText.changeSize("", TAM_POTENCIA_MAX));
		} else if (campo == null) {
			line.append(BasicText.changeSize("", TAM_POTENCIA_MAX, '0'));
		} else {
			line.append(BasicText.changeSize(campo, TAM_POTENCIA_MAX, '0',
					false));
		}

		VehiculoBean vehiculo = detalleMatriculacion.getTramiteTraficoBean()
				.getVehiculo();
		// Sólo es obligatorio para las motocicletas
		// BigDecimal potencia = null != vehiculo.getPotenciaNeta() ? vehiculo
		// .getPotenciaNeta() : null;
		// BigDecimal peso = null != vehiculo.getPesoMma() ? new BigDecimal(
		// vehiculo.getPesoMma()) : null;
		// BigDecimal relacion = null != peso && null != potencia ? utiles
		// .divisionBigDecimals(potencia, peso) : null;

		BigDecimal relacion = vehiculo.getPotenciaPeso();
		campo = utiles.BigDecimalToStringDosDecimales(relacion,
				ConstantesPDF._7);

		if (remolque
				|| (tipoVehiculo!=null && !tipoVehiculo.equals("50") && !tipoVehiculo.equals("51")
						&& !tipoVehiculo.equals("52")
						&& !tipoVehiculo.equals("53") && !tipoVehiculo
						.equals("54"))) {
			line.append(BasicText.changeSize("", TAM_RELACION_POTENCIA_PESO));
		} else if (campo == null) {
			line.append(BasicText.changeSize("", TAM_RELACION_POTENCIA_PESO,
					'0'));
		} else {
			line.append(BasicText.changeSize(campo, TAM_RELACION_POTENCIA_PESO,
					'0', false));
		}

		campo = null != detalleMatriculacion.getTramiteTraficoBean()
				.getVehiculo().getNumPlazasPie() ? detalleMatriculacion
				.getTramiteTraficoBean().getVehiculo().getNumPlazasPie()
				.toString() : null;
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_PLAZAS_DE_PIE, '0'));
		} else {
			line.append(BasicText.changeSize(campo, TAM_PLAZAS_DE_PIE, '0',
					false));
		}

		// Inicio Mate2.5
		// Fabricante
		campo = null != vehiculo.getFabricante() ? vehiculo.getFabricante()
				: ConstantesTrafico.ND;
		if (campo == null) {
			line.append(BasicText.changeSize("",
					ConstantesTrafico.TAM_FABRICANTE));
		} else {
			line.append(BasicText.changeSize(campo,
					ConstantesTrafico.TAM_FABRICANTE));
		}
		// Homologación UE
		campo = null != vehiculo.getHomologacionBean() ? vehiculo.getHomologacionBean().getIdHomologacion():null;
		if (campo == null) {
			line.append(BasicText.changeSize("",
					ConstantesTrafico.TAM_CATEGORIA_EU));
		} else {
			line.append(BasicText.changeSize(campo,
					ConstantesTrafico.TAM_CATEGORIA_EU));
		}
		// Carrocería
		campo = null != vehiculo.getCarroceriaBean() ? vehiculo
				.getCarroceriaBean().getIdCarroceria()
				: null;
		if (campo == null) {
			line.append(BasicText.changeSize("",
					ConstantesTrafico.TAM_CARROCERIA));
		} else {
			line.append(BasicText.changeSize(campo,
					ConstantesTrafico.TAM_CARROCERIA));
		}

		// Consumo: Obligatorio en eléctrico monofuel
		if(vehiculo!=null && vehiculo.getCategoriaElectricaBean()!=null && vehiculo.getCategoriaElectricaBean().getIdCategoriaElectrica()!=null &&
			vehiculo.getAlimentacionBean()!=null && vehiculo.getAlimentacionBean().getIdAlimentacion()!=null && 
			vehiculo.getAlimentacionBean().getIdAlimentacion().equals(Alimentacion.MONOFUEL.getValorEnum())){
			String property = gestorPropiedades.valorPropertie(ConstantesPDF.PROPERTY_COMPLETA_CEROS_ELECTRICOS);
			if (detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getConsumo()== null){
				campo = "0000";
			} else if (property != null && property.equals(ConstantesPDF.VALOR_CIERTO_PROPERTY_COMPLETA_CEROS_ELECTRICOS)){
				DecimalFormat formato4 = new DecimalFormat("#0000");
				if (detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getConsumo()!=null){
					campo = formato4.format(detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getConsumo());
				}
			} else {
				campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getConsumo().toString();
			}
		}else{
			campo = null;
		}

		if (campo == null) {
			line.append(BasicText.changeSize("",
							ConstantesTrafico.TAM_CONSUMO));
		} else {
			line.append(BasicText.changeSize(campo,
					ConstantesTrafico.TAM_CONSUMO,'0',false));
		}
		// Distancia ejes
		campo = null != vehiculo.getDistanciaEntreEjes() ? vehiculo.getDistanciaEntreEjes().toString():null;
		if (campo == null) {
			line.append(BasicText.changeSize("",
					ConstantesTrafico.TAM_DIST_EJES));
		} else {
			line.append(BasicText.changeSize(campo,
					ConstantesTrafico.TAM_DIST_EJES, '0', false));
		}
		// Via Anterior
		campo = null != vehiculo.getViaAnterior() ? vehiculo.getViaAnterior().toString():null;
		if (campo == null) {
			line
					.append(BasicText.changeSize("",
							ConstantesTrafico.TAM_VIA_ANT));
		} else {
			line.append(BasicText.changeSize(campo,
					ConstantesTrafico.TAM_VIA_ANT, '0',false));
		}
		// Via Posterior
		campo = null != vehiculo.getViaPosterior() ? vehiculo.getViaPosterior().toString():null;
		if (campo == null) {
			line.append(BasicText
					.changeSize("", ConstantesTrafico.TAM_VIA_POST));
		} else {
			line.append(BasicText.changeSize(campo,
					ConstantesTrafico.TAM_VIA_POST, '0',false));
		}
		// Alimentación
		campo = null != vehiculo.getAlimentacionBean() ? vehiculo.getAlimentacionBean().getIdAlimentacion():null;
		if (campo == null) {
			line.append(BasicText.changeSize("",
					ConstantesTrafico.TAM_TIPO_ALIMENTACION));
		} else {
			line.append(BasicText.changeSize(campo,
					ConstantesTrafico.TAM_TIPO_ALIMENTACION));
		}
		campo = null != vehiculo.getEcoInnovacion() ? vehiculo.getEcoInnovacion() : "";
		// Eco innova
		if (campo == null) {
			line.append(BasicText.changeSize("",
					ConstantesTrafico.TAM_ECO_INNOVA));
		} else {
			line.append(BasicText.changeSize(campo,
					ConstantesTrafico.TAM_ECO_INNOVA));
		}
		campo = null != vehiculo.getReduccionEco() ? vehiculo.getReduccionEco().toString() : "";
		// Reducción EC
		if (campo == null) {
			line.append(BasicText.changeSize("",
					ConstantesTrafico.TAM_REDUCCION_EC));
		} else {
			line.append(BasicText.changeSize(campo,
					ConstantesTrafico.TAM_REDUCCION_EC));
		}
		campo = null != vehiculo.getCodigoEco() ? vehiculo.getCodigoEco() : "";
		// Código ECO
		if (campo == null) {
			line.append(BasicText.changeSize("",
					ConstantesTrafico.TAM_CODIGO_ECO));
		} else {
			line.append(BasicText.changeSize(campo,
					ConstantesTrafico.TAM_CODIGO_ECO));
		}
		// Nivel de Emisiones
		campo = null != vehiculo.getNivelEmisiones() ? vehiculo.getNivelEmisiones().toString() : "";
		if (campo == null) {
			line.append(BasicText.changeSize("",
					ConstantesTrafico.TAM_NIVEL_EMISIONES));
		} else if (campo!=null
				&& detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getTipoTarjetaItvBean()!=null
				&& detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getTipoTarjetaItvBean().getIdTipoTarjetaItv()!=null
				&& detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getTipoTarjetaItvBean().getIdTipoTarjetaItv().equals("D")) {
			line.append(BasicText.changeSize("1", ConstantesTrafico.TAM_NIVEL_EMISIONES, '0', false));
		} else {
			line.append(BasicText.changeSize(campo,
					ConstantesTrafico.TAM_NIVEL_EMISIONES));
		}
		// Subasta
		campo = null != vehiculo.getSubasta() && vehiculo.getSubasta() == true ? VALOR_S : VALOR_N;
		if (campo == null) {
			line
					.append(BasicText.changeSize("",
							ConstantesTrafico.TAM_SUBASTA));
		} else {
			line.append(BasicText.changeSize(campo,
					ConstantesTrafico.TAM_SUBASTA));
		}
		// Importado
		// campo = null != detalleMatriculacion.getTramiteTraficoBean()
		// .getRenting()
		// && detalleMatriculacion.getTramiteTraficoBean().getRenting()
		// .equals("true") ? VALOR_S : VALOR_N;
		campo = null != vehiculo.getImportado()
				&& vehiculo.getImportado() == true ? VALOR_S : VALOR_N;
		if (null == campo) {
			line.append(BasicText.changeSize("",
					ConstantesTrafico.TAM_IMPORTADO));
		} else {
			line.append(BasicText.changeSize(campo,
					ConstantesTrafico.TAM_IMPORTADO));
		}
		// País Importación
		campo = null != vehiculo.getPaisImportacionBean()
				? vehiculo.getPaisImportacionBean()
				.getIdPaisImportacion() : "";
		if (null == campo) {
			line.append(BasicText.changeSize("",
					ConstantesTrafico.TAM_PAIS_IMPORT));
		} else {
			line.append(BasicText.changeSize(campo,
					ConstantesTrafico.TAM_PAIS_IMPORT));
		}
		// Fin Mate2.5
		
		// Nivel CO2
		campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
				.getCo2();
		/*if (combustibleSinCo2) {
			line.append(BasicText.changeSize("", TAM_CO2, '0', true));
		} else*/ if (campo == null) {
			line.append(BasicText.changeSize("", TAM_CO2, '0', true));
		} else {
			campo = utiles.cambiarFormatoCO2(campo, true);
			line.append(campo);
			// Se cambió la función a usar
//			boolean detras = true;
//			if(campo.contains(".")){
//				campo = campo.replace(".", "");
//				System.out.println("CO2 Nube = ["+campo+"]");
//				if(campo.length()<TAM_CO2){
//					detras = false;
//				}
//			}else{
//				campo = utiles.cambiarDecimalFormatoMancha(campo, true);
//				System.out.println("CO2 Nube = ["+campo+"]");
//				campo = campo.replace(".", "");
//				System.out.println("CO2 Nube = ["+campo+"]");
//				if(campo.length()<TAM_CO2){
//					detras = false;
//				}
//			}
		}
		// Tipo Industria
		campo = null != detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getCriterioConstruccionBean()?
				detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getCriterioConstruccionBean().getIdCriterioConstruccion():null;
		if (campo == null) {
			line.append(BasicText
					.changeSize("", TAM_CLASIFICACION_CONSTRUCCION));
		} else {
			line.append(BasicText.changeSize(campo,
					TAM_CLASIFICACION_CONSTRUCCION));
		}

		campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
				.getCriterioUtilizacionBean().getIdCriterioUtilizacion();
		if (campo == null) {
			line
					.append(BasicText.changeSize("",
							TAM_CLASIFICACION_UTILIZACION));
		} else {
			line.append(BasicText.changeSize(campo,
					TAM_CLASIFICACION_UTILIZACION));
		}
		// Datos de Impuestos
		// Se coge siempre el CEM que venga en pantalla.
		/*
		 * String tipoMatr = UtilesVistaTrafico
		 * .queTipoVehiculo(detalleMatriculacion.getTramiteTraficoBean()
		 * .getVehiculo().getTipoVehiculoBean().getTipoVehiculo()); if
		 * (tipoMatr.equals(ConstantesPDF.TIPO_MATRICULA_REMOLQUE)) { campo =
		 * "00000000"; } else {
		 */
		campo = detalleMatriculacion.getTramiteTraficoBean().getCemIedtm();
		// }

		if (campo == null || campo.length() == 0) {
			line.append(BasicText.changeSize("", TAM_CEM, '0', false));
		} else {
			line.append(BasicText.changeSize(campo, TAM_CEM));
		}

		//TODO MPC Cambio IVTM
		if (null != detalleMatriculacion.getTramiteTraficoBean()) {
			IVTMModeloMatriculacionInterface modeloIVTM = new IVTMModeloMatriculacionImpl();
			IvtmMatriculacionDto ivtmMatriculacion =  modeloIVTM.buscarIvtmNumExp(detalleMatriculacion.getTramiteTraficoBean().getNumExpediente());
			if (ivtmMatriculacion != null) {
				campo = ivtmMatriculacion.getNrc();
			} else {
				campo = null;
			}
		} else {
			campo = null;
		}
		if (campo == null || campo.length() == 0) {
			line.append(BasicText.changeSize("", TAM_CODIGO_CEMU));
		} else {
			line.append(BasicText.changeSize(campo, TAM_CODIGO_CEMU));
		}

		campo = detalleMatriculacion.getTramiteTraficoBean().getCema();
		if (campo == null || campo.length() == 0) {
			line.append(BasicText.changeSize("", TAM_CEM));
		} else {
			line.append(BasicText.changeSize(campo, TAM_CEM));
		}

		// Para ver si hay renting
		campo = null != detalleMatriculacion.getTramiteTraficoBean()
				.getRenting()
				&& detalleMatriculacion.getTramiteTraficoBean().getRenting()
						.equals("true") ? VALOR_S : VALOR_N;
		if (null == campo) {
			line.append(BasicText.changeSize("", TAM_RENTING));
		} else {
			line.append(BasicText.changeSize(campo, TAM_RENTING));
		}

		// if (null != detalleMatriculacion.getTipoGravamen()) {
		// line.append(BasicText.changeSize(VALOR_S, TAM_CARGAS));
		// } else {
		// line.append(BasicText.changeSize(VALOR_N, TAM_CARGAS));
		// }

		// Los kilometros a los que se pasa la ITV los mandamos a ceros
		line.append(BasicText.changeSize("", TAM_KM_ITV, '0', false));
		campo = null != detalleMatriculacion.getTramiteTraficoBean()
				.getVehiculo().getFechaPrimMatri()
				&& !detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
						.getFechaPrimMatri().isfechaNula() ? VALOR_USADO
				: VALOR_NUEVO;
		if (campo == null) {
			line.append(BasicText.changeSize(VALOR_NUEVO, TAM_VEHICULO_USADO));
		} else {
			line.append(BasicText.changeSize(campo, TAM_VEHICULO_USADO));
		}

		// La hora a la que se pasa la ITV los mandamos a ceros
		line.append(BasicText.changeSize("", TAM_HORA_ITV, '0', false));
		// Arriba

		// Datos de Autónomo
		campo = null != detalleMatriculacion.getTitularBean().getAutonomo()
				&& detalleMatriculacion.getTitularBean().getAutonomo().equals(
						"true") ? VALOR_S : VALOR_N;
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_FLAG_IAE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_FLAG_IAE));
		}

		campo = detalleMatriculacion.getTitularBean().getCodigoIAE();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_CODIGO_IAE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_CODIGO_IAE));
		}

		// Datos de Conductor habitual
		campo = null != detalleMatriculacion.getConductorHabitualBean()
				.getPersona().getNif() ? VALOR_S : VALOR_N;
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_FLAG_CONDUCTOR));
		} else {
			line.append(BasicText.changeSize(campo, TAM_FLAG_CONDUCTOR));
		}

		// Datos de Tutor
		if (null != detalleMatriculacion.getRepresentanteTitularBean()
				.getFechaInicio()) {
			campo = detalleMatriculacion.getRepresentanteTitularBean()
					.getPersona().getNif();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_DNI_REPRESENTANTE));
			} else {
				line.append(BasicText.changeSize(campo, TAM_DNI_REPRESENTANTE));
			}

			try {
				if (detalleMatriculacion.getRepresentanteTitularBean()
						.getFechaInicio() != null
						&& !detalleMatriculacion.getRepresentanteTitularBean()
								.getFechaInicio().isfechaNula()) {
					campo = formato.format(detalleMatriculacion
							.getRepresentanteTitularBean().getFechaInicio()
							.getTimestamp());
				} else {
					campo = null;
				}
			} catch (ParseException e) {
				campo = null;
			}
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_FECHA_INICIO_TUTELA));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_FECHA_INICIO_TUTELA, '0', false));
			}

			campo = null;
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_TRAMITE_TUTELA));
			} else {
				line.append(BasicText.changeSize(campo, TAM_TRAMITE_TUTELA));
			}

			campo = null != detalleMatriculacion.getRepresentanteTitularBean()
					.getIdMotivoTutela() ? detalleMatriculacion
					.getRepresentanteTitularBean().getIdMotivoTutela()
					.getValorEnum() : null;
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_TIPO_TUTELA));
			} else {
				line.append(BasicText.changeSize(campo, TAM_TIPO_TUTELA));
			}

		} else {
			line.append(BasicText.changeSize("", TAM_DNI_REPRESENTANTE));
			line.append(BasicText.changeSize("", TAM_FECHA_INICIO_TUTELA));
			line.append(BasicText.changeSize("", TAM_TRAMITE_TUTELA));
			line.append(BasicText.changeSize("", TAM_TIPO_TUTELA));
		}
		
		// Tipo de Matrícula
		if(detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getCriterioConstruccionBean() != null &&
		detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getCriterioConstruccionBean().getIdCriterioConstruccion() != null){
		campo = UtilesVistaTrafico.getInstance().queTipoMatricula(detalleMatriculacion
				.getTramiteTraficoBean().getVehiculo()
				.getCriterioConstruccionBean().getIdCriterioConstruccion());
		}
		else{
			campo = null;
		}
		
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_TIPO_MATRICULA));
		} else {
			line.append(BasicText.changeSize(campo, TAM_TIPO_MATRICULA));
		}
		if(log.isDebugEnabled()) {
			log.debug("Nube de puntos: "+line.toString());
		}
		return line.toString();
	}
	
	/**
	 * Nube de matriculación PDF con NIVE
	 * */
	
	protected String obtenerNubeMatriculacionNive(
			TramiteTraficoMatriculacionBean detalleMatriculacion) {
		SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
		StringBuffer line = new StringBuffer();
		String campo = "";
		String tipoVehiculo = null;
				
		if (detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getTipoVehiculoBean() != null) {
			tipoVehiculo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getTipoVehiculoBean().getTipoVehiculo();
		}

		boolean ciclomotor = null != tipoVehiculo && ConstantesPDF.TIPO_MATRICULA_CICLOMOTOR.equals(UtilesVistaTrafico.getInstance().queTipoVehiculo(tipoVehiculo));
		boolean electrico = null != detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getCarburanteBean()
				&& Combustible.Electrico.getValorEnum().equals(detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getCarburanteBean().getDescripcion());
		boolean remolque = ConstantesPDF.TIPO_MATRICULA_REMOLQUE.equals(UtilesVistaTrafico.getInstance().queTipoVehiculo(tipoVehiculo));

		if (null != detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
				.getCarburanteBean()) {
			electrico = Combustible.Electrico.getValorEnum().equals(detalleMatriculacion
					.getTramiteTraficoBean().getVehiculo().getCarburanteBean()
					.getDescripcion());
		}
		// Comenzamos a meter los campos de las etiquetas en orden para generar
		// la mancha
		campo = ConstantesPDF.VERSION_MATRICULACION;
		line.append(campo);
		campo = ConstantesPDF.PROGRAMA_MATRICULACION_NIVE;
		line.append(campo);

		campo = detalleMatriculacion.getTramiteTraficoBean().getTasa()
				.getCodigoTasa();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_TASA));
		} else if (campo.equals("EXENTO")) {
			line.append(BasicText.changeSize(campo, TAM_TASA));
		} else {
			line.append(BasicText.changeSize(campo, TAM_TASA, '0', false));
		}

		// En la definición la indica como fecha de Matriculación, pero a los
		// efectos tiene que ser la misma.
		try {
			if (detalleMatriculacion.getTramiteTraficoBean()
					.getFechaPresentacion() != null
					&& !detalleMatriculacion.getTramiteTraficoBean()
							.getFechaPresentacion().isfechaNula()) {
				campo = formato.format(detalleMatriculacion
						.getTramiteTraficoBean().getFechaPresentacion()
						.getTimestamp());
			} else {
				campo = null;
			}
		} catch (ParseException e) {
			campo = null;
		}
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_FECHA_MATRICULACION));
		} else {
			line.append(BasicText.changeSize(campo, TAM_FECHA_MATRICULACION,
					'0', false));
		}
		//Servicio
		campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getServicioTraficoBean().getIdServicio();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_SERVICIO_MATE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_SERVICIO_MATE));
		}
		//TipoVehiculoDGT
		if (detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getTipoVehiculoBean() != null) {
			campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getTipoVehiculoBean().getTipoVehiculo();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_TIPO_VEHICULO));
			} else {
				line.append(BasicText.changeSize(campo, TAM_TIPO_VEHICULO));
			}
		} else {
			line.append(BasicText.changeSize("", TAM_TIPO_VEHICULO));
		}

		// Domicilio del vehiculo

		// Si el vehículo tiene dirección propia se la ponemos, si no le ponemos
		// la del titular
		if ((null != detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
				.getDireccionBean())
				&& (null != detalleMatriculacion.getTramiteTraficoBean()
						.getVehiculo().getDireccionBean().getIdDireccion())) {
			campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
					.getDireccionBean().getTipoVia().getIdTipoVia();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_TIPO_VIA_MATE));
			} else {
				line.append(BasicText.changeSize(campo, TAM_TIPO_VIA_MATE));
			}

			// Quiza comprobar Validación MATEGE
			campo = utilesConversiones.ajustarCamposIne(detalleMatriculacion
					.getTramiteTraficoBean().getVehiculo().getDireccionBean()
					.getNombreVia());
			if (campo == null) {
				line.append(BasicText
						.changeSize("", TAM_DOMICILIO_TITULAR_MATE));
			} else {
				line.append(BasicText.changeSize(UtilesCadenaCaracteres
						.quitarCaracteresExtranios(campo),
						TAM_DOMICILIO_TITULAR_MATE));
			}

			campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
					.getDireccionBean().getNumero();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_NUM_DOMICILIO_MATE));
			} else {
				line
						.append(BasicText.changeSize(campo,
								TAM_NUM_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
					.getDireccionBean().getPuntoKilometrico();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_KM_DOMICILIO_MATE));
			} else {
				line.append(BasicText.changeSize(campo, TAM_KM_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
					.getDireccionBean().getHm();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_HEC_DOMICILIO_MATE));
			} else {
				line
						.append(BasicText.changeSize(campo,
								TAM_HEC_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
					.getDireccionBean().getBloque();
			if (campo == null) {
				line
						.append(BasicText.changeSize("",
								TAM_BLOQUE_DOMICILIO_MATE));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_BLOQUE_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
					.getDireccionBean().getPortal();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_LETRA_DOMICILIO_MATE));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_LETRA_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
					.getDireccionBean().getEscalera();
			if (campo == null) {
				line.append(BasicText.changeSize("",
						TAM_ESCALERA_DOMICILIO_MATE));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_ESCALERA_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
					.getDireccionBean().getPlanta();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_PISO_DOMICILIO_MATE));
			} else {
				line.append(BasicText
						.changeSize(campo, TAM_PISO_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
					.getDireccionBean().getPuerta();
			if (campo == null) {
				line
						.append(BasicText.changeSize("",
								TAM_PUERTA_DOMICILIO_MATE));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_PUERTA_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
					.getDireccionBean().getMunicipio().getProvincia()
					.getIdProvincia();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_PROVINCIA_VEHICULO));
			} else {
				line
						.append(BasicText.changeSize(campo,
								TAM_PROVINCIA_VEHICULO));
			}

			campo += detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
					.getDireccionBean().getMunicipio().getIdMunicipio();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_MUNICIPIO_VEHICULO));
			} else {
				line
						.append(BasicText.changeSize(campo,
								TAM_MUNICIPIO_VEHICULO));
			}

			campo = utilesConversiones.ajustarCamposIne(detalleMatriculacion
					.getTramiteTraficoBean().getVehiculo().getDireccionBean()
					.getPueblo());
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_PUEBLO_VEHICULO));
			} else {
				line.append(BasicText.changeSize(campo, TAM_PUEBLO_VEHICULO));
			}

			campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
					.getDireccionBean().getCodPostal();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_CP_VEHICULO));
			} else {
				line.append(BasicText.changeSize(campo, TAM_CP_VEHICULO, '0',
						BasicText.DETRAS));
			}
		} else {
			campo = detalleMatriculacion.getTitularBean().getPersona()
					.getDireccion().getTipoVia().getIdTipoVia();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_TIPO_VIA_MATE));
			} else {
				line.append(BasicText.changeSize(campo, TAM_TIPO_VIA_MATE));
			}

			campo = utilesConversiones.ajustarCamposIne(detalleMatriculacion
					.getTitularBean().getPersona().getDireccion()
					.getNombreVia());
			if (campo == null) {
				line.append(BasicText
						.changeSize("", TAM_DOMICILIO_TITULAR_MATE));
			} else {
				line.append(BasicText.changeSize(UtilesCadenaCaracteres
						.quitarCaracteresExtranios(campo),
						TAM_DOMICILIO_TITULAR_MATE));
			}

			campo = detalleMatriculacion.getTitularBean().getPersona()
					.getDireccion().getNumero();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_NUM_DOMICILIO_MATE));
			} else {
				line
						.append(BasicText.changeSize(campo,
								TAM_NUM_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getTitularBean().getPersona()
					.getDireccion().getPuntoKilometrico();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_KM_DOMICILIO_MATE));
			} else {
				line.append(BasicText.changeSize(campo, TAM_KM_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getTitularBean().getPersona()
					.getDireccion().getHm();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_HEC_DOMICILIO_MATE));
			} else {
				line
						.append(BasicText.changeSize(campo,
								TAM_HEC_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getTitularBean().getPersona()
					.getDireccion().getBloque();
			if (campo == null) {
				line
						.append(BasicText.changeSize("",
								TAM_BLOQUE_DOMICILIO_MATE));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_BLOQUE_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getTitularBean().getPersona()
					.getDireccion().getPortal();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_LETRA_DOMICILIO_MATE));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_LETRA_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getTitularBean().getPersona()
					.getDireccion().getEscalera();
			if (campo == null) {
				line.append(BasicText.changeSize("",
						TAM_ESCALERA_DOMICILIO_MATE));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_ESCALERA_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getTitularBean().getPersona()
					.getDireccion().getPlanta();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_PISO_DOMICILIO_MATE));
			} else {
				line.append(BasicText
						.changeSize(campo, TAM_PISO_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getTitularBean().getPersona()
					.getDireccion().getPuerta();
			if (campo == null) {
				line
						.append(BasicText.changeSize("",
								TAM_PUERTA_DOMICILIO_MATE));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_PUERTA_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getTitularBean().getPersona()
					.getDireccion().getMunicipio().getProvincia()
					.getIdProvincia();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_PROVINCIA_TITULAR));
			} else {
				line.append(BasicText.changeSize(campo, TAM_PROVINCIA_TITULAR));
			}

			campo += detalleMatriculacion.getTitularBean().getPersona()
					.getDireccion().getMunicipio().getIdMunicipio();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_MUNICIPIO_TITULAR));
			} else {
				line.append(BasicText.changeSize(campo, TAM_MUNICIPIO_TITULAR));
			}

			campo = utilesConversiones.ajustarCamposIne(detalleMatriculacion
					.getTitularBean().getPersona().getDireccion().getPueblo());
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_PUEBLO_TITULAR));
			} else {
				line.append(BasicText.changeSize(campo, TAM_PUEBLO_TITULAR));
			}

			campo = detalleMatriculacion.getTitularBean().getPersona()
					.getDireccion().getCodPostal();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_CP_TITULAR));
			} else {
				line.append(BasicText.changeSize(campo, TAM_CP_TITULAR, '0',
						BasicText.DETRAS));
			}
		}

		// Datos del titular
		campo = detalleMatriculacion.getTitularBean().getPersona().getNif();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_DNI_TITULAR));
		} else {
			line.append(BasicText.changeSize(campo, TAM_DNI_TITULAR));
		}

		try {
			if (detalleMatriculacion.getTitularBean().getPersona()
					.getFechaNacimientoBean() != null
					&& !detalleMatriculacion.getTitularBean().getPersona()
							.getFechaNacimientoBean().isfechaNula()) {
				campo = formato.format(detalleMatriculacion.getTitularBean()
						.getPersona().getFechaNacimientoBean().getTimestamp());
			} else {
				campo = null;
			}
		} catch (ParseException e) {
			campo = null;
		}
		if (campo == null) {
			line.append(BasicText.changeSize("",
					TAM_FECHA_NACIMIENTO_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo,
					TAM_FECHA_NACIMIENTO_ADQUIRENTE, '0', false));
		}

		campo = detalleMatriculacion.getTitularBean().getPersona().getSexo();
		if (campo == null || campo.compareTo(PERSONA_JURIDICA) == 0) {
			campo = detalleMatriculacion.getTitularBean().getPersona()
					.getApellido1RazonSocial();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_PRIMER_APELLIDO));
			} else {
				line.append(BasicText.changeSize(campo, TAM_PRIMER_APELLIDO));
			}
			line.append(BasicText.changeSize("", TAM_SEGUNDO_APELLIDO));
			line.append(BasicText.changeSize("", TAM_NOMBRE_TITULAR));
		} else {
			campo = detalleMatriculacion.getTitularBean().getPersona()
					.getApellido1RazonSocial();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_PRIMER_APELLIDO));
			} else {
				line.append(BasicText.changeSize(campo, TAM_PRIMER_APELLIDO));
			}

			campo = detalleMatriculacion.getTitularBean().getPersona()
					.getApellido2();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_SEGUNDO_APELLIDO));
			} else {
				line.append(BasicText.changeSize(campo, TAM_SEGUNDO_APELLIDO));
			}

			campo = detalleMatriculacion.getTitularBean().getPersona()
					.getNombre();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_NOMBRE_TITULAR));
			} else {
				line.append(BasicText.changeSize(campo, TAM_NOMBRE_TITULAR));
			}
		}
		campo = detalleMatriculacion.getTitularBean().getPersona().getSexo();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_SEXO_TITULAR));
		} else {
			line.append(BasicText.changeSize(campo, TAM_SEXO_TITULAR));
		}

		campo = null != detalleMatriculacion.getTitularBean()
				.getCambioDomicilio()
				&& detalleMatriculacion.getTitularBean().getCambioDomicilio()
						.equals("true") ? VALOR_S : null;
		if (campo == null) {
			line.append(BasicText.changeSize("N", TAM_CAMBIO_DOMICILIO));
		} else {
			line.append(BasicText.changeSize(campo, TAM_CAMBIO_DOMICILIO));
		}

		campo = detalleMatriculacion.getTitularBean().getPersona()
				.getDireccion().getTipoVia().getIdTipoVia();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_TIPO_VIA_MATE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_TIPO_VIA_MATE));
		}

		campo = utilesConversiones.ajustarCamposIne(detalleMatriculacion
				.getTitularBean().getPersona().getDireccion().getNombreVia());
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_DOMICILIO_TITULAR_MATE));
		} else {
			line.append(BasicText.changeSize(UtilesCadenaCaracteres
					.quitarCaracteresExtranios(campo),
					TAM_DOMICILIO_TITULAR_MATE));
		}

		campo = detalleMatriculacion.getTitularBean().getPersona()
				.getDireccion().getNumero();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_NUM_DOMICILIO_MATE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_NUM_DOMICILIO_MATE));
		}

		campo = detalleMatriculacion.getTitularBean().getPersona()
				.getDireccion().getPuntoKilometrico();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_KM_DOMICILIO_MATE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_KM_DOMICILIO_MATE));
		}

		campo = detalleMatriculacion.getTitularBean().getPersona()
				.getDireccion().getHm();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_HEC_DOMICILIO_MATE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_HEC_DOMICILIO_MATE));
		}

		campo = detalleMatriculacion.getTitularBean().getPersona()
				.getDireccion().getBloque();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_BLOQUE_DOMICILIO_MATE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_BLOQUE_DOMICILIO_MATE));
		}

		campo = detalleMatriculacion.getTitularBean().getPersona()
				.getDireccion().getPortal();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_LETRA_DOMICILIO_MATE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_LETRA_DOMICILIO_MATE));
		}

		campo = detalleMatriculacion.getTitularBean().getPersona()
				.getDireccion().getEscalera();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_ESCALERA_DOMICILIO_MATE));
		} else {
			line.append(BasicText
					.changeSize(campo, TAM_ESCALERA_DOMICILIO_MATE));
		}

		campo = detalleMatriculacion.getTitularBean().getPersona()
				.getDireccion().getPlanta();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_PISO_DOMICILIO_MATE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_PISO_DOMICILIO_MATE));
		}

		campo = detalleMatriculacion.getTitularBean().getPersona()
				.getDireccion().getPuerta();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_PUERTA_DOMICILIO_MATE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_PUERTA_DOMICILIO_MATE));
		}

		campo = detalleMatriculacion.getTitularBean().getPersona()
				.getDireccion().getMunicipio().getProvincia().getIdProvincia();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_PROVINCIA_TITULAR));
		} else {
			line.append(BasicText.changeSize(campo, TAM_PROVINCIA_TITULAR));
		}

		campo += detalleMatriculacion.getTitularBean().getPersona()
				.getDireccion().getMunicipio().getIdMunicipio();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_MUNICIPIO_TITULAR));
		} else {
			line.append(BasicText.changeSize(campo, TAM_MUNICIPIO_TITULAR));
		}

		campo = utilesConversiones.ajustarCamposIne(detalleMatriculacion
				.getTitularBean().getPersona().getDireccion().getPueblo());
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_PUEBLO_TITULAR));
		} else {
			line.append(BasicText.changeSize(campo, TAM_PUEBLO_TITULAR));
		}

		campo = detalleMatriculacion.getTitularBean().getPersona()
				.getDireccion().getCodPostal();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_CP_TITULAR));
		} else {
			line.append(BasicText.changeSize(campo, TAM_CP_TITULAR, '0',
					BasicText.DETRAS));
		}

		// Datos de vehículo
		// Subasta
		campo = null != detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getSubasta()
				&& detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getSubasta() == true ? VALOR_S : VALOR_N;
		if (campo == null) {
			line
					.append(BasicText.changeSize("",
							ConstantesTrafico.TAM_SUBASTA));
		} else {
			line.append(BasicText.changeSize(campo,
					ConstantesTrafico.TAM_SUBASTA));
		}
		
		//fechaITV
		try {
			if (detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
					.getFechaItv() != null
					&& !detalleMatriculacion.getTramiteTraficoBean()
							.getVehiculo().getFechaItv().isfechaNula()) {
				
				//Requisito Mate 2.5: la fecha de la ITV verificar si fue generada o si fue modificada
				UtilesTrafico utilesTrafico = new UtilesTrafico();
				int iguales = utilesFecha.compararFechas(detalleMatriculacion.getTramiteTraficoBean()
														.getVehiculo().getFechaItv(), utilesTrafico.calcularFechaITV(detalleMatriculacion.getTramiteTraficoBean().getFechaPresentacion(), 
																detalleMatriculacion.getTramiteTraficoBean().getVehiculo()));
				if(iguales == 1){
					campo = null;
				}
				else{
					campo = formato.format(detalleMatriculacion
							.getTramiteTraficoBean().getVehiculo().getFechaItv()
							.getTimestamp());
				}
			} else {
				campo = null;
			}
		} catch (ParseException e) {
			campo = null;
		}
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_FECHA_ITV));
		} else {
			line.append(BasicText.changeSize(campo, TAM_FECHA_ITV, '0', false));
		}

		// Sólo para ciclomotores que tuvieran matrícula en un ayuntamiento,
		// sino USADO
		if (ciclomotor) {
			campo = null != detalleMatriculacion.getTramiteTraficoBean()
					.getVehiculo().getMatriAyuntamiento()
					&& detalleMatriculacion.getTramiteTraficoBean()
							.getVehiculo().getMatriAyuntamiento()
							.equals("true") ? VALOR_S : VALOR_USADO;
		} else {
			campo = null;
		}
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_MATRICULA_AYTO));
		} else {
			line.append(BasicText.changeSize(campo, TAM_MATRICULA_AYTO));
		}

		campo = null != detalleMatriculacion.getTramiteTraficoBean()
				.getVehiculo().getExcesoPeso()
				&& detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
						.getExcesoPeso().equals("true") ? VALOR_S : null;
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_EXCESO_PESO));
		} else {
			line.append(BasicText.changeSize(campo, TAM_EXCESO_PESO));
		}
		//FECHA DE ULTIMA TRANSFERENCIA
		// Inicialmente la fecha de ultima transferencia la ponemos a blanco
		line.append(BasicText.changeSize("", TAM_FECHA_PRIMERA_MATR));

		try {
			if (detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
					.getFechaPrimMatri() != null
					&& !detalleMatriculacion.getTramiteTraficoBean()
							.getVehiculo().getFechaPrimMatri().isfechaNula()) {
				campo = formato.format(detalleMatriculacion
						.getTramiteTraficoBean().getVehiculo()
						.getFechaPrimMatri().getTimestamp());
			} else {
				campo = null;
			}
		} catch (ParseException e) {
			campo = null;
		}
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_FECHA_PRIMERA_MATR));
		} else {
			line.append(BasicText.changeSize(campo, TAM_FECHA_PRIMERA_MATR,
					'0', false));
		}
		//Importado y pais de importacion
		campo = null != detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getImportado()
		&& detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getImportado() == true ? VALOR_S : VALOR_N;
		if (null == campo) {
			line.append(BasicText.changeSize("",
					ConstantesTrafico.TAM_IMPORTADO));
		} else {
			line.append(BasicText.changeSize(campo,
					ConstantesTrafico.TAM_IMPORTADO));
		}
		// País Importación
		campo = null != detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getPaisImportacionBean()
				? detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getPaisImportacionBean()
				.getIdPaisImportacion() : "";
		if (null == campo) {
			line.append(BasicText.changeSize("",
					ConstantesTrafico.TAM_PAIS_IMPORT));
		} else {
			line.append(BasicText.changeSize(campo,
					ConstantesTrafico.TAM_PAIS_IMPORT));
		}
		//FIN
		campo = null != detalleMatriculacion.getRepresentanteTitularBean()
				.getIdMotivoTutela() ? VALOR_S : null;
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_TUTELA_MATRICULACION));
		} else {
			line.append(BasicText.changeSize(campo, TAM_TUTELA_MATRICULACION));
		}

		campo = null != detalleMatriculacion.getTramiteTraficoBean()
				.getVehiculo().getDiplomatico()
				&& detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
						.getDiplomatico().equals("true") ? VALOR_S : null;
		;
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_MATRICULA_DIPL));
		} else {
			line.append(BasicText.changeSize(campo, TAM_MATRICULA_DIPL));
		}

		// Este apartado siempre irá a "E"
		campo = null != detalleMatriculacion.getTramiteTraficoBean().getIedtm()
				&& detalleMatriculacion.getTramiteTraficoBean().getIedtm()
						.equals("E") ? "E" : null;
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_TIPO_LIMITACION));
		} else {
			line.append(BasicText.changeSize(campo, TAM_TIPO_LIMITACION));
		}

		try {
			if (detalleMatriculacion.getTramiteTraficoBean().getFechaIedtm() != null
					&& !detalleMatriculacion.getTramiteTraficoBean()
							.getFechaIedtm().isfechaNula()) {
				campo = formato
						.format(detalleMatriculacion.getTramiteTraficoBean()
								.getFechaIedtm().getTimestamp());
			} else {
				campo = null;
			}
		} catch (ParseException e) {
			campo = null;
		}
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_FECHA_LIMITACION));
		} else {
			line.append(BasicText.changeSize(campo, TAM_FECHA_LIMITACION, '0',
					false));
		}

		campo = null != detalleMatriculacion.getTramiteTraficoBean()
				.getNumRegIedtm()
				&& !"-1".equals(detalleMatriculacion.getTramiteTraficoBean()
						.getNumRegIedtm().getValorEnum()) ? detalleMatriculacion
				.getTramiteTraficoBean().getNumRegIedtm().getValorEnum()
				: null;
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_NUM_REG));
		} else {
			line.append(BasicText.changeSize(campo, TAM_NUM_REG));
		}

		// Se cubrirá con el literal "EXENTO PR IEDMT"
		// campo =
		// detalleMatriculacion.getTramiteTraficoBean().getFinancieraIedtm();
		campo = null != detalleMatriculacion.getTramiteTraficoBean().getIedtm()
				&& detalleMatriculacion.getTramiteTraficoBean().getIedtm()
						.equals("E") ? "EXENTO PR IEDMT" : "";
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_FINANCIERA_LIMITACION));
		} else {
			line.append(BasicText.changeSize(campo, TAM_FINANCIERA_LIMITACION));
		}

		// Datos de Impuestos
		// Se coge siempre el CEM que venga en pantalla.
		campo = detalleMatriculacion.getTramiteTraficoBean().getCemIedtm();	
		if (campo == null || campo.length() == 0) {
			line.append(BasicText.changeSize("", TAM_CEM, '0', false));
		} else {
			line.append(BasicText.changeSize(campo, TAM_CEM));
		}

		//TODO MPC. Cambio IVTM
		if (null != detalleMatriculacion.getTramiteTraficoBean()) {
			IVTMModeloMatriculacionInterface modeloIVTM = new IVTMModeloMatriculacionImpl();
			IvtmMatriculacionDto ivtmMatriculacion =  modeloIVTM.buscarIvtmNumExp(detalleMatriculacion.getTramiteTraficoBean().getNumExpediente());
			if (ivtmMatriculacion != null) {
				campo = ivtmMatriculacion.getNrc();
			} else {
				campo = null;
			}
		} else {
			campo = null;
		}
		if (campo == null || campo.length() == 0) {
			line.append(BasicText.changeSize("", TAM_CODIGO_CEMU));
		} else {
			line.append(BasicText.changeSize(campo, TAM_CODIGO_CEMU));
		}

		campo = detalleMatriculacion.getTramiteTraficoBean().getCema();
		if (campo == null || campo.length() == 0) {
			line.append(BasicText.changeSize("", TAM_CEM));
		} else {
			line.append(BasicText.changeSize(campo, TAM_CEM));
		}

		// Para ver si hay renting
		campo = null != detalleMatriculacion.getTramiteTraficoBean()
				.getRenting()
				&& detalleMatriculacion.getTramiteTraficoBean().getRenting()
						.equals("true") ? VALOR_S : VALOR_N;
		if (null == campo) {
			line.append(BasicText.changeSize("", TAM_RENTING));
		} else {
			line.append(BasicText.changeSize(campo, TAM_RENTING));
		}

		// Los kilometros a los que se pasa la ITV los mandamos a ceros
		line.append(BasicText.changeSize("", TAM_KM_ITV, '0', false));

		// La hora a la que se pasa la ITV los mandamos a ceros
		line.append(BasicText.changeSize("", TAM_HORA_ITV, '0', false));
		// Arriba
		//ARRENDATARIO
		if (null == detalleMatriculacion.getArrendatarioBean().getPersona()
				.getNif()) {
			line.append(BasicText.changeSize("", TAM_ARRENDATARIO_COMPLETO));
		} else {
			campo = detalleMatriculacion.getArrendatarioBean().getPersona()
					.getNif();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_DNI_ARRENDATARIO));
			} else {
				line.append(BasicText.changeSize(campo, TAM_DNI_ARRENDATARIO));
			}

			try {
				if (detalleMatriculacion.getArrendatarioBean().getFechaInicio() != null
						&& !detalleMatriculacion.getArrendatarioBean()
								.getFechaInicio().isfechaNula()) {
					campo = formato.format(detalleMatriculacion
							.getArrendatarioBean().getFechaInicio()
							.getTimestamp());
				} else {
					campo = null;
				}
			} catch (ParseException e) {
				campo = null;
			}
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_FECHA_INICIO_RENTING));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_FECHA_INICIO_RENTING, '0', false));
			}

			try {
				if (detalleMatriculacion.getArrendatarioBean().getFechaFin() != null
						&& !detalleMatriculacion.getArrendatarioBean()
								.getFechaFin().isfechaNula()) {
					campo = formato
							.format(detalleMatriculacion.getArrendatarioBean()
									.getFechaFin().getTimestamp());
				} else {
					campo = null;
				}
			} catch (ParseException e) {
				campo = null;
			}
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_FECHA_FIN_RENTING));
			} else {
				line.append(BasicText.changeSize(campo, TAM_FECHA_FIN_RENTING,
						'0', false));
			}

			campo = null != detalleMatriculacion.getArrendatarioBean()
					.getHoraInicio() ? detalleMatriculacion
					.getArrendatarioBean().getHoraInicio().replaceAll(":", "")
					: null;
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_HORA_INICIO_RENTING));
			} else {
				line.append(BasicText
						.changeSize(campo, TAM_HORA_INICIO_RENTING));
			}

			campo = null != detalleMatriculacion.getArrendatarioBean()
					.getHoraFin() ? detalleMatriculacion.getArrendatarioBean()
					.getHoraFin().replaceAll(":", "") : null;
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_HORA_FIN_RENTING));
			} else {
				line.append(BasicText.changeSize(campo, TAM_HORA_FIN_RENTING));
			}

			try {
				if (detalleMatriculacion.getArrendatarioBean().getPersona()
						.getFechaNacimientoBean() != null
						&& !detalleMatriculacion.getArrendatarioBean()
								.getPersona().getFechaNacimientoBean()
								.isfechaNula()) {
					campo = formato.format(detalleMatriculacion
							.getArrendatarioBean().getPersona()
							.getFechaNacimientoBean().getTimestamp());
				} else {
					campo = null;
				}
			} catch (ParseException e) {
				campo = null;
			}
			if (campo == null) {
				line.append(BasicText.changeSize("",
						TAM_FECHA_NACIMIENTO_ARRENDATARIO));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_FECHA_NACIMIENTO_ARRENDATARIO, '0', false));
			}

			campo = detalleMatriculacion.getArrendatarioBean().getPersona()
					.getSexo();
			if (campo == null || campo.compareTo(PERSONA_JURIDICA) == 0) {
				campo = detalleMatriculacion.getArrendatarioBean().getPersona()
						.getApellido1RazonSocial();
				if (campo == null) {
					line.append(BasicText.changeSize("",
							TAM_PRIMER_APELLIDO_ARRENDATARIO));
				} else {
					line.append(BasicText.changeSize(campo,
							TAM_PRIMER_APELLIDO_ARRENDATARIO));
				}
				line.append(BasicText.changeSize("",
						TAM_SEGUNDO_APELLIDO_ARRENDATARIO));
				line.append(BasicText.changeSize("", TAM_NOMBRE_ARRENDATARIO));
			} else {
				campo = detalleMatriculacion.getArrendatarioBean().getPersona()
						.getApellido1RazonSocial();
				if (campo == null) {
					line.append(BasicText.changeSize("",
							TAM_PRIMER_APELLIDO_ARRENDATARIO));
				} else {
					line.append(BasicText.changeSize(campo,
							TAM_PRIMER_APELLIDO_ARRENDATARIO));
				}

				campo = detalleMatriculacion.getArrendatarioBean().getPersona()
						.getApellido2();
				if (campo == null) {
					line.append(BasicText.changeSize("",
							TAM_SEGUNDO_APELLIDO_ARRENDATARIO));
				} else {
					line.append(BasicText.changeSize(campo,
							TAM_SEGUNDO_APELLIDO_ARRENDATARIO));
				}

				campo = detalleMatriculacion.getArrendatarioBean().getPersona()
						.getNombre();
				if (campo == null) {
					line.append(BasicText.changeSize("",
							TAM_NOMBRE_ARRENDATARIO));
				} else {
					line.append(BasicText.changeSize(campo,
							TAM_NOMBRE_ARRENDATARIO));
				}
			}

			campo = detalleMatriculacion.getArrendatarioBean().getPersona()
					.getSexo();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_SEXO_ARRENDATARIO));
			} else {
				line.append(BasicText.changeSize(campo, TAM_SEXO_ARRENDATARIO));
			}

			campo = detalleMatriculacion.getArrendatarioBean().getPersona()
					.getDireccion().getTipoVia().getIdTipoVia();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_TIPO_VIA_MATE));
			} else {
				line.append(BasicText.changeSize(campo, TAM_TIPO_VIA_MATE));
			}

			campo = utilesConversiones.ajustarCamposIne(detalleMatriculacion
					.getArrendatarioBean().getPersona().getDireccion()
					.getNombreVia());
			if (campo == null) {
				line.append(BasicText
						.changeSize("", TAM_DOMICILIO_TITULAR_MATE));
			} else {
				line.append(BasicText.changeSize(UtilesCadenaCaracteres
						.quitarCaracteresExtranios(campo),
						TAM_DOMICILIO_TITULAR_MATE));
			}

			campo = detalleMatriculacion.getArrendatarioBean().getPersona()
					.getDireccion().getNumero();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_NUM_DOMICILIO_MATE));
			} else {
				line
						.append(BasicText.changeSize(campo,
								TAM_NUM_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getArrendatarioBean().getPersona()
					.getDireccion().getPuntoKilometrico();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_KM_DOMICILIO_MATE));
			} else {
				line.append(BasicText.changeSize(campo, TAM_KM_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getArrendatarioBean().getPersona()
					.getDireccion().getHm();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_HEC_DOMICILIO_MATE));
			} else {
				line
						.append(BasicText.changeSize(campo,
								TAM_HEC_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getArrendatarioBean().getPersona()
					.getDireccion().getBloque();
			if (campo == null) {
				line
						.append(BasicText.changeSize("",
								TAM_BLOQUE_DOMICILIO_MATE));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_BLOQUE_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getArrendatarioBean().getPersona()
					.getDireccion().getPortal();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_LETRA_DOMICILIO_MATE));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_LETRA_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getArrendatarioBean().getPersona()
					.getDireccion().getEscalera();
			if (campo == null) {
				line.append(BasicText.changeSize("",
						TAM_ESCALERA_DOMICILIO_MATE));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_ESCALERA_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getArrendatarioBean().getPersona()
					.getDireccion().getPlanta();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_PISO_DOMICILIO_MATE));
			} else {
				line.append(BasicText
						.changeSize(campo, TAM_PISO_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getArrendatarioBean().getPersona()
					.getDireccion().getPuerta();
			if (campo == null) {
				line
						.append(BasicText.changeSize("",
								TAM_PUERTA_DOMICILIO_MATE));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_PUERTA_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getArrendatarioBean().getPersona()
					.getDireccion().getMunicipio().getProvincia()
					.getIdProvincia();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_PROVINCIA_TITULAR));
			} else {
				line.append(BasicText.changeSize(campo, TAM_PROVINCIA_TITULAR));
			}

			campo += detalleMatriculacion.getArrendatarioBean().getPersona()
					.getDireccion().getMunicipio().getIdMunicipio();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_MUNICIPIO_TITULAR));
			} else {
				line.append(BasicText.changeSize(campo, TAM_MUNICIPIO_TITULAR));
			}

			campo = utilesConversiones.ajustarCamposIne(detalleMatriculacion
					.getArrendatarioBean().getPersona().getDireccion()
					.getPueblo());
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_PUEBLO_TITULAR));
			} else {
				line.append(BasicText.changeSize(campo, TAM_PUEBLO_TITULAR));
			}

			campo = detalleMatriculacion.getArrendatarioBean().getPersona()
					.getDireccion().getCodPostal();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_CP_TITULAR));
			} else {
				line.append(BasicText.changeSize(campo, TAM_CP_TITULAR, '0',
						BasicText.DETRAS));
			}

		}
		//FIN ARRENDATARIO
		
		// Datos de Autónomo
		campo = null != detalleMatriculacion.getTitularBean().getAutonomo()
				&& detalleMatriculacion.getTitularBean().getAutonomo().equals(
						"true") ? VALOR_S : VALOR_N;
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_FLAG_IAE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_FLAG_IAE));
		}

		campo = detalleMatriculacion.getTitularBean().getCodigoIAE();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_CODIGO_IAE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_CODIGO_IAE));
		}

		// Datos de Conductor habitual
		campo = null != detalleMatriculacion.getConductorHabitualBean().getPersona().getNif() ? 
				detalleMatriculacion.getConductorHabitualBean().getPersona().getNif() : null;
		if(campo == null){
			line.append(BasicText.changeSize("", ConstantesPDF.TAM_CONDUCTOR_HABITUAL));
		}else{
			campo = detalleMatriculacion.getConductorHabitualBean().getPersona().getNif();
			if(campo == null){
				line.append(BasicText.changeSize("", ConstantesPDF.TAM_DNI_CONDUCTOR_HABITUAL));
			}else{
				line.append(BasicText.changeSize(campo, ConstantesPDF.TAM_DNI_CONDUCTOR_HABITUAL));
			}
			try {
				if (detalleMatriculacion.getConductorHabitualBean().getFechaInicio() != null
						&& !detalleMatriculacion.getConductorHabitualBean()
								.getFechaInicio().isfechaNula()) {
					campo = formato.format(detalleMatriculacion
							.getConductorHabitualBean().getFechaInicio()
							.getTimestamp());
				} else {
					campo = null;
				}
			} catch (ParseException e) {
				campo = null;
			}
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_FECHA_INICIO_CONDUCTOR));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_FECHA_INICIO_CONDUCTOR, '0', false));
			}

			try {
				if (detalleMatriculacion.getConductorHabitualBean().getFechaFin() != null
						&& !detalleMatriculacion.getConductorHabitualBean()
								.getFechaFin().isfechaNula()) {
					campo = formato
							.format(detalleMatriculacion.getConductorHabitualBean()
									.getFechaFin().getTimestamp());
				} else {
					campo = null;
				}
			} catch (ParseException e) {
				campo = null;
			}
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_FECHA_FIN_CONDUCTOR));
			} else {
				line.append(BasicText.changeSize(campo, TAM_FECHA_FIN_CONDUCTOR,
						'0', false));
			}

			campo = null != detalleMatriculacion.getConductorHabitualBean()
					.getHoraInicio() ? detalleMatriculacion
					.getConductorHabitualBean().getHoraInicio().replaceAll(":", "")
					: null;
			if (campo == null) {
				line.append(BasicText.changeSize("", ConstantesTrafico.TAM_HORA_INICIO_CONDUCTOR));
			} else {
				line.append(BasicText
						.changeSize(campo, ConstantesTrafico.TAM_HORA_INICIO_CONDUCTOR));
			}

			campo = null != detalleMatriculacion.getConductorHabitualBean()
					.getHoraFin() ? detalleMatriculacion.getConductorHabitualBean()
					.getHoraFin().replaceAll(":", "") : null;
			if (campo == null) {
				line.append(BasicText.changeSize("", ConstantesTrafico.TAM_HORA_FIN_CONDUCTOR));
			} else {
				line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_HORA_FIN_CONDUCTOR));
			}
			
		}

		// Datos de Tutor
		if (null != detalleMatriculacion.getRepresentanteTitularBean()
				.getFechaInicio()) {
			campo = detalleMatriculacion.getRepresentanteTitularBean()
					.getPersona().getNif();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_DNI_REPRESENTANTE));
			} else {
				line.append(BasicText.changeSize(campo, TAM_DNI_REPRESENTANTE));
			}

			try {
				if (detalleMatriculacion.getRepresentanteTitularBean()
						.getFechaInicio() != null
						&& !detalleMatriculacion.getRepresentanteTitularBean()
								.getFechaInicio().isfechaNula()) {
					campo = formato.format(detalleMatriculacion
							.getRepresentanteTitularBean().getFechaInicio()
							.getTimestamp());
				} else {
					campo = null;
				}
			} catch (ParseException e) {
				campo = null;
			}
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_FECHA_INICIO_TUTELA));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_FECHA_INICIO_TUTELA, '0', false));
			}

			campo = null;
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_TRAMITE_TUTELA));
			} else {
				line.append(BasicText.changeSize(campo, TAM_TRAMITE_TUTELA));
			}

			campo = null != detalleMatriculacion.getRepresentanteTitularBean()
					.getIdMotivoTutela() ? detalleMatriculacion
					.getRepresentanteTitularBean().getIdMotivoTutela()
					.getValorEnum() : null;
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_TIPO_TUTELA));
			} else {
				line.append(BasicText.changeSize(campo, TAM_TIPO_TUTELA));
			}

		} else {
			line.append(BasicText.changeSize("", TAM_DNI_REPRESENTANTE));
			line.append(BasicText.changeSize("", TAM_FECHA_INICIO_TUTELA));
			line.append(BasicText.changeSize("", TAM_TRAMITE_TUTELA));
			line.append(BasicText.changeSize("", TAM_TIPO_TUTELA));
		}
		//Datos TUTOR
		//NIVE
		campo = detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getNive();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_NIVE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_NIVE));
		}
		// Tipo de Matrícula
		if(detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getCriterioConstruccionBean() != null &&
				detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getCriterioConstruccionBean().getIdCriterioConstruccion() != null){
			campo = UtilesVistaTrafico.getInstance().queTipoMatricula(detalleMatriculacion
					.getTramiteTraficoBean().getVehiculo()
					.getCriterioConstruccionBean().getIdCriterioConstruccion());
		}
		else{
			campo = null;
		}

		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_TIPO_MATRICULA));
		} else {
			line.append(BasicText.changeSize(campo, TAM_TIPO_MATRICULA));
		}
		if(log.isDebugEnabled()) {
			log.debug("Nube de puntos NIVE: "+line.toString());
		}
		return line.toString();
	}

	/**
	 * Método para obtener el String para la nube de matriculación 2.
	 * 
	 * @param detalleMatriculacion
	 * @return
	 */
	protected String obtenerNubeMatriculacion2(
			TramiteTraficoMatriculacionBean detalleMatriculacion) {
		SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
		StringBuffer line = new StringBuffer();
		String campo = "";

		// si no hay conductor habitual ni arrendatario no se informa esta nube
		if ((null == detalleMatriculacion.getConductorHabitualBean()
				.getPersona().getNif())
				&& (null == detalleMatriculacion.getArrendatarioBean()
						.getPersona().getNif())) {
			return null;
		}

		if (null == detalleMatriculacion.getArrendatarioBean().getPersona()
				.getNif()) {
			line.append(BasicText.changeSize("", TAM_ARRENDATARIO_COMPLETO));
		} else {
			campo = detalleMatriculacion.getArrendatarioBean().getPersona()
					.getNif();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_DNI_ARRENDATARIO));
			} else {
				line.append(BasicText.changeSize(campo, TAM_DNI_ARRENDATARIO));
			}

			try {
				if (detalleMatriculacion.getArrendatarioBean().getFechaInicio() != null
						&& !detalleMatriculacion.getArrendatarioBean()
								.getFechaInicio().isfechaNula()) {
					campo = formato.format(detalleMatriculacion
							.getArrendatarioBean().getFechaInicio()
							.getTimestamp());
				} else {
					campo = null;
				}
			} catch (ParseException e) {
				campo = null;
			}
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_FECHA_INICIO_RENTING));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_FECHA_INICIO_RENTING, '0', false));
			}

			try {
				if (detalleMatriculacion.getArrendatarioBean().getFechaFin() != null
						&& !detalleMatriculacion.getArrendatarioBean()
								.getFechaFin().isfechaNula()) {
					campo = formato
							.format(detalleMatriculacion.getArrendatarioBean()
									.getFechaFin().getTimestamp());
				} else {
					campo = null;
				}
			} catch (ParseException e) {
				campo = null;
			}
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_FECHA_FIN_RENTING));
			} else {
				line.append(BasicText.changeSize(campo, TAM_FECHA_FIN_RENTING,
						'0', false));
			}

			campo = null != detalleMatriculacion.getArrendatarioBean()
					.getHoraInicio() ? detalleMatriculacion
					.getArrendatarioBean().getHoraInicio().replaceAll(":", "")
					: null;
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_HORA_INICIO_RENTING));
			} else {
				line.append(BasicText
						.changeSize(campo, TAM_HORA_INICIO_RENTING));
			}

			campo = null != detalleMatriculacion.getArrendatarioBean()
					.getHoraFin() ? detalleMatriculacion.getArrendatarioBean()
					.getHoraFin().replaceAll(":", "") : null;
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_HORA_FIN_RENTING));
			} else {
				line.append(BasicText.changeSize(campo, TAM_HORA_FIN_RENTING));
			}

			try {
				if (detalleMatriculacion.getArrendatarioBean().getPersona()
						.getFechaNacimientoBean() != null
						&& !detalleMatriculacion.getArrendatarioBean()
								.getPersona().getFechaNacimientoBean()
								.isfechaNula()) {
					campo = formato.format(detalleMatriculacion
							.getArrendatarioBean().getPersona()
							.getFechaNacimientoBean().getTimestamp());
				} else {
					campo = null;
				}
			} catch (ParseException e) {
				campo = null;
			}
			if (campo == null) {
				line.append(BasicText.changeSize("",
						TAM_FECHA_NACIMIENTO_ARRENDATARIO));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_FECHA_NACIMIENTO_ARRENDATARIO, '0', false));
			}

			campo = detalleMatriculacion.getArrendatarioBean().getPersona()
					.getSexo();
			if (campo == null || campo.compareTo(PERSONA_JURIDICA) == 0) {
				campo = detalleMatriculacion.getArrendatarioBean().getPersona()
						.getApellido1RazonSocial();
				if (campo == null) {
					line.append(BasicText.changeSize("",
							TAM_PRIMER_APELLIDO_ARRENDATARIO));
				} else {
					line.append(BasicText.changeSize(campo,
							TAM_PRIMER_APELLIDO_ARRENDATARIO));
				}
				line.append(BasicText.changeSize("",
						TAM_SEGUNDO_APELLIDO_ARRENDATARIO));
				line.append(BasicText.changeSize("", TAM_NOMBRE_ARRENDATARIO));
			} else {
				campo = detalleMatriculacion.getArrendatarioBean().getPersona()
						.getApellido1RazonSocial();
				if (campo == null) {
					line.append(BasicText.changeSize("",
							TAM_PRIMER_APELLIDO_ARRENDATARIO));
				} else {
					line.append(BasicText.changeSize(campo,
							TAM_PRIMER_APELLIDO_ARRENDATARIO));
				}

				campo = detalleMatriculacion.getArrendatarioBean().getPersona()
						.getApellido2();
				if (campo == null) {
					line.append(BasicText.changeSize("",
							TAM_SEGUNDO_APELLIDO_ARRENDATARIO));
				} else {
					line.append(BasicText.changeSize(campo,
							TAM_SEGUNDO_APELLIDO_ARRENDATARIO));
				}

				campo = detalleMatriculacion.getArrendatarioBean().getPersona()
						.getNombre();
				if (campo == null) {
					line.append(BasicText.changeSize("",
							TAM_NOMBRE_ARRENDATARIO));
				} else {
					line.append(BasicText.changeSize(campo,
							TAM_NOMBRE_ARRENDATARIO));
				}
			}

			campo = detalleMatriculacion.getArrendatarioBean().getPersona()
					.getSexo();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_SEXO_ARRENDATARIO));
			} else {
				line.append(BasicText.changeSize(campo, TAM_SEXO_ARRENDATARIO));
			}

			campo = detalleMatriculacion.getArrendatarioBean().getPersona()
					.getDireccion().getTipoVia().getIdTipoVia();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_TIPO_VIA_MATE));
			} else {
				line.append(BasicText.changeSize(campo, TAM_TIPO_VIA_MATE));
			}

			campo = utilesConversiones.ajustarCamposIne(detalleMatriculacion
					.getArrendatarioBean().getPersona().getDireccion()
					.getNombreVia());
			if (campo == null) {
				line.append(BasicText
						.changeSize("", TAM_DOMICILIO_TITULAR_MATE));
			} else {
				line.append(BasicText.changeSize(UtilesCadenaCaracteres
						.quitarCaracteresExtranios(campo),
						TAM_DOMICILIO_TITULAR_MATE));
			}

			campo = detalleMatriculacion.getArrendatarioBean().getPersona()
					.getDireccion().getNumero();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_NUM_DOMICILIO_MATE));
			} else {
				line
						.append(BasicText.changeSize(campo,
								TAM_NUM_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getArrendatarioBean().getPersona()
					.getDireccion().getPuntoKilometrico();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_KM_DOMICILIO_MATE));
			} else {
				line.append(BasicText.changeSize(campo, TAM_KM_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getArrendatarioBean().getPersona()
					.getDireccion().getHm();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_HEC_DOMICILIO_MATE));
			} else {
				line
						.append(BasicText.changeSize(campo,
								TAM_HEC_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getArrendatarioBean().getPersona()
					.getDireccion().getBloque();
			if (campo == null) {
				line
						.append(BasicText.changeSize("",
								TAM_BLOQUE_DOMICILIO_MATE));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_BLOQUE_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getArrendatarioBean().getPersona()
					.getDireccion().getPortal();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_LETRA_DOMICILIO_MATE));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_LETRA_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getArrendatarioBean().getPersona()
					.getDireccion().getEscalera();
			if (campo == null) {
				line.append(BasicText.changeSize("",
						TAM_ESCALERA_DOMICILIO_MATE));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_ESCALERA_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getArrendatarioBean().getPersona()
					.getDireccion().getPlanta();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_PISO_DOMICILIO_MATE));
			} else {
				line.append(BasicText
						.changeSize(campo, TAM_PISO_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getArrendatarioBean().getPersona()
					.getDireccion().getPuerta();
			if (campo == null) {
				line
						.append(BasicText.changeSize("",
								TAM_PUERTA_DOMICILIO_MATE));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_PUERTA_DOMICILIO_MATE));
			}

			campo = detalleMatriculacion.getArrendatarioBean().getPersona()
					.getDireccion().getMunicipio().getProvincia()
					.getIdProvincia();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_PROVINCIA_TITULAR));
			} else {
				line.append(BasicText.changeSize(campo, TAM_PROVINCIA_TITULAR));
			}

			campo += detalleMatriculacion.getArrendatarioBean().getPersona()
					.getDireccion().getMunicipio().getIdMunicipio();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_MUNICIPIO_TITULAR));
			} else {
				line.append(BasicText.changeSize(campo, TAM_MUNICIPIO_TITULAR));
			}

			campo = utilesConversiones.ajustarCamposIne(detalleMatriculacion
					.getArrendatarioBean().getPersona().getDireccion()
					.getPueblo());
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_PUEBLO_TITULAR));
			} else {
				line.append(BasicText.changeSize(campo, TAM_PUEBLO_TITULAR));
			}

			campo = detalleMatriculacion.getArrendatarioBean().getPersona()
					.getDireccion().getCodPostal();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_CP_TITULAR));
			} else {
				line.append(BasicText.changeSize(campo, TAM_CP_TITULAR, '0',
						BasicText.DETRAS));
			}

		}

		if (null == detalleMatriculacion.getConductorHabitualBean()
				.getPersona().getNif()) {
			line.append(BasicText.changeSize("", TAM_CONDUCTOR_HABITUAL));
		} else {
			campo = detalleMatriculacion.getConductorHabitualBean()
					.getPersona().getNif();
			if (campo == null) {
				line.append(BasicText
						.changeSize("", TAM_DNI_CONDUCTOR_HABITUAL));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_DNI_CONDUCTOR_HABITUAL));
			}

			try {
				if (detalleMatriculacion.getConductorHabitualBean()
						.getFechaInicio() != null
						&& !detalleMatriculacion.getConductorHabitualBean()
								.getFechaInicio().isfechaNula()) {
					campo = formato.format(detalleMatriculacion
							.getConductorHabitualBean().getFechaInicio()
							.getTimestamp());
				} else {
					campo = null;
				}
			} catch (ParseException e) {
				campo = null;
			}
			if (campo == null) {
				line.append(BasicText
						.changeSize("", TAM_FECHA_INICIO_CONDUCTOR));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_FECHA_INICIO_CONDUCTOR, '0', false));
			}

			try {
				if (detalleMatriculacion.getConductorHabitualBean()
						.getFechaFin() != null
						&& !detalleMatriculacion.getConductorHabitualBean()
								.getFechaFin().isfechaNula()) {
					campo = formato.format(detalleMatriculacion
							.getConductorHabitualBean().getFechaFin()
							.getTimestamp());
				} else {
					campo = null;
				}
			} catch (ParseException e) {
				campo = null;
			}
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_FECHA_FIN_CONDUCTOR));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_FECHA_FIN_CONDUCTOR, '0', false));
			}
			campo = null != detalleMatriculacion.getConductorHabitualBean()
			.getHoraInicio() ? detalleMatriculacion
			.getConductorHabitualBean().getHoraInicio().replaceAll(":", "")
			: null;
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_HORA_INICIO_CONDUCTOR));
			} else {
				line.append(BasicText
						.changeSize(campo, TAM_HORA_INICIO_CONDUCTOR));
			}
		
			campo = null != detalleMatriculacion.getConductorHabitualBean()
					.getHoraFin() ? detalleMatriculacion.getConductorHabitualBean()
					.getHoraFin().replaceAll(":", "") : null;
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_HORA_FIN_CONDUCTOR));
			} else {
				line.append(BasicText.changeSize(campo, TAM_HORA_FIN_CONDUCTOR));
			}

		}
		return line.toString();
	}

	// METODO ObtenerValorCampoNube , Devuelve el valor del campo pasado por parametro y el tramite pasado
	
		public String ObtenerValorCampoNube(String Campo, TramiteTraficoMatriculacionBean  detalleMatriculacion,UtilesConversionesMatw utilConv) 
			{
				
			   String tcamp = "";
			  
			   SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
			  
			  
				if (Campo.equalsIgnoreCase("Tutela"))
				{
					tcamp = null !=  detalleMatriculacion.getRepresentanteTitularBean()
							.getIdMotivoTutela() ? VALOR_S : VALOR_N;
		 
					
				}
				
				// Renting
				if (Campo.equalsIgnoreCase("Renting"))
				{
					tcamp = null !=  detalleMatriculacion.getTramiteTraficoBean().getRenting() &&
							detalleMatriculacion.getTramiteTraficoBean().getRenting().equalsIgnoreCase("true")
							 ? VALOR_S :VALOR_N;
		 
				}	
					
				// Conductor habitual
					if (Campo.equalsIgnoreCase("Conductor Habitual"))
					{
						tcamp = null !=detalleMatriculacion.getConductorHabitualBean()
								.getPersona().getNif() ? VALOR_S :VALOR_N;
			 	
					
					
				     }
				// Num_expediente
				
					
				if (Campo.equalsIgnoreCase(ConstantesTrafico.ID_NUME_EXPEDIENTE))
				{
					tcamp =  detalleMatriculacion.getTramiteTraficoBean().getNumExpediente().toString();
		 		
				}
				
				// Tasa
				if (Campo.equalsIgnoreCase("Tasa") && detalleMatriculacion.getTramiteTraficoBean().getTasa()
						.getCodigoTasa()!=null){
					
					tcamp = detalleMatriculacion.getTramiteTraficoBean().getTasa()
							.getCodigoTasa();
					
				}
				
				

				// Tipo de tramite
				
				if (Campo.equalsIgnoreCase("Tipo Tramite") &&  detalleMatriculacion.getTipoTramiteMatriculacion()!= null){
				 tcamp = detalleMatriculacion.getTipoTramiteMatriculacion().getValorEnum();
					
				}
				
				// Servicio al que se destina el vehiculo 
				if (Campo.equalsIgnoreCase("Servicio") && detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
								.getServicioTraficoBean().getIdServicio() != null){
							tcamp =  detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
								.getServicioTraficoBean().getIdServicio();
							
				}
			
							// Fecha primera matriculación
							try {
								
								if ( Campo.equalsIgnoreCase("Fecha prim matriculacion") && detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
										.getFechaPrimMatri() != null
										&& !detalleMatriculacion.getTramiteTraficoBean()
												.getVehiculo().getFechaPrimMatri().isfechaNula()){
								
									tcamp =formato.format(detalleMatriculacion.getTramiteTraficoBean()
											.getVehiculo().getFechaPrimMatri().getFecha());
									}
									
							}
							catch (ParseException e) {
									tcamp = "";
							}
							
							
						
						// Bastidor
						if (Campo.equalsIgnoreCase("Bastidor") && detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
								.getBastidor() !=null){
							tcamp = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
								.getBastidor() ;
						
						}
						
						// NIVE
						if (Campo.equalsIgnoreCase("NIVE") && detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getNive() != null ) {
							tcamp = detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getNive(); 
						}
						// KM (Kms a los que se pasa la itv)
						
						if (Campo.equalsIgnoreCase("Kilometraje") && detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getKmUso()!=null)
							{
							tcamp = detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getKmUso().toString();
						}
							
						// NUMERO DE HORAS DE USO
						
						if (Campo.equalsIgnoreCase("Cuenta Horas") &&  detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getHorasUso() != null){
								
				          tcamp = detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getHorasUso().toString();
						}


						// Fecha validez itv 
				
						try {
							
							if ( Campo.equalsIgnoreCase("Fecha validez ITV") && detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getFechaItv() != null
									&& !detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getFechaItv().isfechaNula()){
							
								tcamp =formato.format(detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getFechaItv().getFecha());
								}
								
						}
						catch (ParseException e) {
								tcamp = "";
						}
						
						
						// Importado
						/*
						 * Un vehiculo solo puede ser importado si es usado. Y solo puede ser usado si tiene rellena la fecha de primera matricula.
						 */
						if (Campo.equalsIgnoreCase("Importado")){
							tcamp = null !=  detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getImportado()
									&& detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getImportado() == Boolean.TRUE
									&& detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getFechaPrimMatri() != null
									&& detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getVehiUsado() != null
									&& detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getVehiUsado().equals("true")
									?VALOR_S:VALOR_N; 
						
						}	
						
						// Subastado
						
						
						if (Campo.equalsIgnoreCase("Subasta")){
							tcamp = null !=  detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getSubasta()
									&& detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getSubasta() == Boolean.TRUE
									?VALOR_S:VALOR_N; 
						
						}	
						
						
						// USADO 
						/*
						 * Un vehiculo solo puede ser usado si además tiene fecha primera matricula.
						 */
									if (Campo.equalsIgnoreCase("Usado")){
										 if(detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getFechaPrimMatri() != null
											&& detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getVehiUsado() != null 
											&& detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getVehiUsado().equals("true")){
											 	 tcamp = VALOR_S;	
										 	   }else{
												 tcamp = VALOR_N;
										       }
						
						}	
							
						// Matricula origen 	
//						if (Campo.equalsIgnoreCase("Matricula Origen") &&  detalleMatriculacion.getTramiteTraficoBean()
//						.getVehiculo().getMatriculaOrigen() != null){
//						
//							tcamp = detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getMatriculaOrigen();
//						}

						// Matricula origen  Extranjera	, solo se incluye en la nube si el propertie esta activado
						// se utilizara el campo matricu
						if (Campo.equalsIgnoreCase("Matricula Orig Extr") ) {

							tcamp =  null!= detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getMatriculaOrigExtr() &&
							("SI".equalsIgnoreCase(detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getVehiUsado()) 
								|| "true".equalsIgnoreCase(detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getVehiUsado())
								|| "S".equalsIgnoreCase(detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getVehiUsado()))
								&& detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getImportado()?
								detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getMatriculaOrigExtr():"";

						}

						// Tipo de Inspeccion

						if (Campo.equalsIgnoreCase("Tipo Inspeccion") && detalleMatriculacion.getTramiteTraficoBean()
						.getVehiculo().getMotivoBean().getIdMotivo() !=null){
							tcamp = detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getMotivoBean().getIdMotivo();

						}

				// ****************************  DATOS DEL TITULAR ***********************************************

						// DOI del titular
						if (Campo.equalsIgnoreCase("DOI Titular") && detalleMatriculacion.getTitularBean().getPersona().getNif()!=null){
							tcamp = detalleMatriculacion.getTitularBean().getPersona().getNif();
						
						}
				
					
					
					    // Razon Social
						if (Campo.equalsIgnoreCase("Razon social")) {
							if ( detalleMatriculacion.getTitularBean().getPersona().getTipoPersona()!=null && 
									detalleMatriculacion.getTitularBean().getPersona().getTipoPersona().equals(TipoPersona.Juridica)){
								tcamp = null!=  detalleMatriculacion.getTitularBean().getPersona()
										.getApellido1RazonSocial()?detalleMatriculacion.getTitularBean().getPersona()
												.getApellido1RazonSocial():"";
							}
						}
						
						// Nombre del titular (Persona fisica)
						if (Campo.equalsIgnoreCase("Nombre Titular")) {
							if ( detalleMatriculacion.getTitularBean().getPersona().getTipoPersona()!=null && detalleMatriculacion.getTitularBean()
									.getPersona().getTipoPersona().equals(TipoPersona.Fisica)){
										tcamp = null!=  detalleMatriculacion.getTitularBean().getPersona().getNombre()?
												detalleMatriculacion.getTitularBean().getPersona().getNombre():"";
							}
						}
						
						// Apellido 1 titular (Persona fisica)
						if (Campo.equalsIgnoreCase("Apellido1 titular")) {
							if (detalleMatriculacion.getTitularBean().getPersona().getTipoPersona()!=null &&  detalleMatriculacion.getTitularBean()
									.getPersona().getTipoPersona().equals(TipoPersona.Fisica)){
										tcamp = null!= detalleMatriculacion.getTitularBean().getPersona().getApellido1RazonSocial()?
												detalleMatriculacion.getTitularBean().getPersona().getApellido1RazonSocial():"";
							}
						}
						
						// Apellido 2 titular (Persona fisica)
						if (Campo.equalsIgnoreCase("Apellido2 titular")) {
							if (detalleMatriculacion.getTitularBean().getPersona().getTipoPersona()!=null &&  detalleMatriculacion.getTitularBean()
									.getPersona().getTipoPersona().equals(TipoPersona.Fisica)){
										tcamp = null!= detalleMatriculacion.getTitularBean().getPersona().getApellido2()?
												detalleMatriculacion.getTitularBean().getPersona().getApellido2():"";
							}
						}
						
						
						// FECHA DE NACIMIENTO DEL TITULAR
						try {
							if ( Campo.equalsIgnoreCase("Fecha nacimiento titular")){
								 if (detalleMatriculacion.getTitularBean().getPersona().getFechaNacimientoBean() != null
									&& detalleMatriculacion.getTitularBean().getPersona().getTipoPersona()!=null && detalleMatriculacion.getTitularBean()
									.getPersona().getTipoPersona().equals(TipoPersona.Fisica)){
									 		tcamp = null!= formato.format(detalleMatriculacion.getTitularBean().getPersona()
									 		.getFechaNacimientoBean().getFecha()).toString()?formato.format(detalleMatriculacion.getTitularBean().getPersona()
									 		.getFechaNacimientoBean().getFecha()).toString():"";
								
								 }
							}
						}	
						catch (ParseException e) {
								tcamp = "";
						}
						
						
						
						
			
							
	             try {  // controlar y ver bien lo de la fecha.
						
						if ( Campo.equalsIgnoreCase("Fecha prim matriculacion") && detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
								.getFechaPrimMatri() != null
								&& !detalleMatriculacion.getTramiteTraficoBean()
										.getVehiculo().getFechaPrimMatri().isfechaNula()){
						
							tcamp =formato.format(detalleMatriculacion.getTramiteTraficoBean()
									.getVehiculo().getFechaPrimMatri().getFecha());
							}
							
					}
					catch (ParseException e) {
						tcamp = "";
					}

						// Sexo del  titular 
	              //// OJO HAY QUE COMPROBAR SI ES NECESARIO PONERLO PARA UNA EMPRESA  Y PONER EL VALOR X - PERSONA JURIDICA
						if (Campo.equalsIgnoreCase("Sexo titular")&& detalleMatriculacion.getTitularBean().getPersona().getSexo() !=null){
							tcamp = detalleMatriculacion.getTitularBean().getPersona().getSexo();
						}

						// NIF del representante del titular (Si es una empresa)
						if (Campo.equalsIgnoreCase("DOI Represent titular") ){
							if (detalleMatriculacion.getRepresentanteTitularBean().getPersona().getNif()!=null 
									&& detalleMatriculacion.getTitularBean().getPersona().getTipoPersona().equals(TipoPersona.Juridica)){
								tcamp = detalleMatriculacion.getRepresentanteTitularBean().getPersona().getNif();
							}
						}

						// Datos de Autónomo

						// Autonomo (s,n)
						if (Campo.equalsIgnoreCase("Autonomo")){
							if (detalleMatriculacion.getTitularBean().getPersona().getTipoPersona()!=null && 
									detalleMatriculacion.getTitularBean().getPersona().getTipoPersona().equals(TipoPersona.Fisica))
							{
								tcamp =   detalleMatriculacion.getTitularBean().getAutonomo() !=null
										&& detalleMatriculacion.getTitularBean().getAutonomo().equals("true")?VALOR_S:VALOR_N; 
								
							}
						}
						
						// Codigo IAE
						
						if (Campo.equalsIgnoreCase("Codigo IAE") && 
								detalleMatriculacion.getTitularBean().getCodigoIAE() !=null ){
							
						
							tcamp = detalleMatriculacion.getTitularBean().getCodigoIAE(); 
						
								
						
						}
						
						
						// CAMBIO DE DOMICILIO DEL TITULAR
						
						if (Campo.equalsIgnoreCase("Cambio domic titular")){ 
							
						
						tcamp  =null!=  detalleMatriculacion.getTitularBean().getCambioDomicilio() 
								
								&& detalleMatriculacion.getTitularBean().getCambioDomicilio().equals("true")?VALOR_S:VALOR_N; 
						
						}
						
						//Se quita comprobar si hay cambio de domicilio. Siempre se envia el domicilio este o no marcado el check de cambio de domicilio.
							// Tipo via del titular
										
								if (Campo.equalsIgnoreCase("Tipo via tit") && detalleMatriculacion.getTitularBean().getPersona()
										.getDireccion().getTipoVia().getIdTipoVia() != null){
								
									tcamp = detalleMatriculacion.getTitularBean().getPersona()
									.getDireccion().getTipoVia().getIdTipoVia();
								}
								
								// Nombre via titular
								if (Campo.equalsIgnoreCase("Nombre via tit") && detalleMatriculacion
										.getTitularBean().getPersona().getDireccion().getNombreVia() !=null){
										
									tcamp = utilConv.ajustarCamposIne(detalleMatriculacion
										.getTitularBean().getPersona().getDireccion().getNombreVia());

								}
						         // Numero del titular
								if (Campo.equalsIgnoreCase("Numero via tit") && detalleMatriculacion.getTitularBean().
									getPersona().getDireccion().getNumero() !=null){
								tcamp = detalleMatriculacion.getTitularBean().getPersona()
										.getDireccion().getNumero();

								}

								// km del titular 
								if (Campo.equalsIgnoreCase("Kilometro tit") && detalleMatriculacion.getTitularBean().getPersona()
										.getDireccion().getPuntoKilometrico() != null){
								
									tcamp = detalleMatriculacion.getTitularBean().getPersona()
										.getDireccion().getPuntoKilometrico();
								}
										
								// Hm del titular 		
								if (Campo.equalsIgnoreCase("Hectometro tit") && detalleMatriculacion.getTitularBean().getPersona()
										.getDireccion().getHm() !=null){
										
								tcamp = detalleMatriculacion.getTitularBean().getPersona()
										.getDireccion().getHm();
								
								}
		
								// bloque titular 
								
								if (Campo.equalsIgnoreCase("Bloque tit") && detalleMatriculacion.getTitularBean().getPersona()
										.getDireccion().getBloque() !=null){
										
								tcamp = detalleMatriculacion.getTitularBean().getPersona()
										.getDireccion().getBloque();
								
								}
								
							   // Portal 
							   if (Campo.equalsIgnoreCase("Portal tit") && detalleMatriculacion.getTitularBean().getPersona()
										.getDireccion().getPuerta() !=null){
										
								tcamp = detalleMatriculacion.getTitularBean().getPersona()
										.getDireccion().getPuerta();
								
								}
							   
		
								// Escalera 
								 if (Campo.equalsIgnoreCase("Escalera tit") && detalleMatriculacion.getTitularBean().getPersona()
										.getDireccion().getEscalera() !=null){
										
								tcamp = detalleMatriculacion.getTitularBean().getPersona()
										.getDireccion().getEscalera();
								
								}
								
								// Planta
								
								 if (Campo.equalsIgnoreCase("Planta tit") && detalleMatriculacion.getTitularBean().getPersona()
										.getDireccion().getPlanta() !=null){
										
								tcamp = detalleMatriculacion.getTitularBean().getPersona()
										.getDireccion().getPlanta();
								
								}
								 // Puerta 
								 if (Campo.equalsIgnoreCase("Puerta tit") && detalleMatriculacion.getTitularBean().getPersona()
										.getDireccion().getLetra() !=null){
										
								tcamp = detalleMatriculacion.getTitularBean().getPersona()
										.getDireccion().getLetra();
								
								}

							
					  // Provicia Titular 
				       
					    if (Campo.equalsIgnoreCase("Provincia tit") && detalleMatriculacion.getTitularBean().getPersona()
								.getDireccion().getMunicipio().getProvincia().getIdProvincia() !=null){
								
						tcamp = utilConv.getSiglasFromIdProvincia(detalleMatriculacion.getTitularBean().getPersona()
												.getDireccion().getMunicipio().getProvincia().getIdProvincia()).toString();
						
						}
								
						// Municipio Titular
						 if (Campo.equalsIgnoreCase("Municipio tit") && detalleMatriculacion.getTitularBean().getPersona()
								.getDireccion().getMunicipio().getProvincia().getIdProvincia() != null){
						
						tcamp = detalleMatriculacion.getTitularBean().getPersona()
								.getDireccion().getMunicipio().getProvincia().getIdProvincia() + detalleMatriculacion.getTitularBean().getPersona()
								.getDireccion().getMunicipio().getIdMunicipio();
						
						}

						// Pueblo titular 
						 if (Campo.equalsIgnoreCase("Pueblo tit") && detalleMatriculacion
								.getTitularBean().getPersona().getDireccion().getPuebloCorreos() != null){
						tcamp = detalleMatriculacion.getTitularBean().getPersona().getDireccion().getPuebloCorreos();
						
						}

						// Codigo Postal
						if (Campo.equalsIgnoreCase("Cpostal tit") && detalleMatriculacion.getTitularBean().getPersona()
								.getDireccion().getCodPostalCorreos() !=null){
						
							tcamp = detalleMatriculacion.getTitularBean().getPersona()
								.getDireccion().getCodPostalCorreos();
						
						}

						// Pais del titular 
						if (Campo.equalsIgnoreCase("Pais tit"))
						{
							tcamp = VALOR_PAIS;
						}		
						
				// **************************** DATOS DEL DOMICILIO DEL VEHICULO  ****************************************************		
				
						// Tipo via del Vehiculo
									
						if (Campo.equalsIgnoreCase("Tipo via veh")){
						
						
									if (detalleMatriculacion
											.getTramiteTraficoBean().getVehiculo()
											.getDireccionBean().getTipoVia().getIdTipoVia()!=null)
									{
										tcamp =detalleMatriculacion
												.getTramiteTraficoBean().getVehiculo()
												.getDireccionBean().getTipoVia().getIdTipoVia();
										
									}
									else 
									{
										tcamp = detalleMatriculacion
												.getTitularBean().getPersona().getDireccion().getTipoVia().getIdTipoVia()!=null?
														detalleMatriculacion
														.getTitularBean().getPersona().getDireccion().getTipoVia().getIdTipoVia():"";
									}
						
						
						}
						
						// Nombre via Vehiculo
						if (Campo.equalsIgnoreCase("Nombre via veh")){
							
						
									if (detalleMatriculacion
											.getTramiteTraficoBean().getVehiculo()
											.getDireccionBean().getNombreVia()!=null)
									{
										tcamp = utilConv.ajustarCamposIne(detalleMatriculacion
												.getTramiteTraficoBean().getVehiculo()
												.getDireccionBean().getNombreVia());
										
									}
									else 
									{
										tcamp = detalleMatriculacion
												.getTitularBean().getPersona().getDireccion().getNombreVia()!=null?
														utilConv.ajustarCamposIne(detalleMatriculacion
														.getTitularBean().getPersona().getDireccion().getNombreVia()):"";
									}
								
						}
				         // Numero del Vehiculo
						if (Campo.equalsIgnoreCase("Numero via veh")){
						
							
						
									if (detalleMatriculacion
											.getTramiteTraficoBean().getVehiculo()
											.getDireccionBean().getNumero()!=null)
									{
										tcamp =detalleMatriculacion
												.getTramiteTraficoBean().getVehiculo()
												.getDireccionBean().getNumero();
										
									}
									if (detalleMatriculacion
											.getTramiteTraficoBean().getVehiculo()
											.getDireccionBean().getTipoVia().getIdTipoVia()==null) 
									{
										tcamp = detalleMatriculacion
												.getTitularBean().getPersona().getDireccion().getNumero()!=null?
														detalleMatriculacion
														.getTitularBean().getPersona().getDireccion().getNumero():"";
									}
						
						
						}

						// km del Vehiculo 
						if (Campo.equalsIgnoreCase("Kilometro veh")){
						
									if (detalleMatriculacion
											.getTramiteTraficoBean().getVehiculo()
											.getDireccionBean().getPuntoKilometrico()!=null)
									{
										tcamp =detalleMatriculacion
												.getTramiteTraficoBean().getVehiculo()
												.getDireccionBean().getPuntoKilometrico();
										
									}
									if (detalleMatriculacion
											.getTramiteTraficoBean().getVehiculo()
											.getDireccionBean().getTipoVia().getIdTipoVia()==null) 
									{
										tcamp = detalleMatriculacion
												.getTitularBean().getPersona().getDireccion().getPuntoKilometrico()!=null?
														detalleMatriculacion
														.getTitularBean().getPersona().getDireccion().getPuntoKilometrico():"";
									}
						
							
						}
								
						// Hm del Vehiculo 		
						if (Campo.equalsIgnoreCase("Hectometro veh")){
								
								if (detalleMatriculacion
										.getTramiteTraficoBean().getVehiculo()
										.getDireccionBean().getHm()!=null)
								{
									tcamp =detalleMatriculacion
											.getTramiteTraficoBean().getVehiculo()
											.getDireccionBean().getHm();
									
								}
								if (detalleMatriculacion
										.getTramiteTraficoBean().getVehiculo()
										.getDireccionBean().getTipoVia().getIdTipoVia()==null) 
								{
									tcamp = detalleMatriculacion
											.getTitularBean().getPersona().getDireccion().getHm()!=null?
													detalleMatriculacion
													.getTitularBean().getPersona().getDireccion().getHm():"";
								}
							
						}

						// bloque Vehiculo 
						
						if (Campo.equalsIgnoreCase("Bloque veh")){
								
					
								if (detalleMatriculacion
										.getTramiteTraficoBean().getVehiculo()
										.getDireccionBean().getBloque()!=null)
								{
									tcamp =detalleMatriculacion
											.getTramiteTraficoBean().getVehiculo()
											.getDireccionBean().getBloque();
									
								}
								if (detalleMatriculacion
										.getTramiteTraficoBean().getVehiculo()
										.getDireccionBean().getTipoVia().getIdTipoVia()==null) 
								{
									tcamp = detalleMatriculacion
											.getTitularBean().getPersona().getDireccion().getBloque()!=null?
													detalleMatriculacion
													.getTitularBean().getPersona().getDireccion().getBloque():"";
								}
						
						}
						
					   // Portal 
					   if (Campo.equalsIgnoreCase("Portal veh")){
								
					
								if (detalleMatriculacion
										.getTramiteTraficoBean().getVehiculo()
										.getDireccionBean().getPuerta()!=null)
								{
									tcamp =detalleMatriculacion
											.getTramiteTraficoBean().getVehiculo()
											.getDireccionBean().getPuerta();
									
								}
								if (detalleMatriculacion
										.getTramiteTraficoBean().getVehiculo()
										.getDireccionBean().getTipoVia().getIdTipoVia()==null) 
								{
									tcamp = detalleMatriculacion
											.getTitularBean().getPersona().getDireccion().getPuerta()!=null?
													detalleMatriculacion
													.getTitularBean().getPersona().getDireccion().getPuerta():"";
								}
					   
					   
					   
					   
					   }
					   

						// Escalera 
						 if (Campo.equalsIgnoreCase("Escalera veh")){
								
								if (detalleMatriculacion
										.getTramiteTraficoBean().getVehiculo()
										.getDireccionBean().getEscalera()!=null)
								{
									tcamp =detalleMatriculacion
											.getTramiteTraficoBean().getVehiculo()
											.getDireccionBean().getEscalera();
									
								}
								if (detalleMatriculacion
										.getTramiteTraficoBean().getVehiculo()
										.getDireccionBean().getTipoVia().getIdTipoVia()==null) 
								{
									tcamp = detalleMatriculacion
											.getTitularBean().getPersona().getDireccion().getEscalera()!=null?
													detalleMatriculacion
													.getTitularBean().getPersona().getDireccion().getEscalera():"";
								}
						 
						
						}
						
						// Planta
						
						 if (Campo.equalsIgnoreCase("Planta veh") ){
			
								
								if (detalleMatriculacion
										.getTramiteTraficoBean().getVehiculo()
										.getDireccionBean().getPlanta()!=null)
								{
									tcamp =detalleMatriculacion
											.getTramiteTraficoBean().getVehiculo()
											.getDireccionBean().getPlanta();
									
								}
								if (detalleMatriculacion
										.getTramiteTraficoBean().getVehiculo()
										.getDireccionBean().getTipoVia().getIdTipoVia()==null) 
								{
									tcamp = detalleMatriculacion
											.getTitularBean().getPersona().getDireccion().getPlanta()!=null?
													detalleMatriculacion
													.getTitularBean().getPersona().getDireccion().getPlanta():"";
								}
						 
							
						}
						 // Puerta 
						 if (Campo.equalsIgnoreCase("Puerta veh")){
								
								if (detalleMatriculacion
										.getTramiteTraficoBean().getVehiculo()
										.getDireccionBean().getLetra()!=null)
								{
									tcamp =detalleMatriculacion
											.getTramiteTraficoBean().getVehiculo()
											.getDireccionBean().getLetra();
									
								}
								if (detalleMatriculacion
										.getTramiteTraficoBean().getVehiculo()
										.getDireccionBean().getTipoVia().getIdTipoVia()==null) 
								{
									tcamp = detalleMatriculacion
											.getTitularBean().getPersona().getDireccion().getLetra()!=null?
													detalleMatriculacion
													.getTitularBean().getPersona().getDireccion().getLetra():"";
								}
						 
						 
						 }

						
					  // Provicia Vehiculo 
				       
					    if (Campo.equalsIgnoreCase("Provincia veh")){
						
						if (detalleMatriculacion
								.getTramiteTraficoBean().getVehiculo()
								.getDireccionBean().getMunicipio().getProvincia().getIdProvincia()!=null)
						{
							tcamp = utilConv.getSiglasFromIdProvincia(detalleMatriculacion
									.getTramiteTraficoBean().getVehiculo()
									.getDireccionBean().getMunicipio().getProvincia().getIdProvincia());
							
						}
						else 
						{
							tcamp = detalleMatriculacion
									.getTitularBean().getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia()!=null?
											 utilConv.getSiglasFromIdProvincia(detalleMatriculacion
														.getTitularBean().getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia()).toString():"";
						}
						
						
					    }
						
								
						// Municipio Vehiculo
						 if (Campo.equalsIgnoreCase("Municipio veh")){
						
							if (detalleMatriculacion
										.getTramiteTraficoBean().getVehiculo()
										.getDireccionBean().getMunicipio().getProvincia().getIdProvincia()!=null)
								{
									tcamp = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
											.getDireccionBean().getMunicipio().getProvincia().getIdProvincia() +  detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
											.getDireccionBean().getMunicipio().getIdMunicipio() ;
									
								}
								else 
								{
									tcamp = detalleMatriculacion
											.getTitularBean().getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia()!=null?
													detalleMatriculacion.getTitularBean().getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia() +
													detalleMatriculacion.getTitularBean().getPersona().getDireccion().getMunicipio().getIdMunicipio():"";
								}
								
							 }

						// Pueblo Vehiculo 
						 if (Campo.equalsIgnoreCase("Pueblo veh")){
						
					
						if (detalleMatriculacion
								.getTramiteTraficoBean().getVehiculo()
								.getDireccionBean().getPuebloCorreos()!=null)
						{
							tcamp = detalleMatriculacion
									.getTramiteTraficoBean().getVehiculo()
									.getDireccionBean().getPuebloCorreos();
							
//							tcamp = utilConv.ajustarCamposIne(detalleMatriculacion
//									.getTramiteTraficoBean().getVehiculo()
//									.getDireccionBean().getPueblo());
							
						}
						if (detalleMatriculacion
								.getTramiteTraficoBean().getVehiculo()
								.getDireccionBean().getTipoVia().getIdTipoVia()==null) 
						{
							tcamp = detalleMatriculacion
									.getTitularBean().getPersona().getDireccion().getPuebloCorreos()!=null?detalleMatriculacion
											.getTitularBean().getPersona().getDireccion().getPuebloCorreos():"";
						}
						}

						// Codigo Postal
						if (Campo.equalsIgnoreCase("Cpostal veh")){
						
						
						
									if (detalleMatriculacion
											.getTramiteTraficoBean().getVehiculo()
											.getDireccionBean().getCodPostalCorreos()!=null)
									{
										tcamp =detalleMatriculacion
												.getTramiteTraficoBean().getVehiculo()
												.getDireccionBean().getCodPostalCorreos();
										
									}
									if (detalleMatriculacion
											.getTramiteTraficoBean().getVehiculo()
											.getDireccionBean().getTipoVia().getIdTipoVia()==null) 
									{
										tcamp = detalleMatriculacion
												.getTitularBean().getPersona().getDireccion().getCodPostalCorreos()!=null?
														detalleMatriculacion
														.getTitularBean().getPersona().getDireccion().getCodPostalCorreos():"";
									}
						
						
						}

						// Pais del Vehiculo 
						if (Campo.equalsIgnoreCase("Pais veh"))
						{
							tcamp = VALOR_PAIS;
						}		
						
						
						
				// **************************** DATOS TECNICOS ****************************************************
					
					/*	{"Num homologacion", "50"},{"Codigo ITV","9"},{"Tipo veh industria", "4"},{"Tipo veh DGT","2"},{"Procedencia", "3"},{"Marca", "23"},
						{"Fabricante","70"},{"Modelo", "22"},{"Color","2"},{"Tipo tarjeta itv", "25"},{"Variante", "25"},{"Version","35"},
						{"Potencia fiscal", "13"},{"Cilindrada","8"},{"Tara", "7"},{"MMA", "6"},{"Plazas","3"},{"Relacion potpeso", "7"},
						{"Plazas pie","3"},{"C02", "7"},{"Mom", "7"},{"Potencia neta","13"},{"Carroceria", "5"},{"Categoria EU","5"},
						{"Distancia Ejes", "4"},{"Masa Tecnica", "6"},{"Via anterior","4"},{"Via posterior", "4"},{"Codigo ECO","10"},{"Consumo", "4"},
						{"Eco innova", "1"},{"Nivel emisiones","15"},{"Reduccion ECO", "4"},{"Tipo Alimentacion","1"},{"Pais fabricacion", "1"},*/
						
						// Numero de homologacion
						if (Campo.equalsIgnoreCase("Num homologacion") && detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
								.getNumHomologacion()!=null)
						{
							tcamp = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
									.getNumHomologacion();
						}
						
						// Codigo ITV - Si tiene el valor SINCODIG se dejará en la nube el espacio en blanco de los 9 caracteres.
						
						if (Campo.equalsIgnoreCase("Codigo ITV")
								&& (detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getCodItv()!=null 
								&& !detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getCodItv().equals("")
								&& !detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getCodItv().equals(ConstantesTrafico.SIN_CODIG)
								&& !detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getCodItv().equals(ConstantesTrafico.SIN_CODIGO)))
						{
							
							tcamp = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
									.getCodItv();
						}
						
											
						
						
						// TIPO VEHICULO INDUSTRIA
						//Este campo en realidad es la clasificación. Y para rellenar lo que en el diccionario de datos llaman "Tipo vehiculo Industria" habrá que coger 
						//el valor contenido en la columna ClasificacionITV
											
						if (Campo.equalsIgnoreCase("Tipo veh industria") && detalleMatriculacion.getTramiteTraficoBean()
								.getVehiculo().getClasificacionItv()!=null)
						{
							tcamp =  detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getClasificacionItv();
						}
						
						 //  Tipo de vehiculo DGT
						
						/*
						 * Mantis - 0018598 - Alberto Barrero - MATW - Revision tipo Dgt XML y Pdf
						 * Únicamente se debe informar el campo tipo DGT en las solicitudes de matriculación de
							vehículos cuya clasificación de industria puede tener varios tipos DGT que son:
							MOTOCICLETAS, CICLOMOTORES, REMOLQUES, SEMIRREMOLQUES
						 */
						
						
						if (Campo.equalsIgnoreCase("Tipo veh DGT") && detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getTipoVehiculoBean() != null
								&& detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getTipoVehiculoBean().getTipoVehiculo() != null) {
							if (TipoVehiculoDGT.contains(detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getTipoVehiculoBean().getTipoVehiculo())) {
								tcamp = detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getTipoVehiculoBean().getTipoVehiculo();
							} else {
								tcamp = "  ";
							}
				
						}
						
						// Procedencia
						
						if (Campo.equalsIgnoreCase("Procedencia") && detalleMatriculacion.getTramiteTraficoBean()
								.getVehiculo().getProcedencia()!=null && detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getImportado()!= null 
								&& detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getImportado()!=false)
						{
							tcamp =detalleMatriculacion.getTramiteTraficoBean()
									.getVehiculo().getProcedencia();
							
							
						}
						
						// MARCA
						
						if (Campo.equalsIgnoreCase("Marca") && detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
								.getMarcaBean().getMarca()!=null )
						{
							tcamp = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
									.getMarcaBean().getMarca();
							
						}
						
						//Fabricante
	                  
						if (Campo.equalsIgnoreCase("Fabricante") && detalleMatriculacion.getTramiteTraficoBean()
								.getVehiculo().getFabricante() !=null)
						{
							tcamp = detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getFabricante();
						}
						
						// Modelo
						
						if (Campo.equalsIgnoreCase("Modelo") && detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getModelo()!=null)
						{
							tcamp = detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getModelo();
						}
						
						// Color
						
						// Mantis 18808. David Sierra: Se evita que el campo color vaya con null o vacio para que no de un error al imprimir
						if (detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getColorBean() == null 
								|| detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getColorBean().equals("")) {
							
							detalleMatriculacion.getTramiteTraficoBean().getVehiculo().setColorBean("-");
						}
						
						if (Campo.equalsIgnoreCase("Color") && detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
								.getColorBean().getValorEnum()!=null && !detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
										.getColorBean().equals("-")  )
						{
							tcamp = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
									.getColorBean().getValorEnum();
							
						}
						
						// TIPO DE LA TARJETA ITV
						
						
						if (Campo.equalsIgnoreCase("Tipo tarjeta itv") && detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
								.getTipoTarjetaItvBean().getIdTipoTarjetaItv()!=null)
						{
						
							tcamp = detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getTipoTarjetaItvBean().getIdTipoTarjetaItv();	
							
						}
						
						// Variante
						
						
						if (Campo.equalsIgnoreCase("Variante") && detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
								.getVariante()!=null)
						{
							tcamp = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
									.getVariante();
						
						}
						
						// Version
						if (Campo.equalsIgnoreCase("Version") && detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
								.getVersion()!=null)
						{
						
							tcamp = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
									.getVersion();
						}
						
					
					 
					    //Potencia fiscal  
						if (Campo.equalsIgnoreCase("Potencia fiscal") && detalleMatriculacion.getTramiteTraficoBean()
								.getVehiculo().getPotenciaFiscal()!= null)
							{
								// cambio del formato para la version 2  de MATW Ejemplo:9999999999.99
						
							BigDecimal pf =  utiles.stringToBigDecimalDosDecimales(utiles.BigDecimalToStringDosDecimales(detalleMatriculacion.getTramiteTraficoBean()
									.getVehiculo().getPotenciaFiscal(),ConstantesPDF._13));
							tcamp = null!= pf ? pf.toString():detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getPotenciaFiscal().toString();
							}
						
						
						// Cilindrada
						// Contemplar los decimales, las 5 primeras posiciones enteros, dos
						// últimas decimales  
						
						// NUEVOS FORMATO DE MATW Version 2 ejemplo: 99999.99
										
						if (Campo.equalsIgnoreCase("Cilindrada") && detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
								.getCilindrada() !=null){
							
							// Se ha comentado para que se envie sin rellenar la parte entera con .00
							//tcamp = utiles.cambiarFormatoCilindrada(detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
							//		.getCilindrada());
							
							tcamp = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
									.getCilindrada();
							
						}
						
						// Tara
						
						if (Campo.equalsIgnoreCase("Tara") && detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
								.getTara()!=null )
						{
							tcamp = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
									.getTara();
							
						}		
					
					
						//MMA 
						if (Campo.equalsIgnoreCase("MMA")&& detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
								.getPesoMma() !=null){
							
							tcamp = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
									.getPesoMma(); 
							
						}
						
						
						//Plazas 
						if (Campo.equalsIgnoreCase("Plazas")&& detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
								.getPlazas()!=null){
						
							tcamp = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
									.getPlazas().toString();	
							
						}
						
						// Carburante
						if (Campo.equalsIgnoreCase("Tipo Carburante")  && 
								detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getCarburanteBean() !=null &&
								detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getCarburanteBean().getIdCarburante() !=null &&
								!"-1".equals(detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getCarburanteBean().getIdCarburante())){

							tcamp = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
									.getCarburanteBean().getIdCarburante();	

						}

						//Relacion potpeso
						//JRG: El separar la parte entera de la parte decimal es para meter 0 a la izquierda en la parte entera.
						// Si no es necesario meter 0 por la izquierda en la parte entera, con hacer el setEscale Round_Down es suficiente.
						if (Campo.equalsIgnoreCase("Relacion potpeso") && detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getPotenciaPeso() != null
								&& detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getTipoVehiculoBean() != null
								&& !"REMO".equals(detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getTipoVehiculoBean().getTipoVehiculo())) {

							String parteEntera = "";
							String parteDecimal = "";
							BigDecimal RelPotp = detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getPotenciaPeso();
							// BigDecimal RelPotp = utiles.stringToBigDecimalDosDecimales(utiles.BigDecimalToStringDosDecimales(detalleMatriculacion.getTramiteTraficoBean()
							// .getVehiculo().getPotenciaPeso(),ConstantesPDF._7));

							// Rellena con 0 la parte decimal hasta un total de 4 digitos
							if (RelPotp != null) {
								RelPotp = RelPotp.setScale(4, BigDecimal.ROUND_DOWN);
							} else {
								detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getPotenciaPeso().setScale(4, BigDecimal.ROUND_DOWN);
							}

							if (RelPotp.toString().indexOf('.') != -1) {
								parteEntera = RelPotp.toString().substring(0, RelPotp.toString().indexOf('.'));
							} else {
								parteEntera = RelPotp.toString();
							}
							// Rellena con 0 por delante la parte entera, hasta un total de 2 digitos
							parteEntera = utiles.rellenarCeros(parteEntera, 2);
							parteDecimal = RelPotp.toString().substring(RelPotp.toString().indexOf('.') + 1);
							tcamp = parteEntera + '.' + parteDecimal;
						}

		                // PLAZAS DE PIE (SOLO PARA AUTOBUSES)
		                // SEGUN LAS VALIDACIONES PASADAS EN ARCHIVO EXCEL "VALIDACIONES GESTORES" FECHA :15/10/2013  si las plazas vienen con valor nulo 
		                // en la nube se informa como 0.
		                // PLAZAS PIE "ClasificacionNoAutobus=0"

		                if (Campo.equalsIgnoreCase("Plazas pie")){
		                	
		                	// tcamp = detalleMatriculacion.getTramiteTraficoBean()
		 					//		.getVehiculo().getNumPlazasPie().toString();

		                	 tcamp = detalleMatriculacion.getTramiteTraficoBean()
			 							.getVehiculo().getNumPlazasPie()!=null?detalleMatriculacion.getTramiteTraficoBean()
					 							.getVehiculo().getNumPlazasPie().toString():"0";
		                
		                }

						// CO2
						if (Campo.equalsIgnoreCase("C02") && (detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
								.getCo2()!=null))

						{
							tcamp = detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
									.getCo2();
						}

						// Mom Masa en orden de marcha

						if (Campo.equalsIgnoreCase("Mom") &&(detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getMom() != null))
								{
							tcamp = detalleMatriculacion.getTramiteTraficoBean().getVehiculo().
									getMom().toString();
						}

						// Potencia neta maxima

						if (Campo.equalsIgnoreCase("Potencia neta") &&(detalleMatriculacion.getTramiteTraficoBean()
								.getVehiculo().getPotenciaNeta()!=null))
								{
							BigDecimal Potn =  utiles.stringToBigDecimalTresDecimales(utiles.BigDecimalToStringTresDecimales(detalleMatriculacion.getTramiteTraficoBean()
									.getVehiculo().getPotenciaNeta(),ConstantesPDF._13));

		                	tcamp = null!= Potn ? Potn.toString():detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getPotenciaNeta().toString();
								}

						// Carroceria
						if (Campo.equalsIgnoreCase("Carroceria") &&( detalleMatriculacion.getTramiteTraficoBean()
								.getVehiculo().getCarroceriaBean().getIdCarroceria()!=null))
							{
							tcamp = detalleMatriculacion.getTramiteTraficoBean()
									.getVehiculo().getCarroceriaBean().getIdCarroceria();
							}

						// Categoria EU
						if (Campo.equalsIgnoreCase("Categoria EU") &&
								(detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getHomologacionBean()!=null && 
								detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getHomologacionBean().getIdHomologacion()!=null))
							{
							tcamp = detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getHomologacionBean().getIdHomologacion().toString(); 
						}

						//	Distancia Ejes
						 // SEGUN LAS VALIDACIONES PASADAS EN ARCHIVO EXCEL "VALIDACIONES GESTORES" FECHA :15/10/2013  si el campo Distancia Ejes viene  con valor nulo 
		                // en la nube se informa como 0.
		                // DISTANCIA EJES  VALOR (0..999999)"
						
						if (Campo.equalsIgnoreCase("Distancia Ejes") )
							{
									
							  tcamp = null!= detalleMatriculacion.getTramiteTraficoBean()
										.getVehiculo().getDistanciaEntreEjes()?detalleMatriculacion.getTramiteTraficoBean()
												.getVehiculo().getDistanciaEntreEjes().toString():"0"; 
							  
							}
						
						// Masa Tecnica
						 // SEGUN LAS VALIDACIONES PASADAS EN ARCHIVO EXCEL "VALIDACIONES GESTORES" FECHA :15/10/2013  si el campo Masa Tecnica viene  con valor nulo 
		                // en la nube se informa como 0.
		                // MTMA  VALOR 0..999999)"
						if (Campo.equalsIgnoreCase("Masa Tecnica"))
						{
								
						 tcamp = null!= detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getMtmaItv()?
								 detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getMtmaItv():"0"; 
						
						}
						
						 // SEGUN LAS VALIDACIONES PASADAS EN ARCHIVO EXCEL "VALIDACIONES GESTORES" FECHA :15/10/2013  si el campo VÍA ANTERIOR viene  con valor nulo 
		                // en la nube se informa como 0.
		                // VÍA ANTERIOR VALOR 0..999999)"
						// Via anterior
						if (Campo.equalsIgnoreCase("Via anterior") &&(detalleMatriculacion.getTramiteTraficoBean().
								getVehiculo().getViaAnterior() !=null))
						{
								
							tcamp =null!= detalleMatriculacion.getTramiteTraficoBean().
							getVehiculo().getViaAnterior()?detalleMatriculacion.getTramiteTraficoBean().
									getVehiculo().getViaAnterior().toString():"0"; 
						}
						
						// Via posterior
						if (Campo.equalsIgnoreCase("Via posterior") &&(detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getViaPosterior() !=null))
						{
							tcamp = detalleMatriculacion.getTramiteTraficoBean().
							getVehiculo().getViaPosterior().toString(); 
						}
						
						// Codigo ECO
						if (Campo.equalsIgnoreCase("Codigo ECO") &&(detalleMatriculacion.getTramiteTraficoBean()
								.getVehiculo().getCodigoEco() !=null))
						{
								
						  tcamp = detalleMatriculacion.getTramiteTraficoBean()
									.getVehiculo().getCodigoEco(); 
						}

						// Consumo
						if (Campo.equalsIgnoreCase("Consumo")){
							if (detalleMatriculacion!=null && detalleMatriculacion.getTramiteTraficoBean()!=null
								&& detalleMatriculacion.getTramiteTraficoBean().getVehiculo()!=null
								&& detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getConsumo() !=null
								&& detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getCategoriaElectricaBean()!=null)
							{
								String property = gestorPropiedades.valorPropertie(ConstantesPDF.PROPERTY_COMPLETA_CEROS_ELECTRICOS);
								if (property != null && property.equals(ConstantesPDF.VALOR_CIERTO_PROPERTY_COMPLETA_CEROS_ELECTRICOS)){
									DecimalFormat formato4 = new DecimalFormat("#0000");
									if (detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getConsumo()!=null){
										tcamp = formato4.format(detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getConsumo());
									} else {
										tcamp="";
									}
								} else {
									tcamp = detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getConsumo().toString();
								}
							} else {
								tcamp="";
							}
						}

						//	Eco innova
						if (Campo.equalsIgnoreCase("Eco innova")){
								if(null!=detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getEcoInnovacion()
									&& detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getEcoInnovacion().equalsIgnoreCase("Si")){
										tcamp = VALOR_S;
								}else{
									tcamp = VALOR_N;
								}
						}

						//Nivel emisiones
						if (Campo.equalsIgnoreCase("Nivel emisiones") &&  detalleMatriculacion.getTramiteTraficoBean()
								.getVehiculo().getNivelEmisiones()!=null)
								{
								tcamp =  detalleMatriculacion.getTramiteTraficoBean()
										.getVehiculo().getNivelEmisiones();
								}

						//Reduccion ECO
						if (Campo.equalsIgnoreCase("Reduccion ECO") && detalleMatriculacion.getTramiteTraficoBean()
								.getVehiculo().getReduccionEco()!=null)
								{

								tcamp = detalleMatriculacion.getTramiteTraficoBean()
										.getVehiculo().getReduccionEco().toString();
								}

						//Tipo Alimentacion
						if (Campo.equalsIgnoreCase("Tipo Alimentacion") && detalleMatriculacion.getTramiteTraficoBean()
								.getVehiculo().getAlimentacionBean().getIdAlimentacion()!=null)
								{
								tcamp = detalleMatriculacion.getTramiteTraficoBean()
										.getVehiculo().getAlimentacionBean().getIdAlimentacion();
								}

						//Pais fabricacion

						if (Campo.equalsIgnoreCase("Pais fabricacion") && detalleMatriculacion.getTramiteTraficoBean()
								.getVehiculo().getPaisFabricacionBean().getIdPaisFabricacion()!=null) 
								{
								tcamp = detalleMatriculacion.getTramiteTraficoBean()
										.getVehiculo().getPaisFabricacionBean().getIdPaisFabricacion();
								}

						/*
						 * NUEVOS CAMPOS datos tecnicos MANCHA PDF1 
						 * 
						 * 
						 * "Tipo Tarjeta ITV"
							"Marca Base"
							"Fabricante base"
							"Tipo base"
							"Variante base"
							"Mom base"
							"Categoria electrica"
							"Autonomia electrica"
							"Num homologacion base"
							"Version base"
						 * 
						 */

						// TIPO_TARJETA_ITV
						if (Campo.equalsIgnoreCase("Tipo")	&& detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getTipoItv()!=null){
							tcamp = detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getTipoItv();		
						}

						// Estos campos sólo debe informados en caso de que el vehiculo tenga un tipo de tarjeta itv A o D.

						if (detalleMatriculacion.getTramiteTraficoBean()
						.getVehiculo().getTipoTarjetaItvBean().getIdTipoTarjetaItv()!=null &&
								("A".equals(detalleMatriculacion
										.getTramiteTraficoBean().getVehiculo().getTipoTarjetaItvBean()
										.getIdTipoTarjetaItv()
										.substring(0, 1) ) || "D".equals(detalleMatriculacion.getTramiteTraficoBean().getVehiculo()
												.getTipoTarjetaItvBean().getIdTipoTarjetaItv()
												.substring(0, 1))))
						 {

							// MARCA_BASE
							if (Campo.equalsIgnoreCase("Marca Base") && detalleMatriculacion.getTramiteTraficoBean()
									.getVehiculo().getMarcaBaseBean().getMarca()!=null)
									{

								        tcamp = detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getMarcaBaseBean().getMarca();
									}

							// FABRICANTE_BASE
							if (Campo.equalsIgnoreCase("Fabricante base") && detalleMatriculacion.getTramiteTraficoBean()
									.getVehiculo().getFabricanteBase()!=null) 
							{
								tcamp = detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getFabricanteBase();
							}

							// TIPO_BASE
							if (Campo.equalsIgnoreCase("Tipo base") && detalleMatriculacion.getTramiteTraficoBean()
									.getVehiculo().getTipoItvBase()!=null) 
							{
								tcamp = detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getTipoItvBase();
							}

							// VARIANTE_BASE
							if (Campo.equalsIgnoreCase("Variante base")&& detalleMatriculacion.getTramiteTraficoBean()
									.getVehiculo().getVarianteBase()!=null) 
							{
								tcamp = detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getVarianteBase();
							}

							// VERSION_BASE
							if (Campo.equalsIgnoreCase("Version base") && detalleMatriculacion.getTramiteTraficoBean()
									.getVehiculo().getVersionBase()!=null) 
							{
								tcamp = detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getVersionBase();
							}
							
							// NUMERO_HOMOLOGACION_BASE
							if (Campo.equalsIgnoreCase("Num homologacion base") && detalleMatriculacion.getTramiteTraficoBean()
									.getVehiculo().getNumHomologacionBase()!=null) 
							{
								tcamp = detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getNumHomologacionBase();
							}

							// MOM_BASE
							if (Campo.equalsIgnoreCase("Mom base") && detalleMatriculacion.getTramiteTraficoBean()
									.getVehiculo().getMomBase()!=null) 
							{
								tcamp = detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getMomBase().toString();
							}

							}

						// CATEGORIA_ELECTRICA
						if (Campo.equalsIgnoreCase("Categoria electrica") && detalleMatriculacion.getTramiteTraficoBean()
								.getVehiculo().getCategoriaElectricaBean().getIdCategoriaElectrica()!=null) 
						{
							tcamp = detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getCategoriaElectricaBean().getIdCategoriaElectrica();
						}

						// AUTONOMIA_ELECTRICA
						if (Campo.equalsIgnoreCase("Autonomia electrica") && detalleMatriculacion.getTramiteTraficoBean()
								.getVehiculo().getAutonomiaElectrica()!=null) 
						{
							String autonomiaElectrica =detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getAutonomiaElectrica()!=null?detalleMatriculacion.getTramiteTraficoBean().getVehiculo().getAutonomiaElectrica().toString():"";
							String property = gestorPropiedades.valorPropertie(ConstantesPDF.PROPERTY_COMPLETA_CEROS_ELECTRICOS);
							if (property != null && property.equals(ConstantesPDF.VALOR_CIERTO_PROPERTY_COMPLETA_CEROS_ELECTRICOS)){
								DecimalFormat formato4 = new DecimalFormat("#0000");
								if (autonomiaElectrica!=null && !autonomiaElectrica.isEmpty()){
									autonomiaElectrica = formato4.format(new BigDecimal(autonomiaElectrica));
								}
							}
							tcamp = autonomiaElectrica;
						}

				// **************************** DATOS DEL ARRENDATARIO ****************************************************

						// DOI del Arrendatario
						if (Campo.equalsIgnoreCase("DOI arr") && detalleMatriculacion.getArrendatarioBean().getPersona().getNif()!=null){
							tcamp = detalleMatriculacion.getArrendatarioBean().getPersona().getNif();
						}

						//Razon social arrendatario
						if (Campo.equalsIgnoreCase("Razon social arr")){
							if(detalleMatriculacion.getArrendatarioBean().getPersona().getTipoPersona()!= null 
								&& detalleMatriculacion.getArrendatarioBean().getPersona().getTipoPersona().equals(TipoPersona.Juridica)){
									if(null!= detalleMatriculacion.getArrendatarioBean().getPersona().getApellido1RazonSocial()){
										tcamp = detalleMatriculacion.getArrendatarioBean().getPersona().getApellido1RazonSocial();
									}
							}
						}

						// Nombre del Arrendatario (Persona fisica)
						if (Campo.equalsIgnoreCase("Nombre arr")) {
							if(detalleMatriculacion.getArrendatarioBean().getPersona().getTipoPersona()!= null 
								&& detalleMatriculacion.getArrendatarioBean().getPersona().getTipoPersona().equals(TipoPersona.Fisica)) {
									if(null!= detalleMatriculacion.getArrendatarioBean().getPersona().getNombre()){
										tcamp = detalleMatriculacion.getArrendatarioBean().getPersona().getNombre();
									}
							}
						}

						// Apellido 1 Arrendatario (Persona fisica)
						if (Campo.equalsIgnoreCase("Apellido1 arr")){
							if(detalleMatriculacion.getArrendatarioBean().getPersona().getTipoPersona()!= null 
								&& detalleMatriculacion.getArrendatarioBean().getPersona().getTipoPersona().equals(TipoPersona.Fisica)) {
									if(null!= detalleMatriculacion.getArrendatarioBean().getPersona().getApellido1RazonSocial()){
										tcamp = detalleMatriculacion.getArrendatarioBean().getPersona().getApellido1RazonSocial();
									}

						   }
						}

						// Apellido 2 Arrendatario (Persona fisica)
							if (Campo.equalsIgnoreCase("Apellido2 arr")){
								if(detalleMatriculacion.getArrendatarioBean().getPersona().getTipoPersona()!= null && detalleMatriculacion.getArrendatarioBean().getPersona().getTipoPersona().equals(TipoPersona.Fisica)) {
									if(null!= detalleMatriculacion.getArrendatarioBean().getPersona()
									.getApellido2()){
										tcamp = detalleMatriculacion.getArrendatarioBean().getPersona().getApellido2();
									}
								}
							}

						// Fecha de nacimiento del  Arrendatario
						try {
							if (Campo.equalsIgnoreCase("Fecha nacimiento arr")){
								if (detalleMatriculacion.getArrendatarioBean().getPersona().getFechaNacimientoBean() != null
									 && detalleMatriculacion.getArrendatarioBean()
									.getPersona().getTipoPersona()!= null && detalleMatriculacion.getArrendatarioBean()
									.getPersona().getTipoPersona().equals(TipoPersona.Fisica)){
										if(null!= formato.format(detalleMatriculacion.getArrendatarioBean().getPersona().getFechaNacimientoBean().getFecha()).toString()){
												tcamp =formato.format(detalleMatriculacion.getArrendatarioBean().getPersona().getFechaNacimientoBean().getFecha()).toString();
										}
								 }
							}
						} catch (ParseException e) {
							tcamp = "";
						}

						// Sexo del  Arrendatario
						if (Campo.equalsIgnoreCase("Sexo arr")&& detalleMatriculacion.getArrendatarioBean().getPersona().getSexo() !=null){
							tcamp = detalleMatriculacion.getArrendatarioBean().getPersona().getSexo();

						}

						// NIF del representante del Arrendatario
						if (Campo.equalsIgnoreCase("DOI Reprent arr") && detalleMatriculacion.getRepresentanteArrendatarioBean().getPersona().getNif()!=null){
							tcamp = detalleMatriculacion.getRepresentanteArrendatarioBean().getPersona().getNif();
						}

						// Fecha inicial Renting 
						try {

							if ( Campo.equalsIgnoreCase("Fech ini Renting") &&
									detalleMatriculacion.getArrendatarioBean().getFechaInicio() != null
									&& !detalleMatriculacion.getArrendatarioBean()
									.getFechaInicio().isfechaNula()){

								tcamp =formato.format(detalleMatriculacion.getArrendatarioBean().getFechaInicio().getFecha());
							}
						} catch (ParseException e) {
							tcamp = "";
						}

							// Fecha fin Renting 

						try {
							if (Campo.equalsIgnoreCase("Fech fin Renting") && detalleMatriculacion.getArrendatarioBean().getFechaFin() != null
									&& !detalleMatriculacion.getArrendatarioBean()
									.getFechaInicio().isfechaNula()){

								tcamp =formato.format(detalleMatriculacion.getArrendatarioBean().getFechaFin().getFecha());
							}
						} catch (ParseException e) {
							tcamp = "";
						}

						// Hora inicio Renting
						if (Campo.equalsIgnoreCase("Hora ini Renting") && detalleMatriculacion.getArrendatarioBean()
								.getHoraInicio()!=null){
						tcamp =  detalleMatriculacion
								.getArrendatarioBean().getHoraInicio().replaceAll(":", "");
						}

						// Hora fin Renting
						if (Campo.equalsIgnoreCase("Hora fin Renting") && detalleMatriculacion.getArrendatarioBean()
								.getHoraFin() != null){

							tcamp =  detalleMatriculacion
									.getArrendatarioBean().getHoraFin().replaceAll(":", "");
						}

						// CAMBIO DE DOMICILIO DEL Arrendatario

						if (Campo.equalsIgnoreCase("Cambio domic arr")){
							tcamp = null!= detalleMatriculacion.getArrendatarioBean().getCambioDomicilio() 
									&& detalleMatriculacion.getArrendatarioBean().getCambioDomicilio().equals("true")?VALOR_S:VALOR_N;	
							
							//Si no tiene relleno el Arrendatario deja el campo cambio de domicilio vacio
							if(detalleMatriculacion.getArrendatarioBean().getPersona().getNif()==null){
								tcamp = "";
							}
						}

						// Si cambio_domicilio=SI se va a considerar la direccion del titular en la nube. En caso contrario no se considera.

//						if (detalleMatriculacion.getArrendatarioBean().getCambioDomicilio()!=null 
//								&& detalleMatriculacion.getArrendatarioBean().getCambioDomicilio().equals("true")){
								// Tipo via del Arrendatario 
								if (Campo.equalsIgnoreCase("Tipo via arr") && detalleMatriculacion.getArrendatarioBean().getPersona()
									.getDireccion().getTipoVia().getIdTipoVia() != null){

										tcamp = detalleMatriculacion.getArrendatarioBean().getPersona()
												.getDireccion().getTipoVia().getIdTipoVia();
								}

								// Nombre via Arrendatario
								if (Campo.equalsIgnoreCase("Nombre via arr") && detalleMatriculacion
										.getArrendatarioBean().getPersona().getDireccion().getNombreVia() !=null){

									tcamp = utilesConversiones.ajustarCamposIne(detalleMatriculacion
										.getArrendatarioBean().getPersona().getDireccion().getNombreVia());
								}
						         // Numero del Arrendatario
								if (Campo.equalsIgnoreCase("Numero via arr") && detalleMatriculacion.getArrendatarioBean().
									getPersona().getDireccion().getNumero() !=null){
								tcamp = detalleMatriculacion.getArrendatarioBean().getPersona()
										.getDireccion().getNumero();
								}

								// km del Arrendatario 
								if (Campo.equalsIgnoreCase("Kilometro arr") && detalleMatriculacion.getArrendatarioBean().getPersona()
										.getDireccion().getPuntoKilometrico() != null){
								
									tcamp = detalleMatriculacion.getArrendatarioBean().getPersona()
										.getDireccion().getPuntoKilometrico();
								}

								// Hm del Arrendatario 		
								if (Campo.equalsIgnoreCase("Hectometro arr") && detalleMatriculacion.getArrendatarioBean().getPersona()
										.getDireccion().getHm() !=null){
										
								tcamp = detalleMatriculacion.getArrendatarioBean().getPersona()
										.getDireccion().getHm();

								}

								// bloque Arrendatario 

								if (Campo.equalsIgnoreCase("Bloque arr") && detalleMatriculacion.getArrendatarioBean().getPersona()
										.getDireccion().getBloque() !=null){
										
								tcamp = detalleMatriculacion.getArrendatarioBean().getPersona()
										.getDireccion().getBloque();
								}

							   // Portal 
							   if (Campo.equalsIgnoreCase("Portal arr") && detalleMatriculacion.getArrendatarioBean().getPersona()
										.getDireccion().getPuerta() !=null){
										
								tcamp = detalleMatriculacion.getArrendatarioBean().getPersona()
										.getDireccion().getPuerta();

								}
							   

								// Escalera 
								 if (Campo.equalsIgnoreCase("Escalera arr") && detalleMatriculacion.getArrendatarioBean().getPersona()
										.getDireccion().getEscalera() !=null){
										
								tcamp = detalleMatriculacion.getArrendatarioBean().getPersona()
										.getDireccion().getEscalera();
								
								}
								
								// Planta
								
								 if (Campo.equalsIgnoreCase("Planta arr") && detalleMatriculacion.getArrendatarioBean().getPersona()
										.getDireccion().getPlanta() !=null){
										
								tcamp = detalleMatriculacion.getArrendatarioBean().getPersona()
										.getDireccion().getPlanta();
								
								}
								 // Puerta 
								 if (Campo.equalsIgnoreCase("Puerta arr") && detalleMatriculacion.getArrendatarioBean().getPersona()
										.getDireccion().getLetra() !=null){
										
								tcamp = detalleMatriculacion.getArrendatarioBean().getPersona()
										.getDireccion().getLetra();
								
								}
		
								
							  // Provicia Arrendatario 
						       
							    if (Campo.equalsIgnoreCase("Provincia arr") && detalleMatriculacion.getArrendatarioBean().getPersona()
										.getDireccion().getMunicipio().getProvincia().getIdProvincia() !=null){
										
								tcamp = utilConv.getSiglasFromIdProvincia(detalleMatriculacion.getArrendatarioBean().getPersona()
														.getDireccion().getMunicipio().getProvincia().getIdProvincia()).toString();
								
								}
										
								// Municipio Arrendatario
								 if (Campo.equalsIgnoreCase("Municipio arr") && detalleMatriculacion.getArrendatarioBean().getPersona()
										.getDireccion().getMunicipio().getProvincia().getIdProvincia() != null){
								
								tcamp = detalleMatriculacion.getArrendatarioBean().getPersona()
										.getDireccion().getMunicipio().getProvincia().getIdProvincia() + detalleMatriculacion.getArrendatarioBean().getPersona()
										.getDireccion().getMunicipio().getIdMunicipio();
								
								}
		
								// Pueblo Arrendatario 
								 if (Campo.equalsIgnoreCase("Pueblo arr") ){
								
									tcamp =null!= detalleMatriculacion.getArrendatarioBean()
										.getPersona().getNif() && detalleMatriculacion
										.getArrendatarioBean().getPersona().getDireccion()
										.getPuebloCorreos()!=null ? detalleMatriculacion
										.getArrendatarioBean().getPersona().getDireccion()
										.getPuebloCorreos():"";
								
								
								}
		
								// Codigo Postal
								if (Campo.equalsIgnoreCase("Cpostal arr") && detalleMatriculacion.getArrendatarioBean().getPersona()
										.getDireccion().getCodPostalCorreos() !=null){
								
									tcamp = detalleMatriculacion.getArrendatarioBean().getPersona()
										.getDireccion().getCodPostalCorreos();
								
								}
		
								// Pais del Arrendatario 
								if (Campo.equalsIgnoreCase("Pais arr") && detalleMatriculacion.getArrendatarioBean().getPersona().getNif()!=null)
								{
									tcamp = VALOR_PAIS;
								}		
								
//					}
				
				// ********************************** DATOS DEL CONDUCTOR HABITUAL **************************************
				// DOI DEL CONDUCTOR HABITUAL
				
				//{"DOI ch","9"},{"Fecha fin ch", "8"},	{"Hora fin ch", "8"},{"Cambio dom ch","1"},
				if (Campo.equalsIgnoreCase("DOI ch"))
				{
					if (detalleMatriculacion.getConductorHabitualBean().getPersona().getNif()!=null){
						tcamp = detalleMatriculacion.getConductorHabitualBean().getPersona().getNif();
					}
				}
				
				// Fecha fin ch

				
				  try {
						
						if (Campo.equalsIgnoreCase("Fecha fin ch") && detalleMatriculacion.getConductorHabitualBean().getFechaFin() != null
								&& !detalleMatriculacion.getConductorHabitualBean()
								.getFechaFin().isfechaNula()){
						
							if(formato.format(detalleMatriculacion.getConductorHabitualBean().getFechaFin().getFecha())!=null 
									&& detalleMatriculacion.getConductorHabitualBean().getPersona().getNif()!=null){
								tcamp = formato.format(detalleMatriculacion.getConductorHabitualBean().getFechaFin().getFecha());
							}
						}
							
					}
					catch (ParseException e) {
							tcamp = "";
					}
				  
				  
				//{"Hora fin ch", "8"}
				
				if (Campo.equalsIgnoreCase("Hora fin ch") && null != detalleMatriculacion.getConductorHabitualBean().getHoraFin()){
					tcamp = detalleMatriculacion.getConductorHabitualBean().getHoraFin().replaceAll(":", "");
				}
				
				// {"Cambio dom ch","1"},
				if (Campo.equalsIgnoreCase("Cambio dom ch"))
				{
					
					tcamp = null != detalleMatriculacion.getConductorHabitualBean().getCambioDomicilio()
							&& detalleMatriculacion.getConductorHabitualBean().getCambioDomicilio()
									.equals("true") && detalleMatriculacion.getConductorHabitualBean()
									.getPersona().getNif()!=null?VALOR_S :VALOR_N;
							
							//Si no tiene relleno el CH deja el campo cambio de domicilio vacio
							if(detalleMatriculacion.getConductorHabitualBean().getPersona().getNif()==null){
								tcamp = "";
							}
				}
				
				//{"Tipo via ch", "5"},
				if (Campo.equalsIgnoreCase("Tipo via ch"))
				{
				tcamp = null!= detalleMatriculacion.getConductorHabitualBean()
						.getPersona().getNif() && detalleMatriculacion.getConductorHabitualBean().getPersona()
						.getDireccion().getTipoVia().getIdTipoVia()!=null?detalleMatriculacion.getConductorHabitualBean()
								.getPersona().getDireccion().getTipoVia().getIdTipoVia():"";
				}
				
				//{"Nombre via ch", "114"},
				if (Campo.equalsIgnoreCase("Nombre via ch") ){
				
					tcamp = null!= detalleMatriculacion.getConductorHabitualBean()
						.getPersona().getNif() && detalleMatriculacion.getConductorHabitualBean().getPersona().getDireccion()
						.getNombreVia()!=null ? utilesConversiones.ajustarCamposIne(detalleMatriculacion.getConductorHabitualBean().getPersona().getDireccion()
						.getNombreVia()):"";
				}
				
				//{"Numero via ch","5"},
				if (Campo.equalsIgnoreCase("Numero via ch")  ){
					
					tcamp =  null!= detalleMatriculacion.getConductorHabitualBean()
							.getPersona().getNif()? detalleMatriculacion.getConductorHabitualBean().getPersona()
							.getDireccion().getNumero():"";
				
				}
				
				
				if (Campo.equalsIgnoreCase("Kilometro ch") ){
				
				tcamp = null!= detalleMatriculacion.getConductorHabitualBean()
						.getPersona().getNif() && detalleMatriculacion.getConductorHabitualBean().getPersona()
						.getDireccion().getPuntoKilometrico()!=null? detalleMatriculacion.getConductorHabitualBean().getPersona()
						.getDireccion().getPuntoKilometrico():"";
				}
				
				if (Campo.equalsIgnoreCase("Hectometro ch") ){
				
					tcamp = null != detalleMatriculacion.getConductorHabitualBean().getPersona()
							.getDireccion().getHm()?detalleMatriculacion.getConductorHabitualBean().getPersona()
						.getDireccion().getHm():"";
				}
				
				
				if (Campo.equalsIgnoreCase("Bloque ch") ){
				tcamp =  null!= detalleMatriculacion.getConductorHabitualBean()
						.getPersona().getNif() && detalleMatriculacion.getConductorHabitualBean().getPersona()
						.getDireccion().getBloque()!=null?detalleMatriculacion.getConductorHabitualBean().getPersona()
						.getDireccion().getBloque():"";
				}
				
				if (Campo.equalsIgnoreCase("Portal ch") ){
				
				tcamp =  null!= detalleMatriculacion.getConductorHabitualBean()
						.getPersona().getNif()  && detalleMatriculacion.getConductorHabitualBean().getPersona()
						.getDireccion().getPuerta()!=null?detalleMatriculacion.getConductorHabitualBean().getPersona()
						.getDireccion().getPuerta():"";
				}
				
				if (Campo.equalsIgnoreCase("Escalera ch") ){
				
				tcamp =  null!= detalleMatriculacion.getConductorHabitualBean()
						.getPersona().getNif() && detalleMatriculacion.getConductorHabitualBean().getPersona()
						.getDireccion().getEscalera()!=null ?detalleMatriculacion.getConductorHabitualBean().getPersona()
						.getDireccion().getEscalera():"";
				}
				
				if (Campo.equalsIgnoreCase("Planta ch") ){
				
					tcamp =  null!= detalleMatriculacion.getConductorHabitualBean()
							.getPersona().getNif() && detalleMatriculacion.getConductorHabitualBean().getPersona()
							.getDireccion().getPlanta()!=null?detalleMatriculacion.getConductorHabitualBean().getPersona()
						.getDireccion().getPlanta():"";
				}
				
				if (Campo.equalsIgnoreCase("Puerta ch") ){
				
					tcamp =  null!= detalleMatriculacion.getConductorHabitualBean()
							.getPersona().getNif()  && detalleMatriculacion.getConductorHabitualBean().getPersona()
							.getDireccion().getLetra()!=null ?detalleMatriculacion.getConductorHabitualBean().getPersona()
						.getDireccion().getLetra():"";
				}
				
				if (Campo.equalsIgnoreCase("Provincia ch")){
				tcamp = null!= detalleMatriculacion.getConductorHabitualBean().getPersona()
						.getDireccion().getMunicipio().getProvincia().getIdProvincia() && 
						null!= detalleMatriculacion.getConductorHabitualBean().getPersona().getNif()? 
								utilConv.getSiglasFromIdProvincia(detalleMatriculacion.getConductorHabitualBean().getPersona()
										.getDireccion().getMunicipio().getProvincia().getIdProvincia()) :"";
				}
				
				if (Campo.equalsIgnoreCase("Municipio ch")  && detalleMatriculacion.getConductorHabitualBean().getPersona()
						.getDireccion().getMunicipio().getProvincia().getIdProvincia() != null ){
							
								tcamp =  null!= detalleMatriculacion.getConductorHabitualBean()
										.getPersona().getNif()? detalleMatriculacion.getConductorHabitualBean().getPersona()
										.getDireccion().getMunicipio().getProvincia()
										.getIdProvincia() +  detalleMatriculacion.getConductorHabitualBean().getPersona()
										.getDireccion().getMunicipio().getIdMunicipio():"";
				}			
				
				if (Campo.equalsIgnoreCase("Pueblo ch") ){
								tcamp =null!= detalleMatriculacion.getConductorHabitualBean()
										.getPersona().getNif() && detalleMatriculacion
										.getConductorHabitualBean().getPersona().getDireccion()
										.getPuebloCorreos()!=null ? detalleMatriculacion
										.getConductorHabitualBean().getPersona().getDireccion()
										.getPuebloCorreos():"";
				}
				//{"Cpostal ch", "5"},
				if (Campo.equalsIgnoreCase("Cpostal ch") ){
				
						tcamp = null!= detalleMatriculacion.getConductorHabitualBean()
								.getPersona().getNif() && detalleMatriculacion.getConductorHabitualBean().getPersona()
								.getDireccion().getCodPostalCorreos()!=null ?detalleMatriculacion.getConductorHabitualBean().getPersona()
						.getDireccion().getCodPostalCorreos():"";
				}
				
				if (Campo.equalsIgnoreCase("Pais ch")){
			    
					tcamp = null!= detalleMatriculacion.getConductorHabitualBean()
							.getPersona().getNif()? VALOR_PAIS:"";
				}
//			}
				
				// Datos Tutor 
				//	"DOI Tutor". Se pinta el dni del representante en caso de que sea tutor.
				if(Campo.equalsIgnoreCase("DOI Tutor") && detalleMatriculacion.getRepresentanteTitularBean().getIdMotivoTutela()!=null 
						&& detalleMatriculacion.getRepresentanteTitularBean().getConceptoRepre().getValorEnum().equals(ConceptoTutela.Tutela.getValorEnum())){
							tcamp = detalleMatriculacion.getRepresentanteTitularBean().getPersona().getNif();
				} 
				
					// TIPO DE TUTELA	
				  if (Campo.equalsIgnoreCase("Tipo tutela") && detalleMatriculacion.getRepresentanteTitularBean().getIdMotivoTutela()!=null 
						  && detalleMatriculacion.getRepresentanteTitularBean().getConceptoRepre().getValorEnum().equals(ConceptoTutela.Tutela.getValorEnum())){
					  		tcamp = detalleMatriculacion.getRepresentanteTitularBean().getIdMotivoTutela().getValorEnum();
				   }
				
					
				// ************************************* IMPUESTOS **************************************************************
				// 	{"CEM","8"},{"CEMA", "8"},{"Exencion CEM", "10"},{"Justificado IVTM","1"}
				// CEM && detalleMatriculacion.getTramiteTraficoBean().getCemIedtm()!=null
				if (Campo.equalsIgnoreCase("CEM")){
					if(detalleMatriculacion.getTramiteTraficoBean().getExentoCem()!=null && 
							detalleMatriculacion.getTramiteTraficoBean().getExentoCem().equals(Boolean.TRUE.toString())){
						if (detalleMatriculacion.getTramiteTraficoBean().getCemIedtm()!=null 
								&& !detalleMatriculacion.getTramiteTraficoBean().getCemIedtm().equals("")){
							tcamp =  detalleMatriculacion.getTramiteTraficoBean().getCemIedtm();
						}else{
							tcamp = SIN_CEM;
						}
						
					}else{
						if (detalleMatriculacion.getTramiteTraficoBean().getCemIedtm()!=null){
							tcamp =  detalleMatriculacion.getTramiteTraficoBean().getCemIedtm();
						}else{
							tcamp = SIN_CEM;
						}
					}
				}
			
				// CEMA
				if (Campo.equalsIgnoreCase("CEMA")  && detalleMatriculacion.getTramiteTraficoBean().getCema()!=null){
					tcamp = detalleMatriculacion.getTramiteTraficoBean().getCema();
				}
				
				// EXENCION CEM
				if (Campo.equalsIgnoreCase("Exencion CEM")
						&& detalleMatriculacion.getTramiteTraficoBean().getNumRegIedtm() != null
						&& detalleMatriculacion.getTramiteTraficoBean().getNumRegIedtm().getValorEnum() !=null){
							tcamp = detalleMatriculacion.getTramiteTraficoBean().getNumRegIedtm().getValorEnum();
				}
				
				 
				 //JUSTIFICADO IVTM (NUEVO CAMPO EN MATW VERSION 2)
		
				if (Campo.equalsIgnoreCase("Justificado IVTM")){
					
					
					tcamp = null!= detalleMatriculacion.getJustificado_IVTM()
							&&  detalleMatriculacion.getJustificado_IVTM().equalsIgnoreCase("true")?VALOR_S: 
			        	VALOR_N;	
				}
				
				
				
				
				
				
				
				
				
				
				
				return tcamp;
			}
			
		
	
	
	
	
	/**
	 * Método que obtendrá el String para la nube de transmisión.
	 * 
	 * @param tramite
	 * @return
	 */
/**	protected String obtenerNubeTransmision(
			TramiteTraficoTransmisionBean tramite) {
		SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
		StringBuffer line = new StringBuffer();
		String campo = "";
		line.append(ConstantesPDF.VERSION_TRANSMISION);
		line.append(ConstantesPDF.PROGRAMA_TRANSMISION);

		campo = tramite.getTramiteTraficoBean().getTasa().getCodigoTasa();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_TASA));
		} else if (campo.equals("EXENTO")) {
			line.append(BasicText.changeSize(campo, TAM_TASA));
		} else {
			line.append(BasicText.changeSize(campo, TAM_TASA, '0', false));
		}

		campo = tramite.getTramiteTraficoBean().getVehiculo().getMatricula();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_MATRICULA));
		} else {
			campo = utiles.cambiaFormatoMatricula(campo);
			line.append(BasicText.changeSize(campo, TAM_MATRICULA));
		}

		try {
			if (tramite.getTramiteTraficoBean().getVehiculo()
					.getFechaMatriculacion() != null
					&& !tramite.getTramiteTraficoBean().getVehiculo()
							.getFechaMatriculacion().isfechaNula()) {
				campo = formato.format(tramite.getTramiteTraficoBean()
						.getVehiculo().getFechaMatriculacion().getTimestamp());
			} else {
				campo = null;
			}
		} catch (ParseException e) {
			campo = null;
		}
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_FECHA_MATRICULACION));
		} else {
			line.append(BasicText.changeSize(campo, TAM_FECHA_MATRICULACION,
					'0', false));
		}

		try {
			if (tramite.getTramiteTraficoBean().getFechaPresentacion() != null
					&& !tramite.getTramiteTraficoBean().getFechaPresentacion()
							.isfechaNula()) {
				campo = formato.format(tramite.getTramiteTraficoBean()
						.getFechaPresentacion().getTimestamp());
			} else {
				campo = null;
			}
		} catch (ParseException e) {
			campo = null;
		}
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_FECHA_PRESENTACION));
		} else {
			line.append(BasicText.changeSize(campo, TAM_FECHA_PRESENTACION,
					'0', false));
		}

		campo = tramite.getTramiteTraficoBean().getJefaturaTrafico()
				.getJefatura();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_JEFATURA_TRAFICO));
		} else {
			line.append(BasicText.changeSize(campo, TAM_JEFATURA_TRAFICO));
		}

		campo = tramite.getTransmitenteBean().getPersona().getNif();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_DNI_TRANSMITENTE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_DNI_TRANSMITENTE));
		}

		campo = tramite.getAdquirienteBean().getPersona().getNif();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_DNI_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_DNI_ADQUIRENTE));
		}

		try {
			if (tramite.getAdquirienteBean().getPersona()
					.getFechaNacimientoBean() != null
					&& !tramite.getAdquirienteBean().getPersona()
							.getFechaNacimientoBean().isfechaNula()) {
				campo = formato.format(tramite.getAdquirienteBean()
						.getPersona().getFechaNacimientoBean().getTimestamp());
			} else {
				campo = null;
			}
		} catch (ParseException e) {
			campo = null;
		}
		if (campo == null) {
			line.append(BasicText.changeSize("",
					TAM_FECHA_NACIMIENTO_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo,
					TAM_FECHA_NACIMIENTO_ADQUIRENTE, '0', false));
		}

		campo = null != tramite.getAdquirienteBean().getPersona()
				.getTipoPersona() ? tramite.getAdquirienteBean().getPersona()
				.getTipoPersona().getValorEnum() : null;
		if (campo == null || campo.equals(TipoPersona.Juridica.getValorEnum())) {
			campo = tramite.getAdquirienteBean().getPersona()
					.getApellido1RazonSocial();
			if (campo == null) {
				line.append(BasicText.changeSize("",
						TAM_PRIMER_APELLIDO_RAZON_SOCIAL));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_PRIMER_APELLIDO_RAZON_SOCIAL));
			}
		} else {
			campo = tramite.getAdquirienteBean().getPersona()
					.getApellido1RazonSocial();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_PRIMER_APELLIDO));
			} else {
				line.append(BasicText.changeSize(campo, TAM_PRIMER_APELLIDO));
			}

			campo = tramite.getAdquirienteBean().getPersona().getApellido2();
			if (campo == null) {
				line.append(BasicText.changeSize("",
						TAM_SEGUNDO_APELLIDO_ADQUIRENTE));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_SEGUNDO_APELLIDO_ADQUIRENTE));
			}

			campo = tramite.getAdquirienteBean().getPersona().getNombre();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_NOMBRE_ADQUIRENTE));
			} else {
				line.append(BasicText.changeSize(campo, TAM_NOMBRE_ADQUIRENTE));
			}
		}

		campo = tramite.getAdquirienteBean().getPersona().getSexo();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_SEXO_ADQUIRENTE));
		} else if (campo.equals(SEXO_HEMBRA)) {
			line.append(BasicText.changeSize(SEXO_MUJER, TAM_SEXO_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_SEXO_ADQUIRENTE));
		}

		//ABRE DETALLE DEL ACQUIRIENTE
		//***************************************************************
		String tipoVia = "";
		try {
			if (tramite.getAdquirienteBean().getPersona().getDireccion()
					.getTipoVia().getIdTipoVia() != null) {
//				// Realizamos la conversión al tipo de vía DGT
//				String tipoViaINE = tramite.getAdquirienteBean().getPersona()
//						.getDireccion().getTipoVia().getIdTipoVia();
//				String tipoViaDGT = utilesConversiones
//						.getIdTipoViaDGTFromIdTipoVia(tipoViaINE);
//				String tipoViaConvertido = utilesConversiones
//						.getIdTipoViaDGTFromNumeroViaDGT(tipoViaDGT);
//				tipoVia = !"".equals(tipoViaConvertido)
//						&& !" ".equals(tipoViaConvertido)
//						&& !"-1".equals(tipoViaConvertido)
//						&& !"OT".equals(tipoViaConvertido) ? (tipoViaConvertido
//						.length() == 2 ? tipoViaConvertido + " "
//						: tipoViaConvertido + " ") : "CL";
				tipoVia=tramite.getAdquirienteBean().getPersona().getDireccion().getTipoVia().getIdTipoVia();
			}
		} catch (Throwable e) {
			tipoVia = "";
		}
		
		line.append(BasicText.changeSize(tipoVia, TAM_TIPO_VIA_ADQUIRIENTE));
		
		String nombreVia="";
		if (tramite.getAdquirienteBean().getPersona().getDireccion()
				.getNombreVia() != null) {
			nombreVia = nombreVia
					+ UtilesCadenaCaracteres
							.quitarCaracteresExtranios(utilesConversiones
									.ajustarCamposIne(tramite
											.getAdquirienteBean().getPersona()
											.getDireccion().getNombreVia()))
					+ "";
		}

		// TODO: Analizar si se debe centralizar esto en una sola función como
		// quitarCaracteresExtranios
		// Se eliminan espacios dobles
		nombreVia = nombreVia.replaceAll("\\s\\s", " ");
		nombreVia = nombreVia.replaceAll(",", "");
		nombreVia = nombreVia.replaceAll("º", "");
		nombreVia = nombreVia.replaceAll("ª", "");
		nombreVia = nombreVia.replaceAll("/", "");
		

		if (nombreVia.length() > TAM_NOMBRE_VIA_ADQUIRIENTE) {
			nombreVia = nombreVia.substring(0, TAM_NOMBRE_VIA_ADQUIRIENTE);
		}

		line.append(BasicText.changeSize(nombreVia, TAM_NOMBRE_VIA_ADQUIRIENTE));

		String numVia = "";
		if (tramite.getAdquirienteBean().getPersona().getDireccion()
				.getNumero() != null){
			if(!tramite.getAdquirienteBean().getPersona().getDireccion()
						.getNumero().toUpperCase().equals("SN")
				&& !tramite.getAdquirienteBean().getPersona().getDireccion()
						.getNumero().toUpperCase().equals("S/N")) {
			numVia = numVia
					+ tramite.getAdquirienteBean().getPersona().getDireccion()
							.getNumero();
			}else{
				numVia = "SN";
			}
		}else{
			numVia = "SN";
		}
		line.append(BasicText.changeSize(numVia, TAM_NUM_VIA_ADQUIRIENTE));
		
		campo = tramite.getAdquirienteBean().getPersona().getDireccion().getPuntoKilometrico();
		if (campo == null) {
			line.append(BasicText.changeSize("",
					TAM_KILOMETRO_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo,
					TAM_KILOMETRO_ADQUIRENTE));
		}
		
		campo = tramite.getAdquirienteBean().getPersona().getDireccion().getHm();
		if (campo == null) {
			line.append(BasicText.changeSize("",
					TAM_HECTOMETRO_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo,
					TAM_HECTOMETRO_ADQUIRENTE));
		}
		
		campo = tramite.getAdquirienteBean().getPersona().getDireccion().getBloque();
		if (campo == null) {
			line.append(BasicText.changeSize("",
					TAM_BLOQUE_DOMICILIO_ADQUIRENTEE));
		} else {
			line.append(BasicText.changeSize(campo,
					TAM_BLOQUE_DOMICILIO_ADQUIRENTEE));
		}
		campo = tramite.getAdquirienteBean().getPersona().getDireccion().getLetra();
		if (campo == null) {
			line.append(BasicText.changeSize("",
					TAM_PORTAL_DOMICILIO_ADQUIRENTEE));
		} else {
			line.append(BasicText.changeSize(campo,
					TAM_PORTAL_DOMICILIO_ADQUIRENTEE));
		}

		campo = tramite.getAdquirienteBean().getPersona().getDireccion().getEscalera();
		if (campo == null) {
			line.append(BasicText.changeSize("",
					TAM_ESCALERA_DOMICILIO_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo,
					TAM_ESCALERA_DOMICILIO_ADQUIRENTE));
		}

		campo = tramite.getAdquirienteBean().getPersona().getDireccion().getPlanta();
		if (campo == null) {
			line.append(BasicText.changeSize("",
					TAM_PLANTA_DOMICILIO_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo,
					TAM_PLANTA_DOMICILIO_ADQUIRENTE));
		}

		campo = tramite.getAdquirienteBean().getPersona().getDireccion().getPuerta();
		if (campo == null) {
			line.append(BasicText.changeSize("",
					TAM_PUERTA_DOMICILIO_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo,
					TAM_PUERTA_DOMICILIO_ADQUIRENTE));
		}

		campo = tramite.getAdquirienteBean().getPersona().getDireccion()
				.getMunicipio().getProvincia().getIdProvincia();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_PROVINCIA_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_PROVINCIA_ADQUIRENTE));
		}

		// Si existe provincia tambien existe municipio. El municipio se forma con codigo provincia + codigo municipio
		//Ej. Municipio=28; Provincia=049. Resultado a guardar=28049
		//Por este motivo se concatena campo (que tiene el codigo provincia) con el codigo municipio.
		if (tramite.getAdquirienteBean().getPersona().getDireccion().getMunicipio().getIdMunicipio()==null){
			line.append(BasicText.changeSize("", TAM_MUNICIPIO_ADQUIRENTE));
		} else {
			campo = campo + tramite.getAdquirienteBean().getPersona().getDireccion().getMunicipio().getIdMunicipio();
			line.append(BasicText.changeSize(campo, TAM_MUNICIPIO_ADQUIRENTE));
		}
			
		campo = utilesConversiones.ajustarCamposIne(tramite
				.getAdquirienteBean().getPersona().getDireccion().getPueblo());
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_PUEBLO_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_PUEBLO_ADQUIRENTE));
		}

		campo = tramite.getAdquirienteBean().getPersona().getDireccion()
				.getCodPostal();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_CP_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_CP_ADQUIRENTE, '0',
					BasicText.DETRAS));
		}

		//**********************************************************************
		//CIERRE DETALLE DEL ACQUIRIENTE

		//ABRE DETALLE DEL VEHICULO
		//*******************************************************
		//Si la informacion del vehiculo esta a null es que no ha rellenado la direccion en la pestaña vehiculo. 
		//Si se da el caso se cogerá la dirección del adquiriente (trafico lo toma como el titular).
		if (tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getIdDireccion()==null){
			tipoVia = "";
			try {
				if (tramite.getAdquirienteBean().getPersona().getDireccion()
						.getTipoVia().getIdTipoVia() != null) {
//					// Realizamos la conversión al tipo de vía DGT
//					String tipoViaINE = tramite.getAdquirienteBean().getPersona()
//							.getDireccion().getTipoVia().getIdTipoVia();
//					String tipoViaDGT = utilesConversiones
//							.getIdTipoViaDGTFromIdTipoVia(tipoViaINE);
//					String tipoViaConvertido = utilesConversiones
//							.getIdTipoViaDGTFromNumeroViaDGT(tipoViaDGT);
//					tipoVia = !"".equals(tipoViaConvertido)
//							&& !" ".equals(tipoViaConvertido)
//							&& !"-1".equals(tipoViaConvertido)
//							&& !"OT".equals(tipoViaConvertido) ? (tipoViaConvertido
//							.length() == 2 ? tipoViaConvertido + " "
//							: tipoViaConvertido + " ") : "CL";
					tipoVia=tramite.getAdquirienteBean().getPersona().getDireccion().getTipoVia().getIdTipoVia();
				}
			} catch (Throwable e) {
				tipoVia = "";
			}
			
			line.append(BasicText.changeSize(tipoVia, TAM_TIPO_VIA_ADQUIRIENTE));
			
			nombreVia="";
			if (tramite.getAdquirienteBean().getPersona().getDireccion()
					.getNombreVia() != null) {
				nombreVia = nombreVia
						+ UtilesCadenaCaracteres
								.quitarCaracteresExtranios(utilesConversiones
										.ajustarCamposIne(tramite
												.getAdquirienteBean().getPersona()
												.getDireccion().getNombreVia()))
						+ "";
			}

			// TODO: Analizar si se debe centralizar esto en una sola función como
			// quitarCaracteresExtranios
			// Se eliminan espacios dobles
			nombreVia = nombreVia.replaceAll("\\s\\s", " ");
			nombreVia = nombreVia.replaceAll(",", "");
			nombreVia = nombreVia.replaceAll("º", "");
			nombreVia = nombreVia.replaceAll("ª", "");
			nombreVia = nombreVia.replaceAll("/", "");

			if (nombreVia.length() > TAM_NOMBRE_VIA_ADQUIRIENTE) {
				nombreVia = nombreVia.substring(0, TAM_NOMBRE_VIA_ADQUIRIENTE);
			}

			line.append(BasicText.changeSize(nombreVia, TAM_NOMBRE_VIA_ADQUIRIENTE));

			numVia = "";
			if (tramite.getAdquirienteBean().getPersona().getDireccion()
					.getNumero() != null){
				if(!tramite.getAdquirienteBean().getPersona().getDireccion()
							.getNumero().toUpperCase().equals("SN")
					&& !tramite.getAdquirienteBean().getPersona().getDireccion()
							.getNumero().toUpperCase().equals("S/N")) {
				numVia = numVia
						+ tramite.getAdquirienteBean().getPersona().getDireccion()
								.getNumero();
				}else{
					numVia = "SN";
				}
			}else{
				numVia = "SN";
			}
			line.append(BasicText.changeSize(numVia, TAM_NUM_VIA_ADQUIRIENTE));
			
			campo = tramite.getAdquirienteBean().getPersona().getDireccion().getPuntoKilometrico();
			if (campo == null) {
				line.append(BasicText.changeSize("",
						TAM_KILOMETRO_ADQUIRENTE));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_KILOMETRO_ADQUIRENTE));
			}
			
			campo = tramite.getAdquirienteBean().getPersona().getDireccion().getHm();
			if (campo == null) {
				line.append(BasicText.changeSize("",
						TAM_HECTOMETRO_ADQUIRENTE));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_HECTOMETRO_ADQUIRENTE));
			}
			
			campo = tramite.getAdquirienteBean().getPersona().getDireccion().getBloque();
			if (campo == null) {
				line.append(BasicText.changeSize("",
						TAM_BLOQUE_DOMICILIO_ADQUIRENTEE));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_BLOQUE_DOMICILIO_ADQUIRENTEE));
			}
			campo = tramite.getAdquirienteBean().getPersona().getDireccion().getLetra();
			if (campo == null) {
				line.append(BasicText.changeSize("",
						TAM_PORTAL_DOMICILIO_ADQUIRENTEE));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_PORTAL_DOMICILIO_ADQUIRENTEE));
			}
			
			campo = tramite.getAdquirienteBean().getPersona().getDireccion().getEscalera();
			if (campo == null) {
				line.append(BasicText.changeSize("",
						TAM_ESCALERA_DOMICILIO_ADQUIRENTE));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_ESCALERA_DOMICILIO_ADQUIRENTE));
			}
			
			campo = tramite.getAdquirienteBean().getPersona().getDireccion().getPlanta();
			if (campo == null) {
				line.append(BasicText.changeSize("",
						TAM_PLANTA_DOMICILIO_ADQUIRENTE));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_PLANTA_DOMICILIO_ADQUIRENTE));
			}

			campo = tramite.getAdquirienteBean().getPersona().getDireccion().getPuerta();
			if (campo == null) {
				line.append(BasicText.changeSize("",
						TAM_PUERTA_DOMICILIO_ADQUIRENTE));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_PUERTA_DOMICILIO_ADQUIRENTE));
			}
				
			campo = tramite.getAdquirienteBean().getPersona().getDireccion()
					.getMunicipio().getProvincia().getIdProvincia();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_PROVINCIA_ADQUIRENTE));
			} else {
				line.append(BasicText.changeSize(campo, TAM_PROVINCIA_ADQUIRENTE));
			}
			
			// Si existe provincia tambien existe municipio. El municipio se forma con codigo provincia + codigo municipio
			//Ej. Municipio=28; Provincia=049. Resultado a guardar=28049
			//Por este motivo se concatena campo (que tiene el codigo provincia) con el codigo municipio.
			if (tramite.getAdquirienteBean().getPersona().getDireccion().getMunicipio().getIdMunicipio()==null){
				line.append(BasicText.changeSize("", TAM_MUNICIPIO_ADQUIRENTE));
			} else {
				campo = campo + tramite.getAdquirienteBean().getPersona().getDireccion().getMunicipio().getIdMunicipio();
				line.append(BasicText.changeSize(campo, TAM_MUNICIPIO_ADQUIRENTE));
			}
				
			campo = utilesConversiones.ajustarCamposIne(tramite
					.getAdquirienteBean().getPersona().getDireccion().getPueblo());
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_PUEBLO_ADQUIRENTE));
			} else {
				line.append(BasicText.changeSize(campo, TAM_PUEBLO_ADQUIRENTE));
			}
			
			campo = tramite.getAdquirienteBean().getPersona().getDireccion()
					.getCodPostal();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_CP_ADQUIRENTE));
			} else {
				line.append(BasicText.changeSize(campo, TAM_CP_ADQUIRENTE, '0',
						BasicText.DETRAS));
			}
		}else{
			String tipoViaVehiculo = "";
			try {
				if (tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean()
						.getTipoVia().getIdTipoVia() != null) {
//					// Realizamos la conversión al tipo de vía DGT
//					String tipoViaINE = tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean()
//							.getTipoVia().getIdTipoVia();
//					String tipoViaDGT = utilesConversiones
//							.getIdTipoViaDGTFromIdTipoVia(tipoViaINE);
//					String tipoViaConvertido = utilesConversiones
//							.getIdTipoViaDGTFromNumeroViaDGT(tipoViaDGT);
//					tipoViaVehiculo = !"".equals(tipoViaConvertido)
//							&& !" ".equals(tipoViaConvertido)
//							&& !"-1".equals(tipoViaConvertido)
//							&& !"OT".equals(tipoViaConvertido) ? (tipoViaConvertido
//							.length() == 2 ? tipoViaConvertido + " "
//							: tipoViaConvertido + " ") : "CL";
					tipoViaVehiculo=tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getTipoVia().getIdTipoVia();
				}
			} catch (Throwable e) {
				tipoViaVehiculo = "";
			}
			line.append(BasicText.changeSize(tipoViaVehiculo, TAM_TIPO_VIA_VEHICULO));
					
			String nombreViaVehiculo="";
			if (tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean()
					.getNombreVia() != null) {
				nombreViaVehiculo = nombreViaVehiculo
						+ UtilesCadenaCaracteres
								.quitarCaracteresExtranios(utilesConversiones
										.ajustarCamposIne(tramite.getTramiteTraficoBean().getVehiculo()
												.getDireccionBean().getNombreVia()));
	//					+ " ";

				// TODO: Analizar si se debe centralizar esto en una sola función como
				// quitarCaracteresExtranios
				// Se eliminan espacios dobles
				nombreViaVehiculo = nombreViaVehiculo.replaceAll("\\s\\s", " ");
				nombreViaVehiculo = nombreViaVehiculo.replaceAll(",", "");
				nombreViaVehiculo = nombreViaVehiculo.replaceAll("º", "");
				nombreViaVehiculo = nombreViaVehiculo.replaceAll("ª", "");
				nombreViaVehiculo = nombreViaVehiculo.replaceAll("/", "");

				if (nombreViaVehiculo.length() > TAM_VIA_VEHICULO) {
					nombreViaVehiculo = nombreViaVehiculo.substring(0, TAM_VIA_VEHICULO);
				}

			}
			line.append(BasicText.changeSize(nombreViaVehiculo, TAM_VIA_VEHICULO));
			
			String numViaVehiculo = "";
			if (tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean()
					.getNumero() != null){
				if(!tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean()
							.getNumero().toUpperCase().equals("SN")
				&& !tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean()
							.getNumero().toUpperCase().equals("S/N")) {
				numViaVehiculo = numViaVehiculo
							+ tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean()
								.getNumero();
			}else{
					numViaVehiculo = "SN";
				}
			}else{
				numViaVehiculo = "SN";
			}
			line.append(BasicText.changeSize(numViaVehiculo, TAM_NUM_VIA_ADQUIRIENTE));
			
			campo = tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getPuntoKilometrico();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_KM_VEHICULO));
			} else {
				campo = utiles.cambiaFormatoMatricula(campo);
				line.append(BasicText.changeSize(campo, TAM_KM_VEHICULO));
			}
				
			campo = tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getHm();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_HM_VEHICULO));
			} else {
				campo = utiles.cambiaFormatoMatricula(campo);
				line.append(BasicText.changeSize(campo, TAM_HM_VEHICULO));
			}
				
			campo = tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getBloque();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_BLOQUE_DOMICILIO_VEHICULO));
			} else {
				campo = utiles.cambiaFormatoMatricula(campo);
				line.append(BasicText.changeSize(campo, TAM_BLOQUE_DOMICILIO_VEHICULO));
			}
			campo = tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getLetra();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_PORTAL_DOMICILIO_VEHICULO));
			} else {
				campo = utiles.cambiaFormatoMatricula(campo);
				line.append(BasicText.changeSize(campo, TAM_PORTAL_DOMICILIO_VEHICULO));
			}
			
			campo = tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getEscalera();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_ESCALERA_DOMICILIO_VEHICULO));
			} else {
				campo = utiles.cambiaFormatoMatricula(campo);
				line.append(BasicText.changeSize(campo, TAM_ESCALERA_DOMICILIO_VEHICULO));
			}
			
			campo = tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getPlanta();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_PLANTA_DOMICILIO_VEHICULO));
			} else {
				campo = utiles.cambiaFormatoMatricula(campo);
				line.append(BasicText.changeSize(campo, TAM_PLANTA_DOMICILIO_VEHICULO));
			}
			
			campo = tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getPuerta();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_PUERTA_DOMICILIO_VEHICULO));
			} else {
				campo = utiles.cambiaFormatoMatricula(campo);
				line.append(BasicText.changeSize(campo, TAM_PUERTA_DOMICILIO_VEHICULO));
			}
			campo = tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean()
					.getMunicipio().getProvincia().getIdProvincia();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_PROVINCIA_VEHICULO));
			} else {
				campo = utiles.cambiaFormatoMatricula(campo);
				line.append(BasicText.changeSize(campo, TAM_PROVINCIA_VEHICULO));
			}
			
			// Si existe provincia tambien existe municipio. El municipio se forma con codigo provincia + codigo municipio
			//Ej. Municipio=28; Provincia=049. Resultado a guardar=28049
			//Por este motivo se concatena campo (que tiene el codigo provincia) con el codigo municipio.
			if (tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getMunicipio().getIdMunicipio()==null){
				line.append(BasicText.changeSize("", TAM_MUNICIPIO_VEHICULO));
			} else {
				campo = campo + tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getMunicipio().getIdMunicipio();
				line.append(BasicText.changeSize(campo, TAM_MUNICIPIO_VEHICULO));
			}
			
			campo = tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getPueblo();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_PUEBLO_VEHICULO));
			} else {
				campo = utiles.cambiaFormatoMatricula(campo);
				line.append(BasicText.changeSize(campo, TAM_PUEBLO_VEHICULO));
			}
			
			campo = tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getCodPostal();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_CP_VEHICULO));
			} else {
				campo = utiles.cambiaFormatoMatricula(campo);
				line.append(BasicText.changeSize(campo, TAM_CP_VEHICULO));
			}
		}
		//*******************************************************
		//CIERRE DETALLE DEL VEHICULO

		try {
			if (tramite.getTramiteTraficoBean().getVehiculo().getFechaItv() != null
					&& !tramite.getTramiteTraficoBean().getVehiculo()
							.getFechaItv().isfechaNula()) {
				campo = formato.format(tramite.getTramiteTraficoBean()
						.getVehiculo().getFechaItv().getTimestamp());
			} else {
				campo = null;
			}
		} catch (ParseException e) {
			campo = null;
		}
		if (campo == null) {
			line.append(BasicText.changeSize("EXENTO", TAM_FECHA_ITV));
		} else {
			line.append(BasicText.changeSize(campo, TAM_FECHA_ITV, '0', false));
		}

		campo = tramite.getTramiteTraficoBean().getVehiculo().getMotivoItv()
				.getDescripcion();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_MOTIVO_ITV));
		} else {
			line.append(BasicText.changeSize(campo, TAM_MOTIVO_ITV));
		}
		// Casuística de nube con tipo de transferencia
		/*
		 * CFS 24-04-2012
		 * 
		 * En este apartado se comprueba si el tipo de transferencia es
		 * telemática o no telemática.
		 * 
		 * En caso de ser telemática, la leyenda no se incluye en la nube (va
		 * identificada en el documento) En caso de ser no telemática, el tipo
		 * de transferencia se incluye en la nube.
		 */
		/**campo = MapeoTipoTransferencia.valorEnumTipoTransferenciaNotEnt(tramite
				.getTipoTransferencia().getValorEnum());
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_TIPO_TRANSFERENCIA));
		} else {
			if (campo.equals("1") && null != tramite.getImpresionPermiso()
					&& !tramite.getImpresionPermiso().equals("true")) {
				campo = "N";
			}
			line.append(BasicText.changeSize(campo, TAM_TIPO_TRANSFERENCIA));
		}
		/* FIN MOD CFS 24-04-2012 */
	/**	campo = tramite.getRepresentanteAdquirienteBean().getConceptoRepre() != null ? tramite
				.getRepresentanteAdquirienteBean().getConceptoRepre()
				.getValorEnum()
				: null;
		if (campo != null && ConceptoTutela.Tutela.getValorEnum().equals(campo)) {
			line.append(BasicText.changeSize(VALOR_TUTELA, TAM_TUTELA));
		} else {
			line.append(BasicText.changeSize("", TAM_TUTELA));
		}

		campo = tramite.getTramiteTraficoBean().getIedtm() != null
				&& tramite.getTramiteTraficoBean().getIedtm().equals("true") ? VALOR_S
				: null;
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_TIPO_LIMITACION));
		} else {
			line.append(BasicText.changeSize(campo, TAM_TIPO_LIMITACION));
		}

		try {
			if (tramite.getTramiteTraficoBean().getFechaIedtm() != null
					&& !tramite.getTramiteTraficoBean().getFechaIedtm()
							.isfechaNula()) {
				campo = formato.format(tramite.getTramiteTraficoBean()
						.getFechaIedtm().getTimestamp());
			} else {
				campo = null;
			}
		} catch (ParseException e) {
			campo = null;
		}
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_FECHA_LIMITACION));
		} else {
			line.append(BasicText.changeSize(campo, TAM_FECHA_LIMITACION, '0',
					false));
		}

		campo = null != tramite.getTramiteTraficoBean().getNumRegIedtm()
				&& !"-1".equals(tramite.getTramiteTraficoBean()
						.getNumRegIedtm().getValorEnum()) ? tramite
				.getTramiteTraficoBean().getNumRegIedtm().getValorEnum() : null;
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_NUM_REG));
		} else {
			line.append(BasicText.changeSize(campo, TAM_NUM_REG));
		}

		campo = tramite.getTramiteTraficoBean().getFinancieraIedtm();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_FINANCIERA_LIMITACION));
		} else {
			line.append(BasicText.changeSize(campo, TAM_FINANCIERA_LIMITACION));
		}

		campo = tramite.getCetItp();
		if (campo == null) {
			line.append(VALOR_CET_PREDETERMINADO);
		} else {
			line.append(BasicText.changeSize(campo, TAM_CET));
		}

		// Nuevo campo solicitado por la DGT, CEMA para Vehículos agrícolas
		campo = tramite.getTramiteTraficoBean().getCema();
		if (campo == null || campo.length() == 0) {
			line.append(VALOR_CET_PREDETERMINADO);
		} else {
			line.append(BasicText.changeSize(campo, TAM_CEM));
		}


		/*
		 * @author: Carlos Garcia, Santiago Cuenca
		 * Incidencia: 0001632
		 * Informamos el nuevo campo (la provincia del CET)
		
		// Si el CET es nulo, vacío o está informado con ceros, no es necesario informar la provincia del CET
		if( tramite.getCetItp() == null || "".equals(tramite.getCetItp()) || "00000000".equals(tramite.getCetItp()) ){
			line.append(BasicText.changeSize("", TAM_PROVINCIA_CET));
		// Si el CET no es nulo y coincide con la provincia del contrato del colegiado, no es necesario que informemos la provincia del CET
		} else if( tramite.getProvinciaCet()!=null && tramite.getProvinciaCet().getIdProvincia()!=null && tramite.getProvinciaCet().getIdProvincia().equals(utilesColegiado.getContrato().getId_provincia()) ){
			line.append(BasicText.changeSize("", TAM_PROVINCIA_CET));
		// Si el CET no es nulo, pero no coincide con la provincia del colegiado, informamos la provincia del CET
		} else if( tramite.getProvinciaCet()!=null && tramite.getProvinciaCet().getIdProvincia()!=null && !tramite.getProvinciaCet().getIdProvincia().equals(utilesColegiado.getContrato().getId_provincia())
				&& !tramite.getProvinciaCet().getIdProvincia().equals("-1") ){
			campo = tramite.getProvinciaCet().getIdProvincia();
			String provincia = tramite.getProvinciaCet().getIdProvincia();
			provincia = utilesConversiones.getSiglasFromIdProvincia(provincia);
			line.append(BasicText.changeSize(provincia, TAM_PROVINCIA_CET));
		} else {
			line.append(BasicText.changeSize("", TAM_PROVINCIA_CET));
		}

		return line.toString();
	}
	
	*/

	/**
	 * Método que obtendrá un String para la nube de Baja.
	 * 
	 * @param detalleBaja
	 * @return
	 */
	protected String obtenerNubeBaja(TramiteTraficoBajaBean detalleBaja) {
		SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
		StringBuffer line = new StringBuffer();
		String campo = "";
		String nuevaVersion = gestorPropiedades.valorPropertie("version3.nube.bajas");

		// Comenzamos a meter los campos de las etiquetas en orden para generar
		// la mancha
		campo = VERSION_BAJA;
		line.append(campo);
		campo = PROGRAMA_BAJA;
		line.append(campo);

		campo = null != detalleBaja.getTramiteTrafico().getTasa() ? detalleBaja
				.getTramiteTrafico().getTasa().getCodigoTasa() : null;
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_TASA));
		} else if (campo.equals("EXENTO")) {
			line.append(BasicText.changeSize(campo, TAM_TASA));
		} else {
			line.append(BasicText.changeSize(campo, TAM_TASA, '0', false));
		}

		try {
			if (detalleBaja.getTramiteTrafico().getFechaPresentacion() != null
					&& !detalleBaja.getTramiteTrafico().getFechaPresentacion()
							.isfechaNula()) {
				campo = formato.format(detalleBaja.getTramiteTrafico()
						.getFechaPresentacion().getTimestamp());
			} else {
				campo = null;
			}
		} catch (ParseException e) {
			campo = null;
		}
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_FECHA_PRESENTACION));
		} else {
			line.append(BasicText.changeSize(campo, TAM_FECHA_PRESENTACION,
					'0', false));
		}

		campo = null != detalleBaja.getTramiteTrafico().getVehiculo() ? detalleBaja
				.getTramiteTrafico().getVehiculo().getMatricula()
				: null;
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_MATRICULA));
		} else {
			campo = utiles.cambiaFormatoMatricula(campo);
			line.append(BasicText.changeSize(campo, TAM_MATRICULA));
		}

		try {
			if (detalleBaja.getTramiteTrafico().getVehiculo()
					.getFechaPrimMatri() != null
					&& !detalleBaja.getTramiteTrafico().getVehiculo()
							.getFechaPrimMatri().isfechaNula()) {
				campo = formato.format(detalleBaja.getTramiteTrafico()
						.getVehiculo().getFechaPrimMatri().getTimestamp());
			} else {
				campo = null;
			}
		} catch (ParseException e) {
			campo = null;
		}
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_FECHA_MATRICULACION));
		} else {
			line.append(BasicText.changeSize(campo, TAM_FECHA_MATRICULACION,
					'0', false));
		}

		// Datos del titular
		campo = null != detalleBaja.getTitular().getPersona().getNif() ? detalleBaja
				.getTitular().getPersona().getNif()
				: null;
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_DNI_TITULAR));
		} else {
			line.append(BasicText.changeSize(campo, TAM_DNI_TITULAR));
		}

		campo = null != detalleBaja.getMotivoBaja() ? detalleBaja
				.getMotivoBaja().getValorEnum() : null;
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_MOTIVO_BAJA));
		} else {
			line.append(BasicText.changeSize(campo, TAM_MOTIVO_BAJA));
		}

		// Si el tipo de baja es TEMPORAL POR TRANSFERENCIA añadimos los datos
		// del adquirente a la nube de puntos
		// if (BAJA_TEMPORAL_TRANSFERENCIA.equals(campo)) {

		campo = detalleBaja.getAdquiriente().getPersona().getNif();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_DNI_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_DNI_ADQUIRENTE));
		}

		try {
			if (detalleBaja.getAdquiriente().getPersona()
					.getFechaNacimientoBean() != null
					&& !detalleBaja.getAdquiriente().getPersona()
							.getFechaNacimientoBean().isfechaNula()) {
				campo = formato.format(detalleBaja.getAdquiriente()
						.getPersona().getFechaNacimientoBean().getTimestamp());
			} else {
				campo = null;
			}
		} catch (ParseException e) {
			campo = null;
		}
		if (campo == null) {
			line.append(BasicText.changeSize("",
					TAM_FECHA_NACIMIENTO_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo,
					TAM_FECHA_NACIMIENTO_ADQUIRENTE, '0', false));
		}

		campo = null != detalleBaja.getTitular().getPersona().getSexo() ? detalleBaja
				.getAdquiriente().getPersona().getSexo()
				: null;
		if (campo == null || campo.equals(PERSONA_JURIDICA)) {
			campo = detalleBaja.getAdquiriente().getPersona()
					.getApellido1RazonSocial();
			if (campo == null) {
				line.append(BasicText.changeSize("",
						TAM_PRIMER_APELLIDO_RAZON_SOCIAL));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_PRIMER_APELLIDO_RAZON_SOCIAL));
			}
		} else {
			campo = detalleBaja.getAdquiriente().getPersona()
					.getApellido1RazonSocial();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_PRIMER_APELLIDO));
			} else {
				line.append(BasicText.changeSize(campo, TAM_PRIMER_APELLIDO));
			}

			campo = detalleBaja.getAdquiriente().getPersona().getApellido2();
			if (campo == null) {
				line.append(BasicText.changeSize("",
						TAM_SEGUNDO_APELLIDO_ADQUIRENTE));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_SEGUNDO_APELLIDO_ADQUIRENTE));
			}

			campo = detalleBaja.getAdquiriente().getPersona().getNombre();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_NOMBRE_ADQUIRENTE));
			} else {
				line.append(BasicText.changeSize(campo, TAM_NOMBRE_ADQUIRENTE));
			}
		}

		campo = null != detalleBaja.getTitular().getPersona().getSexo() ? detalleBaja
				.getAdquiriente().getPersona().getSexo()
				: null;
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_SEXO_ADQUIRENTE));
		} else if (campo.equals(SEXO_HEMBRA)) {
			line.append(BasicText.changeSize(SEXO_MUJER, TAM_SEXO_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_SEXO_ADQUIRENTE));
		}

		
		//ABRE DETALLE DEL ACQUIRIENTE
		//***************************************************************
		String tipoVia = "";
		try {
			if (detalleBaja.getAdquiriente().getPersona().getDireccion()
					.getTipoVia().getIdTipoVia() != null) {
//				// Realizamos la conversión al tipo de vía DGT
//				String tipoViaINE = detalleBaja.getAdquiriente().getPersona()
//						.getDireccion().getTipoVia().getIdTipoVia();
//				String tipoViaDGT = utilesConversiones
//						.getIdTipoViaDGTFromIdTipoVia(tipoViaINE);
//				String tipoViaConvertido = utilesConversiones
//						.getIdTipoViaDGTFromNumeroViaDGT(tipoViaDGT);
//				tipoVia = !"".equals(tipoViaConvertido)
//						&& !" ".equals(tipoViaConvertido)
//						&& !"-1".equals(tipoViaConvertido)
//						&& !"OT".equals(tipoViaConvertido) ? (tipoViaConvertido
//						.length() == 2 ? tipoViaConvertido + " "
//						: tipoViaConvertido + " ") : "CL";
				tipoVia=detalleBaja.getAdquiriente().getPersona().getDireccion().getTipoVia().getIdTipoVia();
			}
		} catch (Throwable e) {
			tipoVia = "";
		}
		line.append(BasicText.changeSize(tipoVia, TAM_TIPO_VIA_ADQUIRIENTE));
		
		String nombreVia="";
		if (detalleBaja.getAdquiriente().getPersona().getDireccion()
				.getNombreVia() != null) {
			nombreVia = nombreVia
					+ UtilesCadenaCaracteres
							.quitarCaracteresExtranios(utilesConversiones
									.ajustarCamposIne(detalleBaja.getAdquiriente().getPersona()
											.getDireccion().getNombreVia()))
					+ "";
		

			// TODO: Analizar si se debe centralizar esto en una sola función como
			// quitarCaracteresExtranios
			// Se eliminan espacios dobles
			nombreVia = nombreVia.replaceAll("\\s\\s", " ");
			nombreVia = nombreVia.replaceAll(",", "");
			nombreVia = nombreVia.replaceAll("º", "");
			nombreVia = nombreVia.replaceAll("ª", "");
			nombreVia = nombreVia.replaceAll("/", "");
	
			if (nombreVia.length() > TAM_NOMBRE_VIA_ADQUIRIENTE) {
				nombreVia = nombreVia.substring(0, TAM_NOMBRE_VIA_ADQUIRIENTE);
			}
		}		
		line.append(BasicText.changeSize(nombreVia, TAM_NOMBRE_VIA_ADQUIRIENTE));

		String numVia = "";
		if (detalleBaja.getAdquiriente().getPersona().getDireccion()
				.getNumero() != null){
			if(!detalleBaja.getAdquiriente().getPersona().getDireccion()
						.getNumero().toUpperCase().equals("SN")
				&& !detalleBaja.getAdquiriente().getPersona().getDireccion()
						.getNumero().toUpperCase().equals("S/N")) {
			numVia = numVia
					+ detalleBaja.getAdquiriente().getPersona().getDireccion()
							.getNumero();
			}else{
				numVia = "SN";
			}
		}else{
			numVia = "SN";
		}
		line.append(BasicText.changeSize(numVia, TAM_NUM_VIA_ADQUIRIENTE));
		
		campo = detalleBaja.getAdquiriente().getPersona().getDireccion().getPuntoKilometrico();
		if (campo == null) {
			line.append(BasicText.changeSize("",
					TAM_KILOMETRO_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo,
					TAM_KILOMETRO_ADQUIRENTE));
		}
		
		campo = detalleBaja.getAdquiriente().getPersona().getDireccion().getHm();
		if (campo == null) {
			line.append(BasicText.changeSize("",
					TAM_HECTOMETRO_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo,
					TAM_HECTOMETRO_ADQUIRENTE));
		}
			
		campo = detalleBaja.getAdquiriente().getPersona().getDireccion().getBloque();
		if (campo == null) {
			line.append(BasicText.changeSize("",
					TAM_BLOQUE_DOMICILIO_ADQUIRENTEE));
		} else {
			line.append(BasicText.changeSize(campo,
					TAM_BLOQUE_DOMICILIO_ADQUIRENTEE));
		}
		campo = detalleBaja.getAdquiriente().getPersona().getDireccion().getLetra();
		if (campo == null) {
			line.append(BasicText.changeSize("",
					TAM_PORTAL_DOMICILIO_ADQUIRENTEE));
		} else {
			line.append(BasicText.changeSize(campo,
					TAM_PORTAL_DOMICILIO_ADQUIRENTEE));
		}
		
		campo = detalleBaja.getAdquiriente().getPersona().getDireccion().getEscalera();
		if (campo == null) {
			line.append(BasicText.changeSize("",
					TAM_ESCALERA_DOMICILIO_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo,
					TAM_ESCALERA_DOMICILIO_ADQUIRENTE));
		}
		
		campo = detalleBaja.getAdquiriente().getPersona().getDireccion().getPlanta();
		if (campo == null) {
			line.append(BasicText.changeSize("",
					TAM_PLANTA_DOMICILIO_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo,
					TAM_PLANTA_DOMICILIO_ADQUIRENTE));
		}
	
		campo = detalleBaja.getAdquiriente().getPersona().getDireccion().getPuerta();
		if (campo == null) {
			line.append(BasicText.changeSize("",
					TAM_PUERTA_DOMICILIO_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo,
					TAM_PUERTA_DOMICILIO_ADQUIRENTE));
		}
		
		campo = detalleBaja.getAdquiriente()
				.getPersona().getDireccion()
				.getMunicipio().getProvincia().getIdProvincia();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_PROVINCIA_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_PROVINCIA_ADQUIRENTE));
		}
		
		// Si existe provincia tambien existe municipio. El municipio se forma con codigo provincia + codigo municipio
		//Ej. Municipio=28; Provincia=049. Resultado a guardar=28049
		//Por este motivo se concatena campo (que tiene el codigo provincia) con el codigo municipio.
		if (detalleBaja.getAdquiriente().getPersona().getDireccion()
				.getMunicipio().getIdMunicipio()==null){
			line.append(BasicText.changeSize("", TAM_MUNICIPIO_ADQUIRENTE));
		} else {
			campo = campo + detalleBaja.getAdquiriente().getPersona().getDireccion()
					.getMunicipio().getIdMunicipio();
			line.append(BasicText.changeSize(campo, TAM_MUNICIPIO_ADQUIRENTE));
		}
		
		campo = utilesConversiones.ajustarCamposIne(detalleBaja.getAdquiriente()
				.getPersona().getDireccion().getPueblo());
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_PUEBLO_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_PUEBLO_ADQUIRENTE));
		}
		
		campo = detalleBaja.getAdquiriente().getPersona().getDireccion()
				.getCodPostal();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_CP_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_CP_ADQUIRENTE, '0',
					BasicText.DETRAS));
		}
		//**********************************************************************
		//CIERRE DETALLE DEL ACQUIRIENTE
		
		
		// Fin Baja temporal por transferencia

		// Queda introducir dos campos según el último documento de CN:
		// A- Tipo de trámite(String 1), este campo se cubrirá con una clave
		// para recoger los siguientes supuestos:
		// ‘1’ – Si se trata de una Transferencia entre particulares en la que
		// la solicitud ha sido presentada por el comprador o bien por el
		// comprador y vendedor simultáneamente.
		// ‘N’ – Si se trata del mismo supuesto que en el caso de la clave ‘1’
		// pero no se va a imprimir el Permiso de Circulación, por presentarse
		// inmediatamente después otro tramite de Transferencia.
		// ‘2’ – Si se trata de una Notificación de Venta entre particulares,
		// solicitada por el vendedor.
		// ‘3‘ - Si se trata de una Transferencia solicitada por el comprador
		// del vehículo, en la que ha intervenido una empresa de Compra-Venta de
		// vehículos, y por tanto el vehículo se encuentra dado de baja temporal
		// por transferencia.

		// B- Código CEMA (String 8)

		/*
		 * campo = detalleBaja.getMotivoBaja().getValorEnum(); if (campo ==
		 * null) { line.append(BasicText.changeSize("",
		 * TAM_TIPO_TRANSFERENCIA)); } else { if(campo.equals("1") &&
		 * !detalleBaja.getImprPermisoCircu().equals("true")) { campo = "N"; }
		 * line.append(BasicText.changeSize(campo, TAM_TIPO_TRANSFERENCIA)); }
		 */
		// IMPORTANTE: COMENTO CEMA PORQUE NO PASA EL DIA 14/01/2012
		campo = detalleBaja.getCet();
		if (campo == null || campo.length() == 0) {
			line.append(VALOR_CET_PREDETERMINADO);
		} else {
			line.append(BasicText.changeSize(campo, TAM_CEM));
		}
		
		// } FIN TEMPORAL TRANSFERENCIA

			campo =  detalleBaja.getMotivoBaja().getValorEnum();
			if ((MotivoBaja.DefTC.getValorEnum().equals(campo) ||
			     MotivoBaja.DefE.getValorEnum().equals(campo) ||
				 MotivoBaja.DefRP.getValorEnum().equals(campo) ||
				 MotivoBaja.DefV.getValorEnum().equals(campo)) &&
				 detalleBaja.getTramiteTrafico().getVehiculo().getServicioTraficoBean() != null
				 && "B06".equals(detalleBaja.getTramiteTrafico().getVehiculo().getServicioTraficoBean().getIdServicio())) {
			  
			  //Nuevo campo solicitado por la DGT, CEMA para Vehículos agrícolas
			  campo = detalleBaja.getTramiteTrafico().getCema(); 
			  if (campo == null || campo.length() == 0) { 
				  line.append(VALOR_CET_PREDETERMINADO); 
			  }else { 
				  line.append(BasicText.changeSize(campo, TAM_CEM)); }
			  }
		 
		 

		return line.toString();
	}

	/**
	 * Inserta la nube de puntos ya generada en el documento.
	 * 
	 * @param byte1
	 * @param vectPags
	 * @param nube
	 * @param pdf
	 * @return
	 */
	protected byte[] insertarNubePuntos(String tipoNube, byte[] byte1,
			int[] vectPags, String nube, PdfMaker pdf) {
		Image img;
		List<FieldPosition> posiciones = pdf.getFieldPosition(byte1, tipoNube);
		FieldPosition posicion = posiciones.get(0);
		img = pdf.crearNubePuntos(nube);
		img = pdf.escalarImagen(img, posicion.position.getWidth(),
				posicion.position.getHeight());
		return pdf.insertarImagen(byte1, img, vectPags, posicion.position
				.getLeft(), posicion.position.getBottom());
	}
	
	/**
	 * Inserta el código de barras con el cifrado (firma) del gestor o colegio.
	 * 
	 * @param nmobreCampo
	 * @param byte1
	 * @param vectPags
	 * @param barcode
	 * @param pdf
	 * @return
	 */
	protected static byte[] insertarBarcodeFirma(String nombreCampo,
			byte[] byte1, int[] vectPags, String barcode, PdfMaker pdf) {
		List<FieldPosition> posiciones = pdf.getFieldPosition(byte1,
				nombreCampo);
		FieldPosition posicion = posiciones.get(0);
		byte1 = pdf.crearCodigoBarras128ConTexto(barcode, byte1,
				-ConstantesPDF._1, ConstantesPDF._12, -ConstantesPDF._1, true,
				posicion.position.getLeft(), posicion.position.getBottom());
		return byte1;
	}


	/**
	 * Inserta las firmas en Matriculación y Transmisión.
	 * 
	 * @param detalleTramite
	 * @param detalleTitular
	 * @param byte1
	 * @param vectPags
	 * @param pdf
	 * @return
	 * @throws OegamExcepcion
	 */
	protected byte[] insertarFirmasColegiadoYColegio(
			TramiteTraficoBean detalleTramite,
			IntervinienteTrafico detalleTitular, byte[] byte1, int[] vectPags,
			PdfMaker pdf) throws OegamExcepcion {
		String fechaDeHoy = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date());
		ModeloColegiado modeloColegiado = new ModeloColegiado();
		Persona colegiado = modeloColegiado
				.obtenerColegiadoCompleto(detalleTramite.getIdContrato() != null ? detalleTramite
						.getIdContrato()
						: utilesColegiado.getIdContratoSessionBigDecimal());
		BeanPQDatosColegio beanPQDatosColegio = new BeanPQDatosColegio();
		beanPQDatosColegio
				.setP_COLEGIO(utilesColegiado.getColegioDelContrato());
		HashMap<String, Object> datosColegio = getModeloTrafico()
				.obtenerDatosColegio(beanPQDatosColegio);

		String firmaGestor = (detalleTramite.getVehiculo().getBastidor() != null
				&& !detalleTramite.getVehiculo().getBastidor().equals("") ? detalleTramite
				.getVehiculo().getBastidor()
				: "")
				+ "#" + colegiado.getNif() + "#" + fechaDeHoy;
		// Ciframos la firma, convertimos a hexadecimal y restringimos a los
		// primeros _16 caracteres.
		firmaGestor = utiles.cifrarHMACSHA1(firmaGestor,
				"ClaveCifrado");
		firmaGestor = utiles.stringToHex(firmaGestor);
		firmaGestor = firmaGestor.substring(0, ConstantesPDF._16);
		byte1 = insertarBarcodeFirma(ConstantesPDF.ID_FIRMA_GESTOR, byte1,
				vectPags, firmaGestor, pdf);

		// Insertamos Firma del Colegio:
		String firmaColegio = utilesColegiado.getCifContratoSession()
				+ "#"
				+ (detalleTramite.getVehiculo().getBastidor() != null
						&& !detalleTramite.getVehiculo().getBastidor().equals(
								"") ? detalleTramite.getVehiculo()
						.getBastidor() : "")
				+ "#"
				+ detalleTitular.getPersona().getNif()
				+ "#"
				+ colegiado.getNif()
				+ "#"
				+ ((ColegioBean) datosColegio.get(ConstantesPQ.BEANPANTALLA))
						.getCif() + "#" + fechaDeHoy;
		// Ciframos la firma, convertimos a hexadecimal y restringimos a los
		// primeros _16 caracteres.
		firmaColegio = utiles.cifrarHMACSHA1(firmaColegio,
				"ClaveCifrado");
		firmaColegio = utiles.stringToHex(firmaColegio);
		firmaColegio = firmaColegio.substring(0, ConstantesPDF._16);
		byte1 = insertarBarcodeFirma(ConstantesPDF.ID_FIRMA_COLEGIO, byte1,
				vectPags, firmaColegio, pdf);

		return byte1;
	}
	
	/**
	 * Inserta el código de barras del numero de expediente.
	 * 
	 * @param byte1
	 * @param vectPags
	 * @param barcode
	 * @param pdf
	 * @return
	 */
	protected byte[] insertarBarcodeNumExpediente(byte[] byte1,
			int[] vectPags, String barcode, PdfMaker pdf) {
		List<FieldPosition> posiciones = pdf.getFieldPosition(byte1,
				ConstantesPDF.ID_NUM_EXPEDIENTE_FIRMA);
		FieldPosition posicion = posiciones.get(0);
		byte1 = pdf.crearCodigoBarras128ConTexto(barcode, byte1,
				-ConstantesPDF._1, -ConstantesPDF._1, -ConstantesPDF._1, true,
				posicion.position.getLeft(), posicion.position.getBottom());
		return byte1;
	}

	/**
	 * Calcula el tipo de matriculación a partir del criterio de construcción
	 * del vehículo
	 * 
	 * @param construccion
	 * @return
	 */
	protected String obtenerCodigoMatriculacion(String construccion) {

		String codigo = null;

		if (construccion == null) {
			return null;
		}
		// Intenta transformar el código en un número
		int codigo_numerico = 0;
		try {
			codigo_numerico = Integer.parseInt(construccion);
		} catch (Exception ex) {
			codigo_numerico = 0;
		}

		// Si no lo ha podido convertir devuelve nulo

		if (codigo_numerico == 0) {
			return codigo;
		}

		if (codigo_numerico == 3) {
			codigo = ConstantesPDF.VALOR_TIPO_MATRICULACION_CICLOMOTOR;
		} else if ((codigo_numerico >= 40) && (codigo_numerico < 50)) {
			codigo = ConstantesPDF.VALOR_TIPO_MATRICULACION_REMOLQUE;
		} else if (codigo_numerico >= 50) {
			codigo = ConstantesPDF.VALOR_TIPO_MATRICULACION_ESPECIAL;
		} else {
			codigo = ConstantesPDF.VALOR_TIPO_MATRICULACION_ORDINARIA;
		}

		return codigo;
	}

	/**
	 * Método que llamaremos para generar el PDF de etiquetas. Estará todo
	 * prefijado menos el número de etiquetas que imprimiremos por trámite.
	 * 
	 * @param parametrosPegatina
	 * @param listaMatriculaBastidor
	 * @return
	 * @throws Exception
	 */
	public byte[] generarPdfEtiquetas(
			ParametrosPegatinaMatriculacion parametrosPegatina,
			String[] listaMatriculaBastidor) throws Exception {

		byte[] byte1;
		int[] vectPags;
		float tamX = 0;
		float tamY = 0;
		long totalEtiquetas = 0;
		long etiqPagina = 0;
		long etiqPrimeraPag = 0;
		long numPag = 0;
		int num = 1;
		float posX = 0;
		float posY = 0;
		float coordX = 0;
		float coordY = 0;
		Image img = null;
		String matricula = " ";

		// Valores de los parámetros de congifuración de las etiquetas.
		// Integer numEtiquetasFila = ConstantesPDF._5;
		// Integer numEtiquetasColumna = ConstantesPDF._21;
		// Integer primEtiquetaFila = ConstantesPDF._1;
		// Integer primEtiquetaColumna = ConstantesPDF._1;
		// Float margenIzqdo = ConstantesPDF._f0;
		// Float margenSup = ConstantesPDF._f0;
		// Float margenDcho = ConstantesPDF._f0;
		// Float margenInf = ConstantesPDF._f0;
		// // Float margenIzqdo = ConstantesPDF._f17_5;
		// // Float margenSup = ConstantesPDF._f12;
		// // Float margenDcho = ConstantesPDF._f17_5;
		// // Float margenInf = ConstantesPDF._f12;
		// Float espacioHorizontal = ConstantesPDF._f0;
		// Float espacioVertical = ConstantesPDF._f0;

		// Valores de los parámetros de congifuración de las etiquetas.
		Integer numEtiquetasFila = parametrosPegatina.getEtiquetasFila();
		Integer numEtiquetasColumna = parametrosPegatina.getEtiquetasColumna();
		Integer primEtiquetaFila = parametrosPegatina.getFilaPrimer();
		Integer primEtiquetaColumna = parametrosPegatina.getColumnaPrimer();
		Float margenIzqdo = utiles.convertirBigDecimalAFloat(parametrosPegatina
				.getMargenIzqd());
		Float margenSup = utiles.convertirBigDecimalAFloat(parametrosPegatina
				.getMargenSup());
		Float margenDcho = utiles.convertirBigDecimalAFloat(parametrosPegatina
				.getMargenDcho());
		Float margenInf = utiles.convertirBigDecimalAFloat(parametrosPegatina
				.getMargenInf());
		Float espacioHorizontal = utiles
				.convertirBigDecimalAFloat(parametrosPegatina.getHorizontal());
		Float espacioVertical = utiles
				.convertirBigDecimalAFloat(parametrosPegatina.getVertical());

		// Lista de campos que contiene el PDF
		ArrayList<CampoPdfBean> campos = new ArrayList<CampoPdfBean>();
		// Campo del pdf
		CampoPdfBean campo;

		log.trace("Entra en generarPdfEtiquetas");
		log.trace("Empezamos ejecucion de etiquetas");
		// Obtenemos los tramites a utilizar
		List<VehiculoBean> lista = new ArrayList<VehiculoBean>();
		for (String matriculaYBastidor : listaMatriculaBastidor) {
			if (null != matriculaYBastidor) {
				log.trace("Matricula y Bastidor: " + matriculaYBastidor);
				VehiculoBean vehiculo = new VehiculoBean();
				String[] valores = matriculaYBastidor.split(";");
				// Si ha matricula o bastidor
				if (valores.length > 0) {
					// Si el número de caracteres del número de bastidor es
					// menor se pone todo espacios.
					
					/*if (valores[0].length() < ConstantesPDF.NUMERO_CARACTERES_BASTIDOR_PEGATINA) {
						valores[0] = BasicText
								.changeSize(
										"0",
										ConstantesPDF.NUMERO_CARACTERES_BASTIDOR_PEGATINA);
					}*/
					
					vehiculo.setBastidor(valores[0]);
					if (valores.length > 1) {
						vehiculo.setMatricula(valores[1]);
					} else {
						vehiculo.setMatricula(matricula);
					}
				} else {
					vehiculo.setBastidor(BasicText.changeSize("",
							ConstantesPDF.NUMERO_CARACTERES_BASTIDOR_PEGATINA));
					vehiculo.setMatricula(matricula);
				}
				lista.add(vehiculo);
			}
		}

		// Obtenemos valores necesarios para la disposicion de etiquetas
		totalEtiquetas = parametrosPegatina.getEtiquetasTramite()
				* listaMatriculaBastidor.length;
		etiqPagina = numEtiquetasFila * numEtiquetasColumna;
		etiqPrimeraPag = etiqPagina
				- (((primEtiquetaFila - 1) * numEtiquetasFila) + (primEtiquetaColumna - 1));

		// Calculamos el tamaño de la pagina
		tamX = margenIzqdo + (ConstantesPDF.ANCHO_ETIQ * numEtiquetasFila)
				+ (espacioVertical * (numEtiquetasFila - 1)) + margenDcho;
		tamY = margenSup + (ConstantesPDF.ALTO_ETIQ * numEtiquetasColumna)
				+ (espacioHorizontal * (numEtiquetasColumna - 1)) + margenInf;

		tamX = milimetrosAPuntos(tamX);
		tamY = milimetrosAPuntos(tamY);
		posX = primEtiquetaColumna;
		posY = primEtiquetaFila;

		// Generamos el Pdf.
		PdfMaker pdf = new PdfMaker();

		byte1 = pdf.nuevoPdf(tamX, tamY);

		// Calculamos cuantas paginas vamos a necesitar y las creamos
		if (etiqPagina == 1) {
			numPag = totalEtiquetas;
		} else if (etiqPrimeraPag < totalEtiquetas) {
			numPag = ((totalEtiquetas - etiqPrimeraPag) / etiqPagina) + 2;
		} else {
			numPag = 1;
		}
		if (numPag > 1) {
			byte1 = pdf.concatenarPdf(byte1, numPag);
		}

		log.trace("Total de etiquetas: " + totalEtiquetas);
		// Creamos el vector de paginas y lo inicializamos
		vectPags = new int[1];
		vectPags[0] = 1;

		String bastidor = "";
		int indiceTramite = 0;
		for (int ind = 1; ind <= totalEtiquetas; ind++) {
			// log.error("Usr:" + preferencias.getIdUsuario() + " - NumEti:" +
			// ind + " - TotalEti:" + totalEtiquetas + " - Lista:" +
			// listaMatriculaBastidor.length);
			if (num > parametrosPegatina.getEtiquetasTramite()) {
				num = 1;
				indiceTramite = indiceTramite + 1;
			}
			if (num == 1) {
				// Calculamos el codigo de barras
				matricula = lista.get(indiceTramite).getMatricula();
				bastidor = lista.get(indiceTramite).getBastidor();
			   
				if (bastidor.length() > ConstantesPDF.NUMERO_CARACTERES_BASTIDOR_PEGATINA)
				{
					bastidor = bastidor.substring(bastidor.length()
						- ConstantesPDF.NUMERO_CARACTERES_BASTIDOR_PEGATINA);
				}
				String barCode = matricula;

				img = pdf.crearCodigoBarras128(barCode,
						milimetrosAPuntos(ConstantesPDF.ANCHO_BASE_BARRA),
						milimetrosAPuntos(ConstantesPDF.ALTO_BARRA), true);
				// img = pdf.crearCodigoBarras128(barCode,
				// milimetrosAPuntos(ANCHO_BASE_BARRA),
				// milimetrosAPuntos(ALTO_BARRA), true, false, "");
				img = pdf.escalarImagenPorcentaje(img, ConstantesPDF._50,
						ConstantesPDF._100);
			}
			if (posX > numEtiquetasFila) {
				posX = 1;
				posY = posY + 1;
			}
			if (posY > numEtiquetasColumna) {
				posY = 1;
				vectPags[0] = vectPags[0] + 1;
			}

			// Colocamos el código de barras en cada etiqueta (posX, posY).
			coordX = margenIzqdo
					+ ConstantesPDF.POSICION_X_BARCODE
					+ ((posX - 1) * (ConstantesPDF.ANCHO_ETIQ + espacioHorizontal));
			coordY = puntosAmilimetros(tamY)
					- margenSup
					- ConstantesPDF.POSICION_Y_BARCODE
					- ((posY - 1) * (espacioVertical + ConstantesPDF.ALTO_ETIQ));
			// Añadimos el código de barras a la lista de campos a insertar.
			campo = new CampoPdfBean(null, img, milimetrosAPuntos(coordX),
					milimetrosAPuntos(coordY), vectPags[0]);
			campos.add(campo);

			// Insertamos la matricula y el bastidor
			campo = new CampoPdfBean(null, matricula, true, false, 0,
					vectPags[0], milimetrosAPuntos(coordX
							- ConstantesPDF.POSICION_X_MATRICULA),
					milimetrosAPuntos(coordY - ConstantesPDF.POSICION_Y_TEXTO));
			campos.add(campo);
			campo = new CampoPdfBean(null, bastidor, true, false, 0,
					vectPags[0], milimetrosAPuntos(coordX
							+ ConstantesPDF.POSICION_X_BASTIDOR),
					milimetrosAPuntos(coordY - ConstantesPDF.POSICION_Y_TEXTO));
			campos.add(campo);

			posX = posX + 1;
			num = num + 1;
		}
		pdf.establecerFuente(PdfMaker.HELVETICA, true, false,
				milimetrosAPuntos(ConstantesPDF.TAM_FUENTE));
		// Insertar todas las etiquetas en el pdf.
		byte1 = pdf.insertarEtiquetas(byte1, campos);
		log.trace("Terminamos ejecucion de etiquetas");
		return byte1;
	}

	/**
	 * Realiza la conversión de puntos a milimetros
	 * 
	 * @param puntos
	 *            Puntos que se quieren pasar a milimetros
	 * @return Devuelve los milimetros
	 */
	private static float puntosAmilimetros(float puntos) {

		return (puntos * ConstantesPDF.PTO_TO_MM);
	}

	/**
	 * Realiza la conversión de milimetros a puntos
	 * 
	 * @param mm
	 *            Milimetros que se quieren pasar a puntos
	 * @return Devuelve los puntos
	 */
	private static float milimetrosAPuntos(float mm) {

		return (mm * ConstantesPDF.MM_TO_PP);
	}

	// GETTERS AND SETTERS

	protected InputStream getInputStream() {
		return inputStream;
	}

	protected void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	protected String getFileName() {
		return fileName;
	}

	protected void setFileName(String fileName) {
		this.fileName = fileName;
	}

	protected String getFileSize() {
		return fileSize;
	}

	protected void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	
	/**
	 * Mantis: 0001694
	 * Date: 05/06/2012
	 * Author: Carlos García
	 * Método que obtendrá el String de la cadena para la construcción del Código Seguro de Verificación para las transferencias telemáticas.
	 * 
	 * @param tramite
	 * @return String
	 */
	/** protected String obtenerNubeTransmisionTelematicaCSV(
			TramiteTraficoTransmisionBean tramite) {
		SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
		StringBuffer line = new StringBuffer();
		String campo = "";

		campo = tramite.getTramiteTraficoBean().getNumExpediente().toString();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_NUM_EXPEDIENTE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_NUM_EXPEDIENTE));
		}
		
		campo = tramite.getTramiteTraficoBean().getTasa().getCodigoTasa();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_TASA));
//		} else if (campo.equals("EXENTO")) {
//			line.append(BasicText.changeSize(campo, TAM_TASA));
		} else {
			line.append(BasicText.changeSize(campo, TAM_TASA, '0', false));
		}

		campo = tramite.getCodigoTasaInforme();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_TASA));
		} else {
			line.append(BasicText.changeSize(campo, TAM_TASA, '0', false));
		}
		
		campo = tramite.getTramiteTraficoBean().getVehiculo().getMatricula();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_MATRICULA));
		} else {
			campo = utiles.cambiaFormatoMatricula(campo);
			line.append(BasicText.changeSize(campo, TAM_MATRICULA));
		}

		try {
			if (tramite.getTramiteTraficoBean().getVehiculo()
					.getFechaMatriculacion() != null
					&& !tramite.getTramiteTraficoBean().getVehiculo()
							.getFechaMatriculacion().isfechaNula()) {
				campo = formato.format(tramite.getTramiteTraficoBean()
						.getVehiculo().getFechaMatriculacion().getTimestamp());
			} else {
				campo = null;
			}
		} catch (ParseException e) {
			campo = null;
		}
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_FECHA_MATRICULACION));
		} else {
			line.append(BasicText.changeSize(campo, TAM_FECHA_MATRICULACION,
					'0', false));
		}

		try {
			if (tramite.getTramiteTraficoBean().getFechaPresentacion() != null
					&& !tramite.getTramiteTraficoBean().getFechaPresentacion()
							.isfechaNula()) {
				campo = formato.format(tramite.getTramiteTraficoBean()
						.getFechaPresentacion().getTimestamp());
			} else {
				campo = null;
			}
		} catch (ParseException e) {
			campo = null;
		}
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_FECHA_PRESENTACION));
		} else {
			line.append(BasicText.changeSize(campo, TAM_FECHA_PRESENTACION,
					'0', false));
		}

		campo = tramite.getTramiteTraficoBean().getJefaturaTrafico()
				.getJefatura();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_JEFATURA_TRAFICO));
		} else {
			line.append(BasicText.changeSize(campo, TAM_JEFATURA_TRAFICO));
		}

		campo = tramite.getTransmitenteBean().getPersona().getNif();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_DNI_TRANSMITENTE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_DNI_TRANSMITENTE));
		}

		campo = tramite.getAdquirienteBean().getPersona().getNif();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_DNI_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_DNI_ADQUIRENTE));
		}

		if(tramite.getPoseedorBean()!=null && tramite.getPoseedorBean().getPersona()!=null){
			campo = tramite.getPoseedorBean().getPersona().getNif();
		}
		else{
			campo = null;
		}
		
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_DNI_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_DNI_ADQUIRENTE));
		}
		
		try {
			if (tramite.getAdquirienteBean().getPersona()
					.getFechaNacimientoBean() != null
					&& !tramite.getAdquirienteBean().getPersona()
							.getFechaNacimientoBean().isfechaNula()) {
				campo = formato.format(tramite.getAdquirienteBean()
						.getPersona().getFechaNacimientoBean().getTimestamp());
			} else {
				campo = null;
			}
		} catch (ParseException e) {
			campo = null;
		}
		if (campo == null) {
			line.append(BasicText.changeSize("",
					TAM_FECHA_NACIMIENTO_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo,
					TAM_FECHA_NACIMIENTO_ADQUIRENTE, '0', false));
		}

		campo = null != tramite.getAdquirienteBean().getPersona()
				.getTipoPersona() ? tramite.getAdquirienteBean().getPersona()
				.getTipoPersona().getValorEnum() : null;
		if (campo == null || campo.equals(TipoPersona.Juridica.getValorEnum())) {
			campo = tramite.getAdquirienteBean().getPersona()
					.getApellido1RazonSocial();
			if (campo == null) {
				line.append(BasicText.changeSize("",
						TAM_PRIMER_APELLIDO_RAZON_SOCIAL));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_PRIMER_APELLIDO_RAZON_SOCIAL));
			}
		} else {
			campo = tramite.getAdquirienteBean().getPersona()
					.getApellido1RazonSocial();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_PRIMER_APELLIDO));
			} else {
				line.append(BasicText.changeSize(campo, TAM_PRIMER_APELLIDO));
			}

			campo = tramite.getAdquirienteBean().getPersona().getApellido2();
			if (campo == null) {
				line.append(BasicText.changeSize("",
						TAM_SEGUNDO_APELLIDO_ADQUIRENTE));
			} else {
				line.append(BasicText.changeSize(campo,
						TAM_SEGUNDO_APELLIDO_ADQUIRENTE));
			}

			campo = tramite.getAdquirienteBean().getPersona().getNombre();
			if (campo == null) {
				line.append(BasicText.changeSize("", TAM_NOMBRE_ADQUIRENTE));
			} else {
				line.append(BasicText.changeSize(campo, TAM_NOMBRE_ADQUIRENTE));
			}
		}

		campo = tramite.getAdquirienteBean().getPersona().getSexo();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_SEXO_ADQUIRENTE));
		} else if (campo.equals(SEXO_HEMBRA)) {
			line.append(BasicText.changeSize(SEXO_MUJER, TAM_SEXO_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_SEXO_ADQUIRENTE));
		}

		String domicilio = "";
		try {
			if (tramite.getAdquirienteBean().getPersona().getDireccion()
					.getTipoVia().getIdTipoVia() != null) {
				// Realizamos la conversión al tipo de vía DGT
				String tipoViaINE = tramite.getAdquirienteBean().getPersona()
						.getDireccion().getTipoVia().getIdTipoVia();
				String tipoViaDGT = utilesConversiones
						.getIdTipoViaDGTFromIdTipoVia(tipoViaINE);
				String tipoViaConvertido = utilesConversiones
						.getIdTipoViaDGTFromNumeroViaDGT(tipoViaDGT);
				domicilio = !"".equals(tipoViaConvertido)
						&& !" ".equals(tipoViaConvertido)
						&& !"-1".equals(tipoViaConvertido)
						&& !"OT".equals(tipoViaConvertido) ? (tipoViaConvertido
						.length() == 2 ? tipoViaConvertido + " "
						: tipoViaConvertido + " ") : "CL";
			}
		} catch (Throwable e) {
			domicilio = "  ";
		}
		if (tramite.getAdquirienteBean().getPersona().getDireccion()
				.getNombreVia() != null) {
			domicilio = domicilio
					+ UtilesCadenaCaracteres
							.quitarCaracteresExtranios(utilesConversiones
									.ajustarCamposIne(tramite
											.getAdquirienteBean().getPersona()
											.getDireccion().getNombreVia()))
					+ " ";
		}
		if (tramite.getAdquirienteBean().getPersona().getDireccion()
				.getNumero() != null
				&& !tramite.getAdquirienteBean().getPersona().getDireccion()
						.getNumero().toUpperCase().equals("SN")
				&& !tramite.getAdquirienteBean().getPersona().getDireccion()
						.getNumero().toUpperCase().equals("S/N")) {
			domicilio = domicilio
					+ tramite.getAdquirienteBean().getPersona().getDireccion()
							.getNumero();
		}

		// TODO: Analizar si se debe centralizar esto en una sola función como
		// quitarCaracteresExtranios
		// Se eliminan espacios dobles
		domicilio = domicilio.replaceAll("\\s\\s", " ");
		domicilio = domicilio.replaceAll(",", "");
		domicilio = domicilio.replaceAll("º", "");
		domicilio = domicilio.replaceAll("ª", "");
		domicilio = domicilio.replaceAll("/", "");

		if (domicilio.length() > TAM_DOMICILIO_ADQUIRENTE) {
			domicilio = domicilio.substring(0, TAM_DOMICILIO_ADQUIRENTE);
		}

		line.append(BasicText.changeSize(domicilio, TAM_DOMICILIO_ADQUIRENTE));

		String municipioAntiguo = utilesConversiones.convertirMunicipioAntigua(
				tramite.getAdquirienteBean().getPersona().getDireccion()
						.getMunicipio().getIdMunicipio(), tramite
						.getAdquirienteBean().getPersona().getDireccion()
						.getMunicipio().getProvincia().getIdProvincia());
		campo = utilesConversiones.ajustarCamposIne(municipioAntiguo);
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_MUNICIPIO_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_MUNICIPIO_ADQUIRENTE));
		}

		campo = utilesConversiones.ajustarCamposIne(tramite
				.getAdquirienteBean().getPersona().getDireccion().getPueblo());
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_PUEBLO_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_PUEBLO_ADQUIRENTE));
		}

		campo = utilesConversiones.getSiglasFromIdProvincia(tramite
				.getAdquirienteBean().getPersona().getDireccion()
				.getMunicipio().getProvincia().getIdProvincia());
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_PROVINCIA_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_PROVINCIA_ADQUIRENTE));
		}

		campo = tramite.getAdquirienteBean().getPersona().getDireccion()
				.getCodPostal();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_CP_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo, TAM_CP_ADQUIRENTE, '0',
					BasicText.DETRAS));
		}

		campo = "true".equals(tramite.getCambioServicio()) ? "SI": "NO";
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_CAMBIO_SERVICIO));
		} else {
			line.append(BasicText.changeSize(campo, TAM_CAMBIO_SERVICIO));
		}
	
		campo = tramite.getRepresentanteAdquirienteBean().getConceptoRepre() != null ? tramite
				.getRepresentanteAdquirienteBean().getConceptoRepre()
				.getValorEnum()
				: null;
		if (campo != null && ConceptoTutela.Tutela.getValorEnum().equals(campo)) {
			line.append(BasicText.changeSize(VALOR_TUTELA, TAM_TUTELA));
		} else {
			line.append(BasicText.changeSize("", TAM_TUTELA));
		}

		campo = tramite.getFactura();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_FACTURA));
		} else {
			line.append(BasicText.changeSize(campo, TAM_FACTURA));
		}
		
		campo = tramite.getCetItp();
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_CET));
		} else {
			line.append(BasicText.changeSize(campo, TAM_CET));
		}

		//TIPO DE TRANSFERENCIA: CTI, NOT o ENT Según la especificación
		campo = MapeoTipoTransferencia.valorEnumTipoTransferenciaNotEnt(tramite
				.getTipoTransferencia().getValorEnum());
		if (campo == null) {
			line.append(BasicText.changeSize("", TAM_TIPO_TRANSFERENCIA));
		} else {
			if (campo.equals("1")) {
				campo = ConstantesTrafico.VALOR_TIPO_TRANSF_CTI;
			}
			else if(campo.equals("2")){
				campo = ConstantesTrafico.VALOR_TIPO_TRANSF_NOT;
			}
			else if(campo.equals("3")){
				campo = ConstantesTrafico.VALOR_TIPO_TRANSF_ENT;
			}
			line.append(BasicText.changeSize(campo, TAM_TIPO_TRANSFERENCIA));
		}
		
		return line.toString();
	}
*/
	/**
	 * Inserta las firmas en Matriculación y Transmisión.
	 * 
	 * @param detalleTramite
	 * @param detalleTitular
	 * @param byte1
	 * @param vectPags
	 * @param pdf
	 * @param idContrato 
	 * @return
	 * @throws OegamExcepcion
	 */
	protected byte[] insertarFirmasColegiadoYColegio(byte[] byte1, PdfMaker pdf, BigDecimal idContrato, String nifCliente) throws OegamExcepcion {
		String fechaDeHoy = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		ModeloColegiado modeloColegiado = new ModeloColegiado();
		Persona colegiado = modeloColegiado.obtenerColegiadoCompleto(idContrato != null ? idContrato : utilesColegiado.getIdContratoSessionBigDecimal());
		BeanPQDatosColegio beanPQDatosColegio = new BeanPQDatosColegio();
		beanPQDatosColegio.setP_COLEGIO(utilesColegiado.getColegioDelContrato());
		HashMap<String, Object> datosColegio = getModeloTrafico().obtenerDatosColegio(beanPQDatosColegio);

		String firmaGestor = "#" + colegiado.getNif() + "#" + fechaDeHoy;
		// Ciframos la firma, convertimos a hexadecimal y restringimos a los
		// primeros _16 caracteres.
		firmaGestor = utiles.cifrarHMACSHA1(firmaGestor, "ClaveCifrado");
		firmaGestor = utiles.stringToHex(firmaGestor);
		firmaGestor = firmaGestor.substring(0, ConstantesPDF._16);
		byte1 = insertarBarcodeFirma(ConstantesPDF.ID_FIRMA_GESTOR, byte1, null, firmaGestor, pdf);

		// Insertamos Firma del Colegio:
		String firmaColegio = utilesColegiado.getCifContratoSession()
				+ "#"
				+ nifCliente
				+ "#"
				+ colegiado.getNif()
				+ "#"
				+ ((ColegioBean) datosColegio.get(ConstantesPQ.BEANPANTALLA))
						.getCif() + "#" + fechaDeHoy;
		// Ciframos la firma, convertimos a hexadecimal y restringimos a los
		// primeros _16 caracteres.
		firmaColegio = utiles.cifrarHMACSHA1(firmaColegio, "ClaveCifrado");
		firmaColegio = utiles.stringToHex(firmaColegio);
		firmaColegio = firmaColegio.substring(0, ConstantesPDF._16);
		byte1 = insertarBarcodeFirma(ConstantesPDF.ID_FIRMA_COLEGIO, byte1, null, firmaColegio, pdf);

		return byte1;
	}

	/**
	 * Inserta las firmas en Matriculación MATW.
	 * 
	 * @param detalleTramite
	 * @param detalleTitular
	 * @param byte1
	 * @param vectPags
	 * @param pdf
	 * @return
	 * @throws OegamExcepcion
	 */
	protected byte[] insertarFirmaColegio(
			TramiteTraficoBean detalleTramite,
			IntervinienteTrafico detalleTitular, byte[] byte1, int[] vectPags,
			PdfMaker pdf) throws OegamExcepcion {
		String fechaDeHoy = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date());
		ModeloColegiado modeloColegiado = new ModeloColegiado();

		Persona colegiado = modeloColegiado
				.obtenerColegiadoCompleto(detalleTramite.getIdContrato() != null ? detalleTramite
						.getIdContrato()
						: utilesColegiado.getIdContratoSessionBigDecimal());

		BeanPQDatosColegio beanPQDatosColegio = new BeanPQDatosColegio();
		beanPQDatosColegio
				.setP_COLEGIO(utilesColegiado.getColegioDelContrato());

		Map<String, Object> datosColegio = getModeloTrafico()
				.obtenerDatosColegio(beanPQDatosColegio);

		// Insertamos Firma del Colegio:
		String firmaColegio = utilesColegiado.getCifContratoSession()
				+ "#"
				+ (detalleTramite.getVehiculo().getBastidor() != null
						&& !detalleTramite.getVehiculo().getBastidor().equals(
								"") ? detalleTramite.getVehiculo()
						.getBastidor() : "")
				+ "#"
				+ detalleTitular.getPersona().getNif()
				+ "#"
				+ colegiado.getNif()
				+ "#"
				+ ((ColegioBean) datosColegio.get(ConstantesPQ.BEANPANTALLA))
						.getCif() + "#" + fechaDeHoy;
		// Ciframos la firma, convertimos a hexadecimal y restringimos a los
		// primeros _16 caracteres.
		firmaColegio = utiles.cifrarHMACSHA1(firmaColegio,
				"ClaveCifrado");
		firmaColegio = utiles.stringToHex(firmaColegio);
		firmaColegio = firmaColegio.substring(0, ConstantesPDF._16);
		byte1 = insertarBarcodeFirma(ConstantesPDF.ID_FIRMA_COLEGIO, byte1,
				vectPags, firmaColegio, pdf);

		return byte1;
	}

	/**
	 * Genera el documento PDF correspondiente a la solicitud de reconocimiento médico
	 * 
	 * @param ReconocimientoMedico reconocimiento
	 * @param idContrato
	 * @return
	 * @throws OegamExcepcion
	 */
	public ResultBean imprimirPDFReconocimientoMedico(ReconocimientoMedico reconocimiento) throws OegamExcepcion{

		String ruta = "/trafico/plantillasPDF/PlantillaReconocimientoMedico.pdf"; 
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm");

		// En que pagina insertamos las imagenes
		PdfMaker pdf = new PdfMaker();

		UtilResources util = new UtilResources();

		//Abro la plantilla del PDF que corresponda
		byte[] byte1 = pdf.abrirPdf(util.getFilePath(ruta));

		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();

		Persona colegiado = new ModeloColegiado().obtenerColegiadoCompleto(new BigDecimal(reconocimiento.getContrato().getIdContrato()));
		String nombreProf = colegiado.getNombre() + " " + colegiado.getApellido1RazonSocial() + " " + colegiado.getApellido2();

		String direccion = reconocimiento.getPersona().getDireccionActual().getIdTipoVia()
				+ " "
				+ reconocimiento.getPersona().getDireccionActual().getNombreVia()
				+ (reconocimiento.getPersona().getDireccionActual().getNumero() != null
						&& !reconocimiento.getPersona().getDireccionActual().getNumero().trim().isEmpty() ? 
						(" Nº " + reconocimiento.getPersona().getDireccionActual().getNumero())
						: "")
				+ ", "
				+ reconocimiento.getPersona().getDireccionActual().getPueblo()
				+ ", "
				+ reconocimiento.getPersona().getDireccionActual().getMunicipio().getNombre()
				+ " ("
				+ reconocimiento.getPersona().getDireccionActual().getMunicipio().getProvincia().getNombre() + ")";

		//Cuadro principal
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_RECOMED_NOMBRE_PROFESIONAL, nombreProf + ", colegiado número " + reconocimiento.getColegiado().getNumColegiado(), true, false, 10.6F, "Arial"));
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_RECOMED_FECHA_CITA, reconocimiento.getFechaReconocimiento()!=null?sdf.format(reconocimiento.getFechaReconocimiento()):"", true, false, 10.6F, "Arial"));
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_RECOMED_HORA_CITA, reconocimiento.getFechaReconocimiento()!=null?sdfHora.format(reconocimiento.getFechaReconocimiento()):"", true, false, 10.6F, "Arial"));

		// Detalles
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_RECOMED_NIF_CLIENTE, reconocimiento.getPersona().getId().getNif(), false, false, ConstantesPDF._9));
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_RECOMED_NOMBRE_CLIENTE, reconocimiento.getPersona().getNombre(), false, false, ConstantesPDF._9));
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_RECOMED_APELLIDO1_CLIENTE, reconocimiento.getPersona().getApellido1RazonSocial(), false, false, ConstantesPDF._9));
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_RECOMED_APELLIDO2_CLIENTE, reconocimiento.getPersona().getApellido2() != null ? reconocimiento.getPersona().getApellido2() : "", false, false, ConstantesPDF._9));
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_RECOMED_NACIMIENTO_CLIENTE, reconocimiento.getPersona().getFechaNacimiento() != null ? sdf.format(reconocimiento.getPersona().getFechaNacimiento()) : "", false, false, ConstantesPDF._9));
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_RECOMED_CADUCIDAD_CARNET_CLIENTE, reconocimiento.getPersona().getFechaCaducidadCarnet() != null ? sdf.format(reconocimiento.getPersona().getFechaCaducidadCarnet()) : "", false, false, ConstantesPDF._9));
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_RECOMED_TELEFONO_CLIENTE, reconocimiento.getPersona().getTelefonos() != null ? reconocimiento.getPersona().getTelefonos() : "", false, false, ConstantesPDF._9));
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.PDF_RECOMED_DIRECCION_CLIENTE, direccion, false, false, ConstantesPDF._9));

		camposFormateados.add(new CampoPdfBean(ConstantesTrafico.ID_NOMBRE_PROFESIONAL, nombreProf, true, false, ConstantesPDF._9));
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_FECHA_ACTUAL, utilesFecha.getFechaActual().toString(), false, false, ConstantesPDF._11));

		byte1 = pdf.setCampos(byte1, camposFormateados);

		// Firma
		byte1 = insertarFirmasColegiadoYColegio(byte1, pdf, new BigDecimal(reconocimiento.getContrato().getIdContrato()), reconocimiento.getPersona().getId().getNif());

		ResultBean resultadoMetodo = new ResultBean();
		resultadoMetodo.setError(false);
		resultadoMetodo.addAttachment("pdf", byte1);

		return resultadoMetodo;
	}

	protected boolean rejectedCTIT(TramiteTraficoBean tramiteTraficoBean) {
		NotificacionDto filter = new NotificacionDto();
		filter.setIdTramite(tramiteTraficoBean.getNumExpediente());
		filter.setEstadoNue(new BigDecimal(EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum()));
		ServicioNotificacion servicioNotificacion = ContextoSpring.getInstance().getBean(ServicioNotificacion.class);
		List<NotificacionDto> list = servicioNotificacion.getList(filter);
		if (list != null && !list.isEmpty()) {
			if (tramiteTraficoBean.getRespuesta() != null
					&& !"TRÁMITE OK".equals(tramiteTraficoBean.getRespuesta())) {
				return true;
			}
		}
		return false;
	}

	/* *********************************************************************** */
	/* MODELOS *************************************************************** */
	/* *********************************************************************** */

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