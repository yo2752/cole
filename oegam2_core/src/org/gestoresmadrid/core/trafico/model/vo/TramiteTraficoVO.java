package org.gestoresmadrid.core.trafico.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.docPermDistItv.model.vo.DocPermDistItvVO;
import org.gestoresmadrid.core.docbase.model.vo.DocumentoBaseVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.core.ybpdf.model.vo.YbpdfVO;
import org.gestoresmadrid.utilidades.listas.ListsOperator;
import org.hibernate.annotations.DiscriminatorFormula;
import org.hibernate.annotations.Proxy;

/**
 * The persistent class for the TRAMITE_TRAFICO database table.
 */

@NamedQueries({ @NamedQuery(name = TramiteTraficoVO.PRESENTACION_JPT_YBPDF, query = TramiteTraficoVO.PRESENTACION_JPT_YBPDF_QUERY),
		@NamedQuery(name = TramiteTraficoVO.PRESENTACION_JPT, query = TramiteTraficoVO.PRESENTACION_JPT_QUERY),
		@NamedQuery(name = TramiteTraficoVO.REVERTIR_DOC_BASE, query = TramiteTraficoVO.REVERTIR_DOC_BASE_QUERY) })
@Entity
@Table(name = "TRAMITE_TRAFICO")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorFormula("case when TIPO_TRAMITE = 'T2' then 'T7' else TIPO_TRAMITE end")
@Proxy(lazy = false)
public class TramiteTraficoVO implements Serializable {

	private static final long serialVersionUID = 1937580911194583020L;

	public static final String PRESENTACION_JPT_YBPDF = "TramiteTraficoVO.presentacionJptYbpdf";
	static final String PRESENTACION_JPT_YBPDF_QUERY = "UPDATE TramiteTraficoVO t SET t.presentadoJpt='1' where t.ybpdf.idYbpdf = :idDocBase and (t.presentadoJpt<>1 or t.presentadoJpt is null)";

	public static final String PRESENTACION_JPT = "TramiteTraficoVO.presentacionJpt";
	static final String PRESENTACION_JPT_QUERY = "UPDATE TramiteTraficoVO t SET t.presentadoJpt='1' where t.idDocBase = (select d.id from DocumentoBaseVO d where d.docId = :idDocBase) and (t.presentadoJpt<>1 or t.presentadoJpt is null)";

	public static final String REVERTIR_DOC_BASE = "TramiteTraficoVO.revertirDocBase";
	static final String REVERTIR_DOC_BASE_QUERY = "UPDATE TramiteTraficoVO t SET t.ybestado=0, t.idDocBase = null, t.idYbpdf = null, ordenDocBase = null where t.idDocBase in (:listDocBase)";

	@Id
	@Column(name = "NUM_EXPEDIENTE")
	private BigDecimal numExpediente;

	private String anotaciones;

	@Column(name = "CAMBIO_DOMICILIO")
	private String cambioDomicilio;

	private String cem;

	private String cema;

	@Column(name = "ESTADO")
	private BigDecimal estado;

	@Column(name = "EXENTO_CEM")
	private String exentoCem;

	@Column(name = "EXENTO_IEDTM")
	private String exentoIedtm;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_ALTA")
	private Date fechaAlta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_DIGITALIZACION")
	private Date fechaDigitalizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_IEDTM")
	private Date fechaIedtm;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_IMPRESION")
	private Date fechaImpresion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_PRESENTACION")
	private Date fechaPresentacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_PRESENTACION_JPT")
	private Date fechaPresentacionJpt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_ULT_MODIF")
	private Date fechaUltModif;

	@Column(name = "FINANCIERA_IEDTM")
	private String financieraIedtm;

	@Column(name = "ID_PROVINCIA_CEM")
	private String idProvinciaCem;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO")
	private UsuarioVO usuario;

	private String iedtm;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "JEFATURA_PROVINCIAL")
	private JefaturaTraficoVO jefaturaTrafico;

	@OneToMany(mappedBy = "tramiteTrafico")
	private Set<IntervinienteTraficoVO> intervinienteTraficos;

	@Column(name = "MODELO_IEDTM")
	private String modeloIedtm;

	@Column(name = "N_REG_IEDTM")
	private String nRegIedtm;

	@Column(name = "NO_SUJECION_IEDTM")
	private String noSujecionIedtm;

	private String npasos;

	@Column(name = "PRESENTADO_JPT")
	private Short presentadoJpt;

	@Column(name = "REF_PROPIA")
	private String refPropia;

	private String renting;

