package com.picaboo.nor.franchisee.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.picaboo.nor.franchisee.mapper.FranchiseeMapper;
import com.picaboo.nor.franchisee.vo.Food;
import com.picaboo.nor.franchisee.vo.FoodForm;
import com.picaboo.nor.franchisee.vo.FoodPic;
import com.picaboo.nor.franchisee.vo.FoodReservationList;
import com.picaboo.nor.franchisee.vo.FoodStatement;
import com.picaboo.nor.franchisee.vo.Franchisee;
import com.picaboo.nor.franchisee.vo.FranchiseeFAQ;
import com.picaboo.nor.franchisee.vo.FranchiseeFAQPage;
import com.picaboo.nor.franchisee.vo.FranchiseeForm;
import com.picaboo.nor.franchisee.vo.FranchiseeInfoForm;
import com.picaboo.nor.franchisee.vo.FranchiseeOwner;
import com.picaboo.nor.franchisee.vo.FranchiseePic;
import com.picaboo.nor.franchisee.vo.FranchiseeQnA;
import com.picaboo.nor.franchisee.vo.FranchiseeSpec;
import com.picaboo.nor.franchisee.vo.Seat;
import com.picaboo.nor.franchisee.vo.SeatReservationList;
import com.picaboo.nor.franchisee.vo.Spec;
import com.picaboo.nor.franchisee.vo.TodayStatement;
import com.picaboo.nor.franchisee.vo.TotalStatement;
import com.picaboo.nor.ftp.FTPService;
import com.picaboo.nor.membership.vo.Address;

@Service
@Transactional
public class FranchiseeServiceImpl implements FranchiseeService{
	@Autowired FranchiseeMapper franchiseeMapper;
	// QnA디테일	
	public List<FranchiseeQnA> getQnaDetail(int qnaNo) {
		return franchiseeMapper.selectQnaDetail(qnaNo);
	}	
	// 좌석 예약 취소
	@Override
	public int cancelSeatReservation(SeatReservationList seatReservationList) {
		franchiseeMapper.cancelInsertSeatReservation(seatReservationList);
	    franchiseeMapper.cancelUpdateSeatReservation(seatReservationList);	
		return franchiseeMapper.delSeatReservation(seatReservationList);
	}	
	// 좌석 예약 확인
	@Override
	public int delSeatReservation(SeatReservationList seatReservationList) {
		 // 1. del_seat_reservation 테이블에 INSERT 수행 
		franchiseeMapper.insertSeatReservation(seatReservationList);
	    // 3. seat 테이블에 use를 UPDATE 수행 Y로 업데이트
	    franchiseeMapper.updateSeatReservation(seatReservationList);
	    
		return franchiseeMapper.delSeatReservation(seatReservationList);
	}
	// 좌석 예약 서비스 확인
	public List<SeatReservationList> getSeatReservationList(String franchiseeNo) {
		System.out.println("service Impl :  " + franchiseeNo);
		return franchiseeMapper.selectSeatReservationList(franchiseeNo);
	}
	
	// 상품에따른 가맹점별 매출 현황	
	public List<TotalStatement> getTotalStatementList(String ownerNo) {
		System.out.println(ownerNo);
		return franchiseeMapper.selectTotalStatementList(ownerNo);
	}	
	// 오늘 매출 가맹점별 매출 현황
		public List<TodayStatement> getTodayStatementList(String ownerNo) {
			System.out.println(ownerNo);
			return franchiseeMapper.selectTodayStatementList(ownerNo);
		}
	
	//음식 주문 통계
	public List<FoodStatement> getFoodfoodStatementList(FoodStatement foodStatement) {
		return franchiseeMapper.selectFoodfoodStatementList(foodStatement);
	}
	
