package org.gestoresmadrid.core.registradores.model.enumerados;

import org.gestoresmadrid.core.registradores.constantes.ConstantesRegistradores;

import utilidades.web.OegamExcepcion;

public enum EstadoTramiteRegistro {

	Iniciado("1", "Iniciado") {
		public String toString() {
			return "1";
		}
	},
	Pendiente("2", "Pdte. firmas") {
		public String toString() {
			return "2";
		}
	},
	Firmado("3", "Firmado") {
		public String toString() {
			return "3";
		}
	},
	Pendiente_Envio("4", "Pdte. de registro") {
		public String toString() {
			return "4";
		}
	},
	Finalizado_error("5", "Finalizado con error") {
		public String toString() {
			return "5";
		}
	},
	Entrada_Registro("6", "Entrada en registro") {
		public String toString() {
			return "6";
		}
	},
	Tramitandose_Presentacion("7", "Tramitándose presentación") {
		public String toString() {
			return "7";
		}
	},
	Pendiente_Firma_Acuse_Presentacion("8", "Pdte. firma acuse presentación") {
		public String toString() {
			return "8";
		}
	},
	Confirmada_Presentacion("9", "Confirmada Presentación") {
		public String toString() {
			return "9";
		}
	},
	Pendiente_Firma_Acuse_Inscripcion_Total("10", "Pdte. firma acuse inscripción total") {
		public String toString() {
			return "10";
		}
	},
	Finalizado("11", "Finalizado") {
		public String toString() {
			return "11";
		}
	},
	Pendiente_Firma_Acuse_Inscripcion_Parcial("12", "Pdte. firma acuse inscripción parcial") {
		public String toString() {
			return "12";
		}
	},
	Inscrito_Parcialmente("13", "Inscrito parcialmente") {
		public String toString() {
			return "13";
		}
	},
	Pendiente_Firma_Acuse_Calificado_Defectos("14", "Pdte. firma acuse calificación con defectos") {
		public String toString() {
			return "14";
		}
	},
	Calificado_Defectos("15", "Calificado con defectos") {
		public String toString() {
			return "15";
		}
	},
	Tramitandose_Denegacion("16", "Tramitándose denegación") {
		public String toString() {
			return "16";
		}
	},
	Pendiente_Firma_Acuse_Denegacion("17", "Pdte. firma acuse denegación") {
		public String toString() {
			return "17";
		}
	},
	Confirmada_Denegacion("18", "Confirmada Denegación") {
		public String toString() {
			return "18";
		}
	},
	Inscrito("19", "Inscrito") {
		public String toString() {
			return "19";
		}
	},
	Pendiente_Calificacion_Subsanacion("20", "Pdte. calificación subsanación") {
		public String toString() {
			return "20";
		}
	},
	Pendiente_Envio_AN_Denegacion("21", "Pdte. registro del acuse de denegación") {
		public String toString() {
			return "21";
		}
	},
	Pendiente_Envio_AN_Presentacion("22", "Pdte. registro del acuse de presentación") {
		public String toString() {
			return "22";
		}
	},
	Pendiente_Envio_AN_Inscripcion_Total("23", "Pdte. registro del acuse de inscripción total") {
		public String toString() {
			return "23";
		}
	},
	Pendiente_Envio_AN_Parcial("24", "Pdte. registro del acuse de calificación parcial") {
		public String toString() {
			return "24";
		}
	},
	Pendiente_Envio_AN_Defectos("25", "Pdte. registro del acuse de calificación con defectos") {
		public String toString() {
			return "25";
		}
	},
	Pendiente_Envio_Subsanacion("26", "Pdte. registro de la subsanación") {
		public String toString() {
			return "26";
		}
	},
	Pendiente_Respuesta_Sercon("27", "Pdte. respuesta sercon") {
		public String toString() {
			return "27";
		}
	},
	Validado("28", "Validado") {
		public String toString() {
			return "28";
		}
	},
	Anulado("29", "Anulado") {
		public String toString() {
			return "29";
		}
//	},
//	Finalizado_Con_Factura("30", "Pdte. confirmación de pago") {
//		public String toString() {
//			return "30";
//		}
//	},
//	Pendiente_Envio_AN_Confirmacion_Pago("31", "Pdte. registro confirmación de pago") {
//		public String toString() {
//			return "31";
//		}
	};

