-- 1. Tạo CSDL
-- ============================
CREATE DATABASE QuanLyDoanhNghiep;
GO

USE QuanLyDoanhNghiep;
GO

-- ============================
-- 2. Bảng Nhà Cung Cấp
-- ============================
CREATE TABLE NhaCungCap (
    MaNCC VARCHAR(10) PRIMARY KEY,
    TenCongTy NVARCHAR(100) NOT NULL,
    DiaChi NVARCHAR(200),
    SDT VARCHAR(15),
    Email VARCHAR(100),
    NguoiLienHeChinh NVARCHAR(100)
);
GO

-- ============================
-- 3. Bảng Loại Hàng
-- ============================
CREATE TABLE LoaiHang (
    MaLoai VARCHAR(10) PRIMARY KEY,
    TenLoai NVARCHAR(100) NOT NULL
);
GO

-- ============================
-- 4. Bảng Hàng Hóa
-- ============================
CREATE TABLE HangHoa (
    MaHH VARCHAR(10) PRIMARY KEY,
    TenHangHoa NVARCHAR(100) NOT NULL,
    SoLuongTon INT,
    GiaBan DECIMAL(18, 2),
    GiaNhap DECIMAL(18, 2),
    MaNCC VARCHAR(10),
    MaLoai VARCHAR(10),
    FOREIGN KEY (MaNCC) REFERENCES NhaCungCap(MaNCC),
    FOREIGN KEY (MaLoai) REFERENCES LoaiHang(MaLoai)
);
GO

-- ============================
-- 5. Bảng Khách Hàng
-- ============================
CREATE TABLE KhachHang (
    MaKH VARCHAR(10) PRIMARY KEY,
    TenKH NVARCHAR(100) NOT NULL,
    DiaChi NVARCHAR(200),
    Email VARCHAR(100),
    SDT VARCHAR(15)
);
GO

-- ============================
-- 6. Bảng Nhân Viên
-- ============================
CREATE TABLE NhanVien (
    MaNV VARCHAR(10) PRIMARY KEY,
    TenNhanVien NVARCHAR(100) NOT NULL,
    DiaChi NVARCHAR(200),
    Email VARCHAR(100),
    SDT VARCHAR(15),
    TaiKhoan VARCHAR(50) NOT NULL UNIQUE,
    MatKhau VARCHAR(255) NOT NULL,
    MaVaiTro VARCHAR(10),
    FOREIGN KEY (MaVaiTro) REFERENCES VaiTro(MaVaiTro)
);
GO

-- ============================
-- 7. Bảng Vai Trò và Quyền Hạn
-- ============================
CREATE TABLE VaiTro (
    MaVaiTro VARCHAR(10) PRIMARY KEY,
    TenVaiTro NVARCHAR(50) NOT NULL
);
GO

CREATE TABLE QuyenHan (
    MaVaiTro VARCHAR(10),
    TenQuyen NVARCHAR(50),
    PRIMARY KEY (MaVaiTro, TenQuyen),
    FOREIGN KEY (MaVaiTro) REFERENCES VaiTro(MaVaiTro)
);
GO

-- ============================
-- 8. Bảng Đơn Vị Vận Chuyển
-- ============================
CREATE TABLE DonViVanChuyen (
    MaDVVC VARCHAR(10) PRIMARY KEY,
    CongTy NVARCHAR(100) NOT NULL,
    SDTLienHe VARCHAR(15),
    Email VARCHAR(100),
    NguoiLienHeChinh NVARCHAR(100),
    LoaiVanChuyen NVARCHAR(50),
    ThoiGianGiaoHangTB INT
);
GO

-- ============================
-- 9. Bảng Voucher
-- ============================
CREATE TABLE Voucher (
    MaVoucher VARCHAR(10) PRIMARY KEY,
    TenVoucher NVARCHAR(100) NOT NULL,
    ChucNang NVARCHAR(50),
    GiaTri DECIMAL(10, 2),
    SoLuong INT
);
GO

-- ============================
-- 10. Bảng Đơn Hàng
-- ============================
CREATE TABLE DonHang (
    MaDH VARCHAR(10) PRIMARY KEY,
    MaKH VARCHAR(10),
    TenNguoiMua NVARCHAR(100) NOT NULL,
    DiaChi NVARCHAR(200),
    Email NVARCHAR(100),
    SDT VARCHAR(15),
    NgayDatHang DATE,
    TrangThai NVARCHAR(50),
    MaDVVC VARCHAR(10),
    MaVoucher VARCHAR(10),
    FOREIGN KEY (MaKH) REFERENCES KhachHang(MaKH),
    FOREIGN KEY (MaDVVC) REFERENCES DonViVanChuyen(MaDVVC),
    FOREIGN KEY (MaVoucher) REFERENCES Voucher(MaVoucher)
);
GO

-- ============================
-- 11. Bảng Chi Tiết Đơn Hàng
-- ============================
CREATE TABLE ChiTietDonHang (
    MaDH VARCHAR(10),
    MaHH VARCHAR(10),
    SoLuong INT NOT NULL,
    DonGia DECIMAL(18, 2),
    PRIMARY KEY (MaDH, MaHH),
    FOREIGN KEY (MaDH) REFERENCES DonHang(MaDH),
    FOREIGN KEY (MaHH) REFERENCES HangHoa(MaHH)
);
GO

-- ============================
-- 12. Phiếu Nhập Kho
-- ============================
CREATE TABLE PhieuNhapKho (
    MaPNK VARCHAR(10) PRIMARY KEY,
    MaNV VARCHAR(10),
    NgayNhap DATE,
    GhiChu NVARCHAR(200),
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV)
);
GO

-- ============================
-- 13. Chi Tiết Phiếu Nhập
-- ============================
CREATE TABLE ChiTietPhieuNhap (
    MaPNK VARCHAR(10),
    MaHH VARCHAR(10),
    SoLuong INT,
    DonGia DECIMAL(18,2),
    PRIMARY KEY (MaPNK, MaHH),
    FOREIGN KEY (MaPNK) REFERENCES PhieuNhapKho(MaPNK),
    FOREIGN KEY (MaHH) REFERENCES HangHoa(MaHH)
);
GO