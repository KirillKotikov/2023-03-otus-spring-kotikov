### Домашнее задание:<br/>

## pull request #1 - w01-01:
#### Приложение по проведению тестирования студентов - только вывод вопросов и вариантов ответа (если имеются)

> #### Цель:
>> Создать приложение с помощью Spring IoC, чтобы познакомиться с основной функциональностью IoC, на которой строится весь Spring.
<br/>Результат: простое приложение, сконфигурированное XML-контекстом.


> #### Описание/Пошаговая инструкция выполнения домашнего задания:
>> #####  Описание задания:
>>> В ресурсах хранятся вопросы и различные ответы к ним в виде CSV файла (5 вопросов).
>>> <br/>Вопросы могут быть с выбором из нескольких вариантов или со свободным ответом - на Ваше желание и усмотрение.
>>> <br/>Приложение должна просто вывести вопросы теста из CSV-файла с возможными вариантами ответа (если имеются).
>> #### Требования:
>>> 1) В приложении должна присутствовать объектная модель (отдаём предпочтение объектам и классам, а не строчкам и массивам/спискам строчек).
>>> 2) Все классы в приложении должны решать строго определённую задачу (см. п. 18-19 "Правила оформления кода.pdf", прикреплённые к материалам занятия).
>>> 3) Контекст описывается XML-файлом.
>>> 4) Все зависимости должны быть настроены в IoC контейнере.
>>> 5) Имя ресурса с вопросами (CSV-файла) необходимо захардкодить строчкой в XML-файле с контекстом.
>>> 6) CSV с вопросами читается именно как ресурс, а не как файл.
>>> 7) Scanner, PrintStream и другие стандартные типы в контекст класть не нужно!
>>> 8) Весь ввод-вывод осуществляется на английском языке.
>>> 9) Крайне желательно написать юнит-тест какого-нибудь сервиса (оцениваться будет только попытка написать тест).
>>> 10) Помним - "без фанатизма".


## pull request #2 - hw01-02:
#### Приложение по проведению тестирования студентов (с самим тестированием)

> #### Цель:
>> Конфигурировать Spring-приложения современным способом, как это и делается в современном мире
<br/>Результат: готовое современное приложение на чистом Spring


> #### Описание/Пошаговая инструкция выполнения домашнего задания:
>> #####  Описание задания:
>>> В ресурсах хранятся вопросы и различные ответы к ним в виде CSV файла (5 вопросов).
>>> <br/>Вопросы могут быть с выбором из нескольких вариантов или со свободным ответом - на Ваше желание и усмотрение.
>>> <br/>Программа должна спросить у пользователя фамилию и имя, спросить 5 вопросов из CSV-файла и вывести результат тестирования.
>>> <br/>Выполняется на основе предыдущего домашнего задания + , собственно, сам функционал тестирования.  
>> #### Требования:
>>> 1) В приложении должна присутствовать объектная модель (отдаём предпочтение объектам и классам, а не строчкам и массивам/спискам строчек).
>>> 2) Переписать конфигурацию в виде Java + Annotation-based конфигурации. Все зависимости должны быть настроены в IoC контейнере.
>>> 3) Добавить функционал тестирования студента.
>>> 4) Добавьте файл настроек для приложения тестирования студентов.
>>> 5) В конфигурационный файл можно поместить путь до CSV-файла, количество правильных ответов для зачёта - на Ваше усмотрение.
>>> 6) Помним, CSV с вопросами читается именно как ресурс, а не как файл.
>>> 7) Нужно написать интеграционный тест класса, читающего вопросы и юнит-тест сервиса с моком зависимости
>>> 8) Файл настроек и файл с вопросами, для тестов д.б. свои.
>>> 9) Scanner, PrintStream и другие стандартные типы в контекст класть не нужно! См. соответствующие слайды с занятия.
>>> 10) Весь ввод-вывод осуществляется на английском языке.
>>> 11) Помним, "без фанатизма" :)
