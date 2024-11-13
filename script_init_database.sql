-- Xóa cơ sở dữ liệu nếu đã tồn tại
DROP DATABASE IF EXISTS lgecommerce;

-- Tạo lại cơ sở dữ liệu
CREATE DATABASE lgecommerce;

-- Sử dụng cơ sở dữ liệu vừa tạo
USE lgecommerce;

create table author
(
	author_id varchar(50) not null,
	name varchar(255) null,
	created_at datetime null,
	created_by varchar(50) null,
	updated_at datetime null,
	updated_by varchar(50) null,
	primary key (author_id)
);

create table category
(
	category_id varchar(50) not null,
	name varchar(255) null,
	created_at datetime null,
	created_by varchar(50) null,
	updated_at datetime null,
	updated_by varchar(50) null,
	primary key (category_id)
);

create table payment_method
(
	payment_method_id varchar(50) not null,
	payment_name varchar(255) null,
	created_at datetime null,
	created_by varchar(50) null,
	updated_at datetime null,
	updated_by varchar(50) null,
	primary key (payment_method_id)
);

create table property
(
	property_id varchar(50) not null,
	name varchar(255) null,
	created_at datetime null,
	created_by varchar(50) null,
	updated_at datetime null,
	updated_by varchar(50) null,
	primary key (property_id)
);

create table publisher
(
	publisher_id varchar(50) not null,
	name varchar(255) null,
	created_at datetime null,
	created_by varchar(50) null,
	updated_at datetime null,
	updated_by varchar(50) null,
	primary key (publisher_id)
);

create table book
(
	book_id varchar(50) not null,
	title varchar(255) null,
	thumbnail varchar(255) null,
	description text null,
	quantity int null,
	publisher_id varchar(50) null,
	created_at datetime null,
	created_by varchar(50) null,
	updated_at datetime null,
	updated_by varchar(50) null,
	status bit null,
	primary key (book_id),
	constraint book_ibfk_1
		foreign key (publisher_id) references publisher (publisher_id)
);

create index publisher_id
	on book (publisher_id);

create table book_author
(
	book_author_id varchar(50) not null,
	book_id varchar(50) null,
	author_id varchar(50) null,
	created_at datetime null,
	created_by varchar(50) null,
	updated_at datetime null,
	updated_by varchar(50) null,
	primary key (book_author_id),
	constraint book_author_ibfk_1
		foreign key (book_id) references book (book_id),
	constraint book_author_ibfk_2
		foreign key (author_id) references author (author_id)
);

create index author_id
	on book_author (author_id);

create index book_id
	on book_author (book_id);

create table book_category
(
	book_category_id varchar(50) not null,
	book_id varchar(50) null,
	category_id varchar(50) null,
	created_at datetime null,
	created_by varchar(50) null,
	updated_at datetime null,
	updated_by varchar(50) null,
	primary key (book_category_id),
	constraint book_category_ibfk_1
		foreign key (book_id) references book (book_id),
	constraint book_category_ibfk_2
		foreign key (category_id) references category (category_id)
);

create index book_id
	on book_category (book_id);

create index category_id
	on book_category (category_id);

create table book_image
(
	book_image_id varchar(50) not null,
	book_id varchar(50) null,
	image_url varchar(255) null,
	created_at datetime null,
	created_by varchar(50) null,
	updated_at datetime null,
	updated_by varchar(50) null,
	primary key (book_image_id),
	constraint book_image_ibfk_1
		foreign key (book_id) references book (book_id)
);

create index book_id
	on book_image (book_id);

create table book_property
(
	book_property_id varchar(50) not null,
	book_id varchar(50) null,
	property_id varchar(50) null,
	value varchar(255) null,
	created_at datetime null,
	created_by varchar(50) null,
	updated_at datetime null,
	updated_by varchar(50) null,
	primary key (book_property_id),
	constraint book_property_ibfk_1
		foreign key (book_id) references book (book_id),
	constraint book_property_ibfk_2
		foreign key (property_id) references property (property_id)
);

create index book_id
	on book_property (book_id);

create index property_id
	on book_property (property_id);

create table price
(
	price_id varchar(50) not null,
	base_price double null,
	discount_price double null,
	created_at datetime null,
	created_by varchar(50) null,
	updated_at datetime null,
	updated_by varchar(50) null,
	book_id varchar(50) null,
	primary key (price_id),
	constraint price_ibfk_1
		foreign key (book_id) references book (book_id)
);

create index book_id
	on price (book_id);

create table role
(
	role_id varchar(50) not null,
	name varchar(255) null,
	created_at datetime null,
	created_by varchar(50) null,
	updated_at datetime null,
	updated_by varchar(50) null,
	primary key (role_id)
);

CREATE TABLE account (
    account_id VARCHAR(50) NOT NULL,
    username VARCHAR(255) NULL,
    password VARCHAR(255) NULL,
    email VARCHAR(255) NULL,
    role_id VARCHAR(50) NULL,
    status INT NULL,
    created_at DATETIME NULL,
    created_by VARCHAR(50) NULL,
    updated_at DATETIME NULL,
    updated_by VARCHAR(50) NULL,
    PRIMARY KEY (account_id),
    CONSTRAINT account_ibfk_1 FOREIGN KEY (role_id) REFERENCES role (role_id)
);

CREATE INDEX role_id ON account (role_id);

