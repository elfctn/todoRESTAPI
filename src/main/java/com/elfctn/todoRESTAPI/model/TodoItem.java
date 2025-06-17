package com.elfctn.todoRESTAPI.model; // Paket adınız buraya göre değişecektir

import jakarta.persistence.*; // JPA (Java Persistence API) anotasyonları için gerekli
import lombok.Data; // Lombok'un @Data anotasyonu için gerekli
import lombok.NoArgsConstructor; // Lombok'un @NoArgsConstructor anotasyonu için gerekli
import lombok.AllArgsConstructor; // Lombok'un @AllArgsConstructor anotasyonu için gerekli

// @Entity: Bu anotasyon, bu sınıfın bir JPA varlığı olduğunu ve veritabanı tablosuyla eşleneceğini belirtir.
@Entity
// @Table: Bu anotasyon, bu varlığın eşleneceği veritabanı tablosunun adını belirtir.
// Eğer belirtmezsek sınıf adını (TodoItem) kullanır, ancak küçük harfle ve çoğul yapmak iyi bir pratiktir (ör: todos).
@Table(name = "todos") // Veritabanındaki tablo adı 'todos' olacak
@Data // Lombok anotasyonu: Otomatik olarak getter, setter, toString, equals ve hashCode metotlarını oluşturur.
@NoArgsConstructor // Lombok anotasyonu: Parametresiz bir yapıcı metot (constructor) oluşturur. JPA için gereklidir.
@AllArgsConstructor // Lombok anotasyonu: Tüm argümanları içeren bir yapıcı metot (constructor) oluşturur.
public class TodoItem {

    // @Id: Bu alanın birincil anahtar (Primary Key) olduğunu belirtir.
    @Id
    // @GeneratedValue: Birincil anahtarın nasıl üretileceğini belirtir.
    // IDENTITY: Veritabanı tarafından otomatik artırılan bir kimlik sütunu kullanır (PostgreSQL'deki SERIAL/BIGSERIAL gibi).
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Yapılacak öğenin benzersiz kimliği

    // @Column: Veritabanı sütun ayarlamaları için kullanılır.
    // nullable = false: Bu sütunun null değer alamayacağını belirtir (zorunlu alan).
    // length = 255: VARCHAR(255) gibi bir uzunluk sınırı belirler.
    @Column(nullable = false, length = 255)
    private String description; // Yapılacak öğenin açıklaması

    @Column(nullable = false)
    private Boolean completed = false; // Yapılacak öğenin tamamlanıp tamamlanmadığı, varsayılan değeri false olacak

    // OOP Encapsulation (Kapsülleme) Notu:
    // Alanları (id, description, completed) 'private' yaparak ve Lombok'un @Data anotasyonu ile
    // otomatik 'public' getter/setter metotları oluşturarak Encapsulation prensibini uyguluyoruz.
    // Bu, sınıfın iç durumunun dışarıdan doğrudan erişilmesini engeller ve kontrollü erişim sağlar.
}