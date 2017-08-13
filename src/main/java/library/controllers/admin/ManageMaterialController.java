package library.controllers.admin;

import library.domain.Borrow;
import library.domain.Material;
import library.services.borrow.BorrowService;
import library.services.material.MaterialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.sql.Date;

@Controller
public class ManageMaterialController
{
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	private MaterialService materialService;
	private BorrowService borrowService;

	@Autowired
	public void setMaterialService(MaterialService materialService) {
		this.materialService = materialService;
	}

	@Autowired
	public void setMaterialService(BorrowService borrowService) {
		this.borrowService = borrowService;
	}

	@RequestMapping(value = "/manage/material", method = RequestMethod.GET)
	public String load(
			@RequestParam(value = "category_id", required = false, defaultValue = "-1") int categoryId, Model model)
	{
		model.addAttribute("savMaterial", new Material());
		model.addAttribute("upMaterial", new Material());
		model.addAttribute("delMaterial", new Material());
		model.addAttribute("borMaterial", new Material());
		model.addAttribute("retMaterial", new Material());


		if (categoryId == -1)
		{
			model.addAttribute("materialList", materialService.getMaterialWithBorrowStatus());
		}
		else
		{
			model.addAttribute("materialList", materialService.getMaterialListByCategory(categoryId));
		}

		return "admin/admin_all_materials";
	}

	@RequestMapping(value = "/manage/material/save", method = RequestMethod.POST)
	public String save(@Valid @ModelAttribute("savMaterial") Material material, BindingResult bindingResult, @RequestParam(value = "materialType", required = true) int materialType)
	{


		System.out.println(String.format("Processing user create form=%s, bindingResult=%s", material, bindingResult));

		if (bindingResult.hasErrors())
		{
			// failed validation
			return "manage/material";
		}

		try
		{
			material.setCategory(materialType);
			materialService.saveMaterial(material);
		}
		catch (DataIntegrityViolationException e)
		{
			// probably email already exists - very rare case when multiple admins are adding same user
			// at the same time and form validation has passed for more than one of them.
			LOGGER.warn("Exception occurred when trying to save the material, assuming duplicate material", e);
			bindingResult.reject("material.exist", "Material already exists");
			return "manage/material";
		}
		// ok, redirect
		return "redirect:/manage/material";
	}

	@RequestMapping(value = "/manage/material/update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("upMaterial") Material material, BindingResult bindingResult, @RequestParam(value = "materialType", required = true) int materialType)
	{


		System.out.println(String.format("Processing user create form=%s, bindingResult=%s", material, bindingResult));

		if (bindingResult.hasErrors())
		{
			// failed validation
			return "manage/material";
		}

		try
		{
			Material temp = materialService.getMaterialById(material.getId());
			temp.setTitle(material.getTitle());
			temp.setAuthor(material.getAuthor());
			temp.setPublisher(material.getPublisher());
			temp.setYear(material.getYear());
			material.setCategory(materialType);
			materialService.saveMaterial(temp);
		}
		catch (DataIntegrityViolationException e)
		{
			// probably email already exists - very rare case when multiple admins are adding same user
			// at the same time and form validation has passed for more than one of them.
			LOGGER.warn("Exception occurred when trying to update the material, assuming duplicate material", e);
			bindingResult.reject("material.exist", "Material already exists");
			return "manage/material";
		}
		// ok, redirect
		return "redirect:/manage/material";
	}

	@RequestMapping(value = "/manage/material/return", method = RequestMethod.POST)
	public String ret(@Valid @ModelAttribute("retMaterial") Material material, BindingResult bindingResult)
	{
		System.out.println(String.format("Processing user create form=%s, bindingResult=%s", material, bindingResult));

		if (bindingResult.hasErrors())
		{
			// failed validation

			return "manage/material";
		}

		try
		{
			System.out.println("inside try. will call return material function");

			Material temp = materialService.getMaterialById(material.getId());

			Borrow borrow = borrowService.getBorrowMaterialById(temp.getBorrowStatus().getId());
			borrow.setDateReturned(new Date(System.currentTimeMillis()));
			borrowService.saveBorrow(borrow);
		}
		catch (DataIntegrityViolationException e)
		{
			// probably email already exists - very rare case when multiple admins are adding same user
			// at the same time and form validation has passed for more than one of them.
			LOGGER.warn("Exception occurred when trying to update the material, assuming duplicate material", e);
			bindingResult.reject("material.exist", "Material already exists");
			return "manage/material";
		}
		// ok, redirect
		return "redirect:/manage/material";
	}

	@RequestMapping(value = "/manage/material/borrow", method = RequestMethod.POST)
	public String borrow(@Valid @ModelAttribute("retMaterial") Material material, BindingResult bindingResult)
	{
		System.out.println(String.format("Processing user create form=%s, bindingResult=%s", material, bindingResult));

		if (bindingResult.hasErrors())
		{
			// failed validation

			return "manage/material";
		}

		try
		{
			System.out.println("inside try. will call borrow material function");

			Material temp = materialService.getMaterialById(material.getId());

			Borrow borrow = borrowService.getBorrowMaterialById(temp.getBorrowStatus().getId());
			borrow.setDateBorrowed(new Date(System.currentTimeMillis()));
			borrowService.saveBorrow(borrow);
		}
		catch (DataIntegrityViolationException e)
		{
			// probably email already exists - very rare case when multiple admins are adding same user
			// at the same time and form validation has passed for more than one of them.
			LOGGER.warn("Exception occurred when trying to update the material, assuming duplicate material", e);
			bindingResult.reject("material.exist", "Material already exists");
			return "manage/material";
		}
		// ok, redirect
		return "redirect:/manage/material";
	}

	public String delete(@Valid @ModelAttribute("delMaterial") Material material, BindingResult bindingResult)
	{
		System.out.println(String.format("Processing user create form=%s, bindingResult=%s", material, bindingResult));

		if (bindingResult.hasErrors())
		{
			// failed validation

			return "manage/material";
		}

		try
		{
			System.out.println("inside try. will call deleteMaterial function");
			materialService.deleteMaterial(material.getId());
		}
		catch (DataIntegrityViolationException e)
		{
			// probably email already exists - very rare case when multiple admins are adding same user
			// at the same time and form validation has passed for more than one of them.
			LOGGER.warn("Exception occurred when trying to update the material, assuming duplicate material", e);
			bindingResult.reject("material.exist", "Material already exists");
			return "manage/material";
		}
		// ok, redirect
		return "redirect:/manage/material";
	}
}
