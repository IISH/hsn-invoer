package org.iish.hsn.invoer.web;

import org.iish.hsn.invoer.exception.NotFoundException;
import org.iish.hsn.invoer.param.OverviewParams;
import org.iish.hsn.invoer.service.OverviewService;
import org.iish.hsn.invoer.service.scan.Scan;
import org.iish.hsn.invoer.service.scan.ScansService;
import org.iish.hsn.invoer.service.scan.MilitionScanRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.libjpegturbo.turbojpeg.*;

@Controller
@RequestMapping(value = "/militie")
public class MilitieregisterController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MilitieregisterController.class);

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
    public @ResponseBody ResponseEntity<byte[]> getScan(
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
            String contentType = Files.probeContentType(scanPath);
            long fileSize = Files.size(scanPath);

            // In case of the most common image formats larger than 6 MB,
            // try to increase the compression for smaller images
            if ((fileSize > 6291456L) && (contentType.equals(MediaType.IMAGE_JPEG_VALUE)
                    || contentType.equals(MediaType.IMAGE_GIF_VALUE)
                    || contentType.equals(MediaType.IMAGE_PNG_VALUE))) {
                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.setContentType(MediaType.valueOf(MediaType.IMAGE_JPEG_VALUE));

                BufferedImage bufferedImage = getImage(scanPath);
                byte[] image = getCompressedImage(bufferedImage);

                return new ResponseEntity<>(image, responseHeaders, HttpStatus.OK);
            }

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setContentType(MediaType.parseMediaType(contentType));
            ByteArrayResource byteArrayResource = new ByteArrayResource(Files.readAllBytes(scanPath));

            return new ResponseEntity<>(byteArrayResource.getByteArray(), responseHeaders, HttpStatus.OK);
        }

        throw new NotFoundException("Milition scan for RP with idnr " + idnr +
                " and filename " + file + " could not be found!");
    }

    private BufferedImage getImage(Path path) throws IOException {
        BufferedImage image = null;

        if (Files.probeContentType(path).equals(MediaType.IMAGE_JPEG_VALUE)) {
            try {
                byte[] img = Files.readAllBytes(path);
                TJDecompressor decompressor = new TJDecompressor(img);
                int width = decompressor.getWidth();
                int height = decompressor.getHeight();
                image = decompressor.decompress(width, height, BufferedImage.TYPE_INT_RGB, 0);
                decompressor.close();
            }
            catch (UnsatisfiedLinkError ule) {
                LOGGER.warn("Couldn't use turbojpeg to read the JPEG image!");
            }
            catch (Exception e) {
                if (e instanceof IOException)
                    throw (IOException) e;
                throw new IOException(e);
            }
        }

        if (image == null) {
            ImageIO.setUseCache(false);
            image = ImageIO.read(path.toFile());
        }

        return image;
    }

    private byte[] getCompressedImage(BufferedImage image) throws IOException {
        byte[] compressedImage = null;

        try {
            TJCompressor compressor = new TJCompressor();
            compressor.setJPEGQuality(50);
            compressor.setSubsamp(TJ.SAMP_444);
            byte[] img = compressor.compress(image, 0);
            compressedImage = Arrays.copyOfRange(img, 0, compressor.getCompressedSize());
        }
        catch (UnsatisfiedLinkError ule) {
            LOGGER.warn("Couldn't use turbojpeg to write the JPEG image!");
        }
        catch (Exception e) {
            if (e instanceof IOException)
                throw (IOException) e;
            throw new IOException(e);
        }

        if (compressedImage == null) {
            ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("jpg").next();

            ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();
            jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            jpgWriteParam.setCompressionQuality(0.5f);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageOutputStream imageOutputStream = new MemoryCacheImageOutputStream(byteArrayOutputStream);
            jpgWriter.setOutput(imageOutputStream);

            IIOImage outputImage = new IIOImage(image, null, null);
            jpgWriter.write(null, outputImage, jpgWriteParam);
            jpgWriter.dispose();

            compressedImage = byteArrayOutputStream.toByteArray();
        }

        return compressedImage;
    }
}
