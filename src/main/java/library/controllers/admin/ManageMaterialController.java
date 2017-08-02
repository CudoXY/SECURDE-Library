package library.controllers.admin;

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

	@RequestMapping(value = "/manage/material", method = RequestMethod.GET)
	String load(Model model)
	{
		model.addAttribute("materialList", materialService.getMaterialList());
		return "admin/admin_all_materials";
	}
}
