package org.gestoresmadrid.oegam2comun.distintivo.view.bean;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.docPermDistItv.model.vo.VehNoMatOegamVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.oegam2comun.distintivo.view.dto.VehiculoNoMatriculadoOegamDto;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import trafico.beans.TramiteTraficoMatriculacionBean;


public class ResultadoDistintivoDgtBean implements Serializable {


	private static final long serialVersionUID = 2613342057648327886L;

	private Boolean error;
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
	private ResumenDistintivoDgtBean resumen;
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
	private Boolean esPermiso;
	private Boolean esDistintivo;
	private Boolean esEitv;
	private List<String> listaNombreFicheros;
	private List<Long> listaIdDocDistintivos;
	private List<Long> listaCeroVNMO;
	private List<Long> listaEcoVNMO;
	private List<Long> listaBVNMO;
	private List<Long> listaCVNMO;
	private List<Long> listaCeroMotosVNMO;
	private List<Long> listaEcoMotosVNMO;
	private List<Long> listaBMotosVNMO;
	private List<Long> listaCMotosVNMO;
	private List<Long> listaCarsharingVNMO;
	private List<BigDecimal> listaCero;
	private List<BigDecimal> listaEco;
	private List<BigDecimal> listaB;
	private List<BigDecimal> listaC;
	private List<BigDecimal> listaCeroMotos;
	private List<BigDecimal> listaEcoMotos;
	private List<BigDecimal> listaBMotos;
	private List<BigDecimal> listaCMotos;
	private List<BigDecimal> listaCarsharing;
	
	
	private List<VehNoMatOegamVO> listaDemandaCeroVNMO;
	private List<VehNoMatOegamVO> listaDemandaEcoVNMO;
	private List<VehNoMatOegamVO> listaDemandaBVNMO;
	private List<VehNoMatOegamVO> listaDemandaCVNMO;
	private List<VehNoMatOegamVO> listaDemandaCeroMotosVNMO;
	private List<VehNoMatOegamVO> listaDemandaEcoMotosVNMO;
	private List<VehNoMatOegamVO> listaDemandaBMotosVNMO;
	private List<VehNoMatOegamVO> listaDemandaCMotosVNMO;
	
	
	private EstadoPermisoDistintivoItv estado;
	private List<ContratoDto> listaContratos;
	private List<FicheroBean> listaFicherosColegiado;
	private List<FicheroBean> listaFicherosJefatura;
	private VehiculoNoMatriculadoOegamDto vehiculoNoMatrOegam;
	private Boolean esPrimeraImp;
	private Boolean noEsGenerable;
	private Boolean existeTramiteDup;
	private List<String> listaMatriculas;
	private String nifTitularMasivo;
	
	public List<ContratoDto> getListaContratos() {
		return listaContratos;
	}

	public void setListaContratos(List<ContratoDto> listaContratos) {
		this.listaContratos = listaContratos;
	}

	public ResultadoDistintivoDgtBean(Boolean error) {
		super();
		this.error = error;
		this.resumen = new ResumenDistintivoDgtBean();
		this.esZip = Boolean.FALSE;
		this.esPrimeraImp = Boolean.FALSE;
		this.noEsGenerable = Boolean.FALSE;
		this.existeTramiteDup = Boolean.FALSE;
	}
	
	public ResultadoDistintivoDgtBean() {
		super();
		this.error = Boolean.FALSE;
		this.esZip = Boolean.FALSE;
		this.esPrimeraImp = Boolean.FALSE;
		this.noEsGenerable = Boolean.FALSE;
		this.existeTramiteDup = Boolean.FALSE;
	}
	
	public void addListaMatriculas(String matricula){
		if(listaMatriculas == null){
			listaMatriculas = new ArrayList<String>();
		}
		listaMatriculas.add(matricula);
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
	
	public void addListaFicherosJefatura(byte[] fichero, String nombreFichero){
		if(listaFicherosJefatura == null){
			listaFicherosJefatura = new ArrayList<FicheroBean>();
		}
		FicheroBean ficheroBean = new FicheroBean();
		ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);
		ficheroBean.setFicheroByte(fichero);
		ficheroBean.setNombreDocumento(nombreFichero);
		listaFicherosJefatura.add(ficheroBean);
	}
	
