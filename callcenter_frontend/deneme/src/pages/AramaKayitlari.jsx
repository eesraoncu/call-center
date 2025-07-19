// src/pages/AramaKayitlari.jsx

import React, { useEffect, useState } from 'react'; // React temel kütüphanesi, useEffect (yan etki yönetimi) ve useState (durum yönetimi) hook'ları içe aktarılır.
import { getAllCalls, getAllCustomers, getAllServices } from '../services/api'; // Uygulamanın API servislerinden çağrı, müşteri ve servis verilerini çeken fonksiyonlar içe aktarılır.
import './AramaKayitlari.css'; // Bu bileşene özel stil tanımlamalarını içeren CSS dosyası içe aktarılır.


export default function AramaKayitlari() {
  // --- State Tanımlamaları ---
  // Bileşenin farklı durumlarını (state) yönetmek için useState hook'ları kullanılır.

  const [calls, setCalls] = useState([]);         // API'den çekilen tüm çağrı kayıtlarını saklar. Başlangıçta boş bir dizidir.
  const [customers, setCustomers] = useState([]); // API'den çekilen müşteri listesini saklar.
  const [services, setServices] = useState([]);   // API'den çekilen servis tipleri listesini saklar.
  const [loading, setLoading] = useState(true);   // Veri yükleme durumunu belirtir (true: yükleniyor, false: yüklendi).

  // Filtreleme için kullanılan state'ler
  const [filterCustomerName, setFilterCustomerName] = useState(''); // Müşteri adına göre filtreleme inputunun değerini saklar.
  const [filterStartDate, setFilterStartDate] = useState('');       // Başlangıç tarihine göre filtreleme inputunun değerini saklar.
  const [filterEndDate, setFilterEndDate] = useState('');           // Bitiş tarihine göre filtreleme inputunun değerini saklar.
  const [filterServiceType, setFilterServiceType] = useState('');   // Servis tipine göre filtreleme select kutusunun değerini saklar.

  // --- useEffect Hook'u: Bileşen yüklendiğinde (mount edildiğinde) bir kez çalışır ---
  // API'den başlangıç verilerini (çağrılar, müşteriler, servisler) çekmek için kullanılır.
  useEffect(() => {
    async function fetchData() {
      try {
        setLoading(true); // Veri çekme işlemi başladığında yükleme durumunu true yaparız.

        // Promise.all kullanarak üç API isteğini paralel olarak göndeririz.
        // Bu, tüm veriler aynı anda gelene kadar beklememizi sağlar ve performansı artırır.
        const [callsData, customersData, servicesData] = await Promise.all([
          getAllCalls(),      // Çağrı kayıtlarını çeker.
          getAllCustomers(),  // Müşteri kayıtlarını çeker.
          getAllServices()    // Servis tipi kayıtlarını çeker.
        ]);

        // Gelen API yanıtlarını kontrol ederiz. Yanıt doğrudan bir dizi olabilir veya
        // bir obje içinde 'data' özelliği altında gelebilir. Güvenli bir şekilde state'lere atarız.
        setCalls(Array.isArray(callsData) ? callsData : callsData?.data || []);
        setCustomers(Array.isArray(customersData) ? customersData : customersData?.data || []);
        setServices(Array.isArray(servicesData) ? servicesData : servicesData?.data || []);
      } catch (error) {
        // API isteği sırasında bir hata oluşursa, hatayı konsola yazdırır ve state'leri boş diziye set ederiz.
        console.error("Veri çekilirken hata oluştu:", error);
        setCalls([]);
        setCustomers([]);
        setServices([]);
      } finally {
        setLoading(false); // Veri çekme işlemi tamamlandığında (başarılı veya hatalı), yükleme durumunu false yaparız.
      }
    }
    fetchData(); // fetchData fonksiyonunu çağırarak veri çekme işlemini başlatırız.
  }, []); // Bağımlılık dizisi boş olduğu için bu useEffect sadece bileşen ilk render edildiğinde bir kez çalışır.

  /**
   * Müşteri ID'sine göre customers listesinden ilgili müşteri adını ve soyadını bulan yardımcı fonksiyon.
   * API'den gelen müşteri objelerinin id veya customerId gibi farklı anahtarlarla gelebilme ihtimalini yönetir.
   * Eğer müşteri bulunamazsa 'Bilinmiyor' stringini döndürür.
   * @param {number} customerId - Aranacak müşteri ID'si.
   * @returns {string} Müşterinin tam adı (Ad Soyad) veya 'Bilinmiyor'.
   */
  const getCustomerName = (customerId) => {
    const customer = customers.find((c) => (c.id ?? c.customerId) === customerId);
    return customer
      ? `${customer.customerName ?? customer.name ?? ''} ${customer.customerSurname ?? ''}`.trim()
      : 'Bilinmiyor';
  };

  /**
   * Servis ID'sine göre services listesinden ilgili servis tipini bulan yardımcı fonksiyon.
   * ID'lerin veri tipinin farklı olabileceği durumları (number/string) ele almak için karşılaştırma öncesi string'e çevirir.
   * Eğer servis bulunamazsa '-' stringini döndürür.
   * @param {number} serviceId - Aranacak servis ID'si.
   * @returns {string} Servis tipi adı veya '-'.
   */
  const getServiceType = (serviceId) => {
    const service = services.find((s) => String(s.serviceId) === String(serviceId));
    return service ? service.serviceType : '-';
  };

  /**
   * Saniye cinsinden verilen çağrı süresini daha okunabilir bir format olan "X dakika Y saniye" stringine çevirir.
   * Eğer süre sıfır veya geçersizse '-' döndürür.
   * @param {number} seconds - Çağrı süresi (saniye cinsinden).
   * @returns {string} Formatlanmış süre (ör: "2 dakika 30 saniye") veya '-'.
   */
  const formatDuration = (seconds) => {
    if (seconds === undefined || seconds === null) return '-'; // Süre yoksa veya geçersizse '-' döndür.
    const m = Math.floor(seconds / 60); // Dakikayı hesapla.
    const s = seconds % 60;             // Kalan saniyeleri hesapla.
    if (m > 0) return `${m} dakika ${s} saniye`; // Dakika varsa formatla.
    return `${s} saniye`; // Sadece saniye varsa formatla.
  };

  // --- Filtrelenmiş Çağrı Kayıtları ---
  // calls state'i veya filtre state'lerinden herhangi biri değiştiğinde yeniden hesaplanır (computed value).
  const filteredCalls = calls.filter(call => {
    // Müşteri adı filtresi: filterCustomerName boşsa veya çağrıdaki müşteri adı filtre değerini içeriyorsa true döner.
    const customerName = getCustomerName(call.customerId).toLowerCase();
    const matchesCustomer = filterCustomerName === '' ||
      customerName.includes(filterCustomerName.toLowerCase());

    // Tarih filtresi: Çağrı tarihi, başlangıç ve bitiş tarihleri aralığındaysa true döner.
    const callDate = new Date(call.callDate);
    const start = filterStartDate ? new Date(filterStartDate) : null;
    const end = filterEndDate ? new Date(filterEndDate) : null;

    // Adjust end date to include the entire day
    const adjustedEndDate = end ? new Date(end.setHours(23, 59, 59, 999)) : null;

    const matchesDate = (!start || callDate >= start) && (!adjustedEndDate || callDate <= adjustedEndDate);

    // Servis tipi filtresi: filterServiceType boşsa veya çağrıdaki servis tipi filtre değerini içeriyorsa true döner.
    const serviceType = getServiceType(call.serviceId).toLowerCase();
    const matchesServiceType = filterServiceType === '' ||
      serviceType.includes(filterServiceType.toLowerCase());

    // Tüm filtre koşulları true ise, çağrı kaydı filtreye uygundur ve tabloya dahil edilir.
    return matchesCustomer && matchesDate && matchesServiceType;
  });

  // Filtreleme sonrası toplam çağrı kaydı sayısı.
  const totalCalls = filteredCalls.length;

  // --- Bileşenin Render Edilen JSX Yapısı ---
  return (
    <div className="arama-kayitlari-container">
      {/* Üst Bilgi ve Aksiyon Butonlarını içeren bölüm */}
      <div className="header-section">
        <h1 className="header">Arama Kayıtları</h1> {/* Sayfa başlığı */}

        <div className="action-buttons-container">
          {/* Filtreleme Uygula Butonu (Şu an sadece bir alert gösteriyor) */}
          <button
            className="action-button primary"
            onClick={() => alert('Filtreleme uygulandı')} // Tıklandığında bildirim gösterir.
          >
            Filtrele
          </button>
          {/* Filtreleri Temizle Butonu */}
          <button
            className="action-button secondary"
            onClick={() => {
              // Tüm filtre state'lerini varsayılan (boş) değerlerine sıfırlar.
              setFilterCustomerName('');
              setFilterStartDate('');
              setFilterEndDate('');
              setFilterServiceType('');
            }}
          >
            Filtreleri Temizle
          </button>
        </div>
      </div>

      {/* Filtreleme Formu Alanı */}
      <div className="filter-section">
        <div className="filter-container">
          {/* Müşteri Adı Filtresi Inputu */}
          <div className="filter-group">
            <label htmlFor="customerName" className="filter-label">Müşteri Adı:</label>
            <input
              type="text"
              id="customerName"
              className="filter-input"
              value={filterCustomerName} // Input değeri filterCustomerName state'ine bağlıdır.
              onChange={(e) => setFilterCustomerName(e.target.value)} // Input değiştikçe state'i günceller.
              placeholder="Müşteri adı ile ara"
            />
          </div>

          {/* Başlangıç Tarihi Filtresi Inputu */}
          <div className="filter-group">
            <label htmlFor="startDate" className="filter-label">Başlangıç Tarihi:</label>
            <input
              type="date"
              id="startDate"
              className="filter-input"
              value={filterStartDate}
              onChange={(e) => setFilterStartDate(e.target.value)}
            />
          </div>

          {/* Bitiş Tarihi Filtresi Inputu */}
          <div className="filter-group">
            <label htmlFor="endDate" className="filter-label">Bitiş Tarihi:</label>
            <input
              type="date"
              id="endDate"
              className="filter-input"
              value={filterEndDate}
              onChange={(e) => setFilterEndDate(e.target.value)}
            />
          </div>

          {/* Servis Tipi Filtresi Select Kutusu */}
          <div className="filter-group">
            <label htmlFor="serviceType" className="filter-label">Servis Tipi:</label>
            <select
              id="serviceType"
              className="filter-input select-input"
              value={filterServiceType}
              onChange={(e) => setFilterServiceType(e.target.value)}
            >
              <option value="">Tümü</option> {/* Tüm servis tiplerini göster seçeneği */}
              {/* services state'indeki her bir servis tipi için dinamik olarak <option> elemanı oluşturulur. */}
              {services.map(service => (
                <option key={service.serviceId} value={service.serviceType}>
                  {service.serviceType}
                </option>
              ))}
            </select>
          </div>
        </div>

        {/* Toplam Filtrelenen Kayıt Sayısı Bilgisi */}
        <p className="total-count">
          Toplam Filtrelenen Kayıt Sayısı:
          <span className="total-count-number"> {totalCalls}</span> {/* Dinamik olarak filtrelenmiş kayıt sayısını gösterir. */}
        </p>
      </div>

      {/* Ana İçerik Alanı - Çağrı Kayıtları Tablosu */}
      <div className="main-content-area">
        {loading ? (
          // loading durumu true ise "Yükleniyor..." mesajı gösterilir.
          <p className="no-records-cell">Yükleniyor...</p>
        ) : (
          // loading durumu false ise tablo gösterilir.
          <table className="calls-table">
            <thead>
              <tr className="table-header-row">
                <th className="table-header-cell">ID</th>
                <th className="table-header-cell">Müşteri</th>
                <th className="table-header-cell">Tarih</th>
                <th className="table-header-cell">Süre</th>
                <th className="table-header-cell">Servis Tipi</th>
                <th className="table-header-cell">Açıklama</th>
              </tr>
            </thead>
            <tbody>
              {filteredCalls.length === 0 ? (
                // Eğer filtrelenmiş çağrı kaydı yoksa "Hiç arama kaydı bulunamadı." mesajı gösterilir.
                <tr>
                  <td colSpan={6} className="no-records-cell">
                    Hiç arama kaydı bulunamadı.
                  </td>
                </tr>
              ) : (
                // filteredCalls dizisindeki her bir çağrı kaydı için tablo satırı (<tr>) oluşturulur.
                filteredCalls.map((call) => (
                  <tr key={call.callId} className="table-row">
                    {/* Her hücrede data-label özniteliği, mobil görünümde başlık olarak kullanılır. */}
                    <td className="table-cell" data-label="ID">{call.callId}</td>
                    <td className="table-cell" data-label="Müşteri">
                      {getCustomerName(call.customerId)} {/* Müşteri adını yardımcı fonksiyonla alırız. */}
                    </td>
                    <td className="table-cell" data-label="Tarih">{call.callDate}</td>
                    <td className="table-cell" data-label="Süre">
                      {formatDuration(call.callDuration ?? call.duration)} {/* Süreyi formatlayan yardımcı fonksiyonu kullanırız. */}
                    </td>
                    <td className="table-cell" data-label="Servis Tipi">
                      {getServiceType(call.serviceId)} {/* Servis tipini yardımcı fonksiyonla alırız. */}
                    </td>
                    <td className="table-cell" data-label="Açıklama">
                      {call.description || '-'} {/* Açıklama varsa gösterir, yoksa '-' koyar. */}
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
}