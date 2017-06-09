package org.iish.hsn.invoer.web;

import org.iish.hsn.invoer.domain.invoer.mil.Milition;
import org.iish.hsn.invoer.domain.invoer.pick.Plaats;
import org.iish.hsn.invoer.exception.NotFoundException;
import org.iish.hsn.invoer.param.OverviewParams;
import org.iish.hsn.invoer.service.OverviewService;
import org.iish.hsn.invoer.service.scan.Scan;
import org.iish.hsn.invoer.service.scan.ScansService;
import org.iish.hsn.invoer.service.scan.MilitionScanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/militie")
public class MilitieregisterController {
    @Autowired private OverviewService overviewService;
    @Autowired private ScansService scansService;

    @RequestMapping(method = RequestMethod.GET)
    public String start() {
        return "redirect:/militie/hoofdmenu";
    }

    @RequestMapping(value = "/hoofdmenu", method = RequestMethod.GET)
    public String main() {
        return "militie/MHOOFD";
    }

    @RequestMapping(value = "/hoofdmenu", method = RequestMethod.POST)
    public String redirectToModule(@RequestParam("keuze") String module) {
        switch (module) {
            case "1":
                return "redirect:/militie/invoer";
            case "2":
                return "redirect:/militie/overzicht/menu";
            case "3":
                return "redirect:/militie/correctie";
            case "4":
                return "redirect:/militie/verwijderen";
            case "s":
                return "redirect:/hoofdmenu";
            default:
                return "redirect:/militie/hoofdmenu";
        }
    }

    @RequestMapping(value = "/overzicht/menu", method = RequestMethod.GET)
    public ModelAndView getOverviewChoices() {
        Map<String, Object> model = new HashMap<>();
        model.put("action", "/militie/overzicht/menu");
        model.put("isAkte", false);
        model.put("isBirthOverview", false);
        model.put("nameOverz", "MOVERZS");

        return new ModelAndView("main/OVERZ", model);
    }

    @RequestMapping(value = "/overzicht/menu", method = RequestMethod.POST)
    public String getOverviewChoicesRedirect(OverviewParams overviewParams, RedirectAttributes redirectAttributes) {
        redirectAttributes.mergeAttributes(overviewParams.getParamMap());
        return "redirect:/militie/overzicht";
    }

    @RequestMapping(value = "/overzicht", method = RequestMethod.GET)
    public ModelAndView getOverview(OverviewParams overviewParams) {
        Map<String, Object> model = new HashMap<>();
        model.put("overview", overviewService.getMilitionOverview(overviewParams));

        return new ModelAndView("militie/MOVERZ", model);
    }

    @RequestMapping(value = "/overzicht", method = RequestMethod.POST)
    public String getOverviewRedirect() {
        return "redirect:/militie/hoofdmenu";
    }

    @RequestMapping(value = "/scan", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<ByteArrayResource> getScan(
            @RequestParam("idnr") int idnr, @RequestParam("file") String file) throws IOException, NotFoundException {
        Path scanPath = null;

        MilitionScanRepository scanRepository = scansService.getMilitionScanRepository();
        for (Scan scan : scanRepository.findScans(idnr)) {
            if ((scan.getSideA() != null) && scan.getSideA().getFileName().toString().equals(file))
                scanPath = scan.getSideA();
            if ((scan.getSideB() != null) && scan.getSideB().getFileName().toString().equals(file))
                scanPath = scan.getSideB();
        }

        if (scanPath != null) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setContentType(MediaType.parseMediaType(Files.probeContentType(scanPath)));
            ByteArrayResource byteArrayResource = new ByteArrayResource(Files.readAllBytes(scanPath));

            return new ResponseEntity<>(byteArrayResource, responseHeaders, HttpStatus.OK);
        }

        throw new NotFoundException("Milition scan for RP with idnr " + idnr +
                " and filename " + file + " could not be found!");
    }
}
