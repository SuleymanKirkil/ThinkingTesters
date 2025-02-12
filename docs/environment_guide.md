# Test Ortamları (Environments) Rehberi

## Environment (Ortam) Nedir?

Environment (ortam), bir yazılımın çalıştığı farklı çalışma ortamlarını ifade eder. Her ortam, aynı uygulamanın farklı bir kopyasını barındırır, ancak her birinin kendine özgü yapılandırması ve amacı vardır.

## Temel Test Ortamları

### 1. Development (Geliştirme) Ortamı
- **Kimler Kullanır?** Yazılım geliştiriciler
- **Ne İçin Kullanılır?** 
  - Yeni özelliklerin geliştirilmesi
  - Hata düzeltmeleri
  - Kod değişikliklerinin test edilmesi
- **Özellikler:**
  - En kararsız ortam
  - Sürekli değişiklik yapılır
  - Gerçek veriler yerine test verileri kullanılır

### 2. QA (Quality Assurance) Ortamı
- **Kimler Kullanır?** Test ekibi
- **Ne İçin Kullanılır?**
  - Yeni özelliklerin test edilmesi
  - Hata tespiti
  - Otomatize testlerin çalıştırılması
- **Özellikler:**
  - Development ortamından daha kararlı
  - Test verileri kullanılır
  - Gerçek kullanıcı verisi bulunmaz
  - Performans kritik değildir

### 3. Staging (Ön Üretim) Ortamı
- **Kimler Kullanır?** Test ekibi ve iş birimleri
- **Ne İçin Kullanılır?**
  - Production öncesi son kontroller
  - Performans testleri
  - Kullanıcı kabul testleri
- **Özellikler:**
  - Production ortamının birebir kopyası
  - Gerçeğe yakın veriler kullanılır
  - Production'a benzer altyapı
  - Güvenlik ve performans testleri burada yapılır

### 4. Production (Üretim) Ortamı
- **Kimler Kullanır?** Son kullanıcılar
- **Ne İçin Kullanılır?** 
  - Gerçek kullanıcılara hizmet vermek
- **Özellikler:**
  - En kararlı ortam
  - Gerçek kullanıcı verileri
  - Maksimum güvenlik
  - Yüksek performans

## Neden Farklı Ortamlara İhtiyaç Duyarız?

1. **Risk Yönetimi:**
   - Yeni özellikler önce alt ortamlarda test edilir
   - Hatalar gerçek kullanıcıları etkilemeden bulunur
   - Güvenlik açıkları önceden tespit edilir

2. **Kalite Kontrolü:**
   - Her ortam bir kalite kontrol aşamasıdır
   - Hatalar aşama aşama tespit edilir
   - Test süreçleri sistematik şekilde yürütülür

3. **İzolasyon:**
   - Her ekip kendi ortamında çalışabilir
   - Bir ortamdaki sorun diğerlerini etkilemez
   - Farklı test senaryoları paralel yürütülebilir

## Framework'ümüzde Environment Kullanımı

### QA Ortamı İçin Örnek:
```bash
mvn test -Dtest.environment=qa
```
- Test verileri: test-data-qa.properties
- URL: https://qa.thinkingtesters.com
- Test kullanıcısı: qa_test@thinkingtesters.com

### Staging Ortamı İçin Örnek:
```bash
mvn test -Dtest.environment=staging
```
- Test verileri: test-data-staging.properties
- URL: https://staging.thinkingtesters.com
- Test kullanıcısı: staging_test@thinkingtesters.com

## Environment'a Göre Değişen Yapılandırmalar

1. **URL'ler:**
   - QA: https://qa.thinkingtesters.com
   - Staging: https://staging.thinkingtesters.com
   - Production: https://thinkingtesters.com

2. **Test Verileri:**
   - Her ortam için ayrı test verileri
   - Ortama özel kullanıcı bilgileri
   - Ortama özel API anahtarları

3. **Güvenlik Ayarları:**
   - QA'de daha gevşek güvenlik
   - Staging'de production benzeri güvenlik
   - Production'da maksimum güvenlik

## Best Practices (En İyi Uygulamalar)

1. **Ortam İzolasyonu:**
   - Her ortam birbirinden bağımsız olmalı
   - Bir ortamdaki değişiklik diğerlerini etkilememeli

2. **Veri Yönetimi:**
   - Production verisi test ortamlarında kullanılmamalı
   - Her ortam için özel test verileri oluşturulmalı
   - Hassas veriler şifrelenmeli

3. **Yapılandırma Yönetimi:**
   - Ortam değişkenleri kod dışında tutulmalı
   - Yapılandırmalar version control'de saklanmalı
   - Hassas bilgiler (şifreler, API anahtarları) güvenli şekilde saklanmalı

4. **Test Stratejisi:**
   - Smoke testler tüm ortamlarda çalıştırılmalı
   - Regresyon testleri QA ve Staging'de yapılmalı
   - Performance testleri Staging'de yapılmalı

## Örnek Senaryo

1. Geliştirici yeni bir özellik geliştirir
2. Feature branch'i QA ortamına deploy edilir
3. QA ortamında otomatize testler çalıştırılır
4. Hatalar bulunur ve düzeltilir
5. QA onayı alındıktan sonra Staging'e deploy edilir
6. Staging'de son kontroller yapılır
7. Production'a deploy edilir

## Sonuç

Farklı test ortamları kullanmak:
- Riskleri minimize eder
- Hataları erken tespit etmeyi sağlar
- Sistematik test süreçleri oluşturur
- Güvenli ve kaliteli yazılım geliştirmeyi destekler
