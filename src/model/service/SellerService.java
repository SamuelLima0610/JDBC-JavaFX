package model.service;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;


public class SellerService {
	
	private SellerDao dao = DaoFactory.createSellerDao();
	
	public List<Seller> findAll(){
		return dao.findAll();
	}
	
	public void saveOrUpdate(Seller obj) {
		if(obj.getId() != null) {
			dao.update(obj);
		}else {
			dao.insert(obj);
		}
	}
	
	public void delete(Seller obj) {
		dao.deleteById(obj.getId());
	}
}
