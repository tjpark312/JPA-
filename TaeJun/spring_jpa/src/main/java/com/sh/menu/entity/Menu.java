package com.sh.menu.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Menu")
@Table(name = "tbl_menu")
@Data
// set 을 외부에서 하지 않기로함 menu의 가격이 바뀌는건 메뉴 책임이어야함
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_code")
    private Long menuCode;
    @Column(name = "menu_name")
    private String menuName;
    @Column(name = "menu_price")
    private int menuPrice;

    @Column(name = "category_code")
    private Long categoryCode;
    @Column(name = "orderable_status")
    @Enumerated(EnumType.STRING)
    private OrderableStatus orderableStatus;

    // 책임을 누가 가지고 있느냐를 명확히 할 수록 추후에 발생할 요구사항에 대처하기 쉬움, set 을 외부에 사용하면 오류가 어디서 발생하는지 찾기 더어려움
    public void changeMenuPrice(int newMenuPrice){
        this.menuPrice = newMenuPrice;
    }

}
