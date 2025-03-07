package trafico.beans;

import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmMatriculacionDto;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto.EeffLiberacionDTO;

import trafico.utiles.enumerados.CausaHechoImponible;
//TODO MPC. Cambio IVTM. Eliminado
//import trafico.utiles.enumerados.EstadoIVTM;
import trafico.utiles.enumerados.NoSujeccionOExencion;
import trafico.utiles.enumerados.Observaciones;
import trafico.utiles.enumerados.ReduccionNoSujeccionOExencion05;
import trafico.utiles.enumerados.TipoLimitacionDisposicionIEDTM;
import trafico.utiles.enumerados.TipoTramiteMatriculacion;
import utilidades.estructuras.Fecha;

public class TramiteTraficoMatriculacionBean {

	private IntervinienteTrafico titularBean;
	private IntervinienteTrafico representanteTitularBean;
	private IntervinienteTrafico conductorHabitualBean;
	private IntervinienteTrafico arrendatarioBean;
	private IntervinienteTrafico representanteConductorHabitualBean;
	private IntervinienteTrafico representanteArrendatarioBean;
	private MarcaBean marcaBean;
	private TipoTramiteBean tipoTramiteBean;
	private TipoVehiculoBean tipoVehiculoBean;
	private TramiteTraficoBean tramiteTraficoBean;
	private UsuarioBean usuarioBean;
	private VehiculoBean vehiculoPreverBean;

	private String valorTipoMatriculacion;

	// Pestaña de impuesto
	private BigDecimal baseImponible576;

	private boolean reduccion;
	private String tipoReduccion576;
	private BigDecimal baseImponibleReducida576;
	private BigDecimal tipoGravamen; 
	private BigDecimal cuota576; 
	private BigDecimal deduccionLineal576;
	private BigDecimal cuotaIngresar576;
	private BigDecimal deducir576;
	private BigDecimal liquidacion576;
	private BigDecimal importe576; 
	private String numDeclaracionComplementaria576;
	private BigDecimal ejercicioDevengo576;
	private CausaHechoImponible causaHechoImponible576;
	private Observaciones observaciones576;

	private String exento576;
	private String nrc576;
	private Fecha fechaPago576; 

	private TipoLimitacionDisposicionIEDTM tipoLimitacionDisposicionIEDTM;
	private ReduccionNoSujeccionOExencion05 idReduccion05;
	private NoSujeccionOExencion idNoSujeccion06;
	private Fecha fechaInicioExencionIEDTM;
	//TODO MPC. Cambio IVTM. Borrado.
//	private String idIvtm;
//	private BigDecimal importeIvtm;
//	private String nrcIvtm;
//	private String aceptacionIvtm;
	private String aceptacionAeat;
	private String otrosSinCodig;
	//TODO MPC. Cambio IVTM. Borrado.
//	private Fecha fechaIVTM;
//	private String pagoEfectivoIVTM;

//	/*Atributos IVTM*/
//	private String exentoIvtm;
//	private String bonMedAmbIvtm;
//	private String digitosIvtm;
//	private String emisorIvtm;
//	private BigDecimal importeAnualIvtm;
//	private String codGestorIvtm;
//	private BigDecimal idPeticionIvtm;
//	private EstadoIVTM estadoIvtm;
//	private String mensajeIvtm;
//	private Fecha fechaReqIvtm;
//	/*Fin Atributos IVTM*/ 
	private BigDecimal idTipoCreacion;
	private String checkJustifIvtm;
private String EntregaFactMatriculacion;

	/* Entidades Financieras */
	private EeffLiberacionDTO eeffLiberacionDTO;

	// Nuevo campo MATW
	private TipoTramiteMatriculacion tipoTramiteMatriculacion;
	//JMC Última Versión de Matw
	private String justificado_IVTM;

	private String respuestaEitv;

	private String permiso;

	private String distintivo;

	//TODO MPC. Cambio IVTM. Añadido el DTO.
	private IvtmMatriculacionDto ivtmMatrDTO;

	public TipoTramiteMatriculacion getTipoTramiteMatriculacion() {
		return tipoTramiteMatriculacion;
	}

	public void setTipoTramiteMatriculacion(String tipoTramiteMatriculacion) {
		this.tipoTramiteMatriculacion = TipoTramiteMatriculacion.convertir(tipoTramiteMatriculacion);
	}

