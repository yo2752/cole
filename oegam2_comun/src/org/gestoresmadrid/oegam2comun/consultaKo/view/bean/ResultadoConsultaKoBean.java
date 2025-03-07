package org.gestoresmadrid.oegam2comun.consultaKo.view.bean;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.consultaKo.model.vo.ConsultaKoVO;
import org.gestoresmadrid.core.docPermDistItv.model.vo.DocPermDistItvVO;

public class ResultadoConsultaKoBean implements Serializable{

	private static final long serialVersionUID = 1955708343293750468L;

	private Boolean error;
	private String mensaje;
	private Exception excepcion;
	private List<String> listaMensajes;
	private String nombreFichero;
	private Boolean esDescargable;
	private Date fechaGenExcel;
	private File fichero;
	private List<JefaturaTipoGenKoBean> listaKoJefaturas;
	private List<Long> listaIdsConsultaKoError;
	private List<ConsultaKoVO> listaConsultaKo; 
	public Boolean esZip;
	public ResumenConsultaKoBean resumen;
	private DocPermDistItvVO docPermDistItv;
	List<FicheroKoBean> listaFicheroKO;
	List<Long> listaIdsConsultaKO;

	public void addListaJefaturaTipoGenKoBean(ConsultaKoVO consultaKo){
		if (listaKoJefaturas == null || listaKoJefaturas.isEmpty()) {
			listaKoJefaturas = new ArrayList<>();
			JefaturaTipoGenKoBean jefaturaTipoGenKoBean = new JefaturaTipoGenKoBean();
			jefaturaTipoGenKoBean.setTipo(consultaKo.getTipo());
			jefaturaTipoGenKoBean.setTipoTramite(consultaKo.getTipoTramite());
			jefaturaTipoGenKoBean.setJefaturaProvincial(consultaKo.getJefatura());
			jefaturaTipoGenKoBean.addListaGenKoBean(consultaKo.getIdContrato(), consultaKo);
			listaKoJefaturas.add(jefaturaTipoGenKoBean);
		} else {
			Boolean existeJefaturaTipoTipoTramite = Boolean.FALSE;
			for (JefaturaTipoGenKoBean jefaturaTipoGenKoBean : listaKoJefaturas) {
				if (consultaKo.getTipo().equals(jefaturaTipoGenKoBean.getTipo()) && consultaKo.getTipoTramite().equals(jefaturaTipoGenKoBean.getTipoTramite())
						&& consultaKo.getJefatura().equals(jefaturaTipoGenKoBean.getJefaturaProvincial())) {
					jefaturaTipoGenKoBean.addListaGenKoBean(consultaKo.getIdContrato(), consultaKo);
					existeJefaturaTipoTipoTramite = Boolean.TRUE;
					break;
				}
			}
			if (!existeJefaturaTipoTipoTramite) {
				JefaturaTipoGenKoBean jefaturaTipoGenKoBean = new JefaturaTipoGenKoBean();
				jefaturaTipoGenKoBean.setJefaturaProvincial(consultaKo.getJefatura());
				jefaturaTipoGenKoBean.setTipo(consultaKo.getTipo());
				jefaturaTipoGenKoBean.setTipoTramite(consultaKo.getTipoTramite());
				jefaturaTipoGenKoBean.addListaGenKoBean(consultaKo.getIdContrato(), consultaKo);
				listaKoJefaturas.add(jefaturaTipoGenKoBean);
			}
		}
	}

	public void addListaIdsConsultaKOError(Long id) {
		if (listaIdsConsultaKoError == null || listaIdsConsultaKoError.isEmpty()) {
			listaIdsConsultaKoError = new ArrayList<>();
		}
		listaIdsConsultaKoError.add(id);
	}

	public void addListaIdsConsultaKOError(List<ConsultaKoVO> listaConsultaKo) {
		for (ConsultaKoVO consultaKoVO : listaConsultaKo) {
			addListaIdsConsultaKOError(consultaKoVO.getId());
		}
	}

	public void addListaIdsConsultaKO(Long id) {
		if (listaIdsConsultaKO == null || listaIdsConsultaKO.isEmpty()) {
			listaIdsConsultaKO = new ArrayList<>();
		}
		listaIdsConsultaKO.add(id);
	}

	public void addListaConsultaKO(ConsultaKoVO consultaKoVO) {
		if (listaConsultaKo == null || listaConsultaKo.isEmpty()) {
			listaConsultaKo = new ArrayList<>();
		}
		listaConsultaKo.add(consultaKoVO);
	}