CREATE TABLE profile (
    profile_id VARCHAR(50) NOT NULL,
    fullname VARCHAR(255) NULL,
    avatar VARCHAR(255) NULL,
    phone VARCHAR(50) NULL,
    created_at DATETIME NULL,
    created_by VARCHAR(50) NULL,
    updated_at DATETIME NULL,
    updated_by VARCHAR(50) NULL,
	account_id VARCHAR(50) NULL,
    PRIMARY KEY (profile_id),
    CONSTRAINT profile_ibfk_1 FOREIGN KEY (account_id) REFERENCES account (account_id)
);


CREATE TABLE address (
    address_id VARCHAR(50) NOT NULL,
    city VARCHAR(30) NULL,
    province VARCHAR(30) NULL,
    district VARCHAR(30) NULL,
    created_at DATETIME NULL,
    updated_at DATETIME NULL,
    created_by VARCHAR(50) NULL,
    updated_by VARCHAR(50) NULL,
	account_id VARCHAR(50) NULL,
    PRIMARY KEY (address_id),
    CONSTRAINT address_ibfk_1 FOREIGN KEY (account_id) REFERENCES account (account_id)
);

-- Tạo chỉ mục cho các cột created_by và updated_by
CREATE INDEX idx_account_id ON address (account_id);


create table review
(
	review_id varchar(50) not null,
	book_id varchar(50) null,
	rating int null,
	comment varchar(255) null,
    is_deleted boolean null,
	created_at datetime null,
	created_by varchar(50) null,
	updated_at datetime null,
	updated_by varchar(50) null,
	account_id VARCHAR(50) NULL,
	primary key (review_id),
	constraint review_ibfk_1
		foreign key (book_id) references book (book_id),
	constraint review_ibfk_2
		foreign key (account_id) references account (account_id)
);

create index book_id
	on review (book_id);

create index account_id
	on review (account_id);

create table review_image
(
	review_image_id varchar(50) not null,
	review_id varchar(50) null,
	image_url varchar(255) null,
	created_at datetime null,
	created_by varchar(50) null,
	updated_at datetime null,
	updated_by varchar(50) null,
	primary key (review_image_id),
	constraint review_image_ibfk_1
		foreign key (review_id) references review (review_id)
);

create index review_id
	on review_image (review_id);

create table shipping_method
(
	shipping_method_id varchar(50) not null,
	shipping_name varchar(255) null,
	created_at datetime null,
	created_by varchar(50) null,
	updated_at datetime null,
	updated_by varchar(50) null,
	primary key (shipping_method_id)
);

create table status
(
	status_id varchar(50) not null,
	status_name varchar(255) null,
	description varchar(255) null,
	created_at datetime null,
	created_by varchar(50) null,
	updated_at datetime null,
	updated_by varchar(50) null,
	primary key (status_id)
);

CREATE TABLE `order` (
    order_id VARCHAR(50) NOT NULL,
    recipient VARCHAR(255) NULL,
    email VARCHAR(320) NULL,
    phone VARCHAR(10) NULL,
    total_amount DOUBLE NULL,
    shipping_fee DOUBLE NULL,
    detail_address VARCHAR(255) NULL,
    is_deleted BOOLEAN NULL,
    created_at DATETIME NULL,
    updated_at DATETIME NULL,
    created_by VARCHAR(50) NULL,
    updated_by VARCHAR(50) NULL,
    shipping_id VARCHAR(50) NULL,
    payment_id VARCHAR(50) NULL,
    status_id VARCHAR(50) NULL,
	account_id VARCHAR(50) NULL,
    PRIMARY KEY (order_id),
    CONSTRAINT order_ibfk_1 FOREIGN KEY (account_id) REFERENCES account (account_id),
    CONSTRAINT order_ibfk_3 FOREIGN KEY (shipping_id) REFERENCES shipping_method (shipping_method_id),
    CONSTRAINT order_ibfk_4 FOREIGN KEY (payment_id) REFERENCES payment_method (payment_method_id),
    CONSTRAINT order_ibfk_5 FOREIGN KEY (status_id) REFERENCES status (status_id)
);

-- Tạo các chỉ mục cho bảng `order`
CREATE INDEX idx_payment_id ON `order` (payment_id);
CREATE INDEX idx_shipping_id ON `order` (shipping_id);
CREATE INDEX idx_status_id ON `order` (status_id);

CREATE TABLE order_detail (
    order_detail_id VARCHAR(50) NOT NULL,
    quantity INT NULL,
    base_price DOUBLE NULL,
    discount_price DOUBLE NULL,
    final_price DOUBLE NULL,
    is_reviewed BOOLEAN NULL,
    created_at DATETIME NULL,
    updated_at DATETIME NULL,
    created_by VARCHAR(50) NULL,
    updated_by VARCHAR(50) NULL,
    order_id VARCHAR(50) NULL,
    book_id VARCHAR(50) NULL,
    PRIMARY KEY (order_detail_id),
    CONSTRAINT order_detail_ibfk_1 FOREIGN KEY (order_id) REFERENCES `order` (order_id),
    CONSTRAINT order_detail_ibfk_2 FOREIGN KEY (book_id) REFERENCES book (book_id)
);

-- Tạo các chỉ mục cho bảng `order_detail`
CREATE INDEX idx_book_id ON order_detail (book_id);
CREATE INDEX idx_order_id ON order_detail (order_id);