	// 가맹점 상품 수정
	@Override
	public int modifyFranchiseeFood(FoodForm foodForm) {
		System.out.println("Service foodForm: " + foodForm);
		// 리턴 변수
		int rows = 0;
		// foodForm -> food, foodPic 으로 분리
		
		// 1. UPDATE food
		Food food = new Food();
		food.setFoodNo(foodForm.getFoodNo());
		food.setFoodName(foodForm.getFoodName());
		food.setFoodCategory(foodForm.getFoodCategory());
		food.setFoodPrice(foodForm.getFoodPrice());
		// UPDATE 수행
		franchiseeMapper.updateFranchiseeFood(food);
		
		// 2. UPDATE foodPic
		// 삭제할 파일 이름 저장
		String delFileName = "";
		FoodPic delFoodPic = franchiseeMapper.selectFoodPic(foodForm.getFoodNo());
		if(delFoodPic != null)
			delFileName = delFoodPic.getFileName();
		System.out.println("원래 사진: "+ delFileName);
		// 업로드 파일
		MultipartFile mf = foodForm.getFoodPic();
		
		String contentType = mf.getContentType();
		String originName = mf.getOriginalFilename();
		long size = mf.getSize();
		// 파일 확장자명
		String extension = originName.substring(originName.lastIndexOf(".")+1);
		// 랜덤한 UUID에 -를 빼고 원래 파일이름의 확장자만 더해서 저장할 파일이름을 생성
		String uploadFileName = UUID.randomUUID().toString().replace("-", "")+"."+extension;
		
		System.out.println("contentType: " + contentType);
		System.out.println("originName: " + originName);
		System.out.println("size: " + size);
		System.out.println("extension: " + extension);
		System.out.println("fileName: " + uploadFileName);
		
		// 2-1 Insert할 foodPic
		FoodPic foodPic = new FoodPic();
		foodPic.setContentType(contentType);
		foodPic.setOriginName(originName);
		foodPic.setSize(size);
		foodPic.setExtension(extension);
		foodPic.setFileName(uploadFileName);
		foodPic.setFoodNo(foodForm.getFoodNo());
		
		// 2-2. 업로드 파일이 있을 경우 파일 삭제, 업로드 수행
		if(size != 0) { 
			// 파일 디렉토리
			String dir = "/www/food/";
			
			boolean delResult = false;
			boolean upResult = false;
			FTPService ftp = new FTPService();
			
			// db에서 DELETE 후 INSERT 수행
			franchiseeMapper.deletefranchiseeFoodPic(foodForm.getFoodNo());
			franchiseeMapper.insertFranchiseeFoodPic(foodPic);
			
			try {
				
				// MultipartFile을 File로 변환
				File convertFile = new File(uploadFileName);
				convertFile.createNewFile();
				FileOutputStream fos = new FileOutputStream(convertFile);
				fos.write(mf.getBytes());
				fos.close();
				
				// ftp 접속
				ftp.connectFTP(dir);
				// 파일 삭제
				delResult = ftp.deleteFile(delFileName);
				if(delResult) {
					System.out.println("삭제 성공");
					rows++;
				} else {
					System.out.println("삭제 실패");
				}
				// 파일 업로드
				upResult = ftp.uploadFile(convertFile, foodPic.getFileName());
				if(upResult) {
					System.out.println("업로드 성공");
					rows++;
				} else {
					System.out.println("업로드 실패");
				}
				// ftp 연결 해제
				ftp.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
				// 예외 발생 시 rollback 시키기 위해 런타임 예외 발생
				throw new RuntimeException();
			}
		}
		
		return rows;
		
	}
	
	// 가맹점 상품 수정 정보 조회
	@Override
	public Map<String, Object> getFranchiseeFoodOne(int foodNo) {
		Map<String, Object> foodInfo = new HashMap<String, Object>();
		// 가맹점 상품 사진
		FoodPic foodPic = franchiseeMapper.selectFoodPic(foodNo);
		System.out.println("foodPic: " + foodPic);
		foodInfo.put("foodPic", foodPic);
		// 가맹점 상품 정보
		Food food = franchiseeMapper.selectFood(foodNo);
		System.out.println("foodPic: " + food);
		foodInfo.put("food", food);
		// 사진 경로
		String uploadPath = "http://ahp7242.cdn3.cafe24.com/food/";
		foodInfo.put("uploadPath", uploadPath);
		
		return foodInfo;
	}
	
	// 가맹점 상품 삭제
	public int removeFranchiseeFood(int foodNo) {
		System.out.println("Service foodNo: " + foodNo);
		
		
		// 삭제할 파일 이름 저장
		FoodPic foodPic = franchiseeMapper.selectFoodPic(foodNo);
		String fileName = "";
		if(foodPic != null)
			fileName = foodPic.getFileName();
		
		// 1. db에서 삭제
		int rows = 0;
		// 1-1. 상품 사진 삭제, 외래키 걸려있어서 먼저 수행해야 함.
		rows += franchiseeMapper.deletefranchiseeFoodPic(foodNo);
		// 1-2. 상품 삭제
		rows += franchiseeMapper.deletefranchiseeFood(foodNo);
		
		// 2. 파일 삭제
		
		// 파일 삭제 디렉토리
		String dir = "/www/food/";
		
		boolean result = false;
		FTPService ftp = new FTPService();
		try {
			ftp.connectFTP(dir);
			result = ftp.deleteFile(fileName);
			ftp.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			// 예외 발생 시 rollback 시키기 위해 런타임 예외 발생
			throw new RuntimeException();
		}
		
		if(result) {
			System.out.println("삭제 성공");
			rows++;
		} else {
			System.out.println("삭제 실패");
		}
		
		return rows;
	}
	
