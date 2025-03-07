package org.gestoresmadrid.oegam2comun.impresion.util;

import static trafico.utiles.ConstantesPDF.PERSONA_JURIDICA;
import static trafico.utiles.ConstantesPDF.VALOR_N;
import static trafico.utiles.ConstantesPDF.VALOR_S;
import static trafico.utiles.constantes.ConstantesTrafico.CODIGO_ITV;
import static trafico.utiles.constantes.ConstantesTrafico.SIN_CODIGO_MATW;
import static trafico.utiles.constantes.ConstantesTrafico.VALOR_PAIS;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.MotivoBaja;
import org.gestoresmadrid.core.model.enumerados.TipoMotivoExencion;
import org.gestoresmadrid.core.model.enumerados.TipoTutela;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.core.vehiculo.model.enumerados.Alimentacion;
import org.gestoresmadrid.core.vehiculo.model.enumerados.Color;
import org.gestoresmadrid.core.vehiculo.model.enumerados.Combustible;
import org.gestoresmadrid.core.vehiculo.model.enumerados.ConceptoTutela;
import org.gestoresmadrid.core.vehiculo.model.enumerados.Fabricacion;
import org.gestoresmadrid.core.vehiculo.model.enumerados.PaisFabricacion;
import org.gestoresmadrid.core.vehiculo.model.enumerados.PaisImportacion;
import org.gestoresmadrid.core.vehiculo.model.enumerados.Procedencia;
import org.gestoresmadrid.core.vehiculo.model.enumerados.TiposInspeccionItv;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.conversion.Conversiones;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.impresion.ImpresionTramites;
import org.gestoresmadrid.oegam2comun.model.service.ServicioNotificacion;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafBajaDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafMatrDto;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioServicioTrafico;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegam2comun.view.dto.NotificacionDto;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.ServicioTraficoDto;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import general.utiles.BasicText;
import general.utiles.UtilesCadenaCaracteres;
import trafico.beans.jaxb.matw.dgt.tipos.complejos.TipoVehiculoDGT;
import trafico.beans.utiles.CampoPdfBean;
import trafico.utiles.ConstantesPDF;
import trafico.utiles.UtilesMATW;
import trafico.utiles.constantes.ConstantesTrafico;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ImpresionGeneral extends ImpresionTramites {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ImpresionGeneral.class);

	private static final String SIN_CEM = "00000000";

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	Utiles utiles;

	public ImpresionGeneral() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	protected ArrayList<CampoPdfBean> obtenerValoresCamposBaja(Integer tamCampos, Set<String> camposPlantilla, TramiteTrafDto tramiteTraficoDto, ContratoVO contratoVO) throws OegamExcepcion {
		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();
		CampoPdfBean campoAux = null;

		// DATOS GENERALES DE TRÁMITES

		// Insertamos la fecha actual:
		campoAux = new CampoPdfBean(ConstantesPDF.ID_FECHA_ACTUAL, utilesFecha.getFechaActual().toString(), false, false, tamCampos);
		camposFormateados.add(campoAux);

		if (camposPlantilla.contains(ConstantesTrafico.ID_TASA)) {
			if (tramiteTraficoDto.getTasa() != null && tramiteTraficoDto.getTasa().getCodigoTasa() != null) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_TASA, tramiteTraficoDto.getTasa().getCodigoTasa(), true, false, tamCampos);
				camposFormateados.add(campoAux);
			}
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_TIPO_TASA)) {
			if (tramiteTraficoDto.getTasa() != null && tramiteTraficoDto.getTasa().getTipoTasa() != null) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_TIPO_TASA, tramiteTraficoDto.getTasa().getTipoTasa(), true, false, tamCampos);
				camposFormateados.add(campoAux);
			}
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_NUM_EXPEDIENTE)) {
			String numero = (null != tramiteTraficoDto.getNumExpediente()) ? tramiteTraficoDto.getNumExpediente().toString() : "";
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_NUM_EXPEDIENTE, numero, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_MATRI)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_MATRI, tramiteTraficoDto.getVehiculoDto().getMatricula(), true, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_FECHA_MATRI)) {
			if ((null != tramiteTraficoDto.getVehiculoDto() && null != tramiteTraficoDto.getVehiculoDto().getFechaMatriculacion() && tramiteTraficoDto.getVehiculoDto().getFechaMatriculacion()
					.getAnio() != null && tramiteTraficoDto.getVehiculoDto().getFechaMatriculacion().getMes() != null && tramiteTraficoDto.getVehiculoDto().getFechaMatriculacion().getDia() != null)) {
				Fecha fecha = tramiteTraficoDto.getVehiculoDto().getFechaMatriculacion();
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_FECHA_MATRI, fecha.toString(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
		}

		String cem = "";
		cem = null != tramiteTraficoDto.getCem() ? tramiteTraficoDto.getCem() : "";
		if (camposPlantilla.contains(ConstantesTrafico.ID_CEM)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_CEM, cem, true, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_CEMA)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_CEMA, tramiteTraficoDto.getCema(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_FECHA_PRESENTACION)) {
			if (null != tramiteTraficoDto.getFechaPresentacion() && (tramiteTraficoDto.getFechaPresentacion().getAnio() != null || tramiteTraficoDto.getFechaPresentacion().getMes() != null
					|| tramiteTraficoDto.getFechaPresentacion().getDia() != null)) {
				Fecha fecha = tramiteTraficoDto.getFechaPresentacion();
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_FECHA_PRESENTACION, fecha.toString(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_DIA_PRESENTACION)) {
			if (null != tramiteTraficoDto.getFechaPresentacion() && tramiteTraficoDto.getFechaPresentacion().getDia() != null) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_DIA_PRESENTACION, tramiteTraficoDto.getFechaPresentacion().getDia(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_MES_PRESENTACION)) {
			if (null != tramiteTraficoDto.getFechaPresentacion() && tramiteTraficoDto.getFechaPresentacion().getMes() != null) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_MES_PRESENTACION, tramiteTraficoDto.getFechaPresentacion().getMes(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_ANIO_PRESENTACION)) {
			if (null != tramiteTraficoDto.getFechaPresentacion() && tramiteTraficoDto.getFechaPresentacion().getAnio() != null) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_ANIO_PRESENTACION, tramiteTraficoDto.getFechaPresentacion().getAnio(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
		}

		// Jefatura de tráfico
		if (tramiteTraficoDto.getJefaturaTraficoDto() != null) {
			if (camposPlantilla.contains(ConstantesTrafico.ID_JEFATURA_TRAFICO)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_JEFATURA_TRAFICO, tramiteTraficoDto.getJefaturaTraficoDto().getProvinciaDto().getNombre(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
			if (camposPlantilla.contains(ConstantesTrafico.ID_SUCURSAL_JEFATURA)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_SUCURSAL_JEFATURA, tramiteTraficoDto.getJefaturaTraficoDto().getDescripcion(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
		}

		// Datos IEDTM
		if (camposPlantilla.contains(ConstantesTrafico.ID_TIPO_LIMITACION)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_TIPO_LIMITACION, "E".equals(tramiteTraficoDto.getIedtm()) ? "SI" : "NO", false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_FECHA_LIMITACION)) {
			if (tramiteTraficoDto.getFechaIedtm() != null && tramiteTraficoDto.getFechaIedtm().getAnio() != null && tramiteTraficoDto.getFechaIedtm().getMes() != null && tramiteTraficoDto
					.getFechaIedtm().getDia() != null) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_FECHA_LIMITACION, tramiteTraficoDto.getFechaIedtm().toString(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_FINANCIERA_LIMITACION)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_FINANCIERA_LIMITACION, tramiteTraficoDto.getFinancieraIedtm(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_NUM_REG_LIMITACION)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_NUM_REG_LIMITACION, tramiteTraficoDto.getRegIedtm() != null ? tramiteTraficoDto.getRegIedtm() : "", false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		// Datos gestor
		try {
			ServicioPersona servicioPersona = ContextoSpring.getInstance().getBean(ServicioPersona.class);
			PersonaDto colegiado = servicioPersona.obtenerColegiadoCompleto(tramiteTraficoDto.getNumColegiado(), tramiteTraficoDto.getIdContrato());
			if (camposPlantilla.contains(ConstantesTrafico.ID_NUM_PROFESIONAL)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_NUM_PROFESIONAL, tramiteTraficoDto.getNumColegiado(), false, false, ConstantesPDF._9);
				camposFormateados.add(campoAux);
			}
			if (camposPlantilla.contains(ConstantesPDF.ID_GESTOR)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_GESTOR, tramiteTraficoDto.getNumColegiado() + " - " + colegiado.getNombre() + " " + colegiado.getApellido1RazonSocial() + " " + colegiado
						.getApellido2(), false, false, ConstantesPDF._9);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_NOMBRE_PROFESIONAL)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_NOMBRE_PROFESIONAL, colegiado.getNombre() + " " + colegiado.getApellido1RazonSocial() + " " + colegiado.getApellido2(), true, false,
						ConstantesPDF._9);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_GESTORIA)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_GESTORIA, contratoVO.getRazonSocial() + " (" + colegiado.getNif() + ")", false, false, ConstantesPDF._8);
				camposFormateados.add(campoAux);
			}
		} catch (Exception e) {}

		if (camposPlantilla.contains(ConstantesTrafico.ID_CAMBIO_DOMICILIO)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_CAMBIO_DOMICILIO, "true".equals(tramiteTraficoDto.getCambioDomicilio()) ? "SI" : "NO", false, false, ConstantesPDF._6);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_INFORMACION)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_INFORMACION, tramiteTraficoDto.getAnotaciones(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		// DATOS DEL VEHICULO
		tamCampos -= ConstantesPDF._2;
		if (null != tramiteTraficoDto.getVehiculoDto()) {
			VehiculoDto vehiculo = tramiteTraficoDto.getVehiculoDto();
			ServicioVehiculo servicioVehiculo = ContextoSpring.getInstance().getBean(ServicioVehiculo.class);
			if (camposPlantilla.contains(ConstantesTrafico.ID_BASTIDOR)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_BASTIDOR, vehiculo.getBastidor(), false, false, tamCampos + 3);
				camposFormateados.add(campoAux);
			}

			// Subasta
			if (camposPlantilla.contains(ConstantesTrafico.ID_SUBASTA)) {
				String subasta = ConstantesPDF.VALOR_N;
				if (vehiculo.getSubastado().booleanValue() == true) {
					subasta = ConstantesPDF.VALOR_S;
				}
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_SUBASTA, subasta, true, false, tamCampos);
				camposFormateados.add(campoAux);
			}
			// Importacion
			if (camposPlantilla.contains(ConstantesTrafico.ID_IMPORTADO)) {
				String importado = null;
				if (vehiculo.getImportado() != null && vehiculo.getImportado() == true) {
					importado = ConstantesPDF.VALOR_S;
				} else {
					importado = ConstantesPDF.VALOR_N;
				}
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_IMPORTADO, importado, true, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// País de Importación (Según ticket EVG-947-4934 de SEA, se pone el nombre del pais completo, no el identificador)
			if (camposPlantilla.contains(ConstantesTrafico.ID_PAIS_IMPORT)) {
				String pais_imp = "";
				if (vehiculo.getPaisImportacion() != null) {
					pais_imp = PaisImportacion.convertir(vehiculo.getPaisImportacion()).getNombreEnum();
				}
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_PAIS_IMPORT, pais_imp, true, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_CATEGORIA_EU)) {
				String homologacionUE = vehiculo.getIdDirectivaCee();
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_CATEGORIA_EU, homologacionUE, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_DIST_EJES)) {
				/* Distancia entre ejes */
				String distEjes = "";
				if (vehiculo.getDistanciaEjes() != null) {
					distEjes = vehiculo.getDistanciaEjes().toString();
				}
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_DIST_EJES, distEjes, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_VIA_ANT)) {
				/* Via anterior, que se recoge del bean */
				String viaAnt = "";
				if (vehiculo.getViaAnterior() != null) {
					viaAnt = vehiculo.getViaAnterior().toString();
				}
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_VIA_ANT, viaAnt, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
			if (camposPlantilla.contains(ConstantesTrafico.ID_VIA_POST)) {
				/* Via posterior, que se recoge del bean */
				String viaPost = "";
				if (vehiculo.getViaPosterior() != null) {
					viaPost = vehiculo.getViaPosterior().toString();
				}
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_VIA_POST, viaPost, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
			if (camposPlantilla.contains(ConstantesTrafico.ID_CONSUMO)) {
				String campoConsumo = "";
				BigDecimal consumo = vehiculo.getConsumo();
				if (consumo != null)
					campoConsumo = consumo.toString();
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_CONSUMO, campoConsumo, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_FABRICACION)) {
				String fabricacion = vehiculo.getFabricacion() != null ? vehiculo.getFabricacion() : "";

				if (fabricacion != null && Fabricacion.NACIONAL.getValorEnum().equals(fabricacion))
					fabricacion = Fabricacion.NACIONAL.toString();
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_FABRICACION, fabricacion, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_FABRICANTE)) {
				/* Fabricante que se recoge del bean */
				String fabricante = vehiculo.getFabricante();
				if (fabricante == null || fabricante.equalsIgnoreCase(""))
					fabricante = "ND";
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_FABRICANTE, fabricante, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_TIPO_ALIMENTACION)) {
				/* Cuando esté el bean de tipo de Alimentacion se consulta */
				String tipoAlim = vehiculo.getTipoAlimentacion() != null ? vehiculo.getTipoAlimentacion() : "";
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_TIPO_ALIMENTACION, tipoAlim, true, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_NIVEL_EMISIONES)) {
				/* Cuando esté el campo de Nivel de emisiones se consulta */
				String nivelEmisiones = vehiculo.getNivelEmisiones() != null ? vehiculo.getNivelEmisiones().toString() : "";
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_NIVEL_EMISIONES, nivelEmisiones, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_CARROCERIA)) {
				String carroceria = vehiculo.getCarroceria() != null ? vehiculo.getCarroceria() : "";
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_CARROCERIA, carroceria, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_MATRICULA)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_MATRICULA, vehiculo.getMatricula(), true, false, tamCampos + 1);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_CODIGO_ITV)) {
				String codItvAux = vehiculo.getCodItv();
				if (codItvAux == null || codItvAux.length() < ConstantesTrafico.CODIGO_ITV) {
					codItvAux = ConstantesTrafico.SIN_CODIG;
				}
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_CODIGO_ITV, codItvAux, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_TIPO_VEHICULO)) {
				String tipoVehiculoDes = servicioVehiculo.obtenerTipoVehiculoDescripcion(vehiculo.getTipoVehiculo());
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_TIPO_VEHICULO, tipoVehiculoDes, false, false, tamCampos - 2);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_TIPO_MATRICULA)) {
				String tipoMatricula = obtenerCodigoMatriculacion(vehiculo.getCriterioConstruccion());
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_TIPO_MATRICULA, tipoMatricula, false, false, ConstantesPDF._9);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_TIPO_INDUSTRIA)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_TIPO_INDUSTRIA, vehiculo.getTipoIndustria(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_MARCA)) {
				if (vehiculo.getCodigoMarca() != null) {
					String marca = servicioVehiculo.obtenerNombreMarca(vehiculo.getCodigoMarca(), false);
					campoAux = new CampoPdfBean(ConstantesTrafico.ID_MARCA, marca, false, false, tamCampos);
					camposFormateados.add(campoAux);
				}
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_MODELO)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_MODELO, vehiculo.getModelo(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_FECHA_MATRICULACION)) {
				if (null != vehiculo.getFechaMatriculacion() && vehiculo.getFechaMatriculacion().getAnio() != null && vehiculo.getFechaMatriculacion().getMes() != null && vehiculo
						.getFechaMatriculacion().getDia() != null) {
					Fecha fecha = vehiculo.getFechaMatriculacion();
					campoAux = new CampoPdfBean(ConstantesTrafico.ID_FECHA_MATRICULACION, fecha.toString(), false, false, tamCampos);
					camposFormateados.add(campoAux);
				}
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_FECHA_ITV)) {
				if (null != vehiculo.getFechaItv() && (vehiculo.getFechaItv().getAnio() != null || vehiculo.getFechaItv().getMes() != null || vehiculo.getFechaItv().getDia() != null)) {
					Fecha fecha = vehiculo.getFechaItv();
					campoAux = new CampoPdfBean(ConstantesTrafico.ID_FECHA_ITV, fecha.toString(), false, false, tamCampos);
					camposFormateados.add(campoAux);
				}
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_CONCEPTO_ITV)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_CONCEPTO_ITV, vehiculo.getConceptoItv(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_ESTACION_ITV)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_ESTACION_ITV, vehiculo.getEstacionItv(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_FECHA_INSPECCION_ITV)) {

				if (null != vehiculo.getFechaInspeccion() && (vehiculo.getFechaInspeccion().getAnio() != null || vehiculo.getFechaInspeccion().getMes() != null || vehiculo.getFechaInspeccion()
						.getDia() != null)) {
					Fecha fecha = vehiculo.getFechaInspeccion();
					campoAux = new CampoPdfBean(ConstantesTrafico.ID_FECHA_INSPECCION_ITV, fecha.toString(), false, false, tamCampos);
					camposFormateados.add(campoAux);
				}

			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_MOTIVO_ITV)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_MOTIVO_ITV, vehiculo.getMotivoItv(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_RESULTADO_ITV)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_RESULTADO_ITV, vehiculo.getConceptoItv(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
			if (camposPlantilla.contains(ConstantesTrafico.ID_TIPO_ITV)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_TIPO_ITV, vehiculo.getTipoItv(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_VARIANTE)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_VARIANTE, vehiculo.getVariante(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_VERSION)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_VERSION, vehiculo.getVersion(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_TARA)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_TARA, vehiculo.getTara(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_MASA)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_MASA, vehiculo.getPesoMma(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			String mtma = vehiculo.getMtmaItv();

			if (null == mtma || "".equals(mtma)) {
				mtma = "0";
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_MASA_MAX)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_MASA_MAX, mtma, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_NUMERO_RUEDAS)) {
				String numRuedas = (null != vehiculo.getNumRuedas()) ? vehiculo.getNumRuedas().toString() : "";
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_NUMERO_RUEDAS, numRuedas, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_PLAZAS)) {
				String numPlazas = (null != vehiculo.getPlazas()) ? vehiculo.getPlazas().toString() : "";
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_PLAZAS, numPlazas, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_PLAZAS_DE_PIE)) {
				String numPlazas = (null != vehiculo.getNumPlazasPie()) ? vehiculo.getNumPlazasPie().toString() : "";
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_PLAZAS_DE_PIE, numPlazas, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_CARBURANTE)) {
				if (null != vehiculo.getCarburante()) {
					campoAux = new CampoPdfBean(ConstantesTrafico.ID_CARBURANTE, Combustible.convertirAString(vehiculo.getCarburante()), false, false, tamCampos);
					camposFormateados.add(campoAux);
				}
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_CILINDRADA)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_CILINDRADA, vehiculo.getCilindrada(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// Introduce la potencia Fiscal
			if (camposPlantilla.contains(ConstantesTrafico.ID_POTENCIA)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_POTENCIA, null != vehiculo.getPotenciaFiscal() ? utiles.stringToBigDecimalDosDecimales(utiles.BigDecimalToStringDosDecimales(vehiculo
						.getPotenciaFiscal(), ConstantesPDF._5)).toString() : "", false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// Introduce la potencia Neta
			if (camposPlantilla.contains(ConstantesTrafico.ID_POTENCIA_MAX)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_POTENCIA_MAX, null != vehiculo.getPotenciaNeta() ? utiles.stringToBigDecimalDosDecimales(utiles.BigDecimalToStringDosDecimales(vehiculo
						.getPotenciaNeta(), ConstantesPDF._5)).toString() : "", false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_RELACION_POTENCIA_PESO)) {
				BigDecimal relacion = vehiculo.getPotenciaPeso();
				BigDecimal relacionString = utiles.stringToBigDecimalDosDecimales(utiles.BigDecimalToStringDosDecimales(relacion, ConstantesPDF._7));

				String tipoVehiculo = vehiculo.getTipoVehiculo();
				if (tipoVehiculo != null && (tipoVehiculo.equals("50") || tipoVehiculo.equals("51") || tipoVehiculo.equals("52") || tipoVehiculo.equals("53") || tipoVehiculo.equals("54") ||
						tipoVehiculo.equals("90") || tipoVehiculo.equals("91") || tipoVehiculo.equals("92"))) {
					campoAux = new CampoPdfBean(ConstantesTrafico.ID_RELACION_POTENCIA_PESO, null != relacionString ? relacionString.toString() : "", false, false, tamCampos);
					camposFormateados.add(campoAux);
				}
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_POTENCIA_MAX)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_POTENCIA_MAX, null != vehiculo.getPotenciaNeta() ? utiles.stringToBigDecimalDosDecimales(utiles.BigDecimalToStringDosDecimales(vehiculo
						.getPotenciaNeta(), ConstantesPDF._5)).toString() : "", false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_SERVICIO)) {
				String servicioDes = "";
				if (vehiculo.getServicioTrafico().getIdServicio() != null) {
					ServicioServicioTrafico servicioServicioTrafico = ContextoSpring.getInstance().getBean(ServicioServicioTrafico.class);
					ServicioTraficoDto servicio = servicioServicioTrafico.getServicioTrafico(vehiculo.getServicioTrafico().getIdServicio());
					if (servicio != null) {
						servicioDes = servicio.getDescripcion();
					}
				}
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_SERVICIO, servicioDes, false, false, ConstantesPDF._8);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_CAMBIO_SERVICIO)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_CAMBIO_SERVICIO, null != vehiculo.getServicioTraficoAnterior().getIdServicio() ? vehiculo.getServicioTraficoAnterior().getIdServicio()
						: "" + " - " + null != vehiculo.getServicioTraficoAnterior().getDescripcion() ? vehiculo.getServicioTraficoAnterior().getDescripcion() : "", false, false, ConstantesPDF._8);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_NUM_HOMOLOGACION)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_NUM_HOMOLOGACION, vehiculo.getNumHomologacion(), false, false, tamCampos - ConstantesPDF._2);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_MASA_SERVICIO)) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_MASA_SERVICIO, vehiculo.getMom() != null ? vehiculo.getMom().toString() : "", false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_CO2)) {
				String aux = utiles.cambiarFormatoCO2(vehiculo.getCo2());
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_CO2, aux, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_COLOR)) {
				if (null != vehiculo.getColor()) {
					campoAux = new CampoPdfBean(ConstantesTrafico.ID_COLOR, Color.convertir(vehiculo.getColor()).getNombreEnum(), false, false, tamCampos - 1);
					camposFormateados.add(campoAux);
				}
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_FECHA_PRIMERA_MATR)) {
				if (null != vehiculo.getFechaMatriculacion() && (vehiculo.getFechaMatriculacion().getAnio() != null || vehiculo.getFechaMatriculacion().getMes() != null || vehiculo
						.getFechaMatriculacion().getDia() != null)) {
					Fecha fecha = vehiculo.getFechaMatriculacion();
					campoAux = new CampoPdfBean(ConstantesTrafico.ID_FECHA_PRIMERA_MATR, fecha.toString(), false, false, tamCampos);
					camposFormateados.add(campoAux);
				}
			}

			if (camposPlantilla.contains(ConstantesTrafico.ID_NUM_KM_USO)) {
				String kmUso = (null != vehiculo.getKmUso()) ? vehiculo.getKmUso().toString() : "";
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_NUM_KM_USO, kmUso, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// Dirección del vehículo
			if (null != vehiculo.getDireccion()) {
				DireccionDto direccion = vehiculo.getDireccion();
				camposFormateados.addAll(obtenerValoresDireccionBaja(tamCampos - 3, direccion, ConstantesPDF._VEHICULO, camposPlantilla));
			}
		}
		return camposFormateados;
	}

	protected Collection<? extends CampoPdfBean> obtenerValoresIntervinienteBaja(Integer tamCampos, IntervinienteTraficoDto interviniente, String recurso, Set<String> camposPlantilla) {
		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();
		CampoPdfBean campoAux = null;
		// Etiquetas
		if (null != interviniente.getPersona() && null != interviniente.getPersona().getSexo() && interviniente.getPersona().getSexo().equals(ConstantesPDF.PERSONA_JURIDICA)) {
			if (camposPlantilla.contains(ConstantesPDF.TIT_NOMBRE + recurso)) {
				campoAux = new CampoPdfBean(ConstantesPDF.TIT_NOMBRE + recurso, ConstantesPDF.DENOMINACION, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
		} else if (null != interviniente.getPersona() && null != interviniente.getPersona().getSexo() && !interviniente.getPersona().getSexo().equals(ConstantesPDF.PERSONA_JURIDICA)) {
			if (camposPlantilla.contains(ConstantesPDF.TIT_NOMBRE + recurso)) {
				campoAux = new CampoPdfBean(ConstantesPDF.TIT_NOMBRE + recurso, ConstantesPDF.NOMBRE, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesPDF.TIT_PRIMER_APELLIDO + recurso)) {
				campoAux = new CampoPdfBean(ConstantesPDF.TIT_PRIMER_APELLIDO + recurso, ConstantesPDF.APELLIDO1, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesPDF.TIT_SEGUNDO_APELLIDO + recurso)) {
				campoAux = new CampoPdfBean(ConstantesPDF.TIT_SEGUNDO_APELLIDO + recurso, ConstantesPDF.APELLIDO2, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
		}

		// Datos
		if (camposPlantilla.contains(ConstantesTrafico.ID_CAMBIO_DOMICILIO + recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_CAMBIO_DOMICILIO + recurso, (null != interviniente.getCambioDomicilio() ? (interviniente.getCambioDomicilio().equals("true")
					? ConstantesPDF.VALOR_SI : ConstantesPDF.VALOR_NO) : ConstantesPDF.VALOR_NO), true, false, ConstantesPDF._6);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_FLAG_AUTONOMO + recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_FLAG_AUTONOMO + recurso, (null != interviniente.getNifInterviniente() || null != interviniente.getAutonomo()) ? (interviniente
					.getAutonomo().equals("true") ? ConstantesPDF.VALOR_SI : ConstantesPDF.VALOR_NO) : "", true, false, ConstantesPDF._6);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_CODIGO_IAE + recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_CODIGO_IAE + recurso, (null != interviniente.getCodigoIAE() ? interviniente.getCodigoIAE() : ""), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_TUTELA + recurso)) {
			if (null != interviniente.getConceptoRepre()) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_TUTELA + recurso, (ConceptoTutela.Tutela.getValorEnum() == interviniente.getConceptoRepre() ? "SI" : "NO"), false, false,
						ConstantesPDF._8);
				camposFormateados.add(campoAux);
			}
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_TIPO_TUTELA + recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_TIPO_TUTELA + recurso, (null != interviniente.getIdMotivoTutela() ? TipoTutela.convertirTexto(interviniente.getIdMotivoTutela()) : ""),
					false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_TRAMITE_TUTELA + recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_TRAMITE_TUTELA + recurso, (null != interviniente.getDatosDocumento() ? interviniente.getDatosDocumento() : ""), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_CONCEPTO_TUTELA + recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_CONCEPTO_TUTELA + recurso, (null != interviniente.getConceptoRepre() && !"-1".equals(interviniente.getConceptoRepre()) ? interviniente
					.getConceptoRepre() : ""), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		// Para fechas
		if (camposPlantilla.contains(ConstantesTrafico.ID_FECHA_INICIO + recurso)) {
			if (null != interviniente.getFechaInicio() && (interviniente.getFechaInicio().getAnio() != null || interviniente.getFechaInicio().getMes() != null || interviniente.getFechaInicio()
					.getDia() != null)) {
				Fecha fecha = interviniente.getFechaInicio();
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_FECHA_INICIO + recurso, fecha.toString(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
		}
		if (camposPlantilla.contains(ConstantesTrafico.ID_FECHA_FIN + recurso)) {
			if (null != interviniente.getFechaFin() && (interviniente.getFechaFin().getAnio() != null || interviniente.getFechaFin().getMes() != null || interviniente.getFechaFin()
					.getDia() != null)) {
				Fecha fecha = interviniente.getFechaFin();
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_FECHA_FIN + recurso, fecha.toString(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
		}
		if (camposPlantilla.contains(ConstantesTrafico.ID_HORA_INICIO + recurso)) {
			if (null != interviniente.getHoraInicio()) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_HORA_INICIO + recurso, interviniente.getHoraInicio(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
		}
		if (camposPlantilla.contains(ConstantesTrafico.ID_HORA_FIN + recurso)) {
			if (null != interviniente.getHoraFin()) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_HORA_FIN + recurso, interviniente.getHoraFin(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
		}

		PersonaDto persona = interviniente.getPersona();
		camposFormateados.addAll(obtenerValoresPersonaBaja(tamCampos, persona, recurso, camposPlantilla));
		if (null != interviniente.getDireccion()) {
			DireccionDto direccion = interviniente.getDireccion();
			camposFormateados.addAll(obtenerValoresDireccionBaja(tamCampos, direccion, recurso, camposPlantilla));
		}

		return camposFormateados;
	}

	private Collection<? extends CampoPdfBean> obtenerValoresPersonaBaja(Integer tamCampos, PersonaDto persona, String recurso, Set<String> camposPlantilla) {
		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();
		CampoPdfBean campoAux = null;

		if (camposPlantilla.contains(ConstantesTrafico.ID_NOMBRE_APELLIDOS + recurso)) {
			String nombreApellidos = (null != persona.getNombre() ? persona.getNombre() : "") + " " + (null != persona.getApellido1RazonSocial() ? persona.getApellido1RazonSocial() : "") + " "
					+ (null != persona.getApellido2() ? persona.getApellido2() : "");
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_NOMBRE_APELLIDOS + recurso, nombreApellidos, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_FECHA_NACIMIENTO + recurso)) {
			if (null != persona.getFechaNacimiento() && (persona.getFechaNacimiento().getAnio() != null || persona.getFechaNacimiento().getMes() != null || persona.getFechaNacimiento()
					.getDia() != null)) {
				Fecha fecha = persona.getFechaNacimiento();
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_FECHA_NACIMIENTO + recurso, fecha.toString(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_NOMBRE + recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_NOMBRE + recurso, persona.getNombre(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_PRIMER_APELLIDO + recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_PRIMER_APELLIDO + recurso, persona.getApellido1RazonSocial(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_SEGUNDO_APELLIDO + recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_SEGUNDO_APELLIDO + recurso, persona.getApellido2(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_SEXO + recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_SEXO + recurso, persona.getSexo(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_DNI + recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_DNI + recurso, persona.getNif(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		return camposFormateados;
	}

	public Collection<? extends CampoPdfBean> obtenerValoresDireccionBaja(Integer tamCampos, DireccionDto direccion, String recurso, Set<String> camposPlantilla) {
		String municipio = "";
		String provincia = "";
		String tipoVia = "";
		ServicioDireccion servicioDireccion = ContextoSpring.getInstance().getBean(ServicioDireccion.class);
		Conversiones conversiones = ContextoSpring.getInstance().getBean(Conversiones.class);

		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();
		CampoPdfBean campoAux = null;

		if (camposPlantilla.contains(ConstantesTrafico.ID_TIPO_VIA + recurso)) {
			tipoVia = servicioDireccion.obtenerNombreTipoVia(direccion.getIdTipoVia());
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_TIPO_VIA + recurso, tipoVia, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesTrafico.ID_CALLE + recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_CALLE + recurso, conversiones.ajustarCamposIne(direccion.getNombreVia()), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesTrafico.ID_NUM_DOMICILIO + recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_NUM_DOMICILIO + recurso, direccion.getNumero(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesTrafico.ID_KM_DOMICILIO + recurso)) {
			String km = "";
			if (direccion.getKm() != null) {
				km = direccion.getKm().toString();
			}
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_KM_DOMICILIO + recurso, km, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesTrafico.ID_HM_DOMICILIO + recurso)) {
			String hm = "";
			if (direccion.getHm() != null) {
				hm = direccion.getHm().toString();
			}
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_HM_DOMICILIO + recurso, hm, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesTrafico.ID_BLOQUE_DOMICILIO + recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_BLOQUE_DOMICILIO + recurso, direccion.getBloque(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesTrafico.ID_LETRA_DOMICILIO + recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_LETRA_DOMICILIO + recurso, direccion.getLetra(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesTrafico.ID_ESCALERA_DOMICILIO + recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_ESCALERA_DOMICILIO + recurso, direccion.getEscalera(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesTrafico.ID_PISO_DOMICILIO + recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_PISO_DOMICILIO + recurso, direccion.getPlanta(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesTrafico.ID_PUERTA_DOMICILIO + recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_PUERTA_DOMICILIO + recurso, direccion.getPuerta(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesTrafico.ID_MUNICIPIO + recurso)) {
			municipio = servicioDireccion.obtenerNombreMunicipio(direccion.getIdMunicipio(), direccion.getIdProvincia());
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_MUNICIPIO + recurso, conversiones.ajustarCamposIne(municipio), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesTrafico.ID_PUEBLO + recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_PUEBLO + recurso, conversiones.ajustarCamposIne(direccion.getPueblo()), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesTrafico.ID_PROVINCIA + recurso)) {
			provincia = servicioDireccion.obtenerNombreProvincia(direccion.getIdProvincia());
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_PROVINCIA + recurso, provincia, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesTrafico.ID_CP + recurso)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_CP + recurso, direccion.getCodPostal(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesTrafico.ID_DOMICILIO_COMPLETO + recurso)) {
			String domicilioCompleto = null;
			if (tipoVia != null && !"".equals(tipoVia)) {
				domicilioCompleto = tipoVia;
			}
			if (domicilioCompleto != null && direccion.getNombreVia() != null && !direccion.getNombreVia().equals("")) {
				domicilioCompleto += " " + conversiones.ajustarCamposIne(direccion.getNombreVia());
			}
			if (domicilioCompleto != null && direccion.getNumero() != null && !direccion.getNumero().equals("")) {
				domicilioCompleto += ", " + direccion.getNumero();
			}
			if (domicilioCompleto != null && direccion.getLetra() != null && !direccion.getLetra().equals("")) {
				domicilioCompleto += ", letra " + direccion.getLetra();
			}
			if (domicilioCompleto != null && direccion.getEscalera() != null && !direccion.getEscalera().equals("")) {
				domicilioCompleto += ", esc. " + direccion.getEscalera();
			}
			if (domicilioCompleto != null && direccion.getPlanta() != null && !direccion.getPlanta().equals("")) {
				domicilioCompleto += ", piso " + direccion.getPlanta();
			}
			if (domicilioCompleto != null && direccion.getPuerta() != null && !direccion.getPuerta().equals("")) {
				domicilioCompleto += ", puerta " + direccion.getPuerta();
			}
			if (domicilioCompleto != null && direccion.getBloque() != null && !direccion.getBloque().equals("")) {
				domicilioCompleto += ", blq. " + direccion.getBloque();
			}
			if (domicilioCompleto != null && direccion.getKm() != null) {
				domicilioCompleto += ", km. " + direccion.getKm();
			}
			if (domicilioCompleto != null && direccion.getHm() != null) {
				domicilioCompleto += ", hm. " + direccion.getHm();
			}
			if (domicilioCompleto != null && direccion.getCodPostal() != null && !direccion.getCodPostal().equals("")) {
				domicilioCompleto += ", CP. " + direccion.getCodPostal();
			}
			if (domicilioCompleto != null && direccion.getPueblo() != null && !direccion.getPueblo().equals("") && !"-1".equals(direccion.getPueblo())) {
				domicilioCompleto += ", " + conversiones.ajustarCamposIne(direccion.getPueblo());
			}
			if (domicilioCompleto != null && municipio != null && !"".equals(municipio)) {
				domicilioCompleto += ", " + conversiones.ajustarCamposIne(municipio);
			}
			if (domicilioCompleto != null && provincia != null && !"".equals(provincia)) {
				domicilioCompleto += ", " + provincia;
			}
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_DOMICILIO_COMPLETO + recurso, domicilioCompleto != null ? domicilioCompleto : "", false, false, recurso.equals("_VEHICULO") ? tamCampos + 3
					: tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_DOMICILIO_DIRECCION + recurso)) {
			String domicilioDireccion = null;
			if (tipoVia != null && !tipoVia.equals("")) {
				domicilioDireccion = tipoVia;
			}
			if (domicilioDireccion != null && direccion.getNombreVia() != null && !direccion.getNombreVia().equals("")) {
				domicilioDireccion += " " + conversiones.ajustarCamposIne(direccion.getNombreVia());
			}
			if (domicilioDireccion != null && direccion.getNumero() != null && !direccion.getNumero().equals("")) {
				domicilioDireccion += ", " + direccion.getNumero();
			}
			if (domicilioDireccion != null && direccion.getLetra() != null && !direccion.getLetra().equals("")) {
				domicilioDireccion += ", letra " + direccion.getLetra();
			}
			if (domicilioDireccion != null && direccion.getEscalera() != null && !direccion.getEscalera().equals("")) {
				domicilioDireccion += ", esc. " + direccion.getEscalera();
			}
			if (domicilioDireccion != null && direccion.getPlanta() != null && !direccion.getPlanta().equals("")) {
				domicilioDireccion += ", piso " + direccion.getPlanta();
			}
			if (domicilioDireccion != null && direccion.getPuerta() != null && !direccion.getPuerta().equals("")) {
				domicilioDireccion += ", puerta " + direccion.getPuerta();
			}
			if (domicilioDireccion != null && direccion.getBloque() != null && !direccion.getBloque().equals("")) {
				domicilioDireccion += ", blq. " + direccion.getBloque();
			}
			if (domicilioDireccion != null && direccion.getKm() != null) {
				domicilioDireccion += ", km. " + direccion.getKm();
			}
			if (domicilioDireccion != null && direccion.getHm() != null) {
				domicilioDireccion += ", hm. " + direccion.getHm();
			}

			campoAux = new CampoPdfBean(ConstantesTrafico.ID_DOMICILIO_DIRECCION + recurso, domicilioDireccion != null ? domicilioDireccion : "", false, false, recurso.equals("_VEHICULO") ? tamCampos
					+ 3 : tamCampos);
			camposFormateados.add(campoAux);
		}
		return camposFormateados;
	}

	protected Collection<? extends CampoPdfBean> obtenerValoresMandatoMatwDuplicadoYSol(Integer tamCampos, IntervinienteTraficoDto titular, IntervinienteTraficoDto representante,
			Set<String> camposPlantilla, BigDecimal idContrato, String numColegiado) throws OegamExcepcion {
		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();
		CampoPdfBean campoAux = null;
		PersonaDto personaTitular = null;
		PersonaDto personaRepresentante = null;
		String municipio = "";

		ServicioDireccion servicioDireccion = ContextoSpring.getInstance().getBean(ServicioDireccion.class);
		Conversiones conversiones = ContextoSpring.getInstance().getBean(Conversiones.class);

		if (titular != null) {
			personaTitular = titular.getPersona();
			personaTitular.setDireccionDto(titular.getDireccion());
			if (personaTitular.getDireccionDto() != null) {
				municipio = servicioDireccion.obtenerNombreMunicipio(personaTitular.getDireccionDto().getIdMunicipio(), personaTitular.getDireccionDto().getIdProvincia());
			}

			if (personaTitular != null && personaTitular.getSexo() != null && personaTitular.getSexo().equals(PERSONA_JURIDICA)) {
				if (representante != null && representante.getPersona() != null) {
					personaRepresentante = representante.getPersona();
					// Datos de persona física
					if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR)) {
						String nombreApellidos = (null != personaRepresentante.getNombre() ? personaRepresentante.getNombre() : "") + " " + (null != personaRepresentante.getApellido1RazonSocial()
								? personaRepresentante.getApellido1RazonSocial() : "") + " " + (null != personaRepresentante.getApellido2() ? personaRepresentante.getApellido2() : "");
						campoAux = new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR, nombreApellidos, false, false, tamCampos);
						camposFormateados.add(campoAux);
					}

					if (camposPlantilla.contains(ConstantesPDF.ID_DNI_TITULAR)) {
						campoAux = new CampoPdfBean(ConstantesPDF.ID_DNI_TITULAR, personaRepresentante.getNif(), false, false, tamCampos);
						camposFormateados.add(campoAux);
					}
				}

				// Datos de persona jurídica
				if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_ENTIDAD)) {
					campoAux = new CampoPdfBean(ConstantesPDF.ID_NOMBRE_ENTIDAD, personaTitular.getApellido1RazonSocial(), false, false, tamCampos);
					camposFormateados.add(campoAux);
				}

				if (camposPlantilla.contains(ConstantesPDF.CIF_ENTIDAD)) {
					campoAux = new CampoPdfBean(ConstantesPDF.CIF_ENTIDAD, personaTitular.getNif(), false, false, tamCampos);
					camposFormateados.add(campoAux);
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_MUNICIPIO_ENTIDAD)) {
					campoAux = new CampoPdfBean(ConstantesPDF.ID_MUNICIPIO_ENTIDAD, municipio, false, false, tamCampos);
					camposFormateados.add(campoAux);
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_DOMICILIO_ENTIDAD)) {
					campoAux = new CampoPdfBean(ConstantesPDF.ID_DOMICILIO_ENTIDAD, conversiones.ajustarCamposIne(personaTitular.getDireccionDto().getNombreVia()), false, false, tamCampos);
					camposFormateados.add(campoAux);
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_NUM_DOMICILIO_ENTIDAD)) {
					campoAux = new CampoPdfBean(ConstantesPDF.ID_NUM_DOMICILIO_ENTIDAD, personaTitular.getDireccionDto().getNumero(), false, false, tamCampos);
					camposFormateados.add(campoAux);
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_CP_ENTIDAD)) {
					campoAux = new CampoPdfBean(ConstantesPDF.ID_CP_ENTIDAD, personaTitular.getDireccionDto().getCodPostal(), false, false, tamCampos);
					camposFormateados.add(campoAux);
				}

			} else {
				// Datos de persona física
				if (representante != null && representante.getPersona() != null && representante.getPersona().getNif() != null) {
					personaRepresentante = representante.getPersona();
					if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR)) {
						String nombreApellidos = (null != personaRepresentante.getNombre() ? personaRepresentante.getNombre() : "") + " " + (null != personaRepresentante.getApellido1RazonSocial() ? personaRepresentante
								.getApellido1RazonSocial() : "") + " " + (null != personaRepresentante.getApellido2() ? personaRepresentante.getApellido2() : "");
						campoAux = new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR, nombreApellidos, false, false, tamCampos);
						camposFormateados.add(campoAux);
					}
	
					if (camposPlantilla.contains(ConstantesPDF.ID_DNI_TITULAR)) {
						campoAux = new CampoPdfBean(ConstantesPDF.ID_DNI_TITULAR, personaRepresentante.getNif(), false, false, tamCampos);
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
					if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR)) {
						String nombreApellidos = (null != personaTitular.getNombre() ? personaTitular.getNombre() : "") + " " + (null != personaTitular.getApellido1RazonSocial() ? personaTitular
								.getApellido1RazonSocial() : "") + " " + (null != personaTitular.getApellido2() ? personaTitular.getApellido2() : "");
						campoAux = new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR, nombreApellidos, false, false, tamCampos);
						camposFormateados.add(campoAux);
					}
	
					if (camposPlantilla.contains(ConstantesPDF.ID_DNI_TITULAR)) {
						campoAux = new CampoPdfBean(ConstantesPDF.ID_DNI_TITULAR, personaTitular.getNif(), false, false, tamCampos);
						camposFormateados.add(campoAux);
					}
				}
			}

			if (personaTitular != null && personaTitular.getDireccionDto() != null) {
				String nombreVia = conversiones.ajustarCamposIne(personaTitular.getDireccionDto().getNombreVia());
				String nombreViaYMunicipio = "";
				// Picar la dirección si tiene más de 13 caractéres
				if (nombreVia != null && !nombreVia.isEmpty()) {
					nombreViaYMunicipio = nombreVia + (municipio != null ? ", " + municipio : "");
				} else {
					nombreViaYMunicipio = (null != municipio ? municipio : "");
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_PROVINCIA_ENTIDAD)) {
					String provincia = servicioDireccion.obtenerNombreProvincia(personaTitular.getDireccionDto().getIdProvincia());
					campoAux = new CampoPdfBean(ConstantesPDF.ID_PROVINCIA_ENTIDAD, provincia, false, false, tamCampos);
					camposFormateados.add(campoAux);
				}
				if (camposPlantilla.contains(ConstantesPDF.ID_MUNICIPIO_ENTIDAD)) {
					campoAux = new CampoPdfBean(ConstantesPDF.ID_MUNICIPIO_ENTIDAD, municipio, false, false, tamCampos);
					camposFormateados.add(campoAux);
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_DOMICILIO_ENTIDAD)) {
					campoAux = new CampoPdfBean(ConstantesPDF.ID_DOMICILIO_ENTIDAD, nombreVia, false, false, tamCampos);
					camposFormateados.add(campoAux);
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_NUM_DOMICILIO_ENTIDAD)) {
					campoAux = new CampoPdfBean(ConstantesPDF.ID_NUM_DOMICILIO_ENTIDAD, personaTitular.getDireccionDto().getNumero(), false, false, tamCampos);
					camposFormateados.add(campoAux);
				}

				/*
				 * if (camposPlantilla.contains(ConstantesPDF.ID_DOMICILIO_Y_MUNICIPIO_ENTIDAD)) { campoAux = new CampoPdfBean(ConstantesPDF.ID_DOMICILIO_Y_MUNICIPIO_ENTIDAD, nombreViaYMunicipio, false, false, tamCampos); camposFormateados.add(campoAux); }
				 */

				if (camposPlantilla.contains(ConstantesPDF.ID_CP_ENTIDAD)) {
					campoAux = new CampoPdfBean(ConstantesPDF.ID_CP_ENTIDAD, personaTitular.getDireccionDto().getCodPostal(), false, false, tamCampos);
					camposFormateados.add(campoAux);
				}
			}
		}

		ServicioPersona servicioPersona = ContextoSpring.getInstance().getBean(ServicioPersona.class);
		PersonaDto colegiado = servicioPersona.obtenerColegiadoCompleto(numColegiado, idContrato);

		ServicioContrato servicioContrato = ContextoSpring.getInstance().getBean(ServicioContrato.class);
		ContratoDto contrato = servicioContrato.getContratoDto(idContrato);

		if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_GESTOR)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_GESTOR, colegiado.getNombre() + " " + colegiado.getApellido1RazonSocial() + " " + colegiado.getApellido2(), false, false,
					tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_NUM_COLEGIADO_GESTOR)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_NUM_COLEGIADO_GESTOR, numColegiado, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_COLEGIO_GESTOR)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_COLEGIO_GESTOR, contrato.getColegioDto().getColegio(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_MUNICIPIO_GESTOR)) {
			String nombreMunicipio = servicioDireccion.obtenerNombreMunicipio(contrato.getIdMunicipio(), contrato.getIdProvincia());
			campoAux = new CampoPdfBean(ConstantesPDF.ID_MUNICIPIO_GESTOR, nombreMunicipio, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_COLEGIO)) {
			if (contrato.getColegioDto() != null) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_COLEGIO, contrato.getColegioDto().getColegio(), false, false, tamCampos);
			} else {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_COLEGIO, "", false, false, tamCampos);
			}

			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_DOMICILIO_GESTOR)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_DOMICILIO_GESTOR, conversiones.ajustarCamposIne(contrato.getVia()), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.ID_NUM_DOMICILIO_GESTOR)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_NUM_DOMICILIO_GESTOR, contrato.getNumero().toString(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.ID_CP_GESTOR)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_CP_GESTOR, contrato.getCodPostal(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		// Mantis
		// Mantis 25624 NUEVO MANDATO
		// RELLENAMOS LOS DATOS DE LA GESTORIA
		if (camposPlantilla.contains(ConstantesPDF.NIF_GA)) {

			campoAux = new CampoPdfBean(ConstantesPDF.NIF_GA, contrato.getCif(), false, false, tamCampos);

			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_NIF_GA)) {

			campoAux = new CampoPdfBean(ConstantesPDF.ID_NIF_GA, contrato.getCif(), false, false, tamCampos);

			camposFormateados.add(campoAux);
		}

		// provincia
		if (camposPlantilla.contains(ConstantesPDF.ID_DOMICILIO_GA)) {

			String nombreMunicipio = servicioDireccion.obtenerNombreMunicipio(contrato.getIdMunicipio(), contrato.getIdProvincia());
			campoAux = new CampoPdfBean(ConstantesPDF.ID_DOMICILIO_GA, nombreMunicipio, false, false, tamCampos);
			camposFormateados.add(campoAux);

			// campoAux = new CampoPdfBean(ConstantesPDF.ID_DOMICILIO_GA,
			// contrato.getContratoProvincia(), false, false, tamCampos);
			//
			// camposFormateados.add(campoAux);
		}

		// calle
		if (camposPlantilla.contains(ConstantesPDF.ID_VIA_MANDATARIO)) {

			campoAux = new CampoPdfBean(ConstantesPDF.ID_VIA_MANDATARIO, contrato.getVia(), false, false, tamCampos);

			camposFormateados.add(campoAux);
		}

		// Numero
		if (camposPlantilla.contains(ConstantesPDF.ID_VIA_GA)) {

			campoAux = new CampoPdfBean(ConstantesPDF.ID_VIA_GA, contrato.getNumero(), false, false, tamCampos);

			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_CP_GA)) {

			campoAux = new CampoPdfBean(ConstantesPDF.ID_CP_GA, contrato.getCodPostal(), false, false, tamCampos);

			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.ID_MUNICIPIO_MANDANTE)) {

			String nombreMunicipio = servicioDireccion.obtenerNombreMunicipio(contrato.getIdMunicipio(), contrato.getIdProvincia());
			campoAux = new CampoPdfBean(ConstantesPDF.ID_MUNICIPIO_MANDANTE, nombreMunicipio, false, false, tamCampos);
			camposFormateados.add(campoAux);

			// campoAux = new CampoPdfBean(ConstantesPDF.ID_DOMICILIO_GA,
			// contrato.getContratoProvincia(), false, false, tamCampos);
			//
			// camposFormateados.add(campoAux);
		}

		// FIN Mantis 25624 NUEVO MANDATO
		// FIN MANTIS

		return camposFormateados;
	}

	/**
	 * Método que obtendrá un String para la nube de Baja.
	 * @param detalleBaja
	 * @return
	 */
	protected String obtenerNubeBajaNuevo(TramiteTrafBajaDto detalleBaja) {
		SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
		StringBuffer line = new StringBuffer();
		String campo = "";
		String nuevaVersion = gestorPropiedades.valorPropertie("version3.nube.bajas");

		Conversiones conversiones = ContextoSpring.getInstance().getBean(Conversiones.class);

		// Comenzamos a meter los campos de las etiquetas en orden para generar
		// la mancha
		campo = ConstantesPDF.VERSION_BAJA;
		line.append(campo);
		campo = ConstantesPDF.PROGRAMA_BAJA;
		line.append(campo);

		campo = null != detalleBaja.getTasa() ? detalleBaja.getTasa().getCodigoTasa() : null;
		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_TASA));
		} else if (campo.equals("EXENTO")) {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_TASA));
		} else {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_TASA, '0', false));
		}

		try {
			if (detalleBaja.getFechaPresentacion() != null && !detalleBaja.getFechaPresentacion().isfechaNula()) {
				campo = formato.format(detalleBaja.getFechaPresentacion().getTimestamp());
			} else {
				campo = null;
			}
		} catch (ParseException e) {
			campo = null;
		}
		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_FECHA_PRESENTACION));
		} else {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_FECHA_PRESENTACION, '0', false));
		}

		campo = null != detalleBaja.getVehiculoDto() ? detalleBaja.getVehiculoDto().getMatricula() : null;
		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_MATRICULA));
		} else {
			campo = utiles.cambiaFormatoMatricula(campo);
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_MATRICULA));
		}

		try {
			if (detalleBaja.getVehiculoDto().getFechaMatriculacion() != null && !detalleBaja.getVehiculoDto().getFechaMatriculacion().isfechaNula()) {
				campo = formato.format(detalleBaja.getVehiculoDto().getFechaMatriculacion().getTimestamp());
			} else {
				campo = null;
			}
		} catch (ParseException e) {
			campo = null;
		}
		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_FECHA_MATRICULACION));
		} else {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_FECHA_MATRICULACION, '0', false));
		}

		// Datos del titular
		campo = null != detalleBaja.getTitular().getPersona().getNif() ? detalleBaja.getTitular().getPersona().getNif() : null;
		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_DNI_TITULAR));
		} else {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_DNI_TITULAR));
		}

		campo = null != detalleBaja.getMotivoBaja() ? detalleBaja.getMotivoBaja() : null;
		if (ConstantesPDF.VALOR_SI.equals(nuevaVersion)) {
			if (campo == null) {
				line.append(BasicText.changeSize("", ConstantesTrafico.TAM_MOTIVO_BAJA));
			} else {
				line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_MOTIVO_BAJA));
			}
			if (MotivoBaja.DefTC.getValorEnum().equals(campo) || MotivoBaja.DefE.getValorEnum().equals(campo) || MotivoBaja.DefRP.getValorEnum().equals(campo) || MotivoBaja.DefV.getValorEnum().equals(
					campo)) {

				if (ConstantesPDF.VALOR_SERVICIO_AGRICOLA.equals(detalleBaja.getVehiculoDto().getServicioTrafico().getIdServicio())) {

					if (detalleBaja.getCema() != null && !detalleBaja.getCema().equals("")) {
						line.append(BasicText.changeSize(detalleBaja.getCema(), ConstantesTrafico.TAM_CEMA));
					} else {
						line.append(BasicText.changeSize(ConstantesPDF.VALOR_CET_PREDETERMINADO, ConstantesTrafico.TAM_CEMA));
					}

				} else {
					line.append(BasicText.changeSize(ConstantesPDF.VALOR_CET_PREDETERMINADO, ConstantesTrafico.TAM_CEMA));

				}

			}
		} else {

			if (campo == null) {
				line.append(BasicText.changeSize("", ConstantesTrafico.TAM_MOTIVO_BAJA));
			} else {
				line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_MOTIVO_BAJA));
			}

		}

		if (detalleBaja.getAdquiriente() == null) {
			IntervinienteTraficoDto intervinienteTraficoDto = new IntervinienteTraficoDto();
			intervinienteTraficoDto.setPersona(new PersonaDto());
			intervinienteTraficoDto.setDireccion(new DireccionDto());
			detalleBaja.setAdquiriente(intervinienteTraficoDto);
		}

		campo = null != detalleBaja.getTitular().getPersona().getSexo() ? detalleBaja.getAdquiriente().getPersona().getSexo() : null;
		if (campo == null || campo.equals(ConstantesPDF.PERSONA_JURIDICA)) {
			campo = detalleBaja.getAdquiriente().getPersona().getApellido1RazonSocial();
			if (campo == null) {
				line.append(BasicText.changeSize("", ConstantesPDF.TAM_PRIMER_APELLIDO_RAZON_SOCIAL));
			} else {
				line.append(BasicText.changeSize(campo, ConstantesPDF.TAM_PRIMER_APELLIDO_RAZON_SOCIAL));
			}
		} else {
			campo = detalleBaja.getAdquiriente().getPersona().getApellido1RazonSocial();
			if (campo == null) {
				line.append(BasicText.changeSize("", ConstantesPDF.TAM_PRIMER_APELLIDO));
			} else {
				line.append(BasicText.changeSize(campo, ConstantesPDF.TAM_PRIMER_APELLIDO));
			}

			campo = detalleBaja.getAdquiriente().getPersona().getApellido2();
			if (campo == null) {
				line.append(BasicText.changeSize("", ConstantesTrafico.TAM_SEGUNDO_APELLIDO_ADQUIRENTE));
			} else {
				line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_SEGUNDO_APELLIDO_ADQUIRENTE));
			}

			campo = detalleBaja.getAdquiriente().getPersona().getNombre();
			if (campo == null) {
				line.append(BasicText.changeSize("", ConstantesTrafico.TAM_NOMBRE_ADQUIRENTE));
			} else {
				line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_NOMBRE_ADQUIRENTE));
			}
		}

		campo = null != detalleBaja.getTitular().getPersona().getSexo() ? detalleBaja.getAdquiriente().getPersona().getSexo() : null;
		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_SEXO_ADQUIRENTE));
		} else if (campo.equals(ConstantesPDF.SEXO_HEMBRA)) {
			line.append(BasicText.changeSize(ConstantesPDF.SEXO_MUJER, ConstantesTrafico.TAM_SEXO_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_SEXO_ADQUIRENTE));
		}

		// ABRE DETALLE DEL ACQUIRIENTE
		// ***************************************************************

		campo = detalleBaja.getAdquiriente().getPersona().getNif();
		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_DNI_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_DNI_ADQUIRENTE));
		}

		try {
			if (detalleBaja.getAdquiriente().getPersona().getFechaNacimiento() != null && !detalleBaja.getAdquiriente().getPersona().getFechaNacimiento().isfechaNula()) {
				campo = formato.format(detalleBaja.getAdquiriente().getPersona().getFechaNacimiento().getTimestamp());
			} else {
				campo = null;
			}
		} catch (ParseException e) {
			campo = null;
		}
		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_FECHA_NACIMIENTO_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_FECHA_NACIMIENTO_ADQUIRENTE, '0', false));
		}

		String tipoVia = "";
		try {
			if (detalleBaja.getAdquiriente().getDireccion().getIdTipoVia() != null) {
				tipoVia = detalleBaja.getAdquiriente().getDireccion().getIdTipoVia();
			}
		} catch (Throwable e) {
			tipoVia = "";
		}
		line.append(BasicText.changeSize(tipoVia, ConstantesTrafico.TAM_TIPO_VIA_ADQUIRIENTE));

		String nombreVia = "";
		if (detalleBaja.getAdquiriente().getDireccion().getNombreVia() != null) {
			nombreVia = nombreVia + UtilesCadenaCaracteres.quitarCaracteresExtranios(conversiones.ajustarCamposIne(detalleBaja.getAdquiriente().getDireccion().getNombreVia())) + "";
			nombreVia = nombreVia.replaceAll("\\s\\s", " ");
			nombreVia = nombreVia.replaceAll(",", "");
			nombreVia = nombreVia.replaceAll("º", "");
			nombreVia = nombreVia.replaceAll("ª", "");
			nombreVia = nombreVia.replaceAll("/", "");

			if (nombreVia.length() > ConstantesTrafico.TAM_NOMBRE_VIA_ADQUIRIENTE) {
				nombreVia = nombreVia.substring(0, ConstantesTrafico.TAM_NOMBRE_VIA_ADQUIRIENTE);
			}
		}
		line.append(BasicText.changeSize(nombreVia, ConstantesTrafico.TAM_NOMBRE_VIA_ADQUIRIENTE));

		String numVia = "";
		if (detalleBaja.getAdquiriente().getDireccion().getNumero() != null) {
			if (!detalleBaja.getAdquiriente().getDireccion().getNumero().toUpperCase().equals("SN") && !detalleBaja.getAdquiriente().getDireccion().getNumero().toUpperCase().equals("S/N")) {
				numVia = numVia + detalleBaja.getAdquiriente().getDireccion().getNumero();
			} else {
				numVia = "SN";
			}
		} else {
			numVia = "SN";
		}
		line.append(BasicText.changeSize(numVia, ConstantesTrafico.TAM_NUM_VIA_ADQUIRIENTE));

		if (detalleBaja.getAdquiriente().getDireccion().getKm() != null) {
			campo = detalleBaja.getAdquiriente().getDireccion().getKm().toString();
		} else {
			campo = null;
		}
		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_KILOMETRO_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_KILOMETRO_ADQUIRENTE));
		}

		if (detalleBaja.getAdquiriente().getDireccion().getHm() != null) {
			campo = detalleBaja.getAdquiriente().getDireccion().getHm().toString();
		} else {
			campo = null;
		}
		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_HECTOMETRO_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_HECTOMETRO_ADQUIRENTE));
		}

		campo = detalleBaja.getAdquiriente().getDireccion().getBloque();
		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_BLOQUE_DOMICILIO_ADQUIRENTEE));
		} else {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_BLOQUE_DOMICILIO_ADQUIRENTEE));
		}
		campo = detalleBaja.getAdquiriente().getDireccion().getLetra();
		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_PORTAL_DOMICILIO_ADQUIRENTEE));
		} else {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_PORTAL_DOMICILIO_ADQUIRENTEE));
		}

		campo = detalleBaja.getAdquiriente().getDireccion().getEscalera();
		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_ESCALERA_DOMICILIO_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_ESCALERA_DOMICILIO_ADQUIRENTE));
		}

		campo = detalleBaja.getAdquiriente().getDireccion().getPlanta();
		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_PLANTA_DOMICILIO_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_PLANTA_DOMICILIO_ADQUIRENTE));
		}

		campo = detalleBaja.getAdquiriente().getDireccion().getPuerta();
		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_PUERTA_DOMICILIO_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_PUERTA_DOMICILIO_ADQUIRENTE));
		}

		campo = detalleBaja.getAdquiriente().getDireccion().getIdProvincia();
		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_PROVINCIA_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_PROVINCIA_ADQUIRENTE));
		}

		// Si existe provincia tambien existe municipio. El municipio se forma con codigo provincia + codigo municipio
		// Ej. Municipio=28; Provincia=049. Resultado a guardar=28049
		// Por este motivo se concatena campo (que tiene el codigo provincia) con el codigo municipio.
		if (detalleBaja.getAdquiriente().getDireccion().getIdMunicipio() == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_MUNICIPIO_ADQUIRENTE));
		} else {
			campo = campo + detalleBaja.getAdquiriente().getDireccion().getIdMunicipio();
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_MUNICIPIO_ADQUIRENTE));
		}

		campo = conversiones.ajustarCamposIne(detalleBaja.getAdquiriente().getDireccion().getPueblo());
		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_PUEBLO_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_PUEBLO_ADQUIRENTE));
		}

		campo = detalleBaja.getAdquiriente().getDireccion().getCodPostal();
		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_CP_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_CP_ADQUIRENTE, '0', BasicText.DETRAS));
		}
		
		campo = detalleBaja.getMotivoBaja();
		if ((MotivoBaja.DefTC.getValorEnum().equals(campo) || MotivoBaja.DefE.getValorEnum().equals(campo) || MotivoBaja.DefRP.getValorEnum().equals(campo) || MotivoBaja.DefV.getValorEnum().equals(
				campo)) && detalleBaja.getVehiculoDto().getServicioTrafico() != null && "B06".equals(detalleBaja.getVehiculoDto().getServicioTrafico().getIdServicio())) {
			campo = detalleBaja.getCema();
			if (campo == null || campo.length() == 0) {
				line.append(ConstantesTrafico.VALOR_CET_PREDETERMINADO);
			} else {
				line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_CEM));
			}
		}

		return line.toString();
	}

	protected ArrayList<CampoPdfBean> obtenerValoresCamposMatw(Integer tamCampos, Set<String> camposPlantilla, TramiteTrafMatrDto tramiteTrafMatrDto, ContratoVO contratoVO) throws OegamExcepcion {
		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();
		CampoPdfBean campoAux = null;

		if (camposPlantilla.contains(ConstantesPDF.ID_NUME_EXPEDIENTE)) {
			String numero = (null != tramiteTrafMatrDto.getNumExpediente()) ? tramiteTrafMatrDto.getNumExpediente().toString() : "";
			campoAux = new CampoPdfBean(ConstantesPDF.ID_NUME_EXPEDIENTE, numero, false, false, tamCampos + 2);
			camposFormateados.add(campoAux);
		}

		if (tramiteTrafMatrDto.getVehiculoDto() != null) {
			// Bastidor /VIN
			if (camposPlantilla.contains(ConstantesPDF.ID_BASTIDOR_VIN)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_BASTIDOR_VIN, tramiteTrafMatrDto.getVehiculoDto().getBastidor(), false, false, tamCampos + 2);
				camposFormateados.add(campoAux);
			}
			// NIVE
			if (camposPlantilla.contains(ConstantesPDF.ID_NIVE)) {
				String numero = (null != tramiteTrafMatrDto.getVehiculoDto().getNive()) ? tramiteTrafMatrDto.getVehiculoDto().getNive() : "";
				campoAux = new CampoPdfBean(ConstantesPDF.ID_NIVE, numero, false, false, tamCampos + 2);
				camposFormateados.add(campoAux);
			}
		}

		// CEM
		if (camposPlantilla.contains(ConstantesPDF.ID_CEM_MATEW)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_CEM_MATEW, null != tramiteTrafMatrDto.getCem() ? tramiteTrafMatrDto.getCem() : "", true, false, tamCampos + 2);
			camposFormateados.add(campoAux);
		}

		// CEMA
		if (camposPlantilla.contains(ConstantesPDF.ID_CEMA_MTW)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_CEMA_MTW, null != tramiteTrafMatrDto.getCema() ? tramiteTrafMatrDto.getCema() : "", false, false, tamCampos + 2);
			camposFormateados.add(campoAux);
		}

		// MOTIVO DE EXENCION (NumRegIetm)
		if (camposPlantilla.contains(ConstantesPDF.ID_MOTIVO_EXENCION)) {
			TipoMotivoExencion tipoMotivoExencion = TipoMotivoExencion.convertir(tramiteTrafMatrDto.getRegIedtm());
			campoAux = new CampoPdfBean(ConstantesPDF.ID_MOTIVO_EXENCION, tipoMotivoExencion != null ? tipoMotivoExencion.getNombreEnum().substring(5, tipoMotivoExencion.getNombreEnum().length())
					: "", false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		// TASA
		if (camposPlantilla.contains(ConstantesPDF.ID_TASA_MATW)) {
			if (tramiteTrafMatrDto.getTasa() != null && tramiteTrafMatrDto.getTasa().getCodigoTasa() != null) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_TASA_MATW, tramiteTrafMatrDto.getTasa().getCodigoTasa(), true, false, tamCampos + 1);
				camposFormateados.add(campoAux);
			}
		}

		// FECHA DE PRESENTACION
		if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_PRESENTACION_MTW)) {
			if (tramiteTrafMatrDto.getFechaPresentacion() != null && (tramiteTrafMatrDto.getFechaPresentacion().getAnio() != null || tramiteTrafMatrDto.getFechaPresentacion().getMes() != null
					|| tramiteTrafMatrDto.getFechaPresentacion().getDia() != null)) {
				Fecha fecha = tramiteTrafMatrDto.getFechaPresentacion();
				campoAux = new CampoPdfBean(ConstantesPDF.ID_FECHA_PRESENTACION_MTW, fecha.toString(), false, false, tamCampos + 2);
				camposFormateados.add(campoAux);
			}
		}

		// JEFATURA DE TRAFICO
		if (tramiteTrafMatrDto.getJefaturaTraficoDto() != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_JEFATURA_TRAFICO_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_JEFATURA_TRAFICO_MTW, tramiteTrafMatrDto.getJefaturaTraficoDto().getJefaturaProvincial(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_SUCURSAL_JEFATURA_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_SUCURSAL_JEFATURA_MTW, tramiteTrafMatrDto.getJefaturaTraficoDto().getDescripcion(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
		}

		// Datos IEDTM
		if (camposPlantilla.contains(ConstantesPDF.ID_TIPO_LIMITACION)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_TIPO_LIMITACION, "E".equals(tramiteTrafMatrDto.getIedtm()) ? "SI" : "NO", false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		ServicioPersona servicioPersona = ContextoSpring.getInstance().getBean(ServicioPersona.class);
		PersonaDto colegiado = servicioPersona.obtenerColegiadoCompleto(tramiteTrafMatrDto.getNumColegiado(), tramiteTrafMatrDto.getIdContrato());

		if (camposPlantilla.contains(ConstantesPDF.ID_GESTOR_MTW)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_GESTOR_MTW, tramiteTrafMatrDto.getNumColegiado() + " - " + colegiado.getNombre() + " " + colegiado.getApellido1RazonSocial() + " " + colegiado
					.getApellido2(), false, false, ConstantesPDF._9);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_PROFESIONAL_MTW)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_NOMBRE_PROFESIONAL_MTW, colegiado.getNombre() + " " + colegiado.getApellido1RazonSocial() + " " + colegiado.getApellido2(), true, false,
					ConstantesPDF._9);
			camposFormateados.add(campoAux);
		}

		// NOMBRE DEL GESTOR
		if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_GESTOR_MATW)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_NOMBRE_GESTOR_MATW, colegiado.getNombre() + " " + colegiado.getApellido1RazonSocial() + " " + colegiado.getApellido2(), true, false,
					ConstantesPDF._9);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_GESTORIA)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_GESTORIA, contratoVO.getRazonSocial() + " (" + colegiado.getNif() + ")", false, false, ConstantesPDF._8);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.ID_GESTORIA_MTW)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_GESTORIA_MTW, contratoVO.getRazonSocial() + " (" + colegiado.getNif() + ")", false, false, ConstantesPDF._8);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_INFORMACION_MTW)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_INFORMACION_MTW, tramiteTrafMatrDto.getAnotaciones(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		// DATOS DEL VEHICULO
		if (tramiteTrafMatrDto.getVehiculoDto() != null) {

			ServicioVehiculo servicioVehiculo = ContextoSpring.getInstance().getBean(ServicioVehiculo.class);

			// Subasta
			if (camposPlantilla.contains(ConstantesPDF.ID_SUBASTA_MTW)) {
				String subasta = VALOR_N;
				if (tramiteTrafMatrDto.getVehiculoDto().getSubastado()) {
					subasta = VALOR_S;
				}

				campoAux = new CampoPdfBean(ConstantesPDF.ID_SUBASTA_MTW, subasta, true, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// Usado
			if (camposPlantilla.contains(ConstantesPDF.ID_USADO_MTW)) {
				String usado = tramiteTrafMatrDto.getVehiculoDto().getVehiUsado() ? VALOR_S : VALOR_N;
				campoAux = new CampoPdfBean(ConstantesPDF.ID_USADO_MTW, usado, true, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// Importacion
			if (camposPlantilla.contains(ConstantesPDF.ID_IMPORTADO_MTW)) {
				String importado = VALOR_N;
				if (tramiteTrafMatrDto.getVehiculoDto().getImportado() != null && Boolean.TRUE.equals(tramiteTrafMatrDto.getVehiculoDto().getImportado())) {
					importado = VALOR_S;
				}
				campoAux = new CampoPdfBean(ConstantesPDF.ID_IMPORTADO_MTW, importado, true, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// PAIS DE IMPORTACION --
			if (camposPlantilla.contains(ConstantesPDF.ID_PAIS_IMPORT_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_PAIS_IMPORT_MTW, tramiteTrafMatrDto.getVehiculoDto().getProcedencia() != null && tramiteTrafMatrDto.getVehiculoDto().getImportado() != null
						&& tramiteTrafMatrDto.getVehiculoDto().getImportado() != false ? Procedencia.getProcedencia(tramiteTrafMatrDto.getVehiculoDto().getProcedencia()).getNombreEnum() : "", true,
						false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// TIPO TARJETA ITV
			if (camposPlantilla.contains(ConstantesPDF.ID_TIPO_TARJETA_ITV_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_TIPO_TARJETA_ITV_MTW, null != tramiteTrafMatrDto.getVehiculoDto().getTipoTarjetaITV() ? tramiteTrafMatrDto.getVehiculoDto()
						.getTipoTarjetaITV() : "", false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// C0DIGO ITV
			if (camposPlantilla.contains(ConstantesPDF.ID_CODIGO_ITV_MTW)) {
				String codItvAux = tramiteTrafMatrDto.getVehiculoDto().getCodItv();
				if (codItvAux == null || codItvAux.length() < CODIGO_ITV || codItvAux.equals(ConstantesTrafico.SIN_CODIG) || codItvAux.equals(ConstantesTrafico.SIN_CODIGO)) {
					codItvAux = SIN_CODIGO_MATW;
				}
				campoAux = new CampoPdfBean(ConstantesPDF.ID_CODIGO_ITV_MTW, codItvAux, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// TIPO VEHICULO DGT
			if (camposPlantilla.contains(ConstantesPDF.ID_TIPO_VEHICULO_MTW)) {
				String tipoVehiculoDes = servicioVehiculo.obtenerTipoVehiculoDescripcion(tramiteTrafMatrDto.getVehiculoDto().getTipoVehiculo());
				campoAux = new CampoPdfBean(ConstantesPDF.ID_TIPO_VEHICULO_MTW, null != tipoVehiculoDes ? tipoVehiculoDes : "", false, false, tamCampos - 2);
				camposFormateados.add(campoAux);
			}

			// TIPO VEHICULO INDUSTRIA
			if (camposPlantilla.contains(ConstantesPDF.ID_TIPO_VEHICULO_IND_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_TIPO_VEHICULO_IND_MTW, null != tramiteTrafMatrDto.getVehiculoDto().getClasificacionItv() ? tramiteTrafMatrDto.getVehiculoDto()
						.getClasificacionItv() : "", false, false, tamCampos - 2);
				camposFormateados.add(campoAux);
			}

			// CATEGORIA EU
			if (camposPlantilla.contains(ConstantesPDF.ID_CATEGORIA_EU_MTW)) {
				String homologacionUE = tramiteTrafMatrDto.getVehiculoDto().getIdDirectivaCee();
				campoAux = new CampoPdfBean(ConstantesPDF.ID_CATEGORIA_EU_MTW, homologacionUE, false, false, tamCampos + 2);
				camposFormateados.add(campoAux);
			}

			// CARROCERIA
			if (camposPlantilla.contains(ConstantesPDF.ID_CARROCERIA_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_CARROCERIA_MTW, tramiteTrafMatrDto.getVehiculoDto().getCarroceria(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// FECHA CADUCIDAD ITV
			if (camposPlantilla.contains(ConstantesPDF.FECHA_CADUCIDAD_ITV)) {
				campoAux = new CampoPdfBean(ConstantesPDF.FECHA_CADUCIDAD_ITV, tramiteTrafMatrDto.getVehiculoDto().getFechaItv() != null ? tramiteTrafMatrDto.getVehiculoDto().getFechaItv().toString()
						: "", false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// MARCA
			if (camposPlantilla.contains(ConstantesPDF.ID_MARCA_MTW)) {
				String marca = servicioVehiculo.obtenerNombreMarca(tramiteTrafMatrDto.getVehiculoDto().getCodigoMarca(), true);
				campoAux = new CampoPdfBean(ConstantesPDF.ID_MARCA_MTW, marca, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// MARCA BASE
			if (camposPlantilla.contains(ConstantesPDF.ID_MARCA_BASE_MTW)) {
				String marca = servicioVehiculo.obtenerNombreMarca(tramiteTrafMatrDto.getVehiculoDto().getCodigoMarcaBase(), true);
				campoAux = new CampoPdfBean(ConstantesPDF.ID_MARCA_BASE_MTW, marca, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// MODELO
			if (camposPlantilla.contains(ConstantesPDF.ID_MODELO_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_MODELO_MTW, tramiteTrafMatrDto.getVehiculoDto().getModelo(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// FABRICANTE
			if (camposPlantilla.contains(ConstantesPDF.ID_FABRICANTE_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_FABRICANTE_MTW, tramiteTrafMatrDto.getVehiculoDto().getFabricante(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// FABRICANTE BASE
			if (camposPlantilla.contains(ConstantesPDF.ID_FABRICANTE_BASE_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_FABRICANTE_BASE_MTW, tramiteTrafMatrDto.getVehiculoDto().getFabricanteBase(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// TIPO
			String tipoVarianteVersion = "";
			if (camposPlantilla.contains(ConstantesPDF.ID_TIPO_ITV_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_TIPO_ITV_MTW, tramiteTrafMatrDto.getVehiculoDto().getTipoItv(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
			tipoVarianteVersion += tramiteTrafMatrDto.getVehiculoDto().getTipoItv() != null ? tramiteTrafMatrDto.getVehiculoDto().getTipoItv() + " / " : "";

			// VARIANTE
			if (camposPlantilla.contains(ConstantesPDF.ID_VARIANTE_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_VARIANTE_MTW, tramiteTrafMatrDto.getVehiculoDto().getVariante(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
			tipoVarianteVersion += tramiteTrafMatrDto.getVehiculoDto().getVariante() != null ? tramiteTrafMatrDto.getVehiculoDto().getVariante() + " / " : "";

			// VERSION
			if (camposPlantilla.contains(ConstantesPDF.ID_VERSION_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_VERSION_MTW, tramiteTrafMatrDto.getVehiculoDto().getVersion(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
			tipoVarianteVersion += tramiteTrafMatrDto.getVehiculoDto().getVersion() != null ? tramiteTrafMatrDto.getVehiculoDto().getVersion() : "";

			// TIPO, VARIANTE y VERSION
			if (camposPlantilla.contains(ConstantesPDF.ID_TIPO_VARIANTE_VERSION_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_TIPO_VARIANTE_VERSION_MTW, tipoVarianteVersion, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// TIPO BASE
			String tipoVarianteVersionBase = "";
			if (camposPlantilla.contains(ConstantesPDF.ID_TIPO_ITV_BASE_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_TIPO_ITV_BASE_MTW, tramiteTrafMatrDto.getVehiculoDto().getTipoBase(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
			tipoVarianteVersionBase += tramiteTrafMatrDto.getVehiculoDto().getTipoBase() != null ? tramiteTrafMatrDto.getVehiculoDto().getTipoBase() + " / " : "";

			// VARIANTE BASE
			if (camposPlantilla.contains(ConstantesPDF.ID_VARIANTE_BASE_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_VARIANTE_BASE_MTW, tramiteTrafMatrDto.getVehiculoDto().getVarianteBase(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
			tipoVarianteVersionBase += tramiteTrafMatrDto.getVehiculoDto().getVarianteBase() != null ? tramiteTrafMatrDto.getVehiculoDto().getVarianteBase() + " / " : "";

			// VERSION BASE
			if (camposPlantilla.contains(ConstantesPDF.ID_VERSION_BASE_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_VERSION_BASE_MTW, tramiteTrafMatrDto.getVehiculoDto().getVersionBase(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
			tipoVarianteVersionBase += tramiteTrafMatrDto.getVehiculoDto().getVersionBase() != null ? tramiteTrafMatrDto.getVehiculoDto().getVersionBase() : "";

			// TIPO, VARIANTE y VERSION BASES
			if (camposPlantilla.contains(ConstantesPDF.ID_TIPO_VARIANTE_VERSION_BASE_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_TIPO_VARIANTE_VERSION_BASE_MTW, tipoVarianteVersionBase, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// TARA
			if (camposPlantilla.contains(ConstantesPDF.ID_TARA_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_TARA_MTW, tramiteTrafMatrDto.getVehiculoDto().getTara(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// MOM
			if (camposPlantilla.contains(ConstantesPDF.ID_MASA_SERVICIO_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_MASA_SERVICIO_MTW, tramiteTrafMatrDto.getVehiculoDto().getMom() != null ? tramiteTrafMatrDto.getVehiculoDto().getMom().toString() : "",
						false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// MOM BASE
			if (camposPlantilla.contains(ConstantesPDF.ID_MASA_SERVICIO_BASE_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_MASA_SERVICIO_BASE_MTW, tramiteTrafMatrDto.getVehiculoDto().getMomBase() != null ? tramiteTrafMatrDto.getVehiculoDto().getMomBase()
						.toString() : "", false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// MASA MAXIMO AUTO.
			if (camposPlantilla.contains(ConstantesPDF.ID_MASA_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_MASA_MTW, tramiteTrafMatrDto.getVehiculoDto().getPesoMma(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// MTMA
			String mtma = tramiteTrafMatrDto.getVehiculoDto().getMtmaItv();
			if (null == mtma || "".equals(mtma)) {
				mtma = "0";
			}
			if (camposPlantilla.contains(ConstantesPDF.ID_MASA_MAX_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_MASA_MAX_MTW, mtma, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// Nº PLAZAS
			if (camposPlantilla.contains(ConstantesPDF.ID_NUMERO_PLAZAS_MTW)) {
				String numPlazas = (null != tramiteTrafMatrDto.getVehiculoDto().getPlazas()) ? tramiteTrafMatrDto.getVehiculoDto().getPlazas().toString() : "";
				campoAux = new CampoPdfBean(ConstantesPDF.ID_NUMERO_PLAZAS_MTW, numPlazas, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// Nº PLAZAS DE PIE
			if (camposPlantilla.contains(ConstantesPDF.ID_PLAZAS_PIE_MTW)) {
				String numPlazas = (null != tramiteTrafMatrDto.getVehiculoDto().getNumPlazasPie()) ? tramiteTrafMatrDto.getVehiculoDto().getNumPlazasPie().toString() : "0";
				campoAux = new CampoPdfBean(ConstantesPDF.ID_PLAZAS_PIE_MTW, numPlazas, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// DISTANCIA ENTRE EJES
			if (camposPlantilla.contains(ConstantesPDF.ID_DIST_EJES_MTW)) {
				String distEjes = "";
				if (tramiteTrafMatrDto.getVehiculoDto().getDistanciaEjes() != null) {
					distEjes = tramiteTrafMatrDto.getVehiculoDto().getDistanciaEjes().toString();
				}
				campoAux = new CampoPdfBean(ConstantesPDF.ID_DIST_EJES_MTW, distEjes, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// VIA ANTERIOR
			if (camposPlantilla.contains(ConstantesPDF.ID_VIA_ANT_MTW)) {
				String viaAnt = "";
				if (tramiteTrafMatrDto.getVehiculoDto().getViaAnterior() != null) {
					viaAnt = tramiteTrafMatrDto.getVehiculoDto().getViaAnterior().toString();
				}
				campoAux = new CampoPdfBean(ConstantesPDF.ID_VIA_ANT_MTW, viaAnt, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// VIA POSTERIOR
			if (camposPlantilla.contains(ConstantesPDF.ID_VIA_POS_MTW)) {
				/* Via posterior, que se recoge del bean */
				String viaPost = "";
				if (tramiteTrafMatrDto.getVehiculoDto().getViaPosterior() != null) {
					viaPost = tramiteTrafMatrDto.getVehiculoDto().getViaPosterior().toString();
				}
				campoAux = new CampoPdfBean(ConstantesPDF.ID_VIA_POS_MTW, viaPost, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// NEUMATICOS Nº RUEDAS
			if (camposPlantilla.contains(ConstantesPDF.ID_NUMERO_RUEDAS_MTW)) {
				String numRuedas = (null != tramiteTrafMatrDto.getVehiculoDto().getNumRuedas()) ? tramiteTrafMatrDto.getVehiculoDto().getNumRuedas().toString() : "";
				campoAux = new CampoPdfBean(ConstantesPDF.ID_NUMERO_RUEDAS_MTW, numRuedas, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// Nº HOMOLOGACION
			if (camposPlantilla.contains(ConstantesPDF.ID_NUM_HOMOLOGACION_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_NUM_HOMOLOGACION_MTW, tramiteTrafMatrDto.getVehiculoDto().getNumHomologacion(), false, false, tamCampos + 1 - ConstantesPDF._2);
				camposFormateados.add(campoAux);
			}

			// Nº HOMOLOGACION BASE
			if (camposPlantilla.contains(ConstantesPDF.ID_NUM_HOMOLOGACION_BASE_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_NUM_HOMOLOGACION_BASE_MTW, tramiteTrafMatrDto.getVehiculoDto().getNumHomologacionBase(), false, false, tamCampos + 1 - ConstantesPDF._2);
				camposFormateados.add(campoAux);
			}

			// SERVICIO DESTINO
			if (camposPlantilla.contains(ConstantesPDF.ID_SERVICIO_MTW)) {
				String servicioDes = "";
				if (tramiteTrafMatrDto.getVehiculoDto().getServicioTrafico().getIdServicio() != null) {
					ServicioServicioTrafico servicioServicioTrafico = ContextoSpring.getInstance().getBean(ServicioServicioTrafico.class);
					ServicioTraficoDto servicio = servicioServicioTrafico.getServicioTrafico(tramiteTrafMatrDto.getVehiculoDto().getServicioTrafico().getIdServicio());
					if (servicio != null) {
						servicioDes = servicio.getDescripcion();
					}
				}
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_SERVICIO_MTW, servicioDes, false, false, ConstantesPDF._8);
				camposFormateados.add(campoAux);
			}

			// CILINDRADA
			if (camposPlantilla.contains(ConstantesPDF.ID_CILINDRADA_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_CILINDRADA_MTW, tramiteTrafMatrDto.getVehiculoDto().getCilindrada() != null ? tramiteTrafMatrDto.getVehiculoDto().getCilindrada() : "",
						false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// POTENCIA FISCAL
			if (camposPlantilla.contains(ConstantesPDF.ID_POTENCIA_MTW)) {

				campoAux = new CampoPdfBean(ConstantesPDF.ID_POTENCIA_MTW, null != tramiteTrafMatrDto.getVehiculoDto().getPotenciaFiscal() ? utiles.stringToBigDecimalDosDecimales(utiles
						.BigDecimalToStringDosDecimales(tramiteTrafMatrDto.getVehiculoDto().getPotenciaFiscal(), ConstantesPDF._13)).toString() : "", false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// POTENCIA REAL
			if (camposPlantilla.contains(ConstantesPDF.ID_POTENCIA_MAX_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_POTENCIA_MAX_MTW, null != tramiteTrafMatrDto.getVehiculoDto().getPotenciaNeta() ? utiles.stringToBigDecimalTresDecimales(utiles
						.BigDecimalToStringTresDecimales(tramiteTrafMatrDto.getVehiculoDto().getPotenciaNeta(), ConstantesPDF._13)).toString() : "", false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// RELACION POTENCIA/PESO
			if (camposPlantilla.contains(ConstantesPDF.ID_RELACION_POTENCIA_PESO_MTW)) {
				BigDecimal relacionString = tramiteTrafMatrDto.getVehiculoDto().getPotenciaPeso();
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

					String tipoVehiculo = tramiteTrafMatrDto.getVehiculoDto().getTipoVehiculo();

					if (tipoVehiculo != null && (tipoVehiculo.equals("50") || tipoVehiculo.equals("51") || tipoVehiculo.equals("52") || tipoVehiculo.equals("53") || tipoVehiculo.equals("54") ||
							tipoVehiculo.equals("90") || tipoVehiculo.equals("91") || tipoVehiculo.equals("92"))) {

						campoAux = new CampoPdfBean(ConstantesPDF.ID_RELACION_POTENCIA_PESO_MTW, null != tcamp ? tcamp : "", false, false, tamCampos);
						camposFormateados.add(campoAux);
					}
				}
			}

			// CONSUMO Wh/Km
			if (camposPlantilla.contains(ConstantesPDF.ID_CONSUMO_MTW)) {
				/* Hacer conversiones decimales */
				String campoConsumo = "";
				BigDecimal consumo = tramiteTrafMatrDto.getVehiculoDto().getConsumo();
				if (consumo != null) {
					String property = gestorPropiedades.valorPropertie(ConstantesPDF.PROPERTY_COMPLETA_CEROS_ELECTRICOS);
					if (property != null && property.equals(ConstantesPDF.VALOR_CIERTO_PROPERTY_COMPLETA_CEROS_ELECTRICOS)) {
						DecimalFormat formato4 = new DecimalFormat("#0000");
						campoConsumo = formato4.format(consumo);
					} else {
						campoConsumo = consumo.toString();
					}
				}
				campoAux = new CampoPdfBean(ConstantesPDF.ID_CONSUMO_MTW, campoConsumo, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// FABRICACION
			if (camposPlantilla.contains(ConstantesPDF.ID_FABRICACION_MTW)) {
				String fabr = null != tramiteTrafMatrDto.getVehiculoDto().getPaisFabricacion() ? PaisFabricacion.convertirPaisFabricacionBeanEnumerado(tramiteTrafMatrDto.getVehiculoDto()
						.getPaisFabricacion()) : "";
				campoAux = new CampoPdfBean(ConstantesPDF.ID_FABRICACION_MTW, fabr, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// CARBURANTE
			if (camposPlantilla.contains(ConstantesPDF.ID_CARBURANTE_MTW)) {
				if (tramiteTrafMatrDto.getVehiculoDto().getCarburante() != null) {
					String combustible = Combustible.convertirAString(tramiteTrafMatrDto.getVehiculoDto().getCarburante());
					campoAux = new CampoPdfBean(ConstantesPDF.ID_CARBURANTE_MTW, combustible, false, false, tamCampos);
					camposFormateados.add(campoAux);
				}
			}

			// TIPO ALIMENTACION
			if (camposPlantilla.contains(ConstantesPDF.ID_TIPO_ALIMENTACION_MTW)) {
				Alimentacion alimentacion = Alimentacion.convertirAlimentacion(tramiteTrafMatrDto.getVehiculoDto().getTipoAlimentacion());
				String tipoAlim = alimentacion != null ? alimentacion.getNombreEnum() : "";
				campoAux = new CampoPdfBean(ConstantesPDF.ID_TIPO_ALIMENTACION_MTW, tipoAlim, true, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// CO2
			if (camposPlantilla.contains(ConstantesPDF.ID_CO2_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_CO2_MTW, tramiteTrafMatrDto.getVehiculoDto().getCo2(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// CATEGORIA ELECTRICA
			if (camposPlantilla.contains(ConstantesPDF.ID_CATEGORIA_ELECTRICA_MTW)) {
				String categoriaElectrica = tramiteTrafMatrDto.getVehiculoDto().getCategoriaElectrica() != null ? tramiteTrafMatrDto.getVehiculoDto().getCategoriaElectrica() : "";
				campoAux = new CampoPdfBean(ConstantesPDF.ID_CATEGORIA_ELECTRICA_MTW, categoriaElectrica, true, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// AUTONOMIA ELECTRICA
			if (camposPlantilla.contains(ConstantesPDF.ID_AUTONOMIA_ELECTRICA_MTW)) {
				String autonomiaElectrica = tramiteTrafMatrDto.getVehiculoDto().getAutonomiaElectrica() != null ? tramiteTrafMatrDto.getVehiculoDto().getAutonomiaElectrica().toString() : "";
				String property = gestorPropiedades.valorPropertie(ConstantesPDF.PROPERTY_COMPLETA_CEROS_ELECTRICOS);
				if (property != null && property.equals(ConstantesPDF.VALOR_CIERTO_PROPERTY_COMPLETA_CEROS_ELECTRICOS)) {
					DecimalFormat formato4 = new DecimalFormat("#0000");
					if (autonomiaElectrica != null && !autonomiaElectrica.isEmpty()) {
						autonomiaElectrica = formato4.format(new BigDecimal(autonomiaElectrica));
					}
				}
				campoAux = new CampoPdfBean(ConstantesPDF.ID_AUTONOMIA_ELECTRICA_MTW, autonomiaElectrica, true, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// CODIGO ECO
			if (camposPlantilla.contains(ConstantesPDF.ID_CODIGO_ECO_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_CODIGO_ECO_MTW, tramiteTrafMatrDto.getVehiculoDto().getCodigoEco() != null ? tramiteTrafMatrDto.getVehiculoDto().getCodigoEco() : "", true,
						false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// REDUCCION ECO
			if (camposPlantilla.contains(ConstantesPDF.ID_REDUCCION_ECO_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_REDUCCION_ECO_MTW, tramiteTrafMatrDto.getVehiculoDto().getReduccionEco() != null ? tramiteTrafMatrDto.getVehiculoDto().getCodigoEco() : "",
						true, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// ECO INNOVACION
			if (camposPlantilla.contains(ConstantesPDF.ID_ECO_INNOVACION_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_ECO_INNOVACION_MTW, tramiteTrafMatrDto.getVehiculoDto().getEcoInnovacion() != null ? tramiteTrafMatrDto.getVehiculoDto().getCodigoEco()
						: "", true, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// NIVEL DE EMISIONES
			if (camposPlantilla.contains(ConstantesPDF.ID_NIVEL_EMISIONES_MTW)) {
				String nivelEmisiones = tramiteTrafMatrDto.getVehiculoDto().getNivelEmisiones() != null ? tramiteTrafMatrDto.getVehiculoDto().getNivelEmisiones().toString() : "";
				campoAux = new CampoPdfBean(ConstantesPDF.ID_NIVEL_EMISIONES_MTW, nivelEmisiones, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// COLOR
			if (camposPlantilla.contains(ConstantesPDF.ID_COLOR_MTW)) {
				if (tramiteTrafMatrDto.getVehiculoDto().getColor() != null) {
					campoAux = new CampoPdfBean(ConstantesTrafico.ID_COLOR_MTW, Color.convertir(tramiteTrafMatrDto.getVehiculoDto().getColor()).getNombreEnum(), false, false, tamCampos - 1);
					camposFormateados.add(campoAux);
				}
			}

			// FECHA DE PRIMERA MATRICULACION
			if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_PRIMERA_MATR_MTW)) {
				if (null != tramiteTrafMatrDto.getVehiculoDto().getFechaPrimMatri() && (tramiteTrafMatrDto.getVehiculoDto().getFechaPrimMatri().getAnio() != null || tramiteTrafMatrDto.getVehiculoDto()
						.getFechaPrimMatri().getMes() != null || tramiteTrafMatrDto.getVehiculoDto().getFechaPrimMatri().getDia() != null)) {
					Fecha fecha = tramiteTrafMatrDto.getVehiculoDto().getFechaPrimMatri();
					campoAux = new CampoPdfBean(ConstantesPDF.ID_FECHA_PRIMERA_MATR_MTW, fecha.toString(), false, false, tamCampos);
					camposFormateados.add(campoAux);
				}
			}

			// DIRECCION VEHICULO
			if (tramiteTrafMatrDto.getVehiculoDto().getDireccion() != null) {
				camposFormateados.addAll(obtenerValoresDireccionMatw(ConstantesPDF._8, tramiteTrafMatrDto.getVehiculoDto().getDireccion(), 4, camposPlantilla));
			}

			// DATOS ITV
			// MATRICULA ORIGEN
			if (camposPlantilla.contains(ConstantesPDF.MATRICULA_ORIGEN_MTW)) {
				if (tramiteTrafMatrDto.getVehiculoDto().getMatriculaOrigen() != null) {
					// La matricula origen extranjero si que se mostrará
					campoAux = new CampoPdfBean(ConstantesPDF.MATRICULA_ORIGEN_MTW, "", false, false, tamCampos);
					camposFormateados.add(campoAux);

				} else {
					campoAux = new CampoPdfBean(ConstantesPDF.MATRICULA_ORIGEN_MTW, null != tramiteTrafMatrDto.getVehiculoDto().getMatriculaOrigExtr() ? tramiteTrafMatrDto.getVehiculoDto()
							.getMatriculaOrigExtr() : "", false, false, tamCampos);
					camposFormateados.add(campoAux);
				}
			}

			// TIPO INSPECCION
			if (camposPlantilla.contains(ConstantesPDF.TIPO_INSPECCION_ITV)) {
				campoAux = new CampoPdfBean(ConstantesPDF.TIPO_INSPECCION_ITV, null != tramiteTrafMatrDto.getVehiculoDto().getMotivoItv() ? TiposInspeccionItv.getDescripcion(tramiteTrafMatrDto
						.getVehiculoDto().getMotivoItv()) : "", false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// KM ITV
			if (camposPlantilla.contains(ConstantesPDF.KM_ITV_MTW)) {
				if (null != tramiteTrafMatrDto.getVehiculoDto().getKmUso()) {
					campoAux = new CampoPdfBean(ConstantesPDF.KM_ITV_MTW, tramiteTrafMatrDto.getVehiculoDto().getKmUso().toString(), false, false, tamCampos);
					camposFormateados.add(campoAux);
				}
			}

			// HORAS ITV
			if (camposPlantilla.contains(ConstantesPDF.HORAS_ITV_MTW)) {
				if (null != tramiteTrafMatrDto.getVehiculoDto().getHorasUso()) {
					campoAux = new CampoPdfBean(ConstantesPDF.HORAS_ITV_MTW, tramiteTrafMatrDto.getVehiculoDto().getHorasUso().toString(), false, false, tamCampos);
					camposFormateados.add(campoAux);
				}
			}
		}
		return camposFormateados;
	}

	public Collection<? extends CampoPdfBean> obtenerValoresDireccionMatw(Integer tamCampos, DireccionDto direccion, int recurso, Set<String> camposPlantilla) {
		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();
		CampoPdfBean campoAux = null;
		String domicilio = null;

		ServicioDireccion servicioDireccion = ContextoSpring.getInstance().getBean(ServicioDireccion.class);
		Conversiones conversiones = ContextoSpring.getInstance().getBean(Conversiones.class);

		switch (recurso) {
			case 1: {
				domicilio = ConstantesPDF.ID_DOMICILIO_COMPLETO_TITULAR_MTW;
			}
				;
				break;
			// ARRENDATARIO
			case 2: {
				domicilio = ConstantesPDF.ID_DOMICILIO_COMPLETO_ARRENDATARIO_MTW;
			}
				;
				break;
			// CONDUCTOR HABITUAL
			case 3: {
				domicilio = ConstantesPDF.ID_DOMICILIO_COMPLETO_CONDUCTOR_HABITUAL_MTW;
			}
				;
				break;
			// VEHICULO
			case 4: {
				domicilio = ConstantesPDF.ID_DOMICILIO_COMPLETO_MTW_VEHICULO;
			}
				;
				break;
			default: {
				domicilio = "";
			}
				;
				break;
		}

		if (camposPlantilla.contains(domicilio)) {
			String domicilioCompleto = null;
			if (direccion.getIdTipoVia() != null) {
				String tipoViaNombre = servicioDireccion.obtenerNombreTipoVia(direccion.getIdTipoVia());
				domicilioCompleto = tipoViaNombre;
			}
			if (domicilioCompleto != null && direccion.getNombreVia() != null && !direccion.getNombreVia().equals("")) {
				domicilioCompleto += " " + conversiones.ajustarCamposIne(direccion.getNombreVia());
			}
			if (domicilioCompleto != null && direccion.getNumero() != null && !direccion.getNumero().equals("")) {
				domicilioCompleto += ", " + direccion.getNumero();
			}
			if (domicilioCompleto != null && direccion.getLetra() != null && !direccion.getLetra().equals("")) {
				domicilioCompleto += ", puerta " + direccion.getLetra();
			}
			if (domicilioCompleto != null && direccion.getEscalera() != null && !direccion.getEscalera().equals("")) {
				domicilioCompleto += ", esc. " + direccion.getEscalera();
			}
			if (domicilioCompleto != null && direccion.getPlanta() != null && !direccion.getPlanta().equals("")) {
				domicilioCompleto += ", planta " + direccion.getPlanta();
			}
			if (domicilioCompleto != null && direccion.getPuerta() != null && !direccion.getPuerta().equals("")) {
				domicilioCompleto += ", portal " + direccion.getPuerta();
			}
			if (domicilioCompleto != null && direccion.getBloque() != null && !direccion.getBloque().equals("")) {
				domicilioCompleto += ", blq. " + direccion.getBloque();
			}
			if (domicilioCompleto != null && direccion.getKm() != null && !direccion.getKm().equals("")) {
				domicilioCompleto += ", km. " + direccion.getKm();
			}
			if (domicilioCompleto != null && direccion.getHm() != null && !direccion.getHm().equals("")) {
				domicilioCompleto += ", hm. " + direccion.getHm();
			}
			if (domicilioCompleto != null && direccion.getCodPostalCorreos() != null && !direccion.getCodPostalCorreos().equals("")) {
				domicilioCompleto += ", CP. " + direccion.getCodPostalCorreos();
			}
			if (domicilioCompleto != null && direccion.getPuebloCorreos() != null && !direccion.getPuebloCorreos().equals("")) {
				domicilioCompleto += ", " + direccion.getPuebloCorreos();
			}

			if (domicilioCompleto != null && direccion.getIdProvincia() != null) {
				if (direccion.getIdMunicipio() != null) {
					String nombreMunicipio = servicioDireccion.obtenerNombreMunicipio(direccion.getIdMunicipio(), direccion.getIdProvincia());
					domicilioCompleto += ", " + conversiones.ajustarCamposIne(nombreMunicipio);
				}
				String nombreProvincia = servicioDireccion.obtenerNombreProvincia(direccion.getIdProvincia());
				domicilioCompleto += ", " + nombreProvincia;

			}
			campoAux = new CampoPdfBean(domicilio, domicilioCompleto != null ? domicilioCompleto : "", false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		return camposFormateados;
	}

	protected Collection<? extends CampoPdfBean> obtenerValoresIntervinienteMatw(Integer tamCampos, IntervinienteTraficoDto interviniente, int recurso, Set<String> camposPlantilla) {
		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();
		CampoPdfBean campoAux = null;
		String cambio_dom = null;
		String nombre_apellidos = null;
		String fecha_nacimiento = null;
		String sexo_persona = null;
		String dni_persona = null;

		switch (recurso) {
			// TITULAR
			case 1: {
				cambio_dom = ConstantesPDF.ID_CAMBIO_DOMICILIO_MTW;
				nombre_apellidos = ConstantesTrafico.ID_NOMBRE_APELLIDOS_TITULAR_MTW;
				fecha_nacimiento = ConstantesPDF.ID_FECHA_NACIMIENTO_TITULAR_MTW;
				sexo_persona = ConstantesPDF.ID_SEXO_TITULAR_MTW;
				dni_persona = ConstantesPDF.ID_DNI_TITULAR_MTW;

			}
				;
				break;
			// ARRENDATARIO
			case 2: {
				cambio_dom = ConstantesPDF.ID_CAMBIO_DOMICILIO_ARRENDATARIO_MTW;
				nombre_apellidos = ConstantesPDF.ID_NOMBRE_APELLIDOS_ARRENDATARIO_MTW;
				fecha_nacimiento = ConstantesPDF.ID_FECHA_NACIMIENTO_ARRENDATARIO_MTW;
				sexo_persona = ConstantesPDF.ID_SEXO_ARRENDATARIO_MTW;
				dni_persona = ConstantesPDF.ID_DNI_ARRENDATARIO_MTW;

			}
				;
				break;
			// CONDUCTOR HABITUAL
			case 3: {
				cambio_dom = ConstantesPDF.ID_CAMBIO_DOMICILIO_CONDUCTOR_HABITUAL_MTW;
				nombre_apellidos = ConstantesPDF.ID_NOMBRE_APELLIDOS_CONDUCTOR_HABITUAL_MTW;
				fecha_nacimiento = ConstantesPDF.ID_FECHA_NACIMIENTO_CONDUCTOR_HABITUAL_MTW;
				sexo_persona = ConstantesPDF.ID_SEXO_CONDUCTOR_HABITUAL_MTW;
				dni_persona = ConstantesPDF.ID_DNI_CONDUCTOR_HABITUAL_MTW;

			}
				;
				break;
			default: {
				nombre_apellidos = "";
				fecha_nacimiento = "";
				sexo_persona = "";
				dni_persona = "";

			}
				;
				break;
		}

		if (camposPlantilla.contains(cambio_dom)) {
			campoAux = new CampoPdfBean(cambio_dom, (interviniente.getCambioDomicilio() != null && interviniente.getCambioDomicilio() ? VALOR_S : VALOR_N), true, false, ConstantesPDF._6);
			camposFormateados.add(campoAux);
		}

		// SOLO PARA EL TITULAR (FLAG_AUTONOMO Y CODIGO_IAE)
		if (recurso == 1) {
			if (camposPlantilla.contains(ConstantesPDF.ID_FLAG_AUTONOMO_TITULAR_MTW)) {
				String valor = VALOR_N;
				if (interviniente.getNifInterviniente() != null || interviniente.getAutonomo() != null) {
					if (interviniente.getAutonomo()) {
						valor = VALOR_S;
					}
				}
				campoAux = new CampoPdfBean(ConstantesPDF.ID_FLAG_AUTONOMO_TITULAR_MTW, valor, true, false, ConstantesPDF._6);
				camposFormateados.add(campoAux);
			}
			
			if (camposPlantilla.contains(ConstantesPDF.ID_CODIGO_IAE_TITULAR_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_CODIGO_IAE_TITULAR_MTW, (null != interviniente.getCodigoIAE() ? interviniente.getCodigoIAE() : ""), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_TRAMITE_TUTELA + recurso)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_TRAMITE_TUTELA + recurso, (null != interviniente.getDatosDocumento() ? interviniente.getDatosDocumento() : ""), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// Para fechas
			if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_INICIO_TUTELA_TITULAR_MTW)) {
				if (null != interviniente.getFechaInicio() && (interviniente.getFechaInicio().getAnio() != null || interviniente.getFechaInicio().getMes() != null || interviniente.getFechaInicio()
						.getDia() != null)) {
					Fecha fecha = interviniente.getFechaInicio();
					campoAux = new CampoPdfBean(ConstantesPDF.ID_FECHA_INICIO_TUTELA_TITULAR_MTW, fecha.toString(), false, false, tamCampos);
					camposFormateados.add(campoAux);
				}
			}
		}
		// FIN DATOS PROPIOS DEL TITULAR

		// DATOS PROPIOS DEL ARRENDATARIO
		if (recurso == 2) {
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
			fechaHoraInicio += interviniente.getFechaInicio() != null ? interviniente.getFechaInicio().toString() + "  " : "";

			// FECHA FIN RENTING
			if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_FIN_ARRENDATARIO_MTW)) {
				if (null != interviniente.getFechaFin()) {
					String Fech_fin = interviniente.getFechaFin().toString();
					campoAux = new CampoPdfBean(ConstantesPDF.ID_FECHA_FIN_ARRENDATARIO_MTW, Fech_fin, false, false, ConstantesPDF._8);
					camposFormateados.add(campoAux);
				}
			}
			fechaHoraFin += interviniente.getFechaFin() != null ? interviniente.getFechaFin().toString() + "  " : "";

			// HORA INICIO RENTING
			if (camposPlantilla.contains(ConstantesPDF.ID_HORA_INICIO_ARRENDATARIO_MTW)) {
				if (null != interviniente.getHoraInicio()) {
					String Hor_inicio = interviniente.getHoraInicio().toString();
					campoAux = new CampoPdfBean(ConstantesPDF.ID_HORA_INICIO_ARRENDATARIO_MTW, Hor_inicio, false, false, ConstantesPDF._8);
					camposFormateados.add(campoAux);
				}
			}
			fechaHoraInicio += interviniente.getHoraInicio() != null ? interviniente.getHoraInicio().toString() : "";

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
			fechaHoraFin += interviniente.getHoraFin() != null ? interviniente.getHoraFin().toString() : "";

			// FECHA/HORA RENTING INICIO
			if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_HORA_FIN_ARRENDATARIO_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_FECHA_HORA_FIN_ARRENDATARIO_MTW, fechaHoraFin, false, false, ConstantesPDF._8);
				camposFormateados.add(campoAux);
			}
		}

		// campos propios del conductor
		if (recurso == 3) {
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
			fechaHoraFin += interviniente.getFechaFin() != null ? interviniente.getFechaFin().toString() + "  " : "";

			// HORA FIN
			if (camposPlantilla.contains(ConstantesPDF.ID_HORA_FIN_ARRENDATARIO_MTW)) {
				if (null != interviniente.getHoraFin()) {
					String Hor_Fin = interviniente.getHoraFin().toString();
					campoAux = new CampoPdfBean(ConstantesPDF.ID_HORA_FIN_ARRENDATARIO_MTW, Hor_Fin, false, false, ConstantesPDF._8);
					camposFormateados.add(campoAux);
				}
			}
			fechaHoraFin += interviniente.getHoraFin() != null ? interviniente.getHoraFin().toString() : "";

			// FECHA/HORA FIN
			if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_HORA_FIN_CONDUCTOR_HABITUAL_MTW)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_FECHA_HORA_FIN_CONDUCTOR_HABITUAL_MTW, fechaHoraFin, false, false, ConstantesPDF._8);
				camposFormateados.add(campoAux);
			}
		}
		// FIN DE DATOS PROPIOS DEL ARRENDATARIO

		// OBTENEMOS VALORES DE LA PERSONA PARA TODOS LOS TIPOS DE INTERVNIENTES (TITULAR , ARRENDATARIO, CONDUCTOR_HABITUAL

		PersonaDto persona = interviniente.getPersona();
		if (camposPlantilla.contains(nombre_apellidos)) {
			String nombreApellidos = (null != persona.getNombre() ? persona.getNombre() : "") + " " + (null != persona.getApellido1RazonSocial() ? persona.getApellido1RazonSocial() : "") + " "
					+ (null != persona.getApellido2() ? persona.getApellido2() : "");
			campoAux = new CampoPdfBean(nombre_apellidos, nombreApellidos, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(fecha_nacimiento)) {
			if (null != persona.getFechaNacimiento() && (persona.getFechaNacimiento().getAnio() != null || persona.getFechaNacimiento().getMes() != null || persona.getFechaNacimiento()
					.getDia() != null)) {
				Fecha fecha = persona.getFechaNacimiento();
				campoAux = new CampoPdfBean(fecha_nacimiento, fecha.toString(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
		}

		if (camposPlantilla.contains(sexo_persona)) {
			campoAux = new CampoPdfBean(sexo_persona, persona.getSexo(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(dni_persona)) {
			campoAux = new CampoPdfBean(dni_persona, persona.getNif(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		// Dirección DE LA PERSONA
		if (interviniente.getDireccion() != null) {
			DireccionDto direccion = interviniente.getDireccion();
			camposFormateados.addAll(obtenerValoresDireccionMatw(tamCampos, direccion, recurso, camposPlantilla));

		}
		return camposFormateados;
	}

	protected String obtenerNubeMatriculacionMatw(TramiteTrafMatrDto detalleMatriculacion, int mancha_ini, int mancha_fin) {
		StringBuffer line = new StringBuffer();

		// Inicializamos utilesconversiones para la provincia del titular , vehiculo, arrendatario y conductor habitual.
		// Inicializamos ARRAY CON LOS CAMPOS DE LA NUBE
		UtilesMATW ArrayNubeMATW = new UtilesMATW();
		String ncampo = "";
		String campoNube = "";

		for (int i = mancha_ini; i < mancha_fin; i++) {
			ncampo = ArrayNubeMATW.getCampoValorArray(i);
			campoNube = obtenerValorCampoNube(ncampo, detalleMatriculacion);
			if (campoNube != null) {
				line.append(BasicText.changeSize(campoNube, ArrayNubeMATW.getLongitudCampoNube(ncampo)));
			}
		}

		if (log.isDebugEnabled()) {
			log.debug("Nube de puntos: " + line.toString());
		}
		return line.toString();
	}

	public String obtenerValorCampoNube(String campo, TramiteTrafMatrDto detalleMatriculacion) {
		String tcamp = "";
		SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");

		Conversiones conversiones = ContextoSpring.getInstance().getBean(Conversiones.class);
		ServicioVehiculo servicioVehiculo = ContextoSpring.getInstance().getBean(ServicioVehiculo.class);

		// Motivo tutela
		if (campo.equalsIgnoreCase("Tutela")) {
			tcamp = detalleMatriculacion.getRepresentanteTitular() != null && detalleMatriculacion.getRepresentanteTitular().getIdMotivoTutela() != null ? VALOR_S : VALOR_N;
		}

		// Renting
		if (campo.equalsIgnoreCase("Renting")) {
			tcamp = detalleMatriculacion.getRenting() != null && detalleMatriculacion.getRenting() ? VALOR_S : VALOR_N;
		}

		// Conductor habitual
		if (campo.equalsIgnoreCase("Conductor Habitual")) {
			tcamp = detalleMatriculacion.getConductorHabitual() != null && detalleMatriculacion.getConductorHabitual().getPersona().getNif() != null ? VALOR_S : VALOR_N;

		}

		// Num_expediente
		if (campo.equalsIgnoreCase(ConstantesTrafico.ID_NUME_EXPEDIENTE)) {
			tcamp = detalleMatriculacion.getNumExpediente().toString();

		}

		// Tasa
		if (campo.equalsIgnoreCase("Tasa") && detalleMatriculacion.getTasa() != null && detalleMatriculacion.getTasa().getCodigoTasa() != null) {
			tcamp = detalleMatriculacion.getTasa().getCodigoTasa();
		}

		// Tipo de tramite
		if (campo.equalsIgnoreCase("Tipo Tramite") && detalleMatriculacion.getTipoTramiteMatr() != null) {
			tcamp = detalleMatriculacion.getTipoTramiteMatr();
		}

		// Servicio al que se destina el vehiculo
		if (campo.equalsIgnoreCase("Servicio") && detalleMatriculacion.getVehiculoDto().getServicioTrafico().getIdServicio() != null) {
			tcamp = detalleMatriculacion.getVehiculoDto().getServicioTrafico().getIdServicio();

		}

		// Fecha primera matriculación
		try {
			if (campo.equalsIgnoreCase("Fecha prim matriculacion") && detalleMatriculacion.getVehiculoDto().getFechaPrimMatri() != null && !detalleMatriculacion.getVehiculoDto().getFechaPrimMatri()
					.isfechaNula()) {
				tcamp = formato.format(detalleMatriculacion.getVehiculoDto().getFechaPrimMatri().getFecha());
			}
		} catch (ParseException e) {
			tcamp = "";
		}

		// Bastidor
		if (campo.equalsIgnoreCase("Bastidor") && detalleMatriculacion.getVehiculoDto().getBastidor() != null) {
			tcamp = detalleMatriculacion.getVehiculoDto().getBastidor();

		}

		// NIVE
		if (campo.equalsIgnoreCase("NIVE") && detalleMatriculacion.getVehiculoDto().getNive() != null) {
			tcamp = detalleMatriculacion.getVehiculoDto().getNive();
		}

		// KM (Kms a los que se pasa la itv)
		if (campo.equalsIgnoreCase("Kilometraje") && detalleMatriculacion.getVehiculoDto().getKmUso() != null) {
			tcamp = detalleMatriculacion.getVehiculoDto().getKmUso().toString();
		}

		// NUMERO DE HORAS DE USO
		if (campo.equalsIgnoreCase("Cuenta Horas") && detalleMatriculacion.getVehiculoDto().getHorasUso() != null) {
			tcamp = detalleMatriculacion.getVehiculoDto().getHorasUso().toString();
		}

		// Fecha validez itv
		try {
			if (campo.equalsIgnoreCase("Fecha validez ITV") && detalleMatriculacion.getVehiculoDto().getFechaItv() != null && !detalleMatriculacion.getVehiculoDto().getFechaItv().isfechaNula()) {
				tcamp = formato.format(detalleMatriculacion.getVehiculoDto().getFechaItv().getFecha());
			}
		} catch (ParseException e) {
			tcamp = "";
		}

		// Importado
		/*
		 * Un vehiculo solo puede ser importado si es usado. Y solo puede ser usado si tiene rellena la fecha de primera matricula.
		 */
		if (campo.equalsIgnoreCase("Importado")) {
			tcamp = null != detalleMatriculacion.getVehiculoDto().getImportado() && detalleMatriculacion.getVehiculoDto().getImportado() && detalleMatriculacion.getVehiculoDto()
					.getFechaPrimMatri() != null && detalleMatriculacion.getVehiculoDto().getVehiUsado() != null && detalleMatriculacion.getVehiculoDto().getVehiUsado() ? VALOR_S : VALOR_N;
		}

		// Subastado
		if (campo.equalsIgnoreCase("Subasta")) {
			tcamp = null != detalleMatriculacion.getVehiculoDto().getSubastado() && detalleMatriculacion.getVehiculoDto().getSubastado() ? VALOR_S : VALOR_N;
		}

		// USADO
		/*
		 * Un vehiculo solo puede ser usado si además tiene fecha primera matricula.
		 */
		if (campo.equalsIgnoreCase("Usado")) {
			if (detalleMatriculacion.getVehiculoDto().getFechaPrimMatri() != null && detalleMatriculacion.getVehiculoDto().getVehiUsado() != null && detalleMatriculacion.getVehiculoDto()
					.getVehiUsado()) {
				tcamp = VALOR_S;
			} else {
				tcamp = VALOR_N;
			}
		}

		// Matricula origen Extranjera , solo se incluye en la nube si el propertie esta activado
		// se utilizara el campo matricu
		if (campo.equalsIgnoreCase("Matricula Orig Extr")) {
			tcamp = detalleMatriculacion.getVehiculoDto().getMatriculaOrigExtr() != null && detalleMatriculacion.getVehiculoDto().getVehiUsado() != null && detalleMatriculacion.getVehiculoDto()
					.getVehiUsado() && detalleMatriculacion.getVehiculoDto().getImportado() != null && detalleMatriculacion.getVehiculoDto().getImportado() ? detalleMatriculacion.getVehiculoDto()
							.getMatriculaOrigExtr() : "";

		}

		// Tipo de Inspeccion
		if (campo.equalsIgnoreCase("Tipo Inspeccion") && detalleMatriculacion.getVehiculoDto().getMotivoItv() != null) {
			tcamp = detalleMatriculacion.getVehiculoDto().getMotivoItv();
		}

		// **************************** DATOS DEL TITULAR ***********************************************
		// DOI del titular
		if (campo.equalsIgnoreCase("DOI Titular") && detalleMatriculacion.getTitular().getPersona().getNif() != null) {
			tcamp = detalleMatriculacion.getTitular().getPersona().getNif();
		}

		// Razon Social
		if (campo.equalsIgnoreCase("Razon social")) {
			if (detalleMatriculacion.getTitular().getPersona().getTipoPersona() != null && detalleMatriculacion.getTitular().getPersona().getTipoPersona().equals(TipoPersona.Juridica
					.getValorEnum())) {
				tcamp = null != detalleMatriculacion.getTitular().getPersona().getApellido1RazonSocial() ? detalleMatriculacion.getTitular().getPersona().getApellido1RazonSocial() : "";
			}
		}

		// Nombre del titular (Persona fisica)
		if (campo.equalsIgnoreCase("Nombre Titular")) {
			if (detalleMatriculacion.getTitular().getPersona().getTipoPersona() != null && detalleMatriculacion.getTitular().getPersona().getTipoPersona().equals(TipoPersona.Fisica.getValorEnum())) {
				tcamp = null != detalleMatriculacion.getTitular().getPersona().getNombre() ? detalleMatriculacion.getTitular().getPersona().getNombre() : "";
			}
		}

		// Apellido 1 titular (Persona fisica)
		if (campo.equalsIgnoreCase("Apellido1 titular")) {
			if (detalleMatriculacion.getTitular().getPersona().getTipoPersona() != null && detalleMatriculacion.getTitular().getPersona().getTipoPersona().equals(TipoPersona.Fisica.getValorEnum())) {
				tcamp = null != detalleMatriculacion.getTitular().getPersona().getApellido1RazonSocial() ? detalleMatriculacion.getTitular().getPersona().getApellido1RazonSocial() : "";
			}
		}

		// Apellido 2 titular (Persona fisica)
		if (campo.equalsIgnoreCase("Apellido2 titular")) {
			if (detalleMatriculacion.getTitular().getPersona().getTipoPersona() != null && detalleMatriculacion.getTitular().getPersona().getTipoPersona().equals(TipoPersona.Fisica.getValorEnum())) {
				tcamp = null != detalleMatriculacion.getTitular().getPersona().getApellido2() ? detalleMatriculacion.getTitular().getPersona().getApellido2() : "";
			}
		}

		// FECHA DE NACIMIENTO DEL TITULAR
		try {
			if (campo.equalsIgnoreCase("Fecha nacimiento titular")) {
				if (detalleMatriculacion.getTitular().getPersona().getFechaNacimiento() != null && detalleMatriculacion.getTitular().getPersona().getTipoPersona() != null && detalleMatriculacion
						.getTitular().getPersona().getTipoPersona().equals(TipoPersona.Fisica.getValorEnum())) {
					tcamp = null != formato.format(detalleMatriculacion.getTitular().getPersona().getFechaNacimiento().getFecha()).toString() ? formato.format(detalleMatriculacion.getTitular()
							.getPersona().getFechaNacimiento().getFecha()).toString() : "";
				}
			}
		} catch (ParseException e) {
			tcamp = "";
		}

		try {
			if (campo.equalsIgnoreCase("Fecha prim matriculacion") && detalleMatriculacion.getVehiculoDto().getFechaPrimMatri() != null && !detalleMatriculacion.getVehiculoDto().getFechaPrimMatri()
					.isfechaNula()) {
				tcamp = formato.format(detalleMatriculacion.getVehiculoDto().getFechaPrimMatri().getFecha());
			}
		} catch (ParseException e) {
			tcamp = "";
		}

		// Sexo del titular
		// // OJO HAY QUE COMPROBAR SI ES NECESARIO PONERLO PARA UNA EMPRESA Y PONER EL VALOR X - PERSONA JURIDICA
		if (campo.equalsIgnoreCase("Sexo titular") && detalleMatriculacion.getTitular().getPersona().getSexo() != null) {
			tcamp = detalleMatriculacion.getTitular().getPersona().getSexo();
		}

		// NIF del representante del titular (Si es una empresa)
		if (campo.equalsIgnoreCase("DOI Represent titular")) {
			if (detalleMatriculacion.getRepresentanteTitular() != null && detalleMatriculacion.getRepresentanteTitular().getPersona().getNif() != null && detalleMatriculacion.getTitular().getPersona()
					.getTipoPersona().equals(TipoPersona.Juridica.getValorEnum())) {
				tcamp = detalleMatriculacion.getRepresentanteTitular().getPersona().getNif();
			}
		}

		// Datos de Autónomo
		// Autonomo (s,n)
		if (campo.equalsIgnoreCase("Autonomo")) {
			if (detalleMatriculacion.getTitular().getPersona().getTipoPersona() != null && detalleMatriculacion.getTitular().getPersona().getTipoPersona().equals(TipoPersona.Fisica.getValorEnum())) {
				tcamp = detalleMatriculacion.getTitular().getAutonomo() != null && detalleMatriculacion.getTitular().getAutonomo() ? VALOR_S : VALOR_N;
			}
		}

		// Codigo IAE
		if (campo.equalsIgnoreCase("Codigo IAE") && detalleMatriculacion.getTitular().getCodigoIAE() != null) {
			tcamp = detalleMatriculacion.getTitular().getCodigoIAE();
		}

		// CAMBIO DE DOMICILIO DEL TITULAR
		if (campo.equalsIgnoreCase("Cambio domic titular")) {
			tcamp = detalleMatriculacion.getTitular().getCambioDomicilio() != null && detalleMatriculacion.getTitular().getCambioDomicilio() ? VALOR_S : VALOR_N;
		}

		// Se quita comprobar si hay cambio de domicilio. Siempre se envia el domicilio este o no marcado el check de cambio de domicilio.
		// Tipo via del titular
		if (campo.equalsIgnoreCase("Tipo via tit") && detalleMatriculacion.getTitular().getDireccion().getIdTipoVia() != null) {
			tcamp = detalleMatriculacion.getTitular().getDireccion().getIdTipoVia();
		}

		// Nombre via titular
		if (campo.equalsIgnoreCase("Nombre via tit") && detalleMatriculacion.getTitular().getDireccion().getNombreVia() != null) {
			tcamp = conversiones.ajustarCamposIne(detalleMatriculacion.getTitular().getDireccion().getNombreVia());
		}

		// Numero del titular
		if (campo.equalsIgnoreCase("Numero via tit") && detalleMatriculacion.getTitular().getDireccion().getNumero() != null) {
			tcamp = detalleMatriculacion.getTitular().getDireccion().getNumero();
		}

		// km del titular
		if (campo.equalsIgnoreCase("Kilometro tit") && detalleMatriculacion.getTitular().getDireccion().getKm() != null) {
			tcamp = detalleMatriculacion.getTitular().getDireccion().getKm().toString();
		}

		// Hm del titular
		if (campo.equalsIgnoreCase("Hectometro tit") && detalleMatriculacion.getTitular().getDireccion().getHm() != null) {
			tcamp = detalleMatriculacion.getTitular().getDireccion().getHm().toString();
		}

		// bloque titular
		if (campo.equalsIgnoreCase("Bloque tit") && detalleMatriculacion.getTitular().getDireccion().getBloque() != null) {
			tcamp = detalleMatriculacion.getTitular().getDireccion().getBloque();
		}

		// Portal
		if (campo.equalsIgnoreCase("Portal tit") && detalleMatriculacion.getTitular().getDireccion().getPuerta() != null) {
			tcamp = detalleMatriculacion.getTitular().getDireccion().getPuerta();
		}

		// Escalera
		if (campo.equalsIgnoreCase("Escalera tit") && detalleMatriculacion.getTitular().getDireccion().getEscalera() != null) {
			tcamp = detalleMatriculacion.getTitular().getDireccion().getEscalera();
		}

		// Planta
		if (campo.equalsIgnoreCase("Planta tit") && detalleMatriculacion.getTitular().getDireccion().getPlanta() != null) {
			tcamp = detalleMatriculacion.getTitular().getDireccion().getPlanta();
		}

		// Puerta
		if (campo.equalsIgnoreCase("Puerta tit") && detalleMatriculacion.getTitular().getDireccion().getLetra() != null) {
			tcamp = detalleMatriculacion.getTitular().getDireccion().getLetra();
		}

		// Provicia Titular
		if (campo.equalsIgnoreCase("Provincia tit") && detalleMatriculacion.getTitular().getDireccion().getIdProvincia() != null) {
			tcamp = conversiones.getSiglasFromIdProvincia(detalleMatriculacion.getTitular().getDireccion().getIdProvincia());
		}

		// Municipio Titular
		if (campo.equalsIgnoreCase("Municipio tit") && detalleMatriculacion.getTitular().getDireccion().getIdProvincia() != null) {
			tcamp = detalleMatriculacion.getTitular().getDireccion().getIdProvincia() + detalleMatriculacion.getTitular().getDireccion().getIdMunicipio();
		}

		// Pueblo titular
		if (campo.equalsIgnoreCase("Pueblo tit") && detalleMatriculacion.getTitular().getDireccion().getPuebloCorreos() != null) {
			tcamp = detalleMatriculacion.getTitular().getDireccion().getPuebloCorreos();
		}

		// Codigo Postal
		if (campo.equalsIgnoreCase("Cpostal tit") && detalleMatriculacion.getTitular().getDireccion().getCodPostalCorreos() != null) {
			tcamp = detalleMatriculacion.getTitular().getDireccion().getCodPostalCorreos();
		}

		// Pais del titular
		if (campo.equalsIgnoreCase("Pais tit")) {
			tcamp = VALOR_PAIS;
		}

		// **************************** DATOS DEL DOMICILIO DEL VEHICULO ****************************************************
		// Tipo via del Vehiculo
		if (campo.equalsIgnoreCase("Tipo via veh")) {
			if (detalleMatriculacion.getVehiculoDto().getDireccion() != null && detalleMatriculacion.getVehiculoDto().getDireccion().getIdTipoVia() != null) {
				tcamp = detalleMatriculacion.getVehiculoDto().getDireccion().getIdTipoVia();
			}
		}

		// Nombre via Vehiculo
		if (campo.equalsIgnoreCase("Nombre via veh")) {
			if (detalleMatriculacion.getVehiculoDto().getDireccion() != null && detalleMatriculacion.getVehiculoDto().getDireccion().getNombreVia() != null) {
				tcamp = conversiones.ajustarCamposIne(detalleMatriculacion.getVehiculoDto().getDireccion().getNombreVia());
			}
		}

		// Numero del Vehiculo
		if (campo.equalsIgnoreCase("Numero via veh")) {
			if (detalleMatriculacion.getVehiculoDto().getDireccion() != null && detalleMatriculacion.getVehiculoDto().getDireccion().getNumero() != null) {
				tcamp = detalleMatriculacion.getVehiculoDto().getDireccion().getNumero();
			}
		}

		// km del Vehiculo
		if (campo.equalsIgnoreCase("Kilometro veh")) {
			if (detalleMatriculacion.getVehiculoDto().getDireccion() != null && detalleMatriculacion.getVehiculoDto().getDireccion().getKm() != null) {
				tcamp = detalleMatriculacion.getVehiculoDto().getDireccion().getKm().toString();

			}
		}

		// Hm del Vehiculo
		if (campo.equalsIgnoreCase("Hectometro veh")) {
			if (detalleMatriculacion.getVehiculoDto().getDireccion() != null && detalleMatriculacion.getVehiculoDto().getDireccion().getHm() != null) {
				tcamp = detalleMatriculacion.getVehiculoDto().getDireccion().getHm().toString();

			}
		}

		// bloque Vehiculo
		if (campo.equalsIgnoreCase("Bloque veh")) {
			if (detalleMatriculacion.getVehiculoDto().getDireccion() != null && detalleMatriculacion.getVehiculoDto().getDireccion().getBloque() != null) {
				tcamp = detalleMatriculacion.getVehiculoDto().getDireccion().getBloque();
			}
		}

		// Portal
		if (campo.equalsIgnoreCase("Portal veh")) {
			if (detalleMatriculacion.getVehiculoDto().getDireccion() != null && detalleMatriculacion.getVehiculoDto().getDireccion().getPuerta() != null) {
				tcamp = detalleMatriculacion.getVehiculoDto().getDireccion().getPuerta();
			}
		}

		// Escalera
		if (campo.equalsIgnoreCase("Escalera veh")) {
			if (detalleMatriculacion.getVehiculoDto().getDireccion() != null && detalleMatriculacion.getVehiculoDto().getDireccion().getEscalera() != null) {
				tcamp = detalleMatriculacion.getVehiculoDto().getDireccion().getEscalera();
			}
		}

		// Planta
		if (campo.equalsIgnoreCase("Planta veh")) {
			if (detalleMatriculacion.getVehiculoDto().getDireccion() != null && detalleMatriculacion.getVehiculoDto().getDireccion().getPlanta() != null) {
				tcamp = detalleMatriculacion.getVehiculoDto().getDireccion().getPlanta();
			}
		}

		// Puerta
		if (campo.equalsIgnoreCase("Puerta veh")) {
			if (detalleMatriculacion.getVehiculoDto().getDireccion() != null && detalleMatriculacion.getVehiculoDto().getDireccion().getLetra() != null) {
				tcamp = detalleMatriculacion.getVehiculoDto().getDireccion().getLetra();
			}
		}

		// Provicia Vehiculo
		if (campo.equalsIgnoreCase("Provincia veh")) {
			if (detalleMatriculacion.getVehiculoDto().getDireccion() != null && detalleMatriculacion.getVehiculoDto().getDireccion().getIdProvincia() != null) {
				tcamp = conversiones.getSiglasFromIdProvincia(detalleMatriculacion.getVehiculoDto().getDireccion().getIdProvincia());
			}
		}

		// Municipio Vehiculo
		if (campo.equalsIgnoreCase("Municipio veh")) {
			if (detalleMatriculacion.getVehiculoDto().getDireccion() != null && detalleMatriculacion.getVehiculoDto().getDireccion().getIdProvincia() != null) {
				tcamp = detalleMatriculacion.getVehiculoDto().getDireccion().getIdProvincia() + detalleMatriculacion.getVehiculoDto().getDireccion().getIdMunicipio();
			}
		}

		// Pueblo Vehiculo
		if (campo.equalsIgnoreCase("Pueblo veh")) {
			if (detalleMatriculacion.getVehiculoDto().getDireccion() != null && detalleMatriculacion.getVehiculoDto().getDireccion().getPuebloCorreos() != null) {
				tcamp = detalleMatriculacion.getVehiculoDto().getDireccion().getPuebloCorreos();
			}
		}

		// Codigo Postal
		if (campo.equalsIgnoreCase("Cpostal veh")) {
			if (detalleMatriculacion.getVehiculoDto().getDireccion() != null && detalleMatriculacion.getVehiculoDto().getDireccion().getCodPostalCorreos() != null) {
				tcamp = detalleMatriculacion.getVehiculoDto().getDireccion().getCodPostalCorreos();
			}
		}

		// Pais del Vehiculo
		if (campo.equalsIgnoreCase("Pais veh")) {
			tcamp = VALOR_PAIS;
		}

		// **************************** DATOS TECNICOS ****************************************************
		/*
		 * {"Num homologacion", "50"},{"Codigo ITV","9"},{"Tipo veh industria", "4"},{"Tipo veh DGT","2"},{"Procedencia", "3"},{"Marca", "23"}, {"Fabricante","70"},{"Modelo", "22"},{"Color","2"},{"Tipo tarjeta itv", "25"},{"Variante", "25"},{"Version","35"}, {"Potencia fiscal",
		 * "13"},{"Cilindrada","8"},{"Tara", "7"},{"MMA", "6"},{"Plazas","3"},{"Relacion potpeso", "7"}, {"Plazas pie","3"},{"C02", "7"},{"Mom", "7"},{"Potencia neta","13"},{"Carroceria", "5"},{"Categoria EU","5"}, {"Distancia Ejes", "4"},{"Masa Tecnica", "6"},{"Via anterior","4"},{"Via posterior",
		 * "4"},{"Codigo ECO","10"},{"Consumo", "4"}, {"Eco innova", "1"},{"Nivel emisiones","15"},{"Reduccion ECO", "4"},{"Tipo Alimentacion","1"},{"Pais fabricacion", "1"},
		 */

		// Numero de homologacion
		if (campo.equalsIgnoreCase("Num homologacion") && detalleMatriculacion.getVehiculoDto().getNumHomologacion() != null) {
			tcamp = detalleMatriculacion.getVehiculoDto().getNumHomologacion();
		}

		// Codigo ITV - Si tiene el valor SINCODIG se dejará en la nube el espacio en blanco de los 9 caracteres.
		if (campo.equalsIgnoreCase("Codigo ITV") && (detalleMatriculacion.getVehiculoDto().getCodItv() != null && !detalleMatriculacion.getVehiculoDto().getCodItv().isEmpty() && !detalleMatriculacion
				.getVehiculoDto().getCodItv().equals(ConstantesTrafico.SIN_CODIG) && !detalleMatriculacion.getVehiculoDto().getCodItv().equals(ConstantesTrafico.SIN_CODIGO))) {
			tcamp = detalleMatriculacion.getVehiculoDto().getCodItv();
		}

		// TIPO VEHICULO INDUSTRIA
		// Este campo en realidad es la clasificación. Y para rellenar lo que en el diccionario de datos llaman "Tipo vehiculo Industria" habrá que coger
		// el valor contenido en la columna ClasificacionITV
		if (campo.equalsIgnoreCase("Tipo veh industria") && detalleMatriculacion.getVehiculoDto().getClasificacionItv() != null) {
			tcamp = detalleMatriculacion.getVehiculoDto().getClasificacionItv();
		}

		// Tipo de vehiculo DGT
		/*
		 * Mantis - 0018598 - Alberto Barrero - MATW - Revision tipo Dgt XML y Pdf Únicamente se debe informar el campo tipo DGT en las solicitudes de matriculación de vehículos cuya clasificación de industria puede tener varios tipos DGT que son: MOTOCICLETAS, CICLOMOTORES, REMOLQUES,
		 * SEMIRREMOLQUES
		 */
		if (campo.equalsIgnoreCase("Tipo veh DGT") && detalleMatriculacion.getVehiculoDto().getTipoVehiculo() != null) {
			if (TipoVehiculoDGT.contains(detalleMatriculacion.getVehiculoDto().getTipoVehiculo())) {
				tcamp = detalleMatriculacion.getVehiculoDto().getTipoVehiculo();
			} else {
				tcamp = "  ";
			}
		}

		// Procedencia

		if (campo.equalsIgnoreCase("Procedencia") && detalleMatriculacion.getVehiculoDto().getProcedencia() != null && detalleMatriculacion.getVehiculoDto().getImportado() != null
				&& detalleMatriculacion.getVehiculoDto().getImportado() != false) {
			tcamp = detalleMatriculacion.getVehiculoDto().getProcedencia();
		}

		// MARCA

		if (campo.equalsIgnoreCase("Marca") && detalleMatriculacion.getVehiculoDto().getCodigoMarca() != null) {
			String nombreMarca = servicioVehiculo.obtenerNombreMarca(detalleMatriculacion.getVehiculoDto().getCodigoMarca(), true);
			tcamp = nombreMarca;
		}

		// Fabricante
		if (campo.equalsIgnoreCase("Fabricante") && detalleMatriculacion.getVehiculoDto().getFabricante() != null) {
			tcamp = detalleMatriculacion.getVehiculoDto().getFabricante();
		}

		// Modelo
		if (campo.equalsIgnoreCase("Modelo") && detalleMatriculacion.getVehiculoDto().getModelo() != null) {
			tcamp = detalleMatriculacion.getVehiculoDto().getModelo();
		}

		// Color
		if (campo.equalsIgnoreCase("Color") && detalleMatriculacion.getVehiculoDto().getColor() != null && !detalleMatriculacion.getVehiculoDto().getColor().equals("-")) {
			tcamp = detalleMatriculacion.getVehiculoDto().getColor();
		}

		// TIPO DE LA TARJETA ITV
		if (campo.equalsIgnoreCase("Tipo tarjeta itv") && detalleMatriculacion.getVehiculoDto().getTipoTarjetaITV() != null) {
			tcamp = detalleMatriculacion.getVehiculoDto().getTipoTarjetaITV();
		}

		// Variante
		if (campo.equalsIgnoreCase("Variante") && detalleMatriculacion.getVehiculoDto().getVariante() != null) {
			tcamp = detalleMatriculacion.getVehiculoDto().getVariante();
		}

		// Version
		if (campo.equalsIgnoreCase("Version") && detalleMatriculacion.getVehiculoDto().getVersion() != null) {
			tcamp = detalleMatriculacion.getVehiculoDto().getVersion();
		}

		// Potencia fiscal
		if (campo.equalsIgnoreCase("Potencia fiscal") && detalleMatriculacion.getVehiculoDto().getPotenciaFiscal() != null) {
			// cambio del formato para la version 2 de MATW Ejemplo:9999999999.99
			BigDecimal pf = utiles.stringToBigDecimalDosDecimales(utiles.BigDecimalToStringDosDecimales(detalleMatriculacion.getVehiculoDto().getPotenciaFiscal(), ConstantesPDF._13));
			tcamp = null != pf ? pf.toString() : detalleMatriculacion.getVehiculoDto().getPotenciaFiscal().toString();
		}

		// Cilindrada
		// Contemplar los decimales, las 5 primeras posiciones enteros, dos últimas decimales
		// NUEVOS FORMATO DE MATW Version 2 ejemplo: 99999.99
		if (campo.equalsIgnoreCase("Cilindrada") && detalleMatriculacion.getVehiculoDto().getCilindrada() != null) {
			tcamp = detalleMatriculacion.getVehiculoDto().getCilindrada();
		}

		// Tara

		if (campo.equalsIgnoreCase("Tara") && detalleMatriculacion.getVehiculoDto().getTara() != null) {
			tcamp = detalleMatriculacion.getVehiculoDto().getTara();
		}

		// MMA
		if (campo.equalsIgnoreCase("MMA") && detalleMatriculacion.getVehiculoDto().getPesoMma() != null) {
			tcamp = detalleMatriculacion.getVehiculoDto().getPesoMma();
		}

		// Plazas
		if (campo.equalsIgnoreCase("Plazas") && detalleMatriculacion.getVehiculoDto().getPlazas() != null) {
			tcamp = detalleMatriculacion.getVehiculoDto().getPlazas().toString();
		}

		// Carburante
		if (campo.equalsIgnoreCase("Tipo Carburante") && detalleMatriculacion.getVehiculoDto().getCarburante() != null && !"-1".equals(detalleMatriculacion.getVehiculoDto().getCarburante())) {
			tcamp = detalleMatriculacion.getVehiculoDto().getCarburante();
		}

		// Relacion potpeso
		if (campo.equalsIgnoreCase("Relacion potpeso") && detalleMatriculacion.getVehiculoDto().getPotenciaPeso() != null && !"REMO".equals(detalleMatriculacion.getVehiculoDto().getTipoVehiculo())) {
			String parteEntera = "";
			String parteDecimal = "";
			BigDecimal RelPotp = detalleMatriculacion.getVehiculoDto().getPotenciaPeso();

			// Rellena con 0 la parte decimal hasta un total de 4 digitos
			if (RelPotp != null) {
				RelPotp = RelPotp.setScale(4, BigDecimal.ROUND_DOWN);
			} else {
				detalleMatriculacion.getVehiculoDto().getPotenciaPeso().setScale(4, BigDecimal.ROUND_DOWN);
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
		// SEGUN LAS VALIDACIONES PASADAS EN ARCHIVO EXCEL "VALIDACIONES GESTORES" FECHA :15/10/2013 si las plazas vienen con valor nulo en la nube se informa como 0.
		// PLAZAS PIE "ClasificacionNoAutobus=0"
		if (campo.equalsIgnoreCase("Plazas pie")) {
			tcamp = detalleMatriculacion.getVehiculoDto().getNumPlazasPie() != null ? detalleMatriculacion.getVehiculoDto().getNumPlazasPie().toString() : "0";
		}

		// CO2
		if (campo.equalsIgnoreCase("C02") && (detalleMatriculacion.getVehiculoDto().getCo2() != null)) {
			tcamp = detalleMatriculacion.getVehiculoDto().getCo2();
		}

		// Mom Masa en orden de marcha
		if (campo.equalsIgnoreCase("Mom") && (detalleMatriculacion.getVehiculoDto().getMom() != null)) {
			tcamp = detalleMatriculacion.getVehiculoDto().getMom().toString();
		}

		// Potencia neta maxima
		if (campo.equalsIgnoreCase("Potencia neta") && (detalleMatriculacion.getVehiculoDto().getPotenciaNeta() != null)) {
			BigDecimal Potn = utiles.stringToBigDecimalTresDecimales(utiles.BigDecimalToStringTresDecimales(detalleMatriculacion.getVehiculoDto().getPotenciaNeta(), ConstantesPDF._13));
			tcamp = null != Potn ? Potn.toString() : detalleMatriculacion.getVehiculoDto().getPotenciaNeta().toString();
		}

		// Carroceria
		if (campo.equalsIgnoreCase("Carroceria") && (detalleMatriculacion.getVehiculoDto().getCarroceria() != null)) {
			tcamp = detalleMatriculacion.getVehiculoDto().getCarroceria();
		}

		// Categoria EU
		if (campo.equalsIgnoreCase("Categoria EU") && (detalleMatriculacion.getVehiculoDto().getIdDirectivaCee() != null && detalleMatriculacion.getVehiculoDto().getIdDirectivaCee() != null)) {
			tcamp = detalleMatriculacion.getVehiculoDto().getIdDirectivaCee();
		}

		// Distancia Ejes
		// SEGUN LAS VALIDACIONES PASADAS EN ARCHIVO EXCEL "VALIDACIONES GESTORES" FECHA :15/10/2013 si el campo Distancia Ejes viene con valor nulo en la nube se informa como 0.
		// DISTANCIA EJES VALOR (0..999999)"
		if (campo.equalsIgnoreCase("Distancia Ejes")) {
			tcamp = null != detalleMatriculacion.getVehiculoDto().getDistanciaEjes() ? detalleMatriculacion.getVehiculoDto().getDistanciaEjes().toString() : "0";
		}

		// Masa Tecnica
		// SEGUN LAS VALIDACIONES PASADAS EN ARCHIVO EXCEL "VALIDACIONES GESTORES" FECHA :15/10/2013 si el campo Masa Tecnica viene con valor nulo en la nube se informa como 0.
		// MTMA VALOR 0..999999)"
		if (campo.equalsIgnoreCase("Masa Tecnica")) {
			tcamp = null != detalleMatriculacion.getVehiculoDto().getMtmaItv() ? detalleMatriculacion.getVehiculoDto().getMtmaItv() : "0";
		}

		// Via anterior
		// SEGUN LAS VALIDACIONES PASADAS EN ARCHIVO EXCEL "VALIDACIONES GESTORES" FECHA :15/10/2013 si el campo VÍA ANTERIOR viene con valor nulo en la nube se informa como 0.
		// VÍA ANTERIOR VALOR 0..999999)"
		if (campo.equalsIgnoreCase("Via anterior") && (detalleMatriculacion.getVehiculoDto().getViaAnterior() != null)) {
			tcamp = null != detalleMatriculacion.getVehiculoDto().getViaAnterior() ? detalleMatriculacion.getVehiculoDto().getViaAnterior().toString() : "0";
		}

		// Via posterior
		if (campo.equalsIgnoreCase("Via posterior") && (detalleMatriculacion.getVehiculoDto().getViaPosterior() != null)) {
			tcamp = detalleMatriculacion.getVehiculoDto().getViaPosterior().toString();
		}

		// Codigo ECO
		if (campo.equalsIgnoreCase("Codigo ECO") && (detalleMatriculacion.getVehiculoDto().getCodigoEco() != null)) {
			tcamp = detalleMatriculacion.getVehiculoDto().getCodigoEco();
		}

		// Consumo
		if (campo.equalsIgnoreCase("Consumo")) {
			if (detalleMatriculacion != null && detalleMatriculacion.getVehiculoDto() != null && detalleMatriculacion.getVehiculoDto().getConsumo() != null && detalleMatriculacion.getVehiculoDto()
					.getCategoriaElectrica() != null) {
				String property = gestorPropiedades.valorPropertie(ConstantesPDF.PROPERTY_COMPLETA_CEROS_ELECTRICOS);
				if (property != null && property.equals(ConstantesPDF.VALOR_CIERTO_PROPERTY_COMPLETA_CEROS_ELECTRICOS)) {
					DecimalFormat formato4 = new DecimalFormat("#0000");
					if (detalleMatriculacion.getVehiculoDto().getConsumo() != null) {
						tcamp = formato4.format(detalleMatriculacion.getVehiculoDto().getConsumo());
					} else {
						tcamp = "";
					}
				} else {
					tcamp = detalleMatriculacion.getVehiculoDto().getConsumo().toString();
				}
			} else {
				tcamp = "";
			}
		}

		// Eco innova
		if (campo.equalsIgnoreCase("Eco innova")) {
			if (null != detalleMatriculacion.getVehiculoDto().getEcoInnovacion() && detalleMatriculacion.getVehiculoDto().getEcoInnovacion().equalsIgnoreCase("Si")) {
				tcamp = VALOR_S;
			} else {
				tcamp = VALOR_N;
			}
		}

		// Nivel emisiones
		if (campo.equalsIgnoreCase("Nivel emisiones") && detalleMatriculacion.getVehiculoDto().getNivelEmisiones() != null) {
			tcamp = detalleMatriculacion.getVehiculoDto().getNivelEmisiones();
		}

		// Reduccion ECO
		if (campo.equalsIgnoreCase("Reduccion ECO") && detalleMatriculacion.getVehiculoDto().getReduccionEco() != null) {
			tcamp = detalleMatriculacion.getVehiculoDto().getReduccionEco().toString();
		}

		// Tipo Alimentacion
		if (campo.equalsIgnoreCase("Tipo Alimentacion") && detalleMatriculacion.getVehiculoDto().getTipoAlimentacion() != null) {
			tcamp = detalleMatriculacion.getVehiculoDto().getTipoAlimentacion();
		}

		// Pais fabricacion
		if (campo.equalsIgnoreCase("Pais fabricacion") && detalleMatriculacion.getVehiculoDto().getPaisFabricacion() != null) {
			tcamp = detalleMatriculacion.getVehiculoDto().getPaisFabricacion();
		}

		/*
		 * NUEVOS CAMPOS datos tecnicos MANCHA PDF1 "Tipo Tarjeta ITV" "Marca Base" "Fabricante base" "Tipo base" "Variante base" "Mom base" "Categoria electrica" "Autonomia electrica" "Num homologacion base" "Version base"
		 */
		// TIPO_TARJETA_ITV
		if (campo.equalsIgnoreCase("Tipo") && detalleMatriculacion.getVehiculoDto().getTipoItv() != null) {
			tcamp = detalleMatriculacion.getVehiculoDto().getTipoItv();
		}

		// Estos campos sólo debe informados en caso de que el vehiculo tenga un tipo de tarjeta itv A o D.
		if (detalleMatriculacion.getVehiculoDto().getTipoTarjetaITV() != null && ("A".equals(detalleMatriculacion.getVehiculoDto().getTipoTarjetaITV().substring(0, 1)) || "D".equals(
				detalleMatriculacion.getVehiculoDto().getTipoTarjetaITV().substring(0, 1)))) {
			// MARCA_BASE
			if (campo.equalsIgnoreCase("Marca Base") && detalleMatriculacion.getVehiculoDto().getCodigoMarcaBase() != null) {
				tcamp = servicioVehiculo.obtenerNombreMarca(detalleMatriculacion.getVehiculoDto().getCodigoMarcaBase(), true);
			}

			// FABRICANTE_BASE
			if (campo.equalsIgnoreCase("Fabricante base") && detalleMatriculacion.getVehiculoDto().getFabricanteBase() != null) {
				tcamp = detalleMatriculacion.getVehiculoDto().getFabricanteBase();
			}

			// TIPO_BASE
			if (campo.equalsIgnoreCase("Tipo base") && detalleMatriculacion.getVehiculoDto().getTipoBase() != null) {
				tcamp = detalleMatriculacion.getVehiculoDto().getTipoBase();
			}

			// VARIANTE_BASE
			if (campo.equalsIgnoreCase("Variante base") && detalleMatriculacion.getVehiculoDto().getVarianteBase() != null) {
				tcamp = detalleMatriculacion.getVehiculoDto().getVarianteBase();

			}

			// VERSION_BASE
			if (campo.equalsIgnoreCase("Version base") && detalleMatriculacion.getVehiculoDto().getVersionBase() != null) {
				tcamp = detalleMatriculacion.getVehiculoDto().getVersionBase();
			}

			// NUMERO_HOMOLOGACION_BASE
			if (campo.equalsIgnoreCase("Num homologacion base") && detalleMatriculacion.getVehiculoDto().getNumHomologacionBase() != null) {
				tcamp = detalleMatriculacion.getVehiculoDto().getNumHomologacionBase();
			}

			// MOM_BASE
			if (campo.equalsIgnoreCase("Mom base") && detalleMatriculacion.getVehiculoDto().getMomBase() != null) {
				tcamp = detalleMatriculacion.getVehiculoDto().getMomBase().toString();
			}
		}

		// CATEGORIA_ELECTRICA
		if (campo.equalsIgnoreCase("Categoria electrica") && detalleMatriculacion.getVehiculoDto().getCategoriaElectrica() != null) {
			tcamp = detalleMatriculacion.getVehiculoDto().getCategoriaElectrica();
		}

		// AUTONOMIA_ELECTRICA
		if (campo.equalsIgnoreCase("Autonomia electrica") && detalleMatriculacion.getVehiculoDto().getAutonomiaElectrica() != null) {
			String autonomiaElectrica = detalleMatriculacion.getVehiculoDto().getAutonomiaElectrica() != null ? detalleMatriculacion.getVehiculoDto().getAutonomiaElectrica().toString() : "";
			String property = gestorPropiedades.valorPropertie(ConstantesPDF.PROPERTY_COMPLETA_CEROS_ELECTRICOS);
			if (property != null && property.equals(ConstantesPDF.VALOR_CIERTO_PROPERTY_COMPLETA_CEROS_ELECTRICOS)) {
				DecimalFormat formato4 = new DecimalFormat("#0000");
				if (autonomiaElectrica != null && !autonomiaElectrica.isEmpty()) {
					autonomiaElectrica = formato4.format(new BigDecimal(autonomiaElectrica));
				}
			}
			tcamp = autonomiaElectrica;
		}

		// **************************** DATOS DEL ARRENDATARIO ****************************************************

		if (detalleMatriculacion.getArrendatario() != null) {
			// DOI del Arrendatario
			if (campo.equalsIgnoreCase("DOI arr") && detalleMatriculacion.getArrendatario().getPersona().getNif() != null) {
				tcamp = detalleMatriculacion.getArrendatario().getPersona().getNif();
			}

			// Razon social arrendatario
			if (campo.equalsIgnoreCase("Razon social arr")) {
				if (detalleMatriculacion.getArrendatario().getPersona().getTipoPersona() != null && detalleMatriculacion.getArrendatario().getPersona().getTipoPersona().equals(TipoPersona.Juridica
						.getValorEnum())) {
					if (null != detalleMatriculacion.getArrendatario().getPersona().getApellido1RazonSocial()) {
						tcamp = detalleMatriculacion.getArrendatario().getPersona().getApellido1RazonSocial();
					}
				}
			}

			// Nombre del Arrendatario (Persona fisica)
			if (campo.equalsIgnoreCase("Nombre arr")) {
				if (detalleMatriculacion.getArrendatario().getPersona().getTipoPersona() != null && detalleMatriculacion.getArrendatario().getPersona().getTipoPersona().equals(TipoPersona.Fisica
						.getValorEnum())) {
					if (null != detalleMatriculacion.getArrendatario().getPersona().getNombre()) {
						tcamp = detalleMatriculacion.getArrendatario().getPersona().getNombre();
					}
				}
			}

			// Apellido 1 Arrendatario (Persona fisica)
			if (campo.equalsIgnoreCase("Apellido1 arr")) {
				if (detalleMatriculacion.getArrendatario().getPersona().getTipoPersona() != null && detalleMatriculacion.getArrendatario().getPersona().getTipoPersona().equals(TipoPersona.Fisica
						.getValorEnum())) {
					if (null != detalleMatriculacion.getArrendatario().getPersona().getApellido1RazonSocial()) {
						tcamp = detalleMatriculacion.getArrendatario().getPersona().getApellido1RazonSocial();
					}
				}
			}

			// Apellido 2 Arrendatario (Persona fisica)
			if (campo.equalsIgnoreCase("Apellido2 arr")) {
				if (detalleMatriculacion.getArrendatario().getPersona().getTipoPersona() != null && detalleMatriculacion.getArrendatario().getPersona().getTipoPersona().equals(TipoPersona.Fisica
						.getValorEnum())) {
					if (null != detalleMatriculacion.getArrendatario().getPersona().getApellido2()) {
						tcamp = detalleMatriculacion.getArrendatario().getPersona().getApellido2();
					}
				}
			}

			// Fecha de nacimiento del Arrendatario
			try {
				if (campo.equalsIgnoreCase("Fecha nacimiento arr")) {
					if (detalleMatriculacion.getArrendatario().getPersona().getFechaNacimiento() != null && detalleMatriculacion.getArrendatario().getPersona().getTipoPersona() != null
							&& detalleMatriculacion.getArrendatario().getPersona().getTipoPersona().equals(TipoPersona.Fisica.getValorEnum())) {
						if (null != formato.format(detalleMatriculacion.getArrendatario().getPersona().getFechaNacimiento().getFecha()).toString()) {
							tcamp = formato.format(detalleMatriculacion.getArrendatario().getPersona().getFechaNacimiento().getFecha()).toString();
						}
					}
				}
			} catch (ParseException e) {
				tcamp = "";
			}

			// Sexo del Arrendatario
			if (campo.equalsIgnoreCase("Sexo arr") && detalleMatriculacion.getArrendatario().getPersona().getSexo() != null) {
				tcamp = detalleMatriculacion.getArrendatario().getPersona().getSexo();
			}

			// NIF del representante del Arrendatario
			if (campo.equalsIgnoreCase("DOI Reprent arr") && detalleMatriculacion.getRepresentanteArrendatario() != null && detalleMatriculacion.getRepresentanteArrendatario().getPersona()
					.getNif() != null) {
				tcamp = detalleMatriculacion.getRepresentanteArrendatario().getPersona().getNif();
			}

			// Fecha inicial Renting
			try {
				if (campo.equalsIgnoreCase("Fech ini Renting") && detalleMatriculacion.getArrendatario().getFechaInicio() != null && !detalleMatriculacion.getArrendatario().getFechaInicio()
						.isfechaNula()) {
					tcamp = formato.format(detalleMatriculacion.getArrendatario().getFechaInicio().getFecha());
				}
			} catch (ParseException e) {
				tcamp = "";
			}

			// Fecha fin Renting
			try {
				if (campo.equalsIgnoreCase("Fech fin Renting") && detalleMatriculacion.getArrendatario().getFechaFin() != null && !detalleMatriculacion.getArrendatario().getFechaInicio()
						.isfechaNula()) {
					tcamp = formato.format(detalleMatriculacion.getArrendatario().getFechaFin().getFecha());
				}
			} catch (ParseException e) {
				tcamp = "";
			}

			// Hora inicio Renting
			if (campo.equalsIgnoreCase("Hora ini Renting") && detalleMatriculacion.getArrendatario().getHoraInicio() != null) {
				tcamp = detalleMatriculacion.getArrendatario().getHoraInicio().replaceAll(":", "");
			}

			// Hora fin Renting
			if (campo.equalsIgnoreCase("Hora fin Renting") && detalleMatriculacion.getArrendatario().getHoraFin() != null) {
				tcamp = detalleMatriculacion.getArrendatario().getHoraFin().replaceAll(":", "");
			}

			// CAMBIO DE DOMICILIO DEL Arrendatario
			if (campo.equalsIgnoreCase("Cambio domic arr")) {
				tcamp = detalleMatriculacion.getArrendatario().getCambioDomicilio() != null && detalleMatriculacion.getArrendatario().getCambioDomicilio() ? VALOR_S : VALOR_N;
				// Si no tiene relleno el Arrendatario deja el campo cambio de domicilio vacio
				if (detalleMatriculacion.getArrendatario().getPersona().getNif() == null) {
					tcamp = "";
				}
			}

			if (detalleMatriculacion.getArrendatario().getDireccion() != null) {
				if (campo.equalsIgnoreCase("Tipo via arr") && detalleMatriculacion.getArrendatario().getDireccion().getIdTipoVia() != null) {
					tcamp = detalleMatriculacion.getArrendatario().getDireccion().getIdTipoVia();
				}

				// Nombre via Arrendatario
				if (campo.equalsIgnoreCase("Nombre via arr") && detalleMatriculacion.getArrendatario().getDireccion().getNombreVia() != null) {
					tcamp = conversiones.ajustarCamposIne(detalleMatriculacion.getArrendatario().getDireccion().getNombreVia());
				}

				// Numero del Arrendatario
				if (campo.equalsIgnoreCase("Numero via arr") && detalleMatriculacion.getArrendatario().getDireccion().getNumero() != null) {
					tcamp = detalleMatriculacion.getArrendatario().getDireccion().getNumero();
				}

				// km del Arrendatario
				if (campo.equalsIgnoreCase("Kilometro arr") && detalleMatriculacion.getArrendatario().getDireccion().getKm() != null) {
					tcamp = detalleMatriculacion.getArrendatario().getDireccion().getKm().toString();
				}

				// Hm del Arrendatario
				if (campo.equalsIgnoreCase("Hectometro arr") && detalleMatriculacion.getArrendatario().getDireccion().getHm() != null) {
					tcamp = detalleMatriculacion.getArrendatario().getDireccion().getHm().toString();
				}

				// bloque Arrendatario
				if (campo.equalsIgnoreCase("Bloque arr") && detalleMatriculacion.getArrendatario().getDireccion().getBloque() != null) {
					tcamp = detalleMatriculacion.getArrendatario().getDireccion().getBloque();
				}

				// Portal
				if (campo.equalsIgnoreCase("Portal arr") && detalleMatriculacion.getArrendatario().getDireccion().getPuerta() != null) {
					tcamp = detalleMatriculacion.getArrendatario().getDireccion().getPuerta();
				}

				// Escalera
				if (campo.equalsIgnoreCase("Escalera arr") && detalleMatriculacion.getArrendatario().getDireccion().getEscalera() != null) {
					tcamp = detalleMatriculacion.getArrendatario().getDireccion().getEscalera();
				}

				// Planta
				if (campo.equalsIgnoreCase("Planta arr") && detalleMatriculacion.getArrendatario().getDireccion().getPlanta() != null) {
					tcamp = detalleMatriculacion.getArrendatario().getDireccion().getPlanta();
				}

				// Puerta
				if (campo.equalsIgnoreCase("Puerta arr") && detalleMatriculacion.getArrendatario().getDireccion().getLetra() != null) {
					tcamp = detalleMatriculacion.getArrendatario().getDireccion().getLetra();
				}

				// Provicia Arrendatario
				if (campo.equalsIgnoreCase("Provincia arr") && detalleMatriculacion.getArrendatario().getDireccion().getIdProvincia() != null) {
					tcamp = conversiones.getSiglasFromIdProvincia(detalleMatriculacion.getArrendatario().getDireccion().getIdProvincia()).toString();
				}

				// Municipio Arrendatario
				if (campo.equalsIgnoreCase("Municipio arr") && detalleMatriculacion.getArrendatario().getDireccion().getIdProvincia() != null) {
					tcamp = detalleMatriculacion.getArrendatario().getDireccion().getIdProvincia() + detalleMatriculacion.getArrendatario().getDireccion().getIdMunicipio();
				}

				// Pueblo Arrendatario
				if (campo.equalsIgnoreCase("Pueblo arr")) {
					tcamp = null != detalleMatriculacion.getArrendatario().getPersona().getNif() && detalleMatriculacion.getArrendatario().getDireccion().getPuebloCorreos() != null
							? detalleMatriculacion.getArrendatario().getDireccion().getPuebloCorreos() : "";
				}

				// Codigo Postal
				if (campo.equalsIgnoreCase("Cpostal arr") && detalleMatriculacion.getArrendatario().getDireccion().getCodPostalCorreos() != null) {
					tcamp = detalleMatriculacion.getArrendatario().getDireccion().getCodPostalCorreos();
				}
			}

			// Pais del Arrendatario
			if (campo.equalsIgnoreCase("Pais arr") && detalleMatriculacion.getArrendatario().getPersona().getNif() != null) {
				tcamp = VALOR_PAIS;
			}
		}

		// ********************************** DATOS DEL CONDUCTOR HABITUAL **************************************
		// DOI DEL CONDUCTOR HABITUAL

		if (detalleMatriculacion.getConductorHabitual() != null) {
			// {"DOI ch","9"},{"Fecha fin ch", "8"}, {"Hora fin ch", "8"},{"Cambio dom ch","1"},
			if (campo.equalsIgnoreCase("DOI ch")) {
				if (detalleMatriculacion.getConductorHabitual().getPersona().getNif() != null) {
					tcamp = detalleMatriculacion.getConductorHabitual().getPersona().getNif();
				}
			}

			// Fecha fin ch
			try {

				if (campo.equalsIgnoreCase("Fecha fin ch") && detalleMatriculacion.getConductorHabitual().getFechaFin() != null && !detalleMatriculacion.getConductorHabitual().getFechaFin()
						.isfechaNula()) {
					if (formato.format(detalleMatriculacion.getConductorHabitual().getFechaFin().getFecha()) != null && detalleMatriculacion.getConductorHabitual().getPersona().getNif() != null) {
						tcamp = formato.format(detalleMatriculacion.getConductorHabitual().getFechaFin().getFecha());
					}
				}
			} catch (ParseException e) {
				tcamp = "";
			}

			// {"Hora fin ch", "8"}
			if (campo.equalsIgnoreCase("Hora fin ch") && null != detalleMatriculacion.getConductorHabitual().getHoraFin()) {
				tcamp = detalleMatriculacion.getConductorHabitual().getHoraFin().replaceAll(":", "");
			}

			// {"Cambio dom ch","1"},
			if (campo.equalsIgnoreCase("Cambio dom ch")) {
				tcamp = detalleMatriculacion.getConductorHabitual().getCambioDomicilio() != null && detalleMatriculacion.getConductorHabitual().getCambioDomicilio() && detalleMatriculacion
						.getConductorHabitual().getPersona().getNif() != null ? VALOR_S : VALOR_N;
				// Si no tiene relleno el CH deja el campo cambio de domicilio vacio
				if (detalleMatriculacion.getConductorHabitual().getPersona().getNif() == null) {
					tcamp = "";
				}
			}

			// {"Tipo via ch", "5"},
			if (campo.equalsIgnoreCase("Tipo via ch")) {
				tcamp = null != detalleMatriculacion.getConductorHabitual().getPersona().getNif() && detalleMatriculacion.getConductorHabitual().getDireccion().getIdTipoVia() != null
						? detalleMatriculacion.getConductorHabitual().getDireccion().getIdTipoVia() : "";
			}

			// {"Nombre via ch", "114"},
			if (campo.equalsIgnoreCase("Nombre via ch")) {
				tcamp = null != detalleMatriculacion.getConductorHabitual().getPersona().getNif() && detalleMatriculacion.getConductorHabitual().getDireccion().getNombreVia() != null ? conversiones
						.ajustarCamposIne(detalleMatriculacion.getConductorHabitual().getDireccion().getNombreVia()) : "";
			}

			// {"Numero via ch","5"},
			if (campo.equalsIgnoreCase("Numero via ch")) {
				tcamp = null != detalleMatriculacion.getConductorHabitual().getPersona().getNif() ? detalleMatriculacion.getConductorHabitual().getDireccion().getNumero() : "";
			}

			if (campo.equalsIgnoreCase("Kilometro ch")) {
				tcamp = null != detalleMatriculacion.getConductorHabitual().getPersona().getNif() && detalleMatriculacion.getConductorHabitual().getDireccion().getKm() != null ? detalleMatriculacion
						.getConductorHabitual().getDireccion().getKm().toString() : "";
			}

			if (campo.equalsIgnoreCase("Hectometro ch")) {
				tcamp = null != detalleMatriculacion.getConductorHabitual().getDireccion().getHm() ? detalleMatriculacion.getConductorHabitual().getDireccion().getHm().toString() : "";
			}

			if (campo.equalsIgnoreCase("Bloque ch")) {
				tcamp = null != detalleMatriculacion.getConductorHabitual().getPersona().getNif() && detalleMatriculacion.getConductorHabitual().getDireccion().getBloque() != null
						? detalleMatriculacion.getConductorHabitual().getDireccion().getBloque() : "";
			}

			if (campo.equalsIgnoreCase("Portal ch")) {
				tcamp = null != detalleMatriculacion.getConductorHabitual().getPersona().getNif() && detalleMatriculacion.getConductorHabitual().getDireccion().getPuerta() != null
						? detalleMatriculacion.getConductorHabitual().getDireccion().getPuerta() : "";
			}

			if (campo.equalsIgnoreCase("Escalera ch")) {
				tcamp = null != detalleMatriculacion.getConductorHabitual().getPersona().getNif() && detalleMatriculacion.getConductorHabitual().getDireccion().getEscalera() != null
						? detalleMatriculacion.getConductorHabitual().getDireccion().getEscalera() : "";
			}

			if (campo.equalsIgnoreCase("Planta ch")) {
				tcamp = null != detalleMatriculacion.getConductorHabitual().getPersona().getNif() && detalleMatriculacion.getConductorHabitual().getDireccion().getPlanta() != null
						? detalleMatriculacion.getConductorHabitual().getDireccion().getPlanta() : "";
			}

			if (campo.equalsIgnoreCase("Puerta ch")) {
				tcamp = null != detalleMatriculacion.getConductorHabitual().getPersona().getNif() && detalleMatriculacion.getConductorHabitual().getDireccion().getLetra() != null
						? detalleMatriculacion.getConductorHabitual().getDireccion().getLetra() : "";
			}

			if (campo.equalsIgnoreCase("Provincia ch")) {
				tcamp = null != detalleMatriculacion.getConductorHabitual().getDireccion().getIdProvincia() && null != detalleMatriculacion.getConductorHabitual().getPersona().getNif() ? conversiones
						.getSiglasFromIdProvincia(detalleMatriculacion.getConductorHabitual().getDireccion().getIdProvincia()) : "";
			}

			if (campo.equalsIgnoreCase("Municipio ch") && detalleMatriculacion.getConductorHabitual().getDireccion().getIdProvincia() != null) {
				tcamp = null != detalleMatriculacion.getConductorHabitual().getPersona().getNif() ? detalleMatriculacion.getConductorHabitual().getDireccion().getIdProvincia() + detalleMatriculacion
						.getConductorHabitual().getDireccion().getIdMunicipio() : "";
			}

			if (campo.equalsIgnoreCase("Pueblo ch")) {
				tcamp = null != detalleMatriculacion.getConductorHabitual().getPersona().getNif() && detalleMatriculacion.getConductorHabitual().getDireccion().getPuebloCorreos() != null
						? detalleMatriculacion.getConductorHabitual().getDireccion().getPuebloCorreos() : "";
			}

			// {"Cpostal ch", "5"},
			if (campo.equalsIgnoreCase("Cpostal ch")) {
				tcamp = null != detalleMatriculacion.getConductorHabitual().getPersona().getNif() && detalleMatriculacion.getConductorHabitual().getDireccion().getCodPostalCorreos() != null
						? detalleMatriculacion.getConductorHabitual().getDireccion().getCodPostalCorreos() : "";
			}

			if (campo.equalsIgnoreCase("Pais ch")) {
				tcamp = null != detalleMatriculacion.getConductorHabitual().getPersona().getNif() ? VALOR_PAIS : "";
			}
		}

		if (detalleMatriculacion.getRepresentanteTitular() != null) {
			// Datos Tutor
			// "DOI Tutor". Se pinta el dni del representante en caso de que sea tutor.
			if (campo.equalsIgnoreCase("DOI Tutor") && detalleMatriculacion.getRepresentanteTitular().getIdMotivoTutela() != null && detalleMatriculacion.getRepresentanteTitular().getConceptoRepre()
					.equals(ConceptoTutela.Tutela.getValorEnum())) {
				tcamp = detalleMatriculacion.getRepresentanteTitular().getPersona().getNif();
			}

			// TIPO DE TUTELA
			if (campo.equalsIgnoreCase("Tipo tutela") && detalleMatriculacion.getRepresentanteTitular().getIdMotivoTutela() != null && detalleMatriculacion.getRepresentanteTitular().getConceptoRepre()
					.equals(ConceptoTutela.Tutela.getValorEnum())) {
				tcamp = detalleMatriculacion.getRepresentanteTitular().getIdMotivoTutela();
			}
		}

		// ************************************* IMPUESTOS **************************************************************
		// {"CEM","8"},{"CEMA", "8"},{"Exencion CEM", "10"},{"Justificado IVTM","1"}
		// CEM && detalleMatriculacion.getTramiteTraficoBean().getCemIedtm()!=null
		if (campo.equalsIgnoreCase("CEM")) {
			if (detalleMatriculacion.getExentoCem() != null && detalleMatriculacion.getExentoCem()) {
				if (detalleMatriculacion.getCem() != null && !detalleMatriculacion.getCem().equals("")) {
					tcamp = detalleMatriculacion.getCem();
				} else {
					tcamp = SIN_CEM;
				}

			} else {
				if (detalleMatriculacion.getCem() != null) {
					tcamp = detalleMatriculacion.getCem();
				} else {
					tcamp = SIN_CEM;
				}
			}
		}

		// CEMA
		if (campo.equalsIgnoreCase("CEMA") && detalleMatriculacion.getCema() != null) {
			tcamp = detalleMatriculacion.getCema();
		}

		// EXENCION CEM
		if (campo.equalsIgnoreCase("Exencion CEM") && detalleMatriculacion.getRegIedtm() != null) {
			tcamp = detalleMatriculacion.getRegIedtm();
		}

		// JUSTIFICADO IVTM (NUEVO CAMPO EN MATW VERSION 2)
		if (campo.equalsIgnoreCase("Justificado IVTM")) {
			tcamp = detalleMatriculacion.getJustificadoIvtm() != null && detalleMatriculacion.getJustificadoIvtm() ? VALOR_S : VALOR_N;
		}

		return tcamp;
	}

	protected boolean rejectedCTITBaja(TramiteTrafDto tramiteTrafico) {
		NotificacionDto filter = new NotificacionDto();
		filter.setIdTramite(tramiteTrafico.getNumExpediente());
		filter.setEstadoNue(new BigDecimal(EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum()));
		ServicioNotificacion servicioNotificacion = ContextoSpring.getInstance().getBean(ServicioNotificacion.class);
		List<NotificacionDto> list = servicioNotificacion.getList(filter);
		if (list != null && !list.isEmpty()) {
			if (tramiteTrafico.getRespuesta() != null && !"TRÁMITE OK".equals(tramiteTrafico.getRespuesta())) {
				return true;
			}
		}
		return false;
	}
}
