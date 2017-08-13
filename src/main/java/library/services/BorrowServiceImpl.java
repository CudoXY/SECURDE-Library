package library.services;

import library.domain.Borrow;
import library.domain.Role;
import library.repositories.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.util.resources.cldr.ebu.CurrencyNames_ebu;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

@Service
public class BorrowServiceImpl implements BorrowService {
    private BorrowRepository borrowRepository;

    @Autowired
    public void setBorrowRepository(BorrowRepository borrowRepository) {
        this.borrowRepository = borrowRepository;
    }

    @Override
    public Borrow borrow(Borrow borrow)
    {
        borrow.setDateBorrowed(new Date(new java.util.Date().getTime()));
        System.out.println("\tborrow = " + borrow.getBorrower().getId());
        return borrowRepository.save(borrow);
    }

    @Override
    public Borrow reserve(Borrow borrow)
    {
        return borrowRepository.save(borrow);
    }

    @Override
    public Borrow returnMaterial(Borrow borrow)
    {
        borrow.setDateReturned(new Date(new java.util.Date().getTime()));
        return borrowRepository.save(borrow);
    }

    @Override
    public void cancelBorrow(String materialId, int userId)
    {
        Borrow b = getActiveBorrower(materialId);

        if (b.getBorrower().getId() != userId)
        {
            // TODO:
            return;
        }

        borrowRepository.delete(b);
    }

    @Override
    public void cancelReserve(String materialId, int userId)
    {
        if (!isReserverByUser(materialId, userId))
            return;

        Borrow b = borrowRepository.findFirstByMaterial_IdAndBorrower_IdAndDateBorrowedIsNullAndDateReturnedIsNull(materialId, userId);
        borrowRepository.delete(b);
    }

    @Override
    public Borrow getActiveBorrower(String materialId)
    {
        return borrowRepository.findFirstByMaterial_IdAndDateBorrowedIsNotNullAndDateReturnedIsNull(materialId);
    }

    @Override
    public Borrow getBorrowStatusByUser(String materialId, int userId)
    {
        return borrowRepository.findFirstByMaterial_IdAndBorrower_IdAndDateBorrowedIsNullAndDateReturnedIsNull(materialId, userId);
    }

    @Override
    public boolean isReserverByUser(String materialId, int userId)
    {
        return getBorrowStatusByUser(materialId, userId) != null;
    }

    @Override
    public java.util.Date getEstimatedAvailabilityDate(String materialId)
    {
        return getEstimatedAvailabilityDateOfUser(materialId, 0);
    }

    @Override
    public java.util.Date getEstimatedAvailabilityDateOfUser(String materialId, int userId)
    {
        Borrow currentBorrow = borrowRepository.findFirstByMaterial_IdAndDateBorrowedIsNotNullAndDateReturnedIsNull(materialId);

        // Available
        if (currentBorrow == null)
            return null;

        List<Borrow> pendingBorrowList = borrowRepository.findAllByMaterial_IdAndDateBorrowedIsNull(materialId);

        Calendar cal = Calendar.getInstance();
        cal.setTime(currentBorrow.getExpectedReturnDate());

        for (int i = 0; i < pendingBorrowList.size(); i++)
        {
            if (pendingBorrowList.get(i).getBorrower().getId() == userId)
                break;

            cal.add(Calendar.DAY_OF_WEEK, pendingBorrowList.get(i).getBorrower().getRole() == Role.ROLE_STUDENT ? 7 : 14);
        }

        return cal.getTime();
    }
}