	public String getOtrosSinCodig() {
		return otrosSinCodig;
	}

	public void setOtrosSinCodig(String otrosSinCodig) {
		this.otrosSinCodig = otrosSinCodig;
	}

	public Fecha getFechaInicioExencionIEDTM() {
		return fechaInicioExencionIEDTM;
	}

	public String getNumDeclaracionComplementaria576() {
		return numDeclaracionComplementaria576;
	}

	public void setNumDeclaracionComplementaria576(
			String numDeclaracionComplementaria576) {
		this.numDeclaracionComplementaria576 = numDeclaracionComplementaria576;
	}

	public void setFechaInicioExencionIEDTM(Fecha fechaInicioExencionIEDTM) {
		this.fechaInicioExencionIEDTM = fechaInicioExencionIEDTM;
	}

	public String getAceptacionAeat() {
		return aceptacionAeat;
	}

	public void setAceptacionAeat(String aceptacionAeat) {
		this.aceptacionAeat = aceptacionAeat;
	}

	public TramiteTraficoMatriculacionBean() {
		super();
	}

	public boolean isReduccion() {
		return reduccion;
	}

	public void setReduccion(boolean reduccion) {
		this.reduccion = reduccion;
	}

	public TramiteTraficoMatriculacionBean(boolean inicializar) {

		this();
		this.marcaBean= new MarcaBean();
		this.representanteTitularBean = new IntervinienteTrafico(inicializar);
		this.representanteConductorHabitualBean = new IntervinienteTrafico(inicializar);
		this.representanteArrendatarioBean = new IntervinienteTrafico(inicializar);
		this.conductorHabitualBean = new IntervinienteTrafico(inicializar);
		this.arrendatarioBean= new IntervinienteTrafico(inicializar);
//		this.jefaturaTraficoBean = new JefaturaTrafico(inicializar);
//		this.tasaBean = new Tasa(inicializar);
		this.tipoTramiteBean = new TipoTramiteBean(inicializar);
		this.tipoVehiculoBean = new TipoVehiculoBean (inicializar);
		this.tramiteTraficoBean = new TramiteTraficoBean (inicializar);
		this.usuarioBean = new UsuarioBean(inicializar);
//		this.vehiculoBean = new VehiculoBean (inicializar);
		this.vehiculoPreverBean = new VehiculoBean(inicializar);
		this.titularBean = new IntervinienteTrafico(inicializar);
		//TODO MPC. Cambio IVTM. Borrado por IVTM la siguiente linea
//		this.fechaIVTM = new Fecha();
		//this.ejercicioDevengo576 = new EjercicioDevengo(); //ENUM 
		//this.causaHechoImponible576 = new CausaHechoImponible(); //ENUM
		//this.observaciones576 = new Observaciones(); //ENUM
	}

	public VehiculoBean getVehiculoPreverBean() {
		return vehiculoPreverBean;
	}

	public void setVehiculoPreverBean(VehiculoBean vehiculoPreverBean) {
		this.vehiculoPreverBean = vehiculoPreverBean;
	}

	public IntervinienteTrafico getArrendatarioBean() {
		return arrendatarioBean;
	}

	public void setArrendatarioBean(IntervinienteTrafico arrendatarioBean) {
		this.arrendatarioBean = arrendatarioBean;
	}

	public String getTipoReduccion576() {
		return tipoReduccion576;
	}

	public void setTipoReduccion576(String tipoReduccion576) {
		this.tipoReduccion576 = tipoReduccion576;
	}

	public BigDecimal getEjercicioDevengo576() {
		return ejercicioDevengo576;
	}

	public void setEjercicioDevengo576(Integer ejercicioDevengo576) {
		this.ejercicioDevengo576 = null;
		if(ejercicioDevengo576 != null){
			this.ejercicioDevengo576 = BigDecimal.valueOf(ejercicioDevengo576);
		}
	}

	public CausaHechoImponible getCausaHechoImponible() {
		return causaHechoImponible576;
	}

	public String getExento576() {
		return exento576;
	}

	public Observaciones getObservaciones576() {
		return observaciones576;
	}

	public void setObservaciones576(String observaciones576) {
		this.observaciones576 = Observaciones.convertir(observaciones576);
	}

	public TipoLimitacionDisposicionIEDTM getTipoLimitacionDisposicionIEDTM() {
		return tipoLimitacionDisposicionIEDTM;
	}

