-- Bookstore Initial Schema
CREATE TYPE user_role_enum AS ENUM ('Admin', 'Customer');
CREATE TYPE order_status_enum AS ENUM('Pending', 'Shipped', 'Cancelled');


CREATE TABLE Author (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    bio TEXT
);

CREATE TABLE Genre (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE Book (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author_id BIGINT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    stock INT NOT NULL,
    isbn VARCHAR(20) NOT NULL UNIQUE,
    published_date DATE NOT NULL,
    CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES Author (id) ON DELETE CASCADE
);

CREATE TABLE Book_Author (
    book_id INT,
    author_id INT,
    PRIMARY KEY (book_id, author_id),
    FOREIGN KEY (book_id) REFERENCES Book(id),
    FOREIGN KEY (author_id) REFERENCES Author(id)
);

CREATE TABLE Book_Genre (
    book_id BIGINT NOT NULL,
    genre_id BIGINT NOT NULL,
    PRIMARY KEY (book_id, genre_id),
    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES Book (id) ON DELETE CASCADE,
    CONSTRAINT fk_genre FOREIGN KEY (genre_id) REFERENCES Genre (id) ON DELETE CASCADE
);

CREATE TABLE App_User (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role user_role_enum NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE Cart (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    total_price DECIMAL(10, 2) NOT NULL DEFAULT 0.0,
    CONSTRAINT fk_cart_user FOREIGN KEY (user_id) REFERENCES App_User (id) ON DELETE CASCADE
);

CREATE TABLE Cart_Item (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    cart_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    CONSTRAINT fk_cart FOREIGN KEY (cart_id) REFERENCES Cart (id) ON DELETE CASCADE,
    CONSTRAINT fk_cart_item_book FOREIGN KEY (book_id) REFERENCES Book (id) ON DELETE CASCADE
);

CREATE TABLE purchase_order (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id BIGINT NOT NULL,
    total_cost DECIMAL(10, 2) NOT NULL,
    status order_status_enum NOT NULL DEFAULT 'Pending',
    placed_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_order_user FOREIGN KEY (user_id) REFERENCES App_User (id) ON DELETE CASCADE
);

CREATE TABLE Order_Item (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    order_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES purchase_order (id) ON DELETE CASCADE,
    CONSTRAINT fk_order_item_book FOREIGN KEY (book_id) REFERENCES Book (id) ON DELETE CASCADE
);

-- Sample Data
-- Authors
INSERT INTO Author (name, bio) VALUES ('Paulo Coelho', 'Brazilian author known for The Alchemist.');
INSERT INTO Author (name, bio) VALUES ('J.K. Rowling', 'British author known for Harry Potter series.');

-- Genres
INSERT INTO Genre (name) VALUES ('Fiction');
INSERT INTO Genre (name) VALUES ('Drama');
INSERT INTO Genre (name) VALUES ('Fantasy');

-- Books
INSERT INTO Book (title, author_id, price, stock, isbn, published_date)
VALUES ('The Alchemist', 1, 499.99, 10, '978-3-16-148410-0', '2024-01-01');

INSERT INTO Book (title, author_id, price, stock, isbn, published_date)
VALUES ('Harry Potter and the Sorcerer''s Stone', 2, 899.99, 15, '978-0-7475-3269-9', '1997-06-26');

-- Book-Genre Relationships
INSERT INTO Book_Genre (book_id, genre_id) VALUES (1, 1); -- The Alchemist - Fiction
INSERT INTO Book_Genre (book_id, genre_id) VALUES (1, 2); -- The Alchemist - Drama
INSERT INTO Book_Genre (book_id, genre_id) VALUES (2, 1); -- Harry Potter - Fiction
INSERT INTO Book_Genre (book_id, genre_id) VALUES (2, 3); -- Harry Potter - Fantasy

-- User
INSERT INTO App_User (username, password, role, email) VALUES ('admin', 'hashed_password', 'Admin', 'admin@bookstore.com');
INSERT INTO App_User (username, password, role, email) VALUES ('john_doe', 'hashed_password', 'Customer', 'john@example.com');

-- Cart
INSERT INTO Cart (user_id, total_price) VALUES (2, 0.0); -- john_doe's cart

-- Orders
INSERT INTO purchase_order (user_id, total_cost, status) VALUES (2, 2499.95, 'Pending');

-- Order Items
INSERT INTO Order_Item (order_id, book_id, quantity, price) VALUES (1, 1, 2, 999.98); -- 2 Alchemists
INSERT INTO Order_Item (order_id, book_id, quantity, price) VALUES (1, 2, 1, 899.99); -- 1 Harry Potter
