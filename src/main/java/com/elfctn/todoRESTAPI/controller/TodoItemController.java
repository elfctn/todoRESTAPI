package com.elfctn.todoRESTAPI.controller;

import com.elfctn.todoRESTAPI.model.TodoItem;
import com.elfctn.todoRESTAPI.service.TodoItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus; // HTTP durum kodları için
import org.springframework.http.ResponseEntity; // HTTP yanıtları için
import org.springframework.web.bind.annotation.*; // REST API anotasyonları için

import java.util.List;

// @RestController: Bu anotasyon, sınıfın bir REST kontrolcüsü olduğunu ve metotlarının doğrudan HTTP yanıtı döndüreceğini belirtir.
@RestController
// @RequestMapping: Bu kontrolcüdeki tüm metotların temel URL'sini belirtir. RESTful API standartlarına uygun olarak çoğul isimler kullanılır.
@RequestMapping("/api/v1/todos") // Tüm To-Do item endpoint'leri /api/v1/todos altında olacak
@RequiredArgsConstructor // Lombok anotasyonu: Constructor enjeksiyonu için
public class TodoItemController {

    private final TodoItemService todoItemService; // TodoItemService bağımlılığını enjekte ediyoruz.

    // GET /api/v1/todos
    // Tüm yapılacaklar öğelerini getirir.
    // ResponseEntity: HTTP yanıtının durum kodunu, başlıklarını ve gövdesini özelleştirmemizi sağlar.
    @GetMapping
    public ResponseEntity<List<TodoItem>> getAllTodoItems() {
        List<TodoItem> todoItems = todoItemService.getAllTodoItems();
        return ResponseEntity.ok(todoItems); // 200 OK yanıtı ile birlikte öğeleri döndür
    }

    // GET /api/v1/todos/{id}
    // Belirli bir ID'ye sahip yapılacaklar öğesini getirir.
    // @PathVariable: URL yolundaki {id} değerini metodun 'id' parametresine bağlar.
    @GetMapping("/{id}")
    public ResponseEntity<TodoItem> getTodoItemById(@PathVariable Long id) {
        return todoItemService.getTodoItemById(id)
                .map(ResponseEntity::ok) // Eğer öğe bulunursa 200 OK ile döndür
                .orElseGet(() -> ResponseEntity.notFound().build()); // Bulunamazsa 404 Not Found döndür
    }

    // POST /api/v1/todos
    // Yeni bir yapılacaklar öğesi oluşturur.
    // @RequestBody: HTTP isteğinin gövdesindeki JSON verisini TodoItem nesnesine dönüştürür.
    // @ResponseStatus(HttpStatus.CREATED): Başarılı bir POST işleminde 201 Created durum kodunu döndürür.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Başarılı bir oluşturma durumunda HTTP 201 Created döndürür
    public TodoItem createTodoItem(@RequestBody TodoItem todoItem) {
        return todoItemService.createTodoItem(todoItem);
    }

    // PUT /api/v1/todos/{id}
    // Belirli bir ID'ye sahip yapılacaklar öğesini günceller.
    @PutMapping("/{id}")
    public ResponseEntity<TodoItem> updateTodoItem(@PathVariable Long id, @RequestBody TodoItem todoItemDetails) {
        try {
            TodoItem updatedTodoItem = todoItemService.updateTodoItem(id, todoItemDetails);
            return ResponseEntity.ok(updatedTodoItem); // 200 OK ile güncellenmiş öğeyi döndür
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // Eğer öğe bulunamazsa 404 Not Found döndür
        }
    }

    // DELETE /api/v1/todos/{id}
    // Belirli bir ID'ye sahip yapılacaklar öğesini siler.
    // @ResponseStatus(HttpStatus.NO_CONTENT): Başarılı bir silme işleminde 204 No Content durum kodunu döndürür.
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Başarılı bir silme durumunda HTTP 204 No Content döndürür
    public void deleteTodoItem(@PathVariable Long id) {
        try {
            todoItemService.deleteTodoItem(id);
        } catch (RuntimeException e) {
            // Silinecek öğe bulunamazsa 404 Not Found yerine 204 No Content döndürmek RESTful prensiplere daha uygundur.
            // Çünkü idempotency (aynı işlemin birden fazla kez uygulanmasının aynı sonucu vermesi) sağlanır.
            // Yani, silmek istediğiniz şey zaten yoksa da "başarılı" kabul edilir.
            // Ancak, hata durumunu açıkça belirtmek isteniyorsa burada ResponseEntity.notFound().build() da döndürülebilir.
            // Şimdilik basit tutmak için exception fırlatırız ve Spring default olarak 500 döner.
            // Daha iyi hata yönetimi için Sprint 17'deki @ControllerAdvice kullanacağız.
        }
    }

    // PUT /api/v1/todos/{id}/toggle
    // Belirli bir yapılacaklar öğesinin tamamlanma durumunu değiştirir (true ise false, false ise true yapar).
    @PutMapping("/{id}/toggle")
    public ResponseEntity<TodoItem> toggleTodoItemCompletion(@PathVariable Long id) {
        try {
            TodoItem toggledTodoItem = todoItemService.toggleTodoItemCompletion(id);
            return ResponseEntity.ok(toggledTodoItem);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}