	// 주문완료 음식 삭제
	@Override
	public int delFoodReservation(int reservationNo) {
		franchiseeMapper.addFoodReservation(reservationNo);
		return franchiseeMapper.delFoodReservation(reservationNo);
	}
	// 주문취소
	@Override
	public int cancelFoodReservation(int reservationNo) {
		franchiseeMapper.addFoodReservationCancel(reservationNo);
		return franchiseeMapper.delFoodReservationCancel(reservationNo);
	}	
	
	
	// 음식 주문 서비스 확인
	public List<FoodReservationList> getFoodReservationList(String franchiseeNo) {
		System.out.println("service Impl :  " + franchiseeNo);
		return franchiseeMapper.selectFoodReservationList(franchiseeNo);
	}
	
	// 가맹점 상품 정보 가져오기
	@Override
	public Map<String, Object> getFranchiseeFood(String franchiseeNo, String foodCategory) {
		System.out.println("Service franchiseNo: " + franchiseeNo);
		
		// 상품 리스트와 상품 사진 리스트를 가지는 맵
		Map<String, Object> franchiseeFood = new HashMap<String, Object>();
		
		// 상품 리스트
		List<Food> foodList = franchiseeMapper.selectFoodList(franchiseeNo, foodCategory);
		franchiseeFood.put("foodList", foodList);
		// 상품 사진 리스트
		List<FoodPic> foodPicList = franchiseeMapper.selectFoodPicList(franchiseeNo);
		franchiseeFood.put("foodPicList", foodPicList);
		
		String uploadPath = "http://ahp7242.cdn3.cafe24.com/food/";
		franchiseeFood.put("uploadPath", uploadPath);
		
		franchiseeFood.put("franchiseeNo", franchiseeNo);
		
		return franchiseeFood;
	}
	
	// 가맹점 음식 추가
	@Override
	public int addFranchiseeFood(FoodForm foodForm) {
		System.out.println("Service foodForm: " + foodForm);
		
		// 성공한 행의 수를 리턴할 변수
		int rows = 0;
		// foodForm -> Food, FoodPic 으로 나눔
		
		// 1. Food
		Food food = new Food();
		food.setFoodName(foodForm.getFoodName());
		food.setFoodPrice(foodForm.getFoodPrice());
		food.setFoodCategory(foodForm.getFoodCategory());
		food.setFranchisee(new Franchisee());
		food.getFranchisee().setFranchiseeNo(foodForm.getFranchiseeNo());
		// db에 저장
		rows += franchiseeMapper.insertFranchiseeFood(food);
		
		// 2. FoodPic
		
		MultipartFile mf = foodForm.getFoodPic();
		
		String originName = mf.getOriginalFilename();
		String contentType = mf.getContentType();
		
		// 이미지 타입 아닐경우 리턴
		if(!contentType.equals("image/jpeg") && !contentType.equals("image/png") && 
				!contentType.equals("image/gif") && !contentType.equals("image/svg+xml") ) {
			return -1;
		}
		
		FoodPic foodPic = new FoodPic();
		foodPic.setFoodNo(food.getFoodNo());
		foodPic.setContentType(contentType);
		foodPic.setSize(mf.getSize());
		foodPic.setOriginName(originName);

		// 파일 확장자명
		String extension = originName.substring(originName.lastIndexOf(".")+1);
		// 랜덤한 UUID에 -를 빼고 원래 파일이름의 확장자만 더해서 저장할 파일이름을 생성
		String saveFileName = UUID.randomUUID().toString().replace("-", "")+"."+extension;
		
		// 확장자명
		foodPic.setExtension(extension);
		// 파일명
		foodPic.setFileName(saveFileName);
		
		System.out.println("foodPic: " + foodPic.toString());
		// 업로드 결과
		boolean result = false;
		try {
			// db에 저장
			rows += franchiseeMapper.insertFranchiseeFoodPic(foodPic);
			
			// MultipartFile을 File로 변환
			File convertFile = new File(saveFileName);
			convertFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(convertFile);
			fos.write(mf.getBytes());
			fos.close();
			
			// 업로드 디렉토리
			String dir = "/www/food/";
			// CDN에 업로드 시작
			System.out.println("Upload Start");
			FTPService ftpUploader = new FTPService();
			// FTP 연결
			ftpUploader.connectFTP(dir);
			// 파일 업로드
			result = ftpUploader.uploadFile(convertFile, saveFileName);
			// FTP 연결 해제
			ftpUploader.disconnect();
			System.out.println("Done");
			
		} catch (Exception e) {
			e.printStackTrace();
			// 파일을 저장할때 예외가 나면 rollback 시키기 위해서 강제로 런타임 예외 발생시킴.
			throw new RuntimeException();
		}
		
		if(result) {
			System.out.println("업로드 성공");
		} else {
			System.out.println("업로드 실패");
		}
		
		return rows;
	}
	
