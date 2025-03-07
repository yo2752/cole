package org.gestoresmadrid.oegam2comun.docbase.view.dto.jaxb;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("DatosDocBaseListadoDto")
public class DatosDocBaseListadoDto {
	
	private List<DatosDocBaseDto> listaDatosDocBaseDto;
	
	public DatosDocBaseListadoDto () {
		listaDatosDocBaseDto = new ArrayList<DatosDocBaseDto>();
	}

	public List<DatosDocBaseDto> getListaDatosDocBaseDto() {
		return listaDatosDocBaseDto;
	}

	public void setListaDatosDocBaseDto(List<DatosDocBaseDto> listaDatosDocBaseDto) {
		this.listaDatosDocBaseDto = listaDatosDocBaseDto;
	}

}
