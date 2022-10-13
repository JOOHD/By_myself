
/** Architecture 란?
 *  시스템 목적을 달성하기위해 시스템의 상호작용등의 시스템디자인에 대한 제약 및 설계이다.
    시스템의 구조, 행위, 더 많은 뷰를 정의하는 개념적 모형으로, 시스템 목적을 달성하기 위해 시스템의 각
    컴포넌트가 무엇이며 어떻게 상호작용하는지, 정보가 어떻게 교환되는지 설명한다.
        -즉, 어떤 시스템을 만들기 위한 전체적인 흐름의 디자인이라고 생각한다.
        -개념적으로 시스템의 구성물들이 각각 어떤 역할 수행할지 정의함으로써 세분화 및 구조화를 목적을 이룬다.
    
    이것을 공부한 이유
    이번에 flask를 시작하면서 가장 처음에 당황스러웠던 점은 무언가 없어도 너무 없다는 것이다. 
    공식문서에서는 라우팅,비즈니스 로직, db 로직, 앱 환경설정 등... 하나의 파일에서 뭔가 다 될거같은 느낌이였다. 
    하지만, 프로젝트를 다른 사람들과 진행해야되는데, 하나의 파일에 그렇게 모든 로직을 다 때려박으면 나중에 github이나 rebase할 때 
    엄청난 conflict이 일어날거 같아서 여러가지를 찾아보던 중 이 아키텍쳐에 대해 공부하게 되었다.    

    계층화 아키텍쳐란?
        -아키텍처의 컴포넌트들은 각각 어플리케이션의 특정한 역할을 수행하도록 가로로 나누어져 계층을 이룬다.
        -가장 널리 알려진 아키텍처로 전통적인 IT workflow 와 조직 구성과 잘 맞아 떨어져서 많은 비즈니스에 채택된다.
        -주로 3가지 계층으로 이루어져 있다.
            
            1.Presentaion Layer -> (View) 유저 + 브라우저와 상호작용하는 로직이 있는 
               -표현과 이벤트 처리, 데이터 포멧 책임
                사용자와의 최종 접점에 위치하여 사용자로부터 데이터를 입력 받거나, 요청된 데이터를 출력해 보이는 계층이다.
                외로부터의 어플리케이션에 대한 요구를 받아들이는 동시에 처리 결과를 사용자에게 보여주는 부분이며, 컨트롤러의 구성요소와 상호작용

             .Presentaion Layer -> (Controller) 
               -구성 요소간의 처리 흐름을 제어, 데이터 조작 의뢰, 데이터 변환 및 연산, Exception 과 error 처리
                사용자로부터 요청을 받고 응답을 처리하는 계층이다. 하위 계층에서 발생하는 Exception이나 Error에 대한 처리를 맡으며,
                최종 프레젠테션 계층에 표현 해야될 도메인 모델을 엮는 기능을 수행한다. 사용자로부터 전달 받은 데이터의 유효성 검증을 처리하고,
                전체 시스템의 설정상태를 유지한다. 요청에 해당하는 비지니스 로직을 결정하는 역할을 수행한다.
                
                사용자의 요청을 검증, 로직에 요청을 전달, 로직에서 반환된 응답을 적절한 뷰로 연결

            3.Business Layer -> (Service) 요청에 맞는 비즈니스 로직을 수행하는  
               -Controller layer와 Persistence layer를 연결하는 역할, Transaction 처리
                애플리케이션의 비지니스 로직 처리와 비지니스와 관련된 도메인 모델의 적합성을 검증하고, 트랜잭션을 처리한다.
                Controller 계층과 Persistence 계층을 연결하는 역할로서 두 계층의 직접 통신하지 않게 하여 애플리케이션의 유연성을 증가

            4.Persistence Layer -> (DAO)
               -데이터를 저장하고 관리하는
                영구 데이터를 빼내어 객체화 시키며, 영구 저장소에 데이터를 저장, 수정, 삭제하는 계층이다. 
                데이터베이스나 파일에 접근하여 데이터를 CRUD하는 계층이다.
            
            5.Domain Model Layer -> (VO, DTO)
               -관계형 데이터베이스의 엔티티와 비슷한 개념을 가지는 것으로 데이터 객체를 말한다.    

        각각의 계층은 다른 계층과 상호작용하지만, 다른 계층에서 발생하는 로직에는 신경쓰지 않아도 된다.
        예를 들어, 데이터를 다루는 persistence Layer는 그 데이터가 보여지는 presentation layer를 신경쓰지 않아도 되며,
        오로지 자신의 일에만 집중하면 된다
 */

