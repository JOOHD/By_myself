package fileUpload;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

// 실질적으로 업로드된 파일을 저장한다.
public class UploadFileUtils {
    
    private static final Logger log = LoggerFactory.getLogger(UploadFileUtils.class);

    public static String uploadFile(String uploadPath, String originalName, byte[] fileData) throws Exception {

        // 겹치지지 않는 파일명을 위한 유니크한 키값 생성
        UUID uid = UUID.randomUUID();

        // 실제 저장할 파일명 = UUID + _ + 원본파일명
        String saveName = uid.toString() + "_" + originalName;

        // 파일을 저장한 폴더 생성(년 월 일 기준)
        String savePath = calcPath(uploadPath);

        // 저장할 파일 준비 = 기본 저장경로 + 날짜경로 + UUID_파일명
        File target = new File(uploadPath + savePath, saveName);

        // 파일을 저장
        /* 
            FileCopyUtils.copy(inputStream in, outputStream out)
            inputStream in으로 파일을 받고, outputStream out으로 경로 지정 후, 파일 생성
        */ 

        // fileDate를 파일객체에 복사  
        FileCopyUtils.copy(fileData, target);

        // 파일 확장자 추출
        String formatName = originalName.substring(orginalName.lastIndexOf(".") +1);

        // 업로드 파일명 : 썸네일 이미지 파일명 or 일반 파일명
        String uploadedFileName = null;

        // 확장자에 따라 썸네일 이미지 생성 or 일반 파일 아이콘 생성.
        if(MediaUtils.getMediaType(formatName) != null) {

            // 썸네일 이미지 생성, 썸네일 이미지 파일명
            uploadedFileName = makeThumbnail(uploadPath, savePath, saveName);
        
        } else {

            // 파일 아이콘 생성
            uploadedFileName = makeIcon(uploadPath, savePath, saveName);
        }

        // uploadedFileName 는 썸네일명으로 화면에 전달한다, 업로드 파일명 반환
        return uploadedFileName;

    }

    // 1.날짜별 경로 추출
    private static String calcPath(String uploadPath) {

        Calendar calendar = Calendar.getInstance();

        // 년
        String yearPath = File.separator + calendar.get(Calendar.YEAR);

        // 년 + 월
        String monthPath = yearPath + File.separator + new DecimalFormat("00").format(calendar.get(Calendar.MONTH) + 1);
        
        // 년 + 월 + 일
        String datePath = monthPath + File.separator + new DecimalFormat("00").format(calendar.get(Calendar.DATE));

        // 파일 저장 기본 경로 + 날짜 경로 생성
        makeDir(uploadPath, yearPath, monthPath, datePath);

        // 날짜 경로 반환
        return datePath;

    }

    // 2.파일 저장 기본 경로 + 날짜 경로 생성
    private static void makeDir(String uploadPath, String paths) { // uploadPath : 기본, paths : 날짜

        // 기본 경로 + 날짜 경로가 이미 존재 : 메서드 종료
        // paths.length -1 : 배열은 0부터 시작이기 때문에 -1을 해주는것
        if(new File(uploadPath + paths[paths.length -1]).exists()) {         
            return;
        } 

        // 날짜 경로가 존재 X : 경로 생성을 위한 반복문 수행
        for(String path : paths) {

            // 기본 경로 + 날짜 경로에 해당하는 파일 객체 생성
            File dirPath = new File(uploadPath + path);

            // 파일 객체에 해당하는 경로가 존재 X
            if(!dirPath.exists()) {

                // 경로 생성
                dirPath.mkdir();
            }
        }
    }

    // 3.썸네일 생성 : 이미지 파일의 경우
    private static String makeThumbnail(String uploadPath, 
                                        String path, 
                                        String fileName) throws Exception {

        // BufferedImage : 실제 이미지 X, 메모리상의 이미지를 의미하는 객체
        // 원본 이미지 파일을 메모리에 로딩
        BufferedImage sourceImg = 
                ImageIO.read(new File(uploadPath + path, fileName));
        //      기본 경로 + 년/월/일 에서 filName에 해당하는 이미지 파일을 읽어와서 데이터로 저장  
        
        // 정해진 크기에 맞게 원본 이미지를 축소
        BufferedImage destImg = 
                Scalr.resize(sourceImg,                 // 원본 이미지
                Scalr.Method.AUTOMATIC,                 // 이미지 가로/세로 비율 유지
                Scalr.Mode.FIT_TO_HEIGHT, 100);         // 이미지 높이를 100px로 맞춤.
        
        // 썸네일 이미지 파일명
        String thumbnailName = uploadPath + path + File.separator + "S_" + fileName;
        
        // 썸네일 이미지 파일 객체 생성
        File newFile = newFile(thumbnailName);

        // 파일 확장자 추출
        // 형식 = 파일 이름에서 .을 찾아 그 뒤의 인덱스부터 읽어들임
        // ex : test.exe 이면 . 뒤의 exe를 추출, 즉 파일 확장자 추출 과정.
        String formatName = fileName.subString(fileName.lastIndexOf(".") + 1);

        // 썸네일 파일 저장
        ImageIO.write(destImg, formatName.toUpperCase(), newFile);

            // 섬네일이 존재하는 윈도우 전체 경로에서 윈도우의 파일 구분자인 \를 브라우저에서 
            // 제대로 인식하지 못하기 때문에 U에서 사용하는 /로 치환한 다음 반환.                                
            return thumbnailName.substring(uploadPath.length()).replace(File.separatorChar, '/');
    }

    // 4. 아이콘 생성 : 이미지 파일이 아닐 경우
    private static String makeIcon(String uploadPath, String savePath, String fileName) throws Exception {

        // 아이콘 파일명 = 기본 저장경로 + 날짜경로 + 구분자 + 파일명
        String iconName = uploadPath + savedPath + File.separator +fileName;

        return iconName.substring(uploadPath.length()).replace(File.separatorChar, '/');
    }

    // 파일 삭제처리 메서드
    public static void removeFile(String uploadPath, String fileName) {

        // 파일 확장자 추출
        String formatName = fileName.substring(fileName.lastIndexOf(".") +1);

        // 파일 확장자를 통해 이미지 파일인지 판별
        MediaType mediaType = MediaUtils.getMediaType(foramtName);

        // 이미지 파일일 경우, 원본파일 삭제
        if(mediaType != null) {

            // 원본 이미지의 경로 + 파일명 추출
            // 날짜 경로 추출
            String front = fileName.subString(0, 12);

            // UUID + 파일명 추출
            String end = fileName.substring(14);

            // 원본 이미지 파일 삭제(구분자 변환)
            new File(uploadPath + (front + end).replace('/', File.separatorChar)).delete();
        }

        // 파일 삭제(일반 파일 or 썸네일 이미지 파일 삭제)
        new File(uploadPath + fileName.replace('/', File.separatorChar)).delete();
    }

}
