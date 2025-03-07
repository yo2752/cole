package trafico.entidadesFinancieras;

import java.math.BigDecimal;


public enum EstadoConsultaEitv {


	PENDIENTE_ENVIO(new BigDecimal("1"), "PENDIENTE_ENVIO"){
		public String toString() {
			return "Pendiente de envío";
		}
	},
	FINALIZADO(new BigDecimal("2"), "FINALIZADO"){
		public String toString() {
			return "Finalizado";
		}
	},
	FINALIZADO_ERROR(new BigDecimal("3"), "FINALIZADO_ERROR"){
		public String toString() {
			return "Finalizado con error";
		}
	};

	private BigDecimal valorEnum;
	private String nombreEnum;

	private EstadoConsultaEitv(BigDecimal valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public BigDecimal getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static EstadoConsultaEitv convertir(BigDecimal valorEnum) {    

		if(valorEnum == null)
			return null;

		switch (valorEnum.intValue()) {

		case 2:
			return PENDIENTE_ENVIO;

		case 3:
			return FINALIZADO;

		case 4:
			return FINALIZADO_ERROR;

		default:
			return null;
		}
	}

}