/**  DAO 와 Service 관계
MVC는 모델(실제 동작이 수행되는 부분), 뷰(화면에 그려지는 부분), 컨트롤러(모델과 뷰를 제어하는 부분)으로
나뉘어서 이해하고 있다.
Service와 DAO는 MVC 처럼 많이 다루지 않는 것 같은데 많은 스프링 프로그램 로직에서 이와같은 구조를 사용하고 있다.
일부 책에서는  Controller에서 Service Interface를 통해 Service Implement를 사용하고, 또 Service Implement에서 
DAO Interface를 통해 DAO Implement를 사용한다고 명시되있다.

Service와 DAO로 나뉘는 것은 "Layered Architecture" 라는 아키텍쳐로 
Presentaion Layer, Business Layer, Persistence Layer, Database Layer로 나뉘어져 있으며, 대형 프로젝트에서
역할에 따라 분류함으로써 각 로직에 대해 복잡성을 줄일 수 있고 테스트 하기 용이하다.

Spring의 경우에도 Presentation Layer는 View와 Controller, Business Layer는 Service, Persistence Layer는 
말 그대로 데이터베이스 부분으로 해당 아키텍쳐에 맞게 나뉘어져있다.

그렇다면 Controller, Service, DAO에는 어떤 내용의 로직이 들어가야 할까?
EX)
    제 지갑에는 돈이 만원이 있습니다.
    이 돈을 은행에 맡길 생각입니다.

    그렇다면 먼저 은행에 가야겠죠?
    저는 국민은행으로 가서 제 돈 5000원을 입금하려 한다.
    
    이 때 통장을 두고 와서 무통장 입금을 해야하는데, 입출금전표(무통장입금 시 사용하는 용지)를 적어서 은행원에게 무통장입금 요청을 한다.
    은행원은 입출금전표와 제가 건넨 금액을 확인하고 입출금 관리 시스템을 통해 입금 처리 한다.

    입출금 관리 시스템에서는 입금 내역은 데이터베이스에 추가하고 문자메시지를 통해 저에게 입금완료 문자를 전송한다.
    입출금 관리 시스템에서 입금이 완료되었다면 은행원은 입금이 완료되었다는 메시지를 확인하고, 저에게 입금이 완료되었다고 안내한다.
    
    저는 이렇게 돈을 은행에 맡길 수 있었다.

로직에서 말하는 비즈니스 로직에 해당하는 부분은 "국민은행원" 과 "국민은행입출금시스템"이다.
결국 해당 "업무를 담당하는 부분에 대한 로직"을 비즈니스 로직이라고 하며 이 부분을 우리는 "Service" 라고 부른다.

"컨트롤러는 서비스에게 특정 업무를 요청하며 필요한 자료를 DAO에게 요청하거나, 업무를 통해 나온 자료를 DAO를 통해 저장한다."

여기서 추가적으로 Service와 DAO에서 Interface를 사용하는 이유는 Controller(본인)는 Service(담당자)를 통해 입금 처리를 하지만 실제
담당자가 어떤방식으로 처리하는지는 저로써는 알 필요가 없다.
만약 국민은행원이 아니라 신한은행원이라고 하더라도 동일한 업무를 한다면(은행원인터페이스를 구현하였다면) 저는 신한은행에서도 같은 업무를 볼 수 있죠?
이러한 이유로 Controller와 실제 국민은행원 사이를 은행원인터페이스로 분리하고 추후 신한은행원 클래스가 추가된다면 국민은행원을 신한은행으로 변경하는
것만으로 프로그램 수정이 끝나게 된다. (프로그램 수정으로 인한 영향범위가 적어짐)

마찬가지로 DAO의 경우에는 "은행데이터베이스.insert(은행계좌);" 부분을 텍스트 파일로 저장할지 오라클 DB를 사용할 것인지 용도에 따라 DAO를 교체하여 사용하면 된다.

*/ 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestDao {
    public void add(TestDto dto) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "root");

        PreparedStatement preparedStatement = connection.prepareStatement("insert into users(id,name,password) value(?,?,?)");

        preparedStatement.setString(1, dto.getName());
        preparedStatement.setInt(2, dto.getValue());
        preparedStatement.setString(3, dto.getData());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        
        connection.close();
    }
}