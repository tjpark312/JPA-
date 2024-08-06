package com.sh.menu._02_repository;

import com.sh.menu.entity.Menu;
import com.sh.menu.repository.MenuRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;
import static org.junit.jupiter.api.Assertions.*;

/**
 * <pre>
 * 우리가 작성한 MenuRepository 인터페이스의 상속구조
 * - jpa는 MenuRepository 인터페이스의 구현객체를 동적으로 생성해서 제공한다.
 * - 이 객체는 MenuRepository의 추상메소드를  구현했을 것임
 * - 이 객체는 부모인터페이스인 JpaRepository의 추상 메소드를 구현했을 것이다.
 * - 이 객체는 조상인터페이스인 Pageing,CrudRepository의 추상 메소드를 구현했을 것이다.
 * </pre>
 */
@SpringBootTest
class MenuRepositoryTest {
    @Autowired
    private MenuRepository menuRepository;

    @Test
    @DisplayName("MenuRepository 구현클래스의 빈을 의존주입 받는다")
    void test1() {
        // given
        // when
        // then
        assertThat(menuRepository).isNotNull();
        System.out.println(menuRepository);
//        org.springframework.data.jpa.repository.support.SimpleJpaRepository@7b9d602c
    }

    @Test
    @DisplayName("LIKE 연산 처리")
    void test2() {
        // given
        String menuName ="밥";
        // when
        List<Menu> menus = menuRepository.findByMenuNameLike("%" + menuName + "%");
        menus.forEach(System.out::println);
        // then
        assertThat(menus).allMatch((menu) -> menu.getMenuName().contains(menuName));
    }

}