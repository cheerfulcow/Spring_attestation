package com.example.springsecurityapplication.repositories;

import com.example.springsecurityapplication.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

//Тут описываем методы для поиска и сортировки продуктов

//Данные методы СЛЕДУЕТ ТАКЖЕ ПРОПИСАТЬ В СЕРВИСНОМ СЛОЕ, в данном проекте этот момент опущен
@Repository
public interface ProductRepository extends JpaRepository <Product, Integer>{

    //Поиск продуктов по наименованию(по подстроке(части) наименования), игнорируя регистр
    List<Product> findByTitleContainingIgnoreCase(String title);

    // Поиск по наименованию и фильтрация по указанному диапазону цены От - До
    // lower = приводим в нижний регистр,
    // !ВАЖНО! - если в БД наименование колонки отличается от названия поля в модели товара, то в запросе пишем ТОЧНО ТАК КАК ОНА НАЗЫВАЕТСЯ В БД, т.е. если в модели мы в поле указали название @Column(name = "Наименование"), то в postgre столбец будет назван "наименование" и вот записывать надо именно в нижнем регистре, иначе статус 500.
    //?1 - указывает, что это первый параметр нашего метода (String title);
    //Маски: %?1% означает, что искомое значение (?1 = String title) может быть в середине строки, ?1% - в начале строки, '%?1' - в конце строки
    // price >=?2 and price <=?3 : указано, что должен быть диапазон между вторым и третьем параметром метода (float ot, float Do).
    //NativeQuery = true включает методы с применением SQL
    //Обобщение: выбрать все из таблицы product, где слово/фраза (title) может быть в середине или конце или начале строки И (price) должна быть от принятого параметра float ot до float Do)
    @Query(value = "select * from product where ((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') OR (lower(title) LIKE '%?1')) and (price >= ?2 and price <= ?3)", nativeQuery = true)
    List<Product> findByTitleAndPriceGreaterThanEqualAndPriceLessThanEqual(String title, float ot, float Do);

    //Поиск по наименованию (ключевая фраза в любом месте) + фильтр по указанному диапазону цены + сортировка
    //отфильтрованных цен по возрастанию (order by по умолчанию сортирует по возрастанию)
    @Query(value = "select * from product where ((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') OR (lower(title) LIKE '%?1')) and (price >= ?2 and price <= ?3) order by price",nativeQuery = true)
    List<Product> findByTitleOrderByPriceAsc(String title, float ot, float Do);

    //Поиск по наименованию (ключевая фраза в любом месте) + фильтр по указанному диапазону цены + сортировка
    //отфильтрованных цен по убыванию
    @Query (value = "select * from product where ((lower(title) LIKE %?1%) OR (lower(title) LIKE '?1%') OR (lower(title) LIKE '%?1')) and (price >=?2 and price <=?3) order by price desc ", nativeQuery = true)
    List<Product> findByTitleOrderByPriceDesc(String title, float ot, float Do);

    //Поиск по наименованию (ключевая фраза в любом месте) + фильтр по указанному диапазону цены + сортировка
    //отфильтрованных цен по возрастанию + фильтрацию по категории
    @Query (value = "select * from product where category_id = ?4 and ((lower(title) LIKE %?1%) OR (lower(title) LIKE '?1%') OR (lower(title) LIKE '%?1')) and (price >=?2 and price <=?3) order by price", nativeQuery = true)
    List<Product> findByTitleAndCategoryOrderByPriceAsc(String title, float ot, float Do, int category);

    //Поиск по наименованию (ключевая фраза в любом месте) + фильтр по указанному диапазону цены + сортировка
    //отфильтрованных цен по убыванию + фильтрацию по категории
    @Query (value = "select * from product where category_id = ?4 and ((lower(title) LIKE %?1%) OR (lower(title) LIKE '?1%') OR (lower(title) LIKE '%?1')) and (price >=?2 and price <=?3) order by price desc", nativeQuery = true)
    List<Product> findByTitleAndCategoryOrderByPriceDesc(String title, float ot, float Do, int category);


}
