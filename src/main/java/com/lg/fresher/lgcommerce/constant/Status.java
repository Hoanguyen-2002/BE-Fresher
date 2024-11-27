package com.lg.fresher.lgcommerce.constant;

public enum Status {

    OK(0, "Thao tác thành công"),


    WARN_PARTIAL_SUCCESS(1001, "Cảnh báo: thao tác chưa được xử lý hết"),

    WARN_PERFORMANCE_ISSUE(1002, "Cảnh báo: hệ thống gặp vấn đề về hiệu năng"),

    NO_CONTENT(2004, "Không có dữ liệu"),

    FAIL_INVALID_PARAM(4000, "Tham số không hợp lệ"),

    FAIL_INVALID_TOKEN(4001, "Token không hợp lệ"),

    FAIL_TOKEN_ACCESS_FORBIDDEN(4002, "Access token bị từ chối"),

    FAIL_NO_PERMISSION(4003, "Không có quyền thực hiện thao tác này"),

    FAIL_NOT_FOUND(4004, "Không tìm thấy thông tin yêu cầu"),

    FAIL_VALIDATION(4005, "Lỗi xác thực"),

    FAIL_OPERATION(4006, "Thao tác không thành công"),

    FAIL_REQUEST_TIMEOUT(4008, "Hết thời gian chờ"),

    FAIL_AUTHENTICATION(4009, "Xác thực thất bại"),

    FAIL_INVALID_ACCOUNT(4012, "Tài khoản hoặc mật khẩu không chính xác"),

    FAIL_EXCEPTION(5000, "Thao tác gặp lỗi"),

    FAIL_INTERNAL_SERVER_ERROR(5001, "Lỗi hệ thống"),

    FAIL_SERVICE_UNAVAILABLE(5003, "Service hiện không khả dụng"),

    FAIL_DUPLICATE_DATA_EXCEPTION(5003, "Lỗi trùng lặp dữ liệu"),

    FAIL_USER_NOT_FOUND(6001, "Không tìm thấy người dùng"),
    FAIL_USER_IS_NOT_ACTIVE(6002, "Tài khoản chưa được kích hoạt, vui lòng kích hoạt tài khoản"),
    FAIL_USER_IS_BANNED(6003, "Tài khoản đã bị khóa"),
    DATA_IS_DEACTIVE(6005, "Dữ liệu hiện không khả dụng"),

    FAIL_REFRESH_TOKEN_INVALID(7001, "Refresh token không hợp lệ"),
    FAIL_SEARCH_INVALID_PARAM(9001, "Tham số tìm kiếm không hợp lệ"),

    REGISTER_SUCCESS(10000, "Đăng ký thành công"),
    REGISTER_USER_EXISTED(10101, "Tài khoản đã tồn tại"),
    REGISTER_EMAIL_EXISTED(10102, "Email đã tồn tại"),

    VERIFY_SUCCESS(11000, "Xác thực thành công"),
    VERIFY_RESEND_SUCCESS(11001, "Gửi lại mã xác thực thành công"),
    VERIFY_FAIL_CODE_INVALID(11101, "Mã xác thực không hợp lệ"),
    VERIFY_FAIL_CODE_EXPIRATION(11102, "Mã xác thực đã hết hạn, vui lòng yêu cầu mã xác thực mới"),
    VERIFY_FAIL_USER_ALREADY_VERIFIED(11103, "Mã xác thực đã hết hạn, vui lòng yêu cầu mã xác thực mới"),

    UPDATE_ACCOUNT_SUCCESS(12000, "Cập nhật thông tin thành công"),
    UPDATE_ACCOUNT_FAIL_ADDRESS_NOT_FOUND(120101, "Không tìm thấy địa chỉ cập nhật"),
    UPDATE_ACCOUNT_FAIL_CHANGE_PASSWORD_NOT_MATCH(120102, "Mật khẩu không chính xác"),

    LOGOUT_SUCCESS(13000, "Đăng xuất thành công"),

    CHECKOUT_SUCCESS(14000, "Thông tin đơn hàng hợp lệ"),
    CHECKOUT_FAIL_PRODUCT_HAVE_CHANGED(14100, "Sản phẩm đã cập nhật, vui lòng tải lại trang"),
    CHECKOUT_FAIL_PRICE_PRODUCT_HAVE_CHANGED(14101, "Giá sản phẩm đã được cập nhật lại"),

