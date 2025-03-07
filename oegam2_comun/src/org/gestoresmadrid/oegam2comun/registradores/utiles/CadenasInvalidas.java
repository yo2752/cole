package org.gestoresmadrid.oegam2comun.registradores.utiles;

/*
 **
 * En esta clase se implementa una lista de cadenas inválidas.
 */

@SuppressWarnings("serial")
public final class CadenasInvalidas extends java.util.ArrayList<String> {

	/**
	 * Constructor
	 */
	public CadenasInvalidas() {
		setDefaultValues();
	}

	/**
	 * Establecemos la lista de cadenas por defecto
	 */
	private void setDefaultValues() {
		add("%");
		add("*");
		add("+");
		add("<");
		add(">");
		add("$");
		add("DLL");
		add("EXECUTE");
		add(" INSERT");
		add(" UPDATE");
		add("SHELL");
		add("SELECT ");
		add("INSERT ");
		add("UPDATE ");
		add(" IMPORT ");
		add("DELETE ");
		add("FROM ");
		add("DROP ");
		add("UNION ");
		add("JOIN ");
		add(" DELETE");
		add(" FROM");
		add(" DROP");
		add(" UNION");
		add(" JOIN");
		add("VIEW ");
		add("CREATE ");
		add("ALTER ");
		add("TABLE ");
		add("GRANT ");
		add(" VIEW");
		add(" CREATE");
		add(" ALTER");
		add(" TABLE");
		add(" GRANT");
		add("SCRIPT");
		add("DOCUMENT");
		add("COOKIE");
		add("FORWARD");
		add("SEND");
		add("REDIRECT");
		add("HTML");
		add("HEAD");
		add("BODY");
		add("FORM");
		add("IMPORT ");
		add("POST");
		add(":");
		add("SYSTEM");
		add(" SELECT");
		add("qt;");

		// lowercase
		add("shell");
		add("select ");
		add("insert ");
		add("update ");
		add("dll");
		add("delete ");
		add("from ");
		add("drop ");
		add("union ");
		add("join ");
		add(" delete");
		add(" from");
		add(" drop");
		add(" union");
		add(" join");
		add("view ");
		add("create ");
		add("alter ");
		add("table ");
		add("grant ");
		add(" view");
		add(" create");
		add(" alter");
		add(" table");
		add(" grant");
		add("script");
		add("document");
		add("cookie");
		add("forward");
		add("send");
		add("redirect");
		add("html");
		add("head");
		add("body");
		add("form");
		add("import ");
		add("post");
		add("system");
		add(" select");
		add("execute");
		add(" insert");
		add(" update");
		add(" import ");
	}
}
