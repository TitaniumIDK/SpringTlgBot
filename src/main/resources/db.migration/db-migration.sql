--DROP TABLE Roles CASCADE;
DROP TABLE Vehicles CASCADE;
DROP TABLE Employees CASCADE;
DROP TABLE SpareParts CASCADE;
DROP TABLE Orders CASCADE;
DROP TABLE Schedule CASCADE;
DROP TABLE users;

CREATE TABLE users
(
    id   BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Таблица для работников
CREATE TABLE Employees
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(50) NOT NULL,
    surname    VARCHAR(50) NOT NULL,
    date_start DATE        NOT NULL,
    role       VARCHAR(50) NOT NULL,
    salary     NUMERIC(10, 2)
);

-- Таблица для транспортных средств
CREATE TABLE Vehicles
(
    id         SERIAL PRIMARY KEY,
    brand      VARCHAR(50) NOT NULL,
    model      VARCHAR(50) NOT NULL,
    generation INTEGER     NOT NULL
);

-- Таблица для запчастей
CREATE TABLE SpareParts
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(50) NOT NULL,
    vehicle_id INTEGER     NOT NULL REFERENCES Vehicles (id),
    price_in   NUMERIC(10, 2),
    price_out  NUMERIC(10, 2),
    stock      INTEGER
);

-- Таблица для заказов
CREATE TABLE Orders
(
    id         SERIAL PRIMARY KEY,
    order_date DATE        NOT NULL,
    vehicle_id INTEGER     NOT NULL,
    spare_part INTEGER     NOT NULL,
    manager    INTEGER,
    master     INTEGER     NOT NULL,
    status     VARCHAR(50) NOT NULL,
    FOREIGN KEY (vehicle_id) REFERENCES Vehicles (id),
    FOREIGN KEY (spare_part) REFERENCES SpareParts (id),
    FOREIGN KEY (manager) REFERENCES Employees (id),
    FOREIGN KEY (master) REFERENCES Employees (id)
);

CREATE TABLE Schedule
(
    id           SERIAL PRIMARY KEY,
    emp_id       INTEGER REFERENCES Employees (id),
    work_date    DATE NOT NULL,
    first_third  VARCHAR(50),
    second_third VARCHAR(50),
    third_third  VARCHAR(50)
);

-- Таблица Employees
INSERT INTO Employees (name, surname, date_start, role, salary)
VALUES ('John', 'Doe', '2018-01-01', 'MANAGER', 80000.00),
       ('Jane', 'Smith', '2019-03-15', 'MECHANIC', 65000.00),
       ('Michael', 'Johnson', '2020-05-01', 'MECHANIC', 75000.00),
       ('Emily', 'Brown', '2021-09-01', 'ADMINISTRATOR', 55000.00),
       ('David', 'Wilson', '2022-02-15', 'MANAGER', 60000.00),
       ('Sarah', 'Davis', '2018-06-01', 'ADMINISTRATOR', 52000.00),
       ('Robert', 'Anderson', '2019-11-01', 'MECHANIC', 58000.00),
       ('Jessica', 'Thompson', '2020-04-15', 'MANAGER', 85000.00),
       ('William', 'Lee', '2021-08-01', 'MECHANIC', 62000.00),
       ('Olivia', 'Taylor', '2022-01-01', 'ADMINISTRATOR', 48000.00),
       ('Daniel', 'Nguyen', '2023-03-01', 'MANAGER', 70000.00),
       ('Emma', 'Wilson', '2018-02-15', 'ADMINISTRATOR', 75000.00),
       ('Jacob', 'Davis', '2019-06-01', 'MECHANIC', 80000.00),
       ('Sophia', 'Anderson', '2020-10-01', 'MANAGER', 68000.00),
       ('Alexander', 'Thompson', '2021-03-15', 'ADMINISTRATOR', 55000.00),
       ('Isabella', 'Lee', '2022-08-01', 'MECHANIC', 52000.00),
       ('Ethan', 'Taylor', '2018-09-01', 'MANAGER', 62000.00),
       ('Ava', 'Nguyen', '2019-12-15', 'ADMINISTRATOR', 58000.00),
       ('Benjamin', 'Wilson', '2020-05-01', 'MECHANIC', 60000.00),
       ('Mia', 'Davis', '2021-11-01', 'ADMINISTRATOR', 48000.00),
       ('Lucas', 'Anderson', '2022-02-15', 'MANAGER', 72000.00),
       ('Abigail', 'Thompson', '2018-07-01', 'ADMINISTRATOR', 55000.00),
       ('Mason', 'Lee', '2019-09-15', 'MECHANIC', 85000.00),
       ('Isabella', 'Taylor', '2020-12-01', 'MANAGER', 62000.00),
       ('Liam', 'Nguyen', '2021-04-01', 'ADMINISTRATOR', 58000.00),
       ('Olivia', 'Wilson', '2022-09-15', 'MECHANIC', 52000.00),
       ('Noah', 'Davis', '2018-11-01', 'MANAGER', 80000.00),
       ('Emma', 'Anderson', '2019-02-15', 'MANAGER', 65000.00),
       ('Jacob', 'Thompson', '2020-07-01', 'ADMINISTRATOR', 75000.00),
       ('Sophia', 'Lee', '2021-10-15', 'MECHANIC', 82000.00),
       ('Alexander', 'Taylor', '2022-03-01', 'MANAGER', 70000.00),
       ('Isabella', 'Nguyen', '2018-05-01', 'ADMINISTRATOR', 58000.00),
       ('Ethan', 'Wilson', '2019-08-15', 'MECHANIC', 52000.00),
       ('Ava', 'Davis', '2020-11-01', 'ADMINISTRATOR', 60000.00),
       ('Benjamin', 'Anderson', '2021-01-15', 'MECHANIC', 58000.00),
       ('Mia', 'Thompson', '2022-06-01', 'ADMINISTRATOR', 48000.00),
       ('Lucas', 'Lee', '2018-03-15', 'MANAGER', 72000.00),
       ('Abigail', 'Taylor', '2019-10-01', 'ADMINISTRATOR', 55000.00),
       ('Mason', 'Nguyen', '2020-01-15', 'MECHANIC', 85000.00),
       ('Isabella', 'Wilson', '2021-05-01', 'MANAGER', 62000.00),
       ('Liam', 'Davis', '2022-12-15', 'ADMINISTRATOR', 58000.00),
       ('Olivia', 'Anderson', '2018-04-01', 'MECHANIC', 52000.00),
       ('Noah', 'Thompson', '2019-07-15', 'MANAGER', 80000.00),
       ('Emma', 'Lee', '2020-09-01', 'MANAGER', 65000.00),
       ('Jacob', 'Taylor', '2021-02-15', 'ADMINISTRATOR', 75000.00),
       ('Sophia', 'Nguyen', '2022-08-01', 'MECHANIC', 82000.00);

