package general.utiles;

import java.math.BigDecimal;

public enum TipoCreacionEnum {
	SIN_CLASIFICAR("Sin Clasificar"), OEGAM("Plataforma OEGAM"), IMPORTACION_XML("Importacion XML"), IMPORTACION_DGT("Importacion DGT"), IMPORTACION_SIGA("Importacion SIGA");

	private final String nombre;

	private TipoCreacionEnum(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public static TipoCreacionEnum getEnum(BigDecimal id) {
		for (TipoCreacionEnum e : values()) {
			if (e.ordinal() == id.intValue())
				return e;
		}
		return null;
	}
}
