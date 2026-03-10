# Module-01-Coding-Standards

## REFLEKSI 1
Selama pengerjaan modul ini, saya belajar memahami arsitektur Spring Boot yang awalnya terasa rumit dengan banyaknya pembagian layer. Saya kini mengerti bahwa pemisahan antara Repository, Service, dan Controller bukan sekadar aturan kaku, melainkan pembagian tanggung jawab yang logis layaknya sebuah restoran—Controller melayani pelanggan, Service meracik logika, dan Repository mengelola bahan baku data. Hal ini mengajarkan saya prinsip clean code dan Single Responsibility Principle, di mana setiap kelas memiliki tugas spesifik sehingga kode lebih mudah dibaca dan maintainable.

Selain itu, saya juga merasakan manfaat besar dari penggunaan anotasi seperti @Autowired untuk dependency injection dan Lombok untuk mengurangi boilerplate code. Fitur-fitur ini membuat kodingan menjadi jauh lebih ringkas dan efisien tanpa mengurangi fungsinya. Saya juga belajar disiplin dalam alur kerja pengembangan software, terutama pentingnya melakukan git add, commit, dan push secara rutin untuk menghindari konflik.

## REFLEKSI 2
Tantangan terbesar yang saya hadapi dalam tugas ini adalah implementasi testing. Awalnya saya sempat bingung membedakan antara Unit Test dan Functional Test, namun sekarang saya paham bahwa Unit Test berfokus menguji logika internal komponen secara terisolasi (menggunakan Mocking), sedangkan Functional Test mensimulasikan interaksi pengguna nyata dari awal hingga akhir menggunakan Selenium. Saya menyadari bahwa tingginya code coverage itu penting, namun yang lebih penting adalah memastikan skenario tes benar-benar mencakup berbagai kemungkinan logika bisnis.

Proses debugging saat tes gagal juga menjadi pelajaran berharga bagi saya. Pesan error berwarna merah atau halaman "Whitelabel Error" yang awalnya menakutkan, ternyata seringkali disebabkan oleh hal-hal sepele seperti salah menaruh file HTML, lupa membuat Controller untuk halaman utama, atau kesalahan konfigurasi port. Pengalaman ini melatih saya untuk tidak panik saat menghadapi bug, melainkan menjadikannya sebagai petunjuk untuk memperbaiki kualitas software di kemudian hari.

# Module-02-CI-CD-DevOps

## Refleksi 1
Beberapa code quality issue yang saya perbaiki selama pengerjaan exercise ini antara lain adalah unused import, lalu adanya perbedaan versi di build.gradle.kts, dan juga error command. Selain itu saya juga cukup binguing untuk membuat ci scriptnya. Test coverage yang belum 100% pun menjadi tantangan di modul ini.

Strategi saya dalam memperbaikinya dimulai dengan mengidentifikasi letak kode yang bermasalah melalui log dari code scanner (SonarCloud/PMD) di GitHub Actions. Setelah itu, saya membaca penjelasan rule yang dilanggar untuk memahami mengapa hal tersebut dianggap sebagai bad practice (misalnya, JUnit 5 sudah tidak memerlukan modifier public untuk mengeksekusi tes). Terakhir, saya merefaktor kode tersebut, menghapus import/modifier yang tidak diperlukan, lalu melakukan commit dan push untuk memverifikasi bahwa issue tersebut sudah tidak muncul di run pipeline berikutnya.

## Refleksi 2
Menurut saya, implementasi workflow saat ini sudah sangat memenuhi definisi dari Continuous Integration (CI) dan Continuous Deployment (CD). Untuk aspek CI, pipeline sudah diatur agar secara otomatis melakukan build, menjalankan test suite, memeriksa code coverage, dan mengeksekusi static code analysis setiap kali ada push ke repositori, sehingga integrasi kode baru selalu terverifikasi kualitasnya. Sementara untuk aspek CD, saya menggunakan heroku (PaaS) dan menggunak automatic deployment yang dimana ketika saya push ke main maka code akan secara otomatis terupdate.

# Module-03-Maintainability-&-OO-Principles

