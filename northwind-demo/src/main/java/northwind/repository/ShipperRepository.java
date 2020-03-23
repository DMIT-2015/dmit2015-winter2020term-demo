package northwind.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import northwind.entity.Shipper;

@ApplicationScoped
@Transactional
public class ShipperRepository {
	
	@PersistenceContext(unitName = "mssql-jpa-pu")
	private EntityManager em;

	public void add(Shipper newShipper) {
		em.persist(newShipper);
	}
	
	public void update(Shipper existingShipper) {
		em.merge(existingShipper);
		em.flush();
	}
	
	public void remove(Shipper existingShipper) {
		em.remove(em.merge(existingShipper));
		em.flush();
	}
	
	public void remove(int ShipperID) {
		Shipper existingCategoy = findById(ShipperID);
		remove(existingCategoy);
	}
	
	public Shipper findById(int ShipperID) {
		return em.find(Shipper.class, ShipperID);
	}
	
	public List<Shipper> findAll() {
		return em.createQuery(
				"SELECT s FROM Shipper s ORDER BY s.companyName"
			, Shipper.class)
			.getResultList();
	}
}
