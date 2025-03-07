package org.gestoresmadrid.oegam2comun.impresion.masiva.model.service;

import org.gestoresmadrid.oegam2comun.impresion.masiva.view.dto.ImpresionMasivaDto;

import escrituras.beans.ResultBean;

public interface ServicioImpresionMasiva {

	ImpresionMasivaDto getImpresionMasivaPorNombreFichero(String nombreFichero);

	void eliminar(String nombreFichero);

	void guardar(ImpresionMasivaDto impresionMasivaDto);

	void cambiarEstadoImpresion(ImpresionMasivaDto impresionMasivaDto);

	ResultBean guardarImpresionMasiva(String path, String name,	String fechaHora, String tipoImpreso, String numColegiado);
}
