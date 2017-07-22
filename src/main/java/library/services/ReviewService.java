package library.services;


import library.domain.Material;
import library.domain.Review;

public interface ReviewService
{
	Iterable<Review> getReviewList(String materialId);

	Iterable<Review> getReviewListDesc(String materialId);

	Review publishReview(Review review);

	//    Staff

}
