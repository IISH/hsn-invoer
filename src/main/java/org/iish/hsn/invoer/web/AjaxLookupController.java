package org.iish.hsn.invoer.web;

import org.iish.hsn.invoer.domain.invoer.bev.Registration;
import org.iish.hsn.invoer.domain.invoer.bev.RegistrationId;
import org.iish.hsn.invoer.domain.invoer.geb.Gebakte;
import org.iish.hsn.invoer.domain.invoer.geb.Gebknd;
import org.iish.hsn.invoer.domain.invoer.huw.Huw;
import org.iish.hsn.invoer.domain.invoer.huw.Huwttl;
import org.iish.hsn.invoer.domain.invoer.mil.Milition;
import org.iish.hsn.invoer.domain.invoer.ovl.Ovlknd;
import org.iish.hsn.invoer.domain.invoer.pick.*;
import org.iish.hsn.invoer.domain.invoer.pk.Pkknd;
import org.iish.hsn.invoer.domain.reference.Ref_AINB;
import org.iish.hsn.invoer.domain.invoer.geb.Stpb;
import org.iish.hsn.invoer.domain.reference.Ref_RP;
import org.iish.hsn.invoer.exception.NotFoundException;
import org.iish.hsn.invoer.service.LookupService;
import org.iish.hsn.invoer.service.scan.MilitionScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Returns records from the database in JSON format for use in AJAX calls.
 */
@Controller
@RequestMapping(value = "/ajax/lookup")
public class AjaxLookupController {
    @Autowired
    private LookupService lookupService;

    @RequestMapping(value = "/stpb", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Stpb getStpb(@RequestParam Integer idnr) throws NotFoundException {
        return lookupService.getStpb(idnr, true);
    }

    @RequestMapping(value = "/rp", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Ref_RP gerRefRP(@RequestParam Integer idnr) throws NotFoundException {
        return lookupService.getRefRp(idnr, true);
    }

    @RequestMapping(value = "/ainb", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Ref_AINB getRefAinb(@RequestParam Integer keyToSourceRegister) throws NotFoundException {
        return lookupService.getRefAinb(keyToSourceRegister, true);
    }

    @RequestMapping(value = "/plaats", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Plaats getPlaats(@RequestParam Integer gemnr) throws NotFoundException {
        return lookupService.getPlaats(gemnr, true);
    }

    @RequestMapping(value = "/gebakte", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Gebakte getGebakte(@RequestParam Integer idnr) throws NotFoundException {
        return lookupService.getGebakte(idnr, true);
    }

    @RequestMapping(value = "/gebknd", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Gebknd getGebknd(@RequestParam Integer idnr) throws NotFoundException {
        return lookupService.getGebknd(idnr, true);
    }

    @RequestMapping(value = "/ovlknd", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Ovlknd getOvlknd(@RequestParam Integer idnr) throws NotFoundException {
        return lookupService.getOvlknd(idnr, true);
    }

    @RequestMapping(value = "/huwttl", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Huwttl getHuwttl(@RequestParam Integer idnr, Huw huw) throws NotFoundException {
        return lookupService.getHuwttl(idnr, huw, true);
    }

    @RequestMapping(value = "/pkknd", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Pkknd getPkknd(@RequestParam Integer idnr) throws NotFoundException {
        return lookupService.getPkknd(idnr, true);
    }

    @RequestMapping(value = "/b4", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Registration getRegistration(RegistrationId registrationId) throws NotFoundException {
        return lookupService.getRegistration(registrationId, true);
    }

    @RequestMapping(value = "/b4/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Registration> getRegistrations(RegistrationId registrationId) {
        return lookupService.getRegistrations(registrationId);
    }

    @RequestMapping(value = "/b4/op", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Registration getRegistrationForOPDate(int idnr, int day, int month, int year)
            throws NotFoundException {
        return lookupService.getRegistrationOfOp(idnr, day, month, year, true);
    }

    @RequestMapping(value = "/b4/op/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Registration> getRegistrationsForOP(int idnr) {
        return lookupService.getRegistrationsOfOp(idnr);
    }

    @RequestMapping(value = "/m0", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Milition getMilition(int idnr, int seq) throws NotFoundException {
        return lookupService.getMilition(idnr, seq, true);
    }

    @RequestMapping(value = "/m0/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Milition> getMilitions(int idnr) {
        return lookupService.getMilitions(idnr);
    }

    @RequestMapping(value = "/m0/scans", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Set<MilitionScan> getMilitionScans(int idnr) throws IOException {
        return lookupService.getMilitionScans(idnr);
    }

    @RequestMapping(value = "/plaatsen", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Plaats> getPlaatsen(@RequestParam String keyword,
                                                  @RequestParam(required = false) Integer id) {
        return lookupService.findMatchingPlaatsen(keyword, id);
    }

    @RequestMapping(value = "/beroepen", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Beroep> getBeroepen(@RequestParam String keyword) {
        return lookupService.findMatchingBeroepen(keyword);
    }

    @RequestMapping(value = "/relaties", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Relatie> getRelaties(@RequestParam String keyword,
                                                   @RequestParam(required = false) Integer id) {
        return lookupService.findMatchingRelaties(keyword, id);
    }

    @RequestMapping(value = "/kindrelaties", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<KindRelatie> getKindRelaties(@RequestParam String keyword) {
        return lookupService.findMatchingKindRelaties(keyword);
    }

    @RequestMapping(value = "/kerkgenootschappen", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Kg> getKerkGenootschappen(@RequestParam String keyword) {
        return lookupService.findMatchingKg(keyword);
    }

    @RequestMapping(value = "/adrestypes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Adrestp> getAdrestypes(@RequestParam String keyword) {
        return lookupService.findMatchingAdrestypes(keyword);
    }
}
