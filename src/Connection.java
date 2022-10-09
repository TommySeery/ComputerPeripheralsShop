public enum Connection {
	WIRED ("wired"),
	WIRELESS ("wireless");
	
	private String conn;
	
	Connection(String conn) {
		this.conn=conn;
	}

	public String toString() {
		return conn;
	}
}