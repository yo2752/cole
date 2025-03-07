package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.docPermDistItv.model.vo.DocPermDistItvVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafDuplicadoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafTranVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import trafico.beans.TramiteTraficoMatriculacionBean;


public class ResultadoPermisoDistintivoItvBean implements Serializable {


	private static final long serialVersionUID = 2613342057648327886L;
	private Boolean error;
	private Boolean existeImprNocturno;
	private Boolean existeImprPermisosMatw;
	private Exception excepcion;
	private String mensaje;
	private Integer numOk;
	private Integer numError;
	private List<String> listaOk;
	private List<String> listaError;
	private List<String> listaMensajes;
	private List<TramiteTraficoMatriculacionBean> tramites;
	private String respuesta;
	private byte[] xml;
	private ResumenPermisoDistintivoItvBean resumen;
	private File fichero;
	private String nombreFichero;
	private List<String> listaDocIdImpr;
	private Boolean esZip;
	private Long maxIdImpresion;
	private Date fecha;
	private byte[] byteFichero;
	private Long idDocPermDstvEitv;
	private String docId;
	private List<TramiteTrafMatrVO> listaTramitesMatrVO;
	private List<TramiteTrafTranVO> listaTramitesCtit;
	private List<TramiteTrafDuplicadoVO> listaTramitesDuplicado;
	private Boolean esPermiso;
	private Boolean esDistintivo;
	private Boolean esEitv;
	private List<String> listaNombreFicheros;
	private List<Long> listaIdDocDistintivos;
	private List<TramiteTrafMatrVO> listaCero;
	private List<TramiteTrafMatrVO> listaEco;
	private List<TramiteTrafMatrVO> listaB;
	private List<TramiteTrafMatrVO> listaC;
	private List<TramiteTrafMatrVO> listaCarsharing;
	private List<TramiteTrafMatrVO> listaMotosharing;
	private List<TramiteTrafMatrVO> listaCeroMotos;
	private List<TramiteTrafMatrVO> listaEcoMotos;
	private List<TramiteTrafMatrVO> listaBMotos;
	private List<TramiteTrafMatrVO> listaCMotos;
	private EstadoPermisoDistintivoItv estado;
	private List<ContratoDto> listaContratos;
	private List<FicheroBean> listaFicherosColegiado;
	private PaginatedList listaDetallePaginacion;
	private Boolean tieneTramitesST;
	private List<byte[]> listaPdfPermisos;
	private List<byte[]> listaPdfFichas;
	private Boolean errorPermisos;
	private String mensajeErrorPermisos;
	private Boolean errorFichas;
	private String mensajeErrorFichas;
	private HashMap<String,File> listaFicheros;
	private List<TramiteTraficoVO> listaTramitesSinPermiso;
	private List<TramiteTraficoVO> listaTramitesSinFicha;
	private List<TramiteTraficoVO> listaTramitesConPermiso;
	private List<TramiteTraficoVO> listaTramitesConFicha;
	private Boolean noExistenDoc; 
	private DocPermDistItvVO docPermDistItv;
	private List<BigDecimal> listaExpedientesOk;
	private List<TramiteTrafMatrVO> listaTramitesA;
	private List<TramiteTrafMatrVO> listaTramitesNormal;
	
	public ResultadoPermisoDistintivoItvBean(Boolean error) {
		super();
		this.error = error;
		this.resumen = new ResumenPermisoDistintivoItvBean();
		this.esZip = Boolean.FALSE;
		this.errorFichas = Boolean.FALSE;
		this.errorPermisos = Boolean.FALSE;
		this.tieneTramitesST = Boolean.FALSE;
		this.noExistenDoc = Boolean.FALSE;
		this.existeImprNocturno = Boolean.FALSE;
		this.existeImprPermisosMatw = Boolean.FALSE;
	}
	