	public void addListaFicherosColegiado(List<FicheroBean> ficheros){
		if(listaFicherosColegiado == null){
			listaFicherosColegiado = new ArrayList<FicheroBean>();
		}
		for(FicheroBean fich : ficheros){
			listaFicherosColegiado.add(fich);
		}
	}
	
	public void addListaFicherosJefatura(List<FicheroBean> ficheros){
		if(listaFicherosJefatura == null){
			listaFicherosJefatura = new ArrayList<FicheroBean>();
		}
		for(FicheroBean fich : ficheros){
			listaFicherosJefatura.add(fich);
		}
	}
	
	public void addListaIdDocDistintivos(Long id){
		if(listaIdDocDistintivos == null){
			listaIdDocDistintivos = new ArrayList<Long>();
		}
		listaIdDocDistintivos.add(id);
	}
	
	public void addListaCeroVNMO(Long idVehiculoNoMatOegam){
		if(listaCeroVNMO == null){
			listaCeroVNMO = new ArrayList<Long>();
		}
		listaCeroVNMO.add(idVehiculoNoMatOegam);
	}
	
	
	public void addListaCVNMO(Long idVehiculoNoMatOegam){
		if(listaCVNMO == null){
			listaCVNMO = new ArrayList<Long>();
		}
		listaCVNMO.add(idVehiculoNoMatOegam);
	}
	
	public void addListaBVNMO(Long idVehiculoNoMatOegam){
		if(listaBVNMO == null){
			listaBVNMO = new ArrayList<Long>();
		}
		listaBVNMO.add(idVehiculoNoMatOegam);
	}
	
	public void addListaEcoVNMO(Long idVehiculoNoMatOegam){
		if(listaEcoVNMO == null){
			listaEcoVNMO = new ArrayList<Long>();
		}
		listaEcoVNMO.add(idVehiculoNoMatOegam);
	}
	
	public void addListaCeroMotosVNMO(Long idVehiculoNoMatOegam){
		if(listaCeroMotosVNMO == null){
			listaCeroMotosVNMO = new ArrayList<Long>();
		}
		listaCeroMotosVNMO.add(idVehiculoNoMatOegam);
	}
	
	public void addListaCMotosVNMO(Long idVehiculoNoMatOegam){
		if(listaCMotosVNMO == null){
			listaCMotosVNMO = new ArrayList<Long>();
		}
		listaCMotosVNMO.add(idVehiculoNoMatOegam);
	}
	
	public void addListaBMotosVNMO(Long idVehiculoNoMatOegam){
		if(listaBMotosVNMO == null){
			listaBMotosVNMO = new ArrayList<Long>();
		}
		listaBMotosVNMO.add(idVehiculoNoMatOegam);
	}
	
	public void addListaEcoMotosVNMO(Long idVehiculoNoMatOegam){
		if(listaEcoMotosVNMO == null){
			listaEcoMotosVNMO = new ArrayList<Long>();
		}
		listaEcoMotosVNMO.add(idVehiculoNoMatOegam);
	}
	
	public void addListaCarsharingVNMO(Long idVehiculoNoMatOegam){
		if(listaCarsharingVNMO == null){
			listaCarsharingVNMO = new ArrayList<Long>();
		}
		listaCarsharingVNMO.add(idVehiculoNoMatOegam);
	}
	
	public List<VehNoMatOegamVO> getListaDemandaCeroVNMO() {
		return listaDemandaCeroVNMO;
	}

	public void setListaDemandaCeroVNMO(List<VehNoMatOegamVO> listaDemandaCeroVNMO) {
		this.listaDemandaCeroVNMO = listaDemandaCeroVNMO;
	}

	public List<VehNoMatOegamVO> getListaDemandaEcoVNMO() {
		return listaDemandaEcoVNMO;
	}

	public void setListaDemandaEcoVNMO(List<VehNoMatOegamVO> listaDemandaEcoVNMO) {
		this.listaDemandaEcoVNMO = listaDemandaEcoVNMO;
	}

	public List<VehNoMatOegamVO> getListaDemandaBVNMO() {
		return listaDemandaBVNMO;
	}

	public void setListaDemandaBVNMO(List<VehNoMatOegamVO> listaDemandaBVNMO) {
		this.listaDemandaBVNMO = listaDemandaBVNMO;
	}

	public List<VehNoMatOegamVO> getListaDemandaCVNMO() {
		return listaDemandaCVNMO;
	}

