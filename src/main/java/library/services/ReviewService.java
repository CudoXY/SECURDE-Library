package library.services;


import library.domain.Review;

public interface ReviewService
{
	Review publishReview(String message, int userId, String materialId);

	//    Staff

}
