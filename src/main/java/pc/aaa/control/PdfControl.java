package pc.aaa.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pc.aaa.domain.Pdf;
import pc.aaa.service.PdfService;

import java.util.List;

/**
 * Created by wu on 17-6-28.
 */
@RestController
@CrossOrigin(origins = {}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
public class PdfControl {

    @Autowired
    private PdfService pdfService;

    @RequestMapping("/pdf/templastall")
    public List<Pdf> templastall(String resumeid){
        return this.pdfService.templastall(resumeid);
    }
}
