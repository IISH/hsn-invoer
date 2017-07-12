package org.iish.hsn.invoer.web;

import org.iish.hsn.invoer.domain.invoer.bev.Person;
import org.iish.hsn.invoer.domain.invoer.bev.PersonDynamic;
import org.iish.hsn.invoer.domain.invoer.bev.Registration;
import org.iish.hsn.invoer.domain.invoer.bev.RegistrationId;
import org.iish.hsn.invoer.exception.NotFoundException;
import org.iish.hsn.invoer.flow.helper.BevolkingsregisterHelper;
import org.iish.hsn.invoer.flow.helper.BevolkingsregisterLast;
import org.iish.hsn.invoer.flow.state.BevolkingsregisterFlowState;
import org.iish.hsn.invoer.service.LookupService;
import org.iish.hsn.invoer.service.OverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.webflow.context.ExternalContextHolder;
import org.springframework.webflow.context.servlet.DefaultFlowUrlHandler;
import org.springframework.webflow.context.servlet.FlowUrlHandler;
import org.springframework.webflow.context.servlet.ServletExternalContext;
import org.springframework.webflow.execution.FlowExecution;
import org.springframework.webflow.execution.FlowExecutionKey;
import org.springframework.webflow.execution.repository.FlowExecutionRepository;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.executor.FlowExecutorImpl;
import org.springframework.webflow.mvc.servlet.MvcExternalContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/bevolkingsregister")
public class BevolkingsregisterController {
    @Autowired private FlowExecutor flowExecutor;
    @Autowired private ServletContext servletContext;

    @Autowired private LookupService lookupService;
    @Autowired private OverviewService overviewService;
    @Autowired private BevolkingsregisterHelper bevolkingsregisterHelper;
    @Autowired private BevolkingsregisterLast bevolkingsregisterLast;

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

    @RequestMapping(value = "/overzicht/modal", method = RequestMethod.GET)
    public ModelAndView getOverviewModal() {
        List<Registration> registrations = overviewService.getPopulationRegisterRegistrationOverview();

        Map<String, Object> model = new HashMap<>();
        model.put("overview", registrations);

        return new ModelAndView("bevolkingsregister/overview-fragments :: overviewTable", model);
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

    @RequestMapping(value = "/remember", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Registration getLastRegistration() {
        return bevolkingsregisterLast.getLastRegistration();
    }

    @RequestMapping(value = "/remember", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Registration setLastRegistration(RegistrationId registrationId) throws NotFoundException {
        Registration registration = lookupService.getRegistration(registrationId, true);
        bevolkingsregisterLast.setLastRegistration(registration);
        return registration;
    }

    @RequestMapping(value = "/related-person-dynamics", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<PersonDynamic> getRelatedPersonDynamics(HttpServletRequest request, HttpServletResponse response,
                                                                      @RequestParam(value = "person") Integer person,
                                                                      @RequestParam(value = "type") PersonDynamic.Type type) {
        BevolkingsregisterFlowState bevolkingsregisterFlow = getFlowState(request, response);
        if (bevolkingsregisterFlow != null) {
            return bevolkingsregisterHelper.getWhereRelatedPerson(bevolkingsregisterFlow, person, type);
        }
        return new ArrayList<>();
    }

    /**
     * Access the bevolkingsregister flow state for use with AJAX calls with JSON response.
     *
     * @param request  The request.
     * @param response The response.
     * @return The flow state, if found in the current session.
     */
    private BevolkingsregisterFlowState getFlowState(HttpServletRequest request, HttpServletResponse response) {
        FlowUrlHandler flowUrlHandler = new DefaultFlowUrlHandler();
        ServletExternalContext servletExternalContext = new MvcExternalContext(servletContext, request, response, flowUrlHandler);
        ExternalContextHolder.setExternalContext(servletExternalContext);

        FlowExecutionRepository executionRepository = ((FlowExecutorImpl) flowExecutor).getExecutionRepository();
        FlowExecutionKey executionKey = executionRepository.parseFlowExecutionKey(flowUrlHandler.getFlowExecutionKey(request));
        FlowExecution flowExecution = executionRepository.getFlowExecution(executionKey);

        return (BevolkingsregisterFlowState) flowExecution.getActiveSession().getScope().get("akte");
    }
}
