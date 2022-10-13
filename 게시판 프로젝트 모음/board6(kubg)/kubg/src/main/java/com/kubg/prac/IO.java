package com.kubg.prac;

import java.io.File;
import java.io.FileNotFoundException;

public class IOp {
    
    public static void main(String[] args) {

        try {

            // file open...
            FileOutputStream fos = new FileOutputStream("C:/temp/java/test/tes.txt");

            // 기존 팡ㄹ에 내용을 추가 할려면 두번째 인자로 true를 적어 준다. true를 추가해도 없으면 만든다.
            FileOutputStream fos1 = new FileoutputStream("c:/temp/java/test/tes.txt", true)

            // 파일에 저장할 내용
            String strText = " 파일에 저장될 문자열 입니다.\n Hellow world !!";

            // 파일은 byte로 저장한다.
            fos.write(strText.getBytes());

            fos.close();
        }
            // 파일의 내용을 읽어올때는 byte단위로 -> 읽어온 byte를 출력을 위해 String으로 변환

            // file open...
            FileInputStream fis = new FileInputStream("c:/temp/java/test/test.txt");

            // 파일의 내용을 byte 단위로 읽어온다. 읽어서 저장할 버퍼 byte 배열 설정
            byte[] byteBuff = new byte[99999];

            // 파일을 읽고 읽은 크기를 nRLen에 저장한다.
            int nRLen = fis.read(byteBuff);

            // 출력을 위해서 byte배열을 문자열로 변환
            String strBuff = new String(byteBuff, 0, nRLen);

            System.out.printf("읽은 바이트수[%d] : \n읽은 내용 :  \n%s \n", nRLen, strBuff);

            fis.close();

        } catch (Exception e) {
            
        }
    
}

public class SvrFileRead {

    String strBuff = "";
    String strCurrentMsg = null;
    String strFilename;

    // 사원파일을 읽어들일 바이트 버퍼
    byte[] byteBuff = new byte[9999]; 
    // 파일이 저장될 디렉토리 이름
    int nRLen; 

    public void sfrFunc(String strDir, String strFileName) throws Exception {

        // 디렉토리가 있는지 확인하고 없으면 만듬
        File dir = new File(strDir);

        if(dir.exists() == false) {
            boolean t = dir.mkdirs();

            if(t == true) {
                strCurrentMsg = "디렉토리가 정상적으로 만들어 졌습니다.";
                // System.out.println(strCurrentMsg);

                // 파일이 있는지 확인하고 없으면 만듬
                File file = new File(strFileName);

                boolean trueNewFile = file.createNewFile();
                
                if(trueNewFile) {
                    strCurrentMsg = "파일이 없어 파일을 만들었습니다.";
                    // System.out.println(strCurrentMsg);

                } else if(file.exists() == true) { // 파일이 존재하면 실행하는 부분

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
