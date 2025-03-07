package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Posibles tipos de contrato
 */
public enum ClaseDerecho {

	AD("Adjudicación"),
	AF("Afección fiscal"), 
	AR("Arrendamiento"), 
	CA("Concesión administrativa"),
	CR("Condición resolutoria"), 
	CS("Condición suspensiva"),
	DE("Demanda"),
	DX("Dominio expectante"),
	EM("Embargo"),
	EV("Expectante de viudedad"), 
	FA("Fadiga (retracto)"), 
	HE("Hereditario"),
	HI("Hipoteca"), 
	LE("Leasing"), 
	OP("Opción"), 
	OC("Opción de compra"),
	PA("Pacto de reserva de dominio"), 
	PR("Pacto de retro"), 
	PI("Pignoraticio"),
	PO("Posesión"), 
	PD("Prohibición de disponer"), 
	PB("Propiedad del bien"),
	RV("Renta vitalicia"), 
	RD("Reserva de dominio"), 
	RR("Reserva de rango"), 
	RL("Reserva lineal"), 
	RE("Retención"), 
	RC("Retracto convencional"), 
	RG("Retracto legal"), 
	SE("Servidumbre"), 
	SU("Subarriendo"),
	SH("Subhipoteca"), 
	SF("Sustitución fideicomisaria"), 
	TA("Tanteo"), 
	TE("Titularidad Expectante"), 
	US("Uso"),
	VC("Ventas a cartas de gracia (con retracto)"),
	AP("Adjudicación para pago de deudas");


	private String nombreEnum;

	private ClaseDerecho(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}

	@Override
	public String toString() {
		return nombreEnum;
	}
	
	public String getKey(){
		return name();
	}

}
