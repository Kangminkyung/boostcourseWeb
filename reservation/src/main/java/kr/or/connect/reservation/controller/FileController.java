package kr.or.connect.reservation.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
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

import kr.or.connect.reservation.dto.FileInfo;
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
		reviews.setSaveFileName("/tmp/reviewImage/"+ uuid + file.getOriginalFilename());
		reviews.setContentType(file.getContentType());

		return reviews;
	}

	@GetMapping(path ="/downloadImage.do")
	public void download(HttpServletRequest request,HttpServletResponse response) throws IOException {
		System.out.println("파일 다운로드 컨트롤러");
		
		System.out.println("아이디: "+request.getParameter("fileId"));
		int id = Integer.parseInt(request.getParameter("fileId"));

		System.out.println(id);
		if(id == 0) { // 파일 없으면 종료
			return;
		}
		
		// 리뷰 이미지 경로 불러오기 
		String saveFileName = reviewWriteService.selectReviewImageFile(id);
		System.out.println(saveFileName); // tmp/reviewImage/eb1b2375-90d9-4711-9427-3e379f28033cimages.png

		File file = new File("C:\\"+saveFileName);
		System.out.println(file.getName());; // 1_map_1.png
		System.out.println(file.length()); // 0
		
		String fileName = file.getName();
		long fileLength = file.length(); 
		
		response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
        response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Content-Length", "" + fileLength);
        response.setHeader("Pragma", "no-cache;");
        response.setHeader("Expires", "-1;");
		
        Files.copy(file.toPath(), response.getOutputStream());
  /*      response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
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
