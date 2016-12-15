# DiachenkoTokenizer
Необходимо разработать программу, которая получает на вход список ресурсов, содержащих текст,
и считает общее количество вхождений (для всех ресурсов) каждого слова.
Каждый ресурс должен быть обработан в отдельном потоке, текст не должен содержать инностранных символов,
только кириллица, знаки препинания и цифры. Отчет должен строиться в режиме реального времени,
знаки препинания и цифры в отчет не входят. Все ошибки должны быть корректно обработаны,
все API покрыто модульными тестами
Входящий аргумент args содержит массив адресных строк ресурсов, например
 {"D:\\Temp\\Examples\\TextForTokenizer1.txt",
  "D:\\Temp\\Examples\\TextForTokenizer2.txt",
  "http://vjanetta.narod.ru/text.html"};
