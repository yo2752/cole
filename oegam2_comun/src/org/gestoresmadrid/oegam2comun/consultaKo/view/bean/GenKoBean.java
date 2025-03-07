package org.gestoresmadrid.oegam2comun.consultaKo.view.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.consultaKo.model.vo.ConsultaKoVO;

public class GenKoBean implements Serializable{

	private static final long serialVersionUID = 1472797376806291789L;

	Long idContrato;
	List<ConsultaKoVO> listaConsultaKo;

	public void addListaConsultaKo(ConsultaKoVO consultaKo) {
		if(listaConsultaKo == null || listaConsultaKo.isEmpty()) {
			listaConsultaKo = new ArrayList<>();
		}
		listaConsultaKo.add(consultaKo);
	}

	public Long getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}
	public List<ConsultaKoVO> getListaConsultaKo() {
		return listaConsultaKo;
	}
	public void setListaConsultaKo(List<ConsultaKoVO> listaConsultaKo) {
		this.listaConsultaKo = listaConsultaKo;
	}

}