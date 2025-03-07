package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;

public class ResultadoNavegacionEITVBean {
	
	private List<TramiteTrafMatrVO> listaTramitesMatrVO;
	
	private List<Integer> listaCifOK;
	
	private List<Integer> listaCifKO;
	
	private boolean error;
	
	private HashMap<String, byte[]> listaFichas;

	public List<TramiteTrafMatrVO> getListaTramitesMatrVO() {
		return listaTramitesMatrVO;
	}

	public void setListaTramitesMatrVO(List<TramiteTrafMatrVO> listaTramitesMatrVO) {
		this.listaTramitesMatrVO = listaTramitesMatrVO;
	}

	public List<Integer> getListaCifOK() {
		return listaCifOK;
	}

	public void setListaCifOK(List<Integer> listaCifOK) {
		this.listaCifOK = listaCifOK;
	}
	
	public void addListaCifOK() {
		if(listaCifOK==null){
			listaCifOK= new ArrayList<Integer>();
		}
		this.listaCifOK.add(1);
	}
	
	public int getOKs() {
		return this.listaCifOK.size();
	}
	
	public void addListaCifKO() {
		if(listaCifKO==null){
			listaCifKO= new ArrayList<Integer>();
		}
		this.listaCifKO.add(1);
	}
	
	public int getKOs() {
		return this.listaCifKO.size();
	}


	public List<Integer> getListaCifKO() {
		return listaCifKO;
	}

	public void setListaCifKO(List<Integer> listaCifKO) {
		this.listaCifKO = listaCifKO;
	}

	public HashMap<String, byte[]> getListaFichas() {
		return listaFichas;
	}

	public void setListaFichas(HashMap<String, byte[]> listaFichas) {
		this.listaFichas = listaFichas;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

}
