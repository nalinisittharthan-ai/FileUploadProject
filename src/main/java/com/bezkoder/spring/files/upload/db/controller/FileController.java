package com.bezkoder.spring.files.upload.db.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bezkoder.spring.files.upload.db.service.FileStorageService;
import com.bezkoder.spring.files.upload.db.message.ResponseFile;
import com.bezkoder.spring.files.upload.db.message.ResponseMessage;
import com.bezkoder.spring.files.upload.db.model.FileDB;
import com.bezkoder.spring.files.upload.db.model.Trade;

@Controller
@CrossOrigin("http://localhost:4200")
public class FileController {

  @Autowired
  private FileStorageService storageService;

  @PostMapping("/upload")
  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
    String message = "";
    try {
      storageService.store(file);

      message = "Uploaded the file successfully: " + file.getOriginalFilename();
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    } catch (Exception e) {
      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
    }
  }

  @GetMapping("/files")
  public ResponseEntity<List<ResponseFile>> getListFiles() {
    List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {
      String fileDownloadUri = ServletUriComponentsBuilder
          .fromCurrentContextPath()
          .path("/files/")
          .path(dbFile.getId())
          .toUriString();

      return new ResponseFile(
          dbFile.getName(),
          fileDownloadUri,
          dbFile.getType(),
          dbFile.getData().length);
    }).collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK).body(files);
  }

  @GetMapping("/files/{id}")
  public ResponseEntity<byte[]> getFile(@PathVariable String id) {
    FileDB fileDB = storageService.getFile(id);

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
        .body(fileDB.getData());
  }
 
 
  
  
  @GetMapping("/records")
  public ResponseEntity<List<Trade>> getListFiles123() {
	  
	  List<Trade> ls=new ArrayList<>();
	   BigDecimal b=new BigDecimal(34);
	   Trade t1=new Trade();
	   t1.setTradeId("1");
	   t1.setQuantity(34);
	   t1.setPrice(b);
	   t1.setSide("north");
		
	   Trade t2=new Trade();
	   t2.setTradeId("2");
	   t2.setQuantity(22);
	   t2.setPrice(b);
	   t2.setSide("sss");
	   
	   Trade t3=new Trade();
	   t3.setTradeId("2");
	   t3.setQuantity(22);
	   t3.setPrice(b);
	   t3.setSide("sss");
	  // t3.add(t2);ls.add(t1);
	
	   
	   Trade t4=new Trade();
	   t4.setTradeId("1");
	   t4.setQuantity(34);
	   t4.setPrice(b);
	   t4.setSide("north");
		
	   Trade t5=new Trade();
	   t5.setTradeId("2t5");
	   t5.setQuantity(222233);
	   t5.setPrice(b);
	   t5.setSide("sss22222t5");
	   
	   Trade t6=new Trade();
	   t6.setTradeId("2");
	   t6.setQuantity(22);
	   t6.setPrice(b);
	   t6.setSide("sss");
	   ls.add(t6);ls.add(t1); ls.add(t3); ls.add(t2);ls.add(t1); ls.add(t3);
	   ls.add(t2);ls.add(t1); ls.add(t3); ls.add(t2);ls.add(t1); ls.add(t3);
	   ls.add(t2);ls.add(t6); ls.add(t3);
	   ls.add(t6);ls.add(t1); ls.add(t4);
    
    return ResponseEntity.status(HttpStatus.OK).body(ls);
  }
}
