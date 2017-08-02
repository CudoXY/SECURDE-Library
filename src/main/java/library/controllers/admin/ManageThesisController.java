package library.controllers.admin;

import library.services.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ManageThesisController
{
    private MaterialService materialService;

    @Autowired
    public void setMaterialService(MaterialService materialService) {
        this.materialService = materialService;
    }

    @RequestMapping(value = "/manage/thesis", method = RequestMethod.GET)
    String load(Model model)
    {
        model.addAttribute("thesisList", materialService.getMaterialListByCategory(3));
        return "admin/admin_thesis";
    }
}
