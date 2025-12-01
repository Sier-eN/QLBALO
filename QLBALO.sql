-- Tạo Database
CREATE DATABASE QuanLyBaloCapSach;
GO
USE QuanLyBaloCapSach;
GO

-- 1. Bảng Tài Khoản (Tách riêng theo yêu cầu)
-- Chỉ chứa thông tin đăng nhập và phân quyền
CREATE TABLE TaiKhoan (
    ID_TaiKhoan INT IDENTITY(1,1) PRIMARY KEY,
    TenDangNhap VARCHAR(50) NOT NULL UNIQUE,
    MatKhau VARCHAR(255) NOT NULL, -- Nên lưu mã hóa (MD5/SHA) trong thực tế
    VaiTro NVARCHAR(50) -- Ví dụ: Admin, NhanVien
);
GO

-- 2. Bảng Nhân Viên (Quản lý Nhân Viên - Image 3 & 4)
-- Liên kết 1-1 với bảng TaiKhoan
CREATE TABLE NhanVien (
    MaNhanVien VARCHAR(20) PRIMARY KEY, -- Nhập tay ví dụ NV2605
    TenNhanVien NVARCHAR(100) NOT NULL,
    DiaChi NVARCHAR(255),
    Email VARCHAR(100),
    SDT VARCHAR(15),
    ID_TaiKhoan INT UNIQUE, -- Khóa ngoại liên kết bảng TaiKhoan
    FOREIGN KEY (ID_TaiKhoan) REFERENCES TaiKhoan(ID_TaiKhoan)
);
GO

-- 3. Bảng Nhà Cung Cấp (Quản lý Nhà Cung Cấp - Image 1)
CREATE TABLE NhaCungCap (
    MaNhaCungCap VARCHAR(20) PRIMARY KEY,
    TenCongTy NVARCHAR(100) NOT NULL,
    DiaChi NVARCHAR(255),
    SDT_LienHe VARCHAR(15),
    Email VARCHAR(100),
    NguoiLienHeChinh NVARCHAR(100)
);
GO

-- 4. Bảng Đơn Vị Vận Chuyển (Quản lý ĐVVC - Image 7)
CREATE TABLE DonViVanChuyen (
    MaDVVC VARCHAR(20) PRIMARY KEY,
    TenCongTy NVARCHAR(100) NOT NULL,
    SDT_LienHe VARCHAR(15),
    Email VARCHAR(100),
    NguoiLienHeChinh NVARCHAR(100),
    LoaiVanChuyen NVARCHAR(50), -- Đường bộ, Hàng không...
    ThoiGianGiaoHang NVARCHAR(50) -- Ví dụ: 3-5 ngày
);
GO

-- 5. Bảng Voucher (Quản lý Voucher - Image 6)
CREATE TABLE Voucher (
    MaVoucher VARCHAR(20) PRIMARY KEY,
    TenVoucher NVARCHAR(100),
    ChucNang NVARCHAR(100), -- Ví dụ: Giảm giá %
    GiaTri DECIMAL(18, 2),  -- Số tiền giảm hoặc % giảm
    SoLuong INT
);
GO

-- 6. Bảng Hàng Hóa (Quản lý Hàng Hóa - Image 5)
CREATE TABLE HangHoa (
    MaHangHoa VARCHAR(20) PRIMARY KEY,
    TenHangHoa NVARCHAR(100) NOT NULL,
    SoLuong INT,
    GiaBan DECIMAL(18, 0),
    GiaNhap DECIMAL(18, 0),
    MaNhaCungCap VARCHAR(20), -- Khóa ngoại
    FOREIGN KEY (MaNhaCungCap) REFERENCES NhaCungCap(MaNhaCungCap)
);
GO

-- 7. Bảng Đơn Hàng (Quản lý Đơn Hàng - Image 2)
-- Lưu ý: Giao diện của bạn cho chọn 1 "Hàng Hóa" trong đơn hàng.
-- Nếu 1 đơn mua nhiều món, cần tách thêm bảng ChiTietDonHang.
-- Ở đây tôi làm theo đúng giao diện UI (1 đơn - 1 loại hàng).
CREATE TABLE DonHang (
    MaDH VARCHAR(20) PRIMARY KEY,
    TenNguoiMua NVARCHAR(100),
    DiaChi NVARCHAR(255),
    Email VARCHAR(100),
    SDT VARCHAR(15),
    NgayDatHang DATETIME DEFAULT GETDATE(),
    TrangThai NVARCHAR(50), -- Chờ xử lý, Đang giao, Hoàn thành
    
    -- Các khóa ngoại
    MaHangHoa VARCHAR(20),
    MaDVVC VARCHAR(20),
    MaVoucher VARCHAR(20),
    
    FOREIGN KEY (MaHangHoa) REFERENCES HangHoa(MaHangHoa),
    FOREIGN KEY (MaDVVC) REFERENCES DonViVanChuyen(MaDVVC),
    FOREIGN KEY (MaVoucher) REFERENCES Voucher(MaVoucher)
);
GO