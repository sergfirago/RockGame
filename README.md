# RockGame
# Игра "Камень, ножницы, бумага" с голосовым управлением. Используется SpeechKit 

## Простой пример использования библиотеки SpeechKit (Комплекс речевых технологий Яндекса) на примере игры "Камень, ножницы, бумага"


![Imgur](https://i.imgur.com/DTHjpnRl.png)
![Imgur](https://i.imgur.com/zQPL2uXl.png)

При нажатии на кнопке "Микрофон" происходит запись голоса и передача это записи на сервера для распознавания. В случае если в речи присутствовало слово из списка (камень, ножницы, бумага) генерируется случайны выбор - ответ приложение и результаты показываются на экране. Также результат проговаривается голосом.

![Imgur](https://i.imgur.com/kSqyNbWl.png)
![Imgur](https://i.imgur.com/66beXGtl.png)
![Imgur](https://i.imgur.com/zEm2Lq1l.png)


Использовались библиотеки
```
    implementation 'com.yandex.android:speechkit:3.12.2'

    implementation "android.arch.lifecycle:extensions:1.1.1"
    annotationProcessor "android.arch.lifecycle:compiler:1.1.1"
    
    implementation "org.koin:koin-android-architecture:0.9.2"
```
