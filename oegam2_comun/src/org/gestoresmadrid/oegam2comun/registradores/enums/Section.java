package org.gestoresmadrid.oegam2comun.registradores.enums;

import java.util.ArrayList;
import java.util.List;

public enum Section {
	ADMINISTRACION("sectionAdministracion", "Administración", null, "#"),
	CONTRATOS("sectionContratos", "Contratos", null, "#"),
	AYUDA("sectionAyuda", "¿Necesitas ayuda?", null, "#"),

	FINANCIADORES("sectionFinancialBacker", "Financiadores", ADMINISTRACION, "#"),
	ALTAFINANCIADORES("sectionAltaFinancialBacker", "Alta de financiadores", FINANCIADORES, "#"),
	LISTFINANCIADORES("sectionListFinancialBacker", "Listado de financiadores", FINANCIADORES, "#"),
	CACHE("sectionCache", "Administración de cachés", ADMINISTRACION, "#"),

	FINANCING("sectionFinancingDossier", "Financiación", CONTRATOS, "#"),
	LEASING("sectionLeasingDossier", "Leasing", CONTRATOS, "#"),
	RENTING("sectionRentingDossier", "Renting", CONTRATOS, "#"),
	IMPORT("sectionImport", "Importar", CONTRATOS, "#"),

	ALTAFINANCING("sectionAltaFinancingDossier", "Alta de contratos de financiación", FINANCING, "#"),
	LISTFINANCING("sectionListFinancingDossier", "Listado de contratos de financiación", FINANCING, "#"),
	ALTALEASING("sectionAltaLeasingDossier", "Alta de contratos de leasing", LEASING, "#"),
	LISTLESASING("sectionListLeasingDossier", "Listado de contratos de leasing", LEASING, "#"),
	ALTARENTING("sectionAltaRentingDossier", "Alta de contratos de renting", RENTING, "#"),
	LISTRENTING("sectionListRentingDossier", "Listado de contratos de renting", RENTING, "#"),
	IMPORTFINANCING("sectionImportFinancingDossier", "Importación de contratos de financiación", IMPORT, "#"),
	IMPORTLEASINGRENTING("sectionImportLeasingRentingDossier", "Importación de contratos de leasingRenting", IMPORT, "#");

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
