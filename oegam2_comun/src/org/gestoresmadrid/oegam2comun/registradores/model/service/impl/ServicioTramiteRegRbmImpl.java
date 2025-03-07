package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import javax.annotation.Resource;
import javax.xml.bind.UnmarshalException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.model.enumerados.TextoNotificacion;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.core.registradores.model.dao.DatosFinancierosDao;
import org.gestoresmadrid.core.registradores.model.dao.TramiteRegRbmDao;
import org.gestoresmadrid.core.registradores.model.dao.TramiteRegistroDao;
import org.gestoresmadrid.core.registradores.model.enumerados.EstadoTramiteRegistro;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoRegistro;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro;
import org.gestoresmadrid.core.registradores.model.vo.DatosFinancierosVO;
import org.gestoresmadrid.core.registradores.model.vo.IntervinienteRegistroVO;
import org.gestoresmadrid.core.registradores.model.vo.TramiteRegRbmVO;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.dpr.build.BuildDpr;
import org.gestoresmadrid.oegam2comun.registradores.enums.TipoCuadroAmortizacion;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.FORMATOGA.CONTRATOFINANCIACION;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.FORMATOGA.CONTRATOLEASING;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.RegistroType;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioDatoFirma;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioDatosInscripcion;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioEntidadCancelacion;
//IMPLEMENTED JVG. 16/05/2018.
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioEvolucionTramiteRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioIntervinienteRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioPropiedad;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioRegistroFueraSecuencia;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioTramiteRegRbm;
//END IMPLEMENTED.
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioTramiteRegistro;
import org.gestoresmadrid.oegam2comun.registradores.rm.build.BuildRm;
import org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesRegistradores;
import org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesValidaciones;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.ClausulaDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.ComisionDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.CuadroAmortizacionDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.DatoFirmaDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.EvolucionTramiteRegistroDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.FicheroInfo;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.IntervinienteRegistroDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.OtroImporteDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.PactoDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.PropiedadDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.ReconocimientoDeudaDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.RegistroDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.RegistroFueraSecuenciaDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.TramiteRegRbmDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.TramiteRegistroDto;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import escrituras.beans.ResultBean;
import utilidades.estructuras.Fecha;
import utilidades.mensajes.Claves;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioTramiteRegRbmImpl implements ServicioTramiteRegRbm {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1540660981901163565L;

	private static Logger log = Logger.getLogger(ServicioTramiteRegRbm.class);

	private static final String NOMBRE_HOST_SOLICITUD = "nombreHostSolicitud";

	// IMPLEMENTED JVG. 16/05/2018.
	public static String ID_TRAMITE_REGISTRO = "IDTRAMITEREGISTRO";

	// IMPLEMENTED JVG. 16/05/2018.
	@Autowired
	private ServicioEvolucionTramiteRegistro servicioEvolucionTramiteRegistro;

	// END IMPLEMENTED.

	@Autowired
	private TramiteRegistroDao tramiteRegistroDao;

	@Autowired
	private TramiteRegRbmDao tramiteRegRbmDao;

	@Autowired
	private DatosFinancierosDao datosFinancierosDao;

	@Autowired
	private ServicioTramiteRegistro servicioTramiteRegistro;

	@Autowired
	private ServicioDatoFirma servicioDatoFirma;

	@Autowired
	private ServicioEntidadCancelacion servicioEntidadCancelacion;

	@Autowired
	private ServicioDatosInscripcion servicioDatosInscripcion;

	@Autowired
	private ServicioIntervinienteRegistro servicioIntervinienteRegistro;

	@Autowired
	private ServicioPropiedad servicioPropiedad;

	@Autowired
	private Conversor conversor;

	@Autowired
	private ServicioContrato servicioContrato;

	@Autowired
	private BuildDpr buildDpr;

	@Autowired
	private BuildRm buildRm;

	@Autowired
	private ServicioCola servicioCola;

	@Autowired
	private ServicioPersona servicioPersona;

	@Autowired
	private ServicioRegistro servicioRegistro;

	@Autowired
	private ServicioRegistroFueraSecuencia servicioRegistroFueraSecuencia;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	// @Autowired
	// private ServicioFacturaRegistro servicioFacturaRegistro;

	@Resource
	private ModelPagination modeloTramiteRegistroPaginated;

	public ServicioTramiteRegRbmImpl() {
		super();
	}

	@Override
	@Transactional(readOnly = true)

	public ResultRegistro getTramiteRegRbm(String name) {

		ResultRegistro resultRegistro = new ResultRegistro();
		TramiteRegRbmDto tramiteRegRbmDto;

		TramiteRegRbmVO tramiteRegRbmVO = tramiteRegRbmDao.getTramiteRegRbm(new BigDecimal(name), true);
		tramiteRegRbmDto = conversor.transform(tramiteRegRbmVO, TramiteRegRbmDto.class);

		// Creamos las listas vacias para evitar luego preguntar si son nulas
		tramiteRegRbmDto.setCesionarios(new ArrayList<IntervinienteRegistroDto>());
		tramiteRegRbmDto.setArrendadores(new ArrayList<IntervinienteRegistroDto>());
		tramiteRegRbmDto.setArrendatarios(new ArrayList<IntervinienteRegistroDto>());
		tramiteRegRbmDto.setAvalistas(new ArrayList<IntervinienteRegistroDto>());
		tramiteRegRbmDto.setCompradores(new ArrayList<IntervinienteRegistroDto>());
		tramiteRegRbmDto.setVendedores(new ArrayList<IntervinienteRegistroDto>());
		tramiteRegRbmDto.setFinanciadores(new ArrayList<IntervinienteRegistroDto>());
		tramiteRegRbmDto.setProveedores(new ArrayList<IntervinienteRegistroDto>());
		tramiteRegRbmDto.setRepresentantesArrendador(new ArrayList<IntervinienteRegistroDto>());
		tramiteRegRbmDto.setRepresentantesArrendatario(new ArrayList<IntervinienteRegistroDto>());
		tramiteRegRbmDto.setRepresentantesAvalista(new ArrayList<IntervinienteRegistroDto>());
		tramiteRegRbmDto.setRepresentantesCesionario(new ArrayList<IntervinienteRegistroDto>());
		tramiteRegRbmDto.setRepresentantesComprador(new ArrayList<IntervinienteRegistroDto>());
		for (IntervinienteRegistroVO elemento : tramiteRegRbmVO.getIntervinienteRegistro()) {
			IntervinienteRegistroDto resultado = conversor.transform(elemento, IntervinienteRegistroDto.class);
			if (TipoInterviniente.Cesionario.getValorEnum().equalsIgnoreCase(resultado.getTipoInterviniente())) {
				tramiteRegRbmDto.getCesionarios().add(resultado);
			} else if (TipoInterviniente.Arrendador.getValorEnum().equalsIgnoreCase(resultado.getTipoInterviniente())) {
				tramiteRegRbmDto.getArrendadores().add(resultado);
			} else if (TipoInterviniente.Arrendatario.getValorEnum().equalsIgnoreCase(resultado.getTipoInterviniente())) {
				tramiteRegRbmDto.getArrendatarios().add(resultado);
			} else if (TipoInterviniente.Avalista.getValorEnum().equalsIgnoreCase(resultado.getTipoInterviniente())) {
				tramiteRegRbmDto.getAvalistas().add(resultado);
			} else if (TipoInterviniente.Comprador.getValorEnum().equalsIgnoreCase(resultado.getTipoInterviniente())) {
				tramiteRegRbmDto.getCompradores().add(resultado);
			} else if (TipoInterviniente.Vendedor.getValorEnum().equalsIgnoreCase(resultado.getTipoInterviniente())) {
				tramiteRegRbmDto.getVendedores().add(resultado);
			} else if (TipoInterviniente.Financiador.getValorEnum().equalsIgnoreCase(resultado.getTipoInterviniente())) {
				tramiteRegRbmDto.getFinanciadores().add(resultado);
			} else if (TipoInterviniente.Proveedor.getValorEnum().equalsIgnoreCase(resultado.getTipoInterviniente())) {
				tramiteRegRbmDto.getProveedores().add(resultado);
			} else if (TipoInterviniente.RepresentanteArrendador.getValorEnum().equalsIgnoreCase(resultado.getTipoInterviniente())) {
				tramiteRegRbmDto.getRepresentantesArrendador().add(resultado);
			} else if (TipoInterviniente.RepresentanteArrendatario.getValorEnum().equalsIgnoreCase(resultado.getTipoInterviniente())) {
				tramiteRegRbmDto.getRepresentantesArrendatario().add(resultado);
			} else if (TipoInterviniente.RepresentanteAvalista.getValorEnum().equalsIgnoreCase(resultado.getTipoInterviniente())) {
				tramiteRegRbmDto.getRepresentantesAvalista().add(resultado);
			} else if (TipoInterviniente.RepresentanteCesionario.getValorEnum().equalsIgnoreCase(resultado.getTipoInterviniente())) {
				tramiteRegRbmDto.getRepresentantesCesionario().add(resultado);
			} else if (TipoInterviniente.RepresentanteComprador.getValorEnum().equalsIgnoreCase(resultado.getTipoInterviniente())) {
				tramiteRegRbmDto.getRepresentantesComprador().add(resultado);
			}
		}
		for (IntervinienteRegistroDto elemento : tramiteRegRbmDto.getArrendadores()) {
			for (IntervinienteRegistroDto representanteElemento : tramiteRegRbmDto.getRepresentantesArrendador()) {
				if (representanteElemento.getIdRepresentado() == elemento.getIdInterviniente()) {
					elemento.getRepresentantes().add(representanteElemento);
				}
			}
		}
		for (IntervinienteRegistroDto elemento : tramiteRegRbmDto.getArrendatarios()) {
			for (IntervinienteRegistroDto representanteElemento : tramiteRegRbmDto.getRepresentantesArrendatario()) {
				if (representanteElemento.getIdRepresentado() == elemento.getIdInterviniente()) {
					elemento.getRepresentantes().add(representanteElemento);
				}
			}
		}
		for (IntervinienteRegistroDto elemento : tramiteRegRbmDto.getAvalistas()) {
			for (IntervinienteRegistroDto representanteElemento : tramiteRegRbmDto.getRepresentantesAvalista()) {
				if (representanteElemento.getIdRepresentado() == elemento.getIdInterviniente()) {
					elemento.getRepresentantes().add(representanteElemento);
				}
			}
		}
		for (IntervinienteRegistroDto elemento : tramiteRegRbmDto.getCesionarios()) {
			for (IntervinienteRegistroDto representanteElemento : tramiteRegRbmDto.getRepresentantesCesionario()) {
				if (representanteElemento.getIdRepresentado() == elemento.getIdInterviniente()) {
					elemento.getRepresentantes().add(representanteElemento);
				}
			}
		}
		for (IntervinienteRegistroDto elemento : tramiteRegRbmDto.getCompradores()) {
			for (IntervinienteRegistroDto representanteElemento : tramiteRegRbmDto.getRepresentantesComprador()) {
				if (representanteElemento.getIdRepresentado() == elemento.getIdInterviniente()) {
					elemento.getRepresentantes().add(representanteElemento);
				}
			}
		}

		tramiteRegRbmDto.setRepresentantesArrendador(new ArrayList<IntervinienteRegistroDto>());
		tramiteRegRbmDto.setRepresentantesArrendatario(new ArrayList<IntervinienteRegistroDto>());
		tramiteRegRbmDto.setRepresentantesAvalista(new ArrayList<IntervinienteRegistroDto>());
		tramiteRegRbmDto.setRepresentantesCesionario(new ArrayList<IntervinienteRegistroDto>());
		tramiteRegRbmDto.setRepresentantesComprador(new ArrayList<IntervinienteRegistroDto>());

		tramiteRegRbmDto.getDatosFinanciero().setCuadrosAmortizacionGE(new ArrayList<CuadroAmortizacionDto>());
		tramiteRegRbmDto.getDatosFinanciero().setCuadrosAmortizacionFI(new ArrayList<CuadroAmortizacionDto>());
		tramiteRegRbmDto.getDatosFinanciero().setCuadrosAmortizacionFS(new ArrayList<CuadroAmortizacionDto>());
		if (null != tramiteRegRbmDto.getDatosFinanciero().getCuadrosAmortizacion()) {
			for (CuadroAmortizacionDto elemento : tramiteRegRbmDto.getDatosFinanciero().getCuadrosAmortizacion()) {
				if (elemento.getTipoPlazo().equalsIgnoreCase(TipoCuadroAmortizacion.GENERIC.name())) {
					tramiteRegRbmDto.getDatosFinanciero().getCuadrosAmortizacionGE().add(elemento);
				} else if (elemento.getTipoPlazo().equalsIgnoreCase(TipoCuadroAmortizacion.ANTICIPATED_FINANCING.name())) {
					tramiteRegRbmDto.getDatosFinanciero().getCuadrosAmortizacionFI().add(elemento);
				} else if (elemento.getTipoPlazo().equalsIgnoreCase(TipoCuadroAmortizacion.INSURANCE_FINANCING.name())) {
					tramiteRegRbmDto.getDatosFinanciero().getCuadrosAmortizacionFS().add(elemento);
				}
			}
		}

		ArrayList<FicheroInfo> ficherosSubidos = servicioTramiteRegistro.recuperarDocumentos(tramiteRegRbmDto.getIdTramiteRegistro(), tramiteRegRbmDto.getTipoTramite());
		if (null != ficherosSubidos && !ficherosSubidos.isEmpty()) {
			tramiteRegRbmDto.setFicherosSubidos(ficherosSubidos);
		}

		ArrayList<FicheroInfo> ficherosRecibidos = servicioTramiteRegistro.recuperarDocumentosRecibidos(tramiteRegRbmDto.getIdTramiteRegistro(), tramiteRegRbmDto.getTipoTramite());
		if (null != ficherosRecibidos && !ficherosRecibidos.isEmpty()) {
			tramiteRegRbmDto.setListaDocuRecibida(ficherosRecibidos);
		}

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		if (null != tramiteRegRbmVO.getDatosFinanciero()) {
			if (null != tramiteRegRbmVO.getDatosFinanciero().getFecRevision()) {
				tramiteRegRbmDto.getDatosFinanciero().setFecRevisionDatFinancieros(new Fecha(df.format(tramiteRegRbmVO.getDatosFinanciero().getFecRevision())));
			}
			if (null != tramiteRegRbmVO.getDatosFinanciero().getFecUltVencimiento()) {
				tramiteRegRbmDto.getDatosFinanciero().setFecUltVencimientoDatFinancieros(new Fecha(df.format(tramiteRegRbmVO.getDatosFinanciero().getFecUltVencimiento())));
			}
		}

		Collections.sort(tramiteRegRbmDto.getCesionarios(), new ComparadorInterviniente());
		Collections.sort(tramiteRegRbmDto.getArrendadores(), new ComparadorInterviniente());
		Collections.sort(tramiteRegRbmDto.getArrendatarios(), new ComparadorInterviniente());
		Collections.sort(tramiteRegRbmDto.getAvalistas(), new ComparadorInterviniente());
		Collections.sort(tramiteRegRbmDto.getCompradores(), new ComparadorInterviniente());
		Collections.sort(tramiteRegRbmDto.getVendedores(), new ComparadorInterviniente());
		Collections.sort(tramiteRegRbmDto.getFinanciadores(), new ComparadorInterviniente());
		Collections.sort(tramiteRegRbmDto.getProveedores(), new ComparadorInterviniente());

		if (null != tramiteRegRbmDto.getDatoFirmas() && !tramiteRegRbmDto.getDatoFirmas().isEmpty()) {
			Collections.sort(tramiteRegRbmDto.getDatoFirmas(), new ComparadorFirma());
		}
		if (null != tramiteRegRbmDto.getClausulas() && !tramiteRegRbmDto.getClausulas().isEmpty()) {
			Collections.sort(tramiteRegRbmDto.getClausulas(), new ComparadorClausulas());
		}
		if (null != tramiteRegRbmDto.getFicherosSubidos() && !tramiteRegRbmDto.getFicherosSubidos().isEmpty()) {
			Collections.sort(tramiteRegRbmDto.getFicherosSubidos(), new ComparadorFichero());
		}
		if (null != tramiteRegRbmDto.getPactos() && !tramiteRegRbmDto.getPactos().isEmpty()) {
			Collections.sort(tramiteRegRbmDto.getPactos(), new ComparadorPacto());
		}
		if (null != tramiteRegRbmDto.getPropiedades() && !tramiteRegRbmDto.getPropiedades().isEmpty()) {
			Collections.sort(tramiteRegRbmDto.getPropiedades(), new ComparadorPropiedad());
		}
		if (null != tramiteRegRbmDto.getDatosFinanciero().getComisiones() && !tramiteRegRbmDto.getDatosFinanciero().getComisiones().isEmpty()) {
			Collections.sort(tramiteRegRbmDto.getDatosFinanciero().getComisiones(), new ComparadorComisiones());
		}
		if (null != tramiteRegRbmDto.getDatosFinanciero().getCuadrosAmortizacion() && !tramiteRegRbmDto.getDatosFinanciero().getCuadrosAmortizacion().isEmpty()) {
			Collections.sort(tramiteRegRbmDto.getDatosFinanciero().getCuadrosAmortizacion(), new ComparadorCuadroAmortizacion());
		}
		if (null != tramiteRegRbmDto.getDatosFinanciero().getCuadrosAmortizacionFI() && !tramiteRegRbmDto.getDatosFinanciero().getCuadrosAmortizacionFI().isEmpty()) {
			Collections.sort(tramiteRegRbmDto.getDatosFinanciero().getCuadrosAmortizacionFI(), new ComparadorCuadroAmortizacion());
		}
		if (null != tramiteRegRbmDto.getDatosFinanciero().getCuadrosAmortizacionFS() && !tramiteRegRbmDto.getDatosFinanciero().getCuadrosAmortizacionFS().isEmpty()) {
			Collections.sort(tramiteRegRbmDto.getDatosFinanciero().getCuadrosAmortizacionFS(), new ComparadorCuadroAmortizacion());
		}
		if (null != tramiteRegRbmDto.getDatosFinanciero().getCuadrosAmortizacionGE() && !tramiteRegRbmDto.getDatosFinanciero().getCuadrosAmortizacionGE().isEmpty()) {
			Collections.sort(tramiteRegRbmDto.getDatosFinanciero().getCuadrosAmortizacionGE(), new ComparadorCuadroAmortizacion());
		}
		if (null != tramiteRegRbmDto.getDatosFinanciero().getOtroImportes() && !tramiteRegRbmDto.getDatosFinanciero().getOtroImportes().isEmpty()) {
			Collections.sort(tramiteRegRbmDto.getDatosFinanciero().getOtroImportes(), new ComparadorOtroImporte());
		}
		if (null != tramiteRegRbmDto.getDatosFinanciero().getReconocimientoDeudas() && !tramiteRegRbmDto.getDatosFinanciero().getReconocimientoDeudas().isEmpty()) {
			Collections.sort(tramiteRegRbmDto.getDatosFinanciero().getReconocimientoDeudas(), new ComparadorReconocimientoDeuda());
		}

		if (StringUtils.isNotBlank(tramiteRegRbmDto.getIdRegistro())) {
			RegistroDto registroDto = servicioRegistro.getRegistroPorId(Long.parseLong(tramiteRegRbmDto.getIdRegistro()));
			tramiteRegRbmDto.setRegistro(registroDto);
			tramiteRegRbmDto.setIdRegistro(Long.toString(registroDto.getId()));
			tramiteRegRbmDto.setRegistroSeleccionadoOculto(Long.toString(registroDto.getId()));
		}

		tramiteRegRbmDto.setPresentador(servicioPersona.obtenerColegiadoCompleto(tramiteRegRbmDto.getNumColegiado(), tramiteRegRbmDto.getIdContrato()));

		tramiteRegRbmDto.setAcusesPendientes(servicioRegistroFueraSecuencia.getRegistrosFueraSecuencia(tramiteRegRbmDto.getIdTramiteRegistro()));
		if (null != tramiteRegRbmDto.getAcusesPendientes() && !tramiteRegRbmDto.getAcusesPendientes().isEmpty()) {
			Collections.sort(tramiteRegRbmDto.getAcusesPendientes(), new ComparadorAcuses());
		}

		resultRegistro.setObj(tramiteRegRbmDto);

		return resultRegistro;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultRegistro getTramiteRegRbmCancelDesist(String name) {

		ResultRegistro resultRegistro = new ResultRegistro();
		TramiteRegRbmDto tramiteRegRbmDto;
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		TramiteRegRbmVO tramiteRegRbmVO = tramiteRegRbmDao.getTramiteRegRbm(new BigDecimal(name), true);
		tramiteRegRbmDto = conversor.transform(tramiteRegRbmVO, TramiteRegRbmDto.class);

		// Creamos las listas vacias para evitar luego preguntar si son nulas

		tramiteRegRbmDto.setRepresentantesSolicitante(new ArrayList<IntervinienteRegistroDto>());
		tramiteRegRbmDto.setCompradoresArrendatarios(new ArrayList<IntervinienteRegistroDto>());
		tramiteRegRbmDto.setSituacionesJuridicas(new ArrayList<String>());
		for (IntervinienteRegistroVO elemento : tramiteRegRbmVO.getIntervinienteRegistro()) {
			IntervinienteRegistroDto resultado = conversor.transform(elemento, IntervinienteRegistroDto.class);
			if (TipoInterviniente.CompradorArrendatario.getValorEnum().equalsIgnoreCase(resultado.getTipoInterviniente())) {
				tramiteRegRbmDto.getCompradoresArrendatarios().add(resultado);
			} else if (TipoInterviniente.RepresentanteSolicitante.getValorEnum().equalsIgnoreCase(resultado.getTipoInterviniente())) {
				if (TipoTramiteRegistro.MODELO_10.getValorEnum().equalsIgnoreCase(tramiteRegRbmDto.getTipoTramite())) {

					if (null != resultado.getNotario() && null != resultado.getNotario().getFecOtorgamiento()) {
						resultado.getNotario().setFecOtorgamientoNotario(new Fecha(df.format(resultado.getNotario().getFecOtorgamiento())));
					}

					tramiteRegRbmDto.setRepresentanteSolicitante(resultado);
				} else {
					tramiteRegRbmDto.getRepresentantesSolicitante().add(resultado);
				}
			} else if (TipoInterviniente.Solicitante.getValorEnum().equalsIgnoreCase(resultado.getTipoInterviniente())) {

				tramiteRegRbmDto.setSolicitante(resultado);
			}
		}

		if (null != tramiteRegRbmDto.getDatoFirmas() && !tramiteRegRbmDto.getDatoFirmas().isEmpty()) {
			tramiteRegRbmDto.setDatoFirmaDto(tramiteRegRbmDto.getDatoFirmas().get(0));
			if (null != tramiteRegRbmDto.getDatoFirmaDto().getFecFirma()) {
				tramiteRegRbmDto.getDatoFirmaDto().setFecFirmaDatFirma(new Fecha(df.format(new Date(tramiteRegRbmDto.getDatoFirmaDto().getFecFirma().getTime()))));
			}

			if (StringUtils.isNotBlank(tramiteRegRbmDto.getDatoFirmaDto().getLugar())) {
				tramiteRegRbmDto.getDatoFirmaDto().setDireccion(servicioDatoFirma.convertirLugarFirma(tramiteRegRbmDto.getDatoFirmaDto().getLugar()));
			}

		}

		if (null != tramiteRegRbmDto.getDatosInscripcion() && null != tramiteRegRbmDto.getDatosInscripcion().getFechaInscripcion()) {
			tramiteRegRbmDto.getDatosInscripcion().setFechaInscripcionDatInscrip(new Fecha(df.format(new Date(tramiteRegRbmDto.getDatosInscripcion().getFechaInscripcion().getTime()))));
		}

		if (null != tramiteRegRbmDto.getFecEntradaOrigen()) {
			tramiteRegRbmDto.setFecEntradaOrigenDesistimim(new Fecha(df.format(tramiteRegRbmDto.getFecEntradaOrigen())));
		}

		if (null != tramiteRegRbmDto.getFecPresentacionOrigen()) {
			tramiteRegRbmDto.setFecPresentacionOrigenDesistimim(new Fecha(df.format(tramiteRegRbmDto.getFecPresentacionOrigen())));
		}

		// Representantes solicitante trámite Cancelación
		if (null != tramiteRegRbmDto.getSolicitante() && null != tramiteRegRbmDto.getRepresentanteSolicitante() && tramiteRegRbmDto.getRepresentanteSolicitante()
				.getIdRepresentado() == tramiteRegRbmDto.getSolicitante().getIdInterviniente()) {
			tramiteRegRbmDto.getSolicitante().getRepresentantes().add(tramiteRegRbmDto.getRepresentanteSolicitante());
		}

		// Representantes solicitante trámite Desistimiento
		if (null != tramiteRegRbmDto.getSolicitante() && null != tramiteRegRbmDto.getRepresentantesSolicitante() && !tramiteRegRbmDto.getRepresentantesSolicitante().isEmpty()) {
			for (IntervinienteRegistroDto representanteElemento : tramiteRegRbmDto.getRepresentantesSolicitante()) {
				if (representanteElemento.getIdRepresentado() == tramiteRegRbmDto.getSolicitante().getIdInterviniente()) {
					tramiteRegRbmDto.getSolicitante().getRepresentantes().add(representanteElemento);
				}
			}
		}

		Collections.sort(tramiteRegRbmDto.getCompradoresArrendatarios(), new ComparadorInterviniente());

		if (null != tramiteRegRbmDto.getPropiedades() && !tramiteRegRbmDto.getPropiedades().isEmpty()) {
			tramiteRegRbmDto.setPropiedadDto(tramiteRegRbmDto.getPropiedades().get(0));
		}

		if (StringUtils.isNotBlank(tramiteRegRbmDto.getSituacionJuridicaCancelacion())) {
			String[] situaciones = tramiteRegRbmDto.getSituacionJuridicaCancelacion().split(";", 3);

			for (String situacion : situaciones) {
				tramiteRegRbmDto.getSituacionesJuridicas().add(situacion);
			}
		}

		if (StringUtils.isNotBlank(tramiteRegRbmDto.getIdRegistro())) {
			RegistroDto registroDto = servicioRegistro.getRegistroPorId(Long.parseLong(tramiteRegRbmDto.getIdRegistro()));
			tramiteRegRbmDto.setRegistro(registroDto);
			tramiteRegRbmDto.setIdRegistro(Long.toString(registroDto.getId()));
			tramiteRegRbmDto.setRegistroSeleccionadoOculto(Long.toString(registroDto.getId()));
		}

		tramiteRegRbmDto.setPresentador(servicioPersona.obtenerColegiadoCompleto(tramiteRegRbmDto.getNumColegiado(), tramiteRegRbmDto.getIdContrato()));

		tramiteRegRbmDto.setAcusesPendientes(servicioRegistroFueraSecuencia.getRegistrosFueraSecuencia(tramiteRegRbmDto.getIdTramiteRegistro()));
		if (null != tramiteRegRbmDto.getAcusesPendientes() && !tramiteRegRbmDto.getAcusesPendientes().isEmpty()) {
			Collections.sort(tramiteRegRbmDto.getAcusesPendientes(), new ComparadorAcuses());
		}

		// tramiteRegRbmDto.setFacturasRegistro(servicioFacturaRegistro.getFacturasPorTramite(tramiteRegRbmDto.getIdTramiteRegistro()));

		ArrayList<FicheroInfo> ficherosSubidos = servicioTramiteRegistro.recuperarDocumentos(tramiteRegRbmDto.getIdTramiteRegistro(), tramiteRegRbmDto.getTipoTramite());
		if (null != ficherosSubidos && !ficherosSubidos.isEmpty()) {
			tramiteRegRbmDto.setFicherosSubidos(ficherosSubidos);
		}

		ArrayList<FicheroInfo> ficherosRecibidos = servicioTramiteRegistro.recuperarDocumentosRecibidos(tramiteRegRbmDto.getIdTramiteRegistro(), tramiteRegRbmDto.getTipoTramite());
		if (null != ficherosRecibidos && !ficherosRecibidos.isEmpty()) {
			tramiteRegRbmDto.setListaDocuRecibida(ficherosRecibidos);
		}
		resultRegistro.setObj(tramiteRegRbmDto);

		return resultRegistro;
	}

	private class ComparadorAcuses implements Comparator<RegistroFueraSecuenciaDto> {

		@Override
		public int compare(RegistroFueraSecuenciaDto o1, RegistroFueraSecuenciaDto o2) {
			try {
				return (o1.getFechaAlta().getDate()).compareTo(o2.getFechaAlta().getDate());
			} catch (ParseException e) {
				log.error("Error al ordenar por fecha los acuses pendientes: ", e);
			}
			return 0;
		}

	}

	@Override
	@Transactional(readOnly = true)
	public ResultRegistro getListasCancelacion(String name) {

		ResultRegistro resultRegistro = new ResultRegistro();
		TramiteRegRbmDto tramiteRegRbmDto;

		TramiteRegRbmVO tramiteRegRbmVO = tramiteRegRbmDao.getTramiteRegRbm(new BigDecimal(name), true);
		tramiteRegRbmDto = conversor.transform(tramiteRegRbmVO, TramiteRegRbmDto.class);

		// Creamos las listas vacias para evitar luego preguntar si son nulas

		tramiteRegRbmDto.setRepresentantesSolicitante(new ArrayList<IntervinienteRegistroDto>());
		tramiteRegRbmDto.setCompradoresArrendatarios(new ArrayList<IntervinienteRegistroDto>());
		tramiteRegRbmDto.setSituacionesJuridicas(new ArrayList<String>());
		for (IntervinienteRegistroVO elemento : tramiteRegRbmVO.getIntervinienteRegistro()) {
			IntervinienteRegistroDto resultado = conversor.transform(elemento, IntervinienteRegistroDto.class);
			if (TipoInterviniente.CompradorArrendatario.getValorEnum().equalsIgnoreCase(resultado.getTipoInterviniente())) {
				tramiteRegRbmDto.getCompradoresArrendatarios().add(resultado);
			} else if (TipoInterviniente.RepresentanteSolicitante.getValorEnum().equalsIgnoreCase(resultado.getTipoInterviniente())) {
				tramiteRegRbmDto.getRepresentantesSolicitante().add(resultado);
			}

		}

		if (null != tramiteRegRbmDto.getSolicitante() && null != tramiteRegRbmDto.getRepresentantesSolicitante() && !tramiteRegRbmDto.getRepresentantesSolicitante().isEmpty()) {
			for (IntervinienteRegistroDto representanteElemento : tramiteRegRbmDto.getRepresentantesSolicitante()) {
				if (representanteElemento.getIdRepresentado() == tramiteRegRbmDto.getSolicitante().getIdInterviniente()) {
					tramiteRegRbmDto.getSolicitante().getRepresentantes().add(representanteElemento);
				}
			}
		}

		Collections.sort(tramiteRegRbmDto.getCompradoresArrendatarios(), new ComparadorInterviniente());

		if (StringUtils.isNotBlank(tramiteRegRbmDto.getSituacionJuridicaCancelacion())) {
			String[] situaciones = tramiteRegRbmDto.getSituacionJuridicaCancelacion().split(";", 3);

			for (String situacion : situaciones) {
				tramiteRegRbmDto.getSituacionesJuridicas().add(situacion);
			}
		}

		resultRegistro.setObj(tramiteRegRbmDto);

		return resultRegistro;
	}

	@Override
	@Transactional
	public ResultRegistro borrarTramiteRegRbm(String name) {
		log.debug("Servicio ServicioTramiteRegRbmImpl.borrarTramiteRegRbm. Argumento: " + name);
		ResultRegistro result = new ResultRegistro();
		TramiteRegRbmVO tramite = null;
		try {
			tramite = tramiteRegRbmDao.getTramiteRegRbm(new BigDecimal(name), false);
			if (null != tramite) {
				tramite.setEstado(new BigDecimal(EstadoTramiteRegistro.Anulado.getValorEnum()));
				tramite = tramiteRegRbmDao.actualizar(tramite);
			}
		} catch (Exception e) {
			log.error("Error al borrar el contrato", e);
			result.setError(Boolean.TRUE);
			result.setMensaje(e.getMessage());
			return result;
		}
		if (tramite == null) {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al borrar el contrato");
		}
		return result;
	}

	@Override
	@Transactional
	public ResultRegistro guardarTramiteRegRbm(TramiteRegRbmDto tramiteRegRbmDto) {
		ResultRegistro resultado = new ResultRegistro();
		boolean primerGuardado = false;

		log.debug("Servicio ServicioTramiteRegRbmImpl.guardarTramiteRegRbm");
		try {
			// Guardamos la tabla Trámite Registro
			if (null == tramiteRegRbmDto.getIdTramiteRegistro()) {
				ContratoVO contrato = servicioContrato.getContrato(tramiteRegRbmDto.getIdContrato());
				if (contrato != null && contrato.getColegio() != null && contrato.getColegio().getCif() != null && !contrato.getColegio().getCif().isEmpty()) {
					tramiteRegRbmDto.setIdTramiteRegistro(tramiteRegistroDao.generarIdTramiteRegistro(contrato.getColegiado().getNumColegiado(), tramiteRegRbmDto.getTipoTramite()));
					tramiteRegRbmDto.setIdTramiteCorpme(tramiteRegistroDao.generarIdTramiteCorpme(contrato.getColegiado().getNumColegiado(), contrato.getColegio().getCif()));
					tramiteRegRbmDto.setNumColegiado(contrato.getColegiado().getNumColegiado());
					tramiteRegRbmDto.setFechaCreacion(utilesFecha.getFechaHoraActualLEG());
					tramiteRegRbmDto.getDatosFinanciero().setFecCreacion(utilesFecha.getTimestampActual());
					tramiteRegRbmDto.setEstado(new BigDecimal(EstadoTramiteRegistro.Iniciado.getValorEnum()));
					primerGuardado = true;
				} else {
					resultado.setError(Boolean.TRUE);
					log.error("Error al guardar el contrato");
					resultado.setMensaje("Error al guardar el contrato");
					return resultado;
				}
			}

			// -------------------------------------------------------------------------------
			// Fechas tabla Datos Financieros
			if (null != tramiteRegRbmDto.getDatosFinanciero().getFecRevisionDatFinancieros()) {
				tramiteRegRbmDto.getDatosFinanciero().setFecRevision(tramiteRegRbmDto.getDatosFinanciero().getFecRevisionDatFinancieros().getDate());
			}
			if (null != tramiteRegRbmDto.getDatosFinanciero().getFecUltVencimientoDatFinancieros()) {
				tramiteRegRbmDto.getDatosFinanciero().setFecUltVencimiento(tramiteRegRbmDto.getDatosFinanciero().getFecUltVencimientoDatFinancieros().getDate());
			}

			tramiteRegRbmDto.setFecModificacion(utilesFecha.getTimestampActual());

			DatosFinancierosVO datosFinancierosVO = conversor.transform(tramiteRegRbmDto.getDatosFinanciero(), DatosFinancierosVO.class);
			datosFinancierosVO = datosFinancierosDao.guardarOActualizar(datosFinancierosVO);

			// Recogemos el idDatosFinancieros
			tramiteRegRbmDto.getDatosFinanciero().setIdDatosFinancieros(datosFinancierosVO.getIdDatosFinancieros());

			// -----------------------------------------------------------------------------------

			TramiteRegRbmVO tramiteRegRbmVO = conversor.transform(tramiteRegRbmDto, TramiteRegRbmVO.class);
			tramiteRegRbmDao.guardarOActualizar(tramiteRegRbmVO);

			// -----------------------------------------------------------------------------------
			// Interviniente Cesionario
			if (null != tramiteRegRbmDto.getCesionario() && StringUtils.isNotBlank(tramiteRegRbmDto.getCesionario().getNif()) && StringUtils.isNotBlank(tramiteRegRbmDto.getCesionario().getPersona()
					.getApellido1RazonSocial())) {
				tramiteRegRbmDto.getCesionario().setTipoInterviniente(TipoInterviniente.Cesionario.getValorEnum());

				resultado = servicioIntervinienteRegistro.guardarInterviniente(tramiteRegRbmDto.getCesionario(), tramiteRegRbmDto);
				if (resultado.isError()) {
					return resultado;
				} else {
					tramiteRegRbmDto.setCesionario((IntervinienteRegistroDto) resultado.getObj());
				}
			}

			// -----------------------------------------------------------------------------------
			// Interviniente Arrendatario
			if (null != tramiteRegRbmDto.getArrendatario() && StringUtils.isNotBlank(tramiteRegRbmDto.getArrendatario().getNif()) && StringUtils.isNotBlank(tramiteRegRbmDto.getArrendatario()
					.getPersona().getApellido1RazonSocial())) {
				tramiteRegRbmDto.getArrendatario().setTipoInterviniente(TipoInterviniente.Arrendatario.getValorEnum());

				resultado = servicioIntervinienteRegistro.guardarInterviniente(tramiteRegRbmDto.getArrendatario(), tramiteRegRbmDto);
				if (resultado.isError()) {
					return resultado;
				} else {
					tramiteRegRbmDto.setArrendatario((IntervinienteRegistroDto) resultado.getObj());
				}
			}

			// -----------------------------------------------------------------------------------
			// Interviniente Arrendador
			if (null != tramiteRegRbmDto.getArrendador() && StringUtils.isNotBlank(tramiteRegRbmDto.getArrendador().getNif()) && StringUtils.isNotBlank(tramiteRegRbmDto.getArrendador().getPersona()
					.getApellido1RazonSocial())) {
				tramiteRegRbmDto.getArrendador().setTipoInterviniente(TipoInterviniente.Arrendador.getValorEnum());

				resultado = servicioIntervinienteRegistro.guardarInterviniente(tramiteRegRbmDto.getArrendador(), tramiteRegRbmDto);

				if (resultado.isError()) {
					return resultado;
				} else {
					tramiteRegRbmDto.setArrendador((IntervinienteRegistroDto) resultado.getObj());
				}
			}
			// -----------------------------------------------------------------------------------
			// Interviniente Comprador
			if (null != tramiteRegRbmDto.getComprador() && StringUtils.isNotBlank(tramiteRegRbmDto.getComprador().getNif()) && StringUtils.isNotBlank(tramiteRegRbmDto.getComprador().getPersona()
					.getApellido1RazonSocial())) {
				tramiteRegRbmDto.getComprador().setTipoInterviniente(TipoInterviniente.Comprador.getValorEnum());

				resultado = servicioIntervinienteRegistro.guardarInterviniente(tramiteRegRbmDto.getComprador(), tramiteRegRbmDto);
				if (resultado.isError()) {
					return resultado;
				} else {
					tramiteRegRbmDto.setComprador((IntervinienteRegistroDto) resultado.getObj());
				}
			}

			// -----------------------------------------------------------------------------------
			// Interviniente Vendedor
			if (null != tramiteRegRbmDto.getVendedor() && StringUtils.isNotBlank(tramiteRegRbmDto.getVendedor().getNif()) && StringUtils.isNotBlank(tramiteRegRbmDto.getVendedor().getPersona()
					.getApellido1RazonSocial())) {
				tramiteRegRbmDto.getVendedor().setTipoInterviniente(TipoInterviniente.Vendedor.getValorEnum());

				resultado = servicioIntervinienteRegistro.guardarInterviniente(tramiteRegRbmDto.getVendedor(), tramiteRegRbmDto);
				if (resultado.isError()) {
					return resultado;
				} else {
					tramiteRegRbmDto.setVendedor((IntervinienteRegistroDto) resultado.getObj());
				}
			}

			// -----------------------------------------------------------------------------------
			// Interviniente Avalista
			if (null != tramiteRegRbmDto.getAvalista() && StringUtils.isNotBlank(tramiteRegRbmDto.getAvalista().getNif()) && StringUtils.isNotBlank(tramiteRegRbmDto.getAvalista().getPersona()
					.getApellido1RazonSocial())) {
				tramiteRegRbmDto.getAvalista().setTipoInterviniente(TipoInterviniente.Avalista.getValorEnum());

				resultado = servicioIntervinienteRegistro.guardarInterviniente(tramiteRegRbmDto.getAvalista(), tramiteRegRbmDto);
				if (resultado.isError()) {
					return resultado;
				} else {
					tramiteRegRbmDto.setAvalista((IntervinienteRegistroDto) resultado.getObj());
				}
			}

			// -----------------------------------------------------------------------------------
			// Bienes
			if (StringUtils.isNotBlank(tramiteRegRbmDto.getPropiedadDto().getCategoria())) {
				// Guardamos el número registral del bien en la entidad propiedad
				resultado = servicioPropiedad.guardarOActualizarPropiedad(tramiteRegRbmDto);
				if (resultado.isError()) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return resultado;
				}
			}

			// -----------------------------------------------------------------------------------
		} catch (Exception e) {
			resultado.setError(Boolean.TRUE);
			log.error("Error al guardar el trámite, error: ", e);
			resultado.setMensaje("Error al guardar el trámite");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			if (primerGuardado)
				tramiteRegRbmDto.setIdTramiteRegistro(null);
		}
		if (resultado.isError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			if (primerGuardado)
				tramiteRegRbmDto.setIdTramiteRegistro(null);
		} else {
			resultado.setObj(tramiteRegRbmDto);
		}

		return resultado;
	}

	@Override
	@Transactional
	public ResultRegistro guardarTramiteRegRbmCancelDesist(TramiteRegRbmDto tramiteRegRbmDto, BigDecimal estadoAnterior, BigDecimal idUsuario) {
		ResultRegistro resultado = new ResultRegistro();
		boolean primerGuardado = false;

		log.debug("Servicio ServicioTramiteRegRbmImpl.guardarTramiteRegRbmCancelacion");
		try {
			// Guardamos la tabla Trámite Registro
			if (null == tramiteRegRbmDto.getIdTramiteRegistro()) {
				ContratoVO contrato = servicioContrato.getContrato(tramiteRegRbmDto.getIdContrato());
				if (contrato != null && contrato.getColegio() != null && contrato.getColegio().getCif() != null && !contrato.getColegio().getCif().isEmpty()) {
					tramiteRegRbmDto.setIdTramiteRegistro(tramiteRegistroDao.generarIdTramiteRegistro(contrato.getColegiado().getNumColegiado(), tramiteRegRbmDto.getTipoTramite()));
					tramiteRegRbmDto.setIdTramiteCorpme(tramiteRegistroDao.generarIdTramiteCorpme(contrato.getColegiado().getNumColegiado(), contrato.getColegio().getCif()));
					tramiteRegRbmDto.setNumColegiado(contrato.getColegiado().getNumColegiado());
					tramiteRegRbmDto.setFechaCreacion(utilesFecha.getFechaHoraActualLEG());
					tramiteRegRbmDto.setEstado(new BigDecimal(EstadoTramiteRegistro.Iniciado.getValorEnum()));
					primerGuardado = true;
				} else {
					log.error("Error al guardar el contrato");
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Error al guardar el contrato");
					return resultado;
				}
			}

			// -------------------------------------------------------------------------------

			if (null != tramiteRegRbmDto.getFecEntradaOrigenDesistimim()) {
				tramiteRegRbmDto.setFecEntradaOrigen(tramiteRegRbmDto.getFecEntradaOrigenDesistimim().getTimestamp());
			}

			if (null != tramiteRegRbmDto.getFecPresentacionOrigenDesistimim()) {
				tramiteRegRbmDto.setFecPresentacionOrigen(tramiteRegRbmDto.getFecPresentacionOrigenDesistimim().getTimestamp());
			}

			tramiteRegRbmDto.setFecModificacion(utilesFecha.getTimestampActual());

			// ----------------------------------------------------------------------------------
			// Bienes
			if (null != tramiteRegRbmDto.getPropiedadDto() && StringUtils.isNotBlank(tramiteRegRbmDto.getPropiedadDto().getCategoria()) && null != tramiteRegRbmDto.getPropiedadDto().getVehiculo()
					&& (StringUtils.isNotBlank(tramiteRegRbmDto.getPropiedadDto().getVehiculo().getTipo()) || StringUtils.isNotBlank(tramiteRegRbmDto.getPropiedadDto().getVehiculo().getMarca())
							|| StringUtils.isNotBlank(tramiteRegRbmDto.getPropiedadDto().getVehiculo().getModelo()) || StringUtils.isNotBlank(tramiteRegRbmDto.getPropiedadDto().getVehiculo()
									.getMatricula()) || StringUtils.isNotBlank(tramiteRegRbmDto.getPropiedadDto().getVehiculo().getBastidor()) || StringUtils.isNotBlank(tramiteRegRbmDto
											.getPropiedadDto().getVehiculo().getNive()))) {

				// Guardamos el número registral del bien en la entidad propiedad
				if (null != tramiteRegRbmDto.getDatosInscripcion() && null != tramiteRegRbmDto.getDatosInscripcion().getNumeroRegistralBien()) {
					tramiteRegRbmDto.getPropiedadDto().setNumeroRegistral(tramiteRegRbmDto.getDatosInscripcion().getNumeroRegistralBien().toString());
				}

				resultado = servicioPropiedad.guardarOActualizarPropiedad(tramiteRegRbmDto);
				if (resultado.isError()) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					if (primerGuardado)
						tramiteRegRbmDto.setIdTramiteRegistro(null);
					return resultado;
				}
			}
			// ----------------------------------------------------------------------------------

			// Tabla EntidadCancelacion entidadSuscriptora
			// if(null != tramiteRegRbmDto.getEntidadSuscriptora() && (StringUtils.isNotBlank(tramiteRegRbmDto.getEntidadSuscriptora().getCodigoIdentificacionFiscal())
			// || StringUtils.isNotBlank(tramiteRegRbmDto.getEntidadSuscriptora().getRazonSocial()))){
			//
			// tramiteRegRbmDto.getEntidadSuscriptora().setDatRegMercantil(null);
			// tramiteRegRbmDto.getEntidadSuscriptora().setIdContrato(tramiteRegRbmDto.getIdContrato());
			//
			// if(tramiteRegRbmDto.getEntidadSucesora().getCodigoIdentificacionFiscal().equalsIgnoreCase(tramiteRegRbmDto.getEntidadSuscriptora().getCodigoIdentificacionFiscal())){
			// tramiteRegRbmDto.getEntidadSuscriptora().setDatRegMercantil(tramiteRegRbmDto.getEntidadSucesora().getDatRegMercantil());
			// }
			// resultado = servicioEntidadCancelacion.guardarOActualizarEntidadCancelacion(tramiteRegRbmDto.getEntidadSuscriptora());
			//
			// //Recogemos el idEntidad de la entidad suscriptora
			// if(resultado.isError()){
			// TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			// if(primerGuardado)
			// tramiteRegRbmDto.setIdTramiteRegistro(null);
			// return resultado;
			// }else{
			// tramiteRegRbmDto.getEntidadSuscriptora().setIdEntidad((long)resultado.getObj());
			// }
			// }else{
			// tramiteRegRbmDto.setEntidadSuscriptora(null);
			// }

			// -----------------------------------------------------------------------------------
			// Tabla EntidadCancelacion entidadSucesora
			// if(tramiteRegRbmDto.getEntidadSuscriptora()!=null && tramiteRegRbmDto.getEntidadSucesora().getCodigoIdentificacionFiscal().equalsIgnoreCase(tramiteRegRbmDto.getEntidadSuscriptora().getCodigoIdentificacionFiscal())){
			// tramiteRegRbmDto.getEntidadSucesora().setIdEntidad(tramiteRegRbmDto.getEntidadSuscriptora().getIdEntidad());
			// }else{
			if (null != tramiteRegRbmDto.getEntidadSucesora() && (StringUtils.isNotBlank(tramiteRegRbmDto.getEntidadSucesora().getCodigoIdentificacionFiscal()) || StringUtils.isNotBlank(
					tramiteRegRbmDto.getEntidadSucesora().getRazonSocial()))) {

				tramiteRegRbmDto.getEntidadSucesora().setIdContrato(tramiteRegRbmDto.getIdContrato());
				resultado = servicioEntidadCancelacion.guardarOActualizarEntidadCancelacion(tramiteRegRbmDto.getEntidadSucesora());

				// Recogemos el idEntidad de la entidad sucesora
				if (resultado.isError()) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					if (primerGuardado)
						tramiteRegRbmDto.setIdTramiteRegistro(null);
					return resultado;
				} else {
					tramiteRegRbmDto.getEntidadSucesora().setIdEntidad((long) resultado.getObj());
				}
			} else {
				tramiteRegRbmDto.setEntidadSucesora(null);
			}
			// }

			// -----------------------------------------------------------------------------------
			// Tabla DatosInscripcion
			if (null != tramiteRegRbmDto.getDatosInscripcion() && (StringUtils.isNotBlank(tramiteRegRbmDto.getDatosInscripcion().getCodigoRbm()) || UtilesValidaciones.validarObligatoriedad(
					tramiteRegRbmDto.getDatosInscripcion().getNumeroRegistralBien()) || UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosInscripcion().getNumeroInscripcionBien())
					|| UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosInscripcion().getFechaInscripcionDatInscrip()))) {

				resultado = servicioDatosInscripcion.guardarOActualizarDatosInscripcion(tramiteRegRbmDto.getDatosInscripcion());

				// Recogemos el idDatosInscripcion de DatosInscripcion
				if (resultado.isError()) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					if (primerGuardado)
						tramiteRegRbmDto.setIdTramiteRegistro(null);
					return resultado;
				} else {
					tramiteRegRbmDto.getDatosInscripcion().setIdDatosInscripcion((long) resultado.getObj());
				}
			} else {
				tramiteRegRbmDto.setDatosInscripcion(null);
			}
			// -----------------------------------------------------------------------------------
			TramiteRegRbmVO tramiteRegRbmVO = conversor.transform(tramiteRegRbmDto, TramiteRegRbmVO.class);
			tramiteRegRbmVO.setDatosFinanciero(null);
			tramiteRegRbmDao.guardarOActualizar(tramiteRegRbmVO);

			if (primerGuardado) {
				servicioTramiteRegistro.guardarEvolucionTramite(tramiteRegRbmDto.getIdTramiteRegistro(), BigDecimal.ZERO, tramiteRegRbmDto.getEstado(), idUsuario);
			}

			// -----------------------------------------------------------------------------------
			// Interviniente Solicitante
			if (null != tramiteRegRbmDto.getSolicitante() && StringUtils.isNotBlank(tramiteRegRbmDto.getSolicitante().getTipoPersona()) && StringUtils.isNotBlank(tramiteRegRbmDto.getSolicitante()
					.getNif()) && StringUtils.isNotBlank(tramiteRegRbmDto.getSolicitante().getPersona().getApellido1RazonSocial())) {
				tramiteRegRbmDto.getSolicitante().setTipoInterviniente(TipoInterviniente.Solicitante.getValorEnum());

				resultado = servicioIntervinienteRegistro.guardarInterviniente(tramiteRegRbmDto.getSolicitante(), tramiteRegRbmDto);
				if (resultado.isError()) {
					return resultado;
				} else { // Damos de alta el Representante Solicitante
					if (null != tramiteRegRbmDto.getRepresentanteSolicitante() && StringUtils.isNotBlank(tramiteRegRbmDto.getRepresentanteSolicitante().getTipoPersona()) && StringUtils.isNotBlank(
							tramiteRegRbmDto.getRepresentanteSolicitante().getNif()) && StringUtils.isNotBlank(tramiteRegRbmDto.getRepresentanteSolicitante().getPersona().getApellido1RazonSocial())) {

						tramiteRegRbmDto.getRepresentanteSolicitante().setIdRepresentado(((IntervinienteRegistroDto) resultado.getObj()).getIdInterviniente());
						tramiteRegRbmDto.getRepresentanteSolicitante().setTipoInterviniente(TipoInterviniente.RepresentanteSolicitante.getValorEnum());
						if (null != tramiteRegRbmDto.getRepresentanteSolicitante().getNotario() && null != tramiteRegRbmDto.getRepresentanteSolicitante().getNotario().getFecOtorgamientoNotario()) {
							tramiteRegRbmDto.getRepresentanteSolicitante().getNotario().setFecOtorgamiento(tramiteRegRbmDto.getRepresentanteSolicitante().getNotario().getFecOtorgamientoNotario()
									.getTimestamp());
						}
						if (TipoTramiteRegistro.MODELO_10.getValorEnum().equalsIgnoreCase(tramiteRegRbmDto.getTipoTramite())) {
							resultado = servicioIntervinienteRegistro.guardarIntervinienteCancelacion(tramiteRegRbmDto.getRepresentanteSolicitante(), tramiteRegRbmDto);
						} else {
							resultado = servicioIntervinienteRegistro.guardarInterviniente(tramiteRegRbmDto.getRepresentanteSolicitante(), tramiteRegRbmDto);
						}
						if (resultado.isError()) {
							return resultado;
						}
					}
				}
			}

			// -----------------------------------------------------------------------------------
			// Interviniente Comprador Arrendatario
			if (null != tramiteRegRbmDto.getCompradorArrendatario() && StringUtils.isNotBlank(tramiteRegRbmDto.getCompradorArrendatario().getNif()) && StringUtils.isNotBlank(tramiteRegRbmDto
					.getCompradorArrendatario().getPersona().getApellido1RazonSocial())) {
				tramiteRegRbmDto.getCompradorArrendatario().setTipoInterviniente(TipoInterviniente.CompradorArrendatario.getValorEnum());

				resultado = servicioIntervinienteRegistro.guardarIntervinienteCancelacion(tramiteRegRbmDto.getCompradorArrendatario(), tramiteRegRbmDto);
				if (resultado.isError()) {
					return resultado;
				}
			}

			// ----------------------------------------------------------------------------------
			// Tabla datos Firma
			if (StringUtils.isNotBlank(tramiteRegRbmDto.getDatoFirmaDto().getLugar()) || UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatoFirmaDto().getFecFirmaDatFirma())) {
				resultado = servicioDatoFirma.guardarOActualizarDatosFirma(tramiteRegRbmDto.getDatoFirmaDto(), tramiteRegRbmDto.getIdTramiteRegistro(), tramiteRegRbmDto.getTipoTramite());
				if (resultado.isError()) {
					return resultado;
				}
			}

			// -----------------------------------------------------------------------------------

		} catch (Exception e) {
			log.error("Error al guardar el trámite, error: ", e);
			resultado.setMensaje("Error al guardar el trámite");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			if (primerGuardado)
				tramiteRegRbmDto.setIdTramiteRegistro(null);
		}
		if (!resultado.isError()) {
			resultado.setObj(tramiteRegRbmDto);
		}

		return resultado;
	}

	@Override
	@Transactional
	public ResultRegistro construirDpr(String idRegistro, String alias) {

		ResultRegistro resultRegistro = new ResultRegistro();
		String identificacionCorpme = null;
		ResultBean result;

		TramiteRegistroDto tramiteRegistroDto = servicioTramiteRegistro.getTramite(new BigDecimal(idRegistro.trim()));

		if (tramiteRegistroDto.getEstado() == null) {
			resultRegistro.setError(Boolean.TRUE);
			resultRegistro.setMensaje("No se ha podido recuperar el trámite: " + idRegistro);
		} else if (tramiteRegistroDto.getEstado().equals(new BigDecimal(EstadoTramiteRegistro.Pendiente_Envio.getValorEnum()))) {
			resultRegistro.setError(Boolean.TRUE);
			resultRegistro.setMensaje("El trámite: " + idRegistro + " ya se había enviado Dpr.");
		} else if (!UtilesRegistradores.permitidoEnvio(tramiteRegistroDto.getEstado())) {
			resultRegistro.setError(Boolean.TRUE);
			resultRegistro.setMensaje("El estado actual del trámite  " + idRegistro + " no permite el envío del mismo.");
		} else {

			try {

				if (TipoTramiteRegistro.MODELO_10.getValorEnum().equalsIgnoreCase(tramiteRegistroDto.getTipoTramite()) && gestorPropiedades.valorPropertie(
						"establecer.usuario.abonado.corpme.cancelaciones").equalsIgnoreCase("SI")) {
					identificacionCorpme = gestorPropiedades.valorPropertie("codigo.usuario.abonado.corpme.cancelaciones");
				} else {
					PersonaDto colegiado = servicioPersona.obtenerColegiadoCompleto(tramiteRegistroDto.getNumColegiado(), tramiteRegistroDto.getIdContrato());
					if (StringUtils.isNotBlank(colegiado.getUsuarioCorpme()) && StringUtils.isNotBlank(colegiado.getPasswordCorpme())) {
						identificacionCorpme = colegiado.getUsuarioCorpme() + "|" + colegiado.getPasswordCorpme();
					}
				}

				tramiteRegistroDto.setIdentificacionCorpme(identificacionCorpme);

				try {
					ResultBean resultBean = null;

					TramiteRegRbmDto tramiteRegRbmDto = null;

					if (TipoTramiteRegistro.MODELO_7.getValorEnum().equalsIgnoreCase(tramiteRegistroDto.getTipoTramite())) {
						tramiteRegRbmDto = (TramiteRegRbmDto) getTramiteRegRbm(idRegistro.trim()).getObj();
						tramiteRegRbmDto.setIdentificacionCorpme(identificacionCorpme);
						resultBean = buildRm.construirRbmXMLFinanciacion(tramiteRegRbmDto);
					} else if (TipoTramiteRegistro.MODELO_8.getValorEnum().equalsIgnoreCase(tramiteRegistroDto.getTipoTramite())) {
						tramiteRegRbmDto = (TramiteRegRbmDto) getTramiteRegRbm(idRegistro.trim()).getObj();
						tramiteRegRbmDto.setIdentificacionCorpme(identificacionCorpme);
						resultBean = buildRm.construirRbmXMLLeasing(tramiteRegRbmDto);
					} else if (TipoTramiteRegistro.MODELO_9.getValorEnum().equalsIgnoreCase(tramiteRegistroDto.getTipoTramite())) {
						tramiteRegRbmDto = (TramiteRegRbmDto) getTramiteRegRbm(idRegistro.trim()).getObj();
						tramiteRegRbmDto.setIdentificacionCorpme(identificacionCorpme);
						resultBean = buildRm.construirRbmXMLRenting(tramiteRegRbmDto);
					} else if (TipoTramiteRegistro.MODELO_10.getValorEnum().equalsIgnoreCase(tramiteRegistroDto.getTipoTramite())) {
						tramiteRegRbmDto = (TramiteRegRbmDto) getTramiteRegRbmCancelDesist(idRegistro.trim()).getObj();
						tramiteRegRbmDto.setIdentificacionCorpme(identificacionCorpme);
						resultBean = buildRm.construirRbmXMLCancelacion(tramiteRegRbmDto);
					} else if (TipoTramiteRegistro.MODELO_13.getValorEnum().equalsIgnoreCase(tramiteRegistroDto.getTipoTramite())) {
						tramiteRegRbmDto = (TramiteRegRbmDto) getTramiteRegRbmCancelDesist(idRegistro.trim()).getObj();
						tramiteRegRbmDto.setIdentificacionCorpme(identificacionCorpme);
						resultBean = buildRm.construirRbmXMLDesistimiento(tramiteRegRbmDto);
					}

					if (null != resultBean && resultBean.getError()) {
						resultRegistro.setMensaje("La cadena xml del rm no es válida según el esquema");
						resultRegistro.setError(Boolean.TRUE);
						if (resultBean.getListaMensajes() != null && !resultBean.getListaMensajes().isEmpty()) {
							resultRegistro.setValidaciones(resultBean.getListaMensajes());
						}
						return resultRegistro;
					}

					if (!tramiteRegistroDto.getTipoTramite().equalsIgnoreCase(TipoTramiteRegistro.MODELO_7.getValorEnum()) && !tramiteRegistroDto.getTipoTramite().equalsIgnoreCase(
							TipoTramiteRegistro.MODELO_8.getValorEnum()) && !tramiteRegistroDto.getTipoTramite().equalsIgnoreCase(TipoTramiteRegistro.MODELO_9.getValorEnum()) && !tramiteRegistroDto
									.getTipoTramite().equalsIgnoreCase(TipoTramiteRegistro.MODELO_10.getValorEnum()) && !tramiteRegistroDto.getTipoTramite().equalsIgnoreCase(
											TipoTramiteRegistro.MODELO_13.getValorEnum())) {

						result = buildDpr.construirDPR(tramiteRegistroDto, alias);
					} else {
						result = buildDpr.construirDPRRegistro(tramiteRegRbmDto, alias);
					}

				} catch (Throwable e) {
					resultRegistro.setMensaje("Error al construir DPR del trámite " + idRegistro + ".");
					resultRegistro.setError(Boolean.TRUE);
					log.error("Error al construir DPR del trámite " + idRegistro + ", error: ", e);
					return resultRegistro;
				}

				if (result.getError()) {
					resultRegistro.setError(Boolean.TRUE);
					resultRegistro.setMensaje(result.getMensaje());
					return resultRegistro;
				}

				String nombreFichero = (String) result.getAttachment("nombreFichero");

				result = servicioCola.crearSolicitud(ProcesosEnum.WREG.getNombreEnum(), nombreFichero, gestorPropiedades.valorPropertie(NOMBRE_HOST_SOLICITUD), tramiteRegistroDto.getTipoTramite(),
						tramiteRegistroDto.getIdTramiteRegistro().toString(), tramiteRegistroDto.getIdUsuario(), null, tramiteRegistroDto.getIdContrato());

				if (result != null && !result.getError()) {
					resultRegistro.setMensaje("La solicitud de envío del trámite " + idRegistro + " se ha creado correctamente. Recibirá notificación del envío.");

					ResultBean respuestaBean = servicioTramiteRegistro.cambiarEstado(true, tramiteRegistroDto.getIdTramiteRegistro(), tramiteRegistroDto.getEstado(), new BigDecimal(
							EstadoTramiteRegistro.Pendiente_Envio.getValorEnum()), true, TextoNotificacion.Cambio_Estado.getNombreEnum(), tramiteRegistroDto.getIdUsuario());
					if (respuestaBean != null && respuestaBean.getError()) {
						log.error(Claves.CLAVE_LOG_REGISTRADORES_MODELO1_ERROR + "Ha ocurrido el siguiente error actualizando a 'Pendiente_Envio' el tramite con identificador: " + tramiteRegistroDto
								.getIdTramiteRegistro() + " : " + respuestaBean.getMensaje());
					} else {
						tramiteRegistroDto.setEstado(new BigDecimal(EstadoTramiteRegistro.Pendiente_Envio.getValorEnum()));
					}
				} else if (null != result) {
					resultRegistro.setMensaje(result.getMensaje());
					log.error(Claves.CLAVE_LOG_REGISTRADORES_MODELO1_ERROR + " construirDpr. " + result.getMensaje());
				}
			} catch (Exception e) {
				String mensaje = e.getMessage();
				if (e instanceof UnmarshalException) {
					resultRegistro.setMensaje("El xml generado no es válido según el esquema.");
				} else {
					if (mensaje != null) {
						resultRegistro.setMensaje(mensaje);
					} else {
						resultRegistro.setMensaje(e.toString());
					}
				}
			} catch (OegamExcepcion e) {
				resultRegistro.setMensaje(e.getMensajeError1());
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}

		}

		return resultRegistro;
	}

	@Override
	@Transactional
	public ResultRegistro construirDprFacturaEscritura(TramiteRegistroDto tramiteRegistroDto, String alias) {

		ResultRegistro resultRegistro = new ResultRegistro();
		String identificacionCorpme = null;
		ResultBean result;

		if (tramiteRegistroDto.getEstado() == null) {
			resultRegistro.setError(Boolean.TRUE);
			resultRegistro.setMensaje("No se ha podido recuperar el trámite: " + tramiteRegistroDto.getIdTramiteRegistro());
		} else if (tramiteRegistroDto.getEstado().equals(new BigDecimal(EstadoTramiteRegistro.Pendiente_Envio.getValorEnum()))) {
			resultRegistro.setError(Boolean.TRUE);
			resultRegistro.setMensaje("El trámite: " + tramiteRegistroDto.getIdTramiteRegistro() + " ya se había enviado Dpr.");
		} else if (!UtilesRegistradores.permitidoEnvio(tramiteRegistroDto.getEstado())) {
			resultRegistro.setError(Boolean.TRUE);
			resultRegistro.setMensaje("El estado actual del trámite  " + tramiteRegistroDto.getIdTramiteRegistro() + " no permite el envío del mismo.");
		} else {

			try {

				PersonaDto colegiado = servicioPersona.obtenerColegiadoCompleto(tramiteRegistroDto.getNumColegiado(), tramiteRegistroDto.getIdContrato());

				if (StringUtils.isNotBlank(colegiado.getUsuarioCorpme()) && StringUtils.isNotBlank(colegiado.getPasswordCorpme())) {
					identificacionCorpme = colegiado.getUsuarioCorpme() + "|" + colegiado.getPasswordCorpme();
				}

				tramiteRegistroDto.setIdentificacionCorpme(identificacionCorpme);

				try {

					result = buildDpr.construirDPR(tramiteRegistroDto, alias);

				} catch (Throwable e) {
					resultRegistro.setError(Boolean.TRUE);
					resultRegistro.setMensaje("Error al construir DPR del trámite " + tramiteRegistroDto.getIdTramiteRegistro() + ".");
					log.error("Error al construir DPR del trámite " + tramiteRegistroDto.getIdTramiteRegistro() + ", error: ", e);
					return resultRegistro;
				}

				if (result.getError()) {
					resultRegistro.setMensaje(result.getMensaje());
					return resultRegistro;
				}

				String nombreFichero = (String) result.getAttachment("nombreFichero");

				result = servicioCola.crearSolicitud(ProcesosEnum.WREG.getNombreEnum(), nombreFichero, gestorPropiedades.valorPropertie(NOMBRE_HOST_SOLICITUD), tramiteRegistroDto.getTipoTramite(),
						tramiteRegistroDto.getIdTramiteRegistro().toString(), tramiteRegistroDto.getIdUsuario(), null, tramiteRegistroDto.getIdContrato());

				if (result != null && !result.getError()) {
					resultRegistro.setMensaje("La solicitud de envío del justificante de pago del trámite " + tramiteRegistroDto.getIdTramiteRegistro()
							+ " se ha creado correctamente. Recibirá notificación del envío.");

					ResultBean respuestaBean = servicioTramiteRegistro.cambiarEstado(true, tramiteRegistroDto.getIdTramiteRegistro(), tramiteRegistroDto.getEstado(), new BigDecimal(
							EstadoTramiteRegistro.Pendiente_Envio.getValorEnum()), true, TextoNotificacion.Cambio_Estado.getNombreEnum(), tramiteRegistroDto.getIdUsuario());
					if (respuestaBean != null && respuestaBean.getError()) {
						log.error(Claves.CLAVE_LOG_REGISTRADORES_MODELO1_ERROR + "Ha ocurrido el siguiente error actualizando a 'Pendiente_Envio' el tramite con identificador: " + tramiteRegistroDto
								.getIdTramiteRegistro() + " : " + respuestaBean.getMensaje());
					} else {
						tramiteRegistroDto.setEstado(new BigDecimal(EstadoTramiteRegistro.Pendiente_Envio.getValorEnum()));
					}
				} else if (null != result) {
					resultRegistro.setMensaje(result.getMensaje());
					log.error(Claves.CLAVE_LOG_REGISTRADORES_MODELO1_ERROR + " construirDpr. " + result.getMensaje());
				}
			} catch (Exception e) {
				String mensaje = e.getMessage();
				if (e instanceof UnmarshalException) {
					resultRegistro.setMensaje("El xml generado no es válido según el esquema.");
				} else {
					if (mensaje != null) {
						resultRegistro.setMensaje(mensaje);
					} else {
						resultRegistro.setMensaje(e.toString());
					}
				}
			} catch (OegamExcepcion e) {
				resultRegistro.setMensaje(e.getMensajeError1());
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}

		}

		return resultRegistro;
	}

	@Override
	public ResultRegistro validarContrato(TramiteRegRbmDto tramiteRegRbmDto) {
		ResultRegistro resultado = new ResultRegistro();
		if (tramiteRegRbmDto.getTipoTramite().equalsIgnoreCase(TipoTramiteRegistro.MODELO_7.getValorEnum())) {
			resultado = validarContratoFinanciacion(tramiteRegRbmDto);
		} else if (tramiteRegRbmDto.getTipoTramite().equalsIgnoreCase(TipoTramiteRegistro.MODELO_8.getValorEnum())) {
			resultado = validarContratoLeasing(tramiteRegRbmDto);
		} else if (tramiteRegRbmDto.getTipoTramite().equalsIgnoreCase(TipoTramiteRegistro.MODELO_9.getValorEnum())) {
			resultado = validarContratoRenting(tramiteRegRbmDto);
		} else if (tramiteRegRbmDto.getTipoTramite().equalsIgnoreCase(TipoTramiteRegistro.MODELO_10.getValorEnum())) {
			resultado = validarContratoCancelacion(tramiteRegRbmDto);
		} else if (tramiteRegRbmDto.getTipoTramite().equalsIgnoreCase(TipoTramiteRegistro.MODELO_13.getValorEnum())) {
			resultado = validarContratoDesistimiento(tramiteRegRbmDto);
		}

		if (null != tramiteRegRbmDto.getPresentador() && StringUtils.isBlank(tramiteRegRbmDto.getPresentador().getUsuarioCorpme()) || StringUtils.isBlank(tramiteRegRbmDto.getPresentador()
				.getPasswordCorpme())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("Debe introducir la identificación Corpme en el apartado Contratos.");
		}
		return resultado;
	}

	private ResultRegistro validarContratoFinanciacion(TramiteRegRbmDto tramiteRegRbmDto) {
		ResultRegistro resultado = new ResultRegistro();

		validarDatosGenerales(tramiteRegRbmDto, resultado);

		// Datos financieros
		if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getImpPrecioCompraventa())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El precio compraventa del bloque Datos financieros es obligatorio.");
		} else if (!UtilesValidaciones.validarImporte(tramiteRegRbmDto.getDatosFinanciero().getImpPrecioCompraventa())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El precio compraventa del bloque Datos financieros no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getImpDesembolsoIni())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El desembolso inicial del bloque Datos financieros es obligatorio.");
		} else if (!UtilesValidaciones.validarImporte(tramiteRegRbmDto.getDatosFinanciero().getImpDesembolsoIni())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El desembolso inicial del bloque Datos financieros no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getImpCapitalPrestamo())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El importe del capital del préstamo del bloque Datos financieros es obligatorio.");
		} else if (!UtilesValidaciones.validarImporte(tramiteRegRbmDto.getDatosFinanciero().getImpCapitalPrestamo())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El importe del capital del préstamo del bloque Datos financieros no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getImpTotalPrestamo())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El importe total del préstamo del bloque Datos financieros es obligatorio.");
		} else if (!UtilesValidaciones.validarImporte(tramiteRegRbmDto.getDatosFinanciero().getImpTotalPrestamo())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El importe total del préstamo del bloque Datos financieros no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getImtIntereses())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El importe intereses del préstamo del bloque Datos financieros es obligatorio.");
		} else if (!UtilesValidaciones.validarPorcentaje(String.valueOf(tramiteRegRbmDto.getDatosFinanciero().getImtIntereses()))) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El importe intereses del préstamo del bloque Datos financieros no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getTipoDeudor())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El tipo deudor del bloque Datos financieros es obligatorio.");
		} else if (!UtilesValidaciones.validarPorcentaje(String.valueOf(tramiteRegRbmDto.getDatosFinanciero().getTipoDeudor()))) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El tipo deudor del bloque Datos financieros no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getTipoInteresNominalAnual())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El tipo intereses nominal anual del bloque Datos financieros es obligatorio.");
		} else if (!UtilesValidaciones.validarPorcentaje(String.valueOf(tramiteRegRbmDto.getDatosFinanciero().getTipoInteresNominalAnual()))) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El tipo intereses nominal anual del bloque Datos financieros no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getInteresesDemora())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El interés de demora del bloque Datos financieros es obligatorio.");
		} else if (!UtilesValidaciones.validarPorcentaje(String.valueOf(tramiteRegRbmDto.getDatosFinanciero().getInteresesDemora()))) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El interés de demora del bloque Datos financieros no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getNumeroMeses())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El número de meses de intereses por aplazamiento del bloque Datos financieros es obligatorio.");
		} else if (!UtilesValidaciones.validarNumero(tramiteRegRbmDto.getDatosFinanciero().getNumeroMeses())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El número de meses de intereses por aplazamiento del bloque Datos financieros no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getFecUltVencimientoDatFinancieros())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("La fecha último vencimiento del bloque Datos financieros es obligatoria.");
		} else if (!UtilesValidaciones.validarFecha(tramiteRegRbmDto.getDatosFinanciero().getFecUltVencimientoDatFinancieros())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("La fecha último vencimiento del bloque Datos financieros no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarImporte(tramiteRegRbmDto.getDatosFinanciero().getImpImpuestoMatriculacion())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El impuesto matriculación del bloque Datos financieros no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarImporte(tramiteRegRbmDto.getDatosFinanciero().getImpCancelacionRdLd())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El importe cancelación del bloque Datos financieros no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getImpTotalAdeudado())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El importe total + coste total del bloque Datos financieros es obligatoria.");
		} else if (!UtilesValidaciones.validarImporte(tramiteRegRbmDto.getDatosFinanciero().getImpTotalAdeudado())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El importe total + coste total del bloque Datos financieros no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getImpDerechoDes())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El importe derecho desistimiento del bloque Datos financieros es obligatoria.");
		} else if (!UtilesValidaciones.validarImporte(tramiteRegRbmDto.getDatosFinanciero().getImpDerechoDes())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El importe derecho desistimiento del bloque Datos financieros no tiene el formato correcto.");
		}

		// Se valida el bloque variabilidad tipo interés
		validarVariabilidadTipoInteres(tramiteRegRbmDto, resultado);

		// Se valida el bloque de Domicilio de pago
		if (StringUtils.isBlank(tramiteRegRbmDto.getDatosFinanciero().getDomicilioEntidadPago())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El domicilio entidad del bloque Domicilio de pago es obligatorio.");
		}

		if (StringUtils.isBlank(tramiteRegRbmDto.getDatosFinanciero().getCodProvinciaPago())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El código INE provincia del bloque Domicilio de pago es obligatorio.");
		}

		if (StringUtils.isBlank(tramiteRegRbmDto.getDatosFinanciero().getCodMunicipioPago())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El código INE municipio del bloque Domicilio de pago es obligatorio.");
		}

		if (StringUtils.isBlank(tramiteRegRbmDto.getDatosFinanciero().getEntidadPago())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("La entidad de pago del bloque Domicilio de pago es obligatorio.");
		} else if (!UtilesValidaciones.validarNumero(tramiteRegRbmDto.getDatosFinanciero().getEntidadPago())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("La entidad de pago del bloque Domicilio de pago no tiene el formato correcto.");
		}

		if (StringUtils.isBlank(tramiteRegRbmDto.getDatosFinanciero().getCccPago())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El número cuenta corriente del bloque Domicilio de pago es obligatorio.");
		}

		// Este campo solo se muestra en los contratos financiación
		if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getPorcentajeTaePago())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El porcentaje TAE del bloque Domicilio de pago es obligatorio.");
		} else if (!UtilesValidaciones.validarPorcentaje(String.valueOf(tramiteRegRbmDto.getDatosFinanciero().getPorcentajeTaePago()))) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El porcentaje TAE del bloque Domicilio de pago no tiene el formato correcto.");
		}

		// Se valida la obligatoriedad del tipo de cesión
		if (StringUtils.isBlank(tramiteRegRbmDto.getTipoCesionTercero())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El tipo cesión del bloque Datos de cesión es obligatorio.");
		}

		// Listas
		if (null == tramiteRegRbmDto.getDatoFirmas() || tramiteRegRbmDto.getDatoFirmas().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El contrato debe tener al menos una firma.");
		}
		if (null == tramiteRegRbmDto.getDatosFinanciero().getReconocimientoDeudas() || tramiteRegRbmDto.getDatosFinanciero().getReconocimientoDeudas().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El contrato debe tener al menos un reconocimiento de deuda.");
		}
		if (null == tramiteRegRbmDto.getDatosFinanciero().getCuadrosAmortizacion() || tramiteRegRbmDto.getDatosFinanciero().getCuadrosAmortizacion().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El contrato debe tener al menos un cuadro de amortización.");
		}
		if (null == tramiteRegRbmDto.getCompradores() || tramiteRegRbmDto.getCompradores().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El contrato debe tener al menos un comprador.");
		}
		if (null == tramiteRegRbmDto.getFinanciadores() || tramiteRegRbmDto.getFinanciadores().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El contrato debe tener al menos un financiador.");
		}
		if (null == tramiteRegRbmDto.getPropiedades() || tramiteRegRbmDto.getPropiedades().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El contrato debe tener al menos un objeto financiado.");
		}
		return resultado;
	}

	private ResultRegistro validarContratoLeasing(TramiteRegRbmDto tramiteRegRbmDto) {
		ResultRegistro resultado = new ResultRegistro();

		validarDatosGenerales(tramiteRegRbmDto, resultado);

		// Datos financieros
		if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getImpNetoMat())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El importe neto de los bienes del bloque Datos financieros es obligatorio.");
		} else if (!UtilesValidaciones.validarImporte(tramiteRegRbmDto.getDatosFinanciero().getImpNetoMat())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El importe neto de los bienes del bloque Datos financieros no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getImpTotalArrend())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El importe total del arrendamiento del bloque Datos financieros es obligatorio.");
		} else if (!UtilesValidaciones.validarImporte(tramiteRegRbmDto.getDatosFinanciero().getImpTotalArrend())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El importe total del arrendamiento del bloque Datos financieros no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getImpOpcionCompra())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El importe opción de compra del bloque Datos financieros es obligatorio.");
		} else if (!UtilesValidaciones.validarImporte(tramiteRegRbmDto.getDatosFinanciero().getImpOpcionCompra())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El importe opción de compra del bloque Datos financieros no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getImpEntregaCuenta())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El importe entrega a cuenta del bloque Datos financieros es obligatorio.");
		} else if (!UtilesValidaciones.validarImporte(tramiteRegRbmDto.getDatosFinanciero().getImpEntregaCuenta())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El importe entrega a cuenta del bloque Datos financieros no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getImpFianza())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El importe fianza del bloque Datos financieros es obligatorio.");
		} else if (!UtilesValidaciones.validarImporte(tramiteRegRbmDto.getDatosFinanciero().getImpFianza())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El importe fianza del bloque Datos financieros no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getTipoInteresNominalAnual())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El tipo intereses nominal anual del bloque Datos financieros es obligatorio.");
		} else if (!UtilesValidaciones.validarPorcentaje(String.valueOf(tramiteRegRbmDto.getDatosFinanciero().getTipoInteresNominalAnual()))) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El tipo intereses nominal anual del bloque Datos financieros no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getTasaAnualEquiv())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("La tasa anual de equivalencia del bloque Datos financieros es obligatoria.");
		} else if (!UtilesValidaciones.validarPorcentaje(String.valueOf(tramiteRegRbmDto.getDatosFinanciero().getTasaAnualEquiv()))) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("La tasa anual de equivalencia del bloque Datos financieros no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getNumeroMeses())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El número de meses de intereses por aplazamiento del bloque Datos financieros es obligatorio.");
		} else if (!UtilesValidaciones.validarNumero(tramiteRegRbmDto.getDatosFinanciero().getNumeroMeses())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El número de meses de intereses por aplazamiento del bloque Datos financieros no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getFecUltVencimientoDatFinancieros())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("La fecha último vencimiento del bloque Datos financieros es obligatoria.");
		} else if (!UtilesValidaciones.validarFecha(tramiteRegRbmDto.getDatosFinanciero().getFecUltVencimientoDatFinancieros())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("La fecha último vencimiento del bloque Datos financieros no tiene el formato correcto.");
		}

		// Se valida el bloque variabilidad tipo interés
		validarVariabilidadTipoInteres(tramiteRegRbmDto, resultado);

		// Se valida el bloque de Domicilio de pago
		validarDomicilioPago(tramiteRegRbmDto, resultado);

		// Si se informa algún campo del bloque Financiación impuesto anticipado se deben informar todos los campos
		if (UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getImpImpuesto()) || UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero()
				.getTipoInteresNominalAnualFi()) || UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getTasaAnualEquivFi()) || (null != tramiteRegRbmDto
						.getDatosFinanciero().getCuadrosAmortizacionFI() && !tramiteRegRbmDto.getDatosFinanciero().getCuadrosAmortizacionFI().isEmpty())) {

			if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getImpImpuesto())) {
				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("El importe impuesto del bloque Financiación impuesto anticipado es obligatorio.");
			} else if (!UtilesValidaciones.validarImporte(tramiteRegRbmDto.getDatosFinanciero().getImpImpuesto())) {
				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("El importe impuesto del bloque Financiación impuesto anticipado no tiene el formato correcto.");
			}

			if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getTipoInteresNominalAnualFi())) {
				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("El tipo nominal F.I. del bloque Financiación impuesto anticipado es obligatorio.");
			} else if (!UtilesValidaciones.validarPorcentaje(String.valueOf(tramiteRegRbmDto.getDatosFinanciero().getTipoInteresNominalAnualFi()))) {
				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("El tipo nominal F.I. del bloque Financiación impuesto anticipado no tiene el formato correcto.");
			}

			if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getTasaAnualEquivFi())) {
				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("El TAE F.I. del bloque Financiación impuesto anticipado es obligatorio.");
			} else if (!UtilesValidaciones.validarPorcentaje(String.valueOf(tramiteRegRbmDto.getDatosFinanciero().getTasaAnualEquivFi()))) {
				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("El TAE F.I. del bloque Financiación impuesto anticipado no tiene el formato correcto.");
			}

			if (null == tramiteRegRbmDto.getDatosFinanciero().getCuadrosAmortizacionFI() || tramiteRegRbmDto.getDatosFinanciero().getCuadrosAmortizacionFI().isEmpty()) {
				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("Debe añadir algún cuadro F.I. en el bloque Financiación impuesto anticipado.");
			}
		}

		// Si se informa algún campo del bloque Financiación Seguro se deben informar todos los campos
		if (UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getImpSeguro()) || UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero()
				.getTipoInteresNominalAnualFs()) || UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getTasaAnualEquivFs()) || (null != tramiteRegRbmDto
						.getDatosFinanciero().getCuadrosAmortizacionFS() && !tramiteRegRbmDto.getDatosFinanciero().getCuadrosAmortizacionFS().isEmpty())) {

			if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getImpSeguro())) {
				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("El importe seguro del bloque Financiación seguro es obligatorio.");
			} else if (!UtilesValidaciones.validarImporte(tramiteRegRbmDto.getDatosFinanciero().getImpSeguro())) {
				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("El importe seguro del bloque Financiación seguro no tiene el formato correcto.");
			}

			if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getTipoInteresNominalAnualFs())) {
				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("El tipo nominal F.S. del bloque Financiación seguro es obligatorio.");
			} else if (!UtilesValidaciones.validarPorcentaje(String.valueOf(tramiteRegRbmDto.getDatosFinanciero().getTipoInteresNominalAnualFs()))) {
				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("El tipo nominal F.S. del bloque Financiación seguro no tiene el formato correcto.");
			}

			if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getTasaAnualEquivFs())) {
				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("El TAE F.S. del bloque Financiación seguro es obligatorio.");
			} else if (!UtilesValidaciones.validarPorcentaje(String.valueOf(tramiteRegRbmDto.getDatosFinanciero().getTasaAnualEquivFs()))) {
				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("El TAE F.S. del bloque Financiación seguro no tiene el formato correcto.");
			}

			if (null == tramiteRegRbmDto.getDatosFinanciero().getCuadrosAmortizacionFS() || tramiteRegRbmDto.getDatosFinanciero().getCuadrosAmortizacionFS().isEmpty()) {
				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("Debe añadir algún cuadro F.S. en el bloque Financiación seguro.");
			}
		}

		// Se valida la obligatoriedad del tipo de cesión
		if (StringUtils.isBlank(tramiteRegRbmDto.getTipoCesionTercero())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El tipo cesión del bloque Datos de cesión es obligatorio.");
		}

		// Listas
		if (null == tramiteRegRbmDto.getArrendatarios() || tramiteRegRbmDto.getArrendatarios().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El contrato debe tener al menos un arrendatario.");
		}
		if (null == tramiteRegRbmDto.getArrendadores() || tramiteRegRbmDto.getArrendadores().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El contrato debe tener al menos un arrendador.");
		}
		if (null == tramiteRegRbmDto.getPropiedades() || tramiteRegRbmDto.getPropiedades().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El contrato debe tener al menos un objeto financiado.");
		}
		if (null == tramiteRegRbmDto.getDatosFinanciero().getReconocimientoDeudas() || tramiteRegRbmDto.getDatosFinanciero().getReconocimientoDeudas().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El contrato debe tener al menos un reconocimiento de deuda.");
		}
		if (null == tramiteRegRbmDto.getDatosFinanciero().getCuadrosAmortizacion() || tramiteRegRbmDto.getDatosFinanciero().getCuadrosAmortizacion().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El contrato debe tener al menos un cuadro de amortización.");
		}
		if (null == tramiteRegRbmDto.getDatoFirmas() || tramiteRegRbmDto.getDatoFirmas().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El contrato debe tener al menos una firma.");
		}

		return resultado;
	}

	private ResultRegistro validarContratoRenting(TramiteRegRbmDto tramiteRegRbmDto) {
		ResultRegistro resultado = new ResultRegistro();

		validarDatosGenerales(tramiteRegRbmDto, resultado);

		// Datos financieros
		if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getImpTotalArrend())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El importe total del arrendamiento del bloque Datos financieros es obligatorio.");
		} else if (!UtilesValidaciones.validarImporte(tramiteRegRbmDto.getDatosFinanciero().getImpTotalArrend())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El importe total del arrendamiento del bloque Datos financieros no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getImpOpcionCompra())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El importe opción de compra del bloque Datos financieros es obligatorio.");
		} else if (!UtilesValidaciones.validarImporte(tramiteRegRbmDto.getDatosFinanciero().getImpOpcionCompra())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El importe opción de compra del bloque Datos financieros no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getImpEntregaCuenta())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El importe entrega a cuenta del bloque Datos financieros es obligatorio.");
		} else if (!UtilesValidaciones.validarImporte(tramiteRegRbmDto.getDatosFinanciero().getImpEntregaCuenta())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El importe entrega a cuenta del bloque Datos financieros no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getImpFianza())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El importe fianza del bloque Datos financieros es obligatorio.");
		} else if (!UtilesValidaciones.validarImporte(tramiteRegRbmDto.getDatosFinanciero().getImpFianza())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El importe fianza del bloque Datos financieros no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getNumeroMeses())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El número de meses de intereses por aplazamiento del bloque Datos financieros es obligatorio.");
		} else if (!UtilesValidaciones.validarNumero(tramiteRegRbmDto.getDatosFinanciero().getNumeroMeses())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El número de meses de intereses por aplazamiento del bloque Datos financieros no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getFecUltVencimientoDatFinancieros())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("La fecha último vencimiento del bloque Datos financieros es obligatoria.");
		} else if (!UtilesValidaciones.validarFecha(tramiteRegRbmDto.getDatosFinanciero().getFecUltVencimientoDatFinancieros())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("La fecha último vencimiento del bloque Datos financieros no tiene el formato correcto.");
		}

		// Se valida el bloque variabilidad tipo interés
		validarVariabilidadTipoInteres(tramiteRegRbmDto, resultado);

		// Se valida el bloque de Domicilio de pago
		validarDomicilioPago(tramiteRegRbmDto, resultado);

		// Se valida la obligatoriedad del tipo de cesión
		if (StringUtils.isBlank(tramiteRegRbmDto.getTipoCesionTercero())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El tipo cesión del bloque Datos de cesión es obligatorio.");
		}

		// Listas
		if (null == tramiteRegRbmDto.getArrendatarios() || tramiteRegRbmDto.getArrendatarios().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El contrato debe tener al menos un arrendatario.");
		}
		if (null == tramiteRegRbmDto.getArrendadores() || tramiteRegRbmDto.getArrendadores().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El contrato debe tener al menos un arrendador.");
		}
		if (null == tramiteRegRbmDto.getPropiedades() || tramiteRegRbmDto.getPropiedades().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El contrato debe tener al menos un objeto financiado.");
		}
		if (null == tramiteRegRbmDto.getDatosFinanciero().getReconocimientoDeudas() || tramiteRegRbmDto.getDatosFinanciero().getReconocimientoDeudas().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El contrato debe tener al menos un reconocimiento de deuda.");
		}
		if (null == tramiteRegRbmDto.getDatoFirmas() || tramiteRegRbmDto.getDatoFirmas().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El contrato debe tener al menos una firma.");
		}

		return resultado;
	}

	private ResultRegistro validarContratoCancelacion(TramiteRegRbmDto tramiteRegRbmDto) {
		ResultRegistro resultado = new ResultRegistro();

		if (StringUtils.isBlank(tramiteRegRbmDto.getModeloContrato())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El modelo del bloque Datos cancelación es obligatorio.");
		}

		if (null != tramiteRegRbmDto.getImporteComisionCancelacion() && !tramiteRegRbmDto.getImporteComisionCancelacion().equals(BigDecimal.ZERO) && !UtilesValidaciones.validarImporte(tramiteRegRbmDto
				.getImporteComisionCancelacion())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El importe comisión cancelación del bloque Datos cancelación no tiene el formato correcto.");
		}

		if (StringUtils.isBlank(tramiteRegRbmDto.getSituacionJuridicaCancelacion())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("La situación jurídica del bloque Datos cancelación es obligatoria.");
		}

		if (StringUtils.isBlank(tramiteRegRbmDto.getTipoOperacion())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("Tipo operación del bloque Datos cancelación es obligatorio.");
		}

		if (StringUtils.isBlank(tramiteRegRbmDto.getCausaCancelacion())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("Causas cancelación del bloque Datos cancelación es obligatorio.");
		}

		if (StringUtils.isBlank(tramiteRegRbmDto.getIdRegistro())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El registro del bloque Datos cancelación es obligatorio.");
		}

		if (null == tramiteRegRbmDto.getSolicitante()) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El contrato debe tener una entidad solicitante.");
		}

		if (StringUtils.isNotBlank(tramiteRegRbmDto.getRepresentanteSolicitante().getNif()) && (null == tramiteRegRbmDto.getRepresentanteSolicitante().getNotario() || StringUtils.isBlank(
				tramiteRegRbmDto.getRepresentanteSolicitante().getNotario().getNif()) || null == tramiteRegRbmDto.getRepresentanteSolicitante().getDatRegMercantil() || StringUtils.isBlank(
						tramiteRegRbmDto.getRepresentanteSolicitante().getDatRegMercantil().getCodRegistroMercantil()))) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("Si el solicitante tiene representante debe informar los datos del poder: Datos del Notario y Datos del Registro Mercantil.");

		}

		if (null == tramiteRegRbmDto.getPropiedades() || tramiteRegRbmDto.getPropiedades().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El contrato debe tener al menos un objeto financiado.");
		} else if (null != tramiteRegRbmDto.getPropiedades() && tramiteRegRbmDto.getPropiedades().size() > 1) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El contrato debe tener solo un objeto financiado.");
		}

		// if(null == tramiteRegRbmDto.getEntidadSuscriptora()){
		// resultado.addValidacion("El contrato debe tener una entidad suscriptora.");
		// }

		if (null == tramiteRegRbmDto.getDatoFirmaDto()) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El contrato debe tener una firma.");
		} else {
			ResultRegistro resultadoFirma = servicioDatoFirma.validarDatosFirma(tramiteRegRbmDto.getDatoFirmaDto(), tramiteRegRbmDto.getTipoTramite());
			if (resultadoFirma.isError()) {
				for (String validacion : resultadoFirma.getValidaciones()) {
					resultado.addValidacion(validacion);
				}
				resultado.setError(Boolean.TRUE);
			}
		}

		return resultado;
	}

	private ResultRegistro validarContratoDesistimiento(TramiteRegRbmDto tramiteRegRbmDto) {
		ResultRegistro resultado = new ResultRegistro();

		if (StringUtils.isBlank(tramiteRegRbmDto.getIdRegistro())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El registro mercantil del bloque Datos desistimiento es obligatorio.");
		}

		if (UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getFecEntradaOrigenDesistimim()) && !UtilesValidaciones.validarFecha(tramiteRegRbmDto.getFecEntradaOrigenDesistimim())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("La fecha de entrada del bloque Datos desistimiento es obligatoria.");
		}

		if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getNumEntradaOrigen())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El número de entrada del bloque Datos desistimiento es obligatorio.");
		} else if (!UtilesValidaciones.validarNumero(tramiteRegRbmDto.getNumEntradaOrigen())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El número de entrada del bloque Datos desistimiento no tiene el formato correcto.");
		}

		if (UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getFecPresentacionOrigenDesistimim()) && !UtilesValidaciones.validarFecha(tramiteRegRbmDto
				.getFecPresentacionOrigenDesistimim())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("La fecha de presentación del bloque Datos desistimiento es obligatoria.");
		}

		if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getNumPresentacionOrigen())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El número de presentación del bloque Datos desistimiento es obligatorio.");
		} else if (!UtilesValidaciones.validarNumero(tramiteRegRbmDto.getNumPresentacionOrigen())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El número de presentación del bloque Datos desistimiento no tiene el formato correcto.");
		}

		if (null == tramiteRegRbmDto.getSolicitante()) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El contrato debe tener un solicitante.");
		}

		if (null == tramiteRegRbmDto.getDatoFirmaDto()) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El contrato debe tener una firma.");
		} else {
			ResultRegistro resultadoFirma = servicioDatoFirma.validarDatosFirma(tramiteRegRbmDto.getDatoFirmaDto(), tramiteRegRbmDto.getTipoTramite());
			if (resultadoFirma.isError()) {
				for (String validacion : resultadoFirma.getValidaciones()) {
					resultado.addValidacion(validacion);
				}
				resultado.setError(Boolean.TRUE);
			}
		}

		return resultado;
	}

	private ResultRegistro validarDatosGenerales(TramiteRegRbmDto tramiteRegRbmDto, ResultRegistro resultado) {

		if (!UtilesValidaciones.validarMail(tramiteRegRbmDto.getEmail())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El email del bloque Datos generales no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarMail(tramiteRegRbmDto.getEmail2())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El otro email del bloque Datos generales no tiene el formato correcto.");
		}

		if (StringUtils.isBlank(tramiteRegRbmDto.getNombreAsociacion())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El nombre de asociación del bloque Datos generales es obligatorio.");
		}

		if (StringUtils.isBlank(tramiteRegRbmDto.getNumeroOperacion())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El número de operación del bloque Datos generales es obligatorio.");
		}

		if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getNumeroEjemplar())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El número de ejemplar del bloque Datos generales es obligatorio.");
		} else if (!UtilesValidaciones.validarNumero(tramiteRegRbmDto.getNumeroEjemplar())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El número de ejemplar del bloque Datos generales no tiene el formato correcto.");
		}

		if (StringUtils.isBlank(tramiteRegRbmDto.getModeloContrato())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El modelo de contrato del bloque Datos generales es obligatorio.");
		}

		if (StringUtils.isBlank(tramiteRegRbmDto.getNombreDocumento())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El nombre de documento del bloque Datos generales es obligatorio.");
		}

		if (StringUtils.isBlank(tramiteRegRbmDto.getNumeroImpreso())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("Número impreso R.B.M. del bloque Datos generales es obligatorio.");
		}

		if (StringUtils.isBlank(tramiteRegRbmDto.getAprobadoDgrn())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("La referencia DGRN del bloque Datos generales es obligatoria.");
		}

		if (StringUtils.isBlank(tramiteRegRbmDto.getIdRegistro())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El registro mercantil del bloque Datos generales es obligatorio.");
		}

		if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getFechaDocumento())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("La fecha del documento del bloque Datos generales es obligatoria.");
		} else if (!UtilesValidaciones.validarFecha(tramiteRegRbmDto.getFechaDocumento())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("La fecha del documento del bloque Datos generales no tiene el formato correcto.");
		}

		if (StringUtils.isBlank(tramiteRegRbmDto.getTipoContrato())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El tipo contrato del bloque Datos generales es obligatorio.");
		}

		return resultado;
	}

	private ResultRegistro validarVariabilidadTipoInteres(TramiteRegRbmDto tramiteRegRbmDto, ResultRegistro resultado) {

		// Si se informa algún campo de este bloque el resto son obigatorios
		if (UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getFecRevisionDatFinancieros()) || UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto
				.getDatosFinanciero().getPeriodoRevision()) || StringUtils.isNotBlank(tramiteRegRbmDto.getDatosFinanciero().getTipoInteresRef()) || UtilesValidaciones.validarObligatoriedad(
						tramiteRegRbmDto.getDatosFinanciero().getDiferencialFijo()) || UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getTopeMaxIntNominal())) {

			if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getFecRevision())) {
				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("La fecha de revisión del bloque Variabilidad tipo interés es obligatorio.");
			} else if (!UtilesValidaciones.validarFecha(tramiteRegRbmDto.getDatosFinanciero().getFecRevisionDatFinancieros())) {
				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("La fecha de revisión del bloque Variabilidad tipo interés no tiene el formato correcto.");
			}

			if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getPeriodoRevision())) {
				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("El periodo de revisión del bloque Variabilidad tipo interés es obligatorio.");
			} else if (!UtilesValidaciones.validarNumero(tramiteRegRbmDto.getDatosFinanciero().getPeriodoRevision())) {
				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("El periodo de revisión del bloque Variabilidad tipo interés no tiene el formato correcto.");
			}

			if (StringUtils.isBlank(tramiteRegRbmDto.getDatosFinanciero().getTipoInteresRef())) {
				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("El tipo de interés de referencia del bloque Variabilidad tipo interés es obligatorio.");
			}

			if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getDiferencialFijo())) {
				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("El diferencial fijo del bloque Variabilidad tipo interés es obligatorio.");
			} else if (!UtilesValidaciones.validarPorcentaje(String.valueOf(tramiteRegRbmDto.getDatosFinanciero().getDiferencialFijo()))) {
				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("El diferencial fijo del bloque Variabilidad tipo interés no tiene el formato correcto.");
			}

			if (!UtilesValidaciones.validarObligatoriedad(tramiteRegRbmDto.getDatosFinanciero().getTopeMaxIntNominal())) {
				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("El tope máximo del interés nominal del bloque Variabilidad tipo interés es obligatorio.");
			} else if (!UtilesValidaciones.validarPorcentaje(String.valueOf(tramiteRegRbmDto.getDatosFinanciero().getTopeMaxIntNominal()))) {
				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("El tope máximo del interés nominal del bloque Variabilidad tipo interés no tiene el formato correcto.");
			}
		}

		return resultado;
	}

	private ResultRegistro validarDomicilioPago(TramiteRegRbmDto tramiteRegRbmDto, ResultRegistro resultado) {
		// Si se informa algún campo de este bloque el resto son obigatorios
		if (StringUtils.isNotBlank(tramiteRegRbmDto.getDatosFinanciero().getDomicilioEntidadPago()) || StringUtils.isNotBlank(tramiteRegRbmDto.getDatosFinanciero().getCodProvinciaPago())
				|| StringUtils.isNotBlank(tramiteRegRbmDto.getDatosFinanciero().getCodMunicipioPago()) || StringUtils.isNotBlank(tramiteRegRbmDto.getDatosFinanciero().getEntidadPago()) || StringUtils
						.isNotBlank(tramiteRegRbmDto.getDatosFinanciero().getCccPago())) {

			if (StringUtils.isBlank(tramiteRegRbmDto.getDatosFinanciero().getDomicilioEntidadPago())) {
				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("El domicilio entidad del bloque Domicilio de pago es obligatorio.");
			}

			if (StringUtils.isBlank(tramiteRegRbmDto.getDatosFinanciero().getCodProvinciaPago())) {
				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("El código INE provincia del bloque Domicilio de pago es obligatorio.");
			}

			if (StringUtils.isBlank(tramiteRegRbmDto.getDatosFinanciero().getCodMunicipioPago())) {
				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("El código INE municipio del bloque Domicilio de pago es obligatorio.");
			}

			if (StringUtils.isBlank(tramiteRegRbmDto.getDatosFinanciero().getEntidadPago())) {
				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("La entidad de pago del bloque Domicilio de pago es obligatorio.");
			} else if (!UtilesValidaciones.validarNumero(tramiteRegRbmDto.getDatosFinanciero().getEntidadPago())) {
				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("La entidad de pago del bloque Domicilio de pago no tiene el formato correcto.");
			}

			if (StringUtils.isBlank(tramiteRegRbmDto.getDatosFinanciero().getCccPago())) {
				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("El número cuenta corriente del bloque Domicilio de pago es obligatorio.");
			}
		}

		return resultado;
	}

	private class ComparadorInterviniente implements Comparator<IntervinienteRegistroDto> {

		@Override
		public int compare(IntervinienteRegistroDto o1, IntervinienteRegistroDto o2) {
			return ((Long) o1.getIdInterviniente()).compareTo(o2.getIdInterviniente());
		}

	}

	private class ComparadorFirma implements Comparator<DatoFirmaDto> {

		@Override
		public int compare(DatoFirmaDto o1, DatoFirmaDto o2) {
			return ((Long) o1.getIdDatoFirma()).compareTo(o2.getIdDatoFirma());
		}

	}

	private class ComparadorComisiones implements Comparator<ComisionDto> {

		@Override
		public int compare(ComisionDto o1, ComisionDto o2) {
			return ((Long) o1.getIdComision()).compareTo(o2.getIdComision());
		}

	}

	private class ComparadorOtroImporte implements Comparator<OtroImporteDto> {

		@Override
		public int compare(OtroImporteDto o1, OtroImporteDto o2) {
			return ((Long) o1.getIdOtroImporte()).compareTo(o2.getIdOtroImporte());
		}

	}

	private class ComparadorReconocimientoDeuda implements Comparator<ReconocimientoDeudaDto> {

		@Override
		public int compare(ReconocimientoDeudaDto o1, ReconocimientoDeudaDto o2) {
			return ((Long) o1.getIdReconocimientoDeuda()).compareTo(o2.getIdReconocimientoDeuda());
		}

	}

	private class ComparadorCuadroAmortizacion implements Comparator<CuadroAmortizacionDto> {

		@Override
		public int compare(CuadroAmortizacionDto o1, CuadroAmortizacionDto o2) {
			return ((Long) o1.getIdCuadroAmortizacion()).compareTo(o2.getIdCuadroAmortizacion());
		}

	}

	private class ComparadorClausulas implements Comparator<ClausulaDto> {

		@Override
		public int compare(ClausulaDto o1, ClausulaDto o2) {
			return ((Long) o1.getIdClausula()).compareTo(o2.getIdClausula());
		}

	}

	private class ComparadorFichero implements Comparator<FicheroInfo> {

		@Override
		public int compare(FicheroInfo o1, FicheroInfo o2) {
			return o1.getNombre().compareTo(o2.getNombre());
		}

	}

	private class ComparadorPacto implements Comparator<PactoDto> {

		@Override
		public int compare(PactoDto o1, PactoDto o2) {
			return ((Long) o1.getIdPacto()).compareTo(o2.getIdPacto());
		}

	}

	private class ComparadorPropiedad implements Comparator<PropiedadDto> {

		@Override
		public int compare(PropiedadDto o1, PropiedadDto o2) {
			return ((Long) o1.getIdPropiedad()).compareTo(o2.getIdPropiedad());
		}

	}
	// IMPLEMENTED JVG. 16/05/2018.

	@Override
	@Transactional
	public ResultBean guardarFinanciacionImportacion(CONTRATOFINANCIACION contratofinanciacionVo, BigDecimal usuario, ContratoDto contrato) {
		// TODO Auto-generated method stub
		ResultBean result = new ResultBean();
		boolean primerGuardado = false;

		log.debug("Servicio ServicioTramiteRegRbmImpl.guardarFinanciacionImportacion en Contrato Financiación.");

		try {

			TramiteRegRbmVO tramite = conversor.transform(contratofinanciacionVo, TramiteRegRbmVO.class);

			// Guardamos la tabla Trámite Registro
			// if(null == tramite.getIdTramiteRegistro() || tramite.getIdTramiteRegistro().equals(BigDecimal.ZERO) &&
			// null == tramite.getIdContrato() || tramite.getIdContrato().equals(BigDecimal.ZERO) &&
			// null == tramite.getIdRegistro() ||
			// tramite.getIdRegistro().equals(BigDecimal.ZERO) &&
			// null == tramite.getIdTramiteCorpme() || tramite.getIdTramiteCorpme().equals(BigDecimal.ZERO) &&
			// null == tramite.getNumColegiado() || tramite.getNumColegiado().equals(BigDecimal.ZERO) &&
			// null == tramite.getTipoTramite()
			// || tramite.getTipoTramite().equals(BigDecimal.ZERO)){

			// result.setError(Boolean.TRUE);
			// result.setMensaje("Error en el trámite de Financiación de guardar Financiación.");

			// tramite.getIdContrato();
			tramite.setIdContrato(contrato.getIdContrato());
			tramite.setTipoTramite(TipoTramiteRegistro.MODELO_7.getValorEnum());
			tramite.setIdUsuario(usuario);
			// tramite.setIdTramiteRegistro(tramiteRegistroDao.generarIdTramiteRegistro(contrato.getColegiadoDto().getNumColegiado(), tramite.getTipoTramite()));
			// tramite.setIdTramiteCorpme(tramiteRegistroDao.generarIdTramiteCorpme(contrato.getColegiadoDto().getNumColegiado(),contrato.getCif()));
			tramite.setNumColegiado(contrato.getColegiadoDto().getNumColegiado());
			tramite.setFechaCreacion(new Date());
			tramite.getDatosFinanciero().setFecCreacion(utilesFecha.getTimestampActual());
			tramite.setEstado(new BigDecimal(EstadoTramiteRegistro.Iniciado.getValorEnum()));
			tramite.setIdRegistro(dameCodigoRegistro(contratofinanciacionVo.getRegistro(), TipoRegistro.REGISTRO_RBM.getValorEnum()));
			primerGuardado = true;

			// -------------------------------------------------------------------------------
			// Fechas tabla Datos Financieros (Financiación).

			if (null != tramite.getDatosFinanciero().getFecRevision()) {

				tramite.getDatosFinanciero().setFecUltVencimiento(tramite.getDatosFinanciero().getFecRevision());

			}

			if (null != tramite.getDatosFinanciero().getFecUltVencimiento()) {
				tramite.getDatosFinanciero().setFecUltVencimiento(tramite.getDatosFinanciero().getFecUltVencimiento());
			}

			tramite.setFecModificacion(utilesFecha.getTimestampActual());

			// -----------------------------------------------------------------------------------
			DatosFinancierosVO datosFinancierosVO = tramite.getDatosFinanciero();
			datosFinancierosVO = datosFinancierosDao.guardarOActualizar(datosFinancierosVO);

			// Recogemos el idDatosFinancieros
			tramite.getDatosFinanciero().setIdDatosFinancieros(datosFinancierosVO.getIdDatosFinancieros());

			ResultBean respuestaTramite = guardarTramiteRegRbm(tramite, null, usuario);
			if (respuestaTramite.getError()) {
				result.setError(Boolean.TRUE);
				result.setMensaje("Error al guardar la Financiación.");

			} else {

				result.addAttachment(ID_TRAMITE_REGISTRO, respuestaTramite.getAttachment(ID_TRAMITE_REGISTRO));

				// if(null != contratofinanciacionVo.getObjetosFinanciados().getObjetoFinanciado()){
				//
				// ResultRegistro resultBienesMuebles = guardarBienesMuebles(contratofinanciacionVo.getObjetosFinanciados().getObjetoFinanciado(),tramite.getIdTramiteRegistro());
				//
				// if(resultBienesMuebles.isError()){
				// result.addMensajeALista(resultBienesMuebles.getMensaje());
				// }
				// }
			}

		} catch (Exception e) {
			log.error("Error al guardar contrato de financiación, error: ", e);
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al guardar el contrato de Financiación.");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

		return result;
	}
	// IMPLEMENTED JVG. 16/05/2018.

	private String dameCodigoRegistro(RegistroType registro, String tipo) {

		if (null != registro && null != registro.getCodProvincia() && null != registro.getCodRegistro()) {

			Long result = servicioRegistro.getIdRegistro(registro.getCodRegistro(), registro.getCodProvincia(), tipo);
			if (null != result) {
				return result.toString();
			}
		}
		return null;
	}

	@Transactional
	private ResultBean guardarTramiteRegRbm(TramiteRegRbmVO tramite, BigDecimal estadoAnterior, BigDecimal idUsuario) {
		ResultBean result = new ResultBean();
		try {
			if (tramite.getIdTramiteRegistro() == null) {
				BigDecimal idTramiteRegistro = generarIdTramiteRegistro(tramite.getNumColegiado(), tramite.getTipoTramite());
				if (idTramiteRegistro == null) {
					log.error("No se genera el identificador del tramite");
				}
				tramite.setIdTramiteRegistro(idTramiteRegistro);

				String idTramiteCorpme = generarIdTramiteCorpme(tramite.getNumColegiado(), tramite.getIdContrato());
				if (idTramiteCorpme == null) {
					log.error("No se genera el identificador del tramite para Coprme");
				}
				tramite.setIdTramiteCorpme(idTramiteCorpme);

				Fecha fecha = utilesFecha.getFechaHoraActualLEG();
				tramite.setFechaUltEstado(fecha.getFechaHora());
				tramite.setFechaCreacion(new Date());
				tramite.setIdUsuario(idUsuario);
				tramite.setEstado(new BigDecimal(EstadoTramiteRegistro.Iniciado.getValorEnum()));

				tramiteRegRbmDao.guardar(tramite);

				log.debug("Creación trámite de registro: " + idTramiteRegistro);
				result.addAttachment(ID_TRAMITE_REGISTRO, idTramiteRegistro);
				guardarEvolucionTramite(idTramiteRegistro, BigDecimal.ZERO, tramite.getEstado(), idUsuario);
			} else {
				if (estadoAnterior != null && !tramite.getEstado().equals(estadoAnterior)) {
					tramite.setFechaUltEstado(utilesFecha.getFechaHoraActualLEG().getFechaHora());
					try {
						guardarEvolucionTramite(tramite.getIdTramiteRegistro(), estadoAnterior, tramite.getEstado(), idUsuario);
					} catch (Exception e) {
						log.error("Error al guardar la evolución del trámite de registro: " + tramite.getIdTramiteRegistro() + ". Mensaje: " + e.getMessage());
					}
				}
				log.debug("Actualización trámite: " + tramite.getIdTramiteRegistro());
				// tramiteRegistroDao.actualizar(tramiteRegistro);
				tramiteRegRbmDao.actualizar(tramite);
			}
		} catch (Exception e) {
			log.error("Error al guardar el trámite de registro: " + tramite.getIdTramiteRegistro() + ". Mensaje: " + e.getMessage());
			result.setMensaje(e.getMessage());
			result.setError(Boolean.TRUE);
		}
		return result;
	}

	// IMPLEMENTED JVG. 16/05/2018.

	@Override
	@Transactional
	public BigDecimal generarIdTramiteRegistro(String numColegiado, String tipoTramiteRegistro) throws Exception {
		return tramiteRegistroDao.generarIdTramiteRegistro(numColegiado, tipoTramiteRegistro);
	}

	@Override
	@Transactional
	public String generarIdTramiteCorpme(String numColegiado, BigDecimal idContrato) throws Exception {
		ContratoVO contrato = servicioContrato.getContrato(idContrato);
		if (contrato != null && contrato.getColegio() != null && contrato.getColegio().getCif() != null && !contrato.getColegio().getCif().isEmpty()) {
			return tramiteRegistroDao.generarIdTramiteCorpme(numColegiado, contrato.getColegio().getCif());
		}
		return null;
	}

	@Override
	@Transactional
	public void guardarEvolucionTramite(BigDecimal idTramiteRegistro, BigDecimal antiguoEstado, BigDecimal nuevoEstado, BigDecimal idUsuario) {
		EvolucionTramiteRegistroDto evolucion = new EvolucionTramiteRegistroDto();
		if (antiguoEstado != null) {
			evolucion.setEstadoAnterior(antiguoEstado);
		} else {
			evolucion.setEstadoAnterior(BigDecimal.ZERO);
		}
		evolucion.setEstadoNuevo(nuevoEstado);
		evolucion.setFechaCambio(utilesFecha.getFechaHoraActualLEG());
		evolucion.setIdTramiteRegistro(idTramiteRegistro);

		if (idUsuario != null) {
			UsuarioDto usuario = new UsuarioDto();
			usuario.setIdUsuario(idUsuario);
			evolucion.setUsuario(usuario);
		}

		servicioEvolucionTramiteRegistro.guardar(evolucion);
	}

	@Override
	public ResultBean guardarLeasingImportacion(CONTRATOLEASING contratoleasing, BigDecimal usuario, ContratoDto contrato) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultRegistro guardarTramiteRegRbmLeasing(TramiteRegRbmDto tramiteRegRbmDto, BigDecimal estadoAnterior, BigDecimal idUsuario) {
		// TODO Auto-generated method stub
		return null;
	}

}