	public void setListaDemandaCVNMO(List<VehNoMatOegamVO> listaDemandaCVNMO) {
		this.listaDemandaCVNMO = listaDemandaCVNMO;
	}

	public List<VehNoMatOegamVO> getListaDemandaCeroMotosVNMO() {
		return listaDemandaCeroMotosVNMO;
	}

	public void setListaDemandaCeroMotosVNMO(List<VehNoMatOegamVO> listaDemandaCeroMotosVNMO) {
		this.listaDemandaCeroMotosVNMO = listaDemandaCeroMotosVNMO;
	}

	public List<VehNoMatOegamVO> getListaDemandaEcoMotosVNMO() {
		return listaDemandaEcoMotosVNMO;
	}

	public void setListaDemandaEcoMotosVNMO(List<VehNoMatOegamVO> listaDemandaEcoMotosVNMO) {
		this.listaDemandaEcoMotosVNMO = listaDemandaEcoMotosVNMO;
	}

	public List<VehNoMatOegamVO> getListaDemandaBMotosVNMO() {
		return listaDemandaBMotosVNMO;
	}

	public void setListaDemandaBMotosVNMO(List<VehNoMatOegamVO> listaDemandaBMotosVNMO) {
		this.listaDemandaBMotosVNMO = listaDemandaBMotosVNMO;
	}

	public List<VehNoMatOegamVO> getListaDemandaCMotosVNMO() {
		return listaDemandaCMotosVNMO;
	}

	public void setListaDemandaCMotosVNMO(List<VehNoMatOegamVO> listaDemandaCMotosVNMO) {
		this.listaDemandaCMotosVNMO = listaDemandaCMotosVNMO;
	}

	public void addListaDemandaCeroVNMO(VehNoMatOegamVO vehNoMatOegamVO){
		if(listaDemandaCeroVNMO == null){
			listaDemandaCeroVNMO = new ArrayList<VehNoMatOegamVO>();
		}
		listaDemandaCeroVNMO.add(vehNoMatOegamVO);
	}
	
	
	public void addListaDemandaCVNMO(VehNoMatOegamVO vehNoMatOegamVO){
		if(listaDemandaCVNMO == null){
			listaDemandaCVNMO = new ArrayList<VehNoMatOegamVO>();
		}
		listaDemandaCVNMO.add(vehNoMatOegamVO);
	}
	
	public void addListaDemandaBVNMO(VehNoMatOegamVO vehNoMatOegamVO){
		if(listaDemandaBVNMO == null){
			listaDemandaBVNMO = new ArrayList<VehNoMatOegamVO>();
		}
		listaDemandaBVNMO.add(vehNoMatOegamVO);
	}
	
	public void addListaDemandaEcoVNMO(VehNoMatOegamVO vehNoMatOegamVO){
		if(listaDemandaEcoVNMO == null){
			listaDemandaEcoVNMO = new ArrayList<VehNoMatOegamVO>();
		}
		listaDemandaEcoVNMO.add(vehNoMatOegamVO);
	}
	
	public void addListaDemandaCeroMotosVNMO(VehNoMatOegamVO vehNoMatOegamVO){
		if(listaDemandaCeroMotosVNMO == null){
			listaDemandaCeroMotosVNMO = new ArrayList<VehNoMatOegamVO>();
		}
		listaDemandaCeroMotosVNMO.add(vehNoMatOegamVO);
	}
	
	public void addListaDemandaCMotosVNMO(VehNoMatOegamVO vehNoMatOegamVO){
		if(listaDemandaCMotosVNMO == null){
			listaDemandaCMotosVNMO = new ArrayList<VehNoMatOegamVO>();
		}
		listaDemandaCMotosVNMO.add(vehNoMatOegamVO);
	}
	
	public void addListaDemandaBMotosVNMO(VehNoMatOegamVO vehNoMatOegamVO){
		if(listaDemandaBMotosVNMO == null){
			listaDemandaBMotosVNMO = new ArrayList<VehNoMatOegamVO>();
		}
		listaDemandaBMotosVNMO.add(vehNoMatOegamVO);
	}
	
	public void addListaDemandaEcoMotosVNMO(VehNoMatOegamVO vehNoMatOegamVO){
		if(listaDemandaEcoMotosVNMO == null){
			listaDemandaEcoMotosVNMO = new ArrayList<VehNoMatOegamVO>();
		}
		listaDemandaEcoMotosVNMO.add(vehNoMatOegamVO);
	}
	
