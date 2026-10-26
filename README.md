# BT-Event: Thực Hành Bắt Sự Kiện & Xác Thực Form Android

Dự án Android gồm hai Activity: **Màn hình Đăng ký** (`MainActivity`) và **Màn hình Chi tiết** (`DetailActivity`), được xây dựng hoàn chỉnh theo yêu cầu bài thực hành Lecture 05: Events & Form Validation.

## Thông tin sinh viên thực hiện
- **Họ và tên:** Võ Quốc Khánh
- **MSSV:** 2212345
- **Môn học:** Thực hành Lập trình trên thiết bị di động (TH LTTĐTDĐ)
- **Repository:** https://github.com/Khanh048/BT-Event.git

---

## 1. Các tính năng và yêu cầu đã hoàn thành

### Giao diện đăng ký (MainActivity)
- **Các ô nhập liệu (TextInputLayout Material 3 OutlinedBox):**
  - Họ và tên (`etFullName`)
  - Email (`etEmail`)
  - Số điện thoại (`etPhone`)
  - Mã số sinh viên (`etStudentId`)
- **RadioGroup Giới tính (`rgGender`):** Nam, Nữ, Khác.
- **CheckBox Mạng xã hội:** Facebook, Zalo, TikTok, Instagram.
- **CheckBox Điều khoản cam kết (`cbTerms`):** "Tôi đồng ý với các điều khoản cam kết".
- **Nút Đăng ký (`btnRegister`):** MaterialButton có chống spam click (Double Click Debouncing).
- **Thông tin sinh viên:** Hiển thị nổi bật ở chân trang (Footer Card).

### Bắt sự kiện và hiển thị Toast tương tác thời gian thực
- **EditText Events:**
  - Bắt sự kiện `OnFocusChangeListener`: Khi người dùng chạm vào ô nào sẽ hiển thị Toast thông báo đang nhập trường đó.
  - Bắt sự kiện `doAfterTextChanged`: Khi người dùng gõ phím, tự động xóa thông báo lỗi màu đỏ ngay lập tức (`error = null`).
- **RadioButton Events:**
  - Bắt sự kiện `setOnCheckedChangeListener` trên `RadioGroup`: Hiển thị Toast thông báo ngay giới tính được chọn (Nam / Nữ / Khác).
- **CheckBox Events:**
  - Bắt sự kiện `setOnCheckedChangeListener` trên các checkbox mạng xã hội và điều khoản: Hiển thị Toast thông báo trạng thái "Đã chọn..." hoặc "Đã bỏ chọn...".

### Xác thực dữ liệu (Form Validation) & Chuyển màn hình
- Đóng gói kiểm tra tính hợp lệ bằng lớp `FormValidator` (sử dụng Regex chuẩn cho Email và SĐT 10 số đầu 03/05/07/08/09).
- Tự động kiểm tra đồng loạt (Cumulative Validation) và tự động Focus vào trường bị lỗi đầu tiên (`firstErrorView.requestFocus()`).
- Khi bấm **Đăng ký** với dữ liệu hợp lệ:
  - Hiển thị Toast thông báo đăng ký thành công.
  - Sử dụng Explicit Intent đóng gói dữ liệu đã chọn gửi sang `DetailActivity`.

### Màn hình Chi tiết (DetailActivity)
- Nhận và hiển thị đầy đủ các thông tin: Họ tên, Email, Số điện thoại, MSSV, Giới tính, Danh sách Mạng xã hội đã chọn.
- Hiển thị Toast khi mở màn hình chi tiết.
- Nút "Quay lại đăng ký" hỗ trợ điều hướng hai chiều mượt mà.

---

## 2. Hướng dẫn mở và chạy trên Android Studio

1. Mở **Android Studio**.
2. Chọn **Open** và duyệt đến thư mục `BT-Event`.
3. Chờ Gradle Sync hoàn tất.
4. Chọn máy ảo hoặc thiết bị thật kết nối qua USB Debugging.
5. Bấm nút **Run** (Shift + F10) để khởi chạy ứng dụng.
