package trafico.beans.utiles;

import trafico.beans.daos.BeanPQDatosColegio;
import escrituras.beans.ColegioBean;
import general.beans.RespuestaGenerica;

public class ColegioBeanPQConversion {

	private static final String P_NOMBRE = "P_NOMBRE";
	private static final String P_CORREO_ELECTRONICO = "P_CORREO_ELECTRONICO";
	private static final String P_COLEGIO = "P_COLEGIO";
	private static final String P_CIF = "P_CIF";

	public static BeanPQDatosColegio convertirColegioToPQ(ColegioBean colegioBean) {
		return null;
	}

	public static ColegioBean convertirPQToColegio(RespuestaGenerica  resultadoDatosColegio) {

		ColegioBean colegioBean = new ColegioBean();

		colegioBean.setCif((String) resultadoDatosColegio.getParametro(P_CIF));
		colegioBean.setColegio((String) resultadoDatosColegio.getParametro(P_COLEGIO));
		colegioBean.setNombre((String) resultadoDatosColegio.getParametro(P_NOMBRE));

		if (null == resultadoDatosColegio.getParametro(P_CORREO_ELECTRONICO)) {
			colegioBean.setCorreoElectronico("");
		} else {
			colegioBean.setCorreoElectronico((String) resultadoDatosColegio.getParametro(P_CORREO_ELECTRONICO));
		}

		return colegioBean;

	}

}