package org.gestoresmadrid.oegam2comun.impresion.remesas.service;

import java.io.Serializable;

import escrituras.beans.ResultBean;

public interface ServicioImpresionRemesas extends Serializable{

	ResultBean comprobarDatosMinimos(String codSeleccionados, String impreso);

	ResultBean comprobarPermisosRemesas(String codSeleccionados, Boolean tienePermisoAdmin, Long idContrato);

	ResultBean comprobarImpresion(String codSeleccionados);

	ResultBean imprimir(String codSeleccionados, String tipoFichero);

}
