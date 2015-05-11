package org.iish.hsn.invoer.web;

import org.iish.hsn.invoer.service.PicklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Sets new values in pick lists for future use by the user.
 */
@Controller
@RequestMapping(value = "/ajax/picklist/set")
public class AjaxPicklistSetController {
    @Autowired private PicklistService picklistService;

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/plaats", method = RequestMethod.POST)
    public void setPlaats(@RequestParam String value) {
        picklistService.setPlaats(value);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/beroep", method = RequestMethod.POST)
    public void setBeroep(@RequestParam String value) {
        picklistService.setBeroep(value);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/relatie", method = RequestMethod.POST)
    public void setRelatie(@RequestParam String value) {
        picklistService.setRelatie(value);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/kerkgenootschap", method = RequestMethod.POST)
    public void setKerkGenootschap(@RequestParam String value) {
        picklistService.setKg(value);
    }
}
