package unittest.unittest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PdfGenerateService {
    private final PdfService pdfService;

    public ByteArrayResource getPdfFromUserId(Integer id) {
        try {
            byte[] pdfBytes = pdfService.createPdfImageById(id);
            ByteArrayResource resource = new ByteArrayResource(pdfBytes);
            return resource;
        } catch (Exception e) {
            return null;
        }
    }
}
