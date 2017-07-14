package library.controllers;

import library.domain.Borrow;
import library.services.BorrowService;
import library.services.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CatalogController
{
	private BorrowService borrowService;
	private MaterialService materialService;

	@Autowired
	public void setBorrowService(BorrowService borrowService) {
		this.borrowService = borrowService;
	}

	@Autowired
	public void setMaterialService(MaterialService materialService) {
		this.materialService = materialService;
	}

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


	@RequestMapping("/view")
	public String viewMaterial(
			@RequestParam(value = "id") String materialId,
			Model model)
	{
		System.out.println("before viewMaterial");
		model.addAttribute("material", materialService.getMaterialById(materialId));
		System.out.println("viewMaterial");
		return "user/material_view";
	}


	@RequestMapping(value = "/view/borrow", method = RequestMethod.POST)
	public String borrowMaterial(Borrow borrow)
	{
		borrowService.borrowMaterial(borrow);
		System.out.println("borrowMaterial");
		return "redirect:/view?id=" + borrow.getId();
	}
}
