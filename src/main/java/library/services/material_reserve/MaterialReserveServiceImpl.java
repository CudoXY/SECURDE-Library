package library.services.material_reserve;

import library.domain.Material;
import library.domain.MaterialReserve;
import library.domain.User;
import library.repositories.MaterialReserveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class MaterialReserveServiceImpl implements MaterialReserveService
{
	private MaterialReserveRepository materialReserveRepository;

	@Autowired
	public void setBorrowRepository(MaterialReserveRepository materialReserveRepository)
	{
		this.materialReserveRepository = materialReserveRepository;
	}


	@Override
	public MaterialReserve reserveMaterial(String materialId, int userId)
	{
		User u = new User();
		u.setId(userId);

		Material m = new Material();
		m.setId(materialId);

		MaterialReserve r = new MaterialReserve();
		r.setBorrower(u);
		r.setMaterial(m);
		r.setDateReserved(new Timestamp(new Date().getTime()));

		return materialReserveRepository.save(r);
	}

	@Override
	public void unreserveMaterial(MaterialReserve reserve)
	{
		materialReserveRepository.delete(reserve);
	}
}