-- Таблица Vehicles
INSERT INTO Vehicles (brand, model, generation)
VALUES ('Toyota', 'Corolla', 2020),
       ('Honda', 'Accord', 2021),
       ('Ford', 'F-150', 2019),
       ('Chevrolet', 'Silverado', 2022),
       ('Nissan', 'Altima', 2018),
       ('Hyundai', 'Sonata', 2021),
       ('Kia', 'Sportage', 2020),
       ('Subaru', 'Outback', 2019),
       ('BMW', '3 Series', 2022),
       ('Mercedes-Benz', 'C-Class', 2021),
       ('Audi', 'A6', 2023),
       ('Toyota', 'RAV4', 2020),
       ('Honda', 'Pilot', 2021),
       ('Ford', 'Mustang', 2019),
       ('Chevrolet', 'Tahoe', 2022),
       ('Nissan', 'Rogue', 2018),
       ('Hyundai', 'Tucson', 2021),
       ('Kia', 'Sorento', 2020),
       ('Subaru', 'Ascent', 2019),
       ('BMW', '5 Series', 2022),
       ('Mercedes-Benz', 'E-Class', 2021),
       ('Audi', 'Q5', 2023),
       ('Toyota', 'Sienna', 2020),
       ('Honda', 'Odyssey', 2021),
       ('Ford', 'Explorer', 2019),
       ('Chevrolet', 'Traverse', 2022),
       ('Nissan', 'Murano', 2018),
       ('Hyundai', 'Santa Fe', 2021),
       ('Kia', 'Telluride', 2020),
       ('Subaru', 'Forester', 2019),
       ('BMW', 'X5', 2022),
       ('Mercedes-Benz', 'GLC', 2021),
       ('Audi', 'Q7', 2023),
       ('Toyota', 'Highlander', 2020),
       ('Honda', 'CR-V', 2021),
       ('Ford', 'Expedition', 2019),
       ('Chevrolet', 'Blazer', 2022),
       ('Nissan', 'Pathfinder', 2018),
       ('Hyundai', 'Palisade', 2021),
       ('Kia', 'Carnival', 2020),
       ('Subaru', 'Outback', 2019),
       ('BMW', 'X3', 2022),
       ('Mercedes-Benz', 'GLE', 2021),
       ('Audi', 'Q8', 2023),
       ('Toyota', 'Tundra', 2020),
       ('Honda', 'Ridgeline', 2021),
       ('Ford', 'Ranger', 2019),
       ('Chevrolet', 'Colorado', 2022),
       ('Nissan', 'Titan', 2018),
       ('Hyundai', 'Santa Cruz', 2021);

-- Таблица SpareParts
INSERT INTO SpareParts (name, vehicle_id, price_in, price_out, stock)
VALUES
-- Vehicle 1
('Brake Pads', 1, 1500.00, 2000.00, 10),
('Oil Filter', 1, 300.00, 400.00, 15),
('Air Filter', 1, 200.00, 300.00, 20),

