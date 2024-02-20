# Projeye Dair Detaylar

PSQL Bash’te
Create database cart_api;
DDL sorgusunu çalıştırarak uygulamayı başlatabilirsiniz

Postman’e alternatif olarak http://localhost:8080/swagger-ui/index.html#/ URL’inden uygulamaya erişebilirsiniz.

- Stok, ürünü sepetinize eklediğiniz anda rezerve edilir.
- Stok ve totalPrice her adımda güncellenir.
- updateCart metodu otomatik olarak çalışır. (Id değişimi @OneToOne olduğundan ve totalPrice gibi değiştirilmesi tehlikeli bilgiler taşıdığından endPointi bulunmuyor. Bunun yerine tamamen /otomatik olarak çalışır.)
- Orderlar loglanır.
- Price’ı 0 olan ürün eklenebilir (sitedeki etkinliği artırmak için yapılan çekilişler vs.) ama stock 0 olarak eklenemez.
- Eğer bir ürün güncel olarak bir müşterinin sepetindeyse silinemez.

## Senaryo:

Sepetinizde bulunan üründen iki tane daha eklediğinizi varsayalım. Sepette bulunan ürünün Quantity miktarı otomatik olarak artacak. Yeni bir ürün eklendi olarak gözükmeyecek.

## Senaryo 2 :

Sepetinize stocktan fazla ürün eklemeye çalışırsanız success: false dönen ve message içeriği Not enough stocks olan bir ResponseEntity sizi karşılıyor. Status HTTP code bilgisiyle beraber.

## Senaryo 3:

Sepetinizde bulunan miktardan fazla ürün çıkarmaya çalıştığınızda yine hata alırsınız.

## Senaryo 4:

Sepetinizde tam bulunan miktarda ürün çıkartırsanız quantity 0 olur ve ürün tamamen sepetinzden temizlenir. Stock yerine konur.
