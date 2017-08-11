package library.services;


import library.domain.Borrow;

public interface BorrowService
{
	// Staff
	Borrow borrowMaterial(Borrow borrow);
	Borrow returnMaterial(Borrow borrow);

	Borrow getMaterialStatus(String materialId);

	Borrow getBorrowMaterialById(int id);

	Borrow saveBorrow(Borrow borrow);

}
