package trafico.utiles;

import trafico.beans.TramiteTraficoMatriculacionBean;

public class DGTUtil {

	public static boolean isMatw(TramiteTraficoMatriculacionBean traficoTramiteMatriculacionBean) {
		if (traficoTramiteMatriculacionBean != null) {
			return traficoTramiteMatriculacionBean.getTipoTramiteMatriculacion() != null;
		} else {
			return false;
		}
	}

}
