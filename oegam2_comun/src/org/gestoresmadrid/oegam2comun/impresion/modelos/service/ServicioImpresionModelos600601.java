package org.gestoresmadrid.oegam2comun.impresion.modelos.service;

import java.io.Serializable;

import escrituras.beans.ResultBean;

public interface ServicioImpresionModelos600601 extends Serializable{

	ResultBean comprobaDatosObligatorios(String codSeleccionados, String impreso);

	ResultBean comprobarPermisosModelos(String codSeleccionados, Boolean tienePermisoAdmin, Long idContrato);

	ResultBean comprobarImpresion(String codSeleccionados);

	ResultBean imprimir(String codSeleccionados, String tipoFichero);

}
