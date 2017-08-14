package library.controllers.admin;

import library.services.material.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ManageMagazineController
{
    private MaterialService materialService;

    @Autowired
    public void setMaterialService(MaterialService materialService) {
        this.materialService = materialService;
    }

    @RequestMapping(value = "/manage/magazine", method = RequestMethod.GET)
    String load(Model model)
    {
        model.addAttribute("magazineList", materialService.getMaterialListByCategory(2));
        return "admin/admin_magazines";
    }
}