	public void addListaCero(BigDecimal numExpediente){
		if(listaCero == null){
			listaCero =  new ArrayList<BigDecimal>();
		}
		listaCero.add(numExpediente);
	}
	
	public void addListaEco(BigDecimal numExpediente){
		if(listaEco == null){
			listaEco =  new ArrayList<BigDecimal>();
		}
		listaEco.add(numExpediente);
	}	
	
	public void addListaB(BigDecimal numExpediente){
		if(listaB == null){
			listaB =  new ArrayList<BigDecimal>();
		}
		listaB.add(numExpediente);
	}
	
	public void addListaC(BigDecimal numExpediente){
		if(listaC == null){
			listaC =  new ArrayList<BigDecimal>();
		}
		listaC.add(numExpediente);
	}
	
	public void addListaCarsharing(BigDecimal numExpediente){
		if(listaCarsharing == null){
			listaCarsharing =  new ArrayList<BigDecimal>();
		}
		listaCarsharing.add(numExpediente);
	}
	
	public void addListaCeroMotos(BigDecimal numExpediente){
		if(listaCeroMotos == null){
			listaCeroMotos =  new ArrayList<BigDecimal>();
		}
		listaCeroMotos.add(numExpediente);
	}
	
	public void addListaEcoMotos(BigDecimal numExpediente){
		if(listaEcoMotos == null){
			listaEcoMotos =  new ArrayList<BigDecimal>();
		}
		listaEcoMotos.add(numExpediente);
	}	
	
	public void addListaBMotos(BigDecimal numExpediente){
		if(listaBMotos == null){
			listaBMotos =  new ArrayList<BigDecimal>();
		}
		listaBMotos.add(numExpediente);
	}
	
