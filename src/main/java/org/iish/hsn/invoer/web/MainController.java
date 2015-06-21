package org.iish.hsn.invoer.web;

import org.iish.hsn.invoer.util.InputMetadata;
import org.iish.hsn.invoer.util.NoInputMetadataCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @Autowired
    private InputMetadata inputMetadata;

    @NoInputMetadataCheck
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String inputMetadataForm() {
        return "main/metadata";
    }

    @NoInputMetadataCheck
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String validateInputMetadataForm(@RequestParam("init") String init, @RequestParam("ondrzk") String ondrzk,
                                            @RequestParam("opdrnr") String opdrnr) {
        inputMetadata.setInit(init.trim().toUpperCase());
        inputMetadata.setOndrzk(ondrzk.trim().toUpperCase());
        inputMetadata.setOpdrnr(opdrnr.trim().toUpperCase());

        if (inputMetadata.isValid()) {
            return "redirect:/hoofdmenu";
        }
        return "redirect:/";
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
            case "s":
                return "redirect:/";
            default:
                return "redirect:/hoofdmenu";
        }
    }

    @RequestMapping(value = "/backButton", method = RequestMethod.GET)
    public String backButtonPrevent() {
        return "exception/backButtonError";
    }
}