-- Vehicle 2
('Brake Pads', 2, 1600.00, 2100.00, 12),
('Oil Filter', 2, 320.00, 420.00, 18),
('Air Filter', 2, 220.00, 320.00, 22),

-- Vehicle 3
('Brake Pads', 3, 1700.00, 2200.00, 14),
('Oil Filter', 3, 340.00, 440.00, 20),
('Air Filter', 3, 240.00, 340.00, 25),

-- Vehicle 4
('Brake Pads', 4, 1800.00, 2300.00, 16),
('Oil Filter', 4, 360.00, 460.00, 22),
('Air Filter', 4, 260.00, 360.00, 30),

-- Vehicle 5
('Brake Pads', 5, 1900.00, 2400.00, 18),
('Oil Filter', 5, 380.00, 480.00, 25),
('Air Filter', 5, 280.00, 380.00, 35),

-- Vehicle 6
('Brake Pads', 6, 2000.00, 2500.00, 20),
('Oil Filter', 6, 400.00, 500.00, 30),
('Air Filter', 6, 300.00, 400.00, 40),

-- Vehicle 7
('Brake Pads', 7, 2100.00, 2600.00, 22),
('Oil Filter', 7, 420.00, 520.00, 35),
('Air Filter', 7, 320.00, 420.00, 45),

-- Vehicle 8
('Brake Pads', 8, 2200.00, 2700.00, 24),
('Oil Filter', 8, 440.00, 540.00, 40),
('Air Filter', 8, 340.00, 440.00, 50),

-- Vehicle 9
('Brake Pads', 9, 2300.00, 2800.00, 26),
('Oil Filter', 9, 460.00, 560.00, 45),
('Air Filter', 9, 360.00, 460.00, 55),

-- Vehicle 10
('Brake Pads', 10, 2400.00, 2900.00, 28),
('Oil Filter', 10, 480.00, 580.00, 50),
('Air Filter', 10, 380.00, 480.00, 60),

-- Vehicle 11
('Brake Pads', 11, 2500.00, 3000.00, 30),
('Oil Filter', 11, 500.00, 600.00, 55),
('Air Filter', 11, 400.00, 500.00, 65),

-- Vehicle 12
('Brake Pads', 12, 2600.00, 3100.00, 32),
('Oil Filter', 12, 520.00, 620.00, 60),
('Air Filter', 12, 420.00, 520.00, 70),

-- Vehicle 13
('Brake Pads', 13, 2700.00, 3200.00, 34),
('Oil Filter', 13, 540.00, 640.00, 65),
('Air Filter', 13, 440.00, 540.00, 75),

-- Vehicle 14
('Brake Pads', 14, 2800.00, 3300.00, 36),
('Oil Filter', 14, 560.00, 660.00, 70),
('Air Filter', 14, 460.00, 560.00, 80),

-- Vehicle 15
('Brake Pads', 15, 2900.00, 3400.00, 38),
('Oil Filter', 15, 580.00, 680.00, 75),
('Air Filter', 15, 480.00, 580.00, 85),

-- Vehicle 16
('Brake Pads', 16, 3000.00, 3500.00, 40),
('Oil Filter', 16, 600.00, 700.00, 80),
('Air Filter', 16, 500.00, 600.00, 90),

-- Vehicle 17
('Brake Pads', 17, 3100.00, 3600.00, 42),
('Oil Filter', 17, 620.00, 720.00, 85),
('Air Filter', 17, 520.00, 620.00, 95),

-- Vehicle 18
('Brake Pads', 18, 3200.00, 3700.00, 44),
('Oil Filter', 18, 640.00, 740.00, 90),
('Air Filter', 18, 540.00, 640.00, 100),

-- Vehicle 19
('Brake Pads', 19, 3300.00, 3800.00, 46),
('Oil Filter', 19, 660.00, 760.00, 95),
('Air Filter', 19, 560.00, 660.00, 105),

-- Vehicle 20
('Brake Pads', 20, 3400.00, 3900.00, 48),
('Oil Filter', 20, 680.00, 780.00, 100),
('Air Filter', 20, 580.00, 680.00, 110),

-- Vehicle 21
('Brake Pads', 21, 3500.00, 4000.00, 50),
('Oil Filter', 21, 700.00, 800.00, 105),
('Air Filter', 21, 600.00, 700.00, 115),

-- Vehicle 22
('Brake Pads', 22, 3600.00, 4100.00, 52),
('Oil Filter', 22, 720.00, 820.00, 110),
('Air Filter', 22, 620.00, 720.00, 120),

-- Vehicle 23
('Brake Pads', 23, 3700.00, 4200.00, 54),
('Oil Filter', 23, 740.00, 840.00, 115),
('Air Filter', 23, 640.00, 740.00, 125),

