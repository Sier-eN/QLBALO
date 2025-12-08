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

CREATE TABLE DonViVanChuyen (
    MaDVVC VARCHAR(20) PRIMARY KEY,
    TenCongTy NVARCHAR(100) NOT NULL,
    SDT_LienHe VARCHAR(15),
    Email VARCHAR(100),
    NguoiLienHeChinh NVARCHAR(100),
    LoaiVanChuyen NVARCHAR(50), -- Đường bộ, Hàng không...
    ThoiGianGiaoHang NVARCHAR(50), -- Ví dụ: 3-5 ngày
    
    -- Thêm cột này để tính tiền
    PhiVanChuyen DECIMAL(18, 0) DEFAULT 0 
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

-- 6. Bảng Hàng Hóa (Đã chỉnh sửa)
CREATE TABLE HangHoa (
    MaHangHoa VARCHAR(20) PRIMARY KEY,
    TenHangHoa NVARCHAR(100) NOT NULL,
    
    -- Thay đổi ở đây:
    SoLuongNhap INT DEFAULT 0,      -- Số lượng lúc nhập hàng về
    SoLuongConLai INT DEFAULT 0,    -- Số lượng thực tế còn trong kho (để bán)
    
    GiaBan DECIMAL(18, 0),
    GiaNhap DECIMAL(18, 0),
    
    TongTienNhap DECIMAL(18, 0) DEFAULT 0, -- Tự động tính = SoLuongNhap * GiaNhap
    
    MaNhaCungCap VARCHAR(20),
    FOREIGN KEY (MaNhaCungCap) REFERENCES NhaCungCap(MaNhaCungCap)
);
GO

-- 7. Bảng Đơn Hàng (Quản lý Đơn Hàng - Image 2)
-- Lưu ý: Giao diện của bạn cho chọn 1 "Hàng Hóa" trong đơn hàng.
-- Nếu 1 đơn mua nhiều món, cần tách thêm bảng ChiTietDonHang.
-- Ở đây tôi làm theo đúng giao diện UI (1 đơn - 1 loại hàng).
-- 7. Bảng Đơn Hàng (Quản lý Đơn Hàng - Image 2)
CREATE TABLE DonHang (
    MaDH VARCHAR(20) PRIMARY KEY,
    TenNguoiMua NVARCHAR(100),
    DiaChi NVARCHAR(255),
    Email VARCHAR(100),
    SDT VARCHAR(15),
    
    -- THÔNG TIN MUA HÀNG
    MaHangHoa VARCHAR(20),
    SoLuong INT DEFAULT 1,    
    TongTien DECIMAL(18, 0),  
    
    -- CÁC KHÓA NGOẠI KHÁC
    MaDVVC VARCHAR(20),
    MaVoucher VARCHAR(20),
    
    -- TRẠNG THÁI (Đã sửa)
    NgayDatHang DATETIME DEFAULT GETDATE(),
    NgayDuKien DATETIME,
    NgayNhanHang DATETIME,
    
    -- Thêm DEFAULT N'Chờ Duyệt' ở đây
    TrangThai NVARCHAR(50) DEFAULT N'Chờ Duyệt', 
    
    -- LIÊN KẾT
    FOREIGN KEY (MaHangHoa) REFERENCES HangHoa(MaHangHoa),
    FOREIGN KEY (MaDVVC) REFERENCES DonViVanChuyen(MaDVVC),
    FOREIGN KEY (MaVoucher) REFERENCES Voucher(MaVoucher)
);
GO

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

CREATE OR ALTER TRIGGER trg_XuLyDonHang
ON DonHang
AFTER INSERT, UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    -- 1. TỰ ĐỘNG TÍNH TỔNG TIỀN ĐƠN HÀNG (Giữ nguyên logic cũ)
    IF UPDATE(SoLuong) OR UPDATE(MaHangHoa) OR UPDATE(MaVoucher) OR UPDATE(MaDVVC)
    BEGIN
        UPDATE dh
        SET dh.TongTien = 
            (hh.GiaBan * i.SoLuong) 
            - ISNULL(v.GiaTri, 0)   
            + ISNULL(dv.PhiVanChuyen, 0)
        FROM DonHang dh
        JOIN inserted i ON dh.MaDH = i.MaDH
        JOIN HangHoa hh ON dh.MaHangHoa = hh.MaHangHoa
        LEFT JOIN Voucher v ON dh.MaVoucher = v.MaVoucher
        JOIN DonViVanChuyen dv ON dh.MaDVVC = dv.MaDVVC;
    END

    -- 2. TỰ ĐỘNG TRỪ KHO VÀO CỘT 'SoLuongConLai'
    -- Logic: Chỉ trừ khi trạng thái đơn hàng là 'Đã thanh toán'
    
    -- Trường hợp A: Đơn hàng mới thêm vào đã là 'Đã thanh toán' (Bán tại quầy)
    IF EXISTS (SELECT * FROM inserted WHERE TrangThai = N'Đã Giao Hàng')
       AND NOT EXISTS (SELECT * FROM deleted)
    BEGIN
        UPDATE hh
        SET hh.SoLuongConLai = hh.SoLuongConLai - i.SoLuong
        FROM HangHoa hh
        JOIN inserted i ON hh.MaHangHoa = i.MaHangHoa
        WHERE i.TrangThai = N'Đã Giao Hàng';
    END

    -- Trường hợp B: Cập nhật từ trạng thái khác sang 'Đã thanh toán' (Ship COD thành công)
    IF EXISTS (SELECT * FROM inserted WHERE TrangThai = N'Đã Giao Hàng')
       AND EXISTS (SELECT * FROM deleted WHERE TrangThai <> N'Đã Giao Hàng')
    BEGIN
        UPDATE hh
        SET hh.SoLuongConLai = hh.SoLuongConLai - i.SoLuong
        FROM HangHoa hh
        JOIN inserted i ON hh.MaHangHoa = i.MaHangHoa
        WHERE i.TrangThai = N'Đã Giao Hàng';
    END
    
    -- (Nâng cao) Trường hợp C: Hoàn tác kho nếu đơn hàng từ 'Đã thanh toán' chuyển sang 'Đã Hủy' (Khách trả hàng)
    IF EXISTS (SELECT * FROM inserted WHERE TrangThai = N'Hủy Đơn')
       AND EXISTS (SELECT * FROM deleted WHERE TrangThai = N'Đã Giao Hàng')
    BEGIN
        UPDATE hh
        SET hh.SoLuongConLai = hh.SoLuongConLai + i.SoLuong -- Cộng lại kho
        FROM HangHoa hh
        JOIN inserted i ON hh.MaHangHoa = i.MaHangHoa
        WHERE i.TrangThai = N'Hủy Đơn';
    END
END;
GO
--------------------------------------------------------------------------------------------------------------------------------
CREATE OR ALTER TRIGGER trg_TuDongTinhTienNhap
ON HangHoa
AFTER INSERT, UPDATE
AS
BEGIN
    -- Chỉ chạy khi có update liên quan đến Số Lượng Nhập hoặc Giá Nhập
    IF UPDATE(SoLuongNhap) OR UPDATE(GiaNhap)
    BEGIN
        -- 1. Cập nhật Tổng tiền nhập
        UPDATE h
        SET h.TongTienNhap = i.SoLuongNhap * i.GiaNhap
        FROM HangHoa h
        JOIN inserted i ON h.MaHangHoa = i.MaHangHoa;

        -- 2. Cập nhật Số lượng còn lại (Logic: Khởi tạo kho)
        -- Lưu ý: Chỉ cập nhật SoLuongConLai bằng SoLuongNhap KHI CHÚNG TA THÊM MỚI (INSERT)
        -- Nếu là UPDATE, ta không reset SoLuongConLai vì có thể đã bán bớt rồi.
        
        -- Logic dưới đây xử lý cho trường hợp INSERT mới
        UPDATE h
        SET h.SoLuongConLai = i.SoLuongNhap
        FROM HangHoa h
        JOIN inserted i ON h.MaHangHoa = i.MaHangHoa
        WHERE NOT EXISTS (SELECT 1 FROM deleted); -- Đây là dấu hiệu của lệnh INSERT (không có bảng deleted)
    END
END;
GO
-----------------------------------------------------------------------------------------------------------------------------------
CREATE OR ALTER TRIGGER trg_CapNhatHangHoa
ON HangHoa
AFTER UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    -- 1. Nếu có sửa Số Lượng Nhập -> Cập nhật lại Số Lượng Còn Lại & Tổng Tiền
    IF UPDATE(SoLuongNhap)
    BEGIN
        UPDATE h
        SET 
            -- Công thức: Tồn mới = Tồn cũ + (Nhập mới - Nhập cũ)
            h.SoLuongConLai = h.SoLuongConLai + (i.SoLuongNhap - d.SoLuongNhap),
            
            -- Tính lại tổng tiền nhập
            h.TongTienNhap = i.SoLuongNhap * h.GiaNhap
        FROM HangHoa h
        JOIN inserted i ON h.MaHangHoa = i.MaHangHoa
        JOIN deleted d ON h.MaHangHoa = d.MaHangHoa;
    END

    -- 2. Nếu có sửa Giá Nhập -> Tính lại Tổng Tiền
    IF UPDATE(GiaNhap)
    BEGIN
        UPDATE h
        SET h.TongTienNhap = h.SoLuongNhap * i.GiaNhap
        FROM HangHoa h
        JOIN inserted i ON h.MaHangHoa = i.MaHangHoa;
    END
END;
GO

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

CREATE PROCEDURE sp_CapNhatNhanVien
    @MaNhanVien VARCHAR(20),
    @TenNhanVien NVARCHAR(100),
    @DiaChi NVARCHAR(255),
    @Email VARCHAR(100),
    @SDT VARCHAR(15),
    @TenDangNhap VARCHAR(50), -- Cho phép sửa tên đăng nhập nếu muốn
    @MatKhau VARCHAR(255),
    @VaiTro NVARCHAR(50)
AS
BEGIN
    BEGIN TRANSACTION;
    BEGIN TRY
        -- 1. Cập nhật bảng Tài Khoản trước
        -- (Tìm ID_TaiKhoan thông qua MaNhanVien)
        UPDATE TaiKhoan
        SET TenDangNhap = @TenDangNhap,
            MatKhau = @MatKhau,
            VaiTro = @VaiTro
        FROM TaiKhoan t
        INNER JOIN NhanVien n ON t.ID_TaiKhoan = n.ID_TaiKhoan
        WHERE n.MaNhanVien = @MaNhanVien;

        -- 2. Cập nhật bảng Nhân Viên
        UPDATE NhanVien
        SET TenNhanVien = @TenNhanVien,
            DiaChi = @DiaChi,
            Email = @Email,
            SDT = @SDT
        WHERE MaNhanVien = @MaNhanVien;

        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        ROLLBACK TRANSACTION;
        THROW;
    END CATCH
END;
GO

CREATE OR ALTER PROCEDURE sp_ThemNhanVienVaTaiKhoan
    @MaNhanVien VARCHAR(20),
    @TenNhanVien NVARCHAR(100),
    @DiaChi NVARCHAR(255),
    @Email VARCHAR(100),
    @SDT VARCHAR(15),
    @TenDangNhap VARCHAR(50),
    @MatKhau VARCHAR(255),
    @VaiTro NVARCHAR(50)
AS
BEGIN
    BEGIN TRANSACTION; -- Bắt đầu giao dịch an toàn
    BEGIN TRY
        -- 1. Thêm vào bảng TaiKhoan
        INSERT INTO TaiKhoan (TenDangNhap, MatKhau, VaiTro)
        VALUES (@TenDangNhap, @MatKhau, @VaiTro);

        -- 2. Lấy ID_TaiKhoan vừa sinh ra
        DECLARE @NewID INT = SCOPE_IDENTITY();

        -- 3. Thêm vào bảng NhanVien (Dùng ID vừa lấy)
        INSERT INTO NhanVien (MaNhanVien, TenNhanVien, DiaChi, Email, SDT, ID_TaiKhoan)
        VALUES (@MaNhanVien, @TenNhanVien, @DiaChi, @Email, @SDT, @NewID);

        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        ROLLBACK TRANSACTION;
        THROW; -- Ném lỗi ra nếu trùng mã hoặc lỗi hệ thống
    END CATCH
END;
GO

CREATE OR ALTER PROCEDURE sp_XoaNhanVien
    @MaNhanVien VARCHAR(20)
AS
BEGIN
    BEGIN TRANSACTION;
    BEGIN TRY
        -- 1. Tìm ID_TaiKhoan để xóa sau này
        DECLARE @ID_TaiKhoan INT;
        SELECT @ID_TaiKhoan = ID_TaiKhoan FROM NhanVien WHERE MaNhanVien = @MaNhanVien;

        -- 2. Xóa NhanVien trước
        DELETE FROM NhanVien WHERE MaNhanVien = @MaNhanVien;

        -- 3. Xóa TaiKhoan sau (Nếu tìm thấy ID)
        IF @ID_TaiKhoan IS NOT NULL
        BEGIN
            DELETE FROM TaiKhoan WHERE ID_TaiKhoan = @ID_TaiKhoan;
        END

        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        ROLLBACK TRANSACTION;
        THROW; -- Ném lỗi ra (ví dụ: Không xóa được do NV đã lập đơn hàng)
    END CATCH
END;
GO
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

GO
USE master;
Go

-- Bước 1: Ngắt kết nối các user đang dùng database (nếu có)
ALTER DATABASE QuanLyBaLoCapSach
SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
GO

-- Bước 2: Xóa database
DROP DATABASE QuanLyBaLoCapSach;
GO

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

INSERT INTO TaiKhoan (TenDangNhap, MatKhau, VaiTro)
VALUES ('nhanvien', '1', 'NhanVien')

-- 2. Lấy ID vừa tạo để gắn vào nhân viên
DECLARE @NewID INT = SCOPE_IDENTITY();

INSERT INTO NhanVien (MaNhanVien, TenNhanVien, DiaChi, Email, SDT, ID_TaiKhoan)
VALUES ('NV002', N'Nguyễn Trọng Nghĩa', N'Phú Thọ', 'nhanvien1@gmail.com', '0909123456', @NewID);
GO



-- 1. Tạo tài khoản đăng nhập trước
INSERT INTO TaiKhoan (TenDangNhap, MatKhau, VaiTro)
VALUES ('admin', '1', 'Admin');

-- 2. Lấy ID vừa tạo để gắn vào nhân viên
DECLARE @NewID INT = SCOPE_IDENTITY();

-- 3. Tạo thông tin nhân viên tương ứng
INSERT INTO NhanVien (MaNhanVien, TenNhanVien, DiaChi, Email, SDT, ID_TaiKhoan)
VALUES ('NV001', N'Quản Trị Viên', N'Hà Nội', 'admin@gmail.com', '0909123456', @NewID);
GO
-- 1. Giao Hàng Tiết Kiệm (Đi thường - Giá rẻ)
INSERT INTO DonViVanChuyen (MaDVVC, TenCongTy, SDT_LienHe, Email, NguoiLienHeChinh, LoaiVanChuyen, ThoiGianGiaoHang, PhiVanChuyen)
VALUES ('GHTK', N'Giao Hàng Tiết Kiệm', '1900 6092', 'cskh@ghtk.vn', N'Nguyễn Văn A', N'Đường Bộ', N'3-5 Ngày', 30000);

-- 2. Viettel Post (Hỏa tốc - Giá cao hơn)
INSERT INTO DonViVanChuyen (MaDVVC, TenCongTy, SDT_LienHe, Email, NguoiLienHeChinh, LoaiVanChuyen, ThoiGianGiaoHang, PhiVanChuyen)
VALUES ('VTP', N'Viettel Post', '1900 8095', 'support@viettelpost.com.vn', N'Trần Thị B', N'Hỏa Tốc', N'24 Giờ', 55000);
GO
INSERT INTO NhaCungCap (MaNhaCungCap, TenCongTy, DiaChi, SDT_LienHe, Email, NguoiLienHeChinh)
VALUES ('NCC01', N'Công ty May Mặc Việt', N'Hà Nội', '0988111222', 'lienhe@maymac.vn', N'Trần Văn A');

INSERT INTO NhaCungCap (MaNhaCungCap, TenCongTy, DiaChi, SDT_LienHe, Email, NguoiLienHeChinh)
VALUES ('NCC02', N'Balo Hàng Hiệu LTD', N'TP.HCM', '0909333444', 'admin@hanghieu.com', N'Lê Thị B');
GO

INSERT INTO HangHoa (MaHangHoa, TenHangHoa, SoLuongNhap, GiaBan, GiaNhap, MaNhaCungCap)
VALUES ('HH01', N'Balo Chống Gù Nhật Bản', 50, 1200000, 800000, 'NCC01');

INSERT INTO HangHoa (MaHangHoa, TenHangHoa, SoLuongNhap, GiaBan, GiaNhap, MaNhaCungCap)
VALUES ('HH02', N'Cặp Sách Học Sinh Cấp 1', 100, 350000, 200000, 'NCC02');
GO

-- Ví dụ 1: Voucher giảm giá trực tiếp 50k
INSERT INTO Voucher (MaVoucher, TenVoucher, ChucNang, GiaTri, SoLuong)
VALUES ('SALE50K', N'Giảm giá Khai Trương', N'Giảm Giá Hàng', 50000, 100);

-- Ví dụ 2: Voucher miễn phí vận chuyển (Tối đa 30k)
INSERT INTO Voucher (MaVoucher, TenVoucher, ChucNang, GiaTri, SoLuong)
VALUES ('FREESHIP', N'Miễn Phí Vận Chuyển Hè', N'Free Ship', 30000, 200);
GO

-- 1. Đơn hàng mới (Trạng thái: Chờ Duyệt) - Mua Balo (HH01), ship GHTK, không dùng Voucher
INSERT INTO DonHang (MaDH, TenNguoiMua, DiaChi, Email, SDT, MaHangHoa, SoLuong, TongTien, MaDVVC, MaVoucher, NgayDatHang, NgayDuKien, NgayNhanHang, TrangThai)
VALUES ('DH001', N'Nguyễn Văn An', N'123 Cầu Giấy, Hà Nội', 'an@gmail.com', '0912345678', 'HH01', 1, 0, 'GHTK', NULL, GETDATE(), GETDATE()+3, NULL, N'Chờ Duyệt');

-- 2. Đơn hàng đang đi (Trạng thái: Đang Giao) - Mua Cặp (HH02), ship Viettel, dùng Voucher 50K
INSERT INTO DonHang (MaDH, TenNguoiMua, DiaChi, Email, SDT, MaHangHoa, SoLuong, TongTien, MaDVVC, MaVoucher, NgayDatHang, NgayDuKien, NgayNhanHang, TrangThai)
VALUES ('DH002', N'Trần Thị Bích', N'456 Lê Lợi, TP.HCM', 'bich@gmail.com', '0987654321', 'HH02', 2, 0, 'VTP', 'SALE50K', GETDATE()-1, GETDATE()+2, NULL, N'Đang Giao');

-- 3. Đơn hàng thành công (Trạng thái: Đã thanh toán) - Mua Balo (HH01), ship GHTK, dùng Voucher Freeship
-- (Lưu ý: Trigger sẽ tự động trừ kho của HH01 đi 1 cái)
INSERT INTO DonHang (MaDH, TenNguoiMua, DiaChi, Email, SDT, MaHangHoa, SoLuong, TongTien, MaDVVC, MaVoucher, NgayDatHang, NgayDuKien, NgayNhanHang, TrangThai)
VALUES ('DH003', N'Lê Văn Cường', N'789 Hải Phòng, Đà Nẵng', 'cuong@gmail.com', '0905123123', 'HH01', 1, 0, 'GHTK', 'FREESHIP', '2023-11-20', '2023-11-23', '2023-11-23', N'Đã thanh toán');

-- 4. Đơn hàng bị bom/hủy (Trạng thái: Đã Hủy) - Mua số lượng lớn 10 cái nhưng hủy
INSERT INTO DonHang (MaDH, TenNguoiMua, DiaChi, Email, SDT, MaHangHoa, SoLuong, TongTien, MaDVVC, MaVoucher, NgayDatHang, NgayDuKien, NgayNhanHang, TrangThai)
VALUES ('DH004', N'Phạm Tuấn Tú', N'Số 1 Hùng Vương, Cần Thơ', 'tuantut@gmail.com', '0939393939', 'HH02', 10, 0, 'VTP', NULL, '2023-10-10', '2023-10-12', NULL, N'Đã Hủy');

-- 5. Đơn hàng thành công khác (Trạng thái: Đã thanh toán) - Mua Cặp (HH02)
INSERT INTO DonHang (MaDH, TenNguoiMua, DiaChi, Email, SDT, MaHangHoa, SoLuong, TongTien, MaDVVC, MaVoucher, NgayDatHang, NgayDuKien, NgayNhanHang, TrangThai)
VALUES ('DH005', N'Hoàng Thùy Linh', N'Vĩnh Yên, Vĩnh Phúc', 'linh@gmail.com', '0968686868', 'HH02', 3, 0, 'GHTK', 'SALE50K', GETDATE(), GETDATE()+3, GETDATE(), N'Đã thanh toán');
GO