package org.gestoresmadrid.oegam2comun.modelos.view.dto;

import java.util.ArrayList;
import java.util.List;

public class ChartDataDto {

	List<String> labels;
	List<CharDataset> datasets;

	public ChartDataDto(String... dataLabel) {
		labels = new ArrayList<String>();
		datasets = new ArrayList<ChartDataDto.CharDataset>();
		for (String label : dataLabel) {
			datasets.add(new CharDataset(label));
		}
	}

	public ChartDataDto addData(String label, Number data, String backgroundColor, String borderColor) {
		for (CharDataset dataset : datasets) {
			if (label.equals(dataset.getLabel())) {
				dataset.addBar(data, backgroundColor, borderColor);
				break;
			}
		}
		return this;
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public List<CharDataset> getDatasets() {
		return datasets;
	}

	public void setDatasets(List<CharDataset> datasets) {
		this.datasets = datasets;
	}

	public class CharDataset {
		String label;
		List<Number> data;
		List<String> backgroundColor;
		List<String> borderColor;
		int borderWidth = 1;

		public CharDataset(String label) {
			this.label = label;
			data = new ArrayList<Number>();
			backgroundColor = new ArrayList<String>();
			borderColor = new ArrayList<String>();
		}

		public void addBar(Number data, String backgroundColor, String borderColor) {
			this.data.add(data);
			this.backgroundColor.add(backgroundColor);
			this.borderColor.add(borderColor);
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public List<Number> getData() {
			return data;
		}

		public void setData(List<Number> data) {
			this.data = data;
		}

		public List<String> getBackgroundColor() {
			return backgroundColor;
		}

		public void setBackgroundColor(List<String> backgroundColor) {
			this.backgroundColor = backgroundColor;
		}

		public List<String> getBorderColor() {
			return borderColor;
		}

		public void setBorderColor(List<String> borderColor) {
			this.borderColor = borderColor;
		}

		public int getBorderWidth() {
			return borderWidth;
		}

		public void setBorderWidth(int borderWidth) {
			this.borderWidth = borderWidth;
		}

	}
}
