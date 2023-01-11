package com.example.functions.service;

import com.example.functions.repository.ThingRepository;
import com.example.functions.domain.Thing;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FunctionService {

    private final ThingRepository thingRepository;

    public List<Thing> jpaFindThings(List<Integer> ids) {
        return thingRepository.jpaFindByIds(prepare(ids));
    }

    public List<Thing> nativeFindThings(List<Integer> ids) {
        return thingRepository.nativeFindByIds(prepare(ids));
    }

    private String prepare(List<Integer> ids) {
        Objects.requireNonNull(ids);
        String result = StringUtils.collectionToDelimitedString(
                ids.stream().filter(Objects::nonNull).collect(Collectors.toSet()), ",");
        if (!StringUtils.hasText(result)) {
            throw new IllegalArgumentException("Empty id list");
        }
        return result;
    }
}
