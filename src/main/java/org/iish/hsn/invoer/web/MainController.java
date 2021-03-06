package org.iish.hsn.invoer.web;

import org.iish.hsn.invoer.util.InputMetadata;
import org.iish.hsn.invoer.util.InputMetadataChecker;
import org.iish.hsn.invoer.util.NoInputMetadataCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {
    @Autowired private Environment   env;
    @Autowired private InputMetadata inputMetadata;
    @Autowired private InputMetadataChecker inputMetadataChecker;

    @NoInputMetadataCheck
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public ResponseEntity<?> validateInputMetadata(@RequestParam(value = "init", required = false) String init,
                                                   @RequestParam("ondrzk") String ondrzk,
                                                   @RequestParam("opdrnr") String opdrnr) {
        if (init != null)
            inputMetadata.setInit(init.trim().toUpperCase());

        inputMetadata.setOndrzk(ondrzk.trim().toUpperCase());
        inputMetadata.setOpdrnr(opdrnr.trim().toUpperCase());

        if (!inputMetadataChecker.isValid(inputMetadata))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @NoInputMetadataCheck
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String inputMetadataForm(HttpServletRequest request) {
        // Show an authorization failure message in case the user has no access
        if (env.acceptsProfiles(Profiles.of("ldapAuth", "dbAuth")) && !request.isUserInRole("ROLE_USER"))
            return "main/auth";
        return "main/metadata";
    }

    @NoInputMetadataCheck
    @RequestMapping(params = "next", value = "/", method = RequestMethod.POST)
    public String validateInputMetadataForm(@RequestParam(value = "init", required = false) String init,
                                            @RequestParam("ondrzk") String ondrzk,
                                            @RequestParam("opdrnr") String opdrnr) {
        if (init != null)
            inputMetadata.setInit(init.trim().toUpperCase());

        inputMetadata.setOndrzk(ondrzk.trim().toUpperCase());
        inputMetadata.setOpdrnr(opdrnr.trim().toUpperCase());

        if (inputMetadataChecker.isValid(inputMetadata)) {
            return "redirect:/hoofdmenu";
        }
        return "redirect:/";
    }

    @NoInputMetadataCheck
    @RequestMapping(params = "logout", value = "/", method = RequestMethod.POST)
    public String logout() {
        inputMetadata.setInit(null);
        inputMetadata.setOndrzk(null);
        inputMetadata.setOpdrnr(null);

        return "redirect:/logout";
    }

    @NoInputMetadataCheck
    @RequestMapping(value = "/logout/success", method = RequestMethod.GET)
    public String logoutSuccess() {
        return "main/logout";
    }

    @NoInputMetadataCheck
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "main/login";
    }

    @RequestMapping(value = "/hoofdmenu", method = RequestMethod.GET)
    public String main() {
        return "main/main";
    }

    @RequestMapping(value = "/hoofdmenu", method = RequestMethod.POST)
    public String redirectToModule(@RequestParam("keuze") String module) {
        switch (module) {
            case "1":
                return "redirect:/geboorte";
            case "2":
                return "redirect:/overlijden";
            case "3":
                return "redirect:/huwelijk";
            case "4":
                return "redirect:/persoonskaart";
            case "5":
                return "redirect:/bevolkingsregister";
            case "6":
                return "redirect:/militie";
            case "a":
                if (inputMetadataChecker.isAdmin())
                    return "redirect:/admin";
            case "s":
                return "redirect:/?exit=true";
            default:
                return "redirect:/hoofdmenu";
        }
    }

    @RequestMapping(value = "/backButton", method = RequestMethod.GET)
    public String backButtonPrevent() {
        return "exception/backButtonError";
    }

    @NoInputMetadataCheck
    @RequestMapping(value = "/keepalive", method = RequestMethod.POST)
    public ResponseEntity<?> keepAlive() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
