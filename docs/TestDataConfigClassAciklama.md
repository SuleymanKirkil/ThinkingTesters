# TestDataConfig Sınıfı Detaylı Açıklama

## 1. Sınıf Değişkenleri

```java
private static final Logger logger = LogManager.getLogger(TestDataConfig.class);
private static final Properties props = new Properties();
private static final String currentEnv = System.getProperty("test.environment", "qa");
```

### Değişkenlerin Açıklamaları:

- **logger**: 
  - Log kayıtları için kullanılan nesne
  - Hata ve bilgi mesajlarını kaydetmek için kullanılır

- **props**: 
  - Test verilerini tutacak Properties nesnesi
  - Key-value pairs şeklinde test verilerini saklar

- **currentEnv**: 
  - Hangi ortamda çalışıldığını belirten değişken
  - `System.getProperty("test.environment", "qa")` ile değer alır:
    - Eğer `-Dtest.environment=staging` gibi bir parametre verilmişse o değeri kullanır
    - Parametre verilmemişse varsayılan olarak "qa" değerini kullanır

## 2. Static Block (Statik Başlatıcı)

```java
static {
    try (InputStream input = TestDataConfig.class.getClassLoader()
            .getResourceAsStream("test-data.properties")) {
        if (input == null) {
            logger.error("Unable to find test-data.properties");
            throw new RuntimeException("test-data.properties not found in classpath");
        }
        props.load(input);
    } catch (IOException ex) {
        logger.error("Error loading test data properties", ex);
        throw new RuntimeException("Could not load test data properties", ex);
    }
}
```

### Static Block'un Görevleri:

1. Sınıf yüklendiğinde otomatik olarak çalışır
2. `test-data.properties` dosyasını classpath'den okur
3. Eğer dosya bulunamazsa veya okuma hatası olursa:
   - Hata mesajını loglar
   - RuntimeException fırlatır
4. Başarılı okuma durumunda verileri `props` nesnesine yükler

## 3. Test Kullanıcı Bilgilerini Alma Metodları

```java
public static String getTestUserEmail() {
    return props.getProperty(currentEnv + ".test.user.email");
}

public static String getTestUserPassword() {
    return props.getProperty(currentEnv + ".test.user.password");
}
```

### Metodların İşlevleri:

- Ortama özgü kullanıcı bilgilerini döndürür
- Örnek kullanımlar:
  - QA ortamında: 
    - `qa.test.user.email` değerini döndürür
    - `qa.test.user.password` değerini döndürür
  - Staging ortamında:
    - `staging.test.user.email` değerini döndürür
    - `staging.test.user.password` değerini döndürür

## 4. Geçersiz Test Verilerini Alma Metodları

```java
public static String getInvalidEmail() {
    return props.getProperty("test.invalid.email");
}

public static String getInvalidPassword() {
    return props.getProperty("test.invalid.password");
}
```

### Metodların İşlevleri:

- Negatif test senaryoları için geçersiz verileri döndürür
- Bu veriler ortamdan bağımsızdır (her ortamda aynı değerler kullanılır)

## 5. Örnek Kullanım

```java
// QA ortamında çalışırken:
String email = TestDataConfig.getTestUserEmail(); // qa.test.user.email değerini döndürür
String password = TestDataConfig.getTestUserPassword(); // qa.test.user.password değerini döndürür

// Staging ortamında çalışırken (-Dtest.environment=staging):
String email = TestDataConfig.getTestUserEmail(); // staging.test.user.email değerini döndürür
String password = TestDataConfig.getTestUserPassword(); // staging.test.user.password değerini döndürür

// Geçersiz veriler (ortamdan bağımsız):
String invalidEmail = TestDataConfig.getInvalidEmail(); // test.invalid.email değerini döndürür
String invalidPassword = TestDataConfig.getInvalidPassword(); // test.invalid.password değerini döndürür
```

## 6. Bu Yapının Sağladığı Avantajlar

1. **Merkezi Veri Yönetimi**:
   - Tüm test verileri tek bir properties dosyasında toplanır
   - Veri güncellemeleri tek bir noktadan yapılır

2. **Ortam Bazlı Veri Yönetimi**:
   - Her ortam için farklı test verileri tanımlanabilir
   - Ortam değişikliği kod değişikliği gerektirmez

3. **Kod Kalitesi**:
   - Kod tekrarı önlenir
   - Bakımı kolaydır
   - Test verileri kod içinde hardcode edilmez

4. **Güvenlik**:
   - Test verileri properties dosyasında saklanır
   - Hassas veriler (şifreler vb.) kod içinde görünmez