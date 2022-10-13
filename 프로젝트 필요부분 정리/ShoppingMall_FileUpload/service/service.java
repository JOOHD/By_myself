
// 게시글 생성
@Transactional
@Override
public void create(ArticleVO articleVO) Throws Exception {
    
    // string 타입의 files 변수 생성, articleVO 클래스에서 .getFiles(); files 불러오기
    String[] files = articleVO.getFiles();

    if(files == null) { // files가 없다면

        // DAO.articleVO(생성)
        articleDAO.create(articleVO);

        // 생성 후, 원래로 돌아오기
        return;
    }

    // articleVO.파일 조회수(파일.길이만큼) 지정
    articleVO.setFileCnt(files.length);

    // DAO.articleVO(생성)
    articleDAO.create(articleVO);

    // 생성된 VO log로 확인
    logger.info("Create - " + articleVO.toString());

    // integer 타입의 no 변수생성 = vo.getArticle_no 불러오기
    Integer article_no = articleVO.getArticle_no();

    // files(list) 정보를 string 타입의 fileName에 담기
    for (String fileName : files)  {

        // DAO.추가(fileName, article_no)
        articleFileDAO.addAttach(fileName, article_no);
    }

// 게시글 수정    
@Transactional
@Override
public void update(ArticleVO articleVO) throws Exception{

    int article_no = articleVO.getArticle_no();

    articleFileDAO.deleteAllAttach(article_no);

    String[] files = articleVO.getFiles();

    if(files == null) {

        articleVO.setFileCnt(0);

        articleDAO.update(articleVO);

        return;
    }

    articleVO.setFileCnt(files.length);
    
    articleDAO.update(articleVO);

    for(String fileName : files) {

        articleFileDAO.replaceAttach(fileName, article_no);
    }
}

// 게시글 삭제
@Transactional
@Override
public void delete(Integer article_no) throws Exception {

    articleFileDAO.deleteAllAttach(article_no);

    articleDAO.delete(article_no);
    }
}