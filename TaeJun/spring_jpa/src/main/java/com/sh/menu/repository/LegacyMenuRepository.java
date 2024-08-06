package com.sh.menu.repository;

import com.sh.menu.entity.Menu;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class LegacyMenuRepository {
    // JPA에서 EntityManager를 주입하기 위해 사용하는 어노테이션
    // 데이터베이스와의 CRUD 작업을 수행하는데 필요함
    /**
     * 엔티티 관리 : 엔티티의 생명주기를 관리, 영속성  컨텍스트를 통해 엔티티를 데이터베이스와 동기화
     * 트랜잭션 지원 : 트랜잭션 관리하며, 일관성을 유지하는데 필수적임
     *
     * ### 영속성 컨텍스트와 1차 캐시?
     *  - 영속성 컨텍스트?
     *  - JPA에서 관리하는 엔티티 객체의 집합
     *  - 엔티티 매니저가 엔티티를 저장하고 관리하는 공간
     *  - 애플리케이션의 데이터 변경 사항을 관리하고, 트랜잭션이 끝날 때 데이터 베이스오와 동기화
     *  - 영속성 컨텍스트에 저장된 엔티티들은 JPA에 의해 자동으로 관리
     *
     *  - 1차 캐시?
     *   - 영속성 컨텍스트에 포함된, 엔티티를 관리하는 메커니즘
     *   - 각 엔티티 매니저는 자체 1차 캐시를 가지고 있으며, 이는 트랜잭션 내에서 유효하다.
     *   - 데이터베이스에서 조회된 엔티티는 1차 캐시에 저장됨
     *   - 이후 동일한 엔티티를 다시 조회할 때, 데이터베이스에 직접 쿼리를 보내지 않고, 1차 캐시에서 조회함
     *
     */
    @PersistenceContext
    private EntityManager em;

    public Menu findByMenuCode(Long menuCode) {
        // 우리는 엔티티 매니저에게 부탁, 메뉴 엔티티 주고 pk는 이거다
        return em.find(Menu.class, menuCode);
    }

    public void updateMenuPrice(Long menuCode, int newMenuPrice) {
        // 수정/삭제 시 해당 엔티티를 먼저 조회해야 함

        /**
         * 이유?
         *  JPA의 동작 방식과 데이터 일관성 및 안정성을 보장하기 위한 여러가지 이유가 있음
         *  - 1. 조회 및 영속화
         *      - 엔티티가 조회되면 영속성 컨텍스트에 저장된다.
         *      - 이를 통해 엔티티가 관리 상태가 되며, JPA는 해당 엔티티에 대한 변경 사항을 추적할 수 있다.
         *      2. 변경 감지
         *      - 트랜잭션 내에서 엔티티 필드를 수정하면, JPA는 트랜잭션이 커밋될 때 변경된 부분을 감지
         *
         */
        Menu menu = em.find(Menu.class, menuCode);
        // setMenuPrice를 외부에서 해줄 것이 아니라 menuPrice는 menu Class가 관리해야 하므로 menu에서 관리
        menu.changeMenuPrice(newMenuPrice);
    }
}
