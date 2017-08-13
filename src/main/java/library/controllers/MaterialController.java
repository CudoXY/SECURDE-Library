package library.controllers;

import library.domain.Borrow;
import library.domain.Review;
import library.domain.User;
import library.domain.helper.BorrowAction;
import library.domain.helper.Filter;
import library.domain.helper.SearchFilter;
import library.domain.helper.UserHelper;
import library.services.BorrowService;
import library.services.MaterialService;
import library.services.ReviewService;
import library.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Timestamp;
import java.util.Date;

@Controller
@RequestMapping(value = "/catalog")
public class MaterialController
{
	@Autowired
	private BorrowService borrowService;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private MaterialService materialService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search(
			@RequestParam(value = "q", required = false, defaultValue = "") String query, @RequestParam(value = "filterBy", required = true) int filterBy, Model model)
	{
		Filter f = new Filter();
		f.setSearchFilter(SearchFilter.fromInt(filterBy));
		f.setQuery(query);

		model.addAttribute("materialList", materialService.search(f));
		model.addAttribute("filter", f);

		return "user/material_list";
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String load(
			@RequestParam(value = "category_id", required = false, defaultValue = "-1") int categoryId, Model model)
	{

		if (categoryId == -1)
		{
			model.addAttribute("materialList", materialService.getMaterialList());
		}
		else
		{
			model.addAttribute("materialList", materialService.getMaterialListByCategory(categoryId));
		}

		return "user/material_list";
	}


	@RequestMapping("/view")
	public String view(
			@RequestParam(value = "id") String materialId,
			Model model)
	{
		User u = UserHelper.getCurrentUser(userService);
		Borrow activeBorrowStatus = borrowService.getActiveBorrower(materialId);
		boolean isAvailable = activeBorrowStatus == null;

		BorrowAction borrowAction;

		if (isAvailable)
			borrowAction = BorrowAction.BORROW;
		else
		{
			if (u != null && activeBorrowStatus.getBorrower().getId() == u.getId())
			{
				borrowAction = activeBorrowStatus.isReleased() ? BorrowAction.RELEASED : BorrowAction.CANCELBORROW;
				model.addAttribute("returnDate", activeBorrowStatus.getExpectedReturnDate());
			}
			else
			{
				Borrow borrowStatus = u == null ? null : borrowService.getBorrowStatusByUser(materialId, u.getId());
				Date estimatedAvailabilityDate;
				if (borrowStatus == null)
				{
					borrowAction = BorrowAction.RESERVE;
					estimatedAvailabilityDate = borrowService.getEstimatedAvailabilityDate(materialId);
					System.out.println("estimatedAvailabilityDate = " + estimatedAvailabilityDate);
				}
				else
				{
					borrowAction = BorrowAction.CANCELRESERVE;
					estimatedAvailabilityDate = borrowService.getEstimatedAvailabilityDateOfUser(materialId, u.getId());
				}

				model.addAttribute("estimatedAvailabilityDate", estimatedAvailabilityDate);
			}
		}

		model.addAttribute("borrowAction", borrowAction);

		if (u != null)
		{
			boolean canUserReview = reviewService.canUserReview(materialId, u.getId());
			if (canUserReview)
				model.addAttribute("review", new Review());
			model.addAttribute("canUserReview", canUserReview);
		}

		model.addAttribute("material", materialService.getMaterialById(materialId));
		model.addAttribute("reviewList", reviewService.getReviewListDesc(materialId));
		return "user/material_view";
	}


	@PreAuthorize("hasAnyRole('STUDENT', 'FACULTY')")
	@RequestMapping(value = "/borrow", method = RequestMethod.POST)
	public String borrow(String materialId, RedirectAttributes redirectAttributes)
	{
		borrowService.borrow(createBorrowRequest(materialId));

		redirectAttributes.addFlashAttribute("materialId", materialId);
		return "redirect:/catalog/view?id=" + materialId;
	}


	@PreAuthorize("hasAnyRole('STUDENT', 'FACULTY')")
	@RequestMapping(value = "/reserve", method = RequestMethod.POST)
	public String reserve(String materialId, RedirectAttributes redirectAttributes)
	{
		borrowService.reserve(createBorrowRequest(materialId));

		redirectAttributes.addFlashAttribute("materialId", materialId);
		return "redirect:/catalog/view?id=" + materialId;
	}


	@PreAuthorize("hasAnyRole('STUDENT', 'FACULTY')")
	@RequestMapping(value = "/borrow/cancel", method = RequestMethod.POST)
	public String cancelBorrow(String materialId, RedirectAttributes redirectAttributes)
	{
		User u = UserHelper.getCurrentUser(userService);

		borrowService.cancelBorrow(materialId, u.getId());

		redirectAttributes.addFlashAttribute("materialId", materialId);
		return "redirect:/catalog/view?id=" + materialId;
	}


	@PreAuthorize("hasAnyRole('STUDENT', 'FACULTY')")
	@RequestMapping(value = "/reserve/cancel", method = RequestMethod.POST)
	public String cancelReserve(String materialId, RedirectAttributes redirectAttributes)
	{
		User u = UserHelper.getCurrentUser(userService);

		borrowService.cancelReserve(materialId, u.getId());

		redirectAttributes.addFlashAttribute("materialId", materialId);
		return "redirect:/catalog/view?id=" + materialId;
	}

	private Borrow createBorrowRequest(String materialId)
	{
		User user = UserHelper.getCurrentUser(userService);

		Borrow borrow = new Borrow();
		borrow.setMaterial(materialService.getMaterialById(materialId));

		borrow.setBorrower(user);

		return borrow;
	}


	@PreAuthorize("hasAnyRole('STUDENT', 'FACULTY')")
	@RequestMapping(value = "/review", method = RequestMethod.POST)
	public String review(Review review, RedirectAttributes redirectAttributes)
	{
		User user = UserHelper.getCurrentUser(userService);
		String materialId = review.getMaterial().getId();

		review.setMaterial(materialService.getMaterialById(materialId));
		review.setDateReviewed(new Timestamp(new Date().getTime()));
		review.setUser(user);

		reviewService.publishReview(review);
		System.out.println("review");

		redirectAttributes.addFlashAttribute("materialId", materialId);
		return "redirect:/catalog/view?id=" + materialId;
	}
//
//    @RequestMapping("product/edit/{id}")
//    public String edit(@PathVariable Integer id, Model model){
//        model.addAttribute("product", materialService.getProductById(id));
//        return "productform";
//    }
//
//    @RequestMapping("product/new")
//    public String newProduct(Model model){
//        model.addAttribute("product", new Product());
//        return "productform";
//    }
//
//    @RequestMapping(value = "product", method = RequestMethod.POST)
//    public String saveProduct(Product product){
//
//        materialService.saveProduct(product);
//
//        return "redirect:/product/" + product.getId();
//    }

}
