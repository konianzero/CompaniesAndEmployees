Информационная система по кампаниям и их сотрудникам
----------------------------------------------------

Ветка проекта: `master`
```shell
─── master (Spring JDBC : Spring Boot : Vaadin)
    │
    └── JOOQ (JOOQ : Spring Boot : Vaadin)
```
___

Приложение позволяет добавлять/удалять/редактировать компании и сотрудников через веб интерфейс (Vaadin 14).  

![компании](images/companies_tab.png)
![сотрудники](images/employee_tab.png)

Редактирование происходит в модальном окне. Установлены диапазон допустимых значений для вводимых параметров,
маска телефонного номера, русифицированный календарь, в редакторе сотрудника выпадающий список компаний.  

![](images/company_edit.png) ![](images/company_edit_valid.png) ![](images/calendar.png) ![](images/companies_drop-down.png)

Общий поиск для компаний (по всем полям), для сотрудников по отдельному полю.

![](images/search_by_company.png)

Всплывающие уведомления при добавлении/удалении.

![](images/notofication.png)

База данных Postgres, для работы с БД используется NamedParameterJdbcTemplate от Spring.

---

### Требования

- JDK 11
- Maven 3

---

### Запуск
```
 mvn spring-boot:run
```

[http://localhost:8080/](http://localhost:8080/)

