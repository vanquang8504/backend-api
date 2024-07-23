USE master
DROP DATABASE shopapp

CREATE DATABASE shopapp;
USE shopapp;
--Khách hàng khi muốn mua hàng => phải đăng ký tài khoản => bảng users
CREATE TABLE users(
    id INT PRIMARY KEY IDENTITY(1,1),
    fullname NVARCHAR(100) DEFAULT '',
    phone_number NVARCHAR(10) NOT NULL,
    address NVARCHAR(200) DEFAULT '',
    password VARCHAR(100) NOT NULL DEFAULT '',
    created_at DATETIME,
    updated_at DATETIME,
    is_active BIT DEFAULT 1,
    date_of_birth DATE,
    facebook_account_id INT DEFAULT 0,
    google_account_id INT DEFAULT 0
);
ALTER TABLE users ADD role_id INT;

CREATE TABLE roles(
    id INT PRIMARY KEY,
    name VARCHAR(20) NOT NULL 
);
ALTER TABLE users ADD FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TABLE tokens(
    id int PRIMARY KEY IDENTITY(1,1),
    token nvarchar(255) UNIQUE NOT NULL,
    token_type nvarchar(50) NOT NULL,
    expiration_date DATETIME,
    revoked bit NOT NULL,
    expired bit NOT NULL,
    user_id int,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

--hỗ trợ đăng nhập từ Facebook và Google
CREATE TABLE social_accounts(
    id INT PRIMARY KEY IDENTITY(1,1),
    provider NVARCHAR(20) NOT NULL,
    provider_id NVARCHAR(50) NOT NULL,
    email NVARCHAR(150) NOT NULL,
    name NVARCHAR(100) NOT NULL,
    user_id int,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

--Bảng danh mục sản phẩm(Category)
CREATE TABLE categories(
    id INT PRIMARY KEY IDENTITY(1,1),
    name Nvarchar(100) NOT NULL DEFAULT ''
);

--Bảng chứa sản phẩm(Product): "laptop macbook air 15 inch 2023", iphone 15 pro,...
CREATE TABLE products (
    id INT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(350),
    price FLOAT NOT NULL CHECK (price >= 0),
    thumbnail VARCHAR(300) DEFAULT '',
    description NVARCHAR(300) DEFAULT '',
    created_at DATETIME,
    updated_at DATETIME,
    category_id INT,
    FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE product_images(
	id INT IDENTITY(1,1),
	product_id INT,
	image_url varchar(300),
	FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE ON UPDATE CASCADE
)
--Đặt hàng - orders
CREATE TABLE orders(
    id INT PRIMARY KEY IDENTITY(1,1),
    user_id int,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
    fullname VARCHAR(100) DEFAULT '',
    email VARCHAR(100) DEFAULT '',
    phone_number VARCHAR(20) NOT NULL,
    address NVARCHAR(200) NOT NULL,
    note NVARCHAR(100) DEFAULT '',
    order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    status NVARCHAR(20),
    total_money FLOAT CHECK(total_money >= 0)
);

ALTER TABLE orders ADD shipping_method NVARCHAR(100);
ALTER TABLE orders ADD shipping_address NVARCHAR(200);
ALTER TABLE orders ADD shipping_date DATE;
ALTER TABLE orders ADD tracking_number NVARCHAR(100);
ALTER TABLE orders ADD payment_method NVARCHAR(100);
--xóa 1 đơn hàng => xóa mềm => thêm trường active
ALTER TABLE orders ADD active BIT;
--Trạng thái đơn hàng chỉ đc phép nhận "một số giá trị cụ thể"
ALTER TABLE orders 
ALTER COLUMN status VARCHAR(20) NOT NULL
GO

ALTER TABLE orders
ADD CHECK (status IN ('pending', 'processing', 'shipped', 'delivered', 'cancelled'))
GO

CREATE TABLE order_details(
    id INT PRIMARY KEY IDENTITY(1,1),
    order_id INT,
    FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE ON UPDATE CASCADE,
    product_id INT,
    FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE ON UPDATE CASCADE,
    price FLOAT CHECK(price >= 0),
    number_of_products INT CHECK(number_of_products > 0),
    total_money FLOAT CHECK(total_money >= 0),
    color VARCHAR(20) DEFAULT ''
);


select * from categories
select * from products
select * from product_images
select * from users
select * from roles
select * from tokens
select * from social_accounts
select * from orders
select * from order_details