	public ResultadoPermisoDistintivoItvBean() {
		super();
		this.error = Boolean.FALSE;
		this.errorFichas = Boolean.FALSE;
		this.errorPermisos = Boolean.FALSE;
		this.tieneTramitesST = Boolean.FALSE;
		this.existeImprNocturno = Boolean.FALSE;
		this.existeImprPermisosMatw = Boolean.FALSE;
	}
	
	public void addListaExpedientesOk(BigDecimal numExpediente){
		if(listaExpedientesOk == null){
			listaExpedientesOk = new ArrayList<BigDecimal>();
		}
		listaExpedientesOk.add(numExpediente);
	}
	
	public void addListaFicheros(String nombre, File fichero){
		if(listaFicheros == null){
			listaFicheros = new HashMap<String, File>();
		}
		listaFicheros.put(nombre, fichero);
	}
	
	public void addPdfListaPermisos(byte[] pdf){
		if(pdf != null){
			int cont = 0;
			if(listaPdfPermisos == null || listaPdfPermisos.isEmpty()){
				listaPdfPermisos = new ArrayList<byte[]>();
			} else {
				cont = listaPdfPermisos.size();
			}
			listaPdfPermisos.add(cont, pdf);
		}
	}
	
	public void addPdfListaFichas(byte[] pdf){
		if(pdf != null){
			int cont = 0;
			if(listaPdfFichas == null || listaPdfFichas.isEmpty()){
				listaPdfFichas = new ArrayList<byte[]>();
			} else {
				cont = listaPdfFichas.size();
			}
			listaPdfFichas.add(cont, pdf);
		}
	}
	
	public void addListaTramiteSinPermiso(TramiteTraficoVO tramite){
		if(listaTramitesSinPermiso == null){
			listaTramitesSinPermiso = new ArrayList<TramiteTraficoVO>();
		}
		listaTramitesSinPermiso.add(tramite);
	}
	
	public void addListaTramiteSinFicha(TramiteTraficoVO tramite){
		if(listaTramitesSinFicha == null){
			listaTramitesSinFicha = new ArrayList<TramiteTraficoVO>();
		}
		listaTramitesSinFicha.add(tramite);
	}
	
	public void addListaTramiteConPermiso(TramiteTraficoVO tramite){
		if(listaTramitesConPermiso == null){
			listaTramitesConPermiso = new ArrayList<TramiteTraficoVO>();
		}
		listaTramitesConPermiso.add(tramite);
	}
	
	public void addListaTramiteConFicha(TramiteTraficoVO tramite){
		if(listaTramitesConFicha == null){
			listaTramitesConFicha = new ArrayList<TramiteTraficoVO>();
		}
		listaTramitesConFicha.add(tramite);
	}
	
