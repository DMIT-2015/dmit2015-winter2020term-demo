package northwind.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the [Order Details] database table.
 * 
 */
@Embeddable
public class OrderDetailPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="OrderID", insertable=false, updatable=false)
	private int orderID;

	@Column(name="ProductID", insertable=false, updatable=false)
	private int productID;

	public OrderDetailPK() {
	}
	public int getOrderID() {
		return this.orderID;
	}
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	public int getProductID() {
		return this.productID;
	}
	public void setProductID(int productID) {
		this.productID = productID;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof OrderDetailPK)) {
			return false;
		}
		OrderDetailPK castOther = (OrderDetailPK)other;
		return 
			(this.orderID == castOther.orderID)
			&& (this.productID == castOther.productID);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.orderID;
		hash = hash * prime + this.productID;
		
		return hash;
	}
}