	public void setTipoLimitacionDisposicionIEDTM(String tipoLimitacionDisposicionIEDTM){
		this.tipoLimitacionDisposicionIEDTM = TipoLimitacionDisposicionIEDTM.convertir(tipoLimitacionDisposicionIEDTM); 
	}

	public void setExento576(String exento576) {
		this.exento576 = exento576;
	}

	public String getNrc576() {
		return nrc576;
	}

	public void setNrc576(String nrc576) {
		this.nrc576 = nrc576;
	}

	public Fecha getFechaPago576() {
		return fechaPago576;
	}

	public void setFechaPago576(Fecha fechaPago576) {
		this.fechaPago576 = fechaPago576;
	}

	public ReduccionNoSujeccionOExencion05 getIdReduccion05() {
		return idReduccion05;
	}

	public void setIdReduccion05(String idReduccion05) {
		this.idReduccion05 = ReduccionNoSujeccionOExencion05.convertir(idReduccion05);
	}

	public NoSujeccionOExencion getIdNoSujeccion06() {
		return idNoSujeccion06;
	}

	public void setIdNoSujeccion06(String idNoSujeccion) {
		this.idNoSujeccion06 = NoSujeccionOExencion.convertir(idNoSujeccion);
	}

	public MarcaBean getmarcaBean() {
		return marcaBean;
	}
	public void setmarcaBean(MarcaBean marcaBean) {
		this.marcaBean = marcaBean;
	}

	public IntervinienteTrafico getTitularBean() {
		return titularBean;
	}

	public void setTitularBean(IntervinienteTrafico titularBean) {
		this.titularBean = titularBean;
	}

	public IntervinienteTrafico getRepresentanteTitularBean() {
		return representanteTitularBean;
	}

	public void setRepresentanteTitularBean(
			IntervinienteTrafico representanteTitularBean) {
		this.representanteTitularBean = representanteTitularBean;
	}

	public IntervinienteTrafico getConductorHabitualBean() {
		return conductorHabitualBean;
	}

	public void setConductorHabitualBean(IntervinienteTrafico conductorHabitualBean) {
		this.conductorHabitualBean = conductorHabitualBean;
	}

	public MarcaBean getMarcaBean() {
		return marcaBean;
	}

	public void setMarcaBean(MarcaBean marcaBean) {
		this.marcaBean = marcaBean;
	}

	public TipoTramiteBean getTipoTramiteBean() {
		return tipoTramiteBean;
	}

	public void setTipoTramiteBean(TipoTramiteBean tipoTramiteBean) {
		this.tipoTramiteBean = tipoTramiteBean;
	}

	public TipoVehiculoBean getTipoVehiculoBean() {
		return tipoVehiculoBean;
	}

	public void setTipoVehiculoBean(TipoVehiculoBean tipoVehiculoBean) {
		this.tipoVehiculoBean = tipoVehiculoBean;
	}

	public TramiteTraficoBean getTramiteTraficoBean() {
		return tramiteTraficoBean;
	}

	public void setTramiteTraficoBean(TramiteTraficoBean tramiteTraficoBean) {
		this.tramiteTraficoBean = tramiteTraficoBean;
	}

	public UsuarioBean getUsuarioBean() {
		return usuarioBean;
	}

	public void setUsuarioBean(UsuarioBean usuarioBean) {
		this.usuarioBean = usuarioBean;
	}

	public CausaHechoImponible getCausaHechoImponible576() {
		return causaHechoImponible576;
	}

	public void setCausaHechoImponible576(String causaHechoImponible576) {
		this.causaHechoImponible576 = CausaHechoImponible.convertir(causaHechoImponible576);
	}

	public BigDecimal getBaseImponible576() {
		return baseImponible576;
	}

	public String getValorTipoMatriculacion() {
		return valorTipoMatriculacion;
	}
	public void setValorTipoMatriculacion(String valorTipoMatriculacion) {
		this.valorTipoMatriculacion = valorTipoMatriculacion;
	}

	public void setBaseImponible576(BigDecimal baseImponible576) {
		this.baseImponible576 = baseImponible576;
	}

	public BigDecimal getBaseImponibleReducida576() {
		return baseImponibleReducida576;
	}

