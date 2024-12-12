package org.example.cachetest.service;

import lombok.RequiredArgsConstructor;
import org.example.cachetest.dto.RequestUpdateDto;
import org.example.cachetest.entity.MenuEntity;
import org.example.cachetest.repository.MenuRepository;
import org.example.cachetest.vo.Menu;
import org.example.cachetest.vo.MenuList;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuCacheService {
    private final MenuRepository menuRepository;

    @Cacheable(value = "menu", key = "'all'")
    public MenuList getMenu() {
        // Redis 데이터 송수신시에 List형태의 데이터를 직렬화를 하지 못해 객체형태로 랩핑하여 처리
        return MenuList.builder().menuList(menuRepository.findAll().stream().map(item -> {
            return Menu.builder().id(item.getId()).name(item.getName()).type(item.getType()).build();
        }).toList()).build();
    }

    @Cacheable(value = "menu", key = "#id")
    public Menu getMenuById(Long id) {
        MenuEntity menuEntity = menuRepository.findById(id).orElseThrow(() -> new RuntimeException("null exception"));
        return Menu.builder().id(menuEntity.getId()).name(menuEntity.getName()).type(menuEntity.getType()).build();
    }

    @Transactional
    @CacheEvict(value = "menu", allEntries = true)  //menu에 있는 모든 캐시데이터 삭제
    public Menu updateMenu(RequestUpdateDto requestUpdateDto) {
        MenuEntity menuEntity = menuRepository.findById(requestUpdateDto.getId()).orElseThrow(() -> new RuntimeException("null exception"));
        menuEntity.changeName(requestUpdateDto.getChangeName());
        menuEntity.changeType(requestUpdateDto.getChangeType());
        //리턴 타입이 존재해야하며 select해야할 서비스 메소드의 리턴과 일치해야함...
        //리턴 타입을 캐시 메모리에 저장하는것으로 보임
        return Menu.builder().id(menuEntity.getId()).name(menuEntity.getName()).type(menuEntity.getType()).build();
    }

    @CacheEvict(value = "menu", allEntries = true)
    public void deleteMenu(Long id) {
        menuRepository.deleteById(id);
    }
}
