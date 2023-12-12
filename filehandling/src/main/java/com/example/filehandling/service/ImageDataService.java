package com.example.filehandling.service;

import com.example.filehandling.dto.CricketerDto;
import com.example.filehandling.dto.CricketerResponseDto;
import com.example.filehandling.entity.Cricketer;
import com.example.filehandling.excelUtility.ExcelWriter;
import com.example.filehandling.excelUtility.CricketerExcel;
import com.example.filehandling.exception.CustomException;
import com.example.filehandling.pdfUtility.PDFWriter;
import com.example.filehandling.repository.ImageDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ImageDataService {

    @Autowired
    private ImageDataRepository imageDataRepository;

    @Autowired
    private ExcelWriter excelWriter;

    @Autowired
    private PDFWriter pdfWriter;

    public String saveimageData(byte[] imageData, String name, String description){


        Cricketer cricketer1 = new Cricketer();
        cricketer1.setImageData(imageData);
        cricketer1.setName(name);
        cricketer1.setDescription(description);

        imageDataRepository.save(cricketer1);
        return "Saved the image data successfully";
    }

    public byte[] getImage(Long id) throws CustomException {

        Optional<Cricketer> imageData = imageDataRepository.findById(id);

        if(imageData.isPresent()){
            return imageData.get().getImageData();
        }else{
            log.info("Could not find the image!! ");
            throw new CustomException("Could not find the image!!");
        }
    }

    public CricketerResponseDto getImageMetaData(){

        List<Cricketer> imageList = imageDataRepository.findAll();
        List<CricketerDto> cricketerDtoList = new ArrayList<>();

        imageList.forEach((item)->{

            CricketerDto cricketerDto = new CricketerDto();
            BeanUtils.copyProperties(item, cricketerDto);
            cricketerDtoList.add(cricketerDto);

        });
        CricketerResponseDto cricketerResponseDto = new CricketerResponseDto();
        cricketerResponseDto.setCricketerDtoList(cricketerDtoList);
        return cricketerResponseDto;
    }

    public Resource generateExcelFile() throws Exception {

        List<Cricketer> cricketerList = imageDataRepository.findAll();
        List<CricketerExcel> cricketerExcelList = new ArrayList<>();

        cricketerList.forEach((item)->{
            CricketerExcel cricketerExcel = new CricketerExcel();
            BeanUtils.copyProperties(item, cricketerExcel);
            cricketerExcelList.add(cricketerExcel);
        });

        File file =   new File("/Volumes/study/projects/code_created_files/image_data_excel.xlsx");
        if(file.createNewFile()){
            log.info("File has been created to save Excel file");
        }

        log.info(cricketerExcelList +"");
        excelWriter.generateExcelFile(file, cricketerExcelList);
        log.info("Created the excel file");
        ByteArrayResource byteArrayResource = new ByteArrayResource(Files.readAllBytes(file.toPath()));
        return byteArrayResource;

    }

    public Resource generatePdf() throws Exception{

        List<Cricketer> cricketerList = imageDataRepository.findAll();

        File file = new File("/Volumes/study/projects/code_created_files/testFile.pdf");

        pdfWriter.createPdfFile(cricketerList,file);

        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(file.toPath()));

        return resource;

    }

}
