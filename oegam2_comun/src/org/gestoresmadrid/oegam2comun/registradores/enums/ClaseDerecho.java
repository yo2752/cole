package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Posibles tipos de contrato
 */
public enum ClaseDerecho {

	AD("Adjudicaci�n"),
	AF("Afecci�n fiscal"), 
	AR("Arrendamiento"), 
	CA("Concesi�n administrativa"),
	CR("Condici�n resolutoria"), 
	CS("Condici�n suspensiva"),
	DE("Demanda"),
	DX("Dominio expectante"),
	EM("Embargo"),
	EV("Expectante de viudedad"), 
	FA("Fadiga (retracto)"), 
	HE("Hereditario"),
	HI("Hipoteca"), 
	LE("Leasing"), 
	OP("Opci�n"), 
	OC("Opci�n de compra"),
	PA("Pacto de reserva de dominio"), 
	PR("Pacto de retro"), 
	PI("Pignoraticio"),
	PO("Posesi�n"), 
	PD("Prohibici�n de disponer"), 
	PB("Propiedad del bien"),
	RV("Renta vitalicia"), 
	RD("Reserva de dominio"), 
	RR("Reserva de rango"), 
	RL("Reserva lineal"), 
	RE("Retenci�n"), 
	RC("Retracto convencional"), 
	RG("Retracto legal"), 
	SE("Servidumbre"), 
	SU("Subarriendo"),
	SH("Subhipoteca"), 
	SF("Sustituci�n fideicomisaria"), 
	TA("Tanteo"), 
	TE("Titularidad Expectante"), 
	US("Uso"),
	VC("Ventas a cartas de gracia (con retracto)"),
	AP("Adjudicaci�n para pago de deudas");


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
