package library.controllers.admin;

import library.domain.Material;
import library.services.MaterialService;
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

@Controller
public class ManageBookController
{
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private MaterialService materialService;

    @Autowired
    public void setMaterialService(MaterialService materialService) {
        this.materialService = materialService;
    }

    @RequestMapping(value = "/manage/book", method = RequestMethod.GET)
    String load(Model model)
    {
        model.addAttribute("material", new Material());
        model.addAttribute("bookList", materialService.getMaterialListByCategory(1));
        return "admin/admin_books";
    }

    @RequestMapping(value = "/manage/book/save", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("material") Material material, BindingResult bindingResult, @RequestParam(value = "materialType", required = true) int materialType)
    {


        System.out.println(String.format("Processing user create form=%s, bindingResult=%s", material, bindingResult));

        if (bindingResult.hasErrors())
        {
            // failed validation
            return "manage/book";
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
            bindingResult.reject("book.exist", "Book already exists");
            return "manage/book";
        }
        // ok, redirect
        return "redirect:/manage/book";
    }
}