-- Vehicle 24
('Brake Pads', 24, 3800.00, 4300.00, 56),
('Oil Filter', 24, 760.00, 860.00, 120),
('Air Filter', 24, 660.00, 760.00, 130),

-- Vehicle 25
('Brake Pads', 25, 3900.00, 4400.00, 58),
('Oil Filter', 25, 780.00, 880.00, 125),
('Air Filter', 25, 680.00, 780.00, 135),

-- Vehicle 26
('Brake Pads', 26, 4000.00, 4500.00, 60),
('Oil Filter', 26, 800.00, 900.00, 130),
('Air Filter', 26, 700.00, 800.00, 140),

-- Vehicle 27
('Brake Pads', 27, 4100.00, 4600.00, 62),
('Oil Filter', 27, 820.00, 920.00, 135),
('Air Filter', 27, 720.00, 820.00, 145),

-- Vehicle 28
('Brake Pads', 28, 4200.00, 4700.00, 64),
('Oil Filter', 28, 840.00, 940.00, 140),
('Air Filter', 28, 740.00, 840.00, 150),

-- Vehicle 29
('Brake Pads', 29, 4300.00, 4800.00, 66),
('Oil Filter', 29, 860.00, 960.00, 145),
('Air Filter', 29, 760.00, 860.00, 155),

-- Vehicle 30
('Brake Pads', 30, 4400.00, 4900.00, 68),
('Oil Filter', 30, 880.00, 980.00, 150),
('Air Filter', 30, 780.00, 880.00, 160),

-- Vehicle 31
('Brake Pads', 31, 4500.00, 5000.00, 70),
('Oil Filter', 31, 900.00, 1000.00, 155),
('Air Filter', 31, 800.00, 900.00, 165),

-- Vehicle 32
('Brake Pads', 32, 4600.00, 5100.00, 72),
('Oil Filter', 32, 920.00, 1020.00, 160),
('Air Filter', 32, 820.00, 920.00, 170),

-- Vehicle 33
('Brake Pads', 33, 4700.00, 5200.00, 74),
('Oil Filter', 33, 940.00, 1040.00, 165),
('Air Filter', 33, 840.00, 940.00, 175),

-- Vehicle 34
('Brake Pads', 34, 4800.00, 5300.00, 76),
('Oil Filter', 34, 960.00, 1060.00, 170),
('Air Filter', 34, 860.00, 960.00, 180),

-- Vehicle 35
('Brake Pads', 35, 4900.00, 5400.00, 78),
('Oil Filter', 35, 980.00, 1080.00, 175),
('Air Filter', 35, 880.00, 980.00, 185),

-- Vehicle 36
('Brake Pads', 36, 5000.00, 5500.00, 80),
('Oil Filter', 36, 1000.00, 1100.00, 180),
('Air Filter', 36, 900.00, 1000.00, 190),

-- Vehicle 37
('Brake Pads', 37, 5100.00, 5600.00, 82),
('Oil Filter', 37, 1020.00, 1120.00, 185),
('Air Filter', 37, 920.00, 1020.00, 195),

-- Vehicle 38
('Brake Pads', 38, 5200.00, 5700.00, 84),
('Oil Filter', 38, 1040.00, 1140.00, 190),
('Air Filter', 38, 940.00, 1040.00, 200),

-- Vehicle 39
('Brake Pads', 39, 5300.00, 5800.00, 86),
('Oil Filter', 39, 1060.00, 1160.00, 195),
('Air Filter', 39, 960.00, 1060.00, 205),

-- Vehicle 40
('Brake Pads', 40, 5400.00, 5900.00, 88),
('Oil Filter', 40, 1080.00, 1180.00, 200),
('Air Filter', 40, 980.00, 1080.00, 210),

-- Vehicle 41
('Brake Pads', 41, 5500.00, 6000.00, 90),
('Oil Filter', 41, 1100.00, 1200.00, 205),
('Air Filter', 41, 1000.00, 1100.00, 215),

-- Vehicle 42
('Brake Pads', 42, 5600.00, 6100.00, 92),
('Oil Filter', 42, 1120.00, 1220.00, 210),
('Air Filter', 42, 1020.00, 1120.00, 220),

-- Vehicle 43
('Brake Pads', 43, 5700.00, 6200.00, 94),
('Oil Filter', 43, 1140.00, 1240.00, 215),
('Air Filter', 43, 1040.00, 1140.00, 225),

-- Vehicle 44
('Brake Pads', 44, 5800.00, 6300.00, 96),
('Oil Filter', 44, 1160.00, 1260.00, 220),
('Air Filter', 44, 1060.00, 1160.00, 230),

-- Vehicle 45
('Brake Pads', 45, 5900.00, 6400.00, 98),
('Oil Filter', 45, 1180.00, 1280.00, 225),
('Air Filter', 45, 1080.00, 1180.00, 235),

-- Vehicle 46
('Brake Pads', 46, 6000.00, 6500.00, 100),
('Oil Filter', 46, 1200.00, 1300.00, 230),
('Air Filter', 46, 1100.00, 1200.00, 240),

