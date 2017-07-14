package library.controllers;

import library.services.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MaterialController
{

    private MaterialService materialService;

    @Autowired
    public void setMaterialService(MaterialService materialService) {
        this.materialService = materialService;
    }

    @RequestMapping(value = "/catalog", method = RequestMethod.GET)
    public String list(Model model){
        model.addAttribute("materialList", materialService.getMaterialList());
        System.out.println("Returning products:");
        return "user/material_list";
    }

    @RequestMapping("catalog/{categoryId}")
    public String listByCategory(@PathVariable Integer categoryId, Model model){
        model.addAttribute("materialList", materialService.getMaterialListByCategory(categoryId));
        return "user/material_list";
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
