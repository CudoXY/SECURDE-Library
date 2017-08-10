package library.controllers;

import library.domain.Borrow;
import library.domain.Material;
import library.domain.Review;
import library.domain.User;
import library.domain.helper.Filter;
import library.domain.helper.SearchFilter;
import library.services.BorrowService;
import library.services.MaterialService;
import library.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

	@PersistenceContext
	private EntityManager em;

//	@RequestMapping("/catalog")
//	public String viewCatalog(
//			@RequestParam(value = "c", required = false) Integer categoryId,
//			@RequestParam(value = "search", required = false) String search,
//			@RequestParam(value = "by", required = false) String by,
//			Model model)
//	{
//		model.addAttribute("message", "Catalog poh");
//		model.addAttribute("parameter", categoryId + " " + search + " " + by);
//		System.out.println("viewCatalog");
//		return "user/material_view";
//	}

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
		System.out.println("before viewMaterial");
		model.addAttribute("material", materialService.getMaterialById(materialId));
		model.addAttribute("review", new Review());
		System.out.println("viewMaterial");

		model.addAttribute("reviewList", reviewService.getReviewListDesc(materialId));
		return "user/material_view";
	}


	@PreAuthorize("hasAuthority('STUDENT')")
	@RequestMapping(value = "/borrow", method = RequestMethod.POST)
	public String borrow(String materialId)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = em.getReference(User.class, 11427817);

		Borrow borrow = new Borrow();
		borrow.setMaterial(em.getReference(Material.class, materialId));

		borrow.setBorrower(user);
		borrowService.borrowMaterial(borrow);
		System.out.println("borrowMaterial");
		return "redirect:";
	}


	@RequestMapping(value = "/review", method = RequestMethod.POST)
	public String review(Review review, @CookieValue(value = "userId", defaultValue = "-1") int userId)
	{
		User user = em.getReference(User.class, 11427817);

		review.setMaterial(em.getReference(Material.class, review.getMaterial().getId()));
		review.setDateReviewed(new Timestamp(new Date().getTime()));
		review.setUser(user);

		reviewService.publishReview(review);
		System.out.println("review");
		return "redirect:";
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