-- Vehicle 47
('Brake Pads', 47, 6100.00, 6600.00, 102),
('Oil Filter', 47, 1220.00, 1320.00, 235),
('Air Filter', 47, 1120.00, 1220.00, 245),

-- Vehicle 48
('Brake Pads', 48, 6200.00, 6700.00, 104),
('Oil Filter', 48, 1240.00, 1340.00, 240),
('Air Filter', 48, 1140.00, 1240.00, 250),

-- Vehicle 49
('Brake Pads', 49, 6300.00, 6800.00, 106),
('Oil Filter', 49, 1260.00, 1360.00, 245),
('Air Filter', 49, 1160.00, 1260.00, 255),

-- Vehicle 50
('Brake Pads', 50, 6400.00, 6900.00, 108),
('Oil Filter', 50, 1280.00, 1380.00, 250),
('Air Filter', 50, 1180.00, 1280.00, 260);

-- Таблица Orders
INSERT INTO Orders (order_date, vehicle_id, spare_part, manager, master, status)
VALUES ('2023-01-01', 1, 1, 1, 2, 'FINISHED'),
       ('2023-01-15', 2, 2, 1, 3, 'IN_PROCESS'),
       ('2023-02-01', 3, 3, 2, 2, 'NEW'),
       ('2023-02-15', 4, 4, 1, 1, 'FINISHED'),
       ('2023-03-01', 5, 5, 3, 3, 'IN_PROCESS'),
       ('2023-03-15', 6, 6, 2, 2, 'NEW'),
       ('2023-04-01', 7, 7, 1, 1, 'FINISHED'),
       ('2023-04-15', 8, 8, 3, 3, 'IN_PROCESS'),
       ('2023-05-01', 9, 9, 2, 2, 'NEW'),
       ('2023-05-15', 10, 10, 1, 1, 'FINISHED'),
       ('2023-06-01', 11, 11, 3, 3, 'IN_PROCESS'),
       ('2023-06-15', 12, 12, 2, 2, 'NEW'),
       ('2023-07-01', 13, 13, 1, 1, 'FINISHED'),
       ('2023-07-15', 14, 14, 3, 3, 'IN_PROCESS'),
       ('2023-08-01', 15, 15, 2, 2, 'NEW'),
       ('2023-08-15', 16, 16, 1, 1, 'FINISHED'),
       ('2023-09-01', 17, 17, 3, 3, 'IN_PROCESS'),
       ('2023-09-15', 18, 18, 2, 2, 'NEW'),
       ('2023-10-01', 19, 19, 1, 1, 'FINISHED'),
       ('2023-10-15', 20, 20, 3, 3, 'IN_PROCESS'),
       ('2023-11-01', 21, 21, 2, 2, 'NEW'),
       ('2023-11-15', 22, 22, 1, 1, 'FINISHED'),
       ('2023-12-01', 23, 23, 3, 3, 'IN_PROCESS'),
       ('2023-12-15', 24, 24, 2, 2, 'NEW'),
       ('2024-01-01', 25, 25, 1, 1, 'FINISHED'),
       ('2024-01-15', 26, 26, 3, 3, 'IN_PROCESS'),
       ('2024-02-01', 27, 27, 2, 2, 'NEW'),
       ('2024-02-15', 28, 28, 1, 1, 'FINISHED'),
       ('2024-03-01', 29, 29, 3, 3, 'IN_PROCESS'),
       ('2024-03-15', 30, 30, 2, 2, 'NEW'),
       ('2024-04-01', 31, 31, 1, 1, 'FINISHED'),
       ('2024-04-15', 32, 32, 3, 3, 'IN_PROCESS'),
       ('2024-05-01', 33, 33, 2, 2, 'NEW'),
       ('2024-05-15', 34, 34, 1, 1, 'FINISHED'),
       ('2024-06-01', 35, 35, 3, 3, 'IN_PROCESS'),
       ('2024-06-15', 36, 36, 2, 2, 'NEW'),
       ('2024-07-01', 37, 37, 1, 1, 'FINISHED'),
       ('2024-07-15', 38, 38, 3, 3, 'IN_PROCESS'),
       ('2024-08-01', 39, 39, 2, 2, 'NEW'),
       ('2024-08-15', 40, 40, 1, 1, 'FINISHED'),
       ('2024-09-01', 41, 41, 3, 3, 'IN_PROCESS'),
       ('2024-09-15', 42, 42, 2, 2, 'NEW'),
       ('2024-10-01', 43, 43, 1, 1, 'FINISHED'),
       ('2024-10-15', 44, 44, 3, 3, 'IN_PROCESS'),
       ('2024-11-01', 45, 45, 2, 2, 'NEW'),
       ('2024-11-15', 46, 46, 1, 1, 'FINISHED'),
       ('2024-12-01', 47, 47, 3, 3, 'IN_PROCESS'),
       ('2024-12-15', 48, 48, 2, 2, 'NEW'),
       ('2025-01-01', 49, 49, 1, 1, 'FINISHED'),
       ('2025-01-15', 50, 50, 3, 3, 'IN_PROCESS');

