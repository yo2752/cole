package trafico.beans;

public class BotonPaginaConsultaBean {

	private String name;
	private String id;
	private String onclick;
	private String value;

	private String claseCss;
	private String type;
	private String onKeyPress;

	private final String ONKEYPRESS = "this.onClick";
	private final String CLASS = "botonMasGrande";
	private final String TYPE = "button";

	public BotonPaginaConsultaBean(String name, String id, String value, String onclick) {
		this.name = name;
		this.id = id;
		this.value = value;
		this.onclick = onclick;
		this.onKeyPress = ONKEYPRESS;
		this.claseCss = CLASS;
		this.type = TYPE;
	}

	@Override
	public String toString() {
		return "<input class=\"" + this.claseCss + "\" type=\"" + this.type + "\" id=\"" + this.id + 
				"\" name=\"" + this.name + "\" value=\"" + this.value + "\" onClick=\"" + this.onclick + 
				"\" onKeyPress=\"" + this.onKeyPress + "\" />";
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOnclick() {
		return onclick;
	}
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getClaseCss() {
		return claseCss;
	}
	public void setClaseCss(String claseCss) {
		this.claseCss = claseCss;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOnKeyPress() {
		return onKeyPress;
	}
	public void setOnKeyPress(String onKeyPress) {
		this.onKeyPress = onKeyPress;
	}

}