	public void addListaCMotos(BigDecimal numExpediente){
		if(listaCMotos == null){
			listaCMotos =  new ArrayList<BigDecimal>();
		}
		listaCMotos.add(numExpediente);
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
	
	public void addResumenOK(String mensaje){
		if(resumen == null){
			resumen = new ResumenDistintivoDgtBean();
		}
		resumen.addListaOk(mensaje);
	}
	
	public void addResumenKO(String mensaje){
		if(resumen == null){
			resumen = new ResumenDistintivoDgtBean();
		}
		resumen.addListaKO(mensaje);
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

	public List<FicheroBean> getListaFicherosColegiado() {
		return listaFicherosColegiado;
	}

	public void setListaFicherosColegiado(List<FicheroBean> listaFicherosColegiado) {
		this.listaFicherosColegiado = listaFicherosColegiado;
	}

	public List<FicheroBean> getListaFicherosJefatura() {
		return listaFicherosJefatura;
	}

	public void setListaFicherosJefatura(List<FicheroBean> listaFicherosJefatura) {
		this.listaFicherosJefatura = listaFicherosJefatura;
	}

	public ResumenDistintivoDgtBean getResumen() {
		return resumen;
	}

	public void setResumen(ResumenDistintivoDgtBean resumen) {
		this.resumen = resumen;
	}

	public VehiculoNoMatriculadoOegamDto getVehiculoNoMatrOegam() {
		return vehiculoNoMatrOegam;
	}

	public void setVehiculoNoMatrOegam(VehiculoNoMatriculadoOegamDto vehiculoNoMatrOegam) {
		this.vehiculoNoMatrOegam = vehiculoNoMatrOegam;
	}

	public List<Long> getListaCeroVNMO() {
		return listaCeroVNMO;
	}

	public void setListaCeroVNMO(List<Long> listaCeroVNMO) {
		this.listaCeroVNMO = listaCeroVNMO;
	}

	public List<Long> getListaEcoVNMO() {
		return listaEcoVNMO;
	}

	public void setListaEcoVNMO(List<Long> listaEcoVNMO) {
		this.listaEcoVNMO = listaEcoVNMO;
	}

	public List<Long> getListaBVNMO() {
		return listaBVNMO;
	}

	public void setListaBVNMO(List<Long> listaBVNMO) {
		this.listaBVNMO = listaBVNMO;
	}

	public List<Long> getListaCVNMO() {
		return listaCVNMO;
	}

	public void setListaCVNMO(List<Long> listaCVNMO) {
		this.listaCVNMO = listaCVNMO;
	}

	public List<Long> getListaCeroMotosVNMO() {
		return listaCeroMotosVNMO;
	}

	public void setListaCeroMotosVNMO(List<Long> listaCeroMotosVNMO) {
		this.listaCeroMotosVNMO = listaCeroMotosVNMO;
	}

	public List<Long> getListaEcoMotosVNMO() {
		return listaEcoMotosVNMO;
	}

	public void setListaEcoMotosVNMO(List<Long> listaEcoMotosVNMO) {
		this.listaEcoMotosVNMO = listaEcoMotosVNMO;
	}

	public List<Long> getListaBMotosVNMO() {
		return listaBMotosVNMO;
	}

	public void setListaBMotosVNMO(List<Long> listaBMotosVNMO) {
		this.listaBMotosVNMO = listaBMotosVNMO;
	}

	public List<Long> getListaCMotosVNMO() {
		return listaCMotosVNMO;
	}

	public void setListaCMotosVNMO(List<Long> listaCMotosVNMO) {
		this.listaCMotosVNMO = listaCMotosVNMO;
	}

	public List<BigDecimal> getListaCero() {
		return listaCero;
	}

	public void setListaCero(List<BigDecimal> listaCero) {
		this.listaCero = listaCero;
	}

	public List<BigDecimal> getListaEco() {
		return listaEco;
	}

	public void setListaEco(List<BigDecimal> listaEco) {
		this.listaEco = listaEco;
	}

	public List<BigDecimal> getListaB() {
		return listaB;
	}

	public void setListaB(List<BigDecimal> listaB) {
		this.listaB = listaB;
	}

	public List<BigDecimal> getListaC() {
		return listaC;
	}

	public void setListaC(List<BigDecimal> listaC) {
		this.listaC = listaC;
	}

	public List<BigDecimal> getListaCeroMotos() {
		return listaCeroMotos;
	}

	public void setListaCeroMotos(List<BigDecimal> listaCeroMotos) {
		this.listaCeroMotos = listaCeroMotos;
	}

	public List<BigDecimal> getListaEcoMotos() {
		return listaEcoMotos;
	}

	public void setListaEcoMotos(List<BigDecimal> listaEcoMotos) {
		this.listaEcoMotos = listaEcoMotos;
	}

	public List<BigDecimal> getListaBMotos() {
		return listaBMotos;
	}

	public void setListaBMotos(List<BigDecimal> listaBMotos) {
		this.listaBMotos = listaBMotos;
	}

	public List<BigDecimal> getListaCMotos() {
		return listaCMotos;
	}

	public void setListaCMotos(List<BigDecimal> listaCMotos) {
		this.listaCMotos = listaCMotos;
	}

	public Boolean getNoEsGenerable() {
		return noEsGenerable;
	}

	public void setNoEsGenerable(Boolean noEsGenerable) {
		this.noEsGenerable = noEsGenerable;
	}

	public Boolean getEsPrimeraImp() {
		return esPrimeraImp;
	}

	public void setEsPrimeraImp(Boolean esPrimeraImp) {
		this.esPrimeraImp = esPrimeraImp;
	}

	public Boolean getExisteTramiteDup() {
		return existeTramiteDup;
	}

	public void setExisteTramiteDup(Boolean existeTramiteDup) {
		this.existeTramiteDup = existeTramiteDup;
	}

	public List<String> getListaMatriculas() {
		return listaMatriculas;
	}

	public void setListaMatriculas(List<String> listaMatriculas) {
		this.listaMatriculas = listaMatriculas;
	}

	public String getNifTitularMasivo() {
		return nifTitularMasivo;
	}

	public void setNifTitularMasivo(String nifTitularMasivo) {
		this.nifTitularMasivo = nifTitularMasivo;
	}

	public List<Long> getListaCarsharingVNMO() {
		return listaCarsharingVNMO;
	}

	public void setListaCarsharingVNMO(List<Long> listaCarsharingVNMO) {
		this.listaCarsharingVNMO = listaCarsharingVNMO;
	}

	public List<BigDecimal> getListaCarsharing() {
		return listaCarsharing;
	}

	public void setListaCarsharing(List<BigDecimal> listaCarsharing) {
		this.listaCarsharing = listaCarsharing;
	}
}
