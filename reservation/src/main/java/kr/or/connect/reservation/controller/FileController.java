package kr.or.connect.reservation.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
            FileOutputStream fos = new FileOutputStream("c:/tmp/reviewImage/" + fileName);
            InputStream is = file.getInputStream();
        ){
        	int readCount;
        	byte[] buffer = new byte[1024];
        	while((readCount = is.read(buffer)) != -1){
        		fos.write(buffer,0,readCount);
        	}
            ReviewWrite reviews = getReviewData(formData, file, uuid);
            reviewWriteService.addReviewData(reviews, file.isEmpty());
            
        }catch(Exception ex){
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
		reviews.setSaveFileName("/tmp/reviewImage/"+ uuid + file.getOriginalFilename());
		reviews.setContentType(file.getContentType());

		return reviews;
	}

	@GetMapping(path ="/downloadImage.do")
	public void download(HttpServletRequest request,HttpServletResponse response){

		int id = Integer.parseInt(request.getParameter("fileId"));

		if(id == 0) { // 파일 없으면 종료
			return;
		}
		
		// 리뷰 이미지 경로 불러오기 
		String saveFileName = reviewWriteService.selectReviewImageFile(id);
		File file = new File("C:\\"+saveFileName);
		String fileName = file.getName();
		long fileLength = file.length(); 
		  
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Content-Length", "" + fileLength);
        response.setHeader("Pragma", "no-cache;");
        response.setHeader("Expires", "-1;");
		
        try(
            FileInputStream fis = new FileInputStream(file);
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

}