	private String valorEnum;
	private String nombreEnum;

	private EstadoTramiteRegistro(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static String convertirTexto(String valorEnum) {
		for (EstadoTramiteRegistro element : EstadoTramiteRegistro.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}

	public static boolean mensajeFueraSecuencia(String estadoAnterior, String estadoNuevo) throws OegamExcepcion {
		if (Iniciado.getValorEnum().equals(estadoNuevo)) {
			if (!Iniciado.getValorEnum().equals(estadoAnterior) && !Finalizado_error.getValorEnum().equals(estadoAnterior) && !Confirmada_Presentacion.getValorEnum().equals(estadoAnterior)
					&& !Inscrito_Parcialmente.getValorEnum().equals(estadoAnterior) && !Calificado_Defectos.getValorEnum().equals(estadoAnterior)) {
				return true;
			}
		} else if (Pendiente.getValorEnum().equals(estadoNuevo)) {
			if (Iniciado.getValorEnum().equals(estadoAnterior)) {
				return true;
			}
		} else if (Tramitandose_Presentacion.getValorEnum().equals(estadoNuevo)) {
			if (!Entrada_Registro.getValorEnum().equals(estadoAnterior)) {
				return true;
			}
		} else if (Tramitandose_Denegacion.getValorEnum().equals(estadoNuevo)) {
			if (!Entrada_Registro.getValorEnum().equals(estadoAnterior)) {
				return true;
			}
		} else if (Pendiente_Firma_Acuse_Presentacion.getValorEnum().equals(estadoNuevo)) {
			if (!Entrada_Registro.getValorEnum().equals(estadoAnterior) && !Tramitandose_Presentacion.getValorEnum().equals(estadoAnterior)) {
				return true;
			}
		} else if (Pendiente_Envio_AN_Presentacion.getValorEnum().equals(estadoNuevo)) {
			if (!Pendiente_Firma_Acuse_Presentacion.getValorEnum().equals(estadoAnterior)) {
				return true;
			}
		} else if (Confirmada_Presentacion.getValorEnum().equals(estadoNuevo)) {
			if (!Confirmada_Presentacion.getValorEnum().equals(estadoAnterior) && !Pendiente_Envio_AN_Presentacion.getValorEnum().equals(estadoAnterior)) {
				return true;
			}
		} else if (Pendiente_Firma_Acuse_Inscripcion_Total.getValorEnum().equals(estadoNuevo) || Pendiente_Firma_Acuse_Inscripcion_Parcial.getValorEnum().equals(estadoNuevo)
				|| Pendiente_Firma_Acuse_Calificado_Defectos.getValorEnum().equals(estadoNuevo)) {
			if (!Entrada_Registro.getValorEnum().equals(estadoAnterior)
					&& !Confirmada_Presentacion.getValorEnum().equals(estadoAnterior)
					&& !Inscrito_Parcialmente.getValorEnum().equals(estadoAnterior) 
					&& !Calificado_Defectos.getValorEnum().equals(estadoAnterior)
					&& !Inscrito.getValorEnum().equals(estadoAnterior)
					&& !Pendiente_Calificacion_Subsanacion.getValorEnum().equals(estadoAnterior)) {
				return true;
			}
		} else if (Pendiente_Envio_AN_Inscripcion_Total.getValorEnum().equals(estadoNuevo)) {
			if (!Pendiente_Firma_Acuse_Inscripcion_Total.getValorEnum().equals(estadoAnterior)) {
				return true;
			}
		} else if (Pendiente_Envio_AN_Parcial.getValorEnum().equals(estadoNuevo)) {
			if (!Pendiente_Firma_Acuse_Inscripcion_Parcial.getValorEnum().equals(estadoAnterior)) {
				return true;
			}
		} else if (Pendiente_Envio_AN_Defectos.getValorEnum().equals(estadoNuevo)) {
			if (!Pendiente_Firma_Acuse_Calificado_Defectos.getValorEnum().equals(estadoAnterior)) {
				return true;
			}
//		} else if (Pendiente_Envio_AN_Confirmacion_Pago.getValorEnum().equals(estadoNuevo)) {
//			if (!Pendiente_Confirmacion_Pago.getValorEnum().equals(estadoAnterior)) {
//				return true;
//			}
		} else if (Finalizado.getValorEnum().equals(estadoNuevo)) {
			if (!Inscrito.getValorEnum().equals(estadoAnterior)) {
				return true;
			}
		} else if (Inscrito_Parcialmente.getValorEnum().equals(estadoNuevo)) {
			if (!Pendiente_Firma_Acuse_Inscripcion_Parcial.getValorEnum().equals(estadoAnterior) && !Pendiente_Envio_AN_Parcial.getValorEnum().equals(estadoAnterior)) {
				return true;
			}
		} else if (Calificado_Defectos.getValorEnum().equals(estadoNuevo)) {
			if (!Pendiente_Firma_Acuse_Calificado_Defectos.getValorEnum().equals(estadoAnterior) && !Pendiente_Envio_AN_Defectos.getValorEnum().equals(estadoAnterior)) {
				return true;
			}
		} else if (Pendiente_Firma_Acuse_Denegacion.getValorEnum().equals(estadoNuevo)) {
			if (!Entrada_Registro.getValorEnum().equals(estadoAnterior) && !Tramitandose_Denegacion.getValorEnum().equals(estadoAnterior)) {
				return true;
			}
		} else if (Pendiente_Envio_AN_Denegacion.getValorEnum().equals(estadoAnterior)) {
			if (!Pendiente_Firma_Acuse_Denegacion.getValorEnum().equals(estadoAnterior)) {
				return true;
			}
		} else if (Confirmada_Denegacion.getValorEnum().equals(estadoNuevo)) {
			if (!Pendiente_Firma_Acuse_Denegacion.getValorEnum().equals(estadoAnterior) && !EstadoTramiteRegistro.Pendiente_Envio_AN_Denegacion.getValorEnum().equals(estadoAnterior)) {
				return true;
			}
		} else if (Inscrito.getValorEnum().equals(estadoNuevo)) {
			if (!Pendiente_Firma_Acuse_Inscripcion_Total.getValorEnum().equals(estadoAnterior) && !EstadoTramiteRegistro.Pendiente_Envio_AN_Inscripcion_Total.getValorEnum().equals(estadoAnterior)) {
				return true;
			}
		} else if (Pendiente_Calificacion_Subsanacion.getValorEnum().equals(estadoNuevo)) {
			if (!Pendiente_Envio_Subsanacion.getValorEnum().equals(estadoAnterior)) {
				return true;
			}
		} else if (Pendiente_Envio_Subsanacion.getValorEnum().equals(estadoNuevo)) {
			if (!Confirmada_Presentacion.getValorEnum().equals(estadoAnterior) && !Inscrito_Parcialmente.getValorEnum().equals(estadoAnterior)
					&& !Calificado_Defectos.getValorEnum().equals(estadoAnterior)) {
				return true;
			}
		} else if (Tramitandose_Presentacion.getValorEnum().equals(estadoNuevo)) {
			if (Pendiente_Firma_Acuse_Presentacion.getValorEnum().equals(estadoAnterior)) {
				return true;
			}
		} else if (Tramitandose_Denegacion.getValorEnum().equals(estadoNuevo)) {
			if (Pendiente_Firma_Acuse_Denegacion.getValorEnum().equals(estadoAnterior)) {
				return true;
			}
//		} else if (Pendiente_Confirmacion_Pago.getValorEnum().equals(estadoNuevo)) {
//			if (!Inscrito.getValorEnum().equals(estadoAnterior) || !Finalizado.getValorEnum().equals(estadoAnterior)) {
//				return true;
//			}
		}
		return false;
	}

	public static EstadoTramiteRegistro pendienteEnvioSegunEstado(String estado, String tipoMensaje) throws OegamExcepcion {
		if (Iniciado.getValorEnum().equals(estado)) {
			return Pendiente_Envio;
		} else if (Pendiente_Firma_Acuse_Calificado_Defectos.getValorEnum().equals(estado) || ConstantesRegistradores.TIPOMENSAJE_RESOLUCION_DEFECTOS_ACUSE.equalsIgnoreCase(tipoMensaje)) {
			return Pendiente_Envio_AN_Defectos;
		} else if (Pendiente_Firma_Acuse_Inscripcion_Parcial.getValorEnum().equals(estado) || ConstantesRegistradores.TIPOMENSAJE_RESOLUCION_PARCIAL_ACUSE.equalsIgnoreCase(tipoMensaje)) {
			return Pendiente_Envio_AN_Parcial;
		} else if (Pendiente_Firma_Acuse_Inscripcion_Total.getValorEnum().equals(estado) || ConstantesRegistradores.TIPOMENSAJE_RESOLUCION_TOTAL_ACUSE.equalsIgnoreCase(tipoMensaje)) {
			return Pendiente_Envio_AN_Inscripcion_Total;
		} else if (Pendiente_Firma_Acuse_Presentacion.getValorEnum().equals(estado)) {
			return Pendiente_Envio_AN_Presentacion;
		} else if (Confirmada_Presentacion.getValorEnum().equals(estado)) {
			return Pendiente_Envio_Subsanacion;
		} else if (Pendiente_Firma_Acuse_Denegacion.getValorEnum().equals(estado)) {
			return Pendiente_Envio_AN_Denegacion;
//		} else if (Pendiente_Confirmacion_Pago.getValorEnum().equals(estado)) {
//			return Pendiente_Envio_AN_Confirmacion_Pago;
		} else {
			return null;
		}
	}

	public static EstadoTramiteRegistro relacionarMensaje(String tipo, boolean esFactura) throws OegamExcepcion {
		if (esFactura) {
			return Finalizado;
		} else if (tipo.equalsIgnoreCase(ConstantesRegistradores.TIPOMENSAJE_ASIENTO_DENEGACION)) {
			return Tramitandose_Denegacion;
		} else if (tipo.equalsIgnoreCase(ConstantesRegistradores.TIPOMENSAJE_ASIENTO_DENEGACION_CONFIRMACION)) {
			return Pendiente_Firma_Acuse_Denegacion;
		} else if (tipo.equalsIgnoreCase(ConstantesRegistradores.TIPOMENSAJE_ASIENTO_ACEPTACION)) {
			return Tramitandose_Presentacion;
		} else if (tipo.equalsIgnoreCase(ConstantesRegistradores.TIPOMENSAJE_ASIENTO_ACEPTACION_CONFIRMACION)) {
			return Pendiente_Firma_Acuse_Presentacion;
		} else if (tipo.equalsIgnoreCase(ConstantesRegistradores.TIPOMENSAJE_RESOLUCION_TOTAL)) {
			return Pendiente_Firma_Acuse_Inscripcion_Total;
		} else if (tipo.equalsIgnoreCase(ConstantesRegistradores.TIPOMENSAJE_RESOLUCION_PARCIAL)) {
			return Pendiente_Firma_Acuse_Inscripcion_Parcial;
		} else if (tipo.equalsIgnoreCase(ConstantesRegistradores.TIPOMENSAJE_RESOLUCION_DEFECTOS)) {
			return Pendiente_Firma_Acuse_Calificado_Defectos;
		} else if (tipo.equalsIgnoreCase(ConstantesRegistradores.TIPOMENSAJE_FACTURA)) {
			return Finalizado;
		} else {
			return null;
		}
	}

	public static EstadoTramiteRegistro estadoTrasAcuse(String estado, String tipoTramite) throws OegamExcepcion {
		if (Pendiente_Envio_AN_Defectos.getValorEnum().equals(estado)) {
			return Calificado_Defectos;
		} else if (Pendiente_Envio_AN_Denegacion.getValorEnum().equals(estado)) {
			return Confirmada_Denegacion;
		} else if (Pendiente_Envio_AN_Parcial.getValorEnum().equals(estado)) {
			return Inscrito_Parcialmente;
//		} else if (Pendiente_Envio_AN_Inscripcion_Total.getValorEnum().equals(estado) && TipoTramiteRegistro.MODELO_6.getValorEnum().equals(tipoTramite)) {
//			return Finalizado;
		} else if (Pendiente_Envio_AN_Inscripcion_Total.getValorEnum().equals(estado)) {
			return Inscrito;
		} else if (Pendiente_Envio_AN_Presentacion.getValorEnum().equals(estado)) {
			return Confirmada_Presentacion;
//		} else if (Pendiente_Envio_AN_Confirmacion_Pago.getValorEnum().equals(estado)) {
//			return Finalizado;
		} else {
			return null;
		}
	}
}
