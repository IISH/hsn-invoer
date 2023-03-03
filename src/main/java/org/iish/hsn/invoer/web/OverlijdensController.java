package org.iish.hsn.invoer.web;

import org.iish.hsn.invoer.flow.state.OverlijdensStart;
import org.iish.hsn.invoer.param.OverviewParams;
import org.iish.hsn.invoer.service.OverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/overlijden")
public class OverlijdensController {
    @Autowired private OverlijdensStart overlijdensStart;
    @Autowired private OverviewService overviewService;

    @RequestMapping(method = RequestMethod.GET)
    public String start() {
        return "overlijden/OSTART";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String startPost(@RequestParam("levnloos") String levnloos) {
        overlijdensStart.setLevnloos(levnloos);
        return "redirect:/overlijden/hoofdmenu";
    }

    @RequestMapping(value = "/hoofdmenu", method = RequestMethod.GET)
    public String main(Model model) {
        model.addAttribute("levnloos", overlijdensStart.getLevnloos());
        return "overlijden/OHOOFD";
    }

    @RequestMapping(value = "/hoofdmenu", method = RequestMethod.POST)
    public String redirectToModule(@RequestParam("keuze") String module) {
        return switch (module) {
            case "1" -> "redirect:/overlijden/invoer";
            case "2" -> "redirect:/overlijden/overzicht/menu";
            case "3" -> "redirect:/overlijden/correctie";
            case "4" -> "redirect:/overlijden/verwijderen";
            case "s" -> "redirect:/hoofdmenu";
            default -> "redirect:/overlijden/hoofdmenu";
        };
    }

    @RequestMapping(value = "/overzicht/menu", method = RequestMethod.GET)
    public ModelAndView getOverviewChoices() {
        Map<String, Object> model = new HashMap<>();
        model.put("action", "/overlijden/overzicht/menu");
        model.put("isAkte", true);
        model.put("isBirthOverview", false);
        model.put("nameOverz", "OOVERZS");

        return new ModelAndView("main/OVERZ", model);
    }

    @RequestMapping(value = "/overzicht/menu", method = RequestMethod.POST)
    public String getOverviewChoicesRedirect(OverviewParams overviewParams, RedirectAttributes redirectAttributes) {
        redirectAttributes.mergeAttributes(overviewParams.getParamMap());
        return "redirect:/overlijden/overzicht";
    }

    @RequestMapping(value = "/overzicht", method = RequestMethod.GET)
    public ModelAndView getOverview(OverviewParams overviewParams) {
        Map<String, Object> model = new HashMap<>();
        model.put("overview", overviewService.getDeathOverview(overviewParams));

        return new ModelAndView("overlijden/OOVERZ", model);
    }

    @RequestMapping(value = "/overzicht", method = RequestMethod.POST)
    public String getOverviewRedirect() {
        return "redirect:/overlijden/hoofdmenu";
    }
}
