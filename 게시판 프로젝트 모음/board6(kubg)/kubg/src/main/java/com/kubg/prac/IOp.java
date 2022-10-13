import java.io.BufferedInputStream;
import java.io.File;

public class SvrFileRead {

    String strBuff = "";
    String strCurrentMsg = null;
    String strFileName;

    // 사원 파일을 읽어들일 바이트 버퍼
    byte[] byteBuff = new byte[9999];
    // 파일이 저장될 디렉토리 이름
    int nRLen;

    /*
        String path = "D:\\java\\file\\AAA"; 

        File dir = new File(path);

        if(!dir.exists()) {
            dir.mkdir();
            System.out.println("생성 완료");
        } else {
            System.out.println("같은 이름의 폴더가 이미 존재합니다.");
        }
    */

    public void sfrFunc(String strDir, String strFileName) throws Exception {

        // 디렉토리가 있는지 확인하고 없으면 만듬 (strDir = dir 경로)
        File dir = new File(strDir);

        if(dir.exists() == false) {
            boolean t = dri.mkdirs();

            if(t == true) {
                strCurrentMsg = "디렉토리가 정상정그로 만들어 졌습니다.";
                // System.out.println(strCurrentMsg);

                // 파일이 있는지 확인하고 없으면 만듬
                File file = new File(strFileName);

                boolean trueNewFile = file.createNewFile();
            
                if(trueNewFile) {
                    strCurrentMsg = "파일이 없어 파일을 만들었습니다.";
                    // System.out.println(strCurrentMsg);
                } else if(file.exists() == true) {

                    try {
                        FileInputStream fis = new FileInputStream(file);
                        BufferedInputStream bis = new BufferedInputStream(fis);

                        nRLen = bis.read(byteBuff);

                        if(nRLen < 0) {
                            strCurrentMsg = "---파일이 비었습니다.";
                            // System.out.println(strCurrentMsg); 
                            // System.out.println("here");
                        } else {
                            strBuff = new String(byteBuff, 0, nRLen);
                            strCurrentMsg = "기존 파일이 존재합니다.";
                            // System.out.println(strCurrentMsg);;
                            // System.out.println("읽은 바이트수[%d] : 읽은 내용\n", nRLen, strBuff);
                        }

                        bis.close();
                        fis.close(); // try 끝

                    } catch (FileNotFoundException e) {
                        // System.out.println("sfrFileRead 예외 발생 66: " + e.getLocalizedMessage());
                        e.printStackTrace();                
                    }
                }
            }
        }
    }
}