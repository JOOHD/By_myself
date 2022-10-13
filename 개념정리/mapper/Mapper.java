/* Mapper Interface
   MyBatis3.0 부터 xml 파일(mapper.xml)또는 어노테이션에 기재된 sql 쿼리 명령을 수행할 수 있는 
   Mapper 인터페이스를 사용할 수 있다.
   Mapper 인터페이스 메서드 호출시 구현체가 SqlSession 메서드 호출을 통해 sql 실행을 요청하면 SqlSession은 SQL을 실행
 */
    @Mapper
    public interface MemberMapper {

    }
 /* 
  * servsice의 요청을 처리해줄 Member Mapper 인터페이스를 만들어준다.
    @Mapper 어노테이션을 통해 스프링부트가 해당 클래스를 mapper로 인식할 수 있으며 해당
    interface를 Mybatis mapper 빈으로 등록해줄 수 있따.
  */
    
    @Mapper
    public interface MemberMapper {

        @Insert("INSERT INTO MEMBER_INFO(email, password, name) VALUES(#{member.email}, #{member.password}, #{member.name})") 
        @Optional(useGeneratedKeys = true, keyProperty = "id")
        int create(@Param("member") Member member);
    }
/*  이제 MemberMapper에 Service 에서 호출할 create 메서드를 작성해 보자
    매개 변수를 받아올 때는 @Param("")을 통해서 값을 명시하고  #{}을 통해 동적 바인딩 처리를 할 수 있다.

    insert 후 Auto Inresement 설정으로 증가된 PK값을 가져오기 위해 @Options 어노테이션을 사용할 수 있다.
    member 인스턴스 id property에 PK값을 셋팅해준다.
*/
    @Mapper
    public interface MemberMapper {

        @Select("SELECT * FROM MEMBER_INFO WHERE id = #{id}")
        @Result({
            @Result(property="자바 객체 property 명", column="db 테이블 컬럼 명"),
            @Result(property="자바 객체 property 명", column="db 테이블 컬럼 명")
        })  
        Optional<Member> getById(@Param("id") String id);
        })
    }
/*  맵핑할 DB 컬럼 명과 자바 필드가 다른 경우
    @Result 어노테이션을 지정하고 그 안에 복수개의 컬럼 맵핑관계를 지정할 수 있다.
    (자바 객체 property 명, db 테이블 칼럼 명)으로 맵핑한다.
 */
    mybatis.configuration.map.underscore-to<camel-case=true

/*  보통 DB 컬렴명은 스네이크 표기법을, 맴버 변수는 카멜케이스 표기법을 사용한다.
    이 경우 맴핑관계를 지정하지 않고 build.gradle 파일의 간단한 설정을 통해 해결할 수 있다.
*/    
    @Mapper
    public interface MemberMapper {

        @Select("SELECT * FROM MEMBER_INFO WHERE id=#{id}")
        @Result(id = "MemberMap", value={
            @Result(property="자바 객체 property 명", column="db 테이블 컬럼 명"),
            @Result(property="자바 객체 property 명", column="db 테이블 컬럼 명")
        })  
        Optional<Member> getById(@Param("id") String id);
        
        @Select("SELECT * FROM MEMBER_INFO WHERE email=#{email}")
        @ResultMap("MemberMap")
        Optional<Member> getByEmail(@Param("email") String email);
    }
/*  반복적으로 동일한 Mapping 관계를 설정해야 하는 경우에는 @Results에 id값을 붕하여 재사용할 수 있따. */
    public class Company {

        private int id;
        private String name;
        private String address;
        private List<Employee> employeeList;
    }

/*  예시처럼 Company 클래스가 employList를 포함하고 있는 경우 */
    public List<Company> getAll() {
        List<Company> companyList = companyMapper.getAll();
        for(Company company : companyList) {
                company.setEmployeeList(employeeMapper.getByCompanyId(company.getId()));
        }
    }

