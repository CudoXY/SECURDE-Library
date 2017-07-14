package library.services;


import library.domain.MaterialReserve;

public interface MaterialReserveService
{
	MaterialReserve reserveMaterial(String materialId, int userId);

	void unreserveMaterial(MaterialReserve reserve);

	//    Staff

}