## Refleksi 1
Yang aku terapkan dalam project ini adalah SRP, LSP, dan juga DIP. Ketiga SOLID principles tersebut yang belum terpenuhi dalam code saya. 
SRP disini saya implement dikarenakan pada code sebelumnya ProductController dan juga CarController digabung. Untuk memenuhi SRP, saya memisah kedua class tersebut. Hal ini membantu agar code lebih maintainability karena 1 class hanya memiliki 1 job. ProductController mengurus semua mapping yang berhubungan dengan product, CarController mengurus semua mapping yang berhubungan dengan Car. Jika tidak dipenuhi, jika sudah ada banyak controller, maka akan sangat susah untuk mencari Controller mana yang mengatur mapping mana. Line of codenya terlalu banyak.
LSP disini saya implement pada code yang sama yaitu ProductController dan juga CarController. Code sebelumnya adalah CarCOntroller meng extends/inherit dari ProductController. Ini tidak perlu karena mapping yang dibutuhkan oleh Car, tidak berasal dari Prodsuct. Jadi setelah memisahkannya dengan file yang berbeda, saya juga menghapus inheritance CarController.
DIP disini saya implement dikarenakan pada code sebelumnya, CarService menginisiasi Autowired CarService dengan CarServiceImpl. Disini melanggar DIP yang dimana modul tingkat tinggi bergantung dengan modul tingkat rendah. Disini untuk mengimplementasi DIP mudah, hanya inisiasi tetapi berbeda class yaitu langsung ke carService.

# Module-04-Refactoring-and-TDD

## REFLEKSI 1
Selama pengerjaan modul ini, saya dipaksa untuk mengubah pola pikir dari "menulis kode lalu mengujinya" menjadi "menulis tes sebelum fitur ada" melalui alur *Test-Driven Development* (TDD). Pendekatan siklus RED-GREEN-REFACTOR ini awalnya terasa memakan waktu, namun saya menyadari proses ini ibarat membangun fondasi dan memasang sensor alarm sebelum mendirikan dinding bangunan. Saat fungsionalitas aplikasi bertambah kompleks, sensor (tes) ini akan langsung menyala jika ada kode baru yang merusak logika lama, sehingga kode jauh lebih aman dari regresi.

Terkait prinsip F.I.R.S.T dalam pengujian, saya belajar merancang tes yang *Self-Validating* (menggunakan `assertEquals` atau `assertThrows`) dan *Timely* (ditulis tepat sebelum implementasi). Saya juga menyadari krusialnya aspek *Independent*. Dalam lapisan Service, saya mempraktikkan pengisolasian tes menggunakan *Mocking* (Mockito). Hal ini mengajarkan saya bahwa menguji setir kemudi mobil tidak perlu mensyaratkan mesin mobil menyala; kita cukup menyimulasikan respons *Repository* tanpa benar-benar memodifikasi *database* yang asli. Jika di masa depan pengujian saya menjadi lambat, saya tahu hal pertama yang harus diperiksa adalah apakah tes tersebut benar-benar independen atau masih bergantung pada sistem eksternal.

## REFLEKSI 2
Tahap *Refactoring* menyadarkan saya bahwa kode yang berhasil lulus tes (GREEN) belum tentu merupakan kode yang bersih dan profesional. Tantangan utama saya adalah mengenali dan memperbaiki *code smell*, khususnya *Primitive Obsession*, di mana saya awalnya menggunakan tipe data `String` mentah untuk merepresentasikan metode pembayaran (Voucher dan COD). Hal ini ibarat mesin tiket parkir yang membiarkan pengemudi mengetik sendiri nama kendaraannya; sangat rawan manipulasi, tidak valid, dan membutuhkan logika pengecekan *if-else* yang berlebihan di lapisan bawah.

Saya merefaktor hal tersebut dengan menerapkan `Enum` sebagai "tombol fisik" absolut yang membatasi opsi input sejak awal, sehingga celah anomali data tertutup. Selain itu, saya merapikan *Fat Constructor* pada entitas `Payment` dengan mengekstraksi logika validasinya menjadi *private methods* (*Extract Method*), serta mendelegasikan tanggung jawab sinkronisasi status ke `PaymentService` agar tidak melanggar *Single Responsibility Principle*. Pengalaman ini membuktikan bahwa merapikan "kabel internal" kode (refactoring) jauh lebih meyakinkan ketika kita sudah memiliki fondasi *Unit Test* yang kokoh; selama tes tetap berstatus hijau, saya yakin arsitektur yang saya ubah tidak merusak fungsionalitas aslinya.
