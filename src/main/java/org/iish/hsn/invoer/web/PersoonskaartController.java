package org.iish.hsn.invoer.web;

import org.iish.hsn.invoer.param.OverviewParams;
import org.iish.hsn.invoer.service.OverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/persoonskaart")
public class PersoonskaartController {
    @Autowired private OverviewService overviewService;

    @RequestMapping(method = RequestMethod.GET)
    public String start() {
        return "redirect:/persoonskaart/hoofdmenu";
    }

    @RequestMapping(value = "/hoofdmenu", method = RequestMethod.GET)
    public String main() {
        return "persoonskaart/PHOOFD";
    }

    @RequestMapping(value = "/hoofdmenu", method = RequestMethod.POST)
    public String redirectToModule(@RequestParam("keuze") String module) {
        switch (module) {
            case "1":
                return "redirect:/persoonskaart/invoer";
            case "2":
                return "redirect:/persoonskaart/overzicht/menu";
            case "3":
                return "redirect:/persoonskaart/correctie";
            case "4":
                return "redirect:/persoonskaart/verwijderen";
            case "s":
                return "redirect:/hoofdmenu";
            default:
                return "redirect:/persoonskaart/hoofdmenu";
        }
    }

    @RequestMapping(value = "/overzicht/menu", method = RequestMethod.GET)
    public ModelAndView getOverviewChoices() {
        Map<String, Object> model = new HashMap<>();
        model.put("action", "/persoonskaart/overzicht/menu");
        model.put("isBirthOverview", false);
        model.put("nameOverz", "POVERZS");

        return new ModelAndView("main/OVERZ", model);
    }

    @RequestMapping(value = "/overzicht/menu", method = RequestMethod.POST)
    public String getOverviewChoicesRedirect(OverviewParams overviewParams, RedirectAttributes redirectAttributes) {
        redirectAttributes.mergeAttributes(overviewParams.getParamMap());
        return "redirect:/persoonskaart/overzicht";
    }

    @RequestMapping(value = "/overzicht", method = RequestMethod.GET)
    public ModelAndView getOverview(OverviewParams overviewParams) {
        Map<String, Object> model = new HashMap<>();
        model.put("overview", overviewService.getPersonCardOverview(overviewParams));

        return new ModelAndView("persoonskaart/POVERZ", model);
    }

    @RequestMapping(value = "/overzicht", method = RequestMethod.POST)
    public String getOverviewRedirect() {
        return "redirect:/persoonskaart/hoofdmenu";
    }
}
