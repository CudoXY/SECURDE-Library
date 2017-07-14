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
    public Review publishReview(String message, int userId, String materialId)
    {
        User u = new User();
        u.setId(userId);

        Material m = new Material();
        m.setId(materialId);

        Review r = new Review();
        r.setMaterial(m);
        r.setUser(u);
        r.setDateReviewed(new Timestamp(new Date().getTime()));
        r.setMessage(message);

        return reviewRepository.save(r);
    }
}