	public void setBaseImponibleReducida576(BigDecimal baseImponibleReducida576) {
		this.baseImponibleReducida576 = baseImponibleReducida576;
	}

	public BigDecimal getTipoGravamen() {
		return tipoGravamen;
	}

	public void setTipoGravamen(BigDecimal tipoGravamen) {
		this.tipoGravamen = tipoGravamen;
	}

	public BigDecimal getCuota576() {
		return cuota576;
	}

	public void setCuota576(BigDecimal cuota576) {
		this.cuota576 = cuota576;
	}

	public BigDecimal getDeduccionLineal576() {
		return deduccionLineal576;
	}

	public void setDeduccionLineal576(BigDecimal deduccionLineal576) {
		this.deduccionLineal576 = deduccionLineal576;
	}

	public BigDecimal getCuotaIngresar576() {
		return cuotaIngresar576;
	}

	public void setCuotaIngresar576(BigDecimal cuotaIngresar576) {
		this.cuotaIngresar576 = cuotaIngresar576;
	}

	public BigDecimal getDeducir576() {
		return deducir576;
	}

	public void setDeducir576(BigDecimal deducir576) {
		this.deducir576 = deducir576;
	}

	public BigDecimal getLiquidacion576() {
		return liquidacion576;
	}

	public void setLiquidacion576(BigDecimal liquidacion576) {
		this.liquidacion576 = liquidacion576;
	}

	public BigDecimal getImporte576() {
		return importe576;
	}

	public void setImporte576(BigDecimal importe576) {
		this.importe576 = importe576;
	}

	public BigDecimal getIdTipoCreacion() {
		return idTipoCreacion;
	}
	public void setIdTipoCreacion(BigDecimal idTipoCreacion) {
		this.idTipoCreacion = idTipoCreacion;
	}

	public String getEntregaFactMatriculacion() {
		return EntregaFactMatriculacion;
	}

	public void setEntregaFactMatriculacion(String entregaFactMatriculacion) {
		EntregaFactMatriculacion = entregaFactMatriculacion;
	}
	public IntervinienteTrafico getRepresentanteConductorHabitualBean() {
		return representanteConductorHabitualBean;
	}
	public void setRepresentanteConductorHabitualBean(
			IntervinienteTrafico representanteConductorHabitualBean) {
		this.representanteConductorHabitualBean = representanteConductorHabitualBean;
	}
	public IntervinienteTrafico getRepresentanteArrendatarioBean() {
		return representanteArrendatarioBean;
	}
	public void setRepresentanteArrendatarioBean(
			IntervinienteTrafico representanteArrendatarioBean) {
		this.representanteArrendatarioBean = representanteArrendatarioBean;
	}

	public String getJustificado_IVTM() {
		return justificado_IVTM;
	}

	public void setJustificado_IVTM(String justificado_IVTM) {
		this.justificado_IVTM = justificado_IVTM;
	}

	public String getRespuestaEitv() {
		return respuestaEitv;
	}

	public void setRespuestaEitv(String respuestaEitv) {
		this.respuestaEitv = respuestaEitv;
	}

	public EeffLiberacionDTO getEeffLiberacionDTO() {
		return eeffLiberacionDTO;
	}

	public void setEeffLiberacionDTO(EeffLiberacionDTO eeffLiberacionDTO) {
		this.eeffLiberacionDTO = eeffLiberacionDTO;
	}

	// TODO MPC. Cambio IVTM. Añadido get y set del DTO de IVTM.
	public IvtmMatriculacionDto getIvtmMatrDTO() {
		return ivtmMatrDTO;
	}

	public void setIvtmMatrDTO(IvtmMatriculacionDto ivtmMatrDTO) {
		this.ivtmMatrDTO = ivtmMatrDTO;
	}

	public String getPermiso() {
		return permiso;
	}

	public void setPermiso(String permiso) {
		this.permiso = permiso;
	}

	public String getDistintivo() {
		return distintivo;
	}

	public void setDistintivo(String distintivo) {
		this.distintivo = distintivo;
	}

	public String getCheckJustifIvtm() {
		return checkJustifIvtm;
	}

	public void setCheckJustifIvtm(String checkJustifIvtm) {
		this.checkJustifIvtm = checkJustifIvtm;
	}

	// TODO MPC. Cambio IVTM. Hay que borrar los get y set de los campos del IVTM que se han borrado.

}