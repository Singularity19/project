Задание 1.
Постройте график по среднему количеству землетрясений для каждого года.

<img width="361" alt="251630a5-de4e-40df-add1-e160a8dd0582" src="https://github.com/Singularity19/project/assets/154615066/febbb2a7-4919-4f95-adeb-c38bd9c30be6">

Задание 2,3.
Выведите в консоль среднюю магнитуду для штата "West Virginia".

Выведите в консоль название штата в котором произошло самое глубокое землетрясение в 2013 году.
![19f67055-bdfb-4110-aed8-0e8449c62b4c](https://github.com/Singularity19/project/assets/154615066/9fcef7b9-c233-414f-b5bc-56344ef6030d)

![0a83e8dc-1c31-4942-9055-036c085a00e1](https://github.com/Singularity19/project/assets/154615066/89239393-b877-4c02-80b7-cbaa5750eced)

Описание работы кода:

1.Класс EarthquakeData:

Класс "EarthquakeData" представляет данные о землетрясениях. Он содержит поля для идентификатора ("id"), глубины ("depth"), типа магнитуды ("magnitudeType"), магнитуды ("magnitude"), названия штата ("state") и времени ("time") землетрясения.

Класс имеет конструктор для инициализации объекта "EarthquakeData" и методы доступа для получения значений полей.

2.Метод "parseCSV":
Метод "parseCSV" читает CSV-файл, разбивает его строки на компоненты и создает объекты "EarthquakeData" на основе данных из CSV-файла.

Он использует разделение строки с учётом кавычек для правильного разделения данных, игнорируя запятые внутри парных кавычек.

3.Метод "main":
Метод "main" является точкой входа в программу.

Он читает данные из CSV-файла с землетрясениями, затем создает и/или подключается к базе данных SQLite (если она существует или создаёт новую), создает таблицу Earthquakes (если она еще не существует), и вставляет данные землетрясений в базу данных, игнорируя дубликаты по идентификатору.

Выполняет несколько SQL-запросов к базе данных:
    
    "query1": Вычисляет среднюю магнитуду для каждого года.
    
    "query2": Выводит в консоль среднюю магнитуду для штата "West Virginia".
    
    "query3": Находит штат, в котором произошло самое глубокое землетрясение в 2013 году.

Результаты SQL-запросов выводятся в консоль.

4.Работа с базой данных:

Для работы с базой данных используются объекты "Connection", "Statement", "PreparedStatement" и "ResultSet" из пакета "java.sql".
Создание таблицы, вставка данных и выполнение запросов происходит через SQL-запросы.

Созданная БД по требованиям:
![f0e0df5d-10b4-422d-a32a-b32ffda01acb](https://github.com/Singularity19/project/assets/154615066/a715bf2b-8689-4edb-93f1-fbd954b3372c)

CSV:

![0a67a9b8-5ec9-4c7d-ba02-163fa30bc90c](https://github.com/Singularity19/project/assets/154615066/1ef4c60e-8be4-4bc1-ad76-1d5b7e83af79)

