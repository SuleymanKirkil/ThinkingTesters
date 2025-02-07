package com.thinkingtesters.examples;

import com.thinkingtesters.models.ContactData;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BuilderExample {

    @Test
    public void demonstrateBuilderUsage() {
        // 1. Temel Builder Kullanımı
        ContactData contact1 = ContactData.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();

        // 2. Default değerleri ile Builder Kullanımı
        ContactData contact2 = ContactData.builder()
                .firstName("Jane")
                .email("jane@example.com")
                .build();
        
        assertThat(contact2.getCountry()).isEqualTo("Turkey"); // Default değer
        assertThat(contact2.getPhone()).startsWith("+90"); // Default değer

        // 3. Custom builder metodu kullanımı
        ContactData contact3 = ContactData.builder()
                .fullName("Alice Smith")
                .email("alice@example.com")
                .build();

        assertThat(contact3.getFirstName()).isEqualTo("Alice");
        assertThat(contact3.getLastName()).isEqualTo("Smith");

        // 4. Tüm alanları doldurma
        ContactData contact4 = ContactData.builder()
                .firstName("Bob")
                .lastName("Wilson")
                .email("bob@example.com")
                .phone("+90 555 123 4567")
                .address("123 Main St")
                .city("Istanbul")
                .state("Marmara")
                .postalCode("34000")
                .country("Turkey")
                .build();

        // 5. Builder'ı bir değişkende saklama ve tekrar kullanma
        ContactData.ContactDataBuilder templateBuilder = ContactData.builder()
                .city("Istanbul")
                .country("Turkey")
                .state("Marmara");

        // Template'i kullanarak farklı kişiler oluşturma
        ContactData contact5 = templateBuilder
                .firstName("Mehmet")
                .lastName("Yılmaz")
                .email("mehmet@example.com")
                .build();

        ContactData contact6 = templateBuilder
                .firstName("Ayşe")
                .lastName("Demir")
                .email("ayse@example.com")
                .build();
    }

    @Test
    public void demonstrateBuilderInTestData() {
        // Test senaryoları için veri hazırlama
        ContactData validContact = ContactData.builder()
                .firstName("Test")
                .lastName("User")
                .email("test@example.com")
                .phone("+90 555 123 4567")
                .build();

        ContactData invalidContact = ContactData.builder()
                .firstName("")  // Boş isim
                .lastName("User")
                .email("invalid-email")  // Geçersiz email
                .build();

        // Test verilerini kolayca oluşturma
        ContactData[] testContacts = {
            ContactData.builder()
                .firstName("User1")
                .email("user1@example.com")
                .build(),
            ContactData.builder()
                .firstName("User2")
                .email("user2@example.com")
                .build(),
            ContactData.builder()
                .firstName("User3")
                .email("user3@example.com")
                .build()
        };
    }
}
