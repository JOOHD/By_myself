package board(devDiary).java.service;

import java.util.List;
import java.com.company01.springEx01.logic.Board;

public interface BoardService {
    
    List<Board> getBoardList();
    
    int boardwrite(Board board);

    Board getBoardDetail(int id);

    void viewsUpdate(int id);
}
