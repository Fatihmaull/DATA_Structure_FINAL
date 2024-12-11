import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.time.LocalDate;

public class BioskopApp {
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    private HashMap<String, User> pengguna = new HashMap<>();
    private HashMap<String, Pemesanan> riwayatPemesanan = new HashMap<>();
    private HashMap<String, Film> daftarFilm = new HashMap<>();
    private int totalPendapatan = 0;
    private int noIdPengguna = 0;
    private int noPemesanan = 0;
    private User currentUser = null;

    // Konstruktor
    public BioskopApp() {
        daftarFilm.put("F1",
                new Film("Moana 2", "Adventure, Fantasy, Animation, Family, Comedy, Musical", 100, "SU", "1"));
        daftarFilm.put("F2", new Film("Bila Esok Ibu Tiada", "Drama, Family", 104, "13+", "2"));
        daftarFilm.put("F3", new Film("Petak Umpet", "Horror", 86, "13+", "3"));
        daftarFilm.put("F4", new Film("Cinta dalam Ikhlas", "Drama, Romance", 109, "13+", "4"));
        daftarFilm.put("F5", new Film("Devils Stay", "Horror", 95, "17+", "5"));

        frame = new JFrame("Aplikasi Bioskop");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Panel-panel utama
        mainPanel.add(createMenuPanel(), "Menu Utama");
        mainPanel.add(createDaftarPanel(), "Daftar Akun");
        mainPanel.add(createLoginPanel(), "Login Pengguna");
        mainPanel.add(createLoginAdminPanel(), "Login Admin");
        mainPanel.add(createAdminPanel(), "Admin");
        mainPanel.add(createUserMenuPanel(), "Menu Pengguna");

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    // Panel Menu
    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        JLabel title = new JLabel("MENU APLIKASI BIOSKOP", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        JButton btnDaftar = new JButton("DAFTAR AKUN PENGGUNA");
        JButton btnLogin = new JButton("MASUK SEBAGAI PENGGUNA");
        JButton btnAdmin = new JButton("MASUK SEBAGAI ADMIN");
        JButton btnKeluar = new JButton("KELUAR PROGRAM");

        btnDaftar.addActionListener(e -> cardLayout.show(mainPanel, "Daftar Akun"));
        btnLogin.addActionListener(e -> cardLayout.show(mainPanel, "Login Pengguna"));
        btnAdmin.addActionListener(e -> cardLayout.show(mainPanel, "Login Admin"));
        btnKeluar.addActionListener(e -> System.exit(0));

        panel.add(title);
        panel.add(btnDaftar);
        panel.add(btnLogin);
        panel.add(btnAdmin);
        panel.add(btnKeluar);
        return panel;
    }

    // Panel Daftar Akun
    private JPanel createDaftarPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JLabel title = new JLabel("SILAHKAN DAFTAR SEBAGAI PENGGUNA", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        JTextField tfNama = new JTextField();
        JTextField tfEmail = new JTextField();
        JTextField tfNoHp = new JTextField();
        JPasswordField pfPassword = new JPasswordField();
        JButton btnDaftar = new JButton("DAFTAR");
        JButton btnBack = new JButton("KEMBALI");

        formPanel.add(new JLabel("NAMA: "));
        formPanel.add(tfNama);
        formPanel.add(new JLabel("EMAIL: "));
        formPanel.add(tfEmail);
        formPanel.add(new JLabel("NO. HP: "));
        formPanel.add(tfNoHp);
        formPanel.add(new JLabel("PASSWORD: "));
        formPanel.add(pfPassword);
        formPanel.add(btnDaftar);
        formPanel.add(btnBack);

        panel.add(formPanel, BorderLayout.CENTER);

        tfNama.addActionListener(e -> tfEmail.requestFocus());
        tfEmail.addActionListener(e -> tfNoHp.requestFocus());
        tfNoHp.addActionListener(e -> pfPassword.requestFocus());
        pfPassword.addActionListener(e -> btnDaftar.doClick());

        btnDaftar.addActionListener(e -> {
            String nama = tfNama.getText();
            String email = tfEmail.getText();
            String noHp = tfNoHp.getText();
            String password = new String(pfPassword.getPassword());

            if (!nama.isEmpty() && !email.isEmpty() && !noHp.isEmpty() && !password.isEmpty()) {
                noIdPengguna++;
                String idPengguna = "U" + noIdPengguna;
                pengguna.put(idPengguna, new User(idPengguna, nama, email, noHp, password));
                JOptionPane.showMessageDialog(frame, "Akun berhasil dibuat!");
                cardLayout.show(mainPanel, "Menu Utama");
            } else {
                JOptionPane.showMessageDialog(frame, "Semua harus diisi!");
            }

            tfNama.setText("");
            tfEmail.setText("");
            tfNoHp.setText("");
            pfPassword.setText("");
        });

        btnBack.addActionListener(e -> cardLayout.show(mainPanel, "Menu Utama"));
        return panel;
    }

    // Panel Login Pengguna
    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JLabel title = new JLabel("MASUK SEBAGAI PENGGUNA", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        JTextField tfEmail = new JTextField();
        JPasswordField pfPassword = new JPasswordField();
        JButton btnLogin = new JButton("LOGIN");
        JButton btnBack = new JButton("KEMBALI");

        formPanel.add(new JLabel("Email: "));
        formPanel.add(tfEmail);
        formPanel.add(new JLabel("Password: "));
        formPanel.add(pfPassword);
        formPanel.add(btnLogin);
        formPanel.add(btnBack);

        panel.add(formPanel, BorderLayout.CENTER);

        tfEmail.addActionListener(e -> pfPassword.requestFocus());
        pfPassword.addActionListener(e -> btnLogin.doClick());

        int[] loginAttempts = { 0 };

        btnLogin.addActionListener(e -> {
            String email = tfEmail.getText();
            String password = new String(pfPassword.getPassword());

            for (User user : pengguna.values()) {
                if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                    JOptionPane.showMessageDialog(frame, "Login berhasil!");
                    currentUser = user;

                    tfEmail.setText("");
                    pfPassword.setText("");

                    cardLayout.show(mainPanel, "Menu Pengguna");
                    return;
                }
            }
            loginAttempts[0]++;
            if (loginAttempts[0] >= 3) {
                JOptionPane.showMessageDialog(frame, "Terlalu banyak percobaan gagal. Kembali ke Menu Utama.");
                cardLayout.show(mainPanel, "Menu Utama");
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Email atau password salah! Percobaan gagal: " + loginAttempts[0]);
            }
        });

        btnBack.addActionListener(e -> cardLayout.show(mainPanel, "Menu Utama"));
        return panel;
    }

