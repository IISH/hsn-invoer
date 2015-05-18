package org.iish.hsn.invoer.web;

import org.iish.hsn.invoer.domain.invoer.bev.Person;
import org.iish.hsn.invoer.domain.invoer.bev.Registration;
import org.iish.hsn.invoer.domain.invoer.bev.RegistrationId;
import org.iish.hsn.invoer.exception.NotFoundException;
import org.iish.hsn.invoer.service.LookupService;
import org.iish.hsn.invoer.service.OverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/bevolkingsregister")
public class BevolkingsregisterController {
    @Autowired private LookupService   lookupService;
    @Autowired private OverviewService overviewService;

    @RequestMapping(method = RequestMethod.GET)
    public String start() {
        return "redirect:/bevolkingsregister/hoofdmenu";
    }

    @RequestMapping(value = "/hoofdmenu", method = RequestMethod.GET)
    public String main() {
        return "bevolkingsregister/BHOOFD";
    }

    @RequestMapping(value = "/hoofdmenu", method = RequestMethod.POST)
    public String redirectToModule(@RequestParam("keuze") String module) {
        switch (module) {
            case "1":
                return "redirect:/bevolkingsregister/invoer/alle-regels";
            case "2":
                return "redirect:/bevolkingsregister/invoer/per-regel";
            case "3":
                return "redirect:/bevolkingsregister/overzicht";
            case "4":
                return "redirect:/bevolkingsregister/verwijderen";
            case "5":
                return "redirect:/bevolkingsregister/correctie/personen";
            case "6":
                return "redirect:/bevolkingsregister/correctie/samenstelling";
            case "7":
                return "redirect:/bevolkingsregister/correctie/adressen";
            case "8":
                return "redirect:/bevolkingsregister/correctie/bijzonderheden";
            case "9":
                return "redirect:/bevolkingsregister/correctie/identificatie";
            case "s":
                return "redirect:/hoofdmenu";
            default:
                return "redirect:/bevolkingsregister/hoofdmenu";
        }
    }

    @RequestMapping(value = "/overzicht", method = RequestMethod.GET)
    public ModelAndView getOverview(@RequestParam(value = "idnr", required = false) Integer idnr) {
        List<Registration> registrations = overviewService.getPopulationRegisterRegistrationOverview(idnr);
        Registration registration = null;
        List<Person> persons = null;

        if (registrations.size() > 0) {
            registration = registrations.get(0);
            persons =
                    overviewService.getPopulationRegisterRegistrationPersonsOverview(registration.getRegistrationId());
        }

        Map<String, Object> model = new HashMap<>();
        model.put("overview", registrations);
        model.put("registration", registration);
        model.put("persons", persons);

        return new ModelAndView("bevolkingsregister/BOVERZ", model);
    }

    @RequestMapping(value = "/overzicht/persons", method = RequestMethod.GET)
    public ModelAndView getRegistrationOverview(RegistrationId registrationId) throws NotFoundException {
        Map<String, Object> model = new HashMap<>();
        model.put("registration", lookupService.getRegistration(registrationId, true));
        model.put("persons", overviewService.getPopulationRegisterRegistrationPersonsOverview(registrationId));

        return new ModelAndView("bevolkingsregister/BOVERZ :: personsBody", model);
    }

    @RequestMapping(value = "/overzicht", method = RequestMethod.POST)
    public String getOverviewRedirect() {
        return "redirect:/bevolkingsregister/hoofdmenu";
    }
}
