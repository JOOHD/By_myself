import com.company01lspringEx01.logic.Members;

public interface UserService {

    Members getUserOne(String common, String col);

    int userJoin(Members members);
}
