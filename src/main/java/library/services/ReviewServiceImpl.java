package library.services;

import library.domain.Material;
import library.domain.Review;
import library.domain.User;
import library.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;

    @Autowired
    public void setReviewRepository(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

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
        review.setDateReviewed(new Timestamp(new Date().getTime()));

        return reviewRepository.save(review);
    }
}
