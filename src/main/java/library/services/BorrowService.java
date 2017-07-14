package library.services;


import library.domain.Borrow;

public interface BorrowService
{
	// Staff
	Borrow borrowMaterial(Borrow borrow);
	Borrow returnMaterial(Borrow borrow);

}
