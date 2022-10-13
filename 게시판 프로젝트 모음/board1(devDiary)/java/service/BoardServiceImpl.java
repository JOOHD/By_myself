package board(devDiary).java.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company01.springEx01.dao.BoardDAO;
import com.company01.springEx01.logic.Board;

@Service
public class BoardServiceImpl implements BoardService {
    
    @Autowired
    BoardDAO boardDAO;

    @Override
    public List<Board> getBoardList() {

        return boardDAO.getBoardList();
    }

    @Override
    public int boardwrite(Board board) {

        return boardDAO.boardwrite(board);
    }

    @Override
    public Board getBoardDetail(int id) {

        return boardDAO.getBoardDetail(id);
    }

    @Override
    public void viewsUpdate(int id) {
    
        boardDAO.viewUpdate(id);
    }
}