INSERT INTO Schedule (emp_id, work_date, first_third, second_third, third_third)
VALUES (1, '2023-04-01', 'BUSY', 'FREE', 'BUSY'),
       (1, '2023-04-02', 'SICK', 'FREE', 'BUSY'),
       (1, '2023-04-03', 'BUSY', 'FREE', 'DAYOFF'),
       (2, '2023-04-01', 'FREE', 'BUSY', 'BUSY'),
       (2, '2023-04-02', 'BUSY', 'FREE', 'SICK'),
       (2, '2023-04-03', 'BUSY', 'DAYOFF', 'FREE'),
       (3, '2023-04-01', 'BUSY', 'BUSY', 'FREE'),
       (3, '2023-04-02', 'FREE', 'SICK', 'BUSY'),
       (3, '2023-04-03', 'BUSY', 'FREE', 'DAYOFF'),
       (4, '2023-04-01', 'FREE', 'BUSY', 'DAYOFF'),
       (4, '2023-04-02', 'BUSY', 'FREE', 'SICK'),
       (4, '2023-04-03', 'BUSY', 'FREE', 'BUSY'),
       (5, '2023-04-01', 'BUSY', 'FREE', 'BUSY'),
       (5, '2023-04-02', 'SICK', 'BUSY', 'FREE'),
       (5, '2023-04-03', 'FREE', 'BUSY', 'DAYOFF'),
       (6, '2023-04-01', 'FREE', 'BUSY', 'SICK'),
       (6, '2023-04-02', 'BUSY', 'FREE', 'DAYOFF'),
       (6, '2023-04-03', 'BUSY', 'FREE', 'BUSY'),
       (7, '2023-04-01', 'BUSY', 'FREE', 'BUSY'),
       (7, '2023-04-02', 'SICK', 'FREE', 'DAYOFF'),
       (7, '2023-04-03', 'FREE', 'BUSY', 'BUSY'),
       (8, '2023-04-01', 'FREE', 'BUSY', 'SICK'),
       (8, '2023-04-02', 'BUSY', 'FREE', 'DAYOFF'),
       (8, '2023-04-03', 'SICK', 'BUSY', 'FREE'),
       (9, '2023-04-01', 'BUSY', 'FREE', 'BUSY'),
       (9, '2023-04-02', 'FREE', 'BUSY', 'DAYOFF'),
       (9, '2023-04-03', 'SICK', 'FREE', 'BUSY'),
       (10, '2023-04-01', 'FREE', 'BUSY', 'DAYOFF'),
       (10, '2023-04-02', 'BUSY', 'FREE', 'SICK'),
       (10, '2023-04-03', 'BUSY', 'FREE', 'DAYOFF'),
       (11, '2023-04-01', 'BUSY', 'FREE', 'DAYOFF'),
       (11, '2023-04-02', 'SICK', 'BUSY', 'FREE'),
       (11, '2023-04-03', 'FREE', 'BUSY', 'SICK'),
       (12, '2023-04-01', 'FREE', 'BUSY', 'SICK'),
       (12, '2023-04-02', 'BUSY', 'FREE', 'DAYOFF'),
       (12, '2023-04-03', 'SICK', 'FREE', 'BUSY'),
       (13, '2023-04-01', 'BUSY', 'FREE', 'DAYOFF'),
       (13, '2023-04-02', 'FREE', 'BUSY', 'SICK'),
       (13, '2023-04-03', 'BUSY', 'FREE', 'BUSY'),
       (14, '2023-04-01', 'SICK', 'FREE', 'DAYOFF'),
       (14, '2023-04-02', 'BUSY', 'FREE', 'BUSY'),
       (14, '2023-04-03', 'FREE', 'BUSY', 'SICK'),
       (15, '2023-04-01', 'BUSY', 'FREE', 'DAYOFF'),
       (15, '2023-04-02', 'SICK', 'BUSY', 'FREE'),
       (15, '2023-04-03', 'FREE', 'BUSY', 'DAYOFF'),
       (16, '2023-04-01', 'FREE', 'BUSY', 'SICK'),
       (16, '2023-04-02', 'BUSY', 'FREE', 'DAYOFF'),
       (16, '2023-04-03', 'SICK', 'FREE', 'BUSY'),
       (17, '2023-04-01', 'BUSY', 'FREE', 'DAYOFF'),
       (17, '2023-04-02', 'FREE', 'BUSY', 'SICK'),
       (17, '2023-04-03', 'BUSY', 'FREE', 'BUSY'),
       (18, '2023-04-01', 'SICK', 'FREE', 'DAYOFF'),
       (18, '2023-04-02', 'BUSY', 'FREE', 'BUSY'),
       (18, '2023-04-03', 'FREE', 'BUSY', 'SICK'),
       (19, '2023-04-01', 'BUSY', 'FREE', 'DAYOFF'),
       (19, '2023-04-02', 'SICK', 'BUSY', 'FREE'),
       (19, '2023-04-03', 'FREE', 'BUSY', 'DAYOFF'),
       (20, '2023-04-01', 'FREE', 'BUSY', 'SICK'),
       (20, '2023-04-02', 'BUSY', 'FREE', 'DAYOFF'),
       (20, '2023-04-03', 'SICK', 'FREE', 'BUSY'),
       (21, '2023-04-01', 'BUSY', 'FREE', 'DAYOFF'),
       (21, '2023-04-02', 'FREE', 'BUSY', 'SICK'),
       (21, '2023-04-03', 'BUSY', 'FREE', 'BUSY'),
       (22, '2023-04-01', 'SICK', 'FREE', 'DAYOFF'),
       (22, '2023-04-02', 'BUSY', 'FREE', 'BUSY'),
       (22, '2023-04-03', 'FREE', 'BUSY', 'SICK'),
       (23, '2023-04-01', 'BUSY', 'FREE', 'DAYOFF'),
       (23, '2023-04-02', 'SICK', 'BUSY', 'FREE'),
       (23, '2023-04-03', 'FREE', 'BUSY', 'DAYOFF'),
       (24, '2023-04-01', 'FREE', 'BUSY', 'SICK'),
       (24, '2023-04-02', 'BUSY', 'FREE', 'DAYOFF'),
       (24, '2023-04-03', 'SICK', 'FREE', 'BUSY'),
       (25, '2023-04-01', 'BUSY', 'FREE', 'DAYOFF'),
       (25, '2023-04-02', 'SICK', 'BUSY', 'FREE'),
       (25, '2023-04-03', 'FREE', 'BUSY', 'DAYOFF'),
       (26, '2023-04-01', 'FREE', 'BUSY', 'SICK'),
       (26, '2023-04-02', 'BUSY', 'FREE', 'DAYOFF'),
       (26, '2023-04-03', 'SICK', 'FREE', 'BUSY'),
       (27, '2023-04-01', 'BUSY', 'FREE', 'DAYOFF'),
       (27, '2023-04-02', 'FREE', 'BUSY', 'SICK'),
       (27, '2023-04-03', 'BUSY', 'FREE', 'BUSY'),
       (28, '2023-04-01', 'SICK', 'FREE', 'DAYOFF'),
       (28, '2023-04-02', 'BUSY', 'FREE', 'BUSY'),
       (28, '2023-04-03', 'FREE', 'BUSY', 'SICK'),
       (29, '2023-04-01', 'BUSY', 'FREE', 'DAYOFF'),
       (29, '2023-04-02', 'SICK', 'BUSY', 'FREE'),
       (29, '2023-04-03', 'FREE', 'BUSY', 'DAYOFF'),
       (30, '2023-04-01', 'FREE', 'BUSY', 'SICK'),
       (30, '2023-04-02', 'BUSY', 'FREE', 'DAYOFF'),
       (30, '2023-04-03', 'SICK', 'FREE', 'BUSY'),
       (31, '2023-04-01', 'BUSY', 'FREE', 'DAYOFF'),
       (31, '2023-04-02', 'FREE', 'BUSY', 'SICK'),
       (31, '2023-04-03', 'BUSY', 'FREE', 'BUSY'),
       (32, '2023-04-01', 'SICK', 'FREE', 'DAYOFF'),
       (32, '2023-04-02', 'BUSY', 'FREE', 'BUSY'),
       (32, '2023-04-03', 'FREE', 'BUSY', 'SICK'),
       (33, '2023-04-01', 'BUSY', 'FREE', 'DAYOFF'),
       (33, '2023-04-02', 'SICK', 'BUSY', 'FREE'),
       (33, '2023-04-03', 'FREE', 'BUSY', 'DAYOFF'),
       (34, '2023-04-01', 'FREE', 'BUSY', 'SICK'),
       (34, '2023-04-02', 'BUSY', 'FREE', 'DAYOFF'),
       (34, '2023-04-03', 'SICK', 'FREE', 'BUSY'),
       (35, '2023-04-01', 'BUSY', 'FREE', 'DAYOFF'),
       (35, '2023-04-02', 'SICK', 'BUSY', 'FREE'),
       (35, '2023-04-03', 'FREE', 'BUSY', 'DAYOFF'),
       (36, '2023-04-01', 'FREE', 'BUSY', 'SICK'),
       (36, '2023-04-02', 'BUSY', 'FREE', 'DAYOFF'),
       (36, '2023-04-03', 'SICK', 'FREE', 'BUSY'),
       (37, '2023-04-01', 'BUSY', 'FREE', 'DAYOFF'),
       (37, '2023-04-02', 'FREE', 'BUSY', 'SICK'),
       (37, '2023-04-03', 'BUSY', 'FREE', 'BUSY'),
       (38, '2023-04-01', 'SICK', 'FREE', 'DAYOFF'),
       (38, '2023-04-02', 'BUSY', 'FREE', 'BUSY'),
       (38, '2023-04-03', 'FREE', 'BUSY', 'SICK'),
       (39, '2023-04-01', 'BUSY', 'FREE', 'DAYOFF'),
       (39, '2023-04-02', 'SICK', 'BUSY', 'FREE'),
       (39, '2023-04-03', 'FREE', 'BUSY', 'DAYOFF'),
       (40, '2023-04-01', 'FREE', 'BUSY', 'SICK'),
       (40, '2023-04-02', 'BUSY', 'FREE', 'DAYOFF'),
       (40, '2023-04-03', 'SICK', 'FREE', 'BUSY'),
       (41, '2023-04-01', 'BUSY', 'FREE', 'DAYOFF'),
       (41, '2023-04-02', 'FREE', 'BUSY', 'SICK'),
       (41, '2023-04-03', 'BUSY', 'FREE', 'BUSY'),
       (42, '2023-04-01', 'SICK', 'FREE', 'DAYOFF'),
       (42, '2023-04-02', 'BUSY', 'FREE', 'BUSY'),
       (42, '2023-04-03', 'FREE', 'BUSY', 'SICK'),
       (43, '2023-04-01', 'BUSY', 'FREE', 'DAYOFF'),
       (43, '2023-04-02', 'SICK', 'BUSY', 'FREE'),
       (43, '2023-04-03', 'FREE', 'BUSY', 'DAYOFF'),
       (44, '2023-04-01', 'FREE', 'BUSY', 'SICK'),
       (44, '2023-04-02', 'BUSY', 'FREE', 'DAYOFF'),
       (44, '2023-04-03', 'SICK', 'FREE', 'BUSY'),
       (45, '2023-04-01', 'BUSY', 'FREE', 'DAYOFF'),
       (45, '2023-04-02', 'SICK', 'BUSY', 'FREE'),
       (45, '2023-04-03', 'FREE', 'BUSY', 'DAYOFF'),
       (46, '2023-04-01', 'FREE', 'BUSY', 'SICK'),
       (46, '2023-04-02', 'BUSY', 'FREE', 'DAYOFF'),
       (46, '2023-04-03', 'SICK', 'FREE', 'BUSY');



