package library.controllers.admin;

import library.domain.Borrow;
import library.domain.Material;
import library.domain.User;
import library.domain.helper.BorrowAction;
import library.domain.helper.UserHelper;
import library.domain.validator.FormEditMaterialValidator;
import library.domain.validator.FormSaveMaterialValidator;
import library.services.borrow.BorrowService;
import library.services.material.MaterialService;
import library.services.user.UserService;
import org.jxls.template.SimpleExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Controller
public class ManageMaterialController
{
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	private MaterialService materialService;
	private BorrowService borrowService;
	private UserService userService;
	private FormSaveMaterialValidator formSaveMaterialValidator;
	private FormEditMaterialValidator formEditMaterialValidator;

	@Autowired
	public void setFormSaveMaterialValidator(FormSaveMaterialValidator formSaveMaterialValidator)
	{
		this.formSaveMaterialValidator = formSaveMaterialValidator;
	}

	@Autowired
	public void setFormEditMaterialValidator(FormEditMaterialValidator formEditMaterialValidator)
	{
		this.formEditMaterialValidator = formEditMaterialValidator;
	}

	@Autowired
	public void setMaterialService(MaterialService materialService)
	{
		this.materialService = materialService;
	}

	@Autowired
	public void setMaterialService(BorrowService borrowService)
	{
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

	@InitBinder("savMaterial")
	public void initAddMaterialBinder(WebDataBinder binder)
	{
		binder.addValidators(formSaveMaterialValidator);
	}

	@RequestMapping(value = "/manage/material/save", method = RequestMethod.POST)
	public String save(@Valid @ModelAttribute("savMaterial") Material material, BindingResult bindingResult, @RequestParam(value = "materialType", required = true) int materialType)
	{
		System.out.println(String.format("Processing user create form=%s, bindingResult=%s", material, bindingResult));

		if (bindingResult.hasErrors())
		{
			// failed validation
			return "redirect:/manage/material";
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
			return "redirect:/manage/material";
		}
		// ok, redirect
		return "redirect:/manage/material";
	}

	@InitBinder("upMaterial")
	public void initEditMaterialBinder(WebDataBinder binder)
	{
		binder.addValidators(formEditMaterialValidator);
	}

	@RequestMapping(value = "/manage/material/update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("upMaterial") Material material, BindingResult bindingResult, @RequestParam(value = "materialType", required = true) int materialType)
	{
		System.out.println(String.format("Processing user create form=%s, bindingResult=%s", material, bindingResult));

		if (bindingResult.hasErrors())
		{
			// failed validation
			return "redirect:/manage/material";
		}

		try
		{
			Material temp = materialService.getMaterialById(material.getId());
			temp.setTitle(material.getTitle());
			temp.setAuthor(material.getAuthor());
			temp.setPublisher(material.getPublisher());
			temp.setYear(material.getYear());
			temp.setCategory(materialType);
			materialService.saveMaterial(temp);
		}
		catch (DataIntegrityViolationException e)
		{
			// probably email already exists - very rare case when multiple admins are adding same user
			// at the same time and form validation has passed for more than one of them.
			LOGGER.warn("Exception occurred when trying to update the material, assuming duplicate material", e);
			bindingResult.reject("material.exist", "Material already exists");
			return "redirect:/manage/material";
		}
		// ok, redirect
		return "redirect:/manage/material";
	}

	@RequestMapping(value = "/manage/material/return", method = RequestMethod.POST)
	public String ret(@Valid @ModelAttribute("retMaterial") Material materialId, BindingResult bindingResult)
	{
			System.out.println("inside try. will call return material function");

			Material temp = materialService.getMaterialById(materialId.getId());

			Borrow borrow = borrowService.getBorrowMaterialById(temp.getBorrowStatus().getId());
			borrow.setDateReturned(new Date(System.currentTimeMillis()));
			borrowService.saveBorrow(borrow);

		// ok, redirect
		return "redirect:/manage/material";
	}

	@RequestMapping(value = "/manage/material/borrow", method = RequestMethod.POST)
	public String borrow(@Valid @ModelAttribute("retMaterial") Material material, BindingResult bindingResult)
	{
		System.out.println(material.getId());
		Material m = materialService.getMaterialById(material.getId());
		System.out.println(m.getTitle());
		Borrow b = m.getBorrowStatus();
		b.setReleased(true);

		borrowService.saveBorrow(b);

		// ok, redirect
		return "redirect:/manage/material";
	}

	@RequestMapping(value = "/manage/material/delete", method = RequestMethod.POST)
	public String delete(String materialId)
	{
		Borrow b = borrowService.getMaterialStatus(materialId);

		// Reject request if the material is released
		if (b != null && b.isReleased())
		{
			// TODO: Handle error
			return "redirect:/manage/material";
		}
		materialService.deleteMaterial(materialId);

		return "redirect:/manage/material";
	}

	@RequestMapping(value = "/manage/material/download", method = RequestMethod.GET)
	public void export(HttpServletResponse response)
	{
		Iterable<Material> materialList = materialService.getMaterialList();
		Iterator<Material> iter = materialList.iterator();
		List<Material> list = new ArrayList<Material>();
		while (iter.hasNext())
		{
			Material m = iter.next();
			System.out.println(m.getTitle());
			list.add(m);
		}

		List<String> headers = Arrays.asList("Location", "Title", "Author", "Publisher", "Year Published", "Category", "Borrowed");
		try
		{
			System.out.println("Inside the export excel function");
			response.addHeader("Content-disposition", "attachment; filename=Materials.xls");
			response.setContentType("application/vnd.ms-excel");
			SimpleExporter exporter = new SimpleExporter();
			exporter.gridExport(headers, list, "id, title, author, publisher, year, category, borrowStatus ", response.getOutputStream());
			//new SimpleExporter().gridExport(headers, list, "id, title, author, publisher, year, category, borrowStatus ", response.getOutputStream());
			//new SimpleExporter().gridExport(headers, list, "Id Number, Author, Category, Publisher, Title, Year ", response.getOutputStream());
			response.flushBuffer();
		}
		catch (IOException e)
		{
			LOGGER.warn(e.getMessage(), e);
		}
	}


}