	// qna 답변 확인가능 
	@Override
	public List<FranchiseeQnA> getFranchiseeQnaList(String ownerNo) { 
		return franchiseeMapper.selectFranchiseeQnaList(ownerNo);
	}
	
	// 회원의 상세정보를 수정하는 서비스
	@Override
	public int modifyFranchiseeOwner(FranchiseeOwner franchiseeOwner) {
		System.out.println("service Impl :  " + franchiseeOwner);
		return franchiseeMapper.updateFranchiseeOwner(franchiseeOwner);
	}
	
	// 회원의 상세정보를 가져오는 서비스
	@Override
	public FranchiseeOwner detailFranchiseeOwner(String ownerNo) {
		return franchiseeMapper.selectFranchiseeOwner(ownerNo);
	}
	
	// 썸네일 사진 가져오기
	@Override
	public Map<String, Object> getFranchiseeThumbnail(List<Franchisee> franchiseeList) {
		System.out.println("getFranchiseeThumbnail franchiseeList: " + franchiseeList);
		// 썸네일 사진과 경로를 가지는 맵 리턴
		Map<String, Object> thumbnailInfo = new HashMap<String, Object>();
		// 저장 경로
		String uploadPath = "http://ahp7242.cdn3.cafe24.com/franchisee/";
		thumbnailInfo.put("uploadPath", uploadPath);
		// 썸네일 사진
		List<FranchiseePic> thumbnailList = franchiseeMapper.selectFranchiseeThumbnail();
		
		thumbnailInfo.put("thumbnailList", thumbnailList);
		
		return thumbnailInfo;
	}
	
