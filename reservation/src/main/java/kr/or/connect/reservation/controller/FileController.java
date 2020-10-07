package kr.or.connect.reservation.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.or.connect.reservation.dto.ReviewWrite;
import kr.or.connect.reservation.service.ReviewWriteService;

@Controller
public class FileController {

	@Autowired
	private ReviewWriteService reviewWriteService;
	
	@PostMapping("/uploadReview")
	public String upload(MultipartHttpServletRequest formData) {
		
		MultipartFile file = formData.getFile("imageFile");
		String uuid = UUID.randomUUID().toString(); // 중복없는 fileName 생성
		String fileName = uuid + file.getOriginalFilename();
		String directURL = "redirect:/myreservation?reservationEmail=";
		String reservationEmail = formData.getParameter("reservationEmail");
		directURL += reservationEmail;
		

		
        try(
                // 윈도우일 경우
                FileOutputStream fos = new FileOutputStream("c:/tmp/" + fileName);
                InputStream is = file.getInputStream();
        ){
        	    int readCount = 0;
        	    byte[] buffer = new byte[1024];
            while((readCount = is.read(buffer)) != -1){
                fos.write(buffer,0,readCount);
            }
            ReviewWrite reviews = getReviewData(formData, file, uuid);
            reviewWriteService.addReviewData(reviews, file.isEmpty());
            
        }catch(Exception ex){
    		System.out.println("다이렉트주소: " + directURL);
    		System.out.println("파일 이름 : " + file.getOriginalFilename());
    		System.out.println("파일 크기 : " + file.getSize());
    		System.out.println("uuid: " + uuid);
            throw new RuntimeException("file Save Error");
        }
	
		return directURL;
	}	
	
	private ReviewWrite getReviewData(MultipartHttpServletRequest formData, MultipartFile file, String uuid) {
		ReviewWrite reviews = new ReviewWrite();
		
		reviews.setReservationId(Integer.parseInt(formData.getParameter("reservationId")));
		reviews.setProductId(Integer.parseInt(formData.getParameter("productId")));
		reviews.setScore(Double.parseDouble(formData.getParameter("score")));
		reviews.setComment(formData.getParameter("comment"));
		
		reviews.setFileName(uuid + file.getOriginalFilename());
		reviews.setSaveFileName("image/"+ uuid + file.getOriginalFilename());
		reviews.setContentType(file.getContentType());
		
		
		System.out.println("---------getReviewData--------------");
		System.out.println("reservationId: "+ reviews.getReservationId());
		System.out.println("productId: "+ reviews.getProductId());
		System.out.println("score: "+ reviews.getScore());
		System.out.println("reviewContent: "+ reviews.getComment());
		
		System.out.println("FileName: "+ reviews.getFileName());
		System.out.println("SaveFileName: "+ reviews.getSaveFileName());
		System.out.println("ContentType: "+ reviews.getContentType());
		System.out.println("---------getReviewData- END-------------");

		return reviews;
	}

	@GetMapping(path ="/downloadImage.do")
	public void download(@RequestParam("FileId") String FileId, HttpServletRequest request,HttpServletResponse response) {
		int fileId = Integer.parseInt(request.getParameter("FileId"));
		
		System.out.println("파일 다운로드 컨트롤러");
		System.out.println(fileId);
		System.out.println(fileId == 0);
		
		if(fileId == 0) { 
			return;
		}
/*		
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-Type", contentType);
        response.setHeader("Content-Length", "" + fileLength);
        response.setHeader("Pragma", "no-cache;");
        response.setHeader("Expires", "-1;");
        */
	}
/*	
	@GetMapping("/download")
	public void download(HttpServletResponse response) {

        // 직접 파일 정보를 변수에 저장해 놨지만, 이 부분이 db에서 읽어왔다고 가정한다.
		String fileName = "connect.png";
		String saveFileName = "c:/tmp/connect.png"; // 맥일 경우 "/tmp/connect.png" 로 수정
		String contentType = "image/png";
		int fileLength = 1116303;
		
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-Type", contentType);
        response.setHeader("Content-Length", "" + fileLength);
        response.setHeader("Pragma", "no-cache;");
        response.setHeader("Expires", "-1;");
        
        try(
                FileInputStream fis = new FileInputStream(saveFileName);
                OutputStream out = response.getOutputStream();
        ){
        	    int readCount = 0;
        	    byte[] buffer = new byte[1024];
            while((readCount = fis.read(buffer)) != -1){
            		out.write(buffer,0,readCount);
            }
        }catch(Exception ex){
            throw new RuntimeException("file Save Error");
        }
	}
	*/
}
