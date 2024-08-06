package com.sh.menu.repository;

import com.sh.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
// 두개의 제네릭을 전달해야 하는데 하나는 내가 제어하고자 하는 엔티티고 하나는 그 엔티티의 pk 타입임

/**
 * Spring Data JPA는 JPA를 쉽게 사용할 수 있도록 지원하는 스프링 모듈
 * JpaRepository는 Spring Data JPA가 제공하는 인터페이스로, 기본적인 CRUD 기능을 비롯하여 페이징 및 정렬 기능을 간단하게 사용할 수 있게 해준다.
 *
 * JpaRepository의 역할
 *
 * 1. CRUD 제공
 * - save(), findById(), findAll(), deleteById() 와 같은 기본적인 CRUD 메서드 제공
 *
 * 2. 페이징 및 정렬 지원
 * - 데이터를 페이징 처리하거나 특정 기준으로 정렬할 수 있는 메서드를 제공함,
 * 예를 들어 findAll(Pageable pageable) 은 페이징된 결과를 반환함
 *
 * 3. JPQL 쿼리 메서드 지원
 * - 메ㅓ드 이름을 기반으로 한 쿼리 생성 기능을 지원하여 복잡한 JPQL 없이도 데이터베이스 쿼리를 수행할 수 있다.
 */
public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByMenuNameLike(String s);
    // 아까껀 Legacy 였고, 엔티티 하나당 레파지토리가 하나 필요함
}