	// 가맹점 정보 수정
	@Override
	public int modifyFranchiseeInfo(FranchiseeInfoForm franchiseeInfoForm) {
		// 리턴 변수
		int rows = 0;
		//franchiseeInfoForm을 franchiseePic, franchiseeSpec으로 분리
		System.out.println("Service modify franchiseeInfoForm: " + franchiseeInfoForm);
		// 1. franchiseeSpec UPDATE
		FranchiseeSpec franchiseeSpec = new FranchiseeSpec();
		franchiseeSpec.setFranchiseeNo(franchiseeInfoForm.getFranchiseeNo());
		franchiseeSpec.setCpu(franchiseeInfoForm.getCpu());
		franchiseeSpec.setVga(franchiseeInfoForm.getVga());
		franchiseeSpec.setRam(franchiseeInfoForm.getRam());
		
		franchiseeMapper.updateFranchiseeSpec(franchiseeSpec);
		// FTP 파일 경로
		String dir = "/www/franchisee/";
		// 2. franchiseePic DELETE
		// 삭제할 파일 목록 저장
		List<Integer> deletePicList = franchiseeInfoForm.getRemoveFileList();
		// 목록이 null이 아닐경우 파일 삭제 체크한 사진 삭제 
		if(deletePicList != null) {
			// 삭제 리스트 반복문
			for(int picNo : deletePicList) {
				// 삭제할 사진 가져옴
				FranchiseePic deletePic = franchiseeMapper.selectFranchiseePicOne(picNo);
				String storeFileName = deletePic.getFileName();
				// 삭제 결과
				boolean result = false;
				try {
					// db에서 삭제
					rows += franchiseeMapper.deleteFranchiseePic(picNo);
					
					// CDN에서 삭제 시작
					System.out.println("Delete Start");
					FTPService ftpUploader = new FTPService();
					// FTP 연결
					ftpUploader.connectFTP(dir);
					// 파일 삭제
					result = ftpUploader.deleteFile(storeFileName);
					// FTP 연결 해제
					ftpUploader.disconnect();
					System.out.println("Done");
					
				} catch (Exception e) {
					e.printStackTrace();
					// 파일 삭제할 때 예외발생 시 rollback 시키기 위해 강제로 런타임 예외 발생시킴.
					throw new RuntimeException();
				}
				if(result) {
					System.out.println("삭제 성공");
				} else {
					System.out.println("삭제 실패");
				}
			}
		}
		
		// 3. 사진 추가, franchiseePic INSERT
		// 파일 리스트 가져옴
		List<MultipartFile> picList = franchiseeInfoForm.getFranchiseePicList();
		// 파일로 저장할 사진 리스트
		List<FranchiseePic> fileList = new ArrayList<FranchiseePic>();
		// 사진 리스트에서 하나씩 정보를 추출하여 db에 저장
		for(MultipartFile mf : picList) {
			String contentType = mf.getContentType();
			String name = mf.getName();
			String originName = mf.getOriginalFilename();
			long size = mf.getSize();
			// 파일 확장자명
			String extension = originName.substring(originName.lastIndexOf(".")+1);
			// 랜덤한 UUID에 -를 빼고 원래 파일이름의 확장자만 더해서 저장할 파일이름을 생성
			String saveFileName = UUID.randomUUID().toString().replace("-", "")+"."+extension;
						
			System.out.println("addFranchiseeInfo contentType: " + contentType);
			System.out.println("addFranchiseeInfo name: " + name);
			System.out.println("addFranchiseeInfo originName: " + originName);
			System.out.println("addFranchiseeInfo size: " + size);
			System.out.println("addFranchiseeInfo extension: " + extension);
			System.out.println("addFranchiseeInfo saveFileName: " + saveFileName);
			
			if(!contentType.equals("image/jpeg") && !contentType.equals("image/png") && 
					!contentType.equals("image/gif") && !contentType.equals("image/svg+xml") ) {
				return -1;
			}
			
			// franchiseePic으로  db에 저장
			FranchiseePic franchiseePic = new FranchiseePic();
			franchiseePic.setFranchiseeNo(franchiseeInfoForm.getFranchiseeNo());
			franchiseePic.setContentType(contentType);
			franchiseePic.setExtension(extension);
			franchiseePic.setFileName(saveFileName);
			franchiseePic.setOriginName(originName);
			franchiseePic.setSize(size);
			// 파일로 저장할 사진 목록에 추가
			fileList.add(franchiseePic);
			rows += franchiseeMapper.insertFranchiseePic(franchiseePic);
		}
		
		// 파일로 저장할 목록 인덱스
		int FileListIndex = 0;
		
		// CDN 서버에 파일 저장
		// SQL예외가 발생하면 파일이 저장되지 않아야 하므로 맨 마지막에 실행
		for(MultipartFile mf : picList) {
			
			// saveFileName을 가져오기 위한 객체
			FranchiseePic franchiseePic = fileList.get(FileListIndex);
			
			// 저장될 파일 이름
			String storeFileName = franchiseePic.getFileName();
			// 업로드 결과
			boolean result = false;
			try {
				// MultipartFile을 File로 변환
				String fileName = mf.getOriginalFilename();
				File convertFile = new File(fileName);
				convertFile.createNewFile();
				FileOutputStream fos = new FileOutputStream(convertFile);
				fos.write(mf.getBytes());
				fos.close();
				
				// CDN에 업로드 시작
				System.out.println("Upload Start");
				FTPService ftpUploader = new FTPService();
				// FTP 연결
				ftpUploader.connectFTP(dir);
				// 파일 업로드
				result = ftpUploader.uploadFile(convertFile, storeFileName);
				// FTP 연결 해제
				ftpUploader.disconnect();
				System.out.println("Done");
				
			} catch (Exception e) {
				e.printStackTrace();
				// 파일을 저장할때 예외가 나면 rollback 시키기 위해서 강제로 런타임 예외 발생시킴.
				throw new RuntimeException();
			}
			if(result) {
				System.out.println("업로드 성공");
			} else {
				System.out.println("업로드 실패");
			}
			// 인덱스 증가
			FileListIndex++;
		}

		return rows;
	}
	
	// QnA 등록
	@Override
	public int addFranchiseeQnA(FranchiseeQnA franchiseeQnA) {		
		return franchiseeMapper.insertFranchiseeQnA(franchiseeQnA);
	}
	
	// pc 사양 조회
	@Override
	public Map<String, Object> getSpec() {
		List<Spec> cpu = franchiseeMapper.selectSpec("C");
		List<Spec> vga = franchiseeMapper.selectSpec("V");
		List<Spec> ram = franchiseeMapper.selectSpec("R");
		
		Map<String, Object> spec = new HashMap<String, Object>();
		spec.put("cpu", cpu);
		spec.put("vga", vga);
		spec.put("ram", ram);
		
		System.out.println("Service spec: " + spec);
		
		return spec;
	}
	
