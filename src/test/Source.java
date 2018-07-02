package test;

public class Source {
	private String fruit;
	private String sugar;
	private String size;
	@Override
	public String toString() {
		return "Source [fruit=" + fruit + ", sugar=" + sugar + ", size=" + size + "]";
	}
	public String getFruit() {
		return fruit;
	}
	public void setFruit(String fruit) {
		this.fruit = fruit;
	}
	public String getSugar() {
		return sugar;
	}
	public void setSugar(String sugar) {
		this.sugar = sugar;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
}
