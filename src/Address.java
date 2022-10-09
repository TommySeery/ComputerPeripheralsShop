
public class Address {
	private int houseNo;
	private String postcode;
	private String city;
	
	public Address(int houseNo, String postcode, String city) {
		this.houseNo=houseNo;
		this.postcode=postcode;
		this.city=city;
	}
	
	public String toString() {
		return houseNo + " " + city + ", " + postcode;
	}
	
}
