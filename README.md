## Проект автоматизированных тестов Sandbox Товаров

### Опсание структуры проекта

#### Файловая структура

```
AutoSandboxTests
├───.allure           - каталога для генерации отчета
│   └───allure-2.26.0 - это версия allure-commandline, при обновлении в pom.xml переменной allure.report.version - будет создаваться новый каталог рядом, старые каталоги при накоплении можно удалять вручную 
│       └───...       - в данном каталоге файлы Allure, необходимые для генерации отчета
│ 
├───.idea             - каталог с настройками проекта для IntelliJ IDEA
│    └───... 
├───drivers           - каталог с бинарными файлами Selenium Web-драйверов, хранится в Git
└───src               - каталог исходных кодов проекта, все вложенное хранится в Git
    ├───main          - каталог разрабатываемого приложения и/или дополнительного переопределяющего функционала для библиотек
    │   ├───java      - каталог исходных кодов Java
    │   │   └───org.example        - домен 1-го уровня
    │   │        ├───data          - классы хранения тестовых данных для автотестов
    │   │        ├───managers      - классы для управления драйвером и переменными среды
    │   │        └───pages         - классы для стандартного поведения при загрузке страниц
    │   └───resources              - каталог с настройками среды и драйверами
    │       └───drivers            - каталог с драйверами                  
    └───test          - каталог автотестов тестируемого/разрабатываемого приложения
        └───java      - каталог исходных кодов Java

```

## Описание окружений, основные точки входа

Работает только на стендах Новикомбанка

* http://localhost:8080/ - тестовый стенд
* http://localhost:9090/ - Jenkins

### Принципы разделения тестов

### Особенности запуска автотестов

* Простые тесты, которые можно запустить без дополнительной подготовки

## Используемые технологии

* **Allure** - фреймворк для формирования отчетности автотестов
* **Git** - инструмент для работы с системой контроля версий
* **IntelliJ IDEA** - среда разработки автоматизированных тестов
* **JDK** - среда исполнения Java кода
* **Jenkins** - система для автоматизации процессов разработки программного обеспечения