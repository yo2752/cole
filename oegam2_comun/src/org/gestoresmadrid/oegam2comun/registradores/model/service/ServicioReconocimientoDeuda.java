package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.ReconocimientoDeudaDto;

public interface ServicioReconocimientoDeuda extends Serializable{

	public ResultRegistro getReconocimientoDeuda(String id);
	public ResultRegistro guardarOActualizarReconocimientoDeuda(ReconocimientoDeudaDto reconocimientoDeudaDto, long idDatosFinancieros);
	public ResultRegistro borrarReconocimientoDeuda(String id);
	public ResultRegistro validarReconocimientoDeuda(ReconocimientoDeudaDto reconocimientoDeudaDto);

}
