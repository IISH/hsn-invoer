package org.iish.hsn.invoer.web;

import org.iish.hsn.invoer.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired private AdminService adminService;

    @RequestMapping(method = RequestMethod.GET)
    public String start() {
        return "redirect:/admin/hoofdmenu";
    }

    @RequestMapping(value = "/hoofdmenu", method = RequestMethod.GET)
    public String main() {
        return "admin/main";
    }

    @RequestMapping(value = "/hoofdmenu", method = RequestMethod.POST)
    public String redirectToModule(@RequestParam("keuze") String module) {
        switch (module) {
            case "1":
                return "redirect:/admin/import";
            case "s":
                return "redirect:/hoofdmenu";
            default:
                return "redirect:/admin/hoofdmenu";
        }
    }

    @RequestMapping(value = "/import", method = RequestMethod.GET)
    public String importDataSelect() {
        return "admin/import";
    }

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public String importData(@RequestParam("dbFile") MultipartFile dbFile) {
        adminService.uploadMilitionDb(dbFile);
        return "admin/import";
    }
}