    // Panel Menu Pengguna
    private JPanel createUserMenuPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JLabel title = new JLabel("MENU PENGGUNA", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        JButton btnLihatFilm = new JButton("Tampilkan Daftar Film");
        JButton btnPesanTiket = new JButton("Pesan Tiket");
        JButton btnRiwayat = new JButton("Lihat Riwayat Pemesanan");
        JButton btnBack = new JButton("Kembali");

        btnLihatFilm.addActionListener(e -> {
            JPanel filmPanel = new JPanel(new GridLayout(0, 1, 10, 10));

            for (String key : daftarFilm.keySet()) {
                Film film = daftarFilm.get(key);

                JPanel panelFilm = new JPanel(new BorderLayout(10, 10));
                JLabel lblKey = new JLabel("Kode Film: " + key);
                lblKey.setFont(new Font("Arial", Font.BOLD, 14));
                JLabel lblDetails = new JLabel("<html>" +
                        "Judul: " + film.getJudul() + "<br>" +
                        "Genre: " + film.getGenre() + "<br>" +
                        "Durasi: " + film.getDurasi() + " menit<br>" +
                        "Rating Usia: " + film.getRatingUsia() + "<br>" +
                        "Studio: " + film.getStudio() + "<br>" +
                        "Harga: " + "Rp 25000" + "</html>");

                panelFilm.add(lblKey, BorderLayout.NORTH);
                panelFilm.add(lblDetails, BorderLayout.CENTER);
                panelFilm.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

                filmPanel.add(panelFilm);
            }

            JScrollPane scrollPane = new JScrollPane(filmPanel);
            scrollPane.setPreferredSize(new Dimension(400, 300));
            JOptionPane.showMessageDialog(frame, scrollPane, "Daftar Film", JOptionPane.PLAIN_MESSAGE);
        });

        btnPesanTiket.addActionListener(e -> {
            String kodeFilm = JOptionPane.showInputDialog("Masukkan Kode Film:");

            if (!daftarFilm.containsKey(kodeFilm)) {
                JOptionPane.showMessageDialog(frame, "Kode film tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int jumlahTiket;
            try {
                jumlahTiket = Integer.parseInt(JOptionPane.showInputDialog("Jumlah Tiket:"));
                if (jumlahTiket <= 0)
                    throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Jumlah tiket harus angka positif!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int hargaTiket = 25000;
            int totalHarga = jumlahTiket * hargaTiket;

            Film film = daftarFilm.get(kodeFilm);
            String judulFilm = film.getJudul();

            noPemesanan++;
            String kodePemesanan = "P" + noPemesanan;

            riwayatPemesanan.put(kodePemesanan,
                    new Pemesanan(kodePemesanan, currentUser.getIdPengguna(), currentUser.getNama(), judulFilm,
                            jumlahTiket, totalHarga,
                            LocalDate.now()));

            JOptionPane.showMessageDialog(frame, "Tiket berhasil dipesan!\nKode Pesanan: " + kodePemesanan);
        });

        btnRiwayat.addActionListener(e -> {
            if (currentUser == null) {
                JOptionPane.showMessageDialog(frame, "Anda belum login!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JPanel riwayatPanel = new JPanel(new GridLayout(0, 1, 10, 10));
            for (Pemesanan pemesanan : riwayatPemesanan.values()) {
                if (pemesanan.getIdPengguna().equals(currentUser.getIdPengguna())) {
                    JTextArea textArea = new JTextArea(pemesanan.toString());
                    textArea.setEditable(false);
                    textArea.setBorder(BorderFactory.createTitledBorder("Detail Pemesanan"));
                    riwayatPanel.add(textArea);
                }
            }

            if (riwayatPanel.getComponentCount() == 0) {
                JOptionPane.showMessageDialog(frame, "Belum ada pemesanan!", "Riwayat Pemesanan",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JScrollPane scrollPane = new JScrollPane(riwayatPanel);
                JOptionPane.showMessageDialog(frame, scrollPane, "Riwayat Pemesanan", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnBack.addActionListener(e -> cardLayout.show(mainPanel, "Menu Utama"));

        formPanel.add(btnLihatFilm);
        formPanel.add(btnPesanTiket);
        formPanel.add(btnRiwayat);
        formPanel.add(btnBack);
        panel.add(formPanel, BorderLayout.CENTER);
        return panel;
    }

    // Panel Login Admin
    private JPanel createLoginAdminPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JLabel title = new JLabel("MASUK SEBAGAI ADMIN", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        JTextField tfUsername = new JTextField();
        JPasswordField pfPassword = new JPasswordField();
        JButton btnLogin = new JButton("LOGIN");
        JButton btnBack = new JButton("KEMBALI");

        formPanel.add(new JLabel("Username: "));
        formPanel.add(tfUsername);
        formPanel.add(new JLabel("Password: "));
        formPanel.add(pfPassword);
        formPanel.add(btnLogin);
        formPanel.add(btnBack);

        panel.add(formPanel, BorderLayout.CENTER);

        tfUsername.addActionListener(e -> pfPassword.requestFocus());
        pfPassword.addActionListener(e -> btnLogin.doClick());

        int[] loginAttempts = { 0 };

        btnLogin.addActionListener(e -> {
            String username = tfUsername.getText();
            String password = new String(pfPassword.getPassword());

            if (username.equals("admin") && password.equals("12345")) {
                JOptionPane.showMessageDialog(frame, "Login berhasil!");
                tfUsername.setText("");
                pfPassword.setText("");
                cardLayout.show(mainPanel, "Admin");
                return;
            }
            loginAttempts[0]++;
            if (loginAttempts[0] >= 3) {
                JOptionPane.showMessageDialog(frame, "Terlalu banyak percobaan gagal. Kembali ke Menu Utama.");
                cardLayout.show(mainPanel, "Menu Utama");
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Email atau password salah! Percobaan gagal: " + loginAttempts[0]);
            }
        });

        btnBack.addActionListener(e -> cardLayout.show(mainPanel, "Menu Utama"));
        return panel;
    }

    // Panel Admin
    private JPanel createAdminPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JLabel title = new JLabel("MENU ADMIN", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        JButton btnDaftarPengguna = new JButton("Lihat Daftar Pengguna");
        JButton btnRiwayat = new JButton("Lihat Riwayat Pemesanan");
        JButton btnPendapatan = new JButton("Lihat Pendapatan");
        JButton btnBack = new JButton("Kembali");

        btnDaftarPengguna.addActionListener(e -> {
            JPanel penggunaPanel = new JPanel(new GridLayout(0, 1, 10, 10));
            for (User user : pengguna.values()) {
                JTextArea textArea = new JTextArea(
                        "Id Pengguna: " + user.getIdPengguna() +
                                "\nPengguna: " + user.getNama() +
                                "\nEmail: " + user.getEmail() +
                                "\nNo. HP: " + user.getNoHp() +
                                "\nPassword: " + user.getPassword());
                textArea.setEditable(false);
                textArea.setBorder(BorderFactory.createTitledBorder("Detail Pengguna"));
                penggunaPanel.add(textArea);
            }
            JScrollPane scrollPane = new JScrollPane(penggunaPanel);
            JOptionPane.showMessageDialog(frame, scrollPane, "Daftar Pengguna", JOptionPane.INFORMATION_MESSAGE);
        });

        btnRiwayat.addActionListener(e -> {
            JPanel riwayatPanel = new JPanel(new GridLayout(riwayatPemesanan.size(), 1, 10, 10));

            if (riwayatPemesanan.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Belum ada pemesanan!", "Riwayat Pemesanan",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            for (Pemesanan pemesanan : riwayatPemesanan.values()) {
                String idPengguna = pemesanan.getIdPengguna();
                if (pemesanan.getIdPengguna().equals(idPengguna)) {
                    JTextArea textArea = new JTextArea(pemesanan.toString());
                    textArea.setEditable(false);
                    textArea.setBorder(BorderFactory.createTitledBorder("Detail Pemesanan"));
                    riwayatPanel.add(textArea);
                }
            }

            JScrollPane scrollPane = new JScrollPane(riwayatPanel);
            JOptionPane.showMessageDialog(frame, scrollPane, "Riwayat Pemesanan", JOptionPane.INFORMATION_MESSAGE);
        });

        btnPendapatan.addActionListener(e -> {
            for (Pemesanan pemesanan : riwayatPemesanan.values()) {
                totalPendapatan += pemesanan.getHarga();
            }
            JOptionPane.showMessageDialog(frame, "Total Pendapatan: Rp " + totalPendapatan,
                    "Pendapatan", JOptionPane.INFORMATION_MESSAGE);
        });

        btnBack.addActionListener(e -> cardLayout.show(mainPanel, "Menu Utama"));

        formPanel.add(btnDaftarPengguna);
        formPanel.add(btnRiwayat);
        formPanel.add(btnPendapatan);
        formPanel.add(btnBack);
        panel.add(formPanel, BorderLayout.CENTER);
        return panel;
    }

    public static void main(String[] args) {
        new BioskopApp();
    }
}

class User {
    private String idPengguna, nama, email, noHp, password;

    public User(String idPengguna, String nama, String email, String noHp, String password) {
        this.idPengguna = idPengguna;
        this.nama = nama;
        this.email = email;
        this.noHp = noHp;
        this.password = password;
    }

    public String getIdPengguna() {
        return idPengguna;
    }

    public String getNama() {
        return nama;
    }

    public String getEmail() {
        return email;
    }

    public String getNoHp() {
        return noHp;
    }

    public String getPassword() {
        return password;
    }
}

class Pemesanan {
    private String kodePesanan;
    private String idPengguna;
    private String namaPengguna;
    private String judulFilm;
    private int jumlahTiket;
    private int harga;
    private LocalDate tanggal;

    public Pemesanan(String kodePesanan, String idPengguna, String namaPengguna, String judulFilm, int jumlahTiket,
            int harga,
            LocalDate tanggal) {
        this.kodePesanan = kodePesanan;
        this.idPengguna = idPengguna;
        this.namaPengguna = namaPengguna;
        this.judulFilm = judulFilm;
        this.jumlahTiket = jumlahTiket;
        this.harga = harga;
        this.tanggal = tanggal;
    }

    public String getIdPengguna() {
        return idPengguna;
    }

    public String getNamaPengguna() {
        return namaPengguna;
    }

    public int getHarga() {
        return harga;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }

    @Override
    public String toString() {
        return "Kode Pesanan: " + kodePesanan + "\n" +
                "Nama Pengguna: " + namaPengguna + "\n" +
                "Judul Film: " + judulFilm + "\n" +
                "Jumlah Tiket: " + jumlahTiket + "\n" +
                "Harga: " + harga + "\n" +
                "Tanggal: " + tanggal;
    }
}

class Film {
    private String judul;
    private String genre;
    private int durasi;
    private String ratingUsia;
    private String studio;

    public Film(String judul, String genre, int durasi, String ratingUsia, String studio) {
        this.judul = judul;
        this.genre = genre;
        this.durasi = durasi;
        this.ratingUsia = ratingUsia;
        this.studio = studio;
    }

    public String getJudul() {
        return judul;
    }

    public String getGenre() {
        return genre;
    }

    public int getDurasi() {
        return durasi;
    }

    public String getRatingUsia() {
        return ratingUsia;
    }

    public String getStudio() {
        return studio;
    }
}