//	@Column(name = "RES_CHECK_CTIT")
//	private String resCheckCtit;

	private String respuesta;

	@Column(name = "TIPO_TRAMITE")
	private String tipoTramite;

	private BigDecimal ybestado;

	// bi-directional many-to-one association to EvolucionTramiteTrafico
	@OneToMany(mappedBy = "tramiteTrafico")
	private Set<EvolucionTramiteTraficoVO> evolucionTramiteTraficos;

	@Column(name = "ID_TIPO_CREACION")
	private BigDecimal idTipoCreacion;

	// bi-directional many-to-one association to Tasa
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CODIGO_TASA")
	private TasaVO tasa;

	// bi-directional many-to-one association to YbpdfVO
	@OneToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_YBPDF", insertable = false, updatable = false)
	private YbpdfVO ybpdf;

	// bi-directional many-to-one association to YbpdfVO
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tramiteTrafico")
	private Set<EvolucionPresentacionJptVO> evolucionPresentacionJptVO;

	@Column(name = "ID_YBPDF")
	private String idYbpdf;

	@Column(name = "NUM_COLEGIADO", insertable = true, updatable = true)
	private String numColegiado;

	// uni-directional many-to-one association to Vehiculo
	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumns({ @JoinColumn(name = "ID_VEHICULO", referencedColumnName = "ID_VEHICULO") })
	private VehiculoVO vehiculo;

	// bi-directional many-to-one association to Contrato
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CONTRATO")
	private ContratoVO contrato;

	@Column(name = "SIMULTANEA")
	private BigDecimal simultanea;

	@Column(name = "RESPUESTA_GEST")
	private String respuestaGest;

	@Column(name = "RESPUESTA_DIGITALIZACION_GDOC")
	private String respuestaDigitalizacionGdoc;

	@OneToMany
	@JoinColumn(name = "NUM_EXPEDIENTE", insertable = false, updatable = false)
	private Set<TramiteTrafFacturacionVO> tramiteFacturacion;

	/* INICIO Mantis 0011883: Generación Docs Base Oegam - Parte Física (Impresión PDF) */
	// bi-directional many-to-one association to DocBase
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "DOC_BASE", insertable=false, updatable=false)
	private DocumentoBaseVO documentoBase;

	@Column(name = "DOC_BASE")
	private Long idDocBase;

	@Column(name= "ORDEN_DOCBASE")
	private BigDecimal ordenDocBase;

	@Column(name = "NUM_IMPR_PERM")
	private Long numImpresionesPerm;

	@Column(name = "NUM_IMPR_FICHA")
	private Long numImpresionesFicha;

	@Column(name = "PERMISO")
	private String permiso;

	@Column(name = "FICHA_TECNICA")
	private String fichaTecnica;

	@Column(name = "ESTADO_SOL_PERM")
	private String estadoSolPerm;

	@Column(name = "ESTADO_SOL_FICHA")
	private String estadoSolFicha;

	@Column(name = "N_SERIE_PERMISO")
	private String nSeriePermiso;

	@Column(name = "ESTADO_IMP_PERM")
	private String estadoImpPerm;

	@Column(name = "ESTADO_IMP_FICHA")
	private String estadoImpFicha;

	@Column(name = "DOC_PERMISO")
	private Long docPermiso;

	@Column(name = "DOC_FICHA_TECNICA")
	private Long docFichaTecnica;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOC_PERMISO",referencedColumnName="ID", insertable = false, updatable = false)
	private DocPermDistItvVO docPermisoVO;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOC_FICHA_TECNICA",referencedColumnName="ID",insertable = false, updatable = false)
	private DocPermDistItvVO docFichaTecnicaVO;

	@Column(name="EXCEL_KO_IMPR")
	private String excelKoImpr;

	@Column(name="NRE")
	private String nre;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_REGISTRO")
	private Date fechaRegistroNRE;

	/* FIN Mantis 0011883 */

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getAnotaciones() {
		return anotaciones;
	}

	public void setAnotaciones(String anotaciones) {
		this.anotaciones = anotaciones;
	}

	public String getCambioDomicilio() {
		return cambioDomicilio;
	}

	public void setCambioDomicilio(String cambioDomicilio) {
		this.cambioDomicilio = cambioDomicilio;
	}

	public String getCem() {
		return cem;
	}

	public void setCem(String cem) {
		this.cem = cem;
	}

	public String getCema() {
		return cema;
	}

	public void setCema(String cema) {
		this.cema = cema;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public String getExentoCem() {
		return exentoCem;
	}

	public void setExentoCem(String exentoCem) {
		this.exentoCem = exentoCem;
	}

	public String getExentoIedtm() {
		return exentoIedtm;
	}

	public void setExentoIedtm(String exentoIedtm) {
		this.exentoIedtm = exentoIedtm;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaDigitalizacion() {
		return fechaDigitalizacion;
	}

	public void setFechaDigitalizacion(Date fechaDigitalizacion) {
		this.fechaDigitalizacion = fechaDigitalizacion;
	}

	public Date getFechaIedtm() {
		return fechaIedtm;
	}

	public void setFechaIedtm(Date fechaIedtm) {
		this.fechaIedtm = fechaIedtm;
	}

	public Date getFechaImpresion() {
		return fechaImpresion;
	}

	public void setFechaImpresion(Date fechaImpresion) {
		this.fechaImpresion = fechaImpresion;
	}

	public Date getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(Date fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public Date getFechaPresentacionJpt() {
		return fechaPresentacionJpt;
	}

	public void setFechaPresentacionJpt(Date fechaPresentacionJpt) {
		this.fechaPresentacionJpt = fechaPresentacionJpt;
	}

	public Date getFechaUltModif() {
		return fechaUltModif;
	}

	public void setFechaUltModif(Date fechaUltModif) {
		this.fechaUltModif = fechaUltModif;
	}

	public String getFinancieraIedtm() {
		return financieraIedtm;
	}

	public void setFinancieraIedtm(String financieraIedtm) {
		this.financieraIedtm = financieraIedtm;
	}

	public String getIdProvinciaCem() {
		return idProvinciaCem;
	}

	public void setIdProvinciaCem(String idProvinciaCem) {
		this.idProvinciaCem = idProvinciaCem;
	}

	public UsuarioVO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}

	public String getIedtm() {
		return iedtm;
	}

	public void setIedtm(String iedtm) {
		this.iedtm = iedtm;
	}

	public JefaturaTraficoVO getJefaturaTrafico() {
		return jefaturaTrafico;
	}

	public void setJefaturaTrafico(JefaturaTraficoVO jefaturaTrafico) {
		this.jefaturaTrafico = jefaturaTrafico;
	}

	public Set<IntervinienteTraficoVO> getIntervinienteTraficos() {
		return intervinienteTraficos;
	}

	public List<IntervinienteTraficoVO> getIntervinienteTraficosAsList() {
		List<IntervinienteTraficoVO> lista;
		if (intervinienteTraficos != null) {
			// Map from Set to List
			lista = new ArrayList<IntervinienteTraficoVO>(intervinienteTraficos);
		} else {
			lista = Collections.emptyList();
		}
		return lista;
	}

	public IntervinienteTraficoVO getFirstElementIntervinienteTrafico() {
		ListsOperator<IntervinienteTraficoVO> listOperator = new ListsOperator<>();
		return listOperator.getFirstElement(getIntervinienteTraficos());
	}

	public void setIntervinienteTraficos(Set<IntervinienteTraficoVO> intervinienteTraficos) {
		this.intervinienteTraficos = intervinienteTraficos;
	}

	public void setIntervinienteTraficos(List<IntervinienteTraficoVO> tramitesTrafico) {
		if (tramitesTrafico == null) {
			this.intervinienteTraficos = null;
		} else {
			// Map from List to Set
			this.intervinienteTraficos = new HashSet<IntervinienteTraficoVO>(tramitesTrafico);
		}
	}

	public String getModeloIedtm() {
		return modeloIedtm;
	}

	public void setModeloIedtm(String modeloIedtm) {
		this.modeloIedtm = modeloIedtm;
	}

	public String getnRegIedtm() {
		return nRegIedtm;
	}

	public void setnRegIedtm(String nRegIedtm) {
		this.nRegIedtm = nRegIedtm;
	}

	public String getNoSujecionIedtm() {
		return noSujecionIedtm;
	}

	public void setNoSujecionIedtm(String noSujecionIedtm) {
		this.noSujecionIedtm = noSujecionIedtm;
	}

	public String getNpasos() {
		return npasos;
	}

	public void setNpasos(String npasos) {
		this.npasos = npasos;
	}

	public Short getPresentadoJpt() {
		return presentadoJpt;
	}

	public void setPresentadoJpt(Short presentadoJpt) {
		this.presentadoJpt = presentadoJpt;
	}

	public String getRefPropia() {
		return refPropia;
	}

	public void setRefPropia(String refPropia) {
		this.refPropia = refPropia;
	}

	public String getRenting() {
		return renting;
	}

	public void setRenting(String renting) {
		this.renting = renting;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public BigDecimal getYbestado() {
		return ybestado;
	}

	public void setYbestado(BigDecimal ybestado) {
		this.ybestado = ybestado;
	}

	public Set<EvolucionTramiteTraficoVO> getEvolucionTramiteTraficos() {
		return evolucionTramiteTraficos;
	}

	public List<EvolucionTramiteTraficoVO> getEvolucionTramiteTraficosAsList() {
		List<EvolucionTramiteTraficoVO> lista;
		if (evolucionTramiteTraficos != null) {
			// Map from Set to List
			lista =  new ArrayList<EvolucionTramiteTraficoVO>(evolucionTramiteTraficos);
		} else {
			lista = Collections.emptyList();
		}
		return lista;
	}

	public EvolucionTramiteTraficoVO getFirstElementEvolucionTramiteTrafico() {
		ListsOperator<EvolucionTramiteTraficoVO> listOperator = new ListsOperator<EvolucionTramiteTraficoVO>();
		return listOperator.getFirstElement(getEvolucionTramiteTraficos());
	}

	public void setEvolucionTramiteTraficos(Set<EvolucionTramiteTraficoVO> evolucionTramiteTraficos) {
		this.evolucionTramiteTraficos = evolucionTramiteTraficos;
	}

	public void setEvolucionTramiteTraficos(List<EvolucionTramiteTraficoVO> tramitesTrafico) {
		if (tramitesTrafico == null) {
			this.evolucionTramiteTraficos = null;
		} else {
			// Map from List to Set
			this.evolucionTramiteTraficos = new HashSet<EvolucionTramiteTraficoVO>(tramitesTrafico);
		}
	}

	public BigDecimal getIdTipoCreacion() {
		return idTipoCreacion;
	}

	public void setIdTipoCreacion(BigDecimal idTipoCreacion) {
		this.idTipoCreacion = idTipoCreacion;
	}

	public TasaVO getTasa() {
		return tasa;
	}

	public void setTasa(TasaVO tasa) {
		this.tasa = tasa;
	}

	public YbpdfVO getYbpdf() {
		return ybpdf;
	}

	public void setYbpdf(YbpdfVO ybpdf) {
		this.ybpdf = ybpdf;
	}

	public String getIdYbpdf() {
		return idYbpdf;
	}

	public void setIdYbpdf(String idYbpdf) {
		this.idYbpdf = idYbpdf;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public VehiculoVO getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(VehiculoVO vehiculo) {
		this.vehiculo = vehiculo;
	}

	public ContratoVO getContrato() {
		return contrato;
	}

	public void setContrato(ContratoVO contrato) {
		this.contrato = contrato;
	}

	public BigDecimal getSimultanea() {
		return simultanea;
	}

	public void setSimultanea(BigDecimal simultanea) {
		this.simultanea = simultanea;
	}

	public String getRespuestaGest() {
		return respuestaGest;
	}

	public void setRespuestaGest(String respuestaGest) {
		this.respuestaGest = respuestaGest;
	}

	public String getRespuestaDigitalizacionGdoc() {
		return respuestaDigitalizacionGdoc;
	}

	public void setRespuestaDigitalizacionGdoc(String respuestaDigitalizacionGdoc) {
		this.respuestaDigitalizacionGdoc = respuestaDigitalizacionGdoc;
	}

	public Set<TramiteTrafFacturacionVO> getTramiteFacturacion() {
		return tramiteFacturacion;
	}

	public List<TramiteTrafFacturacionVO> getTramiteFacturacionAsList() {
		// Map from Set to List
		List<TramiteTrafFacturacionVO> lista;
		if (tramiteFacturacion != null) {
			lista = new ArrayList<TramiteTrafFacturacionVO>(tramiteFacturacion);
		} else {
			lista = Collections.emptyList();
		}
		return lista;
	}

	public TramiteTrafFacturacionVO getFirstElementTramiteFacturacion() {
		ListsOperator<TramiteTrafFacturacionVO> listOperator = new ListsOperator<TramiteTrafFacturacionVO>();
		return listOperator.getFirstElement(getTramiteFacturacion());
	}

	public void setTramiteFacturacion(Set<TramiteTrafFacturacionVO> tramiteFacturacion) {
		this.tramiteFacturacion = tramiteFacturacion;
	}

	public void setTramiteFacturacion(List<TramiteTrafFacturacionVO> tramitesTrafico) {
		if (tramitesTrafico == null) {
			this.tramiteFacturacion = null;
		} else {
			// Map from List to Set
			this.tramiteFacturacion = new HashSet<TramiteTrafFacturacionVO>(tramitesTrafico);
		}
	}

	public Set<EvolucionPresentacionJptVO> getEvolucionPresentacionJptVO() {
		return evolucionPresentacionJptVO;
	}

	public void setEvolucionPresentacionJptVO(Set<EvolucionPresentacionJptVO> evolucionPresentacionJptVO) {
		this.evolucionPresentacionJptVO = evolucionPresentacionJptVO;
	}

	public BigDecimal getOrdenDocBase() {
		return ordenDocBase;
	}

	public void setOrdenDocBase(BigDecimal ordenDocBase) {
		this.ordenDocBase = ordenDocBase;
	}

	public Long getNumImpresionesPerm() {
		return numImpresionesPerm;
	}

	public void setNumImpresionesPerm(Long numImpresionesPerm) {
		this.numImpresionesPerm = numImpresionesPerm;
	}

	public Long getNumImpresionesFicha() {
		return numImpresionesFicha;
	}

	public void setNumImpresionesFicha(Long numImpresionesFicha) {
		this.numImpresionesFicha = numImpresionesFicha;
	}

	public String getPermiso() {
		return permiso;
	}

	public void setPermiso(String permiso) {
		this.permiso = permiso;
	}

	public String getFichaTecnica() {
		return fichaTecnica;
	}

	public void setFichaTecnica(String fichaTecnica) {
		this.fichaTecnica = fichaTecnica;
	}

	public String getEstadoSolPerm() {
		return estadoSolPerm;
	}

	public void setEstadoSolPerm(String estadoSolPerm) {
		this.estadoSolPerm = estadoSolPerm;
	}

	public String getEstadoSolFicha() {
		return estadoSolFicha;
	}

	public void setEstadoSolFicha(String estadoSolFicha) {
		this.estadoSolFicha = estadoSolFicha;
	}

	public String getnSeriePermiso() {
		return nSeriePermiso;
	}

	public void setnSeriePermiso(String nSeriePermiso) {
		this.nSeriePermiso = nSeriePermiso;
	}

	public String getEstadoImpPerm() {
		return estadoImpPerm;
	}

	public void setEstadoImpPerm(String estadoImpPerm) {
		this.estadoImpPerm = estadoImpPerm;
	}

	public String getEstadoImpFicha() {
		return estadoImpFicha;
	}

	public void setEstadoImpFicha(String estadoImpFicha) {
		this.estadoImpFicha = estadoImpFicha;
	}

	public Long getDocPermiso() {
		return docPermiso;
	}

	public void setDocPermiso(Long docPermiso) {
		this.docPermiso = docPermiso;
	}

	public DocPermDistItvVO getDocPermisoVO() {
		return docPermisoVO;
	}

	public void setDocPermisoVO(DocPermDistItvVO docPermisoVO) {
		this.docPermisoVO = docPermisoVO;
	}

	public Long getDocFichaTecnica() {
		return docFichaTecnica;
	}

	public void setDocFichaTecnica(Long docFichaTecnica) {
		this.docFichaTecnica = docFichaTecnica;
	}

	public DocPermDistItvVO getDocFichaTecnicaVO() {
		return docFichaTecnicaVO;
	}

	public void setDocFichaTecnicaVO(DocPermDistItvVO docFichaTecnicaVO) {
		this.docFichaTecnicaVO = docFichaTecnicaVO;
	}

	public String getExcelKoImpr() {
		return excelKoImpr;
	}

	public void setExcelKoImpr(String excelKoImpr) {
		this.excelKoImpr = excelKoImpr;
	}

	public Long getIdDocBase() {
		return idDocBase;
	}

	public void setIdDocBase(Long idDocBase) {
		this.idDocBase = idDocBase;
	}

	public void setDocumentoBase(DocumentoBaseVO documentoBase) {
		this.documentoBase = documentoBase;
	}

	public DocumentoBaseVO getDocumentoBase() {
		return documentoBase;
	}

	public String getNre() {
		return nre;
	}

	public void setNre(String nre) {
		this.nre = nre;
	}

	public Date getFechaRegistroNRE() {
		return fechaRegistroNRE;
	}

	public void setFechaRegistroNRE(Date fechaRegistroNRE) {
		this.fechaRegistroNRE = fechaRegistroNRE;
	}

}