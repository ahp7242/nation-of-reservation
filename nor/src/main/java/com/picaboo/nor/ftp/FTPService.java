package com.picaboo.nor.ftp;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.io.CopyStreamAdapter;
public class FTPService {
   // 저장할 서버 주소
    private static final String SERVER = "ahp7242.cdn3.cafe24.com";
    private static final String USER = "ahp7242";
    private static final int PORT = 21;
    private static final String PW = "z1x2c3v4!@";
    
    FTPClient ftp = null;
    
    // FTP 연결
    public void connectFTP(String dir) throws Exception{
        ftp = new FTPClient();
        ftp.setControlEncoding("UTF-8"); // 문자 코드를 UTF-8로 인코딩
        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        int reply;
        ftp.connect(SERVER, PORT); // 서버 연결
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new Exception("Exception in connecting to FTP Server");
        }
        ftp.login(USER, PW);
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        ftp.enterLocalPassiveMode();
        ftp.changeWorkingDirectory(dir);
        
    }
    // 파일 업로드
    public boolean uploadFile(File file, String storeFileName){
    	
	    if(file != null) {
	        long fileSize = getFileSize(file);
	        // CopyStream listener 생성
	        CopyStreamAdapter listener = new CopyStreamAdapter() {
	            @Override
	            public void bytesTransferred(long totalBytesTransferred, int bytesTransferred, long streamSize) {
	                int percent = (int)(totalBytesTransferred*100/fileSize);
	                if(percent%10 ==0){
	                    System.out.println("진행률:  "+percent+"%");
	                }
	            }
	        };
	        // CopyStream listener 등록, 쓰레드 방식
	        ftp.setCopyStreamListener(listener);
        }
    	boolean result = false;
        InputStream input = null;
        try {
            input = new FileInputStream(file);
            result = ftp.storeFile(storeFileName,input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return result;
    }
    
    // 파일 삭제
    public boolean deleteFile(String storeFileName) {
    	boolean result = false;
    	
    	try {    
    		result = ftp.deleteFile(storeFileName);//파일삭제
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         }
    	
    	return result;
    }
    
    // 파일 크기 리턴
    public long getFileSize(File file){
        long fileSize = file.length();
        return fileSize;
    }
    
    // FTP 연결 해제
    public void disconnect(){
        if (this.ftp.isConnected()) {
            try {
                this.ftp.logout();
                this.ftp.disconnect();
            } catch (IOException f) {
                f.printStackTrace();
            }
        }
    }
}
