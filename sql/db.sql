CREATE TABLE tbl_houses (
    house_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    poster_id BIGINT,

    room_category VARCHAR(50), -- STUDIO / APARTMENT / MINI_APARTMENT / HOUSE

    room_type VARCHAR(50), -- RENTING / ROOMMATE / SUBLET

    rental_target VARCHAR(50),
    title VARCHAR(255),
    description VARCHAR(255),
    price DOUBLE,
    thumbnail VARCHAR(255),
    slug VARCHAR(255) UNIQUE,
    is_verified BOOLEAN,
    total_views INT DEFAULT 0,
    status VARCHAR(50)

    address VARCHAR(255) ,
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL,
    geom  POINT NOT NULL,

    owner_name VARCHAR(255),
    contact_phone_number VARCHAR(20),
    house_area INT,
    max_occupancy INT,

    wifi_fee DOUBLE,
    electricity_fee DOUBLE,
    water_fee DOUBLE,
    internet_fee DOUBLE,
    common_service_fee DOUBLE,

    -- Amenities
    has_private_bathroom BOOLEAN,
    has_wifi BOOLEAN,
    has_fire_safety BOOLEAN,
    has_emergency_exit BOOLEAN,
    has_mezzanine BOOLEAN,
    has_water_heater BOOLEAN,
    has_outdoor_features BOOLEAN,
    has_cooking_area BOOLEAN,
    has_air_conditioner BOOLEAN,
    has_fridge BOOLEAN,
    has_parking BOOLEAN,

    -- Surrounding environment
    near_market BOOLEAN,
    near_supermarket BOOLEAN,
    near_hospital BOOLEAN,
    near_school BOOLEAN,
    near_park BOOLEAN,
    near_gym BOOLEAN,
    near_bus BOOLEAN,
    near_main_road BOOLEAN,

    created_at DATETIME,
    updated_at DATETIME
) AUTO_INCREMENT = 1000000;

DELIMITER //

CREATE TRIGGER update_geom
BEFORE INSERT ON tbl_houses
FOR EACH ROW
BEGIN
    IF NEW.latitude IS NOT NULL AND NEW.longitude IS NOT NULL THEN
        SET NEW.geom = ST_PointFromText(CONCAT('POINT(', NEW.longitude, ' ', NEW.latitude, ')'));
    END IF;
END //

CREATE TRIGGER update_geom_on_update
BEFORE UPDATE ON tbl_houses
FOR EACH ROW
BEGIN
    IF NEW.latitude IS NOT NULL AND NEW.longitude IS NOT NULL THEN
        SET NEW.geom = ST_PointFromText(CONCAT('POINT(', NEW.longitude, ' ', NEW.latitude, ')'));
    END IF;
END //

DELIMITER ;
DELIMITER //


CREATE TABLE tbl_house_attachments (
    attachment_id BIGINT PRIMARY KEY,
    house_id BIGINT,
    position VARCHAR(50),
    attachment_type VARCHAR(50),
    attachment_name VARCHAR(255),
    source VARCHAR(255),
    created_at DATETIME,
    updated_at DATETIME
);
CREATE TABLE tbl_house_favorites (
    favorite_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    house_id BIGINT,
    UNIQUE KEY (user_id, house_id),
    created_at DATETIME
);