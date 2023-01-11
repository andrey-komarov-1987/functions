package com.example.functions.controller;

import com.example.functions.domain.Thing;
import com.example.functions.service.FunctionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/functions")
@RequiredArgsConstructor
@Slf4j
public class FunctionController {

    private final FunctionService functionService;

    @GetMapping("/jpa")
    public ResponseEntity<List<Thing>> jpaFindThings(@RequestBody List<Integer> ids) {
        log.info("jpaFindThings() - start, ids = {}", StringUtils.collectionToDelimitedString(ids, ", "));
        List<Thing> result = functionService.jpaFindThings(ids);
        log.info("jpaFindThings() - end, count = {}", result.size());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/native")
    public ResponseEntity<List<Thing>> nativeFindThings(@RequestBody List<Integer> ids) {
        log.info("nativeFindThings() - start, ids = {}", StringUtils.collectionToDelimitedString(ids, ", "));
        List<Thing> result = functionService.nativeFindThings(ids);
        log.info("nativeFindThings() - end, count = {}", result.size());
        return ResponseEntity.ok(result);
    }
}
