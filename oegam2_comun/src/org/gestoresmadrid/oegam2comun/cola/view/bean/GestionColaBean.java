package org.gestoresmadrid.oegam2comun.cola.view.bean;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.pagination.model.component.impl.PaginatedListImpl;
import org.gestoresmadrid.oegam2comun.envioData.view.dto.EnvioDataDto;

public class GestionColaBean implements Serializable {

	private static final long serialVersionUID = 8516947557459480537L;

	private Boolean existeBusqueda = Boolean.FALSE;;

	private PaginatedListImpl listaSolicitudesCola;
	private int pageSolicitudesCola;
	private String sortSolicitudesCola;
	private String dirSolicitudesCola;
	private int resultadosPorPaginaSolicitudesCola;

	private List<EnvioDataDto> listaEjecucionesProceso;
	private List<EnvioDataDto> listaEjecucionesProcesoPorCola;

	private PaginatedListImpl listaUltimaEjecucion;
	private int pageUltimaEjecuion;
	private String sortUltimaEjecuion;
	private String dirUltimaEjecuion;
	private int resultadosPorPaginaUltimaEjecucion;

	private Boolean errorPetCola = Boolean.FALSE;
	private Boolean errorUltEjecCola = Boolean.FALSE;

	public GestionColaBean() {
		super();
		pageSolicitudesCola = 1;
		pageUltimaEjecuion = 1;
		dirSolicitudesCola = "asc";
		dirUltimaEjecuion = "asc";
		resultadosPorPaginaUltimaEjecucion = 5;
		resultadosPorPaginaSolicitudesCola = 5;
	}

	public List<EnvioDataDto> getListaEjecucionesProceso() {
		return listaEjecucionesProceso;
	}

	public void setListaEjecucionesProceso(List<EnvioDataDto> listaEjecucionesProceso) {
		this.listaEjecucionesProceso = listaEjecucionesProceso;
	}

	public List<EnvioDataDto> getListaEjecucionesProcesoPorCola() {
		return listaEjecucionesProcesoPorCola;
	}

	public void setListaEjecucionesProcesoPorCola(List<EnvioDataDto> listaEjecucionesProcesoPorCola) {
		this.listaEjecucionesProcesoPorCola = listaEjecucionesProcesoPorCola;
	}

	public PaginatedListImpl getListaSolicitudesCola() {
		return listaSolicitudesCola;
	}

	public void setListaSolicitudesCola(PaginatedListImpl listaSolicitudesCola) {
		this.listaSolicitudesCola = listaSolicitudesCola;
	}

	public int getPageSolicitudesCola() {
		return pageSolicitudesCola;
	}

	public void setPageSolicitudesCola(int pageSolicitudesCola) {
		this.pageSolicitudesCola = pageSolicitudesCola;
	}

	public String getSortSolicitudesCola() {
		return sortSolicitudesCola;
	}

	public void setSortSolicitudesCola(String sortSolicitudesCola) {
		this.sortSolicitudesCola = sortSolicitudesCola;
	}

	public String getDirSolicitudesCola() {
		return dirSolicitudesCola;
	}

	public void setDirSolicitudesCola(String dirSolicitudesCola) {
		this.dirSolicitudesCola = dirSolicitudesCola;
	}

	public int getResultadosPorPaginaSolicitudesCola() {
		return resultadosPorPaginaSolicitudesCola;
	}

	public void setResultadosPorPaginaSolicitudesCola(int resultadosPorPaginaSolicitudesCola) {
		this.resultadosPorPaginaSolicitudesCola = resultadosPorPaginaSolicitudesCola;
	}

	public PaginatedListImpl getListaUltimaEjecucion() {
		return listaUltimaEjecucion;
	}

	public void setListaUltimaEjecucion(PaginatedListImpl listaUltimaEjecucion) {
		this.listaUltimaEjecucion = listaUltimaEjecucion;
	}

	public int getPageUltimaEjecuion() {
		return pageUltimaEjecuion;
	}

	public void setPageUltimaEjecuion(int pageUltimaEjecuion) {
		this.pageUltimaEjecuion = pageUltimaEjecuion;
	}

	public String getSortUltimaEjecuion() {
		return sortUltimaEjecuion;
	}

	public void setSortUltimaEjecuion(String sortUltimaEjecuion) {
		this.sortUltimaEjecuion = sortUltimaEjecuion;
	}

	public String getDirUltimaEjecuion() {
		return dirUltimaEjecuion;
	}

	public void setDirUltimaEjecuion(String dirUltimaEjecuion) {
		this.dirUltimaEjecuion = dirUltimaEjecuion;
	}

	public int getResultadosPorPaginaUltimaEjecucion() {
		return resultadosPorPaginaUltimaEjecucion;
	}

	public void setResultadosPorPaginaUltimaEjecucion(int resultadosPorPaginaUltimaEjecucion) {
		this.resultadosPorPaginaUltimaEjecucion = resultadosPorPaginaUltimaEjecucion;
	}

	public Boolean getExisteBusqueda() {
		return existeBusqueda;
	}

	public void setExisteBusqueda(Boolean existeBusqueda) {
		this.existeBusqueda = existeBusqueda;
	}

	public Boolean getErrorPetCola() {
		return errorPetCola;
	}

	public void setErrorPetCola(Boolean errorPetCola) {
		this.errorPetCola = errorPetCola;
	}

	public Boolean getErrorUltEjecCola() {
		return errorUltEjecCola;
	}

	public void setErrorUltEjecCola(Boolean errorUltEjecCola) {
		this.errorUltEjecCola = errorUltEjecCola;
	}

}
