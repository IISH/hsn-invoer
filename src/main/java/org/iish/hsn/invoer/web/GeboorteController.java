package org.iish.hsn.invoer.web;

import org.iish.hsn.invoer.flow.state.GeboorteStart;
import org.iish.hsn.invoer.service.OverviewService;
import org.iish.hsn.invoer.param.GeboorteOverviewParams;
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
@RequestMapping(value = "/geboorte")
public class GeboorteController {
    @Autowired private GeboorteStart geboorteStart;
    @Autowired private OverviewService overviewService;

    @RequestMapping(method = RequestMethod.GET)
    public String start() {
        return "geboorte/GSTART";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String startPost(@RequestParam("basis") String basis, @RequestParam("invbeper") String invbeper) {
        String oversamp = "n";
        if (basis.equalsIgnoreCase("n")) {
            oversamp = "j";
        }

        geboorteStart.setOversamp(oversamp);
        geboorteStart.setInvbeper(invbeper);

        return "redirect:/geboorte/hoofdmenu";
    }

    @RequestMapping(value = "/hoofdmenu", method = RequestMethod.GET)
    public String main() {
        return "geboorte/GHOOFD";
    }

    @RequestMapping(value = "/hoofdmenu", method = RequestMethod.POST)
    public String redirectToModule(@RequestParam("keuze") String module) {
        switch (module) {
            case "1":
                return "redirect:/geboorte/invoer";
            case "2":
                return "redirect:/geboorte/overzicht/menu";
            case "3":
                return "redirect:/geboorte/correctie";
            case "4":
                return "redirect:/geboorte/verwijderen";
            case "s":
                return "redirect:/hoofdmenu";
            default:
                return "redirect:/geboorte/hoofdmenu";
        }
    }

    @RequestMapping(value = "/overzicht/menu", method = RequestMethod.GET)
    public ModelAndView getOverviewChoices() {
        Map<String, Object> model = new HashMap<>();
        model.put("action", "/geboorte/overzicht/menu");
        model.put("isAkte", true);
        model.put("isBirthOverview", true);
        model.put("nameOverz", "GOVERZS");

        return new ModelAndView("main/OVERZ", model);
    }

    @RequestMapping(value = "/overzicht/menu", method = RequestMethod.POST)
    public String getOverviewChoicesRedirect(GeboorteOverviewParams geboorteOverviewParams,
                                             RedirectAttributes redirectAttributes) {
        redirectAttributes.mergeAttributes(geboorteOverviewParams.getParamMap());
        return "redirect:/geboorte/overzicht";
    }

    @RequestMapping(value = "/overzicht", method = RequestMethod.GET)
    public ModelAndView getOverview(GeboorteOverviewParams geboorteOverviewParams) {
        Map<String, Object> model = new HashMap<>();
        model.put("overview", overviewService.getBirthOverview(geboorteOverviewParams));

        return new ModelAndView("geboorte/GOVERZ", model);
    }

    @RequestMapping(value = "/overzicht", method = RequestMethod.POST)
    public String getOverviewRedirect() {
        return "redirect:/geboorte/hoofdmenu";
    }
}
