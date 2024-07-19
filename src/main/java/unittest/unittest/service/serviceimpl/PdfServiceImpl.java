package unittest.unittest.service.serviceimpl;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import unittest.unittest.model.Customer;
import unittest.unittest.service.CustomerService;
import unittest.unittest.service.PdfService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class PdfServiceImpl implements PdfService {
    private final CustomerService customerService;

    @Override
    public byte[] createPdfImageById(Integer id) throws DocumentException {

        Customer customer = customerService.getById(id);

        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, baos);
        document.open();

        try {
            Image img = Image.getInstance(customer.getUrl());
            img.scaleToFit(500, 500);
            document.add(img);
        } catch (Exception e) {
            log.error(e.getMessage());
            document.add(new Paragraph("Gambar tidak dapat dimuat: " + e.getMessage()));
        }
        document.close();
        return baos.toByteArray();
    }
}
