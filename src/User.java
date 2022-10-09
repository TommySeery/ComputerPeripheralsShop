public abstract class User{

	protected int id;
	protected String username;
	protected String name;
	protected Address addr;
	
	public User(int id, String username, String name, Address addr) {
		this.id=id;
		this.username=username;
		this.name=name;
		this.addr=addr;
	}
	
	public String getAddress() {
		return addr.toString();
	}
}
