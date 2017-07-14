package library.controllers;

import library.domain.Material;
import library.services.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ManageMaterialController
{
	private MaterialService materialService;

	@Autowired
	public void setMaterialService(MaterialService materialService) {
		this.materialService = materialService;
	}

	@RequestMapping("/manage/book")
	public String viewMaterial(Model model)
	{
		model.addAttribute("materialList", materialService.getMaterialList());
		model.addAttribute("material", new Material());
		System.out.println("viewMaterial");
		return "admin/admin_books";
	}

	@RequestMapping(value = "/manage/book/add", method = RequestMethod.POST)
	public String addMaterial(Material material)
	{
		materialService.saveMaterial(material);
		System.out.println("addMaterial");
		return "redirect:/manage/book";
	}
}
