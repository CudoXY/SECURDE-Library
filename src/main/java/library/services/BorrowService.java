package library.services;


import library.domain.Borrow;

public interface BorrowService
{
	// Staff
	Borrow borrow(Borrow borrow);
	Borrow reserve(Borrow borrow);
	Borrow returnMaterial(Borrow borrow);

	void cancelBorrow(String materialId, int userId);
	void cancelReserve(String materialId, int userId);

	Borrow getActiveBorrower(String materialId);
	Borrow getBorrowStatusByUser(String materialId, int userId);
	boolean isReserverByUser(String materialId, int userId);
	java.util.Date getEstimatedAvailabilityDate(String materialId);
	java.util.Date getEstimatedAvailabilityDateOfUser(String materialId, int userId);

}
