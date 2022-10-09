
public enum KeyboardLayout {
	UK("uk"),
	US("us");

	private String layout;

	KeyboardLayout(String layout) {
		this.layout=layout;
	}
	
	public String toString() {
		return layout;
	}
	
}