/*  클래스가 리스트에 포함하고 있는 경우
 
 *  public class Customer {

    private int id;
    private String name;
    
    // getters/setters, custom hashcode/equals
    }

  * List<Customer> customers = new ArrayList<>();
                            //(int id, String name)
    customers.add(new Customer(1, "Jack"));
    customers.add(new Customer(2, "James"));
    customers.add(new Customer(3, "Kelly"));

    이제 요소에 대한 필드 기반 검색을 수행하려면 어떻게 해야 될까요?
    예를 들어, 복권을 발표 하고 트겆ㅇ 이름의 고객을 담청자로 선언 해야한다고 가정 해 보자
    이러한 필드 기반 검색의 경우 반복으로 전환 할 수 있다.
    List을 반복하는 전통적인 방법은 java의 반복 구조 중 하나를 사용하는 것이다.
    반복 할 때마다 List의 현재 항목을 찾고있는 요소와 비교하여 일치하는지 확인한다.
  * public Customer findUsingEnhancedForLoop(String name, List<Customer> customers) {
        for (Customer customer : customers) {
            if (customer.getName().equals(name)) {
                return customer;
            }
        }
        return null;
    }
    여기서 이름은 주어진 고객 List에서 검색 하는 이름을 나타낸다. 이 메서드는 일치하는 이름을 가진
    List의 첫 번째 Customer 객체를 반환 하거나 Customer가 없는 경우 null을 반환한다.
   
  * 자바 8 스트림 API
    Java 8부터는 StreamAPI  를 사용하여List 에서 요소를 찾을 수도  있습니다.
        -List에서 stream() 호출
        -적절한 술어로 filter() 메소드를 호출
        -해당 요소가 존재하는 경우 Oprtional에 래핑 된 필터 조건 자 와 일치하는 첫 번째 요소를 반환하는 findAny()구성 호출

    Customer james = customers.stream()
        .filter(customer -> "James".equals(customer.getName()))
        .findAny()
        .orElse(null)
 */
  
/* 서비스 레이어에서 처리할 수도 있지만 */
@Select("SELECT * FROM company")
@Results(id="CompanyMap", value={
    @Result(property="name", column="company_name"),
    @Result(property="address", column="company_address"),
    @Result(property="employeeList", column="id", many=@Many(select="com.example.demo.EmployeeMapper.getByCompanyId"))
})
List<Company> gettAll();
/* @Many 어노테이션 설정을 통해 Company의 id 컬럼을 이용해 서브쿼리를 수행할 수 있다.*/

/* Groovy Class
 * 그러나 어노테이션의 속성값으로 SQL을 직접 넣어주는 경우 쿼리가 복잡할수록 가독성이 좋지않을 뿐만 아니라 관리가 힘들어집니다.
 */
@Mapper
public interface MemberMapper {

    @InsertProvider(type = MemberSQL.class, method = "create")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int create(@Param("member") Member member);

    @SelectProvider(type = MemberSQL.class, method = "getById")
    Optional<Member> getById(@Param("id") String id);

    @SelectProvider(type = MemberSQL.class, method = "getByEmail")
    Optional<Member> getByEmail(@Param("email") String email);

    @SelectProvider(type = MemberSQL.class, method = "modifyById")
    void modifyById(@Param("id") String id, @Param("member") Member member);
}

/* 따라서 sql String을 생성하는 클래스를 분리하여 클래스와 메서드를 각각 type, method에 정의하였다. */
class MemberSQL {

    public String create(@Param("member") Member member) {
        return new SQL() {{
            INSERT_INTO("MEMBER_INFO")
            VALUSE("email, password, name", "#{member.email}, #{member.password}, #{member.name}")       
            }}
        }

        public String getById(@Param("id") String id) {
            return new SQL() {{
                SELECT("*")
                FROM("MEMBER_INFO")
                WHERE("id=#{id}")
            }}
        }
    
