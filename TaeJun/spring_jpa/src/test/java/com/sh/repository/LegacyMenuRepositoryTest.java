package com.sh.repository;

import com.sh.menu.entity.Menu;
import com.sh.menu.repository.LegacyMenuRepository;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
// Spring 환경에서는 EntiityManagerFactory, EntityManager 객체의 생명주기가 모두 String ApplicationContext 하위에서 관린된다.
// 서비스단에서 처리해야할 트랜잭션, 범위설정 커밋 롤백처리 모두 aop를 이용해 별도로 분리되어 처리된다.
@SpringBootTest
class LegacyMenuRepositoryTest {
    // 이거 의존 주입 해주세요
    @Autowired
    private LegacyMenuRepository legacyMenuRepository;
//    @PersistenceContext
    private EntityManager entityManager;




    @Test
    @DisplayName("메뉴 한건 조회")
    void test1 () {
        // given
        Long menuCode = 11L;
        // when
        Menu menu = legacyMenuRepository.findByMenuCode(menuCode);
        System.out.println(menu.getMenuName());
        // then
        assertThat(menu).isNotNull();
    }

    @Test
    @DisplayName("jpql 로 조인해서 값 가져와보기")
    void test2() {
        // given
        String jpql = """
                select
                m
                from
                Menu m join  category c
                on m.categoryCode = c. categoryCode
               
                
              
                """;
        // when
        TypedQuery<Menu> query = this.entityManager.createQuery(jpql, Menu.class);
        List<Menu> menus = query.getResultList();
        // then
        System.out.println(menus.size());
        menus.forEach(System.out::println);
        assertThat(menus).isNotNull();
    }
    // 트랜잭션은 데이터베이스에서 일련의 작업들을 하나의 논리적인 단위로 묶어 처리하는 것을 의미한다. 트랜잭션은 주로 데이터의 일관성과 무결성을 보장하기 위해서 사용됨. ex) 은행 이체

    // 조회만 하는 경우에는 일반적으로 트랜잭션이라고 간주 (x), 데이터베이스의 상태를 변경하는 작업에 초점을 맞추고 있음

    // @Transactional를 통해서 트랜잭션 범위를 지정할 수 있다. 서비스단에서작성해야 할 어노테이션임
    // EntityManager#getTransaction().begin()
    // EntityManager#getTransaction().commut()/ rollback() 코드 역할을 한다

    // 스프링 테스트에 @Transactional 을 적용하면, 기본적으로 마지막에 rollback 처리한다. 그래서 쿼리가 안날라감 update에 대한

    // @Rollbaack(false)를 통해서 DML 실제 적용할 수 있다. (rollback)되지않음
    @Transactional
    @Test
    @DisplayName("메뉴 가격 업데이트")
    @Rollback(false)
    void test3() {
        // given
        Long menuCode = 8L;
        int newMenuPrice = 9000;
        // when
        Menu menu = legacyMenuRepository.findByMenuCode(menuCode);
        legacyMenuRepository.updateMenuPrice(menuCode, newMenuPrice);


        // then
        assertThat(menu).isNotNull();
        assertThat(menu.getMenuPrice()).isEqualTo(newMenuPrice);

        //

        //Hibernate:
        //    select
        //        m1_0.menu_code,
        //        m1_0.category_code,
        //        m1_0.menu_name,
        //        m1_0.menu_price,
        //        m1_0.orderable_status
        //    from
        //        tbl_menu m1_0
        //    where
        //        m1_0.menu_code=?

        //update
        //        tbl_menu
        //    set
        //        category_code=?,
        //        menu_name=?,
        //        menu_price=?,
        //        orderable_status=?
        //    where
        //        menu_code=?

    }
}