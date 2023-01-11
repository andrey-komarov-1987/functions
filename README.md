# Отбор сущностей по списку значений
Проект демонстрирует отбор сущностей по списку значений с использованием `jpa` и `native` запросов

## Мотивация
Использование предиката `in` приводит к созданию запросов, список параметров которых различается.
Каждый такой запрос должен быть отдельно подготовлен; затрудняется сбор статистики

## Реализация
Предлагается передавать значения в одном параметре. 
Для передачи используется comma separated string, 
так как использование параметров с типом `ARRAY` в `jpa` без [дополнительных зависимостей](https://github.com/vladmihalcea/hypersistence-utils/commit/e87179d65021024ec06c624535df3c67f39ba69e) затруднено.

При выполнении запроса 
* список разворачивается с использованием [string_to_array](https://www.postgresql.org/docs/9.1/functions-array.html)
* значения списка приводятся к `INTEGER`
* проверяется вхождение поля в массив с использованием [ANY](https://stackoverflow.com/questions/31191507/how-to-use-any-instead-of-in-in-a-where-clause/31192557#31192557)

В `native` запросах предлагается использовать данную конструкцю напрямую ввиду ее простоты, см. `com.example.functions.repository.ThingRepository.jpaFindByIds`
```postgresql
SELECT t.id, t.name FROM thing t WHERE t.id = ANY(string_to_array('1,2', ',')::int[])
```

Для `jpa` зарегистрирован шаблон [any_int](org.hibernate.boot.MetadataBuilder.applySqlFunction), см. `com.example.functions.repository.ThingRepository.nativeFindByIds`
```hql
SELECT t FROM Thing t WHERE t = FUNCTION('any_int', :ids)
```
