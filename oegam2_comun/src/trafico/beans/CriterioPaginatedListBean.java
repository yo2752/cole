package trafico.beans;

public class CriterioPaginatedListBean {

	private String sort;
	private String dir;
	private int page=0;

	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	public void setPage(int page){
		this.page=page;
	}
	public int getPage(){
		return page;
	}

}