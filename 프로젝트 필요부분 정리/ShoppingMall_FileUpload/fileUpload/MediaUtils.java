/*스프링 multipartResolver 선언
-화면단에서 multipart/form-date 방식으로 서버에 전송되는 데이터를 스프링 MVC의 multipartResolver로 처리가능
    // servlet-context.xml 
          파일 업로드로 들어오는 데이터를 처리하는 객체
            <beans:bean id="multipartResolver" class="org.springframework.web,commons.CommonsMultipartResolve">
                <beans:property name="maxUploadSize" value="10485760" />
            </beans:bean>
         

업로드할 파일의 위치를 지정한다.
    // servlet-context.xml 
        
          <beans:bean id="uploadPath" class="java.lang.String">
            <beans:constructor-arg value="D:\\SpringUploadRepo\\upload"></beans:constructor-arg>
          </beans:bean>

          maxUploadSize 프로퍼티를 통해 업로드할 파일의 용량을 제한할 수 있다.

          서버에 파일 저장시 고려사항
          1.파일업로드 방식 결정 -> ajax
          2.파일이름 중복문제 -> 일반적 파일시스템에 파일을 저장한다.
                                 업로드 되는 파일의 이름의 중복을 해결할 방법이 필요 -> UUID로 해경
          3.폴더내의 파일 개수가 너무 많아지게 되면, 속도 저하문제가 발생
             -> 파일 지정경로 날짜 SimpleDateFormat으로 해경
          4.이미지 파일 썸네일(thumbnail) 생성 -> imgscalr-lib가 이미지의 썸네일 생성을 해준다.
*/

package fileUpload;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;

// 업로드한 파일중 이미지 파일만 거르는 클래스
public class MediaUtils {

    private static Map<String, MediaType> mediaMap;

    static {  // 이 유틸리티 파일은 이미지 파일을 걸러주는 유틸리티 파일이다.
            mediaMap = new HashMap<String, MediaType>();
            mediaMap.put("JPG", MediaType.IMAGE_JPEG);
            mediaMap.put("GIF", MediaType.IMAGE_GIF);
            mediaMap.put("PNG", MediaType.IMAGE_PNG);
    }
   
	public static MediaType getMediaType(String type) {
		return mediaMap.get(type.toUpperCase());
	}
    
}
