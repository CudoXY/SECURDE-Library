package library.controllers;

import library.domain.Borrow;
import library.services.BorrowService;
import library.services.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MaterialController
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

    @RequestMapping(value = "/catalog", method = RequestMethod.GET)
    public String get(
            @RequestParam(value = "category_id", required = false, defaultValue = "-1") int categoryId, Model model){

        if (categoryId  == -1)
            model.addAttribute("materialList", materialService.getMaterialList());
        else
            model.addAttribute("materialList", materialService.getMaterialListByCategory(categoryId));

        return "user/material_list";
    }


    @RequestMapping("/catalog/view")
    public String view(
            @RequestParam(value = "id") String materialId,
            Model model)
    {
        System.out.println("before viewMaterial");
        model.addAttribute("material", materialService.getMaterialById(materialId));
        System.out.println("viewMaterial");
        return "user/material_view";
    }


    @RequestMapping(value = "/view/borrow", method = RequestMethod.POST)
    public String borrow(Borrow borrow)
    {
        borrowService.borrowMaterial(borrow);
        System.out.println("borrowMaterial");
        return "redirect:/view?id=" + borrow.getId();
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
