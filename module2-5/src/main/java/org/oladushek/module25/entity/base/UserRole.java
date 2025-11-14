package org.oladushek.module25.entity.base;

public enum UserRole {
    ADMIN, //полный доступ ко всем данным и операциям приложения
    MODERATOR, //права уровня USER, а также чтение всех User, чтение/изменение/удаление всех Events и Files
    USER; //только чтение своих данных и загрузка файлов для себя
}
