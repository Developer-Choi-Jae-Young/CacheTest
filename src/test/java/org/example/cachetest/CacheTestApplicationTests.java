package org.example.cachetest;

import org.example.cachetest.dto.RequestUpdateDto;
import org.example.cachetest.service.MenuCacheService;
import org.example.cachetest.service.MenuService;
import org.example.cachetest.vo.Menu;
import org.example.cachetest.vo.MenuList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CacheTestApplicationTests {
    @Autowired
    MenuService menuService;
    @Autowired
    MenuCacheService menuCacheService;

    @Test
    void nonCacheSelectTest() {
        // 캐싱이 되어있지 않는 상태에서 같은 데이터를 반복적으로 조회를 요구할경우 쿼리문이 반복한 수 만큼 발생 하는것을 확인 할수 있음.
        MenuList menuList1 = menuService.getMenu();
        MenuList menuList2 = menuService.getMenu();
    }

    @Test
    void useCacheSelectTest() {
        // 캐싱이 되어있는 상태에서 같은 데이터를 반복적으로 조회를 요구할경우 쿼리문이 반복적으로 발생하지 않는것을 확인할수 있음.
        MenuList menuList1 = menuCacheService.getMenu();
        MenuList menuList2 = menuCacheService.getMenu();
    }

    @Test
    void useCacheUpdateTest() {
        // 캐싱이 되어있는 상태에서 데이터를 수정하면 캐싱되어있는 내용이 삭제 되기 때문에 그 이후에는 조회하는 쿼리문이 발생하지만, 다음 조회부터는
        // 캐싱이 되어있기때문에 쿼리문이 발생하지 않음.
        MenuList menuList1 = menuCacheService.getMenu();
        menuCacheService.updateMenu(new RequestUpdateDto(1L, "수정이름", "수정타입"));
        MenuList menuList2 = menuCacheService.getMenu();
        MenuList menuList3 = menuCacheService.getMenu();
    }

    @Test
    void useCacheDeleteTest() {
        // useCacheUpdateTest와 원리는 똑같음
        MenuList menuList1 = menuCacheService.getMenu();
        menuCacheService.deleteMenu(1L);
        MenuList menuList2 = menuCacheService.getMenu();
        MenuList menuList3 = menuCacheService.getMenu();
    }
}
