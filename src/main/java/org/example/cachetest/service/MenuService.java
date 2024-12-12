package org.example.cachetest.service;

import lombok.RequiredArgsConstructor;
import org.example.cachetest.dto.RequestUpdateDto;
import org.example.cachetest.entity.MenuEntity;
import org.example.cachetest.repository.MenuRepository;
import org.example.cachetest.vo.Menu;
import org.example.cachetest.vo.MenuList;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;

    public MenuList getMenu() {
        // Redis 데이터 송수신시에 List형태의 데이터를 직렬화를 하지 못해 객체형태로 랩핑하여 처리
        return MenuList.builder().menuList(menuRepository.findAll().stream().map(item -> {
            return Menu.builder().id(item.getId()).name(item.getName()).type(item.getType()).build();
        }).toList()).build();
    }

    public Menu getMenuById(Long id) {
        MenuEntity menuEntity = menuRepository.findById(id).orElseThrow(() -> new RuntimeException("null exception"));
        return Menu.builder().id(menuEntity.getId()).name(menuEntity.getName()).type(menuEntity.getType()).build();
    }

    @Transactional
    public void updateMenu(RequestUpdateDto requestUpdateDto) {
        MenuEntity menuEntity = menuRepository.findById(requestUpdateDto.getId()).orElseThrow(() -> new RuntimeException("null exception"));
        menuEntity.changeName(requestUpdateDto.getChangeName());
        menuEntity.changeType(requestUpdateDto.getChangeType());
    }

    public void deleteMenu(Long id) {
        menuRepository.deleteById(id);
    }
}