	// FAQ 리스트 조회
	@Override
	public Map<String, Object> getFranchiseeFAQ(int currentPage, int rowPerPage, String searchWord) {
		
		// 페이징 코드, 검색어 입력
		// Mapper로 페이징 정보를 넘기기 위해 VO에 값 저장
		FranchiseeFAQPage franchiseeFAQPage = new FranchiseeFAQPage();
		franchiseeFAQPage.setRowPerPage(rowPerPage);
		franchiseeFAQPage.setBeginRow((currentPage-1)*rowPerPage);
		franchiseeFAQPage.setSearchWord(searchWord);
		List<FranchiseeFAQ> list = franchiseeMapper.selectFranchiseeFAQ(franchiseeFAQPage);
		System.out.println("serviceImpl List: "+list);
		System.out.println("IMPL 검색: "+searchWord);
		
		// 페이징 버튼을 위한 마지막 페이지 계산
		int totalRowCount = franchiseeMapper.selectFranchiseeFAQCount(searchWord);
		int lastPage = 0;
		if(totalRowCount % rowPerPage == 0) {
			lastPage = totalRowCount / rowPerPage;
		} else {
			lastPage = totalRowCount / rowPerPage + 1;
		}
		
		// 검색과, 페이징한 리스트와 현재 페이지 정보를 맵에 저장하여 리턴
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("currentPage", currentPage);
		map.put("totalRowCount", totalRowCount);
		map.put("lastPage", lastPage);
		map.put("searchWord", searchWord);
		return map;
	}
	
	// 가맹점 pc사양, 사진 조회
	@Override
	public Map<String, Object> getFranchiseeInfo(String franchiseeNo) {
		// FranchiseePic + franchiseeSpec => Map으로 franchiseeInfo 리턴 
		Map<String, Object> franchiseeInfo = new HashMap<String, Object>();
		
		// 가맹점 pc사양
		FranchiseeSpec franchiseeSpec = franchiseeMapper.selelctFranchiseeSpec(franchiseeNo);
		
		System.out.println("franchiseeSpec: " + franchiseeSpec);
		franchiseeInfo.put("franchiseeSpec", franchiseeSpec);
		
		// 가맹점 사진
		List<FranchiseePic> franchisePicList = franchiseeMapper.selectFranchiseePic(franchiseeNo);
		System.out.println("Service franchisePicList: " + franchisePicList);
		// 저장 경로
		String uploadPath = "http://ahp7242.cdn3.cafe24.com/franchisee/";
		
		franchiseeInfo.put("franchisePicList", franchisePicList);
		franchiseeInfo.put("uploadPath", uploadPath);
		
		return franchiseeInfo;
	}
	
