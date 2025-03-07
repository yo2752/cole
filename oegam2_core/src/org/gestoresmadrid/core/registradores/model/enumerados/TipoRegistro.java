package org.gestoresmadrid.core.registradores.model.enumerados;

import java.math.BigInteger;

public enum TipoRegistro {

	REGISTRO_MERCANTIL("RM", "Registro mercantil", BigInteger.ONE) {
		public String toString() {
			return "MERCANTIL";
		}
	},
	REGISTRO_PROPIEDAD("RP", "Registro propiedad", new BigInteger("2")) {
		public String toString() {
			return "PROPIEDAD";
		}
	},
	REGISTRO_RBM("RB", "Registro bienes muebles", new BigInteger("3")) {
		public String toString() {
			return "RB";
		}
	};

	private String valorEnum;
	private String nombreEnum;
	private BigInteger numeroEnum;

	private TipoRegistro(String valorEnum, String nombreEnum, BigInteger numeroEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
		this.numeroEnum = numeroEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public BigInteger getNumeroEnum() {
		return numeroEnum;
	}

	public static TipoRegistro convertir(String valorEnum) {
		if ("RM".equals(valorEnum))
			return REGISTRO_MERCANTIL;
		if ("RP".equals(valorEnum))
			return REGISTRO_PROPIEDAD;
		if ("RB".equals(valorEnum))
			return REGISTRO_RBM;
		return null;
	}

	public static TipoRegistro convertirTipoTramiteToTipoRegistro(String tipoTramite) {
		if (TipoTramiteRegistro.MODELO_6.getValorEnum().equals(tipoTramite)) {
			return REGISTRO_PROPIEDAD;
		}else if (TipoTramiteRegistro.MODELO_7.getValorEnum().equals(tipoTramite) || TipoTramiteRegistro.MODELO_8.getValorEnum().equals(tipoTramite) || TipoTramiteRegistro.MODELO_9.getValorEnum().equals(tipoTramite) || TipoTramiteRegistro.MODELO_10.getValorEnum().equals(tipoTramite) || TipoTramiteRegistro.MODELO_13.getValorEnum().equals(tipoTramite)){
			return REGISTRO_RBM;
		} else {
			return REGISTRO_MERCANTIL;
		}
	}
}