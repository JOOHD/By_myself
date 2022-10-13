
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardServiceTest {
    
    Logger logger = LoggerFactory.getLogger(BoardServiceTest.class)

    @Autowired
    BoardService boardService;

    int boardSeq = 0;

    /* 게시글 목록 조회 시 NULL 아니면 테스트 통과 */
    @Test
    public void testGetBoardDetail() {

        try {

            List<Board> boardList = boardService.getBoardList();
            assertNotNull(boardList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* 게시글 상세 조회 시 NULL이 아니면 테스트 통과 */
    @Test
    public void testGetBoardDetail() {

        try {

            if (boardSeq != 0) {

                Board boardDetail = boardService.getBoardDetail(boardSeq);
                assertNotNull(boardDetail);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* 게시글 등록 후 1이 응답되면 테스트 통과 */
    @Rollback(true)
    @Test
    public void testInsertBoard() {

        try {

            Board board = new Board();
            board.setBoard_writer("게시글 작성자 등록");
            board.setBoard_subject("게시글 재목 등록");
            board.setBoard_contetn("게시글 내용 등록");

            int result = boardService.insertBoard(board);

            assertTrue(result == 1);

        } catch (Exception e) {
            e.printStackTrace();
        }  
    }

    /* 게시글 수정 후 1이 응답되면 태스트 통과 */
    @Rollback(true)
    @Test
    public void testUpdateBoard() {

        try {

            if (boardSeq !=0) {

                Board board = new Board();
                board.setBoard_seq(boardSeq);
                board.setBoard_subject("게시글 제목 수정");
                board.setBoard_content("게시글 내용 수정");

                int result = boardService.updateBoard(board);
                assertTrue(result == 1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* 게시글 삭제 후 1이 응답되면 테스트 통과 */
    @Rollback(true)
    @Test
    public void testDeleteBoard() {

        try {

            if (boardSeq !=0) {

                int result = boardService.deleteBoard(boardSeq);
                assertTrue(result == 1);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
