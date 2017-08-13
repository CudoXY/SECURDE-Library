package library.services.review;

import library.domain.Review;
import library.repositories.BorrowRepository;
import library.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class ReviewServiceImpl implements ReviewService
{

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private BorrowRepository borrowRepository;

	@Override
	public Iterable<Review> getReviewList(String materialId)
	{
		return reviewRepository.findAllByMaterial_Id(materialId);
	}

	@Override
	public Iterable<Review> getReviewListDesc(String materialId)
	{
		return reviewRepository.findAllByMaterial_IdOrderByDateReviewedDesc(materialId);
	}

	@Override
	public Review publishReview(Review review)
	{
		if (!canUserReview(review.getMaterial().getId(), review.getUser().getId()))
		{
			// TODO: Error
			return null;
		}
		review.setDateReviewed(new Timestamp(new Date().getTime()));

		return reviewRepository.save(review);
	}

	@Override
	public boolean canUserReview(String materialId, int userId)
	{
		return borrowRepository.findFirstByMaterial_IdAndBorrower_IdAndDateBorrowedIsNotNullAndIsReleasedIsTrue(materialId, userId) != null;
	}
}
