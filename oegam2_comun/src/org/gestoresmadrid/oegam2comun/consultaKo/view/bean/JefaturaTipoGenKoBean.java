package org.gestoresmadrid.oegam2comun.consultaKo.view.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.consultaKo.model.vo.ConsultaKoVO;

public class JefaturaTipoGenKoBean implements Serializable{

	private static final long serialVersionUID = -7063994607051501278L;

	String tipo;
	String tipoTramite;
	String jefaturaProvincial;
	List<GenKoBean> listaGenKo;

	public void addListaGenKoBean(Long idContrato, ConsultaKoVO consultaKo){
		if(listaGenKo == null || listaGenKo.isEmpty()){
			listaGenKo = new ArrayList<>();
			GenKoBean genKoBean = new GenKoBean();
			genKoBean.setIdContrato(idContrato);
			genKoBean.addListaConsultaKo(consultaKo);
			listaGenKo.add(genKoBean);
		} else {
			Boolean existeGenKo = Boolean.FALSE;
			for(GenKoBean genKoBean : listaGenKo){
				if(idContrato.compareTo(genKoBean.getIdContrato()) == 0){
					genKoBean.addListaConsultaKo(consultaKo);
					existeGenKo = Boolean.TRUE;
					break;
				}
			}
			if(!existeGenKo){
				GenKoBean genKoBean = new GenKoBean();
				genKoBean.setIdContrato(idContrato);
				genKoBean.addListaConsultaKo(consultaKo);
				listaGenKo.add(genKoBean);
			}
		}
	}

	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getTipoTramite() {
		return tipoTramite;
	}
	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}
	public List<GenKoBean> getListaGenKo() {
		return listaGenKo;
	}
	public void setListaGenKo(List<GenKoBean> listaGenKo) {
		this.listaGenKo = listaGenKo;
	}

	public String getJefaturaProvincial() {
		return jefaturaProvincial;
	}

	public void setJefaturaProvincial(String jefaturaProvincial) {
		this.jefaturaProvincial = jefaturaProvincial;
	}

}