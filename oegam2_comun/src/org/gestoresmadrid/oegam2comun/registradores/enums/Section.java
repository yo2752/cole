package org.gestoresmadrid.oegam2comun.registradores.enums;

import java.util.ArrayList;
import java.util.List;

public enum Section {
	ADMINISTRACION("sectionAdministracion", "Administraci�n", null, "#"),
	CONTRATOS("sectionContratos", "Contratos", null, "#"),
	AYUDA("sectionAyuda", "�Necesitas ayuda?", null, "#"),

	FINANCIADORES("sectionFinancialBacker", "Financiadores", ADMINISTRACION, "#"),
	ALTAFINANCIADORES("sectionAltaFinancialBacker", "Alta de financiadores", FINANCIADORES, "#"),
	LISTFINANCIADORES("sectionListFinancialBacker", "Listado de financiadores", FINANCIADORES, "#"),
	CACHE("sectionCache", "Administraci�n de cach�s", ADMINISTRACION, "#"),

	FINANCING("sectionFinancingDossier", "Financiaci�n", CONTRATOS, "#"),
	LEASING("sectionLeasingDossier", "Leasing", CONTRATOS, "#"),
	RENTING("sectionRentingDossier", "Renting", CONTRATOS, "#"),
	IMPORT("sectionImport", "Importar", CONTRATOS, "#"),

	ALTAFINANCING("sectionAltaFinancingDossier", "Alta de contratos de financiaci�n", FINANCING, "#"),
	LISTFINANCING("sectionListFinancingDossier", "Listado de contratos de financiaci�n", FINANCING, "#"),
	ALTALEASING("sectionAltaLeasingDossier", "Alta de contratos de leasing", LEASING, "#"),
	LISTLESASING("sectionListLeasingDossier", "Listado de contratos de leasing", LEASING, "#"),
	ALTARENTING("sectionAltaRentingDossier", "Alta de contratos de renting", RENTING, "#"),
	LISTRENTING("sectionListRentingDossier", "Listado de contratos de renting", RENTING, "#"),
	IMPORTFINANCING("sectionImportFinancingDossier", "Importaci�n de contratos de financiaci�n", IMPORT, "#"),
	IMPORTLEASINGRENTING("sectionImportLeasingRentingDossier", "Importaci�n de contratos de leasingRenting", IMPORT, "#");

	private String idSection;
	private String nombreEnum;
	private Section parent;
	private String link;
	
	private Section(String idSection, String nombreEnum, Section parent, String link) {
		this.idSection = idSection;
		this.nombreEnum = nombreEnum;
		this.parent = parent;
		this.link = link;
	}

	public String getKey() {
		return idSection;
	}

	@Override
	public String toString() {
		return nombreEnum;
	}

	public String getLink() {
		return link;
	}

	public Section getParent() {
		return parent;
	}

	public List<Section> getParents() {
		List<Section> result;
		if (parent != null) {
			result = parent.getParents();
		} else {
			result = new ArrayList<Section>();
		}
		result.add(this);
		return result;
	}

}
