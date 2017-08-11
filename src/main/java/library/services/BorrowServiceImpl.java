package library.services;

import library.domain.Borrow;
import library.repositories.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class BorrowServiceImpl implements BorrowService {
    private BorrowRepository borrowRepository;

    @Autowired
    public void setBorrowRepository(BorrowRepository borrowRepository) {
        this.borrowRepository = borrowRepository;
    }

    @Override
    public Borrow borrowMaterial(Borrow borrow)
    {
        borrow.setDateBorrowed(new Timestamp(new java.util.Date().getTime()));
        System.out.println("\tborrowMaterial = " + borrow.getBorrower().getId());
        return borrowRepository.save(borrow);
    }

    @Override
    public Borrow returnMaterial(Borrow borrow)
    {
        borrow.setDateReturned(new Timestamp(new java.util.Date().getTime()));
        return borrowRepository.save(borrow);
    }

    @Override
    public Borrow getMaterialStatus(String materialId) {
        return borrowRepository.findFirstByMaterial_IdOrderByDateBorrowedDesc(materialId);
    }

    @Override
    public Borrow getBorrowMaterialById(int id) {
        return borrowRepository.findById(id);
    }

    @Override
    public Borrow saveBorrow(Borrow borrow) {
        return borrowRepository.save(borrow);
    }


}
