package kr.or.connect.reservation.dto;

import java.time.LocalDateTime;

// file_info 테이블과 displayInfoimage 테이블을 합쳐서 정의함

public class DisplayInfoImage {
	private int displayInfoImageId; // 전시 이미지 id
	private int displayInfoId; // 전시(display_info) id
	private int fileId; // 파일 id
	private String fileName; // 파일 이름
	private String displayInfoSaveFileName; // 파일 저장 위치 이름
	private String contentType; // 파일 확장자
	private boolean deleteFlag; //삭제 여부
	private LocalDateTime createDate; // 생성일 
	private LocalDateTime modifyDate; // 수정일 
	
	public int getDisplayInfoImageId() {
		return displayInfoImageId;
	}
	public void setDisplayInfoImageId(int displayInfoImageId) {
		this.displayInfoImageId = displayInfoImageId;
	}
	public int getDisplayInfoId() {
		return displayInfoId;
	}
	public void setDisplayInfoId(int displayInfoId) {
		this.displayInfoId = displayInfoId;
	}
	public int getFileId() {
		return fileId;
	}
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getDisplayInfoSaveFileName() {
		return displayInfoSaveFileName;
	}
	public void setDisplayInfoSaveFileName(String displaySaveFileName) {
		this.displayInfoSaveFileName = displaySaveFileName;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public boolean isDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public LocalDateTime getCreateDate() {
		return createDate;
	}
	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}
	public LocalDateTime getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(LocalDateTime modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	@Override
	public String toString() {
		return "DisplayInfoImage [displayInfoImageId=" + displayInfoImageId + ", displayInfoId=" + displayInfoId
			+ ", fileId=" + fileId + ", fileName=" + fileName + ", displayInfoSaveFileName=" + displayInfoSaveFileName + ", contentType="
			+ contentType + ", deleteFlag=" + deleteFlag + ", createDate=" + createDate + ", modifyDate=" + modifyDate
			+ "]";
	}
}