    ORDER_SUCCESS(15000, "Đặt hàng thành công"),
    ORDER_FAIL_ORDER_NOT_FOUND(15100, "Không tìm thấy đơn hàng, vui lòng đặt lại"),
    ORDER_FAIL_SHIPPING_METHOD_NOT_FOUND(15101, "Phương thức giao hàng không tồn tại, vui lòng thử lại"),
    ORDER_FAIL_PAYMENT_METHOD_NOT_FOUND(15102, "Phương thức thanh toán không tồn tại, vui lòng thử lại"),
    ORDER_FAIL_ORDER_HAVE_PLACED(15103, "Đơn hàng đang được xử lý, không thể cập nhật thông tin đơn hàng"),
    ORDER_FIND_NOT_FOUND(15104, "Không tìm thấy đơn hàng , vui lòng kiểm tra mã vận đơn"),

    REVIEW_SUCCESS(16000,"Review sản phẩm thành công"),
    REVIEW_FAIL_ORDER_NOT_COMPLETE_OR_IS_REVIEWED(16100,"Không thể đánh giá sản phẩm này! Đơn hàng chưa hoàn thành hoặc sản phẩm đã được đánh giá."),
    REVIEW_IMAGES_FAIL_UPLOADED(16200,"Không thể tải lên quá 3 ảnh"),

    ACCEPT_ORDER_SUCCESS(17000, "Xác nhận đơn hàng thành công"),
    ACCEPT_ORDER_FAIL_ITEM_OUT_OF_STOCK(17100, "Xử lý đơn hàng thất bại, số lượng trong kho không đủ"),
    ACCEPT_ORDER_FAIL_ORDER_STATUS_HAVE_CHANGED(17101, "Trạng thái đơn hàng đã bị thay đổi" +
            "hoặc trạng thái gửi lên không hợp lệ. Vui lòng tải lại trang"),
    ACCEPT_ORDER_FAIL_ORDER_NOT_FOUND(17102, "Không tìm thấy đơn hàng, vui lòng thử lại"),
    ACCEPT_ORDER_FAIL_DETAIL_ITEM_OUT_OF_STOCK(17103, "Số lượng trong kho không đủ"),

    UPDATE_ORDER_STATUS_SUCCESS(18000, "Cập nhật trạng thái đơn hàng thành công"),
    UPDATE_ORDER_STATUS_FAIL_ORDER_NOT_FOUND(18100, "Không tìm thấy đơn hàng, vui lòng thử lại"),
    UPDATE_ORDER_STATUS_FAIL_ORDER_STATUS_HAVE_CHANGED(18101, "Trạng thái đơn hàng đã bị thay đổi" +
            "hoặc trạng thái gửi lên không hợp lệ. Vui lòng tải lại trang"),

    CANCEL_ORDER_SUCCESS(19000, "Hủy đơn hàng thành công"),
    CANCEL_ORDER_FAIL_ORDER_NOT_FOUND(19100, "Hủy đơn hàng thất bại, không tìm thấy đơn hàng"),
    CANCEL_ORDER_FAIL_ORDER_IS_PROCESSING(19101, "Đơn hàng đang được xử lý, không thể hủy đơn. Vui lòng " +
            "liên hệ Admin để biết thêm thông tin"),
    CANCEL_ORDER_FAIL_USER_IS_NOT_OWNER(19102, "Bạn không sở hữu đơn hàng này")
    ;


    private final int code;
    private final String label;

    Status(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public static int getCode(String value) {
        for (Status eu : Status.values()) {
            if (eu.name().equals(value)) {
                return eu.code();
            }
        }
        return 0;
    }

    public static String getLabel(String value) {
        for (Status eu : Status.values()) {
            if (eu.name().equals(value)) {
                return eu.label();
            }
        }
        return null;
    }

    public static String getLabelFromCode(int code) {
        for (Status eu : Status.values()) {
            if (eu.code() == code) {
                return eu.label();
            }
        }
        return null;
    }

    public int code() {
        return this.code;
    }

    public String label() {
        return this.label;
    }

}