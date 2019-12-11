package com.picaboo.nor.franchisee.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

/*
 * 가맹점 정보 입력 폼 VO
 *  cpu, vga, ram: 가맹점 pc 사양 정보
 *  franchiseePicList: 가맹점 사진 여러개
 *  removeFile: 파일 삭제 체크박스
 */

@Data
public class FranchiseeInfoForm {
	private String franchiseeNo;
	private String cpu;
	private String vga;
	private String ram;
	private List<MultipartFile> franchiseePicList;
	private List<Integer> removeFileList;
}