	// 가맹점 정보 입력
	@Override
	public int addFranchiseeInfo(FranchiseeInfoForm franchiseeInfoForm) {
		// 성공한 처리수를 리턴할 변수
		int rows = 0;
		
		/*
		 *  업로드 가능한 이미지 타입 
		 
			MIME 타입	이미지 타입
			image/gif	GIF 이미지 (무손실 압축, PNG에 의해 대체됨)
			image/jpeg	JPEG 이미지
			image/png	PNG 이미지
			image/svg+xml	SVG 이미지 (벡터 이미지)
			
		 */
		
		// FranchiseeInfoForm 에서 -> franchiseePic, franchiseeSpec 으로 분리하여 처리
		
		// 1. FranchiseeSpec
		
		// FranchiseeInfo로 db에 저장
		FranchiseeSpec franchiseeSpec = new FranchiseeSpec();
		franchiseeSpec.setFranchiseeNo(franchiseeInfoForm.getFranchiseeNo());
		franchiseeSpec.setCpu(franchiseeInfoForm.getCpu());
		franchiseeSpec.setVga(franchiseeInfoForm.getVga());
		franchiseeSpec.setRam(franchiseeInfoForm.getRam());

		System.out.println("getCpu: "+ franchiseeSpec.getCpu());
		System.out.println("getVga: " + franchiseeSpec.getVga());
		System.out.println("getRam: " + franchiseeSpec.getRam());
		
		rows += franchiseeMapper.insertFranchiseeSpec(franchiseeSpec);
		
		// 2. FranchiseePic
		
		// 파일 리스트 가져옴
		List<MultipartFile> picList = franchiseeInfoForm.getFranchiseePicList();
		// 파일로 저장할 사진 리스트
		List<FranchiseePic> fileList = new ArrayList<FranchiseePic>();
		
		// 사진 리스트에서 하나씩 정보를 추출하여 db에 저장
		for(MultipartFile mf : picList) {
			String contentType = mf.getContentType();
			String name = mf.getName();
			String originName = mf.getOriginalFilename();
			long size = mf.getSize();
			// 파일 확장자명
			String extension = originName.substring(originName.lastIndexOf(".")+1);
			// 랜덤한 UUID에 -를 빼고 원래 파일이름의 확장자만 더해서 저장할 파일이름을 생성
			String saveFileName = UUID.randomUUID().toString().replace("-", "")+"."+extension;
						
			System.out.println("addFranchiseeInfo contentType: " + contentType);
			System.out.println("addFranchiseeInfo name: " + name);
			System.out.println("addFranchiseeInfo originName: " + originName);
			System.out.println("addFranchiseeInfo size: " + size);
			System.out.println("addFranchiseeInfo extension: " + extension);
			System.out.println("addFranchiseeInfo saveFileName: " + saveFileName);
			
			if(!contentType.equals("image/jpeg") && !contentType.equals("image/png") && 
					!contentType.equals("image/gif") && !contentType.equals("image/svg+xml") ) {
				return -1;
			}
			
			// franchiseePic으로  db에 저장
			FranchiseePic franchiseePic = new FranchiseePic();
			franchiseePic.setFranchiseeNo(franchiseeInfoForm.getFranchiseeNo());
			franchiseePic.setContentType(contentType);
			franchiseePic.setExtension(extension);
			franchiseePic.setFileName(saveFileName);
			franchiseePic.setOriginName(originName);
			franchiseePic.setSize(size);
			// 파일로 저장할 사진 목록에 추가
			fileList.add(franchiseePic);
			rows += franchiseeMapper.insertFranchiseePic(franchiseePic);
		}
		
		// 파일로 저장할 목록 인덱스
		int FileListIndex = 0;
		
		// CDN 서버에 파일 저장
		// SQL예외가 발생하면 파일이 저장되지 않아야 하므로 맨 마지막에 실행
		for(MultipartFile mf : picList) {
			
			// saveFileName을 가져오기 위한 객체
			FranchiseePic franchiseePic = fileList.get(FileListIndex);
			
			// 저장될 파일 이름
			String storeFileName = franchiseePic.getFileName();
			// 업로드 디렉토리
			String dir = "/www/franchisee/";
			// 업로드 결과
			boolean result = false;
			try {
				// MultipartFile을 File로 변환
				String fileName = mf.getOriginalFilename();
				File convertFile = new File(fileName);
				convertFile.createNewFile();
				FileOutputStream fos = new FileOutputStream(convertFile);
				fos.write(mf.getBytes());
				fos.close();
				
				// CDN에 업로드 시작
				System.out.println("Upload Start");
				FTPService ftpUploader = new FTPService();
				// FTP 연결
				ftpUploader.connectFTP(dir);
				// 파일 업로드
				result = ftpUploader.uploadFile(convertFile, storeFileName);
				// FTP 연결 해제
				ftpUploader.disconnect();
				System.out.println("Done");
				
			} catch (Exception e) {
				e.printStackTrace();
				// 파일을 저장할때 예외가 나면 rollback 시키기 위해서 강제로 런타임 예외 발생시킴.
				throw new RuntimeException();
			}
			if(result) {
				System.out.println("업로드 성공");
			} else {
				System.out.println("업로드 실패");
			}
			// 인덱스 증가
			FileListIndex++;
		}
		
		return rows;
	}
	
	// 가맹점 좌석 삭제
	@Override
	public int removeSeat(String franchiseeNo) {
		int rows = 0;
		rows += franchiseeMapper.deleteSeat(franchiseeNo);
		System.out.println("rows: " + rows);

		if(rows >0) {
			System.out.println("가맹점 좌석  "+rows+"개 삭제 성공");
		} else {
			System.out.println("가맹점 좌석 삭제 실패");
		}
		return rows;
	}
	
	// 가맹점 좌석정보 조회
	@Override
	public List<Seat> getSeat(String franchiseeNo) {
		//System.out.println("가맹정 좌석정보 조회"+ franchiseeNo);
		return franchiseeMapper.selectSeat(franchiseeNo);
	}
	
