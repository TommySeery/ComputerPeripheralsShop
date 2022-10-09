
public enum MouseType {
	STANDARD("standard"),
	GAMING("gaming");
	
	private String type;
	
	MouseType(String type) {
		this.type=type;
	}
	
	public String toString() {
		return type;
	}
}
