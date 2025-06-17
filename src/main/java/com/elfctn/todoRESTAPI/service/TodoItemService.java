package com.elfctn.todoRESTAPI.service;

import com.elfctn.todoRESTAPI.model.TodoItem;
import com.elfctn.todoRESTAPI.repository.TodoItemRepository;
import lombok.RequiredArgsConstructor; // Lombok'un @RequiredArgsConstructor anotasyonu için gerekli
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

// @Service: Bu anotasyon, bu sınıfın bir servis bileşeni olduğunu ve iş mantığını barındırdığını belirtir.
// Spring, bu sınıfı bir Spring Bean'i olarak yönetir ve diğer bileşenlere enjekte edilebilir hale getirir.
@Service
// @RequiredArgsConstructor: Lombok anotasyonu. 'final' olarak işaretlenmiş alanlar için otomatik olarak bir constructor oluşturur.
// Bu, bağımlılık enjeksiyonu (Dependency Injection - DI) için constructor enjeksiyonunu kolaylaştırır.
// Sprint 17'deki "Spring Dependency Injection" konusunun pratik uygulamasıdır.
@RequiredArgsConstructor
public class TodoItemService {

    // TodoItemRepository bağımlılığını enjekte ediyoruz.
    // 'final' olarak tanımlanması ve @RequiredArgsConstructor kullanılması, constructor enjeksiyonunu tercih ettiğimiz anlamına gelir.
    private final TodoItemRepository todoItemRepository;

    // --- CRUD İşlemleri ---

    // CREATE (Oluşturma)
    // Yeni bir TodoItem'ı kaydeder.
    public TodoItem createTodoItem(TodoItem todoItem) {
        // İş mantığı: Örneğin, description'ın boş olup olmadığını kontrol edebiliriz.
        if (todoItem.getDescription() == null || todoItem.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Todo item description cannot be empty.");
        }
        // Repository üzerinden veritabanına kaydetme işlemi.
        return todoItemRepository.save(todoItem);
    }

    // READ (Okuma) - Tümünü Getir
    // Tüm TodoItem'ları listeler.
    public List<TodoItem> getAllTodoItems() {
        return todoItemRepository.findAll();
    }

    // READ (Okuma) - ID ile Getir
    // Belirli bir ID'ye sahip TodoItem'ı getirir. Optional, sonucun var olup olmadığını güvenli bir şekilde kontrol etmeyi sağlar.
    public Optional<TodoItem> getTodoItemById(Long id) {
        return todoItemRepository.findById(id);
    }

    // UPDATE (Güncelleme)
    // Mevcut bir TodoItem'ı günceller.
    public TodoItem updateTodoItem(Long id, TodoItem todoItemDetails) {
        // ID ile öğeyi bulmaya çalış. Eğer yoksa bir hata fırlatabiliriz.
        TodoItem todoItem = todoItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TodoItem not found with id: " + id)); // Hata yönetimine giriş

        // Gelen detaylarla mevcut öğeyi güncelle.
        // OOP Encapsulation prensibi: Nesnenin durumunu setter metotlarıyla değiştiriyoruz.
        todoItem.setDescription(todoItemDetails.getDescription());
        todoItem.setCompleted(todoItemDetails.getCompleted());

        // Güncellenmiş öğeyi veritabanına kaydet.
        return todoItemRepository.save(todoItem);
    }

    // DELETE (Silme)
    // Belirli bir ID'ye sahip TodoItem'ı siler.
    public void deleteTodoItem(Long id) {
        // Silmeden önce var olup olmadığını kontrol etmek iyi bir pratiktir.
        if (!todoItemRepository.existsById(id)) {
            throw new RuntimeException("TodoItem not found with id: " + id); // Hata yönetimine giriş
        }
        todoItemRepository.deleteById(id);
    }

    // --- Ek İş Mantığı (Polymorphism, Abstraction, SOLID için örnekler) ---

    // Örneğin, 'completed' durumunu özel olarak güncellemek için bir metot:
    public TodoItem toggleTodoItemCompletion(Long id) {
        TodoItem todoItem = todoItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TodoItem not found with id: " + id));
        todoItem.setCompleted(!todoItem.getCompleted()); // Durumu tersine çevir
        return todoItemRepository.save(todoItem);
    }

    // Not: Bu basit projede Inheritance, Composition, Polymorphism, Abstraction'ın tüm yönlerini tam olarak göstermek zor.
    // Ancak Service katmanı, farklı iş mantıklarını modüler metotlara ayırarak Single Responsibility Principle'a (SOLID) uymamıza yardımcı olur.
    // İleride daha karmaşık senaryolarda bu prensipleri daha derinlemesine uygulayabiliriz.
}