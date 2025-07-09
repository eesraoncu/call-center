@echo off
echo ========================================
echo Location API Test Script
echo ========================================
echo.

echo 1. Şehirleri oluşturuyorum...
echo.

echo İstanbul şehri oluşturuluyor...
curl -X POST http://localhost:8080/api/locations/cities -H "Content-Type: application/json" -d "{\"cityName\": \"İstanbul\"}"
echo.
echo.

echo Ankara şehri oluşturuluyor...
curl -X POST http://localhost:8080/api/locations/cities -H "Content-Type: application/json" -d "{\"cityName\": \"Ankara\"}"
echo.
echo.

echo 2. İlçeleri oluşturuyorum...
echo.

echo Kadıköy ilçesi oluşturuluyor...
curl -X POST http://localhost:8080/api/locations/districts -H "Content-Type: application/json" -d "{\"cityId\": 1, \"districtName\": \"Kadıköy\"}"
echo.
echo.

echo Beşiktaş ilçesi oluşturuluyor...
curl -X POST http://localhost:8080/api/locations/districts -H "Content-Type: application/json" -d "{\"cityId\": 1, \"districtName\": \"Beşiktaş\"}"
echo.
echo.

echo Çankaya ilçesi oluşturuluyor...
curl -X POST http://localhost:8080/api/locations/districts -H "Content-Type: application/json" -d "{\"cityId\": 2, \"districtName\": \"Çankaya\"}"
echo.
echo.

echo 3. Beldeleri/Köyleri oluşturuyorum...
echo.

echo Fenerbahçe beldesi oluşturuluyor...
curl -X POST http://localhost:8080/api/locations/townships -H "Content-Type: application/json" -d "{\"districtId\": 1, \"districtTownshipTownName\": \"Fenerbahçe\"}"
echo.
echo.

echo Göztepe beldesi oluşturuluyor...
curl -X POST http://localhost:8080/api/locations/townships -H "Content-Type: application/json" -d "{\"districtId\": 1, \"districtTownshipTownName\": \"Göztepe\"}"
echo.
echo.

echo Levent beldesi oluşturuluyor...
curl -X POST http://localhost:8080/api/locations/townships -H "Content-Type: application/json" -d "{\"districtId\": 2, \"districtTownshipTownName\": \"Levent\"}"
echo.
echo.

echo 4. Mahalleleri oluşturuyorum...
echo.

echo Fenerbahçe Mahallesi oluşturuluyor...
curl -X POST http://localhost:8080/api/locations/neighbourhoods -H "Content-Type: application/json" -d "{\"districtTownshipTownId\": 1, \"neighbourhoodName\": \"Fenerbahçe Mahallesi\", \"neighbourhoodExplanation\": \"Fenerbahçe semtinin ana mahallesi\"}"
echo.
echo.

echo Kalamış Mahallesi oluşturuluyor...
curl -X POST http://localhost:8080/api/locations/neighbourhoods -H "Content-Type: application/json" -d "{\"districtTownshipTownId\": 1, \"neighbourhoodName\": \"Kalamış Mahallesi\", \"neighbourhoodExplanation\": \"Fenerbahçe semtinin Kalamış mahallesi\"}"
echo.
echo.

echo Göztepe Mahallesi oluşturuluyor...
curl -X POST http://localhost:8080/api/locations/neighbourhoods -H "Content-Type: application/json" -d "{\"districtTownshipTownId\": 2, \"neighbourhoodName\": \"Göztepe Mahallesi\", \"neighbourhoodExplanation\": \"Göztepe semtinin ana mahallesi\"}"
echo.
echo.

echo 5. Listeleme testleri...
echo.

echo Tüm şehirler listeleniyor...
curl -X GET http://localhost:8080/api/locations/cities
echo.
echo.

echo İstanbul'un ilçeleri listeleniyor...
curl -X GET http://localhost:8080/api/locations/cities/1/districts
echo.
echo.

echo Kadıköy'ün beldeleri listeleniyor...
curl -X GET http://localhost:8080/api/locations/districts/1/townships
echo.
echo.

echo Fenerbahçe'nin mahalleleri listeleniyor...
curl -X GET http://localhost:8080/api/locations/townships/1/neighbourhoods
echo.
echo.

echo ========================================
echo Test tamamlandı!
echo ========================================
pause 