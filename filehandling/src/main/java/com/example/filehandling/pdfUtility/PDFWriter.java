package com.example.filehandling.pdfUtility;


import com.example.filehandling.entity.Cricketer;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64;
import com.mysql.cj.util.Base64Decoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class PDFWriter {


    private Document document;


    public void createPdfFile(List<Cricketer> cricketerList,File file) throws Exception{

        log.info("Creating the pdf document file");
        document = new Document();

        PdfWriter.getInstance(document, Files.newOutputStream(file.toPath()));
        document.open();
        cricketerList.forEach((item)->{
            try {
                createTitle(item.getName());
                createImage(item.getImageData());
                createDescription(item.getDescription());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        document.close();


        log.info("created the pdf file");
    }

    private void createTitle(String title) throws Exception{

        log.info("creating the title");
        Font font = new Font();
        font.setFamily(FontFactory.COURIER_BOLD);
        font.setColor(BaseColor.DARK_GRAY);
        font.setSize(22);

        Chunk chunk = new Chunk(title);
        chunk.setUnderline(BaseColor.GREEN,3,0,-5,0,0);
        chunk.setFont(font);

        Paragraph paragraph =  new Paragraph(chunk);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.setSpacingAfter(20);

        document.add(paragraph);
        log.info("Added the title paragraph");
    }

    private void createImage(byte[] bytes) throws Exception{

        log.info("Adding image to pdf sheet");
//        File file = File.createTempFile("image",".png");
//        File dummyFile = new File("/Volumes/study/projects/code_created_files/image"+bytes.length+".jpeg");
//        if(dummyFile.createNewFile()){
//            System.out.println(dummyFile.getAbsolutePath());
//            BufferedImage image1 = ImageIO.read(new ByteArrayInputStream(bytes));
//            System.out.println(dummyFile.getAbsolutePath()+" 2 "+image1);
//
//            ImageIO.write(image1, "jpeg", dummyFile);
//            System.out.println(dummyFile.getAbsolutePath()+" 3");
//        }

        Image image = Image.getInstance(bytes);

        image.scaleAbsolute(250,150);
        image.setAlignment(Element.ALIGN_CENTER);
        document.add(image);
        log.info("Added image to the pdf sheet");
    }

    private void createDescription(String description) throws Exception{

        log.info("Adding body to the page");
        Paragraph paragraph = new Paragraph();
        Chunk chunk1 = new Chunk(description);
        paragraph.add(chunk1);
        paragraph.setFirstLineIndent(50);

        document.add(paragraph);
        log.info("Added body of the page");
        document.newPage();

    }


}
