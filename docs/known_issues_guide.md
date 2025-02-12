# Known Issues (Bilinen Hatalar) Yönetimi Rehberi

## İçindekiler
1. [Genel Bakış](#genel-bakış)
2. [Tag Kullanım Örnekleri](#tag-kullanım-örnekleri)
3. [Avantajları](#avantajları)
4. [Özellikler](#özellikler)
5. [Best Practices](#best-practices)
6. [Örnek Senaryolar](#örnek-senaryolar)
7. [Raporlama](#raporlama)
8. [Sık Karşılaşılan Sorunlar](#sık-karşılaşılan-sorunlar)

## Genel Bakış

`@KnownIssue` tag'i, bilinen hataları yönetmek ve bu hatalardan etkilenen testleri geçici olarak atlamak için kullanılan bir çözümdür. Bu yaklaşım, test süitinin güvenilirliğini korurken, bilinen sorunların çözülmesini bekleyen testlerin yönetimini kolaylaştırır.

### Temel Prensipler
- Her bilinen hata dokümante edilmelidir
- Geçici çözümler kalıcı olmamalıdır
- Düzenli gözden geçirme yapılmalıdır
- Şeffaf raporlama sağlanmalıdır

## Tag Kullanım Örnekleri

### 1. Temel Kullanım
```gherkin
@KnownIssue
Scenario: Başarılı kayıt senaryosu
```

### 2. Bug Referansı ile Kullanım
```gherkin
@KnownIssue @bug=JIRA-123
Scenario: Başarılı kayıt senaryosu
```

### 3. Son Kullanma Tarihli Kullanım
```gherkin
@KnownIssue @bug=JIRA-123 @expires=2024-03-01
Scenario: Başarılı kayıt senaryosu
```

### 4. Ortam Spesifik Kullanım
```gherkin
@KnownIssue @bug=JIRA-123 @env=staging
Scenario: Başarılı kayıt senaryosu
```

### 5. Kombinasyon Kullanımı
```gherkin
@KnownIssue @bug=JIRA-123 @env=staging @expires=2024-03-01
Scenario: Başarılı kayıt senaryosu
```

### 6. Özel Durum Kullanımı
```gherkin
@KnownIssue @bug=JIRA-123 @critical @workaround="Manuel doğrulama gerekiyor"
Scenario: Kritik işlem senaryosu
```

## Avantajları

1. **İzlenebilirlik**
   - Bug ID'si ile sorunun kaynağını takip etme
   - Hangi testlerin neden atlandığını görme
   - Test raporlarında şeffaf görünüm
   - Metriklerde ayrı kategorilendirme

2. **Esneklik**
   - Ortama özel test atlama
   - Tarih bazlı otomatik devre dışı bırakma
   - Kolay bakım ve yönetim
   - Özel durumlar için genişletilebilirlik

3. **Raporlama**
   - "Skipped" olarak işaretlenen testler
   - Atlama nedenleri ile birlikte detaylı loglama
   - Test raporlarında özel bölüm
   - Trend analizi imkanı

4. **Geçici Çözüm**
   - Kod değişikliği gerektirmez
   - Tag'leri kaldırarak kolay geri alma
   - Minimum müdahale ile yönetim
   - Hızlı uygulama

## Özellikler

### 1. Ortam Kontrolü
- Test ortamı system property'den alınır
- Varsayılan: "local"
- Örnek kullanım: `-Dtest.environment=staging`
- Çoklu ortam desteği

### 2. Tarih Kontrolü
- `@expires` tag'i ile son kullanma tarihi
- Otomatik devre dışı bırakma
- Uyarı logları ile hatırlatma
- Tarih formatı: YYYY-MM-DD

### 3. Detaylı Loglama
- Bug ID
- Etkilenen ortam
- Son kullanma tarihi
- Atlama nedeni
- Stack trace (opsiyonel)
- Test metadata

## Best Practices

1. **Tag Kullanımı**
   - Her zaman bir bug ID ekleyin
   - Mümkünse son kullanma tarihi belirtin
   - Ortam spesifik durumları belirtin
   - Açıklayıcı workaround mesajları ekleyin

2. **Bakım**
   - Düzenli olarak expired tag'leri temizleyin
   - Bug'lar çözüldüğünde tag'leri kaldırın
   - Test raporlarını düzenli kontrol edin
   - Aylık gözden geçirme yapın

3. **Dokümantasyon**
   - Known issue'ları bir yerde listeleyin
   - Çözüm tarihlerini takip edin
   - Etkilenen test sayısını izleyin
   - Etki analizi yapın

## Örnek Senaryolar

### Senaryo 1: Geçici Altyapı Sorunu
```gherkin
@KnownIssue @bug=INFRA-456 @expires=2024-03-15 @env=staging
Feature: Ödeme İşlemleri
  Scenario: Başarılı kredi kartı ödemesi
```

### Senaryo 2: Bilinen UI Hatası
```gherkin
@KnownIssue @bug=UI-789 @workaround="Sayfayı yenileme gerekebilir"
Feature: Kullanıcı Profili
  Scenario: Profil fotoğrafı güncelleme
```

## Raporlama

### Allure Report Entegrasyonu
```java
@Step("Known Issue kontrolü: {bug}")
public void checkKnownIssue(String bug) {
    // Known issue logic
}
```

### JUnit Raporlama
```java
@Test
@DisplayName("Known Issue ile işaretlenmiş test")
@Tag("knownIssue")
void testWithKnownIssue() {
    // Test logic
}
```

## Sık Karşılaşılan Sorunlar

### 1. Tag Yönetimi
- **Sorun**: Eski tag'lerin sistemde kalması
- **Çözüm**: Otomatik temizleme script'i
- **Önlem**: Düzenli kontrol ve temizleme

### 2. Yanlış Kullanım
- **Sorun**: Gereksiz test atlama
- **Çözüm**: Code review sürecinde kontrol
- **Önlem**: Kullanım kılavuzu ve eğitim

### 3. Raporlama Sorunları
- **Sorun**: Eksik veya yanlış raporlama
- **Çözüm**: Raporlama template'lerinin güncellenmesi
- **Önlem**: Otomatik doğrulama kontrolleri