	public void addListaFicherosColegiado(byte[] fichero, String nombreFichero){
		if(listaFicherosColegiado == null){
			listaFicherosColegiado = new ArrayList<FicheroBean>();
		}
		FicheroBean ficheroBean = new FicheroBean();
		ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);
		ficheroBean.setFicheroByte(fichero);
		ficheroBean.setNombreDocumento(nombreFichero);
		listaFicherosColegiado.add(ficheroBean);
	}
	
	public void addListaFicherosColegiado(List<FicheroBean> ficheros){
		if(listaFicherosColegiado == null){
			listaFicherosColegiado = new ArrayList<FicheroBean>();
		}
		for(FicheroBean fich : ficheros){
			listaFicherosColegiado.add(fich);
		}
	}
	
	public void addListaIdDocDistintivos(Long id){
		if(listaIdDocDistintivos == null){
			listaIdDocDistintivos = new ArrayList<Long>();
		}
		listaIdDocDistintivos.add(id);
	}
	
	public void addListaCero(TramiteTrafMatrVO trafMatrVO){
		if(listaCero == null){
			listaCero =  new ArrayList<TramiteTrafMatrVO>();
		}
		listaCero.add(trafMatrVO);
	}
	
	public void addListaEco(TramiteTrafMatrVO trafMatrVO){
		if(listaEco == null){
			listaEco =  new ArrayList<TramiteTrafMatrVO>();
		}
		listaEco.add(trafMatrVO);
	}	
	
	public void addListaB(TramiteTrafMatrVO trafMatrVO){
		if(listaB == null){
			listaB =  new ArrayList<TramiteTrafMatrVO>();
		}
		listaB.add(trafMatrVO);
	}
	
	public void addListaC(TramiteTrafMatrVO trafMatrVO){
		if(listaC == null){
			listaC =  new ArrayList<TramiteTrafMatrVO>();
		}
		listaC.add(trafMatrVO);
	}
	
	public void addListaCarsharing(TramiteTrafMatrVO trafMatrVO){
		if(listaCarsharing == null){
			listaCarsharing =  new ArrayList<TramiteTrafMatrVO>();
		}
		listaCarsharing.add(trafMatrVO);
	}
	
	public void addListaMotosharing(TramiteTrafMatrVO trafMatrVO){
		if(listaMotosharing == null){
			listaMotosharing =  new ArrayList<TramiteTrafMatrVO>();
		}
		listaMotosharing.add(trafMatrVO);
	}
	
	public void addListaCeroMotos(TramiteTrafMatrVO trafMatrVO){
		if(listaCeroMotos == null){
			listaCeroMotos =  new ArrayList<TramiteTrafMatrVO>();
		}
		listaCeroMotos.add(trafMatrVO);
	}
	
	public void addListaEcoMotos(TramiteTrafMatrVO trafMatrVO){
		if(listaEcoMotos == null){
			listaEcoMotos =  new ArrayList<TramiteTrafMatrVO>();
		}
		listaEcoMotos.add(trafMatrVO);
	}	
	
	public void addListaBMotos(TramiteTrafMatrVO trafMatrVO){
		if(listaBMotos == null){
			listaBMotos =  new ArrayList<TramiteTrafMatrVO>();
		}
		listaBMotos.add(trafMatrVO);
	}
	
	public void addListaCMotos(TramiteTrafMatrVO trafMatrVO){
		if(listaCMotos == null){
			listaCMotos =  new ArrayList<TramiteTrafMatrVO>();
		}
		listaCMotos.add(trafMatrVO);
	}
	
	public void addOk(){
		if(numOk== null){
			numOk = 0;
		}
		numOk++;
	}
	
	public void addError(){
		if(numError== null){
			numError = 0;
		}
		numError++;
	}
	public void addListaOk(String mensaje){
		if(listaOk == null){
			listaOk =  new ArrayList<String>();
		}
		listaOk.add(mensaje);
	}
	
	public void addListaError(String mensaje){
		if(listaError == null){
			listaError =  new ArrayList<String>();
		}
		listaError.add(mensaje);
	}
	
	public void addListaMensaje(String mensaje){
		if(listaMensajes == null){
			listaMensajes =  new ArrayList<String>();
		}
		listaMensajes.add(mensaje);
	}
	
	
	public void addListaNombreFichero(String nombre){
		if(listaNombreFicheros == null || listaNombreFicheros.isEmpty()){
			listaNombreFicheros = new ArrayList<String>();
		}
		listaNombreFicheros.add(nombre);
	}
	
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	public Exception getExcepcion() {
		return excepcion;
	}
	public void setExcepcion(Exception excepcion) {
		this.excepcion = excepcion;
	}
	public List<String> getListaMensajes() {
		if(listaMensajes==null){
			listaMensajes = new ArrayList<String>();
		}
		return listaMensajes;
	}
	public void setListaMensajes(List<String> listaMensajes) {
		this.listaMensajes = listaMensajes;
	}
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	public Boolean getError() {
		return error;
	}
	public void setError(Boolean error) {
		this.error = error;
	}
	public byte[] getXml() {
		return xml;
	}
	public void setXml(byte[] xml) {
		this.xml = xml;
	}

	public ResumenPermisoDistintivoItvBean getResumen() {
		return resumen;
	}

	public void setResumen(ResumenPermisoDistintivoItvBean resumen) {
		this.resumen = resumen;
	}

	public File getFichero() {
		return fichero;
	}

	public void setFichero(File fichero) {
		this.fichero = fichero;
	}

	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	public List<String> getListaDocIdImpr() {
		return listaDocIdImpr;
	}

	public void setListaDocIdImpr(List<String> listaDocIdImpr) {
		this.listaDocIdImpr = listaDocIdImpr;
	}

	public Boolean getEsZip() {
		return esZip;
	}

	public void setEsZip(Boolean esZip) {
		this.esZip = esZip;
	}

	public Long getMaxIdImpresion() {
		return maxIdImpresion;
	}

	public void setMaxIdImpresion(Long maxIdImpresion) {
		this.maxIdImpresion = maxIdImpresion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public byte[] getByteFichero() {
		return byteFichero;
	}

	public void setByteFichero(byte[] byteFichero) {
		this.byteFichero = byteFichero;
	}

	public Integer getNumOk() {
		return numOk;
	}

	public void setNumOk(Integer numOk) {
		this.numOk = numOk;
	}

	public Integer getNumError() {
		return numError;
	}

	public void setNumError(Integer numError) {
		this.numError = numError;
	}

	public List<String> getListaOk() {

		if(this.listaOk==null){
			this.listaOk = new ArrayList<String>();
		}
		return listaOk;


	}

	public void setListaOk(List<String> listaOk) {
		this.listaOk = listaOk;
	}

	public List<String> getListaError() {
		if(listaError==null){
			listaError = new ArrayList<String>();
		}
		return listaError;
	}

	public void setListaError(List<String> listaError) {
		this.listaError = listaError;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public List<TramiteTraficoMatriculacionBean> getTramites() {
		return tramites;
	}

	public void setTramites(List<TramiteTraficoMatriculacionBean> tramites) {
		this.tramites = tramites;
	}

	public Long getIdDocPermDstvEitv() {
		return idDocPermDstvEitv;
	}

	public void setIdDocPermDstvEitv(Long idDocPermDstvEitv) {
		this.idDocPermDstvEitv = idDocPermDstvEitv;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public List<TramiteTrafMatrVO> getListaTramitesMatrVO() {
		return listaTramitesMatrVO;
	}

	public void setListaTramitesMatrVO(List<TramiteTrafMatrVO> listaTramitesMatrVO) {
		this.listaTramitesMatrVO = listaTramitesMatrVO;
	}

	public Boolean getEsPermiso() {
		return esPermiso;
	}

	public void setEsPermiso(Boolean esPermiso) {
		this.esPermiso = esPermiso;
	}

	public Boolean getEsDistintivo() {
		return esDistintivo;
	}

	public void setEsDistintivo(Boolean esDistintivo) {
		this.esDistintivo = esDistintivo;
	}

	public Boolean getEsEitv() {
		return esEitv;
	}

	public void setEsEitv(Boolean esEitv) {
		this.esEitv = esEitv;
	}

	public List<TramiteTrafMatrVO> getListaCero() {
		return listaCero;
	}

	public void setListaCero(List<TramiteTrafMatrVO> listaCero) {
		this.listaCero = listaCero;
	}

	public List<TramiteTrafMatrVO> getListaEco() {
		return listaEco;
	}

	public void setListaEco(List<TramiteTrafMatrVO> listaEco) {
		this.listaEco = listaEco;
	}

	public List<TramiteTrafMatrVO> getListaB() {
		return listaB;
	}

	public void setListaB(List<TramiteTrafMatrVO> listaB) {
		this.listaB = listaB;
	}

	public List<TramiteTrafMatrVO> getListaC() {
		return listaC;
	}

	public void setListaC(List<TramiteTrafMatrVO> listaC) {
		this.listaC = listaC;
	}

	public List<String> getListaNombreFicheros() {
		return listaNombreFicheros;
	}

	public void setListaNombreFicheros(List<String> listaNombreFicheros) {
		this.listaNombreFicheros = listaNombreFicheros;
	}

	public List<Long> getListaIdDocDistintivos() {
		return listaIdDocDistintivos;
	}

	public void setListaIdDocDistintivos(List<Long> listaIdDocDistintivos) {
		this.listaIdDocDistintivos = listaIdDocDistintivos;
	}

	public EstadoPermisoDistintivoItv getEstado() {
		return estado;
	}

	public void setEstado(EstadoPermisoDistintivoItv estado) {
		this.estado = estado;
	}


	public List<TramiteTrafMatrVO> getListaCeroMotos() {
		return listaCeroMotos;
	}

	public void setListaCeroMotos(List<TramiteTrafMatrVO> listaCeroMotos) {
		this.listaCeroMotos = listaCeroMotos;
	}

	public List<TramiteTrafMatrVO> getListaEcoMotos() {
		return listaEcoMotos;
	}

	public void setListaEcoMotos(List<TramiteTrafMatrVO> listaEcoMotos) {
		this.listaEcoMotos = listaEcoMotos;
	}

	public List<TramiteTrafMatrVO> getListaBMotos() {
		return listaBMotos;
	}

	public void setListaBMotos(List<TramiteTrafMatrVO> listaBMotos) {
		this.listaBMotos = listaBMotos;
	}

	public List<TramiteTrafMatrVO> getListaCMotos() {
		return listaCMotos;
	}

	public void setListaCMotos(List<TramiteTrafMatrVO> listaCMotos) {
		this.listaCMotos = listaCMotos;
	}

	public List<FicheroBean> getListaFicherosColegiado() {
		return listaFicherosColegiado;
	}

	public void setListaFicherosColegiado(List<FicheroBean> listaFicherosColegiado) {
		this.listaFicherosColegiado = listaFicherosColegiado;
	}

	public PaginatedList getListaDetallePaginacion() {
		return listaDetallePaginacion;
	}

	public void setListaDetallePaginacion(PaginatedList listaDetallePaginacion) {
		this.listaDetallePaginacion = listaDetallePaginacion;
	}

	public Boolean getTieneTramitesST() {
		return tieneTramitesST;
	}

	public void setTieneTramitesST(Boolean tieneTramitesST) {
		this.tieneTramitesST = tieneTramitesST;
	}

	public List<byte[]> getListaPdfPermisos() {
		return listaPdfPermisos;
	}

	public void setListaPdfPermisos(List<byte[]> listaPdfPermisos) {
		this.listaPdfPermisos = listaPdfPermisos;
	}

	public List<byte[]> getListaPdfFichas() {
		return listaPdfFichas;
	}

	public void setListaPdfFichas(List<byte[]> listaPdfFichas) {
		this.listaPdfFichas = listaPdfFichas;
	}

	public List<ContratoDto> getListaContratos() {
		return listaContratos;
	}

	public void setListaContratos(List<ContratoDto> listaContratos) {
		this.listaContratos = listaContratos;
	}

	public Boolean getErrorPermisos() {
		return errorPermisos;
	}

	public void setErrorPermisos(Boolean errorPermisos) {
		this.errorPermisos = errorPermisos;
	}

	public Boolean getErrorFichas() {
		return errorFichas;
	}

	public void setErrorFichas(Boolean errorFichas) {
		this.errorFichas = errorFichas;
	}

	public String getMensajeErrorPermisos() {
		return mensajeErrorPermisos;
	}

	public void setMensajeErrorPermisos(String mensajeErrorPermisos) {
		this.mensajeErrorPermisos = mensajeErrorPermisos;
	}

	public String getMensajeErrorFichas() {
		return mensajeErrorFichas;
	}

	public void setMensajeErrorFichas(String mensajeErrorFichas) {
		this.mensajeErrorFichas = mensajeErrorFichas;
	}

	public HashMap<String, File> getListaFicheros() {
		return listaFicheros;
	}

	public void setListaFicheros(HashMap<String, File> listaFicheros) {
		this.listaFicheros = listaFicheros;
	}

	public List<TramiteTraficoVO> getListaTramitesSinPermiso() {
		return listaTramitesSinPermiso;
	}

	public void setListaTramitesSinPermiso(List<TramiteTraficoVO> listaTramitesSinPermiso) {
		this.listaTramitesSinPermiso = listaTramitesSinPermiso;
	}

	public List<TramiteTraficoVO> getListaTramitesSinFicha() {
		return listaTramitesSinFicha;
	}

	public void setListaTramitesSinFicha(List<TramiteTraficoVO> listaTramitesSinFicha) {
		this.listaTramitesSinFicha = listaTramitesSinFicha;
	}

	public Boolean getNoExistenDoc() {
		return noExistenDoc;
	}

	public void setNoExistenDoc(Boolean noExistenDoc) {
		this.noExistenDoc = noExistenDoc;
	}

	public DocPermDistItvVO getDocPermDistItv() {
		return docPermDistItv;
	}

	public void setDocPermDistItv(DocPermDistItvVO docPermDistItv) {
		this.docPermDistItv = docPermDistItv;
	}

	public List<TramiteTraficoVO> getListaTramitesConPermiso() {
		return listaTramitesConPermiso;
	}

	public void setListaTramitesConPermiso(List<TramiteTraficoVO> listaTramitesConPermiso) {
		this.listaTramitesConPermiso = listaTramitesConPermiso;
	}

	public List<TramiteTraficoVO> getListaTramitesConFicha() {
		return listaTramitesConFicha;
	}

	public void setListaTramitesConFicha(List<TramiteTraficoVO> listaTramitesConFicha) {
		this.listaTramitesConFicha = listaTramitesConFicha;
	}

	public List<TramiteTrafTranVO> getListaTramitesCtit() {
		return listaTramitesCtit;
	}

	public void setListaTramitesCtit(List<TramiteTrafTranVO> listaTramitesCtit) {
		this.listaTramitesCtit = listaTramitesCtit;
	}
	
	public List<TramiteTrafDuplicadoVO> getListaTramitesDuplicado() {
		return listaTramitesDuplicado;
	}

	public void setListaTramitesDuplicado(List<TramiteTrafDuplicadoVO> listaTramitesDuplicado) {
		this.listaTramitesDuplicado = listaTramitesDuplicado;
	}

	public Boolean getExisteImprNocturno() {
		return existeImprNocturno;
	}

	public void setExisteImprNocturno(Boolean existeImprNocturno) {
		this.existeImprNocturno = existeImprNocturno;
	}

	public Boolean getExisteImprPermisosMatw() {
		return existeImprPermisosMatw;
	}

	public void setExisteImprPermisosMatw(Boolean existeImprPermisosMatw) {
		this.existeImprPermisosMatw = existeImprPermisosMatw;
	}

	public List<TramiteTrafMatrVO> getListaCarsharing() {
		return listaCarsharing;
	}

	public void setListaCarsharing(List<TramiteTrafMatrVO> listaCarsharing) {
		this.listaCarsharing = listaCarsharing;
	}

	public List<BigDecimal> getListaExpedientesOk() {
		return listaExpedientesOk;
	}

	public void setListaExpedientesOk(List<BigDecimal> listaExpedientesOk) {
		this.listaExpedientesOk = listaExpedientesOk;
	}

	public List<TramiteTrafMatrVO> getListaMotosharing() {
		return listaMotosharing;
	}

	public void setListaMotosharing(List<TramiteTrafMatrVO> listaMotosharing) {
		this.listaMotosharing = listaMotosharing;
	}

	public List<TramiteTrafMatrVO> getListaTramitesA() {
		return listaTramitesA;
	}

	public void setListaTramitesA(List<TramiteTrafMatrVO> listaTramitesA) {
		this.listaTramitesA = listaTramitesA;
	}

	public List<TramiteTrafMatrVO> getListaTramitesNormal() {
		return listaTramitesNormal;
	}

	public void setListaTramitesNormal(List<TramiteTrafMatrVO> listaTramitesNormal) {
		this.listaTramitesNormal = listaTramitesNormal;
	}
}