-- Добавление данных в таблицу Employees
-- INSERT INTO Employees (name, surname, date_start, role, salary)
-- VALUES ('Ivan', 'Ivanov', '2020-01-01', 'MECHANIC', 50000.00),
--        ('Petr', 'Petrov', '2021-03-15', 'MECHANIC', 60000.00),
--        ('Maria', 'Sidorova', '2022-07-01', 'ADMINISTRATOR', 45000.00);
--
-- -- Добавление данных в таблицу Vehicles
-- INSERT INTO Vehicles (brand, model, generation)
-- VALUES ('Toyota', 'Camry', 2019),
--        ('Honda', 'Civic', 2021),
--        ('Volkswagen', 'Polo', 2018);
--
-- -- Добавление данных в таблицу SpareParts
-- INSERT INTO SpareParts (name, vehicle_id, price_in, price_out, stock)
-- VALUES ('Friction brakes', 1, 500.00, 800.00, 20),
--        ('Oil filter', 2, 150.00, 250.00, 15),
--        ('Engine', 3, 800.00, 1200.00, 10),
--        ('Remen GRM', 1, 500.00, 800.00, 20);
--
-- -- Добавление данных в таблицу Orders
-- INSERT INTO Orders (order_date, vehicle_id, spare_part, manager, master, status)
-- VALUES ('2023-04-15', 1, 1, 1, 2, 'FINISHED'),
--        ('2023-05-20', 2, 2, 1, 3, 'IN_PROCESS'),
--        ('2023-06-01', 3, 3, 2, 2, 'NEW');
--
-- INSERT INTO Schedule (emp_id, work_date, first_third, second_third, third_third)
-- VALUES (1, '2023-04-01', 'FREE', 'FREE', 'FREE'),
--        (1, '2023-04-02', 'FREE', 'FREE', 'FREE'),
--        (1, '2023-04-03', 'FREE', 'FREE', 'FREE'),
--
--        (2, '2023-04-01', 'FREE', 'FREE', 'FREE'),
--        (2, '2023-04-02', 'FREE', 'FREE', 'FREE'),
--        (2, '2023-04-03', 'FREE', 'FREE', 'FREE'),
--
--        (3, '2023-04-01', 'FREE', 'FREE', 'FREE'),
--        (3, '2023-04-02', 'FREE', 'FREE', 'FREE'),
--        (3, '2023-04-03', 'FREE', 'FREE', 'FREE');
