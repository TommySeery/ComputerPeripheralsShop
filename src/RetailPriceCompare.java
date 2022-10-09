import java.util.Comparator;

public class RetailPriceCompare implements Comparator<Product>{
	public int compare(Product p1, Product p2) {
		return (p1.getRetailPrice() > p2.getRetailPrice()) ? 1 : (p1.getRetailPrice() < p2.getRetailPrice() ? -1 : 0);
	}
}