        public String getByEmail(@Param("email") String email) {
            return new SQL() {{
                SELECT("*")
                FROM("MEMBER_INFO")
                WHERE("email=#{email}")
            }}
        }
    
        public String modifyById(@Param("id") String id, @Param("member") Member member) {
            return new SQL() {{
                UPDATE("MEMBER_INFO")
                SET("password=#{member.password}")
                SET("name=#{member.name}")
                WHERE("id=#{id}")
            }}
        }
}

/* XML
 * 복잡한 쿼리는 XML을 사용하는것이 좋은 방법이 될 수 있다.
 

    <?xml version="1.0" encoding="UTF-8"?>
    <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
    <mapper namespace="com.flab.demo.mapper.ProductMapper">
    </mapper>

   SQL Mapper 파일은 <mapper>를 루트 엘리먼트로 사용한다.
   namespace는 Mapper Interface 경로 와 맵핑한다.

   그리고 <insert> <update> <delete> <select> 엘리먼트를 이용하여 필요한 SQL 구문들을 등록합니다.
   이때 id 속성은 필수 속성으로 네임스페이스 내부에서 유일한 아이디를 등록해야 합니다.

    XML의 id 값과 Interface 내의 메서드명이 일치하면 자동으로 xml과 메서드를 맵핑되어 id로 
    등록된 SQL을 실행할 수 있습니다.
 
    <?xml version="1.0" encoding="UTF-8"?>
    <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
    <mapper namespace="com.flab.demo.mapper.ProductMapper">

        <resultMap type="com.flab.demo.domain.Product" id="product">
            <result property="id" column="id"/>
            <result property="productName" column="product_name"/>
            <result property="fixedPrice" column="fixed_price"/>
            <result property="sellerId" column="seller_id"/>
            <result property="salesYn" column="sales_yn"/>
            <result property="createDate" column="create_date"/>
        </resultMap>

        <select id="getById" parameterType="Long" resultMap="product">
            SELECT *
                FROM PRODUCT
                WHERE id = #{id}
        </select>

    <?xml version="1.0" encoding="UTF-8"?>
    <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
    <mapper namespace="com.flab.demo.mapper.ProductMapper">

        <select id="getById" parameterType="Long" resultType="com.flab.demo.domain.Product">
            SELECT *
                FROM PRODUCT
                WHERE id = #{id}
        </select>
    </mapper>    

    SELECT SQL 구문이 실행되면 ResultSet이 리턴되며, ReSULTSet에 저장된 검색 결과를 어던 자바 객체에 맵핑해야 할지 지정해야한다.
    이때 resultType, resultMap 속성을 사용할 수 있다.

    <insert id="create" parameterType="com.flab.demo.domain.Product">
        INSERT INTO PRODUCT(product_name, fixed_price, seller_id)
        VALUES(#{productName}, #{fixedPrice}, #{sellerId})
    </insert>    

    Mapper 파일에 등록된 SQL 실행 시 SQL 실행에 필요한 데이터를 외부로부터 받아야 할 상황이 있을 수 있습니다. 이때 parameterType 속성을 사용할 수 있습니다. 내부적으로 parameter 값을 가져올 수 있는 getter 메서드를 호출합니다.
    mybatis.mapper-locations=mapper/*.xml
    mybatis.type-aliases-package=패키지 경로
    마이바티스 세팅을 위해 application.properties 파일을 수정합니다.

    mybatis.mapper-locations : xml 파일이 위치한 경로/*.xml
    mybatis mapper.xml 파일이 있는 폴더 경로를 설정해줍니다.
    
    보통 classpath 를 통해 경로 설정을 해주는데, spring boot로 프로젝트 생성시 classpath는 resources 폴더 입니다.

    mybatis.mapper-locations : 매퍼용 클래스가 위치한 경로
    Mapper에서 resultType을 단순히 클래스 명으로만 적기 위해 필요합니다.
    미설정시 전체 패키지 경로와 클래스명을 작성해야 합니다.
 */       