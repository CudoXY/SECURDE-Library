package library.services.borrow;


import library.domain.Borrow;

public interface BorrowService
{
	// Staff
	Borrow requestBorrow(Borrow borrow);
	Borrow reserve(Borrow borrow);

	void cancelBorrow(String materialId, int userId);
	void cancelReserve(String materialId, int userId);

	Borrow getActiveBorrower(String materialId);
	Borrow getBorrowStatusByUser(String materialId, int userId);
	boolean isReserverByUser(String materialId, int userId);
	java.util.Date getEstimatedAvailabilityDate(String materialId);
	java.util.Date getEstimatedAvailabilityDateOfUser(String materialId, int userId);

	Borrow returnMaterial(Borrow borrow);
	Borrow getMaterialStatus(String materialId);
	Borrow getBorrowMaterialById(int id);
	void saveBorrow(Borrow borrow);

}
