package com.elfctn.todoRESTAPI.repository;

import com.elfctn.todoRESTAPI.model.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// @Repository: Bu anotasyon, bu arayüzün bir Spring bileşeni olduğunu ve veritabanı işlemleri için kullanıldığını belirtir.
// Spring, çalışma zamanında bu arayüz için otomatik olarak bir implementasyon (gerçekleme) oluşturur.
@Repository
// JpaRepository<Varlık_Tipi, Kimlik_Tipi>:
// JpaRepository'yi genişleterek, TodoItem nesneleri üzerinde temel CRUD (Create, Read, Update, Delete) operasyonlarını
// (save, findById, findAll, deleteById vb.) sağlayan metotları otomatik olarak miras alırız.
public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {
    // Özel sorgulara ihtiyacımız olursa buraya ekleyebiliriz.
    // Örneğin, description'a göre TodoItem bulmak için:
    // Optional<TodoItem> findByDescription(String description);
    //
    // Veya tamamlanmış görevleri bulmak için:
    // List<TodoItem> findByCompletedTrue();
    //
    // Şimdilik boş bırakabilirim, JpaRepository'nin sağladığı metotlar bana yeterli olacaktır diye düşünüyorum
}