	// 가맹점 상세정보 조회
	@Override
	public Franchisee getFranchiseeOne(String franchiseeNo) {
		System.out.println("ServiceImpl.getFranchiseeOne franchiseeNo: " + franchiseeNo);
		return franchiseeMapper.selectFranchiseeOne(franchiseeNo);
	}
	
	// 가맹점 리스트 조회
	@Override
	public List<Franchisee> getFranchiseeList(String ownerNo) { 
		System.out.println("ServiceImpl.getFranchiseeOne ownerNo: " + ownerNo);
		return franchiseeMapper.selectFranchiseeList(ownerNo);
	}
	
	// 가맹점 등록
	@Override
	public int addUnverifiedFranchisee(FranchiseeForm franchiseeForm){
		
		// 가맹점 입력 폼 -> 가맹점 정보, 주소로 분리하여 처리
		System.out.println("Service franchiseeForm: " + franchiseeForm);
		// 리턴 변수 초기화
		int rows = 0;
		// 1. address
		Address address = new Address();
		address.setJibunAddress(franchiseeForm.getJibunAddress());
		address.setRoadAddress(franchiseeForm.getRoadAddress());
		address.setPostcode(franchiseeForm.getPostcode());
		address.setDetailAddress(franchiseeForm.getDetailAddress());
		
		rows += franchiseeMapper.insertAddress(address);
		
		// 2. franchisee
		Franchisee franchisee = new Franchisee();
		franchisee.setFranchiseeName(franchiseeForm.getFranchiseeName());
		franchisee.setFranchiseeCrn(franchiseeForm.getFranchiseeCrn());
		franchisee.setFranchiseePhone(franchiseeForm.getFranchiseePhone());
		franchisee.setOwnerNo(franchiseeForm.getOwnerNo());
		franchisee.setAddress(new Address());
		franchisee.getAddress().setAddressNo(address.getAddressNo());
		
		// 가맹점 번호: "유형" + 숫자7자리
		// 마지막 가맹점번호 저장
		String seqNo = franchiseeMapper.selectFranchiseeSeq();
		
		// NULL값 처리
		if(seqNo == null) {
			seqNo = String.format("%07d", 0);
		}
		
		System.out.println("seqNo: " + seqNo);
		
		// 증가시키기 위한 숫자만 잘라서 저장
		seqNo = seqNo.substring(1);
		// 숫자 증가
		seqNo = String.format("%07d", (Integer.parseInt(seqNo)+1));
		// 증가 후 유형문자 추가 
		String nextNo = "F"+seqNo;
		System.out.println("nextNo: " + nextNo);
		
		// 다음 가맹점 번호 객체에 저장
		franchisee.setFranchiseeNo(nextNo);
		// 다음 가맹점 번호로 insert 수행
		rows += franchiseeMapper.insertUnverifiedFranchisee(franchisee);
		// 마지막 가맹점 번호 갱신
		rows += franchiseeMapper.updateFranchiseeSeq(nextNo);
		
		return rows;
		
	}
	
		
	// 좌석 입력
	@Override
	public int addFranchiseeSeat(Map<String, String> seatMap) {
		System.out.println("FranchiseeServiceImpl.addFranchiseeSeat seatMap: " + seatMap);
		
		// 입력한 좌석 수 계산
		// 맵에 seatNo, seatRow, seatCols, franchiseeNo 가 4개니까 나누기 4
		int seatCount = seatMap.size()/4;
		System.out.println("seatCount: " + seatCount);
		int rows = 0;
		for(int i=0; i<seatCount; i++) {
			Seat seat = new Seat();
		    int seatNo = Integer.parseInt(seatMap.get("seatList["+i+"][seatNo]")); 
			int seatRow = Integer.parseInt(seatMap.get("seatList["+i+"][seatRow]"));
			int seatCols = Integer.parseInt(seatMap.get("seatList["+i+"][seatCols]"));
			String franchiseeNo = seatMap.get("seatList["+i+"][franchiseeNo]");
			
			seat.setSeatNo(seatNo); 
			seat.setSeatRow(seatRow); 
			seat.setSeatCols(seatCols);
			seat.setFranchisee(new Franchisee());
			seat.getFranchisee().setFranchiseeNo(franchiseeNo);
			
			// Map에서 좌석 하나씩 가져와 insert 수행
			rows += franchiseeMapper.insertFranchiseeSeat(seat);
			System.out.println("rows: " + rows);
		}
		
		// 반복문 수행 후 결과 리턴
		if(rows >0) {
			System.out.println("가맹점 좌석 "+rows+"개 입력 성공");
		} else {
			System.out.println("가맹점 좌석 입력 실패");
		}
		
		return rows;
	}
}
