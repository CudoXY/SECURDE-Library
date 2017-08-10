package library.controllers.admin;

import library.services.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ManageRoomController
{
    private MaterialService materialService;

    @Autowired
    public void setMaterialService(MaterialService materialService) {
        this.materialService = materialService;
    }

    @RequestMapping(value = "/manage/room", method = RequestMethod.GET)
    String load(Model model)
    {
        model.addAttribute("authorList", materialService.getMaterialList());
        return "admin/admin_rooms";
    }
}
