package trafico.ivtm.servicio;
//TODO MPC. Cambio IVTM. Esta clase es nueva.
import java.math.BigDecimal;
import java.text.ParseException;

import org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmMatriculacionDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import escrituras.beans.ResultBean;
import trafico.beans.TramiteTraficoMatriculacionBean;
import trafico.dto.TramiteTraficoDto;
import trafico.modelo.impl.ModeloTramiteTrafImpl;
import trafico.modelo.ivtm.IVTMModeloMatriculacionInterface;
import trafico.modelo.ivtm.impl.IVTMModeloMatriculacionImpl;
import trafico.utiles.constantes.ConstantesIVTM;
import trafico.utiles.enumerados.EstadoIVTM;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ServicioIVTMMatriculacionImpl implements ServicioIVTMMatriculacionIntf {

	private static ILoggerOegam log = LoggerOegam.getLogger(ServicioIVTMMatriculacionImpl.class);
	private IVTMModeloMatriculacionInterface modeloMatrIVTM = null;
	
	// PAGO IVTM
	private static final String URL_APLICACION = "<urlAplicacion>";
	private static final String ID_EMISOR = "<idEmisor>";
	private static final String IDENTIFICADOR = "<identificador>";
	private static final String GESTOR = "<gestor>";
	private static final String DC = "<dc>";
	private static final String FECHA_CONTROL = "<fechaControl>";
	private static final String IMPORTE = "<importe>";
	private static final String DISTRITO_MUNICIPAL = "<distritoMunicipal>";
	private static final String NIF = "<nif>";
	private static final String ENTORNO = "<entorno>";
	private static final String TIPO_SERVICIO = "<tipoServicio>";
	private static final String APLICACION = "<aplicacion>";

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	public ServicioIVTMMatriculacionImpl() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	private IVTMModeloMatriculacionInterface getModeloMatrIVTM() {
		if (modeloMatrIVTM==null){
			modeloMatrIVTM=new IVTMModeloMatriculacionImpl();
		}
		return modeloMatrIVTM;
	}

	@Override
	public ResultBean puedeGenerarAutoliquidacion(BigDecimal numExpediente) {
		ResultBean rs = new ResultBean();
		EstadoIVTM estadoGuardado = null;
		IvtmMatriculacionDto ivtmGuardado = getModeloMatrIVTM().buscarIvtmNumExp(numExpediente);
		if(ivtmGuardado != null && ivtmGuardado.getEstadoIvtm()!=null){
			estadoGuardado = EstadoIVTM.convertir(ivtmGuardado.getEstadoIvtm().toString());
			if (!getModeloMatrIVTM().estaEnEstadoValidoParaAutoliquidar(estadoGuardado)){
				rs.setError(true);
				rs.addMensajeALista(ConstantesIVTM.TEXTO_IVTM_NO_PERMISO_USO_AUTOLIQUIDACION);
			}
		}
		return rs;
	}

	@Override
	public IvtmMatriculacionDto guardarIVTM(IvtmMatriculacionDto ivtmMatriculacionDto) {
		try {
			return getModeloMatrIVTM().guardar(ivtmMatriculacionDto);
		} catch (OegamExcepcion e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public ResultBean validarTramite(BigDecimal numExpediente) {
		ResultBean rs = new ResultBean();
		TramiteTraficoDto tramiteDTO = new ModeloTramiteTrafImpl().recuperarDtoPorNumExp(numExpediente);
		if (tramiteDTO!=null && tramiteDTO.getTramiteTrafMatr()!=null){
		//Validamos IVTM
			 rs.setListaMensajes(getModeloMatrIVTM().validarAlta(tramiteDTO, getModeloMatrIVTM().buscarIvtmNumExp(numExpediente)));
		} else {
			rs.addMensajeALista(ConstantesIVTM.TEXTO_IVTM_ERROR_RECUPERANDO);
		}
		if (rs.getListaMensajes()!=null && !rs.getListaMensajes().isEmpty()){
			rs.setError(true);
		}
		return rs;
	}
	
	@Override
	public ResultBean validarTramitePago(BigDecimal numExpediente) {
		ResultBean rs = new ResultBean();
		TramiteTraficoDto tramiteDTO = new ModeloTramiteTrafImpl().recuperarDtoPorNumExp(numExpediente);
		if (tramiteDTO != null && tramiteDTO.getTramiteTrafMatr() != null) {
			// Validamos IVTM Pago
			rs.setListaMensajes(getModeloMatrIVTM().validarPago(tramiteDTO, getModeloMatrIVTM().buscarIvtmNumExp(numExpediente)));
		} else {
			rs.addMensajeALista(ConstantesIVTM.TEXTO_IVTM_ERROR_RECUPERANDO);
		}
		if (rs.getListaMensajes() != null && !rs.getListaMensajes().isEmpty()) {
			rs.setError(true);
		}
		return rs;
	}
	
	@Override
	public String crearUrl(boolean desdeConsulta, IvtmMatriculacionDto ivtmMatriculacionDto, TramiteTraficoMatriculacionBean traficoTramiteMatriculacionBean) {
		// Procesar URL añadiendo campos
		String url = gestorPropiedades.valorPropertie("url.pagoIVTM");
		String aplicacion = gestorPropiedades.valorPropertie("ivtm.pago.aplicacion");
		String entorno = gestorPropiedades.valorPropertie("ivtm.pago.entorno");
		String tipoServicio = gestorPropiedades.valorPropertie("ivtm.pago.tipoServicio");

		if (desdeConsulta) {
			url = url.replace(URL_APLICACION, gestorPropiedades.valorPropertie("ivtm.pago.direccionRetornoConsulta") + "?numExpediente=" + traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente());
		} else {
			url = url.replace(URL_APLICACION, gestorPropiedades.valorPropertie("ivtm.pago.direccionRetornoTramite") + "?numExpediente=" +  traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente());
		}

		url = url.replace(ENTORNO, entorno);
		url = url.replace(TIPO_SERVICIO, tipoServicio);
		url = url.replace(APLICACION, aplicacion);

		//TODO: valores por defecto de momento (puede ser que el emisor y gestor sean siempre los mismos datos)
		url = url.replace(ID_EMISOR, "1");
		url = url.replace(IDENTIFICADOR, "41330000040001059");
		url = url.replace(GESTOR, "44410");
		url = url.replace(DC, "39");
		//url = url.replace(ID_EMISOR, ivtmMatriculacionDto.getEmisor());
		//url = url.replace(IDENTIFICADOR, ivtmMatriculacionDto.getNrc());
		//url = url.replace(GESTOR, ivtmMatriculacionDto.getCodGestor());
		//url = url.replace(DC, ivtmMatriculacionDto.getDigitoControl());
		try {
			url = url.replace(FECHA_CONTROL, utilesFecha.formatoFecha("dd-MM-yyyy", ivtmMatriculacionDto.getFechaPago().getDate()));
		} catch (ParseException e) {
			log.error("Error al convertir la fecha de pago: " + e.getMessage());
		}
		
		int importe = ivtmMatriculacionDto.getImporte().intValue();
		url = url.replace(IMPORTE, String.valueOf(importe*100));
		url = url.replace(NIF, traficoTramiteMatriculacionBean.getTitularBean().getPersona().getNif());
		// TODO: 01 = CENTRO
		url = url.replace(DISTRITO_MUNICIPAL, "01");
		//url = url.replace(DISTRITO_MUNICIPAL, traficoTramiteMatriculacionBean.getTitularBean().getPersona().getDireccion().getMunicipio().getIdMunicipio());

		url = url.replace(" ", "%20");
		
		// URL de prueba
		/*if (desdeConsulta) {
			url = "https://test.munimadrid.es:444/SBAE_058_SFSeleccionTipoPago_Internet/tipoPagoTelematico.do?URLRETORNO=http://localhost:8080/oegam2/vueltaPagoConsultaIVTMRespuestaPagoIVTM.action?numExpediente=205628111400007&codigo=0&mensaje=&numOperacion=1&resultadoIntegracion=0&cadena=&firmaRetorno=&IDEMISOR=1&Identificador=41330000040001059&Gestor=44410&DC=39&Fecha_de_Control=20-10-2014&Importe=00000000001230&NIF%2FCIF=11111111H&Distrito_Municipal=01&ENTORNO=2&APLICACION=Movilidad&TIPOSERVICIO=SERVICIO_BASICO";
		} else {
			url = "https://test.munimadrid.es:444/SBAE_058_SFSeleccionTipoPago_Internet/tipoPagoTelematico.do?URLRETORNO=http://localhost:8080/oegam2/vueltaPagoTramiteIVTMRespuestaPagoIVTM.action?numExpediente=205628111400007&codigo=0&mensaje=&numOperacion=1&resultadoIntegracion=0&cadena=&firmaRetorno=&IDEMISOR=1&Identificador=41330000040001059&Gestor=44410&DC=39&Fecha_de_Control=20-10-2014&Importe=00000000001230&NIF%2FCIF=11111111H&Distrito_Municipal=01&ENTORNO=2&APLICACION=Movilidad&TIPOSERVICIO=SERVICIO_BASICO";
		}*/
		return url;
	}

	@Override
	public BigDecimal generarIdPeticion(String numColegiado) {
		return getModeloMatrIVTM().generarIdPeticion(numColegiado);
	}

	@Override
	public boolean solicitarIVTM(BigDecimal numExpediente) {
		return getModeloMatrIVTM().solicitarIVTM(numExpediente, getModeloMatrIVTM().buscarIvtmNumExp(numExpediente));
	}

	@Override
	public IvtmMatriculacionDto recuperarPorNumExp(BigDecimal numExpediente) {
		return getModeloMatrIVTM().recuperarPorNumExp(numExpediente);
	}

	@Override
	public String getIbanTitular(String nifTitular, String numColegiado) {
		return getModeloMatrIVTM().getIbanTitular(nifTitular, numColegiado);
	}
	
}
