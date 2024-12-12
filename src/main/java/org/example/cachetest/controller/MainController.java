package org.example.cachetest.controller;

import lombok.RequiredArgsConstructor;
import org.example.cachetest.dto.RequestUpdateDto;
import org.example.cachetest.service.MenuCacheService;
import org.example.cachetest.service.MenuService;
import org.example.cachetest.vo.Menu;
import org.example.cachetest.vo.MenuList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MainController {
//    private final MenuService menuService;
    private final MenuCacheService menuService;

    @GetMapping("/menu")
    public ResponseEntity<MenuList> getMenu() {
        return new ResponseEntity<>(menuService.getMenu(), HttpStatus.OK);
    }

    @GetMapping("/menu/{id}")
    public ResponseEntity<Menu> getMenuById(@PathVariable Long id) {
        return new ResponseEntity<>(menuService.getMenuById(id), HttpStatus.OK);
    }

    @PostMapping("/menu/update")
    public String updateMenu(@RequestBody RequestUpdateDto requestUpdateDto) {
        menuService.updateMenu(requestUpdateDto);
        return "update Menu";
    }

    @DeleteMapping("/menu/{id}")
    public String deleteMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);
        return "delete Menu";
    }
}
