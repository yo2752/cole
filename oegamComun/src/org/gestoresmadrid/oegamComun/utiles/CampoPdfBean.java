package org.gestoresmadrid.oegamComun.utiles;

import com.itextpdf.text.Image;

public class CampoPdfBean {

	private static final int _13 = 13;
	private String nombre;
	private String valorTexto;
	private Image valorImagen;
	private boolean imagen;
	private float posX;
	private float posY;
	private boolean negrita;
	private boolean cursiva;
	private float tamFuente;
	private int pagina;
	private String fuente = null;

	public CampoPdfBean(String nombre, String valor, float tamFuente) {
		this(nombre, valor, false, false, tamFuente);
	}

	public CampoPdfBean(String nombre, String valor) {
		this(nombre, valor, false);
	}

	public CampoPdfBean(String nombre, String valor, boolean negrita) {
		this(nombre, valor, negrita, false, _13);
	}

	// SINCODIG Nueva función
	public CampoPdfBean(String nombre, String valor, boolean negrita, boolean cursiva, float tamFuente, String fuente) {
		this(nombre, valor, negrita, cursiva, tamFuente, 0, 0, 0);
		this.fuente = fuente;
	}

	public CampoPdfBean(String nombre, String valor, boolean negrita, boolean cursiva, float tamFuente) {
		this(nombre, valor, negrita, cursiva, tamFuente, 0, 0, 0);
	}

	public CampoPdfBean(String nombre, String valor, boolean negrita, boolean cursiva, float tamFuente, int pagina, float posX, float posY) {
		this.imagen = false;
		this.nombre = nombre;
		this.valorTexto = valor;
		this.negrita = negrita;
		this.cursiva = cursiva;
		this.tamFuente = tamFuente;
		this.pagina = pagina;
		this.posX = posX;
		this.posY = posY;
	}

	public CampoPdfBean(String nombre, Image imagen, float posX, float posY, int pagina) {
		this.imagen = true;
		this.nombre = nombre;
		this.valorImagen = imagen;
		this.posX = posX;
		this.posY = posY;
		this.pagina = pagina;
	}

	public CampoPdfBean() {
		init();
	}

	protected void init() {
		imagen = false;
	}

	public boolean isImagen() {
		return imagen;
	}

	public void setImagen(boolean imagen) {
		this.imagen = imagen;
	}

	public boolean isNegrita() {
		return negrita;
	}

	public void setNegrita(boolean negrita) {
		this.negrita = negrita;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public float getPosX() {
		return posX;
	}

	public void setPosX(float posX) {
		this.posX = posX;
	}

	public float getPosY() {
		return posY;
	}

	public void setPosY(float posY) {
		this.posY = posY;
	}

	public Image getValorImagen() {
		return valorImagen;
	}

	public void setValorImagen(Image valorImagen) {
		this.valorImagen = valorImagen;
	}

	public String getValorTexto() {
		return valorTexto;
	}

	public void setValorTexto(String valorTexto) {
		this.valorTexto = valorTexto;
	}

	public float getTamFuente() {
		return tamFuente;
	}

	public void setTamFuente(float tamFuente) {
		this.tamFuente = tamFuente;
	}

	public boolean isCursiva() {
		return cursiva;
	}

	public void setCursiva(boolean cursiva) {
		this.cursiva = cursiva;
	}

	public int getPagina() {
		return pagina;
	}

	public void setPagina(int pagina) {
		this.pagina = pagina;
	}

	public String getFuente() {
		return fuente;
	}

	public void setFuente(String fuente) {
		this.fuente = fuente;
	}
}
