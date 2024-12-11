import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AplikasiBelanja extends JFrame {
    private JPanel mainPanel;
    private JPanel loginPanel;
    private JPanel daftarPanel;
    private JPanel belanjaPanel;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton daftarButton;

    private JButton hapusButton;
    private JComboBox<String> urutComboBox;
    private JButton urutButton;
    private JButton logoutButton;

    private JTextField newUsernameField;
    private JPasswordField newPasswordField;
    private JButton buatAkunButton;
    private JButton kembaliButton;

    private List<Barang> daftarBarang;
    private DefaultListModel<String> listModelBarang;
    private DefaultListModel<String> listModelKeranjang;
    private List<Barang> keranjang = new ArrayList<>();
    private JLabel totalLabel;

    private List<Akun> daftarAkun = new ArrayList<>();
    private int nomorBarang = 1;

    public AplikasiBelanja() {
        setTitle("Aplikasi Belanja");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel(new CardLayout());
        loginPanel = new JPanel();
        daftarPanel = new JPanel();
        belanjaPanel = new JPanel();

        setupLoginPanel();
        setupDaftarPanel();
        setupBelanjaPanel();

        mainPanel.add(loginPanel, "Login");
        mainPanel.add(daftarPanel, "Daftar");
        mainPanel.add(belanjaPanel, "Belanja");

        add(mainPanel);

        ((CardLayout) mainPanel.getLayout()).show(mainPanel, "Login");
    }

    private void setupLoginPanel() {
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        daftarButton = new JButton("Daftar");

        loginPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);   
       
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        loginPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        loginPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        loginPanel.add(loginButton, gbc);

        gbc.gridy = 3;
        loginPanel.add(daftarButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                boolean loginBerhasil = false;
                for (Akun akun : daftarAkun) {
                    if (akun.getUsername().equals(username) && akun.getPassword().equals(password)) {
                        loginBerhasil = true;
                        break;
                    }
                }

                if (loginBerhasil) {
                    System.out.println("Login berhasil!");
                    ((CardLayout) mainPanel.getLayout()).show(mainPanel, "Belanja");
                } else {
                    System.out.println("Login gagal!");
                    JOptionPane.showMessageDialog(null, "Username atau password salah!");
                }
            }
        });

        daftarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) mainPanel.getLayout()).show(mainPanel, "Daftar");
            }
        });

    }

    private void setupDaftarPanel() {
        newUsernameField = new JTextField(20);
        newPasswordField = new JPasswordField(20);
        buatAkunButton = new JButton("Buat Akun");
        kembaliButton = new JButton("Kembali");

        buatAkunButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newUsername = newUsernameField.getText();
                String newPassword = new String(newPasswordField.getPassword());

                daftarAkun.add(new Akun(newUsername, newPassword));

                JOptionPane.showMessageDialog(null, "Akun berhasil dibuat!");
                ((CardLayout) mainPanel.getLayout()).show(mainPanel, "Login");
            }
        });

        kembaliButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) mainPanel.getLayout()).show(mainPanel, "Login");
            }
        });

        daftarPanel.add(new JLabel("Username:"));
        daftarPanel.add(newUsernameField);
        daftarPanel.add(new JLabel("Password:"));
        daftarPanel.add(newPasswordField);
        daftarPanel.add(buatAkunButton);
        daftarPanel.add(kembaliButton);
    }

    private void setupBelanjaPanel() {
        listModelBarang = new DefaultListModel<>();
        listModelKeranjang = new DefaultListModel<>();

        JLabel daftarBarangLabel = new JLabel("<html><b>Daftar Barang:</b></html>");
        JList<String> daftarBarangList = new JList<>(listModelBarang);
        JScrollPane daftarBarangScrollPane = new JScrollPane(daftarBarangList);

        JLabel keranjangLabel = new JLabel("<html><b>Keranjang Belanja:</b></html>");
        JList<String> keranjangList = new JList<>(listModelKeranjang);
        JScrollPane keranjangScrollPane = new JScrollPane(keranjangList);

        totalLabel = new JLabel("Total: Rp 0");

        daftarBarang = new ArrayList<>();

        daftarBarang.add(new Barang(nomorBarang++, "Sapu lidi", 15000, 10));
        daftarBarang.add(new Barang(nomorBarang++, "Kemoceng", 20000, 5));
        daftarBarang.add(new Barang(nomorBarang++, "Keset", 25000, 12));
        daftarBarang.add(new Barang(nomorBarang++, "Ember", 35000, 8));
        daftarBarang.add(new Barang(nomorBarang++, "Gayung", 12000, 15));

        daftarBarang.add(new Barang(nomorBarang++, "Sabun mandi", 5000, 20));
        daftarBarang.add(new Barang(nomorBarang++, "Shampo", 20000, 10));
        daftarBarang.add(new Barang(nomorBarang++, "Sikat gigi", 10000, 15));
        daftarBarang.add(new Barang(nomorBarang++, "Pasta gigi", 15000, 12));
        daftarBarang.add(new Barang(nomorBarang++, "Handuk", 40000, 5));

        daftarBarang.add(new Barang(nomorBarang++, "Beras 5 kg", 60000, 10));
        daftarBarang.add(new Barang(nomorBarang++, "Minyak goreng 1 L", 20000, 15));
        daftarBarang.add(new Barang(nomorBarang++, "Gula pasir 1 kg", 15000, 20));
        daftarBarang.add(new Barang(nomorBarang++, "Telur ayam 1 kg", 25000, 12));
        daftarBarang.add(new Barang(nomorBarang++, "Mie instan (1 dus)", 80000, 8));

        updateListModelBarang();

        hapusButton = new JButton("Hapus Barang");
        urutComboBox = new JComboBox<>(new String[]{"Ascending (Nama)", "Descending (Nama)", "Ascending (Harga)", "Descending (Harga)"});
        urutButton = new JButton("Urutkan");
        logoutButton = new JButton("Logout");
        JButton beliButton = new JButton("Beli Barang");

        hapusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = keranjangList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Barang barangDihapus = keranjang.get(selectedIndex);
                    for (Barang barang : daftarBarang) {
                        if (barang.getNomor() == barangDihapus.getNomor()) {
                            barang.tambahKuantitas(barangDihapus.getKuantitas());
                            break;
                        }
                    }
                    keranjang.remove(selectedIndex);
                    updateListModelBarang();
                    updateListModelKeranjang();
                }
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) mainPanel.getLayout()).show(mainPanel, "Login");
            }
        });

        urutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String urutan = (String) urutComboBox.getSelectedItem();
                Comparator<Barang> comparator = null;
                switch (urutan) {
                    case "Ascending (Nama)":
                        comparator = Comparator.comparing(Barang::getNama);
                        break;
                    case "Descending (Nama)":
                        comparator = Comparator.comparing(Barang::getNama).reversed();
                        break;
                    case "Ascending (Harga)":
                        comparator = Comparator.comparing(Barang::getHarga);
                        break;
                    case "Descending (Harga)":
                        comparator = Comparator.comparing(Barang::getHarga).reversed();
                        break;
                }
                if (comparator != null) {
                    bubbleSort(keranjang, comparator);
                    updateListModelKeranjang();
                }
            }
        });

        beliButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (keranjang.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Keranjang belanja kosong!");
                    return;
                }

                StringBuilder sb = new StringBuilder();
                sb.append("Barang yang dibeli:\n");
                for (Barang barang : keranjang) {
                    sb.append(barang.toString()).append(" = Rp").append(barang.getHarga() * barang.getKuantitas()).append("\n");
                }
                sb.append("\nTotal: Rp").append(hitungTotalHarga(keranjang));

                JOptionPane.showMessageDialog(null, sb.toString());
                keranjang.clear();
                updateListModelKeranjang();
            }
        });

        JPanel inputPanel = new JPanel();
        JLabel nomorBarangLabel = new JLabel("Masukkan Nomor Barang:");
        JTextField nomorBarangField = new JTextField(10);
        JButton tambahKeranjangButton = new JButton("Tambah ke Keranjang");

        tambahKeranjangButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int nomor = Integer.parseInt(nomorBarangField.getText());
                    Barang barang = cariBarang(nomor);
                    if (barang != null) {
                        int kuantitas = Integer.parseInt(JOptionPane.showInputDialog("Masukkan kuantitas:"));
                        if (kuantitas > 0 && kuantitas <= barang.getKuantitas()) {
                            tambahKeKeranjang(barang, kuantitas);
                            barang.kurangiKuantitas(kuantitas);
                            updateListModelBarang();
                            updateListModelKeranjang();
                            JOptionPane.showMessageDialog(null, "Barang ditambahkan ke keranjang!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Kuantitas tidak valid!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Barang tidak ditemukan!");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Nomor barang dan kuantitas harus berupa angka!");
                }
            }
        });

        inputPanel.add(nomorBarangLabel);
        inputPanel.add(nomorBarangField);
        inputPanel.add(tambahKeranjangButton);

        belanjaPanel.setLayout(new BoxLayout(belanjaPanel, BoxLayout.Y_AXIS));
        belanjaPanel.add(daftarBarangLabel);
        belanjaPanel.add(daftarBarangScrollPane);
        belanjaPanel.add(keranjangLabel);
        belanjaPanel.add(keranjangScrollPane);
        belanjaPanel.add(totalLabel);
        belanjaPanel.add(hapusButton);
        belanjaPanel.add(urutComboBox);
        belanjaPanel.add(urutButton);
        belanjaPanel.add(beliButton); 
        belanjaPanel.add(logoutButton);
        belanjaPanel.add(inputPanel);
    }

    private Barang cariBarang(int nomor) {
        for (Barang barang : daftarBarang) {
            if (barang.getNomor() == nomor) {
                return barang;
            }
        }
        return null;
    }

    private void updateListModelBarang() {
        listModelBarang.clear();
        for (Barang barang : daftarBarang) {
            listModelBarang.addElement(barang.toString());
        }
    }

    private void updateListModelKeranjang() {
        listModelKeranjang.clear();
        int totalHarga = 0;
        for (Barang barang : keranjang) {
            listModelKeranjang.addElement(barang.toString() + " = Rp" + (barang.getHarga() * barang.getKuantitas()));
            totalHarga += barang.getHarga() * barang.getKuantitas();
        }
        totalLabel.setText("Total: Rp " + totalHarga);
    }

    private void tambahKeKeranjang(Barang barang, int kuantitas) {
        for (Barang item : keranjang) {
            if (item.getNomor() == barang.getNomor()) {
                item.tambahKuantitas(kuantitas);
                return;
            }
        }
        keranjang.add(new Barang(barang.getNomor(), barang.getNama(), barang.getHarga(), kuantitas));
    }

    private void bubbleSort(List<Barang> list, Comparator<Barang> comparator) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (comparator.compare(list.get(j), list.get(j + 1)) > 0) {
                    Barang temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }

    private int hitungTotalHarga(List<Barang> keranjang) {
        int totalHarga = 0;
        for (Barang barang : keranjang) {
            totalHarga += barang.getHarga() * barang.getKuantitas();
        }
        return totalHarga;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AplikasiBelanja().setVisible(true);
        });
    }
}

class Barang {
    private int nomor;
    private String nama;
    private int harga;
    private int kuantitas;

    public Barang(int nomor, String nama, int harga, int kuantitas) {
        this.nomor = nomor;
        this.nama = nama;
        this.harga = harga;
        this.kuantitas = kuantitas;
    }

    public int getNomor() {
        return nomor;
    }

    public String getNama() {
        return nama;
    }

    public int getHarga() {
        return harga;
    }

    public int getKuantitas() {
        return kuantitas;
    }

    public void kurangiKuantitas(int jumlah) {
        this.kuantitas -= jumlah;
    }

    public void tambahKuantitas(int jumlah) {
        this.kuantitas += jumlah;
    }

    @Override
    public String toString() {
        return nomor + ". " + nama + " - Rp" + harga + " (Qty: " + kuantitas + ")";
    }
}

class Akun {
    private String username;
    private String password;

    public Akun(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}