	public void addListaFicheroKoBean(String nombreFichero, byte[] fichero) {
		if (listaFicheroKO == null || listaFicheroKO.isEmpty()) {
			listaFicheroKO = new ArrayList<>();
		}
		listaFicheroKO.add(new FicheroKoBean(nombreFichero, fichero));
	}

	public void addResumenError(String mensaje) {
		if (resumen == null) {
			resumen = new ResumenConsultaKoBean();
		}
		resumen.addListaKO(mensaje);
	}

	public void addResumenListaError(List<String> listaMensajeResumenError) {
		if (resumen == null) {
			resumen = new ResumenConsultaKoBean();
		}
		for (String mensaje : listaMensajeResumenError) {
			resumen.addListaKO(mensaje);
		}
	}

	public void addResumenOK(String mensaje) {
		if (resumen == null) {
			resumen = new ResumenConsultaKoBean();
		}
		resumen.addListaOk(mensaje);
	}

	public void addNombreFichero(String nombre) {
		if(nombreFichero == null || nombreFichero.isEmpty()) {
			nombreFichero = nombre;
		} else {
			nombreFichero += ";" +nombre;
		}
	}

	public ResultadoConsultaKoBean(Boolean error) {
		super();
		this.error = error;
		this.esDescargable = Boolean.FALSE;
	}
	public ResultadoConsultaKoBean() {
		this.error = Boolean.FALSE;;
		this.esDescargable = Boolean.FALSE;
	}

	public boolean isError() {
		return error;
	}
	public Boolean getError() {
		return error;
	}
	public void setError(Boolean error) {
		this.error = error;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public List<String> getListaMensajes() {
		return listaMensajes;
	}
	public void setListaMensajes(List<String> listaMensajes) {
		this.listaMensajes = listaMensajes;
	}
	public String getNombreFichero() {
		return nombreFichero;
	}
	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}
	public Boolean getEsDescargable() {
		return esDescargable;
	}
	public void setEsDescargable(Boolean esDescargable) {
		this.esDescargable = esDescargable;
	}
	public Date getFechaGenExcel() {
		return fechaGenExcel;
	}
	public void setFechaGenExcel(Date fechaGenExcel) {
		this.fechaGenExcel = fechaGenExcel;
	}
	public File getFichero() {
		return fichero;
	}
	public void setFichero(File fichero) {
		this.fichero = fichero;
	}

	public Boolean getEsZip() {
		return esZip;
	}

	public void setEsZip(Boolean esZip) {
		this.esZip = esZip;
	}

	public void addListaMensaje(String mensaje) {
		if (listaMensajes == null) {
			listaMensajes =  new ArrayList<>();
		}
		listaMensajes.add(mensaje);
	}

	public ResumenConsultaKoBean getResumen() {
		return resumen;
	}

	public void setResumen(ResumenConsultaKoBean resumen) {
		this.resumen = resumen;
	}

	public DocPermDistItvVO getDocPermDistItv() {
		return docPermDistItv;
	}

	public void setDocPermDistItv(DocPermDistItvVO docPermDistItv) {
		this.docPermDistItv = docPermDistItv;
	}

	public List<FicheroKoBean> getListaFicheroKO() {
		return listaFicheroKO;
	}

	public void setListaFicheroKO(List<FicheroKoBean> listaFicheroKO) {
		this.listaFicheroKO = listaFicheroKO;
	}

	public List<Long> getListaIdsConsultaKO() {
		return listaIdsConsultaKO;
	}

	public void setListaIdsConsultaKO(List<Long> listaIdsConsultaKO) {
		this.listaIdsConsultaKO = listaIdsConsultaKO;
	}

	public Exception getExcepcion() {
		return excepcion;
	}

	public void setExcepcion(Exception excepcion) {
		this.excepcion = excepcion;
	}

	public List<JefaturaTipoGenKoBean> getListaKoJefaturas() {
		return listaKoJefaturas;
	}

	public void setListaKoJefaturas(List<JefaturaTipoGenKoBean> listaKoJefaturas) {
		this.listaKoJefaturas = listaKoJefaturas;
	}

	public List<Long> getListaIdsConsultaKoError() {
		return listaIdsConsultaKoError;
	}

	public void setListaIdsConsultaKoError(List<Long> listaIdsConsultaKoError) {
		this.listaIdsConsultaKoError = listaIdsConsultaKoError;
	}

	public List<ConsultaKoVO> getListaConsultaKo() {
		return listaConsultaKo;
	}

	public void setListaConsultaKo(List<ConsultaKoVO> listaConsultaKo) {
		this.listaConsultaKo = listaConsultaKo;
	}

}